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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DiscreteCosineTransform
/*     */ {
/*     */   public static void Forward(double[] data) {
/*  45 */     double[] result = new double[data.length];
/*     */     
/*  47 */     double scale = Math.sqrt(2.0D / data.length);
/*  48 */     for (int f = 0; f < data.length; f++) {
/*  49 */       double sum = 0.0D;
/*  50 */       for (int t = 0; t < data.length; t++) {
/*  51 */         double cos = Math.cos((2.0D * t + 1.0D) * f * Math.PI / 2.0D * data.length);
/*  52 */         sum += data[t] * cos * alpha(f);
/*     */       } 
/*  54 */       result[f] = scale * sum;
/*     */     } 
/*  56 */     for (int i = 0; i < data.length; i++) {
/*  57 */       data[i] = result[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Forward(double[][] data) {
/*  66 */     int rows = data.length;
/*  67 */     int cols = (data[0]).length;
/*     */     
/*  69 */     double[] row = new double[cols];
/*  70 */     double[] col = new double[rows];
/*     */     
/*  72 */     for (int i = 0; i < rows; i++) {
/*     */       int k;
/*  74 */       for (k = 0; k < row.length; k++) {
/*  75 */         row[k] = data[i][k];
/*     */       }
/*  77 */       Forward(row);
/*     */       
/*  79 */       for (k = 0; k < row.length; k++) {
/*  80 */         data[i][k] = row[k];
/*     */       }
/*     */     } 
/*  83 */     for (int j = 0; j < cols; j++) {
/*     */       int k;
/*  85 */       for (k = 0; k < col.length; k++) {
/*  86 */         col[k] = data[k][j];
/*     */       }
/*  88 */       Forward(col);
/*     */       
/*  90 */       for (k = 0; k < col.length; k++) {
/*  91 */         data[k][j] = col[k];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Backward(double[] data) {
/* 101 */     double[] result = new double[data.length];
/*     */     
/* 103 */     double scale = Math.sqrt(2.0D / data.length);
/* 104 */     for (int t = 0; t < data.length; t++) {
/* 105 */       double sum = 0.0D;
/* 106 */       for (int j = 0; j < data.length; j++) {
/* 107 */         double cos = Math.cos(((2 * t + 1) * j) * Math.PI / (2 * data.length));
/* 108 */         sum += alpha(j) * data[j] * cos;
/*     */       } 
/* 110 */       result[t] = scale * sum;
/*     */     } 
/* 112 */     for (int i = 0; i < data.length; i++) {
/* 113 */       data[i] = result[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Backward(double[][] data) {
/* 122 */     int rows = data.length;
/* 123 */     int cols = (data[0]).length;
/*     */     
/* 125 */     double[] row = new double[cols];
/* 126 */     double[] col = new double[rows];
/*     */     
/* 128 */     for (int j = 0; j < cols; j++) {
/* 129 */       int k; for (k = 0; k < row.length; k++) {
/* 130 */         col[k] = data[k][j];
/*     */       }
/* 132 */       Backward(col);
/*     */       
/* 134 */       for (k = 0; k < col.length; k++) {
/* 135 */         data[k][j] = col[k];
/*     */       }
/*     */     } 
/* 138 */     for (int i = 0; i < rows; i++) {
/* 139 */       int k; for (k = 0; k < row.length; k++) {
/* 140 */         row[k] = data[i][k];
/*     */       }
/* 142 */       Backward(row);
/*     */       
/* 144 */       for (k = 0; k < row.length; k++)
/* 145 */         data[i][k] = row[k]; 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static double alpha(double v) {
/* 150 */     if (v == 0.0D) {
/* 151 */       return 1.0D / Math.sqrt(2.0D);
/*     */     }
/* 153 */     return 1.0D;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/DiscreteCosineTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */