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
/*    */ public class Hellinger
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   public double Function(double[] x, double[] y) {
/* 38 */     double r = 0.0D;
/* 39 */     for (int i = 0; i < x.length; i++)
/*    */     {
/* 41 */       r += Math.sqrt(x[i] * y[i]);
/*    */     }
/*    */     
/* 44 */     return r;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Hellinger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */