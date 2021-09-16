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
/*      */ public class MinimumPhaseFilter
/*      */ {
/*      */   private int _m;
/*      */   private int _min1;
/*      */   private int _max1;
/*      */   private int _min2;
/*      */   private int _max2;
/*      */   private int _min3;
/*      */   private int _max3;
/*      */   private int[] _lag1;
/*      */   private int[] _lag2;
/*      */   private int[] _lag3;
/*      */   private float[] _a;
/*      */   private float _a0;
/*      */   private float _a0i;
/*      */   private float[][] _ai;
/*      */   private float[] _ai0;
/*      */   private float[] _ai0i;
/*      */   
/*      */   public MinimumPhaseFilter(int[] lag1) {
/*   49 */     this(lag1, impulse(lag1.length));
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
/*      */   public MinimumPhaseFilter(int[] lag1, int[] lag2) {
/*   63 */     this(lag1, lag2, impulse(lag1.length));
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
/*      */   public MinimumPhaseFilter(int[] lag1, int[] lag2, int[] lag3) {
/*   78 */     this(lag1, lag2, lag3, impulse(lag1.length));
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
/*      */   public MinimumPhaseFilter(int[] lag1, float[] a) {
/*   91 */     initLags(lag1, a);
/*   92 */     initA(a);
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
/*      */   public MinimumPhaseFilter(int[] lag1, int[] lag2, float[] a) {
/*  107 */     initLags(lag1, lag2, a);
/*  108 */     initA(a);
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
/*      */   public MinimumPhaseFilter(int[] lag1, int[] lag2, float[][] a) {
/*  123 */     initLags(lag1, lag2, a[0]);
/*  124 */     initA(a);
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
/*      */   public MinimumPhaseFilter(int[] lag1, int[] lag2, int[] lag3, float[] a) {
/*  140 */     initLags(lag1, lag2, lag3, a);
/*  141 */     initA(a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getLag1() {
/*  149 */     return ArrayMath.copy(this._lag1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getLag2() {
/*  157 */     return ArrayMath.copy(this._lag2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getLag3() {
/*  165 */     return ArrayMath.copy(this._lag3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] getA() {
/*  173 */     return ArrayMath.copy(this._a);
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
/*      */   public void factorWilsonBurg(int maxiter, float epsilon, float[] r) {
/*  192 */     Check.argument((r.length % 2 == 1), "r.length is odd");
/*      */ 
/*      */     
/*  195 */     int m1 = this._max1 - this._min1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  202 */     int n1 = r.length + 10 * m1;
/*      */ 
/*      */     
/*  205 */     int l1 = (r.length - 1) / 2;
/*  206 */     int k1 = n1 - 1 - this._max1;
/*      */ 
/*      */     
/*  209 */     float[] s = new float[n1];
/*  210 */     float[] t = new float[n1];
/*  211 */     float[] u = new float[n1];
/*      */ 
/*      */     
/*  214 */     ArrayMath.copy(r.length, 0, r, k1 - l1, s);
/*      */ 
/*      */     
/*  217 */     ArrayMath.zero(this._a);
/*  218 */     this._a[0] = ArrayMath.sqrt(s[k1]);
/*  219 */     this._a0 = this._a[0];
/*  220 */     this._a0i = 1.0F / this._a[0];
/*      */ 
/*      */ 
/*      */     
/*  224 */     boolean converged = false;
/*  225 */     float eemax = s[k1] * epsilon;
/*  226 */     for (int niter = 0; niter < maxiter && !converged; niter++) {
/*      */ 
/*      */ 
/*      */       
/*  230 */       applyInverseTranspose(s, t);
/*  231 */       applyInverse(t, u);
/*  232 */       u[k1] = u[k1] + 1.0F;
/*      */ 
/*      */       
/*  235 */       u[k1] = u[k1] * 0.5F;
/*  236 */       for (int i1 = 0; i1 < k1; i1++) {
/*  237 */         u[i1] = 0.0F;
/*      */       }
/*      */       
/*  240 */       apply(u, t);
/*  241 */       converged = true;
/*  242 */       for (int j = 0; j < this._m; j++) {
/*  243 */         int j1 = k1 + this._lag1[j];
/*  244 */         if (0 <= j1 && j1 < n1) {
/*  245 */           float aj = t[j1];
/*  246 */           if (converged) {
/*  247 */             float e = this._a[j] - aj;
/*  248 */             converged = (e * e <= eemax);
/*      */           } 
/*  250 */           this._a[j] = aj;
/*      */         } 
/*      */       } 
/*  253 */       this._a0 = this._a[0];
/*  254 */       this._a0i = 1.0F / this._a[0];
/*      */     } 
/*  256 */     Check.state(converged, "Wilson-Burg iterations converged");
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
/*      */   public void factorWilsonBurg(int maxiter, float epsilon, float[][] r) {
/*  275 */     Check.argument(((r[0]).length % 2 == 1), "r[0].length is odd");
/*  276 */     Check.argument((r.length % 2 == 1), "r.length is odd");
/*      */ 
/*      */     
/*  279 */     int m1 = this._max1 - this._min1;
/*  280 */     int m2 = this._max2 - this._min2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  287 */     int n1 = (r[0]).length + 10 * m1;
/*  288 */     int n2 = r.length + 10 * m2;
/*      */ 
/*      */     
/*  291 */     int l1 = ((r[0]).length - 1) / 2;
/*  292 */     int l2 = (r.length - 1) / 2;
/*  293 */     int k1 = n1 - 1 - this._max1;
/*  294 */     int k2 = n2 - 1 - this._max2;
/*      */ 
/*      */     
/*  297 */     float[][] s = new float[n2][n1];
/*  298 */     float[][] t = new float[n2][n1];
/*  299 */     float[][] u = new float[n2][n1];
/*      */ 
/*      */     
/*  302 */     ArrayMath.copy((r[0]).length, r.length, 0, 0, r, k1 - l1, k2 - l2, s);
/*      */ 
/*      */     
/*  305 */     ArrayMath.zero(this._a);
/*  306 */     this._a[0] = ArrayMath.sqrt(s[k2][k1]);
/*  307 */     this._a0 = this._a[0];
/*  308 */     this._a0i = 1.0F / this._a[0];
/*      */ 
/*      */ 
/*      */     
/*  312 */     boolean converged = false;
/*  313 */     float eemax = s[k2][k1] * epsilon;
/*  314 */     for (int niter = 0; niter < maxiter && !converged; niter++) {
/*      */ 
/*      */ 
/*      */       
/*  318 */       applyInverseTranspose(s, t);
/*  319 */       applyInverse(t, u);
/*  320 */       u[k2][k1] = u[k2][k1] + 1.0F;
/*      */ 
/*      */       
/*  323 */       u[k2][k1] = u[k2][k1] * 0.5F;
/*  324 */       for (int i2 = 0; i2 < k2; i2++) {
/*  325 */         for (int i = 0; i < n1; i++)
/*  326 */           u[i2][i] = 0.0F; 
/*  327 */       }  for (int i1 = 0; i1 < k1; i1++) {
/*  328 */         u[k2][i1] = 0.0F;
/*      */       }
/*      */       
/*  331 */       apply(u, t);
/*  332 */       converged = true;
/*  333 */       for (int j = 0; j < this._m; j++) {
/*  334 */         int j1 = k1 + this._lag1[j];
/*  335 */         int j2 = k2 + this._lag2[j];
/*  336 */         if (0 <= j1 && j1 < n1 && 0 <= j2 && j2 < n2) {
/*  337 */           float aj = t[j2][j1];
/*  338 */           if (converged) {
/*  339 */             float e = this._a[j] - aj;
/*  340 */             converged = (e * e <= eemax);
/*      */           } 
/*  342 */           this._a[j] = aj;
/*      */         } 
/*      */       } 
/*  345 */       this._a0 = this._a[0];
/*  346 */       this._a0i = 1.0F / this._a[0];
/*      */     } 
/*  348 */     Check.state(converged, "Wilson-Burg iterations converged");
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
/*      */   public void factorWilsonBurg(int maxiter, float epsilon, float[][][] r) {
/*  367 */     Check.argument(((r[0][0]).length % 2 == 1), "r[0][0].length is odd");
/*  368 */     Check.argument(((r[0]).length % 2 == 1), "r[0].length is odd");
/*  369 */     Check.argument((r.length % 2 == 1), "r.length is odd");
/*      */ 
/*      */     
/*  372 */     int m1 = this._max1 - this._min1;
/*  373 */     int m2 = this._max2 - this._min2;
/*  374 */     int m3 = this._max3 - this._min3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  381 */     int n1 = (r[0][0]).length + 10 * m1;
/*  382 */     int n2 = (r[0]).length + 10 * m2;
/*  383 */     int n3 = r.length + 10 * m3;
/*      */ 
/*      */     
/*  386 */     int l1 = ((r[0][0]).length - 1) / 2;
/*  387 */     int l2 = ((r[0]).length - 1) / 2;
/*  388 */     int l3 = (r.length - 1) / 2;
/*  389 */     int k1 = n1 - 1 - this._max1;
/*  390 */     int k2 = n2 - 1 - this._max2;
/*  391 */     int k3 = n3 - 1 - this._max3;
/*      */ 
/*      */     
/*  394 */     float[][][] s = new float[n3][n2][n1];
/*  395 */     float[][][] t = new float[n3][n2][n1];
/*  396 */     float[][][] u = new float[n3][n2][n1];
/*      */ 
/*      */     
/*  399 */     ArrayMath.copy((r[0][0]).length, (r[0]).length, r.length, 0, 0, 0, r, k1 - l1, k2 - l2, k3 - l3, s);
/*      */ 
/*      */     
/*  402 */     ArrayMath.zero(this._a);
/*  403 */     this._a[0] = ArrayMath.sqrt(s[k3][k2][k1]);
/*  404 */     this._a0 = this._a[0];
/*  405 */     this._a0i = 1.0F / this._a[0];
/*      */ 
/*      */ 
/*      */     
/*  409 */     boolean converged = false;
/*  410 */     float eemax = s[k3][k2][k1] * epsilon;
/*  411 */     for (int niter = 0; niter < maxiter && !converged; niter++) {
/*      */ 
/*      */ 
/*      */       
/*  415 */       applyInverseTranspose(s, t);
/*  416 */       applyInverse(t, u);
/*  417 */       u[k3][k2][k1] = u[k3][k2][k1] + 1.0F;
/*      */ 
/*      */       
/*  420 */       u[k3][k2][k1] = u[k3][k2][k1] * 0.5F;
/*  421 */       for (int i3 = 0; i3 < k3; i3++) {
/*  422 */         for (int i = 0; i < n2; i++)
/*  423 */         { for (int k = 0; k < n1; k++)
/*  424 */             u[i3][i][k] = 0.0F;  } 
/*  425 */       }  for (int i2 = 0; i2 < k2; i2++) {
/*  426 */         for (int i = 0; i < n1; i++)
/*  427 */           u[k3][i2][i] = 0.0F; 
/*  428 */       }  for (int i1 = 0; i1 < k1; i1++) {
/*  429 */         u[k3][k2][i1] = 0.0F;
/*      */       }
/*      */       
/*  432 */       apply(u, t);
/*  433 */       converged = true;
/*  434 */       for (int j = 0; j < this._m; j++) {
/*  435 */         int j1 = k1 + this._lag1[j];
/*  436 */         int j2 = k2 + this._lag2[j];
/*  437 */         int j3 = k3 + this._lag3[j];
/*  438 */         if (0 <= j1 && j1 < n1 && 0 <= j2 && j2 < n2 && 0 <= j3 && j3 < n3) {
/*  439 */           float aj = t[j3][j2][j1];
/*  440 */           if (converged) {
/*  441 */             float e = this._a[j] - aj;
/*  442 */             converged = (e * e <= eemax);
/*      */           } 
/*  444 */           this._a[j] = aj;
/*      */         } 
/*      */       } 
/*  447 */       this._a0 = this._a[0];
/*  448 */       this._a0i = 1.0F / this._a[0];
/*      */     } 
/*  450 */     Check.state(converged, "Wilson-Burg iterations converged");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply(float[] x, float[] y) {
/*  459 */     int n1 = y.length;
/*  460 */     int i1lo = ArrayMath.min(this._max1, n1); int i1;
/*  461 */     for (i1 = 0; i1 < i1lo; i1++) {
/*  462 */       float yi = this._a0 * x[i1];
/*  463 */       for (int j = 1; j < this._m; j++) {
/*  464 */         int k1 = i1 - this._lag1[j];
/*  465 */         if (0 <= k1)
/*  466 */           yi += this._a[j] * x[k1]; 
/*      */       } 
/*  468 */       y[i1] = yi;
/*      */     } 
/*  470 */     for (i1 = i1lo; i1 < n1; i1++) {
/*  471 */       float yi = this._a0 * x[i1];
/*  472 */       for (int j = 1; j < this._m; j++) {
/*  473 */         int k1 = i1 - this._lag1[j];
/*  474 */         yi += this._a[j] * x[k1];
/*      */       } 
/*  476 */       y[i1] = yi;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply(float[][] x, float[][] y) {
/*  486 */     int n1 = (y[0]).length;
/*  487 */     int n2 = y.length;
/*  488 */     int i1lo = ArrayMath.max(0, this._max1);
/*  489 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  490 */     int i2lo = (i1lo <= i1hi) ? ArrayMath.min(this._max2, n2) : n2; int i2;
/*  491 */     for (i2 = 0; i2 < i2lo; i2++) {
/*  492 */       for (int i1 = 0; i1 < n1; i1++) {
/*  493 */         float yi = this._a0 * x[i2][i1];
/*  494 */         for (int j = 1; j < this._m; j++) {
/*  495 */           int k1 = i1 - this._lag1[j];
/*  496 */           int k2 = i2 - this._lag2[j];
/*  497 */           if (0 <= k1 && k1 < n1 && 0 <= k2)
/*  498 */             yi += this._a[j] * x[k2][k1]; 
/*      */         } 
/*  500 */         y[i2][i1] = yi;
/*      */       } 
/*      */     } 
/*  503 */     for (i2 = i2lo; i2 < n2; i2++) {
/*  504 */       int i1; for (i1 = 0; i1 < i1lo; i1++) {
/*  505 */         float yi = this._a0 * x[i2][i1];
/*  506 */         for (int j = 1; j < this._m; j++) {
/*  507 */           int k1 = i1 - this._lag1[j];
/*  508 */           int k2 = i2 - this._lag2[j];
/*  509 */           if (0 <= k1)
/*  510 */             yi += this._a[j] * x[k2][k1]; 
/*      */         } 
/*  512 */         y[i2][i1] = yi;
/*      */       } 
/*  514 */       for (i1 = i1lo; i1 < i1hi; i1++) {
/*  515 */         float yi = this._a0 * x[i2][i1];
/*  516 */         for (int j = 1; j < this._m; j++) {
/*  517 */           int k1 = i1 - this._lag1[j];
/*  518 */           int k2 = i2 - this._lag2[j];
/*  519 */           yi += this._a[j] * x[k2][k1];
/*      */         } 
/*  521 */         y[i2][i1] = yi;
/*      */       } 
/*  523 */       for (i1 = i1hi; i1 < n1; i1++) {
/*  524 */         float yi = this._a0 * x[i2][i1];
/*  525 */         for (int j = 1; j < this._m; j++) {
/*  526 */           int k1 = i1 - this._lag1[j];
/*  527 */           int k2 = i2 - this._lag2[j];
/*  528 */           if (k1 < n1)
/*  529 */             yi += this._a[j] * x[k2][k1]; 
/*      */         } 
/*  531 */         y[i2][i1] = yi;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply(float[][][] x, float[][][] y) {
/*  542 */     int n1 = (y[0][0]).length;
/*  543 */     int n2 = (y[0]).length;
/*  544 */     int n3 = y.length;
/*  545 */     int i1lo = ArrayMath.max(0, this._max1);
/*  546 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  547 */     int i2lo = ArrayMath.max(0, this._max2);
/*  548 */     int i2hi = ArrayMath.min(n2, n2 + this._min2);
/*  549 */     int i3lo = (i1lo <= i1hi && i2lo <= i2hi) ? ArrayMath.min(this._max3, n3) : n3; int i3;
/*  550 */     for (i3 = 0; i3 < i3lo; i3++) {
/*  551 */       for (int i2 = 0; i2 < n2; i2++) {
/*  552 */         for (int i1 = 0; i1 < n1; i1++) {
/*  553 */           float yi = this._a0 * x[i3][i2][i1];
/*  554 */           for (int j = 1; j < this._m; j++) {
/*  555 */             int k1 = i1 - this._lag1[j];
/*  556 */             int k2 = i2 - this._lag2[j];
/*  557 */             int k3 = i3 - this._lag3[j];
/*  558 */             if (0 <= k1 && k1 < n1 && 0 <= k2 && k2 < n2 && 0 <= k3)
/*  559 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  561 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*      */     } 
/*  565 */     for (i3 = i3lo; i3 < n3; i3++) {
/*  566 */       int i2; for (i2 = 0; i2 < i2lo; i2++) {
/*  567 */         for (int i1 = 0; i1 < n1; i1++) {
/*  568 */           float yi = this._a0 * x[i3][i2][i1];
/*  569 */           for (int j = 1; j < this._m; j++) {
/*  570 */             int k1 = i1 - this._lag1[j];
/*  571 */             int k2 = i2 - this._lag2[j];
/*  572 */             int k3 = i3 - this._lag3[j];
/*  573 */             if (0 <= k2 && 0 <= k1 && k1 < n1)
/*  574 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  576 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*  579 */       for (i2 = i2lo; i2 < i2hi; i2++) {
/*  580 */         int i1; for (i1 = 0; i1 < i1lo; i1++) {
/*  581 */           float yi = this._a0 * x[i3][i2][i1];
/*  582 */           for (int j = 1; j < this._m; j++) {
/*  583 */             int k1 = i1 - this._lag1[j];
/*  584 */             int k2 = i2 - this._lag2[j];
/*  585 */             int k3 = i3 - this._lag3[j];
/*  586 */             if (0 <= k1)
/*  587 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  589 */           y[i3][i2][i1] = yi;
/*      */         } 
/*  591 */         for (i1 = i1lo; i1 < i1hi; i1++) {
/*  592 */           float yi = this._a0 * x[i3][i2][i1];
/*  593 */           for (int j = 1; j < this._m; j++) {
/*  594 */             int k1 = i1 - this._lag1[j];
/*  595 */             int k2 = i2 - this._lag2[j];
/*  596 */             int k3 = i3 - this._lag3[j];
/*  597 */             yi += this._a[j] * x[k3][k2][k1];
/*      */           } 
/*  599 */           y[i3][i2][i1] = yi;
/*      */         } 
/*  601 */         for (i1 = i1hi; i1 < n1; i1++) {
/*  602 */           float yi = this._a0 * x[i3][i2][i1];
/*  603 */           for (int j = 1; j < this._m; j++) {
/*  604 */             int k1 = i1 - this._lag1[j];
/*  605 */             int k2 = i2 - this._lag2[j];
/*  606 */             int k3 = i3 - this._lag3[j];
/*  607 */             if (k1 < n1)
/*  608 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  610 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*  613 */       for (i2 = i2hi; i2 < n2; i2++) {
/*  614 */         for (int i1 = 0; i1 < n1; i1++) {
/*  615 */           float yi = this._a0 * x[i3][i2][i1];
/*  616 */           for (int j = 1; j < this._m; j++) {
/*  617 */             int k1 = i1 - this._lag1[j];
/*  618 */             int k2 = i2 - this._lag2[j];
/*  619 */             int k3 = i3 - this._lag3[j];
/*  620 */             if (k2 < n2 && 0 <= k1 && k1 < n1)
/*  621 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  623 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply(int[][] i, float[][] x, float[][] y) {
/*  636 */     int n1 = (y[0]).length;
/*  637 */     int n2 = y.length;
/*  638 */     int i1lo = ArrayMath.max(0, this._max1);
/*  639 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  640 */     int i2lo = (i1lo <= i1hi) ? ArrayMath.min(this._max2, n2) : n2; int i2;
/*  641 */     for (i2 = 0; i2 < i2lo; i2++) {
/*  642 */       for (int i1 = 0; i1 < n1; i1++) {
/*  643 */         int ii = i[i2][i1];
/*  644 */         float[] aii = this._ai[ii];
/*  645 */         float yi = this._ai0[ii] * x[i2][i1];
/*  646 */         for (int j = 1; j < this._m; j++) {
/*  647 */           int k1 = i1 - this._lag1[j];
/*  648 */           int k2 = i2 - this._lag2[j];
/*  649 */           if (0 <= k1 && k1 < n1 && 0 <= k2)
/*  650 */             yi += aii[j] * x[k2][k1]; 
/*      */         } 
/*  652 */         y[i2][i1] = yi;
/*      */       } 
/*      */     } 
/*  655 */     for (i2 = i2lo; i2 < n2; i2++) {
/*  656 */       int i1; for (i1 = 0; i1 < i1lo; i1++) {
/*  657 */         int ii = i[i2][i1];
/*  658 */         float[] aii = this._ai[ii];
/*  659 */         float yi = this._ai0[ii] * x[i2][i1];
/*  660 */         for (int j = 1; j < this._m; j++) {
/*  661 */           int k1 = i1 - this._lag1[j];
/*  662 */           int k2 = i2 - this._lag2[j];
/*  663 */           if (0 <= k1)
/*  664 */             yi += aii[j] * x[k2][k1]; 
/*      */         } 
/*  666 */         y[i2][i1] = yi;
/*      */       } 
/*  668 */       for (i1 = i1lo; i1 < i1hi; i1++) {
/*  669 */         int ii = i[i2][i1];
/*  670 */         float[] aii = this._ai[ii];
/*  671 */         float yi = this._ai0[ii] * x[i2][i1];
/*  672 */         for (int j = 1; j < this._m; j++) {
/*  673 */           int k1 = i1 - this._lag1[j];
/*  674 */           int k2 = i2 - this._lag2[j];
/*  675 */           yi += aii[j] * x[k2][k1];
/*      */         } 
/*  677 */         y[i2][i1] = yi;
/*      */       } 
/*  679 */       for (i1 = i1hi; i1 < n1; i1++) {
/*  680 */         int ii = i[i2][i1];
/*  681 */         float[] aii = this._ai[ii];
/*  682 */         float yi = this._ai0[ii] * x[i2][i1];
/*  683 */         for (int j = 1; j < this._m; j++) {
/*  684 */           int k1 = i1 - this._lag1[j];
/*  685 */           int k2 = i2 - this._lag2[j];
/*  686 */           if (k1 < n1)
/*  687 */             yi += aii[j] * x[k2][k1]; 
/*      */         } 
/*  689 */         y[i2][i1] = yi;
/*      */       } 
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
/*      */   public void applyTranspose(float[] x, float[] y) {
/*  704 */     int n1 = y.length;
/*  705 */     int i1hi = ArrayMath.max(n1 - this._max1, 0); int i1;
/*  706 */     for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/*  707 */       float yi = this._a0 * x[i1];
/*  708 */       for (int j = 1; j < this._m; j++) {
/*  709 */         int k1 = i1 + this._lag1[j];
/*  710 */         if (k1 < n1)
/*  711 */           yi += this._a[j] * x[k1]; 
/*      */       } 
/*  713 */       y[i1] = yi;
/*      */     } 
/*  715 */     for (i1 = i1hi - 1; i1 >= 0; i1--) {
/*  716 */       float yi = this._a0 * x[i1];
/*  717 */       for (int j = 1; j < this._m; j++) {
/*  718 */         int k1 = i1 + this._lag1[j];
/*  719 */         yi += this._a[j] * x[k1];
/*      */       } 
/*  721 */       y[i1] = yi;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyTranspose(float[][] x, float[][] y) {
/*  732 */     int n1 = (y[0]).length;
/*  733 */     int n2 = y.length;
/*  734 */     int i1lo = ArrayMath.max(0, -this._min1);
/*  735 */     int i1hi = ArrayMath.min(n1, n1 - this._max1);
/*  736 */     int i2hi = (i1lo <= i1hi) ? ArrayMath.max(n2 - this._max2, 0) : 0; int i2;
/*  737 */     for (i2 = n2 - 1; i2 >= i2hi; i2--) {
/*  738 */       for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  739 */         float yi = this._a0 * x[i2][i1];
/*  740 */         for (int j = 1; j < this._m; j++) {
/*  741 */           int k1 = i1 + this._lag1[j];
/*  742 */           int k2 = i2 + this._lag2[j];
/*  743 */           if (0 <= k1 && k1 < n1 && k2 < n2)
/*  744 */             yi += this._a[j] * x[k2][k1]; 
/*      */         } 
/*  746 */         y[i2][i1] = yi;
/*      */       } 
/*      */     } 
/*  749 */     for (i2 = i2hi - 1; i2 >= 0; i2--) {
/*  750 */       int i1; for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/*  751 */         float yi = this._a0 * x[i2][i1];
/*  752 */         for (int j = 1; j < this._m; j++) {
/*  753 */           int k1 = i1 + this._lag1[j];
/*  754 */           int k2 = i2 + this._lag2[j];
/*  755 */           if (k1 < n1)
/*  756 */             yi += this._a[j] * x[k2][k1]; 
/*      */         } 
/*  758 */         y[i2][i1] = yi;
/*      */       } 
/*  760 */       for (i1 = i1hi - 1; i1 >= i1lo; i1--) {
/*  761 */         float yi = this._a0 * x[i2][i1];
/*  762 */         for (int j = 1; j < this._m; j++) {
/*  763 */           int k1 = i1 + this._lag1[j];
/*  764 */           int k2 = i2 + this._lag2[j];
/*  765 */           yi += this._a[j] * x[k2][k1];
/*      */         } 
/*  767 */         y[i2][i1] = yi;
/*      */       } 
/*  769 */       for (i1 = i1lo - 1; i1 >= 0; i1--) {
/*  770 */         float yi = this._a0 * x[i2][i1];
/*  771 */         for (int j = 1; j < this._m; j++) {
/*  772 */           int k1 = i1 + this._lag1[j];
/*  773 */           int k2 = i2 + this._lag2[j];
/*  774 */           if (0 <= k1)
/*  775 */             yi += this._a[j] * x[k2][k1]; 
/*      */         } 
/*  777 */         y[i2][i1] = yi;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyTranspose(float[][][] x, float[][][] y) {
/*  789 */     int n1 = (y[0][0]).length;
/*  790 */     int n2 = (y[0]).length;
/*  791 */     int n3 = y.length;
/*  792 */     int i1lo = ArrayMath.max(0, -this._min1);
/*  793 */     int i1hi = ArrayMath.min(n1, n1 - this._max1);
/*  794 */     int i2lo = ArrayMath.max(0, -this._min2);
/*  795 */     int i2hi = ArrayMath.min(n2, n2 - this._max2);
/*  796 */     int i3hi = (i1lo <= i1hi && i2lo <= i2hi) ? ArrayMath.max(n3 - this._max3, 0) : 0; int i3;
/*  797 */     for (i3 = n3 - 1; i3 >= i3hi; i3--) {
/*  798 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/*  799 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  800 */           float yi = this._a0 * x[i3][i2][i1];
/*  801 */           for (int j = 1; j < this._m; j++) {
/*  802 */             int k1 = i1 + this._lag1[j];
/*  803 */             int k2 = i2 + this._lag2[j];
/*  804 */             int k3 = i3 + this._lag3[j];
/*  805 */             if (0 <= k1 && k1 < n1 && 0 <= k2 && k2 < n2 && k3 < n3)
/*  806 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  808 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*      */     } 
/*  812 */     for (i3 = i3hi - 1; i3 >= 0; i3--) {
/*  813 */       int i2; for (i2 = n2 - 1; i2 >= i2hi; i2--) {
/*  814 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  815 */           float yi = this._a0 * x[i3][i2][i1];
/*  816 */           for (int j = 1; j < this._m; j++) {
/*  817 */             int k1 = i1 + this._lag1[j];
/*  818 */             int k2 = i2 + this._lag2[j];
/*  819 */             int k3 = i3 + this._lag3[j];
/*  820 */             if (k2 < n2 && 0 <= k1 && k1 < n1)
/*  821 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  823 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*  826 */       for (i2 = i2hi - 1; i2 >= i2lo; i2--) {
/*  827 */         int i1; for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/*  828 */           float yi = this._a0 * x[i3][i2][i1];
/*  829 */           for (int j = 1; j < this._m; j++) {
/*  830 */             int k1 = i1 + this._lag1[j];
/*  831 */             int k2 = i2 + this._lag2[j];
/*  832 */             int k3 = i3 + this._lag3[j];
/*  833 */             if (k1 < n1)
/*  834 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  836 */           y[i3][i2][i1] = yi;
/*      */         } 
/*  838 */         for (i1 = i1hi - 1; i1 >= i1lo; i1--) {
/*  839 */           float yi = this._a0 * x[i3][i2][i1];
/*  840 */           for (int j = 1; j < this._m; j++) {
/*  841 */             int k1 = i1 + this._lag1[j];
/*  842 */             int k2 = i2 + this._lag2[j];
/*  843 */             int k3 = i3 + this._lag3[j];
/*  844 */             yi += this._a[j] * x[k3][k2][k1];
/*      */           } 
/*  846 */           y[i3][i2][i1] = yi;
/*      */         } 
/*  848 */         for (i1 = i1lo - 1; i1 >= 0; i1--) {
/*  849 */           float yi = this._a0 * x[i3][i2][i1];
/*  850 */           for (int j = 1; j < this._m; j++) {
/*  851 */             int k1 = i1 + this._lag1[j];
/*  852 */             int k2 = i2 + this._lag2[j];
/*  853 */             int k3 = i3 + this._lag3[j];
/*  854 */             if (0 <= k1)
/*  855 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  857 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*  860 */       for (i2 = i2lo - 1; i2 >= 0; i2--) {
/*  861 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  862 */           float yi = this._a0 * x[i3][i2][i1];
/*  863 */           for (int j = 1; j < this._m; j++) {
/*  864 */             int k1 = i1 + this._lag1[j];
/*  865 */             int k2 = i2 + this._lag2[j];
/*  866 */             int k3 = i3 + this._lag3[j];
/*  867 */             if (0 <= k2 && 0 <= k1 && k1 < n1)
/*  868 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  870 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
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
/*      */   public void applyTranspose(int[][] i, float[][] x, float[][] y) {
/*  884 */     int n1 = (y[0]).length;
/*  885 */     int n2 = y.length;
/*  886 */     int i1lo = ArrayMath.max(0, -this._min1);
/*  887 */     int i1hi = ArrayMath.min(n1, n1 - this._max1);
/*  888 */     int i2hi = (i1lo <= i1hi) ? ArrayMath.max(n2 - this._max2, 0) : 0; int i2;
/*  889 */     for (i2 = n2 - 1; i2 >= i2hi; i2--) {
/*  890 */       for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  891 */         int ii = i[i2][i1];
/*  892 */         float[] aii = this._ai[ii];
/*  893 */         float yi = this._ai0[ii] * x[i2][i1];
/*  894 */         for (int j = 1; j < this._m; j++) {
/*  895 */           int k1 = i1 + this._lag1[j];
/*  896 */           int k2 = i2 + this._lag2[j];
/*  897 */           if (0 <= k1 && k1 < n1 && k2 < n2)
/*  898 */             yi += aii[j] * x[k2][k1]; 
/*      */         } 
/*  900 */         y[i2][i1] = yi;
/*      */       } 
/*      */     } 
/*  903 */     for (i2 = i2hi - 1; i2 >= 0; i2--) {
/*  904 */       int i1; for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/*  905 */         int ii = i[i2][i1];
/*  906 */         float[] aii = this._ai[ii];
/*  907 */         float yi = this._ai0[ii] * x[i2][i1];
/*  908 */         for (int j = 1; j < this._m; j++) {
/*  909 */           int k1 = i1 + this._lag1[j];
/*  910 */           int k2 = i2 + this._lag2[j];
/*  911 */           if (k1 < n1)
/*  912 */             yi += aii[j] * x[k2][k1]; 
/*      */         } 
/*  914 */         y[i2][i1] = yi;
/*      */       } 
/*  916 */       for (i1 = i1hi - 1; i1 >= i1lo; i1--) {
/*  917 */         int ii = i[i2][i1];
/*  918 */         float[] aii = this._ai[ii];
/*  919 */         float yi = this._ai0[ii] * x[i2][i1];
/*  920 */         for (int j = 1; j < this._m; j++) {
/*  921 */           int k1 = i1 + this._lag1[j];
/*  922 */           int k2 = i2 + this._lag2[j];
/*  923 */           yi += aii[j] * x[k2][k1];
/*      */         } 
/*  925 */         y[i2][i1] = yi;
/*      */       } 
/*  927 */       for (i1 = i1lo - 1; i1 >= 0; i1--) {
/*  928 */         int ii = i[i2][i1];
/*  929 */         float[] aii = this._ai[ii];
/*  930 */         float yi = this._ai0[ii] * x[i2][i1];
/*  931 */         for (int j = 1; j < this._m; j++) {
/*  932 */           int k1 = i1 + this._lag1[j];
/*  933 */           int k2 = i2 + this._lag2[j];
/*  934 */           if (0 <= k1)
/*  935 */             yi += aii[j] * x[k2][k1]; 
/*      */         } 
/*  937 */         y[i2][i1] = yi;
/*      */       } 
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
/*      */   public void applyInverse(float[] x, float[] y) {
/*  952 */     int n1 = y.length;
/*  953 */     int i1lo = ArrayMath.min(this._max1, n1); int i1;
/*  954 */     for (i1 = 0; i1 < i1lo; i1++) {
/*  955 */       float yi = x[i1];
/*  956 */       for (int j = 1; j < this._m; j++) {
/*  957 */         int k1 = i1 - this._lag1[j];
/*  958 */         if (0 <= k1)
/*  959 */           yi -= this._a[j] * y[k1]; 
/*      */       } 
/*  961 */       y[i1] = this._a0i * yi;
/*      */     } 
/*  963 */     for (i1 = i1lo; i1 < n1; i1++) {
/*  964 */       float yi = x[i1];
/*  965 */       for (int j = 1; j < this._m; j++) {
/*  966 */         int k1 = i1 - this._lag1[j];
/*  967 */         yi -= this._a[j] * y[k1];
/*      */       } 
/*  969 */       y[i1] = this._a0i * yi;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyInverse(float[][] x, float[][] y) {
/*  980 */     int n1 = (y[0]).length;
/*  981 */     int n2 = y.length;
/*  982 */     int i1lo = ArrayMath.max(0, this._max1);
/*  983 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  984 */     int i2lo = (i1lo <= i1hi) ? ArrayMath.min(this._max2, n2) : n2; int i2;
/*  985 */     for (i2 = 0; i2 < i2lo; i2++) {
/*  986 */       for (int i1 = 0; i1 < n1; i1++) {
/*  987 */         float yi = x[i2][i1];
/*  988 */         for (int j = 1; j < this._m; j++) {
/*  989 */           int k1 = i1 - this._lag1[j];
/*  990 */           int k2 = i2 - this._lag2[j];
/*  991 */           if (0 <= k1 && k1 < n1 && 0 <= k2)
/*  992 */             yi -= this._a[j] * y[k2][k1]; 
/*      */         } 
/*  994 */         y[i2][i1] = this._a0i * yi;
/*      */       } 
/*      */     } 
/*  997 */     for (i2 = i2lo; i2 < n2; i2++) {
/*  998 */       int i1; for (i1 = 0; i1 < i1lo; i1++) {
/*  999 */         float yi = x[i2][i1];
/* 1000 */         for (int j = 1; j < this._m; j++) {
/* 1001 */           int k1 = i1 - this._lag1[j];
/* 1002 */           int k2 = i2 - this._lag2[j];
/* 1003 */           if (0 <= k1)
/* 1004 */             yi -= this._a[j] * y[k2][k1]; 
/*      */         } 
/* 1006 */         y[i2][i1] = this._a0i * yi;
/*      */       } 
/* 1008 */       for (i1 = i1lo; i1 < i1hi; i1++) {
/* 1009 */         float yi = x[i2][i1];
/* 1010 */         for (int j = 1; j < this._m; j++) {
/* 1011 */           int k1 = i1 - this._lag1[j];
/* 1012 */           int k2 = i2 - this._lag2[j];
/* 1013 */           yi -= this._a[j] * y[k2][k1];
/*      */         } 
/* 1015 */         y[i2][i1] = this._a0i * yi;
/*      */       } 
/* 1017 */       for (i1 = i1hi; i1 < n1; i1++) {
/* 1018 */         float yi = x[i2][i1];
/* 1019 */         for (int j = 1; j < this._m; j++) {
/* 1020 */           int k1 = i1 - this._lag1[j];
/* 1021 */           int k2 = i2 - this._lag2[j];
/* 1022 */           if (k1 < n1)
/* 1023 */             yi -= this._a[j] * y[k2][k1]; 
/*      */         } 
/* 1025 */         y[i2][i1] = this._a0i * yi;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyInverse(float[][][] x, float[][][] y) {
/* 1037 */     int n1 = (y[0][0]).length;
/* 1038 */     int n2 = (y[0]).length;
/* 1039 */     int n3 = y.length;
/* 1040 */     int i1lo = ArrayMath.max(0, this._max1);
/* 1041 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/* 1042 */     int i2lo = ArrayMath.max(0, this._max2);
/* 1043 */     int i2hi = ArrayMath.min(n2, n2 + this._min2);
/* 1044 */     int i3lo = (i1lo <= i1hi && i2lo <= i2hi) ? ArrayMath.min(this._max3, n3) : n3; int i3;
/* 1045 */     for (i3 = 0; i3 < i3lo; i3++) {
/* 1046 */       for (int i2 = 0; i2 < n2; i2++) {
/* 1047 */         for (int i1 = 0; i1 < n1; i1++) {
/* 1048 */           float yi = x[i3][i2][i1];
/* 1049 */           for (int j = 1; j < this._m; j++) {
/* 1050 */             int k1 = i1 - this._lag1[j];
/* 1051 */             int k2 = i2 - this._lag2[j];
/* 1052 */             int k3 = i3 - this._lag3[j];
/* 1053 */             if (0 <= k1 && k1 < n1 && 0 <= k2 && k2 < n2 && 0 <= k3)
/* 1054 */               yi -= this._a[j] * y[k3][k2][k1]; 
/*      */           } 
/* 1056 */           y[i3][i2][i1] = this._a0i * yi;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1060 */     for (i3 = i3lo; i3 < n3; i3++) {
/* 1061 */       int i2; for (i2 = 0; i2 < i2lo; i2++) {
/* 1062 */         for (int i1 = 0; i1 < n1; i1++) {
/* 1063 */           float yi = x[i3][i2][i1];
/* 1064 */           for (int j = 1; j < this._m; j++) {
/* 1065 */             int k1 = i1 - this._lag1[j];
/* 1066 */             int k2 = i2 - this._lag2[j];
/* 1067 */             int k3 = i3 - this._lag3[j];
/* 1068 */             if (0 <= k2 && 0 <= k1 && k1 < n1)
/* 1069 */               yi -= this._a[j] * y[k3][k2][k1]; 
/*      */           } 
/* 1071 */           y[i3][i2][i1] = this._a0i * yi;
/*      */         } 
/*      */       } 
/* 1074 */       for (i2 = i2lo; i2 < i2hi; i2++) {
/* 1075 */         int i1; for (i1 = 0; i1 < i1lo; i1++) {
/* 1076 */           float yi = x[i3][i2][i1];
/* 1077 */           for (int j = 1; j < this._m; j++) {
/* 1078 */             int k1 = i1 - this._lag1[j];
/* 1079 */             int k2 = i2 - this._lag2[j];
/* 1080 */             int k3 = i3 - this._lag3[j];
/* 1081 */             if (0 <= k1)
/* 1082 */               yi -= this._a[j] * y[k3][k2][k1]; 
/*      */           } 
/* 1084 */           y[i3][i2][i1] = this._a0i * yi;
/*      */         } 
/* 1086 */         for (i1 = i1lo; i1 < i1hi; i1++) {
/* 1087 */           float yi = x[i3][i2][i1];
/* 1088 */           for (int j = 1; j < this._m; j++) {
/* 1089 */             int k1 = i1 - this._lag1[j];
/* 1090 */             int k2 = i2 - this._lag2[j];
/* 1091 */             int k3 = i3 - this._lag3[j];
/* 1092 */             yi -= this._a[j] * y[k3][k2][k1];
/*      */           } 
/* 1094 */           y[i3][i2][i1] = this._a0i * yi;
/*      */         } 
/* 1096 */         for (i1 = i1hi; i1 < n1; i1++) {
/* 1097 */           float yi = x[i3][i2][i1];
/* 1098 */           for (int j = 1; j < this._m; j++) {
/* 1099 */             int k1 = i1 - this._lag1[j];
/* 1100 */             int k2 = i2 - this._lag2[j];
/* 1101 */             int k3 = i3 - this._lag3[j];
/* 1102 */             if (k1 < n1)
/* 1103 */               yi -= this._a[j] * y[k3][k2][k1]; 
/*      */           } 
/* 1105 */           y[i3][i2][i1] = this._a0i * yi;
/*      */         } 
/*      */       } 
/* 1108 */       for (i2 = i2hi; i2 < n2; i2++) {
/* 1109 */         for (int i1 = 0; i1 < n1; i1++) {
/* 1110 */           float yi = x[i3][i2][i1];
/* 1111 */           for (int j = 1; j < this._m; j++) {
/* 1112 */             int k1 = i1 - this._lag1[j];
/* 1113 */             int k2 = i2 - this._lag2[j];
/* 1114 */             int k3 = i3 - this._lag3[j];
/* 1115 */             if (k2 < n2 && 0 <= k1 && k1 < n1)
/* 1116 */               yi -= this._a[j] * y[k3][k2][k1]; 
/*      */           } 
/* 1118 */           y[i3][i2][i1] = this._a0i * yi;
/*      */         } 
/*      */       } 
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
/*      */   public void applyInverse(int[][] i, float[][] x, float[][] y) {
/* 1132 */     int n1 = (y[0]).length;
/* 1133 */     int n2 = y.length;
/* 1134 */     int i1lo = ArrayMath.max(0, this._max1);
/* 1135 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/* 1136 */     int i2lo = (i1lo <= i1hi) ? ArrayMath.min(this._max2, n2) : n2; int i2;
/* 1137 */     for (i2 = 0; i2 < i2lo; i2++) {
/* 1138 */       for (int i1 = 0; i1 < n1; i1++) {
/* 1139 */         int ii = i[i2][i1];
/* 1140 */         float[] aii = this._ai[ii];
/* 1141 */         float yi = x[i2][i1];
/* 1142 */         for (int j = 1; j < this._m; j++) {
/* 1143 */           int k1 = i1 - this._lag1[j];
/* 1144 */           int k2 = i2 - this._lag2[j];
/* 1145 */           if (0 <= k1 && k1 < n1 && 0 <= k2)
/* 1146 */             yi -= aii[j] * y[k2][k1]; 
/*      */         } 
/* 1148 */         y[i2][i1] = this._ai0i[ii] * yi;
/*      */       } 
/*      */     } 
/* 1151 */     for (i2 = i2lo; i2 < n2; i2++) {
/* 1152 */       int i1; for (i1 = 0; i1 < i1lo; i1++) {
/* 1153 */         int ii = i[i2][i1];
/* 1154 */         float[] aii = this._ai[ii];
/* 1155 */         float yi = x[i2][i1];
/* 1156 */         for (int j = 1; j < this._m; j++) {
/* 1157 */           int k1 = i1 - this._lag1[j];
/* 1158 */           int k2 = i2 - this._lag2[j];
/* 1159 */           if (0 <= k1)
/* 1160 */             yi -= aii[j] * y[k2][k1]; 
/*      */         } 
/* 1162 */         y[i2][i1] = this._ai0i[ii] * yi;
/*      */       } 
/* 1164 */       for (i1 = i1lo; i1 < i1hi; i1++) {
/* 1165 */         int ii = i[i2][i1];
/* 1166 */         float[] aii = this._ai[ii];
/* 1167 */         float yi = x[i2][i1];
/* 1168 */         for (int j = 1; j < this._m; j++) {
/* 1169 */           int k1 = i1 - this._lag1[j];
/* 1170 */           int k2 = i2 - this._lag2[j];
/* 1171 */           yi -= aii[j] * y[k2][k1];
/*      */         } 
/* 1173 */         y[i2][i1] = this._ai0i[ii] * yi;
/*      */       } 
/* 1175 */       for (i1 = i1hi; i1 < n1; i1++) {
/* 1176 */         int ii = i[i2][i1];
/* 1177 */         float[] aii = this._ai[ii];
/* 1178 */         float yi = x[i2][i1];
/* 1179 */         for (int j = 1; j < this._m; j++) {
/* 1180 */           int k1 = i1 - this._lag1[j];
/* 1181 */           int k2 = i2 - this._lag2[j];
/* 1182 */           if (k1 < n1)
/* 1183 */             yi -= aii[j] * y[k2][k1]; 
/*      */         } 
/* 1185 */         y[i2][i1] = this._ai0i[ii] * yi;
/*      */       } 
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
/*      */   public void applyInverseTranspose(float[] x, float[] y) {
/* 1200 */     int n1 = y.length;
/* 1201 */     int i1hi = ArrayMath.max(n1 - this._max1, 0); int i1;
/* 1202 */     for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/* 1203 */       float yi = x[i1];
/* 1204 */       for (int j = 1; j < this._m; j++) {
/* 1205 */         int k1 = i1 + this._lag1[j];
/* 1206 */         if (k1 < n1)
/* 1207 */           yi -= this._a[j] * y[k1]; 
/*      */       } 
/* 1209 */       y[i1] = this._a0i * yi;
/*      */     } 
/* 1211 */     for (i1 = i1hi - 1; i1 >= 0; i1--) {
/* 1212 */       float yi = x[i1];
/* 1213 */       for (int j = 1; j < this._m; j++) {
/* 1214 */         int k1 = i1 + this._lag1[j];
/* 1215 */         yi -= this._a[j] * y[k1];
/*      */       } 
/* 1217 */       y[i1] = this._a0i * yi;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyInverseTranspose(float[][] x, float[][] y) {
/* 1228 */     int n1 = (y[0]).length;
/* 1229 */     int n2 = y.length;
/* 1230 */     int i1lo = ArrayMath.max(0, -this._min1);
/* 1231 */     int i1hi = ArrayMath.min(n1, n1 - this._max1);
/* 1232 */     int i2hi = (i1lo <= i1hi) ? ArrayMath.max(n2 - this._max2, 0) : 0; int i2;
/* 1233 */     for (i2 = n2 - 1; i2 >= i2hi; i2--) {
/* 1234 */       for (int i1 = n1 - 1; i1 >= 0; i1--) {
/* 1235 */         float yi = x[i2][i1];
/* 1236 */         for (int j = 1; j < this._m; j++) {
/* 1237 */           int k1 = i1 + this._lag1[j];
/* 1238 */           int k2 = i2 + this._lag2[j];
/* 1239 */           if (0 <= k1 && k1 < n1 && k2 < n2)
/* 1240 */             yi -= this._a[j] * y[k2][k1]; 
/*      */         } 
/* 1242 */         y[i2][i1] = this._a0i * yi;
/*      */       } 
/*      */     } 
/* 1245 */     for (i2 = i2hi - 1; i2 >= 0; i2--) {
/* 1246 */       int i1; for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/* 1247 */         float yi = x[i2][i1];
/* 1248 */         for (int j = 1; j < this._m; j++) {
/* 1249 */           int k1 = i1 + this._lag1[j];
/* 1250 */           int k2 = i2 + this._lag2[j];
/* 1251 */           if (k1 < n1)
/* 1252 */             yi -= this._a[j] * y[k2][k1]; 
/*      */         } 
/* 1254 */         y[i2][i1] = this._a0i * yi;
/*      */       } 
/* 1256 */       for (i1 = i1hi - 1; i1 >= i1lo; i1--) {
/* 1257 */         float yi = x[i2][i1];
/* 1258 */         for (int j = 1; j < this._m; j++) {
/* 1259 */           int k1 = i1 + this._lag1[j];
/* 1260 */           int k2 = i2 + this._lag2[j];
/* 1261 */           yi -= this._a[j] * y[k2][k1];
/*      */         } 
/* 1263 */         y[i2][i1] = this._a0i * yi;
/*      */       } 
/* 1265 */       for (i1 = i1lo - 1; i1 >= 0; i1--) {
/* 1266 */         float yi = x[i2][i1];
/* 1267 */         for (int j = 1; j < this._m; j++) {
/* 1268 */           int k1 = i1 + this._lag1[j];
/* 1269 */           int k2 = i2 + this._lag2[j];
/* 1270 */           if (0 <= k1)
/* 1271 */             yi -= this._a[j] * y[k2][k1]; 
/*      */         } 
/* 1273 */         y[i2][i1] = this._a0i * yi;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyInverseTranspose(float[][][] x, float[][][] y) {
/* 1285 */     int n1 = (y[0][0]).length;
/* 1286 */     int n2 = (y[0]).length;
/* 1287 */     int n3 = y.length;
/* 1288 */     int i1lo = ArrayMath.max(0, -this._min1);
/* 1289 */     int i1hi = ArrayMath.min(n1, n1 - this._max1);
/* 1290 */     int i2lo = ArrayMath.max(0, -this._min2);
/* 1291 */     int i2hi = ArrayMath.min(n2, n2 - this._max2);
/* 1292 */     int i3hi = (i1lo <= i1hi && i2lo <= i2hi) ? ArrayMath.max(n3 - this._max3, 0) : 0; int i3;
/* 1293 */     for (i3 = n3 - 1; i3 >= i3hi; i3--) {
/* 1294 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/* 1295 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/* 1296 */           float yi = x[i3][i2][i1];
/* 1297 */           for (int j = 1; j < this._m; j++) {
/* 1298 */             int k1 = i1 + this._lag1[j];
/* 1299 */             int k2 = i2 + this._lag2[j];
/* 1300 */             int k3 = i3 + this._lag3[j];
/* 1301 */             if (0 <= k1 && k1 < n1 && 0 <= k2 && k2 < n2 && k3 < n3)
/* 1302 */               yi -= this._a[j] * y[k3][k2][k1]; 
/*      */           } 
/* 1304 */           y[i3][i2][i1] = this._a0i * yi;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1308 */     for (i3 = i3hi - 1; i3 >= 0; i3--) {
/* 1309 */       int i2; for (i2 = n2 - 1; i2 >= i2hi; i2--) {
/* 1310 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/* 1311 */           float yi = x[i3][i2][i1];
/* 1312 */           for (int j = 1; j < this._m; j++) {
/* 1313 */             int k1 = i1 + this._lag1[j];
/* 1314 */             int k2 = i2 + this._lag2[j];
/* 1315 */             int k3 = i3 + this._lag3[j];
/* 1316 */             if (k2 < n2 && 0 <= k1 && k1 < n1)
/* 1317 */               yi -= this._a[j] * y[k3][k2][k1]; 
/*      */           } 
/* 1319 */           y[i3][i2][i1] = this._a0i * yi;
/*      */         } 
/*      */       } 
/* 1322 */       for (i2 = i2hi - 1; i2 >= i2lo; i2--) {
/* 1323 */         int i1; for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/* 1324 */           float yi = x[i3][i2][i1];
/* 1325 */           for (int j = 1; j < this._m; j++) {
/* 1326 */             int k1 = i1 + this._lag1[j];
/* 1327 */             int k2 = i2 + this._lag2[j];
/* 1328 */             int k3 = i3 + this._lag3[j];
/* 1329 */             if (k1 < n1)
/* 1330 */               yi -= this._a[j] * y[k3][k2][k1]; 
/*      */           } 
/* 1332 */           y[i3][i2][i1] = this._a0i * yi;
/*      */         } 
/* 1334 */         for (i1 = i1hi - 1; i1 >= i1lo; i1--) {
/* 1335 */           float yi = x[i3][i2][i1];
/* 1336 */           for (int j = 1; j < this._m; j++) {
/* 1337 */             int k1 = i1 + this._lag1[j];
/* 1338 */             int k2 = i2 + this._lag2[j];
/* 1339 */             int k3 = i3 + this._lag3[j];
/* 1340 */             yi -= this._a[j] * y[k3][k2][k1];
/*      */           } 
/* 1342 */           y[i3][i2][i1] = this._a0i * yi;
/*      */         } 
/* 1344 */         for (i1 = i1lo - 1; i1 >= 0; i1--) {
/* 1345 */           float yi = x[i3][i2][i1];
/* 1346 */           for (int j = 1; j < this._m; j++) {
/* 1347 */             int k1 = i1 + this._lag1[j];
/* 1348 */             int k2 = i2 + this._lag2[j];
/* 1349 */             int k3 = i3 + this._lag3[j];
/* 1350 */             if (0 <= k1)
/* 1351 */               yi -= this._a[j] * y[k3][k2][k1]; 
/*      */           } 
/* 1353 */           y[i3][i2][i1] = this._a0i * yi;
/*      */         } 
/*      */       } 
/* 1356 */       for (i2 = i2lo - 1; i2 >= 0; i2--) {
/* 1357 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/* 1358 */           float yi = x[i3][i2][i1];
/* 1359 */           for (int j = 1; j < this._m; j++) {
/* 1360 */             int k1 = i1 + this._lag1[j];
/* 1361 */             int k2 = i2 + this._lag2[j];
/* 1362 */             int k3 = i3 + this._lag3[j];
/* 1363 */             if (0 <= k2 && 0 <= k1 && k1 < n1)
/* 1364 */               yi -= this._a[j] * y[k3][k2][k1]; 
/*      */           } 
/* 1366 */           y[i3][i2][i1] = this._a0i * yi;
/*      */         } 
/*      */       } 
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
/*      */   public void applyInverseTranspose(int[][] i, float[][] x, float[][] y) {
/* 1380 */     int n1 = (y[0]).length;
/* 1381 */     int n2 = y.length;
/* 1382 */     int i1lo = ArrayMath.max(0, -this._min1);
/* 1383 */     int i1hi = ArrayMath.min(n1, n1 - this._max1);
/* 1384 */     int i2hi = (i1lo <= i1hi) ? ArrayMath.max(n2 - this._max2, 0) : 0; int i2;
/* 1385 */     for (i2 = n2 - 1; i2 >= i2hi; i2--) {
/* 1386 */       for (int i1 = n1 - 1; i1 >= 0; i1--) {
/* 1387 */         int ii = i[i2][i1];
/* 1388 */         float[] aii = this._ai[ii];
/* 1389 */         float yi = x[i2][i1];
/* 1390 */         for (int j = 1; j < this._m; j++) {
/* 1391 */           int k1 = i1 + this._lag1[j];
/* 1392 */           int k2 = i2 + this._lag2[j];
/* 1393 */           if (0 <= k1 && k1 < n1 && k2 < n2)
/* 1394 */             yi -= aii[j] * y[k2][k1]; 
/*      */         } 
/* 1396 */         y[i2][i1] = this._ai0i[ii] * yi;
/*      */       } 
/*      */     } 
/* 1399 */     for (i2 = i2hi - 1; i2 >= 0; i2--) {
/* 1400 */       int i1; for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/* 1401 */         int ii = i[i2][i1];
/* 1402 */         float[] aii = this._ai[ii];
/* 1403 */         float yi = x[i2][i1];
/* 1404 */         for (int j = 1; j < this._m; j++) {
/* 1405 */           int k1 = i1 + this._lag1[j];
/* 1406 */           int k2 = i2 + this._lag2[j];
/* 1407 */           if (k1 < n1)
/* 1408 */             yi -= aii[j] * y[k2][k1]; 
/*      */         } 
/* 1410 */         y[i2][i1] = this._ai0i[ii] * yi;
/*      */       } 
/* 1412 */       for (i1 = i1hi - 1; i1 >= i1lo; i1--) {
/* 1413 */         int ii = i[i2][i1];
/* 1414 */         float[] aii = this._ai[ii];
/* 1415 */         float yi = x[i2][i1];
/* 1416 */         for (int j = 1; j < this._m; j++) {
/* 1417 */           int k1 = i1 + this._lag1[j];
/* 1418 */           int k2 = i2 + this._lag2[j];
/* 1419 */           yi -= aii[j] * y[k2][k1];
/*      */         } 
/* 1421 */         y[i2][i1] = this._ai0i[ii] * yi;
/*      */       } 
/* 1423 */       for (i1 = i1lo - 1; i1 >= 0; i1--) {
/* 1424 */         int ii = i[i2][i1];
/* 1425 */         float[] aii = this._ai[ii];
/* 1426 */         float yi = x[i2][i1];
/* 1427 */         for (int j = 1; j < this._m; j++) {
/* 1428 */           int k1 = i1 + this._lag1[j];
/* 1429 */           int k2 = i2 + this._lag2[j];
/* 1430 */           if (0 <= k1)
/* 1431 */             yi -= aii[j] * y[k2][k1]; 
/*      */         } 
/* 1433 */         y[i2][i1] = this._ai0i[ii] * yi;
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static float[] impulse(int nlag) {
/* 1454 */     float[] a = new float[nlag];
/* 1455 */     a[0] = 1.0F;
/* 1456 */     return a;
/*      */   }
/*      */   
/*      */   private void initLags(int[] lag1, float[] a) {
/* 1460 */     Check.argument((lag1.length > 0), "lag1.length>0");
/* 1461 */     Check.argument((lag1.length == a.length), "lag1.length==a.length");
/* 1462 */     Check.argument((lag1[0] == 0), "lag1[0]==0");
/* 1463 */     for (int j = 1; j < a.length; j++)
/* 1464 */       Check.argument((lag1[j] > 0), "lag1[" + j + "]>0"); 
/* 1465 */     this._m = lag1.length;
/* 1466 */     this._lag1 = ArrayMath.copy(lag1);
/* 1467 */     this._lag2 = ArrayMath.zeroint(this._m);
/* 1468 */     this._lag3 = ArrayMath.zeroint(this._m);
/* 1469 */     this._min1 = ArrayMath.min(lag1);
/* 1470 */     this._max1 = ArrayMath.max(lag1);
/*      */   }
/*      */   
/*      */   private void initLags(int[] lag1, int[] lag2, float[] a) {
/* 1474 */     Check.argument((lag1.length > 0), "lag1.length>0");
/* 1475 */     Check.argument((lag1.length == a.length), "lag1.length==a.length");
/* 1476 */     Check.argument((lag2.length == a.length), "lag2.length==a.length");
/* 1477 */     Check.argument((lag1[0] == 0), "lag1[0]==0");
/* 1478 */     Check.argument((lag2[0] == 0), "lag2[0]==0");
/* 1479 */     for (int j = 1; j < a.length; j++) {
/* 1480 */       Check.argument((lag2[j] >= 0), "lag2[" + j + "]>=0");
/* 1481 */       if (lag2[j] == 0)
/* 1482 */         Check.argument((lag1[j] > 0), "if lag2==0, lag1[" + j + "]>0"); 
/*      */     } 
/* 1484 */     this._m = lag1.length;
/* 1485 */     this._lag1 = ArrayMath.copy(lag1);
/* 1486 */     this._lag2 = ArrayMath.copy(lag2);
/* 1487 */     this._lag3 = ArrayMath.zeroint(this._m);
/* 1488 */     this._min1 = ArrayMath.min(lag1);
/* 1489 */     this._min2 = ArrayMath.min(lag2);
/* 1490 */     this._max1 = ArrayMath.max(lag1);
/* 1491 */     this._max2 = ArrayMath.max(lag2);
/*      */   }
/*      */   
/*      */   private void initLags(int[] lag1, int[] lag2, int[] lag3, float[] a) {
/* 1495 */     Check.argument((lag1.length > 0), "lag1.length>0");
/* 1496 */     Check.argument((lag1.length == a.length), "lag1.length==a.length");
/* 1497 */     Check.argument((lag2.length == a.length), "lag2.length==a.length");
/* 1498 */     Check.argument((lag3.length == a.length), "lag3.length==a.length");
/* 1499 */     Check.argument((lag1[0] == 0), "lag1[0]==0");
/* 1500 */     Check.argument((lag2[0] == 0), "lag2[0]==0");
/* 1501 */     Check.argument((lag3[0] == 0), "lag3[0]==0");
/* 1502 */     for (int j = 1; j < a.length; j++) {
/* 1503 */       Check.argument((lag3[j] >= 0), "lag3[" + j + "]>=0");
/* 1504 */       if (lag3[j] == 0) {
/* 1505 */         Check.argument((lag2[j] >= 0), "if lag3==0, lag2[" + j + "]>=0");
/* 1506 */         if (lag2[j] == 0)
/* 1507 */           Check.argument((lag1[j] > 0), "if lag3==0 && lag2==0, lag1[" + j + "]>0"); 
/*      */       } 
/*      */     } 
/* 1510 */     this._m = a.length;
/* 1511 */     this._lag1 = ArrayMath.copy(lag1);
/* 1512 */     this._lag2 = ArrayMath.copy(lag2);
/* 1513 */     this._lag3 = ArrayMath.copy(lag3);
/* 1514 */     this._min1 = ArrayMath.min(lag1);
/* 1515 */     this._min2 = ArrayMath.min(lag2);
/* 1516 */     this._min3 = ArrayMath.min(lag3);
/* 1517 */     this._max1 = ArrayMath.max(lag1);
/* 1518 */     this._max2 = ArrayMath.max(lag2);
/* 1519 */     this._max3 = ArrayMath.max(lag3);
/*      */   }
/*      */   
/*      */   private void initA(float[] a) {
/* 1523 */     this._a = ArrayMath.copy(a);
/* 1524 */     this._a0 = a[0];
/* 1525 */     this._a0i = 1.0F / a[0];
/*      */   }
/*      */   
/*      */   private void initA(float[][] a) {
/* 1529 */     Check.argument(ArrayMath.isRegular(a), "a is regular");
/* 1530 */     initA(a[0]);
/* 1531 */     int ni = a.length;
/* 1532 */     this._ai = ArrayMath.copy(a);
/* 1533 */     this._ai0 = new float[ni];
/* 1534 */     this._ai0i = new float[ni];
/* 1535 */     for (int ii = 0; ii < ni; ii++) {
/* 1536 */       this._ai0[ii] = a[ii][0];
/* 1537 */       this._ai0i[ii] = 1.0F / a[ii][0];
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/MinimumPhaseFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */