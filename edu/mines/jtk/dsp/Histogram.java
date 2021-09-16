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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Histogram
/*     */ {
/*     */   private float _vmin;
/*     */   private float _vmax;
/*     */   private boolean _computedMinMax;
/*     */   private Sampling _sbin;
/*     */   private long[] _h;
/*     */   private long _nin;
/*     */   private long _nlo;
/*     */   private long _nhi;
/*     */   
/*     */   public Histogram(float[] v) {
/*  58 */     initMinMax(v);
/*  59 */     init(v, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Histogram(float[] v, int nbin) {
/*  69 */     initMinMax(v);
/*  70 */     init(v, nbin);
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
/*     */   public Histogram(float[] v, float vmin, float vmax) {
/*  82 */     Check.argument((vmin <= vmax), "vmin<=vmax");
/*  83 */     initMinMax(vmin, vmax);
/*  84 */     init(v, 0);
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
/*     */   public Histogram(float[] v, float vmin, float vmax, int nbin) {
/*  96 */     Check.argument((vmin <= vmax), "vmin<=vmax");
/*  97 */     initMinMax(vmin, vmax);
/*  98 */     init(v, nbin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinValue() {
/* 106 */     return this._vmin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxValue() {
/* 114 */     return this._vmax;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBinCount() {
/* 122 */     return this._sbin.getCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBinDelta() {
/* 130 */     return this._sbin.getDelta();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBinFirst() {
/* 138 */     return this._sbin.getFirst();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sampling getBinSampling() {
/* 147 */     return this._sbin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long[] getCounts() {
/* 155 */     return ArrayMath.copy(this._h);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getDensities() {
/* 164 */     int nbin = getBinCount();
/* 165 */     float[] d = new float[nbin];
/* 166 */     double s = 1.0D / this._nin;
/* 167 */     for (int ibin = 0; ibin < nbin; ibin++)
/* 168 */       d[ibin] = (float)(s * this._h[ibin]); 
/* 169 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getInCount() {
/* 177 */     return this._nin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLowCount() {
/* 185 */     return this._nlo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getHighCount() {
/* 193 */     return this._nhi;
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
/*     */   private void initMinMax(float[] v) {
/* 209 */     int n = v.length;
/* 210 */     this._vmin = this._vmax = v[0];
/* 211 */     for (int i = 1; i < n; i++) {
/* 212 */       float vi = v[i];
/* 213 */       if (vi < this._vmin)
/* 214 */         this._vmin = vi; 
/* 215 */       if (vi > this._vmax)
/* 216 */         this._vmax = vi; 
/*     */     } 
/* 218 */     this._computedMinMax = true;
/*     */   }
/*     */   
/*     */   private void initMinMax(float vmin, float vmax) {
/* 222 */     this._vmin = vmin;
/* 223 */     this._vmax = vmax;
/* 224 */     this._computedMinMax = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] trim(float[] v) {
/*     */     float[] t;
/* 233 */     if (this._computedMinMax) {
/* 234 */       t = ArrayMath.copy(v);
/*     */     } else {
/* 236 */       int n = v.length;
/* 237 */       t = new float[n];
/* 238 */       int m = 0;
/* 239 */       for (int i = 0; i < n; i++) {
/* 240 */         float vi = v[i];
/* 241 */         if (this._vmin <= vi && vi <= this._vmax)
/* 242 */           t[m++] = vi; 
/*     */       } 
/* 244 */       if (m < n)
/* 245 */         t = ArrayMath.copy(m, t); 
/*     */     } 
/* 247 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(float[] v, int nbin) {
/* 257 */     double dbin = ((this._vmax - this._vmin) / ArrayMath.max(1, nbin));
/* 258 */     if (dbin == 0.0D) {
/* 259 */       dbin = ArrayMath.max(1.0D, 2.0D * ArrayMath.abs(this._vmin) * 1.1920928955078125E-7D);
/*     */     }
/*     */     
/* 262 */     if (nbin == 0) {
/*     */ 
/*     */       
/* 265 */       nbin = 1;
/*     */ 
/*     */       
/* 268 */       if (this._vmin < this._vmax) {
/*     */ 
/*     */         
/* 271 */         float[] t = trim(v);
/* 272 */         int j = t.length;
/*     */ 
/*     */         
/* 275 */         if (j > 0) {
/*     */ 
/*     */           
/* 278 */           int k25 = (int)ArrayMath.rint(0.25D * (j - 1));
/* 279 */           ArrayMath.quickPartialSort(k25, t);
/* 280 */           double v25 = t[k25];
/* 281 */           int k75 = (int)ArrayMath.rint(0.75D * (j - 1));
/* 282 */           ArrayMath.quickPartialSort(k75, t);
/* 283 */           double v75 = t[k75];
/*     */ 
/*     */           
/* 286 */           if (v25 < v75) {
/* 287 */             dbin = 2.0D * (v75 - v25) * ArrayMath.pow(j, -0.3333333333333333D);
/* 288 */             nbin = ArrayMath.max(1, (int)ArrayMath.floor((this._vmax - this._vmin) / dbin));
/* 289 */             dbin = ((this._vmax - this._vmin) / nbin);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 294 */     double fbin = this._vmin + 0.5D * dbin;
/* 295 */     this._sbin = new Sampling(nbin, dbin, fbin);
/*     */ 
/*     */     
/* 298 */     double vscl = 1.0D / dbin;
/* 299 */     int n = v.length;
/* 300 */     this._nlo = 0L;
/* 301 */     this._nhi = 0L;
/* 302 */     this._h = new long[nbin];
/* 303 */     this._nin = 0L;
/* 304 */     for (int i = 0; i < n; i++) {
/* 305 */       float vi = v[i];
/* 306 */       if (vi < this._vmin) {
/* 307 */         this._nlo++;
/* 308 */       } else if (vi > this._vmax) {
/* 309 */         this._nhi++;
/*     */       } else {
/* 311 */         int ibin = (int)ArrayMath.rint((vi - fbin) * vscl);
/* 312 */         if (ibin < 0) {
/* 313 */           ibin = 0;
/* 314 */         } else if (ibin >= nbin) {
/* 315 */           ibin = nbin - 1;
/*     */         } 
/* 317 */         this._h[ibin] = this._h[ibin] + 1L;
/* 318 */         this._nin++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/Histogram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */