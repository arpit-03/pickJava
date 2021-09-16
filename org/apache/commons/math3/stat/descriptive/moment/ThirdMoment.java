/*     */ package org.apache.commons.math3.stat.descriptive.moment;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
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
/*     */ class ThirdMoment
/*     */   extends SecondMoment
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7818711964045118679L;
/*     */   protected double m3;
/*     */   protected double nDevSq;
/*     */   
/*     */   ThirdMoment() {
/*  71 */     this.m3 = Double.NaN;
/*  72 */     this.nDevSq = Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ThirdMoment(ThirdMoment original) throws NullArgumentException {
/*  83 */     copy(original, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment(double d) {
/*  91 */     if (this.n < 1L) {
/*  92 */       this.m3 = this.m2 = this.m1 = 0.0D;
/*     */     }
/*     */     
/*  95 */     double prevM2 = this.m2;
/*  96 */     super.increment(d);
/*  97 */     this.nDevSq = this.nDev * this.nDev;
/*  98 */     double n0 = this.n;
/*  99 */     this.m3 = this.m3 - 3.0D * this.nDev * prevM2 + (n0 - 1.0D) * (n0 - 2.0D) * this.nDevSq * this.dev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getResult() {
/* 107 */     return this.m3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 115 */     super.clear();
/* 116 */     this.m3 = Double.NaN;
/* 117 */     this.nDevSq = Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThirdMoment copy() {
/* 125 */     ThirdMoment result = new ThirdMoment();
/*     */     
/* 127 */     copy(this, result);
/* 128 */     return result;
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
/*     */   public static void copy(ThirdMoment source, ThirdMoment dest) throws NullArgumentException {
/* 141 */     MathUtils.checkNotNull(source);
/* 142 */     MathUtils.checkNotNull(dest);
/* 143 */     SecondMoment.copy(source, dest);
/* 144 */     dest.m3 = source.m3;
/* 145 */     dest.nDevSq = source.nDevSq;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/moment/ThirdMoment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */