/*     */ package org.apache.commons.math3.optimization.general;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optimization.PointVectorValuePair;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   private int solvedCols;
/*     */   private double[] diagR;
/*     */   private double[] jacNorm;
/*     */   private double[] beta;
/*     */   private int[] permutation;
/*     */   private int rank;
/*     */   private double lmPar;
/*     */   private double[] lmDir;
/*     */   private final double initialStepBoundFactor;
/*     */   private final double costRelativeTolerance;
/*     */   private final double parRelativeTolerance;
/*     */   private final double orthoTolerance;
/*     */   private final double qrRankingThreshold;
/*     */   private double[] weightedResidual;
/*     */   private double[][] weightedJacobian;
/*     */   
/*     */   public LevenbergMarquardtOptimizer() {
/* 157 */     this(100.0D, 1.0E-10D, 1.0E-10D, 1.0E-10D, Precision.SAFE_MIN);
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
/* 176 */     this(100.0D, checker, 1.0E-10D, 1.0E-10D, 1.0E-10D, Precision.SAFE_MIN);
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
/* 207 */     super(checker);
/* 208 */     this.initialStepBoundFactor = initialStepBoundFactor;
/* 209 */     this.costRelativeTolerance = costRelativeTolerance;
/* 210 */     this.parRelativeTolerance = parRelativeTolerance;
/* 211 */     this.orthoTolerance = orthoTolerance;
/* 212 */     this.qrRankingThreshold = threshold;
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
/* 236 */     this(100.0D, costRelativeTolerance, parRelativeTolerance, orthoTolerance, Precision.SAFE_MIN);
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
/* 269 */     super((ConvergenceChecker<PointVectorValuePair>)null);
/* 270 */     this.initialStepBoundFactor = initialStepBoundFactor;
/* 271 */     this.costRelativeTolerance = costRelativeTolerance;
/* 272 */     this.parRelativeTolerance = parRelativeTolerance;
/* 273 */     this.orthoTolerance = orthoTolerance;
/* 274 */     this.qrRankingThreshold = threshold;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PointVectorValuePair doOptimize() {
/* 280 */     int nR = (getTarget()).length;
/* 281 */     double[] currentPoint = getStartPoint();
/* 282 */     int nC = currentPoint.length;
/*     */ 
/*     */     
/* 285 */     this.solvedCols = FastMath.min(nR, nC);
/* 286 */     this.diagR = new double[nC];
/* 287 */     this.jacNorm = new double[nC];
/* 288 */     this.beta = new double[nC];
/* 289 */     this.permutation = new int[nC];
/* 290 */     this.lmDir = new double[nC];
/*     */ 
/*     */     
/* 293 */     double delta = 0.0D;
/* 294 */     double xNorm = 0.0D;
/* 295 */     double[] diag = new double[nC];
/* 296 */     double[] oldX = new double[nC];
/* 297 */     double[] oldRes = new double[nR];
/* 298 */     double[] oldObj = new double[nR];
/* 299 */     double[] qtf = new double[nR];
/* 300 */     double[] work1 = new double[nC];
/* 301 */     double[] work2 = new double[nC];
/* 302 */     double[] work3 = new double[nC];
/*     */     
/* 304 */     RealMatrix weightMatrixSqrt = getWeightSquareRoot();
/*     */ 
/*     */     
/* 307 */     double[] currentObjective = computeObjectiveValue(currentPoint);
/* 308 */     double[] currentResiduals = computeResiduals(currentObjective);
/* 309 */     PointVectorValuePair current = new PointVectorValuePair(currentPoint, currentObjective);
/* 310 */     double currentCost = computeCost(currentResiduals);
/*     */ 
/*     */     
/* 313 */     this.lmPar = 0.0D;
/* 314 */     boolean firstIteration = true;
/* 315 */     int iter = 0;
/* 316 */     ConvergenceChecker<PointVectorValuePair> checker = getConvergenceChecker();
/*     */     while (true) {
/* 318 */       iter++;
/* 319 */       PointVectorValuePair previous = current;
/*     */ 
/*     */       
/* 322 */       qrDecomposition(computeWeightedJacobian(currentPoint));
/*     */       
/* 324 */       this.weightedResidual = weightMatrixSqrt.operate(currentResiduals);
/* 325 */       for (int i = 0; i < nR; i++) {
/* 326 */         qtf[i] = this.weightedResidual[i];
/*     */       }
/*     */ 
/*     */       
/* 330 */       qTy(qtf);
/*     */       
/*     */       int k;
/*     */       
/* 334 */       for (k = 0; k < this.solvedCols; k++) {
/* 335 */         int pk = this.permutation[k];
/* 336 */         this.weightedJacobian[k][pk] = this.diagR[pk];
/*     */       } 
/*     */       
/* 339 */       if (firstIteration) {
/*     */ 
/*     */         
/* 342 */         xNorm = 0.0D;
/* 343 */         for (k = 0; k < nC; k++) {
/* 344 */           double dk = this.jacNorm[k];
/* 345 */           if (dk == 0.0D) {
/* 346 */             dk = 1.0D;
/*     */           }
/* 348 */           double xk = dk * currentPoint[k];
/* 349 */           xNorm += xk * xk;
/* 350 */           diag[k] = dk;
/*     */         } 
/* 352 */         xNorm = FastMath.sqrt(xNorm);
/*     */ 
/*     */         
/* 355 */         delta = (xNorm == 0.0D) ? this.initialStepBoundFactor : (this.initialStepBoundFactor * xNorm);
/*     */       } 
/*     */ 
/*     */       
/* 359 */       double maxCosine = 0.0D;
/* 360 */       if (currentCost != 0.0D) {
/* 361 */         for (int m = 0; m < this.solvedCols; m++) {
/* 362 */           int pj = this.permutation[m];
/* 363 */           double s = this.jacNorm[pj];
/* 364 */           if (s != 0.0D) {
/* 365 */             double sum = 0.0D;
/* 366 */             for (int n = 0; n <= m; n++) {
/* 367 */               sum += this.weightedJacobian[n][pj] * qtf[n];
/*     */             }
/* 369 */             maxCosine = FastMath.max(maxCosine, FastMath.abs(sum) / s * currentCost);
/*     */           } 
/*     */         } 
/*     */       }
/* 373 */       if (maxCosine <= this.orthoTolerance) {
/*     */         
/* 375 */         setCost(currentCost);
/*     */         
/* 377 */         this.point = current.getPoint();
/* 378 */         return current;
/*     */       } 
/*     */ 
/*     */       
/* 382 */       for (int j = 0; j < nC; j++) {
/* 383 */         diag[j] = FastMath.max(diag[j], this.jacNorm[j]);
/*     */       }
/*     */ 
/*     */       
/* 387 */       for (double ratio = 0.0D; ratio < 1.0E-4D; ) {
/*     */ 
/*     */         
/* 390 */         for (int m = 0; m < this.solvedCols; m++) {
/* 391 */           int pj = this.permutation[m];
/* 392 */           oldX[pj] = currentPoint[pj];
/*     */         } 
/* 394 */         double previousCost = currentCost;
/* 395 */         double[] tmpVec = this.weightedResidual;
/* 396 */         this.weightedResidual = oldRes;
/* 397 */         oldRes = tmpVec;
/* 398 */         tmpVec = currentObjective;
/* 399 */         currentObjective = oldObj;
/* 400 */         oldObj = tmpVec;
/*     */ 
/*     */         
/* 403 */         determineLMParameter(qtf, delta, diag, work1, work2, work3);
/*     */ 
/*     */         
/* 406 */         double lmNorm = 0.0D;
/* 407 */         for (int n = 0; n < this.solvedCols; n++) {
/* 408 */           int pj = this.permutation[n];
/* 409 */           this.lmDir[pj] = -this.lmDir[pj];
/* 410 */           currentPoint[pj] = oldX[pj] + this.lmDir[pj];
/* 411 */           double s = diag[pj] * this.lmDir[pj];
/* 412 */           lmNorm += s * s;
/*     */         } 
/* 414 */         lmNorm = FastMath.sqrt(lmNorm);
/*     */         
/* 416 */         if (firstIteration) {
/* 417 */           delta = FastMath.min(delta, lmNorm);
/*     */         }
/*     */ 
/*     */         
/* 421 */         currentObjective = computeObjectiveValue(currentPoint);
/* 422 */         currentResiduals = computeResiduals(currentObjective);
/* 423 */         current = new PointVectorValuePair(currentPoint, currentObjective);
/* 424 */         currentCost = computeCost(currentResiduals);
/*     */ 
/*     */         
/* 427 */         double actRed = -1.0D;
/* 428 */         if (0.1D * currentCost < previousCost) {
/* 429 */           double r = currentCost / previousCost;
/* 430 */           actRed = 1.0D - r * r;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 435 */         for (int i1 = 0; i1 < this.solvedCols; i1++) {
/* 436 */           int pj = this.permutation[i1];
/* 437 */           double dirJ = this.lmDir[pj];
/* 438 */           work1[i1] = 0.0D;
/* 439 */           for (int i3 = 0; i3 <= i1; i3++) {
/* 440 */             work1[i3] = work1[i3] + this.weightedJacobian[i3][pj] * dirJ;
/*     */           }
/*     */         } 
/* 443 */         double coeff1 = 0.0D;
/* 444 */         for (int i2 = 0; i2 < this.solvedCols; i2++) {
/* 445 */           coeff1 += work1[i2] * work1[i2];
/*     */         }
/* 447 */         double pc2 = previousCost * previousCost;
/* 448 */         coeff1 /= pc2;
/* 449 */         double coeff2 = this.lmPar * lmNorm * lmNorm / pc2;
/* 450 */         double preRed = coeff1 + 2.0D * coeff2;
/* 451 */         double dirDer = -(coeff1 + coeff2);
/*     */ 
/*     */         
/* 454 */         ratio = (preRed == 0.0D) ? 0.0D : (actRed / preRed);
/*     */ 
/*     */         
/* 457 */         if (ratio <= 0.25D) {
/* 458 */           double tmp = (actRed < 0.0D) ? (0.5D * dirDer / (dirDer + 0.5D * actRed)) : 0.5D;
/*     */           
/* 460 */           if (0.1D * currentCost >= previousCost || tmp < 0.1D) {
/* 461 */             tmp = 0.1D;
/*     */           }
/* 463 */           delta = tmp * FastMath.min(delta, 10.0D * lmNorm);
/* 464 */           this.lmPar /= tmp;
/* 465 */         } else if (this.lmPar == 0.0D || ratio >= 0.75D) {
/* 466 */           delta = 2.0D * lmNorm;
/* 467 */           this.lmPar *= 0.5D;
/*     */         } 
/*     */ 
/*     */         
/* 471 */         if (ratio >= 1.0E-4D) {
/*     */           
/* 473 */           firstIteration = false;
/* 474 */           xNorm = 0.0D;
/* 475 */           for (int i3 = 0; i3 < nC; i3++) {
/* 476 */             double xK = diag[i3] * currentPoint[i3];
/* 477 */             xNorm += xK * xK;
/*     */           } 
/* 479 */           xNorm = FastMath.sqrt(xNorm);
/*     */ 
/*     */           
/* 482 */           if (checker != null && checker.converged(iter, previous, current)) {
/* 483 */             setCost(currentCost);
/*     */             
/* 485 */             this.point = current.getPoint();
/* 486 */             return current;
/*     */           } 
/*     */         } else {
/*     */           
/* 490 */           currentCost = previousCost;
/* 491 */           for (int i3 = 0; i3 < this.solvedCols; i3++) {
/* 492 */             int pj = this.permutation[i3];
/* 493 */             currentPoint[pj] = oldX[pj];
/*     */           } 
/* 495 */           tmpVec = this.weightedResidual;
/* 496 */           this.weightedResidual = oldRes;
/* 497 */           oldRes = tmpVec;
/* 498 */           tmpVec = currentObjective;
/* 499 */           currentObjective = oldObj;
/* 500 */           oldObj = tmpVec;
/*     */           
/* 502 */           current = new PointVectorValuePair(currentPoint, currentObjective);
/*     */         } 
/*     */ 
/*     */         
/* 506 */         if ((FastMath.abs(actRed) <= this.costRelativeTolerance && preRed <= this.costRelativeTolerance && ratio <= 2.0D) || delta <= this.parRelativeTolerance * xNorm) {
/*     */ 
/*     */ 
/*     */           
/* 510 */           setCost(currentCost);
/*     */           
/* 512 */           this.point = current.getPoint();
/* 513 */           return current;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 518 */         if (FastMath.abs(actRed) <= 2.2204E-16D && preRed <= 2.2204E-16D && ratio <= 2.0D) {
/* 519 */           throw new ConvergenceException(LocalizedFormats.TOO_SMALL_COST_RELATIVE_TOLERANCE, new Object[] { Double.valueOf(this.costRelativeTolerance) });
/*     */         }
/* 521 */         if (delta <= 2.2204E-16D * xNorm) {
/* 522 */           throw new ConvergenceException(LocalizedFormats.TOO_SMALL_PARAMETERS_RELATIVE_TOLERANCE, new Object[] { Double.valueOf(this.parRelativeTolerance) });
/*     */         }
/* 524 */         if (maxCosine <= 2.2204E-16D) {
/* 525 */           throw new ConvergenceException(LocalizedFormats.TOO_SMALL_ORTHOGONALITY_TOLERANCE, new Object[] { Double.valueOf(this.orthoTolerance) });
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
/* 556 */     int nC = (this.weightedJacobian[0]).length;
/*     */     
/*     */     int j;
/*     */     
/* 560 */     for (j = 0; j < this.rank; j++) {
/* 561 */       this.lmDir[this.permutation[j]] = qy[j];
/*     */     }
/* 563 */     for (j = this.rank; j < nC; j++) {
/* 564 */       this.lmDir[this.permutation[j]] = 0.0D;
/*     */     }
/* 566 */     for (int k = this.rank - 1; k >= 0; k--) {
/* 567 */       int pk = this.permutation[k];
/* 568 */       double ypk = this.lmDir[pk] / this.diagR[pk];
/* 569 */       for (int n = 0; n < k; n++) {
/* 570 */         this.lmDir[this.permutation[n]] = this.lmDir[this.permutation[n]] - ypk * this.weightedJacobian[n][pk];
/*     */       }
/* 572 */       this.lmDir[pk] = ypk;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 577 */     double dxNorm = 0.0D;
/* 578 */     for (int i = 0; i < this.solvedCols; i++) {
/* 579 */       int pj = this.permutation[i];
/* 580 */       double s = diag[pj] * this.lmDir[pj];
/* 581 */       work1[pj] = s;
/* 582 */       dxNorm += s * s;
/*     */     } 
/* 584 */     dxNorm = FastMath.sqrt(dxNorm);
/* 585 */     double fp = dxNorm - delta;
/* 586 */     if (fp <= 0.1D * delta) {
/* 587 */       this.lmPar = 0.0D;
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 595 */     double parl = 0.0D;
/* 596 */     if (this.rank == this.solvedCols) {
/* 597 */       int n; for (n = 0; n < this.solvedCols; n++) {
/* 598 */         int pj = this.permutation[n];
/* 599 */         work1[pj] = work1[pj] * diag[pj] / dxNorm;
/*     */       } 
/* 601 */       double d = 0.0D;
/* 602 */       for (n = 0; n < this.solvedCols; n++) {
/* 603 */         int pj = this.permutation[n];
/* 604 */         double sum = 0.0D;
/* 605 */         for (int i1 = 0; i1 < n; i1++) {
/* 606 */           sum += this.weightedJacobian[i1][pj] * work1[this.permutation[i1]];
/*     */         }
/* 608 */         double s = (work1[pj] - sum) / this.diagR[pj];
/* 609 */         work1[pj] = s;
/* 610 */         d += s * s;
/*     */       } 
/* 612 */       parl = fp / delta * d;
/*     */     } 
/*     */ 
/*     */     
/* 616 */     double sum2 = 0.0D;
/* 617 */     for (int m = 0; m < this.solvedCols; m++) {
/* 618 */       int pj = this.permutation[m];
/* 619 */       double sum = 0.0D;
/* 620 */       for (int n = 0; n <= m; n++) {
/* 621 */         sum += this.weightedJacobian[n][pj] * qy[n];
/*     */       }
/* 623 */       sum /= diag[pj];
/* 624 */       sum2 += sum * sum;
/*     */     } 
/* 626 */     double gNorm = FastMath.sqrt(sum2);
/* 627 */     double paru = gNorm / delta;
/* 628 */     if (paru == 0.0D)
/*     */     {
/* 630 */       paru = 2.2251E-308D / FastMath.min(delta, 0.1D);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 635 */     this.lmPar = FastMath.min(paru, FastMath.max(this.lmPar, parl));
/* 636 */     if (this.lmPar == 0.0D) {
/* 637 */       this.lmPar = gNorm / dxNorm;
/*     */     }
/*     */     
/* 640 */     for (int countdown = 10; countdown >= 0; countdown--) {
/*     */ 
/*     */       
/* 643 */       if (this.lmPar == 0.0D) {
/* 644 */         this.lmPar = FastMath.max(2.2251E-308D, 0.001D * paru);
/*     */       }
/* 646 */       double sPar = FastMath.sqrt(this.lmPar); int n;
/* 647 */       for (n = 0; n < this.solvedCols; n++) {
/* 648 */         int pj = this.permutation[n];
/* 649 */         work1[pj] = sPar * diag[pj];
/*     */       } 
/* 651 */       determineLMDirection(qy, work1, work2, work3);
/*     */       
/* 653 */       dxNorm = 0.0D;
/* 654 */       for (n = 0; n < this.solvedCols; n++) {
/* 655 */         int pj = this.permutation[n];
/* 656 */         double s = diag[pj] * this.lmDir[pj];
/* 657 */         work3[pj] = s;
/* 658 */         dxNorm += s * s;
/*     */       } 
/* 660 */       dxNorm = FastMath.sqrt(dxNorm);
/* 661 */       double previousFP = fp;
/* 662 */       fp = dxNorm - delta;
/*     */ 
/*     */ 
/*     */       
/* 666 */       if (FastMath.abs(fp) <= 0.1D * delta || (parl == 0.0D && fp <= previousFP && previousFP < 0.0D)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       int i1;
/*     */       
/* 672 */       for (i1 = 0; i1 < this.solvedCols; i1++) {
/* 673 */         int pj = this.permutation[i1];
/* 674 */         work1[pj] = work3[pj] * diag[pj] / dxNorm;
/*     */       } 
/* 676 */       for (i1 = 0; i1 < this.solvedCols; i1++) {
/* 677 */         int pj = this.permutation[i1];
/* 678 */         work1[pj] = work1[pj] / work2[i1];
/* 679 */         double tmp = work1[pj];
/* 680 */         for (int i2 = i1 + 1; i2 < this.solvedCols; i2++) {
/* 681 */           work1[this.permutation[i2]] = work1[this.permutation[i2]] - this.weightedJacobian[i2][pj] * tmp;
/*     */         }
/*     */       } 
/* 684 */       sum2 = 0.0D;
/* 685 */       for (i1 = 0; i1 < this.solvedCols; i1++) {
/* 686 */         double s = work1[this.permutation[i1]];
/* 687 */         sum2 += s * s;
/*     */       } 
/* 689 */       double correction = fp / delta * sum2;
/*     */ 
/*     */       
/* 692 */       if (fp > 0.0D) {
/* 693 */         parl = FastMath.max(parl, this.lmPar);
/* 694 */       } else if (fp < 0.0D) {
/* 695 */         paru = FastMath.min(paru, this.lmPar);
/*     */       } 
/*     */ 
/*     */       
/* 699 */       this.lmPar = FastMath.max(parl, this.lmPar + correction);
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
/* 729 */     for (j = 0; j < this.solvedCols; j++) {
/* 730 */       int pj = this.permutation[j];
/* 731 */       for (int k = j + 1; k < this.solvedCols; k++) {
/* 732 */         this.weightedJacobian[k][pj] = this.weightedJacobian[j][this.permutation[k]];
/*     */       }
/* 734 */       this.lmDir[j] = this.diagR[pj];
/* 735 */       work[j] = qy[j];
/*     */     } 
/*     */ 
/*     */     
/* 739 */     for (j = 0; j < this.solvedCols; j++) {
/*     */ 
/*     */ 
/*     */       
/* 743 */       int pj = this.permutation[j];
/* 744 */       double dpj = diag[pj];
/* 745 */       if (dpj != 0.0D) {
/* 746 */         Arrays.fill(lmDiag, j + 1, lmDiag.length, 0.0D);
/*     */       }
/* 748 */       lmDiag[j] = dpj;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 753 */       double qtbpj = 0.0D;
/* 754 */       for (int k = j; k < this.solvedCols; k++) {
/* 755 */         int pk = this.permutation[k];
/*     */ 
/*     */ 
/*     */         
/* 759 */         if (lmDiag[k] != 0.0D) {
/*     */ 
/*     */ 
/*     */           
/* 763 */           double sin, cos, rkk = this.weightedJacobian[k][pk];
/* 764 */           if (FastMath.abs(rkk) < FastMath.abs(lmDiag[k])) {
/* 765 */             double cotan = rkk / lmDiag[k];
/* 766 */             sin = 1.0D / FastMath.sqrt(1.0D + cotan * cotan);
/* 767 */             cos = sin * cotan;
/*     */           } else {
/* 769 */             double tan = lmDiag[k] / rkk;
/* 770 */             cos = 1.0D / FastMath.sqrt(1.0D + tan * tan);
/* 771 */             sin = cos * tan;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 776 */           this.weightedJacobian[k][pk] = cos * rkk + sin * lmDiag[k];
/* 777 */           double temp = cos * work[k] + sin * qtbpj;
/* 778 */           qtbpj = -sin * work[k] + cos * qtbpj;
/* 779 */           work[k] = temp;
/*     */ 
/*     */           
/* 782 */           for (int m = k + 1; m < this.solvedCols; m++) {
/* 783 */             double rik = this.weightedJacobian[m][pk];
/* 784 */             double temp2 = cos * rik + sin * lmDiag[m];
/* 785 */             lmDiag[m] = -sin * rik + cos * lmDiag[m];
/* 786 */             this.weightedJacobian[m][pk] = temp2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 793 */       lmDiag[j] = this.weightedJacobian[j][this.permutation[j]];
/* 794 */       this.weightedJacobian[j][this.permutation[j]] = this.lmDir[j];
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 799 */     int nSing = this.solvedCols; int i;
/* 800 */     for (i = 0; i < this.solvedCols; i++) {
/* 801 */       if (lmDiag[i] == 0.0D && nSing == this.solvedCols) {
/* 802 */         nSing = i;
/*     */       }
/* 804 */       if (nSing < this.solvedCols) {
/* 805 */         work[i] = 0.0D;
/*     */       }
/*     */     } 
/* 808 */     if (nSing > 0) {
/* 809 */       for (i = nSing - 1; i >= 0; i--) {
/* 810 */         int pj = this.permutation[i];
/* 811 */         double sum = 0.0D;
/* 812 */         for (int k = i + 1; k < nSing; k++) {
/* 813 */           sum += this.weightedJacobian[k][pj] * work[k];
/*     */         }
/* 815 */         work[i] = (work[i] - sum) / lmDiag[i];
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 820 */     for (i = 0; i < this.lmDir.length; i++) {
/* 821 */       this.lmDir[this.permutation[i]] = work[i];
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
/* 852 */     this.weightedJacobian = jacobian.scalarMultiply(-1.0D).getData();
/*     */     
/* 854 */     int nR = this.weightedJacobian.length;
/* 855 */     int nC = (this.weightedJacobian[0]).length;
/*     */     
/*     */     int k;
/* 858 */     for (k = 0; k < nC; k++) {
/* 859 */       this.permutation[k] = k;
/* 860 */       double norm2 = 0.0D;
/* 861 */       for (int i = 0; i < nR; i++) {
/* 862 */         double akk = this.weightedJacobian[i][k];
/* 863 */         norm2 += akk * akk;
/*     */       } 
/* 865 */       this.jacNorm[k] = FastMath.sqrt(norm2);
/*     */     } 
/*     */ 
/*     */     
/* 869 */     for (k = 0; k < nC; k++) {
/*     */ 
/*     */       
/* 872 */       int nextColumn = -1;
/* 873 */       double ak2 = Double.NEGATIVE_INFINITY;
/* 874 */       for (int i = k; i < nC; i++) {
/* 875 */         double norm2 = 0.0D;
/* 876 */         for (int j = k; j < nR; j++) {
/* 877 */           double aki = this.weightedJacobian[j][this.permutation[i]];
/* 878 */           norm2 += aki * aki;
/*     */         } 
/* 880 */         if (Double.isInfinite(norm2) || Double.isNaN(norm2)) {
/* 881 */           throw new ConvergenceException(LocalizedFormats.UNABLE_TO_PERFORM_QR_DECOMPOSITION_ON_JACOBIAN, new Object[] { Integer.valueOf(nR), Integer.valueOf(nC) });
/*     */         }
/*     */         
/* 884 */         if (norm2 > ak2) {
/* 885 */           nextColumn = i;
/* 886 */           ak2 = norm2;
/*     */         } 
/*     */       } 
/* 889 */       if (ak2 <= this.qrRankingThreshold) {
/* 890 */         this.rank = k;
/*     */         return;
/*     */       } 
/* 893 */       int pk = this.permutation[nextColumn];
/* 894 */       this.permutation[nextColumn] = this.permutation[k];
/* 895 */       this.permutation[k] = pk;
/*     */ 
/*     */       
/* 898 */       double akk = this.weightedJacobian[k][pk];
/* 899 */       double alpha = (akk > 0.0D) ? -FastMath.sqrt(ak2) : FastMath.sqrt(ak2);
/* 900 */       double betak = 1.0D / (ak2 - akk * alpha);
/* 901 */       this.beta[pk] = betak;
/*     */ 
/*     */       
/* 904 */       this.diagR[pk] = alpha;
/* 905 */       this.weightedJacobian[k][pk] = this.weightedJacobian[k][pk] - alpha;
/*     */ 
/*     */       
/* 908 */       for (int dk = nC - 1 - k; dk > 0; dk--) {
/* 909 */         double gamma = 0.0D; int j;
/* 910 */         for (j = k; j < nR; j++) {
/* 911 */           gamma += this.weightedJacobian[j][pk] * this.weightedJacobian[j][this.permutation[k + dk]];
/*     */         }
/* 913 */         gamma *= betak;
/* 914 */         for (j = k; j < nR; j++) {
/* 915 */           this.weightedJacobian[j][this.permutation[k + dk]] = this.weightedJacobian[j][this.permutation[k + dk]] - gamma * this.weightedJacobian[j][pk];
/*     */         }
/*     */       } 
/*     */     } 
/* 919 */     this.rank = this.solvedCols;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void qTy(double[] y) {
/* 928 */     int nR = this.weightedJacobian.length;
/* 929 */     int nC = (this.weightedJacobian[0]).length;
/*     */     
/* 931 */     for (int k = 0; k < nC; k++) {
/* 932 */       int pk = this.permutation[k];
/* 933 */       double gamma = 0.0D; int i;
/* 934 */       for (i = k; i < nR; i++) {
/* 935 */         gamma += this.weightedJacobian[i][pk] * y[i];
/*     */       }
/* 937 */       gamma *= this.beta[pk];
/* 938 */       for (i = k; i < nR; i++)
/* 939 */         y[i] = y[i] - gamma * this.weightedJacobian[i][pk]; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/general/LevenbergMarquardtOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */