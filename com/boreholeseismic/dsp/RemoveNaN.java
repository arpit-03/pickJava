/*    */ package com.boreholeseismic.dsp;
/*    */ 
/*    */ 
/*    */ public class RemoveNaN
/*    */ {
/*    */   private double[][] traceData;
/*    */   
/*    */   public RemoveNaN(double[][] IPtraceData) {
/*  9 */     this.traceData = new double[IPtraceData.length][(IPtraceData[0]).length];
/*    */     
/* 11 */     for (int i = 0; i < (IPtraceData[0]).length; i++) {
/*    */       
/* 13 */       for (int j = 0; j < IPtraceData.length; j++) {
/*    */         
/* 15 */         if (Double.isNaN(IPtraceData[j][i])) {
/* 16 */           this.traceData[j][i] = 0.0D;
/*    */         } else {
/* 18 */           this.traceData[j][i] = IPtraceData[j][i];
/*    */         } 
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
/*    */   public double[][] getTraceData() {
/* 33 */     return this.traceData;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/RemoveNaN.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */