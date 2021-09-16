/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class YInterval
/*     */   implements Serializable
/*     */ {
/*     */   private double y;
/*     */   private double yLow;
/*     */   private double yHigh;
/*     */   
/*     */   public YInterval(double y, double yLow, double yHigh) {
/*  70 */     this.y = y;
/*  71 */     this.yLow = yLow;
/*  72 */     this.yHigh = yHigh;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/*  81 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYLow() {
/*  90 */     return this.yLow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYHigh() {
/*  99 */     return this.yHigh;
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
/* 111 */     if (obj == this) {
/* 112 */       return true;
/*     */     }
/* 114 */     if (!(obj instanceof YInterval)) {
/* 115 */       return false;
/*     */     }
/* 117 */     YInterval that = (YInterval)obj;
/* 118 */     if (this.y != that.y) {
/* 119 */       return false;
/*     */     }
/* 121 */     if (this.yLow != that.yLow) {
/* 122 */       return false;
/*     */     }
/* 124 */     if (this.yHigh != that.yHigh) {
/* 125 */       return false;
/*     */     }
/* 127 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/YInterval.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */