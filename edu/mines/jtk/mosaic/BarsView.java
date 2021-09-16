/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.awt.ColorMap;
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BarsView
/*     */   extends TiledView
/*     */ {
/*     */   int _ns;
/*     */   ArrayList<Integer> _nx;
/*     */   ArrayList<float[]> _x1;
/*     */   ArrayList<float[]> _x2;
/*     */   int _nxmax;
/*     */   private Orientation _orientation;
/*     */   private Alignment _alignment;
/*     */   private boolean _stackingBars;
/*     */   private Line _lineStyle;
/*     */   private float _lineWidth;
/*     */   private Color[] _lineColor;
/*     */   private float _barWidth;
/*     */   private String _textFormat;
/*     */   private ColorMap[] _colorMaps;
/*     */   
/*     */   public enum Orientation
/*     */   {
/*  51 */     X1RIGHT_X2UP,
/*  52 */     X1DOWN_X2RIGHT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Line
/*     */   {
/*  60 */     NONE,
/*  61 */     SOLID,
/*  62 */     DASH,
/*  63 */     DOT,
/*  64 */     DASH_DOT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Alignment
/*     */   {
/*  72 */     ALIGN_CENTER,
/*  73 */     ALIGN_BEFORE,
/*  74 */     ALIGN_AFTER;
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
/*     */   public BarsView(float[] x2)
/*     */   {
/* 560 */     this._nx = new ArrayList<>();
/* 561 */     this._x1 = (ArrayList)new ArrayList<>();
/* 562 */     this._x2 = (ArrayList)new ArrayList<>();
/*     */ 
/*     */ 
/*     */     
/* 566 */     this._orientation = Orientation.X1RIGHT_X2UP;
/*     */ 
/*     */     
/* 569 */     this._alignment = Alignment.ALIGN_CENTER;
/*     */ 
/*     */     
/* 572 */     this._stackingBars = false;
/*     */ 
/*     */     
/* 575 */     this._lineStyle = Line.SOLID;
/* 576 */     this._lineWidth = 0.0F;
/* 577 */     this._lineColor = null;
/* 578 */     this._barWidth = 1.0F;
/* 579 */     this._textFormat = "%1.4G";
/*     */ 
/*     */     
/* 582 */     this._colorMaps = null; float[] x1 = ArrayMath.rampfloat(0.0F, 1.0F, x2.length); set(x1, x2); } public BarsView(Sampling s1, float[] x2) { this._nx = new ArrayList<>(); this._x1 = (ArrayList)new ArrayList<>(); this._x2 = (ArrayList)new ArrayList<>(); this._orientation = Orientation.X1RIGHT_X2UP; this._alignment = Alignment.ALIGN_CENTER; this._stackingBars = false; this._lineStyle = Line.SOLID; this._lineWidth = 0.0F; this._lineColor = null; this._barWidth = 1.0F; this._textFormat = "%1.4G"; this._colorMaps = null; set(s1, x2); } public BarsView(float[][] x2) { this._nx = new ArrayList<>(); this._x1 = (ArrayList)new ArrayList<>(); this._x2 = (ArrayList)new ArrayList<>(); this._orientation = Orientation.X1RIGHT_X2UP; this._alignment = Alignment.ALIGN_CENTER; this._stackingBars = false; this._lineStyle = Line.SOLID; this._lineWidth = 0.0F; this._lineColor = null; this._barWidth = 1.0F; this._textFormat = "%1.4G"; this._colorMaps = null; set((float[][])null, x2); } public BarsView(Sampling s1, float[][] x2) { this._nx = new ArrayList<>(); this._x1 = (ArrayList)new ArrayList<>(); this._x2 = (ArrayList)new ArrayList<>(); this._orientation = Orientation.X1RIGHT_X2UP; this._alignment = Alignment.ALIGN_CENTER; this._stackingBars = false; this._lineStyle = Line.SOLID; this._lineWidth = 0.0F; this._lineColor = null; this._barWidth = 1.0F; this._textFormat = "%1.4G"; this._colorMaps = null; Check.argument((s1.getCount() == (x2[0]).length), "s1 count equals x2 length"); int n1 = (x2[0]).length; int n2 = x2.length; float[][] x1 = new float[n2][n1]; for (int i2 = 0; i2 < n2; i2++) { for (int i1 = 0; i1 < n1; i1++) x1[i2][i1] = (float)s1.getValue(i1);  }  set(x1, x2); } public BarsView(float[][] x1, float[][] x2) { this._nx = new ArrayList<>(); this._x1 = (ArrayList)new ArrayList<>(); this._x2 = (ArrayList)new ArrayList<>(); this._orientation = Orientation.X1RIGHT_X2UP; this._alignment = Alignment.ALIGN_CENTER; this._stackingBars = false; this._lineStyle = Line.SOLID; this._lineWidth = 0.0F; this._lineColor = null; this._barWidth = 1.0F; this._textFormat = "%1.4G"; this._colorMaps = null; set(x1, x2); } public BarsView(float[] x1, float[] x2) { this._nx = new ArrayList<>(); this._x1 = (ArrayList)new ArrayList<>(); this._x2 = (ArrayList)new ArrayList<>(); this._orientation = Orientation.X1RIGHT_X2UP; this._alignment = Alignment.ALIGN_CENTER; this._stackingBars = false; this._lineStyle = Line.SOLID; this._lineWidth = 0.0F; this._lineColor = null; this._barWidth = 1.0F; this._textFormat = "%1.4G"; this._colorMaps = null; set(x1, x2); }
/*     */   public void set(Sampling s1, float[] x2) { Check.argument((s1.getCount() == x2.length), "s1 count equals x2 length"); int n1 = x2.length; float[] x1 = new float[n1]; for (int i1 = 0; i1 < n1; i1++) x1[i1] = (float)s1.getValue(i1);  set(x1, x2); }
/*     */   public void set(float[] x1, float[] x2) { Check.argument((x1.length == x2.length), "x1.length equals x2.length"); this._ns = 1; this._nx.clear(); this._x1.clear(); this._x2.clear(); this._nxmax = x1.length; this._nx.add(Integer.valueOf(x1.length)); this._x1.add(ArrayMath.copy(x1)); this._x2.add(ArrayMath.copy(x2)); this._colorMaps = new ColorMap[this._ns]; for (int i = 0; i < this._ns; i++) this._colorMaps[i] = new ColorMap(Color.CYAN);  this._lineColor = new Color[this._ns]; updateBestProjectors(); repaint(); }
/*     */   public void set(float[][] x1, float[][] x2) { Check.argument((x1.length == x2.length), "x1.length equals x2.length"); this._ns = x1.length; this._nx.clear(); this._x1.clear(); this._x2.clear(); this._nxmax = 0; for (int is = 0; is < this._ns; is++) { Check.argument(((x1[is]).length == (x2[is]).length), "x1[i].length equals x2[i].length"); this._nxmax = ArrayMath.max(this._nxmax, (x1[is]).length); this._nx.add(Integer.valueOf((x1[is]).length)); this._x1.add(ArrayMath.copy(x1[is])); this._x2.add(ArrayMath.copy(x2[is])); }  this._colorMaps = new ColorMap[this._ns]; for (int i = 0; i < this._ns; i++) this._colorMaps[i] = new ColorMap(Color.CYAN);  this._lineColor = new Color[this._ns]; updateBestProjectors(); repaint(); }
/*     */   public void setOrientation(Orientation orientation) { if (this._orientation != orientation) { this._orientation = orientation; updateBestProjectors(); repaint(); }  }
/*     */   public Orientation getOrientation() { return this._orientation; }
/* 588 */   public void setStyle(String style) { if (style.contains("r")) { setLineColor(Color.RED); } else if (style.contains("g")) { setLineColor(Color.GREEN); } else if (style.contains("b")) { setLineColor(Color.BLUE); } else if (style.contains("c")) { setLineColor(Color.CYAN); } else if (style.contains("m")) { setLineColor(Color.MAGENTA); } else if (style.contains("y")) { setLineColor(Color.YELLOW); } else if (style.contains("k")) { setLineColor(Color.BLACK); } else if (style.contains("w")) { setLineColor(Color.WHITE); } else { setLineColor((Color)null); }  if (style.contains("--.")) { setLineStyle(Line.DASH_DOT); } else if (style.contains("--")) { setLineStyle(Line.DASH); } else if (style.contains("-.")) { setLineStyle(Line.DOT); } else if (style.contains("-")) { setLineStyle(Line.SOLID); } else { setLineStyle(Line.NONE); }  } public void setBarWidth(float width) { this._barWidth = width; repaint(); } public void setFillColor(Color color) { for (int i = 0; i < this._ns; i++) setFillColor(i, color);  } public void setColorModel(IndexColorModel colorModel) { for (int is = 0; is < this._ns; is++) setColorModel(is, colorModel);  } public void setColorModel(int i, IndexColorModel colorModel) { this._colorMaps[i].setColorModel(colorModel); repaint(); } private void updateBestProjectors() { int max = ((Integer)Collections.<Integer>max(this._nx)).intValue();
/* 589 */     float[] sum = new float[max];
/*     */ 
/*     */     
/* 592 */     float x1min = Float.MAX_VALUE;
/* 593 */     float x2min = Float.MAX_VALUE;
/* 594 */     float x1max = -3.4028235E38F;
/* 595 */     float x2max = -3.4028235E38F;
/* 596 */     for (int is = 0; is < this._ns; is++) {
/* 597 */       int nx = ((Integer)this._nx.get(is)).intValue();
/* 598 */       float[] x1 = this._x1.get(is);
/* 599 */       float[] x2 = this._x2.get(is);
/* 600 */       for (int ix = 0; ix < nx; ix++) {
/* 601 */         float x1i = x1[ix];
/* 602 */         float x2i = x2[ix];
/* 603 */         x1min = ArrayMath.min(x1min, x1i);
/* 604 */         x1max = ArrayMath.max(x1max, x1i);
/* 605 */         x2min = ArrayMath.min(x2min, x2i);
/* 606 */         x2max = ArrayMath.max(x2max, x2i);
/* 607 */         sum[ix] = sum[ix] + x2[ix];
/*     */       } 
/*     */     } 
/*     */     
/* 611 */     if (this._stackingBars) {
/* 612 */       x2max = ArrayMath.max(0.0F, ArrayMath.max(sum));
/* 613 */       x2min = ArrayMath.min(0.0F, ArrayMath.min(sum));
/*     */     } 
/*     */ 
/*     */     
/* 617 */     if (x1min == x1max) {
/* 618 */       x1min -= ArrayMath.ulp(x1min);
/* 619 */       x1max += ArrayMath.ulp(x1max);
/*     */     } 
/* 621 */     if (x2min == x2max) {
/* 622 */       x2min -= ArrayMath.ulp(x2min);
/* 623 */       x2max += ArrayMath.ulp(x2max);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 632 */     double u0 = 0.0D;
/* 633 */     double u1 = 1.0D;
/* 634 */     if (this._lineWidth > 1.0F) {
/* 635 */       u0 = 0.01D;
/* 636 */       u1 = 0.99D;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 642 */     float w = this._barWidth / 2.0F;
/* 643 */     if (this._alignment == Alignment.ALIGN_CENTER) {
/* 644 */       x1min -= w;
/* 645 */       x1max += w;
/* 646 */     } else if (this._alignment == Alignment.ALIGN_BEFORE) {
/* 647 */       x1min -= 2.0F * w;
/*     */     } else {
/* 649 */       x1max += 2.0F * w;
/*     */     } 
/*     */ 
/*     */     
/* 653 */     Projector bhp = null;
/* 654 */     Projector bvp = null;
/* 655 */     if (this._orientation == Orientation.X1RIGHT_X2UP) {
/* 656 */       bhp = (x1min < x1max) ? new Projector(x1min, x1max, u0, u1) : null;
/* 657 */       bvp = (x2min < x2max) ? new Projector(x2max, x2min, u0, u1) : null;
/* 658 */     } else if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/* 659 */       bhp = (x2min < x2max) ? new Projector(x2min, x2max, u0, u1) : null;
/* 660 */       bvp = (x1min < x1max) ? new Projector(x1min, x1max, u0, u1) : null;
/*     */     } 
/* 662 */     setBestProjectors(bhp, bvp); }
/*     */   public void setColorMap(ColorMap colorMap) { for (int is = 0; is < this._ns; is++) setColorMap(is, colorMap);  }
/*     */   public void setColorMap(int i, ColorMap colorMap) { this._colorMaps[i] = colorMap; repaint(); }
/*     */   public void setFillColor(int i, Color color) { this._colorMaps[i] = new ColorMap(color); repaint(); }
/* 666 */   public void setAlignment(Alignment alignment) { if (this._alignment != alignment) { this._alignment = alignment; updateBestProjectors(); repaint(); }  } public void setStackBars(boolean stack) { this._stackingBars = stack; updateBestProjectors(); repaint(); } public void setLineStyle(Line style) { this._lineStyle = style; repaint(); } public void setLineWidth(float width) { if (this._lineWidth != width) { this._lineWidth = width; updateBestProjectors(); repaint(); }  } public void setLineColor(Color color) { for (int i = 0; i < this._ns; i++) setLineColor(i, color);  } public void setLineColor(int ibar, Color color) { this._lineColor[ibar] = color; repaint(); } public void setTextFormat(String format) { this._textFormat = format; repaint(); } public void paint(Graphics2D g2d) { g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); Projector hp = getHorizontalProjector(); Projector vp = getVerticalProjector(); Transcaler ts = getTranscaler(); float fontSize = g2d.getFont().getSize2D(); float lineWidth = 1.0F; Stroke stroke = g2d.getStroke(); if (stroke instanceof BasicStroke) { BasicStroke bs = (BasicStroke)stroke; lineWidth = bs.getLineWidth(); }  Graphics2D gline = null; if (this._lineStyle != Line.NONE) { BasicStroke bs; gline = (Graphics2D)g2d.create(); float width = lineWidth; if (this._lineWidth != 0.0F) width *= this._lineWidth;  float[] dash = null; if (this._lineStyle != Line.SOLID) { float dotLength = 0.5F * width; float dashLength = 2.0F * width; float gapLength = 2.0F * dotLength + dashLength; if (this._lineStyle == Line.DASH) { dash = new float[] { dashLength, gapLength }; } else if (this._lineStyle == Line.DOT) { dash = new float[] { dotLength, gapLength }; } else if (this._lineStyle == Line.DASH_DOT) { dash = new float[] { dashLength, gapLength, dotLength, gapLength }; }  }  if (dash != null) { int cap = 1; int join = 1; float miter = 10.0F; float phase = 0.0F; bs = new BasicStroke(width, cap, join, miter, dash, phase); } else { bs = new BasicStroke(width); }  gline.setStroke(bs); }  Graphics2D gtext = null; int[] x = new int[this._nxmax]; int[] y = new int[this._nxmax]; Graphics2D gbar = (Graphics2D)g2d.create(); float[] o1 = { 0.0F, 1.0F }; float[] o2 = { 0.0F, 1.0F }; int[] ox = new int[2]; int[] oy = new int[2]; Color[][] colors = new Color[this._ns][this._nxmax]; for (int is = 0; is < this._ns; is++) { float[] x2 = this._x2.get(is); for (int ix = 0; ix < this._nxmax; ix++) colors[is][ix] = this._colorMaps[is].getColor(x2[ix]);  }  computeXY(hp, vp, ts, 1, o1, o2, ox, oy); float[] bottom = new float[this._nxmax]; int[] prevTop = new int[this._nxmax]; for (int i = 0; i < this._nxmax; i++) prevTop[i] = (this._orientation == Orientation.X1RIGHT_X2UP) ? oy[0] : ox[0];  int shift = 0; if (this._alignment == Alignment.ALIGN_BEFORE) { shift = -1; } else if (this._alignment == Alignment.ALIGN_AFTER) { shift = 1; }  for (int j = 0; j < this._ns; j++) { int n = ((Integer)this._nx.get(j)).intValue(); float[] x1 = ArrayMath.copy(this._x1.get(j)); float[] x2 = ArrayMath.copy(this._x2.get(j)); if (this._stackingBars) for (int k = 0; k < n; k++) x2[k] = x2[k] + bottom[k];   computeXY(hp, vp, ts, n, x1, x2, x, y); if (this._colorMaps[j] != null) paintBars(gbar, this._barWidth, prevTop, n, x, y, shift, j, true, colors[j]);  if (this._lineColor[j] != null) { gbar.setColor(this._lineColor[j]); } else { gbar.setColor(Color.BLACK); }  paintBars(gbar, this._barWidth, prevTop, n, x, y, shift, j, false, (Color[])null); if (this._stackingBars) if (this._orientation == Orientation.X1RIGHT_X2UP) { bottom = ArrayMath.copy(x2); prevTop = ArrayMath.copy(y); } else { prevTop = ArrayMath.copy(x); bottom = ArrayMath.copy(x2); }   }  } private boolean equalColors(Color ca, Color cb) { return (ca == null) ? ((cb == null)) : ca.equals(cb); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void computeXY(Projector hp, Projector vp, Transcaler ts, int n, float[] x1, float[] x2, int[] x, int[] y) {
/* 673 */     ts = ts.combineWith(hp, vp);
/* 674 */     float[] xv = null;
/* 675 */     float[] yv = null;
/* 676 */     if (this._orientation == Orientation.X1RIGHT_X2UP) {
/* 677 */       xv = x1;
/* 678 */       yv = x2;
/* 679 */     } else if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/* 680 */       xv = x2;
/* 681 */       yv = x1;
/*     */     } 
/* 683 */     for (int i = 0; i < n; i++) {
/* 684 */       x[i] = ts.x(xv[i]);
/* 685 */       y[i] = ts.y(yv[i]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintBars(Graphics2D g2d, float wb, int[] bottoms, int n, int[] x, int[] y, int s, int adj, boolean fill, Color[] colors) {
/* 693 */     if (this._orientation == Orientation.X1RIGHT_X2UP) {
/* 694 */       paintBarsVertical(g2d, wb, bottoms, n, x, y, s, adj, fill, colors);
/*     */     } else {
/* 696 */       paintBarsHorizontal(g2d, wb, bottoms, n, x, y, s, adj, fill, colors);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintBarsHorizontal(Graphics2D g2d, float wb, int[] bottoms, int n, int[] x, int[] y, int s, int adj, boolean fill, Color[] colors) {
/* 704 */     float frac = adj / this._ns;
/* 705 */     int w = (n > 1) ? (int)(wb * (y[1] - y[0])) : 1;
/* 706 */     int hw = w / 2;
/* 707 */     int yim1 = y[0] - hw + s * hw;
/* 708 */     for (int i = 0; i < n; i++) {
/* 709 */       int pw, py, zero = bottoms[i];
/* 710 */       w = (i > 1) ? (int)(wb * (y[i] - y[i - 1])) : w;
/* 711 */       hw = w / 2;
/* 712 */       int ypos = ArrayMath.max(yim1, y[i] - hw);
/* 713 */       int xpos = ArrayMath.min(zero, x[i]);
/* 714 */       int h = ArrayMath.abs(zero - x[i]);
/* 715 */       if (!this._stackingBars) {
/* 716 */         py = ypos + (int)(w * frac);
/* 717 */         pw = w / this._ns;
/*     */       } else {
/* 719 */         py = ypos;
/* 720 */         pw = w;
/*     */       } 
/* 722 */       if (fill) {
/* 723 */         g2d.setColor(colors[i]);
/* 724 */         g2d.fillRect(xpos, py, h, pw);
/*     */       } else {
/*     */         
/* 727 */         g2d.drawRect(xpos, py, h, pw);
/* 728 */       }  yim1 = ypos + w;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintBarsVertical(Graphics2D g2d, float wb, int[] bottoms, int n, int[] x, int[] y, int s, int adj, boolean fill, Color[] colors) {
/* 736 */     float frac = adj / this._ns;
/* 737 */     int w = (n > 1) ? (int)(wb * (x[1] - x[0])) : 1;
/* 738 */     int hw = w / 2;
/* 739 */     int xim1 = x[0] - hw + s * hw;
/* 740 */     for (int i = 0; i < n; i++) {
/* 741 */       int pw, px, zero = bottoms[i];
/* 742 */       w = (i > 1) ? (int)(wb * (x[i] - x[i - 1])) : w;
/* 743 */       hw = w / 2;
/* 744 */       int xpos = ArrayMath.max(xim1, x[i] - hw);
/* 745 */       int ypos = ArrayMath.min(zero, y[i]);
/* 746 */       int h = ArrayMath.abs(zero - y[i]);
/*     */       
/* 748 */       if (!this._stackingBars) {
/* 749 */         px = xpos + (int)(w * frac);
/* 750 */         pw = w / this._ns;
/*     */       } else {
/* 752 */         px = xpos;
/* 753 */         pw = w;
/*     */       } 
/* 755 */       if (fill) {
/* 756 */         g2d.setColor(colors[i]);
/* 757 */         g2d.fillRect(px, ypos, pw, h);
/*     */       } else {
/*     */         
/* 760 */         g2d.drawRect(px, ypos, pw, h);
/* 761 */       }  xim1 = xpos + w;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintLabel(Graphics2D g2d, int s, int n, int[] x, int[] y, float[] z) {
/* 768 */     s /= 2;
/* 769 */     for (int i = 0; i < n; i++) {
/* 770 */       int xi = x[i];
/* 771 */       int yi = y[i];
/* 772 */       g2d.drawString(String.format(this._textFormat, new Object[] { Float.valueOf(z[i]) }), xi + s, yi - s);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/BarsView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */