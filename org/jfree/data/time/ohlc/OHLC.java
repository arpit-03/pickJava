/*     */ package org.jfree.data.time.ohlc;
/*     */ 
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
/*     */ public class OHLC
/*     */   implements Serializable
/*     */ {
/*     */   private double open;
/*     */   private double close;
/*     */   private double high;
/*     */   private double low;
/*     */   
/*     */   public OHLC(double open, double high, double low, double close) {
/*  76 */     this.open = open;
/*  77 */     this.close = close;
/*  78 */     this.high = high;
/*  79 */     this.low = low;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOpen() {
/*  88 */     return this.open;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getClose() {
/*  97 */     return this.close;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getHigh() {
/* 106 */     return this.high;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLow() {
/* 115 */     return this.low;
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
/* 127 */     if (obj == this) {
/* 128 */       return true;
/*     */     }
/* 130 */     if (!(obj instanceof OHLC)) {
/* 131 */       return false;
/*     */     }
/* 133 */     OHLC that = (OHLC)obj;
/* 134 */     if (this.open != that.open) {
/* 135 */       return false;
/*     */     }
/* 137 */     if (this.close != that.close) {
/* 138 */       return false;
/*     */     }
/* 140 */     if (this.high != that.high) {
/* 141 */       return false;
/*     */     }
/* 143 */     if (this.low != that.low) {
/* 144 */       return false;
/*     */     }
/* 146 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 156 */     int result = 193;
/* 157 */     result = HashUtilities.hashCode(result, this.open);
/* 158 */     result = HashUtilities.hashCode(result, this.high);
/* 159 */     result = HashUtilities.hashCode(result, this.low);
/* 160 */     result = HashUtilities.hashCode(result, this.close);
/* 161 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/ohlc/OHLC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */