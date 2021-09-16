/*     */ package org.jfree.data.gantt;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.general.AbstractSeriesDataset;
/*     */ import org.jfree.data.general.SeriesChangeEvent;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import org.jfree.data.time.TimePeriod;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TaskSeriesCollection
/*     */   extends AbstractSeriesDataset
/*     */   implements GanttCategoryDataset, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2065799050738449903L;
/*  89 */   private List keys = new ArrayList();
/*  90 */   private List data = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TaskSeries getSeries(Comparable key) {
/* 103 */     if (key == null) {
/* 104 */       throw new NullPointerException("Null 'key' argument.");
/*     */     }
/* 106 */     TaskSeries result = null;
/* 107 */     int index = getRowIndex(key);
/* 108 */     if (index >= 0) {
/* 109 */       result = getSeries(index);
/*     */     }
/* 111 */     return result;
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
/*     */   public TaskSeries getSeries(int series) {
/* 124 */     if (series < 0 || series >= getSeriesCount()) {
/* 125 */       throw new IllegalArgumentException("Series index out of bounds");
/*     */     }
/* 127 */     return this.data.get(series);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeriesCount() {
/* 137 */     return getRowCount();
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
/* 149 */     TaskSeries ts = this.data.get(series);
/* 150 */     return ts.getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/* 160 */     return this.data.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getRowKeys() {
/* 170 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 180 */     return this.keys.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getColumnKeys() {
/* 190 */     return this.keys;
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
/*     */   public Comparable getColumnKey(int index) {
/* 202 */     return this.keys.get(index);
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
/*     */   public int getColumnIndex(Comparable columnKey) {
/* 214 */     ParamChecks.nullNotPermitted(columnKey, "columnKey");
/* 215 */     return this.keys.indexOf(columnKey);
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
/*     */   public int getRowIndex(Comparable rowKey) {
/* 227 */     int result = -1;
/* 228 */     int count = this.data.size();
/* 229 */     for (int i = 0; i < count; i++) {
/* 230 */       TaskSeries s = this.data.get(i);
/* 231 */       if (s.getKey().equals(rowKey)) {
/* 232 */         result = i;
/*     */         break;
/*     */       } 
/*     */     } 
/* 236 */     return result;
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
/*     */   public Comparable getRowKey(int index) {
/* 248 */     TaskSeries series = this.data.get(index);
/* 249 */     return series.getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(TaskSeries series) {
/* 260 */     ParamChecks.nullNotPermitted(series, "series");
/* 261 */     this.data.add(series);
/* 262 */     series.addChangeListener((SeriesChangeListener)this);
/*     */ 
/*     */     
/* 265 */     Iterator<Task> iterator = series.getTasks().iterator();
/* 266 */     while (iterator.hasNext()) {
/* 267 */       Task task = iterator.next();
/* 268 */       String key = task.getDescription();
/* 269 */       int index = this.keys.indexOf(key);
/* 270 */       if (index < 0) {
/* 271 */         this.keys.add(key);
/*     */       }
/*     */     } 
/* 274 */     fireDatasetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(TaskSeries series) {
/* 285 */     ParamChecks.nullNotPermitted(series, "series");
/* 286 */     if (this.data.contains(series)) {
/* 287 */       series.removeChangeListener((SeriesChangeListener)this);
/* 288 */       this.data.remove(series);
/* 289 */       fireDatasetChanged();
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
/*     */   public void remove(int series) {
/* 301 */     if (series < 0 || series >= getSeriesCount()) {
/* 302 */       throw new IllegalArgumentException("TaskSeriesCollection.remove(): index outside valid range.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 307 */     TaskSeries ts = this.data.get(series);
/* 308 */     ts.removeChangeListener((SeriesChangeListener)this);
/* 309 */     this.data.remove(series);
/* 310 */     fireDatasetChanged();
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
/*     */   public void removeAll() {
/* 323 */     Iterator<TaskSeries> iterator = this.data.iterator();
/* 324 */     while (iterator.hasNext()) {
/* 325 */       TaskSeries series = iterator.next();
/* 326 */       series.removeChangeListener((SeriesChangeListener)this);
/*     */     } 
/*     */ 
/*     */     
/* 330 */     this.data.clear();
/* 331 */     fireDatasetChanged();
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
/*     */   public Number getValue(Comparable rowKey, Comparable columnKey) {
/* 345 */     return getStartValue(rowKey, columnKey);
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
/*     */   public Number getValue(int row, int column) {
/* 358 */     return getStartValue(row, column);
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
/*     */   public Number getStartValue(Comparable rowKey, Comparable columnKey) {
/* 372 */     Number result = null;
/* 373 */     int row = getRowIndex(rowKey);
/* 374 */     TaskSeries series = this.data.get(row);
/* 375 */     Task task = series.get(columnKey.toString());
/* 376 */     if (task != null) {
/* 377 */       TimePeriod duration = task.getDuration();
/* 378 */       if (duration != null) {
/* 379 */         result = new Long(duration.getStart().getTime());
/*     */       }
/*     */     } 
/* 382 */     return result;
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
/*     */   public Number getStartValue(int row, int column) {
/* 395 */     Comparable rowKey = getRowKey(row);
/* 396 */     Comparable columnKey = getColumnKey(column);
/* 397 */     return getStartValue(rowKey, columnKey);
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
/*     */   public Number getEndValue(Comparable rowKey, Comparable columnKey) {
/* 411 */     Number result = null;
/* 412 */     int row = getRowIndex(rowKey);
/* 413 */     TaskSeries series = this.data.get(row);
/* 414 */     Task task = series.get(columnKey.toString());
/* 415 */     if (task != null) {
/* 416 */       TimePeriod duration = task.getDuration();
/* 417 */       if (duration != null) {
/* 418 */         result = new Long(duration.getEnd().getTime());
/*     */       }
/*     */     } 
/* 421 */     return result;
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
/*     */   public Number getEndValue(int row, int column) {
/* 434 */     Comparable rowKey = getRowKey(row);
/* 435 */     Comparable columnKey = getColumnKey(column);
/* 436 */     return getEndValue(rowKey, columnKey);
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
/*     */   public Number getPercentComplete(int row, int column) {
/* 449 */     Comparable rowKey = getRowKey(row);
/* 450 */     Comparable columnKey = getColumnKey(column);
/* 451 */     return getPercentComplete(rowKey, columnKey);
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
/*     */   public Number getPercentComplete(Comparable rowKey, Comparable columnKey) {
/* 464 */     Number result = null;
/* 465 */     int row = getRowIndex(rowKey);
/* 466 */     TaskSeries series = this.data.get(row);
/* 467 */     Task task = series.get(columnKey.toString());
/* 468 */     if (task != null) {
/* 469 */       result = task.getPercentComplete();
/*     */     }
/* 471 */     return result;
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
/*     */   public int getSubIntervalCount(int row, int column) {
/* 484 */     Comparable rowKey = getRowKey(row);
/* 485 */     Comparable columnKey = getColumnKey(column);
/* 486 */     return getSubIntervalCount(rowKey, columnKey);
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
/*     */   public int getSubIntervalCount(Comparable rowKey, Comparable columnKey) {
/* 499 */     int result = 0;
/* 500 */     int row = getRowIndex(rowKey);
/* 501 */     TaskSeries series = this.data.get(row);
/* 502 */     Task task = series.get(columnKey.toString());
/* 503 */     if (task != null) {
/* 504 */       result = task.getSubtaskCount();
/*     */     }
/* 506 */     return result;
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
/*     */   public Number getStartValue(int row, int column, int subinterval) {
/* 520 */     Comparable rowKey = getRowKey(row);
/* 521 */     Comparable columnKey = getColumnKey(column);
/* 522 */     return getStartValue(rowKey, columnKey, subinterval);
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
/*     */   public Number getStartValue(Comparable rowKey, Comparable columnKey, int subinterval) {
/* 537 */     Number result = null;
/* 538 */     int row = getRowIndex(rowKey);
/* 539 */     TaskSeries series = this.data.get(row);
/* 540 */     Task task = series.get(columnKey.toString());
/* 541 */     if (task != null) {
/* 542 */       Task sub = task.getSubtask(subinterval);
/* 543 */       if (sub != null) {
/* 544 */         TimePeriod duration = sub.getDuration();
/* 545 */         result = new Long(duration.getStart().getTime());
/*     */       } 
/*     */     } 
/* 548 */     return result;
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
/*     */   public Number getEndValue(int row, int column, int subinterval) {
/* 562 */     Comparable rowKey = getRowKey(row);
/* 563 */     Comparable columnKey = getColumnKey(column);
/* 564 */     return getEndValue(rowKey, columnKey, subinterval);
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
/*     */   public Number getEndValue(Comparable rowKey, Comparable columnKey, int subinterval) {
/* 579 */     Number result = null;
/* 580 */     int row = getRowIndex(rowKey);
/* 581 */     TaskSeries series = this.data.get(row);
/* 582 */     Task task = series.get(columnKey.toString());
/* 583 */     if (task != null) {
/* 584 */       Task sub = task.getSubtask(subinterval);
/* 585 */       if (sub != null) {
/* 586 */         TimePeriod duration = sub.getDuration();
/* 587 */         result = new Long(duration.getEnd().getTime());
/*     */       } 
/*     */     } 
/* 590 */     return result;
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
/*     */   public Number getPercentComplete(int row, int column, int subinterval) {
/* 604 */     Comparable rowKey = getRowKey(row);
/* 605 */     Comparable columnKey = getColumnKey(column);
/* 606 */     return getPercentComplete(rowKey, columnKey, subinterval);
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
/*     */   public Number getPercentComplete(Comparable rowKey, Comparable columnKey, int subinterval) {
/* 621 */     Number result = null;
/* 622 */     int row = getRowIndex(rowKey);
/* 623 */     TaskSeries series = this.data.get(row);
/* 624 */     Task task = series.get(columnKey.toString());
/* 625 */     if (task != null) {
/* 626 */       Task sub = task.getSubtask(subinterval);
/* 627 */       if (sub != null) {
/* 628 */         result = sub.getPercentComplete();
/*     */       }
/*     */     } 
/* 631 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void seriesChanged(SeriesChangeEvent event) {
/* 641 */     refreshKeys();
/* 642 */     fireDatasetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void refreshKeys() {
/* 650 */     this.keys.clear();
/* 651 */     for (int i = 0; i < getSeriesCount(); i++) {
/* 652 */       TaskSeries series = this.data.get(i);
/*     */       
/* 654 */       Iterator<Task> iterator = series.getTasks().iterator();
/* 655 */       while (iterator.hasNext()) {
/* 656 */         Task task = iterator.next();
/* 657 */         String key = task.getDescription();
/* 658 */         int index = this.keys.indexOf(key);
/* 659 */         if (index < 0) {
/* 660 */           this.keys.add(key);
/*     */         }
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
/*     */   public boolean equals(Object obj) {
/* 676 */     if (obj == this) {
/* 677 */       return true;
/*     */     }
/* 679 */     if (!(obj instanceof TaskSeriesCollection)) {
/* 680 */       return false;
/*     */     }
/* 682 */     TaskSeriesCollection that = (TaskSeriesCollection)obj;
/* 683 */     if (!ObjectUtilities.equal(this.data, that.data)) {
/* 684 */       return false;
/*     */     }
/* 686 */     return true;
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
/* 699 */     TaskSeriesCollection clone = (TaskSeriesCollection)super.clone();
/* 700 */     clone.data = (List)ObjectUtilities.deepClone(this.data);
/* 701 */     clone.keys = new ArrayList(this.keys);
/* 702 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/gantt/TaskSeriesCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */