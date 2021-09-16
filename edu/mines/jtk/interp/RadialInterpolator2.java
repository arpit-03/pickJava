/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.la.DMatrix;
/*     */ import edu.mines.jtk.la.DMatrixLud;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RadialInterpolator2
/*     */ {
/*     */   private Basis _basis;
/*     */   private int _n;
/*     */   private float[] _f;
/*     */   private float[] _x1;
/*     */   private float[] _x2;
/*     */   private float[] _w;
/*     */   private double _m11;
/*     */   private double _m12;
/*     */   private double _m22;
/*     */   private boolean _mt;
/*     */   private PolyTrend2 _trend;
/*     */   private int _order;
/*     */   
/*     */   static interface Basis
/*     */   {
/*     */     double evaluate(double param1Double);
/*     */   }
/*     */   
/*     */   public static class Biharmonic
/*     */     implements Basis
/*     */   {
/*     */     private double _s;
/*     */     
/*     */     public Biharmonic() {
/*  58 */       this(1.0D);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Biharmonic(double scale) {
/*  66 */       this._s = 1.0D / scale;
/*     */     }
/*     */     
/*     */     public double evaluate(double r) {
/*  70 */       double g = 0.0D;
/*  71 */       if (r > 0.0D) {
/*  72 */         r *= this._s;
/*  73 */         g = r * r * (ArrayMath.log(r) - 1.0D);
/*     */       } 
/*  75 */       return g;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class WesselBercovici
/*     */     implements Basis
/*     */   {
/*     */     private double _p;
/*     */ 
/*     */ 
/*     */     
/*     */     private double _q;
/*     */ 
/*     */ 
/*     */     
/*     */     private double _s;
/*     */ 
/*     */ 
/*     */     
/*     */     private static final double LOG2 = 0.6931471805599453D;
/*     */ 
/*     */ 
/*     */     
/*     */     private static final double EULER_GAMMA = 0.5772156649015329D;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WesselBercovici(double tension) {
/* 107 */       this(tension, 1.0D);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WesselBercovici(double tension, double scale) {
/* 116 */       Check.argument((0.0D <= tension), "0.0<=tension");
/* 117 */       Check.argument((tension < 1.0D), "tension<1.0");
/* 118 */       this._p = ArrayMath.sqrt(tension / (1.0D - tension));
/* 119 */       this._q = 2.0D / this._p;
/* 120 */       this._s = 1.0D / scale;
/*     */     }
/*     */ 
/*     */     
/*     */     public double evaluate(double r) {
/* 125 */       double g = 0.0D;
/* 126 */       if (r > 0.0D) {
/* 127 */         r *= this._s;
/* 128 */         if (this._p == 0.0D) {
/* 129 */           g = r * r * (ArrayMath.log(r) - 1.0D);
/*     */         } else {
/* 131 */           double pr = this._p * r;
/* 132 */           if (r <= this._q) {
/* 133 */             double t = pr * pr;
/* 134 */             double y = 0.25D * t;
/* 135 */             double z = t / 14.0625D;
/* 136 */             g = -ArrayMath.log(0.5D * pr) * z * (3.5156229D + z * (3.0899424D + z * (1.2067492D + z * (0.2659732D + z * (0.0360768D + z * 0.0045813D))))) + y * (0.4227842D + y * (0.23069756D + y * (0.0348859D + y * (0.00262698D + y * (1.075E-4D + y * 7.4E-6D)))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 150 */             double y = this._q / r;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 159 */             g = ArrayMath.exp(-pr) / ArrayMath.sqrt(pr) * (1.25331414D + y * (-0.07832358D + y * (0.02189568D + y * (-0.01062446D + y * (0.00587872D + y * (-0.0025154D + y * 5.3208E-4D)))))) + ArrayMath.log(pr) - 0.6931471805599453D + 0.5772156649015329D;
/*     */           } 
/*     */         } 
/*     */       } 
/* 163 */       return g;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RadialInterpolator2(Basis basis, float[] f, float[] x1, float[] x2) {
/* 318 */     this._order = -1; this._basis = basis; setSamples(f, x1, x2);
/*     */   }
/*     */   public void setBasis(Basis basis) { this._basis = basis; }
/* 321 */   public void setSamples(float[] f, float[] x1, float[] x2) { this._n = f.length; this._f = ArrayMath.copy(f); this._x1 = ArrayMath.copy(x1); this._x2 = ArrayMath.copy(x2); this._w = null; if (this._trend != null) this._trend.detrend(this._f, this._x1, this._x2);  } public void setMetricTensor(double m11, double m12, double m22) { Check.argument((m11 * m22 >= m12 * m12), "determinant is non-negative"); if (this._m11 != m11 || this._m12 != m12 || this._m22 != m22) { this._m11 = m11; this._m12 = m12; this._m22 = m22; this._mt = (this._m11 != 1.0D || this._m12 != 0.0D || this._m22 != 1.0D); this._w = null; }  } private double g(double x1a, double x2a, double x1b, double x2b) { return this._basis.evaluate(r(x1a, x2a, x1b, x2b)); }
/*     */   public void setPolyTrend(int order) { Check.argument((-1 <= order), "-1<=order"); Check.argument((order <= 2), "order<=2"); if (this._order != order) { if (this._trend != null) { this._trend.restore(this._f, this._x1, this._x2); this._trend = null; }  if (order != -1) { this._trend = new PolyTrend2(order, this._f, this._x1, this._x2); this._trend.detrend(this._f, this._x1, this._x2); }  this._order = order; this._w = null; }  }
/*     */   public float interpolate(float x1, float x2) { ensureWeights(); double f = 0.0D; double x1i = x1; double x2i = x2; for (int k = 0; k < this._n; k++) { double x1k = this._x1[k]; double x2k = this._x2[k]; f += this._w[k] * g(x1k, x2k, x1i, x2i); }  float ff = (float)f; if (this._trend != null) ff = this._trend.restore(ff, x1, x2);  return ff; }
/*     */   public float[][] interpolate(Sampling s1, Sampling s2) { int n1 = s1.getCount(); int n2 = s2.getCount(); float[][] f = new float[n2][n1]; for (int i2 = 0; i2 < n2; i2++) { float x2 = (float)s2.getValue(i2); for (int i1 = 0; i1 < n1; i1++) { float x1 = (float)s1.getValue(i1); f[i2][i1] = interpolate(x1, x2); }  }  return f; }
/* 325 */   public float[] getWeights() { ensureWeights(); return ArrayMath.copy(this._w); } private double r(double x1a, double x2a, double x1b, double x2b) { return r(x1a - x1b, x2a - x2b); }
/*     */ 
/*     */   
/*     */   private double r(double d1, double d2) {
/* 329 */     return this._mt ? 
/* 330 */       ArrayMath.sqrt(this._m11 * d1 * d1 + 2.0D * this._m12 * d1 * d2 + this._m22 * d2 * d2) : 
/* 331 */       ArrayMath.hypot(d1, d2);
/*     */   }
/*     */   
/*     */   private void ensureWeights() {
/* 335 */     if (this._w != null)
/*     */       return; 
/* 337 */     DMatrix a = new DMatrix(this._n, this._n);
/* 338 */     DMatrix b = new DMatrix(this._n, 1);
/* 339 */     for (int i = 0; i < this._n; i++) {
/* 340 */       double x1i = this._x1[i];
/* 341 */       double x2i = this._x2[i];
/* 342 */       for (int k = 0; k < this._n; k++) {
/* 343 */         double x1j = this._x1[k];
/* 344 */         double x2j = this._x2[k];
/* 345 */         a.set(i, k, g(x1i, x2i, x1j, x2j));
/*     */       } 
/* 347 */       b.set(i, 0, this._f[i]);
/*     */     } 
/* 349 */     DMatrixLud lud = new DMatrixLud(a);
/* 350 */     DMatrix w = lud.solve(b);
/* 351 */     this._w = new float[this._n];
/* 352 */     for (int j = 0; j < this._n; j++)
/* 353 */       this._w[j] = (float)w.get(j, 0); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/RadialInterpolator2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */