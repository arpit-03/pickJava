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
/*    */ public class Sigmoid
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private double alpha;
/*    */   private double constant;
/*    */   
/*    */   public Sigmoid() {
/* 33 */     this(0.01D, -2.718281828459045D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Sigmoid(double alpha, double constant) {
/* 41 */     this.alpha = alpha;
/* 42 */     this.constant = constant;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getConstant() {
/* 50 */     return this.constant;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setConstant(double constant) {
/* 58 */     constant = constant;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getAlpha() {
/* 66 */     return this.alpha;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setAlpha(double alpha) {
/* 74 */     this.alpha = alpha;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 84 */     double sum = 0.0D;
/* 85 */     for (int i = 0; i < x.length; i++) {
/* 86 */       sum += x[i] * y[i];
/*    */     }
/* 88 */     return Math.tanh(this.alpha * sum + this.constant);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Sigmoid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */