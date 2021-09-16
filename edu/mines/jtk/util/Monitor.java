/*    */ package edu.mines.jtk.util;
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
/*    */ 
/*    */ public interface Monitor
/*    */ {
/* 47 */   public static final Monitor NULL_MONITOR = new Monitor() {
/*    */       public void initReport(double initFraction) {}
/*    */       
/*    */       public void report(double fraction) {}
/*    */       
/*    */       public boolean isCanceled() {
/* 53 */         return false;
/*    */       }
/*    */     };
/*    */   
/*    */   void initReport(double paramDouble);
/*    */   
/*    */   void report(double paramDouble);
/*    */   
/*    */   boolean isCanceled();
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Monitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */