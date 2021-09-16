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
/*    */ public class ThinPlateSpline
/*    */   implements IMercerKernel<double[]>
/*    */ {
/* 30 */   private double sigma = 1.0D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getSigma() {
/* 37 */     return this.sigma;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSigma(double sigma) {
/* 45 */     this.sigma = Math.max(1.0D, sigma);
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
/*    */   
/*    */   public ThinPlateSpline(double sigma) {
/* 58 */     setSigma(sigma);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 64 */     double r = 0.0D;
/* 65 */     for (int i = 0; i < x.length; i++) {
/* 66 */       double dxy = x[i] - y[i];
/* 67 */       r += dxy * dxy;
/*    */     } 
/*    */     
/* 70 */     return r / this.sigma * this.sigma * Math.log(Math.sqrt(r) / this.sigma);
/*    */   }
/*    */   
/*    */   public ThinPlateSpline() {}
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/ThinPlateSpline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */