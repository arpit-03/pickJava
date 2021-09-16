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
/*     */ 
/*     */ 
/*     */ public class DiscreteSibsonGridder3
/*     */   implements Gridder3
/*     */ {
/*     */   private int _n;
/*     */   private float[] _f;
/*     */   private float[] _x1;
/*     */   private float[] _x2;
/*     */   private float[] _x3;
/*     */   private int _nsmooth;
/*     */   
/*     */   public DiscreteSibsonGridder3(float[] f, float[] x1, float[] x2, float[] x3) {
/*  72 */     setScattered(f, x1, x2, x3);
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
/*     */   private void setSmooth(int nsmooth) {
/*  87 */     this._nsmooth = nsmooth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScattered(float[] f, float[] x1, float[] x2, float[] x3) {
/*  94 */     this._n = f.length;
/*  95 */     this._f = f;
/*  96 */     this._x1 = x1;
/*  97 */     this._x2 = x2;
/*  98 */     this._x3 = x3;
/*     */   }
/*     */   
/*     */   public float[][][] grid(Sampling s1, Sampling s2, Sampling s3) {
/* 102 */     int n1 = s1.getCount();
/* 103 */     int n2 = s2.getCount();
/* 104 */     int n3 = s3.getCount();
/* 105 */     float fx1 = (float)s1.getFirst();
/* 106 */     float fx2 = (float)s2.getFirst();
/* 107 */     float fx3 = (float)s3.getFirst();
/* 108 */     float lx1 = (float)s1.getLast();
/* 109 */     float lx2 = (float)s2.getLast();
/* 110 */     float lx3 = (float)s3.getLast();
/* 111 */     float dx1 = (float)s1.getDelta();
/* 112 */     float dx2 = (float)s2.getDelta();
/* 113 */     float dx3 = (float)s3.getDelta();
/* 114 */     float od1 = 1.0F / dx1;
/* 115 */     float od2 = 1.0F / dx2;
/* 116 */     float od3 = 1.0F / dx3;
/*     */ 
/*     */     
/* 119 */     float[][][] g = new float[n3][n2][n1];
/* 120 */     float[][][] c = new float[n3][n2][n1];
/* 121 */     for (int i = 0; i < this._n; i++) {
/* 122 */       float x1i = this._x1[i];
/* 123 */       float x2i = this._x2[i];
/* 124 */       float x3i = this._x3[i];
/* 125 */       if (x1i >= fx1 && x1i <= lx1 && 
/* 126 */         x2i >= fx2 && x2i <= lx2 && 
/* 127 */         x3i >= fx3 && x3i <= lx3) {
/* 128 */         int i1 = (int)(0.5F + (x1i - fx1) * od1);
/* 129 */         int i2 = (int)(0.5F + (x2i - fx2) * od2);
/* 130 */         int m = (int)(0.5F + (x3i - fx3) * od3);
/* 131 */         c[m][i2][i1] = c[m][i2][i1] + 1.0F;
/* 132 */         g[m][i2][i1] = g[m][i2][i1] + this._f[i];
/*     */       } 
/*     */     } 
/*     */     
/* 136 */     for (int i3 = 0; i3 < n3; i3++) {
/* 137 */       for (int i2 = 0; i2 < n2; i2++) {
/* 138 */         for (int i1 = 0; i1 < n1; i1++) {
/* 139 */           if (c[i3][i2][i1] > 0.0F) {
/* 140 */             g[i3][i2][i1] = g[i3][i2][i1] / c[i3][i2][i1];
/* 141 */             c[i3][i2][i1] = -c[i3][i2][i1];
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     int nk = n1 * n2 * n3;
/* 152 */     int[] kk = new int[nk];
/* 153 */     float[] ds = new float[nk]; int j, k;
/* 154 */     for (j = 0, k = 0; j < n3; j++) {
/* 155 */       double x3 = (j * dx3);
/* 156 */       for (int i2 = 0; i2 < n2; i2++) {
/* 157 */         double x2 = (i2 * dx2);
/* 158 */         for (int i1 = 0; i1 < n1; i1++, k++) {
/* 159 */           double x1 = (i1 * dx1);
/* 160 */           ds[k] = (float)(x1 * x1 + x2 * x2 + x3 * x3);
/* 161 */           kk[k] = k;
/*     */         } 
/*     */       } 
/*     */     } 
/* 165 */     ArrayMath.quickIndexSort(ds, kk);
/*     */ 
/*     */     
/* 168 */     for (j = 0; j < n3; j++) {
/* 169 */       for (int i2 = 0; i2 < n2; i2++) {
/* 170 */         for (int i1 = 0; i1 < n1; i1++) {
/*     */ 
/*     */ 
/*     */           
/* 174 */           int kn = -1;
/* 175 */           float fn = 0.0F;
/* 176 */           for (int n = 0; n < nk && kn < 0; n++) {
/* 177 */             int ik = kk[n];
/* 178 */             int k1 = ik % n1; ik /= n1;
/* 179 */             int k2 = ik % n2; ik /= n2;
/* 180 */             int k3 = ik; int j3;
/* 181 */             for (int m3 = 0; m3 < 3; m3++, j3 = j + k3) {
/* 182 */               if ((j3 != j || m3 <= 0) && 
/* 183 */                 j3 >= 0 && j3 < n3) {
/* 184 */                 int j2; for (int m2 = 0; m2 < 2; m2++, j2 = i2 + k2) {
/* 185 */                   if ((j2 != i2 || m2 <= 0) && 
/* 186 */                     j2 >= 0 && j2 < n2) {
/* 187 */                     int j1; for (int m1 = 0; m1 < 2; m1++, j1 = i1 + k1) {
/* 188 */                       if ((j1 != i1 || m1 <= 0) && 
/* 189 */                         j1 >= 0 && j1 < n1 && 
/* 190 */                         c[j3][j2][j1] < 0.0F) {
/* 191 */                         kn = n;
/* 192 */                         fn = g[j3][j2][j1];
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 201 */           for (float dsk = ds[kk[kn]]; kn + 1 < nk && dsk == ds[kk[kn + 1]]; kn++);
/*     */ 
/*     */ 
/*     */           
/* 205 */           for (int m = 0; m <= kn; m++) {
/* 206 */             int ik = kk[m];
/* 207 */             int k1 = ik % n1; ik /= n1;
/* 208 */             int k2 = ik % n2; ik /= n2;
/* 209 */             int k3 = ik; int j3;
/* 210 */             for (int m3 = 0; m3 < 2; m3++, j3 = j + k3) {
/* 211 */               if ((j3 != j || m3 <= 0) && 
/* 212 */                 j3 >= 0 && j3 < n3) {
/* 213 */                 int j2; for (int m2 = 0; m2 < 2; m2++, j2 = i2 + k2) {
/* 214 */                   if ((j2 != i2 || m2 <= 0) && 
/* 215 */                     j2 >= 0 && j2 < n2) {
/* 216 */                     int j1; for (int m1 = 0; m1 < 2; m1++, j1 = i1 + k1) {
/* 217 */                       if ((j1 != i1 || m1 <= 0) && 
/* 218 */                         j1 >= 0 && j1 < n1 && 
/* 219 */                         c[j3][j2][j1] >= 0.0F) {
/* 220 */                         g[j3][j2][j1] = g[j3][j2][j1] + fn;
/* 221 */                         c[j3][j2][j1] = c[j3][j2][j1] + 1.0F;
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 232 */     for (j = 0; j < n3; j++) {
/* 233 */       for (int i2 = 0; i2 < n2; i2++) {
/* 234 */         for (int i1 = 0; i1 < n1; i1++) {
/* 235 */           if (c[j][i2][i1] > 0.0F) {
/* 236 */             g[j][i2][i1] = g[j][i2][i1] / c[j][i2][i1];
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     return g;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/DiscreteSibsonGridder3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */