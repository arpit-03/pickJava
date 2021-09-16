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
/*    */ public class Multiquadric
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private double constant;
/*    */   
/*    */   public double getConstant() {
/* 42 */     return this.constant;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setConstant(double constant) {
/* 50 */     this.constant = constant;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Multiquadric() {
/* 57 */     this(1.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Multiquadric(double constant) {
/* 65 */     this.constant = constant;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 70 */     double norm = 0.0D;
/* 71 */     for (int i = 0; i < x.length; i++) {
/*    */       
/* 73 */       double d = x[i] - y[i];
/* 74 */       norm += d * d;
/*    */     } 
/*    */     
/* 77 */     return -(norm + this.constant * this.constant);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Multiquadric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */