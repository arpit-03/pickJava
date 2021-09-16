/*     */ package org.jfree.chart.entity;
/*     */ 
/*     */ import java.awt.Shape;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XYItemEntity
/*     */   extends ChartEntity
/*     */ {
/*     */   private static final long serialVersionUID = -3870862224880283771L;
/*     */   private transient XYDataset dataset;
/*     */   private int series;
/*     */   private int item;
/*     */   
/*     */   public XYItemEntity(Shape area, XYDataset dataset, int series, int item, String toolTipText, String urlText) {
/*  88 */     super(area, toolTipText, urlText);
/*  89 */     this.dataset = dataset;
/*  90 */     this.series = series;
/*  91 */     this.item = item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYDataset getDataset() {
/* 100 */     return this.dataset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDataset(XYDataset dataset) {
/* 109 */     this.dataset = dataset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeriesIndex() {
/* 118 */     return this.series;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeriesIndex(int series) {
/* 127 */     this.series = series;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItem() {
/* 136 */     return this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItem(int item) {
/* 145 */     this.item = item;
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
/* 157 */     if (obj == this) {
/* 158 */       return true;
/*     */     }
/* 160 */     if (obj instanceof XYItemEntity && super.equals(obj)) {
/* 161 */       XYItemEntity ie = (XYItemEntity)obj;
/* 162 */       if (this.series != ie.series) {
/* 163 */         return false;
/*     */       }
/* 165 */       if (this.item != ie.item) {
/* 166 */         return false;
/*     */       }
/* 168 */       return true;
/*     */     } 
/* 170 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 181 */     return "XYItemEntity: series = " + getSeriesIndex() + ", item = " + 
/* 182 */       getItem() + ", dataset = " + getDataset();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/entity/XYItemEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */