/*      */ package edu.mines.jtk.dsp;
/*      */ 
/*      */ import edu.mines.jtk.util.ArrayMath;
/*      */ import edu.mines.jtk.util.Parallel;
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
/*      */ public class LocalDiffusionKernel
/*      */ {
/*      */   public enum Stencil
/*      */   {
/*   69 */     D21,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   76 */     D22,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   83 */     D24,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   89 */     D33,
/*      */ 
/*      */ 
/*      */     
/*   93 */     D71,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   98 */     D91;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalDiffusionKernel() {
/*  105 */     this(Stencil.D22);
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
/*      */   public Stencil getStencil() {
/*      */     return this._stencil;
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
/*      */   public void setNumberOfPasses(int npass) {
/*      */     this._npass = npass;
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
/*      */   public void apply(float[][] x, float[][] y) {
/*      */     apply((Tensors2)null, 1.0F, x, y);
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
/*      */   public void apply(Tensors2 d, float[][] x, float[][] y) {
/*      */     apply(d, 1.0F, x, y);
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
/*      */   public void apply(float c, float[][] x, float[][] y) {
/*      */     apply((Tensors2)null, c, (float[][])null, x, y);
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
/*      */   public LocalDiffusionKernel(Stencil s) {
/*  337 */     this._npass = 1;
/*  338 */     this._parallel = true; this._stencil = s;
/*      */   } public void apply(Tensors2 d, float c, float[][] x, float[][] y) { apply(d, c, (float[][])null, x, y); } public void apply(float c, float[][] s, float[][] x, float[][] y) { apply((Tensors2)null, c, s, x, y); } public void apply(Tensors2 d, float c, float[][] s, float[][] x, float[][] y) { for (int ipass = 0; ipass < this._npass; ipass++) { if (ipass > 0) x = ArrayMath.copy(y);  if (d == null) d = IDENTITY_TENSORS2;  if (this._stencil == Stencil.D21) { apply21(c, s, x, y); } else if (this._stencil == Stencil.D22) { apply22(d, c, s, x, y); } else if (this._stencil == Stencil.D24) { apply24(d, c, s, x, y); } else if (this._stencil == Stencil.D33) { apply33(d, c, s, x, y); } else if (this._stencil == Stencil.D71) { apply71(d, c, s, x, y); } else if (this._stencil == Stencil.D91) { apply91(d, c, s, x, y); }  }  }
/*      */   public void apply(float[][][] x, float[][][] y) { apply((Tensors3)null, 1.0F, x, y); }
/*  341 */   private static void trace(String s) { System.out.println(s); }
/*      */   
/*      */   public void apply(Tensors3 d, float[][][] x, float[][][] y) {
/*      */     apply(d, 1.0F, x, y);
/*      */   } public void apply(float c, float[][][] x, float[][][] y) {
/*      */     apply((Tensors3)null, c, (float[][][])null, x, y);
/*  347 */   } private void apply(int i3, Tensors3 d, float c, float[][][] s, float[][][] x, float[][][] y) { if (this._stencil == Stencil.D21)
/*  348 */     { apply21(i3, c, s, x, y); }
/*  349 */     else if (this._stencil == Stencil.D22)
/*  350 */     { apply22(i3, d, c, s, x, y); }
/*  351 */     else { if (this._stencil == Stencil.D24)
/*      */       {
/*  353 */         throw new UnsupportedOperationException("Stencil.D24 not supported for 3D arrays");
/*      */       }
/*  355 */       if (this._stencil == Stencil.D33)
/*  356 */       { apply33(i3, d, c, s, x, y); }
/*  357 */       else if (this._stencil == Stencil.D71)
/*  358 */       { apply71(i3, d, c, s, x, y); }
/*  359 */       else if (this._stencil == Stencil.D91)
/*      */       
/*  361 */       { throw new UnsupportedOperationException("Stencil.D91 not supported for 3D arrays"); }  }  }
/*      */   public void apply(Tensors3 d, float c, float[][][] x, float[][][] y) { apply(d, c, (float[][][])null, x, y); } public void apply(float c, float[][][] s, float[][][] x, float[][][] y) { apply((Tensors3)null, c, s, x, y); } public void apply(Tensors3 d, float c, float[][][] s, float[][][] x, float[][][] y) { int n3 = x.length; int i3start = 0; int i3step = 1; int i3stop = n3; for (int ipass = 0; ipass < this._npass; ipass++) { if (ipass > 0)
/*      */         x = ArrayMath.copy(y);  if (d == null)
/*      */         d = IDENTITY_TENSORS3;  if (this._stencil == Stencil.D21) { i3start = 0; i3step = 2; i3stop = n3; } else if (this._stencil == Stencil.D22) { i3start = 1; i3step = 2; i3stop = n3; } else if (this._stencil == Stencil.D24) { i3start = 1; i3step = 4; i3stop = n3; } else if (this._stencil == Stencil.D33) { i3start = 1; i3step = 3; i3stop = n3 - 1; } else if (this._stencil == Stencil.D71) { i3start = 0; i3step = 7; i3stop = n3; }  if (this._parallel) { applyParallel(i3start, i3step, i3stop, d, c, s, x, y); } else { applySerial(i3start, 1, i3stop, d, c, s, x, y); }
/*      */        }
/*      */      } private static Tensors2 IDENTITY_TENSORS2 = new Tensors2() {
/*      */       public void getTensor(int i1, int i2, float[] d) { d[0] = 1.0F; d[1] = 0.0F; d[2] = 1.0F; }
/*      */     }; private static Tensors3 IDENTITY_TENSORS3 = new Tensors3() {
/*      */       public void getTensor(int i1, int i2, int i3, float[] d) { d[0] = 1.0F; d[1] = 0.0F; d[2] = 0.0F; d[3] = 1.0F; d[4] = 0.0F; d[5] = 1.0F; }
/*  370 */     }; private Stencil _stencil; private int _npass; private boolean _parallel; private void applySerial(int i3start, int i3step, int i3stop, Tensors3 d, float c, float[][][] s, float[][][] x, float[][][] y) { int i3; for (i3 = i3start; i3 < i3stop; i3 += i3step) {
/*  371 */       apply(i3, d, c, s, x, y);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void applyParallel(int i3start, int i3step, int i3stop, final Tensors3 d, final float c, final float[][][] s, final float[][][] x, final float[][][] y) {
/*  379 */     for (int i3pass = 0; i3pass < i3step; i3pass++, i3start++) {
/*  380 */       Parallel.loop(i3start, i3stop, i3step, new Parallel.LoopInt() {
/*      */             public void compute(int i3) {
/*  382 */               LocalDiffusionKernel.this.apply(i3, d, c, s, x, y);
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void apply21(float c, float[][] s, float[][] x, float[][] y) {
/*  392 */     int n1 = (x[0]).length;
/*  393 */     int n2 = x.length;
/*  394 */     for (int i2 = 0; i2 < n2; i2++) {
/*  395 */       int m2 = (i2 > 0) ? (i2 - 1) : 0;
/*  396 */       for (int i1 = 0; i1 < n1; i1++) {
/*  397 */         int m1 = (i1 > 0) ? (i1 - 1) : 0;
/*  398 */         float cs1 = c;
/*  399 */         float cs2 = c;
/*  400 */         if (s != null) {
/*  401 */           cs1 *= 0.5F * (s[i2][i1] + s[i2][m1]);
/*  402 */           cs2 *= 0.5F * (s[i2][i1] + s[m2][i1]);
/*      */         } 
/*  404 */         float x1 = x[i2][i1] - x[i2][m1];
/*  405 */         float x2 = x[i2][i1] - x[m2][i1];
/*  406 */         float y1 = cs1 * x1;
/*  407 */         float y2 = cs2 * x2;
/*  408 */         y[i2][i1] = y[i2][i1] + y1 + y2;
/*  409 */         y[i2][m1] = y[i2][m1] - y1;
/*  410 */         y[m2][i1] = y[m2][i1] - y2;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void apply21(int i3, float c, float[][][] s, float[][][] x, float[][][] y) {
/*  417 */     int n1 = (x[0][0]).length;
/*  418 */     int n2 = (x[0]).length;
/*  419 */     int m3 = (i3 > 0) ? (i3 - 1) : 0;
/*  420 */     for (int i2 = 0; i2 < n2; i2++) {
/*  421 */       int m2 = (i2 > 0) ? (i2 - 1) : 0;
/*  422 */       for (int i1 = 0; i1 < n1; i1++) {
/*  423 */         int m1 = (i1 > 0) ? (i1 - 1) : 0;
/*  424 */         float cs1 = c;
/*  425 */         float cs2 = c;
/*  426 */         float cs3 = c;
/*  427 */         if (s != null) {
/*  428 */           cs1 *= 0.5F * (s[i3][i2][i1] + s[i3][i2][m1]);
/*  429 */           cs2 *= 0.5F * (s[i3][i2][i1] + s[i3][m2][i1]);
/*  430 */           cs3 *= 0.5F * (s[i3][i2][i1] + s[m3][i2][i1]);
/*      */         } 
/*  432 */         float x1 = x[i3][i2][i1] - x[i3][i2][m1];
/*  433 */         float x2 = x[i3][i2][i1] - x[i3][m2][i1];
/*  434 */         float x3 = x[i3][i2][i1] - x[m3][i2][i1];
/*  435 */         float y1 = cs1 * x1;
/*  436 */         float y2 = cs2 * x2;
/*  437 */         float y3 = cs3 * x3;
/*  438 */         y[i3][i2][i1] = y[i3][i2][i1] + y1 + y2 + y3;
/*  439 */         y[i3][i2][m1] = y[i3][i2][m1] - y1;
/*  440 */         y[i3][m2][i1] = y[i3][m2][i1] - y2;
/*  441 */         y[m3][i2][i1] = y[m3][i2][i1] - y3;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void apply22(Tensors2 d, float c, float[][] s, float[][] x, float[][] y) {
/*  452 */     c *= 0.25F;
/*  453 */     int n1 = (x[0]).length;
/*  454 */     int n2 = x.length;
/*  455 */     float[] di = new float[3];
/*  456 */     for (int i2 = 1; i2 < n2; i2++) {
/*  457 */       float[] x0 = x[i2];
/*  458 */       float[] xm = x[i2 - 1];
/*  459 */       float[] y0 = y[i2];
/*  460 */       float[] ym = y[i2 - 1];
/*  461 */       for (int i1 = 1, m1 = 0; i1 < n1; i1++, m1++) {
/*  462 */         d.getTensor(i1, i2, di);
/*  463 */         float csi = (s != null) ? (c * s[i2][i1]) : c;
/*  464 */         float d11 = di[0] * csi;
/*  465 */         float d12 = di[1] * csi;
/*  466 */         float d22 = di[2] * csi;
/*  467 */         float x00 = x0[i1];
/*  468 */         float x0m = x0[m1];
/*  469 */         float xm0 = xm[i1];
/*  470 */         float xmm = xm[m1];
/*  471 */         float xa = x00 - xmm;
/*  472 */         float xb = x0m - xm0;
/*  473 */         float x1 = xa - xb;
/*  474 */         float x2 = xa + xb;
/*  475 */         float y1 = d11 * x1 + d12 * x2;
/*  476 */         float y2 = d12 * x1 + d22 * x2;
/*  477 */         float ya = y1 + y2;
/*  478 */         float yb = y1 - y2;
/*  479 */         y0[i1] = y0[i1] + ya;
/*  480 */         y0[m1] = y0[m1] - yb;
/*  481 */         ym[i1] = ym[i1] + yb;
/*  482 */         ym[m1] = ym[m1] - ya;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void apply22X(Tensors2 d, float c, float[][] s, float[][] x, float[][] y) {
/*  490 */     c *= 0.25F;
/*  491 */     int n1 = (x[0]).length;
/*  492 */     int n2 = x.length;
/*  493 */     float[] di = new float[3];
/*  494 */     for (int i2 = 1; i2 < n2; i2++) {
/*  495 */       float[] xp = x[i2];
/*  496 */       float[] xm = x[i2 - 1];
/*  497 */       float[] yp = y[i2];
/*  498 */       float[] ym = y[i2 - 1];
/*  499 */       float xmp = xm[0], xpp = xp[0];
/*  500 */       float ymp = ym[0], ypp = yp[0];
/*  501 */       for (int i1 = 1, m1 = 0; i1 < n1; i1++, m1++) {
/*  502 */         float xmm = xmp, xpm = xpp; xmp = xm[i1]; xpp = xp[i1];
/*  503 */         float ymm = ymp, ypm = ypp; ymp = ym[i1]; ypp = yp[i1];
/*  504 */         d.getTensor(i1, i2, di);
/*  505 */         float csi = (s != null) ? (c * s[i2][i1]) : c;
/*  506 */         float d11 = di[0] * csi;
/*  507 */         float d12 = di[1] * csi;
/*  508 */         float d22 = di[2] * csi;
/*  509 */         float xa = xpp - xmm;
/*  510 */         float xb = xpm - xmp;
/*  511 */         float x1 = xa - xb;
/*  512 */         float x2 = xa + xb;
/*  513 */         float y1 = d11 * x1 + d12 * x2;
/*  514 */         float y2 = d12 * x1 + d22 * x2;
/*  515 */         float ya = y1 + y2;
/*  516 */         float yb = y1 - y2;
/*  517 */         ypp += ya; ymm -= ya;
/*  518 */         ymp += yb; ypm -= yb;
/*  519 */         ym[m1] = ymm;
/*  520 */         yp[m1] = ypm;
/*      */       } 
/*  522 */       ym[n1 - 1] = ymp;
/*  523 */       yp[n1 - 1] = ypp;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void apply22(int i3, Tensors3 d, float c, float[][][] s, float[][][] x, float[][][] y) {
/*  530 */     c *= 0.0625F;
/*  531 */     int n1 = (x[0][0]).length;
/*  532 */     int n2 = (x[0]).length;
/*  533 */     float[] di = new float[6];
/*  534 */     for (int i2 = 1; i2 < n2; i2++) {
/*  535 */       float[] x00 = x[i3][i2];
/*  536 */       float[] x0m = x[i3][i2 - 1];
/*  537 */       float[] xm0 = x[i3 - 1][i2];
/*  538 */       float[] xmm = x[i3 - 1][i2 - 1];
/*  539 */       float[] y00 = y[i3][i2];
/*  540 */       float[] y0m = y[i3][i2 - 1];
/*  541 */       float[] ym0 = y[i3 - 1][i2];
/*  542 */       float[] ymm = y[i3 - 1][i2 - 1];
/*  543 */       for (int i1 = 1, m1 = 0; i1 < n1; i1++, m1++) {
/*  544 */         d.getTensor(i1, i2, i3, di);
/*  545 */         float csi = (s != null) ? (c * s[i3][i2][i1]) : c;
/*  546 */         float d11 = di[0] * csi;
/*  547 */         float d12 = di[1] * csi;
/*  548 */         float d13 = di[2] * csi;
/*  549 */         float d22 = di[3] * csi;
/*  550 */         float d23 = di[4] * csi;
/*  551 */         float d33 = di[5] * csi;
/*  552 */         float xa = x00[i1] - xmm[m1];
/*  553 */         float xb = x00[m1] - xmm[i1];
/*  554 */         float xc = x0m[i1] - xm0[m1];
/*  555 */         float xd = xm0[i1] - x0m[m1];
/*  556 */         float x1 = xa - xb + xc + xd;
/*  557 */         float x2 = xa + xb - xc + xd;
/*  558 */         float x3 = xa + xb + xc - xd;
/*  559 */         float y1 = d11 * x1 + d12 * x2 + d13 * x3;
/*  560 */         float y2 = d12 * x1 + d22 * x2 + d23 * x3;
/*  561 */         float y3 = d13 * x1 + d23 * x2 + d33 * x3;
/*  562 */         float ya = y1 + y2 + y3; y00[i1] = y00[i1] + ya; ymm[m1] = ymm[m1] - ya;
/*  563 */         float yb = y1 - y2 + y3; y0m[i1] = y0m[i1] + yb; ym0[m1] = ym0[m1] - yb;
/*  564 */         float yc = y1 + y2 - y3; ym0[i1] = ym0[i1] + yc; y0m[m1] = y0m[m1] - yc;
/*  565 */         float yd = y1 - y2 - y3; ymm[i1] = ymm[i1] + yd; y00[m1] = y00[m1] - yd;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void apply24(Tensors2 d, float c, float[][] s, float[][] x, float[][] y) {
/*  576 */     float p = 0.18F;
/*  577 */     float a = 0.5F * (1.0F + p);
/*  578 */     float b = 0.5F * -p;
/*  579 */     b /= a;
/*  580 */     c *= a * a;
/*  581 */     int n1 = (x[0]).length;
/*  582 */     int n2 = x.length;
/*  583 */     float[] di = new float[3];
/*  584 */     int i2m1 = 0, i2p0 = 0, i2p1 = 1;
/*  585 */     for (int i2 = 1; i2 < n2; i2++) {
/*  586 */       int i2m2 = i2m1; i2m1 = i2p0; i2p0 = i2p1; i2p1++;
/*  587 */       if (i2p1 >= n1) i2p1 = n1 - 1; 
/*  588 */       float[] xm2 = x[i2m2], xm1 = x[i2m1], xp0 = x[i2p0], xp1 = x[i2p1];
/*  589 */       float[] ym2 = y[i2m2], ym1 = y[i2m1], yp0 = y[i2p0], yp1 = y[i2p1];
/*  590 */       int m1 = 0, p0 = 0, p1 = 1;
/*  591 */       for (int i1 = 1; i1 < n1; i1++) {
/*  592 */         int m2 = m1; m1 = p0; p0 = p1; p1++;
/*  593 */         if (p1 >= n1) p1 = n1 - 1; 
/*  594 */         d.getTensor(i1, i2, di);
/*  595 */         float csi = (s != null) ? (c * s[i2][i1]) : c;
/*  596 */         float d11 = di[0] * csi;
/*  597 */         float d12 = di[1] * csi;
/*  598 */         float d22 = di[2] * csi;
/*  599 */         float xa = xp0[p0] - xm1[m1];
/*  600 */         float xb = xm1[p0] - xp0[m1];
/*  601 */         float x1 = xa + xb + b * (xp1[p0] + xm2[p0] - xp1[m1] - xm2[m1]);
/*  602 */         float x2 = xa - xb + b * (xp0[p1] + xp0[m2] - xm1[p1] - xm1[m2]);
/*  603 */         float y1 = d11 * x1 + d12 * x2;
/*  604 */         float y2 = d12 * x1 + d22 * x2;
/*  605 */         float ya = y1 + y2;
/*  606 */         float yb = y1 - y2;
/*  607 */         float yc = b * y1;
/*  608 */         float yd = b * y2;
/*  609 */         yp0[p0] = yp0[p0] + ya; ym1[m1] = ym1[m1] - ya;
/*  610 */         ym1[p0] = ym1[p0] + yb; yp0[m1] = yp0[m1] - yb;
/*  611 */         yp1[p0] = yp1[p0] + yc; ym2[m1] = ym2[m1] - yc;
/*  612 */         ym2[p0] = ym2[p0] + yc; yp1[m1] = yp1[m1] - yc;
/*  613 */         yp0[p1] = yp0[p1] + yd; ym1[m2] = ym1[m2] - yd;
/*  614 */         yp0[m2] = yp0[m2] + yd; ym1[p1] = ym1[p1] - yd;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void apply33(Tensors2 d, float c, float[][] s, float[][] x, float[][] y) {
/*  625 */     float p = 0.182962F;
/*  626 */     float a = 0.5F - p;
/*  627 */     float b = 0.5F * p;
/*  628 */     b /= a;
/*  629 */     c *= a * a;
/*  630 */     int n1 = (x[0]).length;
/*  631 */     int n2 = x.length;
/*  632 */     float[] di = new float[3];
/*  633 */     for (int i2 = 1; i2 < n2 - 1; i2++) {
/*  634 */       float[] xm = x[i2 - 1], x0 = x[i2], xp = x[i2 + 1];
/*  635 */       float[] ym = y[i2 - 1], y0 = y[i2], yp = y[i2 + 1];
/*  636 */       float xm0 = xm[0], xmp = xm[1];
/*  637 */       float x00 = x0[0], x0p = x0[1];
/*  638 */       float xp0 = xp[0], xpp = xp[1];
/*  639 */       float ym0 = ym[0], ymp = ym[1];
/*  640 */       float y00 = y0[0], y0p = y0[1];
/*  641 */       float yp0 = yp[0], ypp = yp[1];
/*  642 */       for (int i1m = 0, i1 = 1, i1p = 2; i1p < n1; i1m++, i1++, i1p++) {
/*  643 */         float xmm = xm0; xm0 = xmp; xmp = xm[i1p];
/*  644 */         float x0m = x00; x00 = x0p; x0p = x0[i1p];
/*  645 */         float xpm = xp0; xp0 = xpp; xpp = xp[i1p];
/*  646 */         float ymm = ym0; ym0 = ymp; ymp = ym[i1p];
/*  647 */         float y0m = y00; y00 = y0p; y0p = y0[i1p];
/*  648 */         float ypm = yp0; yp0 = ypp; ypp = yp[i1p];
/*  649 */         d.getTensor(i1, i2, di);
/*  650 */         float csi = (s != null) ? (c * s[i2][i1]) : c;
/*  651 */         float d11 = di[0] * csi;
/*  652 */         float d12 = di[1] * csi;
/*  653 */         float d22 = di[2] * csi;
/*  654 */         float xa = b * (xpp - xmm);
/*  655 */         float xb = b * (xmp - xpm);
/*  656 */         float x1 = x0p - x0m + xa + xb;
/*  657 */         float x2 = xp0 - xm0 + xa - xb;
/*  658 */         float y1 = d11 * x1 + d12 * x2;
/*  659 */         float y2 = d12 * x1 + d22 * x2;
/*  660 */         float ya = b * (y1 + y2);
/*  661 */         float yb = b * (y1 - y2);
/*  662 */         y0p += y1; y0m -= y1;
/*  663 */         ypp += ya; ymm -= ya;
/*  664 */         ymp += yb; ypm -= yb;
/*  665 */         yp0 += y2; ym0 -= y2;
/*  666 */         ym[i1m] = ymm;
/*  667 */         y0[i1m] = y0m;
/*  668 */         yp[i1m] = ypm;
/*      */       } 
/*  670 */       ym[n1 - 2] = ym0; ym[n1 - 1] = ymp;
/*  671 */       y0[n1 - 2] = y00; y0[n1 - 1] = y0p;
/*  672 */       yp[n1 - 2] = yp0; yp[n1 - 1] = ypp;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void apply33X(Tensors2 d, float c, float[][] s, float[][] x, float[][] y) {
/*  679 */     float p = 0.182962F;
/*  680 */     float a = 0.5F - p;
/*  681 */     float b = 0.5F * p;
/*  682 */     b /= a;
/*  683 */     c *= a * a;
/*  684 */     int n1 = (x[0]).length;
/*  685 */     int n2 = x.length;
/*  686 */     float[] di = new float[3];
/*  687 */     for (int i2 = 1; i2 < n2 - 1; i2++) {
/*  688 */       float[] xm = x[i2 - 1], x0 = x[i2], xp = x[i2 + 1];
/*  689 */       float[] ym = y[i2 - 1], y0 = y[i2], yp = y[i2 + 1];
/*  690 */       for (int m1 = 0, i1 = 1, p1 = 2; p1 < n1; m1++, i1++, p1++) {
/*  691 */         d.getTensor(i1, i2, di);
/*  692 */         float csi = (s != null) ? (c * s[i2][i1]) : c;
/*  693 */         float d11 = di[0] * csi;
/*  694 */         float d12 = di[1] * csi;
/*  695 */         float d22 = di[2] * csi;
/*  696 */         float xa = b * (xp[p1] - xm[m1]);
/*  697 */         float xb = b * (xm[p1] - xp[m1]);
/*  698 */         float x1 = x0[p1] - x0[m1] + xa + xb;
/*  699 */         float x2 = xp[i1] - xm[i1] + xa - xb;
/*  700 */         float y1 = d11 * x1 + d12 * x2;
/*  701 */         float y2 = d12 * x1 + d22 * x2;
/*  702 */         float ya = b * (y1 + y2);
/*  703 */         float yb = b * (y1 - y2);
/*  704 */         y0[p1] = y0[p1] + y1; y0[m1] = y0[m1] - y1;
/*  705 */         yp[p1] = yp[p1] + ya; ym[m1] = ym[m1] - ya;
/*  706 */         ym[p1] = ym[p1] + yb; yp[m1] = yp[m1] - yb;
/*  707 */         yp[i1] = yp[i1] + y2; ym[i1] = ym[i1] - y2;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void apply33(int i3, Tensors3 d, float c, float[][][] s, float[][][] x, float[][][] y) {
/*  715 */     float p = 0.174654F;
/*  716 */     float a = 1.0F - 2.0F * p;
/*  717 */     float b = p;
/*  718 */     float aa = 0.5F * a * a;
/*  719 */     float ab = 0.5F * a * b;
/*  720 */     float bb = 0.5F * b * b;
/*  721 */     int n1 = (x[0][0]).length;
/*  722 */     int n2 = (x[0]).length;
/*  723 */     float[] di = new float[6];
/*  724 */     for (int i2 = 1; i2 < n2 - 1; i2++) {
/*  725 */       float[] xmm = x[i3 - 1][i2 - 1], xm0 = x[i3 - 1][i2], xmp = x[i3 - 1][i2 + 1];
/*  726 */       float[] x0m = x[i3][i2 - 1], x00 = x[i3][i2], x0p = x[i3][i2 + 1];
/*  727 */       float[] xpm = x[i3 + 1][i2 - 1], xp0 = x[i3 + 1][i2], xpp = x[i3 + 1][i2 + 1];
/*  728 */       float[] ymm = y[i3 - 1][i2 - 1], ym0 = y[i3 - 1][i2], ymp = y[i3 - 1][i2 + 1];
/*  729 */       float[] y0m = y[i3][i2 - 1], y00 = y[i3][i2], y0p = y[i3][i2 + 1];
/*  730 */       float[] ypm = y[i3 + 1][i2 - 1], yp0 = y[i3 + 1][i2], ypp = y[i3 + 1][i2 + 1];
/*  731 */       for (int m1 = 0, i1 = 1, p1 = 2; p1 < n1; m1++, i1++, p1++) {
/*  732 */         d.getTensor(i1, i2, i3, di);
/*  733 */         float csi = (s != null) ? (c * s[i3][i2][i1]) : c;
/*  734 */         float d11 = di[0] * csi;
/*  735 */         float d12 = di[1] * csi;
/*  736 */         float d13 = di[2] * csi;
/*  737 */         float d22 = di[3] * csi;
/*  738 */         float d23 = di[4] * csi;
/*  739 */         float d33 = di[5] * csi;
/*  740 */         float xmmm = xmm[m1], xmm0 = xmm[i1], xmmp = xmm[p1];
/*  741 */         float xm0m = xm0[m1], xm00 = xm0[i1], xm0p = xm0[p1];
/*  742 */         float xmpm = xmp[m1], xmp0 = xmp[i1], xmpp = xmp[p1];
/*  743 */         float x0mm = x0m[m1], x0m0 = x0m[i1], x0mp = x0m[p1];
/*  744 */         float x00m = x00[m1], x00p = x00[p1];
/*  745 */         float x0pm = x0p[m1], x0p0 = x0p[i1], x0pp = x0p[p1];
/*  746 */         float xpmm = xpm[m1], xpm0 = xpm[i1], xpmp = xpm[p1];
/*  747 */         float xp0m = xp0[m1], xp00 = xp0[i1], xp0p = xp0[p1];
/*  748 */         float xppm = xpp[m1], xpp0 = xpp[i1], xppp = xpp[p1];
/*  749 */         float x00p00m = x00p - x00m;
/*  750 */         float x0p00m0 = x0p0 - x0m0;
/*  751 */         float xp00m00 = xp00 - xm00;
/*  752 */         float xmp0mm0 = xmp0 - xmm0;
/*  753 */         float xpp0pm0 = xpp0 - xpm0;
/*  754 */         float xpm0mm0 = xpm0 - xmm0;
/*  755 */         float xpp0mp0 = xpp0 - xmp0;
/*  756 */         float xm0pm0m = xm0p - xm0m;
/*  757 */         float xp0pp0m = xp0p - xp0m;
/*  758 */         float xp0mm0m = xp0m - xm0m;
/*  759 */         float xp0pm0p = xp0p - xm0p;
/*  760 */         float x0mp0mm = x0mp - x0mm;
/*  761 */         float x0pp0pm = x0pp - x0pm;
/*  762 */         float x0pm0mm = x0pm - x0mm;
/*  763 */         float x0pp0mp = x0pp - x0mp;
/*  764 */         float xpppmmm = xppp - xmmm;
/*  765 */         float xppmmmp = xppm - xmmp;
/*  766 */         float xpmpmpm = xpmp - xmpm;
/*  767 */         float xmpppmm = xmpp - xpmm;
/*  768 */         float x1 = aa * x00p00m + ab * (x0pp0pm + x0mp0mm + xp0pp0m + xm0pm0m) + bb * (xpppmmm - xppmmmp + xpmpmpm + xmpppmm);
/*      */ 
/*      */         
/*  771 */         float x2 = aa * x0p00m0 + ab * (x0pp0mp + x0pm0mm + xpp0pm0 + xmp0mm0) + bb * (xpppmmm + xppmmmp - xpmpmpm + xmpppmm);
/*      */ 
/*      */         
/*  774 */         float x3 = aa * xp00m00 + ab * (xp0pm0p + xp0mm0m + xpp0mp0 + xpm0mm0) + bb * (xpppmmm + xppmmmp + xpmpmpm - xmpppmm);
/*      */ 
/*      */         
/*  777 */         float y1 = d11 * x1 + d12 * x2 + d13 * x3;
/*  778 */         float y2 = d12 * x1 + d22 * x2 + d23 * x3;
/*  779 */         float y3 = d13 * x1 + d23 * x2 + d33 * x3;
/*  780 */         float aa00p = aa * y1; y00[p1] = y00[p1] + aa00p; y00[m1] = y00[m1] - aa00p;
/*  781 */         float aa0p0 = aa * y2; y0p[i1] = y0p[i1] + aa0p0; y0m[i1] = y0m[i1] - aa0p0;
/*  782 */         float aap00 = aa * y3; yp0[i1] = yp0[i1] + aap00; ym0[i1] = ym0[i1] - aap00;
/*  783 */         float ab0pp = ab * (y1 + y2); y0p[p1] = y0p[p1] + ab0pp; y0m[m1] = y0m[m1] - ab0pp;
/*  784 */         float ab0mp = ab * (y1 - y2); y0m[p1] = y0m[p1] + ab0mp; y0p[m1] = y0p[m1] - ab0mp;
/*  785 */         float abp0p = ab * (y1 + y3); yp0[p1] = yp0[p1] + abp0p; ym0[m1] = ym0[m1] - abp0p;
/*  786 */         float abm0p = ab * (y1 - y3); ym0[p1] = ym0[p1] + abm0p; yp0[m1] = yp0[m1] - abm0p;
/*  787 */         float abpp0 = ab * (y2 + y3); ypp[i1] = ypp[i1] + abpp0; ymm[i1] = ymm[i1] - abpp0;
/*  788 */         float abmp0 = ab * (y2 - y3); ymp[i1] = ymp[i1] + abmp0; ypm[i1] = ypm[i1] - abmp0;
/*  789 */         float bbppp = bb * (y1 + y2 + y3); ypp[p1] = ypp[p1] + bbppp; ymm[m1] = ymm[m1] - bbppp;
/*  790 */         float bbmmp = bb * (y1 - y2 - y3); ymm[p1] = ymm[p1] + bbmmp; ypp[m1] = ypp[m1] - bbmmp;
/*  791 */         float bbpmp = bb * (y1 - y2 + y3); ypm[p1] = ypm[p1] + bbpmp; ymp[m1] = ymp[m1] - bbpmp;
/*  792 */         float bbmpp = bb * (y1 + y2 - y3); ymp[p1] = ymp[p1] + bbmpp; ypm[m1] = ypm[m1] - bbmpp;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  800 */   private static final float[] C71 = new float[] { 0.0F, 0.830893F, -0.227266F, 0.042877F };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void apply71X(Tensors2 d, float c, float[][] s, float[][] x, float[][] y) {
/*  807 */     float c1 = C71[1], c2 = C71[2], c3 = C71[3];
/*  808 */     int n1 = (x[0]).length;
/*  809 */     int n2 = x.length;
/*  810 */     float[] di = new float[3];
/*  811 */     float[] g1 = new float[n1];
/*  812 */     for (int i2 = 0; i2 < n2; i2++) {
/*  813 */       int i2m3 = ArrayMath.max(0, i2 - 3), i2p3 = ArrayMath.min(n2 - 1, i2 + 3);
/*  814 */       int i2m2 = ArrayMath.max(0, i2 - 2), i2p2 = ArrayMath.min(n2 - 1, i2 + 2);
/*  815 */       int i2m1 = ArrayMath.max(0, i2 - 1), i2p1 = ArrayMath.min(n2 - 1, i2 + 1);
/*  816 */       float[] xm1 = x[i2m1], xm2 = x[i2m2], xm3 = x[i2m3];
/*  817 */       float[] xp1 = x[i2p1], xp2 = x[i2p2], xp3 = x[i2p3];
/*  818 */       float[] ym1 = y[i2m1], ym2 = y[i2m2], ym3 = y[i2m3];
/*  819 */       float[] yp1 = y[i2p1], yp2 = y[i2p2], yp3 = y[i2p3];
/*  820 */       gf(C71, x[i2], g1);
/*  821 */       for (int i1 = 0; i1 < n1; i1++) {
/*  822 */         d.getTensor(i1, i2, di);
/*  823 */         float csi = (s != null) ? (c * s[i2][i1]) : c;
/*  824 */         float d11 = di[0] * csi;
/*  825 */         float d12 = di[1] * csi;
/*  826 */         float d22 = di[2] * csi;
/*  827 */         float x1 = g1[i1];
/*  828 */         float x2 = c1 * (xp1[i1] - xm1[i1]) + c2 * (xp2[i1] - xm2[i1]) + c3 * (xp3[i1] - xm3[i1]);
/*      */ 
/*      */         
/*  831 */         float y1 = d11 * x1 + d12 * x2;
/*  832 */         float y2 = d12 * x1 + d22 * x2;
/*  833 */         g1[i1] = y1;
/*  834 */         float c1y2 = c1 * y2; yp1[i1] = yp1[i1] + c1y2; ym1[i1] = ym1[i1] - c1y2;
/*  835 */         float c2y2 = c2 * y2; yp2[i1] = yp2[i1] + c2y2; ym2[i1] = ym2[i1] - c2y2;
/*  836 */         float c3y2 = c3 * y2; yp3[i1] = yp3[i1] + c3y2; ym3[i1] = ym3[i1] - c3y2;
/*      */       } 
/*  838 */       gt(C71, g1, y[i2]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void apply71(Tensors2 d, float c, float[][] s, float[][] x, float[][] y) {
/*  845 */     float c1 = C71[1], c2 = C71[2], c3 = C71[3];
/*  846 */     int n1 = (x[0]).length;
/*  847 */     int n2 = x.length;
/*  848 */     float[] di = new float[3];
/*  849 */     int i2m2 = 0, i2m1 = 0, i2p0 = 0, i2p1 = 0, i2p2 = 1, i2p3 = 2;
/*  850 */     for (int i2 = 0; i2 < n2; i2++) {
/*  851 */       int i2m3 = i2m2; i2m2 = i2m1; i2m1 = i2p0;
/*  852 */       i2p0 = i2p1; i2p1 = i2p2; i2p2 = i2p3; i2p3++;
/*  853 */       if (i2p1 >= n2) i2p1 = n2 - 1; 
/*  854 */       if (i2p2 >= n2) i2p2 = n2 - 1; 
/*  855 */       if (i2p3 >= n2) i2p3 = n2 - 1; 
/*  856 */       float[] xm3 = x[i2m3], xm2 = x[i2m2], xm1 = x[i2m1];
/*  857 */       float[] xp3 = x[i2p3], xp2 = x[i2p2], xp1 = x[i2p1];
/*  858 */       float[] xp0 = x[i2p0];
/*  859 */       float[] ym3 = y[i2m3], ym2 = y[i2m2], ym1 = y[i2m1];
/*  860 */       float[] yp3 = y[i2p3], yp2 = y[i2p2], yp1 = y[i2p1];
/*  861 */       float[] yp0 = y[i2p0];
/*  862 */       int m2 = 0, m1 = 0, p0 = 0, p1 = 0, p2 = 1, p3 = 2;
/*  863 */       for (int i1 = 0; i1 < n1; i1++) {
/*  864 */         int m3 = m2; m2 = m1; m1 = p0;
/*  865 */         p0 = p1; p1 = p2; p2 = p3; p3++;
/*  866 */         if (p1 >= n1) p1 = n1 - 1; 
/*  867 */         if (p2 >= n1) p2 = n1 - 1; 
/*  868 */         if (p3 >= n1) p3 = n1 - 1; 
/*  869 */         d.getTensor(i1, i2, di);
/*  870 */         float csi = (s != null) ? (c * s[i2][i1]) : c;
/*  871 */         float d11 = di[0] * csi;
/*  872 */         float d12 = di[1] * csi;
/*  873 */         float d22 = di[2] * csi;
/*  874 */         float x1 = c1 * (xp0[p1] - xp0[m1]) + c2 * (xp0[p2] - xp0[m2]) + c3 * (xp0[p3] - xp0[m3]);
/*      */ 
/*      */         
/*  877 */         float x2 = c1 * (xp1[p0] - xm1[p0]) + c2 * (xp2[p0] - xm2[p0]) + c3 * (xp3[p0] - xm3[p0]);
/*      */ 
/*      */         
/*  880 */         float y1 = d11 * x1 + d12 * x2;
/*  881 */         float y2 = d12 * x1 + d22 * x2;
/*  882 */         float c1y1 = c1 * y1; yp0[p1] = yp0[p1] + c1y1; yp0[m1] = yp0[m1] - c1y1;
/*  883 */         float c2y1 = c2 * y1; yp0[p2] = yp0[p2] + c2y1; yp0[m2] = yp0[m2] - c2y1;
/*  884 */         float c3y1 = c3 * y1; yp0[p3] = yp0[p3] + c3y1; yp0[m3] = yp0[m3] - c3y1;
/*  885 */         float c1y2 = c1 * y2; yp1[p0] = yp1[p0] + c1y2; ym1[p0] = ym1[p0] - c1y2;
/*  886 */         float c2y2 = c2 * y2; yp2[p0] = yp2[p0] + c2y2; ym2[p0] = ym2[p0] - c2y2;
/*  887 */         float c3y2 = c3 * y2; yp3[p0] = yp3[p0] + c3y2; ym3[p0] = ym3[p0] - c3y2;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void apply71X(int i3, Tensors3 d, float c, float[][][] s, float[][][] x, float[][][] y) {
/*  895 */     float c1 = C71[1], c2 = C71[2], c3 = C71[3];
/*  896 */     int n1 = (x[0][0]).length;
/*  897 */     int n2 = (x[0]).length;
/*  898 */     int n3 = x.length;
/*  899 */     float[] di = new float[6];
/*  900 */     int i3m3 = ArrayMath.max(0, i3 - 3), i3p3 = ArrayMath.min(n3 - 1, i3 + 3);
/*  901 */     int i3m2 = ArrayMath.max(0, i3 - 2), i3p2 = ArrayMath.min(n3 - 1, i3 + 2);
/*  902 */     int i3m1 = ArrayMath.max(0, i3 - 1), i3p1 = ArrayMath.min(n3 - 1, i3 + 1);
/*  903 */     float[][] g1 = new float[n2][n1];
/*  904 */     float[][] g2 = new float[n2][n1];
/*  905 */     gf(C71, x[i3], g1, g2);
/*  906 */     for (int i2 = 0; i2 < n2; i2++) {
/*  907 */       float[] xm1 = x[i3m1][i2], xm2 = x[i3m2][i2], xm3 = x[i3m3][i2];
/*  908 */       float[] xp1 = x[i3p1][i2], xp2 = x[i3p2][i2], xp3 = x[i3p3][i2];
/*  909 */       float[] ym1 = y[i3m1][i2], ym2 = y[i3m2][i2], ym3 = y[i3m3][i2];
/*  910 */       float[] yp1 = y[i3p1][i2], yp2 = y[i3p2][i2], yp3 = y[i3p3][i2];
/*  911 */       float[] g1i = g1[i2];
/*  912 */       float[] g2i = g2[i2];
/*  913 */       for (int i1 = 0; i1 < n1; i1++) {
/*  914 */         d.getTensor(i1, i2, i3, di);
/*  915 */         float csi = (s != null) ? (c * s[i3][i2][i1]) : c;
/*  916 */         float d11 = di[0] * csi;
/*  917 */         float d12 = di[1] * csi;
/*  918 */         float d13 = di[2] * csi;
/*  919 */         float d22 = di[3] * csi;
/*  920 */         float d23 = di[4] * csi;
/*  921 */         float d33 = di[5] * csi;
/*  922 */         float x1 = g1i[i1];
/*  923 */         float x2 = g2i[i1];
/*  924 */         float x3 = c1 * (xp1[i1] - xm1[i1]) + c2 * (xp2[i1] - xm2[i1]) + c3 * (xp3[i1] - xm3[i1]);
/*      */ 
/*      */         
/*  927 */         float y1 = d11 * x1 + d12 * x2 + d13 * x3;
/*  928 */         float y2 = d12 * x1 + d22 * x2 + d23 * x3;
/*  929 */         float y3 = d13 * x1 + d23 * x2 + d33 * x3;
/*  930 */         g1i[i1] = y1;
/*  931 */         g2i[i1] = y2;
/*  932 */         float c1y3 = c1 * y3; yp1[i1] = yp1[i1] + c1y3; ym1[i1] = ym1[i1] - c1y3;
/*  933 */         float c2y3 = c2 * y3; yp2[i1] = yp2[i1] + c2y3; ym2[i1] = ym2[i1] - c2y3;
/*  934 */         float c3y3 = c3 * y3; yp3[i1] = yp3[i1] + c3y3; ym3[i1] = ym3[i1] - c3y3;
/*      */       } 
/*      */     } 
/*  937 */     gt(C71, g1, g2, y[i3]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void apply71(int i3, Tensors3 d, float c, float[][][] s, float[][][] x, float[][][] y) {
/*  943 */     float c1 = C71[1], c2 = C71[2], c3 = C71[3];
/*  944 */     int n1 = (x[0][0]).length;
/*  945 */     int n2 = (x[0]).length;
/*  946 */     int n3 = x.length;
/*  947 */     float[] di = new float[6];
/*  948 */     int i3m3 = i3 - 3; if (i3m3 < 0) i3m3 = 0; 
/*  949 */     int i3m2 = i3 - 2; if (i3m2 < 0) i3m2 = 0; 
/*  950 */     int i3m1 = i3 - 1; if (i3m1 < 0) i3m1 = 0; 
/*  951 */     int i3p0 = i3;
/*  952 */     int i3p1 = i3 + 1; if (i3p1 >= n3) i3p1 = n3 - 1; 
/*  953 */     int i3p2 = i3 + 2; if (i3p2 >= n3) i3p2 = n3 - 1; 
/*  954 */     int i3p3 = i3 + 3; if (i3p3 >= n3) i3p3 = n3 - 1; 
/*  955 */     int i2m2 = 0, i2m1 = 0, i2p0 = 0, i2p1 = 0, i2p2 = 1, i2p3 = 2;
/*  956 */     for (int i2 = 0; i2 < n2; i2++) {
/*  957 */       int i2m3 = i2m2; i2m2 = i2m1; i2m1 = i2p0;
/*  958 */       i2p0 = i2p1; i2p1 = i2p2; i2p2 = i2p3; i2p3++;
/*  959 */       if (i2p1 >= n2) i2p1 = n2 - 1; 
/*  960 */       if (i2p2 >= n2) i2p2 = n2 - 1; 
/*  961 */       if (i2p3 >= n2) i2p3 = n2 - 1; 
/*  962 */       float[] xp0p0 = x[i3p0][i2p0], yp0p0 = y[i3p0][i2p0];
/*  963 */       float[] xp0m3 = x[i3p0][i2m3], yp0m3 = y[i3p0][i2m3];
/*  964 */       float[] xp0m2 = x[i3p0][i2m2], yp0m2 = y[i3p0][i2m2];
/*  965 */       float[] xp0m1 = x[i3p0][i2m1], yp0m1 = y[i3p0][i2m1];
/*  966 */       float[] xp0p1 = x[i3p0][i2p1], yp0p1 = y[i3p0][i2p1];
/*  967 */       float[] xp0p2 = x[i3p0][i2p2], yp0p2 = y[i3p0][i2p2];
/*  968 */       float[] xp0p3 = x[i3p0][i2p3], yp0p3 = y[i3p0][i2p3];
/*  969 */       float[] xm3p0 = x[i3m3][i2p0], ym3p0 = y[i3m3][i2p0];
/*  970 */       float[] xm2p0 = x[i3m2][i2p0], ym2p0 = y[i3m2][i2p0];
/*  971 */       float[] xm1p0 = x[i3m1][i2p0], ym1p0 = y[i3m1][i2p0];
/*  972 */       float[] xp1p0 = x[i3p1][i2p0], yp1p0 = y[i3p1][i2p0];
/*  973 */       float[] xp2p0 = x[i3p2][i2p0], yp2p0 = y[i3p2][i2p0];
/*  974 */       float[] xp3p0 = x[i3p3][i2p0], yp3p0 = y[i3p3][i2p0];
/*  975 */       int m2 = 0, m1 = 0, p0 = 0, p1 = 0, p2 = 1, p3 = 2;
/*  976 */       for (int i1 = 0; i1 < n1; i1++) {
/*  977 */         int m3 = m2; m2 = m1; m1 = p0;
/*  978 */         p0 = p1; p1 = p2; p2 = p3; p3++;
/*  979 */         if (p1 >= n1) p1 = n1 - 1; 
/*  980 */         if (p2 >= n1) p2 = n1 - 1; 
/*  981 */         if (p3 >= n1) p3 = n1 - 1; 
/*  982 */         d.getTensor(i1, i2, i3, di);
/*  983 */         float csi = (s != null) ? (c * s[i3][i2][i1]) : c;
/*  984 */         float d11 = di[0] * csi;
/*  985 */         float d12 = di[1] * csi;
/*  986 */         float d13 = di[2] * csi;
/*  987 */         float d22 = di[3] * csi;
/*  988 */         float d23 = di[4] * csi;
/*  989 */         float d33 = di[5] * csi;
/*  990 */         float x1 = c1 * (xp0p0[p1] - xp0p0[m1]) + c2 * (xp0p0[p2] - xp0p0[m2]) + c3 * (xp0p0[p3] - xp0p0[m3]);
/*      */ 
/*      */         
/*  993 */         float x2 = c1 * (xp0p1[p0] - xp0m1[p0]) + c2 * (xp0p2[p0] - xp0m2[p0]) + c3 * (xp0p3[p0] - xp0m3[p0]);
/*      */ 
/*      */         
/*  996 */         float x3 = c1 * (xp1p0[p0] - xm1p0[p0]) + c2 * (xp2p0[p0] - xm2p0[p0]) + c3 * (xp3p0[p0] - xm3p0[p0]);
/*      */ 
/*      */         
/*  999 */         float y1 = d11 * x1 + d12 * x2 + d13 * x3;
/* 1000 */         float y2 = d12 * x1 + d22 * x2 + d23 * x3;
/* 1001 */         float y3 = d13 * x1 + d23 * x2 + d33 * x3;
/* 1002 */         float c1y1 = c1 * y1; yp0p0[p1] = yp0p0[p1] + c1y1; yp0p0[m1] = yp0p0[m1] - c1y1;
/* 1003 */         float c2y1 = c2 * y1; yp0p0[p2] = yp0p0[p2] + c2y1; yp0p0[m2] = yp0p0[m2] - c2y1;
/* 1004 */         float c3y1 = c3 * y1; yp0p0[p3] = yp0p0[p3] + c3y1; yp0p0[m3] = yp0p0[m3] - c3y1;
/* 1005 */         float c1y2 = c1 * y2; yp0p1[p0] = yp0p1[p0] + c1y2; yp0m1[p0] = yp0m1[p0] - c1y2;
/* 1006 */         float c2y2 = c2 * y2; yp0p2[p0] = yp0p2[p0] + c2y2; yp0m2[p0] = yp0m2[p0] - c2y2;
/* 1007 */         float c3y2 = c3 * y2; yp0p3[p0] = yp0p3[p0] + c3y2; yp0m3[p0] = yp0m3[p0] - c3y2;
/* 1008 */         float c1y3 = c1 * y3; yp1p0[p0] = yp1p0[p0] + c1y3; ym1p0[p0] = ym1p0[p0] - c1y3;
/* 1009 */         float c2y3 = c2 * y3; yp2p0[p0] = yp2p0[p0] + c2y3; ym2p0[p0] = ym2p0[p0] - c2y3;
/* 1010 */         float c3y3 = c3 * y3; yp3p0[p0] = yp3p0[p0] + c3y3; ym3p0[p0] = ym3p0[p0] - c3y3;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1018 */   private static final float[] C91 = new float[] { 0.0F, 0.8947167F, -0.3153471F, 0.1096895F, -0.0259358F };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void apply91(Tensors2 d, float c, float[][] s, float[][] x, float[][] y) {
/* 1025 */     float c1 = C91[1], c2 = C91[2], c3 = C91[3], c4 = C91[4];
/* 1026 */     int n1 = (x[0]).length;
/* 1027 */     int n2 = x.length;
/* 1028 */     float[] di = new float[3];
/* 1029 */     int i2m3 = 0, i2m2 = 0, i2m1 = 0, i2p0 = 0, i2p1 = 0, i2p2 = 1, i2p3 = 2, i2p4 = 3;
/* 1030 */     for (int i2 = 0; i2 < n2; i2++) {
/* 1031 */       int i2m4 = i2m3; i2m3 = i2m2; i2m2 = i2m1; i2m1 = i2p0;
/* 1032 */       i2p0 = i2p1; i2p1 = i2p2; i2p2 = i2p3; i2p3 = i2p4; i2p4++;
/* 1033 */       if (i2p1 >= n2) i2p1 = n2 - 1; 
/* 1034 */       if (i2p2 >= n2) i2p2 = n2 - 1; 
/* 1035 */       if (i2p3 >= n2) i2p3 = n2 - 1; 
/* 1036 */       if (i2p4 >= n2) i2p4 = n2 - 1; 
/* 1037 */       float[] xm4 = x[i2m4], xm3 = x[i2m3], xm2 = x[i2m2], xm1 = x[i2m1];
/* 1038 */       float[] xp4 = x[i2p4], xp3 = x[i2p3], xp2 = x[i2p2], xp1 = x[i2p1];
/* 1039 */       float[] xp0 = x[i2p0];
/* 1040 */       float[] ym4 = y[i2m4], ym3 = y[i2m3], ym2 = y[i2m2], ym1 = y[i2m1];
/* 1041 */       float[] yp4 = y[i2p4], yp3 = y[i2p3], yp2 = y[i2p2], yp1 = y[i2p1];
/* 1042 */       float[] yp0 = y[i2p0];
/* 1043 */       int m3 = 0, m2 = 0, m1 = 0, p0 = 0, p1 = 0, p2 = 1, p3 = 2, p4 = 3;
/* 1044 */       for (int i1 = 0; i1 < n1; i1++) {
/* 1045 */         int m4 = m3; m3 = m2; m2 = m1; m1 = p0;
/* 1046 */         p0 = p1; p1 = p2; p2 = p3; p3 = p4; p4++;
/* 1047 */         if (p1 >= n1) p1 = n1 - 1; 
/* 1048 */         if (p2 >= n1) p2 = n1 - 1; 
/* 1049 */         if (p3 >= n1) p3 = n1 - 1; 
/* 1050 */         if (p4 >= n1) p4 = n1 - 1; 
/* 1051 */         d.getTensor(i1, i2, di);
/* 1052 */         float csi = (s != null) ? (c * s[i2][i1]) : c;
/* 1053 */         float d11 = di[0] * csi;
/* 1054 */         float d12 = di[1] * csi;
/* 1055 */         float d22 = di[2] * csi;
/* 1056 */         float x1 = c1 * (xp0[p1] - xp0[m1]) + c2 * (xp0[p2] - xp0[m2]) + c3 * (xp0[p3] - xp0[m3]) + c4 * (xp0[p4] - xp0[m4]);
/*      */ 
/*      */ 
/*      */         
/* 1060 */         float x2 = c1 * (xp1[p0] - xm1[p0]) + c2 * (xp2[p0] - xm2[p0]) + c3 * (xp3[p0] - xm3[p0]) + c4 * (xp4[p0] - xm4[p0]);
/*      */ 
/*      */ 
/*      */         
/* 1064 */         float y1 = d11 * x1 + d12 * x2;
/* 1065 */         float y2 = d12 * x1 + d22 * x2;
/* 1066 */         float c1y1 = c1 * y1; yp0[p1] = yp0[p1] + c1y1; yp0[m1] = yp0[m1] - c1y1;
/* 1067 */         float c2y1 = c2 * y1; yp0[p2] = yp0[p2] + c2y1; yp0[m2] = yp0[m2] - c2y1;
/* 1068 */         float c3y1 = c3 * y1; yp0[p3] = yp0[p3] + c3y1; yp0[m3] = yp0[m3] - c3y1;
/* 1069 */         float c4y1 = c4 * y1; yp0[p4] = yp0[p4] + c4y1; yp0[m4] = yp0[m4] - c4y1;
/* 1070 */         float c1y2 = c1 * y2; yp1[p0] = yp1[p0] + c1y2; ym1[p0] = ym1[p0] - c1y2;
/* 1071 */         float c2y2 = c2 * y2; yp2[p0] = yp2[p0] + c2y2; ym2[p0] = ym2[p0] - c2y2;
/* 1072 */         float c3y2 = c3 * y2; yp3[p0] = yp3[p0] + c3y2; ym3[p0] = ym3[p0] - c3y2;
/* 1073 */         float c4y2 = c4 * y2; yp4[p0] = yp4[p0] + c4y2; ym4[p0] = ym4[p0] - c4y2;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void gf(float[] c, float[] x, float[] y) {
/* 1082 */     int nc = c.length - 1;
/* 1083 */     int n1 = x.length;
/* 1084 */     int n1m1 = n1 - 1, n1nc = n1 - nc; int i1;
/* 1085 */     for (i1 = 0; i1 < ArrayMath.min(nc, n1nc); i1++) {
/* 1086 */       float yi = 0.0F;
/* 1087 */       for (int ic = 1; ic <= nc; ic++) {
/* 1088 */         float ci = c[ic];
/* 1089 */         int im = i1 - ic; if (im < 0) im = 0; 
/* 1090 */         int ip = i1 + ic; if (ip > n1m1) ip = n1m1; 
/* 1091 */         yi += ci * (x[ip] - x[im]);
/*      */       } 
/* 1093 */       y[i1] = yi;
/*      */     } 
/* 1095 */     if (nc == 3 && n1 > 6) {
/* 1096 */       float c1 = c[1], c2 = c[2], c3 = c[3];
/* 1097 */       float xm2 = x[0], xm1 = x[1], xp0 = x[2];
/* 1098 */       float xp1 = x[3], xp2 = x[4], xp3 = x[5];
/* 1099 */       for (int i = 3; i < n1nc; i++) {
/* 1100 */         float xm3 = xm2; xm2 = xm1; xm1 = xp0;
/* 1101 */         xp0 = xp1; xp1 = xp2; xp2 = xp3;
/* 1102 */         xp3 = x[i + 3];
/* 1103 */         y[i] = c1 * (xp1 - xm1) + c2 * (xp2 - xm2) + c3 * (xp3 - xm3);
/*      */       } 
/*      */     } else {
/* 1106 */       for (i1 = nc; i1 < n1nc; i1++) {
/* 1107 */         float yi = 0.0F;
/* 1108 */         for (int ic = 1; ic <= nc; ic++)
/* 1109 */           yi += c[ic] * (x[i1 + ic] - x[i1 - ic]); 
/* 1110 */         y[i1] = yi;
/*      */       } 
/*      */     } 
/* 1113 */     for (i1 = ArrayMath.max(n1nc, 0); i1 < n1; i1++) {
/* 1114 */       float yi = 0.0F;
/* 1115 */       for (int ic = 1; ic <= nc; ic++) {
/* 1116 */         float ci = c[ic];
/* 1117 */         int im = i1 - ic; if (im < 0) im = 0; 
/* 1118 */         int ip = i1 + ic; if (ip > n1m1) ip = n1m1; 
/* 1119 */         yi += ci * (x[ip] - x[im]);
/*      */       } 
/* 1121 */       y[i1] = yi;
/*      */     } 
/*      */   }
/*      */   private static void gt(float[] c, float[] x, float[] y) {
/* 1125 */     int nc = c.length - 1;
/* 1126 */     int n1 = x.length;
/* 1127 */     int n1m1 = n1 - 1, n1nc = n1 - nc; int i1;
/* 1128 */     for (i1 = 0; i1 < ArrayMath.min(2 * nc, n1); i1++) {
/* 1129 */       float xi = x[i1];
/* 1130 */       for (int ic = 1; ic <= nc; ic++) {
/* 1131 */         float ci = c[ic];
/* 1132 */         int im = i1 - ic; if (im < 0) im = 0; 
/* 1133 */         int ip = i1 + ic; if (ip > n1m1) ip = n1m1; 
/* 1134 */         if (im < nc) y[im] = y[im] - ci * xi; 
/* 1135 */         if (ip < nc) y[ip] = y[ip] + ci * xi; 
/*      */       } 
/*      */     } 
/* 1138 */     if (nc == 3 && n1 > 6) {
/* 1139 */       float c1 = c[1], c2 = c[2], c3 = c[3];
/* 1140 */       float xm2 = x[0], xm1 = x[1], xp0 = x[2];
/* 1141 */       float xp1 = x[3], xp2 = x[4], xp3 = x[5];
/* 1142 */       for (int i = 3; i < n1nc; i++) {
/* 1143 */         float xm3 = xm2; xm2 = xm1; xm1 = xp0;
/* 1144 */         xp0 = xp1; xp1 = xp2; xp2 = xp3;
/* 1145 */         xp3 = x[i + 3];
/* 1146 */         y[i] = y[i] + c1 * (xm1 - xp1) + c2 * (xm2 - xp2) + c3 * (xm3 - xp3);
/*      */       } 
/*      */     } else {
/* 1149 */       for (i1 = nc; i1 < n1nc; i1++) {
/* 1150 */         float yi = y[i1];
/* 1151 */         for (int ic = 1; ic <= nc; ic++)
/* 1152 */           yi += c[ic] * (x[i1 - ic] - x[i1 + ic]); 
/* 1153 */         y[i1] = yi;
/*      */       } 
/*      */     } 
/* 1156 */     n1nc = ArrayMath.max(n1nc, nc);
/* 1157 */     for (i1 = ArrayMath.max(n1 - 2 * nc, 0); i1 < n1; i1++) {
/* 1158 */       float xi = x[i1];
/* 1159 */       for (int ic = 1; ic <= nc; ic++) {
/* 1160 */         float ci = c[ic];
/* 1161 */         int im = i1 - ic; if (im < 0) im = 0; 
/* 1162 */         int ip = i1 + ic; if (ip > n1m1) ip = n1m1; 
/* 1163 */         if (im >= n1nc) y[im] = y[im] - ci * xi; 
/* 1164 */         if (ip >= n1nc) y[ip] = y[ip] + ci * xi; 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void gf1(float[] c, float[][] x, float[][] g1) {
/* 1169 */     int n2 = x.length;
/* 1170 */     for (int i2 = 0; i2 < n2; i2++)
/* 1171 */       gf(c, x[i2], g1[i2]); 
/*      */   }
/*      */   private static void gf2(float[] c, float[][] x, float[][] g2) {
/* 1174 */     int nc = c.length - 1;
/* 1175 */     int n1 = (x[0]).length;
/* 1176 */     int n2 = x.length;
/* 1177 */     if (nc == 3) {
/* 1178 */       float c1 = C71[1], c2 = C71[2], c3 = C71[3];
/* 1179 */       int n2m1 = n2 - 1, n2m2 = n2 - 2, n2m3 = n2 - 3;
/* 1180 */       for (int i2 = 0; i2 < n2; i2++) {
/* 1181 */         float[] xm3 = (i2 >= 3) ? x[i2 - 3] : x[0];
/* 1182 */         float[] xm2 = (i2 >= 2) ? x[i2 - 2] : x[0];
/* 1183 */         float[] xm1 = (i2 >= 1) ? x[i2 - 1] : x[0];
/* 1184 */         float[] xp1 = (i2 < n2m1) ? x[i2 + 1] : x[n2m1];
/* 1185 */         float[] xp2 = (i2 < n2m2) ? x[i2 + 2] : x[n2m1];
/* 1186 */         float[] xp3 = (i2 < n2m3) ? x[i2 + 3] : x[n2m1];
/* 1187 */         float[] g2i = g2[i2];
/* 1188 */         for (int i1 = 0; i1 < n1; i1++) {
/* 1189 */           g2i[i1] = c1 * (xp1[i1] - xm1[i1]) + c2 * (xp2[i1] - xm2[i1]) + c3 * (xp3[i1] - xm3[i1]);
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1195 */       int n2m1 = n2 - 1;
/* 1196 */       for (int i2 = 0; i2 < n2; i2++) {
/* 1197 */         float[] g2i = g2[i2];
/* 1198 */         ArrayMath.zero(g2i);
/* 1199 */         for (int ic = 1; ic <= nc; ic++) {
/* 1200 */           float ci = c[ic];
/* 1201 */           float[] xm = (i2 >= ic) ? x[i2 - ic] : x[0];
/* 1202 */           float[] xp = (i2 < n2 - ic) ? x[i2 + ic] : x[n2m1];
/* 1203 */           for (int i1 = 0; i1 < n1; i1++)
/* 1204 */             g2i[i1] = g2i[i1] + ci * (xp[i1] - xm[i1]); 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void gt1(float[] c, float[][] g1, float[][] x) {
/* 1210 */     int n2 = x.length;
/* 1211 */     for (int i2 = 0; i2 < n2; i2++)
/* 1212 */       gt(c, g1[i2], x[i2]); 
/*      */   }
/*      */   private static void gt2(float[] c, float[][] g2, float[][] x) {
/* 1215 */     int nc = c.length - 1;
/* 1216 */     int n1 = (x[0]).length;
/* 1217 */     int n2 = x.length;
/* 1218 */     int n2m1 = n2 - 1, n2nc = n2 - nc; int i2;
/* 1219 */     for (i2 = 0; i2 < ArrayMath.min(2 * nc, n2); i2++) {
/* 1220 */       float[] g2i = g2[i2];
/* 1221 */       for (int ic = 1; ic <= nc; ic++) {
/* 1222 */         float ci = c[ic];
/* 1223 */         int im = i2 - ic; if (im < 0) im = 0; 
/* 1224 */         int ip = i2 + ic; if (ip > n2m1) ip = n2m1; 
/* 1225 */         if (im < nc) {
/* 1226 */           float[] x2m = x[im];
/* 1227 */           for (int i1 = 0; i1 < n1; i1++)
/* 1228 */             x2m[i1] = x2m[i1] - ci * g2i[i1]; 
/*      */         } 
/* 1230 */         if (ip < nc) {
/* 1231 */           float[] x2p = x[ip];
/* 1232 */           for (int i1 = 0; i1 < n1; i1++)
/* 1233 */             x2p[i1] = x2p[i1] + ci * g2i[i1]; 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1237 */     if (nc == 3 && n1 > 6) {
/* 1238 */       float c1 = c[1], c2 = c[2], c3 = c[3];
/* 1239 */       for (int i = 3; i < n2 - 3; i++) {
/* 1240 */         float[] gm3 = g2[i - 3];
/* 1241 */         float[] gm2 = g2[i - 2];
/* 1242 */         float[] gm1 = g2[i - 1];
/* 1243 */         float[] gp1 = g2[i + 1];
/* 1244 */         float[] gp2 = g2[i + 2];
/* 1245 */         float[] gp3 = g2[i + 3];
/* 1246 */         float[] x2 = x[i];
/* 1247 */         for (int i1 = 0; i1 < n1; i1++) {
/* 1248 */           x2[i1] = x2[i1] + c1 * (gm1[i1] - gp1[i1]) + c2 * (gm2[i1] - gp2[i1]) + c3 * (gm3[i1] - gp3[i1]);
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1254 */       for (i2 = nc; i2 < n2 - nc; i2++) {
/* 1255 */         float[] x2 = x[i2];
/* 1256 */         for (int ic = 1; ic <= nc; ic++) {
/* 1257 */           float ci = c[ic];
/* 1258 */           float[] g2m = g2[i2 - ic];
/* 1259 */           float[] g2p = g2[i2 + ic];
/* 1260 */           for (int i1 = 0; i1 < n1; i1++)
/* 1261 */             x2[i1] = x2[i1] + ci * (g2m[i1] - g2p[i1]); 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1265 */     n2nc = ArrayMath.max(n2nc, nc);
/* 1266 */     for (i2 = ArrayMath.max(n2 - 2 * nc, 0); i2 < n2; i2++) {
/* 1267 */       float[] g2i = g2[i2];
/* 1268 */       for (int ic = 1; ic <= nc; ic++) {
/* 1269 */         float ci = c[ic];
/* 1270 */         int im = i2 - ic; if (im < 0) im = 0; 
/* 1271 */         int ip = i2 + ic; if (ip > n2m1) ip = n2m1; 
/* 1272 */         if (im >= n2nc) {
/* 1273 */           float[] x2m = x[im];
/* 1274 */           for (int i1 = 0; i1 < n1; i1++)
/* 1275 */             x2m[i1] = x2m[i1] - ci * g2i[i1]; 
/*      */         } 
/* 1277 */         if (ip >= n2nc) {
/* 1278 */           float[] x2p = x[ip];
/* 1279 */           for (int i1 = 0; i1 < n1; i1++)
/* 1280 */             x2p[i1] = x2p[i1] + ci * g2i[i1]; 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void gf(float[] c, float[][] x, float[][] g1, float[][] g2) {
/* 1286 */     gf1(c, x, g1);
/* 1287 */     gf2(c, x, g2);
/*      */   }
/*      */   private static void gt(float[] c, float[][] g1, float[][] g2, float[][] x) {
/* 1290 */     gt2(c, g2, x);
/* 1291 */     gt1(c, g1, x);
/*      */   }
/*      */   private static void testGrad1() {
/* 1294 */     int n = 21;
/* 1295 */     float[] x = ArrayMath.randfloat(n);
/* 1296 */     float[] y = ArrayMath.randfloat(n);
/*      */ 
/*      */     
/* 1299 */     float[] gx = ArrayMath.zerofloat(n);
/* 1300 */     float[] gy = ArrayMath.zerofloat(n);
/* 1301 */     gf(C71, x, gx);
/* 1302 */     gt(C71, y, gy);
/* 1303 */     ArrayMath.dump(gx);
/* 1304 */     ArrayMath.dump(gy);
/* 1305 */     float ygx = ArrayMath.sum(ArrayMath.mul(y, gx));
/* 1306 */     float xgy = ArrayMath.sum(ArrayMath.mul(x, gy));
/* 1307 */     trace("ygx=" + ygx);
/* 1308 */     trace("xgy=" + xgy);
/*      */   }
/*      */   private static void testGrad2() {
/* 1311 */     int n1 = 11;
/* 1312 */     int n2 = 21;
/* 1313 */     float[][] x = ArrayMath.randfloat(n1, n2);
/* 1314 */     float[][] y1 = ArrayMath.randfloat(n1, n2);
/* 1315 */     float[][] y2 = ArrayMath.randfloat(n1, n2);
/* 1316 */     float[][] y = ArrayMath.zerofloat(n1, n2);
/* 1317 */     float[][] x1 = ArrayMath.zerofloat(n1, n2);
/* 1318 */     float[][] x2 = ArrayMath.zerofloat(n1, n2);
/* 1319 */     gf(C71, x, x1, x2);
/* 1320 */     gt(C71, y1, y2, y);
/* 1321 */     float ygx = ArrayMath.sum(ArrayMath.add(ArrayMath.mul(y1, x1), ArrayMath.mul(y2, x2)));
/* 1322 */     float xgy = ArrayMath.sum(ArrayMath.mul(x, y));
/* 1323 */     trace("ygx=" + ygx);
/* 1324 */     trace("xgy=" + xgy);
/*      */   }
/*      */   
/*      */   public static void main(String[] args) {}
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/LocalDiffusionKernel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */