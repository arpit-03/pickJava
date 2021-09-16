/*     */ package edu.mines.jtk.lapack;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import org.netlib.lapack.LAPACK;
/*     */ import org.netlib.util.intW;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DMatrixEvd
/*     */ {
/*     */   public DMatrixEvd(DMatrix a) {
/*  44 */     Check.argument(a.isSquare(), "A is square");
/*  45 */     this._n = a.getN();
/*  46 */     this._v = new double[this._n * this._n];
/*  47 */     this._d = new double[this._n];
/*  48 */     this._e = new double[this._n];
/*  49 */     double[] aa = a.getPackedColumns();
/*  50 */     LapackInfo li = new LapackInfo();
/*  51 */     intW mW = new intW(0);
/*  52 */     if (a.isSymmetric()) {
/*  53 */       int[] isuppz = new int[2 * this._n];
/*  54 */       double[] work = new double[1];
/*  55 */       int[] iwork = new int[1];
/*  56 */       _lapack.dsyevr("V", "A", "L", this._n, aa, this._n, 0.0D, 0.0D, 0, 0, 0.0D, mW, this._d, this._v, this._n, isuppz, work, -1, iwork, -1, li);
/*     */ 
/*     */       
/*  59 */       if (li.get("dsyevr") > 0)
/*  60 */         throw new RuntimeException("internal error in LAPACK dsyevr"); 
/*  61 */       int lwork = (int)work[0];
/*  62 */       work = new double[lwork];
/*  63 */       int liwork = iwork[0];
/*  64 */       iwork = new int[liwork];
/*  65 */       _lapack.dsyevr("V", "A", "L", this._n, aa, this._n, 0.0D, 0.0D, 0, 0, 0.0D, mW, this._d, this._v, this._n, isuppz, work, lwork, iwork, liwork, li);
/*     */ 
/*     */       
/*  68 */       if (li.get("dsyevr") > 0)
/*  69 */         throw new RuntimeException("internal error in LAPACK dsyevr"); 
/*     */     } else {
/*  71 */       double[] work = new double[1];
/*  72 */       _lapack.dgeev("N", "V", this._n, aa, this._n, this._d, this._e, this._v, this._n, this._v, this._n, work, -1, li);
/*  73 */       li.check("dgeev");
/*  74 */       int lwork = (int)work[0];
/*  75 */       work = new double[lwork];
/*  76 */       _lapack.dgeev("N", "V", this._n, aa, this._n, this._d, this._e, this._v, this._n, this._v, this._n, work, lwork, li);
/*  77 */       if (li.get("dgeev") > 0) {
/*  78 */         throw new RuntimeException("LAPACK dgeev failed to converge");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getV() {
/*  87 */     return new DMatrix(this._n, this._n, this._v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getD() {
/*  95 */     double[] d = new double[this._n * this._n];
/*  96 */     for (int i = 0; i < this._n; i++) {
/*  97 */       for (int j = 0; j < this._n; j++) {
/*  98 */         d[i + j * this._n] = 0.0D;
/*     */       }
/* 100 */       d[i + i * this._n] = this._d[i];
/* 101 */       if (this._e[i] > 0.0D) {
/* 102 */         d[i + (i + 1) * this._n] = this._e[i];
/* 103 */       } else if (this._e[i] < 0.0D) {
/* 104 */         d[i + (i - 1) * this._n] = this._e[i];
/*     */       } 
/*     */     } 
/* 107 */     return new DMatrix(this._n, this._n, d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getRealEigenvalues() {
/* 115 */     return ArrayMath.copy(this._d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getImagEigenvalues() {
/* 123 */     return ArrayMath.copy(this._e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   private static final LAPACK _lapack = LAPACK.getInstance();
/*     */   private int _n;
/*     */   private double[] _v;
/*     */   private double[] _d;
/*     */   private double[] _e;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/lapack/DMatrixEvd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */