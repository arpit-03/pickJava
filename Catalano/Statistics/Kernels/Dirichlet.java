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
/*    */ public class Dirichlet
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private int dimension;
/*    */   
/*    */   public int getDimension() {
/* 40 */     return this.dimension;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDimension(int dimension) {
/* 48 */     this.dimension = dimension;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Dirichlet(int dimension) {
/* 56 */     this.dimension = dimension;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 64 */     double prod = 1.0D;
/* 65 */     for (int i = 0; i < x.length; i++) {
/*    */       
/* 67 */       double delta = x[i] - y[i];
/* 68 */       double num = Math.sin((this.dimension + 0.5D) * delta);
/* 69 */       double den = 2.0D * Math.sin(delta / 2.0D);
/* 70 */       prod *= num / den;
/*    */     } 
/*    */     
/* 73 */     return prod;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Dirichlet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */