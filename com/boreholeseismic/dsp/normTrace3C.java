/*     */ package com.boreholeseismic.dsp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class normTrace3C
/*     */ {
/*     */   private final double[][] traceValue;
/*     */   public double[] maxPoints;
/*     */   
/*     */   public normTrace3C(double[][] traceIn, String display_type, String normalization_type) {
/*  17 */     this.traceValue = traceIn;
/*     */     
/*  19 */     double[][] absoluteTrace = traceIn;
/*  20 */     int rows = absoluteTrace.length;
/*  21 */     int cols = (absoluteTrace[0]).length;
/*  22 */     double[] maxValues = new double[(absoluteTrace[0]).length];
/*     */     int i;
/*  24 */     for (i = 0; i < maxValues.length; i++) {
/*  25 */       maxValues[i] = Math.abs(absoluteTrace[0][i]);
/*     */     }
/*     */     
/*  28 */     for (i = 0; i < cols; i++) {
/*  29 */       for (int j = 0; j < rows; j++) {
/*     */         
/*  31 */         if (maxValues[i] < Math.abs(absoluteTrace[j][i])) {
/*  32 */           maxValues[i] = Math.abs(absoluteTrace[j][i]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  40 */     for (i = 0; i < cols; i += 3) {
/*  41 */       double temp = Math.max(maxValues[i], Math.max(maxValues[i + 1], maxValues[i + 2]));
/*  42 */       maxValues[i] = temp;
/*  43 */       maxValues[i + 1] = temp;
/*  44 */       maxValues[i + 2] = temp;
/*     */     } 
/*     */     
/*  47 */     if (normalization_type == "Entire Trace") {
/*     */       
/*  49 */       double temp = 0.0D;
/*     */       int j;
/*  51 */       for (j = 0; j < cols; j++) {
/*  52 */         temp = Math.max(temp, maxValues[j]);
/*     */       }
/*     */       
/*  55 */       for (j = 0; j < cols; j++) {
/*  56 */         maxValues[j] = temp;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  61 */     for (i = 0; i < cols; i++) {
/*  62 */       for (int j = 0; j < rows; j++) {
/*  63 */         if (display_type == "Wiggle") {
/*  64 */           this.traceValue[j][i] = (1 + i / 3) + 0.9D * this.traceValue[j][i] / maxValues[i];
/*     */         } else {
/*     */           
/*  67 */           this.traceValue[j][i] = this.traceValue[j][i] / maxValues[i];
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public normTrace3C(double[][] traceIn, int startSamp, int endSamp) {
/*  75 */     this.traceValue = traceIn;
/*     */     
/*  77 */     double[][] absoluteTrace = traceIn;
/*  78 */     int rows = absoluteTrace.length;
/*  79 */     int cols = (absoluteTrace[0]).length;
/*  80 */     double[] maxValues = new double[(absoluteTrace[0]).length]; int i;
/*  81 */     for (i = 0; i < maxValues.length; i++) {
/*  82 */       maxValues[i] = Math.abs(absoluteTrace[startSamp][i]);
/*     */     }
/*  84 */     for (i = 0; i < cols; i++) {
/*  85 */       for (int j = startSamp + 1; j < endSamp; j++) {
/*  86 */         if (maxValues[i] < Math.abs(absoluteTrace[j][i])) {
/*  87 */           maxValues[i] = Math.abs(absoluteTrace[j][i]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  93 */     for (i = 0; i < cols; i += 3) {
/*     */       
/*  95 */       double temp = Math.max(maxValues[i], Math.max(maxValues[i + 1], maxValues[i + 2]));
/*  96 */       maxValues[i] = temp;
/*  97 */       maxValues[i + 1] = temp;
/*  98 */       maxValues[i + 2] = temp;
/*     */     } 
/*     */     
/* 101 */     for (i = 0; i < cols; i++) {
/* 102 */       int j; for (j = 0; j < startSamp + 1; j++) {
/* 103 */         this.traceValue[j][i] = (1 + i / 3);
/*     */       }
/* 105 */       for (j = startSamp + 1; j < endSamp; j++) {
/* 106 */         this.traceValue[j][i] = (1 + i / 3) + 0.9D * this.traceValue[j][i] / maxValues[i];
/*     */       }
/* 108 */       for (j = endSamp; j < rows; j++) {
/* 109 */         this.traceValue[j][i] = (1 + i / 3);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public normTrace3C(double[][] traceIn, double scalar) {
/* 116 */     this.traceValue = traceIn;
/*     */     
/* 118 */     double[][] absoluteTrace = traceIn;
/* 119 */     int rows = absoluteTrace.length;
/* 120 */     int cols = (absoluteTrace[0]).length;
/* 121 */     double[] maxValues = new double[(absoluteTrace[0]).length];
/* 122 */     this.maxPoints = new double[(absoluteTrace[0]).length];
/*     */     int i;
/* 124 */     for (i = 0; i < maxValues.length; i++) {
/* 125 */       maxValues[i] = Math.abs(absoluteTrace[0][i]);
/*     */     }
/*     */     
/* 128 */     for (i = 0; i < cols; i++) {
/* 129 */       for (int j = 0; j < rows; j++) {
/* 130 */         if (maxValues[i] < Math.abs(absoluteTrace[j][i])) {
/* 131 */           maxValues[i] = Math.abs(absoluteTrace[j][i]);
/* 132 */           this.maxPoints[i] = j;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 139 */     for (i = 0; i < cols; i += 3) {
/* 140 */       double temp = Math.max(maxValues[i], Math.max(maxValues[i + 1], maxValues[i + 2]));
/* 141 */       maxValues[i] = temp;
/* 142 */       maxValues[i + 1] = temp;
/* 143 */       maxValues[i + 2] = temp;
/*     */     } 
/*     */     
/* 146 */     for (i = 0; i < cols; i++) {
/* 147 */       for (int j = 0; j < rows; j++) {
/* 148 */         this.traceValue[j][i] = (1 + i / 3) + scalar * this.traceValue[j][i] / maxValues[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public normTrace3C(double[][] traceIn, double scalar, boolean methodForPicksQC) {
/* 154 */     this.traceValue = traceIn;
/*     */     
/* 156 */     double[][] absoluteTrace = traceIn;
/* 157 */     int rows = absoluteTrace.length;
/* 158 */     int cols = (absoluteTrace[0]).length;
/* 159 */     double[] maxValues = new double[(absoluteTrace[0]).length];
/* 160 */     this.maxPoints = new double[(absoluteTrace[0]).length];
/*     */     int i;
/* 162 */     for (i = 0; i < maxValues.length; i++) {
/* 163 */       maxValues[i] = Math.abs(absoluteTrace[0][i]);
/*     */     }
/*     */     
/* 166 */     for (i = 0; i < cols; i++) {
/* 167 */       for (int j = 0; j < rows; j++) {
/* 168 */         if (maxValues[i] < Math.abs(absoluteTrace[j][i])) {
/* 169 */           maxValues[i] = Math.abs(absoluteTrace[j][i]);
/* 170 */           this.maxPoints[i] = j;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 177 */     for (i = 0; i < cols; i += 3) {
/* 178 */       double temp = Math.max(maxValues[i], Math.max(maxValues[i + 1], maxValues[i + 2]));
/* 179 */       maxValues[i] = temp;
/* 180 */       maxValues[i + 1] = temp;
/* 181 */       maxValues[i + 2] = temp;
/*     */     } 
/*     */     
/* 184 */     for (i = 0; i < cols; i++) {
/* 185 */       for (int j = 0; j < rows; j++) {
/* 186 */         this.traceValue[j][i] = (1 + i) + scalar * this.traceValue[j][i] / maxValues[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getTraceData() {
/* 196 */     return this.traceValue;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/normTrace3C.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */