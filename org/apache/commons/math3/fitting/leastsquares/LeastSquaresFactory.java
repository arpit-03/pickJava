/*     */ package org.apache.commons.math3.fitting.leastsquares;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
/*     */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*     */ import org.apache.commons.math3.linear.ArrayRealVector;
/*     */ import org.apache.commons.math3.linear.DiagonalMatrix;
/*     */ import org.apache.commons.math3.linear.EigenDecomposition;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.optim.AbstractOptimizationProblem;
/*     */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optim.PointVectorValuePair;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Incrementor;
/*     */ import org.apache.commons.math3.util.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LeastSquaresFactory
/*     */ {
/*     */   public static LeastSquaresProblem create(MultivariateJacobianFunction model, RealVector observed, RealVector start, RealMatrix weight, ConvergenceChecker<LeastSquaresProblem.Evaluation> checker, int maxEvaluations, int maxIterations, boolean lazyEvaluation, ParameterValidator paramValidator) {
/*  74 */     LeastSquaresProblem p = new LocalLeastSquaresProblem(model, observed, start, checker, maxEvaluations, maxIterations, lazyEvaluation, paramValidator);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     if (weight != null) {
/*  83 */       return weightMatrix(p, weight);
/*     */     }
/*  85 */     return p;
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
/*     */   public static LeastSquaresProblem create(MultivariateJacobianFunction model, RealVector observed, RealVector start, ConvergenceChecker<LeastSquaresProblem.Evaluation> checker, int maxEvaluations, int maxIterations) {
/* 107 */     return create(model, observed, start, null, checker, maxEvaluations, maxIterations, false, null);
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
/*     */   public static LeastSquaresProblem create(MultivariateJacobianFunction model, RealVector observed, RealVector start, RealMatrix weight, ConvergenceChecker<LeastSquaresProblem.Evaluation> checker, int maxEvaluations, int maxIterations) {
/* 138 */     return weightMatrix(create(model, observed, start, checker, maxEvaluations, maxIterations), weight);
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
/*     */   
/*     */   public static LeastSquaresProblem create(MultivariateVectorFunction model, MultivariateMatrixFunction jacobian, double[] observed, double[] start, RealMatrix weight, ConvergenceChecker<LeastSquaresProblem.Evaluation> checker, int maxEvaluations, int maxIterations) {
/* 174 */     return create(model(model, jacobian), (RealVector)new ArrayRealVector(observed, false), (RealVector)new ArrayRealVector(start, false), weight, checker, maxEvaluations, maxIterations);
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
/*     */   public static LeastSquaresProblem weightMatrix(LeastSquaresProblem problem, RealMatrix weights) {
/* 193 */     final RealMatrix weightSquareRoot = squareRoot(weights);
/* 194 */     return new LeastSquaresAdapter(problem)
/*     */       {
/*     */         public LeastSquaresProblem.Evaluation evaluate(RealVector point)
/*     */         {
/* 198 */           return new DenseWeightedEvaluation(super.evaluate(point), weightSquareRoot);
/*     */         }
/*     */       };
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
/*     */   public static LeastSquaresProblem weightDiagonal(LeastSquaresProblem problem, RealVector weights) {
/* 214 */     return weightMatrix(problem, (RealMatrix)new DiagonalMatrix(weights.toArray()));
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
/*     */   public static LeastSquaresProblem countEvaluations(LeastSquaresProblem problem, final Incrementor counter) {
/* 228 */     return new LeastSquaresAdapter(problem)
/*     */       {
/*     */         
/*     */         public LeastSquaresProblem.Evaluation evaluate(RealVector point)
/*     */         {
/* 233 */           counter.incrementCount();
/* 234 */           return super.evaluate(point);
/*     */         }
/*     */       };
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
/*     */   public static ConvergenceChecker<LeastSquaresProblem.Evaluation> evaluationChecker(final ConvergenceChecker<PointVectorValuePair> checker) {
/* 249 */     return new ConvergenceChecker<LeastSquaresProblem.Evaluation>()
/*     */       {
/*     */         
/*     */         public boolean converged(int iteration, LeastSquaresProblem.Evaluation previous, LeastSquaresProblem.Evaluation current)
/*     */         {
/* 254 */           return checker.converged(iteration, new PointVectorValuePair(previous.getPoint().toArray(), previous.getResiduals().toArray(), false), new PointVectorValuePair(current.getPoint().toArray(), current.getResiduals().toArray(), false));
/*     */         }
/*     */       };
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
/*     */   private static RealMatrix squareRoot(RealMatrix m) {
/* 276 */     if (m instanceof DiagonalMatrix) {
/* 277 */       int dim = m.getRowDimension();
/* 278 */       DiagonalMatrix diagonalMatrix = new DiagonalMatrix(dim);
/* 279 */       for (int i = 0; i < dim; i++) {
/* 280 */         diagonalMatrix.setEntry(i, i, FastMath.sqrt(m.getEntry(i, i)));
/*     */       }
/* 282 */       return (RealMatrix)diagonalMatrix;
/*     */     } 
/* 284 */     EigenDecomposition dec = new EigenDecomposition(m);
/* 285 */     return dec.getSquareRoot();
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
/*     */   public static MultivariateJacobianFunction model(MultivariateVectorFunction value, MultivariateMatrixFunction jacobian) {
/* 299 */     return new LocalValueAndJacobianFunction(value, jacobian);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LocalValueAndJacobianFunction
/*     */     implements ValueAndJacobianFunction
/*     */   {
/*     */     private final MultivariateVectorFunction value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final MultivariateMatrixFunction jacobian;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LocalValueAndJacobianFunction(MultivariateVectorFunction value, MultivariateMatrixFunction jacobian) {
/* 323 */       this.value = value;
/* 324 */       this.jacobian = jacobian;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Pair<RealVector, RealMatrix> value(RealVector point) {
/* 330 */       double[] p = point.toArray();
/*     */ 
/*     */       
/* 333 */       return new Pair(computeValue(p), computeJacobian(p));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public RealVector computeValue(double[] params) {
/* 339 */       return (RealVector)new ArrayRealVector(this.value.value(params), false);
/*     */     }
/*     */ 
/*     */     
/*     */     public RealMatrix computeJacobian(double[] params) {
/* 344 */       return (RealMatrix)new Array2DRowRealMatrix(this.jacobian.value(params), false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LocalLeastSquaresProblem
/*     */     extends AbstractOptimizationProblem<LeastSquaresProblem.Evaluation>
/*     */     implements LeastSquaresProblem
/*     */   {
/*     */     private final RealVector target;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final MultivariateJacobianFunction model;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final RealVector start;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean lazyEvaluation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final ParameterValidator paramValidator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LocalLeastSquaresProblem(MultivariateJacobianFunction model, RealVector target, RealVector start, ConvergenceChecker<LeastSquaresProblem.Evaluation> checker, int maxEvaluations, int maxIterations, boolean lazyEvaluation, ParameterValidator paramValidator) {
/* 390 */       super(maxEvaluations, maxIterations, checker);
/* 391 */       this.target = target;
/* 392 */       this.model = model;
/* 393 */       this.start = start;
/* 394 */       this.lazyEvaluation = lazyEvaluation;
/* 395 */       this.paramValidator = paramValidator;
/*     */       
/* 397 */       if (lazyEvaluation && !(model instanceof ValueAndJacobianFunction))
/*     */       {
/*     */ 
/*     */         
/* 401 */         throw new MathIllegalStateException(LocalizedFormats.INVALID_IMPLEMENTATION, new Object[] { model.getClass().getName() });
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getObservationSize() {
/* 408 */       return this.target.getDimension();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getParameterSize() {
/* 413 */       return this.start.getDimension();
/*     */     }
/*     */ 
/*     */     
/*     */     public RealVector getStart() {
/* 418 */       return (this.start == null) ? null : this.start.copy();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public LeastSquaresProblem.Evaluation evaluate(RealVector point) {
/* 424 */       RealVector p = (this.paramValidator == null) ? point.copy() : this.paramValidator.validate(point.copy());
/*     */ 
/*     */ 
/*     */       
/* 428 */       if (this.lazyEvaluation) {
/* 429 */         return new LazyUnweightedEvaluation((ValueAndJacobianFunction)this.model, this.target, p);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 434 */       Pair<RealVector, RealMatrix> value = this.model.value(p);
/* 435 */       return new UnweightedEvaluation((RealVector)value.getFirst(), (RealMatrix)value.getSecond(), this.target, p);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static class UnweightedEvaluation
/*     */       extends AbstractEvaluation
/*     */     {
/*     */       private final RealVector point;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private final RealMatrix jacobian;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private final RealVector residuals;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private UnweightedEvaluation(RealVector values, RealMatrix jacobian, RealVector target, RealVector point) {
/* 465 */         super(target.getDimension());
/* 466 */         this.jacobian = jacobian;
/* 467 */         this.point = point;
/* 468 */         this.residuals = target.subtract(values);
/*     */       }
/*     */ 
/*     */       
/*     */       public RealMatrix getJacobian() {
/* 473 */         return this.jacobian;
/*     */       }
/*     */ 
/*     */       
/*     */       public RealVector getPoint() {
/* 478 */         return this.point;
/*     */       }
/*     */ 
/*     */       
/*     */       public RealVector getResiduals() {
/* 483 */         return this.residuals;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static class LazyUnweightedEvaluation
/*     */       extends AbstractEvaluation
/*     */     {
/*     */       private final RealVector point;
/*     */ 
/*     */ 
/*     */       
/*     */       private final ValueAndJacobianFunction model;
/*     */ 
/*     */ 
/*     */       
/*     */       private final RealVector target;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private LazyUnweightedEvaluation(ValueAndJacobianFunction model, RealVector target, RealVector point) {
/* 508 */         super(target.getDimension());
/*     */         
/* 510 */         this.model = model;
/* 511 */         this.point = point;
/* 512 */         this.target = target;
/*     */       }
/*     */ 
/*     */       
/*     */       public RealMatrix getJacobian() {
/* 517 */         return this.model.computeJacobian(this.point.toArray());
/*     */       }
/*     */ 
/*     */       
/*     */       public RealVector getPoint() {
/* 522 */         return this.point;
/*     */       }
/*     */ 
/*     */       
/*     */       public RealVector getResiduals() {
/* 527 */         return this.target.subtract(this.model.computeValue(this.point.toArray()));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/leastsquares/LeastSquaresFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */