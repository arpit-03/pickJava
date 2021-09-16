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
/*    */ public class Bessel
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private int order;
/*    */   private double sigma;
/*    */   
/*    */   public int getOrder() {
/* 42 */     return this.order;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOrder(int order) {
/* 50 */     this.order = order;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getSigma() {
/* 58 */     return this.sigma;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSigma(double sigma) {
/* 66 */     this.sigma = sigma;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Bessel(int order, double sigma) {
/* 75 */     this.order = order;
/* 76 */     this.sigma = sigma;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 81 */     double norm = 0.0D;
/*    */     
/* 83 */     for (int k = 0; k < x.length; k++) {
/*    */       
/* 85 */       double d = x[k] - y[k];
/* 86 */       norm += d * d;
/*    */     } 
/* 88 */     norm = Math.sqrt(norm);
/*    */     
/* 90 */     return Catalano.Math.Functions.Bessel.J(this.order, this.sigma * norm) / 
/* 91 */       Math.pow(norm, -norm * this.order);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Bessel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */