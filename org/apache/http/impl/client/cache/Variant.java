/*    */ package org.apache.http.impl.client.cache;
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
/*    */ class Variant
/*    */ {
/*    */   private final String variantKey;
/*    */   private final String cacheKey;
/*    */   private final HttpCacheEntry entry;
/*    */   
/*    */   public Variant(String variantKey, String cacheKey, HttpCacheEntry entry) {
/* 39 */     this.variantKey = variantKey;
/* 40 */     this.cacheKey = cacheKey;
/* 41 */     this.entry = entry;
/*    */   }
/*    */   
/*    */   public String getVariantKey() {
/* 45 */     return this.variantKey;
/*    */   }
/*    */   
/*    */   public String getCacheKey() {
/* 49 */     return this.cacheKey;
/*    */   }
/*    */   
/*    */   public HttpCacheEntry getEntry() {
/* 53 */     return this.entry;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/Variant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */