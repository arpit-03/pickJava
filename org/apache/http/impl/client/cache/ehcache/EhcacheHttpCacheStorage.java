/*     */ package org.apache.http.impl.client.cache.ehcache;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import net.sf.ehcache.Ehcache;
/*     */ import net.sf.ehcache.Element;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
/*     */ import org.apache.http.client.cache.HttpCacheEntrySerializer;
/*     */ import org.apache.http.client.cache.HttpCacheStorage;
/*     */ import org.apache.http.client.cache.HttpCacheUpdateCallback;
/*     */ import org.apache.http.client.cache.HttpCacheUpdateException;
/*     */ import org.apache.http.impl.client.cache.CacheConfig;
/*     */ import org.apache.http.impl.client.cache.DefaultHttpCacheEntrySerializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EhcacheHttpCacheStorage
/*     */   implements HttpCacheStorage
/*     */ {
/*     */   private final Ehcache cache;
/*     */   private final HttpCacheEntrySerializer serializer;
/*     */   private final int maxUpdateRetries;
/*     */   
/*     */   public EhcacheHttpCacheStorage(Ehcache cache) {
/*  73 */     this(cache, CacheConfig.DEFAULT, (HttpCacheEntrySerializer)new DefaultHttpCacheEntrySerializer());
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
/*     */   public EhcacheHttpCacheStorage(Ehcache cache, CacheConfig config) {
/*  85 */     this(cache, config, (HttpCacheEntrySerializer)new DefaultHttpCacheEntrySerializer());
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
/*     */   public EhcacheHttpCacheStorage(Ehcache cache, CacheConfig config, HttpCacheEntrySerializer serializer) {
/*  99 */     this.cache = cache;
/* 100 */     this.maxUpdateRetries = config.getMaxUpdateRetries();
/* 101 */     this.serializer = serializer;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void putEntry(String key, HttpCacheEntry entry) throws IOException {
/* 106 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 107 */     this.serializer.writeTo(entry, bos);
/* 108 */     this.cache.put(new Element(key, (Serializable)bos.toByteArray()));
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized HttpCacheEntry getEntry(String key) throws IOException {
/* 113 */     Element e = this.cache.get(key);
/* 114 */     if (e == null) {
/* 115 */       return null;
/*     */     }
/*     */     
/* 118 */     byte[] data = (byte[])e.getValue();
/* 119 */     return this.serializer.readFrom(new ByteArrayInputStream(data));
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void removeEntry(String key) {
/* 124 */     this.cache.remove(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void updateEntry(String key, HttpCacheUpdateCallback callback) throws IOException, HttpCacheUpdateException {
/* 130 */     int numRetries = 0;
/*     */     while (true) {
/* 132 */       Element oldElement = this.cache.get(key);
/*     */       
/* 134 */       HttpCacheEntry existingEntry = null;
/* 135 */       if (oldElement != null) {
/* 136 */         byte[] data = (byte[])oldElement.getValue();
/* 137 */         existingEntry = this.serializer.readFrom(new ByteArrayInputStream(data));
/*     */       } 
/*     */       
/* 140 */       HttpCacheEntry updatedEntry = callback.update(existingEntry);
/*     */       
/* 142 */       if (existingEntry == null) {
/* 143 */         putEntry(key, updatedEntry);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 149 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 150 */       this.serializer.writeTo(updatedEntry, bos);
/* 151 */       Element newElement = new Element(key, (Serializable)bos.toByteArray());
/* 152 */       if (this.cache.replace(oldElement, newElement)) {
/*     */         return;
/*     */       }
/* 155 */       numRetries++;
/*     */ 
/*     */       
/* 158 */       if (numRetries > this.maxUpdateRetries)
/* 159 */         throw new HttpCacheUpdateException("Failed to update"); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/ehcache/EhcacheHttpCacheStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */