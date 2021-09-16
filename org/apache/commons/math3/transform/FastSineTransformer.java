/*     */ package org.apache.commons.math3.transform;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.analysis.FunctionUtils;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.complex.Complex;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.ArithmeticUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FastSineTransformer
/*     */   implements RealTransformer, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 20120211L;
/*     */   private final DstNormalization normalization;
/*     */   
/*     */   public FastSineTransformer(DstNormalization normalization) {
/*  84 */     this.normalization = normalization;
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
/*     */   public double[] transform(double[] f, TransformType type) {
/*  96 */     if (this.normalization == DstNormalization.ORTHOGONAL_DST_I) {
/*  97 */       double d = FastMath.sqrt(2.0D / f.length);
/*  98 */       return TransformUtils.scaleArray(fst(f), d);
/*     */     } 
/* 100 */     if (type == TransformType.FORWARD) {
/* 101 */       return fst(f);
/*     */     }
/* 103 */     double s = 2.0D / f.length;
/* 104 */     return TransformUtils.scaleArray(fst(f), s);
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
/*     */   public double[] transform(UnivariateFunction f, double min, double max, int n, TransformType type) {
/* 122 */     double[] data = FunctionUtils.sample(f, min, max, n);
/* 123 */     data[0] = 0.0D;
/* 124 */     return transform(data, type);
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
/*     */   protected double[] fst(double[] f) throws MathIllegalArgumentException {
/* 138 */     double[] transformed = new double[f.length];
/*     */     
/* 140 */     if (!ArithmeticUtils.isPowerOfTwo(f.length)) {
/* 141 */       throw new MathIllegalArgumentException(LocalizedFormats.NOT_POWER_OF_TWO_CONSIDER_PADDING, new Object[] { Integer.valueOf(f.length) });
/*     */     }
/*     */ 
/*     */     
/* 145 */     if (f[0] != 0.0D) {
/* 146 */       throw new MathIllegalArgumentException(LocalizedFormats.FIRST_ELEMENT_NOT_ZERO, new Object[] { Double.valueOf(f[0]) });
/*     */     }
/*     */ 
/*     */     
/* 150 */     int n = f.length;
/* 151 */     if (n == 1) {
/* 152 */       transformed[0] = 0.0D;
/* 153 */       return transformed;
/*     */     } 
/*     */ 
/*     */     
/* 157 */     double[] x = new double[n];
/* 158 */     x[0] = 0.0D;
/* 159 */     x[n >> 1] = 2.0D * f[n >> 1];
/* 160 */     for (int i = 1; i < n >> 1; i++) {
/* 161 */       double a = FastMath.sin(i * Math.PI / n) * (f[i] + f[n - i]);
/* 162 */       double b = 0.5D * (f[i] - f[n - i]);
/* 163 */       x[i] = a + b;
/* 164 */       x[n - i] = a - b;
/*     */     } 
/*     */     
/* 167 */     FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
/* 168 */     Complex[] y = transformer.transform(x, TransformType.FORWARD);
/*     */ 
/*     */     
/* 171 */     transformed[0] = 0.0D;
/* 172 */     transformed[1] = 0.5D * y[0].getReal();
/* 173 */     for (int j = 1; j < n >> 1; j++) {
/* 174 */       transformed[2 * j] = -y[j].getImaginary();
/* 175 */       transformed[2 * j + 1] = y[j].getReal() + transformed[2 * j - 1];
/*     */     } 
/*     */     
/* 178 */     return transformed;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/transform/FastSineTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */