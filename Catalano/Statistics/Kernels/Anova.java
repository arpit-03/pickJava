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
/*    */ 
/*    */ public class Anova
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private int n;
/*    */   private int p;
/*    */   private double[][][] K;
/*    */   
/*    */   public Anova(int vectorLength, int subsequenceLength) {
/* 45 */     this.n = vectorLength;
/* 46 */     this.p = subsequenceLength;
/* 47 */     this.K = new double[vectorLength][vectorLength][subsequenceLength];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 53 */     for (int k = 0; k < this.p; k++) {
/* 54 */       for (int i = 0; i < this.n; i++) {
/* 55 */         for (int j = 0; j < this.n; j++)
/* 56 */           this.K[i][j][k] = kernel(x, i, y, j, k); 
/*    */       } 
/*    */     } 
/* 59 */     return this.K[this.n - 1][this.n - 1][this.p - 1];
/*    */   }
/*    */ 
/*    */   
/*    */   private double kernel(double[] x, int ni, double[] y, int mi, int pi) {
/*    */     double a;
/* 65 */     if (ni == 0 || mi == 0) {
/*    */       
/* 67 */       a = 0.0D;
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 72 */       a = this.K[ni - 1][mi - 1][pi];
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 77 */     double k = x[ni] * y[mi];
/*    */ 
/*    */     
/* 80 */     if (pi == 0)
/*    */     {
/* 82 */       return a + k;
/*    */     }
/* 84 */     if (ni == 0 || mi == 0)
/*    */     {
/* 86 */       return a;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 91 */     return a + k * this.K[ni - 1][mi - 1][pi - 1];
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Anova.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */