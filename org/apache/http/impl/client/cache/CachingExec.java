/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HeaderElement;
/*     */ import org.apache.http.HttpException;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.HttpMessage;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.HttpVersion;
/*     */ import org.apache.http.ProtocolException;
/*     */ import org.apache.http.ProtocolVersion;
/*     */ import org.apache.http.RequestLine;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.cache.CacheResponseStatus;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
/*     */ import org.apache.http.client.cache.HttpCacheStorage;
/*     */ import org.apache.http.client.cache.ResourceFactory;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpExecutionAware;
/*     */ import org.apache.http.client.methods.HttpRequestWrapper;
/*     */ import org.apache.http.client.protocol.HttpClientContext;
/*     */ import org.apache.http.client.utils.DateUtils;
/*     */ import org.apache.http.client.utils.URIUtils;
/*     */ import org.apache.http.conn.routing.HttpRoute;
/*     */ import org.apache.http.conn.routing.RouteInfo;
/*     */ import org.apache.http.impl.execchain.ClientExecChain;
/*     */ import org.apache.http.message.BasicHttpResponse;
/*     */ import org.apache.http.protocol.HttpContext;
/*     */ import org.apache.http.util.Args;
/*     */ import org.apache.http.util.VersionInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Contract(threading = ThreadingBehavior.SAFE_CONDITIONAL)
/*     */ public class CachingExec
/*     */   implements ClientExecChain
/*     */ {
/*     */   private static final boolean SUPPORTS_RANGE_AND_CONTENT_RANGE_HEADERS = false;
/* 108 */   private final AtomicLong cacheHits = new AtomicLong();
/* 109 */   private final AtomicLong cacheMisses = new AtomicLong();
/* 110 */   private final AtomicLong cacheUpdates = new AtomicLong();
/*     */   
/* 112 */   private final Map<ProtocolVersion, String> viaHeaders = new HashMap<ProtocolVersion, String>(4);
/*     */   
/*     */   private final CacheConfig cacheConfig;
/*     */   
/*     */   private final ClientExecChain backend;
/*     */   
/*     */   private final HttpCache responseCache;
/*     */   private final CacheValidityPolicy validityPolicy;
/*     */   private final CachedHttpResponseGenerator responseGenerator;
/*     */   private final CacheableRequestPolicy cacheableRequestPolicy;
/*     */   private final CachedResponseSuitabilityChecker suitabilityChecker;
/*     */   private final ConditionalRequestBuilder conditionalRequestBuilder;
/*     */   private final ResponseProtocolCompliance responseCompliance;
/*     */   private final RequestProtocolCompliance requestCompliance;
/*     */   private final ResponseCachingPolicy responseCachingPolicy;
/*     */   private final AsynchronousValidator asynchRevalidator;
/* 128 */   private final Log log = LogFactory.getLog(getClass());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CachingExec(ClientExecChain backend, HttpCache cache, CacheConfig config) {
/* 134 */     this(backend, cache, config, (AsynchronousValidator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CachingExec(ClientExecChain backend, HttpCache cache, CacheConfig config, AsynchronousValidator asynchRevalidator) {
/* 143 */     Args.notNull(backend, "HTTP backend");
/* 144 */     Args.notNull(cache, "HttpCache");
/* 145 */     this.cacheConfig = (config != null) ? config : CacheConfig.DEFAULT;
/* 146 */     this.backend = backend;
/* 147 */     this.responseCache = cache;
/* 148 */     this.validityPolicy = new CacheValidityPolicy();
/* 149 */     this.responseGenerator = new CachedHttpResponseGenerator(this.validityPolicy);
/* 150 */     this.cacheableRequestPolicy = new CacheableRequestPolicy();
/* 151 */     this.suitabilityChecker = new CachedResponseSuitabilityChecker(this.validityPolicy, this.cacheConfig);
/* 152 */     this.conditionalRequestBuilder = new ConditionalRequestBuilder();
/* 153 */     this.responseCompliance = new ResponseProtocolCompliance();
/* 154 */     this.requestCompliance = new RequestProtocolCompliance(this.cacheConfig.isWeakETagOnPutDeleteAllowed());
/* 155 */     this.responseCachingPolicy = new ResponseCachingPolicy(this.cacheConfig.getMaxObjectSize(), this.cacheConfig.isSharedCache(), this.cacheConfig.isNeverCacheHTTP10ResponsesWithQuery(), this.cacheConfig.is303CachingEnabled());
/*     */ 
/*     */     
/* 158 */     this.asynchRevalidator = asynchRevalidator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CachingExec(ClientExecChain backend, ResourceFactory resourceFactory, HttpCacheStorage storage, CacheConfig config) {
/* 166 */     this(backend, new BasicHttpCache(resourceFactory, storage, config), config);
/*     */   }
/*     */   
/*     */   public CachingExec(ClientExecChain backend) {
/* 170 */     this(backend, new BasicHttpCache(), CacheConfig.DEFAULT);
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
/*     */   CachingExec(ClientExecChain backend, HttpCache responseCache, CacheValidityPolicy validityPolicy, ResponseCachingPolicy responseCachingPolicy, CachedHttpResponseGenerator responseGenerator, CacheableRequestPolicy cacheableRequestPolicy, CachedResponseSuitabilityChecker suitabilityChecker, ConditionalRequestBuilder conditionalRequestBuilder, ResponseProtocolCompliance responseCompliance, RequestProtocolCompliance requestCompliance, CacheConfig config, AsynchronousValidator asynchRevalidator) {
/* 186 */     this.cacheConfig = (config != null) ? config : CacheConfig.DEFAULT;
/* 187 */     this.backend = backend;
/* 188 */     this.responseCache = responseCache;
/* 189 */     this.validityPolicy = validityPolicy;
/* 190 */     this.responseCachingPolicy = responseCachingPolicy;
/* 191 */     this.responseGenerator = responseGenerator;
/* 192 */     this.cacheableRequestPolicy = cacheableRequestPolicy;
/* 193 */     this.suitabilityChecker = suitabilityChecker;
/* 194 */     this.conditionalRequestBuilder = conditionalRequestBuilder;
/* 195 */     this.responseCompliance = responseCompliance;
/* 196 */     this.requestCompliance = requestCompliance;
/* 197 */     this.asynchRevalidator = asynchRevalidator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCacheHits() {
/* 206 */     return this.cacheHits.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCacheMisses() {
/* 215 */     return this.cacheMisses.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCacheUpdates() {
/* 224 */     return this.cacheUpdates.get();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CloseableHttpResponse execute(HttpRoute route, HttpRequestWrapper request) throws IOException, HttpException {
/* 230 */     return execute(route, request, HttpClientContext.create(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CloseableHttpResponse execute(HttpRoute route, HttpRequestWrapper request, HttpClientContext context) throws IOException, HttpException {
/* 237 */     return execute(route, request, context, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CloseableHttpResponse execute(HttpRoute route, HttpRequestWrapper request, HttpClientContext context, HttpExecutionAware execAware) throws IOException, HttpException {
/* 247 */     HttpHost target = context.getTargetHost();
/* 248 */     String via = generateViaHeader((HttpMessage)request.getOriginal());
/*     */ 
/*     */     
/* 251 */     setResponseStatus((HttpContext)context, CacheResponseStatus.CACHE_MISS);
/*     */     
/* 253 */     if (clientRequestsOurOptions((HttpRequest)request)) {
/* 254 */       setResponseStatus((HttpContext)context, CacheResponseStatus.CACHE_MODULE_RESPONSE);
/* 255 */       return Proxies.enhanceResponse(new OptionsHttp11Response());
/*     */     } 
/*     */     
/* 258 */     HttpResponse fatalErrorResponse = getFatallyNoncompliantResponse(request, (HttpContext)context);
/* 259 */     if (fatalErrorResponse != null) {
/* 260 */       return Proxies.enhanceResponse(fatalErrorResponse);
/*     */     }
/*     */     
/* 263 */     this.requestCompliance.makeRequestCompliant(request);
/* 264 */     request.addHeader("Via", via);
/*     */     
/* 266 */     if (!this.cacheableRequestPolicy.isServableFromCache((HttpRequest)request)) {
/* 267 */       this.log.debug("Request is not servable from cache");
/* 268 */       flushEntriesInvalidatedByRequest(context.getTargetHost(), request);
/* 269 */       return callBackend(route, request, context, execAware);
/*     */     } 
/*     */     
/* 272 */     HttpCacheEntry entry = satisfyFromCache(target, request);
/* 273 */     if (entry == null) {
/* 274 */       this.log.debug("Cache miss");
/* 275 */       return handleCacheMiss(route, request, context, execAware);
/*     */     } 
/* 277 */     return handleCacheHit(route, request, context, execAware, entry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CloseableHttpResponse handleCacheHit(HttpRoute route, HttpRequestWrapper request, HttpClientContext context, HttpExecutionAware execAware, HttpCacheEntry entry) throws IOException, HttpException {
/* 287 */     HttpHost target = context.getTargetHost();
/* 288 */     recordCacheHit(target, request);
/* 289 */     CloseableHttpResponse out = null;
/* 290 */     Date now = getCurrentDate();
/* 291 */     if (this.suitabilityChecker.canCachedResponseBeUsed(target, (HttpRequest)request, entry, now))
/* 292 */     { this.log.debug("Cache hit");
/* 293 */       out = generateCachedResponse(request, (HttpContext)context, entry, now); }
/* 294 */     else if (!mayCallBackend(request))
/* 295 */     { this.log.debug("Cache entry not suitable but only-if-cached requested");
/* 296 */       out = generateGatewayTimeout((HttpContext)context); }
/* 297 */     else { if (entry.getStatusCode() != 304 || this.suitabilityChecker.isConditional((HttpRequest)request)) {
/*     */         
/* 299 */         this.log.debug("Revalidating cache entry");
/* 300 */         return revalidateCacheEntry(route, request, context, execAware, entry, now);
/*     */       } 
/* 302 */       this.log.debug("Cache entry not usable; calling backend");
/* 303 */       return callBackend(route, request, context, execAware); }
/*     */     
/* 305 */     context.setAttribute("http.route", route);
/* 306 */     context.setAttribute("http.target_host", target);
/* 307 */     context.setAttribute("http.request", request);
/* 308 */     context.setAttribute("http.response", out);
/* 309 */     context.setAttribute("http.request_sent", Boolean.TRUE);
/* 310 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CloseableHttpResponse revalidateCacheEntry(HttpRoute route, HttpRequestWrapper request, HttpClientContext context, HttpExecutionAware execAware, HttpCacheEntry entry, Date now) throws HttpException {
/*     */     try {
/* 322 */       if (this.asynchRevalidator != null && !staleResponseNotAllowed(request, entry, now) && this.validityPolicy.mayReturnStaleWhileRevalidating(entry, now)) {
/*     */ 
/*     */         
/* 325 */         this.log.trace("Serving stale with asynchronous revalidation");
/* 326 */         CloseableHttpResponse resp = generateCachedResponse(request, (HttpContext)context, entry, now);
/* 327 */         this.asynchRevalidator.revalidateCacheEntry(this, route, request, context, execAware, entry);
/* 328 */         return resp;
/*     */       } 
/* 330 */       return revalidateCacheEntry(route, request, context, execAware, entry);
/* 331 */     } catch (IOException ioex) {
/* 332 */       return handleRevalidationFailure(request, (HttpContext)context, entry, now);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CloseableHttpResponse handleCacheMiss(HttpRoute route, HttpRequestWrapper request, HttpClientContext context, HttpExecutionAware execAware) throws IOException, HttpException {
/* 341 */     HttpHost target = context.getTargetHost();
/* 342 */     recordCacheMiss(target, request);
/*     */     
/* 344 */     if (!mayCallBackend(request)) {
/* 345 */       return Proxies.enhanceResponse((HttpResponse)new BasicHttpResponse((ProtocolVersion)HttpVersion.HTTP_1_1, 504, "Gateway Timeout"));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 350 */     Map<String, Variant> variants = getExistingCacheVariants(target, request);
/* 351 */     if (variants != null && !variants.isEmpty()) {
/* 352 */       return negotiateResponseFromVariants(route, request, context, execAware, variants);
/*     */     }
/*     */ 
/*     */     
/* 356 */     return callBackend(route, request, context, execAware);
/*     */   }
/*     */ 
/*     */   
/*     */   private HttpCacheEntry satisfyFromCache(HttpHost target, HttpRequestWrapper request) {
/* 361 */     HttpCacheEntry entry = null;
/*     */     try {
/* 363 */       entry = this.responseCache.getCacheEntry(target, (HttpRequest)request);
/* 364 */     } catch (IOException ioe) {
/* 365 */       this.log.warn("Unable to retrieve entries from cache", ioe);
/*     */     } 
/* 367 */     return entry;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private HttpResponse getFatallyNoncompliantResponse(HttpRequestWrapper request, HttpContext context) {
/* 373 */     HttpResponse fatalErrorResponse = null;
/* 374 */     List<RequestProtocolError> fatalError = this.requestCompliance.requestIsFatallyNonCompliant((HttpRequest)request);
/*     */     
/* 376 */     for (RequestProtocolError error : fatalError) {
/* 377 */       setResponseStatus(context, CacheResponseStatus.CACHE_MODULE_RESPONSE);
/* 378 */       fatalErrorResponse = this.requestCompliance.getErrorForRequest(error);
/*     */     } 
/* 380 */     return fatalErrorResponse;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, Variant> getExistingCacheVariants(HttpHost target, HttpRequestWrapper request) {
/* 386 */     Map<String, Variant> variants = null;
/*     */     try {
/* 388 */       variants = this.responseCache.getVariantCacheEntriesWithEtags(target, (HttpRequest)request);
/* 389 */     } catch (IOException ioe) {
/* 390 */       this.log.warn("Unable to retrieve variant entries from cache", ioe);
/*     */     } 
/* 392 */     return variants;
/*     */   }
/*     */   
/*     */   private void recordCacheMiss(HttpHost target, HttpRequestWrapper request) {
/* 396 */     this.cacheMisses.getAndIncrement();
/* 397 */     if (this.log.isTraceEnabled()) {
/* 398 */       RequestLine rl = request.getRequestLine();
/* 399 */       this.log.trace("Cache miss [host: " + target + "; uri: " + rl.getUri() + "]");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void recordCacheHit(HttpHost target, HttpRequestWrapper request) {
/* 404 */     this.cacheHits.getAndIncrement();
/* 405 */     if (this.log.isTraceEnabled()) {
/* 406 */       RequestLine rl = request.getRequestLine();
/* 407 */       this.log.trace("Cache hit [host: " + target + "; uri: " + rl.getUri() + "]");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void recordCacheUpdate(HttpContext context) {
/* 412 */     this.cacheUpdates.getAndIncrement();
/* 413 */     setResponseStatus(context, CacheResponseStatus.VALIDATED);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void flushEntriesInvalidatedByRequest(HttpHost target, HttpRequestWrapper request) {
/*     */     try {
/* 420 */       this.responseCache.flushInvalidatedCacheEntriesFor(target, (HttpRequest)request);
/* 421 */     } catch (IOException ioe) {
/* 422 */       this.log.warn("Unable to flush invalidated entries from cache", ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private CloseableHttpResponse generateCachedResponse(HttpRequestWrapper request, HttpContext context, HttpCacheEntry entry, Date now) {
/*     */     CloseableHttpResponse cachedResponse;
/* 429 */     if (request.containsHeader("If-None-Match") || request.containsHeader("If-Modified-Since")) {
/*     */       
/* 431 */       cachedResponse = this.responseGenerator.generateNotModifiedResponse(entry);
/*     */     } else {
/* 433 */       cachedResponse = this.responseGenerator.generateResponse(request, entry);
/*     */     } 
/* 435 */     setResponseStatus(context, CacheResponseStatus.CACHE_HIT);
/* 436 */     if (this.validityPolicy.getStalenessSecs(entry, now) > 0L) {
/* 437 */       cachedResponse.addHeader("Warning", "110 localhost \"Response is stale\"");
/*     */     }
/* 439 */     return cachedResponse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CloseableHttpResponse handleRevalidationFailure(HttpRequestWrapper request, HttpContext context, HttpCacheEntry entry, Date now) {
/* 447 */     if (staleResponseNotAllowed(request, entry, now)) {
/* 448 */       return generateGatewayTimeout(context);
/*     */     }
/* 450 */     return unvalidatedCacheHit(request, context, entry);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private CloseableHttpResponse generateGatewayTimeout(HttpContext context) {
/* 456 */     setResponseStatus(context, CacheResponseStatus.CACHE_MODULE_RESPONSE);
/* 457 */     return Proxies.enhanceResponse((HttpResponse)new BasicHttpResponse((ProtocolVersion)HttpVersion.HTTP_1_1, 504, "Gateway Timeout"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CloseableHttpResponse unvalidatedCacheHit(HttpRequestWrapper request, HttpContext context, HttpCacheEntry entry) {
/* 466 */     CloseableHttpResponse cachedResponse = this.responseGenerator.generateResponse(request, entry);
/* 467 */     setResponseStatus(context, CacheResponseStatus.CACHE_HIT);
/* 468 */     cachedResponse.addHeader("Warning", "111 localhost \"Revalidation failed\"");
/* 469 */     return cachedResponse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean staleResponseNotAllowed(HttpRequestWrapper request, HttpCacheEntry entry, Date now) {
/* 476 */     return (this.validityPolicy.mustRevalidate(entry) || (this.cacheConfig.isSharedCache() && this.validityPolicy.proxyRevalidate(entry)) || explicitFreshnessRequest(request, entry, now));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mayCallBackend(HttpRequestWrapper request) {
/* 482 */     for (Header h : request.getHeaders("Cache-Control")) {
/* 483 */       for (HeaderElement elt : h.getElements()) {
/* 484 */         if ("only-if-cached".equals(elt.getName())) {
/* 485 */           this.log.trace("Request marked only-if-cached");
/* 486 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/* 490 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean explicitFreshnessRequest(HttpRequestWrapper request, HttpCacheEntry entry, Date now) {
/* 497 */     for (Header h : request.getHeaders("Cache-Control")) {
/* 498 */       for (HeaderElement elt : h.getElements()) {
/* 499 */         if ("max-stale".equals(elt.getName())) {
/*     */           try {
/* 501 */             int maxstale = Integer.parseInt(elt.getValue());
/* 502 */             long age = this.validityPolicy.getCurrentAgeSecs(entry, now);
/* 503 */             long lifetime = this.validityPolicy.getFreshnessLifetimeSecs(entry);
/* 504 */             if (age - lifetime > maxstale) {
/* 505 */               return true;
/*     */             }
/* 507 */           } catch (NumberFormatException nfe) {
/* 508 */             return true;
/*     */           } 
/* 510 */         } else if ("min-fresh".equals(elt.getName()) || "max-age".equals(elt.getName())) {
/*     */           
/* 512 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 516 */     return false;
/*     */   }
/*     */   
/*     */   private String generateViaHeader(HttpMessage msg) {
/*     */     String value;
/* 521 */     ProtocolVersion pv = msg.getProtocolVersion();
/* 522 */     String existingEntry = this.viaHeaders.get(pv);
/* 523 */     if (existingEntry != null) {
/* 524 */       return existingEntry;
/*     */     }
/*     */     
/* 527 */     VersionInfo vi = VersionInfo.loadVersionInfo("org.apache.http.client", getClass().getClassLoader());
/* 528 */     String release = (vi != null) ? vi.getRelease() : "UNAVAILABLE";
/*     */ 
/*     */     
/* 531 */     int major = pv.getMajor();
/* 532 */     int minor = pv.getMinor();
/* 533 */     if ("http".equalsIgnoreCase(pv.getProtocol())) {
/* 534 */       value = String.format("%d.%d localhost (Apache-HttpClient/%s (cache))", new Object[] { Integer.valueOf(major), Integer.valueOf(minor), release });
/*     */     } else {
/*     */       
/* 537 */       value = String.format("%s/%d.%d localhost (Apache-HttpClient/%s (cache))", new Object[] { pv.getProtocol(), Integer.valueOf(major), Integer.valueOf(minor), release });
/*     */     } 
/*     */     
/* 540 */     this.viaHeaders.put(pv, value);
/*     */     
/* 542 */     return value;
/*     */   }
/*     */   
/*     */   private void setResponseStatus(HttpContext context, CacheResponseStatus value) {
/* 546 */     if (context != null) {
/* 547 */       context.setAttribute("http.cache.response.status", value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsRangeAndContentRangeHeaders() {
/* 558 */     return false;
/*     */   }
/*     */   
/*     */   Date getCurrentDate() {
/* 562 */     return new Date();
/*     */   }
/*     */   
/*     */   boolean clientRequestsOurOptions(HttpRequest request) {
/* 566 */     RequestLine line = request.getRequestLine();
/*     */     
/* 568 */     if (!"OPTIONS".equals(line.getMethod())) {
/* 569 */       return false;
/*     */     }
/*     */     
/* 572 */     if (!"*".equals(line.getUri())) {
/* 573 */       return false;
/*     */     }
/*     */     
/* 576 */     if (!"0".equals(request.getFirstHeader("Max-Forwards").getValue())) {
/* 577 */       return false;
/*     */     }
/*     */     
/* 580 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CloseableHttpResponse callBackend(HttpRoute route, HttpRequestWrapper request, HttpClientContext context, HttpExecutionAware execAware) throws IOException, HttpException {
/* 589 */     Date requestDate = getCurrentDate();
/*     */     
/* 591 */     this.log.trace("Calling the backend");
/* 592 */     CloseableHttpResponse backendResponse = this.backend.execute(route, request, context, execAware);
/*     */     try {
/* 594 */       backendResponse.addHeader("Via", generateViaHeader((HttpMessage)backendResponse));
/* 595 */       return handleBackendResponse(request, context, requestDate, getCurrentDate(), backendResponse);
/*     */     }
/* 597 */     catch (IOException ex) {
/* 598 */       backendResponse.close();
/* 599 */       throw ex;
/* 600 */     } catch (RuntimeException ex) {
/* 601 */       backendResponse.close();
/* 602 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean revalidationResponseIsTooOld(HttpResponse backendResponse, HttpCacheEntry cacheEntry) {
/* 608 */     Header entryDateHeader = cacheEntry.getFirstHeader("Date");
/* 609 */     Header responseDateHeader = backendResponse.getFirstHeader("Date");
/* 610 */     if (entryDateHeader != null && responseDateHeader != null) {
/* 611 */       Date entryDate = DateUtils.parseDate(entryDateHeader.getValue());
/* 612 */       Date respDate = DateUtils.parseDate(responseDateHeader.getValue());
/* 613 */       if (entryDate == null || respDate == null)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 618 */         return false;
/*     */       }
/* 620 */       if (respDate.before(entryDate)) {
/* 621 */         return true;
/*     */       }
/*     */     } 
/* 624 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CloseableHttpResponse negotiateResponseFromVariants(HttpRoute route, HttpRequestWrapper request, HttpClientContext context, HttpExecutionAware execAware, Map<String, Variant> variants) throws IOException, HttpException {
/* 633 */     HttpRequestWrapper conditionalRequest = this.conditionalRequestBuilder.buildConditionalRequestFromVariants(request, variants);
/*     */ 
/*     */     
/* 636 */     Date requestDate = getCurrentDate();
/* 637 */     CloseableHttpResponse backendResponse = this.backend.execute(route, conditionalRequest, context, execAware);
/*     */     
/*     */     try {
/* 640 */       Date responseDate = getCurrentDate();
/*     */       
/* 642 */       backendResponse.addHeader("Via", generateViaHeader((HttpMessage)backendResponse));
/*     */       
/* 644 */       if (backendResponse.getStatusLine().getStatusCode() != 304) {
/* 645 */         return handleBackendResponse(request, context, requestDate, responseDate, backendResponse);
/*     */       }
/*     */ 
/*     */       
/* 649 */       Header resultEtagHeader = backendResponse.getFirstHeader("ETag");
/* 650 */       if (resultEtagHeader == null) {
/* 651 */         this.log.warn("304 response did not contain ETag");
/* 652 */         IOUtils.consume(backendResponse.getEntity());
/* 653 */         backendResponse.close();
/* 654 */         return callBackend(route, request, context, execAware);
/*     */       } 
/*     */       
/* 657 */       String resultEtag = resultEtagHeader.getValue();
/* 658 */       Variant matchingVariant = variants.get(resultEtag);
/* 659 */       if (matchingVariant == null) {
/* 660 */         this.log.debug("304 response did not contain ETag matching one sent in If-None-Match");
/* 661 */         IOUtils.consume(backendResponse.getEntity());
/* 662 */         backendResponse.close();
/* 663 */         return callBackend(route, request, context, execAware);
/*     */       } 
/*     */       
/* 666 */       HttpCacheEntry matchedEntry = matchingVariant.getEntry();
/*     */       
/* 668 */       if (revalidationResponseIsTooOld((HttpResponse)backendResponse, matchedEntry)) {
/* 669 */         IOUtils.consume(backendResponse.getEntity());
/* 670 */         backendResponse.close();
/* 671 */         return retryRequestUnconditionally(route, request, context, execAware, matchedEntry);
/*     */       } 
/*     */       
/* 674 */       recordCacheUpdate((HttpContext)context);
/*     */       
/* 676 */       HttpCacheEntry responseEntry = getUpdatedVariantEntry(context.getTargetHost(), conditionalRequest, requestDate, responseDate, backendResponse, matchingVariant, matchedEntry);
/*     */ 
/*     */       
/* 679 */       backendResponse.close();
/*     */       
/* 681 */       CloseableHttpResponse resp = this.responseGenerator.generateResponse(request, responseEntry);
/* 682 */       tryToUpdateVariantMap(context.getTargetHost(), request, matchingVariant);
/*     */       
/* 684 */       if (shouldSendNotModifiedResponse(request, responseEntry)) {
/* 685 */         return this.responseGenerator.generateNotModifiedResponse(responseEntry);
/*     */       }
/* 687 */       return resp;
/* 688 */     } catch (IOException ex) {
/* 689 */       backendResponse.close();
/* 690 */       throw ex;
/* 691 */     } catch (RuntimeException ex) {
/* 692 */       backendResponse.close();
/* 693 */       throw ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CloseableHttpResponse retryRequestUnconditionally(HttpRoute route, HttpRequestWrapper request, HttpClientContext context, HttpExecutionAware execAware, HttpCacheEntry matchedEntry) throws IOException, HttpException {
/* 703 */     HttpRequestWrapper unconditional = this.conditionalRequestBuilder.buildUnconditionalRequest(request, matchedEntry);
/*     */     
/* 705 */     return callBackend(route, unconditional, context, execAware);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HttpCacheEntry getUpdatedVariantEntry(HttpHost target, HttpRequestWrapper conditionalRequest, Date requestDate, Date responseDate, CloseableHttpResponse backendResponse, Variant matchingVariant, HttpCacheEntry matchedEntry) throws IOException {
/* 716 */     HttpCacheEntry responseEntry = matchedEntry;
/*     */     try {
/* 718 */       responseEntry = this.responseCache.updateVariantCacheEntry(target, (HttpRequest)conditionalRequest, matchedEntry, (HttpResponse)backendResponse, requestDate, responseDate, matchingVariant.getCacheKey());
/*     */     }
/* 720 */     catch (IOException ioe) {
/* 721 */       this.log.warn("Could not update cache entry", ioe);
/*     */     } finally {
/* 723 */       backendResponse.close();
/*     */     } 
/* 725 */     return responseEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void tryToUpdateVariantMap(HttpHost target, HttpRequestWrapper request, Variant matchingVariant) {
/*     */     try {
/* 733 */       this.responseCache.reuseVariantEntryFor(target, (HttpRequest)request, matchingVariant);
/* 734 */     } catch (IOException ioe) {
/* 735 */       this.log.warn("Could not update cache entry to reuse variant", ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldSendNotModifiedResponse(HttpRequestWrapper request, HttpCacheEntry responseEntry) {
/* 742 */     return (this.suitabilityChecker.isConditional((HttpRequest)request) && this.suitabilityChecker.allConditionalsMatch((HttpRequest)request, responseEntry, new Date()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CloseableHttpResponse revalidateCacheEntry(HttpRoute route, HttpRequestWrapper request, HttpClientContext context, HttpExecutionAware execAware, HttpCacheEntry cacheEntry) throws IOException, HttpException {
/* 753 */     HttpRequestWrapper conditionalRequest = this.conditionalRequestBuilder.buildConditionalRequest(request, cacheEntry);
/* 754 */     URI uri = conditionalRequest.getURI();
/* 755 */     if (uri != null) {
/*     */       try {
/* 757 */         conditionalRequest.setURI(URIUtils.rewriteURIForRoute(uri, (RouteInfo)route, context.getRequestConfig().isNormalizeUri()));
/* 758 */       } catch (URISyntaxException ex) {
/* 759 */         throw new ProtocolException("Invalid URI: " + uri, ex);
/*     */       } 
/*     */     }
/*     */     
/* 763 */     Date requestDate = getCurrentDate();
/* 764 */     CloseableHttpResponse backendResponse = this.backend.execute(route, conditionalRequest, context, execAware);
/*     */     
/* 766 */     Date responseDate = getCurrentDate();
/*     */     
/* 768 */     if (revalidationResponseIsTooOld((HttpResponse)backendResponse, cacheEntry)) {
/* 769 */       backendResponse.close();
/* 770 */       HttpRequestWrapper unconditional = this.conditionalRequestBuilder.buildUnconditionalRequest(request, cacheEntry);
/*     */       
/* 772 */       requestDate = getCurrentDate();
/* 773 */       backendResponse = this.backend.execute(route, unconditional, context, execAware);
/* 774 */       responseDate = getCurrentDate();
/*     */     } 
/*     */     
/* 777 */     backendResponse.addHeader("Via", generateViaHeader((HttpMessage)backendResponse));
/*     */     
/* 779 */     int statusCode = backendResponse.getStatusLine().getStatusCode();
/* 780 */     if (statusCode == 304 || statusCode == 200) {
/* 781 */       recordCacheUpdate((HttpContext)context);
/*     */     }
/*     */     
/* 784 */     if (statusCode == 304) {
/* 785 */       HttpCacheEntry updatedEntry = this.responseCache.updateCacheEntry(context.getTargetHost(), (HttpRequest)request, cacheEntry, (HttpResponse)backendResponse, requestDate, responseDate);
/*     */ 
/*     */       
/* 788 */       if (this.suitabilityChecker.isConditional((HttpRequest)request) && this.suitabilityChecker.allConditionalsMatch((HttpRequest)request, updatedEntry, new Date()))
/*     */       {
/* 790 */         return this.responseGenerator.generateNotModifiedResponse(updatedEntry);
/*     */       }
/*     */       
/* 793 */       return this.responseGenerator.generateResponse(request, updatedEntry);
/*     */     } 
/*     */     
/* 796 */     if (staleIfErrorAppliesTo(statusCode) && !staleResponseNotAllowed(request, cacheEntry, getCurrentDate()) && this.validityPolicy.mayReturnStaleIfError((HttpRequest)request, cacheEntry, responseDate)) {
/*     */       
/*     */       try {
/*     */         
/* 800 */         CloseableHttpResponse cachedResponse = this.responseGenerator.generateResponse(request, cacheEntry);
/* 801 */         cachedResponse.addHeader("Warning", "110 localhost \"Response is stale\"");
/* 802 */         return cachedResponse;
/*     */       } finally {
/* 804 */         backendResponse.close();
/*     */       } 
/*     */     }
/* 807 */     return handleBackendResponse(conditionalRequest, context, requestDate, responseDate, backendResponse);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean staleIfErrorAppliesTo(int statusCode) {
/* 812 */     return (statusCode == 500 || statusCode == 502 || statusCode == 503 || statusCode == 504);
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
/*     */   CloseableHttpResponse handleBackendResponse(HttpRequestWrapper request, HttpClientContext context, Date requestDate, Date responseDate, CloseableHttpResponse backendResponse) throws IOException {
/* 825 */     this.log.trace("Handling Backend response");
/* 826 */     this.responseCompliance.ensureProtocolCompliance(request, (HttpResponse)backendResponse);
/*     */     
/* 828 */     HttpHost target = context.getTargetHost();
/* 829 */     boolean cacheable = this.responseCachingPolicy.isResponseCacheable((HttpRequest)request, (HttpResponse)backendResponse);
/* 830 */     this.responseCache.flushInvalidatedCacheEntriesFor(target, (HttpRequest)request, (HttpResponse)backendResponse);
/* 831 */     if (cacheable && !alreadyHaveNewerCacheEntry(target, request, (HttpResponse)backendResponse)) {
/* 832 */       storeRequestIfModifiedSinceFor304Response((HttpRequest)request, (HttpResponse)backendResponse);
/* 833 */       return this.responseCache.cacheAndReturnResponse(target, (HttpRequest)request, backendResponse, requestDate, responseDate);
/*     */     } 
/*     */     
/* 836 */     if (!cacheable) {
/*     */       try {
/* 838 */         this.responseCache.flushCacheEntriesFor(target, (HttpRequest)request);
/* 839 */       } catch (IOException ioe) {
/* 840 */         this.log.warn("Unable to flush invalid cache entries", ioe);
/*     */       } 
/*     */     }
/* 843 */     return backendResponse;
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
/*     */   private void storeRequestIfModifiedSinceFor304Response(HttpRequest request, HttpResponse backendResponse) {
/* 856 */     if (backendResponse.getStatusLine().getStatusCode() == 304) {
/* 857 */       Header h = request.getFirstHeader("If-Modified-Since");
/* 858 */       if (h != null) {
/* 859 */         backendResponse.addHeader("Last-Modified", h.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean alreadyHaveNewerCacheEntry(HttpHost target, HttpRequestWrapper request, HttpResponse backendResponse) {
/* 866 */     HttpCacheEntry existing = null;
/*     */     try {
/* 868 */       existing = this.responseCache.getCacheEntry(target, (HttpRequest)request);
/* 869 */     } catch (IOException ioe) {}
/*     */ 
/*     */     
/* 872 */     if (existing == null) {
/* 873 */       return false;
/*     */     }
/* 875 */     Header entryDateHeader = existing.getFirstHeader("Date");
/* 876 */     if (entryDateHeader == null) {
/* 877 */       return false;
/*     */     }
/* 879 */     Header responseDateHeader = backendResponse.getFirstHeader("Date");
/* 880 */     if (responseDateHeader == null) {
/* 881 */       return false;
/*     */     }
/* 883 */     Date entryDate = DateUtils.parseDate(entryDateHeader.getValue());
/* 884 */     Date responseDate = DateUtils.parseDate(responseDateHeader.getValue());
/* 885 */     if (entryDate == null || responseDate == null) {
/* 886 */       return false;
/*     */     }
/* 888 */     return responseDate.before(entryDate);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CachingExec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */