/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.jfree.data.time.RegularTimePeriod;
/*     */ import org.jfree.data.time.TimeSeriesCollection;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.data.xy.XisSymbolic;
/*     */ import org.jfree.data.xy.YisSymbolic;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SymbolicXYItemLabelGenerator
/*     */   implements XYItemLabelGenerator, XYToolTipGenerator, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3963400354475494395L;
/*     */   
/*     */   public String generateToolTip(XYDataset data, int series, int item) {
/*     */     String xStr;
/*     */     String yStr;
/*  84 */     if (data instanceof YisSymbolic) {
/*  85 */       yStr = ((YisSymbolic)data).getYSymbolicValue(series, item);
/*     */     } else {
/*     */       
/*  88 */       double y = data.getYValue(series, item);
/*  89 */       yStr = Double.toString(round(y, 2));
/*     */     } 
/*  91 */     if (data instanceof XisSymbolic) {
/*  92 */       xStr = ((XisSymbolic)data).getXSymbolicValue(series, item);
/*     */     }
/*  94 */     else if (data instanceof TimeSeriesCollection) {
/*     */ 
/*     */       
/*  97 */       RegularTimePeriod p = ((TimeSeriesCollection)data).getSeries(series).getTimePeriod(item);
/*  98 */       xStr = p.toString();
/*     */     } else {
/*     */       
/* 101 */       double x = data.getXValue(series, item);
/* 102 */       xStr = Double.toString(round(x, 2));
/*     */     } 
/* 104 */     return "X: " + xStr + ", Y: " + yStr;
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
/*     */   public String generateLabel(XYDataset dataset, int series, int category) {
/* 119 */     return null;
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
/*     */   private static double round(double value, int nb) {
/* 131 */     if (nb <= 0) {
/* 132 */       return Math.floor(value + 0.5D);
/*     */     }
/* 134 */     double p = Math.pow(10.0D, nb);
/* 135 */     double tempval = Math.floor(value * p + 0.5D);
/* 136 */     return tempval / p;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 148 */     return super.clone();
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
/* 160 */     if (obj == this) {
/* 161 */       return true;
/*     */     }
/* 163 */     if (obj instanceof SymbolicXYItemLabelGenerator) {
/* 164 */       return true;
/*     */     }
/* 166 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 176 */     int result = 127;
/* 177 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/SymbolicXYItemLabelGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */