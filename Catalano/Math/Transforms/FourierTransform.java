/*     */ package Catalano.Math.Transforms;
/*     */ 
/*     */ import Catalano.Math.ComplexNumber;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  64 */     Forward,
/*     */ 
/*     */ 
/*     */     
/*  68 */     Backward;
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
/*  80 */     int n = data.length;
/*  81 */     ComplexNumber[] c = new ComplexNumber[n];
/*     */     
/*     */     int i;
/*  84 */     for (i = 0; i < n; i++) {
/*  85 */       c[i] = new ComplexNumber(0.0D, 0.0D);
/*  86 */       double sumRe = 0.0D;
/*  87 */       double sumIm = 0.0D;
/*  88 */       double phim = 6.283185307179586D * i / n;
/*     */ 
/*     */       
/*  91 */       for (int j = 0; j < n; j++) {
/*  92 */         double gRe = (data[j]).real;
/*  93 */         double gIm = (data[j]).imaginary;
/*  94 */         double cosw = Math.cos(phim * j);
/*  95 */         double sinw = Math.sin(phim * j);
/*  96 */         if (direction == Direction.Backward) {
/*  97 */           sinw = -sinw;
/*     */         }
/*  99 */         sumRe += gRe * cosw + (data[j]).imaginary * sinw;
/* 100 */         sumIm += gIm * cosw - (data[j]).real * sinw;
/*     */       } 
/*     */       
/* 103 */       c[i] = new ComplexNumber(sumRe, sumIm);
/*     */     } 
/*     */     
/* 106 */     if (direction == Direction.Backward) {
/* 107 */       for (i = 0; i < c.length; i++) {
/* 108 */         (c[i]).real /= n;
/* 109 */         (c[i]).imaginary /= n;
/*     */       } 
/*     */     } else {
/*     */       
/* 113 */       for (i = 0; i < c.length; i++) {
/* 114 */         (data[i]).real = (c[i]).real;
/* 115 */         (data[i]).imaginary = (c[i]).imaginary;
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
/* 127 */     int n = data.length;
/* 128 */     int m = (data[0]).length;
/* 129 */     ComplexNumber[] row = new ComplexNumber[Math.max(m, n)];
/*     */     
/* 131 */     for (int i = 0; i < n; i++) {
/*     */       int k;
/* 133 */       for (k = 0; k < n; k++) {
/* 134 */         row[k] = data[i][k];
/*     */       }
/* 136 */       DFT(row, direction);
/*     */       
/* 138 */       for (k = 0; k < n; k++) {
/* 139 */         data[i][k] = row[k];
/*     */       }
/*     */     } 
/*     */     
/* 143 */     ComplexNumber[] col = new ComplexNumber[n];
/*     */     
/* 145 */     for (int j = 0; j < n; j++) {
/*     */       int k;
/* 147 */       for (k = 0; k < n; k++) {
/* 148 */         col[k] = data[k][j];
/*     */       }
/* 150 */       DFT(col, direction);
/*     */       
/* 152 */       for (k = 0; k < n; k++) {
/* 153 */         data[k][j] = col[k];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void FFT(ComplexNumber[] data, Direction direction) {
/* 163 */     double[] real = ComplexNumber.getReal(data);
/* 164 */     double[] img = ComplexNumber.getImaginary(data);
/* 165 */     if (direction == Direction.Forward) {
/* 166 */       FFT(real, img);
/*     */     } else {
/* 168 */       FFT(img, real);
/* 169 */     }  if (direction == Direction.Forward) {
/* 170 */       for (int i = 0; i < real.length; i++) {
/* 171 */         data[i] = new ComplexNumber(real[i], img[i]);
/*     */       }
/*     */     } else {
/*     */       
/* 175 */       int n = real.length;
/* 176 */       for (int i = 0; i < n; i++) {
/* 177 */         data[i] = new ComplexNumber(real[i] / n, img[i] / n);
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
/* 188 */     int n = data.length;
/* 189 */     int m = (data[0]).length;
/*     */ 
/*     */     
/* 192 */     for (int i = 0; i < n; i++) {
/*     */ 
/*     */ 
/*     */       
/* 196 */       ComplexNumber[] row = data[i];
/*     */       
/* 198 */       FFT(row, direction);
/*     */       
/* 200 */       for (int k = 0; k < m; k++) {
/* 201 */         data[i][k] = row[k];
/*     */       }
/*     */     } 
/*     */     
/* 205 */     ComplexNumber[] col = new ComplexNumber[n];
/*     */     
/* 207 */     for (int j = 0; j < m; j++) {
/*     */       int k;
/* 209 */       for (k = 0; k < n; k++) {
/* 210 */         col[k] = data[k][j];
/*     */       }
/* 212 */       FFT(col, direction);
/*     */       
/* 214 */       for (k = 0; k < n; k++) {
/* 215 */         data[k][j] = col[k];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void FFT(double[] real, double[] imag) {
/* 224 */     int n = real.length;
/* 225 */     if (n == 0)
/*     */       return; 
/* 227 */     if ((n & n - 1) == 0) {
/* 228 */       transformRadix2(real, imag);
/*     */     } else {
/* 230 */       transformBluestein(real, imag);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void inverseTransform(double[] real, double[] imag) {
/* 239 */     FFT(imag, real);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void transformRadix2(double[] real, double[] imag) {
/* 247 */     int n = real.length;
/* 248 */     int levels = 31 - Integer.numberOfLeadingZeros(n);
/*     */ 
/*     */     
/* 251 */     double[] cosTable = new double[n / 2];
/* 252 */     double[] sinTable = new double[n / 2]; int i;
/* 253 */     for (i = 0; i < n / 2; i++) {
/* 254 */       cosTable[i] = Math.cos(6.283185307179586D * i / n);
/* 255 */       sinTable[i] = Math.sin(6.283185307179586D * i / n);
/*     */     } 
/*     */ 
/*     */     
/* 259 */     for (i = 0; i < n; i++) {
/* 260 */       int j = Integer.reverse(i) >>> 32 - levels;
/* 261 */       if (j > i) {
/* 262 */         double temp = real[i];
/* 263 */         real[i] = real[j];
/* 264 */         real[j] = temp;
/* 265 */         temp = imag[i];
/* 266 */         imag[i] = imag[j];
/* 267 */         imag[j] = temp;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 272 */     for (int size = 2; size <= n; size *= 2) {
/* 273 */       int halfsize = size / 2;
/* 274 */       int tablestep = n / size;
/* 275 */       for (int j = 0; j < n; j += size) {
/* 276 */         for (int m = j, k = 0; m < j + halfsize; m++, k += tablestep) {
/* 277 */           double tpre = real[m + halfsize] * cosTable[k] + imag[m + halfsize] * sinTable[k];
/* 278 */           double tpim = -real[m + halfsize] * sinTable[k] + imag[m + halfsize] * cosTable[k];
/* 279 */           real[m + halfsize] = real[m] - tpre;
/* 280 */           imag[m + halfsize] = imag[m] - tpim;
/* 281 */           real[m] = real[m] + tpre;
/* 282 */           imag[m] = imag[m] + tpim;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 287 */       if (size == n) {
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
/* 298 */     int n = real.length;
/* 299 */     int m = Integer.highestOneBit(n * 2 + 1) << 1;
/*     */ 
/*     */     
/* 302 */     double[] cosTable = new double[n];
/* 303 */     double[] sinTable = new double[n];
/* 304 */     for (int i = 0; i < n; i++) {
/* 305 */       int i2 = (int)(i * i % (n * 2));
/* 306 */       cosTable[i] = Math.cos(Math.PI * i2 / n);
/* 307 */       sinTable[i] = Math.sin(Math.PI * i2 / n);
/*     */     } 
/*     */ 
/*     */     
/* 311 */     double[] areal = new double[m];
/* 312 */     double[] aimag = new double[m];
/* 313 */     for (int j = 0; j < n; j++) {
/* 314 */       areal[j] = real[j] * cosTable[j] + imag[j] * sinTable[j];
/* 315 */       aimag[j] = -real[j] * sinTable[j] + imag[j] * cosTable[j];
/*     */     } 
/* 317 */     double[] breal = new double[m];
/* 318 */     double[] bimag = new double[m];
/* 319 */     breal[0] = cosTable[0];
/* 320 */     bimag[0] = sinTable[0];
/* 321 */     for (int k = 1; k < n; k++) {
/* 322 */       breal[m - k] = cosTable[k]; breal[k] = cosTable[k];
/* 323 */       bimag[m - k] = sinTable[k]; bimag[k] = sinTable[k];
/*     */     } 
/*     */ 
/*     */     
/* 327 */     double[] creal = new double[m];
/* 328 */     double[] cimag = new double[m];
/* 329 */     convolve(areal, aimag, breal, bimag, creal, cimag);
/*     */ 
/*     */     
/* 332 */     for (int i1 = 0; i1 < n; i1++) {
/* 333 */       real[i1] = creal[i1] * cosTable[i1] + cimag[i1] * sinTable[i1];
/* 334 */       imag[i1] = -creal[i1] * sinTable[i1] + cimag[i1] * cosTable[i1];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void convolve(double[] x, double[] y, double[] out) {
/* 344 */     int n = x.length;
/* 345 */     convolve(x, new double[n], y, new double[n], out, new double[n]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void convolve(double[] xreal, double[] ximag, double[] yreal, double[] yimag, double[] outreal, double[] outimag) {
/* 355 */     int n = xreal.length;
/*     */     
/* 357 */     FFT(xreal, ximag);
/* 358 */     FFT(yreal, yimag); int i;
/* 359 */     for (i = 0; i < n; i++) {
/* 360 */       double temp = xreal[i] * yreal[i] - ximag[i] * yimag[i];
/* 361 */       ximag[i] = ximag[i] * yreal[i] + xreal[i] * yimag[i];
/* 362 */       xreal[i] = temp;
/*     */     } 
/* 364 */     inverseTransform(xreal, ximag);
/*     */ 
/*     */     
/* 367 */     for (i = 0; i < n; i++) {
/* 368 */       outreal[i] = xreal[i] / n;
/* 369 */       outimag[i] = ximag[i] / n;
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
/* 380 */     if (x.length == 1) {
/*     */       return;
/*     */     }
/* 383 */     double[] temp = (double[])x.clone();
/* 384 */     int move = x.length / 2;
/*     */     
/* 386 */     if (direction == Direction.Forward) {
/* 387 */       int c = 0; int i;
/* 388 */       for (i = x.length - move; i < x.length; i++) {
/* 389 */         x[c++] = temp[i];
/*     */       }
/* 391 */       for (i = 0; i < x.length - move; i++) {
/* 392 */         x[c++] = temp[i];
/*     */       }
/*     */     } else {
/*     */       
/* 396 */       int c = 0; int i;
/* 397 */       for (i = move; i < x.length; i++) {
/* 398 */         x[c++] = temp[i];
/*     */       }
/* 400 */       for (i = 0; i < move; i++) {
/* 401 */         x[c++] = temp[i];
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
/* 414 */     if (x.length == 1) {
/*     */       return;
/*     */     }
/* 417 */     Object[] temp = (Object[])x.clone();
/* 418 */     int move = x.length / 2;
/*     */     
/* 420 */     if (direction == Direction.Forward) {
/* 421 */       int c = 0; int i;
/* 422 */       for (i = x.length - move; i < x.length; i++) {
/* 423 */         x[c++] = temp[i];
/*     */       }
/* 425 */       for (i = 0; i < x.length - move; i++) {
/* 426 */         x[c++] = temp[i];
/*     */       }
/*     */     } else {
/* 429 */       int c = 0; int i;
/* 430 */       for (i = move; i < x.length; i++) {
/* 431 */         x[c++] = temp[i];
/*     */       }
/* 433 */       for (i = 0; i < move; i++) {
/* 434 */         x[c++] = temp[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void FFTShift2D(double[][] x, Direction direction) {
/* 444 */     FFTShift2D(x, direction, 1);
/* 445 */     FFTShift2D(x, direction, 2);
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
/* 457 */     double[][] temp = new double[x.length][(x[0]).length]; int i;
/* 458 */     for (i = 0; i < x.length; i++) {
/* 459 */       for (int j = 0; j < (x[0]).length; j++) {
/* 460 */         temp[i][j] = x[i][j];
/*     */       }
/*     */     } 
/*     */     
/* 464 */     if (direction == Direction.Forward) {
/*     */       
/* 466 */       if (dimension == 1) {
/* 467 */         int move = temp.length / 2; int j;
/* 468 */         for (j = 0; j < move; j++) {
/* 469 */           for (int k = 0; k < (x[0]).length; k++) {
/* 470 */             x[j][k] = temp[temp.length - move + j][k];
/*     */           }
/*     */         } 
/*     */         
/* 474 */         for (j = move; j < x.length; j++) {
/* 475 */           for (int k = 0; k < (x[0]).length; k++) {
/* 476 */             x[j][k] = temp[j - move][k];
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 483 */       if (dimension == 2) {
/* 484 */         for (i = 0; i < x.length; i++) {
/* 485 */           FFTShift1D(x[i], Direction.Forward);
/*     */         }
/*     */       }
/*     */     } else {
/*     */       
/* 490 */       if (dimension == 1) {
/* 491 */         int move = temp.length / 2; int j;
/* 492 */         for (j = 0; j < x.length - move; j++) {
/* 493 */           for (int k = 0; k < (x[0]).length; k++) {
/* 494 */             x[j][k] = temp[move + j][k];
/*     */           }
/*     */         } 
/* 497 */         for (j = 0; j < move; j++) {
/* 498 */           for (int k = 0; k < (x[0]).length; k++) {
/* 499 */             x[x.length - move + j][k] = temp[j][k];
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 504 */       if (dimension == 2) {
/* 505 */         for (i = 0; i < x.length; i++) {
/* 506 */           FFTShift1D(x[i], Direction.Backward);
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
/* 518 */     FFTShift2D(x, direction, 1);
/* 519 */     FFTShift2D(x, direction, 2);
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
/* 531 */     Object[][] temp = new Object[x.length][(x[0]).length]; int i;
/* 532 */     for (i = 0; i < x.length; i++) {
/* 533 */       for (int j = 0; j < (x[0]).length; j++) {
/* 534 */         temp[i][j] = x[i][j];
/*     */       }
/*     */     } 
/*     */     
/* 538 */     if (direction == Direction.Forward) {
/*     */       
/* 540 */       if (dimension == 1) {
/* 541 */         int move = temp.length / 2; int j;
/* 542 */         for (j = 0; j < move; j++) {
/* 543 */           for (int k = 0; k < (x[0]).length; k++) {
/* 544 */             x[j][k] = temp[temp.length - move + j][k];
/*     */           }
/*     */         } 
/*     */         
/* 548 */         for (j = move; j < x.length; j++) {
/* 549 */           for (int k = 0; k < (x[0]).length; k++) {
/* 550 */             x[j][k] = temp[j - move][k];
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 557 */       if (dimension == 2) {
/* 558 */         for (i = 0; i < x.length; i++) {
/* 559 */           FFTShift1D(x[i], Direction.Forward);
/*     */         }
/*     */       }
/*     */     } else {
/*     */       
/* 564 */       if (dimension == 1) {
/* 565 */         int move = temp.length / 2; int j;
/* 566 */         for (j = 0; j < x.length - move; j++) {
/* 567 */           for (int k = 0; k < (x[0]).length; k++) {
/* 568 */             x[j][k] = temp[move + j][k];
/*     */           }
/*     */         } 
/* 571 */         for (j = 0; j < move; j++) {
/* 572 */           for (int k = 0; k < (x[0]).length; k++) {
/* 573 */             x[x.length - move + j][k] = temp[j][k];
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 578 */       if (dimension == 2)
/* 579 */         for (i = 0; i < x.length; i++)
/* 580 */           FFTShift1D(x[i], Direction.Backward);  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Transforms/FourierTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */