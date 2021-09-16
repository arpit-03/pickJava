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
/*     */ public class FftFilter
/*     */ {
/*     */   private int _nx1;
/*     */   private int _nx2;
/*     */   private int _nx3;
/*     */   private int _nh1;
/*     */   private int _nh2;
/*     */   private int _nh3;
/*     */   private int _kh1;
/*     */   private int _kh2;
/*     */   private int _kh3;
/*     */   private int _nfft1;
/*     */   private int _nfft2;
/*     */   private int _nfft3;
/*     */   private FftReal _fft1;
/*     */   private FftComplex _fft2;
/*     */   private FftComplex _fft3;
/*     */   private float[] _h1;
/*     */   private float[] _h1fft;
/*     */   private float[][] _h2;
/*     */   private float[][] _h2fft;
/*     */   private float[][][] _h3;
/*     */   private float[][][] _h3fft;
/*     */   private Extrapolation _extrapolation;
/*     */   private boolean _filterCaching;
/*     */   
/*     */   public enum Extrapolation
/*     */   {
/*  74 */     ZERO_VALUE,
/*     */ 
/*     */ 
/*     */     
/*  78 */     ZERO_SLOPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FftFilter(float[] h) {
/*  87 */     this((h.length - 1) / 2, h);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FftFilter(int kh, float[] h)
/*     */   {
/* 329 */     this._extrapolation = Extrapolation.ZERO_VALUE; Check.argument((0 <= kh && kh < h.length), "index kh is valid"); this._nh1 = h.length; this._kh1 = kh; this._h1 = ArrayMath.copy(h); } public FftFilter(float[][] h) { this(((h[0]).length - 1) / 2, (h.length - 1) / 2, h); } public FftFilter(int kh1, int kh2, float[][] h) { this._extrapolation = Extrapolation.ZERO_VALUE; Check.argument((0 <= kh1 && kh1 < (h[0]).length), "index kh1 is valid"); Check.argument((0 <= kh2 && kh2 < h.length), "index kh2 is valid"); this._nh1 = (h[0]).length; this._nh2 = h.length; this._kh1 = kh1; this._kh2 = kh2; this._h2 = ArrayMath.copy(h); } public FftFilter(float[][][] h) { this(((h[0][0]).length - 1) / 2, ((h[0]).length - 1) / 2, (h.length - 1) / 2, h); } public FftFilter(int kh1, int kh2, int kh3, float[][][] h) { this._extrapolation = Extrapolation.ZERO_VALUE; Check.argument((0 <= kh1 && kh1 < (h[0][0]).length), "index kh1 is valid"); Check.argument((0 <= kh2 && kh2 < (h[0]).length), "index kh2 is valid"); Check.argument((0 <= kh3 && kh3 < h.length), "index kh3 is valid"); this._nh1 = (h[0][0]).length; this._nh2 = (h[0]).length; this._nh3 = h.length; this._kh1 = kh1; this._kh2 = kh2; this._kh3 = kh3; this._h3 = ArrayMath.copy(h); }
/*     */   public void setExtrapolation(Extrapolation extrapolation) { this._extrapolation = extrapolation; }
/*     */   public void setFilterCaching(boolean filterCaching) { this._filterCaching = filterCaching; }
/*     */   public float[] apply(float[] x) { float[] y = new float[x.length]; apply(x, y); return y; }
/* 333 */   public void apply(float[] x, float[] y) { Check.state((this._h1 != null), "1D filter is available"); int nx1 = x.length; updateFfts(nx1); float[] xfft = new float[this._nfft1 + 2]; ArrayMath.copy(nx1, x, xfft); extrapolate(xfft); this._fft1.realToComplex(-1, xfft, xfft); int nk1 = this._nfft1 / 2 + 1; for (int ik1 = 0, k1r = 0, k1i = 1; ik1 < nk1; ik1++, k1r += 2, k1i += 2) { float xr = xfft[k1r]; float xi = xfft[k1i]; float hr = this._h1fft[k1r]; float hi = this._h1fft[k1i]; xfft[k1r] = xr * hr - xi * hi; xfft[k1i] = xr * hi + xi * hr; }  if (!this._filterCaching) this._h1fft = null;  this._fft1.complexToReal(1, xfft, xfft); ArrayMath.copy(nx1, xfft, y); } private void updateFfts(int nx1) { if (this._fft1 == null || this._h1fft == null || this._nx1 != nx1)
/* 334 */     { this._nx1 = nx1;
/* 335 */       this._nx2 = 0;
/* 336 */       this._nx3 = 0;
/* 337 */       this._nfft1 = FftReal.nfftFast(this._nx1 + this._nh1);
/* 338 */       this._fft1 = new FftReal(this._nfft1);
/* 339 */       this._fft2 = null;
/* 340 */       this._fft3 = null;
/* 341 */       this._h1fft = new float[this._nfft1 + 2];
/* 342 */       this._h2fft = (float[][])null;
/* 343 */       this._h3fft = (float[][][])null;
/* 344 */       float scale = 1.0F / this._nfft1;
/* 345 */       for (int ih1 = 0; ih1 < this._nh1; ih1++) {
/* 346 */         int jh1 = ih1 - this._kh1;
/* 347 */         if (jh1 < 0) jh1 += this._nfft1; 
/* 348 */         this._h1fft[jh1] = scale * this._h1[ih1];
/*     */       } 
/* 350 */       this._fft1.realToComplex(-1, this._h1fft, this._h1fft); }  }
/*     */   public float[][] apply(float[][] x) { float[][] y = new float[x.length][(x[0]).length]; apply(x, y); return y; }
/*     */   public void apply(float[][] x, float[][] y) { Check.state((this._h2 != null), "2D filter is valid"); int nx1 = (x[0]).length; int nx2 = x.length; updateFfts(nx1, nx2); float[][] xfft = new float[this._nfft2][this._nfft1 + 2]; ArrayMath.copy(nx1, nx2, x, xfft); extrapolate(xfft); this._fft1.realToComplex1(-1, this._nfft2, xfft, xfft); this._fft2.complexToComplex2(-1, this._nfft1 / 2 + 1, xfft, xfft); int nk1 = this._nfft1 / 2 + 1; int nk2 = this._nfft2; for (int ik2 = 0; ik2 < nk2; ik2++) { float[] x2 = xfft[ik2]; float[] h2 = this._h2fft[ik2]; for (int ik1 = 0, k1r = 0, k1i = 1; ik1 < nk1; ik1++, k1r += 2, k1i += 2) { float xr = x2[k1r]; float xi = x2[k1i]; float hr = h2[k1r]; float hi = h2[k1i]; x2[k1r] = xr * hr - xi * hi; x2[k1i] = xr * hi + xi * hr; }  }  if (!this._filterCaching) this._h2fft = (float[][])null;  this._fft2.complexToComplex2(1, this._nfft1 / 2 + 1, xfft, xfft); this._fft1.complexToReal1(1, this._nfft2, xfft, xfft); ArrayMath.copy(nx1, nx2, xfft, y); }
/*     */   public float[][][] apply(float[][][] x) { float[][][] y = new float[x.length][(x[0]).length][(x[0][0]).length]; apply(x, y); return y; }
/*     */   public void apply(float[][][] x, float[][][] y) { Check.state((this._h3 != null), "3D filter is valid"); int nx1 = (x[0][0]).length; int nx2 = (x[0]).length; int nx3 = x.length; updateFfts(nx1, nx2, nx3); float[][][] xfft = new float[this._nfft3][this._nfft2][this._nfft1 + 2]; ArrayMath.copy(nx1, nx2, nx3, x, xfft); extrapolate(xfft); this._fft1.realToComplex1(-1, this._nfft2, this._nfft3, xfft, xfft); this._fft2.complexToComplex2(-1, this._nfft1 / 2 + 1, this._nfft3, xfft, xfft); this._fft3.complexToComplex3(-1, this._nfft1 / 2 + 1, this._nfft2, xfft, xfft); int nk1 = this._nfft1 / 2 + 1; int nk2 = this._nfft2; int nk3 = this._nfft3; for (int ik3 = 0; ik3 < nk3; ik3++) { for (int ik2 = 0; ik2 < nk2; ik2++) { float[] x32 = xfft[ik3][ik2]; float[] h32 = this._h3fft[ik3][ik2]; for (int ik1 = 0, k1r = 0, k1i = 1; ik1 < nk1; ik1++, k1r += 2, k1i += 2) { float xr = x32[k1r]; float xi = x32[k1i]; float hr = h32[k1r]; float hi = h32[k1i]; x32[k1r] = xr * hr - xi * hi; x32[k1i] = xr * hi + xi * hr; }  }  }  if (!this._filterCaching)
/* 355 */       this._h3fft = (float[][][])null;  this._fft3.complexToComplex3(1, this._nfft1 / 2 + 1, this._nfft2, xfft, xfft); this._fft2.complexToComplex2(1, this._nfft1 / 2 + 1, this._nfft3, xfft, xfft); this._fft1.complexToReal1(1, this._nfft2, this._nfft3, xfft, xfft); ArrayMath.copy(nx1, nx2, nx3, xfft, y); } private void updateFfts(int nx1, int nx2) { if (this._fft2 == null || this._h2fft == null || this._nx1 != nx1 || this._nx2 != nx2) {
/* 356 */       this._nx1 = nx1;
/* 357 */       this._nx2 = nx2;
/* 358 */       this._nx3 = 0;
/* 359 */       this._nfft1 = FftReal.nfftFast(this._nx1 + this._nh1);
/* 360 */       this._nfft2 = FftComplex.nfftFast(this._nx2 + this._nh2);
/* 361 */       this._fft1 = new FftReal(this._nfft1);
/* 362 */       this._fft2 = new FftComplex(this._nfft2);
/* 363 */       this._fft3 = null;
/* 364 */       this._h1fft = null;
/* 365 */       this._h2fft = new float[this._nfft2][this._nfft1 + 2];
/* 366 */       this._h3fft = (float[][][])null;
/* 367 */       float scale = 1.0F / this._nfft1 / this._nfft2;
/* 368 */       for (int ih2 = 0; ih2 < this._nh2; ih2++) {
/* 369 */         int jh2 = ih2 - this._kh2;
/* 370 */         if (jh2 < 0) jh2 += this._nfft2; 
/* 371 */         for (int ih1 = 0; ih1 < this._nh1; ih1++) {
/* 372 */           int jh1 = ih1 - this._kh1;
/* 373 */           if (jh1 < 0) jh1 += this._nfft1; 
/* 374 */           this._h2fft[jh2][jh1] = scale * this._h2[ih2][ih1];
/*     */         } 
/*     */       } 
/* 377 */       this._fft1.realToComplex1(-1, this._nfft2, this._h2fft, this._h2fft);
/* 378 */       this._fft2.complexToComplex2(-1, this._nfft1 / 2 + 1, this._h2fft, this._h2fft);
/*     */     }  }
/*     */ 
/*     */   
/*     */   private void updateFfts(int nx1, int nx2, int nx3) {
/* 383 */     if (this._fft3 == null || this._h3fft == null || this._nx1 != nx1 || this._nx2 != nx2 || this._nx3 != nx3) {
/* 384 */       this._nx1 = nx1;
/* 385 */       this._nx2 = nx2;
/* 386 */       this._nx3 = nx3;
/* 387 */       this._nfft1 = FftReal.nfftFast(this._nx1 + this._nh1);
/* 388 */       this._nfft2 = FftComplex.nfftFast(this._nx2 + this._nh2);
/* 389 */       this._nfft3 = FftComplex.nfftFast(this._nx3 + this._nh3);
/* 390 */       this._fft1 = new FftReal(this._nfft1);
/* 391 */       this._fft2 = new FftComplex(this._nfft2);
/* 392 */       this._fft3 = new FftComplex(this._nfft3);
/* 393 */       this._h1fft = null;
/* 394 */       this._h2fft = (float[][])null;
/* 395 */       this._h3fft = new float[this._nfft3][this._nfft2][this._nfft1 + 2];
/* 396 */       float scale = 1.0F / this._nfft1 / this._nfft2 / this._nfft3;
/* 397 */       for (int ih3 = 0; ih3 < this._nh3; ih3++) {
/* 398 */         int jh3 = ih3 - this._kh3;
/* 399 */         if (jh3 < 0) jh3 += this._nfft3; 
/* 400 */         for (int ih2 = 0; ih2 < this._nh2; ih2++) {
/* 401 */           int jh2 = ih2 - this._kh2;
/* 402 */           if (jh2 < 0) jh2 += this._nfft2; 
/* 403 */           for (int ih1 = 0; ih1 < this._nh1; ih1++) {
/* 404 */             int jh1 = ih1 - this._kh1;
/* 405 */             if (jh1 < 0) jh1 += this._nfft1; 
/* 406 */             this._h3fft[jh3][jh2][jh1] = scale * this._h3[ih3][ih2][ih1];
/*     */           } 
/*     */         } 
/*     */       } 
/* 410 */       this._fft1.realToComplex1(-1, this._nfft2, this._nfft3, this._h3fft, this._h3fft);
/* 411 */       this._fft2.complexToComplex2(-1, this._nfft1 / 2 + 1, this._nfft3, this._h3fft, this._h3fft);
/* 412 */       this._fft3.complexToComplex3(-1, this._nfft1 / 2 + 1, this._nfft2, this._h3fft, this._h3fft);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void extrapolate(float[] xfft) {
/* 417 */     if (this._extrapolation == Extrapolation.ZERO_SLOPE) {
/* 418 */       int mr1 = this._nx1 + this._kh1;
/* 419 */       float xr1 = xfft[this._nx1 - 1];
/* 420 */       for (int i1 = this._nx1; i1 < mr1; i1++)
/* 421 */         xfft[i1] = xr1; 
/* 422 */       int ml1 = this._nfft1 + this._kh1 - this._nh1 + 1;
/* 423 */       float xl1 = xfft[0];
/* 424 */       for (int i = ml1; i < this._nfft1; i++)
/* 425 */         xfft[i] = xl1; 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void extrapolate(float[][] xfft) {
/* 430 */     if (this._extrapolation == Extrapolation.ZERO_SLOPE) {
/* 431 */       for (int i2 = 0; i2 < this._nx2; i2++)
/* 432 */         extrapolate(xfft[i2]); 
/* 433 */       int mr2 = this._nx2 + this._kh2;
/* 434 */       float[] xr2 = xfft[this._nx2 - 1];
/* 435 */       for (int i = this._nx2; i < mr2; i++)
/* 436 */         ArrayMath.copy(xr2, xfft[i]); 
/* 437 */       int ml2 = this._nfft2 + this._kh2 - this._nh2 + 1;
/* 438 */       float[] xl2 = xfft[0];
/* 439 */       for (int j = ml2; j < this._nfft2; j++)
/* 440 */         ArrayMath.copy(xl2, xfft[j]); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void extrapolate(float[][][] xfft) {
/* 445 */     if (this._extrapolation == Extrapolation.ZERO_SLOPE) {
/* 446 */       for (int i3 = 0; i3 < this._nx3; i3++)
/* 447 */         extrapolate(xfft[i3]); 
/* 448 */       int mr3 = this._nx3 + this._kh3;
/* 449 */       float[][] xr3 = xfft[this._nx3 - 1];
/* 450 */       for (int i = this._nx3; i < mr3; i++)
/* 451 */         ArrayMath.copy(xr3, xfft[i]); 
/* 452 */       int ml3 = this._nfft3 + this._kh3 - this._nh3 + 1;
/* 453 */       float[][] xl3 = xfft[0];
/* 454 */       for (int j = ml3; j < this._nfft3; j++)
/* 455 */         ArrayMath.copy(xl3, xfft[j]); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/FftFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */