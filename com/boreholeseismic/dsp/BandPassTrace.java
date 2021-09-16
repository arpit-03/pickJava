/*    */ package com.boreholeseismic.dsp;
/*    */ 
/*    */ import edu.mines.jtk.dsp.BandPassFilter;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JOptionPane;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BandPassTrace
/*    */ {
/*    */   private double[][] traceData;
/*    */   
/*    */   public BandPassTrace(double[][] traceIn, double freqLower, double freqUpper, double transWidth, double amplitudeError, double samplingTime) {
/* 15 */     float[][] floatArray = new float[traceIn.length][(traceIn[0]).length];
/* 16 */     double nyquistHz = 1.0D / samplingTime;
/*    */     
/* 18 */     for (int i = 0; i < traceIn.length; i++) {
/* 19 */       for (int k = 0; k < (traceIn[0]).length; k++)
/*    */       {
/* 21 */         floatArray[i][k] = (float)traceIn[i][k];
/*    */       }
/*    */     } 
/* 24 */     BandPassFilter Filter = new BandPassFilter(freqLower / nyquistHz, freqUpper / nyquistHz, transWidth / nyquistHz, amplitudeError);
/*    */     
/* 26 */     this.traceData = new double[traceIn.length][(traceIn[0]).length];
/*    */     
/* 28 */     for (int j = 0; j < (traceIn[0]).length; j++) {
/*    */       
/* 30 */       float[] temp = new float[traceIn.length]; int k;
/* 31 */       for (k = 0; k < traceIn.length; k++) {
/* 32 */         temp[k] = floatArray[k][j];
/*    */       }
/* 34 */       Filter.apply(temp, temp);
/*    */       
/* 36 */       for (k = 0; k < traceIn.length; k++) {
/* 37 */         this.traceData[k][j] = temp[k];
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public BandPassTrace(double[][] traceIn, double[] a, double[] b, double[] z) {
/* 44 */     if (a.length != b.length) {
/*    */       
/* 46 */       JOptionPane.showMessageDialog(new JFrame("Filter Issue!"), "Please check A & B Coef. Unequal size");
/*    */       
/*    */       return;
/*    */     } 
/* 50 */     if (z.length != b.length - 1) {
/*    */       
/* 52 */       JOptionPane.showMessageDialog(new JFrame("Filter Issue!"), "Please check Z Coef. Unequal size");
/*    */       
/*    */       return;
/*    */     } 
/* 56 */     this.traceData = new double[traceIn.length][(traceIn[0]).length];
/*    */     
/* 58 */     for (int i = 0; i < (traceIn[0]).length; i++) {
/*    */ 
/*    */       
/* 61 */       double[] temp = new double[traceIn.length];
/* 62 */       for (int j = 0; j < traceIn.length; j++) {
/* 63 */         temp[j] = traceIn[j][i];
/*    */       }
/*    */       
/* 66 */       Filter Trial = new Filter(b, a, temp, z);
/* 67 */       temp = Trial.getFilterOutput();
/*    */       
/* 69 */       for (int k = 0; k < traceIn.length; k++) {
/* 70 */         this.traceData[k][i] = temp[k];
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[][] getTraceData() {
/* 79 */     return this.traceData;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/BandPassTrace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */