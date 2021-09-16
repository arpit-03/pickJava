/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZeroMask
/*     */ {
/*     */   private int _n1;
/*     */   private int _n2;
/*     */   private int _n3;
/*     */   private boolean[][] _mask2;
/*     */   private boolean[][][] _mask3;
/*     */   
/*     */   public ZeroMask(double small, double sigma1, double sigma2, float[][] x) {
/*  55 */     this._n1 = (x[0]).length;
/*  56 */     this._n2 = x.length;
/*  57 */     float[][] t = ArrayMath.abs(x);
/*  58 */     float a = ArrayMath.sum(t) / this._n1 / this._n2;
/*  59 */     RecursiveGaussianFilter rgf1 = new RecursiveGaussianFilter(sigma1);
/*  60 */     RecursiveGaussianFilter rgf2 = new RecursiveGaussianFilter(sigma2);
/*  61 */     float[][] b = ArrayMath.zerofloat(this._n1, this._n2);
/*  62 */     rgf1.apply0X(t, b);
/*  63 */     rgf2.applyX0(b, t);
/*  64 */     this._mask2 = new boolean[this._n2][this._n1];
/*  65 */     for (int i2 = 0; i2 < this._n2; i2++) {
/*  66 */       for (int i1 = 0; i1 < this._n1; i1++) {
/*  67 */         this._mask2[i2][i1] = (t[i2][i1] >= small * a);
/*     */       }
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
/*     */   public ZeroMask(double small, double sigma1, double sigma2, double sigma3, float[][][] x) {
/*  84 */     this._n1 = (x[0][0]).length;
/*  85 */     this._n2 = (x[0]).length;
/*  86 */     this._n3 = x.length;
/*  87 */     float[][][] t = ArrayMath.abs(x);
/*  88 */     float a = ArrayMath.sum(t) / this._n1 / this._n2 / this._n3;
/*  89 */     RecursiveGaussianFilter rgf1 = new RecursiveGaussianFilter(sigma1);
/*  90 */     RecursiveGaussianFilter rgf2 = new RecursiveGaussianFilter(sigma2);
/*  91 */     RecursiveGaussianFilter rgf3 = new RecursiveGaussianFilter(sigma3);
/*  92 */     float[][][] b = ArrayMath.zerofloat(this._n1, this._n2, this._n3);
/*  93 */     rgf1.apply0XX(t, b);
/*  94 */     rgf2.applyX0X(b, t);
/*  95 */     rgf3.applyXX0(t, b);
/*  96 */     this._mask3 = new boolean[this._n3][this._n2][this._n1];
/*  97 */     for (int i3 = 0; i3 < this._n3; i3++) {
/*  98 */       for (int i2 = 0; i2 < this._n2; i2++) {
/*  99 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 100 */           this._mask3[i3][i2][i1] = (b[i3][i2][i1] >= small * a);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZeroMask(float[][] x) {
/* 112 */     this._n1 = (x[0]).length;
/* 113 */     this._n2 = x.length;
/* 114 */     this._mask2 = new boolean[this._n2][this._n1];
/* 115 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 116 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 117 */         if (x[i2][i1] != 0.0F) {
/* 118 */           this._mask2[i2][i1] = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZeroMask(float[][][] x) {
/* 129 */     this._n1 = (x[0][0]).length;
/* 130 */     this._n2 = (x[0]).length;
/* 131 */     this._n3 = x.length;
/* 132 */     this._mask3 = new boolean[this._n3][this._n2][this._n1];
/* 133 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 134 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 135 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 136 */           if (x[i3][i2][i1] != 0.0F) {
/* 137 */             this._mask3[i3][i2][i1] = true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] getAsFloats2() {
/* 149 */     Check.state((this._mask2 != null), "mask constructed for a 2D image");
/* 150 */     float[][] mask = new float[this._n2][this._n1];
/* 151 */     getAsFloats(mask);
/* 152 */     return mask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getAsFloats(float[][] mask) {
/* 161 */     Check.state((this._mask2 != null), "mask constructed for a 2D image");
/* 162 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 163 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 164 */         mask[i2][i1] = this._mask2[i2][i1] ? 1.0F : 0.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] getAsFloats3() {
/* 173 */     Check.state((this._mask3 != null), "mask constructed for a 3D image");
/* 174 */     float[][][] mask = new float[this._n3][this._n2][this._n1];
/* 175 */     getAsFloats(mask);
/* 176 */     return mask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getAsFloats(float[][][] mask) {
/* 185 */     Check.state((this._mask3 != null), "mask constructed for a 3D image");
/* 186 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 187 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 188 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 189 */           mask[i3][i2][i1] = this._mask3[i3][i2][i1] ? 1.0F : 0.0F;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(float vfalse, float[][] v) {
/* 198 */     Check.state((this._mask2 != null), "mask constructed for a 2D image");
/* 199 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 200 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 201 */         if (!this._mask2[i2][i1]) {
/* 202 */           v[i2][i1] = vfalse;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(float vfalse, float[][][] v) {
/* 213 */     Check.state((this._mask3 != null), "mask constructed for a 3D image");
/* 214 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 215 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 216 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 217 */           if (!this._mask3[i3][i2][i1]) {
/* 218 */             v[i3][i2][i1] = vfalse;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(float[] efalse, EigenTensors2 e) {
/* 231 */     Check.state((this._mask2 != null), "mask constructed for a 2D image");
/* 232 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 233 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 234 */         if (!this._mask2[i2][i1]) {
/* 235 */           e.setTensor(i1, i2, efalse);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(float[] efalse, EigenTensors3 e) {
/* 247 */     Check.state((this._mask3 != null), "mask constructed for a 3D image");
/* 248 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 249 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 250 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 251 */           if (!this._mask3[i3][i2][i1])
/* 252 */             e.setTensor(i1, i2, i3, efalse); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/ZeroMask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */