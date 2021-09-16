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
/*      */ 
/*      */ public class SincInterp
/*      */ {
/*      */   private static final double EWIN_FRAC = 0.9D;
/*      */   private static final int NTAB_MAX = 16385;
/*      */   
/*      */   public enum Extrapolation
/*      */   {
/*   79 */     ZERO,
/*   80 */     CONSTANT;
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
/*      */   public static SincInterp fromErrorAndLength(double emax, int lmax) {
/*   97 */     return new SincInterp(emax, 0.0D, lmax);
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
/*      */   public static SincInterp fromErrorAndFrequency(double emax, double fmax) {
/*  112 */     return new SincInterp(emax, fmax, 0);
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
/*      */   public static SincInterp fromFrequencyAndLength(double fmax, int lmax) {
/*  132 */     return new SincInterp(0.0D, fmax, lmax);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SincInterp() {
/*  142 */     this(0.0D, 0.3D, 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMaximumError() {
/*  150 */     return this._table.design.emax;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMaximumFrequency() {
/*  158 */     return this._table.design.fmax;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumLength() {
/*  166 */     return this._table.design.lmax;
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
/*  177 */     long nbytes = 4L;
/*  178 */     nbytes *= this._table.lsinc;
/*  179 */     nbytes *= this._table.nsinc;
/*  180 */     return nbytes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Extrapolation getExtrapolation() {
/*  188 */     return this._extrap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExtrapolation(Extrapolation extrap) {
/*  197 */     this._extrap = extrap;
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
/*  212 */     double xscale = 1.0D / dxu;
/*  213 */     double xshift = this._lsinc - fxu * xscale;
/*  214 */     int nxum = nxu - this._lsinc;
/*  215 */     return interpolate(xscale, xshift, nxum, nxu, yu, xi);
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
/*  232 */     double xscale = 1.0D / dxu;
/*  233 */     double xshift = this._lsinc - fxu * xscale;
/*  234 */     int nxum = nxu - this._lsinc;
/*  235 */     for (int ixi = 0; ixi < nxi; ixi++) {
/*  236 */       yi[ixi] = interpolate(xscale, xshift, nxum, nxu, yu, xi[ixi]);
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
/*  254 */     if (dxu == dxi) {
/*  255 */       shift(nxu, dxu, fxu, yu, nxi, fxi, yi);
/*      */     } else {
/*  257 */       double xscale = 1.0D / dxu;
/*  258 */       double xshift = this._lsinc - fxu * xscale;
/*  259 */       int nxum = nxu - this._lsinc;
/*  260 */       for (int ixi = 0; ixi < nxi; ixi++) {
/*  261 */         double xi = fxi + ixi * dxi;
/*  262 */         yi[ixi] = interpolate(xscale, xshift, nxum, nxu, yu, xi);
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
/*  285 */     double x1scale = 1.0D / dx1u;
/*  286 */     double x2scale = 1.0D / dx2u;
/*  287 */     double x1shift = this._lsinc - fx1u * x1scale;
/*  288 */     double x2shift = this._lsinc - fx2u * x2scale;
/*  289 */     int nx1um = nx1u - this._lsinc;
/*  290 */     int nx2um = nx2u - this._lsinc;
/*  291 */     return interpolate(x1scale, x1shift, nx1um, nx1u, x2scale, x2shift, nx2um, nx2u, yu, x1i, x2i);
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
/*  320 */     double x1scale = 1.0D / dx1u;
/*  321 */     double x2scale = 1.0D / dx2u;
/*  322 */     double x3scale = 1.0D / dx3u;
/*  323 */     double x1shift = this._lsinc - fx1u * x1scale;
/*  324 */     double x2shift = this._lsinc - fx2u * x2scale;
/*  325 */     double x3shift = this._lsinc - fx3u * x3scale;
/*  326 */     int nx1um = nx1u - this._lsinc;
/*  327 */     int nx2um = nx2u - this._lsinc;
/*  328 */     int nx3um = nx3u - this._lsinc;
/*  329 */     return interpolate(x1scale, x1shift, nx1um, nx1u, x2scale, x2shift, nx2um, nx2u, x3scale, x3shift, nx3um, nx3u, yu, x1i, x2i, x3i);
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
/*  344 */     Check.argument(sxu.isUniform(), "input sampling is uniform");
/*  345 */     return interpolate(sxu.getCount(), sxu.getDelta(), sxu.getFirst(), yu, xi);
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
/*  359 */     Check.argument(sxu.isUniform(), "input sampling is uniform");
/*  360 */     if (sxi.isUniform()) {
/*  361 */       interpolate(sxu.getCount(), sxu.getDelta(), sxu.getFirst(), yu, sxi
/*  362 */           .getCount(), sxi.getDelta(), sxi.getFirst(), yi);
/*      */     } else {
/*  364 */       int nxu = sxu.getCount();
/*  365 */       int nxi = sxi.getCount();
/*  366 */       double xscale = 1.0D / sxu.getDelta();
/*  367 */       double xshift = this._lsinc - sxu.getFirst() * xscale;
/*  368 */       int nxum = nxu - this._lsinc;
/*  369 */       for (int ixi = 0; ixi < nxi; ixi++) {
/*  370 */         double xi = sxi.getValue(ixi);
/*  371 */         yi[ixi] = interpolate(xscale, xshift, nxum, nxu, yu, xi);
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
/*  389 */     Check.argument(sx1u.isUniform(), "input sampling of x1 is uniform");
/*  390 */     Check.argument(sx2u.isUniform(), "input sampling of x2 is uniform");
/*  391 */     return interpolate(sx1u
/*  392 */         .getCount(), sx1u.getDelta(), sx1u.getFirst(), sx2u
/*  393 */         .getCount(), sx2u.getDelta(), sx2u.getFirst(), yu, x1i, x2i);
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
/*  412 */     Check.argument(sx1u.isUniform(), "input sampling of x1 is uniform");
/*  413 */     Check.argument(sx2u.isUniform(), "input sampling of x2 is uniform");
/*  414 */     Check.argument(sx3u.isUniform(), "input sampling of x3 is uniform");
/*  415 */     return interpolate(sx1u
/*  416 */         .getCount(), sx1u.getDelta(), sx1u.getFirst(), sx2u
/*  417 */         .getCount(), sx2u.getDelta(), sx2u.getFirst(), sx3u
/*  418 */         .getCount(), sx3u.getDelta(), sx3u.getFirst(), yu, x1i, x2i, x3i);
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
/*  439 */     double xscale = 1.0D / dxu;
/*  440 */     double xshift = this._lsinc - fxu * xscale;
/*  441 */     int nxum = nxu - this._lsinc;
/*  442 */     for (int ixi = 0; ixi < nxi; ixi++) {
/*  443 */       double xi = fxi + ixi * dxi;
/*  444 */       interpolateComplex(xscale, xshift, nxum, nxu, yu, ixi, xi, yi);
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
/*  464 */     double xscale = 1.0D / dxu;
/*  465 */     double xshift = this._lsinc - fxu * xscale;
/*  466 */     int nxum = nxu - this._lsinc;
/*  467 */     for (int ixi = 0; ixi < nxi; ixi++) {
/*  468 */       interpolateComplex(xscale, xshift, nxum, nxu, yu, ixi, xi[ixi], yi);
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
/*  484 */     Check.argument(sxu.isUniform(), "input sampling is uniform");
/*  485 */     if (sxi.isUniform()) {
/*  486 */       interpolateComplex(sxu.getCount(), sxu.getDelta(), sxu.getFirst(), yu, sxi
/*  487 */           .getCount(), sxi.getDelta(), sxi.getFirst(), yi);
/*      */     } else {
/*  489 */       int nxu = sxu.getCount();
/*  490 */       int nxi = sxi.getCount();
/*  491 */       double xscale = 1.0D / sxu.getDelta();
/*  492 */       double xshift = this._lsinc - sxu.getFirst() * xscale;
/*  493 */       int nxum = nxu - this._lsinc;
/*  494 */       for (int ixi = 0; ixi < nxi; ixi++) {
/*  495 */         double xi = sxi.getValue(ixi);
/*  496 */         interpolateComplex(xscale, xshift, nxum, nxu, yu, ixi, xi, yi);
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
/*  517 */     double xscale = 1.0D / dxu;
/*  518 */     double xshift = this._lsinc - fxu * xscale;
/*  519 */     int nxum = nxu - this._lsinc;
/*  520 */     accumulate(xscale, xshift, nxum, xa, ya, nxu, yu);
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
/*  540 */     double xscale = 1.0D / dxu;
/*  541 */     double xshift = this._lsinc - fxu * xscale;
/*  542 */     int nxum = nxu - this._lsinc;
/*  543 */     for (int ixa = 0; ixa < nxa; ixa++) {
/*  544 */       accumulate(xscale, xshift, nxum, xa[ixa], ya[ixa], nxu, yu);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[][] getTable() {
/*  553 */     return ArrayMath.copy(this._table.asinc);
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
/*  566 */   private Extrapolation _extrap = Extrapolation.ZERO;
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
/*      */   private SincInterp(double emax, double fmax, int lmax) {
/*  582 */     Check.argument(((emax == 0.0D && fmax != 0.0D && lmax != 0) || (emax != 0.0D && fmax == 0.0D && lmax != 0) || (emax != 0.0D && fmax != 0.0D && lmax == 0)), "exactly one of emax, fmax, and lmax is zero");
/*      */ 
/*      */ 
/*      */     
/*  586 */     if (emax == 0.0D) {
/*  587 */       Check.argument((fmax < 0.5D), "fmax<0.5");
/*  588 */       Check.argument((lmax >= 8), "lmax>=8");
/*  589 */       Check.argument((lmax % 2 == 0), "lmax is even");
/*  590 */       Check.argument(((1.0D - 2.0D * fmax) * lmax > 1.0D), "(1.0-2.0*fmax)*lmax>1.0");
/*  591 */     } else if (fmax == 0.0D) {
/*  592 */       Check.argument((emax <= 0.1D), "emax<=0.1");
/*  593 */       Check.argument((lmax >= 8), "lmax>=8");
/*  594 */       Check.argument((lmax % 2 == 0), "lmax is even");
/*  595 */     } else if (lmax == 0) {
/*  596 */       Check.argument((emax <= 0.1D), "emax<=0.1");
/*  597 */       Check.argument((fmax < 0.5D), "fmax<0.5");
/*      */     } 
/*  599 */     this._table = getTable(emax, fmax, lmax);
/*  600 */     this._lsinc = this._table.lsinc;
/*  601 */     this._nsinc = this._table.nsinc;
/*  602 */     this._nsincm1 = this._table.nsincm1;
/*  603 */     this._ishift = this._table.ishift;
/*  604 */     this._dsinc = this._table.dsinc;
/*  605 */     this._asinc = this._table.asinc;
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
/*  616 */       this.emax = emax;
/*  617 */       this.fmax = fmax;
/*  618 */       this.lmax = lmax;
/*      */     }
/*      */     public int hashCode() {
/*  621 */       long lemax = Double.doubleToLongBits(this.emax);
/*  622 */       long lfmax = Double.doubleToLongBits(this.fmax);
/*  623 */       return (int)(lemax ^ lemax >>> 32L) ^ (int)(lfmax ^ lfmax >>> 32L) ^ this.lmax;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object object) {
/*  628 */       Design that = (Design)object;
/*  629 */       return (this.emax == that.emax && this.fmax == that.fmax && this.lmax == that.lmax);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Table
/*      */   {
/*      */     SincInterp.Design design;
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
/*  650 */     double emax = design.emax;
/*  651 */     double fmax = design.fmax;
/*  652 */     int lmax = design.lmax;
/*      */ 
/*      */ 
/*      */     
/*  656 */     double wwin = 2.0D * (0.5D - fmax);
/*      */ 
/*      */ 
/*      */     
/*  660 */     double ewin = emax * 0.9D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  672 */     if (emax == 0.0D) {
/*  673 */       kwin = KaiserWindow.fromWidthAndLength(wwin, lmax);
/*  674 */       ewin = 3.0D * kwin.getError();
/*  675 */       emax = ewin / 0.9D;
/*  676 */       double etabMin = 3.455751918948773D * fmax / 16384.0D;
/*  677 */       double emaxMin = etabMin / 0.09999999999999998D;
/*  678 */       if (emax < emaxMin) {
/*  679 */         emax = emaxMin;
/*  680 */         ewin = emax * 0.9D;
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
/*  691 */     else if (fmax == 0.0D) {
/*  692 */       kwin = KaiserWindow.fromErrorAndLength(ewin / 3.0D, lmax);
/*  693 */       fmax = ArrayMath.max(0.0D, 0.5D - 0.5D * kwin.getWidth());
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/*  702 */       kwin = KaiserWindow.fromErrorAndWidth(ewin / 3.0D, wwin);
/*  703 */       double lwin = kwin.getLength();
/*  704 */       lmax = (int)lwin;
/*  705 */       while (lmax < lwin || lmax < 8 || lmax % 2 == 1)
/*  706 */         lmax++; 
/*  707 */       kwin = KaiserWindow.fromErrorAndLength(ewin / 3.0D, lmax);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  716 */     double etab = emax - ewin;
/*  717 */     double dsinc = (fmax > 0.0D) ? (etab / Math.PI * fmax) : 1.0D;
/*  718 */     int nsincMin = 1 + (int)ArrayMath.ceil(1.0D / dsinc);
/*  719 */     int nsinc = 2;
/*  720 */     while (nsinc < nsincMin)
/*  721 */       nsinc *= 2; 
/*  722 */     nsinc++;
/*  723 */     int lsinc = lmax;
/*  724 */     Table table = makeTable(nsinc, lsinc, kwin);
/*  725 */     table.design = new Design(emax, fmax, lmax);
/*  726 */     _tables.put(design, table);
/*  727 */     return table;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Table makeTable(int nsinc, int lsinc, KaiserWindow kwin) {
/*  734 */     float[][] asinc = new float[nsinc][lsinc];
/*  735 */     int nsincm1 = nsinc - 1;
/*  736 */     int ishift = -lsinc - lsinc / 2 + 1;
/*  737 */     double dsinc = 1.0D / (nsinc - 1);
/*      */ 
/*      */ 
/*      */     
/*  741 */     for (int j = 0; j < lsinc; j++) {
/*  742 */       asinc[0][j] = 0.0F;
/*  743 */       asinc[nsinc - 1][j] = 0.0F;
/*      */     } 
/*  745 */     asinc[0][lsinc / 2 - 1] = 1.0F;
/*  746 */     asinc[nsinc - 1][lsinc / 2] = 1.0F;
/*      */ 
/*      */     
/*  749 */     for (int isinc = 1; isinc < nsinc - 1; isinc++) {
/*  750 */       double x = (-lsinc / 2 + 1) - dsinc * isinc;
/*  751 */       for (int i = 0; i < lsinc; i++, x++) {
/*  752 */         asinc[isinc][i] = (float)(sinc(x) * kwin.evaluate(x));
/*      */       }
/*      */     } 
/*  755 */     Table table = new Table();
/*  756 */     table.lsinc = lsinc;
/*  757 */     table.nsinc = nsinc;
/*  758 */     table.nsincm1 = nsincm1;
/*  759 */     table.ishift = ishift;
/*  760 */     table.dsinc = dsinc;
/*  761 */     table.asinc = asinc;
/*  762 */     return table;
/*      */   }
/*      */   private static double sinc(double x) {
/*  765 */     return (x != 0.0D) ? (ArrayMath.sin(Math.PI * x) / Math.PI * x) : 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  772 */   private static final HashMap<Design, Table> _tables = new HashMap<>();
/*      */   private static Table getTable(double emax, double fmax, int lmax) {
/*  774 */     Design design = new Design(emax, fmax, lmax);
/*  775 */     synchronized (_tables) {
/*  776 */       Table table = _tables.get(design);
/*  777 */       if (table == null)
/*  778 */         table = makeTable(design); 
/*  779 */       return table;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float interpolate(double xscale, double xshift, int nxum, int nxu, float[] yu, double x) {
/*  788 */     double xn = xshift + x * xscale;
/*  789 */     int ixn = (int)xn;
/*  790 */     int kyu = this._ishift + ixn;
/*      */ 
/*      */     
/*  793 */     double frac = xn - ixn;
/*  794 */     if (frac < 0.0D)
/*  795 */       frac++; 
/*  796 */     int ksinc = (int)(frac * this._nsincm1 + 0.5D);
/*  797 */     float[] asinc = this._asinc[ksinc];
/*      */ 
/*      */ 
/*      */     
/*  801 */     float yr = 0.0F;
/*  802 */     if (kyu >= 0 && kyu <= nxum) {
/*  803 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++)
/*  804 */         yr += yu[kyu] * asinc[isinc]; 
/*  805 */     } else if (this._extrap == Extrapolation.ZERO) {
/*  806 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++) {
/*  807 */         if (0 <= kyu && kyu < nxu)
/*  808 */           yr += yu[kyu] * asinc[isinc]; 
/*      */       } 
/*  810 */     } else if (this._extrap == Extrapolation.CONSTANT) {
/*  811 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++) {
/*  812 */         int jyu = (kyu < 0) ? 0 : ((nxu <= kyu) ? (nxu - 1) : kyu);
/*  813 */         yr += yu[jyu] * asinc[isinc];
/*      */       } 
/*      */     } 
/*  816 */     return yr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void shift(int nxu, double dxu, double fxu, float[] yu, int nxi, double fxi, float[] yi) {
/*  823 */     double lxu = fxu + (nxu - 1) * dxu;
/*  824 */     double xscale = 1.0D / dxu;
/*  825 */     double xshift = this._lsinc - fxu * xscale;
/*  826 */     int nxum = nxu - this._lsinc;
/*      */ 
/*      */     
/*  829 */     double dx = dxu;
/*  830 */     double x1 = fxu + dxu * this._lsinc / 2.0D;
/*  831 */     double x2 = lxu - dxu * this._lsinc / 2.0D;
/*  832 */     double x1n = (x1 - fxi) / dx;
/*  833 */     double x2n = (x2 - fxi) / dx;
/*  834 */     int ix1 = ArrayMath.max(0, ArrayMath.min(nxi, (int)x1n + 1));
/*  835 */     int ix2 = ArrayMath.max(0, ArrayMath.min(nxi, (int)x2n - 1));
/*      */     
/*      */     int ixi;
/*  838 */     for (ixi = 0; ixi < ix1; ixi++) {
/*  839 */       double xi = fxi + ixi * dx;
/*  840 */       yi[ixi] = interpolate(xscale, xshift, nxum, nxu, yu, xi);
/*      */     } 
/*      */ 
/*      */     
/*  844 */     for (ixi = ix2; ixi < nxi; ixi++) {
/*  845 */       double xi = fxi + ixi * dx;
/*  846 */       yi[ixi] = interpolate(xscale, xshift, nxum, nxu, yu, xi);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  852 */     double xn = xshift + (fxi + ix1 * dx) * xscale;
/*  853 */     int ixn = (int)xn;
/*  854 */     int kyu = this._ishift + ixn;
/*      */ 
/*      */     
/*  857 */     double frac = xn - ixn;
/*  858 */     if (frac < 0.0D)
/*  859 */       frac++; 
/*  860 */     int ksinc = (int)(frac * this._nsincm1 + 0.5D);
/*  861 */     float[] asinc = this._asinc[ksinc];
/*      */ 
/*      */     
/*  864 */     for (int ix = ix1; ix < ix2; ix++, kyu++) {
/*  865 */       float yr = 0.0F;
/*  866 */       for (int isinc = 0, jyu = kyu; isinc < this._lsinc; isinc++, jyu++)
/*  867 */         yr += yu[jyu] * asinc[isinc]; 
/*  868 */       yi[ix] = yr;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void accumulate(double xscale, double xshift, int nxum, double x, float y, int nxu, float[] yu) {
/*  877 */     double xn = xshift + x * xscale;
/*  878 */     int ixn = (int)xn;
/*  879 */     int kyu = this._ishift + ixn;
/*      */ 
/*      */     
/*  882 */     double frac = xn - ixn;
/*  883 */     if (frac < 0.0D)
/*  884 */       frac++; 
/*  885 */     int ksinc = (int)(frac * this._nsincm1 + 0.5D);
/*  886 */     float[] asinc = this._asinc[ksinc];
/*      */ 
/*      */ 
/*      */     
/*  890 */     if (kyu >= 0 && kyu <= nxum) {
/*  891 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++)
/*  892 */         yu[kyu] = yu[kyu] + y * asinc[isinc]; 
/*  893 */     } else if (this._extrap == Extrapolation.ZERO) {
/*  894 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++) {
/*  895 */         if (0 <= kyu && kyu < nxu)
/*  896 */           yu[kyu] = yu[kyu] + y * asinc[isinc]; 
/*      */       } 
/*  898 */     } else if (this._extrap == Extrapolation.CONSTANT) {
/*  899 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++) {
/*  900 */         int jyu = (kyu < 0) ? 0 : ((nxu <= kyu) ? (nxu - 1) : kyu);
/*  901 */         yu[jyu] = yu[jyu] + y * asinc[isinc];
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
/*  912 */     double x1n = x1shift + x1 * x1scale;
/*  913 */     double x2n = x2shift + x2 * x2scale;
/*  914 */     int ix1n = (int)x1n;
/*  915 */     int ix2n = (int)x2n;
/*  916 */     int ky1u = this._ishift + ix1n;
/*  917 */     int ky2u = this._ishift + ix2n;
/*      */ 
/*      */     
/*  920 */     double frac1 = x1n - ix1n;
/*  921 */     double frac2 = x2n - ix2n;
/*  922 */     if (frac1 < 0.0D)
/*  923 */       frac1++; 
/*  924 */     if (frac2 < 0.0D)
/*  925 */       frac2++; 
/*  926 */     int ksinc1 = (int)(frac1 * this._nsincm1 + 0.5D);
/*  927 */     int ksinc2 = (int)(frac2 * this._nsincm1 + 0.5D);
/*  928 */     float[] asinc1 = this._asinc[ksinc1];
/*  929 */     float[] asinc2 = this._asinc[ksinc2];
/*      */ 
/*      */ 
/*      */     
/*  933 */     float yr = 0.0F;
/*  934 */     if (ky1u >= 0 && ky1u <= nx1um && ky2u >= 0 && ky2u <= nx2um) {
/*  935 */       for (int i2sinc = 0; i2sinc < this._lsinc; i2sinc++, ky2u++) {
/*  936 */         float asinc22 = asinc2[i2sinc];
/*  937 */         float[] yuk2 = yu[ky2u];
/*  938 */         float yr2 = 0.0F;
/*  939 */         for (int i1sinc = 0, my1u = ky1u; i1sinc < this._lsinc; i1sinc++, my1u++)
/*  940 */           yr2 += yuk2[my1u] * asinc1[i1sinc]; 
/*  941 */         yr += asinc22 * yr2;
/*      */       } 
/*  943 */     } else if (this._extrap == Extrapolation.ZERO) {
/*  944 */       for (int i2sinc = 0; i2sinc < this._lsinc; i2sinc++, ky2u++) {
/*  945 */         if (0 <= ky2u && ky2u < nx2u)
/*  946 */           for (int i1sinc = 0, my1u = ky1u; i1sinc < this._lsinc; i1sinc++, my1u++) {
/*  947 */             if (0 <= my1u && my1u < nx1u) {
/*  948 */               yr += yu[ky2u][my1u] * asinc2[i2sinc] * asinc1[i1sinc];
/*      */             }
/*      */           }  
/*      */       } 
/*  952 */     } else if (this._extrap == Extrapolation.CONSTANT) {
/*  953 */       for (int i2sinc = 0; i2sinc < this._lsinc; i2sinc++, ky2u++) {
/*  954 */         int jy2u = (ky2u < 0) ? 0 : ((nx2u <= ky2u) ? (nx2u - 2) : ky2u);
/*  955 */         for (int i1sinc = 0, my1u = ky1u; i1sinc < this._lsinc; i1sinc++, my1u++) {
/*  956 */           int jy1u = (my1u < 0) ? 0 : ((nx1u <= my1u) ? (nx1u - 1) : my1u);
/*  957 */           yr += yu[jy2u][jy1u] * asinc2[i2sinc] * asinc1[i1sinc];
/*      */         } 
/*      */       } 
/*      */     } 
/*  961 */     return yr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float interpolate(double x1scale, double x1shift, int nx1um, int nx1u, double x2scale, double x2shift, int nx2um, int nx2u, double x3scale, double x3shift, int nx3um, int nx3u, float[][][] yu, double x1, double x2, double x3) {
/*  971 */     double x1n = x1shift + x1 * x1scale;
/*  972 */     double x2n = x2shift + x2 * x2scale;
/*  973 */     double x3n = x3shift + x3 * x3scale;
/*  974 */     int ix1n = (int)x1n;
/*  975 */     int ix2n = (int)x2n;
/*  976 */     int ix3n = (int)x3n;
/*  977 */     int ky1u = this._ishift + ix1n;
/*  978 */     int ky2u = this._ishift + ix2n;
/*  979 */     int ky3u = this._ishift + ix3n;
/*      */ 
/*      */     
/*  982 */     double frac1 = x1n - ix1n;
/*  983 */     double frac2 = x2n - ix2n;
/*  984 */     double frac3 = x3n - ix3n;
/*  985 */     if (frac1 < 0.0D)
/*  986 */       frac1++; 
/*  987 */     if (frac2 < 0.0D)
/*  988 */       frac2++; 
/*  989 */     if (frac3 < 0.0D)
/*  990 */       frac3++; 
/*  991 */     int ksinc1 = (int)(frac1 * this._nsincm1 + 0.5D);
/*  992 */     int ksinc2 = (int)(frac2 * this._nsincm1 + 0.5D);
/*  993 */     int ksinc3 = (int)(frac3 * this._nsincm1 + 0.5D);
/*  994 */     float[] asinc1 = this._asinc[ksinc1];
/*  995 */     float[] asinc2 = this._asinc[ksinc2];
/*  996 */     float[] asinc3 = this._asinc[ksinc3];
/*      */ 
/*      */ 
/*      */     
/* 1000 */     float yr = 0.0F;
/* 1001 */     if (ky1u >= 0 && ky1u <= nx1um && ky2u >= 0 && ky2u <= nx2um && ky3u >= 0 && ky3u <= nx3um) {
/*      */ 
/*      */       
/* 1004 */       for (int i3sinc = 0; i3sinc < this._lsinc; i3sinc++, ky3u++) {
/* 1005 */         float asinc33 = asinc3[i3sinc];
/* 1006 */         float[][] yu3 = yu[ky3u];
/* 1007 */         float yr2 = 0.0F;
/* 1008 */         for (int i2sinc = 0, my2u = ky2u; i2sinc < this._lsinc; i2sinc++, my2u++) {
/* 1009 */           float asinc22 = asinc2[i2sinc];
/* 1010 */           float[] yu32 = yu3[my2u];
/* 1011 */           float yr1 = 0.0F;
/* 1012 */           for (int i1sinc = 0, my1u = ky1u; i1sinc < this._lsinc; i1sinc++, my1u++)
/* 1013 */             yr1 += yu32[my1u] * asinc1[i1sinc]; 
/* 1014 */           yr2 += asinc22 * yr1;
/*      */         } 
/* 1016 */         yr += asinc33 * yr2;
/*      */       } 
/* 1018 */     } else if (this._extrap == Extrapolation.ZERO) {
/* 1019 */       for (int i3sinc = 0; i3sinc < this._lsinc; i3sinc++, ky3u++) {
/* 1020 */         if (0 <= ky3u && ky3u < nx3u) {
/* 1021 */           for (int i2sinc = 0, my2u = ky2u; i2sinc < this._lsinc; i2sinc++, my2u++) {
/* 1022 */             if (0 <= my2u && my2u < nx2u) {
/* 1023 */               for (int i1sinc = 0, my1u = ky1u; i1sinc < this._lsinc; i1sinc++, my1u++) {
/* 1024 */                 if (0 <= my1u && my1u < nx1u) {
/* 1025 */                   yr += yu[ky3u][my2u][my1u] * asinc3[i3sinc] * asinc2[i2sinc] * asinc1[i1sinc];
/*      */                 }
/*      */               }
/*      */             
/*      */             }
/*      */           }
/*      */         
/*      */         }
/*      */       } 
/* 1034 */     } else if (this._extrap == Extrapolation.CONSTANT) {
/* 1035 */       for (int i3sinc = 0; i3sinc < this._lsinc; i3sinc++, ky3u++) {
/* 1036 */         int jy3u = (ky3u < 0) ? 0 : ((nx3u <= ky3u) ? (nx3u - 2) : ky3u);
/* 1037 */         for (int i2sinc = 0, my2u = ky2u; i2sinc < this._lsinc; i2sinc++, my2u++) {
/* 1038 */           int jy2u = (my2u < 0) ? 0 : ((nx2u <= my2u) ? (nx2u - 2) : my2u);
/* 1039 */           for (int i1sinc = 0, my1u = ky1u; i1sinc < this._lsinc; i1sinc++, my1u++) {
/* 1040 */             int jy1u = (my1u < 0) ? 0 : ((nx1u <= my1u) ? (nx1u - 1) : my1u);
/* 1041 */             yr += yu[jy3u][jy2u][jy1u] * asinc3[i3sinc] * asinc2[i2sinc] * asinc1[i1sinc];
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1049 */     return yr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void interpolateComplex(double xscale, double xshift, int nxum, int nxu, float[] yu, int ix, double x, float[] y) {
/* 1057 */     double xn = xshift + x * xscale;
/* 1058 */     int ixn = (int)xn;
/* 1059 */     int kyu = this._ishift + ixn;
/*      */ 
/*      */     
/* 1062 */     double frac = xn - ixn;
/* 1063 */     if (frac < 0.0D)
/* 1064 */       frac++; 
/* 1065 */     int ksinc = (int)(frac * this._nsincm1 + 0.5D);
/* 1066 */     float[] asinc = this._asinc[ksinc];
/*      */ 
/*      */ 
/*      */     
/* 1070 */     float yr = 0.0F;
/* 1071 */     float yi = 0.0F;
/* 1072 */     if (kyu >= 0 && kyu <= nxum) {
/* 1073 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++) {
/* 1074 */         int jyu = 2 * kyu;
/* 1075 */         float asinci = asinc[isinc];
/* 1076 */         yr += yu[jyu] * asinci;
/* 1077 */         yi += yu[jyu + 1] * asinci;
/*      */       } 
/* 1079 */     } else if (this._extrap == Extrapolation.ZERO) {
/* 1080 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++) {
/* 1081 */         if (0 <= kyu && kyu < nxu) {
/* 1082 */           int jyu = 2 * kyu;
/* 1083 */           float asinci = asinc[isinc];
/* 1084 */           yr += yu[jyu] * asinci;
/* 1085 */           yi += yu[jyu + 1] * asinci;
/*      */         } 
/*      */       } 
/* 1088 */     } else if (this._extrap == Extrapolation.CONSTANT) {
/* 1089 */       for (int isinc = 0; isinc < this._lsinc; isinc++, kyu++) {
/* 1090 */         int jyu = (kyu < 0) ? 0 : ((nxu <= kyu) ? (2 * nxu - 2) : (2 * kyu));
/* 1091 */         float asinci = asinc[isinc];
/* 1092 */         yr += yu[jyu] * asinci;
/* 1093 */         yi += yu[jyu + 1] * asinci;
/*      */       } 
/*      */     } 
/* 1096 */     int jx = 2 * ix;
/* 1097 */     y[jx] = yr;
/* 1098 */     y[jx + 1] = yi;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/SincInterp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */