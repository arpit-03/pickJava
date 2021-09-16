/*    */ package org.apache.commons.math3.analysis.interpolation;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.BivariateFunction;
/*    */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*    */ import org.apache.commons.math3.exception.NoDataException;
/*    */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*    */ import org.apache.commons.math3.exception.NullArgumentException;
/*    */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PiecewiseBicubicSplineInterpolator
/*    */   implements BivariateGridInterpolator
/*    */ {
/*    */   public PiecewiseBicubicSplineInterpolatingFunction interpolate(double[] xval, double[] yval, double[][] fval) throws DimensionMismatchException, NullArgumentException, NoDataException, NonMonotonicSequenceException {
/* 43 */     if (xval == null || yval == null || fval == null || fval[0] == null)
/*    */     {
/*    */ 
/*    */       
/* 47 */       throw new NullArgumentException();
/*    */     }
/*    */     
/* 50 */     if (xval.length == 0 || yval.length == 0 || fval.length == 0)
/*    */     {
/*    */       
/* 53 */       throw new NoDataException();
/*    */     }
/*    */     
/* 56 */     MathArrays.checkOrder(xval);
/* 57 */     MathArrays.checkOrder(yval);
/*    */     
/* 59 */     return new PiecewiseBicubicSplineInterpolatingFunction(xval, yval, fval);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/PiecewiseBicubicSplineInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */