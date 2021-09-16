/*     */ package org.apache.commons.math3.analysis.function;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.FunctionUtils;
/*     */ import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
/*     */ import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ public class Gaussian
/*     */   implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction
/*     */ {
/*     */   private final double mean;
/*     */   private final double is;
/*     */   private final double i2s2;
/*     */   private final double norm;
/*     */   
/*     */   public Gaussian(double norm, double mean, double sigma) throws NotStrictlyPositiveException {
/*  62 */     if (sigma <= 0.0D) {
/*  63 */       throw new NotStrictlyPositiveException(Double.valueOf(sigma));
/*     */     }
/*     */     
/*  66 */     this.norm = norm;
/*  67 */     this.mean = mean;
/*  68 */     this.is = 1.0D / sigma;
/*  69 */     this.i2s2 = 0.5D * this.is * this.is;
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
/*     */   public Gaussian(double mean, double sigma) throws NotStrictlyPositiveException {
/*  82 */     this(1.0D / sigma * FastMath.sqrt(6.283185307179586D), mean, sigma);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Gaussian() {
/*  89 */     this(0.0D, 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public double value(double x) {
/*  94 */     return value(x - this.mean, this.norm, this.i2s2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public UnivariateFunction derivative() {
/* 102 */     return FunctionUtils.toDifferentiableUnivariateFunction(this).derivative();
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
/*     */   public static class Parametric
/*     */     implements ParametricUnivariateFunction
/*     */   {
/*     */     public double value(double x, double... param) throws NullArgumentException, DimensionMismatchException, NotStrictlyPositiveException {
/* 130 */       validateParameters(param);
/*     */       
/* 132 */       double diff = x - param[1];
/* 133 */       double i2s2 = 1.0D / 2.0D * param[2] * param[2];
/* 134 */       return Gaussian.value(diff, param[0], i2s2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] gradient(double x, double... param) throws NullArgumentException, DimensionMismatchException, NotStrictlyPositiveException {
/* 155 */       validateParameters(param);
/*     */       
/* 157 */       double norm = param[0];
/* 158 */       double diff = x - param[1];
/* 159 */       double sigma = param[2];
/* 160 */       double i2s2 = 1.0D / 2.0D * sigma * sigma;
/*     */       
/* 162 */       double n = Gaussian.value(diff, 1.0D, i2s2);
/* 163 */       double m = norm * n * 2.0D * i2s2 * diff;
/* 164 */       double s = m * diff / sigma;
/*     */       
/* 166 */       return new double[] { n, m, s };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void validateParameters(double[] param) throws NullArgumentException, DimensionMismatchException, NotStrictlyPositiveException {
/* 184 */       if (param == null) {
/* 185 */         throw new NullArgumentException();
/*     */       }
/* 187 */       if (param.length != 3) {
/* 188 */         throw new DimensionMismatchException(param.length, 3);
/*     */       }
/* 190 */       if (param[2] <= 0.0D) {
/* 191 */         throw new NotStrictlyPositiveException(Double.valueOf(param[2]));
/*     */       }
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
/*     */   private static double value(double xMinusMean, double norm, double i2s2) {
/* 205 */     return norm * FastMath.exp(-xMinusMean * xMinusMean * i2s2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
/* 214 */     double u = this.is * (t.getValue() - this.mean);
/* 215 */     double[] f = new double[t.getOrder() + 1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     double[] p = new double[f.length];
/* 225 */     p[0] = 1.0D;
/* 226 */     double u2 = u * u;
/* 227 */     double coeff = this.norm * FastMath.exp(-0.5D * u2);
/* 228 */     if (coeff <= Precision.SAFE_MIN) {
/* 229 */       Arrays.fill(f, 0.0D);
/*     */     } else {
/* 231 */       f[0] = coeff;
/* 232 */       for (int n = 1; n < f.length; n++) {
/*     */ 
/*     */         
/* 235 */         double v = 0.0D;
/* 236 */         p[n] = -p[n - 1];
/* 237 */         for (int k = n; k >= 0; k -= 2) {
/* 238 */           v = v * u2 + p[k];
/* 239 */           if (k > 2) {
/* 240 */             p[k - 2] = (k - 1) * p[k - 1] - p[k - 3];
/* 241 */           } else if (k == 2) {
/* 242 */             p[0] = p[1];
/*     */           } 
/*     */         } 
/* 245 */         if ((n & 0x1) == 1) {
/* 246 */           v *= u;
/*     */         }
/*     */         
/* 249 */         coeff *= this.is;
/* 250 */         f[n] = coeff * v;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 255 */     return t.compose(f);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/function/Gaussian.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */