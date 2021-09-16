/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Date;
/*     */ import org.jfree.data.xy.IntervalXYDataset;
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
/*     */ public class IntervalXYItemLabelGenerator
/*     */   extends AbstractXYItemLabelGenerator
/*     */   implements XYItemLabelGenerator, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   public static final String DEFAULT_ITEM_LABEL_FORMAT = "{5} - {6}";
/*     */   
/*     */   public IntervalXYItemLabelGenerator() {
/*  70 */     this("{5} - {6}", NumberFormat.getNumberInstance(), 
/*  71 */         NumberFormat.getNumberInstance());
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
/*     */   public IntervalXYItemLabelGenerator(String formatString, NumberFormat xFormat, NumberFormat yFormat) {
/*  87 */     super(formatString, xFormat, yFormat);
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
/*     */   public IntervalXYItemLabelGenerator(String formatString, DateFormat xFormat, NumberFormat yFormat) {
/* 103 */     super(formatString, xFormat, yFormat);
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
/*     */   
/*     */   public IntervalXYItemLabelGenerator(String formatString, NumberFormat xFormat, DateFormat yFormat) {
/* 121 */     super(formatString, xFormat, yFormat);
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
/*     */   public IntervalXYItemLabelGenerator(String formatString, DateFormat xFormat, DateFormat yFormat) {
/* 137 */     super(formatString, xFormat, yFormat);
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
/*     */   
/*     */   protected Object[] createItemArray(XYDataset dataset, int series, int item) {
/* 155 */     IntervalXYDataset intervalDataset = null;
/* 156 */     if (dataset instanceof IntervalXYDataset) {
/* 157 */       intervalDataset = (IntervalXYDataset)dataset;
/*     */     }
/* 159 */     Object[] result = new Object[7];
/* 160 */     result[0] = dataset.getSeriesKey(series).toString();
/*     */     
/* 162 */     double x = dataset.getXValue(series, item);
/* 163 */     double xs = x;
/* 164 */     double xe = x;
/* 165 */     double y = dataset.getYValue(series, item);
/* 166 */     double ys = y;
/* 167 */     double ye = y;
/* 168 */     if (intervalDataset != null) {
/* 169 */       xs = intervalDataset.getStartXValue(series, item);
/* 170 */       xe = intervalDataset.getEndXValue(series, item);
/* 171 */       ys = intervalDataset.getStartYValue(series, item);
/* 172 */       ye = intervalDataset.getEndYValue(series, item);
/*     */     } 
/*     */     
/* 175 */     DateFormat xdf = getXDateFormat();
/* 176 */     if (xdf != null) {
/* 177 */       result[1] = xdf.format(new Date((long)x));
/* 178 */       result[2] = xdf.format(new Date((long)xs));
/* 179 */       result[3] = xdf.format(new Date((long)xe));
/*     */     } else {
/*     */       
/* 182 */       NumberFormat xnf = getXFormat();
/* 183 */       result[1] = xnf.format(x);
/* 184 */       result[2] = xnf.format(xs);
/* 185 */       result[3] = xnf.format(xe);
/*     */     } 
/*     */     
/* 188 */     NumberFormat ynf = getYFormat();
/* 189 */     DateFormat ydf = getYDateFormat();
/* 190 */     if (Double.isNaN(y) && dataset.getY(series, item) == null) {
/* 191 */       result[4] = getNullYString();
/*     */     
/*     */     }
/* 194 */     else if (ydf != null) {
/* 195 */       result[4] = ydf.format(new Date((long)y));
/*     */     } else {
/*     */       
/* 198 */       result[4] = ynf.format(y);
/*     */     } 
/*     */     
/* 201 */     if (Double.isNaN(ys) && intervalDataset != null && intervalDataset
/* 202 */       .getStartY(series, item) == null) {
/* 203 */       result[5] = getNullYString();
/*     */     
/*     */     }
/* 206 */     else if (ydf != null) {
/* 207 */       result[5] = ydf.format(new Date((long)ys));
/*     */     } else {
/*     */       
/* 210 */       result[5] = ynf.format(ys);
/*     */     } 
/*     */     
/* 213 */     if (Double.isNaN(ye) && intervalDataset != null && intervalDataset
/* 214 */       .getEndY(series, item) == null) {
/* 215 */       result[6] = getNullYString();
/*     */     
/*     */     }
/* 218 */     else if (ydf != null) {
/* 219 */       result[6] = ydf.format(new Date((long)ye));
/*     */     } else {
/*     */       
/* 222 */       result[6] = ynf.format(ye);
/*     */     } 
/*     */     
/* 225 */     return result;
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
/*     */   public String generateLabel(XYDataset dataset, int series, int item) {
/* 239 */     return generateLabelString(dataset, series, item);
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
/* 251 */     return super.clone();
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
/* 263 */     if (obj == this) {
/* 264 */       return true;
/*     */     }
/* 266 */     if (!(obj instanceof IntervalXYItemLabelGenerator)) {
/* 267 */       return false;
/*     */     }
/* 269 */     return super.equals(obj);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/IntervalXYItemLabelGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */