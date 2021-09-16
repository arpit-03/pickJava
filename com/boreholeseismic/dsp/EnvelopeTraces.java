/*    */ package com.boreholeseismic.dsp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnvelopeTraces
/*    */ {
/*    */   private double[][] traceData;
/*    */   
/*    */   public EnvelopeTraces(double[][] traceIn) {
/* 11 */     this.traceData = traceIn;
/*    */     
/* 13 */     for (int i = 0; i < (traceIn[0]).length; i++) {
/* 14 */       double[] temp = new double[traceIn.length]; int j;
/* 15 */       for (j = 0; j < traceIn.length; j++) {
/* 16 */         temp[j] = traceIn[j][i];
/*    */       }
/* 18 */       HilbertTransform.FHT(temp, FourierTransform.Direction.Forward);
/*    */       
/* 20 */       for (j = 0; j < traceIn.length; j++) {
/* 21 */         this.traceData[j][i] = temp[j];
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public double[][] getTraceData() {
/* 28 */     return this.traceData;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/EnvelopeTraces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */