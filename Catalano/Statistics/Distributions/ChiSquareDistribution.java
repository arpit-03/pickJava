/*    */ package Catalano.Statistics.Distributions;
/*    */ 
/*    */ import Catalano.Math.Functions.Gamma;
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
/*    */ public class ChiSquareDistribution
/*    */   implements IDistribution
/*    */ {
/*    */   private int degreesOfFreedom;
/*    */   private double entropy;
/*    */   
/*    */   public ChiSquareDistribution(int degreesOfFreedom) {
/* 45 */     this.degreesOfFreedom = degreesOfFreedom;
/*    */   }
/*    */   
/*    */   public int getDegreesOfFreedom() {
/* 49 */     return this.degreesOfFreedom;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Mean() {
/* 54 */     return this.degreesOfFreedom;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Variance() {
/* 59 */     return 2.0D * this.degreesOfFreedom;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Entropy() {
/* 64 */     double kd2 = this.degreesOfFreedom / 2.0D;
/* 65 */     double m1 = Math.log(2.0D * Gamma.Function(kd2));
/* 66 */     double m2 = (1.0D - kd2) * Gamma.Digamma(kd2);
/* 67 */     this.entropy = kd2 + m1 + m2;
/*    */     
/* 69 */     return this.entropy;
/*    */   }
/*    */   
/*    */   public double ComplementaryDistributionFunction(double x) {
/* 73 */     return Gamma.ComplementedIncomplete(this.degreesOfFreedom / 2.0D, x / 2.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public double DistributionFunction(double x) {
/* 78 */     return Gamma.Incomplete(this.degreesOfFreedom / 2.0D, x / 2.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public double ProbabilityDensityFunction(double x) {
/* 83 */     double v = this.degreesOfFreedom;
/* 84 */     double m1 = Math.pow(x, (v - 2.0D) / 2.0D);
/* 85 */     double m2 = Math.exp(-x / 2.0D);
/* 86 */     double m3 = Math.pow(2.0D, v / 2.0D) * Gamma.Function(v / 2.0D);
/* 87 */     return m1 * m2 / m3;
/*    */   }
/*    */ 
/*    */   
/*    */   public double LogProbabilityDensityFunction(double x) {
/* 92 */     double v = this.degreesOfFreedom;
/* 93 */     double m1 = (v - 2.0D) / 2.0D * Math.log(x);
/* 94 */     double m2 = -x / 2.0D;
/* 95 */     double m3 = v / 2.0D * Math.log(2.0D) + Gamma.Log(v / 2.0D);
/* 96 */     return m1 + m2 - m3;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/ChiSquareDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */