/*     */ package org.apache.commons.math3.analysis.interpolation;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
/*     */ import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ import org.apache.commons.math3.util.Precision;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AkimaSplineInterpolator
/*     */   implements UnivariateInterpolator
/*     */ {
/*     */   private static final int MINIMUM_NUMBER_POINTS = 5;
/*     */   
/*     */   public PolynomialSplineFunction interpolate(double[] xvals, double[] yvals) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
/*  71 */     if (xvals == null || yvals == null)
/*     */     {
/*  73 */       throw new NullArgumentException();
/*     */     }
/*     */     
/*  76 */     if (xvals.length != yvals.length) {
/*  77 */       throw new DimensionMismatchException(xvals.length, yvals.length);
/*     */     }
/*     */     
/*  80 */     if (xvals.length < 5) {
/*  81 */       throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_OF_POINTS, Integer.valueOf(xvals.length), Integer.valueOf(5), true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  86 */     MathArrays.checkOrder(xvals);
/*     */     
/*  88 */     int numberOfDiffAndWeightElements = xvals.length - 1;
/*     */     
/*  90 */     double[] differences = new double[numberOfDiffAndWeightElements];
/*  91 */     double[] weights = new double[numberOfDiffAndWeightElements];
/*     */     int i;
/*  93 */     for (i = 0; i < differences.length; i++) {
/*  94 */       differences[i] = (yvals[i + 1] - yvals[i]) / (xvals[i + 1] - xvals[i]);
/*     */     }
/*     */     
/*  97 */     for (i = 1; i < weights.length; i++) {
/*  98 */       weights[i] = FastMath.abs(differences[i] - differences[i - 1]);
/*     */     }
/*     */ 
/*     */     
/* 102 */     double[] firstDerivatives = new double[xvals.length];
/*     */     
/* 104 */     for (int j = 2; j < firstDerivatives.length - 2; j++) {
/* 105 */       double wP = weights[j + 1];
/* 106 */       double wM = weights[j - 1];
/* 107 */       if (Precision.equals(wP, 0.0D) && Precision.equals(wM, 0.0D)) {
/*     */         
/* 109 */         double xv = xvals[j];
/* 110 */         double xvP = xvals[j + 1];
/* 111 */         double xvM = xvals[j - 1];
/* 112 */         firstDerivatives[j] = ((xvP - xv) * differences[j - 1] + (xv - xvM) * differences[j]) / (xvP - xvM);
/*     */       } else {
/* 114 */         firstDerivatives[j] = (wP * differences[j - 1] + wM * differences[j]) / (wP + wM);
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     firstDerivatives[0] = differentiateThreePoint(xvals, yvals, 0, 0, 1, 2);
/* 119 */     firstDerivatives[1] = differentiateThreePoint(xvals, yvals, 1, 0, 1, 2);
/* 120 */     firstDerivatives[xvals.length - 2] = differentiateThreePoint(xvals, yvals, xvals.length - 2, xvals.length - 3, xvals.length - 2, xvals.length - 1);
/*     */ 
/*     */     
/* 123 */     firstDerivatives[xvals.length - 1] = differentiateThreePoint(xvals, yvals, xvals.length - 1, xvals.length - 3, xvals.length - 2, xvals.length - 1);
/*     */ 
/*     */ 
/*     */     
/* 127 */     return interpolateHermiteSorted(xvals, yvals, firstDerivatives);
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
/*     */   private double differentiateThreePoint(double[] xvals, double[] yvals, int indexOfDifferentiation, int indexOfFirstSample, int indexOfSecondsample, int indexOfThirdSample) {
/* 148 */     double x0 = yvals[indexOfFirstSample];
/* 149 */     double x1 = yvals[indexOfSecondsample];
/* 150 */     double x2 = yvals[indexOfThirdSample];
/*     */     
/* 152 */     double t = xvals[indexOfDifferentiation] - xvals[indexOfFirstSample];
/* 153 */     double t1 = xvals[indexOfSecondsample] - xvals[indexOfFirstSample];
/* 154 */     double t2 = xvals[indexOfThirdSample] - xvals[indexOfFirstSample];
/*     */     
/* 156 */     double a = (x2 - x0 - t2 / t1 * (x1 - x0)) / (t2 * t2 - t1 * t2);
/* 157 */     double b = (x1 - x0 - a * t1 * t1) / t1;
/*     */     
/* 159 */     return 2.0D * a * t + b;
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
/*     */   private PolynomialSplineFunction interpolateHermiteSorted(double[] xvals, double[] yvals, double[] firstDerivatives) {
/* 175 */     if (xvals.length != yvals.length) {
/* 176 */       throw new DimensionMismatchException(xvals.length, yvals.length);
/*     */     }
/*     */     
/* 179 */     if (xvals.length != firstDerivatives.length) {
/* 180 */       throw new DimensionMismatchException(xvals.length, firstDerivatives.length);
/*     */     }
/*     */ 
/*     */     
/* 184 */     int minimumLength = 2;
/* 185 */     if (xvals.length < 2) {
/* 186 */       throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_OF_POINTS, Integer.valueOf(xvals.length), Integer.valueOf(2), true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 191 */     int size = xvals.length - 1;
/* 192 */     PolynomialFunction[] polynomials = new PolynomialFunction[size];
/* 193 */     double[] coefficients = new double[4];
/*     */     
/* 195 */     for (int i = 0; i < polynomials.length; i++) {
/* 196 */       double w = xvals[i + 1] - xvals[i];
/* 197 */       double w2 = w * w;
/*     */       
/* 199 */       double yv = yvals[i];
/* 200 */       double yvP = yvals[i + 1];
/*     */       
/* 202 */       double fd = firstDerivatives[i];
/* 203 */       double fdP = firstDerivatives[i + 1];
/*     */       
/* 205 */       coefficients[0] = yv;
/* 206 */       coefficients[1] = firstDerivatives[i];
/* 207 */       coefficients[2] = (3.0D * (yvP - yv) / w - 2.0D * fd - fdP) / w;
/* 208 */       coefficients[3] = (2.0D * (yv - yvP) / w + fd + fdP) / w2;
/* 209 */       polynomials[i] = new PolynomialFunction(coefficients);
/*     */     } 
/*     */     
/* 212 */     return new PolynomialSplineFunction(xvals, polynomials);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/interpolation/AkimaSplineInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */