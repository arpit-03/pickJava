/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.util.Check;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Tile
/*     */   extends IPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Mosaic _mosaic;
/*     */   private int _irow;
/*     */   private int _icol;
/*     */   private ArrayList<TiledView> _tvs;
/*     */   private Projector _hp;
/*     */   private Projector _vp;
/*     */   private Projector _bhp;
/*     */   private Projector _bvp;
/*     */   private Projector _shp;
/*     */   private Projector _svp;
/*     */   private Transcaler _ts;
/*     */   private DRectangle _vr;
/*     */   
/*     */   public Mosaic getMosaic() {
/*  60 */     return this._mosaic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowIndex() {
/*  68 */     return this._irow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnIndex() {
/*  76 */     return this._icol;
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
/*     */   public void setLimits(double hmin, double vmin, double hmax, double vmax) {
/*  89 */     setHLimits(hmin, hmax);
/*  90 */     setVLimits(vmin, vmax);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHLimits(double hmin, double hmax) {
/* 101 */     Check.argument((hmin < hmax), "hmin<hmax");
/* 102 */     if (this._bhp != null && this._bhp.isLog())
/* 103 */       Check.argument((hmin > 0.0D), "hmin>0 for LOG scales"); 
/* 104 */     this._shp = new Projector(hmin, hmax);
/* 105 */     alignProjectors();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVLimits(double vmin, double vmax) {
/* 116 */     Check.argument((vmin < vmax), "vmin<vmax");
/* 117 */     if (this._bvp != null && this._bvp.isLog())
/* 118 */       Check.argument((vmin > 0.0D), "vmin>0 for LOG scales"); 
/* 119 */     if (this._bvp.v0() < this._bvp.v1()) {
/* 120 */       this._svp = new Projector(vmin, vmax);
/*     */     } else {
/* 122 */       this._svp = new Projector(vmax, vmin);
/* 123 */     }  alignProjectors();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLimitsDefault() {
/* 131 */     this._svp = null;
/* 132 */     this._shp = null;
/* 133 */     alignProjectors();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHLimitsDefault() {
/* 141 */     this._shp = null;
/* 142 */     alignProjectors();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVLimitsDefault() {
/* 150 */     this._svp = null;
/* 151 */     alignProjectors();
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
/*     */   @Deprecated
/*     */   public void setBestHorizontalProjector(Projector bhp) {
/* 164 */     this._shp = bhp;
/* 165 */     alignProjectors();
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
/*     */   @Deprecated
/*     */   public void setBestVerticalProjector(Projector bvp) {
/* 178 */     this._svp = bvp;
/* 179 */     alignProjectors();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Projector getHorizontalProjector() {
/* 187 */     return this._hp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Projector getVerticalProjector() {
/* 195 */     return this._vp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transcaler getTranscaler() {
/* 203 */     return this._ts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double pixelToWorldHorizontal(int x) {
/* 212 */     return this._hp.v(this._ts.x(x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double pixelToWorldVertical(int y) {
/* 221 */     return this._vp.v(this._ts.y(y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addTiledView(TiledView tv) {
/* 232 */     boolean removed = this._tvs.remove(tv);
/* 233 */     this._tvs.add(tv);
/* 234 */     tv.setTile(this);
/* 235 */     alignProjectors();
/* 236 */     return !removed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeTiledView(TiledView tv) {
/* 247 */     if (this._tvs.remove(tv)) {
/* 248 */       tv.setTile(null);
/* 249 */       alignProjectors();
/* 250 */       return true;
/*     */     } 
/* 252 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int countTiledViews() {
/* 260 */     return this._tvs.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TiledView getTiledView(int index) {
/* 269 */     return this._tvs.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<TiledView> getTiledViews() {
/* 277 */     return this._tvs.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DRectangle getViewRectangle() {
/* 287 */     return new DRectangle(this._vr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setViewRectangle(DRectangle vr) {
/* 297 */     this._mosaic.setViewRect(this, vr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBounds(int x, int y, int width, int height) {
/* 304 */     super.setBounds(x, y, width, height);
/* 305 */     this._ts.setMapping(width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileAxis getTileAxisTop() {
/* 313 */     return this._mosaic.getTileAxisTop(this._icol);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileAxis getTileAxisLeft() {
/* 321 */     return this._mosaic.getTileAxisLeft(this._irow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileAxis getTileAxisBottom() {
/* 329 */     return this._mosaic.getTileAxisBottom(this._icol);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileAxis getTileAxisRight() {
/* 337 */     return this._mosaic.getTileAxisRight(this._irow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisScale getHScale() {
/* 345 */     return (this._bhp != null) ? this._bhp.getScale() : AxisScale.LINEAR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisScale getVScale() {
/* 353 */     return (this._bvp != null) ? this._bvp.getScale() : AxisScale.LINEAR;
/*     */   }
/*     */   
/*     */   public void paintToRect(Graphics2D g2d, int x, int y, int w, int h) {
/* 357 */     g2d = createGraphics(g2d, x, y, w, h);
/*     */ 
/*     */     
/* 360 */     Transcaler tsPanel = this._ts;
/*     */ 
/*     */     
/* 363 */     this._ts = getTranscaler(w, h);
/*     */ 
/*     */     
/* 366 */     for (TiledView tv : this._tvs) {
/* 367 */       Graphics2D gtv = (Graphics2D)g2d.create();
/* 368 */       tv.paint(gtv);
/* 369 */       gtv.dispose();
/*     */     } 
/*     */ 
/*     */     
/* 373 */     this._ts = tsPanel;
/*     */     
/* 375 */     g2d.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintComponent(Graphics g) {
/* 382 */     super.paintComponent(g);
/* 383 */     paintToRect((Graphics2D)g, 0, 0, getWidth(), getHeight());
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
/*     */   Projector getBestHorizontalProjector() {
/*     */     return this._bhp;
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
/*     */   Projector getBestVerticalProjector() {
/*     */     return this._bvp;
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
/*     */   void setProjectors(Projector hp, Projector vp) {
/*     */     Check.argument((hp != null), "horizontal projector not null");
/*     */     Check.argument((vp != null), "vertical projector not null");
/*     */     this._hp = hp;
/*     */     this._vp = vp;
/*     */     repaint();
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
/*     */   void setHorizontalProjector(Projector hp) {
/*     */     Check.argument((hp != null), "horizontal projector not null");
/*     */     this._hp = hp;
/*     */     repaint();
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
/*     */   Tile(Mosaic mosaic, int irow, int icol) {
/* 487 */     this._tvs = new ArrayList<>();
/* 488 */     this._hp = new Projector(0.0D, 1.0D, 0.0D, 1.0D);
/* 489 */     this._vp = new Projector(0.0D, 1.0D, 0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 494 */     this._ts = new Transcaler();
/* 495 */     this._vr = new DRectangle(0.0D, 0.0D, 1.0D, 1.0D);
/*     */     this._mosaic = mosaic;
/*     */     this._irow = irow;
/*     */     this._icol = icol;
/*     */     mosaic.add(this);
/*     */   }
/*     */   private void updateBestProjectors(AxisScale hscale, AxisScale vscale) {
/* 502 */     Projector bhp = null;
/* 503 */     Projector bvp = null;
/* 504 */     int ntv = this._tvs.size();
/* 505 */     if (this._shp == null) {
/* 506 */       int itv = ntv - 1;
/* 507 */       for (; bhp == null && itv >= 0; itv--) {
/* 508 */         TiledView tv = this._tvs.get(itv);
/* 509 */         if (tv.getBestHorizontalProjector() != null)
/* 510 */           bhp = new Projector(tv.getBestHorizontalProjector()); 
/*     */       } 
/* 512 */       for (; itv >= 0; itv--) {
/* 513 */         TiledView tv = this._tvs.get(itv);
/* 514 */         bhp.merge(tv.getBestHorizontalProjector());
/*     */       } 
/*     */     } else {
/* 517 */       this._shp.setScale(hscale);
/*     */     } 
/* 519 */     if (this._svp == null) {
/* 520 */       int itv = ntv - 1;
/* 521 */       for (; bvp == null && itv >= 0; itv--) {
/* 522 */         TiledView tv = this._tvs.get(itv);
/* 523 */         if (tv.getBestVerticalProjector() != null)
/* 524 */           bvp = new Projector(tv.getBestVerticalProjector()); 
/*     */       } 
/* 526 */       for (; itv >= 0; itv--) {
/* 527 */         TiledView tv = this._tvs.get(itv);
/* 528 */         bvp.merge(tv.getBestVerticalProjector());
/*     */       } 
/*     */     } else {
/* 531 */       this._svp.setScale(vscale);
/*     */     } 
/* 533 */     this._bhp = (this._shp != null) ? this._shp : bhp;
/* 534 */     this._bvp = (this._svp != null) ? this._svp : bvp;
/*     */ 
/*     */     
/* 537 */     boolean[] checkScales = checkViewScales(hscale, vscale);
/* 538 */     if (!checkScales[0])
/* 539 */       this._bhp.setScale(AxisScale.LINEAR); 
/* 540 */     if (!checkScales[1]) {
/* 541 */       this._bvp.setScale(AxisScale.LINEAR);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean[] checkViewScales(AxisScale hscale, AxisScale vscale) {
/* 546 */     boolean[] compat = { true, true };
/* 547 */     for (TiledView tv : this._tvs) {
/* 548 */       compat[0] = (tv.getHScale() == hscale && compat[0]);
/* 549 */       compat[1] = (tv.getVScale() == vscale && compat[1]);
/*     */     } 
/* 551 */     return compat;
/*     */   }
/*     */   
/*     */   void setVerticalProjector(Projector vp) {
/*     */     Check.argument((vp != null), "vertical projector not null");
/*     */     this._vp = vp;
/*     */     repaint();
/*     */   }
/*     */   
/*     */   void alignProjectors() {
/*     */     alignProjectors(this._hp.getScale(), this._vp.getScale());
/*     */   }
/*     */   
/*     */   void alignProjectors(AxisScale hscale, AxisScale vscale) {
/*     */     updateBestProjectors(hscale, vscale);
/*     */     this._mosaic.alignProjectors(this);
/*     */   }
/*     */   
/*     */   void setViewRect(DRectangle vr) {
/*     */     if (this._vr.x != vr.x || this._vr.y != vr.y || this._vr.width != vr.width || this._vr.height != vr.height) {
/*     */       this._vr = new DRectangle(vr);
/*     */       this._vr.x = Math.max(0.0D, Math.min(1.0D, this._vr.x));
/*     */       this._vr.y = Math.max(0.0D, Math.min(1.0D, this._vr.y));
/*     */       this._vr.width = Math.max(0.0D, Math.min(1.0D - this._vr.x, this._vr.width));
/*     */       this._vr.height = Math.max(0.0D, Math.min(1.0D - this._vr.y, this._vr.height));
/*     */       this._ts.setMapping(this._vr.x, this._vr.y, this._vr.x + this._vr.width, this._vr.y + this._vr.height);
/*     */       repaint();
/*     */     } 
/*     */   }
/*     */   
/*     */   Transcaler getTranscaler(int w, int h) {
/*     */     return new Transcaler(this._vr.x, this._vr.y, this._vr.x + this._vr.width, this._vr.y + this._vr.height, 0, 0, w - 1, h - 1);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/Tile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */