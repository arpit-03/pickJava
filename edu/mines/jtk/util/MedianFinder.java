/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MedianFinder
/*     */ {
/*     */   private int[] _m;
/*     */   private int _n;
/*     */   private float[] _w;
/*     */   private float[] _x;
/*     */   
/*     */   public float findMedian(float[] x) {
/*     */     Check.argument((this._n == x.length), "length of x is valid");
/*     */     ArrayMath.copy(x, this._x);
/*     */     int k = (this._n - 1) / 2;
/*     */     ArrayMath.quickPartialSort(k, this._x);
/*     */     float xmed = this._x[k];
/*     */     if (this._n % 2 == 0) {
/*     */       float xmin = this._x[this._n - 1];
/*     */       for (int i = this._n - 2; i > k; i--) {
/*     */         if (this._x[i] < xmin)
/*     */           xmin = this._x[i]; 
/*     */       } 
/*     */       xmed = 0.5F * (xmed + xmin);
/*     */     } 
/*     */     return xmed;
/*     */   }
/*     */   
/*     */   public MedianFinder(int n) {
/* 122 */     this._m = new int[2];
/*     */     this._n = n;
/*     */     this._x = new float[n];
/*     */   }
/*     */   
/*     */   private static int med3(float[] a, int i, int j, int k) {
/* 128 */     return (a[i] < a[j]) ? ((a[j] < a[k]) ? j : ((a[i] < a[k]) ? k : i)) : ((a[j] > a[k]) ? j : ((a[i] > a[k]) ? k : i));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void swap(float[] a, float[] b, int i, int j) {
/* 134 */     float ai = a[i];
/* 135 */     a[i] = a[j];
/* 136 */     a[j] = ai;
/* 137 */     float bi = b[i];
/* 138 */     b[i] = b[j];
/* 139 */     b[j] = bi;
/*     */   } public float findMedian(float[] w, float[] x) { Check.argument((this._n == w.length), "length of w is valid"); Check.argument((this._n == x.length), "length of x is valid"); if (this._w == null)
/*     */       this._w = new float[this._n];  ArrayMath.copy(w, this._w); ArrayMath.copy(x, this._x); if (this._n < 16)
/*     */       return findMedianSmallN(this._w, this._x); 
/* 143 */     return findMedianLargeN(this._w, this._x); } private static void swap(float[] a, float[] b, int i, int j, int n) { while (n > 0) {
/* 144 */       float ai = a[i];
/* 145 */       a[i] = a[j];
/* 146 */       a[j] = ai;
/* 147 */       float bi = b[i];
/* 148 */       b[i++] = b[j];
/* 149 */       b[j++] = bi;
/* 150 */       n--;
/*     */     }  }
/*     */ 
/*     */   
/*     */   private static void insertionSort(float[] w, float[] x, int p, int q) {
/* 155 */     for (int i = p + 1; i <= q; i++) {
/* 156 */       for (int j = i; j > p && x[j - 1] > x[j]; j--)
/* 157 */         swap(w, x, j, j - 1); 
/*     */     } 
/*     */   }
/*     */   private static void quickPartition(float[] w, float[] x, int[] m) {
/* 161 */     int p = m[0];
/* 162 */     int q = m[1];
/* 163 */     int k = med3(x, p, (p + q) / 2, q);
/* 164 */     float y = x[k];
/* 165 */     int a = p, b = p;
/* 166 */     int c = q, d = q;
/*     */     while (true) {
/* 168 */       if (b <= c && x[b] <= y) {
/* 169 */         if (x[b] == y)
/* 170 */           swap(w, x, a++, b); 
/* 171 */         b++; continue;
/*     */       } 
/* 173 */       while (c >= b && x[c] >= y) {
/* 174 */         if (x[c] == y)
/* 175 */           swap(w, x, c, d--); 
/* 176 */         c--;
/*     */       } 
/* 178 */       if (b > c)
/*     */         break; 
/* 180 */       swap(w, x, b, c);
/* 181 */       b++;
/* 182 */       c--;
/*     */     } 
/* 184 */     int r = Math.min(a - p, b - a);
/* 185 */     int s = Math.min(d - c, q - d);
/* 186 */     int t = q + 1;
/* 187 */     swap(w, x, p, b - r, r);
/* 188 */     swap(w, x, b, t - s, s);
/* 189 */     m[0] = p + b - a;
/* 190 */     m[1] = q - d - c;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float findMedianSmallN(float[] w, float[] x) {
/* 196 */     for (int i = 1; i < this._n; i++) {
/* 197 */       for (int k = i; k > 0 && x[k - 1] > x[k]; k--) {
/* 198 */         swap(w, x, k, k - 1);
/*     */       }
/*     */     } 
/*     */     
/* 202 */     float ws = 0.0F;
/* 203 */     for (int j = 0; j < this._n; j++)
/* 204 */       ws += w[j]; 
/* 205 */     float wh = 0.5F * ws;
/*     */ 
/*     */     
/* 208 */     int kl = 0;
/* 209 */     float wl = w[kl];
/* 210 */     while (wl < wh) {
/* 211 */       wl += w[++kl];
/*     */     }
/*     */     
/* 214 */     int kr = this._n - 1;
/* 215 */     float wr = w[kr];
/* 216 */     while (wr < wh) {
/* 217 */       wr += w[--kr];
/*     */     }
/*     */     
/* 220 */     if (kl == kr) {
/* 221 */       return x[kl];
/*     */     }
/* 223 */     return 0.5F * (x[kl] + x[kr]);
/*     */   }
/*     */   
/*     */   private float findMedianLargeN(float[] w, float[] x) {
/* 227 */     int p = 0, p0 = p;
/* 228 */     int q = this._n - 1, q0 = q;
/* 229 */     float wc = 0.0F;
/* 230 */     float xnot = Float.MAX_VALUE;
/* 231 */     float xmed = xnot;
/* 232 */     while (p < q && xmed == xnot) {
/* 233 */       this._m[0] = p;
/* 234 */       this._m[1] = q;
/* 235 */       quickPartition(w, x, this._m);
/* 236 */       int pp = this._m[0];
/* 237 */       int qq = this._m[1];
/* 238 */       float wl = 0.0F;
/* 239 */       for (int i = p; i < pp; i++)
/* 240 */         wl += w[i]; 
/* 241 */       float wm = 0.0F;
/* 242 */       for (int j = pp; j <= qq; j++)
/* 243 */         wm += w[j]; 
/* 244 */       float wr = 0.0F;
/* 245 */       for (int k = qq + 1; k <= q; k++)
/* 246 */         wr += w[k]; 
/* 247 */       float dl = wc + wl - wm - wr;
/* 248 */       float dr = wc + wl + wm - wr;
/* 249 */       if (dl > 0.0F) {
/* 250 */         q = pp - 1;
/* 251 */         wc -= wm + wr; continue;
/* 252 */       }  if (dr < 0.0F) {
/* 253 */         p = qq + 1;
/* 254 */         wc += wl + wm; continue;
/* 255 */       }  if (dl == 0.0F) {
/* 256 */         float xmax = x[p0];
/* 257 */         for (int m = pp - 1; m > p0; m--) {
/* 258 */           if (x[m] > xmax)
/* 259 */             xmax = x[m]; 
/* 260 */         }  xmed = 0.5F * (x[pp] + xmax); continue;
/* 261 */       }  if (dr == 0.0F) {
/* 262 */         float xmin = x[q0];
/* 263 */         for (int m = qq + 1; m < q0; m++) {
/* 264 */           if (x[m] < xmin)
/* 265 */             xmin = x[m]; 
/* 266 */         }  xmed = 0.5F * (x[qq] + xmin); continue;
/*     */       } 
/* 268 */       xmed = x[pp];
/*     */     } 
/*     */     
/* 271 */     if (xmed == xnot)
/* 272 */       xmed = x[p]; 
/* 273 */     return xmed;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/MedianFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */