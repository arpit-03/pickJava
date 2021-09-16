/*    */ package org.junit.runner;
/*    */ 
/*    */ import org.junit.runner.notification.RunNotifier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Runner
/*    */   implements Describable
/*    */ {
/*    */   public abstract Description getDescription();
/*    */   
/*    */   public abstract void run(RunNotifier paramRunNotifier);
/*    */   
/*    */   public int testCount() {
/* 41 */     return getDescription().testCount();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/junit/runner/Runner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */