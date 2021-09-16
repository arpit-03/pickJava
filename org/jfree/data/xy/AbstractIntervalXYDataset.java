/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractIntervalXYDataset
/*     */   extends AbstractXYDataset
/*     */   implements IntervalXYDataset
/*     */ {
/*     */   public double getStartXValue(int series, int item) {
/*  65 */     double result = Double.NaN;
/*  66 */     Number x = getStartX(series, item);
/*  67 */     if (x != null) {
/*  68 */       result = x.doubleValue();
/*     */     }
/*  70 */     return result;
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
/*     */   public double getEndXValue(int series, int item) {
/*  84 */     double result = Double.NaN;
/*  85 */     Number x = getEndX(series, item);
/*  86 */     if (x != null) {
/*  87 */       result = x.doubleValue();
/*     */     }
/*  89 */     return result;
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
/*     */   public double getStartYValue(int series, int item) {
/* 103 */     double result = Double.NaN;
/* 104 */     Number y = getStartY(series, item);
/* 105 */     if (y != null) {
/* 106 */       result = y.doubleValue();
/*     */     }
/* 108 */     return result;
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
/*     */   public double getEndYValue(int series, int item) {
/* 122 */     double result = Double.NaN;
/* 123 */     Number y = getEndY(series, item);
/* 124 */     if (y != null) {
/* 125 */       result = y.doubleValue();
/*     */     }
/* 127 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/AbstractIntervalXYDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */