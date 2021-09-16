/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WarpFunction2
/*     */ {
/*     */   private int _n1;
/*     */   private int _n2;
/*     */   
/*     */   public static WarpFunction2 constant(double u1, double u2, int n1, int n2) {
/*  47 */     return new ConstantWarp2(u1, u2, n1, n2);
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
/*     */   public static WarpFunction2 gaussian(double u1, double u2, int n1, int n2) {
/*  59 */     return new GaussianWarp2(u1, u2, n1, n2);
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
/*     */   public static WarpFunction2 sinusoid(double u1, double u2, int n1, int n2) {
/*  71 */     return new SinusoidWarp2(u1, u2, n1, n2);
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
/*     */   public static WarpFunction2 constantPlusSinusoid(double c1, double c2, double u1, double u2, int n1, int n2) {
/*  87 */     return new SinusoidWarp2(c1, c2, u1, u2, n1, n2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract double u1(double paramDouble1, double paramDouble2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract double u2(double paramDouble1, double paramDouble2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double u1x(double x1, double x2) {
/* 113 */     return u1(x1, x2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double u2x(double x1, double x2) {
/* 123 */     return u2(x1, x2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double u1m(double m1, double m2) {
/* 134 */     double u1m = 0.0D;
/* 135 */     double u2m = 0.0D;
/*     */     while (true) {
/* 137 */       double u1p = u1m;
/* 138 */       u1m = u1(m1 - 0.5D * u1m, m2 - 0.5D * u2m);
/* 139 */       u2m = u2(m1 - 0.5D * u1m, m2 - 0.5D * u2m);
/* 140 */       if (ArrayMath.abs(u1m - u1p) <= 1.0E-4D) {
/* 141 */         return u1m;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double u2m(double m1, double m2) {
/* 152 */     double u1m = 0.0D;
/* 153 */     double u2m = 0.0D;
/*     */     while (true) {
/* 155 */       double u2p = u2m;
/* 156 */       u1m = u1(m1 - 0.5D * u1m, m2 - 0.5D * u2m);
/* 157 */       u2m = u2(m1 - 0.5D * u1m, m2 - 0.5D * u2m);
/* 158 */       if (ArrayMath.abs(u2m - u2p) <= 1.0E-4D) {
/* 159 */         return u2m;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double u1y(double y1, double y2) {
/* 170 */     double u1y = 0.0D;
/* 171 */     double u2y = 0.0D;
/*     */     while (true) {
/* 173 */       double u1p = u1y;
/* 174 */       u1y = u1(y1 - u1y, y2 - u2y);
/* 175 */       u2y = u2(y1 - u1y, y2 - u2y);
/* 176 */       if (ArrayMath.abs(u1y - u1p) <= 1.0E-4D) {
/* 177 */         return u1y;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double u2y(double y1, double y2) {
/* 188 */     double u1y = 0.0D;
/* 189 */     double u2y = 0.0D;
/*     */     while (true) {
/* 191 */       double u2p = u2y;
/* 192 */       u1y = u1(y1 - u1y, y2 - u2y);
/* 193 */       u2y = u2(y1 - u1y, y2 - u2y);
/* 194 */       if (ArrayMath.abs(u2y - u2p) <= 1.0E-4D) {
/* 195 */         return u2y;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] u1x() {
/* 203 */     float[][] u = new float[this._n2][this._n1];
/* 204 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 205 */       double x2 = i2;
/* 206 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 207 */         double x1 = i1;
/* 208 */         u[i2][i1] = (float)u1x(x1, x2);
/*     */       } 
/*     */     } 
/* 211 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] u2x() {
/* 219 */     float[][] u = new float[this._n2][this._n1];
/* 220 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 221 */       double x2 = i2;
/* 222 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 223 */         double x1 = i1;
/* 224 */         u[i2][i1] = (float)u2x(x1, x2);
/*     */       } 
/*     */     } 
/* 227 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] u1m() {
/* 235 */     float[][] u = new float[this._n2][this._n1];
/* 236 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 237 */       double m2 = i2;
/* 238 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 239 */         double m1 = i1;
/* 240 */         u[i2][i1] = (float)u1m(m1, m2);
/*     */       } 
/*     */     } 
/* 243 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] u2m() {
/* 251 */     float[][] u = new float[this._n2][this._n1];
/* 252 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 253 */       double m2 = i2;
/* 254 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 255 */         double m1 = i1;
/* 256 */         u[i2][i1] = (float)u2m(m1, m2);
/*     */       } 
/*     */     } 
/* 259 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] u1y() {
/* 267 */     float[][] u = new float[this._n2][this._n1];
/* 268 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 269 */       double y2 = i2;
/* 270 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 271 */         double y1 = i1;
/* 272 */         u[i2][i1] = (float)u1y(y1, y2);
/*     */       } 
/*     */     } 
/* 275 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] u2y() {
/* 283 */     float[][] u = new float[this._n2][this._n1];
/* 284 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 285 */       double y2 = i2;
/* 286 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 287 */         double y1 = i1;
/* 288 */         u[i2][i1] = (float)u2y(y1, y2);
/*     */       } 
/*     */     } 
/* 291 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] warp1(float[][] f) {
/* 300 */     SincInterpolator si = new SincInterpolator();
/* 301 */     float[][] g = new float[this._n2][this._n1];
/* 302 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 303 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 304 */         g[i2][i1] = si.interpolate(this._n1, 1.0D, 0.0D, f[i2], i1 - u1y(i1, i2));
/*     */       }
/*     */     } 
/* 307 */     return g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] warp(float[][] f) {
/* 316 */     SincInterpolator si = new SincInterpolator();
/* 317 */     float[][] g = new float[this._n2][this._n1];
/* 318 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 319 */       double y2 = i2;
/* 320 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 321 */         double y1 = i1;
/* 322 */         double x1 = y1 - u1y(y1, y2);
/* 323 */         double x2 = y2 - u2y(y1, y2);
/* 324 */         g[i2][i1] = si.interpolate(this._n1, 1.0D, 0.0D, this._n2, 1.0D, 0.0D, f, x1, x2);
/*     */       } 
/*     */     } 
/* 327 */     return g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] unwarp1(float[][] g) {
/* 336 */     SincInterpolator si = new SincInterpolator();
/* 337 */     float[][] f = new float[this._n2][this._n1];
/* 338 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 339 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 340 */         f[i2][i1] = si.interpolate(this._n1, 1.0D, 0.0D, g[i2], i1 + u1x(i1, i2));
/*     */       }
/*     */     } 
/* 343 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] unwarp(float[][] g) {
/* 352 */     SincInterpolator si = new SincInterpolator();
/* 353 */     float[][] f = new float[this._n2][this._n1];
/* 354 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 355 */       double x2 = i2;
/* 356 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 357 */         double x1 = i1;
/* 358 */         double y1 = x1 + u1x(x1, x2);
/* 359 */         double y2 = x2 + u2x(x1, x2);
/* 360 */         f[i2][i1] = si.interpolate(this._n1, 1.0D, 0.0D, this._n2, 1.0D, 0.0D, g, y1, y2);
/*     */       } 
/*     */     } 
/* 363 */     return f;
/*     */   }
/*     */   
/*     */   protected WarpFunction2(int n1, int n2) {
/* 367 */     this._n1 = n1;
/* 368 */     this._n2 = n2;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ConstantWarp2
/*     */     extends WarpFunction2
/*     */   {
/*     */     private double _u1;
/*     */     
/*     */     private double _u2;
/*     */ 
/*     */     
/*     */     public ConstantWarp2(double u1, double u2, int n1, int n2) {
/* 381 */       super(n1, n2);
/* 382 */       this._u1 = u1;
/* 383 */       this._u2 = u2;
/*     */     }
/*     */     public double u1(double x1, double x2) {
/* 386 */       return this._u1;
/*     */     }
/*     */     public double u2(double x1, double x2) {
/* 389 */       return this._u2;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class GaussianWarp2 extends WarpFunction2 {
/*     */     private double _a1;
/*     */     private double _a2;
/*     */     private double _b1;
/*     */     
/*     */     public GaussianWarp2(double u1max, double u2max, int n1, int n2) {
/* 399 */       super(n1, n2);
/* 400 */       this._a1 = (n1 - 1) / 2.0D;
/* 401 */       this._a2 = (n2 - 1) / 2.0D;
/* 402 */       this._b1 = this._a1 / 3.0D;
/* 403 */       this._b2 = this._a2 / 3.0D;
/* 404 */       this._c1 = u1max * ArrayMath.exp(0.5D) / this._b1;
/* 405 */       this._c2 = u2max * ArrayMath.exp(0.5D) / this._b2;
/*     */     } private double _b2; private double _c1; private double _c2;
/*     */     public double u1(double x1, double x2) {
/* 408 */       double xa1 = x1 - this._a1;
/* 409 */       double xa2 = x2 - this._a2;
/* 410 */       return -this._c1 * xa1 * ArrayMath.exp(-0.5D * (xa1 * xa1 / this._b1 * this._b1 + xa2 * xa2 / this._b2 * this._b2));
/*     */     }
/*     */     public double u2(double x1, double x2) {
/* 413 */       double xa1 = x1 - this._a1;
/* 414 */       double xa2 = x2 - this._a2;
/* 415 */       return -this._c2 * xa2 * ArrayMath.exp(-0.5D * (xa1 * xa1 / this._b1 * this._b1 + xa2 * xa2 / this._b2 * this._b2));
/*     */     } }
/*     */   
/*     */   private static class SinusoidWarp2 extends WarpFunction2 {
/*     */     private double _a1;
/*     */     private double _a2;
/*     */     private double _b1;
/*     */     private double _b2;
/*     */     private double _c1;
/*     */     private double _c2;
/*     */     
/*     */     public SinusoidWarp2(double u1max, double u2max, int n1, int n2) {
/* 427 */       this(0.0D, 0.0D, u1max, u2max, n1, n2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SinusoidWarp2(double u1add, double u2add, double u1max, double u2max, int n1, int n2) {
/* 434 */       super(n1, n2);
/* 435 */       double l1 = (n1 - 1);
/* 436 */       double l2 = (n2 - 1);
/* 437 */       this._c1 = u1add;
/* 438 */       this._c2 = u2add;
/* 439 */       this._a1 = u1max;
/* 440 */       this._a2 = u2max;
/* 441 */       this._b1 = 6.283185307179586D / l1;
/* 442 */       this._b2 = 6.283185307179586D / l2;
/*     */     }
/*     */     public double u1(double x1, double x2) {
/* 445 */       return this._c1 + this._a1 * ArrayMath.sin(this._b1 * x1) * ArrayMath.sin(0.5D * this._b2 * x2);
/*     */     }
/*     */     public double u2(double x1, double x2) {
/* 448 */       return this._c2 + this._a2 * ArrayMath.sin(this._b2 * x2) * ArrayMath.sin(0.5D * this._b1 * x1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/WarpFunction2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */