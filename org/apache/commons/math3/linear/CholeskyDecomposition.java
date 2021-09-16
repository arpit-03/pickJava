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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CholeskyDecomposition
/*     */ {
/*     */   public static final double DEFAULT_RELATIVE_SYMMETRY_THRESHOLD = 1.0E-15D;
/*     */   public static final double DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD = 1.0E-10D;
/*     */   private double[][] lTData;
/*     */   private RealMatrix cachedL;
/*     */   private RealMatrix cachedLT;
/*     */   
/*     */   public CholeskyDecomposition(RealMatrix matrix) {
/*  84 */     this(matrix, 1.0E-15D, 1.0E-10D);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CholeskyDecomposition(RealMatrix matrix, double relativeSymmetryThreshold, double absolutePositivityThreshold) {
/* 106 */     if (!matrix.isSquare()) {
/* 107 */       throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
/*     */     }
/*     */ 
/*     */     
/* 111 */     int order = matrix.getRowDimension();
/* 112 */     this.lTData = matrix.getData();
/* 113 */     this.cachedL = null;
/* 114 */     this.cachedLT = null;
/*     */     
/*     */     int i;
/* 117 */     for (i = 0; i < order; i++) {
/* 118 */       double[] lI = this.lTData[i];
/*     */ 
/*     */       
/* 121 */       for (int j = i + 1; j < order; j++) {
/* 122 */         double[] lJ = this.lTData[j];
/* 123 */         double lIJ = lI[j];
/* 124 */         double lJI = lJ[i];
/* 125 */         double maxDelta = relativeSymmetryThreshold * FastMath.max(FastMath.abs(lIJ), FastMath.abs(lJI));
/*     */         
/* 127 */         if (FastMath.abs(lIJ - lJI) > maxDelta) {
/* 128 */           throw new NonSymmetricMatrixException(i, j, relativeSymmetryThreshold);
/*     */         }
/* 130 */         lJ[i] = 0.0D;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 135 */     for (i = 0; i < order; i++) {
/*     */       
/* 137 */       double[] ltI = this.lTData[i];
/*     */ 
/*     */       
/* 140 */       if (ltI[i] <= absolutePositivityThreshold) {
/* 141 */         throw new NonPositiveDefiniteMatrixException(ltI[i], i, absolutePositivityThreshold);
/*     */       }
/*     */       
/* 144 */       ltI[i] = FastMath.sqrt(ltI[i]);
/* 145 */       double inverse = 1.0D / ltI[i];
/*     */       
/* 147 */       for (int q = order - 1; q > i; q--) {
/* 148 */         ltI[q] = ltI[q] * inverse;
/* 149 */         double[] ltQ = this.lTData[q];
/* 150 */         for (int p = q; p < order; p++) {
/* 151 */           ltQ[p] = ltQ[p] - ltI[q] * ltI[p];
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
/*     */   public RealMatrix getL() {
/* 163 */     if (this.cachedL == null) {
/* 164 */       this.cachedL = getLT().transpose();
/*     */     }
/* 166 */     return this.cachedL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getLT() {
/* 176 */     if (this.cachedLT == null) {
/* 177 */       this.cachedLT = MatrixUtils.createRealMatrix(this.lTData);
/*     */     }
/*     */ 
/*     */     
/* 181 */     return this.cachedLT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDeterminant() {
/* 189 */     double determinant = 1.0D;
/* 190 */     for (int i = 0; i < this.lTData.length; i++) {
/* 191 */       double lTii = this.lTData[i][i];
/* 192 */       determinant *= lTii * lTii;
/*     */     } 
/* 194 */     return determinant;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DecompositionSolver getSolver() {
/* 202 */     return new Solver(this.lTData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Solver
/*     */     implements DecompositionSolver
/*     */   {
/*     */     private final double[][] lTData;
/*     */ 
/*     */ 
/*     */     
/*     */     private Solver(double[][] lTData) {
/* 215 */       this.lTData = lTData;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isNonSingular() {
/* 221 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public RealVector solve(RealVector b) {
/* 226 */       int m = this.lTData.length;
/* 227 */       if (b.getDimension() != m) {
/* 228 */         throw new DimensionMismatchException(b.getDimension(), m);
/*     */       }
/*     */       
/* 231 */       double[] x = b.toArray();
/*     */       
/*     */       int j;
/* 234 */       for (j = 0; j < m; j++) {
/* 235 */         double[] lJ = this.lTData[j];
/* 236 */         x[j] = x[j] / lJ[j];
/* 237 */         double xJ = x[j];
/* 238 */         for (int i = j + 1; i < m; i++) {
/* 239 */           x[i] = x[i] - xJ * lJ[i];
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 244 */       for (j = m - 1; j >= 0; j--) {
/* 245 */         x[j] = x[j] / this.lTData[j][j];
/* 246 */         double xJ = x[j];
/* 247 */         for (int i = 0; i < j; i++) {
/* 248 */           x[i] = x[i] - xJ * this.lTData[i][j];
/*     */         }
/*     */       } 
/*     */       
/* 252 */       return new ArrayRealVector(x, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public RealMatrix solve(RealMatrix b) {
/* 257 */       int m = this.lTData.length;
/* 258 */       if (b.getRowDimension() != m) {
/* 259 */         throw new DimensionMismatchException(b.getRowDimension(), m);
/*     */       }
/*     */       
/* 262 */       int nColB = b.getColumnDimension();
/* 263 */       double[][] x = b.getData();
/*     */       
/*     */       int j;
/* 266 */       for (j = 0; j < m; j++) {
/* 267 */         double[] lJ = this.lTData[j];
/* 268 */         double lJJ = lJ[j];
/* 269 */         double[] xJ = x[j];
/* 270 */         for (int k = 0; k < nColB; k++) {
/* 271 */           xJ[k] = xJ[k] / lJJ;
/*     */         }
/* 273 */         for (int i = j + 1; i < m; i++) {
/* 274 */           double[] xI = x[i];
/* 275 */           double lJI = lJ[i];
/* 276 */           for (int n = 0; n < nColB; n++) {
/* 277 */             xI[n] = xI[n] - xJ[n] * lJI;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 283 */       for (j = m - 1; j >= 0; j--) {
/* 284 */         double lJJ = this.lTData[j][j];
/* 285 */         double[] xJ = x[j];
/* 286 */         for (int k = 0; k < nColB; k++) {
/* 287 */           xJ[k] = xJ[k] / lJJ;
/*     */         }
/* 289 */         for (int i = 0; i < j; i++) {
/* 290 */           double[] xI = x[i];
/* 291 */           double lIJ = this.lTData[i][j];
/* 292 */           for (int n = 0; n < nColB; n++) {
/* 293 */             xI[n] = xI[n] - xJ[n] * lIJ;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 298 */       return new Array2DRowRealMatrix(x);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RealMatrix getInverse() {
/* 307 */       return solve(MatrixUtils.createRealIdentityMatrix(this.lTData.length));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/CholeskyDecomposition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */