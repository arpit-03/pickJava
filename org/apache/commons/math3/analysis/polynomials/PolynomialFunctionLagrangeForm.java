/*     */ package org.apache.commons.math3.analysis.polynomials;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NonMonotonicSequenceException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ public class PolynomialFunctionLagrangeForm
/*     */   implements UnivariateFunction
/*     */ {
/*     */   private double[] coefficients;
/*     */   private final double[] x;
/*     */   private final double[] y;
/*     */   private boolean coefficientsComputed;
/*     */   
/*     */   public PolynomialFunctionLagrangeForm(double[] x, double[] y) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
/*  73 */     this.x = new double[x.length];
/*  74 */     this.y = new double[y.length];
/*  75 */     System.arraycopy(x, 0, this.x, 0, x.length);
/*  76 */     System.arraycopy(y, 0, this.y, 0, y.length);
/*  77 */     this.coefficientsComputed = false;
/*     */     
/*  79 */     if (!verifyInterpolationArray(x, y, false)) {
/*  80 */       MathArrays.sortInPlace(this.x, new double[][] { this.y });
/*     */       
/*  82 */       verifyInterpolationArray(this.x, this.y, true);
/*     */     } 
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
/*     */   public double value(double z) {
/*  99 */     return evaluateInternal(this.x, this.y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int degree() {
/* 108 */     return this.x.length - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getInterpolatingPoints() {
/* 119 */     double[] out = new double[this.x.length];
/* 120 */     System.arraycopy(this.x, 0, out, 0, this.x.length);
/* 121 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getInterpolatingValues() {
/* 132 */     double[] out = new double[this.y.length];
/* 133 */     System.arraycopy(this.y, 0, out, 0, this.y.length);
/* 134 */     return out;
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
/*     */   public double[] getCoefficients() {
/* 148 */     if (!this.coefficientsComputed) {
/* 149 */       computeCoefficients();
/*     */     }
/* 151 */     double[] out = new double[this.coefficients.length];
/* 152 */     System.arraycopy(this.coefficients, 0, out, 0, this.coefficients.length);
/* 153 */     return out;
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
/*     */   public static double evaluate(double[] x, double[] y, double z) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
/* 174 */     if (verifyInterpolationArray(x, y, false)) {
/* 175 */       return evaluateInternal(x, y, z);
/*     */     }
/*     */ 
/*     */     
/* 179 */     double[] xNew = new double[x.length];
/* 180 */     double[] yNew = new double[y.length];
/* 181 */     System.arraycopy(x, 0, xNew, 0, x.length);
/* 182 */     System.arraycopy(y, 0, yNew, 0, y.length);
/*     */     
/* 184 */     MathArrays.sortInPlace(xNew, new double[][] { yNew });
/*     */     
/* 186 */     verifyInterpolationArray(xNew, yNew, true);
/* 187 */     return evaluateInternal(xNew, yNew, z);
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
/*     */   private static double evaluateInternal(double[] x, double[] y, double z) {
/* 207 */     int nearest = 0;
/* 208 */     int n = x.length;
/* 209 */     double[] c = new double[n];
/* 210 */     double[] d = new double[n];
/* 211 */     double min_dist = Double.POSITIVE_INFINITY;
/* 212 */     for (int i = 0; i < n; i++) {
/*     */       
/* 214 */       c[i] = y[i];
/* 215 */       d[i] = y[i];
/*     */       
/* 217 */       double dist = FastMath.abs(z - x[i]);
/* 218 */       if (dist < min_dist) {
/* 219 */         nearest = i;
/* 220 */         min_dist = dist;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 225 */     double value = y[nearest];
/*     */     
/* 227 */     for (int j = 1; j < n; j++) {
/* 228 */       for (int k = 0; k < n - j; k++) {
/* 229 */         double tc = x[k] - z;
/* 230 */         double td = x[j + k] - z;
/* 231 */         double divider = x[k] - x[j + k];
/*     */         
/* 233 */         double w = (c[k + 1] - d[k]) / divider;
/* 234 */         c[k] = tc * w;
/* 235 */         d[k] = td * w;
/*     */       } 
/*     */       
/* 238 */       if (nearest < 0.5D * (n - j + 1)) {
/* 239 */         value += c[nearest];
/*     */       } else {
/* 241 */         nearest--;
/* 242 */         value += d[nearest];
/*     */       } 
/*     */     } 
/*     */     
/* 246 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void computeCoefficients() {
/* 256 */     int n = degree() + 1;
/* 257 */     this.coefficients = new double[n];
/* 258 */     for (int i = 0; i < n; i++) {
/* 259 */       this.coefficients[i] = 0.0D;
/*     */     }
/*     */ 
/*     */     
/* 263 */     double[] c = new double[n + 1];
/* 264 */     c[0] = 1.0D;
/* 265 */     for (int j = 0; j < n; j++) {
/* 266 */       for (int m = j; m > 0; m--) {
/* 267 */         c[m] = c[m - 1] - c[m] * this.x[j];
/*     */       }
/* 269 */       c[0] = c[0] * -this.x[j];
/* 270 */       c[j + 1] = 1.0D;
/*     */     } 
/*     */     
/* 273 */     double[] tc = new double[n];
/* 274 */     for (int k = 0; k < n; k++) {
/*     */       
/* 276 */       double d = 1.0D;
/* 277 */       for (int m = 0; m < n; m++) {
/* 278 */         if (k != m) {
/* 279 */           d *= this.x[k] - this.x[m];
/*     */         }
/*     */       } 
/* 282 */       double t = this.y[k] / d;
/*     */ 
/*     */ 
/*     */       
/* 286 */       tc[n - 1] = c[n];
/* 287 */       this.coefficients[n - 1] = this.coefficients[n - 1] + t * tc[n - 1];
/* 288 */       for (int i1 = n - 2; i1 >= 0; i1--) {
/* 289 */         tc[i1] = c[i1 + 1] + tc[i1 + 1] * this.x[k];
/* 290 */         this.coefficients[i1] = this.coefficients[i1] + t * tc[i1];
/*     */       } 
/*     */     } 
/*     */     
/* 294 */     this.coefficientsComputed = true;
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
/*     */   public static boolean verifyInterpolationArray(double[] x, double[] y, boolean abort) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
/* 317 */     if (x.length != y.length) {
/* 318 */       throw new DimensionMismatchException(x.length, y.length);
/*     */     }
/* 320 */     if (x.length < 2) {
/* 321 */       throw new NumberIsTooSmallException(LocalizedFormats.WRONG_NUMBER_OF_POINTS, Integer.valueOf(2), Integer.valueOf(x.length), true);
/*     */     }
/*     */     
/* 324 */     return MathArrays.checkOrder(x, MathArrays.OrderDirection.INCREASING, true, abort);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/polynomials/PolynomialFunctionLagrangeForm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */