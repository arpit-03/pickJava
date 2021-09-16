/*    */ package org.apache.http.impl.client.cache;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.apache.http.annotation.Contract;
/*    */ import org.apache.http.annotation.ThreadingBehavior;
/*    */ import org.apache.http.client.cache.InputLimit;
/*    */ import org.apache.http.client.cache.Resource;
/*    */ import org.apache.http.client.cache.ResourceFactory;
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
/*    */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*    */ public class HeapResourceFactory
/*    */   implements ResourceFactory
/*    */ {
/*    */   public Resource generate(String requestId, InputStream inStream, InputLimit limit) throws IOException {
/* 52 */     ByteArrayOutputStream outStream = new ByteArrayOutputStream();
/* 53 */     byte[] buf = new byte[2048];
/* 54 */     long total = 0L;
/*    */     int l;
/* 56 */     while ((l = inStream.read(buf)) != -1) {
/* 57 */       outStream.write(buf, 0, l);
/* 58 */       total += l;
/* 59 */       if (limit != null && total > limit.getValue()) {
/* 60 */         limit.reached();
/*    */         break;
/*    */       } 
/*    */     } 
/* 64 */     return createResource(outStream.toByteArray());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Resource copy(String requestId, Resource resource) throws IOException {
/*    */     byte[] body;
/* 72 */     if (resource instanceof HeapResource) {
/* 73 */       body = ((HeapResource)resource).getByteArray();
/*    */     } else {
/* 75 */       ByteArrayOutputStream outStream = new ByteArrayOutputStream();
/* 76 */       IOUtils.copyAndClose(resource.getInputStream(), outStream);
/* 77 */       body = outStream.toByteArray();
/*    */     } 
/* 79 */     return createResource(body);
/*    */   }
/*    */   
/*    */   Resource createResource(byte[] buf) {
/* 83 */     return new HeapResource(buf);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/HeapResourceFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */