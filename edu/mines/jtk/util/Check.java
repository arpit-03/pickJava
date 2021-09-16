/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Check
/*     */ {
/*     */   private static byte _b;
/*     */   private static short _s;
/*     */   private static int _i;
/*     */   private static long _l;
/*     */   private static float _f;
/*     */   private static double _d;
/*     */   
/*     */   public static void argument(boolean condition, String message) {
/*  32 */     if (!condition) {
/*  33 */       throw new IllegalArgumentException("required condition: " + message);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void state(boolean condition, String message) {
/*  43 */     if (!condition) {
/*  44 */       throw new IllegalStateException("required condition: " + message);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void index(int n, int i) {
/*  54 */     if (i < 0)
/*  55 */       throw new IndexOutOfBoundsException("index i=" + i + " < 0"); 
/*  56 */     if (n <= i) {
/*  57 */       throw new IndexOutOfBoundsException("index i=" + i + " >= n=" + n);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void index(byte[] a, int i) {
/*  67 */     _b = a[i];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void index(short[] a, int i) {
/*  77 */     _s = a[i];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void index(int[] a, int i) {
/*  87 */     _i = a[i];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void index(long[] a, int i) {
/*  97 */     _l = a[i];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void index(float[] a, int i) {
/* 107 */     _f = a[i];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void index(double[] a, int i) {
/* 117 */     _d = a[i];
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
/*     */   private Check() {
/* 129 */     System.out.println(_b);
/* 130 */     System.out.println(_s);
/* 131 */     System.out.println(_i);
/* 132 */     System.out.println(_l);
/* 133 */     System.out.println(_f);
/* 134 */     System.out.println(_d);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Check.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */