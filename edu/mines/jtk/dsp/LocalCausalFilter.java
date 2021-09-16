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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LocalCausalFilter
/*      */ {
/*      */   private int _m;
/*      */   private int _min1;
/*      */   private int _max1;
/*      */   private int _min2;
/*      */   private int _max2;
/*      */   private int _max3;
/*      */   private int[] _lag1;
/*      */   private int[] _lag2;
/*      */   private int[] _lag3;
/*      */   
/*      */   public LocalCausalFilter(int[] lag1) {
/*  108 */     initLags(lag1);
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
/*      */   public LocalCausalFilter(int[] lag1, int[] lag2) {
/*  122 */     initLags(lag1, lag2);
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
/*      */   public LocalCausalFilter(int[] lag1, int[] lag2, int[] lag3) {
/*  137 */     initLags(lag1, lag2, lag3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getLag1() {
/*  145 */     return ArrayMath.copy(this._lag1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getLag2() {
/*  153 */     return ArrayMath.copy(this._lag2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getLag3() {
/*  161 */     return ArrayMath.copy(this._lag3);
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
/*      */   public void apply(A1 a1, float[] x, float[] y) {
/*  186 */     float[] a = new float[this._m];
/*  187 */     int n1 = x.length;
/*  188 */     int i1lo = ArrayMath.min(this._max1, n1); int i1;
/*  189 */     for (i1 = n1 - 1; i1 >= i1lo; i1--) {
/*  190 */       a1.get(i1, a);
/*  191 */       float yi = a[0] * x[i1];
/*  192 */       for (int j = 1; j < this._m; j++) {
/*  193 */         int k1 = i1 - this._lag1[j];
/*  194 */         yi += a[j] * x[k1];
/*      */       } 
/*  196 */       y[i1] = yi;
/*      */     } 
/*  198 */     for (i1 = i1lo - 1; i1 >= 0; i1--) {
/*  199 */       a1.get(i1, a);
/*  200 */       float yi = a[0] * x[i1];
/*  201 */       for (int j = 1; j < this._m; j++) {
/*  202 */         int k1 = i1 - this._lag1[j];
/*  203 */         if (0 <= k1)
/*  204 */           yi += a[j] * x[k1]; 
/*      */       } 
/*  206 */       y[i1] = yi;
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
/*      */   public void applyTranspose(A1 a1, float[] x, float[] y) {
/*  220 */     float[] a = new float[this._m];
/*  221 */     int n1 = x.length;
/*  222 */     int i1lo = ArrayMath.min(this._max1, n1); int i1;
/*  223 */     for (i1 = 0; i1 < i1lo; i1++) {
/*  224 */       a1.get(i1, a);
/*  225 */       float xi = x[i1];
/*  226 */       y[i1] = a[0] * xi;
/*  227 */       for (int j = 1; j < this._m; j++) {
/*  228 */         int k1 = i1 - this._lag1[j];
/*  229 */         if (0 <= k1)
/*  230 */           y[k1] = y[k1] + a[j] * xi; 
/*      */       } 
/*      */     } 
/*  233 */     for (i1 = i1lo; i1 < n1; i1++) {
/*  234 */       a1.get(i1, a);
/*  235 */       float xi = x[i1];
/*  236 */       y[i1] = a[0] * xi;
/*  237 */       for (int j = 1; j < this._m; j++) {
/*  238 */         int k1 = i1 - this._lag1[j];
/*  239 */         y[k1] = y[k1] + a[j] * xi;
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
/*      */   public void applyInverse(A1 a1, float[] y, float[] x) {
/*  254 */     float[] a = new float[this._m];
/*  255 */     int n1 = y.length;
/*  256 */     int i1lo = ArrayMath.min(this._max1, n1); int i1;
/*  257 */     for (i1 = 0; i1 < i1lo; i1++) {
/*  258 */       a1.get(i1, a);
/*  259 */       float xi = 0.0F;
/*  260 */       for (int j = 1; j < this._m; j++) {
/*  261 */         int k1 = i1 - this._lag1[j];
/*  262 */         if (0 <= k1)
/*  263 */           xi += a[j] * x[k1]; 
/*      */       } 
/*  265 */       x[i1] = (y[i1] - xi) / a[0];
/*      */     } 
/*  267 */     for (i1 = i1lo; i1 < n1; i1++) {
/*  268 */       a1.get(i1, a);
/*  269 */       float xi = 0.0F;
/*  270 */       for (int j = 1; j < this._m; j++) {
/*  271 */         int k1 = i1 - this._lag1[j];
/*  272 */         xi += a[j] * x[k1];
/*      */       } 
/*  274 */       x[i1] = (y[i1] - xi) / a[0];
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
/*      */   public void applyInverseTranspose(A1 a1, float[] y, float[] x) {
/*  289 */     Check.argument((x != y), "x!=y");
/*  290 */     ArrayMath.zero(x);
/*  291 */     float[] a = new float[this._m];
/*  292 */     int n1 = y.length;
/*  293 */     int i1lo = ArrayMath.min(this._max1, n1); int i1;
/*  294 */     for (i1 = n1 - 1; i1 >= i1lo; i1--) {
/*  295 */       a1.get(i1, a);
/*  296 */       float xi = x[i1] = (y[i1] - x[i1]) / a[0];
/*  297 */       for (int j = 1; j < this._m; j++) {
/*  298 */         int k1 = i1 - this._lag1[j];
/*  299 */         x[k1] = x[k1] + a[j] * xi;
/*      */       } 
/*      */     } 
/*  302 */     for (i1 = i1lo - 1; i1 >= 0; i1--) {
/*  303 */       a1.get(i1, a);
/*  304 */       float xi = x[i1] = (y[i1] - x[i1]) / a[0];
/*  305 */       for (int j = 1; j < this._m; j++) {
/*  306 */         int k1 = i1 - this._lag1[j];
/*  307 */         if (0 <= k1) {
/*  308 */           x[k1] = x[k1] + a[j] * xi;
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
/*      */   public void apply(A2 a2, float[][] x, float[][] y) {
/*  326 */     float[] a = new float[this._m];
/*  327 */     int n1 = (x[0]).length;
/*  328 */     int n2 = x.length;
/*  329 */     int i1lo = ArrayMath.max(0, this._max1);
/*  330 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  331 */     int i2lo = (i1lo <= i1hi) ? ArrayMath.min(this._max2, n2) : n2; int i2;
/*  332 */     for (i2 = n2 - 1; i2 >= i2lo; i2--) {
/*  333 */       int i1; for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/*  334 */         a2.get(i1, i2, a);
/*  335 */         float yi = a[0] * x[i2][i1];
/*  336 */         for (int j = 1; j < this._m; j++) {
/*  337 */           int k1 = i1 - this._lag1[j];
/*  338 */           int k2 = i2 - this._lag2[j];
/*  339 */           if (k1 < n1)
/*  340 */             yi += a[j] * x[k2][k1]; 
/*      */         } 
/*  342 */         y[i2][i1] = yi;
/*      */       } 
/*  344 */       for (i1 = i1hi - 1; i1 >= i1lo; i1--) {
/*  345 */         a2.get(i1, i2, a);
/*  346 */         float yi = a[0] * x[i2][i1];
/*  347 */         for (int j = 1; j < this._m; j++) {
/*  348 */           int k1 = i1 - this._lag1[j];
/*  349 */           int k2 = i2 - this._lag2[j];
/*  350 */           yi += a[j] * x[k2][k1];
/*      */         } 
/*  352 */         y[i2][i1] = yi;
/*      */       } 
/*  354 */       for (i1 = i1lo - 1; i1 >= 0; i1--) {
/*  355 */         a2.get(i1, i2, a);
/*  356 */         float yi = a[0] * x[i2][i1];
/*  357 */         for (int j = 1; j < this._m; j++) {
/*  358 */           int k1 = i1 - this._lag1[j];
/*  359 */           int k2 = i2 - this._lag2[j];
/*  360 */           if (0 <= k1)
/*  361 */             yi += a[j] * x[k2][k1]; 
/*      */         } 
/*  363 */         y[i2][i1] = yi;
/*      */       } 
/*      */     } 
/*  366 */     for (i2 = i2lo - 1; i2 >= 0; i2--) {
/*  367 */       for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  368 */         a2.get(i1, i2, a);
/*  369 */         float yi = a[0] * x[i2][i1];
/*  370 */         for (int j = 1; j < this._m; j++) {
/*  371 */           int k1 = i1 - this._lag1[j];
/*  372 */           int k2 = i2 - this._lag2[j];
/*  373 */           if (0 <= k1 && k1 < n1 && 0 <= k2)
/*  374 */             yi += a[j] * x[k2][k1]; 
/*      */         } 
/*  376 */         y[i2][i1] = yi;
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
/*      */   public void applyTranspose(A2 a2, float[][] x, float[][] y) {
/*  391 */     float[] a = new float[this._m];
/*  392 */     int n1 = (x[0]).length;
/*  393 */     int n2 = x.length;
/*  394 */     int i1lo = ArrayMath.max(0, this._max1);
/*  395 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  396 */     int i2lo = (i1lo <= i1hi) ? ArrayMath.min(this._max2, n2) : n2; int i2;
/*  397 */     for (i2 = 0; i2 < i2lo; i2++) {
/*  398 */       for (int i1 = 0; i1 < n1; i1++) {
/*  399 */         a2.get(i1, i2, a);
/*  400 */         float xi = x[i2][i1];
/*  401 */         y[i2][i1] = a[0] * xi;
/*  402 */         for (int j = 1; j < this._m; j++) {
/*  403 */           int k1 = i1 - this._lag1[j];
/*  404 */           int k2 = i2 - this._lag2[j];
/*  405 */           if (0 <= k1 && k1 < n1 && 0 <= k2)
/*  406 */             y[k2][k1] = y[k2][k1] + a[j] * xi; 
/*      */         } 
/*      */       } 
/*      */     } 
/*  410 */     for (i2 = i2lo; i2 < n2; i2++) {
/*  411 */       int i1; for (i1 = 0; i1 < i1lo; i1++) {
/*  412 */         a2.get(i1, i2, a);
/*  413 */         float xi = x[i2][i1];
/*  414 */         y[i2][i1] = a[0] * xi;
/*  415 */         for (int j = 1; j < this._m; j++) {
/*  416 */           int k1 = i1 - this._lag1[j];
/*  417 */           int k2 = i2 - this._lag2[j];
/*  418 */           if (0 <= k1)
/*  419 */             y[k2][k1] = y[k2][k1] + a[j] * xi; 
/*      */         } 
/*      */       } 
/*  422 */       for (i1 = i1lo; i1 < i1hi; i1++) {
/*  423 */         a2.get(i1, i2, a);
/*  424 */         float xi = x[i2][i1];
/*  425 */         y[i2][i1] = a[0] * xi;
/*  426 */         for (int j = 1; j < this._m; j++) {
/*  427 */           int k1 = i1 - this._lag1[j];
/*  428 */           int k2 = i2 - this._lag2[j];
/*  429 */           y[k2][k1] = y[k2][k1] + a[j] * xi;
/*      */         } 
/*      */       } 
/*  432 */       for (i1 = i1hi; i1 < n1; i1++) {
/*  433 */         a2.get(i1, i2, a);
/*  434 */         float xi = x[i2][i1];
/*  435 */         y[i2][i1] = a[0] * xi;
/*  436 */         for (int j = 1; j < this._m; j++) {
/*  437 */           int k1 = i1 - this._lag1[j];
/*  438 */           int k2 = i2 - this._lag2[j];
/*  439 */           if (k1 < n1) {
/*  440 */             y[k2][k1] = y[k2][k1] + a[j] * xi;
/*      */           }
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
/*      */   public void applyInverse(A2 a2, float[][] y, float[][] x) {
/*  456 */     float[] a = new float[this._m];
/*  457 */     int n1 = (y[0]).length;
/*  458 */     int n2 = y.length;
/*  459 */     int i1lo = ArrayMath.min(this._max1, n1);
/*  460 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  461 */     int i2lo = (i1lo <= i1hi) ? ArrayMath.min(this._max2, n2) : n2; int i2;
/*  462 */     for (i2 = 0; i2 < i2lo; i2++) {
/*  463 */       for (int i1 = 0; i1 < n1; i1++) {
/*  464 */         a2.get(i1, i2, a);
/*  465 */         float xi = 0.0F;
/*  466 */         for (int j = 1; j < this._m; j++) {
/*  467 */           int k1 = i1 - this._lag1[j];
/*  468 */           int k2 = i2 - this._lag2[j];
/*  469 */           if (0 <= k1 && k1 < n1 && 0 <= k2)
/*  470 */             xi += a[j] * x[k2][k1]; 
/*      */         } 
/*  472 */         x[i2][i1] = (y[i2][i1] - xi) / a[0];
/*      */       } 
/*      */     } 
/*  475 */     for (i2 = i2lo; i2 < n2; i2++) {
/*  476 */       int i1; for (i1 = 0; i1 < i1lo; i1++) {
/*  477 */         a2.get(i1, i2, a);
/*  478 */         float xi = 0.0F;
/*  479 */         for (int j = 1; j < this._m; j++) {
/*  480 */           int k1 = i1 - this._lag1[j];
/*  481 */           int k2 = i2 - this._lag2[j];
/*  482 */           if (0 <= k1)
/*  483 */             xi += a[j] * x[k2][k1]; 
/*      */         } 
/*  485 */         x[i2][i1] = (y[i2][i1] - xi) / a[0];
/*      */       } 
/*  487 */       for (i1 = i1lo; i1 < i1hi; i1++) {
/*  488 */         a2.get(i1, i2, a);
/*  489 */         float xi = 0.0F;
/*  490 */         for (int j = 1; j < this._m; j++) {
/*  491 */           int k1 = i1 - this._lag1[j];
/*  492 */           int k2 = i2 - this._lag2[j];
/*  493 */           xi += a[j] * x[k2][k1];
/*      */         } 
/*  495 */         x[i2][i1] = (y[i2][i1] - xi) / a[0];
/*      */       } 
/*  497 */       for (i1 = i1hi; i1 < n1; i1++) {
/*  498 */         a2.get(i1, i2, a);
/*  499 */         float xi = 0.0F;
/*  500 */         for (int j = 1; j < this._m; j++) {
/*  501 */           int k1 = i1 - this._lag1[j];
/*  502 */           int k2 = i2 - this._lag2[j];
/*  503 */           if (k1 < n1)
/*  504 */             xi += a[j] * x[k2][k1]; 
/*      */         } 
/*  506 */         x[i2][i1] = (y[i2][i1] - xi) / a[0];
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
/*      */   public void applyInverseTranspose(A2 a2, float[][] y, float[][] x) {
/*  522 */     Check.argument((x != y), "x!=y");
/*  523 */     ArrayMath.zero(x);
/*  524 */     float[] a = new float[this._m];
/*  525 */     int n1 = (y[0]).length;
/*  526 */     int n2 = y.length;
/*  527 */     int i1lo = ArrayMath.min(this._max1, n1);
/*  528 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  529 */     int i2lo = (i1lo <= i1hi) ? ArrayMath.min(this._max2, n2) : n2; int i2;
/*  530 */     for (i2 = n2 - 1; i2 >= i2lo; i2--) {
/*  531 */       int i1; for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/*  532 */         a2.get(i1, i2, a);
/*  533 */         float xi = x[i2][i1] = (y[i2][i1] - x[i2][i1]) / a[0];
/*  534 */         for (int j = 1; j < this._m; j++) {
/*  535 */           int k1 = i1 - this._lag1[j];
/*  536 */           int k2 = i2 - this._lag2[j];
/*  537 */           if (k1 < n1)
/*  538 */             x[k2][k1] = x[k2][k1] + a[j] * xi; 
/*      */         } 
/*      */       } 
/*  541 */       for (i1 = i1hi - 1; i1 >= i1lo; i1--) {
/*  542 */         a2.get(i1, i2, a);
/*  543 */         float xi = x[i2][i1] = (y[i2][i1] - x[i2][i1]) / a[0];
/*  544 */         for (int j = 1; j < this._m; j++) {
/*  545 */           int k1 = i1 - this._lag1[j];
/*  546 */           int k2 = i2 - this._lag2[j];
/*  547 */           x[k2][k1] = x[k2][k1] + a[j] * xi;
/*      */         } 
/*      */       } 
/*  550 */       for (i1 = i1lo - 1; i1 >= 0; i1--) {
/*  551 */         a2.get(i1, i2, a);
/*  552 */         float xi = x[i2][i1] = (y[i2][i1] - x[i2][i1]) / a[0];
/*  553 */         for (int j = 1; j < this._m; j++) {
/*  554 */           int k1 = i1 - this._lag1[j];
/*  555 */           int k2 = i2 - this._lag2[j];
/*  556 */           if (0 <= k1)
/*  557 */             x[k2][k1] = x[k2][k1] + a[j] * xi; 
/*      */         } 
/*      */       } 
/*      */     } 
/*  561 */     for (i2 = i2lo - 1; i2 >= 0; i2--) {
/*  562 */       for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  563 */         a2.get(i1, i2, a);
/*  564 */         float xi = x[i2][i1] = (y[i2][i1] - x[i2][i1]) / a[0];
/*  565 */         for (int j = 1; j < this._m; j++) {
/*  566 */           int k1 = i1 - this._lag1[j];
/*  567 */           int k2 = i2 - this._lag2[j];
/*  568 */           if (0 <= k1 && k1 < n1 && 0 <= k2) {
/*  569 */             x[k2][k1] = x[k2][k1] + a[j] * xi;
/*      */           }
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
/*      */   public void apply(A3 a3, float[][][] x, float[][][] y) {
/*  588 */     float[] a = new float[this._m];
/*  589 */     int n1 = (x[0][0]).length;
/*  590 */     int n2 = (x[0]).length;
/*  591 */     int n3 = x.length;
/*  592 */     int i1lo = ArrayMath.max(0, this._max1);
/*  593 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  594 */     int i2lo = ArrayMath.max(0, this._max2);
/*  595 */     int i2hi = ArrayMath.min(n2, n2 + this._min2);
/*  596 */     int i3lo = (i1lo <= i1hi && i2lo <= i2hi) ? ArrayMath.min(this._max3, n3) : n3; int i3;
/*  597 */     for (i3 = n3 - 1; i3 >= i3lo; i3--) {
/*  598 */       int i2; for (i2 = n2 - 1; i2 >= i2hi; i2--) {
/*  599 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  600 */           a3.get(i1, i2, i3, a);
/*  601 */           float yi = a[0] * x[i3][i2][i1];
/*  602 */           for (int j = 1; j < this._m; j++) {
/*  603 */             int k1 = i1 - this._lag1[j];
/*  604 */             int k2 = i2 - this._lag2[j];
/*  605 */             int k3 = i3 - this._lag3[j];
/*  606 */             if (0 <= k1 && k1 < n1 && k2 < n2)
/*  607 */               yi += a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  609 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*  612 */       for (i2 = i2hi - 1; i2 >= i2lo; i2--) {
/*  613 */         int i1; for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/*  614 */           a3.get(i1, i2, i3, a);
/*  615 */           float yi = a[0] * x[i3][i2][i1];
/*  616 */           for (int j = 1; j < this._m; j++) {
/*  617 */             int k1 = i1 - this._lag1[j];
/*  618 */             int k2 = i2 - this._lag2[j];
/*  619 */             int k3 = i3 - this._lag3[j];
/*  620 */             if (k1 < n1)
/*  621 */               yi += a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  623 */           y[i3][i2][i1] = yi;
/*      */         } 
/*  625 */         for (i1 = i1hi - 1; i1 >= i1lo; i1--) {
/*  626 */           a3.get(i1, i2, i3, a);
/*  627 */           float yi = a[0] * x[i3][i2][i1];
/*  628 */           for (int j = 1; j < this._m; j++) {
/*  629 */             int k1 = i1 - this._lag1[j];
/*  630 */             int k2 = i2 - this._lag2[j];
/*  631 */             int k3 = i3 - this._lag3[j];
/*  632 */             yi += a[j] * x[k3][k2][k1];
/*      */           } 
/*  634 */           y[i3][i2][i1] = yi;
/*      */         } 
/*  636 */         for (i1 = i1lo - 1; i1 >= 0; i1--) {
/*  637 */           a3.get(i1, i2, i3, a);
/*  638 */           float yi = a[0] * x[i3][i2][i1];
/*  639 */           for (int j = 1; j < this._m; j++) {
/*  640 */             int k1 = i1 - this._lag1[j];
/*  641 */             int k2 = i2 - this._lag2[j];
/*  642 */             int k3 = i3 - this._lag3[j];
/*  643 */             if (0 <= k1)
/*  644 */               yi += a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  646 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*  649 */       for (i2 = i2lo - 1; i2 >= 0; i2--) {
/*  650 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  651 */           a3.get(i1, i2, i3, a);
/*  652 */           float yi = a[0] * x[i3][i2][i1];
/*  653 */           for (int j = 1; j < this._m; j++) {
/*  654 */             int k1 = i1 - this._lag1[j];
/*  655 */             int k2 = i2 - this._lag2[j];
/*  656 */             int k3 = i3 - this._lag3[j];
/*  657 */             if (0 <= k1 && k1 < n1 && 0 <= k2)
/*  658 */               yi += a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  660 */           y[i3][i2][i1] = yi;
/*      */         } 
/*      */       } 
/*      */     } 
/*  664 */     for (i3 = i3lo - 1; i3 >= 0; i3--) {
/*  665 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/*  666 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  667 */           a3.get(i1, i2, i3, a);
/*  668 */           float yi = a[0] * x[i3][i2][i1];
/*  669 */           for (int j = 1; j < this._m; j++) {
/*  670 */             int k1 = i1 - this._lag1[j];
/*  671 */             int k2 = i2 - this._lag2[j];
/*  672 */             int k3 = i3 - this._lag3[j];
/*  673 */             if (0 <= k1 && k1 < n1 && 0 <= k2 && k2 < n2 && 0 <= k3)
/*  674 */               yi += a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  676 */           y[i3][i2][i1] = yi;
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
/*      */   public void applyTranspose(A3 a3, float[][][] x, float[][][] y) {
/*  692 */     float[] a = new float[this._m];
/*  693 */     int n1 = (x[0][0]).length;
/*  694 */     int n2 = (x[0]).length;
/*  695 */     int n3 = x.length;
/*  696 */     int i1lo = ArrayMath.max(0, this._max1);
/*  697 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  698 */     int i2lo = ArrayMath.max(0, this._max2);
/*  699 */     int i2hi = ArrayMath.min(n2, n2 + this._min2);
/*  700 */     int i3lo = (i1lo <= i1hi && i2lo <= i2hi) ? ArrayMath.min(this._max3, n3) : n3; int i3;
/*  701 */     for (i3 = 0; i3 < i3lo; i3++) {
/*  702 */       for (int i2 = 0; i2 < n2; i2++) {
/*  703 */         for (int i1 = 0; i1 < n1; i1++) {
/*  704 */           a3.get(i1, i2, i3, a);
/*  705 */           float xi = x[i3][i2][i1];
/*  706 */           y[i3][i2][i1] = a[0] * xi;
/*  707 */           for (int j = 1; j < this._m; j++) {
/*  708 */             int k1 = i1 - this._lag1[j];
/*  709 */             int k2 = i2 - this._lag2[j];
/*  710 */             int k3 = i3 - this._lag3[j];
/*  711 */             if (0 <= k1 && k1 < n1 && 0 <= k2 && k2 < n2 && 0 <= k3)
/*  712 */               y[k3][k2][k1] = y[k3][k2][k1] + a[j] * xi; 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  717 */     for (i3 = i3lo; i3 < n3; i3++) {
/*  718 */       int i2; for (i2 = 0; i2 < i2lo; i2++) {
/*  719 */         for (int i1 = 0; i1 < n1; i1++) {
/*  720 */           a3.get(i1, i2, i3, a);
/*  721 */           float xi = x[i3][i2][i1];
/*  722 */           y[i3][i2][i1] = a[0] * xi;
/*  723 */           for (int j = 1; j < this._m; j++) {
/*  724 */             int k1 = i1 - this._lag1[j];
/*  725 */             int k2 = i2 - this._lag2[j];
/*  726 */             int k3 = i3 - this._lag3[j];
/*  727 */             if (0 <= k1 && k1 < n1 && 0 <= k2)
/*  728 */               y[k3][k2][k1] = y[k3][k2][k1] + a[j] * xi; 
/*      */           } 
/*      */         } 
/*      */       } 
/*  732 */       for (i2 = i2lo; i2 < i2hi; i2++) {
/*  733 */         int i1; for (i1 = 0; i1 < i1lo; i1++) {
/*  734 */           a3.get(i1, i2, i3, a);
/*  735 */           float xi = x[i3][i2][i1];
/*  736 */           y[i3][i2][i1] = a[0] * xi;
/*  737 */           for (int j = 1; j < this._m; j++) {
/*  738 */             int k1 = i1 - this._lag1[j];
/*  739 */             int k2 = i2 - this._lag2[j];
/*  740 */             int k3 = i3 - this._lag3[j];
/*  741 */             if (0 <= k1)
/*  742 */               y[k3][k2][k1] = y[k3][k2][k1] + a[j] * xi; 
/*      */           } 
/*      */         } 
/*  745 */         for (i1 = i1lo; i1 < i1hi; i1++) {
/*  746 */           a3.get(i1, i2, i3, a);
/*  747 */           float xi = x[i3][i2][i1];
/*  748 */           y[i3][i2][i1] = a[0] * xi;
/*  749 */           for (int j = 1; j < this._m; j++) {
/*  750 */             int k1 = i1 - this._lag1[j];
/*  751 */             int k2 = i2 - this._lag2[j];
/*  752 */             int k3 = i3 - this._lag3[j];
/*  753 */             y[k3][k2][k1] = y[k3][k2][k1] + a[j] * xi;
/*      */           } 
/*      */         } 
/*  756 */         for (i1 = i1hi; i1 < n1; i1++) {
/*  757 */           a3.get(i1, i2, i3, a);
/*  758 */           float xi = x[i3][i2][i1];
/*  759 */           y[i3][i2][i1] = a[0] * xi;
/*  760 */           for (int j = 1; j < this._m; j++) {
/*  761 */             int k1 = i1 - this._lag1[j];
/*  762 */             int k2 = i2 - this._lag2[j];
/*  763 */             int k3 = i3 - this._lag3[j];
/*  764 */             if (k1 < n1)
/*  765 */               y[k3][k2][k1] = y[k3][k2][k1] + a[j] * xi; 
/*      */           } 
/*      */         } 
/*      */       } 
/*  769 */       for (i2 = i2hi; i2 < n2; i2++) {
/*  770 */         for (int i1 = 0; i1 < n1; i1++) {
/*  771 */           a3.get(i1, i2, i3, a);
/*  772 */           float xi = x[i3][i2][i1];
/*  773 */           y[i3][i2][i1] = a[0] * xi;
/*  774 */           for (int j = 1; j < this._m; j++) {
/*  775 */             int k1 = i1 - this._lag1[j];
/*  776 */             int k2 = i2 - this._lag2[j];
/*  777 */             int k3 = i3 - this._lag3[j];
/*  778 */             if (0 <= k1 && k1 < n1 && k2 < n2) {
/*  779 */               y[k3][k2][k1] = y[k3][k2][k1] + a[j] * xi;
/*      */             }
/*      */           } 
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
/*      */   public void applyInverse(A3 a3, float[][][] y, float[][][] x) {
/*  796 */     float[] a = new float[this._m];
/*  797 */     int n1 = (y[0][0]).length;
/*  798 */     int n2 = (y[0]).length;
/*  799 */     int n3 = y.length;
/*  800 */     int i1lo = ArrayMath.max(0, this._max1);
/*  801 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  802 */     int i2lo = ArrayMath.max(0, this._max2);
/*  803 */     int i2hi = ArrayMath.min(n2, n2 + this._min2);
/*  804 */     int i3lo = (i1lo <= i1hi && i2lo <= i2hi) ? ArrayMath.min(this._max3, n3) : n3; int i3;
/*  805 */     for (i3 = 0; i3 < i3lo; i3++) {
/*  806 */       for (int i2 = 0; i2 < n2; i2++) {
/*  807 */         for (int i1 = 0; i1 < n1; i1++) {
/*  808 */           a3.get(i1, i2, i3, a);
/*  809 */           float xi = 0.0F;
/*  810 */           for (int j = 1; j < this._m; j++) {
/*  811 */             int k1 = i1 - this._lag1[j];
/*  812 */             int k2 = i2 - this._lag2[j];
/*  813 */             int k3 = i3 - this._lag3[j];
/*  814 */             if (0 <= k1 && k1 < n1 && 0 <= k2 && k2 < n2 && 0 <= k3)
/*  815 */               xi += a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  817 */           x[i3][i2][i1] = (y[i3][i2][i1] - xi) / a[0];
/*      */         } 
/*      */       } 
/*      */     } 
/*  821 */     for (i3 = i3lo; i3 < n3; i3++) {
/*  822 */       int i2; for (i2 = 0; i2 < i2lo; i2++) {
/*  823 */         for (int i1 = 0; i1 < n1; i1++) {
/*  824 */           a3.get(i1, i2, i3, a);
/*  825 */           float xi = 0.0F;
/*  826 */           for (int j = 1; j < this._m; j++) {
/*  827 */             int k1 = i1 - this._lag1[j];
/*  828 */             int k2 = i2 - this._lag2[j];
/*  829 */             int k3 = i3 - this._lag3[j];
/*  830 */             if (0 <= k1 && k1 < n1 && 0 <= k2)
/*  831 */               xi += a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  833 */           x[i3][i2][i1] = (y[i3][i2][i1] - xi) / a[0];
/*      */         } 
/*      */       } 
/*  836 */       for (i2 = i2lo; i2 < i2hi; i2++) {
/*  837 */         int i1; for (i1 = 0; i1 < i1lo; i1++) {
/*  838 */           a3.get(i1, i2, i3, a);
/*  839 */           float xi = 0.0F;
/*  840 */           for (int j = 1; j < this._m; j++) {
/*  841 */             int k1 = i1 - this._lag1[j];
/*  842 */             int k2 = i2 - this._lag2[j];
/*  843 */             int k3 = i3 - this._lag3[j];
/*  844 */             if (0 <= k1)
/*  845 */               xi += a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  847 */           x[i3][i2][i1] = (y[i3][i2][i1] - xi) / a[0];
/*      */         } 
/*  849 */         for (i1 = i1lo; i1 < i1hi; i1++) {
/*  850 */           a3.get(i1, i2, i3, a);
/*  851 */           float xi = 0.0F;
/*  852 */           for (int j = 1; j < this._m; j++) {
/*  853 */             int k1 = i1 - this._lag1[j];
/*  854 */             int k2 = i2 - this._lag2[j];
/*  855 */             int k3 = i3 - this._lag3[j];
/*  856 */             xi += a[j] * x[k3][k2][k1];
/*      */           } 
/*  858 */           x[i3][i2][i1] = (y[i3][i2][i1] - xi) / a[0];
/*      */         } 
/*  860 */         for (i1 = i1hi; i1 < n1; i1++) {
/*  861 */           a3.get(i1, i2, i3, a);
/*  862 */           float xi = 0.0F;
/*  863 */           for (int j = 1; j < this._m; j++) {
/*  864 */             int k1 = i1 - this._lag1[j];
/*  865 */             int k2 = i2 - this._lag2[j];
/*  866 */             int k3 = i3 - this._lag3[j];
/*  867 */             if (k1 < n1)
/*  868 */               xi += a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  870 */           x[i3][i2][i1] = (y[i3][i2][i1] - xi) / a[0];
/*      */         } 
/*      */       } 
/*  873 */       for (i2 = i2hi; i2 < n2; i2++) {
/*  874 */         for (int i1 = 0; i1 < n1; i1++) {
/*  875 */           a3.get(i1, i2, i3, a);
/*  876 */           float xi = 0.0F;
/*  877 */           for (int j = 1; j < this._m; j++) {
/*  878 */             int k1 = i1 - this._lag1[j];
/*  879 */             int k2 = i2 - this._lag2[j];
/*  880 */             int k3 = i3 - this._lag3[j];
/*  881 */             if (0 <= k1 && k1 < n1 && k2 < n2)
/*  882 */               xi += a[j] * x[k3][k2][k1]; 
/*      */           } 
/*  884 */           x[i3][i2][i1] = (y[i3][i2][i1] - xi) / a[0];
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
/*      */   public void applyInverseTranspose(A3 a3, float[][][] y, float[][][] x) {
/*  901 */     Check.argument((x != y), "x!=y");
/*  902 */     ArrayMath.zero(x);
/*  903 */     float[] a = new float[this._m];
/*  904 */     int n1 = (y[0][0]).length;
/*  905 */     int n2 = (y[0]).length;
/*  906 */     int n3 = y.length;
/*  907 */     int i1lo = ArrayMath.max(0, this._max1);
/*  908 */     int i1hi = ArrayMath.min(n1, n1 + this._min1);
/*  909 */     int i2lo = ArrayMath.max(0, this._max2);
/*  910 */     int i2hi = ArrayMath.min(n2, n2 + this._min2);
/*  911 */     int i3lo = (i1lo <= i1hi && i2lo <= i2hi) ? ArrayMath.min(this._max3, n3) : n3; int i3;
/*  912 */     for (i3 = n3 - 1; i3 >= i3lo; i3--) {
/*  913 */       int i2; for (i2 = n2 - 1; i2 >= i2hi; i2--) {
/*  914 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  915 */           a3.get(i1, i2, i3, a);
/*  916 */           float xi = x[i3][i2][i1] = (y[i3][i2][i1] - x[i3][i2][i1]) / a[0];
/*  917 */           for (int j = 1; j < this._m; j++) {
/*  918 */             int k1 = i1 - this._lag1[j];
/*  919 */             int k2 = i2 - this._lag2[j];
/*  920 */             int k3 = i3 - this._lag3[j];
/*  921 */             if (0 <= k1 && k1 < n1 && k2 < n2)
/*  922 */               x[k3][k2][k1] = x[k3][k2][k1] + a[j] * xi; 
/*      */           } 
/*      */         } 
/*      */       } 
/*  926 */       for (i2 = i2hi - 1; i2 >= i2lo; i2--) {
/*  927 */         int i1; for (i1 = n1 - 1; i1 >= i1hi; i1--) {
/*  928 */           a3.get(i1, i2, i3, a);
/*  929 */           float xi = x[i3][i2][i1] = (y[i3][i2][i1] - x[i3][i2][i1]) / a[0];
/*  930 */           for (int j = 1; j < this._m; j++) {
/*  931 */             int k1 = i1 - this._lag1[j];
/*  932 */             int k2 = i2 - this._lag2[j];
/*  933 */             int k3 = i3 - this._lag3[j];
/*  934 */             if (k1 < n1)
/*  935 */               x[k3][k2][k1] = x[k3][k2][k1] + a[j] * xi; 
/*      */           } 
/*      */         } 
/*  938 */         for (i1 = i1hi - 1; i1 >= i1lo; i1--) {
/*  939 */           a3.get(i1, i2, i3, a);
/*  940 */           float xi = x[i3][i2][i1] = (y[i3][i2][i1] - x[i3][i2][i1]) / a[0];
/*  941 */           for (int j = 1; j < this._m; j++) {
/*  942 */             int k1 = i1 - this._lag1[j];
/*  943 */             int k2 = i2 - this._lag2[j];
/*  944 */             int k3 = i3 - this._lag3[j];
/*  945 */             x[k3][k2][k1] = x[k3][k2][k1] + a[j] * xi;
/*      */           } 
/*      */         } 
/*  948 */         for (i1 = i1lo - 1; i1 >= 0; i1--) {
/*  949 */           a3.get(i1, i2, i3, a);
/*  950 */           float xi = x[i3][i2][i1] = (y[i3][i2][i1] - x[i3][i2][i1]) / a[0];
/*  951 */           for (int j = 1; j < this._m; j++) {
/*  952 */             int k1 = i1 - this._lag1[j];
/*  953 */             int k2 = i2 - this._lag2[j];
/*  954 */             int k3 = i3 - this._lag3[j];
/*  955 */             if (0 <= k1)
/*  956 */               x[k3][k2][k1] = x[k3][k2][k1] + a[j] * xi; 
/*      */           } 
/*      */         } 
/*      */       } 
/*  960 */       for (i2 = i2lo - 1; i2 >= 0; i2--) {
/*  961 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  962 */           a3.get(i1, i2, i3, a);
/*  963 */           float xi = x[i3][i2][i1] = (y[i3][i2][i1] - x[i3][i2][i1]) / a[0];
/*  964 */           for (int j = 1; j < this._m; j++) {
/*  965 */             int k1 = i1 - this._lag1[j];
/*  966 */             int k2 = i2 - this._lag2[j];
/*  967 */             int k3 = i3 - this._lag3[j];
/*  968 */             if (0 <= k1 && k1 < n1 && 0 <= k2)
/*  969 */               x[k3][k2][k1] = x[k3][k2][k1] + a[j] * xi; 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  974 */     for (i3 = i3lo - 1; i3 >= 0; i3--) {
/*  975 */       for (int i2 = n2 - 1; i2 >= 0; i2--) {
/*  976 */         for (int i1 = n1 - 1; i1 >= 0; i1--) {
/*  977 */           a3.get(i1, i2, i3, a);
/*  978 */           float xi = x[i3][i2][i1] = (y[i3][i2][i1] - x[i3][i2][i1]) / a[0];
/*  979 */           for (int j = 1; j < this._m; j++) {
/*  980 */             int k1 = i1 - this._lag1[j];
/*  981 */             int k2 = i2 - this._lag2[j];
/*  982 */             int k3 = i3 - this._lag3[j];
/*  983 */             if (0 <= k1 && k1 < n1 && 0 <= k2 && k2 < n2 && 0 <= k3) {
/*  984 */               x[k3][k2][k1] = x[k3][k2][k1] + a[j] * xi;
/*      */             }
/*      */           } 
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
/*      */   private void initLags(int[] lag1) {
/* 1003 */     Check.argument((lag1.length > 0), "lag1.length>0");
/* 1004 */     Check.argument((lag1[0] == 0), "lag1[0]==0");
/* 1005 */     for (int j = 1; j < lag1.length; j++)
/* 1006 */       Check.argument((lag1[j] > 0), "lag1[" + j + "]>0"); 
/* 1007 */     this._m = lag1.length;
/* 1008 */     this._lag1 = ArrayMath.copy(lag1);
/* 1009 */     this._lag2 = ArrayMath.zeroint(this._m);
/* 1010 */     this._lag3 = ArrayMath.zeroint(this._m);
/* 1011 */     this._min1 = ArrayMath.min(lag1);
/* 1012 */     this._max1 = ArrayMath.max(lag1);
/*      */   }
/*      */   
/*      */   private void initLags(int[] lag1, int[] lag2) {
/* 1016 */     Check.argument((lag1.length > 0), "lag1.length>0");
/* 1017 */     Check.argument((lag1[0] == 0), "lag1[0]==0");
/* 1018 */     Check.argument((lag2[0] == 0), "lag2[0]==0");
/* 1019 */     for (int j = 1; j < lag1.length; j++) {
/* 1020 */       Check.argument((lag2[j] >= 0), "lag2[" + j + "]>=0");
/* 1021 */       if (lag2[j] == 0)
/* 1022 */         Check.argument((lag1[j] > 0), "if lag2==0, lag1[" + j + "]>0"); 
/*      */     } 
/* 1024 */     this._m = lag1.length;
/* 1025 */     this._lag1 = ArrayMath.copy(lag1);
/* 1026 */     this._lag2 = ArrayMath.copy(lag2);
/* 1027 */     this._lag3 = ArrayMath.zeroint(this._m);
/* 1028 */     this._min1 = ArrayMath.min(lag1);
/* 1029 */     this._min2 = ArrayMath.min(lag2);
/* 1030 */     this._max1 = ArrayMath.max(lag1);
/* 1031 */     this._max2 = ArrayMath.max(lag2);
/*      */   }
/*      */   
/*      */   private void initLags(int[] lag1, int[] lag2, int[] lag3) {
/* 1035 */     Check.argument((lag1.length > 0), "lag1.length>0");
/* 1036 */     Check.argument((lag1[0] == 0), "lag1[0]==0");
/* 1037 */     Check.argument((lag2[0] == 0), "lag2[0]==0");
/* 1038 */     Check.argument((lag3[0] == 0), "lag3[0]==0");
/* 1039 */     for (int j = 1; j < lag1.length; j++) {
/* 1040 */       Check.argument((lag3[j] >= 0), "lag3[" + j + "]>=0");
/* 1041 */       if (lag3[j] == 0) {
/* 1042 */         Check.argument((lag2[j] >= 0), "if lag3==0, lag2[" + j + "]>=0");
/* 1043 */         if (lag2[j] == 0)
/* 1044 */           Check.argument((lag1[j] > 0), "if lag3==0 && lag2==0, lag1[" + j + "]>0"); 
/*      */       } 
/*      */     } 
/* 1047 */     this._m = lag1.length;
/* 1048 */     this._lag1 = ArrayMath.copy(lag1);
/* 1049 */     this._lag2 = ArrayMath.copy(lag2);
/* 1050 */     this._lag3 = ArrayMath.copy(lag3);
/* 1051 */     this._min1 = ArrayMath.min(lag1);
/* 1052 */     this._min2 = ArrayMath.min(lag2);
/* 1053 */     this._max1 = ArrayMath.max(lag1);
/* 1054 */     this._max2 = ArrayMath.max(lag2);
/* 1055 */     this._max3 = ArrayMath.max(lag3);
/*      */   }
/*      */   
/*      */   public static interface A3 {
/*      */     void get(int param1Int1, int param1Int2, int param1Int3, float[] param1ArrayOffloat);
/*      */   }
/*      */   
/*      */   public static interface A2 {
/*      */     void get(int param1Int1, int param1Int2, float[] param1ArrayOffloat);
/*      */   }
/*      */   
/*      */   public static interface A1 {
/*      */     void get(int param1Int, float[] param1ArrayOffloat);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/LocalCausalFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */