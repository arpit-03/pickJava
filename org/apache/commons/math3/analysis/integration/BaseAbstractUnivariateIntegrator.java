/*     */ package org.apache.commons.math3.analysis.integration;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.solvers.UnivariateSolverUtils;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.util.Incrementor;
/*     */ import org.apache.commons.math3.util.IntegerSequence;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseAbstractUnivariateIntegrator
/*     */   implements UnivariateIntegrator
/*     */ {
/*     */   public static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-15D;
/*     */   public static final double DEFAULT_RELATIVE_ACCURACY = 1.0E-6D;
/*     */   public static final int DEFAULT_MIN_ITERATIONS_COUNT = 3;
/*     */   public static final int DEFAULT_MAX_ITERATIONS_COUNT = 2147483647;
/*     */   @Deprecated
/*     */   protected Incrementor iterations;
/*     */   private IntegerSequence.Incrementor count;
/*     */   private final double absoluteAccuracy;
/*     */   private final double relativeAccuracy;
/*     */   private final int minimalIterationCount;
/*     */   private IntegerSequence.Incrementor evaluations;
/*     */   private UnivariateFunction function;
/*     */   private double min;
/*     */   private double max;
/*     */   
/*     */   protected BaseAbstractUnivariateIntegrator(double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException {
/* 121 */     this.relativeAccuracy = relativeAccuracy;
/* 122 */     this.absoluteAccuracy = absoluteAccuracy;
/*     */ 
/*     */     
/* 125 */     if (minimalIterationCount <= 0) {
/* 126 */       throw new NotStrictlyPositiveException(Integer.valueOf(minimalIterationCount));
/*     */     }
/* 128 */     if (maximalIterationCount <= minimalIterationCount) {
/* 129 */       throw new NumberIsTooSmallException(Integer.valueOf(maximalIterationCount), Integer.valueOf(minimalIterationCount), false);
/*     */     }
/* 131 */     this.minimalIterationCount = minimalIterationCount;
/* 132 */     this.count = IntegerSequence.Incrementor.create().withMaximalCount(maximalIterationCount);
/*     */ 
/*     */     
/* 135 */     Incrementor wrapped = Incrementor.wrap(this.count);
/*     */     
/* 137 */     this.iterations = wrapped;
/*     */ 
/*     */     
/* 140 */     this.evaluations = IntegerSequence.Incrementor.create();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BaseAbstractUnivariateIntegrator(double relativeAccuracy, double absoluteAccuracy) {
/* 151 */     this(relativeAccuracy, absoluteAccuracy, 3, 2147483647);
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
/*     */   protected BaseAbstractUnivariateIntegrator(int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException {
/* 167 */     this(1.0E-6D, 1.0E-15D, minimalIterationCount, maximalIterationCount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRelativeAccuracy() {
/* 173 */     return this.relativeAccuracy;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getAbsoluteAccuracy() {
/* 178 */     return this.absoluteAccuracy;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinimalIterationCount() {
/* 183 */     return this.minimalIterationCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaximalIterationCount() {
/* 188 */     return this.count.getMaximalCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEvaluations() {
/* 193 */     return this.evaluations.getCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIterations() {
/* 198 */     return this.count.getCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void incrementCount() throws MaxCountExceededException {
/* 206 */     this.count.increment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getMin() {
/* 213 */     return this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getMax() {
/* 219 */     return this.max;
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
/*     */   protected double computeObjectiveValue(double point) throws TooManyEvaluationsException {
/*     */     try {
/* 233 */       this.evaluations.increment();
/* 234 */     } catch (MaxCountExceededException e) {
/* 235 */       throw new TooManyEvaluationsException(e.getMax());
/*     */     } 
/* 237 */     return this.function.value(point);
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
/*     */   protected void setup(int maxEval, UnivariateFunction f, double lower, double upper) throws NullArgumentException, MathIllegalArgumentException {
/* 258 */     MathUtils.checkNotNull(f);
/* 259 */     UnivariateSolverUtils.verifyInterval(lower, upper);
/*     */ 
/*     */     
/* 262 */     this.min = lower;
/* 263 */     this.max = upper;
/* 264 */     this.function = f;
/* 265 */     this.evaluations = this.evaluations.withMaximalCount(maxEval).withStart(0);
/* 266 */     this.count = this.count.withStart(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double integrate(int maxEval, UnivariateFunction f, double lower, double upper) throws TooManyEvaluationsException, MaxCountExceededException, MathIllegalArgumentException, NullArgumentException {
/* 277 */     setup(maxEval, f, lower, upper);
/*     */ 
/*     */     
/* 280 */     return doIntegrate();
/*     */   }
/*     */   
/*     */   protected abstract double doIntegrate() throws TooManyEvaluationsException, MaxCountExceededException;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/integration/BaseAbstractUnivariateIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */