/*    */ package org.apache.http.impl.nio.pool;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.http.HttpHost;
/*    */ import org.apache.http.annotation.Contract;
/*    */ import org.apache.http.annotation.ThreadingBehavior;
/*    */ import org.apache.http.nio.NHttpClientConnection;
/*    */ import org.apache.http.pool.PoolEntry;
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
/*    */ public class BasicNIOPoolEntry
/*    */   extends PoolEntry<HttpHost, NHttpClientConnection>
/*    */ {
/*    */   private volatile int socketTimeout;
/*    */   
/*    */   public BasicNIOPoolEntry(String id, HttpHost route, NHttpClientConnection conn) {
/* 51 */     super(id, route, conn);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/*    */     try {
/* 57 */       NHttpClientConnection connection = (NHttpClientConnection)getConnection();
/*    */       try {
/* 59 */         int socketTimeout = connection.getSocketTimeout();
/* 60 */         if (socketTimeout <= 0 || socketTimeout > 1000) {
/* 61 */           connection.setSocketTimeout(1000);
/*    */         }
/* 63 */         connection.close();
/* 64 */       } catch (IOException ex) {
/* 65 */         connection.shutdown();
/*    */       } 
/* 67 */     } catch (IOException ignore) {}
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isClosed() {
/* 73 */     return !((NHttpClientConnection)getConnection()).isOpen();
/*    */   }
/*    */   
/*    */   int getSocketTimeout() {
/* 77 */     return this.socketTimeout;
/*    */   }
/*    */   
/*    */   void setSocketTimeout(int socketTimeout) {
/* 81 */     this.socketTimeout = socketTimeout;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/nio/pool/BasicNIOPoolEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */