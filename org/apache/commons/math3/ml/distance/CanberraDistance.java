/*    */ package org.apache.commons.math3.ml.distance;
/*    */ 
/*    */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*    */ import org.apache.commons.math3.util.FastMath;
/*    */ import org.apache.commons.math3.util.MathArrays;
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
/*    */ public class CanberraDistance
/*    */   implements DistanceMeasure
/*    */ {
/*    */   private static final long serialVersionUID = -6972277381587032228L;
/*    */   
/*    */   public double compute(double[] a, double[] b) throws DimensionMismatchException {
/* 36 */     MathArrays.checkEqualLength(a, b);
/* 37 */     double sum = 0.0D;
/* 38 */     for (int i = 0; i < a.length; i++) {
/* 39 */       double num = FastMath.abs(a[i] - b[i]);
/* 40 */       double denom = FastMath.abs(a[i]) + FastMath.abs(b[i]);
/* 41 */       sum += (num == 0.0D && denom == 0.0D) ? 0.0D : (num / denom);
/*    */     } 
/* 43 */     return sum;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/distance/CanberraDistance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */