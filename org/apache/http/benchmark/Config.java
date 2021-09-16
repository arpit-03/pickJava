/*     */ package org.apache.http.benchmark;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.net.URL;
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
/*     */ public class Config
/*     */ {
/*     */   private URL url;
/*     */   private int requests;
/*     */   private int threads;
/*     */   private boolean keepAlive;
/*     */   private int verbosity;
/*     */   private boolean headInsteadOfGet;
/*     */   private boolean useHttp1_0;
/*     */   private String contentType;
/*     */   private String[] headers;
/*     */   private int socketTimeout;
/*  44 */   private String method = "GET";
/*     */   private boolean useChunking;
/*     */   private boolean useExpectContinue;
/*     */   private boolean useAcceptGZip;
/*  48 */   private File payloadFile = null;
/*  49 */   private String payloadText = null;
/*  50 */   private String soapAction = null;
/*     */   
/*     */   private boolean disableSSLVerification = true;
/*  53 */   private String trustStorePath = null;
/*  54 */   private String identityStorePath = null;
/*  55 */   private String trustStorePassword = null;
/*  56 */   private String identityStorePassword = null;
/*     */ 
/*     */   
/*     */   public Config() {
/*  60 */     this.url = null;
/*  61 */     this.requests = 1;
/*  62 */     this.threads = 1;
/*  63 */     this.keepAlive = false;
/*  64 */     this.verbosity = 0;
/*  65 */     this.headInsteadOfGet = false;
/*  66 */     this.useHttp1_0 = false;
/*  67 */     this.payloadFile = null;
/*  68 */     this.payloadText = null;
/*  69 */     this.contentType = null;
/*  70 */     this.headers = null;
/*  71 */     this.socketTimeout = 60000;
/*     */   }
/*     */   
/*     */   public URL getUrl() {
/*  75 */     return this.url;
/*     */   }
/*     */   
/*     */   public void setUrl(URL url) {
/*  79 */     this.url = url;
/*     */   }
/*     */   
/*     */   public int getRequests() {
/*  83 */     return this.requests;
/*     */   }
/*     */   
/*     */   public void setRequests(int requests) {
/*  87 */     this.requests = requests;
/*     */   }
/*     */   
/*     */   public int getThreads() {
/*  91 */     return this.threads;
/*     */   }
/*     */   
/*     */   public void setThreads(int threads) {
/*  95 */     this.threads = threads;
/*     */   }
/*     */   
/*     */   public boolean isKeepAlive() {
/*  99 */     return this.keepAlive;
/*     */   }
/*     */   
/*     */   public void setKeepAlive(boolean keepAlive) {
/* 103 */     this.keepAlive = keepAlive;
/*     */   }
/*     */   
/*     */   public int getVerbosity() {
/* 107 */     return this.verbosity;
/*     */   }
/*     */   
/*     */   public void setVerbosity(int verbosity) {
/* 111 */     this.verbosity = verbosity;
/*     */   }
/*     */   
/*     */   public boolean isHeadInsteadOfGet() {
/* 115 */     return this.headInsteadOfGet;
/*     */   }
/*     */   
/*     */   public void setHeadInsteadOfGet(boolean headInsteadOfGet) {
/* 119 */     this.headInsteadOfGet = headInsteadOfGet;
/* 120 */     this.method = "HEAD";
/*     */   }
/*     */   
/*     */   public boolean isUseHttp1_0() {
/* 124 */     return this.useHttp1_0;
/*     */   }
/*     */   
/*     */   public void setUseHttp1_0(boolean useHttp1_0) {
/* 128 */     this.useHttp1_0 = useHttp1_0;
/*     */   }
/*     */   
/*     */   public File getPayloadFile() {
/* 132 */     return this.payloadFile;
/*     */   }
/*     */   
/*     */   public void setPayloadFile(File payloadFile) {
/* 136 */     this.payloadFile = payloadFile;
/*     */   }
/*     */   
/*     */   public String getContentType() {
/* 140 */     return this.contentType;
/*     */   }
/*     */   
/*     */   public void setContentType(String contentType) {
/* 144 */     this.contentType = contentType;
/*     */   }
/*     */   
/*     */   public String[] getHeaders() {
/* 148 */     return this.headers;
/*     */   }
/*     */   
/*     */   public void setHeaders(String[] headers) {
/* 152 */     this.headers = headers;
/*     */   }
/*     */   
/*     */   public int getSocketTimeout() {
/* 156 */     return this.socketTimeout;
/*     */   }
/*     */   
/*     */   public void setSocketTimeout(int socketTimeout) {
/* 160 */     this.socketTimeout = socketTimeout;
/*     */   }
/*     */   
/*     */   public void setMethod(String method) {
/* 164 */     this.method = method;
/*     */   }
/*     */   
/*     */   public void setUseChunking(boolean useChunking) {
/* 168 */     this.useChunking = useChunking;
/*     */   }
/*     */   
/*     */   public void setUseExpectContinue(boolean useExpectContinue) {
/* 172 */     this.useExpectContinue = useExpectContinue;
/*     */   }
/*     */   
/*     */   public void setUseAcceptGZip(boolean useAcceptGZip) {
/* 176 */     this.useAcceptGZip = useAcceptGZip;
/*     */   }
/*     */   
/*     */   public String getMethod() {
/* 180 */     return this.method;
/*     */   }
/*     */   
/*     */   public boolean isUseChunking() {
/* 184 */     return this.useChunking;
/*     */   }
/*     */   
/*     */   public boolean isUseExpectContinue() {
/* 188 */     return this.useExpectContinue;
/*     */   }
/*     */   
/*     */   public boolean isUseAcceptGZip() {
/* 192 */     return this.useAcceptGZip;
/*     */   }
/*     */   
/*     */   public String getPayloadText() {
/* 196 */     return this.payloadText;
/*     */   }
/*     */   
/*     */   public String getSoapAction() {
/* 200 */     return this.soapAction;
/*     */   }
/*     */   
/*     */   public boolean isDisableSSLVerification() {
/* 204 */     return this.disableSSLVerification;
/*     */   }
/*     */   
/*     */   public String getTrustStorePath() {
/* 208 */     return this.trustStorePath;
/*     */   }
/*     */   
/*     */   public String getIdentityStorePath() {
/* 212 */     return this.identityStorePath;
/*     */   }
/*     */   
/*     */   public String getTrustStorePassword() {
/* 216 */     return this.trustStorePassword;
/*     */   }
/*     */   
/*     */   public String getIdentityStorePassword() {
/* 220 */     return this.identityStorePassword;
/*     */   }
/*     */   
/*     */   public void setPayloadText(String payloadText) {
/* 224 */     this.payloadText = payloadText;
/*     */   }
/*     */   
/*     */   public void setSoapAction(String soapAction) {
/* 228 */     this.soapAction = soapAction;
/*     */   }
/*     */   
/*     */   public void setDisableSSLVerification(boolean disableSSLVerification) {
/* 232 */     this.disableSSLVerification = disableSSLVerification;
/*     */   }
/*     */   
/*     */   public void setTrustStorePath(String trustStorePath) {
/* 236 */     this.trustStorePath = trustStorePath;
/*     */   }
/*     */   
/*     */   public void setIdentityStorePath(String identityStorePath) {
/* 240 */     this.identityStorePath = identityStorePath;
/*     */   }
/*     */   
/*     */   public void setTrustStorePassword(String trustStorePassword) {
/* 244 */     this.trustStorePassword = trustStorePassword;
/*     */   }
/*     */   
/*     */   public void setIdentityStorePassword(String identityStorePassword) {
/* 248 */     this.identityStorePassword = identityStorePassword;
/*     */   }
/*     */   
/*     */   public Config copy() {
/* 252 */     Config copy = new Config();
/* 253 */     copy.url = this.url;
/* 254 */     copy.requests = this.requests;
/* 255 */     copy.threads = this.threads;
/* 256 */     copy.keepAlive = this.keepAlive;
/* 257 */     copy.verbosity = this.verbosity;
/* 258 */     copy.headInsteadOfGet = this.headInsteadOfGet;
/* 259 */     copy.useHttp1_0 = this.useHttp1_0;
/* 260 */     copy.contentType = this.contentType;
/* 261 */     copy.headers = this.headers;
/* 262 */     copy.socketTimeout = this.socketTimeout;
/* 263 */     copy.method = this.method;
/* 264 */     copy.useChunking = this.useChunking;
/* 265 */     copy.useExpectContinue = this.useExpectContinue;
/* 266 */     copy.useAcceptGZip = this.useAcceptGZip;
/* 267 */     copy.payloadFile = this.payloadFile;
/* 268 */     copy.payloadText = this.payloadText;
/* 269 */     copy.soapAction = this.soapAction;
/*     */     
/* 271 */     copy.disableSSLVerification = this.disableSSLVerification;
/* 272 */     copy.trustStorePath = this.trustStorePath;
/* 273 */     copy.identityStorePath = this.identityStorePath;
/* 274 */     copy.trustStorePassword = this.trustStorePassword;
/* 275 */     copy.identityStorePassword = this.identityStorePassword;
/* 276 */     return copy;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/benchmark/Config.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */