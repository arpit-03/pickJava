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
/*    */ public class Hypersecant
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
/*    */   public void setGamma(double gamma) {
/* 48 */     this.gamma = gamma;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Hypersecant(double gamma) {
/* 56 */     this.gamma = gamma;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 61 */     double norm = 0.0D;
/* 62 */     for (int i = 0; i < x.length; i++) {
/*    */       
/* 64 */       double d = x[i] - y[i];
/* 65 */       norm += d * d;
/*    */     } 
/*    */     
/* 68 */     norm = Math.sqrt(norm);
/*    */     
/* 70 */     return 2.0D / Math.exp(this.gamma * norm) + Math.exp(-this.gamma * norm);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Hypersecant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */