/*    */ package Catalano.Core.Concurrent;
/*    */ 
/*    */ import java.util.concurrent.ThreadFactory;
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
/*    */ class SimpleDeamonThreadFactory
/*    */   implements ThreadFactory
/*    */ {
/*    */   public Thread newThread(Runnable r) {
/* 29 */     Thread t = new Thread(r);
/* 30 */     t.setDaemon(true);
/* 31 */     return t;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Core/Concurrent/SimpleDeamonThreadFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */