/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import edu.mines.jtk.util.Parallel;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SymmetricTridiagonalFilter
/*     */ {
/*     */   private double _af;
/*     */   private double _ai;
/*     */   private double _al;
/*     */   private double _b;
/*     */   
/*     */   public SymmetricTridiagonalFilter(double af, double ai, double al, double b) {
/*  74 */     this._af = af;
/*  75 */     this._ai = ai;
/*  76 */     this._al = al;
/*  77 */     this._b = b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(float[] x, float[] y) {
/*  86 */     int n = x.length;
/*  87 */     int nm1 = n - 1;
/*  88 */     float af = (float)this._af;
/*  89 */     float ai = (float)this._ai;
/*  90 */     float al = (float)this._al;
/*  91 */     float b = (float)this._b;
/*     */     
/*  93 */     float xi = x[0];
/*  94 */     float xip1 = x[1];
/*  95 */     y[0] = af * xi + b * xip1;
/*  96 */     for (int i = 1; i < nm1; i++) {
/*  97 */       float f = xi;
/*  98 */       xi = xip1;
/*  99 */       xip1 = x[i + 1];
/* 100 */       y[i] = ai * xi + b * (f + xip1);
/*     */     } 
/* 102 */     float xim1 = xi;
/* 103 */     xi = xip1;
/* 104 */     y[n - 1] = al * xi + b * xim1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply1(final float[][] x, final float[][] y) {
/* 113 */     int n = x.length;
/* 114 */     Parallel.loop(n, new Parallel.LoopInt() {
/*     */           public void compute(int i) {
/* 116 */             SymmetricTridiagonalFilter.this.apply(x[i], y[i]);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply2(float[][] x, float[][] y) {
/* 126 */     int n1 = (x[0]).length;
/* 127 */     int n2 = x.length;
/* 128 */     int n2m1 = n2 - 1;
/* 129 */     float af = (float)this._af;
/* 130 */     float ai = (float)this._ai;
/* 131 */     float al = (float)this._al;
/* 132 */     float b = (float)this._b;
/* 133 */     float[] xi2m1 = new float[n1];
/* 134 */     float[] xi2 = ArrayMath.copy(x[0]);
/* 135 */     float[] xi2p1 = ArrayMath.copy(x[1]);
/* 136 */     float[] yi2 = y[0];
/* 137 */     for (int i = 0; i < n1; i++)
/* 138 */       yi2[i] = af * xi2[i] + b * xi2p1[i]; 
/* 139 */     for (int i2 = 1; i2 < n2m1; i2++) {
/* 140 */       float[] xtemp = xi2m1;
/* 141 */       xi2m1 = xi2;
/* 142 */       xi2 = xi2p1;
/* 143 */       xi2p1 = xtemp;
/* 144 */       ArrayMath.copy(x[i2 + 1], xi2p1);
/* 145 */       yi2 = y[i2];
/* 146 */       for (int j = 0; j < n1; j++)
/* 147 */         yi2[j] = ai * xi2[j] + b * (xi2m1[j] + xi2p1[j]); 
/*     */     } 
/* 149 */     xi2m1 = xi2;
/* 150 */     xi2 = xi2p1;
/* 151 */     yi2 = y[n2 - 1];
/* 152 */     for (int i1 = 0; i1 < n1; i1++) {
/* 153 */       yi2[i1] = al * xi2[i1] + b * xi2m1[i1];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply1(final float[][][] x, final float[][][] y) {
/* 162 */     int n = x.length;
/* 163 */     Parallel.loop(n, new Parallel.LoopInt() {
/*     */           public void compute(int i) {
/* 165 */             SymmetricTridiagonalFilter.this.apply1(x[i], y[i]);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply2(final float[][][] x, final float[][][] y) {
/* 175 */     int n = x.length;
/* 176 */     Parallel.loop(n, new Parallel.LoopInt() {
/*     */           public void compute(int i) {
/* 178 */             SymmetricTridiagonalFilter.this.apply2(x[i], y[i]);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply3(float[][][] x, float[][][] y) {
/* 188 */     int n2 = (x[0]).length;
/* 189 */     int n3 = x.length;
/* 190 */     float[][][] xt = new float[n2][n3][];
/* 191 */     float[][][] yt = new float[n2][n3][];
/* 192 */     for (int i2 = 0; i2 < n2; i2++) {
/* 193 */       for (int i3 = 0; i3 < n3; i3++) {
/* 194 */         xt[i2][i3] = x[i3][i2];
/* 195 */         yt[i2][i3] = y[i3][i2];
/*     */       } 
/*     */     } 
/* 198 */     apply2(xt, yt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyInverse(float[] x, float[] y) {
/* 207 */     checkInvertible();
/* 208 */     int n = x.length;
/* 209 */     int nm1 = n - 1;
/*     */ 
/*     */     
/* 212 */     if (this._b == 0.0D) {
/* 213 */       y[0] = x[0] / (float)this._af;
/* 214 */       float oa = 1.0F / (float)this._ai;
/* 215 */       for (int j = 1; j < nm1; j++)
/* 216 */         y[j] = x[j] * oa; 
/* 217 */       y[n - 1] = x[n - 1] / (float)this._al;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 222 */     float u = (float)(this._ai / this._af);
/* 223 */     float v = (float)(this._ai / this._al);
/* 224 */     float a = (float)(this._ai / this._b);
/* 225 */     float aa = a * a;
/* 226 */     float b = -a * (1.0F + ArrayMath.sqrt(1.0F - 4.0F / aa)) / 2.0F;
/* 227 */     if (ArrayMath.abs(b) > 1.0F)
/* 228 */       b = 1.0F / b; 
/* 229 */     float bb = b * b;
/* 230 */     float ss = (1.0F + bb) / u - bb;
/* 231 */     float gg = (1.0F + bb) / v - 1.0F;
/*     */ 
/*     */     
/* 234 */     float scale = (1.0F + bb) / (float)this._ai; int i;
/* 235 */     for (i = 0; i < n; i++) {
/* 236 */       y[i] = scale * x[i];
/*     */     }
/*     */     
/* 239 */     if (bb < 1.0F) {
/*     */ 
/*     */       
/* 242 */       float ynm1 = 0.0F;
/* 243 */       float c = (1.0F - bb - ss) / ss;
/* 244 */       float d = 1.0F - bb + gg * (1.0F + c * ArrayMath.pow(bb, (n - 1)));
/* 245 */       float e = ArrayMath.pow(1.0F - ArrayMath.abs(b), 2.0F) * 1.1920929E-7F / 4.0F;
/* 246 */       int k = ArrayMath.min((int)ArrayMath.ceil(ArrayMath.log(e) / ArrayMath.log(ArrayMath.abs(b))), 2 * (n - 1));
/* 247 */       int m = k - n + 1; int j;
/* 248 */       for (j = m; j > 0; j--)
/* 249 */         ynm1 = b * ynm1 + y[j]; 
/* 250 */       ynm1 *= c;
/* 251 */       if (n - k < 1)
/* 252 */         ynm1 = b * ynm1 + (1.0F + c) * y[0]; 
/* 253 */       m = ArrayMath.max(n - k, 1);
/* 254 */       for (j = m; j < n; j++)
/* 255 */         ynm1 = b * ynm1 + y[j]; 
/* 256 */       ynm1 /= d;
/*     */ 
/*     */       
/* 259 */       y[n - 1] = y[n - 1] - gg * ynm1;
/* 260 */       for (j = n - 2; j >= 0; j--) {
/* 261 */         y[j] = y[j] + b * y[j + 1];
/*     */       }
/*     */       
/* 264 */       y[0] = y[0] / ss;
/*     */ 
/*     */       
/* 267 */       for (j = 1; j < nm1; j++)
/* 268 */         y[j] = y[j] + b * y[j - 1]; 
/* 269 */       y[n - 1] = ynm1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 277 */     else if (ss > 0.0F && gg > 0.0F) {
/*     */ 
/*     */       
/* 280 */       float oss = 1.0F / ss;
/* 281 */       float sum = 0.0F;
/* 282 */       for (int j = 0; j < nm1; j++)
/* 283 */         sum = (sum + (j + oss) * y[j]) * b; 
/* 284 */       sum += ((n - 1) + oss) * y[n - 1];
/* 285 */       float ynm1 = sum / (1.0F + gg * (n - 1) + gg / ss);
/*     */ 
/*     */       
/* 288 */       y[n - 1] = y[n - 1] - gg * ynm1; int k;
/* 289 */       for (k = n - 2; k >= 0; k--)
/* 290 */         y[k] = y[k] + b * y[k + 1]; 
/* 291 */       y[0] = y[0] / ss;
/*     */ 
/*     */       
/* 294 */       for (k = 1; k < nm1; k++)
/* 295 */         y[k] = y[k] + b * y[k - 1]; 
/* 296 */       y[n - 1] = ynm1;
/*     */ 
/*     */     
/*     */     }
/* 300 */     else if (ss == 0.0F) {
/*     */ 
/*     */       
/* 303 */       y[0] = y[0] * -b;
/* 304 */       for (i = 1; i < nm1; i++) {
/* 305 */         y[i] = b * (y[i - 1] - y[i]);
/*     */       }
/*     */       
/* 308 */       y[n - 1] = (y[n - 1] - y[n - 2]) / gg;
/*     */ 
/*     */       
/* 311 */       for (i = n - 2; i >= 0; i--) {
/* 312 */         y[i] = b * (y[i + 1] - y[i]);
/*     */       
/*     */       }
/*     */     }
/* 316 */     else if (gg == 0.0F) {
/*     */ 
/*     */       
/* 319 */       for (i = n - 2; i > 0; i--) {
/* 320 */         y[i] = y[i] + b * y[i + 1];
/*     */       }
/*     */       
/* 323 */       y[0] = (y[0] + b * y[1]) / ss;
/*     */ 
/*     */       
/* 326 */       for (i = 1; i < n; i++) {
/* 327 */         y[i] = y[i] + b * y[i - 1];
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 332 */       Check.state(false, "filter is invertible");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyInverse1(final float[][] x, final float[][] y) {
/* 343 */     int n = x.length;
/* 344 */     Parallel.loop(n, new Parallel.LoopInt() {
/*     */           public void compute(int i) {
/* 346 */             SymmetricTridiagonalFilter.this.applyInverse(x[i], y[i]);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyInverse2(float[][] x, float[][] y) {
/* 356 */     checkInvertible();
/* 357 */     int n1 = (x[0]).length;
/* 358 */     int n2 = x.length;
/* 359 */     int n2m1 = n2 - 1;
/*     */ 
/*     */     
/* 362 */     if (this._b == 0.0D) {
/* 363 */       float oa = 1.0F / (float)this._af;
/* 364 */       for (int j = 0; j < n1; j++)
/* 365 */         y[0][j] = x[0][j] * oa; 
/* 366 */       oa = 1.0F / (float)this._ai;
/* 367 */       for (int i = 1; i < n2m1; i++) {
/* 368 */         for (int k = 0; k < n1; k++)
/* 369 */           y[i][k] = x[i][k] * oa; 
/* 370 */       }  oa = 1.0F / (float)this._al;
/* 371 */       for (int i1 = 0; i1 < n1; i1++) {
/* 372 */         y[n2m1][i1] = x[n2m1][i1] * oa;
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 377 */     float u = (float)(this._ai / this._af);
/* 378 */     float v = (float)(this._ai / this._al);
/* 379 */     float a = (float)(this._ai / this._b);
/* 380 */     float aa = a * a;
/* 381 */     float b = -a * (1.0F + ArrayMath.sqrt(1.0F - 4.0F / aa)) / 2.0F;
/* 382 */     if (ArrayMath.abs(b) > 1.0F)
/* 383 */       b = 1.0F / b; 
/* 384 */     float bb = b * b;
/* 385 */     float ss = (1.0F + bb) / u - bb;
/* 386 */     float gg = (1.0F + bb) / v - 1.0F;
/*     */ 
/*     */     
/* 389 */     float scale = (1.0F + bb) / (float)this._ai; int i2;
/* 390 */     for (i2 = 0; i2 < n2; i2++) {
/* 391 */       for (int i1 = 0; i1 < n1; i1++) {
/* 392 */         y[i2][i1] = scale * x[i2][i1];
/*     */       }
/*     */     } 
/* 395 */     if (bb < 1.0F) {
/*     */ 
/*     */       
/* 398 */       float[] yn2m1 = new float[n1];
/* 399 */       float c = (1.0F - bb - ss) / ss;
/* 400 */       float d = 1.0F - bb + gg * (1.0F + c * ArrayMath.pow(bb, (n2 - 1)));
/* 401 */       float e = ArrayMath.pow(1.0F - ArrayMath.abs(b), 2.0F) * 1.1920929E-7F / 4.0F;
/* 402 */       int k2 = ArrayMath.min((int)ArrayMath.ceil(ArrayMath.log(e) / ArrayMath.log(ArrayMath.abs(b))), 2 * (n2 - 1));
/* 403 */       int m2 = k2 - n2 + 1;
/* 404 */       for (int i4 = m2; i4 > 0; i4--) {
/* 405 */         for (int i5 = 0; i5 < n1; i5++)
/* 406 */           yn2m1[i5] = b * yn2m1[i5] + y[i4][i5]; 
/* 407 */       }  int i3; for (i3 = 0; i3 < n1; i3++)
/* 408 */         yn2m1[i3] = yn2m1[i3] * c; 
/* 409 */       if (n2 - k2 < 1)
/* 410 */         for (i3 = 0; i3 < n1; i3++) {
/* 411 */           yn2m1[i3] = b * yn2m1[i3] + (1.0F + c) * y[0][i3];
/*     */         } 
/* 413 */       m2 = ArrayMath.max(n2 - k2, 1);
/* 414 */       for (int n = m2; n < n2; n++) {
/* 415 */         for (int i5 = 0; i5 < n1; i5++)
/* 416 */           yn2m1[i5] = b * yn2m1[i5] + y[n][i5]; 
/* 417 */       }  int m; for (m = 0; m < n1; m++) {
/* 418 */         yn2m1[m] = yn2m1[m] / d;
/*     */       }
/*     */       
/* 421 */       for (m = 0; m < n1; m++)
/* 422 */         y[n2 - 1][m] = y[n2 - 1][m] - gg * yn2m1[m]; 
/* 423 */       for (int k = n2 - 2; k >= 0; k--) {
/* 424 */         for (int i5 = 0; i5 < n1; i5++) {
/* 425 */           y[k][i5] = y[k][i5] + b * y[k + 1][i5];
/*     */         }
/*     */       } 
/* 428 */       for (int j = 0; j < n1; j++) {
/* 429 */         y[0][j] = y[0][j] / ss;
/*     */       }
/*     */       
/* 432 */       for (int i = 1; i < n2m1; i++) {
/* 433 */         for (int i5 = 0; i5 < n1; i5++)
/* 434 */           y[i][i5] = y[i][i5] + b * y[i - 1][i5]; 
/* 435 */       }  for (int i1 = 0; i1 < n1; i1++) {
/* 436 */         y[n2m1][i1] = yn2m1[i1];
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 444 */     else if (ss > 0.0F && gg > 0.0F) {
/*     */ 
/*     */       
/* 447 */       float[] yn2m1 = new float[n1];
/* 448 */       float oss = 1.0F / ss;
/* 449 */       float sum = 0.0F;
/* 450 */       for (int n = 0; n < n2m1; n++) {
/* 451 */         for (int i3 = 0; i3 < n1; i3++)
/* 452 */           yn2m1[i3] = (yn2m1[i3] + (n + oss) * y[n][i3]) * b; 
/* 453 */       }  int m; for (m = 0; m < n1; m++) {
/* 454 */         yn2m1[m] = yn2m1[m] + (n2m1 + oss) * y[n2m1][m];
/* 455 */         yn2m1[m] = yn2m1[m] / (1.0F + gg * n2m1 + gg / ss);
/*     */       } 
/*     */ 
/*     */       
/* 459 */       for (m = 0; m < n1; m++)
/* 460 */         y[n2m1][m] = y[n2m1][m] - gg * yn2m1[m]; 
/* 461 */       for (int k = n2 - 2; k >= 0; k--) {
/* 462 */         for (int i3 = 0; i3 < n1; i3++)
/* 463 */           y[k][i3] = y[k][i3] + b * y[k + 1][i3]; 
/* 464 */       }  for (int j = 0; j < n1; j++) {
/* 465 */         y[0][j] = y[0][j] / ss;
/*     */       }
/*     */       
/* 468 */       for (int i = 1; i < n2m1; i++) {
/* 469 */         for (int i3 = 0; i3 < n1; i3++)
/* 470 */           y[i][i3] = y[i][i3] + b * y[i - 1][i3]; 
/* 471 */       }  for (int i1 = 0; i1 < n1; i1++) {
/* 472 */         y[n2 - 1][i1] = yn2m1[i1];
/*     */       
/*     */       }
/*     */     }
/* 476 */     else if (ss == 0.0F) {
/*     */ 
/*     */       
/* 479 */       for (int k = 0; k < n1; k++)
/* 480 */         y[0][k] = y[0][k] * -b; 
/* 481 */       for (int j = 1; j < n2m1; j++) {
/* 482 */         for (int m = 0; m < n1; m++) {
/* 483 */           y[j][m] = b * (y[j - 1][m] - y[j][m]);
/*     */         }
/*     */       } 
/* 486 */       for (int i1 = 0; i1 < n1; i1++) {
/* 487 */         y[n2m1][i1] = (y[n2 - 1][i1] - y[n2 - 2][i1]) / gg;
/*     */       }
/*     */       
/* 490 */       for (int i = n2 - 2; i >= 0; i--) {
/* 491 */         for (int m = 0; m < n1; m++) {
/* 492 */           y[i][m] = b * (y[i + 1][m] - y[i][m]);
/*     */         }
/*     */       }
/*     */     
/* 496 */     } else if (gg == 0.0F) {
/*     */ 
/*     */       
/* 499 */       for (i2 = n2 - 2; i2 > 0; i2--) {
/* 500 */         for (int j = 0; j < n1; j++) {
/* 501 */           y[i2][j] = y[i2][j] + b * y[i2 + 1][j];
/*     */         }
/*     */       } 
/* 504 */       for (int i1 = 0; i1 < n1; i1++) {
/* 505 */         y[0][i1] = (y[0][i1] + b * y[1][i1]) / ss;
/*     */       }
/*     */       
/* 508 */       for (int i = 1; i < n2; i++) {
/* 509 */         for (int j = 0; j < n1; j++) {
/* 510 */           y[i][j] = y[i][j] + b * y[i - 1][j];
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 515 */       Check.state(false, "filter is invertible");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyInverse1(final float[][][] x, final float[][][] y) {
/* 526 */     int n = x.length;
/* 527 */     Parallel.loop(n, new Parallel.LoopInt() {
/*     */           public void compute(int i) {
/* 529 */             SymmetricTridiagonalFilter.this.applyInverse1(x[i], y[i]);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyInverse2(final float[][][] x, final float[][][] y) {
/* 539 */     int n = x.length;
/* 540 */     Parallel.loop(n, new Parallel.LoopInt() {
/*     */           public void compute(int i) {
/* 542 */             SymmetricTridiagonalFilter.this.applyInverse2(x[i], y[i]);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyInverse3(float[][][] x, float[][][] y) {
/* 552 */     int n2 = (x[0]).length;
/* 553 */     int n3 = x.length;
/* 554 */     float[][][] xt = new float[n2][n3][];
/* 555 */     float[][][] yt = new float[n2][n3][];
/* 556 */     for (int i2 = 0; i2 < n2; i2++) {
/* 557 */       for (int i3 = 0; i3 < n3; i3++) {
/* 558 */         xt[i2][i3] = x[i3][i2];
/* 559 */         yt[i2][i3] = y[i3][i2];
/*     */       } 
/*     */     } 
/* 562 */     applyInverse2(xt, yt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkInvertible() {
/* 571 */     Check.state((ArrayMath.abs(this._ai) >= 2.0D * ArrayMath.abs(this._b)), "filter is invertible");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void trace(String s) {
/* 578 */     System.out.println(s);
/*     */   }
/*     */   
/*     */   public static void test1Simple() {
/* 582 */     trace("test1Simple");
/* 583 */     int n = 5;
/*     */ 
/*     */     
/* 586 */     double al = 0.75D, af = al;
/* 587 */     double ai = 0.5D;
/* 588 */     double b = 0.25D;
/* 589 */     SymmetricTridiagonalFilter f = new SymmetricTridiagonalFilter(af, ai, al, b);
/* 590 */     float[] x = ArrayMath.zerofloat(n);
/* 591 */     float[] y = ArrayMath.zerofloat(n);
/* 592 */     float[] z = ArrayMath.zerofloat(n);
/* 593 */     ArrayMath.fill(1.0F, x);
/*     */     
/* 595 */     f.apply(x, y);
/* 596 */     f.applyInverse(y, z);
/*     */     
/* 598 */     assertEqual(x, z);
/*     */   }
/*     */   public static void test2Simple() {
/* 601 */     trace("test2Simple");
/* 602 */     int n1 = 5;
/* 603 */     int n2 = 4;
/*     */ 
/*     */     
/* 606 */     double al = 0.75D, af = al;
/* 607 */     double ai = 0.5D;
/* 608 */     double b = 0.25D;
/* 609 */     SymmetricTridiagonalFilter f = new SymmetricTridiagonalFilter(af, ai, al, b);
/* 610 */     float[][] x = ArrayMath.zerofloat(n1, n2);
/* 611 */     float[][] y = ArrayMath.zerofloat(n1, n2);
/* 612 */     float[][] z = ArrayMath.zerofloat(n1, n2);
/* 613 */     ArrayMath.fill(1.0F, x);
/*     */     
/* 615 */     f.apply1(x, y);
/* 616 */     f.apply2(y, y);
/* 617 */     f.applyInverse1(y, z);
/* 618 */     f.applyInverse2(z, z);
/*     */     
/* 620 */     assertEqual(x, z);
/*     */   }
/*     */   public static void test3Simple() {
/* 623 */     trace("test3Simple");
/* 624 */     int n1 = 11;
/* 625 */     int n2 = 12;
/* 626 */     int n3 = 13;
/* 627 */     float[][][] r = ArrayMath.randfloat(n1, n2, n3);
/* 628 */     float[][][] x = ArrayMath.copy(r);
/* 629 */     float[][][] y = ArrayMath.copy(r);
/* 630 */     SymmetricTridiagonalFilter stf = new SymmetricTridiagonalFilter(2.6D, 2.5D, 2.7D, 1.2D);
/*     */     
/* 632 */     stf.apply1(x, x);
/* 633 */     stf.apply2(x, x);
/* 634 */     stf.apply3(x, x);
/* 635 */     stf.apply1(y, y);
/* 636 */     y = transpose12(y);
/* 637 */     stf.apply1(y, y);
/* 638 */     y = transpose12(y);
/* 639 */     y = transpose23(y);
/* 640 */     stf.apply2(y, y);
/* 641 */     y = transpose23(y);
/* 642 */     assertEqual(x, y);
/*     */   }
/*     */   private static float[][][] transpose12(float[][][] x) {
/* 645 */     int n1 = (x[0][0]).length;
/* 646 */     int n2 = (x[0]).length;
/* 647 */     int n3 = x.length;
/* 648 */     float[][][] y = new float[n3][n1][n2];
/* 649 */     for (int i3 = 0; i3 < n3; i3++) {
/* 650 */       for (int i2 = 0; i2 < n2; i2++) {
/* 651 */         for (int i1 = 0; i1 < n1; i1++) {
/* 652 */           y[i3][i1][i2] = x[i3][i2][i1];
/*     */         }
/*     */       } 
/*     */     } 
/* 656 */     return y;
/*     */   }
/*     */   private static float[][][] transpose23(float[][][] x) {
/* 659 */     int n1 = (x[0][0]).length;
/* 660 */     int n2 = (x[0]).length;
/* 661 */     int n3 = x.length;
/* 662 */     float[][][] y = new float[n2][n3][n1];
/* 663 */     for (int i3 = 0; i3 < n3; i3++) {
/* 664 */       for (int i2 = 0; i2 < n2; i2++) {
/* 665 */         for (int i1 = 0; i1 < n1; i1++) {
/* 666 */           y[i2][i3][i1] = x[i3][i2][i1];
/*     */         }
/*     */       } 
/*     */     } 
/* 670 */     return y;
/*     */   }
/*     */   private static SymmetricTridiagonalFilter makeRandomFilter() {
/* 673 */     Random r = new Random();
/*     */     
/* 675 */     boolean aeq2b = r.nextBoolean();
/* 676 */     boolean abneg = r.nextBoolean();
/* 677 */     boolean afzs = r.nextBoolean();
/* 678 */     boolean alzs = r.nextBoolean();
/* 679 */     if (aeq2b && afzs == true && alzs == true) {
/* 680 */       if (r.nextBoolean()) {
/* 681 */         afzs = false;
/*     */       } else {
/* 683 */         alzs = false;
/*     */       } 
/*     */     }
/* 686 */     float b = r.nextFloat();
/* 687 */     float ai = 2.0F * b;
/* 688 */     if (!aeq2b) ai = (float)(ai + ArrayMath.max(0.001D, r.nextFloat()) * b); 
/* 689 */     if (abneg) ai = -ai; 
/* 690 */     float af = ai;
/* 691 */     float al = ai;
/* 692 */     if (afzs) af = ai + b; 
/* 693 */     if (alzs) al = ai + b; 
/* 694 */     return new SymmetricTridiagonalFilter(af, ai, al, b);
/*     */   }
/*     */   public static void test1Random() {
/* 697 */     trace("test1Random");
/* 698 */     Random r = new Random();
/* 699 */     int ntest = 1000;
/* 700 */     for (int itest = 0; itest < ntest; itest++) {
/* 701 */       SymmetricTridiagonalFilter stf = makeRandomFilter();
/* 702 */       boolean inplace = r.nextBoolean();
/* 703 */       int n = 2 + r.nextInt(10);
/* 704 */       float[] t = ArrayMath.randfloat(r, n);
/* 705 */       float[] x = ArrayMath.copy(t);
/* 706 */       float[] y = inplace ? x : ArrayMath.zerofloat(n);
/* 707 */       float[] z = inplace ? x : ArrayMath.zerofloat(n);
/* 708 */       stf.apply(x, y);
/* 709 */       stf.applyInverse(y, z);
/* 710 */       assertEqual(t, x);
/* 711 */       assertEqual(t, z);
/*     */     } 
/*     */   }
/*     */   public static void test2Random() {
/* 715 */     trace("test2Random");
/* 716 */     Random r = new Random();
/* 717 */     int ntest = 1000;
/* 718 */     for (int itest = 0; itest < ntest; itest++) {
/* 719 */       SymmetricTridiagonalFilter stf = makeRandomFilter();
/* 720 */       boolean inplace = r.nextBoolean();
/* 721 */       int n1 = 2 + r.nextInt(11);
/* 722 */       int n2 = 2 + r.nextInt(12);
/* 723 */       float[][] t = ArrayMath.randfloat(r, n1, n2);
/* 724 */       float[][] x = ArrayMath.copy(t);
/* 725 */       float[][] y = inplace ? x : ArrayMath.zerofloat(n1, n2);
/* 726 */       float[][] z = inplace ? x : ArrayMath.zerofloat(n1, n2);
/* 727 */       stf.apply1(x, y);
/* 728 */       stf.applyInverse1(y, z);
/* 729 */       assertEqual(t, x);
/* 730 */       assertEqual(t, z);
/* 731 */       stf.apply2(x, y);
/* 732 */       stf.applyInverse2(y, z);
/* 733 */       assertEqual(t, x);
/* 734 */       assertEqual(t, z);
/*     */     } 
/*     */   }
/*     */   public static void test3Random() {
/* 738 */     trace("test3Random");
/* 739 */     Random r = new Random();
/* 740 */     int ntest = 1000;
/* 741 */     for (int itest = 0; itest < ntest; itest++) {
/* 742 */       SymmetricTridiagonalFilter stf = makeRandomFilter();
/* 743 */       boolean inplace = r.nextBoolean();
/* 744 */       int n1 = 2 + r.nextInt(11);
/* 745 */       int n2 = 2 + r.nextInt(12);
/* 746 */       int n3 = 2 + r.nextInt(13);
/* 747 */       float[][][] t = ArrayMath.randfloat(r, n1, n2, n3);
/* 748 */       float[][][] x = ArrayMath.copy(t);
/* 749 */       float[][][] y = inplace ? x : ArrayMath.zerofloat(n1, n2, n3);
/* 750 */       float[][][] z = inplace ? x : ArrayMath.zerofloat(n1, n2, n3);
/* 751 */       stf.apply1(x, y);
/* 752 */       stf.applyInverse1(y, z);
/* 753 */       assertEqual(t, x);
/* 754 */       assertEqual(t, z);
/* 755 */       stf.apply2(x, y);
/* 756 */       stf.applyInverse2(y, z);
/* 757 */       assertEqual(t, x);
/* 758 */       assertEqual(t, z);
/* 759 */       stf.apply3(x, y);
/* 760 */       stf.applyInverse3(y, z);
/* 761 */       assertEqual(t, x);
/* 762 */       assertEqual(t, z);
/*     */     } 
/*     */   }
/*     */   private static void assertEqual(float[] e, float[] a) {
/* 766 */     float tol = 0.001F * ArrayMath.max(ArrayMath.abs(e));
/* 767 */     assertEqual(e, a, tol);
/*     */   }
/*     */   private static void assertEqual(float[][] e, float[][] a) {
/* 770 */     float tol = 0.001F * ArrayMath.max(ArrayMath.abs(e));
/* 771 */     assertEqual(e, a, tol);
/*     */   }
/*     */   private static void assertEqual(float[][][] e, float[][][] a) {
/* 774 */     float tol = 0.001F * ArrayMath.max(ArrayMath.abs(e));
/* 775 */     assertEqual(e, a, tol);
/*     */   }
/*     */   private static void assertEqual(float[] e, float[] a, float tol) {
/* 778 */     int n = e.length;
/* 779 */     for (int i = 0; i < n; i++) {
/* 780 */       float error = ArrayMath.abs(e[i] - a[i]);
/* 781 */       if (error > tol)
/* 782 */         trace("expected=" + e[i] + " actual=" + a[i]); 
/* 783 */       assert error < tol;
/*     */     } 
/*     */   }
/*     */   private static void assertEqual(float[][] e, float[][] a, float tol) {
/* 787 */     int n = e.length;
/* 788 */     for (int i = 0; i < n; i++)
/* 789 */       assertEqual(e[i], a[i], tol); 
/*     */   }
/*     */   private static void assertEqual(float[][][] e, float[][][] a, float tol) {
/* 792 */     int n = e.length;
/* 793 */     for (int i = 0; i < n; i++)
/* 794 */       assertEqual(e[i], a[i], tol); 
/*     */   }
/*     */   public static void main(String[] args) {
/* 797 */     test1Simple();
/* 798 */     test2Simple();
/* 799 */     test3Simple();
/* 800 */     test1Random();
/* 801 */     test2Random();
/* 802 */     test3Random();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/SymmetricTridiagonalFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */