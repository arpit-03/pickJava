/*     */ package org.jfree.chart.util;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.BufferedImageOp;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultShadowGenerator
/*     */   implements ShadowGenerator, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2732993885591386064L;
/*     */   private int shadowSize;
/*     */   private Color shadowColor;
/*     */   private float shadowOpacity;
/*     */   private double angle;
/*     */   private int distance;
/*     */   
/*     */   public DefaultShadowGenerator() {
/*  83 */     this(5, Color.black, 0.5F, 5, -0.7853981633974483D);
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
/*     */   public DefaultShadowGenerator(int size, Color color, float opacity, int distance, double angle) {
/*  97 */     ParamChecks.nullNotPermitted(color, "color");
/*  98 */     this.shadowSize = size;
/*  99 */     this.shadowColor = color;
/* 100 */     this.shadowOpacity = opacity;
/* 101 */     this.distance = distance;
/* 102 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getShadowSize() {
/* 111 */     return this.shadowSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getShadowColor() {
/* 120 */     return this.shadowColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getShadowOpacity() {
/* 129 */     return this.shadowOpacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDistance() {
/* 138 */     return this.distance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAngle() {
/* 147 */     return this.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int calculateOffsetX() {
/* 158 */     return (int)(Math.cos(this.angle) * this.distance) - this.shadowSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int calculateOffsetY() {
/* 169 */     return -((int)(Math.sin(this.angle) * this.distance)) - this.shadowSize;
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
/*     */   public BufferedImage createDropShadow(BufferedImage source) {
/* 184 */     BufferedImage subject = new BufferedImage(source.getWidth() + this.shadowSize * 2, source.getHeight() + this.shadowSize * 2, 2);
/*     */ 
/*     */     
/* 187 */     Graphics2D g2 = subject.createGraphics();
/* 188 */     g2.drawImage(source, (BufferedImageOp)null, this.shadowSize, this.shadowSize);
/* 189 */     g2.dispose();
/* 190 */     applyShadow(subject);
/* 191 */     return subject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void applyShadow(BufferedImage image) {
/* 200 */     int dstWidth = image.getWidth();
/* 201 */     int dstHeight = image.getHeight();
/*     */     
/* 203 */     int left = this.shadowSize - 1 >> 1;
/* 204 */     int right = this.shadowSize - left;
/* 205 */     int xStart = left;
/* 206 */     int xStop = dstWidth - right;
/* 207 */     int yStart = left;
/* 208 */     int yStop = dstHeight - right;
/*     */     
/* 210 */     int shadowRgb = this.shadowColor.getRGB() & 0xFFFFFF;
/*     */     
/* 212 */     int[] aHistory = new int[this.shadowSize];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 217 */     int[] dataBuffer = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
/* 218 */     int lastPixelOffset = right * dstWidth;
/* 219 */     float sumDivider = this.shadowOpacity / this.shadowSize;
/*     */     
/*     */     int bufferOffset;
/*     */     
/* 223 */     for (int y = 0; y < dstHeight; bufferOffset = ++y * dstWidth) {
/* 224 */       int aSum = 0;
/* 225 */       int historyIdx = 0; int i;
/* 226 */       for (i = 0; i < this.shadowSize; i++, bufferOffset++) {
/* 227 */         int a = dataBuffer[bufferOffset] >>> 24;
/* 228 */         aHistory[i] = a;
/* 229 */         aSum += a;
/*     */       } 
/*     */       
/* 232 */       bufferOffset -= right;
/*     */       
/* 234 */       for (i = xStart; i < xStop; i++, bufferOffset++) {
/* 235 */         int a = (int)(aSum * sumDivider);
/* 236 */         dataBuffer[bufferOffset] = a << 24 | shadowRgb;
/*     */ 
/*     */         
/* 239 */         aSum -= aHistory[historyIdx];
/*     */ 
/*     */         
/* 242 */         a = dataBuffer[bufferOffset + right] >>> 24;
/* 243 */         aHistory[historyIdx] = a;
/* 244 */         aSum += a;
/*     */         
/* 246 */         if (++historyIdx >= this.shadowSize) {
/* 247 */           historyIdx -= this.shadowSize;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 253 */     for (int x = 0; x < dstWidth; bufferOffset = ++x) {
/* 254 */       int aSum = 0;
/* 255 */       int historyIdx = 0; int i;
/* 256 */       for (i = 0; i < this.shadowSize; i++, 
/* 257 */         bufferOffset += dstWidth) {
/* 258 */         int a = dataBuffer[bufferOffset] >>> 24;
/* 259 */         aHistory[i] = a;
/* 260 */         aSum += a;
/*     */       } 
/*     */       
/* 263 */       bufferOffset -= lastPixelOffset;
/*     */       
/* 265 */       for (i = yStart; i < yStop; i++, bufferOffset += dstWidth) {
/* 266 */         int a = (int)(aSum * sumDivider);
/* 267 */         dataBuffer[bufferOffset] = a << 24 | shadowRgb;
/*     */ 
/*     */         
/* 270 */         aSum -= aHistory[historyIdx];
/*     */ 
/*     */         
/* 273 */         a = dataBuffer[bufferOffset + lastPixelOffset] >>> 24;
/* 274 */         aHistory[historyIdx] = a;
/* 275 */         aSum += a;
/*     */         
/* 277 */         if (++historyIdx >= this.shadowSize) {
/* 278 */           historyIdx -= this.shadowSize;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 293 */     if (obj == this) {
/* 294 */       return true;
/*     */     }
/* 296 */     if (!(obj instanceof DefaultShadowGenerator)) {
/* 297 */       return false;
/*     */     }
/* 299 */     DefaultShadowGenerator that = (DefaultShadowGenerator)obj;
/* 300 */     if (this.shadowSize != that.shadowSize) {
/* 301 */       return false;
/*     */     }
/* 303 */     if (!this.shadowColor.equals(that.shadowColor)) {
/* 304 */       return false;
/*     */     }
/* 306 */     if (this.shadowOpacity != that.shadowOpacity) {
/* 307 */       return false;
/*     */     }
/* 309 */     if (this.distance != that.distance) {
/* 310 */       return false;
/*     */     }
/* 312 */     if (this.angle != that.angle) {
/* 313 */       return false;
/*     */     }
/* 315 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 325 */     int hash = HashUtilities.hashCode(17, this.shadowSize);
/* 326 */     hash = HashUtilities.hashCode(hash, this.shadowColor);
/* 327 */     hash = HashUtilities.hashCode(hash, this.shadowOpacity);
/* 328 */     hash = HashUtilities.hashCode(hash, this.distance);
/* 329 */     hash = HashUtilities.hashCode(hash, this.angle);
/* 330 */     return hash;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/util/DefaultShadowGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */