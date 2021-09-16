/*     */ package org.apache.commons.math3.optim.nonlinear.vector.jacobian;
/*     */ 
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.linear.ArrayRealVector;
/*     */ import org.apache.commons.math3.linear.BlockRealMatrix;
/*     */ import org.apache.commons.math3.linear.DecompositionSolver;
/*     */ import org.apache.commons.math3.linear.LUDecomposition;
/*     */ import org.apache.commons.math3.linear.QRDecomposition;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.linear.SingularMatrixException;
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
/*     */ @Deprecated
/*     */ public class GaussNewtonOptimizer
/*     */   extends AbstractLeastSquaresOptimizer
/*     */ {
/*     */   private final boolean useLU;
/*     */   
/*     */   public GaussNewtonOptimizer(ConvergenceChecker<PointVectorValuePair> checker) {
/*  66 */     this(true, checker);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GaussNewtonOptimizer(boolean useLU, ConvergenceChecker<PointVectorValuePair> checker) {
/*  77 */     super(checker);
/*  78 */     this.useLU = useLU;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PointVectorValuePair doOptimize() {
/*  84 */     checkParameters();
/*     */     
/*  86 */     ConvergenceChecker<PointVectorValuePair> checker = getConvergenceChecker();
/*     */ 
/*     */ 
/*     */     
/*  90 */     if (checker == null) {
/*  91 */       throw new NullArgumentException();
/*     */     }
/*     */     
/*  94 */     double[] targetValues = getTarget();
/*  95 */     int nR = targetValues.length;
/*     */     
/*  97 */     RealMatrix weightMatrix = getWeight();
/*     */     
/*  99 */     double[] residualsWeights = new double[nR];
/* 100 */     for (int i = 0; i < nR; i++) {
/* 101 */       residualsWeights[i] = weightMatrix.getEntry(i, i);
/*     */     }
/*     */     
/* 104 */     double[] currentPoint = getStartPoint();
/* 105 */     int nC = currentPoint.length;
/*     */ 
/*     */     
/* 108 */     PointVectorValuePair current = null;
/* 109 */     for (boolean converged = false; !converged; ) {
/* 110 */       incrementIterationCount();
/*     */ 
/*     */       
/* 113 */       PointVectorValuePair previous = current;
/*     */       
/* 115 */       double[] currentObjective = computeObjectiveValue(currentPoint);
/* 116 */       double[] currentResiduals = computeResiduals(currentObjective);
/* 117 */       RealMatrix weightedJacobian = computeWeightedJacobian(currentPoint);
/* 118 */       current = new PointVectorValuePair(currentPoint, currentObjective);
/*     */ 
/*     */       
/* 121 */       double[] b = new double[nC];
/* 122 */       double[][] a = new double[nC][nC];
/* 123 */       for (int j = 0; j < nR; j++) {
/*     */         
/* 125 */         double[] grad = weightedJacobian.getRow(j);
/* 126 */         double weight = residualsWeights[j];
/* 127 */         double residual = currentResiduals[j];
/*     */ 
/*     */         
/* 130 */         double wr = weight * residual;
/* 131 */         for (int m = 0; m < nC; m++) {
/* 132 */           b[m] = b[m] + wr * grad[m];
/*     */         }
/*     */ 
/*     */         
/* 136 */         for (int k = 0; k < nC; k++) {
/* 137 */           double[] ak = a[k];
/* 138 */           double wgk = weight * grad[k];
/* 139 */           for (int l = 0; l < nC; l++) {
/* 140 */             ak[l] = ak[l] + wgk * grad[l];
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 146 */       if (previous != null) {
/* 147 */         converged = checker.converged(getIterations(), previous, current);
/* 148 */         if (converged) {
/* 149 */           setCost(computeCost(currentResiduals));
/* 150 */           return current;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 156 */         BlockRealMatrix blockRealMatrix = new BlockRealMatrix(a);
/* 157 */         DecompositionSolver solver = this.useLU ? (new LUDecomposition((RealMatrix)blockRealMatrix)).getSolver() : (new QRDecomposition((RealMatrix)blockRealMatrix)).getSolver();
/*     */ 
/*     */         
/* 160 */         double[] dX = solver.solve((RealVector)new ArrayRealVector(b, false)).toArray();
/*     */         
/* 162 */         for (int k = 0; k < nC; k++) {
/* 163 */           currentPoint[k] = currentPoint[k] + dX[k];
/*     */         }
/* 165 */       } catch (SingularMatrixException e) {
/* 166 */         throw new ConvergenceException(LocalizedFormats.UNABLE_TO_SOLVE_SINGULAR_PROBLEM, new Object[0]);
/*     */       } 
/*     */     } 
/*     */     
/* 170 */     throw new MathInternalError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkParameters() {
/* 178 */     if (getLowerBound() != null || getUpperBound() != null)
/*     */     {
/* 180 */       throw new MathUnsupportedOperationException(LocalizedFormats.CONSTRAINT, new Object[0]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/vector/jacobian/GaussNewtonOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */