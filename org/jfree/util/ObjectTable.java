/*     */ package org.jfree.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectTable
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3968322452944912066L;
/*     */   private int rows;
/*     */   private int columns;
/*     */   private transient Object[][] data;
/*     */   private int rowIncrement;
/*     */   private int columnIncrement;
/*     */   
/*     */   public ObjectTable() {
/*  98 */     this(5, 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectTable(int increment) {
/* 108 */     this(increment, increment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectTable(int rowIncrement, int colIncrement) {
/* 119 */     if (rowIncrement < 1)
/*     */     {
/* 121 */       throw new IllegalArgumentException("Increment must be positive.");
/*     */     }
/*     */     
/* 124 */     if (colIncrement < 1)
/*     */     {
/* 126 */       throw new IllegalArgumentException("Increment must be positive.");
/*     */     }
/*     */     
/* 129 */     this.rows = 0;
/* 130 */     this.columns = 0;
/* 131 */     this.rowIncrement = rowIncrement;
/* 132 */     this.columnIncrement = colIncrement;
/*     */     
/* 134 */     this.data = new Object[rowIncrement][];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnIncrement() {
/* 144 */     return this.columnIncrement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowIncrement() {
/* 154 */     return this.rowIncrement;
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
/*     */   protected void ensureRowCapacity(int row) {
/* 167 */     if (row >= this.data.length) {
/*     */ 
/*     */       
/* 170 */       Object[][] enlarged = new Object[row + this.rowIncrement][];
/* 171 */       System.arraycopy(this.data, 0, enlarged, 0, this.data.length);
/*     */ 
/*     */       
/* 174 */       this.data = enlarged;
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
/*     */   public void ensureCapacity(int row, int column) {
/* 187 */     if (row < 0)
/*     */     {
/* 189 */       throw new IndexOutOfBoundsException("Row is invalid. " + row);
/*     */     }
/* 191 */     if (column < 0)
/*     */     {
/* 193 */       throw new IndexOutOfBoundsException("Column is invalid. " + column);
/*     */     }
/*     */     
/* 196 */     ensureRowCapacity(row);
/*     */     
/* 198 */     Object[] current = this.data[row];
/* 199 */     if (current == null) {
/*     */ 
/*     */       
/* 202 */       Object[] enlarged = new Object[Math.max(column + 1, this.columnIncrement)];
/* 203 */       this.data[row] = enlarged;
/*     */     }
/* 205 */     else if (column >= current.length) {
/*     */       
/* 207 */       Object[] enlarged = new Object[column + this.columnIncrement];
/* 208 */       System.arraycopy(current, 0, enlarged, 0, current.length);
/* 209 */       this.data[row] = enlarged;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/* 220 */     return this.rows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 230 */     return this.columns;
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
/*     */   protected Object getObject(int row, int column) {
/* 246 */     if (row < this.data.length) {
/*     */       
/* 248 */       Object[] current = this.data[row];
/* 249 */       if (current == null)
/*     */       {
/* 251 */         return null;
/*     */       }
/* 253 */       if (column < current.length)
/*     */       {
/* 255 */         return current[column];
/*     */       }
/*     */     } 
/* 258 */     return null;
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
/*     */   protected void setObject(int row, int column, Object object) {
/* 274 */     ensureCapacity(row, column);
/*     */     
/* 276 */     this.data[row][column] = object;
/* 277 */     this.rows = Math.max(this.rows, row + 1);
/* 278 */     this.columns = Math.max(this.columns, column + 1);
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
/* 291 */     if (o == null)
/*     */     {
/* 293 */       return false;
/*     */     }
/*     */     
/* 296 */     if (this == o)
/*     */     {
/* 298 */       return true;
/*     */     }
/*     */     
/* 301 */     if (!(o instanceof ObjectTable))
/*     */     {
/* 303 */       return false;
/*     */     }
/*     */     
/* 306 */     ObjectTable ot = (ObjectTable)o;
/* 307 */     if (getRowCount() != ot.getRowCount())
/*     */     {
/* 309 */       return false;
/*     */     }
/*     */     
/* 312 */     if (getColumnCount() != ot.getColumnCount())
/*     */     {
/* 314 */       return false;
/*     */     }
/*     */     
/* 317 */     for (int r = 0; r < getRowCount(); r++) {
/*     */       
/* 319 */       for (int c = 0; c < getColumnCount(); c++) {
/*     */         
/* 321 */         if (!ObjectUtilities.equal(getObject(r, c), ot
/* 322 */             .getObject(r, c)))
/*     */         {
/* 324 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 328 */     return true;
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
/* 339 */     int result = this.rows;
/* 340 */     result = 29 * result + this.columns;
/* 341 */     return result;
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
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 353 */     stream.defaultWriteObject();
/* 354 */     int rowCount = this.data.length;
/* 355 */     stream.writeInt(rowCount);
/* 356 */     for (int r = 0; r < rowCount; r++) {
/*     */       
/* 358 */       Object[] column = this.data[r];
/* 359 */       stream.writeBoolean((column != null));
/* 360 */       if (column != null) {
/*     */         
/* 362 */         int columnCount = column.length;
/* 363 */         stream.writeInt(columnCount);
/* 364 */         for (int c = 0; c < columnCount; c++)
/*     */         {
/* 366 */           writeSerializedData(stream, column[c]);
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
/*     */   
/*     */   protected void writeSerializedData(ObjectOutputStream stream, Object o) throws IOException {
/* 383 */     stream.writeObject(o);
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 396 */     stream.defaultReadObject();
/* 397 */     int rowCount = stream.readInt();
/* 398 */     this.data = new Object[rowCount][];
/* 399 */     for (int r = 0; r < rowCount; r++) {
/*     */       
/* 401 */       boolean isNotNull = stream.readBoolean();
/* 402 */       if (isNotNull) {
/*     */         
/* 404 */         int columnCount = stream.readInt();
/* 405 */         Object[] column = new Object[columnCount];
/* 406 */         this.data[r] = column;
/* 407 */         for (int c = 0; c < columnCount; c++)
/*     */         {
/* 409 */           column[c] = readSerializedData(stream);
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
/*     */ 
/*     */   
/*     */   protected Object readSerializedData(ObjectInputStream stream) throws ClassNotFoundException, IOException {
/* 427 */     return stream.readObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 435 */     this.rows = 0;
/* 436 */     this.columns = 0;
/* 437 */     for (int i = 0; i < this.data.length; i++) {
/*     */       
/* 439 */       if (this.data[i] != null)
/*     */       {
/* 441 */         Arrays.fill(this.data[i], (Object)null);
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
/*     */   protected void copyColumn(int oldColumn, int newColumn) {
/* 454 */     for (int i = 0; i < getRowCount(); i++)
/*     */     {
/* 456 */       setObject(i, newColumn, getObject(i, oldColumn));
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
/*     */   protected void copyRow(int oldRow, int newRow) {
/* 469 */     ensureCapacity(newRow, getColumnCount());
/* 470 */     Object[] oldRowStorage = this.data[oldRow];
/* 471 */     if (oldRowStorage == null) {
/*     */       
/* 473 */       Object[] newRowStorage = this.data[newRow];
/* 474 */       if (newRowStorage != null)
/*     */       {
/* 476 */         Arrays.fill(newRowStorage, (Object)null);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 481 */       this.data[newRow] = (Object[])oldRowStorage.clone();
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
/*     */   protected void setData(Object[][] data, int colCount) {
/* 493 */     if (data == null) {
/* 494 */       throw new NullPointerException();
/*     */     }
/* 496 */     if (colCount < 0) {
/* 497 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/* 500 */     this.data = data;
/* 501 */     this.rows = data.length;
/* 502 */     this.columns = colCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object[][] getData() {
/* 512 */     return this.data;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/util/ObjectTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */