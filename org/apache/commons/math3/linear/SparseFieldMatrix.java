/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.FieldElement;
/*     */ import org.apache.commons.math3.util.OpenIntToFieldHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SparseFieldMatrix<T extends FieldElement<T>>
/*     */   extends AbstractFieldMatrix<T>
/*     */ {
/*     */   private final OpenIntToFieldHashMap<T> entries;
/*     */   private final int rows;
/*     */   private final int columns;
/*     */   
/*     */   public SparseFieldMatrix(Field<T> field) {
/*  51 */     super(field);
/*  52 */     this.rows = 0;
/*  53 */     this.columns = 0;
/*  54 */     this.entries = new OpenIntToFieldHashMap(field);
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
/*     */   public SparseFieldMatrix(Field<T> field, int rowDimension, int columnDimension) {
/*  69 */     super(field, rowDimension, columnDimension);
/*  70 */     this.rows = rowDimension;
/*  71 */     this.columns = columnDimension;
/*  72 */     this.entries = new OpenIntToFieldHashMap(field);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseFieldMatrix(SparseFieldMatrix<T> other) {
/*  81 */     super(other.getField(), other.getRowDimension(), other.getColumnDimension());
/*  82 */     this.rows = other.getRowDimension();
/*  83 */     this.columns = other.getColumnDimension();
/*  84 */     this.entries = new OpenIntToFieldHashMap(other.entries);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseFieldMatrix(FieldMatrix<T> other) {
/*  93 */     super(other.getField(), other.getRowDimension(), other.getColumnDimension());
/*  94 */     this.rows = other.getRowDimension();
/*  95 */     this.columns = other.getColumnDimension();
/*  96 */     this.entries = new OpenIntToFieldHashMap(getField());
/*  97 */     for (int i = 0; i < this.rows; i++) {
/*  98 */       for (int j = 0; j < this.columns; j++) {
/*  99 */         setEntry(i, j, other.getEntry(i, j));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToEntry(int row, int column, T increment) {
/* 107 */     checkRowIndex(row);
/* 108 */     checkColumnIndex(column);
/* 109 */     int key = computeKey(row, column);
/* 110 */     FieldElement fieldElement = (FieldElement)this.entries.get(key).add(increment);
/* 111 */     if (((FieldElement)getField().getZero()).equals(fieldElement)) {
/* 112 */       this.entries.remove(key);
/*     */     } else {
/* 114 */       this.entries.put(key, fieldElement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldMatrix<T> copy() {
/* 121 */     return new SparseFieldMatrix(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldMatrix<T> createMatrix(int rowDimension, int columnDimension) {
/* 127 */     return new SparseFieldMatrix(getField(), rowDimension, columnDimension);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnDimension() {
/* 133 */     return this.columns;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T getEntry(int row, int column) {
/* 139 */     checkRowIndex(row);
/* 140 */     checkColumnIndex(column);
/* 141 */     return (T)this.entries.get(computeKey(row, column));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowDimension() {
/* 147 */     return this.rows;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyEntry(int row, int column, T factor) {
/* 153 */     checkRowIndex(row);
/* 154 */     checkColumnIndex(column);
/* 155 */     int key = computeKey(row, column);
/* 156 */     FieldElement fieldElement = (FieldElement)this.entries.get(key).multiply(factor);
/* 157 */     if (((FieldElement)getField().getZero()).equals(fieldElement)) {
/* 158 */       this.entries.remove(key);
/*     */     } else {
/* 160 */       this.entries.put(key, fieldElement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntry(int row, int column, T value) {
/* 168 */     checkRowIndex(row);
/* 169 */     checkColumnIndex(column);
/* 170 */     if (((FieldElement)getField().getZero()).equals(value)) {
/* 171 */       this.entries.remove(computeKey(row, column));
/*     */     } else {
/* 173 */       this.entries.put(computeKey(row, column), (FieldElement)value);
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
/*     */   private int computeKey(int row, int column) {
/* 185 */     return row * this.columns + column;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/SparseFieldMatrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */