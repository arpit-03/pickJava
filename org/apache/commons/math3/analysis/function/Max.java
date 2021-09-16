/*    */ package org.apache.commons.math3.analysis.function;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.BivariateFunction;
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
/*    */ public class Max
/*    */   implements BivariateFunction
/*    */ {
/*    */   public double value(double x, double y) {
/* 31 */     return FastMath.max(x, y);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/function/Max.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */