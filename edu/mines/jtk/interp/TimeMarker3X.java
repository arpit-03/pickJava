/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.dsp.Tensors3;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Stopwatch;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.CompletionService;
/*     */ import java.util.concurrent.ExecutorCompletionService;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TimeMarker3X
/*     */ {
/*     */   public enum Concurrency
/*     */   {
/*  67 */     PARALLEL,
/*  68 */     SERIAL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTensors(Tensors3 tensors) {
/*     */     this._tensors = tensors;
/*     */   }
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
/*     */   public void apply(float[][][] times, int[][][] marks) {
/*     */     Stopwatch sw = new Stopwatch();
/*     */     sw.start();
/*     */     log.fine("TimeMarker3X.apply: begin time=" + (int)sw.time());
/*     */     TimeHeap3 theap = makeTimeHeap(times, marks);
/*     */     int nk = theap.size();
/*     */     log.fine("TimeMarker3X.apply: heap time=" + (int)sw.time());
/*     */     for (int i3 = 0; i3 < this._n3; i3++) {
/*     */       for (int i2 = 0; i2 < this._n2; i2++) {
/*     */         for (int i1 = 0; i1 < this._n1; i1++) {
/*     */           times[i3][i2][i1] = Float.MAX_VALUE;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     float[][][] t = new float[this._n3][this._n2][this._n1];
/*     */     ActiveList al = new ActiveList();
/*     */     for (int ik = 0; ik < nk; ik++) {
/*     */       if (ik % (1 + (nk - 1) / 100) == 0) {
/*     */         log.fine("  apply: ik/nk=" + ik + "/" + nk + " time=" + (int)sw.time());
/*     */       }
/*     */       TimeHeap3.Entry ek = theap.remove();
/*     */       int k1 = ek.i1;
/*     */       int k2 = ek.i2;
/*     */       int k3 = ek.i3;
/*     */       int m = ek.mark;
/*     */       times[k3][k2][k1] = 0.0F;
/*     */       marks[k3][k2][k1] = m;
/*     */       clearActivated();
/*     */       t[k3][k2][k1] = 0.0F;
/*     */       al.append(this._s[k3][k2][k1]);
/*     */       solve(al, t, m, times, marks);
/*     */       updateTimeHeap(times, marks, ek, theap);
/*     */     } 
/*     */     sw.stop();
/*     */     log.fine("TimeMarker3X.apply: end time=" + (int)sw.time());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Logger log = Logger.getLogger(NearestGridder3.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float INFINITY = 3.4028235E38F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float EPSILON = 0.001F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float ONE_MINUS_EPSILON = 0.999F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _n1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _n2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _n3;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Tensors3 _tensors;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Sample[][][] _s;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeMarker3X(int n1, int n2, int n3, Tensors3 tensors) {
/* 185 */     this._als = new ArrayList<>(2048);
/* 186 */     this._concurrency = Concurrency.PARALLEL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     this._activated = 1; init(n1, n2, n3, tensors);
/*     */   } private ArrayList<Sample> _als; private Concurrency _concurrency; private void init(int n1, int n2, int n3, Tensors3 tensors) { this._n1 = n1; this._n2 = n2; this._n3 = n3; this._tensors = tensors; this._s = new Sample[n3][n2][n1]; for (int i3 = 0; i3 < n3; i3++) { for (int i2 = 0; i2 < n2; i2++) { for (int i1 = 0; i1 < n1; i1++) this._s[i3][i2][i1] = new Sample(i1, i2, i3);  }  }  } private static final int[] K1 = new int[] { -1, 1, 0, 0, 0, 0 }; private static final int[] K2 = new int[] { 0, 0, -1, 1, 0, 0 }; private static final int[] K3 = new int[] { 0, 0, 0, 0, -1, 1 }; private static final int[][] K1S = new int[][] { { 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, 1, -1, 1, -1, 1, 0, 0, 0 }, { -1, 1, -1, 1, -1, 1, 0, 0, 0 }, { -1, -1, 1, 1, 0, 0, -1, 1, 0 }, { -1, -1, 1, 1, 0, 0, -1, 1, 0 }, { -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0 } }; private static final int[][] K2S = new int[][] { { -1, -1, 1, 1, 0, 0, -1, 1, 0 }, { -1, -1, 1, 1, 0, 0, -1, 1, 0 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, 1, -1, 1, -1, 1, 0, 0, 0 }, { -1, 1, -1, 1, -1, 1, 0, 0, 0 }, { -1, -1, 1, 1, -1, -1, 1, 1, -1, -1, 1, 1, 0, 0, 0, 0, -1, 1, -1, 1, 0, 0, -1, 1, 0, 0 } }; private static final int[][] K3S = new int[][] { { -1, 1, -1, 1, -1, 1, 0, 0, 0 }, { -1, 1, -1, 1, -1, 1, 0, 0, 0 }, { -1, -1, 1, 1, 0, 0, -1, 1, 0 }, { -1, -1, 1, 1, 0, 0, -1, 1, 0 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, 1, 1, 1, 1, 0, 0, 0, 0, -1, -1, 1, 1, -1, -1, 1, 1, 0, 0, 0, 0, -1, 1 } }; private int _activated; private static class Sample {
/* 324 */     int i1; int i2; int i3; int activated; boolean absent; Sample(int i1, int i2, int i3) { this.i1 = i1; this.i2 = i2; this.i3 = i3; } } private class ActiveList { private int _n; void append(TimeMarker3X.Sample s) { s.activated = TimeMarker3X.this._activated; if (this._n == this._a.length) growTo(2 * this._n);  this._a[this._n++] = s; } boolean isEmpty() { return (this._n == 0); } int size() { return this._n; } TimeMarker3X.Sample get(int i) { return this._a[i]; } void clear() { this._n = 0; } void setAllAbsent() { for (int i = 0; i < this._n; i++) (this._a[i]).absent = true;  } void appendIfAbsent(ActiveList al) { if (this._n + al._n > this._a.length) growTo(2 * (this._n + al._n));  int n = al._n; for (int i = 0; i < n; i++) { TimeMarker3X.Sample s = al.get(i); if (s.absent) { this._a[this._n++] = s; s.absent = false; }  }  } void shuffle() { Random r = new Random(); for (int i = 0; i < this._n; i++) { int j = r.nextInt(this._n); int k = r.nextInt(this._n); TimeMarker3X.Sample aj = this._a[j]; this._a[j] = this._a[k]; this._a[k] = aj; }  } void dump() { TimeMarker3X.trace("ActiveList.dump: n=" + this._n); for (int i = 0; i < this._n; i++) { TimeMarker3X.Sample s = this._a[i]; TimeMarker3X.trace(" s[" + i + "] = (" + s.i1 + "," + s.i2 + "," + s.i3 + ")"); }  } private TimeMarker3X.Sample[] _a = new TimeMarker3X.Sample[1024]; private void growTo(int capacity) { TimeMarker3X.Sample[] a = new TimeMarker3X.Sample[capacity]; System.arraycopy(this._a, 0, a, 0, this._n); this._a = a; } private ActiveList() {} } private void clearActivated() { if (this._activated == Integer.MAX_VALUE) {
/* 325 */       this._activated = 1;
/* 326 */       for (int i3 = 0; i3 < this._n3; i3++) {
/* 327 */         for (int i2 = 0; i2 < this._n2; i2++) {
/* 328 */           for (int i1 = 0; i1 < this._n1; i1++) {
/* 329 */             (this._s[i3][i2][i1]).activated = 0;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/* 334 */       this._activated++;
/*     */     }  }
/*     */   
/*     */   private void setActivated(Sample s) {
/* 338 */     s.activated = this._activated;
/*     */   }
/*     */   private void clearActivated(Sample s) {
/* 341 */     s.activated = 0;
/*     */   }
/*     */   private boolean wasActivated(Sample s) {
/* 344 */     return (s.activated == this._activated);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TimeHeap3 makeTimeHeap(float[][][] times, int[][][] marks) {
/* 354 */     int[][] kk = indexKnownSamples(times, marks);
/* 355 */     int[] km = kk[0];
/* 356 */     int[] k1 = kk[1];
/* 357 */     int[] k2 = kk[2];
/* 358 */     int[] k3 = kk[3];
/* 359 */     int nk = km.length;
/*     */ 
/*     */     
/* 362 */     int ikmid = 0;
/* 363 */     float dkmid = Float.MAX_VALUE;
/* 364 */     int m1 = this._n1 / 2;
/* 365 */     int m2 = this._n2 / 2;
/* 366 */     int m3 = this._n3 / 2;
/* 367 */     for (int ik = 0; ik < nk; ik++) {
/* 368 */       int i1 = k1[ik];
/* 369 */       int i2 = k2[ik];
/* 370 */       int i3 = k3[ik];
/* 371 */       float d1 = (i1 - m1);
/* 372 */       float d2 = (i2 - m2);
/* 373 */       float d3 = (i3 - m3);
/* 374 */       float dk = d1 * d1 + d2 * d2 + d3 * d3;
/* 375 */       if (dk < dkmid) {
/* 376 */         ikmid = ik;
/* 377 */         dkmid = dk;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 383 */     TimeHeap3 theap = new TimeHeap3(TimeHeap3.Type.MAX, this._n1, this._n2, this._n3);
/* 384 */     for (int i = 0; i < nk; i++) {
/* 385 */       if (i == ikmid) {
/* 386 */         theap.insert(k1[i], k2[i], k3[i], Float.MAX_VALUE, km[i]);
/*     */       } else {
/* 388 */         theap.insert(k1[i], k2[i], k3[i], 1.7014117E38F, km[i]);
/*     */       } 
/*     */     } 
/* 391 */     return theap;
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
/*     */   private void updateTimeHeap(float[][][] times, int[][][] marks, TimeHeap3.Entry ek, TimeHeap3 theap) {
/* 403 */     if (theap.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 407 */     int k1 = ek.i1;
/* 408 */     int k2 = ek.i2;
/* 409 */     int k3 = ek.i3;
/* 410 */     int m = ek.mark;
/*     */ 
/*     */     
/* 413 */     this._als.clear();
/*     */ 
/*     */     
/* 416 */     addSampleToList(k1, k2, k3);
/*     */ 
/*     */     
/* 419 */     while (!this._als.isEmpty()) {
/*     */ 
/*     */       
/* 422 */       Sample s = this._als.remove(this._als.size() - 1);
/* 423 */       int i1 = s.i1;
/* 424 */       int i2 = s.i2;
/* 425 */       int i3 = s.i3;
/*     */ 
/*     */       
/* 428 */       if (m == marks[i3][i2][i1] && theap.contains(i1, i2, i3)) {
/* 429 */         theap.reduce(i1, i2, i3, times[i3][i2][i1]);
/*     */       }
/*     */       
/* 432 */       if (0 < i1) addSampleToList(i1 - 1, i2, i3); 
/* 433 */       if (0 < i2) addSampleToList(i1, i2 - 1, i3); 
/* 434 */       if (0 < i3) addSampleToList(i1, i2, i3 - 1); 
/* 435 */       if (i1 < this._n1 - 1) addSampleToList(i1 + 1, i2, i3); 
/* 436 */       if (i2 < this._n2 - 1) addSampleToList(i1, i2 + 1, i3); 
/* 437 */       if (i3 < this._n3 - 1) addSampleToList(i1, i3, i3 + 1); 
/*     */     } 
/*     */   }
/*     */   private void addSampleToList(int i1, int i2, int i3) {
/* 441 */     if (wasActivated(this._s[i3][i2][i1])) {
/* 442 */       this._als.add(this._s[i3][i2][i1]);
/* 443 */       clearActivated(this._s[i3][i2][i1]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[][] indexKnownSamples(float[][][] times, int[][][] marks) {
/* 451 */     int nk = 0;
/* 452 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 453 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 454 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 455 */           if (times[i3][i2][i1] == 0.0F)
/* 456 */             nk++; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 460 */     int[] km = new int[nk];
/* 461 */     int[] k1 = new int[nk];
/* 462 */     int[] k2 = new int[nk];
/* 463 */     int[] k3 = new int[nk];
/* 464 */     for (int i = 0, ik = 0; i < this._n3; i++) {
/* 465 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 466 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 467 */           if (times[i][i2][i1] == 0.0F) {
/* 468 */             km[ik] = marks[i][i2][i1];
/* 469 */             k1[ik] = i1;
/* 470 */             k2[ik] = i2;
/* 471 */             k3[ik] = i;
/* 472 */             ik++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 477 */     return new int[][] { km, k1, k2, k3 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void solve(ActiveList al, float[][][] t, int m, float[][][] times, int[][][] marks) {
/* 486 */     if (this._concurrency == Concurrency.PARALLEL) {
/* 487 */       solveParallel(al, t, m, times, marks);
/*     */     } else {
/* 489 */       solveSerial(al, t, m, times, marks);
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
/*     */   private void solveSerial(ActiveList al, float[][][] t, int m, float[][][] times, int[][][] marks) {
/* 501 */     float[] d = new float[6];
/* 502 */     ActiveList bl = new ActiveList();
/* 503 */     int ntotal = 0;
/* 504 */     while (!al.isEmpty()) {
/*     */       
/* 506 */       int n = al.size();
/* 507 */       ntotal += n;
/* 508 */       for (int i = 0; i < n; i++) {
/* 509 */         Sample s = al.get(i);
/* 510 */         solveOne(t, m, times, marks, s, bl, d);
/*     */       } 
/* 512 */       bl.setAllAbsent();
/* 513 */       al.clear();
/* 514 */       al.appendIfAbsent(bl);
/* 515 */       bl.clear();
/*     */     } 
/* 517 */     trace("solveSerial: ntotal=" + ntotal);
/* 518 */     trace("             nratio=" + (ntotal / (this._n1 * this._n2 * this._n3)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void solveParallel(final ActiveList al, final float[][][] t, final int m, final float[][][] times, final int[][][] marks) {
/* 529 */     int nthread = Runtime.getRuntime().availableProcessors();
/* 530 */     ExecutorService es = Executors.newFixedThreadPool(nthread);
/* 531 */     CompletionService<Void> cs = new ExecutorCompletionService<>(es);
/* 532 */     ActiveList[] bl = new ActiveList[nthread];
/* 533 */     float[][] d = new float[nthread][];
/* 534 */     for (int ithread = 0; ithread < nthread; ithread++) {
/* 535 */       bl[ithread] = new ActiveList();
/* 536 */       d[ithread] = new float[6];
/*     */     } 
/* 538 */     final AtomicInteger ai = new AtomicInteger();
/*     */ 
/*     */     
/* 541 */     while (!al.isEmpty()) {
/* 542 */       ai.set(0);
/* 543 */       final int n = al.size();
/*     */       
/* 545 */       int mb = 32;
/* 546 */       final int nb = 1 + (n - 1) / 32;
/* 547 */       int ntask = ArrayMath.min(nb, nthread); int itask;
/* 548 */       for (itask = 0; itask < ntask; itask++) {
/* 549 */         final ActiveList bltask = bl[itask];
/* 550 */         final float[] dtask = d[itask];
/* 551 */         cs.submit(new Callable<Void>() {
/*     */               public Void call() {
/* 553 */                 for (int ib = ai.getAndIncrement(); ib < nb; ib = ai.getAndIncrement()) {
/* 554 */                   int i = ib * 32;
/* 555 */                   int j = ArrayMath.min(i + 32, n);
/* 556 */                   for (int k = i; k < j; k++) {
/* 557 */                     TimeMarker3X.Sample s = al.get(k);
/* 558 */                     TimeMarker3X.this.solveOne(t, m, times, marks, s, bltask, dtask);
/*     */                   } 
/*     */                 } 
/* 561 */                 bltask.setAllAbsent();
/* 562 */                 return null;
/*     */               }
/*     */             });
/*     */       } 
/*     */       try {
/* 567 */         for (itask = 0; itask < ntask; itask++)
/* 568 */           cs.take(); 
/* 569 */       } catch (InterruptedException e) {
/* 570 */         throw new RuntimeException(e);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 576 */       al.clear();
/* 577 */       for (itask = 0; itask < ntask; itask++) {
/* 578 */         al.appendIfAbsent(bl[itask]);
/* 579 */         bl[itask].clear();
/*     */       } 
/*     */     } 
/*     */     
/* 583 */     es.shutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float currentTime(float[][][] t, int i1, int i2, int i3) {
/* 593 */     return wasActivated(this._s[i3][i2][i1]) ? t[i3][i2][i1] : Float.MAX_VALUE;
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
/*     */   private void solveOne(float[][][] t, int m, float[][][] times, int[][][] marks, Sample s, ActiveList bl, float[] d) {
/* 605 */     int i1 = s.i1;
/* 606 */     int i2 = s.i2;
/* 607 */     int i3 = s.i3;
/*     */ 
/*     */     
/* 610 */     float ti = currentTime(t, i1, i2, i3);
/* 611 */     float ci = computeTime(t, i1, i2, i3, K1S[6], K2S[6], K3S[6], d);
/* 612 */     t[i3][i2][i1] = ci;
/*     */ 
/*     */     
/* 615 */     if (ci >= ti * 0.999F) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 621 */       boolean checkNabors = (ci <= 1.5F * times[i3][i2][i1]);
/*     */ 
/*     */       
/* 624 */       if (ci < times[i3][i2][i1]) {
/* 625 */         times[i3][i2][i1] = ci;
/* 626 */         marks[i3][i2][i1] = m;
/*     */       } 
/*     */ 
/*     */       
/* 630 */       if (checkNabors)
/*     */       {
/*     */         
/* 633 */         for (int k = 0; k < 6; k++) {
/*     */ 
/*     */           
/* 636 */           int j1 = i1 + K1[k]; if (j1 >= 0 && j1 < this._n1) {
/* 637 */             int j2 = i2 + K2[k]; if (j2 >= 0 && j2 < this._n2) {
/* 638 */               int j3 = i3 + K3[k]; if (j3 >= 0 && j3 < this._n3) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 644 */                 float tj = currentTime(t, j1, j2, j3);
/* 645 */                 float cj = computeTime(t, j1, j2, j3, K1S[k], K2S[k], K3S[k], d);
/*     */ 
/*     */                 
/* 648 */                 if (cj < tj * 0.999F) {
/*     */ 
/*     */                   
/* 651 */                   t[j3][j2][j1] = cj;
/*     */ 
/*     */                   
/* 654 */                   bl.append(this._s[j3][j2][j1]);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } else {
/* 662 */       bl.append(s);
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
/*     */ 
/*     */   
/*     */   private float t1m(float[][][] t, int i1, int i2, int i3) {
/* 687 */     return (--i1 >= 0 && wasActivated(this._s[i3][i2][i1])) ? t[i3][i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t1p(float[][][] t, int i1, int i2, int i3) {
/* 690 */     return (++i1 < this._n1 && wasActivated(this._s[i3][i2][i1])) ? t[i3][i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t2m(float[][][] t, int i1, int i2, int i3) {
/* 693 */     return (--i2 >= 0 && wasActivated(this._s[i3][i2][i1])) ? t[i3][i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t2p(float[][][] t, int i1, int i2, int i3) {
/* 696 */     return (++i2 < this._n2 && wasActivated(this._s[i3][i2][i1])) ? t[i3][i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t3m(float[][][] t, int i1, int i2, int i3) {
/* 699 */     return (--i3 >= 0 && wasActivated(this._s[i3][i2][i1])) ? t[i3][i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t3p(float[][][] t, int i1, int i2, int i3) {
/* 702 */     return (++i3 < this._n3 && wasActivated(this._s[i3][i2][i1])) ? t[i3][i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float computeTime(float[][][] t, int i1, int i2, int i3, int[] k1s, int[] k2s, int[] k3s, float[] d) {
/* 713 */     this._tensors.getTensor(i1, i2, i3, d);
/* 714 */     float d11 = d[0];
/* 715 */     float d12 = d[1];
/* 716 */     float d13 = d[2];
/* 717 */     float d22 = d[3];
/* 718 */     float d23 = d[4];
/* 719 */     float d33 = d[5];
/* 720 */     float o11 = 1.0F / d11;
/* 721 */     float o22 = 1.0F / d22;
/* 722 */     float o33 = 1.0F / d33;
/* 723 */     float d1212 = d12 * d12;
/* 724 */     float d1213 = d12 * d13;
/* 725 */     float d1223 = d12 * d23;
/* 726 */     float d1313 = d13 * d13;
/* 727 */     float d1323 = d13 * d23;
/* 728 */     float d2323 = d23 * d23;
/* 729 */     float a11 = d11 - d1313 * o33;
/* 730 */     float a12 = d12 - d1323 * o33;
/* 731 */     float a22 = d22 - d2323 * o33;
/* 732 */     float b11 = d11 - d1212 * o22;
/* 733 */     float b13 = d13 - d1223 * o22;
/* 734 */     float b33 = d33 - d2323 * o22;
/* 735 */     float c22 = d22 - d1212 * o11;
/* 736 */     float c23 = d23 - d1213 * o11;
/* 737 */     float c33 = d33 - d1313 * o11;
/* 738 */     float e12 = 1.0F / (a11 * a22 - a12 * a12);
/* 739 */     float e13 = 1.0F / (b11 * b33 - b13 * b13);
/* 740 */     float tc = currentTime(t, i1, i2, i3);
/* 741 */     float t1m = t1m(t, i1, i2, i3);
/* 742 */     float t1p = t1p(t, i1, i2, i3);
/* 743 */     float t2m = t2m(t, i1, i2, i3);
/* 744 */     float t2p = t2p(t, i1, i2, i3);
/* 745 */     float t3m = t3m(t, i1, i2, i3);
/* 746 */     float t3p = t3p(t, i1, i2, i3);
/* 747 */     for (int k = 0; k < k1s.length; k++) {
/* 748 */       float t0; int k1 = k1s[k];
/* 749 */       int k2 = k2s[k];
/* 750 */       int k3 = k3s[k];
/*     */       
/* 752 */       if (k1 != 0 && k2 != 0 && k3 != 0) {
/* 753 */         float t1 = (k1 < 0) ? t1m : t1p; if (t1 == Float.MAX_VALUE)
/* 754 */           continue;  float t2 = (k2 < 0) ? t2m : t2p; if (t2 == Float.MAX_VALUE)
/* 755 */           continue;  float t3 = (k3 < 0) ? t3m : t3p; if (t3 == Float.MAX_VALUE)
/* 756 */           continue;  t0 = computeTime(d11, d12, d13, d22, d23, d33, k1, k2, k3, t1, t2, t3);
/* 757 */       } else if (k1 != 0 && k2 != 0) {
/* 758 */         float t1 = (k1 < 0) ? t1m : t1p; if (t1 == Float.MAX_VALUE)
/* 759 */           continue;  float t2 = (k2 < 0) ? t2m : t2p; if (t2 == Float.MAX_VALUE)
/* 760 */           continue;  t0 = computeTime(a11, a12, a22, k1, k2, t1, t2);
/* 761 */       } else if (k1 != 0 && k3 != 0) {
/* 762 */         float t1 = (k1 < 0) ? t1m : t1p; if (t1 == Float.MAX_VALUE)
/* 763 */           continue;  float t3 = (k3 < 0) ? t3m : t3p; if (t3 == Float.MAX_VALUE)
/* 764 */           continue;  t0 = computeTime(b11, b13, b33, k1, k3, t1, t3);
/* 765 */       } else if (k2 != 0 && k3 != 0) {
/* 766 */         float t2 = (k2 < 0) ? t2m : t2p; if (t2 == Float.MAX_VALUE)
/* 767 */           continue;  float t3 = (k3 < 0) ? t3m : t3p; if (t3 == Float.MAX_VALUE)
/* 768 */           continue;  t0 = computeTime(c22, c23, c33, k2, k3, t2, t3);
/* 769 */       } else if (k1 != 0) {
/* 770 */         float t1 = (k1 < 0) ? t1m : t1p; if (t1 == Float.MAX_VALUE)
/* 771 */           continue;  t0 = t1 + ArrayMath.sqrt(a22 * e12);
/* 772 */       } else if (k2 != 0) {
/* 773 */         float t2 = (k2 < 0) ? t2m : t2p; if (t2 == Float.MAX_VALUE)
/* 774 */           continue;  t0 = t2 + ArrayMath.sqrt(a11 * e12);
/*     */       } else {
/* 776 */         float t3 = (k3 < 0) ? t3m : t3p; if (t3 == Float.MAX_VALUE)
/* 777 */           continue;  t0 = t3 + ArrayMath.sqrt(b11 * e13);
/*     */       } 
/* 779 */       if (t0 < tc)
/* 780 */         return t0;  continue;
/*     */     } 
/* 782 */     return tc;
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
/*     */   private static float computeTime(float d11, float d12, float d13, float d22, float d23, float d33, float s1, float s2, float s3, float t1, float t2, float t3) {
/* 809 */     double ds11 = (d11 * s1 * s1);
/* 810 */     double ds22 = (d22 * s2 * s2);
/* 811 */     double ds33 = (d33 * s3 * s3);
/* 812 */     double ds12 = (d12 * s1 * s2);
/* 813 */     double ds13 = (d13 * s1 * s3);
/* 814 */     double ds23 = (d23 * s2 * s3);
/* 815 */     double t12 = (t1 - t2);
/* 816 */     double t13 = (t1 - t3);
/* 817 */     double a = ds11 + ds22 + ds33 + 2.0D * (ds12 + ds13 + ds23);
/* 818 */     double b = 2.0D * ((ds22 + ds12 + ds23) * t12 + (ds33 + ds13 + ds23) * t13);
/* 819 */     double c = ds22 * t12 * t12 + ds33 * t13 * t13 + 2.0D * ds23 * t12 * t13 - 1.0D;
/* 820 */     double d = b * b - 4.0D * a * c;
/* 821 */     if (d < 0.0D)
/* 822 */       return Float.MAX_VALUE; 
/* 823 */     double u1 = (-b + ArrayMath.sqrt(d)) / 2.0D * a;
/* 824 */     double u2 = u1 + t12;
/* 825 */     double u3 = u1 + t13;
/* 826 */     if (ds11 * u1 + ds12 * u2 + ds13 * u3 < 0.0D || ds12 * u1 + ds22 * u2 + ds23 * u3 < 0.0D || ds13 * u1 + ds23 * u2 + ds33 * u3 < 0.0D)
/*     */     {
/*     */       
/* 829 */       return Float.MAX_VALUE; } 
/* 830 */     return t1 + (float)u1;
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
/* 851 */     double ds11 = (d11 * s1 * s1);
/* 852 */     double ds12 = (d12 * s1 * s2);
/* 853 */     double ds22 = (d22 * s2 * s2);
/* 854 */     double t12 = (t1 - t2);
/* 855 */     double a = ds11 + 2.0D * ds12 + ds22;
/* 856 */     double b = 2.0D * (ds12 + ds22) * t12;
/* 857 */     double c = ds22 * t12 * t12 - 1.0D;
/* 858 */     double d = b * b - 4.0D * a * c;
/* 859 */     if (d < 0.0D)
/* 860 */       return Float.MAX_VALUE; 
/* 861 */     double u1 = (-b + ArrayMath.sqrt(d)) / 2.0D * a;
/* 862 */     double u2 = u1 + t12;
/* 863 */     if (ds11 * u1 + ds12 * u2 < 0.0D || ds12 * u1 + ds22 * u2 < 0.0D)
/*     */     {
/* 865 */       return Float.MAX_VALUE; } 
/* 866 */     return t1 + (float)u1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void trace(String s) {
/* 873 */     System.out.println(s);
/*     */   }
/*     */   private static float[][][] toFloat(int[][][] i) {
/* 876 */     int n1 = (i[0][0]).length;
/* 877 */     int n2 = (i[0]).length;
/* 878 */     int n3 = i.length;
/* 879 */     float[][][] f = new float[n3][n2][n1];
/* 880 */     for (int i3 = 0; i3 < n3; i3++) {
/* 881 */       for (int i2 = 0; i2 < n2; i2++)
/* 882 */       { for (int i1 = 0; i1 < n1; i1++)
/* 883 */           f[i3][i2][i1] = i[i3][i2][i1];  } 
/* 884 */     }  return f;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/TimeMarker3X.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */