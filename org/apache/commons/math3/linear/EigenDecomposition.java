/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import org.apache.commons.math3.complex.Complex;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EigenDecomposition
/*     */ {
/*     */   private static final double EPSILON = 1.0E-12D;
/*  80 */   private byte maxIter = 30;
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] main;
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] secondary;
/*     */ 
/*     */ 
/*     */   
/*     */   private TriDiagonalTransformer transformer;
/*     */ 
/*     */   
/*     */   private double[] realEigenvalues;
/*     */ 
/*     */   
/*     */   private double[] imagEigenvalues;
/*     */ 
/*     */   
/*     */   private ArrayRealVector[] eigenvectors;
/*     */ 
/*     */   
/*     */   private RealMatrix cachedV;
/*     */ 
/*     */   
/*     */   private RealMatrix cachedD;
/*     */ 
/*     */   
/*     */   private RealMatrix cachedVt;
/*     */ 
/*     */   
/*     */   private final boolean isSymmetric;
/*     */ 
/*     */ 
/*     */   
/*     */   public EigenDecomposition(RealMatrix matrix) throws MathArithmeticException {
/* 118 */     double symTol = (10 * matrix.getRowDimension() * matrix.getColumnDimension()) * Precision.EPSILON;
/* 119 */     this.isSymmetric = MatrixUtils.isSymmetric(matrix, symTol);
/* 120 */     if (this.isSymmetric) {
/* 121 */       transformToTridiagonal(matrix);
/* 122 */       findEigenVectors(this.transformer.getQ().getData());
/*     */     } else {
/* 124 */       SchurTransformer t = transformToSchur(matrix);
/* 125 */       findEigenVectorsFromSchur(t);
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
/*     */   
/*     */   @Deprecated
/*     */   public EigenDecomposition(RealMatrix matrix, double splitTolerance) throws MathArithmeticException {
/* 144 */     this(matrix);
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
/*     */   public EigenDecomposition(double[] main, double[] secondary) {
/* 157 */     this.isSymmetric = true;
/* 158 */     this.main = (double[])main.clone();
/* 159 */     this.secondary = (double[])secondary.clone();
/* 160 */     this.transformer = null;
/* 161 */     int size = main.length;
/* 162 */     double[][] z = new double[size][size];
/* 163 */     for (int i = 0; i < size; i++) {
/* 164 */       z[i][i] = 1.0D;
/*     */     }
/* 166 */     findEigenVectors(z);
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
/*     */   @Deprecated
/*     */   public EigenDecomposition(double[] main, double[] secondary, double splitTolerance) {
/* 183 */     this(main, secondary);
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
/*     */   public RealMatrix getV() {
/* 198 */     if (this.cachedV == null) {
/* 199 */       int m = this.eigenvectors.length;
/* 200 */       this.cachedV = MatrixUtils.createRealMatrix(m, m);
/* 201 */       for (int k = 0; k < m; k++) {
/* 202 */         this.cachedV.setColumnVector(k, this.eigenvectors[k]);
/*     */       }
/*     */     } 
/*     */     
/* 206 */     return this.cachedV;
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
/*     */   public RealMatrix getD() {
/* 222 */     if (this.cachedD == null) {
/*     */       
/* 224 */       this.cachedD = MatrixUtils.createRealDiagonalMatrix(this.realEigenvalues);
/*     */       
/* 226 */       for (int i = 0; i < this.imagEigenvalues.length; i++) {
/* 227 */         if (Precision.compareTo(this.imagEigenvalues[i], 0.0D, 1.0E-12D) > 0) {
/* 228 */           this.cachedD.setEntry(i, i + 1, this.imagEigenvalues[i]);
/* 229 */         } else if (Precision.compareTo(this.imagEigenvalues[i], 0.0D, 1.0E-12D) < 0) {
/* 230 */           this.cachedD.setEntry(i, i - 1, this.imagEigenvalues[i]);
/*     */         } 
/*     */       } 
/*     */     } 
/* 234 */     return this.cachedD;
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
/*     */   public RealMatrix getVT() {
/* 249 */     if (this.cachedVt == null) {
/* 250 */       int m = this.eigenvectors.length;
/* 251 */       this.cachedVt = MatrixUtils.createRealMatrix(m, m);
/* 252 */       for (int k = 0; k < m; k++) {
/* 253 */         this.cachedVt.setRowVector(k, this.eigenvectors[k]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 258 */     return this.cachedVt;
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
/*     */   public boolean hasComplexEigenvalues() {
/* 271 */     for (int i = 0; i < this.imagEigenvalues.length; i++) {
/* 272 */       if (!Precision.equals(this.imagEigenvalues[i], 0.0D, 1.0E-12D)) {
/* 273 */         return true;
/*     */       }
/*     */     } 
/* 276 */     return false;
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
/*     */   public double[] getRealEigenvalues() {
/* 289 */     return (double[])this.realEigenvalues.clone();
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
/*     */   public double getRealEigenvalue(int i) {
/* 305 */     return this.realEigenvalues[i];
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
/*     */   public double[] getImagEigenvalues() {
/* 320 */     return (double[])this.imagEigenvalues.clone();
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
/*     */   public double getImagEigenvalue(int i) {
/* 336 */     return this.imagEigenvalues[i];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector getEigenvector(int i) {
/* 347 */     return this.eigenvectors[i].copy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDeterminant() {
/* 356 */     double determinant = 1.0D;
/* 357 */     for (double lambda : this.realEigenvalues) {
/* 358 */       determinant *= lambda;
/*     */     }
/* 360 */     return determinant;
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
/*     */   public RealMatrix getSquareRoot() {
/* 374 */     if (!this.isSymmetric) {
/* 375 */       throw new MathUnsupportedOperationException();
/*     */     }
/*     */     
/* 378 */     double[] sqrtEigenValues = new double[this.realEigenvalues.length];
/* 379 */     for (int i = 0; i < this.realEigenvalues.length; i++) {
/* 380 */       double eigen = this.realEigenvalues[i];
/* 381 */       if (eigen <= 0.0D) {
/* 382 */         throw new MathUnsupportedOperationException();
/*     */       }
/* 384 */       sqrtEigenValues[i] = FastMath.sqrt(eigen);
/*     */     } 
/* 386 */     RealMatrix sqrtEigen = MatrixUtils.createRealDiagonalMatrix(sqrtEigenValues);
/* 387 */     RealMatrix v = getV();
/* 388 */     RealMatrix vT = getVT();
/*     */     
/* 390 */     return v.multiply(sqrtEigen).multiply(vT);
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
/*     */   public DecompositionSolver getSolver() {
/* 405 */     if (hasComplexEigenvalues()) {
/* 406 */       throw new MathUnsupportedOperationException();
/*     */     }
/* 408 */     return new Solver(this.realEigenvalues, this.imagEigenvalues, this.eigenvectors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Solver
/*     */     implements DecompositionSolver
/*     */   {
/*     */     private double[] realEigenvalues;
/*     */ 
/*     */ 
/*     */     
/*     */     private double[] imagEigenvalues;
/*     */ 
/*     */ 
/*     */     
/*     */     private final ArrayRealVector[] eigenvectors;
/*     */ 
/*     */ 
/*     */     
/*     */     private Solver(double[] realEigenvalues, double[] imagEigenvalues, ArrayRealVector[] eigenvectors) {
/* 430 */       this.realEigenvalues = realEigenvalues;
/* 431 */       this.imagEigenvalues = imagEigenvalues;
/* 432 */       this.eigenvectors = eigenvectors;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RealVector solve(RealVector b) {
/* 449 */       if (!isNonSingular()) {
/* 450 */         throw new SingularMatrixException();
/*     */       }
/*     */       
/* 453 */       int m = this.realEigenvalues.length;
/* 454 */       if (b.getDimension() != m) {
/* 455 */         throw new DimensionMismatchException(b.getDimension(), m);
/*     */       }
/*     */       
/* 458 */       double[] bp = new double[m];
/* 459 */       for (int i = 0; i < m; i++) {
/* 460 */         ArrayRealVector v = this.eigenvectors[i];
/* 461 */         double[] vData = v.getDataRef();
/* 462 */         double s = v.dotProduct(b) / this.realEigenvalues[i];
/* 463 */         for (int j = 0; j < m; j++) {
/* 464 */           bp[j] = bp[j] + s * vData[j];
/*     */         }
/*     */       } 
/*     */       
/* 468 */       return new ArrayRealVector(bp, false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public RealMatrix solve(RealMatrix b) {
/* 474 */       if (!isNonSingular()) {
/* 475 */         throw new SingularMatrixException();
/*     */       }
/*     */       
/* 478 */       int m = this.realEigenvalues.length;
/* 479 */       if (b.getRowDimension() != m) {
/* 480 */         throw new DimensionMismatchException(b.getRowDimension(), m);
/*     */       }
/*     */       
/* 483 */       int nColB = b.getColumnDimension();
/* 484 */       double[][] bp = new double[m][nColB];
/* 485 */       double[] tmpCol = new double[m];
/* 486 */       for (int k = 0; k < nColB; k++) {
/* 487 */         int i; for (i = 0; i < m; i++) {
/* 488 */           tmpCol[i] = b.getEntry(i, k);
/* 489 */           bp[i][k] = 0.0D;
/*     */         } 
/* 491 */         for (i = 0; i < m; i++) {
/* 492 */           ArrayRealVector v = this.eigenvectors[i];
/* 493 */           double[] vData = v.getDataRef();
/* 494 */           double s = 0.0D; int j;
/* 495 */           for (j = 0; j < m; j++) {
/* 496 */             s += v.getEntry(j) * tmpCol[j];
/*     */           }
/* 498 */           s /= this.realEigenvalues[i];
/* 499 */           for (j = 0; j < m; j++) {
/* 500 */             bp[j][k] = bp[j][k] + s * vData[j];
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 505 */       return new Array2DRowRealMatrix(bp, false);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isNonSingular() {
/* 515 */       double largestEigenvalueNorm = 0.0D;
/*     */       
/*     */       int i;
/* 518 */       for (i = 0; i < this.realEigenvalues.length; i++) {
/* 519 */         largestEigenvalueNorm = FastMath.max(largestEigenvalueNorm, eigenvalueNorm(i));
/*     */       }
/*     */       
/* 522 */       if (largestEigenvalueNorm == 0.0D) {
/* 523 */         return false;
/*     */       }
/* 525 */       for (i = 0; i < this.realEigenvalues.length; i++) {
/*     */ 
/*     */         
/* 528 */         if (Precision.equals(eigenvalueNorm(i) / largestEigenvalueNorm, 0.0D, 1.0E-12D)) {
/* 529 */           return false;
/*     */         }
/*     */       } 
/* 532 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double eigenvalueNorm(int i) {
/* 540 */       double re = this.realEigenvalues[i];
/* 541 */       double im = this.imagEigenvalues[i];
/* 542 */       return FastMath.sqrt(re * re + im * im);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RealMatrix getInverse() {
/* 552 */       if (!isNonSingular()) {
/* 553 */         throw new SingularMatrixException();
/*     */       }
/*     */       
/* 556 */       int m = this.realEigenvalues.length;
/* 557 */       double[][] invData = new double[m][m];
/*     */       
/* 559 */       for (int i = 0; i < m; i++) {
/* 560 */         double[] invI = invData[i];
/* 561 */         for (int j = 0; j < m; j++) {
/* 562 */           double invIJ = 0.0D;
/* 563 */           for (int k = 0; k < m; k++) {
/* 564 */             double[] vK = this.eigenvectors[k].getDataRef();
/* 565 */             invIJ += vK[i] * vK[j] / this.realEigenvalues[k];
/*     */           } 
/* 567 */           invI[j] = invIJ;
/*     */         } 
/*     */       } 
/* 570 */       return MatrixUtils.createRealMatrix(invData);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transformToTridiagonal(RealMatrix matrix) {
/* 581 */     this.transformer = new TriDiagonalTransformer(matrix);
/* 582 */     this.main = this.transformer.getMainDiagonalRef();
/* 583 */     this.secondary = this.transformer.getSecondaryDiagonalRef();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void findEigenVectors(double[][] householderMatrix) {
/* 593 */     double[][] z = (double[][])householderMatrix.clone();
/* 594 */     int n = this.main.length;
/* 595 */     this.realEigenvalues = new double[n];
/* 596 */     this.imagEigenvalues = new double[n];
/* 597 */     double[] e = new double[n];
/* 598 */     for (int i = 0; i < n - 1; i++) {
/* 599 */       this.realEigenvalues[i] = this.main[i];
/* 600 */       e[i] = this.secondary[i];
/*     */     } 
/* 602 */     this.realEigenvalues[n - 1] = this.main[n - 1];
/* 603 */     e[n - 1] = 0.0D;
/*     */ 
/*     */     
/* 606 */     double maxAbsoluteValue = 0.0D; int m;
/* 607 */     for (m = 0; m < n; m++) {
/* 608 */       if (FastMath.abs(this.realEigenvalues[m]) > maxAbsoluteValue) {
/* 609 */         maxAbsoluteValue = FastMath.abs(this.realEigenvalues[m]);
/*     */       }
/* 611 */       if (FastMath.abs(e[m]) > maxAbsoluteValue) {
/* 612 */         maxAbsoluteValue = FastMath.abs(e[m]);
/*     */       }
/*     */     } 
/*     */     
/* 616 */     if (maxAbsoluteValue != 0.0D) {
/* 617 */       for (m = 0; m < n; m++) {
/* 618 */         if (FastMath.abs(this.realEigenvalues[m]) <= Precision.EPSILON * maxAbsoluteValue) {
/* 619 */           this.realEigenvalues[m] = 0.0D;
/*     */         }
/* 621 */         if (FastMath.abs(e[m]) <= Precision.EPSILON * maxAbsoluteValue) {
/* 622 */           e[m] = 0.0D;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 627 */     for (int j = 0; j < n; ) {
/* 628 */       int its = 0;
/*     */       while (true) {
/*     */         int i2;
/* 631 */         for (i2 = j; i2 < n - 1; i2++) {
/* 632 */           double delta = FastMath.abs(this.realEigenvalues[i2]) + FastMath.abs(this.realEigenvalues[i2 + 1]);
/*     */           
/* 634 */           if (FastMath.abs(e[i2]) + delta == delta) {
/*     */             break;
/*     */           }
/*     */         } 
/* 638 */         if (i2 != j) {
/* 639 */           if (its == this.maxIter) {
/* 640 */             throw new MaxCountExceededException(LocalizedFormats.CONVERGENCE_FAILED, Byte.valueOf(this.maxIter), new Object[0]);
/*     */           }
/*     */           
/* 643 */           its++;
/* 644 */           double q = (this.realEigenvalues[j + 1] - this.realEigenvalues[j]) / 2.0D * e[j];
/* 645 */           double t = FastMath.sqrt(1.0D + q * q);
/* 646 */           if (q < 0.0D) {
/* 647 */             q = this.realEigenvalues[i2] - this.realEigenvalues[j] + e[j] / (q - t);
/*     */           } else {
/* 649 */             q = this.realEigenvalues[i2] - this.realEigenvalues[j] + e[j] / (q + t);
/*     */           } 
/* 651 */           double u = 0.0D;
/* 652 */           double s = 1.0D;
/* 653 */           double c = 1.0D;
/*     */           int i3;
/* 655 */           for (i3 = i2 - 1; i3 >= j; i3--) {
/* 656 */             double p = s * e[i3];
/* 657 */             double h = c * e[i3];
/* 658 */             if (FastMath.abs(p) >= FastMath.abs(q)) {
/* 659 */               c = q / p;
/* 660 */               t = FastMath.sqrt(c * c + 1.0D);
/* 661 */               e[i3 + 1] = p * t;
/* 662 */               s = 1.0D / t;
/* 663 */               c *= s;
/*     */             } else {
/* 665 */               s = p / q;
/* 666 */               t = FastMath.sqrt(s * s + 1.0D);
/* 667 */               e[i3 + 1] = q * t;
/* 668 */               c = 1.0D / t;
/* 669 */               s *= c;
/*     */             } 
/* 671 */             if (e[i3 + 1] == 0.0D) {
/* 672 */               this.realEigenvalues[i3 + 1] = this.realEigenvalues[i3 + 1] - u;
/* 673 */               e[i2] = 0.0D;
/*     */               break;
/*     */             } 
/* 676 */             q = this.realEigenvalues[i3 + 1] - u;
/* 677 */             t = (this.realEigenvalues[i3] - q) * s + 2.0D * c * h;
/* 678 */             u = s * t;
/* 679 */             this.realEigenvalues[i3 + 1] = q + u;
/* 680 */             q = c * t - h;
/* 681 */             for (int ia = 0; ia < n; ia++) {
/* 682 */               p = z[ia][i3 + 1];
/* 683 */               z[ia][i3 + 1] = s * z[ia][i3] + c * p;
/* 684 */               z[ia][i3] = c * z[ia][i3] - s * p;
/*     */             } 
/*     */           } 
/* 687 */           if (t != 0.0D || i3 < j) {
/*     */ 
/*     */             
/* 690 */             this.realEigenvalues[j] = this.realEigenvalues[j] - u;
/* 691 */             e[j] = q;
/* 692 */             e[i2] = 0.0D;
/*     */           } 
/* 694 */         }  if (i2 == j)
/*     */           j++; 
/*     */       } 
/*     */     }  int k;
/* 698 */     for (k = 0; k < n; k++) {
/* 699 */       int i2 = k;
/* 700 */       double p = this.realEigenvalues[k]; int i3;
/* 701 */       for (i3 = k + 1; i3 < n; i3++) {
/* 702 */         if (this.realEigenvalues[i3] > p) {
/* 703 */           i2 = i3;
/* 704 */           p = this.realEigenvalues[i3];
/*     */         } 
/*     */       } 
/* 707 */       if (i2 != k) {
/* 708 */         this.realEigenvalues[i2] = this.realEigenvalues[k];
/* 709 */         this.realEigenvalues[k] = p;
/* 710 */         for (i3 = 0; i3 < n; i3++) {
/* 711 */           p = z[i3][k];
/* 712 */           z[i3][k] = z[i3][i2];
/* 713 */           z[i3][i2] = p;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 719 */     maxAbsoluteValue = 0.0D;
/* 720 */     for (k = 0; k < n; k++) {
/* 721 */       if (FastMath.abs(this.realEigenvalues[k]) > maxAbsoluteValue) {
/* 722 */         maxAbsoluteValue = FastMath.abs(this.realEigenvalues[k]);
/*     */       }
/*     */     } 
/*     */     
/* 726 */     if (maxAbsoluteValue != 0.0D) {
/* 727 */       for (k = 0; k < n; k++) {
/* 728 */         if (FastMath.abs(this.realEigenvalues[k]) < Precision.EPSILON * maxAbsoluteValue) {
/* 729 */           this.realEigenvalues[k] = 0.0D;
/*     */         }
/*     */       } 
/*     */     }
/* 733 */     this.eigenvectors = new ArrayRealVector[n];
/* 734 */     double[] tmp = new double[n];
/* 735 */     for (int i1 = 0; i1 < n; i1++) {
/* 736 */       for (int i2 = 0; i2 < n; i2++) {
/* 737 */         tmp[i2] = z[i2][i1];
/*     */       }
/* 739 */       this.eigenvectors[i1] = new ArrayRealVector(tmp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SchurTransformer transformToSchur(RealMatrix matrix) {
/* 750 */     SchurTransformer schurTransform = new SchurTransformer(matrix);
/* 751 */     double[][] matT = schurTransform.getT().getData();
/*     */     
/* 753 */     this.realEigenvalues = new double[matT.length];
/* 754 */     this.imagEigenvalues = new double[matT.length];
/*     */     
/* 756 */     for (int i = 0; i < this.realEigenvalues.length; i++) {
/* 757 */       if (i == this.realEigenvalues.length - 1 || Precision.equals(matT[i + 1][i], 0.0D, 1.0E-12D)) {
/*     */         
/* 759 */         this.realEigenvalues[i] = matT[i][i];
/*     */       } else {
/* 761 */         double x = matT[i + 1][i + 1];
/* 762 */         double p = 0.5D * (matT[i][i] - x);
/* 763 */         double z = FastMath.sqrt(FastMath.abs(p * p + matT[i + 1][i] * matT[i][i + 1]));
/* 764 */         this.realEigenvalues[i] = x + p;
/* 765 */         this.imagEigenvalues[i] = z;
/* 766 */         this.realEigenvalues[i + 1] = x + p;
/* 767 */         this.imagEigenvalues[i + 1] = -z;
/* 768 */         i++;
/*     */       } 
/*     */     } 
/* 771 */     return schurTransform;
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
/*     */   private Complex cdiv(double xr, double xi, double yr, double yi) {
/* 785 */     return (new Complex(xr, xi)).divide(new Complex(yr, yi));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void findEigenVectorsFromSchur(SchurTransformer schur) throws MathArithmeticException {
/* 796 */     double[][] matrixT = schur.getT().getData();
/* 797 */     double[][] matrixP = schur.getP().getData();
/*     */     
/* 799 */     int n = matrixT.length;
/*     */ 
/*     */     
/* 802 */     double norm = 0.0D;
/* 803 */     for (int i = 0; i < n; i++) {
/* 804 */       for (int m = FastMath.max(i - 1, 0); m < n; m++) {
/* 805 */         norm += FastMath.abs(matrixT[i][m]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 810 */     if (Precision.equals(norm, 0.0D, 1.0E-12D)) {
/* 811 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 816 */     double r = 0.0D;
/* 817 */     double s = 0.0D;
/* 818 */     double z = 0.0D;
/*     */     
/* 820 */     for (int idx = n - 1; idx >= 0; idx--) {
/* 821 */       double p = this.realEigenvalues[idx];
/* 822 */       double q = this.imagEigenvalues[idx];
/*     */       
/* 824 */       if (Precision.equals(q, 0.0D)) {
/*     */         
/* 826 */         int l = idx;
/* 827 */         matrixT[idx][idx] = 1.0D;
/* 828 */         for (int m = idx - 1; m >= 0; m--) {
/* 829 */           double w = matrixT[m][m] - p;
/* 830 */           r = 0.0D;
/* 831 */           for (int i1 = l; i1 <= idx; i1++) {
/* 832 */             r += matrixT[m][i1] * matrixT[i1][idx];
/*     */           }
/* 834 */           if (Precision.compareTo(this.imagEigenvalues[m], 0.0D, 1.0E-12D) < 0) {
/* 835 */             z = w;
/* 836 */             s = r;
/*     */           } else {
/* 838 */             l = m;
/* 839 */             if (Precision.equals(this.imagEigenvalues[m], 0.0D)) {
/* 840 */               if (w != 0.0D) {
/* 841 */                 matrixT[m][idx] = -r / w;
/*     */               } else {
/* 843 */                 matrixT[m][idx] = -r / Precision.EPSILON * norm;
/*     */               } 
/*     */             } else {
/*     */               
/* 847 */               double x = matrixT[m][m + 1];
/* 848 */               double y = matrixT[m + 1][m];
/* 849 */               q = (this.realEigenvalues[m] - p) * (this.realEigenvalues[m] - p) + this.imagEigenvalues[m] * this.imagEigenvalues[m];
/*     */               
/* 851 */               double d1 = (x * s - z * r) / q;
/* 852 */               matrixT[m][idx] = d1;
/* 853 */               if (FastMath.abs(x) > FastMath.abs(z)) {
/* 854 */                 matrixT[m + 1][idx] = (-r - w * d1) / x;
/*     */               } else {
/* 856 */                 matrixT[m + 1][idx] = (-s - y * d1) / z;
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 861 */             double t = FastMath.abs(matrixT[m][idx]);
/* 862 */             if (Precision.EPSILON * t * t > 1.0D) {
/* 863 */               for (int i2 = m; i2 <= idx; i2++) {
/* 864 */                 matrixT[i2][idx] = matrixT[i2][idx] / t;
/*     */               }
/*     */             }
/*     */           } 
/*     */         } 
/* 869 */       } else if (q < 0.0D) {
/*     */         
/* 871 */         int l = idx - 1;
/*     */ 
/*     */         
/* 874 */         if (FastMath.abs(matrixT[idx][idx - 1]) > FastMath.abs(matrixT[idx - 1][idx])) {
/* 875 */           matrixT[idx - 1][idx - 1] = q / matrixT[idx][idx - 1];
/* 876 */           matrixT[idx - 1][idx] = -(matrixT[idx][idx] - p) / matrixT[idx][idx - 1];
/*     */         } else {
/* 878 */           Complex result = cdiv(0.0D, -matrixT[idx - 1][idx], matrixT[idx - 1][idx - 1] - p, q);
/*     */           
/* 880 */           matrixT[idx - 1][idx - 1] = result.getReal();
/* 881 */           matrixT[idx - 1][idx] = result.getImaginary();
/*     */         } 
/*     */         
/* 884 */         matrixT[idx][idx - 1] = 0.0D;
/* 885 */         matrixT[idx][idx] = 1.0D;
/*     */         
/* 887 */         for (int m = idx - 2; m >= 0; m--) {
/* 888 */           double ra = 0.0D;
/* 889 */           double sa = 0.0D;
/* 890 */           for (int i1 = l; i1 <= idx; i1++) {
/* 891 */             ra += matrixT[m][i1] * matrixT[i1][idx - 1];
/* 892 */             sa += matrixT[m][i1] * matrixT[i1][idx];
/*     */           } 
/* 894 */           double w = matrixT[m][m] - p;
/*     */           
/* 896 */           if (Precision.compareTo(this.imagEigenvalues[m], 0.0D, 1.0E-12D) < 0) {
/* 897 */             z = w;
/* 898 */             r = ra;
/* 899 */             s = sa;
/*     */           } else {
/* 901 */             l = m;
/* 902 */             if (Precision.equals(this.imagEigenvalues[m], 0.0D)) {
/* 903 */               Complex c = cdiv(-ra, -sa, w, q);
/* 904 */               matrixT[m][idx - 1] = c.getReal();
/* 905 */               matrixT[m][idx] = c.getImaginary();
/*     */             } else {
/*     */               
/* 908 */               double x = matrixT[m][m + 1];
/* 909 */               double y = matrixT[m + 1][m];
/* 910 */               double vr = (this.realEigenvalues[m] - p) * (this.realEigenvalues[m] - p) + this.imagEigenvalues[m] * this.imagEigenvalues[m] - q * q;
/*     */               
/* 912 */               double vi = (this.realEigenvalues[m] - p) * 2.0D * q;
/* 913 */               if (Precision.equals(vr, 0.0D) && Precision.equals(vi, 0.0D)) {
/* 914 */                 vr = Precision.EPSILON * norm * (FastMath.abs(w) + FastMath.abs(q) + FastMath.abs(x) + FastMath.abs(y) + FastMath.abs(z));
/*     */               }
/*     */ 
/*     */               
/* 918 */               Complex c = cdiv(x * r - z * ra + q * sa, x * s - z * sa - q * ra, vr, vi);
/*     */               
/* 920 */               matrixT[m][idx - 1] = c.getReal();
/* 921 */               matrixT[m][idx] = c.getImaginary();
/*     */               
/* 923 */               if (FastMath.abs(x) > FastMath.abs(z) + FastMath.abs(q)) {
/* 924 */                 matrixT[m + 1][idx - 1] = (-ra - w * matrixT[m][idx - 1] + q * matrixT[m][idx]) / x;
/*     */                 
/* 926 */                 matrixT[m + 1][idx] = (-sa - w * matrixT[m][idx] - q * matrixT[m][idx - 1]) / x;
/*     */               } else {
/*     */                 
/* 929 */                 Complex c2 = cdiv(-r - y * matrixT[m][idx - 1], -s - y * matrixT[m][idx], z, q);
/*     */                 
/* 931 */                 matrixT[m + 1][idx - 1] = c2.getReal();
/* 932 */                 matrixT[m + 1][idx] = c2.getImaginary();
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 937 */             double t = FastMath.max(FastMath.abs(matrixT[m][idx - 1]), FastMath.abs(matrixT[m][idx]));
/*     */             
/* 939 */             if (Precision.EPSILON * t * t > 1.0D) {
/* 940 */               for (int i2 = m; i2 <= idx; i2++) {
/* 941 */                 matrixT[i2][idx - 1] = matrixT[i2][idx - 1] / t;
/* 942 */                 matrixT[i2][idx] = matrixT[i2][idx] / t;
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 951 */     for (int j = n - 1; j >= 0; j--) {
/* 952 */       for (int m = 0; m <= n - 1; m++) {
/* 953 */         z = 0.0D;
/* 954 */         for (int i1 = 0; i1 <= FastMath.min(j, n - 1); i1++) {
/* 955 */           z += matrixP[m][i1] * matrixT[i1][j];
/*     */         }
/* 957 */         matrixP[m][j] = z;
/*     */       } 
/*     */     } 
/*     */     
/* 961 */     this.eigenvectors = new ArrayRealVector[n];
/* 962 */     double[] tmp = new double[n];
/* 963 */     for (int k = 0; k < n; k++) {
/* 964 */       for (int m = 0; m < n; m++) {
/* 965 */         tmp[m] = matrixP[m][k];
/*     */       }
/* 967 */       this.eigenvectors[k] = new ArrayRealVector(tmp);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/EigenDecomposition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */