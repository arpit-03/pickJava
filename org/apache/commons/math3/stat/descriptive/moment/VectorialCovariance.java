/*     */ package org.apache.commons.math3.stat.descriptive.moment;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.linear.MatrixUtils;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VectorialCovariance
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4118372414238930270L;
/*     */   private final double[] sums;
/*     */   private final double[] productsSums;
/*     */   private final boolean isBiasCorrected;
/*     */   private long n;
/*     */   
/*     */   public VectorialCovariance(int dimension, boolean isBiasCorrected) {
/*  53 */     this.sums = new double[dimension];
/*  54 */     this.productsSums = new double[dimension * (dimension + 1) / 2];
/*  55 */     this.n = 0L;
/*  56 */     this.isBiasCorrected = isBiasCorrected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment(double[] v) throws DimensionMismatchException {
/*  65 */     if (v.length != this.sums.length) {
/*  66 */       throw new DimensionMismatchException(v.length, this.sums.length);
/*     */     }
/*  68 */     int k = 0;
/*  69 */     for (int i = 0; i < v.length; i++) {
/*  70 */       this.sums[i] = this.sums[i] + v[i];
/*  71 */       for (int j = 0; j <= i; j++) {
/*  72 */         this.productsSums[k++] = this.productsSums[k++] + v[i] * v[j];
/*     */       }
/*     */     } 
/*  75 */     this.n++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getResult() {
/*  84 */     int dimension = this.sums.length;
/*  85 */     RealMatrix result = MatrixUtils.createRealMatrix(dimension, dimension);
/*     */     
/*  87 */     if (this.n > 1L) {
/*  88 */       double c = 1.0D / (this.n * (this.isBiasCorrected ? (this.n - 1L) : this.n));
/*  89 */       int k = 0;
/*  90 */       for (int i = 0; i < dimension; i++) {
/*  91 */         for (int j = 0; j <= i; j++) {
/*  92 */           double e = c * (this.n * this.productsSums[k++] - this.sums[i] * this.sums[j]);
/*  93 */           result.setEntry(i, j, e);
/*  94 */           result.setEntry(j, i, e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/* 108 */     return this.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 115 */     this.n = 0L;
/* 116 */     Arrays.fill(this.sums, 0.0D);
/* 117 */     Arrays.fill(this.productsSums, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 123 */     int prime = 31;
/* 124 */     int result = 1;
/* 125 */     result = 31 * result + (this.isBiasCorrected ? 1231 : 1237);
/* 126 */     result = 31 * result + (int)(this.n ^ this.n >>> 32L);
/* 127 */     result = 31 * result + Arrays.hashCode(this.productsSums);
/* 128 */     result = 31 * result + Arrays.hashCode(this.sums);
/* 129 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 135 */     if (this == obj) {
/* 136 */       return true;
/*     */     }
/* 138 */     if (!(obj instanceof VectorialCovariance)) {
/* 139 */       return false;
/*     */     }
/* 141 */     VectorialCovariance other = (VectorialCovariance)obj;
/* 142 */     if (this.isBiasCorrected != other.isBiasCorrected) {
/* 143 */       return false;
/*     */     }
/* 145 */     if (this.n != other.n) {
/* 146 */       return false;
/*     */     }
/* 148 */     if (!Arrays.equals(this.productsSums, other.productsSums)) {
/* 149 */       return false;
/*     */     }
/* 151 */     if (!Arrays.equals(this.sums, other.sums)) {
/* 152 */       return false;
/*     */     }
/* 154 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/moment/VectorialCovariance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */