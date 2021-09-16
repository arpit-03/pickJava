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
/*      */ public class CausalFilter
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
/*      */   
/*      */   public CausalFilter(int[] lag1) {
/*   55 */     this(lag1, impulse(lag1.length));
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
/*      */   public CausalFilter(int[] lag1, int[] lag2) {
/*   69 */     this(lag1, lag2, impulse(lag1.length));
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
/*      */   public CausalFilter(int[] lag1, int[] lag2, int[] lag3) {
/*   84 */     this(lag1, lag2, lag3, impulse(lag1.length));
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
/*      */   public CausalFilter(int[] lag1, float[] a) {
/*   97 */     initLags(lag1, a);
/*   98 */     initA(a);
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
/*      */   public CausalFilter(int[] lag1, int[] lag2, float[] a) {
/*  113 */     initLags(lag1, lag2, a);
/*  114 */     initA(a);
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
/*      */   public CausalFilter(int[] lag1, int[] lag2, int[] lag3, float[] a) {
/*  130 */     initLags(lag1, lag2, lag3, a);
/*  131 */     initA(a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getLag1() {
/*  139 */     return ArrayMath.copy(this._lag1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getLag2() {
/*  147 */     return ArrayMath.copy(this._lag2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getLag3() {
/*  155 */     return ArrayMath.copy(this._lag3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] getA() {
/*  163 */     return ArrayMath.copy(this._a);
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
/*  182 */     Check.argument((r.length % 2 == 1), "r.length is odd");
/*      */ 
/*      */     
/*  185 */     int m1 = this._max1 - this._min1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  192 */     int n1 = r.length + 10 * m1;
/*      */ 
/*      */     
/*  195 */     int l1 = (r.length - 1) / 2;
/*  196 */     int k1 = n1 - 1 - this._max1;
/*      */ 
/*      */     
/*  199 */     float[] s = new float[n1];
/*  200 */     float[] t = new float[n1];
/*  201 */     float[] u = new float[n1];
/*      */ 
/*      */     
/*  204 */     ArrayMath.copy(l1 + 1 + l1, 0, r, k1 - l1, s);
/*      */ 
/*      */     
/*  207 */     ArrayMath.zero(this._a);
/*  208 */     this._a[0] = ArrayMath.sqrt(s[k1]);
/*  209 */     this._a0 = this._a[0];
/*  210 */     this._a0i = 1.0F / this._a[0];
/*      */ 
/*      */ 
/*      */     
/*  214 */     boolean converged = false;
/*  215 */     float eemax = s[k1] * epsilon;
/*  216 */     for (int niter = 0; niter < maxiter && !converged; niter++) {
/*      */ 
/*      */ 
/*      */       
/*  220 */       applyInverseTranspose(s, t);
/*  221 */       applyInverse(t, u);
/*  222 */       u[k1] = u[k1] + 1.0F;
/*      */ 
/*      */       
/*  225 */       u[k1] = u[k1] * 0.5F;
/*  226 */       for (int i1 = 0; i1 < k1; i1++) {
/*  227 */         u[i1] = 0.0F;
/*      */       }
/*      */       
/*  230 */       apply(u, t);
/*  231 */       converged = true;
/*  232 */       for (int j = 0; j < this._m; j++) {
/*  233 */         int j1 = k1 + this._lag1[j];
/*  234 */         if (0 <= j1 && j1 < n1) {
/*  235 */           float aj = t[j1];
/*  236 */           if (converged) {
/*  237 */             float e = this._a[j] - aj;
/*  238 */             converged = (e * e <= eemax);
/*      */           } 
/*  240 */           this._a[j] = aj;
/*      */         } 
/*      */       } 
/*  243 */       this._a0 = this._a[0];
/*  244 */       this._a0i = 1.0F / this._a[0];
/*      */     } 
/*  246 */     Check.state(converged, "Wilson-Burg iterations converged");
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
/*  265 */     Check.argument(((r[0]).length % 2 == 1), "r[0].length is odd");
/*  266 */     Check.argument((r.length % 2 == 1), "r.length is odd");
/*      */ 
/*      */     
/*  269 */     int m1 = this._max1 - this._min1;
/*  270 */     int m2 = this._max2 - this._min2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  277 */     int n1 = (r[0]).length + 10 * m1;
/*  278 */     int n2 = r.length + 10 * m2;
/*      */ 
/*      */     
/*  281 */     int l1 = ((r[0]).length - 1) / 2;
/*  282 */     int l2 = (r.length - 1) / 2;
/*  283 */     int k1 = n1 - 1 - this._max1;
/*  284 */     int k2 = n2 - 1 - this._max2;
/*      */ 
/*      */     
/*  287 */     float[][] s = new float[n2][n1];
/*  288 */     float[][] t = new float[n2][n1];
/*  289 */     float[][] u = new float[n2][n1];
/*      */ 
/*      */     
/*  292 */     ArrayMath.copy(l1 + 1 + l1, l2 + 1 + l2, 0, 0, r, k1 - l1, k2 - l2, s);
/*      */ 
/*      */     
/*  295 */     ArrayMath.zero(this._a);
/*  296 */     this._a[0] = ArrayMath.sqrt(s[k2][k1]);
/*  297 */     this._a0 = this._a[0];
/*  298 */     this._a0i = 1.0F / this._a[0];
/*      */ 
/*      */ 
/*      */     
/*  302 */     boolean converged = false;
/*  303 */     float eemax = s[k2][k1] * epsilon;
/*  304 */     for (int niter = 0; niter < maxiter && !converged; niter++) {
/*      */ 
/*      */ 
/*      */       
/*  308 */       applyInverseTranspose(s, t);
/*  309 */       applyInverse(t, u);
/*  310 */       u[k2][k1] = u[k2][k1] + 1.0F;
/*      */ 
/*      */       
/*  313 */       u[k2][k1] = u[k2][k1] * 0.5F;
/*  314 */       for (int i2 = 0; i2 < k2; i2++) {
/*  315 */         for (int i = 0; i < n1; i++)
/*  316 */           u[i2][i] = 0.0F; 
/*  317 */       }  for (int i1 = 0; i1 < k1; i1++) {
/*  318 */         u[k2][i1] = 0.0F;
/*      */       }
/*      */       
/*  321 */       apply(u, t);
/*  322 */       converged = true;
/*  323 */       for (int j = 0; j < this._m; j++) {
/*  324 */         int j1 = k1 + this._lag1[j];
/*  325 */         int j2 = k2 + this._lag2[j];
/*  326 */         if (0 <= j1 && j1 < n1 && 0 <= j2 && j2 < n2) {
/*  327 */           float aj = t[j2][j1];
/*  328 */           if (converged) {
/*  329 */             float e = this._a[j] - aj;
/*  330 */             converged = (e * e <= eemax);
/*      */           } 
/*  332 */           this._a[j] = aj;
/*      */         } 
/*      */       } 
/*  335 */       this._a0 = this._a[0];
/*  336 */       this._a0i = 1.0F / this._a[0];
/*      */     } 
/*  338 */     Check.state(converged, "Wilson-Burg iterations converged");
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
/*  357 */     Check.argument(((r[0][0]).length % 2 == 1), "r[0][0].length is odd");
/*  358 */     Check.argument(((r[0]).length % 2 == 1), "r[0].length is odd");
/*  359 */     Check.argument((r.length % 2 == 1), "r.length is odd");
/*      */ 
/*      */     
/*  362 */     int m1 = this._max1 - this._min1;
/*  363 */     int m2 = this._max2 - this._min2;
/*  364 */     int m3 = this._max3 - this._min3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  371 */     int n1 = (r[0][0]).length + 10 * m1;
/*  372 */     int n2 = (r[0]).length + 10 * m2;
/*  373 */     int n3 = r.length + 10 * m3;
/*      */ 
/*      */     
/*  376 */     int l1 = ((r[0][0]).length - 1) / 2;
/*  377 */     int l2 = ((r[0]).length - 1) / 2;
/*  378 */     int l3 = (r.length - 1) / 2;
/*  379 */     int k1 = n1 - 1 - this._max1;
/*  380 */     int k2 = n2 - 1 - this._max2;
/*  381 */     int k3 = n3 - 1 - this._max3;
/*      */ 
/*      */     
/*  384 */     float[][][] s = new float[n3][n2][n1];
/*  385 */     float[][][] t = new float[n3][n2][n1];
/*  386 */     float[][][] u = new float[n3][n2][n1];
/*      */ 
/*      */     
/*  389 */     ArrayMath.copy(l1 + 1 + l1, l2 + 1 + l2, l3 + 1 + l3, 0, 0, 0, r, k1 - l1, k2 - l2, k3 - l3, s);
/*      */ 
/*      */     
/*  392 */     ArrayMath.zero(this._a);
/*  393 */     this._a[0] = ArrayMath.sqrt(s[k3][k2][k1]);
/*  394 */     this._a0 = this._a[0];
/*  395 */     this._a0i = 1.0F / this._a[0];
/*      */ 
/*      */ 
/*      */     
/*  399 */     boolean converged = false;
/*  400 */     float eemax = s[k3][k2][k1] * epsilon;
/*  401 */     for (int niter = 0; niter < maxiter && !converged; niter++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  407 */       applyInverseTranspose(s, t);
/*  408 */       applyInverse(t, u);
/*  409 */       u[k3][k2][k1] = u[k3][k2][k1] + 1.0F;
/*      */ 
/*      */       
/*  412 */       u[k3][k2][k1] = u[k3][k2][k1] * 0.5F;
/*  413 */       for (int i3 = 0; i3 < k3; i3++) {
/*  414 */         for (int i = 0; i < n2; i++)
/*  415 */         { for (int k = 0; k < n1; k++)
/*  416 */             u[i3][i][k] = 0.0F;  } 
/*  417 */       }  for (int i2 = 0; i2 < k2; i2++) {
/*  418 */         for (int i = 0; i < n1; i++)
/*  419 */           u[k3][i2][i] = 0.0F; 
/*  420 */       }  for (int i1 = 0; i1 < k1; i1++) {
/*  421 */         u[k3][k2][i1] = 0.0F;
/*      */       }
/*      */       
/*  424 */       apply(u, t);
/*  425 */       converged = true;
/*  426 */       for (int j = 0; j < this._m; j++) {
/*  427 */         int j1 = k1 + this._lag1[j];
/*  428 */         int j2 = k2 + this._lag2[j];
/*  429 */         int j3 = k3 + this._lag3[j];
/*  430 */         if (0 <= j1 && j1 < n1 && 0 <= j2 && j2 < n2 && 0 <= j3 && j3 < n3) {
/*  431 */           float aj = t[j3][j2][j1];
/*  432 */           if (converged) {
/*  433 */             float e = this._a[j] - aj;
/*  434 */             converged = (e * e <= eemax);
/*      */           } 
/*  436 */           this._a[j] = aj;
/*      */         } 
/*      */       } 
/*  439 */       this._a0 = this._a[0];
/*  440 */       this._a0i = 1.0F / this._a[0];
/*      */     } 
/*  442 */     Check.state(converged, "Wilson-Burg iterations converged");
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
/*      */   public void apply(float[] x, float[] y) {
/*  477 */     int n1 = x.length;
/*  478 */     int i1lo = ArrayMath.min(this._max1, n1); int i1;
/*  479 */     for (i1 = n1 - 1; i1 >= i1lo; i1--) {
/*  480 */       float yi = this._a0 * x[i1];
/*  481 */       for (int j = 1; j < this._m; j++) {
/*  482 */         int k1 = i1 - this._lag1[j];
/*  483 */         yi += this._a[j] * x[k1];
/*      */       } 
/*  485 */       y[i1] = yi;
/*      */     } 
/*  487 */     for (i1 = i1lo - 1; i1 >= 0; i1--) {
/*  488 */       float yi = this._a0 * x[i1];
/*  489 */       for (int j = 1; j < this._m; j++) {
/*  490 */         int k1 = i1 - this._lag1[j];
/*  491 */         if (0 <= k1)
/*  492 */           yi += this._a[j] * x[k1]; 
/*      */       } 
/*  494 */       y[i1] = yi;
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
/*      */   public void applyTranspose(float[] x, float[] y) {
/*  507 */     int n1 = x.length;
/*  508 */     int i1hi = ArrayMath.max(n1 - this._max1, 0); int i1;
/*  509 */     for (i1 = 0; i1 < i1hi; i1++) {
/*  510 */       float yi = this._a0 * x[i1];
/*  511 */       for (int j = 1; j < this._m; j++) {
/*  512 */         int k1 = i1 + this._lag1[j];
/*  513 */         yi += this._a[j] * x[k1];
/*      */       } 
/*  515 */       y[i1] = yi;
/*      */     } 
/*  517 */     for (i1 = i1hi; i1 < n1; i1++) {
/*  518 */       float yi = this._a0 * x[i1];
/*  519 */       for (int j = 1; j < this._m; j++) {
/*  520 */         int k1 = i1 + this._lag1[j];
/*  521 */         if (k1 < n1)
/*  522 */           yi += this._a[j] * x[k1]; 
/*      */       } 
/*  524 */       y[i1] = yi;
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
/*      */   public void applyInverse(float[] y, float[] x) {
/*  537 */     int n1 = y.length;
/*  538 */     int i1lo = ArrayMath.min(this._max1, n1); int i1;
/*  539 */     for (i1 = 0; i1 < i1lo; i1++) {
/*  540 */       float xi = y[i1];
/*  541 */       for (int j = 1; j < this._m; j++) {
/*  542 */         int k1 = i1 - this._lag1[j];
/*  543 */         if (0 <= k1)
/*  544 */           xi -= this._a[j] * x[k1]; 
/*      */       } 
/*  546 */       x[i1] = xi * this._a0i;
/*      */     } 
/*  548 */     for (i1 = i1lo; i1 < n1; i1++) {
/*  549 */       float xi = y[i1];
/*  550 */       for (int j = 1; j < this._m; j++) {
/*  551 */         int k1 = i1 - this._lag1[j];
/*  552 */         xi -= this._a[j] * x[k1];
/*      */       } 
/*  554 */       x[i1] = xi * this._a0i;
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
/*      */   public void applyInverseTranspose(float[] y, float[] x) {
/*  567 */     int n1 = y.length;
/*  568 */     int i1hi = ArrayMath.max(n1 - this._max1, 0); int i1;
/*  569 */     for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/*  570 */       float xi = y[i1];
/*  571 */       for (int j = 1; j < this._m; j++) {
/*  572 */         int k1 = i1 + this._lag1[j];
/*  573 */         if (k1 < n1)
/*  574 */           xi -= this._a[j] * x[k1]; 
/*      */       } 
/*  576 */       x[i1] = xi * this._a0i;
/*      */     } 
/*  578 */     for (i1 = i1hi - 1; i1 >= 0; i1--) {
/*  579 */       float xi = y[i1];
/*  580 */       for (int j = 1; j < this._m; j++) {
/*  581 */         int k1 = i1 + this._lag1[j];
/*  582 */         xi -= this._a[j] * x[k1];
/*      */       } 
/*  584 */       x[i1] = xi * this._a0i;
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
/*      */   public void apply(float[][] x, float[][] y) {
/*  600 */     int n1 = (x[0]).length;
/*  601 */     int n2 = x.length;
/*  602 */     int i1lo = ArrayMath.max(0, this._max1);
/*  603 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  604 */     int i2lo = (i1lo <= i1hi) ? ArrayMath.min(this._max2, n2) : n2; int i2;
/*  605 */     for (i2 = n2 - 1; i2 >= i2lo; i2--) {
/*  606 */       int i1; for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/*  607 */         float yi = this._a0 * x[i2][i1];
/*  608 */         for (int j = 1; j < this._m; j++) {
/*  609 */           int k1 = i1 - this._lag1[j];
/*  610 */           int k2 = i2 - this._lag2[j];
/*  611 */           if (k1 < n1)
/*  612 */             yi += this._a[j] * x[k2][k1]; 
/*      */         } 
/*  614 */         y[i2][i1] = yi;
/*      */       } 
/*  616 */       for (i1 = i1hi - 1; i1 >= i1lo; i1--) {
/*  617 */         float yi = this._a0 * x[i2][i1];
/*  618 */         for (int j = 1; j < this._m; j++) {
/*  619 */           int k1 = i1 - this._lag1[j];
/*  620 */           int k2 = i2 - this._lag2[j];
/*  621 */           yi += this._a[j] * x[k2][k1];
/*      */         } 
/*  623 */         y[i2][i1] = yi;
/*      */       } 
/*  625 */       for (i1 = i1lo - 1; i1 >= 0; i1--) {
/*  626 */         float yi = this._a0 * x[i2][i1];
/*  627 */         for (int j = 1; j < this._m; j++) {
/*  628 */           int k1 = i1 - this._lag1[j];
/*  629 */           int k2 = i2 - this._lag2[j];
/*  630 */           if (0 <= k1)
/*  631 */             yi += this._a[j] * x[k2][k1]; 
/*      */         } 
/*  633 */         y[i2][i1] = yi;
/*      */       } 
/*      */     } 
/*  636 */     for (i2 = i2lo - 1; i2 >= 0; i2--) {
/*  637 */       for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  638 */         float yi = this._a0 * x[i2][i1];
/*  639 */         for (int j = 1; j < this._m; j++) {
/*  640 */           int k1 = i1 - this._lag1[j];
/*  641 */           int k2 = i2 - this._lag2[j];
/*  642 */           if (0 <= k1 && k1 < n1 && 0 <= k2)
/*  643 */             yi += this._a[j] * x[k2][k1]; 
/*      */         } 
/*  645 */         y[i2][i1] = yi;
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
/*      */   public void applyTranspose(float[][] x, float[][] y) {
/*  659 */     int n1 = (x[0]).length;
/*  660 */     int n2 = x.length;
/*  661 */     int i1lo = ArrayMath.max(0, -this._min1);
/*  662 */     int i1hi = ArrayMath.min(n1, n1 - this._max1);
/*  663 */     int i2hi = (i1lo <= i1hi) ? ArrayMath.max(n2 - this._max2, 0) : 0; int i2;
/*  664 */     for (i2 = 0; i2 < i2hi; i2++) {
/*  665 */       int i1; for (i1 = 0; i1 < i1lo; i1++) {
/*  666 */         float yi = this._a0 * x[i2][i1];
/*  667 */         for (int j = 1; j < this._m; j++) {
/*  668 */           int k1 = i1 + this._lag1[j];
/*  669 */           int k2 = i2 + this._lag2[j];
/*  670 */           if (0 <= k1)
/*  671 */             yi += this._a[j] * x[k2][k1]; 
/*      */         } 
/*  673 */         y[i2][i1] = yi;
/*      */       } 
/*  675 */       for (i1 = i1lo; i1 < i1hi; i1++) {
/*  676 */         float yi = this._a0 * x[i2][i1];
/*  677 */         for (int j = 1; j < this._m; j++) {
/*  678 */           int k1 = i1 + this._lag1[j];
/*  679 */           int k2 = i2 + this._lag2[j];
/*  680 */           yi += this._a[j] * x[k2][k1];
/*      */         } 
/*  682 */         y[i2][i1] = yi;
/*      */       } 
/*  684 */       for (i1 = i1hi; i1 < n1; i1++) {
/*  685 */         float yi = this._a0 * x[i2][i1];
/*  686 */         for (int j = 1; j < this._m; j++) {
/*  687 */           int k1 = i1 + this._lag1[j];
/*  688 */           int k2 = i2 + this._lag2[j];
/*  689 */           if (k1 < n1)
/*  690 */             yi += this._a[j] * x[k2][k1]; 
/*      */         } 
/*  692 */         y[i2][i1] = yi;
/*      */       } 
/*      */     } 
/*  695 */     for (i2 = i2hi; i2 < n2; i2++) {
/*  696 */       for (int i1 = 0; i1 < n1; i1++) {
/*  697 */         float yi = this._a0 * x[i2][i1];
/*  698 */         for (int j = 1; j < this._m; j++) {
/*  699 */           int k1 = i1 + this._lag1[j];
/*  700 */           int k2 = i2 + this._lag2[j];
/*  701 */           if (0 <= k1 && k1 < n1 && k2 < n2)
/*  702 */             yi += this._a[j] * x[k2][k1]; 
/*      */         } 
/*  704 */         y[i2][i1] = yi;
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
/*      */   public void applyInverse(float[][] y, float[][] x) {
/*  718 */     int n1 = (y[0]).length;
/*  719 */     int n2 = y.length;
/*  720 */     int i1lo = ArrayMath.min(this._max1, n1);
/*  721 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  722 */     int i2lo = (i1lo <= i1hi) ? ArrayMath.min(this._max2, n2) : n2; int i2;
/*  723 */     for (i2 = 0; i2 < i2lo; i2++) {
/*  724 */       for (int i1 = 0; i1 < n1; i1++) {
/*  725 */         float xi = y[i2][i1];
/*  726 */         for (int j = 1; j < this._m; j++) {
/*  727 */           int k1 = i1 - this._lag1[j];
/*  728 */           int k2 = i2 - this._lag2[j];
/*  729 */           if (0 <= k1 && k1 < n1 && 0 <= k2)
/*  730 */             xi -= this._a[j] * x[k2][k1]; 
/*      */         } 
/*  732 */         x[i2][i1] = xi * this._a0i;
/*      */       } 
/*      */     } 
/*  735 */     for (i2 = i2lo; i2 < n2; i2++) {
/*  736 */       int i1; for (i1 = 0; i1 < i1lo; i1++) {
/*  737 */         float xi = y[i2][i1];
/*  738 */         for (int j = 1; j < this._m; j++) {
/*  739 */           int k1 = i1 - this._lag1[j];
/*  740 */           int k2 = i2 - this._lag2[j];
/*  741 */           if (0 <= k1)
/*  742 */             xi -= this._a[j] * x[k2][k1]; 
/*      */         } 
/*  744 */         x[i2][i1] = xi * this._a0i;
/*      */       } 
/*  746 */       for (i1 = i1lo; i1 < i1hi; i1++) {
/*  747 */         float xi = y[i2][i1];
/*  748 */         for (int j = 1; j < this._m; j++) {
/*  749 */           int k1 = i1 - this._lag1[j];
/*  750 */           int k2 = i2 - this._lag2[j];
/*  751 */           xi -= this._a[j] * x[k2][k1];
/*      */         } 
/*  753 */         x[i2][i1] = xi * this._a0i;
/*      */       } 
/*  755 */       for (i1 = i1hi; i1 < n1; i1++) {
/*  756 */         float xi = y[i2][i1];
/*  757 */         for (int j = 1; j < this._m; j++) {
/*  758 */           int k1 = i1 - this._lag1[j];
/*  759 */           int k2 = i2 - this._lag2[j];
/*  760 */           if (k1 < n1)
/*  761 */             xi -= this._a[j] * x[k2][k1]; 
/*      */         } 
/*  763 */         x[i2][i1] = xi * this._a0i;
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
/*      */   public void applyInverseTranspose(float[][] y, float[][] x) {
/*  777 */     int n1 = (y[0]).length;
/*  778 */     int n2 = y.length;
/*  779 */     int i1lo = ArrayMath.max(0, -this._min1);
/*  780 */     int i1hi = ArrayMath.min(n1, n1 - this._max1);
/*  781 */     int i2hi = (i1lo <= i1hi) ? ArrayMath.max(n2 - this._max2, 0) : 0; int i2;
/*  782 */     for (i2 = n2 - 1; i2 >= i2hi; i2--) {
/*  783 */       for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  784 */         float xi = y[i2][i1];
/*  785 */         for (int j = 1; j < this._m; j++) {
/*  786 */           int k1 = i1 + this._lag1[j];
/*  787 */           int k2 = i2 + this._lag2[j];
/*  788 */           if (0 <= k1 && k1 < n1 && k2 < n2)
/*  789 */             xi -= this._a[j] * x[k2][k1]; 
/*      */         } 
/*  791 */         x[i2][i1] = xi * this._a0i;
/*      */       } 
/*      */     } 
/*  794 */     for (i2 = i2hi - 1; i2 >= 0; i2--) {
/*  795 */       int i1; for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/*  796 */         float xi = y[i2][i1];
/*  797 */         for (int j = 1; j < this._m; j++) {
/*  798 */           int k1 = i1 + this._lag1[j];
/*  799 */           int k2 = i2 + this._lag2[j];
/*  800 */           if (k1 < n1)
/*  801 */             xi -= this._a[j] * x[k2][k1]; 
/*      */         } 
/*  803 */         x[i2][i1] = xi * this._a0i;
/*      */       } 
/*  805 */       for (i1 = i1hi - 1; i1 >= i1lo; i1--) {
/*  806 */         float xi = y[i2][i1];
/*  807 */         for (int j = 1; j < this._m; j++) {
/*  808 */           int k1 = i1 + this._lag1[j];
/*  809 */           int k2 = i2 + this._lag2[j];
/*  810 */           xi -= this._a[j] * x[k2][k1];
/*      */         } 
/*  812 */         x[i2][i1] = xi * this._a0i;
/*      */       } 
/*  814 */       for (i1 = i1lo - 1; i1 >= 0; i1--) {
/*  815 */         float xi = y[i2][i1];
/*  816 */         for (int j = 1; j < this._m; j++) {
/*  817 */           int k1 = i1 + this._lag1[j];
/*  818 */           int k2 = i2 + this._lag2[j];
/*  819 */           if (0 <= k1)
/*  820 */             xi -= this._a[j] * x[k2][k1]; 
/*      */         } 
/*  822 */         x[i2][i1] = xi * this._a0i;
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
/*      */   public void apply(float[][][] x, float[][][] y) {
/*  839 */     int n1 = (x[0][0]).length;
/*  840 */     int n2 = (x[0]).length;
/*  841 */     int n3 = x.length;
/*  842 */     int i1lo = ArrayMath.max(0, this._max1);
/*  843 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  844 */     int i2lo = ArrayMath.max(0, this._max2);
/*  845 */     int i2hi = ArrayMath.min(n2, n2 + this._min2);
/*  846 */     int i3lo = (i1lo <= i1hi && i2lo <= i2hi) ? ArrayMath.min(this._max3, n3) : n3; int i3;
/*  847 */     for (i3 = n3 - 1; i3 >= i3lo; i3--) {
/*  848 */       int i2; for (i2 = n2 - 1; i2 >= i2hi; i2--) {
/*  849 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  850 */           float yi = this._a0 * x[i3][i2][i1];
/*  851 */           for (int j = 1; j < this._m; j++) {
/*  852 */             int k1 = i1 - this._lag1[j];
/*  853 */             int k2 = i2 - this._lag2[j];
/*  854 */             int k3 = i3 - this._lag3[j];
/*  855 */             if (0 <= k1 && k1 < n1 && k2 < n2)
/*  856 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  858 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*  861 */       for (i2 = i2hi - 1; i2 >= i2lo; i2--) {
/*  862 */         int i1; for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/*  863 */           float yi = this._a0 * x[i3][i2][i1];
/*  864 */           for (int j = 1; j < this._m; j++) {
/*  865 */             int k1 = i1 - this._lag1[j];
/*  866 */             int k2 = i2 - this._lag2[j];
/*  867 */             int k3 = i3 - this._lag3[j];
/*  868 */             if (k1 < n1)
/*  869 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  871 */           y[i3][i2][i1] = yi;
/*      */         } 
/*  873 */         for (i1 = i1hi - 1; i1 >= i1lo; i1--) {
/*  874 */           float yi = this._a0 * x[i3][i2][i1];
/*  875 */           for (int j = 1; j < this._m; j++) {
/*  876 */             int k1 = i1 - this._lag1[j];
/*  877 */             int k2 = i2 - this._lag2[j];
/*  878 */             int k3 = i3 - this._lag3[j];
/*  879 */             yi += this._a[j] * x[k3][k2][k1];
/*      */           } 
/*  881 */           y[i3][i2][i1] = yi;
/*      */         } 
/*  883 */         for (i1 = i1lo - 1; i1 >= 0; i1--) {
/*  884 */           float yi = this._a0 * x[i3][i2][i1];
/*  885 */           for (int j = 1; j < this._m; j++) {
/*  886 */             int k1 = i1 - this._lag1[j];
/*  887 */             int k2 = i2 - this._lag2[j];
/*  888 */             int k3 = i3 - this._lag3[j];
/*  889 */             if (0 <= k1)
/*  890 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  892 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*  895 */       for (i2 = i2lo - 1; i2 >= 0; i2--) {
/*  896 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  897 */           float yi = this._a0 * x[i3][i2][i1];
/*  898 */           for (int j = 1; j < this._m; j++) {
/*  899 */             int k1 = i1 - this._lag1[j];
/*  900 */             int k2 = i2 - this._lag2[j];
/*  901 */             int k3 = i3 - this._lag3[j];
/*  902 */             if (0 <= k1 && k1 < n1 && 0 <= k2)
/*  903 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  905 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*      */     } 
/*  909 */     for (i3 = i3lo - 1; i3 >= 0; i3--) {
/*  910 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/*  911 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  912 */           float yi = this._a0 * x[i3][i2][i1];
/*  913 */           for (int j = 1; j < this._m; j++) {
/*  914 */             int k1 = i1 - this._lag1[j];
/*  915 */             int k2 = i2 - this._lag2[j];
/*  916 */             int k3 = i3 - this._lag3[j];
/*  917 */             if (0 <= k1 && k1 < n1 && 0 <= k2 && k2 < n2 && 0 <= k3)
/*  918 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  920 */           y[i3][i2][i1] = yi;
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
/*      */   
/*      */   public void applyTranspose(float[][][] x, float[][][] y) {
/*  935 */     int n1 = (x[0][0]).length;
/*  936 */     int n2 = (x[0]).length;
/*  937 */     int n3 = x.length;
/*  938 */     int i1lo = ArrayMath.max(0, -this._min1);
/*  939 */     int i1hi = ArrayMath.min(n1, n1 - this._max1);
/*  940 */     int i2lo = ArrayMath.max(0, -this._min2);
/*  941 */     int i2hi = ArrayMath.min(n2, n2 - this._max2);
/*  942 */     int i3hi = (i1lo <= i1hi && i2lo <= i2hi) ? ArrayMath.max(n3 - this._max3, 0) : 0; int i3;
/*  943 */     for (i3 = 0; i3 < i3hi; i3++) {
/*  944 */       int i2; for (i2 = 0; i2 < i2lo; i2++) {
/*  945 */         for (int i1 = 0; i1 < n1; i1++) {
/*  946 */           float yi = this._a0 * x[i3][i2][i1];
/*  947 */           for (int j = 1; j < this._m; j++) {
/*  948 */             int k1 = i1 + this._lag1[j];
/*  949 */             int k2 = i2 + this._lag2[j];
/*  950 */             int k3 = i3 + this._lag3[j];
/*  951 */             if (0 <= k1 && k1 < n1 && 0 <= k2)
/*  952 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  954 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*  957 */       for (i2 = i2lo; i2 < i2hi; i2++) {
/*  958 */         int i1; for (i1 = 0; i1 < i1lo; i1++) {
/*  959 */           float yi = this._a0 * x[i3][i2][i1];
/*  960 */           for (int j = 1; j < this._m; j++) {
/*  961 */             int k1 = i1 + this._lag1[j];
/*  962 */             int k2 = i2 + this._lag2[j];
/*  963 */             int k3 = i3 + this._lag3[j];
/*  964 */             if (0 <= k1)
/*  965 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  967 */           y[i3][i2][i1] = yi;
/*      */         } 
/*  969 */         for (i1 = i1lo; i1 < i1hi; i1++) {
/*  970 */           float yi = this._a0 * x[i3][i2][i1];
/*  971 */           for (int j = 1; j < this._m; j++) {
/*  972 */             int k1 = i1 + this._lag1[j];
/*  973 */             int k2 = i2 + this._lag2[j];
/*  974 */             int k3 = i3 + this._lag3[j];
/*  975 */             yi += this._a[j] * x[k3][k2][k1];
/*      */           } 
/*  977 */           y[i3][i2][i1] = yi;
/*      */         } 
/*  979 */         for (i1 = i1hi; i1 < n1; i1++) {
/*  980 */           float yi = this._a0 * x[i3][i2][i1];
/*  981 */           for (int j = 1; j < this._m; j++) {
/*  982 */             int k1 = i1 + this._lag1[j];
/*  983 */             int k2 = i2 + this._lag2[j];
/*  984 */             int k3 = i3 + this._lag3[j];
/*  985 */             if (k1 < n1)
/*  986 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  988 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*  991 */       for (i2 = i2hi; i2 < n2; i2++) {
/*  992 */         for (int i1 = 0; i1 < n1; i1++) {
/*  993 */           float yi = this._a0 * x[i3][i2][i1];
/*  994 */           for (int j = 1; j < this._m; j++) {
/*  995 */             int k1 = i1 + this._lag1[j];
/*  996 */             int k2 = i2 + this._lag2[j];
/*  997 */             int k3 = i3 + this._lag3[j];
/*  998 */             if (0 <= k1 && k1 < n1 && k2 < n2)
/*  999 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/* 1001 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1005 */     for (i3 = i3hi; i3 < n3; i3++) {
/* 1006 */       for (int i2 = 0; i2 < n2; i2++) {
/* 1007 */         for (int i1 = 0; i1 < n1; i1++) {
/* 1008 */           float yi = this._a0 * x[i3][i2][i1];
/* 1009 */           for (int j = 1; j < this._m; j++) {
/* 1010 */             int k1 = i1 + this._lag1[j];
/* 1011 */             int k2 = i2 + this._lag2[j];
/* 1012 */             int k3 = i3 + this._lag3[j];
/* 1013 */             if (0 <= k1 && k1 < n1 && 0 <= k2 && k2 < n2 && k3 < n3)
/* 1014 */               yi += this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/* 1016 */           y[i3][i2][i1] = yi;
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
/*      */   
/*      */   public void applyInverse(float[][][] y, float[][][] x) {
/* 1031 */     int n1 = (y[0][0]).length;
/* 1032 */     int n2 = (y[0]).length;
/* 1033 */     int n3 = y.length;
/* 1034 */     int i1lo = ArrayMath.max(0, this._max1);
/* 1035 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/* 1036 */     int i2lo = ArrayMath.max(0, this._max2);
/* 1037 */     int i2hi = ArrayMath.min(n2, n2 + this._min2);
/* 1038 */     int i3lo = (i1lo <= i1hi && i2lo <= i2hi) ? ArrayMath.min(this._max3, n3) : n3; int i3;
/* 1039 */     for (i3 = 0; i3 < i3lo; i3++) {
/* 1040 */       for (int i2 = 0; i2 < n2; i2++) {
/* 1041 */         for (int i1 = 0; i1 < n1; i1++) {
/* 1042 */           float xi = y[i3][i2][i1];
/* 1043 */           for (int j = 1; j < this._m; j++) {
/* 1044 */             int k1 = i1 - this._lag1[j];
/* 1045 */             int k2 = i2 - this._lag2[j];
/* 1046 */             int k3 = i3 - this._lag3[j];
/* 1047 */             if (0 <= k1 && k1 < n1 && 0 <= k2 && k2 < n2 && 0 <= k3)
/* 1048 */               xi -= this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/* 1050 */           x[i3][i2][i1] = xi * this._a0i;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1054 */     for (i3 = i3lo; i3 < n3; i3++) {
/* 1055 */       int i2; for (i2 = 0; i2 < i2lo; i2++) {
/* 1056 */         for (int i1 = 0; i1 < n1; i1++) {
/* 1057 */           float xi = y[i3][i2][i1];
/* 1058 */           for (int j = 1; j < this._m; j++) {
/* 1059 */             int k1 = i1 - this._lag1[j];
/* 1060 */             int k2 = i2 - this._lag2[j];
/* 1061 */             int k3 = i3 - this._lag3[j];
/* 1062 */             if (0 <= k1 && k1 < n1 && 0 <= k2)
/* 1063 */               xi -= this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/* 1065 */           x[i3][i2][i1] = xi * this._a0i;
/*      */         } 
/*      */       } 
/* 1068 */       for (i2 = i2lo; i2 < i2hi; i2++) {
/* 1069 */         int i1; for (i1 = 0; i1 < i1lo; i1++) {
/* 1070 */           float xi = y[i3][i2][i1];
/* 1071 */           for (int j = 1; j < this._m; j++) {
/* 1072 */             int k1 = i1 - this._lag1[j];
/* 1073 */             int k2 = i2 - this._lag2[j];
/* 1074 */             int k3 = i3 - this._lag3[j];
/* 1075 */             if (0 <= k1)
/* 1076 */               xi -= this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/* 1078 */           x[i3][i2][i1] = xi * this._a0i;
/*      */         } 
/* 1080 */         for (i1 = i1lo; i1 < i1hi; i1++) {
/* 1081 */           float xi = y[i3][i2][i1];
/* 1082 */           for (int j = 1; j < this._m; j++) {
/* 1083 */             int k1 = i1 - this._lag1[j];
/* 1084 */             int k2 = i2 - this._lag2[j];
/* 1085 */             int k3 = i3 - this._lag3[j];
/* 1086 */             xi -= this._a[j] * x[k3][k2][k1];
/*      */           } 
/* 1088 */           x[i3][i2][i1] = xi * this._a0i;
/*      */         } 
/* 1090 */         for (i1 = i1hi; i1 < n1; i1++) {
/* 1091 */           float xi = y[i3][i2][i1];
/* 1092 */           for (int j = 1; j < this._m; j++) {
/* 1093 */             int k1 = i1 - this._lag1[j];
/* 1094 */             int k2 = i2 - this._lag2[j];
/* 1095 */             int k3 = i3 - this._lag3[j];
/* 1096 */             if (k1 < n1)
/* 1097 */               xi -= this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/* 1099 */           x[i3][i2][i1] = xi * this._a0i;
/*      */         } 
/*      */       } 
/* 1102 */       for (i2 = i2hi; i2 < n2; i2++) {
/* 1103 */         for (int i1 = 0; i1 < n1; i1++) {
/* 1104 */           float xi = y[i3][i2][i1];
/* 1105 */           for (int j = 1; j < this._m; j++) {
/* 1106 */             int k1 = i1 - this._lag1[j];
/* 1107 */             int k2 = i2 - this._lag2[j];
/* 1108 */             int k3 = i3 - this._lag3[j];
/* 1109 */             if (0 <= k1 && k1 < n1 && k2 < n2)
/* 1110 */               xi -= this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/* 1112 */           x[i3][i2][i1] = xi * this._a0i;
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
/*      */   
/*      */   public void applyInverseTranspose(float[][][] y, float[][][] x) {
/* 1127 */     int n1 = (y[0][0]).length;
/* 1128 */     int n2 = (y[0]).length;
/* 1129 */     int n3 = y.length;
/* 1130 */     int i1lo = ArrayMath.max(0, -this._min1);
/* 1131 */     int i1hi = ArrayMath.min(n1, n1 - this._max1);
/* 1132 */     int i2lo = ArrayMath.max(0, -this._min2);
/* 1133 */     int i2hi = ArrayMath.min(n2, n2 - this._max2);
/* 1134 */     int i3hi = (i1lo <= i1hi && i2lo <= i2hi) ? ArrayMath.max(n3 - this._max3, 0) : 0; int i3;
/* 1135 */     for (i3 = n3 - 1; i3 >= i3hi; i3--) {
/* 1136 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/* 1137 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/* 1138 */           float xi = y[i3][i2][i1];
/* 1139 */           for (int j = 1; j < this._m; j++) {
/* 1140 */             int k1 = i1 + this._lag1[j];
/* 1141 */             int k2 = i2 + this._lag2[j];
/* 1142 */             int k3 = i3 + this._lag3[j];
/* 1143 */             if (0 <= k1 && k1 < n1 && 0 <= k2 && k2 < n2 && k3 < n3)
/* 1144 */               xi -= this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/* 1146 */           x[i3][i2][i1] = xi * this._a0i;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1150 */     for (i3 = i3hi - 1; i3 >= 0; i3--) {
/* 1151 */       int i2; for (i2 = n2 - 1; i2 >= i2hi; i2--) {
/* 1152 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/* 1153 */           float xi = y[i3][i2][i1];
/* 1154 */           for (int j = 1; j < this._m; j++) {
/* 1155 */             int k1 = i1 + this._lag1[j];
/* 1156 */             int k2 = i2 + this._lag2[j];
/* 1157 */             int k3 = i3 + this._lag3[j];
/* 1158 */             if (0 <= k1 && k1 < n1 && k2 < n2)
/* 1159 */               xi -= this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/* 1161 */           x[i3][i2][i1] = xi * this._a0i;
/*      */         } 
/*      */       } 
/* 1164 */       for (i2 = i2hi - 1; i2 >= i2lo; i2--) {
/* 1165 */         int i1; for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/* 1166 */           float xi = y[i3][i2][i1];
/* 1167 */           for (int j = 1; j < this._m; j++) {
/* 1168 */             int k1 = i1 + this._lag1[j];
/* 1169 */             int k2 = i2 + this._lag2[j];
/* 1170 */             int k3 = i3 + this._lag3[j];
/* 1171 */             if (k1 < n1)
/* 1172 */               xi -= this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/* 1174 */           x[i3][i2][i1] = xi * this._a0i;
/*      */         } 
/* 1176 */         for (i1 = i1hi - 1; i1 >= i1lo; i1--) {
/* 1177 */           float xi = y[i3][i2][i1];
/* 1178 */           for (int j = 1; j < this._m; j++) {
/* 1179 */             int k1 = i1 + this._lag1[j];
/* 1180 */             int k2 = i2 + this._lag2[j];
/* 1181 */             int k3 = i3 + this._lag3[j];
/* 1182 */             xi -= this._a[j] * x[k3][k2][k1];
/*      */           } 
/* 1184 */           x[i3][i2][i1] = xi * this._a0i;
/*      */         } 
/* 1186 */         for (i1 = i1lo - 1; i1 >= 0; i1--) {
/* 1187 */           float xi = y[i3][i2][i1];
/* 1188 */           for (int j = 1; j < this._m; j++) {
/* 1189 */             int k1 = i1 + this._lag1[j];
/* 1190 */             int k2 = i2 + this._lag2[j];
/* 1191 */             int k3 = i3 + this._lag3[j];
/* 1192 */             if (0 <= k1)
/* 1193 */               xi -= this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/* 1195 */           x[i3][i2][i1] = xi * this._a0i;
/*      */         } 
/*      */       } 
/* 1198 */       for (i2 = i2lo - 1; i2 >= 0; i2--) {
/* 1199 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/* 1200 */           float xi = y[i3][i2][i1];
/* 1201 */           for (int j = 1; j < this._m; j++) {
/* 1202 */             int k1 = i1 + this._lag1[j];
/* 1203 */             int k2 = i2 + this._lag2[j];
/* 1204 */             int k3 = i3 + this._lag3[j];
/* 1205 */             if (0 <= k1 && k1 < n1 && 0 <= k2)
/* 1206 */               xi -= this._a[j] * x[k3][k2][k1]; 
/*      */           } 
/* 1208 */           x[i3][i2][i1] = xi * this._a0i;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static float[] impulse(int nlag) {
/* 1228 */     float[] a = new float[nlag];
/* 1229 */     a[0] = 1.0F;
/* 1230 */     return a;
/*      */   }
/*      */   
/*      */   private void initLags(int[] lag1, float[] a) {
/* 1234 */     Check.argument((lag1.length > 0), "lag1.length>0");
/* 1235 */     Check.argument((lag1.length == a.length), "lag1.length==a.length");
/* 1236 */     Check.argument((lag1[0] == 0), "lag1[0]==0");
/* 1237 */     for (int j = 1; j < a.length; j++)
/* 1238 */       Check.argument((lag1[j] > 0), "lag1[" + j + "]>0"); 
/* 1239 */     this._m = lag1.length;
/* 1240 */     this._lag1 = ArrayMath.copy(lag1);
/* 1241 */     this._lag2 = ArrayMath.zeroint(this._m);
/* 1242 */     this._lag3 = ArrayMath.zeroint(this._m);
/* 1243 */     this._min1 = ArrayMath.min(lag1);
/* 1244 */     this._max1 = ArrayMath.max(lag1);
/*      */   }
/*      */   
/*      */   private void initLags(int[] lag1, int[] lag2, float[] a) {
/* 1248 */     Check.argument((lag1.length > 0), "lag1.length>0");
/* 1249 */     Check.argument((lag1.length == a.length), "lag1.length==a.length");
/* 1250 */     Check.argument((lag2.length == a.length), "lag2.length==a.length");
/* 1251 */     Check.argument((lag1[0] == 0), "lag1[0]==0");
/* 1252 */     Check.argument((lag2[0] == 0), "lag2[0]==0");
/* 1253 */     for (int j = 1; j < a.length; j++) {
/* 1254 */       Check.argument((lag2[j] >= 0), "lag2[" + j + "]>=0");
/* 1255 */       if (lag2[j] == 0)
/* 1256 */         Check.argument((lag1[j] > 0), "if lag2==0, lag1[" + j + "]>0"); 
/*      */     } 
/* 1258 */     this._m = lag1.length;
/* 1259 */     this._lag1 = ArrayMath.copy(lag1);
/* 1260 */     this._lag2 = ArrayMath.copy(lag2);
/* 1261 */     this._lag3 = ArrayMath.zeroint(this._m);
/* 1262 */     this._min1 = ArrayMath.min(lag1);
/* 1263 */     this._min2 = ArrayMath.min(lag2);
/* 1264 */     this._max1 = ArrayMath.max(lag1);
/* 1265 */     this._max2 = ArrayMath.max(lag2);
/*      */   }
/*      */   
/*      */   private void initLags(int[] lag1, int[] lag2, int[] lag3, float[] a) {
/* 1269 */     Check.argument((lag1.length > 0), "lag1.length>0");
/* 1270 */     Check.argument((lag1.length == a.length), "lag1.length==a.length");
/* 1271 */     Check.argument((lag2.length == a.length), "lag2.length==a.length");
/* 1272 */     Check.argument((lag3.length == a.length), "lag3.length==a.length");
/* 1273 */     Check.argument((lag1[0] == 0), "lag1[0]==0");
/* 1274 */     Check.argument((lag2[0] == 0), "lag2[0]==0");
/* 1275 */     Check.argument((lag3[0] == 0), "lag3[0]==0");
/* 1276 */     for (int j = 1; j < a.length; j++) {
/* 1277 */       Check.argument((lag3[j] >= 0), "lag3[" + j + "]>=0");
/* 1278 */       if (lag3[j] == 0) {
/* 1279 */         Check.argument((lag2[j] >= 0), "if lag3==0, lag2[" + j + "]>=0");
/* 1280 */         if (lag2[j] == 0)
/* 1281 */           Check.argument((lag1[j] > 0), "if lag3==0 && lag2==0, lag1[" + j + "]>0"); 
/*      */       } 
/*      */     } 
/* 1284 */     this._m = a.length;
/* 1285 */     this._lag1 = ArrayMath.copy(lag1);
/* 1286 */     this._lag2 = ArrayMath.copy(lag2);
/* 1287 */     this._lag3 = ArrayMath.copy(lag3);
/* 1288 */     this._min1 = ArrayMath.min(lag1);
/* 1289 */     this._min2 = ArrayMath.min(lag2);
/* 1290 */     this._min3 = ArrayMath.min(lag3);
/* 1291 */     this._max1 = ArrayMath.max(lag1);
/* 1292 */     this._max2 = ArrayMath.max(lag2);
/* 1293 */     this._max3 = ArrayMath.max(lag3);
/*      */   }
/*      */   
/*      */   private void initA(float[] a) {
/* 1297 */     this._a = ArrayMath.copy(a);
/* 1298 */     this._a0 = a[0];
/* 1299 */     this._a0i = 1.0F / a[0];
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/CausalFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */