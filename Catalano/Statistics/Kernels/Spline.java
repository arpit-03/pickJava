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
/*    */ public class Spline
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   public double Function(double[] x, double[] y) {
/* 41 */     double k = 1.0D;
/* 42 */     for (int i = 0; i < x.length; i++) {
/* 43 */       double min = Math.min(x[i], y[i]);
/* 44 */       double xy = x[i] * y[i];
/*    */ 
/*    */       
/* 47 */       k *= 1.0D + xy + xy * min - (x[i] + y[i]) / 2.0D * min * min + min * min * min / 3.0D;
/*    */     } 
/*    */     
/* 50 */     return k;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Spline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */