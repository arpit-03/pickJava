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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Kurtosis
/*     */   extends AbstractStorelessUnivariateStatistic
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2784465764798260919L;
/*     */   protected FourthMoment moment;
/*     */   protected boolean incMoment;
/*     */   
/*     */   public Kurtosis() {
/*  69 */     this.incMoment = true;
/*  70 */     this.moment = new FourthMoment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Kurtosis(FourthMoment m4) {
/*  79 */     this.incMoment = false;
/*  80 */     this.moment = m4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Kurtosis(Kurtosis original) throws NullArgumentException {
/*  91 */     copy(original, this);
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
/* 102 */     if (this.incMoment) {
/* 103 */       this.moment.increment(d);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getResult() {
/* 112 */     double kurtosis = Double.NaN;
/* 113 */     if (this.moment.getN() > 3L) {
/* 114 */       double variance = this.moment.m2 / (this.moment.n - 1L);
/* 115 */       if (this.moment.n <= 3L || variance < 1.0E-19D) {
/* 116 */         kurtosis = 0.0D;
/*     */       } else {
/* 118 */         double n = this.moment.n;
/* 119 */         kurtosis = (n * (n + 1.0D) * this.moment.getResult() - 3.0D * this.moment.m2 * this.moment.m2 * (n - 1.0D)) / (n - 1.0D) * (n - 2.0D) * (n - 3.0D) * variance * variance;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 125 */     return kurtosis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 133 */     if (this.incMoment) {
/* 134 */       this.moment.clear();
/*     */     }
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
/*     */ 
/*     */   
/*     */   public double evaluate(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 166 */     double kurt = Double.NaN;
/*     */     
/* 168 */     if (test(values, begin, length) && length > 3) {
/*     */ 
/*     */       
/* 171 */       Variance variance = new Variance();
/* 172 */       variance.incrementAll(values, begin, length);
/* 173 */       double mean = variance.moment.m1;
/* 174 */       double stdDev = FastMath.sqrt(variance.getResult());
/*     */ 
/*     */ 
/*     */       
/* 178 */       double accum3 = 0.0D;
/* 179 */       for (int i = begin; i < begin + length; i++) {
/* 180 */         accum3 += FastMath.pow(values[i] - mean, 4.0D);
/*     */       }
/* 182 */       accum3 /= FastMath.pow(stdDev, 4.0D);
/*     */ 
/*     */       
/* 185 */       double n0 = length;
/*     */       
/* 187 */       double coefficientOne = n0 * (n0 + 1.0D) / (n0 - 1.0D) * (n0 - 2.0D) * (n0 - 3.0D);
/*     */       
/* 189 */       double termTwo = 3.0D * FastMath.pow(n0 - 1.0D, 2.0D) / (n0 - 2.0D) * (n0 - 3.0D);
/*     */ 
/*     */ 
/*     */       
/* 193 */       kurt = coefficientOne * accum3 - termTwo;
/*     */     } 
/* 195 */     return kurt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Kurtosis copy() {
/* 203 */     Kurtosis result = new Kurtosis();
/*     */     
/* 205 */     copy(this, result);
/* 206 */     return result;
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
/*     */   public static void copy(Kurtosis source, Kurtosis dest) throws NullArgumentException {
/* 219 */     MathUtils.checkNotNull(source);
/* 220 */     MathUtils.checkNotNull(dest);
/* 221 */     dest.setData(source.getDataRef());
/* 222 */     dest.moment = source.moment.copy();
/* 223 */     dest.incMoment = source.incMoment;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/moment/Kurtosis.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */