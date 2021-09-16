/*     */ package org.apache.commons.math3.optim.nonlinear.vector;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.optim.BaseMultivariateOptimizer;
/*     */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optim.OptimizationData;
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
/*     */ @Deprecated
/*     */ public abstract class MultivariateVectorOptimizer
/*     */   extends BaseMultivariateOptimizer<PointVectorValuePair>
/*     */ {
/*     */   private double[] target;
/*     */   private RealMatrix weightMatrix;
/*     */   private MultivariateVectorFunction model;
/*     */   
/*     */   protected MultivariateVectorOptimizer(ConvergenceChecker<PointVectorValuePair> checker) {
/*  48 */     super(checker);
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
/*     */   protected double[] computeObjectiveValue(double[] params) {
/*  62 */     incrementEvaluationCount();
/*  63 */     return this.model.value(params);
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
/*     */   public PointVectorValuePair optimize(OptimizationData... optData) throws TooManyEvaluationsException, DimensionMismatchException {
/*  88 */     return (PointVectorValuePair)super.optimize(optData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getWeight() {
/*  97 */     return this.weightMatrix.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getTarget() {
/* 106 */     return (double[])this.target.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTargetSize() {
/* 115 */     return this.target.length;
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
/*     */   protected void parseOptimizationData(OptimizationData... optData) {
/* 132 */     super.parseOptimizationData(optData);
/*     */ 
/*     */ 
/*     */     
/* 136 */     for (OptimizationData data : optData) {
/* 137 */       if (data instanceof ModelFunction) {
/* 138 */         this.model = ((ModelFunction)data).getModelFunction();
/*     */       
/*     */       }
/* 141 */       else if (data instanceof Target) {
/* 142 */         this.target = ((Target)data).getTarget();
/*     */       
/*     */       }
/* 145 */       else if (data instanceof Weight) {
/* 146 */         this.weightMatrix = ((Weight)data).getWeight();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 152 */     checkParameters();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkParameters() {
/* 162 */     if (this.target.length != this.weightMatrix.getColumnDimension())
/* 163 */       throw new DimensionMismatchException(this.target.length, this.weightMatrix.getColumnDimension()); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/vector/MultivariateVectorOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */