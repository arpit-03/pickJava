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
/*     */ public class CholeskyDecomposition
/*     */   implements Serializable
/*     */ {
/*     */   private double[][] L;
/*     */   private int n;
/*     */   private boolean isspd;
/*     */   
/*     */   public CholeskyDecomposition(double[][] matrix) {
/*  65 */     double[][] A = matrix;
/*  66 */     this.n = matrix.length;
/*  67 */     this.L = new double[this.n][this.n];
/*  68 */     this.isspd = ((matrix[0]).length == this.n);
/*     */     
/*  70 */     for (int j = 0; j < this.n; j++) {
/*  71 */       double[] Lrowj = this.L[j];
/*  72 */       double d = 0.0D; int k;
/*  73 */       for (k = 0; k < j; k++) {
/*  74 */         double[] Lrowk = this.L[k];
/*  75 */         double s = 0.0D;
/*  76 */         for (int i = 0; i < k; i++) {
/*  77 */           s += Lrowk[i] * Lrowj[i];
/*     */         }
/*  79 */         Lrowj[k] = s = (A[j][k] - s) / this.L[k][k];
/*  80 */         d += s * s;
/*  81 */         this.isspd &= (A[k][j] == A[j][k]) ? 1 : 0;
/*     */       } 
/*  83 */       d = A[j][j] - d;
/*  84 */       this.isspd &= (d > 0.0D) ? 1 : 0;
/*  85 */       this.L[j][j] = Math.sqrt(Math.max(d, 0.0D));
/*  86 */       for (k = j + 1; k < this.n; k++) {
/*  87 */         this.L[j][k] = 0.0D;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSPD() {
/*  96 */     return this.isspd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getL() {
/* 103 */     return Matrix.SubMatrix(this.L, this.n, this.n);
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
/* 114 */     if (B.length != this.n) {
/* 115 */       throw new IllegalArgumentException("Matrix row dimensions must agree.");
/*     */     }
/* 117 */     if (!this.isspd) {
/* 118 */       throw new RuntimeException("Matrix is not symmetric positive definite.");
/*     */     }
/*     */ 
/*     */     
/* 122 */     double[][] X = Matrix.Copy(B);
/* 123 */     int nx = (B[0]).length;
/*     */     
/*     */     int k;
/* 126 */     for (k = 0; k < this.n; k++) {
/* 127 */       for (int j = 0; j < nx; j++) {
/* 128 */         for (int i = 0; i < k; i++) {
/* 129 */           X[k][j] = X[k][j] - X[i][j] * this.L[k][i];
/*     */         }
/* 131 */         X[k][j] = X[k][j] / this.L[k][k];
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 136 */     for (k = this.n - 1; k >= 0; k--) {
/* 137 */       for (int j = 0; j < nx; j++) {
/* 138 */         for (int i = k + 1; i < this.n; i++) {
/* 139 */           X[k][j] = X[k][j] - X[i][j] * this.L[i][k];
/*     */         }
/* 141 */         X[k][j] = X[k][j] / this.L[k][k];
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     return Matrix.SubMatrix(X, this.n, nx);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Decompositions/CholeskyDecomposition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */