/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.util.Date;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HeaderElement;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
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
/*     */ class CachedResponseSuitabilityChecker
/*     */ {
/*  53 */   private final Log log = LogFactory.getLog(getClass());
/*     */   
/*     */   private final boolean sharedCache;
/*     */   
/*     */   private final boolean useHeuristicCaching;
/*     */   
/*     */   private final float heuristicCoefficient;
/*     */   private final long heuristicDefaultLifetime;
/*     */   private final CacheValidityPolicy validityStrategy;
/*     */   
/*     */   CachedResponseSuitabilityChecker(CacheValidityPolicy validityStrategy, CacheConfig config) {
/*  64 */     this.validityStrategy = validityStrategy;
/*  65 */     this.sharedCache = config.isSharedCache();
/*  66 */     this.useHeuristicCaching = config.isHeuristicCachingEnabled();
/*  67 */     this.heuristicCoefficient = config.getHeuristicCoefficient();
/*  68 */     this.heuristicDefaultLifetime = config.getHeuristicDefaultLifetime();
/*     */   }
/*     */   
/*     */   CachedResponseSuitabilityChecker(CacheConfig config) {
/*  72 */     this(new CacheValidityPolicy(), config);
/*     */   }
/*     */   
/*     */   private boolean isFreshEnough(HttpCacheEntry entry, HttpRequest request, Date now) {
/*  76 */     if (this.validityStrategy.isResponseFresh(entry, now)) {
/*  77 */       return true;
/*     */     }
/*  79 */     if (this.useHeuristicCaching && this.validityStrategy.isResponseHeuristicallyFresh(entry, now, this.heuristicCoefficient, this.heuristicDefaultLifetime))
/*     */     {
/*  81 */       return true;
/*     */     }
/*  83 */     if (originInsistsOnFreshness(entry)) {
/*  84 */       return false;
/*     */     }
/*  86 */     long maxstale = getMaxStale(request);
/*  87 */     if (maxstale == -1L) {
/*  88 */       return false;
/*     */     }
/*  90 */     return (maxstale > this.validityStrategy.getStalenessSecs(entry, now));
/*     */   }
/*     */   
/*     */   private boolean originInsistsOnFreshness(HttpCacheEntry entry) {
/*  94 */     if (this.validityStrategy.mustRevalidate(entry)) {
/*  95 */       return true;
/*     */     }
/*  97 */     if (!this.sharedCache) {
/*  98 */       return false;
/*     */     }
/* 100 */     return (this.validityStrategy.proxyRevalidate(entry) || this.validityStrategy.hasCacheControlDirective(entry, "s-maxage"));
/*     */   }
/*     */ 
/*     */   
/*     */   private long getMaxStale(HttpRequest request) {
/* 105 */     long maxstale = -1L;
/* 106 */     for (Header h : request.getHeaders("Cache-Control")) {
/* 107 */       for (HeaderElement elt : h.getElements()) {
/* 108 */         if ("max-stale".equals(elt.getName())) {
/* 109 */           if ((elt.getValue() == null || "".equals(elt.getValue().trim())) && maxstale == -1L) {
/*     */             
/* 111 */             maxstale = Long.MAX_VALUE;
/*     */           } else {
/*     */             try {
/* 114 */               long val = Long.parseLong(elt.getValue());
/* 115 */               if (val < 0L) {
/* 116 */                 val = 0L;
/*     */               }
/* 118 */               if (maxstale == -1L || val < maxstale) {
/* 119 */                 maxstale = val;
/*     */               }
/* 121 */             } catch (NumberFormatException nfe) {
/*     */               
/* 123 */               maxstale = 0L;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 129 */     return maxstale;
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
/*     */   public boolean canCachedResponseBeUsed(HttpHost host, HttpRequest request, HttpCacheEntry entry, Date now) {
/* 147 */     if (!isFreshEnough(entry, request, now)) {
/* 148 */       this.log.trace("Cache entry was not fresh enough");
/* 149 */       return false;
/*     */     } 
/*     */     
/* 152 */     if (isGet(request) && !this.validityStrategy.contentLengthHeaderMatchesActualLength(entry)) {
/* 153 */       this.log.debug("Cache entry Content-Length and header information do not match");
/* 154 */       return false;
/*     */     } 
/*     */     
/* 157 */     if (hasUnsupportedConditionalHeaders(request)) {
/* 158 */       this.log.debug("Request contained conditional headers we don't handle");
/* 159 */       return false;
/*     */     } 
/*     */     
/* 162 */     if (!isConditional(request) && entry.getStatusCode() == 304) {
/* 163 */       return false;
/*     */     }
/*     */     
/* 166 */     if (isConditional(request) && !allConditionalsMatch(request, entry, now)) {
/* 167 */       return false;
/*     */     }
/*     */     
/* 170 */     if (hasUnsupportedCacheEntryForGet(request, entry)) {
/* 171 */       this.log.debug("HEAD response caching enabled but the cache entry does not contain a request method, entity or a 204 response");
/*     */       
/* 173 */       return false;
/*     */     } 
/*     */     
/* 176 */     for (Header ccHdr : request.getHeaders("Cache-Control")) {
/* 177 */       for (HeaderElement elt : ccHdr.getElements()) {
/* 178 */         if ("no-cache".equals(elt.getName())) {
/* 179 */           this.log.trace("Response contained NO CACHE directive, cache was not suitable");
/* 180 */           return false;
/*     */         } 
/*     */         
/* 183 */         if ("no-store".equals(elt.getName())) {
/* 184 */           this.log.trace("Response contained NO STORE directive, cache was not suitable");
/* 185 */           return false;
/*     */         } 
/*     */         
/* 188 */         if ("max-age".equals(elt.getName())) {
/*     */           try {
/* 190 */             int maxage = Integer.parseInt(elt.getValue());
/* 191 */             if (this.validityStrategy.getCurrentAgeSecs(entry, now) > maxage) {
/* 192 */               this.log.trace("Response from cache was NOT suitable due to max age");
/* 193 */               return false;
/*     */             } 
/* 195 */           } catch (NumberFormatException ex) {
/*     */             
/* 197 */             this.log.debug("Response from cache was malformed" + ex.getMessage());
/* 198 */             return false;
/*     */           } 
/*     */         }
/*     */         
/* 202 */         if ("max-stale".equals(elt.getName())) {
/*     */           try {
/* 204 */             int maxstale = Integer.parseInt(elt.getValue());
/* 205 */             if (this.validityStrategy.getFreshnessLifetimeSecs(entry) > maxstale) {
/* 206 */               this.log.trace("Response from cache was not suitable due to Max stale freshness");
/* 207 */               return false;
/*     */             } 
/* 209 */           } catch (NumberFormatException ex) {
/*     */             
/* 211 */             this.log.debug("Response from cache was malformed: " + ex.getMessage());
/* 212 */             return false;
/*     */           } 
/*     */         }
/*     */         
/* 216 */         if ("min-fresh".equals(elt.getName())) {
/*     */           try {
/* 218 */             long minfresh = Long.parseLong(elt.getValue());
/* 219 */             if (minfresh < 0L) {
/* 220 */               return false;
/*     */             }
/* 222 */             long age = this.validityStrategy.getCurrentAgeSecs(entry, now);
/* 223 */             long freshness = this.validityStrategy.getFreshnessLifetimeSecs(entry);
/* 224 */             if (freshness - age < minfresh) {
/* 225 */               this.log.trace("Response from cache was not suitable due to min fresh freshness requirement");
/*     */               
/* 227 */               return false;
/*     */             } 
/* 229 */           } catch (NumberFormatException ex) {
/*     */             
/* 231 */             this.log.debug("Response from cache was malformed: " + ex.getMessage());
/* 232 */             return false;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 238 */     this.log.trace("Response from cache was suitable");
/* 239 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isGet(HttpRequest request) {
/* 243 */     return request.getRequestLine().getMethod().equals("GET");
/*     */   }
/*     */   
/*     */   private boolean entryIsNotA204Response(HttpCacheEntry entry) {
/* 247 */     return (entry.getStatusCode() != 204);
/*     */   }
/*     */   
/*     */   private boolean cacheEntryDoesNotContainMethodAndEntity(HttpCacheEntry entry) {
/* 251 */     return (entry.getRequestMethod() == null && entry.getResource() == null);
/*     */   }
/*     */   
/*     */   private boolean hasUnsupportedCacheEntryForGet(HttpRequest request, HttpCacheEntry entry) {
/* 255 */     return (isGet(request) && cacheEntryDoesNotContainMethodAndEntity(entry) && entryIsNotA204Response(entry));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConditional(HttpRequest request) {
/* 264 */     return (hasSupportedEtagValidator(request) || hasSupportedLastModifiedValidator(request));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean allConditionalsMatch(HttpRequest request, HttpCacheEntry entry, Date now) {
/* 275 */     boolean hasEtagValidator = hasSupportedEtagValidator(request);
/* 276 */     boolean hasLastModifiedValidator = hasSupportedLastModifiedValidator(request);
/*     */     
/* 278 */     boolean etagValidatorMatches = (hasEtagValidator && etagValidatorMatches(request, entry));
/* 279 */     boolean lastModifiedValidatorMatches = (hasLastModifiedValidator && lastModifiedValidatorMatches(request, entry, now));
/*     */     
/* 281 */     if (hasEtagValidator && hasLastModifiedValidator && (!etagValidatorMatches || !lastModifiedValidatorMatches))
/*     */     {
/* 283 */       return false; } 
/* 284 */     if (hasEtagValidator && !etagValidatorMatches) {
/* 285 */       return false;
/*     */     }
/*     */     
/* 288 */     if (hasLastModifiedValidator && !lastModifiedValidatorMatches) {
/* 289 */       return false;
/*     */     }
/* 291 */     return true;
/*     */   }
/*     */   
/*     */   private boolean hasUnsupportedConditionalHeaders(HttpRequest request) {
/* 295 */     return (request.getFirstHeader("If-Range") != null || request.getFirstHeader("If-Match") != null || hasValidDateField(request, "If-Unmodified-Since"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasSupportedEtagValidator(HttpRequest request) {
/* 301 */     return request.containsHeader("If-None-Match");
/*     */   }
/*     */   
/*     */   private boolean hasSupportedLastModifiedValidator(HttpRequest request) {
/* 305 */     return hasValidDateField(request, "If-Modified-Since");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean etagValidatorMatches(HttpRequest request, HttpCacheEntry entry) {
/* 315 */     Header etagHeader = entry.getFirstHeader("ETag");
/* 316 */     String etag = (etagHeader != null) ? etagHeader.getValue() : null;
/* 317 */     Header[] ifNoneMatch = request.getHeaders("If-None-Match");
/* 318 */     if (ifNoneMatch != null) {
/* 319 */       for (Header h : ifNoneMatch) {
/* 320 */         for (HeaderElement elt : h.getElements()) {
/* 321 */           String reqEtag = elt.toString();
/* 322 */           if (("*".equals(reqEtag) && etag != null) || reqEtag.equals(etag))
/*     */           {
/* 324 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/* 329 */     return false;
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
/*     */   private boolean lastModifiedValidatorMatches(HttpRequest request, HttpCacheEntry entry, Date now) {
/* 341 */     Header lastModifiedHeader = entry.getFirstHeader("Last-Modified");
/* 342 */     Date lastModified = null;
/* 343 */     if (lastModifiedHeader != null) {
/* 344 */       lastModified = DateUtils.parseDate(lastModifiedHeader.getValue());
/*     */     }
/* 346 */     if (lastModified == null) {
/* 347 */       return false;
/*     */     }
/*     */     
/* 350 */     for (Header h : request.getHeaders("If-Modified-Since")) {
/* 351 */       Date ifModifiedSince = DateUtils.parseDate(h.getValue());
/* 352 */       if (ifModifiedSince != null && (
/* 353 */         ifModifiedSince.after(now) || lastModified.after(ifModifiedSince))) {
/* 354 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 358 */     return true;
/*     */   }
/*     */   
/*     */   private boolean hasValidDateField(HttpRequest request, String headerName) {
/* 362 */     Header[] arr$ = request.getHeaders(headerName); int len$ = arr$.length, i$ = 0; if (i$ < len$) { Header h = arr$[i$];
/* 363 */       Date date = DateUtils.parseDate(h.getValue());
/* 364 */       return (date != null); }
/*     */     
/* 366 */     return false;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CachedResponseSuitabilityChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */