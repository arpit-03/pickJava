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
/*    */ 
/*    */ public class NormalApproximationInterval
/*    */   implements BinomialConfidenceInterval
/*    */ {
/*    */   public ConfidenceInterval createInterval(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
/* 35 */     IntervalUtils.checkParameters(numberOfTrials, numberOfSuccesses, confidenceLevel);
/* 36 */     double mean = numberOfSuccesses / numberOfTrials;
/* 37 */     double alpha = (1.0D - confidenceLevel) / 2.0D;
/* 38 */     NormalDistribution normalDistribution = new NormalDistribution();
/* 39 */     double difference = normalDistribution.inverseCumulativeProbability(1.0D - alpha) * FastMath.sqrt(1.0D / numberOfTrials * mean * (1.0D - mean));
/*    */     
/* 41 */     return new ConfidenceInterval(mean - difference, mean + difference, confidenceLevel);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/interval/NormalApproximationInterval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */