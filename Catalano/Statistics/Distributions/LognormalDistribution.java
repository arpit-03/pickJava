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
/*     */ public class LognormalDistribution
/*     */   implements IDistribution
/*     */ {
/*  38 */   private double location = 0.0D;
/*  39 */   private double shape = 1.0D;
/*     */   
/*     */   private Double mean;
/*     */   
/*     */   private Double variance;
/*     */   
/*     */   private Double entropy;
/*     */   
/*     */   private double shape2;
/*     */   private double constant;
/*     */   
/*     */   public LognormalDistribution() {
/*  51 */     init(this.location, this.shape, this.shape * this.shape);
/*     */   }
/*     */   
/*     */   public LognormalDistribution(double location) {
/*  55 */     init(location, this.shape, this.shape * this.shape);
/*     */   }
/*     */   
/*     */   public LognormalDistribution(double location, double shape) {
/*  59 */     init(location, shape, shape * shape);
/*     */   }
/*     */   
/*     */   private void init(double mu, double dev, double var) {
/*  63 */     this.location = mu;
/*  64 */     this.shape = dev;
/*  65 */     this.shape2 = var;
/*     */ 
/*     */     
/*  68 */     this.constant = 1.0D / 2.5066282746310007D * dev;
/*     */   }
/*     */   
/*     */   public double getShape() {
/*  72 */     return this.shape;
/*     */   }
/*     */   
/*     */   public double getLocation() {
/*  76 */     return this.location;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Mean() {
/*  81 */     if (this.mean == null)
/*  82 */       this.mean = Double.valueOf(Math.exp(this.location + this.shape2 / 2.0D)); 
/*  83 */     return this.mean.doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public double Variance() {
/*  88 */     if (this.variance == null)
/*  89 */       this.variance = Double.valueOf((Math.exp(this.shape2) - 1.0D) * Math.exp(2.0D * this.location + this.shape2)); 
/*  90 */     return this.variance.doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public double Entropy() {
/*  95 */     if (this.entropy == null)
/*  96 */       this.entropy = Double.valueOf(0.5D + 0.5D * Math.log(6.283185307179586D * this.shape2) + this.location); 
/*  97 */     return this.entropy.doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public double DistributionFunction(double x) {
/* 102 */     double z = (Math.log(x) - this.location) / this.shape;
/* 103 */     return 0.5D * Special.Erfc(-z / 1.4142135623730951D);
/*     */   }
/*     */ 
/*     */   
/*     */   public double ProbabilityDensityFunction(double x) {
/* 108 */     double z = (Math.log(x) - this.location) / this.shape;
/* 109 */     return this.constant * Math.exp(-z * z * 0.5D) / x;
/*     */   }
/*     */ 
/*     */   
/*     */   public double LogProbabilityDensityFunction(double x) {
/* 114 */     double z = (Math.log(x) - this.location) / this.shape;
/* 115 */     return Math.log(this.constant) + -z * z * 0.5D - Math.log(x);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/LognormalDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */