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
/*     */ public class Gaussian
/*     */ {
/*  36 */   private static final double SQRT2PI = Math.sqrt(6.283185307179586D);
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
/*     */   public static int[][][] BlurRGB(int[][][] raw, int rad, double intens) {
/*  48 */     int height = raw.length;
/*  49 */     int width = (raw[0]).length;
/*  50 */     double intensSquared2 = 2.0D * intens * intens;
/*     */     
/*  52 */     double invIntensSqrPi = 1.0D / SQRT2PI * intens;
/*  53 */     double norm = 0.0D;
/*  54 */     double[] mask = new double[2 * rad + 1];
/*  55 */     int[][][] outRGB = new int[height - 2 * rad][width - 2 * rad][3];
/*     */ 
/*     */     
/*  58 */     for (int x = -rad; x < rad + 1; x++) {
/*  59 */       double exp = Math.exp(-((x * x) / intensSquared2));
/*     */       
/*  61 */       mask[x + rad] = invIntensSqrPi * exp;
/*  62 */       norm += mask[x + rad];
/*     */     } 
/*     */     
/*     */     int r;
/*  66 */     for (r = rad; r < height - rad; r++) {
/*  67 */       for (int c = rad; c < width - rad; c++) {
/*  68 */         double[] sum = new double[3];
/*     */         
/*  70 */         for (int mr = -rad; mr < rad + 1; mr++) {
/*  71 */           for (int i = 0; i < 3; i++) {
/*  72 */             sum[i] = sum[i] + mask[mr + rad] * raw[r][c + mr][i];
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/*  77 */         for (int chan = 0; chan < 3; chan++) {
/*  78 */           sum[chan] = sum[chan] / norm;
/*  79 */           outRGB[r - rad][c - rad][chan] = (int)Math.round(sum[chan]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  85 */     for (r = rad; r < height - rad; r++) {
/*  86 */       for (int c = rad; c < width - rad; c++) {
/*  87 */         double[] sum = new double[3];
/*     */         
/*  89 */         for (int mr = -rad; mr < rad + 1; mr++) {
/*  90 */           for (int i = 0; i < 3; i++) {
/*  91 */             sum[i] = sum[i] + mask[mr + rad] * raw[r + mr][c][i];
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/*  96 */         for (int chan = 0; chan < 3; chan++) {
/*  97 */           sum[chan] = sum[chan] / norm;
/*  98 */           outRGB[r - rad][c - rad][chan] = (int)Math.round(sum[chan]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     return outRGB;
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
/*     */   public static int[][] BlurGS(int[][] raw, int rad, double intens) {
/* 116 */     int height = raw.length;
/* 117 */     int width = (raw[0]).length;
/* 118 */     double norm = 0.0D;
/* 119 */     double intensSquared2 = 2.0D * intens * intens;
/*     */     
/* 121 */     double invIntensSqrPi = 1.0D / SQRT2PI * intens;
/* 122 */     double[] mask = new double[2 * rad + 1];
/* 123 */     int[][] outGS = new int[height - 2 * rad][width - 2 * rad];
/*     */ 
/*     */     
/* 126 */     for (int x = -rad; x < rad + 1; x++) {
/* 127 */       double exp = Math.exp(-((x * x) / intensSquared2));
/*     */       
/* 129 */       mask[x + rad] = invIntensSqrPi * exp;
/* 130 */       norm += mask[x + rad];
/*     */     } 
/*     */     
/*     */     int r;
/* 134 */     for (r = rad; r < height - rad; r++) {
/* 135 */       for (int c = rad; c < width - rad; c++) {
/* 136 */         double sum = 0.0D;
/*     */         
/* 138 */         for (int mr = -rad; mr < rad + 1; mr++) {
/* 139 */           sum += mask[mr + rad] * raw[r][c + mr];
/*     */         }
/*     */ 
/*     */         
/* 143 */         sum /= norm;
/* 144 */         outGS[r - rad][c - rad] = (int)Math.round(sum);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 149 */     for (r = rad; r < height - rad; r++) {
/* 150 */       for (int c = rad; c < width - rad; c++) {
/* 151 */         double sum = 0.0D;
/*     */         
/* 153 */         for (int mr = -rad; mr < rad + 1; mr++) {
/* 154 */           sum += mask[mr + rad] * raw[r + mr][c];
/*     */         }
/*     */ 
/*     */         
/* 158 */         sum /= norm;
/* 159 */         outGS[r - rad][c - rad] = (int)Math.round(sum);
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     return outGS;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/Gaussian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */