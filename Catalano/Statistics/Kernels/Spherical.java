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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Spherical
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private double sigma;
/*    */   
/*    */   public double getSigma() {
/* 41 */     return this.sigma;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDegree(double value) {
/* 49 */     this.sigma = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Spherical() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Spherical(double sigma) {
/* 62 */     this.sigma = sigma;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 67 */     double norm = 0.0D;
/* 68 */     for (int i = 0; i < x.length; i++) {
/* 69 */       double d = x[i] - y[i];
/* 70 */       norm += d * d;
/*    */     } 
/*    */     
/* 73 */     norm = Math.sqrt(norm);
/*    */     
/* 75 */     if (norm >= this.sigma) {
/* 76 */       return 0.0D;
/*    */     }
/*    */     
/* 79 */     norm /= this.sigma;
/* 80 */     return 1.0D - 1.5D * norm + 0.5D * norm * norm * norm;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Spherical.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */