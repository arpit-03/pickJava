/*    */ package Catalano.Statistics.Kernels;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Gaussian
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private double gamma;
/*    */   
/*    */   public double getGamma() {
/* 33 */     return this.gamma;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setGamma(double gamma) {
/* 41 */     this.gamma = gamma / 100.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Gaussian() {
/* 48 */     this(1.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Gaussian(double gamma) {
/* 56 */     setGamma(gamma);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 68 */     if (x == y) return 1.0D;
/*    */     
/* 70 */     double norm = 0.0D;
/* 71 */     for (int i = 0; i < x.length; i++) {
/* 72 */       double d = x[i] - y[i];
/* 73 */       norm += d * d;
/*    */     } 
/*    */     
/* 76 */     return Math.exp(-this.gamma * norm);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Gaussian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */