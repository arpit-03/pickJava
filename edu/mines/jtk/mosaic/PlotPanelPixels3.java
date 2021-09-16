/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.awt.ColorMap;
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Clips;
/*     */ import edu.mines.jtk.util.Float3;
/*     */ import edu.mines.jtk.util.SimpleFloat3;
/*     */ import java.awt.Color;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.IndexColorModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlotPanelPixels3
/*     */   extends PlotPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int _n1;
/*     */   private int _n2;
/*     */   private int _n3;
/*     */   private int _k1;
/*     */   private int _k2;
/*     */   private int _k3;
/*     */   private Sampling _s1;
/*     */   private Sampling _s2;
/*     */   private Sampling _s3;
/*     */   private PixelsView _p12;
/*     */   private PixelsView _p13;
/*     */   private PixelsView _p23;
/*     */   private PointsView _l12;
/*     */   private PointsView _l13;
/*     */   private PointsView _l23;
/*     */   private boolean _transpose23;
/*     */   private int _nc;
/*     */   private Clips[] _clips;
/*     */   private Float3[] _f3;
/*     */   private ColorMap _colorMap;
/*     */   private Orientation _orientation;
/*     */   private AxesPlacement _axesPlacement;
/*     */   
/*     */   public enum Orientation
/*     */   {
/*  61 */     X1DOWN,
/*  62 */     X1DOWN_X2RIGHT,
/*  63 */     X1DOWN_X3RIGHT,
/*  64 */     X1RIGHT,
/*  65 */     X1RIGHT_X2UP,
/*  66 */     X1RIGHT_X3UP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum AxesPlacement
/*     */   {
/*  73 */     LEFT_BOTTOM,
/*  74 */     NONE;
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
/*     */   public PlotPanelPixels3(Orientation orientation, AxesPlacement axesPlacement, Sampling s1, Sampling s2, Sampling s3, float[][][] f) {
/*  90 */     this(orientation, axesPlacement, s1, s2, s3, (Float3)new SimpleFloat3(f));
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
/*     */   public PlotPanelPixels3(Orientation orientation, AxesPlacement axesPlacement, Sampling s1, Sampling s2, Sampling s3, float[][][][] f) {
/* 106 */     this(orientation, axesPlacement, s1, s2, s3, toFloat3(f));
/*     */   }
/*     */   private static Float3[] toFloat3(float[][][][] f) {
/* 109 */     Float3[] f3 = new Float3[f.length];
/* 110 */     for (int i = 0; i < f.length; i++)
/* 111 */       f3[i] = (Float3)new SimpleFloat3(f[i]); 
/* 112 */     return f3;
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
/*     */   public PlotPanelPixels3(Orientation orientation, AxesPlacement axesPlacement, Sampling s1, Sampling s2, Sampling s3, Float3 f3) {
/* 128 */     this(orientation, axesPlacement, s1, s2, s3, new Float3[] { f3 });
/*     */   }
/*     */   public PixelsView getPixelsView12() { return this._p12; }
/*     */   public PixelsView getPixelsView13() { return this._p13; }
/*     */   public PixelsView getPixelsView23() { return this._p23; }
/*     */   public void setSlice23(int k1) { setSlices(k1, this._k2, this._k3); }
/*     */   public void setSlice13(int k2) { setSlices(this._k1, k2, this._k3); }
/*     */   public void setSlice12(int k3) { setSlices(this._k1, this._k2, k3); }
/*     */   public void setSlices(int k1, int k2, int k3) { if (this._k1 != k1) { this._k1 = k1; if (this._transpose23) { this._p23.set(this._s3, this._s2, slice23()); } else { this._p23.set(this._s2, this._s3, slice23()); }  this._l12.set(lines12a(), lines12b()); this._l13.set(lines13a(), lines13b()); }  if (this._k2 != k2) { this._k2 = k2; this._p13.set(this._s1, this._s3, slice13()); this._l12.set(lines12a(), lines12b()); this._l23.set(lines23a(), lines23b()); }  if (this._k3 != k3) { this._k3 = k3; this._p12.set(this._s1, this._s2, slice12()); this._l13.set(lines13a(), lines13b()); this._l23.set(lines23a(), lines23b()); }  }
/*     */   public void setInterpolation(PixelsView.Interpolation interpolation) { this._p12.setInterpolation(interpolation); this._p13.setInterpolation(interpolation); this._p23.setInterpolation(interpolation); }
/*     */   public void setLineColor(Color color) { if (color == null) { hideLines(); } else { showLines(); this._l12.setLineColor(color); this._l13.setLineColor(color); this._l23.setLineColor(color); }  }
/*     */   public void setLabel1(String label) { if (this._orientation == Orientation.X1DOWN_X2RIGHT) { setVLabel(1, label); } else if (this._orientation == Orientation.X1DOWN_X3RIGHT) { setVLabel(1, label); } else if (this._orientation == Orientation.X1RIGHT_X2UP) { setHLabel(0, label); } else if (this._orientation == Orientation.X1RIGHT_X3UP) { setHLabel(0, label); }  }
/*     */   public void setLabel2(String label) { if (this._orientation == Orientation.X1DOWN_X2RIGHT) { setHLabel(0, label); } else if (this._orientation == Orientation.X1DOWN_X3RIGHT) { setHLabel(1, label); setVLabel(0, label); } else if (this._orientation == Orientation.X1RIGHT_X2UP) { setVLabel(1, label); } else if (this._orientation == Orientation.X1RIGHT_X3UP) { setHLabel(1, label); setVLabel(0, label); }  } public void setLabel3(String label) { if (this._orientation == Orientation.X1DOWN_X2RIGHT) { setHLabel(1, label); setVLabel(0, label); }
/*     */     else if (this._orientation == Orientation.X1DOWN_X3RIGHT) { setHLabel(0, label); }
/*     */     else if (this._orientation == Orientation.X1RIGHT_X2UP) { setHLabel(1, label); setVLabel(0, label); }
/*     */     else if (this._orientation == Orientation.X1RIGHT_X3UP) { setVLabel(1, label); }
/* 144 */      } public PlotPanelPixels3(Orientation orientation, AxesPlacement axesPlacement, Sampling s1, Sampling s2, Sampling s3, Float3[] f3) { super(2, 2, 
/* 145 */         plotPanelOrientation(orientation), 
/* 146 */         plotPanelAxesPlacement(axesPlacement));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 590 */     this._colorMap = new ColorMap(ColorMap.GRAY); this._nc = f3.length; this._f3 = f3; this._clips = new Clips[this._nc]; for (int ic = 0; ic < this._nc; ic++)
/*     */       this._clips[ic] = new Clips(this._f3[ic]);  init(orientation, axesPlacement, s1, s2, s3); setPercentiles(0.0F, 100.0F); }
/*     */   public void setFormat1(String format) { if (this._orientation == Orientation.X1DOWN_X2RIGHT) { setVFormat(1, format); } else if (this._orientation == Orientation.X1DOWN_X3RIGHT) { setVFormat(1, format); } else if (this._orientation == Orientation.X1RIGHT_X2UP) { setHFormat(0, format); } else if (this._orientation == Orientation.X1RIGHT_X3UP) { setHFormat(0, format); }  }
/*     */   public void setFormat2(String format) { if (this._orientation == Orientation.X1DOWN_X2RIGHT) { setHFormat(0, format); } else if (this._orientation == Orientation.X1DOWN_X3RIGHT) { setHFormat(1, format); setVFormat(0, format); } else if (this._orientation == Orientation.X1RIGHT_X2UP) { setVFormat(1, format); } else if (this._orientation == Orientation.X1RIGHT_X3UP) { setHFormat(1, format); setVFormat(0, format); }  }
/*     */   public void setFormat3(String format) { if (this._orientation == Orientation.X1DOWN_X2RIGHT) { setHFormat(1, format); setVFormat(0, format); } else if (this._orientation == Orientation.X1DOWN_X3RIGHT) { setHFormat(0, format); } else if (this._orientation == Orientation.X1RIGHT_X2UP) { setHFormat(1, format); setVFormat(0, format); } else if (this._orientation == Orientation.X1RIGHT_X3UP) { setVFormat(1, format); }  }
/*     */   public void setInterval1(double interval) { if (this._orientation == Orientation.X1DOWN_X2RIGHT) { setVInterval(1, interval); } else if (this._orientation == Orientation.X1DOWN_X3RIGHT) { setVInterval(1, interval); } else if (this._orientation == Orientation.X1RIGHT_X2UP) { setHInterval(0, interval); }
/*     */     else if (this._orientation == Orientation.X1RIGHT_X3UP) { setHInterval(0, interval); }
/* 597 */      } private static PlotPanel.AxesPlacement plotPanelAxesPlacement(AxesPlacement axesPlacement) { if (axesPlacement == AxesPlacement.LEFT_BOTTOM) {
/* 598 */       return PlotPanel.AxesPlacement.LEFT_BOTTOM;
/*     */     }
/* 600 */     return PlotPanel.AxesPlacement.NONE; } public void setInterval2(double interval) { if (this._orientation == Orientation.X1DOWN_X2RIGHT) { setHInterval(0, interval); } else if (this._orientation == Orientation.X1DOWN_X3RIGHT) { setHInterval(1, interval); setVInterval(0, interval); } else if (this._orientation == Orientation.X1RIGHT_X2UP) { setVInterval(1, interval); } else if (this._orientation == Orientation.X1RIGHT_X3UP) { setHInterval(1, interval); setVInterval(0, interval); }  } public void setInterval3(double interval) { if (this._orientation == Orientation.X1DOWN_X2RIGHT) { setHInterval(1, interval); setVInterval(0, interval); } else if (this._orientation == Orientation.X1DOWN_X3RIGHT) { setHInterval(0, interval); } else if (this._orientation == Orientation.X1RIGHT_X2UP) { setHInterval(1, interval); setVInterval(0, interval); } else if (this._orientation == Orientation.X1RIGHT_X3UP) { setVInterval(1, interval); }  } public void setColorModel(IndexColorModel colorModel) { if (this._nc == 1) { this._colorMap.setColorModel(colorModel); this._p12.setColorModel(colorModel); this._p13.setColorModel(colorModel); this._p23.setColorModel(colorModel); }  }
/*     */   public IndexColorModel getColorModel() { return (this._nc == 1) ? this._colorMap.getColorModel() : null; }
/*     */   public void setClips(float clipMin, float clipMax) { for (int ic = 0; ic < this._nc; ic++) this._clips[ic].setClips(clipMin, clipMax);  this._p12.setClips(clipMin, clipMax); this._p13.setClips(clipMin, clipMax); this._p23.setClips(clipMin, clipMax); }
/*     */   public float getClipMin() { return this._clips[0].getClipMin(); }
/*     */   public float getClipMax() { return this._clips[0].getClipMax(); }
/*     */   public void setPercentiles(float percMin, float percMax) { for (int ic = 0; ic < this._nc; ic++) { this._clips[ic].setPercentiles(percMin, percMax); float clipMin = this._clips[ic].getClipMin(); float clipMax = this._clips[ic].getClipMax(); this._p12.setClips(clipMin, clipMax); this._p13.setClips(clipMin, clipMax); this._p23.setClips(clipMin, clipMax); }  }
/* 606 */   private static PlotPanel.Orientation plotPanelOrientation(Orientation orientation) { if (orientation == Orientation.X1DOWN || orientation == Orientation.X1DOWN_X2RIGHT || orientation == Orientation.X1DOWN_X3RIGHT)
/*     */     {
/*     */       
/* 609 */       return PlotPanel.Orientation.X1DOWN_X2RIGHT;
/*     */     }
/* 611 */     return PlotPanel.Orientation.X1RIGHT_X2UP; } public float getPercentileMin() { return this._clips[0].getPercentileMin(); }
/*     */   public float getPercentileMax() { return this._clips[0].getPercentileMax(); }
/*     */   public void setClips(int ic, float clipMin, float clipMax) { this._clips[ic].setClips(clipMin, clipMax); clipMin = this._clips[ic].getClipMin(); clipMax = this._clips[ic].getClipMax(); this._p12.setClips(ic, clipMin, clipMax); this._p13.setClips(ic, clipMin, clipMax); this._p23.setClips(ic, clipMin, clipMax); }
/*     */   public float getClipMin(int ic) { return this._clips[ic].getClipMin(); }
/*     */   public float getClipMax(int ic) { return this._clips[ic].getClipMax(); }
/*     */   public void setPercentiles(int ic, float percMin, float percMax) { this._clips[ic].setPercentiles(percMin, percMax); float clipMin = this._clips[ic].getClipMin(); float clipMax = this._clips[ic].getClipMax(); this._p12.setClips(ic, clipMin, clipMax); this._p13.setClips(ic, clipMin, clipMax); this._p23.setClips(ic, clipMin, clipMax); }
/*     */   public float getPercentileMin(int ic) { return this._clips[ic].getPercentileMin(); }
/*     */   public float getPercentileMax(int ic) { return this._clips[ic].getPercentileMax(); }
/* 619 */   private void init(Orientation orientation, AxesPlacement axesPlacement, Sampling s1, Sampling s2, Sampling s3) { this._s1 = s1;
/* 620 */     this._s2 = s2;
/* 621 */     this._s3 = s3;
/* 622 */     this._n1 = s1.getCount();
/* 623 */     this._n2 = s2.getCount();
/* 624 */     this._n3 = s3.getCount();
/* 625 */     this._k1 = this._n1 / 2;
/* 626 */     this._k2 = this._n2 / 2;
/* 627 */     this._k3 = this._n3 / 2;
/* 628 */     if (orientation == Orientation.X1DOWN) {
/* 629 */       orientation = (this._n2 > this._n3) ? Orientation.X1DOWN_X2RIGHT : Orientation.X1DOWN_X3RIGHT;
/*     */     
/*     */     }
/* 632 */     else if (orientation == Orientation.X1RIGHT) {
/* 633 */       orientation = (this._n2 > this._n3) ? Orientation.X1RIGHT_X2UP : Orientation.X1RIGHT_X3UP;
/*     */     } 
/*     */ 
/*     */     
/* 637 */     this._orientation = orientation;
/* 638 */     this._axesPlacement = axesPlacement;
/* 639 */     if (orientation == Orientation.X1DOWN_X2RIGHT) {
/* 640 */       this._transpose23 = false;
/* 641 */       this._p12 = addPixels(1, 0, s1, s2, slice12());
/* 642 */       this._p13 = addPixels(1, 1, s1, s3, slice13());
/* 643 */       this._p23 = addPixels(0, 0, s2, s3, slice23());
/* 644 */       this._p12.setOrientation(PixelsView.Orientation.X1DOWN_X2RIGHT);
/* 645 */       this._p13.setOrientation(PixelsView.Orientation.X1DOWN_X2RIGHT);
/* 646 */       this._p23.setOrientation(PixelsView.Orientation.X1RIGHT_X2UP);
/* 647 */       this._l12 = addPoints(1, 0, lines12a(), lines12b());
/* 648 */       this._l13 = addPoints(1, 1, lines13a(), lines13b());
/* 649 */       this._l23 = addPoints(0, 0, lines23a(), lines23b());
/* 650 */       this._l12.setOrientation(PointsView.Orientation.X1DOWN_X2RIGHT);
/* 651 */       this._l13.setOrientation(PointsView.Orientation.X1DOWN_X2RIGHT);
/* 652 */       this._l23.setOrientation(PointsView.Orientation.X1RIGHT_X2UP);
/* 653 */       getMosaic().setWidthElastic(0, 100 * this._n2 / this._n3);
/* 654 */       getMosaic().setHeightElastic(0, 100 * this._n3 / this._n1);
/* 655 */     } else if (orientation == Orientation.X1DOWN_X3RIGHT) {
/* 656 */       this._transpose23 = true;
/* 657 */       this._p12 = addPixels(1, 1, s1, s2, slice12());
/* 658 */       this._p13 = addPixels(1, 0, s1, s3, slice13());
/* 659 */       this._p23 = addPixels(0, 0, s3, s2, slice23());
/* 660 */       this._p12.setOrientation(PixelsView.Orientation.X1DOWN_X2RIGHT);
/* 661 */       this._p13.setOrientation(PixelsView.Orientation.X1DOWN_X2RIGHT);
/* 662 */       this._p23.setOrientation(PixelsView.Orientation.X1RIGHT_X2UP);
/* 663 */       this._l12 = addPoints(1, 1, lines12a(), lines12b());
/* 664 */       this._l13 = addPoints(1, 0, lines13a(), lines13b());
/* 665 */       this._l23 = addPoints(0, 0, lines23a(), lines23b());
/* 666 */       this._l12.setOrientation(PointsView.Orientation.X1DOWN_X2RIGHT);
/* 667 */       this._l13.setOrientation(PointsView.Orientation.X1DOWN_X2RIGHT);
/* 668 */       this._l23.setOrientation(PointsView.Orientation.X1RIGHT_X2UP);
/* 669 */       getMosaic().setWidthElastic(0, 100 * this._n3 / this._n2);
/* 670 */       getMosaic().setHeightElastic(0, 100 * this._n2 / this._n1);
/* 671 */     } else if (orientation == Orientation.X1RIGHT_X2UP) {
/* 672 */       this._transpose23 = true;
/* 673 */       this._p12 = addPixels(1, 0, s1, s2, slice12());
/* 674 */       this._p13 = addPixels(0, 0, s1, s3, slice13());
/* 675 */       this._p23 = addPixels(1, 1, s3, s2, slice23());
/* 676 */       this._p12.setOrientation(PixelsView.Orientation.X1RIGHT_X2UP);
/* 677 */       this._p13.setOrientation(PixelsView.Orientation.X1RIGHT_X2UP);
/* 678 */       this._p23.setOrientation(PixelsView.Orientation.X1RIGHT_X2UP);
/* 679 */       this._l12 = addPoints(1, 0, lines12a(), lines12b());
/* 680 */       this._l13 = addPoints(0, 0, lines13a(), lines13b());
/* 681 */       this._l23 = addPoints(1, 1, lines23a(), lines23b());
/* 682 */       this._l12.setOrientation(PointsView.Orientation.X1RIGHT_X2UP);
/* 683 */       this._l13.setOrientation(PointsView.Orientation.X1RIGHT_X2UP);
/* 684 */       this._l23.setOrientation(PointsView.Orientation.X1RIGHT_X2UP);
/* 685 */       getMosaic().setWidthElastic(0, 100 * this._n1 / this._n3);
/* 686 */       getMosaic().setHeightElastic(0, 100 * this._n3 / this._n2);
/* 687 */     } else if (orientation == Orientation.X1RIGHT_X3UP) {
/* 688 */       this._transpose23 = false;
/* 689 */       this._p12 = addPixels(0, 0, s1, s2, slice12());
/* 690 */       this._p13 = addPixels(1, 0, s1, s3, slice13());
/* 691 */       this._p23 = addPixels(1, 1, s2, s3, slice23());
/* 692 */       this._p12.setOrientation(PixelsView.Orientation.X1RIGHT_X2UP);
/* 693 */       this._p13.setOrientation(PixelsView.Orientation.X1RIGHT_X2UP);
/* 694 */       this._p23.setOrientation(PixelsView.Orientation.X1RIGHT_X2UP);
/* 695 */       this._l12 = addPoints(0, 0, lines12a(), lines12b());
/* 696 */       this._l13 = addPoints(1, 0, lines13a(), lines13b());
/* 697 */       this._l23 = addPoints(1, 1, lines23a(), lines23b());
/* 698 */       this._l12.setOrientation(PointsView.Orientation.X1RIGHT_X2UP);
/* 699 */       this._l13.setOrientation(PointsView.Orientation.X1RIGHT_X2UP);
/* 700 */       this._l23.setOrientation(PointsView.Orientation.X1RIGHT_X2UP);
/* 701 */       getMosaic().setWidthElastic(0, 100 * this._n1 / this._n2);
/* 702 */       getMosaic().setHeightElastic(0, 100 * this._n2 / this._n3);
/*     */     }  }
/*     */ 
/*     */   
/*     */   private float[][][] slice12() {
/* 707 */     float[][][] x = new float[this._nc][this._n2][this._n1];
/* 708 */     for (int ic = 0; ic < this._nc; ic++)
/* 709 */       this._f3[ic].get12(this._n1, this._n2, 0, 0, this._k3, x[ic]); 
/* 710 */     return x;
/*     */   }
/*     */   private float[][][] slice13() {
/* 713 */     float[][][] x = new float[this._nc][this._n3][this._n1];
/* 714 */     for (int ic = 0; ic < this._nc; ic++)
/* 715 */       this._f3[ic].get13(this._n1, this._n3, 0, this._k2, 0, x[ic]); 
/* 716 */     return x;
/*     */   }
/*     */   private float[][][] slice23() {
/* 719 */     float[][][] x = new float[this._nc][][];
/* 720 */     float[][] xic = new float[this._n3][this._n2];
/* 721 */     for (int ic = 0; ic < this._nc; ic++) {
/* 722 */       this._f3[ic].get23(this._n2, this._n3, this._k1, 0, 0, xic);
/* 723 */       x[ic] = this._transpose23 ? ArrayMath.transpose(xic) : ArrayMath.copy(xic);
/*     */     } 
/* 725 */     return x;
/*     */   }
/*     */   
/*     */   private float[][] lines12a() {
/* 729 */     float xa1 = (float)this._s1.getValue(0);
/* 730 */     float xk1 = (float)this._s1.getValue(this._k1);
/* 731 */     float xb1 = (float)this._s1.getValue(this._n1 - 1);
/* 732 */     return new float[][] { { xk1, xk1 }, { xa1, xb1 } };
/*     */   }
/*     */   private float[][] lines12b() {
/* 735 */     float xa2 = (float)this._s2.getValue(0);
/* 736 */     float xk2 = (float)this._s2.getValue(this._k2);
/* 737 */     float xb2 = (float)this._s2.getValue(this._n2 - 1);
/* 738 */     return new float[][] { { xa2, xb2 }, { xk2, xk2 } };
/*     */   }
/*     */   private float[][] lines13a() {
/* 741 */     float xa1 = (float)this._s1.getValue(0);
/* 742 */     float xk1 = (float)this._s1.getValue(this._k1);
/* 743 */     float xb1 = (float)this._s1.getValue(this._n1 - 1);
/* 744 */     return new float[][] { { xk1, xk1 }, { xa1, xb1 } };
/*     */   }
/*     */   private float[][] lines13b() {
/* 747 */     float xa3 = (float)this._s3.getValue(0);
/* 748 */     float xk3 = (float)this._s3.getValue(this._k3);
/* 749 */     float xb3 = (float)this._s3.getValue(this._n3 - 1);
/* 750 */     return new float[][] { { xa3, xb3 }, { xk3, xk3 } };
/*     */   }
/*     */   private float[][] lines23a() {
/* 753 */     if (this._transpose23) {
/* 754 */       float xa3 = (float)this._s3.getValue(0);
/* 755 */       float xk3 = (float)this._s3.getValue(this._k3);
/* 756 */       float xb3 = (float)this._s3.getValue(this._n3 - 1);
/* 757 */       return new float[][] { { xk3, xk3 }, { xa3, xb3 } };
/*     */     } 
/* 759 */     float xa2 = (float)this._s2.getValue(0);
/* 760 */     float xk2 = (float)this._s2.getValue(this._k2);
/* 761 */     float xb2 = (float)this._s2.getValue(this._n2 - 1);
/* 762 */     return new float[][] { { xk2, xk2 }, { xa2, xb2 } };
/*     */   }
/*     */   
/*     */   private float[][] lines23b() {
/* 766 */     if (this._transpose23) {
/* 767 */       float xa2 = (float)this._s2.getValue(0);
/* 768 */       float xk2 = (float)this._s2.getValue(this._k2);
/* 769 */       float xb2 = (float)this._s2.getValue(this._n2 - 1);
/* 770 */       return new float[][] { { xa2, xb2 }, { xk2, xk2 } };
/*     */     } 
/* 772 */     float xa3 = (float)this._s3.getValue(0);
/* 773 */     float xk3 = (float)this._s3.getValue(this._k3);
/* 774 */     float xb3 = (float)this._s3.getValue(this._n3 - 1);
/* 775 */     return new float[][] { { xa3, xb3 }, { xk3, xk3 } };
/*     */   }
/*     */ 
/*     */   
/*     */   private void showLines() {
/* 780 */     Mosaic mosaic = getMosaic();
/* 781 */     Tile t00 = mosaic.getTile(0, 0);
/* 782 */     Tile t10 = mosaic.getTile(1, 0);
/* 783 */     Tile t11 = mosaic.getTile(1, 1);
/* 784 */     if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/* 785 */       t00.addTiledView(this._l23);
/* 786 */       t10.addTiledView(this._l12);
/* 787 */       t11.addTiledView(this._l13);
/* 788 */     } else if (this._orientation == Orientation.X1DOWN_X3RIGHT) {
/* 789 */       t00.addTiledView(this._l23);
/* 790 */       t10.addTiledView(this._l13);
/* 791 */       t11.addTiledView(this._l12);
/* 792 */     } else if (this._orientation == Orientation.X1RIGHT_X2UP) {
/* 793 */       t00.addTiledView(this._l13);
/* 794 */       t10.addTiledView(this._l12);
/* 795 */       t11.addTiledView(this._l23);
/* 796 */     } else if (this._orientation == Orientation.X1RIGHT_X3UP) {
/* 797 */       t00.addTiledView(this._l12);
/* 798 */       t10.addTiledView(this._l13);
/* 799 */       t11.addTiledView(this._l23);
/*     */     } 
/*     */   }
/*     */   private void hideLines() {
/* 803 */     Mosaic mosaic = getMosaic();
/* 804 */     Tile t00 = mosaic.getTile(0, 0);
/* 805 */     Tile t10 = mosaic.getTile(1, 0);
/* 806 */     Tile t11 = mosaic.getTile(1, 1);
/* 807 */     if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/* 808 */       t00.removeTiledView(this._l23);
/* 809 */       t10.removeTiledView(this._l12);
/* 810 */       t11.removeTiledView(this._l13);
/* 811 */     } else if (this._orientation == Orientation.X1DOWN_X3RIGHT) {
/* 812 */       t00.removeTiledView(this._l23);
/* 813 */       t10.removeTiledView(this._l13);
/* 814 */       t11.removeTiledView(this._l12);
/* 815 */     } else if (this._orientation == Orientation.X1RIGHT_X2UP) {
/* 816 */       t00.removeTiledView(this._l13);
/* 817 */       t10.removeTiledView(this._l12);
/* 818 */       t11.removeTiledView(this._l23);
/* 819 */     } else if (this._orientation == Orientation.X1RIGHT_X3UP) {
/* 820 */       t00.removeTiledView(this._l12);
/* 821 */       t10.removeTiledView(this._l13);
/* 822 */       t11.removeTiledView(this._l23);
/*     */     } 
/*     */   }
/*     */   
/*     */   TileAxis[] getAxes(int i) {
/* 827 */     Mosaic mosaic = getMosaic();
/* 828 */     TileAxis al0 = mosaic.getTileAxisLeft(0);
/* 829 */     TileAxis al1 = mosaic.getTileAxisLeft(1);
/* 830 */     TileAxis ab0 = mosaic.getTileAxisBottom(0);
/* 831 */     TileAxis ab1 = mosaic.getTileAxisBottom(1);
/* 832 */     TileAxis[] a = null;
/* 833 */     if (this._axesPlacement == AxesPlacement.NONE) {
/* 834 */       a = new TileAxis[0];
/* 835 */     } else if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/* 836 */       if (i == 1) a = new TileAxis[] { al1 }; 
/* 837 */       if (i == 2) a = new TileAxis[] { ab0 }; 
/* 838 */       if (i == 3) a = new TileAxis[] { al0, ab1 }; 
/* 839 */     } else if (this._orientation == Orientation.X1DOWN_X3RIGHT) {
/* 840 */       if (i == 1) a = new TileAxis[] { al1 }; 
/* 841 */       if (i == 2) a = new TileAxis[] { al0, ab1 }; 
/* 842 */       if (i == 3) a = new TileAxis[] { ab0 }; 
/* 843 */     } else if (this._orientation == Orientation.X1RIGHT_X2UP) {
/* 844 */       if (i == 1) a = new TileAxis[] { ab0 }; 
/* 845 */       if (i == 2) a = new TileAxis[] { al1 }; 
/* 846 */       if (i == 3) a = new TileAxis[] { al0, ab1 }; 
/* 847 */     } else if (this._orientation == Orientation.X1RIGHT_X3UP) {
/* 848 */       if (i == 1) a = new TileAxis[] { ab0 }; 
/* 849 */       if (i == 2) a = new TileAxis[] { al0, ab1 }; 
/* 850 */       if (i == 3) a = new TileAxis[] { al1 }; 
/*     */     } 
/* 852 */     return a;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/PlotPanelPixels3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */