/*     */ package org.jfree.data.time.ohlc;
/*     */ 
/*     */ import org.jfree.data.ComparableObjectItem;
/*     */ import org.jfree.data.time.RegularTimePeriod;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OHLCItem
/*     */   extends ComparableObjectItem
/*     */ {
/*     */   public OHLCItem(RegularTimePeriod period, double open, double high, double low, double close) {
/*  65 */     super((Comparable)period, new OHLC(open, high, low, close));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegularTimePeriod getPeriod() {
/*  74 */     return (RegularTimePeriod)getComparable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYValue() {
/*  83 */     return getCloseValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOpenValue() {
/*  92 */     OHLC ohlc = (OHLC)getObject();
/*  93 */     if (ohlc != null) {
/*  94 */       return ohlc.getOpen();
/*     */     }
/*     */     
/*  97 */     return Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getHighValue() {
/* 107 */     OHLC ohlc = (OHLC)getObject();
/* 108 */     if (ohlc != null) {
/* 109 */       return ohlc.getHigh();
/*     */     }
/*     */     
/* 112 */     return Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLowValue() {
/* 122 */     OHLC ohlc = (OHLC)getObject();
/* 123 */     if (ohlc != null) {
/* 124 */       return ohlc.getLow();
/*     */     }
/*     */     
/* 127 */     return Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getCloseValue() {
/* 137 */     OHLC ohlc = (OHLC)getObject();
/* 138 */     if (ohlc != null) {
/* 139 */       return ohlc.getClose();
/*     */     }
/*     */     
/* 142 */     return Double.NaN;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/ohlc/OHLCItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */