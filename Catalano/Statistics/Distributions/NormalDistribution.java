/*     */ package Catalano.Statistics.Distributions;
/*     */ 
/*     */ import Catalano.Math.Functions.Normal;
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
/*     */ public class NormalDistribution
/*     */   implements IDistribution
/*     */ {
/*  38 */   private double mean = 0.0D;
/*  39 */   private double stdDev = 1.0D;
/*     */ 
/*     */   
/*     */   private Double entropy;
/*     */ 
/*     */   
/*  45 */   private double variance = 1.0D;
/*     */   
/*     */   private double lnconstant;
/*     */   
/*  49 */   private final double p95 = 1.9599639845400543D;
/*     */   
/*     */   public NormalDistribution() {
/*  52 */     init(this.mean, this.stdDev, this.stdDev * this.stdDev);
/*     */   }
/*     */   
/*     */   public NormalDistribution(double mean) {
/*  56 */     init(mean, this.stdDev, this.stdDev * this.stdDev);
/*     */   }
/*     */ 
/*     */   
/*     */   public NormalDistribution(double mean, double stdDev) {
/*  61 */     if (stdDev <= 0.0D) {
/*     */       try {
/*  63 */         throw new IllegalArgumentException("Standard deviation must be positive.");
/*  64 */       } catch (Exception e) {
/*  65 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  69 */     init(mean, stdDev, stdDev * stdDev);
/*     */   }
/*     */   
/*     */   private void init(double mu, double dev, double var) {
/*  73 */     this.mean = mu;
/*  74 */     this.stdDev = dev;
/*  75 */     this.variance = var;
/*     */ 
/*     */     
/*  78 */     this.lnconstant = -Math.log(2.5066282746310007D * dev);
/*     */   }
/*     */ 
/*     */   
/*     */   public double Mean() {
/*  83 */     return this.mean;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Variance() {
/*  88 */     return this.variance;
/*     */   }
/*     */   
/*     */   public double StandartDeviation() {
/*  92 */     return this.stdDev;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Entropy() {
/*  97 */     if (this.entropy == null) {
/*  98 */       this.entropy = Double.valueOf(0.5D * (Math.log(6.283185307179586D * this.variance) + 1.0D));
/*     */     }
/*     */     
/* 101 */     return this.entropy.doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public double DistributionFunction(double x) {
/* 106 */     double z = (x - this.mean) / this.stdDev;
/* 107 */     return Special.Erfc(-z / 1.4142135623730951D) * 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double InverseDistributionFunction(double p) {
/* 117 */     return this.mean + this.stdDev * Normal.Inverse(p);
/*     */   }
/*     */ 
/*     */   
/*     */   public double ProbabilityDensityFunction(double x) {
/* 122 */     double z = (x - this.mean) / this.stdDev;
/* 123 */     double lnp = this.lnconstant - z * z * 0.5D;
/*     */     
/* 125 */     return Math.exp(lnp);
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
/*     */   public double LogProbabilityDensityFunction(double x) {
/* 140 */     double z = (x - this.mean) / this.stdDev;
/* 141 */     double lnp = this.lnconstant - z * z * 0.5D;
/*     */     
/* 143 */     return lnp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double ZScore(double x) {
/* 152 */     return (x - this.mean) / this.stdDev;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/NormalDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */