/*     */ package org.jfree.chart.urls;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.jfree.data.general.PieDataset;
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
/*     */ public class CustomPieURLGenerator
/*     */   implements PieURLGenerator, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7100607670144900503L;
/*  74 */   private ArrayList urls = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  91 */     return getURL(key, pieIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListCount() {
/* 102 */     return this.urls.size();
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
/*     */   public int getURLCount(int list) {
/* 116 */     int result = 0;
/* 117 */     Map urlMap = this.urls.get(list);
/* 118 */     if (urlMap != null) {
/* 119 */       result = urlMap.size();
/*     */     }
/* 121 */     return result;
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
/*     */   public String getURL(Comparable key, int mapIndex) {
/* 133 */     String result = null;
/* 134 */     if (mapIndex < getListCount()) {
/* 135 */       Map urlMap = this.urls.get(mapIndex);
/* 136 */       if (urlMap != null) {
/* 137 */         result = (String)urlMap.get(key);
/*     */       }
/*     */     } 
/* 140 */     return result;
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
/*     */   public void addURLs(Map urlMap) {
/* 155 */     this.urls.add(urlMap);
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
/*     */   public boolean equals(Object o) {
/* 168 */     if (o == this) {
/* 169 */       return true;
/*     */     }
/*     */     
/* 172 */     if (o instanceof CustomPieURLGenerator) {
/* 173 */       CustomPieURLGenerator generator = (CustomPieURLGenerator)o;
/* 174 */       if (getListCount() != generator.getListCount()) {
/* 175 */         return false;
/*     */       }
/*     */       
/* 178 */       for (int pieItem = 0; pieItem < getListCount(); pieItem++) {
/* 179 */         if (getURLCount(pieItem) != generator.getURLCount(pieItem)) {
/* 180 */           return false;
/*     */         }
/* 182 */         Set keySet = ((HashMap)this.urls.get(pieItem)).keySet();
/*     */         
/* 184 */         for (Iterator<String> i = keySet.iterator(); i.hasNext(); ) {
/* 185 */           String key = i.next();
/* 186 */           if (!getURL(key, pieItem).equals(generator
/* 187 */               .getURL(key, pieItem))) {
/* 188 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/* 192 */       return true;
/*     */     } 
/* 194 */     return false;
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
/* 206 */     CustomPieURLGenerator urlGen = new CustomPieURLGenerator();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     for (Iterator<Map> i = this.urls.iterator(); i.hasNext(); ) {
/* 212 */       Map map = i.next();
/*     */       
/* 214 */       Map<Object, Object> newMap = new HashMap<Object, Object>();
/* 215 */       for (Iterator<String> j = map.keySet().iterator(); j.hasNext(); ) {
/* 216 */         String key = j.next();
/* 217 */         newMap.put(key, map.get(key));
/*     */       } 
/*     */       
/* 220 */       urlGen.addURLs(newMap);
/*     */     } 
/*     */     
/* 223 */     return urlGen;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/urls/CustomPieURLGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */