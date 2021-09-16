/*    */ package edu.mines.jtk.bench;
/*    */ 
/*    */ import edu.mines.jtk.dsp.FftComplex;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FftBench
/*    */ {
/*    */   public static void main(String[] args) {
/* 28 */     for (int niter = 0; niter < 5; niter++) {
/* 29 */       for (int nfft = 1; nfft <= 720720; ) {
/* 30 */         int nfftSmall = FftComplex.nfftSmall(nfft);
/* 31 */         int nfftFast = FftComplex.nfftFast(nfft);
/* 32 */         double timeSmall = time(nfftSmall);
/* 33 */         if (nfftFast == nfftSmall) {
/* 34 */           System.out.printf("nsmall=%d tsmall=%.14f\n", new Object[] { Integer.valueOf(nfftSmall), Double.valueOf(timeSmall) });
/*    */         } else {
/* 36 */           double timeFast = (nfftFast > nfftSmall) ? time(nfftFast) : timeSmall;
/* 37 */           System.out.printf("nsmall=%d tsmall=%.14f tfast=%.14f\n", new Object[] {
/* 38 */                 Integer.valueOf(nfftSmall), Double.valueOf(timeSmall), Double.valueOf(timeFast) });
/* 39 */           if (timeSmall < timeFast)
/* 40 */             System.out.println("*** WARNING: tsmall<tfast! ***"); 
/*    */         } 
/* 42 */         nfft = 1 + nfftSmall;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private static double time(int nfft) {
/* 48 */     double maxtime = 2.0D;
/* 49 */     FftComplex fft = new FftComplex(nfft);
/* 50 */     float[] cx = ArrayMath.crandfloat(nfft);
/*    */     
/* 52 */     Stopwatch sw = new Stopwatch();
/* 53 */     sw.start(); int count;
/* 54 */     for (count = 0; sw.time() < maxtime; count++) {
/* 55 */       fft.complexToComplex(-1, cx, cx);
/* 56 */       fft.complexToComplex(1, cx, cx);
/* 57 */       fft.scale(nfft, cx);
/*    */     } 
/* 59 */     sw.stop();
/* 60 */     double time = sw.time() / count;
/* 61 */     return time;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/bench/FftBench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */