/*     */ package Catalano.Math.Transforms;
/*     */ 
/*     */ import Catalano.Math.ComplexNumber;
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
/*     */ public class HilbertTransform
/*     */ {
/*     */   public static void FHTAbsolute(double[] data, FourierTransform.Direction direction) {
/*  44 */     int N = data.length;
/*     */ 
/*     */     
/*  47 */     if (direction == FourierTransform.Direction.Forward) {
/*     */ 
/*     */ 
/*     */       
/*  51 */       ComplexNumber[] cdata = new ComplexNumber[N]; int i;
/*  52 */       for (i = 0; i < N; i++) {
/*  53 */         cdata[i] = new ComplexNumber(data[i], 0.0D);
/*     */       }
/*     */       
/*  56 */       FourierTransform.FFT(cdata, FourierTransform.Direction.Forward);
/*     */ 
/*     */ 
/*     */       
/*  60 */       if (N % 2 == 0) {
/*     */         
/*  62 */         for (i = 1; i < N / 2; i++) {
/*  63 */           (cdata[i]).real *= 2.0D;
/*  64 */           (cdata[i]).imaginary *= 2.0D;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  69 */         for (i = N / 2 + 1; i < N; i++) {
/*  70 */           (cdata[i]).real = 0.0D;
/*  71 */           (cdata[i]).imaginary = 0.0D;
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/*  78 */         for (i = 1; i < (N + 1) / 2; i++) {
/*  79 */           (cdata[i]).real *= 2.0D;
/*  80 */           (cdata[i]).imaginary *= 2.0D;
/*     */         } 
/*     */         
/*  83 */         for (i = (N - 1) / 2 + 1; i < N; i++) {
/*  84 */           (cdata[i]).real *= 0.0D;
/*  85 */           (cdata[i]).imaginary *= 0.0D;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  91 */       FourierTransform.FFT(cdata, FourierTransform.Direction.Backward);
/*     */ 
/*     */       
/*  94 */       for (i = 0; i < N; i++) {
/*  95 */         data[i] = cdata[i].getMagnitude();
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 106 */       FHT(data, FourierTransform.Direction.Forward);
/*     */       
/* 108 */       for (int i = 0; i < data.length; i++) {
/* 109 */         data[i] = -data[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void FHT(double[] data, FourierTransform.Direction direction) {
/* 117 */     int N = data.length;
/*     */ 
/*     */     
/* 120 */     if (direction == FourierTransform.Direction.Forward) {
/*     */ 
/*     */ 
/*     */       
/* 124 */       ComplexNumber[] cdata = new ComplexNumber[N]; int i;
/* 125 */       for (i = 0; i < N; i++) {
/* 126 */         cdata[i] = new ComplexNumber(data[i], 0.0D);
/*     */       }
/*     */       
/* 129 */       FourierTransform.FFT(cdata, FourierTransform.Direction.Forward);
/*     */ 
/*     */ 
/*     */       
/* 133 */       if (N % 2 == 0) {
/*     */         
/* 135 */         for (i = 1; i < N / 2; i++) {
/* 136 */           (cdata[i]).real *= 2.0D;
/* 137 */           (cdata[i]).imaginary *= 2.0D;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 142 */         for (i = N / 2 + 1; i < N; i++) {
/* 143 */           (cdata[i]).real = 0.0D;
/* 144 */           (cdata[i]).imaginary = 0.0D;
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 151 */         for (i = 1; i < (N + 1) / 2; i++) {
/* 152 */           (cdata[i]).real *= 2.0D;
/* 153 */           (cdata[i]).imaginary *= 2.0D;
/*     */         } 
/*     */         
/* 156 */         for (i = (N - 1) / 2 + 1; i < N; i++) {
/* 157 */           (cdata[i]).real *= 0.0D;
/* 158 */           (cdata[i]).imaginary *= 0.0D;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 164 */       FourierTransform.FFT(cdata, FourierTransform.Direction.Backward);
/*     */ 
/*     */       
/* 167 */       for (i = 0; i < N; i++) {
/* 168 */         data[i] = (cdata[i]).imaginary;
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 179 */       FHT(data, FourierTransform.Direction.Forward);
/*     */       
/* 181 */       for (int i = 0; i < data.length; i++) {
/* 182 */         data[i] = -data[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void FHT(ComplexNumber[] data, FourierTransform.Direction direction) {
/* 192 */     int N = data.length;
/*     */ 
/*     */     
/* 195 */     if (direction == FourierTransform.Direction.Forward) {
/*     */ 
/*     */       
/* 198 */       ComplexNumber[] shift = (ComplexNumber[])data.clone();
/*     */ 
/*     */       
/* 201 */       FourierTransform.FFT(shift, FourierTransform.Direction.Backward);
/*     */       
/*     */       int i;
/* 204 */       for (i = 1; i < N / 2; i++) {
/* 205 */         (shift[i]).real *= 2.0D;
/* 206 */         (shift[i]).imaginary *= 2.0D;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 211 */       for (i = N / 2 + 1; i < N; i++) {
/* 212 */         (shift[i]).real = 0.0D;
/* 213 */         (shift[i]).imaginary = 0.0D;
/*     */       } 
/*     */ 
/*     */       
/* 217 */       FourierTransform.FFT(shift, FourierTransform.Direction.Forward);
/*     */ 
/*     */ 
/*     */       
/* 221 */       for (i = 0; i < N; i++) {
/* 222 */         (data[i]).imaginary = (shift[i]).imaginary;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 227 */       for (int i = 0; i < data.length; i++) {
/* 228 */         (data[i]).imaginary = 0.0D;
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
/* 239 */     int n = data.length;
/* 240 */     int m = (data[0]).length;
/* 241 */     ComplexNumber[] row = new ComplexNumber[Math.max(m, n)];
/*     */     
/* 243 */     for (int i = 0; i < n; i++) {
/*     */       int k;
/* 245 */       for (k = 0; k < n; k++) {
/* 246 */         row[k] = data[i][k];
/*     */       }
/* 248 */       FHT(row, direction);
/*     */       
/* 250 */       for (k = 0; k < n; k++) {
/* 251 */         data[i][k] = row[k];
/*     */       }
/*     */     } 
/*     */     
/* 255 */     ComplexNumber[] col = new ComplexNumber[n];
/*     */     
/* 257 */     for (int j = 0; j < n; j++) {
/*     */       int k;
/* 259 */       for (k = 0; k < n; k++) {
/* 260 */         col[k] = data[k][j];
/*     */       }
/* 262 */       FHT(col, direction);
/*     */       
/* 264 */       for (k = 0; k < n; k++)
/* 265 */         data[k][j] = col[k]; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Transforms/HilbertTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */