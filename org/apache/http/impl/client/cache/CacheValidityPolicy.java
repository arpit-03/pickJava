/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.util.Date;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HeaderElement;
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
/*     */ 
/*     */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*     */ class CacheValidityPolicy
/*     */ {
/*     */   public static final long MAX_AGE = 2147483648L;
/*     */   
/*     */   public long getCurrentAgeSecs(HttpCacheEntry entry, Date now) {
/*  54 */     return getCorrectedInitialAgeSecs(entry) + getResidentTimeSecs(entry, now);
/*     */   }
/*     */   
/*     */   public long getFreshnessLifetimeSecs(HttpCacheEntry entry) {
/*  58 */     long maxage = getMaxAge(entry);
/*  59 */     if (maxage > -1L) {
/*  60 */       return maxage;
/*     */     }
/*     */     
/*  63 */     Date dateValue = entry.getDate();
/*  64 */     if (dateValue == null) {
/*  65 */       return 0L;
/*     */     }
/*     */     
/*  68 */     Date expiry = getExpirationDate(entry);
/*  69 */     if (expiry == null) {
/*  70 */       return 0L;
/*     */     }
/*  72 */     long diff = expiry.getTime() - dateValue.getTime();
/*  73 */     return diff / 1000L;
/*     */   }
/*     */   
/*     */   public boolean isResponseFresh(HttpCacheEntry entry, Date now) {
/*  77 */     return (getCurrentAgeSecs(entry, now) < getFreshnessLifetimeSecs(entry));
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
/*     */   public boolean isResponseHeuristicallyFresh(HttpCacheEntry entry, Date now, float coefficient, long defaultLifetime) {
/*  95 */     return (getCurrentAgeSecs(entry, now) < getHeuristicFreshnessLifetimeSecs(entry, coefficient, defaultLifetime));
/*     */   }
/*     */ 
/*     */   
/*     */   public long getHeuristicFreshnessLifetimeSecs(HttpCacheEntry entry, float coefficient, long defaultLifetime) {
/* 100 */     Date dateValue = entry.getDate();
/* 101 */     Date lastModifiedValue = getLastModifiedValue(entry);
/*     */     
/* 103 */     if (dateValue != null && lastModifiedValue != null) {
/* 104 */       long diff = dateValue.getTime() - lastModifiedValue.getTime();
/* 105 */       if (diff < 0L) {
/* 106 */         return 0L;
/*     */       }
/* 108 */       return (long)(coefficient * (float)(diff / 1000L));
/*     */     } 
/*     */     
/* 111 */     return defaultLifetime;
/*     */   }
/*     */   
/*     */   public boolean isRevalidatable(HttpCacheEntry entry) {
/* 115 */     return (entry.getFirstHeader("ETag") != null || entry.getFirstHeader("Last-Modified") != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mustRevalidate(HttpCacheEntry entry) {
/* 120 */     return hasCacheControlDirective(entry, "must-revalidate");
/*     */   }
/*     */   
/*     */   public boolean proxyRevalidate(HttpCacheEntry entry) {
/* 124 */     return hasCacheControlDirective(entry, "proxy-revalidate");
/*     */   }
/*     */   
/*     */   public boolean mayReturnStaleWhileRevalidating(HttpCacheEntry entry, Date now) {
/* 128 */     for (Header h : entry.getHeaders("Cache-Control")) {
/* 129 */       for (HeaderElement elt : h.getElements()) {
/* 130 */         if ("stale-while-revalidate".equalsIgnoreCase(elt.getName())) {
/*     */           try {
/* 132 */             int allowedStalenessLifetime = Integer.parseInt(elt.getValue());
/* 133 */             if (getStalenessSecs(entry, now) <= allowedStalenessLifetime) {
/* 134 */               return true;
/*     */             }
/* 136 */           } catch (NumberFormatException nfe) {}
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 143 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mayReturnStaleIfError(HttpRequest request, HttpCacheEntry entry, Date now) {
/* 148 */     long stalenessSecs = getStalenessSecs(entry, now);
/* 149 */     return (mayReturnStaleIfError(request.getHeaders("Cache-Control"), stalenessSecs) || mayReturnStaleIfError(entry.getHeaders("Cache-Control"), stalenessSecs));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mayReturnStaleIfError(Header[] headers, long stalenessSecs) {
/* 156 */     boolean result = false;
/* 157 */     for (Header h : headers) {
/* 158 */       for (HeaderElement elt : h.getElements()) {
/* 159 */         if ("stale-if-error".equals(elt.getName())) {
/*     */           try {
/* 161 */             int staleIfErrorSecs = Integer.parseInt(elt.getValue());
/* 162 */             if (stalenessSecs <= staleIfErrorSecs) {
/* 163 */               result = true;
/*     */               break;
/*     */             } 
/* 166 */           } catch (NumberFormatException nfe) {}
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 172 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected Date getDateValue(HttpCacheEntry entry) {
/* 182 */     return entry.getDate();
/*     */   }
/*     */   
/*     */   protected Date getLastModifiedValue(HttpCacheEntry entry) {
/* 186 */     Header dateHdr = entry.getFirstHeader("Last-Modified");
/* 187 */     if (dateHdr == null) {
/* 188 */       return null;
/*     */     }
/* 190 */     return DateUtils.parseDate(dateHdr.getValue());
/*     */   }
/*     */   
/*     */   protected long getContentLengthValue(HttpCacheEntry entry) {
/* 194 */     Header cl = entry.getFirstHeader("Content-Length");
/* 195 */     if (cl == null) {
/* 196 */       return -1L;
/*     */     }
/*     */     
/*     */     try {
/* 200 */       return Long.parseLong(cl.getValue());
/* 201 */     } catch (NumberFormatException ex) {
/* 202 */       return -1L;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean hasContentLengthHeader(HttpCacheEntry entry) {
/* 207 */     return (null != entry.getFirstHeader("Content-Length"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contentLengthHeaderMatchesActualLength(HttpCacheEntry entry) {
/* 218 */     return (!hasContentLengthHeader(entry) || (entry.getResource() != null && getContentLengthValue(entry) == entry.getResource().length()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected long getApparentAgeSecs(HttpCacheEntry entry) {
/* 223 */     Date dateValue = entry.getDate();
/* 224 */     if (dateValue == null) {
/* 225 */       return 2147483648L;
/*     */     }
/* 227 */     long diff = entry.getResponseDate().getTime() - dateValue.getTime();
/* 228 */     if (diff < 0L) {
/* 229 */       return 0L;
/*     */     }
/* 231 */     return diff / 1000L;
/*     */   }
/*     */   
/*     */   protected long getAgeValue(HttpCacheEntry entry) {
/* 235 */     long ageValue = 0L;
/* 236 */     for (Header hdr : entry.getHeaders("Age")) {
/*     */       long l;
/*     */       try {
/* 239 */         l = Long.parseLong(hdr.getValue());
/* 240 */         if (l < 0L) {
/* 241 */           l = 2147483648L;
/*     */         }
/* 243 */       } catch (NumberFormatException nfe) {
/* 244 */         l = 2147483648L;
/*     */       } 
/* 246 */       ageValue = (l > ageValue) ? l : ageValue;
/*     */     } 
/* 248 */     return ageValue;
/*     */   }
/*     */   
/*     */   protected long getCorrectedReceivedAgeSecs(HttpCacheEntry entry) {
/* 252 */     long apparentAge = getApparentAgeSecs(entry);
/* 253 */     long ageValue = getAgeValue(entry);
/* 254 */     return (apparentAge > ageValue) ? apparentAge : ageValue;
/*     */   }
/*     */   
/*     */   protected long getResponseDelaySecs(HttpCacheEntry entry) {
/* 258 */     long diff = entry.getResponseDate().getTime() - entry.getRequestDate().getTime();
/* 259 */     return diff / 1000L;
/*     */   }
/*     */   
/*     */   protected long getCorrectedInitialAgeSecs(HttpCacheEntry entry) {
/* 263 */     return getCorrectedReceivedAgeSecs(entry) + getResponseDelaySecs(entry);
/*     */   }
/*     */   
/*     */   protected long getResidentTimeSecs(HttpCacheEntry entry, Date now) {
/* 267 */     long diff = now.getTime() - entry.getResponseDate().getTime();
/* 268 */     return diff / 1000L;
/*     */   }
/*     */   
/*     */   protected long getMaxAge(HttpCacheEntry entry) {
/* 272 */     long maxage = -1L;
/* 273 */     for (Header hdr : entry.getHeaders("Cache-Control")) {
/* 274 */       for (HeaderElement elt : hdr.getElements()) {
/* 275 */         if ("max-age".equals(elt.getName()) || "s-maxage".equals(elt.getName())) {
/*     */           
/*     */           try {
/* 278 */             long currMaxAge = Long.parseLong(elt.getValue());
/* 279 */             if (maxage == -1L || currMaxAge < maxage) {
/* 280 */               maxage = currMaxAge;
/*     */             }
/* 282 */           } catch (NumberFormatException nfe) {
/*     */             
/* 284 */             maxage = 0L;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 289 */     return maxage;
/*     */   }
/*     */   
/*     */   protected Date getExpirationDate(HttpCacheEntry entry) {
/* 293 */     Header expiresHeader = entry.getFirstHeader("Expires");
/* 294 */     if (expiresHeader == null) {
/* 295 */       return null;
/*     */     }
/* 297 */     return DateUtils.parseDate(expiresHeader.getValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCacheControlDirective(HttpCacheEntry entry, String directive) {
/* 302 */     for (Header h : entry.getHeaders("Cache-Control")) {
/* 303 */       for (HeaderElement elt : h.getElements()) {
/* 304 */         if (directive.equalsIgnoreCase(elt.getName())) {
/* 305 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 309 */     return false;
/*     */   }
/*     */   
/*     */   public long getStalenessSecs(HttpCacheEntry entry, Date now) {
/* 313 */     long age = getCurrentAgeSecs(entry, now);
/* 314 */     long freshness = getFreshnessLifetimeSecs(entry);
/* 315 */     if (age <= freshness) {
/* 316 */       return 0L;
/*     */     }
/* 318 */     return age - freshness;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CacheValidityPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */