/*     */ package org.apache.commons.math3.complex;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.ZeroException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RootsOfUnity
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20120201L;
/*  70 */   private int omegaCount = 0;
/*  71 */   private double[] omegaReal = null;
/*  72 */   private double[] omegaImaginaryCounterClockwise = null;
/*  73 */   private double[] omegaImaginaryClockwise = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isCounterClockWise = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isCounterClockWise() throws MathIllegalStateException {
/*  90 */     if (this.omegaCount == 0) {
/*  91 */       throw new MathIllegalStateException(LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET, new Object[0]);
/*     */     }
/*     */     
/*  94 */     return this.isCounterClockWise;
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
/*     */   public synchronized void computeRoots(int n) throws ZeroException {
/* 118 */     if (n == 0) {
/* 119 */       throw new ZeroException(LocalizedFormats.CANNOT_COMPUTE_0TH_ROOT_OF_UNITY, new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 123 */     this.isCounterClockWise = (n > 0);
/*     */ 
/*     */     
/* 126 */     int absN = FastMath.abs(n);
/*     */     
/* 128 */     if (absN == this.omegaCount) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 133 */     double t = 6.283185307179586D / absN;
/* 134 */     double cosT = FastMath.cos(t);
/* 135 */     double sinT = FastMath.sin(t);
/* 136 */     this.omegaReal = new double[absN];
/* 137 */     this.omegaImaginaryCounterClockwise = new double[absN];
/* 138 */     this.omegaImaginaryClockwise = new double[absN];
/* 139 */     this.omegaReal[0] = 1.0D;
/* 140 */     this.omegaImaginaryCounterClockwise[0] = 0.0D;
/* 141 */     this.omegaImaginaryClockwise[0] = 0.0D;
/* 142 */     for (int i = 1; i < absN; i++) {
/* 143 */       this.omegaReal[i] = this.omegaReal[i - 1] * cosT - this.omegaImaginaryCounterClockwise[i - 1] * sinT;
/*     */       
/* 145 */       this.omegaImaginaryCounterClockwise[i] = this.omegaReal[i - 1] * sinT + this.omegaImaginaryCounterClockwise[i - 1] * cosT;
/*     */       
/* 147 */       this.omegaImaginaryClockwise[i] = -this.omegaImaginaryCounterClockwise[i];
/*     */     } 
/* 149 */     this.omegaCount = absN;
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
/*     */   public synchronized double getReal(int k) throws MathIllegalStateException, MathIllegalArgumentException {
/* 164 */     if (this.omegaCount == 0) {
/* 165 */       throw new MathIllegalStateException(LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET, new Object[0]);
/*     */     }
/*     */     
/* 168 */     if (k < 0 || k >= this.omegaCount) {
/* 169 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE_ROOT_OF_UNITY_INDEX, Integer.valueOf(k), Integer.valueOf(0), Integer.valueOf(this.omegaCount - 1));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     return this.omegaReal[k];
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
/*     */   public synchronized double getImaginary(int k) throws MathIllegalStateException, OutOfRangeException {
/* 191 */     if (this.omegaCount == 0) {
/* 192 */       throw new MathIllegalStateException(LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET, new Object[0]);
/*     */     }
/*     */     
/* 195 */     if (k < 0 || k >= this.omegaCount) {
/* 196 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE_ROOT_OF_UNITY_INDEX, Integer.valueOf(k), Integer.valueOf(0), Integer.valueOf(this.omegaCount - 1));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     return this.isCounterClockWise ? this.omegaImaginaryCounterClockwise[k] : this.omegaImaginaryClockwise[k];
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
/*     */   public synchronized int getNumberOfRoots() {
/* 216 */     return this.omegaCount;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/complex/RootsOfUnity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */