/*     */ package org.apache.commons.math3.fitting;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleCurveFitter
/*     */   extends AbstractCurveFitter
/*     */ {
/*     */   private final ParametricUnivariateFunction function;
/*     */   private final double[] initialGuess;
/*     */   private final int maxIter;
/*     */   
/*     */   private SimpleCurveFitter(ParametricUnivariateFunction function, double[] initialGuess, int maxIter) {
/*  50 */     this.function = function;
/*  51 */     this.initialGuess = initialGuess;
/*  52 */     this.maxIter = maxIter;
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
/*     */   public static SimpleCurveFitter create(ParametricUnivariateFunction f, double[] start) {
/*  71 */     return new SimpleCurveFitter(f, start, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleCurveFitter withStartPoint(double[] newStart) {
/*  80 */     return new SimpleCurveFitter(this.function, (double[])newStart.clone(), this.maxIter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleCurveFitter withMaxIterations(int newMaxIter) {
/*  91 */     return new SimpleCurveFitter(this.function, this.initialGuess, newMaxIter);
/*     */   }
/*     */ 
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
/* 104 */     int count = 0;
/* 105 */     for (WeightedObservedPoint obs : observations) {
/* 106 */       target[count] = obs.getY();
/* 107 */       weights[count] = obs.getWeight();
/* 108 */       count++;
/*     */     } 
/*     */     
/* 111 */     AbstractCurveFitter.TheoreticalValuesFunction model = new AbstractCurveFitter.TheoreticalValuesFunction(this.function, observations);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     return (new LeastSquaresBuilder()).maxEvaluations(2147483647).maxIterations(this.maxIter).start(this.initialGuess).target(target).weight((RealMatrix)new DiagonalMatrix(weights)).model(model.getModelFunction(), model.getModelFunctionJacobian()).build();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/SimpleCurveFitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */