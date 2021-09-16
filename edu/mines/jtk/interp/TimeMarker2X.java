/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.awt.ColorMap;
/*     */ import edu.mines.jtk.dsp.Tensors2;
/*     */ import edu.mines.jtk.mosaic.PixelsView;
/*     */ import edu.mines.jtk.mosaic.SimplePlot;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.CompletionService;
/*     */ import java.util.concurrent.ExecutorCompletionService;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TimeMarker2X
/*     */ {
/*     */   private static final float INFINITY = 3.4028235E38F;
/*     */   private static final float EPSILON = 0.001F;
/*     */   private static final float ONE_MINUS_EPSILON = 0.999F;
/*     */   private int _n1;
/*     */   private int _n2;
/*     */   private Tensors2 _tensors;
/*     */   private Sample[][] _s;
/*     */   private ArrayList<Sample> _als;
/*     */   private Concurrency _concurrency;
/*     */   
/*     */   public enum Concurrency
/*     */   {
/*  65 */     PARALLEL,
/*  66 */     SERIAL;
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
/*     */   public void setTensors(Tensors2 tensors) {
/*     */     this._tensors = tensors;
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
/*     */   public void setConcurrency(Concurrency concurrency) {
/*     */     this._concurrency = concurrency;
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
/*     */   public void apply(float[][] times, int[][] marks) {
/*     */     TimeHeap2 theap = makeTimeHeap(times, marks);
/*     */     int nk = theap.size();
/*     */     for (int i2 = 0; i2 < this._n2; i2++) {
/*     */       for (int i1 = 0; i1 < this._n1; i1++) {
/*     */         times[i2][i1] = Float.MAX_VALUE;
/*     */       }
/*     */     } 
/*     */     float[][] t = new float[this._n2][this._n1];
/*     */     ActiveList al = new ActiveList();
/*     */     for (int ik = 0; ik < nk; ik++) {
/*     */       TimeHeap2.Entry ek = theap.remove();
/*     */       int k1 = ek.i1;
/*     */       int k2 = ek.i2;
/*     */       int m = ek.mark;
/*     */       times[k2][k1] = 0.0F;
/*     */       marks[k2][k1] = m;
/*     */       clearActivated();
/*     */       t[k2][k1] = 0.0F;
/*     */       al.append(this._s[k2][k1]);
/*     */       solve(al, t, m, times, marks);
/*     */       updateTimeHeap(times, marks, ek, theap);
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
/*     */   private void init(int n1, int n2, Tensors2 tensors) {
/*     */     this._n1 = n1;
/*     */     this._n2 = n2;
/*     */     this._tensors = tensors;
/*     */     this._s = new Sample[n2][n1];
/*     */     for (int i2 = 0; i2 < n2; i2++) {
/*     */       for (int i1 = 0; i1 < n1; i1++) {
/*     */         this._s[i2][i1] = new Sample(i1, i2);
/*     */       }
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
/*     */   private static final int[] K1 = new int[] { -1, 1, 0, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeMarker2X(int n1, int n2, Tensors2 tensors) {
/* 164 */     this._als = new ArrayList<>(2048);
/* 165 */     this._concurrency = Concurrency.PARALLEL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 278 */     this._activated = 1; init(n1, n2, tensors);
/*     */   } private static final int[] K2 = new int[] { 0, 0, -1, 1 }; private static final int[][] K1S = new int[][] { { 1, 1, 1 }, { -1, -1, -1 }, { -1, 1, 0 }, { -1, 1, 0 }, { -1, 1, -1, 1, -1, 1, 0, 0 } }; private static final int[][] K2S = new int[][] { { -1, 1, 0 }, { -1, 1, 0 }, { 1, 1, 1 }, { -1, -1, -1 }, { -1, -1, 1, 1, 0, 0, -1, 1 } }; private int _activated; private static class Sample {
/* 280 */     int i1; int i2; int activated; boolean absent; Sample(int i1, int i2) { this.i1 = i1; this.i2 = i2; } } private class ActiveList { private int _n; void append(TimeMarker2X.Sample s) { s.activated = TimeMarker2X.this._activated; if (this._n == this._a.length) growTo(2 * this._n);  this._a[this._n++] = s; } boolean isEmpty() { return (this._n == 0); } int size() { return this._n; } TimeMarker2X.Sample get(int i) { return this._a[i]; } void clear() { this._n = 0; } void setAllAbsent() { for (int i = 0; i < this._n; i++) (this._a[i]).absent = true;  } void appendIfAbsent(ActiveList al) { if (this._n + al._n > this._a.length) growTo(2 * (this._n + al._n));  int n = al._n; for (int i = 0; i < n; i++) { TimeMarker2X.Sample s = al.get(i); if (s.absent) { this._a[this._n++] = s; s.absent = false; }  }  } void shuffle() { Random r = new Random(); for (int i = 0; i < this._n; i++) { int j = r.nextInt(this._n); int k = r.nextInt(this._n); TimeMarker2X.Sample aj = this._a[j]; this._a[j] = this._a[k]; this._a[k] = aj; }  } void dump() { TimeMarker2X.trace("ActiveList.dump: n=" + this._n); for (int i = 0; i < this._n; i++) { TimeMarker2X.Sample s = this._a[i]; TimeMarker2X.trace(" s[" + i + "] = (" + s.i1 + "," + s.i2 + ")"); }  } private TimeMarker2X.Sample[] _a = new TimeMarker2X.Sample[1024]; private void growTo(int capacity) { TimeMarker2X.Sample[] a = new TimeMarker2X.Sample[capacity]; System.arraycopy(this._a, 0, a, 0, this._n); this._a = a; } private ActiveList() {} } private void clearActivated() { if (this._activated == Integer.MAX_VALUE) {
/* 281 */       this._activated = 1;
/* 282 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 283 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 284 */           (this._s[i2][i1]).activated = 0;
/*     */         }
/*     */       } 
/*     */     } else {
/* 288 */       this._activated++;
/*     */     }  }
/*     */   
/*     */   private void setActivated(Sample s) {
/* 292 */     s.activated = this._activated;
/*     */   }
/*     */   private void clearActivated(Sample s) {
/* 295 */     s.activated = 0;
/*     */   }
/*     */   private boolean wasActivated(Sample s) {
/* 298 */     return (s.activated == this._activated);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TimeHeap2 makeTimeHeap(float[][] times, int[][] marks) {
/* 308 */     int[][] kk = indexKnownSamples(times, marks);
/* 309 */     int[] km = kk[0];
/* 310 */     int[] k1 = kk[1];
/* 311 */     int[] k2 = kk[2];
/* 312 */     int nk = km.length;
/*     */ 
/*     */     
/* 315 */     int ikmid = 0;
/* 316 */     float dkmid = Float.MAX_VALUE;
/* 317 */     int m1 = this._n1 / 2;
/* 318 */     int m2 = this._n2 / 2;
/* 319 */     for (int ik = 0; ik < nk; ik++) {
/* 320 */       int i1 = k1[ik];
/* 321 */       int i2 = k2[ik];
/* 322 */       float d1 = (i1 - m1);
/* 323 */       float d2 = (i2 - m2);
/* 324 */       float dk = d1 * d1 + d2 * d2;
/* 325 */       if (dk < dkmid) {
/* 326 */         ikmid = ik;
/* 327 */         dkmid = dk;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 333 */     TimeHeap2 theap = new TimeHeap2(TimeHeap2.Type.MAX, this._n1, this._n2);
/* 334 */     for (int i = 0; i < nk; i++) {
/* 335 */       if (i == ikmid) {
/* 336 */         theap.insert(k1[i], k2[i], Float.MAX_VALUE, km[i]);
/*     */       } else {
/* 338 */         theap.insert(k1[i], k2[i], 1.7014117E38F, km[i]);
/*     */       } 
/*     */     } 
/* 341 */     return theap;
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
/*     */   private void updateTimeHeap(float[][] times, int[][] marks, TimeHeap2.Entry ek, TimeHeap2 theap) {
/* 353 */     if (theap.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 357 */     int k1 = ek.i1;
/* 358 */     int k2 = ek.i2;
/* 359 */     int m = ek.mark;
/*     */ 
/*     */     
/* 362 */     this._als.clear();
/*     */ 
/*     */     
/* 365 */     addSampleToList(k1, k2);
/*     */ 
/*     */     
/* 368 */     while (!this._als.isEmpty()) {
/*     */ 
/*     */       
/* 371 */       Sample s = this._als.remove(this._als.size() - 1);
/* 372 */       int i1 = s.i1;
/* 373 */       int i2 = s.i2;
/*     */ 
/*     */       
/* 376 */       if (m == marks[i2][i1] && theap.contains(i1, i2)) {
/* 377 */         theap.reduce(i1, i2, times[i2][i1]);
/*     */       }
/*     */       
/* 380 */       if (0 < i1) addSampleToList(i1 - 1, i2); 
/* 381 */       if (0 < i2) addSampleToList(i1, i2 - 1); 
/* 382 */       if (i1 < this._n1 - 1) addSampleToList(i1 + 1, i2); 
/* 383 */       if (i2 < this._n2 - 1) addSampleToList(i1, i2 + 1); 
/*     */     } 
/*     */   }
/*     */   private void addSampleToList(int i1, int i2) {
/* 387 */     if (wasActivated(this._s[i2][i1])) {
/* 388 */       this._als.add(this._s[i2][i1]);
/* 389 */       clearActivated(this._s[i2][i1]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[][] indexKnownSamples(float[][] times, int[][] marks) {
/* 397 */     int nk = 0;
/* 398 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 399 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 400 */         if (times[i2][i1] == 0.0F)
/* 401 */           nk++; 
/*     */       } 
/*     */     } 
/* 404 */     int[] km = new int[nk];
/* 405 */     int[] k1 = new int[nk];
/* 406 */     int[] k2 = new int[nk];
/* 407 */     for (int i = 0, ik = 0; i < this._n2; i++) {
/* 408 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 409 */         if (times[i][i1] == 0.0F) {
/* 410 */           km[ik] = marks[i][i1];
/* 411 */           k1[ik] = i1;
/* 412 */           k2[ik] = i;
/* 413 */           ik++;
/*     */         } 
/*     */       } 
/*     */     } 
/* 417 */     return new int[][] { km, k1, k2 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void solve(ActiveList al, float[][] t, int m, float[][] times, int[][] marks) {
/* 426 */     if (this._concurrency == Concurrency.PARALLEL) {
/* 427 */       solveParallel(al, t, m, times, marks);
/*     */     } else {
/* 429 */       solveSerial(al, t, m, times, marks);
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
/*     */   private void solveSerial(ActiveList al, float[][] t, int m, float[][] times, int[][] marks) {
/* 441 */     float[] d = new float[3];
/* 442 */     ActiveList bl = new ActiveList();
/*     */     
/* 444 */     while (!al.isEmpty()) {
/*     */       
/* 446 */       int n = al.size();
/*     */       
/* 448 */       for (int i = 0; i < n; i++) {
/* 449 */         Sample s = al.get(i);
/* 450 */         solveOne(t, m, times, marks, s, bl, d);
/*     */       } 
/* 452 */       bl.setAllAbsent();
/* 453 */       al.clear();
/* 454 */       al.appendIfAbsent(bl);
/* 455 */       bl.clear();
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
/*     */   private void solveParallel(final ActiveList al, final float[][] t, final int m, final float[][] times, final int[][] marks) {
/* 469 */     int nthread = Runtime.getRuntime().availableProcessors();
/* 470 */     ExecutorService es = Executors.newFixedThreadPool(nthread);
/* 471 */     CompletionService<Void> cs = new ExecutorCompletionService<>(es);
/* 472 */     ActiveList[] bl = new ActiveList[nthread];
/* 473 */     float[][] d = new float[nthread][];
/* 474 */     for (int ithread = 0; ithread < nthread; ithread++) {
/* 475 */       bl[ithread] = new ActiveList();
/* 476 */       d[ithread] = new float[3];
/*     */     } 
/* 478 */     final AtomicInteger ai = new AtomicInteger();
/*     */ 
/*     */     
/* 481 */     while (!al.isEmpty()) {
/* 482 */       ai.set(0);
/* 483 */       final int n = al.size();
/*     */       
/* 485 */       int mb = 32;
/* 486 */       final int nb = 1 + (n - 1) / 32;
/* 487 */       int ntask = ArrayMath.min(nb, nthread); int itask;
/* 488 */       for (itask = 0; itask < ntask; itask++) {
/* 489 */         final ActiveList bltask = bl[itask];
/* 490 */         final float[] dtask = d[itask];
/* 491 */         cs.submit(new Callable<Void>() {
/*     */               public Void call() {
/* 493 */                 for (int ib = ai.getAndIncrement(); ib < nb; ib = ai.getAndIncrement()) {
/* 494 */                   int i = ib * 32;
/* 495 */                   int j = ArrayMath.min(i + 32, n);
/* 496 */                   for (int k = i; k < j; k++) {
/* 497 */                     TimeMarker2X.Sample s = al.get(k);
/* 498 */                     TimeMarker2X.this.solveOne(t, m, times, marks, s, bltask, dtask);
/*     */                   } 
/*     */                 } 
/* 501 */                 bltask.setAllAbsent();
/* 502 */                 return null;
/*     */               }
/*     */             });
/*     */       } 
/*     */       try {
/* 507 */         for (itask = 0; itask < ntask; itask++)
/* 508 */           cs.take(); 
/* 509 */       } catch (InterruptedException e) {
/* 510 */         throw new RuntimeException(e);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 516 */       al.clear();
/* 517 */       for (itask = 0; itask < ntask; itask++) {
/* 518 */         al.appendIfAbsent(bl[itask]);
/* 519 */         bl[itask].clear();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 525 */     es.shutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float currentTime(float[][] t, int i1, int i2) {
/* 535 */     return wasActivated(this._s[i2][i1]) ? t[i2][i1] : Float.MAX_VALUE;
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
/*     */   private void solveOne(float[][] t, int m, float[][] times, int[][] marks, Sample s, ActiveList bl, float[] d) {
/* 547 */     int i1 = s.i1;
/* 548 */     int i2 = s.i2;
/*     */ 
/*     */     
/* 551 */     float ti = currentTime(t, i1, i2);
/* 552 */     float ci = computeTime(t, i1, i2, K1S[4], K2S[4], d);
/* 553 */     t[i2][i1] = ci;
/*     */ 
/*     */     
/* 556 */     if (ci >= ti * 0.999F) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 562 */       boolean checkNabors = (ci <= 1.5F * times[i2][i1]);
/*     */ 
/*     */       
/* 565 */       if (ci < times[i2][i1]) {
/* 566 */         times[i2][i1] = ci;
/* 567 */         marks[i2][i1] = m;
/*     */       } 
/*     */ 
/*     */       
/* 571 */       if (checkNabors)
/*     */       {
/*     */         
/* 574 */         for (int k = 0; k < 4; k++) {
/*     */ 
/*     */           
/* 577 */           int j1 = i1 + K1[k]; if (j1 >= 0 && j1 < this._n1) {
/* 578 */             int j2 = i2 + K2[k]; if (j2 >= 0 && j2 < this._n2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 584 */               float tj = currentTime(t, j1, j2);
/* 585 */               float cj = computeTime(t, j1, j2, K1S[k], K2S[k], d);
/*     */ 
/*     */               
/* 588 */               if (cj < tj * 0.999F) {
/*     */ 
/*     */                 
/* 591 */                 t[j2][j1] = cj;
/*     */ 
/*     */                 
/* 594 */                 bl.append(this._s[j2][j1]);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } else {
/*     */       
/* 602 */       bl.append(s);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float t1m(float[][] t, int i1, int i2) {
/* 625 */     return (--i1 >= 0 && wasActivated(this._s[i2][i1])) ? t[i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t1p(float[][] t, int i1, int i2) {
/* 628 */     return (++i1 < this._n1 && wasActivated(this._s[i2][i1])) ? t[i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t2m(float[][] t, int i1, int i2) {
/* 631 */     return (--i2 >= 0 && wasActivated(this._s[i2][i1])) ? t[i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t2p(float[][] t, int i1, int i2) {
/* 634 */     return (++i2 < this._n2 && wasActivated(this._s[i2][i1])) ? t[i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float computeTime(float[][] t, int i1, int i2, int[] k1s, int[] k2s, float[] d) {
/* 644 */     this._tensors.getTensor(i1, i2, d);
/* 645 */     float d11 = d[0];
/* 646 */     float d12 = d[1];
/* 647 */     float d22 = d[2];
/* 648 */     float e12 = 1.0F / (d11 * d22 - d12 * d12);
/* 649 */     float tc = currentTime(t, i1, i2);
/* 650 */     float t1m = t1m(t, i1, i2);
/* 651 */     float t1p = t1p(t, i1, i2);
/* 652 */     float t2m = t2m(t, i1, i2);
/* 653 */     float t2p = t2p(t, i1, i2);
/* 654 */     for (int k = 0; k < k1s.length; k++) {
/* 655 */       float t0; int k1 = k1s[k];
/* 656 */       int k2 = k2s[k];
/*     */       
/* 658 */       if (k1 != 0 && k2 != 0) {
/* 659 */         float t1 = (k1 < 0) ? t1m : t1p; if (t1 == Float.MAX_VALUE)
/* 660 */           continue;  float t2 = (k2 < 0) ? t2m : t2p; if (t2 == Float.MAX_VALUE)
/* 661 */           continue;  t0 = computeTime(d11, d12, d22, k1, k2, t1, t2);
/* 662 */       } else if (k1 != 0) {
/* 663 */         float t1 = (k1 < 0) ? t1m : t1p; if (t1 == Float.MAX_VALUE)
/* 664 */           continue;  t0 = t1 + ArrayMath.sqrt(d22 * e12);
/*     */       } else {
/* 666 */         float t2 = (k2 < 0) ? t2m : t2p; if (t2 == Float.MAX_VALUE)
/* 667 */           continue;  t0 = t2 + ArrayMath.sqrt(d11 * e12);
/*     */       } 
/* 669 */       if (t0 < tc)
/* 670 */         return t0;  continue;
/*     */     } 
/* 672 */     return tc;
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
/*     */   private static float computeTime(float d11, float d12, float d22, float s1, float s2, float t1, float t2) {
/* 693 */     double ds11 = (d11 * s1 * s1);
/* 694 */     double ds12 = (d12 * s1 * s2);
/* 695 */     double ds22 = (d22 * s2 * s2);
/* 696 */     double t12 = (t1 - t2);
/* 697 */     double a = ds11 + 2.0D * ds12 + ds22;
/* 698 */     double b = 2.0D * (ds12 + ds22) * t12;
/* 699 */     double c = ds22 * t12 * t12 - 1.0D;
/* 700 */     double d = b * b - 4.0D * a * c;
/* 701 */     if (d < 0.0D)
/* 702 */       return Float.MAX_VALUE; 
/* 703 */     double u1 = (-b + ArrayMath.sqrt(d)) / 2.0D * a;
/* 704 */     double u2 = u1 + t12;
/* 705 */     if (ds11 * u1 + ds12 * u2 < 0.0D || ds12 * u1 + ds22 * u2 < 0.0D)
/*     */     {
/* 707 */       return Float.MAX_VALUE; } 
/* 708 */     return t1 + (float)u1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void trace(String s) {
/* 715 */     System.out.println(s);
/*     */   }
/*     */   private static float[][] toFloat(int[][] i) {
/* 718 */     int n1 = (i[0]).length;
/* 719 */     int n2 = i.length;
/* 720 */     float[][] f = new float[n2][n1];
/* 721 */     for (int i2 = 0; i2 < n2; i2++) {
/* 722 */       for (int i1 = 0; i1 < n1; i1++)
/* 723 */         f[i2][i1] = i[i2][i1]; 
/* 724 */     }  return f;
/*     */   }
/*     */   private static void plot(int[][] i) {
/* 727 */     plot(toFloat(i));
/*     */   }
/*     */   private static void plot(float[][] f) {
/* 730 */     trace("plot f min=" + ArrayMath.min(f) + " max=" + ArrayMath.max(f));
/* 731 */     SimplePlot sp = new SimplePlot(SimplePlot.Origin.UPPER_LEFT);
/*     */ 
/*     */     
/* 734 */     sp.setSize(920, 900);
/* 735 */     PixelsView pv = sp.addPixels(f);
/* 736 */     pv.setColorModel(ColorMap.JET);
/* 737 */     pv.setInterpolation(PixelsView.Interpolation.NEAREST);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/TimeMarker2X.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */