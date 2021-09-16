/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
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
/*     */ public class DefaultWindDataset
/*     */   extends AbstractXYDataset
/*     */   implements WindDataset, PublicCloneable
/*     */ {
/*     */   private List seriesKeys;
/*     */   private List allSeriesData;
/*     */   
/*     */   public DefaultWindDataset() {
/*  77 */     this.seriesKeys = new ArrayList();
/*  78 */     this.allSeriesData = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultWindDataset(Object[][][] data) {
/*  89 */     this(seriesNameListFromDataArray((Object[][])data), data);
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
/*     */   public DefaultWindDataset(String[] seriesNames, Object[][][] data) {
/* 103 */     this(Arrays.asList(seriesNames), data);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultWindDataset(List seriesKeys, Object[][][] data) {
/* 131 */     ParamChecks.nullNotPermitted(seriesKeys, "seriesKeys");
/* 132 */     if (seriesKeys.size() != data.length) {
/* 133 */       throw new IllegalArgumentException("The number of series keys does not match the number of series in the data array.");
/*     */     }
/*     */     
/* 136 */     this.seriesKeys = seriesKeys;
/* 137 */     int seriesCount = data.length;
/* 138 */     this.allSeriesData = new ArrayList(seriesCount);
/*     */     
/* 140 */     for (int seriesIndex = 0; seriesIndex < seriesCount; seriesIndex++) {
/* 141 */       List<WindDataItem> oneSeriesData = new ArrayList();
/* 142 */       int maxItemCount = (data[seriesIndex]).length;
/* 143 */       for (int itemIndex = 0; itemIndex < maxItemCount; itemIndex++) {
/* 144 */         Object xObject = data[seriesIndex][itemIndex][0];
/* 145 */         if (xObject != null) {
/*     */           Number xNumber;
/* 147 */           if (xObject instanceof Number) {
/* 148 */             xNumber = (Number)xObject;
/*     */           
/*     */           }
/* 151 */           else if (xObject instanceof Date) {
/* 152 */             Date xDate = (Date)xObject;
/* 153 */             xNumber = new Long(xDate.getTime());
/*     */           } else {
/*     */             
/* 156 */             xNumber = new Integer(0);
/*     */           } 
/*     */           
/* 159 */           Number windDir = (Number)data[seriesIndex][itemIndex][1];
/* 160 */           Number windForce = (Number)data[seriesIndex][itemIndex][2];
/* 161 */           oneSeriesData.add(new WindDataItem(xNumber, windDir, windForce));
/*     */         } 
/*     */       } 
/*     */       
/* 165 */       Collections.sort(oneSeriesData);
/* 166 */       this.allSeriesData.add(seriesIndex, oneSeriesData);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeriesCount() {
/* 178 */     return this.allSeriesData.size();
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
/*     */   public int getItemCount(int series) {
/* 190 */     if (series < 0 || series >= getSeriesCount()) {
/* 191 */       throw new IllegalArgumentException("Invalid series index: " + series);
/*     */     }
/*     */     
/* 194 */     List oneSeriesData = this.allSeriesData.get(series);
/* 195 */     return oneSeriesData.size();
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
/*     */   public Comparable getSeriesKey(int series) {
/* 207 */     if (series < 0 || series >= getSeriesCount()) {
/* 208 */       throw new IllegalArgumentException("Invalid series index: " + series);
/*     */     }
/*     */     
/* 211 */     return this.seriesKeys.get(series);
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
/*     */   public Number getX(int series, int item) {
/* 226 */     List<WindDataItem> oneSeriesData = this.allSeriesData.get(series);
/* 227 */     WindDataItem windItem = oneSeriesData.get(item);
/* 228 */     return windItem.getX();
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
/*     */   public Number getY(int series, int item) {
/* 243 */     return getWindForce(series, item);
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
/*     */   public Number getWindDirection(int series, int item) {
/* 257 */     List<WindDataItem> oneSeriesData = this.allSeriesData.get(series);
/* 258 */     WindDataItem windItem = oneSeriesData.get(item);
/* 259 */     return windItem.getWindDirection();
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
/*     */   public Number getWindForce(int series, int item) {
/* 273 */     List<WindDataItem> oneSeriesData = this.allSeriesData.get(series);
/* 274 */     WindDataItem windItem = oneSeriesData.get(item);
/* 275 */     return windItem.getWindForce();
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
/*     */   public static List seriesNameListFromDataArray(Object[][] data) {
/* 288 */     int seriesCount = data.length;
/* 289 */     List<String> seriesNameList = new ArrayList(seriesCount);
/* 290 */     for (int i = 0; i < seriesCount; i++) {
/* 291 */       seriesNameList.add("Series " + (i + 1));
/*     */     }
/* 293 */     return seriesNameList;
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
/*     */   public boolean equals(Object obj) {
/* 313 */     if (this == obj) {
/* 314 */       return true;
/*     */     }
/* 316 */     if (!(obj instanceof DefaultWindDataset)) {
/* 317 */       return false;
/*     */     }
/* 319 */     DefaultWindDataset that = (DefaultWindDataset)obj;
/* 320 */     if (!this.seriesKeys.equals(that.seriesKeys)) {
/* 321 */       return false;
/*     */     }
/* 323 */     if (!this.allSeriesData.equals(that.allSeriesData)) {
/* 324 */       return false;
/*     */     }
/* 326 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/DefaultWindDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */