/*     */ package edu.mines.jtk.dsp;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FftReal
/*     */ {
/*     */   private int _nfft;
/*     */   
/*     */   public FftReal(int nfft) {
/*  69 */     Check.argument((nfft % 2 == 0 && 
/*  70 */         Pfacc.nfftValid(nfft / 2)), "nfft=" + nfft + " is valid FFT length");
/*     */     
/*  72 */     this._nfft = nfft;
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
/*  85 */     Check.argument((n <= 1441440), "n does not exceed 1441440");
/*  86 */     return 2 * Pfacc.nfftSmall((n + 1) / 2);
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
/*  99 */     Check.argument((n <= 1441440), "n does not exceed 1441440");
/* 100 */     return 2 * Pfacc.nfftFast((n + 1) / 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNfft() {
/* 108 */     return this._nfft;
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
/*     */   public void realToComplex(int sign, float[] rx, float[] cy) {
/* 120 */     checkSign(sign);
/* 121 */     checkArray(this._nfft, rx, "rx");
/* 122 */     checkArray(this._nfft + 2, cy, "cy");
/* 123 */     int n = this._nfft;
/* 124 */     while (--n >= 0)
/* 125 */       cy[n] = 0.5F * rx[n]; 
/* 126 */     Pfacc.transform(sign, this._nfft / 2, cy);
/* 127 */     cy[this._nfft] = 2.0F * (cy[0] - cy[1]);
/* 128 */     cy[0] = 2.0F * (cy[0] + cy[1]);
/* 129 */     cy[this._nfft + 1] = 0.0F;
/* 130 */     cy[1] = 0.0F;
/* 131 */     double theta = sign * 2.0D * Math.PI / this._nfft;
/* 132 */     double wt = Math.sin(0.5D * theta);
/* 133 */     double wpr = -2.0D * wt * wt;
/* 134 */     double wpi = Math.sin(theta);
/* 135 */     double wr = 1.0D + wpr;
/* 136 */     double wi = wpi;
/* 137 */     for (int j = 2, k = this._nfft - 2; j <= k; j += 2, k -= 2) {
/* 138 */       float sumr = cy[j] + cy[k];
/* 139 */       float sumi = cy[j + 1] + cy[k + 1];
/* 140 */       float difr = cy[j] - cy[k];
/* 141 */       float difi = cy[j + 1] - cy[k + 1];
/* 142 */       float tmpr = (float)(wi * difr + wr * sumi);
/* 143 */       float tmpi = (float)(wi * sumi - wr * difr);
/* 144 */       cy[j] = sumr + tmpr;
/* 145 */       cy[j + 1] = tmpi + difi;
/* 146 */       cy[k] = sumr - tmpr;
/* 147 */       cy[k + 1] = tmpi - difi;
/* 148 */       wt = wr;
/* 149 */       wr += wr * wpr - wi * wpi;
/* 150 */       wi += wi * wpr + wt * wpi;
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
/*     */   public void complexToReal(int sign, float[] cx, float[] ry) {
/* 163 */     checkSign(sign);
/* 164 */     checkArray(this._nfft + 2, cx, "cx");
/* 165 */     checkArray(this._nfft, ry, "ry");
/* 166 */     if (cx != ry) {
/* 167 */       int n = this._nfft;
/* 168 */       while (--n >= 2)
/* 169 */         ry[n] = cx[n]; 
/*     */     } 
/* 171 */     ry[1] = cx[0] - cx[this._nfft];
/* 172 */     ry[0] = cx[0] + cx[this._nfft];
/* 173 */     double theta = -sign * 2.0D * Math.PI / this._nfft;
/* 174 */     double wt = Math.sin(0.5D * theta);
/* 175 */     double wpr = -2.0D * wt * wt;
/* 176 */     double wpi = Math.sin(theta);
/* 177 */     double wr = 1.0D + wpr;
/* 178 */     double wi = wpi;
/* 179 */     for (int j = 2, k = this._nfft - 2; j <= k; j += 2, k -= 2) {
/* 180 */       float sumr = ry[j] + ry[k];
/* 181 */       float sumi = ry[j + 1] + ry[k + 1];
/* 182 */       float difr = ry[j] - ry[k];
/* 183 */       float difi = ry[j + 1] - ry[k + 1];
/* 184 */       float tmpr = (float)(wi * difr - wr * sumi);
/* 185 */       float tmpi = (float)(wi * sumi + wr * difr);
/* 186 */       ry[j] = sumr + tmpr;
/* 187 */       ry[j + 1] = tmpi + difi;
/* 188 */       ry[k] = sumr - tmpr;
/* 189 */       ry[k + 1] = tmpi - difi;
/* 190 */       wt = wr;
/* 191 */       wr += wr * wpr - wi * wpi;
/* 192 */       wi += wi * wpr + wt * wpi;
/*     */     } 
/* 194 */     Pfacc.transform(sign, this._nfft / 2, ry);
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
/*     */   public void realToComplex1(int sign, int n2, float[][] rx, float[][] cy) {
/* 207 */     checkSign(sign);
/* 208 */     checkArray(this._nfft, n2, rx, "rx");
/* 209 */     checkArray(this._nfft + 2, n2, cy, "cy");
/* 210 */     for (int i2 = 0; i2 < n2; i2++) {
/* 211 */       realToComplex(sign, rx[i2], cy[i2]);
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
/*     */   public void complexToReal1(int sign, int n2, float[][] cx, float[][] ry) {
/* 224 */     checkSign(sign);
/* 225 */     checkArray(this._nfft + 2, n2, cx, "cx");
/* 226 */     checkArray(this._nfft, n2, ry, "ry");
/* 227 */     for (int i2 = 0; i2 < n2; i2++) {
/* 228 */       complexToReal(sign, cx[i2], ry[i2]);
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
/*     */   public void realToComplex2(int sign, int n1, float[][] rx, float[][] cy) {
/* 241 */     checkSign(sign);
/* 242 */     checkArray(n1, this._nfft, rx, "rx");
/* 243 */     checkArray(2 * n1, this._nfft / 2 + 1, cy, "cy");
/*     */ 
/*     */ 
/*     */     
/* 247 */     for (int i1 = n1 - 1, j1 = i1 * 2; i1 >= 0; i1--, j1 -= 2) {
/* 248 */       for (int i2 = this._nfft - 2, j = i2 / 2; i2 >= 0; i2 -= 2, j--) {
/* 249 */         cy[j][j1] = 0.5F * rx[i2][i1];
/* 250 */         cy[j][j1 + 1] = 0.5F * rx[i2 + 1][i1];
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 255 */     Pfacc.transform2a(sign, n1, this._nfft / 2, cy);
/*     */ 
/*     */     
/* 258 */     float[] cy0 = cy[0];
/* 259 */     float[] cyn = cy[this._nfft / 2];
/* 260 */     for (int i = 2 * n1 - 2; i >= 0; i -= 2) {
/* 261 */       cyn[i] = 2.0F * (cy0[i] - cy0[i + 1]);
/* 262 */       cy0[i] = 2.0F * (cy0[i] + cy0[i + 1]);
/* 263 */       cyn[i + 1] = 0.0F;
/* 264 */       cy0[i + 1] = 0.0F;
/*     */     } 
/* 266 */     double theta = sign * 2.0D * Math.PI / this._nfft;
/* 267 */     double wt = Math.sin(0.5D * theta);
/* 268 */     double wpr = -2.0D * wt * wt;
/* 269 */     double wpi = Math.sin(theta);
/* 270 */     double wr = 1.0D + wpr;
/* 271 */     double wi = wpi;
/* 272 */     for (int j2 = 1, k2 = this._nfft / 2 - 1; j2 <= k2; j2++, k2--) {
/* 273 */       float[] cyj2 = cy[j2];
/* 274 */       float[] cyk2 = cy[k2];
/* 275 */       for (int j = 0, k = 0; j < n1; j++, k += 2) {
/* 276 */         float sumr = cyj2[k] + cyk2[k];
/* 277 */         float sumi = cyj2[k + 1] + cyk2[k + 1];
/* 278 */         float difr = cyj2[k] - cyk2[k];
/* 279 */         float difi = cyj2[k + 1] - cyk2[k + 1];
/* 280 */         float tmpr = (float)(wi * difr + wr * sumi);
/* 281 */         float tmpi = (float)(wi * sumi - wr * difr);
/* 282 */         cyj2[k] = sumr + tmpr;
/* 283 */         cyj2[k + 1] = tmpi + difi;
/* 284 */         cyk2[k] = sumr - tmpr;
/* 285 */         cyk2[k + 1] = tmpi - difi;
/*     */       } 
/* 287 */       wt = wr;
/* 288 */       wr += wr * wpr - wi * wpi;
/* 289 */       wi += wi * wpr + wt * wpi;
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
/*     */   public void complexToReal2(int sign, int n1, float[][] cx, float[][] ry) {
/* 303 */     checkSign(sign);
/* 304 */     checkArray(2 * n1, this._nfft / 2 + 1, cx, "cx");
/* 305 */     checkArray(n1, this._nfft, ry, "ry");
/*     */ 
/*     */ 
/*     */     
/* 309 */     for (int i1 = 0, j1 = 0; j1 < n1; i1 += 2, j1++) {
/* 310 */       float cx0 = cx[0][i1];
/* 311 */       float cxn = cx[this._nfft / 2][i1];
/* 312 */       for (int i2 = this._nfft / 2 - 1, i = 2 * i2; i2 > 0; i2--, i -= 2) {
/* 313 */         ry[i][j1] = cx[i2][i1];
/* 314 */         ry[i + 1][j1] = cx[i2][i1 + 1];
/*     */       } 
/* 316 */       ry[1][j1] = cx0 - cxn;
/* 317 */       ry[0][j1] = cx0 + cxn;
/*     */     } 
/*     */ 
/*     */     
/* 321 */     double theta = -sign * 2.0D * Math.PI / this._nfft;
/* 322 */     double wt = Math.sin(0.5D * theta);
/* 323 */     double wpr = -2.0D * wt * wt;
/* 324 */     double wpi = Math.sin(theta);
/* 325 */     double wr = 1.0D + wpr;
/* 326 */     double wi = wpi;
/* 327 */     for (int j2 = 2, k2 = this._nfft - 2; j2 <= k2; j2 += 2, k2 -= 2) {
/* 328 */       float[] ryj2r = ry[j2];
/* 329 */       float[] ryj2i = ry[j2 + 1];
/* 330 */       float[] ryk2r = ry[k2];
/* 331 */       float[] ryk2i = ry[k2 + 1];
/* 332 */       for (int i = 0; i < n1; i++) {
/* 333 */         float sumr = ryj2r[i] + ryk2r[i];
/* 334 */         float sumi = ryj2i[i] + ryk2i[i];
/* 335 */         float difr = ryj2r[i] - ryk2r[i];
/* 336 */         float difi = ryj2i[i] - ryk2i[i];
/* 337 */         float tmpr = (float)(wi * difr - wr * sumi);
/* 338 */         float tmpi = (float)(wi * sumi + wr * difr);
/* 339 */         ryj2r[i] = sumr + tmpr;
/* 340 */         ryj2i[i] = tmpi + difi;
/* 341 */         ryk2r[i] = sumr - tmpr;
/* 342 */         ryk2i[i] = tmpi - difi;
/*     */       } 
/* 344 */       wt = wr;
/* 345 */       wr += wr * wpr - wi * wpi;
/* 346 */       wi += wi * wpr + wt * wpi;
/*     */     } 
/*     */ 
/*     */     
/* 350 */     Pfacc.transform2b(sign, n1, this._nfft / 2, ry);
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
/*     */   public void realToComplex1(int sign, int n2, int n3, float[][][] rx, float[][][] cy) {
/* 367 */     checkSign(sign);
/* 368 */     checkArray(this._nfft, n2, n3, rx, "rx");
/* 369 */     checkArray(this._nfft + 2, n2, n3, cy, "cy");
/* 370 */     for (int i3 = 0; i3 < n3; i3++) {
/* 371 */       realToComplex1(sign, n2, rx[i3], cy[i3]);
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
/*     */   public void complexToReal1(int sign, int n2, int n3, float[][][] cx, float[][][] ry) {
/* 388 */     checkSign(sign);
/* 389 */     checkArray(this._nfft + 2, n2, n3, cx, "cx");
/* 390 */     checkArray(this._nfft, n2, n3, ry, "ry");
/* 391 */     for (int i3 = 0; i3 < n3; i3++) {
/* 392 */       complexToReal1(sign, n2, cx[i3], ry[i3]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scale(int n1, float[] rx) {
/* 403 */     float s = 1.0F / this._nfft;
/* 404 */     while (--n1 >= 0) {
/* 405 */       rx[n1] = rx[n1] * s;
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
/*     */   public void scale(int n1, int n2, float[][] rx) {
/* 417 */     for (int i2 = 0; i2 < n2; i2++) {
/* 418 */       scale(n1, rx[i2]);
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
/*     */   public void scale(int n1, int n2, int n3, float[][][] rx) {
/* 431 */     for (int i3 = 0; i3 < n3; i3++) {
/* 432 */       scale(n1, n2, rx[i3]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkSign(int sign) {
/* 441 */     Check.argument((sign == 1 || sign == -1), "sign equals 1 or -1");
/*     */   }
/*     */   
/*     */   private static void checkArray(int n, float[] a, String name) {
/* 445 */     Check.argument((a.length >= n), "dimensions of " + name + " are valid");
/*     */   }
/*     */   
/*     */   private static void checkArray(int n1, int n2, float[][] a, String name) {
/* 449 */     boolean ok = (a.length >= n2);
/* 450 */     for (int i2 = 0; i2 < n2 && ok; i2++)
/* 451 */       ok = ((a[i2]).length >= n1); 
/* 452 */     Check.argument(ok, "dimensions of " + name + " are valid");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkArray(int n1, int n2, int n3, float[][][] a, String name) {
/* 458 */     boolean ok = (a.length >= n3);
/* 459 */     for (int i3 = 0; i3 < n3 && ok; i3++) {
/* 460 */       ok = ((a[i3]).length >= n2);
/* 461 */       for (int i2 = 0; i2 < n2 && ok; i2++) {
/* 462 */         ok = ((a[i3][i2]).length >= n1);
/*     */       }
/*     */     } 
/* 465 */     Check.argument(ok, "dimensions of " + name + " are valid");
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/FftReal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */