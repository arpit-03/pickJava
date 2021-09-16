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
/*     */ public class DiscreteSineTransform
/*     */ {
/*     */   public static void Forward(double[] data) {
/*  44 */     Forward(data, 1.0D);
/*     */   }
/*     */   
/*     */   private static void Forward(double[] data, double inverse) {
/*  48 */     double[] result = new double[data.length];
/*     */     
/*  50 */     for (int k = 1; k < result.length + 1; k++) {
/*  51 */       double sum = 0.0D;
/*  52 */       for (int j = 1; j < data.length + 1; j++) {
/*  53 */         sum += data[j - 1] * Math.sin(Math.PI * (k * j) / (data.length + 1.0D));
/*     */       }
/*  55 */       result[k - 1] = sum * inverse;
/*     */     } 
/*     */     
/*  58 */     for (int i = 0; i < data.length; i++) {
/*  59 */       data[i] = result[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Backward(double[] data) {
/*  68 */     double inverse = 2.0D / (data.length + 1);
/*  69 */     Forward(data, inverse);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Forward(double[][] data) {
/*  77 */     Forward(data, 1.0D);
/*     */   }
/*     */   
/*     */   private static void Forward(double[][] data, double inverse) {
/*  81 */     int rows = data.length;
/*  82 */     int cols = (data[0]).length;
/*     */     
/*  84 */     double[] row = new double[cols];
/*  85 */     double[] col = new double[rows];
/*     */     
/*  87 */     for (int i = 0; i < rows; i++) {
/*     */       int k;
/*  89 */       for (k = 0; k < row.length; k++) {
/*  90 */         row[k] = data[i][k];
/*     */       }
/*  92 */       Forward(row, inverse);
/*     */       
/*  94 */       for (k = 0; k < row.length; k++) {
/*  95 */         data[i][k] = row[k];
/*     */       }
/*     */     } 
/*  98 */     for (int j = 0; j < cols; j++) {
/*     */       int k;
/* 100 */       for (k = 0; k < col.length; k++) {
/* 101 */         col[k] = data[k][j];
/*     */       }
/* 103 */       Forward(col, inverse);
/*     */       
/* 105 */       for (k = 0; k < col.length; k++) {
/* 106 */         data[k][j] = col[k];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void Backward(double[][] data) {
/* 115 */     double inverse = 2.0D / (data.length + 1);
/* 116 */     Forward(data, inverse);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/DiscreteSineTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */