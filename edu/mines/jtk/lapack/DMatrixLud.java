/*     */ package edu.mines.jtk.lapack;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import org.netlib.lapack.LAPACK;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public DMatrixLud(DMatrix a) {
/*  45 */     this._m = a.getM();
/*  46 */     this._n = a.getN();
/*  47 */     this._lu = a.getPackedColumns();
/*  48 */     this._npiv = Math.min(this._m, this._n);
/*  49 */     this._ipiv = new int[this._npiv];
/*  50 */     LapackInfo li = new LapackInfo();
/*  51 */     _lapack.dgetrf(this._m, this._n, this._lu, this._m, this._ipiv, li);
/*  52 */     int info = li.get("dgetrf");
/*  53 */     this._p = new int[this._m]; int i;
/*  54 */     for (i = 0; i < this._m; i++)
/*  55 */       this._p[i] = i; 
/*  56 */     this._det = 1.0D;
/*  57 */     for (i = 0; i < this._m; i++) {
/*  58 */       if (i < this._npiv) {
/*  59 */         int j = this._ipiv[i] - 1;
/*  60 */         this._det *= this._lu[i + i * this._m];
/*  61 */         if (j != i) {
/*  62 */           int pi = this._p[i];
/*  63 */           this._p[i] = this._p[j];
/*  64 */           this._p[j] = pi;
/*  65 */           this._det = -this._det;
/*     */         } 
/*     */       } 
/*     */     } 
/*  69 */     this._singular = (info > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSingular() {
/*  78 */     return this._singular;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getL() {
/*  87 */     int m = this._m;
/*  88 */     int n = Math.min(this._m, this._n);
/*  89 */     double[] l = new double[m * n];
/*  90 */     for (int j = 0; j < n; j++) {
/*  91 */       l[j + j * m] = 1.0D;
/*  92 */       for (int i = j + 1; i < m; i++) {
/*  93 */         l[i + j * m] = this._lu[i + j * this._m];
/*     */       }
/*     */     } 
/*  96 */     return new DMatrix(m, n, l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getU() {
/* 105 */     int m = Math.min(this._m, this._n);
/* 106 */     int n = this._n;
/* 107 */     double[] u = new double[m * n];
/* 108 */     for (int j = 0; j < n; j++) {
/* 109 */       int imax = Math.min(m - 1, j);
/* 110 */       for (int i = 0; i <= imax; i++) {
/* 111 */         u[i + j * m] = this._lu[i + j * this._m];
/*     */       }
/*     */     } 
/* 114 */     return new DMatrix(m, n, u);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getP() {
/* 123 */     int m = this._m;
/* 124 */     int n = this._m;
/* 125 */     double[] p = new double[m * n];
/* 126 */     for (int i = 0; i < m; i++) {
/* 127 */       p[this._p[i] + i * m] = 1.0D;
/*     */     }
/* 129 */     return new DMatrix(m, n, p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getPivotIndices() {
/* 139 */     return ArrayMath.copy(this._p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double det() {
/* 148 */     Check.argument((this._m == this._n), "A is square");
/* 149 */     return this._det;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix solve(DMatrix b) {
/* 160 */     Check.argument((this._m == this._n), "A is square");
/* 161 */     Check.argument((this._m == b.getM()), "A and B have same number of rows");
/* 162 */     Check.state(!this._singular, "A is not singular");
/* 163 */     int n = this._n;
/* 164 */     int nrhs = b.getN();
/* 165 */     double[] aa = this._lu;
/* 166 */     int lda = this._m;
/* 167 */     int[] ipiv = this._ipiv;
/* 168 */     double[] ba = b.getPackedColumns();
/* 169 */     int ldb = this._m;
/* 170 */     LapackInfo li = new LapackInfo();
/* 171 */     _lapack.dgetrs("N", n, nrhs, aa, lda, ipiv, ba, ldb, li);
/* 172 */     li.check("dgetrs");
/* 173 */     return new DMatrix(this._m, nrhs, ba);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 179 */   private static final LAPACK _lapack = LAPACK.getInstance();
/*     */   private int _m;
/*     */   private int _n;
/*     */   private double[] _lu;
/*     */   private int _npiv;
/*     */   private int[] _ipiv;
/*     */   private int[] _p;
/*     */   private double _det;
/*     */   private boolean _singular;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/lapack/DMatrixLud.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */