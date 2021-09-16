/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HeaderElement;
/*     */ import org.apache.http.HttpMessage;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.HttpVersion;
/*     */ import org.apache.http.ProtocolVersion;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.utils.DateUtils;
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
/*     */ class ResponseCachingPolicy
/*     */ {
/*  57 */   private static final String[] AUTH_CACHEABLE_PARAMS = new String[] { "s-maxage", "must-revalidate", "public" };
/*     */   
/*     */   private final long maxObjectSizeBytes;
/*     */   
/*     */   private final boolean sharedCache;
/*     */   private final boolean neverCache1_0ResponsesWithQueryString;
/*  63 */   private final Log log = LogFactory.getLog(getClass());
/*  64 */   private static final Set<Integer> cacheableStatuses = new HashSet<Integer>(Arrays.asList(new Integer[] { Integer.valueOf(200), Integer.valueOf(203), Integer.valueOf(300), Integer.valueOf(301), Integer.valueOf(410) }));
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
/*     */   private final Set<Integer> uncacheableStatuses;
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
/*     */   public ResponseCachingPolicy(long maxObjectSizeBytes, boolean sharedCache, boolean neverCache1_0ResponsesWithQueryString, boolean allow303Caching) {
/*  87 */     this.maxObjectSizeBytes = maxObjectSizeBytes;
/*  88 */     this.sharedCache = sharedCache;
/*  89 */     this.neverCache1_0ResponsesWithQueryString = neverCache1_0ResponsesWithQueryString;
/*  90 */     if (allow303Caching) {
/*  91 */       this.uncacheableStatuses = new HashSet<Integer>(Arrays.asList(new Integer[] { Integer.valueOf(206) }));
/*     */     } else {
/*     */       
/*  94 */       this.uncacheableStatuses = new HashSet<Integer>(Arrays.asList(new Integer[] { Integer.valueOf(206), Integer.valueOf(303) }));
/*     */     } 
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
/*     */   public boolean isResponseCacheable(String httpMethod, HttpResponse response) {
/* 107 */     boolean cacheable = false;
/*     */     
/* 109 */     if (!"GET".equals(httpMethod) && !"HEAD".equals(httpMethod)) {
/*     */       
/* 111 */       this.log.debug("Response was not cacheable.");
/* 112 */       return false;
/*     */     } 
/*     */     
/* 115 */     int status = response.getStatusLine().getStatusCode();
/* 116 */     if (cacheableStatuses.contains(Integer.valueOf(status)))
/*     */     
/* 118 */     { cacheable = true; }
/* 119 */     else { if (this.uncacheableStatuses.contains(Integer.valueOf(status)))
/* 120 */         return false; 
/* 121 */       if (unknownStatusCode(status))
/*     */       {
/*     */         
/* 124 */         return false;
/*     */       } }
/*     */     
/* 127 */     Header contentLength = response.getFirstHeader("Content-Length");
/* 128 */     if (contentLength != null) {
/* 129 */       long contentLengthValue = Long.parseLong(contentLength.getValue());
/* 130 */       if (contentLengthValue > this.maxObjectSizeBytes) {
/* 131 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 135 */     Header[] ageHeaders = response.getHeaders("Age");
/*     */     
/* 137 */     if (ageHeaders.length > 1) {
/* 138 */       return false;
/*     */     }
/*     */     
/* 141 */     Header[] expiresHeaders = response.getHeaders("Expires");
/*     */     
/* 143 */     if (expiresHeaders.length > 1) {
/* 144 */       return false;
/*     */     }
/*     */     
/* 147 */     Header[] dateHeaders = response.getHeaders("Date");
/*     */     
/* 149 */     if (dateHeaders.length != 1) {
/* 150 */       return false;
/*     */     }
/*     */     
/* 153 */     Date date = DateUtils.parseDate(dateHeaders[0].getValue());
/* 154 */     if (date == null) {
/* 155 */       return false;
/*     */     }
/*     */     
/* 158 */     for (Header varyHdr : response.getHeaders("Vary")) {
/* 159 */       for (HeaderElement elem : varyHdr.getElements()) {
/* 160 */         if ("*".equals(elem.getName())) {
/* 161 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 166 */     if (isExplicitlyNonCacheable(response)) {
/* 167 */       return false;
/*     */     }
/*     */     
/* 170 */     return (cacheable || isExplicitlyCacheable(response));
/*     */   }
/*     */   
/*     */   private boolean unknownStatusCode(int status) {
/* 174 */     if (status >= 100 && status <= 101) {
/* 175 */       return false;
/*     */     }
/* 177 */     if (status >= 200 && status <= 206) {
/* 178 */       return false;
/*     */     }
/* 180 */     if (status >= 300 && status <= 307) {
/* 181 */       return false;
/*     */     }
/* 183 */     if (status >= 400 && status <= 417) {
/* 184 */       return false;
/*     */     }
/* 186 */     if (status >= 500 && status <= 505) {
/* 187 */       return false;
/*     */     }
/* 189 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean isExplicitlyNonCacheable(HttpResponse response) {
/* 193 */     Header[] cacheControlHeaders = response.getHeaders("Cache-Control");
/* 194 */     for (Header header : cacheControlHeaders) {
/* 195 */       for (HeaderElement elem : header.getElements()) {
/* 196 */         if ("no-store".equals(elem.getName()) || "no-cache".equals(elem.getName()) || (this.sharedCache && "private".equals(elem.getName())))
/*     */         {
/*     */           
/* 199 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 203 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean hasCacheControlParameterFrom(HttpMessage msg, String[] params) {
/* 207 */     Header[] cacheControlHeaders = msg.getHeaders("Cache-Control");
/* 208 */     for (Header header : cacheControlHeaders) {
/* 209 */       for (HeaderElement elem : header.getElements()) {
/* 210 */         for (String param : params) {
/* 211 */           if (param.equalsIgnoreCase(elem.getName())) {
/* 212 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 217 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean isExplicitlyCacheable(HttpResponse response) {
/* 221 */     if (response.getFirstHeader("Expires") != null) {
/* 222 */       return true;
/*     */     }
/* 224 */     String[] cacheableParams = { "max-age", "s-maxage", "must-revalidate", "proxy-revalidate", "public" };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     return hasCacheControlParameterFrom((HttpMessage)response, cacheableParams);
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
/*     */   public boolean isResponseCacheable(HttpRequest request, HttpResponse response) {
/* 241 */     if (requestProtocolGreaterThanAccepted(request)) {
/* 242 */       this.log.debug("Response was not cacheable.");
/* 243 */       return false;
/*     */     } 
/*     */     
/* 246 */     String[] uncacheableRequestDirectives = { "no-store" };
/* 247 */     if (hasCacheControlParameterFrom((HttpMessage)request, uncacheableRequestDirectives)) {
/* 248 */       return false;
/*     */     }
/*     */     
/* 251 */     if (request.getRequestLine().getUri().contains("?")) {
/* 252 */       if (this.neverCache1_0ResponsesWithQueryString && from1_0Origin(response)) {
/* 253 */         this.log.debug("Response was not cacheable as it had a query string.");
/* 254 */         return false;
/* 255 */       }  if (!isExplicitlyCacheable(response)) {
/* 256 */         this.log.debug("Response was not cacheable as it is missing explicit caching headers.");
/* 257 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 261 */     if (expiresHeaderLessOrEqualToDateHeaderAndNoCacheControl(response)) {
/* 262 */       return false;
/*     */     }
/*     */     
/* 265 */     if (this.sharedCache) {
/* 266 */       Header[] authNHeaders = request.getHeaders("Authorization");
/* 267 */       if (authNHeaders != null && authNHeaders.length > 0 && !hasCacheControlParameterFrom((HttpMessage)response, AUTH_CACHEABLE_PARAMS))
/*     */       {
/* 269 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 273 */     String method = request.getRequestLine().getMethod();
/* 274 */     return isResponseCacheable(method, response);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean expiresHeaderLessOrEqualToDateHeaderAndNoCacheControl(HttpResponse response) {
/* 279 */     if (response.getFirstHeader("Cache-Control") != null) {
/* 280 */       return false;
/*     */     }
/* 282 */     Header expiresHdr = response.getFirstHeader("Expires");
/* 283 */     Header dateHdr = response.getFirstHeader("Date");
/* 284 */     if (expiresHdr == null || dateHdr == null) {
/* 285 */       return false;
/*     */     }
/* 287 */     Date expires = DateUtils.parseDate(expiresHdr.getValue());
/* 288 */     Date date = DateUtils.parseDate(dateHdr.getValue());
/* 289 */     if (expires == null || date == null) {
/* 290 */       return false;
/*     */     }
/* 292 */     return (expires.equals(date) || expires.before(date));
/*     */   }
/*     */   
/*     */   private boolean from1_0Origin(HttpResponse response) {
/* 296 */     Header via = response.getFirstHeader("Via");
/* 297 */     if (via != null) {
/* 298 */       HeaderElement[] arr$ = via.getElements(); int len$ = arr$.length, i$ = 0; if (i$ < len$) { HeaderElement elt = arr$[i$];
/* 299 */         String proto = elt.toString().split("\\s")[0];
/* 300 */         if (proto.contains("/")) {
/* 301 */           return proto.equals("HTTP/1.0");
/*     */         }
/* 303 */         return proto.equals("1.0"); }
/*     */     
/*     */     } 
/*     */     
/* 307 */     return HttpVersion.HTTP_1_0.equals(response.getProtocolVersion());
/*     */   }
/*     */   
/*     */   private boolean requestProtocolGreaterThanAccepted(HttpRequest req) {
/* 311 */     return (req.getProtocolVersion().compareToVersion((ProtocolVersion)HttpVersion.HTTP_1_1) > 0);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/ResponseCachingPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */