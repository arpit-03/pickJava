/*      */ package org.apache.commons.math3.optimization.direct;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*      */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*      */ import org.apache.commons.math3.exception.NotPositiveException;
/*      */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*      */ import org.apache.commons.math3.exception.OutOfRangeException;
/*      */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*      */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*      */ import org.apache.commons.math3.linear.EigenDecomposition;
/*      */ import org.apache.commons.math3.linear.MatrixUtils;
/*      */ import org.apache.commons.math3.linear.RealMatrix;
/*      */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*      */ import org.apache.commons.math3.optimization.GoalType;
/*      */ import org.apache.commons.math3.optimization.MultivariateOptimizer;
/*      */ import org.apache.commons.math3.optimization.OptimizationData;
/*      */ import org.apache.commons.math3.optimization.PointValuePair;
/*      */ import org.apache.commons.math3.optimization.SimpleValueChecker;
/*      */ import org.apache.commons.math3.random.MersenneTwister;
/*      */ import org.apache.commons.math3.random.RandomGenerator;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.MathArrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */ public class CMAESOptimizer
/*      */   extends BaseAbstractMultivariateSimpleBoundsOptimizer<MultivariateFunction>
/*      */   implements MultivariateOptimizer
/*      */ {
/*      */   public static final int DEFAULT_CHECKFEASABLECOUNT = 0;
/*      */   public static final double DEFAULT_STOPFITNESS = 0.0D;
/*      */   public static final boolean DEFAULT_ISACTIVECMA = true;
/*      */   public static final int DEFAULT_MAXITERATIONS = 30000;
/*      */   public static final int DEFAULT_DIAGONALONLY = 0;
/*   99 */   public static final RandomGenerator DEFAULT_RANDOMGENERATOR = (RandomGenerator)new MersenneTwister();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int lambda;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isActiveCMA;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int checkFeasableCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double[] inputSigma;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int dimension;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  137 */   private int diagonalOnly = 0;
/*      */ 
/*      */   
/*      */   private boolean isMinimize = true;
/*      */ 
/*      */   
/*      */   private boolean generateStatistics = false;
/*      */ 
/*      */   
/*      */   private int maxIterations;
/*      */ 
/*      */   
/*      */   private double stopFitness;
/*      */ 
/*      */   
/*      */   private double stopTolUpX;
/*      */ 
/*      */   
/*      */   private double stopTolX;
/*      */ 
/*      */   
/*      */   private double stopTolFun;
/*      */ 
/*      */   
/*      */   private double stopTolHistFun;
/*      */ 
/*      */   
/*      */   private int mu;
/*      */ 
/*      */   
/*      */   private double logMu2;
/*      */ 
/*      */   
/*      */   private RealMatrix weights;
/*      */   
/*      */   private double mueff;
/*      */   
/*      */   private double sigma;
/*      */   
/*      */   private double cc;
/*      */   
/*      */   private double cs;
/*      */   
/*      */   private double damps;
/*      */   
/*      */   private double ccov1;
/*      */   
/*      */   private double ccovmu;
/*      */   
/*      */   private double chiN;
/*      */   
/*      */   private double ccov1Sep;
/*      */   
/*      */   private double ccovmuSep;
/*      */   
/*      */   private RealMatrix xmean;
/*      */   
/*      */   private RealMatrix pc;
/*      */   
/*      */   private RealMatrix ps;
/*      */   
/*      */   private double normps;
/*      */   
/*      */   private RealMatrix B;
/*      */   
/*      */   private RealMatrix D;
/*      */   
/*      */   private RealMatrix BD;
/*      */   
/*      */   private RealMatrix diagD;
/*      */   
/*      */   private RealMatrix C;
/*      */   
/*      */   private RealMatrix diagC;
/*      */   
/*      */   private int iterations;
/*      */   
/*      */   private double[] fitnessHistory;
/*      */   
/*      */   private int historySize;
/*      */   
/*      */   private RandomGenerator random;
/*      */   
/*  220 */   private List<Double> statisticsSigmaHistory = new ArrayList<Double>();
/*      */   
/*  222 */   private List<RealMatrix> statisticsMeanHistory = new ArrayList<RealMatrix>();
/*      */   
/*  224 */   private List<Double> statisticsFitnessHistory = new ArrayList<Double>();
/*      */   
/*  226 */   private List<RealMatrix> statisticsDHistory = new ArrayList<RealMatrix>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public CMAESOptimizer() {
/*  237 */     this(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public CMAESOptimizer(int lambda) {
/*  248 */     this(lambda, (double[])null, 30000, 0.0D, true, 0, 0, DEFAULT_RANDOMGENERATOR, false, (ConvergenceChecker<PointValuePair>)null);
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
/*      */   @Deprecated
/*      */   public CMAESOptimizer(int lambda, double[] inputSigma) {
/*  264 */     this(lambda, inputSigma, 30000, 0.0D, true, 0, 0, DEFAULT_RANDOMGENERATOR, false);
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
/*      */   @Deprecated
/*      */   public CMAESOptimizer(int lambda, double[] inputSigma, int maxIterations, double stopFitness, boolean isActiveCMA, int diagonalOnly, int checkFeasableCount, RandomGenerator random, boolean generateStatistics) {
/*  290 */     this(lambda, inputSigma, maxIterations, stopFitness, isActiveCMA, diagonalOnly, checkFeasableCount, random, generateStatistics, (ConvergenceChecker<PointValuePair>)new SimpleValueChecker());
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
/*      */   @Deprecated
/*      */   public CMAESOptimizer(int lambda, double[] inputSigma, int maxIterations, double stopFitness, boolean isActiveCMA, int diagonalOnly, int checkFeasableCount, RandomGenerator random, boolean generateStatistics, ConvergenceChecker<PointValuePair> checker) {
/*  320 */     super(checker);
/*  321 */     this.lambda = lambda;
/*  322 */     this.inputSigma = (inputSigma == null) ? null : (double[])inputSigma.clone();
/*  323 */     this.maxIterations = maxIterations;
/*  324 */     this.stopFitness = stopFitness;
/*  325 */     this.isActiveCMA = isActiveCMA;
/*  326 */     this.diagonalOnly = diagonalOnly;
/*  327 */     this.checkFeasableCount = checkFeasableCount;
/*  328 */     this.random = random;
/*  329 */     this.generateStatistics = generateStatistics;
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
/*      */   public CMAESOptimizer(int maxIterations, double stopFitness, boolean isActiveCMA, int diagonalOnly, int checkFeasableCount, RandomGenerator random, boolean generateStatistics, ConvergenceChecker<PointValuePair> checker) {
/*  355 */     super(checker);
/*  356 */     this.maxIterations = maxIterations;
/*  357 */     this.stopFitness = stopFitness;
/*  358 */     this.isActiveCMA = isActiveCMA;
/*  359 */     this.diagonalOnly = diagonalOnly;
/*  360 */     this.checkFeasableCount = checkFeasableCount;
/*  361 */     this.random = random;
/*  362 */     this.generateStatistics = generateStatistics;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Double> getStatisticsSigmaHistory() {
/*  369 */     return this.statisticsSigmaHistory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<RealMatrix> getStatisticsMeanHistory() {
/*  376 */     return this.statisticsMeanHistory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Double> getStatisticsFitnessHistory() {
/*  383 */     return this.statisticsFitnessHistory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<RealMatrix> getStatisticsDHistory() {
/*  390 */     return this.statisticsDHistory;
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
/*      */   public static class Sigma
/*      */     implements OptimizationData
/*      */   {
/*      */     private final double[] sigma;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Sigma(double[] s) throws NotPositiveException {
/*  416 */       for (int i = 0; i < s.length; i++) {
/*  417 */         if (s[i] < 0.0D) {
/*  418 */           throw new NotPositiveException(Double.valueOf(s[i]));
/*      */         }
/*      */       } 
/*      */       
/*  422 */       this.sigma = (double[])s.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double[] getSigma() {
/*  429 */       return (double[])this.sigma.clone();
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
/*      */   public static class PopulationSize
/*      */     implements OptimizationData
/*      */   {
/*      */     private final int lambda;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PopulationSize(int size) throws NotStrictlyPositiveException {
/*  454 */       if (size <= 0) {
/*  455 */         throw new NotStrictlyPositiveException(Integer.valueOf(size));
/*      */       }
/*  457 */       this.lambda = size;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getPopulationSize() {
/*  464 */       return this.lambda;
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
/*      */   protected PointValuePair optimizeInternal(int maxEval, MultivariateFunction f, GoalType goalType, OptimizationData... optData) {
/*  488 */     parseOptimizationData(optData);
/*      */ 
/*      */ 
/*      */     
/*  492 */     return super.optimizeInternal(maxEval, f, goalType, optData);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected PointValuePair doOptimize() {
/*  498 */     checkParameters();
/*      */     
/*  500 */     this.isMinimize = getGoalType().equals(GoalType.MINIMIZE);
/*  501 */     FitnessFunction fitfun = new FitnessFunction();
/*  502 */     double[] guess = getStartPoint();
/*      */     
/*  504 */     this.dimension = guess.length;
/*  505 */     initializeCMA(guess);
/*  506 */     this.iterations = 0;
/*  507 */     double bestValue = fitfun.value(guess);
/*  508 */     push(this.fitnessHistory, bestValue);
/*  509 */     PointValuePair optimum = new PointValuePair(getStartPoint(), this.isMinimize ? bestValue : -bestValue);
/*      */     
/*  511 */     PointValuePair lastResult = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  516 */     label101: for (this.iterations = 1; this.iterations <= this.maxIterations; this.iterations++) {
/*      */       
/*  518 */       RealMatrix arz = randn1(this.dimension, this.lambda);
/*  519 */       RealMatrix arx = zeros(this.dimension, this.lambda);
/*  520 */       double[] fitness = new double[this.lambda];
/*      */       
/*  522 */       for (int k = 0; k < this.lambda; k++) {
/*  523 */         RealMatrix arxk = null;
/*  524 */         for (int j = 0; j < this.checkFeasableCount + 1; j++) {
/*  525 */           if (this.diagonalOnly <= 0) {
/*  526 */             arxk = this.xmean.add(this.BD.multiply(arz.getColumnMatrix(k)).scalarMultiply(this.sigma));
/*      */           } else {
/*      */             
/*  529 */             arxk = this.xmean.add(times(this.diagD, arz.getColumnMatrix(k)).scalarMultiply(this.sigma));
/*      */           } 
/*      */           
/*  532 */           if (j >= this.checkFeasableCount || fitfun.isFeasible(arxk.getColumn(0))) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/*  537 */           arz.setColumn(k, randn(this.dimension));
/*      */         } 
/*  539 */         copyColumn(arxk, 0, arx, k);
/*      */         try {
/*  541 */           fitness[k] = fitfun.value(arx.getColumn(k));
/*  542 */         } catch (TooManyEvaluationsException e) {
/*      */           break label101;
/*      */         } 
/*      */       } 
/*      */       
/*  547 */       int[] arindex = sortedIndices(fitness);
/*      */       
/*  549 */       RealMatrix xold = this.xmean;
/*  550 */       RealMatrix bestArx = selectColumns(arx, MathArrays.copyOf(arindex, this.mu));
/*  551 */       this.xmean = bestArx.multiply(this.weights);
/*  552 */       RealMatrix bestArz = selectColumns(arz, MathArrays.copyOf(arindex, this.mu));
/*  553 */       RealMatrix zmean = bestArz.multiply(this.weights);
/*  554 */       boolean hsig = updateEvolutionPaths(zmean, xold);
/*  555 */       if (this.diagonalOnly <= 0) {
/*  556 */         updateCovariance(hsig, bestArx, arz, arindex, xold);
/*      */       } else {
/*  558 */         updateCovarianceDiagonalOnly(hsig, bestArz);
/*      */       } 
/*      */       
/*  561 */       this.sigma *= FastMath.exp(FastMath.min(1.0D, (this.normps / this.chiN - 1.0D) * this.cs / this.damps));
/*  562 */       double bestFitness = fitness[arindex[0]];
/*  563 */       double worstFitness = fitness[arindex[arindex.length - 1]];
/*  564 */       if (bestValue > bestFitness) {
/*  565 */         bestValue = bestFitness;
/*  566 */         lastResult = optimum;
/*  567 */         optimum = new PointValuePair(fitfun.repair(bestArx.getColumn(0)), this.isMinimize ? bestFitness : -bestFitness);
/*      */         
/*  569 */         if (getConvergenceChecker() != null && lastResult != null && getConvergenceChecker().converged(this.iterations, optimum, lastResult)) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  576 */       if (this.stopFitness != 0.0D && bestFitness < (this.isMinimize ? this.stopFitness : -this.stopFitness)) {
/*      */         break;
/*      */       }
/*  579 */       double[] sqrtDiagC = sqrt(this.diagC).getColumn(0);
/*  580 */       double[] pcCol = this.pc.getColumn(0); int i;
/*  581 */       for (i = 0; i < this.dimension && 
/*  582 */         this.sigma * FastMath.max(FastMath.abs(pcCol[i]), sqrtDiagC[i]) <= this.stopTolX; i++) {
/*      */ 
/*      */         
/*  585 */         if (i >= this.dimension - 1) {
/*      */           break label101;
/*      */         }
/*      */       } 
/*  589 */       for (i = 0; i < this.dimension; i++) {
/*  590 */         if (this.sigma * sqrtDiagC[i] > this.stopTolUpX) {
/*      */           break label101;
/*      */         }
/*      */       } 
/*  594 */       double historyBest = min(this.fitnessHistory);
/*  595 */       double historyWorst = max(this.fitnessHistory);
/*  596 */       if (this.iterations > 2 && FastMath.max(historyWorst, worstFitness) - FastMath.min(historyBest, bestFitness) < this.stopTolFun) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  601 */       if (this.iterations > this.fitnessHistory.length && historyWorst - historyBest < this.stopTolHistFun) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  606 */       if (max(this.diagD) / min(this.diagD) > 1.0E7D) {
/*      */         break;
/*      */       }
/*      */       
/*  610 */       if (getConvergenceChecker() != null) {
/*  611 */         PointValuePair current = new PointValuePair(bestArx.getColumn(0), this.isMinimize ? bestFitness : -bestFitness);
/*      */ 
/*      */         
/*  614 */         if (lastResult != null && getConvergenceChecker().converged(this.iterations, current, lastResult)) {
/*      */           break;
/*      */         }
/*      */         
/*  618 */         lastResult = current;
/*      */       } 
/*      */       
/*  621 */       if (bestValue == fitness[arindex[(int)(0.1D + this.lambda / 4.0D)]]) {
/*  622 */         this.sigma *= FastMath.exp(0.2D + this.cs / this.damps);
/*      */       }
/*  624 */       if (this.iterations > 2 && FastMath.max(historyWorst, bestFitness) - FastMath.min(historyBest, bestFitness) == 0.0D)
/*      */       {
/*  626 */         this.sigma *= FastMath.exp(0.2D + this.cs / this.damps);
/*      */       }
/*      */       
/*  629 */       push(this.fitnessHistory, bestFitness);
/*  630 */       fitfun.setValueRange(worstFitness - bestFitness);
/*  631 */       if (this.generateStatistics) {
/*  632 */         this.statisticsSigmaHistory.add(Double.valueOf(this.sigma));
/*  633 */         this.statisticsFitnessHistory.add(Double.valueOf(bestFitness));
/*  634 */         this.statisticsMeanHistory.add(this.xmean.transpose());
/*  635 */         this.statisticsDHistory.add(this.diagD.transpose().scalarMultiply(100000.0D));
/*      */       } 
/*      */     } 
/*  638 */     return optimum;
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
/*      */   private void parseOptimizationData(OptimizationData... optData) {
/*  654 */     for (OptimizationData data : optData) {
/*  655 */       if (data instanceof Sigma) {
/*  656 */         this.inputSigma = ((Sigma)data).getSigma();
/*      */       
/*      */       }
/*  659 */       else if (data instanceof PopulationSize) {
/*  660 */         this.lambda = ((PopulationSize)data).getPopulationSize();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkParameters() {
/*  670 */     double[] init = getStartPoint();
/*  671 */     double[] lB = getLowerBound();
/*  672 */     double[] uB = getUpperBound();
/*      */     
/*  674 */     if (this.inputSigma != null) {
/*  675 */       if (this.inputSigma.length != init.length) {
/*  676 */         throw new DimensionMismatchException(this.inputSigma.length, init.length);
/*      */       }
/*  678 */       for (int i = 0; i < init.length; i++) {
/*  679 */         if (this.inputSigma[i] < 0.0D)
/*      */         {
/*  681 */           throw new NotPositiveException(Double.valueOf(this.inputSigma[i]));
/*      */         }
/*  683 */         if (this.inputSigma[i] > uB[i] - lB[i]) {
/*  684 */           throw new OutOfRangeException(Double.valueOf(this.inputSigma[i]), Integer.valueOf(0), Double.valueOf(uB[i] - lB[i]));
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
/*      */   private void initializeCMA(double[] guess) {
/*  696 */     if (this.lambda <= 0)
/*      */     {
/*      */       
/*  699 */       this.lambda = 4 + (int)(3.0D * FastMath.log(this.dimension));
/*      */     }
/*      */     
/*  702 */     double[][] sigmaArray = new double[guess.length][1];
/*  703 */     for (int i = 0; i < guess.length; i++)
/*      */     {
/*      */       
/*  706 */       sigmaArray[i][0] = (this.inputSigma == null) ? 0.3D : this.inputSigma[i];
/*      */     }
/*  708 */     Array2DRowRealMatrix array2DRowRealMatrix = new Array2DRowRealMatrix(sigmaArray, false);
/*  709 */     this.sigma = max((RealMatrix)array2DRowRealMatrix);
/*      */ 
/*      */     
/*  712 */     this.stopTolUpX = 1000.0D * max((RealMatrix)array2DRowRealMatrix);
/*  713 */     this.stopTolX = 1.0E-11D * max((RealMatrix)array2DRowRealMatrix);
/*  714 */     this.stopTolFun = 1.0E-12D;
/*  715 */     this.stopTolHistFun = 1.0E-13D;
/*      */ 
/*      */     
/*  718 */     this.mu = this.lambda / 2;
/*  719 */     this.logMu2 = FastMath.log(this.mu + 0.5D);
/*  720 */     this.weights = log(sequence(1.0D, this.mu, 1.0D)).scalarMultiply(-1.0D).scalarAdd(this.logMu2);
/*  721 */     double sumw = 0.0D;
/*  722 */     double sumwq = 0.0D; int j;
/*  723 */     for (j = 0; j < this.mu; j++) {
/*  724 */       double w = this.weights.getEntry(j, 0);
/*  725 */       sumw += w;
/*  726 */       sumwq += w * w;
/*      */     } 
/*  728 */     this.weights = this.weights.scalarMultiply(1.0D / sumw);
/*  729 */     this.mueff = sumw * sumw / sumwq;
/*      */ 
/*      */     
/*  732 */     this.cc = (4.0D + this.mueff / this.dimension) / ((this.dimension + 4) + 2.0D * this.mueff / this.dimension);
/*      */     
/*  734 */     this.cs = (this.mueff + 2.0D) / (this.dimension + this.mueff + 3.0D);
/*  735 */     this.damps = (1.0D + 2.0D * FastMath.max(0.0D, FastMath.sqrt((this.mueff - 1.0D) / (this.dimension + 1)) - 1.0D)) * FastMath.max(0.3D, 1.0D - this.dimension / (1.0E-6D + this.maxIterations)) + this.cs;
/*      */ 
/*      */ 
/*      */     
/*  739 */     this.ccov1 = 2.0D / ((this.dimension + 1.3D) * (this.dimension + 1.3D) + this.mueff);
/*  740 */     this.ccovmu = FastMath.min(1.0D - this.ccov1, 2.0D * (this.mueff - 2.0D + 1.0D / this.mueff) / (((this.dimension + 2) * (this.dimension + 2)) + this.mueff));
/*      */     
/*  742 */     this.ccov1Sep = FastMath.min(1.0D, this.ccov1 * (this.dimension + 1.5D) / 3.0D);
/*  743 */     this.ccovmuSep = FastMath.min(1.0D - this.ccov1, this.ccovmu * (this.dimension + 1.5D) / 3.0D);
/*  744 */     this.chiN = FastMath.sqrt(this.dimension) * (1.0D - 1.0D / 4.0D * this.dimension + 1.0D / 21.0D * this.dimension * this.dimension);
/*      */ 
/*      */     
/*  747 */     this.xmean = MatrixUtils.createColumnRealMatrix(guess);
/*  748 */     this.diagD = array2DRowRealMatrix.scalarMultiply(1.0D / this.sigma);
/*  749 */     this.diagC = square(this.diagD);
/*  750 */     this.pc = zeros(this.dimension, 1);
/*  751 */     this.ps = zeros(this.dimension, 1);
/*  752 */     this.normps = this.ps.getFrobeniusNorm();
/*      */     
/*  754 */     this.B = eye(this.dimension, this.dimension);
/*  755 */     this.D = ones(this.dimension, 1);
/*  756 */     this.BD = times(this.B, repmat(this.diagD.transpose(), this.dimension, 1));
/*  757 */     this.C = this.B.multiply(diag(square(this.D)).multiply(this.B.transpose()));
/*  758 */     this.historySize = 10 + (int)((30 * this.dimension) / this.lambda);
/*  759 */     this.fitnessHistory = new double[this.historySize];
/*  760 */     for (j = 0; j < this.historySize; j++) {
/*  761 */       this.fitnessHistory[j] = Double.MAX_VALUE;
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
/*      */   private boolean updateEvolutionPaths(RealMatrix zmean, RealMatrix xold) {
/*  774 */     this.ps = this.ps.scalarMultiply(1.0D - this.cs).add(this.B.multiply(zmean).scalarMultiply(FastMath.sqrt(this.cs * (2.0D - this.cs) * this.mueff)));
/*      */     
/*  776 */     this.normps = this.ps.getFrobeniusNorm();
/*  777 */     boolean hsig = (this.normps / FastMath.sqrt(1.0D - FastMath.pow(1.0D - this.cs, 2 * this.iterations)) / this.chiN < 1.4D + 2.0D / (this.dimension + 1.0D));
/*      */ 
/*      */     
/*  780 */     this.pc = this.pc.scalarMultiply(1.0D - this.cc);
/*  781 */     if (hsig) {
/*  782 */       this.pc = this.pc.add(this.xmean.subtract(xold).scalarMultiply(FastMath.sqrt(this.cc * (2.0D - this.cc) * this.mueff) / this.sigma));
/*      */     }
/*  784 */     return hsig;
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
/*      */   private void updateCovarianceDiagonalOnly(boolean hsig, RealMatrix bestArz) {
/*  797 */     double oldFac = hsig ? 0.0D : (this.ccov1Sep * this.cc * (2.0D - this.cc));
/*  798 */     oldFac += 1.0D - this.ccov1Sep - this.ccovmuSep;
/*  799 */     this.diagC = this.diagC.scalarMultiply(oldFac).add(square(this.pc).scalarMultiply(this.ccov1Sep)).add(times(this.diagC, square(bestArz).multiply(this.weights)).scalarMultiply(this.ccovmuSep));
/*      */ 
/*      */ 
/*      */     
/*  803 */     this.diagD = sqrt(this.diagC);
/*  804 */     if (this.diagonalOnly > 1 && this.iterations > this.diagonalOnly) {
/*      */ 
/*      */       
/*  807 */       this.diagonalOnly = 0;
/*  808 */       this.B = eye(this.dimension, this.dimension);
/*  809 */       this.BD = diag(this.diagD);
/*  810 */       this.C = diag(this.diagC);
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
/*      */   private void updateCovariance(boolean hsig, RealMatrix bestArx, RealMatrix arz, int[] arindex, RealMatrix xold) {
/*  828 */     double negccov = 0.0D;
/*  829 */     if (this.ccov1 + this.ccovmu > 0.0D) {
/*  830 */       RealMatrix arpos = bestArx.subtract(repmat(xold, 1, this.mu)).scalarMultiply(1.0D / this.sigma);
/*      */       
/*  832 */       RealMatrix roneu = this.pc.multiply(this.pc.transpose()).scalarMultiply(this.ccov1);
/*      */ 
/*      */       
/*  835 */       double oldFac = hsig ? 0.0D : (this.ccov1 * this.cc * (2.0D - this.cc));
/*  836 */       oldFac += 1.0D - this.ccov1 - this.ccovmu;
/*  837 */       if (this.isActiveCMA) {
/*      */         
/*  839 */         negccov = (1.0D - this.ccovmu) * 0.25D * this.mueff / (FastMath.pow((this.dimension + 2), 1.5D) + 2.0D * this.mueff);
/*      */ 
/*      */         
/*  842 */         double negminresidualvariance = 0.66D;
/*      */         
/*  844 */         double negalphaold = 0.5D;
/*      */         
/*  846 */         int[] arReverseIndex = reverse(arindex);
/*  847 */         RealMatrix arzneg = selectColumns(arz, MathArrays.copyOf(arReverseIndex, this.mu));
/*  848 */         RealMatrix arnorms = sqrt(sumRows(square(arzneg)));
/*  849 */         int[] idxnorms = sortedIndices(arnorms.getRow(0));
/*  850 */         RealMatrix arnormsSorted = selectColumns(arnorms, idxnorms);
/*  851 */         int[] idxReverse = reverse(idxnorms);
/*  852 */         RealMatrix arnormsReverse = selectColumns(arnorms, idxReverse);
/*  853 */         arnorms = divide(arnormsReverse, arnormsSorted);
/*  854 */         int[] idxInv = inverse(idxnorms);
/*  855 */         RealMatrix arnormsInv = selectColumns(arnorms, idxInv);
/*      */         
/*  857 */         double negcovMax = 0.33999999999999997D / square(arnormsInv).multiply(this.weights).getEntry(0, 0);
/*      */         
/*  859 */         if (negccov > negcovMax) {
/*  860 */           negccov = negcovMax;
/*      */         }
/*  862 */         arzneg = times(arzneg, repmat(arnormsInv, this.dimension, 1));
/*  863 */         RealMatrix artmp = this.BD.multiply(arzneg);
/*  864 */         RealMatrix Cneg = artmp.multiply(diag(this.weights)).multiply(artmp.transpose());
/*  865 */         oldFac += 0.5D * negccov;
/*  866 */         this.C = this.C.scalarMultiply(oldFac).add(roneu).add(arpos.scalarMultiply(this.ccovmu + 0.5D * negccov).multiply(times(repmat(this.weights, 1, this.dimension), arpos.transpose()))).subtract(Cneg.scalarMultiply(negccov));
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/*  875 */         this.C = this.C.scalarMultiply(oldFac).add(roneu).add(arpos.scalarMultiply(this.ccovmu).multiply(times(repmat(this.weights, 1, this.dimension), arpos.transpose())));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  882 */     updateBD(negccov);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateBD(double negccov) {
/*  891 */     if (this.ccov1 + this.ccovmu + negccov > 0.0D && this.iterations % 1.0D / (this.ccov1 + this.ccovmu + negccov) / this.dimension / 10.0D < 1.0D) {
/*      */ 
/*      */       
/*  894 */       this.C = triu(this.C, 0).add(triu(this.C, 1).transpose());
/*      */       
/*  896 */       EigenDecomposition eig = new EigenDecomposition(this.C);
/*  897 */       this.B = eig.getV();
/*  898 */       this.D = eig.getD();
/*  899 */       this.diagD = diag(this.D);
/*  900 */       if (min(this.diagD) <= 0.0D) {
/*  901 */         for (int i = 0; i < this.dimension; i++) {
/*  902 */           if (this.diagD.getEntry(i, 0) < 0.0D) {
/*  903 */             this.diagD.setEntry(i, 0, 0.0D);
/*      */           }
/*      */         } 
/*  906 */         double tfac = max(this.diagD) / 1.0E14D;
/*  907 */         this.C = this.C.add(eye(this.dimension, this.dimension).scalarMultiply(tfac));
/*  908 */         this.diagD = this.diagD.add(ones(this.dimension, 1).scalarMultiply(tfac));
/*      */       } 
/*  910 */       if (max(this.diagD) > 1.0E14D * min(this.diagD)) {
/*  911 */         double tfac = max(this.diagD) / 1.0E14D - min(this.diagD);
/*  912 */         this.C = this.C.add(eye(this.dimension, this.dimension).scalarMultiply(tfac));
/*  913 */         this.diagD = this.diagD.add(ones(this.dimension, 1).scalarMultiply(tfac));
/*      */       } 
/*  915 */       this.diagC = diag(this.C);
/*  916 */       this.diagD = sqrt(this.diagD);
/*  917 */       this.BD = times(this.B, repmat(this.diagD.transpose(), this.dimension, 1));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void push(double[] vals, double val) {
/*  928 */     for (int i = vals.length - 1; i > 0; i--) {
/*  929 */       vals[i] = vals[i - 1];
/*      */     }
/*  931 */     vals[0] = val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] sortedIndices(double[] doubles) {
/*  941 */     DoubleIndex[] dis = new DoubleIndex[doubles.length];
/*  942 */     for (int i = 0; i < doubles.length; i++) {
/*  943 */       dis[i] = new DoubleIndex(doubles[i], i);
/*      */     }
/*  945 */     Arrays.sort((Object[])dis);
/*  946 */     int[] indices = new int[doubles.length];
/*  947 */     for (int j = 0; j < doubles.length; j++) {
/*  948 */       indices[j] = (dis[j]).index;
/*      */     }
/*  950 */     return indices;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class DoubleIndex
/*      */     implements Comparable<DoubleIndex>
/*      */   {
/*      */     private final double value;
/*      */ 
/*      */ 
/*      */     
/*      */     private final int index;
/*      */ 
/*      */ 
/*      */     
/*      */     DoubleIndex(double value, int index) {
/*  968 */       this.value = value;
/*  969 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(DoubleIndex o) {
/*  974 */       return Double.compare(this.value, o.value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object other) {
/*  981 */       if (this == other) {
/*  982 */         return true;
/*      */       }
/*      */       
/*  985 */       if (other instanceof DoubleIndex) {
/*  986 */         return (Double.compare(this.value, ((DoubleIndex)other).value) == 0);
/*      */       }
/*      */       
/*  989 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  995 */       long bits = Double.doubleToLongBits(this.value);
/*  996 */       return (int)((0x15F34EL ^ bits >>> 32L ^ bits) & 0xFFFFFFFFFFFFFFFFL);
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
/*      */   private class FitnessFunction
/*      */   {
/* 1017 */     private double valueRange = 1.0D;
/*      */ 
/*      */ 
/*      */     
/*      */     private final boolean isRepairMode = true;
/*      */ 
/*      */ 
/*      */     
/*      */     public double value(double[] point) {
/*      */       double value;
/* 1027 */       if (this.isRepairMode) {
/* 1028 */         double[] repaired = repair(point);
/* 1029 */         value = CMAESOptimizer.this.computeObjectiveValue(repaired) + penalty(point, repaired);
/*      */       } else {
/*      */         
/* 1032 */         value = CMAESOptimizer.this.computeObjectiveValue(point);
/*      */       } 
/* 1034 */       return CMAESOptimizer.this.isMinimize ? value : -value;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isFeasible(double[] x) {
/* 1042 */       double[] lB = CMAESOptimizer.this.getLowerBound();
/* 1043 */       double[] uB = CMAESOptimizer.this.getUpperBound();
/*      */       
/* 1045 */       for (int i = 0; i < x.length; i++) {
/* 1046 */         if (x[i] < lB[i]) {
/* 1047 */           return false;
/*      */         }
/* 1049 */         if (x[i] > uB[i]) {
/* 1050 */           return false;
/*      */         }
/*      */       } 
/* 1053 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValueRange(double valueRange) {
/* 1060 */       this.valueRange = valueRange;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private double[] repair(double[] x) {
/* 1068 */       double[] lB = CMAESOptimizer.this.getLowerBound();
/* 1069 */       double[] uB = CMAESOptimizer.this.getUpperBound();
/*      */       
/* 1071 */       double[] repaired = new double[x.length];
/* 1072 */       for (int i = 0; i < x.length; i++) {
/* 1073 */         if (x[i] < lB[i]) {
/* 1074 */           repaired[i] = lB[i];
/* 1075 */         } else if (x[i] > uB[i]) {
/* 1076 */           repaired[i] = uB[i];
/*      */         } else {
/* 1078 */           repaired[i] = x[i];
/*      */         } 
/*      */       } 
/* 1081 */       return repaired;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private double penalty(double[] x, double[] repaired) {
/* 1090 */       double penalty = 0.0D;
/* 1091 */       for (int i = 0; i < x.length; i++) {
/* 1092 */         double diff = FastMath.abs(x[i] - repaired[i]);
/* 1093 */         penalty += diff * this.valueRange;
/*      */       } 
/* 1095 */       return CMAESOptimizer.this.isMinimize ? penalty : -penalty;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix log(RealMatrix m) {
/* 1106 */     double[][] d = new double[m.getRowDimension()][m.getColumnDimension()];
/* 1107 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1108 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1109 */         d[r][c] = FastMath.log(m.getEntry(r, c));
/*      */       }
/*      */     } 
/* 1112 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix sqrt(RealMatrix m) {
/* 1120 */     double[][] d = new double[m.getRowDimension()][m.getColumnDimension()];
/* 1121 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1122 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1123 */         d[r][c] = FastMath.sqrt(m.getEntry(r, c));
/*      */       }
/*      */     } 
/* 1126 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix square(RealMatrix m) {
/* 1134 */     double[][] d = new double[m.getRowDimension()][m.getColumnDimension()];
/* 1135 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1136 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1137 */         double e = m.getEntry(r, c);
/* 1138 */         d[r][c] = e * e;
/*      */       } 
/*      */     } 
/* 1141 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix times(RealMatrix m, RealMatrix n) {
/* 1150 */     double[][] d = new double[m.getRowDimension()][m.getColumnDimension()];
/* 1151 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1152 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1153 */         d[r][c] = m.getEntry(r, c) * n.getEntry(r, c);
/*      */       }
/*      */     } 
/* 1156 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix divide(RealMatrix m, RealMatrix n) {
/* 1165 */     double[][] d = new double[m.getRowDimension()][m.getColumnDimension()];
/* 1166 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1167 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1168 */         d[r][c] = m.getEntry(r, c) / n.getEntry(r, c);
/*      */       }
/*      */     } 
/* 1171 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix selectColumns(RealMatrix m, int[] cols) {
/* 1180 */     double[][] d = new double[m.getRowDimension()][cols.length];
/* 1181 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1182 */       for (int c = 0; c < cols.length; c++) {
/* 1183 */         d[r][c] = m.getEntry(r, cols[c]);
/*      */       }
/*      */     } 
/* 1186 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix triu(RealMatrix m, int k) {
/* 1195 */     double[][] d = new double[m.getRowDimension()][m.getColumnDimension()];
/* 1196 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1197 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1198 */         d[r][c] = (r <= c - k) ? m.getEntry(r, c) : 0.0D;
/*      */       }
/*      */     } 
/* 1201 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix sumRows(RealMatrix m) {
/* 1209 */     double[][] d = new double[1][m.getColumnDimension()];
/* 1210 */     for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1211 */       double sum = 0.0D;
/* 1212 */       for (int r = 0; r < m.getRowDimension(); r++) {
/* 1213 */         sum += m.getEntry(r, c);
/*      */       }
/* 1215 */       d[0][c] = sum;
/*      */     } 
/* 1217 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix diag(RealMatrix m) {
/* 1226 */     if (m.getColumnDimension() == 1) {
/* 1227 */       double[][] arrayOfDouble = new double[m.getRowDimension()][m.getRowDimension()];
/* 1228 */       for (int j = 0; j < m.getRowDimension(); j++) {
/* 1229 */         arrayOfDouble[j][j] = m.getEntry(j, 0);
/*      */       }
/* 1231 */       return (RealMatrix)new Array2DRowRealMatrix(arrayOfDouble, false);
/*      */     } 
/* 1233 */     double[][] d = new double[m.getRowDimension()][1];
/* 1234 */     for (int i = 0; i < m.getColumnDimension(); i++) {
/* 1235 */       d[i][0] = m.getEntry(i, i);
/*      */     }
/* 1237 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
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
/*      */   private static void copyColumn(RealMatrix m1, int col1, RealMatrix m2, int col2) {
/* 1251 */     for (int i = 0; i < m1.getRowDimension(); i++) {
/* 1252 */       m2.setEntry(i, col2, m1.getEntry(i, col1));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix ones(int n, int m) {
/* 1262 */     double[][] d = new double[n][m];
/* 1263 */     for (int r = 0; r < n; r++) {
/* 1264 */       Arrays.fill(d[r], 1.0D);
/*      */     }
/* 1266 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix eye(int n, int m) {
/* 1276 */     double[][] d = new double[n][m];
/* 1277 */     for (int r = 0; r < n; r++) {
/* 1278 */       if (r < m) {
/* 1279 */         d[r][r] = 1.0D;
/*      */       }
/*      */     } 
/* 1282 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix zeros(int n, int m) {
/* 1291 */     return (RealMatrix)new Array2DRowRealMatrix(n, m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix repmat(RealMatrix mat, int n, int m) {
/* 1301 */     int rd = mat.getRowDimension();
/* 1302 */     int cd = mat.getColumnDimension();
/* 1303 */     double[][] d = new double[n * rd][m * cd];
/* 1304 */     for (int r = 0; r < n * rd; r++) {
/* 1305 */       for (int c = 0; c < m * cd; c++) {
/* 1306 */         d[r][c] = mat.getEntry(r % rd, c % cd);
/*      */       }
/*      */     } 
/* 1309 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix sequence(double start, double end, double step) {
/* 1319 */     int size = (int)((end - start) / step + 1.0D);
/* 1320 */     double[][] d = new double[size][1];
/* 1321 */     double value = start;
/* 1322 */     for (int r = 0; r < size; r++) {
/* 1323 */       d[r][0] = value;
/* 1324 */       value += step;
/*      */     } 
/* 1326 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double max(RealMatrix m) {
/* 1334 */     double max = -1.7976931348623157E308D;
/* 1335 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1336 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1337 */         double e = m.getEntry(r, c);
/* 1338 */         if (max < e) {
/* 1339 */           max = e;
/*      */         }
/*      */       } 
/*      */     } 
/* 1343 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double min(RealMatrix m) {
/* 1351 */     double min = Double.MAX_VALUE;
/* 1352 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1353 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1354 */         double e = m.getEntry(r, c);
/* 1355 */         if (min > e) {
/* 1356 */           min = e;
/*      */         }
/*      */       } 
/*      */     } 
/* 1360 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double max(double[] m) {
/* 1368 */     double max = -1.7976931348623157E308D;
/* 1369 */     for (int r = 0; r < m.length; r++) {
/* 1370 */       if (max < m[r]) {
/* 1371 */         max = m[r];
/*      */       }
/*      */     } 
/* 1374 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double min(double[] m) {
/* 1382 */     double min = Double.MAX_VALUE;
/* 1383 */     for (int r = 0; r < m.length; r++) {
/* 1384 */       if (min > m[r]) {
/* 1385 */         min = m[r];
/*      */       }
/*      */     } 
/* 1388 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] inverse(int[] indices) {
/* 1396 */     int[] inverse = new int[indices.length];
/* 1397 */     for (int i = 0; i < indices.length; i++) {
/* 1398 */       inverse[indices[i]] = i;
/*      */     }
/* 1400 */     return inverse;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] reverse(int[] indices) {
/* 1408 */     int[] reverse = new int[indices.length];
/* 1409 */     for (int i = 0; i < indices.length; i++) {
/* 1410 */       reverse[i] = indices[indices.length - i - 1];
/*      */     }
/* 1412 */     return reverse;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double[] randn(int size) {
/* 1420 */     double[] randn = new double[size];
/* 1421 */     for (int i = 0; i < size; i++) {
/* 1422 */       randn[i] = this.random.nextGaussian();
/*      */     }
/* 1424 */     return randn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RealMatrix randn1(int size, int popSize) {
/* 1433 */     double[][] d = new double[size][popSize];
/* 1434 */     for (int r = 0; r < size; r++) {
/* 1435 */       for (int c = 0; c < popSize; c++) {
/* 1436 */         d[r][c] = this.random.nextGaussian();
/*      */       }
/*      */     } 
/* 1439 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/direct/CMAESOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */