/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HeaderIterator;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.HttpVersion;
/*     */ import org.apache.http.ProtocolVersion;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.message.AbstractHttpMessage;
/*     */ import org.apache.http.message.BasicStatusLine;
/*     */ import org.apache.http.params.BasicHttpParams;
/*     */ import org.apache.http.params.HttpParams;
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
/*     */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*     */ final class OptionsHttp11Response
/*     */   extends AbstractHttpMessage
/*     */   implements HttpResponse
/*     */ {
/*  53 */   private final StatusLine statusLine = (StatusLine)new BasicStatusLine((ProtocolVersion)HttpVersion.HTTP_1_1, 501, "");
/*     */   
/*  55 */   private final ProtocolVersion version = (ProtocolVersion)HttpVersion.HTTP_1_1;
/*     */ 
/*     */   
/*     */   public StatusLine getStatusLine() {
/*  59 */     return this.statusLine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatusLine(StatusLine statusline) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatusLine(ProtocolVersion ver, int code) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatusLine(ProtocolVersion ver, int code, String reason) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatusCode(int code) throws IllegalStateException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReasonPhrase(String reason) throws IllegalStateException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpEntity getEntity() {
/*  89 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntity(HttpEntity entity) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Locale getLocale() {
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocale(Locale loc) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ProtocolVersion getProtocolVersion() {
/* 109 */     return this.version;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsHeader(String name) {
/* 114 */     return this.headergroup.containsHeader(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Header[] getHeaders(String name) {
/* 119 */     return this.headergroup.getHeaders(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Header getFirstHeader(String name) {
/* 124 */     return this.headergroup.getFirstHeader(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Header getLastHeader(String name) {
/* 129 */     return this.headergroup.getLastHeader(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Header[] getAllHeaders() {
/* 134 */     return this.headergroup.getAllHeaders();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addHeader(Header header) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addHeader(String name, String value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeader(Header header) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeader(String name, String value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeaders(Header[] headers) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeHeader(Header header) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeHeaders(String name) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderIterator headerIterator() {
/* 174 */     return this.headergroup.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public HeaderIterator headerIterator(String name) {
/* 179 */     return this.headergroup.iterator(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpParams getParams() {
/* 184 */     if (this.params == null) {
/* 185 */       this.params = (HttpParams)new BasicHttpParams();
/*     */     }
/* 187 */     return this.params;
/*     */   }
/*     */   
/*     */   public void setParams(HttpParams params) {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/OptionsHttp11Response.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */