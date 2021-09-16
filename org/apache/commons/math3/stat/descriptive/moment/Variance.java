/*     */ package org.apache.commons.math3.stat.descriptive.moment;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.stat.descriptive.AbstractStorelessUnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.StorelessUnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.UnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.WeightedEvaluation;
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
/*     */ public class Variance
/*     */   extends AbstractStorelessUnivariateStatistic
/*     */   implements Serializable, WeightedEvaluation
/*     */ {
/*     */   private static final long serialVersionUID = -9111962718267217978L;
/*  75 */   protected SecondMoment moment = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean incMoment = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBiasCorrected = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Variance() {
/*  98 */     this.moment = new SecondMoment();
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
/*     */   public Variance(SecondMoment m2) {
/* 112 */     this.incMoment = false;
/* 113 */     this.moment = m2;
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
/*     */   public Variance(boolean isBiasCorrected) {
/* 125 */     this.moment = new SecondMoment();
/* 126 */     this.isBiasCorrected = isBiasCorrected;
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
/*     */   public Variance(boolean isBiasCorrected, SecondMoment m2) {
/* 139 */     this.incMoment = false;
/* 140 */     this.moment = m2;
/* 141 */     this.isBiasCorrected = isBiasCorrected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Variance(Variance original) throws NullArgumentException {
/* 152 */     copy(original, this);
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
/*     */   public void increment(double d) {
/* 170 */     if (this.incMoment) {
/* 171 */       this.moment.increment(d);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getResult() {
/* 180 */     if (this.moment.n == 0L)
/* 181 */       return Double.NaN; 
/* 182 */     if (this.moment.n == 1L) {
/* 183 */       return 0.0D;
/*     */     }
/* 185 */     if (this.isBiasCorrected) {
/* 186 */       return this.moment.m2 / (this.moment.n - 1.0D);
/*     */     }
/* 188 */     return this.moment.m2 / this.moment.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/* 197 */     return this.moment.getN();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 205 */     if (this.incMoment) {
/* 206 */       this.moment.clear();
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
/*     */   public double evaluate(double[] values) throws MathIllegalArgumentException {
/* 228 */     if (values == null) {
/* 229 */       throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
/*     */     }
/* 231 */     return evaluate(values, 0, values.length);
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
/* 259 */     double var = Double.NaN;
/*     */     
/* 261 */     if (test(values, begin, length)) {
/* 262 */       clear();
/* 263 */       if (length == 1) {
/* 264 */         var = 0.0D;
/* 265 */       } else if (length > 1) {
/* 266 */         Mean mean = new Mean();
/* 267 */         double m = mean.evaluate(values, begin, length);
/* 268 */         var = evaluate(values, m, begin, length);
/*     */       } 
/*     */     } 
/* 271 */     return var;
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
/* 319 */     double var = Double.NaN;
/*     */     
/* 321 */     if (test(values, weights, begin, length)) {
/* 322 */       clear();
/* 323 */       if (length == 1) {
/* 324 */         var = 0.0D;
/* 325 */       } else if (length > 1) {
/* 326 */         Mean mean = new Mean();
/* 327 */         double m = mean.evaluate(values, weights, begin, length);
/* 328 */         var = evaluate(values, weights, m, begin, length);
/*     */       } 
/*     */     } 
/* 331 */     return var;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double evaluate(double[] values, double[] weights) throws MathIllegalArgumentException {
/* 374 */     return evaluate(values, weights, 0, values.length);
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
/*     */   public double evaluate(double[] values, double mean, int begin, int length) throws MathIllegalArgumentException {
/* 406 */     double var = Double.NaN;
/*     */     
/* 408 */     if (test(values, begin, length)) {
/* 409 */       if (length == 1) {
/* 410 */         var = 0.0D;
/* 411 */       } else if (length > 1) {
/* 412 */         double accum = 0.0D;
/* 413 */         double dev = 0.0D;
/* 414 */         double accum2 = 0.0D;
/* 415 */         for (int i = begin; i < begin + length; i++) {
/* 416 */           dev = values[i] - mean;
/* 417 */           accum += dev * dev;
/* 418 */           accum2 += dev;
/*     */         } 
/* 420 */         double len = length;
/* 421 */         if (this.isBiasCorrected) {
/* 422 */           var = (accum - accum2 * accum2 / len) / (len - 1.0D);
/*     */         } else {
/* 424 */           var = (accum - accum2 * accum2 / len) / len;
/*     */         } 
/*     */       } 
/*     */     }
/* 428 */     return var;
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
/*     */   public double evaluate(double[] values, double mean) throws MathIllegalArgumentException {
/* 457 */     return evaluate(values, mean, 0, values.length);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double evaluate(double[] values, double[] weights, double mean, int begin, int length) throws MathIllegalArgumentException {
/* 509 */     double var = Double.NaN;
/*     */     
/* 511 */     if (test(values, weights, begin, length)) {
/* 512 */       if (length == 1) {
/* 513 */         var = 0.0D;
/* 514 */       } else if (length > 1) {
/* 515 */         double accum = 0.0D;
/* 516 */         double dev = 0.0D;
/* 517 */         double accum2 = 0.0D;
/* 518 */         for (int i = begin; i < begin + length; i++) {
/* 519 */           dev = values[i] - mean;
/* 520 */           accum += weights[i] * dev * dev;
/* 521 */           accum2 += weights[i] * dev;
/*     */         } 
/*     */         
/* 524 */         double sumWts = 0.0D;
/* 525 */         for (int j = begin; j < begin + length; j++) {
/* 526 */           sumWts += weights[j];
/*     */         }
/*     */         
/* 529 */         if (this.isBiasCorrected) {
/* 530 */           var = (accum - accum2 * accum2 / sumWts) / (sumWts - 1.0D);
/*     */         } else {
/* 532 */           var = (accum - accum2 * accum2 / sumWts) / sumWts;
/*     */         } 
/*     */       } 
/*     */     }
/* 536 */     return var;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double evaluate(double[] values, double[] weights, double mean) throws MathIllegalArgumentException {
/* 582 */     return evaluate(values, weights, mean, 0, values.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBiasCorrected() {
/* 589 */     return this.isBiasCorrected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBiasCorrected(boolean biasCorrected) {
/* 596 */     this.isBiasCorrected = biasCorrected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Variance copy() {
/* 604 */     Variance result = new Variance();
/*     */     
/* 606 */     copy(this, result);
/* 607 */     return result;
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
/*     */   public static void copy(Variance source, Variance dest) throws NullArgumentException {
/* 620 */     MathUtils.checkNotNull(source);
/* 621 */     MathUtils.checkNotNull(dest);
/* 622 */     dest.setData(source.getDataRef());
/* 623 */     dest.moment = source.moment.copy();
/* 624 */     dest.isBiasCorrected = source.isBiasCorrected;
/* 625 */     dest.incMoment = source.incMoment;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/moment/Variance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */