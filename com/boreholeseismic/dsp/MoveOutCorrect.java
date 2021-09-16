/*     */ package com.boreholeseismic.dsp;
/*     */ 
/*     */ public class MoveOutCorrect
/*     */ {
/*   5 */   public double pMax = 0.0D; public double sMax = 0.0D; public double allMax = 0.0D; private double[][] traceData;
/*     */   private double[] pickP;
/*     */   private double[] pickS;
/*     */   public double pFlatten;
/*     */   public double sFlatten;
/*     */   
/*     */   public MoveOutCorrect(double[][] traceIn, double[][] pickOutP, double[][] pickOutS, double timeSample) {
/*  12 */     this.traceData = traceIn;
/*     */     
/*  14 */     this.pickP = new double[pickOutP.length];
/*  15 */     this.pickS = new double[pickOutP.length];
/*     */     
/*  17 */     double maxTime = traceIn.length * timeSample;
/*  18 */     double timeMin = 0.0D, timeMax = maxTime, timeFlatten = maxTime * 0.5D;
/*  19 */     int sampleMin = 0, sampleMax = traceIn.length, sampleFlatten = (int)(traceIn.length * 0.5D);
/*  20 */     double minP = pickOutP[0][1], maxP = pickOutP[0][1];
/*  21 */     double minS = pickOutS[0][1], maxS = pickOutS[0][1];
/*     */ 
/*     */     
/*  24 */     for (int i = 0; i < pickOutP.length; i++) {
/*     */       
/*  26 */       this.pickP[i] = pickOutP[i][1];
/*     */       
/*  28 */       if (this.pickP[i] < minP) {
/*  29 */         minP = this.pickP[i];
/*     */       }
/*  31 */       if (this.pickP[i] > maxP) {
/*  32 */         maxP = this.pickP[i];
/*     */       }
/*  34 */       this.pickS[i] = pickOutS[i][1];
/*     */       
/*  36 */       if (this.pickS[i] < minS) {
/*  37 */         minS = this.pickS[i];
/*     */       }
/*  39 */       if (this.pickS[i] > maxS) {
/*  40 */         maxS = this.pickS[i];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  45 */     timeMin = Math.min(minP - maxP - minP, minS - maxS - minS);
/*  46 */     timeMax = Math.max(maxP + maxP - minP, maxS += maxS - minS);
/*     */     
/*  48 */     timeFlatten = Math.min(minP, minS);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     this.pFlatten = timeFlatten;
/*  54 */     this.sFlatten = timeFlatten;
/*     */ 
/*     */     
/*  57 */     if (timeMin < 0.0D) {
/*  58 */       timeMin = 0.0D;
/*     */     }
/*  60 */     if (timeMax > maxTime) {
/*  61 */       timeMax = maxTime - 1.0D;
/*     */     }
/*     */     
/*  64 */     sampleMin = (int)((int)timeMin / timeSample);
/*  65 */     sampleMax = (int)((int)timeMax / timeSample);
/*     */ 
/*     */     
/*  68 */     double[][] moveOutCorrectedP = new double[traceIn.length][(traceIn[0]).length];
/*  69 */     double[][] moveOutCorrectedS = new double[traceIn.length][(traceIn[0]).length];
/*     */     int k;
/*  71 */     for (k = 0; k < pickOutP.length; k++) {
/*     */       
/*  73 */       if (pickOutP[k][1] != Double.NaN) {
/*     */ 
/*     */ 
/*     */         
/*  77 */         int diffP = (int)Math.round((pickOutP[k][1] - this.pFlatten) / timeSample);
/*     */         
/*  79 */         if (diffP >= 0)
/*     */         {
/*     */ 
/*     */           
/*  83 */           for (int m = 3 * k; m < 3 * k + 2; m++) {
/*  84 */             for (int n = 0; n < traceIn.length - diffP; n++)
/*     */             {
/*  86 */               moveOutCorrectedP[n][m] = traceIn[n + diffP][m];
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*  92 */     for (k = 0; k < pickOutS.length; k++) {
/*     */       
/*  94 */       if (pickOutS[k][1] != Double.NaN) {
/*     */ 
/*     */ 
/*     */         
/*  98 */         int diffS = (int)Math.round((pickOutS[k][1] - this.sFlatten) / timeSample);
/*  99 */         if (diffS >= 0)
/*     */         {
/*     */ 
/*     */           
/* 103 */           for (int m = 3 * k; m < 3 * k + 2; m++) {
/* 104 */             for (int n = 0; n < traceIn.length - diffS; n++)
/*     */             {
/* 106 */               moveOutCorrectedS[n][m] = traceIn[n + diffS][m];
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 115 */     this.traceData = new double[traceIn.length][3];
/*     */ 
/*     */     
/* 118 */     double pMaxAmp = 0.0D, sMaxAmp = 0.0D, allMaxAmp = 0.0D;
/*     */     
/* 120 */     for (int j = 0; j < (traceIn[0]).length; j++) {
/* 121 */       for (int m = 0; m < traceIn.length; m++) {
/* 122 */         this.traceData[m][0] = this.traceData[m][0] + moveOutCorrectedP[m][j];
/* 123 */         this.traceData[m][1] = this.traceData[m][1] + moveOutCorrectedS[m][j];
/* 124 */         this.traceData[m][2] = this.traceData[m][2] + moveOutCorrectedP[m][j] + moveOutCorrectedS[m][j];
/*     */         
/* 126 */         if ((((m > sampleMin) ? 1 : 0) & ((m < sampleMax) ? 1 : 0)) != 0) {
/*     */           
/* 128 */           if (Math.abs(this.traceData[m][0]) > pMaxAmp) {
/* 129 */             pMaxAmp = Math.abs(this.traceData[m][0]);
/* 130 */             this.pMax = m * timeSample;
/*     */           } 
/*     */           
/* 133 */           if (Math.abs(this.traceData[m][1]) > sMaxAmp) {
/* 134 */             sMaxAmp = Math.abs(this.traceData[m][1]);
/* 135 */             this.sMax = m * timeSample;
/*     */           } 
/*     */           
/* 138 */           if (Math.abs(this.traceData[m][2]) > allMaxAmp) {
/* 139 */             allMaxAmp = Math.abs(this.traceData[m][2]);
/* 140 */             this.allMax = m * timeSample;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getTraceData() {
/* 151 */     return this.traceData;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/MoveOutCorrect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */