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
/*    */ public class RayleighDistribution
/*    */   implements IDistribution
/*    */ {
/*    */   private double sigma;
/*    */   
/*    */   public RayleighDistribution(double sigma) {
/* 52 */     this.sigma = sigma;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Mean() {
/* 57 */     return this.sigma * Math.sqrt(1.5707963267948966D);
/*    */   }
/*    */ 
/*    */   
/*    */   public double Variance() {
/* 62 */     return 0.42920367320510344D * this.sigma * this.sigma;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Entropy() {
/* 67 */     return 1.0D + Math.log(this.sigma / 1.4142135623730951D) + 0.28860783245076643D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double DistributionFunction(double x) {
/* 72 */     return 1.0D - Math.exp(-x * x / 2.0D * this.sigma * this.sigma);
/*    */   }
/*    */ 
/*    */   
/*    */   public double ProbabilityDensityFunction(double x) {
/* 77 */     return x / this.sigma * this.sigma * Math.exp(-x * x / 2.0D * this.sigma * this.sigma);
/*    */   }
/*    */ 
/*    */   
/*    */   public double LogProbabilityDensityFunction(double x) {
/* 82 */     return Math.log(x / this.sigma * this.sigma) + -x * x / 2.0D * this.sigma * this.sigma;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/RayleighDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */