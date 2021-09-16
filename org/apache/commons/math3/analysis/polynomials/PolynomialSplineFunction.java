/*     */ package org.apache.commons.math3.analysis.polynomials;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
/*     */ import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.MathArrays;
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
/*     */ public class PolynomialSplineFunction
/*     */   implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction
/*     */ {
/*     */   private final double[] knots;
/*     */   private final PolynomialFunction[] polynomials;
/*     */   private final int n;
/*     */   
/*     */   public PolynomialSplineFunction(double[] knots, PolynomialFunction[] polynomials) throws NullArgumentException, NumberIsTooSmallException, DimensionMismatchException, NonMonotonicSequenceException {
/* 104 */     if (knots == null || polynomials == null)
/*     */     {
/* 106 */       throw new NullArgumentException();
/*     */     }
/* 108 */     if (knots.length < 2) {
/* 109 */       throw new NumberIsTooSmallException(LocalizedFormats.NOT_ENOUGH_POINTS_IN_SPLINE_PARTITION, Integer.valueOf(2), Integer.valueOf(knots.length), false);
/*     */     }
/*     */     
/* 112 */     if (knots.length - 1 != polynomials.length) {
/* 113 */       throw new DimensionMismatchException(polynomials.length, knots.length);
/*     */     }
/* 115 */     MathArrays.checkOrder(knots);
/*     */     
/* 117 */     this.n = knots.length - 1;
/* 118 */     this.knots = new double[this.n + 1];
/* 119 */     System.arraycopy(knots, 0, this.knots, 0, this.n + 1);
/* 120 */     this.polynomials = new PolynomialFunction[this.n];
/* 121 */     System.arraycopy(polynomials, 0, this.polynomials, 0, this.n);
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
/*     */   public double value(double v) {
/* 136 */     if (v < this.knots[0] || v > this.knots[this.n]) {
/* 137 */       throw new OutOfRangeException(Double.valueOf(v), Double.valueOf(this.knots[0]), Double.valueOf(this.knots[this.n]));
/*     */     }
/* 139 */     int i = Arrays.binarySearch(this.knots, v);
/* 140 */     if (i < 0) {
/* 141 */       i = -i - 2;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 146 */     if (i >= this.polynomials.length) {
/* 147 */       i--;
/*     */     }
/* 149 */     return this.polynomials[i].value(v - this.knots[i]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnivariateFunction derivative() {
/* 158 */     return (UnivariateFunction)polynomialSplineDerivative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolynomialSplineFunction polynomialSplineDerivative() {
/* 167 */     PolynomialFunction[] derivativePolynomials = new PolynomialFunction[this.n];
/* 168 */     for (int i = 0; i < this.n; i++) {
/* 169 */       derivativePolynomials[i] = this.polynomials[i].polynomialDerivative();
/*     */     }
/* 171 */     return new PolynomialSplineFunction(this.knots, derivativePolynomials);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivativeStructure value(DerivativeStructure t) {
/* 179 */     double t0 = t.getValue();
/* 180 */     if (t0 < this.knots[0] || t0 > this.knots[this.n]) {
/* 181 */       throw new OutOfRangeException(Double.valueOf(t0), Double.valueOf(this.knots[0]), Double.valueOf(this.knots[this.n]));
/*     */     }
/* 183 */     int i = Arrays.binarySearch(this.knots, t0);
/* 184 */     if (i < 0) {
/* 185 */       i = -i - 2;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 190 */     if (i >= this.polynomials.length) {
/* 191 */       i--;
/*     */     }
/* 193 */     return this.polynomials[i].value(t.subtract(this.knots[i]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getN() {
/* 203 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolynomialFunction[] getPolynomials() {
/* 214 */     PolynomialFunction[] p = new PolynomialFunction[this.n];
/* 215 */     System.arraycopy(this.polynomials, 0, p, 0, this.n);
/* 216 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getKnots() {
/* 227 */     double[] out = new double[this.n + 1];
/* 228 */     System.arraycopy(this.knots, 0, out, 0, this.n + 1);
/* 229 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidPoint(double x) {
/* 239 */     if (x < this.knots[0] || x > this.knots[this.n])
/*     */     {
/* 241 */       return false;
/*     */     }
/* 243 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/polynomials/PolynomialSplineFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */