/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Stroke;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PointsView
/*     */   extends TiledView
/*     */ {
/*     */   int _ns;
/*     */   ArrayList<Integer> _nx;
/*     */   ArrayList<float[]> _x1;
/*     */   ArrayList<float[]> _x2;
/*     */   ArrayList<float[]> _x3;
/*     */   int _nxmax;
/*     */   private Orientation _orientation;
/*     */   private Line _lineStyle;
/*     */   private float _lineWidth;
/*     */   private Color _lineColor;
/*     */   private Mark _markStyle;
/*     */   private float _markSize;
/*     */   private Color _markColor;
/*     */   private String _textFormat;
/*     */   
/*     */   public enum Orientation
/*     */   {
/*  54 */     X1RIGHT_X2UP,
/*  55 */     X1DOWN_X2RIGHT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Mark
/*     */   {
/*  63 */     NONE,
/*  64 */     POINT,
/*  65 */     PLUS,
/*  66 */     CROSS,
/*  67 */     ASTERISK,
/*  68 */     HOLLOW_CIRCLE,
/*  69 */     HOLLOW_SQUARE,
/*  70 */     FILLED_CIRCLE,
/*  71 */     FILLED_SQUARE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Line
/*     */   {
/*  79 */     NONE,
/*  80 */     SOLID,
/*  81 */     DASH,
/*  82 */     DOT,
/*  83 */     DASH_DOT;
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
/*     */   public PointsView(float[] x2)
/*     */   {
/* 571 */     this._nx = new ArrayList<>();
/* 572 */     this._x1 = (ArrayList)new ArrayList<>();
/* 573 */     this._x2 = (ArrayList)new ArrayList<>();
/* 574 */     this._x3 = (ArrayList)new ArrayList<>();
/*     */     
/* 576 */     this._orientation = Orientation.X1RIGHT_X2UP;
/* 577 */     this._lineStyle = Line.SOLID;
/* 578 */     this._lineWidth = 0.0F;
/* 579 */     this._lineColor = null;
/* 580 */     this._markStyle = Mark.NONE;
/* 581 */     this._markSize = -1.0F;
/* 582 */     this._markColor = null;
/* 583 */     this._textFormat = "%1.4G"; float[] x1 = ArrayMath.rampfloat(0.0F, 1.0F, x2.length); set(x1, x2); } public PointsView(Sampling s1, float[] x2) { this._nx = new ArrayList<>(); this._x1 = (ArrayList)new ArrayList<>(); this._x2 = (ArrayList)new ArrayList<>(); this._x3 = (ArrayList)new ArrayList<>(); this._orientation = Orientation.X1RIGHT_X2UP; this._lineStyle = Line.SOLID; this._lineWidth = 0.0F; this._lineColor = null; this._markStyle = Mark.NONE; this._markSize = -1.0F; this._markColor = null; this._textFormat = "%1.4G"; set(s1, x2); } public PointsView(float[] x1, float[] x2) { this._nx = new ArrayList<>(); this._x1 = (ArrayList)new ArrayList<>(); this._x2 = (ArrayList)new ArrayList<>(); this._x3 = (ArrayList)new ArrayList<>(); this._orientation = Orientation.X1RIGHT_X2UP; this._lineStyle = Line.SOLID; this._lineWidth = 0.0F; this._lineColor = null; this._markStyle = Mark.NONE; this._markSize = -1.0F; this._markColor = null; this._textFormat = "%1.4G"; set(x1, x2); } public PointsView(float[][] x1, float[][] x2) { this._nx = new ArrayList<>(); this._x1 = (ArrayList)new ArrayList<>(); this._x2 = (ArrayList)new ArrayList<>(); this._x3 = (ArrayList)new ArrayList<>(); this._orientation = Orientation.X1RIGHT_X2UP; this._lineStyle = Line.SOLID; this._lineWidth = 0.0F; this._lineColor = null; this._markStyle = Mark.NONE; this._markSize = -1.0F; this._markColor = null; this._textFormat = "%1.4G"; set(x1, x2); } public PointsView(float[] x1, float[] x2, float[] x3) { this._nx = new ArrayList<>(); this._x1 = (ArrayList)new ArrayList<>(); this._x2 = (ArrayList)new ArrayList<>(); this._x3 = (ArrayList)new ArrayList<>(); this._orientation = Orientation.X1RIGHT_X2UP; this._lineStyle = Line.SOLID; this._lineWidth = 0.0F; this._lineColor = null; this._markStyle = Mark.NONE; this._markSize = -1.0F; this._markColor = null; this._textFormat = "%1.4G"; set(x1, x2, x3); } public PointsView(float[][] x1, float[][] x2, float[][] x3) { this._nx = new ArrayList<>(); this._x1 = (ArrayList)new ArrayList<>(); this._x2 = (ArrayList)new ArrayList<>(); this._x3 = (ArrayList)new ArrayList<>(); this._orientation = Orientation.X1RIGHT_X2UP; this._lineStyle = Line.SOLID; this._lineWidth = 0.0F; this._lineColor = null; this._markStyle = Mark.NONE; this._markSize = -1.0F; this._markColor = null; this._textFormat = "%1.4G"; set(x1, x2, x3); } public void set(Sampling s1, float[] x2) { Check.argument((s1.getCount() == x2.length), "s1 count equals x2 length"); int n1 = x2.length; float[] x1 = new float[n1]; for (int i1 = 0; i1 < n1; i1++) x1[i1] = (float)s1.getValue(i1);  set(x1, x2); } public void set(float[] x1, float[] x2) { set(x1, x2, (float[])null); } public void set(float[] x1, float[] x2, float[] x3) { Check.argument((x1.length == x2.length), "x1.length equals x2.length"); if (x3 != null) Check.argument((x1.length == x3.length), "x1.length equals x3.length");  this._ns = 1; this._nx.clear(); this._x1.clear(); this._x2.clear(); this._x3.clear(); this._nxmax = x1.length; this._nx.add(Integer.valueOf(x1.length)); this._x1.add(ArrayMath.copy(x1)); this._x2.add(ArrayMath.copy(x2)); if (x3 != null) this._x3.add(ArrayMath.copy(x3));  updateBestProjectors(); repaint(); }
/*     */   public void set(float[][] x1, float[][] x2) { set(x1, x2, (float[][])null); }
/*     */   public void set(float[][] x1, float[][] x2, float[][] x3) { Check.argument((x1.length == x2.length), "x1.length equals x2.length"); if (x3 != null) Check.argument((x1.length == x3.length), "x1.length equals x3.length");  this._ns = x1.length; this._nx.clear(); this._x1.clear(); this._x2.clear(); this._x3.clear(); this._nxmax = 0; for (int is = 0; is < this._ns; is++) { Check.argument(((x1[is]).length == (x2[is]).length), "x1[i].length equals x2[i].length"); this._nxmax = ArrayMath.max(this._nxmax, (x1[is]).length); this._nx.add(Integer.valueOf((x1[is]).length)); this._x1.add(ArrayMath.copy(x1[is])); this._x2.add(ArrayMath.copy(x2[is])); if (x3 != null) this._x3.add(ArrayMath.copy(x3[is]));  }  updateBestProjectors(); repaint(); }
/*     */   public void setOrientation(Orientation orientation) { if (this._orientation != orientation) { this._orientation = orientation; updateBestProjectors(); repaint(); }  }
/*     */   public Orientation getOrientation() { return this._orientation; }
/*     */   public void setStyle(String style) { if (style.contains("r")) { setLineColor(Color.RED); setMarkColor(Color.RED); } else if (style.contains("g")) { setLineColor(Color.GREEN); setMarkColor(Color.GREEN); } else if (style.contains("b")) { setLineColor(Color.BLUE); setMarkColor(Color.BLUE); } else if (style.contains("c")) { setLineColor(Color.CYAN); setMarkColor(Color.CYAN); } else if (style.contains("m")) { setLineColor(Color.MAGENTA); setMarkColor(Color.MAGENTA); } else if (style.contains("y")) { setLineColor(Color.YELLOW); setMarkColor(Color.YELLOW); } else if (style.contains("k")) { setLineColor(Color.BLACK); setMarkColor(Color.BLACK); } else if (style.contains("w")) { setLineColor(Color.WHITE); setMarkColor(Color.WHITE); } else { setLineColor((Color)null); setMarkColor((Color)null); }  if (style.contains("--.")) { setLineStyle(Line.DASH_DOT); } else if (style.contains("--")) { setLineStyle(Line.DASH); } else if (style.contains("-.")) { setLineStyle(Line.DOT); } else if (style.contains("-")) { setLineStyle(Line.SOLID); } else { setLineStyle(Line.NONE); }  if (style.contains("+")) { setMarkStyle(Mark.PLUS); } else if (style.contains("x")) { setMarkStyle(Mark.CROSS); } else if (style.contains("o")) { setMarkStyle(Mark.HOLLOW_CIRCLE); } else if (style.contains("O")) { setMarkStyle(Mark.FILLED_CIRCLE); } else if (style.contains("s")) { setMarkStyle(Mark.HOLLOW_SQUARE); } else if (style.contains("S")) { setMarkStyle(Mark.FILLED_SQUARE); } else if (style.contains(".")) { int i = style.indexOf("."); if (i == 0 || style.charAt(i - 1) != '-') setMarkStyle(Mark.POINT);  } else { setMarkStyle(Mark.NONE); }  }
/* 589 */   private void updateBestProjectors() { updateBestProjectors(getHScale(), getVScale()); }
/*     */   public void setLineStyle(Line style) { this._lineStyle = style; repaint(); }
/*     */   public void setLineWidth(float width) { if (this._lineWidth != width) { this._lineWidth = width; updateBestProjectors(); repaint(); }  }
/*     */   public void setLineColor(Color color) { if (!equalColors(this._lineColor, color)) { this._lineColor = color; repaint(); }  }
/*     */   public void setMarkStyle(Mark style) { if (this._markStyle != style) { this._markStyle = style; updateBestProjectors(); repaint(); }
/* 594 */      } private void updateBestProjectors(AxisScale hscale, AxisScale vscale) { float x1min = Float.MAX_VALUE;
/* 595 */     float x2min = Float.MAX_VALUE;
/* 596 */     float x1max = -3.4028235E38F;
/* 597 */     float x2max = -3.4028235E38F;
/* 598 */     for (int is = 0; is < this._ns; is++) {
/* 599 */       int nx = ((Integer)this._nx.get(is)).intValue();
/* 600 */       float[] x1 = this._x1.get(is);
/* 601 */       float[] x2 = this._x2.get(is);
/* 602 */       for (int ix = 0; ix < nx; ix++) {
/* 603 */         float x1i = x1[ix];
/* 604 */         float x2i = x2[ix];
/* 605 */         x1min = ArrayMath.min(x1min, x1i);
/* 606 */         x2min = ArrayMath.min(x2min, x2i);
/* 607 */         x1max = ArrayMath.max(x1max, x1i);
/* 608 */         x2max = ArrayMath.max(x2max, x2i);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 613 */       if (hscale == AxisScale.LOG10) {
/* 614 */         int ind = getFirstPositiveInd(x1);
/* 615 */         if (ind > -1) {
/* 616 */           x1min = ArrayMath.max(x1min, x1[ind]);
/*     */         }
/*     */       } 
/* 619 */       if (vscale == AxisScale.LOG10) {
/* 620 */         int ind = getSmallestPositiveInd(x2);
/* 621 */         if (ind > -1) {
/* 622 */           x2min = ArrayMath.max(x2min, x2[ind]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 627 */     if (x1min == x1max) {
/* 628 */       x1min -= ArrayMath.ulp(x1min);
/* 629 */       x1max += ArrayMath.ulp(x1max);
/*     */     } 
/* 631 */     if (x2min == x2max) {
/* 632 */       x2min -= ArrayMath.ulp(x2min);
/* 633 */       x2max += ArrayMath.ulp(x2max);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 642 */     double u0 = 0.0D;
/* 643 */     double u1 = 1.0D;
/* 644 */     if (this._markStyle != Mark.NONE || this._lineWidth > 1.0F) {
/* 645 */       u0 = 0.01D;
/* 646 */       u1 = 0.99D;
/*     */     } 
/*     */ 
/*     */     
/* 650 */     Projector bhp = null;
/* 651 */     Projector bvp = null;
/* 652 */     if (this._orientation == Orientation.X1RIGHT_X2UP) {
/* 653 */       bhp = (x1min < x1max) ? new Projector(x1min, x1max, u0, u1, hscale) : null;
/* 654 */       bvp = (x2min < x2max) ? new Projector(x2max, x2min, u0, u1, vscale) : null;
/* 655 */     } else if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/* 656 */       bhp = (x2min < x2max) ? new Projector(x2min, x2max, u0, u1, hscale) : null;
/* 657 */       bvp = (x1min < x1max) ? new Projector(x1min, x1max, u0, u1, vscale) : null;
/*     */     } 
/* 659 */     setBestProjectors(bhp, bvp); }
/*     */   public void setMarkSize(float size) { if (this._markSize != size) { this._markSize = size; updateBestProjectors(); repaint(); }  }
/*     */   public void setMarkColor(Color color) { if (!equalColors(this._markColor, color)) { this._markColor = color; repaint(); }  }
/*     */   public void setTextFormat(String format) { this._textFormat = format; repaint(); }
/* 663 */   public PointsView setScales(AxisScale hscale, AxisScale vscale) { if (hscale != getHScale() || vscale != getVScale()) updateBestProjectors(hscale, vscale);  return this; } public void paint(Graphics2D g2d) { g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); Projector hp = getHorizontalProjector(); Projector vp = getVerticalProjector(); Transcaler ts = getTranscaler(); float fontSize = g2d.getFont().getSize2D(); float lineWidth = 1.0F; Stroke stroke = g2d.getStroke(); if (stroke instanceof BasicStroke) { BasicStroke bs = (BasicStroke)stroke; lineWidth = bs.getLineWidth(); }  Graphics2D gline = null; if (this._lineStyle != Line.NONE) { BasicStroke bs; gline = (Graphics2D)g2d.create(); float width = lineWidth; if (this._lineWidth != 0.0F) width *= this._lineWidth;  float[] dash = null; if (this._lineStyle != Line.SOLID) { float dotLength = 0.5F * width; float dashLength = 2.0F * width; float gapLength = 2.0F * dotLength + dashLength; if (this._lineStyle == Line.DASH) { dash = new float[] { dashLength, gapLength }; } else if (this._lineStyle == Line.DOT) { dash = new float[] { dotLength, gapLength }; } else if (this._lineStyle == Line.DASH_DOT) { dash = new float[] { dashLength, gapLength, dotLength, gapLength }; }  }  if (dash != null) { int cap = 1; int join = 1; float miter = 10.0F; float phase = 0.0F; bs = new BasicStroke(width, cap, join, miter, dash, phase); } else { bs = new BasicStroke(width); }  gline.setStroke(bs); if (this._lineColor != null) gline.setColor(this._lineColor);  }  Graphics2D gmark = null; int markSize = ArrayMath.round(fontSize / 2.0F); if (this._markStyle != Mark.NONE) { gmark = (Graphics2D)g2d.create(); if (this._markSize >= 0.0F) markSize = ArrayMath.round(this._markSize * lineWidth);  if (this._markColor != null) gmark.setColor(this._markColor);  float width = lineWidth; if (this._lineWidth != 0.0F) width *= this._lineWidth;  BasicStroke bs = new BasicStroke(width); gmark.setStroke(bs); }  Graphics2D gtext = null; if (this._x3.size() > 0) gtext = (Graphics2D)g2d.create();  int[] x = new int[this._nxmax]; int[] y = new int[this._nxmax]; for (int is = 0; is < this._ns; is++) { int n = ((Integer)this._nx.get(is)).intValue(); float[] x1 = this._x1.get(is); float[] x2 = this._x2.get(is); computeXY(hp, vp, ts, n, x1, x2, x, y); if (gline != null) gline.drawPolyline(x, y, n);  if (gmark != null) if (this._markStyle == Mark.POINT) { paintPoint(gmark, n, x, y); } else if (this._markStyle == Mark.PLUS) { paintPlus(gmark, markSize, n, x, y); } else if (this._markStyle == Mark.CROSS) { paintCross(gmark, markSize, n, x, y); } else if (this._markStyle == Mark.FILLED_CIRCLE) { paintFilledCircle(gmark, markSize, n, x, y); } else if (this._markStyle == Mark.HOLLOW_CIRCLE) { paintHollowCircle(gmark, markSize, n, x, y); } else if (this._markStyle == Mark.FILLED_SQUARE) { paintFilledSquare(gmark, markSize, n, x, y); } else if (this._markStyle == Mark.HOLLOW_SQUARE) { paintHollowSquare(gmark, markSize, n, x, y); }   if (gtext != null) { float[] z = this._x3.get(is); paintLabel(gtext, markSize, n, x, y, z); }  }  } private int getFirstPositiveInd(float[] x) { int ind = -1;
/* 664 */     for (int i = 0; i < x.length; i++) {
/* 665 */       if (x[i] > 0.0F) {
/* 666 */         ind = i;
/*     */         break;
/*     */       } 
/*     */     } 
/* 670 */     return ind; }
/*     */ 
/*     */   
/*     */   private int getSmallestPositiveInd(float[] x) {
/* 674 */     int ind = -1;
/* 675 */     float smallest = Float.MAX_VALUE;
/* 676 */     for (int i = 0; i < x.length; i++) {
/* 677 */       if (x[i] > 0.0F && x[i] < smallest) {
/* 678 */         ind = i;
/* 679 */         smallest = x[i];
/*     */       } 
/*     */     } 
/* 682 */     return ind;
/*     */   }
/*     */   
/*     */   private boolean equalColors(Color ca, Color cb) {
/* 686 */     return (ca == null) ? ((cb == null)) : ca.equals(cb);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void computeXY(Projector hp, Projector vp, Transcaler ts, int n, float[] x1, float[] x2, int[] x, int[] y) {
/* 693 */     ts = ts.combineWith(hp, vp);
/* 694 */     float[] xv = null;
/* 695 */     float[] yv = null;
/* 696 */     if (this._orientation == Orientation.X1RIGHT_X2UP) {
/* 697 */       xv = x1;
/* 698 */       yv = x2;
/* 699 */     } else if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/* 700 */       xv = x2;
/* 701 */       yv = x1;
/*     */     } 
/* 703 */     double hLeft = Math.min(hp.v0(), hp.v1());
/* 704 */     double vBot = Math.min(vp.v0(), vp.v1());
/* 705 */     for (int i = 0; i < n; i++) {
/* 706 */       x[i] = ts.x((xv[i] <= 0.0F && hp.getScale() == AxisScale.LOG10) ? hLeft : xv[i]);
/* 707 */       y[i] = ts.y((yv[i] <= 0.0F && vp.getScale() == AxisScale.LOG10) ? vBot : yv[i]);
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
/*     */   private void paintPoint(Graphics2D g2d, int n, int[] x, int[] y) {
/* 726 */     for (int i = 0; i < n; i++) {
/* 727 */       int xi = x[i];
/* 728 */       int yi = y[i];
/* 729 */       g2d.drawLine(xi, yi, xi, yi);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void paintPlus(Graphics2D g2d, int s, int n, int[] x, int[] y) {
/* 734 */     int wh = 2 * s / 2;
/* 735 */     int xy = wh / 2;
/* 736 */     for (int i = 0; i < n; i++) {
/* 737 */       int xi = x[i];
/* 738 */       int yi = y[i];
/* 739 */       g2d.drawLine(xi - xy, yi, xi + xy, yi);
/* 740 */       g2d.drawLine(xi, yi - xy, xi, yi + xy);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void paintCross(Graphics2D g2d, int s, int n, int[] x, int[] y) {
/* 745 */     int wh = 2 * s / 2;
/* 746 */     int xy = wh / 2;
/* 747 */     for (int i = 0; i < n; i++) {
/* 748 */       int xi = x[i];
/* 749 */       int yi = y[i];
/* 750 */       g2d.drawLine(xi - xy, yi - xy, xi + xy, yi + xy);
/* 751 */       g2d.drawLine(xi + xy, yi - xy, xi - xy, yi + xy);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintFilledCircle(Graphics2D g2d, int s, int n, int[] x, int[] y) {
/* 758 */     int wh = 1 + 2 * s / 2;
/* 759 */     int xy = wh / 2;
/* 760 */     for (int i = 0; i < n; i++) {
/* 761 */       g2d.fillOval(x[i] - xy, y[i] - xy, wh, wh);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintHollowCircle(Graphics2D g2d, int s, int n, int[] x, int[] y) {
/* 767 */     int wh = 1 + 2 * s / 2;
/* 768 */     int xy = wh / 2;
/* 769 */     for (int i = 0; i < n; i++) {
/* 770 */       g2d.drawOval(x[i] - xy, y[i] - xy, wh - 1, wh - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintFilledSquare(Graphics2D g2d, int s, int n, int[] x, int[] y) {
/* 776 */     int wh = 1 + 2 * s / 2;
/* 777 */     int xy = wh / 2;
/* 778 */     for (int i = 0; i < n; i++) {
/* 779 */       g2d.fillRect(x[i] - xy, y[i] - xy, wh, wh);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintHollowSquare(Graphics2D g2d, int s, int n, int[] x, int[] y) {
/* 785 */     int wh = 1 + 2 * s / 2;
/* 786 */     int xy = wh / 2;
/* 787 */     for (int i = 0; i < n; i++) {
/* 788 */       g2d.drawRect(x[i] - xy, y[i] - xy, wh - 1, wh - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintLabel(Graphics2D g2d, int s, int n, int[] x, int[] y, float[] z) {
/* 794 */     s /= 2;
/* 795 */     for (int i = 0; i < n; i++) {
/* 796 */       int xi = x[i];
/* 797 */       int yi = y[i];
/* 798 */       g2d.drawString(String.format(this._textFormat, new Object[] { Float.valueOf(z[i]) }), xi + s, yi - s);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/PointsView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */