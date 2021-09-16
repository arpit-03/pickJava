/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Projector
/*     */ {
/*     */   private double _u0;
/*     */   private double _u1;
/*     */   private double _v0;
/*     */   private double _v1;
/*     */   private double _ushift;
/*     */   private double _uscale;
/*     */   private double _vshift;
/*     */   private double _vscale;
/*     */   private AxisScale _scaleType;
/*     */   
/*     */   public Projector(double v0, double v1) {
/*  77 */     this(v0, v1, 0.0D, 1.0D, AxisScale.LINEAR);
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
/*     */   public Projector(double v0, double v1, AxisScale scale) {
/*  90 */     this(v0, v1, 0.0D, 1.0D, scale);
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
/*     */   public Projector(double v0, double v1, double u0, double u1) {
/* 106 */     this(v0, v1, u0, u1, AxisScale.LINEAR);
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
/*     */   public Projector(double v0, double v1, double u0, double u1, AxisScale s) {
/* 125 */     Check.argument((0.0D <= u0), "0.0 <= u0");
/* 126 */     Check.argument((u0 < u1), "u0 < u1");
/* 127 */     Check.argument((u1 <= 1.0D), "u1 <= 1.0");
/* 128 */     Check.argument((v0 != v1), "v0 != v1");
/* 129 */     setScale(s);
/* 130 */     this._u0 = u0;
/* 131 */     this._u1 = u1;
/* 132 */     this._v0 = v0;
/* 133 */     this._v1 = v1;
/* 134 */     this._scaleType = s;
/* 135 */     computeShiftsAndScales();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Projector(Projector p) {
/* 143 */     this(p._v0, p._v1, p._u0, p._u1, p._scaleType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double u(double v) {
/* 152 */     if (this._scaleType == AxisScale.LINEAR)
/* 153 */       return this._vshift + this._vscale * v; 
/* 154 */     if (this._scaleType == AxisScale.LOG10)
/* 155 */       return this._vshift + this._vscale * ArrayMath.log10(v); 
/* 156 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double v(double u) {
/* 165 */     if (this._scaleType == AxisScale.LINEAR)
/* 166 */       return this._ushift + this._uscale * u; 
/* 167 */     if (this._scaleType == AxisScale.LOG10)
/* 168 */       return ArrayMath.pow(10.0D, this._ushift + this._uscale * u); 
/* 169 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double u0() {
/* 177 */     return this._u0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double u1() {
/* 185 */     return this._u1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double v0() {
/* 193 */     return this._v0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double v1() {
/* 201 */     return this._v1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisScale getScale() {
/* 209 */     return this._scaleType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLinear() {
/* 217 */     return this._scaleType.isLinear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLog() {
/* 225 */     return this._scaleType.isLog();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void merge(Projector p) {
/* 234 */     if (p == null) {
/*     */       return;
/*     */     }
/*     */     
/* 238 */     double u0a = this._u0;
/* 239 */     double u1a = this._u1;
/* 240 */     double v0a = this._v0;
/* 241 */     double v1a = this._v1;
/*     */ 
/*     */     
/* 244 */     double u0b = p._u0;
/* 245 */     double u1b = p._u1;
/* 246 */     double v0b = p._v0;
/* 247 */     double v1b = p._v1;
/*     */ 
/*     */     
/* 250 */     if (MathPlus.signum(v1a - v0a) != MathPlus.signum(v1b - v0b)) {
/* 251 */       u0b = 1.0D - p._u1;
/* 252 */       u1b = 1.0D - p._u0;
/* 253 */       v0b = p._v1;
/* 254 */       v1b = p._v0;
/*     */     } 
/*     */ 
/*     */     
/* 258 */     double vmin = MathPlus.min(MathPlus.min(v0a, v1a), MathPlus.min(v0b, v1b));
/* 259 */     double vmax = MathPlus.max(MathPlus.max(v0a, v1a), MathPlus.max(v0b, v1b));
/* 260 */     this._v0 = (v0a < v1a) ? vmin : vmax;
/* 261 */     this._v1 = (v0a < v1a) ? vmax : vmin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 270 */     double r0a = (v0a - this._v0) / (this._v1 - this._v0);
/* 271 */     double r0b = (v0b - this._v0) / (this._v1 - this._v0);
/* 272 */     double r1a = (v1a - this._v0) / (this._v1 - this._v0);
/* 273 */     double r1b = (v1b - this._v0) / (this._v1 - this._v0);
/* 274 */     assert 0.0D <= r0a && r0a < 1.0D : r0a;
/* 275 */     assert 0.0D <= r0b && r0b < 1.0D : r0b;
/* 276 */     assert 0.0D < r1a && r1a <= 1.0D : r1a;
/* 277 */     assert 0.0D < r1b && r1b <= 1.0D : r1b;
/*     */     
/* 279 */     double u0 = 0.0D;
/* 280 */     double u1 = 1.0D;
/* 281 */     int niter = 0;
/*     */     do {
/* 283 */       this._u0 = u0;
/* 284 */       this._u1 = u1;
/* 285 */       u0 = MathPlus.max((u0a - r0a * this._u1) / (1.0D - r0a), (u0b - r0b * this._u1) / (1.0D - r0b));
/* 286 */       u1 = MathPlus.min((u1a - (1.0D - r1a) * this._u0) / r1a, (u1b - (1.0D - r1b) * this._u0) / r1b);
/* 287 */       niter++;
/* 288 */     } while ((this._u0 < u0 || u1 < this._u1) && niter < 10);
/* 289 */     assert niter < 10 : "niter<10";
/* 290 */     assert 0.0D <= this._u0 && this._u0 < this._u1 && this._u1 <= 1.0D : "_u0 and _u1 valid";
/*     */ 
/*     */     
/* 293 */     if (p.isLinear() && !isLinear()) {
/* 294 */       setScale(AxisScale.LINEAR);
/*     */     }
/*     */     
/* 297 */     computeShiftsAndScales();
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
/*     */   public double getScaleRatio(Projector p) {
/* 316 */     return this._vscale / p._vscale;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 321 */     if (this == obj)
/* 322 */       return true; 
/* 323 */     if (obj == null || getClass() != obj.getClass())
/* 324 */       return false; 
/* 325 */     Projector that = (Projector)obj;
/* 326 */     return (this._u0 == that._u0 && this._u1 == that._u1 && this._v0 == that._v0 && this._v1 == that._v1 && this._scaleType == that._scaleType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 335 */     long u0bits = Double.doubleToLongBits(this._u0);
/* 336 */     long u1bits = Double.doubleToLongBits(this._u1);
/* 337 */     long v0bits = Double.doubleToLongBits(this._v0);
/* 338 */     long v1bits = Double.doubleToLongBits(this._v1);
/* 339 */     return (int)(u0bits ^ u0bits >>> 32L ^ u1bits ^ u1bits >>> 32L ^ v0bits ^ v0bits >>> 32L ^ v1bits ^ v1bits >>> 32L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 347 */     return "Projector(" + this._v0 + ", " + this._v1 + ", " + this._u0 + ", " + this._u1 + ", " + this._scaleType + ")";
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
/*     */   protected Projector setScale(AxisScale s) {
/* 359 */     if (s.isLog() && (this._v0 != 0.0D || this._v1 != 0.0D))
/* 360 */       Check.argument((this._v0 > 0.0D && this._v1 > 0.0D), "LOG scale: v0<=0 or v1<=0"); 
/* 361 */     this._scaleType = s;
/* 362 */     computeShiftsAndScales();
/* 363 */     return this;
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
/*     */   private void computeShiftsAndScales() {
/* 376 */     if (this._scaleType == AxisScale.LINEAR) {
/* 377 */       this._uscale = (this._v1 - this._v0) / (this._u1 - this._u0);
/* 378 */       this._ushift = this._v0 - this._uscale * this._u0;
/* 379 */       this._vscale = (this._u1 - this._u0) / (this._v1 - this._v0);
/* 380 */       this._vshift = this._u0 - this._vscale * this._v0;
/* 381 */     } else if (this._scaleType == AxisScale.LOG10) {
/* 382 */       this._vscale = (this._u1 - this._u0) / (ArrayMath.log10(this._v1) - ArrayMath.log10(this._v0));
/* 383 */       this._uscale = (ArrayMath.log10(this._v1) - ArrayMath.log10(this._v0)) / (this._u1 - this._u0);
/* 384 */       this._ushift = ArrayMath.log10(this._v0) - this._uscale * this._u0;
/* 385 */       this._vshift = this._u0 - this._vscale * ArrayMath.log10(this._v0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/Projector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */