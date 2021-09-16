/*    */ package Catalano.Statistics.Distributions;
/*    */ 
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LaplaceDistribution
/*    */   implements IDistribution
/*    */ {
/*    */   private double u;
/*    */   private double b;
/*    */   private double constant;
/*    */   
/*    */   public LaplaceDistribution(double location, double scale) {
/* 57 */     if (scale <= 0.0D)
/* 58 */       try { throw new Exception("Scale must be non-negative."); }
/* 59 */       catch (Exception ex)
/* 60 */       { Logger.getLogger(LaplaceDistribution.class.getName()).log(Level.SEVERE, (String)null, ex); }
/*    */        
/* 62 */     this.u = location;
/* 63 */     this.b = scale;
/*    */     
/* 65 */     this.constant = 1.0D / 2.0D * this.b;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Mean() {
/* 70 */     return this.u;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Variance() {
/* 75 */     return 2.0D * this.b * this.b;
/*    */   }
/*    */ 
/*    */   
/*    */   public double Entropy() {
/* 80 */     return Math.log(5.43656365691809D * this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public double DistributionFunction(double x) {
/* 85 */     return 0.5D * (1.0D + Math.signum(x - this.u) * (1.0D - Math.exp(-Math.abs(x - this.u) / this.b)));
/*    */   }
/*    */ 
/*    */   
/*    */   public double ProbabilityDensityFunction(double x) {
/* 90 */     return this.constant * Math.exp(-Math.abs(x - this.u) / this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public double LogProbabilityDensityFunction(double x) {
/* 95 */     return Math.log(this.constant) - Math.abs(x - this.u) / this.b;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Distributions/LaplaceDistribution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */