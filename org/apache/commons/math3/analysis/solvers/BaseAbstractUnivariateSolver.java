/*     */ package org.apache.commons.math3.analysis.solvers;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
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
/*     */ public abstract class BaseAbstractUnivariateSolver<FUNC extends UnivariateFunction>
/*     */   implements BaseUnivariateSolver<FUNC>
/*     */ {
/*     */   private static final double DEFAULT_RELATIVE_ACCURACY = 1.0E-14D;
/*     */   private static final double DEFAULT_FUNCTION_VALUE_ACCURACY = 1.0E-15D;
/*     */   private final double functionValueAccuracy;
/*     */   private final double absoluteAccuracy;
/*     */   private final double relativeAccuracy;
/*     */   private IntegerSequence.Incrementor evaluations;
/*     */   private double searchMin;
/*     */   private double searchMax;
/*     */   private double searchStart;
/*     */   private FUNC function;
/*     */   
/*     */   protected BaseAbstractUnivariateSolver(double absoluteAccuracy) {
/*  70 */     this(1.0E-14D, absoluteAccuracy, 1.0E-15D);
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
/*     */   protected BaseAbstractUnivariateSolver(double relativeAccuracy, double absoluteAccuracy) {
/*  83 */     this(relativeAccuracy, absoluteAccuracy, 1.0E-15D);
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
/*     */   protected BaseAbstractUnivariateSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
/*  98 */     this.absoluteAccuracy = absoluteAccuracy;
/*  99 */     this.relativeAccuracy = relativeAccuracy;
/* 100 */     this.functionValueAccuracy = functionValueAccuracy;
/* 101 */     this.evaluations = IntegerSequence.Incrementor.create();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEvaluations() {
/* 106 */     return this.evaluations.getMaximalCount();
/*     */   }
/*     */   
/*     */   public int getEvaluations() {
/* 110 */     return this.evaluations.getCount();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMin() {
/* 116 */     return this.searchMin;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMax() {
/* 122 */     return this.searchMax;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getStartValue() {
/* 128 */     return this.searchStart;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAbsoluteAccuracy() {
/* 134 */     return this.absoluteAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRelativeAccuracy() {
/* 140 */     return this.relativeAccuracy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFunctionValueAccuracy() {
/* 146 */     return this.functionValueAccuracy;
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
/* 159 */     incrementEvaluationCount();
/* 160 */     return this.function.value(point);
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
/*     */   protected void setup(int maxEval, FUNC f, double min, double max, double startValue) throws NullArgumentException {
/* 181 */     MathUtils.checkNotNull(f);
/*     */ 
/*     */     
/* 184 */     this.searchMin = min;
/* 185 */     this.searchMax = max;
/* 186 */     this.searchStart = startValue;
/* 187 */     this.function = f;
/* 188 */     this.evaluations = this.evaluations.withMaximalCount(maxEval).withStart(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double solve(int maxEval, FUNC f, double min, double max, double startValue) throws TooManyEvaluationsException, NoBracketingException {
/* 196 */     setup(maxEval, f, min, max, startValue);
/*     */ 
/*     */     
/* 199 */     return doSolve();
/*     */   }
/*     */ 
/*     */   
/*     */   public double solve(int maxEval, FUNC f, double min, double max) {
/* 204 */     return solve(maxEval, f, min, max, min + 0.5D * (max - min));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double solve(int maxEval, FUNC f, double startValue) throws TooManyEvaluationsException, NoBracketingException {
/* 211 */     return solve(maxEval, f, Double.NaN, Double.NaN, startValue);
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
/*     */   protected abstract double doSolve() throws TooManyEvaluationsException, NoBracketingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isBracketing(double lower, double upper) {
/* 237 */     return UnivariateSolverUtils.isBracketing((UnivariateFunction)this.function, lower, upper);
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
/*     */   protected boolean isSequence(double start, double mid, double end) {
/* 251 */     return UnivariateSolverUtils.isSequence(start, mid, end);
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
/*     */   protected void verifyInterval(double lower, double upper) throws NumberIsTooLargeException {
/* 264 */     UnivariateSolverUtils.verifyInterval(lower, upper);
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
/*     */   protected void verifySequence(double lower, double initial, double upper) throws NumberIsTooLargeException {
/* 280 */     UnivariateSolverUtils.verifySequence(lower, initial, upper);
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
/*     */   protected void verifyBracketing(double lower, double upper) throws NullArgumentException, NoBracketingException {
/* 297 */     UnivariateSolverUtils.verifyBracketing((UnivariateFunction)this.function, lower, upper);
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
/*     */   protected void incrementEvaluationCount() throws TooManyEvaluationsException {
/*     */     try {
/* 313 */       this.evaluations.increment();
/* 314 */     } catch (MaxCountExceededException e) {
/* 315 */       throw new TooManyEvaluationsException(e.getMax());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/BaseAbstractUnivariateSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */