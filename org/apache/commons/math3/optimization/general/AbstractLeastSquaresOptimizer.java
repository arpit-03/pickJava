/*     */ package org.apache.commons.math3.optimization.general;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.DifferentiableMultivariateVectorFunction;
/*     */ import org.apache.commons.math3.analysis.FunctionUtils;
/*     */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
/*     */ import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
/*     */ import org.apache.commons.math3.analysis.differentiation.MultivariateDifferentiableVectorFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.linear.ArrayRealVector;
/*     */ import org.apache.commons.math3.linear.DecompositionSolver;
/*     */ import org.apache.commons.math3.linear.DiagonalMatrix;
/*     */ import org.apache.commons.math3.linear.EigenDecomposition;
/*     */ import org.apache.commons.math3.linear.MatrixUtils;
/*     */ import org.apache.commons.math3.linear.QRDecomposition;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optimization.DifferentiableMultivariateVectorOptimizer;
/*     */ import org.apache.commons.math3.optimization.InitialGuess;
/*     */ import org.apache.commons.math3.optimization.OptimizationData;
/*     */ import org.apache.commons.math3.optimization.PointVectorValuePair;
/*     */ import org.apache.commons.math3.optimization.Target;
/*     */ import org.apache.commons.math3.optimization.Weight;
/*     */ import org.apache.commons.math3.optimization.direct.BaseAbstractMultivariateVectorOptimizer;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class AbstractLeastSquaresOptimizer
/*     */   extends BaseAbstractMultivariateVectorOptimizer<DifferentiableMultivariateVectorFunction>
/*     */   implements DifferentiableMultivariateVectorOptimizer
/*     */ {
/*     */   @Deprecated
/*     */   private static final double DEFAULT_SINGULARITY_THRESHOLD = 1.0E-14D;
/*     */   @Deprecated
/*     */   protected double[][] weightedResidualJacobian;
/*     */   @Deprecated
/*     */   protected int cols;
/*     */   @Deprecated
/*     */   protected int rows;
/*     */   @Deprecated
/*     */   protected double[] point;
/*     */   @Deprecated
/*     */   protected double[] objective;
/*     */   @Deprecated
/*     */   protected double[] weightedResiduals;
/*     */   @Deprecated
/*     */   protected double cost;
/*     */   private MultivariateDifferentiableVectorFunction jF;
/*     */   private int jacobianEvaluations;
/*     */   private RealMatrix weightMatrixSqrt;
/*     */   
/*     */   @Deprecated
/*     */   protected AbstractLeastSquaresOptimizer() {}
/*     */   
/*     */   protected AbstractLeastSquaresOptimizer(ConvergenceChecker<PointVectorValuePair> checker) {
/* 134 */     super(checker);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getJacobianEvaluations() {
/* 141 */     return this.jacobianEvaluations;
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
/*     */   protected void updateJacobian() {
/* 154 */     RealMatrix weightedJacobian = computeWeightedJacobian(this.point);
/* 155 */     this.weightedResidualJacobian = weightedJacobian.scalarMultiply(-1.0D).getData();
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
/*     */   protected RealMatrix computeWeightedJacobian(double[] params) {
/* 168 */     this.jacobianEvaluations++;
/*     */     
/* 170 */     DerivativeStructure[] dsPoint = new DerivativeStructure[params.length];
/* 171 */     int nC = params.length;
/* 172 */     for (int i = 0; i < nC; i++) {
/* 173 */       dsPoint[i] = new DerivativeStructure(nC, 1, i, params[i]);
/*     */     }
/* 175 */     DerivativeStructure[] dsValue = this.jF.value(dsPoint);
/* 176 */     int nR = (getTarget()).length;
/* 177 */     if (dsValue.length != nR) {
/* 178 */       throw new DimensionMismatchException(dsValue.length, nR);
/*     */     }
/* 180 */     double[][] jacobianData = new double[nR][nC];
/* 181 */     for (int j = 0; j < nR; j++) {
/* 182 */       int[] orders = new int[nC];
/* 183 */       for (int k = 0; k < nC; k++) {
/* 184 */         orders[k] = 1;
/* 185 */         jacobianData[j][k] = dsValue[j].getPartialDerivative(orders);
/* 186 */         orders[k] = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     return this.weightMatrixSqrt.multiply(MatrixUtils.createRealMatrix(jacobianData));
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
/*     */   @Deprecated
/*     */   protected void updateResidualsAndCost() {
/* 205 */     this.objective = computeObjectiveValue(this.point);
/* 206 */     double[] res = computeResiduals(this.objective);
/*     */ 
/*     */     
/* 209 */     this.cost = computeCost(res);
/*     */ 
/*     */     
/* 212 */     ArrayRealVector residuals = new ArrayRealVector(res);
/* 213 */     this.weightedResiduals = this.weightMatrixSqrt.operate((RealVector)residuals).toArray();
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
/*     */   protected double computeCost(double[] residuals) {
/* 225 */     ArrayRealVector r = new ArrayRealVector(residuals);
/* 226 */     return FastMath.sqrt(r.dotProduct(getWeight().operate((RealVector)r)));
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
/*     */   public double getRMS() {
/* 240 */     return FastMath.sqrt(getChiSquare() / this.rows);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getChiSquare() {
/* 250 */     return this.cost * this.cost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getWeightSquareRoot() {
/* 260 */     return this.weightMatrixSqrt.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setCost(double cost) {
/* 270 */     this.cost = cost;
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
/*     */   @Deprecated
/*     */   public double[][] getCovariances() {
/* 285 */     return getCovariances(1.0E-14D);
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
/*     */   @Deprecated
/*     */   public double[][] getCovariances(double threshold) {
/* 307 */     return computeCovariances(this.point, threshold);
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
/*     */   public double[][] computeCovariances(double[] params, double threshold) {
/* 330 */     RealMatrix j = computeWeightedJacobian(params);
/*     */ 
/*     */     
/* 333 */     RealMatrix jTj = j.transpose().multiply(j);
/*     */ 
/*     */     
/* 336 */     DecompositionSolver solver = (new QRDecomposition(jTj, threshold)).getSolver();
/*     */     
/* 338 */     return solver.getInverse().getData();
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
/*     */   @Deprecated
/*     */   public double[] guessParametersErrors() {
/* 372 */     if (this.rows <= this.cols) {
/* 373 */       throw new NumberIsTooSmallException(LocalizedFormats.NO_DEGREES_OF_FREEDOM, Integer.valueOf(this.rows), Integer.valueOf(this.cols), false);
/*     */     }
/*     */     
/* 376 */     double[] errors = new double[this.cols];
/* 377 */     double c = FastMath.sqrt(getChiSquare() / (this.rows - this.cols));
/* 378 */     double[][] covar = computeCovariances(this.point, 1.0E-14D);
/* 379 */     for (int i = 0; i < errors.length; i++) {
/* 380 */       errors[i] = FastMath.sqrt(covar[i][i]) * c;
/*     */     }
/* 382 */     return errors;
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
/*     */   public double[] computeSigma(double[] params, double covarianceSingularityThreshold) {
/* 402 */     int nC = params.length;
/* 403 */     double[] sig = new double[nC];
/* 404 */     double[][] cov = computeCovariances(params, covarianceSingularityThreshold);
/* 405 */     for (int i = 0; i < nC; i++) {
/* 406 */       sig[i] = FastMath.sqrt(cov[i][i]);
/*     */     }
/* 408 */     return sig;
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
/*     */   @Deprecated
/*     */   public PointVectorValuePair optimize(int maxEval, DifferentiableMultivariateVectorFunction f, double[] target, double[] weights, double[] startPoint) {
/* 424 */     return optimizeInternal(maxEval, FunctionUtils.toMultivariateDifferentiableVectorFunction(f), new OptimizationData[] { (OptimizationData)new Target(target), (OptimizationData)new Weight(weights), (OptimizationData)new InitialGuess(startPoint) });
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
/*     */   @Deprecated
/*     */   public PointVectorValuePair optimize(int maxEval, MultivariateDifferentiableVectorFunction f, double[] target, double[] weights, double[] startPoint) {
/* 461 */     return optimizeInternal(maxEval, f, new OptimizationData[] { (OptimizationData)new Target(target), (OptimizationData)new Weight(weights), (OptimizationData)new InitialGuess(startPoint) });
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
/*     */   
/*     */   @Deprecated
/*     */   protected PointVectorValuePair optimizeInternal(int maxEval, MultivariateDifferentiableVectorFunction f, OptimizationData... optData) {
/* 499 */     return optimizeInternal(maxEval, (MultivariateVectorFunction)FunctionUtils.toDifferentiableMultivariateVectorFunction(f), optData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setUp() {
/* 505 */     super.setUp();
/*     */ 
/*     */     
/* 508 */     this.jacobianEvaluations = 0;
/*     */ 
/*     */     
/* 511 */     this.weightMatrixSqrt = squareRoot(getWeight());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 519 */     this.jF = FunctionUtils.toMultivariateDifferentiableVectorFunction((DifferentiableMultivariateVectorFunction)getObjectiveFunction());
/*     */ 
/*     */     
/* 522 */     this.point = getStartPoint();
/* 523 */     this.rows = (getTarget()).length;
/* 524 */     this.cols = this.point.length;
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
/*     */   protected double[] computeResiduals(double[] objectiveValue) {
/* 544 */     double[] target = getTarget();
/* 545 */     if (objectiveValue.length != target.length) {
/* 546 */       throw new DimensionMismatchException(target.length, objectiveValue.length);
/*     */     }
/*     */ 
/*     */     
/* 550 */     double[] residuals = new double[target.length];
/* 551 */     for (int i = 0; i < target.length; i++) {
/* 552 */       residuals[i] = target[i] - objectiveValue[i];
/*     */     }
/*     */     
/* 555 */     return residuals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RealMatrix squareRoot(RealMatrix m) {
/* 565 */     if (m instanceof DiagonalMatrix) {
/* 566 */       int dim = m.getRowDimension();
/* 567 */       DiagonalMatrix diagonalMatrix = new DiagonalMatrix(dim);
/* 568 */       for (int i = 0; i < dim; i++) {
/* 569 */         diagonalMatrix.setEntry(i, i, FastMath.sqrt(m.getEntry(i, i)));
/*     */       }
/* 571 */       return (RealMatrix)diagonalMatrix;
/*     */     } 
/* 573 */     EigenDecomposition dec = new EigenDecomposition(m);
/* 574 */     return dec.getSquareRoot();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/general/AbstractLeastSquaresOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */