/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.awt.geom.Point2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CategoryCrosshairState
/*     */   extends CrosshairState
/*     */ {
/*  72 */   private Comparable rowKey = null;
/*  73 */   private Comparable columnKey = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparable getRowKey() {
/*  82 */     return this.rowKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRowKey(Comparable key) {
/*  91 */     this.rowKey = key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparable getColumnKey() {
/* 100 */     return this.columnKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColumnKey(Comparable key) {
/* 109 */     this.columnKey = key;
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
/*     */   public void updateCrosshairPoint(Comparable rowKey, Comparable columnKey, double value, int datasetIndex, double transX, double transY, PlotOrientation orientation) {
/* 128 */     Point2D anchor = getAnchor();
/* 129 */     if (anchor != null) {
/* 130 */       double xx = anchor.getX();
/* 131 */       double yy = anchor.getY();
/* 132 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 133 */         double temp = yy;
/* 134 */         yy = xx;
/* 135 */         xx = temp;
/*     */       } 
/* 137 */       double d = (transX - xx) * (transX - xx) + (transY - yy) * (transY - yy);
/*     */ 
/*     */       
/* 140 */       if (d < getCrosshairDistance()) {
/* 141 */         this.rowKey = rowKey;
/* 142 */         this.columnKey = columnKey;
/* 143 */         setCrosshairY(value);
/* 144 */         setDatasetIndex(datasetIndex);
/* 145 */         setCrosshairDistance(d);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCrosshairX(Comparable rowKey, Comparable columnKey, int datasetIndex, double transX, PlotOrientation orientation) {
/* 164 */     Point2D anchor = getAnchor();
/* 165 */     if (anchor != null) {
/* 166 */       double anchorX = anchor.getX();
/* 167 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 168 */         anchorX = anchor.getY();
/*     */       }
/* 170 */       double d = Math.abs(transX - anchorX);
/* 171 */       if (d < getCrosshairDistance()) {
/* 172 */         this.rowKey = rowKey;
/* 173 */         this.columnKey = columnKey;
/* 174 */         setDatasetIndex(datasetIndex);
/* 175 */         setCrosshairDistance(d);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/CategoryCrosshairState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */