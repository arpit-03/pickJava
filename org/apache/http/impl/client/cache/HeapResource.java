/*    */ package org.apache.http.impl.client.cache;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.InputStream;
/*    */ import org.apache.http.annotation.Contract;
/*    */ import org.apache.http.annotation.ThreadingBehavior;
/*    */ import org.apache.http.client.cache.Resource;
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
/*    */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*    */ public class HeapResource
/*    */   implements Resource
/*    */ {
/*    */   private static final long serialVersionUID = -2078599905620463394L;
/*    */   private final byte[] b;
/*    */   
/*    */   public HeapResource(byte[] b) {
/* 50 */     this.b = b;
/*    */   }
/*    */   
/*    */   byte[] getByteArray() {
/* 54 */     return this.b;
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream getInputStream() {
/* 59 */     return new ByteArrayInputStream(this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public long length() {
/* 64 */     return this.b.length;
/*    */   }
/*    */   
/*    */   public void dispose() {}
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/HeapResource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */