/*     */ package org.apache.commons.math3.optim.nonlinear.vector.jacobian;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.linear.ArrayRealVector;
/*     */ import org.apache.commons.math3.linear.DecompositionSolver;
/*     */ import org.apache.commons.math3.linear.DiagonalMatrix;
/*     */ import org.apache.commons.math3.linear.EigenDecomposition;
/*     */ import org.apache.commons.math3.linear.MatrixUtils;
/*     */ import org.apache.commons.math3.linear.QRDecomposition;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optim.OptimizationData;
/*     */ import org.apache.commons.math3.optim.PointVectorValuePair;
/*     */ import org.apache.commons.math3.optim.nonlinear.vector.JacobianMultivariateVectorOptimizer;
/*     */ import org.apache.commons.math3.optim.nonlinear.vector.Weight;
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
/*     */ @Deprecated
/*     */ public abstract class AbstractLeastSquaresOptimizer
/*     */   extends JacobianMultivariateVectorOptimizer
/*     */ {
/*     */   private RealMatrix weightMatrixSqrt;
/*     */   private double cost;
/*     */   
/*     */   protected AbstractLeastSquaresOptimizer(ConvergenceChecker<PointVectorValuePair> checker) {
/*  57 */     super(checker);
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
/*     */   protected RealMatrix computeWeightedJacobian(double[] params) {
/*  69 */     return this.weightMatrixSqrt.multiply(MatrixUtils.createRealMatrix(computeJacobian(params)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double computeCost(double[] residuals) {
/*  80 */     ArrayRealVector r = new ArrayRealVector(residuals);
/*  81 */     return FastMath.sqrt(r.dotProduct(getWeight().operate((RealVector)r)));
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
/*     */   public double getRMS() {
/*  96 */     return FastMath.sqrt(getChiSquare() / getTargetSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getChiSquare() {
/* 106 */     return this.cost * this.cost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getWeightSquareRoot() {
/* 115 */     return this.weightMatrixSqrt.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setCost(double cost) {
/* 124 */     this.cost = cost;
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
/*     */   public double[][] computeCovariances(double[] params, double threshold) {
/* 146 */     RealMatrix j = computeWeightedJacobian(params);
/*     */ 
/*     */     
/* 149 */     RealMatrix jTj = j.transpose().multiply(j);
/*     */ 
/*     */     
/* 152 */     DecompositionSolver solver = (new QRDecomposition(jTj, threshold)).getSolver();
/*     */     
/* 154 */     return solver.getInverse().getData();
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
/*     */   public double[] computeSigma(double[] params, double covarianceSingularityThreshold) {
/* 173 */     int nC = params.length;
/* 174 */     double[] sig = new double[nC];
/* 175 */     double[][] cov = computeCovariances(params, covarianceSingularityThreshold);
/* 176 */     for (int i = 0; i < nC; i++) {
/* 177 */       sig[i] = FastMath.sqrt(cov[i][i]);
/*     */     }
/* 179 */     return sig;
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
/*     */   public PointVectorValuePair optimize(OptimizationData... optData) throws TooManyEvaluationsException {
/* 201 */     return super.optimize(optData);
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
/*     */   protected double[] computeResiduals(double[] objectiveValue) {
/* 220 */     double[] target = getTarget();
/* 221 */     if (objectiveValue.length != target.length) {
/* 222 */       throw new DimensionMismatchException(target.length, objectiveValue.length);
/*     */     }
/*     */ 
/*     */     
/* 226 */     double[] residuals = new double[target.length];
/* 227 */     for (int i = 0; i < target.length; i++) {
/* 228 */       residuals[i] = target[i] - objectiveValue[i];
/*     */     }
/*     */     
/* 231 */     return residuals;
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
/* 248 */     super.parseOptimizationData(optData);
/*     */ 
/*     */ 
/*     */     
/* 252 */     for (OptimizationData data : optData) {
/* 253 */       if (data instanceof Weight) {
/* 254 */         this.weightMatrixSqrt = squareRoot(((Weight)data).getWeight());
/*     */         break;
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
/*     */   
/*     */   private RealMatrix squareRoot(RealMatrix m) {
/* 269 */     if (m instanceof DiagonalMatrix) {
/* 270 */       int dim = m.getRowDimension();
/* 271 */       DiagonalMatrix diagonalMatrix = new DiagonalMatrix(dim);
/* 272 */       for (int i = 0; i < dim; i++) {
/* 273 */         diagonalMatrix.setEntry(i, i, FastMath.sqrt(m.getEntry(i, i)));
/*     */       }
/* 275 */       return (RealMatrix)diagonalMatrix;
/*     */     } 
/* 277 */     EigenDecomposition dec = new EigenDecomposition(m);
/* 278 */     return dec.getSquareRoot();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/vector/jacobian/AbstractLeastSquaresOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */