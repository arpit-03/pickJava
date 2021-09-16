/*     */ package org.apache.commons.math3.distribution;
/*     */ 
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
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
/*     */ public class ConstantRealDistribution
/*     */   extends AbstractRealDistribution
/*     */ {
/*     */   private static final long serialVersionUID = -4157745166772046273L;
/*     */   private final double value;
/*     */   
/*     */   public ConstantRealDistribution(double value) {
/*  41 */     super(null);
/*  42 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public double density(double x) {
/*  47 */     return (x == this.value) ? 1.0D : 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double cumulativeProbability(double x) {
/*  52 */     return (x < this.value) ? 0.0D : 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double inverseCumulativeProbability(double p) throws OutOfRangeException {
/*  59 */     if (p < 0.0D || p > 1.0D) {
/*  60 */       throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
/*     */     }
/*  62 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalMean() {
/*  69 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNumericalVariance() {
/*  76 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSupportLowerBound() {
/*  83 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSupportUpperBound() {
/*  90 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportLowerBoundInclusive() {
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportUpperBoundInclusive() {
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportConnected() {
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double sample() {
/* 113 */     return this.value;
/*     */   }
/*     */   
/*     */   public void reseedRandomGenerator(long seed) {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/distribution/ConstantRealDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */