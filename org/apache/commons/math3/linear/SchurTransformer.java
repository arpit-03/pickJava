/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SchurTransformer
/*     */ {
/*     */   private static final int MAX_ITERATIONS = 100;
/*     */   private final double[][] matrixP;
/*     */   private final double[][] matrixT;
/*     */   private RealMatrix cachedP;
/*     */   private RealMatrix cachedT;
/*     */   private RealMatrix cachedPt;
/*  60 */   private final double epsilon = Precision.EPSILON;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SchurTransformer(RealMatrix matrix) {
/*  69 */     if (!matrix.isSquare()) {
/*  70 */       throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
/*     */     }
/*     */ 
/*     */     
/*  74 */     HessenbergTransformer transformer = new HessenbergTransformer(matrix);
/*  75 */     this.matrixT = transformer.getH().getData();
/*  76 */     this.matrixP = transformer.getP().getData();
/*  77 */     this.cachedT = null;
/*  78 */     this.cachedP = null;
/*  79 */     this.cachedPt = null;
/*     */ 
/*     */     
/*  82 */     transform();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getP() {
/*  92 */     if (this.cachedP == null) {
/*  93 */       this.cachedP = MatrixUtils.createRealMatrix(this.matrixP);
/*     */     }
/*  95 */     return this.cachedP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getPT() {
/* 105 */     if (this.cachedPt == null) {
/* 106 */       this.cachedPt = getP().transpose();
/*     */     }
/*     */ 
/*     */     
/* 110 */     return this.cachedPt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getT() {
/* 119 */     if (this.cachedT == null) {
/* 120 */       this.cachedT = MatrixUtils.createRealMatrix(this.matrixT);
/*     */     }
/*     */ 
/*     */     
/* 124 */     return this.cachedT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transform() {
/* 132 */     int n = this.matrixT.length;
/*     */ 
/*     */     
/* 135 */     double norm = getNorm();
/*     */ 
/*     */     
/* 138 */     ShiftInfo shift = new ShiftInfo();
/*     */ 
/*     */     
/* 141 */     int iteration = 0;
/* 142 */     int iu = n - 1;
/* 143 */     while (iu >= 0) {
/*     */ 
/*     */       
/* 146 */       int il = findSmallSubDiagonalElement(iu, norm);
/*     */ 
/*     */       
/* 149 */       if (il == iu) {
/*     */         
/* 151 */         this.matrixT[iu][iu] = this.matrixT[iu][iu] + shift.exShift;
/* 152 */         iu--;
/* 153 */         iteration = 0; continue;
/* 154 */       }  if (il == iu - 1) {
/*     */         
/* 156 */         double p = (this.matrixT[iu - 1][iu - 1] - this.matrixT[iu][iu]) / 2.0D;
/* 157 */         double q = p * p + this.matrixT[iu][iu - 1] * this.matrixT[iu - 1][iu];
/* 158 */         this.matrixT[iu][iu] = this.matrixT[iu][iu] + shift.exShift;
/* 159 */         this.matrixT[iu - 1][iu - 1] = this.matrixT[iu - 1][iu - 1] + shift.exShift;
/*     */         
/* 161 */         if (q >= 0.0D) {
/* 162 */           double z = FastMath.sqrt(FastMath.abs(q));
/* 163 */           if (p >= 0.0D) {
/* 164 */             z = p + z;
/*     */           } else {
/* 166 */             z = p - z;
/*     */           } 
/* 168 */           double x = this.matrixT[iu][iu - 1];
/* 169 */           double s = FastMath.abs(x) + FastMath.abs(z);
/* 170 */           p = x / s;
/* 171 */           q = z / s;
/* 172 */           double r = FastMath.sqrt(p * p + q * q);
/* 173 */           p /= r;
/* 174 */           q /= r;
/*     */ 
/*     */           
/* 177 */           for (int j = iu - 1; j < n; j++) {
/* 178 */             z = this.matrixT[iu - 1][j];
/* 179 */             this.matrixT[iu - 1][j] = q * z + p * this.matrixT[iu][j];
/* 180 */             this.matrixT[iu][j] = q * this.matrixT[iu][j] - p * z;
/*     */           } 
/*     */           
/*     */           int i;
/* 184 */           for (i = 0; i <= iu; i++) {
/* 185 */             z = this.matrixT[i][iu - 1];
/* 186 */             this.matrixT[i][iu - 1] = q * z + p * this.matrixT[i][iu];
/* 187 */             this.matrixT[i][iu] = q * this.matrixT[i][iu] - p * z;
/*     */           } 
/*     */ 
/*     */           
/* 191 */           for (i = 0; i <= n - 1; i++) {
/* 192 */             z = this.matrixP[i][iu - 1];
/* 193 */             this.matrixP[i][iu - 1] = q * z + p * this.matrixP[i][iu];
/* 194 */             this.matrixP[i][iu] = q * this.matrixP[i][iu] - p * z;
/*     */           } 
/*     */         } 
/* 197 */         iu -= 2;
/* 198 */         iteration = 0;
/*     */         continue;
/*     */       } 
/* 201 */       computeShift(il, iu, iteration, shift);
/*     */ 
/*     */       
/* 204 */       if (++iteration > 100) {
/* 205 */         throw new MaxCountExceededException(LocalizedFormats.CONVERGENCE_FAILED, Integer.valueOf(100), new Object[0]);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 210 */       double[] hVec = new double[3];
/*     */       
/* 212 */       int im = initQRStep(il, iu, shift, hVec);
/* 213 */       performDoubleQRStep(il, im, iu, shift, hVec);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getNorm() {
/* 224 */     double norm = 0.0D;
/* 225 */     for (int i = 0; i < this.matrixT.length; i++) {
/*     */       
/* 227 */       for (int j = FastMath.max(i - 1, 0); j < this.matrixT.length; j++) {
/* 228 */         norm += FastMath.abs(this.matrixT[i][j]);
/*     */       }
/*     */     } 
/* 231 */     return norm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int findSmallSubDiagonalElement(int startIdx, double norm) {
/* 242 */     int l = startIdx;
/* 243 */     while (l > 0) {
/* 244 */       double s = FastMath.abs(this.matrixT[l - 1][l - 1]) + FastMath.abs(this.matrixT[l][l]);
/* 245 */       if (s == 0.0D) {
/* 246 */         s = norm;
/*     */       }
/* 248 */       if (FastMath.abs(this.matrixT[l][l - 1]) < this.epsilon * s) {
/*     */         break;
/*     */       }
/* 251 */       l--;
/*     */     } 
/* 253 */     return l;
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
/*     */   private void computeShift(int l, int idx, int iteration, ShiftInfo shift) {
/* 266 */     shift.x = this.matrixT[idx][idx];
/* 267 */     shift.y = shift.w = 0.0D;
/* 268 */     if (l < idx) {
/* 269 */       shift.y = this.matrixT[idx - 1][idx - 1];
/* 270 */       shift.w = this.matrixT[idx][idx - 1] * this.matrixT[idx - 1][idx];
/*     */     } 
/*     */ 
/*     */     
/* 274 */     if (iteration == 10) {
/* 275 */       shift.exShift += shift.x;
/* 276 */       for (int i = 0; i <= idx; i++) {
/* 277 */         this.matrixT[i][i] = this.matrixT[i][i] - shift.x;
/*     */       }
/* 279 */       double s = FastMath.abs(this.matrixT[idx][idx - 1]) + FastMath.abs(this.matrixT[idx - 1][idx - 2]);
/* 280 */       shift.x = 0.75D * s;
/* 281 */       shift.y = 0.75D * s;
/* 282 */       shift.w = -0.4375D * s * s;
/*     */     } 
/*     */ 
/*     */     
/* 286 */     if (iteration == 30) {
/* 287 */       double s = (shift.y - shift.x) / 2.0D;
/* 288 */       s = s * s + shift.w;
/* 289 */       if (s > 0.0D) {
/* 290 */         s = FastMath.sqrt(s);
/* 291 */         if (shift.y < shift.x) {
/* 292 */           s = -s;
/*     */         }
/* 294 */         s = shift.x - shift.w / ((shift.y - shift.x) / 2.0D + s);
/* 295 */         for (int i = 0; i <= idx; i++) {
/* 296 */           this.matrixT[i][i] = this.matrixT[i][i] - s;
/*     */         }
/* 298 */         shift.exShift += s;
/* 299 */         shift.x = shift.y = shift.w = 0.964D;
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
/*     */   private int initQRStep(int il, int iu, ShiftInfo shift, double[] hVec) {
/* 315 */     int im = iu - 2;
/* 316 */     while (im >= il) {
/* 317 */       double z = this.matrixT[im][im];
/* 318 */       double r = shift.x - z;
/* 319 */       double s = shift.y - z;
/* 320 */       hVec[0] = (r * s - shift.w) / this.matrixT[im + 1][im] + this.matrixT[im][im + 1];
/* 321 */       hVec[1] = this.matrixT[im + 1][im + 1] - z - r - s;
/* 322 */       hVec[2] = this.matrixT[im + 2][im + 1];
/*     */       
/* 324 */       if (im == il) {
/*     */         break;
/*     */       }
/*     */       
/* 328 */       double lhs = FastMath.abs(this.matrixT[im][im - 1]) * (FastMath.abs(hVec[1]) + FastMath.abs(hVec[2]));
/* 329 */       double rhs = FastMath.abs(hVec[0]) * (FastMath.abs(this.matrixT[im - 1][im - 1]) + FastMath.abs(z) + FastMath.abs(this.matrixT[im + 1][im + 1]));
/*     */ 
/*     */ 
/*     */       
/* 333 */       if (lhs < this.epsilon * rhs) {
/*     */         break;
/*     */       }
/* 336 */       im--;
/*     */     } 
/*     */     
/* 339 */     return im;
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
/*     */   private void performDoubleQRStep(int il, int im, int iu, ShiftInfo shift, double[] hVec) {
/* 354 */     int n = this.matrixT.length;
/* 355 */     double p = hVec[0];
/* 356 */     double q = hVec[1];
/* 357 */     double r = hVec[2];
/*     */     
/* 359 */     for (int k = im; k <= iu - 1; k++) {
/* 360 */       boolean notlast = (k != iu - 1);
/* 361 */       if (k != im) {
/* 362 */         p = this.matrixT[k][k - 1];
/* 363 */         q = this.matrixT[k + 1][k - 1];
/* 364 */         r = notlast ? this.matrixT[k + 2][k - 1] : 0.0D;
/* 365 */         shift.x = FastMath.abs(p) + FastMath.abs(q) + FastMath.abs(r);
/* 366 */         if (Precision.equals(shift.x, 0.0D, this.epsilon)) {
/*     */           continue;
/*     */         }
/* 369 */         p /= shift.x;
/* 370 */         q /= shift.x;
/* 371 */         r /= shift.x;
/*     */       } 
/* 373 */       double s = FastMath.sqrt(p * p + q * q + r * r);
/* 374 */       if (p < 0.0D) {
/* 375 */         s = -s;
/*     */       }
/* 377 */       if (s != 0.0D) {
/* 378 */         if (k != im) {
/* 379 */           this.matrixT[k][k - 1] = -s * shift.x;
/* 380 */         } else if (il != im) {
/* 381 */           this.matrixT[k][k - 1] = -this.matrixT[k][k - 1];
/*     */         } 
/* 383 */         p += s;
/* 384 */         shift.x = p / s;
/* 385 */         shift.y = q / s;
/* 386 */         double z = r / s;
/* 387 */         q /= p;
/* 388 */         r /= p;
/*     */ 
/*     */         
/* 391 */         for (int j = k; j < n; j++) {
/* 392 */           p = this.matrixT[k][j] + q * this.matrixT[k + 1][j];
/* 393 */           if (notlast) {
/* 394 */             p += r * this.matrixT[k + 2][j];
/* 395 */             this.matrixT[k + 2][j] = this.matrixT[k + 2][j] - p * z;
/*     */           } 
/* 397 */           this.matrixT[k][j] = this.matrixT[k][j] - p * shift.x;
/* 398 */           this.matrixT[k + 1][j] = this.matrixT[k + 1][j] - p * shift.y;
/*     */         } 
/*     */ 
/*     */         
/* 402 */         for (int m = 0; m <= FastMath.min(iu, k + 3); m++) {
/* 403 */           p = shift.x * this.matrixT[m][k] + shift.y * this.matrixT[m][k + 1];
/* 404 */           if (notlast) {
/* 405 */             p += z * this.matrixT[m][k + 2];
/* 406 */             this.matrixT[m][k + 2] = this.matrixT[m][k + 2] - p * r;
/*     */           } 
/* 408 */           this.matrixT[m][k] = this.matrixT[m][k] - p;
/* 409 */           this.matrixT[m][k + 1] = this.matrixT[m][k + 1] - p * q;
/*     */         } 
/*     */ 
/*     */         
/* 413 */         int high = this.matrixT.length - 1;
/* 414 */         for (int i1 = 0; i1 <= high; i1++) {
/* 415 */           p = shift.x * this.matrixP[i1][k] + shift.y * this.matrixP[i1][k + 1];
/* 416 */           if (notlast) {
/* 417 */             p += z * this.matrixP[i1][k + 2];
/* 418 */             this.matrixP[i1][k + 2] = this.matrixP[i1][k + 2] - p * r;
/*     */           } 
/* 420 */           this.matrixP[i1][k] = this.matrixP[i1][k] - p;
/* 421 */           this.matrixP[i1][k + 1] = this.matrixP[i1][k + 1] - p * q;
/*     */         } 
/*     */       } 
/*     */       
/*     */       continue;
/*     */     } 
/* 427 */     for (int i = im + 2; i <= iu; i++) {
/* 428 */       this.matrixT[i][i - 2] = 0.0D;
/* 429 */       if (i > im + 2)
/* 430 */         this.matrixT[i][i - 3] = 0.0D; 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class ShiftInfo {
/*     */     double x;
/*     */     double y;
/*     */     double w;
/*     */     double exShift;
/*     */     
/*     */     private ShiftInfo() {}
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/SchurTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */