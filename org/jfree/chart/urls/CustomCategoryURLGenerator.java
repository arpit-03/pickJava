/*     */ package org.jfree.chart.urls;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jfree.data.category.CategoryDataset;
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
/*     */ public class CustomCategoryURLGenerator
/*     */   implements CategoryURLGenerator, Cloneable, PublicCloneable, Serializable
/*     */ {
/*  58 */   private ArrayList urlSeries = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  73 */     return this.urlSeries.size();
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
/*  84 */     int result = 0;
/*  85 */     List urls = this.urlSeries.get(list);
/*  86 */     if (urls != null) {
/*  87 */       result = urls.size();
/*     */     }
/*  89 */     return result;
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
/* 101 */     String result = null;
/* 102 */     if (series < getListCount()) {
/* 103 */       List<String> urls = this.urlSeries.get(series);
/* 104 */       if (urls != null && 
/* 105 */         item < urls.size()) {
/* 106 */         result = urls.get(item);
/*     */       }
/*     */     } 
/*     */     
/* 110 */     return result;
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
/*     */   public String generateURL(CategoryDataset dataset, int series, int item) {
/* 124 */     return getURL(series, item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addURLSeries(List<?> urls) {
/* 133 */     List listToAdd = null;
/* 134 */     if (urls != null) {
/* 135 */       listToAdd = new ArrayList(urls);
/*     */     }
/* 137 */     this.urlSeries.add(listToAdd);
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
/* 149 */     if (obj == this) {
/* 150 */       return true;
/*     */     }
/* 152 */     if (!(obj instanceof CustomCategoryURLGenerator)) {
/* 153 */       return false;
/*     */     }
/* 155 */     CustomCategoryURLGenerator generator = (CustomCategoryURLGenerator)obj;
/* 156 */     int listCount = getListCount();
/* 157 */     if (listCount != generator.getListCount()) {
/* 158 */       return false;
/*     */     }
/*     */     
/* 161 */     for (int series = 0; series < listCount; series++) {
/* 162 */       int urlCount = getURLCount(series);
/* 163 */       if (urlCount != generator.getURLCount(series)) {
/* 164 */         return false;
/*     */       }
/*     */       
/* 167 */       for (int item = 0; item < urlCount; item++) {
/* 168 */         String u1 = getURL(series, item);
/* 169 */         String u2 = generator.getURL(series, item);
/* 170 */         if (u1 != null) {
/* 171 */           if (!u1.equals(u2)) {
/* 172 */             return false;
/*     */           }
/*     */         }
/* 175 */         else if (u2 != null) {
/* 176 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     return true;
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
/* 195 */     CustomCategoryURLGenerator clone = (CustomCategoryURLGenerator)super.clone();
/* 196 */     clone.urlSeries = new ArrayList(this.urlSeries);
/* 197 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/urls/CustomCategoryURLGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */