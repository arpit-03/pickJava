/*     */ package Catalano.Math.Decompositions;
/*     */ 
/*     */ import Catalano.Math.Tools;
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
/*     */ public class QRDecomposition
/*     */   implements Serializable
/*     */ {
/*     */   private double[][] QR;
/*     */   private int m;
/*     */   private int n;
/*     */   private double[] Rdiag;
/*     */   
/*     */   public QRDecomposition(double[][] matrix) {
/*  68 */     this.QR = (double[][])matrix.clone();
/*  69 */     this.m = matrix.length;
/*  70 */     this.n = (matrix[0]).length;
/*  71 */     this.Rdiag = new double[this.n];
/*     */ 
/*     */     
/*  74 */     for (int k = 0; k < this.n; k++) {
/*     */       
/*  76 */       double nrm = 0.0D; int i;
/*  77 */       for (i = k; i < this.m; i++) {
/*  78 */         nrm = Tools.Hypotenuse(nrm, this.QR[i][k]);
/*     */       }
/*     */       
/*  81 */       if (nrm != 0.0D) {
/*     */         
/*  83 */         if (this.QR[k][k] < 0.0D) {
/*  84 */           nrm = -nrm;
/*     */         }
/*  86 */         for (i = k; i < this.m; i++) {
/*  87 */           this.QR[i][k] = this.QR[i][k] / nrm;
/*     */         }
/*  89 */         this.QR[k][k] = this.QR[k][k] + 1.0D;
/*     */ 
/*     */         
/*  92 */         for (int j = k + 1; j < this.n; j++) {
/*  93 */           double s = 0.0D; int m;
/*  94 */           for (m = k; m < this.m; m++) {
/*  95 */             s += this.QR[m][k] * this.QR[m][j];
/*     */           }
/*  97 */           s = -s / this.QR[k][k];
/*  98 */           for (m = k; m < this.m; m++) {
/*  99 */             this.QR[m][j] = this.QR[m][j] + s * this.QR[m][k];
/*     */           }
/*     */         } 
/*     */       } 
/* 103 */       this.Rdiag[k] = -nrm;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullRank() {
/* 112 */     for (int j = 0; j < this.n; j++) {
/* 113 */       if (this.Rdiag[j] == 0.0D)
/* 114 */         return false; 
/*     */     } 
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getH() {
/* 124 */     double[][] X = new double[this.m][this.n];
/* 125 */     double[][] H = X;
/* 126 */     for (int i = 0; i < this.m; i++) {
/* 127 */       for (int j = 0; j < this.n; j++) {
/* 128 */         if (i >= j) {
/* 129 */           H[i][j] = this.QR[i][j];
/*     */         } else {
/* 131 */           H[i][j] = 0.0D;
/*     */         } 
/*     */       } 
/*     */     } 
/* 135 */     return X;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getR() {
/* 143 */     double[][] X = new double[this.n][this.n];
/* 144 */     double[][] R = X;
/* 145 */     for (int i = 0; i < this.n; i++) {
/* 146 */       for (int j = 0; j < this.n; j++) {
/* 147 */         if (i < j) {
/* 148 */           R[i][j] = this.QR[i][j];
/* 149 */         } else if (i == j) {
/* 150 */           R[i][j] = this.Rdiag[i];
/*     */         } else {
/* 152 */           R[i][j] = 0.0D;
/*     */         } 
/*     */       } 
/*     */     } 
/* 156 */     return X;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getQ() {
/* 164 */     double[][] X = new double[this.m][this.n];
/* 165 */     double[][] Q = X;
/* 166 */     for (int k = this.n - 1; k >= 0; k--) {
/* 167 */       for (int i = 0; i < this.m; i++) {
/* 168 */         Q[i][k] = 0.0D;
/*     */       }
/* 170 */       Q[k][k] = 1.0D;
/* 171 */       for (int j = k; j < this.n; j++) {
/* 172 */         if (this.QR[k][k] != 0.0D) {
/* 173 */           double s = 0.0D; int m;
/* 174 */           for (m = k; m < this.m; m++) {
/* 175 */             s += this.QR[m][k] * Q[m][j];
/*     */           }
/* 177 */           s = -s / this.QR[k][k];
/* 178 */           for (m = k; m < this.m; m++) {
/* 179 */             Q[m][j] = Q[m][j] + s * this.QR[m][k];
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 184 */     return X;
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
/* 195 */     if (B.length != this.m) {
/* 196 */       throw new IllegalArgumentException("Matrix row dimensions must agree.");
/*     */     }
/* 198 */     if (!isFullRank()) {
/* 199 */       throw new RuntimeException("Matrix is rank deficient.");
/*     */     }
/*     */ 
/*     */     
/* 203 */     int nx = (B[0]).length;
/* 204 */     double[][] X = (double[][])B.clone();
/*     */     
/*     */     int k;
/* 207 */     for (k = 0; k < this.n; k++) {
/* 208 */       for (int j = 0; j < nx; j++) {
/* 209 */         double s = 0.0D; int m;
/* 210 */         for (m = k; m < this.m; m++) {
/* 211 */           s += this.QR[m][k] * X[m][j];
/*     */         }
/* 213 */         s = -s / this.QR[k][k];
/* 214 */         for (m = k; m < this.m; m++) {
/* 215 */           X[m][j] = X[m][j] + s * this.QR[m][k];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     for (k = this.n - 1; k >= 0; k--) {
/* 221 */       for (int j = 0; j < nx; j++) {
/* 222 */         X[k][j] = X[k][j] / this.Rdiag[k];
/*     */       }
/* 224 */       for (int m = 0; m < k; m++) {
/* 225 */         for (int n = 0; n < nx; n++) {
/* 226 */           X[m][n] = X[m][n] - X[k][n] * this.QR[m][k];
/*     */         }
/*     */       } 
/*     */     } 
/* 230 */     double[][] mat = new double[this.n][nx];
/* 231 */     for (int i = 0; i < mat.length; i++) {
/* 232 */       for (int j = 0; j < (mat[0]).length; j++) {
/* 233 */         mat[i][j] = X[i][j];
/*     */       }
/*     */     } 
/*     */     
/* 237 */     return mat;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Decompositions/QRDecomposition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */