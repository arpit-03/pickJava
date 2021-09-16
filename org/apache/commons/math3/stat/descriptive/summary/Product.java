/*     */ package org.apache.commons.math3.stat.descriptive.summary;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.stat.descriptive.AbstractStorelessUnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.StorelessUnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.UnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.WeightedEvaluation;
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
/*     */ public class Product
/*     */   extends AbstractStorelessUnivariateStatistic
/*     */   implements Serializable, WeightedEvaluation
/*     */ {
/*     */   private static final long serialVersionUID = 2824226005990582538L;
/*     */   private long n;
/*     */   private double value;
/*     */   
/*     */   public Product() {
/*  58 */     this.n = 0L;
/*  59 */     this.value = 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Product(Product original) throws NullArgumentException {
/*  70 */     copy(original, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment(double d) {
/*  78 */     this.value *= d;
/*  79 */     this.n++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getResult() {
/*  87 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/*  94 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 102 */     this.value = 1.0D;
/* 103 */     this.n = 0L;
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
/*     */   public double evaluate(double[] values, int begin, int length) throws MathIllegalArgumentException {
/* 123 */     double product = Double.NaN;
/* 124 */     if (test(values, begin, length, true)) {
/* 125 */       product = 1.0D;
/* 126 */       for (int i = begin; i < begin + length; i++) {
/* 127 */         product *= values[i];
/*     */       }
/*     */     } 
/* 130 */     return product;
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
/*     */   public double evaluate(double[] values, double[] weights, int begin, int length) throws MathIllegalArgumentException {
/* 163 */     double product = Double.NaN;
/* 164 */     if (test(values, weights, begin, length, true)) {
/* 165 */       product = 1.0D;
/* 166 */       for (int i = begin; i < begin + length; i++) {
/* 167 */         product *= FastMath.pow(values[i], weights[i]);
/*     */       }
/*     */     } 
/* 170 */     return product;
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
/*     */   public double evaluate(double[] values, double[] weights) throws MathIllegalArgumentException {
/* 198 */     return evaluate(values, weights, 0, values.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Product copy() {
/* 207 */     Product result = new Product();
/*     */     
/* 209 */     copy(this, result);
/* 210 */     return result;
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
/*     */   public static void copy(Product source, Product dest) throws NullArgumentException {
/* 223 */     MathUtils.checkNotNull(source);
/* 224 */     MathUtils.checkNotNull(dest);
/* 225 */     dest.setData(source.getDataRef());
/* 226 */     dest.n = source.n;
/* 227 */     dest.value = source.value;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/summary/Product.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */