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
/*     */ public class HilbertTransformFilter
/*     */ {
/*     */   private static final int NMAX_DEFAULT = 100000;
/*     */   private static final float EMAX_DEFAULT = 0.01F;
/*     */   private static final float FMIN_DEFAULT = 0.025F;
/*     */   private static final float FMAX_DEFAULT = 0.475F;
/*     */   private float[] _filter;
/*     */   
/*     */   public HilbertTransformFilter() {
/*  43 */     this._filter = design(100000, 0.01F, 0.025F, 0.475F);
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
/*     */   public HilbertTransformFilter(int nmax, float emax, float fmin, float fmax) {
/*  67 */     this._filter = design(nmax, emax, fmin, fmax);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(int n, float[] x, float[] y) {
/*  77 */     Conv.conv(this._filter.length, -(this._filter.length - 1) / 2, this._filter, n, 0, x, n, 0, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/*  85 */     return this._filter.length;
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
/*     */   private static float idealFilter(float x) {
/*  98 */     if (x == 0.0F) return 0.0F; 
/*  99 */     float y = 1.5707964F * x;
/* 100 */     float s = ArrayMath.sin(y);
/* 101 */     return -s * s / y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[] design() {
/* 112 */     return design(100000, 0.01F, 0.025F, 0.475F);
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
/*     */   private static float[] design(int nmax, float emax, float fmin, float fmax) {
/* 137 */     Check.argument((nmax > 0), "nmax>0");
/* 138 */     Check.argument((emax > 0.0F), "emax>0.0f");
/* 139 */     Check.argument((fmin > 0.0F), "fmin>0.0f");
/* 140 */     Check.argument((fmax < 0.5F), "fmax<0.5f");
/* 141 */     Check.argument((fmin < fmax), "fmin<fmax");
/* 142 */     if (fmin < 0.5F - fmax) fmax = 0.5F - fmin; 
/* 143 */     float width = 2.0F * (0.5F - fmax);
/* 144 */     float emaxWindow = 0.5F * emax;
/* 145 */     KaiserWindow kw = KaiserWindow.fromErrorAndWidth(emaxWindow, width);
/* 146 */     int n = 1 + (int)ArrayMath.ceil(kw.getLength());
/* 147 */     if (n % 2 == 0) n++; 
/* 148 */     if (n > nmax) n = nmax; 
/* 149 */     if (n % 2 == 0) n--; 
/* 150 */     float length = n;
/* 151 */     kw = KaiserWindow.fromWidthAndLength(width, length);
/* 152 */     float[] f = new float[n];
/* 153 */     int k = (n - 1) / 2;
/* 154 */     for (int i = 0, j = n - 1; i < k; i++, j--) {
/* 155 */       float x = (i - k);
/* 156 */       float fideal = idealFilter(x);
/* 157 */       float window = (float)kw.evaluate(x);
/* 158 */       f[i] = fideal * window;
/* 159 */       f[j] = -f[i];
/*     */     } 
/* 161 */     f[k] = 0.0F;
/* 162 */     return f;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/HilbertTransformFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */