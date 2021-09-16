/*      */ package org.apache.http.impl.client.cache;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.UndeclaredThrowableException;
/*      */ import java.net.URI;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ArrayBlockingQueue;
/*      */ import java.util.concurrent.ExecutorService;
/*      */ import java.util.concurrent.RejectedExecutionException;
/*      */ import java.util.concurrent.ThreadPoolExecutor;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.logging.LogFactory;
/*      */ import org.apache.http.Header;
/*      */ import org.apache.http.HeaderElement;
/*      */ import org.apache.http.HttpEntity;
/*      */ import org.apache.http.HttpHost;
/*      */ import org.apache.http.HttpMessage;
/*      */ import org.apache.http.HttpRequest;
/*      */ import org.apache.http.HttpResponse;
/*      */ import org.apache.http.HttpVersion;
/*      */ import org.apache.http.ProtocolException;
/*      */ import org.apache.http.ProtocolVersion;
/*      */ import org.apache.http.RequestLine;
/*      */ import org.apache.http.annotation.Contract;
/*      */ import org.apache.http.annotation.ThreadingBehavior;
/*      */ import org.apache.http.client.ClientProtocolException;
/*      */ import org.apache.http.client.HttpClient;
/*      */ import org.apache.http.client.ResponseHandler;
/*      */ import org.apache.http.client.cache.CacheResponseStatus;
/*      */ import org.apache.http.client.cache.HttpCacheEntry;
/*      */ import org.apache.http.client.cache.HttpCacheStorage;
/*      */ import org.apache.http.client.cache.ResourceFactory;
/*      */ import org.apache.http.client.methods.CloseableHttpResponse;
/*      */ import org.apache.http.client.methods.HttpRequestWrapper;
/*      */ import org.apache.http.client.methods.HttpUriRequest;
/*      */ import org.apache.http.conn.ClientConnectionManager;
/*      */ import org.apache.http.impl.client.DefaultHttpClient;
/*      */ import org.apache.http.impl.cookie.DateParseException;
/*      */ import org.apache.http.impl.cookie.DateUtils;
/*      */ import org.apache.http.message.BasicHttpResponse;
/*      */ import org.apache.http.params.HttpParams;
/*      */ import org.apache.http.protocol.HttpContext;
/*      */ import org.apache.http.util.Args;
/*      */ import org.apache.http.util.VersionInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Deprecated
/*      */ @Contract(threading = ThreadingBehavior.SAFE_CONDITIONAL)
/*      */ public class CachingHttpClient
/*      */   implements HttpClient
/*      */ {
/*      */   public static final String CACHE_RESPONSE_STATUS = "http.cache.response.status";
/*      */   private static final boolean SUPPORTS_RANGE_AND_CONTENT_RANGE_HEADERS = false;
/*  143 */   private final AtomicLong cacheHits = new AtomicLong();
/*  144 */   private final AtomicLong cacheMisses = new AtomicLong();
/*  145 */   private final AtomicLong cacheUpdates = new AtomicLong();
/*      */   
/*  147 */   private final Map<ProtocolVersion, String> viaHeaders = new HashMap<ProtocolVersion, String>(4);
/*      */   
/*      */   private final HttpClient backend;
/*      */   
/*      */   private final HttpCache responseCache;
/*      */   
/*      */   private final CacheValidityPolicy validityPolicy;
/*      */   
/*      */   private final ResponseCachingPolicy responseCachingPolicy;
/*      */   
/*      */   private final CachedHttpResponseGenerator responseGenerator;
/*      */   
/*      */   private final CacheableRequestPolicy cacheableRequestPolicy;
/*      */   private final CachedResponseSuitabilityChecker suitabilityChecker;
/*      */   private final ConditionalRequestBuilder conditionalRequestBuilder;
/*      */   private final long maxObjectSizeBytes;
/*      */   private final boolean sharedCache;
/*      */   private final ResponseProtocolCompliance responseCompliance;
/*      */   private final RequestProtocolCompliance requestCompliance;
/*      */   private final AsynchronousValidator asynchRevalidator;
/*  167 */   private final Log log = LogFactory.getLog(getClass());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   CachingHttpClient(HttpClient client, HttpCache cache, CacheConfig config) {
/*  174 */     Args.notNull(client, "HttpClient");
/*  175 */     Args.notNull(cache, "HttpCache");
/*  176 */     Args.notNull(config, "CacheConfig");
/*  177 */     this.maxObjectSizeBytes = config.getMaxObjectSize();
/*  178 */     this.sharedCache = config.isSharedCache();
/*  179 */     this.backend = client;
/*  180 */     this.responseCache = cache;
/*  181 */     this.validityPolicy = new CacheValidityPolicy();
/*  182 */     this.responseCachingPolicy = new ResponseCachingPolicy(this.maxObjectSizeBytes, this.sharedCache, config.isNeverCacheHTTP10ResponsesWithQuery(), config.is303CachingEnabled());
/*      */     
/*  184 */     this.responseGenerator = new CachedHttpResponseGenerator(this.validityPolicy);
/*  185 */     this.cacheableRequestPolicy = new CacheableRequestPolicy();
/*  186 */     this.suitabilityChecker = new CachedResponseSuitabilityChecker(this.validityPolicy, config);
/*  187 */     this.conditionalRequestBuilder = new ConditionalRequestBuilder();
/*      */     
/*  189 */     this.responseCompliance = new ResponseProtocolCompliance();
/*  190 */     this.requestCompliance = new RequestProtocolCompliance(config.isWeakETagOnPutDeleteAllowed());
/*      */     
/*  192 */     this.asynchRevalidator = makeAsynchronousValidator(config);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CachingHttpClient() {
/*  201 */     this((HttpClient)new DefaultHttpClient(), new BasicHttpCache(), new CacheConfig());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CachingHttpClient(CacheConfig config) {
/*  213 */     this((HttpClient)new DefaultHttpClient(), new BasicHttpCache(config), config);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CachingHttpClient(HttpClient client) {
/*  225 */     this(client, new BasicHttpCache(), new CacheConfig());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CachingHttpClient(HttpClient client, CacheConfig config) {
/*  238 */     this(client, new BasicHttpCache(config), config);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CachingHttpClient(HttpClient client, ResourceFactory resourceFactory, HttpCacheStorage storage, CacheConfig config) {
/*  258 */     this(client, new BasicHttpCache(resourceFactory, storage, config), config);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CachingHttpClient(HttpClient client, HttpCacheStorage storage, CacheConfig config) {
/*  275 */     this(client, new BasicHttpCache(new HeapResourceFactory(), storage, config), config);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   CachingHttpClient(HttpClient backend, CacheValidityPolicy validityPolicy, ResponseCachingPolicy responseCachingPolicy, HttpCache responseCache, CachedHttpResponseGenerator responseGenerator, CacheableRequestPolicy cacheableRequestPolicy, CachedResponseSuitabilityChecker suitabilityChecker, ConditionalRequestBuilder conditionalRequestBuilder, ResponseProtocolCompliance responseCompliance, RequestProtocolCompliance requestCompliance) {
/*  291 */     CacheConfig config = new CacheConfig();
/*  292 */     this.maxObjectSizeBytes = config.getMaxObjectSize();
/*  293 */     this.sharedCache = config.isSharedCache();
/*  294 */     this.backend = backend;
/*  295 */     this.validityPolicy = validityPolicy;
/*  296 */     this.responseCachingPolicy = responseCachingPolicy;
/*  297 */     this.responseCache = responseCache;
/*  298 */     this.responseGenerator = responseGenerator;
/*  299 */     this.cacheableRequestPolicy = cacheableRequestPolicy;
/*  300 */     this.suitabilityChecker = suitabilityChecker;
/*  301 */     this.conditionalRequestBuilder = conditionalRequestBuilder;
/*  302 */     this.responseCompliance = responseCompliance;
/*  303 */     this.requestCompliance = requestCompliance;
/*  304 */     this.asynchRevalidator = makeAsynchronousValidator(config);
/*      */   }
/*      */ 
/*      */   
/*      */   private AsynchronousValidator makeAsynchronousValidator(CacheConfig config) {
/*  309 */     if (config.getAsynchronousWorkersMax() > 0) {
/*  310 */       return new AsynchronousValidator(this, config);
/*      */     }
/*  312 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getCacheHits() {
/*  321 */     return this.cacheHits.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getCacheMisses() {
/*  330 */     return this.cacheMisses.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getCacheUpdates() {
/*  339 */     return this.cacheUpdates.get();
/*      */   }
/*      */ 
/*      */   
/*      */   public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException {
/*  344 */     HttpContext defaultContext = null;
/*  345 */     return execute(target, request, defaultContext);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler) throws IOException {
/*  351 */     return execute(target, request, responseHandler, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException {
/*  357 */     HttpResponse resp = execute(target, request, context);
/*  358 */     return handleAndConsume(responseHandler, resp);
/*      */   }
/*      */ 
/*      */   
/*      */   public HttpResponse execute(HttpUriRequest request) throws IOException {
/*  363 */     HttpContext context = null;
/*  364 */     return execute(request, context);
/*      */   }
/*      */ 
/*      */   
/*      */   public HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException {
/*  369 */     URI uri = request.getURI();
/*  370 */     HttpHost httpHost = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
/*  371 */     return execute(httpHost, (HttpRequest)request, context);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException {
/*  377 */     return execute(request, responseHandler, (HttpContext)null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException {
/*  383 */     HttpResponse resp = execute(request, context);
/*  384 */     return handleAndConsume(responseHandler, resp);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> T handleAndConsume(ResponseHandler<? extends T> responseHandler, HttpResponse response) throws Error, IOException {
/*      */     T result;
/*      */     try {
/*  392 */       result = (T)responseHandler.handleResponse(response);
/*  393 */     } catch (Exception t) {
/*  394 */       HttpEntity httpEntity = response.getEntity();
/*      */       try {
/*  396 */         IOUtils.consume(httpEntity);
/*  397 */       } catch (Exception t2) {
/*      */ 
/*      */         
/*  400 */         this.log.warn("Error consuming content after an exception.", t2);
/*      */       } 
/*  402 */       if (t instanceof RuntimeException) {
/*  403 */         throw (RuntimeException)t;
/*      */       }
/*  405 */       if (t instanceof IOException) {
/*  406 */         throw (IOException)t;
/*      */       }
/*  408 */       throw new UndeclaredThrowableException(t);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  413 */     HttpEntity entity = response.getEntity();
/*  414 */     IOUtils.consume(entity);
/*  415 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public ClientConnectionManager getConnectionManager() {
/*  420 */     return this.backend.getConnectionManager();
/*      */   }
/*      */ 
/*      */   
/*      */   public HttpParams getParams() {
/*  425 */     return this.backend.getParams();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpResponse execute(HttpHost target, HttpRequest originalRequest, HttpContext context) throws IOException {
/*      */     HttpRequestWrapper request;
/*  433 */     if (originalRequest instanceof HttpRequestWrapper) {
/*  434 */       request = (HttpRequestWrapper)originalRequest;
/*      */     } else {
/*  436 */       request = HttpRequestWrapper.wrap(originalRequest);
/*      */     } 
/*  438 */     String via = generateViaHeader((HttpMessage)originalRequest);
/*      */ 
/*      */     
/*  441 */     setResponseStatus(context, CacheResponseStatus.CACHE_MISS);
/*      */     
/*  443 */     if (clientRequestsOurOptions((HttpRequest)request)) {
/*  444 */       setResponseStatus(context, CacheResponseStatus.CACHE_MODULE_RESPONSE);
/*  445 */       return new OptionsHttp11Response();
/*      */     } 
/*      */     
/*  448 */     HttpResponse fatalErrorResponse = getFatallyNoncompliantResponse(request, context);
/*      */     
/*  450 */     if (fatalErrorResponse != null) {
/*  451 */       return fatalErrorResponse;
/*      */     }
/*      */     
/*  454 */     this.requestCompliance.makeRequestCompliant(request);
/*  455 */     request.addHeader("Via", via);
/*      */     
/*  457 */     flushEntriesInvalidatedByRequest(target, request);
/*      */     
/*  459 */     if (!this.cacheableRequestPolicy.isServableFromCache((HttpRequest)request)) {
/*  460 */       this.log.debug("Request is not servable from cache");
/*  461 */       return callBackend(target, request, context);
/*      */     } 
/*      */     
/*  464 */     HttpCacheEntry entry = satisfyFromCache(target, request);
/*  465 */     if (entry == null) {
/*  466 */       this.log.debug("Cache miss");
/*  467 */       return handleCacheMiss(target, request, context);
/*      */     } 
/*      */     
/*  470 */     return handleCacheHit(target, request, context, entry);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private HttpResponse handleCacheHit(HttpHost target, HttpRequestWrapper request, HttpContext context, HttpCacheEntry entry) throws ClientProtocolException, IOException {
/*  476 */     recordCacheHit(target, request);
/*  477 */     HttpResponse out = null;
/*  478 */     Date now = getCurrentDate();
/*  479 */     if (this.suitabilityChecker.canCachedResponseBeUsed(target, (HttpRequest)request, entry, now)) {
/*  480 */       this.log.debug("Cache hit");
/*  481 */       out = generateCachedResponse(request, context, entry, now);
/*  482 */     } else if (!mayCallBackend(request)) {
/*  483 */       this.log.debug("Cache entry not suitable but only-if-cached requested");
/*  484 */       out = generateGatewayTimeout(context);
/*      */     } else {
/*  486 */       this.log.debug("Revalidating cache entry");
/*  487 */       return revalidateCacheEntry(target, request, context, entry, now);
/*      */     } 
/*  489 */     if (context != null) {
/*  490 */       context.setAttribute("http.target_host", target);
/*  491 */       context.setAttribute("http.request", request);
/*  492 */       context.setAttribute("http.response", out);
/*  493 */       context.setAttribute("http.request_sent", Boolean.TRUE);
/*      */     } 
/*  495 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private HttpResponse revalidateCacheEntry(HttpHost target, HttpRequestWrapper request, HttpContext context, HttpCacheEntry entry, Date now) throws ClientProtocolException {
/*      */     try {
/*  503 */       if (this.asynchRevalidator != null && !staleResponseNotAllowed(request, entry, now) && this.validityPolicy.mayReturnStaleWhileRevalidating(entry, now)) {
/*      */ 
/*      */         
/*  506 */         this.log.trace("Serving stale with asynchronous revalidation");
/*  507 */         HttpResponse resp = generateCachedResponse(request, context, entry, now);
/*      */         
/*  509 */         this.asynchRevalidator.revalidateCacheEntry(target, request, context, entry);
/*      */         
/*  511 */         return resp;
/*      */       } 
/*  513 */       return revalidateCacheEntry(target, request, context, entry);
/*  514 */     } catch (IOException ioex) {
/*  515 */       return handleRevalidationFailure(request, context, entry, now);
/*  516 */     } catch (ProtocolException e) {
/*  517 */       throw new ClientProtocolException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private HttpResponse handleCacheMiss(HttpHost target, HttpRequestWrapper request, HttpContext context) throws IOException {
/*  523 */     recordCacheMiss(target, request);
/*      */     
/*  525 */     if (!mayCallBackend(request)) {
/*  526 */       return (HttpResponse)new BasicHttpResponse((ProtocolVersion)HttpVersion.HTTP_1_1, 504, "Gateway Timeout");
/*      */     }
/*      */ 
/*      */     
/*  530 */     Map<String, Variant> variants = getExistingCacheVariants(target, request);
/*      */     
/*  532 */     if (variants != null && variants.size() > 0) {
/*  533 */       return negotiateResponseFromVariants(target, request, context, variants);
/*      */     }
/*      */     
/*  536 */     return callBackend(target, request, context);
/*      */   }
/*      */   
/*      */   private HttpCacheEntry satisfyFromCache(HttpHost target, HttpRequestWrapper request) {
/*  540 */     HttpCacheEntry entry = null;
/*      */     try {
/*  542 */       entry = this.responseCache.getCacheEntry(target, (HttpRequest)request);
/*  543 */     } catch (IOException ioe) {
/*  544 */       this.log.warn("Unable to retrieve entries from cache", ioe);
/*      */     } 
/*  546 */     return entry;
/*      */   }
/*      */ 
/*      */   
/*      */   private HttpResponse getFatallyNoncompliantResponse(HttpRequestWrapper request, HttpContext context) {
/*  551 */     HttpResponse fatalErrorResponse = null;
/*  552 */     List<RequestProtocolError> fatalError = this.requestCompliance.requestIsFatallyNonCompliant((HttpRequest)request);
/*      */     
/*  554 */     for (RequestProtocolError error : fatalError) {
/*  555 */       setResponseStatus(context, CacheResponseStatus.CACHE_MODULE_RESPONSE);
/*  556 */       fatalErrorResponse = this.requestCompliance.getErrorForRequest(error);
/*      */     } 
/*  558 */     return fatalErrorResponse;
/*      */   }
/*      */ 
/*      */   
/*      */   private Map<String, Variant> getExistingCacheVariants(HttpHost target, HttpRequestWrapper request) {
/*  563 */     Map<String, Variant> variants = null;
/*      */     try {
/*  565 */       variants = this.responseCache.getVariantCacheEntriesWithEtags(target, (HttpRequest)request);
/*  566 */     } catch (IOException ioe) {
/*  567 */       this.log.warn("Unable to retrieve variant entries from cache", ioe);
/*      */     } 
/*  569 */     return variants;
/*      */   }
/*      */   
/*      */   private void recordCacheMiss(HttpHost target, HttpRequestWrapper request) {
/*  573 */     this.cacheMisses.getAndIncrement();
/*  574 */     if (this.log.isTraceEnabled()) {
/*  575 */       RequestLine rl = request.getRequestLine();
/*  576 */       this.log.trace("Cache miss [host: " + target + "; uri: " + rl.getUri() + "]");
/*      */     } 
/*      */   }
/*      */   
/*      */   private void recordCacheHit(HttpHost target, HttpRequestWrapper request) {
/*  581 */     this.cacheHits.getAndIncrement();
/*  582 */     if (this.log.isTraceEnabled()) {
/*  583 */       RequestLine rl = request.getRequestLine();
/*  584 */       this.log.trace("Cache hit [host: " + target + "; uri: " + rl.getUri() + "]");
/*      */     } 
/*      */   }
/*      */   
/*      */   private void recordCacheUpdate(HttpContext context) {
/*  589 */     this.cacheUpdates.getAndIncrement();
/*  590 */     setResponseStatus(context, CacheResponseStatus.VALIDATED);
/*      */   }
/*      */ 
/*      */   
/*      */   private void flushEntriesInvalidatedByRequest(HttpHost target, HttpRequestWrapper request) {
/*      */     try {
/*  596 */       this.responseCache.flushInvalidatedCacheEntriesFor(target, (HttpRequest)request);
/*  597 */     } catch (IOException ioe) {
/*  598 */       this.log.warn("Unable to flush invalidated entries from cache", ioe);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private HttpResponse generateCachedResponse(HttpRequestWrapper request, HttpContext context, HttpCacheEntry entry, Date now) {
/*      */     CloseableHttpResponse closeableHttpResponse;
/*  605 */     if (request.containsHeader("If-None-Match") || request.containsHeader("If-Modified-Since")) {
/*      */       
/*  607 */       closeableHttpResponse = this.responseGenerator.generateNotModifiedResponse(entry);
/*      */     } else {
/*  609 */       closeableHttpResponse = this.responseGenerator.generateResponse(request, entry);
/*      */     } 
/*  611 */     setResponseStatus(context, CacheResponseStatus.CACHE_HIT);
/*  612 */     if (this.validityPolicy.getStalenessSecs(entry, now) > 0L) {
/*  613 */       closeableHttpResponse.addHeader("Warning", "110 localhost \"Response is stale\"");
/*      */     }
/*  615 */     return (HttpResponse)closeableHttpResponse;
/*      */   }
/*      */ 
/*      */   
/*      */   private HttpResponse handleRevalidationFailure(HttpRequestWrapper request, HttpContext context, HttpCacheEntry entry, Date now) {
/*  620 */     if (staleResponseNotAllowed(request, entry, now)) {
/*  621 */       return generateGatewayTimeout(context);
/*      */     }
/*  623 */     return unvalidatedCacheHit(request, context, entry);
/*      */   }
/*      */ 
/*      */   
/*      */   private HttpResponse generateGatewayTimeout(HttpContext context) {
/*  628 */     setResponseStatus(context, CacheResponseStatus.CACHE_MODULE_RESPONSE);
/*  629 */     return (HttpResponse)new BasicHttpResponse((ProtocolVersion)HttpVersion.HTTP_1_1, 504, "Gateway Timeout");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private HttpResponse unvalidatedCacheHit(HttpRequestWrapper request, HttpContext context, HttpCacheEntry entry) {
/*  637 */     CloseableHttpResponse closeableHttpResponse = this.responseGenerator.generateResponse(request, entry);
/*  638 */     setResponseStatus(context, CacheResponseStatus.CACHE_HIT);
/*  639 */     closeableHttpResponse.addHeader("Warning", "111 localhost \"Revalidation failed\"");
/*  640 */     return (HttpResponse)closeableHttpResponse;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean staleResponseNotAllowed(HttpRequestWrapper request, HttpCacheEntry entry, Date now) {
/*  645 */     return (this.validityPolicy.mustRevalidate(entry) || (isSharedCache() && this.validityPolicy.proxyRevalidate(entry)) || explicitFreshnessRequest(request, entry, now));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean mayCallBackend(HttpRequestWrapper request) {
/*  651 */     for (Header h : request.getHeaders("Cache-Control")) {
/*  652 */       for (HeaderElement elt : h.getElements()) {
/*  653 */         if ("only-if-cached".equals(elt.getName())) {
/*  654 */           this.log.trace("Request marked only-if-cached");
/*  655 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/*  659 */     return true;
/*      */   }
/*      */   
/*      */   private boolean explicitFreshnessRequest(HttpRequestWrapper request, HttpCacheEntry entry, Date now) {
/*  663 */     for (Header h : request.getHeaders("Cache-Control")) {
/*  664 */       for (HeaderElement elt : h.getElements()) {
/*  665 */         if ("max-stale".equals(elt.getName())) {
/*      */           try {
/*  667 */             int maxstale = Integer.parseInt(elt.getValue());
/*  668 */             long age = this.validityPolicy.getCurrentAgeSecs(entry, now);
/*  669 */             long lifetime = this.validityPolicy.getFreshnessLifetimeSecs(entry);
/*  670 */             if (age - lifetime > maxstale) {
/*  671 */               return true;
/*      */             }
/*  673 */           } catch (NumberFormatException nfe) {
/*  674 */             return true;
/*      */           } 
/*  676 */         } else if ("min-fresh".equals(elt.getName()) || "max-age".equals(elt.getName())) {
/*      */           
/*  678 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*  682 */     return false;
/*      */   }
/*      */   
/*      */   private String generateViaHeader(HttpMessage msg) {
/*      */     String value;
/*  687 */     ProtocolVersion pv = msg.getProtocolVersion();
/*  688 */     String existingEntry = this.viaHeaders.get(pv);
/*  689 */     if (existingEntry != null) {
/*  690 */       return existingEntry;
/*      */     }
/*      */     
/*  693 */     VersionInfo vi = VersionInfo.loadVersionInfo("org.apache.http.client", getClass().getClassLoader());
/*  694 */     String release = (vi != null) ? vi.getRelease() : "UNAVAILABLE";
/*      */ 
/*      */     
/*  697 */     if ("http".equalsIgnoreCase(pv.getProtocol())) {
/*  698 */       value = String.format("%d.%d localhost (Apache-HttpClient/%s (cache))", new Object[] { Integer.valueOf(pv.getMajor()), Integer.valueOf(pv.getMinor()), release });
/*      */     } else {
/*      */       
/*  701 */       value = String.format("%s/%d.%d localhost (Apache-HttpClient/%s (cache))", new Object[] { pv.getProtocol(), Integer.valueOf(pv.getMajor()), Integer.valueOf(pv.getMinor()), release });
/*      */     } 
/*      */     
/*  704 */     this.viaHeaders.put(pv, value);
/*      */     
/*  706 */     return value;
/*      */   }
/*      */   
/*      */   private void setResponseStatus(HttpContext context, CacheResponseStatus value) {
/*  710 */     if (context != null) {
/*  711 */       context.setAttribute("http.cache.response.status", value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsRangeAndContentRangeHeaders() {
/*  722 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSharedCache() {
/*  733 */     return this.sharedCache;
/*      */   }
/*      */   
/*      */   Date getCurrentDate() {
/*  737 */     return new Date();
/*      */   }
/*      */   
/*      */   boolean clientRequestsOurOptions(HttpRequest request) {
/*  741 */     RequestLine line = request.getRequestLine();
/*      */     
/*  743 */     if (!"OPTIONS".equals(line.getMethod())) {
/*  744 */       return false;
/*      */     }
/*      */     
/*  747 */     if (!"*".equals(line.getUri())) {
/*  748 */       return false;
/*      */     }
/*      */     
/*  751 */     Header h = request.getFirstHeader("Max-Forwards");
/*  752 */     if (!"0".equals((h != null) ? h.getValue() : null)) {
/*  753 */       return false;
/*      */     }
/*      */     
/*  756 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   HttpResponse callBackend(HttpHost target, HttpRequestWrapper request, HttpContext context) throws IOException {
/*  762 */     Date requestDate = getCurrentDate();
/*      */     
/*  764 */     this.log.trace("Calling the backend");
/*  765 */     HttpResponse backendResponse = this.backend.execute(target, (HttpRequest)request, context);
/*  766 */     backendResponse.addHeader("Via", generateViaHeader((HttpMessage)backendResponse));
/*  767 */     return handleBackendResponse(target, request, requestDate, getCurrentDate(), backendResponse);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean revalidationResponseIsTooOld(HttpResponse backendResponse, HttpCacheEntry cacheEntry) {
/*  774 */     Header entryDateHeader = cacheEntry.getFirstHeader("Date");
/*  775 */     Header responseDateHeader = backendResponse.getFirstHeader("Date");
/*  776 */     if (entryDateHeader != null && responseDateHeader != null) {
/*      */       try {
/*  778 */         Date entryDate = DateUtils.parseDate(entryDateHeader.getValue());
/*  779 */         Date respDate = DateUtils.parseDate(responseDateHeader.getValue());
/*  780 */         if (respDate.before(entryDate)) {
/*  781 */           return true;
/*      */         }
/*  783 */       } catch (DateParseException e) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  790 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   HttpResponse negotiateResponseFromVariants(HttpHost target, HttpRequestWrapper request, HttpContext context, Map<String, Variant> variants) throws IOException {
/*  796 */     HttpRequestWrapper conditionalRequest = this.conditionalRequestBuilder.buildConditionalRequestFromVariants(request, variants);
/*      */ 
/*      */     
/*  799 */     Date requestDate = getCurrentDate();
/*  800 */     HttpResponse backendResponse = this.backend.execute(target, (HttpRequest)conditionalRequest, context);
/*  801 */     Date responseDate = getCurrentDate();
/*      */     
/*  803 */     backendResponse.addHeader("Via", generateViaHeader((HttpMessage)backendResponse));
/*      */     
/*  805 */     if (backendResponse.getStatusLine().getStatusCode() != 304) {
/*  806 */       return handleBackendResponse(target, request, requestDate, responseDate, backendResponse);
/*      */     }
/*      */     
/*  809 */     Header resultEtagHeader = backendResponse.getFirstHeader("ETag");
/*  810 */     if (resultEtagHeader == null) {
/*  811 */       this.log.warn("304 response did not contain ETag");
/*  812 */       return callBackend(target, request, context);
/*      */     } 
/*      */     
/*  815 */     String resultEtag = resultEtagHeader.getValue();
/*  816 */     Variant matchingVariant = variants.get(resultEtag);
/*  817 */     if (matchingVariant == null) {
/*  818 */       this.log.debug("304 response did not contain ETag matching one sent in If-None-Match");
/*  819 */       return callBackend(target, request, context);
/*      */     } 
/*      */     
/*  822 */     HttpCacheEntry matchedEntry = matchingVariant.getEntry();
/*      */     
/*  824 */     if (revalidationResponseIsTooOld(backendResponse, matchedEntry)) {
/*  825 */       IOUtils.consume(backendResponse.getEntity());
/*  826 */       return retryRequestUnconditionally(target, request, context, matchedEntry);
/*      */     } 
/*      */ 
/*      */     
/*  830 */     recordCacheUpdate(context);
/*      */     
/*  832 */     HttpCacheEntry responseEntry = getUpdatedVariantEntry(target, conditionalRequest, requestDate, responseDate, backendResponse, matchingVariant, matchedEntry);
/*      */ 
/*      */ 
/*      */     
/*  836 */     CloseableHttpResponse closeableHttpResponse = this.responseGenerator.generateResponse(request, responseEntry);
/*  837 */     tryToUpdateVariantMap(target, request, matchingVariant);
/*      */     
/*  839 */     if (shouldSendNotModifiedResponse(request, responseEntry)) {
/*  840 */       return (HttpResponse)this.responseGenerator.generateNotModifiedResponse(responseEntry);
/*      */     }
/*      */     
/*  843 */     return (HttpResponse)closeableHttpResponse;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private HttpResponse retryRequestUnconditionally(HttpHost target, HttpRequestWrapper request, HttpContext context, HttpCacheEntry matchedEntry) throws IOException {
/*  849 */     HttpRequestWrapper unconditional = this.conditionalRequestBuilder.buildUnconditionalRequest(request, matchedEntry);
/*      */     
/*  851 */     return callBackend(target, unconditional, context);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private HttpCacheEntry getUpdatedVariantEntry(HttpHost target, HttpRequestWrapper conditionalRequest, Date requestDate, Date responseDate, HttpResponse backendResponse, Variant matchingVariant, HttpCacheEntry matchedEntry) {
/*  858 */     HttpCacheEntry responseEntry = matchedEntry;
/*      */     try {
/*  860 */       responseEntry = this.responseCache.updateVariantCacheEntry(target, (HttpRequest)conditionalRequest, matchedEntry, backendResponse, requestDate, responseDate, matchingVariant.getCacheKey());
/*      */     }
/*  862 */     catch (IOException ioe) {
/*  863 */       this.log.warn("Could not update cache entry", ioe);
/*      */     } 
/*  865 */     return responseEntry;
/*      */   }
/*      */ 
/*      */   
/*      */   private void tryToUpdateVariantMap(HttpHost target, HttpRequestWrapper request, Variant matchingVariant) {
/*      */     try {
/*  871 */       this.responseCache.reuseVariantEntryFor(target, (HttpRequest)request, matchingVariant);
/*  872 */     } catch (IOException ioe) {
/*  873 */       this.log.warn("Could not update cache entry to reuse variant", ioe);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean shouldSendNotModifiedResponse(HttpRequestWrapper request, HttpCacheEntry responseEntry) {
/*  879 */     return (this.suitabilityChecker.isConditional((HttpRequest)request) && this.suitabilityChecker.allConditionalsMatch((HttpRequest)request, responseEntry, new Date()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   HttpResponse revalidateCacheEntry(HttpHost target, HttpRequestWrapper request, HttpContext context, HttpCacheEntry cacheEntry) throws IOException, ProtocolException {
/*  889 */     HttpRequestWrapper conditionalRequest = this.conditionalRequestBuilder.buildConditionalRequest(request, cacheEntry);
/*      */     
/*  891 */     Date requestDate = getCurrentDate();
/*  892 */     HttpResponse backendResponse = this.backend.execute(target, (HttpRequest)conditionalRequest, context);
/*  893 */     Date responseDate = getCurrentDate();
/*      */     
/*  895 */     if (revalidationResponseIsTooOld(backendResponse, cacheEntry)) {
/*  896 */       IOUtils.consume(backendResponse.getEntity());
/*  897 */       HttpRequestWrapper httpRequestWrapper = this.conditionalRequestBuilder.buildUnconditionalRequest(request, cacheEntry);
/*      */       
/*  899 */       requestDate = getCurrentDate();
/*  900 */       backendResponse = this.backend.execute(target, (HttpRequest)httpRequestWrapper, context);
/*  901 */       responseDate = getCurrentDate();
/*      */     } 
/*      */     
/*  904 */     backendResponse.addHeader("Via", generateViaHeader((HttpMessage)backendResponse));
/*      */     
/*  906 */     int statusCode = backendResponse.getStatusLine().getStatusCode();
/*  907 */     if (statusCode == 304 || statusCode == 200) {
/*  908 */       recordCacheUpdate(context);
/*      */     }
/*      */     
/*  911 */     if (statusCode == 304) {
/*  912 */       HttpCacheEntry updatedEntry = this.responseCache.updateCacheEntry(target, (HttpRequest)request, cacheEntry, backendResponse, requestDate, responseDate);
/*      */       
/*  914 */       if (this.suitabilityChecker.isConditional((HttpRequest)request) && this.suitabilityChecker.allConditionalsMatch((HttpRequest)request, updatedEntry, new Date()))
/*      */       {
/*  916 */         return (HttpResponse)this.responseGenerator.generateNotModifiedResponse(updatedEntry);
/*      */       }
/*  918 */       return (HttpResponse)this.responseGenerator.generateResponse(request, updatedEntry);
/*      */     } 
/*      */     
/*  921 */     if (staleIfErrorAppliesTo(statusCode) && !staleResponseNotAllowed(request, cacheEntry, getCurrentDate()) && this.validityPolicy.mayReturnStaleIfError((HttpRequest)request, cacheEntry, responseDate)) {
/*      */ 
/*      */       
/*  924 */       CloseableHttpResponse closeableHttpResponse = this.responseGenerator.generateResponse(request, cacheEntry);
/*  925 */       closeableHttpResponse.addHeader("Warning", "110 localhost \"Response is stale\"");
/*  926 */       HttpEntity errorBody = backendResponse.getEntity();
/*  927 */       if (errorBody != null) {
/*  928 */         IOUtils.consume(errorBody);
/*      */       }
/*  930 */       return (HttpResponse)closeableHttpResponse;
/*      */     } 
/*      */     
/*  933 */     return handleBackendResponse(target, conditionalRequest, requestDate, responseDate, backendResponse);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean staleIfErrorAppliesTo(int statusCode) {
/*  938 */     return (statusCode == 500 || statusCode == 502 || statusCode == 503 || statusCode == 504);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   HttpResponse handleBackendResponse(HttpHost target, HttpRequestWrapper request, Date requestDate, Date responseDate, HttpResponse backendResponse) throws IOException {
/*  951 */     this.log.trace("Handling Backend response");
/*  952 */     this.responseCompliance.ensureProtocolCompliance(request, backendResponse);
/*      */     
/*  954 */     boolean cacheable = this.responseCachingPolicy.isResponseCacheable((HttpRequest)request, backendResponse);
/*  955 */     this.responseCache.flushInvalidatedCacheEntriesFor(target, (HttpRequest)request, backendResponse);
/*  956 */     if (cacheable && !alreadyHaveNewerCacheEntry(target, (HttpRequest)request, backendResponse)) {
/*      */       
/*      */       try {
/*  959 */         storeRequestIfModifiedSinceFor304Response((HttpRequest)request, backendResponse);
/*  960 */         return this.responseCache.cacheAndReturnResponse(target, (HttpRequest)request, backendResponse, requestDate, responseDate);
/*      */       }
/*  962 */       catch (IOException ioe) {
/*  963 */         this.log.warn("Unable to store entries in cache", ioe);
/*      */       } 
/*      */     }
/*  966 */     if (!cacheable) {
/*      */       try {
/*  968 */         this.responseCache.flushCacheEntriesFor(target, (HttpRequest)request);
/*  969 */       } catch (IOException ioe) {
/*  970 */         this.log.warn("Unable to flush invalid cache entries", ioe);
/*      */       } 
/*      */     }
/*  973 */     return backendResponse;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void storeRequestIfModifiedSinceFor304Response(HttpRequest request, HttpResponse backendResponse) {
/*  986 */     if (backendResponse.getStatusLine().getStatusCode() == 304) {
/*  987 */       Header h = request.getFirstHeader("If-Modified-Since");
/*  988 */       if (h != null) {
/*  989 */         backendResponse.addHeader("Last-Modified", h.getValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean alreadyHaveNewerCacheEntry(HttpHost target, HttpRequest request, HttpResponse backendResponse) {
/*  996 */     HttpCacheEntry existing = null;
/*      */     try {
/*  998 */       existing = this.responseCache.getCacheEntry(target, request);
/*  999 */     } catch (IOException ioe) {}
/*      */ 
/*      */     
/* 1002 */     if (existing == null) {
/* 1003 */       return false;
/*      */     }
/* 1005 */     Header entryDateHeader = existing.getFirstHeader("Date");
/* 1006 */     if (entryDateHeader == null) {
/* 1007 */       return false;
/*      */     }
/* 1009 */     Header responseDateHeader = backendResponse.getFirstHeader("Date");
/* 1010 */     if (responseDateHeader == null) {
/* 1011 */       return false;
/*      */     }
/*      */     try {
/* 1014 */       Date entryDate = DateUtils.parseDate(entryDateHeader.getValue());
/* 1015 */       Date responseDate = DateUtils.parseDate(responseDateHeader.getValue());
/* 1016 */       return responseDate.before(entryDate);
/* 1017 */     } catch (DateParseException e) {
/*      */ 
/*      */       
/* 1020 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   static class AsynchronousValidator {
/*      */     private final CachingHttpClient cachingClient;
/*      */     private final ExecutorService executor;
/*      */     private final Set<String> queued;
/*      */     private final CacheKeyGenerator cacheKeyGenerator;
/* 1029 */     private final Log log = LogFactory.getLog(getClass());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AsynchronousValidator(CachingHttpClient cachingClient, CacheConfig config) {
/* 1045 */       this(cachingClient, new ThreadPoolExecutor(config.getAsynchronousWorkersCore(), config.getAsynchronousWorkersMax(), config.getAsynchronousWorkerIdleLifetimeSecs(), TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(config.getRevalidationQueueSize())));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     AsynchronousValidator(CachingHttpClient cachingClient, ExecutorService executor) {
/* 1063 */       this.cachingClient = cachingClient;
/* 1064 */       this.executor = executor;
/* 1065 */       this.queued = new HashSet<String>();
/* 1066 */       this.cacheKeyGenerator = new CacheKeyGenerator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized void revalidateCacheEntry(HttpHost target, HttpRequestWrapper request, HttpContext context, HttpCacheEntry entry) {
/* 1080 */       String uri = this.cacheKeyGenerator.getVariantURI(target, (HttpRequest)request, entry);
/*      */       
/* 1082 */       if (!this.queued.contains(uri)) {
/* 1083 */         CachingHttpClient.AsynchronousValidationRequest revalidationRequest = new CachingHttpClient.AsynchronousValidationRequest(this, this.cachingClient, target, request, context, entry, uri);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1088 */           this.executor.execute(revalidationRequest);
/* 1089 */           this.queued.add(uri);
/* 1090 */         } catch (RejectedExecutionException ree) {
/* 1091 */           this.log.debug("Revalidation for [" + uri + "] not scheduled: " + ree);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void markComplete(String identifier) {
/* 1104 */       this.queued.remove(identifier);
/*      */     }
/*      */     
/*      */     Set<String> getScheduledIdentifiers() {
/* 1108 */       return Collections.unmodifiableSet(this.queued);
/*      */     }
/*      */     
/*      */     ExecutorService getExecutor() {
/* 1112 */       return this.executor;
/*      */     }
/*      */   }
/*      */   
/*      */   static class AsynchronousValidationRequest
/*      */     implements Runnable {
/*      */     private final CachingHttpClient.AsynchronousValidator parent;
/*      */     private final CachingHttpClient cachingClient;
/*      */     private final HttpHost target;
/*      */     private final HttpRequestWrapper request;
/*      */     private final HttpContext context;
/*      */     private final HttpCacheEntry cacheEntry;
/*      */     private final String identifier;
/* 1125 */     private final Log log = LogFactory.getLog(getClass());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     AsynchronousValidationRequest(CachingHttpClient.AsynchronousValidator parent, CachingHttpClient cachingClient, HttpHost target, HttpRequestWrapper request, HttpContext context, HttpCacheEntry cacheEntry, String identifier) {
/* 1143 */       this.parent = parent;
/* 1144 */       this.cachingClient = cachingClient;
/* 1145 */       this.target = target;
/* 1146 */       this.request = request;
/* 1147 */       this.context = context;
/* 1148 */       this.cacheEntry = cacheEntry;
/* 1149 */       this.identifier = identifier;
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() {
/*      */       try {
/* 1155 */         this.cachingClient.revalidateCacheEntry(this.target, this.request, this.context, this.cacheEntry);
/* 1156 */       } catch (IOException ioe) {
/* 1157 */         this.log.debug("Asynchronous revalidation failed due to exception: " + ioe);
/* 1158 */       } catch (ProtocolException pe) {
/* 1159 */         this.log.error("ProtocolException thrown during asynchronous revalidation: " + pe);
/*      */       } finally {
/* 1161 */         this.parent.markComplete(this.identifier);
/*      */       } 
/*      */     }
/*      */     
/*      */     String getIdentifier() {
/* 1166 */       return this.identifier;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CachingHttpClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */