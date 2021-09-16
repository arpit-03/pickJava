/*     */ package org.apache.http.impl.client.cache.memcached;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import net.spy.memcached.CASResponse;
/*     */ import net.spy.memcached.CASValue;
/*     */ import net.spy.memcached.MemcachedClient;
/*     */ import net.spy.memcached.MemcachedClientIF;
/*     */ import net.spy.memcached.OperationTimeoutException;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
/*     */ import org.apache.http.client.cache.HttpCacheEntrySerializer;
/*     */ import org.apache.http.client.cache.HttpCacheStorage;
/*     */ import org.apache.http.client.cache.HttpCacheUpdateCallback;
/*     */ import org.apache.http.client.cache.HttpCacheUpdateException;
/*     */ import org.apache.http.impl.client.cache.CacheConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MemcachedHttpCacheStorage
/*     */   implements HttpCacheStorage
/*     */ {
/*  96 */   private static final Log log = LogFactory.getLog(MemcachedHttpCacheStorage.class);
/*     */ 
/*     */   
/*     */   private final MemcachedClientIF client;
/*     */ 
/*     */   
/*     */   private final KeyHashingScheme keyHashingScheme;
/*     */ 
/*     */   
/*     */   private final MemcachedCacheEntryFactory memcachedCacheEntryFactory;
/*     */ 
/*     */   
/*     */   private final int maxUpdateRetries;
/*     */ 
/*     */   
/*     */   public MemcachedHttpCacheStorage(InetSocketAddress address) throws IOException {
/* 112 */     this((MemcachedClientIF)new MemcachedClient(new InetSocketAddress[] { address }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemcachedHttpCacheStorage(MemcachedClientIF cache) {
/* 121 */     this(cache, CacheConfig.DEFAULT, new MemcachedCacheEntryFactoryImpl(), new SHA256KeyHashingScheme());
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
/*     */   @Deprecated
/*     */   public MemcachedHttpCacheStorage(MemcachedClientIF client, CacheConfig config, HttpCacheEntrySerializer serializer) {
/* 145 */     this(client, config, new MemcachedCacheEntryFactoryImpl(), new SHA256KeyHashingScheme());
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
/*     */   public MemcachedHttpCacheStorage(MemcachedClientIF client, CacheConfig config, MemcachedCacheEntryFactory memcachedCacheEntryFactory, KeyHashingScheme keyHashingScheme) {
/* 163 */     this.client = client;
/* 164 */     this.maxUpdateRetries = config.getMaxUpdateRetries();
/* 165 */     this.memcachedCacheEntryFactory = memcachedCacheEntryFactory;
/* 166 */     this.keyHashingScheme = keyHashingScheme;
/*     */   }
/*     */ 
/*     */   
/*     */   public void putEntry(String url, HttpCacheEntry entry) throws IOException {
/* 171 */     byte[] bytes = serializeEntry(url, entry);
/* 172 */     String key = getCacheKey(url);
/* 173 */     if (key == null) {
/*     */       return;
/*     */     }
/*     */     try {
/* 177 */       this.client.set(key, 0, bytes);
/* 178 */     } catch (OperationTimeoutException ex) {
/* 179 */       throw new MemcachedOperationTimeoutException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getCacheKey(String url) {
/*     */     try {
/* 185 */       return this.keyHashingScheme.hash(url);
/* 186 */     } catch (MemcachedKeyHashingException mkhe) {
/* 187 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] serializeEntry(String url, HttpCacheEntry hce) throws IOException {
/* 192 */     MemcachedCacheEntry mce = this.memcachedCacheEntryFactory.getMemcachedCacheEntry(url, hce);
/*     */     try {
/* 194 */       return mce.toByteArray();
/* 195 */     } catch (MemcachedSerializationException mse) {
/* 196 */       IOException ioe = new IOException();
/* 197 */       ioe.initCause(mse);
/* 198 */       throw ioe;
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] convertToByteArray(Object o) {
/* 203 */     if (o == null) {
/* 204 */       return null;
/*     */     }
/* 206 */     if (!(o instanceof byte[])) {
/* 207 */       log.warn("got a non-bytearray back from memcached: " + o);
/* 208 */       return null;
/*     */     } 
/* 210 */     return (byte[])o;
/*     */   }
/*     */   
/*     */   private MemcachedCacheEntry reconstituteEntry(Object o) {
/* 214 */     byte[] bytes = convertToByteArray(o);
/* 215 */     if (bytes == null) {
/* 216 */       return null;
/*     */     }
/* 218 */     MemcachedCacheEntry mce = this.memcachedCacheEntryFactory.getUnsetCacheEntry();
/*     */     try {
/* 220 */       mce.set(bytes);
/* 221 */     } catch (MemcachedSerializationException mse) {
/* 222 */       return null;
/*     */     } 
/* 224 */     return mce;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpCacheEntry getEntry(String url) throws IOException {
/* 229 */     String key = getCacheKey(url);
/* 230 */     if (key == null) {
/* 231 */       return null;
/*     */     }
/*     */     try {
/* 234 */       MemcachedCacheEntry mce = reconstituteEntry(this.client.get(key));
/* 235 */       if (mce == null || !url.equals(mce.getStorageKey())) {
/* 236 */         return null;
/*     */       }
/* 238 */       return mce.getHttpCacheEntry();
/* 239 */     } catch (OperationTimeoutException ex) {
/* 240 */       throw new MemcachedOperationTimeoutException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeEntry(String url) throws IOException {
/* 246 */     String key = getCacheKey(url);
/* 247 */     if (key == null) {
/*     */       return;
/*     */     }
/*     */     try {
/* 251 */       this.client.delete(key);
/* 252 */     } catch (OperationTimeoutException ex) {
/* 253 */       throw new MemcachedOperationTimeoutException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateEntry(String url, HttpCacheUpdateCallback callback) throws HttpCacheUpdateException, IOException {
/* 260 */     int numRetries = 0;
/* 261 */     String key = getCacheKey(url);
/* 262 */     if (key == null) {
/* 263 */       throw new HttpCacheUpdateException("couldn't generate cache key");
/*     */     }
/*     */     do {
/*     */       try {
/* 267 */         CASValue<Object> v = this.client.gets(key);
/* 268 */         MemcachedCacheEntry mce = (v == null) ? null : reconstituteEntry(v.getValue());
/*     */         
/* 270 */         if (mce != null && !url.equals(mce.getStorageKey())) {
/* 271 */           mce = null;
/*     */         }
/* 273 */         HttpCacheEntry existingEntry = (mce == null) ? null : mce.getHttpCacheEntry();
/*     */         
/* 275 */         HttpCacheEntry updatedEntry = callback.update(existingEntry);
/*     */         
/* 277 */         if (existingEntry == null) {
/* 278 */           putEntry(url, updatedEntry);
/*     */           
/*     */           return;
/*     */         } 
/* 282 */         byte[] updatedBytes = serializeEntry(url, updatedEntry);
/* 283 */         CASResponse casResult = this.client.cas(key, v.getCas(), updatedBytes);
/*     */         
/* 285 */         if (casResult != CASResponse.OK) {
/* 286 */           numRetries++;
/*     */         } else {
/*     */           
/*     */           return;
/*     */         } 
/* 291 */       } catch (OperationTimeoutException ex) {
/* 292 */         throw new MemcachedOperationTimeoutException(ex);
/*     */       } 
/* 294 */     } while (numRetries <= this.maxUpdateRetries);
/*     */     
/* 296 */     throw new HttpCacheUpdateException("Failed to update");
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/memcached/MemcachedHttpCacheStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */