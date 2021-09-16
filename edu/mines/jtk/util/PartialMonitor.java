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
/*    */ public class PartialMonitor
/*    */   implements Monitor
/*    */ {
/* 25 */   private Monitor _wrapped = null;
/* 26 */   private double _begin = 0.0D;
/* 27 */   private double _end = 1.0D;
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
/*    */   public PartialMonitor(Monitor wrapped, double begin, double end) {
/* 39 */     this._wrapped = wrapped;
/* 40 */     this._begin = begin;
/* 41 */     this._end = end;
/*    */   }
/*    */ 
/*    */   
/*    */   public void report(double fraction) {
/* 46 */     if (this._wrapped == null) {
/*    */       return;
/*    */     }
/* 49 */     this._wrapped.report(fraction * (this._end - this._begin) + this._begin);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCanceled() {
/* 54 */     return this._wrapped.isCanceled();
/*    */   }
/*    */ 
/*    */   
/*    */   public void initReport(double initFraction) {
/* 59 */     this._wrapped.initReport(initFraction * (this._end - this._begin) + this._begin);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/PartialMonitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */