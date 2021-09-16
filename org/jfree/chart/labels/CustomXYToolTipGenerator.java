/*     */ package org.jfree.chart.labels;
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
/*     */ public class CustomXYToolTipGenerator
/*     */   implements XYToolTipGenerator, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8636030004670141362L;
/*  66 */   private List toolTipSeries = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  81 */     return this.toolTipSeries.size();
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
/*     */   public int getToolTipCount(int list) {
/*  93 */     int result = 0;
/*  94 */     List tooltips = this.toolTipSeries.get(list);
/*  95 */     if (tooltips != null) {
/*  96 */       result = tooltips.size();
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
/*     */   
/*     */   public String getToolTipText(int series, int item) {
/* 111 */     String result = null;
/*     */     
/* 113 */     if (series < getListCount()) {
/* 114 */       List<String> tooltips = this.toolTipSeries.get(series);
/* 115 */       if (tooltips != null && 
/* 116 */         item < tooltips.size()) {
/* 117 */         result = tooltips.get(item);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 122 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToolTipSeries(List toolTips) {
/* 131 */     this.toolTipSeries.add(toolTips);
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
/*     */   public String generateToolTip(XYDataset data, int series, int item) {
/* 145 */     return getToolTipText(series, item);
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
/* 158 */     CustomXYToolTipGenerator clone = (CustomXYToolTipGenerator)super.clone();
/* 159 */     if (this.toolTipSeries != null) {
/* 160 */       clone.toolTipSeries = new ArrayList(this.toolTipSeries);
/*     */     }
/* 162 */     return clone;
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
/* 174 */     if (obj == this) {
/* 175 */       return true;
/*     */     }
/* 177 */     if (obj instanceof CustomXYToolTipGenerator) {
/* 178 */       CustomXYToolTipGenerator generator = (CustomXYToolTipGenerator)obj;
/* 179 */       boolean result = true;
/* 180 */       for (int series = 0; series < getListCount(); series++) {
/* 181 */         for (int item = 0; item < getToolTipCount(series); item++) {
/* 182 */           String t1 = getToolTipText(series, item);
/* 183 */           String t2 = generator.getToolTipText(series, item);
/* 184 */           if (t1 != null) {
/* 185 */             result = (result && t1.equals(t2));
/*     */           } else {
/*     */             
/* 188 */             result = (result && t2 == null);
/*     */           } 
/*     */         } 
/*     */       } 
/* 192 */       return result;
/*     */     } 
/* 194 */     return false;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/CustomXYToolTipGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */