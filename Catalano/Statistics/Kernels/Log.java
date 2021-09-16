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
/*    */ public class Log
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private double degree;
/*    */   
/*    */   public double getDegree() {
/* 41 */     return this.degree;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDegree(double degree) {
/* 49 */     this.degree = degree;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 59 */     double norm = 0.0D;
/*    */     
/* 61 */     for (int k = 0; k < x.length; k++) {
/*    */       
/* 63 */       double d = x[k] - y[k];
/* 64 */       norm += d * d;
/*    */     } 
/*    */     
/* 67 */     return -Math.log(Math.pow(norm, this.degree / 2.0D) + 1.0D);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Log.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */