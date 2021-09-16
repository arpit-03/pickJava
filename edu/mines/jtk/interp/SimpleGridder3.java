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
/*     */ public class SimpleGridder3
/*     */   implements Gridder3
/*     */ {
/*     */   private int _n;
/*     */   private float _fnull;
/*     */   private float[] _f;
/*     */   private float[] _x1;
/*     */   private float[] _x2;
/*     */   private float[] _x3;
/*     */   
/*     */   public SimpleGridder3(float[] f, float[] x1, float[] x2, float[] x3) {
/*  45 */     setScattered(f, x1, x2, x3);
/*     */   }
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
/*     */   public static float[][] getGriddedSamples(float fnull, Sampling s1, Sampling s2, Sampling s3, float[][][] g) {
/*  68 */     int n1 = s1.getCount();
/*  69 */     int n2 = s2.getCount();
/*  70 */     int n3 = s3.getCount();
/*  71 */     int n = 0;
/*  72 */     for (int i3 = 0; i3 < n3; i3++) {
/*  73 */       for (int i2 = 0; i2 < n2; i2++) {
/*  74 */         for (int i1 = 0; i1 < n1; i1++) {
/*  75 */           if (g[i3][i2][i1] != fnull)
/*  76 */             n++; 
/*     */         } 
/*     */       } 
/*     */     } 
/*  80 */     float[] f = new float[n];
/*  81 */     float[] x1 = new float[n];
/*  82 */     float[] x2 = new float[n];
/*  83 */     float[] x3 = new float[n];
/*  84 */     for (int j = 0, i = 0; j < n3; j++) {
/*  85 */       float x3i = (float)s3.getValue(j);
/*  86 */       for (int i2 = 0; i2 < n2; i2++) {
/*  87 */         float x2i = (float)s2.getValue(i2);
/*  88 */         for (int i1 = 0; i1 < n1; i1++) {
/*  89 */           if (g[j][i2][i1] != fnull) {
/*  90 */             float x1i = (float)s1.getValue(i1);
/*  91 */             x1[i] = x1i;
/*  92 */             x2[i] = x2i;
/*  93 */             x3[i] = x3i;
/*  94 */             f[i] = g[j][i2][i1];
/*  95 */             i++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 100 */     return new float[][] { f, x1, x2, x3 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScattered(float[] f, float[] x1, float[] x2, float[] x3) {
/* 107 */     this._n = f.length;
/* 108 */     this._f = f;
/* 109 */     this._x1 = x1;
/* 110 */     this._x2 = x2;
/* 111 */     this._x3 = x3;
/*     */   }
/*     */   
/*     */   public float[][][] grid(Sampling s1, Sampling s2, Sampling s3) {
/* 115 */     int n1 = s1.getCount();
/* 116 */     int n2 = s2.getCount();
/* 117 */     int n3 = s3.getCount();
/* 118 */     double d1 = s1.getDelta();
/* 119 */     double d2 = s2.getDelta();
/* 120 */     double d3 = s3.getDelta();
/* 121 */     double f1 = s1.getFirst();
/* 122 */     double f2 = s2.getFirst();
/* 123 */     double f3 = s3.getFirst();
/* 124 */     double l1 = s1.getLast();
/* 125 */     double l2 = s2.getLast();
/* 126 */     double l3 = s3.getLast();
/* 127 */     f1 -= 0.5D * d1; l1 += 0.5D * d1;
/* 128 */     f2 -= 0.5D * d2; l2 += 0.5D * d2;
/* 129 */     f3 -= 0.5D * d3; l3 += 0.5D * d3;
/* 130 */     float[][][] g = new float[n3][n2][n1];
/* 131 */     float[][][] c = new float[n3][n2][n1];
/* 132 */     for (int i = 0; i < this._n; i++) {
/* 133 */       double x1 = this._x1[i];
/* 134 */       double x2 = this._x2[i];
/* 135 */       double x3 = this._x3[i];
/* 136 */       if (f1 <= x1 && x1 <= l1 && f2 <= x2 && x2 <= l2 && f3 <= x3 && x3 <= l3) {
/* 137 */         int i1 = s1.indexOfNearest(x1);
/* 138 */         int i2 = s2.indexOfNearest(x2);
/* 139 */         int j = s3.indexOfNearest(x3);
/* 140 */         g[j][i2][i1] = g[j][i2][i1] + this._f[i];
/* 141 */         c[j][i2][i1] = c[j][i2][i1] + 1.0F;
/*     */       } 
/*     */     } 
/* 144 */     for (int i3 = 0; i3 < n3; i3++) {
/* 145 */       for (int i2 = 0; i2 < n2; i2++) {
/* 146 */         for (int i1 = 0; i1 < n1; i1++) {
/* 147 */           g[i3][i2][i1] = (c[i3][i2][i1] > 0.0F) ? (g[i3][i2][i1] / c[i3][i2][i1]) : this._fnull;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 153 */     return g;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/SimpleGridder3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */