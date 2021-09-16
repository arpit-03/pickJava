/*     */ package org.apache.commons.math3.analysis.function;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.FunctionUtils;
/*     */ import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
/*     */ import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
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
/*     */ public class Logit
/*     */   implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction
/*     */ {
/*     */   private final double lo;
/*     */   private final double hi;
/*     */   
/*     */   public Logit() {
/*  49 */     this(0.0D, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Logit(double lo, double hi) {
/*  60 */     this.lo = lo;
/*  61 */     this.hi = hi;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double value(double x) throws OutOfRangeException {
/*  67 */     return value(x, this.lo, this.hi);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public UnivariateFunction derivative() {
/*  75 */     return FunctionUtils.toDifferentiableUnivariateFunction(this).derivative();
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
/*     */   public static class Parametric
/*     */     implements ParametricUnivariateFunction
/*     */   {
/*     */     public double value(double x, double... param) throws NullArgumentException, DimensionMismatchException {
/* 100 */       validateParameters(param);
/* 101 */       return Logit.value(x, param[0], param[1]);
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
/* 120 */       validateParameters(param);
/*     */       
/* 122 */       double lo = param[0];
/* 123 */       double hi = param[1];
/*     */       
/* 125 */       return new double[] { 1.0D / (lo - x), 1.0D / (hi - x) };
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
/* 141 */       if (param == null) {
/* 142 */         throw new NullArgumentException();
/*     */       }
/* 144 */       if (param.length != 2) {
/* 145 */         throw new DimensionMismatchException(param.length, 2);
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
/*     */ 
/*     */   
/*     */   private static double value(double x, double lo, double hi) throws OutOfRangeException {
/* 161 */     if (x < lo || x > hi) {
/* 162 */       throw new OutOfRangeException(Double.valueOf(x), Double.valueOf(lo), Double.valueOf(hi));
/*     */     }
/* 164 */     return FastMath.log((x - lo) / (hi - x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivativeStructure value(DerivativeStructure t) throws OutOfRangeException {
/* 173 */     double x = t.getValue();
/* 174 */     if (x < this.lo || x > this.hi) {
/* 175 */       throw new OutOfRangeException(Double.valueOf(x), Double.valueOf(this.lo), Double.valueOf(this.hi));
/*     */     }
/* 177 */     double[] f = new double[t.getOrder() + 1];
/*     */ 
/*     */     
/* 180 */     f[0] = FastMath.log((x - this.lo) / (this.hi - x));
/*     */     
/* 182 */     if (Double.isInfinite(f[0])) {
/*     */       
/* 184 */       if (f.length > 1) {
/* 185 */         f[1] = Double.POSITIVE_INFINITY;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       for (int i = 2; i < f.length; i++) {
/* 193 */         f[i] = f[i - 2];
/*     */       
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 199 */       double invL = 1.0D / (x - this.lo);
/* 200 */       double xL = invL;
/* 201 */       double invH = 1.0D / (this.hi - x);
/* 202 */       double xH = invH;
/* 203 */       for (int i = 1; i < f.length; i++) {
/* 204 */         f[i] = xL + xH;
/* 205 */         xL *= -i * invL;
/* 206 */         xH *= i * invH;
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     return t.compose(f);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/function/Logit.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */