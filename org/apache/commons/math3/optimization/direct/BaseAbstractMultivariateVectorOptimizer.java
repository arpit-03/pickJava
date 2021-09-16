/*     */ package org.apache.commons.math3.optimization.direct;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.optimization.BaseMultivariateVectorOptimizer;
/*     */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optimization.InitialGuess;
/*     */ import org.apache.commons.math3.optimization.OptimizationData;
/*     */ import org.apache.commons.math3.optimization.PointVectorValuePair;
/*     */ import org.apache.commons.math3.optimization.SimpleVectorValueChecker;
/*     */ import org.apache.commons.math3.optimization.Target;
/*     */ import org.apache.commons.math3.optimization.Weight;
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
/*     */ public abstract class BaseAbstractMultivariateVectorOptimizer<FUNC extends MultivariateVectorFunction>
/*     */   implements BaseMultivariateVectorOptimizer<FUNC>
/*     */ {
/*  50 */   protected final Incrementor evaluations = new Incrementor();
/*     */ 
/*     */   
/*     */   private ConvergenceChecker<PointVectorValuePair> checker;
/*     */ 
/*     */   
/*     */   private double[] target;
/*     */ 
/*     */   
/*     */   private RealMatrix weightMatrix;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   private double[] weight;
/*     */ 
/*     */   
/*     */   private double[] start;
/*     */ 
/*     */   
/*     */   private FUNC function;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected BaseAbstractMultivariateVectorOptimizer() {
/*  74 */     this((ConvergenceChecker<PointVectorValuePair>)new SimpleVectorValueChecker());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BaseAbstractMultivariateVectorOptimizer(ConvergenceChecker<PointVectorValuePair> checker) {
/*  80 */     this.checker = checker;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEvaluations() {
/*  85 */     return this.evaluations.getMaximalCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEvaluations() {
/*  90 */     return this.evaluations.getCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public ConvergenceChecker<PointVectorValuePair> getConvergenceChecker() {
/*  95 */     return this.checker;
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
/*     */   protected double[] computeObjectiveValue(double[] point) {
/*     */     try {
/* 108 */       this.evaluations.incrementCount();
/* 109 */     } catch (MaxCountExceededException e) {
/* 110 */       throw new TooManyEvaluationsException(e.getMax());
/*     */     } 
/* 112 */     return this.function.value(point);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PointVectorValuePair optimize(int maxEval, FUNC f, double[] t, double[] w, double[] startPoint) {
/* 124 */     return optimizeInternal(maxEval, f, t, w, startPoint);
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
/*     */   protected PointVectorValuePair optimize(int maxEval, FUNC f, OptimizationData... optData) throws TooManyEvaluationsException, DimensionMismatchException {
/* 152 */     return optimizeInternal(maxEval, f, optData);
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
/*     */   @Deprecated
/*     */   protected PointVectorValuePair optimizeInternal(int maxEval, FUNC f, double[] t, double[] w, double[] startPoint) {
/* 183 */     if (f == null) {
/* 184 */       throw new NullArgumentException();
/*     */     }
/* 186 */     if (t == null) {
/* 187 */       throw new NullArgumentException();
/*     */     }
/* 189 */     if (w == null) {
/* 190 */       throw new NullArgumentException();
/*     */     }
/* 192 */     if (startPoint == null) {
/* 193 */       throw new NullArgumentException();
/*     */     }
/* 195 */     if (t.length != w.length) {
/* 196 */       throw new DimensionMismatchException(t.length, w.length);
/*     */     }
/*     */     
/* 199 */     return optimizeInternal(maxEval, f, new OptimizationData[] { (OptimizationData)new Target(t), (OptimizationData)new Weight(w), (OptimizationData)new InitialGuess(startPoint) });
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
/*     */   protected PointVectorValuePair optimizeInternal(int maxEval, FUNC f, OptimizationData... optData) throws TooManyEvaluationsException, DimensionMismatchException {
/* 231 */     this.evaluations.setMaximalCount(maxEval);
/* 232 */     this.evaluations.resetCount();
/* 233 */     this.function = f;
/*     */     
/* 235 */     parseOptimizationData(optData);
/*     */     
/* 237 */     checkParameters();
/*     */     
/* 239 */     setUp();
/*     */     
/* 241 */     return doOptimize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getStartPoint() {
/* 250 */     return (double[])this.start.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getWeight() {
/* 260 */     return this.weightMatrix.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getTarget() {
/* 270 */     return (double[])this.target.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FUNC getObjectiveFunction() {
/* 281 */     return this.function;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract PointVectorValuePair doOptimize();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected double[] getTargetRef() {
/* 298 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected double[] getWeightRef() {
/* 306 */     return this.weight;
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
/*     */   protected void setUp() {
/* 321 */     int dim = this.target.length;
/* 322 */     this.weight = new double[dim];
/* 323 */     for (int i = 0; i < dim; i++) {
/* 324 */       this.weight[i] = this.weightMatrix.getEntry(i, i);
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
/*     */   private void parseOptimizationData(OptimizationData... optData) {
/* 342 */     for (OptimizationData data : optData) {
/* 343 */       if (data instanceof Target) {
/* 344 */         this.target = ((Target)data).getTarget();
/*     */       
/*     */       }
/* 347 */       else if (data instanceof Weight) {
/* 348 */         this.weightMatrix = ((Weight)data).getWeight();
/*     */       
/*     */       }
/* 351 */       else if (data instanceof InitialGuess) {
/* 352 */         this.start = ((InitialGuess)data).getInitialGuess();
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
/*     */   private void checkParameters() {
/* 365 */     if (this.target.length != this.weightMatrix.getColumnDimension())
/* 366 */       throw new DimensionMismatchException(this.target.length, this.weightMatrix.getColumnDimension()); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/direct/BaseAbstractMultivariateVectorOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */