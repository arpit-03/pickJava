/*     */ package edu.mines.jtk.mosaic;
/*     */ import edu.mines.jtk.awt.ColorMap;
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.AxisTics;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class ContoursView extends TiledView implements ColorMapped {
/*     */   private float _lineWidth;
/*     */   private Line _lineStyle;
/*     */   private Line _lineStyleNegative;
/*     */   private ColorMap _colorMap;
/*     */   private Sampling _s1;
/*     */   private Sampling _s2;
/*     */   private float[][] _f;
/*     */   private Orientation _orientation;
/*     */   private boolean _transposed;
/*     */   private boolean _xflipped;
/*     */   private boolean _yflipped;
/*     */   private int _nx;
/*     */   private double _dx;
/*     */   private double _fx;
/*     */   private int _ny;
/*     */   private double _dy;
/*     */   private double _fy;
/*     */   private Clips _clips;
/*     */   private float _clipMin;
/*     */   private float _clipMax;
/*     */   private int _nc;
/*     */   private boolean _readableContours;
/*     */   private Sampling _cs;
/*     */   private ArrayList<Contour> _cl;
/*     */   private static final byte WEST = 1;
/*     */   private static final byte SOUTH = 2;
/*     */   private static final byte NOT_WEST = -2;
/*     */   private static final byte NOT_SOUTH = -3;
/*     */   
/*     */   public enum Orientation {
/*  45 */     X1RIGHT_X2UP,
/*  46 */     X1DOWN_X2RIGHT;
/*     */   }
/*     */   
/*     */   public enum Line {
/*  50 */     DEFAULT,
/*  51 */     SOLID,
/*  52 */     DASH,
/*  53 */     DOT,
/*  54 */     DASH_DOT;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContoursView(float[][] f)
/*     */   {
/* 555 */     this._lineWidth = 0.0F;
/* 556 */     this._lineStyle = Line.SOLID;
/* 557 */     this._lineStyleNegative = Line.DEFAULT;
/* 558 */     this._colorMap = new ColorMap(ColorMap.JET);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 566 */     this._orientation = Orientation.X1RIGHT_X2UP;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 585 */     this._nc = 25;
/* 586 */     this._readableContours = true; set(f); } public ContoursView(Sampling s1, Sampling s2, float[][] f) { this._lineWidth = 0.0F; this._lineStyle = Line.SOLID; this._lineStyleNegative = Line.DEFAULT; this._colorMap = new ColorMap(ColorMap.JET); this._orientation = Orientation.X1RIGHT_X2UP; this._nc = 25; this._readableContours = true; set(s1, s2, f); } public void set(float[][] f) { set(new Sampling((f[0]).length), new Sampling(f.length), f); } public void set(Sampling s1, Sampling s2, float[][] f) { Check.argument(s1.isUniform(), "s1 is uniform"); Check.argument(s2.isUniform(), "s2 is uniform"); Check.argument(ArrayMath.isRegular(f), "f is regular"); Check.argument((s1.getCount() == (f[0]).length), "s1 consistent with f"); Check.argument((s2.getCount() == f.length), "s2 consistent with f"); this._s1 = s1; this._s2 = s2; this._f = ArrayMath.copy(f); this._clips = new Clips(f); updateArraySampling(); this._cs = null; this._cl = null; } public void setOrientation(Orientation orientation) { if (this._orientation != orientation) { this._orientation = orientation; updateArraySampling(); repaint(); }  } public Orientation getOrientation() { return this._orientation; } public void setLineStyle(Line style) { if (this._lineStyle != style) { this._lineStyle = style; repaint(); }  } public void setLineStyleNegative(Line style) { if (this._lineStyleNegative != style) { this._lineStyleNegative = style; repaint(); }  }
/*     */   public void setLineWidth(float width) { if (this._lineWidth != width) { this._lineWidth = width; updateBestProjectors(); repaint(); }  }
/*     */   public void setLineColor(Color color) { this._colorMap.setColorModel(color); repaint(); }
/*     */   public void setColorModel(IndexColorModel colorModel) { this._colorMap.setColorModel(colorModel); repaint(); }
/*     */   public ColorMap getColorMap() { return this._colorMap; }
/*     */   public void setReadableContours(boolean readableContours) { if (this._readableContours != readableContours) { this._readableContours = readableContours; this._cs = null; this._cl = null; repaint(); }  }
/*     */   public void setContours(int n) { this._nc = n; this._cs = null; this._cl = null; repaint(); }
/*     */   public void setContours(float[] c) { double[] cd = new double[c.length]; for (int i = 0; i < c.length; i++) cd[i] = c[i];  setContours(new Sampling(cd)); }
/* 594 */   private void updateClips() { float clipMin = this._clips.getClipMin();
/* 595 */     float clipMax = this._clips.getClipMax();
/* 596 */     if (this._clipMin != clipMin || this._clipMax != clipMax) {
/* 597 */       this._clipMin = clipMin;
/* 598 */       this._clipMax = clipMax;
/* 599 */       this._colorMap.setValueRange(clipMin, clipMax);
/*     */     }  } public void setContours(Sampling cs) { this._readableContours = false; this._cs = cs; this._cl = null; repaint(); }
/*     */   public float[] getContours() { updateContourSampling(); float[] values = new float[this._cs.getCount()]; for (int n = 0; n < values.length; n++)
/*     */       values[n] = ((Contour)this._cl.get(n)).fc;  return values; }
/*     */   public void setClips(float clipMin, float clipMax) { this._clips.setClips(clipMin, clipMax); this._cs = null; this._cl = null;
/*     */     repaint(); }
/*     */   public float getClipMin() { return this._clips.getClipMin(); }
/*     */   public float getClipMax() { return this._clips.getClipMax(); }
/*     */   public void setPercentiles(float percMin, float percMax) { this._clips.setPercentiles(percMin, percMax);
/*     */     this._cs = null;
/*     */     this._cl = null;
/*     */     repaint(); }
/* 611 */   private void updateContourSampling() { if (this._cs == null) {
/* 612 */       int nc; double dc, fc; updateClips();
/*     */ 
/*     */       
/* 615 */       if (this._readableContours) {
/* 616 */         AxisTics at = new AxisTics(this._clipMin, this._clipMax, this._nc);
/* 617 */         nc = at.getCountMajor();
/* 618 */         dc = at.getDeltaMajor();
/* 619 */         fc = at.getFirstMajor();
/*     */       } else {
/* 621 */         nc = this._nc;
/* 622 */         dc = ((this._clipMax - this._clipMin) / (nc + 1));
/* 623 */         fc = this._clipMin;
/*     */       } 
/* 625 */       double[] cstep = new double[nc];
/* 626 */       cstep[0] = fc + dc;
/* 627 */       int count = 1;
/* 628 */       while (count < nc) {
/* 629 */         cstep[count] = cstep[count - 1] + dc;
/* 630 */         count++;
/*     */       } 
/* 632 */       this._cs = new Sampling(cstep);
/*     */     }  }
/*     */   
/*     */   public float getPercentileMin() {
/*     */     return this._clips.getPercentileMin();
/*     */   } public float getPercentileMax() {
/*     */     return this._clips.getPercentileMax();
/*     */   } public void addColorMapListener(ColorMapListener cml) {
/*     */     this._colorMap.addListener(cml);
/*     */   }
/*     */   public void removeColorMapListener(ColorMapListener cml) {
/*     */     this._colorMap.removeListener(cml);
/*     */   }
/* 645 */   private void updateArraySampling() { int n1 = this._s1.getCount();
/* 646 */     int n2 = this._s2.getCount();
/* 647 */     double d1 = this._s1.getDelta();
/* 648 */     double d2 = this._s2.getDelta();
/* 649 */     double f1 = this._s1.getFirst();
/* 650 */     double f2 = this._s2.getFirst();
/* 651 */     if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/* 652 */       this._transposed = true;
/* 653 */       this._xflipped = false;
/* 654 */       this._yflipped = false;
/* 655 */       this._nx = n2;
/* 656 */       this._dx = d2;
/* 657 */       this._fx = f2;
/* 658 */       this._ny = n1;
/* 659 */       this._dy = d1;
/* 660 */       this._fy = f1;
/* 661 */     } else if (this._orientation == Orientation.X1RIGHT_X2UP) {
/* 662 */       this._transposed = false;
/* 663 */       this._xflipped = false;
/* 664 */       this._yflipped = true;
/* 665 */       this._nx = n1;
/* 666 */       this._dx = d1;
/* 667 */       this._fx = f1;
/* 668 */       this._ny = n2;
/* 669 */       this._dy = d2;
/* 670 */       this._fy = f2;
/*     */     } 
/* 672 */     updateBestProjectors(); }
/*     */   public void paint(Graphics2D g2d) { BasicStroke bs; updateContourSampling(); updateContours(); Projector hp = getHorizontalProjector(); Projector vp = getVerticalProjector(); Transcaler ts = getTranscaler(); double vx0 = this._fx - 0.5D * this._dx; double vx1 = this._fx + this._dx * (this._nx - 0.5D); double vy0 = this._fy - 0.5D * this._dy; double vy1 = this._fy + this._dy * (this._ny - 0.5D); double ux0 = hp.u(vx0); double ux1 = hp.u(vx1); double uy0 = vp.u(vy0); double uy1 = vp.u(vy1); double uxmin = ArrayMath.min(ux0, ux1); double uxmax = ArrayMath.max(ux0, ux1); double uymin = ArrayMath.min(uy0, uy1); double uymax = ArrayMath.max(uy0, uy1); int xd = ts.x(uxmin); int yd = ts.y(uymin); int wd = ts.width(uxmax - uxmin); int hd = ts.height(uymax - uymin); Rectangle viewRect = new Rectangle(xd, yd, wd, hd); Rectangle clipRect = g2d.getClipBounds(); if (clipRect == null)
/*     */       clipRect = viewRect;  clipRect = clipRect.intersection(viewRect); if (clipRect.isEmpty())
/*     */       return;  float lineWidth = 1.0F; Graphics2D gline = (Graphics2D)g2d.create(); float[] dash = null; Line lineStyle = this._lineStyle; if (lineStyle == Line.DEFAULT)
/*     */       lineStyle = Line.SOLID;  if (lineStyle != Line.SOLID) { float dotLength = lineWidth; float dashLength = 5.0F * lineWidth; float gapLength = 5.0F * lineWidth; if (lineStyle == Line.DASH) { dash = new float[] { dashLength, gapLength }; } else if (lineStyle == Line.DOT) { dash = new float[] { dotLength, gapLength }; } else if (lineStyle == Line.DASH_DOT) { dash = new float[] { dashLength, gapLength, dotLength, gapLength }; }  }  float width = lineWidth; if (this._lineWidth != 0.0F)
/*     */       width *= this._lineWidth;  if (dash != null) { int cap = 1; int join = 1; float miter = 10.0F; float phase = 0.0F; bs = new BasicStroke(width, cap, join, miter, dash, phase); } else { bs = new BasicStroke(width); }  gline.setStroke(bs); BasicStroke bsneg = null; if (this._lineStyleNegative != Line.DEFAULT) { dash = null; if (this._lineStyleNegative != Line.SOLID) { float dotLength = lineWidth; float dashLength = 5.0F * lineWidth; float gapLength = 5.0F * lineWidth; if (this._lineStyleNegative == Line.DASH) { dash = new float[] { dashLength, gapLength }; } else if (this._lineStyleNegative == Line.DOT) { dash = new float[] { dotLength, gapLength }; } else if (this._lineStyleNegative == Line.DASH_DOT) { dash = new float[] { dashLength, gapLength, dotLength, gapLength }; }  }  width = lineWidth; if (this._lineWidth != 0.0F)
/*     */         width *= this._lineWidth;  if (dash != null) { int cap = 1; int join = 1; float miter = 10.0F; float phase = 0.0F; bsneg = new BasicStroke(width, cap, join, miter, dash, phase); } else { bsneg = new BasicStroke(width); }  }  IndexColorModel cm = this._colorMap.getColorModel(); for (int is = 0; is < this._cs.getCount(); is++) { float fc = ((Contour)this._cl.get(is)).fc; ArrayList<float[]> cx1 = ((Contour)this._cl.get(is)).x1; ArrayList<float[]> cx2 = ((Contour)this._cl.get(is)).x2; Iterator<float[]> it1 = (Iterator)cx1.iterator(); Iterator<float[]> it2 = (Iterator)cx2.iterator(); if (cm != null) { int index; if (fc < this._clipMin) { index = 0; } else if (fc > this._clipMax) { index = 255; } else { index = (int)((fc - this._clipMin) / (this._clipMax - this._clipMin) * 255.0F); }  gline.setColor(new Color(cm.getRGB(index))); }  if (this._lineStyleNegative != Line.DEFAULT) { if (fc < 0.0F)
/*     */           gline.setStroke(bsneg);  if (fc >= 0.0F)
/*     */           gline.setStroke(bs);  }  while (it1.hasNext()) { float[] xc1 = it1.next(); float[] xc2 = it2.next(); int n = xc1.length; int[] xcon = new int[xc1.length]; int[] ycon = new int[xc2.length]; computeXY(hp, vp, ts, n, xc1, xc2, xcon, ycon); if (gline != null)
/*     */           gline.drawPolyline(xcon, ycon, n);  }  }
/* 682 */      } private void updateBestProjectors() { double x0 = this._fx;
/* 683 */     double x1 = this._fx + this._dx * (this._nx - 1);
/* 684 */     double y0 = this._fy;
/* 685 */     double y1 = this._fy + this._dy * (this._ny - 1);
/* 686 */     if (this._xflipped) {
/* 687 */       double xt = y0;
/* 688 */       x0 = x1;
/* 689 */       x1 = xt;
/*     */     } 
/* 691 */     if (this._yflipped) {
/* 692 */       double yt = y0;
/* 693 */       y0 = y1;
/* 694 */       y1 = yt;
/*     */     } 
/*     */ 
/*     */     
/* 698 */     if (x0 == x1) {
/* 699 */       double tiny = ArrayMath.max(0.5D, 1.1920928955078125E-7D * ArrayMath.abs(x0));
/* 700 */       x0 -= tiny;
/* 701 */       x1 += tiny;
/*     */     } 
/* 703 */     if (y0 == y1) {
/* 704 */       double tiny = ArrayMath.max(0.5D, 1.1920928955078125E-7D * ArrayMath.abs(y0));
/* 705 */       y0 -= tiny;
/* 706 */       y1 += tiny;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 714 */     double u0 = 0.0D;
/* 715 */     double u1 = 1.0D;
/* 716 */     if (this._lineWidth > 1.0F) {
/* 717 */       u0 = 0.01D;
/* 718 */       u1 = 0.99D;
/*     */     } 
/*     */ 
/*     */     
/* 722 */     Projector bhp = new Projector(x0, x1, u0, u1);
/* 723 */     Projector bvp = new Projector(y0, y1, u0, u1);
/* 724 */     setBestProjectors(bhp, bvp); } private static class Contour {
/*     */     float fc; int ns = 0; ArrayList<float[]> x1 = (ArrayList)new ArrayList<>(); ArrayList<float[]> x2 = (ArrayList)new ArrayList<>(); Contour(float fc) { this.fc = fc; } void append(ContoursView.FloatList x1List, ContoursView.FloatList x2List) { this.ns++; this.x1.add(x1List.trim()); this.x2.add(x2List.trim()); } } private static class FloatList {
/*     */     public int n; public float[] a = new float[64];
/*     */     public void add(double f) { if (this.n == this.a.length) { float[] t = new float[2 * this.a.length]; for (int i = 0; i < this.n; i++) t[i] = this.a[i];  this.a = t; }  this.a[this.n++] = (float)f; }
/*     */     public float[] trim() { float[] t = new float[this.n]; for (int i = 0; i < this.n; i++)
/*     */         t[i] = this.a[i];  return t; }
/*     */     private FloatList() {} }
/* 731 */   private void updateContours() { if (this._cl == null) {
/* 732 */       int nc = this._cs.getCount();
/* 733 */       this._cl = new ArrayList<>();
/* 734 */       for (int ic = 0; ic < nc; ic++) {
/* 735 */         float fc = (float)this._cs.getValue(ic);
/* 736 */         Contour c = makeContour(fc, this._s1, this._s2, this._f);
/* 737 */         this._cl.add(c);
/*     */       } 
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void computeXY(Projector hp, Projector vp, Transcaler ts, int n, float[] x1, float[] x2, int[] x, int[] y) {
/*     */     float[] xv, yv;
/* 749 */     ts = ts.combineWith(hp, vp);
/*     */     
/* 751 */     if (this._transposed) {
/* 752 */       xv = x2;
/* 753 */       yv = x1;
/*     */     } else {
/* 755 */       xv = x1;
/* 756 */       yv = x2;
/*     */     } 
/* 758 */     for (int i = 0; i < n; i++) {
/* 759 */       x[i] = ts.x(xv[i]);
/* 760 */       y[i] = ts.y(yv[i]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Contour makeContour(float fc, Sampling s1, Sampling s2, float[][] f) {
/* 784 */     int n1 = s1.getCount();
/* 785 */     double d1 = s1.getDelta();
/* 786 */     double f1 = s1.getFirst();
/* 787 */     int n2 = s2.getCount();
/* 788 */     double d2 = s2.getDelta();
/* 789 */     double f2 = s2.getFirst();
/* 790 */     int n1m1 = n1 - 1;
/* 791 */     int n2m1 = n2 - 1;
/*     */ 
/*     */ 
/*     */     
/* 795 */     byte[][] flags = new byte[n2][n1];
/* 796 */     int ni = 0; int i2;
/* 797 */     for (i2 = 0; i2 < n2; i2++) {
/* 798 */       for (int i = 0; i < n1; i++) {
/* 799 */         if (i2 < n2m1 && between(fc, f[i2][i], f[i2 + 1][i])) {
/* 800 */           setw(i, i2, flags);
/* 801 */           ni++;
/*     */         } 
/* 803 */         if (i < n1m1 && between(fc, f[i2][i], f[i2][i + 1])) {
/* 804 */           sets(i, i2, flags);
/* 805 */           ni++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 811 */     Contour c = new Contour(fc);
/*     */ 
/*     */     
/* 814 */     i2 = n2m1; int i1, is;
/* 815 */     for (i1 = 0, is = i1 + i2 * n1; i1 < n1m1 && ni > 0; i1++, is++) {
/* 816 */       if (sset(i1, i2, flags)) {
/* 817 */         float d = delta(fc, f[i2][i1], f[i2][i1 + 1]);
/* 818 */         FloatList x1 = new FloatList();
/* 819 */         FloatList x2 = new FloatList();
/* 820 */         x1.add(f1 + (i1 + d) * d1);
/* 821 */         x2.add(f2 + i2 * d2);
/* 822 */         clrs(i1, i2, flags); int i;
/* 823 */         for (i = is - n1; i >= 0; i = connect(i, fc, n1, d1, f1, n2, d2, f2, f, flags, x1, x2))
/* 824 */           ni--; 
/* 825 */         c.append(x1, x2);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 830 */     i1 = n1m1;
/* 831 */     for (i2 = 0, is = i1 + i2 * n1; i2 < n2m1 && ni > 0; i2++, is += n1) {
/* 832 */       if (wset(i1, i2, flags)) {
/* 833 */         float d = delta(fc, f[i2][i1], f[i2 + 1][i1]);
/* 834 */         FloatList x1 = new FloatList();
/* 835 */         FloatList x2 = new FloatList();
/* 836 */         x1.add(f1 + i1 * d1);
/* 837 */         x2.add(f2 + (i2 + d) * d2);
/* 838 */         clrw(i1, i2, flags); int i;
/* 839 */         for (i = is - 1; i >= 0; i = connect(i, fc, n1, d1, f1, n2, d2, f2, f, flags, x1, x2))
/* 840 */           ni--; 
/* 841 */         c.append(x1, x2);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 846 */     i2 = 0;
/* 847 */     for (i1 = 0, is = i1 + i2 * n1; i1 < n1m1 && ni > 0; i1++, is++) {
/* 848 */       if (sset(i1, i2, flags)) {
/* 849 */         float d = delta(fc, f[i2][i1], f[i2][i1 + 1]);
/* 850 */         FloatList x1 = new FloatList();
/* 851 */         FloatList x2 = new FloatList();
/* 852 */         x1.add(f1 + (i1 + d) * d1);
/* 853 */         x2.add(f2 + i2 * d2);
/* 854 */         clrs(i1, i2, flags); int i;
/* 855 */         for (i = is; i >= 0; i = connect(i, fc, n1, d1, f1, n2, d2, f2, f, flags, x1, x2))
/* 856 */           ni--; 
/* 857 */         c.append(x1, x2);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 862 */     i1 = 0;
/* 863 */     for (i2 = 0, is = i1 + i2 * n1; i2 < n2m1 && ni > 0; i2++, is += n1) {
/* 864 */       if (wset(i1, i2, flags)) {
/* 865 */         float d = delta(fc, f[i2][i1], f[i2 + 1][i1]);
/* 866 */         FloatList x1 = new FloatList();
/* 867 */         FloatList x2 = new FloatList();
/* 868 */         x1.add(f1 + i1 * d1);
/* 869 */         x2.add(f2 + (i2 + d) * d2);
/* 870 */         clrw(i1, i2, flags); int i;
/* 871 */         for (i = is; i >= 0; i = connect(i, fc, n1, d1, f1, n2, d2, f2, f, flags, x1, x2))
/* 872 */           ni--; 
/* 873 */         c.append(x1, x2);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 878 */     for (i2 = 1; i2 < n2m1 && ni > 0; i2++) {
/* 879 */       for (i1 = 0, is = i1 + i2 * n1; i1 < n1m1 && ni > 0; i1++, is++) {
/* 880 */         if (sset(i1, i2, flags)) {
/* 881 */           float d = delta(fc, f[i2][i1], f[i2][i1 + 1]);
/* 882 */           FloatList x1 = new FloatList();
/* 883 */           FloatList x2 = new FloatList();
/* 884 */           x1.add(f1 + (i1 + d) * d1);
/* 885 */           x2.add(f2 + i2 * d2);
/* 886 */           clrs(i1, i2, flags); int i;
/* 887 */           for (i = is; i >= 0; i = connect(i, fc, n1, d1, f1, n2, d2, f2, f, flags, x1, x2)) {
/* 888 */             ni--;
/*     */           }
/* 890 */           x1.add(x1.a[0]);
/* 891 */           x2.add(x2.a[0]);
/* 892 */           c.append(x1, x2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 897 */     return c;
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
/*     */   private static int connect(int index, float fc, int n1, double d1, double f1, int n2, double d2, double f2, float[][] f, byte[][] flags, FloatList x1, FloatList x2) {
/* 915 */     int i1 = index % n1;
/* 916 */     int i2 = index / n1;
/*     */ 
/*     */     
/* 919 */     if (sset(i1, i2 + 1, flags)) {
/* 920 */       float d = delta(fc, f[i2 + 1][i1], f[i2 + 1][i1 + 1]);
/* 921 */       x1.add(f1 + (i1 + d) * d1);
/* 922 */       x2.add(f2 + (i2 + 1) * d2);
/* 923 */       clrs(i1, ++i2, flags);
/* 924 */       return (i2 < n2 - 1) ? (index + n1) : -1;
/*     */     } 
/*     */ 
/*     */     
/* 928 */     if (wset(i1 + 1, i2, flags)) {
/* 929 */       float d = delta(fc, f[i2][i1 + 1], f[i2 + 1][i1 + 1]);
/* 930 */       x1.add(f1 + (i1 + 1) * d1);
/* 931 */       x2.add(f2 + (i2 + d) * d2);
/* 932 */       clrw(++i1, i2, flags);
/* 933 */       return (i1 < n1 - 1) ? (index + 1) : -1;
/*     */     } 
/*     */ 
/*     */     
/* 937 */     if (sset(i1, i2, flags)) {
/* 938 */       float d = delta(fc, f[i2][i1], f[i2][i1 + 1]);
/* 939 */       x1.add(f1 + (i1 + d) * d1);
/* 940 */       x2.add(f2 + i2 * d2);
/* 941 */       clrs(i1, i2, flags);
/* 942 */       return (i2 > 0) ? (index - n1) : -1;
/*     */     } 
/*     */ 
/*     */     
/* 946 */     if (wset(i1, i2, flags)) {
/* 947 */       float d = delta(fc, f[i2][i1], f[i2 + 1][i1]);
/* 948 */       x1.add(f1 + i1 * d1);
/* 949 */       x2.add(f2 + (i2 + d) * d2);
/* 950 */       clrw(i1, i2, flags);
/* 951 */       return (i1 > 0) ? (index - 1) : -1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 956 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void sets(int i1, int i2, byte[][] flags) {
/* 967 */     flags[i2][i1] = (byte)(flags[i2][i1] | 0x2);
/*     */   }
/*     */   
/*     */   private static void setw(int i1, int i2, byte[][] flags) {
/* 971 */     flags[i2][i1] = (byte)(flags[i2][i1] | 0x1);
/*     */   }
/*     */   
/*     */   private static void clrs(int i1, int i2, byte[][] flags) {
/* 975 */     flags[i2][i1] = (byte)(flags[i2][i1] & 0xFFFFFFFD);
/*     */   }
/*     */   
/*     */   private static void clrw(int i1, int i2, byte[][] flags) {
/* 979 */     flags[i2][i1] = (byte)(flags[i2][i1] & 0xFFFFFFFE);
/*     */   }
/*     */   
/*     */   private static boolean sset(int i1, int i2, byte[][] flags) {
/* 983 */     return ((flags[i2][i1] & 0x2) != 0);
/*     */   }
/*     */   
/*     */   private static boolean wset(int i1, int i2, byte[][] flags) {
/* 987 */     return ((flags[i2][i1] & 0x1) != 0);
/*     */   }
/*     */   
/*     */   private static boolean between(float fc, float f1, float f2) {
/* 991 */     return (f1 <= f2) ? ((f1 <= fc && fc < f2)) : ((f2 <= fc && fc < f1));
/*     */   }
/*     */   
/*     */   private static float delta(float fc, float f1, float f2) {
/* 995 */     return (f1 != f2) ? ((fc - f1) / (f2 - f1)) : 1.0F;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/ContoursView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */