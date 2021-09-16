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
/*     */ public class DMatrixChd
/*     */ {
/*     */   public DMatrixChd(DMatrix a) {
/*  38 */     Check.argument(a.isSquare(), "A is square");
/*  39 */     this._n = a.getN();
/*  40 */     this._l = a.getPackedColumns();
/*     */ 
/*     */     
/*  43 */     for (int j = 0; j < this._n; j++) {
/*  44 */       for (int k = 0; k < j; k++) {
/*  45 */         this._l[k + j * this._n] = 0.0D;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  50 */     LapackInfo li = new LapackInfo();
/*  51 */     _lapack.dpotrf("L", this._n, this._l, this._n, li);
/*  52 */     int info = li.get("dpotrf");
/*     */     
/*  54 */     this._pd = (info == 0);
/*     */     
/*  56 */     this._det = 1.0D;
/*  57 */     for (int i = 0; i < this._n; i++)
/*  58 */       this._det *= this._l[i + i * this._n]; 
/*  59 */     this._det *= this._det;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPositiveDefinite() {
/*  70 */     return this._pd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DMatrix getL() {
/*  78 */     return new DMatrix(this._n, this._n, ArrayMath.copy(this._l));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double det() {
/*  86 */     return this._det;
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
/*  97 */     Check.argument((this._n == b.getM()), "A and B have same number of rows");
/*  98 */     Check.state(this._pd, "A is positive-definite");
/*  99 */     int n = this._n;
/* 100 */     int nrhs = b.getN();
/* 101 */     double[] aa = this._l;
/* 102 */     int lda = this._n;
/* 103 */     double[] ba = b.getPackedColumns();
/* 104 */     int ldb = this._n;
/* 105 */     LapackInfo li = new LapackInfo();
/* 106 */     _lapack.dpotrs("L", n, nrhs, aa, lda, ba, ldb, li);
/* 107 */     li.check("dpotrs");
/* 108 */     return new DMatrix(this._n, nrhs, ba);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   private static final LAPACK _lapack = LAPACK.getInstance();
/*     */   private int _n;
/*     */   private double[] _l;
/*     */   private double _det;
/*     */   private boolean _pd;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/lapack/DMatrixChd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */