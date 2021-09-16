/*     */ package org.jfree.data.gantt;
/*     */ 
/*     */ import java.util.Date;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.general.DatasetChangeEvent;
/*     */ import org.jfree.data.general.DatasetChangeListener;
/*     */ import org.jfree.data.time.TimePeriod;
/*     */ import org.jfree.data.xy.AbstractXYDataset;
/*     */ import org.jfree.data.xy.IntervalXYDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XYTaskDataset
/*     */   extends AbstractXYDataset
/*     */   implements IntervalXYDataset, DatasetChangeListener
/*     */ {
/*     */   private TaskSeriesCollection underlying;
/*     */   private double seriesWidth;
/*     */   private boolean transposed;
/*     */   
/*     */   public XYTaskDataset(TaskSeriesCollection tasks) {
/*  83 */     ParamChecks.nullNotPermitted(tasks, "tasks");
/*  84 */     this.underlying = tasks;
/*  85 */     this.seriesWidth = 0.8D;
/*  86 */     this.underlying.addChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskSeriesCollection getTasks() {
/*  96 */     return this.underlying;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSeriesWidth() {
/* 107 */     return this.seriesWidth;
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
/*     */   public void setSeriesWidth(double w) {
/* 119 */     if (w <= 0.0D) {
/* 120 */       throw new IllegalArgumentException("Requires 'w' > 0.0.");
/*     */     }
/* 122 */     this.seriesWidth = w;
/* 123 */     fireDatasetChanged();
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
/*     */   public boolean isTransposed() {
/* 138 */     return this.transposed;
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
/*     */   public void setTransposed(boolean transposed) {
/* 150 */     this.transposed = transposed;
/* 151 */     fireDatasetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeriesCount() {
/* 161 */     return this.underlying.getSeriesCount();
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
/* 173 */     return this.underlying.getSeriesKey(series);
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
/* 185 */     return this.underlying.getSeries(series).getItemCount();
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
/* 198 */     if (!this.transposed) {
/* 199 */       return getSeriesValue(series);
/*     */     }
/*     */     
/* 202 */     return getItemValue(series, item);
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
/*     */   public double getStartXValue(int series, int item) {
/* 218 */     if (!this.transposed) {
/* 219 */       return getSeriesStartValue(series);
/*     */     }
/*     */     
/* 222 */     return getItemStartValue(series, item);
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
/*     */   public double getEndXValue(int series, int item) {
/* 238 */     if (!this.transposed) {
/* 239 */       return getSeriesEndValue(series);
/*     */     }
/*     */     
/* 242 */     return getItemEndValue(series, item);
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
/*     */   public Number getX(int series, int item) {
/* 256 */     return new Double(getXValue(series, item));
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
/*     */   public Number getStartX(int series, int item) {
/* 271 */     return new Double(getStartXValue(series, item));
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
/*     */   public Number getEndX(int series, int item) {
/* 286 */     return new Double(getEndXValue(series, item));
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
/* 299 */     if (!this.transposed) {
/* 300 */       return getItemValue(series, item);
/*     */     }
/*     */     
/* 303 */     return getSeriesValue(series);
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
/*     */   public double getStartYValue(int series, int item) {
/* 318 */     if (!this.transposed) {
/* 319 */       return getItemStartValue(series, item);
/*     */     }
/*     */     
/* 322 */     return getSeriesStartValue(series);
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
/*     */   public double getEndYValue(int series, int item) {
/* 337 */     if (!this.transposed) {
/* 338 */       return getItemEndValue(series, item);
/*     */     }
/*     */     
/* 341 */     return getSeriesEndValue(series);
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
/*     */   public Number getY(int series, int item) {
/* 357 */     return new Double(getYValue(series, item));
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
/*     */   public Number getStartY(int series, int item) {
/* 371 */     return new Double(getStartYValue(series, item));
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
/*     */   public Number getEndY(int series, int item) {
/* 385 */     return new Double(getEndYValue(series, item));
/*     */   }
/*     */   
/*     */   private double getSeriesValue(int series) {
/* 389 */     return series;
/*     */   }
/*     */   
/*     */   private double getSeriesStartValue(int series) {
/* 393 */     return series - this.seriesWidth / 2.0D;
/*     */   }
/*     */   
/*     */   private double getSeriesEndValue(int series) {
/* 397 */     return series + this.seriesWidth / 2.0D;
/*     */   }
/*     */   
/*     */   private double getItemValue(int series, int item) {
/* 401 */     TaskSeries s = this.underlying.getSeries(series);
/* 402 */     Task t = s.get(item);
/* 403 */     TimePeriod duration = t.getDuration();
/* 404 */     Date start = duration.getStart();
/* 405 */     Date end = duration.getEnd();
/* 406 */     return (start.getTime() + end.getTime()) / 2.0D;
/*     */   }
/*     */   
/*     */   private double getItemStartValue(int series, int item) {
/* 410 */     TaskSeries s = this.underlying.getSeries(series);
/* 411 */     Task t = s.get(item);
/* 412 */     TimePeriod duration = t.getDuration();
/* 413 */     Date start = duration.getStart();
/* 414 */     return start.getTime();
/*     */   }
/*     */   
/*     */   private double getItemEndValue(int series, int item) {
/* 418 */     TaskSeries s = this.underlying.getSeries(series);
/* 419 */     Task t = s.get(item);
/* 420 */     TimePeriod duration = t.getDuration();
/* 421 */     Date end = duration.getEnd();
/* 422 */     return end.getTime();
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
/*     */   public void datasetChanged(DatasetChangeEvent event) {
/* 434 */     fireDatasetChanged();
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
/* 446 */     if (obj == this) {
/* 447 */       return true;
/*     */     }
/* 449 */     if (!(obj instanceof XYTaskDataset)) {
/* 450 */       return false;
/*     */     }
/* 452 */     XYTaskDataset that = (XYTaskDataset)obj;
/* 453 */     if (this.seriesWidth != that.seriesWidth) {
/* 454 */       return false;
/*     */     }
/* 456 */     if (this.transposed != that.transposed) {
/* 457 */       return false;
/*     */     }
/* 459 */     if (!this.underlying.equals(that.underlying)) {
/* 460 */       return false;
/*     */     }
/* 462 */     return true;
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
/* 474 */     XYTaskDataset clone = (XYTaskDataset)super.clone();
/* 475 */     clone.underlying = (TaskSeriesCollection)this.underlying.clone();
/* 476 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/gantt/XYTaskDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */