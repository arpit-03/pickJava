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
/*     */ public class HarmonicOscillator
/*     */   implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction
/*     */ {
/*     */   private final double amplitude;
/*     */   private final double omega;
/*     */   private final double phase;
/*     */   
/*     */   public HarmonicOscillator(double amplitude, double omega, double phase) {
/*  54 */     this.amplitude = amplitude;
/*  55 */     this.omega = omega;
/*  56 */     this.phase = phase;
/*     */   }
/*     */ 
/*     */   
/*     */   public double value(double x) {
/*  61 */     return value(this.omega * x + this.phase, this.amplitude);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public UnivariateFunction derivative() {
/*  69 */     return FunctionUtils.toDifferentiableUnivariateFunction(this).derivative();
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
/*  95 */       validateParameters(param);
/*  96 */       return HarmonicOscillator.value(x * param[1] + param[2], param[0]);
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
/* 115 */       validateParameters(param);
/*     */       
/* 117 */       double amplitude = param[0];
/* 118 */       double omega = param[1];
/* 119 */       double phase = param[2];
/*     */       
/* 121 */       double xTimesOmegaPlusPhase = omega * x + phase;
/* 122 */       double a = HarmonicOscillator.value(xTimesOmegaPlusPhase, 1.0D);
/* 123 */       double p = -amplitude * FastMath.sin(xTimesOmegaPlusPhase);
/* 124 */       double w = p * x;
/*     */       
/* 126 */       return new double[] { a, w, p };
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
/* 142 */       if (param == null) {
/* 143 */         throw new NullArgumentException();
/*     */       }
/* 145 */       if (param.length != 3) {
/* 146 */         throw new DimensionMismatchException(param.length, 3);
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
/*     */   private static double value(double xTimesOmegaPlusPhase, double amplitude) {
/* 158 */     return amplitude * FastMath.cos(xTimesOmegaPlusPhase);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
/* 166 */     double x = t.getValue();
/* 167 */     double[] f = new double[t.getOrder() + 1];
/*     */     
/* 169 */     double alpha = this.omega * x + this.phase;
/* 170 */     f[0] = this.amplitude * FastMath.cos(alpha);
/* 171 */     if (f.length > 1) {
/* 172 */       f[1] = -this.amplitude * this.omega * FastMath.sin(alpha);
/* 173 */       double mo2 = -this.omega * this.omega;
/* 174 */       for (int i = 2; i < f.length; i++) {
/* 175 */         f[i] = mo2 * f[i - 2];
/*     */       }
/*     */     } 
/*     */     
/* 179 */     return t.compose(f);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/function/HarmonicOscillator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */