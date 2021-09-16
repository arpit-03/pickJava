/*     */ package edu.mines.jtk.bench;
/*     */ 
/*     */ import edu.mines.jtk.io.ArrayFile;
/*     */ import edu.mines.jtk.io.ArrayInputStream;
/*     */ import edu.mines.jtk.io.ArrayOutputStream;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Stopwatch;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArrayFileBench
/*     */ {
/*     */   public static void main(String[] args) {
/*  31 */     benchEndian();
/*  32 */     benchStream();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void benchStream() {
/*     */     try {
/*  40 */       boolean[] b = { false, true };
/*  41 */       for (boolean buffered : b) {
/*  42 */         File file = File.createTempFile("junk", "dat");
/*  43 */         file.deleteOnExit();
/*     */         
/*  45 */         benchStream(file, buffered);
/*     */       } 
/*  47 */     } catch (IOException ioe) {
/*  48 */       throw new RuntimeException(ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void benchStream(File file, boolean buffered) throws IOException {
/*  54 */     System.out.println("buffered=" + buffered);
/*  55 */     Stopwatch sw = new Stopwatch();
/*  56 */     int nh = 60;
/*  57 */     int na = 1000;
/*  58 */     int[] h = ArrayMath.randint(nh);
/*  59 */     float[] a = ArrayMath.randfloat(na);
/*  60 */     double maxtime = 60.0D;
/*  61 */     double mbytes = 4.0E-6D * (nh + na);
/*     */     
/*  63 */     FileOutputStream fos = new FileOutputStream(file);
/*  64 */     ArrayOutputStream aos = buffered ? new ArrayOutputStream(new BufferedOutputStream(fos)) : new ArrayOutputStream(fos);
/*     */ 
/*     */     
/*  67 */     sw.restart(); double s; long nw;
/*  68 */     for (nw = 0L, s = 0.0D; sw.time() < maxtime; nw++) {
/*  69 */       s += ArrayMath.sum(a);
/*  70 */       aos.writeInts(h);
/*  71 */       aos.writeFloats(a);
/*     */     } 
/*  73 */     aos.close();
/*  74 */     sw.stop();
/*  75 */     long rate = (int)(mbytes * nw / sw.time());
/*  76 */     System.out.println("ArrayOutputStream: sum=" + s + " nw=" + nw + " rate=" + rate);
/*  77 */     FileInputStream fis = new FileInputStream(file);
/*  78 */     ArrayInputStream ais = buffered ? new ArrayInputStream(new BufferedInputStream(fis)) : new ArrayInputStream(fis);
/*     */ 
/*     */     
/*  81 */     sw.restart(); long nr;
/*  82 */     for (nr = 0L, s = 0.0D; nr < nw; nr++) {
/*  83 */       ais.readInts(h);
/*  84 */       ais.readFloats(a);
/*  85 */       s += ArrayMath.sum(a);
/*     */     } 
/*  87 */     ais.close();
/*  88 */     sw.stop();
/*  89 */     rate = (int)(mbytes * nr / sw.time());
/*  90 */     System.out.println(" ArrayInputStream: sum=" + s + " nr=" + nr + " rate=" + rate);
/*     */   }
/*     */   
/*     */   private static void benchEndian() {
/*  94 */     benchBigEndian();
/*  95 */     benchLittleEndian();
/*     */   }
/*     */   
/*     */   private static void benchBigEndian() {
/*  99 */     bench(ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */   
/*     */   private static void benchLittleEndian() {
/* 103 */     bench(ByteOrder.LITTLE_ENDIAN);
/*     */   }
/*     */   
/*     */   private static void bench(ByteOrder order) {
/* 107 */     System.out.println("order=" + order);
/* 108 */     int n = 1000000;
/*     */     try {
/* 110 */       File file = File.createTempFile("junk", "dat");
/* 111 */       file.deleteOnExit();
/* 112 */       ArrayFile af = new ArrayFile(file, "rw", order, order);
/* 113 */       benchFloat(af, n);
/* 114 */       benchDouble(af, n);
/* 115 */       af.close();
/* 116 */     } catch (IOException ioe) {
/* 117 */       throw new RuntimeException(ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void benchFloat(ArrayFile af, int n) throws IOException {
/* 124 */     float[] a = ArrayMath.randfloat(n);
/* 125 */     float[] b = ArrayMath.zerofloat(n);
/*     */     
/* 127 */     Stopwatch sw = new Stopwatch();
/* 128 */     sw.start(); int nio;
/* 129 */     for (nio = 0; sw.time() < 5.0D; nio++) {
/* 130 */       af.seek(0L);
/* 131 */       af.writeFloats(a);
/* 132 */       af.seek(0L);
/* 133 */       af.readFloats(b);
/*     */     } 
/* 135 */     sw.stop();
/* 136 */     for (int i = 0; i < n; i++) {
/* 137 */       if (a[i] != b[i])
/* 138 */         throw new RuntimeException(" float: i/o failure"); 
/* 139 */     }  double time = sw.time();
/* 140 */     double rate = 8.0E-6D * nio * n / time;
/* 141 */     System.out.println(" float: rate=" + rate + " MB/s");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void benchDouble(ArrayFile af, int n) throws IOException {
/* 147 */     double[] a = ArrayMath.randdouble(n);
/* 148 */     double[] b = ArrayMath.zerodouble(n);
/*     */     
/* 150 */     Stopwatch sw = new Stopwatch();
/* 151 */     sw.start(); int nio;
/* 152 */     for (nio = 0; sw.time() < 5.0D; nio++) {
/* 153 */       af.seek(0L);
/* 154 */       af.writeDoubles(a);
/* 155 */       af.seek(0L);
/* 156 */       af.readDoubles(b);
/*     */     } 
/* 158 */     sw.stop();
/* 159 */     for (int i = 0; i < n; i++) {
/* 160 */       if (a[i] != b[i])
/* 161 */         throw new RuntimeException("double: i/o failure"); 
/* 162 */     }  double time = sw.time();
/* 163 */     double rate = 1.6E-5D * nio * n / time;
/* 164 */     System.out.println("double: rate=" + rate + " MB/s");
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/bench/ArrayFileBench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */