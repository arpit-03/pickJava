/*    */ package org.apache.commons.math3.ode.events;
/*    */ 
/*    */ import org.apache.commons.math3.util.FastMath;
/*    */ import org.apache.commons.math3.util.Precision;
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
/*    */ enum Transformer
/*    */ {
/* 37 */   UNINITIALIZED
/*    */   {
/*    */     protected double transformed(double g)
/*    */     {
/* 41 */       return 0.0D;
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   PLUS
/*    */   {
/*    */     protected double transformed(double g)
/*    */     {
/* 55 */       return g;
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 65 */   MINUS
/*    */   {
/*    */     protected double transformed(double g)
/*    */     {
/* 69 */       return -g;
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 79 */   MIN
/*    */   {
/*    */     protected double transformed(double g)
/*    */     {
/* 83 */       return FastMath.min(-Precision.SAFE_MIN, FastMath.min(-g, g));
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 93 */   MAX
/*    */   {
/*    */     protected double transformed(double g)
/*    */     {
/* 97 */       return FastMath.max(Precision.SAFE_MIN, FastMath.max(-g, g));
/*    */     }
/*    */   };
/*    */   
/*    */   protected abstract double transformed(double paramDouble);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/events/Transformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */