/*     */ package edu.mines.jtk.lapack;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import org.netlib.blas.BLAS;
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
/*     */ public class DMatrixQrd
/*     */ {
/*     */   public DMatrixQrd(DMatrix a) {
/*  42 */     Check.argument((a.getM() >= a.getN()), "m >= n");
/*  43 */     this._m = a.getM();
/*  44 */     this._n = a.getN();
/*  45 */     this._k = ArrayMath.min(this._m, this._n);
/*  46 */     this._qr = a.getPackedColumns();
/*  47 */     this._tau = new double[this._k];
/*  48 */     this._work = new double[1];
/*  49 */     LapackInfo li = new LapackInfo();
/*  50 */     _lapack.dgeqrf(this._m, this._n, this._qr, this._m, this._tau, this._work, -1, li);
/*  51 */     li.check("dgeqrf");
/*  52 */     this._lwork = (int)this._work[0];
/*  53 */     this._work = new double[this._lwork];
/*  54 */     _lapack.dgeqrf(this._m, this._n, this._qr, this._m, this._tau, this._work, this._lwork, li);
/*  55 */     li.check("dgeqrf");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullRank() {
/*  63 */     for (int j = 0; j < this._n; j++) {
/*  64 */       if (this._qr[j + j * this._m] == 0.0D)
/*  65 */         return false; 
/*     */     } 
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getQ() {
/*  75 */     double[] q = ArrayMath.copy(this._qr);
/*  76 */     LapackInfo li = new LapackInfo();
/*  77 */     _lapack.dorgqr(this._m, this._n, this._k, q, this._m, this._tau, this._work, this._lwork, li);
/*  78 */     li.check("dorgqr");
/*  79 */     return new DMatrix(this._m, this._n, q);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getR() {
/*  87 */     double[] r = new double[this._n * this._n];
/*  88 */     for (int j = 0; j < this._n; j++) {
/*  89 */       for (int i = 0; i <= j; i++)
/*  90 */         r[i + j * this._n] = this._qr[i + j * this._m]; 
/*  91 */     }  return new DMatrix(this._n, this._n, r);
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
/* 104 */     Check.argument((b.getM() == this._m), "A and B have the same number of rows");
/* 105 */     Check.state(isFullRank(), "A is of full rank");
/*     */ 
/*     */ 
/*     */     
/* 109 */     int nrhs = b.getN();
/* 110 */     DMatrix c = new DMatrix(b);
/* 111 */     double[] ca = c.getArray();
/* 112 */     double[] work = new double[1];
/* 113 */     LapackInfo li = new LapackInfo();
/* 114 */     _lapack.dormqr("L", "T", this._m, nrhs, this._k, this._qr, this._m, this._tau, ca, this._m, work, -1, li);
/* 115 */     li.check("dormqr");
/* 116 */     int lwork = (int)work[0];
/* 117 */     work = new double[lwork];
/* 118 */     _lapack.dormqr("L", "T", this._m, nrhs, this._k, this._qr, this._m, this._tau, ca, this._m, work, lwork, li);
/* 119 */     li.check("dormqr");
/*     */ 
/*     */     
/* 122 */     _blas.dtrsm("L", "U", "N", "N", this._n, nrhs, 1.0D, this._qr, this._m, ca, this._m);
/*     */ 
/*     */     
/* 125 */     DMatrix x = c.get(0, this._n - 1, (int[])null);
/* 126 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   private static final BLAS _blas = BLAS.getInstance();
/* 133 */   private static final LAPACK _lapack = LAPACK.getInstance();
/*     */   int _m;
/*     */   int _n;
/*     */   int _k;
/*     */   double[] _qr;
/*     */   double[] _tau;
/*     */   double[] _work;
/*     */   int _lwork;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/lapack/DMatrixQrd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */