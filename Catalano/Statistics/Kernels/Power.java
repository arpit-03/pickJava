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
/*    */ public class Power
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private double degree;
/*    */   
/*    */   public Power(double degree) {
/* 38 */     this.degree = degree;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getDegree() {
/* 46 */     return this.degree;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDegree(double degree) {
/* 54 */     degree = degree;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 64 */     double norm = 0.0D;
/* 65 */     for (int i = 0; i < x.length; i++) {
/* 66 */       double d = x[i] - y[i];
/* 67 */       norm += d * d;
/*    */     } 
/*    */     
/* 70 */     return -Math.pow(norm, this.degree);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Power.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */