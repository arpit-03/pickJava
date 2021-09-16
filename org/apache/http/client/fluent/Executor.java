/*     */ package org.apache.http.client.fluent;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.KeyManagementException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.auth.AuthScheme;
/*     */ import org.apache.http.auth.AuthScope;
/*     */ import org.apache.http.auth.Credentials;
/*     */ import org.apache.http.auth.MalformedChallengeException;
/*     */ import org.apache.http.auth.NTCredentials;
/*     */ import org.apache.http.auth.UsernamePasswordCredentials;
/*     */ import org.apache.http.client.AuthCache;
/*     */ import org.apache.http.client.ClientProtocolException;
/*     */ import org.apache.http.client.CookieStore;
/*     */ import org.apache.http.client.CredentialsProvider;
/*     */ import org.apache.http.client.HttpClient;
/*     */ import org.apache.http.client.protocol.HttpClientContext;
/*     */ import org.apache.http.config.Registry;
/*     */ import org.apache.http.config.RegistryBuilder;
/*     */ import org.apache.http.conn.HttpClientConnectionManager;
/*     */ import org.apache.http.conn.scheme.Scheme;
/*     */ import org.apache.http.conn.socket.ConnectionSocketFactory;
/*     */ import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
/*     */ import org.apache.http.conn.socket.PlainConnectionSocketFactory;
/*     */ import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
/*     */ import org.apache.http.conn.ssl.SSLInitializationException;
/*     */ import org.apache.http.impl.auth.BasicScheme;
/*     */ import org.apache.http.impl.client.BasicAuthCache;
/*     */ import org.apache.http.impl.client.BasicCredentialsProvider;
/*     */ import org.apache.http.impl.client.HttpClientBuilder;
/*     */ import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
/*     */ import org.apache.http.message.BasicHeader;
/*     */ import org.apache.http.protocol.HttpContext;
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
/*     */ public class Executor
/*     */ {
/*     */   static final PoolingHttpClientConnectionManager CONNMGR;
/*     */   
/*     */   static {
/*     */     SSLConnectionSocketFactory sSLConnectionSocketFactory;
/*  76 */     LayeredConnectionSocketFactory ssl = null;
/*     */     try {
/*  78 */       sSLConnectionSocketFactory = SSLConnectionSocketFactory.getSystemSocketFactory();
/*  79 */     } catch (SSLInitializationException ex) {
/*     */ 
/*     */       
/*  82 */       try { SSLContext sslcontext = SSLContext.getInstance("TLS");
/*  83 */         sslcontext.init(null, null, null);
/*  84 */         sSLConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontext); }
/*  85 */       catch (SecurityException ignore) {  }
/*  86 */       catch (KeyManagementException ignore) {  }
/*  87 */       catch (NoSuchAlgorithmException ignore) {}
/*     */     } 
/*     */ 
/*     */     
/*  91 */     Registry<ConnectionSocketFactory> sfr = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", (sSLConnectionSocketFactory != null) ? sSLConnectionSocketFactory : SSLConnectionSocketFactory.getSocketFactory()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     CONNMGR = new PoolingHttpClientConnectionManager(sfr);
/*  97 */     CONNMGR.setDefaultMaxPerRoute(100);
/*  98 */     CONNMGR.setMaxTotal(200);
/*  99 */     CONNMGR.setValidateAfterInactivity(1000);
/* 100 */   } static final HttpClient CLIENT = (HttpClient)HttpClientBuilder.create().setConnectionManager((HttpClientConnectionManager)CONNMGR).build(); private final HttpClient httpclient;
/*     */   private volatile AuthCache authCache;
/*     */   private volatile CredentialsProvider credentialsProvider;
/*     */   private volatile CookieStore cookieStore;
/*     */   
/*     */   public static Executor newInstance() {
/* 106 */     return new Executor(CLIENT);
/*     */   }
/*     */   
/*     */   public static Executor newInstance(HttpClient httpclient) {
/* 110 */     return new Executor((httpclient != null) ? httpclient : CLIENT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Executor(HttpClient httpclient) {
/* 120 */     this.httpclient = httpclient;
/* 121 */     this.authCache = (AuthCache)new BasicAuthCache();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Executor use(CredentialsProvider credentialsProvider) {
/* 128 */     this.credentialsProvider = credentialsProvider;
/* 129 */     return this;
/*     */   }
/*     */   
/*     */   public Executor auth(AuthScope authScope, Credentials creds) {
/* 133 */     if (this.credentialsProvider == null) {
/* 134 */       this.credentialsProvider = (CredentialsProvider)new BasicCredentialsProvider();
/*     */     }
/* 136 */     this.credentialsProvider.setCredentials(authScope, creds);
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   public Executor auth(HttpHost host, Credentials creds) {
/* 141 */     AuthScope authScope = (host != null) ? new AuthScope(host.getHostName(), host.getPort()) : AuthScope.ANY;
/*     */     
/* 143 */     return auth(authScope, creds);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Executor auth(String host, Credentials creds) {
/* 150 */     return auth(HttpHost.create(host), creds);
/*     */   }
/*     */   
/*     */   public Executor authPreemptive(HttpHost host) {
/* 154 */     BasicScheme basicScheme = new BasicScheme();
/*     */     try {
/* 156 */       basicScheme.processChallenge((Header)new BasicHeader("WWW-Authenticate", "BASIC "));
/* 157 */     } catch (MalformedChallengeException ignore) {}
/*     */     
/* 159 */     this.authCache.put(host, (AuthScheme)basicScheme);
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Executor authPreemptive(String host) {
/* 167 */     return authPreemptive(HttpHost.create(host));
/*     */   }
/*     */   
/*     */   public Executor authPreemptiveProxy(HttpHost proxy) {
/* 171 */     BasicScheme basicScheme = new BasicScheme();
/*     */     try {
/* 173 */       basicScheme.processChallenge((Header)new BasicHeader("Proxy-Authenticate", "BASIC "));
/* 174 */     } catch (MalformedChallengeException ignore) {}
/*     */     
/* 176 */     this.authCache.put(proxy, (AuthScheme)basicScheme);
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Executor authPreemptiveProxy(String proxy) {
/* 184 */     return authPreemptiveProxy(HttpHost.create(proxy));
/*     */   }
/*     */   
/*     */   public Executor auth(Credentials cred) {
/* 188 */     return auth(AuthScope.ANY, cred);
/*     */   }
/*     */   
/*     */   public Executor auth(String username, String password) {
/* 192 */     return auth((Credentials)new UsernamePasswordCredentials(username, password));
/*     */   }
/*     */ 
/*     */   
/*     */   public Executor auth(String username, String password, String workstation, String domain) {
/* 197 */     return auth((Credentials)new NTCredentials(username, password, workstation, domain));
/*     */   }
/*     */ 
/*     */   
/*     */   public Executor auth(HttpHost host, String username, String password) {
/* 202 */     return auth(host, (Credentials)new UsernamePasswordCredentials(username, password));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Executor auth(HttpHost host, String username, String password, String workstation, String domain) {
/* 208 */     return auth(host, (Credentials)new NTCredentials(username, password, workstation, domain));
/*     */   }
/*     */   
/*     */   public Executor clearAuth() {
/* 212 */     if (this.credentialsProvider != null) {
/* 213 */       this.credentialsProvider.clear();
/*     */     }
/* 215 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Executor cookieStore(CookieStore cookieStore) {
/* 223 */     this.cookieStore = cookieStore;
/* 224 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Executor use(CookieStore cookieStore) {
/* 231 */     this.cookieStore = cookieStore;
/* 232 */     return this;
/*     */   }
/*     */   
/*     */   public Executor clearCookies() {
/* 236 */     if (this.cookieStore != null) {
/* 237 */       this.cookieStore.clear();
/*     */     }
/* 239 */     return this;
/*     */   }
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
/*     */   public Response execute(Request request) throws ClientProtocolException, IOException {
/* 252 */     HttpClientContext localContext = HttpClientContext.create();
/* 253 */     if (this.credentialsProvider != null) {
/* 254 */       localContext.setAttribute("http.auth.credentials-provider", this.credentialsProvider);
/*     */     }
/* 256 */     if (this.authCache != null) {
/* 257 */       localContext.setAttribute("http.auth.auth-cache", this.authCache);
/*     */     }
/* 259 */     if (this.cookieStore != null) {
/* 260 */       localContext.setAttribute("http.cookie-store", this.cookieStore);
/*     */     }
/* 262 */     return new Response(request.internalExecute(this.httpclient, (HttpContext)localContext));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void registerScheme(Scheme scheme) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void unregisterScheme(String name) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void closeIdleConnections() {
/* 284 */     CONNMGR.closeIdleConnections(0L, TimeUnit.MICROSECONDS);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/fluent/Executor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */