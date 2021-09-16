/*     */ package edu.mines.jtk.bench;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Stopwatch;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecursiveFilterBench
/*     */ {
/*     */   public static void main(String[] args) {
/*  28 */     double maxtime = 5.0D;
/*  29 */     int n1 = 4000;
/*  30 */     int n2 = 4000;
/*  31 */     float b0 = 2.0F;
/*  32 */     float b1 = -3.2F;
/*  33 */     float b2 = 1.28F;
/*  34 */     float a1 = -1.8F;
/*  35 */     float a2 = 0.81F;
/*  36 */     float[][] x = ArrayMath.rampfloat(0.0F, 0.0F, 1.0F, n1, n2);
/*  37 */     float[][] y = ArrayMath.zerofloat(n1, n2);
/*  38 */     double mflop = 9.0D * n1 * n2 * 1.0E-6D;
/*     */ 
/*     */     
/*  41 */     Stopwatch sw = new Stopwatch();
/*  42 */     for (int niter = 0; niter < 5; niter++) {
/*  43 */       sw.restart(); int n;
/*  44 */       for (n = 0; sw.time() < maxtime; n++)
/*  45 */         filter1(b0, b1, b2, a1, a2, x, y); 
/*  46 */       sw.stop();
/*  47 */       double sum = ArrayMath.sum(y);
/*  48 */       double rate = n * mflop / sw.time();
/*  49 */       System.out.println("filter1: rate=" + rate + " sum=" + sum);
/*     */       
/*  51 */       sw.restart();
/*  52 */       for (n = 0; sw.time() < maxtime; n++)
/*  53 */         filter2a(b0, b1, b2, a1, a2, x, y); 
/*  54 */       sw.stop();
/*  55 */       sum = ArrayMath.sum(y);
/*  56 */       rate = n * mflop / sw.time();
/*  57 */       System.out.println("filter2a: rate=" + rate + " sum=" + sum);
/*     */       
/*  59 */       sw.restart();
/*  60 */       for (n = 0; sw.time() < maxtime; n++)
/*  61 */         filter2b(b0, b1, b2, a1, a2, x, y); 
/*  62 */       sw.stop();
/*  63 */       sum = ArrayMath.sum(y);
/*  64 */       rate = n * mflop / sw.time();
/*  65 */       System.out.println("filter2b: rate=" + rate + " sum=" + sum);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void filter(float b0, float b1, float b2, float a1, float a2, float[] x, float[] y) {
/*  73 */     int n = y.length;
/*  74 */     float yim2 = 0.0F;
/*  75 */     float yim1 = 0.0F;
/*  76 */     float xim2 = 0.0F;
/*  77 */     float xim1 = 0.0F;
/*  78 */     for (int i = 0; i < n; i++) {
/*  79 */       float xi = x[i];
/*  80 */       float yi = b0 * xi + b1 * xim1 + b2 * xim2 - a1 * yim1 - a2 * yim2;
/*  81 */       y[i] = yi;
/*  82 */       yim2 = yim1;
/*  83 */       yim1 = yi;
/*  84 */       xim2 = xim1;
/*  85 */       xim1 = xi;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void filter1(float b0, float b1, float b2, float a1, float a2, float[][] x, float[][] y) {
/*  93 */     int n2 = y.length;
/*  94 */     for (int i2 = 0; i2 < n2; i2++) {
/*  95 */       filter(b0, b1, b2, a1, a2, x[i2], y[i2]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void filter2a(float b0, float b1, float b2, float a1, float a2, float[][] x, float[][] y) {
/* 103 */     int n2 = y.length;
/* 104 */     int n1 = (y[0]).length;
/* 105 */     float[] xt = new float[n2];
/* 106 */     float[] yt = new float[n2];
/* 107 */     for (int i1 = 0; i1 < n1; i1++) {
/* 108 */       int i2; for (i2 = 0; i2 < n2; i2++) {
/* 109 */         xt[i2] = x[i2][i1];
/*     */       }
/* 111 */       filter(b0, b1, b2, a1, a2, xt, yt);
/* 112 */       for (i2 = 0; i2 < n2; i2++) {
/* 113 */         y[i2][i1] = yt[i2];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void filter2b(float b0, float b1, float b2, float a1, float a2, float[][] x, float[][] y) {
/* 122 */     int n2 = y.length;
/* 123 */     int n1 = (y[0]).length;
/* 124 */     float[] yim2 = new float[n1];
/* 125 */     float[] yim1 = new float[n1];
/* 126 */     float[] xim2 = new float[n1];
/* 127 */     float[] xim1 = new float[n1];
/* 128 */     float[] xi = new float[n1];
/* 129 */     for (int i2 = 0; i2 < n2; i2++) {
/* 130 */       float[] xi2 = x[i2];
/* 131 */       float[] yi = y[i2];
/* 132 */       for (int i1 = 0; i1 < n1; i1++) {
/* 133 */         xi[i1] = xi2[i1];
/* 134 */         yi[i1] = b0 * xi[i1] + b1 * xim1[i1] + b2 * xim2[i1] - a1 * yim1[i1] - a2 * yim2[i1];
/*     */       } 
/*     */       
/* 137 */       yim2 = yim1;
/* 138 */       yim1 = yi;
/* 139 */       float[] xt = xim2;
/* 140 */       xim2 = xim1;
/* 141 */       xim1 = xi;
/* 142 */       xi = xt;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/bench/RecursiveFilterBench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */