/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import org.jfree.util.ObjectUtilities;
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
/*     */ public class VectorSeriesCollection
/*     */   extends AbstractXYDataset
/*     */   implements VectorXYDataset, PublicCloneable, Serializable
/*     */ {
/*  71 */   private List data = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSeries(VectorSeries series) {
/*  81 */     ParamChecks.nullNotPermitted(series, "series");
/*  82 */     this.data.add(series);
/*  83 */     series.addChangeListener((SeriesChangeListener)this);
/*  84 */     fireDatasetChanged();
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
/*     */   public boolean removeSeries(VectorSeries series) {
/*  97 */     ParamChecks.nullNotPermitted(series, "series");
/*  98 */     boolean removed = this.data.remove(series);
/*  99 */     if (removed) {
/* 100 */       series.removeChangeListener((SeriesChangeListener)this);
/* 101 */       fireDatasetChanged();
/*     */     } 
/* 103 */     return removed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllSeries() {
/* 114 */     for (int i = 0; i < this.data.size(); i++) {
/* 115 */       VectorSeries series = this.data.get(i);
/* 116 */       series.removeChangeListener((SeriesChangeListener)this);
/*     */     } 
/*     */ 
/*     */     
/* 120 */     this.data.clear();
/* 121 */     fireDatasetChanged();
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
/* 132 */     return this.data.size();
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
/*     */   public VectorSeries getSeries(int series) {
/* 146 */     if (series < 0 || series >= getSeriesCount()) {
/* 147 */       throw new IllegalArgumentException("Series index out of bounds");
/*     */     }
/* 149 */     return this.data.get(series);
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
/*     */   public Comparable getSeriesKey(int series) {
/* 166 */     return getSeries(series).getKey();
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
/*     */   public int indexOf(VectorSeries series) {
/* 178 */     ParamChecks.nullNotPermitted(series, "series");
/* 179 */     return this.data.indexOf(series);
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
/*     */   public int getItemCount(int series) {
/* 195 */     return getSeries(series).getItemCount();
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
/*     */   public double getXValue(int series, int item) {
/* 208 */     VectorSeries s = this.data.get(series);
/* 209 */     VectorDataItem di = (VectorDataItem)s.getDataItem(item);
/* 210 */     return di.getXValue();
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
/* 225 */     return new Double(getXValue(series, item));
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
/*     */   public double getYValue(int series, int item) {
/* 238 */     VectorSeries s = this.data.get(series);
/* 239 */     VectorDataItem di = (VectorDataItem)s.getDataItem(item);
/* 240 */     return di.getYValue();
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
/* 255 */     return new Double(getYValue(series, item));
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
/*     */   public Vector getVector(int series, int item) {
/* 268 */     VectorSeries s = this.data.get(series);
/* 269 */     VectorDataItem di = (VectorDataItem)s.getDataItem(item);
/* 270 */     return di.getVector();
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
/*     */   public double getVectorXValue(int series, int item) {
/* 283 */     VectorSeries s = this.data.get(series);
/* 284 */     VectorDataItem di = (VectorDataItem)s.getDataItem(item);
/* 285 */     return di.getVectorX();
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
/*     */   public double getVectorYValue(int series, int item) {
/* 298 */     VectorSeries s = this.data.get(series);
/* 299 */     VectorDataItem di = (VectorDataItem)s.getDataItem(item);
/* 300 */     return di.getVectorY();
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
/* 312 */     if (obj == this) {
/* 313 */       return true;
/*     */     }
/* 315 */     if (!(obj instanceof VectorSeriesCollection)) {
/* 316 */       return false;
/*     */     }
/* 318 */     VectorSeriesCollection that = (VectorSeriesCollection)obj;
/* 319 */     return ObjectUtilities.equal(this.data, that.data);
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
/* 332 */     VectorSeriesCollection clone = (VectorSeriesCollection)super.clone();
/* 333 */     clone.data = (List)ObjectUtilities.deepClone(this.data);
/* 334 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/VectorSeriesCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */