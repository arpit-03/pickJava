/*     */ package org.apache.commons.math3.linear;
/*     */ 
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
/*     */ class BiDiagonalTransformer
/*     */ {
/*     */   private final double[][] householderVectors;
/*     */   private final double[] main;
/*     */   private final double[] secondary;
/*     */   private RealMatrix cachedU;
/*     */   private RealMatrix cachedB;
/*     */   private RealMatrix cachedV;
/*     */   
/*     */   BiDiagonalTransformer(RealMatrix matrix) {
/*  63 */     int m = matrix.getRowDimension();
/*  64 */     int n = matrix.getColumnDimension();
/*  65 */     int p = FastMath.min(m, n);
/*  66 */     this.householderVectors = matrix.getData();
/*  67 */     this.main = new double[p];
/*  68 */     this.secondary = new double[p - 1];
/*  69 */     this.cachedU = null;
/*  70 */     this.cachedB = null;
/*  71 */     this.cachedV = null;
/*     */ 
/*     */     
/*  74 */     if (m >= n) {
/*  75 */       transformToUpperBiDiagonal();
/*     */     } else {
/*  77 */       transformToLowerBiDiagonal();
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
/*     */   public RealMatrix getU() {
/*  89 */     if (this.cachedU == null) {
/*     */       
/*  91 */       int m = this.householderVectors.length;
/*  92 */       int n = (this.householderVectors[0]).length;
/*  93 */       int p = this.main.length;
/*  94 */       int diagOffset = (m >= n) ? 0 : 1;
/*  95 */       double[] diagonal = (m >= n) ? this.main : this.secondary;
/*  96 */       double[][] ua = new double[m][m];
/*     */       
/*     */       int k;
/*  99 */       for (k = m - 1; k >= p; k--) {
/* 100 */         ua[k][k] = 1.0D;
/*     */       }
/*     */ 
/*     */       
/* 104 */       for (k = p - 1; k >= diagOffset; k--) {
/* 105 */         double[] hK = this.householderVectors[k];
/* 106 */         ua[k][k] = 1.0D;
/* 107 */         if (hK[k - diagOffset] != 0.0D) {
/* 108 */           for (int j = k; j < m; j++) {
/* 109 */             double alpha = 0.0D; int i;
/* 110 */             for (i = k; i < m; i++) {
/* 111 */               alpha -= ua[i][j] * this.householderVectors[i][k - diagOffset];
/*     */             }
/* 113 */             alpha /= diagonal[k - diagOffset] * hK[k - diagOffset];
/*     */             
/* 115 */             for (i = k; i < m; i++) {
/* 116 */               ua[i][j] = ua[i][j] + -alpha * this.householderVectors[i][k - diagOffset];
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/* 121 */       if (diagOffset > 0) {
/* 122 */         ua[0][0] = 1.0D;
/*     */       }
/* 124 */       this.cachedU = MatrixUtils.createRealMatrix(ua);
/*     */     } 
/*     */ 
/*     */     
/* 128 */     return this.cachedU;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getB() {
/* 138 */     if (this.cachedB == null) {
/*     */       
/* 140 */       int m = this.householderVectors.length;
/* 141 */       int n = (this.householderVectors[0]).length;
/* 142 */       double[][] ba = new double[m][n];
/* 143 */       for (int i = 0; i < this.main.length; i++) {
/* 144 */         ba[i][i] = this.main[i];
/* 145 */         if (m < n) {
/* 146 */           if (i > 0) {
/* 147 */             ba[i][i - 1] = this.secondary[i - 1];
/*     */           }
/*     */         }
/* 150 */         else if (i < this.main.length - 1) {
/* 151 */           ba[i][i + 1] = this.secondary[i];
/*     */         } 
/*     */       } 
/*     */       
/* 155 */       this.cachedB = MatrixUtils.createRealMatrix(ba);
/*     */     } 
/*     */ 
/*     */     
/* 159 */     return this.cachedB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getV() {
/* 170 */     if (this.cachedV == null) {
/*     */       
/* 172 */       int m = this.householderVectors.length;
/* 173 */       int n = (this.householderVectors[0]).length;
/* 174 */       int p = this.main.length;
/* 175 */       int diagOffset = (m >= n) ? 1 : 0;
/* 176 */       double[] diagonal = (m >= n) ? this.secondary : this.main;
/* 177 */       double[][] va = new double[n][n];
/*     */       
/*     */       int k;
/* 180 */       for (k = n - 1; k >= p; k--) {
/* 181 */         va[k][k] = 1.0D;
/*     */       }
/*     */ 
/*     */       
/* 185 */       for (k = p - 1; k >= diagOffset; k--) {
/* 186 */         double[] hK = this.householderVectors[k - diagOffset];
/* 187 */         va[k][k] = 1.0D;
/* 188 */         if (hK[k] != 0.0D) {
/* 189 */           for (int j = k; j < n; j++) {
/* 190 */             double beta = 0.0D; int i;
/* 191 */             for (i = k; i < n; i++) {
/* 192 */               beta -= va[i][j] * hK[i];
/*     */             }
/* 194 */             beta /= diagonal[k - diagOffset] * hK[k];
/*     */             
/* 196 */             for (i = k; i < n; i++) {
/* 197 */               va[i][j] = va[i][j] + -beta * hK[i];
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/* 202 */       if (diagOffset > 0) {
/* 203 */         va[0][0] = 1.0D;
/*     */       }
/* 205 */       this.cachedV = MatrixUtils.createRealMatrix(va);
/*     */     } 
/*     */ 
/*     */     
/* 209 */     return this.cachedV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double[][] getHouseholderVectorsRef() {
/* 220 */     return this.householderVectors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double[] getMainDiagonalRef() {
/* 230 */     return this.main;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double[] getSecondaryDiagonalRef() {
/* 240 */     return this.secondary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isUpperBiDiagonal() {
/* 248 */     return (this.householderVectors.length >= (this.householderVectors[0]).length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transformToUpperBiDiagonal() {
/* 258 */     int m = this.householderVectors.length;
/* 259 */     int n = (this.householderVectors[0]).length;
/* 260 */     for (int k = 0; k < n; k++) {
/*     */ 
/*     */       
/* 263 */       double xNormSqr = 0.0D;
/* 264 */       for (int i = k; i < m; i++) {
/* 265 */         double c = this.householderVectors[i][k];
/* 266 */         xNormSqr += c * c;
/*     */       } 
/* 268 */       double[] hK = this.householderVectors[k];
/* 269 */       double a = (hK[k] > 0.0D) ? -FastMath.sqrt(xNormSqr) : FastMath.sqrt(xNormSqr);
/* 270 */       this.main[k] = a;
/* 271 */       if (a != 0.0D) {
/* 272 */         hK[k] = hK[k] - a;
/* 273 */         for (int j = k + 1; j < n; j++) {
/* 274 */           double alpha = 0.0D; int i1;
/* 275 */           for (i1 = k; i1 < m; i1++) {
/* 276 */             double[] hI = this.householderVectors[i1];
/* 277 */             alpha -= hI[j] * hI[k];
/*     */           } 
/* 279 */           alpha /= a * this.householderVectors[k][k];
/* 280 */           for (i1 = k; i1 < m; i1++) {
/* 281 */             double[] hI = this.householderVectors[i1];
/* 282 */             hI[j] = hI[j] - alpha * hI[k];
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 287 */       if (k < n - 1) {
/*     */         
/* 289 */         xNormSqr = 0.0D;
/* 290 */         for (int j = k + 1; j < n; j++) {
/* 291 */           double c = hK[j];
/* 292 */           xNormSqr += c * c;
/*     */         } 
/* 294 */         double b = (hK[k + 1] > 0.0D) ? -FastMath.sqrt(xNormSqr) : FastMath.sqrt(xNormSqr);
/* 295 */         this.secondary[k] = b;
/* 296 */         if (b != 0.0D) {
/* 297 */           hK[k + 1] = hK[k + 1] - b;
/* 298 */           for (int i1 = k + 1; i1 < m; i1++) {
/* 299 */             double[] hI = this.householderVectors[i1];
/* 300 */             double beta = 0.0D; int i2;
/* 301 */             for (i2 = k + 1; i2 < n; i2++) {
/* 302 */               beta -= hI[i2] * hK[i2];
/*     */             }
/* 304 */             beta /= b * hK[k + 1];
/* 305 */             for (i2 = k + 1; i2 < n; i2++) {
/* 306 */               hI[i2] = hI[i2] - beta * hK[i2];
/*     */             }
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
/*     */ 
/*     */   
/*     */   private void transformToLowerBiDiagonal() {
/* 322 */     int m = this.householderVectors.length;
/* 323 */     int n = (this.householderVectors[0]).length;
/* 324 */     for (int k = 0; k < m; k++) {
/*     */ 
/*     */       
/* 327 */       double[] hK = this.householderVectors[k];
/* 328 */       double xNormSqr = 0.0D;
/* 329 */       for (int j = k; j < n; j++) {
/* 330 */         double c = hK[j];
/* 331 */         xNormSqr += c * c;
/*     */       } 
/* 333 */       double a = (hK[k] > 0.0D) ? -FastMath.sqrt(xNormSqr) : FastMath.sqrt(xNormSqr);
/* 334 */       this.main[k] = a;
/* 335 */       if (a != 0.0D) {
/* 336 */         hK[k] = hK[k] - a;
/* 337 */         for (int i = k + 1; i < m; i++) {
/* 338 */           double[] hI = this.householderVectors[i];
/* 339 */           double alpha = 0.0D; int i1;
/* 340 */           for (i1 = k; i1 < n; i1++) {
/* 341 */             alpha -= hI[i1] * hK[i1];
/*     */           }
/* 343 */           alpha /= a * this.householderVectors[k][k];
/* 344 */           for (i1 = k; i1 < n; i1++) {
/* 345 */             hI[i1] = hI[i1] - alpha * hK[i1];
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 350 */       if (k < m - 1) {
/*     */         
/* 352 */         double[] hKp1 = this.householderVectors[k + 1];
/* 353 */         xNormSqr = 0.0D;
/* 354 */         for (int i = k + 1; i < m; i++) {
/* 355 */           double c = this.householderVectors[i][k];
/* 356 */           xNormSqr += c * c;
/*     */         } 
/* 358 */         double b = (hKp1[k] > 0.0D) ? -FastMath.sqrt(xNormSqr) : FastMath.sqrt(xNormSqr);
/* 359 */         this.secondary[k] = b;
/* 360 */         if (b != 0.0D) {
/* 361 */           hKp1[k] = hKp1[k] - b;
/* 362 */           for (int i1 = k + 1; i1 < n; i1++) {
/* 363 */             double beta = 0.0D; int i2;
/* 364 */             for (i2 = k + 1; i2 < m; i2++) {
/* 365 */               double[] hI = this.householderVectors[i2];
/* 366 */               beta -= hI[i1] * hI[k];
/*     */             } 
/* 368 */             beta /= b * hKp1[k];
/* 369 */             for (i2 = k + 1; i2 < m; i2++) {
/* 370 */               double[] hI = this.householderVectors[i2];
/* 371 */               hI[i1] = hI[i1] - beta * hI[k];
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/BiDiagonalTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */