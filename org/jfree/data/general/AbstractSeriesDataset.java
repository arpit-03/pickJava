/*     */ package org.jfree.data.general;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSeriesDataset
/*     */   extends AbstractDataset
/*     */   implements SeriesDataset, SeriesChangeListener, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6074996219705033171L;
/*     */   
/*     */   public abstract int getSeriesCount();
/*     */   
/*     */   public abstract Comparable getSeriesKey(int paramInt);
/*     */   
/*     */   public int indexOf(Comparable seriesKey) {
/*  98 */     int seriesCount = getSeriesCount();
/*  99 */     for (int s = 0; s < seriesCount; s++) {
/* 100 */       if (getSeriesKey(s).equals(seriesKey)) {
/* 101 */         return s;
/*     */       }
/*     */     } 
/* 104 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void seriesChanged(SeriesChangeEvent event) {
/* 114 */     fireDatasetChanged();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/general/AbstractSeriesDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */