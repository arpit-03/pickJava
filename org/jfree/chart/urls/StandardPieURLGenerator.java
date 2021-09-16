/*     */ package org.jfree.chart.urls;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.general.PieDataset;
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
/*     */ public class StandardPieURLGenerator
/*     */   implements PieURLGenerator, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1626966402065883419L;
/*  71 */   private String prefix = "index.html";
/*     */ 
/*     */   
/*  74 */   private String categoryParamName = "category";
/*     */ 
/*     */   
/*  77 */   private String indexParamName = "pieIndex";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StandardPieURLGenerator() {
/*  83 */     this("index.html");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StandardPieURLGenerator(String prefix) {
/*  92 */     this(prefix, "category");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StandardPieURLGenerator(String prefix, String categoryParamName) {
/* 103 */     this(prefix, categoryParamName, "pieIndex");
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
/*     */   public StandardPieURLGenerator(String prefix, String categoryParamName, String indexParamName) {
/* 116 */     ParamChecks.nullNotPermitted(prefix, "prefix");
/* 117 */     ParamChecks.nullNotPermitted(categoryParamName, "categoryParamName");
/* 118 */     this.prefix = prefix;
/* 119 */     this.categoryParamName = categoryParamName;
/* 120 */     this.indexParamName = indexParamName;
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
/*     */   public String generateURL(PieDataset dataset, Comparable key, int pieIndex) {
/* 135 */     String url = this.prefix;
/*     */     try {
/* 137 */       if (url.contains("?")) {
/*     */         
/* 139 */         url = url + "&amp;" + this.categoryParamName + "=" + URLEncoder.encode(key.toString(), "UTF-8");
/*     */       } else {
/*     */         
/* 142 */         url = url + "?" + this.categoryParamName + "=" + URLEncoder.encode(key.toString(), "UTF-8");
/*     */       } 
/* 144 */       if (this.indexParamName != null) {
/* 145 */         url = url + "&amp;" + this.indexParamName + "=" + pieIndex;
/*     */       }
/* 147 */     } catch (UnsupportedEncodingException e) {
/* 148 */       throw new RuntimeException(e);
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
/*     */   public boolean equals(Object obj) {
/* 162 */     if (obj == this) {
/* 163 */       return true;
/*     */     }
/* 165 */     if (!(obj instanceof StandardPieURLGenerator)) {
/* 166 */       return false;
/*     */     }
/* 168 */     StandardPieURLGenerator that = (StandardPieURLGenerator)obj;
/* 169 */     if (!this.prefix.equals(that.prefix)) {
/* 170 */       return false;
/*     */     }
/* 172 */     if (!this.categoryParamName.equals(that.categoryParamName)) {
/* 173 */       return false;
/*     */     }
/* 175 */     if (!ObjectUtilities.equal(this.indexParamName, that.indexParamName)) {
/* 176 */       return false;
/*     */     }
/* 178 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/urls/StandardPieURLGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */