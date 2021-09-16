/*     */ package com.boreholeseismic.dsp;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.Arrays;
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
/*     */ public class CannyEdgeDetector
/*     */ {
/*  74 */   private float lowThreshold = 2.5F;
/*  75 */   private float highThreshold = 7.5F;
/*  76 */   private float gaussianKernelRadius = 2.0F;
/*  77 */   private int gaussianKernelWidth = 16;
/*     */   
/*     */   private boolean contrastNormalized = false;
/*     */   
/*     */   private static final float GAUSSIAN_CUT_OFF = 0.005F;
/*     */   
/*     */   private static final float MAGNITUDE_SCALE = 100.0F;
/*     */   private static final float MAGNITUDE_LIMIT = 1000.0F;
/*     */   private static final int MAGNITUDE_MAX = 100000;
/*     */   private int height;
/*     */   private int width;
/*     */   private int picsize;
/*     */   
/*     */   public BufferedImage getSourceImage() {
/*  91 */     return this.sourceImage;
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] data;
/*     */   
/*     */   private int[] magnitude;
/*     */   
/*     */   private BufferedImage sourceImage;
/*     */   private BufferedImage edgesImage;
/*     */   
/*     */   public void setSourceImage(BufferedImage image) {
/* 103 */     this.sourceImage = image;
/*     */   }
/*     */ 
/*     */   
/*     */   private float[] xConv;
/*     */   
/*     */   private float[] yConv;
/*     */   
/*     */   private float[] xGradient;
/*     */   
/*     */   private float[] yGradient;
/*     */ 
/*     */   
/*     */   public BufferedImage getEdgesImage() {
/* 117 */     return this.edgesImage;
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
/*     */   public void setEdgesImage(BufferedImage edgesImage) {
/* 129 */     this.edgesImage = edgesImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLowThreshold() {
/* 139 */     return this.lowThreshold;
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
/*     */   public void setLowThreshold(float threshold) {
/* 151 */     if (threshold < 0.0F) throw new IllegalArgumentException(); 
/* 152 */     this.lowThreshold = threshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHighThreshold() {
/* 162 */     return this.highThreshold;
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
/*     */   public void setHighThreshold(float threshold) {
/* 175 */     if (threshold < 0.0F) throw new IllegalArgumentException(); 
/* 176 */     this.highThreshold = threshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGaussianKernelWidth() {
/* 187 */     return this.gaussianKernelWidth;
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
/*     */   public void setGaussianKernelWidth(int gaussianKernelWidth) {
/* 200 */     if (gaussianKernelWidth < 2) throw new IllegalArgumentException(); 
/* 201 */     this.gaussianKernelWidth = gaussianKernelWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getGaussianKernelRadius() {
/* 212 */     return this.gaussianKernelRadius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGaussianKernelRadius(float gaussianKernelRadius) {
/* 223 */     if (gaussianKernelRadius < 0.1F) throw new IllegalArgumentException(); 
/* 224 */     this.gaussianKernelRadius = gaussianKernelRadius;
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
/*     */   public boolean isContrastNormalized() {
/* 236 */     return this.contrastNormalized;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContrastNormalized(boolean contrastNormalized) {
/* 246 */     this.contrastNormalized = contrastNormalized;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process() {
/* 252 */     this.width = this.sourceImage.getWidth();
/* 253 */     this.height = this.sourceImage.getHeight();
/* 254 */     this.picsize = this.width * this.height;
/* 255 */     initArrays();
/* 256 */     readLuminance();
/* 257 */     if (this.contrastNormalized) normalizeContrast(); 
/* 258 */     computeGradients(this.gaussianKernelRadius, this.gaussianKernelWidth);
/* 259 */     int low = Math.round(this.lowThreshold * 100.0F);
/* 260 */     int high = Math.round(this.highThreshold * 100.0F);
/* 261 */     performHysteresis(low, high);
/* 262 */     thresholdEdges();
/* 263 */     writeEdges(this.data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initArrays() {
/* 269 */     if (this.data == null || this.picsize != this.data.length) {
/* 270 */       this.data = new int[this.picsize];
/* 271 */       this.magnitude = new int[this.picsize];
/*     */       
/* 273 */       this.xConv = new float[this.picsize];
/* 274 */       this.yConv = new float[this.picsize];
/* 275 */       this.xGradient = new float[this.picsize];
/* 276 */       this.yGradient = new float[this.picsize];
/*     */     } 
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
/*     */   private void computeGradients(float kernelRadius, int kernelWidth) {
/* 294 */     float[] kernel = new float[kernelWidth];
/* 295 */     float[] diffKernel = new float[kernelWidth];
/*     */     int kwidth;
/* 297 */     for (kwidth = 0; kwidth < kernelWidth; kwidth++) {
/* 298 */       float g1 = gaussian(kwidth, kernelRadius);
/* 299 */       if (g1 <= 0.005F && kwidth >= 2)
/* 300 */         break;  float g2 = gaussian(kwidth - 0.5F, kernelRadius);
/* 301 */       float g3 = gaussian(kwidth + 0.5F, kernelRadius);
/* 302 */       kernel[kwidth] = (g1 + g2 + g3) / 3.0F / 6.2831855F * kernelRadius * kernelRadius;
/* 303 */       diffKernel[kwidth] = g3 - g2;
/*     */     } 
/*     */     
/* 306 */     int initX = kwidth - 1;
/* 307 */     int maxX = this.width - kwidth - 1;
/* 308 */     int initY = this.width * (kwidth - 1);
/* 309 */     int maxY = this.width * (this.height - kwidth - 1);
/*     */     
/*     */     int x;
/* 312 */     for (x = initX; x < maxX; x++) {
/* 313 */       for (int y = initY; y < maxY; y += this.width) {
/* 314 */         int index = x + y;
/* 315 */         float sumX = this.data[index] * kernel[0];
/* 316 */         float sumY = sumX;
/* 317 */         int xOffset = 1;
/* 318 */         int yOffset = this.width;
/* 319 */         while (xOffset < kwidth) {
/* 320 */           sumY += kernel[xOffset] * (this.data[index - yOffset] + this.data[index + yOffset]);
/* 321 */           sumX += kernel[xOffset] * (this.data[index - xOffset] + this.data[index + xOffset]);
/* 322 */           yOffset += this.width;
/* 323 */           xOffset++;
/*     */         } 
/*     */         
/* 326 */         this.yConv[index] = sumY;
/* 327 */         this.xConv[index] = sumX;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 332 */     for (x = initX; x < maxX; x++) {
/* 333 */       for (int y = initY; y < maxY; y += this.width) {
/* 334 */         float sum = 0.0F;
/* 335 */         int index = x + y;
/* 336 */         for (int i = 1; i < kwidth; i++) {
/* 337 */           sum += diffKernel[i] * (this.yConv[index - i] - this.yConv[index + i]);
/*     */         }
/* 339 */         this.xGradient[index] = sum;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 344 */     for (x = kwidth; x < this.width - kwidth; x++) {
/* 345 */       for (int y = initY; y < maxY; y += this.width) {
/* 346 */         float sum = 0.0F;
/* 347 */         int index = x + y;
/* 348 */         int yOffset = this.width;
/* 349 */         for (int i = 1; i < kwidth; i++) {
/* 350 */           sum += diffKernel[i] * (this.xConv[index - yOffset] - this.xConv[index + yOffset]);
/* 351 */           yOffset += this.width;
/*     */         } 
/*     */         
/* 354 */         this.yGradient[index] = sum;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 359 */     initX = kwidth;
/* 360 */     maxX = this.width - kwidth;
/* 361 */     initY = this.width * kwidth;
/* 362 */     maxY = this.width * (this.height - kwidth);
/* 363 */     for (x = initX; x < maxX; x++) {
/* 364 */       for (int y = initY; y < maxY; y += this.width) {
/* 365 */         int index = x + y;
/* 366 */         int indexN = index - this.width;
/* 367 */         int indexS = index + this.width;
/* 368 */         int indexW = index - 1;
/* 369 */         int indexE = index + 1;
/* 370 */         int indexNW = indexN - 1;
/* 371 */         int indexNE = indexN + 1;
/* 372 */         int indexSW = indexS - 1;
/* 373 */         int indexSE = indexS + 1;
/*     */         
/* 375 */         float xGrad = this.xGradient[index];
/* 376 */         float yGrad = this.yGradient[index];
/* 377 */         float gradMag = hypot(xGrad, yGrad);
/*     */ 
/*     */         
/* 380 */         float nMag = hypot(this.xGradient[indexN], this.yGradient[indexN]);
/* 381 */         float sMag = hypot(this.xGradient[indexS], this.yGradient[indexS]);
/* 382 */         float wMag = hypot(this.xGradient[indexW], this.yGradient[indexW]);
/* 383 */         float eMag = hypot(this.xGradient[indexE], this.yGradient[indexE]);
/* 384 */         float neMag = hypot(this.xGradient[indexNE], this.yGradient[indexNE]);
/* 385 */         float seMag = hypot(this.xGradient[indexSE], this.yGradient[indexSE]);
/* 386 */         float swMag = hypot(this.xGradient[indexSW], this.yGradient[indexSW]);
/* 387 */         float nwMag = hypot(this.xGradient[indexNW], this.yGradient[indexNW]);
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
/*     */         float tmp;
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
/* 417 */         if ((xGrad * yGrad <= 0.0F) ? (
/* 418 */           (Math.abs(xGrad) >= Math.abs(yGrad)) ? ((
/* 419 */           tmp = Math.abs(xGrad * gradMag)) >= Math.abs(yGrad * neMag - (xGrad + yGrad) * eMag) && 
/* 420 */           tmp > Math.abs(yGrad * swMag - (xGrad + yGrad) * wMag)) : ((
/* 421 */           tmp = Math.abs(yGrad * gradMag)) >= Math.abs(xGrad * neMag - (yGrad + xGrad) * nMag) && 
/* 422 */           tmp > Math.abs(xGrad * swMag - (yGrad + xGrad) * sMag))) : (
/* 423 */           (Math.abs(xGrad) >= Math.abs(yGrad)) ? ((
/* 424 */           tmp = Math.abs(xGrad * gradMag)) >= Math.abs(yGrad * seMag + (xGrad - yGrad) * eMag) && 
/* 425 */           tmp > Math.abs(yGrad * nwMag + (xGrad - yGrad) * wMag)) : ((
/* 426 */           tmp = Math.abs(yGrad * gradMag)) >= Math.abs(xGrad * seMag + (yGrad - xGrad) * sMag) && 
/* 427 */           tmp > Math.abs(xGrad * nwMag + (yGrad - xGrad) * nMag)))) {
/*     */           
/* 429 */           this.magnitude[index] = (gradMag >= 1000.0F) ? 100000 : (int)(100.0F * gradMag);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 434 */           this.magnitude[index] = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float hypot(float x, float y) {
/* 444 */     return (float)Math.hypot(x, y);
/*     */   }
/*     */   
/*     */   private float gaussian(float x, float sigma) {
/* 448 */     return (float)Math.exp((-(x * x) / 2.0F * sigma * sigma));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void performHysteresis(int low, int high) {
/* 456 */     Arrays.fill(this.data, 0);
/*     */     
/* 458 */     int offset = 0;
/* 459 */     for (int y = 0; y < this.height; y++) {
/* 460 */       for (int x = 0; x < this.width; x++) {
/* 461 */         if (this.data[offset] == 0 && this.magnitude[offset] >= high) {
/* 462 */           follow(x, y, offset, low);
/*     */         }
/* 464 */         offset++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void follow(int x1, int y1, int i1, int threshold) {
/* 470 */     int x0 = (x1 == 0) ? x1 : (x1 - 1);
/* 471 */     int x2 = (x1 == this.width - 1) ? x1 : (x1 + 1);
/* 472 */     int y0 = (y1 == 0) ? y1 : (y1 - 1);
/* 473 */     int y2 = (y1 == this.height - 1) ? y1 : (y1 + 1);
/*     */     
/* 475 */     this.data[i1] = this.magnitude[i1];
/* 476 */     for (int x = x0; x <= x2; x++) {
/* 477 */       for (int y = y0; y <= y2; y++) {
/* 478 */         int i2 = x + y * this.width;
/* 479 */         if ((y != y1 || x != x1) && 
/* 480 */           this.data[i2] == 0 && 
/* 481 */           this.magnitude[i2] >= threshold) {
/* 482 */           follow(x, y, i2, threshold);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void thresholdEdges() {
/* 490 */     for (int i = 0; i < this.picsize; i++) {
/* 491 */       this.data[i] = (this.data[i] > 0) ? -1 : -16777216;
/*     */     }
/*     */   }
/*     */   
/*     */   private int luminance(float r, float g, float b) {
/* 496 */     return Math.round(0.299F * r + 0.587F * g + 0.114F * b);
/*     */   }
/*     */   
/*     */   private void readLuminance() {
/* 500 */     int type = this.sourceImage.getType();
/* 501 */     if (type == 1 || type == 2) {
/* 502 */       int[] pixels = (int[])this.sourceImage.getData().getDataElements(0, 0, this.width, this.height, null);
/* 503 */       for (int i = 0; i < this.picsize; i++) {
/* 504 */         int p = pixels[i];
/* 505 */         int r = (p & 0xFF0000) >> 16;
/* 506 */         int g = (p & 0xFF00) >> 8;
/* 507 */         int b = p & 0xFF;
/* 508 */         this.data[i] = luminance(r, g, b);
/*     */       } 
/* 510 */     } else if (type == 10) {
/* 511 */       byte[] pixels = (byte[])this.sourceImage.getData().getDataElements(0, 0, this.width, this.height, null);
/* 512 */       for (int i = 0; i < this.picsize; i++) {
/* 513 */         this.data[i] = pixels[i] & 0xFF;
/*     */       }
/* 515 */     } else if (type == 11) {
/* 516 */       short[] pixels = (short[])this.sourceImage.getData().getDataElements(0, 0, this.width, this.height, null);
/* 517 */       for (int i = 0; i < this.picsize; i++) {
/* 518 */         this.data[i] = (pixels[i] & 0xFFFF) / 256;
/*     */       }
/* 520 */     } else if (type == 5) {
/* 521 */       byte[] pixels = (byte[])this.sourceImage.getData().getDataElements(0, 0, this.width, this.height, null);
/* 522 */       int offset = 0;
/* 523 */       for (int i = 0; i < this.picsize; i++) {
/* 524 */         int b = pixels[offset++] & 0xFF;
/* 525 */         int g = pixels[offset++] & 0xFF;
/* 526 */         int r = pixels[offset++] & 0xFF;
/* 527 */         this.data[i] = luminance(r, g, b);
/*     */       } 
/*     */     } else {
/* 530 */       throw new IllegalArgumentException("Unsupported image type: " + type);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void normalizeContrast() {
/* 535 */     int[] histogram = new int[256];
/* 536 */     for (int i = 0; i < this.data.length; i++) {
/* 537 */       histogram[this.data[i]] = histogram[this.data[i]] + 1;
/*     */     }
/* 539 */     int[] remap = new int[256];
/* 540 */     int sum = 0;
/* 541 */     int j = 0; int k;
/* 542 */     for (k = 0; k < histogram.length; k++) {
/* 543 */       sum += histogram[k];
/* 544 */       int target = sum * 255 / this.picsize;
/* 545 */       for (int m = j + 1; m <= target; m++) {
/* 546 */         remap[m] = k;
/*     */       }
/* 548 */       j = target;
/*     */     } 
/*     */     
/* 551 */     for (k = 0; k < this.data.length; k++) {
/* 552 */       this.data[k] = remap[this.data[k]];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeEdges(int[] pixels) {
/* 560 */     if (this.edgesImage == null) {
/* 561 */       this.edgesImage = new BufferedImage(this.width, this.height, 2);
/*     */     }
/* 563 */     this.edgesImage.getWritableTile(0, 0).setDataElements(0, 0, this.width, this.height, pixels);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/CannyEdgeDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */