/*     */ package org.apache.http.benchmark;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.net.URL;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.net.SocketFactory;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import org.apache.commons.cli.CommandLine;
/*     */ import org.apache.commons.cli.Options;
/*     */ import org.apache.commons.cli.PosixParser;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.HttpVersion;
/*     */ import org.apache.http.ProtocolVersion;
/*     */ import org.apache.http.entity.ContentType;
/*     */ import org.apache.http.entity.FileEntity;
/*     */ import org.apache.http.entity.StringEntity;
/*     */ import org.apache.http.message.BasicHttpEntityEnclosingRequest;
/*     */ import org.apache.http.message.BasicHttpRequest;
/*     */ import org.apache.http.ssl.SSLContextBuilder;
/*     */ import org.apache.http.ssl.TrustStrategy;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpBenchmark
/*     */ {
/*     */   private final Config config;
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*  71 */     Options options = CommandLineUtils.getOptions();
/*  72 */     PosixParser posixParser = new PosixParser();
/*  73 */     CommandLine cmd = posixParser.parse(options, args);
/*     */     
/*  75 */     if (args.length == 0 || cmd.hasOption('h') || (cmd.getArgs()).length != 1) {
/*  76 */       CommandLineUtils.showUsage(options);
/*  77 */       System.exit(1);
/*     */     } 
/*     */     
/*  80 */     Config config = new Config();
/*  81 */     CommandLineUtils.parseCommandLine(cmd, config);
/*     */     
/*  83 */     if (config.getUrl() == null) {
/*  84 */       CommandLineUtils.showUsage(options);
/*  85 */       System.exit(1);
/*     */     } 
/*     */     
/*  88 */     HttpBenchmark httpBenchmark = new HttpBenchmark(config);
/*  89 */     httpBenchmark.execute();
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpBenchmark(Config config) {
/*  94 */     this.config = (config != null) ? config : new Config();
/*     */   } private HttpRequest createRequest() {
/*     */     StringEntity stringEntity;
/*     */     BasicHttpRequest basicHttpRequest;
/*  98 */     URL url = this.config.getUrl();
/*  99 */     HttpEntity entity = null;
/*     */ 
/*     */     
/* 102 */     if (this.config.getPayloadFile() != null) {
/* 103 */       FileEntity fe = new FileEntity(this.config.getPayloadFile());
/* 104 */       fe.setContentType(this.config.getContentType());
/* 105 */       fe.setChunked(this.config.isUseChunking());
/* 106 */       FileEntity fileEntity1 = fe;
/* 107 */     } else if (this.config.getPayloadText() != null) {
/* 108 */       StringEntity se = new StringEntity(this.config.getPayloadText(), ContentType.parse(this.config.getContentType()));
/*     */       
/* 110 */       se.setChunked(this.config.isUseChunking());
/* 111 */       stringEntity = se;
/*     */     } 
/* 113 */     HttpVersion ver = this.config.isUseHttp1_0() ? HttpVersion.HTTP_1_0 : HttpVersion.HTTP_1_1;
/*     */     
/* 115 */     if ("POST".equals(this.config.getMethod())) {
/* 116 */       BasicHttpEntityEnclosingRequest httppost = new BasicHttpEntityEnclosingRequest("POST", url.getPath(), (ProtocolVersion)ver);
/*     */       
/* 118 */       httppost.setEntity((HttpEntity)stringEntity);
/* 119 */       BasicHttpEntityEnclosingRequest basicHttpEntityEnclosingRequest1 = httppost;
/* 120 */     } else if ("PUT".equals(this.config.getMethod())) {
/* 121 */       BasicHttpEntityEnclosingRequest httpput = new BasicHttpEntityEnclosingRequest("PUT", url.getPath(), (ProtocolVersion)ver);
/*     */       
/* 123 */       httpput.setEntity((HttpEntity)stringEntity);
/* 124 */       BasicHttpEntityEnclosingRequest basicHttpEntityEnclosingRequest1 = httpput;
/*     */     } else {
/* 126 */       String path = url.getPath();
/* 127 */       if (url.getQuery() != null && url.getQuery().length() > 0) {
/* 128 */         path = path + "?" + url.getQuery();
/* 129 */       } else if (path.trim().isEmpty()) {
/* 130 */         path = "/";
/*     */       } 
/* 132 */       basicHttpRequest = new BasicHttpRequest(this.config.getMethod(), path, (ProtocolVersion)ver);
/*     */     } 
/*     */     
/* 135 */     if (!this.config.isKeepAlive()) {
/* 136 */       basicHttpRequest.addHeader((Header)new DefaultHeader("Connection", "Close"));
/*     */     }
/*     */     
/* 139 */     String[] headers = this.config.getHeaders();
/* 140 */     if (headers != null) {
/* 141 */       for (String s : headers) {
/* 142 */         int pos = s.indexOf(':');
/* 143 */         if (pos != -1) {
/* 144 */           DefaultHeader defaultHeader = new DefaultHeader(s.substring(0, pos).trim(), s.substring(pos + 1));
/* 145 */           basicHttpRequest.addHeader((Header)defaultHeader);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 150 */     if (this.config.isUseAcceptGZip()) {
/* 151 */       basicHttpRequest.addHeader((Header)new DefaultHeader("Accept-Encoding", "gzip"));
/*     */     }
/*     */     
/* 154 */     if (this.config.getSoapAction() != null && this.config.getSoapAction().length() > 0) {
/* 155 */       basicHttpRequest.addHeader((Header)new DefaultHeader("SOAPAction", this.config.getSoapAction()));
/*     */     }
/* 157 */     return (HttpRequest)basicHttpRequest;
/*     */   }
/*     */   
/*     */   public String execute() throws Exception {
/* 161 */     Results results = doExecute();
/* 162 */     ResultProcessor.printResults(results);
/* 163 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public Results doExecute() throws Exception {
/* 168 */     URL url = this.config.getUrl();
/* 169 */     HttpHost host = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
/*     */     
/* 171 */     ThreadPoolExecutor workerPool = new ThreadPoolExecutor(this.config.getThreads(), this.config.getThreads(), 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory()
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*     */           public Thread newThread(Runnable r)
/*     */           {
/* 178 */             return new Thread(r, "ClientPool");
/*     */           }
/*     */         });
/*     */     
/* 182 */     workerPool.prestartAllCoreThreads();
/*     */     
/* 184 */     SocketFactory socketFactory = null;
/* 185 */     if ("https".equals(host.getSchemeName())) {
/* 186 */       SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
/* 187 */       sslContextBuilder.setProtocol("SSL");
/* 188 */       if (this.config.isDisableSSLVerification()) {
/* 189 */         sslContextBuilder.loadTrustMaterial(null, new TrustStrategy()
/*     */             {
/*     */               
/*     */               public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException
/*     */               {
/* 194 */                 return true;
/*     */               }
/*     */             });
/*     */       }
/* 198 */       else if (this.config.getTrustStorePath() != null) {
/* 199 */         sslContextBuilder.loadTrustMaterial(new File(this.config.getTrustStorePath()), (this.config.getTrustStorePassword() != null) ? this.config.getTrustStorePassword().toCharArray() : null);
/*     */       } 
/*     */ 
/*     */       
/* 203 */       if (this.config.getIdentityStorePath() != null) {
/* 204 */         sslContextBuilder.loadKeyMaterial(new File(this.config.getIdentityStorePath()), (this.config.getIdentityStorePassword() != null) ? this.config.getIdentityStorePassword().toCharArray() : null, (this.config.getIdentityStorePassword() != null) ? this.config.getIdentityStorePassword().toCharArray() : null);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 209 */       SSLContext sslContext = sslContextBuilder.build();
/* 210 */       socketFactory = sslContext.getSocketFactory();
/*     */     } 
/*     */     
/* 213 */     BenchmarkWorker[] workers = new BenchmarkWorker[this.config.getThreads()];
/* 214 */     for (int i = 0; i < workers.length; i++) {
/* 215 */       workers[i] = new BenchmarkWorker(createRequest(), host, socketFactory, this.config);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 220 */       workerPool.execute(workers[i]);
/*     */     } 
/*     */     
/* 223 */     while (workerPool.getCompletedTaskCount() < this.config.getThreads()) {
/* 224 */       Thread.yield();
/*     */       try {
/* 226 */         Thread.sleep(1000L);
/* 227 */       } catch (InterruptedException ignore) {}
/*     */     } 
/*     */ 
/*     */     
/* 231 */     workerPool.shutdown();
/* 232 */     return ResultProcessor.collectResults(workers, host, this.config.getUrl().toString());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/benchmark/HttpBenchmark.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */