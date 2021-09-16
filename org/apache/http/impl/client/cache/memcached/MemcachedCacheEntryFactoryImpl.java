/*    */ package org.apache.http.impl.client.cache.memcached;
/*    */ 
/*    */ import org.apache.http.client.cache.HttpCacheEntry;
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
/*    */ public class MemcachedCacheEntryFactoryImpl
/*    */   implements MemcachedCacheEntryFactory
/*    */ {
/*    */   public MemcachedCacheEntry getMemcachedCacheEntry(String key, HttpCacheEntry entry) {
/* 38 */     return new MemcachedCacheEntryImpl(key, entry);
/*    */   }
/*    */ 
/*    */   
/*    */   public MemcachedCacheEntry getUnsetCacheEntry() {
/* 43 */     return new MemcachedCacheEntryImpl(null, null);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/memcached/MemcachedCacheEntryFactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */