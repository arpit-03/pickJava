/*     */ package edu.mines.jtk.lapack;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
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
/*     */ public class DMatrixSvd
/*     */ {
/*     */   public DMatrixSvd(DMatrix a) {
/*  41 */     double[] aa = a.getPackedColumns();
/*  42 */     this._m = a.getM();
/*  43 */     this._n = a.getN();
/*  44 */     this._mn = ArrayMath.min(this._m, this._n);
/*  45 */     this._s = new double[this._mn];
/*  46 */     this._u = new double[this._m * this._mn];
/*  47 */     this._vt = new double[this._mn * this._n];
/*  48 */     double[] work = new double[1];
/*  49 */     LapackInfo li = new LapackInfo();
/*  50 */     _lapack.dgesvd("S", "S", this._m, this._n, aa, this._m, this._s, this._u, this._m, this._vt, this._mn, work, -1, li);
/*  51 */     li.check("dgesvd");
/*  52 */     int lwork = (int)work[0];
/*  53 */     work = new double[lwork];
/*  54 */     _lapack.dgesvd("S", "S", this._m, this._n, aa, this._m, this._s, this._u, this._m, this._vt, this._mn, work, lwork, li);
/*  55 */     li.check("dgesvd");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getU() {
/*  63 */     return new DMatrix(this._m, this._mn, ArrayMath.copy(this._u));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getS() {
/*  71 */     return DMatrix.diagonal(this._s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getSingularValues() {
/*  79 */     return ArrayMath.copy(this._s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getV() {
/*  88 */     return (new DMatrix(this._mn, this._n, this._vt)).transpose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getVTranspose() {
/*  97 */     return new DMatrix(this._mn, this._n, ArrayMath.copy(this._vt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double norm2() {
/* 105 */     return this._s[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double cond() {
/* 113 */     return this._s[0] / this._s[this._mn - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int rank() {
/* 123 */     double eps = ArrayMath.ulp(1.0D);
/* 124 */     double tol = ArrayMath.max(this._m, this._n) * this._s[0] * eps;
/* 125 */     int r = 0;
/* 126 */     for (int i = 0; i < this._mn; i++) {
/* 127 */       if (this._s[i] > tol)
/* 128 */         r++; 
/*     */     } 
/* 130 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   private static final LAPACK _lapack = LAPACK.getInstance();
/*     */   int _m;
/*     */   int _n;
/*     */   int _mn;
/*     */   double[] _s;
/*     */   double[] _u;
/*     */   double[] _vt;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/lapack/DMatrixSvd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */