/*     */ package org.apache.commons.math3.stat.interval;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
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
/*     */ public class ConfidenceInterval
/*     */ {
/*     */   private double lowerBound;
/*     */   private double upperBound;
/*     */   private double confidenceLevel;
/*     */   
/*     */   public ConfidenceInterval(double lowerBound, double upperBound, double confidenceLevel) {
/*  57 */     checkParameters(lowerBound, upperBound, confidenceLevel);
/*  58 */     this.lowerBound = lowerBound;
/*  59 */     this.upperBound = upperBound;
/*  60 */     this.confidenceLevel = confidenceLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLowerBound() {
/*  67 */     return this.lowerBound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getUpperBound() {
/*  74 */     return this.upperBound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getConfidenceLevel() {
/*  82 */     return this.confidenceLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  90 */     return "[" + this.lowerBound + ";" + this.upperBound + "] (confidence level:" + this.confidenceLevel + ")";
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
/*     */   private void checkParameters(double lower, double upper, double confidence) {
/* 102 */     if (lower >= upper) {
/* 103 */       throw new MathIllegalArgumentException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, new Object[] { Double.valueOf(lower), Double.valueOf(upper) });
/*     */     }
/* 105 */     if (confidence <= 0.0D || confidence >= 1.0D)
/* 106 */       throw new MathIllegalArgumentException(LocalizedFormats.OUT_OF_BOUNDS_CONFIDENCE_LEVEL, new Object[] { Double.valueOf(confidence), Integer.valueOf(0), Integer.valueOf(1) }); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/interval/ConfidenceInterval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */