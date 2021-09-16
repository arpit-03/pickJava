/*      */ package org.apache.commons.math3.optim.nonlinear.scalar.noderiv;
/*      */ 
/*      */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*      */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*      */ import org.apache.commons.math3.exception.OutOfRangeException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*      */ import org.apache.commons.math3.linear.ArrayRealVector;
/*      */ import org.apache.commons.math3.linear.RealVector;
/*      */ import org.apache.commons.math3.optim.PointValuePair;
/*      */ import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
/*      */ import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
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
/*      */ public class BOBYQAOptimizer
/*      */   extends MultivariateOptimizer
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
/*  218 */     this(numberOfInterpolationPoints, 10.0D, 1.0E-8D);
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
/*  234 */     super(null);
/*  235 */     this.numberOfInterpolationPoints = numberOfInterpolationPoints;
/*  236 */     this.initialTrustRegionRadius = initialTrustRegionRadius;
/*  237 */     this.stoppingTrustRegionRadius = stoppingTrustRegionRadius;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected PointValuePair doOptimize() {
/*  243 */     double[] lowerBound = getLowerBound();
/*  244 */     double[] upperBound = getUpperBound();
/*      */ 
/*      */     
/*  247 */     setup(lowerBound, upperBound);
/*      */     
/*  249 */     this.isMinimize = (getGoalType() == GoalType.MINIMIZE);
/*  250 */     this.currentBest = new ArrayRealVector(getStartPoint());
/*      */     
/*  252 */     double value = bobyqa(lowerBound, upperBound);
/*      */     
/*  254 */     return new PointValuePair(this.currentBest.getDataRef(), this.isMinimize ? value : -value);
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
/*  295 */     printMethod();
/*      */     
/*  297 */     int n = this.currentBest.getDimension();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  306 */     for (int j = 0; j < n; j++) {
/*  307 */       double boundDiff = this.boundDifference[j];
/*  308 */       this.lowerDifference.setEntry(j, lowerBound[j] - this.currentBest.getEntry(j));
/*  309 */       this.upperDifference.setEntry(j, upperBound[j] - this.currentBest.getEntry(j));
/*  310 */       if (this.lowerDifference.getEntry(j) >= -this.initialTrustRegionRadius) {
/*  311 */         if (this.lowerDifference.getEntry(j) >= 0.0D) {
/*  312 */           this.currentBest.setEntry(j, lowerBound[j]);
/*  313 */           this.lowerDifference.setEntry(j, 0.0D);
/*  314 */           this.upperDifference.setEntry(j, boundDiff);
/*      */         } else {
/*  316 */           this.currentBest.setEntry(j, lowerBound[j] + this.initialTrustRegionRadius);
/*  317 */           this.lowerDifference.setEntry(j, -this.initialTrustRegionRadius);
/*      */           
/*  319 */           double deltaOne = upperBound[j] - this.currentBest.getEntry(j);
/*  320 */           this.upperDifference.setEntry(j, FastMath.max(deltaOne, this.initialTrustRegionRadius));
/*      */         } 
/*  322 */       } else if (this.upperDifference.getEntry(j) <= this.initialTrustRegionRadius) {
/*  323 */         if (this.upperDifference.getEntry(j) <= 0.0D) {
/*  324 */           this.currentBest.setEntry(j, upperBound[j]);
/*  325 */           this.lowerDifference.setEntry(j, -boundDiff);
/*  326 */           this.upperDifference.setEntry(j, 0.0D);
/*      */         } else {
/*  328 */           this.currentBest.setEntry(j, upperBound[j] - this.initialTrustRegionRadius);
/*      */           
/*  330 */           double deltaOne = lowerBound[j] - this.currentBest.getEntry(j);
/*  331 */           double deltaTwo = -this.initialTrustRegionRadius;
/*  332 */           this.lowerDifference.setEntry(j, FastMath.min(deltaOne, deltaTwo));
/*  333 */           this.upperDifference.setEntry(j, this.initialTrustRegionRadius);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  340 */     return bobyqb(lowerBound, upperBound);
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
/*  384 */     printMethod();
/*      */     
/*  386 */     int n = this.currentBest.getDimension();
/*  387 */     int npt = this.numberOfInterpolationPoints;
/*  388 */     int np = n + 1;
/*  389 */     int nptm = npt - np;
/*  390 */     int nh = n * np / 2;
/*      */     
/*  392 */     ArrayRealVector work1 = new ArrayRealVector(n);
/*  393 */     ArrayRealVector work2 = new ArrayRealVector(npt);
/*  394 */     ArrayRealVector work3 = new ArrayRealVector(npt);
/*      */     
/*  396 */     double cauchy = Double.NaN;
/*  397 */     double alpha = Double.NaN;
/*  398 */     double dsq = Double.NaN;
/*  399 */     double crvmin = Double.NaN;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  413 */     this.trustRegionCenterInterpolationPointIndex = 0;
/*      */     
/*  415 */     prelim(lowerBound, upperBound);
/*  416 */     double xoptsq = 0.0D;
/*  417 */     for (int i = 0; i < n; i++) {
/*  418 */       this.trustRegionCenterOffset.setEntry(i, this.interpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex, i));
/*      */       
/*  420 */       double deltaOne = this.trustRegionCenterOffset.getEntry(i);
/*  421 */       xoptsq += deltaOne * deltaOne;
/*      */     } 
/*  423 */     double fsave = this.fAtInterpolationPoints.getEntry(0);
/*  424 */     int kbase = 0;
/*      */ 
/*      */ 
/*      */     
/*  428 */     int ntrits = 0;
/*  429 */     int itest = 0;
/*  430 */     int knew = 0;
/*  431 */     int nfsav = getEvaluations();
/*  432 */     double rho = this.initialTrustRegionRadius;
/*  433 */     double delta = rho;
/*  434 */     double diffa = 0.0D;
/*  435 */     double diffb = 0.0D;
/*  436 */     double diffc = 0.0D;
/*  437 */     double f = 0.0D;
/*  438 */     double beta = 0.0D;
/*  439 */     double adelt = 0.0D;
/*  440 */     double denom = 0.0D;
/*  441 */     double ratio = 0.0D;
/*  442 */     double dnorm = 0.0D;
/*  443 */     double scaden = 0.0D;
/*  444 */     double biglsq = 0.0D;
/*  445 */     double distsq = 0.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  450 */     int state = 20; while (true) {
/*      */       ArrayRealVector gnew; double[] alphaCauchy; int i2; int m; double bsum; int i1; double fopt; int k; ArrayRealVector xbdi; int i3; ArrayRealVector s; double dx; double vquad; ArrayRealVector hs; ArrayRealVector hred; int j; int ih; double[] dsqCrvmin; int i5; int i4; double diff; double deltaOne; double pqold; double deltaTwo; int i10; int i9; int i8; int i7; int i6; double d1; double d2;
/*  452 */       switch (state) {
/*      */         case 20:
/*  454 */           printState(20);
/*  455 */           if (this.trustRegionCenterInterpolationPointIndex != 0) {
/*  456 */             int i11 = 0;
/*  457 */             for (int i12 = 0; i12 < n; i12++) {
/*  458 */               for (int i13 = 0; i13 <= i12; i13++) {
/*  459 */                 if (i13 < i12) {
/*  460 */                   this.gradientAtTrustRegionCenter.setEntry(i12, this.gradientAtTrustRegionCenter.getEntry(i12) + this.modelSecondDerivativesValues.getEntry(i11) * this.trustRegionCenterOffset.getEntry(i13));
/*      */                 }
/*  462 */                 this.gradientAtTrustRegionCenter.setEntry(i13, this.gradientAtTrustRegionCenter.getEntry(i13) + this.modelSecondDerivativesValues.getEntry(i11) * this.trustRegionCenterOffset.getEntry(i12));
/*  463 */                 i11++;
/*      */               } 
/*      */             } 
/*  466 */             if (getEvaluations() > npt) {
/*  467 */               for (int i13 = 0; i13 < npt; i13++) {
/*  468 */                 double temp = 0.0D;
/*  469 */                 for (int i15 = 0; i15 < n; i15++) {
/*  470 */                   temp += this.interpolationPoints.getEntry(i13, i15) * this.trustRegionCenterOffset.getEntry(i15);
/*      */                 }
/*  472 */                 temp *= this.modelSecondDerivativesParameters.getEntry(i13);
/*  473 */                 for (int i14 = 0; i14 < n; i14++) {
/*  474 */                   this.gradientAtTrustRegionCenter.setEntry(i14, this.gradientAtTrustRegionCenter.getEntry(i14) + temp * this.interpolationPoints.getEntry(i13, i14));
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
/*  490 */           printState(60);
/*  491 */           gnew = new ArrayRealVector(n);
/*  492 */           xbdi = new ArrayRealVector(n);
/*  493 */           s = new ArrayRealVector(n);
/*  494 */           hs = new ArrayRealVector(n);
/*  495 */           hred = new ArrayRealVector(n);
/*      */           
/*  497 */           dsqCrvmin = trsbox(delta, gnew, xbdi, s, hs, hred);
/*      */           
/*  499 */           dsq = dsqCrvmin[0];
/*  500 */           crvmin = dsqCrvmin[1];
/*      */ 
/*      */           
/*  503 */           deltaOne = delta;
/*  504 */           deltaTwo = FastMath.sqrt(dsq);
/*  505 */           dnorm = FastMath.min(deltaOne, deltaTwo);
/*  506 */           if (dnorm < 0.5D * rho) {
/*  507 */             ntrits = -1;
/*      */             
/*  509 */             deltaOne = 10.0D * rho;
/*  510 */             distsq = deltaOne * deltaOne;
/*  511 */             if (getEvaluations() <= nfsav + 2) {
/*  512 */               state = 650;
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  522 */             deltaOne = FastMath.max(diffa, diffb);
/*  523 */             double errbig = FastMath.max(deltaOne, diffc);
/*  524 */             double frhosq = rho * 0.125D * rho;
/*  525 */             if (crvmin > 0.0D && errbig > frhosq * crvmin) {
/*      */               
/*  527 */               state = 650; continue;
/*      */             } 
/*  529 */             double bdtol = errbig / rho;
/*  530 */             for (int i11 = 0; i11 < n; i11++) {
/*  531 */               double bdtest = bdtol;
/*  532 */               if (this.newPoint.getEntry(i11) == this.lowerDifference.getEntry(i11)) {
/*  533 */                 bdtest = work1.getEntry(i11);
/*      */               }
/*  535 */               if (this.newPoint.getEntry(i11) == this.upperDifference.getEntry(i11)) {
/*  536 */                 bdtest = -work1.getEntry(i11);
/*      */               }
/*  538 */               if (bdtest < bdtol) {
/*  539 */                 double curv = this.modelSecondDerivativesValues.getEntry((i11 + i11 * i11) / 2);
/*  540 */                 for (int i12 = 0; i12 < npt; i12++) {
/*      */                   
/*  542 */                   double d = this.interpolationPoints.getEntry(i12, i11);
/*  543 */                   curv += this.modelSecondDerivativesParameters.getEntry(i12) * d * d;
/*      */                 } 
/*  545 */                 bdtest += 0.5D * curv * rho;
/*  546 */                 if (bdtest < bdtol) {
/*  547 */                   state = 650;
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } 
/*  552 */             state = 680; continue;
/*      */           } 
/*  554 */           ntrits++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 90:
/*  564 */           printState(90);
/*  565 */           if (dsq <= xoptsq * 0.001D) {
/*  566 */             double fracsq = xoptsq * 0.25D;
/*  567 */             double sumpq = 0.0D;
/*      */ 
/*      */             
/*  570 */             for (int i13 = 0; i13 < npt; i13++) {
/*  571 */               sumpq += this.modelSecondDerivativesParameters.getEntry(i13);
/*  572 */               double sum = -0.5D * xoptsq;
/*  573 */               for (int i16 = 0; i16 < n; i16++) {
/*  574 */                 sum += this.interpolationPoints.getEntry(i13, i16) * this.trustRegionCenterOffset.getEntry(i16);
/*      */               }
/*      */               
/*  577 */               work2.setEntry(i13, sum);
/*  578 */               double temp = fracsq - 0.5D * sum;
/*  579 */               for (int i17 = 0; i17 < n; i17++) {
/*  580 */                 work1.setEntry(i17, this.bMatrix.getEntry(i13, i17));
/*  581 */                 this.lagrangeValuesAtNewPoint.setEntry(i17, sum * this.interpolationPoints.getEntry(i13, i17) + temp * this.trustRegionCenterOffset.getEntry(i17));
/*  582 */                 int ip = npt + i17;
/*  583 */                 for (int i18 = 0; i18 <= i17; i18++) {
/*  584 */                   this.bMatrix.setEntry(ip, i18, this.bMatrix.getEntry(ip, i18) + work1.getEntry(i17) * this.lagrangeValuesAtNewPoint.getEntry(i18) + this.lagrangeValuesAtNewPoint.getEntry(i17) * work1.getEntry(i18));
/*      */                 }
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  594 */             for (int i12 = 0; i12 < nptm; i12++) {
/*  595 */               double sumz = 0.0D;
/*  596 */               double sumw = 0.0D;
/*  597 */               for (int i18 = 0; i18 < npt; i18++) {
/*  598 */                 sumz += this.zMatrix.getEntry(i18, i12);
/*  599 */                 this.lagrangeValuesAtNewPoint.setEntry(i18, work2.getEntry(i18) * this.zMatrix.getEntry(i18, i12));
/*  600 */                 sumw += this.lagrangeValuesAtNewPoint.getEntry(i18);
/*      */               } 
/*  602 */               for (int i17 = 0; i17 < n; i17++) {
/*  603 */                 double sum = (fracsq * sumz - 0.5D * sumw) * this.trustRegionCenterOffset.getEntry(i17); int i19;
/*  604 */                 for (i19 = 0; i19 < npt; i19++) {
/*  605 */                   sum += this.lagrangeValuesAtNewPoint.getEntry(i19) * this.interpolationPoints.getEntry(i19, i17);
/*      */                 }
/*  607 */                 work1.setEntry(i17, sum);
/*  608 */                 for (i19 = 0; i19 < npt; i19++) {
/*  609 */                   this.bMatrix.setEntry(i19, i17, this.bMatrix.getEntry(i19, i17) + sum * this.zMatrix.getEntry(i19, i12));
/*      */                 }
/*      */               } 
/*      */ 
/*      */               
/*  614 */               for (int i16 = 0; i16 < n; i16++) {
/*  615 */                 int ip = i16 + npt;
/*  616 */                 double temp = work1.getEntry(i16);
/*  617 */                 for (int i19 = 0; i19 <= i16; i19++) {
/*  618 */                   this.bMatrix.setEntry(ip, i19, this.bMatrix.getEntry(ip, i19) + temp * work1.getEntry(i19));
/*      */                 }
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  628 */             int i11 = 0;
/*  629 */             for (int i15 = 0; i15 < n; i15++) {
/*  630 */               work1.setEntry(i15, -0.5D * sumpq * this.trustRegionCenterOffset.getEntry(i15));
/*  631 */               for (int i17 = 0; i17 < npt; i17++) {
/*  632 */                 work1.setEntry(i15, work1.getEntry(i15) + this.modelSecondDerivativesParameters.getEntry(i17) * this.interpolationPoints.getEntry(i17, i15));
/*  633 */                 this.interpolationPoints.setEntry(i17, i15, this.interpolationPoints.getEntry(i17, i15) - this.trustRegionCenterOffset.getEntry(i15));
/*      */               } 
/*  635 */               for (int i16 = 0; i16 <= i15; i16++) {
/*  636 */                 this.modelSecondDerivativesValues.setEntry(i11, this.modelSecondDerivativesValues.getEntry(i11) + work1.getEntry(i16) * this.trustRegionCenterOffset.getEntry(i15) + this.trustRegionCenterOffset.getEntry(i16) * work1.getEntry(i15));
/*      */ 
/*      */ 
/*      */                 
/*  640 */                 this.bMatrix.setEntry(npt + i16, i15, this.bMatrix.getEntry(npt + i15, i16));
/*  641 */                 i11++;
/*      */               } 
/*      */             } 
/*  644 */             for (int i14 = 0; i14 < n; i14++) {
/*  645 */               this.originShift.setEntry(i14, this.originShift.getEntry(i14) + this.trustRegionCenterOffset.getEntry(i14));
/*  646 */               this.newPoint.setEntry(i14, this.newPoint.getEntry(i14) - this.trustRegionCenterOffset.getEntry(i14));
/*  647 */               this.lowerDifference.setEntry(i14, this.lowerDifference.getEntry(i14) - this.trustRegionCenterOffset.getEntry(i14));
/*  648 */               this.upperDifference.setEntry(i14, this.upperDifference.getEntry(i14) - this.trustRegionCenterOffset.getEntry(i14));
/*  649 */               this.trustRegionCenterOffset.setEntry(i14, 0.0D);
/*      */             } 
/*  651 */             xoptsq = 0.0D;
/*      */           } 
/*  653 */           if (ntrits == 0) {
/*  654 */             state = 210; continue;
/*      */           } 
/*  656 */           state = 230;
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
/*  669 */           printState(210);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  681 */           alphaCauchy = altmov(knew, adelt);
/*  682 */           alpha = alphaCauchy[0];
/*  683 */           cauchy = alphaCauchy[1];
/*      */           
/*  685 */           for (i3 = 0; i3 < n; i3++) {
/*  686 */             this.trialStepPoint.setEntry(i3, this.newPoint.getEntry(i3) - this.trustRegionCenterOffset.getEntry(i3));
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 230:
/*  695 */           printState(230);
/*  696 */           for (i2 = 0; i2 < npt; i2++) {
/*  697 */             double suma = 0.0D;
/*  698 */             double sumb = 0.0D;
/*  699 */             double sum = 0.0D;
/*  700 */             for (int i11 = 0; i11 < n; i11++) {
/*  701 */               suma += this.interpolationPoints.getEntry(i2, i11) * this.trialStepPoint.getEntry(i11);
/*  702 */               sumb += this.interpolationPoints.getEntry(i2, i11) * this.trustRegionCenterOffset.getEntry(i11);
/*  703 */               sum += this.bMatrix.getEntry(i2, i11) * this.trialStepPoint.getEntry(i11);
/*      */             } 
/*  705 */             work3.setEntry(i2, suma * (0.5D * suma + sumb));
/*  706 */             this.lagrangeValuesAtNewPoint.setEntry(i2, sum);
/*  707 */             work2.setEntry(i2, suma);
/*      */           } 
/*  709 */           beta = 0.0D;
/*  710 */           for (m = 0; m < nptm; m++) {
/*  711 */             double sum = 0.0D; int i11;
/*  712 */             for (i11 = 0; i11 < npt; i11++) {
/*  713 */               sum += this.zMatrix.getEntry(i11, m) * work3.getEntry(i11);
/*      */             }
/*  715 */             beta -= sum * sum;
/*  716 */             for (i11 = 0; i11 < npt; i11++) {
/*  717 */               this.lagrangeValuesAtNewPoint.setEntry(i11, this.lagrangeValuesAtNewPoint.getEntry(i11) + sum * this.zMatrix.getEntry(i11, m));
/*      */             }
/*      */           } 
/*  720 */           dsq = 0.0D;
/*  721 */           bsum = 0.0D;
/*  722 */           dx = 0.0D;
/*  723 */           for (j = 0; j < n; j++) {
/*      */             
/*  725 */             double d3 = this.trialStepPoint.getEntry(j);
/*  726 */             dsq += d3 * d3;
/*  727 */             double sum = 0.0D;
/*  728 */             for (int i11 = 0; i11 < npt; i11++) {
/*  729 */               sum += work3.getEntry(i11) * this.bMatrix.getEntry(i11, j);
/*      */             }
/*  731 */             bsum += sum * this.trialStepPoint.getEntry(j);
/*  732 */             int jp = npt + j;
/*  733 */             for (int i12 = 0; i12 < n; i12++) {
/*  734 */               sum += this.bMatrix.getEntry(jp, i12) * this.trialStepPoint.getEntry(i12);
/*      */             }
/*  736 */             this.lagrangeValuesAtNewPoint.setEntry(jp, sum);
/*  737 */             bsum += sum * this.trialStepPoint.getEntry(j);
/*  738 */             dx += this.trialStepPoint.getEntry(j) * this.trustRegionCenterOffset.getEntry(j);
/*      */           } 
/*      */           
/*  741 */           beta = dx * dx + dsq * (xoptsq + dx + dx + 0.5D * dsq) + beta - bsum;
/*      */ 
/*      */ 
/*      */           
/*  745 */           this.lagrangeValuesAtNewPoint.setEntry(this.trustRegionCenterInterpolationPointIndex, this.lagrangeValuesAtNewPoint.getEntry(this.trustRegionCenterInterpolationPointIndex) + 1.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  752 */           if (ntrits == 0) {
/*      */             
/*  754 */             double d = this.lagrangeValuesAtNewPoint.getEntry(knew);
/*  755 */             denom = d * d + alpha * beta;
/*  756 */             if (denom < cauchy && cauchy > 0.0D) {
/*  757 */               for (int i11 = 0; i11 < n; i11++) {
/*  758 */                 this.newPoint.setEntry(i11, this.alternativeNewPoint.getEntry(i11));
/*  759 */                 this.trialStepPoint.setEntry(i11, this.newPoint.getEntry(i11) - this.trustRegionCenterOffset.getEntry(i11));
/*      */               } 
/*  761 */               cauchy = 0.0D;
/*  762 */               state = 230;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */           } else {
/*  771 */             double delsq = delta * delta;
/*  772 */             scaden = 0.0D;
/*  773 */             biglsq = 0.0D;
/*  774 */             knew = 0;
/*  775 */             for (int i11 = 0; i11 < npt; i11++) {
/*  776 */               if (i11 != this.trustRegionCenterInterpolationPointIndex) {
/*      */ 
/*      */                 
/*  779 */                 double hdiag = 0.0D;
/*  780 */                 for (int i12 = 0; i12 < nptm; i12++) {
/*      */                   
/*  782 */                   double d = this.zMatrix.getEntry(i11, i12);
/*  783 */                   hdiag += d * d;
/*      */                 } 
/*      */                 
/*  786 */                 double d3 = this.lagrangeValuesAtNewPoint.getEntry(i11);
/*  787 */                 double den = beta * hdiag + d3 * d3;
/*  788 */                 distsq = 0.0D;
/*  789 */                 for (int i13 = 0; i13 < n; i13++) {
/*      */                   
/*  791 */                   double d = this.interpolationPoints.getEntry(i11, i13) - this.trustRegionCenterOffset.getEntry(i13);
/*  792 */                   distsq += d * d;
/*      */                 } 
/*      */ 
/*      */                 
/*  796 */                 double d4 = distsq / delsq;
/*  797 */                 double temp = FastMath.max(1.0D, d4 * d4);
/*  798 */                 if (temp * den > scaden) {
/*  799 */                   scaden = temp * den;
/*  800 */                   knew = i11;
/*  801 */                   denom = den;
/*      */                 } 
/*      */ 
/*      */                 
/*  805 */                 double d5 = this.lagrangeValuesAtNewPoint.getEntry(i11);
/*  806 */                 biglsq = FastMath.max(biglsq, temp * d5 * d5);
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
/*  818 */           printState(360);
/*  819 */           for (i1 = 0; i1 < n; i1++) {
/*      */ 
/*      */             
/*  822 */             double d3 = lowerBound[i1];
/*  823 */             double d4 = this.originShift.getEntry(i1) + this.newPoint.getEntry(i1);
/*  824 */             double d5 = FastMath.max(d3, d4);
/*  825 */             double d6 = upperBound[i1];
/*  826 */             this.currentBest.setEntry(i1, FastMath.min(d5, d6));
/*  827 */             if (this.newPoint.getEntry(i1) == this.lowerDifference.getEntry(i1)) {
/*  828 */               this.currentBest.setEntry(i1, lowerBound[i1]);
/*      */             }
/*  830 */             if (this.newPoint.getEntry(i1) == this.upperDifference.getEntry(i1)) {
/*  831 */               this.currentBest.setEntry(i1, upperBound[i1]);
/*      */             }
/*      */           } 
/*      */           
/*  835 */           f = computeObjectiveValue(this.currentBest.toArray());
/*      */           
/*  837 */           if (!this.isMinimize) {
/*  838 */             f = -f;
/*      */           }
/*  840 */           if (ntrits == -1) {
/*  841 */             fsave = f;
/*  842 */             state = 720;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/*  848 */           fopt = this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex);
/*  849 */           vquad = 0.0D;
/*  850 */           ih = 0;
/*  851 */           for (i5 = 0; i5 < n; i5++) {
/*  852 */             vquad += this.trialStepPoint.getEntry(i5) * this.gradientAtTrustRegionCenter.getEntry(i5);
/*  853 */             for (int i11 = 0; i11 <= i5; i11++) {
/*  854 */               double temp = this.trialStepPoint.getEntry(i11) * this.trialStepPoint.getEntry(i5);
/*  855 */               if (i11 == i5) {
/*  856 */                 temp *= 0.5D;
/*      */               }
/*  858 */               vquad += this.modelSecondDerivativesValues.getEntry(ih) * temp;
/*  859 */               ih++;
/*      */             } 
/*      */           } 
/*  862 */           for (i4 = 0; i4 < npt; i4++) {
/*      */             
/*  864 */             double d3 = work2.getEntry(i4);
/*  865 */             double d4 = d3 * d3;
/*  866 */             vquad += 0.5D * this.modelSecondDerivativesParameters.getEntry(i4) * d4;
/*      */           } 
/*  868 */           diff = f - fopt - vquad;
/*  869 */           diffc = diffb;
/*  870 */           diffb = diffa;
/*  871 */           diffa = FastMath.abs(diff);
/*  872 */           if (dnorm > rho) {
/*  873 */             nfsav = getEvaluations();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  878 */           if (ntrits > 0) {
/*  879 */             if (vquad >= 0.0D) {
/*  880 */               throw new MathIllegalStateException(LocalizedFormats.TRUST_REGION_STEP_FAILED, new Object[] { Double.valueOf(vquad) });
/*      */             }
/*  882 */             ratio = (f - fopt) / vquad;
/*  883 */             double hDelta = 0.5D * delta;
/*  884 */             if (ratio <= 0.1D) {
/*      */               
/*  886 */               delta = FastMath.min(hDelta, dnorm);
/*  887 */             } else if (ratio <= 0.7D) {
/*      */               
/*  889 */               delta = FastMath.max(hDelta, dnorm);
/*      */             } else {
/*      */               
/*  892 */               delta = FastMath.max(hDelta, 2.0D * dnorm);
/*      */             } 
/*  894 */             if (delta <= rho * 1.5D) {
/*  895 */               delta = rho;
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  900 */             if (f < fopt) {
/*  901 */               int ksav = knew;
/*  902 */               double densav = denom;
/*  903 */               double delsq = delta * delta;
/*  904 */               scaden = 0.0D;
/*  905 */               biglsq = 0.0D;
/*  906 */               knew = 0;
/*  907 */               for (int i11 = 0; i11 < npt; i11++) {
/*  908 */                 double hdiag = 0.0D;
/*  909 */                 for (int i12 = 0; i12 < nptm; i12++) {
/*      */                   
/*  911 */                   double d = this.zMatrix.getEntry(i11, i12);
/*  912 */                   hdiag += d * d;
/*      */                 } 
/*      */                 
/*  915 */                 double d6 = this.lagrangeValuesAtNewPoint.getEntry(i11);
/*  916 */                 double den = beta * hdiag + d6 * d6;
/*  917 */                 distsq = 0.0D;
/*  918 */                 for (int i13 = 0; i13 < n; i13++) {
/*      */                   
/*  920 */                   double d = this.interpolationPoints.getEntry(i11, i13) - this.newPoint.getEntry(i13);
/*  921 */                   distsq += d * d;
/*      */                 } 
/*      */ 
/*      */                 
/*  925 */                 double d3 = distsq / delsq;
/*  926 */                 double temp = FastMath.max(1.0D, d3 * d3);
/*  927 */                 if (temp * den > scaden) {
/*  928 */                   scaden = temp * den;
/*  929 */                   knew = i11;
/*  930 */                   denom = den;
/*      */                 } 
/*      */ 
/*      */                 
/*  934 */                 double d4 = this.lagrangeValuesAtNewPoint.getEntry(i11);
/*  935 */                 double d5 = temp * d4 * d4;
/*  936 */                 biglsq = FastMath.max(biglsq, d5);
/*      */               } 
/*  938 */               if (scaden <= 0.5D * biglsq) {
/*  939 */                 knew = ksav;
/*  940 */                 denom = densav;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  948 */           update(beta, denom, knew);
/*      */           
/*  950 */           ih = 0;
/*  951 */           pqold = this.modelSecondDerivativesParameters.getEntry(knew);
/*  952 */           this.modelSecondDerivativesParameters.setEntry(knew, 0.0D);
/*  953 */           for (i10 = 0; i10 < n; i10++) {
/*  954 */             double temp = pqold * this.interpolationPoints.getEntry(knew, i10);
/*  955 */             for (int i11 = 0; i11 <= i10; i11++) {
/*  956 */               this.modelSecondDerivativesValues.setEntry(ih, this.modelSecondDerivativesValues.getEntry(ih) + temp * this.interpolationPoints.getEntry(knew, i11));
/*  957 */               ih++;
/*      */             } 
/*      */           } 
/*  960 */           for (i9 = 0; i9 < nptm; i9++) {
/*  961 */             double temp = diff * this.zMatrix.getEntry(knew, i9);
/*  962 */             for (int i11 = 0; i11 < npt; i11++) {
/*  963 */               this.modelSecondDerivativesParameters.setEntry(i11, this.modelSecondDerivativesParameters.getEntry(i11) + temp * this.zMatrix.getEntry(i11, i9));
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  970 */           this.fAtInterpolationPoints.setEntry(knew, f);
/*  971 */           for (i8 = 0; i8 < n; i8++) {
/*  972 */             this.interpolationPoints.setEntry(knew, i8, this.newPoint.getEntry(i8));
/*  973 */             work1.setEntry(i8, this.bMatrix.getEntry(knew, i8));
/*      */           } 
/*  975 */           for (i7 = 0; i7 < npt; i7++) {
/*  976 */             double suma = 0.0D;
/*  977 */             for (int i11 = 0; i11 < nptm; i11++) {
/*  978 */               suma += this.zMatrix.getEntry(knew, i11) * this.zMatrix.getEntry(i7, i11);
/*      */             }
/*  980 */             double sumb = 0.0D;
/*  981 */             for (int i12 = 0; i12 < n; i12++) {
/*  982 */               sumb += this.interpolationPoints.getEntry(i7, i12) * this.trustRegionCenterOffset.getEntry(i12);
/*      */             }
/*  984 */             double temp = suma * sumb;
/*  985 */             for (int i13 = 0; i13 < n; i13++) {
/*  986 */               work1.setEntry(i13, work1.getEntry(i13) + temp * this.interpolationPoints.getEntry(i7, i13));
/*      */             }
/*      */           } 
/*  989 */           for (i6 = 0; i6 < n; i6++) {
/*  990 */             this.gradientAtTrustRegionCenter.setEntry(i6, this.gradientAtTrustRegionCenter.getEntry(i6) + diff * work1.getEntry(i6));
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  995 */           if (f < fopt) {
/*  996 */             this.trustRegionCenterInterpolationPointIndex = knew;
/*  997 */             xoptsq = 0.0D;
/*  998 */             ih = 0;
/*  999 */             for (int i12 = 0; i12 < n; i12++) {
/* 1000 */               this.trustRegionCenterOffset.setEntry(i12, this.newPoint.getEntry(i12));
/*      */               
/* 1002 */               double d = this.trustRegionCenterOffset.getEntry(i12);
/* 1003 */               xoptsq += d * d;
/* 1004 */               for (int i13 = 0; i13 <= i12; i13++) {
/* 1005 */                 if (i13 < i12) {
/* 1006 */                   this.gradientAtTrustRegionCenter.setEntry(i12, this.gradientAtTrustRegionCenter.getEntry(i12) + this.modelSecondDerivativesValues.getEntry(ih) * this.trialStepPoint.getEntry(i13));
/*      */                 }
/* 1008 */                 this.gradientAtTrustRegionCenter.setEntry(i13, this.gradientAtTrustRegionCenter.getEntry(i13) + this.modelSecondDerivativesValues.getEntry(ih) * this.trialStepPoint.getEntry(i12));
/* 1009 */                 ih++;
/*      */               } 
/*      */             } 
/* 1012 */             for (int i11 = 0; i11 < npt; i11++) {
/* 1013 */               double temp = 0.0D;
/* 1014 */               for (int i14 = 0; i14 < n; i14++) {
/* 1015 */                 temp += this.interpolationPoints.getEntry(i11, i14) * this.trialStepPoint.getEntry(i14);
/*      */               }
/* 1017 */               temp *= this.modelSecondDerivativesParameters.getEntry(i11);
/* 1018 */               for (int i13 = 0; i13 < n; i13++) {
/* 1019 */                 this.gradientAtTrustRegionCenter.setEntry(i13, this.gradientAtTrustRegionCenter.getEntry(i13) + temp * this.interpolationPoints.getEntry(i11, i13));
/*      */               }
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1028 */           if (ntrits > 0) {
/* 1029 */             for (int i13 = 0; i13 < npt; i13++) {
/* 1030 */               this.lagrangeValuesAtNewPoint.setEntry(i13, this.fAtInterpolationPoints.getEntry(i13) - this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex));
/* 1031 */               work3.setEntry(i13, 0.0D);
/*      */             } 
/* 1033 */             for (int i12 = 0; i12 < nptm; i12++) {
/* 1034 */               double sum = 0.0D; int i15;
/* 1035 */               for (i15 = 0; i15 < npt; i15++) {
/* 1036 */                 sum += this.zMatrix.getEntry(i15, i12) * this.lagrangeValuesAtNewPoint.getEntry(i15);
/*      */               }
/* 1038 */               for (i15 = 0; i15 < npt; i15++) {
/* 1039 */                 work3.setEntry(i15, work3.getEntry(i15) + sum * this.zMatrix.getEntry(i15, i12));
/*      */               }
/*      */             } 
/* 1042 */             for (int i11 = 0; i11 < npt; i11++) {
/* 1043 */               double sum = 0.0D;
/* 1044 */               for (int i15 = 0; i15 < n; i15++) {
/* 1045 */                 sum += this.interpolationPoints.getEntry(i11, i15) * this.trustRegionCenterOffset.getEntry(i15);
/*      */               }
/* 1047 */               work2.setEntry(i11, work3.getEntry(i11));
/* 1048 */               work3.setEntry(i11, sum * work3.getEntry(i11));
/*      */             } 
/* 1050 */             double gqsq = 0.0D;
/* 1051 */             double gisq = 0.0D; int i14;
/* 1052 */             for (i14 = 0; i14 < n; i14++) {
/* 1053 */               double sum = 0.0D;
/* 1054 */               for (int i15 = 0; i15 < npt; i15++) {
/* 1055 */                 sum += this.bMatrix.getEntry(i15, i14) * this.lagrangeValuesAtNewPoint.getEntry(i15) + this.interpolationPoints.getEntry(i15, i14) * work3.getEntry(i15);
/*      */               }
/*      */               
/* 1058 */               if (this.trustRegionCenterOffset.getEntry(i14) == this.lowerDifference.getEntry(i14)) {
/*      */ 
/*      */                 
/* 1061 */                 double d3 = FastMath.min(0.0D, this.gradientAtTrustRegionCenter.getEntry(i14));
/* 1062 */                 gqsq += d3 * d3;
/*      */                 
/* 1064 */                 double d4 = FastMath.min(0.0D, sum);
/* 1065 */                 gisq += d4 * d4;
/* 1066 */               } else if (this.trustRegionCenterOffset.getEntry(i14) == this.upperDifference.getEntry(i14)) {
/*      */ 
/*      */                 
/* 1069 */                 double d3 = FastMath.max(0.0D, this.gradientAtTrustRegionCenter.getEntry(i14));
/* 1070 */                 gqsq += d3 * d3;
/*      */                 
/* 1072 */                 double d4 = FastMath.max(0.0D, sum);
/* 1073 */                 gisq += d4 * d4;
/*      */               } else {
/*      */                 
/* 1076 */                 double d = this.gradientAtTrustRegionCenter.getEntry(i14);
/* 1077 */                 gqsq += d * d;
/* 1078 */                 gisq += sum * sum;
/*      */               } 
/* 1080 */               this.lagrangeValuesAtNewPoint.setEntry(npt + i14, sum);
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1086 */             itest++;
/* 1087 */             if (gqsq < 10.0D * gisq) {
/* 1088 */               itest = 0;
/*      */             }
/* 1090 */             if (itest >= 3) {
/* 1091 */               int max; for (i14 = 0, max = FastMath.max(npt, nh); i14 < max; i14++) {
/* 1092 */                 if (i14 < n) {
/* 1093 */                   this.gradientAtTrustRegionCenter.setEntry(i14, this.lagrangeValuesAtNewPoint.getEntry(npt + i14));
/*      */                 }
/* 1095 */                 if (i14 < npt) {
/* 1096 */                   this.modelSecondDerivativesParameters.setEntry(i14, work2.getEntry(i14));
/*      */                 }
/* 1098 */                 if (i14 < nh) {
/* 1099 */                   this.modelSecondDerivativesValues.setEntry(i14, 0.0D);
/*      */                 }
/* 1101 */                 itest = 0;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1110 */           if (ntrits == 0) {
/* 1111 */             state = 60; continue;
/*      */           } 
/* 1113 */           if (f <= fopt + 0.1D * vquad) {
/* 1114 */             state = 60;
/*      */ 
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/* 1122 */           d1 = 2.0D * delta;
/*      */           
/* 1124 */           d2 = 10.0D * rho;
/* 1125 */           distsq = FastMath.max(d1 * d1, d2 * d2);
/*      */         
/*      */         case 650:
/* 1128 */           printState(650);
/* 1129 */           knew = -1;
/* 1130 */           for (k = 0; k < npt; k++) {
/* 1131 */             double sum = 0.0D;
/* 1132 */             for (int i11 = 0; i11 < n; i11++) {
/*      */               
/* 1134 */               double d = this.interpolationPoints.getEntry(k, i11) - this.trustRegionCenterOffset.getEntry(i11);
/* 1135 */               sum += d * d;
/*      */             } 
/* 1137 */             if (sum > distsq) {
/* 1138 */               knew = k;
/* 1139 */               distsq = sum;
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1149 */           if (knew >= 0) {
/* 1150 */             double dist = FastMath.sqrt(distsq);
/* 1151 */             if (ntrits == -1) {
/*      */               
/* 1153 */               delta = FastMath.min(0.1D * delta, 0.5D * dist);
/* 1154 */               if (delta <= rho * 1.5D) {
/* 1155 */                 delta = rho;
/*      */               }
/*      */             } 
/* 1158 */             ntrits = 0;
/*      */ 
/*      */             
/* 1161 */             double d3 = FastMath.min(0.1D * dist, delta);
/* 1162 */             adelt = FastMath.max(d3, rho);
/* 1163 */             dsq = adelt * adelt;
/* 1164 */             state = 90; continue;
/*      */           } 
/* 1166 */           if (ntrits == -1) {
/* 1167 */             state = 680; continue;
/*      */           } 
/* 1169 */           if (ratio > 0.0D) {
/* 1170 */             state = 60; continue;
/*      */           } 
/* 1172 */           if (FastMath.max(delta, dnorm) > rho) {
/* 1173 */             state = 60;
/*      */             continue;
/*      */           } 
/*      */ 
/*      */ 
/*      */         
/*      */         case 680:
/* 1180 */           printState(680);
/* 1181 */           if (rho > this.stoppingTrustRegionRadius) {
/* 1182 */             delta = 0.5D * rho;
/* 1183 */             ratio = rho / this.stoppingTrustRegionRadius;
/* 1184 */             if (ratio <= 16.0D) {
/* 1185 */               rho = this.stoppingTrustRegionRadius;
/* 1186 */             } else if (ratio <= 250.0D) {
/* 1187 */               rho = FastMath.sqrt(ratio) * this.stoppingTrustRegionRadius;
/*      */             } else {
/* 1189 */               rho *= 0.1D;
/*      */             } 
/* 1191 */             delta = FastMath.max(delta, rho);
/* 1192 */             ntrits = 0;
/* 1193 */             nfsav = getEvaluations();
/* 1194 */             state = 60;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 1200 */           if (ntrits == -1) {
/* 1201 */             state = 360;
/*      */             continue;
/*      */           } 
/*      */         case 720:
/* 1205 */           printState(720);
/* 1206 */           if (this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex) <= fsave) {
/* 1207 */             for (int i11 = 0; i11 < n; i11++) {
/*      */ 
/*      */               
/* 1210 */               double d3 = lowerBound[i11];
/* 1211 */               double d4 = this.originShift.getEntry(i11) + this.trustRegionCenterOffset.getEntry(i11);
/* 1212 */               double d5 = FastMath.max(d3, d4);
/* 1213 */               double d6 = upperBound[i11];
/* 1214 */               this.currentBest.setEntry(i11, FastMath.min(d5, d6));
/* 1215 */               if (this.trustRegionCenterOffset.getEntry(i11) == this.lowerDifference.getEntry(i11)) {
/* 1216 */                 this.currentBest.setEntry(i11, lowerBound[i11]);
/*      */               }
/* 1218 */               if (this.trustRegionCenterOffset.getEntry(i11) == this.upperDifference.getEntry(i11)) {
/* 1219 */                 this.currentBest.setEntry(i11, upperBound[i11]);
/*      */               }
/*      */             } 
/* 1222 */             f = this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex);
/*      */           } 
/* 1224 */           return f;
/*      */       }  break;
/*      */     } 
/* 1227 */     throw new MathIllegalStateException(LocalizedFormats.SIMPLE_MESSAGE, new Object[] { "bobyqb" });
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
/* 1270 */     printMethod();
/*      */     
/* 1272 */     int n = this.currentBest.getDimension();
/* 1273 */     int npt = this.numberOfInterpolationPoints;
/*      */     
/* 1275 */     ArrayRealVector glag = new ArrayRealVector(n);
/* 1276 */     ArrayRealVector hcol = new ArrayRealVector(npt);
/*      */     
/* 1278 */     ArrayRealVector work1 = new ArrayRealVector(n);
/* 1279 */     ArrayRealVector work2 = new ArrayRealVector(n);
/*      */     
/* 1281 */     for (int k = 0; k < npt; k++) {
/* 1282 */       hcol.setEntry(k, 0.0D);
/*      */     }
/* 1284 */     for (int j = 0, max = npt - n - 1; j < max; j++) {
/* 1285 */       double tmp = this.zMatrix.getEntry(knew, j);
/* 1286 */       for (int i3 = 0; i3 < npt; i3++) {
/* 1287 */         hcol.setEntry(i3, hcol.getEntry(i3) + tmp * this.zMatrix.getEntry(i3, j));
/*      */       }
/*      */     } 
/* 1290 */     double alpha = hcol.getEntry(knew);
/* 1291 */     double ha = 0.5D * alpha;
/*      */ 
/*      */ 
/*      */     
/* 1295 */     for (int i = 0; i < n; i++) {
/* 1296 */       glag.setEntry(i, this.bMatrix.getEntry(knew, i));
/*      */     }
/* 1298 */     for (int m = 0; m < npt; m++) {
/* 1299 */       double tmp = 0.0D;
/* 1300 */       for (int i4 = 0; i4 < n; i4++) {
/* 1301 */         tmp += this.interpolationPoints.getEntry(m, i4) * this.trustRegionCenterOffset.getEntry(i4);
/*      */       }
/* 1303 */       tmp *= hcol.getEntry(m);
/* 1304 */       for (int i3 = 0; i3 < n; i3++) {
/* 1305 */         glag.setEntry(i3, glag.getEntry(i3) + tmp * this.interpolationPoints.getEntry(m, i3));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1315 */     double presav = 0.0D;
/* 1316 */     double step = Double.NaN;
/* 1317 */     int ksav = 0;
/* 1318 */     int ibdsav = 0;
/* 1319 */     double stpsav = 0.0D;
/* 1320 */     for (int i2 = 0; i2 < npt; i2++) {
/* 1321 */       if (i2 != this.trustRegionCenterInterpolationPointIndex) {
/*      */ 
/*      */         
/* 1324 */         double dderiv = 0.0D;
/* 1325 */         double distsq = 0.0D;
/* 1326 */         for (int i3 = 0; i3 < n; i3++) {
/* 1327 */           double d = this.interpolationPoints.getEntry(i2, i3) - this.trustRegionCenterOffset.getEntry(i3);
/* 1328 */           dderiv += glag.getEntry(i3) * d;
/* 1329 */           distsq += d * d;
/*      */         } 
/* 1331 */         double subd = adelt / FastMath.sqrt(distsq);
/* 1332 */         double slbd = -subd;
/* 1333 */         int ilbd = 0;
/* 1334 */         int iubd = 0;
/* 1335 */         double sumin = FastMath.min(1.0D, subd);
/*      */ 
/*      */ 
/*      */         
/* 1339 */         for (int i4 = 0; i4 < n; i4++) {
/* 1340 */           double d = this.interpolationPoints.getEntry(i2, i4) - this.trustRegionCenterOffset.getEntry(i4);
/* 1341 */           if (d > 0.0D) {
/* 1342 */             if (slbd * d < this.lowerDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) {
/* 1343 */               slbd = (this.lowerDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) / d;
/* 1344 */               ilbd = -i4 - 1;
/*      */             } 
/* 1346 */             if (subd * d > this.upperDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) {
/*      */               
/* 1348 */               subd = FastMath.max(sumin, (this.upperDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) / d);
/*      */               
/* 1350 */               iubd = i4 + 1;
/*      */             } 
/* 1352 */           } else if (d < 0.0D) {
/* 1353 */             if (slbd * d > this.upperDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) {
/* 1354 */               slbd = (this.upperDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) / d;
/* 1355 */               ilbd = i4 + 1;
/*      */             } 
/* 1357 */             if (subd * d < this.lowerDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) {
/*      */               
/* 1359 */               subd = FastMath.max(sumin, (this.lowerDifference.getEntry(i4) - this.trustRegionCenterOffset.getEntry(i4)) / d);
/*      */               
/* 1361 */               iubd = -i4 - 1;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1369 */         step = slbd;
/* 1370 */         int isbd = ilbd;
/* 1371 */         double vlag = Double.NaN;
/* 1372 */         if (i2 == knew) {
/* 1373 */           double diff = dderiv - 1.0D;
/* 1374 */           vlag = slbd * (dderiv - slbd * diff);
/* 1375 */           double d1 = subd * (dderiv - subd * diff);
/* 1376 */           if (FastMath.abs(d1) > FastMath.abs(vlag)) {
/* 1377 */             step = subd;
/* 1378 */             vlag = d1;
/* 1379 */             isbd = iubd;
/*      */           } 
/* 1381 */           double d2 = 0.5D * dderiv;
/* 1382 */           double d3 = d2 - diff * slbd;
/* 1383 */           double d4 = d2 - diff * subd;
/* 1384 */           if (d3 * d4 < 0.0D) {
/* 1385 */             double d5 = d2 * d2 / diff;
/* 1386 */             if (FastMath.abs(d5) > FastMath.abs(vlag)) {
/* 1387 */               step = d2 / diff;
/* 1388 */               vlag = d5;
/* 1389 */               isbd = 0;
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1396 */           vlag = slbd * (1.0D - slbd);
/* 1397 */           double d = subd * (1.0D - subd);
/* 1398 */           if (FastMath.abs(d) > FastMath.abs(vlag)) {
/* 1399 */             step = subd;
/* 1400 */             vlag = d;
/* 1401 */             isbd = iubd;
/*      */           } 
/* 1403 */           if (subd > 0.5D && FastMath.abs(vlag) < 0.25D) {
/* 1404 */             step = 0.5D;
/* 1405 */             vlag = 0.25D;
/* 1406 */             isbd = 0;
/*      */           } 
/* 1408 */           vlag *= dderiv;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1413 */         double tmp = step * (1.0D - step) * distsq;
/* 1414 */         double predsq = vlag * vlag * (vlag * vlag + ha * tmp * tmp);
/* 1415 */         if (predsq > presav) {
/* 1416 */           presav = predsq;
/* 1417 */           ksav = i2;
/* 1418 */           stpsav = step;
/* 1419 */           ibdsav = isbd;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1425 */     for (int i1 = 0; i1 < n; i1++) {
/* 1426 */       double tmp = this.trustRegionCenterOffset.getEntry(i1) + stpsav * (this.interpolationPoints.getEntry(ksav, i1) - this.trustRegionCenterOffset.getEntry(i1));
/* 1427 */       this.newPoint.setEntry(i1, FastMath.max(this.lowerDifference.getEntry(i1), FastMath.min(this.upperDifference.getEntry(i1), tmp)));
/*      */     } 
/*      */     
/* 1430 */     if (ibdsav < 0) {
/* 1431 */       this.newPoint.setEntry(-ibdsav - 1, this.lowerDifference.getEntry(-ibdsav - 1));
/*      */     }
/* 1433 */     if (ibdsav > 0) {
/* 1434 */       this.newPoint.setEntry(ibdsav - 1, this.upperDifference.getEntry(ibdsav - 1));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1441 */     double bigstp = adelt + adelt;
/* 1442 */     int iflag = 0;
/* 1443 */     double cauchy = Double.NaN;
/* 1444 */     double csave = 0.0D;
/*      */     while (true) {
/* 1446 */       double wfixsq = 0.0D;
/* 1447 */       double ggfree = 0.0D;
/* 1448 */       for (int i3 = 0; i3 < n; i3++) {
/* 1449 */         double glagValue = glag.getEntry(i3);
/* 1450 */         work1.setEntry(i3, 0.0D);
/* 1451 */         if (FastMath.min(this.trustRegionCenterOffset.getEntry(i3) - this.lowerDifference.getEntry(i3), glagValue) > 0.0D || FastMath.max(this.trustRegionCenterOffset.getEntry(i3) - this.upperDifference.getEntry(i3), glagValue) < 0.0D) {
/*      */           
/* 1453 */           work1.setEntry(i3, bigstp);
/*      */           
/* 1455 */           ggfree += glagValue * glagValue;
/*      */         } 
/*      */       } 
/* 1458 */       if (ggfree == 0.0D) {
/* 1459 */         return new double[] { alpha, 0.0D };
/*      */       }
/*      */ 
/*      */       
/* 1463 */       double tmp1 = adelt * adelt - wfixsq;
/* 1464 */       if (tmp1 > 0.0D) {
/* 1465 */         step = FastMath.sqrt(tmp1 / ggfree);
/* 1466 */         ggfree = 0.0D;
/* 1467 */         for (int i6 = 0; i6 < n; i6++) {
/* 1468 */           if (work1.getEntry(i6) == bigstp) {
/* 1469 */             double tmp2 = this.trustRegionCenterOffset.getEntry(i6) - step * glag.getEntry(i6);
/* 1470 */             if (tmp2 <= this.lowerDifference.getEntry(i6)) {
/* 1471 */               work1.setEntry(i6, this.lowerDifference.getEntry(i6) - this.trustRegionCenterOffset.getEntry(i6));
/*      */               
/* 1473 */               double d1 = work1.getEntry(i6);
/* 1474 */               wfixsq += d1 * d1;
/* 1475 */             } else if (tmp2 >= this.upperDifference.getEntry(i6)) {
/* 1476 */               work1.setEntry(i6, this.upperDifference.getEntry(i6) - this.trustRegionCenterOffset.getEntry(i6));
/*      */               
/* 1478 */               double d1 = work1.getEntry(i6);
/* 1479 */               wfixsq += d1 * d1;
/*      */             } else {
/*      */               
/* 1482 */               double d1 = glag.getEntry(i6);
/* 1483 */               ggfree += d1 * d1;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1492 */       double gw = 0.0D;
/* 1493 */       for (int i4 = 0; i4 < n; i4++) {
/* 1494 */         double glagValue = glag.getEntry(i4);
/* 1495 */         if (work1.getEntry(i4) == bigstp) {
/* 1496 */           work1.setEntry(i4, -step * glagValue);
/* 1497 */           double min = FastMath.min(this.upperDifference.getEntry(i4), this.trustRegionCenterOffset.getEntry(i4) + work1.getEntry(i4));
/*      */           
/* 1499 */           this.alternativeNewPoint.setEntry(i4, FastMath.max(this.lowerDifference.getEntry(i4), min));
/* 1500 */         } else if (work1.getEntry(i4) == 0.0D) {
/* 1501 */           this.alternativeNewPoint.setEntry(i4, this.trustRegionCenterOffset.getEntry(i4));
/* 1502 */         } else if (glagValue > 0.0D) {
/* 1503 */           this.alternativeNewPoint.setEntry(i4, this.lowerDifference.getEntry(i4));
/*      */         } else {
/* 1505 */           this.alternativeNewPoint.setEntry(i4, this.upperDifference.getEntry(i4));
/*      */         } 
/* 1507 */         gw += glagValue * work1.getEntry(i4);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1515 */       double curv = 0.0D;
/* 1516 */       for (int i5 = 0; i5 < npt; i5++) {
/* 1517 */         double tmp = 0.0D;
/* 1518 */         for (int i6 = 0; i6 < n; i6++) {
/* 1519 */           tmp += this.interpolationPoints.getEntry(i5, i6) * work1.getEntry(i6);
/*      */         }
/* 1521 */         curv += hcol.getEntry(i5) * tmp * tmp;
/*      */       } 
/* 1523 */       if (iflag == 1) {
/* 1524 */         curv = -curv;
/*      */       }
/* 1526 */       if (curv > -gw && curv < -gw * (1.0D + FastMath.sqrt(2.0D))) {
/*      */         
/* 1528 */         double scale = -gw / curv;
/* 1529 */         for (int i6 = 0; i6 < n; i6++) {
/* 1530 */           double tmp = this.trustRegionCenterOffset.getEntry(i6) + scale * work1.getEntry(i6);
/* 1531 */           this.alternativeNewPoint.setEntry(i6, FastMath.max(this.lowerDifference.getEntry(i6), FastMath.min(this.upperDifference.getEntry(i6), tmp)));
/*      */         } 
/*      */ 
/*      */         
/* 1535 */         double d1 = 0.5D * gw * scale;
/* 1536 */         cauchy = d1 * d1;
/*      */       } else {
/*      */         
/* 1539 */         double d1 = gw + 0.5D * curv;
/* 1540 */         cauchy = d1 * d1;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1547 */       if (iflag == 0) {
/* 1548 */         for (int i6 = 0; i6 < n; i6++) {
/* 1549 */           glag.setEntry(i6, -glag.getEntry(i6));
/* 1550 */           work2.setEntry(i6, this.alternativeNewPoint.getEntry(i6));
/*      */         } 
/* 1552 */         csave = cauchy;
/* 1553 */         iflag = 1;
/*      */         continue;
/*      */       } 
/*      */       break;
/*      */     } 
/* 1558 */     if (csave > cauchy) {
/* 1559 */       for (int i3 = 0; i3 < n; i3++) {
/* 1560 */         this.alternativeNewPoint.setEntry(i3, work2.getEntry(i3));
/*      */       }
/* 1562 */       cauchy = csave;
/*      */     } 
/*      */     
/* 1565 */     return new double[] { alpha, cauchy };
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
/* 1592 */     printMethod();
/*      */     
/* 1594 */     int n = this.currentBest.getDimension();
/* 1595 */     int npt = this.numberOfInterpolationPoints;
/* 1596 */     int ndim = this.bMatrix.getRowDimension();
/*      */     
/* 1598 */     double rhosq = this.initialTrustRegionRadius * this.initialTrustRegionRadius;
/* 1599 */     double recip = 1.0D / rhosq;
/* 1600 */     int np = n + 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1605 */     for (int j = 0; j < n; j++) {
/* 1606 */       this.originShift.setEntry(j, this.currentBest.getEntry(j));
/* 1607 */       for (int i1 = 0; i1 < npt; i1++) {
/* 1608 */         this.interpolationPoints.setEntry(i1, j, 0.0D);
/*      */       }
/* 1610 */       for (int m = 0; m < ndim; m++) {
/* 1611 */         this.bMatrix.setEntry(m, j, 0.0D);
/*      */       }
/*      */     } 
/* 1614 */     for (int i = 0, max = n * np / 2; i < max; i++) {
/* 1615 */       this.modelSecondDerivativesValues.setEntry(i, 0.0D);
/*      */     }
/* 1617 */     for (int k = 0; k < npt; k++) {
/* 1618 */       this.modelSecondDerivativesParameters.setEntry(k, 0.0D);
/* 1619 */       for (int m = 0, i1 = npt - np; m < i1; m++) {
/* 1620 */         this.zMatrix.setEntry(k, m, 0.0D);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1628 */     int ipt = 0;
/* 1629 */     int jpt = 0;
/* 1630 */     double fbeg = Double.NaN;
/*      */     do {
/* 1632 */       int nfm = getEvaluations();
/* 1633 */       int nfx = nfm - n;
/* 1634 */       int nfmm = nfm - 1;
/* 1635 */       int nfxm = nfx - 1;
/* 1636 */       double stepa = 0.0D;
/* 1637 */       double stepb = 0.0D;
/* 1638 */       if (nfm <= 2 * n) {
/* 1639 */         if (nfm >= 1 && nfm <= n) {
/*      */           
/* 1641 */           stepa = this.initialTrustRegionRadius;
/* 1642 */           if (this.upperDifference.getEntry(nfmm) == 0.0D) {
/* 1643 */             stepa = -stepa;
/*      */           }
/*      */           
/* 1646 */           this.interpolationPoints.setEntry(nfm, nfmm, stepa);
/* 1647 */         } else if (nfm > n) {
/* 1648 */           stepa = this.interpolationPoints.getEntry(nfx, nfxm);
/* 1649 */           stepb = -this.initialTrustRegionRadius;
/* 1650 */           if (this.lowerDifference.getEntry(nfxm) == 0.0D) {
/* 1651 */             stepb = FastMath.min(2.0D * this.initialTrustRegionRadius, this.upperDifference.getEntry(nfxm));
/*      */           }
/*      */           
/* 1654 */           if (this.upperDifference.getEntry(nfxm) == 0.0D) {
/* 1655 */             stepb = FastMath.max(-2.0D * this.initialTrustRegionRadius, this.lowerDifference.getEntry(nfxm));
/*      */           }
/*      */           
/* 1658 */           this.interpolationPoints.setEntry(nfm, nfxm, stepb);
/*      */         } 
/*      */       } else {
/* 1661 */         int tmp1 = (nfm - np) / n;
/* 1662 */         jpt = nfm - tmp1 * n - n;
/* 1663 */         ipt = jpt + tmp1;
/* 1664 */         if (ipt > n) {
/* 1665 */           int tmp2 = jpt;
/* 1666 */           jpt = ipt - n;
/* 1667 */           ipt = tmp2;
/*      */         } 
/*      */         
/* 1670 */         int iptMinus1 = ipt - 1;
/* 1671 */         int jptMinus1 = jpt - 1;
/* 1672 */         this.interpolationPoints.setEntry(nfm, iptMinus1, this.interpolationPoints.getEntry(ipt, iptMinus1));
/* 1673 */         this.interpolationPoints.setEntry(nfm, jptMinus1, this.interpolationPoints.getEntry(jpt, jptMinus1));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1679 */       for (int m = 0; m < n; m++) {
/* 1680 */         this.currentBest.setEntry(m, FastMath.min(FastMath.max(lowerBound[m], this.originShift.getEntry(m) + this.interpolationPoints.getEntry(nfm, m)), upperBound[m]));
/*      */ 
/*      */         
/* 1683 */         if (this.interpolationPoints.getEntry(nfm, m) == this.lowerDifference.getEntry(m)) {
/* 1684 */           this.currentBest.setEntry(m, lowerBound[m]);
/*      */         }
/* 1686 */         if (this.interpolationPoints.getEntry(nfm, m) == this.upperDifference.getEntry(m)) {
/* 1687 */           this.currentBest.setEntry(m, upperBound[m]);
/*      */         }
/*      */       } 
/*      */       
/* 1691 */       double objectiveValue = computeObjectiveValue(this.currentBest.toArray());
/* 1692 */       double f = this.isMinimize ? objectiveValue : -objectiveValue;
/* 1693 */       int numEval = getEvaluations();
/* 1694 */       this.fAtInterpolationPoints.setEntry(nfm, f);
/*      */       
/* 1696 */       if (numEval == 1) {
/* 1697 */         fbeg = f;
/* 1698 */         this.trustRegionCenterInterpolationPointIndex = 0;
/* 1699 */       } else if (f < this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex)) {
/* 1700 */         this.trustRegionCenterInterpolationPointIndex = nfm;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1709 */       if (numEval <= 2 * n + 1) {
/* 1710 */         if (numEval >= 2 && numEval <= n + 1) {
/*      */           
/* 1712 */           this.gradientAtTrustRegionCenter.setEntry(nfmm, (f - fbeg) / stepa);
/* 1713 */           if (npt < numEval + n) {
/* 1714 */             double oneOverStepA = 1.0D / stepa;
/* 1715 */             this.bMatrix.setEntry(0, nfmm, -oneOverStepA);
/* 1716 */             this.bMatrix.setEntry(nfm, nfmm, oneOverStepA);
/* 1717 */             this.bMatrix.setEntry(npt + nfmm, nfmm, -0.5D * rhosq);
/*      */           }
/*      */         
/* 1720 */         } else if (numEval >= n + 2) {
/* 1721 */           int ih = nfx * (nfx + 1) / 2 - 1;
/* 1722 */           double tmp = (f - fbeg) / stepb;
/* 1723 */           double diff = stepb - stepa;
/* 1724 */           this.modelSecondDerivativesValues.setEntry(ih, 2.0D * (tmp - this.gradientAtTrustRegionCenter.getEntry(nfxm)) / diff);
/* 1725 */           this.gradientAtTrustRegionCenter.setEntry(nfxm, (this.gradientAtTrustRegionCenter.getEntry(nfxm) * stepb - tmp * stepa) / diff);
/* 1726 */           if (stepa * stepb < 0.0D && f < this.fAtInterpolationPoints.getEntry(nfm - n)) {
/* 1727 */             this.fAtInterpolationPoints.setEntry(nfm, this.fAtInterpolationPoints.getEntry(nfm - n));
/* 1728 */             this.fAtInterpolationPoints.setEntry(nfm - n, f);
/* 1729 */             if (this.trustRegionCenterInterpolationPointIndex == nfm) {
/* 1730 */               this.trustRegionCenterInterpolationPointIndex = nfm - n;
/*      */             }
/* 1732 */             this.interpolationPoints.setEntry(nfm - n, nfxm, stepb);
/* 1733 */             this.interpolationPoints.setEntry(nfm, nfxm, stepa);
/*      */           } 
/* 1735 */           this.bMatrix.setEntry(0, nfxm, -(stepa + stepb) / stepa * stepb);
/* 1736 */           this.bMatrix.setEntry(nfm, nfxm, -0.5D / this.interpolationPoints.getEntry(nfm - n, nfxm));
/* 1737 */           this.bMatrix.setEntry(nfm - n, nfxm, -this.bMatrix.getEntry(0, nfxm) - this.bMatrix.getEntry(nfm, nfxm));
/*      */           
/* 1739 */           this.zMatrix.setEntry(0, nfxm, FastMath.sqrt(2.0D) / stepa * stepb);
/* 1740 */           this.zMatrix.setEntry(nfm, nfxm, FastMath.sqrt(0.5D) / rhosq);
/*      */           
/* 1742 */           this.zMatrix.setEntry(nfm - n, nfxm, -this.zMatrix.getEntry(0, nfxm) - this.zMatrix.getEntry(nfm, nfxm));
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1750 */         this.zMatrix.setEntry(0, nfxm, recip);
/* 1751 */         this.zMatrix.setEntry(nfm, nfxm, recip);
/* 1752 */         this.zMatrix.setEntry(ipt, nfxm, -recip);
/* 1753 */         this.zMatrix.setEntry(jpt, nfxm, -recip);
/*      */         
/* 1755 */         int ih = ipt * (ipt - 1) / 2 + jpt - 1;
/* 1756 */         double tmp = this.interpolationPoints.getEntry(nfm, ipt - 1) * this.interpolationPoints.getEntry(nfm, jpt - 1);
/* 1757 */         this.modelSecondDerivativesValues.setEntry(ih, (fbeg - this.fAtInterpolationPoints.getEntry(ipt) - this.fAtInterpolationPoints.getEntry(jpt) + f) / tmp);
/*      */       }
/*      */     
/* 1760 */     } while (getEvaluations() < npt);
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
/* 1818 */     printMethod();
/*      */     
/* 1820 */     int n = this.currentBest.getDimension();
/* 1821 */     int npt = this.numberOfInterpolationPoints;
/*      */     
/* 1823 */     double dsq = Double.NaN;
/* 1824 */     double crvmin = Double.NaN;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1829 */     double beta = 0.0D;
/* 1830 */     int iact = -1;
/* 1831 */     int nact = 0;
/* 1832 */     double angt = 0.0D;
/*      */     
/* 1834 */     double temp = 0.0D, xsav = 0.0D, xsum = 0.0D, angbd = 0.0D, dredg = 0.0D, sredg = 0.0D;
/*      */     
/* 1836 */     double resid = 0.0D, delsq = 0.0D, ggsav = 0.0D, tempa = 0.0D, tempb = 0.0D;
/* 1837 */     double redmax = 0.0D, dredsq = 0.0D, redsav = 0.0D, gredsq = 0.0D, rednew = 0.0D;
/* 1838 */     int itcsav = 0;
/* 1839 */     double rdprev = 0.0D, rdnext = 0.0D, stplen = 0.0D, stepsq = 0.0D;
/* 1840 */     int itermax = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1853 */     int iterc = 0;
/* 1854 */     nact = 0;
/* 1855 */     for (int i = 0; i < n; i++) {
/* 1856 */       xbdi.setEntry(i, 0.0D);
/* 1857 */       if (this.trustRegionCenterOffset.getEntry(i) <= this.lowerDifference.getEntry(i)) {
/* 1858 */         if (this.gradientAtTrustRegionCenter.getEntry(i) >= 0.0D) {
/* 1859 */           xbdi.setEntry(i, -1.0D);
/*      */         }
/* 1861 */       } else if (this.trustRegionCenterOffset.getEntry(i) >= this.upperDifference.getEntry(i) && this.gradientAtTrustRegionCenter.getEntry(i) <= 0.0D) {
/*      */         
/* 1863 */         xbdi.setEntry(i, 1.0D);
/*      */       } 
/* 1865 */       if (xbdi.getEntry(i) != 0.0D) {
/* 1866 */         nact++;
/*      */       }
/* 1868 */       this.trialStepPoint.setEntry(i, 0.0D);
/* 1869 */       gnew.setEntry(i, this.gradientAtTrustRegionCenter.getEntry(i));
/*      */     } 
/* 1871 */     delsq = delta * delta;
/* 1872 */     double qred = 0.0D;
/* 1873 */     crvmin = -1.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1881 */     int state = 20; while (true) {
/*      */       double ds; int iu; double dhd; double dhs; double cth; double shs; double sth; double sdec; double blen; int isav; int m; int ih; int j; RealVector tmp; int k; int i1;
/* 1883 */       switch (state) {
/*      */         case 20:
/* 1885 */           printState(20);
/* 1886 */           beta = 0.0D;
/*      */         
/*      */         case 30:
/* 1889 */           printState(30);
/* 1890 */           stepsq = 0.0D;
/* 1891 */           for (m = 0; m < n; m++) {
/* 1892 */             if (xbdi.getEntry(m) != 0.0D) {
/* 1893 */               s.setEntry(m, 0.0D);
/* 1894 */             } else if (beta == 0.0D) {
/* 1895 */               s.setEntry(m, -gnew.getEntry(m));
/*      */             } else {
/* 1897 */               s.setEntry(m, beta * s.getEntry(m) - gnew.getEntry(m));
/*      */             } 
/*      */             
/* 1900 */             double d1 = s.getEntry(m);
/* 1901 */             stepsq += d1 * d1;
/*      */           } 
/* 1903 */           if (stepsq == 0.0D) {
/* 1904 */             state = 190; continue;
/*      */           } 
/* 1906 */           if (beta == 0.0D) {
/* 1907 */             gredsq = stepsq;
/* 1908 */             itermax = iterc + n - nact;
/*      */           } 
/* 1910 */           if (gredsq * delsq <= qred * 1.0E-4D * qred) {
/* 1911 */             state = 190;
/*      */ 
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/* 1919 */           state = 210;
/*      */           continue;
/*      */         case 50:
/* 1922 */           printState(50);
/* 1923 */           resid = delsq;
/* 1924 */           ds = 0.0D;
/* 1925 */           shs = 0.0D;
/* 1926 */           for (m = 0; m < n; m++) {
/* 1927 */             if (xbdi.getEntry(m) == 0.0D) {
/*      */               
/* 1929 */               double d1 = this.trialStepPoint.getEntry(m);
/* 1930 */               resid -= d1 * d1;
/* 1931 */               ds += s.getEntry(m) * this.trialStepPoint.getEntry(m);
/* 1932 */               shs += s.getEntry(m) * hs.getEntry(m);
/*      */             } 
/*      */           } 
/* 1935 */           if (resid <= 0.0D) {
/* 1936 */             state = 90; continue;
/*      */           } 
/* 1938 */           temp = FastMath.sqrt(stepsq * resid + ds * ds);
/* 1939 */           if (ds < 0.0D) {
/* 1940 */             blen = (temp - ds) / stepsq;
/*      */           } else {
/* 1942 */             blen = resid / (temp + ds);
/*      */           } 
/* 1944 */           stplen = blen;
/* 1945 */           if (shs > 0.0D)
/*      */           {
/* 1947 */             stplen = FastMath.min(blen, gredsq / shs);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1953 */           iact = -1;
/* 1954 */           for (m = 0; m < n; m++) {
/* 1955 */             if (s.getEntry(m) != 0.0D) {
/* 1956 */               xsum = this.trustRegionCenterOffset.getEntry(m) + this.trialStepPoint.getEntry(m);
/* 1957 */               if (s.getEntry(m) > 0.0D) {
/* 1958 */                 temp = (this.upperDifference.getEntry(m) - xsum) / s.getEntry(m);
/*      */               } else {
/* 1960 */                 temp = (this.lowerDifference.getEntry(m) - xsum) / s.getEntry(m);
/*      */               } 
/* 1962 */               if (temp < stplen) {
/* 1963 */                 stplen = temp;
/* 1964 */                 iact = m;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1971 */           sdec = 0.0D;
/* 1972 */           if (stplen > 0.0D) {
/* 1973 */             iterc++;
/* 1974 */             temp = shs / stepsq;
/* 1975 */             if (iact == -1 && temp > 0.0D) {
/* 1976 */               crvmin = FastMath.min(crvmin, temp);
/* 1977 */               if (crvmin == -1.0D) {
/* 1978 */                 crvmin = temp;
/*      */               }
/*      */             } 
/* 1981 */             ggsav = gredsq;
/* 1982 */             gredsq = 0.0D;
/* 1983 */             for (m = 0; m < n; m++) {
/* 1984 */               gnew.setEntry(m, gnew.getEntry(m) + stplen * hs.getEntry(m));
/* 1985 */               if (xbdi.getEntry(m) == 0.0D) {
/*      */                 
/* 1987 */                 double d = gnew.getEntry(m);
/* 1988 */                 gredsq += d * d;
/*      */               } 
/* 1990 */               this.trialStepPoint.setEntry(m, this.trialStepPoint.getEntry(m) + stplen * s.getEntry(m));
/*      */             } 
/*      */             
/* 1993 */             double d1 = stplen * (ggsav - 0.5D * stplen * shs);
/* 1994 */             sdec = FastMath.max(d1, 0.0D);
/* 1995 */             qred += sdec;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2000 */           if (iact >= 0) {
/* 2001 */             nact++;
/* 2002 */             xbdi.setEntry(iact, 1.0D);
/* 2003 */             if (s.getEntry(iact) < 0.0D) {
/* 2004 */               xbdi.setEntry(iact, -1.0D);
/*      */             }
/*      */             
/* 2007 */             double d1 = this.trialStepPoint.getEntry(iact);
/* 2008 */             delsq -= d1 * d1;
/* 2009 */             if (delsq <= 0.0D) {
/* 2010 */               state = 190; continue;
/*      */             } 
/* 2012 */             state = 20;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 2018 */           if (stplen < blen) {
/* 2019 */             if (iterc == itermax) {
/* 2020 */               state = 190; continue;
/*      */             } 
/* 2022 */             if (sdec <= qred * 0.01D) {
/* 2023 */               state = 190; continue;
/*      */             } 
/* 2025 */             beta = gredsq / ggsav;
/* 2026 */             state = 30;
/*      */             continue;
/*      */           } 
/*      */         case 90:
/* 2030 */           printState(90);
/* 2031 */           crvmin = 0.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 100:
/* 2039 */           printState(100);
/* 2040 */           if (nact >= n - 1) {
/* 2041 */             state = 190; continue;
/*      */           } 
/* 2043 */           dredsq = 0.0D;
/* 2044 */           dredg = 0.0D;
/* 2045 */           gredsq = 0.0D;
/* 2046 */           for (m = 0; m < n; m++) {
/* 2047 */             if (xbdi.getEntry(m) == 0.0D) {
/*      */               
/* 2049 */               double d1 = this.trialStepPoint.getEntry(m);
/* 2050 */               dredsq += d1 * d1;
/* 2051 */               dredg += this.trialStepPoint.getEntry(m) * gnew.getEntry(m);
/*      */               
/* 2053 */               d1 = gnew.getEntry(m);
/* 2054 */               gredsq += d1 * d1;
/* 2055 */               s.setEntry(m, this.trialStepPoint.getEntry(m));
/*      */             } else {
/* 2057 */               s.setEntry(m, 0.0D);
/*      */             } 
/*      */           } 
/* 2060 */           itcsav = iterc;
/* 2061 */           state = 210;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 120:
/* 2066 */           printState(120);
/* 2067 */           iterc++;
/* 2068 */           temp = gredsq * dredsq - dredg * dredg;
/* 2069 */           if (temp <= qred * 1.0E-4D * qred) {
/* 2070 */             state = 190; continue;
/*      */           } 
/* 2072 */           temp = FastMath.sqrt(temp);
/* 2073 */           for (m = 0; m < n; m++) {
/* 2074 */             if (xbdi.getEntry(m) == 0.0D) {
/* 2075 */               s.setEntry(m, (dredg * this.trialStepPoint.getEntry(m) - dredsq * gnew.getEntry(m)) / temp);
/*      */             } else {
/* 2077 */               s.setEntry(m, 0.0D);
/*      */             } 
/*      */           } 
/* 2080 */           sredg = -temp;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2087 */           angbd = 1.0D;
/* 2088 */           iact = -1;
/* 2089 */           for (m = 0; m < n; m++) {
/* 2090 */             if (xbdi.getEntry(m) == 0.0D) {
/* 2091 */               tempa = this.trustRegionCenterOffset.getEntry(m) + this.trialStepPoint.getEntry(m) - this.lowerDifference.getEntry(m);
/* 2092 */               tempb = this.upperDifference.getEntry(m) - this.trustRegionCenterOffset.getEntry(m) - this.trialStepPoint.getEntry(m);
/* 2093 */               if (tempa <= 0.0D) {
/* 2094 */                 nact++;
/* 2095 */                 xbdi.setEntry(m, -1.0D);
/* 2096 */                 state = 100; break;
/* 2097 */               }  if (tempb <= 0.0D) {
/* 2098 */                 nact++;
/* 2099 */                 xbdi.setEntry(m, 1.0D);
/* 2100 */                 state = 100;
/*      */                 break;
/*      */               } 
/* 2103 */               double d1 = this.trialStepPoint.getEntry(m);
/*      */               
/* 2105 */               double d2 = s.getEntry(m);
/* 2106 */               double ssq = d1 * d1 + d2 * d2;
/*      */               
/* 2108 */               d1 = this.trustRegionCenterOffset.getEntry(m) - this.lowerDifference.getEntry(m);
/* 2109 */               temp = ssq - d1 * d1;
/* 2110 */               if (temp > 0.0D) {
/* 2111 */                 temp = FastMath.sqrt(temp) - s.getEntry(m);
/* 2112 */                 if (angbd * temp > tempa) {
/* 2113 */                   angbd = tempa / temp;
/* 2114 */                   iact = m;
/* 2115 */                   xsav = -1.0D;
/*      */                 } 
/*      */               } 
/*      */               
/* 2119 */               d1 = this.upperDifference.getEntry(m) - this.trustRegionCenterOffset.getEntry(m);
/* 2120 */               temp = ssq - d1 * d1;
/* 2121 */               if (temp > 0.0D) {
/* 2122 */                 temp = FastMath.sqrt(temp) + s.getEntry(m);
/* 2123 */                 if (angbd * temp > tempb) {
/* 2124 */                   angbd = tempb / temp;
/* 2125 */                   iact = m;
/* 2126 */                   xsav = 1.0D;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2134 */           state = 210;
/*      */           continue;
/*      */         case 150:
/* 2137 */           printState(150);
/* 2138 */           shs = 0.0D;
/* 2139 */           dhs = 0.0D;
/* 2140 */           dhd = 0.0D;
/* 2141 */           for (m = 0; m < n; m++) {
/* 2142 */             if (xbdi.getEntry(m) == 0.0D) {
/* 2143 */               shs += s.getEntry(m) * hs.getEntry(m);
/* 2144 */               dhs += this.trialStepPoint.getEntry(m) * hs.getEntry(m);
/* 2145 */               dhd += this.trialStepPoint.getEntry(m) * hred.getEntry(m);
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2153 */           redmax = 0.0D;
/* 2154 */           isav = -1;
/* 2155 */           redsav = 0.0D;
/* 2156 */           iu = (int)(angbd * 17.0D + 3.1D);
/* 2157 */           for (m = 0; m < iu; m++) {
/* 2158 */             angt = angbd * m / iu;
/* 2159 */             double d = (angt + angt) / (1.0D + angt * angt);
/* 2160 */             temp = shs + angt * (angt * dhd - dhs - dhs);
/* 2161 */             rednew = d * (angt * dredg - sredg - 0.5D * d * temp);
/* 2162 */             if (rednew > redmax) {
/* 2163 */               redmax = rednew;
/* 2164 */               isav = m;
/* 2165 */               rdprev = redsav;
/* 2166 */             } else if (m == isav + 1) {
/* 2167 */               rdnext = rednew;
/*      */             } 
/* 2169 */             redsav = rednew;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2175 */           if (isav < 0) {
/* 2176 */             state = 190; continue;
/*      */           } 
/* 2178 */           if (isav < iu) {
/* 2179 */             temp = (rdnext - rdprev) / (redmax + redmax - rdprev - rdnext);
/* 2180 */             angt = angbd * (isav + 0.5D * temp) / iu;
/*      */           } 
/* 2182 */           cth = (1.0D - angt * angt) / (1.0D + angt * angt);
/* 2183 */           sth = (angt + angt) / (1.0D + angt * angt);
/* 2184 */           temp = shs + angt * (angt * dhd - dhs - dhs);
/* 2185 */           sdec = sth * (angt * dredg - sredg - 0.5D * sth * temp);
/* 2186 */           if (sdec <= 0.0D) {
/* 2187 */             state = 190;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/* 2194 */           dredg = 0.0D;
/* 2195 */           gredsq = 0.0D;
/* 2196 */           for (m = 0; m < n; m++) {
/* 2197 */             gnew.setEntry(m, gnew.getEntry(m) + (cth - 1.0D) * hred.getEntry(m) + sth * hs.getEntry(m));
/* 2198 */             if (xbdi.getEntry(m) == 0.0D) {
/* 2199 */               this.trialStepPoint.setEntry(m, cth * this.trialStepPoint.getEntry(m) + sth * s.getEntry(m));
/* 2200 */               dredg += this.trialStepPoint.getEntry(m) * gnew.getEntry(m);
/*      */               
/* 2202 */               double d1 = gnew.getEntry(m);
/* 2203 */               gredsq += d1 * d1;
/*      */             } 
/* 2205 */             hred.setEntry(m, cth * hred.getEntry(m) + sth * hs.getEntry(m));
/*      */           } 
/* 2207 */           qred += sdec;
/* 2208 */           if (iact >= 0 && isav == iu) {
/* 2209 */             nact++;
/* 2210 */             xbdi.setEntry(iact, xsav);
/* 2211 */             state = 100;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 2217 */           if (sdec > qred * 0.01D) {
/* 2218 */             state = 120;
/*      */             continue;
/*      */           } 
/*      */         case 190:
/* 2222 */           printState(190);
/* 2223 */           dsq = 0.0D;
/* 2224 */           for (m = 0; m < n; m++) {
/*      */ 
/*      */             
/* 2227 */             double min = FastMath.min(this.trustRegionCenterOffset.getEntry(m) + this.trialStepPoint.getEntry(m), this.upperDifference.getEntry(m));
/*      */             
/* 2229 */             this.newPoint.setEntry(m, FastMath.max(min, this.lowerDifference.getEntry(m)));
/* 2230 */             if (xbdi.getEntry(m) == -1.0D) {
/* 2231 */               this.newPoint.setEntry(m, this.lowerDifference.getEntry(m));
/*      */             }
/* 2233 */             if (xbdi.getEntry(m) == 1.0D) {
/* 2234 */               this.newPoint.setEntry(m, this.upperDifference.getEntry(m));
/*      */             }
/* 2236 */             this.trialStepPoint.setEntry(m, this.newPoint.getEntry(m) - this.trustRegionCenterOffset.getEntry(m));
/*      */             
/* 2238 */             double d1 = this.trialStepPoint.getEntry(m);
/* 2239 */             dsq += d1 * d1;
/*      */           } 
/* 2241 */           return new double[] { dsq, crvmin };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 210:
/* 2248 */           printState(210);
/* 2249 */           ih = 0;
/* 2250 */           for (j = 0; j < n; j++) {
/* 2251 */             hs.setEntry(j, 0.0D);
/* 2252 */             for (int i2 = 0; i2 <= j; i2++) {
/* 2253 */               if (i2 < j) {
/* 2254 */                 hs.setEntry(j, hs.getEntry(j) + this.modelSecondDerivativesValues.getEntry(ih) * s.getEntry(i2));
/*      */               }
/* 2256 */               hs.setEntry(i2, hs.getEntry(i2) + this.modelSecondDerivativesValues.getEntry(ih) * s.getEntry(j));
/* 2257 */               ih++;
/*      */             } 
/*      */           } 
/* 2260 */           tmp = this.interpolationPoints.operate((RealVector)s).ebeMultiply((RealVector)this.modelSecondDerivativesParameters);
/* 2261 */           for (k = 0; k < npt; k++) {
/* 2262 */             if (this.modelSecondDerivativesParameters.getEntry(k) != 0.0D) {
/* 2263 */               for (int i2 = 0; i2 < n; i2++) {
/* 2264 */                 hs.setEntry(i2, hs.getEntry(i2) + tmp.getEntry(k) * this.interpolationPoints.getEntry(k, i2));
/*      */               }
/*      */             }
/*      */           } 
/* 2268 */           if (crvmin != 0.0D) {
/* 2269 */             state = 50; continue;
/*      */           } 
/* 2271 */           if (iterc > itcsav) {
/* 2272 */             state = 150; continue;
/*      */           } 
/* 2274 */           for (i1 = 0; i1 < n; i1++) {
/* 2275 */             hred.setEntry(i1, hs.getEntry(i1));
/*      */           }
/* 2277 */           state = 120; continue;
/*      */       }  break;
/*      */     } 
/* 2280 */     throw new MathIllegalStateException(LocalizedFormats.SIMPLE_MESSAGE, new Object[] { "trsbox" });
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
/* 2305 */     printMethod();
/*      */     
/* 2307 */     int n = this.currentBest.getDimension();
/* 2308 */     int npt = this.numberOfInterpolationPoints;
/* 2309 */     int nptm = npt - n - 1;
/*      */ 
/*      */     
/* 2312 */     ArrayRealVector work = new ArrayRealVector(npt + n);
/*      */     
/* 2314 */     double ztest = 0.0D;
/* 2315 */     for (int k = 0; k < npt; k++) {
/* 2316 */       for (int i2 = 0; i2 < nptm; i2++)
/*      */       {
/* 2318 */         ztest = FastMath.max(ztest, FastMath.abs(this.zMatrix.getEntry(k, i2)));
/*      */       }
/*      */     } 
/* 2321 */     ztest *= 1.0E-20D;
/*      */ 
/*      */ 
/*      */     
/* 2325 */     for (int j = 1; j < nptm; j++) {
/* 2326 */       double d = this.zMatrix.getEntry(knew, j);
/* 2327 */       if (FastMath.abs(d) > ztest) {
/*      */         
/* 2329 */         double d7 = this.zMatrix.getEntry(knew, 0);
/*      */         
/* 2331 */         double d3 = this.zMatrix.getEntry(knew, j);
/* 2332 */         double d4 = FastMath.sqrt(d7 * d7 + d3 * d3);
/* 2333 */         double d5 = this.zMatrix.getEntry(knew, 0) / d4;
/* 2334 */         double d6 = this.zMatrix.getEntry(knew, j) / d4;
/* 2335 */         for (int i2 = 0; i2 < npt; i2++) {
/* 2336 */           double d8 = d5 * this.zMatrix.getEntry(i2, 0) + d6 * this.zMatrix.getEntry(i2, j);
/* 2337 */           this.zMatrix.setEntry(i2, j, d5 * this.zMatrix.getEntry(i2, j) - d6 * this.zMatrix.getEntry(i2, 0));
/* 2338 */           this.zMatrix.setEntry(i2, 0, d8);
/*      */         } 
/*      */       } 
/* 2341 */       this.zMatrix.setEntry(knew, j, 0.0D);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2347 */     for (int i = 0; i < npt; i++) {
/* 2348 */       work.setEntry(i, this.zMatrix.getEntry(knew, 0) * this.zMatrix.getEntry(i, 0));
/*      */     }
/* 2350 */     double alpha = work.getEntry(knew);
/* 2351 */     double tau = this.lagrangeValuesAtNewPoint.getEntry(knew);
/* 2352 */     this.lagrangeValuesAtNewPoint.setEntry(knew, this.lagrangeValuesAtNewPoint.getEntry(knew) - 1.0D);
/*      */ 
/*      */ 
/*      */     
/* 2356 */     double sqrtDenom = FastMath.sqrt(denom);
/* 2357 */     double d1 = tau / sqrtDenom;
/* 2358 */     double d2 = this.zMatrix.getEntry(knew, 0) / sqrtDenom;
/* 2359 */     for (int i1 = 0; i1 < npt; i1++) {
/* 2360 */       this.zMatrix.setEntry(i1, 0, d1 * this.zMatrix.getEntry(i1, 0) - d2 * this.lagrangeValuesAtNewPoint.getEntry(i1));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2366 */     for (int m = 0; m < n; m++) {
/* 2367 */       int jp = npt + m;
/* 2368 */       work.setEntry(jp, this.bMatrix.getEntry(knew, m));
/* 2369 */       double d3 = (alpha * this.lagrangeValuesAtNewPoint.getEntry(jp) - tau * work.getEntry(jp)) / denom;
/* 2370 */       double d4 = (-beta * work.getEntry(jp) - tau * this.lagrangeValuesAtNewPoint.getEntry(jp)) / denom;
/* 2371 */       for (int i2 = 0; i2 <= jp; i2++) {
/* 2372 */         this.bMatrix.setEntry(i2, m, this.bMatrix.getEntry(i2, m) + d3 * this.lagrangeValuesAtNewPoint.getEntry(i2) + d4 * work.getEntry(i2));
/*      */         
/* 2374 */         if (i2 >= npt) {
/* 2375 */           this.bMatrix.setEntry(jp, i2 - npt, this.bMatrix.getEntry(i2, m));
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
/* 2389 */     printMethod();
/*      */     
/* 2391 */     double[] init = getStartPoint();
/* 2392 */     int dimension = init.length;
/*      */ 
/*      */     
/* 2395 */     if (dimension < 2) {
/* 2396 */       throw new NumberIsTooSmallException(Integer.valueOf(dimension), Integer.valueOf(2), true);
/*      */     }
/*      */     
/* 2399 */     int[] nPointsInterval = { dimension + 2, (dimension + 2) * (dimension + 1) / 2 };
/* 2400 */     if (this.numberOfInterpolationPoints < nPointsInterval[0] || this.numberOfInterpolationPoints > nPointsInterval[1])
/*      */     {
/* 2402 */       throw new OutOfRangeException(LocalizedFormats.NUMBER_OF_INTERPOLATION_POINTS, Integer.valueOf(this.numberOfInterpolationPoints), Integer.valueOf(nPointsInterval[0]), Integer.valueOf(nPointsInterval[1]));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2409 */     this.boundDifference = new double[dimension];
/*      */     
/* 2411 */     double requiredMinDiff = 2.0D * this.initialTrustRegionRadius;
/* 2412 */     double minDiff = Double.POSITIVE_INFINITY;
/* 2413 */     for (int i = 0; i < dimension; i++) {
/* 2414 */       this.boundDifference[i] = upperBound[i] - lowerBound[i];
/* 2415 */       minDiff = FastMath.min(minDiff, this.boundDifference[i]);
/*      */     } 
/* 2417 */     if (minDiff < requiredMinDiff) {
/* 2418 */       this.initialTrustRegionRadius = minDiff / 3.0D;
/*      */     }
/*      */ 
/*      */     
/* 2422 */     this.bMatrix = new Array2DRowRealMatrix(dimension + this.numberOfInterpolationPoints, dimension);
/*      */     
/* 2424 */     this.zMatrix = new Array2DRowRealMatrix(this.numberOfInterpolationPoints, this.numberOfInterpolationPoints - dimension - 1);
/*      */     
/* 2426 */     this.interpolationPoints = new Array2DRowRealMatrix(this.numberOfInterpolationPoints, dimension);
/*      */     
/* 2428 */     this.originShift = new ArrayRealVector(dimension);
/* 2429 */     this.fAtInterpolationPoints = new ArrayRealVector(this.numberOfInterpolationPoints);
/* 2430 */     this.trustRegionCenterOffset = new ArrayRealVector(dimension);
/* 2431 */     this.gradientAtTrustRegionCenter = new ArrayRealVector(dimension);
/* 2432 */     this.lowerDifference = new ArrayRealVector(dimension);
/* 2433 */     this.upperDifference = new ArrayRealVector(dimension);
/* 2434 */     this.modelSecondDerivativesParameters = new ArrayRealVector(this.numberOfInterpolationPoints);
/* 2435 */     this.newPoint = new ArrayRealVector(dimension);
/* 2436 */     this.alternativeNewPoint = new ArrayRealVector(dimension);
/* 2437 */     this.trialStepPoint = new ArrayRealVector(dimension);
/* 2438 */     this.lagrangeValuesAtNewPoint = new ArrayRealVector(dimension + this.numberOfInterpolationPoints);
/* 2439 */     this.modelSecondDerivativesValues = new ArrayRealVector(dimension * (dimension + 1) / 2);
/*      */   }
/*      */ 
/*      */   
/*      */   private static String caller(int n) {
/* 2444 */     Throwable t = new Throwable();
/* 2445 */     StackTraceElement[] elements = t.getStackTrace();
/* 2446 */     StackTraceElement e = elements[n];
/* 2447 */     return e.getMethodName() + " (at line " + e.getLineNumber() + ")";
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
/* 2471 */       super("If this exception is thrown, just remove it from the code " + BOBYQAOptimizer.caller(3));
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/noderiv/BOBYQAOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */