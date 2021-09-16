/*     */ package edu.mines.jtk.bench;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import edu.mines.jtk.util.Parallel;
/*     */ import edu.mines.jtk.util.Stopwatch;
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
/*     */ public class MtMatMulBench
/*     */ {
/*  32 */   public static final int NTHREAD = Runtime.getRuntime().availableProcessors();
/*     */   
/*     */   public static void main(String[] args) {
/*  35 */     int m = 1001;
/*  36 */     int n = 1002;
/*  37 */     float[][] a = ArrayMath.randfloat(n, m);
/*  38 */     float[][] b = ArrayMath.randfloat(m, n);
/*  39 */     float[][] c1 = ArrayMath.zerofloat(m, m);
/*  40 */     float[][] c2 = ArrayMath.zerofloat(m, m);
/*  41 */     float[][] c3 = ArrayMath.zerofloat(m, m);
/*  42 */     float[][] c4 = ArrayMath.zerofloat(m, m);
/*  43 */     float[][] c5 = ArrayMath.zerofloat(m, m);
/*  44 */     Stopwatch s = new Stopwatch();
/*  45 */     double mflops = 2.0E-6D * m * m * n;
/*  46 */     double maxtime = 5.0D;
/*     */     
/*  48 */     System.out.println("Matrix multiply benchmark");
/*  49 */     System.out.println("m=" + m + " n=" + n + " nthread=" + NTHREAD);
/*  50 */     System.out.println("Methods:");
/*  51 */     System.out.println("mul1 = single-threaded");
/*  52 */     System.out.println("mul2 = multi-threaded (equal chunks)");
/*  53 */     System.out.println("mul3 = multi-threaded (atomic-integer)");
/*  54 */     System.out.println("mul4 = multi-threaded (thread-pool)");
/*  55 */     System.out.println("mul5 = multi-threaded (fork-join)");
/*     */     
/*  57 */     for (int ntrial = 0; ntrial < 3; ntrial++) {
/*  58 */       System.out.println();
/*     */ 
/*     */       
/*  61 */       s.restart(); int nmul;
/*  62 */       for (nmul = 0; s.time() < maxtime; nmul++)
/*  63 */         mul1(a, b, c1); 
/*  64 */       s.stop();
/*  65 */       System.out.println("mul1: rate=" + (int)(nmul * mflops / s.time()) + " mflops");
/*     */       
/*  67 */       s.restart();
/*  68 */       for (nmul = 0; s.time() < maxtime; nmul++)
/*  69 */         mul2(a, b, c2); 
/*  70 */       s.stop();
/*  71 */       System.out.println("mul2: rate=" + (int)(nmul * mflops / s.time()) + " mflops");
/*     */       
/*  73 */       s.restart();
/*  74 */       for (nmul = 0; s.time() < maxtime; nmul++)
/*  75 */         mul3(a, b, c3); 
/*  76 */       s.stop();
/*  77 */       System.out.println("mul3: rate=" + (int)(nmul * mflops / s.time()) + " mflops");
/*     */       
/*  79 */       s.restart();
/*  80 */       for (nmul = 0; s.time() < maxtime; nmul++)
/*  81 */         mul4(a, b, c4); 
/*  82 */       s.stop();
/*  83 */       System.out.println("mul4: rate=" + (int)(nmul * mflops / s.time()) + " mflops");
/*     */       
/*  85 */       s.restart();
/*  86 */       for (nmul = 0; s.time() < maxtime; nmul++)
/*  87 */         mul5(a, b, c5); 
/*  88 */       s.stop();
/*  89 */       System.out.println("mul5: rate=" + (int)(nmul * mflops / s.time()) + " mflops");
/*     */       
/*  91 */       assertEquals(c1, c2);
/*  92 */       assertEquals(c1, c3);
/*  93 */       assertEquals(c1, c4);
/*  94 */       assertEquals(c1, c5);
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
/*     */   private static void computeColumn(int j, float[][] a, float[][] b, float[][] c) {
/* 107 */     int ni = c.length;
/* 108 */     int nk = b.length;
/* 109 */     float[] bj = new float[nk];
/* 110 */     for (int k = 0; k < nk; k++)
/* 111 */       bj[k] = b[k][j]; 
/* 112 */     for (int i = 0; i < ni; i++) {
/* 113 */       float[] ai = a[i];
/* 114 */       float cij = 0.0F;
/* 115 */       int mk = nk % 4; int m;
/* 116 */       for (m = 0; m < mk; m++)
/* 117 */         cij += ai[m] * bj[m]; 
/* 118 */       for (m = mk; m < nk; m += 4) {
/* 119 */         cij += ai[m] * bj[m];
/* 120 */         cij += ai[m + 1] * bj[m + 1];
/* 121 */         cij += ai[m + 2] * bj[m + 2];
/* 122 */         cij += ai[m + 3] * bj[m + 3];
/*     */       } 
/* 124 */       c[i][j] = cij;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void mul1(float[][] a, float[][] b, float[][] c) {
/* 132 */     checkDimensions(a, b, c);
/* 133 */     int nj = (c[0]).length;
/* 134 */     for (int j = 0; j < nj; j++) {
/* 135 */       computeColumn(j, a, b, c);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void mul2(final float[][] a, final float[][] b, final float[][] c) {
/* 144 */     checkDimensions(a, b, c);
/* 145 */     int nj = (c[0]).length;
/* 146 */     int mj = 1 + nj / NTHREAD;
/* 147 */     Thread[] threads = new Thread[NTHREAD];
/* 148 */     for (int ithread = 0; ithread < NTHREAD; ithread++) {
/* 149 */       final int jfirst = ithread * mj;
/* 150 */       final int jlast = Math.min(jfirst + mj, nj);
/* 151 */       threads[ithread] = new Thread(new Runnable() {
/*     */             public void run() {
/* 153 */               for (int j = jfirst; j < jlast; j++)
/* 154 */                 MtMatMulBench.computeColumn(j, a, b, c); 
/*     */             }
/*     */           });
/*     */     } 
/* 158 */     startAndJoin(threads);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void mul3(final float[][] a, final float[][] b, final float[][] c) {
/* 167 */     checkDimensions(a, b, c);
/* 168 */     final int nj = (c[0]).length;
/* 169 */     final AtomicInteger aj = new AtomicInteger();
/* 170 */     Thread[] threads = new Thread[NTHREAD];
/* 171 */     for (int ithread = 0; ithread < threads.length; ithread++) {
/* 172 */       threads[ithread] = new Thread(new Runnable() {
/*     */             public void run() {
/* 174 */               for (int j = aj.getAndIncrement(); j < nj; j = aj.getAndIncrement())
/* 175 */                 MtMatMulBench.computeColumn(j, a, b, c); 
/*     */             }
/*     */           });
/*     */     } 
/* 179 */     startAndJoin(threads);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void mul4(final float[][] a, final float[][] b, final float[][] c) {
/* 188 */     checkDimensions(a, b, c);
/* 189 */     int nj = (c[0]).length;
/* 190 */     ExecutorService es = Executors.newFixedThreadPool(NTHREAD);
/* 191 */     CompletionService<Void> cs = new ExecutorCompletionService<>(es); int j;
/* 192 */     for (j = 0; j < nj; j++) {
/* 193 */       final int jj = j;
/* 194 */       cs.submit(new Runnable() {
/*     */             public void run() {
/* 196 */               MtMatMulBench.computeColumn(jj, a, b, c);
/*     */             }
/*     */           }null);
/*     */     } 
/*     */     try {
/* 201 */       for (j = 0; j < nj; j++)
/* 202 */         cs.take(); 
/* 203 */     } catch (InterruptedException ie) {
/* 204 */       throw new RuntimeException(ie);
/*     */     } 
/* 206 */     es.shutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void mul5(final float[][] a, final float[][] b, final float[][] c) {
/* 215 */     checkDimensions(a, b, c);
/* 216 */     int nj = (c[0]).length;
/* 217 */     Parallel.loop(nj, new Parallel.LoopInt() {
/*     */           public void compute(int j) {
/* 219 */             MtMatMulBench.computeColumn(j, a, b, c);
/*     */           }
/*     */         });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void startAndJoin(Thread[] threads) {
/* 264 */     for (Thread thread : threads)
/* 265 */       thread.start(); 
/*     */     try {
/* 267 */       for (Thread thread : threads)
/* 268 */         thread.join(); 
/* 269 */     } catch (InterruptedException ie) {
/* 270 */       throw new RuntimeException(ie);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void assertEquals(float[][] a, float[][] b) {
/* 275 */     Check.state((a.length == b.length), "same dimensions");
/* 276 */     Check.state(((a[0]).length == (b[0]).length), "same dimensions");
/* 277 */     int m = (a[0]).length;
/* 278 */     int n = a.length;
/* 279 */     for (int i = 0; i < m; i++) {
/* 280 */       for (int j = 0; j < n; j++) {
/* 281 */         Check.state((a[i][j] == b[i][j]), "same elements");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void checkDimensions(float[][] a, float[][] b, float[][] c) {
/* 287 */     int ma = a.length;
/* 288 */     int na = (a[0]).length;
/* 289 */     int mb = b.length;
/* 290 */     int nb = (b[0]).length;
/* 291 */     int mc = c.length;
/* 292 */     int nc = (c[0]).length;
/* 293 */     Check.argument((na == mb), "number of columns in A = number of rows in B");
/* 294 */     Check.argument((ma == mc), "number of rows in A = number of rows in C");
/* 295 */     Check.argument((nb == nc), "number of columns in B = number of columns in C");
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/bench/MtMatMulBench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */