/*    */ package org.apache.commons.math3.analysis.interpolation;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*    */ import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
/*    */ import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
/*    */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*    */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*    */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*    */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*    */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LinearInterpolator
/*    */   implements UnivariateInterpolator
/*    */ {
/*    */   public PolynomialSplineFunction interpolate(double[] x, double[] y) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
/* 49 */     if (x.length != y.length) {
/* 50 */       throw new DimensionMismatchException(x.length, y.length);
/*    */     }
/*    */     
/* 53 */     if (x.length < 2) {
/* 54 */       throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_OF_POINTS, Integer.valueOf(x.length), Integer.valueOf(2), true);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 59 */     int n = x.length - 1;
/*    */     
/* 61 */     MathArrays.checkOrder(x);
/*    */ 
/*    */     
/* 64 */     double[] m = new double[n];
/* 65 */     for (int i = 0; i < n; i++) {
/* 66 */       m[i] = (y[i + 1] - y[i]) / (x[i + 1] - x[i]);
/*    */     }
/*    */     
/* 69 */     PolynomialFunction[] polynomials = new PolynomialFunction[n];
/* 70 */     double[] coefficients = new double[2];
/* 71 */     for (int j = 0; j < n; j++) {
/* 72 */       coefficients[0] = y[j];
/* 73 */       coefficients[1] = m[j];
/* 74 */       polynomials[j] = new PolynomialFunction(coefficients);
/*    */     } 
/*    */     
/* 77 */     return new PolynomialSplineFunction(x, polynomials);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/LinearInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */