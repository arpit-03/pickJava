/*     */ package Catalano.Statistics.Distributions;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InverseGammaDistribution
/*     */   implements IDistribution
/*     */ {
/*     */   private double mean;
/*     */   private double lambda;
/*     */   
/*     */   public InverseGammaDistribution(double mean, double shape) {
/*  54 */     if (mean <= 0.0D || shape <= 0.0D) {
/*     */       try {
/*  56 */         throw new IllegalArgumentException("Mean or shape must be greater than 0");
/*  57 */       } catch (Exception e) {
/*  58 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  62 */     this.mean = mean;
/*  63 */     this.lambda = shape;
/*     */   }
/*     */   
/*     */   public double getShape() {
/*  67 */     return this.lambda;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Variance() {
/*  72 */     return this.mean * this.mean * this.mean / this.lambda;
/*     */   }
/*     */ 
/*     */   
/*     */   public double DistributionFunction(double x) {
/*  77 */     double sqrt = Math.sqrt(this.lambda / x);
/*     */     
/*  79 */     double a = 0.5D * Special.Erfc(sqrt * (this.mean - x) / 1.4142135623730951D * this.mean);
/*  80 */     double b = 0.5D * Special.Erfc(sqrt * (this.mean + x) / 1.4142135623730951D * this.mean);
/*  81 */     double c = Math.exp(2.0D * this.lambda / this.mean);
/*     */     
/*  83 */     return a + b * c;
/*     */   }
/*     */ 
/*     */   
/*     */   public double ProbabilityDensityFunction(double x) {
/*  88 */     double a = Math.sqrt(this.lambda / 6.283185307179586D * x * x * x);
/*  89 */     double b = -this.lambda * (x - this.mean) * (x - this.mean) / 2.0D * this.mean * this.mean * x;
/*     */     
/*  91 */     return a * Math.exp(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public double LogProbabilityDensityFunction(double x) {
/*  96 */     double a = Math.sqrt(this.lambda / 6.283185307179586D * x * x * x);
/*  97 */     double b = -this.lambda * (x - this.mean) * (x - this.mean) / 2.0D * this.mean * this.mean * x;
/*     */     
/*  99 */     return Math.log(a) + b;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Mean() {
/* 104 */     return this.mean;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Entropy() {
/* 109 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/InverseGammaDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */