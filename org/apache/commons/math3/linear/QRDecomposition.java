/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QRDecomposition
/*     */ {
/*     */   private double[][] qrt;
/*     */   private double[] rDiag;
/*     */   private RealMatrix cachedQ;
/*     */   private RealMatrix cachedQT;
/*     */   private RealMatrix cachedR;
/*     */   private RealMatrix cachedH;
/*     */   private final double threshold;
/*     */   
/*     */   public QRDecomposition(RealMatrix matrix) {
/*  80 */     this(matrix, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QRDecomposition(RealMatrix matrix, double threshold) {
/*  91 */     this.threshold = threshold;
/*     */     
/*  93 */     int m = matrix.getRowDimension();
/*  94 */     int n = matrix.getColumnDimension();
/*  95 */     this.qrt = matrix.transpose().getData();
/*  96 */     this.rDiag = new double[FastMath.min(m, n)];
/*  97 */     this.cachedQ = null;
/*  98 */     this.cachedQT = null;
/*  99 */     this.cachedR = null;
/* 100 */     this.cachedH = null;
/*     */     
/* 102 */     decompose(this.qrt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decompose(double[][] matrix) {
/* 111 */     for (int minor = 0; minor < FastMath.min(matrix.length, (matrix[0]).length); minor++) {
/* 112 */       performHouseholderReflection(minor, matrix);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void performHouseholderReflection(int minor, double[][] matrix) {
/* 123 */     double[] qrtMinor = matrix[minor];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     double xNormSqr = 0.0D;
/* 133 */     for (int row = minor; row < qrtMinor.length; row++) {
/* 134 */       double c = qrtMinor[row];
/* 135 */       xNormSqr += c * c;
/*     */     } 
/* 137 */     double a = (qrtMinor[minor] > 0.0D) ? -FastMath.sqrt(xNormSqr) : FastMath.sqrt(xNormSqr);
/* 138 */     this.rDiag[minor] = a;
/*     */     
/* 140 */     if (a != 0.0D) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 150 */       qrtMinor[minor] = qrtMinor[minor] - a;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 164 */       for (int col = minor + 1; col < matrix.length; col++) {
/* 165 */         double[] qrtCol = matrix[col];
/* 166 */         double alpha = 0.0D; int i;
/* 167 */         for (i = minor; i < qrtCol.length; i++) {
/* 168 */           alpha -= qrtCol[i] * qrtMinor[i];
/*     */         }
/* 170 */         alpha /= a * qrtMinor[minor];
/*     */ 
/*     */         
/* 173 */         for (i = minor; i < qrtCol.length; i++) {
/* 174 */           qrtCol[i] = qrtCol[i] - alpha * qrtMinor[i];
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
/*     */   public RealMatrix getR() {
/* 188 */     if (this.cachedR == null) {
/*     */ 
/*     */       
/* 191 */       int n = this.qrt.length;
/* 192 */       int m = (this.qrt[0]).length;
/* 193 */       double[][] ra = new double[m][n];
/*     */       
/* 195 */       for (int row = FastMath.min(m, n) - 1; row >= 0; row--) {
/* 196 */         ra[row][row] = this.rDiag[row];
/* 197 */         for (int col = row + 1; col < n; col++) {
/* 198 */           ra[row][col] = this.qrt[col][row];
/*     */         }
/*     */       } 
/* 201 */       this.cachedR = MatrixUtils.createRealMatrix(ra);
/*     */     } 
/*     */ 
/*     */     
/* 205 */     return this.cachedR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getQ() {
/* 214 */     if (this.cachedQ == null) {
/* 215 */       this.cachedQ = getQT().transpose();
/*     */     }
/* 217 */     return this.cachedQ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getQT() {
/* 226 */     if (this.cachedQT == null) {
/*     */ 
/*     */       
/* 229 */       int n = this.qrt.length;
/* 230 */       int m = (this.qrt[0]).length;
/* 231 */       double[][] qta = new double[m][m];
/*     */ 
/*     */ 
/*     */       
/*     */       int minor;
/*     */ 
/*     */       
/* 238 */       for (minor = m - 1; minor >= FastMath.min(m, n); minor--) {
/* 239 */         qta[minor][minor] = 1.0D;
/*     */       }
/*     */       
/* 242 */       for (minor = FastMath.min(m, n) - 1; minor >= 0; minor--) {
/* 243 */         double[] qrtMinor = this.qrt[minor];
/* 244 */         qta[minor][minor] = 1.0D;
/* 245 */         if (qrtMinor[minor] != 0.0D) {
/* 246 */           for (int col = minor; col < m; col++) {
/* 247 */             double alpha = 0.0D; int row;
/* 248 */             for (row = minor; row < m; row++) {
/* 249 */               alpha -= qta[col][row] * qrtMinor[row];
/*     */             }
/* 251 */             alpha /= this.rDiag[minor] * qrtMinor[minor];
/*     */             
/* 253 */             for (row = minor; row < m; row++) {
/* 254 */               qta[col][row] = qta[col][row] + -alpha * qrtMinor[row];
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/* 259 */       this.cachedQT = MatrixUtils.createRealMatrix(qta);
/*     */     } 
/*     */ 
/*     */     
/* 263 */     return this.cachedQT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getH() {
/* 274 */     if (this.cachedH == null) {
/*     */       
/* 276 */       int n = this.qrt.length;
/* 277 */       int m = (this.qrt[0]).length;
/* 278 */       double[][] ha = new double[m][n];
/* 279 */       for (int i = 0; i < m; i++) {
/* 280 */         for (int j = 0; j < FastMath.min(i + 1, n); j++) {
/* 281 */           ha[i][j] = this.qrt[j][i] / -this.rDiag[j];
/*     */         }
/*     */       } 
/* 284 */       this.cachedH = MatrixUtils.createRealMatrix(ha);
/*     */     } 
/*     */ 
/*     */     
/* 288 */     return this.cachedH;
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
/*     */   public DecompositionSolver getSolver() {
/* 304 */     return new Solver(this.qrt, this.rDiag, this.threshold);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Solver
/*     */     implements DecompositionSolver
/*     */   {
/*     */     private final double[][] qrt;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double[] rDiag;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double threshold;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Solver(double[][] qrt, double[] rDiag, double threshold) {
/* 331 */       this.qrt = qrt;
/* 332 */       this.rDiag = rDiag;
/* 333 */       this.threshold = threshold;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isNonSingular() {
/* 338 */       for (double diag : this.rDiag) {
/* 339 */         if (FastMath.abs(diag) <= this.threshold) {
/* 340 */           return false;
/*     */         }
/*     */       } 
/* 343 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public RealVector solve(RealVector b) {
/* 348 */       int n = this.qrt.length;
/* 349 */       int m = (this.qrt[0]).length;
/* 350 */       if (b.getDimension() != m) {
/* 351 */         throw new DimensionMismatchException(b.getDimension(), m);
/*     */       }
/* 353 */       if (!isNonSingular()) {
/* 354 */         throw new SingularMatrixException();
/*     */       }
/*     */       
/* 357 */       double[] x = new double[n];
/* 358 */       double[] y = b.toArray();
/*     */ 
/*     */       
/* 361 */       for (int minor = 0; minor < FastMath.min(m, n); minor++) {
/*     */         
/* 363 */         double[] qrtMinor = this.qrt[minor];
/* 364 */         double dotProduct = 0.0D; int i;
/* 365 */         for (i = minor; i < m; i++) {
/* 366 */           dotProduct += y[i] * qrtMinor[i];
/*     */         }
/* 368 */         dotProduct /= this.rDiag[minor] * qrtMinor[minor];
/*     */         
/* 370 */         for (i = minor; i < m; i++) {
/* 371 */           y[i] = y[i] + dotProduct * qrtMinor[i];
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 376 */       for (int row = this.rDiag.length - 1; row >= 0; row--) {
/* 377 */         y[row] = y[row] / this.rDiag[row];
/* 378 */         double yRow = y[row];
/* 379 */         double[] qrtRow = this.qrt[row];
/* 380 */         x[row] = yRow;
/* 381 */         for (int i = 0; i < row; i++) {
/* 382 */           y[i] = y[i] - yRow * qrtRow[i];
/*     */         }
/*     */       } 
/*     */       
/* 386 */       return new ArrayRealVector(x, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public RealMatrix solve(RealMatrix b) {
/* 391 */       int n = this.qrt.length;
/* 392 */       int m = (this.qrt[0]).length;
/* 393 */       if (b.getRowDimension() != m) {
/* 394 */         throw new DimensionMismatchException(b.getRowDimension(), m);
/*     */       }
/* 396 */       if (!isNonSingular()) {
/* 397 */         throw new SingularMatrixException();
/*     */       }
/*     */       
/* 400 */       int columns = b.getColumnDimension();
/* 401 */       int blockSize = 52;
/* 402 */       int cBlocks = (columns + 52 - 1) / 52;
/* 403 */       double[][] xBlocks = BlockRealMatrix.createBlocksLayout(n, columns);
/* 404 */       double[][] y = new double[b.getRowDimension()][52];
/* 405 */       double[] alpha = new double[52];
/*     */       
/* 407 */       for (int kBlock = 0; kBlock < cBlocks; kBlock++) {
/* 408 */         int kStart = kBlock * 52;
/* 409 */         int kEnd = FastMath.min(kStart + 52, columns);
/* 410 */         int kWidth = kEnd - kStart;
/*     */ 
/*     */         
/* 413 */         b.copySubMatrix(0, m - 1, kStart, kEnd - 1, y);
/*     */ 
/*     */         
/* 416 */         for (int minor = 0; minor < FastMath.min(m, n); minor++) {
/* 417 */           double[] qrtMinor = this.qrt[minor];
/* 418 */           double factor = 1.0D / this.rDiag[minor] * qrtMinor[minor];
/*     */           
/* 420 */           Arrays.fill(alpha, 0, kWidth, 0.0D);
/* 421 */           for (int i = minor; i < m; i++) {
/* 422 */             double d = qrtMinor[i];
/* 423 */             double[] yRow = y[i];
/* 424 */             for (int i1 = 0; i1 < kWidth; i1++) {
/* 425 */               alpha[i1] = alpha[i1] + d * yRow[i1];
/*     */             }
/*     */           } 
/* 428 */           for (int k = 0; k < kWidth; k++) {
/* 429 */             alpha[k] = alpha[k] * factor;
/*     */           }
/*     */           
/* 432 */           for (int row = minor; row < m; row++) {
/* 433 */             double d = qrtMinor[row];
/* 434 */             double[] yRow = y[row];
/* 435 */             for (int i1 = 0; i1 < kWidth; i1++) {
/* 436 */               yRow[i1] = yRow[i1] + alpha[i1] * d;
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 442 */         for (int j = this.rDiag.length - 1; j >= 0; j--) {
/* 443 */           int jBlock = j / 52;
/* 444 */           int jStart = jBlock * 52;
/* 445 */           double factor = 1.0D / this.rDiag[j];
/* 446 */           double[] yJ = y[j];
/* 447 */           double[] xBlock = xBlocks[jBlock * cBlocks + kBlock];
/* 448 */           int index = (j - jStart) * kWidth;
/* 449 */           for (int k = 0; k < kWidth; k++) {
/* 450 */             yJ[k] = yJ[k] * factor;
/* 451 */             xBlock[index++] = yJ[k];
/*     */           } 
/*     */           
/* 454 */           double[] qrtJ = this.qrt[j];
/* 455 */           for (int i = 0; i < j; i++) {
/* 456 */             double rIJ = qrtJ[i];
/* 457 */             double[] yI = y[i];
/* 458 */             for (int i1 = 0; i1 < kWidth; i1++) {
/* 459 */               yI[i1] = yI[i1] - yJ[i1] * rIJ;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 465 */       return new BlockRealMatrix(n, columns, xBlocks, false);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RealMatrix getInverse() {
/* 473 */       return solve(MatrixUtils.createRealIdentityMatrix((this.qrt[0]).length));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/QRDecomposition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */