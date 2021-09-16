/*    */ package com.boreholeseismic.dsp;
/*    */ 
/*    */ 
/*    */ public class DiffTraces
/*    */ {
/*    */   private double[][] traceData;
/*    */   
/*    */   public DiffTraces(double[][] traceIn) {
/*  9 */     this.traceData = traceIn;
/*    */     
/* 11 */     for (int i = 0; i < (traceIn[0]).length; i++) {
/* 12 */       double[] temp = new double[traceIn.length];
/* 13 */       double[] bk_temp = new double[traceIn.length];
/*    */ 
/*    */       
/* 16 */       for (int m = 0; m < traceIn.length; m++) {
/* 17 */         temp[m] = traceIn[m][i];
/* 18 */         bk_temp[m] = traceIn[m][i];
/*    */       } 
/*    */       
/* 21 */       for (int k = 1; k < temp.length - 1; k++) {
/* 22 */         temp[k] = 0.5D * (bk_temp[k + 1] - bk_temp[k - 1]);
/*    */       }
/* 24 */       temp[0] = 0.5D * (-3.0D * bk_temp[0] + 4.0D * bk_temp[1] - bk_temp[2]);
/* 25 */       temp[temp.length - 1] = 0.5D * (3.0D * bk_temp[temp.length - 1] - 4.0D * bk_temp[temp.length - 2] + bk_temp[temp.length - 3]);
/*    */       
/* 27 */       for (int j = 0; j < traceIn.length; j++) {
/* 28 */         this.traceData[j][i] = temp[j];
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double[][] getTraceData() {
/* 36 */     return this.traceData;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/DiffTraces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */