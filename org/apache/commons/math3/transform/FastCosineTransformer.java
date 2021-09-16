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
/*     */ public class FastCosineTransformer
/*     */   implements RealTransformer, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 20120212L;
/*     */   private final DctNormalization normalization;
/*     */   
/*     */   public FastCosineTransformer(DctNormalization normalization) {
/*  81 */     this.normalization = normalization;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] transform(double[] f, TransformType type) throws MathIllegalArgumentException {
/*     */     double s1;
/*  92 */     if (type == TransformType.FORWARD) {
/*  93 */       if (this.normalization == DctNormalization.ORTHOGONAL_DCT_I) {
/*  94 */         double s = FastMath.sqrt(2.0D / (f.length - 1));
/*  95 */         return TransformUtils.scaleArray(fct(f), s);
/*     */       } 
/*  97 */       return fct(f);
/*     */     } 
/*  99 */     double s2 = 2.0D / (f.length - 1);
/*     */     
/* 101 */     if (this.normalization == DctNormalization.ORTHOGONAL_DCT_I) {
/* 102 */       s1 = FastMath.sqrt(s2);
/*     */     } else {
/* 104 */       s1 = s2;
/*     */     } 
/* 106 */     return TransformUtils.scaleArray(fct(f), s1);
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
/*     */   public double[] transform(UnivariateFunction f, double min, double max, int n, TransformType type) throws MathIllegalArgumentException {
/* 123 */     double[] data = FunctionUtils.sample(f, min, max, n);
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
/*     */   protected double[] fct(double[] f) throws MathIllegalArgumentException {
/* 138 */     double[] transformed = new double[f.length];
/*     */     
/* 140 */     int n = f.length - 1;
/* 141 */     if (!ArithmeticUtils.isPowerOfTwo(n)) {
/* 142 */       throw new MathIllegalArgumentException(LocalizedFormats.NOT_POWER_OF_TWO_PLUS_ONE, new Object[] { Integer.valueOf(f.length) });
/*     */     }
/*     */ 
/*     */     
/* 146 */     if (n == 1) {
/* 147 */       transformed[0] = 0.5D * (f[0] + f[1]);
/* 148 */       transformed[1] = 0.5D * (f[0] - f[1]);
/* 149 */       return transformed;
/*     */     } 
/*     */ 
/*     */     
/* 153 */     double[] x = new double[n];
/* 154 */     x[0] = 0.5D * (f[0] + f[n]);
/* 155 */     x[n >> 1] = f[n >> 1];
/*     */     
/* 157 */     double t1 = 0.5D * (f[0] - f[n]);
/* 158 */     for (int i = 1; i < n >> 1; i++) {
/* 159 */       double a = 0.5D * (f[i] + f[n - i]);
/* 160 */       double b = FastMath.sin(i * Math.PI / n) * (f[i] - f[n - i]);
/* 161 */       double c = FastMath.cos(i * Math.PI / n) * (f[i] - f[n - i]);
/* 162 */       x[i] = a - b;
/* 163 */       x[n - i] = a + b;
/* 164 */       t1 += c;
/*     */     } 
/*     */     
/* 167 */     FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
/* 168 */     Complex[] y = transformer.transform(x, TransformType.FORWARD);
/*     */ 
/*     */     
/* 171 */     transformed[0] = y[0].getReal();
/* 172 */     transformed[1] = t1;
/* 173 */     for (int j = 1; j < n >> 1; j++) {
/* 174 */       transformed[2 * j] = y[j].getReal();
/* 175 */       transformed[2 * j + 1] = transformed[2 * j - 1] - y[j].getImaginary();
/*     */     } 
/* 177 */     transformed[n] = y[n >> 1].getReal();
/*     */     
/* 179 */     return transformed;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/transform/FastCosineTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */