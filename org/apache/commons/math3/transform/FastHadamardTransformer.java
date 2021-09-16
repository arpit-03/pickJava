/*     */ package org.apache.commons.math3.transform;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.analysis.FunctionUtils;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.ArithmeticUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FastHadamardTransformer
/*     */   implements RealTransformer, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 20120211L;
/*     */   
/*     */   public double[] transform(double[] f, TransformType type) {
/*  51 */     if (type == TransformType.FORWARD) {
/*  52 */       return fht(f);
/*     */     }
/*  54 */     return TransformUtils.scaleArray(fht(f), 1.0D / f.length);
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
/*     */   public double[] transform(UnivariateFunction f, double min, double max, int n, TransformType type) {
/*  70 */     return transform(FunctionUtils.sample(f, min, max, n), type);
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
/*     */   public int[] transform(int[] f) {
/*  83 */     return fht(f);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double[] fht(double[] x) throws MathIllegalArgumentException {
/* 230 */     int n = x.length;
/* 231 */     int halfN = n / 2;
/*     */     
/* 233 */     if (!ArithmeticUtils.isPowerOfTwo(n)) {
/* 234 */       throw new MathIllegalArgumentException(LocalizedFormats.NOT_POWER_OF_TWO, new Object[] { Integer.valueOf(n) });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     double[] yPrevious = new double[n];
/* 244 */     double[] yCurrent = (double[])x.clone();
/*     */     
/*     */     int j;
/* 247 */     for (j = 1; j < n; j <<= 1) {
/*     */ 
/*     */       
/* 250 */       double[] yTmp = yCurrent;
/* 251 */       yCurrent = yPrevious;
/* 252 */       yPrevious = yTmp;
/*     */       
/*     */       int i;
/* 255 */       for (i = 0; i < halfN; i++) {
/*     */         
/* 257 */         int twoI = 2 * i;
/* 258 */         yCurrent[i] = yPrevious[twoI] + yPrevious[twoI + 1];
/*     */       } 
/* 260 */       for (i = halfN; i < n; i++) {
/*     */         
/* 262 */         int twoI = 2 * i;
/* 263 */         yCurrent[i] = yPrevious[twoI - n] - yPrevious[twoI - n + 1];
/*     */       } 
/*     */     } 
/*     */     
/* 267 */     return yCurrent;
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
/*     */   protected int[] fht(int[] x) throws MathIllegalArgumentException {
/* 281 */     int n = x.length;
/* 282 */     int halfN = n / 2;
/*     */     
/* 284 */     if (!ArithmeticUtils.isPowerOfTwo(n)) {
/* 285 */       throw new MathIllegalArgumentException(LocalizedFormats.NOT_POWER_OF_TWO, new Object[] { Integer.valueOf(n) });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     int[] yPrevious = new int[n];
/* 295 */     int[] yCurrent = (int[])x.clone();
/*     */     
/*     */     int j;
/* 298 */     for (j = 1; j < n; j <<= 1) {
/*     */ 
/*     */       
/* 301 */       int[] yTmp = yCurrent;
/* 302 */       yCurrent = yPrevious;
/* 303 */       yPrevious = yTmp;
/*     */       
/*     */       int i;
/* 306 */       for (i = 0; i < halfN; i++) {
/*     */         
/* 308 */         int twoI = 2 * i;
/* 309 */         yCurrent[i] = yPrevious[twoI] + yPrevious[twoI + 1];
/*     */       } 
/* 311 */       for (i = halfN; i < n; i++) {
/*     */         
/* 313 */         int twoI = 2 * i;
/* 314 */         yCurrent[i] = yPrevious[twoI - n] - yPrevious[twoI - n + 1];
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 319 */     return yCurrent;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/transform/FastHadamardTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */