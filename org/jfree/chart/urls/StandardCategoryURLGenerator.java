/*     */ package org.jfree.chart.urls;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.category.CategoryDataset;
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
/*     */ public class StandardCategoryURLGenerator
/*     */   implements CategoryURLGenerator, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2276668053074881909L;
/*  77 */   private String prefix = "index.html";
/*     */ 
/*     */   
/*  80 */   private String seriesParameterName = "series";
/*     */ 
/*     */   
/*  83 */   private String categoryParameterName = "category";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StandardCategoryURLGenerator() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StandardCategoryURLGenerator(String prefix) {
/*  98 */     ParamChecks.nullNotPermitted(prefix, "prefix");
/*  99 */     this.prefix = prefix;
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
/*     */   public StandardCategoryURLGenerator(String prefix, String seriesParameterName, String categoryParameterName) {
/* 114 */     ParamChecks.nullNotPermitted(prefix, "prefix");
/* 115 */     ParamChecks.nullNotPermitted(seriesParameterName, "seriesParameterName");
/*     */     
/* 117 */     ParamChecks.nullNotPermitted(categoryParameterName, "categoryParameterName");
/*     */     
/* 119 */     this.prefix = prefix;
/* 120 */     this.seriesParameterName = seriesParameterName;
/* 121 */     this.categoryParameterName = categoryParameterName;
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
/*     */   public String generateURL(CategoryDataset dataset, int series, int category) {
/* 137 */     String url = this.prefix;
/* 138 */     Comparable seriesKey = dataset.getRowKey(series);
/* 139 */     Comparable categoryKey = dataset.getColumnKey(category);
/* 140 */     boolean firstParameter = !url.contains("?");
/* 141 */     url = url + (firstParameter ? "?" : "&amp;");
/*     */     try {
/* 143 */       url = url + this.seriesParameterName + "=" + URLEncoder.encode(seriesKey
/* 144 */           .toString(), "UTF-8");
/*     */       
/* 146 */       url = url + "&amp;" + this.categoryParameterName + "=" + URLEncoder.encode(categoryKey.toString(), "UTF-8");
/* 147 */     } catch (UnsupportedEncodingException ex) {
/* 148 */       throw new RuntimeException(ex);
/*     */     } 
/* 150 */     return url;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 166 */     return super.clone();
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
/* 178 */     if (obj == this) {
/* 179 */       return true;
/*     */     }
/* 181 */     if (!(obj instanceof StandardCategoryURLGenerator)) {
/* 182 */       return false;
/*     */     }
/* 184 */     StandardCategoryURLGenerator that = (StandardCategoryURLGenerator)obj;
/* 185 */     if (!ObjectUtilities.equal(this.prefix, that.prefix)) {
/* 186 */       return false;
/*     */     }
/*     */     
/* 189 */     if (!ObjectUtilities.equal(this.seriesParameterName, that.seriesParameterName))
/*     */     {
/* 191 */       return false;
/*     */     }
/* 193 */     if (!ObjectUtilities.equal(this.categoryParameterName, that.categoryParameterName))
/*     */     {
/* 195 */       return false;
/*     */     }
/* 197 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 208 */     int result = (this.prefix != null) ? this.prefix.hashCode() : 0;
/*     */ 
/*     */     
/* 211 */     result = 29 * result + ((this.seriesParameterName != null) ? this.seriesParameterName.hashCode() : 0);
/*     */ 
/*     */     
/* 214 */     result = 29 * result + ((this.categoryParameterName != null) ? this.categoryParameterName.hashCode() : 0);
/* 215 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/urls/StandardCategoryURLGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */