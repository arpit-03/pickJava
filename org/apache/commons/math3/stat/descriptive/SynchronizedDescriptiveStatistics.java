/*     */ package org.apache.commons.math3.stat.descriptive;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.util.MathUtils;
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
/*     */ public class SynchronizedDescriptiveStatistics
/*     */   extends DescriptiveStatistics
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public SynchronizedDescriptiveStatistics() {
/*  45 */     this(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynchronizedDescriptiveStatistics(int window) throws MathIllegalArgumentException {
/*  55 */     super(window);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynchronizedDescriptiveStatistics(SynchronizedDescriptiveStatistics original) throws NullArgumentException {
/*  66 */     copy(original, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addValue(double v) {
/*  74 */     super.addValue(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double apply(UnivariateStatistic stat) {
/*  82 */     return super.apply(stat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void clear() {
/*  90 */     super.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double getElement(int index) {
/*  98 */     return super.getElement(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long getN() {
/* 106 */     return super.getN();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double getStandardDeviation() {
/* 114 */     return super.getStandardDeviation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double getQuadraticMean() {
/* 122 */     return super.getQuadraticMean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized double[] getValues() {
/* 130 */     return super.getValues();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getWindowSize() {
/* 138 */     return super.getWindowSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setWindowSize(int windowSize) throws MathIllegalArgumentException {
/* 146 */     super.setWindowSize(windowSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String toString() {
/* 154 */     return super.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized SynchronizedDescriptiveStatistics copy() {
/* 165 */     SynchronizedDescriptiveStatistics result = new SynchronizedDescriptiveStatistics();
/*     */ 
/*     */     
/* 168 */     copy(this, result);
/* 169 */     return result;
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
/*     */   public static void copy(SynchronizedDescriptiveStatistics source, SynchronizedDescriptiveStatistics dest) throws NullArgumentException {
/* 184 */     MathUtils.checkNotNull(source);
/* 185 */     MathUtils.checkNotNull(dest);
/* 186 */     synchronized (source) {
/* 187 */       synchronized (dest) {
/* 188 */         DescriptiveStatistics.copy(source, dest);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/SynchronizedDescriptiveStatistics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */