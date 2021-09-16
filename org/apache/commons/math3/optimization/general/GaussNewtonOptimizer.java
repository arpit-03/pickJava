/*     */ package org.apache.commons.math3.optimization.general;
/*     */ 
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
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
/*     */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optimization.PointVectorValuePair;
/*     */ import org.apache.commons.math3.optimization.SimpleVectorValueChecker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   @Deprecated
/*     */   public GaussNewtonOptimizer() {
/*  62 */     this(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GaussNewtonOptimizer(ConvergenceChecker<PointVectorValuePair> checker) {
/*  72 */     this(true, checker);
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
/*     */   public GaussNewtonOptimizer(boolean useLU) {
/*  87 */     this(useLU, (ConvergenceChecker<PointVectorValuePair>)new SimpleVectorValueChecker());
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
/*  98 */     super(checker);
/*  99 */     this.useLU = useLU;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PointVectorValuePair doOptimize() {
/* 105 */     ConvergenceChecker<PointVectorValuePair> checker = getConvergenceChecker();
/*     */ 
/*     */ 
/*     */     
/* 109 */     if (checker == null) {
/* 110 */       throw new NullArgumentException();
/*     */     }
/*     */     
/* 113 */     double[] targetValues = getTarget();
/* 114 */     int nR = targetValues.length;
/*     */     
/* 116 */     RealMatrix weightMatrix = getWeight();
/*     */     
/* 118 */     double[] residualsWeights = new double[nR];
/* 119 */     for (int i = 0; i < nR; i++) {
/* 120 */       residualsWeights[i] = weightMatrix.getEntry(i, i);
/*     */     }
/*     */     
/* 123 */     double[] currentPoint = getStartPoint();
/* 124 */     int nC = currentPoint.length;
/*     */ 
/*     */     
/* 127 */     PointVectorValuePair current = null;
/* 128 */     int iter = 0;
/* 129 */     for (boolean converged = false; !converged; ) {
/* 130 */       iter++;
/*     */ 
/*     */       
/* 133 */       PointVectorValuePair previous = current;
/*     */       
/* 135 */       double[] currentObjective = computeObjectiveValue(currentPoint);
/* 136 */       double[] currentResiduals = computeResiduals(currentObjective);
/* 137 */       RealMatrix weightedJacobian = computeWeightedJacobian(currentPoint);
/* 138 */       current = new PointVectorValuePair(currentPoint, currentObjective);
/*     */ 
/*     */       
/* 141 */       double[] b = new double[nC];
/* 142 */       double[][] a = new double[nC][nC];
/* 143 */       for (int j = 0; j < nR; j++) {
/*     */         
/* 145 */         double[] grad = weightedJacobian.getRow(j);
/* 146 */         double weight = residualsWeights[j];
/* 147 */         double residual = currentResiduals[j];
/*     */ 
/*     */         
/* 150 */         double wr = weight * residual;
/* 151 */         for (int m = 0; m < nC; m++) {
/* 152 */           b[m] = b[m] + wr * grad[m];
/*     */         }
/*     */ 
/*     */         
/* 156 */         for (int k = 0; k < nC; k++) {
/* 157 */           double[] ak = a[k];
/* 158 */           double wgk = weight * grad[k];
/* 159 */           for (int l = 0; l < nC; l++) {
/* 160 */             ak[l] = ak[l] + wgk * grad[l];
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 167 */         BlockRealMatrix blockRealMatrix = new BlockRealMatrix(a);
/* 168 */         DecompositionSolver solver = this.useLU ? (new LUDecomposition((RealMatrix)blockRealMatrix)).getSolver() : (new QRDecomposition((RealMatrix)blockRealMatrix)).getSolver();
/*     */ 
/*     */         
/* 171 */         double[] dX = solver.solve((RealVector)new ArrayRealVector(b, false)).toArray();
/*     */         
/* 173 */         for (int k = 0; k < nC; k++) {
/* 174 */           currentPoint[k] = currentPoint[k] + dX[k];
/*     */         }
/* 176 */       } catch (SingularMatrixException e) {
/* 177 */         throw new ConvergenceException(LocalizedFormats.UNABLE_TO_SOLVE_SINGULAR_PROBLEM, new Object[0]);
/*     */       } 
/*     */ 
/*     */       
/* 181 */       if (previous != null) {
/* 182 */         converged = checker.converged(iter, previous, current);
/* 183 */         if (converged) {
/* 184 */           this.cost = computeCost(currentResiduals);
/*     */           
/* 186 */           this.point = current.getPoint();
/* 187 */           return current;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     throw new MathInternalError();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/general/GaussNewtonOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */