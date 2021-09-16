/*    */ package com.boreholeseismic.gui;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MatrixScalarMultiply
/*    */ {
/*    */   private final double[][] traceData;
/*    */   
/*    */   public MatrixScalarMultiply(double[][] traceIn, double scalar) {
/* 10 */     this.traceData = new double[traceIn.length][(traceIn[0]).length];
/*    */     
/* 12 */     for (int i = 0; i < (traceIn[0]).length; i++) {
/* 13 */       for (int j = 1; j < traceIn.length; j++) {
/* 14 */         this.traceData[j][i] = scalar * traceIn[j][i];
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public double[][] getTraceData() {
/* 20 */     return this.traceData;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/gui/MatrixScalarMultiply.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */