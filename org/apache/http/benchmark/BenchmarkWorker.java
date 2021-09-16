/*     */ package org.apache.http.benchmark;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.nio.charset.Charset;
/*     */ import javax.net.SocketFactory;
/*     */ import org.apache.http.ConnectionReuseStrategy;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HeaderIterator;
/*     */ import org.apache.http.HttpClientConnection;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpException;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.HttpRequestInterceptor;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.entity.ContentType;
/*     */ import org.apache.http.impl.DefaultConnectionReuseStrategy;
/*     */ import org.apache.http.protocol.HTTP;
/*     */ import org.apache.http.protocol.HttpContext;
/*     */ import org.apache.http.protocol.HttpCoreContext;
/*     */ import org.apache.http.protocol.HttpProcessor;
/*     */ import org.apache.http.protocol.HttpRequestExecutor;
/*     */ import org.apache.http.protocol.ImmutableHttpProcessor;
/*     */ import org.apache.http.protocol.RequestConnControl;
/*     */ import org.apache.http.protocol.RequestContent;
/*     */ import org.apache.http.protocol.RequestExpectContinue;
/*     */ import org.apache.http.protocol.RequestTargetHost;
/*     */ import org.apache.http.protocol.RequestUserAgent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BenchmarkWorker
/*     */   implements Runnable
/*     */ {
/*  67 */   private final byte[] buffer = new byte[4096];
/*     */   private final HttpCoreContext context;
/*     */   private final HttpProcessor httpProcessor;
/*     */   private final HttpRequestExecutor httpexecutor;
/*     */   private final ConnectionReuseStrategy connstrategy;
/*     */   private final HttpRequest request;
/*     */   private final HttpHost targetHost;
/*     */   private final Config config;
/*     */   private final SocketFactory socketFactory;
/*  76 */   private final Stats stats = new Stats();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BenchmarkWorker(HttpRequest request, HttpHost targetHost, SocketFactory socketFactory, Config config) {
/*  84 */     this.context = new HttpCoreContext();
/*  85 */     this.request = request;
/*  86 */     this.targetHost = targetHost;
/*  87 */     this.config = config;
/*  88 */     this.httpProcessor = (HttpProcessor)new ImmutableHttpProcessor(new HttpRequestInterceptor[] { (HttpRequestInterceptor)new RequestContent(), (HttpRequestInterceptor)new RequestTargetHost(), (HttpRequestInterceptor)new RequestConnControl(), (HttpRequestInterceptor)new RequestUserAgent("HttpCore-AB/1.1"), (HttpRequestInterceptor)new RequestExpectContinue(this.config.isUseExpectContinue()) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     this.httpexecutor = new HttpRequestExecutor();
/*     */     
/*  96 */     this.connstrategy = (ConnectionReuseStrategy)DefaultConnectionReuseStrategy.INSTANCE;
/*  97 */     this.socketFactory = socketFactory;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 102 */     HttpResponse response = null;
/* 103 */     BenchmarkConnection conn = new BenchmarkConnection(8192, this.stats);
/*     */     
/* 105 */     String scheme = this.targetHost.getSchemeName();
/* 106 */     String hostname = this.targetHost.getHostName();
/* 107 */     int port = this.targetHost.getPort();
/* 108 */     if (port == -1) {
/* 109 */       if (scheme.equalsIgnoreCase("https")) {
/* 110 */         port = 443;
/*     */       } else {
/* 112 */         port = 80;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 117 */     this.context.setTargetHost(this.targetHost);
/*     */     
/* 119 */     this.stats.start();
/* 120 */     int count = this.config.getRequests();
/* 121 */     for (int i = 0; i < count; i++) {
/*     */       
/*     */       try {
/* 124 */         resetHeader(this.request);
/* 125 */         if (!conn.isOpen()) {
/*     */           Socket socket;
/*     */           
/* 128 */           if (this.socketFactory != null) {
/* 129 */             socket = this.socketFactory.createSocket();
/*     */           } else {
/* 131 */             socket = new Socket();
/*     */           } 
/*     */           
/* 134 */           int timeout = this.config.getSocketTimeout();
/* 135 */           socket.setSoTimeout(timeout);
/* 136 */           socket.connect(new InetSocketAddress(hostname, port), timeout);
/*     */           
/* 138 */           conn.bind(socket);
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 143 */           this.httpexecutor.preProcess(this.request, this.httpProcessor, (HttpContext)this.context);
/*     */           
/* 145 */           response = this.httpexecutor.execute(this.request, (HttpClientConnection)conn, (HttpContext)this.context);
/*     */           
/* 147 */           this.httpexecutor.postProcess(response, this.httpProcessor, (HttpContext)this.context);
/*     */         }
/* 149 */         catch (HttpException e) {
/* 150 */           this.stats.incWriteErrors();
/* 151 */           if (this.config.getVerbosity() >= 2) {
/* 152 */             System.err.println("Failed HTTP request : " + e.getMessage());
/*     */           }
/* 154 */           conn.shutdown();
/*     */         } 
/*     */ 
/*     */         
/* 158 */         verboseOutput(response);
/*     */         
/* 160 */         if (response.getStatusLine().getStatusCode() == 200) {
/* 161 */           this.stats.incSuccessCount();
/*     */         } else {
/* 163 */           this.stats.incFailureCount();
/*     */         } 
/*     */         
/* 166 */         HttpEntity entity = response.getEntity();
/* 167 */         if (entity != null) {
/* 168 */           ContentType ct = ContentType.getOrDefault(entity);
/* 169 */           Charset charset = ct.getCharset();
/* 170 */           if (charset == null) {
/* 171 */             charset = HTTP.DEF_CONTENT_CHARSET;
/*     */           }
/* 173 */           long contentLen = 0L;
/* 174 */           InputStream inStream = entity.getContent();
/*     */           int l;
/* 176 */           while ((l = inStream.read(this.buffer)) != -1) {
/* 177 */             contentLen += l;
/* 178 */             if (this.config.getVerbosity() >= 4) {
/* 179 */               String s = new String(this.buffer, 0, l, charset);
/* 180 */               System.out.print(s);
/*     */             } 
/*     */           } 
/* 183 */           inStream.close();
/* 184 */           this.stats.setContentLength(contentLen);
/*     */         } 
/*     */         
/* 187 */         if (this.config.getVerbosity() >= 4) {
/* 188 */           System.out.println();
/* 189 */           System.out.println();
/*     */         } 
/*     */         
/* 192 */         if (!this.config.isKeepAlive() || !this.connstrategy.keepAlive(response, (HttpContext)this.context)) {
/* 193 */           conn.close();
/*     */         } else {
/* 195 */           this.stats.incKeepAliveCount();
/*     */         }
/*     */       
/* 198 */       } catch (IOException ex) {
/* 199 */         this.stats.incFailureCount();
/* 200 */         if (this.config.getVerbosity() >= 2) {
/* 201 */           System.err.println("I/O error: " + ex.getMessage());
/*     */         }
/* 203 */       } catch (Exception ex) {
/* 204 */         this.stats.incFailureCount();
/* 205 */         if (this.config.getVerbosity() >= 2) {
/* 206 */           System.err.println("Generic error: " + ex.getMessage());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 211 */     this.stats.finish();
/*     */     
/* 213 */     if (response != null) {
/* 214 */       Header header = response.getFirstHeader("Server");
/* 215 */       if (header != null) {
/* 216 */         this.stats.setServerName(header.getValue());
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 221 */       conn.close();
/* 222 */     } catch (IOException ex) {
/* 223 */       this.stats.incFailureCount();
/* 224 */       if (this.config.getVerbosity() >= 2) {
/* 225 */         System.err.println("I/O error: " + ex.getMessage());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void verboseOutput(HttpResponse response) {
/* 231 */     if (this.config.getVerbosity() >= 3) {
/* 232 */       System.out.println(">> " + this.request.getRequestLine().toString());
/* 233 */       Header[] headers = this.request.getAllHeaders();
/* 234 */       for (Header header : headers) {
/* 235 */         System.out.println(">> " + header.toString());
/*     */       }
/* 237 */       System.out.println();
/*     */     } 
/* 239 */     if (this.config.getVerbosity() >= 2) {
/* 240 */       System.out.println(response.getStatusLine().getStatusCode());
/*     */     }
/* 242 */     if (this.config.getVerbosity() >= 3) {
/* 243 */       System.out.println("<< " + response.getStatusLine().toString());
/* 244 */       Header[] headers = response.getAllHeaders();
/* 245 */       for (Header header : headers) {
/* 246 */         System.out.println("<< " + header.toString());
/*     */       }
/* 248 */       System.out.println();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void resetHeader(HttpRequest request) {
/* 253 */     for (HeaderIterator it = request.headerIterator(); it.hasNext(); ) {
/* 254 */       Header header = it.nextHeader();
/* 255 */       if (!(header instanceof DefaultHeader)) {
/* 256 */         it.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Stats getStats() {
/* 262 */     return this.stats;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/benchmark/BenchmarkWorker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */