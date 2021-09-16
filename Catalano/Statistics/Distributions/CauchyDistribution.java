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
/*     */ public class CauchyDistribution
/*     */   implements IDistribution
/*     */ {
/*     */   private double location;
/*     */   private double scale;
/*     */   private double lnconstant;
/*     */   private double constant;
/*     */   private boolean immutable;
/*     */   
/*     */   public CauchyDistribution() {
/*  44 */     this(0.0D, 1.0D);
/*     */   }
/*     */   
/*     */   public CauchyDistribution(double location, double scale) {
/*  48 */     if (scale < 0.0D) {
/*     */       try {
/*  50 */         throw new IllegalArgumentException("Scale must be greater than zero.");
/*  51 */       } catch (Exception e) {
/*  52 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*  55 */     Init(location, scale);
/*     */   }
/*     */   
/*     */   private void Init(double location, double scale) {
/*  59 */     this.location = location;
/*  60 */     this.scale = scale;
/*     */     
/*  62 */     this.constant = 1.0D / Math.PI * scale;
/*  63 */     this.lnconstant = -Math.log(Math.PI * scale);
/*     */   }
/*     */   
/*     */   public double getScale() {
/*  67 */     return this.scale;
/*     */   }
/*     */   
/*     */   public double getLocation() {
/*  71 */     return this.location;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Mean() {
/*  76 */     return Double.NaN;
/*     */   }
/*     */   
/*     */   public double Median() {
/*  80 */     return this.location;
/*     */   }
/*     */   
/*     */   public double Mode() {
/*  84 */     return this.location;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Variance() {
/*  89 */     return Double.NaN;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Entropy() {
/*  94 */     return Math.log(this.scale) + Math.log(12.566370614359172D);
/*     */   }
/*     */ 
/*     */   
/*     */   public double DistributionFunction(double x) {
/*  99 */     return 0.3183098861837907D * Math.atan2(x - this.location, this.scale) + 0.5D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double ProbabilityDensityFunction(double x) {
/* 104 */     double z = (x - this.location) / this.scale;
/* 105 */     return this.constant / (1.0D + z * z);
/*     */   }
/*     */ 
/*     */   
/*     */   public double LogProbabilityDensityFunction(double x) {
/* 110 */     double z = (x - this.location) / this.scale;
/* 111 */     return this.lnconstant - Math.log(1.0D + z * z);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/CauchyDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */