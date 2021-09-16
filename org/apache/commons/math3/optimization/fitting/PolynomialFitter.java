/*     */ package org.apache.commons.math3.optimization.fitting;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
/*     */ import org.apache.commons.math3.optimization.DifferentiableMultivariateVectorOptimizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class PolynomialFitter
/*     */   extends CurveFitter<PolynomialFunction.Parametric>
/*     */ {
/*     */   @Deprecated
/*     */   private final int degree;
/*     */   
/*     */   @Deprecated
/*     */   public PolynomialFitter(int degree, DifferentiableMultivariateVectorOptimizer optimizer) {
/*  51 */     super(optimizer);
/*  52 */     this.degree = degree;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolynomialFitter(DifferentiableMultivariateVectorOptimizer optimizer) {
/*  62 */     super(optimizer);
/*  63 */     this.degree = -1;
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
/*     */   @Deprecated
/*     */   public double[] fit() {
/*  76 */     return fit(new PolynomialFunction.Parametric(), new double[this.degree + 1]);
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
/*     */   public double[] fit(int maxEval, double[] guess) {
/*  94 */     return fit(maxEval, new PolynomialFunction.Parametric(), guess);
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
/*     */   public double[] fit(double[] guess) {
/* 109 */     return fit(new PolynomialFunction.Parametric(), guess);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/fitting/PolynomialFitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */