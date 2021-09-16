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
/*    */ public class Circular
/*    */   implements IMercerKernel<double[]>
/*    */ {
/* 37 */   private final double c2dPI = 0.6366197723675814D;
/*    */ 
/*    */   
/*    */   private double sigma;
/*    */ 
/*    */ 
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
/*    */   public Circular(double sigma) {
/* 61 */     this.sigma = sigma;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 66 */     double norm = 0.0D;
/* 67 */     for (int i = 0; i < x.length; i++) {
/*    */       
/* 69 */       double d = x[i] - y[i];
/* 70 */       norm += d * d;
/*    */     } 
/*    */     
/* 73 */     norm = Math.sqrt(norm);
/*    */     
/* 75 */     if (norm >= this.sigma)
/*    */     {
/* 77 */       return 0.0D;
/*    */     }
/*    */ 
/*    */     
/* 81 */     norm /= this.sigma;
/* 82 */     double a = 0.6366197723675814D * Math.acos(-norm);
/* 83 */     double b = 0.6366197723675814D * norm;
/* 84 */     double c = 1.0D - norm * norm;
/*    */     
/* 86 */     return a - b * Math.sqrt(c);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Circular.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */