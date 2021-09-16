/*     */ package org.jfree.data.category;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.general.AbstractDataset;
/*     */ import org.jfree.data.general.DatasetChangeEvent;
/*     */ import org.jfree.data.general.DatasetChangeListener;
/*     */ import org.jfree.data.general.PieDataset;
/*     */ import org.jfree.util.TableOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CategoryToPieDataset
/*     */   extends AbstractDataset
/*     */   implements PieDataset, DatasetChangeListener
/*     */ {
/*     */   static final long serialVersionUID = 5516396319762189617L;
/*     */   private CategoryDataset source;
/*     */   private TableOrder extract;
/*     */   private int index;
/*     */   
/*     */   public CategoryToPieDataset(CategoryDataset source, TableOrder extract, int index) {
/*  95 */     ParamChecks.nullNotPermitted(extract, "extract");
/*  96 */     this.source = source;
/*  97 */     if (this.source != null) {
/*  98 */       this.source.addChangeListener(this);
/*     */     }
/* 100 */     this.extract = extract;
/* 101 */     this.index = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CategoryDataset getUnderlyingDataset() {
/* 112 */     return this.source;
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
/*     */   public TableOrder getExtractType() {
/* 124 */     return this.extract;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtractIndex() {
/* 135 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/* 146 */     int result = 0;
/* 147 */     if (this.source != null) {
/* 148 */       if (this.extract == TableOrder.BY_ROW) {
/* 149 */         result = this.source.getColumnCount();
/*     */       }
/* 151 */       else if (this.extract == TableOrder.BY_COLUMN) {
/* 152 */         result = this.source.getRowCount();
/*     */       } 
/*     */     }
/* 155 */     return result;
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
/*     */   public Number getValue(int item) {
/* 170 */     Number result = null;
/* 171 */     if (item < 0 || item >= getItemCount())
/*     */     {
/* 173 */       throw new IndexOutOfBoundsException("The 'item' index is out of bounds.");
/*     */     }
/*     */     
/* 176 */     if (this.extract == TableOrder.BY_ROW) {
/* 177 */       result = this.source.getValue(this.index, item);
/*     */     }
/* 179 */     else if (this.extract == TableOrder.BY_COLUMN) {
/* 180 */       result = this.source.getValue(item, this.index);
/*     */     } 
/* 182 */     return result;
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
/*     */   public Comparable getKey(int index) {
/* 198 */     Comparable result = null;
/* 199 */     if (index < 0 || index >= getItemCount())
/*     */     {
/* 201 */       throw new IndexOutOfBoundsException("Invalid 'index': " + index);
/*     */     }
/* 203 */     if (this.extract == TableOrder.BY_ROW) {
/* 204 */       result = this.source.getColumnKey(index);
/*     */     }
/* 206 */     else if (this.extract == TableOrder.BY_COLUMN) {
/* 207 */       result = this.source.getRowKey(index);
/*     */     } 
/* 209 */     return result;
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
/*     */   public int getIndex(Comparable key) {
/* 222 */     int result = -1;
/* 223 */     if (this.source != null) {
/* 224 */       if (this.extract == TableOrder.BY_ROW) {
/* 225 */         result = this.source.getColumnIndex(key);
/*     */       }
/* 227 */       else if (this.extract == TableOrder.BY_COLUMN) {
/* 228 */         result = this.source.getRowIndex(key);
/*     */       } 
/*     */     }
/* 231 */     return result;
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
/*     */   public List getKeys() {
/* 244 */     List result = Collections.EMPTY_LIST;
/* 245 */     if (this.source != null) {
/* 246 */       if (this.extract == TableOrder.BY_ROW) {
/* 247 */         result = this.source.getColumnKeys();
/*     */       }
/* 249 */       else if (this.extract == TableOrder.BY_COLUMN) {
/* 250 */         result = this.source.getRowKeys();
/*     */       } 
/*     */     }
/* 253 */     return result;
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
/*     */   public Number getValue(Comparable key) {
/* 267 */     Number result = null;
/* 268 */     int keyIndex = getIndex(key);
/* 269 */     if (keyIndex != -1) {
/* 270 */       if (this.extract == TableOrder.BY_ROW) {
/* 271 */         result = this.source.getValue(this.index, keyIndex);
/*     */       }
/* 273 */       else if (this.extract == TableOrder.BY_COLUMN) {
/* 274 */         result = this.source.getValue(keyIndex, this.index);
/*     */       } 
/*     */     }
/* 277 */     return result;
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
/*     */   
/*     */   public boolean equals(Object obj) {
/* 303 */     if (obj == this) {
/* 304 */       return true;
/*     */     }
/* 306 */     if (!(obj instanceof PieDataset)) {
/* 307 */       return false;
/*     */     }
/* 309 */     PieDataset that = (PieDataset)obj;
/* 310 */     int count = getItemCount();
/* 311 */     if (that.getItemCount() != count) {
/* 312 */       return false;
/*     */     }
/* 314 */     for (int i = 0; i < count; i++) {
/* 315 */       Comparable k1 = getKey(i);
/* 316 */       Comparable k2 = that.getKey(i);
/* 317 */       if (!k1.equals(k2)) {
/* 318 */         return false;
/*     */       }
/*     */       
/* 321 */       Number v1 = getValue(i);
/* 322 */       Number v2 = that.getValue(i);
/* 323 */       if (v1 == null) {
/* 324 */         if (v2 != null) {
/* 325 */           return false;
/*     */         
/*     */         }
/*     */       }
/* 329 */       else if (!v1.equals(v2)) {
/* 330 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 334 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/category/CategoryToPieDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */