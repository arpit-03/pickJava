/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.dsp.Sampling;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TrilinearInterpolator3
/*     */ {
/*     */   private float[] _x1;
/*     */   private float[] _x2;
/*     */   private float[] _x3;
/*     */   private float[][][] _a000;
/*     */   private float[][][] _a100;
/*     */   private float[][][] _a010;
/*     */   private float[][][] _a001;
/*     */   private float[][][] _a110;
/*     */   private float[][][] _a101;
/*     */   private float[][][] _a011;
/*     */   private float[][][] _a111;
/*     */   private int[] _ks;
/*     */   
/*     */   public TrilinearInterpolator3(float[] x1, float[] x2, float[] x3, float[][][] y) {
/*  51 */     this(x1.length, x2.length, x3.length, x1, x2, x3, y);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrilinearInterpolator3(int n1, int n2, int n3, float[] x1, float[] x2, float[] x3, float[][][] y) {
/* 284 */     this._ks = new int[] { 0, 0, 0 }; Check.argument(ArrayMath.isMonotonic(x1), "array x1 is monotonic"); Check.argument(ArrayMath.isMonotonic(x2), "array x2 is monotonic"); Check.argument(ArrayMath.isMonotonic(x3), "array x3 is monotonic"); this._x1 = ArrayMath.copy(n1, x1); this._x2 = ArrayMath.copy(n2, x2); this._x3 = ArrayMath.copy(n3, x3); makeCoefficients(x1, x2, x3, y);
/*     */   }
/*     */   public float interpolate(float x1, float x2, float x3) { return interpolate000(x1, x2, x3); }
/* 287 */   public float interpolate000(float x1, float x2, float x3) { return interpolate000(x1, x2, x3, this._ks); } public float interpolate100(float x1, float x2, float x3) { return interpolate100(x1, x2, x3, this._ks); } public float interpolate010(float x1, float x2, float x3) { return interpolate010(x1, x2, x3, this._ks); } public float interpolate001(float x1, float x2, float x3) { return interpolate001(x1, x2, x3, this._ks); } public float[][][] interpolate(Sampling s1, Sampling s2, Sampling s3) { return interpolate000(s1, s2, s3); } public float[][][] interpolate000(Sampling s1, Sampling s2, Sampling s3) { int n1 = s1.getCount(); int n2 = s2.getCount(); int n3 = s3.getCount(); float[][][] y = new float[n3][n2][n1]; interpolate000(s1, s2, s3, y); return y; } private static int index(float x, float[] xs, int i) { i = ArrayMath.binarySearch(xs, x, i);
/* 288 */     if (i < 0)
/* 289 */       i = (i < -1) ? (-2 - i) : 0; 
/* 290 */     if (i >= xs.length - 1)
/* 291 */       i = xs.length - 2; 
/* 292 */     return i; }
/*     */   public void interpolate(Sampling s1, Sampling s2, Sampling s3, float[][][] y) { interpolate000(s1, s2, s3, y); }
/*     */   public void interpolate000(Sampling s1, Sampling s2, Sampling s3, float[][][] y) { int n1 = s1.getCount(); int n2 = s2.getCount(); int n3 = s3.getCount(); int[] k1 = makeIndices(s1, this._x1); int[] k2 = makeIndices(s2, this._x2); int[] k3 = makeIndices(s3, this._x3); for (int i3 = 0; i3 < n3; i3++) { float x3 = (float)s3.getValue(i3); for (int i2 = 0; i2 < n2; i2++) { float x2 = (float)s2.getValue(i2); for (int i1 = 0; i1 < n1; i1++) { float x1 = (float)s1.getValue(i1); y[i3][i2][i1] = interpolate000(x1, x2, x3, k1[i1], k2[i2], k3[i3]); }  }  }  }
/*     */   public float[][][] interpolate(float[] x1, float[] x2, float[] x3) { return interpolate000(x1, x2, x3); }
/* 296 */   public float[][][] interpolate000(float[] x1, float[] x2, float[] x3) { int n1 = x1.length; int n2 = x2.length; int n3 = x3.length; float[][][] y = new float[n3][n2][n1]; interpolate000(x1, x2, x3, y); return y; } public void interpolate(float[] x1, float[] x2, float[] x3, float[][][] y) { interpolate000(x1, x2, x3, y); } public void interpolate000(float[] x1, float[] x2, float[] x3, float[][][] y) { int n1 = x1.length; int n2 = x2.length; int n3 = x3.length; int[] k1 = makeIndices(x1, this._x1); int[] k2 = makeIndices(x2, this._x2); int[] k3 = makeIndices(x3, this._x3); for (int i3 = 0; i3 < n3; i3++) { for (int i2 = 0; i2 < n2; i2++) { for (int i1 = 0; i1 < n1; i1++) y[i3][i2][i1] = interpolate000(x1[i1], x2[i2], x3[i3], k1[i1], k2[i2], k3[i3]);  }  }  } private static void trace(String s) { System.out.println(s); } private void updateIndices(float x1, float x2, float x3, int[] ks) { ks[0] = index(x1, this._x1, ks[0]);
/* 297 */     ks[1] = index(x2, this._x2, ks[1]);
/* 298 */     ks[2] = index(x3, this._x3, ks[2]); }
/*     */ 
/*     */   
/*     */   private static int[] makeIndices(float[] xi, float[] xs) {
/* 302 */     int n = xi.length;
/* 303 */     int[] ki = new int[n];
/* 304 */     ki[0] = index(xi[0], xs, 0);
/* 305 */     for (int i = 1; i < n; i++)
/* 306 */       ki[i] = index(xi[i], xs, ki[i - 1]); 
/* 307 */     return ki;
/*     */   }
/*     */   private static int[] makeIndices(Sampling si, float[] xs) {
/* 310 */     int n = si.getCount();
/* 311 */     int[] ki = new int[n];
/* 312 */     ki[0] = index((float)si.getValue(0), xs, 0);
/* 313 */     for (int i = 1; i < n; i++)
/* 314 */       ki[i] = index((float)si.getValue(i), xs, ki[i - 1]); 
/* 315 */     return ki;
/*     */   }
/*     */   
/*     */   private float interpolate000(float x1, float x2, float x3, int[] ks) {
/* 319 */     updateIndices(x1, x2, x3, ks);
/* 320 */     return interpolate000(x1, x2, x3, ks[0], ks[1], ks[2]);
/*     */   }
/*     */   private float interpolate100(float x1, float x2, float x3, int[] ks) {
/* 323 */     updateIndices(x1, x2, x3, ks);
/* 324 */     return interpolate100(x1, x2, x3, ks[0], ks[1], ks[2]);
/*     */   }
/*     */   private float interpolate010(float x1, float x2, float x3, int[] ks) {
/* 327 */     updateIndices(x1, x2, x3, ks);
/* 328 */     return interpolate010(x1, x2, x3, ks[0], ks[1], ks[2]);
/*     */   }
/*     */   private float interpolate001(float x1, float x2, float x3, int[] ks) {
/* 331 */     updateIndices(x1, x2, x3, ks);
/* 332 */     return interpolate001(x1, x2, x3, ks[0], ks[1], ks[2]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float interpolate000(float x1, float x2, float x3, int k1, int k2, int k3) {
/* 338 */     float d1 = x1 - this._x1[k1];
/* 339 */     float d2 = x2 - this._x2[k2];
/* 340 */     float d3 = x3 - this._x3[k3];
/* 341 */     return this._a000[k3][k2][k1] + d1 * (this._a100[k3][k2][k1] + d3 * this._a101[k3][k2][k1]) + d2 * (this._a010[k3][k2][k1] + d1 * this._a110[k3][k2][k1]) + d3 * (this._a001[k3][k2][k1] + d2 * (this._a011[k3][k2][k1] + d1 * this._a111[k3][k2][k1]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float interpolate100(float x1, float x2, float x3, int k1, int k2, int k3) {
/* 349 */     float d2 = x2 - this._x2[k2];
/* 350 */     float d3 = x3 - this._x3[k3];
/* 351 */     return this._a100[k3][k2][k1] + d2 * this._a110[k3][k2][k1] + d3 * (this._a101[k3][k2][k1] + d2 * this._a111[k3][k2][k1]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float interpolate010(float x1, float x2, float x3, int k1, int k2, int k3) {
/* 357 */     float d1 = x1 - this._x1[k1];
/* 358 */     float d3 = x3 - this._x3[k3];
/* 359 */     return this._a010[k3][k2][k1] + d1 * this._a110[k3][k2][k1] + d3 * (this._a011[k3][k2][k1] + d1 * this._a111[k3][k2][k1]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float interpolate001(float x1, float x2, float x3, int k1, int k2, int k3) {
/* 365 */     float d1 = x1 - this._x1[k1];
/* 366 */     float d2 = x2 - this._x2[k2];
/* 367 */     return this._a001[k3][k2][k1] + d1 * this._a101[k3][k2][k1] + d2 * (this._a011[k3][k2][k1] + d1 * this._a111[k3][k2][k1]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void makeCoefficients(float[] x1, float[] x2, float[] x3, float[][][] y) {
/* 374 */     int n1 = x1.length;
/* 375 */     int n2 = x2.length;
/* 376 */     int n3 = x3.length;
/* 377 */     this._a000 = new float[n3 - 1][n2 - 1][n1 - 1];
/* 378 */     this._a100 = new float[n3 - 1][n2 - 1][n1 - 1];
/* 379 */     this._a010 = new float[n3 - 1][n2 - 1][n1 - 1];
/* 380 */     this._a001 = new float[n3 - 1][n2 - 1][n1 - 1];
/* 381 */     this._a110 = new float[n3 - 1][n2 - 1][n1 - 1];
/* 382 */     this._a101 = new float[n3 - 1][n2 - 1][n1 - 1];
/* 383 */     this._a011 = new float[n3 - 1][n2 - 1][n1 - 1];
/* 384 */     this._a111 = new float[n3 - 1][n2 - 1][n1 - 1];
/* 385 */     for (int k3 = 0; k3 < n3 - 1; k3++) {
/* 386 */       float d3 = x3[k3 + 1] - x3[k3];
/* 387 */       for (int k2 = 0; k2 < n2 - 1; k2++) {
/* 388 */         float d2 = x2[k2 + 1] - x2[k2];
/* 389 */         for (int k1 = 0; k1 < n1 - 1; k1++) {
/* 390 */           float d1 = x1[k1 + 1] - x1[k1];
/* 391 */           float y000 = y[k3][k2][k1];
/* 392 */           float y100 = y[k3][k2][k1 + 1];
/* 393 */           float y010 = y[k3][k2 + 1][k1];
/* 394 */           float y001 = y[k3 + 1][k2][k1];
/* 395 */           float y110 = y[k3][k2 + 1][k1 + 1];
/* 396 */           float y101 = y[k3 + 1][k2][k1 + 1];
/* 397 */           float y011 = y[k3 + 1][k2 + 1][k1];
/* 398 */           float y111 = y[k3 + 1][k2 + 1][k1 + 1];
/* 399 */           this._a000[k3][k2][k1] = y000;
/* 400 */           this._a100[k3][k2][k1] = (y100 - y000) / d1;
/* 401 */           this._a010[k3][k2][k1] = (y010 - y000) / d2;
/* 402 */           this._a001[k3][k2][k1] = (y001 - y000) / d3;
/* 403 */           this._a110[k3][k2][k1] = (y000 - y100 + y110 - y010) / d1 * d2;
/* 404 */           this._a101[k3][k2][k1] = (y000 - y100 + y101 - y001) / d1 * d3;
/* 405 */           this._a011[k3][k2][k1] = (y000 - y010 + y011 - y001) / d2 * d3;
/* 406 */           this._a111[k3][k2][k1] = (y111 - y000 + y100 - y011 + y010 - y101 + y001 - y110) / d1 * d2 * d3;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/TrilinearInterpolator3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */