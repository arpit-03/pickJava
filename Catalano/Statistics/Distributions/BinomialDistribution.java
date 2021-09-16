/*     */ package Catalano.Statistics.Distributions;
/*     */ 
/*     */ import Catalano.Math.Functions.Beta;
/*     */ import Catalano.Math.Special;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BinomialDistribution
/*     */   implements IDiscreteDistribution
/*     */ {
/*     */   private int numberOfTrials;
/*     */   private double probability;
/*     */   
/*     */   public int getNumberOfTrials() {
/*  50 */     return this.numberOfTrials;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getProbability() {
/*  58 */     return this.probability;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinomialDistribution(int trials) {
/*  66 */     this(trials, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinomialDistribution(int trials, double probability) {
/*  76 */     if (trials <= 0) {
/*  77 */       throw new IllegalArgumentException("The number of trials should be greater than zero.");
/*     */     }
/*  79 */     if (probability < 0.0D || probability > 1.0D) {
/*  80 */       throw new IllegalArgumentException("A probability must be between 0 and 1.");
/*     */     }
/*  82 */     this.numberOfTrials = trials;
/*  83 */     this.probability = probability;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Mean() {
/*  88 */     return this.numberOfTrials * this.probability;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Variance() {
/*  93 */     return this.numberOfTrials * this.probability * (1.0D - this.probability);
/*     */   }
/*     */ 
/*     */   
/*     */   public double Entropy() {
/*  98 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public double DistributionFunction(int k) {
/* 103 */     return Beta.Incomplete((this.numberOfTrials - k), (k + 1), 1.0D - this.probability);
/*     */   }
/*     */ 
/*     */   
/*     */   public double ProbabilityMassFunction(int k) {
/* 108 */     if (k < 0 || k > this.numberOfTrials) return 0.0D; 
/* 109 */     return Special.Binomial(this.numberOfTrials, k) * Math.pow(this.probability, k) * Math.pow(1.0D - this.probability, (this.numberOfTrials - k));
/*     */   }
/*     */ 
/*     */   
/*     */   public double LogProbabilityMassFunction(int k) {
/* 114 */     if (k < 0 || k > this.numberOfTrials) return Double.NEGATIVE_INFINITY; 
/* 115 */     return Special.LogBinomial(this.numberOfTrials, k) + k * Math.log(this.probability) + (this.numberOfTrials - k) * Math.log(1.0D - this.probability);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/BinomialDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */