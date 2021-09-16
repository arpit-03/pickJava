/*     */ package org.apache.commons.math3.analysis.polynomials;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
/*     */ import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolynomialFunctionNewtonForm
/*     */   implements UnivariateDifferentiableFunction
/*     */ {
/*     */   private double[] coefficients;
/*     */   private final double[] c;
/*     */   private final double[] a;
/*     */   private boolean coefficientsComputed;
/*     */   
/*     */   public PolynomialFunctionNewtonForm(double[] a, double[] c) throws NullArgumentException, NoDataException, DimensionMismatchException {
/*  81 */     verifyInputArray(a, c);
/*  82 */     this.a = new double[a.length];
/*  83 */     this.c = new double[c.length];
/*  84 */     System.arraycopy(a, 0, this.a, 0, a.length);
/*  85 */     System.arraycopy(c, 0, this.c, 0, c.length);
/*  86 */     this.coefficientsComputed = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double value(double z) {
/*  96 */     return evaluate(this.a, this.c, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivativeStructure value(DerivativeStructure t) {
/* 104 */     verifyInputArray(this.a, this.c);
/*     */     
/* 106 */     int n = this.c.length;
/* 107 */     DerivativeStructure value = new DerivativeStructure(t.getFreeParameters(), t.getOrder(), this.a[n]);
/* 108 */     for (int i = n - 1; i >= 0; i--) {
/* 109 */       value = t.subtract(this.c[i]).multiply(value).add(this.a[i]);
/*     */     }
/*     */     
/* 112 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int degree() {
/* 122 */     return this.c.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getNewtonCoefficients() {
/* 133 */     double[] out = new double[this.a.length];
/* 134 */     System.arraycopy(this.a, 0, out, 0, this.a.length);
/* 135 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getCenters() {
/* 146 */     double[] out = new double[this.c.length];
/* 147 */     System.arraycopy(this.c, 0, out, 0, this.c.length);
/* 148 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getCoefficients() {
/* 159 */     if (!this.coefficientsComputed) {
/* 160 */       computeCoefficients();
/*     */     }
/* 162 */     double[] out = new double[this.coefficients.length];
/* 163 */     System.arraycopy(this.coefficients, 0, out, 0, this.coefficients.length);
/* 164 */     return out;
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
/*     */   public static double evaluate(double[] a, double[] c, double z) throws NullArgumentException, DimensionMismatchException, NoDataException {
/* 183 */     verifyInputArray(a, c);
/*     */     
/* 185 */     int n = c.length;
/* 186 */     double value = a[n];
/* 187 */     for (int i = n - 1; i >= 0; i--) {
/* 188 */       value = a[i] + (z - c[i]) * value;
/*     */     }
/*     */     
/* 191 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void computeCoefficients() {
/* 199 */     int n = degree();
/*     */     
/* 201 */     this.coefficients = new double[n + 1]; int i;
/* 202 */     for (i = 0; i <= n; i++) {
/* 203 */       this.coefficients[i] = 0.0D;
/*     */     }
/*     */     
/* 206 */     this.coefficients[0] = this.a[n];
/* 207 */     for (i = n - 1; i >= 0; i--) {
/* 208 */       for (int j = n - i; j > 0; j--) {
/* 209 */         this.coefficients[j] = this.coefficients[j - 1] - this.c[i] * this.coefficients[j];
/*     */       }
/* 211 */       this.coefficients[0] = this.a[i] - this.c[i] * this.coefficients[0];
/*     */     } 
/*     */     
/* 214 */     this.coefficientsComputed = true;
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
/*     */   protected static void verifyInputArray(double[] a, double[] c) throws NullArgumentException, NoDataException, DimensionMismatchException {
/* 234 */     MathUtils.checkNotNull(a);
/* 235 */     MathUtils.checkNotNull(c);
/* 236 */     if (a.length == 0 || c.length == 0) {
/* 237 */       throw new NoDataException(LocalizedFormats.EMPTY_POLYNOMIALS_COEFFICIENTS_ARRAY);
/*     */     }
/* 239 */     if (a.length != c.length + 1)
/* 240 */       throw new DimensionMismatchException(LocalizedFormats.ARRAY_SIZES_SHOULD_HAVE_DIFFERENCE_1, a.length, c.length); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/polynomials/PolynomialFunctionNewtonForm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */