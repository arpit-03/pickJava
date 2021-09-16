/*      */ package edu.mines.jtk.dsp;
/*      */ 
/*      */ import edu.mines.jtk.util.ArrayMath;
/*      */ import edu.mines.jtk.util.Check;
/*      */ import edu.mines.jtk.util.Parallel;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DynamicWarping
/*      */ {
/*      */   private int _nl;
/*      */   private int _lmin;
/*      */   private int _lmax;
/*      */   private ErrorExtrapolation _extrap;
/*      */   private float _epow;
/*      */   private int _esmooth;
/*      */   private double _usmooth1;
/*      */   private double _usmooth2;
/*      */   private double _usmooth3;
/*      */   private int _bstrain1;
/*      */   private int _bstrain2;
/*      */   private int _bstrain3;
/*      */   private RecursiveExponentialFilter _ref1;
/*      */   private RecursiveExponentialFilter _ref2;
/*      */   private RecursiveExponentialFilter _ref3;
/*      */   private SincInterpolator _si;
/*      */   private int _owl2;
/*      */   private int _owl3;
/*      */   private double _owf2;
/*      */   private double _owf3;
/*      */   
/*      */   public enum ErrorExtrapolation
/*      */   {
/*   87 */     NEAREST,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   92 */     AVERAGE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   97 */     REFLECT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DynamicWarping(int shiftMin, int shiftMax) {
/* 1104 */     this._epow = 2.0F;
/* 1105 */     this._esmooth = 0;
/* 1106 */     this._usmooth1 = 0.0D;
/* 1107 */     this._usmooth2 = 0.0D;
/* 1108 */     this._usmooth3 = 0.0D;
/* 1109 */     this._bstrain1 = 1;
/* 1110 */     this._bstrain2 = 1;
/* 1111 */     this._bstrain3 = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1116 */     this._owl2 = 50;
/* 1117 */     this._owl3 = 50;
/* 1118 */     this._owf2 = 0.5D;
/* 1119 */     this._owf3 = 0.5D; Check.argument((shiftMax - shiftMin > 1), "shiftMax-shiftMin>1"); this._lmin = shiftMin; this._lmax = shiftMax; this._nl = 1 + this._lmax - this._lmin; this._si = new SincInterpolator(); this._extrap = ErrorExtrapolation.NEAREST;
/*      */   } public void setStrainMax(double strainMax) { Check.argument((strainMax <= 1.0D), "strainMax<=1.0"); Check.argument((strainMax > 0.0D), "strainMax>0.0"); setStrainMax(strainMax, strainMax); } public void setStrainMax(double strainMax1, double strainMax2) { Check.argument((strainMax1 <= 1.0D), "strainMax1<=1.0"); Check.argument((strainMax2 <= 1.0D), "strainMax2<=1.0"); Check.argument((strainMax1 > 0.0D), "strainMax1>0.0"); Check.argument((strainMax2 > 0.0D), "strainMax2>0.0"); setStrainMax(strainMax1, strainMax2, strainMax2); } public void setStrainMax(double strainMax1, double strainMax2, double strainMax3) { Check.argument((strainMax1 <= 1.0D), "strainMax1<=1.0"); Check.argument((strainMax2 <= 1.0D), "strainMax2<=1.0"); Check.argument((strainMax3 <= 1.0D), "strainMax3<=1.0"); Check.argument((strainMax1 > 0.0D), "strainMax1>0.0"); Check.argument((strainMax2 > 0.0D), "strainMax2>0.0"); Check.argument((strainMax3 > 0.0D), "strainMax3>0.0"); this._bstrain1 = (int)ArrayMath.ceil(1.0D / strainMax1); this._bstrain2 = (int)ArrayMath.ceil(1.0D / strainMax2); this._bstrain3 = (int)ArrayMath.ceil(1.0D / strainMax3); updateSmoothingFilters(); } public void setErrorExtrapolation(ErrorExtrapolation ee) { this._extrap = ee; } public void setErrorExponent(double e) { this._epow = (float)e; } public void setErrorSmoothing(int esmooth) { this._esmooth = esmooth; } public void setShiftSmoothing(double usmooth) { setShiftSmoothing(usmooth, usmooth); } public void setShiftSmoothing(double usmooth1, double usmooth2) { setShiftSmoothing(usmooth1, usmooth2, usmooth2); } public void setShiftSmoothing(double usmooth1, double usmooth2, double usmooth3) { this._usmooth1 = usmooth1; this._usmooth2 = usmooth2; this._usmooth3 = usmooth3; updateSmoothingFilters(); } public void setWindowSizeAndOverlap(int l2, int l3, double f2, double f3) { this._owl2 = l2; this._owl3 = l3; this._owf2 = f2; this._owf3 = f3; } public float[] findShifts(float[] f, float[] g) { float[] u = like(f); findShifts(f, g, u); return u; } public float[][] findShifts(float[][] f, float[][] g) { float[][] u = like(f); findShifts(f, g, u); return u; } public float[][][] findShifts(float[][][] f, float[][][] g) { float[][][] u = like(f); findShifts(f, g, u); return u; } public float[] findShifts1(float[][] f, float[][] g) { float[] u = like(f[0]); findShifts1(f, g, u); return u; } public float[] findShifts1(float[][][] f, float[][][] g) { float[] u = like(f[0][0]); findShifts1(f, g, u); return u; } public void findShifts(float[] f, float[] g, float[] u) { float[][] e = computeErrors(f, g); for (int is = 0; is < this._esmooth; is++) smoothErrors(e, e);  float[][] d = accumulateForward(e); backtrackReverse(d, e, u); smoothShifts(u, u); } public void findShifts(float[][] f, float[][] g, float[][] u) { final float[][][] e = computeErrors(f, g); final int nl = (e[0][0]).length; final int n1 = (e[0]).length; int n2 = e.length; final float[][] uf = u; for (int is = 0; is < this._esmooth; is++) smoothErrors(e, e);  final Parallel.Unsafe<float[][]> du = new Parallel.Unsafe(); Parallel.loop(n2, new Parallel.LoopInt() { public void compute(int i2) { float[][] d = (float[][])du.get(); if (d == null) du.set(d = new float[n1][nl]);  DynamicWarping.this.accumulateForward(e[i2], d); DynamicWarping.this.backtrackReverse(d, e[i2], uf[i2]); } }
/*      */       ); smoothShifts(u, u); } public void findShifts(float[][][] f, float[][][] g, float[][][] u) { int n1 = (f[0][0]).length; int n2 = (f[0]).length; int n3 = f.length; OverlappingWindows2 ow = new OverlappingWindows2(n2, n3, this._owl2, this._owl3, this._owf2, this._owf3); int m2 = ow.getM1(); int m3 = ow.getM2(); int l2 = ow.getL1(); int l3 = ow.getL2(); float[][][] fw = new float[l3][l2][]; float[][][] gw = new float[l3][l2][]; float[][][] uw = new float[l3][l2][n1]; float[][][][] ew = new float[l3][l2][n1][this._nl]; for (int k3 = 0; k3 < m3; k3++) { int i3 = ow.getI2(k3); for (int k2 = 0; k2 < m2; k2++) { int i2 = ow.getI1(k2); for (int i = 0; i < l3; i++) { for (int j2 = 0; j2 < l2; j2++) { fw[i][j2] = f[i3 + i][i2 + j2]; gw[i][j2] = g[i3 + i][i2 + j2]; }  }  computeErrors(fw, gw, ew); normalizeErrors(ew); for (int is = 0; is < this._esmooth; is++) smoothErrors(ew);  computeShifts(ew, uw); for (int j3 = 0; j3 < l3; j3++) { for (int j2 = 0; j2 < l2; j2++) { float wij = ow.getWeight(i2, i3, j2, j3); float[] u32 = u[i3 + j3][i2 + j2]; for (int i1 = 0; i1 < n1; i1++) u32[i1] = u32[i1] + wij * uw[j3][j2][i1];  }  }  }  }  smoothShifts(u); } public void findShifts1(float[][] f, float[][] g, float[] u) { float[][] e = computeErrors1(f, g); for (int is = 0; is < this._esmooth; is++) smoothErrors(e, e);  float[][] d = accumulateForward(e); backtrackReverse(d, e, u); smoothShifts(u, u); } public void findShifts1(float[][][] f, float[][][] g, float[] u) { float[][] e = computeErrors1(f, g); for (int is = 0; is < this._esmooth; is++) smoothErrors(e, e);  float[][] d = accumulateForward(e); backtrackReverse(d, e, u); smoothShifts(u, u); } public float[] applyShifts(float[] u, float[] g) { float[] h = like(g); applyShifts(u, g, h); return h; } public float[][] applyShifts(float[][] u, float[][] g) { float[][] h = like(g); applyShifts(u, g, h); return h; } public float[][][] applyShifts(float[][][] u, float[][][] g) { float[][][] h = like(g); applyShifts(u, g, h); return h; } public void applyShifts(float[] u, float[] g, float[] h) { int n1 = u.length; for (int i1 = 0; i1 < n1; i1++) h[i1] = this._si.interpolate(n1, 1.0D, 0.0D, g, (i1 + u[i1]));  } public void applyShifts(float[][] u, float[][] g, float[][] h) { final int n1 = (u[0]).length; int n2 = u.length; final float[][] uf = u; final float[][] gf = g; final float[][] hf = h; Parallel.loop(n2, new Parallel.LoopInt() { public void compute(int i2) { for (int i1 = 0; i1 < n1; i1++) hf[i2][i1] = DynamicWarping.this._si.interpolate(n1, 1.0D, 0.0D, gf[i2], (i1 + uf[i2][i1]));  } }
/* 1122 */       ); } public void applyShifts(float[][][] u, float[][][] g, float[][][] h) { int n3 = u.length; final float[][][] uf = u; final float[][][] gf = g; final float[][][] hf = h; Parallel.loop(n3, new Parallel.LoopInt() { public void compute(int i3) { DynamicWarping.this.applyShifts(uf[i3], gf[i3], hf[i3]); } }); } public float[][] computeErrors(float[] f, float[] g) { int n1 = f.length; float[][] e = new float[n1][this._nl]; computeErrors(f, g, e); normalizeErrors(e); return e; } public float[][][] computeErrors(float[][] f, float[][] g) { int n1 = (f[0]).length; int n2 = f.length; final float[][] ff = f; final float[][] gf = g; final float[][][] ef = new float[n2][n1][this._nl]; Parallel.loop(n2, new Parallel.LoopInt() { public void compute(int i2) { DynamicWarping.this.computeErrors(ff[i2], gf[i2], ef[i2]); } }); normalizeErrors(ef); return ef; } public float[][] computeErrors1(float[][] f, float[][] g) { final float[][] ff = f; final float[][] gf = g; final int nl = 1 + this._lmax - this._lmin; final int n1 = (f[0]).length; int n2 = f.length; float[][] e = (float[][])Parallel.reduce(n2, new Parallel.ReduceInt<float[][]>() { public float[][] compute(int i2) { float[][] e = new float[n1][nl]; DynamicWarping.this.computeErrors(ff[i2], gf[i2], e); return e; } public float[][] combine(float[][] ea, float[][] eb) { return ArrayMath.add(ea, eb); } }); normalizeErrors(e); return e; } public float[][] computeErrors1(float[][][] f, float[][][] g) { final float[][][] ff = f; final float[][][] gf = g; final int nl = 1 + this._lmax - this._lmin; final int n1 = (f[0][0]).length; final int n2 = (f[0]).length; int n3 = f.length; float[][] e = (float[][])Parallel.reduce(n2 * n3, new Parallel.ReduceInt<float[][]>() { public float[][] compute(int i23) { int i2 = i23 % n2; int i3 = i23 / n2; float[][] e = new float[n1][nl]; DynamicWarping.this.computeErrors(ff[i3][i2], gf[i3][i2], e); return e; } public float[][] combine(float[][] ea, float[][] eb) { return ArrayMath.add(ea, eb); } }); normalizeErrors(e); return e; } public float[][] smoothErrors(float[][] e) { float[][] es = like(e); smoothErrors(e, es); return es; } private float error(float f, float g) { return ArrayMath.pow(ArrayMath.abs(f - g), this._epow); }
/*      */   public float[][][] smoothErrors(float[][][] e) { float[][][] es = like(e); smoothErrors(e, es); return es; }
/*      */   public void smoothErrors(float[][] e, float[][] es) { smoothErrors1(this._bstrain1, e, es); normalizeErrors(es); } public void smoothErrors(float[][][] e, float[][][] es) { smoothErrors1(this._bstrain1, e, es); normalizeErrors(es); smoothErrors2(this._bstrain2, es, es); normalizeErrors(es); } public void smoothErrors1(float[][][] e, float[][][] es) { smoothErrors1(this._bstrain1, e, es); normalizeErrors(es); } public float[] smoothShifts(float[] u) { float[] us = like(u); smoothShifts(u, us); return us; } public float[][] smoothShifts(float[][] u) { float[][] us = like(u); smoothShifts(u, us); return us; } public void smoothShifts(float[] u, float[] us) { if (this._ref1 != null) { this._ref1.apply(u, us); } else if (u != us) { ArrayMath.copy(u, us); }  } public void smoothShifts(float[][] u, float[][] us) { if (this._ref1 != null) { this._ref1.apply1(u, us); } else { ArrayMath.copy(u, us); }  if (this._ref2 != null) this._ref2.apply2(us, us);  } public float[][] accumulateForward(float[][] e) { float[][] d = like(e); accumulateForward(e, d); return d; } public float[][] accumulateReverse(float[][] e) { float[][] d = like(e); accumulateReverse(e, d); return d; } public float[][][] accumulateForward1(float[][][] e) { float[][][] d = like(e); accumulateForward1(e, d); return d; } public float[][][] accumulateReverse1(float[][][] e) { float[][][] d = like(e); accumulateReverse1(e, d); return d; } public float[][][] accumulateForward2(float[][][] e) { float[][][] d = like(e); accumulateForward2(e, d); return d; } public float[][][] accumulateReverse2(float[][][] e) { float[][][] d = like(e); accumulateReverse2(e, d); return d; } public void accumulateForward(float[][] e, float[][] d) { accumulate(1, this._bstrain1, e, d); } public void accumulateReverse(float[][] e, float[][] d) { accumulate(-1, this._bstrain1, e, d); } public void accumulateForward1(float[][][] e, float[][][] d) { int n2 = e.length; for (int i2 = 0; i2 < n2; i2++) accumulateForward(e[i2], d[i2]);  } public void accumulateReverse1(float[][][] e, float[][][] d) { int n2 = e.length; for (int i2 = 0; i2 < n2; i2++) accumulateReverse(e[i2], d[i2]);  } public void accumulateForward2(float[][][] e, float[][][] d) { int n1 = (e[0]).length; int n2 = e.length; float[][] ei1 = new float[n2][]; float[][] di1 = new float[n2][]; for (int i1 = 0; i1 < n1; i1++) { for (int i2 = 0; i2 < n2; i2++) { ei1[i2] = e[i2][i1]; di1[i2] = d[i2][i1]; }  accumulate(1, this._bstrain2, ei1, di1); }  } public void accumulateReverse2(float[][][] e, float[][][] d) { int n1 = (e[0]).length; int n2 = e.length; float[][] ei1 = new float[n2][]; float[][] di1 = new float[n2][]; for (int i1 = 0; i1 < n1; i1++) { for (int i2 = 0; i2 < n2; i2++) { ei1[i2] = e[i2][i1]; di1[i2] = d[i2][i1]; }  accumulate(-1, this._bstrain2, ei1, di1); }  } public float[] backtrackReverse(float[][] d, float[][] e) { float[] u = new float[d.length]; backtrackReverse(d, e, u); return u; } public float[][] backtrackReverse1(float[][][] d, float[][][] e) { float[][] u = new float[d.length][(d[0]).length]; backtrackReverse1(d, e, u); return u; } public float[][] backtrackReverse2(float[][][] d, float[][][] e) { float[][] u = new float[d.length][(d[0]).length]; backtrackReverse2(d, e, u); return u; } public void backtrackReverse(float[][] d, float[][] e, float[] u) { backtrack(-1, this._bstrain1, this._lmin, d, e, u); } public void backtrackReverse1(float[][][] d, float[][][] e, float[][] u) { int n2 = d.length; for (int i2 = 0; i2 < n2; i2++) backtrackReverse(d[i2], e[i2], u[i2]);  } public void backtrackReverse2(float[][][] d, float[][][] e, float[][] u) { int n1 = (d[0]).length; int n2 = d.length; float[][] di1 = new float[n2][]; float[][] ei1 = new float[n2][]; float[] ui1 = new float[n2]; for (int i1 = 0; i1 < n1; i1++) { int i2; for (i2 = 0; i2 < n2; i2++) { di1[i2] = d[i2][i1]; ei1[i2] = e[i2][i1]; }  backtrack(-1, this._bstrain2, this._lmin, di1, ei1, ui1); for (i2 = 0; i2 < n2; i2++) u[i2][i1] = ui1[i2];  }  } public static void normalizeErrors(float[][] e) { int nl = (e[0]).length; int n1 = e.length; float emin = e[0][0]; float emax = e[0][0]; for (int i1 = 0; i1 < n1; i1++) { for (int il = 0; il < nl; il++) { float ei = e[i1][il]; if (ei < emin) emin = ei;  if (ei > emax) emax = ei;  }  }  shiftAndScale(emin, emax, e); } public static void normalizeErrors(float[][][] e) { final float[][][] ef = e; int n2 = e.length; MinMax mm = (MinMax)Parallel.reduce(n2, new Parallel.ReduceInt<MinMax>() {
/*      */           public DynamicWarping.MinMax compute(int i2) { int nl = (ef[i2][0]).length; int n1 = (ef[i2]).length; float emin = Float.MAX_VALUE; float emax = -3.4028235E38F; for (int i1 = 0; i1 < n1; i1++) { for (int il = 0; il < nl; il++) { float ei = ef[i2][i1][il]; if (ei < emin) emin = ei;  if (ei > emax) emax = ei;  }  }  return new DynamicWarping.MinMax(emin, emax); } public DynamicWarping.MinMax combine(DynamicWarping.MinMax mm1, DynamicWarping.MinMax mm2) { return new DynamicWarping.MinMax(ArrayMath.min(mm1.emin, mm2.emin), ArrayMath.max(mm1.emax, mm2.emax)); }
/* 1126 */         }); shiftAndScale(mm.emin, mm.emax, e); } public float sumErrors(float[][] e, float[] u) { int n1 = e.length; int nl = (e[0]).length; float ul = 0.5F - this._lmin; double sum = 0.0D; for (int i1 = 0; i1 < n1; i1++) { int il = (int)(u[i1] + ul); il = ArrayMath.max(0, ArrayMath.min(nl - 1, il)); sum += e[i1][il]; }  return (float)sum; } public float sumErrors(float[][][] e, float[][] u) { int n2 = e.length; double sum = 0.0D; for (int i2 = 0; i2 < n2; i2++) sum += sumErrors(e[i2], u[i2]);  return (float)sum; } public static float[][] transposeLag(float[][] e) { int nl = (e[0]).length; int n1 = e.length; float[][] t = new float[nl][n1]; for (int il = 0; il < nl; il++) { for (int i1 = 0; i1 < n1; i1++) t[il][i1] = e[i1][il];  }  return t; } public static float[][][] transposeLag(float[][][] e) { int nl = (e[0][0]).length; int n1 = (e[0]).length; int n2 = e.length; float[][][] t = new float[nl][n2][n1]; for (int il = 0; il < nl; il++) { for (int i2 = 0; i2 < n2; i2++) { for (int i1 = 0; i1 < n1; i1++) t[il][i2][i1] = e[i2][i1][il];  }  }  return t; } private void updateSmoothingFilters() { this._ref1 = (this._usmooth1 <= 0.0D) ? null : new RecursiveExponentialFilter(this._usmooth1 * this._bstrain1);
/*      */     
/* 1128 */     this._ref2 = (this._usmooth2 <= 0.0D) ? null : new RecursiveExponentialFilter(this._usmooth2 * this._bstrain2);
/*      */     
/* 1130 */     this._ref3 = (this._usmooth3 <= 0.0D) ? null : new RecursiveExponentialFilter(this._usmooth3 * this._bstrain3); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void computeErrors(float[] f, float[] g, float[][] e) {
/* 1141 */     int n1 = f.length;
/* 1142 */     int nl = this._nl;
/* 1143 */     int n1m = n1 - 1;
/* 1144 */     boolean average = (this._extrap == ErrorExtrapolation.AVERAGE);
/* 1145 */     boolean nearest = (this._extrap == ErrorExtrapolation.NEAREST);
/* 1146 */     boolean reflect = (this._extrap == ErrorExtrapolation.REFLECT);
/* 1147 */     float[] eavg = average ? new float[nl] : null;
/* 1148 */     int[] navg = average ? new int[nl] : null;
/* 1149 */     float emax = 0.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int i1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1163 */     for (i1 = 0; i1 < n1; i1++) {
/* 1164 */       int illo = ArrayMath.max(0, -this._lmin - i1);
/* 1165 */       int ilhi = ArrayMath.min(nl, n1 - this._lmin - i1);
/* 1166 */       for (int il = illo, j1 = i1 + il + this._lmin; il < ilhi; il++, j1++) {
/* 1167 */         float ei = error(f[i1], g[j1]);
/* 1168 */         e[i1][il] = ei;
/* 1169 */         if (average) {
/* 1170 */           eavg[il] = eavg[il] + ei;
/* 1171 */           navg[il] = navg[il] + 1;
/*      */         } 
/* 1173 */         if (ei > emax) {
/* 1174 */           emax = ei;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1179 */     if (average) {
/* 1180 */       for (int il = 0; il < nl; il++) {
/* 1181 */         if (navg[il] > 0) {
/* 1182 */           eavg[il] = eavg[il] / navg[il];
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1187 */     for (i1 = 0; i1 < n1; i1++) {
/* 1188 */       int illo = ArrayMath.max(0, -this._lmin - i1);
/* 1189 */       int ilhi = ArrayMath.min(nl, n1 - this._lmin - i1);
/* 1190 */       for (int il = 0; il < nl; il++) {
/* 1191 */         if (il < illo || il >= ilhi) {
/* 1192 */           if (average) {
/* 1193 */             if (navg[il] > 0) {
/* 1194 */               e[i1][il] = eavg[il];
/*      */             } else {
/* 1196 */               e[i1][il] = emax;
/*      */             } 
/* 1198 */           } else if (nearest || reflect) {
/* 1199 */             int k1 = (il < illo) ? (-this._lmin - il) : (n1m - this._lmin - il);
/* 1200 */             if (reflect)
/* 1201 */               k1 += k1 - i1; 
/* 1202 */             if (0 <= k1 && k1 < n1) {
/* 1203 */               e[i1][il] = e[k1][il];
/*      */             } else {
/* 1205 */               e[i1][il] = emax;
/*      */             } 
/*      */           } else {
/* 1208 */             e[i1][il] = emax;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void accumulate(int dir, int b, float[][] e, float[][] d) {
/* 1223 */     int nl = (e[0]).length;
/* 1224 */     int ni = e.length;
/* 1225 */     int nlm1 = nl - 1;
/* 1226 */     int nim1 = ni - 1;
/* 1227 */     int ib = (dir > 0) ? 0 : nim1;
/* 1228 */     int ie = (dir > 0) ? ni : -1;
/* 1229 */     int is = (dir > 0) ? 1 : -1;
/* 1230 */     for (int il = 0; il < nl; il++)
/* 1231 */       d[ib][il] = 0.0F;  int ii;
/* 1232 */     for (ii = ib; ii != ie; ii += is) {
/* 1233 */       int ji = ArrayMath.max(0, ArrayMath.min(nim1, ii - is));
/* 1234 */       int jb = ArrayMath.max(0, ArrayMath.min(nim1, ii - is * b));
/* 1235 */       for (int i = 0; i < nl; i++) {
/* 1236 */         int ilm1 = i - 1; if (ilm1 == -1) ilm1 = 0; 
/* 1237 */         int ilp1 = i + 1; if (ilp1 == nl) ilp1 = nlm1; 
/* 1238 */         float dm = d[jb][ilm1];
/* 1239 */         float di = d[ji][i];
/* 1240 */         float dp = d[jb][ilp1]; int kb;
/* 1241 */         for (kb = ji; kb != jb; kb -= is) {
/* 1242 */           dm += e[kb][ilm1];
/* 1243 */           dp += e[kb][ilp1];
/*      */         } 
/* 1245 */         d[ii][i] = min3(dm, di, dp) + e[ii][i];
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void backtrack(int dir, int b, int lmin, float[][] d, float[][] e, float[] u) {
/* 1264 */     float ob = 1.0F / b;
/* 1265 */     int nl = (d[0]).length;
/* 1266 */     int ni = d.length;
/* 1267 */     int nlm1 = nl - 1;
/* 1268 */     int nim1 = ni - 1;
/* 1269 */     int ib = (dir > 0) ? 0 : nim1;
/* 1270 */     int ie = (dir > 0) ? nim1 : 0;
/* 1271 */     int is = (dir > 0) ? 1 : -1;
/* 1272 */     int ii = ib;
/* 1273 */     int il = ArrayMath.max(0, ArrayMath.min(nlm1, -lmin));
/* 1274 */     float dl = d[ii][il];
/* 1275 */     for (int jl = 1; jl < nl; jl++) {
/* 1276 */       if (d[ii][jl] < dl) {
/* 1277 */         dl = d[ii][jl];
/* 1278 */         il = jl;
/*      */       } 
/*      */     } 
/* 1281 */     u[ii] = (il + lmin);
/* 1282 */     while (ii != ie) {
/* 1283 */       int ji = ArrayMath.max(0, ArrayMath.min(nim1, ii + is));
/* 1284 */       int jb = ArrayMath.max(0, ArrayMath.min(nim1, ii + is * b));
/* 1285 */       int ilm1 = il - 1; if (ilm1 == -1) ilm1 = 0; 
/* 1286 */       int ilp1 = il + 1; if (ilp1 == nl) ilp1 = nlm1; 
/* 1287 */       float dm = d[jb][ilm1];
/* 1288 */       float di = d[ji][il];
/* 1289 */       float dp = d[jb][ilp1]; int kb;
/* 1290 */       for (kb = ji; kb != jb; kb += is) {
/* 1291 */         dm += e[kb][ilm1];
/* 1292 */         dp += e[kb][ilp1];
/*      */       } 
/* 1294 */       dl = min3(dm, di, dp);
/* 1295 */       if (dl != di) {
/* 1296 */         if (dl == dm) {
/* 1297 */           il = ilm1;
/*      */         } else {
/* 1299 */           il = ilp1;
/*      */         } 
/*      */       }
/* 1302 */       ii += is;
/* 1303 */       u[ii] = (il + lmin);
/* 1304 */       if (il == ilm1 || il == ilp1) {
/* 1305 */         float du = (u[ii] - u[ii - is]) * ob;
/* 1306 */         u[ii] = u[ii - is] + du; int i;
/* 1307 */         for (i = ji; i != jb; i += is) {
/* 1308 */           ii += is;
/* 1309 */           u[ii] = u[ii - is] + du;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void shiftAndScale(float emin, float emax, float[][] e) {
/* 1322 */     int nl = (e[0]).length;
/* 1323 */     int n1 = e.length;
/* 1324 */     float eshift = emin;
/* 1325 */     float escale = (emax > emin) ? (1.0F / (emax - emin)) : 1.0F;
/* 1326 */     for (int i1 = 0; i1 < n1; i1++) {
/* 1327 */       for (int il = 0; il < nl; il++) {
/* 1328 */         e[i1][il] = (e[i1][il] - eshift) * escale;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void shiftAndScale(float emin, float emax, float[][][] e) {
/* 1340 */     int n2 = e.length;
/* 1341 */     final float eshift = emin;
/* 1342 */     final float escale = (emax > emin) ? (1.0F / (emax - emin)) : 1.0F;
/* 1343 */     final float[][][] ef = e;
/* 1344 */     Parallel.loop(n2, new Parallel.LoopInt() {
/*      */           public void compute(int i2) {
/* 1346 */             int nl = (ef[i2][0]).length;
/* 1347 */             int n1 = (ef[i2]).length;
/* 1348 */             for (int i1 = 0; i1 < n1; i1++) {
/* 1349 */               for (int il = 0; il < nl; il++) {
/* 1350 */                 ef[i2][i1][il] = (ef[i2][i1][il] - eshift) * escale;
/*      */               }
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void smoothErrors1(int b, float[][] e, float[][] es) {
/* 1364 */     int nl = (e[0]).length;
/* 1365 */     int n1 = e.length;
/* 1366 */     float[][] ef = new float[n1][nl];
/* 1367 */     float[][] er = new float[n1][nl];
/* 1368 */     accumulate(1, b, e, ef);
/* 1369 */     accumulate(-1, b, e, er);
/* 1370 */     for (int i1 = 0; i1 < n1; i1++) {
/* 1371 */       for (int il = 0; il < nl; il++) {
/* 1372 */         es[i1][il] = ef[i1][il] + er[i1][il] - e[i1][il];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void smoothErrors1(int b, float[][][] e, float[][][] es) {
/* 1383 */     int n2 = e.length;
/* 1384 */     final int bf = b;
/* 1385 */     final float[][][] ef = e;
/* 1386 */     final float[][][] esf = es;
/* 1387 */     Parallel.loop(n2, new Parallel.LoopInt() {
/*      */           public void compute(int i2) {
/* 1389 */             DynamicWarping.smoothErrors1(bf, ef[i2], esf[i2]);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void smoothErrors2(int b, float[][][] e, float[][][] es) {
/* 1401 */     final int nl = (e[0][0]).length;
/* 1402 */     int n1 = (e[0]).length;
/* 1403 */     final int n2 = e.length;
/* 1404 */     final int bf = b;
/* 1405 */     final float[][][] ef = e;
/* 1406 */     final float[][][] esf = es;
/* 1407 */     final Parallel.Unsafe<float[][][]> eeu = new Parallel.Unsafe();
/*      */     
/* 1409 */     Parallel.loop(n1, new Parallel.LoopInt() {
/*      */           public void compute(int i1) {
/* 1411 */             float[][][] ee = (float[][][])eeu.get();
/* 1412 */             if (ee == null) eeu.set(ee = new float[4][n2][nl]); 
/* 1413 */             float[][] e1 = ee[0];
/* 1414 */             float[][] es1 = ee[1];
/* 1415 */             float[][] ef1 = ee[2];
/* 1416 */             float[][] er1 = ee[3]; int i2;
/* 1417 */             for (i2 = 0; i2 < n2; i2++) {
/* 1418 */               e1[i2] = ef[i2][i1];
/* 1419 */               es1[i2] = esf[i2][i1];
/* 1420 */               for (int il = 0; il < nl; il++) {
/* 1421 */                 ef1[i2][il] = 0.0F;
/* 1422 */                 er1[i2][il] = 0.0F;
/*      */               } 
/*      */             } 
/* 1425 */             DynamicWarping.accumulate(1, bf, e1, ef1);
/* 1426 */             DynamicWarping.accumulate(-1, bf, e1, er1);
/* 1427 */             for (i2 = 0; i2 < n2; i2++) {
/* 1428 */               for (int il = 0; il < nl; il++)
/* 1429 */                 es1[i2][il] = ef1[i2][il] + er1[i2][il] - e1[i2][il]; 
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private static float min3(float a, float b, float c) {
/* 1436 */     return (b <= a) ? ((b <= c) ? b : c) : ((a <= c) ? a : c);
/*      */   }
/*      */   
/*      */   private static float[] like(float[] a) {
/* 1440 */     return new float[a.length];
/*      */   }
/*      */   private static float[][] like(float[][] a) {
/* 1443 */     return new float[a.length][(a[0]).length];
/*      */   }
/*      */   private static float[][][] like(float[][][] a) {
/* 1446 */     return new float[a.length][(a[0]).length][(a[0][0]).length];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void computeErrors(float[][][] f, float[][][] g, float[][][][] e) {
/* 1453 */     final int n2 = (e[0]).length;
/* 1454 */     int n3 = e.length;
/* 1455 */     final float[][][] ff = f;
/* 1456 */     final float[][][] gf = g;
/* 1457 */     final float[][][][] ef = e;
/* 1458 */     Parallel.loop(n3, new Parallel.LoopInt() {
/*      */           public void compute(int i3) {
/* 1460 */             for (int i2 = 0; i2 < n2; i2++)
/* 1461 */               DynamicWarping.this.computeErrors(ff[i3][i2], gf[i3][i2], ef[i3][i2]); 
/*      */           }
/*      */         });
/* 1464 */     normalizeErrors(e);
/*      */   }
/*      */   private static void normalizeErrors(float[][][][] e) {
/* 1467 */     final int nl = (e[0][0][0]).length;
/* 1468 */     final int n1 = (e[0][0]).length;
/* 1469 */     final int n2 = (e[0]).length;
/* 1470 */     int n3 = e.length;
/* 1471 */     final float[][][][] ef = e;
/* 1472 */     MinMax mm = (MinMax)Parallel.reduce(n3, new Parallel.ReduceInt<MinMax>() {
/*      */           public DynamicWarping.MinMax compute(int i3) {
/* 1474 */             float emin = Float.MAX_VALUE;
/* 1475 */             float emax = -3.4028235E38F;
/* 1476 */             for (int i2 = 0; i2 < n2; i2++) {
/* 1477 */               for (int i1 = 0; i1 < n1; i1++) {
/* 1478 */                 for (int il = 0; il < nl; il++) {
/* 1479 */                   float ei = ef[i3][i2][i1][il];
/* 1480 */                   if (ei < emin) emin = ei; 
/* 1481 */                   if (ei > emax) emax = ei; 
/*      */                 } 
/*      */               } 
/*      */             } 
/* 1485 */             return new DynamicWarping.MinMax(emin, emax);
/*      */           }
/*      */           
/* 1488 */           public DynamicWarping.MinMax combine(DynamicWarping.MinMax mm1, DynamicWarping.MinMax mm2) { return new DynamicWarping.MinMax(ArrayMath.min(mm1.emin, mm2.emin), ArrayMath.max(mm1.emax, mm2.emax)); }
/*      */         });
/* 1490 */     shiftAndScale(mm.emin, mm.emax, e);
/*      */   }
/*      */   private static void shiftAndScale(float emin, float emax, float[][][][] e) {
/* 1493 */     final int nl = (e[0][0][0]).length;
/* 1494 */     final int n1 = (e[0][0]).length;
/* 1495 */     final int n2 = (e[0]).length;
/* 1496 */     int n3 = e.length;
/* 1497 */     final float eshift = emin;
/* 1498 */     final float escale = (emax > emin) ? (1.0F / (emax - emin)) : 1.0F;
/* 1499 */     final float[][][][] ef = e;
/* 1500 */     Parallel.loop(n3, new Parallel.LoopInt() {
/*      */           public void compute(int i3) {
/* 1502 */             for (int i2 = 0; i2 < n2; i2++) {
/* 1503 */               for (int i1 = 0; i1 < n1; i1++) {
/* 1504 */                 for (int il = 0; il < nl; il++)
/* 1505 */                   ef[i3][i2][i1][il] = (ef[i3][i2][i1][il] - eshift) * escale; 
/*      */               } 
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   private void smoothErrors(float[][][][] e) {
/* 1512 */     int n2 = (e[0]).length;
/* 1513 */     final int n3 = e.length;
/* 1514 */     final float[][][][] ef = e;
/* 1515 */     Parallel.loop(n3, new Parallel.LoopInt() {
/*      */           public void compute(int i3) {
/* 1517 */             DynamicWarping.smoothErrors1(DynamicWarping.this._bstrain1, ef[i3], ef[i3]); }
/*      */         });
/* 1519 */     normalizeErrors(e);
/* 1520 */     Parallel.loop(n3, new Parallel.LoopInt() {
/*      */           public void compute(int i3) {
/* 1522 */             DynamicWarping.smoothErrors2(DynamicWarping.this._bstrain2, ef[i3], ef[i3]); }
/*      */         });
/* 1524 */     normalizeErrors(e);
/* 1525 */     Parallel.loop(n2, new Parallel.LoopInt() {
/*      */           public void compute(int i2) {
/* 1527 */             float[][][] ei2 = new float[n3][][];
/* 1528 */             for (int i3 = 0; i3 < n3; i3++)
/* 1529 */               ei2[i3] = ef[i3][i2]; 
/* 1530 */             DynamicWarping.smoothErrors2(DynamicWarping.this._bstrain3, ei2, ei2); }
/*      */         });
/* 1532 */     normalizeErrors(e);
/*      */   }
/*      */   private void computeShifts(float[][][][] e, float[][][] u) {
/* 1535 */     final int nl = (e[0][0][0]).length;
/* 1536 */     final int n1 = (e[0][0]).length;
/* 1537 */     final int n2 = (e[0]).length;
/* 1538 */     int n3 = e.length;
/* 1539 */     final float[][][][] ef = e;
/* 1540 */     final float[][][] uf = u;
/* 1541 */     final Parallel.Unsafe<float[][]> du = new Parallel.Unsafe();
/* 1542 */     Parallel.loop(n3, new Parallel.LoopInt() {
/*      */           public void compute(int i3) {
/* 1544 */             float[][] d = (float[][])du.get();
/* 1545 */             if (d == null) du.set(d = new float[n1][nl]); 
/* 1546 */             for (int i2 = 0; i2 < n2; i2++) {
/* 1547 */               DynamicWarping.this.accumulateForward(ef[i3][i2], d);
/* 1548 */               DynamicWarping.this.backtrackReverse(d, ef[i3][i2], uf[i3][i2]);
/*      */             } 
/*      */           }
/*      */         });
/*      */   } private void smoothShifts(float[][][] u) {
/* 1553 */     if (this._ref1 != null) this._ref1.apply1(u, u); 
/* 1554 */     if (this._ref2 != null) this._ref2.apply2(u, u); 
/* 1555 */     if (this._ref3 != null) this._ref3.apply3(u, u); 
/*      */   }
/*      */   private static class MinMax { float emin; float emax;
/*      */     
/*      */     MinMax(float emin, float emax) {
/* 1560 */       this.emin = emin;
/* 1561 */       this.emax = emax;
/*      */     } }
/*      */   private static class OverlappingWindows2 { private int _n1; private int _n2; private int _l1; private int _l2; private int _m1; private int _m2; private double _s1; private double _s2;
/*      */     private float[][] _w;
/*      */     private float[][] _s;
/*      */     
/*      */     public OverlappingWindows2(int n1, int n2, int l1, int l2, double f1, double f2) {
/* 1568 */       Check.argument((0.0D <= f1 && f1 < 1.0D), "0 <= f1 < 1");
/* 1569 */       Check.argument((0.0D <= f2 && f2 < 1.0D), "0 <= f2 < 1");
/* 1570 */       this._n1 = n1;
/* 1571 */       this._n2 = n2;
/* 1572 */       this._l1 = ArrayMath.min(l1, n1);
/* 1573 */       this._l2 = ArrayMath.min(l2, n2);
/* 1574 */       this._m1 = 1 + (int)ArrayMath.ceil((this._n1 - this._l1) / this._l1 * (1.0D - f1));
/* 1575 */       this._m2 = 1 + (int)ArrayMath.ceil((this._n2 - this._l2) / this._l2 * (1.0D - f2));
/* 1576 */       this._s1 = (this._n1 - this._l1) / ArrayMath.max(1, this._m1 - 1);
/* 1577 */       this._s2 = (this._n2 - this._l2) / ArrayMath.max(1, this._m2 - 1);
/* 1578 */       makeWeights();
/* 1579 */       makeScalars();
/*      */     }
/* 1581 */     public int getN1() { return this._n1; }
/* 1582 */     public int getN2() { return this._n2; }
/* 1583 */     public int getL1() { return this._l1; }
/* 1584 */     public int getL2() { return this._l2; }
/* 1585 */     public int getM1() { return this._m1; }
/* 1586 */     public int getM2() { return this._m2; }
/* 1587 */     public int getI1(int k1) { return (int)(k1 * this._s1 + 0.5D); } public int getI2(int k2) {
/* 1588 */       return (int)(k2 * this._s2 + 0.5D);
/*      */     } public float getWeight(int i1, int i2, int j1, int j2) {
/* 1590 */       return this._w[j2][j1] * this._s[i2 + j2][i1 + j1];
/*      */     }
/* 1592 */     public float[][] getWeights() { return this._w; } public float[][] getScalars() {
/* 1593 */       return this._s;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void makeWeights() {
/* 1601 */       this._w = new float[this._l2][this._l1];
/* 1602 */       for (int i2 = 0; i2 < this._l2; i2++) {
/* 1603 */         for (int i1 = 0; i1 < this._l1; i1++) {
/* 1604 */           double s1 = ArrayMath.sin((i1 + 1.0D) * Math.PI / (this._l1 + 1.0D));
/* 1605 */           double s2 = ArrayMath.sin((i2 + 1.0D) * Math.PI / (this._l2 + 1.0D));
/* 1606 */           this._w[i2][i1] = (float)(s1 * s1 * s2 * s2);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     private void makeScalars() {
/* 1611 */       this._s = new float[this._n2][this._n1];
/* 1612 */       for (int k2 = 0; k2 < this._m2; k2++) {
/* 1613 */         int i = getI2(k2);
/* 1614 */         for (int k1 = 0; k1 < this._m1; k1++) {
/* 1615 */           int i1 = getI1(k1);
/* 1616 */           for (int j2 = 0; j2 < this._l2; j2++) {
/* 1617 */             for (int j1 = 0; j1 < this._l1; j1++) {
/* 1618 */               this._s[i + j2][i1 + j1] = this._s[i + j2][i1 + j1] + this._w[j2][j1];
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/* 1623 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 1624 */         for (int i1 = 0; i1 < this._n1; i1++)
/* 1625 */           this._s[i2][i1] = 1.0F / this._s[i2][i1]; 
/*      */       } 
/*      */     } }
/*      */ 
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/DynamicWarping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */