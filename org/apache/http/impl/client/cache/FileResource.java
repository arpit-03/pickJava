/*    */ package org.apache.http.impl.client.cache;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
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
/*    */ 
/*    */ @Contract(threading = ThreadingBehavior.SAFE)
/*    */ public class FileResource
/*    */   implements Resource
/*    */ {
/*    */   private static final long serialVersionUID = 4132244415919043397L;
/*    */   private final File file;
/*    */   private volatile boolean disposed;
/*    */   
/*    */   public FileResource(File file) {
/* 54 */     this.file = file;
/* 55 */     this.disposed = false;
/*    */   }
/*    */   
/*    */   synchronized File getFile() {
/* 59 */     return this.file;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized InputStream getInputStream() throws IOException {
/* 64 */     return new FileInputStream(this.file);
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized long length() {
/* 69 */     return this.file.length();
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void dispose() {
/* 74 */     if (this.disposed) {
/*    */       return;
/*    */     }
/* 77 */     this.disposed = true;
/* 78 */     this.file.delete();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/FileResource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */