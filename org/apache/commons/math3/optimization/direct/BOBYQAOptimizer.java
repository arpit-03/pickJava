/*      */ package org.apache.commons.math3.optimization.direct;
/*      */ 
/*      */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*      */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*      */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*      */ import org.apache.commons.math3.exception.OutOfRangeException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*      */ import org.apache.commons.math3.linear.ArrayRealVector;
/*      */ import org.apache.commons.math3.linear.RealVector;
/*      */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*      */ import org.apache.commons.math3.optimization.GoalType;
/*      */ import org.apache.commons.math3.optimization.MultivariateOptimizer;
/*      */ import org.apache.commons.math3.optimization.PointValuePair;
/*      */ import org.apache.commons.math3.util.FastMath;
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
/*      */ @Deprecated
/*      */ public class BOBYQAOptimizer
/*      */   extends BaseAbstractMultivariateSimpleBoundsOptimizer<MultivariateFunction>
/*      */   implements MultivariateOptimizer
/*      */ {
/*      */   public static final int MINIMUM_PROBLEM_DIMENSION = 2;
/*      */   public static final double DEFAULT_INITIAL_RADIUS = 10.0D;
/*      */   public static final double DEFAULT_STOPPING_RADIUS = 1.0E-8D;
/*      */   private static final double ZERO = 0.0D;
/*      */   private static final double ONE = 1.0D;
/*      */   private static final double TWO = 2.0D;
/*      */   private static final double TEN = 10.0D;
/*      */   private static final double SIXTEEN = 16.0D;
/*      */   private static final double TWO_HUNDRED_FIFTY = 250.0D;
/*      */   private static final double MINUS_ONE = -1.0D;
/*      */   private static final double HALF = 0.5D;
/*      */   private static final double ONE_OVER_FOUR = 0.25D;
/*      */   private static final double ONE_OVER_EIGHT = 0.125D;
/*      */   private static final double ONE_OVER_TEN = 0.1D;
/*      */   private static final double ONE_OVER_A_THOUSAND = 0.001D;
/*      */   private final int numberOfInterpolationPoints;
/*      */   private double initialTrustRegionRadius;
/*      */   private final double stoppingTrustRegionRadius;
/*      */   private boolean isMinimize;
/*      */   private ArrayRealVector currentBest;
/*      */   private double[] boundDifference;
/*      */   private int trustRegionCenterInterpolationPointIndex;
/*      */   private Array2DRowRealMatrix bMatrix;
/*      */   private Array2DRowRealMatrix zMatrix;
/*      */   private Array2DRowRealMatrix interpolationPoints;
/*      */   private ArrayRealVector originShift;
/*      */   private ArrayRealVector fAtInterpolationPoints;
/*      */   private ArrayRealVector trustRegionCenterOffset;
/*      */   private ArrayRealVector gradientAtTrustRegionCenter;
/*      */   private ArrayRealVector lowerDifference;
/*      */   private ArrayRealVector upperDifference;
/*      */   private ArrayRealVector modelSecondDerivativesParameters;
/*      */   private ArrayRealVector newPoint;
/*      */   private ArrayRealVector alternativeNewPoint;
/*      */   private ArrayRealVector trialStepPoint;
/*      */   private ArrayRealVector lagrangeValuesAtNewPoint;
/*      */   private ArrayRealVector modelSecondDerivativesValues;
/*      */   
/*      */   public BOBYQAOptimizer(int numberOfInterpolationPoints) {
/*  223 */     this(numberOfInterpolationPoints, 10.0D, 1.0E-8D);
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
/*      */   public BOBYQAOptimizer(int numberOfInterpolationPoints, double initialTrustRegionRadius, double stoppingTrustRegionRadius) {
/*  239 */     super((ConvergenceChecker<PointValuePair>)null);
/*  240 */     this.numberOfInterpolationPoints = numberOfInterpolationPoints;
/*  241 */     this.initialTrustRegionRadius = initialTrustRegionRadius;
/*  242 */     this.stoppingTrustRegionRadius = stoppingTrustRegionRadius;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected PointValuePair doOptimize() {
/*  248 */     double[] lowerBound = getLowerBound();
/*  249 */     double[] upperBound = getUpperBound();
/*      */ 
/*      */     
/*  252 */     setup(lowerBound, upperBound);
/*      */     
/*  254 */     this.isMinimize = (getGoalType() == GoalType.MINIMIZE);
/*  255 */     this.currentBest = new ArrayRealVector(getStartPoint());
/*      */     
/*  257 */     double value = bobyqa(lowerBound, upperBound);
/*      */     
/*  259 */     return new PointValuePair(this.currentBest.getDataRef(), this.isMinimize ? value : -value);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double bobyqa(double[] lowerBound, double[] upperBound) {
/*  300 */     printMethod();
/*      */     
/*  302 */     int n = this.currentBest.getDimension();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  311 */     for (int j = 0; j < n; j++) {
/*  312 */       double boundDiff = this.boundDifference[j];
/*  313 */       this.lowerDifference.setEntry(j, lowerBound[j] - this.currentBest.getEntry(j));
/*  314 */       this.upperDifference.setEntry(j, upperBound[j] - this.currentBest.getEntry(j));
/*  315 */       if (this.lowerDifference.getEntry(j) >= -this.initialTrustRegionRadius) {
/*  316 */         if (this.lowerDifference.getEntry(j) >= 0.0D) {
/*  317 */           this.currentBest.setEntry(j, lowerBound[j]);
/*  318 */           this.lowerDifference.setEntry(j, 0.0D);
/*  319 */           this.upperDifference.setEntry(j, boundDiff);
/*      */         } else {
/*  321 */           this.currentBest.setEntry(j, lowerBound[j] + this.initialTrustRegionRadius);
/*  322 */           this.lowerDifference.setEntry(j, -this.initialTrustRegionRadius);
/*      */           
/*  324 */           double deltaOne = upperBound[j] - this.currentBest.getEntry(j);
/*  325 */           this.upperDifference.setEntry(j, FastMath.max(deltaOne, this.initialTrustRegionRadius));
/*      */         } 
/*  327 */       } else if (this.upperDifference.getEntry(j) <= this.initialTrustRegionRadius) {
/*  328 */         if (this.upperDifference.getEntry(j) <= 0.0D) {
/*  329 */           this.currentBest.setEntry(j, upperBound[j]);
/*  330 */           this.lowerDifference.setEntry(j, -boundDiff);
/*  331 */           this.upperDifference.setEntry(j, 0.0D);
/*      */         } else {
/*  333 */           this.currentBest.setEntry(j, upperBound[j] - this.initialTrustRegionRadius);
/*      */           
/*  335 */           double deltaOne = lowerBound[j] - this.currentBest.getEntry(j);
/*  336 */           double deltaTwo = -this.initialTrustRegionRadius;
/*  337 */           this.lowerDifference.setEntry(j, FastMath.min(deltaOne, deltaTwo));
/*  338 */           this.upperDifference.setEntry(j, this.initialTrustRegionRadius);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  345 */     return bobyqb(lowerBound, upperBound);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double bobyqb(double[] lowerBound, double[] upperBound) {
/*  389 */     printMethod();
/*      */     
/*  391 */     int n = this.currentBest.getDimension();
/*  392 */     int npt = this.numberOfInterpolationPoints;
/*  393 */     int np = n + 1;
/*  394 */     int nptm = npt - np;
/*  395 */     int nh = n * np / 2;
/*      */     
/*  397 */     ArrayRealVector work1 = new ArrayRealVector(n);
/*  398 */     ArrayRealVector work2 = new ArrayRealVector(npt);
/*  399 */     ArrayRealVector work3 = new ArrayRealVector(npt);
/*      */     
/*  401 */     double cauchy = Double.NaN;
/*  402 */     double alpha = Double.NaN;
/*  403 */     double dsq = Double.NaN;
/*  404 */     double crvmin = Double.NaN;
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
/*  418 */     this.trustRegionCenterInterpolationPointIndex = 0;
/*      */     
/*  420 */     prelim(lowerBound, upperBound);
/*  421 */     double xoptsq = 0.0D;
/*  422 */     for (int i = 0; i < n; i++) {
/*  423 */       this.trustRegionCenterOffset.setEntry(i, this.interpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex, i));
/*      */       
/*  425 */       double deltaOne = this.trustRegionCenterOffset.getEntry(i);
/*  426 */       xoptsq += deltaOne * deltaOne;
/*      */     } 
/*  428 */     double fsave = this.fAtInterpolationPoints.getEntry(0);
/*  429 */     int kbase = 0;
/*      */ 
/*      */ 
/*      */     
/*  433 */     int ntrits = 0;
/*  434 */     int itest = 0;
/*  435 */     int knew = 0;
/*  436 */     int nfsav = getEvaluations();
/*  437 */     double rho = this.initialTrustRegionRadius;
/*  438 */     double delta = rho;
/*  439 */     double diffa = 0.0D;
/*  440 */     double diffb = 0.0D;
/*  441 */     double diffc = 0.0D;
/*  442 */     double f = 0.0D;
/*  443 */     double beta = 0.0D;
/*  444 */     double adelt = 0.0D;
/*  445 */     double denom = 0.0D;
/*  446 */     double ratio = 0.0D;
/*  447 */     double dnorm = 0.0D;
/*  448 */     double scaden = 0.0D;
/*  449 */     double biglsq = 0.0D;
/*  450 */     double distsq = 0.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  455 */     int state = 20; while (true) {
/*      */       ArrayRealVector gnew; double[] alphaCauchy; int i2; int m; double bsum; int i1; double fopt; int k; ArrayRealVector xbdi; int i3; ArrayRealVector s; double dx; double vquad; ArrayRealVector hs; ArrayRealVector hred; int j; int ih; double[] dsqCrvmin; int i5; int i4; double diff; double deltaOne; double pqold; double deltaTwo; int i10; int i9; int i8; int i7; int i6; double d1; double d2;
/*  457 */       switch (state) {
/*      */         case 20:
/*  459 */           printState(20);
/*  460 */           if (this.trustRegionCenterInterpolationPointIndex != 0) {
/*  461 */             int i11 = 0;
/*  462 */             for (int i12 = 0; i12 < n; i12++) {
/*  463 */               for (int i13 = 0; i13 <= i12; i13++) {
/*  464 */                 if (i13 < i12) {
/*  465 */                   this.gradientAtTrustRegionCenter.setEntry(i12, this.gradientAtTrustRegionCenter.getEntry(i12) + this.modelSecondDerivativesValues.getEntry(i11) * this.trustRegionCenterOffset.getEntry(i13));
/*      */                 }
/*  467 */                 this.gradientAtTrustRegionCenter.setEntry(i13, this.gradientAtTrustRegionCenter.getEntry(i13) + this.modelSecondDerivativesValues.getEntry(i11) * this.trustRegionCenterOffset.getEntry(i12));
/*  468 */                 i11++;
/*      */               } 
/*      */             } 
/*  471 */             if (getEvaluations() > npt) {
/*  472 */               for (int i13 = 0; i13 < npt; i13++) {
/*  473 */                 double temp = 0.0D;
/*  474 */                 for (int i15 = 0; i15 < n; i15++) {
/*  475 */                   temp += this.interpolationPoints.getEntry(i13, i15) * this.trustRegionCenterOffset.getEntry(i15);
/*      */                 }
/*  477 */                 temp *= this.modelSecondDerivativesParameters.getEntry(i13);
/*  478 */                 for (int i14 = 0; i14 < n; i14++) {
/*  479 */                   this.gradientAtTrustRegionCenter.setEntry(i14, this.gradientAtTrustRegionCenter.getEntry(i14) + temp * this.interpolationPoints.getEntry(i13, i14));
/*      */                 }
/*      */               } 
/*      */             }
/*      */           } 
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
/*      */         case 60:
/*  495 */           printState(60);
/*  496 */           gnew = new ArrayRealVector(n);
/*  497 */           xbdi = new ArrayRealVector(n);
/*  498 */           s = new ArrayRealVector(n);
/*  499 */           hs = new ArrayRealVector(n);
/*  500 */           hred = new ArrayRealVector(n);
/*      */           
/*  502 */           dsqCrvmin = trsbox(delta, gnew, xbdi, s, hs, hred);
/*      */           
/*  504 */           dsq = dsqCrvmin[0];
/*  505 */           crvmin = dsqCrvmin[1];
/*      */ 
/*      */           
/*  508 */           deltaOne = delta;
/*  509 */           deltaTwo = FastMath.sqrt(dsq);
/*  510 */           dnorm = FastMath.min(deltaOne, deltaTwo);
/*  511 */           if (dnorm < 0.5D * rho) {
/*  512 */             ntrits = -1;
/*      */             
/*  514 */             deltaOne = 10.0D * rho;
/*  515 */             distsq = deltaOne * deltaOne;
/*  516 */             if (getEvaluations() <= nfsav + 2) {
/*  517 */               state = 650;
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  527 */             deltaOne = FastMath.max(diffa, diffb);
/*  528 */             double errbig = FastMath.max(deltaOne, diffc);
/*  529 */             double frhosq = rho * 0.125D * rho;
/*  530 */             if (crvmin > 0.0D && errbig > frhosq * crvmin) {
/*      */               
/*  532 */               state = 650; continue;
/*      */             } 
/*  534 */             double bdtol = errbig / rho;
/*  535 */             for (int i11 = 0; i11 < n; i11++) {
/*  536 */               double bdtest = bdtol;
/*  537 */               if (this.newPoint.getEntry(i11) == this.lowerDifference.getEntry(i11)) {
/*  538 */                 bdtest = work1.getEntry(i11);
/*      */               }
/*  540 */               if (this.newPoint.getEntry(i11) == this.upperDifference.getEntry(i11)) {
/*  541 */                 bdtest = -work1.getEntry(i11);
/*      */               }
/*  543 */               if (bdtest < bdtol) {
/*  544 */                 double curv = this.modelSecondDerivativesValues.getEntry((i11 + i11 * i11) / 2);
/*  545 */                 for (int i12 = 0; i12 < npt; i12++) {
/*      */                   
/*  547 */                   double d = this.interpolationPoints.getEntry(i12, i11);
/*  548 */                   curv += this.modelSecondDerivativesParameters.getEntry(i12) * d * d;
/*      */                 } 
/*  550 */                 bdtest += 0.5D * curv * rho;
/*  551 */                 if (bdtest < bdtol) {
/*  552 */                   state = 650;
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } 
/*  557 */             state = 680; continue;
/*      */           } 
/*  559 */           ntrits++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 90:
/*  569 */           printState(90);
/*  570 */           if (dsq <= xoptsq * 0.001D) {
/*  571 */             double fracsq = xoptsq * 0.25D;
/*  572 */             double sumpq = 0.0D;
/*      */ 
/*      */             
/*  575 */             for (int i13 = 0; i13 < npt; i13++) {
/*  576 */               sumpq += this.modelSecondDerivativesParameters.getEntry(i13);
/*  577 */               double sum = -0.5D * xoptsq;
/*  578 */               for (int i16 = 0; i16 < n; i16++) {
/*  579 */                 sum += this.interpolationPoints.getEntry(i13, i16) * this.trustRegionCenterOffset.getEntry(i16);
/*      */               }
/*      */               
/*  582 */               work2.setEntry(i13, sum);
/*  583 */               double temp = fracsq - 0.5D * sum;
/*  584 */               for (int i17 = 0; i17 < n; i17++) {
/*  585 */                 work1.setEntry(i17, this.bMatrix.getEntry(i13, i17));
/*  586 */                 this.lagrangeValuesAtNewPoint.setEntry(i17, sum * this.interpolationPoints.getEntry(i13, i17) + temp * this.trustRegionCenterOffset.getEntry(i17));
/*  587 */                 int ip = npt + i17;
/*  588 */                 for (int i18 = 0; i18 <= i17; i18++) {
/*  589 */                   this.bMatrix.setEntry(ip, i18, this.bMatrix.getEntry(ip, i18) + work1.getEntry(i17) * this.lagrangeValuesAtNewPoint.getEntry(i18) + this.lagrangeValuesAtNewPoint.getEntry(i17) * work1.getEntry(i18));
/*      */                 }
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  599 */             for (int i12 = 0; i12 < nptm; i12++) {
/*  600 */               double sumz = 0.0D;
/*  601 */               double sumw = 0.0D;
/*  602 */               for (int i18 = 0; i18 < npt; i18++) {
/*  603 */                 sumz += this.zMatrix.getEntry(i18, i12);
/*  604 */                 this.lagrangeValuesAtNewPoint.setEntry(i18, work2.getEntry(i18) * this.zMatrix.getEntry(i18, i12));
/*  605 */                 sumw += this.lagrangeValuesAtNewPoint.getEntry(i18);
/*      */               } 
/*  607 */               for (int i17 = 0; i17 < n; i17++) {
/*  608 */                 double sum = (fracsq * sumz - 0.5D * sumw) * this.trustRegionCenterOffset.getEntry(i17); int i19;
/*  609 */                 for (i19 = 0; i19 < npt; i19++) {
/*  610 */                   sum += this.lagrangeValuesAtNewPoint.getEntry(i19) * this.interpolationPoints.getEntry(i19, i17);
/*      */                 }
/*  612 */                 work1.setEntry(i17, sum);
/*  613 */                 for (i19 = 0; i19 < npt; i19++) {
/*  614 */                   this.bMatrix.setEntry(i19, i17, this.bMatrix.getEntry(i19, i17) + sum * this.zMatrix.getEntry(i19, i12));
/*      */                 }
/*      */               } 
/*      */ 
/*      */               
/*  619 */               for (int i16 = 0; i16 < n; i16++) {
/*  620 */                 int ip = i16 + npt;
/*  621 */                 double temp = work1.getEntry(i16);
/*  622 */                 for (int i19 = 0; i19 <= i16; i19++) {
/*  623 */                   this.bMatrix.setEntry(ip, i19, this.bMatrix.getEntry(ip, i19) + temp * work1.getEntry(i19));
/*      */                 }
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  633 */             int i11 = 0;
/*  634 */             for (int i15 = 0; i15 < n; i15++) {
/*  635 */               work1.setEntry(i15, -0.5D * sumpq * this.trustRegionCenterOffset.getEntry(i15));
/*  636 */               for (int i17 = 0; i17 < npt; i17++) {
/*  637 */                 work1.setEntry(i15, work1.getEntry(i15) + this.modelSecondDerivativesParameters.getEntry(i17) * this.interpolationPoints.getEntry(i17, i15));
/*  638 */                 this.interpolationPoints.setEntry(i17, i15, this.interpolationPoints.getEntry(i17, i15) - this.trustRegionCenterOffset.getEntry(i15));
/*      */               } 
/*  640 */               for (int i16 = 0; i16 <= i15; i16++) {
/*  641 */                 this.modelSecondDerivativesValues.setEntry(i11, this.modelSecondDerivativesValues.getEntry(i11) + work1.getEntry(i16) * this.trustRegionCenterOffset.getEntry(i15) + this.trustRegionCenterOffset.getEntry(i16) * work1.getEntry(i15));
/*      */ 
/*      */ 
/*      */                 
/*  645 */                 this.bMatrix.setEntry(npt + i16, i15, this.bMatrix.getEntry(npt + i15, i16));
/*  646 */                 i11++;
/*      */               } 
/*      */             } 
/*  649 */             for (int i14 = 0; i14 < n; i14++) {
/*  650 */               this.originShift.setEntry(i14, this.originShift.getEntry(i14) + this.trustRegionCenterOffset.getEntry(i14));
/*  651 */               this.newPoint.setEntry(i14, this.newPoint.getEntry(i14) - this.trustRegionCenterOffset.getEntry(i14));
/*  652 */               this.lowerDifference.setEntry(i14, this.lowerDifference.getEntry(i14) - this.trustRegionCenterOffset.getEntry(i14));
/*  653 */               this.upperDifference.setEntry(i14, this.upperDifference.getEntry(i14) - this.trustRegionCenterOffset.getEntry(i14));
/*  654 */               this.trustRegionCenterOffset.setEntry(i14, 0.0D);
/*      */             } 
/*  656 */             xoptsq = 0.0D;
/*      */           } 
/*  658 */           if (ntrits == 0) {
/*  659 */             state = 210; continue;
/*      */           } 
/*  661 */           state = 230;
/*      */           continue;
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
/*      */         case 210:
/*  674 */           printState(210);
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
/*  686 */           alphaCauchy = altmov(knew, adelt);
/*  687 */           alpha = alphaCauchy[0];
/*  688 */           cauchy = alphaCauchy[1];
/*      */           
/*  690 */           for (i3 = 0; i3 < n; i3++) {
/*  691 */             this.trialStepPoint.setEntry(i3, this.newPoint.getEntry(i3) - this.trustRegionCenterOffset.getEntry(i3));
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 230:
/*  700 */           printState(230);
/*  701 */           for (i2 = 0; i2 < npt; i2++) {
/*  702 */             double suma = 0.0D;
/*  703 */             double sumb = 0.0D;
/*  704 */             double sum = 0.0D;
/*  705 */             for (int i11 = 0; i11 < n; i11++) {
/*  706 */               suma += this.interpolationPoints.getEntry(i2, i11) * this.trialStepPoint.getEntry(i11);
/*  707 */               sumb += this.interpolationPoints.getEntry(i2, i11) * this.trustRegionCenterOffset.getEntry(i11);
/*  708 */               sum += this.bMatrix.getEntry(i2, i11) * this.trialStepPoint.getEntry(i11);
/*      */             } 
/*  710 */             work3.setEntry(i2, suma * (0.5D * suma + sumb));
/*  711 */             this.lagrangeValuesAtNewPoint.setEntry(i2, sum);
/*  712 */             work2.setEntry(i2, suma);
/*      */           } 
/*  714 */           beta = 0.0D;
/*  715 */           for (m = 0; m < nptm; m++) {
/*  716 */             double sum = 0.0D; int i11;
/*  717 */             for (i11 = 0; i11 < npt; i11++) {
/*  718 */               sum += this.zMatrix.getEntry(i11, m) * work3.getEntry(i11);
/*      */             }
/*  720 */             beta -= sum * sum;
/*  721 */             for (i11 = 0; i11 < npt; i11++) {
/*  722 */               this.lagrangeValuesAtNewPoint.setEntry(i11, this.lagrangeValuesAtNewPoint.getEntry(i11) + sum * this.zMatrix.getEntry(i11, m));
/*      */             }
/*      */           } 
/*  725 */           dsq = 0.0D;
/*  726 */           bsum = 0.0D;
/*  727 */           dx = 0.0D;
/*  728 */           for (j = 0; j < n; j++) {
/*      */             
/*  730 */             double d3 = this.trialStepPoint.getEntry(j);
/*  731 */             dsq += d3 * d3;
/*  732 */             double sum = 0.0D;
/*  733 */             for (int i11 = 0; i11 < npt; i11++) {
/*  734 */               sum += work3.getEntry(i11) * this.bMatrix.getEntry(i11, j);
/*      */             }
/*  736 */             bsum += sum * this.trialStepPoint.getEntry(j);
/*  737 */             int jp = npt + j;
/*  738 */             for (int i12 = 0; i12 < n; i12++) {
/*  739 */               sum += this.bMatrix.getEntry(jp, i12) * this.trialStepPoint.getEntry(i12);
/*      */             }
/*  741 */             this.lagrangeValuesAtNewPoint.setEntry(jp, sum);
/*  742 */             bsum += sum * this.trialStepPoint.getEntry(j);
/*  743 */             dx += this.trialStepPoint.getEntry(j) * this.trustRegionCenterOffset.getEntry(j);
/*      */           } 
/*      */           
/*  746 */           beta = dx * dx + dsq * (xoptsq + dx + dx + 0.5D * dsq) + beta - bsum;
/*      */ 
/*      */ 
/*      */           
/*  750 */           this.lagrangeValuesAtNewPoint.setEntry(this.trustRegionCenterInterpolationPointIndex, this.lagrangeValuesAtNewPoint.getEntry(this.trustRegionCenterInterpolationPointIndex) + 1.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  757 */           if (ntrits == 0) {
/*      */             
/*  759 */             double d = this.lagrangeValuesAtNewPoint.getEntry(knew);
/*  760 */             denom = d * d + alpha * beta;
/*  761 */             if (denom < cauchy && cauchy > 0.0D) {
/*  762 */               for (int i11 = 0; i11 < n; i11++) {
/*  763 */                 this.newPoint.setEntry(i11, this.alternativeNewPoint.getEntry(i11));
/*  764 */                 this.trialStepPoint.setEntry(i11, this.newPoint.getEntry(i11) - this.trustRegionCenterOffset.getEntry(i11));
/*      */               } 
/*  766 */               cauchy = 0.0D;
/*  767 */               state = 230;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */           } else {
/*  776 */             double delsq = delta * delta;
/*  777 */             scaden = 0.0D;
/*  778 */             biglsq = 0.0D;
/*  779 */             knew = 0;
/*  780 */             for (int i11 = 0; i11 < npt; i11++) {
/*  781 */               if (i11 != this.trustRegionCenterInterpolationPointIndex) {
/*      */ 
/*      */                 
/*  784 */                 double hdiag = 0.0D;
/*  785 */                 for (int i12 = 0; i12 < nptm; i12++) {
/*      */                   
/*  787 */                   double d = this.zMatrix.getEntry(i11, i12);
/*  788 */                   hdiag += d * d;
/*      */                 } 
/*      */                 
/*  791 */                 double d3 = this.lagrangeValuesAtNewPoint.getEntry(i11);
/*  792 */                 double den = beta * hdiag + d3 * d3;
/*  793 */                 distsq = 0.0D;
/*  794 */                 for (int i13 = 0; i13 < n; i13++) {
/*      */                   
/*  796 */                   double d = this.interpolationPoints.getEntry(i11, i13) - this.trustRegionCenterOffset.getEntry(i13);
/*  797 */                   distsq += d * d;
/*      */                 } 
/*      */ 
/*      */                 
/*  801 */                 double d4 = distsq / delsq;
/*  802 */                 double temp = FastMath.max(1.0D, d4 * d4);
/*  803 */                 if (temp * den > scaden) {
/*  804 */                   scaden = temp * den;
/*  805 */                   knew = i11;
/*  806 */                   denom = den;
/*      */                 } 
/*      */ 
/*      */                 
/*  810 */                 double d5 = this.lagrangeValuesAtNewPoint.getEntry(i11);
/*  811 */                 biglsq = FastMath.max(biglsq, temp * d5 * d5);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 360:
/*  823 */           printState(360);
/*  824 */           for (i1 = 0; i1 < n; i1++) {
/*      */ 
/*      */             
/*  827 */             double d3 = lowerBound[i1];
/*  828 */             double d4 = this.originShift.getEntry(i1) + this.newPoint.getEntry(i1);
/*  829 */             double d5 = FastMath.max(d3, d4);
/*  830 */             double d6 = upperBound[i1];
/*  831 */             this.currentBest.setEntry(i1, FastMath.min(d5, d6));
/*  832 */             if (this.newPoint.getEntry(i1) == this.lowerDifference.getEntry(i1)) {
/*  833 */               this.currentBest.setEntry(i1, lowerBound[i1]);
/*      */             }
/*  835 */             if (this.newPoint.getEntry(i1) == this.upperDifference.getEntry(i1)) {
/*  836 */               this.currentBest.setEntry(i1, upperBound[i1]);
/*      */             }
/*      */           } 
/*      */           
/*  840 */           f = computeObjectiveValue(this.currentBest.toArray());
/*      */           
/*  842 */           if (!this.isMinimize) {
/*  843 */             f = -f;
/*      */           }
/*  845 */           if (ntrits == -1) {
/*  846 */             fsave = f;
/*  847 */             state = 720;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/*  853 */           fopt = this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex);
/*  854 */           vquad = 0.0D;
/*  855 */           ih = 0;
/*  856 */           for (i5 = 0; i5 < n; i5++) {
/*  857 */             vquad += this.trialStepPoint.getEntry(i5) * this.gradientAtTrustRegionCenter.getEntry(i5);
/*  858 */             for (int i11 = 0; i11 <= i5; i11++) {
/*  859 */               double temp = this.trialStepPoint.getEntry(i11) * this.trialStepPoint.getEntry(i5);
/*  860 */               if (i11 == i5) {
/*  861 */                 temp *= 0.5D;
/*      */               }
/*  863 */               vquad += this.modelSecondDerivativesValues.getEntry(ih) * temp;
/*  864 */               ih++;
/*      */             } 
/*      */           } 
/*  867 */           for (i4 = 0; i4 < npt; i4++) {
/*      */             
/*  869 */             double d3 = work2.getEntry(i4);
/*  870 */             double d4 = d3 * d3;
/*  871 */             vquad += 0.5D * this.modelSecondDerivativesParameters.getEntry(i4) * d4;
/*      */           } 
/*  873 */           diff = f - fopt - vquad;
/*  874 */           diffc = diffb;
/*  875 */           diffb = diffa;
/*  876 */           diffa = FastMath.abs(diff);
/*  877 */           if (dnorm > rho) {
/*  878 */             nfsav = getEvaluations();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  883 */           if (ntrits > 0) {
/*  884 */             if (vquad >= 0.0D) {
/*  885 */               throw new MathIllegalStateException(LocalizedFormats.TRUST_REGION_STEP_FAILED, new Object[] { Double.valueOf(vquad) });
/*      */             }
/*  887 */             ratio = (f - fopt) / vquad;
/*  888 */             double hDelta = 0.5D * delta;
/*  889 */             if (ratio <= 0.1D) {
/*      */               
/*  891 */               delta = FastMath.min(hDelta, dnorm);
/*  892 */             } else if (ratio <= 0.7D) {
/*      */               
/*  894 */               delta = FastMath.max(hDelta, dnorm);
/*      */             } else {
/*      */               
/*  897 */               delta = FastMath.max(hDelta, 2.0D * dnorm);
/*      */             } 
/*  899 */             if (delta <= rho * 1.5D) {
/*  900 */               delta = rho;
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  905 */             if (f < fopt) {
/*  906 */               int ksav = knew;
/*  907 */               double densav = denom;
/*  908 */               double delsq = delta * delta;
/*  909 */               scaden = 0.0D;
/*  910 */               biglsq = 0.0D;
/*  911 */               knew = 0;
/*  912 */               for (int i11 = 0; i11 < npt; i11++) {
/*  913 */                 double hdiag = 0.0D;
/*  914 */                 for (int i12 = 0; i12 < nptm; i12++) {
/*      */                   
/*  916 */                   double d = this.zMatrix.getEntry(i11, i12);
/*  917 */                   hdiag += d * d;
/*      */                 } 
/*      */                 
/*  920 */                 double d6 = this.lagrangeValuesAtNewPoint.getEntry(i11);
/*  921 */                 double den = beta * hdiag + d6 * d6;
/*  922 */                 distsq = 0.0D;
/*  923 */                 for (int i13 = 0; i13 < n; i13++) {
/*      */                   
/*  925 */                   double d = this.interpolationPoints.getEntry(i11, i13) - this.newPoint.getEntry(i13);
/*  926 */                   distsq += d * d;
/*      */                 } 
/*      */ 
/*      */                 
/*  930 */                 double d3 = distsq / delsq;
/*  931 */                 double temp = FastMath.max(1.0D, d3 * d3);
/*  932 */                 if (temp * den > scaden) {
/*  933 */                   scaden = temp * den;
/*  934 */                   knew = i11;
/*  935 */                   denom = den;
/*      */                 } 
/*      */ 
/*      */                 
/*  939 */                 double d4 = this.lagrangeValuesAtNewPoint.getEntry(i11);
/*  940 */                 double d5 = temp * d4 * d4;
/*  941 */                 biglsq = FastMath.max(biglsq, d5);
/*      */               } 
/*  943 */               if (scaden <= 0.5D * biglsq) {
/*  944 */                 knew = ksav;
/*  945 */                 denom = densav;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  953 */           update(beta, denom, knew);
/*      */           
/*  955 */           ih = 0;
/*  956 */           pqold = this.modelSecondDerivativesParameters.getEntry(knew);
/*  957 */           this.modelSecondDerivativesParameters.setEntry(knew, 0.0D);
/*  958 */           for (i10 = 0; i10 < n; i10++) {
/*  959 */             double temp = pqold * this.interpolationPoints.getEntry(knew, i10);
/*  960 */             for (int i11 = 0; i11 <= i10; i11++) {
/*  961 */               this.modelSecondDerivativesValues.setEntry(ih, this.modelSecondDerivativesValues.getEntry(ih) + temp * this.interpolationPoints.getEntry(knew, i11));
/*  962 */               ih++;
/*      */             } 
/*      */           } 
/*  965 */           for (i9 = 0; i9 < nptm; i9++) {
/*  966 */             double temp = diff * this.zMatrix.getEntry(knew, i9);
/*  967 */             for (int i11 = 0; i11 < npt; i11++) {
/*  968 */               this.modelSecondDerivativesParameters.setEntry(i11, this.modelSecondDerivativesParameters.getEntry(i11) + temp * this.zMatrix.getEntry(i11, i9));
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  975 */           this.fAtInterpolationPoints.setEntry(knew, f);
/*  976 */           for (i8 = 0; i8 < n; i8++) {
/*  977 */             this.interpolationPoints.setEntry(knew, i8, this.newPoint.getEntry(i8));
/*  978 */             work1.setEntry(i8, this.bMatrix.getEntry(knew, i8));
/*      */           } 
/*  980 */           for (i7 = 0; i7 < npt; i7++) {
/*  981 */             double suma = 0.0D;
/*  982 */             for (int i11 = 0; i11 < nptm; i11++) {
/*  983 */               suma += this.zMatrix.getEntry(knew, i11) * this.zMatrix.getEntry(i7, i11);
/*      */             }
/*  985 */             double sumb = 0.0D;
/*  986 */             for (int i12 = 0; i12 < n; i12++) {
/*  987 */               sumb += this.interpolationPoints.getEntry(i7, i12) * this.trustRegionCenterOffset.getEntry(i12);
/*      */             }
/*  989 */             double temp = suma * sumb;
/*  990 */             for (int i13 = 0; i13 < n; i13++) {
/*  991 */               work1.setEntry(i13, work1.getEntry(i13) + temp * this.interpolationPoints.getEntry(i7, i13));
/*      */             }
/*      */           } 
/*  994 */           for (i6 = 0; i6 < n; i6++) {
/*  995 */             this.gradientAtTrustRegionCenter.setEntry(i6, this.gradientAtTrustRegionCenter.getEntry(i6) + diff * work1.getEntry(i6));
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1000 */           if (f < fopt) {
/* 1001 */             this.trustRegionCenterInterpolationPointIndex = knew;
/* 1002 */             xoptsq = 0.0D;
/* 1003 */             ih = 0;
/* 1004 */             for (int i12 = 0; i12 < n; i12++) {
/* 1005 */               this.trustRegionCenterOffset.setEntry(i12, this.newPoint.getEntry(i12));
/*      */               
/* 1007 */               double d = this.trustRegionCenterOffset.getEntry(i12);
/* 1008 */               xoptsq += d * d;
/* 1009 */               for (int i13 = 0; i13 <= i12; i13++) {
/* 1010 */                 if (i13 < i12) {
/* 1011 */                   this.gradientAtTrustRegionCenter.setEntry(i12, this.gradientAtTrustRegionCenter.getEntry(i12) + this.modelSecondDerivativesValues.getEntry(ih) * this.trialStepPoint.getEntry(i13));
/*      */                 }
/* 1013 */                 this.gradientAtTrustRegionCenter.setEntry(i13, this.gradientAtTrustRegionCenter.getEntry(i13) + this.modelSecondDerivativesValues.getEntry(ih) * this.trialStepPoint.getEntry(i12));
/* 1014 */                 ih++;
/*      */               } 
/*      */             } 
/* 1017 */             for (int i11 = 0; i11 < npt; i11++) {
/* 1018 */               double temp = 0.0D;
/* 1019 */               for (int i14 = 0; i14 < n; i14++) {
/* 1020 */                 temp += this.interpolationPoints.getEntry(i11, i14) * this.trialStepPoint.getEntry(i14);
/*      */               }
/* 1022 */               temp *= this.modelSecondDerivativesParameters.getEntry(i11);
/* 1023 */               for (int i13 = 0; i13 < n; i13++) {
/* 1024 */                 this.gradientAtTrustRegionCenter.setEntry(i13, this.gradientAtTrustRegionCenter.getEntry(i13) + temp * this.interpolationPoints.getEntry(i11, i13));
/*      */               }
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1033 */           if (ntrits > 0) {
/* 1034 */             for (int i13 = 0; i13 < npt; i13++) {
/* 1035 */               this.lagrangeValuesAtNewPoint.setEntry(i13, this.fAtInterpolationPoints.getEntry(i13) - this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex));
/* 1036 */               work3.setEntry(i13, 0.0D);
/*      */             } 
/* 1038 */             for (int i12 = 0; i12 < nptm; i12++) {
/* 1039 */               double sum = 0.0D; int i15;
/* 1040 */               for (i15 = 0; i15 < npt; i15++) {
/* 1041 */                 sum += this.zMatrix.getEntry(i15, i12) * this.lagrangeValuesAtNewPoint.getEntry(i15);
/*      */               }
/* 1043 */               for (i15 = 0; i15 < npt; i15++) {
/* 1044 */                 work3.setEntry(i15, work3.getEntry(i15) + sum * this.zMatrix.getEntry(i15, i12));
/*      */               }
/*      */             } 
/* 1047 */             for (int i11 = 0; i11 < npt; i11++) {
/* 1048 */               double sum = 0.0D;
/* 1049 */               for (int i15 = 0; i15 < n; i15++) {
/* 1050 */                 sum += this.interpolationPoints.getEntry(i11, i15) * this.trustRegionCenterOffset.getEntry(i15);
/*      */               }
/* 1052 */               work2.setEntry(i11, work3.getEntry(i11));
/* 1053 */               work3.setEntry(i11, sum * work3.getEntry(i11));
/*      */             } 
/* 1055 */             double gqsq = 0.0D;
/* 1056 */             double gisq = 0.0D; int i14;
/* 1057 */             for (i14 = 0; i14 < n; i14++) {
/* 1058 */               double sum = 0.0D;
/* 1059 */               for (int i15 = 0; i15 < npt; i15++) {
/* 1060 */                 sum += this.bMatrix.getEntry(i15, i14) * this.lagrangeValuesAtNewPoint.getEntry(i15) + this.interpolationPoints.getEntry(i15, i14) * work3.getEntry(i15);
/*      */               }
/*      */               
/* 1063 */               if (this.trustRegionCenterOffset.getEntry(i14) == this.lowerDifference.getEntry(i14)) {
/*      */ 
/*      */                 
/* 1066 */                 double d3 = FastMath.min(0.0D, this.gradientAtTrustRegionCenter.getEntry(i14));
/* 1067 */                 gqsq += d3 * d3;
/*      */                 
/* 1069 */                 double d4 = FastMath.min(0.0D, sum);
/* 1070 */                 gisq += d4 * d4;
/* 1071 */               } else if (this.trustRegionCenterOffset.getEntry(i14) == this.upperDifference.getEntry(i14)) {
/*      */ 
/*      */                 
/* 1074 */                 double d3 = FastMath.max(0.0D, this.gradientAtTrustRegionCenter.getEntry(i14));
/* 1075 */                 gqsq += d3 * d3;
/*      */                 
/* 1077 */                 double d4 = FastMath.max(0.0D, sum);
/* 1078 */                 gisq += d4 * d4;
/*      */               } else {
/*      */                 
/* 1081 */                 double d = this.gradientAtTrustRegionCenter.getEntry(i14);
/* 1082 */                 gqsq += d * d;
/* 1083 */                 gisq += sum * sum;
/*      */               } 
/* 1085 */               this.lagrangeValuesAtNewPoint.setEntry(npt + i14, sum);
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1091 */             itest++;
/* 1092 */             if (gqsq < 10.0D * gisq) {
/* 1093 */               itest = 0;
/*      */             }
/* 1095 */             if (itest >= 3) {
/* 1096 */               int max; for (i14 = 0, max = FastMath.max(npt, nh); i14 < max; i14++) {
/* 1097 */                 if (i14 < n) {
/* 1098 */                   this.gradientAtTrustRegionCenter.setEntry(i14, this.lagrangeValuesAtNewPoint.getEntry(npt + i14));
/*      */                 }
/* 1100 */                 if (i14 < npt) {
/* 1101 */                   this.modelSecondDerivativesParameters.setEntry(i14, work2.getEntry(i14));
/*      */                 }
/* 1103 */                 if (i14 < nh) {
/* 1104 */                   this.modelSecondDerivativesValues.setEntry(i14, 0.0D);
/*      */                 }
/* 1106 */                 itest = 0;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1115 */           if (ntrits == 0) {
/* 1116 */             state = 60; continue;
/*      */           } 
/* 1118 */           if (f <= fopt + 0.1D * vquad) {
/* 1119 */             state = 60;
/*      */ 
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/* 1127 */           d1 = 2.0D * delta;
/*      */           
/* 1129 */           d2 = 10.0D * rho;
/* 1130 */           distsq = FastMath.max(d1 * d1, d2 * d2);
/*      */         
/*      */         case 650:
/* 1133 */           printState(650);
/* 1134 */           knew = -1;
/* 1135 */           for (k = 0; k < npt; k++) {
/* 1136 */             double sum = 0.0D;
/* 1137 */             for (int i11 = 0; i11 < n; i11++) {
/*      */               
/* 1139 */               double d = this.interpolationPoints.getEntry(k, i11) - this.trustRegionCenterOffset.getEntry(i11);
/* 1140 */               sum += d * d;
/*      */             } 
/* 1142 */             if (sum > distsq) {
/* 1143 */               knew = k;
/* 1144 */               distsq = sum;
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1154 */           if (knew >= 0) {
/* 1155 */             double dist = FastMath.sqrt(distsq);
/* 1156 */             if (ntrits == -1) {
/*      */               
/* 1158 */               delta = FastMath.min(0.1D * delta, 0.5D * dist);
/* 1159 */               if (delta <= rho * 1.5D) {
/* 1160 */                 delta = rho;
/*      */               }
/*      */             } 
/* 1163 */             ntrits = 0;
/*      */ 
/*      */             
/* 1166 */             double d3 = FastMath.min(0.1D * dist, delta);
/* 1167 */             adelt = FastMath.max(d3, rho);
/* 1168 */             dsq = adelt * adelt;
/* 1169 */             state = 90; continue;
/*      */           } 
/* 1171 */           if (ntrits == -1) {
/* 1172 */             state = 680; continue;
/*      */           } 
/* 1174 */           if (ratio > 0.0D) {
/* 1175 */             state = 60; continue;
/*      */           } 
/* 1177 */           if (FastMath.max(delta, dnorm) > rho) {
/* 1178 */             state = 60;
/*      */             continue;
/*      */           } 
/*      */ 
/*      */ 
/*      */         
/*      */         case 680:
/* 1185 */           printState(680);
/* 1186 */           if (rho > this.stoppingTrustRegionRadius) {
/* 1187 */             delta = 0.5D * rho;
/* 1188 */             ratio = rho / this.stoppingTrustRegionRadius;
/* 1189 */             if (ratio <= 16.0D) {
/* 1190 */               rho = this.stoppingTrustRegionRadius;
/* 1191 */             } else if (ratio <= 250.0D) {
/* 1192 */               rho = FastMath.sqrt(ratio) * this.stoppingTrustRegionRadius;
/*      */             } else {
/* 1194 */               rho *= 0.1D;
/*      */             } 
/* 1196 */             delta = FastMath.max(delta, rho);
/* 1197 */             ntrits = 0;
/* 1198 */             nfsav = getEvaluations();
/* 1199 */             state = 60;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 1205 */           if (ntrits == -1) {
/* 1206 */             state = 360;
/*      */             continue;
/*      */           } 
/*      */         case 720:
/* 1210 */           printState(720);
/* 1211 */           if (this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex) <= fsave) {
/* 1212 */             for (int i11 = 0; i11 < n; i11++) {
/*      */ 
/*      */               
/* 1215 */               double d3 = lowerBound[i11];
/* 1216 */               double d4 = this.originShift.getEntry(i11) + this.trustRegionCenterOffset.getEntry(i11);
/* 1217 */               double d5 = FastMath.max(d3, d4);
/* 1218 */               double d6 = upperBound[i11];
/* 1219 */               this.currentBest.setEntry(i11, FastMath.min(d5, d6));
/* 1220 */               if (this.trustRegionCenterOffset.getEntry(i11) == this.lowerDifference.getEntry(i11)) {
/* 1221 */                 this.currentBest.setEntry(i11, lowerBound[i11]);
/*      */               }
/* 1223 */               if (this.trustRegionCenterOffset.getEntry(i11) == this.upperDifference.getEntry(i11)) {
/* 1224 */                 this.currentBest.setEntry(i11, upperBound[i11]);
/*      */               }
/*      */             } 
/* 1227 */             f = this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex);
/*      */           } 
/* 1229 */           return f;
/*      */       }  break;
/*      */     } 
/* 1232 */     throw new MathIllegalStateException(LocalizedFormats.SIMPLE_MESSAGE, new Object[] { "bobyqb" });
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double[] altmov(int knew, double adelt) {
/* 1275 */     printMethod();
/*      */     
/* 1277 */     int n = this.currentBest.getDimension();
/* 1278 */     int npt = this.numberOfInterpolationPoints;
/*      */     
/* 1280 */     ArrayRealVector glag = new ArrayRealVector(n);
/* 1281 */     ArrayRealVector hcol = new ArrayRealVector(npt);
/*      */     
/* 1283 */     ArrayRealVector work1 = new ArrayRealVector(n);
/* 1284 */     ArrayRealVector work2 = new ArrayRealVector(n);
/*      */     
/* 1286 */     for (int k = 0; k < npt; k++) {
/* 1287 */       hcol.setEntry(k, 0.0D);
/*      */     }
/* 1289 */     for (int j = 0, max = npt - n - 1; j < max; j++) {
/* 1290 */       double tmp = this.zMatrix.getEntry(knew, j);
/* 1291 */       for (int i3 = 0; i3 < npt; i3++) {
/* 1292 */         hcol.setEntry(i3, hcol.getEntry(i3) + tmp * this.zMatrix.getEntry(i3, j));
/*      */       }
/*      */     } 
/* 1295 */     double alpha = hcol.getEntry(knew);
/* 1296 */     double ha = 0.5D * alpha;
/*      */ 
/*      */ 
/*      */     
/* 1300 */     for (int i = 0; i < n; i++) {
/* 1301 */       glag.setEntry(i, this.bMatrix.getEntry(knew, i));
/*      */     }
/* 1303 */     for (int m = 0; m < npt; m++) {
/* 1304 */       double tmp = 0.0D;
/* 1305 */       for (int i4 = 0; i4 < n; i4++) {
/* 1306 */         tmp += this.interpolationPoints.getEntry(m, i4) * this.trustRegionCenterOffset.getEntry(i4);
/*      */       }
/* 1308 */       tmp *= hcol.getEntry(m);
/* 1309 */       for (int i3 = 0; i3 < n; i3++) {
/* 1310 */         glag.setEntry(i3, glag.getEntry(i3) + tmp * this.interpolationPoints.getEntry(m, i3));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1320 */     double presav = 0.0D;
/* 1321 */     double step = Double.NaN;
/* 1322 */     int ksav = 0;
/* 1323 */     int ibdsav = 0;
/* 1324 */     double stpsav = 0.0D;
/* 1325 */     for (int i2 = 0; i2 < npt; i2++) {
/* 1326 */       if (i2 != this.trustRegionCenterInterpolationPointIndex) {
/*      */ 
/*      */         
/* 1329 */         double dderiv = 0.0D;
/* 1330 */         double distsq = 0.0D;
/* 1331 */         for (int i3 = 0; i3 < n; i3++) {
/* 1332 */           double d = this.interpolationPoints.getEntry(i2, i3) - this.trustRegionCenterOffset.getEntry(i3);
/* 1333 */           dderiv += glag.getEntry(i3) * d;
/* 1334 */           distsq += d * d;
/*      */         } 
/* 1336 */         double subd = adelt / FastMath.sqrt(distsq);
/* 1337 */         double slbd = -subd;
/* 1338 */         int ilbd = 0;
/* 1339 */         int iubd = 0;
/* 1340 */         double sumin = FastMath.min(1.0D, subd);
/*      */ 
/*      */ 
/*      */         
/* 1344 */         for (int i4 = 0; i4 < n; i4++) {
/* 1345 */           double d = this.interpolationPoints.getEntry(i2, i4) - this.trustRegionCenterOffset.getEntry(i4);
/* 1346 */           if (d > 0.0D) {
/* 1347 */             if (slbd * d < this.lowerDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) {
/* 1348 */               slbd = (this.lowerDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) / d;
/* 1349 */               ilbd = -i4 - 1;
/*      */             } 
/* 1351 */             if (subd * d > this.upperDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) {
/*      */               
/* 1353 */               subd = FastMath.max(sumin, (this.upperDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) / d);
/*      */               
/* 1355 */               iubd = i4 + 1;
/*      */             } 
/* 1357 */           } else if (d < 0.0D) {
/* 1358 */             if (slbd * d > this.upperDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) {
/* 1359 */               slbd = (this.upperDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) / d;
/* 1360 */               ilbd = i4 + 1;
/*      */             } 
/* 1362 */             if (subd * d < this.lowerDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) {
/*      */               
/* 1364 */               subd = FastMath.max(sumin, (this.lowerDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) / d);
/*      */               
/* 1366 */               iubd = -i4 - 1;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1374 */         step = slbd;
/* 1375 */         int isbd = ilbd;
/* 1376 */         double vlag = Double.NaN;
/* 1377 */         if (i2 == knew) {
/* 1378 */           double diff = dderiv - 1.0D;
/* 1379 */           vlag = slbd * (dderiv - slbd * diff);
/* 1380 */           double d1 = subd * (dderiv - subd * diff);
/* 1381 */           if (FastMath.abs(d1) > FastMath.abs(vlag)) {
/* 1382 */             step = subd;
/* 1383 */             vlag = d1;
/* 1384 */             isbd = iubd;
/*      */           } 
/* 1386 */           double d2 = 0.5D * dderiv;
/* 1387 */           double d3 = d2 - diff * slbd;
/* 1388 */           double d4 = d2 - diff * subd;
/* 1389 */           if (d3 * d4 < 0.0D) {
/* 1390 */             double d5 = d2 * d2 / diff;
/* 1391 */             if (FastMath.abs(d5) > FastMath.abs(vlag)) {
/* 1392 */               step = d2 / diff;
/* 1393 */               vlag = d5;
/* 1394 */               isbd = 0;
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1401 */           vlag = slbd * (1.0D - slbd);
/* 1402 */           double d = subd * (1.0D - subd);
/* 1403 */           if (FastMath.abs(d) > FastMath.abs(vlag)) {
/* 1404 */             step = subd;
/* 1405 */             vlag = d;
/* 1406 */             isbd = iubd;
/*      */           } 
/* 1408 */           if (subd > 0.5D && FastMath.abs(vlag) < 0.25D) {
/* 1409 */             step = 0.5D;
/* 1410 */             vlag = 0.25D;
/* 1411 */             isbd = 0;
/*      */           } 
/* 1413 */           vlag *= dderiv;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1418 */         double tmp = step * (1.0D - step) * distsq;
/* 1419 */         double predsq = vlag * vlag * (vlag * vlag + ha * tmp * tmp);
/* 1420 */         if (predsq > presav) {
/* 1421 */           presav = predsq;
/* 1422 */           ksav = i2;
/* 1423 */           stpsav = step;
/* 1424 */           ibdsav = isbd;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1430 */     for (int i1 = 0; i1 < n; i1++) {
/* 1431 */       double tmp = this.trustRegionCenterOffset.getEntry(i1) + stpsav * (this.interpolationPoints.getEntry(ksav, i1) - this.trustRegionCenterOffset.getEntry(i1));
/* 1432 */       this.newPoint.setEntry(i1, FastMath.max(this.lowerDifference.getEntry(i1), FastMath.min(this.upperDifference.getEntry(i1), tmp)));
/*      */     } 
/*      */     
/* 1435 */     if (ibdsav < 0) {
/* 1436 */       this.newPoint.setEntry(-ibdsav - 1, this.lowerDifference.getEntry(-ibdsav - 1));
/*      */     }
/* 1438 */     if (ibdsav > 0) {
/* 1439 */       this.newPoint.setEntry(ibdsav - 1, this.upperDifference.getEntry(ibdsav - 1));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1446 */     double bigstp = adelt + adelt;
/* 1447 */     int iflag = 0;
/* 1448 */     double cauchy = Double.NaN;
/* 1449 */     double csave = 0.0D;
/*      */     while (true) {
/* 1451 */       double wfixsq = 0.0D;
/* 1452 */       double ggfree = 0.0D;
/* 1453 */       for (int i3 = 0; i3 < n; i3++) {
/* 1454 */         double glagValue = glag.getEntry(i3);
/* 1455 */         work1.setEntry(i3, 0.0D);
/* 1456 */         if (FastMath.min(this.trustRegionCenterOffset.getEntry(i3) - this.lowerDifference.getEntry(i3), glagValue) > 0.0D || FastMath.max(this.trustRegionCenterOffset.getEntry(i3) - this.upperDifference.getEntry(i3), glagValue) < 0.0D) {
/*      */           
/* 1458 */           work1.setEntry(i3, bigstp);
/*      */           
/* 1460 */           ggfree += glagValue * glagValue;
/*      */         } 
/*      */       } 
/* 1463 */       if (ggfree == 0.0D) {
/* 1464 */         return new double[] { alpha, 0.0D };
/*      */       }
/*      */ 
/*      */       
/* 1468 */       double tmp1 = adelt * adelt - wfixsq;
/* 1469 */       if (tmp1 > 0.0D) {
/* 1470 */         step = FastMath.sqrt(tmp1 / ggfree);
/* 1471 */         ggfree = 0.0D;
/* 1472 */         for (int i6 = 0; i6 < n; i6++) {
/* 1473 */           if (work1.getEntry(i6) == bigstp) {
/* 1474 */             double tmp2 = this.trustRegionCenterOffset.getEntry(i6) - step * glag.getEntry(i6);
/* 1475 */             if (tmp2 <= this.lowerDifference.getEntry(i6)) {
/* 1476 */               work1.setEntry(i6, this.lowerDifference.getEntry(i6) - this.trustRegionCenterOffset.getEntry(i6));
/*      */               
/* 1478 */               double d1 = work1.getEntry(i6);
/* 1479 */               wfixsq += d1 * d1;
/* 1480 */             } else if (tmp2 >= this.upperDifference.getEntry(i6)) {
/* 1481 */               work1.setEntry(i6, this.upperDifference.getEntry(i6) - this.trustRegionCenterOffset.getEntry(i6));
/*      */               
/* 1483 */               double d1 = work1.getEntry(i6);
/* 1484 */               wfixsq += d1 * d1;
/*      */             } else {
/*      */               
/* 1487 */               double d1 = glag.getEntry(i6);
/* 1488 */               ggfree += d1 * d1;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1497 */       double gw = 0.0D;
/* 1498 */       for (int i4 = 0; i4 < n; i4++) {
/* 1499 */         double glagValue = glag.getEntry(i4);
/* 1500 */         if (work1.getEntry(i4) == bigstp) {
/* 1501 */           work1.setEntry(i4, -step * glagValue);
/* 1502 */           double min = FastMath.min(this.upperDifference.getEntry(i4), this.trustRegionCenterOffset.getEntry(i4) + work1.getEntry(i4));
/*      */           
/* 1504 */           this.alternativeNewPoint.setEntry(i4, FastMath.max(this.lowerDifference.getEntry(i4), min));
/* 1505 */         } else if (work1.getEntry(i4) == 0.0D) {
/* 1506 */           this.alternativeNewPoint.setEntry(i4, this.trustRegionCenterOffset.getEntry(i4));
/* 1507 */         } else if (glagValue > 0.0D) {
/* 1508 */           this.alternativeNewPoint.setEntry(i4, this.lowerDifference.getEntry(i4));
/*      */         } else {
/* 1510 */           this.alternativeNewPoint.setEntry(i4, this.upperDifference.getEntry(i4));
/*      */         } 
/* 1512 */         gw += glagValue * work1.getEntry(i4);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1520 */       double curv = 0.0D;
/* 1521 */       for (int i5 = 0; i5 < npt; i5++) {
/* 1522 */         double tmp = 0.0D;
/* 1523 */         for (int i6 = 0; i6 < n; i6++) {
/* 1524 */           tmp += this.interpolationPoints.getEntry(i5, i6) * work1.getEntry(i6);
/*      */         }
/* 1526 */         curv += hcol.getEntry(i5) * tmp * tmp;
/*      */       } 
/* 1528 */       if (iflag == 1) {
/* 1529 */         curv = -curv;
/*      */       }
/* 1531 */       if (curv > -gw && curv < -gw * (1.0D + FastMath.sqrt(2.0D))) {
/*      */         
/* 1533 */         double scale = -gw / curv;
/* 1534 */         for (int i6 = 0; i6 < n; i6++) {
/* 1535 */           double tmp = this.trustRegionCenterOffset.getEntry(i6) + scale * work1.getEntry(i6);
/* 1536 */           this.alternativeNewPoint.setEntry(i6, FastMath.max(this.lowerDifference.getEntry(i6), FastMath.min(this.upperDifference.getEntry(i6), tmp)));
/*      */         } 
/*      */ 
/*      */         
/* 1540 */         double d1 = 0.5D * gw * scale;
/* 1541 */         cauchy = d1 * d1;
/*      */       } else {
/*      */         
/* 1544 */         double d1 = gw + 0.5D * curv;
/* 1545 */         cauchy = d1 * d1;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1552 */       if (iflag == 0) {
/* 1553 */         for (int i6 = 0; i6 < n; i6++) {
/* 1554 */           glag.setEntry(i6, -glag.getEntry(i6));
/* 1555 */           work2.setEntry(i6, this.alternativeNewPoint.getEntry(i6));
/*      */         } 
/* 1557 */         csave = cauchy;
/* 1558 */         iflag = 1;
/*      */         continue;
/*      */       } 
/*      */       break;
/*      */     } 
/* 1563 */     if (csave > cauchy) {
/* 1564 */       for (int i3 = 0; i3 < n; i3++) {
/* 1565 */         this.alternativeNewPoint.setEntry(i3, work2.getEntry(i3));
/*      */       }
/* 1567 */       cauchy = csave;
/*      */     } 
/*      */     
/* 1570 */     return new double[] { alpha, cauchy };
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
/*      */   private void prelim(double[] lowerBound, double[] upperBound) {
/* 1597 */     printMethod();
/*      */     
/* 1599 */     int n = this.currentBest.getDimension();
/* 1600 */     int npt = this.numberOfInterpolationPoints;
/* 1601 */     int ndim = this.bMatrix.getRowDimension();
/*      */     
/* 1603 */     double rhosq = this.initialTrustRegionRadius * this.initialTrustRegionRadius;
/* 1604 */     double recip = 1.0D / rhosq;
/* 1605 */     int np = n + 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1610 */     for (int j = 0; j < n; j++) {
/* 1611 */       this.originShift.setEntry(j, this.currentBest.getEntry(j));
/* 1612 */       for (int i1 = 0; i1 < npt; i1++) {
/* 1613 */         this.interpolationPoints.setEntry(i1, j, 0.0D);
/*      */       }
/* 1615 */       for (int m = 0; m < ndim; m++) {
/* 1616 */         this.bMatrix.setEntry(m, j, 0.0D);
/*      */       }
/*      */     } 
/* 1619 */     for (int i = 0, max = n * np / 2; i < max; i++) {
/* 1620 */       this.modelSecondDerivativesValues.setEntry(i, 0.0D);
/*      */     }
/* 1622 */     for (int k = 0; k < npt; k++) {
/* 1623 */       this.modelSecondDerivativesParameters.setEntry(k, 0.0D);
/* 1624 */       for (int m = 0, i1 = npt - np; m < i1; m++) {
/* 1625 */         this.zMatrix.setEntry(k, m, 0.0D);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1633 */     int ipt = 0;
/* 1634 */     int jpt = 0;
/* 1635 */     double fbeg = Double.NaN;
/*      */     do {
/* 1637 */       int nfm = getEvaluations();
/* 1638 */       int nfx = nfm - n;
/* 1639 */       int nfmm = nfm - 1;
/* 1640 */       int nfxm = nfx - 1;
/* 1641 */       double stepa = 0.0D;
/* 1642 */       double stepb = 0.0D;
/* 1643 */       if (nfm <= 2 * n) {
/* 1644 */         if (nfm >= 1 && nfm <= n) {
/*      */           
/* 1646 */           stepa = this.initialTrustRegionRadius;
/* 1647 */           if (this.upperDifference.getEntry(nfmm) == 0.0D) {
/* 1648 */             stepa = -stepa;
/*      */           }
/*      */           
/* 1651 */           this.interpolationPoints.setEntry(nfm, nfmm, stepa);
/* 1652 */         } else if (nfm > n) {
/* 1653 */           stepa = this.interpolationPoints.getEntry(nfx, nfxm);
/* 1654 */           stepb = -this.initialTrustRegionRadius;
/* 1655 */           if (this.lowerDifference.getEntry(nfxm) == 0.0D) {
/* 1656 */             stepb = FastMath.min(2.0D * this.initialTrustRegionRadius, this.upperDifference.getEntry(nfxm));
/*      */           }
/*      */           
/* 1659 */           if (this.upperDifference.getEntry(nfxm) == 0.0D) {
/* 1660 */             stepb = FastMath.max(-2.0D * this.initialTrustRegionRadius, this.lowerDifference.getEntry(nfxm));
/*      */           }
/*      */           
/* 1663 */           this.interpolationPoints.setEntry(nfm, nfxm, stepb);
/*      */         } 
/*      */       } else {
/* 1666 */         int tmp1 = (nfm - np) / n;
/* 1667 */         jpt = nfm - tmp1 * n - n;
/* 1668 */         ipt = jpt + tmp1;
/* 1669 */         if (ipt > n) {
/* 1670 */           int tmp2 = jpt;
/* 1671 */           jpt = ipt - n;
/* 1672 */           ipt = tmp2;
/*      */         } 
/*      */         
/* 1675 */         int iptMinus1 = ipt - 1;
/* 1676 */         int jptMinus1 = jpt - 1;
/* 1677 */         this.interpolationPoints.setEntry(nfm, iptMinus1, this.interpolationPoints.getEntry(ipt, iptMinus1));
/* 1678 */         this.interpolationPoints.setEntry(nfm, jptMinus1, this.interpolationPoints.getEntry(jpt, jptMinus1));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1684 */       for (int m = 0; m < n; m++) {
/* 1685 */         this.currentBest.setEntry(m, FastMath.min(FastMath.max(lowerBound[m], this.originShift.getEntry(m) + this.interpolationPoints.getEntry(nfm, m)), upperBound[m]));
/*      */ 
/*      */         
/* 1688 */         if (this.interpolationPoints.getEntry(nfm, m) == this.lowerDifference.getEntry(m)) {
/* 1689 */           this.currentBest.setEntry(m, lowerBound[m]);
/*      */         }
/* 1691 */         if (this.interpolationPoints.getEntry(nfm, m) == this.upperDifference.getEntry(m)) {
/* 1692 */           this.currentBest.setEntry(m, upperBound[m]);
/*      */         }
/*      */       } 
/*      */       
/* 1696 */       double objectiveValue = computeObjectiveValue(this.currentBest.toArray());
/* 1697 */       double f = this.isMinimize ? objectiveValue : -objectiveValue;
/* 1698 */       int numEval = getEvaluations();
/* 1699 */       this.fAtInterpolationPoints.setEntry(nfm, f);
/*      */       
/* 1701 */       if (numEval == 1) {
/* 1702 */         fbeg = f;
/* 1703 */         this.trustRegionCenterInterpolationPointIndex = 0;
/* 1704 */       } else if (f < this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex)) {
/* 1705 */         this.trustRegionCenterInterpolationPointIndex = nfm;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1714 */       if (numEval <= 2 * n + 1) {
/* 1715 */         if (numEval >= 2 && numEval <= n + 1) {
/*      */           
/* 1717 */           this.gradientAtTrustRegionCenter.setEntry(nfmm, (f - fbeg) / stepa);
/* 1718 */           if (npt < numEval + n) {
/* 1719 */             double oneOverStepA = 1.0D / stepa;
/* 1720 */             this.bMatrix.setEntry(0, nfmm, -oneOverStepA);
/* 1721 */             this.bMatrix.setEntry(nfm, nfmm, oneOverStepA);
/* 1722 */             this.bMatrix.setEntry(npt + nfmm, nfmm, -0.5D * rhosq);
/*      */           }
/*      */         
/* 1725 */         } else if (numEval >= n + 2) {
/* 1726 */           int ih = nfx * (nfx + 1) / 2 - 1;
/* 1727 */           double tmp = (f - fbeg) / stepb;
/* 1728 */           double diff = stepb - stepa;
/* 1729 */           this.modelSecondDerivativesValues.setEntry(ih, 2.0D * (tmp - this.gradientAtTrustRegionCenter.getEntry(nfxm)) / diff);
/* 1730 */           this.gradientAtTrustRegionCenter.setEntry(nfxm, (this.gradientAtTrustRegionCenter.getEntry(nfxm) * stepb - tmp * stepa) / diff);
/* 1731 */           if (stepa * stepb < 0.0D && f < this.fAtInterpolationPoints.getEntry(nfm - n)) {
/* 1732 */             this.fAtInterpolationPoints.setEntry(nfm, this.fAtInterpolationPoints.getEntry(nfm - n));
/* 1733 */             this.fAtInterpolationPoints.setEntry(nfm - n, f);
/* 1734 */             if (this.trustRegionCenterInterpolationPointIndex == nfm) {
/* 1735 */               this.trustRegionCenterInterpolationPointIndex = nfm - n;
/*      */             }
/* 1737 */             this.interpolationPoints.setEntry(nfm - n, nfxm, stepb);
/* 1738 */             this.interpolationPoints.setEntry(nfm, nfxm, stepa);
/*      */           } 
/* 1740 */           this.bMatrix.setEntry(0, nfxm, -(stepa + stepb) / stepa * stepb);
/* 1741 */           this.bMatrix.setEntry(nfm, nfxm, -0.5D / this.interpolationPoints.getEntry(nfm - n, nfxm));
/* 1742 */           this.bMatrix.setEntry(nfm - n, nfxm, -this.bMatrix.getEntry(0, nfxm) - this.bMatrix.getEntry(nfm, nfxm));
/*      */           
/* 1744 */           this.zMatrix.setEntry(0, nfxm, FastMath.sqrt(2.0D) / stepa * stepb);
/* 1745 */           this.zMatrix.setEntry(nfm, nfxm, FastMath.sqrt(0.5D) / rhosq);
/*      */           
/* 1747 */           this.zMatrix.setEntry(nfm - n, nfxm, -this.zMatrix.getEntry(0, nfxm) - this.zMatrix.getEntry(nfm, nfxm));
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1755 */         this.zMatrix.setEntry(0, nfxm, recip);
/* 1756 */         this.zMatrix.setEntry(nfm, nfxm, recip);
/* 1757 */         this.zMatrix.setEntry(ipt, nfxm, -recip);
/* 1758 */         this.zMatrix.setEntry(jpt, nfxm, -recip);
/*      */         
/* 1760 */         int ih = ipt * (ipt - 1) / 2 + jpt - 1;
/* 1761 */         double tmp = this.interpolationPoints.getEntry(nfm, ipt - 1) * this.interpolationPoints.getEntry(nfm, jpt - 1);
/* 1762 */         this.modelSecondDerivativesValues.setEntry(ih, (fbeg - this.fAtInterpolationPoints.getEntry(ipt) - this.fAtInterpolationPoints.getEntry(jpt) + f) / tmp);
/*      */       }
/*      */     
/* 1765 */     } while (getEvaluations() < npt);
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
/*      */   private double[] trsbox(double delta, ArrayRealVector gnew, ArrayRealVector xbdi, ArrayRealVector s, ArrayRealVector hs, ArrayRealVector hred) {
/* 1823 */     printMethod();
/*      */     
/* 1825 */     int n = this.currentBest.getDimension();
/* 1826 */     int npt = this.numberOfInterpolationPoints;
/*      */     
/* 1828 */     double dsq = Double.NaN;
/* 1829 */     double crvmin = Double.NaN;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1834 */     double beta = 0.0D;
/* 1835 */     int iact = -1;
/* 1836 */     int nact = 0;
/* 1837 */     double angt = 0.0D;
/*      */     
/* 1839 */     double temp = 0.0D, xsav = 0.0D, xsum = 0.0D, angbd = 0.0D, dredg = 0.0D, sredg = 0.0D;
/*      */     
/* 1841 */     double resid = 0.0D, delsq = 0.0D, ggsav = 0.0D, tempa = 0.0D, tempb = 0.0D;
/* 1842 */     double redmax = 0.0D, dredsq = 0.0D, redsav = 0.0D, gredsq = 0.0D, rednew = 0.0D;
/* 1843 */     int itcsav = 0;
/* 1844 */     double rdprev = 0.0D, rdnext = 0.0D, stplen = 0.0D, stepsq = 0.0D;
/* 1845 */     int itermax = 0;
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
/* 1858 */     int iterc = 0;
/* 1859 */     nact = 0;
/* 1860 */     for (int i = 0; i < n; i++) {
/* 1861 */       xbdi.setEntry(i, 0.0D);
/* 1862 */       if (this.trustRegionCenterOffset.getEntry(i) <= this.lowerDifference.getEntry(i)) {
/* 1863 */         if (this.gradientAtTrustRegionCenter.getEntry(i) >= 0.0D) {
/* 1864 */           xbdi.setEntry(i, -1.0D);
/*      */         }
/* 1866 */       } else if (this.trustRegionCenterOffset.getEntry(i) >= this.upperDifference.getEntry(i) && this.gradientAtTrustRegionCenter.getEntry(i) <= 0.0D) {
/*      */         
/* 1868 */         xbdi.setEntry(i, 1.0D);
/*      */       } 
/* 1870 */       if (xbdi.getEntry(i) != 0.0D) {
/* 1871 */         nact++;
/*      */       }
/* 1873 */       this.trialStepPoint.setEntry(i, 0.0D);
/* 1874 */       gnew.setEntry(i, this.gradientAtTrustRegionCenter.getEntry(i));
/*      */     } 
/* 1876 */     delsq = delta * delta;
/* 1877 */     double qred = 0.0D;
/* 1878 */     crvmin = -1.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1886 */     int state = 20; while (true) {
/*      */       double ds; int iu; double dhd; double dhs; double cth; double shs; double sth; double sdec; double blen; int isav; int m; int ih; int j; RealVector tmp; int k; int i1;
/* 1888 */       switch (state) {
/*      */         case 20:
/* 1890 */           printState(20);
/* 1891 */           beta = 0.0D;
/*      */         
/*      */         case 30:
/* 1894 */           printState(30);
/* 1895 */           stepsq = 0.0D;
/* 1896 */           for (m = 0; m < n; m++) {
/* 1897 */             if (xbdi.getEntry(m) != 0.0D) {
/* 1898 */               s.setEntry(m, 0.0D);
/* 1899 */             } else if (beta == 0.0D) {
/* 1900 */               s.setEntry(m, -gnew.getEntry(m));
/*      */             } else {
/* 1902 */               s.setEntry(m, beta * s.getEntry(m) - gnew.getEntry(m));
/*      */             } 
/*      */             
/* 1905 */             double d1 = s.getEntry(m);
/* 1906 */             stepsq += d1 * d1;
/*      */           } 
/* 1908 */           if (stepsq == 0.0D) {
/* 1909 */             state = 190; continue;
/*      */           } 
/* 1911 */           if (beta == 0.0D) {
/* 1912 */             gredsq = stepsq;
/* 1913 */             itermax = iterc + n - nact;
/*      */           } 
/* 1915 */           if (gredsq * delsq <= qred * 1.0E-4D * qred) {
/* 1916 */             state = 190;
/*      */ 
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/* 1924 */           state = 210;
/*      */           continue;
/*      */         case 50:
/* 1927 */           printState(50);
/* 1928 */           resid = delsq;
/* 1929 */           ds = 0.0D;
/* 1930 */           shs = 0.0D;
/* 1931 */           for (m = 0; m < n; m++) {
/* 1932 */             if (xbdi.getEntry(m) == 0.0D) {
/*      */               
/* 1934 */               double d1 = this.trialStepPoint.getEntry(m);
/* 1935 */               resid -= d1 * d1;
/* 1936 */               ds += s.getEntry(m) * this.trialStepPoint.getEntry(m);
/* 1937 */               shs += s.getEntry(m) * hs.getEntry(m);
/*      */             } 
/*      */           } 
/* 1940 */           if (resid <= 0.0D) {
/* 1941 */             state = 90; continue;
/*      */           } 
/* 1943 */           temp = FastMath.sqrt(stepsq * resid + ds * ds);
/* 1944 */           if (ds < 0.0D) {
/* 1945 */             blen = (temp - ds) / stepsq;
/*      */           } else {
/* 1947 */             blen = resid / (temp + ds);
/*      */           } 
/* 1949 */           stplen = blen;
/* 1950 */           if (shs > 0.0D)
/*      */           {
/* 1952 */             stplen = FastMath.min(blen, gredsq / shs);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1958 */           iact = -1;
/* 1959 */           for (m = 0; m < n; m++) {
/* 1960 */             if (s.getEntry(m) != 0.0D) {
/* 1961 */               xsum = this.trustRegionCenterOffset.getEntry(m) + this.trialStepPoint.getEntry(m);
/* 1962 */               if (s.getEntry(m) > 0.0D) {
/* 1963 */                 temp = (this.upperDifference.getEntry(m) - xsum) / s.getEntry(m);
/*      */               } else {
/* 1965 */                 temp = (this.lowerDifference.getEntry(m) - xsum) / s.getEntry(m);
/*      */               } 
/* 1967 */               if (temp < stplen) {
/* 1968 */                 stplen = temp;
/* 1969 */                 iact = m;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1976 */           sdec = 0.0D;
/* 1977 */           if (stplen > 0.0D) {
/* 1978 */             iterc++;
/* 1979 */             temp = shs / stepsq;
/* 1980 */             if (iact == -1 && temp > 0.0D) {
/* 1981 */               crvmin = FastMath.min(crvmin, temp);
/* 1982 */               if (crvmin == -1.0D) {
/* 1983 */                 crvmin = temp;
/*      */               }
/*      */             } 
/* 1986 */             ggsav = gredsq;
/* 1987 */             gredsq = 0.0D;
/* 1988 */             for (m = 0; m < n; m++) {
/* 1989 */               gnew.setEntry(m, gnew.getEntry(m) + stplen * hs.getEntry(m));
/* 1990 */               if (xbdi.getEntry(m) == 0.0D) {
/*      */                 
/* 1992 */                 double d = gnew.getEntry(m);
/* 1993 */                 gredsq += d * d;
/*      */               } 
/* 1995 */               this.trialStepPoint.setEntry(m, this.trialStepPoint.getEntry(m) + stplen * s.getEntry(m));
/*      */             } 
/*      */             
/* 1998 */             double d1 = stplen * (ggsav - 0.5D * stplen * shs);
/* 1999 */             sdec = FastMath.max(d1, 0.0D);
/* 2000 */             qred += sdec;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2005 */           if (iact >= 0) {
/* 2006 */             nact++;
/* 2007 */             xbdi.setEntry(iact, 1.0D);
/* 2008 */             if (s.getEntry(iact) < 0.0D) {
/* 2009 */               xbdi.setEntry(iact, -1.0D);
/*      */             }
/*      */             
/* 2012 */             double d1 = this.trialStepPoint.getEntry(iact);
/* 2013 */             delsq -= d1 * d1;
/* 2014 */             if (delsq <= 0.0D) {
/* 2015 */               state = 190; continue;
/*      */             } 
/* 2017 */             state = 20;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 2023 */           if (stplen < blen) {
/* 2024 */             if (iterc == itermax) {
/* 2025 */               state = 190; continue;
/*      */             } 
/* 2027 */             if (sdec <= qred * 0.01D) {
/* 2028 */               state = 190; continue;
/*      */             } 
/* 2030 */             beta = gredsq / ggsav;
/* 2031 */             state = 30;
/*      */             continue;
/*      */           } 
/*      */         case 90:
/* 2035 */           printState(90);
/* 2036 */           crvmin = 0.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 100:
/* 2044 */           printState(100);
/* 2045 */           if (nact >= n - 1) {
/* 2046 */             state = 190; continue;
/*      */           } 
/* 2048 */           dredsq = 0.0D;
/* 2049 */           dredg = 0.0D;
/* 2050 */           gredsq = 0.0D;
/* 2051 */           for (m = 0; m < n; m++) {
/* 2052 */             if (xbdi.getEntry(m) == 0.0D) {
/*      */               
/* 2054 */               double d1 = this.trialStepPoint.getEntry(m);
/* 2055 */               dredsq += d1 * d1;
/* 2056 */               dredg += this.trialStepPoint.getEntry(m) * gnew.getEntry(m);
/*      */               
/* 2058 */               d1 = gnew.getEntry(m);
/* 2059 */               gredsq += d1 * d1;
/* 2060 */               s.setEntry(m, this.trialStepPoint.getEntry(m));
/*      */             } else {
/* 2062 */               s.setEntry(m, 0.0D);
/*      */             } 
/*      */           } 
/* 2065 */           itcsav = iterc;
/* 2066 */           state = 210;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 120:
/* 2071 */           printState(120);
/* 2072 */           iterc++;
/* 2073 */           temp = gredsq * dredsq - dredg * dredg;
/* 2074 */           if (temp <= qred * 1.0E-4D * qred) {
/* 2075 */             state = 190; continue;
/*      */           } 
/* 2077 */           temp = FastMath.sqrt(temp);
/* 2078 */           for (m = 0; m < n; m++) {
/* 2079 */             if (xbdi.getEntry(m) == 0.0D) {
/* 2080 */               s.setEntry(m, (dredg * this.trialStepPoint.getEntry(m) - dredsq * gnew.getEntry(m)) / temp);
/*      */             } else {
/* 2082 */               s.setEntry(m, 0.0D);
/*      */             } 
/*      */           } 
/* 2085 */           sredg = -temp;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2092 */           angbd = 1.0D;
/* 2093 */           iact = -1;
/* 2094 */           for (m = 0; m < n; m++) {
/* 2095 */             if (xbdi.getEntry(m) == 0.0D) {
/* 2096 */               tempa = this.trustRegionCenterOffset.getEntry(m) + this.trialStepPoint.getEntry(m) - this.lowerDifference.getEntry(m);
/* 2097 */               tempb = this.upperDifference.getEntry(m) - this.trustRegionCenterOffset.getEntry(m) - this.trialStepPoint.getEntry(m);
/* 2098 */               if (tempa <= 0.0D) {
/* 2099 */                 nact++;
/* 2100 */                 xbdi.setEntry(m, -1.0D);
/* 2101 */                 state = 100; break;
/* 2102 */               }  if (tempb <= 0.0D) {
/* 2103 */                 nact++;
/* 2104 */                 xbdi.setEntry(m, 1.0D);
/* 2105 */                 state = 100;
/*      */                 break;
/*      */               } 
/* 2108 */               double d1 = this.trialStepPoint.getEntry(m);
/*      */               
/* 2110 */               double d2 = s.getEntry(m);
/* 2111 */               double ssq = d1 * d1 + d2 * d2;
/*      */               
/* 2113 */               d1 = this.trustRegionCenterOffset.getEntry(m) - this.lowerDifference.getEntry(m);
/* 2114 */               temp = ssq - d1 * d1;
/* 2115 */               if (temp > 0.0D) {
/* 2116 */                 temp = FastMath.sqrt(temp) - s.getEntry(m);
/* 2117 */                 if (angbd * temp > tempa) {
/* 2118 */                   angbd = tempa / temp;
/* 2119 */                   iact = m;
/* 2120 */                   xsav = -1.0D;
/*      */                 } 
/*      */               } 
/*      */               
/* 2124 */               d1 = this.upperDifference.getEntry(m) - this.trustRegionCenterOffset.getEntry(m);
/* 2125 */               temp = ssq - d1 * d1;
/* 2126 */               if (temp > 0.0D) {
/* 2127 */                 temp = FastMath.sqrt(temp) + s.getEntry(m);
/* 2128 */                 if (angbd * temp > tempb) {
/* 2129 */                   angbd = tempb / temp;
/* 2130 */                   iact = m;
/* 2131 */                   xsav = 1.0D;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2139 */           state = 210;
/*      */           continue;
/*      */         case 150:
/* 2142 */           printState(150);
/* 2143 */           shs = 0.0D;
/* 2144 */           dhs = 0.0D;
/* 2145 */           dhd = 0.0D;
/* 2146 */           for (m = 0; m < n; m++) {
/* 2147 */             if (xbdi.getEntry(m) == 0.0D) {
/* 2148 */               shs += s.getEntry(m) * hs.getEntry(m);
/* 2149 */               dhs += this.trialStepPoint.getEntry(m) * hs.getEntry(m);
/* 2150 */               dhd += this.trialStepPoint.getEntry(m) * hred.getEntry(m);
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2158 */           redmax = 0.0D;
/* 2159 */           isav = -1;
/* 2160 */           redsav = 0.0D;
/* 2161 */           iu = (int)(angbd * 17.0D + 3.1D);
/* 2162 */           for (m = 0; m < iu; m++) {
/* 2163 */             angt = angbd * m / iu;
/* 2164 */             double d = (angt + angt) / (1.0D + angt * angt);
/* 2165 */             temp = shs + angt * (angt * dhd - dhs - dhs);
/* 2166 */             rednew = d * (angt * dredg - sredg - 0.5D * d * temp);
/* 2167 */             if (rednew > redmax) {
/* 2168 */               redmax = rednew;
/* 2169 */               isav = m;
/* 2170 */               rdprev = redsav;
/* 2171 */             } else if (m == isav + 1) {
/* 2172 */               rdnext = rednew;
/*      */             } 
/* 2174 */             redsav = rednew;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2180 */           if (isav < 0) {
/* 2181 */             state = 190; continue;
/*      */           } 
/* 2183 */           if (isav < iu) {
/* 2184 */             temp = (rdnext - rdprev) / (redmax + redmax - rdprev - rdnext);
/* 2185 */             angt = angbd * (isav + 0.5D * temp) / iu;
/*      */           } 
/* 2187 */           cth = (1.0D - angt * angt) / (1.0D + angt * angt);
/* 2188 */           sth = (angt + angt) / (1.0D + angt * angt);
/* 2189 */           temp = shs + angt * (angt * dhd - dhs - dhs);
/* 2190 */           sdec = sth * (angt * dredg - sredg - 0.5D * sth * temp);
/* 2191 */           if (sdec <= 0.0D) {
/* 2192 */             state = 190;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/* 2199 */           dredg = 0.0D;
/* 2200 */           gredsq = 0.0D;
/* 2201 */           for (m = 0; m < n; m++) {
/* 2202 */             gnew.setEntry(m, gnew.getEntry(m) + (cth - 1.0D) * hred.getEntry(m) + sth * hs.getEntry(m));
/* 2203 */             if (xbdi.getEntry(m) == 0.0D) {
/* 2204 */               this.trialStepPoint.setEntry(m, cth * this.trialStepPoint.getEntry(m) + sth * s.getEntry(m));
/* 2205 */               dredg += this.trialStepPoint.getEntry(m) * gnew.getEntry(m);
/*      */               
/* 2207 */               double d1 = gnew.getEntry(m);
/* 2208 */               gredsq += d1 * d1;
/*      */             } 
/* 2210 */             hred.setEntry(m, cth * hred.getEntry(m) + sth * hs.getEntry(m));
/*      */           } 
/* 2212 */           qred += sdec;
/* 2213 */           if (iact >= 0 && isav == iu) {
/* 2214 */             nact++;
/* 2215 */             xbdi.setEntry(iact, xsav);
/* 2216 */             state = 100;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 2222 */           if (sdec > qred * 0.01D) {
/* 2223 */             state = 120;
/*      */             continue;
/*      */           } 
/*      */         case 190:
/* 2227 */           printState(190);
/* 2228 */           dsq = 0.0D;
/* 2229 */           for (m = 0; m < n; m++) {
/*      */ 
/*      */             
/* 2232 */             double min = FastMath.min(this.trustRegionCenterOffset.getEntry(m) + this.trialStepPoint.getEntry(m), this.upperDifference.getEntry(m));
/*      */             
/* 2234 */             this.newPoint.setEntry(m, FastMath.max(min, this.lowerDifference.getEntry(m)));
/* 2235 */             if (xbdi.getEntry(m) == -1.0D) {
/* 2236 */               this.newPoint.setEntry(m, this.lowerDifference.getEntry(m));
/*      */             }
/* 2238 */             if (xbdi.getEntry(m) == 1.0D) {
/* 2239 */               this.newPoint.setEntry(m, this.upperDifference.getEntry(m));
/*      */             }
/* 2241 */             this.trialStepPoint.setEntry(m, this.newPoint.getEntry(m) - this.trustRegionCenterOffset.getEntry(m));
/*      */             
/* 2243 */             double d1 = this.trialStepPoint.getEntry(m);
/* 2244 */             dsq += d1 * d1;
/*      */           } 
/* 2246 */           return new double[] { dsq, crvmin };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 210:
/* 2253 */           printState(210);
/* 2254 */           ih = 0;
/* 2255 */           for (j = 0; j < n; j++) {
/* 2256 */             hs.setEntry(j, 0.0D);
/* 2257 */             for (int i2 = 0; i2 <= j; i2++) {
/* 2258 */               if (i2 < j) {
/* 2259 */                 hs.setEntry(j, hs.getEntry(j) + this.modelSecondDerivativesValues.getEntry(ih) * s.getEntry(i2));
/*      */               }
/* 2261 */               hs.setEntry(i2, hs.getEntry(i2) + this.modelSecondDerivativesValues.getEntry(ih) * s.getEntry(j));
/* 2262 */               ih++;
/*      */             } 
/*      */           } 
/* 2265 */           tmp = this.interpolationPoints.operate((RealVector)s).ebeMultiply((RealVector)this.modelSecondDerivativesParameters);
/* 2266 */           for (k = 0; k < npt; k++) {
/* 2267 */             if (this.modelSecondDerivativesParameters.getEntry(k) != 0.0D) {
/* 2268 */               for (int i2 = 0; i2 < n; i2++) {
/* 2269 */                 hs.setEntry(i2, hs.getEntry(i2) + tmp.getEntry(k) * this.interpolationPoints.getEntry(k, i2));
/*      */               }
/*      */             }
/*      */           } 
/* 2273 */           if (crvmin != 0.0D) {
/* 2274 */             state = 50; continue;
/*      */           } 
/* 2276 */           if (iterc > itcsav) {
/* 2277 */             state = 150; continue;
/*      */           } 
/* 2279 */           for (i1 = 0; i1 < n; i1++) {
/* 2280 */             hred.setEntry(i1, hs.getEntry(i1));
/*      */           }
/* 2282 */           state = 120; continue;
/*      */       }  break;
/*      */     } 
/* 2285 */     throw new MathIllegalStateException(LocalizedFormats.SIMPLE_MESSAGE, new Object[] { "trsbox" });
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
/*      */   private void update(double beta, double denom, int knew) {
/* 2310 */     printMethod();
/*      */     
/* 2312 */     int n = this.currentBest.getDimension();
/* 2313 */     int npt = this.numberOfInterpolationPoints;
/* 2314 */     int nptm = npt - n - 1;
/*      */ 
/*      */     
/* 2317 */     ArrayRealVector work = new ArrayRealVector(npt + n);
/*      */     
/* 2319 */     double ztest = 0.0D;
/* 2320 */     for (int k = 0; k < npt; k++) {
/* 2321 */       for (int i2 = 0; i2 < nptm; i2++)
/*      */       {
/* 2323 */         ztest = FastMath.max(ztest, FastMath.abs(this.zMatrix.getEntry(k, i2)));
/*      */       }
/*      */     } 
/* 2326 */     ztest *= 1.0E-20D;
/*      */ 
/*      */ 
/*      */     
/* 2330 */     for (int j = 1; j < nptm; j++) {
/* 2331 */       double d = this.zMatrix.getEntry(knew, j);
/* 2332 */       if (FastMath.abs(d) > ztest) {
/*      */         
/* 2334 */         double d7 = this.zMatrix.getEntry(knew, 0);
/*      */         
/* 2336 */         double d3 = this.zMatrix.getEntry(knew, j);
/* 2337 */         double d4 = FastMath.sqrt(d7 * d7 + d3 * d3);
/* 2338 */         double d5 = this.zMatrix.getEntry(knew, 0) / d4;
/* 2339 */         double d6 = this.zMatrix.getEntry(knew, j) / d4;
/* 2340 */         for (int i2 = 0; i2 < npt; i2++) {
/* 2341 */           double d8 = d5 * this.zMatrix.getEntry(i2, 0) + d6 * this.zMatrix.getEntry(i2, j);
/* 2342 */           this.zMatrix.setEntry(i2, j, d5 * this.zMatrix.getEntry(i2, j) - d6 * this.zMatrix.getEntry(i2, 0));
/* 2343 */           this.zMatrix.setEntry(i2, 0, d8);
/*      */         } 
/*      */       } 
/* 2346 */       this.zMatrix.setEntry(knew, j, 0.0D);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2352 */     for (int i = 0; i < npt; i++) {
/* 2353 */       work.setEntry(i, this.zMatrix.getEntry(knew, 0) * this.zMatrix.getEntry(i, 0));
/*      */     }
/* 2355 */     double alpha = work.getEntry(knew);
/* 2356 */     double tau = this.lagrangeValuesAtNewPoint.getEntry(knew);
/* 2357 */     this.lagrangeValuesAtNewPoint.setEntry(knew, this.lagrangeValuesAtNewPoint.getEntry(knew) - 1.0D);
/*      */ 
/*      */ 
/*      */     
/* 2361 */     double sqrtDenom = FastMath.sqrt(denom);
/* 2362 */     double d1 = tau / sqrtDenom;
/* 2363 */     double d2 = this.zMatrix.getEntry(knew, 0) / sqrtDenom;
/* 2364 */     for (int i1 = 0; i1 < npt; i1++) {
/* 2365 */       this.zMatrix.setEntry(i1, 0, d1 * this.zMatrix.getEntry(i1, 0) - d2 * this.lagrangeValuesAtNewPoint.getEntry(i1));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2371 */     for (int m = 0; m < n; m++) {
/* 2372 */       int jp = npt + m;
/* 2373 */       work.setEntry(jp, this.bMatrix.getEntry(knew, m));
/* 2374 */       double d3 = (alpha * this.lagrangeValuesAtNewPoint.getEntry(jp) - tau * work.getEntry(jp)) / denom;
/* 2375 */       double d4 = (-beta * work.getEntry(jp) - tau * this.lagrangeValuesAtNewPoint.getEntry(jp)) / denom;
/* 2376 */       for (int i2 = 0; i2 <= jp; i2++) {
/* 2377 */         this.bMatrix.setEntry(i2, m, this.bMatrix.getEntry(i2, m) + d3 * this.lagrangeValuesAtNewPoint.getEntry(i2) + d4 * work.getEntry(i2));
/*      */         
/* 2379 */         if (i2 >= npt) {
/* 2380 */           this.bMatrix.setEntry(jp, i2 - npt, this.bMatrix.getEntry(i2, m));
/*      */         }
/*      */       } 
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
/*      */   private void setup(double[] lowerBound, double[] upperBound) {
/* 2394 */     printMethod();
/*      */     
/* 2396 */     double[] init = getStartPoint();
/* 2397 */     int dimension = init.length;
/*      */ 
/*      */     
/* 2400 */     if (dimension < 2) {
/* 2401 */       throw new NumberIsTooSmallException(Integer.valueOf(dimension), Integer.valueOf(2), true);
/*      */     }
/*      */     
/* 2404 */     int[] nPointsInterval = { dimension + 2, (dimension + 2) * (dimension + 1) / 2 };
/* 2405 */     if (this.numberOfInterpolationPoints < nPointsInterval[0] || this.numberOfInterpolationPoints > nPointsInterval[1])
/*      */     {
/* 2407 */       throw new OutOfRangeException(LocalizedFormats.NUMBER_OF_INTERPOLATION_POINTS, Integer.valueOf(this.numberOfInterpolationPoints), Integer.valueOf(nPointsInterval[0]), Integer.valueOf(nPointsInterval[1]));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2414 */     this.boundDifference = new double[dimension];
/*      */     
/* 2416 */     double requiredMinDiff = 2.0D * this.initialTrustRegionRadius;
/* 2417 */     double minDiff = Double.POSITIVE_INFINITY;
/* 2418 */     for (int i = 0; i < dimension; i++) {
/* 2419 */       this.boundDifference[i] = upperBound[i] - lowerBound[i];
/* 2420 */       minDiff = FastMath.min(minDiff, this.boundDifference[i]);
/*      */     } 
/* 2422 */     if (minDiff < requiredMinDiff) {
/* 2423 */       this.initialTrustRegionRadius = minDiff / 3.0D;
/*      */     }
/*      */ 
/*      */     
/* 2427 */     this.bMatrix = new Array2DRowRealMatrix(dimension + this.numberOfInterpolationPoints, dimension);
/*      */     
/* 2429 */     this.zMatrix = new Array2DRowRealMatrix(this.numberOfInterpolationPoints, this.numberOfInterpolationPoints - dimension - 1);
/*      */     
/* 2431 */     this.interpolationPoints = new Array2DRowRealMatrix(this.numberOfInterpolationPoints, dimension);
/*      */     
/* 2433 */     this.originShift = new ArrayRealVector(dimension);
/* 2434 */     this.fAtInterpolationPoints = new ArrayRealVector(this.numberOfInterpolationPoints);
/* 2435 */     this.trustRegionCenterOffset = new ArrayRealVector(dimension);
/* 2436 */     this.gradientAtTrustRegionCenter = new ArrayRealVector(dimension);
/* 2437 */     this.lowerDifference = new ArrayRealVector(dimension);
/* 2438 */     this.upperDifference = new ArrayRealVector(dimension);
/* 2439 */     this.modelSecondDerivativesParameters = new ArrayRealVector(this.numberOfInterpolationPoints);
/* 2440 */     this.newPoint = new ArrayRealVector(dimension);
/* 2441 */     this.alternativeNewPoint = new ArrayRealVector(dimension);
/* 2442 */     this.trialStepPoint = new ArrayRealVector(dimension);
/* 2443 */     this.lagrangeValuesAtNewPoint = new ArrayRealVector(dimension + this.numberOfInterpolationPoints);
/* 2444 */     this.modelSecondDerivativesValues = new ArrayRealVector(dimension * (dimension + 1) / 2);
/*      */   }
/*      */ 
/*      */   
/*      */   private static String caller(int n) {
/* 2449 */     Throwable t = new Throwable();
/* 2450 */     StackTraceElement[] elements = t.getStackTrace();
/* 2451 */     StackTraceElement e = elements[n];
/* 2452 */     return e.getMethodName() + " (at line " + e.getLineNumber() + ")";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void printState(int s) {}
/*      */ 
/*      */ 
/*      */   
/*      */   private static void printMethod() {}
/*      */ 
/*      */ 
/*      */   
/*      */   private static class PathIsExploredException
/*      */     extends RuntimeException
/*      */   {
/*      */     private static final long serialVersionUID = 745350979634801853L;
/*      */ 
/*      */     
/*      */     private static final String PATH_IS_EXPLORED = "If this exception is thrown, just remove it from the code";
/*      */ 
/*      */ 
/*      */     
/*      */     PathIsExploredException() {
/* 2476 */       super("If this exception is thrown, just remove it from the code " + BOBYQAOptimizer.caller(3));
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/direct/BOBYQAOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */