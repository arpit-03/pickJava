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
/*    */ public class ChiSquare
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   public double Function(double[] x, double[] y) {
/* 41 */     double sum = 0.0D;
/* 42 */     for (int i = 0; i < x.length; i++) {
/*    */       
/* 44 */       double num = x[i] - y[i];
/* 45 */       double den = 0.5D * (x[i] + y[i]);
/*    */       
/* 47 */       if (den != 0.0D) {
/* 48 */         sum += num * num / den;
/*    */       }
/*    */     } 
/* 51 */     return 1.0D - sum;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/ChiSquare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */