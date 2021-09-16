/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.awt.ColorMap;
/*     */ import edu.mines.jtk.awt.ColorMapListener;
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.util.EnumSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColorBar
/*     */   extends IPanel
/*     */   implements ColorMapListener
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public ColorBar() {
/*  39 */     this((String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private Mosaic _mosaic = new Mosaic(1, 1, EnumSet.of(Mosaic.AxesPlacement.RIGHT)); public ColorBar(String label) {
/*  49 */     if (label != null)
/*  50 */       this._mosaic.getTileAxisRight(0).setLabel(label); 
/*  51 */     this._mosaic.setWidthMinimum(0, 15);
/*  52 */     this._mosaic.setWidthElastic(0, 0);
/*  53 */     this._tile = this._mosaic.getTile(0, 0);
/*  54 */     setLayout(new BorderLayout());
/*  55 */     add(this._mosaic, "Center");
/*     */   }
/*     */ 
/*     */   
/*     */   private Tile _tile;
/*     */   private PixelsView _pixels;
/*     */   
/*     */   public void setLabel(String label) {
/*  63 */     this._mosaic.getTileAxisRight(0).setLabel(label);
/*  64 */     revalidate();
/*     */   }
/*     */   
/*     */   public void setWidthMinimum(int widthMinimum) {
/*  68 */     this._mosaic.getTileAxisRight(0).setWidthMinimum(widthMinimum);
/*  69 */     revalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFormat(String format) {
/*  80 */     this._mosaic.getTileAxisRight(0).setFormat(format);
/*  81 */     revalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInterval(double interval) {
/*  89 */     this._mosaic.getTileAxisRight(0).setInterval(interval);
/*  90 */     revalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tile getTile() {
/*  98 */     return this._tile;
/*     */   }
/*     */   
/*     */   public void colorMapChanged(ColorMap cm) {
/* 102 */     float vmin = (float)cm.getMinValue();
/* 103 */     float vmax = (float)cm.getMaxValue();
/* 104 */     if (vmin == vmax) {
/* 105 */       vmin -= Math.ulp(vmin);
/* 106 */       vmax += Math.ulp(vmax);
/*     */     } 
/* 108 */     int nv = 256;
/* 109 */     double dv = ((vmax - vmin) / (nv - 1));
/* 110 */     double fv = vmin;
/* 111 */     Sampling vs = new Sampling(nv, dv, fv);
/* 112 */     float[][] va = new float[nv][1];
/* 113 */     Color[] ca = new Color[nv];
/* 114 */     for (int iv = 0; iv < nv; iv++) {
/* 115 */       float vi = (float)vs.getValue(iv);
/* 116 */       va[iv][0] = vi;
/* 117 */       ca[iv] = cm.getColor(vi);
/*     */     } 
/* 119 */     if (this._pixels == null) {
/* 120 */       this._pixels = new PixelsView(va);
/* 121 */       this._pixels.setOrientation(PixelsView.Orientation.X1RIGHT_X2UP);
/* 122 */       this._pixels.setInterpolation(PixelsView.Interpolation.LINEAR);
/* 123 */       this._tile.addTiledView(this._pixels);
/*     */     } 
/* 125 */     IndexColorModel icm = ColorMap.makeIndexColorModel(ca);
/* 126 */     this._pixels.setClips(vmin, vmax);
/* 127 */     this._pixels.setColorModel(icm);
/* 128 */     Sampling s1 = new Sampling(1);
/* 129 */     Sampling s2 = vs;
/* 130 */     this._pixels.set(s1, s2, va);
/*     */   }
/*     */   
/*     */   public void paintToRect(Graphics2D g2d, int x, int y, int w, int h) {
/* 134 */     this._mosaic.paintToRect(g2d, x, y, w, h);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 139 */     super.setFont(font);
/* 140 */     if (this._mosaic != null)
/* 141 */       this._mosaic.setFont(font); 
/* 142 */     revalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setForeground(Color color) {
/* 147 */     super.setForeground(color);
/* 148 */     if (this._mosaic != null) {
/* 149 */       this._mosaic.setForeground(color);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setBackground(Color color) {
/* 154 */     super.setBackground(color);
/* 155 */     if (this._mosaic != null) {
/* 156 */       this._mosaic.setBackground(color);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintComponent(Graphics g) {
/* 163 */     super.paintComponent(g);
/* 164 */     paintToRect((Graphics2D)g, 0, 0, getWidth(), getHeight());
/*     */   }
/*     */   
/*     */   protected Mosaic getMosaic() {
/* 168 */     return this._mosaic;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/ColorBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */