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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BicubicInterpolator2
/*     */ {
/*     */   private int _n1;
/*     */   private int _n2;
/*     */   private float[] _x1;
/*     */   private float[] _x2;
/*     */   private float[][][][] _a;
/*     */   private int[] _ks;
/*     */   
/*     */   public enum Method
/*     */   {
/*  52 */     MONOTONIC,
/*     */ 
/*     */ 
/*     */     
/*  56 */     SPLINE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BicubicInterpolator2(float[] x1, float[] x2, float[][] y) {
/*  66 */     this(Method.MONOTONIC, Method.MONOTONIC, x1, x2, y);
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
/*     */   public BicubicInterpolator2(Method method1, Method method2, float[] x1, float[] x2, float[][] y) {
/*  81 */     this(method1, method2, x1.length, x2.length, x1, x2, y);
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
/*     */   public BicubicInterpolator2(Method method1, Method method2, int n1, int n2, float[] x1, float[] x2, float[][] y) {
/* 269 */     this._ks = new int[] { 0, 0 }; Check.argument(ArrayMath.isMonotonic(x1), "array x1 is monotonic"); Check.argument(ArrayMath.isMonotonic(x2), "array x2 is monotonic"); this._n1 = n1; this._n2 = n2; this._x1 = ArrayMath.copy(n1, x1); this._x2 = ArrayMath.copy(n2, x2); this._a = makeCoefficients(method1, method2, n1, n2, x1, x2, y);
/*     */   }
/*     */   public float interpolate(float x1, float x2) { return interpolate00(x1, x2); }
/* 272 */   public float interpolate00(float x1, float x2) { return interpolate00(x1, x2, this._ks); } public float interpolate10(float x1, float x2) { return interpolate10(x1, x2, this._ks); } public float interpolate01(float x1, float x2) { return interpolate01(x1, x2, this._ks); } public float[][] interpolate(Sampling s1, Sampling s2) { return interpolate00(s1, s2); } public float[][] interpolate00(Sampling s1, Sampling s2) { int n1 = s1.getCount(); int n2 = s2.getCount(); float[][] y = new float[n2][n1]; interpolate00(s1, s2, y); return y; } private static int index(float x, float[] xs, int i) { i = ArrayMath.binarySearch(xs, x, i);
/* 273 */     if (i < 0)
/* 274 */       i = (i < -1) ? (-2 - i) : 0; 
/* 275 */     if (i >= xs.length - 1)
/* 276 */       i = xs.length - 2; 
/* 277 */     return i; }
/*     */   public void interpolate(Sampling s1, Sampling s2, float[][] y) { interpolate00(s1, s2, y); }
/*     */   public void interpolate00(Sampling s1, Sampling s2, float[][] y) { int n1 = s1.getCount(); int n2 = s2.getCount(); int[] k1 = makeIndices(s1, this._x1); int[] k2 = makeIndices(s2, this._x2); for (int i2 = 0; i2 < n2; i2++) { float x2 = (float)s2.getValue(i2); for (int i1 = 0; i1 < n1; i1++) { float x1 = (float)s1.getValue(i1); y[i2][i1] = interpolate00(x1, x2, k1[i1], k2[i2]); }  }  }
/*     */   public float[][] interpolate(float[] x1, float[] x2) { return interpolate00(x1, x2); }
/* 281 */   public float[][] interpolate00(float[] x1, float[] x2) { int n1 = x1.length; int n2 = x2.length; float[][] y = new float[n2][n1]; interpolate00(x1, x2, y); return y; } public void interpolate(float[] x1, float[] x2, float[][] y) { interpolate00(x1, x2, y); } public void interpolate00(float[] x1, float[] x2, float[][] y) { int n1 = x1.length; int n2 = x2.length; int[] k1 = makeIndices(x1, this._x1); int[] k2 = makeIndices(x2, this._x2); for (int i2 = 0; i2 < n2; i2++) { for (int i1 = 0; i1 < n1; i1++) y[i2][i1] = interpolate00(x1[i1], x2[i2], k1[i1], k2[i2]);  }  } private static void trace(String s) { System.out.println(s); } private void updateIndices(float x1, float x2, int[] ks) { ks[0] = index(x1, this._x1, ks[0]);
/* 282 */     ks[1] = index(x2, this._x2, ks[1]); }
/*     */ 
/*     */   
/*     */   private static int[] makeIndices(float[] xi, float[] xs) {
/* 286 */     int n = xi.length;
/* 287 */     int[] ki = new int[n];
/* 288 */     ki[0] = index(xi[0], xs, 0);
/* 289 */     for (int i = 1; i < n; i++)
/* 290 */       ki[i] = index(xi[i], xs, ki[i - 1]); 
/* 291 */     return ki;
/*     */   }
/*     */   private static int[] makeIndices(Sampling si, float[] xs) {
/* 294 */     int n = si.getCount();
/* 295 */     int[] ki = new int[n];
/* 296 */     ki[0] = index((float)si.getValue(0), xs, 0);
/* 297 */     for (int i = 1; i < n; i++)
/* 298 */       ki[i] = index((float)si.getValue(i), xs, ki[i - 1]); 
/* 299 */     return ki;
/*     */   }
/*     */   
/*     */   private float interpolate00(float x1, float x2, int[] ks) {
/* 303 */     updateIndices(x1, x2, ks);
/* 304 */     return interpolate00(x1, x2, ks[0], ks[1]);
/*     */   }
/*     */   private float interpolate10(float x1, float x2, int[] ks) {
/* 307 */     updateIndices(x1, x2, ks);
/* 308 */     return interpolate10(x1, x2, ks[0], ks[1]);
/*     */   }
/*     */   private float interpolate01(float x1, float x2, int[] ks) {
/* 311 */     updateIndices(x1, x2, ks);
/* 312 */     return interpolate01(x1, x2, ks[0], ks[1]);
/*     */   }
/*     */   
/*     */   private float interpolate00(float x1, float x2, int k1, int k2) {
/* 316 */     return eval00(this._a[k2][k1], x1 - this._x1[k1], x2 - this._x2[k2]);
/*     */   }
/*     */   private float interpolate10(float x1, float x2, int k1, int k2) {
/* 319 */     return eval10(this._a[k2][k1], x1 - this._x1[k1], x2 - this._x2[k2]);
/*     */   }
/*     */   private float interpolate01(float x1, float x2, int k1, int k2) {
/* 322 */     return eval01(this._a[k2][k1], x1 - this._x1[k1], x2 - this._x2[k2]);
/*     */   }
/*     */   
/*     */   private static float eval00(float[][] a, float d1, float d2) {
/* 326 */     return a[0][0] + d1 * (a[0][1] + d1 * (a[0][2] + d1 * a[0][3])) + d2 * (a[1][0] + d1 * (a[1][1] + d1 * (a[1][2] + d1 * a[1][3])) + d2 * (a[2][0] + d1 * (a[2][1] + d1 * (a[2][2] + d1 * a[2][3])) + d2 * (a[3][0] + d1 * (a[3][1] + d1 * (a[3][2] + d1 * a[3][3])))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static float eval10(float[][] a, float d1, float d2) {
/* 332 */     return a[0][1] + d2 * (a[1][1] + d2 * (a[2][1] + d2 * a[3][1])) + d1 * (2.0F * (a[0][2] + d2 * (a[1][2] + d2 * (a[2][2] + d2 * a[3][2]))) + d1 * 3.0F * (a[0][3] + d2 * (a[1][3] + d2 * (a[2][3] + d2 * a[3][3]))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static float eval01(float[][] a, float d1, float d2) {
/* 337 */     return a[1][0] + d1 * (a[1][1] + d1 * (a[1][2] + d1 * a[1][3])) + d2 * (2.0F * (a[2][0] + d1 * (a[2][1] + d1 * (a[2][2] + d1 * a[2][3]))) + d2 * 3.0F * (a[3][0] + d1 * (a[3][1] + d1 * (a[3][2] + d1 * a[3][3]))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 343 */   private static final float[][] AINV = new float[][] { { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F }, { 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F }, { -3.0F, 3.0F, 0.0F, 0.0F, -2.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F }, { 2.0F, -2.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F }, { 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F }, { 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F }, { 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.0F, 3.0F, 0.0F, 0.0F, -2.0F, -1.0F, 0.0F, 0.0F }, { 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.0F, -2.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F }, { -3.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -2.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F }, { 0.0F, 0.0F, 0.0F, 0.0F, -3.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -2.0F, 0.0F, -1.0F, 0.0F }, { 9.0F, -9.0F, -9.0F, 9.0F, 6.0F, 3.0F, -6.0F, -3.0F, 6.0F, -6.0F, 3.0F, -3.0F, 4.0F, 2.0F, 2.0F, 1.0F }, { -6.0F, 6.0F, 6.0F, -6.0F, -3.0F, -3.0F, 3.0F, 3.0F, -4.0F, 4.0F, -2.0F, 2.0F, -2.0F, -2.0F, -1.0F, -1.0F }, { 2.0F, 0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F }, { 0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F }, { -6.0F, 6.0F, 6.0F, -6.0F, -4.0F, -2.0F, 4.0F, 2.0F, -3.0F, 3.0F, -3.0F, 3.0F, -2.0F, -1.0F, -2.0F, -1.0F }, { 4.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, -2.0F, -2.0F, 2.0F, -2.0F, 2.0F, -2.0F, 1.0F, 1.0F, 1.0F, 1.0F } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[][] getA(float[] dxs, float[][] yds) {
/* 362 */     float d1 = dxs[1];
/* 363 */     float d2 = dxs[2];
/* 364 */     float[][] a = new float[4][4];
/* 365 */     for (int m2 = 0, i = 0; m2 < 4; m2++) {
/* 366 */       for (int m1 = 0; m1 < 4; m1++, i++) {
/* 367 */         float am = 0.0F;
/* 368 */         for (int jd = 0, j = 0; jd < 4; jd++) {
/* 369 */           for (int jp = 0; jp < 4; jp++, j++) {
/* 370 */             if (AINV[i][j] != 0.0F)
/* 371 */               am += AINV[i][j] * yds[jd][jp] * dxs[jd]; 
/*     */           } 
/*     */         } 
/* 374 */         am /= ArrayMath.pow(d1, m1) * ArrayMath.pow(d2, m2);
/* 375 */         a[m2][m1] = am;
/*     */       } 
/*     */     } 
/* 378 */     return a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[][][] makeDerivatives(Method method1, Method method2, int n1, int n2, float[] x1, float[] x2, float[][] y) {
/* 389 */     CubicInterpolator.Method cim1 = CubicInterpolator.Method.MONOTONIC;
/* 390 */     CubicInterpolator.Method cim2 = CubicInterpolator.Method.MONOTONIC;
/* 391 */     if (method1 == Method.SPLINE) cim1 = CubicInterpolator.Method.SPLINE; 
/* 392 */     if (method2 == Method.SPLINE) cim2 = CubicInterpolator.Method.SPLINE; 
/* 393 */     float[][] y00 = y;
/*     */ 
/*     */     
/* 396 */     float[][] y10 = new float[n2][n1];
/* 397 */     for (int i2 = 0; i2 < n2; i2++) {
/* 398 */       float[] ys = new float[n1];
/* 399 */       for (int k = 0; k < n1; k++)
/* 400 */         ys[k] = y[i2][k]; 
/* 401 */       CubicInterpolator ci = new CubicInterpolator(cim1, n1, x1, ys);
/* 402 */       for (int m = 0; m < n1; m++) {
/* 403 */         y10[i2][m] = ci.interpolate1(x1[m]);
/*     */       }
/*     */     } 
/*     */     
/* 407 */     float[][] y01 = new float[n2][n1];
/* 408 */     for (int i1 = 0; i1 < n1; i1++) {
/* 409 */       float[] ys = new float[n2];
/* 410 */       for (int k = 0; k < n2; k++)
/* 411 */         ys[k] = y[k][i1]; 
/* 412 */       CubicInterpolator ci = new CubicInterpolator(cim2, n2, x2, ys);
/* 413 */       for (int m = 0; m < n2; m++) {
/* 414 */         y01[m][i1] = ci.interpolate1(x2[m]);
/*     */       }
/*     */     } 
/*     */     
/* 418 */     float[][] y11 = new float[n2][n1];
/* 419 */     for (int j = 0; j < n2; j++) {
/* 420 */       float[] ys = new float[n1];
/* 421 */       for (int k = 0; k < n1; k++)
/* 422 */         ys[k] = y01[j][k]; 
/* 423 */       CubicInterpolator ci = new CubicInterpolator(cim1, n1, x1, ys);
/* 424 */       for (int m = 0; m < n1; m++)
/* 425 */         y11[j][m] = y11[j][m] + 0.5F * ci.interpolate1(x1[m]); 
/*     */     } 
/* 427 */     for (int i = 0; i < n1; i++) {
/* 428 */       float[] ys = new float[n2];
/* 429 */       for (int k = 0; k < n2; k++)
/* 430 */         ys[k] = y10[k][i]; 
/* 431 */       CubicInterpolator ci = new CubicInterpolator(cim2, n2, x2, ys);
/* 432 */       for (int m = 0; m < n2; m++) {
/* 433 */         y11[m][i] = y11[m][i] + 0.5F * ci.interpolate1(x2[m]);
/*     */       }
/*     */     } 
/* 436 */     return new float[][][] { y00, y10, y01, y11 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[][][][] makeCoefficients(Method method1, Method method2, int n1, int n2, float[] x1, float[] x2, float[][] y) {
/* 446 */     float[][][] yd = makeDerivatives(method1, method2, n1, n2, x1, x2, y);
/* 447 */     float[][] y00 = yd[0];
/* 448 */     float[][] y10 = yd[1];
/* 449 */     float[][] y01 = yd[2];
/* 450 */     float[][] y11 = yd[3];
/* 451 */     float[][][][] a = new float[n2 - 1][n1 - 1][4][4];
/* 452 */     for (int i2 = 0, j2 = 1; i2 < n2 - 1; i2++, j2++) {
/* 453 */       float dx2 = x2[j2] - x2[i2];
/* 454 */       for (int i1 = 0, j1 = 1; i1 < n1 - 1; i1++, j1++) {
/* 455 */         float dx1 = x1[j1] - x1[i1];
/* 456 */         float[] dxs = { 1.0F, dx1, dx2, dx1 * dx2 };
/* 457 */         float[][] yds = { { y00[i2][i1], y00[i2][j1], y00[j2][i1], y00[j2][j1] }, { y10[i2][i1], y10[i2][j1], y10[j2][i1], y10[j2][j1] }, { y01[i2][i1], y01[i2][j1], y01[j2][i1], y01[j2][j1] }, { y11[i2][i1], y11[i2][j1], y11[j2][i1], y11[j2][j1] } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 463 */         a[i2][i1] = getA(dxs, yds);
/*     */       } 
/*     */     } 
/* 466 */     return a;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/BicubicInterpolator2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */