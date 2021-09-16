/*     */ package org.jfree.chart.renderer;
/*     */ 
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
/*     */ public class OutlierListCollection
/*     */ {
/*     */   private List outlierLists;
/*     */   private boolean highFarOut = false;
/*     */   private boolean lowFarOut = false;
/*     */   
/*     */   public OutlierListCollection() {
/*  82 */     this.outlierLists = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHighFarOut() {
/*  92 */     return this.highFarOut;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHighFarOut(boolean farOut) {
/* 102 */     this.highFarOut = farOut;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLowFarOut() {
/* 112 */     return this.lowFarOut;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLowFarOut(boolean farOut) {
/* 122 */     this.lowFarOut = farOut;
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
/*     */   public boolean add(Outlier outlier) {
/* 137 */     if (this.outlierLists.isEmpty()) {
/* 138 */       return this.outlierLists.add(new OutlierList(outlier));
/*     */     }
/*     */     
/* 141 */     boolean updated = false;
/* 142 */     Iterator<OutlierList> iterator = this.outlierLists.iterator();
/* 143 */     while (iterator.hasNext()) {
/* 144 */       OutlierList list = iterator.next();
/* 145 */       if (list.isOverlapped(outlier)) {
/* 146 */         updated = updateOutlierList(list, outlier);
/*     */       }
/*     */     } 
/* 149 */     if (!updated)
/*     */     {
/* 151 */       updated = this.outlierLists.add(new OutlierList(outlier));
/*     */     }
/* 153 */     return updated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator iterator() {
/* 164 */     return this.outlierLists.iterator();
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
/*     */   private boolean updateOutlierList(OutlierList list, Outlier outlier) {
/* 179 */     boolean result = false;
/* 180 */     result = list.add(outlier);
/* 181 */     list.updateAveragedOutlier();
/* 182 */     list.setMultiple(true);
/* 183 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/OutlierListCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */