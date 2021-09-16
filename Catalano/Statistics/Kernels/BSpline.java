/*    */ package Catalano.Statistics.Kernels;
/*    */ 
/*    */ import Catalano.Math.Special;
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
/*    */ public class BSpline
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private int order;
/*    */   
/*    */   public int getOrder() {
/* 44 */     return this.order;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOrder(int order) {
/* 52 */     this.order = order;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BSpline(int order) {
/* 60 */     this.order = order;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 65 */     double k = 1.0D;
/* 66 */     int n = 2 * this.order + 1;
/*    */     
/* 68 */     for (int p = 0; p < x.length; p++) {
/* 69 */       k *= Special.BSpline(n, x[p] - y[p]);
/*    */     }
/* 71 */     return k;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/BSpline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */