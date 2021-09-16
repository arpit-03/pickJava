/*     */ package org.apache.commons.math3.optimization.direct;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.optimization.BaseMultivariateOptimizer;
/*     */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optimization.GoalType;
/*     */ import org.apache.commons.math3.optimization.InitialGuess;
/*     */ import org.apache.commons.math3.optimization.OptimizationData;
/*     */ import org.apache.commons.math3.optimization.PointValuePair;
/*     */ import org.apache.commons.math3.optimization.SimpleBounds;
/*     */ import org.apache.commons.math3.optimization.SimpleValueChecker;
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
/*     */ @Deprecated
/*     */ public abstract class BaseAbstractMultivariateOptimizer<FUNC extends MultivariateFunction>
/*     */   implements BaseMultivariateOptimizer<FUNC>
/*     */ {
/*  50 */   protected final Incrementor evaluations = new Incrementor();
/*     */ 
/*     */   
/*     */   private ConvergenceChecker<PointValuePair> checker;
/*     */ 
/*     */   
/*     */   private GoalType goal;
/*     */ 
/*     */   
/*     */   private double[] start;
/*     */ 
/*     */   
/*     */   private double[] lowerBound;
/*     */   
/*     */   private double[] upperBound;
/*     */   
/*     */   private MultivariateFunction function;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected BaseAbstractMultivariateOptimizer() {
/*  71 */     this((ConvergenceChecker<PointValuePair>)new SimpleValueChecker());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BaseAbstractMultivariateOptimizer(ConvergenceChecker<PointValuePair> checker) {
/*  77 */     this.checker = checker;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEvaluations() {
/*  82 */     return this.evaluations.getMaximalCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEvaluations() {
/*  87 */     return this.evaluations.getCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public ConvergenceChecker<PointValuePair> getConvergenceChecker() {
/*  92 */     return this.checker;
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
/*     */   protected double computeObjectiveValue(double[] point) {
/*     */     try {
/* 105 */       this.evaluations.incrementCount();
/* 106 */     } catch (MaxCountExceededException e) {
/* 107 */       throw new TooManyEvaluationsException(e.getMax());
/*     */     } 
/* 109 */     return this.function.value(point);
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
/*     */   public PointValuePair optimize(int maxEval, FUNC f, GoalType goalType, double[] startPoint) {
/* 122 */     return optimizeInternal(maxEval, f, goalType, new OptimizationData[] { (OptimizationData)new InitialGuess(startPoint) });
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
/*     */   public PointValuePair optimize(int maxEval, FUNC f, GoalType goalType, OptimizationData... optData) {
/* 144 */     return optimizeInternal(maxEval, f, goalType, optData);
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
/*     */   @Deprecated
/*     */   protected PointValuePair optimizeInternal(int maxEval, FUNC f, GoalType goalType, double[] startPoint) {
/* 170 */     return optimizeInternal(maxEval, f, goalType, new OptimizationData[] { (OptimizationData)new InitialGuess(startPoint) });
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
/*     */   protected PointValuePair optimizeInternal(int maxEval, FUNC f, GoalType goalType, OptimizationData... optData) throws TooManyEvaluationsException {
/* 196 */     this.evaluations.setMaximalCount(maxEval);
/* 197 */     this.evaluations.resetCount();
/* 198 */     this.function = (MultivariateFunction)f;
/* 199 */     this.goal = goalType;
/*     */     
/* 201 */     parseOptimizationData(optData);
/*     */     
/* 203 */     checkParameters();
/*     */     
/* 205 */     return doOptimize();
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
/*     */   private void parseOptimizationData(OptimizationData... optData) {
/* 221 */     for (OptimizationData data : optData) {
/* 222 */       if (data instanceof InitialGuess) {
/* 223 */         this.start = ((InitialGuess)data).getInitialGuess();
/*     */       
/*     */       }
/* 226 */       else if (data instanceof SimpleBounds) {
/* 227 */         SimpleBounds bounds = (SimpleBounds)data;
/* 228 */         this.lowerBound = bounds.getLower();
/* 229 */         this.upperBound = bounds.getUpper();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GoalType getGoalType() {
/* 239 */     return this.goal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getStartPoint() {
/* 246 */     return (this.start == null) ? null : (double[])this.start.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getLowerBound() {
/* 253 */     return (this.lowerBound == null) ? null : (double[])this.lowerBound.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getUpperBound() {
/* 260 */     return (this.upperBound == null) ? null : (double[])this.upperBound.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract PointValuePair doOptimize();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkParameters() {
/* 275 */     if (this.start != null) {
/* 276 */       int dim = this.start.length;
/* 277 */       if (this.lowerBound != null) {
/* 278 */         if (this.lowerBound.length != dim) {
/* 279 */           throw new DimensionMismatchException(this.lowerBound.length, dim);
/*     */         }
/* 281 */         for (int i = 0; i < dim; i++) {
/* 282 */           double v = this.start[i];
/* 283 */           double lo = this.lowerBound[i];
/* 284 */           if (v < lo) {
/* 285 */             throw new NumberIsTooSmallException(Double.valueOf(v), Double.valueOf(lo), true);
/*     */           }
/*     */         } 
/*     */       } 
/* 289 */       if (this.upperBound != null) {
/* 290 */         if (this.upperBound.length != dim) {
/* 291 */           throw new DimensionMismatchException(this.upperBound.length, dim);
/*     */         }
/* 293 */         for (int i = 0; i < dim; i++) {
/* 294 */           double v = this.start[i];
/* 295 */           double hi = this.upperBound[i];
/* 296 */           if (v > hi) {
/* 297 */             throw new NumberIsTooLargeException(Double.valueOf(v), Double.valueOf(hi), true);
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 304 */       if (this.lowerBound == null) {
/* 305 */         this.lowerBound = new double[dim];
/* 306 */         for (int i = 0; i < dim; i++) {
/* 307 */           this.lowerBound[i] = Double.NEGATIVE_INFINITY;
/*     */         }
/*     */       } 
/* 310 */       if (this.upperBound == null) {
/* 311 */         this.upperBound = new double[dim];
/* 312 */         for (int i = 0; i < dim; i++)
/* 313 */           this.upperBound[i] = Double.POSITIVE_INFINITY; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/direct/BaseAbstractMultivariateOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */