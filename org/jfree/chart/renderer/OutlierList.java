/*     */ package org.jfree.chart.renderer;
/*     */ 
/*     */ import java.awt.geom.Point2D;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OutlierList
/*     */ {
/*     */   private List outliers;
/*     */   private Outlier averagedOutlier;
/*     */   private boolean multiple = false;
/*     */   
/*     */   public OutlierList(Outlier outlier) {
/*  86 */     this.outliers = new ArrayList();
/*  87 */     setAveragedOutlier(outlier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(Outlier outlier) {
/*  98 */     return this.outliers.add(outlier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/* 107 */     return this.outliers.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Outlier getAveragedOutlier() {
/* 116 */     return this.averagedOutlier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAveragedOutlier(Outlier averagedOutlier) {
/* 125 */     this.averagedOutlier = averagedOutlier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMultiple() {
/* 135 */     return this.multiple;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMultiple(boolean multiple) {
/* 145 */     this.multiple = multiple;
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
/*     */   public boolean isOverlapped(Outlier other) {
/* 158 */     if (other == null) {
/* 159 */       return false;
/*     */     }
/*     */     
/* 162 */     boolean result = other.overlaps(getAveragedOutlier());
/* 163 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateAveragedOutlier() {
/* 172 */     double totalXCoords = 0.0D;
/* 173 */     double totalYCoords = 0.0D;
/* 174 */     int size = getItemCount();
/* 175 */     Iterator<Outlier> iterator = this.outliers.iterator();
/* 176 */     while (iterator.hasNext()) {
/* 177 */       Outlier o = iterator.next();
/* 178 */       totalXCoords += o.getX();
/* 179 */       totalYCoords += o.getY();
/*     */     } 
/* 181 */     getAveragedOutlier().getPoint().setLocation(new Point2D.Double(totalXCoords / size, totalYCoords / size));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/OutlierList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */