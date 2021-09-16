/*     */ package org.apache.commons.math3.stat.descriptive;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractUnivariateStatistic
/*     */   implements UnivariateStatistic
/*     */ {
/*     */   private double[] storedData;
/*     */   
/*     */   public void setData(double[] values) {
/*  53 */     this.storedData = (values == null) ? null : (double[])values.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getData() {
/*  61 */     return (this.storedData == null) ? null : (double[])this.storedData.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double[] getDataRef() {
/*  69 */     return this.storedData;
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
/*     */   public void setData(double[] values, int begin, int length) throws MathIllegalArgumentException {
/*  84 */     if (values == null) {
/*  85 */       throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
/*     */     }
/*     */     
/*  88 */     if (begin < 0) {
/*  89 */       throw new NotPositiveException(LocalizedFormats.START_POSITION, Integer.valueOf(begin));
/*     */     }
/*     */     
/*  92 */     if (length < 0) {
/*  93 */       throw new NotPositiveException(LocalizedFormats.LENGTH, Integer.valueOf(length));
/*     */     }
/*     */     
/*  96 */     if (begin + length > values.length) {
/*  97 */       throw new NumberIsTooLargeException(LocalizedFormats.SUBARRAY_ENDS_AFTER_ARRAY_END, Integer.valueOf(begin + length), Integer.valueOf(values.length), true);
/*     */     }
/*     */     
/* 100 */     this.storedData = new double[length];
/* 101 */     System.arraycopy(values, begin, this.storedData, 0, length);
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
/*     */   public double evaluate() throws MathIllegalArgumentException {
/* 113 */     return evaluate(this.storedData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double evaluate(double[] values) throws MathIllegalArgumentException {
/* 120 */     test(values, 0, 0);
/* 121 */     return evaluate(values, 0, values.length);
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
/*     */   public abstract double evaluate(double[] paramArrayOfdouble, int paramInt1, int paramInt2) throws MathIllegalArgumentException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract UnivariateStatistic copy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean test(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 158 */     return MathArrays.verifyValues(values, begin, length, false);
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
/*     */   protected boolean test(double[] values, int begin, int length, boolean allowEmpty) throws MathIllegalArgumentException {
/* 184 */     return MathArrays.verifyValues(values, begin, length, allowEmpty);
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
/*     */   protected boolean test(double[] values, double[] weights, int begin, int length) throws MathIllegalArgumentException {
/* 221 */     return MathArrays.verifyValues(values, weights, begin, length, false);
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
/*     */   
/*     */   protected boolean test(double[] values, double[] weights, int begin, int length, boolean allowEmpty) throws MathIllegalArgumentException {
/* 260 */     return MathArrays.verifyValues(values, weights, begin, length, allowEmpty);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/AbstractUnivariateStatistic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */