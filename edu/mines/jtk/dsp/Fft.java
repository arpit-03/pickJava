/*      */ package edu.mines.jtk.dsp;
/*      */ 
/*      */ import edu.mines.jtk.util.ArrayMath;
/*      */ import edu.mines.jtk.util.Check;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Fft
/*      */ {
/*      */   private FftReal _fft1r;
/*      */   private FftComplex _fft1c;
/*      */   private FftComplex _fft2;
/*      */   private FftComplex _fft3;
/*      */   private Sampling _sx1;
/*      */   private Sampling _sx2;
/*      */   private Sampling _sx3;
/*      */   private Sampling _sk1;
/*      */   private Sampling _sk2;
/*      */   private Sampling _sk3;
/*      */   private int _sign1;
/*      */   private int _sign2;
/*      */   private int _sign3;
/*      */   private int _nfft1;
/*      */   private int _nfft2;
/*      */   private int _nfft3;
/*      */   private int _padding1;
/*      */   private int _padding2;
/*      */   private int _padding3;
/*      */   private boolean _center1;
/*      */   private boolean _center2;
/*      */   private boolean _center3;
/*      */   private boolean _complex;
/*      */   private boolean _overwrite;
/*      */   
/*      */   public Fft(float[] f) {
/*   98 */     this(f.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fft(float[][] f) {
/*  109 */     this((f[0]).length, f.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fft(float[][][] f) {
/*  120 */     this((f[0][0]).length, (f[0]).length, f.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fft(boolean complex, float[] f) {
/*  132 */     this(f.length / 2);
/*  133 */     setComplex(complex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fft(boolean complex, float[][] f) {
/*  145 */     this((f[0]).length / 2, f.length);
/*  146 */     setComplex(complex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fft(boolean complex, float[][][] f) {
/*  158 */     this((f[0][0]).length / 2, (f[0]).length, f.length);
/*  159 */     setComplex(complex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fft(int nx1) {
/*  168 */     this(new Sampling(nx1, 1.0D, 0.0D));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fft(int nx1, int nx2) {
/*  178 */     this(new Sampling(nx1, 1.0D, 0.0D), new Sampling(nx2, 1.0D, 0.0D));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fft(int nx1, int nx2, int nx3) {
/*  190 */     this(new Sampling(nx1, 1.0D, 0.0D), new Sampling(nx2, 1.0D, 0.0D), new Sampling(nx3, 1.0D, 0.0D));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fft(Sampling sx1) {
/*  200 */     this(sx1, (Sampling)null, (Sampling)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fft(Sampling sx1, Sampling sx2) {
/*  209 */     this(sx1, sx2, (Sampling)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Fft(Sampling sx1, Sampling sx2, Sampling sx3) {
/*  219 */     this._sx1 = sx1;
/*  220 */     this._sx2 = sx2;
/*  221 */     this._sx3 = sx3;
/*  222 */     this._sign1 = -1;
/*  223 */     this._sign2 = -1;
/*  224 */     this._sign3 = -1;
/*  225 */     updateSampling1();
/*  226 */     updateSampling2();
/*  227 */     updateSampling3();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setComplex(boolean complex) {
/*  236 */     if (this._complex != complex) {
/*  237 */       this._complex = complex;
/*  238 */       updateSampling1();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOverwrite(boolean overwrite) {
/*  253 */     this._overwrite = overwrite;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Sampling getFrequencySampling1() {
/*  261 */     return this._sk1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Sampling getFrequencySampling2() {
/*  269 */     return this._sk2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Sampling getFrequencySampling3() {
/*  277 */     return this._sk3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSign(int sign) {
/*  287 */     setSign1(sign);
/*  288 */     setSign2(sign);
/*  289 */     setSign3(sign);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSign1(int sign) {
/*  299 */     this._sign1 = (sign >= 0) ? 1 : -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSign2(int sign) {
/*  309 */     this._sign2 = (sign >= 0) ? 1 : -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSign3(int sign) {
/*  319 */     this._sign3 = (sign >= 0) ? 1 : -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCenter(boolean center) {
/*  331 */     setCenter1(center);
/*  332 */     setCenter2(center);
/*  333 */     setCenter3(center);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCenter1(boolean center) {
/*  345 */     if (this._center1 != center) {
/*  346 */       this._center1 = center;
/*  347 */       updateSampling1();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCenter2(boolean center) {
/*  360 */     if (this._center2 != center) {
/*  361 */       this._center2 = center;
/*  362 */       updateSampling2();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCenter3(boolean center) {
/*  375 */     if (this._center3 != center) {
/*  376 */       this._center3 = center;
/*  377 */       updateSampling3();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPadding(int padding) {
/*  388 */     setPadding1(padding);
/*  389 */     setPadding2(padding);
/*  390 */     setPadding3(padding);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPadding1(int padding) {
/*  400 */     if (this._padding1 != padding) {
/*  401 */       this._padding1 = padding;
/*  402 */       updateSampling1();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPadding2(int padding) {
/*  413 */     if (this._padding2 != padding) {
/*  414 */       this._padding2 = padding;
/*  415 */       updateSampling2();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPadding3(int padding) {
/*  426 */     if (this._padding3 != padding) {
/*  427 */       this._padding3 = padding;
/*  428 */       updateSampling3();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] applyForward(float[] f) {
/*  438 */     ensureSamplingX1(f);
/*  439 */     float[] fpad = pad(f);
/*  440 */     if (this._complex) {
/*  441 */       this._fft1c.complexToComplex(this._sign1, fpad, fpad);
/*      */     } else {
/*  443 */       this._fft1r.realToComplex(this._sign1, fpad, fpad);
/*      */     } 
/*  445 */     phase(fpad);
/*  446 */     center(fpad);
/*  447 */     return fpad;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[][] applyForward(float[][] f) {
/*  456 */     ensureSamplingX2(f);
/*  457 */     float[][] fpad = pad(f);
/*  458 */     int nx2 = this._sx2.getCount();
/*  459 */     if (this._complex) {
/*  460 */       this._fft1c.complexToComplex1(this._sign1, nx2, fpad, fpad);
/*  461 */       this._fft2.complexToComplex2(this._sign2, this._nfft1, fpad, fpad);
/*      */     } else {
/*  463 */       this._fft1r.realToComplex1(this._sign1, nx2, fpad, fpad);
/*  464 */       this._fft2.complexToComplex2(this._sign2, this._nfft1 / 2 + 1, fpad, fpad);
/*      */     } 
/*  466 */     phase(fpad);
/*  467 */     center(fpad);
/*  468 */     return fpad;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[][][] applyForward(float[][][] f) {
/*  477 */     ensureSamplingX3(f);
/*  478 */     float[][][] fpad = pad(f);
/*  479 */     int nx2 = this._sx2.getCount();
/*  480 */     int nx3 = this._sx3.getCount();
/*  481 */     if (this._complex) {
/*  482 */       this._fft1c.complexToComplex1(this._sign1, nx2, nx3, fpad, fpad);
/*  483 */       this._fft2.complexToComplex2(this._sign2, this._nfft1, nx3, fpad, fpad);
/*  484 */       this._fft3.complexToComplex3(this._sign3, this._nfft1, this._nfft2, fpad, fpad);
/*      */     } else {
/*  486 */       this._fft1r.realToComplex1(this._sign1, nx2, nx3, fpad, fpad);
/*  487 */       this._fft2.complexToComplex2(this._sign2, this._nfft1 / 2 + 1, nx3, fpad, fpad);
/*  488 */       this._fft3.complexToComplex3(this._sign3, this._nfft1 / 2 + 1, this._nfft2, fpad, fpad);
/*      */     } 
/*  490 */     phase(fpad);
/*  491 */     center(fpad);
/*  492 */     return fpad;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] applyInverse(float[] g) {
/*  501 */     ensureSamplingK1(g);
/*  502 */     int nx1 = this._sx1.getCount();
/*  503 */     float[] gpad = this._overwrite ? g : ArrayMath.copy(g);
/*  504 */     uncenter(gpad);
/*  505 */     unphase(gpad);
/*  506 */     if (this._complex) {
/*  507 */       this._fft1c.complexToComplex(-this._sign1, gpad, gpad);
/*  508 */       this._fft1c.scale(nx1, gpad);
/*  509 */       return ArrayMath.ccopy(nx1, gpad);
/*      */     } 
/*  511 */     this._fft1r.complexToReal(-this._sign1, gpad, gpad);
/*  512 */     this._fft1r.scale(nx1, gpad);
/*  513 */     return ArrayMath.copy(nx1, gpad);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[][] applyInverse(float[][] g) {
/*  523 */     ensureSamplingK2(g);
/*  524 */     float[][] gpad = this._overwrite ? g : ArrayMath.copy(g);
/*  525 */     int nx1 = this._sx1.getCount();
/*  526 */     int nx2 = this._sx2.getCount();
/*  527 */     uncenter(gpad);
/*  528 */     unphase(gpad);
/*  529 */     if (this._complex) {
/*  530 */       this._fft2.complexToComplex2(-this._sign2, this._nfft1, gpad, gpad);
/*  531 */       this._fft2.scale(this._nfft1, nx2, gpad);
/*  532 */       this._fft1c.complexToComplex1(-this._sign1, nx2, gpad, gpad);
/*  533 */       this._fft1c.scale(nx1, nx2, gpad);
/*  534 */       return ArrayMath.ccopy(nx1, nx2, gpad);
/*      */     } 
/*  536 */     this._fft2.complexToComplex2(-this._sign2, this._nfft1 / 2 + 1, gpad, gpad);
/*  537 */     this._fft2.scale(this._nfft1 / 2 + 1, nx2, gpad);
/*  538 */     this._fft1r.complexToReal1(-this._sign1, nx2, gpad, gpad);
/*  539 */     this._fft1r.scale(nx1, nx2, gpad);
/*  540 */     return ArrayMath.copy(nx1, nx2, gpad);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[][][] applyInverse(float[][][] g) {
/*  550 */     ensureSamplingK3(g);
/*  551 */     float[][][] gpad = this._overwrite ? g : ArrayMath.copy(g);
/*  552 */     int nx1 = this._sx1.getCount();
/*  553 */     int nx2 = this._sx2.getCount();
/*  554 */     int nx3 = this._sx3.getCount();
/*  555 */     uncenter(gpad);
/*  556 */     unphase(gpad);
/*  557 */     if (this._complex) {
/*  558 */       this._fft3.complexToComplex3(-this._sign3, this._nfft1, this._nfft2, gpad, gpad);
/*  559 */       this._fft3.scale(this._nfft1, this._nfft2, nx3, gpad);
/*  560 */       this._fft2.complexToComplex2(-this._sign2, this._nfft1, nx3, gpad, gpad);
/*  561 */       this._fft2.scale(this._nfft1, nx2, nx3, gpad);
/*  562 */       this._fft1c.complexToComplex1(-this._sign1, nx2, nx3, gpad, gpad);
/*  563 */       this._fft1c.scale(nx1, nx2, nx3, gpad);
/*  564 */       return ArrayMath.ccopy(nx1, nx2, nx3, gpad);
/*      */     } 
/*  566 */     this._fft3.complexToComplex3(-this._sign3, this._nfft1 / 2 + 1, this._nfft2, gpad, gpad);
/*  567 */     this._fft3.scale(this._nfft1 / 2 + 1, this._nfft2, nx3, gpad);
/*  568 */     this._fft2.complexToComplex2(-this._sign2, this._nfft1 / 2 + 1, nx3, gpad, gpad);
/*  569 */     this._fft2.scale(this._nfft1 / 2 + 1, nx2, nx3, gpad);
/*  570 */     this._fft1r.complexToReal1(-this._sign1, nx2, nx3, gpad, gpad);
/*  571 */     this._fft1r.scale(nx1, nx2, nx3, gpad);
/*  572 */     return ArrayMath.copy(nx1, nx2, nx3, gpad);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateSampling1() {
/*      */     int nk;
/*      */     double dk, fk;
/*  591 */     if (this._sx1 == null)
/*      */       return; 
/*  593 */     int nx = this._sx1.getCount();
/*  594 */     double dx = this._sx1.getDelta();
/*  595 */     int npad = nx + this._padding1;
/*      */ 
/*      */     
/*  598 */     if (this._complex) {
/*  599 */       int nfft = FftComplex.nfftSmall(npad);
/*  600 */       dk = 1.0D / nfft * dx;
/*  601 */       if (this._center1) {
/*  602 */         boolean even = (nfft % 2 == 0);
/*  603 */         nk = even ? (nfft + 1) : nfft;
/*  604 */         fk = even ? (-0.5D / dx) : (-0.5D / dx + 0.5D * dk);
/*      */       } else {
/*  606 */         nk = nfft;
/*  607 */         fk = 0.0D;
/*      */       } 
/*  609 */       if (this._fft1c == null || this._nfft1 != nfft) {
/*  610 */         this._fft1c = new FftComplex(nfft);
/*  611 */         this._fft1r = null;
/*  612 */         this._nfft1 = nfft;
/*      */       } 
/*      */     } else {
/*  615 */       int nfft = FftReal.nfftSmall(npad);
/*  616 */       dk = 1.0D / nfft * dx;
/*  617 */       if (this._center1) {
/*  618 */         nk = nfft + 1;
/*  619 */         fk = -0.5D / dx;
/*      */       } else {
/*  621 */         nk = nfft / 2 + 1;
/*  622 */         fk = 0.0D;
/*      */       } 
/*  624 */       if (this._fft1r == null || this._nfft1 != nfft) {
/*  625 */         this._fft1r = new FftReal(nfft);
/*  626 */         this._fft1c = null;
/*  627 */         this._nfft1 = nfft;
/*      */       } 
/*      */     } 
/*  630 */     this._sk1 = new Sampling(nk, dk, fk);
/*      */   } private void updateSampling2() {
/*      */     double fk;
/*      */     int nk;
/*  634 */     if (this._sx2 == null)
/*      */       return; 
/*  636 */     int nx = this._sx2.getCount();
/*  637 */     double dx = this._sx2.getDelta();
/*  638 */     int npad = nx + this._padding2;
/*  639 */     int nfft = FftComplex.nfftSmall(npad);
/*  640 */     double dk = 1.0D / nfft * dx;
/*      */ 
/*      */     
/*  643 */     if (this._center2) {
/*  644 */       boolean even = (nfft % 2 == 0);
/*  645 */       nk = even ? (nfft + 1) : nfft;
/*  646 */       fk = even ? (-0.5D / dx) : (-0.5D / dx + 0.5D * dk);
/*      */     } else {
/*  648 */       nk = nfft;
/*  649 */       fk = 0.0D;
/*      */     } 
/*  651 */     if (this._fft2 == null || this._nfft2 != nfft) {
/*  652 */       this._fft2 = new FftComplex(nfft);
/*  653 */       this._nfft2 = nfft;
/*      */     } 
/*  655 */     this._sk2 = new Sampling(nk, dk, fk);
/*      */   } private void updateSampling3() {
/*      */     double fk;
/*      */     int nk;
/*  659 */     if (this._sx3 == null)
/*      */       return; 
/*  661 */     int nx = this._sx3.getCount();
/*  662 */     double dx = this._sx3.getDelta();
/*  663 */     int npad = nx + this._padding3;
/*  664 */     int nfft = FftComplex.nfftSmall(npad);
/*  665 */     double dk = 1.0D / nfft * dx;
/*      */ 
/*      */     
/*  668 */     if (this._center3) {
/*  669 */       boolean even = (nfft % 2 == 0);
/*  670 */       nk = even ? (nfft + 1) : nfft;
/*  671 */       fk = even ? (-0.5D / dx) : (-0.5D / dx + 0.5D * dk);
/*      */     } else {
/*  673 */       nk = nfft;
/*  674 */       fk = 0.0D;
/*      */     } 
/*  676 */     if (this._fft3 == null || this._nfft3 != nfft) {
/*  677 */       this._fft3 = new FftComplex(nfft);
/*  678 */       this._nfft3 = nfft;
/*      */     } 
/*  680 */     this._sk3 = new Sampling(nk, dk, fk);
/*      */   }
/*      */ 
/*      */   
/*      */   private float[] pad(float[] f) {
/*  685 */     int nk1 = this._sk1.getCount();
/*  686 */     float[] fpad = new float[2 * nk1];
/*  687 */     if (this._complex) {
/*  688 */       ArrayMath.ccopy(f.length / 2, f, fpad);
/*      */     } else {
/*  690 */       ArrayMath.copy(f.length, f, fpad);
/*      */     } 
/*  692 */     return fpad;
/*      */   }
/*      */   private float[][] pad(float[][] f) {
/*  695 */     int nk1 = this._sk1.getCount();
/*  696 */     int nk2 = this._sk2.getCount();
/*  697 */     float[][] fpad = new float[nk2][2 * nk1];
/*  698 */     if (this._complex) {
/*  699 */       ArrayMath.ccopy((f[0]).length / 2, f.length, f, fpad);
/*      */     } else {
/*  701 */       ArrayMath.copy((f[0]).length, f.length, f, fpad);
/*      */     } 
/*  703 */     return fpad;
/*      */   }
/*      */   private float[][][] pad(float[][][] f) {
/*  706 */     int nk1 = this._sk1.getCount();
/*  707 */     int nk2 = this._sk2.getCount();
/*  708 */     int nk3 = this._sk3.getCount();
/*  709 */     float[][][] fpad = new float[nk3][nk2][2 * nk1];
/*  710 */     if (this._complex) {
/*  711 */       ArrayMath.ccopy((f[0][0]).length / 2, (f[0]).length, f.length, f, fpad);
/*      */     } else {
/*  713 */       ArrayMath.copy((f[0][0]).length, (f[0]).length, f.length, f, fpad);
/*      */     } 
/*  715 */     return fpad;
/*      */   }
/*      */   
/*      */   private void ensureSamplingX1(float[] f) {
/*  719 */     Check.state((this._sx1 != null), "sampling sx1 exists for 1st dimension");
/*  720 */     int l1 = f.length;
/*  721 */     int n1 = this._sx1.getCount();
/*  722 */     if (this._complex)
/*  723 */       n1 *= 2; 
/*  724 */     Check.argument((n1 == l1), "array length consistent with sampling sx1");
/*      */   }
/*      */   private void ensureSamplingX2(float[][] f) {
/*  727 */     Check.state((this._sx2 != null), "sampling sx2 exists for 2nd dimension");
/*  728 */     ensureSamplingX1(f[0]);
/*  729 */     int l2 = f.length;
/*  730 */     int n2 = this._sx2.getCount();
/*  731 */     Check.argument((n2 == l2), "array length consistent with sampling sx2");
/*      */   }
/*      */   private void ensureSamplingX3(float[][][] f) {
/*  734 */     Check.state((this._sx3 != null), "sampling sx3 exists for 3rd dimension");
/*  735 */     ensureSamplingX2(f[0]);
/*  736 */     int l3 = f.length;
/*  737 */     int n3 = this._sx3.getCount();
/*  738 */     Check.argument((n3 == l3), "array length consistent with sampling sx3");
/*      */   }
/*      */   
/*      */   private void ensureSamplingK1(float[] f) {
/*  742 */     Check.state((this._sk1 != null), "sampling sk1 exists for 1st dimension");
/*  743 */     int l1 = f.length;
/*  744 */     int n1 = this._sk1.getCount();
/*  745 */     Check.argument((2 * n1 == l1), "array length consistent with sampling sk1");
/*      */   }
/*      */   private void ensureSamplingK2(float[][] f) {
/*  748 */     Check.state((this._sk2 != null), "sampling sk2 exists for 2nd dimension");
/*  749 */     ensureSamplingK1(f[0]);
/*  750 */     int l2 = f.length;
/*  751 */     int n2 = this._sk2.getCount();
/*  752 */     Check.argument((n2 == l2), "array length consistent with sampling sk2");
/*      */   }
/*      */   private void ensureSamplingK3(float[][][] f) {
/*  755 */     Check.state((this._sk3 != null), "sampling sk3 exists for 3rd dimension");
/*  756 */     ensureSamplingK2(f[0]);
/*  757 */     int l3 = f.length;
/*  758 */     int n3 = this._sk3.getCount();
/*  759 */     Check.argument((n3 == l3), "array length consistent with sampling sk3");
/*      */   }
/*      */   
/*      */   private void center(float[] f) {
/*  763 */     center1(f);
/*  764 */     if (this._center1 && !this._complex)
/*      */     {
/*      */ 
/*      */       
/*  768 */       creflect(this._nfft1 / 2, this._nfft1 / 2, f); } 
/*      */   }
/*      */   private void center1(float[] f) {
/*  771 */     if (!this._center1)
/*      */       return; 
/*  773 */     int nk1 = this._sk1.getCount();
/*  774 */     int nfft1 = this._nfft1;
/*  775 */     boolean even1 = (nfft1 % 2 == 0);
/*  776 */     if (this._complex) {
/*  777 */       if (even1)
/*      */       {
/*      */ 
/*      */         
/*  781 */         cswap(nfft1 / 2, 0, nfft1 / 2, f);
/*  782 */         f[2 * (nk1 - 1)] = f[0];
/*  783 */         f[2 * (nk1 - 1) + 1] = f[1];
/*      */       
/*      */       }
/*      */       else
/*      */       {
/*      */         
/*  789 */         cswap((nfft1 - 1) / 2, 0, (nfft1 + 1) / 2, f);
/*  790 */         crotateLeft((nfft1 + 1) / 2, (nfft1 - 1) / 2, f);
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  796 */       cshift(nfft1 / 2 + 1, 0, nfft1 / 2, f);
/*      */     } 
/*      */   }
/*      */   private void uncenter(float[] f) {
/*  800 */     uncenter1(f);
/*      */   }
/*      */   private void uncenter1(float[] f) {
/*  803 */     if (!this._center1)
/*      */       return; 
/*  805 */     int nfft1 = this._nfft1;
/*  806 */     boolean even1 = (nfft1 % 2 == 0);
/*  807 */     if (this._complex) {
/*  808 */       if (even1)
/*      */       {
/*      */ 
/*      */         
/*  812 */         cswap(nfft1 / 2, 0, nfft1 / 2, f);
/*      */       
/*      */       }
/*      */       else
/*      */       {
/*      */         
/*  818 */         crotateRight((nfft1 + 1) / 2, (nfft1 - 1) / 2, f);
/*  819 */         cswap((nfft1 - 1) / 2, 0, (nfft1 + 1) / 2, f);
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  825 */       cshift(nfft1 / 2 + 1, nfft1 / 2, 0, f);
/*      */     } 
/*      */   }
/*      */   private static void creflect(int n, int i, float[] f) {
/*  829 */     int ir = 2 * (i + 1), ii = ir + 1;
/*  830 */     int jr = 2 * (i - 1), ji = jr + 1;
/*  831 */     for (int k = 0; k < n; k++, ir += 2, ii += 2, jr -= 2, ji -= 2) {
/*  832 */       f[jr] = f[ir];
/*  833 */       f[ji] = -f[ii];
/*      */     } 
/*      */   }
/*      */   private static void cshift(int n, int i, int j, float[] f) {
/*  837 */     if (i < j) {
/*  838 */       int ir = 2 * (i + n - 1), ii = ir + 1;
/*  839 */       int jr = 2 * (j + n - 1), ji = jr + 1;
/*  840 */       for (int k = 0; k < n; k++, ir -= 2, ii -= 2, jr -= 2, ji -= 2) {
/*  841 */         f[jr] = f[ir];
/*  842 */         f[ji] = f[ii];
/*      */       } 
/*      */     } else {
/*  845 */       int ir = 2 * i, ii = ir + 1;
/*  846 */       int jr = 2 * j, ji = jr + 1;
/*  847 */       for (int k = 0; k < n; k++, ir += 2, ii += 2, jr += 2, ji += 2) {
/*  848 */         f[jr] = f[ir];
/*  849 */         f[ji] = f[ii];
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void cswap(int n, int i, int j, float[] f) {
/*  854 */     int ir = 2 * i, ii = ir + 1;
/*  855 */     int jr = 2 * j, ji = jr + 1;
/*  856 */     for (int k = 0; k < n; k++, ir += 2, ii += 2, jr += 2, ji += 2) {
/*  857 */       float fir = f[ir]; f[ir] = f[jr]; f[jr] = fir;
/*  858 */       float fii = f[ii]; f[ii] = f[ji]; f[ji] = fii;
/*      */     } 
/*      */   }
/*      */   private static void crotateLeft(int n, int j, float[] f) {
/*  862 */     float fjr = f[j * 2];
/*  863 */     float fji = f[j * 2 + 1];
/*  864 */     int i = j + 1, ir = 2 * i, ii = ir + 1;
/*  865 */     for (int k = 1; k < n; k++, ir += 2, ii += 2) {
/*  866 */       f[ir - 2] = f[ir];
/*  867 */       f[ii - 2] = f[ii];
/*      */     } 
/*  869 */     f[ir - 2] = fjr;
/*  870 */     f[ii - 2] = fji;
/*      */   }
/*      */   private static void crotateRight(int n, int j, float[] f) {
/*  873 */     int m = j + n - 1;
/*  874 */     float fmr = f[m * 2];
/*  875 */     float fmi = f[m * 2 + 1];
/*  876 */     int i = m, ir = 2 * i, ii = ir + 1;
/*  877 */     for (int k = 1; k < n; k++, ir -= 2, ii -= 2) {
/*  878 */       f[ir] = f[ir - 2];
/*  879 */       f[ii] = f[ii - 2];
/*      */     } 
/*  881 */     f[ir] = fmr;
/*  882 */     f[ii] = fmi;
/*      */   }
/*      */   
/*      */   private void center(float[][] f) {
/*  886 */     if (this._center1)
/*  887 */       for (int i2 = 0; i2 < this._nfft2; i2++) {
/*  888 */         center1(f[i2]);
/*      */       } 
/*  890 */     center2(f);
/*  891 */     if (this._center1 && !this._complex)
/*  892 */       creflect(this._nfft1 / 2, this._nfft1 / 2, f); 
/*      */   }
/*      */   private void uncenter(float[][] f) {
/*  895 */     uncenter2(f);
/*  896 */     if (this._center1)
/*  897 */       for (int i2 = 0; i2 < this._nfft2; i2++)
/*  898 */         uncenter1(f[i2]);  
/*      */   }
/*      */   
/*      */   private void center2(float[][] f) {
/*  902 */     if (!this._center2)
/*      */       return; 
/*  904 */     int nk2 = this._sk2.getCount();
/*  905 */     int nfft2 = this._nfft2;
/*  906 */     boolean even2 = (nfft2 % 2 == 0);
/*  907 */     if (even2) {
/*      */ 
/*      */ 
/*      */       
/*  911 */       cswap(nfft2 / 2, 0, nfft2 / 2, f);
/*  912 */       ArrayMath.ccopy(f[0], f[nk2 - 1]);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  918 */       cswap((nfft2 - 1) / 2, 0, (nfft2 + 1) / 2, f);
/*  919 */       crotateLeft((nfft2 + 1) / 2, (nfft2 - 1) / 2, f);
/*      */     } 
/*      */   }
/*      */   private void uncenter2(float[][] f) {
/*  923 */     if (!this._center2)
/*      */       return; 
/*  925 */     int nfft2 = this._nfft2;
/*  926 */     boolean even2 = (nfft2 % 2 == 0);
/*  927 */     if (even2) {
/*      */ 
/*      */ 
/*      */       
/*  931 */       cswap(nfft2 / 2, 0, nfft2 / 2, f);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  937 */       crotateRight((nfft2 + 1) / 2, (nfft2 - 1) / 2, f);
/*  938 */       cswap((nfft2 - 1) / 2, 0, (nfft2 + 1) / 2, f);
/*      */     } 
/*      */   }
/*      */   private static void creflect(int n, int i, float[][] f) {
/*  942 */     int n2 = f.length;
/*  943 */     for (int i2 = 0, j2 = n2 - 1; i2 < n2; i2++, j2--) {
/*  944 */       int ir = 2 * (i + 1), ii = ir + 1;
/*  945 */       int jr = 2 * (i - 1), ji = jr + 1;
/*  946 */       float[] fj2 = f[j2];
/*  947 */       float[] fi2 = f[i2];
/*  948 */       for (int k = 0; k < n; k++, ir += 2, ii += 2, jr -= 2, ji -= 2) {
/*  949 */         fj2[jr] = fi2[ir];
/*  950 */         fj2[ji] = -fi2[ii];
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void cswap(int n, int i, int j, float[][] f) {
/*  955 */     for (int k = 0; k < n; k++, i++, j++) {
/*  956 */       float[] fi = f[i]; f[i] = f[j]; f[j] = fi;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void crotateLeft(int n, int j, float[][] f) {
/*  964 */     float[] fj = f[j];
/*  965 */     int m = j + n;
/*      */     int i;
/*  967 */     for (i = j + 1; i < m; i++)
/*  968 */       f[i - 1] = f[i]; 
/*  969 */     f[i - 1] = fj;
/*      */   }
/*      */   private static void crotateRight(int n, int j, float[][] f) {
/*  972 */     int m = j + n - 1;
/*  973 */     float[] fm = f[m];
/*      */     int i;
/*  975 */     for (i = m; i > j; i--)
/*  976 */       f[i] = f[i - 1]; 
/*  977 */     f[i] = fm;
/*      */   }
/*      */   
/*      */   private void center(float[][][] f) {
/*  981 */     if (this._center1)
/*  982 */       for (int i3 = 0; i3 < this._nfft3; i3++) {
/*  983 */         for (int i2 = 0; i2 < this._nfft2; i2++)
/*  984 */           center1(f[i3][i2]); 
/*      */       }  
/*  986 */     if (this._center2)
/*  987 */       for (int i3 = 0; i3 < this._nfft3; i3++) {
/*  988 */         center2(f[i3]);
/*      */       } 
/*  990 */     center3(f);
/*  991 */     if (this._center1 && !this._complex)
/*  992 */       creflect(this._nfft1 / 2, this._nfft1 / 2, f); 
/*      */   }
/*      */   private void uncenter(float[][][] f) {
/*  995 */     uncenter3(f);
/*  996 */     if (this._center2)
/*  997 */       for (int i3 = 0; i3 < this._nfft3; i3++) {
/*  998 */         uncenter2(f[i3]);
/*      */       } 
/* 1000 */     if (this._center1)
/* 1001 */       for (int i3 = 0; i3 < this._nfft3; i3++) {
/* 1002 */         for (int i2 = 0; i2 < this._nfft2; i2++)
/* 1003 */           uncenter1(f[i3][i2]); 
/*      */       }  
/*      */   }
/*      */   private void center3(float[][][] f) {
/* 1007 */     if (!this._center3)
/*      */       return; 
/* 1009 */     int nk3 = this._sk3.getCount();
/* 1010 */     int nfft3 = this._nfft3;
/* 1011 */     boolean even3 = (nfft3 % 2 == 0);
/* 1012 */     if (even3) {
/*      */ 
/*      */ 
/*      */       
/* 1016 */       cswap(nfft3 / 2, 0, nfft3 / 2, f);
/* 1017 */       ArrayMath.ccopy(f[0], f[nk3 - 1]);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1023 */       cswap((nfft3 - 1) / 2, 0, (nfft3 + 1) / 2, f);
/* 1024 */       crotateLeft((nfft3 + 1) / 2, (nfft3 - 1) / 2, f);
/*      */     } 
/*      */   }
/*      */   private void uncenter3(float[][][] f) {
/* 1028 */     if (!this._center3)
/*      */       return; 
/* 1030 */     int nfft3 = this._nfft3;
/* 1031 */     boolean even3 = (nfft3 % 2 == 0);
/* 1032 */     if (even3) {
/*      */ 
/*      */ 
/*      */       
/* 1036 */       cswap(nfft3 / 2, 0, nfft3 / 2, f);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1042 */       crotateRight((nfft3 + 1) / 2, (nfft3 - 1) / 2, f);
/* 1043 */       cswap((nfft3 - 1) / 2, 0, (nfft3 + 1) / 2, f);
/*      */     } 
/*      */   }
/*      */   private static void creflect(int n, int i, float[][][] f) {
/* 1047 */     int n2 = (f[0]).length;
/* 1048 */     int n3 = f.length;
/* 1049 */     for (int i3 = 0, j3 = n3 - 1; i3 < n3; i3++, j3--) {
/* 1050 */       for (int i2 = 0, j2 = n2 - 1; i2 < n2; i2++, j2--) {
/* 1051 */         int ir = 2 * (i + 1), ii = ir + 1;
/* 1052 */         int jr = 2 * (i - 1), ji = jr + 1;
/* 1053 */         float[] fj3j2 = f[j3][j2];
/* 1054 */         float[] fi3i2 = f[i3][i2];
/* 1055 */         for (int k = 0; k < n; k++, ir += 2, ii += 2, jr -= 2, ji -= 2) {
/* 1056 */           fj3j2[jr] = fi3i2[ir];
/* 1057 */           fj3j2[ji] = -fi3i2[ii];
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void cswap(int n, int i, int j, float[][][] f) {
/* 1063 */     for (int k = 0; k < n; k++, i++, j++) {
/* 1064 */       float[][] fi = f[i]; f[i] = f[j]; f[j] = fi;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void crotateLeft(int n, int j, float[][][] f) {
/* 1072 */     float[][] fj = f[j];
/* 1073 */     int m = j + n;
/*      */     int i;
/* 1075 */     for (i = j + 1; i < m; i++)
/* 1076 */       f[i - 1] = f[i]; 
/* 1077 */     f[i - 1] = fj;
/*      */   }
/*      */   private static void crotateRight(int n, int j, float[][][] f) {
/* 1080 */     int m = j + n - 1;
/* 1081 */     float[][] fm = f[m];
/*      */     int i;
/* 1083 */     for (i = m; i > j; i--)
/* 1084 */       f[i] = f[i - 1]; 
/* 1085 */     f[i] = fm;
/*      */   }
/*      */   
/*      */   private void phase(float[] f) {
/* 1089 */     phase(this._sign1, f);
/*      */   }
/*      */   private void unphase(float[] f) {
/* 1092 */     phase(-this._sign1, f);
/*      */   }
/*      */   private void phase(int sign1, float[] f) {
/* 1095 */     double fx = this._sx1.getFirst();
/* 1096 */     if (fx == 0.0D)
/*      */       return; 
/* 1098 */     int nk = this._complex ? this._nfft1 : (this._nfft1 / 2 + 1);
/* 1099 */     double dp = sign1 * 2.0D * Math.PI * this._sk1.getDelta() * fx;
/* 1100 */     for (int i = 0, ir = 0, ii = 1; i < nk; i++, ir += 2, ii += 2) {
/* 1101 */       float p = (float)(i * dp);
/* 1102 */       float cosp = ArrayMath.cos(p);
/* 1103 */       float sinp = ArrayMath.sin(p);
/* 1104 */       float fr = f[ir];
/* 1105 */       float fi = f[ii];
/* 1106 */       f[ir] = fr * cosp - fi * sinp;
/* 1107 */       f[ii] = fi * cosp + fr * sinp;
/*      */     } 
/*      */   }
/*      */   private void phase(float[][] f) {
/* 1111 */     phase(this._sign1, this._sign2, f);
/*      */   }
/*      */   private void unphase(float[][] f) {
/* 1114 */     phase(-this._sign1, -this._sign2, f);
/*      */   }
/*      */   private void phase(int sign1, int sign2, float[][] f) {
/* 1117 */     double fx1 = this._sx1.getFirst();
/* 1118 */     double fx2 = this._sx2.getFirst();
/* 1119 */     if (fx1 == 0.0D && fx2 == 0.0D)
/*      */       return; 
/* 1121 */     int nk1 = this._complex ? this._nfft1 : (this._nfft1 / 2 + 1);
/* 1122 */     int nk2 = this._nfft2;
/* 1123 */     double dp1 = sign1 * 2.0D * Math.PI * this._sk1.getDelta() * fx1;
/* 1124 */     double dp2 = sign2 * 2.0D * Math.PI * this._sk2.getDelta() * fx2;
/* 1125 */     for (int i2 = 0; i2 < nk2; i2++) {
/* 1126 */       double p2 = i2 * dp2;
/* 1127 */       float[] f2 = f[i2];
/* 1128 */       for (int i1 = 0, ir = 0, ii = 1; i1 < nk1; i1++, ir += 2, ii += 2) {
/* 1129 */         float p = (float)(i1 * dp1 + p2);
/* 1130 */         float cosp = ArrayMath.cos(p);
/* 1131 */         float sinp = ArrayMath.sin(p);
/* 1132 */         float fr = f2[ir];
/* 1133 */         float fi = f2[ii];
/* 1134 */         f2[ir] = fr * cosp - fi * sinp;
/* 1135 */         f2[ii] = fi * cosp + fr * sinp;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void phase(float[][][] f) {
/* 1140 */     phase(this._sign1, this._sign2, this._sign3, f);
/*      */   }
/*      */   private void unphase(float[][][] f) {
/* 1143 */     phase(-this._sign1, -this._sign2, -this._sign3, f);
/*      */   }
/*      */   private void phase(int sign1, int sign2, int sign3, float[][][] f) {
/* 1146 */     double fx1 = this._sx1.getFirst();
/* 1147 */     double fx2 = this._sx2.getFirst();
/* 1148 */     double fx3 = this._sx3.getFirst();
/* 1149 */     if (fx1 == 0.0D && fx2 == 0.0D && fx3 == 0.0D)
/*      */       return; 
/* 1151 */     int nk1 = this._complex ? this._nfft1 : (this._nfft1 / 2 + 1);
/* 1152 */     int nk2 = this._nfft2;
/* 1153 */     int nk3 = this._nfft3;
/* 1154 */     double dp1 = sign1 * 2.0D * Math.PI * this._sk1.getDelta() * fx1;
/* 1155 */     double dp2 = sign2 * 2.0D * Math.PI * this._sk2.getDelta() * fx2;
/* 1156 */     double dp3 = sign3 * 2.0D * Math.PI * this._sk3.getDelta() * fx3;
/* 1157 */     for (int i3 = 0; i3 < nk3; i3++) {
/* 1158 */       for (int i2 = 0; i2 < nk2; i2++) {
/* 1159 */         double p23 = i2 * dp2 + i3 * dp3;
/* 1160 */         float[] f32 = f[i3][i2];
/* 1161 */         for (int i1 = 0, ir = 0, ii = 1; i1 < nk1; i1++, ir += 2, ii += 2) {
/* 1162 */           float p = (float)(i1 * dp1 + p23);
/* 1163 */           float cosp = ArrayMath.cos(p);
/* 1164 */           float sinp = ArrayMath.sin(p);
/* 1165 */           float fr = f32[ir];
/* 1166 */           float fi = f32[ii];
/* 1167 */           f32[ir] = fr * cosp - fi * sinp;
/* 1168 */           f32[ii] = fi * cosp + fr * sinp;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/Fft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */