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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XYInterval
/*     */   implements Serializable
/*     */ {
/*     */   private double xLow;
/*     */   private double xHigh;
/*     */   private double y;
/*     */   private double yLow;
/*     */   private double yHigh;
/*     */   
/*     */   public XYInterval(double xLow, double xHigh, double y, double yLow, double yHigh) {
/*  79 */     this.xLow = xLow;
/*  80 */     this.xHigh = xHigh;
/*  81 */     this.y = y;
/*  82 */     this.yLow = yLow;
/*  83 */     this.yHigh = yHigh;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXLow() {
/*  92 */     return this.xLow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXHigh() {
/* 101 */     return this.xHigh;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/* 110 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYLow() {
/* 119 */     return this.yLow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYHigh() {
/* 128 */     return this.yHigh;
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
/* 140 */     if (obj == this) {
/* 141 */       return true;
/*     */     }
/* 143 */     if (!(obj instanceof XYInterval)) {
/* 144 */       return false;
/*     */     }
/* 146 */     XYInterval that = (XYInterval)obj;
/* 147 */     if (this.xLow != that.xLow) {
/* 148 */       return false;
/*     */     }
/* 150 */     if (this.xHigh != that.xHigh) {
/* 151 */       return false;
/*     */     }
/* 153 */     if (this.y != that.y) {
/* 154 */       return false;
/*     */     }
/* 156 */     if (this.yLow != that.yLow) {
/* 157 */       return false;
/*     */     }
/* 159 */     if (this.yHigh != that.yHigh) {
/* 160 */       return false;
/*     */     }
/* 162 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/XYInterval.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */