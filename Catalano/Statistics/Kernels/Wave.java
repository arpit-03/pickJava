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
/*    */ public class Wave
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private double sigma;
/*    */   
/*    */   public double getSigma() {
/* 18 */     return this.sigma;
/*    */   }
/*    */   
/*    */   public void setSigma(double sigma) {
/* 22 */     this.sigma = sigma;
/*    */   }
/*    */   
/*    */   public Wave(double sigma) {
/* 26 */     this.sigma = sigma;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 31 */     double norm = 0.0D;
/* 32 */     for (int i = 0; i < x.length; i++) {
/*    */       
/* 34 */       double d = x[i] - y[i];
/* 35 */       norm += d * d;
/*    */     } 
/* 37 */     norm = Math.sqrt(norm);
/*    */     
/* 39 */     if (this.sigma == 0.0D || norm == 0.0D) {
/* 40 */       return 0.0D;
/*    */     }
/* 42 */     return this.sigma / norm * Math.sin(norm / this.sigma);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Wave.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */