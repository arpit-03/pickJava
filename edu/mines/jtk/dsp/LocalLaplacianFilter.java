/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.Parallel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalLaplacianFilter
/*     */ {
/*     */   private float _scale;
/*     */   
/*     */   public LocalLaplacianFilter() {
/*  58 */     this(1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalLaplacianFilter(double scale) {
/*  66 */     this._scale = (float)scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(Tensors2 d, float[][] x, float[][] y) {
/*  76 */     float[] di = new float[3];
/*  77 */     int n1 = (x[0]).length;
/*  78 */     int n2 = x.length;
/*  79 */     for (int i2 = 1; i2 < n2; i2++) {
/*  80 */       for (int i1 = 1; i1 < n1; i1++) {
/*  81 */         d.getTensor(i1, i2, di);
/*  82 */         float d11 = di[0] * this._scale;
/*  83 */         float d12 = di[1] * this._scale;
/*  84 */         float d22 = di[2] * this._scale;
/*  85 */         float x00 = x[i2][i1];
/*  86 */         float x01 = x[i2][i1 - 1];
/*  87 */         float x10 = x[i2 - 1][i1];
/*  88 */         float x11 = x[i2 - 1][i1 - 1];
/*  89 */         float xa = x00 - x11;
/*  90 */         float xb = x01 - x10;
/*  91 */         float x1 = 0.5F * (xa - xb);
/*  92 */         float x2 = 0.5F * (xa + xb);
/*  93 */         float y1 = d11 * x1 + d12 * x2;
/*  94 */         float y2 = d12 * x1 + d22 * x2;
/*  95 */         float ya = 0.5F * (y1 + y2);
/*  96 */         float yb = 0.5F * (y1 - y2);
/*  97 */         y[i2][i1] = y[i2][i1] + ya;
/*  98 */         y[i2][i1 - 1] = y[i2][i1 - 1] - yb;
/*  99 */         y[i2 - 1][i1] = y[i2 - 1][i1] + yb;
/* 100 */         y[i2 - 1][i1 - 1] = y[i2 - 1][i1 - 1] - ya;
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
/*     */   
/*     */   public void apply(Tensors3 d, float[][][] x, float[][][] y) {
/* 113 */     applyParallel(d, x, y);
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
/*     */   private void applyParallel(final Tensors3 d, final float[][][] x, final float[][][] y) {
/* 130 */     int n3 = x.length;
/*     */ 
/*     */     
/* 133 */     Parallel.loop(1, n3, 2, new Parallel.LoopInt() {
/*     */           public void compute(int i3) {
/* 135 */             LocalLaplacianFilter.this.applySlice3(i3, d, x, y);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 140 */     Parallel.loop(2, n3, 2, new Parallel.LoopInt() {
/*     */           public void compute(int i3) {
/* 142 */             LocalLaplacianFilter.this.applySlice3(i3, d, x, y);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void applySlice3(int i3, Tensors3 d, float[][][] x, float[][][] y) {
/* 149 */     float[] di = new float[6];
/* 150 */     int n1 = (x[0][0]).length;
/* 151 */     int n2 = (x[0]).length;
/* 152 */     for (int i2 = 1; i2 < n2; i2++) {
/* 153 */       float[] x00 = x[i3][i2];
/* 154 */       float[] x01 = x[i3][i2 - 1];
/* 155 */       float[] x10 = x[i3 - 1][i2];
/* 156 */       float[] x11 = x[i3 - 1][i2 - 1];
/* 157 */       float[] y00 = y[i3][i2];
/* 158 */       float[] y01 = y[i3][i2 - 1];
/* 159 */       float[] y10 = y[i3 - 1][i2];
/* 160 */       float[] y11 = y[i3 - 1][i2 - 1];
/* 161 */       for (int i1 = 1, i1m = 0; i1 < n1; i1++, i1m++) {
/* 162 */         d.getTensor(i1, i2, i3, di);
/* 163 */         float d11 = di[0] * this._scale;
/* 164 */         float d12 = di[1] * this._scale;
/* 165 */         float d13 = di[2] * this._scale;
/* 166 */         float d22 = di[3] * this._scale;
/* 167 */         float d23 = di[4] * this._scale;
/* 168 */         float d33 = di[5] * this._scale;
/* 169 */         apply(i1, d11, d12, d13, d22, d23, d33, x00, x01, x10, x11, y00, y01, y10, y11);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static void apply(int i1, float d11, float d12, float d13, float d22, float d23, float d33, float[] x00, float[] x01, float[] x10, float[] x11, float[] y00, float[] y01, float[] y10, float[] y11) {
/* 184 */     int i1m = i1 - 1;
/* 185 */     float x000 = x00[i1];
/* 186 */     float x001 = x00[i1m];
/* 187 */     float x010 = x01[i1];
/* 188 */     float x100 = x10[i1];
/* 189 */     float x011 = x01[i1m];
/* 190 */     float x101 = x10[i1m];
/* 191 */     float x110 = x11[i1];
/* 192 */     float x111 = x11[i1m];
/*     */ 
/*     */ 
/*     */     
/* 196 */     float xa = x000 - x111;
/* 197 */     float xb = x001 - x110;
/* 198 */     float xc = x010 - x101;
/* 199 */     float xd = x100 - x011;
/* 200 */     float x1 = 0.25F * (xa - xb + xc + xd);
/* 201 */     float x2 = 0.25F * (xa + xb - xc + xd);
/* 202 */     float x3 = 0.25F * (xa + xb + xc - xd);
/* 203 */     float y1 = d11 * x1 + d12 * x2 + d13 * x3;
/* 204 */     float y2 = d12 * x1 + d22 * x2 + d23 * x3;
/* 205 */     float y3 = d13 * x1 + d23 * x2 + d33 * x3;
/* 206 */     float ya = 0.25F * (y1 + y2 + y3);
/* 207 */     float yb = 0.25F * (y1 - y2 + y3);
/* 208 */     float yc = 0.25F * (y1 + y2 - y3);
/* 209 */     float yd = 0.25F * (y1 - y2 - y3);
/* 210 */     y00[i1] = y00[i1] + ya;
/* 211 */     y00[i1m] = y00[i1m] - yd;
/* 212 */     y01[i1] = y01[i1] + yb;
/* 213 */     y10[i1] = y10[i1] + yc;
/* 214 */     y01[i1m] = y01[i1m] - yc;
/* 215 */     y10[i1m] = y10[i1m] - yb;
/* 216 */     y11[i1] = y11[i1] + yd;
/* 217 */     y11[i1m] = y11[i1m] - ya;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/LocalLaplacianFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */