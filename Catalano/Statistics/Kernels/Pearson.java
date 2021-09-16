/*     */ package Catalano.Statistics.Kernels;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Pearson
/*     */   implements IMercerKernel<double[]>
/*     */ {
/*     */   private double omega;
/*     */   private double sigma;
/*     */   private double constant;
/*     */   
/*     */   public double getOmega() {
/*  40 */     return this.omega;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOmega(double omega) {
/*  48 */     this.omega = omega;
/*  49 */     this.constant = 2.0D * Math.sqrt(Math.pow(2.0D, 1.0D / omega) - 1.0D) / this.sigma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSigma() {
/*  57 */     return this.sigma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSigma(double sigma) {
/*  65 */     this.sigma = sigma;
/*  66 */     this.constant = 2.0D * Math.sqrt(Math.pow(2.0D, 1.0D / this.omega) - 1.0D) / sigma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pearson() {
/*  73 */     this(1.0D, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pearson(double omega, double sigma) {
/*  82 */     setOmega(omega);
/*  83 */     setSigma(sigma);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double Function(double[] x, double[] y) {
/*  90 */     double xx = 0.0D;
/*  91 */     double yy = 0.0D;
/*  92 */     double xy = 0.0D;
/*  93 */     for (int i = 0; i < x.length; i++) {
/*  94 */       xx += x[i] * x[i];
/*  95 */       yy += y[i] * y[i];
/*  96 */       xy += x[i] * y[i];
/*     */     } 
/*     */     
/*  99 */     double m = this.constant * Math.sqrt(-2.0D * xy + xx + yy);
/* 100 */     return 1.0D / Math.pow(1.0D + m * m, this.omega);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Pearson.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */