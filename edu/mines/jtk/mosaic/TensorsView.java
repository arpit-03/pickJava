/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.dsp.EigenTensors2;
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.util.ArrayMath;
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
/*     */ public class TensorsView
/*     */   extends TiledView
/*     */ {
/*     */   private Sampling _s1;
/*     */   private Sampling _s2;
/*     */   private EigenTensors2 _et;
/*     */   private Sampling _e1;
/*     */   private Sampling _e2;
/*     */   private int _ne;
/*     */   private int _np;
/*     */   private float[][] _x1;
/*     */   private float[][] _x2;
/*     */   private Orientation _orientation;
/*     */   private float _lineWidth;
/*     */   private Color _lineColor;
/*     */   private double _scale;
/*     */   
/*     */   public enum Orientation
/*     */   {
/*  39 */     X1RIGHT_X2UP,
/*  40 */     X1DOWN_X2RIGHT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TensorsView(EigenTensors2 et) {
/*  48 */     this(new Sampling(et.getN1(), 1.0D, 0.0D), new Sampling(et
/*  49 */           .getN2(), 1.0D, 0.0D), et);
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
/*     */   public void set(EigenTensors2 et) {
/*     */     this._et = et;
/*     */     updateTensorEllipses();
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
/*     */   public void setOrientation(Orientation orientation) {
/*     */     if (this._orientation != orientation) {
/*     */       this._orientation = orientation;
/*     */       updateBestProjectors();
/*     */       repaint();
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
/*     */   public Orientation getOrientation() {
/*     */     return this._orientation;
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
/*     */   public void setEllipsesDisplayed(int ne) {
/*     */     this._ne = ne;
/*     */     updateTensorEllipses();
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
/*     */   public void setEllipsesDisplayed(Sampling e1, Sampling e2) {
/*     */     this._e1 = e1;
/*     */     this._e2 = e2;
/*     */     updateTensorEllipses();
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
/*     */   public void setScale(double scale) {
/*     */     this._scale = scale;
/*     */     updateTensorEllipses();
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
/*     */   public void setLineWidth(float width) {
/*     */     if (this._lineWidth != width) {
/*     */       this._lineWidth = width;
/*     */       updateBestProjectors();
/*     */       repaint();
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
/*     */   public void setLineColor(Color color) {
/*     */     if (!equalColors(this._lineColor, color)) {
/*     */       this._lineColor = color;
/*     */       repaint();
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
/*     */   public void paint(Graphics2D g2d) {
/*     */     g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*     */     Projector hp = getHorizontalProjector();
/*     */     Projector vp = getVerticalProjector();
/*     */     Transcaler ts = getTranscaler();
/*     */     ts = ts.combineWith(hp, vp);
/*     */     float lineWidth = 1.0F;
/*     */     Stroke stroke = g2d.getStroke();
/*     */     if (stroke instanceof BasicStroke) {
/*     */       BasicStroke basicStroke = (BasicStroke)stroke;
/*     */       lineWidth = basicStroke.getLineWidth();
/*     */     } 
/*     */     Graphics2D gpoly = (Graphics2D)g2d.create();
/*     */     float width = lineWidth;
/*     */     if (this._lineWidth != 0.0F) {
/*     */       width *= this._lineWidth;
/*     */     }
/*     */     BasicStroke bs = new BasicStroke(width);
/*     */     gpoly.setStroke(bs);
/*     */     if (this._lineColor != null) {
/*     */       gpoly.setColor(this._lineColor);
/*     */     }
/*     */     int ne = this._x1.length;
/*     */     int np = (this._x1[0]).length;
/*     */     int[] xp = new int[np];
/*     */     int[] yp = new int[np];
/*     */     float[][] xv = (float[][])null;
/*     */     float[][] yv = (float[][])null;
/*     */     if (this._orientation == Orientation.X1RIGHT_X2UP) {
/*     */       xv = this._x1;
/*     */       yv = this._x2;
/*     */     } else if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/*     */       xv = this._x2;
/*     */       yv = this._x1;
/*     */     } 
/*     */     for (int ie = 0; ie < ne; ie++) {
/*     */       float[] xe = xv[ie];
/*     */       float[] ye = yv[ie];
/*     */       for (int ip = 0; ip < np; ip++) {
/*     */         xp[ip] = ts.x(xe[ip]);
/*     */         yp[ip] = ts.y(ye[ip]);
/*     */       } 
/*     */       gpoly.drawPolygon(xp, yp, np);
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
/*     */   public TensorsView(Sampling s1, Sampling s2, EigenTensors2 et) {
/* 230 */     this._ne = 20;
/* 231 */     this._np = 50;
/*     */ 
/*     */     
/* 234 */     this._orientation = Orientation.X1RIGHT_X2UP;
/* 235 */     this._lineWidth = 0.0F;
/* 236 */     this._lineColor = null;
/* 237 */     this._scale = 1.0D; this._s1 = s1;
/*     */     this._s2 = s2;
/*     */     updateBestProjectors();
/* 240 */     set(et); } private void updateTensorEllipses() { int n1 = this._s1.getCount();
/* 241 */     int n2 = this._s2.getCount();
/* 242 */     double d1 = this._s1.getDelta();
/* 243 */     double d2 = this._s2.getDelta();
/* 244 */     double f1 = this._s1.getFirst();
/* 245 */     double f2 = this._s2.getFirst();
/*     */ 
/*     */     
/* 248 */     Sampling e1 = this._e1;
/* 249 */     Sampling e2 = this._e2;
/* 250 */     if (e1 == null || e2 == null) {
/* 251 */       int ne = ArrayMath.min(this._ne, n1, n2);
/* 252 */       int ns = ArrayMath.max(n1 / ne, n2 / ne);
/* 253 */       int j = (n1 - 1) / ns;
/* 254 */       int k = (n2 - 1) / ns;
/* 255 */       int j1 = (n1 - 1 - (j - 1) * ns) / 2;
/* 256 */       int j2 = (n2 - 1 - (k - 1) * ns) / 2;
/* 257 */       e1 = new Sampling(j, ns * d1, f1 + j1 * d1);
/* 258 */       e2 = new Sampling(k, ns * d2, f2 + j2 * d2);
/*     */     } 
/* 260 */     int m1 = e1.getCount();
/* 261 */     int m2 = e2.getCount();
/* 262 */     int nm = m1 * m2;
/*     */ 
/*     */     
/* 265 */     int np = this._np;
/* 266 */     double dp = 6.283185307179586D / np;
/* 267 */     double fp = 0.0D;
/*     */ 
/*     */     
/* 270 */     float emax = 0.0F;
/* 271 */     float[] a = new float[2];
/* 272 */     for (int i2 = 0; i2 < n2; i2++) {
/* 273 */       for (int i1 = 0; i1 < n1; i1++) {
/* 274 */         this._et.getEigenvalues(i1, i2, a);
/* 275 */         emax = ArrayMath.max(emax, a[0], a[1]);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 281 */     double ratio = ArrayMath.min(e1.getDelta() / d1, e2.getDelta() / d2);
/* 282 */     double r = (emax > 0.0D) ? (0.48D * ratio / ArrayMath.sqrt(emax)) : 0.0D;
/* 283 */     r *= this._scale;
/*     */ 
/*     */     
/* 286 */     this._x1 = new float[nm][np];
/* 287 */     this._x2 = new float[nm][np];
/* 288 */     float[] u = new float[2];
/* 289 */     for (int i = 0, im = 0; i < m2; i++) {
/* 290 */       double x2 = e2.getValue(i);
/* 291 */       int j2 = this._s2.indexOfNearest(x2);
/*     */       
/* 293 */       for (int i1 = 0; i1 < m1; i1++, im++) {
/* 294 */         double x1 = e1.getValue(i1);
/* 295 */         int j1 = this._s1.indexOfNearest(x1);
/*     */         
/* 297 */         this._et.getEigenvalues(j1, j2, a);
/* 298 */         this._et.getEigenvectorU(j1, j2, u);
/* 299 */         double u1 = u[0];
/* 300 */         double u2 = u[1];
/* 301 */         double du = a[0];
/* 302 */         double dv = a[1];
/* 303 */         double ru = r * ArrayMath.sqrt(du);
/* 304 */         double rv = r * ArrayMath.sqrt(dv);
/* 305 */         for (int ip = 0; ip < np; ip++) {
/* 306 */           double p = fp + ip * dp;
/* 307 */           double cosp = ArrayMath.cos(p);
/* 308 */           double sinp = ArrayMath.sin(p);
/* 309 */           this._x1[im][ip] = (float)(x1 + d1 * (ru * cosp * u1 - rv * sinp * u2));
/* 310 */           this._x2[im][ip] = (float)(x2 + d2 * (rv * sinp * u1 + ru * cosp * u2));
/*     */         } 
/*     */       } 
/*     */     }  }
/*     */   
/*     */   private void updateBestProjectors() {
/*     */     double x0, y0, x1, y1, ux0, uy0, ux1, uy1;
/* 317 */     int n1 = this._s1.getCount();
/* 318 */     int n2 = this._s2.getCount();
/* 319 */     double d1 = this._s1.getDelta();
/* 320 */     double d2 = this._s2.getDelta();
/* 321 */     double f1 = this._s1.getFirst();
/* 322 */     double f2 = this._s2.getFirst();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 329 */     if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/* 330 */       x0 = f2;
/* 331 */       x1 = f2 + d2 * (n2 - 1);
/* 332 */       y0 = f1;
/* 333 */       y1 = f1 + d1 * (n1 - 1);
/* 334 */       ux0 = 0.5D / n2;
/* 335 */       ux1 = 1.0D - 0.5D / n2;
/* 336 */       uy0 = 0.5D / n1;
/* 337 */       uy1 = 1.0D - 0.5D / n1;
/*     */     } else {
/* 339 */       x0 = f1;
/* 340 */       x1 = f1 + d1 * (n1 - 1);
/* 341 */       y0 = f2 + d2 * (n2 - 1);
/* 342 */       y1 = f2;
/* 343 */       ux0 = 0.5D / n1;
/* 344 */       ux1 = 1.0D - 0.5D / n1;
/* 345 */       uy0 = 0.5D / n2;
/* 346 */       uy1 = 1.0D - 0.5D / n2;
/*     */     } 
/*     */ 
/*     */     
/* 350 */     Projector bhp = new Projector(x0, x1, ux0, ux1);
/* 351 */     Projector bvp = new Projector(y0, y1, uy0, uy1);
/* 352 */     setBestProjectors(bhp, bvp);
/*     */   }
/*     */   
/*     */   private boolean equalColors(Color ca, Color cb) {
/* 356 */     return (ca == null) ? ((cb == null)) : ca.equals(cb);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/TensorsView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */