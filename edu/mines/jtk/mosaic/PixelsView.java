/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.awt.ColorMap;
/*     */ import edu.mines.jtk.awt.ColorMapListener;
/*     */ import edu.mines.jtk.awt.ColorMapped;
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import edu.mines.jtk.util.Clips;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.SampleModel;
/*     */ import java.awt.image.SinglePixelPackedSampleModel;
/*     */ import java.awt.image.WritableRaster;
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
/*     */ public class PixelsView
/*     */   extends TiledView
/*     */   implements ColorMapped
/*     */ {
/*     */   private int _nc;
/*     */   private Sampling _s1;
/*     */   private Sampling _s2;
/*     */   private float[][][] _f;
/*     */   private Orientation _orientation;
/*     */   private Interpolation _interpolation;
/*     */   Clips[] _clips;
/*     */   float[] _clipMin;
/*     */   float[] _clipMax;
/*     */   private ColorMap _colorMap;
/*     */   private boolean _transposed;
/*     */   private boolean _xflipped;
/*     */   private boolean _yflipped;
/*     */   private int _nx;
/*     */   private double _dx;
/*     */   private double _fx;
/*     */   private int _ny;
/*     */   private double _dy;
/*     */   private double _fy;
/*     */   
/*     */   public enum Orientation
/*     */   {
/*  66 */     X1RIGHT_X2UP,
/*  67 */     X1DOWN_X2RIGHT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Interpolation
/*     */   {
/*  77 */     NEAREST,
/*  78 */     LINEAR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PixelsView(float[][] f) {
/*  88 */     this(new float[][][] { f });
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
/*     */   public PixelsView(float[][][] f)
/*     */   {
/* 568 */     this._orientation = Orientation.X1RIGHT_X2UP;
/*     */ 
/*     */     
/* 571 */     this._interpolation = Interpolation.LINEAR;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 579 */     this._colorMap = new ColorMap(ColorMap.GRAY); set(f); } public PixelsView(Sampling s1, Sampling s2, float[][] f) { this(s1, s2, new float[][][] { f }); } public PixelsView(Sampling s1, Sampling s2, float[][][] f) { this._orientation = Orientation.X1RIGHT_X2UP; this._interpolation = Interpolation.LINEAR; this._colorMap = new ColorMap(ColorMap.GRAY); set(s1, s2, f); } public void set(float[][] f) { set(new float[][][] { f }); }
/*     */   public void set(float[][][] f) { if (this._s1 != null && this._s1.getCount() == (f[0][0]).length && this._s2 != null && this._s2.getCount() == (f[0]).length) { set(this._s1, this._s2, f); } else { set(new Sampling((f[0][0]).length), new Sampling((f[0]).length), f); }  }
/*     */   public void set(Sampling s1, Sampling s2, float[][] f) { set(s1, s2, new float[][][] { f }); }
/*     */   public void set(Sampling s1, Sampling s2, float[][][] f) { Check.argument(s1.isUniform(), "s1 is uniform"); Check.argument(s2.isUniform(), "s2 is uniform"); Check.argument(ArrayMath.isRegular(f), "f is regular"); Check.argument((s1.getCount() == (f[0][0]).length), "s1 consistent with f"); Check.argument((s2.getCount() == (f[0]).length), "s2 consistent with f"); Check.argument((this._nc != 0 || f.length == 1 || f.length == 3 || f.length == 4), "number of sampled functions is one, three, or four"); Check.argument((this._nc == 0 || this._nc == f.length), "number of sampled functions is same as when view constructed"); this._nc = f.length; this._s1 = s1; this._s2 = s2; this._f = ArrayMath.copy(f); if (this._clips == null) { this._clips = new Clips[this._nc]; for (int ic = 0; ic < this._nc; ic++) this._clips[ic] = new Clips(this._f[ic]);  } else { for (int ic = 0; ic < this._nc; ic++) this._clips[ic].setArray(this._f[ic]);  }  this._clipMin = null; this._clipMax = null; updateSampling(); repaint(); }
/*     */   public void setOrientation(Orientation orientation) { if (this._orientation != orientation) { this._orientation = orientation; updateSampling(); repaint(); }  }
/*     */   public Orientation getOrientation() { return this._orientation; }
/*     */   public void setInterpolation(Interpolation interpolation) { if (this._interpolation != interpolation) { this._interpolation = interpolation; updateBestProjectors(); repaint(); }  }
/*     */   public Interpolation getInterpolation() { return this._interpolation; }
/*     */   public void setColorModel(IndexColorModel colorModel) { if (this._nc == 1) { this._colorMap.setColorModel(colorModel); repaint(); }  }
/*     */   public IndexColorModel getColorModel() { return (this._nc == 1) ? this._colorMap.getColorModel() : null; }
/*     */   public void setClips(float clipMin, float clipMax) { for (int ic = 0; ic < this._nc; ic++)
/*     */       setClips(ic, clipMin, clipMax);  }
/*     */   public float getClipMin() { return getClipMin(0); }
/*     */   public float getClipMax() { return this._clips[0].getClipMax(); }
/* 593 */   private void checkComponent(int ic) { Check.argument((ic < this._nc), "valid index for color component"); } public void setPercentiles(float percMin, float percMax) { for (int ic = 0; ic < this._nc; ic++)
/*     */       setPercentiles(ic, percMin, percMax);  }
/*     */   public float getPercentileMin() { return this._clips[0].getPercentileMin(); }
/*     */   public float getPercentileMax() { return this._clips[0].getPercentileMax(); }
/*     */   public void setClips(int ic, float clipMin, float clipMax) { checkComponent(ic); this._clips[ic].setClips(clipMin, clipMax); repaint(); }
/*     */   public float getClipMin(int ic) { return this._clips[ic].getClipMin(); }
/*     */   public float getClipMax(int ic) { return this._clips[ic].getClipMax(); }
/* 600 */   private void updateClips() { if (this._clipMin == null)
/* 601 */       this._clipMin = new float[this._nc]; 
/* 602 */     if (this._clipMax == null)
/* 603 */       this._clipMax = new float[this._nc]; 
/* 604 */     for (int ic = 0; ic < this._nc; ic++) {
/* 605 */       float clipMin = this._clips[ic].getClipMin();
/* 606 */       float clipMax = this._clips[ic].getClipMax();
/* 607 */       if (this._clipMin[ic] != clipMin || this._clipMax[ic] != clipMax) {
/* 608 */         this._clipMin[ic] = clipMin;
/* 609 */         this._clipMax[ic] = clipMax;
/* 610 */         if (this._nc == 1)
/* 611 */           this._colorMap.setValueRange(clipMin, clipMax); 
/*     */       } 
/*     */     }  }
/*     */   
/*     */   public void setPercentiles(int ic, float percMin, float percMax) {
/*     */     this._clips[ic].setPercentiles(percMin, percMax);
/*     */     repaint();
/*     */   }
/*     */   public float getPercentileMin(int ic) {
/*     */     return this._clips[ic].getPercentileMin();
/*     */   }
/*     */   public float getPercentileMax(int ic) {
/*     */     return this._clips[ic].getPercentileMax();
/*     */   }
/*     */   
/* 626 */   private void updateSampling() { int n1 = this._s1.getCount();
/* 627 */     int n2 = this._s2.getCount();
/* 628 */     double d1 = this._s1.getDelta();
/* 629 */     double d2 = this._s2.getDelta();
/* 630 */     double f1 = this._s1.getFirst();
/* 631 */     double f2 = this._s2.getFirst();
/* 632 */     if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/* 633 */       this._transposed = true;
/* 634 */       this._xflipped = false;
/* 635 */       this._yflipped = false;
/* 636 */       this._nx = n2;
/* 637 */       this._dx = d2;
/* 638 */       this._fx = f2;
/* 639 */       this._ny = n1;
/* 640 */       this._dy = d1;
/* 641 */       this._fy = f1;
/* 642 */     } else if (this._orientation == Orientation.X1RIGHT_X2UP) {
/* 643 */       this._transposed = false;
/* 644 */       this._xflipped = false;
/* 645 */       this._yflipped = true;
/* 646 */       this._nx = n1;
/* 647 */       this._dx = d1;
/* 648 */       this._fx = f1;
/* 649 */       this._ny = n2;
/* 650 */       this._dy = d2;
/* 651 */       this._fy = f2;
/*     */     } 
/* 653 */     updateBestProjectors(); }
/*     */   
/*     */   public void addColorMapListener(ColorMapListener cml) {
/*     */     this._colorMap.addListener(cml);
/*     */   }
/*     */   
/*     */   public void removeColorMapListener(ColorMapListener cml) {
/*     */     this._colorMap.removeListener(cml);
/*     */   }
/*     */   
/* 663 */   private void updateBestProjectors() { double x0 = this._fx;
/* 664 */     double x1 = this._fx + this._dx * (this._nx - 1);
/* 665 */     double y0 = this._fy;
/* 666 */     double y1 = this._fy + this._dy * (this._ny - 1);
/* 667 */     if (this._xflipped) {
/* 668 */       double xt = y0;
/* 669 */       x0 = x1;
/* 670 */       x1 = xt;
/*     */     } 
/* 672 */     if (this._yflipped) {
/* 673 */       double yt = y0;
/* 674 */       y0 = y1;
/* 675 */       y1 = yt;
/*     */     } 
/*     */ 
/*     */     
/* 679 */     if (x0 == x1) {
/* 680 */       double tiny = ArrayMath.max(0.5D, 1.1920928955078125E-7D * ArrayMath.abs(x0));
/* 681 */       x0 -= tiny;
/* 682 */       x1 += tiny;
/*     */     } 
/* 684 */     if (y0 == y1) {
/* 685 */       double tiny = ArrayMath.max(0.5D, 1.1920928955078125E-7D * ArrayMath.abs(y0));
/* 686 */       y0 -= tiny;
/* 687 */       y1 += tiny;
/*     */     } 
/*     */ 
/*     */     
/* 691 */     double uxMargin = (this._nx > 1) ? (0.5D / this._nx) : 0.0D;
/* 692 */     double uyMargin = (this._ny > 1) ? (0.5D / this._ny) : 0.0D;
/*     */ 
/*     */ 
/*     */     
/* 696 */     double ux0 = uxMargin;
/* 697 */     double uy0 = uyMargin;
/* 698 */     double ux1 = 1.0D - uxMargin;
/* 699 */     double uy1 = 1.0D - uyMargin;
/*     */ 
/*     */     
/* 702 */     Projector bhp = new Projector(x0, x1, ux0, ux1);
/* 703 */     Projector bvp = new Projector(y0, y1, uy0, uy1);
/* 704 */     setBestProjectors(bhp, bvp); }
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
/*     */   private byte[] interpolateImageBytesLinear(float[][] f, float clipMin, float clipMax, int nx, double dx, double fx, int ny, double dy, double fy) {
/* 718 */     byte[] b = new byte[nx * ny];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 726 */     float[] temp1 = new float[nx];
/* 727 */     float[] temp2 = new float[nx];
/* 728 */     int jy1 = -2;
/*     */ 
/*     */     
/* 731 */     int[] kf = new int[nx];
/* 732 */     float[] wf = new float[nx];
/* 733 */     for (int ix = 0; ix < nx; ix++) {
/* 734 */       double xi = fx + ix * dx;
/* 735 */       double xn = (xi - this._fx) / this._dx;
/* 736 */       if (xn <= 0.0D) {
/* 737 */         kf[ix] = 0;
/* 738 */         wf[ix] = 0.0F;
/* 739 */       } else if (xn >= (this._nx - 1)) {
/* 740 */         kf[ix] = this._nx - 2;
/* 741 */         wf[ix] = 1.0F;
/*     */       } else {
/* 743 */         kf[ix] = (int)xn;
/* 744 */         wf[ix] = (float)(xn - (int)xn);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 749 */     for (int iy = 0; iy < ny; iy++) {
/* 750 */       double yi = fy + iy * dy;
/*     */ 
/*     */       
/* 753 */       double yn = ArrayMath.max(0.0D, ArrayMath.min((this._ny - 1), (yi - this._fy) / this._dy));
/* 754 */       int jy = ArrayMath.max(0, ArrayMath.min(this._ny - 2, (int)yn));
/*     */ 
/*     */       
/* 757 */       if (jy != jy1 || iy == 0) {
/*     */ 
/*     */         
/* 760 */         if (jy == jy1 + 1 && iy != 0) {
/* 761 */           float[] temp = temp1;
/* 762 */           temp1 = temp2;
/* 763 */           temp2 = temp;
/* 764 */           interpx(f, clipMin, clipMax, ArrayMath.min(jy + 1, this._ny - 1), nx, kf, wf, temp2);
/*     */ 
/*     */         
/*     */         }
/* 768 */         else if (jy == jy1 - 1 && iy != 0) {
/* 769 */           float[] temp = temp1;
/* 770 */           temp1 = temp2;
/* 771 */           temp2 = temp;
/* 772 */           interpx(f, clipMin, clipMax, jy, nx, kf, wf, temp1);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 777 */           interpx(f, clipMin, clipMax, jy, nx, kf, wf, temp1);
/* 778 */           interpx(f, clipMin, clipMax, ArrayMath.min(jy + 1, this._ny - 1), nx, kf, wf, temp2);
/*     */         } 
/*     */ 
/*     */         
/* 782 */         jy1 = jy;
/*     */       } 
/*     */ 
/*     */       
/* 786 */       double frac = yn - jy;
/* 787 */       interpy(nx, frac, temp1, temp2, iy * nx, b);
/*     */     } 
/* 789 */     return b;
/*     */   }
/*     */   public void paint(Graphics2D g2d) { updateClips(); Projector hp = getHorizontalProjector(); Projector vp = getVerticalProjector(); Transcaler ts = getTranscaler(); double vx0 = this._fx - 0.5D * this._dx; double vx1 = this._fx + this._dx * (this._nx - 0.5D); double vy0 = this._fy - 0.5D * this._dy; double vy1 = this._fy + this._dy * (this._ny - 0.5D); double ux0 = hp.u(vx0); double ux1 = hp.u(vx1); double uy0 = vp.u(vy0); double uy1 = vp.u(vy1); double uxmin = ArrayMath.min(ux0, ux1); double uxmax = ArrayMath.max(ux0, ux1); double uymin = ArrayMath.min(uy0, uy1); double uymax = ArrayMath.max(uy0, uy1); int xd = ts.x(uxmin); int yd = ts.y(uymin); int wd = ts.width(uxmax - uxmin); int hd = ts.height(uymax - uymin); Rectangle viewRect = new Rectangle(xd, yd, wd, hd); Rectangle clipRect = g2d.getClipBounds(); if (clipRect == null)
/*     */       clipRect = viewRect;  clipRect = clipRect.intersection(viewRect); if (clipRect.isEmpty())
/*     */       return;  int xc = clipRect.x; int yc = clipRect.y; int wc = clipRect.width; int hc = clipRect.height; double xu = ts.x(xc); double yu = ts.y(yc); double wu = ts.width(wc); double hu = ts.height(hc); double x0 = hp.v(xu); double y0 = vp.v(yu); double x1 = hp.v(xu + wu); double y1 = vp.v(yu + hu); int nx = wc; int ny = hc; int nxy = nx * ny; double dx = (x1 - x0) / ArrayMath.max(1, nx - 1); double dy = (y1 - y0) / ArrayMath.max(1, ny - 1); double fx = x0; double fy = y0; if (this._nc == 1) { float[][] f = this._f[0]; float clipMin = this._clipMin[0]; float clipMax = this._clipMax[0]; byte[] b = (this._interpolation == Interpolation.LINEAR) ? interpolateImageBytesLinear(f, clipMin, clipMax, nx, dx, fx, ny, dy, fy) : interpolateImageBytesNearest(f, clipMin, clipMax, nx, dx, fx, ny, dy, fy); ColorModel cm = this._colorMap.getColorModel(); DataBuffer db = new DataBufferByte(b, nxy, 0); int dt = 0; int[] bm = { 255 }; SampleModel sm = new SinglePixelPackedSampleModel(dt, nx, ny, bm); WritableRaster wr = Raster.createWritableRaster(sm, db, null); BufferedImage bi = new BufferedImage(cm, wr, false, null); g2d.drawImage(bi, xc, yc, (ImageObserver)null); }
/*     */     else { ColorModel cm; int[] bm; byte[][] b = new byte[this._nc][]; for (int ic = 0; ic < this._nc; ic++) { float[][] f = this._f[ic]; float clipMin = this._clipMin[ic]; float clipMax = this._clipMax[ic]; b[ic] = (this._interpolation == Interpolation.LINEAR) ? interpolateImageBytesLinear(f, clipMin, clipMax, nx, dx, fx, ny, dy, fy) : interpolateImageBytesNearest(f, clipMin, clipMax, nx, dx, fx, ny, dy, fy); }
/*     */        int[] i = new int[nxy]; if (this._nc == 3) { cm = new DirectColorModel(24, 16711680, 65280, 255); bm = new int[] { 16711680, 65280, 255 }; byte[] b0 = b[0], b1 = b[1], b2 = b[2]; for (int ixy = 0; ixy < nxy; ixy++)
/*     */           i[ixy] = (b0[ixy] & 0xFF) << 16 | (b1[ixy] & 0xFF) << 8 | b2[ixy] & 0xFF;  }
/*     */       else { cm = new DirectColorModel(32, 16711680, 65280, 255, -16777216); bm = new int[] { 16711680, 65280, 255, -16777216 }; byte[] b0 = b[0], b1 = b[1], b2 = b[2], b3 = b[3]; for (int ixy = 0; ixy < nxy; ixy++)
/*     */           i[ixy] = (b3[ixy] & 0xFF) << 24 | (b0[ixy] & 0xFF) << 16 | (b1[ixy] & 0xFF) << 8 | b2[ixy] & 0xFF;  }
/*     */        DataBuffer db = new DataBufferInt(i, nxy, 0); int dt = 3; SampleModel sm = new SinglePixelPackedSampleModel(dt, nx, ny, bm); WritableRaster wr = Raster.createWritableRaster(sm, db, null); BufferedImage bi = new BufferedImage(cm, wr, false, null); g2d.drawImage(bi, xc, yc, (ImageObserver)null); }
/* 800 */      } public ColorMap getColorMap() { return this._colorMap; } private void interpx(float[][] f, float clipMin, float clipMax, int jy, int nx, int[] kf, float[] wf, float[] t) { float fscale = 255.0F / (clipMax - clipMin);
/* 801 */     float fshift = clipMin;
/* 802 */     if (this._transposed) {
/* 803 */       if (this._nx == 1) {
/* 804 */         float fc = (f[0][jy] - fshift) * fscale;
/* 805 */         for (int ix = 0; ix < nx; ix++)
/* 806 */           t[ix] = fc; 
/*     */       } else {
/* 808 */         for (int ix = 0; ix < nx; ix++) {
/* 809 */           int kx = kf[ix];
/* 810 */           float wx = wf[ix];
/* 811 */           float f1 = (f[kx][jy] - fshift) * fscale;
/* 812 */           float f2 = (f[kx + 1][jy] - fshift) * fscale;
/* 813 */           t[ix] = (1.0F - wx) * f1 + wx * f2;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 817 */       float[] fjy = f[jy];
/* 818 */       if (this._nx == 1) {
/* 819 */         float f0 = (fjy[0] - fshift) * fscale;
/* 820 */         for (int ix = 0; ix < nx; ix++)
/* 821 */           t[ix] = f0; 
/*     */       } else {
/* 823 */         for (int ix = 0; ix < nx; ix++) {
/* 824 */           int kx = kf[ix];
/* 825 */           float wx = wf[ix];
/* 826 */           float f1 = (fjy[kx] - fshift) * fscale;
/* 827 */           float f2 = (fjy[kx + 1] - fshift) * fscale;
/* 828 */           t[ix] = (1.0F - wx) * f1 + wx * f2;
/*     */         } 
/*     */       } 
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void interpy(int nx, double frac, float[] temp1, float[] temp2, int kb, byte[] b) {
/* 840 */     float w2 = (float)frac;
/* 841 */     float w1 = 1.0F - w2;
/* 842 */     for (int ix = 0, ib = kb; ix < nx; ix++, ib++) {
/* 843 */       float ti = w1 * temp1[ix] + w2 * temp2[ix];
/* 844 */       if (ti < 0.0F)
/* 845 */         ti = 0.0F; 
/* 846 */       if (ti > 255.0F)
/* 847 */         ti = 255.0F; 
/* 848 */       b[ib] = (byte)(int)(ti + 0.5F);
/*     */     } 
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
/*     */   private byte[] interpolateImageBytesNearest(float[][] f, float clipMin, float clipMax, int nx, double dx, double fx, int ny, double dy, double fy) {
/* 863 */     byte[] b = new byte[nx * ny];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 869 */     byte[] temp = new byte[nx];
/* 870 */     int jytemp = -1;
/*     */ 
/*     */     
/* 873 */     int[] kf = new int[nx];
/* 874 */     for (int ix = 0; ix < nx; ix++) {
/* 875 */       double xi = fx + ix * dx;
/* 876 */       double xn = (xi - this._fx) / this._dx;
/* 877 */       if (xn <= 0.0D) {
/* 878 */         kf[ix] = 0;
/* 879 */       } else if (xn >= (this._nx - 1)) {
/* 880 */         kf[ix] = this._nx - 1;
/*     */       } else {
/* 882 */         kf[ix] = (int)(xn + 0.5D);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 887 */     for (int iy = 0; iy < ny; iy++) {
/* 888 */       double yi = fy + iy * dy;
/*     */ 
/*     */       
/* 891 */       double yn = ArrayMath.max(0.0D, ArrayMath.min((this._ny - 1), (yi - this._fy) / this._dy));
/* 892 */       int jy = ArrayMath.max(0, ArrayMath.min(this._ny - 1, (int)(yn + 0.5D)));
/*     */ 
/*     */       
/* 895 */       if (jy != jytemp) {
/* 896 */         interpx(f, clipMin, clipMax, jy, nx, kf, temp);
/* 897 */         jytemp = jy;
/*     */       } 
/*     */ 
/*     */       
/* 901 */       for (int i = 0, j = iy * nx; i < nx; i++, j++) {
/* 902 */         b[j] = temp[i];
/*     */       }
/*     */     } 
/* 905 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void interpx(float[][] f, float clipMin, float clipMax, int jy, int nx, int[] kf, byte[] b) {
/* 915 */     float fscale = 255.0F / (clipMax - clipMin);
/* 916 */     float fshift = clipMin;
/* 917 */     if (this._transposed) {
/* 918 */       for (int ix = 0; ix < nx; ix++) {
/* 919 */         int kx = kf[ix];
/* 920 */         float fi = (f[kx][jy] - fshift) * fscale;
/* 921 */         if (fi < 0.0F)
/* 922 */           fi = 0.0F; 
/* 923 */         if (fi > 255.0F)
/* 924 */           fi = 255.0F; 
/* 925 */         b[ix] = (byte)(int)(fi + 0.5F);
/*     */       } 
/*     */     } else {
/* 928 */       float[] fjy = f[jy];
/* 929 */       for (int ix = 0; ix < nx; ix++) {
/* 930 */         int kx = kf[ix];
/* 931 */         float fi = (fjy[kx] - fshift) * fscale;
/* 932 */         if (fi < 0.0F)
/* 933 */           fi = 0.0F; 
/* 934 */         if (fi > 255.0F)
/* 935 */           fi = 255.0F; 
/* 936 */         b[ix] = (byte)(int)(fi + 0.5F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/PixelsView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */