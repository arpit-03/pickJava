/*     */ package com.boreholeseismic.imageproc;
/*     */ 
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
/*     */ public class ImageUtils
/*     */ {
/*     */   public static int[][][] RGBArray(BufferedImage img) {
/*  38 */     int[][][] rgb = null;
/*  39 */     int height = img.getHeight();
/*  40 */     int width = img.getWidth();
/*     */     
/*  42 */     if (height > 0 && width > 0) {
/*  43 */       rgb = new int[height][width][3];
/*     */       
/*  45 */       for (int row = 0; row < height; row++) {
/*  46 */         for (int column = 0; column < width; column++) {
/*  47 */           rgb[row][column] = intRGB(img.getRGB(column, row));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  52 */     return rgb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage RGBImg(int[][][] raw) {
/*  62 */     BufferedImage img = null;
/*  63 */     int height = raw.length;
/*  64 */     int width = (raw[0]).length;
/*     */     
/*  66 */     if ((height > 0 && width > 0) || (raw[0][0]).length == 3) {
/*  67 */       img = new BufferedImage(width, height, 1);
/*     */       
/*  69 */       for (int row = 0; row < height; row++) {
/*  70 */         for (int column = 0; column < width; column++) {
/*  71 */           img.setRGB(column, row, raw[row][column][0] << 16 | raw[row][column][1] << 8 | raw[row][column][2]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     return img;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[][] GSArray(BufferedImage img) {
/*  86 */     int[][] gs = null;
/*  87 */     int height = img.getHeight();
/*  88 */     int width = img.getWidth();
/*     */     
/*  90 */     if (height > 0 && width > 0) {
/*  91 */       gs = new int[height][width];
/*     */       
/*  93 */       for (int i = 0; i < height; i++) {
/*  94 */         for (int j = 0; j < width; j++) {
/*  95 */           int bits = img.getRGB(j, i);
/*     */           
/*  97 */           long avg = Math.round(((bits >> 16 & 0xFF) + (bits >> 8 & 0xFF) + (bits & 0xFF)) / 3.0D);
/*  98 */           gs[i][j] = (int)avg;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     return gs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage GSImg(int[][] raw) {
/* 113 */     BufferedImage img = null;
/* 114 */     int height = raw.length;
/* 115 */     int width = (raw[0]).length;
/*     */     
/* 117 */     if (height > 0 && width > 0) {
/* 118 */       img = new BufferedImage(width, height, 1);
/*     */       
/* 120 */       for (int i = 0; i < height; i++) {
/* 121 */         for (int j = 0; j < width; j++) {
/* 122 */           img.setRGB(j, i, raw[i][j] << 16 | raw[i][j] << 8 | raw[i][j]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 127 */     return img;
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
/*     */   public static double[][][] HSVArray(BufferedImage img) {
/* 139 */     if (img == null) {
/* 140 */       throw new IllegalArgumentException("ERROR: Source image is null!");
/*     */     }
/*     */     
/* 143 */     int height = img.getHeight();
/* 144 */     int width = img.getWidth();
/* 145 */     double[][][] hsv = new double[height][width][];
/*     */     
/* 147 */     for (int r = 0; r < height; r++) {
/* 148 */       for (int c = 0; c < width; c++) {
/* 149 */         double cmax, cmin; int[] rgb = intRGB(img.getRGB(c, r));
/*     */         
/* 151 */         double[] rgbprime = new double[3];
/* 152 */         double[] pixelHSV = new double[3];
/*     */         
/* 154 */         for (int i = 0; i < 3; i++) {
/* 155 */           rgbprime[i] = rgb[i] / 255.0D;
/*     */         }
/*     */ 
/*     */         
/* 159 */         if (rgbprime[0] > rgbprime[1] && rgbprime[0] > rgbprime[2]) {
/* 160 */           cmax = rgbprime[0];
/* 161 */           cmin = (rgbprime[1] < rgbprime[2]) ? rgbprime[1] : rgbprime[2];
/* 162 */           pixelHSV[0] = 60.0D * (rgbprime[1] - rgbprime[2]) / (cmax - cmin) % 6.0D;
/*     */         }
/* 164 */         else if (rgbprime[1] > rgbprime[0] && rgbprime[1] > rgbprime[2]) {
/* 165 */           cmax = rgbprime[1];
/* 166 */           cmin = (rgbprime[0] < rgbprime[2]) ? rgbprime[0] : rgbprime[2];
/* 167 */           pixelHSV[0] = 60.0D * ((rgbprime[2] - rgbprime[0]) / (cmax - cmin) + 2.0D);
/*     */         }
/* 169 */         else if (rgbprime[2] > rgbprime[1] && rgbprime[2] > rgbprime[0]) {
/* 170 */           cmax = rgbprime[2];
/* 171 */           cmin = (rgbprime[0] < rgbprime[1]) ? rgbprime[0] : rgbprime[1];
/* 172 */           pixelHSV[0] = 60.0D * ((rgbprime[0] - rgbprime[1]) / (cmax - cmin) + 4.0D);
/*     */         } else {
/*     */           
/* 175 */           cmax = 0.0D;
/* 176 */           cmin = 0.0D;
/* 177 */           pixelHSV[0] = 0.0D;
/*     */         } 
/*     */         
/* 180 */         pixelHSV[1] = (cmax == 0.0D) ? 0.0D : ((cmax - cmin) / cmax);
/* 181 */         pixelHSV[2] = cmax;
/* 182 */         hsv[r][c] = pixelHSV;
/*     */       } 
/*     */     } 
/*     */     
/* 186 */     return hsv;
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
/*     */   public static double[][][] HSIArray(BufferedImage img) {
/* 198 */     if (img == null) {
/* 199 */       throw new IllegalArgumentException("ERROR: Source image is null!");
/*     */     }
/*     */     
/* 202 */     int height = img.getHeight();
/* 203 */     int width = img.getWidth();
/* 204 */     double[][][] hsi = new double[height][width][];
/* 205 */     double piRad = 57.29577951308232D;
/*     */     
/* 207 */     for (int r = 0; r < height; r++) {
/* 208 */       for (int c = 0; c < width; c++) {
/* 209 */         int[] rgb = intRGB(img.getRGB(c, r));
/* 210 */         double[] pixelHSI = new double[3];
/* 211 */         double cos1 = rgb[0] - 0.5D * rgb[1] - 0.5D * rgb[2];
/* 212 */         double cos2 = Math.sqrt((rgb[0] * rgb[0] + rgb[1] * rgb[1] + rgb[2] * rgb[2] - 
/* 213 */             rgb[0] * rgb[1] - rgb[0] * rgb[2] - rgb[1] * rgb[2]));
/*     */         
/* 215 */         pixelHSI[0] = Math.acos(cos1 / cos2) * piRad;
/*     */         
/* 217 */         if (rgb[1] < rgb[2]) {
/* 218 */           pixelHSI[0] = 360.0D - pixelHSI[0];
/*     */         }
/*     */         
/* 221 */         pixelHSI[2] = (rgb[0] + rgb[1] + rgb[2]) / 3.0D;
/* 222 */         pixelHSI[1] = 1.0D - Min(rgb[0], rgb[1], rgb[2]) / pixelHSI[2];
/* 223 */         hsi[r][c] = pixelHSI;
/*     */       } 
/*     */     } 
/*     */     
/* 227 */     return hsi;
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
/*     */   public static double[][][] TSLArray(BufferedImage img) {
/* 239 */     if (img == null) {
/* 240 */       throw new IllegalArgumentException("ERROR: Source image is null!");
/*     */     }
/*     */     
/* 243 */     int height = img.getHeight();
/* 244 */     int width = img.getWidth();
/* 245 */     double[][][] tsl = new double[height][width][];
/*     */     
/* 247 */     for (int r = 0; r < height; r++) {
/* 248 */       for (int c = 0; c < width; c++) {
/* 249 */         int[] rgb = intRGB(img.getRGB(c, r));
/* 250 */         double sum = (rgb[0] + rgb[1] + rgb[2]);
/* 251 */         double gPrime = rgb[1] / sum;
/* 252 */         double[] pixelTSL = new double[3];
/*     */         
/* 254 */         if (gPrime > 0.1D) {
/* 255 */           pixelTSL[0] = Math.atan2(rgb[0] / sum, gPrime) / 6.283185307179586D + 0.25D;
/* 256 */         } else if (gPrime < -0.1D) {
/* 257 */           pixelTSL[0] = Math.atan2(rgb[0] / sum, gPrime) / 6.283185307179586D + 0.75D;
/*     */         } else {
/* 259 */           pixelTSL[0] = 0.0D;
/*     */         } 
/*     */         
/* 262 */         pixelTSL[1] = Math.sqrt(1.8D * (rgb[0] / sum - 0.3333D) * (rgb[0] / sum - 0.3333D) + (
/* 263 */             rgb[1] / sum - 0.3333D) * (rgb[1] / sum - 0.3333D));
/* 264 */         pixelTSL[2] = 0.299D * rgb[0] + 0.587D * rgb[1] + 0.114D * rgb[2];
/* 265 */         tsl[r][c] = pixelTSL;
/*     */       } 
/*     */     } 
/*     */     
/* 269 */     return tsl;
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
/*     */   public static BufferedImage HSVImg(double[][][] raw) {
/* 281 */     int height = raw.length;
/* 282 */     int width = (raw[0]).length;
/*     */     
/* 284 */     if (height < 1 || width < 1 || (raw[0][0]).length != 3) {
/* 285 */       throw new IllegalArgumentException("ERROR: Malformed RGB array!");
/*     */     }
/*     */     
/* 288 */     BufferedImage img = new BufferedImage(width, height, 1);
/*     */     
/* 290 */     for (int r = 0; r < height; r++) {
/* 291 */       for (int c = 0; c < width; c++) {
/* 292 */         int[] rgb = new int[3];
/*     */         
/* 294 */         rgb[0] = (int)raw[r][c][0] * 255;
/* 295 */         rgb[1] = (int)raw[r][c][1] * 255;
/* 296 */         rgb[2] = (int)(raw[r][c][2] / 360.0D * raw[r][c][2]);
/*     */         
/* 298 */         img.setRGB(c, r, rgb[0] << 16 | rgb[1] << 8 | rgb[2]);
/*     */       } 
/*     */     } 
/*     */     
/* 302 */     return img;
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
/*     */   public static BufferedImage HSIImg(double[][][] raw) {
/* 314 */     int height = raw.length;
/* 315 */     int width = (raw[0]).length;
/*     */     
/* 317 */     if (height < 1 || width < 1 || (raw[0][0]).length != 3) {
/* 318 */       throw new IllegalArgumentException("ERROR: Malformed RGB array!");
/*     */     }
/*     */     
/* 321 */     BufferedImage img = new BufferedImage(width, height, 1);
/*     */     
/* 323 */     for (int r = 0; r < height; r++) {
/* 324 */       for (int c = 0; c < width; c++) {
/* 325 */         int[] rgb = new int[3];
/*     */         
/* 327 */         rgb[0] = (int)raw[r][c][0] * 255;
/* 328 */         rgb[1] = (int)raw[r][c][1] * 255;
/* 329 */         rgb[2] = (int)(raw[r][c][2] / 360.0D * raw[r][c][2]);
/*     */         
/* 331 */         img.setRGB(c, r, rgb[0] << 16 | rgb[1] << 8 | rgb[2]);
/*     */       } 
/*     */     } 
/*     */     
/* 335 */     return img;
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
/*     */   public static double[][][] YCbCrArray(BufferedImage img) {
/* 347 */     if (img == null) {
/* 348 */       throw new IllegalArgumentException("ERROR: Source image is null!");
/*     */     }
/*     */     
/* 351 */     int height = img.getHeight();
/* 352 */     int width = img.getWidth();
/* 353 */     double[][][] out = new double[height][width][3];
/*     */     
/* 355 */     for (int i = 0; i < height; i++) {
/* 356 */       for (int j = 0; j < width; j++) {
/* 357 */         int[] rgb = intRGB(img.getRGB(j, i));
/*     */         
/* 359 */         out[i][j][0] = 16.0D + 0.2568D * rgb[0] + 0.5022D * rgb[1] + 0.0975D * rgb[2];
/*     */         
/* 361 */         out[i][j][1] = 128.0D + -0.1476D * rgb[0] + -0.2899D * rgb[1] + 0.4375D * rgb[2];
/*     */         
/* 363 */         out[i][j][2] = 128.0D + 0.4375D * rgb[0] + -0.3663D * rgb[1] + -0.0711D * rgb[2];
/*     */       } 
/*     */     } 
/*     */     
/* 367 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] intRGB(int bits) {
/* 377 */     int[] rgb = { bits >> 16 & 0xFF, bits >> 8 & 0xFF, bits & 0xFF };
/*     */ 
/*     */     
/* 380 */     for (int i = 0; i < 3; i++) {
/* 381 */       if (rgb[i] < 0) {
/* 382 */         rgb[i] = 0;
/* 383 */       } else if (rgb[i] > 255) {
/* 384 */         rgb[i] = 255;
/*     */       } 
/*     */     } 
/*     */     
/* 388 */     return rgb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double Min(int a, int b, int c) {
/* 395 */     if (a <= b && a <= c)
/* 396 */       return a; 
/* 397 */     if (b <= c && b <= a) {
/* 398 */       return b;
/*     */     }
/* 400 */     return c;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/imageproc/ImageUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */