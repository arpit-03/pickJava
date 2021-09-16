/*    */ package org.apache.commons.math3.stat.interval;
/*    */ 
/*    */ import org.apache.commons.math3.distribution.FDistribution;
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
/*    */ public class ClopperPearsonInterval
/*    */   implements BinomialConfidenceInterval
/*    */ {
/*    */   public ConfidenceInterval createInterval(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
/* 34 */     IntervalUtils.checkParameters(numberOfTrials, numberOfSuccesses, confidenceLevel);
/* 35 */     double lowerBound = 0.0D;
/* 36 */     double upperBound = 0.0D;
/* 37 */     double alpha = (1.0D - confidenceLevel) / 2.0D;
/*    */     
/* 39 */     FDistribution distributionLowerBound = new FDistribution((2 * (numberOfTrials - numberOfSuccesses + 1)), (2 * numberOfSuccesses));
/*    */     
/* 41 */     double fValueLowerBound = distributionLowerBound.inverseCumulativeProbability(1.0D - alpha);
/* 42 */     if (numberOfSuccesses > 0) {
/* 43 */       lowerBound = numberOfSuccesses / (numberOfSuccesses + (numberOfTrials - numberOfSuccesses + 1) * fValueLowerBound);
/*    */     }
/*    */ 
/*    */     
/* 47 */     FDistribution distributionUpperBound = new FDistribution((2 * (numberOfSuccesses + 1)), (2 * (numberOfTrials - numberOfSuccesses)));
/*    */     
/* 49 */     double fValueUpperBound = distributionUpperBound.inverseCumulativeProbability(1.0D - alpha);
/* 50 */     if (numberOfSuccesses > 0) {
/* 51 */       upperBound = (numberOfSuccesses + 1) * fValueUpperBound / ((numberOfTrials - numberOfSuccesses) + (numberOfSuccesses + 1) * fValueUpperBound);
/*    */     }
/*    */ 
/*    */     
/* 55 */     return new ConfidenceInterval(lowerBound, upperBound, confidenceLevel);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/interval/ClopperPearsonInterval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */