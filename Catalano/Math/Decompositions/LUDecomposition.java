/*     */ package Catalano.Math.Decompositions;
/*     */ 
/*     */ import Catalano.Math.Matrix;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   implements Serializable
/*     */ {
/*     */   private double[][] LU;
/*     */   private int m;
/*     */   private int n;
/*     */   private int pivsign;
/*     */   private int[] piv;
/*     */   
/*     */   public LUDecomposition(double[][] matrix) {
/*  70 */     this.LU = Matrix.Copy(matrix);
/*  71 */     this.m = matrix.length;
/*  72 */     this.n = (matrix[0]).length;
/*  73 */     this.piv = new int[this.m];
/*     */     
/*  75 */     for (int i = 0; i < this.m; i++) {
/*  76 */       this.piv[i] = i;
/*     */     }
/*  78 */     this.pivsign = 1;
/*     */     
/*  80 */     double[] LUcolj = new double[this.m];
/*     */ 
/*     */     
/*  83 */     for (int j = 0; j < this.n; j++) {
/*     */       int k;
/*     */ 
/*     */       
/*  87 */       for (k = 0; k < this.m; k++) {
/*  88 */         LUcolj[k] = this.LU[k][j];
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  93 */       for (k = 0; k < this.m; k++) {
/*  94 */         double[] LUrowi = this.LU[k];
/*     */ 
/*     */ 
/*     */         
/*  98 */         int kmax = Math.min(k, j);
/*  99 */         double s = 0.0D;
/* 100 */         for (int n = 0; n < kmax; n++) {
/* 101 */           s += LUrowi[n] * LUcolj[n];
/*     */         }
/*     */         
/* 104 */         LUcolj[k] = LUcolj[k] - s; LUrowi[j] = LUcolj[k] - s;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 109 */       int p = j; int m;
/* 110 */       for (m = j + 1; m < this.m; m++) {
/* 111 */         if (Math.abs(LUcolj[m]) > Math.abs(LUcolj[p])) {
/* 112 */           p = m;
/*     */         }
/*     */       } 
/* 115 */       if (p != j) {
/* 116 */         int n; for (n = 0; n < this.n; n++) {
/* 117 */           double t = this.LU[p][n]; this.LU[p][n] = this.LU[j][n]; this.LU[j][n] = t;
/*     */         } 
/* 119 */         n = this.piv[p]; this.piv[p] = this.piv[j]; this.piv[j] = n;
/* 120 */         this.pivsign = -this.pivsign;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 125 */       if (j < this.m && this.LU[j][j] != 0.0D) {
/* 126 */         for (m = j + 1; m < this.m; m++) {
/* 127 */           this.LU[m][j] = this.LU[m][j] / this.LU[j][j];
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNonsingular() {
/* 138 */     for (int j = 0; j < this.n; j++) {
/* 139 */       if (this.LU[j][j] == 0.0D)
/* 140 */         return false; 
/*     */     } 
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] inverse() {
/* 150 */     return solve(Matrix.Identity(this.m, this.m));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getL() {
/* 158 */     double[][] X = new double[this.m][this.n];
/* 159 */     double[][] L = X;
/* 160 */     for (int i = 0; i < this.m; i++) {
/* 161 */       for (int j = 0; j < this.n; j++) {
/* 162 */         if (i > j) {
/* 163 */           L[i][j] = this.LU[i][j];
/* 164 */         } else if (i == j) {
/* 165 */           L[i][j] = 1.0D;
/*     */         } else {
/* 167 */           L[i][j] = 0.0D;
/*     */         } 
/*     */       } 
/*     */     } 
/* 171 */     return X;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getU() {
/* 179 */     double[][] X = new double[this.n][this.n];
/* 180 */     double[][] U = X;
/* 181 */     for (int i = 0; i < this.n; i++) {
/* 182 */       for (int j = 0; j < this.n; j++) {
/* 183 */         if (i <= j) {
/* 184 */           U[i][j] = this.LU[i][j];
/*     */         } else {
/* 186 */           U[i][j] = 0.0D;
/*     */         } 
/*     */       } 
/*     */     } 
/* 190 */     return X;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getPivot() {
/* 198 */     int[] p = new int[this.m];
/* 199 */     for (int i = 0; i < this.m; i++) {
/* 200 */       p[i] = this.piv[i];
/*     */     }
/* 202 */     return p;
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
/*     */   public double[] getDoublePivot() {
/* 214 */     double[] vals = new double[this.m];
/* 215 */     for (int i = 0; i < this.m; i++) {
/* 216 */       vals[i] = this.piv[i];
/*     */     }
/* 218 */     return vals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double determinant() {
/* 226 */     if (this.m != this.n) {
/* 227 */       throw new IllegalArgumentException("Matrix must be square.");
/*     */     }
/* 229 */     double d = this.pivsign;
/* 230 */     for (int j = 0; j < this.n; j++) {
/* 231 */       d *= this.LU[j][j];
/*     */     }
/* 233 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] solve(double[][] B) {
/* 244 */     if (B.length != this.m) {
/* 245 */       throw new IllegalArgumentException("Matrix row dimensions must agree.");
/*     */     }
/* 247 */     if (!isNonsingular()) {
/* 248 */       throw new RuntimeException("Matrix is singular.");
/*     */     }
/*     */ 
/*     */     
/* 252 */     int nx = (B[0]).length;
/* 253 */     double[][] X = Matrix.Submatrix(B, this.piv, 0, nx - 1);
/*     */     
/*     */     int k;
/* 256 */     for (k = 0; k < this.n; k++) {
/* 257 */       for (int i = k + 1; i < this.n; i++) {
/* 258 */         for (int j = 0; j < nx; j++) {
/* 259 */           X[i][j] = X[i][j] - X[k][j] * this.LU[i][k];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 264 */     for (k = this.n - 1; k >= 0; k--) {
/* 265 */       for (int j = 0; j < nx; j++) {
/* 266 */         X[k][j] = X[k][j] / this.LU[k][k];
/*     */       }
/* 268 */       for (int i = 0; i < k; i++) {
/* 269 */         for (int m = 0; m < nx; m++) {
/* 270 */           X[i][m] = X[i][m] - X[k][m] * this.LU[i][k];
/*     */         }
/*     */       } 
/*     */     } 
/* 274 */     return X;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Decompositions/LUDecomposition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */