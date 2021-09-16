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
/*     */ public abstract class WarpFunction1
/*     */ {
/*     */   private int _n;
/*     */   
/*     */   public static WarpFunction1 constant(double u, int n) {
/*  45 */     return new ConstantWarp1(u, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WarpFunction1 gaussian(double u, int n) {
/*  55 */     return new GaussianWarp1(u, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WarpFunction1 sinusoid(double u, int n) {
/*  65 */     return new SinusoidWarp1(u, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WarpFunction1 constantPlusSinusoid(double c, double u, int n) {
/*  76 */     return new SinusoidWarp1(c, u, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract double u(double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double ux(double x) {
/*  92 */     return u(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double um(double m) {
/* 101 */     double um = 0.0D;
/*     */     
/*     */     while (true) {
/* 104 */       double up = um;
/* 105 */       um = u(m - 0.5D * um);
/* 106 */       if (ArrayMath.abs(um - up) <= 1.0E-4D) {
/* 107 */         return um;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double uy(double y) {
/* 116 */     double uy = 0.0D;
/*     */     
/*     */     while (true) {
/* 119 */       double up = uy;
/* 120 */       uy = u(y - uy);
/* 121 */       if (ArrayMath.abs(uy - up) <= 1.0E-4D) {
/* 122 */         return uy;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] ux() {
/* 130 */     float[] u = new float[this._n];
/* 131 */     for (int i = 0; i < this._n; i++) {
/* 132 */       double x = i;
/* 133 */       u[i] = (float)ux(x);
/*     */     } 
/* 135 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] um() {
/* 143 */     float[] u = new float[this._n];
/* 144 */     for (int i = 0; i < this._n; i++) {
/* 145 */       double m = i;
/* 146 */       u[i] = (float)um(m);
/*     */     } 
/* 148 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] uy() {
/* 156 */     float[] u = new float[this._n];
/* 157 */     for (int i = 0; i < this._n; i++) {
/* 158 */       double y = i;
/* 159 */       u[i] = (float)uy(y);
/*     */     } 
/* 161 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] warp(float[] f) {
/* 170 */     SincInterpolator si = new SincInterpolator();
/* 171 */     float[] g = new float[this._n];
/* 172 */     for (int i = 0; i < this._n; i++) {
/* 173 */       double y = i;
/* 174 */       double x = y - uy(y);
/* 175 */       g[i] = si.interpolate(this._n, 1.0D, 0.0D, f, x);
/*     */     } 
/* 177 */     return g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] unwarp(float[] g) {
/* 186 */     SincInterpolator si = new SincInterpolator();
/* 187 */     float[] f = new float[this._n];
/* 188 */     for (int i = 0; i < this._n; i++) {
/* 189 */       double x = i;
/* 190 */       double y = x + ux(x);
/* 191 */       f[i] = si.interpolate(this._n, 1.0D, 0.0D, g, y);
/*     */     } 
/* 193 */     return f;
/*     */   }
/*     */   
/*     */   protected WarpFunction1(int n) {
/* 197 */     this._n = n;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ConstantWarp1
/*     */     extends WarpFunction1
/*     */   {
/*     */     private double _u;
/*     */ 
/*     */ 
/*     */     
/*     */     public ConstantWarp1(double u, int n) {
/* 210 */       super(n);
/* 211 */       this._u = u;
/*     */     }
/*     */     public double u(double x) {
/* 214 */       return this._u;
/*     */     }
/*     */     public double umax() {
/* 217 */       return this._u;
/*     */     }
/*     */     public double e(double x) {
/* 220 */       return 0.0D;
/*     */     }
/*     */     public double emax() {
/* 223 */       return 0.0D;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class GaussianWarp1
/*     */     extends WarpFunction1 {
/*     */     private double _a;
/*     */     private double _b;
/*     */     
/*     */     public GaussianWarp1(double umax, int n) {
/* 233 */       super(n);
/* 234 */       this._a = (n - 1) / 2.0D;
/* 235 */       this._b = this._a / 3.0D;
/* 236 */       this._c = umax * ArrayMath.exp(0.5D) / this._b;
/* 237 */       this._umax = umax;
/* 238 */       this._emax = this._c;
/*     */     } private double _c; private double _umax; private double _emax;
/*     */     public double u(double x) {
/* 241 */       double xa = x - this._a;
/* 242 */       return -this._c * xa * ArrayMath.exp(-0.5D * xa * xa / this._b * this._b);
/*     */     }
/*     */     public double umax() {
/* 245 */       return this._umax;
/*     */     }
/*     */     public double e(double x) {
/* 248 */       double xa = x - this._a;
/* 249 */       return -this._c * (1.0D - xa * xa / this._b * this._b) * ArrayMath.exp(-0.5D * xa * xa / this._b * this._b);
/*     */     }
/*     */     public double emax() {
/* 252 */       return this._emax;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SinusoidWarp2
/*     */     extends WarpFunction2 {
/*     */     private double _a1;
/*     */     private double _a2;
/*     */     private double _b1;
/*     */     private double _b2;
/*     */     private double _c1;
/*     */     private double _c2;
/*     */     
/*     */     public SinusoidWarp2(double u1max, double u2max, int n1, int n2) {
/* 266 */       this(0.0D, 0.0D, u1max, u2max, n1, n2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SinusoidWarp2(double u1add, double u2add, double u1max, double u2max, int n1, int n2) {
/* 273 */       super(n1, n2);
/* 274 */       double l1 = (n1 - 1);
/* 275 */       double l2 = (n2 - 1);
/* 276 */       this._c1 = u1add;
/* 277 */       this._c2 = u2add;
/* 278 */       this._a1 = u1max;
/* 279 */       this._a2 = u2max;
/* 280 */       this._b1 = 6.283185307179586D / l1;
/* 281 */       this._b2 = 6.283185307179586D / l2;
/*     */     }
/*     */     public double u1(double x1, double x2) {
/* 284 */       return this._c1 + this._a1 * ArrayMath.sin(this._b1 * x1) * ArrayMath.sin(0.5D * this._b2 * x2);
/*     */     }
/*     */     public double u2(double x1, double x2) {
/* 287 */       return this._c2 + this._a2 * ArrayMath.sin(this._b2 * x2) * ArrayMath.sin(0.5D * this._b1 * x1);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SinusoidWarp1 extends WarpFunction1 {
/*     */     private double _a;
/*     */     private double _b;
/*     */     private double _c;
/*     */     private double _umax;
/*     */     private double _emax;
/*     */     
/*     */     public SinusoidWarp1(double umax, int n) {
/* 299 */       this(0.0D, umax, n);
/*     */     }
/*     */     public SinusoidWarp1(double uadd, double umax, int n) {
/* 302 */       super(n);
/* 303 */       double l = (n - 1);
/* 304 */       this._a = umax;
/* 305 */       this._b = 6.283185307179586D / l;
/* 306 */       this._c = uadd;
/* 307 */       this._umax = umax;
/* 308 */       this._emax = this._a * this._b;
/*     */     }
/*     */     public double u(double x) {
/* 311 */       return this._c + this._a * ArrayMath.sin(this._b * x);
/*     */     }
/*     */     public double umax() {
/* 314 */       return this._umax;
/*     */     }
/*     */     public double e(double x) {
/* 317 */       return this._a * this._b * ArrayMath.cos(this._b * x);
/*     */     }
/*     */     public double emax() {
/* 320 */       return this._emax;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/WarpFunction1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */