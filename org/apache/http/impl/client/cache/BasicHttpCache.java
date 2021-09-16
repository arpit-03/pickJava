/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.HttpVersion;
/*     */ import org.apache.http.ProtocolVersion;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
/*     */ import org.apache.http.client.cache.HttpCacheInvalidator;
/*     */ import org.apache.http.client.cache.HttpCacheStorage;
/*     */ import org.apache.http.client.cache.HttpCacheUpdateCallback;
/*     */ import org.apache.http.client.cache.HttpCacheUpdateException;
/*     */ import org.apache.http.client.cache.Resource;
/*     */ import org.apache.http.client.cache.ResourceFactory;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpRequestWrapper;
/*     */ import org.apache.http.entity.ByteArrayEntity;
/*     */ import org.apache.http.message.BasicHttpResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BasicHttpCache
/*     */   implements HttpCache
/*     */ {
/*  60 */   private static final Set<String> safeRequestMethods = new HashSet<String>(Arrays.asList(new String[] { "HEAD", "GET", "OPTIONS", "TRACE" }));
/*     */   
/*     */   private final CacheKeyGenerator uriExtractor;
/*     */   
/*     */   private final ResourceFactory resourceFactory;
/*     */   
/*     */   private final long maxObjectSizeBytes;
/*     */   
/*     */   private final CacheEntryUpdater cacheEntryUpdater;
/*     */   
/*     */   private final CachedHttpResponseGenerator responseGenerator;
/*     */   private final HttpCacheInvalidator cacheInvalidator;
/*     */   private final HttpCacheStorage storage;
/*  73 */   private final Log log = LogFactory.getLog(getClass());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicHttpCache(ResourceFactory resourceFactory, HttpCacheStorage storage, CacheConfig config, CacheKeyGenerator uriExtractor, HttpCacheInvalidator cacheInvalidator) {
/*  81 */     this.resourceFactory = resourceFactory;
/*  82 */     this.uriExtractor = uriExtractor;
/*  83 */     this.cacheEntryUpdater = new CacheEntryUpdater(resourceFactory);
/*  84 */     this.maxObjectSizeBytes = config.getMaxObjectSize();
/*  85 */     this.responseGenerator = new CachedHttpResponseGenerator();
/*  86 */     this.storage = storage;
/*  87 */     this.cacheInvalidator = cacheInvalidator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicHttpCache(ResourceFactory resourceFactory, HttpCacheStorage storage, CacheConfig config, CacheKeyGenerator uriExtractor) {
/*  95 */     this(resourceFactory, storage, config, uriExtractor, new CacheInvalidator(uriExtractor, storage));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicHttpCache(ResourceFactory resourceFactory, HttpCacheStorage storage, CacheConfig config) {
/* 103 */     this(resourceFactory, storage, config, new CacheKeyGenerator());
/*     */   }
/*     */   
/*     */   public BasicHttpCache(CacheConfig config) {
/* 107 */     this(new HeapResourceFactory(), new BasicHttpCacheStorage(config), config);
/*     */   }
/*     */   
/*     */   public BasicHttpCache() {
/* 111 */     this(CacheConfig.DEFAULT);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushCacheEntriesFor(HttpHost host, HttpRequest request) throws IOException {
/* 117 */     if (!safeRequestMethods.contains(request.getRequestLine().getMethod())) {
/* 118 */       String uri = this.uriExtractor.getURI(host, request);
/* 119 */       this.storage.removeEntry(uri);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void flushInvalidatedCacheEntriesFor(HttpHost host, HttpRequest request, HttpResponse response) {
/* 125 */     if (!safeRequestMethods.contains(request.getRequestLine().getMethod())) {
/* 126 */       this.cacheInvalidator.flushInvalidatedCacheEntries(host, request, response);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void storeInCache(HttpHost target, HttpRequest request, HttpCacheEntry entry) throws IOException {
/* 132 */     if (entry.hasVariants()) {
/* 133 */       storeVariantEntry(target, request, entry);
/*     */     } else {
/* 135 */       storeNonVariantEntry(target, request, entry);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void storeNonVariantEntry(HttpHost target, HttpRequest req, HttpCacheEntry entry) throws IOException {
/* 141 */     String uri = this.uriExtractor.getURI(target, req);
/* 142 */     this.storage.putEntry(uri, entry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void storeVariantEntry(HttpHost target, final HttpRequest req, final HttpCacheEntry entry) throws IOException {
/* 149 */     String parentURI = this.uriExtractor.getURI(target, req);
/* 150 */     final String variantURI = this.uriExtractor.getVariantURI(target, req, entry);
/* 151 */     this.storage.putEntry(variantURI, entry);
/*     */     
/* 153 */     HttpCacheUpdateCallback callback = new HttpCacheUpdateCallback()
/*     */       {
/*     */         public HttpCacheEntry update(HttpCacheEntry existing) throws IOException
/*     */         {
/* 157 */           return BasicHttpCache.this.doGetUpdatedParentEntry(req.getRequestLine().getUri(), existing, entry, BasicHttpCache.this.uriExtractor.getVariantKey(req, entry), variantURI);
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 166 */       this.storage.updateEntry(parentURI, callback);
/* 167 */     } catch (HttpCacheUpdateException e) {
/* 168 */       this.log.warn("Could not update key [" + parentURI + "]", (Throwable)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reuseVariantEntryFor(HttpHost target, final HttpRequest req, Variant variant) throws IOException {
/* 175 */     String parentCacheKey = this.uriExtractor.getURI(target, req);
/* 176 */     final HttpCacheEntry entry = variant.getEntry();
/* 177 */     final String variantKey = this.uriExtractor.getVariantKey(req, entry);
/* 178 */     final String variantCacheKey = variant.getCacheKey();
/*     */     
/* 180 */     HttpCacheUpdateCallback callback = new HttpCacheUpdateCallback()
/*     */       {
/*     */         public HttpCacheEntry update(HttpCacheEntry existing) throws IOException
/*     */         {
/* 184 */           return BasicHttpCache.this.doGetUpdatedParentEntry(req.getRequestLine().getUri(), existing, entry, variantKey, variantCacheKey);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     try {
/* 190 */       this.storage.updateEntry(parentCacheKey, callback);
/* 191 */     } catch (HttpCacheUpdateException e) {
/* 192 */       this.log.warn("Could not update key [" + parentCacheKey + "]", (Throwable)e);
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isIncompleteResponse(HttpResponse resp, Resource resource) {
/* 197 */     int contentLength, status = resp.getStatusLine().getStatusCode();
/* 198 */     if (status != 200 && status != 206)
/*     */     {
/* 200 */       return false;
/*     */     }
/* 202 */     Header hdr = resp.getFirstHeader("Content-Length");
/* 203 */     if (hdr == null) {
/* 204 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 208 */       contentLength = Integer.parseInt(hdr.getValue());
/* 209 */     } catch (NumberFormatException nfe) {
/* 210 */       return false;
/*     */     } 
/* 212 */     if (resource == null) {
/* 213 */       return false;
/*     */     }
/* 215 */     return (resource.length() < contentLength);
/*     */   }
/*     */ 
/*     */   
/*     */   CloseableHttpResponse generateIncompleteResponseError(HttpResponse response, Resource resource) {
/* 220 */     Integer contentLength = Integer.valueOf(response.getFirstHeader("Content-Length").getValue());
/* 221 */     BasicHttpResponse basicHttpResponse = new BasicHttpResponse((ProtocolVersion)HttpVersion.HTTP_1_1, 502, "Bad Gateway");
/*     */     
/* 223 */     basicHttpResponse.setHeader("Content-Type", "text/plain;charset=UTF-8");
/* 224 */     String msg = String.format("Received incomplete response with Content-Length %d but actual body length %d", new Object[] { contentLength, Long.valueOf(resource.length()) });
/*     */ 
/*     */     
/* 227 */     byte[] msgBytes = msg.getBytes();
/* 228 */     basicHttpResponse.setHeader("Content-Length", Integer.toString(msgBytes.length));
/* 229 */     basicHttpResponse.setEntity((HttpEntity)new ByteArrayEntity(msgBytes));
/* 230 */     return Proxies.enhanceResponse((HttpResponse)basicHttpResponse);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HttpCacheEntry doGetUpdatedParentEntry(String requestId, HttpCacheEntry existing, HttpCacheEntry entry, String variantKey, String variantCacheKey) throws IOException {
/* 239 */     HttpCacheEntry src = existing;
/* 240 */     if (src == null) {
/* 241 */       src = entry;
/*     */     }
/*     */     
/* 244 */     Resource resource = null;
/* 245 */     if (src.getResource() != null) {
/* 246 */       resource = this.resourceFactory.copy(requestId, src.getResource());
/*     */     }
/* 248 */     Map<String, String> variantMap = new HashMap<String, String>(src.getVariantMap());
/* 249 */     variantMap.put(variantKey, variantCacheKey);
/* 250 */     return new HttpCacheEntry(src.getRequestDate(), src.getResponseDate(), src.getStatusLine(), src.getAllHeaders(), resource, variantMap, src.getRequestMethod());
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
/*     */   public HttpCacheEntry updateCacheEntry(HttpHost target, HttpRequest request, HttpCacheEntry stale, HttpResponse originResponse, Date requestSent, Date responseReceived) throws IOException {
/* 264 */     HttpCacheEntry updatedEntry = this.cacheEntryUpdater.updateCacheEntry(request.getRequestLine().getUri(), stale, requestSent, responseReceived, originResponse);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 270 */     storeInCache(target, request, updatedEntry);
/* 271 */     return updatedEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpCacheEntry updateVariantCacheEntry(HttpHost target, HttpRequest request, HttpCacheEntry stale, HttpResponse originResponse, Date requestSent, Date responseReceived, String cacheKey) throws IOException {
/* 278 */     HttpCacheEntry updatedEntry = this.cacheEntryUpdater.updateCacheEntry(request.getRequestLine().getUri(), stale, requestSent, responseReceived, originResponse);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     this.storage.putEntry(cacheKey, updatedEntry);
/* 285 */     return updatedEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpResponse cacheAndReturnResponse(HttpHost host, HttpRequest request, HttpResponse originResponse, Date requestSent, Date responseReceived) throws IOException {
/* 292 */     return (HttpResponse)cacheAndReturnResponse(host, request, Proxies.enhanceResponse(originResponse), requestSent, responseReceived);
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
/*     */   public CloseableHttpResponse cacheAndReturnResponse(HttpHost host, HttpRequest request, CloseableHttpResponse originResponse, Date requestSent, Date responseReceived) throws IOException {
/* 305 */     boolean closeOriginResponse = true;
/* 306 */     SizeLimitedResponseReader responseReader = getResponseReader(request, originResponse);
/*     */     try {
/* 308 */       responseReader.readResponse();
/*     */       
/* 310 */       if (responseReader.isLimitReached()) {
/* 311 */         closeOriginResponse = false;
/* 312 */         return responseReader.getReconstructedResponse();
/*     */       } 
/*     */       
/* 315 */       Resource resource = responseReader.getResource();
/* 316 */       if (isIncompleteResponse((HttpResponse)originResponse, resource)) {
/* 317 */         return generateIncompleteResponseError((HttpResponse)originResponse, resource);
/*     */       }
/*     */       
/* 320 */       HttpCacheEntry entry = new HttpCacheEntry(requestSent, responseReceived, originResponse.getStatusLine(), originResponse.getAllHeaders(), resource, request.getRequestLine().getMethod());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 327 */       storeInCache(host, request, entry);
/* 328 */       return this.responseGenerator.generateResponse(HttpRequestWrapper.wrap(request, host), entry);
/*     */     } finally {
/* 330 */       if (closeOriginResponse) {
/* 331 */         originResponse.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SizeLimitedResponseReader getResponseReader(HttpRequest request, CloseableHttpResponse backEndResponse) {
/* 338 */     return new SizeLimitedResponseReader(this.resourceFactory, this.maxObjectSizeBytes, request, backEndResponse);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpCacheEntry getCacheEntry(HttpHost host, HttpRequest request) throws IOException {
/* 344 */     HttpCacheEntry root = this.storage.getEntry(this.uriExtractor.getURI(host, request));
/* 345 */     if (root == null) {
/* 346 */       return null;
/*     */     }
/* 348 */     if (!root.hasVariants()) {
/* 349 */       return root;
/*     */     }
/* 351 */     String variantCacheKey = (String)root.getVariantMap().get(this.uriExtractor.getVariantKey(request, root));
/* 352 */     if (variantCacheKey == null) {
/* 353 */       return null;
/*     */     }
/* 355 */     return this.storage.getEntry(variantCacheKey);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushInvalidatedCacheEntriesFor(HttpHost host, HttpRequest request) throws IOException {
/* 361 */     this.cacheInvalidator.flushInvalidatedCacheEntries(host, request);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Variant> getVariantCacheEntriesWithEtags(HttpHost host, HttpRequest request) throws IOException {
/* 367 */     Map<String, Variant> variants = new HashMap<String, Variant>();
/* 368 */     HttpCacheEntry root = this.storage.getEntry(this.uriExtractor.getURI(host, request));
/* 369 */     if (root == null || !root.hasVariants()) {
/* 370 */       return variants;
/*     */     }
/* 372 */     for (Map.Entry<String, String> variant : (Iterable<Map.Entry<String, String>>)root.getVariantMap().entrySet()) {
/* 373 */       String variantKey = variant.getKey();
/* 374 */       String variantCacheKey = variant.getValue();
/* 375 */       addVariantWithEtag(variantKey, variantCacheKey, variants);
/*     */     } 
/* 377 */     return variants;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addVariantWithEtag(String variantKey, String variantCacheKey, Map<String, Variant> variants) throws IOException {
/* 383 */     HttpCacheEntry entry = this.storage.getEntry(variantCacheKey);
/* 384 */     if (entry == null) {
/*     */       return;
/*     */     }
/* 387 */     Header etagHeader = entry.getFirstHeader("ETag");
/* 388 */     if (etagHeader == null) {
/*     */       return;
/*     */     }
/* 391 */     variants.put(etagHeader.getValue(), new Variant(variantKey, variantCacheKey, entry));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/BasicHttpCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */