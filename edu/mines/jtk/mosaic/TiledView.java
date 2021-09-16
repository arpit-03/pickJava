/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Graphics2D;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TiledView
/*     */ {
/*     */   private Tile _tile;
/*     */   
/*     */   public abstract void paint(Graphics2D paramGraphics2D);
/*     */   
/*     */   public Tile getTile() {
/*  47 */     return this._tile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Projector getHorizontalProjector() {
/*  58 */     return (this._tile != null) ? this._tile.getHorizontalProjector() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Projector getVerticalProjector() {
/*  69 */     return (this._tile != null) ? this._tile.getVerticalProjector() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transcaler getTranscaler() {
/*  80 */     return (this._tile != null) ? this._tile.getTranscaler() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisScale getHScale() {
/*  88 */     return (this._bhp != null) ? this._bhp.getScale() : AxisScale.LINEAR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisScale getVScale() {
/*  96 */     return (this._bvp != null) ? this._bvp.getScale() : AxisScale.LINEAR;
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
/*     */   public TiledView setScales(AxisScale hscale, AxisScale vscale) {
/* 108 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TiledView setScales(AxisScale scale) {
/* 118 */     return setScales(scale, scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TiledView setHScale(AxisScale scale) {
/* 128 */     return setScales(scale, this._bvp.getScale());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TiledView setVScale(AxisScale scale) {
/* 138 */     return setScales(this._bhp.getScale(), scale);
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
/*     */   protected void setBestProjectors(Projector bhp, Projector bvp) {
/* 154 */     if (!equal(this._bhp, bhp) || !equal(this._bvp, bvp)) {
/* 155 */       this._bhp = (bhp != null) ? new Projector(bhp) : null;
/* 156 */       this._bvp = (bvp != null) ? new Projector(bvp) : null;
/*     */       
/* 158 */       if (setScales(bhp.getScale(), bvp.getScale()) == null) {
/* 159 */         this._bhp.setScale(AxisScale.LINEAR);
/* 160 */         this._bvp.setScale(AxisScale.LINEAR);
/*     */       } 
/* 162 */       if (this._tile != null) {
/* 163 */         this._tile.alignProjectors(this._bhp.getScale(), this._bvp.getScale());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Projector getBestHorizontalProjector() {
/* 172 */     return this._bhp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Projector getBestVerticalProjector() {
/* 180 */     return this._bvp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void repaint() {
/* 187 */     if (this._tile != null) {
/* 188 */       this._tile.repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getLineWidth(Graphics2D g2d) {
/* 197 */     float lineWidth = 1.0F;
/* 198 */     Stroke stroke = g2d.getStroke();
/* 199 */     if (stroke instanceof BasicStroke) {
/* 200 */       BasicStroke bs = (BasicStroke)stroke;
/* 201 */       lineWidth = bs.getLineWidth();
/*     */     } 
/* 203 */     return lineWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setTile(Tile tile) {
/* 213 */     this._tile = tile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   private Projector _bhp = null;
/* 221 */   private Projector _bvp = null;
/*     */   
/*     */   private boolean equal(Projector a, Projector b) {
/* 224 */     return (a == null) ? ((b == null)) : a.equals(b);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/TiledView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */