/*     */ package org.jfree.chart.urls;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Date;
/*     */ import org.jfree.chart.util.ParamChecks;
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
/*     */ public class TimeSeriesURLGenerator
/*     */   implements XYURLGenerator, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -9122773175671182445L;
/*  72 */   private DateFormat dateFormat = DateFormat.getInstance();
/*     */ 
/*     */   
/*  75 */   private String prefix = "index.html";
/*     */ 
/*     */   
/*  78 */   private String seriesParameterName = "series";
/*     */ 
/*     */   
/*  81 */   private String itemParameterName = "item";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeSeriesURLGenerator() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeSeriesURLGenerator(DateFormat dateFormat, String prefix, String seriesParameterName, String itemParameterName) {
/* 104 */     ParamChecks.nullNotPermitted(dateFormat, "dateFormat");
/* 105 */     ParamChecks.nullNotPermitted(prefix, "prefix");
/* 106 */     ParamChecks.nullNotPermitted(seriesParameterName, "seriesParameterName");
/* 107 */     ParamChecks.nullNotPermitted(itemParameterName, "itemParameterName");
/* 108 */     this.dateFormat = (DateFormat)dateFormat.clone();
/* 109 */     this.prefix = prefix;
/* 110 */     this.seriesParameterName = seriesParameterName;
/* 111 */     this.itemParameterName = itemParameterName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateFormat getDateFormat() {
/* 122 */     return (DateFormat)this.dateFormat.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 133 */     return this.prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSeriesParameterName() {
/* 144 */     return this.seriesParameterName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItemParameterName() {
/* 155 */     return this.itemParameterName;
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
/*     */   public String generateURL(XYDataset dataset, int series, int item) {
/* 169 */     String result = this.prefix;
/* 170 */     boolean firstParameter = !result.contains("?");
/* 171 */     Comparable seriesKey = dataset.getSeriesKey(series);
/* 172 */     if (seriesKey != null) {
/* 173 */       result = result + (firstParameter ? "?" : "&amp;");
/*     */       try {
/* 175 */         result = result + this.seriesParameterName + "=" + URLEncoder.encode(seriesKey
/* 176 */             .toString(), "UTF-8");
/* 177 */       } catch (UnsupportedEncodingException ex) {
/* 178 */         throw new RuntimeException(ex);
/*     */       } 
/* 180 */       firstParameter = false;
/*     */     } 
/*     */     
/* 183 */     long x = (long)dataset.getXValue(series, item);
/* 184 */     String xValue = this.dateFormat.format(new Date(x));
/* 185 */     result = result + (firstParameter ? "?" : "&amp;");
/*     */     try {
/* 187 */       result = result + this.itemParameterName + "=" + URLEncoder.encode(xValue, "UTF-8");
/*     */     }
/* 189 */     catch (UnsupportedEncodingException ex) {
/* 190 */       throw new RuntimeException(ex);
/*     */     } 
/*     */     
/* 193 */     return result;
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
/* 205 */     if (obj == this) {
/* 206 */       return true;
/*     */     }
/* 208 */     if (!(obj instanceof TimeSeriesURLGenerator)) {
/* 209 */       return false;
/*     */     }
/* 211 */     TimeSeriesURLGenerator that = (TimeSeriesURLGenerator)obj;
/* 212 */     if (!this.dateFormat.equals(that.dateFormat)) {
/* 213 */       return false;
/*     */     }
/* 215 */     if (!this.itemParameterName.equals(that.itemParameterName)) {
/* 216 */       return false;
/*     */     }
/* 218 */     if (!this.prefix.equals(that.prefix)) {
/* 219 */       return false;
/*     */     }
/* 221 */     if (!this.seriesParameterName.equals(that.seriesParameterName)) {
/* 222 */       return false;
/*     */     }
/* 224 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/urls/TimeSeriesURLGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */