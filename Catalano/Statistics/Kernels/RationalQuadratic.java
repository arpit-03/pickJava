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
/*    */ public class RationalQuadratic
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private double constant;
/*    */   
/*    */   public double getConstant() {
/* 18 */     return this.constant;
/*    */   }
/*    */   
/*    */   public void setConstant(double constant) {
/* 22 */     this.constant = constant;
/*    */   }
/*    */   
/*    */   public RationalQuadratic() {
/* 26 */     this(1.0D);
/*    */   }
/*    */   
/*    */   public RationalQuadratic(double constant) {
/* 30 */     this.constant = constant;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 35 */     double norm = 0.0D;
/* 36 */     for (int i = 0; i < x.length; i++) {
/*    */       
/* 38 */       double d = x[i] - y[i];
/* 39 */       norm += d * d;
/*    */     } 
/*    */     
/* 42 */     return 1.0D - norm / (norm - this.constant);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/RationalQuadratic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */