/*     */ package org.jfree.data;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultKeyedValues2D
/*     */   implements KeyedValues2D, PublicCloneable, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5514169970951994748L;
/*     */   private List rowKeys;
/*     */   private List columnKeys;
/*     */   private List rows;
/*     */   private boolean sortRowKeys;
/*     */   
/*     */   public DefaultKeyedValues2D() {
/*  97 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultKeyedValues2D(boolean sortRowKeys) {
/* 106 */     this.rowKeys = new ArrayList();
/* 107 */     this.columnKeys = new ArrayList();
/* 108 */     this.rows = new ArrayList();
/* 109 */     this.sortRowKeys = sortRowKeys;
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
/*     */   public int getRowCount() {
/* 121 */     return this.rowKeys.size();
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
/*     */   public int getColumnCount() {
/* 133 */     return this.columnKeys.size();
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
/*     */   public Number getValue(int row, int column) {
/* 148 */     Number result = null;
/* 149 */     DefaultKeyedValues rowData = this.rows.get(row);
/* 150 */     if (rowData != null) {
/* 151 */       Comparable columnKey = this.columnKeys.get(column);
/*     */ 
/*     */       
/* 154 */       int index = rowData.getIndex(columnKey);
/* 155 */       if (index >= 0) {
/* 156 */         result = rowData.getValue(index);
/*     */       }
/*     */     } 
/* 159 */     return result;
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
/*     */   public Comparable getRowKey(int row) {
/* 174 */     return this.rowKeys.get(row);
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
/*     */   public int getRowIndex(Comparable key) {
/* 189 */     ParamChecks.nullNotPermitted(key, "key");
/* 190 */     if (this.sortRowKeys) {
/* 191 */       return Collections.binarySearch(this.rowKeys, key);
/*     */     }
/*     */     
/* 194 */     return this.rowKeys.indexOf(key);
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
/*     */   public List getRowKeys() {
/* 207 */     return Collections.unmodifiableList(this.rowKeys);
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
/*     */   public Comparable getColumnKey(int column) {
/* 223 */     return this.columnKeys.get(column);
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
/*     */   public int getColumnIndex(Comparable key) {
/* 238 */     ParamChecks.nullNotPermitted(key, "key");
/* 239 */     return this.columnKeys.indexOf(key);
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
/* 251 */     return Collections.unmodifiableList(this.columnKeys);
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
/*     */   public Number getValue(Comparable rowKey, Comparable columnKey) {
/* 269 */     ParamChecks.nullNotPermitted(rowKey, "rowKey");
/* 270 */     ParamChecks.nullNotPermitted(columnKey, "columnKey");
/*     */ 
/*     */     
/* 273 */     if (!this.columnKeys.contains(columnKey)) {
/* 274 */       throw new UnknownKeyException("Unrecognised columnKey: " + columnKey);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     int row = getRowIndex(rowKey);
/* 282 */     if (row >= 0) {
/*     */       
/* 284 */       DefaultKeyedValues rowData = this.rows.get(row);
/* 285 */       int col = rowData.getIndex(columnKey);
/* 286 */       return (col >= 0) ? rowData.getValue(col) : null;
/*     */     } 
/*     */     
/* 289 */     throw new UnknownKeyException("Unrecognised rowKey: " + rowKey);
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
/*     */   public void addValue(Number value, Comparable rowKey, Comparable columnKey) {
/* 307 */     setValue(value, rowKey, columnKey);
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
/*     */   public void setValue(Number value, Comparable rowKey, Comparable columnKey) {
/*     */     DefaultKeyedValues row;
/* 324 */     int rowIndex = getRowIndex(rowKey);
/*     */     
/* 326 */     if (rowIndex >= 0) {
/* 327 */       row = this.rows.get(rowIndex);
/*     */     } else {
/*     */       
/* 330 */       row = new DefaultKeyedValues();
/* 331 */       if (this.sortRowKeys) {
/* 332 */         rowIndex = -rowIndex - 1;
/* 333 */         this.rowKeys.add(rowIndex, rowKey);
/* 334 */         this.rows.add(rowIndex, row);
/*     */       } else {
/*     */         
/* 337 */         this.rowKeys.add(rowKey);
/* 338 */         this.rows.add(row);
/*     */       } 
/*     */     } 
/* 341 */     row.setValue(columnKey, value);
/*     */     
/* 343 */     int columnIndex = this.columnKeys.indexOf(columnKey);
/* 344 */     if (columnIndex < 0) {
/* 345 */       this.columnKeys.add(columnKey);
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
/*     */   public void removeValue(Comparable rowKey, Comparable columnKey) {
/* 360 */     setValue(null, rowKey, columnKey);
/*     */ 
/*     */     
/* 363 */     boolean allNull = true;
/* 364 */     int rowIndex = getRowIndex(rowKey);
/* 365 */     DefaultKeyedValues row = this.rows.get(rowIndex);
/*     */     int item, itemCount;
/* 367 */     for (item = 0, itemCount = row.getItemCount(); item < itemCount; 
/* 368 */       item++) {
/* 369 */       if (row.getValue(item) != null) {
/* 370 */         allNull = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 375 */     if (allNull) {
/* 376 */       this.rowKeys.remove(rowIndex);
/* 377 */       this.rows.remove(rowIndex);
/*     */     } 
/*     */ 
/*     */     
/* 381 */     allNull = true;
/*     */ 
/*     */     
/* 384 */     for (item = 0, itemCount = this.rows.size(); item < itemCount; 
/* 385 */       item++) {
/* 386 */       row = this.rows.get(item);
/* 387 */       int columnIndex = row.getIndex(columnKey);
/* 388 */       if (columnIndex >= 0 && row.getValue(columnIndex) != null) {
/* 389 */         allNull = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 394 */     if (allNull) {
/* 395 */       for (item = 0, itemCount = this.rows.size(); item < itemCount; 
/* 396 */         item++) {
/* 397 */         row = this.rows.get(item);
/* 398 */         int columnIndex = row.getIndex(columnKey);
/* 399 */         if (columnIndex >= 0) {
/* 400 */           row.removeValue(columnIndex);
/*     */         }
/*     */       } 
/* 403 */       this.columnKeys.remove(columnKey);
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
/*     */   public void removeRow(int rowIndex) {
/* 416 */     this.rowKeys.remove(rowIndex);
/* 417 */     this.rows.remove(rowIndex);
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
/*     */   public void removeRow(Comparable rowKey) {
/* 432 */     ParamChecks.nullNotPermitted(rowKey, "rowKey");
/* 433 */     int index = getRowIndex(rowKey);
/* 434 */     if (index >= 0) {
/* 435 */       removeRow(index);
/*     */     } else {
/*     */       
/* 438 */       throw new UnknownKeyException("Unknown key: " + rowKey);
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
/*     */   public void removeColumn(int columnIndex) {
/* 451 */     Comparable columnKey = getColumnKey(columnIndex);
/* 452 */     removeColumn(columnKey);
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
/*     */   public void removeColumn(Comparable columnKey) {
/* 469 */     ParamChecks.nullNotPermitted(columnKey, "columnKey");
/* 470 */     if (!this.columnKeys.contains(columnKey)) {
/* 471 */       throw new UnknownKeyException("Unknown key: " + columnKey);
/*     */     }
/* 473 */     Iterator<DefaultKeyedValues> iterator = this.rows.iterator();
/* 474 */     while (iterator.hasNext()) {
/* 475 */       DefaultKeyedValues rowData = iterator.next();
/* 476 */       int index = rowData.getIndex(columnKey);
/* 477 */       if (index >= 0) {
/* 478 */         rowData.removeValue(columnKey);
/*     */       }
/*     */     } 
/* 481 */     this.columnKeys.remove(columnKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 488 */     this.rowKeys.clear();
/* 489 */     this.columnKeys.clear();
/* 490 */     this.rows.clear();
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
/*     */   public boolean equals(Object o) {
/* 503 */     if (o == null) {
/* 504 */       return false;
/*     */     }
/* 506 */     if (o == this) {
/* 507 */       return true;
/*     */     }
/*     */     
/* 510 */     if (!(o instanceof KeyedValues2D)) {
/* 511 */       return false;
/*     */     }
/* 513 */     KeyedValues2D kv2D = (KeyedValues2D)o;
/* 514 */     if (!getRowKeys().equals(kv2D.getRowKeys())) {
/* 515 */       return false;
/*     */     }
/* 517 */     if (!getColumnKeys().equals(kv2D.getColumnKeys())) {
/* 518 */       return false;
/*     */     }
/* 520 */     int rowCount = getRowCount();
/* 521 */     if (rowCount != kv2D.getRowCount()) {
/* 522 */       return false;
/*     */     }
/*     */     
/* 525 */     int colCount = getColumnCount();
/* 526 */     if (colCount != kv2D.getColumnCount()) {
/* 527 */       return false;
/*     */     }
/*     */     
/* 530 */     for (int r = 0; r < rowCount; r++) {
/* 531 */       for (int c = 0; c < colCount; c++) {
/* 532 */         Number v1 = getValue(r, c);
/* 533 */         Number v2 = kv2D.getValue(r, c);
/* 534 */         if (v1 == null) {
/* 535 */           if (v2 != null) {
/* 536 */             return false;
/*     */           
/*     */           }
/*     */         }
/* 540 */         else if (!v1.equals(v2)) {
/* 541 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 546 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 557 */     int result = this.rowKeys.hashCode();
/* 558 */     result = 29 * result + this.columnKeys.hashCode();
/* 559 */     result = 29 * result + this.rows.hashCode();
/* 560 */     return result;
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
/* 573 */     DefaultKeyedValues2D clone = (DefaultKeyedValues2D)super.clone();
/*     */ 
/*     */     
/* 576 */     clone.columnKeys = new ArrayList(this.columnKeys);
/* 577 */     clone.rowKeys = new ArrayList(this.rowKeys);
/*     */ 
/*     */     
/* 580 */     clone.rows = (List)ObjectUtilities.deepClone(this.rows);
/* 581 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/DefaultKeyedValues2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */