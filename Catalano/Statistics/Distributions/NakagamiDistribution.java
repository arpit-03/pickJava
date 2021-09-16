/*     */ package Catalano.Statistics.Distributions;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NakagamiDistribution
/*     */   implements IDistribution
/*     */ {
/*     */   private double mu;
/*     */   private double omega;
/*     */   private Double mean;
/*     */   private Double variance;
/*     */   private double constant;
/*     */   private double nratio;
/*     */   private double twoMu1;
/*     */   
/*     */   public NakagamiDistribution(double shape, double spread) {
/*  54 */     if (shape < 0.5D) {
/*     */       try {
/*  56 */         throw new IllegalArgumentException("Shape parameter (mu) should be greater than or equal to 0.5.");
/*  57 */       } catch (Exception e) {
/*  58 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  62 */     if (spread <= 0.0D) {
/*     */       try {
/*  64 */         throw new IllegalArgumentException("Spread parameter (omega) should be greater than 0.");
/*  65 */       } catch (Exception e) {
/*  66 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  70 */     this.mu = shape;
/*  71 */     this.omega = spread;
/*     */     
/*  73 */     init(shape, spread);
/*     */   }
/*     */   
/*     */   private void init(double shape, double spread) {
/*  77 */     double twoMuMu = 2.0D * Math.pow(shape, shape);
/*  78 */     double gammaMu = Gamma.Function(shape);
/*  79 */     double spreadMu = Math.pow(spread, shape);
/*  80 */     this.nratio = -shape / spread;
/*  81 */     this.twoMu1 = 2.0D * shape - 1.0D;
/*     */     
/*  83 */     this.constant = twoMuMu / gammaMu * spreadMu;
/*     */     
/*  85 */     this.mean = null;
/*  86 */     this.variance = null;
/*     */   }
/*     */   
/*     */   public double getShape() {
/*  90 */     return this.mu;
/*     */   }
/*     */   
/*     */   public double getSpread() {
/*  94 */     return this.omega;
/*     */   }
/*     */ 
/*     */   
/*     */   public double Mean() {
/*  99 */     if (this.mean == null)
/* 100 */       this.mean = Double.valueOf(Gamma.Function(this.mu + 0.5D) / Gamma.Function(this.mu) * Math.sqrt(this.omega / this.mu)); 
/* 101 */     return this.mean.doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public double Variance() {
/* 106 */     if (this.variance == null) {
/*     */       
/* 108 */       double a = Gamma.Function(this.mu + 0.5D) / Gamma.Function(this.mu);
/* 109 */       this.variance = Double.valueOf(this.omega * (1.0D - 1.0D / this.mu * a * a));
/*     */     } 
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
/* 121 */     return Gamma.LowerIncomplete(this.mu, this.mu / this.omega * x * x);
/*     */   }
/*     */ 
/*     */   
/*     */   public double ProbabilityDensityFunction(double x) {
/* 126 */     return this.constant * Math.pow(x, this.twoMu1) * Math.exp(this.nratio * x * x);
/*     */   }
/*     */ 
/*     */   
/*     */   public double LogProbabilityDensityFunction(double x) {
/* 131 */     return Math.log(this.constant) + this.twoMu1 * Math.log(x) + this.nratio * x * x;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/NakagamiDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */