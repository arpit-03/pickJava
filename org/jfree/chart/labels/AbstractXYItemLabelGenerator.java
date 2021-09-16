/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Date;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AbstractXYItemLabelGenerator
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5869744396278660636L;
/*     */   private String formatString;
/*     */   private NumberFormat xFormat;
/*     */   private DateFormat xDateFormat;
/*     */   private NumberFormat yFormat;
/*     */   private DateFormat yDateFormat;
/*  93 */   private String nullYString = "null";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractXYItemLabelGenerator() {
/*  99 */     this("{2}", NumberFormat.getNumberInstance(), 
/* 100 */         NumberFormat.getNumberInstance());
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
/*     */   protected AbstractXYItemLabelGenerator(String formatString, NumberFormat xFormat, NumberFormat yFormat) {
/* 116 */     ParamChecks.nullNotPermitted(formatString, "formatString");
/* 117 */     ParamChecks.nullNotPermitted(xFormat, "xFormat");
/* 118 */     ParamChecks.nullNotPermitted(yFormat, "yFormat");
/* 119 */     this.formatString = formatString;
/* 120 */     this.xFormat = xFormat;
/* 121 */     this.yFormat = yFormat;
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
/*     */   protected AbstractXYItemLabelGenerator(String formatString, DateFormat xFormat, NumberFormat yFormat) {
/* 137 */     this(formatString, NumberFormat.getInstance(), yFormat);
/* 138 */     this.xDateFormat = xFormat;
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
/*     */ 
/*     */   
/*     */   protected AbstractXYItemLabelGenerator(String formatString, NumberFormat xFormat, DateFormat yFormat) {
/* 158 */     this(formatString, xFormat, NumberFormat.getInstance());
/* 159 */     this.yDateFormat = yFormat;
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
/*     */   protected AbstractXYItemLabelGenerator(String formatString, DateFormat xFormat, DateFormat yFormat) {
/* 175 */     this(formatString, NumberFormat.getInstance(), 
/* 176 */         NumberFormat.getInstance());
/* 177 */     this.xDateFormat = xFormat;
/* 178 */     this.yDateFormat = yFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormatString() {
/* 188 */     return this.formatString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getXFormat() {
/* 197 */     return this.xFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateFormat getXDateFormat() {
/* 206 */     return this.xDateFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getYFormat() {
/* 215 */     return this.yFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateFormat getYDateFormat() {
/* 224 */     return this.yDateFormat;
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
/*     */   public String generateLabelString(XYDataset dataset, int series, int item) {
/* 238 */     Object[] items = createItemArray(dataset, series, item);
/* 239 */     String result = MessageFormat.format(this.formatString, items);
/* 240 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNullYString() {
/* 251 */     return this.nullYString;
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
/*     */   protected Object[] createItemArray(XYDataset dataset, int series, int item) {
/* 267 */     Object[] result = new Object[3];
/* 268 */     result[0] = dataset.getSeriesKey(series).toString();
/*     */     
/* 270 */     double x = dataset.getXValue(series, item);
/* 271 */     if (this.xDateFormat != null) {
/* 272 */       result[1] = this.xDateFormat.format(new Date((long)x));
/*     */     } else {
/*     */       
/* 275 */       result[1] = this.xFormat.format(x);
/*     */     } 
/*     */     
/* 278 */     double y = dataset.getYValue(series, item);
/* 279 */     if (Double.isNaN(y) && dataset.getY(series, item) == null) {
/* 280 */       result[2] = this.nullYString;
/*     */     
/*     */     }
/* 283 */     else if (this.yDateFormat != null) {
/* 284 */       result[2] = this.yDateFormat.format(new Date((long)y));
/*     */     } else {
/*     */       
/* 287 */       result[2] = this.yFormat.format(y);
/*     */     } 
/*     */     
/* 290 */     return result;
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
/* 302 */     if (obj == this) {
/* 303 */       return true;
/*     */     }
/* 305 */     if (!(obj instanceof AbstractXYItemLabelGenerator)) {
/* 306 */       return false;
/*     */     }
/* 308 */     AbstractXYItemLabelGenerator that = (AbstractXYItemLabelGenerator)obj;
/* 309 */     if (!this.formatString.equals(that.formatString)) {
/* 310 */       return false;
/*     */     }
/* 312 */     if (!ObjectUtilities.equal(this.xFormat, that.xFormat)) {
/* 313 */       return false;
/*     */     }
/* 315 */     if (!ObjectUtilities.equal(this.xDateFormat, that.xDateFormat)) {
/* 316 */       return false;
/*     */     }
/* 318 */     if (!ObjectUtilities.equal(this.yFormat, that.yFormat)) {
/* 319 */       return false;
/*     */     }
/* 321 */     if (!ObjectUtilities.equal(this.yDateFormat, that.yDateFormat)) {
/* 322 */       return false;
/*     */     }
/* 324 */     if (!this.nullYString.equals(that.nullYString)) {
/* 325 */       return false;
/*     */     }
/* 327 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 337 */     int result = 127;
/* 338 */     result = HashUtilities.hashCode(result, this.formatString);
/* 339 */     result = HashUtilities.hashCode(result, this.xFormat);
/* 340 */     result = HashUtilities.hashCode(result, this.xDateFormat);
/* 341 */     result = HashUtilities.hashCode(result, this.yFormat);
/* 342 */     result = HashUtilities.hashCode(result, this.yDateFormat);
/* 343 */     return result;
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
/* 356 */     AbstractXYItemLabelGenerator clone = (AbstractXYItemLabelGenerator)super.clone();
/* 357 */     if (this.xFormat != null) {
/* 358 */       clone.xFormat = (NumberFormat)this.xFormat.clone();
/*     */     }
/* 360 */     if (this.yFormat != null) {
/* 361 */       clone.yFormat = (NumberFormat)this.yFormat.clone();
/*     */     }
/* 363 */     if (this.xDateFormat != null) {
/* 364 */       clone.xDateFormat = (DateFormat)this.xDateFormat.clone();
/*     */     }
/* 366 */     if (this.yDateFormat != null) {
/* 367 */       clone.yDateFormat = (DateFormat)this.yDateFormat.clone();
/*     */     }
/* 369 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/AbstractXYItemLabelGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */