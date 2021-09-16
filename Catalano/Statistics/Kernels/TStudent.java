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
/*    */ public class TStudent
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private int degree;
/*    */   
/*    */   public int getDegree() {
/* 41 */     return this.degree;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDegree(int degree) {
/* 49 */     this.degree = degree;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TStudent() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TStudent(int degree) {
/* 62 */     this.degree = degree;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 67 */     double norm = 0.0D;
/* 68 */     for (int i = 0; i < x.length; i++) {
/* 69 */       double d = x[i] - y[i];
/* 70 */       norm += d * d;
/*    */     } 
/* 72 */     norm = Math.sqrt(norm);
/*    */     
/* 74 */     return 1.0D / (1.0D + Math.pow(norm, this.degree));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/TStudent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */