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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Sampling
/*     */ {
/*     */   public static final double DEFAULT_TOLERANCE = 1.0E-6D;
/*     */   private int _n;
/*     */   private double _d;
/*     */   private double _f;
/*     */   private double[] _v;
/*     */   private double _t;
/*     */   private double _td;
/*     */   
/*     */   public Sampling(int n) {
/*  98 */     this(n, 1.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sampling(int n, double d, double f) {
/* 108 */     this(n, d, f, 1.0E-6D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sampling(int n, double d, double f, double t) {
/* 119 */     Check.argument((n > 0), "n>0");
/* 120 */     Check.argument((d > 0.0D), "d>0.0");
/* 121 */     this._n = n;
/* 122 */     this._d = d;
/* 123 */     this._f = f;
/* 124 */     this._v = null;
/* 125 */     this._t = t;
/* 126 */     this._td = this._t * this._d;
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
/*     */   public Sampling(double[] v) {
/* 141 */     this(v, 1.0E-6D);
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
/*     */   public Sampling(double[] v, double t) {
/* 156 */     Check.argument((v.length > 0), "v.length>0");
/* 157 */     Check.argument(ArrayMath.isIncreasing(v), "v is increasing");
/* 158 */     this._n = v.length;
/* 159 */     this._d = (this._n < 2) ? 1.0D : ((v[this._n - 1] - v[0]) / (this._n - 1));
/* 160 */     this._f = v[0];
/* 161 */     this._t = t;
/* 162 */     this._td = this._t * this._d;
/* 163 */     boolean uniform = true;
/* 164 */     for (int i = 0; i < this._n && uniform; i++) {
/* 165 */       double vi = this._f + i * this._d;
/* 166 */       if (!almostEqual(v[i], vi, this._td))
/* 167 */         uniform = false; 
/*     */     } 
/* 169 */     this._v = uniform ? null : ArrayMath.copy(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/* 177 */     return this._n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDelta() {
/* 186 */     return this._d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFirst() {
/* 194 */     return this._f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLast() {
/* 202 */     return (this._v != null) ? this._v[this._n - 1] : (this._f + (this._n - 1) * this._d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValue(int i) {
/* 211 */     Check.index(this._n, i);
/* 212 */     return value(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getValues() {
/*     */     double[] v;
/* 223 */     if (this._v != null) {
/* 224 */       v = ArrayMath.copy(this._v);
/*     */     } else {
/* 226 */       v = new double[this._n];
/* 227 */       for (int i = 0; i < this._n; i++)
/* 228 */         v[i] = this._f + i * this._d; 
/*     */     } 
/* 230 */     return v;
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
/*     */   public boolean isUniform() {
/* 244 */     return (this._v == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEquivalentTo(Sampling s) {
/* 255 */     Sampling t = this;
/* 256 */     if (t.isUniform() != s.isUniform())
/* 257 */       return false; 
/* 258 */     if (t.isUniform()) {
/* 259 */       if (t.getCount() != s.getCount())
/* 260 */         return false; 
/* 261 */       double d1 = tinyWith(s);
/* 262 */       double tf = t.getFirst();
/* 263 */       double tl = t.getLast();
/* 264 */       double sf = s.getFirst();
/* 265 */       double sl = s.getLast();
/* 266 */       return (almostEqual(tf, sf, d1) && almostEqual(tl, sl, d1));
/*     */     } 
/* 268 */     double tiny = tinyWith(s);
/* 269 */     for (int i = 0; i < this._n; i++) {
/* 270 */       if (!almostEqual(this._v[i], s.value(i), tiny))
/* 271 */         return false; 
/*     */     } 
/* 273 */     return true;
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
/*     */   public boolean isCompatible(Sampling s) {
/* 286 */     Sampling t = this;
/* 287 */     int nt = t.getCount();
/* 288 */     int ns = s.getCount();
/* 289 */     double tf = t.getFirst();
/* 290 */     double sf = s.getFirst();
/* 291 */     double tl = t.getLast();
/* 292 */     double sl = s.getLast();
/* 293 */     int it = 0;
/* 294 */     int is = 0;
/* 295 */     int jt = nt - 1;
/* 296 */     int js = ns - 1;
/* 297 */     if (tl < sf)
/* 298 */       return true; 
/* 299 */     if (sl < tf) {
/* 300 */       return true;
/*     */     }
/* 302 */     if (tf < sf) {
/* 303 */       it = t.indexOf(sf);
/*     */     } else {
/* 305 */       is = s.indexOf(tf);
/*     */     } 
/* 307 */     if (it < 0 || is < 0)
/* 308 */       return false; 
/* 309 */     if (tl < sl) {
/* 310 */       js = s.indexOf(tl);
/*     */     } else {
/* 312 */       jt = t.indexOf(sl);
/*     */     } 
/* 314 */     if (jt < 0 || js < 0)
/* 315 */       return false; 
/* 316 */     int mt = 1 + jt - it;
/* 317 */     int ms = 1 + js - is;
/* 318 */     if (mt != ms)
/* 319 */       return false; 
/* 320 */     if (!t.isUniform() || !s.isUniform()) {
/* 321 */       double tiny = tinyWith(s);
/* 322 */       for (jt = it, js = is; jt != mt; jt++, js++) {
/* 323 */         if (!almostEqual(t.value(jt), s.value(js), tiny))
/* 324 */           return false; 
/*     */       } 
/*     */     } 
/* 327 */     return true;
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
/*     */   public int indexOf(double x) {
/* 340 */     int i = -1;
/* 341 */     if (isUniform()) {
/* 342 */       int j = (int)Math.round((x - this._f) / this._d);
/* 343 */       if (0 <= j && j < this._n && almostEqual(x, this._f + j * this._d, this._td))
/* 344 */         i = j; 
/*     */     } else {
/* 346 */       int j = ArrayMath.binarySearch(this._v, x);
/* 347 */       if (0 <= j) {
/* 348 */         i = j;
/*     */       } else {
/* 350 */         j = -(j + 1);
/* 351 */         if (j > 0 && almostEqual(x, this._v[j - 1], this._td)) {
/* 352 */           i = j - 1;
/* 353 */         } else if (j < this._n && almostEqual(x, this._v[j], this._td)) {
/* 354 */           i = j;
/*     */         } 
/*     */       } 
/*     */     } 
/* 358 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOfNearest(double x) {
/*     */     int i;
/* 368 */     if (isUniform()) {
/* 369 */       i = (int)Math.round((x - this._f) / this._d);
/* 370 */       if (i < 0)
/* 371 */         i = 0; 
/* 372 */       if (i >= this._n)
/* 373 */         i = this._n - 1; 
/*     */     } else {
/* 375 */       i = ArrayMath.binarySearch(this._v, x);
/* 376 */       if (i < 0) {
/* 377 */         i = -(i + 1);
/* 378 */         if (i == this._n) {
/* 379 */           i = this._n - 1;
/* 380 */         } else if (i > 0 && Math.abs(x - this._v[i - 1]) < Math.abs(x - this._v[i])) {
/* 381 */           i--;
/*     */         } 
/*     */       } 
/*     */     } 
/* 385 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double valueOfNearest(double x) {
/* 394 */     return getValue(indexOfNearest(x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInBounds(int i) {
/* 405 */     return (0 <= i && i < this._n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInBounds(double x) {
/* 416 */     return (getFirst() <= x && x <= getLast());
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
/*     */   public boolean isInBoundsExtended(double x) {
/* 429 */     Check.state(isUniform(), "sampling is uniform");
/* 430 */     double dhalf = 0.5D * this._d;
/* 431 */     return (getFirst() - dhalf <= x && x <= getLast() + dhalf);
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
/*     */   public double getValueExtended(int i) {
/* 443 */     Check.state(isUniform(), "sampling is uniform");
/* 444 */     return this._f + i * this._d;
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
/*     */   public int indexOfNearestExtended(double x) {
/* 457 */     Check.state(isUniform(), "sampling is uniform");
/* 458 */     return (int)Math.round((x - this._f) / this._d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double valueOfNearestExtended(double x) {
/* 469 */     return getValueExtended(indexOfNearestExtended(x));
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
/*     */   public int indexOfFloorExtended(double x) {
/* 483 */     Check.state(isUniform(), "sampling is uniform");
/* 484 */     double xn = (x - this._f) / this._d;
/* 485 */     int ix = (int)xn;
/* 486 */     return (ix <= xn) ? ix : (ix - 1);
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
/*     */   public double normalizedDifferenceExtended(double x, int i) {
/* 500 */     Check.state(isUniform(), "sampling is uniform");
/* 501 */     return (x - this._f + i * this._d) / this._d;
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
/*     */   public int[] overlapWith(Sampling s) {
/* 534 */     Sampling t = this;
/* 535 */     int nt = t.getCount();
/* 536 */     int ns = s.getCount();
/* 537 */     double tf = t.getFirst();
/* 538 */     double sf = s.getFirst();
/* 539 */     double tl = t.getLast();
/* 540 */     double sl = s.getLast();
/* 541 */     int it = 0;
/* 542 */     int is = 0;
/* 543 */     int jt = nt - 1;
/* 544 */     int js = ns - 1;
/* 545 */     if (tl < sf)
/* 546 */       return new int[] { 0, nt, 0 }; 
/* 547 */     if (sl < tf) {
/* 548 */       return new int[] { 0, 0, ns };
/*     */     }
/* 550 */     if (tf < sf) {
/* 551 */       it = t.indexOf(sf);
/*     */     } else {
/* 553 */       is = s.indexOf(tf);
/*     */     } 
/* 555 */     if (it < 0 || is < 0)
/* 556 */       return null; 
/* 557 */     if (tl < sl) {
/* 558 */       js = s.indexOf(tl);
/*     */     } else {
/* 560 */       jt = t.indexOf(sl);
/*     */     } 
/* 562 */     if (jt < 0 || js < 0)
/* 563 */       return null; 
/* 564 */     int mt = 1 + jt - it;
/* 565 */     int ms = 1 + js - is;
/* 566 */     if (mt != ms)
/* 567 */       return null; 
/* 568 */     if (!t.isUniform() || !s.isUniform()) {
/* 569 */       double tiny = tinyWith(s);
/* 570 */       for (jt = it, js = is; jt != mt; jt++, js++) {
/* 571 */         if (!almostEqual(t.value(jt), s.value(js), tiny))
/* 572 */           return null; 
/*     */       } 
/*     */     } 
/* 575 */     return new int[] { mt, it, is };
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
/*     */   public Sampling mergeWith(Sampling s) {
/* 599 */     Sampling t = this;
/* 600 */     int[] overlap = t.overlapWith(s);
/* 601 */     if (overlap == null)
/* 602 */       return null; 
/* 603 */     int n = overlap[0];
/* 604 */     int it = overlap[1];
/* 605 */     int is = overlap[2];
/* 606 */     int nt = t.getCount();
/* 607 */     int ns = s.getCount();
/* 608 */     int nm = nt + ns - n;
/* 609 */     if (n > 0 && t.isUniform() && s.isUniform()) {
/* 610 */       double dm = t.getDelta();
/* 611 */       double fm = (it == 0) ? s.getFirst() : t.getFirst();
/* 612 */       return new Sampling(nm, dm, fm);
/*     */     } 
/* 614 */     double[] vm = new double[nm];
/* 615 */     int jm = 0;
/* 616 */     for (int j = 0; j < it; j++)
/* 617 */       vm[jm++] = t.value(j); 
/* 618 */     for (int i = 0; i < is; i++)
/* 619 */       vm[jm++] = s.value(i);  int jt;
/* 620 */     for (jt = it; jt < it + n; jt++)
/* 621 */       vm[jm++] = t.value(jt); 
/* 622 */     for (jt = it + n; jt < nt; jt++)
/* 623 */       vm[jm++] = t.value(jt); 
/* 624 */     for (int js = is + n; js < ns; js++)
/* 625 */       vm[jm++] = s.value(js); 
/* 626 */     return new Sampling(vm);
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
/*     */   public Sampling shift(double s) {
/* 638 */     if (this._v == null) {
/* 639 */       return new Sampling(this._n, this._d, this._f + s, this._t);
/*     */     }
/* 641 */     double[] v = new double[this._n];
/* 642 */     for (int i = 0; i < this._n; i++)
/* 643 */       v[i] = this._v[i] + s; 
/* 644 */     return new Sampling(v, this._t);
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
/*     */   public Sampling prepend(int m) {
/* 658 */     int n = this._n + m;
/* 659 */     double f = this._f - m * this._d;
/* 660 */     if (this._v == null) {
/* 661 */       return new Sampling(n, this._d, f, this._t);
/*     */     }
/* 663 */     double[] v = new double[n]; int i;
/* 664 */     for (i = 0; i < m; i++)
/* 665 */       v[i] = f + i * this._d; 
/* 666 */     for (i = m; i < n; i++)
/* 667 */       v[i] = this._v[i - m]; 
/* 668 */     return new Sampling(v, this._t);
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
/*     */   public Sampling append(int m) {
/* 682 */     int n = this._n + m;
/* 683 */     if (this._v == null) {
/* 684 */       return new Sampling(n, this._d, this._f, this._t);
/*     */     }
/* 686 */     double[] v = new double[n]; int i;
/* 687 */     for (i = 0; i < this._n; i++)
/* 688 */       v[i] = this._v[i]; 
/* 689 */     for (i = this._n; i < n; i++)
/* 690 */       v[i] = this._f + i * this._d; 
/* 691 */     return new Sampling(v, this._t);
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
/*     */   public Sampling decimate(int m) {
/* 705 */     Check.argument((m > 0), "m>0");
/* 706 */     int n = 1 + (this._n - 1) / m;
/* 707 */     if (this._v == null) {
/* 708 */       return new Sampling(n, m * this._d, this._f, this._t);
/*     */     }
/* 710 */     double[] v = new double[n]; int j;
/* 711 */     for (int i = 0; i < n; i++, j += m)
/* 712 */       v[i] = this._v[j]; 
/* 713 */     return new Sampling(v, this._t);
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
/*     */   public Sampling interpolate(int m) {
/* 727 */     Check.argument((m > 0), "m>0");
/* 728 */     int n = this._n + (this._n - 1) * (m - 1);
/* 729 */     if (this._v == null) {
/* 730 */       return new Sampling(n, this._d / m, this._f, this._t);
/*     */     }
/* 732 */     double[] v = new double[n];
/* 733 */     v[0] = this._v[0]; int j;
/* 734 */     for (int i = 1; i < this._n; i++, j += m) {
/* 735 */       v[j] = this._v[i];
/* 736 */       double dk = (v[j] - v[j - m]) / m;
/* 737 */       double vk = v[j - m];
/* 738 */       for (int k = j - m + 1; k < j; k++, vk += dk)
/* 739 */         v[k] = vk; 
/*     */     } 
/* 741 */     return new Sampling(v, this._t);
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
/*     */   private double value(int i) {
/* 756 */     return (this._v != null) ? this._v[i] : (this._f + i * this._d);
/*     */   }
/*     */   
/*     */   private boolean almostEqual(double v1, double v2, double tiny) {
/* 760 */     double diff = v1 - v2;
/* 761 */     return (diff < 0.0D) ? ((-diff < tiny)) : ((diff < tiny));
/*     */   }
/*     */   
/*     */   private double tinyWith(Sampling s) {
/* 765 */     return (this._td < s._td) ? this._td : s._td;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/Sampling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */