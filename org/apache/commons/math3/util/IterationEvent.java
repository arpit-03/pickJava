/*    */ package org.apache.commons.math3.util;
/*    */ 
/*    */ import java.util.EventObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IterationEvent
/*    */   extends EventObject
/*    */ {
/*    */   private static final long serialVersionUID = 20120128L;
/*    */   private final int iterations;
/*    */   
/*    */   public IterationEvent(Object source, int iterations) {
/* 42 */     super(source);
/* 43 */     this.iterations = iterations;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIterations() {
/* 53 */     return this.iterations;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/IterationEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */