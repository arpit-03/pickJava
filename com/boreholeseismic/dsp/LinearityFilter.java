/*    */ package com.boreholeseismic.dsp;
/*    */ 
/*    */ import org.apache.commons.math3.linear.MatrixUtils;
/*    */ import org.apache.commons.math3.linear.RealMatrix;
/*    */ import org.apache.commons.math3.linear.SingularValueDecomposition;
/*    */ 
/*    */ 
/*    */ public class LinearityFilter
/*    */ {
/*    */   private double[][] traceData;
/*    */   private double[][] linearity;
/*    */   
/*    */   public LinearityFilter(double[][] traceIn, FilterSettings filterSettings) {
/* 14 */     this.linearity = new double[traceIn.length][(traceIn[0]).length];
/* 15 */     this.traceData = new double[traceIn.length][(traceIn[0]).length];
/*    */     
/* 17 */     int noRec = (traceIn[0]).length / 3;
/*    */     
/* 19 */     for (int rec = 0; rec < noRec; rec++) {
/*    */       
/* 21 */       double[] tempLinearity = new double[traceIn.length];
/*    */       
/* 23 */       for (int sample = (int)filterSettings.LPWindow; sample < this.traceData.length - (int)filterSettings.LPWindow; sample++) {
/*    */         
/* 25 */         double[][] tempArray = new double[2 * (int)filterSettings.LPWindow + 1][3];
/*    */         
/* 27 */         for (int i = -((int)filterSettings.LPWindow); i <= (int)filterSettings.LPWindow; i++) {
/* 28 */           for (int j = 0; j < (tempArray[0]).length; j++) {
/* 29 */             tempArray[i + (int)filterSettings.LPWindow][j] = traceIn[sample + i][3 * rec + j];
/*    */           }
/*    */         } 
/* 32 */         RealMatrix m = MatrixUtils.createRealMatrix(tempArray);
/* 33 */         SingularValueDecomposition svd = new SingularValueDecomposition(m);
/* 34 */         RealMatrix n = svd.getS();
/* 35 */         tempLinearity[sample] = 0.5D * (3.0D * Math.max(Math.max(n.getEntry(0, 0), n.getEntry(1, 1)), n.getEntry(2, 2)) / n.getTrace() - 1.0D);
/*    */       } 
/*    */       
/* 38 */       double maxLinearity = tempLinearity[0];
/*    */       int k;
/* 40 */       for (k = 1; k < traceIn.length; k++) {
/* 41 */         if (maxLinearity < tempLinearity[k])
/* 42 */           maxLinearity = tempLinearity[k]; 
/*    */       } 
/* 44 */       for (k = 0; k < traceIn.length; k++) {
/*    */         
/* 46 */         tempLinearity[k] = tempLinearity[k] / maxLinearity;
/*    */         
/* 48 */         if (tempLinearity[k] > filterSettings.LPThresh) {
/* 49 */           tempLinearity[k] = 1.0D;
/*    */         } else {
/* 51 */           tempLinearity[k] = Math.pow(tempLinearity[k], filterSettings.LPExp);
/*    */         } 
/*    */         
/* 54 */         this.traceData[k][3 * rec] = tempLinearity[k] * traceIn[k][3 * rec];
/* 55 */         this.traceData[k][3 * rec + 1] = tempLinearity[k] * traceIn[k][3 * rec + 1];
/* 56 */         this.traceData[k][3 * rec + 2] = tempLinearity[k] * traceIn[k][3 * rec + 2];
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[][] getTraceData() {
/* 66 */     return this.traceData;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/LinearityFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */