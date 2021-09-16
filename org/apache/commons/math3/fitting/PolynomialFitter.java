/*    */ package org.apache.commons.math3.fitting;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
/*    */ import org.apache.commons.math3.optim.nonlinear.vector.MultivariateVectorOptimizer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class PolynomialFitter
/*    */   extends CurveFitter<PolynomialFunction.Parametric>
/*    */ {
/*    */   public PolynomialFitter(MultivariateVectorOptimizer optimizer) {
/* 39 */     super(optimizer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] fit(int maxEval, double[] guess) {
/* 56 */     return fit(maxEval, new PolynomialFunction.Parametric(), guess);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] fit(double[] guess) {
/* 70 */     return fit(new PolynomialFunction.Parametric(), guess);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/PolynomialFitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */