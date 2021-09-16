/*     */ package org.jfree.data;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.general.Series;
/*     */ import org.jfree.data.general.SeriesException;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComparableObjectSeries
/*     */   extends Series
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   protected List data;
/*  67 */   private int maximumItemCount = Integer.MAX_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean autoSort;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean allowDuplicateXValues;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComparableObjectSeries(Comparable key) {
/*  83 */     this(key, true, true);
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
/*     */   public ComparableObjectSeries(Comparable key, boolean autoSort, boolean allowDuplicateXValues) {
/*  98 */     super(key);
/*  99 */     this.data = new ArrayList();
/* 100 */     this.autoSort = autoSort;
/* 101 */     this.allowDuplicateXValues = allowDuplicateXValues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAutoSort() {
/* 112 */     return this.autoSort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAllowDuplicateXValues() {
/* 122 */     return this.allowDuplicateXValues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemCount() {
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
/*     */   public int getMaximumItemCount() {
/* 143 */     return this.maximumItemCount;
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
/*     */   public void setMaximumItemCount(int maximum) {
/* 161 */     this.maximumItemCount = maximum;
/* 162 */     boolean dataRemoved = false;
/* 163 */     while (this.data.size() > maximum) {
/* 164 */       this.data.remove(0);
/* 165 */       dataRemoved = true;
/*     */     } 
/* 167 */     if (dataRemoved) {
/* 168 */       fireSeriesChanged();
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
/*     */   protected void add(Comparable x, Object y) {
/* 184 */     add(x, y, true);
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
/*     */   protected void add(Comparable x, Object y, boolean notify) {
/* 202 */     ComparableObjectItem item = new ComparableObjectItem(x, y);
/* 203 */     add(item, notify);
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
/*     */   protected void add(ComparableObjectItem item, boolean notify) {
/* 217 */     ParamChecks.nullNotPermitted(item, "item");
/* 218 */     if (this.autoSort) {
/* 219 */       int index = Collections.binarySearch(this.data, item);
/* 220 */       if (index < 0) {
/* 221 */         this.data.add(-index - 1, item);
/*     */       
/*     */       }
/* 224 */       else if (this.allowDuplicateXValues) {
/*     */         
/* 226 */         int size = this.data.size();
/* 227 */         while (index < size && item
/* 228 */           .compareTo(this.data.get(index)) == 0) {
/* 229 */           index++;
/*     */         }
/* 231 */         if (index < this.data.size()) {
/* 232 */           this.data.add(index, item);
/*     */         } else {
/*     */           
/* 235 */           this.data.add(item);
/*     */         } 
/*     */       } else {
/*     */         
/* 239 */         throw new SeriesException("X-value already exists.");
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 244 */       if (!this.allowDuplicateXValues) {
/*     */ 
/*     */         
/* 247 */         int index = indexOf(item.getComparable());
/* 248 */         if (index >= 0) {
/* 249 */           throw new SeriesException("X-value already exists.");
/*     */         }
/*     */       } 
/* 252 */       this.data.add(item);
/*     */     } 
/* 254 */     if (getItemCount() > this.maximumItemCount) {
/* 255 */       this.data.remove(0);
/*     */     }
/* 257 */     if (notify) {
/* 258 */       fireSeriesChanged();
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
/*     */   public int indexOf(Comparable x) {
/* 273 */     if (this.autoSort) {
/* 274 */       return Collections.binarySearch(this.data, new ComparableObjectItem(x, null));
/*     */     }
/*     */ 
/*     */     
/* 278 */     for (int i = 0; i < this.data.size(); i++) {
/*     */       
/* 280 */       ComparableObjectItem item = this.data.get(i);
/* 281 */       if (item.getComparable().equals(x)) {
/* 282 */         return i;
/*     */       }
/*     */     } 
/* 285 */     return -1;
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
/*     */   protected void update(Comparable x, Object y) {
/* 299 */     int index = indexOf(x);
/* 300 */     if (index < 0) {
/* 301 */       throw new SeriesException("No observation for x = " + x);
/*     */     }
/*     */     
/* 304 */     ComparableObjectItem item = getDataItem(index);
/* 305 */     item.setObject(y);
/* 306 */     fireSeriesChanged();
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
/*     */   protected void updateByIndex(int index, Object y) {
/* 318 */     ComparableObjectItem item = getDataItem(index);
/* 319 */     item.setObject(y);
/* 320 */     fireSeriesChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ComparableObjectItem getDataItem(int index) {
/* 331 */     return this.data.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void delete(int start, int end) {
/* 342 */     for (int i = start; i <= end; i++) {
/* 343 */       this.data.remove(start);
/*     */     }
/* 345 */     fireSeriesChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 354 */     if (this.data.size() > 0) {
/* 355 */       this.data.clear();
/* 356 */       fireSeriesChanged();
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
/*     */   protected ComparableObjectItem remove(int index) {
/* 369 */     ComparableObjectItem result = this.data.remove(index);
/*     */     
/* 371 */     fireSeriesChanged();
/* 372 */     return result;
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
/*     */   public ComparableObjectItem remove(Comparable x) {
/* 384 */     return remove(indexOf(x));
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
/* 397 */     if (obj == this) {
/* 398 */       return true;
/*     */     }
/* 400 */     if (!(obj instanceof ComparableObjectSeries)) {
/* 401 */       return false;
/*     */     }
/* 403 */     if (!super.equals(obj)) {
/* 404 */       return false;
/*     */     }
/* 406 */     ComparableObjectSeries that = (ComparableObjectSeries)obj;
/* 407 */     if (this.maximumItemCount != that.maximumItemCount) {
/* 408 */       return false;
/*     */     }
/* 410 */     if (this.autoSort != that.autoSort) {
/* 411 */       return false;
/*     */     }
/* 413 */     if (this.allowDuplicateXValues != that.allowDuplicateXValues) {
/* 414 */       return false;
/*     */     }
/* 416 */     if (!ObjectUtilities.equal(this.data, that.data)) {
/* 417 */       return false;
/*     */     }
/* 419 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 429 */     int result = super.hashCode();
/*     */ 
/*     */     
/* 432 */     int count = getItemCount();
/* 433 */     if (count > 0) {
/* 434 */       ComparableObjectItem item = getDataItem(0);
/* 435 */       result = 29 * result + item.hashCode();
/*     */     } 
/* 437 */     if (count > 1) {
/* 438 */       ComparableObjectItem item = getDataItem(count - 1);
/* 439 */       result = 29 * result + item.hashCode();
/*     */     } 
/* 441 */     if (count > 2) {
/* 442 */       ComparableObjectItem item = getDataItem(count / 2);
/* 443 */       result = 29 * result + item.hashCode();
/*     */     } 
/* 445 */     result = 29 * result + this.maximumItemCount;
/* 446 */     result = 29 * result + (this.autoSort ? 1 : 0);
/* 447 */     result = 29 * result + (this.allowDuplicateXValues ? 1 : 0);
/* 448 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/ComparableObjectSeries.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */