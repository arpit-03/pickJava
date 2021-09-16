/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.awt.ColorMap;
/*     */ import edu.mines.jtk.dsp.Tensors2;
/*     */ import edu.mines.jtk.mosaic.PixelsView;
/*     */ import edu.mines.jtk.mosaic.SimplePlot;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Parallel;
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
/*     */ public class TimeMarker2
/*     */ {
/*     */   private static final float INFINITY = 3.4028235E38F;
/*     */   private static final float EPSILON = 0.001F;
/*     */   private static final float ONE_MINUS_EPSILON = 0.999F;
/*     */   private int _n1;
/*     */   private int _n2;
/*     */   private Tensors2 _tensors;
/*     */   private Sample[][] _s;
/*     */   private Concurrency _concurrency;
/*     */   
/*     */   public enum Concurrency
/*     */   {
/*  63 */     PARALLELX,
/*  64 */     PARALLEL,
/*  65 */     SERIAL;
/*     */   }
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
/*     */   public void apply(float[][] times, int[][] marks) {
/*     */     for (int i2 = 0; i2 < this._n2; i2++) {
/*     */       for (int i1 = 0; i1 < this._n1; i1++) {
/*     */         if (times[i2][i1] != 0.0F) {
/*     */           times[i2][i1] = Float.MAX_VALUE;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     short[][] kk = indexKnownSamples(times);
/*     */     short[] k1 = kk[0];
/*     */     short[] k2 = kk[1];
/*     */     shuffle(k1, k2);
/*     */     int nk = k1.length;
/*     */     float[][] t = new float[this._n2][this._n1];
/*     */     ActiveList al = new ActiveList();
/*     */     for (int ik = 0; ik < nk; ik++) {
/*     */       int i1 = k1[ik];
/*     */       int i = k2[ik];
/*     */       clearActivated();
/*     */       t[i][i1] = 0.0F;
/*     */       al.append(this._s[i][i1]);
/*     */       int m = marks[i][i1];
/*     */       solve(al, t, m, times, marks);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void solve(ActiveList al, float[][] t, int m, float[][] times, int[][] marks) {
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
/*     */   private static final int[] K1 = new int[] { -1, 1, 0, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeMarker2(int n1, int n2, Tensors2 tensors) {
/* 171 */     this._concurrency = Concurrency.PARALLEL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     this._activated = 1; init(n1, n2, tensors);
/*     */   } private static final int[] K2 = new int[] { 0, 0, -1, 1 }; private static final int[][] K1S = new int[][] { { 1, 1, 1 }, { -1, -1, -1 }, { -1, 1, 0 }, { -1, 1, 0 }, { -1, 1, -1, 1, -1, 1, 0, 0 } }; private static final int[][] K2S = new int[][] { { -1, 1, 0 }, { -1, 1, 0 }, { 1, 1, 1 }, { -1, -1, -1 }, { -1, -1, 1, 1, 0, 0, -1, 1 } }; private int _activated; private static class Sample {
/* 286 */     int i1; int i2; int activated; boolean absent; Sample(int i1, int i2) { this.i1 = i1; this.i2 = i2; } } private class ActiveList { private int _n; void append(TimeMarker2.Sample s) { s.activated = TimeMarker2.this._activated; if (this._n == this._a.length) growTo(2 * this._n);  this._a[this._n++] = s; } boolean isEmpty() { return (this._n == 0); } int size() { return this._n; } TimeMarker2.Sample get(int i) { return this._a[i]; } void clear() { this._n = 0; } void setAllAbsent() { for (int i = 0; i < this._n; i++) (this._a[i]).absent = true;  } void appendIfAbsent(ActiveList al) { if (this._n + al._n > this._a.length) growTo(2 * (this._n + al._n));  int n = al._n; for (int i = 0; i < n; i++) { TimeMarker2.Sample s = al.get(i); if (s.absent) { this._a[this._n++] = s; s.absent = false; }  }  } void shuffle() { Random r = new Random(); for (int i = 0; i < this._n; i++) { int j = r.nextInt(this._n); int k = r.nextInt(this._n); TimeMarker2.Sample aj = this._a[j]; this._a[j] = this._a[k]; this._a[k] = aj; }  } void dump() { TimeMarker2.trace("ActiveList.dump: n=" + this._n); for (int i = 0; i < this._n; i++) { TimeMarker2.Sample s = this._a[i]; TimeMarker2.trace(" s[" + i + "] = (" + s.i1 + "," + s.i2 + ")"); }  } private TimeMarker2.Sample[] _a = new TimeMarker2.Sample[128]; private void growTo(int capacity) { TimeMarker2.Sample[] a = new TimeMarker2.Sample[capacity]; System.arraycopy(this._a, 0, a, 0, this._n); this._a = a; } private ActiveList() {} } private void clearActivated() { if (this._activated == Integer.MAX_VALUE) {
/* 287 */       this._activated = 1;
/* 288 */       for (int i2 = 0; i2 < this._n2; i2++) {
/* 289 */         for (int i1 = 0; i1 < this._n1; i1++) {
/* 290 */           (this._s[i2][i1]).activated = 0;
/*     */         }
/*     */       } 
/*     */     } else {
/* 294 */       this._activated++;
/*     */     }  }
/*     */   
/*     */   private void setActivated(Sample s) {
/* 298 */     s.activated = this._activated;
/*     */   }
/*     */   private void clearActivated(Sample s) {
/* 301 */     s.activated = 0;
/*     */   }
/*     */   private boolean wasActivated(Sample s) {
/* 304 */     return (s.activated == this._activated);
/*     */   }
/*     */   
/*     */   private static class ShortStack
/*     */   {
/*     */     void push(int k) {
/* 310 */       if (this._n == this._a.length) {
/* 311 */         short[] a = new short[2 * this._n];
/* 312 */         System.arraycopy(this._a, 0, a, 0, this._n);
/* 313 */         this._a = a;
/*     */       } 
/* 315 */       this._a[this._n++] = (short)k;
/*     */     }
/*     */     short pop() {
/* 318 */       return this._a[--this._n];
/*     */     }
/*     */     int size() {
/* 321 */       return this._n;
/*     */     }
/*     */     void clear() {
/* 324 */       this._n = 0;
/*     */     }
/*     */     boolean isEmpty() {
/* 327 */       return (this._n == 0);
/*     */     }
/*     */     short[] array() {
/* 330 */       short[] a = new short[this._n];
/* 331 */       System.arraycopy(this._a, 0, a, 0, this._n);
/* 332 */       return a;
/*     */     }
/* 334 */     private int _n = 0;
/* 335 */     private short[] _a = new short[2048];
/*     */ 
/*     */ 
/*     */     
/*     */     private ShortStack() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private short[][] indexKnownSamples(float[][] times) {
/* 344 */     ShortStack ss1 = new ShortStack();
/* 345 */     ShortStack ss2 = new ShortStack();
/* 346 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 347 */       for (int i = 0; i < this._n1; i++) {
/* 348 */         if (times[i2][i] == 0.0F)
/* 349 */           for (int k = 0; k < 4; k++) {
/* 350 */             int j1 = i + K1[k]; if (j1 >= 0 && j1 < this._n1) {
/* 351 */               int j2 = i2 + K2[k]; if (j2 >= 0 && j2 < this._n2 && 
/* 352 */                 times[j2][j1] != 0.0F) {
/* 353 */                 ss1.push(i);
/* 354 */                 ss2.push(i2);
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           }  
/*     */       } 
/*     */     } 
/* 361 */     short[] i1 = ss1.array();
/* 362 */     short[] arrayOfShort1 = ss2.array();
/* 363 */     return new short[][] { i1, arrayOfShort1 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void shuffle(short[] i1, short[] i2) {
/* 370 */     int n = i1.length;
/* 371 */     Random r = new Random(314159L);
/*     */     
/* 373 */     for (int i = n - 1; i > 0; i--) {
/* 374 */       int j = r.nextInt(i + 1);
/* 375 */       short ii = i1[i]; i1[i] = i1[j]; i1[j] = ii;
/* 376 */       ii = i2[i]; i2[i] = i2[j]; i2[j] = ii;
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
/* 388 */     float[] d = new float[3];
/* 389 */     ActiveList bl = new ActiveList();
/*     */     
/* 391 */     while (!al.isEmpty()) {
/*     */       
/* 393 */       int n = al.size();
/*     */       
/* 395 */       for (int i = 0; i < n; i++) {
/* 396 */         Sample s = al.get(i);
/* 397 */         solveOne(t, m, times, marks, s, bl, d);
/*     */       } 
/* 399 */       bl.setAllAbsent();
/* 400 */       al.clear();
/* 401 */       al.appendIfAbsent(bl);
/* 402 */       bl.clear();
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
/*     */   private void solveParallelX(final ActiveList al, final float[][] t, final int m, final float[][] times, final int[][] marks) {
/* 416 */     int nthread = Runtime.getRuntime().availableProcessors();
/* 417 */     ExecutorService es = Executors.newFixedThreadPool(nthread);
/* 418 */     CompletionService<Void> cs = new ExecutorCompletionService<>(es);
/* 419 */     ActiveList[] bl = new ActiveList[nthread];
/* 420 */     float[][] d = new float[nthread][];
/* 421 */     for (int ithread = 0; ithread < nthread; ithread++) {
/* 422 */       bl[ithread] = new ActiveList();
/* 423 */       d[ithread] = new float[3];
/*     */     } 
/* 425 */     final AtomicInteger ai = new AtomicInteger();
/*     */ 
/*     */     
/* 428 */     while (!al.isEmpty()) {
/* 429 */       ai.set(0);
/* 430 */       final int n = al.size();
/*     */       
/* 432 */       int mb = 32;
/* 433 */       final int nb = 1 + (n - 1) / 32;
/* 434 */       int ntask = ArrayMath.min(nb, nthread); int itask;
/* 435 */       for (itask = 0; itask < ntask; itask++) {
/* 436 */         final ActiveList bltask = bl[itask];
/* 437 */         final float[] dtask = d[itask];
/* 438 */         cs.submit(new Callable<Void>() {
/*     */               public Void call() {
/* 440 */                 for (int ib = ai.getAndIncrement(); ib < nb; ib = ai.getAndIncrement()) {
/* 441 */                   int i = ib * 32;
/* 442 */                   int j = ArrayMath.min(i + 32, n);
/* 443 */                   for (int k = i; k < j; k++) {
/* 444 */                     TimeMarker2.Sample s = al.get(k);
/* 445 */                     TimeMarker2.this.solveOne(t, m, times, marks, s, bltask, dtask);
/*     */                   } 
/*     */                 } 
/* 448 */                 bltask.setAllAbsent();
/* 449 */                 return null;
/*     */               }
/*     */             });
/*     */       } 
/*     */       try {
/* 454 */         for (itask = 0; itask < ntask; itask++)
/* 455 */           cs.take(); 
/* 456 */       } catch (InterruptedException e) {
/* 457 */         throw new RuntimeException(e);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 463 */       al.clear();
/* 464 */       for (itask = 0; itask < ntask; itask++) {
/* 465 */         al.appendIfAbsent(bl[itask]);
/* 466 */         bl[itask].clear();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 472 */     es.shutdown();
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
/* 485 */     int mbmin = 64;
/* 486 */     int nbmax = 256;
/* 487 */     final float[][] dtask = new float[nbmax][];
/* 488 */     final ActiveList[] bltask = new ActiveList[nbmax];
/* 489 */     while (!al.isEmpty()) {
/* 490 */       final int n = al.size();
/* 491 */       int mbmax = ArrayMath.max(mbmin, 1 + (n - 1) / nbmax);
/* 492 */       int nb = 1 + (n - 1) / mbmax;
/* 493 */       final int mb = 1 + (n - 1) / nb;
/* 494 */       Parallel.loop(nb, new Parallel.LoopInt() {
/*     */             public void compute(int ib) {
/* 496 */               if (bltask[ib] == null) {
/* 497 */                 dtask[ib] = new float[3];
/* 498 */                 bltask[ib] = new TimeMarker2.ActiveList();
/*     */               } 
/* 500 */               int i = ib * mb;
/* 501 */               int j = ArrayMath.min(i + mb, n);
/* 502 */               for (int k = i; k < j; k++) {
/* 503 */                 TimeMarker2.Sample s = al.get(k);
/* 504 */                 TimeMarker2.this.solveOne(t, m, times, marks, s, bltask[ib], dtask[ib]);
/*     */               } 
/* 506 */               bltask[ib].setAllAbsent();
/*     */             }
/*     */           });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 514 */       al.clear();
/* 515 */       for (int ib = 0; ib < nb; ib++) {
/* 516 */         if (bltask[ib] != null) {
/* 517 */           al.appendIfAbsent(bltask[ib]);
/* 518 */           bltask[ib].clear();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float currentTime(float[][] t, int i1, int i2) {
/* 529 */     return wasActivated(this._s[i2][i1]) ? t[i2][i1] : Float.MAX_VALUE;
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
/* 541 */     int i1 = s.i1;
/* 542 */     int i2 = s.i2;
/*     */ 
/*     */     
/* 545 */     float ti = currentTime(t, i1, i2);
/* 546 */     float ci = computeTime(t, i1, i2, K1S[4], K2S[4], d);
/* 547 */     t[i2][i1] = ci;
/*     */ 
/*     */     
/* 550 */     if (ci >= ti * 0.999F) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 556 */       boolean checkNabors = (ci <= 1.5F * times[i2][i1]);
/*     */ 
/*     */       
/* 559 */       if (ci < times[i2][i1]) {
/* 560 */         times[i2][i1] = ci;
/* 561 */         marks[i2][i1] = m;
/*     */       } 
/*     */ 
/*     */       
/* 565 */       if (checkNabors)
/*     */       {
/*     */         
/* 568 */         for (int k = 0; k < 4; k++) {
/*     */ 
/*     */           
/* 571 */           int j1 = i1 + K1[k]; if (j1 >= 0 && j1 < this._n1) {
/* 572 */             int j2 = i2 + K2[k]; if (j2 >= 0 && j2 < this._n2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 578 */               float tj = currentTime(t, j1, j2);
/* 579 */               float cj = computeTime(t, j1, j2, K1S[k], K2S[k], d);
/*     */ 
/*     */               
/* 582 */               if (cj < tj * 0.999F) {
/*     */ 
/*     */                 
/* 585 */                 t[j2][j1] = cj;
/*     */ 
/*     */                 
/* 588 */                 bl.append(this._s[j2][j1]);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } else {
/*     */       
/* 596 */       bl.append(s);
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
/* 619 */     return (--i1 >= 0 && wasActivated(this._s[i2][i1])) ? t[i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t1p(float[][] t, int i1, int i2) {
/* 622 */     return (++i1 < this._n1 && wasActivated(this._s[i2][i1])) ? t[i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t2m(float[][] t, int i1, int i2) {
/* 625 */     return (--i2 >= 0 && wasActivated(this._s[i2][i1])) ? t[i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */   private float t2p(float[][] t, int i1, int i2) {
/* 628 */     return (++i2 < this._n2 && wasActivated(this._s[i2][i1])) ? t[i2][i1] : Float.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float computeTime(float[][] t, int i1, int i2, int[] k1s, int[] k2s, float[] d) {
/* 638 */     this._tensors.getTensor(i1, i2, d);
/* 639 */     float d11 = d[0];
/* 640 */     float d12 = d[1];
/* 641 */     float d22 = d[2];
/* 642 */     float e12 = 1.0F / (d11 * d22 - d12 * d12);
/* 643 */     float tc = currentTime(t, i1, i2);
/* 644 */     float t1m = t1m(t, i1, i2);
/* 645 */     float t1p = t1p(t, i1, i2);
/* 646 */     float t2m = t2m(t, i1, i2);
/* 647 */     float t2p = t2p(t, i1, i2);
/* 648 */     for (int k = 0; k < k1s.length; k++) {
/* 649 */       float t0; int k1 = k1s[k];
/* 650 */       int k2 = k2s[k];
/*     */       
/* 652 */       if (k1 != 0 && k2 != 0) {
/* 653 */         float t1 = (k1 < 0) ? t1m : t1p; if (t1 == Float.MAX_VALUE)
/* 654 */           continue;  float t2 = (k2 < 0) ? t2m : t2p; if (t2 == Float.MAX_VALUE)
/* 655 */           continue;  t0 = computeTime(d11, d12, d22, k1, k2, t1, t2);
/* 656 */       } else if (k1 != 0) {
/* 657 */         float t1 = (k1 < 0) ? t1m : t1p; if (t1 == Float.MAX_VALUE)
/* 658 */           continue;  t0 = t1 + ArrayMath.sqrt(d22 * e12);
/*     */       } else {
/* 660 */         float t2 = (k2 < 0) ? t2m : t2p; if (t2 == Float.MAX_VALUE)
/* 661 */           continue;  t0 = t2 + ArrayMath.sqrt(d11 * e12);
/*     */       } 
/* 663 */       if (t0 < tc)
/* 664 */         return t0;  continue;
/*     */     } 
/* 666 */     return tc;
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
/* 687 */     double ds11 = (d11 * s1 * s1);
/* 688 */     double ds12 = (d12 * s1 * s2);
/* 689 */     double ds22 = (d22 * s2 * s2);
/* 690 */     double t12 = (t1 - t2);
/* 691 */     double a = ds11 + 2.0D * ds12 + ds22;
/* 692 */     double b = 2.0D * (ds12 + ds22) * t12;
/* 693 */     double c = ds22 * t12 * t12 - 1.0D;
/* 694 */     double d = b * b - 4.0D * a * c;
/* 695 */     if (d < 0.0D)
/* 696 */       return Float.MAX_VALUE; 
/* 697 */     double u1 = (-b + ArrayMath.sqrt(d)) / 2.0D * a;
/* 698 */     double u2 = u1 + t12;
/* 699 */     if (ds11 * u1 + ds12 * u2 < 0.0D || ds12 * u1 + ds22 * u2 < 0.0D)
/*     */     {
/* 701 */       return Float.MAX_VALUE; } 
/* 702 */     return t1 + (float)u1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void trace(String s) {
/* 709 */     System.out.println(s);
/*     */   }
/*     */   private static float[][] toFloat(int[][] i) {
/* 712 */     int n1 = (i[0]).length;
/* 713 */     int n2 = i.length;
/* 714 */     float[][] f = new float[n2][n1];
/* 715 */     for (int i2 = 0; i2 < n2; i2++) {
/* 716 */       for (int i1 = 0; i1 < n1; i1++)
/* 717 */         f[i2][i1] = i[i2][i1]; 
/* 718 */     }  return f;
/*     */   }
/*     */   private static void plot(int[][] i) {
/* 721 */     plot(toFloat(i));
/*     */   }
/*     */   private static void plot(float[][] f) {
/* 724 */     trace("plot f min=" + ArrayMath.min(f) + " max=" + ArrayMath.max(f));
/* 725 */     SimplePlot sp = new SimplePlot(SimplePlot.Origin.UPPER_LEFT);
/*     */ 
/*     */     
/* 728 */     sp.setSize(920, 900);
/* 729 */     PixelsView pv = sp.addPixels(f);
/* 730 */     pv.setColorModel(ColorMap.JET);
/* 731 */     pv.setInterpolation(PixelsView.Interpolation.NEAREST);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/TimeMarker2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */