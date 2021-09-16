/*     */ package org.apache.commons.math3.stat.descriptive.moment;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.stat.descriptive.AbstractStorelessUnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.StorelessUnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.UnivariateStatistic;
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ public class Skewness
/*     */   extends AbstractStorelessUnivariateStatistic
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7101857578996691352L;
/*  54 */   protected ThirdMoment moment = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean incMoment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Skewness() {
/*  68 */     this.incMoment = true;
/*  69 */     this.moment = new ThirdMoment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Skewness(ThirdMoment m3) {
/*  77 */     this.incMoment = false;
/*  78 */     this.moment = m3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Skewness(Skewness original) throws NullArgumentException {
/*  89 */     copy(original, this);
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
/* 100 */     if (this.incMoment) {
/* 101 */       this.moment.increment(d);
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
/*     */   
/*     */   public double getResult() {
/* 115 */     if (this.moment.n < 3L) {
/* 116 */       return Double.NaN;
/*     */     }
/* 118 */     double variance = this.moment.m2 / (this.moment.n - 1L);
/* 119 */     if (variance < 1.0E-19D) {
/* 120 */       return 0.0D;
/*     */     }
/* 122 */     double n0 = this.moment.getN();
/* 123 */     return n0 * this.moment.m3 / (n0 - 1.0D) * (n0 - 2.0D) * FastMath.sqrt(variance) * variance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/* 132 */     return this.moment.getN();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 140 */     if (this.incMoment) {
/* 141 */       this.moment.clear();
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
/*     */ 
/*     */ 
/*     */ 
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
/* 166 */     double skew = Double.NaN;
/*     */     
/* 168 */     if (test(values, begin, length) && length > 2) {
/* 169 */       Mean mean = new Mean();
/*     */       
/* 171 */       double m = mean.evaluate(values, begin, length);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 176 */       double accum = 0.0D;
/* 177 */       double accum2 = 0.0D;
/* 178 */       for (int i = begin; i < begin + length; i++) {
/* 179 */         double d = values[i] - m;
/* 180 */         accum += d * d;
/* 181 */         accum2 += d;
/*     */       } 
/* 183 */       double variance = (accum - accum2 * accum2 / length) / (length - 1);
/*     */       
/* 185 */       double accum3 = 0.0D;
/* 186 */       for (int j = begin; j < begin + length; j++) {
/* 187 */         double d = values[j] - m;
/* 188 */         accum3 += d * d * d;
/*     */       } 
/* 190 */       accum3 /= variance * FastMath.sqrt(variance);
/*     */ 
/*     */       
/* 193 */       double n0 = length;
/*     */ 
/*     */       
/* 196 */       skew = n0 / (n0 - 1.0D) * (n0 - 2.0D) * accum3;
/*     */     } 
/* 198 */     return skew;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Skewness copy() {
/* 206 */     Skewness result = new Skewness();
/*     */     
/* 208 */     copy(this, result);
/* 209 */     return result;
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
/*     */   public static void copy(Skewness source, Skewness dest) throws NullArgumentException {
/* 222 */     MathUtils.checkNotNull(source);
/* 223 */     MathUtils.checkNotNull(dest);
/* 224 */     dest.setData(source.getDataRef());
/* 225 */     dest.moment = new ThirdMoment(source.moment.copy());
/* 226 */     dest.incMoment = source.incMoment;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/moment/Skewness.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */