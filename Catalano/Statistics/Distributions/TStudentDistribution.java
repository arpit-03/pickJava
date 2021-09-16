/*     */ package Catalano.Statistics.Distributions;
/*     */ 
/*     */ import Catalano.Math.Functions.Beta;
/*     */ import Catalano.Math.Functions.Gamma;
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
/*     */ public class TStudentDistribution
/*     */   implements IDistribution
/*     */ {
/*     */   private double constant;
/*     */   private double degreesOfFreedom;
/*     */   
/*     */   public double getDegreesOfFreedom() {
/*  44 */     return this.degreesOfFreedom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDegreesOfFreedom(double degreesOfFreedom) {
/*  52 */     this.degreesOfFreedom = degreesOfFreedom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TStudentDistribution(double degreesOfFreedom) {
/*  60 */     if (degreesOfFreedom < 1.0D) {
/*     */       try {
/*  62 */         throw new IllegalArgumentException("degreesOfFreedom");
/*  63 */       } catch (Exception e) {
/*  64 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  68 */     this.degreesOfFreedom = degreesOfFreedom;
/*     */     
/*  70 */     double v = degreesOfFreedom;
/*     */ 
/*     */     
/*  73 */     this.constant = Gamma.Function((v + 1.0D) / 2.0D) / Math.sqrt(v * Math.PI) * Gamma.Function(v / 2.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public double Mean() {
/*  78 */     return (this.degreesOfFreedom > 1.0D) ? 0.0D : Double.NaN;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Variance() {
/*  83 */     if (this.degreesOfFreedom > 2.0D)
/*  84 */       return this.degreesOfFreedom / (this.degreesOfFreedom - 2.0D); 
/*  85 */     if (this.degreesOfFreedom > 1.0D)
/*  86 */       return Double.POSITIVE_INFINITY; 
/*  87 */     return Double.NaN;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Entropy() {
/*  92 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   
/*     */   public double DistributionFunction(double x) {
/*  97 */     double v = this.degreesOfFreedom;
/*  98 */     double sqrt = Math.sqrt(x * x + v);
/*  99 */     double u = (x + sqrt) / 2.0D * sqrt;
/* 100 */     return Beta.Incomplete(v / 2.0D, v / 2.0D, u);
/*     */   }
/*     */ 
/*     */   
/*     */   public double ProbabilityDensityFunction(double x) {
/* 105 */     double v = this.degreesOfFreedom;
/* 106 */     return this.constant * Math.pow(1.0D + x * x / this.degreesOfFreedom, -(v + 1.0D) / 2.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public double LogProbabilityDensityFunction(double x) {
/* 111 */     double v = this.degreesOfFreedom;
/* 112 */     return Math.log(this.constant) - (v + 1.0D) / 2.0D * Math.log(1.0D + x * x / this.degreesOfFreedom);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/TStudentDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */