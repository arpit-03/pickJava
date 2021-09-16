/*     */ package org.apache.commons.math3.stat.descriptive.moment;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ class FirstMoment
/*     */   extends AbstractStorelessUnivariateStatistic
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6112755307178490473L;
/*     */   protected long n;
/*     */   protected double m1;
/*     */   protected double dev;
/*     */   protected double nDev;
/*     */   
/*     */   FirstMoment() {
/*  81 */     this.n = 0L;
/*  82 */     this.m1 = Double.NaN;
/*  83 */     this.dev = Double.NaN;
/*  84 */     this.nDev = Double.NaN;
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
/*     */   FirstMoment(FirstMoment original) throws NullArgumentException {
/*  96 */     copy(original, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment(double d) {
/* 104 */     if (this.n == 0L) {
/* 105 */       this.m1 = 0.0D;
/*     */     }
/* 107 */     this.n++;
/* 108 */     double n0 = this.n;
/* 109 */     this.dev = d - this.m1;
/* 110 */     this.nDev = this.dev / n0;
/* 111 */     this.m1 += this.nDev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 119 */     this.m1 = Double.NaN;
/* 120 */     this.n = 0L;
/* 121 */     this.dev = Double.NaN;
/* 122 */     this.nDev = Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getResult() {
/* 130 */     return this.m1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/* 137 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FirstMoment copy() {
/* 145 */     FirstMoment result = new FirstMoment();
/*     */     
/* 147 */     copy(this, result);
/* 148 */     return result;
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
/*     */   public static void copy(FirstMoment source, FirstMoment dest) throws NullArgumentException {
/* 161 */     MathUtils.checkNotNull(source);
/* 162 */     MathUtils.checkNotNull(dest);
/* 163 */     dest.setData(source.getDataRef());
/* 164 */     dest.n = source.n;
/* 165 */     dest.m1 = source.m1;
/* 166 */     dest.dev = source.dev;
/* 167 */     dest.nDev = source.nDev;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/moment/FirstMoment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */