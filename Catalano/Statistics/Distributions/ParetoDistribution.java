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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParetoDistribution
/*     */   implements IDistribution
/*     */ {
/*     */   private double xm;
/*     */   private double a;
/*     */   
/*     */   public ParetoDistribution(double scale, double shape) {
/*  47 */     this.xm = scale;
/*  48 */     this.a = shape;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Mean() {
/*  53 */     return this.a * this.xm / (this.a - 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public double Variance() {
/*  58 */     return this.xm * this.xm * this.a / (this.a - 1.0D) * (this.a - 1.0D) * (this.a - 2.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public double Entropy() {
/*  63 */     return Math.log(this.xm / this.a) + 1.0D / this.a + 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double Mode() {
/*  71 */     return this.xm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double Median() {
/*  79 */     return this.xm * Math.pow(2.0D, 1.0D / this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   public double DistributionFunction(double x) {
/*  84 */     if (x >= this.xm)
/*  85 */       return 1.0D - Math.pow(this.xm / x, this.a); 
/*  86 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double ProbabilityDensityFunction(double x) {
/*  91 */     if (x >= this.xm)
/*  92 */       return this.a * Math.pow(this.xm, this.a) / Math.pow(x, this.a + 1.0D); 
/*  93 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double LogProbabilityDensityFunction(double x) {
/*  98 */     if (x >= this.xm)
/*  99 */       return Math.log(this.a) + this.a * Math.log(this.xm) - (this.a + 1.0D) * Math.log(x); 
/* 100 */     return 0.0D;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/ParetoDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */