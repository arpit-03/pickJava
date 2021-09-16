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
/*    */ public class PowerRegression
/*    */   implements ISimpleRegression, ILinear
/*    */ {
/*    */   private double[] x;
/*    */   private double[] y;
/*    */   private double inclination;
/*    */   private double interception;
/*    */   
/*    */   public PowerRegression(double[] x, double[] y) {
/* 47 */     this.x = x;
/* 48 */     this.y = y;
/* 49 */     double[] xx = Matrix.Log(x);
/* 50 */     double[] yy = Matrix.Log(y);
/* 51 */     this.inclination = Tools.Inclination(xx, yy);
/* 52 */     this.interception = Math.exp(Tools.Interception(xx, yy));
/*    */   }
/*    */ 
/*    */   
/*    */   public double getInclination() {
/* 57 */     return this.inclination;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInclination(double inclination) {
/* 62 */     this.inclination = inclination;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getInterception() {
/* 67 */     return this.interception;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInterception(double interception) {
/* 72 */     this.interception = interception;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Regression(double x) {
/* 77 */     return this.interception * Math.pow(x, this.inclination);
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] Regression(double[] x) {
/* 82 */     double[] result = new double[x.length];
/*    */     
/* 84 */     for (int i = 0; i < x.length; i++) {
/* 85 */       result[i] = this.interception * Math.pow(x[i], this.inclination);
/*    */     }
/* 87 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public double CoefficientOfDetermination() {
/* 92 */     double[] r = Regression(this.x);
/* 93 */     return Math.pow(Correlations.PearsonCorrelation(r, this.y), 2.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 98 */     return "y = " + String.format("%.4f", new Object[] { Double.valueOf(this.interception) }) + "x^" + String.format("%.4f", new Object[] { Double.valueOf(this.inclination) });
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Regression/PowerRegression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */