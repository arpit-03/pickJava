/*     */ package org.jfree.data.gantt;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.jfree.data.UnknownKeyException;
/*     */ import org.jfree.data.general.AbstractDataset;
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
/*     */ public class SlidingGanttCategoryDataset
/*     */   extends AbstractDataset
/*     */   implements GanttCategoryDataset
/*     */ {
/*     */   private GanttCategoryDataset underlying;
/*     */   private int firstCategoryIndex;
/*     */   private int maximumCategoryCount;
/*     */   
/*     */   public SlidingGanttCategoryDataset(GanttCategoryDataset underlying, int firstColumn, int maxColumns) {
/*  82 */     this.underlying = underlying;
/*  83 */     this.firstCategoryIndex = firstColumn;
/*  84 */     this.maximumCategoryCount = maxColumns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GanttCategoryDataset getUnderlyingDataset() {
/*  93 */     return this.underlying;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFirstCategoryIndex() {
/* 104 */     return this.firstCategoryIndex;
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
/*     */   public void setFirstCategoryIndex(int first) {
/* 117 */     if (first < 0 || first >= this.underlying.getColumnCount()) {
/* 118 */       throw new IllegalArgumentException("Invalid index.");
/*     */     }
/* 120 */     this.firstCategoryIndex = first;
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
/*     */   public int getMaximumCategoryCount() {
/* 132 */     return this.maximumCategoryCount;
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
/*     */   public void setMaximumCategoryCount(int max) {
/* 144 */     if (max < 0) {
/* 145 */       throw new IllegalArgumentException("Requires 'max' >= 0.");
/*     */     }
/* 147 */     this.maximumCategoryCount = max;
/* 148 */     fireDatasetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int lastCategoryIndex() {
/* 157 */     if (this.maximumCategoryCount == 0) {
/* 158 */       return -1;
/*     */     }
/* 160 */     return Math.min(this.firstCategoryIndex + this.maximumCategoryCount, this.underlying
/* 161 */         .getColumnCount()) - 1;
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
/*     */   public int getColumnIndex(Comparable key) {
/* 173 */     int index = this.underlying.getColumnIndex(key);
/* 174 */     if (index >= this.firstCategoryIndex && index <= lastCategoryIndex()) {
/* 175 */       return index - this.firstCategoryIndex;
/*     */     }
/* 177 */     return -1;
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
/*     */   public Comparable getColumnKey(int column) {
/* 191 */     return this.underlying.getColumnKey(column + this.firstCategoryIndex);
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
/*     */   public List getColumnKeys() {
/* 203 */     List<Comparable> result = new ArrayList();
/* 204 */     int last = lastCategoryIndex();
/* 205 */     for (int i = this.firstCategoryIndex; i < last; i++) {
/* 206 */       result.add(this.underlying.getColumnKey(i));
/*     */     }
/* 208 */     return Collections.unmodifiableList(result);
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
/*     */   public int getRowIndex(Comparable key) {
/* 220 */     return this.underlying.getRowIndex(key);
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
/*     */   public Comparable getRowKey(int row) {
/* 234 */     return this.underlying.getRowKey(row);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getRowKeys() {
/* 244 */     return this.underlying.getRowKeys();
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
/*     */   public Number getValue(Comparable rowKey, Comparable columnKey) {
/* 259 */     int r = getRowIndex(rowKey);
/* 260 */     int c = getColumnIndex(columnKey);
/* 261 */     if (c != -1) {
/* 262 */       return this.underlying.getValue(r, c + this.firstCategoryIndex);
/*     */     }
/*     */     
/* 265 */     throw new UnknownKeyException("Unknown columnKey: " + columnKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 276 */     int last = lastCategoryIndex();
/* 277 */     if (last == -1) {
/* 278 */       return 0;
/*     */     }
/*     */     
/* 281 */     return Math.max(last - this.firstCategoryIndex + 1, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/* 292 */     return this.underlying.getRowCount();
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
/* 305 */     return this.underlying.getValue(row, column + this.firstCategoryIndex);
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
/* 318 */     int r = getRowIndex(rowKey);
/* 319 */     int c = getColumnIndex(columnKey);
/* 320 */     if (c != -1) {
/* 321 */       return this.underlying.getPercentComplete(r, c + this.firstCategoryIndex);
/*     */     }
/*     */ 
/*     */     
/* 325 */     throw new UnknownKeyException("Unknown columnKey: " + columnKey);
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
/*     */   public Number getPercentComplete(Comparable rowKey, Comparable columnKey, int subinterval) {
/* 343 */     int r = getRowIndex(rowKey);
/* 344 */     int c = getColumnIndex(columnKey);
/* 345 */     if (c != -1) {
/* 346 */       return this.underlying.getPercentComplete(r, c + this.firstCategoryIndex, subinterval);
/*     */     }
/*     */ 
/*     */     
/* 350 */     throw new UnknownKeyException("Unknown columnKey: " + columnKey);
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
/*     */   public Number getEndValue(Comparable rowKey, Comparable columnKey, int subinterval) {
/* 368 */     int r = getRowIndex(rowKey);
/* 369 */     int c = getColumnIndex(columnKey);
/* 370 */     if (c != -1) {
/* 371 */       return this.underlying.getEndValue(r, c + this.firstCategoryIndex, subinterval);
/*     */     }
/*     */ 
/*     */     
/* 375 */     throw new UnknownKeyException("Unknown columnKey: " + columnKey);
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
/*     */   public Number getEndValue(int row, int column, int subinterval) {
/* 392 */     return this.underlying.getEndValue(row, column + this.firstCategoryIndex, subinterval);
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
/*     */   public Number getPercentComplete(int series, int category) {
/* 406 */     return this.underlying.getPercentComplete(series, category + this.firstCategoryIndex);
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
/*     */   public Number getPercentComplete(int row, int column, int subinterval) {
/* 423 */     return this.underlying.getPercentComplete(row, column + this.firstCategoryIndex, subinterval);
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
/*     */   public Number getStartValue(Comparable rowKey, Comparable columnKey, int subinterval) {
/* 441 */     int r = getRowIndex(rowKey);
/* 442 */     int c = getColumnIndex(columnKey);
/* 443 */     if (c != -1) {
/* 444 */       return this.underlying.getStartValue(r, c + this.firstCategoryIndex, subinterval);
/*     */     }
/*     */ 
/*     */     
/* 448 */     throw new UnknownKeyException("Unknown columnKey: " + columnKey);
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
/*     */   public Number getStartValue(int row, int column, int subinterval) {
/* 465 */     return this.underlying.getStartValue(row, column + this.firstCategoryIndex, subinterval);
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
/*     */   public int getSubIntervalCount(Comparable rowKey, Comparable columnKey) {
/* 481 */     int r = getRowIndex(rowKey);
/* 482 */     int c = getColumnIndex(columnKey);
/* 483 */     if (c != -1) {
/* 484 */       return this.underlying.getSubIntervalCount(r, c + this.firstCategoryIndex);
/*     */     }
/*     */ 
/*     */     
/* 488 */     throw new UnknownKeyException("Unknown columnKey: " + columnKey);
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
/*     */   public int getSubIntervalCount(int row, int column) {
/* 504 */     return this.underlying.getSubIntervalCount(row, column + this.firstCategoryIndex);
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
/*     */   public Number getStartValue(Comparable rowKey, Comparable columnKey) {
/* 520 */     int r = getRowIndex(rowKey);
/* 521 */     int c = getColumnIndex(columnKey);
/* 522 */     if (c != -1) {
/* 523 */       return this.underlying.getStartValue(r, c + this.firstCategoryIndex);
/*     */     }
/*     */ 
/*     */     
/* 527 */     throw new UnknownKeyException("Unknown columnKey: " + columnKey);
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
/*     */   public Number getStartValue(int row, int column) {
/* 543 */     return this.underlying.getStartValue(row, column + this.firstCategoryIndex);
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
/*     */   public Number getEndValue(Comparable rowKey, Comparable columnKey) {
/* 559 */     int r = getRowIndex(rowKey);
/* 560 */     int c = getColumnIndex(columnKey);
/* 561 */     if (c != -1) {
/* 562 */       return this.underlying.getEndValue(r, c + this.firstCategoryIndex);
/*     */     }
/*     */     
/* 565 */     throw new UnknownKeyException("Unknown columnKey: " + columnKey);
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
/*     */   public Number getEndValue(int series, int category) {
/* 579 */     return this.underlying.getEndValue(series, category + this.firstCategoryIndex);
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
/*     */   public boolean equals(Object obj) {
/* 593 */     if (obj == this) {
/* 594 */       return true;
/*     */     }
/* 596 */     if (!(obj instanceof SlidingGanttCategoryDataset)) {
/* 597 */       return false;
/*     */     }
/* 599 */     SlidingGanttCategoryDataset that = (SlidingGanttCategoryDataset)obj;
/* 600 */     if (this.firstCategoryIndex != that.firstCategoryIndex) {
/* 601 */       return false;
/*     */     }
/* 603 */     if (this.maximumCategoryCount != that.maximumCategoryCount) {
/* 604 */       return false;
/*     */     }
/* 606 */     if (!this.underlying.equals(that.underlying)) {
/* 607 */       return false;
/*     */     }
/* 609 */     return true;
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
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 629 */     SlidingGanttCategoryDataset clone = (SlidingGanttCategoryDataset)super.clone();
/* 630 */     if (this.underlying instanceof PublicCloneable) {
/* 631 */       PublicCloneable pc = (PublicCloneable)this.underlying;
/* 632 */       clone.underlying = (GanttCategoryDataset)pc.clone();
/*     */     } 
/* 634 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/gantt/SlidingGanttCategoryDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */