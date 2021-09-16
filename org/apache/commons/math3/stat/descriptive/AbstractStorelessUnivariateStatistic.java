/*     */ package org.apache.commons.math3.stat.descriptive;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractStorelessUnivariateStatistic
/*     */   extends AbstractUnivariateStatistic
/*     */   implements StorelessUnivariateStatistic
/*     */ {
/*     */   public double evaluate(double[] values) throws MathIllegalArgumentException {
/*  60 */     if (values == null) {
/*  61 */       throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
/*     */     }
/*  63 */     return evaluate(values, 0, values.length);
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
/*  91 */     if (test(values, begin, length)) {
/*  92 */       clear();
/*  93 */       incrementAll(values, begin, length);
/*     */     } 
/*  95 */     return getResult();
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
/*     */   public void incrementAll(double[] values) throws MathIllegalArgumentException {
/* 130 */     if (values == null) {
/* 131 */       throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
/*     */     }
/* 133 */     incrementAll(values, 0, values.length);
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
/*     */   public void incrementAll(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 149 */     if (test(values, begin, length)) {
/* 150 */       int k = begin + length;
/* 151 */       for (int i = begin; i < k; i++) {
/* 152 */         increment(values[i]);
/*     */       }
/*     */     } 
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
/*     */   public boolean equals(Object object) {
/* 166 */     if (object == this) {
/* 167 */       return true;
/*     */     }
/* 169 */     if (!(object instanceof AbstractStorelessUnivariateStatistic)) {
/* 170 */       return false;
/*     */     }
/* 172 */     AbstractStorelessUnivariateStatistic stat = (AbstractStorelessUnivariateStatistic)object;
/* 173 */     return (Precision.equalsIncludingNaN(stat.getResult(), getResult()) && Precision.equalsIncludingNaN((float)stat.getN(), (float)getN()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 184 */     return 31 * (31 + MathUtils.hash(getResult())) + MathUtils.hash(getN());
/*     */   }
/*     */   
/*     */   public abstract StorelessUnivariateStatistic copy();
/*     */   
/*     */   public abstract void clear();
/*     */   
/*     */   public abstract double getResult();
/*     */   
/*     */   public abstract void increment(double paramDouble);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/AbstractStorelessUnivariateStatistic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */