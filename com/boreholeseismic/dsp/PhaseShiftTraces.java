/*    */ package com.boreholeseismic.dsp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PhaseShiftTraces
/*    */ {
/*    */   private double[][] traceData;
/*    */   
/*    */   public PhaseShiftTraces(double[][] traceIn) {
/* 12 */     this.traceData = traceIn;
/*    */ 
/*    */     
/* 15 */     for (int i = 0; i < (traceIn[0]).length; i++) {
/*    */       
/* 17 */       double[] temp = new double[traceIn.length];
/*    */       
/* 19 */       for (int j = 0; j < traceIn.length; j++) {
/* 20 */         temp[j] = traceIn[j][i];
/*    */       }
/* 22 */       double[] absHT = (double[])temp.clone(), angleHT = (double[])temp.clone(), result = (double[])temp.clone();
/* 23 */       HilbertTransform.FHT(absHT, FourierTransform.Direction.Forward);
/* 24 */       HilbertTransform.FHTAngle(angleHT, FourierTransform.Direction.Forward);
/* 25 */       for (int k = 0; k < absHT.length; k++) {
/*    */         
/* 27 */         ComplexNumber exponentCalculation = ComplexNumber.Multiply(new ComplexNumber(0.0D, 1.0D), angleHT[k] - 1.5707963267948966D);
/* 28 */         ComplexNumber magMultiplication = ComplexNumber.Multiply(ComplexNumber.Exp(exponentCalculation), absHT[k]);
/* 29 */         result[k] = magMultiplication.real;
/*    */       } 
/*    */       
/* 32 */       for (int m = 0; m < traceIn.length; m++) {
/* 33 */         this.traceData[m][i] = result[m];
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[][] getTraceData() {
/* 44 */     return this.traceData;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/PhaseShiftTraces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */