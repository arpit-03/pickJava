/*    */ package Catalano.Statistics.Distributions;
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
/*    */ public class ExponentialDistribution
/*    */   implements IDistribution
/*    */ {
/*    */   private double lambda;
/*    */   private double lnlambda;
/*    */   
/*    */   public ExponentialDistribution(double rate) {
/* 42 */     this.lambda = rate;
/* 43 */     this.lnlambda = Math.log(rate);
/*    */   }
/*    */ 
/*    */   
/*    */   public double Mean() {
/* 48 */     return 1.0D / this.lambda;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Variance() {
/* 53 */     return 1.0D / this.lambda * this.lambda;
/*    */   }
/*    */   
/*    */   public double Median() {
/* 57 */     return Math.log(2.0D) / this.lambda;
/*    */   }
/*    */   
/*    */   public double Mode() {
/* 61 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Entropy() {
/* 66 */     return 1.0D - Math.log(this.lambda);
/*    */   }
/*    */ 
/*    */   
/*    */   public double DistributionFunction(double x) {
/* 71 */     return 1.0D - Math.exp(-this.lambda * x);
/*    */   }
/*    */ 
/*    */   
/*    */   public double ProbabilityDensityFunction(double x) {
/* 76 */     return this.lambda * Math.exp(-this.lambda * x);
/*    */   }
/*    */ 
/*    */   
/*    */   public double LogProbabilityDensityFunction(double x) {
/* 81 */     return this.lnlambda - this.lambda * x;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/ExponentialDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */