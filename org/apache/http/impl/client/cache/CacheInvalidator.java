/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
/*     */ import org.apache.http.client.cache.HttpCacheInvalidator;
/*     */ import org.apache.http.client.cache.HttpCacheStorage;
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
/*     */ 
/*     */ @Contract(threading = ThreadingBehavior.IMMUTABLE_CONDITIONAL)
/*     */ class CacheInvalidator
/*     */   implements HttpCacheInvalidator
/*     */ {
/*     */   private final HttpCacheStorage storage;
/*     */   private final CacheKeyGenerator cacheKeyGenerator;
/*  61 */   private final Log log = LogFactory.getLog(getClass());
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
/*     */   public CacheInvalidator(CacheKeyGenerator uriExtractor, HttpCacheStorage storage) {
/*  73 */     this.cacheKeyGenerator = uriExtractor;
/*  74 */     this.storage = storage;
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
/*     */   public void flushInvalidatedCacheEntries(HttpHost host, HttpRequest req) {
/*  86 */     String theUri = this.cacheKeyGenerator.getURI(host, req);
/*  87 */     HttpCacheEntry parent = getEntry(theUri);
/*     */     
/*  89 */     if (requestShouldNotBeCached(req) || shouldInvalidateHeadCacheEntry(req, parent)) {
/*  90 */       this.log.debug("Invalidating parent cache entry: " + parent);
/*  91 */       if (parent != null) {
/*  92 */         for (String variantURI : parent.getVariantMap().values()) {
/*  93 */           flushEntry(variantURI);
/*     */         }
/*  95 */         flushEntry(theUri);
/*     */       } 
/*  97 */       URL reqURL = getAbsoluteURL(theUri);
/*  98 */       if (reqURL == null) {
/*  99 */         this.log.error("Couldn't transform request into valid URL");
/*     */         return;
/*     */       } 
/* 102 */       Header clHdr = req.getFirstHeader("Content-Location");
/* 103 */       if (clHdr != null) {
/* 104 */         String contentLocation = clHdr.getValue();
/* 105 */         if (!flushAbsoluteUriFromSameHost(reqURL, contentLocation)) {
/* 106 */           flushRelativeUriFromSameHost(reqURL, contentLocation);
/*     */         }
/*     */       } 
/* 109 */       Header lHdr = req.getFirstHeader("Location");
/* 110 */       if (lHdr != null) {
/* 111 */         flushAbsoluteUriFromSameHost(reqURL, lHdr.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean shouldInvalidateHeadCacheEntry(HttpRequest req, HttpCacheEntry parentCacheEntry) {
/* 117 */     return (requestIsGet(req) && isAHeadCacheEntry(parentCacheEntry));
/*     */   }
/*     */   
/*     */   private boolean requestIsGet(HttpRequest req) {
/* 121 */     return req.getRequestLine().getMethod().equals("GET");
/*     */   }
/*     */   
/*     */   private boolean isAHeadCacheEntry(HttpCacheEntry parentCacheEntry) {
/* 125 */     return (parentCacheEntry != null && parentCacheEntry.getRequestMethod().equals("HEAD"));
/*     */   }
/*     */   
/*     */   private void flushEntry(String uri) {
/*     */     try {
/* 130 */       this.storage.removeEntry(uri);
/* 131 */     } catch (IOException ioe) {
/* 132 */       this.log.warn("unable to flush cache entry", ioe);
/*     */     } 
/*     */   }
/*     */   
/*     */   private HttpCacheEntry getEntry(String theUri) {
/*     */     try {
/* 138 */       return this.storage.getEntry(theUri);
/* 139 */     } catch (IOException ioe) {
/* 140 */       this.log.warn("could not retrieve entry from storage", ioe);
/*     */       
/* 142 */       return null;
/*     */     } 
/*     */   }
/*     */   protected void flushUriIfSameHost(URL requestURL, URL targetURL) {
/* 146 */     URL canonicalTarget = getAbsoluteURL(this.cacheKeyGenerator.canonicalizeUri(targetURL.toString()));
/* 147 */     if (canonicalTarget == null) {
/*     */       return;
/*     */     }
/* 150 */     if (canonicalTarget.getAuthority().equalsIgnoreCase(requestURL.getAuthority())) {
/* 151 */       flushEntry(canonicalTarget.toString());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void flushRelativeUriFromSameHost(URL reqURL, String relUri) {
/* 156 */     URL relURL = getRelativeURL(reqURL, relUri);
/* 157 */     if (relURL == null) {
/*     */       return;
/*     */     }
/* 160 */     flushUriIfSameHost(reqURL, relURL);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean flushAbsoluteUriFromSameHost(URL reqURL, String uri) {
/* 165 */     URL absURL = getAbsoluteURL(uri);
/* 166 */     if (absURL == null) {
/* 167 */       return false;
/*     */     }
/* 169 */     flushUriIfSameHost(reqURL, absURL);
/* 170 */     return true;
/*     */   }
/*     */   
/*     */   private URL getAbsoluteURL(String uri) {
/* 174 */     URL absURL = null;
/*     */     try {
/* 176 */       absURL = new URL(uri);
/* 177 */     } catch (MalformedURLException mue) {}
/*     */ 
/*     */     
/* 180 */     return absURL;
/*     */   }
/*     */   
/*     */   private URL getRelativeURL(URL reqURL, String relUri) {
/* 184 */     URL relURL = null;
/*     */     try {
/* 186 */       relURL = new URL(reqURL, relUri);
/* 187 */     } catch (MalformedURLException e) {}
/*     */ 
/*     */     
/* 190 */     return relURL;
/*     */   }
/*     */   
/*     */   protected boolean requestShouldNotBeCached(HttpRequest req) {
/* 194 */     String method = req.getRequestLine().getMethod();
/* 195 */     return notGetOrHeadRequest(method);
/*     */   }
/*     */   
/*     */   private boolean notGetOrHeadRequest(String method) {
/* 199 */     return (!"GET".equals(method) && !"HEAD".equals(method));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushInvalidatedCacheEntries(HttpHost host, HttpRequest request, HttpResponse response) {
/* 209 */     int status = response.getStatusLine().getStatusCode();
/* 210 */     if (status < 200 || status > 299) {
/*     */       return;
/*     */     }
/* 213 */     URL reqURL = getAbsoluteURL(this.cacheKeyGenerator.getURI(host, request));
/* 214 */     if (reqURL == null) {
/*     */       return;
/*     */     }
/* 217 */     URL contentLocation = getContentLocationURL(reqURL, response);
/* 218 */     if (contentLocation != null) {
/* 219 */       flushLocationCacheEntry(reqURL, response, contentLocation);
/*     */     }
/* 221 */     URL location = getLocationURL(reqURL, response);
/* 222 */     if (location != null) {
/* 223 */       flushLocationCacheEntry(reqURL, response, location);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void flushLocationCacheEntry(URL reqURL, HttpResponse response, URL location) {
/* 229 */     String cacheKey = this.cacheKeyGenerator.canonicalizeUri(location.toString());
/* 230 */     HttpCacheEntry entry = getEntry(cacheKey);
/* 231 */     if (entry == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 238 */     if (responseDateOlderThanEntryDate(response, entry)) {
/*     */       return;
/*     */     }
/* 241 */     if (!responseAndEntryEtagsDiffer(response, entry)) {
/*     */       return;
/*     */     }
/*     */     
/* 245 */     flushUriIfSameHost(reqURL, location);
/*     */   }
/*     */   
/*     */   private URL getContentLocationURL(URL reqURL, HttpResponse response) {
/* 249 */     Header clHeader = response.getFirstHeader("Content-Location");
/* 250 */     if (clHeader == null) {
/* 251 */       return null;
/*     */     }
/* 253 */     String contentLocation = clHeader.getValue();
/* 254 */     URL canonURL = getAbsoluteURL(contentLocation);
/* 255 */     if (canonURL != null) {
/* 256 */       return canonURL;
/*     */     }
/* 258 */     return getRelativeURL(reqURL, contentLocation);
/*     */   }
/*     */   
/*     */   private URL getLocationURL(URL reqURL, HttpResponse response) {
/* 262 */     Header clHeader = response.getFirstHeader("Location");
/* 263 */     if (clHeader == null) {
/* 264 */       return null;
/*     */     }
/* 266 */     String location = clHeader.getValue();
/* 267 */     URL canonURL = getAbsoluteURL(location);
/* 268 */     if (canonURL != null) {
/* 269 */       return canonURL;
/*     */     }
/* 271 */     return getRelativeURL(reqURL, location);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean responseAndEntryEtagsDiffer(HttpResponse response, HttpCacheEntry entry) {
/* 276 */     Header entryEtag = entry.getFirstHeader("ETag");
/* 277 */     Header responseEtag = response.getFirstHeader("ETag");
/* 278 */     if (entryEtag == null || responseEtag == null) {
/* 279 */       return false;
/*     */     }
/* 281 */     return !entryEtag.getValue().equals(responseEtag.getValue());
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean responseDateOlderThanEntryDate(HttpResponse response, HttpCacheEntry entry) {
/* 286 */     Header entryDateHeader = entry.getFirstHeader("Date");
/* 287 */     Header responseDateHeader = response.getFirstHeader("Date");
/* 288 */     if (entryDateHeader == null || responseDateHeader == null)
/*     */     {
/* 290 */       return false;
/*     */     }
/* 292 */     Date entryDate = DateUtils.parseDate(entryDateHeader.getValue());
/* 293 */     Date responseDate = DateUtils.parseDate(responseDateHeader.getValue());
/* 294 */     if (entryDate == null || responseDate == null) {
/* 295 */       return false;
/*     */     }
/* 297 */     return responseDate.before(entryDate);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CacheInvalidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */