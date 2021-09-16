/*     */ package org.jfree.chart.urls;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class StandardXYURLGenerator
/*     */   implements XYURLGenerator, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1771624523496595382L;
/*     */   public static final String DEFAULT_PREFIX = "index.html";
/*     */   public static final String DEFAULT_SERIES_PARAMETER = "series";
/*     */   public static final String DEFAULT_ITEM_PARAMETER = "item";
/*     */   private String prefix;
/*     */   private String seriesParameterName;
/*     */   private String itemParameterName;
/*     */   
/*     */   public StandardXYURLGenerator() {
/*  90 */     this("index.html", "series", "item");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StandardXYURLGenerator(String prefix) {
/* 101 */     this(prefix, "series", "item");
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
/*     */   public StandardXYURLGenerator(String prefix, String seriesParameterName, String itemParameterName) {
/* 115 */     ParamChecks.nullNotPermitted(prefix, "prefix");
/* 116 */     ParamChecks.nullNotPermitted(seriesParameterName, "seriesParameterName");
/* 117 */     ParamChecks.nullNotPermitted(itemParameterName, "itemParameterName");
/* 118 */     this.prefix = prefix;
/* 119 */     this.seriesParameterName = seriesParameterName;
/* 120 */     this.itemParameterName = itemParameterName;
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
/*     */   public String generateURL(XYDataset dataset, int series, int item) {
/* 135 */     String url = this.prefix;
/* 136 */     boolean firstParameter = (url.indexOf("?") == -1);
/* 137 */     url = url + (firstParameter ? "?" : "&amp;");
/* 138 */     url = url + this.seriesParameterName + "=" + series + "&amp;" + this.itemParameterName + "=" + item;
/*     */     
/* 140 */     return url;
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
/* 152 */     if (obj == this) {
/* 153 */       return true;
/*     */     }
/* 155 */     if (!(obj instanceof StandardXYURLGenerator)) {
/* 156 */       return false;
/*     */     }
/* 158 */     StandardXYURLGenerator that = (StandardXYURLGenerator)obj;
/* 159 */     if (!ObjectUtilities.equal(that.prefix, this.prefix)) {
/* 160 */       return false;
/*     */     }
/* 162 */     if (!ObjectUtilities.equal(that.seriesParameterName, this.seriesParameterName))
/*     */     {
/* 164 */       return false;
/*     */     }
/* 166 */     if (!ObjectUtilities.equal(that.itemParameterName, this.itemParameterName))
/*     */     {
/* 168 */       return false;
/*     */     }
/* 170 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/urls/StandardXYURLGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */