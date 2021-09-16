/*     */ package org.apache.commons.math3.fitting.leastsquares;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
/*     */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
/*     */ import org.apache.commons.math3.linear.ArrayRealVector;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optim.PointVectorValuePair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LeastSquaresBuilder
/*     */ {
/*     */   private int maxEvaluations;
/*     */   private int maxIterations;
/*     */   private ConvergenceChecker<LeastSquaresProblem.Evaluation> checker;
/*     */   private MultivariateJacobianFunction model;
/*     */   private RealVector target;
/*     */   private RealVector start;
/*     */   private RealMatrix weight;
/*     */   private boolean lazyEvaluation;
/*     */   private ParameterValidator paramValidator;
/*     */   
/*     */   public LeastSquaresProblem build() {
/*  69 */     return LeastSquaresFactory.create(this.model, this.target, this.start, this.weight, this.checker, this.maxEvaluations, this.maxIterations, this.lazyEvaluation, this.paramValidator);
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
/*     */   public LeastSquaresBuilder maxEvaluations(int newMaxEvaluations) {
/*  87 */     this.maxEvaluations = newMaxEvaluations;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LeastSquaresBuilder maxIterations(int newMaxIterations) {
/*  98 */     this.maxIterations = newMaxIterations;
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LeastSquaresBuilder checker(ConvergenceChecker<LeastSquaresProblem.Evaluation> newChecker) {
/* 109 */     this.checker = newChecker;
/* 110 */     return this;
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
/*     */   public LeastSquaresBuilder checkerPair(ConvergenceChecker<PointVectorValuePair> newChecker) {
/* 122 */     return checker(LeastSquaresFactory.evaluationChecker(newChecker));
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
/*     */   public LeastSquaresBuilder model(MultivariateVectorFunction value, MultivariateMatrixFunction jacobian) {
/* 134 */     return model(LeastSquaresFactory.model(value, jacobian));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LeastSquaresBuilder model(MultivariateJacobianFunction newModel) {
/* 144 */     this.model = newModel;
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LeastSquaresBuilder target(RealVector newTarget) {
/* 155 */     this.target = newTarget;
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LeastSquaresBuilder target(double[] newTarget) {
/* 166 */     return target((RealVector)new ArrayRealVector(newTarget, false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LeastSquaresBuilder start(RealVector newStart) {
/* 176 */     this.start = newStart;
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LeastSquaresBuilder start(double[] newStart) {
/* 187 */     return start((RealVector)new ArrayRealVector(newStart, false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LeastSquaresBuilder weight(RealMatrix newWeight) {
/* 197 */     this.weight = newWeight;
/* 198 */     return this;
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
/*     */   public LeastSquaresBuilder lazyEvaluation(boolean newValue) {
/* 210 */     this.lazyEvaluation = newValue;
/* 211 */     return this;
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
/*     */   public LeastSquaresBuilder parameterValidator(ParameterValidator newValidator) {
/* 223 */     this.paramValidator = newValidator;
/* 224 */     return this;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/leastsquares/LeastSquaresBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */