/*      */ package edu.mines.jtk.dsp;
/*      */ 
/*      */ import edu.mines.jtk.util.ArrayMath;
/*      */ import edu.mines.jtk.util.Check;
/*      */ import java.util.HashMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SincInterpolator
/*      */ {
/*      */   private static final double EWIN_FRAC = 0.9D;
/*      */   private static final int NTAB_MAX = 16385;
/*      */   
/*      */   public enum Extrapolation
/*      */   {
/*   78 */     ZERO,
/*   79 */     CONSTANT;
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
/*      */   public static SincInterpolator fromErrorAndLength(double emax, int lmax) {
/*   96 */     return new SincInterpolator(emax, 0.0D, lmax);
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
/*      */   public static SincInterpolator fromErrorAndFrequency(double emax, double fmax) {
/*  111 */     return new SincInterpolator(emax, fmax, 0);
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
/*      */   public static SincInterpolator fromFrequencyAndLength(double fmax, int lmax) {
/*  131 */     return new SincInterpolator(0.0D, fmax, lmax);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SincInterpolator() {
/*  141 */     this(0.0D, 0.3D, 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMaximumError() {
/*  149 */     return this._table.design.emax;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMaximumFrequency() {
/*  157 */     return this._table.design.fmax;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumLength() {
/*  165 */     return this._table.design.lmax;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getTableBytes() {
/*  176 */     long nbytes = 4L;
/*  177 */     nbytes *= this._table.lsinc;
/*  178 */     nbytes *= this._table.nsinc;
/*  179 */     return nbytes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Extrapolation getExtrapolation() {
/*  187 */     return this._extrap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExtrapolation(Extrapolation extrap) {
/*  196 */     this._extrap = extrap;
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
/*      */   public float interpolate(int nxu, double dxu, double fxu, float[] yu, double xi) {
/*  211 */     double xscale = 1.0D / dxu;
/*  212 */     double xshift = this._lsinc - fxu * xscale;
/*  213 */     int nxum = nxu - this._lsinc;
/*  214 */     return interpolate(xscale, xshift, nxum, nxu, yu, xi);
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
/*      */   public void interpolate(int nxu, double dxu, double fxu, float[] yu, int nxi, float[] xi, float[] yi) {
/*  231 */     double xscale = 1.0D / dxu;
/*  232 */     double xshift = this._lsinc - fxu * xscale;
/*  233 */     int nxum = nxu - this._lsinc;
/*  234 */     for (int ixi = 0; ixi < nxi; ixi++) {
/*  235 */       yi[ixi] = interpolate(xscale, xshift, nxum, nxu, yu, xi[ixi]);
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
/*      */   public void interpolate(int nxu, double dxu, double fxu, float[] yu, int nxi, double dxi, double fxi, float[] yi) {
/*  253 */     if (dxu == dxi) {
/*  254 */       shift(nxu, dxu, fxu, yu, nxi, fxi, yi);
/*      */     } else {
/*  256 */       double xscale = 1.0D / dxu;
/*  257 */       double xshift = this._lsinc - fxu * xscale;
/*  258 */       int nxum = nxu - this._lsinc;
/*  259 */       for (int ixi = 0; ixi < nxi; ixi++) {
/*  260 */         double xi = fxi + ixi * dxi;
/*  261 */         yi[ixi] = interpolate(xscale, xshift, nxum, nxu, yu, xi);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float interpolate(int nx1u, double dx1u, double fx1u, int nx2u, double dx2u, double fx2u, float[][] yu, double x1i, double x2i) {
/*  284 */     double x1scale = 1.0D / dx1u;
/*  285 */     double x2scale = 1.0D / dx2u;
/*  286 */     double x1shift = this._lsinc - fx1u * x1scale;
/*  287 */     double x2shift = this._lsinc - fx2u * x2scale;
/*  288 */     int nx1um = nx1u - this._lsinc;
/*  289 */     int nx2um = nx2u - this._lsinc;
/*  290 */     return interpolate(x1scale, x1shift, nx1um, nx1u, x2scale, x2shift, nx2um, nx2u, yu, x1i, x2i);
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
/*      */   public float interpolate(int nx1u, double dx1u, double fx1u, int nx2u, double dx2u, double fx2u, int nx3u, double dx3u, double fx3u, float[][][] yu, double x1i, double x2i, double x3i) {
/*  319 */     double x1scale = 1.0D / dx1u;
/*  320 */     double x2scale = 1.0D / dx2u;
/*  321 */     double x3scale = 1.0D / dx3u;
/*  322 */     double x1shift = this._lsinc - fx1u * x1scale;
/*  323 */     double x2shift = this._lsinc - fx2u * x2scale;
/*  324 */     double x3shift = this._lsinc - fx3u * x3scale;
/*  325 */     int nx1um = nx1u - this._lsinc;
/*  326 */     int nx2um = nx2u - this._lsinc;
/*  327 */     int nx3um = nx3u - this._lsinc;
/*  328 */     return interpolate(x1scale, x1shift, nx1um, nx1u, x2scale, x2shift, nx2um, nx2u, x3scale, x3shift, nx3um, nx3u, yu, x1i, x2i, x3i);
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
/*      */   public float interpolate(Sampling sxu, float[] yu, double xi) {
/*  343 */     Check.argument(sxu.isUniform(), "input sampling is uniform");
/*  344 */     return interpolate(sxu.getCount(), sxu.getDelta(), sxu.getFirst(), yu, xi);
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
/*      */   public void interpolate(Sampling sxu, float[] yu, Sampling sxi, float[] yi) {
/*  358 */     Check.argument(sxu.isUniform(), "input sampling is uniform");
/*  359 */     if (sxi.isUniform()) {
/*  360 */       interpolate(sxu.getCount(), sxu.getDelta(), sxu.getFirst(), yu, sxi
/*  361 */           .getCount(), sxi.getDelta(), sxi.getFirst(), yi);
/*      */     } else {
/*  363 */       int nxu = sxu.getCount();
/*  364 */       int nxi = sxi.getCount();
/*  365 */       double xscale = 1.0D / sxu.getDelta();
/*  366 */       double xshift = this._lsinc - sxu.getFirst() * xscale;
/*  367 */       int nxum = nxu - this._lsinc;
/*  368 */       for (int ixi = 0; ixi < nxi; ixi++) {
/*  369 */         double xi = sxi.getValue(ixi);
/*  370 */         yi[ixi] = interpolate(xscale, xshift, nxum, nxu, yu, xi);
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
/*      */   public float interpolate(Sampling sx1u, Sampling sx2u, float[][] yu, double x1i, double x2i) {
/*  388 */     Check.argument(sx1u.isUniform(), "input sampling of x1 is uniform");
/*  389 */     Check.argument(sx2u.isUniform(), "input sampling of x2 is uniform");
/*  390 */     return interpolate(sx1u
/*  391 */         .getCount(), sx1u.getDelta(), sx1u.getFirst(), sx2u
/*  392 */         .getCount(), sx2u.getDelta(), sx2u.getFirst(), yu, x1i, x2i);
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
/*      */   public float interpolate(Sampling sx1u, Sampling sx2u, Sampling sx3u, float[][][] yu, double x1i, double x2i, double x3i) {
/*  411 */     Check.argument(sx1u.isUniform(), "input sampling of x1 is uniform");
/*  412 */     Check.argument(sx2u.isUniform(), "input sampling of x2 is uniform");
/*  413 */     Check.argument(sx3u.isUniform(), "input sampling of x3 is uniform");
/*  414 */     return interpolate(sx1u
/*  415 */         .getCount(), sx1u.getDelta(), sx1u.getFirst(), sx2u
/*  416 */         .getCount(), sx2u.getDelta(), sx2u.getFirst(), sx3u
/*  417 */         .getCount(), sx3u.getDelta(), sx3u.getFirst(), yu, x1i, x2i, x3i);
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
/*      */   public void interpolateComplex(int nxu, double dxu, double fxu, float[] yu, int nxi, double dxi, double fxi, float[] yi) {
/*  438 */     double xscale = 1.0D / dxu;
/*  439 */     double xshift = this._lsinc - fxu * xscale;
/*  440 */     int nxum = nxu - this._lsinc;
/*  441 */     for (int ixi = 0; ixi < nxi; ixi++) {
/*  442 */       double xi = fxi + ixi * dxi;
/*  443 */       interpolateComplex(xscale, xshift, nxum, nxu, yu, ixi, xi, yi);
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
/*      */ 
/*      */   
/*      */   public void interpolateComplex(int nxu, double dxu, double fxu, float[] yu, int nxi, float[] xi, float[] yi) {
/*  463 */     double xscale = 1.0D / dxu;
/*  464 */     double xshift = this._lsinc - fxu * xscale;
/*  465 */     int nxum = nxu - this._lsinc;
/*  466 */     for (int ixi = 0; ixi < nxi; ixi++) {
/*  467 */       interpolateComplex(xscale, xshift, nxum, nxu, yu, ixi, xi[ixi], yi);
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
/*      */   public void interpolateComplex(Sampling sxu, float[] yu, Sampling sxi, float[] yi) {
/*  483 */     Check.argument(sxu.isUniform(), "input sampling is uniform");
/*  484 */     if (sxi.isUniform()) {
/*  485 */       interpolateComplex(sxu.getCount(), sxu.getDelta(), sxu.getFirst(), yu, sxi
/*  486 */           .getCount(), sxi.getDelta(), sxi.getFirst(), yi);
/*      */     } else {
/*  488 */       int nxu = sxu.getCount();
/*  489 */       int nxi = sxi.getCount();
/*  490 */       double xscale = 1.0D / sxu.getDelta();
/*  491 */       double xshift = this._lsinc - sxu.getFirst() * xscale;
/*  492 */       int nxum = nxu - this._lsinc;
/*  493 */       for (int ixi = 0; ixi < nxi; ixi++) {
/*  494 */         double xi = sxi.getValue(ixi);
/*  495 */         interpolateComplex(xscale, xshift, nxum, nxu, yu, ixi, xi, yi);
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
/*      */ 
/*      */   
/*      */   public void accumulate(double xa, float ya, int nxu, double dxu, double fxu, float[] yu) {
/*  516 */     double xscale = 1.0D / dxu;
/*  517 */     double xshift = this._lsinc - fxu * xscale;
/*  518 */     int nxum = nxu - this._lsinc;
/*  519 */     accumulate(xscale, xshift, nxum, xa, ya, nxu, yu);
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
/*      */   public void accumulate(int nxa, float[] xa, float[] ya, int nxu, double dxu, double fxu, float[] yu) {
/*  539 */     double xscale = 1.0D / dxu;
/*  540 */     double xshift = this._lsinc - fxu * xscale;
/*  541 */     int nxum = nxu - this._lsinc;
/*  542 */     for (int ixa = 0; ixa < nxa; ixa++) {
/*  543 */       accumulate(xscale, xshift, nxum, xa[ixa], ya[ixa], nxu, yu);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[][] getTable() {
/*  552 */     return ArrayMath.copy(this._table.asinc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumberInTable() {
/*  560 */     return this._table.asinc.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLengthInTable() {
/*  568 */     return (this._table.asinc[0]).length;
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
/*  581 */   private Extrapolation _extrap = Extrapolation.ZERO;
/*      */   
/*      */   private Table _table;
/*      */   
/*      */   private int _lsinc;
/*      */   
/*      */   private int _nsinc;
/*      */   
/*      */   private double _dsinc;
/*      */   
/*      */   private float[][] _asinc;
/*      */   
/*      */   private double _nsincm1;
/*      */   private int _ishift;
/*      */   
/*      */   private SincInterpolator(double emax, double fmax, int lmax) {
/*  597 */     Check.argument(((emax == 0.0D && fmax != 0.0D && lmax != 0) || (emax != 0.0D && fmax == 0.0D && lmax != 0) || (emax != 0.0D && fmax != 0.0D && lmax == 0)), "exactly one of emax, fmax, and lmax is zero");
/*      */ 
/*      */ 
/*      */     
/*  601 */     if (emax == 0.0D) {
/*  602 */       Check.argument((fmax < 0.5D), "fmax<0.5");
/*  603 */       Check.argument((lmax >= 8), "lmax>=8");
/*  604 */       Check.argument((lmax % 2 == 0), "lmax is even");
/*  605 */       Check.argument(((1.0D - 2.0D * fmax) * lmax > 1.0D), "(1.0-2.0*fmax)*lmax>1.0");
/*  606 */     } else if (fmax == 0.0D) {
/*  607 */       Check.argument((emax <= 0.1D), "emax<=0.1");
/*  608 */       Check.argument((lmax >= 8), "lmax>=8");
/*  609 */       Check.argument((lmax % 2 == 0), "lmax is even");
/*  610 */     } else if (lmax == 0) {
/*  611 */       Check.argument((emax <= 0.1D), "emax<=0.1");
/*  612 */       Check.argument((fmax < 0.5D), "fmax<0.5");
/*      */     } 
/*  614 */     this._table = getTable(emax, fmax, lmax);
/*  615 */     this._lsinc = this._table.lsinc;
/*  616 */     this._nsinc = this._table.nsinc;
/*  617 */     this._nsincm1 = this._table.nsincm1;
/*  618 */     this._ishift = this._table.ishift;
/*  619 */     this._dsinc = this._table.dsinc;
/*  620 */     this._asinc = this._table.asinc;
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Design
/*      */   {
/*      */     double emax;
/*      */     double fmax;
/*      */     int lmax;
/*      */     
/*      */     Design(double emax, double fmax, int lmax) {
/*  631 */       this.emax = emax;
/*  632 */       this.fmax = fmax;
/*  633 */       this.lmax = lmax;
/*      */     }
/*      */     public int hashCode() {
/*  636 */       long lemax = Double.doubleToLongBits(this.emax);
/*  637 */       long lfmax = Double.doubleToLongBits(this.fmax);
/*  638 */       return (int)(lemax ^ lemax >>> 32L) ^ (int)(lfmax ^ lfmax >>> 32L) ^ this.lmax;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object object) {
/*  643 */       Design that = (Design)object;
/*  644 */       return (this.emax == that.emax && this.fmax == that.fmax && this.lmax == that.lmax);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Table
/*      */   {
/*      */     SincInterpolator.Design design;
/*      */     
/*      */     int lsinc;
/*      */     int nsinc;
/*      */     int nsincm1;
/*      */     int ishift;
/*      */     double dsinc;
/*      */     float[][] asinc;
/*      */     
/*      */     private Table() {}
/*      */   }
/*      */   
/*      */   private static Table makeTable(Design design) {
/*      */     KaiserWindow kwin;
/*  665 */     double emax = design.emax;
/*  666 */     double fmax = design.fmax;
/*  667 */     int lmax = design.lmax;
/*      */ 
/*      */ 
/*      */     
/*  671 */     double wwin = 2.0D * (0.5D - fmax);
/*      */ 
/*      */ 
/*      */     
/*  675 */     double ewin = emax * 0.9D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  687 */     if (emax == 0.0D) {
/*  688 */       kwin = KaiserWindow.fromWidthAndLength(wwin, lmax);
/*  689 */       ewin = 3.0D * kwin.getError();
/*  690 */       emax = ewin / 0.9D;
/*  691 */       double etabMin = 3.455751918948773D * fmax / 16384.0D;
/*  692 */       double emaxMin = etabMin / 0.09999999999999998D;
/*  693 */       if (emax < emaxMin) {
/*  694 */         emax = emaxMin;
/*  695 */         ewin = emax * 0.9D;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  706 */     else if (fmax == 0.0D) {
/*  707 */       kwin = KaiserWindow.fromErrorAndLength(ewin / 3.0D, lmax);
/*  708 */       fmax = ArrayMath.max(0.0D, 0.5D - 0.5D * kwin.getWidth());
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/*  717 */       kwin = KaiserWindow.fromErrorAndWidth(ewin / 3.0D, wwin);
/*  718 */       double lwin = kwin.getLength();
/*  719 */       lmax = (int)lwin;
/*  720 */       while (lmax < lwin || lmax < 8 || lmax % 2 == 1)
/*  721 */         lmax++; 
/*  722 */       kwin = KaiserWindow.fromErrorAndLength(ewin / 3.0D, lmax);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  731 */     double etab = emax - ewin;
/*  732 */     double dsinc = (fmax > 0.0D) ? (etab / Math.PI * fmax) : 1.0D;
/*  733 */     int nsincMin = 1 + (int)ArrayMath.ceil(1.0D / dsinc);
/*  734 */     int nsinc = 2;
/*  735 */     while (nsinc < nsincMin)
/*  736 */       nsinc *= 2; 
/*  737 */     nsinc++;
/*  738 */     int lsinc = lmax;
/*  739 */     Table table = makeTable(nsinc, lsinc, kwin);
/*  740 */     table.design = new Design(emax, fmax, lmax);
/*  741 */     _tables.put(design, table);
/*  742 */     return table;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Table makeTable(int nsinc, int lsinc, KaiserWindow kwin) {
/*  749 */     float[][] asinc = new float[nsinc][lsinc];
/*  750 */     int nsincm1 = nsinc - 1;
/*  751 */     int ishift = -lsinc - lsinc / 2 + 1;
/*  752 */     double dsinc = 1.0D / (nsinc - 1);
/*      */ 
/*      */ 
/*      */     
/*  756 */     for (int j = 0; j < lsinc; j++) {
/*  757 */       asinc[0][j] = 0.0F;
/*  758 */       asinc[nsinc - 1][j] = 0.0F;
/*      */     } 
/*  760 */     asinc[0][lsinc / 2 - 1] = 1.0F;
/*  761 */     asinc[nsinc - 1][lsinc / 2] = 1.0F;
/*      */ 
/*      */     
/*  764 */     for (int isinc = 1; isinc < nsinc - 1; isinc++) {
/*  765 */       double x = (-lsinc / 2 + 1) - dsinc * isinc;
/*  766 */       for (int i = 0; i < lsinc; i++, x++) {
/*  767 */         asinc[isinc][i] = (float)(sinc(x) * kwin.evaluate(x));
/*      */       }
/*      */     } 
/*  770 */     Table table = new Table();
/*  771 */     table.lsinc = lsinc;
/*  772 */     table.nsinc = nsinc;
/*  773 */     table.nsincm1 = nsincm1;
/*  774 */     table.ishift = ishift;
/*  775 */     table.dsinc = dsinc;
/*  776 */     table.asinc = asinc;
/*  777 */     return table;
/*      */   }
/*      */   private static double sinc(double x) {
/*  780 */     return (x != 0.0D) ? (ArrayMath.sin(Math.PI * x) / Math.PI * x) : 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  787 */   private static final HashMap<Design, Table> _tables = new HashMap<>();
/*      */   private static Table getTable(double emax, double fmax, int lmax) {
/*  789 */     Design design = new Design(emax, fmax, lmax);
/*  790 */     synchronized (_tables) {
/*  791 */       Table table = _tables.get(design);
/*  792 */       if (table == null)
/*  793 */         table = makeTable(design); 
/*  794 */       return table;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float interpolate(double xscale, double xshift, int nxum, int nxu, float[] yu, double x) {
/*  803 */     double xn = xshift + x * xscale;
/*  804 */     int ixn = (int)xn;
/*  805 */     int kyu = this._ishift + ixn;
/*      */ 
/*      */     
/*  808 */     double frac = xn - ixn;
/*  809 */     if (frac < 0.0D)
/*  810 */       frac++; 
/*  811 */     int ksinc = (int)(frac * this._nsincm1 + 0.5D);
/*  812 */     float[] asinc = this._asinc[ksinc];
/*      */ 
/*      */ 
/*      */     
/*  816 */     float yr = 0.0F;
/*  817 */     if (kyu >= 0 && kyu <= nxum) {
/*  818 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++)
/*  819 */         yr += yu[kyu] * asinc[isinc]; 
/*  820 */     } else if (this._extrap == Extrapolation.ZERO) {
/*  821 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++) {
/*  822 */         if (0 <= kyu && kyu < nxu)
/*  823 */           yr += yu[kyu] * asinc[isinc]; 
/*      */       } 
/*  825 */     } else if (this._extrap == Extrapolation.CONSTANT) {
/*  826 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++) {
/*  827 */         int jyu = (kyu < 0) ? 0 : ((nxu <= kyu) ? (nxu - 1) : kyu);
/*  828 */         yr += yu[jyu] * asinc[isinc];
/*      */       } 
/*      */     } 
/*  831 */     return yr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void shift(int nxu, double dxu, double fxu, float[] yu, int nxi, double fxi, float[] yi) {
/*  838 */     double lxu = fxu + (nxu - 1) * dxu;
/*  839 */     double xscale = 1.0D / dxu;
/*  840 */     double xshift = this._lsinc - fxu * xscale;
/*  841 */     int nxum = nxu - this._lsinc;
/*      */ 
/*      */     
/*  844 */     double dx = dxu;
/*  845 */     double x1 = fxu + dxu * this._lsinc / 2.0D;
/*  846 */     double x2 = lxu - dxu * this._lsinc / 2.0D;
/*  847 */     double x1n = (x1 - fxi) / dx;
/*  848 */     double x2n = (x2 - fxi) / dx;
/*  849 */     int ix1 = ArrayMath.max(0, ArrayMath.min(nxi, (int)x1n + 1));
/*  850 */     int ix2 = ArrayMath.max(0, ArrayMath.min(nxi, (int)x2n - 1));
/*      */     
/*      */     int ixi;
/*  853 */     for (ixi = 0; ixi < ix1; ixi++) {
/*  854 */       double xi = fxi + ixi * dx;
/*  855 */       yi[ixi] = interpolate(xscale, xshift, nxum, nxu, yu, xi);
/*      */     } 
/*      */ 
/*      */     
/*  859 */     for (ixi = ix2; ixi < nxi; ixi++) {
/*  860 */       double xi = fxi + ixi * dx;
/*  861 */       yi[ixi] = interpolate(xscale, xshift, nxum, nxu, yu, xi);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  867 */     double xn = xshift + (fxi + ix1 * dx) * xscale;
/*  868 */     int ixn = (int)xn;
/*  869 */     int kyu = this._ishift + ixn;
/*      */ 
/*      */     
/*  872 */     double frac = xn - ixn;
/*  873 */     if (frac < 0.0D)
/*  874 */       frac++; 
/*  875 */     int ksinc = (int)(frac * this._nsincm1 + 0.5D);
/*  876 */     float[] asinc = this._asinc[ksinc];
/*      */ 
/*      */     
/*  879 */     for (int ix = ix1; ix < ix2; ix++, kyu++) {
/*  880 */       float yr = 0.0F;
/*  881 */       for (int isinc = 0, jyu = kyu; isinc < this._lsinc; isinc++, jyu++)
/*  882 */         yr += yu[jyu] * asinc[isinc]; 
/*  883 */       yi[ix] = yr;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void accumulate(double xscale, double xshift, int nxum, double x, float y, int nxu, float[] yu) {
/*  892 */     double xn = xshift + x * xscale;
/*  893 */     int ixn = (int)xn;
/*  894 */     int kyu = this._ishift + ixn;
/*      */ 
/*      */     
/*  897 */     double frac = xn - ixn;
/*  898 */     if (frac < 0.0D)
/*  899 */       frac++; 
/*  900 */     int ksinc = (int)(frac * this._nsincm1 + 0.5D);
/*  901 */     float[] asinc = this._asinc[ksinc];
/*      */ 
/*      */ 
/*      */     
/*  905 */     if (kyu >= 0 && kyu <= nxum) {
/*  906 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++)
/*  907 */         yu[kyu] = yu[kyu] + y * asinc[isinc]; 
/*  908 */     } else if (this._extrap == Extrapolation.ZERO) {
/*  909 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++) {
/*  910 */         if (0 <= kyu && kyu < nxu)
/*  911 */           yu[kyu] = yu[kyu] + y * asinc[isinc]; 
/*      */       } 
/*  913 */     } else if (this._extrap == Extrapolation.CONSTANT) {
/*  914 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++) {
/*  915 */         int jyu = (kyu < 0) ? 0 : ((nxu <= kyu) ? (nxu - 1) : kyu);
/*  916 */         yu[jyu] = yu[jyu] + y * asinc[isinc];
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float interpolate(double x1scale, double x1shift, int nx1um, int nx1u, double x2scale, double x2shift, int nx2um, int nx2u, float[][] yu, double x1, double x2) {
/*  927 */     double x1n = x1shift + x1 * x1scale;
/*  928 */     double x2n = x2shift + x2 * x2scale;
/*  929 */     int ix1n = (int)x1n;
/*  930 */     int ix2n = (int)x2n;
/*  931 */     int ky1u = this._ishift + ix1n;
/*  932 */     int ky2u = this._ishift + ix2n;
/*      */ 
/*      */     
/*  935 */     double frac1 = x1n - ix1n;
/*  936 */     double frac2 = x2n - ix2n;
/*  937 */     if (frac1 < 0.0D)
/*  938 */       frac1++; 
/*  939 */     if (frac2 < 0.0D)
/*  940 */       frac2++; 
/*  941 */     int ksinc1 = (int)(frac1 * this._nsincm1 + 0.5D);
/*  942 */     int ksinc2 = (int)(frac2 * this._nsincm1 + 0.5D);
/*  943 */     float[] asinc1 = this._asinc[ksinc1];
/*  944 */     float[] asinc2 = this._asinc[ksinc2];
/*      */ 
/*      */ 
/*      */     
/*  948 */     float yr = 0.0F;
/*  949 */     if (ky1u >= 0 && ky1u <= nx1um && ky2u >= 0 && ky2u <= nx2um) {
/*  950 */       for (int i2sinc = 0; i2sinc < this._lsinc; i2sinc++, ky2u++) {
/*  951 */         float asinc22 = asinc2[i2sinc];
/*  952 */         float[] yuk2 = yu[ky2u];
/*  953 */         float yr2 = 0.0F;
/*  954 */         for (int i1sinc = 0, my1u = ky1u; i1sinc < this._lsinc; i1sinc++, my1u++)
/*  955 */           yr2 += yuk2[my1u] * asinc1[i1sinc]; 
/*  956 */         yr += asinc22 * yr2;
/*      */       } 
/*  958 */     } else if (this._extrap == Extrapolation.ZERO) {
/*  959 */       for (int i2sinc = 0; i2sinc < this._lsinc; i2sinc++, ky2u++) {
/*  960 */         if (0 <= ky2u && ky2u < nx2u)
/*  961 */           for (int i1sinc = 0, my1u = ky1u; i1sinc < this._lsinc; i1sinc++, my1u++) {
/*  962 */             if (0 <= my1u && my1u < nx1u) {
/*  963 */               yr += yu[ky2u][my1u] * asinc2[i2sinc] * asinc1[i1sinc];
/*      */             }
/*      */           }  
/*      */       } 
/*  967 */     } else if (this._extrap == Extrapolation.CONSTANT) {
/*  968 */       for (int i2sinc = 0; i2sinc < this._lsinc; i2sinc++, ky2u++) {
/*  969 */         int jy2u = (ky2u < 0) ? 0 : ((nx2u <= ky2u) ? (nx2u - 2) : ky2u);
/*  970 */         for (int i1sinc = 0, my1u = ky1u; i1sinc < this._lsinc; i1sinc++, my1u++) {
/*  971 */           int jy1u = (my1u < 0) ? 0 : ((nx1u <= my1u) ? (nx1u - 1) : my1u);
/*  972 */           yr += yu[jy2u][jy1u] * asinc2[i2sinc] * asinc1[i1sinc];
/*      */         } 
/*      */       } 
/*      */     } 
/*  976 */     return yr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float interpolate(double x1scale, double x1shift, int nx1um, int nx1u, double x2scale, double x2shift, int nx2um, int nx2u, double x3scale, double x3shift, int nx3um, int nx3u, float[][][] yu, double x1, double x2, double x3) {
/*  986 */     double x1n = x1shift + x1 * x1scale;
/*  987 */     double x2n = x2shift + x2 * x2scale;
/*  988 */     double x3n = x3shift + x3 * x3scale;
/*  989 */     int ix1n = (int)x1n;
/*  990 */     int ix2n = (int)x2n;
/*  991 */     int ix3n = (int)x3n;
/*  992 */     int ky1u = this._ishift + ix1n;
/*  993 */     int ky2u = this._ishift + ix2n;
/*  994 */     int ky3u = this._ishift + ix3n;
/*      */ 
/*      */     
/*  997 */     double frac1 = x1n - ix1n;
/*  998 */     double frac2 = x2n - ix2n;
/*  999 */     double frac3 = x3n - ix3n;
/* 1000 */     if (frac1 < 0.0D)
/* 1001 */       frac1++; 
/* 1002 */     if (frac2 < 0.0D)
/* 1003 */       frac2++; 
/* 1004 */     if (frac3 < 0.0D)
/* 1005 */       frac3++; 
/* 1006 */     int ksinc1 = (int)(frac1 * this._nsincm1 + 0.5D);
/* 1007 */     int ksinc2 = (int)(frac2 * this._nsincm1 + 0.5D);
/* 1008 */     int ksinc3 = (int)(frac3 * this._nsincm1 + 0.5D);
/* 1009 */     float[] asinc1 = this._asinc[ksinc1];
/* 1010 */     float[] asinc2 = this._asinc[ksinc2];
/* 1011 */     float[] asinc3 = this._asinc[ksinc3];
/*      */ 
/*      */ 
/*      */     
/* 1015 */     float yr = 0.0F;
/* 1016 */     if (ky1u >= 0 && ky1u <= nx1um && ky2u >= 0 && ky2u <= nx2um && ky3u >= 0 && ky3u <= nx3um) {
/*      */ 
/*      */       
/* 1019 */       for (int i3sinc = 0; i3sinc < this._lsinc; i3sinc++, ky3u++) {
/* 1020 */         float asinc33 = asinc3[i3sinc];
/* 1021 */         float[][] yu3 = yu[ky3u];
/* 1022 */         float yr2 = 0.0F;
/* 1023 */         for (int i2sinc = 0, my2u = ky2u; i2sinc < this._lsinc; i2sinc++, my2u++) {
/* 1024 */           float asinc22 = asinc2[i2sinc];
/* 1025 */           float[] yu32 = yu3[my2u];
/* 1026 */           float yr1 = 0.0F;
/* 1027 */           for (int i1sinc = 0, my1u = ky1u; i1sinc < this._lsinc; i1sinc++, my1u++)
/* 1028 */             yr1 += yu32[my1u] * asinc1[i1sinc]; 
/* 1029 */           yr2 += asinc22 * yr1;
/*      */         } 
/* 1031 */         yr += asinc33 * yr2;
/*      */       } 
/* 1033 */     } else if (this._extrap == Extrapolation.ZERO) {
/* 1034 */       for (int i3sinc = 0; i3sinc < this._lsinc; i3sinc++, ky3u++) {
/* 1035 */         if (0 <= ky3u && ky3u < nx3u) {
/* 1036 */           for (int i2sinc = 0, my2u = ky2u; i2sinc < this._lsinc; i2sinc++, my2u++) {
/* 1037 */             if (0 <= my2u && my2u < nx2u) {
/* 1038 */               for (int i1sinc = 0, my1u = ky1u; i1sinc < this._lsinc; i1sinc++, my1u++) {
/* 1039 */                 if (0 <= my1u && my1u < nx1u) {
/* 1040 */                   yr += yu[ky3u][my2u][my1u] * asinc3[i3sinc] * asinc2[i2sinc] * asinc1[i1sinc];
/*      */                 }
/*      */               }
/*      */             
/*      */             }
/*      */           }
/*      */         
/*      */         }
/*      */       } 
/* 1049 */     } else if (this._extrap == Extrapolation.CONSTANT) {
/* 1050 */       for (int i3sinc = 0; i3sinc < this._lsinc; i3sinc++, ky3u++) {
/* 1051 */         int jy3u = (ky3u < 0) ? 0 : ((nx3u <= ky3u) ? (nx3u - 2) : ky3u);
/* 1052 */         for (int i2sinc = 0, my2u = ky2u; i2sinc < this._lsinc; i2sinc++, my2u++) {
/* 1053 */           int jy2u = (my2u < 0) ? 0 : ((nx2u <= my2u) ? (nx2u - 2) : my2u);
/* 1054 */           for (int i1sinc = 0, my1u = ky1u; i1sinc < this._lsinc; i1sinc++, my1u++) {
/* 1055 */             int jy1u = (my1u < 0) ? 0 : ((nx1u <= my1u) ? (nx1u - 1) : my1u);
/* 1056 */             yr += yu[jy3u][jy2u][jy1u] * asinc3[i3sinc] * asinc2[i2sinc] * asinc1[i1sinc];
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1064 */     return yr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void interpolateComplex(double xscale, double xshift, int nxum, int nxu, float[] yu, int ix, double x, float[] y) {
/* 1072 */     double xn = xshift + x * xscale;
/* 1073 */     int ixn = (int)xn;
/* 1074 */     int kyu = this._ishift + ixn;
/*      */ 
/*      */     
/* 1077 */     double frac = xn - ixn;
/* 1078 */     if (frac < 0.0D)
/* 1079 */       frac++; 
/* 1080 */     int ksinc = (int)(frac * this._nsincm1 + 0.5D);
/* 1081 */     float[] asinc = this._asinc[ksinc];
/*      */ 
/*      */ 
/*      */     
/* 1085 */     float yr = 0.0F;
/* 1086 */     float yi = 0.0F;
/* 1087 */     if (kyu >= 0 && kyu <= nxum) {
/* 1088 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++) {
/* 1089 */         int jyu = 2 * kyu;
/* 1090 */         float asinci = asinc[isinc];
/* 1091 */         yr += yu[jyu] * asinci;
/* 1092 */         yi += yu[jyu + 1] * asinci;
/*      */       } 
/* 1094 */     } else if (this._extrap == Extrapolation.ZERO) {
/* 1095 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++) {
/* 1096 */         if (0 <= kyu && kyu < nxu) {
/* 1097 */           int jyu = 2 * kyu;
/* 1098 */           float asinci = asinc[isinc];
/* 1099 */           yr += yu[jyu] * asinci;
/* 1100 */           yi += yu[jyu + 1] * asinci;
/*      */         } 
/*      */       } 
/* 1103 */     } else if (this._extrap == Extrapolation.CONSTANT) {
/* 1104 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++) {
/* 1105 */         int jyu = (kyu < 0) ? 0 : ((nxu <= kyu) ? (2 * nxu - 2) : (2 * kyu));
/* 1106 */         float asinci = asinc[isinc];
/* 1107 */         yr += yu[jyu] * asinci;
/* 1108 */         yi += yu[jyu + 1] * asinci;
/*      */       } 
/*      */     } 
/* 1111 */     int jx = 2 * ix;
/* 1112 */     y[jx] = yr;
/* 1113 */     y[jx + 1] = yi;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/SincInterpolator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */