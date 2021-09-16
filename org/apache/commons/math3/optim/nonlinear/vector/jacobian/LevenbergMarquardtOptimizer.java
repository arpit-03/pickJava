/*     */ package org.apache.commons.math3.optim.nonlinear.vector.jacobian;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optim.PointVectorValuePair;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class LevenbergMarquardtOptimizer
/*     */   extends AbstractLeastSquaresOptimizer
/*     */ {
/* 120 */   private static final double TWO_EPS = 2.0D * Precision.EPSILON;
/*     */ 
/*     */   
/*     */   private int solvedCols;
/*     */ 
/*     */   
/*     */   private double[] diagR;
/*     */ 
/*     */   
/*     */   private double[] jacNorm;
/*     */ 
/*     */   
/*     */   private double[] beta;
/*     */ 
/*     */   
/*     */   private int[] permutation;
/*     */ 
/*     */   
/*     */   private int rank;
/*     */ 
/*     */   
/*     */   private double lmPar;
/*     */ 
/*     */   
/*     */   private double[] lmDir;
/*     */ 
/*     */   
/*     */   private final double initialStepBoundFactor;
/*     */ 
/*     */   
/*     */   private final double costRelativeTolerance;
/*     */ 
/*     */   
/*     */   private final double parRelativeTolerance;
/*     */ 
/*     */   
/*     */   private final double orthoTolerance;
/*     */ 
/*     */   
/*     */   private final double qrRankingThreshold;
/*     */ 
/*     */   
/*     */   private double[] weightedResidual;
/*     */   
/*     */   private double[][] weightedJacobian;
/*     */ 
/*     */   
/*     */   public LevenbergMarquardtOptimizer() {
/* 168 */     this(100.0D, 1.0E-10D, 1.0E-10D, 1.0E-10D, Precision.SAFE_MIN);
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
/*     */   public LevenbergMarquardtOptimizer(ConvergenceChecker<PointVectorValuePair> checker) {
/* 187 */     this(100.0D, checker, 1.0E-10D, 1.0E-10D, 1.0E-10D, Precision.SAFE_MIN);
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
/*     */   public LevenbergMarquardtOptimizer(double initialStepBoundFactor, ConvergenceChecker<PointVectorValuePair> checker, double costRelativeTolerance, double parRelativeTolerance, double orthoTolerance, double threshold) {
/* 218 */     super(checker);
/* 219 */     this.initialStepBoundFactor = initialStepBoundFactor;
/* 220 */     this.costRelativeTolerance = costRelativeTolerance;
/* 221 */     this.parRelativeTolerance = parRelativeTolerance;
/* 222 */     this.orthoTolerance = orthoTolerance;
/* 223 */     this.qrRankingThreshold = threshold;
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
/*     */   public LevenbergMarquardtOptimizer(double costRelativeTolerance, double parRelativeTolerance, double orthoTolerance) {
/* 247 */     this(100.0D, costRelativeTolerance, parRelativeTolerance, orthoTolerance, Precision.SAFE_MIN);
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
/*     */   public LevenbergMarquardtOptimizer(double initialStepBoundFactor, double costRelativeTolerance, double parRelativeTolerance, double orthoTolerance, double threshold) {
/* 280 */     super((ConvergenceChecker<PointVectorValuePair>)null);
/* 281 */     this.initialStepBoundFactor = initialStepBoundFactor;
/* 282 */     this.costRelativeTolerance = costRelativeTolerance;
/* 283 */     this.parRelativeTolerance = parRelativeTolerance;
/* 284 */     this.orthoTolerance = orthoTolerance;
/* 285 */     this.qrRankingThreshold = threshold;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PointVectorValuePair doOptimize() {
/* 291 */     checkParameters();
/*     */     
/* 293 */     int nR = (getTarget()).length;
/* 294 */     double[] currentPoint = getStartPoint();
/* 295 */     int nC = currentPoint.length;
/*     */ 
/*     */     
/* 298 */     this.solvedCols = FastMath.min(nR, nC);
/* 299 */     this.diagR = new double[nC];
/* 300 */     this.jacNorm = new double[nC];
/* 301 */     this.beta = new double[nC];
/* 302 */     this.permutation = new int[nC];
/* 303 */     this.lmDir = new double[nC];
/*     */ 
/*     */     
/* 306 */     double delta = 0.0D;
/* 307 */     double xNorm = 0.0D;
/* 308 */     double[] diag = new double[nC];
/* 309 */     double[] oldX = new double[nC];
/* 310 */     double[] oldRes = new double[nR];
/* 311 */     double[] oldObj = new double[nR];
/* 312 */     double[] qtf = new double[nR];
/* 313 */     double[] work1 = new double[nC];
/* 314 */     double[] work2 = new double[nC];
/* 315 */     double[] work3 = new double[nC];
/*     */     
/* 317 */     RealMatrix weightMatrixSqrt = getWeightSquareRoot();
/*     */ 
/*     */     
/* 320 */     double[] currentObjective = computeObjectiveValue(currentPoint);
/* 321 */     double[] currentResiduals = computeResiduals(currentObjective);
/* 322 */     PointVectorValuePair current = new PointVectorValuePair(currentPoint, currentObjective);
/* 323 */     double currentCost = computeCost(currentResiduals);
/*     */ 
/*     */     
/* 326 */     this.lmPar = 0.0D;
/* 327 */     boolean firstIteration = true;
/* 328 */     ConvergenceChecker<PointVectorValuePair> checker = getConvergenceChecker();
/*     */     while (true) {
/* 330 */       incrementIterationCount();
/*     */       
/* 332 */       PointVectorValuePair previous = current;
/*     */ 
/*     */       
/* 335 */       qrDecomposition(computeWeightedJacobian(currentPoint));
/*     */       
/* 337 */       this.weightedResidual = weightMatrixSqrt.operate(currentResiduals);
/* 338 */       for (int i = 0; i < nR; i++) {
/* 339 */         qtf[i] = this.weightedResidual[i];
/*     */       }
/*     */ 
/*     */       
/* 343 */       qTy(qtf);
/*     */       
/*     */       int k;
/*     */       
/* 347 */       for (k = 0; k < this.solvedCols; k++) {
/* 348 */         int pk = this.permutation[k];
/* 349 */         this.weightedJacobian[k][pk] = this.diagR[pk];
/*     */       } 
/*     */       
/* 352 */       if (firstIteration) {
/*     */ 
/*     */         
/* 355 */         xNorm = 0.0D;
/* 356 */         for (k = 0; k < nC; k++) {
/* 357 */           double dk = this.jacNorm[k];
/* 358 */           if (dk == 0.0D) {
/* 359 */             dk = 1.0D;
/*     */           }
/* 361 */           double xk = dk * currentPoint[k];
/* 362 */           xNorm += xk * xk;
/* 363 */           diag[k] = dk;
/*     */         } 
/* 365 */         xNorm = FastMath.sqrt(xNorm);
/*     */ 
/*     */         
/* 368 */         delta = (xNorm == 0.0D) ? this.initialStepBoundFactor : (this.initialStepBoundFactor * xNorm);
/*     */       } 
/*     */ 
/*     */       
/* 372 */       double maxCosine = 0.0D;
/* 373 */       if (currentCost != 0.0D) {
/* 374 */         for (int m = 0; m < this.solvedCols; m++) {
/* 375 */           int pj = this.permutation[m];
/* 376 */           double s = this.jacNorm[pj];
/* 377 */           if (s != 0.0D) {
/* 378 */             double sum = 0.0D;
/* 379 */             for (int n = 0; n <= m; n++) {
/* 380 */               sum += this.weightedJacobian[n][pj] * qtf[n];
/*     */             }
/* 382 */             maxCosine = FastMath.max(maxCosine, FastMath.abs(sum) / s * currentCost);
/*     */           } 
/*     */         } 
/*     */       }
/* 386 */       if (maxCosine <= this.orthoTolerance) {
/*     */         
/* 388 */         setCost(currentCost);
/* 389 */         return current;
/*     */       } 
/*     */ 
/*     */       
/* 393 */       for (int j = 0; j < nC; j++) {
/* 394 */         diag[j] = FastMath.max(diag[j], this.jacNorm[j]);
/*     */       }
/*     */ 
/*     */       
/* 398 */       for (double ratio = 0.0D; ratio < 1.0E-4D; ) {
/*     */ 
/*     */         
/* 401 */         for (int m = 0; m < this.solvedCols; m++) {
/* 402 */           int pj = this.permutation[m];
/* 403 */           oldX[pj] = currentPoint[pj];
/*     */         } 
/* 405 */         double previousCost = currentCost;
/* 406 */         double[] tmpVec = this.weightedResidual;
/* 407 */         this.weightedResidual = oldRes;
/* 408 */         oldRes = tmpVec;
/* 409 */         tmpVec = currentObjective;
/* 410 */         currentObjective = oldObj;
/* 411 */         oldObj = tmpVec;
/*     */ 
/*     */         
/* 414 */         determineLMParameter(qtf, delta, diag, work1, work2, work3);
/*     */ 
/*     */         
/* 417 */         double lmNorm = 0.0D;
/* 418 */         for (int n = 0; n < this.solvedCols; n++) {
/* 419 */           int pj = this.permutation[n];
/* 420 */           this.lmDir[pj] = -this.lmDir[pj];
/* 421 */           currentPoint[pj] = oldX[pj] + this.lmDir[pj];
/* 422 */           double s = diag[pj] * this.lmDir[pj];
/* 423 */           lmNorm += s * s;
/*     */         } 
/* 425 */         lmNorm = FastMath.sqrt(lmNorm);
/*     */         
/* 427 */         if (firstIteration) {
/* 428 */           delta = FastMath.min(delta, lmNorm);
/*     */         }
/*     */ 
/*     */         
/* 432 */         currentObjective = computeObjectiveValue(currentPoint);
/* 433 */         currentResiduals = computeResiduals(currentObjective);
/* 434 */         current = new PointVectorValuePair(currentPoint, currentObjective);
/* 435 */         currentCost = computeCost(currentResiduals);
/*     */ 
/*     */         
/* 438 */         double actRed = -1.0D;
/* 439 */         if (0.1D * currentCost < previousCost) {
/* 440 */           double r = currentCost / previousCost;
/* 441 */           actRed = 1.0D - r * r;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 446 */         for (int i1 = 0; i1 < this.solvedCols; i1++) {
/* 447 */           int pj = this.permutation[i1];
/* 448 */           double dirJ = this.lmDir[pj];
/* 449 */           work1[i1] = 0.0D;
/* 450 */           for (int i3 = 0; i3 <= i1; i3++) {
/* 451 */             work1[i3] = work1[i3] + this.weightedJacobian[i3][pj] * dirJ;
/*     */           }
/*     */         } 
/* 454 */         double coeff1 = 0.0D;
/* 455 */         for (int i2 = 0; i2 < this.solvedCols; i2++) {
/* 456 */           coeff1 += work1[i2] * work1[i2];
/*     */         }
/* 458 */         double pc2 = previousCost * previousCost;
/* 459 */         coeff1 /= pc2;
/* 460 */         double coeff2 = this.lmPar * lmNorm * lmNorm / pc2;
/* 461 */         double preRed = coeff1 + 2.0D * coeff2;
/* 462 */         double dirDer = -(coeff1 + coeff2);
/*     */ 
/*     */         
/* 465 */         ratio = (preRed == 0.0D) ? 0.0D : (actRed / preRed);
/*     */ 
/*     */         
/* 468 */         if (ratio <= 0.25D) {
/* 469 */           double tmp = (actRed < 0.0D) ? (0.5D * dirDer / (dirDer + 0.5D * actRed)) : 0.5D;
/*     */           
/* 471 */           if (0.1D * currentCost >= previousCost || tmp < 0.1D) {
/* 472 */             tmp = 0.1D;
/*     */           }
/* 474 */           delta = tmp * FastMath.min(delta, 10.0D * lmNorm);
/* 475 */           this.lmPar /= tmp;
/* 476 */         } else if (this.lmPar == 0.0D || ratio >= 0.75D) {
/* 477 */           delta = 2.0D * lmNorm;
/* 478 */           this.lmPar *= 0.5D;
/*     */         } 
/*     */ 
/*     */         
/* 482 */         if (ratio >= 1.0E-4D) {
/*     */           
/* 484 */           firstIteration = false;
/* 485 */           xNorm = 0.0D;
/* 486 */           for (int i3 = 0; i3 < nC; i3++) {
/* 487 */             double xK = diag[i3] * currentPoint[i3];
/* 488 */             xNorm += xK * xK;
/*     */           } 
/* 490 */           xNorm = FastMath.sqrt(xNorm);
/*     */ 
/*     */           
/* 493 */           if (checker != null && checker.converged(getIterations(), previous, current)) {
/* 494 */             setCost(currentCost);
/* 495 */             return current;
/*     */           } 
/*     */         } else {
/*     */           
/* 499 */           currentCost = previousCost;
/* 500 */           for (int i3 = 0; i3 < this.solvedCols; i3++) {
/* 501 */             int pj = this.permutation[i3];
/* 502 */             currentPoint[pj] = oldX[pj];
/*     */           } 
/* 504 */           tmpVec = this.weightedResidual;
/* 505 */           this.weightedResidual = oldRes;
/* 506 */           oldRes = tmpVec;
/* 507 */           tmpVec = currentObjective;
/* 508 */           currentObjective = oldObj;
/* 509 */           oldObj = tmpVec;
/*     */           
/* 511 */           current = new PointVectorValuePair(currentPoint, currentObjective);
/*     */         } 
/*     */ 
/*     */         
/* 515 */         if ((FastMath.abs(actRed) <= this.costRelativeTolerance && preRed <= this.costRelativeTolerance && ratio <= 2.0D) || delta <= this.parRelativeTolerance * xNorm) {
/*     */ 
/*     */ 
/*     */           
/* 519 */           setCost(currentCost);
/* 520 */           return current;
/*     */         } 
/*     */ 
/*     */         
/* 524 */         if (FastMath.abs(actRed) <= TWO_EPS && preRed <= TWO_EPS && ratio <= 2.0D)
/*     */         {
/*     */           
/* 527 */           throw new ConvergenceException(LocalizedFormats.TOO_SMALL_COST_RELATIVE_TOLERANCE, new Object[] { Double.valueOf(this.costRelativeTolerance) });
/*     */         }
/* 529 */         if (delta <= TWO_EPS * xNorm) {
/* 530 */           throw new ConvergenceException(LocalizedFormats.TOO_SMALL_PARAMETERS_RELATIVE_TOLERANCE, new Object[] { Double.valueOf(this.parRelativeTolerance) });
/*     */         }
/* 532 */         if (maxCosine <= TWO_EPS) {
/* 533 */           throw new ConvergenceException(LocalizedFormats.TOO_SMALL_ORTHOGONALITY_TOLERANCE, new Object[] { Double.valueOf(this.orthoTolerance) });
/*     */         }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void determineLMParameter(double[] qy, double delta, double[] diag, double[] work1, double[] work2, double[] work3) {
/* 564 */     int nC = (this.weightedJacobian[0]).length;
/*     */     
/*     */     int j;
/*     */     
/* 568 */     for (j = 0; j < this.rank; j++) {
/* 569 */       this.lmDir[this.permutation[j]] = qy[j];
/*     */     }
/* 571 */     for (j = this.rank; j < nC; j++) {
/* 572 */       this.lmDir[this.permutation[j]] = 0.0D;
/*     */     }
/* 574 */     for (int k = this.rank - 1; k >= 0; k--) {
/* 575 */       int pk = this.permutation[k];
/* 576 */       double ypk = this.lmDir[pk] / this.diagR[pk];
/* 577 */       for (int n = 0; n < k; n++) {
/* 578 */         this.lmDir[this.permutation[n]] = this.lmDir[this.permutation[n]] - ypk * this.weightedJacobian[n][pk];
/*     */       }
/* 580 */       this.lmDir[pk] = ypk;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 585 */     double dxNorm = 0.0D;
/* 586 */     for (int i = 0; i < this.solvedCols; i++) {
/* 587 */       int pj = this.permutation[i];
/* 588 */       double s = diag[pj] * this.lmDir[pj];
/* 589 */       work1[pj] = s;
/* 590 */       dxNorm += s * s;
/*     */     } 
/* 592 */     dxNorm = FastMath.sqrt(dxNorm);
/* 593 */     double fp = dxNorm - delta;
/* 594 */     if (fp <= 0.1D * delta) {
/* 595 */       this.lmPar = 0.0D;
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 603 */     double parl = 0.0D;
/* 604 */     if (this.rank == this.solvedCols) {
/* 605 */       int n; for (n = 0; n < this.solvedCols; n++) {
/* 606 */         int pj = this.permutation[n];
/* 607 */         work1[pj] = work1[pj] * diag[pj] / dxNorm;
/*     */       } 
/* 609 */       double d = 0.0D;
/* 610 */       for (n = 0; n < this.solvedCols; n++) {
/* 611 */         int pj = this.permutation[n];
/* 612 */         double sum = 0.0D;
/* 613 */         for (int i1 = 0; i1 < n; i1++) {
/* 614 */           sum += this.weightedJacobian[i1][pj] * work1[this.permutation[i1]];
/*     */         }
/* 616 */         double s = (work1[pj] - sum) / this.diagR[pj];
/* 617 */         work1[pj] = s;
/* 618 */         d += s * s;
/*     */       } 
/* 620 */       parl = fp / delta * d;
/*     */     } 
/*     */ 
/*     */     
/* 624 */     double sum2 = 0.0D;
/* 625 */     for (int m = 0; m < this.solvedCols; m++) {
/* 626 */       int pj = this.permutation[m];
/* 627 */       double sum = 0.0D;
/* 628 */       for (int n = 0; n <= m; n++) {
/* 629 */         sum += this.weightedJacobian[n][pj] * qy[n];
/*     */       }
/* 631 */       sum /= diag[pj];
/* 632 */       sum2 += sum * sum;
/*     */     } 
/* 634 */     double gNorm = FastMath.sqrt(sum2);
/* 635 */     double paru = gNorm / delta;
/* 636 */     if (paru == 0.0D) {
/* 637 */       paru = Precision.SAFE_MIN / FastMath.min(delta, 0.1D);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 642 */     this.lmPar = FastMath.min(paru, FastMath.max(this.lmPar, parl));
/* 643 */     if (this.lmPar == 0.0D) {
/* 644 */       this.lmPar = gNorm / dxNorm;
/*     */     }
/*     */     
/* 647 */     for (int countdown = 10; countdown >= 0; countdown--) {
/*     */ 
/*     */       
/* 650 */       if (this.lmPar == 0.0D) {
/* 651 */         this.lmPar = FastMath.max(Precision.SAFE_MIN, 0.001D * paru);
/*     */       }
/* 653 */       double sPar = FastMath.sqrt(this.lmPar); int n;
/* 654 */       for (n = 0; n < this.solvedCols; n++) {
/* 655 */         int pj = this.permutation[n];
/* 656 */         work1[pj] = sPar * diag[pj];
/*     */       } 
/* 658 */       determineLMDirection(qy, work1, work2, work3);
/*     */       
/* 660 */       dxNorm = 0.0D;
/* 661 */       for (n = 0; n < this.solvedCols; n++) {
/* 662 */         int pj = this.permutation[n];
/* 663 */         double s = diag[pj] * this.lmDir[pj];
/* 664 */         work3[pj] = s;
/* 665 */         dxNorm += s * s;
/*     */       } 
/* 667 */       dxNorm = FastMath.sqrt(dxNorm);
/* 668 */       double previousFP = fp;
/* 669 */       fp = dxNorm - delta;
/*     */ 
/*     */ 
/*     */       
/* 673 */       if (FastMath.abs(fp) <= 0.1D * delta || (parl == 0.0D && fp <= previousFP && previousFP < 0.0D)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       int i1;
/*     */       
/* 679 */       for (i1 = 0; i1 < this.solvedCols; i1++) {
/* 680 */         int pj = this.permutation[i1];
/* 681 */         work1[pj] = work3[pj] * diag[pj] / dxNorm;
/*     */       } 
/* 683 */       for (i1 = 0; i1 < this.solvedCols; i1++) {
/* 684 */         int pj = this.permutation[i1];
/* 685 */         work1[pj] = work1[pj] / work2[i1];
/* 686 */         double tmp = work1[pj];
/* 687 */         for (int i2 = i1 + 1; i2 < this.solvedCols; i2++) {
/* 688 */           work1[this.permutation[i2]] = work1[this.permutation[i2]] - this.weightedJacobian[i2][pj] * tmp;
/*     */         }
/*     */       } 
/* 691 */       sum2 = 0.0D;
/* 692 */       for (i1 = 0; i1 < this.solvedCols; i1++) {
/* 693 */         double s = work1[this.permutation[i1]];
/* 694 */         sum2 += s * s;
/*     */       } 
/* 696 */       double correction = fp / delta * sum2;
/*     */ 
/*     */       
/* 699 */       if (fp > 0.0D) {
/* 700 */         parl = FastMath.max(parl, this.lmPar);
/* 701 */       } else if (fp < 0.0D) {
/* 702 */         paru = FastMath.min(paru, this.lmPar);
/*     */       } 
/*     */ 
/*     */       
/* 706 */       this.lmPar = FastMath.max(parl, this.lmPar + correction);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void determineLMDirection(double[] qy, double[] diag, double[] lmDiag, double[] work) {
/*     */     int j;
/* 736 */     for (j = 0; j < this.solvedCols; j++) {
/* 737 */       int pj = this.permutation[j];
/* 738 */       for (int k = j + 1; k < this.solvedCols; k++) {
/* 739 */         this.weightedJacobian[k][pj] = this.weightedJacobian[j][this.permutation[k]];
/*     */       }
/* 741 */       this.lmDir[j] = this.diagR[pj];
/* 742 */       work[j] = qy[j];
/*     */     } 
/*     */ 
/*     */     
/* 746 */     for (j = 0; j < this.solvedCols; j++) {
/*     */ 
/*     */ 
/*     */       
/* 750 */       int pj = this.permutation[j];
/* 751 */       double dpj = diag[pj];
/* 752 */       if (dpj != 0.0D) {
/* 753 */         Arrays.fill(lmDiag, j + 1, lmDiag.length, 0.0D);
/*     */       }
/* 755 */       lmDiag[j] = dpj;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 760 */       double qtbpj = 0.0D;
/* 761 */       for (int k = j; k < this.solvedCols; k++) {
/* 762 */         int pk = this.permutation[k];
/*     */ 
/*     */ 
/*     */         
/* 766 */         if (lmDiag[k] != 0.0D) {
/*     */ 
/*     */ 
/*     */           
/* 770 */           double sin, cos, rkk = this.weightedJacobian[k][pk];
/* 771 */           if (FastMath.abs(rkk) < FastMath.abs(lmDiag[k])) {
/* 772 */             double cotan = rkk / lmDiag[k];
/* 773 */             sin = 1.0D / FastMath.sqrt(1.0D + cotan * cotan);
/* 774 */             cos = sin * cotan;
/*     */           } else {
/* 776 */             double tan = lmDiag[k] / rkk;
/* 777 */             cos = 1.0D / FastMath.sqrt(1.0D + tan * tan);
/* 778 */             sin = cos * tan;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 783 */           this.weightedJacobian[k][pk] = cos * rkk + sin * lmDiag[k];
/* 784 */           double temp = cos * work[k] + sin * qtbpj;
/* 785 */           qtbpj = -sin * work[k] + cos * qtbpj;
/* 786 */           work[k] = temp;
/*     */ 
/*     */           
/* 789 */           for (int m = k + 1; m < this.solvedCols; m++) {
/* 790 */             double rik = this.weightedJacobian[m][pk];
/* 791 */             double temp2 = cos * rik + sin * lmDiag[m];
/* 792 */             lmDiag[m] = -sin * rik + cos * lmDiag[m];
/* 793 */             this.weightedJacobian[m][pk] = temp2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 800 */       lmDiag[j] = this.weightedJacobian[j][this.permutation[j]];
/* 801 */       this.weightedJacobian[j][this.permutation[j]] = this.lmDir[j];
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 806 */     int nSing = this.solvedCols; int i;
/* 807 */     for (i = 0; i < this.solvedCols; i++) {
/* 808 */       if (lmDiag[i] == 0.0D && nSing == this.solvedCols) {
/* 809 */         nSing = i;
/*     */       }
/* 811 */       if (nSing < this.solvedCols) {
/* 812 */         work[i] = 0.0D;
/*     */       }
/*     */     } 
/* 815 */     if (nSing > 0) {
/* 816 */       for (i = nSing - 1; i >= 0; i--) {
/* 817 */         int pj = this.permutation[i];
/* 818 */         double sum = 0.0D;
/* 819 */         for (int k = i + 1; k < nSing; k++) {
/* 820 */           sum += this.weightedJacobian[k][pj] * work[k];
/*     */         }
/* 822 */         work[i] = (work[i] - sum) / lmDiag[i];
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 827 */     for (i = 0; i < this.lmDir.length; i++) {
/* 828 */       this.lmDir[this.permutation[i]] = work[i];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void qrDecomposition(RealMatrix jacobian) throws ConvergenceException {
/* 859 */     this.weightedJacobian = jacobian.scalarMultiply(-1.0D).getData();
/*     */     
/* 861 */     int nR = this.weightedJacobian.length;
/* 862 */     int nC = (this.weightedJacobian[0]).length;
/*     */     
/*     */     int k;
/* 865 */     for (k = 0; k < nC; k++) {
/* 866 */       this.permutation[k] = k;
/* 867 */       double norm2 = 0.0D;
/* 868 */       for (int i = 0; i < nR; i++) {
/* 869 */         double akk = this.weightedJacobian[i][k];
/* 870 */         norm2 += akk * akk;
/*     */       } 
/* 872 */       this.jacNorm[k] = FastMath.sqrt(norm2);
/*     */     } 
/*     */ 
/*     */     
/* 876 */     for (k = 0; k < nC; k++) {
/*     */ 
/*     */       
/* 879 */       int nextColumn = -1;
/* 880 */       double ak2 = Double.NEGATIVE_INFINITY;
/* 881 */       for (int i = k; i < nC; i++) {
/* 882 */         double norm2 = 0.0D;
/* 883 */         for (int j = k; j < nR; j++) {
/* 884 */           double aki = this.weightedJacobian[j][this.permutation[i]];
/* 885 */           norm2 += aki * aki;
/*     */         } 
/* 887 */         if (Double.isInfinite(norm2) || Double.isNaN(norm2)) {
/* 888 */           throw new ConvergenceException(LocalizedFormats.UNABLE_TO_PERFORM_QR_DECOMPOSITION_ON_JACOBIAN, new Object[] { Integer.valueOf(nR), Integer.valueOf(nC) });
/*     */         }
/*     */         
/* 891 */         if (norm2 > ak2) {
/* 892 */           nextColumn = i;
/* 893 */           ak2 = norm2;
/*     */         } 
/*     */       } 
/* 896 */       if (ak2 <= this.qrRankingThreshold) {
/* 897 */         this.rank = k;
/*     */         return;
/*     */       } 
/* 900 */       int pk = this.permutation[nextColumn];
/* 901 */       this.permutation[nextColumn] = this.permutation[k];
/* 902 */       this.permutation[k] = pk;
/*     */ 
/*     */       
/* 905 */       double akk = this.weightedJacobian[k][pk];
/* 906 */       double alpha = (akk > 0.0D) ? -FastMath.sqrt(ak2) : FastMath.sqrt(ak2);
/* 907 */       double betak = 1.0D / (ak2 - akk * alpha);
/* 908 */       this.beta[pk] = betak;
/*     */ 
/*     */       
/* 911 */       this.diagR[pk] = alpha;
/* 912 */       this.weightedJacobian[k][pk] = this.weightedJacobian[k][pk] - alpha;
/*     */ 
/*     */       
/* 915 */       for (int dk = nC - 1 - k; dk > 0; dk--) {
/* 916 */         double gamma = 0.0D; int j;
/* 917 */         for (j = k; j < nR; j++) {
/* 918 */           gamma += this.weightedJacobian[j][pk] * this.weightedJacobian[j][this.permutation[k + dk]];
/*     */         }
/* 920 */         gamma *= betak;
/* 921 */         for (j = k; j < nR; j++) {
/* 922 */           this.weightedJacobian[j][this.permutation[k + dk]] = this.weightedJacobian[j][this.permutation[k + dk]] - gamma * this.weightedJacobian[j][pk];
/*     */         }
/*     */       } 
/*     */     } 
/* 926 */     this.rank = this.solvedCols;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void qTy(double[] y) {
/* 935 */     int nR = this.weightedJacobian.length;
/* 936 */     int nC = (this.weightedJacobian[0]).length;
/*     */     
/* 938 */     for (int k = 0; k < nC; k++) {
/* 939 */       int pk = this.permutation[k];
/* 940 */       double gamma = 0.0D; int i;
/* 941 */       for (i = k; i < nR; i++) {
/* 942 */         gamma += this.weightedJacobian[i][pk] * y[i];
/*     */       }
/* 944 */       gamma *= this.beta[pk];
/* 945 */       for (i = k; i < nR; i++) {
/* 946 */         y[i] = y[i] - gamma * this.weightedJacobian[i][pk];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkParameters() {
/* 956 */     if (getLowerBound() != null || getUpperBound() != null)
/*     */     {
/* 958 */       throw new MathUnsupportedOperationException(LocalizedFormats.CONSTRAINT, new Object[0]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/vector/jacobian/LevenbergMarquardtOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */