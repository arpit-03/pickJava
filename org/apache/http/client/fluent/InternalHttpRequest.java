/*     */ package org.apache.http.client.fluent;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import org.apache.http.HttpVersion;
/*     */ import org.apache.http.ProtocolVersion;
/*     */ import org.apache.http.RequestLine;
/*     */ import org.apache.http.client.config.RequestConfig;
/*     */ import org.apache.http.client.methods.Configurable;
/*     */ import org.apache.http.client.methods.HttpExecutionAware;
/*     */ import org.apache.http.client.methods.HttpUriRequest;
/*     */ import org.apache.http.concurrent.Cancellable;
/*     */ import org.apache.http.message.AbstractHttpMessage;
/*     */ import org.apache.http.message.BasicRequestLine;
/*     */ import org.apache.http.util.Args;
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
/*     */ class InternalHttpRequest
/*     */   extends AbstractHttpMessage
/*     */   implements HttpUriRequest, HttpExecutionAware, Configurable
/*     */ {
/*     */   private final String method;
/*     */   private ProtocolVersion version;
/*     */   private URI uri;
/*     */   private RequestConfig config;
/*     */   private final AtomicBoolean aborted;
/*     */   private final AtomicReference<Cancellable> cancellableRef;
/*     */   
/*     */   InternalHttpRequest(String method, URI requestURI) {
/*  58 */     Args.notBlank(method, "Method");
/*  59 */     Args.notNull(requestURI, "Request URI");
/*  60 */     this.method = method;
/*  61 */     this.uri = requestURI;
/*  62 */     this.aborted = new AtomicBoolean(false);
/*  63 */     this.cancellableRef = new AtomicReference<Cancellable>(null);
/*     */   }
/*     */   
/*     */   public void setProtocolVersion(ProtocolVersion version) {
/*  67 */     this.version = version;
/*     */   }
/*     */ 
/*     */   
/*     */   public ProtocolVersion getProtocolVersion() {
/*  72 */     return (this.version != null) ? this.version : (ProtocolVersion)HttpVersion.HTTP_1_1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMethod() {
/*  77 */     return this.method;
/*     */   }
/*     */ 
/*     */   
/*     */   public URI getURI() {
/*  82 */     return this.uri;
/*     */   }
/*     */ 
/*     */   
/*     */   public void abort() throws UnsupportedOperationException {
/*  87 */     if (this.aborted.compareAndSet(false, true)) {
/*  88 */       Cancellable cancellable = this.cancellableRef.getAndSet(null);
/*  89 */       if (cancellable != null) {
/*  90 */         cancellable.cancel();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAborted() {
/*  97 */     return this.aborted.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCancellable(Cancellable cancellable) {
/* 102 */     if (!this.aborted.get()) {
/* 103 */       this.cancellableRef.set(cancellable);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public RequestLine getRequestLine() {
/* 109 */     ProtocolVersion ver = getProtocolVersion();
/* 110 */     URI uriCopy = getURI();
/* 111 */     String uritext = null;
/* 112 */     if (uriCopy != null) {
/* 113 */       uritext = uriCopy.toASCIIString();
/*     */     }
/* 115 */     if (uritext == null || uritext.isEmpty()) {
/* 116 */       uritext = "/";
/*     */     }
/* 118 */     return (RequestLine)new BasicRequestLine(getMethod(), uritext, ver);
/*     */   }
/*     */ 
/*     */   
/*     */   public RequestConfig getConfig() {
/* 123 */     return this.config;
/*     */   }
/*     */   
/*     */   public void setConfig(RequestConfig config) {
/* 127 */     this.config = config;
/*     */   }
/*     */   
/*     */   public void setURI(URI uri) {
/* 131 */     this.uri = uri;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 136 */     return getMethod() + " " + getURI() + " " + getProtocolVersion();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/fluent/InternalHttpRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */