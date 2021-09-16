/*      */ package org.apache.commons.math3.fitting.leastsquares;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ import org.apache.commons.math3.exception.ConvergenceException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.linear.ArrayRealVector;
/*      */ import org.apache.commons.math3.linear.RealMatrix;
/*      */ import org.apache.commons.math3.linear.RealVector;
/*      */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.Incrementor;
/*      */ import org.apache.commons.math3.util.Precision;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LevenbergMarquardtOptimizer
/*      */   implements LeastSquaresOptimizer
/*      */ {
/*  113 */   private static final double TWO_EPS = 2.0D * Precision.EPSILON;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final double initialStepBoundFactor;
/*      */ 
/*      */ 
/*      */   
/*      */   private final double costRelativeTolerance;
/*      */ 
/*      */ 
/*      */   
/*      */   private final double parRelativeTolerance;
/*      */ 
/*      */ 
/*      */   
/*      */   private final double orthoTolerance;
/*      */ 
/*      */ 
/*      */   
/*      */   private final double qrRankingThreshold;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LevenbergMarquardtOptimizer() {
/*  140 */     this(100.0D, 1.0E-10D, 1.0E-10D, 1.0E-10D, Precision.SAFE_MIN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LevenbergMarquardtOptimizer(double initialStepBoundFactor, double costRelativeTolerance, double parRelativeTolerance, double orthoTolerance, double qrRankingThreshold) {
/*  160 */     this.initialStepBoundFactor = initialStepBoundFactor;
/*  161 */     this.costRelativeTolerance = costRelativeTolerance;
/*  162 */     this.parRelativeTolerance = parRelativeTolerance;
/*  163 */     this.orthoTolerance = orthoTolerance;
/*  164 */     this.qrRankingThreshold = qrRankingThreshold;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LevenbergMarquardtOptimizer withInitialStepBoundFactor(double newInitialStepBoundFactor) {
/*  178 */     return new LevenbergMarquardtOptimizer(newInitialStepBoundFactor, this.costRelativeTolerance, this.parRelativeTolerance, this.orthoTolerance, this.qrRankingThreshold);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LevenbergMarquardtOptimizer withCostRelativeTolerance(double newCostRelativeTolerance) {
/*  191 */     return new LevenbergMarquardtOptimizer(this.initialStepBoundFactor, newCostRelativeTolerance, this.parRelativeTolerance, this.orthoTolerance, this.qrRankingThreshold);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LevenbergMarquardtOptimizer withParameterRelativeTolerance(double newParRelativeTolerance) {
/*  205 */     return new LevenbergMarquardtOptimizer(this.initialStepBoundFactor, this.costRelativeTolerance, newParRelativeTolerance, this.orthoTolerance, this.qrRankingThreshold);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LevenbergMarquardtOptimizer withOrthoTolerance(double newOrthoTolerance) {
/*  221 */     return new LevenbergMarquardtOptimizer(this.initialStepBoundFactor, this.costRelativeTolerance, this.parRelativeTolerance, newOrthoTolerance, this.qrRankingThreshold);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LevenbergMarquardtOptimizer withRankingThreshold(double newQRRankingThreshold) {
/*  237 */     return new LevenbergMarquardtOptimizer(this.initialStepBoundFactor, this.costRelativeTolerance, this.parRelativeTolerance, this.orthoTolerance, newQRRankingThreshold);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getInitialStepBoundFactor() {
/*  252 */     return this.initialStepBoundFactor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getCostRelativeTolerance() {
/*  262 */     return this.costRelativeTolerance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getParameterRelativeTolerance() {
/*  272 */     return this.parRelativeTolerance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrthoTolerance() {
/*  282 */     return this.orthoTolerance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getRankingThreshold() {
/*  292 */     return this.qrRankingThreshold;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public LeastSquaresOptimizer.Optimum optimize(LeastSquaresProblem problem) {
/*  298 */     int nR = problem.getObservationSize();
/*  299 */     int nC = problem.getParameterSize();
/*      */     
/*  301 */     Incrementor iterationCounter = problem.getIterationCounter();
/*  302 */     Incrementor evaluationCounter = problem.getEvaluationCounter();
/*      */     
/*  304 */     ConvergenceChecker<LeastSquaresProblem.Evaluation> checker = problem.getConvergenceChecker();
/*      */ 
/*      */     
/*  307 */     int solvedCols = FastMath.min(nR, nC);
/*      */     
/*  309 */     double[] lmDir = new double[nC];
/*      */     
/*  311 */     double lmPar = 0.0D;
/*      */ 
/*      */     
/*  314 */     double delta = 0.0D;
/*  315 */     double xNorm = 0.0D;
/*  316 */     double[] diag = new double[nC];
/*  317 */     double[] oldX = new double[nC];
/*  318 */     double[] oldRes = new double[nR];
/*  319 */     double[] qtf = new double[nR];
/*  320 */     double[] work1 = new double[nC];
/*  321 */     double[] work2 = new double[nC];
/*  322 */     double[] work3 = new double[nC];
/*      */ 
/*      */ 
/*      */     
/*  326 */     evaluationCounter.incrementCount();
/*      */     
/*  328 */     LeastSquaresProblem.Evaluation current = problem.evaluate(problem.getStart());
/*  329 */     double[] currentResiduals = current.getResiduals().toArray();
/*  330 */     double currentCost = current.getCost();
/*  331 */     double[] currentPoint = current.getPoint().toArray();
/*      */ 
/*      */     
/*  334 */     boolean firstIteration = true;
/*      */     while (true) {
/*  336 */       iterationCounter.incrementCount();
/*      */       
/*  338 */       LeastSquaresProblem.Evaluation previous = current;
/*      */ 
/*      */       
/*  341 */       InternalData internalData = qrDecomposition(current.getJacobian(), solvedCols);
/*      */       
/*  343 */       double[][] weightedJacobian = internalData.weightedJacobian;
/*  344 */       int[] permutation = internalData.permutation;
/*  345 */       double[] diagR = internalData.diagR;
/*  346 */       double[] jacNorm = internalData.jacNorm;
/*      */ 
/*      */       
/*  349 */       double[] weightedResidual = currentResiduals;
/*  350 */       for (int i = 0; i < nR; i++) {
/*  351 */         qtf[i] = weightedResidual[i];
/*      */       }
/*      */ 
/*      */       
/*  355 */       qTy(qtf, internalData);
/*      */       
/*      */       int k;
/*      */       
/*  359 */       for (k = 0; k < solvedCols; k++) {
/*  360 */         int pk = permutation[k];
/*  361 */         weightedJacobian[k][pk] = diagR[pk];
/*      */       } 
/*      */       
/*  364 */       if (firstIteration) {
/*      */ 
/*      */         
/*  367 */         xNorm = 0.0D;
/*  368 */         for (k = 0; k < nC; k++) {
/*  369 */           double dk = jacNorm[k];
/*  370 */           if (dk == 0.0D) {
/*  371 */             dk = 1.0D;
/*      */           }
/*  373 */           double xk = dk * currentPoint[k];
/*  374 */           xNorm += xk * xk;
/*  375 */           diag[k] = dk;
/*      */         } 
/*  377 */         xNorm = FastMath.sqrt(xNorm);
/*      */ 
/*      */         
/*  380 */         delta = (xNorm == 0.0D) ? this.initialStepBoundFactor : (this.initialStepBoundFactor * xNorm);
/*      */       } 
/*      */ 
/*      */       
/*  384 */       double maxCosine = 0.0D;
/*  385 */       if (currentCost != 0.0D) {
/*  386 */         for (int m = 0; m < solvedCols; m++) {
/*  387 */           int pj = permutation[m];
/*  388 */           double s = jacNorm[pj];
/*  389 */           if (s != 0.0D) {
/*  390 */             double sum = 0.0D;
/*  391 */             for (int n = 0; n <= m; n++) {
/*  392 */               sum += weightedJacobian[n][pj] * qtf[n];
/*      */             }
/*  394 */             maxCosine = FastMath.max(maxCosine, FastMath.abs(sum) / s * currentCost);
/*      */           } 
/*      */         } 
/*      */       }
/*  398 */       if (maxCosine <= this.orthoTolerance)
/*      */       {
/*  400 */         return new OptimumImpl(current, evaluationCounter.getCount(), iterationCounter.getCount());
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  407 */       for (int j = 0; j < nC; j++) {
/*  408 */         diag[j] = FastMath.max(diag[j], jacNorm[j]);
/*      */       }
/*      */ 
/*      */       
/*  412 */       for (double ratio = 0.0D; ratio < 1.0E-4D; ) {
/*      */ 
/*      */         
/*  415 */         for (int m = 0; m < solvedCols; m++) {
/*  416 */           int pj = permutation[m];
/*  417 */           oldX[pj] = currentPoint[pj];
/*      */         } 
/*  419 */         double previousCost = currentCost;
/*  420 */         double[] tmpVec = weightedResidual;
/*  421 */         weightedResidual = oldRes;
/*  422 */         oldRes = tmpVec;
/*      */ 
/*      */         
/*  425 */         lmPar = determineLMParameter(qtf, delta, diag, internalData, solvedCols, work1, work2, work3, lmDir, lmPar);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  430 */         double lmNorm = 0.0D;
/*  431 */         for (int n = 0; n < solvedCols; n++) {
/*  432 */           int pj = permutation[n];
/*  433 */           lmDir[pj] = -lmDir[pj];
/*  434 */           currentPoint[pj] = oldX[pj] + lmDir[pj];
/*  435 */           double s = diag[pj] * lmDir[pj];
/*  436 */           lmNorm += s * s;
/*      */         } 
/*  438 */         lmNorm = FastMath.sqrt(lmNorm);
/*      */         
/*  440 */         if (firstIteration) {
/*  441 */           delta = FastMath.min(delta, lmNorm);
/*      */         }
/*      */ 
/*      */         
/*  445 */         evaluationCounter.incrementCount();
/*  446 */         current = problem.evaluate((RealVector)new ArrayRealVector(currentPoint));
/*  447 */         currentResiduals = current.getResiduals().toArray();
/*  448 */         currentCost = current.getCost();
/*  449 */         currentPoint = current.getPoint().toArray();
/*      */ 
/*      */         
/*  452 */         double actRed = -1.0D;
/*  453 */         if (0.1D * currentCost < previousCost) {
/*  454 */           double r = currentCost / previousCost;
/*  455 */           actRed = 1.0D - r * r;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  460 */         for (int i1 = 0; i1 < solvedCols; i1++) {
/*  461 */           int pj = permutation[i1];
/*  462 */           double dirJ = lmDir[pj];
/*  463 */           work1[i1] = 0.0D;
/*  464 */           for (int i3 = 0; i3 <= i1; i3++) {
/*  465 */             work1[i3] = work1[i3] + weightedJacobian[i3][pj] * dirJ;
/*      */           }
/*      */         } 
/*  468 */         double coeff1 = 0.0D;
/*  469 */         for (int i2 = 0; i2 < solvedCols; i2++) {
/*  470 */           coeff1 += work1[i2] * work1[i2];
/*      */         }
/*  472 */         double pc2 = previousCost * previousCost;
/*  473 */         coeff1 /= pc2;
/*  474 */         double coeff2 = lmPar * lmNorm * lmNorm / pc2;
/*  475 */         double preRed = coeff1 + 2.0D * coeff2;
/*  476 */         double dirDer = -(coeff1 + coeff2);
/*      */ 
/*      */         
/*  479 */         ratio = (preRed == 0.0D) ? 0.0D : (actRed / preRed);
/*      */ 
/*      */         
/*  482 */         if (ratio <= 0.25D) {
/*  483 */           double tmp = (actRed < 0.0D) ? (0.5D * dirDer / (dirDer + 0.5D * actRed)) : 0.5D;
/*      */           
/*  485 */           if (0.1D * currentCost >= previousCost || tmp < 0.1D) {
/*  486 */             tmp = 0.1D;
/*      */           }
/*  488 */           delta = tmp * FastMath.min(delta, 10.0D * lmNorm);
/*  489 */           lmPar /= tmp;
/*  490 */         } else if (lmPar == 0.0D || ratio >= 0.75D) {
/*  491 */           delta = 2.0D * lmNorm;
/*  492 */           lmPar *= 0.5D;
/*      */         } 
/*      */ 
/*      */         
/*  496 */         if (ratio >= 1.0E-4D) {
/*      */           
/*  498 */           firstIteration = false;
/*  499 */           xNorm = 0.0D;
/*  500 */           for (int i3 = 0; i3 < nC; i3++) {
/*  501 */             double xK = diag[i3] * currentPoint[i3];
/*  502 */             xNorm += xK * xK;
/*      */           } 
/*  504 */           xNorm = FastMath.sqrt(xNorm);
/*      */ 
/*      */           
/*  507 */           if (checker != null && checker.converged(iterationCounter.getCount(), previous, current)) {
/*  508 */             return new OptimumImpl(current, evaluationCounter.getCount(), iterationCounter.getCount());
/*      */           }
/*      */         } else {
/*      */           
/*  512 */           currentCost = previousCost;
/*  513 */           for (int i3 = 0; i3 < solvedCols; i3++) {
/*  514 */             int pj = permutation[i3];
/*  515 */             currentPoint[pj] = oldX[pj];
/*      */           } 
/*  517 */           tmpVec = weightedResidual;
/*  518 */           weightedResidual = oldRes;
/*  519 */           oldRes = tmpVec;
/*      */           
/*  521 */           current = previous;
/*      */         } 
/*      */ 
/*      */         
/*  525 */         if ((FastMath.abs(actRed) <= this.costRelativeTolerance && preRed <= this.costRelativeTolerance && ratio <= 2.0D) || delta <= this.parRelativeTolerance * xNorm)
/*      */         {
/*      */ 
/*      */           
/*  529 */           return new OptimumImpl(current, evaluationCounter.getCount(), iterationCounter.getCount());
/*      */         }
/*      */ 
/*      */         
/*  533 */         if (FastMath.abs(actRed) <= TWO_EPS && preRed <= TWO_EPS && ratio <= 2.0D)
/*      */         {
/*      */           
/*  536 */           throw new ConvergenceException(LocalizedFormats.TOO_SMALL_COST_RELATIVE_TOLERANCE, new Object[] { Double.valueOf(this.costRelativeTolerance) });
/*      */         }
/*  538 */         if (delta <= TWO_EPS * xNorm) {
/*  539 */           throw new ConvergenceException(LocalizedFormats.TOO_SMALL_PARAMETERS_RELATIVE_TOLERANCE, new Object[] { Double.valueOf(this.parRelativeTolerance) });
/*      */         }
/*  541 */         if (maxCosine <= TWO_EPS) {
/*  542 */           throw new ConvergenceException(LocalizedFormats.TOO_SMALL_ORTHOGONALITY_TOLERANCE, new Object[] { Double.valueOf(this.orthoTolerance) });
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class InternalData
/*      */   {
/*      */     private final double[][] weightedJacobian;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int[] permutation;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int rank;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final double[] diagR;
/*      */ 
/*      */ 
/*      */     
/*      */     private final double[] jacNorm;
/*      */ 
/*      */ 
/*      */     
/*      */     private final double[] beta;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     InternalData(double[][] weightedJacobian, int[] permutation, int rank, double[] diagR, double[] jacNorm, double[] beta) {
/*  583 */       this.weightedJacobian = weightedJacobian;
/*  584 */       this.permutation = permutation;
/*  585 */       this.rank = rank;
/*  586 */       this.diagR = diagR;
/*  587 */       this.jacNorm = jacNorm;
/*  588 */       this.beta = beta;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double determineLMParameter(double[] qy, double delta, double[] diag, InternalData internalData, int solvedCols, double[] work1, double[] work2, double[] work3, double[] lmDir, double lmPar) {
/*  624 */     double[][] weightedJacobian = internalData.weightedJacobian;
/*  625 */     int[] permutation = internalData.permutation;
/*  626 */     int rank = internalData.rank;
/*  627 */     double[] diagR = internalData.diagR;
/*      */     
/*  629 */     int nC = (weightedJacobian[0]).length;
/*      */     
/*      */     int j;
/*      */     
/*  633 */     for (j = 0; j < rank; j++) {
/*  634 */       lmDir[permutation[j]] = qy[j];
/*      */     }
/*  636 */     for (j = rank; j < nC; j++) {
/*  637 */       lmDir[permutation[j]] = 0.0D;
/*      */     }
/*  639 */     for (int k = rank - 1; k >= 0; k--) {
/*  640 */       int pk = permutation[k];
/*  641 */       double ypk = lmDir[pk] / diagR[pk];
/*  642 */       for (int n = 0; n < k; n++) {
/*  643 */         lmDir[permutation[n]] = lmDir[permutation[n]] - ypk * weightedJacobian[n][pk];
/*      */       }
/*  645 */       lmDir[pk] = ypk;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  650 */     double dxNorm = 0.0D;
/*  651 */     for (int i = 0; i < solvedCols; i++) {
/*  652 */       int pj = permutation[i];
/*  653 */       double s = diag[pj] * lmDir[pj];
/*  654 */       work1[pj] = s;
/*  655 */       dxNorm += s * s;
/*      */     } 
/*  657 */     dxNorm = FastMath.sqrt(dxNorm);
/*  658 */     double fp = dxNorm - delta;
/*  659 */     if (fp <= 0.1D * delta) {
/*  660 */       lmPar = 0.0D;
/*  661 */       return lmPar;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  668 */     double parl = 0.0D;
/*  669 */     if (rank == solvedCols) {
/*  670 */       int n; for (n = 0; n < solvedCols; n++) {
/*  671 */         int pj = permutation[n];
/*  672 */         work1[pj] = work1[pj] * diag[pj] / dxNorm;
/*      */       } 
/*  674 */       double d = 0.0D;
/*  675 */       for (n = 0; n < solvedCols; n++) {
/*  676 */         int pj = permutation[n];
/*  677 */         double sum = 0.0D;
/*  678 */         for (int i1 = 0; i1 < n; i1++) {
/*  679 */           sum += weightedJacobian[i1][pj] * work1[permutation[i1]];
/*      */         }
/*  681 */         double s = (work1[pj] - sum) / diagR[pj];
/*  682 */         work1[pj] = s;
/*  683 */         d += s * s;
/*      */       } 
/*  685 */       parl = fp / delta * d;
/*      */     } 
/*      */ 
/*      */     
/*  689 */     double sum2 = 0.0D;
/*  690 */     for (int m = 0; m < solvedCols; m++) {
/*  691 */       int pj = permutation[m];
/*  692 */       double sum = 0.0D;
/*  693 */       for (int n = 0; n <= m; n++) {
/*  694 */         sum += weightedJacobian[n][pj] * qy[n];
/*      */       }
/*  696 */       sum /= diag[pj];
/*  697 */       sum2 += sum * sum;
/*      */     } 
/*  699 */     double gNorm = FastMath.sqrt(sum2);
/*  700 */     double paru = gNorm / delta;
/*  701 */     if (paru == 0.0D) {
/*  702 */       paru = Precision.SAFE_MIN / FastMath.min(delta, 0.1D);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  707 */     lmPar = FastMath.min(paru, FastMath.max(lmPar, parl));
/*  708 */     if (lmPar == 0.0D) {
/*  709 */       lmPar = gNorm / dxNorm;
/*      */     }
/*      */     
/*  712 */     for (int countdown = 10; countdown >= 0; countdown--) {
/*      */ 
/*      */       
/*  715 */       if (lmPar == 0.0D) {
/*  716 */         lmPar = FastMath.max(Precision.SAFE_MIN, 0.001D * paru);
/*      */       }
/*  718 */       double sPar = FastMath.sqrt(lmPar); int n;
/*  719 */       for (n = 0; n < solvedCols; n++) {
/*  720 */         int pj = permutation[n];
/*  721 */         work1[pj] = sPar * diag[pj];
/*      */       } 
/*  723 */       determineLMDirection(qy, work1, work2, internalData, solvedCols, work3, lmDir);
/*      */       
/*  725 */       dxNorm = 0.0D;
/*  726 */       for (n = 0; n < solvedCols; n++) {
/*  727 */         int pj = permutation[n];
/*  728 */         double s = diag[pj] * lmDir[pj];
/*  729 */         work3[pj] = s;
/*  730 */         dxNorm += s * s;
/*      */       } 
/*  732 */       dxNorm = FastMath.sqrt(dxNorm);
/*  733 */       double previousFP = fp;
/*  734 */       fp = dxNorm - delta;
/*      */ 
/*      */ 
/*      */       
/*  738 */       if (FastMath.abs(fp) <= 0.1D * delta || (parl == 0.0D && fp <= previousFP && previousFP < 0.0D))
/*      */       {
/*      */ 
/*      */         
/*  742 */         return lmPar;
/*      */       }
/*      */       
/*      */       int i1;
/*  746 */       for (i1 = 0; i1 < solvedCols; i1++) {
/*  747 */         int pj = permutation[i1];
/*  748 */         work1[pj] = work3[pj] * diag[pj] / dxNorm;
/*      */       } 
/*  750 */       for (i1 = 0; i1 < solvedCols; i1++) {
/*  751 */         int pj = permutation[i1];
/*  752 */         work1[pj] = work1[pj] / work2[i1];
/*  753 */         double tmp = work1[pj];
/*  754 */         for (int i2 = i1 + 1; i2 < solvedCols; i2++) {
/*  755 */           work1[permutation[i2]] = work1[permutation[i2]] - weightedJacobian[i2][pj] * tmp;
/*      */         }
/*      */       } 
/*  758 */       sum2 = 0.0D;
/*  759 */       for (i1 = 0; i1 < solvedCols; i1++) {
/*  760 */         double s = work1[permutation[i1]];
/*  761 */         sum2 += s * s;
/*      */       } 
/*  763 */       double correction = fp / delta * sum2;
/*      */ 
/*      */       
/*  766 */       if (fp > 0.0D) {
/*  767 */         parl = FastMath.max(parl, lmPar);
/*  768 */       } else if (fp < 0.0D) {
/*  769 */         paru = FastMath.min(paru, lmPar);
/*      */       } 
/*      */ 
/*      */       
/*  773 */       lmPar = FastMath.max(parl, lmPar + correction);
/*      */     } 
/*      */     
/*  776 */     return lmPar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void determineLMDirection(double[] qy, double[] diag, double[] lmDiag, InternalData internalData, int solvedCols, double[] work, double[] lmDir) {
/*  808 */     int[] permutation = internalData.permutation;
/*  809 */     double[][] weightedJacobian = internalData.weightedJacobian;
/*  810 */     double[] diagR = internalData.diagR;
/*      */     
/*      */     int j;
/*      */     
/*  814 */     for (j = 0; j < solvedCols; j++) {
/*  815 */       int pj = permutation[j];
/*  816 */       for (int k = j + 1; k < solvedCols; k++) {
/*  817 */         weightedJacobian[k][pj] = weightedJacobian[j][permutation[k]];
/*      */       }
/*  819 */       lmDir[j] = diagR[pj];
/*  820 */       work[j] = qy[j];
/*      */     } 
/*      */ 
/*      */     
/*  824 */     for (j = 0; j < solvedCols; j++) {
/*      */ 
/*      */ 
/*      */       
/*  828 */       int pj = permutation[j];
/*  829 */       double dpj = diag[pj];
/*  830 */       if (dpj != 0.0D) {
/*  831 */         Arrays.fill(lmDiag, j + 1, lmDiag.length, 0.0D);
/*      */       }
/*  833 */       lmDiag[j] = dpj;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  838 */       double qtbpj = 0.0D;
/*  839 */       for (int k = j; k < solvedCols; k++) {
/*  840 */         int pk = permutation[k];
/*      */ 
/*      */ 
/*      */         
/*  844 */         if (lmDiag[k] != 0.0D) {
/*      */ 
/*      */ 
/*      */           
/*  848 */           double sin, cos, rkk = weightedJacobian[k][pk];
/*  849 */           if (FastMath.abs(rkk) < FastMath.abs(lmDiag[k])) {
/*  850 */             double cotan = rkk / lmDiag[k];
/*  851 */             sin = 1.0D / FastMath.sqrt(1.0D + cotan * cotan);
/*  852 */             cos = sin * cotan;
/*      */           } else {
/*  854 */             double tan = lmDiag[k] / rkk;
/*  855 */             cos = 1.0D / FastMath.sqrt(1.0D + tan * tan);
/*  856 */             sin = cos * tan;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  861 */           weightedJacobian[k][pk] = cos * rkk + sin * lmDiag[k];
/*  862 */           double temp = cos * work[k] + sin * qtbpj;
/*  863 */           qtbpj = -sin * work[k] + cos * qtbpj;
/*  864 */           work[k] = temp;
/*      */ 
/*      */           
/*  867 */           for (int m = k + 1; m < solvedCols; m++) {
/*  868 */             double rik = weightedJacobian[m][pk];
/*  869 */             double temp2 = cos * rik + sin * lmDiag[m];
/*  870 */             lmDiag[m] = -sin * rik + cos * lmDiag[m];
/*  871 */             weightedJacobian[m][pk] = temp2;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  878 */       lmDiag[j] = weightedJacobian[j][permutation[j]];
/*  879 */       weightedJacobian[j][permutation[j]] = lmDir[j];
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  884 */     int nSing = solvedCols; int i;
/*  885 */     for (i = 0; i < solvedCols; i++) {
/*  886 */       if (lmDiag[i] == 0.0D && nSing == solvedCols) {
/*  887 */         nSing = i;
/*      */       }
/*  889 */       if (nSing < solvedCols) {
/*  890 */         work[i] = 0.0D;
/*      */       }
/*      */     } 
/*  893 */     if (nSing > 0) {
/*  894 */       for (i = nSing - 1; i >= 0; i--) {
/*  895 */         int pj = permutation[i];
/*  896 */         double sum = 0.0D;
/*  897 */         for (int k = i + 1; k < nSing; k++) {
/*  898 */           sum += weightedJacobian[k][pj] * work[k];
/*      */         }
/*  900 */         work[i] = (work[i] - sum) / lmDiag[i];
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  905 */     for (i = 0; i < lmDir.length; i++) {
/*  906 */       lmDir[permutation[i]] = work[i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InternalData qrDecomposition(RealMatrix jacobian, int solvedCols) throws ConvergenceException {
/*  940 */     double[][] weightedJacobian = jacobian.scalarMultiply(-1.0D).getData();
/*      */     
/*  942 */     int nR = weightedJacobian.length;
/*  943 */     int nC = (weightedJacobian[0]).length;
/*      */     
/*  945 */     int[] permutation = new int[nC];
/*  946 */     double[] diagR = new double[nC];
/*  947 */     double[] jacNorm = new double[nC];
/*  948 */     double[] beta = new double[nC];
/*      */     
/*      */     int k;
/*  951 */     for (k = 0; k < nC; k++) {
/*  952 */       permutation[k] = k;
/*  953 */       double norm2 = 0.0D;
/*  954 */       for (int i = 0; i < nR; i++) {
/*  955 */         double akk = weightedJacobian[i][k];
/*  956 */         norm2 += akk * akk;
/*      */       } 
/*  958 */       jacNorm[k] = FastMath.sqrt(norm2);
/*      */     } 
/*      */ 
/*      */     
/*  962 */     for (k = 0; k < nC; k++) {
/*      */ 
/*      */       
/*  965 */       int nextColumn = -1;
/*  966 */       double ak2 = Double.NEGATIVE_INFINITY;
/*  967 */       for (int i = k; i < nC; i++) {
/*  968 */         double norm2 = 0.0D;
/*  969 */         for (int j = k; j < nR; j++) {
/*  970 */           double aki = weightedJacobian[j][permutation[i]];
/*  971 */           norm2 += aki * aki;
/*      */         } 
/*  973 */         if (Double.isInfinite(norm2) || Double.isNaN(norm2)) {
/*  974 */           throw new ConvergenceException(LocalizedFormats.UNABLE_TO_PERFORM_QR_DECOMPOSITION_ON_JACOBIAN, new Object[] { Integer.valueOf(nR), Integer.valueOf(nC) });
/*      */         }
/*      */         
/*  977 */         if (norm2 > ak2) {
/*  978 */           nextColumn = i;
/*  979 */           ak2 = norm2;
/*      */         } 
/*      */       } 
/*  982 */       if (ak2 <= this.qrRankingThreshold) {
/*  983 */         return new InternalData(weightedJacobian, permutation, k, diagR, jacNorm, beta);
/*      */       }
/*  985 */       int pk = permutation[nextColumn];
/*  986 */       permutation[nextColumn] = permutation[k];
/*  987 */       permutation[k] = pk;
/*      */ 
/*      */       
/*  990 */       double akk = weightedJacobian[k][pk];
/*  991 */       double alpha = (akk > 0.0D) ? -FastMath.sqrt(ak2) : FastMath.sqrt(ak2);
/*  992 */       double betak = 1.0D / (ak2 - akk * alpha);
/*  993 */       beta[pk] = betak;
/*      */ 
/*      */       
/*  996 */       diagR[pk] = alpha;
/*  997 */       weightedJacobian[k][pk] = weightedJacobian[k][pk] - alpha;
/*      */ 
/*      */       
/* 1000 */       for (int dk = nC - 1 - k; dk > 0; dk--) {
/* 1001 */         double gamma = 0.0D; int j;
/* 1002 */         for (j = k; j < nR; j++) {
/* 1003 */           gamma += weightedJacobian[j][pk] * weightedJacobian[j][permutation[k + dk]];
/*      */         }
/* 1005 */         gamma *= betak;
/* 1006 */         for (j = k; j < nR; j++) {
/* 1007 */           weightedJacobian[j][permutation[k + dk]] = weightedJacobian[j][permutation[k + dk]] - gamma * weightedJacobian[j][pk];
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1012 */     return new InternalData(weightedJacobian, permutation, solvedCols, diagR, jacNorm, beta);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void qTy(double[] y, InternalData internalData) {
/* 1023 */     double[][] weightedJacobian = internalData.weightedJacobian;
/* 1024 */     int[] permutation = internalData.permutation;
/* 1025 */     double[] beta = internalData.beta;
/*      */     
/* 1027 */     int nR = weightedJacobian.length;
/* 1028 */     int nC = (weightedJacobian[0]).length;
/*      */     
/* 1030 */     for (int k = 0; k < nC; k++) {
/* 1031 */       int pk = permutation[k];
/* 1032 */       double gamma = 0.0D; int i;
/* 1033 */       for (i = k; i < nR; i++) {
/* 1034 */         gamma += weightedJacobian[i][pk] * y[i];
/*      */       }
/* 1036 */       gamma *= beta[pk];
/* 1037 */       for (i = k; i < nR; i++)
/* 1038 */         y[i] = y[i] - gamma * weightedJacobian[i][pk]; 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/fitting/leastsquares/LevenbergMarquardtOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */