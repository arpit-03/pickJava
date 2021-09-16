/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AxisTics
/*     */ {
/*     */   private double _xmin;
/*     */   private double _xmax;
/*     */   private int _mtic;
/*     */   private int _ntic;
/*     */   private double _dtic;
/*     */   private double _ftic;
/*     */   private int _nticMinor;
/*     */   private double _dticMinor;
/*     */   private double _fticMinor;
/*     */   
/*     */   public AxisTics(double x1, double x2, double dtic) {
/*  58 */     double xmin = MathPlus.min(x1, x2);
/*  59 */     double xmax = MathPlus.max(x1, x2);
/*  60 */     xmin -= (xmax - xmin) * 1.1920928955078125E-7D;
/*  61 */     xmax += (xmax - xmin) * 1.1920928955078125E-7D;
/*  62 */     double d = MathPlus.abs(dtic);
/*  63 */     double f = MathPlus.ceil(xmin / d) * d;
/*     */ 
/*     */ 
/*     */     
/*  67 */     int n = 1 + (int)((xmax - f) / d);
/*  68 */     this._xmin = xmin;
/*  69 */     this._xmax = xmax;
/*  70 */     this._ntic = n;
/*  71 */     this._dtic = d;
/*  72 */     this._ftic = f;
/*  73 */     computeMultiple();
/*  74 */     computeMinorTics();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisTics(double x1, double x2, int ntic) {
/*  84 */     double xmin = this._xmin = MathPlus.min(x1, x2);
/*  85 */     double xmax = this._xmax = MathPlus.max(x1, x2);
/*  86 */     xmin -= (xmax - xmin) * 1.1920928955078125E-7D;
/*  87 */     xmax += (xmax - xmin) * 1.1920928955078125E-7D;
/*  88 */     int nmax = (ntic >= 2) ? ntic : 2;
/*  89 */     double dmax = (xmax - xmin) / (nmax - 1);
/*  90 */     int nmult = _mult.length;
/*  91 */     int nbest = 0;
/*  92 */     int mbest = 0;
/*  93 */     double dbest = 0.0D;
/*  94 */     double fbest = 0.0D;
/*  95 */     for (int imult = 0; imult < nmult; imult++) {
/*  96 */       int m = _mult[imult];
/*  97 */       int l = (int)MathPlus.floor(MathPlus.log10(dmax / m));
/*  98 */       double d = m * MathPlus.pow(10.0D, l);
/*  99 */       double f = MathPlus.ceil(xmin / d) * d;
/* 100 */       int n = 1 + (int)((xmax - f) / d);
/* 101 */       if (n > nmax) {
/* 102 */         d *= 10.0D;
/* 103 */         f = MathPlus.ceil(xmin / d) * d;
/* 104 */         n = 1 + (int)((xmax - f) / d);
/*     */       } 
/* 106 */       if (nbest < n && n <= ntic) {
/* 107 */         nbest = n;
/* 108 */         mbest = m;
/* 109 */         dbest = d;
/* 110 */         fbest = f;
/*     */       } 
/*     */     } 
/* 113 */     nbest = 1 + (int)((xmax - fbest) / dbest);
/* 114 */     if (nbest < 2) {
/* 115 */       this._ntic = 2;
/* 116 */       this._dtic = this._xmax - this._xmin;
/* 117 */       this._ftic = this._xmin;
/* 118 */       computeMultiple();
/*     */     } else {
/* 120 */       this._ntic = nbest;
/* 121 */       this._dtic = dbest;
/* 122 */       this._ftic = fbest;
/* 123 */       this._mtic = mbest;
/*     */     } 
/* 125 */     computeMinorTics();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCountMajor() {
/* 133 */     return this._ntic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDeltaMajor() {
/* 141 */     return this._dtic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFirstMajor() {
/* 149 */     return this._ftic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCountMinor() {
/* 157 */     return this._nticMinor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDeltaMinor() {
/* 165 */     return this._dticMinor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFirstMinor() {
/* 173 */     return this._fticMinor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMultiple() {
/* 183 */     return this._mtic;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 199 */   private static final int[] _mult = new int[] { 2, 5, 10 };
/*     */   
/*     */   private void computeMultiple() {
/* 202 */     this._mtic = 1;
/* 203 */     double l10 = MathPlus.log10(this._dtic / 10.0D);
/* 204 */     double l5 = MathPlus.log10(this._dtic / 5.0D);
/* 205 */     double l2 = MathPlus.log10(this._dtic / 2.0D);
/* 206 */     if (almostEqual(MathPlus.rint(l10), l10)) {
/* 207 */       this._mtic = 10;
/* 208 */     } else if (almostEqual(MathPlus.rint(l5), l5)) {
/* 209 */       this._mtic = 5;
/* 210 */     } else if (almostEqual(MathPlus.rint(l2), l2)) {
/* 211 */       this._mtic = 2;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void computeMinorTics() {
/* 216 */     double dm = this._dtic / this._mtic;
/* 217 */     double fm = this._ftic;
/* 218 */     while (this._xmin <= fm - dm)
/* 219 */       fm -= dm; 
/* 220 */     int nm = 1 + (int)((this._xmax - fm) / dm);
/* 221 */     this._nticMinor = nm;
/* 222 */     this._dticMinor = dm;
/* 223 */     this._fticMinor = fm;
/*     */   }
/*     */   
/*     */   private static boolean almostEqual(double x1, double x2) {
/* 227 */     return (MathPlus.abs(x1 - x2) <= MathPlus.max(MathPlus.abs(x1), MathPlus.abs(x2)) * 100.0D * 2.220446049250313E-16D);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/AxisTics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */