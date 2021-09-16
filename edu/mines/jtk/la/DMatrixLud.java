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
/*     */ public class DMatrixLud
/*     */ {
/*     */   int _m;
/*     */   int _n;
/*     */   double[][] _lu;
/*     */   int[] _piv;
/*     */   int _pivsign;
/*     */   
/*     */   public DMatrixLud(DMatrix a) {
/*  48 */     int m = this._m = a.getM();
/*  49 */     int n = this._n = a.getN();
/*  50 */     double[][] lu = this._lu = a.get();
/*  51 */     this._piv = new int[m];
/*  52 */     for (int i = 0; i < m; i++)
/*  53 */       this._piv[i] = i; 
/*  54 */     this._pivsign = 1;
/*     */     
/*  56 */     double[] lucolj = new double[m];
/*     */ 
/*     */     
/*  59 */     for (int j = 0; j < n; j++) {
/*     */       int k;
/*     */       
/*  62 */       for (k = 0; k < m; k++) {
/*  63 */         lucolj[k] = lu[k][j];
/*     */       }
/*     */       
/*  66 */       for (k = 0; k < m; k++) {
/*  67 */         double[] lurowi = lu[k];
/*     */ 
/*     */         
/*  70 */         int kmax = Math.min(k, j);
/*  71 */         double s = 0.0D;
/*  72 */         for (int i2 = 0; i2 < kmax; i2++)
/*  73 */           s += lurowi[i2] * lucolj[i2]; 
/*  74 */         lucolj[k] = lucolj[k] - s; lurowi[j] = lucolj[k] - s;
/*     */       } 
/*     */ 
/*     */       
/*  78 */       int p = j; int i1;
/*  79 */       for (i1 = j + 1; i1 < m; i1++) {
/*  80 */         if (Math.abs(lucolj[i1]) > Math.abs(lucolj[p]))
/*  81 */           p = i1; 
/*     */       } 
/*  83 */       if (p != j) {
/*  84 */         int i2; for (i2 = 0; i2 < n; i2++) {
/*  85 */           double t = lu[p][i2];
/*  86 */           lu[p][i2] = lu[j][i2];
/*  87 */           lu[j][i2] = t;
/*     */         } 
/*  89 */         i2 = this._piv[p];
/*  90 */         this._piv[p] = this._piv[j];
/*  91 */         this._piv[j] = i2;
/*  92 */         this._pivsign = -this._pivsign;
/*     */       } 
/*     */ 
/*     */       
/*  96 */       if (j < m && lu[j][j] != 0.0D) {
/*  97 */         for (i1 = j + 1; i1 < m; i1++) {
/*  98 */           lu[i1][j] = lu[i1][j] / lu[j][j];
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNonSingular() {
/* 108 */     for (int j = 0; j < this._n; j++) {
/* 109 */       if (this._lu[j][j] == 0.0D)
/* 110 */         return false; 
/*     */     } 
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSingular() {
/* 120 */     return !isNonSingular();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getL() {
/* 128 */     double[][] l = new double[this._m][this._n];
/* 129 */     for (int i = 0; i < this._m; i++) {
/* 130 */       for (int j = 0; j < this._n; j++) {
/* 131 */         if (i > j) {
/* 132 */           l[i][j] = this._lu[i][j];
/* 133 */         } else if (i == j) {
/* 134 */           l[i][j] = 1.0D;
/*     */         } else {
/* 136 */           l[i][j] = 0.0D;
/*     */         } 
/*     */       } 
/*     */     } 
/* 140 */     return new DMatrix(this._m, this._n, l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getU() {
/* 148 */     double[][] u = new double[this._n][this._n];
/* 149 */     for (int i = 0; i < this._n; i++) {
/* 150 */       for (int j = 0; j < this._n; j++) {
/* 151 */         if (i <= j) {
/* 152 */           u[i][j] = this._lu[i][j];
/*     */         } else {
/* 154 */           u[i][j] = 0.0D;
/*     */         } 
/*     */       } 
/*     */     } 
/* 158 */     return new DMatrix(this._n, this._n, u);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getPivot() {
/* 166 */     int[] p = new int[this._m];
/* 167 */     for (int i = 0; i < this._m; i++)
/* 168 */       p[i] = this._piv[i]; 
/* 169 */     return p;
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
/* 182 */     Check.argument((b.getM() == this._m), "A and B have the same number of rows");
/* 183 */     Check.state(isNonSingular(), "A is non-singular");
/*     */ 
/*     */     
/* 186 */     int nx = b.getN();
/* 187 */     DMatrix xx = b.get(this._piv, 0, nx - 1);
/* 188 */     double[][] x = xx.getArray();
/*     */     
/*     */     int k;
/* 191 */     for (k = 0; k < this._n; k++) {
/* 192 */       for (int i = k + 1; i < this._n; i++) {
/* 193 */         for (int j = 0; j < nx; j++) {
/* 194 */           x[i][j] = x[i][j] - x[k][j] * this._lu[i][k];
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 200 */     for (k = this._n - 1; k >= 0; k--) {
/* 201 */       for (int j = 0; j < nx; j++) {
/* 202 */         x[k][j] = x[k][j] / this._lu[k][k];
/*     */       }
/* 204 */       for (int i = 0; i < k; i++) {
/* 205 */         for (int m = 0; m < nx; m++) {
/* 206 */           x[i][m] = x[i][m] - x[k][m] * this._lu[i][k];
/*     */         }
/*     */       } 
/*     */     } 
/* 210 */     return xx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double det() {
/* 219 */     Check.state((this._m == this._n), "A is square");
/* 220 */     double d = this._pivsign;
/* 221 */     for (int j = 0; j < this._n; j++)
/* 222 */       d *= this._lu[j][j]; 
/* 223 */     return d;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/la/DMatrixLud.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */