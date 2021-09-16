/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.ode.ExpandableStatefulODE;
/*     */ import org.apache.commons.math3.ode.events.EventHandler;
/*     */ import org.apache.commons.math3.ode.sampling.AbstractStepInterpolator;
/*     */ import org.apache.commons.math3.ode.sampling.StepHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GraggBulirschStoerIntegrator
/*     */   extends AdaptiveStepsizeIntegrator
/*     */ {
/*     */   private static final String METHOD_NAME = "Gragg-Bulirsch-Stoer";
/*     */   private int maxOrder;
/*     */   private int[] sequence;
/*     */   private int[] costPerStep;
/*     */   private double[] costPerTimeUnit;
/*     */   private double[] optimalStep;
/*     */   private double[][] coeff;
/*     */   private boolean performTest;
/*     */   private int maxChecks;
/*     */   private int maxIter;
/*     */   private double stabilityReduction;
/*     */   private double stepControl1;
/*     */   private double stepControl2;
/*     */   private double stepControl3;
/*     */   private double stepControl4;
/*     */   private double orderControl1;
/*     */   private double orderControl2;
/*     */   private boolean useInterpolationError;
/*     */   private int mudif;
/*     */   
/*     */   public GraggBulirschStoerIntegrator(double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
/* 171 */     super("Gragg-Bulirsch-Stoer", minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
/*     */     
/* 173 */     setStabilityCheck(true, -1, -1, -1.0D);
/* 174 */     setControlFactors(-1.0D, -1.0D, -1.0D, -1.0D);
/* 175 */     setOrderControl(-1, -1.0D, -1.0D);
/* 176 */     setInterpolationControl(true, -1);
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
/*     */   public GraggBulirschStoerIntegrator(double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
/* 193 */     super("Gragg-Bulirsch-Stoer", minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/*     */     
/* 195 */     setStabilityCheck(true, -1, -1, -1.0D);
/* 196 */     setControlFactors(-1.0D, -1.0D, -1.0D, -1.0D);
/* 197 */     setOrderControl(-1, -1.0D, -1.0D);
/* 198 */     setInterpolationControl(true, -1);
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
/*     */   public void setStabilityCheck(boolean performStabilityCheck, int maxNumIter, int maxNumChecks, double stepsizeReductionFactor) {
/* 223 */     this.performTest = performStabilityCheck;
/* 224 */     this.maxIter = (maxNumIter <= 0) ? 2 : maxNumIter;
/* 225 */     this.maxChecks = (maxNumChecks <= 0) ? 1 : maxNumChecks;
/*     */     
/* 227 */     if (stepsizeReductionFactor < 1.0E-4D || stepsizeReductionFactor > 0.9999D) {
/* 228 */       this.stabilityReduction = 0.5D;
/*     */     } else {
/* 230 */       this.stabilityReduction = stepsizeReductionFactor;
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
/*     */   
/*     */   public void setControlFactors(double control1, double control2, double control3, double control4) {
/* 262 */     if (control1 < 1.0E-4D || control1 > 0.9999D) {
/* 263 */       this.stepControl1 = 0.65D;
/*     */     } else {
/* 265 */       this.stepControl1 = control1;
/*     */     } 
/*     */     
/* 268 */     if (control2 < 1.0E-4D || control2 > 0.9999D) {
/* 269 */       this.stepControl2 = 0.94D;
/*     */     } else {
/* 271 */       this.stepControl2 = control2;
/*     */     } 
/*     */     
/* 274 */     if (control3 < 1.0E-4D || control3 > 0.9999D) {
/* 275 */       this.stepControl3 = 0.02D;
/*     */     } else {
/* 277 */       this.stepControl3 = control3;
/*     */     } 
/*     */     
/* 280 */     if (control4 < 1.0001D || control4 > 999.9D) {
/* 281 */       this.stepControl4 = 4.0D;
/*     */     } else {
/* 283 */       this.stepControl4 = control4;
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
/*     */   public void setOrderControl(int maximalOrder, double control1, double control2) {
/* 314 */     if (maximalOrder <= 6 || maximalOrder % 2 != 0) {
/* 315 */       this.maxOrder = 18;
/*     */     }
/*     */     
/* 318 */     if (control1 < 1.0E-4D || control1 > 0.9999D) {
/* 319 */       this.orderControl1 = 0.8D;
/*     */     } else {
/* 321 */       this.orderControl1 = control1;
/*     */     } 
/*     */     
/* 324 */     if (control2 < 1.0E-4D || control2 > 0.9999D) {
/* 325 */       this.orderControl2 = 0.9D;
/*     */     } else {
/* 327 */       this.orderControl2 = control2;
/*     */     } 
/*     */ 
/*     */     
/* 331 */     initializeArrays();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStepHandler(StepHandler handler) {
/* 339 */     super.addStepHandler(handler);
/*     */ 
/*     */     
/* 342 */     initializeArrays();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEventHandler(EventHandler function, double maxCheckInterval, double convergence, int maxIterationCount, UnivariateSolver solver) {
/* 353 */     super.addEventHandler(function, maxCheckInterval, convergence, maxIterationCount, solver);
/*     */ 
/*     */ 
/*     */     
/* 357 */     initializeArrays();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeArrays() {
/* 364 */     int size = this.maxOrder / 2;
/*     */     
/* 366 */     if (this.sequence == null || this.sequence.length != size) {
/*     */       
/* 368 */       this.sequence = new int[size];
/* 369 */       this.costPerStep = new int[size];
/* 370 */       this.coeff = new double[size][];
/* 371 */       this.costPerTimeUnit = new double[size];
/* 372 */       this.optimalStep = new double[size];
/*     */     } 
/*     */     
/*     */     int k;
/* 376 */     for (k = 0; k < size; k++) {
/* 377 */       this.sequence[k] = 4 * k + 2;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 382 */     this.costPerStep[0] = this.sequence[0] + 1;
/* 383 */     for (k = 1; k < size; k++) {
/* 384 */       this.costPerStep[k] = this.costPerStep[k - 1] + this.sequence[k];
/*     */     }
/*     */ 
/*     */     
/* 388 */     for (k = 0; k < size; k++) {
/* 389 */       this.coeff[k] = (k > 0) ? new double[k] : null;
/* 390 */       for (int l = 0; l < k; l++) {
/* 391 */         double ratio = this.sequence[k] / this.sequence[k - l - 1];
/* 392 */         this.coeff[k][l] = 1.0D / (ratio * ratio - 1.0D);
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
/*     */   public void setInterpolationControl(boolean useInterpolationErrorForControl, int mudifControlParameter) {
/* 411 */     this.useInterpolationError = useInterpolationErrorForControl;
/*     */     
/* 413 */     if (mudifControlParameter <= 0 || mudifControlParameter >= 7) {
/* 414 */       this.mudif = 4;
/*     */     } else {
/* 416 */       this.mudif = mudifControlParameter;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rescale(double[] y1, double[] y2, double[] scale) {
/* 427 */     if (this.vecAbsoluteTolerance == null) {
/* 428 */       for (int i = 0; i < scale.length; i++) {
/* 429 */         double yi = FastMath.max(FastMath.abs(y1[i]), FastMath.abs(y2[i]));
/* 430 */         scale[i] = this.scalAbsoluteTolerance + this.scalRelativeTolerance * yi;
/*     */       } 
/*     */     } else {
/* 433 */       for (int i = 0; i < scale.length; i++) {
/* 434 */         double yi = FastMath.max(FastMath.abs(y1[i]), FastMath.abs(y2[i]));
/* 435 */         scale[i] = this.vecAbsoluteTolerance[i] + this.vecRelativeTolerance[i] * yi;
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
/*     */   private boolean tryStep(double t0, double[] y0, double step, int k, double[] scale, double[][] f, double[] yMiddle, double[] yEnd, double[] yTmp) throws MaxCountExceededException, DimensionMismatchException {
/* 463 */     int n = this.sequence[k];
/* 464 */     double subStep = step / n;
/* 465 */     double subStep2 = 2.0D * subStep;
/*     */ 
/*     */     
/* 468 */     double t = t0 + subStep;
/* 469 */     for (int m = 0; m < y0.length; m++) {
/* 470 */       yTmp[m] = y0[m];
/* 471 */       yEnd[m] = y0[m] + subStep * f[0][m];
/*     */     } 
/* 473 */     computeDerivatives(t, yEnd, f[1]);
/*     */ 
/*     */     
/* 476 */     for (int j = 1; j < n; j++) {
/*     */       
/* 478 */       if (2 * j == n)
/*     */       {
/* 480 */         System.arraycopy(yEnd, 0, yMiddle, 0, y0.length);
/*     */       }
/*     */       
/* 483 */       t += subStep;
/* 484 */       for (int i1 = 0; i1 < y0.length; i1++) {
/* 485 */         double middle = yEnd[i1];
/* 486 */         yEnd[i1] = yTmp[i1] + subStep2 * f[j][i1];
/* 487 */         yTmp[i1] = middle;
/*     */       } 
/*     */       
/* 490 */       computeDerivatives(t, yEnd, f[j + 1]);
/*     */ 
/*     */       
/* 493 */       if (this.performTest && j <= this.maxChecks && k < this.maxIter) {
/* 494 */         double initialNorm = 0.0D;
/* 495 */         for (int l = 0; l < scale.length; l++) {
/* 496 */           double ratio = f[0][l] / scale[l];
/* 497 */           initialNorm += ratio * ratio;
/*     */         } 
/* 499 */         double deltaNorm = 0.0D;
/* 500 */         for (int i2 = 0; i2 < scale.length; i2++) {
/* 501 */           double ratio = (f[j + 1][i2] - f[0][i2]) / scale[i2];
/* 502 */           deltaNorm += ratio * ratio;
/*     */         } 
/* 504 */         if (deltaNorm > 4.0D * FastMath.max(1.0E-15D, initialNorm)) {
/* 505 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 512 */     for (int i = 0; i < y0.length; i++) {
/* 513 */       yEnd[i] = 0.5D * (yTmp[i] + yEnd[i] + subStep * f[n][i]);
/*     */     }
/*     */     
/* 516 */     return true;
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
/*     */   private void extrapolate(int offset, int k, double[][] diag, double[] last) {
/* 531 */     for (int j = 1; j < k; j++) {
/* 532 */       for (int m = 0; m < last.length; m++)
/*     */       {
/* 534 */         diag[k - j - 1][m] = diag[k - j][m] + this.coeff[k + offset][j - 1] * (diag[k - j][m] - diag[k - j - 1][m]);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 540 */     for (int i = 0; i < last.length; i++)
/*     */     {
/* 542 */       last[i] = diag[0][i] + this.coeff[k + offset][k - 1] * (diag[0][i] - last[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void integrate(ExpandableStatefulODE equations, double t) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException {
/* 552 */     sanityChecks(equations, t);
/* 553 */     setEquations(equations);
/* 554 */     boolean forward = (t > equations.getTime());
/*     */ 
/*     */     
/* 557 */     double[] y0 = equations.getCompleteState();
/* 558 */     double[] y = (double[])y0.clone();
/* 559 */     double[] yDot0 = new double[y.length];
/* 560 */     double[] y1 = new double[y.length];
/* 561 */     double[] yTmp = new double[y.length];
/* 562 */     double[] yTmpDot = new double[y.length];
/*     */     
/* 564 */     double[][] diagonal = new double[this.sequence.length - 1][];
/* 565 */     double[][] y1Diag = new double[this.sequence.length - 1][];
/* 566 */     for (int k = 0; k < this.sequence.length - 1; k++) {
/* 567 */       diagonal[k] = new double[y.length];
/* 568 */       y1Diag[k] = new double[y.length];
/*     */     } 
/*     */     
/* 571 */     double[][][] fk = new double[this.sequence.length][][];
/* 572 */     for (int i = 0; i < this.sequence.length; i++) {
/*     */       
/* 574 */       fk[i] = new double[this.sequence[i] + 1][];
/*     */ 
/*     */       
/* 577 */       fk[i][0] = yDot0;
/*     */       
/* 579 */       for (int l = 0; l < this.sequence[i]; l++) {
/* 580 */         fk[i][l + 1] = new double[y0.length];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 585 */     if (y != y0) {
/* 586 */       System.arraycopy(y0, 0, y, 0, y0.length);
/*     */     }
/*     */     
/* 589 */     double[] yDot1 = new double[y0.length];
/* 590 */     double[][] yMidDots = new double[1 + 2 * this.sequence.length][y0.length];
/*     */ 
/*     */     
/* 593 */     double[] scale = new double[this.mainSetDimension];
/* 594 */     rescale(y, y, scale);
/*     */ 
/*     */     
/* 597 */     double tol = (this.vecRelativeTolerance == null) ? this.scalRelativeTolerance : this.vecRelativeTolerance[0];
/*     */     
/* 599 */     double log10R = FastMath.log10(FastMath.max(1.0E-10D, tol));
/* 600 */     int targetIter = FastMath.max(1, FastMath.min(this.sequence.length - 2, (int)FastMath.floor(0.5D - 0.6D * log10R)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 605 */     AbstractStepInterpolator interpolator = new GraggBulirschStoerStepInterpolator(y, yDot0, y1, yDot1, yMidDots, forward, equations.getPrimaryMapper(), equations.getSecondaryMappers());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 611 */     interpolator.storeTime(equations.getTime());
/*     */     
/* 613 */     this.stepStart = equations.getTime();
/* 614 */     double hNew = 0.0D;
/* 615 */     double maxError = Double.MAX_VALUE;
/* 616 */     boolean previousRejected = false;
/* 617 */     boolean firstTime = true;
/* 618 */     boolean newStep = true;
/* 619 */     boolean firstStepAlreadyComputed = false;
/* 620 */     initIntegration(equations.getTime(), y0, t);
/* 621 */     this.costPerTimeUnit[0] = 0.0D;
/* 622 */     this.isLastStep = false;
/*     */ 
/*     */     
/*     */     do {
/* 626 */       boolean reject = false;
/*     */       
/* 628 */       if (newStep) {
/*     */         
/* 630 */         interpolator.shift();
/*     */ 
/*     */         
/* 633 */         if (!firstStepAlreadyComputed) {
/* 634 */           computeDerivatives(this.stepStart, y, yDot0);
/*     */         }
/*     */         
/* 637 */         if (firstTime) {
/* 638 */           hNew = initializeStep(forward, 2 * targetIter + 1, scale, this.stepStart, y, yDot0, yTmp, yTmpDot);
/*     */         }
/*     */ 
/*     */         
/* 642 */         newStep = false;
/*     */       } 
/*     */ 
/*     */       
/* 646 */       this.stepSize = hNew;
/*     */ 
/*     */       
/* 649 */       if ((forward && this.stepStart + this.stepSize > t) || (!forward && this.stepStart + this.stepSize < t))
/*     */       {
/* 651 */         this.stepSize = t - this.stepStart;
/*     */       }
/* 653 */       double nextT = this.stepStart + this.stepSize;
/* 654 */       this.isLastStep = forward ? ((nextT >= t)) : ((nextT <= t));
/*     */ 
/*     */       
/* 657 */       int j = -1; boolean loop;
/* 658 */       for (loop = true; loop; ) {
/*     */         
/* 660 */         j++;
/*     */ 
/*     */         
/* 663 */         if (!tryStep(this.stepStart, y, this.stepSize, j, scale, fk[j], (j == 0) ? yMidDots[0] : diagonal[j - 1], (j == 0) ? y1 : y1Diag[j - 1], yTmp)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 669 */           hNew = FastMath.abs(filterStep(this.stepSize * this.stabilityReduction, forward, false));
/* 670 */           reject = true;
/* 671 */           loop = false;
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 676 */         if (j > 0) {
/*     */           double ratio;
/*     */ 
/*     */           
/* 680 */           extrapolate(0, j, y1Diag, y1);
/* 681 */           rescale(y, y1, scale);
/*     */ 
/*     */           
/* 684 */           double error = 0.0D;
/* 685 */           for (int m = 0; m < this.mainSetDimension; m++) {
/* 686 */             double e = FastMath.abs(y1[m] - y1Diag[0][m]) / scale[m];
/* 687 */             error += e * e;
/*     */           } 
/* 689 */           error = FastMath.sqrt(error / this.mainSetDimension);
/*     */           
/* 691 */           if (error > 1.0E15D || (j > 1 && error > maxError)) {
/*     */             
/* 693 */             hNew = FastMath.abs(filterStep(this.stepSize * this.stabilityReduction, forward, false));
/* 694 */             reject = true;
/* 695 */             loop = false;
/*     */             continue;
/*     */           } 
/* 698 */           maxError = FastMath.max(4.0D * error, 1.0D);
/*     */ 
/*     */           
/* 701 */           double exp = 1.0D / (2 * j + 1);
/* 702 */           double fac = this.stepControl2 / FastMath.pow(error / this.stepControl1, exp);
/* 703 */           double pow = FastMath.pow(this.stepControl3, exp);
/* 704 */           fac = FastMath.max(pow / this.stepControl4, FastMath.min(1.0D / pow, fac));
/* 705 */           this.optimalStep[j] = FastMath.abs(filterStep(this.stepSize * fac, forward, true));
/* 706 */           this.costPerTimeUnit[j] = this.costPerStep[j] / this.optimalStep[j];
/*     */ 
/*     */           
/* 709 */           switch (j - targetIter) {
/*     */             
/*     */             case -1:
/* 712 */               if (targetIter > 1 && !previousRejected) {
/*     */ 
/*     */                 
/* 715 */                 if (error <= 1.0D) {
/*     */                   
/* 717 */                   loop = false;
/*     */                   
/*     */                   continue;
/*     */                 } 
/*     */                 
/* 722 */                 double d = this.sequence[targetIter] * this.sequence[targetIter + 1] / (this.sequence[0] * this.sequence[0]);
/*     */                 
/* 724 */                 if (error > d * d) {
/*     */ 
/*     */                   
/* 727 */                   reject = true;
/* 728 */                   loop = false;
/* 729 */                   targetIter = j;
/* 730 */                   if (targetIter > 1 && this.costPerTimeUnit[targetIter - 1] < this.orderControl1 * this.costPerTimeUnit[targetIter])
/*     */                   {
/*     */                     
/* 733 */                     targetIter--;
/*     */                   }
/* 735 */                   hNew = this.optimalStep[targetIter];
/*     */                 } 
/*     */               } 
/*     */               continue;
/*     */ 
/*     */             
/*     */             case 0:
/* 742 */               if (error <= 1.0D) {
/*     */                 
/* 744 */                 loop = false;
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */               
/* 749 */               ratio = this.sequence[j + 1] / this.sequence[0];
/* 750 */               if (error > ratio * ratio) {
/*     */ 
/*     */                 
/* 753 */                 reject = true;
/* 754 */                 loop = false;
/* 755 */                 if (targetIter > 1 && this.costPerTimeUnit[targetIter - 1] < this.orderControl1 * this.costPerTimeUnit[targetIter])
/*     */                 {
/*     */                   
/* 758 */                   targetIter--;
/*     */                 }
/* 760 */                 hNew = this.optimalStep[targetIter];
/*     */               } 
/*     */               continue;
/*     */ 
/*     */             
/*     */             case 1:
/* 766 */               if (error > 1.0D) {
/* 767 */                 reject = true;
/* 768 */                 if (targetIter > 1 && this.costPerTimeUnit[targetIter - 1] < this.orderControl1 * this.costPerTimeUnit[targetIter])
/*     */                 {
/*     */                   
/* 771 */                   targetIter--;
/*     */                 }
/* 773 */                 hNew = this.optimalStep[targetIter];
/*     */               } 
/* 775 */               loop = false;
/*     */               continue;
/*     */           } 
/*     */           
/* 779 */           if ((firstTime || this.isLastStep) && error <= 1.0D) {
/* 780 */             loop = false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 791 */       if (!reject)
/*     */       {
/* 793 */         computeDerivatives(this.stepStart + this.stepSize, y1, yDot1);
/*     */       }
/*     */ 
/*     */       
/* 797 */       double hInt = getMaxStep();
/* 798 */       if (!reject) {
/*     */ 
/*     */         
/* 801 */         for (int m = 1; m <= j; m++) {
/* 802 */           extrapolate(0, m, diagonal, yMidDots[0]);
/*     */         }
/*     */         
/* 805 */         int mu = 2 * j - this.mudif + 3;
/*     */         
/* 807 */         for (int l = 0; l < mu; l++) {
/*     */ 
/*     */           
/* 810 */           int l2 = l / 2;
/* 811 */           double factor = FastMath.pow(0.5D * this.sequence[l2], l);
/* 812 */           int middleIndex = (fk[l2]).length / 2;
/* 813 */           for (int i3 = 0; i3 < y0.length; i3++) {
/* 814 */             yMidDots[l + 1][i3] = factor * fk[l2][middleIndex + l][i3];
/*     */           }
/* 816 */           for (int i2 = 1; i2 <= j - l2; i2++) {
/* 817 */             factor = FastMath.pow(0.5D * this.sequence[i2 + l2], l);
/* 818 */             middleIndex = (fk[l2 + i2]).length / 2;
/* 819 */             for (int i4 = 0; i4 < y0.length; i4++) {
/* 820 */               diagonal[i2 - 1][i4] = factor * fk[l2 + i2][middleIndex + l][i4];
/*     */             }
/* 822 */             extrapolate(l2, i2, diagonal, yMidDots[l + 1]);
/*     */           } 
/* 824 */           for (int i1 = 0; i1 < y0.length; i1++) {
/* 825 */             yMidDots[l + 1][i1] = yMidDots[l + 1][i1] * this.stepSize;
/*     */           }
/*     */ 
/*     */           
/* 829 */           for (int n = (l + 1) / 2; n <= j; n++) {
/* 830 */             for (int i4 = (fk[n]).length - 1; i4 >= 2 * (l + 1); i4--) {
/* 831 */               for (int i5 = 0; i5 < y0.length; i5++) {
/* 832 */                 fk[n][i4][i5] = fk[n][i4][i5] - fk[n][i4 - 2][i5];
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 839 */         if (mu >= 0) {
/*     */ 
/*     */           
/* 842 */           GraggBulirschStoerStepInterpolator gbsInterpolator = (GraggBulirschStoerStepInterpolator)interpolator;
/*     */           
/* 844 */           gbsInterpolator.computeCoefficients(mu, this.stepSize);
/*     */           
/* 846 */           if (this.useInterpolationError) {
/*     */             
/* 848 */             double interpError = gbsInterpolator.estimateError(scale);
/* 849 */             hInt = FastMath.abs(this.stepSize / FastMath.max(FastMath.pow(interpError, 1.0D / (mu + 4)), 0.01D));
/*     */             
/* 851 */             if (interpError > 10.0D) {
/* 852 */               hNew = hInt;
/* 853 */               reject = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 861 */       if (!reject) {
/*     */         int optimalIter;
/*     */         
/* 864 */         interpolator.storeTime(this.stepStart + this.stepSize);
/* 865 */         this.stepStart = acceptStep(interpolator, y1, yDot1, t);
/*     */ 
/*     */         
/* 868 */         interpolator.storeTime(this.stepStart);
/* 869 */         System.arraycopy(y1, 0, y, 0, y0.length);
/* 870 */         System.arraycopy(yDot1, 0, yDot0, 0, y0.length);
/* 871 */         firstStepAlreadyComputed = true;
/*     */ 
/*     */         
/* 874 */         if (j == 1) {
/* 875 */           optimalIter = 2;
/* 876 */           if (previousRejected) {
/* 877 */             optimalIter = 1;
/*     */           }
/* 879 */         } else if (j <= targetIter) {
/* 880 */           optimalIter = j;
/* 881 */           if (this.costPerTimeUnit[j - 1] < this.orderControl1 * this.costPerTimeUnit[j]) {
/* 882 */             optimalIter = j - 1;
/* 883 */           } else if (this.costPerTimeUnit[j] < this.orderControl2 * this.costPerTimeUnit[j - 1]) {
/* 884 */             optimalIter = FastMath.min(j + 1, this.sequence.length - 2);
/*     */           } 
/*     */         } else {
/* 887 */           optimalIter = j - 1;
/* 888 */           if (j > 2 && this.costPerTimeUnit[j - 2] < this.orderControl1 * this.costPerTimeUnit[j - 1])
/*     */           {
/* 890 */             optimalIter = j - 2;
/*     */           }
/* 892 */           if (this.costPerTimeUnit[j] < this.orderControl2 * this.costPerTimeUnit[optimalIter]) {
/* 893 */             optimalIter = FastMath.min(j, this.sequence.length - 2);
/*     */           }
/*     */         } 
/*     */         
/* 897 */         if (previousRejected) {
/*     */ 
/*     */           
/* 900 */           targetIter = FastMath.min(optimalIter, j);
/* 901 */           hNew = FastMath.min(FastMath.abs(this.stepSize), this.optimalStep[targetIter]);
/*     */         } else {
/*     */           
/* 904 */           if (optimalIter <= j) {
/* 905 */             hNew = this.optimalStep[optimalIter];
/*     */           }
/* 907 */           else if (j < targetIter && this.costPerTimeUnit[j] < this.orderControl2 * this.costPerTimeUnit[j - 1]) {
/*     */             
/* 909 */             hNew = filterStep(this.optimalStep[j] * this.costPerStep[optimalIter + 1] / this.costPerStep[j], forward, false);
/*     */           } else {
/*     */             
/* 912 */             hNew = filterStep(this.optimalStep[j] * this.costPerStep[optimalIter] / this.costPerStep[j], forward, false);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 917 */           targetIter = optimalIter;
/*     */         } 
/*     */ 
/*     */         
/* 921 */         newStep = true;
/*     */       } 
/*     */ 
/*     */       
/* 925 */       hNew = FastMath.min(hNew, hInt);
/* 926 */       if (!forward) {
/* 927 */         hNew = -hNew;
/*     */       }
/*     */       
/* 930 */       firstTime = false;
/*     */       
/* 932 */       if (reject) {
/* 933 */         this.isLastStep = false;
/* 934 */         previousRejected = true;
/*     */       } else {
/* 936 */         previousRejected = false;
/*     */       }
/*     */     
/* 939 */     } while (!this.isLastStep);
/*     */ 
/*     */     
/* 942 */     equations.setTime(this.stepStart);
/* 943 */     equations.setCompleteState(y);
/*     */     
/* 945 */     resetInternalState();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/GraggBulirschStoerIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */