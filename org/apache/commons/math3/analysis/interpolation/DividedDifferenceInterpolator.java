/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
/*     */ import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionNewtonForm;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DividedDifferenceInterpolator
/*     */   implements UnivariateInterpolator, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 107049519551235069L;
/*     */   
/*     */   public PolynomialFunctionNewtonForm interpolate(double[] x, double[] y) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
/*  63 */     PolynomialFunctionLagrangeForm.verifyInterpolationArray(x, y, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     double[] c = new double[x.length - 1];
/*  74 */     System.arraycopy(x, 0, c, 0, c.length);
/*     */     
/*  76 */     double[] a = computeDividedDifference(x, y);
/*  77 */     return new PolynomialFunctionNewtonForm(a, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static double[] computeDividedDifference(double[] x, double[] y) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
/* 103 */     PolynomialFunctionLagrangeForm.verifyInterpolationArray(x, y, true);
/*     */     
/* 105 */     double[] divdiff = (double[])y.clone();
/*     */     
/* 107 */     int n = x.length;
/* 108 */     double[] a = new double[n];
/* 109 */     a[0] = divdiff[0];
/* 110 */     for (int i = 1; i < n; i++) {
/* 111 */       for (int j = 0; j < n - i; j++) {
/* 112 */         double denominator = x[j + i] - x[j];
/* 113 */         divdiff[j] = (divdiff[j + 1] - divdiff[j]) / denominator;
/*     */       } 
/* 115 */       a[i] = divdiff[0];
/*     */     } 
/*     */     
/* 118 */     return a;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/DividedDifferenceInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */