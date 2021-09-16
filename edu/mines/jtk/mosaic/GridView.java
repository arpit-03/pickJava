/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.util.AxisTics;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Stroke;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GridView
/*     */   extends TiledView
/*     */ {
/*     */   private Horizontal _horizontal;
/*     */   private Vertical _vertical;
/*     */   private Style _style;
/*     */   private Color _color;
/*     */   
/*     */   public enum Horizontal
/*     */   {
/*  38 */     NONE,
/*  39 */     ZERO,
/*  40 */     MAJOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Vertical
/*     */   {
/*  50 */     NONE,
/*  51 */     ZERO,
/*  52 */     MAJOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Style
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GridView()
/*     */   {
/* 373 */     this._horizontal = Horizontal.MAJOR;
/* 374 */     this._vertical = Vertical.MAJOR;
/* 375 */     this._style = Style.SOLID;
/* 376 */     this._color = null; } public GridView(Horizontal horizontal, Vertical vertical) { this._horizontal = Horizontal.MAJOR; this._vertical = Vertical.MAJOR; this._style = Style.SOLID; this._color = null; setHorizontal(horizontal); setVertical(vertical); } public GridView(Style style) { this._horizontal = Horizontal.MAJOR; this._vertical = Vertical.MAJOR; this._style = Style.SOLID; this._color = null; setStyle(style); } public GridView(Color color) { this._horizontal = Horizontal.MAJOR; this._vertical = Vertical.MAJOR; this._style = Style.SOLID; this._color = null; setColor(color); } public GridView(Horizontal horizontal, Vertical vertical, Color color) { this._horizontal = Horizontal.MAJOR; this._vertical = Vertical.MAJOR; this._style = Style.SOLID; this._color = null; setHorizontal(horizontal); setVertical(vertical); setColor(color); } public GridView(Horizontal horizontal, Vertical vertical, Color color, Style style) { this._horizontal = Horizontal.MAJOR; this._vertical = Vertical.MAJOR; this._style = Style.SOLID; this._color = null; setHorizontal(horizontal); setVertical(vertical); setColor(color); setStyle(style); } public GridView(String parameters) { this._horizontal = Horizontal.MAJOR; this._vertical = Vertical.MAJOR; this._style = Style.SOLID; this._color = null; setParameters(parameters); }
/*     */   public void setHorizontal(Horizontal horizontal) { if (this._horizontal != horizontal) {
/*     */       this._horizontal = horizontal; repaint();
/* 379 */     }  } private boolean equalColors(Color ca, Color cb) { return (ca == null) ? ((cb == null)) : ca.equals(cb); }
/*     */ 
/*     */   
/*     */   public void setVertical(Vertical vertical) {
/*     */     if (this._vertical != vertical) {
/*     */       this._vertical = vertical;
/*     */       repaint();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setColor(Color color) {
/*     */     if (!equalColors(this._color, color)) {
/*     */       this._color = color;
/*     */       repaint();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setStyle(Style style) {
/*     */     if (this._style != style) {
/*     */       this._style = style;
/*     */       repaint();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setParameters(String parameters) {
/*     */     if (parameters.contains("H0")) {
/*     */       setHorizontal(Horizontal.ZERO);
/*     */     } else if (parameters.contains("H")) {
/*     */       setHorizontal(Horizontal.MAJOR);
/*     */     } else {
/*     */       setHorizontal(Horizontal.NONE);
/*     */     } 
/*     */     if (parameters.contains("V0")) {
/*     */       setVertical(Vertical.ZERO);
/*     */     } else if (parameters.contains("V")) {
/*     */       setVertical(Vertical.MAJOR);
/*     */     } else {
/*     */       setVertical(Vertical.NONE);
/*     */     } 
/*     */     if (parameters.contains("r")) {
/*     */       setColor(Color.RED);
/*     */     } else if (parameters.contains("g")) {
/*     */       setColor(Color.GREEN);
/*     */     } else if (parameters.contains("b")) {
/*     */       setColor(Color.BLUE);
/*     */     } else if (parameters.contains("c")) {
/*     */       setColor(Color.CYAN);
/*     */     } else if (parameters.contains("m")) {
/*     */       setColor(Color.MAGENTA);
/*     */     } else if (parameters.contains("y")) {
/*     */       setColor(Color.YELLOW);
/*     */     } else if (parameters.contains("k")) {
/*     */       setColor(Color.BLACK);
/*     */     } else if (parameters.contains("w")) {
/*     */       setColor(Color.WHITE);
/*     */     } else {
/*     */       setColor((Color)null);
/*     */     } 
/*     */     if (parameters.contains("--.")) {
/*     */       setStyle(Style.DASH_DOT);
/*     */     } else if (parameters.contains("--")) {
/*     */       setStyle(Style.DASH);
/*     */     } else if (parameters.contains("-.")) {
/*     */       setStyle(Style.DOT);
/*     */     } else if (parameters.contains("-")) {
/*     */       setStyle(Style.SOLID);
/*     */     } else {
/*     */       setStyle(Style.NONE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void paint(Graphics2D g2d) {
/*     */     BasicStroke bs;
/*     */     if (this._horizontal == Horizontal.NONE && this._vertical == Vertical.NONE)
/*     */       return; 
/*     */     if (this._style == Style.NONE)
/*     */       return; 
/*     */     Tile tile = getTile();
/*     */     TileAxis axisTop = tile.getTileAxisTop();
/*     */     TileAxis axisLeft = tile.getTileAxisLeft();
/*     */     TileAxis axisBottom = tile.getTileAxisBottom();
/*     */     TileAxis axisRight = tile.getTileAxisRight();
/*     */     TileAxis axisLeftRight = (axisLeft != null) ? axisLeft : axisRight;
/*     */     TileAxis axisTopBottom = (axisTop != null) ? axisTop : axisBottom;
/*     */     if (axisLeftRight == null && axisTopBottom == null)
/*     */       return; 
/*     */     g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*     */     float lineWidth = 1.0F;
/*     */     Stroke stroke = g2d.getStroke();
/*     */     if (stroke instanceof BasicStroke) {
/*     */       BasicStroke basicStroke = (BasicStroke)stroke;
/*     */       lineWidth = basicStroke.getLineWidth();
/*     */     } 
/*     */     float width = lineWidth;
/*     */     float[] dash = null;
/*     */     if (this._style != Style.SOLID) {
/*     */       float dotLength = 0.5F * width;
/*     */       float dashLength = 2.0F * width;
/*     */       float gapLength = 2.0F * dotLength + dashLength;
/*     */       if (this._style == Style.DASH) {
/*     */         dash = new float[] { dashLength, gapLength };
/*     */       } else if (this._style == Style.DOT) {
/*     */         dash = new float[] { dotLength, gapLength };
/*     */       } else if (this._style == Style.DASH_DOT) {
/*     */         dash = new float[] { dashLength, gapLength, dotLength, gapLength };
/*     */       } 
/*     */     } 
/*     */     if (dash != null) {
/*     */       int cap = 1;
/*     */       int join = 1;
/*     */       float miter = 10.0F;
/*     */       float phase = 0.0F;
/*     */       bs = new BasicStroke(width, cap, join, miter, dash, phase);
/*     */     } else {
/*     */       bs = new BasicStroke(width);
/*     */     } 
/*     */     g2d.setStroke(bs);
/*     */     if (this._color != null)
/*     */       g2d.setColor(this._color); 
/*     */     Projector hp = getHorizontalProjector();
/*     */     Projector vp = getVerticalProjector();
/*     */     Transcaler ts = getTranscaler();
/*     */     DRectangle vr = tile.getViewRectangle();
/*     */     int w = ts.width(vr.width);
/*     */     int h = ts.height(vr.height);
/*     */     if (this._horizontal == Horizontal.ZERO) {
/*     */       int y = ts.y(vp.u(0.0D));
/*     */       g2d.drawLine(0, y, w - 1, y);
/*     */     } else if (this._horizontal == Horizontal.MAJOR && axisLeftRight != null) {
/*     */       AxisTics at = axisLeftRight.getAxisTics();
/*     */       int nticMajor = at.getCountMajor();
/*     */       double dticMajor = at.getDeltaMajor();
/*     */       double fticMajor = at.getFirstMajor();
/*     */       for (int itic = 0; itic < nticMajor; itic++) {
/*     */         double vtic = fticMajor + itic * dticMajor;
/*     */         double utic = vp.u(vtic);
/*     */         int y = ts.y(utic);
/*     */         g2d.drawLine(0, y, w - 1, y);
/*     */       } 
/*     */     } 
/*     */     if (this._vertical == Vertical.ZERO) {
/*     */       int x = ts.x(hp.u(0.0D));
/*     */       g2d.drawLine(x, 0, x, h - 1);
/*     */     } else if (this._vertical == Vertical.MAJOR && axisTopBottom != null) {
/*     */       AxisTics at = axisTopBottom.getAxisTics();
/*     */       int nticMajor = at.getCountMajor();
/*     */       double dticMajor = at.getDeltaMajor();
/*     */       double fticMajor = at.getFirstMajor();
/*     */       for (int itic = 0; itic < nticMajor; itic++) {
/*     */         double vtic = fticMajor + itic * dticMajor;
/*     */         double utic = hp.u(vtic);
/*     */         int x = ts.x(utic);
/*     */         g2d.drawLine(x, 0, x, h - 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/GridView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */