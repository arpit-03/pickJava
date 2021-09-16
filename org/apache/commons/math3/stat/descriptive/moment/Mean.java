/*     */ package org.apache.commons.math3.stat.descriptive.moment;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.stat.descriptive.AbstractStorelessUnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.StorelessUnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.UnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.WeightedEvaluation;
/*     */ import org.apache.commons.math3.stat.descriptive.summary.Sum;
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
/*     */ public class Mean
/*     */   extends AbstractStorelessUnivariateStatistic
/*     */   implements Serializable, WeightedEvaluation
/*     */ {
/*     */   private static final long serialVersionUID = -1296043746617791564L;
/*     */   protected FirstMoment moment;
/*     */   protected boolean incMoment;
/*     */   
/*     */   public Mean() {
/*  82 */     this.incMoment = true;
/*  83 */     this.moment = new FirstMoment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mean(FirstMoment m1) {
/*  92 */     this.moment = m1;
/*  93 */     this.incMoment = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mean(Mean original) throws NullArgumentException {
/* 104 */     copy(original, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment(double d) {
/* 115 */     if (this.incMoment) {
/* 116 */       this.moment.increment(d);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 125 */     if (this.incMoment) {
/* 126 */       this.moment.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getResult() {
/* 135 */     return this.moment.m1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/* 142 */     return this.moment.getN();
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
/*     */   public double evaluate(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 164 */     if (test(values, begin, length)) {
/* 165 */       Sum sum = new Sum();
/* 166 */       double sampleSize = length;
/*     */ 
/*     */       
/* 169 */       double xbar = sum.evaluate(values, begin, length) / sampleSize;
/*     */ 
/*     */       
/* 172 */       double correction = 0.0D;
/* 173 */       for (int i = begin; i < begin + length; i++) {
/* 174 */         correction += values[i] - xbar;
/*     */       }
/* 176 */       return xbar + correction / sampleSize;
/*     */     } 
/* 178 */     return Double.NaN;
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
/*     */   public double evaluate(double[] values, double[] weights, int begin, int length) throws MathIllegalArgumentException {
/* 212 */     if (test(values, weights, begin, length)) {
/* 213 */       Sum sum = new Sum();
/*     */ 
/*     */       
/* 216 */       double sumw = sum.evaluate(weights, begin, length);
/* 217 */       double xbarw = sum.evaluate(values, weights, begin, length) / sumw;
/*     */ 
/*     */       
/* 220 */       double correction = 0.0D;
/* 221 */       for (int i = begin; i < begin + length; i++) {
/* 222 */         correction += weights[i] * (values[i] - xbarw);
/*     */       }
/* 224 */       return xbarw + correction / sumw;
/*     */     } 
/* 226 */     return Double.NaN;
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
/*     */   public double evaluate(double[] values, double[] weights) throws MathIllegalArgumentException {
/* 255 */     return evaluate(values, weights, 0, values.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mean copy() {
/* 263 */     Mean result = new Mean();
/*     */     
/* 265 */     copy(this, result);
/* 266 */     return result;
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
/*     */   public static void copy(Mean source, Mean dest) throws NullArgumentException {
/* 280 */     MathUtils.checkNotNull(source);
/* 281 */     MathUtils.checkNotNull(dest);
/* 282 */     dest.setData(source.getDataRef());
/* 283 */     dest.incMoment = source.incMoment;
/* 284 */     dest.moment = source.moment.copy();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/moment/Mean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */