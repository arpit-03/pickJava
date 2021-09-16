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
/*     */ public class LogAxisTics
/*     */   extends AxisTics
/*     */ {
/*     */   private int _mtic;
/*     */   private int _ktic;
/*     */   private int _ntic;
/*     */   private double _dtic;
/*     */   private double _ftic;
/*     */   private int _nticMinor;
/*     */   private double _dticMinor;
/*     */   private double _fticMinor;
/*     */   private double _expMin;
/*     */   private double _expMax;
/*     */   
/*     */   public LogAxisTics(double x1, double x2) {
/*  45 */     super(x1, x2, 0);
/*  46 */     double xmin = Math.min(x1, x2);
/*  47 */     double xmax = Math.max(x1, x2);
/*  48 */     this._expMin = MathPlus.log10(xmin);
/*  49 */     this._expMax = MathPlus.log10(xmax);
/*  50 */     this._dtic = 1.0D;
/*  51 */     this._ftic = MathPlus.pow(10.0D, Math.ceil(this._expMin));
/*  52 */     this._ntic = (int)(Math.floor(ArrayMath.log10(xmax)) - Math.ceil(ArrayMath.log10(xmin))) + 1;
/*  53 */     computeMultiple();
/*  54 */     computeMinorTics();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCountMajor() {
/*  62 */     return this._ntic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDeltaMajor() {
/*  70 */     return this._dtic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFirstMajor() {
/*  78 */     return this._ftic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCountMinor() {
/*  86 */     return this._nticMinor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDeltaMinor() {
/*  94 */     return this._dticMinor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFirstMinor() {
/* 102 */     return this._fticMinor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMultiple() {
/* 112 */     return this._mtic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFirstMinorSkip() {
/* 121 */     return this._ktic;
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void computeMultiple() {
/* 141 */     this._mtic = 9;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void computeMinorTics() {
/* 147 */     double c = MathPlus.pow(10.0D, this._expMin - Math.ceil(this._expMin) + 1.0D);
/*     */ 
/*     */     
/* 150 */     int c2 = 9 - (int)Math.floor(c);
/*     */ 
/*     */     
/* 153 */     double d = MathPlus.pow(10.0D, this._expMax - Math.floor(this._expMax));
/*     */ 
/*     */     
/* 156 */     int d2 = (int)Math.floor(d);
/*     */     
/* 158 */     this._nticMinor = 9 * (this._ntic - 1) + c2 + d2;
/* 159 */     this._dticMinor = 1.0D;
/* 160 */     this._fticMinor = MathPlus.pow(10.0D, Math.ceil(this._expMin) - 1.0D) * (int)Math.ceil(c);
/* 161 */     this._ktic = c2 + 1;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/LogAxisTics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */