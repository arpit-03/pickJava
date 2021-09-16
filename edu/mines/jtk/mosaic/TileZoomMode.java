/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.awt.Mode;
/*     */ import edu.mines.jtk.awt.ModeManager;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileZoomMode
/*     */   extends Mode
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Tile _tile;
/*     */   private TileAxis _axis;
/*     */   private int _xbegin;
/*     */   private int _ybegin;
/*     */   private int _xdraw;
/*     */   private int _ydraw;
/*     */   private MouseListener _ml;
/*     */   private MouseMotionListener _mml;
/*     */   
/*     */   public TileZoomMode(ModeManager modeManager) {
/*  38 */     super(modeManager);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     this._ml = new MouseAdapter() {
/*     */         public void mousePressed(MouseEvent e) {
/*  71 */           if (e.isControlDown() && e.isAltDown()) {
/*  72 */             Object source = e.getSource();
/*  73 */             if (source instanceof Tile) {
/*  74 */               Tile tile = (Tile)source;
/*  75 */               Container frame = tile.getTopLevelAncestor();
/*  76 */               Projector hp = tile.getHorizontalProjector();
/*  77 */               Projector vp = tile.getVerticalProjector();
/*  78 */               DRectangle r = tile.getViewRectangle();
/*  79 */               double ffsize = frame.getFont().getSize();
/*  80 */               double fwidth = frame.getWidth();
/*  81 */               double fheight = frame.getHeight();
/*  82 */               double fratio = fwidth / fheight;
/*  83 */               double pwidth = tile.getWidth();
/*  84 */               double pheight = tile.getHeight();
/*  85 */               double pratio = pwidth / pheight;
/*  86 */               double vwidth = Math.abs(hp.v(r.x) - hp.v(r.x + r.width));
/*  87 */               double vheight = Math.abs(vp.v(r.y) - vp.v(r.y + r.height));
/*  88 */               double vratio = vwidth / vheight;
/*  89 */               System.out.printf("Tile: frame font size  = %1d%n", new Object[] { Integer.valueOf((int)ffsize) });
/*  90 */               System.out.printf("      frame width  = %1d%n", new Object[] { Integer.valueOf((int)fwidth) });
/*  91 */               System.out.printf("      frame height = %1d%n", new Object[] { Integer.valueOf((int)fheight) });
/*  92 */               System.out.printf("      frame ratio  = %1.4g%n", new Object[] { Double.valueOf(fratio) });
/*  93 */               System.out.printf("      pixel width  = %1d%n", new Object[] { Integer.valueOf((int)pwidth) });
/*  94 */               System.out.printf("      pixel height = %1d%n", new Object[] { Integer.valueOf((int)pheight) });
/*  95 */               System.out.printf("      pixel ratio  = %1.4g%n", new Object[] { Double.valueOf(pratio) });
/*  96 */               System.out.printf("      value width  = %1.4g%n", new Object[] { Double.valueOf(vwidth) });
/*  97 */               System.out.printf("      value height = %1.4g%n", new Object[] { Double.valueOf(vheight) });
/*  98 */               System.out.printf("      value ratio  = %1.4g%n", new Object[] { Double.valueOf(vratio) });
/*     */             } 
/*     */           } else {
/* 101 */             TileZoomMode.this.beginZoom(e);
/*     */           } 
/*     */         }
/*     */         public void mouseReleased(MouseEvent e) {
/* 105 */           TileZoomMode.this.endZoom();
/*     */         }
/*     */       };
/*     */     
/* 109 */     this._mml = new MouseMotionAdapter()
/*     */       {
/* 111 */         public void mouseDragged(MouseEvent e) { TileZoomMode.this.duringZoom(e); } };
/*     */     setName("Zoom");
/*     */     setIcon(loadIcon(TileZoomMode.class, "ZoomIn16.gif"));
/*     */     setMnemonicKey(90);
/*     */     setAcceleratorKey(KeyStroke.getKeyStroke(90, 0));
/* 116 */     setShortDescription("Zoom in tile or axis"); } private void beginZoom(MouseEvent e) { this._xbegin = e.getX();
/* 117 */     this._ybegin = e.getY();
/* 118 */     Object source = e.getSource();
/* 119 */     if (source instanceof Tile) {
/* 120 */       Tile tile = this._tile = (Tile)source;
/* 121 */       drawZoom(tile, this._xbegin, this._ybegin, true, true);
/* 122 */       tile.addMouseMotionListener(this._mml);
/* 123 */     } else if (source instanceof TileAxis) {
/* 124 */       TileAxis axis = this._axis = (TileAxis)source;
/* 125 */       drawZoom(axis, this._xbegin, this._ybegin, this._axis.isHorizontal(), this._axis.isVertical());
/* 126 */       axis.addMouseMotionListener(this._mml);
/*     */     }  }
/*     */   protected void setActive(Component component, boolean active) { if (component instanceof Tile || component instanceof TileAxis)
/*     */       if (active) { component.addMouseListener(this._ml); }
/*     */       else { component.removeMouseListener(this._ml); }
/* 131 */         } private void duringZoom(MouseEvent e) { int xdraw = e.getX();
/* 132 */     int ydraw = e.getY();
/* 133 */     if (this._tile != null) {
/* 134 */       drawZoom(this._tile, this._xdraw, this._ydraw, true, true);
/* 135 */       drawZoom(this._tile, xdraw, ydraw, true, true);
/* 136 */     } else if (this._axis != null) {
/* 137 */       drawZoom(this._axis, this._xdraw, this._ydraw, this._axis.isHorizontal(), this._axis.isVertical());
/* 138 */       drawZoom(this._axis, xdraw, ydraw, this._axis.isHorizontal(), this._axis.isVertical());
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void endZoom() {
/* 145 */     Tile tile = null;
/* 146 */     boolean zx = false;
/* 147 */     boolean zy = false;
/*     */ 
/*     */     
/* 150 */     if (this._tile != null) {
/* 151 */       tile = this._tile;
/* 152 */       zx = true;
/* 153 */       zy = true;
/* 154 */       drawZoom(this._tile, this._xdraw, this._ydraw, zx, zy);
/* 155 */       this._tile.removeMouseMotionListener(this._mml);
/* 156 */       this._tile = null;
/*     */ 
/*     */     
/*     */     }
/* 160 */     else if (this._axis != null) {
/* 161 */       tile = this._axis.getTile();
/* 162 */       zx = this._axis.isHorizontal();
/* 163 */       zy = this._axis.isVertical();
/* 164 */       drawZoom(this._axis, this._xdraw, this._ydraw, zx, zy);
/* 165 */       this._axis.removeMouseMotionListener(this._mml);
/* 166 */       this._axis = null;
/*     */     } 
/*     */ 
/*     */     
/* 170 */     if (tile != null && (zx || zy)) {
/* 171 */       int xmin = Math.min(this._xbegin, this._xdraw);
/* 172 */       int xmax = Math.max(this._xbegin, this._xdraw);
/* 173 */       int ymin = Math.min(this._ybegin, this._ydraw);
/* 174 */       int ymax = Math.max(this._ybegin, this._ydraw);
/* 175 */       Transcaler ts = tile.getTranscaler();
/* 176 */       DRectangle vr = tile.getViewRectangle();
/*     */ 
/*     */       
/* 179 */       if (zx) {
/* 180 */         vr.x = (xmin < xmax) ? ts.x(xmin) : 0.0D;
/* 181 */         vr.width = (xmin < xmax) ? (ts.x(xmax) - vr.x) : 1.0D;
/*     */       } 
/*     */ 
/*     */       
/* 185 */       if (zy) {
/* 186 */         vr.y = (ymin < ymax) ? ts.y(ymin) : 0.0D;
/* 187 */         vr.height = (ymin < ymax) ? (ts.y(ymax) - vr.y) : 1.0D;
/*     */       } 
/*     */ 
/*     */       
/* 191 */       double tiny = 1.0E-4D;
/* 192 */       if (vr.width < tiny) {
/* 193 */         vr.x -= (tiny - vr.width) / 2.0D;
/* 194 */         vr.width = tiny;
/*     */       } 
/* 196 */       if (vr.height < tiny) {
/* 197 */         vr.y -= (tiny - vr.height) / 2.0D;
/* 198 */         vr.height = tiny;
/*     */       } 
/* 200 */       vr.x = Math.min(1.0D - vr.width, vr.x);
/* 201 */       vr.y = Math.min(1.0D - vr.height, vr.y);
/*     */ 
/*     */       
/* 204 */       tile.setViewRectangle(vr);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawZoom(Tile tile, int x, int y, boolean bx, boolean by) {
/* 209 */     if (tile == null) {
/*     */       return;
/*     */     }
/*     */     
/* 213 */     if (tile == this._tile) {
/*     */ 
/*     */       
/* 216 */       x = Math.max(0, Math.min(tile.getWidth() - 1, x));
/* 217 */       y = Math.max(0, Math.min(tile.getHeight() - 1, y));
/*     */ 
/*     */       
/* 220 */       drawRect(tile, x, y, bx, by);
/*     */ 
/*     */       
/* 223 */       Mosaic mosaic = tile.getMosaic();
/* 224 */       int jrow = tile.getRowIndex();
/* 225 */       int jcol = tile.getColumnIndex();
/* 226 */       int nrow = mosaic.countRows();
/* 227 */       int ncol = mosaic.countColumns();
/* 228 */       for (int irow = 0; irow < nrow; irow++) {
/* 229 */         if (irow != jrow)
/* 230 */           drawZoom(mosaic.getTile(irow, jcol), x, y, true, false); 
/*     */       } 
/* 232 */       for (int icol = 0; icol < ncol; icol++) {
/* 233 */         if (icol != jcol)
/* 234 */           drawZoom(mosaic.getTile(jrow, icol), x, y, false, true); 
/*     */       } 
/* 236 */       drawZoom(mosaic.getTileAxisTop(jcol), x, y, true, false);
/* 237 */       drawZoom(mosaic.getTileAxisLeft(jrow), x, y, false, true);
/* 238 */       drawZoom(mosaic.getTileAxisBottom(jcol), x, y, true, false);
/* 239 */       drawZoom(mosaic.getTileAxisRight(jrow), x, y, false, true);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 246 */       drawRect(tile, x, y, bx, by);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawZoom(TileAxis axis, int x, int y, boolean bx, boolean by) {
/* 251 */     if (axis == null) {
/*     */       return;
/*     */     }
/*     */     
/* 255 */     if (axis == this._axis) {
/*     */ 
/*     */       
/* 258 */       x = Math.max(0, Math.min(axis.getWidth() - 1, x));
/* 259 */       y = Math.max(0, Math.min(axis.getHeight() - 1, y));
/*     */ 
/*     */       
/* 262 */       drawRect(axis, x, y, bx, by);
/*     */ 
/*     */       
/* 265 */       Mosaic mosaic = axis.getMosaic();
/* 266 */       if (axis.isHorizontal()) {
/* 267 */         int jcol = axis.getIndex();
/* 268 */         int nrow = mosaic.countRows();
/* 269 */         for (int irow = 0; irow < nrow; irow++)
/* 270 */           drawZoom(mosaic.getTile(irow, jcol), x, y, true, false); 
/* 271 */         if (axis.isTop()) {
/* 272 */           drawZoom(mosaic.getTileAxisBottom(jcol), x, y, true, false);
/*     */         } else {
/* 274 */           drawZoom(mosaic.getTileAxisTop(jcol), x, y, true, false);
/*     */         } 
/*     */       } else {
/* 277 */         int jrow = axis.getIndex();
/* 278 */         int ncol = mosaic.countColumns();
/* 279 */         for (int icol = 0; icol < ncol; icol++)
/* 280 */           drawZoom(mosaic.getTile(jrow, icol), x, y, false, true); 
/* 281 */         if (axis.isLeft()) {
/* 282 */           drawZoom(mosaic.getTileAxisRight(jrow), x, y, false, true);
/*     */         } else {
/* 284 */           drawZoom(mosaic.getTileAxisLeft(jrow), x, y, false, true);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 293 */       drawRect(axis, x, y, bx, by);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawRect(JComponent c, int x, int y, boolean bx, boolean by) {
/* 298 */     this._xdraw = x;
/* 299 */     this._ydraw = y;
/* 300 */     int xmin = bx ? Math.min(this._xbegin, this._xdraw) : -1;
/* 301 */     int xmax = bx ? Math.max(this._xbegin, this._xdraw) : c.getWidth();
/* 302 */     int ymin = by ? Math.min(this._ybegin, this._ydraw) : -1;
/* 303 */     int ymax = by ? Math.max(this._ybegin, this._ydraw) : c.getHeight();
/* 304 */     Graphics g = c.getGraphics();
/* 305 */     g.setColor(Color.RED);
/* 306 */     g.setXORMode(c.getBackground());
/* 307 */     g.drawRect(xmin, ymin, xmax - xmin, ymax - ymin);
/* 308 */     g.dispose();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/TileZoomMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */