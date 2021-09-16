/*    */ package Catalano.Statistics.Kernels;
/*    */ 
/*    */ import Catalano.Math.Matrix;
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
/*    */ public class Additive
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private IMercerKernel[] kernels;
/*    */   private double[] weights;
/*    */   
/*    */   public Additive(IMercerKernel[] kernels) {
/* 36 */     this(kernels, Matrix.CreateMatrix1D(kernels.length, 1.0D));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Additive(IMercerKernel[] kernels, double[] weights) {
/* 45 */     this.kernels = kernels;
/* 46 */     this.weights = weights;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 56 */     double sum = 0.0D;
/* 57 */     for (int i = 0; i < this.kernels.length; i++) {
/* 58 */       sum += this.weights[i] * this.kernels[i].Function(x, y);
/*    */     }
/* 60 */     return sum;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Additive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */