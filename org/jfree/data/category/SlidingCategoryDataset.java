/*     */ package org.jfree.data.category;
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
/*     */ 
/*     */ public class SlidingCategoryDataset
/*     */   extends AbstractDataset
/*     */   implements CategoryDataset
/*     */ {
/*     */   private CategoryDataset underlying;
/*     */   private int firstCategoryIndex;
/*     */   private int maximumCategoryCount;
/*     */   
/*     */   public SlidingCategoryDataset(CategoryDataset underlying, int firstColumn, int maxColumns) {
/*  83 */     this.underlying = underlying;
/*  84 */     this.firstCategoryIndex = firstColumn;
/*  85 */     this.maximumCategoryCount = maxColumns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CategoryDataset getUnderlyingDataset() {
/*  94 */     return this.underlying;
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
/* 105 */     return this.firstCategoryIndex;
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
/* 118 */     if (first < 0 || first >= this.underlying.getColumnCount()) {
/* 119 */       throw new IllegalArgumentException("Invalid index.");
/*     */     }
/* 121 */     this.firstCategoryIndex = first;
/* 122 */     fireDatasetChanged();
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
/* 133 */     return this.maximumCategoryCount;
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
/* 145 */     if (max < 0) {
/* 146 */       throw new IllegalArgumentException("Requires 'max' >= 0.");
/*     */     }
/* 148 */     this.maximumCategoryCount = max;
/* 149 */     fireDatasetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int lastCategoryIndex() {
/* 158 */     if (this.maximumCategoryCount == 0) {
/* 159 */       return -1;
/*     */     }
/* 161 */     return Math.min(this.firstCategoryIndex + this.maximumCategoryCount, this.underlying
/* 162 */         .getColumnCount()) - 1;
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
/* 174 */     int index = this.underlying.getColumnIndex(key);
/* 175 */     if (index >= this.firstCategoryIndex && index <= lastCategoryIndex()) {
/* 176 */       return index - this.firstCategoryIndex;
/*     */     }
/* 178 */     return -1;
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
/* 192 */     return this.underlying.getColumnKey(column + this.firstCategoryIndex);
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
/* 204 */     List<Comparable> result = new ArrayList();
/* 205 */     int last = lastCategoryIndex();
/* 206 */     for (int i = this.firstCategoryIndex; i <= last; i++) {
/* 207 */       result.add(this.underlying.getColumnKey(i));
/*     */     }
/* 209 */     return Collections.unmodifiableList(result);
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
/* 221 */     return this.underlying.getRowIndex(key);
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
/* 235 */     return this.underlying.getRowKey(row);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getRowKeys() {
/* 245 */     return this.underlying.getRowKeys();
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
/* 260 */     int r = getRowIndex(rowKey);
/* 261 */     int c = getColumnIndex(columnKey);
/* 262 */     if (c != -1) {
/* 263 */       return this.underlying.getValue(r, c + this.firstCategoryIndex);
/*     */     }
/*     */     
/* 266 */     throw new UnknownKeyException("Unknown columnKey: " + columnKey);
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
/* 277 */     int last = lastCategoryIndex();
/* 278 */     if (last == -1) {
/* 279 */       return 0;
/*     */     }
/*     */     
/* 282 */     return Math.max(last - this.firstCategoryIndex + 1, 0);
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
/* 293 */     return this.underlying.getRowCount();
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
/* 306 */     return this.underlying.getValue(row, column + this.firstCategoryIndex);
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
/* 319 */     if (obj == this) {
/* 320 */       return true;
/*     */     }
/* 322 */     if (!(obj instanceof SlidingCategoryDataset)) {
/* 323 */       return false;
/*     */     }
/* 325 */     SlidingCategoryDataset that = (SlidingCategoryDataset)obj;
/* 326 */     if (this.firstCategoryIndex != that.firstCategoryIndex) {
/* 327 */       return false;
/*     */     }
/* 329 */     if (this.maximumCategoryCount != that.maximumCategoryCount) {
/* 330 */       return false;
/*     */     }
/* 332 */     if (!this.underlying.equals(that.underlying)) {
/* 333 */       return false;
/*     */     }
/* 335 */     return true;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 354 */     SlidingCategoryDataset clone = (SlidingCategoryDataset)super.clone();
/* 355 */     if (this.underlying instanceof PublicCloneable) {
/* 356 */       PublicCloneable pc = (PublicCloneable)this.underlying;
/* 357 */       clone.underlying = (CategoryDataset)pc.clone();
/*     */     } 
/* 359 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/category/SlidingCategoryDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */