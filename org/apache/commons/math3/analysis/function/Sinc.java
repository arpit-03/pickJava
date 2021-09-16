/*     */ package org.apache.commons.math3.analysis.function;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.FunctionUtils;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
/*     */ import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
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
/*     */ public class Sinc
/*     */   implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction
/*     */ {
/*     */   private static final double SHORTCUT = 0.006D;
/*     */   private final boolean normalized;
/*     */   
/*     */   public Sinc() {
/*  71 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sinc(boolean normalized) {
/*  81 */     this.normalized = normalized;
/*     */   }
/*     */ 
/*     */   
/*     */   public double value(double x) {
/*  86 */     double scaledX = this.normalized ? (Math.PI * x) : x;
/*  87 */     if (FastMath.abs(scaledX) <= 0.006D) {
/*     */       
/*  89 */       double scaledX2 = scaledX * scaledX;
/*  90 */       return ((scaledX2 - 20.0D) * scaledX2 + 120.0D) / 120.0D;
/*     */     } 
/*     */     
/*  93 */     return FastMath.sin(scaledX) / scaledX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public UnivariateFunction derivative() {
/* 102 */     return FunctionUtils.toDifferentiableUnivariateFunction(this).derivative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
/* 111 */     double scaledX = (this.normalized ? Math.PI : 1.0D) * t.getValue();
/* 112 */     double scaledX2 = scaledX * scaledX;
/*     */     
/* 114 */     double[] f = new double[t.getOrder() + 1];
/*     */     
/* 116 */     if (FastMath.abs(scaledX) <= 0.006D) {
/*     */       
/* 118 */       for (int i = 0; i < f.length; i++) {
/* 119 */         int k = i / 2;
/* 120 */         if ((i & 0x1) == 0)
/*     */         {
/* 122 */           f[i] = (((k & 0x1) == 0) ? true : -1) * (1.0D / (i + 1) - scaledX2 * (1.0D / (2 * i + 6) - scaledX2 / (24 * i + 120)));
/*     */         }
/*     */         else
/*     */         {
/* 126 */           f[i] = (((k & 0x1) == 0) ? -scaledX : scaledX) * (1.0D / (i + 2) - scaledX2 * (1.0D / (6 * i + 24) - scaledX2 / (120 * i + 720)));
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 133 */       double inv = 1.0D / scaledX;
/* 134 */       double cos = FastMath.cos(scaledX);
/* 135 */       double sin = FastMath.sin(scaledX);
/*     */       
/* 137 */       f[0] = inv * sin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 149 */       double[] sc = new double[f.length];
/* 150 */       sc[0] = 1.0D;
/*     */       
/* 152 */       double coeff = inv;
/* 153 */       for (int n = 1; n < f.length; n++) {
/*     */         int kStart;
/* 155 */         double s = 0.0D;
/* 156 */         double c = 0.0D;
/*     */ 
/*     */ 
/*     */         
/* 160 */         if ((n & 0x1) == 0) {
/*     */           
/* 162 */           sc[n] = 0.0D;
/* 163 */           kStart = n;
/*     */         } else {
/*     */           
/* 166 */           sc[n] = sc[n - 1];
/* 167 */           c = sc[n];
/* 168 */           kStart = n - 1;
/*     */         } 
/*     */ 
/*     */         
/* 172 */         for (int k = kStart; k > 1; k -= 2) {
/*     */ 
/*     */           
/* 175 */           sc[k] = (k - n) * sc[k] - sc[k - 1];
/* 176 */           s = s * scaledX2 + sc[k];
/*     */ 
/*     */           
/* 179 */           sc[k - 1] = (k - 1 - n) * sc[k - 1] + sc[k - 2];
/* 180 */           c = c * scaledX2 + sc[k - 1];
/*     */         } 
/*     */         
/* 183 */         sc[0] = sc[0] * -n;
/* 184 */         s = s * scaledX2 + sc[0];
/*     */         
/* 186 */         coeff *= inv;
/* 187 */         f[n] = coeff * (s * sin + c * scaledX * cos);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 193 */     if (this.normalized) {
/* 194 */       double scale = Math.PI;
/* 195 */       for (int i = 1; i < f.length; i++) {
/* 196 */         f[i] = f[i] * scale;
/* 197 */         scale *= Math.PI;
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     return t.compose(f);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/function/Sinc.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */