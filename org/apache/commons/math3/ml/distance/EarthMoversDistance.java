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
/*    */ 
/*    */ 
/*    */ public class EarthMoversDistance
/*    */   implements DistanceMeasure
/*    */ {
/*    */   private static final long serialVersionUID = -5406732779747414922L;
/*    */   
/*    */   public double compute(double[] a, double[] b) throws DimensionMismatchException {
/* 38 */     MathArrays.checkEqualLength(a, b);
/* 39 */     double lastDistance = 0.0D;
/* 40 */     double totalDistance = 0.0D;
/* 41 */     for (int i = 0; i < a.length; i++) {
/* 42 */       double currentDistance = a[i] + lastDistance - b[i];
/* 43 */       totalDistance += FastMath.abs(currentDistance);
/* 44 */       lastDistance = currentDistance;
/*    */     } 
/* 46 */     return totalDistance;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/distance/EarthMoversDistance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */