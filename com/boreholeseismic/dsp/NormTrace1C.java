/*    */ package com.boreholeseismic.dsp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NormTrace1C
/*    */ {
/*    */   private double[][] traceData;
/*    */   public double[] maxPoints;
/*    */   
/*    */   public NormTrace1C(double[][] traceIn, double scalar) {
/* 13 */     double[][] absoluteTrace = traceIn;
/* 14 */     int rows = absoluteTrace.length;
/* 15 */     int cols = (absoluteTrace[0]).length;
/* 16 */     double[] maxValues = new double[(absoluteTrace[0]).length];
/* 17 */     this.maxPoints = new double[(absoluteTrace[0]).length];
/*    */     int i;
/* 19 */     for (i = 0; i < maxValues.length; i++) {
/* 20 */       maxValues[i] = Math.abs(absoluteTrace[0][i]);
/*    */     }
/*    */     
/* 23 */     for (i = 0; i < cols; i++) {
/* 24 */       for (int j = 0; j < rows; j++) {
/* 25 */         if (maxValues[i] < Math.abs(absoluteTrace[j][i])) {
/* 26 */           maxValues[i] = Math.abs(absoluteTrace[j][i]);
/* 27 */           this.maxPoints[i] = j;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 32 */     this.traceData = new double[rows][cols];
/*    */     
/* 34 */     for (i = 0; i < cols; i++) {
/* 35 */       for (int j = 0; j < rows; j++) {
/* 36 */         this.traceData[j][i] = (1 + i) + scalar * traceIn[j][i] / maxValues[i];
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public double[][] getTraceData() {
/* 42 */     return this.traceData;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/NormTrace1C.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */