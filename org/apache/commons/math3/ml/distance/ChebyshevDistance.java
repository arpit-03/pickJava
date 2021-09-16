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
/*    */ public class ChebyshevDistance
/*    */   implements DistanceMeasure
/*    */ {
/*    */   private static final long serialVersionUID = -4694868171115238296L;
/*    */   
/*    */   public double compute(double[] a, double[] b) throws DimensionMismatchException {
/* 35 */     return MathArrays.distanceInf(a, b);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/distance/ChebyshevDistance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */