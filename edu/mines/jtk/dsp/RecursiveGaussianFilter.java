/*      */ package edu.mines.jtk.dsp;
/*      */ 
/*      */ import edu.mines.jtk.util.ArrayMath;
/*      */ import edu.mines.jtk.util.Cdouble;
/*      */ import edu.mines.jtk.util.Check;
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
/*      */ public class RecursiveGaussianFilter
/*      */ {
/*      */   private Filter _filter;
/*      */   
/*      */   public enum Method
/*      */   {
/*   62 */     DERICHE,
/*   63 */     VAN_VLIET;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RecursiveGaussianFilter(double sigma, Method method) {
/*   72 */     Check.argument((sigma >= 1.0D), "sigma>=1.0");
/*   73 */     this._filter = (method == Method.DERICHE) ? new DericheFilter(sigma) : new VanVlietFilter(sigma);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RecursiveGaussianFilter(double sigma) {
/*   83 */     Check.argument((sigma >= 1.0D), "sigma>=1.0");
/*   84 */     this._filter = (sigma < 32.0D) ? new DericheFilter(sigma) : new VanVlietFilter(sigma);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply0(float[] x, float[] y) {
/*   95 */     this._filter.applyN(0, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply1(float[] x, float[] y) {
/*  104 */     this._filter.applyN(1, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply2(float[] x, float[] y) {
/*  113 */     this._filter.applyN(2, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply0X(float[][] x, float[][] y) {
/*  123 */     this._filter.applyNX(0, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply1X(float[][] x, float[][] y) {
/*  133 */     this._filter.applyNX(1, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply2X(float[][] x, float[][] y) {
/*  143 */     this._filter.applyNX(2, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyX0(float[][] x, float[][] y) {
/*  153 */     this._filter.applyXN(0, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyX1(float[][] x, float[][] y) {
/*  163 */     this._filter.applyXN(1, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyX2(float[][] x, float[][] y) {
/*  173 */     this._filter.applyXN(2, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply0XX(float[][][] x, float[][][] y) {
/*  183 */     this._filter.applyNXX(0, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply1XX(float[][][] x, float[][][] y) {
/*  193 */     this._filter.applyNXX(1, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply2XX(float[][][] x, float[][][] y) {
/*  203 */     this._filter.applyNXX(2, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyX0X(float[][][] x, float[][][] y) {
/*  213 */     this._filter.applyXNX(0, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyX1X(float[][][] x, float[][][] y) {
/*  223 */     this._filter.applyXNX(1, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyX2X(float[][][] x, float[][][] y) {
/*  233 */     this._filter.applyXNX(2, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyXX0(float[][][] x, float[][][] y) {
/*  243 */     this._filter.applyXXN(0, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyXX1(float[][][] x, float[][][] y) {
/*  253 */     this._filter.applyXXN(1, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyXX2(float[][][] x, float[][][] y) {
/*  263 */     this._filter.applyXXN(2, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply00(float[][] x, float[][] y) {
/*  272 */     this._filter.applyXN(0, x, y);
/*  273 */     this._filter.applyNX(0, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply10(float[][] x, float[][] y) {
/*  283 */     this._filter.applyXN(0, x, y);
/*  284 */     this._filter.applyNX(1, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply01(float[][] x, float[][] y) {
/*  294 */     this._filter.applyXN(1, x, y);
/*  295 */     this._filter.applyNX(0, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply11(float[][] x, float[][] y) {
/*  304 */     this._filter.applyXN(1, x, y);
/*  305 */     this._filter.applyNX(1, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply20(float[][] x, float[][] y) {
/*  315 */     this._filter.applyXN(0, x, y);
/*  316 */     this._filter.applyNX(2, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply02(float[][] x, float[][] y) {
/*  326 */     this._filter.applyXN(2, x, y);
/*  327 */     this._filter.applyNX(0, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply000(float[][][] x, float[][][] y) {
/*  336 */     this._filter.applyXXN(0, x, y);
/*  337 */     this._filter.applyXNX(0, y, y);
/*  338 */     this._filter.applyNXX(0, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply100(float[][][] x, float[][][] y) {
/*  348 */     this._filter.applyXXN(0, x, y);
/*  349 */     this._filter.applyXNX(0, y, y);
/*  350 */     this._filter.applyNXX(1, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply010(float[][][] x, float[][][] y) {
/*  360 */     this._filter.applyXXN(0, x, y);
/*  361 */     this._filter.applyXNX(1, y, y);
/*  362 */     this._filter.applyNXX(0, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply001(float[][][] x, float[][][] y) {
/*  372 */     this._filter.applyXXN(1, x, y);
/*  373 */     this._filter.applyXNX(0, y, y);
/*  374 */     this._filter.applyNXX(0, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply110(float[][][] x, float[][][] y) {
/*  384 */     this._filter.applyXXN(0, x, y);
/*  385 */     this._filter.applyXNX(1, y, y);
/*  386 */     this._filter.applyNXX(1, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply101(float[][][] x, float[][][] y) {
/*  396 */     this._filter.applyXXN(1, x, y);
/*  397 */     this._filter.applyXNX(0, y, y);
/*  398 */     this._filter.applyNXX(1, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply011(float[][][] x, float[][][] y) {
/*  408 */     this._filter.applyXXN(1, x, y);
/*  409 */     this._filter.applyXNX(1, y, y);
/*  410 */     this._filter.applyNXX(0, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply200(float[][][] x, float[][][] y) {
/*  420 */     this._filter.applyXXN(0, x, y);
/*  421 */     this._filter.applyXNX(0, y, y);
/*  422 */     this._filter.applyNXX(2, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply020(float[][][] x, float[][][] y) {
/*  432 */     this._filter.applyXXN(0, x, y);
/*  433 */     this._filter.applyXNX(2, y, y);
/*  434 */     this._filter.applyNXX(0, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply002(float[][][] x, float[][][] y) {
/*  444 */     this._filter.applyXXN(2, x, y);
/*  445 */     this._filter.applyXNX(0, y, y);
/*  446 */     this._filter.applyNXX(0, y, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkArrays(float[] x, float[] y) {
/*  455 */     Check.argument((x.length == y.length), "x.length==y.length");
/*      */   }
/*      */   
/*      */   private static void checkArrays(float[][] x, float[][] y) {
/*  459 */     Check.argument((x.length == y.length), "x.length==y.length");
/*  460 */     Check.argument(((x[0]).length == (y[0]).length), "x[0].length==y[0].length");
/*  461 */     Check.argument(ArrayMath.isRegular(x), "x is regular");
/*  462 */     Check.argument(ArrayMath.isRegular(y), "y is regular");
/*      */   }
/*      */   
/*      */   private static void checkArrays(float[][][] x, float[][][] y) {
/*  466 */     Check.argument((x.length == y.length), "x.length==y.length");
/*  467 */     Check.argument(((x[0]).length == (y[0]).length), "x[0].length==y[0].length");
/*  468 */     Check.argument(((x[0][0]).length == (y[0][0]).length), "x[0][0].length==y[0][0].length");
/*      */     
/*  470 */     Check.argument(ArrayMath.isRegular(x), "x is regular");
/*  471 */     Check.argument(ArrayMath.isRegular(y), "y is regular");
/*      */   }
/*      */   
/*      */   private static boolean sameArrays(float[] x, float[] y) {
/*  475 */     return (x == y);
/*      */   }
/*      */   
/*      */   private static boolean sameArrays(float[][] x, float[][] y) {
/*  479 */     if (x == y) {
/*  480 */       return true;
/*      */     }
/*  482 */     int n2 = x.length;
/*  483 */     for (int i2 = 0; i2 < n2; i2++) {
/*  484 */       if (x[i2] == y[i2])
/*  485 */         return true; 
/*      */     } 
/*  487 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static abstract class Filter
/*      */   {
/*      */     private Filter() {}
/*      */ 
/*      */     
/*      */     void applyNX(int nd, float[][] x, float[][] y) {
/*  498 */       int m2 = y.length;
/*  499 */       for (int i2 = 0; i2 < m2; i2++)
/*  500 */         applyN(nd, x[i2], y[i2]); 
/*      */     }
/*      */     
/*      */     void applyNXX(final int nd, final float[][][] x, final float[][][] y) {
/*  504 */       int m3 = y.length;
/*  505 */       Parallel.loop(m3, new Parallel.LoopInt() {
/*      */             public void compute(int i3) {
/*  507 */               RecursiveGaussianFilter.Filter.this.applyNX(nd, x[i3], y[i3]);
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*      */     void applyXNX(final int nd, final float[][][] x, final float[][][] y) {
/*  513 */       int m3 = y.length;
/*  514 */       Parallel.loop(m3, new Parallel.LoopInt() {
/*      */             public void compute(int i3) {
/*  516 */               RecursiveGaussianFilter.Filter.this.applyXN(nd, x[i3], y[i3]);
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*      */     void applyXXN(final int nd, float[][][] x, float[][][] y) {
/*  522 */       RecursiveGaussianFilter.checkArrays(x, y);
/*  523 */       int m3 = y.length;
/*  524 */       int m2 = (y[0]).length;
/*  525 */       final float[][][] tx = new float[m2][m3][];
/*  526 */       final float[][][] ty = new float[m2][m3][];
/*  527 */       for (int i3 = 0; i3 < m3; i3++) {
/*  528 */         for (int i2 = 0; i2 < m2; i2++) {
/*  529 */           tx[i2][i3] = x[i3][i2];
/*  530 */           ty[i2][i3] = y[i3][i2];
/*      */         } 
/*      */       } 
/*  533 */       Parallel.loop(m2, new Parallel.LoopInt() {
/*      */             public void compute(int i2) {
/*  535 */               RecursiveGaussianFilter.Filter.this.applyXN(nd, tx[i2], ty[i2]);
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract void applyN(int param1Int, float[] param1ArrayOffloat1, float[] param1ArrayOffloat2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract void applyXN(int param1Int, float[][] param1ArrayOffloat1, float[][] param1ArrayOffloat2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class DericheFilter
/*      */     extends Filter
/*      */   {
/*      */     DericheFilter(double sigma) {
/*  561 */       makeND(sigma);
/*      */     }
/*      */     
/*      */     void applyN(int nd, float[] x, float[] y) {
/*  565 */       RecursiveGaussianFilter.checkArrays(x, y);
/*  566 */       if (RecursiveGaussianFilter.sameArrays(x, y))
/*  567 */         x = ArrayMath.copy(x); 
/*  568 */       int m = y.length;
/*  569 */       float n0 = this._n0[nd], n1 = this._n1[nd], n2 = this._n2[nd], n3 = this._n3[nd];
/*  570 */       float d1 = this._d1[nd], d2 = this._d2[nd], d3 = this._d3[nd], d4 = this._d4[nd];
/*  571 */       float yim4 = 0.0F, yim3 = 0.0F, yim2 = 0.0F, yim1 = 0.0F;
/*  572 */       float xim3 = 0.0F, xim2 = 0.0F, xim1 = 0.0F;
/*  573 */       for (int i = 0; i < m; i++) {
/*  574 */         float xi = x[i];
/*  575 */         float yi = n0 * xi + n1 * xim1 + n2 * xim2 + n3 * xim3 - d1 * yim1 - d2 * yim2 - d3 * yim3 - d4 * yim4;
/*      */         
/*  577 */         y[i] = yi;
/*  578 */         yim4 = yim3; yim3 = yim2; yim2 = yim1; yim1 = yi;
/*  579 */         xim3 = xim2; xim2 = xim1; xim1 = xi;
/*      */       } 
/*  581 */       n1 -= d1 * n0;
/*  582 */       n2 -= d2 * n0;
/*  583 */       n3 -= d3 * n0;
/*  584 */       float n4 = -d4 * n0;
/*  585 */       if (nd % 2 != 0) {
/*  586 */         n1 = -n1; n2 = -n2; n3 = -n3; n4 = -n4;
/*      */       } 
/*  588 */       float yip4 = 0.0F, yip3 = 0.0F, yip2 = 0.0F, yip1 = 0.0F;
/*  589 */       float xip4 = 0.0F, xip3 = 0.0F, xip2 = 0.0F, xip1 = 0.0F;
/*  590 */       for (int j = m - 1; j >= 0; j--) {
/*  591 */         float xi = x[j];
/*  592 */         float yi = n1 * xip1 + n2 * xip2 + n3 * xip3 + n4 * xip4 - d1 * yip1 - d2 * yip2 - d3 * yip3 - d4 * yip4;
/*      */         
/*  594 */         y[j] = y[j] + yi;
/*  595 */         yip4 = yip3; yip3 = yip2; yip2 = yip1; yip1 = yi;
/*  596 */         xip4 = xip3; xip3 = xip2; xip2 = xip1; xip1 = xi;
/*      */       } 
/*      */     }
/*      */     
/*      */     void applyXN(int nd, float[][] x, float[][] y) {
/*  601 */       RecursiveGaussianFilter.checkArrays(x, y);
/*  602 */       if (RecursiveGaussianFilter.sameArrays(x, y))
/*  603 */         x = ArrayMath.copy(x); 
/*  604 */       int m2 = y.length;
/*  605 */       int m1 = (y[0]).length;
/*  606 */       float n0 = this._n0[nd], n1 = this._n1[nd], n2 = this._n2[nd], n3 = this._n3[nd];
/*  607 */       float d1 = this._d1[nd], d2 = this._d2[nd], d3 = this._d3[nd], d4 = this._d4[nd];
/*  608 */       float[] yim4 = new float[m1];
/*  609 */       float[] yim3 = new float[m1];
/*  610 */       float[] yim2 = new float[m1];
/*  611 */       float[] yim1 = new float[m1];
/*  612 */       float[] xim4 = new float[m1];
/*  613 */       float[] xim3 = new float[m1];
/*  614 */       float[] xim2 = new float[m1];
/*  615 */       float[] xim1 = new float[m1];
/*  616 */       float[] yi = new float[m1];
/*  617 */       float[] xi = new float[m1];
/*  618 */       for (int i2 = 0; i2 < m2; i2++) {
/*  619 */         float[] x2 = x[i2];
/*  620 */         float[] y2 = y[i2];
/*  621 */         for (int j = 0; j < m1; j++) {
/*  622 */           xi[j] = x2[j];
/*  623 */           yi[j] = n0 * xi[j] + n1 * xim1[j] + n2 * xim2[j] + n3 * xim3[j] - d1 * yim1[j] - d2 * yim2[j] - d3 * yim3[j] - d4 * yim4[j];
/*      */           
/*  625 */           y2[j] = yi[j];
/*      */         } 
/*  627 */         float[] yt = yim4;
/*  628 */         yim4 = yim3;
/*  629 */         yim3 = yim2;
/*  630 */         yim2 = yim1;
/*  631 */         yim1 = yi;
/*  632 */         yi = yt;
/*  633 */         float[] xt = xim3;
/*  634 */         xim3 = xim2;
/*  635 */         xim2 = xim1;
/*  636 */         xim1 = xi;
/*  637 */         xi = xt;
/*      */       } 
/*  639 */       n1 -= d1 * n0;
/*  640 */       n2 -= d2 * n0;
/*  641 */       n3 -= d3 * n0;
/*  642 */       float n4 = -d4 * n0;
/*  643 */       if (nd % 2 != 0) {
/*  644 */         n1 = -n1; n2 = -n2; n3 = -n3; n4 = -n4;
/*      */       } 
/*  646 */       float[] yip4 = yim4;
/*  647 */       float[] yip3 = yim3;
/*  648 */       float[] yip2 = yim2;
/*  649 */       float[] yip1 = yim1;
/*  650 */       float[] xip4 = xim4;
/*  651 */       float[] xip3 = xim3;
/*  652 */       float[] xip2 = xim2;
/*  653 */       float[] xip1 = xim1;
/*  654 */       for (int i1 = 0; i1 < m1; i1++) {
/*  655 */         yip4[i1] = 0.0F;
/*  656 */         yip3[i1] = 0.0F;
/*  657 */         yip2[i1] = 0.0F;
/*  658 */         yip1[i1] = 0.0F;
/*  659 */         xip4[i1] = 0.0F;
/*  660 */         xip3[i1] = 0.0F;
/*  661 */         xip2[i1] = 0.0F;
/*  662 */         xip1[i1] = 0.0F;
/*      */       } 
/*  664 */       for (int i = m2 - 1; i >= 0; i--) {
/*  665 */         float[] x2 = x[i];
/*  666 */         float[] y2 = y[i];
/*  667 */         for (int j = 0; j < m1; j++) {
/*  668 */           xi[j] = x2[j];
/*  669 */           yi[j] = n1 * xip1[j] + n2 * xip2[j] + n3 * xip3[j] + n4 * xip4[j] - d1 * yip1[j] - d2 * yip2[j] - d3 * yip3[j] - d4 * yip4[j];
/*      */           
/*  671 */           y2[j] = y2[j] + yi[j];
/*      */         } 
/*  673 */         float[] yt = yip4;
/*  674 */         yip4 = yip3;
/*  675 */         yip3 = yip2;
/*  676 */         yip2 = yip1;
/*  677 */         yip1 = yi;
/*  678 */         yi = yt;
/*  679 */         float[] xt = xip4;
/*  680 */         xip4 = xip3;
/*  681 */         xip3 = xip2;
/*  682 */         xip2 = xip1;
/*  683 */         xip1 = xi;
/*  684 */         xi = xt;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  693 */     private static double a00 = 1.6797292232361107D;
/*  694 */     private static double a10 = 3.734829826910358D;
/*  695 */     private static double b00 = 1.7831906544515104D;
/*  696 */     private static double b10 = 1.7228297663338028D;
/*  697 */     private static double c00 = -0.6802783501806897D;
/*  698 */     private static double c10 = -0.2598300478959625D;
/*  699 */     private static double w00 = 0.6318113174569493D;
/*  700 */     private static double w10 = 1.996927683248777D;
/*      */     
/*  702 */     private static double a01 = 0.649402400844062D;
/*  703 */     private static double a11 = 0.9557370760729773D;
/*  704 */     private static double b01 = 1.5159726670750566D;
/*  705 */     private static double b11 = 1.526760873479114D;
/*  706 */     private static double c01 = -0.6472105276644291D;
/*  707 */     private static double c11 = -4.530692304457076D;
/*  708 */     private static double w01 = 2.071895365878265D;
/*  709 */     private static double w11 = 0.6719055957689513D;
/*      */     
/*  711 */     private static double a02 = 0.3224570510072559D;
/*  712 */     private static double a12 = -1.7382843963561239D;
/*  713 */     private static double b02 = 1.313805492651688D;
/*  714 */     private static double b12 = 1.2402181393295362D;
/*  715 */     private static double c02 = -1.3312275593739595D;
/*  716 */     private static double c12 = 3.6607035671974897D;
/*  717 */     private static double w02 = 2.1656041357418863D;
/*  718 */     private static double w12 = 0.7479888745408682D;
/*      */     
/*  720 */     private static double[] a0 = new double[] { a00, a01, a02 };
/*  721 */     private static double[] a1 = new double[] { a10, a11, a12 };
/*  722 */     private static double[] b0 = new double[] { b00, b01, b02 };
/*  723 */     private static double[] b1 = new double[] { b10, b11, b12 };
/*  724 */     private static double[] c0 = new double[] { c00, c01, c02 };
/*  725 */     private static double[] c1 = new double[] { c10, c11, c12 };
/*  726 */     private static double[] w0 = new double[] { w00, w01, w02 };
/*  727 */     private static double[] w1 = new double[] { w10, w11, w12 };
/*      */     
/*      */     private float[] _n0;
/*      */     
/*      */     private float[] _n1;
/*      */     
/*      */     private float[] _n2;
/*      */     
/*      */     private float[] _n3;
/*      */     
/*      */     private float[] _d1;
/*      */     
/*      */     private float[] _d2;
/*      */     
/*      */     private float[] _d3;
/*      */     
/*      */     private float[] _d4;
/*      */ 
/*      */     
/*      */     private void makeND(double sigma) {
/*  747 */       this._n0 = new float[3];
/*  748 */       this._n1 = new float[3];
/*  749 */       this._n2 = new float[3];
/*  750 */       this._n3 = new float[3];
/*  751 */       this._d1 = new float[3];
/*  752 */       this._d2 = new float[3];
/*  753 */       this._d3 = new float[3];
/*  754 */       this._d4 = new float[3];
/*      */ 
/*      */       
/*  757 */       for (int i = 0; i < 3; i++) {
/*  758 */         double n0 = (i % 2 == 0) ? (a0[i] + c0[i]) : 0.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  764 */         double n1 = ArrayMath.exp(-b1[i] / sigma) * (c1[i] * ArrayMath.sin(w1[i] / sigma) - (c0[i] + 2.0D * a0[i]) * ArrayMath.cos(w1[i] / sigma)) + ArrayMath.exp(-b0[i] / sigma) * (a1[i] * ArrayMath.sin(w0[i] / sigma) - (2.0D * c0[i] + a0[i]) * ArrayMath.cos(w0[i] / sigma));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  770 */         double n2 = 2.0D * ArrayMath.exp(-(b0[i] + b1[i]) / sigma) * ((a0[i] + c0[i]) * ArrayMath.cos(w1[i] / sigma) * ArrayMath.cos(w0[i] / sigma) - a1[i] * ArrayMath.cos(w1[i] / sigma) * ArrayMath.sin(w0[i] / sigma) - c1[i] * ArrayMath.cos(w0[i] / sigma) * ArrayMath.sin(w1[i] / sigma)) + c0[i] * ArrayMath.exp(-2.0D * b0[i] / sigma) + a0[i] * ArrayMath.exp(-2.0D * b1[i] / sigma);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  776 */         double n3 = ArrayMath.exp(-(b1[i] + 2.0D * b0[i]) / sigma) * (c1[i] * ArrayMath.sin(w1[i] / sigma) - c0[i] * ArrayMath.cos(w1[i] / sigma)) + ArrayMath.exp(-(b0[i] + 2.0D * b1[i]) / sigma) * (a1[i] * ArrayMath.sin(w0[i] / sigma) - a0[i] * ArrayMath.cos(w0[i] / sigma));
/*      */         
/*  778 */         double d1 = -2.0D * ArrayMath.exp(-b0[i] / sigma) * ArrayMath.cos(w0[i] / sigma) - 2.0D * ArrayMath.exp(-b1[i] / sigma) * ArrayMath.cos(w1[i] / sigma);
/*      */ 
/*      */ 
/*      */         
/*  782 */         double d2 = 4.0D * ArrayMath.exp(-(b0[i] + b1[i]) / sigma) * ArrayMath.cos(w0[i] / sigma) * ArrayMath.cos(w1[i] / sigma) + ArrayMath.exp(-2.0D * b0[i] / sigma) + ArrayMath.exp(-2.0D * b1[i] / sigma);
/*      */         
/*  784 */         double d3 = -2.0D * ArrayMath.exp(-(b0[i] + 2.0D * b1[i]) / sigma) * ArrayMath.cos(w0[i] / sigma) - 2.0D * ArrayMath.exp(-(b1[i] + 2.0D * b0[i]) / sigma) * ArrayMath.cos(w1[i] / sigma);
/*  785 */         double d4 = ArrayMath.exp(-2.0D * (b0[i] + b1[i]) / sigma);
/*  786 */         this._n0[i] = (float)n0;
/*  787 */         this._n1[i] = (float)n1;
/*  788 */         this._n2[i] = (float)n2;
/*  789 */         this._n3[i] = (float)n3;
/*  790 */         this._d1[i] = (float)d1;
/*  791 */         this._d2[i] = (float)d2;
/*  792 */         this._d3[i] = (float)d3;
/*  793 */         this._d4[i] = (float)d4;
/*      */       } 
/*  795 */       scaleN(sigma);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void scaleN(double sigma) {
/*  805 */       int n = 1 + 2 * (int)(10.0D * sigma);
/*  806 */       float[] x = new float[n];
/*  807 */       float[] y0 = new float[n];
/*  808 */       float[] y1 = new float[n];
/*  809 */       float[] y2 = new float[n];
/*  810 */       int m = (n - 1) / 2;
/*  811 */       x[m] = 1.0F;
/*  812 */       applyN(0, x, y0);
/*  813 */       applyN(1, x, y1);
/*  814 */       applyN(2, x, y2);
/*  815 */       double[] s = new double[3]; int i, j;
/*  816 */       for (i = 0, j = n - 1; i < j; i++, j--) {
/*  817 */         double t = (i - m);
/*  818 */         s[0] = s[0] + (y0[j] + y0[i]);
/*  819 */         s[1] = s[1] + ArrayMath.sin(t / sigma) * (y1[j] - y1[i]);
/*  820 */         s[2] = s[2] + ArrayMath.cos(t * ArrayMath.sqrt(2.0D) / sigma) * (y2[j] + y2[i]);
/*      */       } 
/*  822 */       s[0] = s[0] + y0[m];
/*  823 */       s[2] = s[2] + y2[m];
/*  824 */       s[1] = s[1] * sigma * ArrayMath.exp(0.5D);
/*  825 */       s[2] = s[2] * -(sigma * sigma) / 2.0D * ArrayMath.exp(1.0D);
/*  826 */       for (i = 0; i < 3; i++) {
/*  827 */         this._n0[i] = (float)(this._n0[i] / s[i]);
/*  828 */         this._n1[i] = (float)(this._n1[i] / s[i]);
/*  829 */         this._n2[i] = (float)(this._n2[i] / s[i]);
/*  830 */         this._n3[i] = (float)(this._n3[i] / s[i]);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private static class VanVlietFilter
/*      */     extends Filter
/*      */   {
/*      */     VanVlietFilter(double sigma) {
/*  839 */       makeG(sigma);
/*      */     }
/*      */     
/*      */     void applyN(int nd, float[] x, float[] y) {
/*  843 */       RecursiveGaussianFilter.checkArrays(x, y);
/*  844 */       if (RecursiveGaussianFilter.sameArrays(x, y))
/*  845 */         x = ArrayMath.copy(x); 
/*  846 */       this._g[nd][0][0].applyForward(x, y);
/*  847 */       this._g[nd][0][1].accumulateReverse(x, y);
/*  848 */       this._g[nd][1][0].accumulateForward(x, y);
/*  849 */       this._g[nd][1][1].accumulateReverse(x, y);
/*      */     }
/*      */     
/*      */     void applyXN(int nd, float[][] x, float[][] y) {
/*  853 */       RecursiveGaussianFilter.checkArrays(x, y);
/*  854 */       if (RecursiveGaussianFilter.sameArrays(x, y))
/*  855 */         x = ArrayMath.copy(x); 
/*  856 */       this._g[nd][0][0].apply2Forward(x, y);
/*  857 */       this._g[nd][0][1].accumulate2Reverse(x, y);
/*  858 */       this._g[nd][1][0].accumulate2Forward(x, y);
/*  859 */       this._g[nd][1][1].accumulate2Reverse(x, y);
/*      */     }
/*      */ 
/*      */     
/*  863 */     private static Cdouble[][] POLES = new Cdouble[][] { { new Cdouble(1.12075D, 1.27788D), new Cdouble(1.12075D, -1.27788D), new Cdouble(1.76952D, 0.46611D), new Cdouble(1.76952D, -0.46611D) }, { new Cdouble(1.04185D, 1.24034D), new Cdouble(1.04185D, -1.24034D), new Cdouble(1.69747D, 0.4479D), new Cdouble(1.69747D, -0.4479D) }, { new Cdouble(0.9457D, 1.21064D), new Cdouble(0.9457D, -1.21064D), new Cdouble(1.60161D, 0.42647D), new Cdouble(1.60161D, -0.42647D) } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Recursive2ndOrderFilter[][][] _g;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void makeG(double sigma) {
/*  881 */       this._g = new Recursive2ndOrderFilter[3][2][2];
/*      */ 
/*      */       
/*  884 */       for (int nd = 0; nd < 3; nd++) {
/*      */ 
/*      */         
/*  887 */         Cdouble[] poles = adjustPoles(sigma, POLES[nd]);
/*      */ 
/*      */         
/*  890 */         double gain = computeGain(poles);
/*  891 */         double gg = gain * gain;
/*      */ 
/*      */         
/*  894 */         Cdouble d0 = new Cdouble(poles[0]);
/*  895 */         Cdouble d1 = new Cdouble(poles[2]);
/*  896 */         Cdouble e0 = d0.inv();
/*  897 */         Cdouble e1 = d1.inv();
/*  898 */         Cdouble g0 = gr(nd, d0, poles, gg);
/*  899 */         Cdouble g1 = gr(nd, d1, poles, gg);
/*      */ 
/*      */         
/*  902 */         double a10 = -2.0D * d0.r;
/*  903 */         double a11 = -2.0D * d1.r;
/*  904 */         double a20 = d0.norm();
/*  905 */         double a21 = d1.norm();
/*      */ 
/*      */ 
/*      */         
/*  909 */         if (nd == 0 || nd == 2) {
/*  910 */           double b10 = g0.i / e0.i;
/*  911 */           double b11 = g1.i / e1.i;
/*  912 */           double b00 = g0.r - b10 * e0.r;
/*  913 */           double b01 = g1.r - b11 * e1.r;
/*  914 */           double b20 = 0.0D;
/*  915 */           double b21 = 0.0D;
/*  916 */           this._g[nd][0][0] = makeFilter(b00, b10, b20, a10, a20);
/*  917 */           this._g[nd][1][0] = makeFilter(b01, b11, b21, a11, a21);
/*  918 */           b20 -= b00 * a20;
/*  919 */           b21 -= b01 * a21;
/*  920 */           b10 -= b00 * a10;
/*  921 */           b11 -= b01 * a11;
/*  922 */           b00 = 0.0D;
/*  923 */           b01 = 0.0D;
/*  924 */           this._g[nd][0][1] = makeFilter(b00, b10, b20, a10, a20);
/*  925 */           this._g[nd][1][1] = makeFilter(b01, b11, b21, a11, a21);
/*      */         
/*      */         }
/*  928 */         else if (nd == 1) {
/*  929 */           double b20 = g0.i / e0.i;
/*  930 */           double b21 = g1.i / e1.i;
/*  931 */           double b10 = g0.r - b20 * e0.r;
/*  932 */           double b11 = g1.r - b21 * e1.r;
/*  933 */           double b00 = 0.0D;
/*  934 */           double b01 = 0.0D;
/*  935 */           this._g[nd][0][0] = makeFilter(b00, b10, b20, a10, a20);
/*  936 */           this._g[nd][1][0] = makeFilter(b01, b11, b21, a11, a21);
/*  937 */           b20 = -b20;
/*  938 */           b21 = -b21;
/*  939 */           b10 = -b10;
/*  940 */           b11 = -b11;
/*  941 */           b00 = 0.0D;
/*  942 */           b01 = 0.0D;
/*  943 */           this._g[nd][0][1] = makeFilter(b00, b10, b20, a10, a20);
/*  944 */           this._g[nd][1][1] = makeFilter(b01, b11, b21, a11, a21);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     Recursive2ndOrderFilter makeFilter(double b0, double b1, double b2, double a1, double a2) {
/*  951 */       return new Recursive2ndOrderFilter((float)b0, (float)b1, (float)b2, (float)a1, (float)a2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Cdouble gr(int nd, Cdouble polej, Cdouble[] poles, double gain) {
/*  959 */       Cdouble pj = polej;
/*  960 */       Cdouble qj = pj.inv();
/*  961 */       Cdouble c1 = new Cdouble(1.0D, 0.0D);
/*  962 */       Cdouble gz = new Cdouble(c1);
/*  963 */       if (nd == 1) {
/*  964 */         gz.timesEquals(c1.minus(qj));
/*  965 */         gz.timesEquals(c1.plus(pj));
/*  966 */         gz.timesEquals(pj);
/*  967 */         gz.timesEquals(0.5D);
/*  968 */       } else if (nd == 2) {
/*  969 */         gz.timesEquals(c1.minus(qj));
/*  970 */         gz.timesEquals(c1.minus(pj));
/*  971 */         gz.timesEquals(-1.0D);
/*      */       } 
/*  973 */       Cdouble gp = new Cdouble(c1);
/*  974 */       int np = poles.length;
/*  975 */       for (int ip = 0; ip < np; ip++) {
/*  976 */         Cdouble pi = poles[ip];
/*  977 */         if (!pi.equals(pj) && !pi.equals(pj.conj()))
/*  978 */           gp.timesEquals(c1.minus(pi.times(qj))); 
/*  979 */         gp.timesEquals(c1.minus(pi.times(pj)));
/*      */       } 
/*  981 */       return gz.over(gp).times(gain);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static Cdouble[] adjustPoles(double sigma, Cdouble[] poles) {
/*  987 */       double q = sigma;
/*  988 */       double s = computeSigma(q, poles);
/*  989 */       for (int iter = 0; ArrayMath.abs(sigma - s) > sigma * 1.0E-8D; iter++) {
/*      */         
/*  991 */         Check.state((iter < 100), "number of iterations less than 100");
/*  992 */         s = computeSigma(q, poles);
/*  993 */         q *= sigma / s;
/*      */       } 
/*      */ 
/*      */       
/*  997 */       int npole = poles.length;
/*  998 */       Cdouble[] apoles = new Cdouble[npole];
/*  999 */       for (int ipole = 0; ipole < npole; ipole++) {
/* 1000 */         Cdouble pi = poles[ipole];
/* 1001 */         double a = ArrayMath.pow(pi.abs(), 2.0D / q);
/* 1002 */         double t = ArrayMath.atan2(pi.i, pi.r) * 2.0D / q;
/* 1003 */         apoles[ipole] = Cdouble.polar(a, t).inv();
/*      */       } 
/* 1005 */       return apoles;
/*      */     }
/*      */     
/*      */     private static double computeGain(Cdouble[] poles) {
/* 1009 */       int npole = poles.length;
/* 1010 */       Cdouble c1 = new Cdouble(1.0D, 0.0D);
/* 1011 */       Cdouble cg = new Cdouble(c1);
/* 1012 */       for (int ipole = 0; ipole < npole; ipole++) {
/* 1013 */         cg.timesEquals(c1.minus(poles[ipole]));
/*      */       }
/* 1015 */       return cg.r;
/*      */     }
/*      */     
/*      */     private static double computeSigma(double sigma, Cdouble[] poles) {
/* 1019 */       int npole = poles.length;
/* 1020 */       double q = sigma / 2.0D;
/* 1021 */       Cdouble c1 = new Cdouble(1.0D);
/* 1022 */       Cdouble cs = new Cdouble();
/* 1023 */       for (int ipole = 0; ipole < npole; ipole++) {
/* 1024 */         Cdouble pi = poles[ipole];
/* 1025 */         double a = ArrayMath.pow(pi.abs(), -1.0D / q);
/* 1026 */         double t = ArrayMath.atan2(pi.i, pi.r) / q;
/* 1027 */         Cdouble b = Cdouble.polar(a, t);
/* 1028 */         Cdouble c = c1.minus(b);
/* 1029 */         Cdouble d = c.times(c);
/* 1030 */         cs.plusEquals(b.times(2.0D).over(d));
/*      */       } 
/* 1032 */       return ArrayMath.sqrt(cs.r);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/RecursiveGaussianFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */