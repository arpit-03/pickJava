/*      */ package edu.mines.jtk.dsp;
/*      */ 
/*      */ import edu.mines.jtk.util.ArrayMath;
/*      */ import edu.mines.jtk.util.Check;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LocalCorrelationFilter
/*      */ {
/*      */   private Window _window;
/*      */   private Type _type;
/*      */   private double _sigma1;
/*      */   private double _sigma2;
/*      */   private double _sigma3;
/*      */   private Filter _f1;
/*      */   private Filter _f2;
/*      */   private Filter _f3;
/*      */   private int _dimension;
/*      */   private int _n1;
/*      */   private int _n2;
/*      */   private int _n3;
/*      */   private float[][][] _f;
/*      */   private float[][][] _g;
/*      */   private float[][][][] _s;
/*      */   
/*      */   public enum Type
/*      */   {
/*   63 */     SIMPLE,
/*   64 */     SYMMETRIC;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Window
/*      */   {
/*   71 */     GAUSSIAN,
/*   72 */     RECTANGLE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalCorrelationFilter(Type type, Window window, double sigma) {
/*   84 */     this(type, window, sigma, sigma, sigma);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalCorrelationFilter(Type type, Window window, double sigma1, double sigma2) {
/*  102 */     this(type, window, sigma1, sigma2, sigma2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalCorrelationFilter(Type type, Window window, double sigma1, double sigma2, double sigma3) {
/*  438 */     this._window = Window.GAUSSIAN;
/*  439 */     this._type = Type.SYMMETRIC; Check.argument((sigma1 >= 1.0D), "sigma1>=1.0"); Check.argument((sigma2 >= 1.0D), "sigma2>=1.0"); Check.argument((sigma3 >= 1.0D), "sigma3>=1.0"); this._type = type; this._window = window; this._sigma1 = sigma1; this._sigma2 = sigma2; this._sigma3 = sigma3; if (window == Window.GAUSSIAN) { this._f1 = new GaussianFilter(sigma1); this._f2 = new GaussianFilter(sigma2); this._f3 = new GaussianFilter(sigma3); } else { this._f1 = new RectangleFilter(sigma1); this._f2 = new RectangleFilter(sigma2); this._f3 = new RectangleFilter(sigma3); } 
/*      */   }
/*      */   public void setInputs(float[] f, float[] g) { if (f == null || g == null) { this._dimension = this._n1 = this._n2 = this._n3 = 0; this._f = (float[][][])null; this._g = (float[][][])null; } else { Check.argument((f.length == g.length), "f.length==g.length"); this._dimension = 1; this._n1 = f.length; this._n2 = this._n3 = 0; this._f = new float[1][1][]; this._g = new float[1][1][]; this._f[0][0] = f; this._g[0][0] = g; }  this._s = (float[][][][])null; }
/*      */   public void setInputs(float[][] f, float[][] g) { if (f == null || g == null) { this._dimension = this._n1 = this._n2 = this._n3 = 0; this._f = (float[][][])null; this._g = (float[][][])null; } else { Check.argument(((f[0]).length == (g[0]).length), "f[0].length==g[0].length"); Check.argument((f.length == g.length), "f.length==g.length"); Check.argument(ArrayMath.isRegular(f), "f is regular"); Check.argument(ArrayMath.isRegular(g), "g is regular"); this._dimension = 2; this._n1 = (f[0]).length; this._n2 = f.length; this._n3 = 0; this._f = new float[1][][]; this._g = new float[1][][]; this._f[0] = f; this._g[0] = g; }
/*      */      this._s = (float[][][][])null; }
/*      */   public void setInputs(float[][][] f, float[][][] g) { if (f == null || g == null) { this._dimension = this._n1 = this._n2 = this._n3 = 0; this._f = (float[][][])null; this._g = (float[][][])null; }
/*      */     else { Check.argument(((f[0][0]).length == (g[0][0]).length), "f[0][0].length==g[0][0].length"); Check.argument(((f[0]).length == (g[0]).length), "f[0].length==g[0].length"); Check.argument((f.length == g.length), "f.length==g.length"); Check.argument(ArrayMath.isRegular(f), "f is regular"); Check.argument(ArrayMath.isRegular(g), "g is regular"); this._dimension = 3; this._n1 = (f[0][0]).length; this._n2 = (f[0]).length; this._n3 = f.length; this._f = f; this._g = g; }
/*      */      this._s = (float[][][][])null; }
/*      */   public void correlate(int lag, float[] c) { checkDimensions(c); correlate(lag, this._f[0][0], this._g[0][0], c); }
/*  448 */   public void correlate(int lag1, int lag2, float[][] c) { checkDimensions(c); correlate(lag1, lag2, this._f[0], this._g[0], c); } public void correlate(int lag1, int lag2, int lag3, float[][][] c) { checkDimensions(c); correlate(lag1, lag2, lag3, this._f, this._g, c); } private static float S1 = 0.615728F;
/*  449 */   public void normalize(int lag, float[] c) { checkDimensions(c); if (this._s == null) updateNormalize();  int n1 = this._n1; int l1 = lag; if (this._type == Type.SIMPLE) { float[] sf = this._s[0][0][0]; float[] sg = this._s[1][0][0]; int i1min = ArrayMath.max(0, -l1); int i1max = ArrayMath.min(n1, n1 - l1); int i1; for (i1 = 0; i1 < i1min; i1++) c[i1] = c[i1] * sf[i1] * sg[0];  for (i1 = i1min; i1 < i1max; i1++) c[i1] = c[i1] * sf[i1] * sg[i1 + l1];  for (i1 = i1max; i1 < n1; i1++) c[i1] = c[i1] * sf[i1] * sg[n1 - 1];  } else if (this._type == Type.SYMMETRIC) { float[] s = this._s[0][0][0]; for (int i1 = 0; i1 < n1; i1++) c[i1] = c[i1] * s[i1];  }  } public void normalize(int lag1, int lag2, float[][] c) { checkDimensions(c); if (this._s == null) updateNormalize();  int n1 = this._n1; int n2 = this._n2; int l1 = lag1; int l2 = lag2; if (this._type == Type.SIMPLE) { float[][] sf = this._s[0][0]; float[][] sg = this._s[1][0]; int i1min = ArrayMath.max(0, -l1); int i1max = ArrayMath.min(n1, n1 - l1); for (int i2 = 0; i2 < n2; i2++) { float[] c2 = c[i2]; float[] sf2 = sf[i2]; float[] sg2 = sg[ArrayMath.max(0, ArrayMath.min(n2 - 1, i2 + l2))]; int i1; for (i1 = 0; i1 < i1min; i1++) c2[i1] = c2[i1] * sf2[i1] * sg2[0];  for (i1 = i1min; i1 < i1max; i1++) c2[i1] = c2[i1] * sf2[i1] * sg2[i1 + l1];  for (i1 = i1max; i1 < n1; i1++) c2[i1] = c2[i1] * sf2[i1] * sg2[n1 - 1];  }  } else if (this._type == Type.SYMMETRIC) { float[][] s = this._s[0][0]; for (int i2 = 0; i2 < n2; i2++) { float[] c2 = c[i2]; float[] s2 = s[i2]; for (int i1 = 0; i1 < n1; i1++) c2[i1] = c2[i1] * s2[i1];  }  }  } public void normalize(int lag1, int lag2, int lag3, float[][][] c) { checkDimensions(c); if (this._s == null) updateNormalize();  int n1 = this._n1; int n2 = this._n2; int n3 = this._n3; int l1 = lag1; int l2 = lag2; int l3 = lag3; if (this._type == Type.SIMPLE) { float[][][] sf = this._s[0]; float[][][] sg = this._s[1]; int i1min = ArrayMath.max(0, -l1); int i1max = ArrayMath.min(n1, n1 - l1); for (int i3 = 0; i3 < n3; i3++) { float[][] c3 = c[i3]; float[][] sf3 = sf[i3]; float[][] sg3 = sg[ArrayMath.max(0, ArrayMath.min(n3 - 1, i3 + l3))]; for (int i2 = 0; i2 < n2; i2++) { float[] c32 = c3[i2]; float[] sf32 = sf3[i2]; float[] sg32 = sg3[ArrayMath.max(0, ArrayMath.min(n2 - 1, i2 + l2))]; int i1; for (i1 = 0; i1 < i1min; i1++) c32[i1] = c32[i1] * sf32[i1] * sg32[0];  for (i1 = i1min; i1 < i1max; i1++) c32[i1] = c32[i1] * sf32[i1] * sg32[i1 + l1];  for (i1 = i1max; i1 < n1; i1++) c32[i1] = c32[i1] * sf32[i1] * sg32[n1 - 1];  }  }  } else if (this._type == Type.SYMMETRIC) { float[][][] s = this._s[0]; for (int i3 = 0; i3 < n3; i3++) { float[][] c3 = c[i3]; float[][] s3 = s[i3]; for (int i2 = 0; i2 < n2; i2++) { float[] c32 = c3[i2]; float[] s32 = s3[i2]; for (int i1 = 0; i1 < n1; i1++) c32[i1] = c32[i1] * s32[i1];  }  }  }  } public float[] unbias(float[] f) { int n1 = f.length; float[] t = new float[n1]; this._f1.apply(f, t); ArrayMath.sub(f, t, t); return t; } public float[][] unbias(float[][] f) { int n1 = (f[0]).length; int n2 = f.length; float[][] t = new float[n2][n1]; this._f1.apply1(f, t); this._f2.apply2(t, t); ArrayMath.sub(f, t, t); return t; } public float[][][] unbias(float[][][] f) { int n1 = (f[0][0]).length; int n2 = (f[0]).length; int n3 = f.length; float[][][] t = new float[n3][n2][n1]; this._f1.apply1(f, t); this._f2.apply2(t, t); this._f3.apply3(t, t); ArrayMath.sub(f, t, t); return t; } private static float S2 = -0.1558022F;
/*  450 */   private static float S3 = 0.0509014F;
/*  451 */   private static float S4 = -0.0115417F;
/*  452 */   private static float[] S = new float[] { S4, S3, S2, S1, S1, S2, S3, S4 };
/*      */   
/*      */   private void correlate(int lag, float[] f, float[] g, float[] c) {
/*  455 */     Check.argument((f != c), "f!=c");
/*  456 */     Check.argument((g != c), "g!=c");
/*  457 */     int n1 = f.length;
/*  458 */     int l1 = lag;
/*      */ 
/*      */     
/*  461 */     int l1f = 0;
/*  462 */     int l1g = l1;
/*      */ 
/*      */     
/*  465 */     if (this._type == Type.SYMMETRIC) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  470 */       l1f = (l1 >= 0) ? (l1 / 2) : ((l1 - 1) / 2);
/*  471 */       l1g = (l1 >= 0) ? ((l1 + 1) / 2) : (l1 / 2);
/*      */     } 
/*      */ 
/*      */     
/*  475 */     double scale1 = 1.0D;
/*  476 */     if (this._window == Window.GAUSSIAN) {
/*  477 */       scale1 *= ArrayMath.sqrt(6.283185307179586D) * this._sigma1;
/*      */     } else {
/*  479 */       scale1 *= 1.0D + 2.0D * this._sigma1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  487 */     if (this._type == Type.SYMMETRIC) {
/*  488 */       if (this._window == Window.GAUSSIAN) {
/*  489 */         scale1 *= ArrayMath.exp(-0.125D * l1 * l1 / this._sigma1 * this._sigma1);
/*      */       } else {
/*  491 */         scale1 *= ArrayMath.max(0.0D, 1.0D + 2.0D * this._sigma1 - ArrayMath.abs(l1)) / (1.0D + 2.0D * this._sigma1);
/*      */       } 
/*      */     }
/*  494 */     float scale = (float)scale1;
/*      */ 
/*      */     
/*  497 */     float[] h = new float[n1];
/*  498 */     int i1min = ArrayMath.max(0, l1f, -l1g);
/*  499 */     int i1max = ArrayMath.min(n1, n1 + l1f, n1 - l1g);
/*  500 */     for (int i1 = i1min; i1 < i1max; i1++) {
/*  501 */       h[i1] = scale * f[i1 - l1f] * g[i1 + l1g];
/*      */     }
/*      */ 
/*      */     
/*  505 */     if (this._window == Window.GAUSSIAN && this._type == Type.SYMMETRIC && 
/*  506 */       l1f != l1g) {
/*  507 */       shift(h, c);
/*  508 */       ArrayMath.copy(c, h);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  515 */     Filter f1 = this._f1;
/*  516 */     if (this._window == Window.RECTANGLE && this._type == Type.SYMMETRIC)
/*  517 */       f1 = new RectangleFilter(this._sigma1, l1); 
/*  518 */     f1.apply(h, c);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void correlate(int lag1, int lag2, float[][] f, float[][] g, float[][] c) {
/*  524 */     Check.argument((f != c), "f!=c");
/*  525 */     Check.argument((g != c), "g!=c");
/*  526 */     int n1 = (f[0]).length;
/*  527 */     int n2 = f.length;
/*  528 */     int l1 = lag1;
/*  529 */     int l2 = lag2;
/*      */ 
/*      */     
/*  532 */     int l1f = 0;
/*  533 */     int l1g = l1;
/*  534 */     int l2f = 0;
/*  535 */     int l2g = l2;
/*      */ 
/*      */     
/*  538 */     if (this._type == Type.SYMMETRIC) {
/*  539 */       l1f = (l1 >= 0) ? (l1 / 2) : ((l1 - 1) / 2);
/*  540 */       l1g = (l1 >= 0) ? ((l1 + 1) / 2) : (l1 / 2);
/*  541 */       l2f = (l2 >= 0) ? (l2 / 2) : ((l2 - 1) / 2);
/*  542 */       l2g = (l2 >= 0) ? ((l2 + 1) / 2) : (l2 / 2);
/*      */     } 
/*      */ 
/*      */     
/*  546 */     double scale1 = 1.0D;
/*  547 */     double scale2 = 1.0D;
/*  548 */     if (this._window == Window.GAUSSIAN) {
/*  549 */       scale1 *= ArrayMath.sqrt(6.283185307179586D) * this._sigma1;
/*  550 */       scale2 *= ArrayMath.sqrt(6.283185307179586D) * this._sigma2;
/*      */     } else {
/*  552 */       scale1 *= 1.0D + 2.0D * this._sigma1;
/*  553 */       scale2 *= 1.0D + 2.0D * this._sigma2;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  561 */     if (this._type == Type.SYMMETRIC) {
/*  562 */       if (this._window == Window.GAUSSIAN) {
/*  563 */         scale1 *= ArrayMath.exp(-0.125D * l1 * l1 / this._sigma1 * this._sigma1);
/*  564 */         scale2 *= ArrayMath.exp(-0.125D * l2 * l2 / this._sigma2 * this._sigma2);
/*      */       } else {
/*  566 */         scale1 *= ArrayMath.max(0.0D, 1.0D + 2.0D * this._sigma1 - ArrayMath.abs(l1)) / (1.0D + 2.0D * this._sigma1);
/*  567 */         scale2 *= ArrayMath.max(0.0D, 1.0D + 2.0D * this._sigma2 - ArrayMath.abs(l2)) / (1.0D + 2.0D * this._sigma2);
/*      */       } 
/*      */     }
/*  570 */     float scale = (float)(scale1 * scale2);
/*      */ 
/*      */     
/*  573 */     float[][] h = new float[n2][n1];
/*  574 */     int i1min = ArrayMath.max(0, l1f, -l1g);
/*  575 */     int i1max = ArrayMath.min(n1, n1 + l1f, n1 - l1g);
/*  576 */     int i2min = ArrayMath.max(0, l2f, -l2g);
/*  577 */     int i2max = ArrayMath.min(n2, n2 + l2f, n2 - l2g);
/*  578 */     for (int i2 = i2min; i2 < i2max; i2++) {
/*  579 */       float[] arrayOfFloat1 = f[i2 - l2f];
/*  580 */       float[] g2 = g[i2 + l2g];
/*  581 */       float[] h2 = h[i2];
/*  582 */       for (int i1 = i1min; i1 < i1max; i1++) {
/*  583 */         h2[i1] = scale * arrayOfFloat1[i1 - l1f] * g2[i1 + l1g];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  588 */     if (this._window == Window.GAUSSIAN && this._type == Type.SYMMETRIC) {
/*  589 */       if (l1f != l1g) {
/*  590 */         shift1(h, c);
/*  591 */         ArrayMath.copy(c, h);
/*      */       } 
/*  593 */       if (l2f != l2g) {
/*  594 */         shift2(h, c);
/*  595 */         ArrayMath.copy(c, h);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  602 */     Filter f1 = this._f1;
/*  603 */     Filter f2 = this._f2;
/*  604 */     if (this._window == Window.RECTANGLE && this._type == Type.SYMMETRIC) {
/*  605 */       f1 = new RectangleFilter(this._sigma1, l1);
/*  606 */       f2 = new RectangleFilter(this._sigma2, l2);
/*      */     } 
/*  608 */     f1.apply1(h, c);
/*  609 */     ArrayMath.copy(c, h);
/*  610 */     f2.apply2(h, c);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void correlate(int lag1, int lag2, int lag3, float[][][] f, float[][][] g, float[][][] c) {
/*  616 */     Check.argument((f != c), "f!=c");
/*  617 */     Check.argument((g != c), "g!=c");
/*  618 */     int n1 = (f[0][0]).length;
/*  619 */     int n2 = (f[0]).length;
/*  620 */     int n3 = f.length;
/*  621 */     int l1 = lag1;
/*  622 */     int l2 = lag2;
/*  623 */     int l3 = lag3;
/*      */ 
/*      */     
/*  626 */     int l1f = 0;
/*  627 */     int l1g = l1;
/*  628 */     int l2f = 0;
/*  629 */     int l2g = l2;
/*  630 */     int l3f = 0;
/*  631 */     int l3g = l3;
/*      */ 
/*      */     
/*  634 */     if (this._type == Type.SYMMETRIC) {
/*  635 */       l1f = (l1 >= 0) ? (l1 / 2) : ((l1 - 1) / 2);
/*  636 */       l1g = (l1 >= 0) ? ((l1 + 1) / 2) : (l1 / 2);
/*  637 */       l2f = (l2 >= 0) ? (l2 / 2) : ((l2 - 1) / 2);
/*  638 */       l2g = (l2 >= 0) ? ((l2 + 1) / 2) : (l2 / 2);
/*  639 */       l3f = (l3 >= 0) ? (l3 / 2) : ((l3 - 1) / 2);
/*  640 */       l3g = (l3 >= 0) ? ((l3 + 1) / 2) : (l3 / 2);
/*      */     } 
/*      */ 
/*      */     
/*  644 */     double scale1 = 1.0D;
/*  645 */     double scale2 = 1.0D;
/*  646 */     double scale3 = 1.0D;
/*  647 */     if (this._window == Window.GAUSSIAN) {
/*  648 */       scale1 *= ArrayMath.sqrt(6.283185307179586D) * this._sigma1;
/*  649 */       scale2 *= ArrayMath.sqrt(6.283185307179586D) * this._sigma2;
/*  650 */       scale3 *= ArrayMath.sqrt(6.283185307179586D) * this._sigma3;
/*      */     } else {
/*  652 */       scale1 *= 1.0D + 2.0D * this._sigma1;
/*  653 */       scale2 *= 1.0D + 2.0D * this._sigma2;
/*  654 */       scale3 *= 1.0D + 2.0D * this._sigma3;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  662 */     if (this._type == Type.SYMMETRIC) {
/*  663 */       if (this._window == Window.GAUSSIAN) {
/*  664 */         scale1 *= ArrayMath.exp(-0.125D * l1 * l1 / this._sigma1 * this._sigma1);
/*  665 */         scale2 *= ArrayMath.exp(-0.125D * l2 * l2 / this._sigma2 * this._sigma2);
/*  666 */         scale3 *= ArrayMath.exp(-0.125D * l3 * l3 / this._sigma3 * this._sigma3);
/*      */       } else {
/*  668 */         scale1 *= ArrayMath.max(0.0D, 1.0D + 2.0D * this._sigma1 - ArrayMath.abs(l1)) / (1.0D + 2.0D * this._sigma1);
/*  669 */         scale2 *= ArrayMath.max(0.0D, 1.0D + 2.0D * this._sigma2 - ArrayMath.abs(l2)) / (1.0D + 2.0D * this._sigma2);
/*  670 */         scale3 *= ArrayMath.max(0.0D, 1.0D + 2.0D * this._sigma3 - ArrayMath.abs(l3)) / (1.0D + 2.0D * this._sigma3);
/*      */       } 
/*      */     }
/*  673 */     float scale = (float)(scale1 * scale2 * scale3);
/*      */ 
/*      */     
/*  676 */     float[][][] h = new float[n3][n2][n1];
/*  677 */     int i1min = ArrayMath.max(0, l1f, -l1g);
/*  678 */     int i1max = ArrayMath.min(n1, n1 + l1f, n1 - l1g);
/*  679 */     int i2min = ArrayMath.max(0, l2f, -l2g);
/*  680 */     int i2max = ArrayMath.min(n2, n2 + l2f, n2 - l2g);
/*  681 */     int i3min = ArrayMath.max(0, l3f, -l3g);
/*  682 */     int i3max = ArrayMath.min(n3, n3 + l3f, n3 - l3g);
/*  683 */     for (int i3 = i3min; i3 < i3max; i3++) {
/*  684 */       float[][] arrayOfFloat1 = f[i3 - l3f];
/*  685 */       float[][] g3 = g[i3 + l3g];
/*  686 */       float[][] h3 = h[i3];
/*  687 */       for (int i2 = i2min; i2 < i2max; i2++) {
/*  688 */         float[] f32 = arrayOfFloat1[i2 - l2f];
/*  689 */         float[] g32 = g3[i2 + l2g];
/*  690 */         float[] h32 = h3[i2];
/*  691 */         for (int i1 = i1min; i1 < i1max; i1++) {
/*  692 */           h32[i1] = scale * f32[i1 - l1f] * g32[i1 + l1g];
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  698 */     if (this._window == Window.GAUSSIAN && this._type == Type.SYMMETRIC) {
/*  699 */       if (l1f != l1g) {
/*  700 */         shift1(h, c);
/*  701 */         ArrayMath.copy(c, h);
/*      */       } 
/*  703 */       if (l2f != l2g) {
/*  704 */         shift2(h, c);
/*  705 */         ArrayMath.copy(c, h);
/*      */       } 
/*  707 */       if (l3f != l3g) {
/*  708 */         shift3(h, c);
/*  709 */         ArrayMath.copy(c, h);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  716 */     Filter f1 = this._f1;
/*  717 */     Filter f2 = this._f2;
/*  718 */     Filter f3 = this._f3;
/*  719 */     if (this._window == Window.RECTANGLE && this._type == Type.SYMMETRIC) {
/*  720 */       f1 = new RectangleFilter(this._sigma1, l1);
/*  721 */       f2 = new RectangleFilter(this._sigma2, l2);
/*  722 */       f3 = new RectangleFilter(this._sigma3, l3);
/*      */     } 
/*  724 */     f1.apply1(h, c);
/*  725 */     ArrayMath.copy(c, h);
/*  726 */     f2.apply2(h, c);
/*  727 */     ArrayMath.copy(c, h);
/*  728 */     f3.apply3(h, c);
/*      */   }
/*      */   
/*      */   private void updateNormalize() {
/*  732 */     if (this._dimension == 0)
/*      */       return; 
/*  734 */     int ns = (this._type == Type.SIMPLE) ? 2 : 1;
/*  735 */     int n1 = ArrayMath.max(1, this._n1);
/*  736 */     int n2 = ArrayMath.max(1, this._n2);
/*  737 */     int n3 = ArrayMath.max(1, this._n3);
/*  738 */     this._s = new float[ns][n3][n2][n1];
/*  739 */     if (this._type == Type.SIMPLE) {
/*  740 */       if (this._dimension == 1) {
/*  741 */         float[] f = this._f[0][0];
/*  742 */         float[] g = this._g[0][0];
/*  743 */         float[] sf = this._s[0][0][0];
/*  744 */         float[] sg = this._s[1][0][0];
/*  745 */         correlate(0, f, f, sf);
/*  746 */         correlate(0, g, g, sg);
/*  747 */         ArrayMath.sqrt(sf, sf);
/*  748 */         ArrayMath.sqrt(sg, sg);
/*  749 */         ArrayMath.div(1.0F, sf, sf);
/*  750 */         ArrayMath.div(1.0F, sg, sg);
/*  751 */       } else if (this._dimension == 2) {
/*  752 */         float[][] f = this._f[0];
/*  753 */         float[][] g = this._g[0];
/*  754 */         float[][] sf = this._s[0][0];
/*  755 */         float[][] sg = this._s[1][0];
/*  756 */         correlate(0, 0, f, f, sf);
/*  757 */         correlate(0, 0, g, g, sg);
/*  758 */         ArrayMath.sqrt(sf, sf);
/*  759 */         ArrayMath.sqrt(sg, sg);
/*  760 */         ArrayMath.div(1.0F, sf, sf);
/*  761 */         ArrayMath.div(1.0F, sg, sg);
/*      */       } else {
/*  763 */         float[][][] f = this._f;
/*  764 */         float[][][] g = this._g;
/*  765 */         float[][][] sf = this._s[0];
/*  766 */         float[][][] sg = this._s[1];
/*  767 */         correlate(0, 0, 0, f, f, sf);
/*  768 */         correlate(0, 0, 0, g, g, sg);
/*  769 */         ArrayMath.sqrt(sf, sf);
/*  770 */         ArrayMath.sqrt(sg, sg);
/*  771 */         ArrayMath.div(1.0F, sf, sf);
/*  772 */         ArrayMath.div(1.0F, sg, sg);
/*      */       }
/*      */     
/*  775 */     } else if (this._dimension == 1) {
/*  776 */       float[] f = this._f[0][0];
/*  777 */       float[] g = this._g[0][0];
/*  778 */       float[] s = this._s[0][0][0];
/*  779 */       float[] sf = s;
/*  780 */       float[] sg = new float[this._n1];
/*  781 */       correlate(0, f, f, sf);
/*  782 */       correlate(0, g, g, sg);
/*  783 */       ArrayMath.mul(sf, sg, s);
/*  784 */       ArrayMath.sqrt(s, s);
/*  785 */       ArrayMath.div(1.0F, s, s);
/*  786 */     } else if (this._dimension == 2) {
/*  787 */       float[][] f = this._f[0];
/*  788 */       float[][] g = this._g[0];
/*  789 */       float[][] s = this._s[0][0];
/*  790 */       float[][] sf = s;
/*  791 */       float[][] sg = new float[this._n2][this._n1];
/*  792 */       correlate(0, 0, f, f, sf);
/*  793 */       correlate(0, 0, g, g, sg);
/*  794 */       ArrayMath.mul(sf, sg, s);
/*  795 */       ArrayMath.sqrt(s, s);
/*  796 */       ArrayMath.div(1.0F, s, s);
/*      */     } else {
/*  798 */       float[][][] f = this._f;
/*  799 */       float[][][] g = this._g;
/*  800 */       float[][][] s = this._s[0];
/*  801 */       float[][][] sf = s;
/*  802 */       float[][][] sg = new float[this._n3][this._n2][this._n1];
/*  803 */       correlate(0, 0, 0, f, f, sf);
/*  804 */       correlate(0, 0, 0, g, g, sg);
/*  805 */       ArrayMath.mul(sf, sg, s);
/*  806 */       ArrayMath.sqrt(s, s);
/*  807 */       ArrayMath.div(1.0F, s, s);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void shift(float[] f, float[] g) {
/*  813 */     int n1 = f.length;
/*      */ 
/*      */ 
/*      */     
/*  817 */     int i1b = 0;
/*  818 */     int i1e = ArrayMath.min(4, n1); int i1;
/*  819 */     for (i1 = i1b; i1 < i1e; i1++) {
/*  820 */       int ib = ArrayMath.max(0, 4 - i1);
/*  821 */       int ie = ArrayMath.min(8, 4 - i1 + n1);
/*  822 */       g[i1] = 0.0F;
/*  823 */       for (int i = ib; i < ie; i++) {
/*  824 */         g[i1] = g[i1] + S[i] * f[i1 + i - 4];
/*      */       }
/*      */     } 
/*      */     
/*  828 */     i1b = 4;
/*  829 */     i1e = n1 - 3;
/*  830 */     for (i1 = i1b; i1 < i1e; i1++) {
/*  831 */       g[i1] = S4 * (f[i1 - 4] + f[i1 + 3]) + S3 * (f[i1 - 3] + f[i1 + 2]) + S2 * (f[i1 - 2] + f[i1 + 1]) + S1 * (f[i1 - 1] + f[i1]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  838 */     i1b = ArrayMath.max(0, n1 - 3);
/*  839 */     i1e = n1;
/*  840 */     for (i1 = i1b; i1 < i1e; i1++) {
/*  841 */       int ib = ArrayMath.max(0, 4 - i1);
/*  842 */       int ie = ArrayMath.min(8, 4 - i1 + n1);
/*  843 */       g[i1] = 0.0F;
/*  844 */       for (int i = ib; i < ie; i++)
/*  845 */         g[i1] = g[i1] + S[i] * f[i1 + i - 4]; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void shift1(float[][] f, float[][] g) {
/*  850 */     int n2 = f.length;
/*  851 */     for (int i2 = 0; i2 < n2; i2++)
/*  852 */       shift(f[i2], g[i2]); 
/*      */   }
/*      */   
/*      */   private static void shift2(float[][] f, float[][] g) {
/*  856 */     int n2 = f.length;
/*  857 */     int n1 = (f[0]).length;
/*      */ 
/*      */ 
/*      */     
/*  861 */     int i2b = 0;
/*  862 */     int i2e = ArrayMath.min(4, n2); int i2;
/*  863 */     for (i2 = i2b; i2 < i2e; i2++) {
/*  864 */       int ib = ArrayMath.max(0, 4 - i2);
/*  865 */       int ie = ArrayMath.min(8, 4 - i2 + n2);
/*  866 */       for (int i1 = 0; i1 < n1; i1++)
/*  867 */         g[i2][i1] = 0.0F; 
/*  868 */       for (int i = ib; i < ie; i++) {
/*  869 */         for (int j = 0; j < n1; j++) {
/*  870 */           g[i2][j] = g[i2][j] + S[i] * f[i2 + i - 4][j];
/*      */         }
/*      */       } 
/*      */     } 
/*  874 */     i2b = 4;
/*  875 */     i2e = n2 - 3;
/*  876 */     for (i2 = i2b; i2 < i2e; i2++) {
/*  877 */       float[] g2 = g[i2];
/*  878 */       float[] fm4 = f[i2 - 4];
/*  879 */       float[] fm3 = f[i2 - 3];
/*  880 */       float[] fm2 = f[i2 - 2];
/*  881 */       float[] fm1 = f[i2 - 1];
/*  882 */       float[] fp0 = f[i2];
/*  883 */       float[] fp1 = f[i2 + 1];
/*  884 */       float[] fp2 = f[i2 + 2];
/*  885 */       float[] fp3 = f[i2 + 3];
/*  886 */       for (int i1 = 0; i1 < n1; i1++) {
/*  887 */         g2[i1] = S4 * (fm4[i1] + fp3[i1]) + S3 * (fm3[i1] + fp2[i1]) + S2 * (fm2[i1] + fp1[i1]) + S1 * (fm1[i1] + fp0[i1]);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  894 */     i2b = ArrayMath.max(0, n2 - 3);
/*  895 */     i2e = n2;
/*  896 */     for (i2 = i2b; i2 < i2e; i2++) {
/*  897 */       int ib = ArrayMath.max(0, 4 - i2);
/*  898 */       int ie = ArrayMath.min(8, 4 - i2 + n2);
/*  899 */       for (int i1 = 0; i1 < n1; i1++)
/*  900 */         g[i2][i1] = 0.0F; 
/*  901 */       for (int i = ib; i < ie; i++) {
/*  902 */         for (int j = 0; j < n1; j++)
/*  903 */           g[i2][j] = g[i2][j] + S[i] * f[i2 + i - 4][j]; 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void shift1(float[][][] f, float[][][] g) {
/*  908 */     int n3 = f.length;
/*  909 */     for (int i3 = 0; i3 < n3; i3++)
/*  910 */       shift1(f[i3], g[i3]); 
/*      */   }
/*      */   
/*      */   private static void shift2(float[][][] f, float[][][] g) {
/*  914 */     int n3 = f.length;
/*  915 */     for (int i3 = 0; i3 < n3; i3++)
/*  916 */       shift2(f[i3], g[i3]); 
/*      */   }
/*      */   
/*      */   private static void shift3(float[][][] f, float[][][] g) {
/*  920 */     int n3 = f.length;
/*  921 */     int n2 = (f[0]).length;
/*  922 */     float[][] f2 = new float[n3][];
/*  923 */     float[][] g2 = new float[n3][];
/*  924 */     for (int i2 = 0; i2 < n2; i2++) {
/*  925 */       for (int i3 = 0; i3 < n3; i3++) {
/*  926 */         f2[i3] = f[i3][i2];
/*  927 */         g2[i3] = g[i3][i2];
/*      */       } 
/*  929 */       shift2(f2, g2);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkDimension(int dimension) {
/*  934 */     Check.state((this._dimension == dimension), "dimension is valid");
/*      */   }
/*      */   
/*      */   private void checkDimensions(float[] c) {
/*  938 */     Check.argument((this._n1 == c.length), "array length is valid");
/*  939 */     checkDimension(1);
/*      */   }
/*      */   
/*      */   private void checkDimensions(float[][] c) {
/*  943 */     Check.argument(ArrayMath.isRegular(c), "c is regular");
/*  944 */     Check.argument((this._n1 == (c[0]).length), "array dimension 1 is valid");
/*  945 */     Check.argument((this._n2 == c.length), "array dimension 2 is valid");
/*  946 */     checkDimension(2);
/*      */   }
/*      */   
/*      */   private void checkDimensions(float[][][] c) {
/*  950 */     Check.argument(ArrayMath.isRegular(c), "c is regular");
/*  951 */     Check.argument((this._n1 == (c[0][0]).length), "array dimension 1 is valid");
/*  952 */     Check.argument((this._n2 == (c[0]).length), "array dimension 2 is valid");
/*  953 */     Check.argument((this._n3 == c.length), "array dimension 3 is valid");
/*  954 */     checkDimension(3);
/*      */   }
/*      */   private static interface Filter {
/*      */     void apply(float[] param1ArrayOffloat1, float[] param1ArrayOffloat2);
/*      */     void apply1(float[][] param1ArrayOffloat1, float[][] param1ArrayOffloat2);
/*      */     void apply2(float[][] param1ArrayOffloat1, float[][] param1ArrayOffloat2);
/*      */     void apply1(float[][][] param1ArrayOffloat1, float[][][] param1ArrayOffloat2);
/*      */     void apply2(float[][][] param1ArrayOffloat1, float[][][] param1ArrayOffloat2);
/*      */     
/*      */     void apply3(float[][][] param1ArrayOffloat1, float[][][] param1ArrayOffloat2); }
/*      */   
/*      */   private class RectangleFilter implements Filter { private RecursiveRectangleFilter _rrf;
/*      */     
/*      */     public RectangleFilter(double sigma) {
/*  968 */       this(sigma, 0);
/*      */     }
/*      */     public RectangleFilter(double sigma, int lag) {
/*  971 */       int n = (int)ArrayMath.round(1.0D + 2.0D * sigma);
/*  972 */       int m = ArrayMath.max(0, (n - 1 - ArrayMath.abs(lag)) / 2);
/*  973 */       int l = (lag % 2 == 0) ? -m : (-m - 1);
/*  974 */       this._rrf = new RecursiveRectangleFilter(l, m);
/*      */     }
/*      */     public void apply(float[] x, float[] y) {
/*  977 */       this._rrf.apply(x, y);
/*      */     }
/*      */     public void apply1(float[][] x, float[][] y) {
/*  980 */       this._rrf.apply1(x, y);
/*      */     }
/*      */     public void apply2(float[][] x, float[][] y) {
/*  983 */       this._rrf.apply2(x, y);
/*      */     }
/*      */     public void apply1(float[][][] x, float[][][] y) {
/*  986 */       this._rrf.apply1(x, y);
/*      */     }
/*      */     public void apply2(float[][][] x, float[][][] y) {
/*  989 */       this._rrf.apply2(x, y);
/*      */     }
/*      */     public void apply3(float[][][] x, float[][][] y) {
/*  992 */       this._rrf.apply3(x, y);
/*      */     } }
/*      */   
/*      */   private class GaussianFilter implements Filter { private RecursiveGaussianFilter _rgf;
/*      */     
/*      */     public GaussianFilter(double sigma) {
/*  998 */       this._rgf = new RecursiveGaussianFilter(sigma);
/*      */     }
/*      */     public void apply(float[] x, float[] y) {
/* 1001 */       this._rgf.apply0(x, y);
/*      */     }
/*      */     public void apply1(float[][] x, float[][] y) {
/* 1004 */       this._rgf.apply0X(x, y);
/*      */     }
/*      */     public void apply2(float[][] x, float[][] y) {
/* 1007 */       this._rgf.applyX0(x, y);
/*      */     }
/*      */     public void apply1(float[][][] x, float[][][] y) {
/* 1010 */       this._rgf.apply0XX(x, y);
/*      */     }
/*      */     public void apply2(float[][][] x, float[][][] y) {
/* 1013 */       this._rgf.applyX0X(x, y);
/*      */     }
/*      */     public void apply3(float[][][] x, float[][][] y) {
/* 1016 */       this._rgf.applyXX0(x, y);
/*      */     } }
/*      */ 
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/LocalCorrelationFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */