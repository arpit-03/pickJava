/*    */ package org.apache.commons.math3.stat.interval;
/*    */ 
/*    */ import org.apache.commons.math3.distribution.NormalDistribution;
/*    */ import org.apache.commons.math3.util.FastMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AgrestiCoullInterval
/*    */   implements BinomialConfidenceInterval
/*    */ {
/*    */   public ConfidenceInterval createInterval(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
/* 34 */     IntervalUtils.checkParameters(numberOfTrials, numberOfSuccesses, confidenceLevel);
/* 35 */     double alpha = (1.0D - confidenceLevel) / 2.0D;
/* 36 */     NormalDistribution normalDistribution = new NormalDistribution();
/* 37 */     double z = normalDistribution.inverseCumulativeProbability(1.0D - alpha);
/* 38 */     double zSquared = FastMath.pow(z, 2);
/* 39 */     double modifiedNumberOfTrials = numberOfTrials + zSquared;
/* 40 */     double modifiedSuccessesRatio = 1.0D / modifiedNumberOfTrials * (numberOfSuccesses + 0.5D * zSquared);
/* 41 */     double difference = z * FastMath.sqrt(1.0D / modifiedNumberOfTrials * modifiedSuccessesRatio * (1.0D - modifiedSuccessesRatio));
/*    */ 
/*    */     
/* 44 */     return new ConfidenceInterval(modifiedSuccessesRatio - difference, modifiedSuccessesRatio + difference, confidenceLevel);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/interval/AgrestiCoullInterval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */