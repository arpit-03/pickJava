/*    */ package edu.mines.jtk.bench;
/*    */ 
/*    */ import edu.mines.jtk.la.DMatrix;
/*    */ import edu.mines.jtk.la.DMatrixQrd;
/*    */ import edu.mines.jtk.lapack.DMatrix;
/*    */ import edu.mines.jtk.lapack.DMatrixQrd;
/*    */ import edu.mines.jtk.util.ArrayMath;
/*    */ import edu.mines.jtk.util.Stopwatch;
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
/*    */ public class QrdBench
/*    */ {
/*    */   public static void main(String[] args) {
/* 28 */     double maxtime = 5.0D;
/* 29 */     int m = 100;
/* 30 */     int n = 5;
/* 31 */     int nrhs = 5;
/*    */ 
/*    */ 
/*    */     
/* 35 */     DMatrix aj = DMatrix.random(m, n);
/*    */     
/* 37 */     DMatrix bj = DMatrix.random(m, nrhs);
/*    */ 
/*    */ 
/*    */     
/* 41 */     DMatrix al = new DMatrix(aj.get());
/*    */     
/* 43 */     DMatrix bl = new DMatrix(bj.get());
/*    */ 
/*    */ 
/*    */     
/* 47 */     Stopwatch sw = new Stopwatch();
/* 48 */     for (int niter = 0; niter < 5; niter++) {
/*    */ 
/*    */ 
/*    */       
/* 52 */       DMatrix x1 = new DMatrix(1, 1);
/* 53 */       sw.restart(); int nqrd;
/* 54 */       for (nqrd = 0; sw.time() < maxtime; nqrd++) {
/* 55 */         DMatrixQrd qrd1 = new DMatrixQrd(aj);
/* 56 */         x1 = qrd1.solve(bj);
/*    */       } 
/* 58 */       sw.stop();
/* 59 */       double sum = ArrayMath.sum(x1.getArray());
/* 60 */       double rate = nqrd / sw.time();
/* 61 */       System.out.println("edu.mines.jtk.la:     rate=" + rate + " sum=" + sum);
/*    */ 
/*    */ 
/*    */       
/* 65 */       DMatrix x2 = new DMatrix(1, 1);
/* 66 */       sw.restart();
/* 67 */       for (nqrd = 0; sw.time() < maxtime; nqrd++) {
/* 68 */         DMatrixQrd qrd2 = new DMatrixQrd(al);
/* 69 */         x2 = qrd2.solve(bl);
/*    */       } 
/* 71 */       sw.stop();
/* 72 */       sum = ArrayMath.sum(x2.getArray());
/* 73 */       rate = nqrd / sw.time();
/* 74 */       System.out.println("edu.mines.jtk.lapack: rate=" + rate + " sum=" + sum);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/bench/QrdBench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */