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
/*    */ public class Polynomial
/*    */   implements IMercerKernel<double[]>
/*    */ {
/*    */   private int degree;
/*    */   private double constant;
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
/* 49 */     this.degree = Math.max(1, degree);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getConstant() {
/* 57 */     return this.constant;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setConstant(double constant) {
/* 65 */     this.constant = constant;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Polynomial() {
/* 72 */     this(2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Polynomial(int degree) {
/* 80 */     this(degree, 1.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Polynomial(int degree, double constant) {
/* 89 */     setDegree(degree);
/* 90 */     this.constant = constant;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Function(double[] x, double[] y) {
/* 95 */     double sum = this.constant;
/* 96 */     for (int i = 0; i < x.length; i++) {
/* 97 */       sum += x[i] * y[i];
/*    */     }
/* 99 */     return Math.pow(sum, this.degree);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/Polynomial.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */