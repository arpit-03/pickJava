/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.util.AxisTics;
/*     */ import edu.mines.jtk.util.LogAxisTics;
/*     */ import edu.mines.jtk.util.MathPlus;
/*     */ import edu.mines.jtk.util.StringUtil;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.LineMetrics;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileAxis
/*     */   extends IPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int _widthMinimum;
/*     */   private Mosaic _mosaic;
/*     */   private Placement _placement;
/*     */   private boolean _isRotated;
/*     */   private int _index;
/*     */   private String _label;
/*     */   private String _format;
/*     */   private int _xtrack;
/*     */   private int _ytrack;
/*     */   private boolean _tracking;
/*     */   private double _interval;
/*     */   private int _ticLabelWidth;
/*     */   private int _ticLabelHeight;
/*     */   private AxisTics _axisTics;
/*     */   private boolean _revalidatePending;
/*     */   
/*     */   public enum Placement
/*     */   {
/*  49 */     TOP, LEFT, BOTTOM, RIGHT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mosaic getMosaic() {
/*  57 */     return this._mosaic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/*  65 */     return this._index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Placement getPlacement() {
/*  73 */     return this._placement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tile getTile() {
/*  81 */     if (isTop())
/*  82 */       return this._mosaic.getTile(0, this._index); 
/*  83 */     if (isLeft())
/*  84 */       return this._mosaic.getTile(this._index, 0); 
/*  85 */     if (isBottom()) {
/*  86 */       int irow = this._mosaic.countRows() - 1;
/*  87 */       return this._mosaic.getTile(irow, this._index);
/*  88 */     }  if (isRight()) {
/*  89 */       int icol = this._mosaic.countColumns() - 1;
/*  90 */       return this._mosaic.getTile(this._index, icol);
/*     */     } 
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTop() {
/* 101 */     return (this._placement == Placement.TOP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLeft() {
/* 109 */     return (this._placement == Placement.LEFT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBottom() {
/* 117 */     return (this._placement == Placement.BOTTOM);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRight() {
/* 125 */     return (this._placement == Placement.RIGHT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHorizontal() {
/* 134 */     return (this._placement == Placement.TOP || this._placement == Placement.BOTTOM);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVertical() {
/* 143 */     return (this._placement == Placement.LEFT || this._placement == Placement.RIGHT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVerticalRotated() {
/* 152 */     return (isVertical() && this._isRotated);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInterval(double interval) {
/* 162 */     this._interval = interval;
/* 163 */     if (updateAxisTics())
/* 164 */       revalidate(); 
/* 165 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLabel(String label) {
/* 173 */     this._label = label;
/* 174 */     if (updateAxisTics())
/* 175 */       revalidate(); 
/* 176 */     repaint();
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
/* 187 */     this._format = format;
/* 188 */     if (updateAxisTics())
/* 189 */       revalidate(); 
/* 190 */     repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 195 */     super.setFont(font);
/* 196 */     if (updateAxisTics())
/* 197 */       revalidate(); 
/* 198 */     repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBounds(int x, int y, int width, int height) {
/* 203 */     super.setBounds(x, y, width, height);
/* 204 */     if (updateAxisTics())
/* 205 */       revalidateLater(); 
/* 206 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVerticalAxisRotated(boolean rotated) {
/* 216 */     this._isRotated = rotated;
/* 217 */     if (updateAxisTics())
/* 218 */       revalidate(); 
/* 219 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisTics getAxisTics() {
/* 227 */     return this._axisTics;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintToRect(Graphics2D g2d, int x, int y, int w, int h) {
/* 233 */     if (this._axisTics == null) {
/*     */       return;
/*     */     }
/*     */     
/* 237 */     Tile tile = getTile();
/* 238 */     if (tile == null) {
/*     */       return;
/*     */     }
/*     */     
/* 242 */     g2d = createGraphics(g2d, x, y, w, h);
/* 243 */     g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*     */ 
/*     */     
/* 246 */     g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 253 */     Projector p = isHorizontal() ? tile.getHorizontalProjector() : tile.getVerticalProjector();
/* 254 */     Transcaler t = tile.getTranscaler(w, h);
/*     */ 
/*     */     
/* 257 */     Font font = g2d.getFont();
/* 258 */     FontMetrics fm = g2d.getFontMetrics();
/* 259 */     FontRenderContext frc = g2d.getFontRenderContext();
/* 260 */     LineMetrics lm = font.getLineMetrics("0.123456789", frc);
/* 261 */     int fh = Math.round(lm.getHeight());
/* 262 */     int fa = Math.round(lm.getAscent());
/* 263 */     int fd = Math.round(lm.getDescent());
/* 264 */     int fl = Math.round(lm.getLeading());
/*     */ 
/*     */     
/* 267 */     int tl = fa / 3;
/*     */ 
/*     */     
/* 270 */     boolean isHorizontal = isHorizontal();
/* 271 */     boolean isTop = isTop();
/* 272 */     boolean isLeft = isLeft();
/* 273 */     boolean isVerticalRotated = isVerticalRotated();
/* 274 */     boolean isLogScale = p.getScale().isLog();
/*     */ 
/*     */     
/* 277 */     int nticMajor = this._axisTics.getCountMajor();
/* 278 */     double dticMajor = this._axisTics.getDeltaMajor();
/* 279 */     double fticMajor = this._axisTics.getFirstMajor();
/* 280 */     int nticMinor = this._axisTics.getCountMinor();
/* 281 */     double dticMinor = this._axisTics.getDeltaMinor();
/* 282 */     double fticMinor = this._axisTics.getFirstMinor();
/* 283 */     int mtic = this._axisTics.getMultiple();
/*     */ 
/*     */ 
/*     */     
/* 287 */     int ktic = isLogScale ? (((LogAxisTics)this._axisTics).getFirstMinorSkip() - 1) : (int)Math.round((fticMajor - fticMinor) / dticMinor);
/* 288 */     for (int itic = 0; itic < nticMinor; itic++) {
/* 289 */       if (itic == ktic) {
/* 290 */         ktic += mtic;
/*     */       } else {
/* 292 */         double vtic = 0.0D;
/* 293 */         if (isLogScale) {
/* 294 */           vtic = (itic + fticMinor * MathPlus.pow(10.0D, -Math.floor(MathPlus.log10(fticMinor))) - 1.0D) % 9.0D + 1.0D;
/* 295 */           vtic *= MathPlus.pow(10.0D, ((ktic - 1) / mtic) + Math.floor(MathPlus.log10(fticMinor)));
/*     */         } else {
/*     */           
/* 298 */           vtic = fticMinor + itic * dticMinor;
/* 299 */         }  double utic = p.u(vtic);
/* 300 */         if (isHorizontal) {
/* 301 */           x = t.x(utic);
/* 302 */           if (isTop) {
/* 303 */             g2d.drawLine(x, h - 1, x, h - 1 - tl / 2);
/*     */           } else {
/* 305 */             g2d.drawLine(x, 0, x, tl / 2);
/*     */           } 
/*     */         } else {
/* 308 */           y = t.y(utic);
/* 309 */           if (isLeft) {
/* 310 */             g2d.drawLine(w - 1, y, w - 1 - tl / 2, y);
/*     */           } else {
/* 312 */             g2d.drawLine(0, y, tl / 2, y);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 319 */     int wsmax = 0;
/* 320 */     double tiny = 1.0E-6D * Math.abs(dticMajor);
/* 321 */     for (int i = 0; i < nticMajor; i++) {
/* 322 */       double vtic = isLogScale ? (fticMajor * Math.pow(10.0D, i)) : (fticMajor + i * dticMajor);
/*     */       
/* 324 */       double utic = p.u(vtic);
/* 325 */       if (Math.abs(vtic) < tiny)
/* 326 */         vtic = 0.0D; 
/* 327 */       String stic = formatTic(vtic);
/* 328 */       if (isHorizontal) {
/* 329 */         x = t.x(utic);
/* 330 */         if (isTop) {
/* 331 */           y = h - 1;
/* 332 */           g2d.drawLine(x, y, x, y - tl);
/* 333 */           y -= tl + fd;
/*     */         } else {
/* 335 */           y = 0;
/* 336 */           g2d.drawLine(x, y, x, y + tl);
/* 337 */           y += tl + fa;
/*     */         } 
/* 339 */         int ws = fm.stringWidth(stic);
/* 340 */         int xs = Math.max(0, Math.min(w - ws, x - ws / 2));
/* 341 */         int ys = y;
/* 342 */         g2d.drawString(stic, xs, ys);
/*     */       }
/* 344 */       else if (isVerticalRotated) {
/* 345 */         y = t.y(utic);
/* 346 */         if (isLeft) {
/* 347 */           x = w - 1;
/* 348 */           g2d.drawLine(x, y, x - tl, y);
/* 349 */           x -= tl + fd;
/*     */         } else {
/* 351 */           x = 0;
/* 352 */           g2d.drawLine(x, y, x + tl, y);
/* 353 */           x += tl + fd;
/*     */         } 
/* 355 */         int ws = fm.stringWidth(stic);
/* 356 */         int xs = x;
/* 357 */         int ys = Math.max(ws, Math.min(h, y + ws / 2));
/* 358 */         g2d.translate(xs, ys);
/* 359 */         g2d.rotate(-1.5707963267948966D);
/* 360 */         g2d.drawString(stic, 0, 0);
/* 361 */         g2d.rotate(1.5707963267948966D);
/* 362 */         g2d.translate(-xs, -ys);
/*     */       } else {
/* 364 */         y = t.y(utic);
/* 365 */         if (isLeft) {
/* 366 */           x = w - 1;
/* 367 */           g2d.drawLine(x, y, x - tl, y);
/* 368 */           x -= tl + fd;
/*     */         } else {
/* 370 */           x = 0;
/* 371 */           g2d.drawLine(x, y, x + tl, y);
/* 372 */           x += tl + fd;
/*     */         } 
/* 374 */         int ws = fm.stringWidth(stic);
/* 375 */         if (ws > wsmax)
/* 376 */           wsmax = ws; 
/* 377 */         int xs = isLeft ? (x - ws) : x;
/* 378 */         int ys = Math.max(fa, Math.min(h - 1, y + (int)Math.round(0.3D * fa)));
/* 379 */         g2d.drawString(stic, xs, ys);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 385 */     if (this._label != null) {
/* 386 */       if (isHorizontal) {
/* 387 */         int wl = fm.stringWidth(this._label);
/* 388 */         int xl = Math.max(0, Math.min(w - wl, (w - wl) / 2));
/* 389 */         int yl = isTop ? (h - 1 - tl - fh - fd) : (tl + fh + fa);
/* 390 */         g2d.drawString(this._label, xl, yl);
/*     */       } else {
/* 392 */         int wl = fm.stringWidth(this._label);
/*     */ 
/*     */ 
/*     */         
/* 396 */         int xl = isLeft ? (fa + fd) : (w - 1 - fd - fd - fl);
/* 397 */         int yl = Math.max(wl, Math.min(h, (h + wl) / 2));
/* 398 */         g2d.translate(xl, yl);
/* 399 */         g2d.rotate(-1.5707963267948966D);
/* 400 */         g2d.drawString(this._label, 0, 0);
/* 401 */         g2d.rotate(1.5707963267948966D);
/* 402 */         g2d.translate(-xl, -yl);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 407 */     g2d.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintComponent(Graphics g) {
/* 414 */     super.paintComponent(g);
/* 415 */     endTracking();
/* 416 */     paintToRect((Graphics2D)g, 0, 0, getWidth(), getHeight());
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
/*     */   int getWidthMinimum() {
/*     */     int width;
/*     */     if (this._widthMinimum != 0) {
/*     */       return this._widthMinimum;
/*     */     }
/*     */     FontMetrics fm = getFontMetrics(getFont());
/*     */     if (this._ticLabelWidth == 0 && updateAxisTics()) {
/*     */       revalidate();
/*     */     }
/*     */     int ticLabelWidth = this._ticLabelWidth;
/*     */     if (ticLabelWidth == 0) {
/*     */       ticLabelWidth = maxTicLabelWidth(fm);
/*     */     }
/*     */     if (isVertical()) {
/*     */       if (isVerticalRotated()) {
/*     */         width = fm.getAscent() + fm.getHeight();
/*     */       } else {
/*     */         width = ticLabelWidth + fm.getHeight();
/*     */       } 
/*     */       if (this._label != null) {
/*     */         width += fm.getHeight();
/*     */       }
/*     */     } else {
/*     */       width = 50;
/*     */       if (this._label != null) {
/*     */         width = Math.max(width, fm.stringWidth(this._label));
/*     */       }
/*     */     } 
/*     */     return width;
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
/*     */   void setWidthMinimum(int widthMinimum) {
/*     */     this._widthMinimum = widthMinimum;
/*     */     revalidate();
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
/*     */   int getHeightMinimum() {
/*     */     int height;
/*     */     FontMetrics fm = getFontMetrics(getFont());
/*     */     if (isHorizontal()) {
/*     */       height = fm.getHeight() + fm.getAscent();
/*     */       if (this._label != null) {
/*     */         height += fm.getHeight();
/*     */       }
/*     */     } else {
/*     */       height = 50;
/*     */       if (this._label != null) {
/*     */         height = Math.max(height, fm.stringWidth(this._label));
/*     */       }
/*     */     } 
/*     */     return height;
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
/*     */   boolean updateAxisTics() {
/*     */     double u0, u1, du;
/*     */     Tile tile = getTile();
/*     */     if (tile == null) {
/*     */       return false;
/*     */     }
/*     */     int w = getWidth();
/*     */     int h = getHeight();
/*     */     if (w == 0 || h == 0) {
/*     */       return false;
/*     */     }
/*     */     Projector p = isHorizontal() ? tile.getHorizontalProjector() : tile.getVerticalProjector();
/*     */     Transcaler t = tile.getTranscaler(w, h);
/*     */     Font font = getFont();
/*     */     FontRenderContext frc = new FontRenderContext(null, true, false);
/*     */     boolean isHorizontal = isHorizontal();
/*     */     boolean isVerticalRotated = isVerticalRotated();
/*     */     double umin = p.u0();
/*     */     double umax = p.u1();
/*     */     double vmin = Math.min(p.v0(), p.v1());
/*     */     double vmax = Math.max(p.v0(), p.v1());
/*     */     if (isHorizontal) {
/*     */       u0 = Math.max(umin, t.x(0));
/*     */       u1 = Math.min(umax, t.x(w - 1));
/*     */       du = Math.min(umax - umin, t.width(w));
/*     */     } else {
/*     */       u0 = Math.max(umin, t.y(0));
/*     */       u1 = Math.min(umax, t.y(h - 1));
/*     */       du = Math.min(umax - umin, t.height(h));
/*     */     } 
/*     */     double v0 = Math.max(vmin, Math.min(vmax, p.v(u0)));
/*     */     double v1 = Math.max(vmin, Math.min(vmax, p.v(u1)));
/*     */     double dv = Math.abs(p.v(u0 + du) - p.v(u0));
/*     */     int ticLabelWidth = 0;
/*     */     int ticLabelHeight = 0;
/*     */     double dtic = 0.0D;
/*     */     int nmax;
/*     */     for (nmax = 20; nmax >= 2; nmax = ntic - 1) {
/*     */       AxisTics at;
/*     */       if (this._interval == 0.0D) {
/*     */         at = new AxisTics(vmin, vmin + dv, nmax);
/*     */       } else {
/*     */         at = new AxisTics(vmin, vmin + dv, this._interval);
/*     */       } 
/*     */       int ntic = at.getCountMajor();
/*     */       dtic = at.getDeltaMajor();
/*     */       double va = Math.ceil(vmin / dtic) * dtic;
/*     */       double vb = Math.floor(vmax / dtic) * dtic;
/*     */       Rectangle2D.Double r = new Rectangle2D.Double();
/*     */       Rectangle2D.union(font.getStringBounds(formatTic(va), frc), r, r);
/*     */       Rectangle2D.union(font.getStringBounds(formatTic(va + dtic), frc), r, r);
/*     */       Rectangle2D.union(font.getStringBounds(formatTic(vb - dtic), frc), r, r);
/*     */       Rectangle2D.union(font.getStringBounds(formatTic(vb), frc), r, r);
/*     */       ticLabelWidth = (int)Math.ceil(r.width);
/*     */       ticLabelHeight = (int)Math.ceil(r.height);
/*     */       if (this._interval != 0.0D) {
/*     */         break;
/*     */       }
/*     */       if (isHorizontal ? ((ticLabelWidth * ntic) < 0.7D * w) : (isVerticalRotated ? ((ticLabelWidth * ntic) < 0.7D * h) : ((ticLabelHeight * ntic) < 0.6D * h))) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     if (p.getScale() == AxisScale.LINEAR) {
/*     */       this._axisTics = new AxisTics(v0, v1, dtic);
/*     */     } else if (p.getScale() == AxisScale.LOG10) {
/*     */       double v0a = Math.min(v0, v1);
/*     */       double v1a = Math.max(v0, v1);
/*     */       this._axisTics = (AxisTics)new LogAxisTics(v0a, v1a);
/*     */     } 
/*     */     if (this._ticLabelWidth != ticLabelWidth || this._ticLabelHeight != ticLabelHeight) {
/*     */       this._ticLabelWidth = ticLabelWidth;
/*     */       this._ticLabelHeight = ticLabelHeight;
/*     */       return true;
/*     */     } 
/*     */     return false;
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
/*     */   TileAxis(Mosaic mosaic, Placement placement, int index) {
/* 709 */     this._format = "%1.4G";
/*     */     this._mosaic = mosaic;
/*     */     this._placement = placement;
/*     */     this._index = index;
/*     */     mosaic.add(this);
/*     */   }
/*     */ 
/*     */   
/*     */   void beginTracking(int x, int y) {
/*     */     if (!this._tracking) {
/*     */       this._xtrack = x;
/*     */       this._ytrack = y;
/*     */       this._tracking = true;
/*     */       paintTrack(x, y);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void revalidateLater()
/*     */   {
/* 728 */     if (!this._revalidatePending) {
/* 729 */       SwingUtilities.invokeLater(new Runnable() {
/*     */             public void run() {
/* 731 */               TileAxis.this.revalidate();
/* 732 */               TileAxis.this._revalidatePending = false;
/*     */             }
/*     */           });
/* 735 */       this._revalidatePending = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int maxTicLabelWidth(FontMetrics fm) {
/* 744 */     double vtic = -0.0123456789D;
/* 745 */     return fm.stringWidth(formatTic(vtic));
/*     */   } void duringTracking(int x, int y) { if (this._tracking)
/*     */       paintTrack(this._xtrack, this._ytrack);  this._xtrack = x;
/*     */     this._ytrack = y;
/*     */     this._tracking = true;
/* 750 */     paintTrack(this._xtrack, this._ytrack); } private String formatTic(double v) { String s = String.format(this._format, new Object[] { Double.valueOf(v) });
/* 751 */     s = StringUtil.removeTrailingZeros(s);
/* 752 */     return s; }
/*     */ 
/*     */   
/*     */   void endTracking() {
/*     */     if (this._tracking) {
/*     */       paintTrack(this._xtrack, this._ytrack);
/*     */       this._tracking = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void paintTrack(int x, int y) {
/*     */     int w = getWidth();
/*     */     int h = getHeight();
/*     */     Graphics g = getGraphics();
/*     */     g.setColor(Color.BLUE);
/*     */     g.setXORMode(getBackground());
/*     */     if (isHorizontal()) {
/*     */       g.drawLine(x, -1, x, h);
/*     */     } else {
/*     */       g.drawLine(-1, y, w, y);
/*     */     } 
/*     */     g.dispose();
/*     */   }
/*     */   
/*     */   static boolean revalidatePending(Container parent) {
/*     */     int n = parent.getComponentCount();
/*     */     for (int i = 0; i < n; i++) {
/*     */       Component child = parent.getComponent(i);
/*     */       if (child instanceof TileAxis) {
/*     */         TileAxis ta = (TileAxis)child;
/*     */         if (ta._revalidatePending)
/*     */           return true; 
/*     */       } else if (child instanceof Container && revalidatePending((Container)child)) {
/*     */         return true;
/*     */       } 
/*     */     } 
/*     */     return false;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/TileAxis.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */