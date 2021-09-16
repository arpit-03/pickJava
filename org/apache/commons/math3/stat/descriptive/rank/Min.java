/*     */ package org.apache.commons.math3.stat.descriptive.rank;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.stat.descriptive.AbstractStorelessUnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.StorelessUnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.UnivariateStatistic;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Min
/*     */   extends AbstractStorelessUnivariateStatistic
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2941995784909003131L;
/*     */   private long n;
/*     */   private double value;
/*     */   
/*     */   public Min() {
/*  57 */     this.n = 0L;
/*  58 */     this.value = Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Min(Min original) throws NullArgumentException {
/*  69 */     copy(original, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment(double d) {
/*  77 */     if (d < this.value || Double.isNaN(this.value)) {
/*  78 */       this.value = d;
/*     */     }
/*  80 */     this.n++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  88 */     this.value = Double.NaN;
/*  89 */     this.n = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getResult() {
/*  97 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/* 104 */     return this.n;
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
/*     */   public double evaluate(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 132 */     double min = Double.NaN;
/* 133 */     if (test(values, begin, length)) {
/* 134 */       min = values[begin];
/* 135 */       for (int i = begin; i < begin + length; i++) {
/* 136 */         if (!Double.isNaN(values[i])) {
/* 137 */           min = (min < values[i]) ? min : values[i];
/*     */         }
/*     */       } 
/*     */     } 
/* 141 */     return min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Min copy() {
/* 149 */     Min result = new Min();
/*     */     
/* 151 */     copy(this, result);
/* 152 */     return result;
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
/*     */   public static void copy(Min source, Min dest) throws NullArgumentException {
/* 165 */     MathUtils.checkNotNull(source);
/* 166 */     MathUtils.checkNotNull(dest);
/* 167 */     dest.setData(source.getDataRef());
/* 168 */     dest.n = source.n;
/* 169 */     dest.value = source.value;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/rank/Min.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */