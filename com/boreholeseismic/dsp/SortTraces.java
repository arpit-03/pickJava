/*    */ package com.boreholeseismic.dsp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SortTraces
/*    */ {
/*    */   private double[][] traceData;
/*    */   
/*    */   public SortTraces(double[][] traceDataTemp, double[] tracePolarity, double[] traceOrder) {
/* 11 */     for (int i = 0; i < (traceDataTemp[0]).length / 3; i++) {
/* 12 */       for (int k = 0; k < traceDataTemp.length; k++) {
/*    */         
/* 14 */         for (int m = 0; m < 3; m++) {
/* 15 */           if (traceDataTemp[k][3 * i + m] != Double.NaN)
/* 16 */             traceDataTemp[k][3 * i + m] = traceDataTemp[k][3 * i + m] * tracePolarity[m]; 
/*    */         } 
/*    */       } 
/*    */     } 
/* 20 */     double[][] traceDataOrdered = new double[traceDataTemp.length][(traceDataTemp[0]).length];
/*    */     
/* 22 */     for (int j = 0; j < (traceDataTemp[0]).length / 3; j++) {
/* 23 */       for (int k = 0; k < traceDataTemp.length; k++) {
/*    */         
/* 25 */         for (int m = 0; m < 3; m++) {
/* 26 */           traceDataOrdered[k][3 * j + m] = traceDataTemp[k][(int)((3 * j) + traceOrder[m] - 1.0D)];
/*    */         }
/*    */       } 
/*    */     } 
/* 30 */     this.traceData = new double[traceDataTemp.length][(traceDataTemp[0]).length];
/* 31 */     this.traceData = traceDataOrdered;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[][] getTraceData() {
/* 38 */     return this.traceData;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/SortTraces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */