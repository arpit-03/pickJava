/*     */ package edu.mines.jtk.la;
/*     */ 
/*     */ import edu.mines.jtk.util.Check;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DMatrixQrd
/*     */ {
/*     */   int _m;
/*     */   int _n;
/*     */   double[][] _qr;
/*     */   double[] _rdiag;
/*     */   
/*     */   public DMatrixQrd(DMatrix a) {
/*  48 */     Check.argument((a.getM() >= a.getN()), "m >= n");
/*  49 */     int m = this._m = a.getM();
/*  50 */     int n = this._n = a.getN();
/*  51 */     this._qr = a.get();
/*  52 */     this._rdiag = new double[this._n];
/*     */ 
/*     */     
/*  55 */     for (int k = 0; k < n; k++) {
/*     */ 
/*     */       
/*  58 */       double nrm = 0.0D; int i;
/*  59 */       for (i = k; i < m; i++) {
/*  60 */         nrm = Math.hypot(nrm, this._qr[i][k]);
/*     */       }
/*  62 */       if (nrm != 0.0D) {
/*     */ 
/*     */         
/*  65 */         if (this._qr[k][k] < 0.0D)
/*  66 */           nrm = -nrm; 
/*  67 */         for (i = k; i < m; i++)
/*  68 */           this._qr[i][k] = this._qr[i][k] / nrm; 
/*  69 */         this._qr[k][k] = this._qr[k][k] + 1.0D;
/*     */ 
/*     */         
/*  72 */         for (int j = k + 1; j < n; j++) {
/*  73 */           double s = 0.0D; int i1;
/*  74 */           for (i1 = k; i1 < m; i1++)
/*  75 */             s += this._qr[i1][k] * this._qr[i1][j]; 
/*  76 */           s = -s / this._qr[k][k];
/*  77 */           for (i1 = k; i1 < m; i1++)
/*  78 */             this._qr[i1][j] = this._qr[i1][j] + s * this._qr[i1][k]; 
/*     */         } 
/*     */       } 
/*  81 */       this._rdiag[k] = -nrm;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullRank() {
/*  90 */     for (int j = 0; j < this._n; j++) {
/*  91 */       if (this._rdiag[j] == 0.0D)
/*  92 */         return false; 
/*     */     } 
/*  94 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getQ() {
/* 102 */     double[][] q = new double[this._m][this._n];
/* 103 */     for (int k = this._n - 1; k >= 0; k--) {
/* 104 */       for (int i = 0; i < this._m; i++) {
/* 105 */         q[i][k] = 0.0D;
/*     */       }
/* 107 */       q[k][k] = 1.0D;
/* 108 */       for (int j = k; j < this._n; j++) {
/* 109 */         if (this._qr[k][k] != 0.0D) {
/* 110 */           double s = 0.0D; int m;
/* 111 */           for (m = k; m < this._m; m++) {
/* 112 */             s += this._qr[m][k] * q[m][j];
/*     */           }
/* 114 */           s = -s / this._qr[k][k];
/* 115 */           for (m = k; m < this._m; m++) {
/* 116 */             q[m][j] = q[m][j] + s * this._qr[m][k];
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 121 */     return new DMatrix(this._m, this._n, q);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getR() {
/* 129 */     double[][] r = new double[this._n][this._n];
/* 130 */     for (int i = 0; i < this._n; i++) {
/* 131 */       r[i][i] = this._rdiag[i];
/* 132 */       for (int j = i + 1; j < this._n; j++) {
/* 133 */         r[i][j] = this._qr[i][j];
/*     */       }
/*     */     } 
/* 136 */     return new DMatrix(this._n, this._n, r);
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
/*     */   public DMatrix solve(DMatrix b) {
/* 149 */     Check.argument((b.getM() == this._m), "A and B have the same number of rows");
/* 150 */     Check.state(isFullRank(), "A is of full rank");
/*     */ 
/*     */     
/* 153 */     int nx = b.getN();
/* 154 */     double[][] x = b.get();
/*     */     
/*     */     int k;
/* 157 */     for (k = 0; k < this._n; k++) {
/* 158 */       for (int j = 0; j < nx; j++) {
/* 159 */         double s = 0.0D; int i;
/* 160 */         for (i = k; i < this._m; i++) {
/* 161 */           s += this._qr[i][k] * x[i][j];
/*     */         }
/* 163 */         s = -s / this._qr[k][k];
/* 164 */         for (i = k; i < this._m; i++) {
/* 165 */           x[i][j] = x[i][j] + s * this._qr[i][k];
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 171 */     for (k = this._n - 1; k >= 0; k--) {
/* 172 */       for (int j = 0; j < nx; j++)
/* 173 */         x[k][j] = x[k][j] / this._rdiag[k]; 
/* 174 */       for (int i = 0; i < k; i++) {
/* 175 */         for (int m = 0; m < nx; m++) {
/* 176 */           x[i][m] = x[i][m] - x[k][m] * this._qr[i][k];
/*     */         }
/*     */       } 
/*     */     } 
/* 180 */     return (new DMatrix(this._m, nx, x)).get(0, this._n - 1, (int[])null);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/la/DMatrixQrd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */