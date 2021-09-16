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
/*    */ public class SquaredSinc
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private double gamma;
/*    */   
/*    */   public double getGamma() {
/* 40 */     return this.gamma;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setGamma(double value) {
/* 48 */     this.gamma = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SquaredSinc() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SquaredSinc(double gamma) {
/* 61 */     this.gamma = gamma;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 66 */     double norm = 0.0D;
/* 67 */     for (int i = 0; i < x.length; i++) {
/*    */       
/* 69 */       double d = x[i] - y[i];
/* 70 */       norm += d * d;
/*    */     } 
/*    */     
/* 73 */     double num = this.gamma * Math.sqrt(norm);
/* 74 */     double den = this.gamma * this.gamma * norm;
/*    */     
/* 76 */     return Math.sin(num) / den;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/SquaredSinc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */