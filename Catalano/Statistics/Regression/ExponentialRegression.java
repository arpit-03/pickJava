/*    */ package Catalano.Statistics.Regression;
/*    */ 
/*    */ import Catalano.Math.Matrix;
/*    */ import Catalano.Statistics.Correlations;
/*    */ import Catalano.Statistics.Tools;
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
/*    */ public class ExponentialRegression
/*    */   implements ISimpleRegression, ILinear
/*    */ {
/*    */   private double[] x;
/*    */   private double[] y;
/*    */   private double inclination;
/*    */   private double interception;
/*    */   
/*    */   public ExponentialRegression(double[] x, double[] y) {
/* 47 */     this.x = x;
/* 48 */     this.y = y;
/* 49 */     double[] yy = Matrix.Log(y);
/* 50 */     this.inclination = Tools.Inclination(x, yy);
/* 51 */     this.interception = Math.exp(Tools.Interception(x, yy));
/*    */   }
/*    */ 
/*    */   
/*    */   public double getInclination() {
/* 56 */     return this.inclination;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInclination(double inclination) {
/* 61 */     this.inclination = inclination;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getInterception() {
/* 66 */     return this.interception;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInterception(double interception) {
/* 71 */     this.interception = interception;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Regression(double x) {
/* 76 */     return this.interception * Math.exp(this.inclination * x);
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] Regression(double[] x) {
/* 81 */     double[] result = new double[x.length];
/*    */     
/* 83 */     for (int i = 0; i < x.length; i++) {
/* 84 */       result[i] = this.interception * Math.exp(this.interception * x[i]);
/*    */     }
/* 86 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public double CoefficientOfDetermination() {
/* 91 */     double[] r = Regression(this.x);
/* 92 */     return Math.pow(Correlations.PearsonCorrelation(r, this.y), 2.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 97 */     return "y = " + String.format("%.4f", new Object[] { Double.valueOf(this.interception) }) + "exp(" + String.format("%.4f", new Object[] { Double.valueOf(this.inclination) }) + "x)";
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Regression/ExponentialRegression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */