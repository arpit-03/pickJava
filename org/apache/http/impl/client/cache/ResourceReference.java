/*    */ package org.apache.http.impl.client.cache;
/*    */ 
/*    */ import java.lang.ref.PhantomReference;
/*    */ import java.lang.ref.ReferenceQueue;
/*    */ import org.apache.http.annotation.Contract;
/*    */ import org.apache.http.annotation.ThreadingBehavior;
/*    */ import org.apache.http.client.cache.HttpCacheEntry;
/*    */ import org.apache.http.client.cache.Resource;
/*    */ import org.apache.http.util.Args;
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
/*    */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*    */ class ResourceReference
/*    */   extends PhantomReference<HttpCacheEntry>
/*    */ {
/*    */   private final Resource resource;
/*    */   
/*    */   public ResourceReference(HttpCacheEntry entry, ReferenceQueue<HttpCacheEntry> q) {
/* 44 */     super(entry, q);
/* 45 */     Args.notNull(entry.getResource(), "Resource");
/* 46 */     this.resource = entry.getResource();
/*    */   }
/*    */   
/*    */   public Resource getResource() {
/* 50 */     return this.resource;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 55 */     return this.resource.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 60 */     return this.resource.equals(obj);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/ResourceReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */