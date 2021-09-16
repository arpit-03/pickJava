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
/*     */ class FourthMoment
/*     */   extends ThirdMoment
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4763990447117157611L;
/*     */   private double m4;
/*     */   
/*     */   FourthMoment() {
/*  70 */     this.m4 = Double.NaN;
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
/*     */   FourthMoment(FourthMoment original) throws NullArgumentException {
/*  82 */     copy(original, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment(double d) {
/*  90 */     if (this.n < 1L) {
/*  91 */       this.m4 = 0.0D;
/*  92 */       this.m3 = 0.0D;
/*  93 */       this.m2 = 0.0D;
/*  94 */       this.m1 = 0.0D;
/*     */     } 
/*     */     
/*  97 */     double prevM3 = this.m3;
/*  98 */     double prevM2 = this.m2;
/*     */     
/* 100 */     super.increment(d);
/*     */     
/* 102 */     double n0 = this.n;
/*     */     
/* 104 */     this.m4 = this.m4 - 4.0D * this.nDev * prevM3 + 6.0D * this.nDevSq * prevM2 + (n0 * n0 - 3.0D * (n0 - 1.0D)) * this.nDevSq * this.nDevSq * (n0 - 1.0D) * n0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getResult() {
/* 113 */     return this.m4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 121 */     super.clear();
/* 122 */     this.m4 = Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FourthMoment copy() {
/* 130 */     FourthMoment result = new FourthMoment();
/*     */     
/* 132 */     copy(this, result);
/* 133 */     return result;
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
/*     */   public static void copy(FourthMoment source, FourthMoment dest) throws NullArgumentException {
/* 146 */     MathUtils.checkNotNull(source);
/* 147 */     MathUtils.checkNotNull(dest);
/* 148 */     ThirdMoment.copy(source, dest);
/* 149 */     dest.m4 = source.m4;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/moment/FourthMoment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */