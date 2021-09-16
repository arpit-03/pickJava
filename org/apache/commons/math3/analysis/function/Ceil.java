/*    */ package org.apache.commons.math3.analysis.function;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*    */ import org.apache.commons.math3.util.FastMath;
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
/*    */ public class Ceil
/*    */   implements UnivariateFunction
/*    */ {
/*    */   public double value(double x) {
/* 31 */     return FastMath.ceil(x);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/function/Ceil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */