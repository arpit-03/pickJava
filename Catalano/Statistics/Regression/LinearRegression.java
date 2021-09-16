/*    */ package Catalano.Statistics.Regression;
/*    */ 
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
/*    */ public class LinearRegression
/*    */   implements ISimpleRegression, ILinear
/*    */ {
/*    */   private double[] x;
/*    */   private double[] y;
/*    */   private double inclination;
/*    */   private double interception;
/*    */   
/*    */   public LinearRegression(double[] x, double[] y) {
/* 45 */     this.x = x;
/* 46 */     this.y = y;
/* 47 */     this.inclination = Tools.Inclination(x, y);
/* 48 */     this.interception = Tools.Interception(x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getInclination() {
/* 53 */     return this.inclination;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInclination(double inclination) {
/* 58 */     this.inclination = inclination;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getInterception() {
/* 63 */     return this.interception;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInterception(double interception) {
/* 68 */     this.interception = interception;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Regression(double x) {
/* 73 */     return this.inclination * x + this.interception;
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] Regression(double[] x) {
/* 78 */     double[] result = new double[x.length];
/*    */     
/* 80 */     for (int i = 0; i < x.length; i++) {
/* 81 */       result[i] = this.inclination * x[i] + this.interception;
/*    */     }
/* 83 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public double CoefficientOfDetermination() {
/* 88 */     return Math.pow(Correlations.PearsonCorrelation(this.x, this.y), 2.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 93 */     if (this.interception >= 0.0D)
/* 94 */       return "y = " + String.format("%.4f", new Object[] { Double.valueOf(this.inclination) }) + "x + " + String.format("%.4f", new Object[] { Double.valueOf(this.interception) }); 
/* 95 */     return "y = " + String.format("%.4f", new Object[] { Double.valueOf(this.inclination) }) + "x " + String.format("%.4f", new Object[] { Double.valueOf(this.interception) });
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Regression/LinearRegression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */