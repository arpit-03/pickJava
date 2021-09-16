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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FftComplex
/*     */ {
/*     */   private int _nfft;
/*     */   
/*     */   public FftComplex(int nfft) {
/*  54 */     Check.argument(Pfacc.nfftValid(nfft), "nfft=" + nfft + " is valid FFT length");
/*  55 */     this._nfft = nfft;
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
/*     */   public static int nfftSmall(int n) {
/*  68 */     Check.argument((n <= 720720), "n does not exceed 720720");
/*  69 */     return Pfacc.nfftSmall(n);
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
/*     */   public static int nfftFast(int n) {
/*  82 */     Check.argument((n <= 720720), "n does not exceed 720720");
/*  83 */     return Pfacc.nfftFast(n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNfft() {
/*  91 */     return this._nfft;
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
/*     */   public void complexToComplex(int sign, float[] cx, float[] cy) {
/* 103 */     checkSign(sign);
/* 104 */     checkArray(2 * this._nfft, cx, "cx");
/* 105 */     checkArray(2 * this._nfft, cy, "cy");
/* 106 */     if (cx != cy)
/* 107 */       ArrayMath.ccopy(this._nfft, cx, cy); 
/* 108 */     Pfacc.transform(sign, this._nfft, cy);
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
/*     */   public void complexToComplex1(int sign, int n2, float[][] cx, float[][] cy) {
/* 121 */     checkSign(sign);
/* 122 */     checkArray(2 * this._nfft, n2, cx, "cx");
/* 123 */     checkArray(2 * this._nfft, n2, cy, "cy");
/* 124 */     for (int i2 = 0; i2 < n2; i2++) {
/* 125 */       complexToComplex(sign, cx[i2], cy[i2]);
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
/*     */   public void complexToComplex2(int sign, int n1, float[][] cx, float[][] cy) {
/* 138 */     checkSign(sign);
/* 139 */     checkArray(2 * n1, this._nfft, cx, "cx");
/* 140 */     checkArray(2 * n1, this._nfft, cy, "cy");
/* 141 */     if (cx != cy)
/* 142 */       ArrayMath.ccopy(n1, this._nfft, cx, cy); 
/* 143 */     Pfacc.transform2a(sign, n1, this._nfft, cy);
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
/*     */   public void complexToComplex1(int sign, int n2, int n3, float[][][] cx, float[][][] cy) {
/* 160 */     checkSign(sign);
/* 161 */     checkArray(2 * this._nfft, n2, n3, cx, "cx");
/* 162 */     checkArray(2 * this._nfft, n2, n3, cy, "cy");
/* 163 */     for (int i3 = 0; i3 < n3; i3++) {
/* 164 */       complexToComplex1(sign, n2, cx[i3], cy[i3]);
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
/*     */   public void complexToComplex2(int sign, int n1, int n3, float[][][] cx, float[][][] cy) {
/* 181 */     checkSign(sign);
/* 182 */     checkArray(2 * n1, this._nfft, n3, cx, "cx");
/* 183 */     checkArray(2 * n1, this._nfft, n3, cy, "cy");
/* 184 */     for (int i3 = 0; i3 < n3; i3++) {
/* 185 */       complexToComplex2(sign, n1, cx[i3], cy[i3]);
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
/*     */   public void complexToComplex3(int sign, int n1, int n2, float[][][] cx, float[][][] cy) {
/* 202 */     checkSign(sign);
/* 203 */     checkArray(2 * n1, n2, this._nfft, cx, "cx");
/* 204 */     checkArray(2 * n1, n2, this._nfft, cy, "cy");
/* 205 */     float[][] cxi2 = new float[this._nfft][];
/* 206 */     float[][] cyi2 = new float[this._nfft][];
/* 207 */     for (int i2 = 0; i2 < n2; i2++) {
/* 208 */       for (int i3 = 0; i3 < this._nfft; i3++) {
/* 209 */         cxi2[i3] = cx[i3][i2];
/* 210 */         cyi2[i3] = cy[i3][i2];
/*     */       } 
/* 212 */       complexToComplex2(sign, n1, cxi2, cyi2);
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
/*     */   public void scale(int n1, float[] cx) {
/* 224 */     float s = 1.0F / this._nfft;
/* 225 */     int n = 2 * n1;
/* 226 */     while (--n >= 0) {
/* 227 */       cx[n] = cx[n] * s;
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
/*     */   public void scale(int n1, int n2, float[][] cx) {
/* 239 */     for (int i2 = 0; i2 < n2; i2++) {
/* 240 */       scale(n1, cx[i2]);
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
/*     */   public void scale(int n1, int n2, int n3, float[][][] cx) {
/* 253 */     for (int i3 = 0; i3 < n3; i3++) {
/* 254 */       scale(n1, n2, cx[i3]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkSign(int sign) {
/* 263 */     Check.argument((sign == 1 || sign == -1), "sign equals 1 or -1");
/*     */   }
/*     */   
/*     */   private static void checkArray(int n, float[] a, String name) {
/* 267 */     Check.argument((a.length >= n), "dimensions of " + name + " are valid");
/*     */   }
/*     */   
/*     */   private static void checkArray(int n1, int n2, float[][] a, String name) {
/* 271 */     boolean ok = (a.length >= n2);
/* 272 */     for (int i2 = 0; i2 < n2 && ok; i2++)
/* 273 */       ok = ((a[i2]).length >= n1); 
/* 274 */     Check.argument(ok, "dimensions of " + name + " are valid");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkArray(int n1, int n2, int n3, float[][][] a, String name) {
/* 280 */     boolean ok = (a.length >= n3);
/* 281 */     for (int i3 = 0; i3 < n3 && ok; i3++) {
/* 282 */       ok = ((a[i3]).length >= n2);
/* 283 */       for (int i2 = 0; i2 < n2 && ok; i2++) {
/* 284 */         ok = ((a[i3][i2]).length >= n1);
/*     */       }
/*     */     } 
/* 287 */     Check.argument(ok, "dimensions of " + name + " are valid");
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/FftComplex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */