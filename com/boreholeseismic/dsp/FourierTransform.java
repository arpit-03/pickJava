/*     */ package com.boreholeseismic.dsp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FourierTransform
/*     */ {
/*     */   public enum Direction
/*     */   {
/*  62 */     Forward,
/*     */ 
/*     */ 
/*     */     
/*  66 */     Backward;
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
/*     */   public static void DFT(ComplexNumber[] data, Direction direction) {
/*  78 */     int n = data.length;
/*  79 */     ComplexNumber[] c = new ComplexNumber[n];
/*     */     
/*     */     int i;
/*  82 */     for (i = 0; i < n; i++) {
/*  83 */       c[i] = new ComplexNumber(0.0D, 0.0D);
/*  84 */       double sumRe = 0.0D;
/*  85 */       double sumIm = 0.0D;
/*  86 */       double phim = 6.283185307179586D * i / n;
/*     */ 
/*     */       
/*  89 */       for (int j = 0; j < n; j++) {
/*  90 */         double gRe = (data[j]).real;
/*  91 */         double gIm = (data[j]).imaginary;
/*  92 */         double cosw = Math.cos(phim * j);
/*  93 */         double sinw = Math.sin(phim * j);
/*  94 */         if (direction == Direction.Backward) {
/*  95 */           sinw = -sinw;
/*     */         }
/*  97 */         sumRe += gRe * cosw + (data[j]).imaginary * sinw;
/*  98 */         sumIm += gIm * cosw - (data[j]).real * sinw;
/*     */       } 
/*     */       
/* 101 */       c[i] = new ComplexNumber(sumRe, sumIm);
/*     */     } 
/*     */     
/* 104 */     if (direction == Direction.Backward) {
/* 105 */       for (i = 0; i < c.length; i++) {
/* 106 */         (c[i]).real /= n;
/* 107 */         (c[i]).imaginary /= n;
/*     */       } 
/*     */     } else {
/*     */       
/* 111 */       for (i = 0; i < c.length; i++) {
/* 112 */         (data[i]).real = (c[i]).real;
/* 113 */         (data[i]).imaginary = (c[i]).imaginary;
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
/*     */   public static void DFT2(ComplexNumber[][] data, Direction direction) {
/* 125 */     int n = data.length;
/* 126 */     int m = (data[0]).length;
/* 127 */     ComplexNumber[] row = new ComplexNumber[Math.max(m, n)];
/*     */     
/* 129 */     for (int i = 0; i < n; i++) {
/*     */       int k;
/* 131 */       for (k = 0; k < n; k++) {
/* 132 */         row[k] = data[i][k];
/*     */       }
/* 134 */       DFT(row, direction);
/*     */       
/* 136 */       for (k = 0; k < n; k++) {
/* 137 */         data[i][k] = row[k];
/*     */       }
/*     */     } 
/*     */     
/* 141 */     ComplexNumber[] col = new ComplexNumber[n];
/*     */     
/* 143 */     for (int j = 0; j < n; j++) {
/*     */       int k;
/* 145 */       for (k = 0; k < n; k++) {
/* 146 */         col[k] = data[k][j];
/*     */       }
/* 148 */       DFT(col, direction);
/*     */       
/* 150 */       for (k = 0; k < n; k++) {
/* 151 */         data[k][j] = col[k];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void FFT(ComplexNumber[] data, Direction direction) {
/* 161 */     double[] real = ComplexNumber.getReal(data);
/* 162 */     double[] img = ComplexNumber.getImaginary(data);
/* 163 */     if (direction == Direction.Forward) {
/* 164 */       FFT(real, img);
/*     */     } else {
/* 166 */       FFT(img, real);
/* 167 */     }  if (direction == Direction.Forward) {
/* 168 */       for (int i = 0; i < real.length; i++) {
/* 169 */         data[i] = new ComplexNumber(real[i], img[i]);
/*     */       }
/*     */     } else {
/*     */       
/* 173 */       int n = real.length;
/* 174 */       for (int i = 0; i < n; i++) {
/* 175 */         data[i] = new ComplexNumber(real[i] / n, img[i] / n);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void FFT2(ComplexNumber[][] data, Direction direction) {
/* 186 */     int n = data.length;
/* 187 */     int m = (data[0]).length;
/*     */ 
/*     */     
/* 190 */     for (int i = 0; i < n; i++) {
/*     */ 
/*     */ 
/*     */       
/* 194 */       ComplexNumber[] row = data[i];
/*     */       
/* 196 */       FFT(row, direction);
/*     */       
/* 198 */       for (int k = 0; k < m; k++) {
/* 199 */         data[i][k] = row[k];
/*     */       }
/*     */     } 
/*     */     
/* 203 */     ComplexNumber[] col = new ComplexNumber[n];
/*     */     
/* 205 */     for (int j = 0; j < m; j++) {
/*     */       int k;
/* 207 */       for (k = 0; k < n; k++) {
/* 208 */         col[k] = data[k][j];
/*     */       }
/* 210 */       FFT(col, direction);
/*     */       
/* 212 */       for (k = 0; k < n; k++) {
/* 213 */         data[k][j] = col[k];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void FFT(double[] real, double[] imag) {
/* 222 */     int n = real.length;
/* 223 */     if (n == 0)
/*     */       return; 
/* 225 */     if ((n & n - 1) == 0) {
/* 226 */       transformRadix2(real, imag);
/*     */     } else {
/* 228 */       transformBluestein(real, imag);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void inverseTransform(double[] real, double[] imag) {
/* 237 */     FFT(imag, real);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void transformRadix2(double[] real, double[] imag) {
/* 245 */     int n = real.length;
/* 246 */     int levels = 31 - Integer.numberOfLeadingZeros(n);
/*     */ 
/*     */     
/* 249 */     double[] cosTable = new double[n / 2];
/* 250 */     double[] sinTable = new double[n / 2]; int i;
/* 251 */     for (i = 0; i < n / 2; i++) {
/* 252 */       cosTable[i] = Math.cos(6.283185307179586D * i / n);
/* 253 */       sinTable[i] = Math.sin(6.283185307179586D * i / n);
/*     */     } 
/*     */ 
/*     */     
/* 257 */     for (i = 0; i < n; i++) {
/* 258 */       int j = Integer.reverse(i) >>> 32 - levels;
/* 259 */       if (j > i) {
/* 260 */         double temp = real[i];
/* 261 */         real[i] = real[j];
/* 262 */         real[j] = temp;
/* 263 */         temp = imag[i];
/* 264 */         imag[i] = imag[j];
/* 265 */         imag[j] = temp;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 270 */     for (int size = 2; size <= n; size *= 2) {
/* 271 */       int halfsize = size / 2;
/* 272 */       int tablestep = n / size;
/* 273 */       for (int j = 0; j < n; j += size) {
/* 274 */         for (int m = j, k = 0; m < j + halfsize; m++, k += tablestep) {
/* 275 */           double tpre = real[m + halfsize] * cosTable[k] + imag[m + halfsize] * sinTable[k];
/* 276 */           double tpim = -real[m + halfsize] * sinTable[k] + imag[m + halfsize] * cosTable[k];
/* 277 */           real[m + halfsize] = real[m] - tpre;
/* 278 */           imag[m + halfsize] = imag[m] - tpim;
/* 279 */           real[m] = real[m] + tpre;
/* 280 */           imag[m] = imag[m] + tpim;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 285 */       if (size == n) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void transformBluestein(double[] real, double[] imag) {
/* 296 */     int n = real.length;
/* 297 */     int m = Integer.highestOneBit(n * 2 + 1) << 1;
/*     */ 
/*     */     
/* 300 */     double[] cosTable = new double[n];
/* 301 */     double[] sinTable = new double[n];
/* 302 */     for (int i = 0; i < n; i++) {
/* 303 */       int i2 = (int)(i * i % (n * 2));
/* 304 */       cosTable[i] = Math.cos(Math.PI * i2 / n);
/* 305 */       sinTable[i] = Math.sin(Math.PI * i2 / n);
/*     */     } 
/*     */ 
/*     */     
/* 309 */     double[] areal = new double[m];
/* 310 */     double[] aimag = new double[m];
/* 311 */     for (int j = 0; j < n; j++) {
/* 312 */       areal[j] = real[j] * cosTable[j] + imag[j] * sinTable[j];
/* 313 */       aimag[j] = -real[j] * sinTable[j] + imag[j] * cosTable[j];
/*     */     } 
/* 315 */     double[] breal = new double[m];
/* 316 */     double[] bimag = new double[m];
/* 317 */     breal[0] = cosTable[0];
/* 318 */     bimag[0] = sinTable[0];
/* 319 */     for (int k = 1; k < n; k++) {
/* 320 */       breal[m - k] = cosTable[k]; breal[k] = cosTable[k];
/* 321 */       bimag[m - k] = sinTable[k]; bimag[k] = sinTable[k];
/*     */     } 
/*     */ 
/*     */     
/* 325 */     double[] creal = new double[m];
/* 326 */     double[] cimag = new double[m];
/* 327 */     convolve(areal, aimag, breal, bimag, creal, cimag);
/*     */ 
/*     */     
/* 330 */     for (int i1 = 0; i1 < n; i1++) {
/* 331 */       real[i1] = creal[i1] * cosTable[i1] + cimag[i1] * sinTable[i1];
/* 332 */       imag[i1] = -creal[i1] * sinTable[i1] + cimag[i1] * cosTable[i1];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void convolve(double[] x, double[] y, double[] out) {
/* 342 */     int n = x.length;
/* 343 */     convolve(x, new double[n], y, new double[n], out, new double[n]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void convolve(double[] xreal, double[] ximag, double[] yreal, double[] yimag, double[] outreal, double[] outimag) {
/* 353 */     int n = xreal.length;
/*     */     
/* 355 */     FFT(xreal, ximag);
/* 356 */     FFT(yreal, yimag); int i;
/* 357 */     for (i = 0; i < n; i++) {
/* 358 */       double temp = xreal[i] * yreal[i] - ximag[i] * yimag[i];
/* 359 */       ximag[i] = ximag[i] * yreal[i] + xreal[i] * yimag[i];
/* 360 */       xreal[i] = temp;
/*     */     } 
/* 362 */     inverseTransform(xreal, ximag);
/*     */ 
/*     */     
/* 365 */     for (i = 0; i < n; i++) {
/* 366 */       outreal[i] = xreal[i] / n;
/* 367 */       outimag[i] = ximag[i] / n;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void FFTShift1D(double[] x, Direction direction) {
/* 378 */     if (x.length == 1) {
/*     */       return;
/*     */     }
/* 381 */     double[] temp = (double[])x.clone();
/* 382 */     int move = x.length / 2;
/*     */     
/* 384 */     if (direction == Direction.Forward) {
/* 385 */       int c = 0; int i;
/* 386 */       for (i = x.length - move; i < x.length; i++) {
/* 387 */         x[c++] = temp[i];
/*     */       }
/* 389 */       for (i = 0; i < x.length - move; i++) {
/* 390 */         x[c++] = temp[i];
/*     */       }
/*     */     } else {
/*     */       
/* 394 */       int c = 0; int i;
/* 395 */       for (i = move; i < x.length; i++) {
/* 396 */         x[c++] = temp[i];
/*     */       }
/* 398 */       for (i = 0; i < move; i++) {
/* 399 */         x[c++] = temp[i];
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
/*     */   public static <E> void FFTShift1D(Object[] x, Direction direction) {
/* 412 */     if (x.length == 1) {
/*     */       return;
/*     */     }
/* 415 */     Object[] temp = (Object[])x.clone();
/* 416 */     int move = x.length / 2;
/*     */     
/* 418 */     if (direction == Direction.Forward) {
/* 419 */       int c = 0; int i;
/* 420 */       for (i = x.length - move; i < x.length; i++) {
/* 421 */         x[c++] = temp[i];
/*     */       }
/* 423 */       for (i = 0; i < x.length - move; i++) {
/* 424 */         x[c++] = temp[i];
/*     */       }
/*     */     } else {
/* 427 */       int c = 0; int i;
/* 428 */       for (i = move; i < x.length; i++) {
/* 429 */         x[c++] = temp[i];
/*     */       }
/* 431 */       for (i = 0; i < move; i++) {
/* 432 */         x[c++] = temp[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void FFTShift2D(double[][] x, Direction direction) {
/* 442 */     FFTShift2D(x, direction, 1);
/* 443 */     FFTShift2D(x, direction, 2);
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
/*     */   public static void FFTShift2D(double[][] x, Direction direction, int dimension) {
/* 455 */     double[][] temp = new double[x.length][(x[0]).length]; int i;
/* 456 */     for (i = 0; i < x.length; i++) {
/* 457 */       for (int j = 0; j < (x[0]).length; j++) {
/* 458 */         temp[i][j] = x[i][j];
/*     */       }
/*     */     } 
/*     */     
/* 462 */     if (direction == Direction.Forward) {
/*     */       
/* 464 */       if (dimension == 1) {
/* 465 */         int move = temp.length / 2; int j;
/* 466 */         for (j = 0; j < move; j++) {
/* 467 */           for (int k = 0; k < (x[0]).length; k++) {
/* 468 */             x[j][k] = temp[temp.length - move + j][k];
/*     */           }
/*     */         } 
/*     */         
/* 472 */         for (j = move; j < x.length; j++) {
/* 473 */           for (int k = 0; k < (x[0]).length; k++) {
/* 474 */             x[j][k] = temp[j - move][k];
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 481 */       if (dimension == 2) {
/* 482 */         for (i = 0; i < x.length; i++) {
/* 483 */           FFTShift1D(x[i], Direction.Forward);
/*     */         }
/*     */       }
/*     */     } else {
/*     */       
/* 488 */       if (dimension == 1) {
/* 489 */         int move = temp.length / 2; int j;
/* 490 */         for (j = 0; j < x.length - move; j++) {
/* 491 */           for (int k = 0; k < (x[0]).length; k++) {
/* 492 */             x[j][k] = temp[move + j][k];
/*     */           }
/*     */         } 
/* 495 */         for (j = 0; j < move; j++) {
/* 496 */           for (int k = 0; k < (x[0]).length; k++) {
/* 497 */             x[x.length - move + j][k] = temp[j][k];
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 502 */       if (dimension == 2) {
/* 503 */         for (i = 0; i < x.length; i++) {
/* 504 */           FFTShift1D(x[i], Direction.Backward);
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
/*     */   public static <E> void FFTShift2D(Object[][] x, Direction direction) {
/* 516 */     FFTShift2D(x, direction, 1);
/* 517 */     FFTShift2D(x, direction, 2);
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
/*     */   public static <E> void FFTShift2D(Object[][] x, Direction direction, int dimension) {
/* 529 */     Object[][] temp = new Object[x.length][(x[0]).length]; int i;
/* 530 */     for (i = 0; i < x.length; i++) {
/* 531 */       for (int j = 0; j < (x[0]).length; j++) {
/* 532 */         temp[i][j] = x[i][j];
/*     */       }
/*     */     } 
/*     */     
/* 536 */     if (direction == Direction.Forward) {
/*     */       
/* 538 */       if (dimension == 1) {
/* 539 */         int move = temp.length / 2; int j;
/* 540 */         for (j = 0; j < move; j++) {
/* 541 */           for (int k = 0; k < (x[0]).length; k++) {
/* 542 */             x[j][k] = temp[temp.length - move + j][k];
/*     */           }
/*     */         } 
/*     */         
/* 546 */         for (j = move; j < x.length; j++) {
/* 547 */           for (int k = 0; k < (x[0]).length; k++) {
/* 548 */             x[j][k] = temp[j - move][k];
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 555 */       if (dimension == 2) {
/* 556 */         for (i = 0; i < x.length; i++) {
/* 557 */           FFTShift1D(x[i], Direction.Forward);
/*     */         }
/*     */       }
/*     */     } else {
/*     */       
/* 562 */       if (dimension == 1) {
/* 563 */         int move = temp.length / 2; int j;
/* 564 */         for (j = 0; j < x.length - move; j++) {
/* 565 */           for (int k = 0; k < (x[0]).length; k++) {
/* 566 */             x[j][k] = temp[move + j][k];
/*     */           }
/*     */         } 
/* 569 */         for (j = 0; j < move; j++) {
/* 570 */           for (int k = 0; k < (x[0]).length; k++) {
/* 571 */             x[x.length - move + j][k] = temp[j][k];
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 576 */       if (dimension == 2)
/* 577 */         for (i = 0; i < x.length; i++)
/* 578 */           FFTShift1D(x[i], Direction.Backward);  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/FourierTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */