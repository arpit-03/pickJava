/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ import org.jfree.data.ComparableObjectItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XIntervalDataItem
/*     */   extends ComparableObjectItem
/*     */ {
/*     */   public XIntervalDataItem(double x, double xLow, double xHigh, double y) {
/*  61 */     super(new Double(x), new YWithXInterval(y, xLow, xHigh));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getX() {
/*  70 */     return (Number)getComparable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYValue() {
/*  79 */     YWithXInterval interval = (YWithXInterval)getObject();
/*  80 */     if (interval != null) {
/*  81 */       return interval.getY();
/*     */     }
/*     */     
/*  84 */     return Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXLowValue() {
/*  94 */     YWithXInterval interval = (YWithXInterval)getObject();
/*  95 */     if (interval != null) {
/*  96 */       return interval.getXLow();
/*     */     }
/*     */     
/*  99 */     return Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXHighValue() {
/* 109 */     YWithXInterval interval = (YWithXInterval)getObject();
/* 110 */     if (interval != null) {
/* 111 */       return interval.getXHigh();
/*     */     }
/*     */     
/* 114 */     return Double.NaN;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/XIntervalDataItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */