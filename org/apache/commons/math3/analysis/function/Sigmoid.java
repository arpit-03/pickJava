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
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Sigmoid
/*     */   implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction
/*     */ {
/*     */   private final double lo;
/*     */   private final double hi;
/*     */   
/*     */   public Sigmoid() {
/*  52 */     this(0.0D, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sigmoid(double lo, double hi) {
/*  63 */     this.lo = lo;
/*  64 */     this.hi = hi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public UnivariateFunction derivative() {
/*  72 */     return FunctionUtils.toDifferentiableUnivariateFunction(this).derivative();
/*     */   }
/*     */ 
/*     */   
/*     */   public double value(double x) {
/*  77 */     return value(x, this.lo, this.hi);
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
/*     */   public static class Parametric
/*     */     implements ParametricUnivariateFunction
/*     */   {
/*     */     public double value(double x, double... param) throws NullArgumentException, DimensionMismatchException {
/* 103 */       validateParameters(param);
/* 104 */       return Sigmoid.value(x, param[0], param[1]);
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
/*     */     public double[] gradient(double x, double... param) throws NullArgumentException, DimensionMismatchException {
/* 123 */       validateParameters(param);
/*     */       
/* 125 */       double invExp1 = 1.0D / (1.0D + FastMath.exp(-x));
/*     */       
/* 127 */       return new double[] { 1.0D - invExp1, invExp1 };
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
/*     */     private void validateParameters(double[] param) throws NullArgumentException, DimensionMismatchException {
/* 143 */       if (param == null) {
/* 144 */         throw new NullArgumentException();
/*     */       }
/* 146 */       if (param.length != 2) {
/* 147 */         throw new DimensionMismatchException(param.length, 2);
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
/*     */   private static double value(double x, double lo, double hi) {
/* 161 */     return lo + (hi - lo) / (1.0D + FastMath.exp(-x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
/* 170 */     double[] f = new double[t.getOrder() + 1];
/* 171 */     double exp = FastMath.exp(-t.getValue());
/* 172 */     if (Double.isInfinite(exp)) {
/*     */ 
/*     */       
/* 175 */       f[0] = this.lo;
/* 176 */       Arrays.fill(f, 1, f.length, 0.0D);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 186 */       double[] p = new double[f.length];
/*     */       
/* 188 */       double inv = 1.0D / (1.0D + exp);
/* 189 */       double coeff = this.hi - this.lo;
/* 190 */       for (int n = 0; n < f.length; n++) {
/*     */ 
/*     */         
/* 193 */         double v = 0.0D;
/* 194 */         p[n] = 1.0D;
/* 195 */         for (int k = n; k >= 0; k--) {
/* 196 */           v = v * exp + p[k];
/* 197 */           if (k > 1) {
/* 198 */             p[k - 1] = (n - k + 2) * p[k - 2] - (k - 1) * p[k - 1];
/*     */           } else {
/* 200 */             p[0] = 0.0D;
/*     */           } 
/*     */         } 
/*     */         
/* 204 */         coeff *= inv;
/* 205 */         f[n] = coeff * v;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 210 */       f[0] = f[0] + this.lo;
/*     */     } 
/*     */ 
/*     */     
/* 214 */     return t.compose(f);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/function/Sigmoid.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */