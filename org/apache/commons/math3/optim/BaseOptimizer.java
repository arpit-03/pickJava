/*     */ package org.apache.commons.math3.optim;
/*     */ 
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.exception.TooManyIterationsException;
/*     */ import org.apache.commons.math3.util.Incrementor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseOptimizer<PAIR>
/*     */ {
/*     */   protected final Incrementor evaluations;
/*     */   protected final Incrementor iterations;
/*     */   private final ConvergenceChecker<PAIR> checker;
/*     */   
/*     */   protected BaseOptimizer(ConvergenceChecker<PAIR> checker) {
/*  47 */     this(checker, 0, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BaseOptimizer(ConvergenceChecker<PAIR> checker, int maxEval, int maxIter) {
/*  58 */     this.checker = checker;
/*     */     
/*  60 */     this.evaluations = new Incrementor(maxEval, new MaxEvalCallback());
/*  61 */     this.iterations = new Incrementor(maxIter, new MaxIterCallback());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEvaluations() {
/*  70 */     return this.evaluations.getMaximalCount();
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
/*     */   public int getEvaluations() {
/*  82 */     return this.evaluations.getCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxIterations() {
/*  91 */     return this.iterations.getMaximalCount();
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
/*     */   public int getIterations() {
/* 103 */     return this.iterations.getCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConvergenceChecker<PAIR> getConvergenceChecker() {
/* 112 */     return this.checker;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PAIR optimize(OptimizationData... optData) throws TooManyEvaluationsException, TooManyIterationsException {
/* 147 */     parseOptimizationData(optData);
/*     */ 
/*     */     
/* 150 */     this.evaluations.resetCount();
/* 151 */     this.iterations.resetCount();
/*     */     
/* 153 */     return doOptimize();
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
/*     */   public PAIR optimize() throws TooManyEvaluationsException, TooManyIterationsException {
/* 169 */     this.evaluations.resetCount();
/* 170 */     this.iterations.resetCount();
/*     */     
/* 172 */     return doOptimize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract PAIR doOptimize();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void incrementEvaluationCount() throws TooManyEvaluationsException {
/* 191 */     this.evaluations.incrementCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void incrementIterationCount() throws TooManyIterationsException {
/* 202 */     this.iterations.incrementCount();
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
/* 219 */     for (OptimizationData data : optData) {
/* 220 */       if (data instanceof MaxEval) {
/* 221 */         this.evaluations.setMaximalCount(((MaxEval)data).getMaxEval());
/*     */       
/*     */       }
/* 224 */       else if (data instanceof MaxIter) {
/* 225 */         this.iterations.setMaximalCount(((MaxIter)data).getMaxIter());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MaxEvalCallback
/*     */     implements Incrementor.MaxCountExceededCallback
/*     */   {
/*     */     private MaxEvalCallback() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void trigger(int max) {
/* 242 */       throw new TooManyEvaluationsException(Integer.valueOf(max));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MaxIterCallback
/*     */     implements Incrementor.MaxCountExceededCallback
/*     */   {
/*     */     private MaxIterCallback() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void trigger(int max) {
/* 257 */       throw new TooManyIterationsException(Integer.valueOf(max));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/BaseOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */