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
/*     */ public class BilinearInterpolator2
/*     */ {
/*     */   private float[] _x1;
/*     */   private float[] _x2;
/*     */   private float[][] _a00;
/*     */   private float[][] _a10;
/*     */   private float[][] _a01;
/*     */   private float[][] _a11;
/*     */   private int[] _ks;
/*     */   
/*     */   public BilinearInterpolator2(float[] x1, float[] x2, float[][] y) {
/*  47 */     this(x1.length, x2.length, x1, x2, y);
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
/*     */   public BilinearInterpolator2(int n1, int n2, float[] x1, float[] x2, float[][] y) {
/* 229 */     this._ks = new int[] { 0, 0 }; Check.argument(ArrayMath.isMonotonic(x1), "array x1 is monotonic"); Check.argument(ArrayMath.isMonotonic(x2), "array x2 is monotonic"); this._x1 = ArrayMath.copy(n1, x1); this._x2 = ArrayMath.copy(n2, x2); makeCoefficients(this._x1, this._x2, y);
/*     */   }
/*     */   public float interpolate(float x1, float x2) { return interpolate00(x1, x2); }
/* 232 */   public float interpolate00(float x1, float x2) { return interpolate00(x1, x2, this._ks); } public float interpolate10(float x1, float x2) { return interpolate10(x1, x2, this._ks); } public float interpolate01(float x1, float x2) { return interpolate01(x1, x2, this._ks); } public float[][] interpolate(Sampling s1, Sampling s2) { return interpolate00(s1, s2); } public float[][] interpolate00(Sampling s1, Sampling s2) { int n1 = s1.getCount(); int n2 = s2.getCount(); float[][] y = new float[n2][n1]; interpolate00(s1, s2, y); return y; } private static int index(float x, float[] xs, int i) { i = ArrayMath.binarySearch(xs, x, i);
/* 233 */     if (i < 0)
/* 234 */       i = (i < -1) ? (-2 - i) : 0; 
/* 235 */     if (i >= xs.length - 1)
/* 236 */       i = xs.length - 2; 
/* 237 */     return i; }
/*     */   public void interpolate(Sampling s1, Sampling s2, float[][] y) { interpolate00(s1, s2, y); }
/*     */   public void interpolate00(Sampling s1, Sampling s2, float[][] y) { int n1 = s1.getCount(); int n2 = s2.getCount(); int[] k1 = makeIndices(s1, this._x1); int[] k2 = makeIndices(s2, this._x2); for (int i2 = 0; i2 < n2; i2++) { float x2 = (float)s2.getValue(i2); for (int i1 = 0; i1 < n1; i1++) { float x1 = (float)s1.getValue(i1); y[i2][i1] = interpolate00(x1, x2, k1[i1], k2[i2]); }  }  }
/*     */   public float[][] interpolate(float[] x1, float[] x2) { return interpolate00(x1, x2); }
/* 241 */   public float[][] interpolate00(float[] x1, float[] x2) { int n1 = x1.length; int n2 = x2.length; float[][] y = new float[n2][n1]; interpolate00(x1, x2, y); return y; } public void interpolate(float[] x1, float[] x2, float[][] y) { interpolate00(x1, x2, y); } public void interpolate00(float[] x1, float[] x2, float[][] y) { int n1 = x1.length; int n2 = x2.length; int[] k1 = makeIndices(x1, this._x1); int[] k2 = makeIndices(x2, this._x2); for (int i2 = 0; i2 < n2; i2++) { for (int i1 = 0; i1 < n1; i1++) y[i2][i1] = interpolate00(x1[i1], x2[i2], k1[i1], k2[i2]);  }  } private static void trace(String s) { System.out.println(s); } private void updateIndices(float x1, float x2, int[] ks) { ks[0] = index(x1, this._x1, ks[0]);
/* 242 */     ks[1] = index(x2, this._x2, ks[1]); }
/*     */ 
/*     */   
/*     */   private static int[] makeIndices(float[] xi, float[] xs) {
/* 246 */     int n = xi.length;
/* 247 */     int[] ki = new int[n];
/* 248 */     ki[0] = index(xi[0], xs, 0);
/* 249 */     for (int i = 1; i < n; i++)
/* 250 */       ki[i] = index(xi[i], xs, ki[i - 1]); 
/* 251 */     return ki;
/*     */   }
/*     */   
/*     */   private static int[] makeIndices(Sampling si, float[] xs) {
/* 255 */     int n = si.getCount();
/* 256 */     int[] ki = new int[n];
/* 257 */     ki[0] = index((float)si.getValue(0), xs, 0);
/* 258 */     for (int i = 1; i < n; i++)
/* 259 */       ki[i] = index((float)si.getValue(i), xs, ki[i - 1]); 
/* 260 */     return ki;
/*     */   }
/*     */   
/*     */   private float interpolate00(float x1, float x2, int[] ks) {
/* 264 */     updateIndices(x1, x2, ks);
/* 265 */     return interpolate00(x1, x2, ks[0], ks[1]);
/*     */   }
/*     */   private float interpolate10(float x1, float x2, int[] ks) {
/* 268 */     updateIndices(x1, x2, ks);
/* 269 */     return interpolate10(x1, x2, ks[0], ks[1]);
/*     */   }
/*     */   private float interpolate01(float x1, float x2, int[] ks) {
/* 272 */     updateIndices(x1, x2, ks);
/* 273 */     return interpolate01(x1, x2, ks[0], ks[1]);
/*     */   }
/*     */   
/*     */   private float interpolate00(float x1, float x2, int k1, int k2) {
/* 277 */     float d1 = x1 - this._x1[k1];
/* 278 */     float d2 = x2 - this._x2[k2];
/* 279 */     return this._a00[k2][k1] + d1 * this._a10[k2][k1] + d2 * (this._a01[k2][k1] + d1 * this._a11[k2][k1]);
/*     */   }
/*     */   
/*     */   private float interpolate10(float x1, float x2, int k1, int k2) {
/* 283 */     float d2 = x2 - this._x2[k2];
/* 284 */     return this._a10[k2][k1] + d2 * this._a11[k2][k1];
/*     */   }
/*     */   private float interpolate01(float x1, float x2, int k1, int k2) {
/* 287 */     float d1 = x1 - this._x1[k1];
/* 288 */     return this._a01[k2][k1] + d1 * this._a11[k2][k1];
/*     */   }
/*     */   
/*     */   private void makeCoefficients(float[] x1, float[] x2, float[][] y) {
/* 292 */     int n1 = x1.length;
/* 293 */     int n2 = x2.length;
/* 294 */     this._a00 = new float[n2 - 1][n1 - 1];
/* 295 */     this._a10 = new float[n2 - 1][n1 - 1];
/* 296 */     this._a01 = new float[n2 - 1][n1 - 1];
/* 297 */     this._a11 = new float[n2 - 1][n1 - 1];
/* 298 */     for (int k2 = 0; k2 < n2 - 1; k2++) {
/* 299 */       float d2 = x2[k2 + 1] - x2[k2];
/* 300 */       for (int k1 = 0; k1 < n1 - 1; k1++) {
/* 301 */         float d1 = x1[k1 + 1] - x1[k1];
/* 302 */         float y00 = y[k2][k1];
/* 303 */         float y10 = y[k2][k1 + 1];
/* 304 */         float y01 = y[k2 + 1][k1];
/* 305 */         float y11 = y[k2 + 1][k1 + 1];
/* 306 */         this._a00[k2][k1] = y00;
/* 307 */         this._a10[k2][k1] = (y10 - y00) / d1;
/* 308 */         this._a01[k2][k1] = (y01 - y00) / d2;
/* 309 */         this._a11[k2][k1] = (y00 - y10 + y11 - y01) / d1 * d2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/BilinearInterpolator2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */