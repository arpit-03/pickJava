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
/*    */ public class WilsonScoreInterval
/*    */   implements BinomialConfidenceInterval
/*    */ {
/*    */   public ConfidenceInterval createInterval(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
/* 34 */     IntervalUtils.checkParameters(numberOfTrials, numberOfSuccesses, confidenceLevel);
/* 35 */     double alpha = (1.0D - confidenceLevel) / 2.0D;
/* 36 */     NormalDistribution normalDistribution = new NormalDistribution();
/* 37 */     double z = normalDistribution.inverseCumulativeProbability(1.0D - alpha);
/* 38 */     double zSquared = FastMath.pow(z, 2);
/* 39 */     double mean = numberOfSuccesses / numberOfTrials;
/*    */     
/* 41 */     double factor = 1.0D / (1.0D + 1.0D / numberOfTrials * zSquared);
/* 42 */     double modifiedSuccessRatio = mean + 1.0D / (2 * numberOfTrials) * zSquared;
/* 43 */     double difference = z * FastMath.sqrt(1.0D / numberOfTrials * mean * (1.0D - mean) + 1.0D / 4.0D * FastMath.pow(numberOfTrials, 2) * zSquared);
/*    */ 
/*    */ 
/*    */     
/* 47 */     double lowerBound = factor * (modifiedSuccessRatio - difference);
/* 48 */     double upperBound = factor * (modifiedSuccessRatio + difference);
/* 49 */     return new ConfidenceInterval(lowerBound, upperBound, confidenceLevel);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/interval/WilsonScoreInterval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */