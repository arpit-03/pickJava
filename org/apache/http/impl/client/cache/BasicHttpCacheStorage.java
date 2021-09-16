/*    */ package org.apache.http.impl.client.cache;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.http.annotation.Contract;
/*    */ import org.apache.http.annotation.ThreadingBehavior;
/*    */ import org.apache.http.client.cache.HttpCacheEntry;
/*    */ import org.apache.http.client.cache.HttpCacheStorage;
/*    */ import org.apache.http.client.cache.HttpCacheUpdateCallback;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Contract(threading = ThreadingBehavior.SAFE)
/*    */ public class BasicHttpCacheStorage
/*    */   implements HttpCacheStorage
/*    */ {
/*    */   private final CacheMap entries;
/*    */   
/*    */   public BasicHttpCacheStorage(CacheConfig config) {
/* 54 */     this.entries = new CacheMap(config.getMaxCacheEntries());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void putEntry(String url, HttpCacheEntry entry) throws IOException {
/* 67 */     this.entries.put(url, entry);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized HttpCacheEntry getEntry(String url) throws IOException {
/* 79 */     return this.entries.get(url);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void removeEntry(String url) throws IOException {
/* 90 */     this.entries.remove(url);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void updateEntry(String url, HttpCacheUpdateCallback callback) throws IOException {
/* 97 */     HttpCacheEntry existingEntry = this.entries.get(url);
/* 98 */     this.entries.put(url, callback.update(existingEntry));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/BasicHttpCacheStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */