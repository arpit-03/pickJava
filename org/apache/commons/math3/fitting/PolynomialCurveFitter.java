/*     */ package org.apache.commons.math3.fitting;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
/*     */ import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
/*     */ import org.apache.commons.math3.linear.DiagonalMatrix;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolynomialCurveFitter
/*     */   extends AbstractCurveFitter
/*     */ {
/*  41 */   private static final PolynomialFunction.Parametric FUNCTION = new PolynomialFunction.Parametric();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double[] initialGuess;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int maxIter;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PolynomialCurveFitter(double[] initialGuess, int maxIter) {
/*  56 */     this.initialGuess = initialGuess;
/*  57 */     this.maxIter = maxIter;
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
/*     */   public static PolynomialCurveFitter create(int degree) {
/*  73 */     return new PolynomialCurveFitter(new double[degree + 1], 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolynomialCurveFitter withStartPoint(double[] newStart) {
/*  82 */     return new PolynomialCurveFitter((double[])newStart.clone(), this.maxIter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolynomialCurveFitter withMaxIterations(int newMaxIter) {
/*  92 */     return new PolynomialCurveFitter(this.initialGuess, newMaxIter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LeastSquaresProblem getProblem(Collection<WeightedObservedPoint> observations) {
/* 100 */     int len = observations.size();
/* 101 */     double[] target = new double[len];
/* 102 */     double[] weights = new double[len];
/*     */     
/* 104 */     int i = 0;
/* 105 */     for (WeightedObservedPoint obs : observations) {
/* 106 */       target[i] = obs.getY();
/* 107 */       weights[i] = obs.getWeight();
/* 108 */       i++;
/*     */     } 
/*     */     
/* 111 */     AbstractCurveFitter.TheoreticalValuesFunction model = new AbstractCurveFitter.TheoreticalValuesFunction((ParametricUnivariateFunction)FUNCTION, observations);
/*     */ 
/*     */     
/* 114 */     if (this.initialGuess == null) {
/* 115 */       throw new MathInternalError();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 120 */     return (new LeastSquaresBuilder()).maxEvaluations(2147483647).maxIterations(this.maxIter).start(this.initialGuess).target(target).weight((RealMatrix)new DiagonalMatrix(weights)).model(model.getModelFunction(), model.getModelFunctionJacobian()).build();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/PolynomialCurveFitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */