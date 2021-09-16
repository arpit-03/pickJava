/*     */ package org.apache.http.client.cache;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HeaderIterator;
/*     */ import org.apache.http.ProtocolVersion;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.utils.DateUtils;
/*     */ import org.apache.http.message.HeaderGroup;
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
/*     */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*     */ public class HttpCacheEntry
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6300496422359477413L;
/*     */   private static final String REQUEST_METHOD_HEADER_NAME = "Hc-Request-Method";
/*     */   private final Date requestDate;
/*     */   private final Date responseDate;
/*     */   private final StatusLine statusLine;
/*     */   private final HeaderGroup responseHeaders;
/*     */   private final Resource resource;
/*     */   private final Map<String, String> variantMap;
/*     */   private final Date date;
/*     */   
/*     */   public HttpCacheEntry(Date requestDate, Date responseDate, StatusLine statusLine, Header[] responseHeaders, Resource resource, Map<String, String> variantMap, String requestMethod) {
/*  97 */     Args.notNull(requestDate, "Request date");
/*  98 */     Args.notNull(responseDate, "Response date");
/*  99 */     Args.notNull(statusLine, "Status line");
/* 100 */     Args.notNull(responseHeaders, "Response headers");
/* 101 */     this.requestDate = requestDate;
/* 102 */     this.responseDate = responseDate;
/* 103 */     this.statusLine = statusLine;
/* 104 */     this.responseHeaders = new HeaderGroup();
/* 105 */     this.responseHeaders.setHeaders(responseHeaders);
/* 106 */     this.resource = resource;
/* 107 */     this.variantMap = (variantMap != null) ? new HashMap<String, String>(variantMap) : null;
/*     */ 
/*     */     
/* 110 */     this.date = parseDate();
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
/*     */   public HttpCacheEntry(Date requestDate, Date responseDate, StatusLine statusLine, Header[] responseHeaders, Resource resource, Map<String, String> variantMap) {
/* 138 */     this(requestDate, responseDate, statusLine, responseHeaders, resource, variantMap, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpCacheEntry(Date requestDate, Date responseDate, StatusLine statusLine, Header[] responseHeaders, Resource resource) {
/* 159 */     this(requestDate, responseDate, statusLine, responseHeaders, resource, new HashMap<String, String>());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpCacheEntry(Date requestDate, Date responseDate, StatusLine statusLine, Header[] responseHeaders, Resource resource, String requestMethod) {
/* 181 */     this(requestDate, responseDate, statusLine, responseHeaders, resource, new HashMap<String, String>(), requestMethod);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Date parseDate() {
/* 190 */     Header dateHdr = getFirstHeader("Date");
/* 191 */     if (dateHdr == null) {
/* 192 */       return null;
/*     */     }
/* 194 */     return DateUtils.parseDate(dateHdr.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatusLine getStatusLine() {
/* 202 */     return this.statusLine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProtocolVersion getProtocolVersion() {
/* 210 */     return this.statusLine.getProtocolVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReasonPhrase() {
/* 218 */     return this.statusLine.getReasonPhrase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStatusCode() {
/* 226 */     return this.statusLine.getStatusCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getRequestDate() {
/* 235 */     return this.requestDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getResponseDate() {
/* 243 */     return this.responseDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Header[] getAllHeaders() {
/* 250 */     HeaderGroup filteredHeaders = new HeaderGroup();
/* 251 */     HeaderIterator iterator = this.responseHeaders.iterator();
/* 252 */     while (iterator.hasNext()) {
/* 253 */       Header header = (Header)iterator.next();
/* 254 */       if (!"Hc-Request-Method".equals(header.getName())) {
/* 255 */         filteredHeaders.addHeader(header);
/*     */       }
/*     */     } 
/* 258 */     return filteredHeaders.getAllHeaders();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Header getFirstHeader(String name) {
/* 266 */     if ("Hc-Request-Method".equalsIgnoreCase(name)) {
/* 267 */       return null;
/*     */     }
/* 269 */     return this.responseHeaders.getFirstHeader(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Header[] getHeaders(String name) {
/* 277 */     if ("Hc-Request-Method".equalsIgnoreCase(name)) {
/* 278 */       return new Header[0];
/*     */     }
/* 280 */     return this.responseHeaders.getHeaders(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getDate() {
/* 290 */     return this.date;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Resource getResource() {
/* 297 */     return this.resource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasVariants() {
/* 307 */     return (getFirstHeader("Vary") != null);
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
/*     */   public Map<String, String> getVariantMap() {
/* 320 */     return Collections.unmodifiableMap(this.variantMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRequestMethod() {
/* 330 */     Header requestMethodHeader = this.responseHeaders.getFirstHeader("Hc-Request-Method");
/*     */     
/* 332 */     if (requestMethodHeader != null) {
/* 333 */       return requestMethodHeader.getValue();
/*     */     }
/* 335 */     return "GET";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 344 */     return "[request date=" + this.requestDate + "; response date=" + this.responseDate + "; statusLine=" + this.statusLine + "]";
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/cache/HttpCacheEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */