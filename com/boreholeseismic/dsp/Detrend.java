/*    */ package com.boreholeseismic.dsp;
/*    */ 
/*    */ import org.apache.commons.math3.stat.regression.SimpleRegression;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Detrend
/*    */ {
/*    */   private double[][] traceData;
/*    */   
/*    */   public Detrend(double[][] IPtraceData) {
/* 12 */     this.traceData = new double[IPtraceData.length][(IPtraceData[0]).length];
/*    */ 
/*    */     
/* 15 */     for (int i = 0; i < (IPtraceData[0]).length; i++) {
/*    */ 
/*    */       
/* 18 */       SimpleRegression regression = new SimpleRegression();
/*    */       
/* 20 */       for (int j = 0; j < IPtraceData.length; j++)
/*    */       {
/* 22 */         regression.addData(j, IPtraceData[j][i]);
/*    */       }
/*    */       
/* 25 */       double slope = regression.getSlope();
/* 26 */       double intercept = regression.getIntercept();
/*    */       
/* 28 */       for (int k = 0; k < IPtraceData.length; k++)
/*    */       {
/* 30 */         this.traceData[k][i] = IPtraceData[k][i] - intercept + IPtraceData[k][i] * slope;
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[][] getTraceData() {
/* 39 */     return this.traceData;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/Detrend.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */