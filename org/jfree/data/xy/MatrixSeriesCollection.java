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
/*     */ public class MatrixSeriesCollection
/*     */   extends AbstractXYZDataset
/*     */   implements XYZDataset, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3197705779242543945L;
/*     */   
/*     */   public MatrixSeriesCollection() {
/*  74 */     this(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private List seriesList = new ArrayList();
/*     */   public MatrixSeriesCollection(MatrixSeries series) {
/*  86 */     if (series != null) {
/*  87 */       this.seriesList.add(series);
/*  88 */       series.addChangeListener((SeriesChangeListener)this);
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
/*     */   public int getItemCount(int seriesIndex) {
/* 101 */     return getSeries(seriesIndex).getItemCount();
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
/*     */   public MatrixSeries getSeries(int seriesIndex) {
/* 113 */     if (seriesIndex < 0 || seriesIndex > getSeriesCount()) {
/* 114 */       throw new IllegalArgumentException("Index outside valid range.");
/*     */     }
/* 116 */     MatrixSeries series = this.seriesList.get(seriesIndex);
/* 117 */     return series;
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
/* 128 */     return this.seriesList.size();
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
/*     */   public Comparable getSeriesKey(int seriesIndex) {
/* 141 */     return getSeries(seriesIndex).getKey();
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
/*     */   public Number getX(int seriesIndex, int itemIndex) {
/* 158 */     MatrixSeries series = this.seriesList.get(seriesIndex);
/* 159 */     int x = series.getItemColumn(itemIndex);
/*     */     
/* 161 */     return new Integer(x);
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
/*     */   public Number getY(int seriesIndex, int itemIndex) {
/* 178 */     MatrixSeries series = this.seriesList.get(seriesIndex);
/* 179 */     int y = series.getItemRow(itemIndex);
/*     */     
/* 181 */     return new Integer(y);
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
/*     */   public Number getZ(int seriesIndex, int itemIndex) {
/* 198 */     MatrixSeries series = this.seriesList.get(seriesIndex);
/* 199 */     Number z = series.getItem(itemIndex);
/* 200 */     return z;
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
/*     */   public void addSeries(MatrixSeries series) {
/* 213 */     ParamChecks.nullNotPermitted(series, "series");
/*     */ 
/*     */ 
/*     */     
/* 217 */     this.seriesList.add(series);
/* 218 */     series.addChangeListener((SeriesChangeListener)this);
/* 219 */     fireDatasetChanged();
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
/*     */   public boolean equals(Object obj) {
/* 232 */     if (obj == null) {
/* 233 */       return false;
/*     */     }
/*     */     
/* 236 */     if (obj == this) {
/* 237 */       return true;
/*     */     }
/*     */     
/* 240 */     if (obj instanceof MatrixSeriesCollection) {
/* 241 */       MatrixSeriesCollection c = (MatrixSeriesCollection)obj;
/*     */       
/* 243 */       return ObjectUtilities.equal(this.seriesList, c.seriesList);
/*     */     } 
/*     */     
/* 246 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 256 */     return (this.seriesList != null) ? this.seriesList.hashCode() : 0;
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
/* 268 */     MatrixSeriesCollection clone = (MatrixSeriesCollection)super.clone();
/* 269 */     clone.seriesList = (List)ObjectUtilities.deepClone(this.seriesList);
/* 270 */     return clone;
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
/*     */   public void removeAllSeries() {
/* 282 */     for (int i = 0; i < this.seriesList.size(); i++) {
/* 283 */       MatrixSeries series = this.seriesList.get(i);
/* 284 */       series.removeChangeListener((SeriesChangeListener)this);
/*     */     } 
/*     */ 
/*     */     
/* 288 */     this.seriesList.clear();
/* 289 */     fireDatasetChanged();
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
/*     */   public void removeSeries(MatrixSeries series) {
/* 302 */     ParamChecks.nullNotPermitted(series, "series");
/* 303 */     if (this.seriesList.contains(series)) {
/* 304 */       series.removeChangeListener((SeriesChangeListener)this);
/* 305 */       this.seriesList.remove(series);
/* 306 */       fireDatasetChanged();
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
/*     */   public void removeSeries(int seriesIndex) {
/* 320 */     if (seriesIndex < 0 || seriesIndex > getSeriesCount()) {
/* 321 */       throw new IllegalArgumentException("Index outside valid range.");
/*     */     }
/*     */ 
/*     */     
/* 325 */     MatrixSeries series = this.seriesList.get(seriesIndex);
/* 326 */     series.removeChangeListener((SeriesChangeListener)this);
/* 327 */     this.seriesList.remove(seriesIndex);
/* 328 */     fireDatasetChanged();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/MatrixSeriesCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */