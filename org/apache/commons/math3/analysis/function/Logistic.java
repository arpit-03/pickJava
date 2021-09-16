/*     */ package org.apache.commons.math3.analysis.function;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Logistic
/*     */   implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction
/*     */ {
/*     */   private final double a;
/*     */   private final double k;
/*     */   private final double b;
/*     */   private final double oneOverN;
/*     */   private final double q;
/*     */   private final double m;
/*     */   
/*     */   public Logistic(double k, double m, double b, double q, double a, double n) throws NotStrictlyPositiveException {
/*  71 */     if (n <= 0.0D) {
/*  72 */       throw new NotStrictlyPositiveException(Double.valueOf(n));
/*     */     }
/*     */     
/*  75 */     this.k = k;
/*  76 */     this.m = m;
/*  77 */     this.b = b;
/*  78 */     this.q = q;
/*  79 */     this.a = a;
/*  80 */     this.oneOverN = 1.0D / n;
/*     */   }
/*     */ 
/*     */   
/*     */   public double value(double x) {
/*  85 */     return value(this.m - x, this.k, this.b, this.q, this.a, this.oneOverN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public UnivariateFunction derivative() {
/*  93 */     return FunctionUtils.toDifferentiableUnivariateFunction(this).derivative();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Parametric
/*     */     implements ParametricUnivariateFunction
/*     */   {
/*     */     public double value(double x, double... param) throws NullArgumentException, DimensionMismatchException, NotStrictlyPositiveException {
/* 126 */       validateParameters(param);
/* 127 */       return Logistic.value(param[1] - x, param[0], param[2], param[3], param[4], 1.0D / param[5]);
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
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] gradient(double x, double... param) throws NullArgumentException, DimensionMismatchException, NotStrictlyPositiveException {
/* 151 */       validateParameters(param);
/*     */       
/* 153 */       double b = param[2];
/* 154 */       double q = param[3];
/*     */       
/* 156 */       double mMinusX = param[1] - x;
/* 157 */       double oneOverN = 1.0D / param[5];
/* 158 */       double exp = FastMath.exp(b * mMinusX);
/* 159 */       double qExp = q * exp;
/* 160 */       double qExp1 = qExp + 1.0D;
/* 161 */       double factor1 = (param[0] - param[4]) * oneOverN / FastMath.pow(qExp1, oneOverN);
/* 162 */       double factor2 = -factor1 / qExp1;
/*     */ 
/*     */       
/* 165 */       double gk = Logistic.value(mMinusX, 1.0D, b, q, 0.0D, oneOverN);
/* 166 */       double gm = factor2 * b * qExp;
/* 167 */       double gb = factor2 * mMinusX * qExp;
/* 168 */       double gq = factor2 * exp;
/* 169 */       double ga = Logistic.value(mMinusX, 0.0D, b, q, 1.0D, oneOverN);
/* 170 */       double gn = factor1 * FastMath.log(qExp1) * oneOverN;
/*     */       
/* 172 */       return new double[] { gk, gm, gb, gq, ga, gn };
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
/*     */     private void validateParameters(double[] param) throws NullArgumentException, DimensionMismatchException, NotStrictlyPositiveException {
/* 191 */       if (param == null) {
/* 192 */         throw new NullArgumentException();
/*     */       }
/* 194 */       if (param.length != 6) {
/* 195 */         throw new DimensionMismatchException(param.length, 6);
/*     */       }
/* 197 */       if (param[5] <= 0.0D) {
/* 198 */         throw new NotStrictlyPositiveException(Double.valueOf(param[5]));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double value(double mMinusX, double k, double b, double q, double a, double oneOverN) {
/* 218 */     return a + (k - a) / FastMath.pow(1.0D + q * FastMath.exp(b * mMinusX), oneOverN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivativeStructure value(DerivativeStructure t) {
/* 225 */     return t.negate().add(this.m).multiply(this.b).exp().multiply(this.q).add(1.0D).pow(this.oneOverN).reciprocal().multiply(this.k - this.a).add(this.a);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/function/Logistic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */