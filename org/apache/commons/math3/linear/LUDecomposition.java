/*     */ package org.apache.commons.math3.linear;
/*     */ 
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
/*     */ public class LUDecomposition
/*     */ {
/*     */   private static final double DEFAULT_TOO_SMALL = 1.0E-11D;
/*     */   private final double[][] lu;
/*     */   private final int[] pivot;
/*     */   private boolean even;
/*     */   private boolean singular;
/*     */   private RealMatrix cachedL;
/*     */   private RealMatrix cachedU;
/*     */   private RealMatrix cachedP;
/*     */   
/*     */   public LUDecomposition(RealMatrix matrix) {
/*  75 */     this(matrix, 1.0E-11D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LUDecomposition(RealMatrix matrix, double singularityThreshold) {
/*  86 */     if (!matrix.isSquare()) {
/*  87 */       throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
/*     */     }
/*     */ 
/*     */     
/*  91 */     int m = matrix.getColumnDimension();
/*  92 */     this.lu = matrix.getData();
/*  93 */     this.pivot = new int[m];
/*  94 */     this.cachedL = null;
/*  95 */     this.cachedU = null;
/*  96 */     this.cachedP = null;
/*     */ 
/*     */     
/*  99 */     for (int row = 0; row < m; row++) {
/* 100 */       this.pivot[row] = row;
/*     */     }
/* 102 */     this.even = true;
/* 103 */     this.singular = false;
/*     */ 
/*     */     
/* 106 */     for (int col = 0; col < m; col++) {
/*     */ 
/*     */       
/* 109 */       for (int i = 0; i < col; i++) {
/* 110 */         double[] luRow = this.lu[i];
/* 111 */         double sum = luRow[col];
/* 112 */         for (int n = 0; n < i; n++) {
/* 113 */           sum -= luRow[n] * this.lu[n][col];
/*     */         }
/* 115 */         luRow[col] = sum;
/*     */       } 
/*     */ 
/*     */       
/* 119 */       int max = col;
/* 120 */       double largest = Double.NEGATIVE_INFINITY;
/* 121 */       for (int j = col; j < m; j++) {
/* 122 */         double[] luRow = this.lu[j];
/* 123 */         double sum = luRow[col];
/* 124 */         for (int n = 0; n < col; n++) {
/* 125 */           sum -= luRow[n] * this.lu[n][col];
/*     */         }
/* 127 */         luRow[col] = sum;
/*     */ 
/*     */         
/* 130 */         if (FastMath.abs(sum) > largest) {
/* 131 */           largest = FastMath.abs(sum);
/* 132 */           max = j;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 137 */       if (FastMath.abs(this.lu[max][col]) < singularityThreshold) {
/* 138 */         this.singular = true;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 143 */       if (max != col) {
/* 144 */         double tmp = 0.0D;
/* 145 */         double[] luMax = this.lu[max];
/* 146 */         double[] luCol = this.lu[col];
/* 147 */         for (int n = 0; n < m; n++) {
/* 148 */           tmp = luMax[n];
/* 149 */           luMax[n] = luCol[n];
/* 150 */           luCol[n] = tmp;
/*     */         } 
/* 152 */         int temp = this.pivot[max];
/* 153 */         this.pivot[max] = this.pivot[col];
/* 154 */         this.pivot[col] = temp;
/* 155 */         this.even = !this.even;
/*     */       } 
/*     */ 
/*     */       
/* 159 */       double luDiag = this.lu[col][col];
/* 160 */       for (int k = col + 1; k < m; k++) {
/* 161 */         this.lu[k][col] = this.lu[k][col] / luDiag;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getL() {
/* 172 */     if (this.cachedL == null && !this.singular) {
/* 173 */       int m = this.pivot.length;
/* 174 */       this.cachedL = MatrixUtils.createRealMatrix(m, m);
/* 175 */       for (int i = 0; i < m; i++) {
/* 176 */         double[] luI = this.lu[i];
/* 177 */         for (int j = 0; j < i; j++) {
/* 178 */           this.cachedL.setEntry(i, j, luI[j]);
/*     */         }
/* 180 */         this.cachedL.setEntry(i, i, 1.0D);
/*     */       } 
/*     */     } 
/* 183 */     return this.cachedL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getU() {
/* 192 */     if (this.cachedU == null && !this.singular) {
/* 193 */       int m = this.pivot.length;
/* 194 */       this.cachedU = MatrixUtils.createRealMatrix(m, m);
/* 195 */       for (int i = 0; i < m; i++) {
/* 196 */         double[] luI = this.lu[i];
/* 197 */         for (int j = i; j < m; j++) {
/* 198 */           this.cachedU.setEntry(i, j, luI[j]);
/*     */         }
/*     */       } 
/*     */     } 
/* 202 */     return this.cachedU;
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
/*     */   public RealMatrix getP() {
/* 215 */     if (this.cachedP == null && !this.singular) {
/* 216 */       int m = this.pivot.length;
/* 217 */       this.cachedP = MatrixUtils.createRealMatrix(m, m);
/* 218 */       for (int i = 0; i < m; i++) {
/* 219 */         this.cachedP.setEntry(i, this.pivot[i], 1.0D);
/*     */       }
/*     */     } 
/* 222 */     return this.cachedP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getPivot() {
/* 231 */     return (int[])this.pivot.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDeterminant() {
/* 239 */     if (this.singular) {
/* 240 */       return 0.0D;
/*     */     }
/* 242 */     int m = this.pivot.length;
/* 243 */     double determinant = this.even ? 1.0D : -1.0D;
/* 244 */     for (int i = 0; i < m; i++) {
/* 245 */       determinant *= this.lu[i][i];
/*     */     }
/* 247 */     return determinant;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DecompositionSolver getSolver() {
/* 257 */     return new Solver(this.lu, this.pivot, this.singular);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Solver
/*     */     implements DecompositionSolver
/*     */   {
/*     */     private final double[][] lu;
/*     */ 
/*     */ 
/*     */     
/*     */     private final int[] pivot;
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean singular;
/*     */ 
/*     */ 
/*     */     
/*     */     private Solver(double[][] lu, int[] pivot, boolean singular) {
/* 279 */       this.lu = lu;
/* 280 */       this.pivot = pivot;
/* 281 */       this.singular = singular;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isNonSingular() {
/* 286 */       return !this.singular;
/*     */     }
/*     */ 
/*     */     
/*     */     public RealVector solve(RealVector b) {
/* 291 */       int m = this.pivot.length;
/* 292 */       if (b.getDimension() != m) {
/* 293 */         throw new DimensionMismatchException(b.getDimension(), m);
/*     */       }
/* 295 */       if (this.singular) {
/* 296 */         throw new SingularMatrixException();
/*     */       }
/*     */       
/* 299 */       double[] bp = new double[m];
/*     */ 
/*     */       
/* 302 */       for (int row = 0; row < m; row++) {
/* 303 */         bp[row] = b.getEntry(this.pivot[row]);
/*     */       }
/*     */       
/*     */       int col;
/* 307 */       for (col = 0; col < m; col++) {
/* 308 */         double bpCol = bp[col];
/* 309 */         for (int i = col + 1; i < m; i++) {
/* 310 */           bp[i] = bp[i] - bpCol * this.lu[i][col];
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 315 */       for (col = m - 1; col >= 0; col--) {
/* 316 */         bp[col] = bp[col] / this.lu[col][col];
/* 317 */         double bpCol = bp[col];
/* 318 */         for (int i = 0; i < col; i++) {
/* 319 */           bp[i] = bp[i] - bpCol * this.lu[i][col];
/*     */         }
/*     */       } 
/*     */       
/* 323 */       return new ArrayRealVector(bp, false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public RealMatrix solve(RealMatrix b) {
/* 329 */       int m = this.pivot.length;
/* 330 */       if (b.getRowDimension() != m) {
/* 331 */         throw new DimensionMismatchException(b.getRowDimension(), m);
/*     */       }
/* 333 */       if (this.singular) {
/* 334 */         throw new SingularMatrixException();
/*     */       }
/*     */       
/* 337 */       int nColB = b.getColumnDimension();
/*     */ 
/*     */       
/* 340 */       double[][] bp = new double[m][nColB];
/* 341 */       for (int row = 0; row < m; row++) {
/* 342 */         double[] bpRow = bp[row];
/* 343 */         int pRow = this.pivot[row];
/* 344 */         for (int i = 0; i < nColB; i++) {
/* 345 */           bpRow[i] = b.getEntry(pRow, i);
/*     */         }
/*     */       } 
/*     */       
/*     */       int col;
/* 350 */       for (col = 0; col < m; col++) {
/* 351 */         double[] bpCol = bp[col];
/* 352 */         for (int i = col + 1; i < m; i++) {
/* 353 */           double[] bpI = bp[i];
/* 354 */           double luICol = this.lu[i][col];
/* 355 */           for (int j = 0; j < nColB; j++) {
/* 356 */             bpI[j] = bpI[j] - bpCol[j] * luICol;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 362 */       for (col = m - 1; col >= 0; col--) {
/* 363 */         double[] bpCol = bp[col];
/* 364 */         double luDiag = this.lu[col][col];
/* 365 */         for (int j = 0; j < nColB; j++) {
/* 366 */           bpCol[j] = bpCol[j] / luDiag;
/*     */         }
/* 368 */         for (int i = 0; i < col; i++) {
/* 369 */           double[] bpI = bp[i];
/* 370 */           double luICol = this.lu[i][col];
/* 371 */           for (int k = 0; k < nColB; k++) {
/* 372 */             bpI[k] = bpI[k] - bpCol[k] * luICol;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 377 */       return new Array2DRowRealMatrix(bp, false);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RealMatrix getInverse() {
/* 387 */       return solve(MatrixUtils.createRealIdentityMatrix(this.pivot.length));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/LUDecomposition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */