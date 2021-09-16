/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SequenceView
/*     */   extends TiledView
/*     */ {
/*     */   Sampling _sx;
/*     */   float[] _f;
/*     */   private Color _color;
/*     */   private Zero _zero;
/*     */   
/*     */   public enum Zero
/*     */   {
/*  56 */     NORMAL, ALWAYS, MIDDLE;
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
/*     */   public SequenceView(float[] f)
/*     */   {
/* 200 */     this._color = null;
/* 201 */     this._zero = Zero.ALWAYS; set(f); } public SequenceView(Sampling sx, float[] f) { this._color = null; this._zero = Zero.ALWAYS; set(sx, f); }
/*     */   public void set(float[] f) { set(new Sampling(f.length), f); }
/*     */   public void set(Sampling sx, float[] f) { Check.argument((sx.getCount() == f.length), "sx count equals length of f"); this._sx = sx;
/*     */     this._f = ArrayMath.copy(f);
/*     */     updateBestProjectors();
/*     */     repaint(); }
/* 207 */   public Sampling getSampling() { return this._sx; } private void updateBestProjectors() { double nx = this._sx.getCount();
/* 208 */     double xf = this._sx.getFirst();
/* 209 */     double xl = this._sx.getLast();
/* 210 */     double xmin = ArrayMath.min(xf, xl);
/* 211 */     double xmax = ArrayMath.max(xf, xl);
/* 212 */     if (xmin == xmax) {
/* 213 */       double tiny = ArrayMath.max(1.0D, ArrayMath.ulp(1.0F) * ArrayMath.abs(xmin));
/* 214 */       xmin -= tiny;
/* 215 */       xmax += tiny;
/*     */     } 
/*     */ 
/*     */     
/* 219 */     double fmin = this._f[0];
/* 220 */     double fmax = this._f[0];
/* 221 */     for (int ix = 0; ix < nx; ix++) {
/* 222 */       if (this._f[ix] < fmin)
/* 223 */         fmin = this._f[ix]; 
/* 224 */       if (this._f[ix] > fmax) {
/* 225 */         fmax = this._f[ix];
/*     */       }
/*     */     } 
/*     */     
/* 229 */     if (this._zero == Zero.ALWAYS) {
/* 230 */       fmin = ArrayMath.min(0.0D, fmin);
/* 231 */       fmax = ArrayMath.max(0.0D, fmax);
/* 232 */     } else if (this._zero == Zero.MIDDLE) {
/* 233 */       fmax = ArrayMath.max(ArrayMath.abs(fmin), ArrayMath.abs(fmax));
/* 234 */       fmin = -fmax;
/*     */     } 
/* 236 */     if (fmin == fmax) {
/* 237 */       double tiny = ArrayMath.max(1.0D, ArrayMath.ulp(1.0F) * ArrayMath.abs(fmin));
/* 238 */       fmin -= tiny;
/* 239 */       fmax += tiny;
/*     */     } 
/*     */ 
/*     */     
/* 243 */     double rbx = ballRadiusX();
/* 244 */     double rby = ballRadiusY();
/* 245 */     Projector bhp = new Projector(xmin, xmax, rbx, 1.0D - rbx);
/* 246 */     Projector bvp = new Projector(fmax, fmin, rby, 1.0D - rby);
/* 247 */     setBestProjectors(bhp, bvp); }
/*     */   public float[] getFunction() { return ArrayMath.copy(this._f); }
/*     */   public void setZero(Zero zero) { if (this._zero != zero) { this._zero = zero; updateBestProjectors(); repaint(); }  }
/*     */   public void setColor(Color color) { if (!equalColors(this._color, color)) { this._color = color; repaint(); }  }
/* 251 */   public void paint(Graphics2D g2d) { g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); int nx = this._sx.getCount(); double dx = this._sx.getDelta(); double fx = this._sx.getFirst(); double lx = this._sx.getLast(); Projector bhp = getBestHorizontalProjector(); Projector bvp = getBestVerticalProjector(); Projector hp = getHorizontalProjector(); Projector vp = getVerticalProjector(); Transcaler ts = getTranscaler(); double rbx = ballRadiusX() * hp.getScaleRatio(bhp); double rby = ballRadiusY() * vp.getScaleRatio(bvp); int rx = ts.width(rbx); int ry = ts.height(rby); int rb = ArrayMath.max(0, ArrayMath.min(rx, ry) - 1); if (this._color != null) g2d.setColor(this._color);  int xf = ts.x(hp.u(fx)); int xl = ts.x(hp.u(lx)); int x1 = ArrayMath.min(xf, xl) - rb; int x2 = ArrayMath.max(xf, xl) + rb; int y0 = ts.y(vp.u(0.0D)); g2d.drawLine(x1, y0, x2, y0); for (int ix = 0; ix < nx; ix++) { double xi = fx + ix * dx; double fi = this._f[ix]; int x = ts.x(hp.u(xi)); int y = ts.y(vp.u(fi)); g2d.drawLine(x, y0, x, y); g2d.fillOval(x - rb, y - rb, 1 + 2 * rb, 1 + 2 * rb); }  } private double ballRadiusX() { double nx = this._sx.getCount();
/* 252 */     return 0.9D / 2.0D * nx; }
/*     */ 
/*     */   
/*     */   private double ballRadiusY() {
/* 256 */     return 0.04D;
/*     */   }
/*     */   
/*     */   private boolean equalColors(Color ca, Color cb) {
/* 260 */     return (ca == null) ? ((cb == null)) : ca.equals(cb);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/SequenceView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */