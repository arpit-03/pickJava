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
/*    */ public class HistogramIntersection
/*    */   implements IMercerKernel<double[]>
/*    */ {
/* 38 */   private double alpha = 1.0D;
/* 39 */   private double beta = 1.0D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HistogramIntersection(double alpha, double beta) {
/* 47 */     this.alpha = alpha;
/* 48 */     this.beta = beta;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 53 */     double sum = 0.0D;
/*    */     
/* 55 */     for (int i = 0; i < x.length; i++)
/*    */     {
/* 57 */       sum += Math.min(
/* 58 */           Math.pow(Math.abs(x[i]), this.alpha), 
/* 59 */           Math.pow(Math.abs(y[i]), this.beta));
/*    */     }
/*    */     
/* 62 */     return sum;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/HistogramIntersection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */