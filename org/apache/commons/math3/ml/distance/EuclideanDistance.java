/*    */ package org.apache.commons.math3.ml.distance;
/*    */ 
/*    */ import org.apache.commons.math3.exception.DimensionMismatchException;
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
/*    */ public class EuclideanDistance
/*    */   implements DistanceMeasure
/*    */ {
/*    */   private static final long serialVersionUID = 1717556319784040040L;
/*    */   
/*    */   public double compute(double[] a, double[] b) throws DimensionMismatchException {
/* 35 */     return MathArrays.distance(a, b);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/distance/EuclideanDistance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */