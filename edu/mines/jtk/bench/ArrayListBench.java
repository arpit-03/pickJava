/*    */ package edu.mines.jtk.bench;
/*    */ 
/*    */ import edu.mines.jtk.util.Stopwatch;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArrayListBench
/*    */ {
/*    */   private static final int INITIAL_CAPACITY = 8;
/*    */   
/*    */   public static class FloatList
/*    */   {
/*    */     public int n;
/* 32 */     public float[] a = new float[8];
/*    */     public void add(float f) {
/* 34 */       if (this.n == this.a.length) {
/* 35 */         float[] t = new float[2 * this.a.length];
/* 36 */         System.arraycopy(this.a, 0, t, 0, this.n);
/* 37 */         this.a = t;
/*    */       } 
/* 39 */       this.a[this.n++] = f;
/*    */     }
/*    */     public float[] trim() {
/* 42 */       float[] t = new float[this.n];
/* 43 */       System.arraycopy(this.a, 0, t, 0, this.n);
/* 44 */       return t;
/*    */     }
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 49 */     double maxtime = 2.0D;
/* 50 */     int n = 10000;
/*    */     
/* 52 */     for (int niter = 0; niter < 5; niter++) {
/* 53 */       double rate = benchArrayList(maxtime, n);
/* 54 */       System.out.println("ArrayList<Float> rate=" + rate);
/* 55 */       rate = benchFloatList(maxtime, n);
/* 56 */       System.out.println("FloatList        rate=" + rate);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static double benchList(double maxtime, int n, ListMaker lm) {
/* 65 */     Stopwatch sw = new Stopwatch();
/* 66 */     sw.start();
/*    */     int niter;
/* 68 */     for (niter = 0; sw.time() < maxtime; niter++)
/* 69 */       lm.makeList(n); 
/* 70 */     sw.stop();
/* 71 */     return n * niter / sw.time() * 1.0E-6D;
/*    */   }
/*    */   
/*    */   static double benchArrayList(double maxtime, int n) {
/* 75 */     return benchList(maxtime, n, new ListMaker() {
/*    */           public void makeList(int n) {
/* 77 */             ArrayList<Float> list = new ArrayList<>(8);
/* 78 */             float f = 0.0F;
/* 79 */             for (int i = 0; i < n; i++) {
/* 80 */               list.add(Float.valueOf(f));
/* 81 */               f++;
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   static double benchFloatList(double maxtime, int n) {
/* 88 */     return benchList(maxtime, n, new ListMaker() {
/*    */           public void makeList(int n) {
/* 90 */             ArrayListBench.FloatList list = new ArrayListBench.FloatList();
/* 91 */             float f = 0.0F;
/* 92 */             for (int i = 0; i < n; i++) {
/* 93 */               list.add(f);
/* 94 */               f++;
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   static interface ListMaker {
/*    */     void makeList(int param1Int);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/bench/ArrayListBench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */