/*     */ package Catalano.Statistics.Distributions;
/*     */ 
/*     */ import Catalano.Math.Functions.Beta;
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
/*     */ public class FisherDistribution
/*     */   implements IDistribution
/*     */ {
/*     */   private int d1;
/*     */   private int d2;
/*     */   private double b;
/*     */   private Double mean;
/*     */   private Double variance;
/*     */   
/*     */   public FisherDistribution(int degrees1, int degrees2) {
/*  51 */     if (degrees1 <= 0) {
/*     */       try {
/*  53 */         throw new IllegalArgumentException("Degrees of freedom must be positive.");
/*  54 */       } catch (Exception e) {
/*  55 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  59 */     if (degrees2 <= 0) {
/*     */       try {
/*  61 */         throw new IllegalArgumentException("Degrees of freedom must be positive.");
/*  62 */       } catch (Exception e) {
/*  63 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  67 */     this.d1 = degrees1;
/*  68 */     this.d2 = degrees2;
/*     */     
/*  70 */     this.b = Beta.Function(degrees1 * 0.5D, degrees2 * 0.5D);
/*     */   }
/*     */   
/*     */   public int getDegreesOfFreedom1() {
/*  74 */     return this.d1;
/*     */   }
/*     */   
/*     */   public int getDegreesOfFreedom2() {
/*  78 */     return this.d2;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Mean() {
/*  83 */     if (this.mean == null)
/*     */     {
/*  85 */       if (this.d2 <= 2) {
/*     */         
/*  87 */         this.mean = Double.valueOf(Double.NaN);
/*     */       }
/*     */       else {
/*     */         
/*  91 */         this.mean = Double.valueOf(this.d2 / (this.d2 - 2.0D));
/*     */       } 
/*     */     }
/*  94 */     return this.mean.doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public double Variance() {
/*  99 */     if (this.variance == null) {
/* 100 */       if (this.d2 <= 4) {
/*     */         
/* 102 */         this.variance = Double.valueOf(Double.NaN);
/*     */       }
/*     */       else {
/*     */         
/* 106 */         this.variance = 
/* 107 */           Double.valueOf(2.0D * this.d2 * this.d2 * (this.d1 + this.d2 - 2) / (this.d1 * (this.d2 - 2) * (this.d2 - 2) * (this.d2 - 4)));
/*     */       } 
/*     */     }
/*     */     
/* 111 */     return this.variance.doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public double Entropy() {
/* 116 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   
/*     */   public double DistributionFunction(double x) {
/* 121 */     double u = this.d1 * x / (this.d1 * x + this.d2);
/* 122 */     return Beta.Incomplete(this.d1 * 0.5D, this.d2 * 0.5D, u);
/*     */   }
/*     */   
/*     */   public double ComplementaryDistributionFunction(double x) {
/* 126 */     double u = this.d1 / (this.d1 * x + this.d2);
/* 127 */     return Beta.Incomplete(this.d2 * 0.5D, this.d1 * 0.5D, u);
/*     */   }
/*     */ 
/*     */   
/*     */   public double ProbabilityDensityFunction(double x) {
/* 132 */     double u = Math.pow(this.d1 * x, this.d1) * Math.pow(this.d2, this.d2) / 
/* 133 */       Math.pow(this.d1 * x + this.d2, (this.d1 + this.d2));
/* 134 */     return Math.sqrt(u) / x * this.b;
/*     */   }
/*     */ 
/*     */   
/*     */   public double LogProbabilityDensityFunction(double x) {
/* 139 */     double lnu = this.d1 * Math.log(this.d1 * x) + this.d2 * Math.log(this.d2) - (
/* 140 */       this.d1 + this.d2) * Math.log(this.d1 * x + this.d2);
/* 141 */     return 0.5D * lnu - Math.log(x * this.b);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/FisherDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */