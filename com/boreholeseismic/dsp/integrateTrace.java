/*    */ package com.boreholeseismic.dsp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class integrateTrace
/*    */ {
/*    */   private final double[][] traceData;
/*    */   
/*    */   public integrateTrace(double[][] traceIn, double timeSample) {
/* 15 */     this.traceData = new double[traceIn.length][(traceIn[0]).length];
/* 16 */     double[][] calcTraceData = (double[][])this.traceData.clone();
/*    */ 
/*    */ 
/*    */     
/* 20 */     for (int i = 0; i < (traceIn[0]).length; i++) {
/*    */       
/* 22 */       this.traceData[0][i] = 0.0D;
/* 23 */       for (int j = 1; j < traceIn.length; j++) {
/* 24 */         this.traceData[j][i] = timeSample * (this.traceData[j - 1][i] + (traceIn[j - 1][i] + traceIn[j][i]) / 2.0D);
/*    */       }
/*    */     } 
/*    */   }
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
/*    */   public double[][] getTraceData() {
/* 57 */     return this.traceData;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/integrateTrace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */