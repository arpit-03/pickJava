/*     */ package com.boreholeseismic.dsp;
/*     */ 
/*     */ import Catalano.Math.Matrix;
/*     */ import com.boreholeseismic.imageproc.ImageUtils;
/*     */ import java.awt.image.BufferedImage;
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
/*     */ public class JCanny
/*     */ {
/*     */   private static int stDev;
/*     */   private static int mean;
/*     */   private static int numDev;
/*     */   private static double tHi;
/*     */   private static double tLo;
/*     */   private static double tFract;
/*     */   private static int[][] dir;
/*     */   private static int[][] gx;
/*     */   private static int[][] gy;
/*     */   private static double[][] mag;
/*     */   
/*     */   public static BufferedImage CannyEdges(BufferedImage img, int numberDeviations, double fract) {
/*  55 */     int[][] raw = null;
/*  56 */     int[][] blurred = null;
/*  57 */     BufferedImage edges = null;
/*  58 */     numDev = numberDeviations;
/*  59 */     tFract = fract;
/*     */ 
/*     */     
/*  62 */     if (img != null && numberDeviations > 0 && fract > 0.0D) {
/*  63 */       raw = ImageUtils.GSArray(img);
/*     */       
/*  65 */       blurred = Gaussian.BlurGS(raw, 3, 1.4D);
/*  66 */       gx = Sobel.Horizontal(blurred);
/*  67 */       gy = Sobel.Vertical(blurred);
/*     */       
/*  69 */       Magnitude();
/*  70 */       Direction();
/*  71 */       Suppression();
/*     */       
/*  73 */       edges = ImageUtils.GSImg(Hysteresis());
/*     */     } 
/*     */     
/*  76 */     return edges;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void Magnitude() {
/*  85 */     double sum = 0.0D;
/*  86 */     double var = 0.0D;
/*  87 */     int height = gx.length;
/*  88 */     int width = (gx[0]).length;
/*  89 */     double pixelTotal = (height * width);
/*  90 */     mag = new double[height][width];
/*     */     int r;
/*  92 */     for (r = 0; r < height; r++) {
/*  93 */       for (int c = 0; c < width; c++) {
/*  94 */         mag[r][c] = Math.sqrt((gx[r][c] * gx[r][c] + gy[r][c] * gy[r][c]));
/*     */         
/*  96 */         sum += mag[r][c];
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     mean = (int)Math.round(sum / pixelTotal);
/*     */ 
/*     */     
/* 103 */     for (r = 0; r < height; r++) {
/* 104 */       for (int c = 0; c < width; c++) {
/* 105 */         double diff = mag[r][c] - mean;
/*     */         
/* 107 */         var += diff * diff;
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     stDev = (int)Math.sqrt(var / pixelTotal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void Direction() {
/* 120 */     int height = gx.length;
/* 121 */     int width = (gx[0]).length;
/* 122 */     double piRad = 57.29577951308232D;
/* 123 */     dir = new int[height][width];
/*     */     
/* 125 */     for (int r = 0; r < height; r++) {
/* 126 */       for (int c = 0; c < width; c++) {
/* 127 */         double angle = Math.atan2(gy[r][c], gx[r][c]) * piRad;
/*     */ 
/*     */         
/* 130 */         if (angle < 0.0D) {
/* 131 */           angle += 360.0D;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 136 */         if (angle <= 22.5D || (angle >= 157.5D && angle <= 202.5D) || angle >= 337.5D) {
/* 137 */           dir[r][c] = 0;
/* 138 */         } else if ((angle >= 22.5D && angle <= 67.5D) || (angle >= 202.5D && angle <= 247.5D)) {
/* 139 */           dir[r][c] = 45;
/* 140 */         } else if ((angle >= 67.5D && angle <= 112.5D) || (angle >= 247.5D && angle <= 292.5D)) {
/* 141 */           dir[r][c] = 90;
/*     */         } else {
/* 143 */           dir[r][c] = 135;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void Suppression() {
/* 155 */     int height = mag.length - 1;
/* 156 */     int width = (mag[0]).length - 1;
/*     */     
/* 158 */     for (int r = 1; r < height; r++) {
/* 159 */       for (int c = 1; c < width; c++) {
/* 160 */         double magnitude = mag[r][c];
/*     */         
/* 162 */         switch (dir[r][c]) {
/*     */           case 0:
/* 164 */             if (magnitude < mag[r][c - 1] && magnitude < mag[r][c + 1]) {
/* 165 */               mag[r - 1][c - 1] = 0.0D;
/*     */             }
/*     */             break;
/*     */           case 45:
/* 169 */             if (magnitude < mag[r - 1][c + 1] && magnitude < mag[r + 1][c - 1]) {
/* 170 */               mag[r - 1][c - 1] = 0.0D;
/*     */             }
/*     */             break;
/*     */           case 90:
/* 174 */             if (magnitude < mag[r - 1][c] && magnitude < mag[r + 1][c]) {
/* 175 */               mag[r - 1][c - 1] = 0.0D;
/*     */             }
/*     */             break;
/*     */           case 135:
/* 179 */             if (magnitude < mag[r - 1][c - 1] && magnitude < mag[r + 1][c + 1]) {
/* 180 */               mag[r - 1][c - 1] = 0.0D;
/*     */             }
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double[] smooth1DArray(double[] rawData, double window) {
/* 193 */     int halfSpace = (int)Math.round((window - 1.0D) / 2.0D);
/*     */     
/* 195 */     double[] start1 = Matrix.CreateMatrix1D(halfSpace + 1, 1.0D);
/* 196 */     double[] start2 = Matrix.CreateMatrix1D(rawData.length - halfSpace - 1, 0.0D);
/*     */     
/* 198 */     for (int i = 0; i < start2.length; i++) {
/* 199 */       start2[i] = (i + 2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     double[] startMerged = merge(start1, start2);
/*     */     
/* 209 */     double[] stop1 = Matrix.CreateMatrix1D(rawData.length - halfSpace, 0.0D);
/*     */     
/* 211 */     for (int j = 0; j < stop1.length; j++) {
/* 212 */       stop1[j] = (j + halfSpace + 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 220 */     double[] stop2 = Matrix.CreateMatrix1D(halfSpace, rawData.length);
/* 221 */     double[] stopMerged = merge(stop1, stop2);
/*     */     
/* 223 */     double[] divide = Matrix.Subtract(stopMerged, startMerged);
/* 224 */     Matrix.Add(divide, 1.0D);
/*     */     
/* 226 */     double[] cumulativeSum = new double[rawData.length];
/*     */     
/* 228 */     for (int k = 0; k < rawData.length; k++) {
/* 229 */       for (int n = 0; n < k + 1; n++) {
/* 230 */         cumulativeSum[k] = cumulativeSum[k] + rawData[n];
/*     */       }
/*     */     } 
/*     */     
/* 234 */     double[] tempSum = new double[rawData.length];
/* 235 */     double[] result = new double[rawData.length];
/*     */     
/* 237 */     for (int m = 0; m < tempSum.length; m++) {
/* 238 */       tempSum[m] = cumulativeSum[(int)(stopMerged[m] - 1.0D)] - cumulativeSum[(int)Math.max(0.0D, startMerged[m] - 2.0D)];
/*     */       
/* 240 */       if (startMerged[m] == 1.0D) {
/* 241 */         tempSum[m] = tempSum[m] + rawData[0];
/*     */       }
/*     */       
/* 244 */       result[m] = tempSum[m] / divide[m];
/*     */     } 
/*     */     
/* 247 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] merge(double[] traceA, double[] traceB) {
/* 254 */     double[] traceOut = new double[traceA.length + traceB.length];
/*     */     
/*     */     int j;
/* 257 */     for (j = 0; j < traceA.length; j++) {
/* 258 */       traceOut[j] = traceA[j];
/*     */     }
/*     */     
/* 261 */     for (j = 0; j < traceB.length; j++) {
/* 262 */       traceOut[traceA.length + j] = traceB[j];
/*     */     }
/*     */     
/* 265 */     return traceOut;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double[][] smoothdata(double[][] smoothImg, int dimension, double window) {
/* 272 */     if (window % 2.0D == 0.0D) {
/* 273 */       window--;
/*     */     }
/*     */     
/* 276 */     double n = window;
/* 277 */     double[][] input = smoothImg;
/*     */ 
/*     */     
/* 280 */     return smoothImg;
/*     */   }
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
/*     */   private static int[][] Hysteresis() {
/* 313 */     int height = mag.length - 1;
/* 314 */     int width = (mag[0]).length - 1;
/* 315 */     int[][] bin = new int[height - 1][width - 1];
/*     */     
/* 317 */     tHi = (mean + numDev * stDev);
/* 318 */     tLo = tHi * tFract;
/*     */     
/* 320 */     for (int r = 1; r < height; r++) {
/* 321 */       for (int c = 1; c < width; c++) {
/* 322 */         double magnitude = mag[r][c];
/*     */         
/* 324 */         if (magnitude >= tHi) {
/* 325 */           bin[r - 1][c - 1] = 255;
/* 326 */         } else if (magnitude < tLo) {
/* 327 */           bin[r - 1][c - 1] = 0;
/*     */         } else {
/* 329 */           boolean connected = false;
/*     */           
/* 331 */           for (int nr = -1; nr < 2; nr++) {
/* 332 */             for (int nc = -1; nc < 2; nc++) {
/* 333 */               if (mag[r + nr][c + nc] >= tHi) {
/* 334 */                 connected = true;
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/* 339 */           bin[r - 1][c - 1] = connected ? 255 : 0;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 344 */     return bin;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/JCanny.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */