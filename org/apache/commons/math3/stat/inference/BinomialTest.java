/*     */ package org.apache.commons.math3.stat.inference;
/*     */ 
/*     */ import org.apache.commons.math3.distribution.BinomialDistribution;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BinomialTest
/*     */ {
/*     */   public boolean binomialTest(int numberOfTrials, int numberOfSuccesses, double probability, AlternativeHypothesis alternativeHypothesis, double alpha) {
/*  63 */     double pValue = binomialTest(numberOfTrials, numberOfSuccesses, probability, alternativeHypothesis);
/*  64 */     return (pValue < alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double binomialTest(int numberOfTrials, int numberOfSuccesses, double probability, AlternativeHypothesis alternativeHypothesis) {
/*     */     int criticalValueLow, criticalValueHigh;
/*     */     double pTotal;
/* 104 */     if (numberOfTrials < 0) {
/* 105 */       throw new NotPositiveException(Integer.valueOf(numberOfTrials));
/*     */     }
/* 107 */     if (numberOfSuccesses < 0) {
/* 108 */       throw new NotPositiveException(Integer.valueOf(numberOfSuccesses));
/*     */     }
/* 110 */     if (probability < 0.0D || probability > 1.0D) {
/* 111 */       throw new OutOfRangeException(Double.valueOf(probability), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/* 113 */     if (numberOfTrials < numberOfSuccesses) {
/* 114 */       throw new MathIllegalArgumentException(LocalizedFormats.BINOMIAL_INVALID_PARAMETERS_ORDER, new Object[] { Integer.valueOf(numberOfTrials), Integer.valueOf(numberOfSuccesses) });
/*     */     }
/*     */ 
/*     */     
/* 118 */     if (alternativeHypothesis == null) {
/* 119 */       throw new NullArgumentException();
/*     */     }
/*     */ 
/*     */     
/* 123 */     BinomialDistribution distribution = new BinomialDistribution(null, numberOfTrials, probability);
/* 124 */     switch (alternativeHypothesis) {
/*     */       case GREATER_THAN:
/* 126 */         return 1.0D - distribution.cumulativeProbability(numberOfSuccesses - 1);
/*     */       case LESS_THAN:
/* 128 */         return distribution.cumulativeProbability(numberOfSuccesses);
/*     */       case TWO_SIDED:
/* 130 */         criticalValueLow = 0;
/* 131 */         criticalValueHigh = numberOfTrials;
/* 132 */         pTotal = 0.0D;
/*     */         
/*     */         do {
/* 135 */           double pLow = distribution.probability(criticalValueLow);
/* 136 */           double pHigh = distribution.probability(criticalValueHigh);
/*     */           
/* 138 */           if (pLow == pHigh) {
/* 139 */             pTotal += 2.0D * pLow;
/* 140 */             criticalValueLow++;
/* 141 */             criticalValueHigh--;
/* 142 */           } else if (pLow < pHigh) {
/* 143 */             pTotal += pLow;
/* 144 */             criticalValueLow++;
/*     */           } else {
/* 146 */             pTotal += pHigh;
/* 147 */             criticalValueHigh--;
/*     */           }
/*     */         
/* 150 */         } while (criticalValueLow <= numberOfSuccesses && criticalValueHigh >= numberOfSuccesses);
/*     */ 
/*     */ 
/*     */         
/* 154 */         return pTotal;
/*     */     } 
/* 156 */     throw new MathInternalError(LocalizedFormats.OUT_OF_RANGE_SIMPLE, new Object[] { alternativeHypothesis, AlternativeHypothesis.TWO_SIDED, AlternativeHypothesis.LESS_THAN });
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/inference/BinomialTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */