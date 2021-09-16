/*     */ package Catalano.Statistics.Distributions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContinuousUniformDistribution
/*     */   implements IDistribution
/*     */ {
/*     */   private double a;
/*     */   private double b;
/*     */   
/*     */   public ContinuousUniformDistribution() {
/*  40 */     this(0.0D, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContinuousUniformDistribution(double a, double b) {
/*  50 */     if (a > b) {
/*     */       try {
/*  52 */         throw new IllegalArgumentException("The starting number a must be lower than b.");
/*  53 */       } catch (Exception e) {
/*  54 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  58 */     this.a = a;
/*  59 */     this.b = b;
/*     */   }
/*     */   
/*     */   public double Length() {
/*  63 */     return this.b - this.a;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Mean() {
/*  68 */     return (this.a + this.b) / 2.0D;
/*     */   }
/*     */   
/*     */   public double Minimum() {
/*  72 */     return this.a;
/*     */   }
/*     */   
/*     */   public double Maximum() {
/*  76 */     return this.b;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Variance() {
/*  81 */     return (this.b - this.a) * (this.b - this.a) / 12.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Entropy() {
/*  86 */     return Math.log(this.b - this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   public double DistributionFunction(double x) {
/*  91 */     if (x < this.a)
/*  92 */       return 0.0D; 
/*  93 */     if (x >= this.b)
/*  94 */       return 1.0D; 
/*  95 */     return (x - this.a) / (this.b - this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   public double ProbabilityDensityFunction(double x) {
/* 100 */     if (x >= this.a && x <= this.b)
/* 101 */       return 1.0D / (this.b - this.a); 
/* 102 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double LogProbabilityDensityFunction(double x) {
/* 107 */     if (x >= this.a && x <= this.b)
/* 108 */       return -Math.log(this.b - this.a); 
/* 109 */     return Double.NEGATIVE_INFINITY;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/ContinuousUniformDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */