/*    */ package org.apache.commons.math3.ode.events;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface EventHandler
/*    */ {
/*    */   void init(double paramDouble1, double[] paramArrayOfdouble, double paramDouble2);
/*    */   
/*    */   double g(double paramDouble, double[] paramArrayOfdouble);
/*    */   
/*    */   Action eventOccurred(double paramDouble, double[] paramArrayOfdouble, boolean paramBoolean);
/*    */   
/*    */   void resetState(double paramDouble, double[] paramArrayOfdouble);
/*    */   
/*    */   public enum Action
/*    */   {
/* 60 */     STOP,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 69 */     RESET_STATE,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 79 */     RESET_DERIVATIVES,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 86 */     CONTINUE;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/events/EventHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */