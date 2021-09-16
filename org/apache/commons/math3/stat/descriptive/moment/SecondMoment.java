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
/*     */ public class SecondMoment
/*     */   extends FirstMoment
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3942403127395076445L;
/*     */   protected double m2;
/*     */   
/*     */   public SecondMoment() {
/*  62 */     this.m2 = Double.NaN;
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
/*     */   public SecondMoment(SecondMoment original) throws NullArgumentException {
/*  74 */     super(original);
/*  75 */     this.m2 = original.m2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment(double d) {
/*  83 */     if (this.n < 1L) {
/*  84 */       this.m1 = this.m2 = 0.0D;
/*     */     }
/*  86 */     super.increment(d);
/*  87 */     this.m2 += (this.n - 1.0D) * this.dev * this.nDev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  95 */     super.clear();
/*  96 */     this.m2 = Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getResult() {
/* 104 */     return this.m2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecondMoment copy() {
/* 112 */     SecondMoment result = new SecondMoment();
/*     */     
/* 114 */     copy(this, result);
/* 115 */     return result;
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
/*     */   public static void copy(SecondMoment source, SecondMoment dest) throws NullArgumentException {
/* 128 */     MathUtils.checkNotNull(source);
/* 129 */     MathUtils.checkNotNull(dest);
/* 130 */     FirstMoment.copy(source, dest);
/* 131 */     dest.m2 = source.m2;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/moment/SecondMoment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */