/*    */ package com.boreholeseismic.dsp;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StackTraces
/*    */ {
/*    */   private double[][] traceData;
/*    */   
/*    */   public StackTraces(double[][] traceIn) {
/* 10 */     this.traceData = traceIn;
/*    */     
/* 12 */     for (int i = 0; i < (traceIn[0]).length; i += 3) {
/* 13 */       for (int j = 0; j < traceIn.length; j++) {
/* 14 */         this.traceData[j][i] = traceIn[j][i] + traceIn[j][i + 1] + traceIn[j][i + 2];
/* 15 */         this.traceData[j][i + 1] = 0.0D;
/* 16 */         this.traceData[j][i + 2] = 0.0D;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public double[][] getTraceData() {
/* 23 */     return this.traceData;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/StackTraces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */