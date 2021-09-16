/*     */ package Catalano.Math.Wavelets;
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
/*     */ public class Haar
/*     */   implements IWavelet
/*     */ {
/*     */   private static final double w0 = 0.5D;
/*     */   private static final double w1 = -0.5D;
/*     */   private static final double s0 = 0.5D;
/*     */   private static final double s1 = 0.5D;
/*  37 */   private int levels = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Haar(int levels) {
/*  44 */     this.levels = levels;
/*     */   }
/*     */ 
/*     */   
/*     */   public void Forward(double[] data) {
/*  49 */     double[] temp = new double[data.length];
/*  50 */     int h = data.length >> 1;
/*     */     int i;
/*  52 */     for (i = 0; i < h; i++) {
/*  53 */       int k = i << 1;
/*  54 */       temp[i] = data[k] * 0.5D + data[k + 1] * 0.5D;
/*  55 */       temp[i + h] = data[k] * 0.5D + data[k + 1] * -0.5D;
/*     */     } 
/*     */     
/*  58 */     for (i = 0; i < data.length; i++) {
/*  59 */       data[i] = temp[i];
/*     */     }
/*     */   }
/*     */   
/*     */   public void Forward(double[][] data) {
/*  64 */     int rows = data.length;
/*  65 */     int cols = (data[0]).length;
/*     */     
/*  67 */     double[] row = new double[cols];
/*  68 */     double[] col = new double[rows];
/*     */     
/*  70 */     for (int k = 0; k < this.levels; k++) {
/*     */       
/*  72 */       for (int i = 0; i < rows; i++) {
/*     */         int m;
/*  74 */         for (m = 0; m < row.length; m++) {
/*  75 */           row[m] = data[i][m];
/*     */         }
/*  77 */         Forward(row);
/*     */         
/*  79 */         for (m = 0; m < row.length; m++) {
/*  80 */           data[i][m] = row[m];
/*     */         }
/*     */       } 
/*  83 */       for (int j = 0; j < cols; j++) {
/*     */         int m;
/*  85 */         for (m = 0; m < col.length; m++) {
/*  86 */           col[m] = data[m][j];
/*     */         }
/*  88 */         Forward(col);
/*     */         
/*  90 */         for (m = 0; m < col.length; m++) {
/*  91 */           data[m][j] = col[m];
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void Backward(double[] data) {
/*  98 */     double[] temp = new double[data.length];
/*  99 */     int h = data.length >> 1;
/*     */     int i;
/* 101 */     for (i = 0; i < h; i++) {
/* 102 */       int k = i << 1;
/* 103 */       temp[k] = (data[i] * 0.5D + data[i + h] * 0.5D) / 0.5D;
/* 104 */       temp[k + 1] = (data[i] * 0.5D + data[i + h] * -0.5D) / 0.5D;
/*     */     } 
/*     */     
/* 107 */     for (i = 0; i < data.length; i++) {
/* 108 */       data[i] = temp[i];
/*     */     }
/*     */   }
/*     */   
/*     */   public void Backward(double[][] data) {
/* 113 */     int rows = data.length;
/* 114 */     int cols = (data[0]).length;
/*     */     
/* 116 */     double[] row = new double[cols];
/* 117 */     double[] col = new double[rows];
/*     */     
/* 119 */     for (int l = 0; l < this.levels; l++) {
/* 120 */       for (int j = 0; j < cols; j++) {
/* 121 */         int k; for (k = 0; k < row.length; k++) {
/* 122 */           col[k] = data[k][j];
/*     */         }
/* 124 */         Backward(col);
/*     */         
/* 126 */         for (k = 0; k < col.length; k++) {
/* 127 */           data[k][j] = col[k];
/*     */         }
/*     */       } 
/* 130 */       for (int i = 0; i < rows; i++) {
/* 131 */         int k; for (k = 0; k < row.length; k++) {
/* 132 */           row[k] = data[i][k];
/*     */         }
/* 134 */         Backward(row);
/*     */         
/* 136 */         for (k = 0; k < row.length; k++)
/* 137 */           data[i][k] = row[k]; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Wavelets/Haar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */