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
/*    */ public class InverseMultiquadric
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private double constant;
/*    */   
/*    */   public double getConstant() {
/* 41 */     return this.constant;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setConstant(double constant) {
/* 49 */     this.constant = constant;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InverseMultiquadric() {
/* 56 */     this(1.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InverseMultiquadric(double constant) {
/* 64 */     this.constant = constant;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 69 */     double norm = 0.0D;
/* 70 */     for (int i = 0; i < x.length; i++) {
/*    */       
/* 72 */       double d = x[i] - y[i];
/* 73 */       norm += d * d;
/*    */     } 
/*    */     
/* 76 */     return 1.0D / (norm + this.constant * this.constant);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/InverseMultiquadric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */