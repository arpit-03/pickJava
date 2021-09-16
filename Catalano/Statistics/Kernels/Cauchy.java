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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Cauchy
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private double sigma;
/*    */   
/*    */   public double getSigma() {
/* 45 */     return this.sigma;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSigma(double sigma) {
/* 53 */     this.sigma = sigma;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Cauchy() {
/* 61 */     this.sigma = 1.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Cauchy(double sigma) {
/* 69 */     this.sigma = sigma;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 76 */     if (x == y) return 1.0D;
/*    */     
/* 78 */     double norm = 0.0D;
/* 79 */     for (int i = 0; i < x.length; i++) {
/*    */       
/* 81 */       double d = x[i] - y[i];
/* 82 */       norm += d * d;
/*    */     } 
/*    */     
/* 85 */     return 1.0D / (1.0D + norm / this.sigma);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Cauchy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */