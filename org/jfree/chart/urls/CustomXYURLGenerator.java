/*     */ package org.jfree.chart.urls;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class CustomXYURLGenerator
/*     */   implements XYURLGenerator, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8565933356596551832L;
/*  67 */   private ArrayList urlSeries = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListCount() {
/*  82 */     return this.urlSeries.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getURLCount(int list) {
/*  93 */     int result = 0;
/*  94 */     List urls = this.urlSeries.get(list);
/*  95 */     if (urls != null) {
/*  96 */       result = urls.size();
/*     */     }
/*  98 */     return result;
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
/*     */   public String getURL(int series, int item) {
/* 110 */     String result = null;
/* 111 */     if (series < getListCount()) {
/* 112 */       List<String> urls = this.urlSeries.get(series);
/* 113 */       if (urls != null && 
/* 114 */         item < urls.size()) {
/* 115 */         result = urls.get(item);
/*     */       }
/*     */     } 
/*     */     
/* 119 */     return result;
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
/* 133 */     return getURL(series, item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addURLSeries(List<?> urls) {
/* 143 */     List listToAdd = null;
/* 144 */     if (urls != null) {
/* 145 */       listToAdd = new ArrayList(urls);
/*     */     }
/* 147 */     this.urlSeries.add(listToAdd);
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
/* 159 */     if (obj == this) {
/* 160 */       return true;
/*     */     }
/* 162 */     if (!(obj instanceof CustomXYURLGenerator)) {
/* 163 */       return false;
/*     */     }
/* 165 */     CustomXYURLGenerator that = (CustomXYURLGenerator)obj;
/* 166 */     int listCount = getListCount();
/* 167 */     if (listCount != that.getListCount()) {
/* 168 */       return false;
/*     */     }
/*     */     
/* 171 */     for (int series = 0; series < listCount; series++) {
/* 172 */       int urlCount = getURLCount(series);
/* 173 */       if (urlCount != that.getURLCount(series)) {
/* 174 */         return false;
/*     */       }
/*     */       
/* 177 */       for (int item = 0; item < urlCount; item++) {
/* 178 */         String u1 = getURL(series, item);
/* 179 */         String u2 = that.getURL(series, item);
/* 180 */         if (u1 != null) {
/* 181 */           if (!u1.equals(u2)) {
/* 182 */             return false;
/*     */           
/*     */           }
/*     */         }
/* 186 */         else if (u2 != null) {
/* 187 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     return true;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 206 */     CustomXYURLGenerator clone = (CustomXYURLGenerator)super.clone();
/* 207 */     clone.urlSeries = new ArrayList(this.urlSeries);
/* 208 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/urls/CustomXYURLGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */