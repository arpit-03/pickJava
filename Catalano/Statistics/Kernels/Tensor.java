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
/*    */ public class Tensor
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private IMercerKernel<double[]>[] kernels;
/*    */   
/*    */   public Tensor(IMercerKernel[] kernels) {
/* 18 */     this.kernels = (IMercerKernel<double[]>[])kernels;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 23 */     double product = 1.0D;
/*    */     
/* 25 */     for (int i = 0; i < this.kernels.length; i++)
/*    */     {
/* 27 */       product *= this.kernels[i].Function(x, y);
/*    */     }
/*    */     
/* 30 */     return product;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Tensor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */