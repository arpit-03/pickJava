/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.dsp.Tensors3;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Parallel;
/*     */ import edu.mines.jtk.util.Stopwatch;
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
/*     */ public class TimeMarker3
/*     */ {
/*     */   public enum Concurrency
/*     */   {
/*  65 */     PARALLELX,
/*  66 */     PARALLEL,
/*  67 */     SERIAL;
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
/*     */     log.fine("TimeMarker3.apply: begin time=" + (int)sw.time());
/*     */     for (int i3 = 0; i3 < this._n3; i3++) {
/*     */       for (int i2 = 0; i2 < this._n2; i2++) {
/*     */         for (int i1 = 0; i1 < this._n1; i1++) {
/*     */           if (times[i3][i2][i1] != 0.0F) {
/*     */             times[i3][i2][i1] = Float.MAX_VALUE;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     short[][] kk = indexKnownSamples(times);
/*     */     short[] k1 = kk[0];
/*     */     short[] k2 = kk[1];
/*     */     short[] k3 = kk[2];
/*     */     shuffle(k1, k2, k3);
/*     */     int nk = k1.length;
/*     */     float[][][] t = new float[this._n3][this._n2][this._n1];
/*     */     ActiveList al = new ActiveList();
/*     */     for (int ik = 0; ik < nk; ik++) {
/*     */       if (ik % (1 + (nk - 1) / 100) == 0) {
/*     */         log.fine("  apply: ik/nk=" + ik + "/" + nk + " time=" + (int)sw.time());
/*     */       }
/*     */       int i1 = k1[ik];
/*     */       int i2 = k2[ik];
/*     */       int i = k3[ik];
/*     */       clearActivated();
/*     */       t[i][i2][i1] = 0.0F;
/*     */       al.append(this._s[i][i2][i1]);
/*     */       int m = marks[i][i2][i1];
/*     */       solve(al, t, m, times, marks);
/*     */     } 
/*     */     sw.stop();
/*     */     log.fine("TimeMarker3.apply: end time=" + (int)sw.time());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void solve(ActiveList al, float[][][] t, int m, float[][][] times, int[][][] marks) {
/*     */     if (this._concurrency == Concurrency.PARALLEL) {
/*     */       solveParallel(al, t, m, times, marks);
/*     */     } else if (this._concurrency == Concurrency.PARALLELX) {
/*     */       solveParallelX(al, t, m, times, marks);
/*     */     } else {
/*     */       solveSerial(al, t, m, times, marks);
/*     */     } 
/*     */   }
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
/*     */   private static final float INFINITY = 3.4028235E38F;
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
/*     */   
/*     */   public TimeMarker3(int n1, int n2, int n3, Tensors3 tensors) {
/* 192 */     this._concurrency = Concurrency.PARALLEL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 328 */     this._activated = 1; init(n1, n2, n3, tensors);
/*     */   } private Sample[][][] _s; private Concurrency _concurrency; private void init(int n1, int n2, int n3, Tensors3 tensors) { this._n1 = n1; this._n2 = n2; this._n3 = n3; this._tensors = tensors; this._s = new Sample[n3][n2][n1]; for (int i3 = 0; i3 < n3; i3++) { for (int i2 = 0; i2 < n2; i2++) { for (int i1 = 0; i1 < n1; i1++) this._s[i3][i2][i1] = new Sample(i1, i2, i3);  }  }  } private static final int[] K1 = new int[] { -1, 1, 0, 0, 0, 0 }; private static final int[] K2 = new int[] { 0, 0, -1, 1, 0, 0 }; private static final int[] K3 = new int[] { 0, 0, 0, 0, -1, 1 }; private static final int[][] K1S = new int[][] { { 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, 1, -1, 1, -1, 1, 0, 0, 0 }, { -1, 1, -1, 1, -1, 1, 0, 0, 0 }, { -1, -1, 1, 1, 0, 0, -1, 1, 0 }, { -1, -1, 1, 1, 0, 0, -1, 1, 0 }, { -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0 } }; private static final int[][] K2S = new int[][] { { -1, -1, 1, 1, 0, 0, -1, 1, 0 }, { -1, -1, 1, 1, 0, 0, -1, 1, 0 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, 1, -1, 1, -1, 1, 0, 0, 0 }, { -1, 1, -1, 1, -1, 1, 0, 0, 0 }, { -1, -1, 1, 1, -1, -1, 1, 1, -1, -1, 1, 1, 0, 0, 0, 0, -1, 1, -1, 1, 0, 0, -1, 1, 0, 0 } }; private static final int[][] K3S = new int[][] { { -1, 1, -1, 1, -1, 1, 0, 0, 0 }, { -1, 1, -1, 1, -1, 1, 0, 0, 0 }, { -1, -1, 1, 1, 0, 0, -1, 1, 0 }, { -1, -1, 1, 1, 0, 0, -1, 1, 0 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, 1, 1, 1, 1, 0, 0, 0, 0, -1, -1, 1, 1, -1, -1, 1, 1, 0, 0, 0, 0, -1, 1 } }; private int _activated; private static class Sample {
/* 330 */     int i1; int i2; int i3; int activated; boolean absent; Sample(int i1, int i2, int i3) { this.i1 = i1; this.i2 = i2; this.i3 = i3; } } private class ActiveList { private int _n; void append(TimeMarker3.Sample s) { s.activated = TimeMarker3.this._activated; if (this._n == this._a.length) growTo(2 * this._n);  this._a[this._n++] = s; } boolean isEmpty() { return (this._n == 0); } int size() { return this._n; } TimeMarker3.Sample get(int i) { return this._a[i]; } void clear() { this._n = 0; } void setAllAbsent() { for (int i = 0; i < this._n; i++) (this._a[i]).absent = true;  } void appendIfAbsent(ActiveList al) { if (this._n + al._n > this._a.length) growTo(2 * (this._n + al._n));  int n = al._n; for (int i = 0; i < n; i++) { TimeMarker3.Sample s = al.get(i); if (s.absent) { this._a[this._n++] = s; s.absent = false; }  }  } void shuffle() { Random r = new Random(); for (int i = 0; i < this._n; i++) { int j = r.nextInt(this._n); int k = r.nextInt(this._n); TimeMarker3.Sample aj = this._a[j]; this._a[j] = this._a[k]; this._a[k] = aj; }  } void dump() { TimeMarker3.trace("ActiveList.dump: n=" + this._n); for (int i = 0; i < this._n; i++) { TimeMarker3.Sample s = this._a[i]; TimeMarker3.trace(" s[" + i + "] = (" + s.i1 + "," + s.i2 + "," + s.i3 + ")"); }  } private TimeMarker3.Sample[] _a = new TimeMarker3.Sample[1024]; private void growTo(int capacity) { TimeMarker3.Sample[] a = new TimeMarker3.Sample[capacity]; System.arraycopy(this._a, 0, a, 0, this._n); this._a = a; } private ActiveList() {} } private void clearActivated() { if (this._activated == Integer.MAX_VALUE) {
/* 331 */       this._activated = 1;
/* 332 */       for (int i3 = 0; i3 < this._n3; i3++) {
/* 333 */         for (int i2 = 0; i2 < this._n2; i2++) {
/* 334 */           for (int i1 = 0; i1 < this._n1; i1++) {
/* 335 */             (this._s[i3][i2][i1]).activated = 0;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/* 340 */       this._activated++;
/*     */     }  }
/*     */   
/*     */   private void setActivated(Sample s) {
/* 344 */     s.activated = this._activated;
/*     */   }
/*     */   private void clearActivated(Sample s) {
/* 347 */     s.activated = 0;
/*     */   }
/*     */   private boolean wasActivated(Sample s) {
/* 350 */     return (s.activated == this._activated);
/*     */   }
/*     */   
/*     */   private static class ShortStack
/*     */   {
/*     */     void push(int k) {
/* 356 */       if (this._n == this._a.length) {
/* 357 */         short[] a = new short[2 * this._n];
/* 358 */         System.arraycopy(this._a, 0, a, 0, this._n);
/* 359 */         this._a = a;
/*     */       } 
/* 361 */       this._a[this._n++] = (short)k;
/*     */     }
/*     */     short pop() {
/* 364 */       return this._a[--this._n];
/*     */     }
/*     */     int size() {
/* 367 */       return this._n;
/*     */     }
/*     */     void clear() {
/* 370 */       this._n = 0;
/*     */     }
/*     */     boolean isEmpty() {
/* 373 */       return (this._n == 0);
/*     */     }
/*     */     short[] array() {
/* 376 */       short[] a = new short[this._n];
/* 377 */       System.arraycopy(this._a, 0, a, 0, this._n);
/* 378 */       return a;
/*     */     }
/* 380 */     private int _n = 0;
/* 381 */     private short[] _a = new short[2048];
/*     */ 
/*     */ 
/*     */     
/*     */     private ShortStack() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private short[][] indexKnownSamples(float[][][] times) {
/* 390 */     ShortStack ss1 = new ShortStack();
/* 391 */     ShortStack ss2 = new ShortStack();
/* 392 */     ShortStack ss3 = new ShortStack();
/* 393 */     for (int i3 = 0; i3 < this._n3; i3++) {
/* 394 */       for (int i = 0; i < this._n2; i++) {
/* 395 */         for (int j = 0; j < this._n1; j++) {
/* 396 */           if (times[i3][i][j] == 0.0F)
/* 397 */             for (int k = 0; k < 6; k++) {
/* 398 */               int j1 = j + K1[k]; if (j1 >= 0 && j1 < this._n1) {
/* 399 */                 int j2 = i + K2[k]; if (j2 >= 0 && j2 < this._n2) {
/* 400 */                   int j3 = i3 + K3[k]; if (j3 >= 0 && j3 < this._n3 && 
/* 401 */                     times[j3][j2][j1] != 0.0F) {
/* 402 */                     ss1.push(j);
/* 403 */                     ss2.push(i);
/* 404 */                     ss3.push(i3); break;
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             }  
/*     */         } 
/*     */       } 
/*     */     } 
/* 412 */     short[] i1 = ss1.array();
/* 413 */     short[] i2 = ss2.array();
/* 414 */     short[] arrayOfShort1 = ss3.array();
/* 415 */     return new short[][] { i1, i2, arrayOfShort1 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void shuffle(short[] i1, short[] i2, short[] i3) {
/* 422 */     int n = i1.length;
/* 423 */     Random r = new Random(314159L);
/*     */     
/* 425 */     for (int i = n - 1; i > 0; i--) {
/* 426 */       int j = r.nextInt(i + 1);
/* 427 */       short ii = i1[i]; i1[i] = i1[j]; i1[j] = ii;
/* 428 */       ii = i2[i]; i2[i] = i2[j]; i2[j] = ii;
/* 429 */       ii = i3[i]; i3[i] = i3[j]; i3[j] = ii;
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
/* 441 */     float[] d = new float[6];
/* 442 */     ActiveList bl = new ActiveList();
/* 443 */     int ntotal = 0;
/* 444 */     while (!al.isEmpty()) {
/*     */       
/* 446 */       int n = al.size();
/* 447 */       ntotal += n;
/* 448 */       for (int i = 0; i < n; i++) {
/* 449 */         Sample s = al.get(i);
/* 450 */         solveOne(t, m, times, marks, s, bl, d);
/*     */       } 
/* 452 */       bl.setAllAbsent();
/* 453 */       al.clear();
/* 454 */       al.appendIfAbsent(bl);
/* 455 */       bl.clear();
/*     */     } 
/* 457 */     trace("solveSerial: ntotal=" + ntotal);
/* 458 */     trace("             nratio=" + (ntotal / (this._n1 * this._n2 * this._n3)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void solveParallelX(final ActiveList al, final float[][][] t, final int m, final float[][][] times, final int[][][] marks) {
/* 469 */     int nthread = Runtime.getRuntime().availableProcessors();
/* 470 */     ExecutorService es = Executors.newFixedThreadPool(nthread);
/* 471 */     CompletionService<Void> cs = new ExecutorCompletionService<>(es);
/* 472 */     ActiveList[] bl = new ActiveList[nthread];
/* 473 */     float[][] d = new float[nthread][];
/* 474 */     for (int ithread = 0; ithread < nthread; ithread++) {
/* 475 */       bl[ithread] = new ActiveList();
/* 476 */       d[ithread] = new float[6];
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
/* 497 */                     TimeMarker3.Sample s = al.get(k);
/* 498 */                     TimeMarker3.this.solveOne(t, m, times, marks, s, bltask, dtask);
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
/* 523 */     es.shutdown();
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
/*     */   private void solveParallel(final ActiveList al, final float[][][] t, final int m, final float[][][] times, final int[][][] marks) {
/* 536 */     int mbmin = 64;
/* 537 */     int nbmax = 256;
/* 538 */     final float[][] dtask = new float[nbmax][];
/* 539 */     final ActiveList[] bltask = new ActiveList[nbmax];
/* 540 */     while (!al.isEmpty()) {
/* 541 */       final int n = al.size();
/* 542 */       int mbmax = ArrayMath.max(mbmin, 1 + (n - 1) / nbmax);
/* 543 */       int nb = 1 + (n - 1) / mbmax;
/* 544 */       final int mb = 1 + (n - 1) / nb;
/* 545 */       Parallel.loop(nb, new Parallel.LoopInt() {
/*     */             public void compute(int ib) {
/* 547 */               if (bltask[ib] == null) {
/* 548 */                 dtask[ib] = new float[6];
/* 549 */                 bltask[ib] = new TimeMarker3.ActiveList();
/*     */               } 
/* 551 */               int i = ib * mb;
/* 552 */               int j = ArrayMath.min(i + mb, n);
/* 553 */               for (int k = i; k < j; k++) {
/* 554 */                 TimeMarker3.Sample s = al.get(k);
/* 555 */                 TimeMarker3.this.solveOne(t, m, times, marks, s, bltask[ib], dtask[ib]);
/*     */               } 
/* 557 */               bltask[ib].setAllAbsent();
/*     */             }
/*     */           });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 565 */       al.clear();
/* 566 */       for (int ib = 0; ib < nb; ib++) {
/* 567 */         if (bltask[ib] != null) {
/* 568 */           al.appendIfAbsent(bltask[ib]);
/* 569 */           bltask[ib].clear();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float currentTime(float[][][] t, int i1, int i2, int i3) {
/* 580 */     return wasActivated(this._s[i3][i2][i1]) ? t[i3][i2][i1] : Float.MAX_VALUE;
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
/* 592 */     int i1 = s.i1;
/* 593 */     int i2 = s.i2;
/* 594 */     int i3 = s.i3;
/*     */ 
/*     */     
/* 597 */     float ti = currentTime(t, i1, i2, i3);
/* 598 */     float ci = computeTime(t, i1, i2, i3, K1S[6], K2S[6], K3S[6], d);
/* 599 */     t[i3][i2][i1] = ci;
/*     */ 
/*     */     
/* 602 */     if (ci >= ti * 0.999F) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 608 */       boolean checkNabors = (ci <= 1.5F * times[i3][i2][i1]);
/*     */ 
/*     */       
/* 611 */       if (ci < times[i3][i2][i1]) {
/* 612 */         times[i3][i2][i1] = ci;
/* 613 */         marks[i3][i2][i1] = m;
/*     */       } 
/*     */ 
/*     */       
/* 617 */       if (checkNabors)
/*     */       {
/*     */         
/* 620 */         for (int k = 0; k < 6; k++) {
/*     */ 
/*     */           
/* 623 */           int j1 = i1 + K1[k]; if (j1 >= 0 && j1 < this._n1) {
/* 624 */             int j2 = i2 + K2[k]; if (j2 >= 0 && j2 < this._n2) {
/* 625 */               int j3 = i3 + K3[k]; if (j3 >= 0 && j3 < this._n3) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 631 */                 float tj = currentTime(t, j1, j2, j3);
/* 632 */                 float cj = computeTime(t, j1, j2, j3, K1S[k], K2S[k], K3S[k], d);
/*     */ 
/*     */                 
/* 635 */                 if (cj < tj * 0.999F) {
/*     */ 
/*     */                   
/* 638 */                   t[j3][j2][j1] = cj;
/*     */ 
/*     */                   
/* 641 */                   bl.append(this._s[j3][j2][j1]);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } else {
/* 649 */       bl.append(s);
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
/* 674 */     return (--i1 >= 0 && wasActivated(this._s[i3][i2][i1])) ? t[i3][i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t1p(float[][][] t, int i1, int i2, int i3) {
/* 677 */     return (++i1 < this._n1 && wasActivated(this._s[i3][i2][i1])) ? t[i3][i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t2m(float[][][] t, int i1, int i2, int i3) {
/* 680 */     return (--i2 >= 0 && wasActivated(this._s[i3][i2][i1])) ? t[i3][i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t2p(float[][][] t, int i1, int i2, int i3) {
/* 683 */     return (++i2 < this._n2 && wasActivated(this._s[i3][i2][i1])) ? t[i3][i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t3m(float[][][] t, int i1, int i2, int i3) {
/* 686 */     return (--i3 >= 0 && wasActivated(this._s[i3][i2][i1])) ? t[i3][i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t3p(float[][][] t, int i1, int i2, int i3) {
/* 689 */     return (++i3 < this._n3 && wasActivated(this._s[i3][i2][i1])) ? t[i3][i2][i1] : Float.MAX_VALUE;
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
/* 700 */     this._tensors.getTensor(i1, i2, i3, d);
/* 701 */     float d11 = d[0];
/* 702 */     float d12 = d[1];
/* 703 */     float d13 = d[2];
/* 704 */     float d22 = d[3];
/* 705 */     float d23 = d[4];
/* 706 */     float d33 = d[5];
/* 707 */     float o11 = 1.0F / d11;
/* 708 */     float o22 = 1.0F / d22;
/* 709 */     float o33 = 1.0F / d33;
/* 710 */     float d1212 = d12 * d12;
/* 711 */     float d1213 = d12 * d13;
/* 712 */     float d1223 = d12 * d23;
/* 713 */     float d1313 = d13 * d13;
/* 714 */     float d1323 = d13 * d23;
/* 715 */     float d2323 = d23 * d23;
/* 716 */     float a11 = d11 - d1313 * o33;
/* 717 */     float a12 = d12 - d1323 * o33;
/* 718 */     float a22 = d22 - d2323 * o33;
/* 719 */     float b11 = d11 - d1212 * o22;
/* 720 */     float b13 = d13 - d1223 * o22;
/* 721 */     float b33 = d33 - d2323 * o22;
/* 722 */     float c22 = d22 - d1212 * o11;
/* 723 */     float c23 = d23 - d1213 * o11;
/* 724 */     float c33 = d33 - d1313 * o11;
/* 725 */     float e12 = 1.0F / (a11 * a22 - a12 * a12);
/* 726 */     float e13 = 1.0F / (b11 * b33 - b13 * b13);
/* 727 */     float tc = currentTime(t, i1, i2, i3);
/* 728 */     float t1m = t1m(t, i1, i2, i3);
/* 729 */     float t1p = t1p(t, i1, i2, i3);
/* 730 */     float t2m = t2m(t, i1, i2, i3);
/* 731 */     float t2p = t2p(t, i1, i2, i3);
/* 732 */     float t3m = t3m(t, i1, i2, i3);
/* 733 */     float t3p = t3p(t, i1, i2, i3);
/* 734 */     for (int k = 0; k < k1s.length; k++) {
/* 735 */       float t0; int k1 = k1s[k];
/* 736 */       int k2 = k2s[k];
/* 737 */       int k3 = k3s[k];
/*     */       
/* 739 */       if (k1 != 0 && k2 != 0 && k3 != 0) {
/* 740 */         float t1 = (k1 < 0) ? t1m : t1p; if (t1 == Float.MAX_VALUE)
/* 741 */           continue;  float t2 = (k2 < 0) ? t2m : t2p; if (t2 == Float.MAX_VALUE)
/* 742 */           continue;  float t3 = (k3 < 0) ? t3m : t3p; if (t3 == Float.MAX_VALUE)
/* 743 */           continue;  t0 = computeTime(d11, d12, d13, d22, d23, d33, k1, k2, k3, t1, t2, t3);
/* 744 */       } else if (k1 != 0 && k2 != 0) {
/* 745 */         float t1 = (k1 < 0) ? t1m : t1p; if (t1 == Float.MAX_VALUE)
/* 746 */           continue;  float t2 = (k2 < 0) ? t2m : t2p; if (t2 == Float.MAX_VALUE)
/* 747 */           continue;  t0 = computeTime(a11, a12, a22, k1, k2, t1, t2);
/* 748 */       } else if (k1 != 0 && k3 != 0) {
/* 749 */         float t1 = (k1 < 0) ? t1m : t1p; if (t1 == Float.MAX_VALUE)
/* 750 */           continue;  float t3 = (k3 < 0) ? t3m : t3p; if (t3 == Float.MAX_VALUE)
/* 751 */           continue;  t0 = computeTime(b11, b13, b33, k1, k3, t1, t3);
/* 752 */       } else if (k2 != 0 && k3 != 0) {
/* 753 */         float t2 = (k2 < 0) ? t2m : t2p; if (t2 == Float.MAX_VALUE)
/* 754 */           continue;  float t3 = (k3 < 0) ? t3m : t3p; if (t3 == Float.MAX_VALUE)
/* 755 */           continue;  t0 = computeTime(c22, c23, c33, k2, k3, t2, t3);
/* 756 */       } else if (k1 != 0) {
/* 757 */         float t1 = (k1 < 0) ? t1m : t1p; if (t1 == Float.MAX_VALUE)
/* 758 */           continue;  t0 = t1 + ArrayMath.sqrt(a22 * e12);
/* 759 */       } else if (k2 != 0) {
/* 760 */         float t2 = (k2 < 0) ? t2m : t2p; if (t2 == Float.MAX_VALUE)
/* 761 */           continue;  t0 = t2 + ArrayMath.sqrt(a11 * e12);
/*     */       } else {
/* 763 */         float t3 = (k3 < 0) ? t3m : t3p; if (t3 == Float.MAX_VALUE)
/* 764 */           continue;  t0 = t3 + ArrayMath.sqrt(b11 * e13);
/*     */       } 
/* 766 */       if (t0 < tc)
/* 767 */         return t0;  continue;
/*     */     } 
/* 769 */     return tc;
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
/* 796 */     double ds11 = (d11 * s1 * s1);
/* 797 */     double ds22 = (d22 * s2 * s2);
/* 798 */     double ds33 = (d33 * s3 * s3);
/* 799 */     double ds12 = (d12 * s1 * s2);
/* 800 */     double ds13 = (d13 * s1 * s3);
/* 801 */     double ds23 = (d23 * s2 * s3);
/* 802 */     double t12 = (t1 - t2);
/* 803 */     double t13 = (t1 - t3);
/* 804 */     double a = ds11 + ds22 + ds33 + 2.0D * (ds12 + ds13 + ds23);
/* 805 */     double b = 2.0D * ((ds22 + ds12 + ds23) * t12 + (ds33 + ds13 + ds23) * t13);
/* 806 */     double c = ds22 * t12 * t12 + ds33 * t13 * t13 + 2.0D * ds23 * t12 * t13 - 1.0D;
/* 807 */     double d = b * b - 4.0D * a * c;
/* 808 */     if (d < 0.0D)
/* 809 */       return Float.MAX_VALUE; 
/* 810 */     double u1 = (-b + ArrayMath.sqrt(d)) / 2.0D * a;
/* 811 */     double u2 = u1 + t12;
/* 812 */     double u3 = u1 + t13;
/* 813 */     if (ds11 * u1 + ds12 * u2 + ds13 * u3 < 0.0D || ds12 * u1 + ds22 * u2 + ds23 * u3 < 0.0D || ds13 * u1 + ds23 * u2 + ds33 * u3 < 0.0D)
/*     */     {
/*     */       
/* 816 */       return Float.MAX_VALUE; } 
/* 817 */     return t1 + (float)u1;
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
/* 838 */     double ds11 = (d11 * s1 * s1);
/* 839 */     double ds12 = (d12 * s1 * s2);
/* 840 */     double ds22 = (d22 * s2 * s2);
/* 841 */     double t12 = (t1 - t2);
/* 842 */     double a = ds11 + 2.0D * ds12 + ds22;
/* 843 */     double b = 2.0D * (ds12 + ds22) * t12;
/* 844 */     double c = ds22 * t12 * t12 - 1.0D;
/* 845 */     double d = b * b - 4.0D * a * c;
/* 846 */     if (d < 0.0D)
/* 847 */       return Float.MAX_VALUE; 
/* 848 */     double u1 = (-b + ArrayMath.sqrt(d)) / 2.0D * a;
/* 849 */     double u2 = u1 + t12;
/* 850 */     if (ds11 * u1 + ds12 * u2 < 0.0D || ds12 * u1 + ds22 * u2 < 0.0D)
/*     */     {
/* 852 */       return Float.MAX_VALUE; } 
/* 853 */     return t1 + (float)u1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void trace(String s) {
/* 860 */     System.out.println(s);
/*     */   }
/*     */   private static float[][][] toFloat(int[][][] i) {
/* 863 */     int n1 = (i[0][0]).length;
/* 864 */     int n2 = (i[0]).length;
/* 865 */     int n3 = i.length;
/* 866 */     float[][][] f = new float[n3][n2][n1];
/* 867 */     for (int i3 = 0; i3 < n3; i3++) {
/* 868 */       for (int i2 = 0; i2 < n2; i2++)
/* 869 */       { for (int i1 = 0; i1 < n1; i1++)
/* 870 */           f[i3][i2][i1] = i[i3][i2][i1];  } 
/* 871 */     }  return f;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/TimeMarker3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */