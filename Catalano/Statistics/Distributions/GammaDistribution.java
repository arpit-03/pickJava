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
/*    */ 
/*    */ public class GammaDistribution
/*    */   implements IDistribution
/*    */ {
/*    */   private double scale;
/*    */   private double shape;
/*    */   private double constant;
/*    */   private double lnconstant;
/*    */   
/*    */   public GammaDistribution(double scale, double shape) {
/* 49 */     this.scale = scale;
/* 50 */     this.shape = shape;
/*    */     
/* 52 */     this.constant = 1.0D / Math.pow(scale, shape) * Gamma.Function(shape);
/* 53 */     this.lnconstant = -(shape * Math.log(scale) + Gamma.Log(shape));
/*    */   }
/*    */   
/*    */   public double getScale() {
/* 57 */     return this.scale;
/*    */   }
/*    */   
/*    */   public double getShape() {
/* 61 */     return this.shape;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Mean() {
/* 66 */     return this.shape * this.scale;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Variance() {
/* 71 */     return this.shape * this.scale * this.scale;
/*    */   }
/*    */   
/*    */   public double Median() {
/* 75 */     return Double.NaN;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Entropy() {
/* 80 */     return this.shape + Math.log(this.scale) + Gamma.Log(this.shape) + (1.0D - this.shape) * Gamma.Digamma(this.shape);
/*    */   }
/*    */ 
/*    */   
/*    */   public double DistributionFunction(double x) {
/* 85 */     return Gamma.LowerIncomplete(this.shape, x / this.scale);
/*    */   }
/*    */ 
/*    */   
/*    */   public double ProbabilityDensityFunction(double x) {
/* 90 */     return this.constant * Math.pow(x, this.shape - 1.0D) * Math.exp(-x / this.scale);
/*    */   }
/*    */ 
/*    */   
/*    */   public double LogProbabilityDensityFunction(double x) {
/* 95 */     return this.lnconstant + (this.shape - 1.0D) * Math.log(x) - x / this.scale;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/GammaDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */