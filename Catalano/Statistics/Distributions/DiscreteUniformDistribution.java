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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiscreteUniformDistribution
/*     */   implements IDiscreteDistribution
/*     */ {
/*     */   private int a;
/*     */   private int b;
/*     */   private int n;
/*     */   
/*     */   public double getMinimum() {
/*  45 */     return this.a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaximum() {
/*  53 */     return this.a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLength() {
/*  61 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DiscreteUniformDistribution(int a, int b) {
/*  70 */     if (a > b) {
/*  71 */       throw new IllegalArgumentException("The starting number a must be lower than b.");
/*     */     }
/*  73 */     this.a = a;
/*  74 */     this.b = b;
/*  75 */     this.n = b - a + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Mean() {
/*  80 */     return (this.a + this.b) / 2.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Variance() {
/*  85 */     return ((this.b - this.a) * (this.b - this.a)) / 12.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Entropy() {
/*  90 */     return Math.log((this.b - this.a));
/*     */   }
/*     */ 
/*     */   
/*     */   public double DistributionFunction(int k) {
/*  95 */     if (k < this.a)
/*  96 */       return 0.0D; 
/*  97 */     if (k >= this.b)
/*  98 */       return 1.0D; 
/*  99 */     return ((k - this.a) + 1.0D) / this.n;
/*     */   }
/*     */ 
/*     */   
/*     */   public double ProbabilityMassFunction(int k) {
/* 104 */     if (k >= this.a && k <= this.b)
/* 105 */       return 1.0D / this.n; 
/* 106 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double LogProbabilityMassFunction(int k) {
/* 111 */     if (k >= this.a && k <= this.b)
/* 112 */       return -Math.log(this.n); 
/* 113 */     return Double.NEGATIVE_INFINITY;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/DiscreteUniformDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */