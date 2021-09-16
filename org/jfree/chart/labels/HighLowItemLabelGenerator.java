/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Date;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.data.xy.OHLCDataset;
/*     */ import org.jfree.data.xy.XYDataset;
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
/*     */ 
/*     */ public class HighLowItemLabelGenerator
/*     */   implements XYItemLabelGenerator, XYToolTipGenerator, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5617111754832211830L;
/*     */   private DateFormat dateFormatter;
/*     */   private NumberFormat numberFormatter;
/*     */   
/*     */   public HighLowItemLabelGenerator() {
/*  86 */     this(DateFormat.getInstance(), NumberFormat.getInstance());
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
/*     */   public HighLowItemLabelGenerator(DateFormat dateFormatter, NumberFormat numberFormatter) {
/*  99 */     if (dateFormatter == null) {
/* 100 */       throw new IllegalArgumentException("Null 'dateFormatter' argument.");
/*     */     }
/*     */     
/* 103 */     if (numberFormatter == null) {
/* 104 */       throw new IllegalArgumentException("Null 'numberFormatter' argument.");
/*     */     }
/*     */     
/* 107 */     this.dateFormatter = dateFormatter;
/* 108 */     this.numberFormatter = numberFormatter;
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
/*     */   public String generateToolTip(XYDataset dataset, int series, int item) {
/* 122 */     if (!(dataset instanceof OHLCDataset)) {
/* 123 */       return null;
/*     */     }
/* 125 */     StringBuilder sb = new StringBuilder();
/* 126 */     OHLCDataset d = (OHLCDataset)dataset;
/* 127 */     Number high = d.getHigh(series, item);
/* 128 */     Number low = d.getLow(series, item);
/* 129 */     Number open = d.getOpen(series, item);
/* 130 */     Number close = d.getClose(series, item);
/* 131 */     Number x = d.getX(series, item);
/* 132 */     sb.append(d.getSeriesKey(series).toString());
/* 133 */     if (x != null) {
/* 134 */       Date date = new Date(x.longValue());
/* 135 */       sb.append("--> Date=").append(this.dateFormatter.format(date));
/* 136 */       if (high != null) {
/* 137 */         sb.append(" High=");
/* 138 */         sb.append(this.numberFormatter.format(high.doubleValue()));
/*     */       } 
/* 140 */       if (low != null) {
/* 141 */         sb.append(" Low=");
/* 142 */         sb.append(this.numberFormatter.format(low.doubleValue()));
/*     */       } 
/* 144 */       if (open != null) {
/* 145 */         sb.append(" Open=");
/* 146 */         sb.append(this.numberFormatter.format(open.doubleValue()));
/*     */       } 
/* 148 */       if (close != null) {
/* 149 */         sb.append(" Close=");
/* 150 */         sb.append(this.numberFormatter.format(close.doubleValue()));
/*     */       } 
/*     */     } 
/* 153 */     return sb.toString();
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
/* 168 */     return null;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 181 */     HighLowItemLabelGenerator clone = (HighLowItemLabelGenerator)super.clone();
/* 182 */     if (this.dateFormatter != null) {
/* 183 */       clone.dateFormatter = (DateFormat)this.dateFormatter.clone();
/*     */     }
/* 185 */     if (this.numberFormatter != null) {
/* 186 */       clone.numberFormatter = (NumberFormat)this.numberFormatter.clone();
/*     */     }
/* 188 */     return clone;
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
/* 200 */     if (obj == this) {
/* 201 */       return true;
/*     */     }
/* 203 */     if (!(obj instanceof HighLowItemLabelGenerator)) {
/* 204 */       return false;
/*     */     }
/* 206 */     HighLowItemLabelGenerator generator = (HighLowItemLabelGenerator)obj;
/* 207 */     if (!this.dateFormatter.equals(generator.dateFormatter)) {
/* 208 */       return false;
/*     */     }
/* 210 */     if (!this.numberFormatter.equals(generator.numberFormatter)) {
/* 211 */       return false;
/*     */     }
/* 213 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 223 */     int result = 127;
/* 224 */     result = HashUtilities.hashCode(result, this.dateFormatter);
/* 225 */     result = HashUtilities.hashCode(result, this.numberFormatter);
/* 226 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/HighLowItemLabelGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */