/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.dsp.Sampling;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiscreteSibsonGridder2
/*     */   implements Gridder2
/*     */ {
/*     */   private int _n;
/*     */   private float[] _f;
/*     */   private float[] _x1;
/*     */   private float[] _x2;
/*     */   private int _nsmooth;
/*     */   
/*     */   public DiscreteSibsonGridder2(float[] f, float[] x1, float[] x2) {
/*  69 */     setScattered(f, x1, x2);
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
/*     */   public void setSmooth(int nsmooth) {
/*  83 */     this._nsmooth = nsmooth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScattered(float[] f, float[] x1, float[] x2) {
/*  90 */     this._n = f.length;
/*  91 */     this._f = f;
/*  92 */     this._x1 = x1;
/*  93 */     this._x2 = x2;
/*     */   }
/*     */   
/*     */   public float[][] grid(Sampling s1, Sampling s2) {
/*  97 */     int n1 = s1.getCount();
/*  98 */     int n2 = s2.getCount();
/*  99 */     float fx1 = (float)s1.getFirst();
/* 100 */     float fx2 = (float)s2.getFirst();
/* 101 */     float lx1 = (float)s1.getLast();
/* 102 */     float lx2 = (float)s2.getLast();
/* 103 */     float dx1 = (float)s1.getDelta();
/* 104 */     float dx2 = (float)s2.getDelta();
/* 105 */     float od1 = 1.0F / dx1;
/* 106 */     float od2 = 1.0F / dx2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     int nk = n1 * n2;
/* 113 */     int[] kk = new int[nk];
/* 114 */     float[] ds = new float[nk];
/* 115 */     for (int i2 = 0, k = 0; i2 < n2; i2++) {
/* 116 */       double x2 = (i2 * dx2);
/* 117 */       for (int i1 = 0; i1 < n1; i1++, k++) {
/* 118 */         double x1 = (i1 * dx1);
/* 119 */         ds[k] = (float)(x1 * x1 + x2 * x2);
/* 120 */         kk[k] = k;
/*     */       } 
/*     */     } 
/* 123 */     ArrayMath.quickIndexSort(ds, kk);
/* 124 */     ds = null;
/* 125 */     int[] k1 = new int[nk];
/* 126 */     int[] k2 = new int[nk];
/* 127 */     for (int j = 0; j < nk; j++) {
/* 128 */       int ik = kk[j];
/* 129 */       int i1 = ik % n1;
/* 130 */       int n = ik / n1;
/* 131 */       k1[j] = i1;
/* 132 */       k2[j] = n;
/*     */     } 
/* 134 */     kk = null;
/*     */ 
/*     */     
/* 137 */     float[][] g = new float[n2][n1];
/* 138 */     float[][] c = new float[n2][n1];
/* 139 */     for (int i = 0; i < this._n; i++) {
/* 140 */       float x1i = this._x1[i];
/* 141 */       float x2i = this._x2[i];
/* 142 */       if (x1i >= fx1 && x1i <= lx1 && 
/* 143 */         x2i >= fx2 && x2i <= lx2) {
/* 144 */         int i1 = (int)(0.5F + (x1i - fx1) * od1);
/* 145 */         int n = (int)(0.5F + (x2i - fx2) * od2);
/* 146 */         c[n][i1] = c[n][i1] + 1.0F;
/* 147 */         g[n][i1] = g[n][i1] + this._f[i];
/*     */       } 
/*     */     } 
/*     */     int m;
/* 151 */     for (m = 0; m < n2; m++) {
/* 152 */       for (int i1 = 0; i1 < n1; i1++) {
/* 153 */         if (c[m][i1] > 0.0F) {
/* 154 */           g[m][i1] = g[m][i1] / c[m][i1];
/* 155 */           c[m][i1] = -c[m][i1];
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 161 */     for (m = 0; m < n2; m++) {
/* 162 */       for (int i1 = 0; i1 < n1; i1++) {
/*     */ 
/*     */ 
/*     */         
/* 166 */         int kn = -1;
/* 167 */         float fn = 0.0F; int n;
/* 168 */         for (n = 0; n < nk && kn < 0; n++) {
/* 169 */           int k1k = k1[n];
/* 170 */           int k2k = k2[n]; int j2;
/* 171 */           for (int m2 = 0; m2 < 2; m2++, j2 = m + k2k) {
/* 172 */             if (j2 >= 0 && j2 < n2) {
/* 173 */               int j1; for (int m1 = 0; m1 < 2; m1++, j1 = i1 + k1k) {
/* 174 */                 if (j1 >= 0 && j1 < n1 && 
/* 175 */                   c[j2][j1] < 0.0F) {
/* 176 */                   kn = n;
/* 177 */                   fn = g[j2][j1];
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 189 */         if (kn < nk - 1 && k1[kn] == k2[kn + 1] && k2[kn] == k1[kn + 1]) {
/* 190 */           kn++;
/*     */         }
/*     */         
/* 193 */         for (n = 0; n <= kn; n++) {
/* 194 */           int k1k = k1[n];
/* 195 */           int k2k = k2[n]; int j2;
/* 196 */           for (int m2 = 0; m2 < 2; m2++, j2 = m + k2k) {
/* 197 */             if (j2 >= 0 && j2 < n2) {
/* 198 */               int j1; for (int m1 = 0; m1 < 2; m1++, j1 = i1 + k1k) {
/* 199 */                 if (j1 >= 0 && j1 < n1 && 
/* 200 */                   c[j2][j1] >= 0.0F) {
/* 201 */                   g[j2][j1] = g[j2][j1] + fn;
/* 202 */                   c[j2][j1] = c[j2][j1] + 1.0F;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 211 */     for (m = 0; m < n2; m++) {
/* 212 */       for (int i1 = 0; i1 < n1; i1++) {
/* 213 */         if (c[m][i1] > 0.0F) {
/* 214 */           g[m][i1] = g[m][i1] / c[m][i1];
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 220 */     int n1m = n1 - 1;
/* 221 */     int n2m = n2 - 1;
/* 222 */     float a1 = 0.4F;
/* 223 */     float a2 = -0.1F;
/* 224 */     float a3 = -0.05F;
/* 225 */     for (int jsmooth = 0; jsmooth < this._nsmooth; jsmooth++) {
/* 226 */       for (int n = 0; n < n2; n++) {
/* 227 */         int i2m = (n == 0) ? n : (n - 1);
/* 228 */         int i2p = (n == n2m) ? n : (n + 1);
/* 229 */         int i2mm = (i2m == 0) ? i2m : (i2m - 1);
/* 230 */         int i2pp = (i2p == n2m) ? i2p : (i2p + 1);
/* 231 */         for (int i1 = 0; i1 < n1; i1++) {
/* 232 */           int i1m = (i1 == 0) ? i1 : (i1 - 1);
/* 233 */           int i1p = (i1 == n1m) ? i1 : (i1 + 1);
/* 234 */           int i1mm = (i1m == 0) ? i1m : (i1m - 1);
/* 235 */           int i1pp = (i1p == n1m) ? i1p : (i1p + 1);
/* 236 */           if (c[n][i1] > 0.0F) {
/* 237 */             float g1 = a1 * (g[n][i1m] + g[n][i1p] + g[i2m][i1] + g[i2p][i1]);
/* 238 */             float g2 = a2 * (g[i2m][i1m] + g[i2m][i1p] + g[i2p][i1m] + g[i2p][i1p]);
/* 239 */             float g3 = a3 * (g[n][i1mm] + g[n][i1pp] + g[i2mm][i1] + g[i2pp][i1]);
/* 240 */             g[n][i1] = g1 + g2 + g3;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 246 */     return g;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/DiscreteSibsonGridder2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */