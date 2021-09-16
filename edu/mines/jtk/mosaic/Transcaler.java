/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.util.Check;
/*     */ import edu.mines.jtk.util.MathPlus;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Transcaler
/*     */ {
/*     */   private static final double DMIN = -32768.0D;
/*     */   private static final double DMAX = 32767.0D;
/*     */   private static final double DMINW = -65535.0D;
/*     */   private static final double DMAXW = 65535.0D;
/*     */   private double _x1u;
/*     */   private double _y1u;
/*     */   private double _x2u;
/*     */   private double _y2u;
/*     */   private int _x1d;
/*     */   private int _y1d;
/*     */   private int _x2d;
/*     */   private int _y2d;
/*     */   private double _xushift;
/*     */   private double _xuscale;
/*     */   private double _yushift;
/*     */   private double _yuscale;
/*     */   private double _xdshift;
/*     */   private double _xdscale;
/*     */   private double _ydshift;
/*     */   private double _ydscale;
/*     */   private AxisScale _xpScale;
/*     */   private AxisScale _ypScale;
/*     */   
/*     */   public Transcaler() {
/*  53 */     this(0.0D, 0.0D, 1.0D, 1.0D, 0, 0, 1, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transcaler(int width, int height) {
/*  64 */     this(0.0D, 0.0D, 1.0D, 1.0D, 0, 0, width - 1, height - 1);
/*  65 */     Check.argument((width > 0), "width>0");
/*  66 */     Check.argument((height > 0), "height>0");
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
/*     */   public void setMapping(double x1u, double y1u, double x2u, double y2u, int x1d, int y1d, int x2d, int y2d) {
/*     */     this._x1u = x1u;
/*     */     this._x2u = x2u;
/*     */     this._y1u = y1u;
/*     */     this._y2u = y2u;
/*     */     this._x1d = x1d;
/*     */     this._x2d = x2d;
/*     */     this._y1d = y1d;
/*     */     this._y2d = y2d;
/*     */     computeShiftAndScale();
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
/*     */   public void setMapping(double x1u, double y1u, double x2u, double y2u) {
/*     */     this._x1u = x1u;
/*     */     this._x2u = x2u;
/*     */     this._y1u = y1u;
/*     */     this._y2u = y2u;
/*     */     computeShiftAndScale();
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
/*     */   public void setMapping(int x1d, int y1d, int x2d, int y2d) {
/*     */     this._x1d = x1d;
/*     */     this._x2d = x2d;
/*     */     this._y1d = y1d;
/*     */     this._y2d = y2d;
/*     */     computeShiftAndScale();
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
/*     */   public void setMapping(int width, int height) {
/*     */     Check.argument((width > 0), "width>0");
/*     */     Check.argument((height > 0), "height>0");
/*     */     setMapping(0, 0, width - 1, height - 1);
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
/*     */   public Transcaler combineWith(Projector xp, Projector yp) {
/*     */     AxisScale xsc = xp.getScale();
/*     */     AxisScale ysc = yp.getScale();
/*     */     double x1v = (xsc == AxisScale.LOG10) ? MathPlus.log10(xp.v(this._x1u)) : xp.v(this._x1u);
/*     */     double x2v = (xsc == AxisScale.LOG10) ? MathPlus.log10(xp.v(this._x2u)) : xp.v(this._x2u);
/*     */     double y1v = (ysc == AxisScale.LOG10) ? MathPlus.log10(yp.v(this._y1u)) : yp.v(this._y1u);
/*     */     double y2v = (ysc == AxisScale.LOG10) ? MathPlus.log10(yp.v(this._y2u)) : yp.v(this._y2u);
/*     */     return new Transcaler(x1v, y1v, x2v, y2v, this._x1d, this._y1d, this._x2d, this._y2d, xsc, ysc);
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
/*     */   public int x(double xu) {
/*     */     if (this._xpScale == AxisScale.LOG10) {
/*     */       xu = MathPlus.log10(xu);
/*     */     }
/*     */     double xd = this._xushift + this._xuscale * xu;
/*     */     if (xd < -32768.0D) {
/*     */       xd = -32768.0D;
/*     */     } else if (xd > 32767.0D) {
/*     */       xd = 32767.0D;
/*     */     } 
/*     */     return (int)xd;
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
/*     */   public Transcaler(double x1u, double y1u, double x2u, double y2u, int x1d, int y1d, int x2d, int y2d) {
/* 274 */     this._xpScale = AxisScale.LINEAR;
/* 275 */     this._ypScale = AxisScale.LINEAR;
/*     */     setMapping(x1u, y1u, x2u, y2u, x1d, y1d, x2d, y2d);
/*     */   } private void computeShiftAndScale() {
/* 278 */     if (this._x1u != this._x2u) {
/* 279 */       this._xuscale = (this._x2d - this._x1d) / (this._x2u - this._x1u);
/* 280 */       this._xushift = this._x1d - this._x1u * this._xuscale + 0.5D;
/*     */     } else {
/* 282 */       this._xushift = 0.5D * (this._x1d + this._x2d) + 0.5D;
/* 283 */       this._xuscale = 0.0D;
/*     */     } 
/* 285 */     if (this._x1d != this._x2d) {
/* 286 */       this._xdscale = (this._x2u - this._x1u) / (this._x2d - this._x1d);
/* 287 */       this._xdshift = this._x1u - this._x1d * this._xdscale;
/*     */     } else {
/* 289 */       this._xdshift = 0.5D * (this._x1u + this._x2u);
/* 290 */       this._xdscale = 0.0D;
/*     */     } 
/* 292 */     if (this._y1u != this._y2u) {
/* 293 */       this._yuscale = (this._y2d - this._y1d) / (this._y2u - this._y1u);
/* 294 */       this._yushift = this._y1d - this._y1u * this._yuscale + 0.5D;
/*     */     } else {
/* 296 */       this._yushift = 0.5D * (this._y1d + this._y2d) + 0.5D;
/* 297 */       this._yuscale = 0.0D;
/*     */     } 
/* 299 */     if (this._y1d != this._y2d) {
/* 300 */       this._ydscale = (this._y2u - this._y1u) / (this._y2d - this._y1d);
/* 301 */       this._ydshift = this._y1u - this._y1d * this._ydscale;
/*     */     } else {
/* 303 */       this._ydshift = 0.5D * (this._y1u + this._y2u);
/* 304 */       this._ydscale = 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int y(double yu) {
/*     */     if (this._ypScale == AxisScale.LOG10) {
/*     */       yu = MathPlus.log10(yu);
/*     */     }
/*     */     double yd = this._yushift + this._yuscale * yu;
/*     */     if (yd < -32768.0D) {
/*     */       yd = -32768.0D;
/*     */     } else if (yd > 32767.0D) {
/*     */       yd = 32767.0D;
/*     */     } 
/*     */     return (int)yd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Transcaler(double x1u, double y1u, double x2u, double y2u, int x1d, int y1d, int x2d, int y2d, AxisScale hscale, AxisScale vscale) {
/*     */     this._xpScale = AxisScale.LINEAR;
/*     */     this._ypScale = AxisScale.LINEAR;
/* 330 */     this._xpScale = hscale;
/* 331 */     this._ypScale = vscale;
/* 332 */     setMapping(x1u, y1u, x2u, y2u, x1d, y1d, x2d, y2d);
/*     */   }
/*     */   
/*     */   public int width(double wu) {
/*     */     double wd = this._xuscale * wu;
/*     */     if (wd < -65535.0D) {
/*     */       wd = -65535.0D;
/*     */     } else if (wd > 65535.0D) {
/*     */       wd = 65535.0D;
/*     */     } 
/*     */     return (int)(wd + 1.5D);
/*     */   }
/*     */   
/*     */   public int height(double hu) {
/*     */     double hd = this._yuscale * hu;
/*     */     if (hd < -65535.0D) {
/*     */       hd = -65535.0D;
/*     */     } else if (hd > 65535.0D) {
/*     */       hd = 65535.0D;
/*     */     } 
/*     */     return (int)(hd + 1.5D);
/*     */   }
/*     */   
/*     */   public double x(int xd) {
/*     */     return this._xdshift + this._xdscale * xd;
/*     */   }
/*     */   
/*     */   public double y(int yd) {
/*     */     return this._ydshift + this._ydscale * yd;
/*     */   }
/*     */   
/*     */   public double width(int wd) {
/*     */     return this._xdscale * (wd - 1);
/*     */   }
/*     */   
/*     */   public double height(int hd) {
/*     */     return this._ydscale * (hd - 1);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/Transcaler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */