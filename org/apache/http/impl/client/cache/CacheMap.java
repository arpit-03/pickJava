/*    */ package org.apache.http.impl.client.cache;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
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
/*    */ final class CacheMap
/*    */   extends LinkedHashMap<String, HttpCacheEntry>
/*    */ {
/*    */   private static final long serialVersionUID = -7750025207539768511L;
/*    */   private final int maxEntries;
/*    */   
/*    */   CacheMap(int maxEntries) {
/* 41 */     super(20, 0.75F, true);
/* 42 */     this.maxEntries = maxEntries;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean removeEldestEntry(Map.Entry<String, HttpCacheEntry> eldest) {
/* 47 */     return (size() > this.maxEntries);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CacheMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */