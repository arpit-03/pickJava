/*     */ package org.jfree.data.statistics;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.KeyedObjects2D;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.RangeInfo;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultMultiValueCategoryDataset
/*     */   extends AbstractDataset
/*     */   implements MultiValueCategoryDataset, RangeInfo, PublicCloneable
/*     */ {
/*  90 */   protected KeyedObjects2D data = new KeyedObjects2D();
/*  91 */   private Number minimumRangeValue = null;
/*  92 */   private Number maximumRangeValue = null;
/*  93 */   private Range rangeBounds = new Range(0.0D, 0.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(List values, Comparable rowKey, Comparable columnKey) {
/* 107 */     ParamChecks.nullNotPermitted(values, "values");
/* 108 */     ParamChecks.nullNotPermitted(rowKey, "rowKey");
/* 109 */     ParamChecks.nullNotPermitted(columnKey, "columnKey");
/* 110 */     List<Number> vlist = new ArrayList(values.size());
/* 111 */     Iterator iterator = values.listIterator();
/* 112 */     while (iterator.hasNext()) {
/* 113 */       Object obj = iterator.next();
/* 114 */       if (obj instanceof Number) {
/* 115 */         Number n = (Number)obj;
/* 116 */         double v = n.doubleValue();
/* 117 */         if (!Double.isNaN(v)) {
/* 118 */           vlist.add(n);
/*     */         }
/*     */       } 
/*     */     } 
/* 122 */     Collections.sort(vlist);
/* 123 */     this.data.addObject(vlist, rowKey, columnKey);
/*     */     
/* 125 */     if (vlist.size() > 0) {
/* 126 */       double maxval = Double.NEGATIVE_INFINITY;
/* 127 */       double minval = Double.POSITIVE_INFINITY;
/* 128 */       for (int i = 0; i < vlist.size(); i++) {
/* 129 */         Number n = vlist.get(i);
/* 130 */         double v = n.doubleValue();
/* 131 */         minval = Math.min(minval, v);
/* 132 */         maxval = Math.max(maxval, v);
/*     */       } 
/*     */ 
/*     */       
/* 136 */       if (this.maximumRangeValue == null) {
/* 137 */         this.maximumRangeValue = new Double(maxval);
/*     */       }
/* 139 */       else if (maxval > this.maximumRangeValue.doubleValue()) {
/* 140 */         this.maximumRangeValue = new Double(maxval);
/*     */       } 
/*     */       
/* 143 */       if (this.minimumRangeValue == null) {
/* 144 */         this.minimumRangeValue = new Double(minval);
/*     */       }
/* 146 */       else if (minval < this.minimumRangeValue.doubleValue()) {
/* 147 */         this.minimumRangeValue = new Double(minval);
/*     */       } 
/* 149 */       this
/* 150 */         .rangeBounds = new Range(this.minimumRangeValue.doubleValue(), this.maximumRangeValue.doubleValue());
/*     */     } 
/*     */     
/* 153 */     fireDatasetChanged();
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
/*     */   public List getValues(int row, int column) {
/* 167 */     List<?> values = (List)this.data.getObject(row, column);
/* 168 */     if (values != null) {
/* 169 */       return Collections.unmodifiableList(values);
/*     */     }
/*     */     
/* 172 */     return Collections.EMPTY_LIST;
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
/*     */   public List getValues(Comparable rowKey, Comparable columnKey) {
/* 187 */     return Collections.unmodifiableList((List)this.data.getObject(rowKey, columnKey));
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
/*     */   public Number getValue(Comparable row, Comparable column) {
/* 201 */     List<Number> l = (List)this.data.getObject(row, column);
/* 202 */     double average = 0.0D;
/* 203 */     int count = 0;
/* 204 */     if (l != null && l.size() > 0) {
/* 205 */       for (int i = 0; i < l.size(); i++) {
/* 206 */         Number n = l.get(i);
/* 207 */         average += n.doubleValue();
/* 208 */         count++;
/*     */       } 
/* 210 */       if (count > 0) {
/* 211 */         average /= count;
/*     */       }
/*     */     } 
/* 214 */     if (count == 0) {
/* 215 */       return null;
/*     */     }
/* 217 */     return new Double(average);
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
/* 230 */     List<Number> l = (List)this.data.getObject(row, column);
/* 231 */     double average = 0.0D;
/* 232 */     int count = 0;
/* 233 */     if (l != null && l.size() > 0) {
/* 234 */       for (int i = 0; i < l.size(); i++) {
/* 235 */         Number n = l.get(i);
/* 236 */         average += n.doubleValue();
/* 237 */         count++;
/*     */       } 
/* 239 */       if (count > 0) {
/* 240 */         average /= count;
/*     */       }
/*     */     } 
/* 243 */     if (count == 0) {
/* 244 */       return null;
/*     */     }
/* 246 */     return new Double(average);
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
/* 258 */     return this.data.getColumnIndex(key);
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
/*     */   public Comparable getColumnKey(int column) {
/* 270 */     return this.data.getColumnKey(column);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getColumnKeys() {
/* 280 */     return this.data.getColumnKeys();
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
/* 292 */     return this.data.getRowIndex(key);
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
/*     */   public Comparable getRowKey(int row) {
/* 304 */     return this.data.getRowKey(row);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getRowKeys() {
/* 314 */     return this.data.getRowKeys();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/* 324 */     return this.data.getRowCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 334 */     return this.data.getColumnCount();
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
/*     */   public double getRangeLowerBound(boolean includeInterval) {
/* 347 */     double result = Double.NaN;
/* 348 */     if (this.minimumRangeValue != null) {
/* 349 */       result = this.minimumRangeValue.doubleValue();
/*     */     }
/* 351 */     return result;
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
/*     */   public double getRangeUpperBound(boolean includeInterval) {
/* 364 */     double result = Double.NaN;
/* 365 */     if (this.maximumRangeValue != null) {
/* 366 */       result = this.maximumRangeValue.doubleValue();
/*     */     }
/* 368 */     return result;
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
/*     */   public Range getRangeBounds(boolean includeInterval) {
/* 380 */     return this.rangeBounds;
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
/* 392 */     if (obj == this) {
/* 393 */       return true;
/*     */     }
/* 395 */     if (!(obj instanceof DefaultMultiValueCategoryDataset)) {
/* 396 */       return false;
/*     */     }
/* 398 */     DefaultMultiValueCategoryDataset that = (DefaultMultiValueCategoryDataset)obj;
/*     */     
/* 400 */     return this.data.equals(that.data);
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
/* 413 */     DefaultMultiValueCategoryDataset clone = (DefaultMultiValueCategoryDataset)super.clone();
/* 414 */     clone.data = (KeyedObjects2D)this.data.clone();
/* 415 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/statistics/DefaultMultiValueCategoryDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */