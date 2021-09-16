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
/*    */ 
/*    */ public class WeibullDistribution
/*    */   implements IDistribution
/*    */ {
/*    */   private double a;
/*    */   private double b;
/*    */   
/*    */   public WeibullDistribution(double shape, double scale) {
/* 46 */     this.a = shape;
/* 47 */     this.b = scale;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Mean() {
/* 52 */     return this.b * Gamma.Function(1.0D + 1.0D / this.a);
/*    */   }
/*    */ 
/*    */   
/*    */   public double Variance() {
/* 57 */     return this.b * this.b * Gamma.Function(1.0D + 2.0D / this.a) - Mean() * Mean();
/*    */   }
/*    */ 
/*    */   
/*    */   public double Entropy() {
/* 62 */     return 0.5772156649015329D * (1.0D - 1.0D / this.a) + Math.log(this.b / this.a) + 1.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double DistributionFunction(double x) {
/* 67 */     if (x > 0.0D)
/* 68 */       return 1.0D - Math.exp(-Math.pow(x / this.b, this.a)); 
/* 69 */     if (x == 0.0D)
/* 70 */       return Double.POSITIVE_INFINITY; 
/* 71 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double ProbabilityDensityFunction(double x) {
/* 76 */     if (x > 0.0D)
/* 77 */       return this.a / this.b * Math.pow(x / this.b, this.a - 1.0D) * Math.exp(-Math.pow(x / this.b, this.a)); 
/* 78 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double LogProbabilityDensityFunction(double x) {
/* 83 */     if (x >= 0.0D)
/* 84 */       return Math.log(this.a / this.b) + (this.a - 1.0D) * Math.log(x / this.b) - Math.pow(x / this.b, this.a); 
/* 85 */     return Double.NEGATIVE_INFINITY;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/WeibullDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */