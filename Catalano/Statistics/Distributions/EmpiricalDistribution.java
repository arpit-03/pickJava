/*     */ package Catalano.Statistics.Distributions;
/*     */ 
/*     */ import Catalano.Statistics.Tools;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EmpiricalDistribution
/*     */   implements IDistribution
/*     */ {
/*     */   private double[] samples;
/*     */   private Double smoothing;
/*     */   private Double mean;
/*     */   private Double variance;
/*     */   private Double entropy;
/*     */   
/*     */   public EmpiricalDistribution(double[] samples) {
/*  50 */     initialize(samples, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EmpiricalDistribution(double[] samples, double smoothing) {
/*  59 */     initialize(samples, Double.valueOf(smoothing));
/*     */   }
/*     */   
/*     */   private void initialize(double[] observations, Double smoothing) {
/*  63 */     if (smoothing == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  68 */       double sigma = Tools.StandartDeviation(observations);
/*  69 */       smoothing = Double.valueOf(sigma * Math.pow(4.0D / 3.0D * observations.length, -0.2D));
/*     */     } 
/*     */     
/*  72 */     this.samples = observations;
/*  73 */     this.smoothing = Double.valueOf(smoothing.doubleValue());
/*     */     
/*  75 */     this.mean = null;
/*  76 */     this.variance = null;
/*     */   }
/*     */   
/*     */   public double[] getSamples() {
/*  80 */     return this.samples;
/*     */   }
/*     */   
/*     */   public double getSmoothing() {
/*  84 */     return this.smoothing.doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public double Mean() {
/*  89 */     if (this.mean == null)
/*  90 */       this.mean = Double.valueOf(Tools.Mean(this.samples)); 
/*  91 */     return this.mean.doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public double Variance() {
/*  96 */     if (this.variance == null)
/*  97 */       this.variance = Double.valueOf(Tools.Variance(this.samples)); 
/*  98 */     return this.variance.doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public double Entropy() {
/* 103 */     if (this.entropy == null) {
/*     */       
/* 105 */       this.entropy = Double.valueOf(0.0D);
/* 106 */       for (int i = 0; i < this.samples.length; i++) {
/*     */         
/* 108 */         double p = ProbabilityDensityFunction(this.samples[i]);
/*     */         
/* 110 */         this.entropy = Double.valueOf(this.entropy.doubleValue() + p * Math.log(p));
/*     */       } 
/*     */     } 
/* 113 */     return this.entropy.doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public double DistributionFunction(double x) {
/* 118 */     int sum = 0;
/* 119 */     for (int i = 0; i < this.samples.length; i++) {
/* 120 */       if (this.samples[i] <= x) sum++; 
/* 121 */     }  return sum / this.samples.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public double ProbabilityDensityFunction(double x) {
/* 126 */     double p = 0.0D;
/*     */     
/* 128 */     for (int i = 0; i < this.samples.length; i++) {
/*     */       
/* 130 */       double z = (x - this.samples[i]) / this.smoothing.doubleValue();
/* 131 */       p += Math.exp(-z * z * 0.5D);
/*     */     } 
/*     */     
/* 134 */     p *= 1.0D / 2.5066282746310007D * this.smoothing.doubleValue();
/*     */     
/* 136 */     return p / this.samples.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public double LogProbabilityDensityFunction(double x) {
/* 141 */     double p = 0.0D;
/*     */     
/* 143 */     for (int i = 0; i < this.samples.length; i++) {
/*     */       
/* 145 */       double z = (x - this.samples[i]) / this.smoothing.doubleValue();
/* 146 */       p += Math.exp(-z * z * 0.5D);
/*     */     } 
/*     */     
/* 149 */     double logp = Math.log(p) - Math.log(2.5066282746310007D * this.smoothing.doubleValue());
/*     */     
/* 151 */     return logp - Math.log(this.samples.length);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/EmpiricalDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */