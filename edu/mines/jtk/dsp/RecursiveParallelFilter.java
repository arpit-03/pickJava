/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.Cdouble;
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
/*     */ public class RecursiveParallelFilter
/*     */ {
/*     */   private int _np;
/*     */   private int _nz;
/*     */   private int _nc;
/*     */   private int _nr;
/*     */   private float _c;
/*     */   private float _g;
/*     */   private int _n1;
/*     */   private int _n2;
/*     */   private Recursive2ndOrderFilter[] _f1;
/*     */   private Recursive2ndOrderFilter[] _f2;
/*     */   
/*     */   public RecursiveParallelFilter(Cdouble[] poles, Cdouble[] zeros, double gain) {
/*  53 */     init(poles, zeros, gain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyForward(float[] x, float[] y) {
/*  64 */     scale(this._c, x, y);
/*  65 */     for (int i1 = 0; i1 < this._n1; i1++) {
/*  66 */       this._f1[i1].accumulateForward(x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyReverse(float[] x, float[] y) {
/*  77 */     scale(this._c, x, y);
/*  78 */     for (int i1 = 0; i1 < this._n1; i1++) {
/*  79 */       this._f1[i1].accumulateReverse(x, y);
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
/*     */   public void applyForwardReverse(float[] x, float[] y) {
/*  93 */     scale(this._c * this._g, x, y);
/*  94 */     for (int i2 = 0; i2 < this._n2; i2 += 2) {
/*  95 */       this._f2[i2].accumulateForward(x, y);
/*  96 */       this._f2[i2 + 1].accumulateReverse(x, y);
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
/*     */   public void apply1Forward(float[][] x, float[][] y) {
/* 108 */     scale(this._c, x, y);
/* 109 */     for (int i1 = 0; i1 < this._n1; i1++) {
/* 110 */       this._f1[i1].accumulate1Forward(x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply1Reverse(float[][] x, float[][] y) {
/* 121 */     scale(this._c, x, y);
/* 122 */     for (int i1 = 0; i1 < this._n1; i1++) {
/* 123 */       this._f1[i1].accumulate1Reverse(x, y);
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
/*     */   public void apply1ForwardReverse(float[][] x, float[][] y) {
/* 135 */     scale(this._c * this._g, x, y);
/* 136 */     for (int i2 = 0; i2 < this._n2; i2 += 2) {
/* 137 */       this._f2[i2].accumulate1Forward(x, y);
/* 138 */       this._f2[i2 + 1].accumulate1Reverse(x, y);
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
/*     */   public void apply2Forward(float[][] x, float[][] y) {
/* 150 */     scale(this._c, x, y);
/* 151 */     for (int i1 = 0; i1 < this._n1; i1++) {
/* 152 */       this._f1[i1].accumulate2Forward(x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply2Reverse(float[][] x, float[][] y) {
/* 163 */     scale(this._c, x, y);
/* 164 */     for (int i1 = 0; i1 < this._n1; i1++) {
/* 165 */       this._f1[i1].accumulate2Reverse(x, y);
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
/*     */   public void apply2ForwardReverse(float[][] x, float[][] y) {
/* 177 */     scale(this._c * this._g, x, y);
/* 178 */     for (int i2 = 0; i2 < this._n2; i2 += 2) {
/* 179 */       this._f2[i2].accumulate2Forward(x, y);
/* 180 */       this._f2[i2 + 1].accumulate2Reverse(x, y);
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
/*     */   public void apply1Forward(float[][][] x, float[][][] y) {
/* 192 */     scale(this._c, x, y);
/* 193 */     for (int i1 = 0; i1 < this._n1; i1++) {
/* 194 */       this._f1[i1].accumulate1Forward(x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply1Reverse(float[][][] x, float[][][] y) {
/* 205 */     scale(this._c, x, y);
/* 206 */     for (int i1 = 0; i1 < this._n1; i1++) {
/* 207 */       this._f1[i1].accumulate1Reverse(x, y);
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
/*     */   public void apply1ForwardReverse(float[][][] x, float[][][] y) {
/* 219 */     scale(this._c * this._g, x, y);
/* 220 */     for (int i2 = 0; i2 < this._n2; i2 += 2) {
/* 221 */       this._f2[i2].accumulate1Forward(x, y);
/* 222 */       this._f2[i2 + 1].accumulate1Reverse(x, y);
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
/*     */   public void apply2Forward(float[][][] x, float[][][] y) {
/* 234 */     scale(this._c, x, y);
/* 235 */     for (int i1 = 0; i1 < this._n1; i1++) {
/* 236 */       this._f1[i1].accumulate2Forward(x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply2Reverse(float[][][] x, float[][][] y) {
/* 247 */     scale(this._c, x, y);
/* 248 */     for (int i1 = 0; i1 < this._n1; i1++) {
/* 249 */       this._f1[i1].accumulate2Reverse(x, y);
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
/*     */   public void apply2ForwardReverse(float[][][] x, float[][][] y) {
/* 261 */     scale(this._c * this._g, x, y);
/* 262 */     for (int i2 = 0; i2 < this._n2; i2 += 2) {
/* 263 */       this._f2[i2].accumulate2Forward(x, y);
/* 264 */       this._f2[i2 + 1].accumulate2Reverse(x, y);
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
/*     */   public void apply3Forward(float[][][] x, float[][][] y) {
/* 276 */     scale(this._c, x, y);
/* 277 */     for (int i1 = 0; i1 < this._n1; i1++) {
/* 278 */       this._f1[i1].accumulate3Forward(x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply3Reverse(float[][][] x, float[][][] y) {
/* 289 */     scale(this._c, x, y);
/* 290 */     for (int i1 = 0; i1 < this._n1; i1++) {
/* 291 */       this._f1[i1].accumulate3Reverse(x, y);
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
/*     */   public void apply3ForwardReverse(float[][][] x, float[][][] y) {
/* 303 */     scale(this._c * this._g, x, y);
/* 304 */     for (int i2 = 0; i2 < this._n2; i2 += 2) {
/* 305 */       this._f2[i2].accumulate3Forward(x, y);
/* 306 */       this._f2[i2 + 1].accumulate3Reverse(x, y);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyFrf(float[] x, float[] y) {
/* 316 */     scale(this._c * this._g, x, y);
/* 317 */     for (int i2 = 0; i2 < this._n2; i2 += 2) {
/* 318 */       this._f2[i2].accumulateForward(x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyFrr(float[] x, float[] y) {
/* 329 */     scale(this._c * this._g, x, y);
/* 330 */     for (int i2 = 0; i2 < this._n2; i2 += 2)
/*     */     {
/* 332 */       this._f2[i2 + 1].accumulateReverse(x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RecursiveParallelFilter() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init(Cdouble[] poles, Cdouble[] zeros, double gain) {
/* 345 */     poles = nonZero(poles);
/* 346 */     zeros = nonZero(zeros);
/*     */ 
/*     */     
/* 349 */     Check.argument((zeros.length <= poles.length), "number of non-zero zeros does not exceed number of non-zero poles");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 354 */     Check.argument(polesUnique(poles), "all poles are unique");
/*     */ 
/*     */     
/* 357 */     this._np = poles.length;
/* 358 */     this._nz = zeros.length;
/*     */ 
/*     */     
/* 361 */     poles = sort(poles);
/* 362 */     zeros = sort(zeros);
/*     */ 
/*     */     
/* 365 */     this._nc = 0;
/* 366 */     this._nr = 0;
/* 367 */     for (int ip = 0; ip < this._np; ip++) {
/* 368 */       if ((poles[ip]).i != 0.0D) {
/* 369 */         this._nc++;
/*     */       } else {
/* 371 */         this._nr++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     this._n1 = this._nr + this._nc / 2;
/* 379 */     this._n2 = 2 * this._n1;
/* 380 */     this._f1 = new Recursive2ndOrderFilter[this._n1];
/* 381 */     this._f2 = new Recursive2ndOrderFilter[this._n2];
/* 382 */     double c = (this._nz == this._np) ? gain : 0.0D;
/* 383 */     for (int i1 = 0, i2 = 0, jp = 0; i1 < this._n1; jp++) {
/* 384 */       double fb0, fb1, fb2, rb0, rb1, rb2, b0, b1, b2, a1, a2; Cdouble pj = poles[jp];
/* 385 */       Cdouble hi = hi(pj, poles, zeros, gain);
/* 386 */       Cdouble hj = hr(pj, poles, zeros, gain);
/* 387 */       Cdouble hihj = hi.times(hj);
/*     */       
/* 389 */       if (pj.i == 0.0D) {
/* 390 */         a1 = -pj.r;
/* 391 */         a2 = 0.0D;
/* 392 */         b0 = hj.r;
/* 393 */         b1 = 0.0D;
/* 394 */         b2 = 0.0D;
/* 395 */         fb0 = hihj.r;
/* 396 */         fb1 = 0.0D;
/* 397 */         fb2 = 0.0D;
/* 398 */         rb0 = 0.0D;
/* 399 */         rb1 = -fb0 * a1;
/* 400 */         rb2 = 0.0D;
/*     */       } else {
/* 402 */         jp++;
/* 403 */         Cdouble qj = pj.inv();
/* 404 */         a1 = -2.0D * pj.r;
/* 405 */         a2 = pj.norm();
/* 406 */         b0 = hj.r - hj.i * qj.r / qj.i;
/* 407 */         b1 = hj.i / qj.i;
/* 408 */         b2 = 0.0D;
/* 409 */         fb0 = hihj.r - hihj.i * qj.r / qj.i;
/* 410 */         fb1 = hihj.i / qj.i;
/* 411 */         fb2 = 0.0D;
/* 412 */         rb0 = 0.0D;
/* 413 */         rb1 = fb1 - fb0 * a1;
/* 414 */         rb2 = -fb0 * a2;
/*     */       } 
/* 416 */       this._f1[i1++] = makeFilter(b0, b1, b2, a1, a2);
/* 417 */       this._f2[i2++] = makeFilter(fb0, fb1, fb2, a1, a2);
/* 418 */       this._f2[i2++] = makeFilter(rb0, rb1, rb2, a1, a2);
/* 419 */       if (this._nz == this._np)
/* 420 */         c -= b0; 
/*     */     } 
/* 422 */     this._c = (float)c;
/* 423 */     this._g = (float)gain;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Recursive2ndOrderFilter makeFilter(double b0, double b1, double b2, double a1, double a2) {
/* 428 */     return new Recursive2ndOrderFilter((float)b0, (float)b1, (float)b2, (float)a1, (float)a2);
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
/*     */   private Cdouble hi(Cdouble z, Cdouble[] poles, Cdouble[] zeros, double gain) {
/* 452 */     Cdouble c1 = new Cdouble(1.0D, 0.0D);
/* 453 */     Cdouble hz = new Cdouble(c1);
/* 454 */     for (int iz = 0; iz < this._nz; iz++) {
/* 455 */       Cdouble zi = zeros[iz];
/* 456 */       hz.timesEquals(c1.minus(zi.times(z)));
/*     */     } 
/* 458 */     Cdouble hp = new Cdouble(c1);
/* 459 */     for (int ip = 0; ip < this._np; ip++) {
/* 460 */       Cdouble pi = poles[ip];
/* 461 */       hp.timesEquals(c1.minus(pi.times(z)));
/*     */     } 
/* 463 */     return hz.over(hp).times(gain);
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
/*     */   private Cdouble hr(Cdouble polej, Cdouble[] poles, Cdouble[] zeros, double gain) {
/* 476 */     Cdouble pj = polej;
/* 477 */     Cdouble qj = pj.inv();
/* 478 */     Cdouble c1 = new Cdouble(1.0D, 0.0D);
/* 479 */     Cdouble hz = new Cdouble(c1);
/* 480 */     for (int iz = 0; iz < this._nz; iz++) {
/* 481 */       Cdouble zi = zeros[iz];
/* 482 */       hz.timesEquals(c1.minus(zi.times(qj)));
/*     */     } 
/* 484 */     Cdouble hp = new Cdouble(c1);
/* 485 */     for (int ip = 0; ip < this._np; ip++) {
/* 486 */       Cdouble pi = poles[ip];
/* 487 */       if (!pi.equals(pj) && !pi.equals(pj.conj()))
/* 488 */         hp.timesEquals(c1.minus(pi.times(qj))); 
/*     */     } 
/* 490 */     return hz.over(hp).times(gain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean polesUnique(Cdouble[] poles) {
/* 497 */     int np = poles.length;
/* 498 */     for (int ip = 0; ip < np; ip++) {
/* 499 */       Cdouble pi = poles[ip];
/* 500 */       for (int jp = ip + 1; jp < np; jp++) {
/* 501 */         Cdouble pj = poles[jp];
/* 502 */         if (pi.equals(pj))
/* 503 */           return false; 
/*     */       } 
/*     */     } 
/* 506 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Cdouble[] nonZero(Cdouble[] c) {
/* 513 */     int n = c.length;
/* 514 */     int m = 0;
/* 515 */     for (int i = 0; i < n; i++) {
/* 516 */       if ((c[i]).r != 0.0D || (c[i]).i != 0.0D)
/* 517 */         m++; 
/*     */     } 
/* 519 */     Cdouble[] d = new Cdouble[m];
/* 520 */     m = 0;
/* 521 */     for (int j = 0; j < n; j++) {
/* 522 */       if ((c[j]).r != 0.0D || (c[j]).i != 0.0D)
/* 523 */         d[m++] = c[j]; 
/*     */     } 
/* 525 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Cdouble[] sort(Cdouble[] c) {
/* 535 */     int n = c.length;
/* 536 */     Cdouble[] cs = new Cdouble[n];
/* 537 */     int ns = 0; int i;
/* 538 */     for (i = 0; i < n; i++) {
/* 539 */       if (!c[i].isReal()) {
/* 540 */         Cdouble cc = c[i].conj();
/* 541 */         int j = 0;
/* 542 */         while (j < n && !cc.equals(c[j]))
/* 543 */           j++; 
/* 544 */         Check.argument((j < n), "complex " + c[i] + " has a conjugate mate");
/* 545 */         if (i < j) {
/* 546 */           cs[ns++] = c[i];
/* 547 */           cs[ns++] = c[j];
/*     */         } 
/*     */       } 
/*     */     } 
/* 551 */     for (i = 0; i < n; i++) {
/* 552 */       if (c[i].isReal())
/* 553 */         cs[ns++] = c[i]; 
/*     */     } 
/* 555 */     return cs;
/*     */   }
/*     */   
/*     */   private static void scale(float s, float[] x, float[] y) {
/* 559 */     int n1 = y.length;
/* 560 */     for (int i1 = 0; i1 < n1; i1++)
/* 561 */       y[i1] = s * x[i1]; 
/*     */   }
/*     */   
/*     */   private static void scale(float s, float[][] x, float[][] y) {
/* 565 */     int n2 = y.length;
/* 566 */     int n1 = (y[0]).length;
/* 567 */     for (int i2 = 0; i2 < n2; i2++) {
/* 568 */       float[] x2 = x[i2];
/* 569 */       float[] y2 = y[i2];
/* 570 */       for (int i1 = 0; i1 < n1; i1++)
/* 571 */         y2[i1] = s * x2[i1]; 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void scale(float s, float[][][] x, float[][][] y) {
/* 576 */     int n3 = y.length;
/* 577 */     int n2 = (y[0]).length;
/* 578 */     int n1 = (y[0][0]).length;
/* 579 */     for (int i3 = 0; i3 < n3; i3++) {
/* 580 */       float[][] x3 = x[i3];
/* 581 */       float[][] y3 = y[i3];
/* 582 */       for (int i2 = 0; i2 < n2; i2++) {
/* 583 */         float[] x32 = x3[i2];
/* 584 */         float[] y32 = y3[i2];
/* 585 */         for (int i1 = 0; i1 < n1; i1++)
/* 586 */           y32[i1] = s * x32[i1]; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/RecursiveParallelFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */