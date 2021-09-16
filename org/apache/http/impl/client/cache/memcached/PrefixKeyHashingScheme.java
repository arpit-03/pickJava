/*    */ package org.apache.http.impl.client.cache.memcached;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefixKeyHashingScheme
/*    */   implements KeyHashingScheme
/*    */ {
/*    */   private final String prefix;
/*    */   private final KeyHashingScheme backingScheme;
/*    */   
/*    */   public PrefixKeyHashingScheme(String prefix, KeyHashingScheme backingScheme) {
/* 51 */     this.prefix = prefix;
/* 52 */     this.backingScheme = backingScheme;
/*    */   }
/*    */ 
/*    */   
/*    */   public String hash(String storageKey) {
/* 57 */     return this.prefix + this.backingScheme.hash(storageKey);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/memcached/PrefixKeyHashingScheme.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */