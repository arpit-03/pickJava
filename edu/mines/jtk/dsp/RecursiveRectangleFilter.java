/*     */ package edu.mines.jtk.dsp;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecursiveRectangleFilter
/*     */ {
/*     */   private int _l;
/*     */   private int _m;
/*     */   
/*     */   public RecursiveRectangleFilter(int l, int m) {
/*  49 */     Check.argument((l <= m), "l<=m");
/*  50 */     this._l = l;
/*  51 */     this._m = m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(float[] x, float[] y) {
/*  60 */     checkArrays(x, y);
/*  61 */     if (x == y)
/*  62 */       x = ArrayMath.copy(x); 
/*  63 */     int n = x.length;
/*  64 */     int m = this._m;
/*  65 */     int l = this._l;
/*  66 */     int l1 = l - 1;
/*  67 */     float s = 1.0F / (1 + m - l);
/*     */ 
/*     */     
/*  70 */     y[0] = 0.0F;
/*  71 */     int ilo = ArrayMath.max(0, l);
/*  72 */     int ihi = ArrayMath.min(n, m + 1); int i;
/*  73 */     for (i = ilo; i < ihi; i++) {
/*  74 */       y[0] = y[0] + s * x[i];
/*     */     }
/*     */     
/*  77 */     ilo = 1;
/*  78 */     ihi = ArrayMath.min(n, -m);
/*  79 */     for (i = ilo; i < ihi; i++) {
/*  80 */       y[i] = y[i - 1];
/*     */     }
/*     */     
/*  83 */     ilo = ArrayMath.max(1, -m);
/*  84 */     ihi = ArrayMath.min(n, n - m, 1 - l);
/*  85 */     for (i = ilo; i < ihi; i++) {
/*  86 */       y[i] = y[i - 1] + s * x[i + m];
/*     */     }
/*     */     
/*  89 */     if (1 - l > n - m) {
/*  90 */       ilo = ArrayMath.max(1, n - m);
/*  91 */       ihi = ArrayMath.min(n, 1 - l);
/*  92 */       for (i = ilo; i < ihi; i++)
/*  93 */         y[i] = y[i - 1]; 
/*     */     } else {
/*  95 */       ilo = ArrayMath.max(1, 1 - l);
/*  96 */       ihi = ArrayMath.min(n, n - m);
/*  97 */       for (i = ilo; i < ihi; i++) {
/*  98 */         y[i] = y[i - 1] + s * (x[i + m] - x[i + l1]);
/*     */       }
/*     */     } 
/*     */     
/* 102 */     ilo = ArrayMath.max(1, n - m, 1 - l);
/* 103 */     ihi = ArrayMath.min(n, n + 1 - l);
/* 104 */     for (i = ilo; i < ihi; i++) {
/* 105 */       y[i] = y[i - 1] - s * x[i + l1];
/*     */     }
/*     */     
/* 108 */     ilo = ArrayMath.max(1, n + 1 - l);
/* 109 */     ihi = n;
/* 110 */     for (i = ilo; i < ihi; i++) {
/* 111 */       y[i] = y[i - 1];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply1(float[][] x, float[][] y) {
/* 121 */     checkArrays(x, y);
/* 122 */     int n2 = x.length;
/* 123 */     for (int i2 = 0; i2 < n2; i2++) {
/* 124 */       apply(x[i2], y[i2]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply2(float[][] x, float[][] y) {
/* 134 */     checkArrays(x, y);
/* 135 */     int n1 = (x[0]).length;
/* 136 */     int n2 = x.length;
/* 137 */     if (x == y)
/* 138 */       x = ArrayMath.copy(x); 
/* 139 */     int m = this._m;
/* 140 */     int l = this._l;
/* 141 */     float s = 1.0F / (1 + m - l);
/*     */ 
/*     */     
/* 144 */     ArrayMath.zero(y[0]);
/* 145 */     int i2lo = ArrayMath.max(0, l);
/* 146 */     int i2hi = ArrayMath.min(n2, m + 1); int i2;
/* 147 */     for (i2 = i2lo; i2 < i2hi; i2++) {
/* 148 */       float[] y2 = y[0];
/* 149 */       float[] x2 = x[i2];
/* 150 */       for (int i1 = 0; i1 < n1; i1++) {
/* 151 */         y2[i1] = y2[i1] + s * x2[i1];
/*     */       }
/*     */     } 
/*     */     
/* 155 */     i2lo = 1;
/* 156 */     i2hi = ArrayMath.min(n2, -m);
/* 157 */     for (i2 = i2lo; i2 < i2hi; i2++) {
/* 158 */       float[] y2 = y[i2];
/* 159 */       float[] y2p = y[i2 - 1];
/* 160 */       for (int i1 = 0; i1 < n1; i1++) {
/* 161 */         y2[i1] = y2p[i1];
/*     */       }
/*     */     } 
/*     */     
/* 165 */     i2lo = ArrayMath.max(1, -m);
/* 166 */     i2hi = ArrayMath.min(n2, n2 - m, 1 - l);
/* 167 */     for (i2 = i2lo; i2 < i2hi; i2++) {
/* 168 */       float[] y2 = y[i2];
/* 169 */       float[] y2p = y[i2 - 1];
/* 170 */       float[] x2m = x[i2 + m];
/* 171 */       for (int i1 = 0; i1 < n1; i1++) {
/* 172 */         y2[i1] = y2p[i1] + s * x2m[i1];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 177 */     if (1 - l > n2 - m) {
/* 178 */       i2lo = ArrayMath.max(1, n2 - m);
/* 179 */       i2hi = ArrayMath.min(n2, 1 - l);
/* 180 */       for (i2 = i2lo; i2 < i2hi; i2++) {
/* 181 */         float[] y2 = y[i2];
/* 182 */         float[] y2p = y[i2 - 1];
/* 183 */         for (int i1 = 0; i1 < n1; i1++)
/* 184 */           y2[i1] = y2p[i1]; 
/*     */       } 
/*     */     } else {
/* 187 */       i2lo = ArrayMath.max(1, 1 - l);
/* 188 */       i2hi = ArrayMath.min(n2, n2 - m);
/* 189 */       for (i2 = i2lo; i2 < i2hi; i2++) {
/* 190 */         float[] y2 = y[i2];
/* 191 */         float[] y2p = y[i2 - 1];
/* 192 */         float[] x2m = x[i2 + m];
/* 193 */         float[] x2l = x[i2 + l - 1];
/* 194 */         for (int i1 = 0; i1 < n1; i1++) {
/* 195 */           y2[i1] = y2p[i1] + s * (x2m[i1] - x2l[i1]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     i2lo = ArrayMath.max(1, n2 - m, 1 - l);
/* 201 */     i2hi = ArrayMath.min(n2, n2 + 1 - l);
/* 202 */     for (i2 = i2lo; i2 < i2hi; i2++) {
/* 203 */       float[] y2 = y[i2];
/* 204 */       float[] y2p = y[i2 - 1];
/* 205 */       float[] x2l = x[i2 + l - 1];
/* 206 */       for (int i1 = 0; i1 < n1; i1++) {
/* 207 */         y2[i1] = y2p[i1] - s * x2l[i1];
/*     */       }
/*     */     } 
/*     */     
/* 211 */     i2lo = ArrayMath.max(1, n2 + 1 - l);
/* 212 */     i2hi = n2;
/* 213 */     for (i2 = i2lo; i2 < i2hi; i2++) {
/* 214 */       float[] y2 = y[i2];
/* 215 */       float[] y2p = y[i2 - 1];
/* 216 */       for (int i1 = 0; i1 < n1; i1++) {
/* 217 */         y2[i1] = y2p[i1];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply1(float[][][] x, float[][][] y) {
/* 228 */     checkArrays(x, y);
/* 229 */     int n3 = y.length;
/* 230 */     for (int i3 = 0; i3 < n3; i3++) {
/* 231 */       apply1(x[i3], y[i3]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply2(float[][][] x, float[][][] y) {
/* 241 */     checkArrays(x, y);
/* 242 */     int n3 = y.length;
/* 243 */     for (int i3 = 0; i3 < n3; i3++) {
/* 244 */       apply2(x[i3], y[i3]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply3(float[][][] x, float[][][] y) {
/* 254 */     checkArrays(x, y);
/* 255 */     int n3 = y.length;
/* 256 */     int n2 = (y[0]).length;
/* 257 */     int n1 = (y[0][0]).length;
/* 258 */     float[][] x2 = new float[n3][n1];
/* 259 */     float[][] y2 = new float[n3][n1];
/* 260 */     for (int i2 = 0; i2 < n2; i2++) {
/* 261 */       int i3; for (i3 = 0; i3 < n3; i3++) {
/* 262 */         float[] x32 = x[i3][i2];
/* 263 */         float[] x23 = x2[i3];
/* 264 */         for (int i1 = 0; i1 < n1; i1++) {
/* 265 */           x23[i1] = x32[i1];
/*     */         }
/*     */       } 
/* 268 */       apply2(x2, y2);
/* 269 */       for (i3 = 0; i3 < n3; i3++) {
/* 270 */         float[] y32 = y[i3][i2];
/* 271 */         float[] y23 = y2[i3];
/* 272 */         for (int i1 = 0; i1 < n1; i1++) {
/* 273 */           y32[i1] = y23[i1];
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkArrays(float[] x, float[] y) {
/* 286 */     Check.argument((x.length == y.length), "x.length==y.length");
/*     */   }
/*     */   
/*     */   private static void checkArrays(float[][] x, float[][] y) {
/* 290 */     Check.argument((x.length == y.length), "x.length==y.length");
/* 291 */     Check.argument(((x[0]).length == (y[0]).length), "x[0].length==y[0].length");
/* 292 */     Check.argument(ArrayMath.isRegular(x), "x is regular");
/* 293 */     Check.argument(ArrayMath.isRegular(y), "y is regular");
/*     */   }
/*     */   
/*     */   private static void checkArrays(float[][][] x, float[][][] y) {
/* 297 */     Check.argument((x.length == y.length), "x.length==y.length");
/* 298 */     Check.argument(((x[0]).length == (y[0]).length), "x[0].length==y[0].length");
/* 299 */     Check.argument(((x[0][0]).length == (y[0][0]).length), "x[0][0].length==y[0][0].length");
/*     */     
/* 301 */     Check.argument(ArrayMath.isRegular(x), "x is regular");
/* 302 */     Check.argument(ArrayMath.isRegular(y), "y is regular");
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/RecursiveRectangleFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */