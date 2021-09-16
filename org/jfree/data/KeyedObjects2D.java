/*     */ package org.jfree.data;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyedObjects2D
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1015873563138522374L;
/*  76 */   private List rowKeys = new ArrayList();
/*  77 */   private List columnKeys = new ArrayList();
/*  78 */   private List rows = new ArrayList();
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
/*  89 */     return this.rowKeys.size();
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
/* 100 */     return this.columnKeys.size();
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
/*     */   public Object getObject(int row, int column) {
/* 114 */     Object result = null;
/* 115 */     KeyedObjects rowData = this.rows.get(row);
/* 116 */     if (rowData != null) {
/* 117 */       Comparable columnKey = this.columnKeys.get(column);
/* 118 */       if (columnKey != null) {
/* 119 */         int index = rowData.getIndex(columnKey);
/* 120 */         if (index >= 0) {
/* 121 */           result = rowData.getObject(columnKey);
/*     */         }
/*     */       } 
/*     */     } 
/* 125 */     return result;
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
/*     */   public Comparable getRowKey(int row) {
/* 138 */     return this.rowKeys.get(row);
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
/*     */   public int getRowIndex(Comparable key) {
/* 152 */     ParamChecks.nullNotPermitted(key, "key");
/* 153 */     return this.rowKeys.indexOf(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getRowKeys() {
/* 164 */     return Collections.unmodifiableList(this.rowKeys);
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
/*     */   public Comparable getColumnKey(int column) {
/* 177 */     return this.columnKeys.get(column);
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
/*     */   public int getColumnIndex(Comparable key) {
/* 191 */     ParamChecks.nullNotPermitted(key, "key");
/* 192 */     return this.columnKeys.indexOf(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getColumnKeys() {
/* 203 */     return Collections.unmodifiableList(this.columnKeys);
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
/*     */   public Object getObject(Comparable rowKey, Comparable columnKey) {
/* 220 */     ParamChecks.nullNotPermitted(rowKey, "rowKey");
/* 221 */     ParamChecks.nullNotPermitted(columnKey, "columnKey");
/* 222 */     int row = this.rowKeys.indexOf(rowKey);
/* 223 */     if (row < 0) {
/* 224 */       throw new UnknownKeyException("Row key (" + rowKey + ") not recognised.");
/*     */     }
/*     */     
/* 227 */     int column = this.columnKeys.indexOf(columnKey);
/* 228 */     if (column < 0) {
/* 229 */       throw new UnknownKeyException("Column key (" + columnKey + ") not recognised.");
/*     */     }
/*     */     
/* 232 */     KeyedObjects rowData = this.rows.get(row);
/* 233 */     int index = rowData.getIndex(columnKey);
/* 234 */     if (index >= 0) {
/* 235 */       return rowData.getObject(index);
/*     */     }
/*     */     
/* 238 */     return null;
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
/*     */   public void addObject(Object object, Comparable rowKey, Comparable columnKey) {
/* 251 */     setObject(object, rowKey, columnKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObject(Object object, Comparable rowKey, Comparable columnKey) {
/*     */     KeyedObjects row;
/* 263 */     ParamChecks.nullNotPermitted(rowKey, "rowKey");
/* 264 */     ParamChecks.nullNotPermitted(columnKey, "columnKey");
/*     */     
/* 266 */     int rowIndex = this.rowKeys.indexOf(rowKey);
/* 267 */     if (rowIndex >= 0) {
/* 268 */       row = this.rows.get(rowIndex);
/*     */     } else {
/*     */       
/* 271 */       this.rowKeys.add(rowKey);
/* 272 */       row = new KeyedObjects();
/* 273 */       this.rows.add(row);
/*     */     } 
/* 275 */     row.setObject(columnKey, object);
/* 276 */     int columnIndex = this.columnKeys.indexOf(columnKey);
/* 277 */     if (columnIndex < 0) {
/* 278 */       this.columnKeys.add(columnKey);
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
/*     */   public void removeObject(Comparable rowKey, Comparable columnKey) {
/* 293 */     int rowIndex = getRowIndex(rowKey);
/* 294 */     if (rowIndex < 0) {
/* 295 */       throw new UnknownKeyException("Row key (" + rowKey + ") not recognised.");
/*     */     }
/*     */     
/* 298 */     int columnIndex = getColumnIndex(columnKey);
/* 299 */     if (columnIndex < 0) {
/* 300 */       throw new UnknownKeyException("Column key (" + columnKey + ") not recognised.");
/*     */     }
/*     */     
/* 303 */     setObject(null, rowKey, columnKey);
/*     */ 
/*     */     
/* 306 */     boolean allNull = true;
/* 307 */     KeyedObjects row = this.rows.get(rowIndex);
/*     */     int item, itemCount;
/* 309 */     for (item = 0, itemCount = row.getItemCount(); item < itemCount; 
/* 310 */       item++) {
/* 311 */       if (row.getObject(item) != null) {
/* 312 */         allNull = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 317 */     if (allNull) {
/* 318 */       this.rowKeys.remove(rowIndex);
/* 319 */       this.rows.remove(rowIndex);
/*     */     } 
/*     */ 
/*     */     
/* 323 */     allNull = true;
/*     */     
/* 325 */     for (item = 0, itemCount = this.rows.size(); item < itemCount; 
/* 326 */       item++) {
/* 327 */       row = this.rows.get(item);
/* 328 */       int colIndex = row.getIndex(columnKey);
/* 329 */       if (colIndex >= 0 && row.getObject(colIndex) != null) {
/* 330 */         allNull = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 335 */     if (allNull) {
/* 336 */       for (item = 0, itemCount = this.rows.size(); item < itemCount; 
/* 337 */         item++) {
/* 338 */         row = this.rows.get(item);
/* 339 */         int colIndex = row.getIndex(columnKey);
/* 340 */         if (colIndex >= 0) {
/* 341 */           row.removeValue(colIndex);
/*     */         }
/*     */       } 
/* 344 */       this.columnKeys.remove(columnKey);
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
/*     */   public void removeRow(int rowIndex) {
/* 356 */     this.rowKeys.remove(rowIndex);
/* 357 */     this.rows.remove(rowIndex);
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
/*     */   public void removeRow(Comparable rowKey) {
/* 370 */     int index = getRowIndex(rowKey);
/* 371 */     if (index < 0) {
/* 372 */       throw new UnknownKeyException("Row key (" + rowKey + ") not recognised.");
/*     */     }
/*     */     
/* 375 */     removeRow(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeColumn(int columnIndex) {
/* 386 */     Comparable columnKey = getColumnKey(columnIndex);
/* 387 */     removeColumn(columnKey);
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
/*     */   public void removeColumn(Comparable columnKey) {
/* 400 */     int index = getColumnIndex(columnKey);
/* 401 */     if (index < 0) {
/* 402 */       throw new UnknownKeyException("Column key (" + columnKey + ") not recognised.");
/*     */     }
/*     */     
/* 405 */     Iterator<KeyedObjects> iterator = this.rows.iterator();
/* 406 */     while (iterator.hasNext()) {
/* 407 */       KeyedObjects rowData = iterator.next();
/* 408 */       int i = rowData.getIndex(columnKey);
/* 409 */       if (i >= 0) {
/* 410 */         rowData.removeValue(i);
/*     */       }
/*     */     } 
/* 413 */     this.columnKeys.remove(columnKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 422 */     this.rowKeys.clear();
/* 423 */     this.columnKeys.clear();
/* 424 */     this.rows.clear();
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
/* 436 */     if (obj == this) {
/* 437 */       return true;
/*     */     }
/* 439 */     if (!(obj instanceof KeyedObjects2D)) {
/* 440 */       return false;
/*     */     }
/*     */     
/* 443 */     KeyedObjects2D that = (KeyedObjects2D)obj;
/* 444 */     if (!getRowKeys().equals(that.getRowKeys())) {
/* 445 */       return false;
/*     */     }
/* 447 */     if (!getColumnKeys().equals(that.getColumnKeys())) {
/* 448 */       return false;
/*     */     }
/* 450 */     int rowCount = getRowCount();
/* 451 */     if (rowCount != that.getRowCount()) {
/* 452 */       return false;
/*     */     }
/* 454 */     int colCount = getColumnCount();
/* 455 */     if (colCount != that.getColumnCount()) {
/* 456 */       return false;
/*     */     }
/* 458 */     for (int r = 0; r < rowCount; r++) {
/* 459 */       for (int c = 0; c < colCount; c++) {
/* 460 */         Object v1 = getObject(r, c);
/* 461 */         Object v2 = that.getObject(r, c);
/* 462 */         if (v1 == null) {
/* 463 */           if (v2 != null) {
/* 464 */             return false;
/*     */           
/*     */           }
/*     */         }
/* 468 */         else if (!v1.equals(v2)) {
/* 469 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 474 */     return true;
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
/* 485 */     int result = this.rowKeys.hashCode();
/* 486 */     result = 29 * result + this.columnKeys.hashCode();
/* 487 */     result = 29 * result + this.rows.hashCode();
/* 488 */     return result;
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
/* 501 */     KeyedObjects2D clone = (KeyedObjects2D)super.clone();
/* 502 */     clone.columnKeys = new ArrayList(this.columnKeys);
/* 503 */     clone.rowKeys = new ArrayList(this.rowKeys);
/* 504 */     clone.rows = new ArrayList(this.rows.size());
/* 505 */     Iterator<KeyedObjects> iterator = this.rows.iterator();
/* 506 */     while (iterator.hasNext()) {
/* 507 */       KeyedObjects row = iterator.next();
/* 508 */       clone.rows.add(row.clone());
/*     */     } 
/* 510 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/KeyedObjects2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */