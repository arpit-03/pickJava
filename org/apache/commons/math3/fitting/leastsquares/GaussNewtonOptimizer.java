/*     */ package org.apache.commons.math3.fitting.leastsquares;
/*     */ 
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.linear.ArrayRealVector;
/*     */ import org.apache.commons.math3.linear.CholeskyDecomposition;
/*     */ import org.apache.commons.math3.linear.LUDecomposition;
/*     */ import org.apache.commons.math3.linear.MatrixUtils;
/*     */ import org.apache.commons.math3.linear.NonPositiveDefiniteMatrixException;
/*     */ import org.apache.commons.math3.linear.QRDecomposition;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.linear.RealVector;
/*     */ import org.apache.commons.math3.linear.SingularMatrixException;
/*     */ import org.apache.commons.math3.linear.SingularValueDecomposition;
/*     */ import org.apache.commons.math3.optim.ConvergenceChecker;
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
/*     */ public class GaussNewtonOptimizer
/*     */   implements LeastSquaresOptimizer
/*     */ {
/*     */   private static final double SINGULARITY_THRESHOLD = 1.0E-11D;
/*     */   private final Decomposition decomposition;
/*     */   
/*     */   public enum Decomposition
/*     */   {
/*  62 */     LU
/*     */     {
/*     */       protected RealVector solve(RealMatrix jacobian, RealVector residuals)
/*     */       {
/*     */         try {
/*  67 */           Pair<RealMatrix, RealVector> normalEquation = GaussNewtonOptimizer.computeNormalMatrix(jacobian, residuals);
/*     */           
/*  69 */           RealMatrix normal = (RealMatrix)normalEquation.getFirst();
/*  70 */           RealVector jTr = (RealVector)normalEquation.getSecond();
/*  71 */           return (new LUDecomposition(normal, 1.0E-11D)).getSolver().solve(jTr);
/*     */         
/*     */         }
/*  74 */         catch (SingularMatrixException e) {
/*  75 */           throw new ConvergenceException(LocalizedFormats.UNABLE_TO_SOLVE_SINGULAR_PROBLEM, new Object[] { e });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     },
/*  87 */     QR
/*     */     {
/*     */       protected RealVector solve(RealMatrix jacobian, RealVector residuals)
/*     */       {
/*     */         try {
/*  92 */           return (new QRDecomposition(jacobian, 1.0E-11D)).getSolver().solve(residuals);
/*     */         
/*     */         }
/*  95 */         catch (SingularMatrixException e) {
/*  96 */           throw new ConvergenceException(LocalizedFormats.UNABLE_TO_SOLVE_SINGULAR_PROBLEM, new Object[] { e });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     },
/* 108 */     CHOLESKY
/*     */     {
/*     */       protected RealVector solve(RealMatrix jacobian, RealVector residuals)
/*     */       {
/*     */         try {
/* 113 */           Pair<RealMatrix, RealVector> normalEquation = GaussNewtonOptimizer.computeNormalMatrix(jacobian, residuals);
/*     */           
/* 115 */           RealMatrix normal = (RealMatrix)normalEquation.getFirst();
/* 116 */           RealVector jTr = (RealVector)normalEquation.getSecond();
/* 117 */           return (new CholeskyDecomposition(normal, 1.0E-11D, 1.0E-11D)).getSolver().solve(jTr);
/*     */ 
/*     */         
/*     */         }
/* 121 */         catch (NonPositiveDefiniteMatrixException e) {
/* 122 */           throw new ConvergenceException(LocalizedFormats.UNABLE_TO_SOLVE_SINGULAR_PROBLEM, new Object[] { e });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     },
/* 133 */     SVD
/*     */     {
/*     */       protected RealVector solve(RealMatrix jacobian, RealVector residuals)
/*     */       {
/* 137 */         return (new SingularValueDecomposition(jacobian)).getSolver().solve(residuals);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract RealVector solve(RealMatrix param1RealMatrix, RealVector param1RealVector);
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
/*     */   public GaussNewtonOptimizer() {
/* 174 */     this(Decomposition.QR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GaussNewtonOptimizer(Decomposition decomposition) {
/* 184 */     this.decomposition = decomposition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decomposition getDecomposition() {
/* 193 */     return this.decomposition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GaussNewtonOptimizer withDecomposition(Decomposition newDecomposition) {
/* 203 */     return new GaussNewtonOptimizer(newDecomposition);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LeastSquaresOptimizer.Optimum optimize(LeastSquaresProblem lsp) {
/* 209 */     Incrementor evaluationCounter = lsp.getEvaluationCounter();
/* 210 */     Incrementor iterationCounter = lsp.getIterationCounter();
/* 211 */     ConvergenceChecker<LeastSquaresProblem.Evaluation> checker = lsp.getConvergenceChecker();
/*     */ 
/*     */ 
/*     */     
/* 215 */     if (checker == null) {
/* 216 */       throw new NullArgumentException();
/*     */     }
/*     */     
/* 219 */     RealVector currentPoint = lsp.getStart();
/*     */ 
/*     */     
/* 222 */     LeastSquaresProblem.Evaluation current = null;
/*     */     while (true) {
/* 224 */       iterationCounter.incrementCount();
/*     */ 
/*     */       
/* 227 */       LeastSquaresProblem.Evaluation previous = current;
/*     */       
/* 229 */       evaluationCounter.incrementCount();
/* 230 */       current = lsp.evaluate(currentPoint);
/* 231 */       RealVector currentResiduals = current.getResiduals();
/* 232 */       RealMatrix weightedJacobian = current.getJacobian();
/* 233 */       currentPoint = current.getPoint();
/*     */ 
/*     */       
/* 236 */       if (previous != null && checker.converged(iterationCounter.getCount(), previous, current))
/*     */       {
/* 238 */         return new OptimumImpl(current, evaluationCounter.getCount(), iterationCounter.getCount());
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 244 */       RealVector dX = this.decomposition.solve(weightedJacobian, currentResiduals);
/*     */       
/* 246 */       currentPoint = currentPoint.add(dX);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 253 */     return "GaussNewtonOptimizer{decomposition=" + this.decomposition + '}';
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
/*     */   private static Pair<RealMatrix, RealVector> computeNormalMatrix(RealMatrix jacobian, RealVector residuals) {
/* 268 */     int nR = jacobian.getRowDimension();
/* 269 */     int nC = jacobian.getColumnDimension();
/*     */     
/* 271 */     RealMatrix normal = MatrixUtils.createRealMatrix(nC, nC);
/* 272 */     ArrayRealVector arrayRealVector = new ArrayRealVector(nC);
/*     */     int i;
/* 274 */     for (i = 0; i < nR; i++) {
/*     */       
/* 276 */       for (int j = 0; j < nC; j++) {
/* 277 */         arrayRealVector.setEntry(j, arrayRealVector.getEntry(j) + residuals.getEntry(i) * jacobian.getEntry(i, j));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 282 */       for (int k = 0; k < nC; k++) {
/*     */         
/* 284 */         for (int l = k; l < nC; l++) {
/* 285 */           normal.setEntry(k, l, normal.getEntry(k, l) + jacobian.getEntry(i, k) * jacobian.getEntry(i, l));
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 291 */     for (i = 0; i < nC; i++) {
/* 292 */       for (int j = 0; j < i; j++) {
/* 293 */         normal.setEntry(i, j, normal.getEntry(j, i));
/*     */       }
/*     */     } 
/* 296 */     return new Pair(normal, arrayRealVector);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/leastsquares/GaussNewtonOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */