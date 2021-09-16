/*     */ package edu.mines.jtk.la;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TridiagonalFMatrix
/*     */ {
/*     */   private int _n;
/*     */   private float[] _a;
/*     */   private float[] _b;
/*     */   private float[] _c;
/*     */   private float[] _w;
/*     */   
/*     */   public TridiagonalFMatrix(int n) {
/*  41 */     this(n, new float[n], new float[n], new float[n]);
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
/*     */   public TridiagonalFMatrix(int n, float[] a, float[] b, float[] c) {
/*  53 */     this._n = n;
/*  54 */     this._a = a;
/*  55 */     this._b = b;
/*  56 */     this._c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int n() {
/*  64 */     return this._n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] a() {
/*  72 */     return this._a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] b() {
/*  80 */     return this._b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] c() {
/*  88 */     return this._c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void solve(float[] r, float[] u) {
/*  99 */     if (this._w == null)
/* 100 */       this._w = new float[this._n]; 
/* 101 */     float t = this._b[0];
/* 102 */     u[0] = r[0] / t; int j;
/* 103 */     for (j = 1; j < this._n; j++) {
/* 104 */       this._w[j] = this._c[j - 1] / t;
/* 105 */       t = this._b[j] - this._a[j] * this._w[j];
/* 106 */       u[j] = (r[j] - this._a[j] * u[j - 1]) / t;
/*     */     } 
/* 108 */     for (j = this._n - 1; j > 0; j--) {
/* 109 */       u[j - 1] = u[j - 1] - this._w[j] * u[j];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] times(float[] x) {
/* 118 */     int n = x.length;
/* 119 */     float[] y = new float[n];
/* 120 */     times(x, y);
/* 121 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void times(float[] x, float[] y) {
/* 130 */     int n = x.length;
/* 131 */     int nm1 = n - 1;
/*     */     
/* 133 */     float xip1 = 0.0F;
/* 134 */     float xi = x[0];
/* 135 */     y[0] = this._b[0] * xi;
/* 136 */     if (n > 1) {
/* 137 */       xip1 = x[1];
/* 138 */       y[0] = y[0] + this._c[0] * xip1;
/* 139 */       y[n - 1] = this._a[n - 1] * x[n - 2] + this._b[n - 1] * x[n - 1];
/*     */     } 
/* 141 */     for (int i = 1; i < nm1; i++) {
/* 142 */       float xim1 = xi;
/* 143 */       xi = xip1;
/* 144 */       xip1 = x[i + 1];
/* 145 */       y[i] = this._a[i] * xim1 + this._b[i] * xi + this._c[i] * xip1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/la/TridiagonalFMatrix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */