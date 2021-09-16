/*    */ package edu.mines.jtk.util;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Stopwatch
/*    */ {
/*    */   private boolean _running;
/*    */   private long _start;
/*    */   private long _time;
/*    */   
/*    */   public void start() {
/* 38 */     if (!this._running) {
/* 39 */       this._running = true;
/* 40 */       this._start = System.nanoTime();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void stop() {
/* 48 */     if (this._running) {
/* 49 */       this._time += System.nanoTime() - this._start;
/* 50 */       this._running = false;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 58 */     stop();
/* 59 */     this._time = 0L;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void restart() {
/* 66 */     reset();
/* 67 */     start();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double time() {
/* 75 */     if (this._running) {
/* 76 */       return 1.0E-9D * (this._time + System.nanoTime() - this._start);
/*    */     }
/* 78 */     return 1.0E-9D * this._time;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Stopwatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */