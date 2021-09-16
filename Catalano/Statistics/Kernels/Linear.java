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
/*    */ public class Linear
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private double constant;
/*    */   
/*    */   public Linear(double constant) {
/* 33 */     this.constant = constant;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Linear() {
/* 40 */     this.constant = 1.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getConstant() {
/* 48 */     return this.constant;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setConstant(double value) {
/* 56 */     this.constant = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 66 */     double sum = this.constant;
/* 67 */     for (int i = 0; i < x.length; i++) {
/* 68 */       sum += x[i] * y[i];
/*    */     }
/* 70 */     return sum;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Linear.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */