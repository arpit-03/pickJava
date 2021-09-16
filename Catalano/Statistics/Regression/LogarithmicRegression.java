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
/*    */ public class LogarithmicRegression
/*    */   implements ISimpleRegression, ILinear
/*    */ {
/*    */   private double[] x;
/*    */   private double[] y;
/*    */   private double inclination;
/*    */   private double interception;
/*    */   
/*    */   public LogarithmicRegression(double[] x, double[] y) {
/* 47 */     this.x = x;
/* 48 */     this.y = y;
/* 49 */     double[] xx = Matrix.Log(x);
/* 50 */     this.inclination = Tools.Inclination(xx, y);
/* 51 */     this.interception = Tools.Interception(xx, y);
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
/* 76 */     return this.inclination * Math.log(x) + this.interception;
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] Regression(double[] x) {
/* 81 */     double[] r = new double[x.length];
/* 82 */     for (int i = 0; i < x.length; i++) {
/* 83 */       r[i] = this.inclination * Math.log(x[i]) + this.interception;
/*    */     }
/* 85 */     return r;
/*    */   }
/*    */ 
/*    */   
/*    */   public double CoefficientOfDetermination() {
/* 90 */     double[] r = Regression(this.x);
/* 91 */     return Math.pow(Correlations.PearsonCorrelation(r, this.y), 2.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 96 */     if (this.interception > 0.0D)
/* 97 */       return "y = " + String.format("%.4f", new Object[] { Double.valueOf(this.inclination) }) + "ln(x) + " + this.interception; 
/* 98 */     return "y = " + this.inclination + "ln(x) " + this.interception;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Regression/LogarithmicRegression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */