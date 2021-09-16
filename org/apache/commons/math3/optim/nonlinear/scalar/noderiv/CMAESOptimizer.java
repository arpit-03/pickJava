/*      */ package org.apache.commons.math3.optim.nonlinear.scalar.noderiv;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*      */ import org.apache.commons.math3.exception.NotPositiveException;
/*      */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*      */ import org.apache.commons.math3.exception.OutOfRangeException;
/*      */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*      */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*      */ import org.apache.commons.math3.linear.EigenDecomposition;
/*      */ import org.apache.commons.math3.linear.MatrixUtils;
/*      */ import org.apache.commons.math3.linear.RealMatrix;
/*      */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*      */ import org.apache.commons.math3.optim.OptimizationData;
/*      */ import org.apache.commons.math3.optim.PointValuePair;
/*      */ import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
/*      */ import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CMAESOptimizer
/*      */   extends MultivariateOptimizer
/*      */ {
/*      */   private int lambda;
/*      */   private final boolean isActiveCMA;
/*      */   private final int checkFeasableCount;
/*      */   private double[] inputSigma;
/*      */   private int dimension;
/*      */   private int diagonalOnly;
/*      */   private boolean isMinimize = true;
/*      */   private final boolean generateStatistics;
/*      */   private final int maxIterations;
/*      */   private final double stopFitness;
/*      */   private double stopTolUpX;
/*      */   private double stopTolX;
/*      */   private double stopTolFun;
/*      */   private double stopTolHistFun;
/*      */   private int mu;
/*      */   private double logMu2;
/*      */   private RealMatrix weights;
/*      */   private double mueff;
/*      */   private double sigma;
/*      */   private double cc;
/*      */   private double cs;
/*      */   private double damps;
/*      */   private double ccov1;
/*      */   private double ccovmu;
/*      */   private double chiN;
/*      */   private double ccov1Sep;
/*      */   private double ccovmuSep;
/*      */   private RealMatrix xmean;
/*      */   private RealMatrix pc;
/*      */   private RealMatrix ps;
/*      */   private double normps;
/*      */   private RealMatrix B;
/*      */   private RealMatrix D;
/*      */   private RealMatrix BD;
/*      */   private RealMatrix diagD;
/*      */   private RealMatrix C;
/*      */   private RealMatrix diagC;
/*      */   private int iterations;
/*      */   private double[] fitnessHistory;
/*      */   private int historySize;
/*      */   private final RandomGenerator random;
/*  202 */   private final List<Double> statisticsSigmaHistory = new ArrayList<Double>();
/*      */   
/*  204 */   private final List<RealMatrix> statisticsMeanHistory = new ArrayList<RealMatrix>();
/*      */   
/*  206 */   private final List<Double> statisticsFitnessHistory = new ArrayList<Double>();
/*      */   
/*  208 */   private final List<RealMatrix> statisticsDHistory = new ArrayList<RealMatrix>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  233 */     super(checker);
/*  234 */     this.maxIterations = maxIterations;
/*  235 */     this.stopFitness = stopFitness;
/*  236 */     this.isActiveCMA = isActiveCMA;
/*  237 */     this.diagonalOnly = diagonalOnly;
/*  238 */     this.checkFeasableCount = checkFeasableCount;
/*  239 */     this.random = random;
/*  240 */     this.generateStatistics = generateStatistics;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Double> getStatisticsSigmaHistory() {
/*  247 */     return this.statisticsSigmaHistory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<RealMatrix> getStatisticsMeanHistory() {
/*  254 */     return this.statisticsMeanHistory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Double> getStatisticsFitnessHistory() {
/*  261 */     return this.statisticsFitnessHistory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<RealMatrix> getStatisticsDHistory() {
/*  268 */     return this.statisticsDHistory;
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
/*  293 */       for (int i = 0; i < s.length; i++) {
/*  294 */         if (s[i] < 0.0D) {
/*  295 */           throw new NotPositiveException(Double.valueOf(s[i]));
/*      */         }
/*      */       } 
/*      */       
/*  299 */       this.sigma = (double[])s.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double[] getSigma() {
/*  306 */       return (double[])this.sigma.clone();
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
/*  330 */       if (size <= 0) {
/*  331 */         throw new NotStrictlyPositiveException(Integer.valueOf(size));
/*      */       }
/*  333 */       this.lambda = size;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getPopulationSize() {
/*  340 */       return this.lambda;
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
/*      */   public PointValuePair optimize(OptimizationData... optData) throws TooManyEvaluationsException, DimensionMismatchException {
/*  365 */     return super.optimize(optData);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PointValuePair doOptimize() {
/*  372 */     this.isMinimize = getGoalType().equals(GoalType.MINIMIZE);
/*  373 */     FitnessFunction fitfun = new FitnessFunction();
/*  374 */     double[] guess = getStartPoint();
/*      */     
/*  376 */     this.dimension = guess.length;
/*  377 */     initializeCMA(guess);
/*  378 */     this.iterations = 0;
/*  379 */     ValuePenaltyPair valuePenalty = fitfun.value(guess);
/*  380 */     double bestValue = valuePenalty.value + valuePenalty.penalty;
/*  381 */     push(this.fitnessHistory, bestValue);
/*  382 */     PointValuePair optimum = new PointValuePair(getStartPoint(), this.isMinimize ? bestValue : -bestValue);
/*      */ 
/*      */     
/*  385 */     PointValuePair lastResult = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  390 */     label107: for (this.iterations = 1; this.iterations <= this.maxIterations; this.iterations++) {
/*  391 */       incrementIterationCount();
/*      */ 
/*      */       
/*  394 */       RealMatrix arz = randn1(this.dimension, this.lambda);
/*  395 */       RealMatrix arx = zeros(this.dimension, this.lambda);
/*  396 */       double[] fitness = new double[this.lambda];
/*  397 */       ValuePenaltyPair[] valuePenaltyPairs = new ValuePenaltyPair[this.lambda];
/*      */       
/*  399 */       for (int k = 0; k < this.lambda; k++) {
/*  400 */         RealMatrix arxk = null;
/*  401 */         for (int j = 0; j < this.checkFeasableCount + 1; j++) {
/*  402 */           if (this.diagonalOnly <= 0) {
/*  403 */             arxk = this.xmean.add(this.BD.multiply(arz.getColumnMatrix(k)).scalarMultiply(this.sigma));
/*      */           } else {
/*      */             
/*  406 */             arxk = this.xmean.add(times(this.diagD, arz.getColumnMatrix(k)).scalarMultiply(this.sigma));
/*      */           } 
/*      */           
/*  409 */           if (j >= this.checkFeasableCount || fitfun.isFeasible(arxk.getColumn(0))) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/*  414 */           arz.setColumn(k, randn(this.dimension));
/*      */         } 
/*  416 */         copyColumn(arxk, 0, arx, k);
/*      */         try {
/*  418 */           valuePenaltyPairs[k] = fitfun.value(arx.getColumn(k));
/*  419 */         } catch (TooManyEvaluationsException e) {
/*      */           break label107;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  425 */       double valueRange = valueRange(valuePenaltyPairs);
/*  426 */       for (int iValue = 0; iValue < valuePenaltyPairs.length; iValue++) {
/*  427 */         fitness[iValue] = (valuePenaltyPairs[iValue]).value + (valuePenaltyPairs[iValue]).penalty * valueRange;
/*      */       }
/*      */ 
/*      */       
/*  431 */       int[] arindex = sortedIndices(fitness);
/*      */       
/*  433 */       RealMatrix xold = this.xmean;
/*  434 */       RealMatrix bestArx = selectColumns(arx, MathArrays.copyOf(arindex, this.mu));
/*  435 */       this.xmean = bestArx.multiply(this.weights);
/*  436 */       RealMatrix bestArz = selectColumns(arz, MathArrays.copyOf(arindex, this.mu));
/*  437 */       RealMatrix zmean = bestArz.multiply(this.weights);
/*  438 */       boolean hsig = updateEvolutionPaths(zmean, xold);
/*  439 */       if (this.diagonalOnly <= 0) {
/*  440 */         updateCovariance(hsig, bestArx, arz, arindex, xold);
/*      */       } else {
/*  442 */         updateCovarianceDiagonalOnly(hsig, bestArz);
/*      */       } 
/*      */       
/*  445 */       this.sigma *= FastMath.exp(FastMath.min(1.0D, (this.normps / this.chiN - 1.0D) * this.cs / this.damps));
/*  446 */       double bestFitness = fitness[arindex[0]];
/*  447 */       double worstFitness = fitness[arindex[arindex.length - 1]];
/*  448 */       if (bestValue > bestFitness) {
/*  449 */         bestValue = bestFitness;
/*  450 */         lastResult = optimum;
/*  451 */         optimum = new PointValuePair(fitfun.repair(bestArx.getColumn(0)), this.isMinimize ? bestFitness : -bestFitness);
/*      */         
/*  453 */         if (getConvergenceChecker() != null && lastResult != null && getConvergenceChecker().converged(this.iterations, optimum, lastResult)) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  460 */       if (this.stopFitness != 0.0D && bestFitness < (this.isMinimize ? this.stopFitness : -this.stopFitness)) {
/*      */         break;
/*      */       }
/*  463 */       double[] sqrtDiagC = sqrt(this.diagC).getColumn(0);
/*  464 */       double[] pcCol = this.pc.getColumn(0); int i;
/*  465 */       for (i = 0; i < this.dimension && 
/*  466 */         this.sigma * FastMath.max(FastMath.abs(pcCol[i]), sqrtDiagC[i]) <= this.stopTolX; i++) {
/*      */ 
/*      */         
/*  469 */         if (i >= this.dimension - 1) {
/*      */           break label107;
/*      */         }
/*      */       } 
/*  473 */       for (i = 0; i < this.dimension; i++) {
/*  474 */         if (this.sigma * sqrtDiagC[i] > this.stopTolUpX) {
/*      */           break label107;
/*      */         }
/*      */       } 
/*  478 */       double historyBest = min(this.fitnessHistory);
/*  479 */       double historyWorst = max(this.fitnessHistory);
/*  480 */       if (this.iterations > 2 && FastMath.max(historyWorst, worstFitness) - FastMath.min(historyBest, bestFitness) < this.stopTolFun) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  485 */       if (this.iterations > this.fitnessHistory.length && historyWorst - historyBest < this.stopTolHistFun) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  490 */       if (max(this.diagD) / min(this.diagD) > 1.0E7D) {
/*      */         break;
/*      */       }
/*      */       
/*  494 */       if (getConvergenceChecker() != null) {
/*  495 */         PointValuePair current = new PointValuePair(bestArx.getColumn(0), this.isMinimize ? bestFitness : -bestFitness);
/*      */ 
/*      */         
/*  498 */         if (lastResult != null && getConvergenceChecker().converged(this.iterations, current, lastResult)) {
/*      */           break;
/*      */         }
/*      */         
/*  502 */         lastResult = current;
/*      */       } 
/*      */       
/*  505 */       if (bestValue == fitness[arindex[(int)(0.1D + this.lambda / 4.0D)]]) {
/*  506 */         this.sigma *= FastMath.exp(0.2D + this.cs / this.damps);
/*      */       }
/*  508 */       if (this.iterations > 2 && FastMath.max(historyWorst, bestFitness) - FastMath.min(historyBest, bestFitness) == 0.0D)
/*      */       {
/*  510 */         this.sigma *= FastMath.exp(0.2D + this.cs / this.damps);
/*      */       }
/*      */       
/*  513 */       push(this.fitnessHistory, bestFitness);
/*  514 */       if (this.generateStatistics) {
/*  515 */         this.statisticsSigmaHistory.add(Double.valueOf(this.sigma));
/*  516 */         this.statisticsFitnessHistory.add(Double.valueOf(bestFitness));
/*  517 */         this.statisticsMeanHistory.add(this.xmean.transpose());
/*  518 */         this.statisticsDHistory.add(this.diagD.transpose().scalarMultiply(100000.0D));
/*      */       } 
/*      */     } 
/*  521 */     return optimum;
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
/*      */   protected void parseOptimizationData(OptimizationData... optData) {
/*  537 */     super.parseOptimizationData(optData);
/*      */ 
/*      */ 
/*      */     
/*  541 */     for (OptimizationData data : optData) {
/*  542 */       if (data instanceof Sigma) {
/*  543 */         this.inputSigma = ((Sigma)data).getSigma();
/*      */       
/*      */       }
/*  546 */       else if (data instanceof PopulationSize) {
/*  547 */         this.lambda = ((PopulationSize)data).getPopulationSize();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  552 */     checkParameters();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkParameters() {
/*  559 */     double[] init = getStartPoint();
/*  560 */     double[] lB = getLowerBound();
/*  561 */     double[] uB = getUpperBound();
/*      */     
/*  563 */     if (this.inputSigma != null) {
/*  564 */       if (this.inputSigma.length != init.length) {
/*  565 */         throw new DimensionMismatchException(this.inputSigma.length, init.length);
/*      */       }
/*  567 */       for (int i = 0; i < init.length; i++) {
/*  568 */         if (this.inputSigma[i] > uB[i] - lB[i]) {
/*  569 */           throw new OutOfRangeException(Double.valueOf(this.inputSigma[i]), Integer.valueOf(0), Double.valueOf(uB[i] - lB[i]));
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
/*  581 */     if (this.lambda <= 0) {
/*  582 */       throw new NotStrictlyPositiveException(Integer.valueOf(this.lambda));
/*      */     }
/*      */     
/*  585 */     double[][] sigmaArray = new double[guess.length][1];
/*  586 */     for (int i = 0; i < guess.length; i++) {
/*  587 */       sigmaArray[i][0] = this.inputSigma[i];
/*      */     }
/*  589 */     Array2DRowRealMatrix array2DRowRealMatrix = new Array2DRowRealMatrix(sigmaArray, false);
/*  590 */     this.sigma = max((RealMatrix)array2DRowRealMatrix);
/*      */ 
/*      */     
/*  593 */     this.stopTolUpX = 1000.0D * max((RealMatrix)array2DRowRealMatrix);
/*  594 */     this.stopTolX = 1.0E-11D * max((RealMatrix)array2DRowRealMatrix);
/*  595 */     this.stopTolFun = 1.0E-12D;
/*  596 */     this.stopTolHistFun = 1.0E-13D;
/*      */ 
/*      */     
/*  599 */     this.mu = this.lambda / 2;
/*  600 */     this.logMu2 = FastMath.log(this.mu + 0.5D);
/*  601 */     this.weights = log(sequence(1.0D, this.mu, 1.0D)).scalarMultiply(-1.0D).scalarAdd(this.logMu2);
/*  602 */     double sumw = 0.0D;
/*  603 */     double sumwq = 0.0D; int j;
/*  604 */     for (j = 0; j < this.mu; j++) {
/*  605 */       double w = this.weights.getEntry(j, 0);
/*  606 */       sumw += w;
/*  607 */       sumwq += w * w;
/*      */     } 
/*  609 */     this.weights = this.weights.scalarMultiply(1.0D / sumw);
/*  610 */     this.mueff = sumw * sumw / sumwq;
/*      */ 
/*      */     
/*  613 */     this.cc = (4.0D + this.mueff / this.dimension) / ((this.dimension + 4) + 2.0D * this.mueff / this.dimension);
/*      */     
/*  615 */     this.cs = (this.mueff + 2.0D) / (this.dimension + this.mueff + 3.0D);
/*  616 */     this.damps = (1.0D + 2.0D * FastMath.max(0.0D, FastMath.sqrt((this.mueff - 1.0D) / (this.dimension + 1)) - 1.0D)) * FastMath.max(0.3D, 1.0D - this.dimension / (1.0E-6D + this.maxIterations)) + this.cs;
/*      */ 
/*      */ 
/*      */     
/*  620 */     this.ccov1 = 2.0D / ((this.dimension + 1.3D) * (this.dimension + 1.3D) + this.mueff);
/*  621 */     this.ccovmu = FastMath.min(1.0D - this.ccov1, 2.0D * (this.mueff - 2.0D + 1.0D / this.mueff) / (((this.dimension + 2) * (this.dimension + 2)) + this.mueff));
/*      */     
/*  623 */     this.ccov1Sep = FastMath.min(1.0D, this.ccov1 * (this.dimension + 1.5D) / 3.0D);
/*  624 */     this.ccovmuSep = FastMath.min(1.0D - this.ccov1, this.ccovmu * (this.dimension + 1.5D) / 3.0D);
/*  625 */     this.chiN = FastMath.sqrt(this.dimension) * (1.0D - 1.0D / 4.0D * this.dimension + 1.0D / 21.0D * this.dimension * this.dimension);
/*      */ 
/*      */     
/*  628 */     this.xmean = MatrixUtils.createColumnRealMatrix(guess);
/*  629 */     this.diagD = array2DRowRealMatrix.scalarMultiply(1.0D / this.sigma);
/*  630 */     this.diagC = square(this.diagD);
/*  631 */     this.pc = zeros(this.dimension, 1);
/*  632 */     this.ps = zeros(this.dimension, 1);
/*  633 */     this.normps = this.ps.getFrobeniusNorm();
/*      */     
/*  635 */     this.B = eye(this.dimension, this.dimension);
/*  636 */     this.D = ones(this.dimension, 1);
/*  637 */     this.BD = times(this.B, repmat(this.diagD.transpose(), this.dimension, 1));
/*  638 */     this.C = this.B.multiply(diag(square(this.D)).multiply(this.B.transpose()));
/*  639 */     this.historySize = 10 + (int)((30 * this.dimension) / this.lambda);
/*  640 */     this.fitnessHistory = new double[this.historySize];
/*  641 */     for (j = 0; j < this.historySize; j++) {
/*  642 */       this.fitnessHistory[j] = Double.MAX_VALUE;
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
/*  655 */     this.ps = this.ps.scalarMultiply(1.0D - this.cs).add(this.B.multiply(zmean).scalarMultiply(FastMath.sqrt(this.cs * (2.0D - this.cs) * this.mueff)));
/*      */ 
/*      */     
/*  658 */     this.normps = this.ps.getFrobeniusNorm();
/*  659 */     boolean hsig = (this.normps / FastMath.sqrt(1.0D - FastMath.pow(1.0D - this.cs, 2 * this.iterations)) / this.chiN < 1.4D + 2.0D / (this.dimension + 1.0D));
/*      */ 
/*      */     
/*  662 */     this.pc = this.pc.scalarMultiply(1.0D - this.cc);
/*  663 */     if (hsig) {
/*  664 */       this.pc = this.pc.add(this.xmean.subtract(xold).scalarMultiply(FastMath.sqrt(this.cc * (2.0D - this.cc) * this.mueff) / this.sigma));
/*      */     }
/*  666 */     return hsig;
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
/*  679 */     double oldFac = hsig ? 0.0D : (this.ccov1Sep * this.cc * (2.0D - this.cc));
/*  680 */     oldFac += 1.0D - this.ccov1Sep - this.ccovmuSep;
/*  681 */     this.diagC = this.diagC.scalarMultiply(oldFac).add(square(this.pc).scalarMultiply(this.ccov1Sep)).add(times(this.diagC, square(bestArz).multiply(this.weights)).scalarMultiply(this.ccovmuSep));
/*      */ 
/*      */ 
/*      */     
/*  685 */     this.diagD = sqrt(this.diagC);
/*  686 */     if (this.diagonalOnly > 1 && this.iterations > this.diagonalOnly) {
/*      */ 
/*      */       
/*  689 */       this.diagonalOnly = 0;
/*  690 */       this.B = eye(this.dimension, this.dimension);
/*  691 */       this.BD = diag(this.diagD);
/*  692 */       this.C = diag(this.diagC);
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
/*  710 */     double negccov = 0.0D;
/*  711 */     if (this.ccov1 + this.ccovmu > 0.0D) {
/*  712 */       RealMatrix arpos = bestArx.subtract(repmat(xold, 1, this.mu)).scalarMultiply(1.0D / this.sigma);
/*      */       
/*  714 */       RealMatrix roneu = this.pc.multiply(this.pc.transpose()).scalarMultiply(this.ccov1);
/*      */ 
/*      */       
/*  717 */       double oldFac = hsig ? 0.0D : (this.ccov1 * this.cc * (2.0D - this.cc));
/*  718 */       oldFac += 1.0D - this.ccov1 - this.ccovmu;
/*  719 */       if (this.isActiveCMA) {
/*      */         
/*  721 */         negccov = (1.0D - this.ccovmu) * 0.25D * this.mueff / (FastMath.pow((this.dimension + 2), 1.5D) + 2.0D * this.mueff);
/*      */ 
/*      */ 
/*      */         
/*  725 */         double negminresidualvariance = 0.66D;
/*      */         
/*  727 */         double negalphaold = 0.5D;
/*      */         
/*  729 */         int[] arReverseIndex = reverse(arindex);
/*  730 */         RealMatrix arzneg = selectColumns(arz, MathArrays.copyOf(arReverseIndex, this.mu));
/*  731 */         RealMatrix arnorms = sqrt(sumRows(square(arzneg)));
/*  732 */         int[] idxnorms = sortedIndices(arnorms.getRow(0));
/*  733 */         RealMatrix arnormsSorted = selectColumns(arnorms, idxnorms);
/*  734 */         int[] idxReverse = reverse(idxnorms);
/*  735 */         RealMatrix arnormsReverse = selectColumns(arnorms, idxReverse);
/*  736 */         arnorms = divide(arnormsReverse, arnormsSorted);
/*  737 */         int[] idxInv = inverse(idxnorms);
/*  738 */         RealMatrix arnormsInv = selectColumns(arnorms, idxInv);
/*      */         
/*  740 */         double negcovMax = 0.33999999999999997D / square(arnormsInv).multiply(this.weights).getEntry(0, 0);
/*      */         
/*  742 */         if (negccov > negcovMax) {
/*  743 */           negccov = negcovMax;
/*      */         }
/*  745 */         arzneg = times(arzneg, repmat(arnormsInv, this.dimension, 1));
/*  746 */         RealMatrix artmp = this.BD.multiply(arzneg);
/*  747 */         RealMatrix Cneg = artmp.multiply(diag(this.weights)).multiply(artmp.transpose());
/*  748 */         oldFac += 0.5D * negccov;
/*  749 */         this.C = this.C.scalarMultiply(oldFac).add(roneu).add(arpos.scalarMultiply(this.ccovmu + 0.5D * negccov).multiply(times(repmat(this.weights, 1, this.dimension), arpos.transpose()))).subtract(Cneg.scalarMultiply(negccov));
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/*  758 */         this.C = this.C.scalarMultiply(oldFac).add(roneu).add(arpos.scalarMultiply(this.ccovmu).multiply(times(repmat(this.weights, 1, this.dimension), arpos.transpose())));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  765 */     updateBD(negccov);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateBD(double negccov) {
/*  774 */     if (this.ccov1 + this.ccovmu + negccov > 0.0D && this.iterations % 1.0D / (this.ccov1 + this.ccovmu + negccov) / this.dimension / 10.0D < 1.0D) {
/*      */ 
/*      */       
/*  777 */       this.C = triu(this.C, 0).add(triu(this.C, 1).transpose());
/*      */       
/*  779 */       EigenDecomposition eig = new EigenDecomposition(this.C);
/*  780 */       this.B = eig.getV();
/*  781 */       this.D = eig.getD();
/*  782 */       this.diagD = diag(this.D);
/*  783 */       if (min(this.diagD) <= 0.0D) {
/*  784 */         for (int i = 0; i < this.dimension; i++) {
/*  785 */           if (this.diagD.getEntry(i, 0) < 0.0D) {
/*  786 */             this.diagD.setEntry(i, 0, 0.0D);
/*      */           }
/*      */         } 
/*  789 */         double tfac = max(this.diagD) / 1.0E14D;
/*  790 */         this.C = this.C.add(eye(this.dimension, this.dimension).scalarMultiply(tfac));
/*  791 */         this.diagD = this.diagD.add(ones(this.dimension, 1).scalarMultiply(tfac));
/*      */       } 
/*  793 */       if (max(this.diagD) > 1.0E14D * min(this.diagD)) {
/*  794 */         double tfac = max(this.diagD) / 1.0E14D - min(this.diagD);
/*  795 */         this.C = this.C.add(eye(this.dimension, this.dimension).scalarMultiply(tfac));
/*  796 */         this.diagD = this.diagD.add(ones(this.dimension, 1).scalarMultiply(tfac));
/*      */       } 
/*  798 */       this.diagC = diag(this.C);
/*  799 */       this.diagD = sqrt(this.diagD);
/*  800 */       this.BD = times(this.B, repmat(this.diagD.transpose(), this.dimension, 1));
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
/*  811 */     for (int i = vals.length - 1; i > 0; i--) {
/*  812 */       vals[i] = vals[i - 1];
/*      */     }
/*  814 */     vals[0] = val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] sortedIndices(double[] doubles) {
/*  824 */     DoubleIndex[] dis = new DoubleIndex[doubles.length];
/*  825 */     for (int i = 0; i < doubles.length; i++) {
/*  826 */       dis[i] = new DoubleIndex(doubles[i], i);
/*      */     }
/*  828 */     Arrays.sort((Object[])dis);
/*  829 */     int[] indices = new int[doubles.length];
/*  830 */     for (int j = 0; j < doubles.length; j++) {
/*  831 */       indices[j] = (dis[j]).index;
/*      */     }
/*  833 */     return indices;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double valueRange(ValuePenaltyPair[] vpPairs) {
/*  842 */     double max = Double.NEGATIVE_INFINITY;
/*  843 */     double min = Double.MAX_VALUE;
/*  844 */     for (ValuePenaltyPair vpPair : vpPairs) {
/*  845 */       if (vpPair.value > max) {
/*  846 */         max = vpPair.value;
/*      */       }
/*  848 */       if (vpPair.value < min) {
/*  849 */         min = vpPair.value;
/*      */       }
/*      */     } 
/*  852 */     return max - min;
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
/*  870 */       this.value = value;
/*  871 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(DoubleIndex o) {
/*  876 */       return Double.compare(this.value, o.value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object other) {
/*  883 */       if (this == other) {
/*  884 */         return true;
/*      */       }
/*      */       
/*  887 */       if (other instanceof DoubleIndex) {
/*  888 */         return (Double.compare(this.value, ((DoubleIndex)other).value) == 0);
/*      */       }
/*      */       
/*  891 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  897 */       long bits = Double.doubleToLongBits(this.value);
/*  898 */       return (int)((0x15F34EL ^ bits >>> 32L ^ bits) & 0xFFFFFFFFFFFFFFFFL);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ValuePenaltyPair
/*      */   {
/*      */     private double value;
/*      */ 
/*      */     
/*      */     private double penalty;
/*      */ 
/*      */ 
/*      */     
/*      */     ValuePenaltyPair(double value, double penalty) {
/*  915 */       this.value = value;
/*  916 */       this.penalty = penalty;
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
/*      */   private class FitnessFunction
/*      */   {
/*      */     private final boolean isRepairMode = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CMAESOptimizer.ValuePenaltyPair value(double[] point) {
/*  944 */       double penalty = 0.0D;
/*  945 */       if (this.isRepairMode) {
/*  946 */         double[] repaired = repair(point);
/*  947 */         value = CMAESOptimizer.this.computeObjectiveValue(repaired);
/*  948 */         penalty = penalty(point, repaired);
/*      */       } else {
/*  950 */         value = CMAESOptimizer.this.computeObjectiveValue(point);
/*      */       } 
/*  952 */       double value = CMAESOptimizer.this.isMinimize ? value : -value;
/*  953 */       penalty = CMAESOptimizer.this.isMinimize ? penalty : -penalty;
/*  954 */       return new CMAESOptimizer.ValuePenaltyPair(value, penalty);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isFeasible(double[] x) {
/*  962 */       double[] lB = CMAESOptimizer.this.getLowerBound();
/*  963 */       double[] uB = CMAESOptimizer.this.getUpperBound();
/*      */       
/*  965 */       for (int i = 0; i < x.length; i++) {
/*  966 */         if (x[i] < lB[i]) {
/*  967 */           return false;
/*      */         }
/*  969 */         if (x[i] > uB[i]) {
/*  970 */           return false;
/*      */         }
/*      */       } 
/*  973 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private double[] repair(double[] x) {
/*  981 */       double[] lB = CMAESOptimizer.this.getLowerBound();
/*  982 */       double[] uB = CMAESOptimizer.this.getUpperBound();
/*      */       
/*  984 */       double[] repaired = new double[x.length];
/*  985 */       for (int i = 0; i < x.length; i++) {
/*  986 */         if (x[i] < lB[i]) {
/*  987 */           repaired[i] = lB[i];
/*  988 */         } else if (x[i] > uB[i]) {
/*  989 */           repaired[i] = uB[i];
/*      */         } else {
/*  991 */           repaired[i] = x[i];
/*      */         } 
/*      */       } 
/*  994 */       return repaired;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private double penalty(double[] x, double[] repaired) {
/* 1003 */       double penalty = 0.0D;
/* 1004 */       for (int i = 0; i < x.length; i++) {
/* 1005 */         double diff = FastMath.abs(x[i] - repaired[i]);
/* 1006 */         penalty += diff;
/*      */       } 
/* 1008 */       return CMAESOptimizer.this.isMinimize ? penalty : -penalty;
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
/* 1019 */     double[][] d = new double[m.getRowDimension()][m.getColumnDimension()];
/* 1020 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1021 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1022 */         d[r][c] = FastMath.log(m.getEntry(r, c));
/*      */       }
/*      */     } 
/* 1025 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix sqrt(RealMatrix m) {
/* 1033 */     double[][] d = new double[m.getRowDimension()][m.getColumnDimension()];
/* 1034 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1035 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1036 */         d[r][c] = FastMath.sqrt(m.getEntry(r, c));
/*      */       }
/*      */     } 
/* 1039 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix square(RealMatrix m) {
/* 1047 */     double[][] d = new double[m.getRowDimension()][m.getColumnDimension()];
/* 1048 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1049 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1050 */         double e = m.getEntry(r, c);
/* 1051 */         d[r][c] = e * e;
/*      */       } 
/*      */     } 
/* 1054 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix times(RealMatrix m, RealMatrix n) {
/* 1063 */     double[][] d = new double[m.getRowDimension()][m.getColumnDimension()];
/* 1064 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1065 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1066 */         d[r][c] = m.getEntry(r, c) * n.getEntry(r, c);
/*      */       }
/*      */     } 
/* 1069 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix divide(RealMatrix m, RealMatrix n) {
/* 1078 */     double[][] d = new double[m.getRowDimension()][m.getColumnDimension()];
/* 1079 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1080 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1081 */         d[r][c] = m.getEntry(r, c) / n.getEntry(r, c);
/*      */       }
/*      */     } 
/* 1084 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix selectColumns(RealMatrix m, int[] cols) {
/* 1093 */     double[][] d = new double[m.getRowDimension()][cols.length];
/* 1094 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1095 */       for (int c = 0; c < cols.length; c++) {
/* 1096 */         d[r][c] = m.getEntry(r, cols[c]);
/*      */       }
/*      */     } 
/* 1099 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix triu(RealMatrix m, int k) {
/* 1108 */     double[][] d = new double[m.getRowDimension()][m.getColumnDimension()];
/* 1109 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1110 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1111 */         d[r][c] = (r <= c - k) ? m.getEntry(r, c) : 0.0D;
/*      */       }
/*      */     } 
/* 1114 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix sumRows(RealMatrix m) {
/* 1122 */     double[][] d = new double[1][m.getColumnDimension()];
/* 1123 */     for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1124 */       double sum = 0.0D;
/* 1125 */       for (int r = 0; r < m.getRowDimension(); r++) {
/* 1126 */         sum += m.getEntry(r, c);
/*      */       }
/* 1128 */       d[0][c] = sum;
/*      */     } 
/* 1130 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix diag(RealMatrix m) {
/* 1139 */     if (m.getColumnDimension() == 1) {
/* 1140 */       double[][] arrayOfDouble = new double[m.getRowDimension()][m.getRowDimension()];
/* 1141 */       for (int j = 0; j < m.getRowDimension(); j++) {
/* 1142 */         arrayOfDouble[j][j] = m.getEntry(j, 0);
/*      */       }
/* 1144 */       return (RealMatrix)new Array2DRowRealMatrix(arrayOfDouble, false);
/*      */     } 
/* 1146 */     double[][] d = new double[m.getRowDimension()][1];
/* 1147 */     for (int i = 0; i < m.getColumnDimension(); i++) {
/* 1148 */       d[i][0] = m.getEntry(i, i);
/*      */     }
/* 1150 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
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
/* 1164 */     for (int i = 0; i < m1.getRowDimension(); i++) {
/* 1165 */       m2.setEntry(i, col2, m1.getEntry(i, col1));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix ones(int n, int m) {
/* 1175 */     double[][] d = new double[n][m];
/* 1176 */     for (int r = 0; r < n; r++) {
/* 1177 */       Arrays.fill(d[r], 1.0D);
/*      */     }
/* 1179 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix eye(int n, int m) {
/* 1189 */     double[][] d = new double[n][m];
/* 1190 */     for (int r = 0; r < n; r++) {
/* 1191 */       if (r < m) {
/* 1192 */         d[r][r] = 1.0D;
/*      */       }
/*      */     } 
/* 1195 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix zeros(int n, int m) {
/* 1204 */     return (RealMatrix)new Array2DRowRealMatrix(n, m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix repmat(RealMatrix mat, int n, int m) {
/* 1214 */     int rd = mat.getRowDimension();
/* 1215 */     int cd = mat.getColumnDimension();
/* 1216 */     double[][] d = new double[n * rd][m * cd];
/* 1217 */     for (int r = 0; r < n * rd; r++) {
/* 1218 */       for (int c = 0; c < m * cd; c++) {
/* 1219 */         d[r][c] = mat.getEntry(r % rd, c % cd);
/*      */       }
/*      */     } 
/* 1222 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static RealMatrix sequence(double start, double end, double step) {
/* 1232 */     int size = (int)((end - start) / step + 1.0D);
/* 1233 */     double[][] d = new double[size][1];
/* 1234 */     double value = start;
/* 1235 */     for (int r = 0; r < size; r++) {
/* 1236 */       d[r][0] = value;
/* 1237 */       value += step;
/*      */     } 
/* 1239 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double max(RealMatrix m) {
/* 1247 */     double max = -1.7976931348623157E308D;
/* 1248 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1249 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1250 */         double e = m.getEntry(r, c);
/* 1251 */         if (max < e) {
/* 1252 */           max = e;
/*      */         }
/*      */       } 
/*      */     } 
/* 1256 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double min(RealMatrix m) {
/* 1264 */     double min = Double.MAX_VALUE;
/* 1265 */     for (int r = 0; r < m.getRowDimension(); r++) {
/* 1266 */       for (int c = 0; c < m.getColumnDimension(); c++) {
/* 1267 */         double e = m.getEntry(r, c);
/* 1268 */         if (min > e) {
/* 1269 */           min = e;
/*      */         }
/*      */       } 
/*      */     } 
/* 1273 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double max(double[] m) {
/* 1281 */     double max = -1.7976931348623157E308D;
/* 1282 */     for (int r = 0; r < m.length; r++) {
/* 1283 */       if (max < m[r]) {
/* 1284 */         max = m[r];
/*      */       }
/*      */     } 
/* 1287 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double min(double[] m) {
/* 1295 */     double min = Double.MAX_VALUE;
/* 1296 */     for (int r = 0; r < m.length; r++) {
/* 1297 */       if (min > m[r]) {
/* 1298 */         min = m[r];
/*      */       }
/*      */     } 
/* 1301 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] inverse(int[] indices) {
/* 1309 */     int[] inverse = new int[indices.length];
/* 1310 */     for (int i = 0; i < indices.length; i++) {
/* 1311 */       inverse[indices[i]] = i;
/*      */     }
/* 1313 */     return inverse;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] reverse(int[] indices) {
/* 1321 */     int[] reverse = new int[indices.length];
/* 1322 */     for (int i = 0; i < indices.length; i++) {
/* 1323 */       reverse[i] = indices[indices.length - i - 1];
/*      */     }
/* 1325 */     return reverse;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double[] randn(int size) {
/* 1333 */     double[] randn = new double[size];
/* 1334 */     for (int i = 0; i < size; i++) {
/* 1335 */       randn[i] = this.random.nextGaussian();
/*      */     }
/* 1337 */     return randn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RealMatrix randn1(int size, int popSize) {
/* 1346 */     double[][] d = new double[size][popSize];
/* 1347 */     for (int r = 0; r < size; r++) {
/* 1348 */       for (int c = 0; c < popSize; c++) {
/* 1349 */         d[r][c] = this.random.nextGaussian();
/*      */       }
/*      */     } 
/* 1352 */     return (RealMatrix)new Array2DRowRealMatrix(d, false);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/noderiv/CMAESOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */