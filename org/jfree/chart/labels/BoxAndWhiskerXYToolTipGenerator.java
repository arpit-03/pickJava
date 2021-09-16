/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Date;
/*     */ import org.jfree.data.statistics.BoxAndWhiskerXYDataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BoxAndWhiskerXYToolTipGenerator
/*     */   extends StandardXYToolTipGenerator
/*     */   implements XYToolTipGenerator, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2648775791161459710L;
/*     */   public static final String DEFAULT_TOOL_TIP_FORMAT = "X: {1} Mean: {2} Median: {3} Min: {4} Max: {5} Q1: {6} Q3: {7} ";
/*     */   
/*     */   public BoxAndWhiskerXYToolTipGenerator() {
/*  93 */     super("X: {1} Mean: {2} Median: {3} Min: {4} Max: {5} Q1: {6} Q3: {7} ", NumberFormat.getInstance(), 
/*  94 */         NumberFormat.getInstance());
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
/*     */   public BoxAndWhiskerXYToolTipGenerator(String toolTipFormat, DateFormat dateFormat, NumberFormat numberFormat) {
/* 111 */     super(toolTipFormat, dateFormat, numberFormat);
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
/*     */   protected Object[] createItemArray(XYDataset dataset, int series, int item) {
/* 128 */     Object[] result = new Object[8];
/* 129 */     result[0] = dataset.getSeriesKey(series).toString();
/* 130 */     Number x = dataset.getX(series, item);
/* 131 */     if (getXDateFormat() != null) {
/* 132 */       result[1] = getXDateFormat().format(new Date(x.longValue()));
/*     */     } else {
/*     */       
/* 135 */       result[1] = getXFormat().format(x);
/*     */     } 
/* 137 */     NumberFormat formatter = getYFormat();
/*     */     
/* 139 */     if (dataset instanceof BoxAndWhiskerXYDataset) {
/* 140 */       BoxAndWhiskerXYDataset d = (BoxAndWhiskerXYDataset)dataset;
/* 141 */       result[2] = formatter.format(d.getMeanValue(series, item));
/* 142 */       result[3] = formatter.format(d.getMedianValue(series, item));
/* 143 */       result[4] = formatter.format(d.getMinRegularValue(series, item));
/* 144 */       result[5] = formatter.format(d.getMaxRegularValue(series, item));
/* 145 */       result[6] = formatter.format(d.getQ1Value(series, item));
/* 146 */       result[7] = formatter.format(d.getQ3Value(series, item));
/*     */     } 
/* 148 */     return result;
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
/* 163 */     if (!(obj instanceof BoxAndWhiskerXYToolTipGenerator)) {
/* 164 */       return false;
/*     */     }
/* 166 */     return super.equals(obj);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/BoxAndWhiskerXYToolTipGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */