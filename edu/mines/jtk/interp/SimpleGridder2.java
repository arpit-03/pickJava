/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleGridder2
/*     */   implements Gridder2
/*     */ {
/*     */   private int _n;
/*     */   private float _fnull;
/*     */   private float[] _f;
/*     */   private float[] _x1;
/*     */   private float[] _x2;
/*     */   
/*     */   public SimpleGridder2(float[] f, float[] x1, float[] x2) {
/*  44 */     setScattered(f, x1, x2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNullValue(float fnull) {
/*  53 */     this._fnull = fnull;
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
/*     */   public static float[][] samplesOnGrid(Sampling s1, Sampling s2, float[] f, float[] x1, float[] x2) {
/*  68 */     float[][] fxs = samplesOnGrid(s1, s2, f, x1, x2, null);
/*  69 */     return new float[][] { fxs[0], fxs[1], fxs[2] };
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
/*     */   public static float[][] samplesOnGrid(Sampling s1, Sampling s2, float[] f, float[] x1, float[] x2, float[] sd) {
/*  88 */     int n = f.length;
/*  89 */     int n1 = s1.getCount();
/*  90 */     int n2 = s2.getCount();
/*  91 */     double d1 = s1.getDelta();
/*  92 */     double d2 = s2.getDelta();
/*  93 */     double f1 = s1.getFirst();
/*  94 */     double f2 = s2.getFirst();
/*  95 */     double l1 = s1.getLast();
/*  96 */     double l2 = s2.getLast();
/*  97 */     f1 -= 0.5D * d1; l1 += 0.5D * d1;
/*  98 */     f2 -= 0.5D * d2; l2 += 0.5D * d2;
/*     */ 
/*     */     
/* 101 */     boolean[][] ez = (boolean[][])null;
/* 102 */     if (sd != null) {
/* 103 */       ez = new boolean[n2][n1];
/* 104 */       for (int k = 0; k < n; k++) {
/* 105 */         if (sd[k] == 0.0F) {
/* 106 */           double x1i = x1[k];
/* 107 */           double x2i = x2[k];
/* 108 */           if (f1 <= x1i && x1i <= l1 && f2 <= x2i && x2i <= l2) {
/* 109 */             int i1 = s1.indexOfNearest(x1i);
/* 110 */             int m = s2.indexOfNearest(x2i);
/* 111 */             ez[m][i1] = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 118 */     float[][] g = new float[n2][n1];
/* 119 */     float[][] w = new float[n2][n1];
/* 120 */     for (int i = 0; i < n; i++) {
/* 121 */       double x1i = x1[i];
/* 122 */       double x2i = x2[i];
/* 123 */       if (f1 <= x1i && x1i <= l1 && f2 <= x2i && x2i <= l2) {
/* 124 */         int i1 = s1.indexOfNearest(x1i);
/* 125 */         int k = s2.indexOfNearest(x2i);
/* 126 */         if (sd == null || (ez[k][i1] && sd[i] == 0.0F)) {
/* 127 */           g[k][i1] = g[k][i1] + f[i];
/* 128 */           w[k][i1] = w[k][i1] + 1.0F;
/* 129 */         } else if (!ez[k][i1]) {
/* 130 */           float wi = 1.0F / sd[i] * sd[i];
/* 131 */           g[k][i1] = g[k][i1] + wi * f[i];
/* 132 */           w[k][i1] = w[k][i1] + wi;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 138 */     n = 0; int i2;
/* 139 */     for (i2 = 0; i2 < n2; i2++) {
/* 140 */       for (int i1 = 0; i1 < n1; i1++) {
/* 141 */         if (w[i2][i1] > 0.0F) {
/* 142 */           n++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     f = new float[n];
/* 148 */     x1 = new float[n];
/* 149 */     x2 = new float[n];
/* 150 */     if (sd != null)
/* 151 */       sd = new float[n];  int j;
/* 152 */     for (i2 = 0, j = 0; i2 < n2; i2++) {
/* 153 */       for (int i1 = 0; i1 < n1; i1++) {
/* 154 */         if (w[i2][i1] > 0.0F) {
/* 155 */           f[j] = g[i2][i1] / w[i2][i1];
/* 156 */           x1[j] = (float)s1.getValue(i1);
/* 157 */           x2[j] = (float)s2.getValue(i2);
/* 158 */           if (sd != null)
/* 159 */             sd[j] = ez[i2][i1] ? 0.0F : (1.0F / (float)Math.sqrt(w[i2][i1])); 
/* 160 */           j++;
/*     */         } 
/*     */       } 
/*     */     } 
/* 164 */     return new float[][] { f, x1, x2, sd };
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
/*     */   public static float[][] getGriddedSamples(float fnull, Sampling s1, Sampling s2, float[][] g) {
/* 178 */     int n1 = s1.getCount();
/* 179 */     int n2 = s2.getCount();
/* 180 */     int n = 0;
/* 181 */     for (int i2 = 0; i2 < n2; i2++) {
/* 182 */       for (int i1 = 0; i1 < n1; i1++) {
/* 183 */         if (g[i2][i1] != fnull)
/* 184 */           n++; 
/*     */       } 
/*     */     } 
/* 187 */     float[] f = new float[n];
/* 188 */     float[] x1 = new float[n];
/* 189 */     float[] x2 = new float[n];
/* 190 */     for (int j = 0, i = 0; j < n2; j++) {
/* 191 */       float x2i = (float)s2.getValue(j);
/* 192 */       for (int i1 = 0; i1 < n1; i1++) {
/* 193 */         if (g[j][i1] != fnull) {
/* 194 */           float x1i = (float)s1.getValue(i1);
/* 195 */           x1[i] = x1i;
/* 196 */           x2[i] = x2i;
/* 197 */           f[i] = g[j][i1];
/* 198 */           i++;
/*     */         } 
/*     */       } 
/*     */     } 
/* 202 */     return new float[][] { f, x1, x2 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScattered(float[] f, float[] x1, float[] x2) {
/* 209 */     this._n = f.length;
/* 210 */     this._f = f;
/* 211 */     this._x1 = x1;
/* 212 */     this._x2 = x2;
/*     */   }
/*     */   
/*     */   public float[][] grid(Sampling s1, Sampling s2) {
/* 216 */     int n1 = s1.getCount();
/* 217 */     int n2 = s2.getCount();
/* 218 */     double d1 = s1.getDelta();
/* 219 */     double d2 = s2.getDelta();
/* 220 */     double f1 = s1.getFirst();
/* 221 */     double f2 = s2.getFirst();
/* 222 */     double l1 = s1.getLast();
/* 223 */     double l2 = s2.getLast();
/* 224 */     f1 -= 0.5D * d1; l1 += 0.5D * d1;
/* 225 */     f2 -= 0.5D * d2; l2 += 0.5D * d2;
/* 226 */     float[][] g = new float[n2][n1];
/* 227 */     float[][] c = new float[n2][n1];
/* 228 */     for (int i = 0; i < this._n; i++) {
/* 229 */       double x1 = this._x1[i];
/* 230 */       double x2 = this._x2[i];
/* 231 */       if (f1 <= x1 && x1 <= l1 && f2 <= x2 && x2 <= l2) {
/* 232 */         int i1 = s1.indexOfNearest(x1);
/* 233 */         int j = s2.indexOfNearest(x2);
/* 234 */         g[j][i1] = g[j][i1] + this._f[i];
/* 235 */         c[j][i1] = c[j][i1] + 1.0F;
/*     */       } 
/*     */     } 
/* 238 */     for (int i2 = 0; i2 < n2; i2++) {
/* 239 */       for (int i1 = 0; i1 < n1; i1++) {
/* 240 */         g[i2][i1] = (c[i2][i1] > 0.0F) ? (g[i2][i1] / c[i2][i1]) : this._fnull;
/*     */       }
/*     */     } 
/* 243 */     return g;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/SimpleGridder2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */