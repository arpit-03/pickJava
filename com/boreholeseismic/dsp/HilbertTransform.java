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
/*     */ public class HilbertTransform
/*     */ {
/*     */   public static void FHT(double[] data, FourierTransform.Direction direction) {
/*  14 */     int N = data.length;
/*     */ 
/*     */     
/*  17 */     if (direction == FourierTransform.Direction.Forward) {
/*     */ 
/*     */ 
/*     */       
/*  21 */       ComplexNumber[] cdata = new ComplexNumber[N]; int i;
/*  22 */       for (i = 0; i < N; i++) {
/*  23 */         cdata[i] = new ComplexNumber(data[i], 0.0D);
/*     */       }
/*     */       
/*  26 */       FourierTransform.FFT(cdata, FourierTransform.Direction.Forward);
/*     */ 
/*     */       
/*  29 */       for (i = 1; i < N / 2; i++) {
/*     */         
/*  31 */         (cdata[i]).real *= 2.0D;
/*  32 */         (cdata[i]).imaginary *= 2.0D;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  37 */       for (i = N / 2 + 1; i < N; i++) {
/*     */         
/*  39 */         (cdata[i]).real = 0.0D;
/*  40 */         (cdata[i]).imaginary = 0.0D;
/*     */       } 
/*     */ 
/*     */       
/*  44 */       FourierTransform.FFT(cdata, FourierTransform.Direction.Backward);
/*     */ 
/*     */       
/*  47 */       for (i = 0; i < N; i++) {
/*  48 */         data[i] = ComplexNumber.Abs(cdata[i]);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  62 */       FHT(data, FourierTransform.Direction.Forward);
/*     */       
/*  64 */       for (int i = 0; i < data.length; i++) {
/*  65 */         data[i] = -data[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void FHTAngle(double[] data, FourierTransform.Direction direction) {
/*  72 */     int N = data.length;
/*     */ 
/*     */     
/*  75 */     if (direction == FourierTransform.Direction.Forward) {
/*     */ 
/*     */ 
/*     */       
/*  79 */       ComplexNumber[] cdata = new ComplexNumber[N]; int i;
/*  80 */       for (i = 0; i < N; i++) {
/*  81 */         cdata[i] = new ComplexNumber(data[i], 0.0D);
/*     */       }
/*     */       
/*  84 */       FourierTransform.FFT(cdata, FourierTransform.Direction.Forward);
/*     */ 
/*     */       
/*  87 */       for (i = 1; i < N / 2; i++) {
/*     */         
/*  89 */         (cdata[i]).real *= 2.0D;
/*  90 */         (cdata[i]).imaginary *= 2.0D;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  95 */       for (i = N / 2 + 1; i < N; i++) {
/*     */         
/*  97 */         (cdata[i]).real = 0.0D;
/*  98 */         (cdata[i]).imaginary = 0.0D;
/*     */       } 
/*     */ 
/*     */       
/* 102 */       FourierTransform.FFT(cdata, FourierTransform.Direction.Backward);
/*     */ 
/*     */       
/* 105 */       for (i = 0; i < N; i++) {
/* 106 */         data[i] = Math.atan2((cdata[i]).imaginary, (cdata[i]).real);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 120 */       FHT(data, FourierTransform.Direction.Forward);
/*     */       
/* 122 */       for (int i = 0; i < data.length; i++) {
/* 123 */         data[i] = -data[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void FHT(ComplexNumber[] data, FourierTransform.Direction direction) {
/* 133 */     int N = data.length;
/*     */ 
/*     */     
/* 136 */     if (direction == FourierTransform.Direction.Forward) {
/*     */ 
/*     */       
/* 139 */       ComplexNumber[] shift = (ComplexNumber[])data.clone();
/*     */ 
/*     */       
/* 142 */       FourierTransform.FFT(shift, FourierTransform.Direction.Backward);
/*     */       
/*     */       int i;
/* 145 */       for (i = 1; i < N / 2; i++) {
/* 146 */         (shift[i]).real *= 2.0D;
/* 147 */         (shift[i]).imaginary *= 2.0D;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 152 */       for (i = N / 2 + 1; i < N; i++) {
/* 153 */         (shift[i]).real = 0.0D;
/* 154 */         (shift[i]).imaginary = 0.0D;
/*     */       } 
/*     */ 
/*     */       
/* 158 */       FourierTransform.FFT(shift, FourierTransform.Direction.Forward);
/*     */ 
/*     */ 
/*     */       
/* 162 */       for (i = 0; i < N; i++) {
/* 163 */         (data[i]).imaginary = (shift[i]).imaginary;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 168 */       for (int i = 0; i < data.length; i++) {
/* 169 */         (data[i]).imaginary = 0.0D;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void FHT2(ComplexNumber[][] data, FourierTransform.Direction direction) {
/* 180 */     int n = data.length;
/* 181 */     int m = (data[0]).length;
/* 182 */     ComplexNumber[] row = new ComplexNumber[Math.max(m, n)];
/*     */     
/* 184 */     for (int i = 0; i < n; i++) {
/*     */       int k;
/* 186 */       for (k = 0; k < n; k++) {
/* 187 */         row[k] = data[i][k];
/*     */       }
/* 189 */       FHT(row, direction);
/*     */       
/* 191 */       for (k = 0; k < n; k++) {
/* 192 */         data[i][k] = row[k];
/*     */       }
/*     */     } 
/*     */     
/* 196 */     ComplexNumber[] col = new ComplexNumber[n];
/*     */     
/* 198 */     for (int j = 0; j < n; j++) {
/*     */       int k;
/* 200 */       for (k = 0; k < n; k++) {
/* 201 */         col[k] = data[k][j];
/*     */       }
/* 203 */       FHT(col, direction);
/*     */       
/* 205 */       for (k = 0; k < n; k++)
/* 206 */         data[k][j] = col[k]; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/HilbertTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */