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
/*    */ public class BernoulliDistribution
/*    */   implements IDiscreteDistribution
/*    */ {
/*    */   private double probability;
/*    */   private double complement;
/*    */   private Double entropy;
/*    */   
/*    */   public BernoulliDistribution(double mean) {
/* 53 */     this.probability = mean;
/* 54 */     this.complement = 1.0D - mean;
/*    */     
/* 56 */     this.entropy = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Mean() {
/* 61 */     return this.probability;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Variance() {
/* 66 */     return this.probability * this.complement;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Entropy() {
/* 71 */     if (this.entropy == null) {
/* 72 */       this.entropy = 
/* 73 */         Double.valueOf(-this.probability * Math.log(this.probability) - this.complement * Math.log(this.complement));
/*    */     }
/*    */     
/* 76 */     return this.entropy.doubleValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public double DistributionFunction(int k) {
/* 81 */     if (k < 0) return 0.0D; 
/* 82 */     if (k >= 1) return 1.0D; 
/* 83 */     return this.complement;
/*    */   }
/*    */ 
/*    */   
/*    */   public double ProbabilityMassFunction(int k) {
/* 88 */     if (k == 1) return this.probability; 
/* 89 */     if (k == 0) return this.complement; 
/* 90 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double LogProbabilityMassFunction(int k) {
/* 95 */     if (k == 1) return Math.log(this.probability); 
/* 96 */     if (k == 0) return Math.log(this.complement); 
/* 97 */     return Double.NEGATIVE_INFINITY;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/BernoulliDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */