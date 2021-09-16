/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.FieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Array2DRowFieldMatrix<T extends FieldElement<T>>
/*     */   extends AbstractFieldMatrix<T>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7260756672015356458L;
/*     */   private T[][] data;
/*     */   
/*     */   public Array2DRowFieldMatrix(Field<T> field) {
/*  58 */     super(field);
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
/*     */   public Array2DRowFieldMatrix(Field<T> field, int rowDimension, int columnDimension) throws NotStrictlyPositiveException {
/*  72 */     super(field, rowDimension, columnDimension);
/*  73 */     this.data = (T[][])MathArrays.buildArray(field, rowDimension, columnDimension);
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
/*     */   public Array2DRowFieldMatrix(T[][] d) throws DimensionMismatchException, NullArgumentException, NoDataException {
/*  92 */     this(extractField(d), d);
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
/*     */   public Array2DRowFieldMatrix(Field<T> field, T[][] d) throws DimensionMismatchException, NullArgumentException, NoDataException {
/* 112 */     super(field);
/* 113 */     copyIn(d);
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
/*     */   
/*     */   public Array2DRowFieldMatrix(T[][] d, boolean copyArray) throws DimensionMismatchException, NoDataException, NullArgumentException {
/* 134 */     this(extractField(d), d, copyArray);
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
/*     */   
/*     */   public Array2DRowFieldMatrix(Field<T> field, T[][] d, boolean copyArray) throws DimensionMismatchException, NoDataException, NullArgumentException {
/* 155 */     super(field);
/* 156 */     if (copyArray) {
/* 157 */       copyIn(d);
/*     */     } else {
/* 159 */       MathUtils.checkNotNull(d);
/* 160 */       int nRows = d.length;
/* 161 */       if (nRows == 0) {
/* 162 */         throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_ROW);
/*     */       }
/* 164 */       int nCols = (d[0]).length;
/* 165 */       if (nCols == 0) {
/* 166 */         throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_COLUMN);
/*     */       }
/* 168 */       for (int r = 1; r < nRows; r++) {
/* 169 */         if ((d[r]).length != nCols) {
/* 170 */           throw new DimensionMismatchException(nCols, (d[r]).length);
/*     */         }
/*     */       } 
/* 173 */       this.data = d;
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
/*     */   public Array2DRowFieldMatrix(T[] v) throws NoDataException {
/* 186 */     this(extractField(v), v);
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
/*     */   public Array2DRowFieldMatrix(Field<T> field, T[] v) {
/* 198 */     super(field);
/* 199 */     int nRows = v.length;
/* 200 */     this.data = (T[][])MathArrays.buildArray(getField(), nRows, 1);
/* 201 */     for (int row = 0; row < nRows; row++) {
/* 202 */       this.data[row][0] = v[row];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldMatrix<T> createMatrix(int rowDimension, int columnDimension) throws NotStrictlyPositiveException {
/* 211 */     return new Array2DRowFieldMatrix(getField(), rowDimension, columnDimension);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldMatrix<T> copy() {
/* 217 */     return new Array2DRowFieldMatrix(getField(), copyOut(), false);
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
/*     */   public Array2DRowFieldMatrix<T> add(Array2DRowFieldMatrix<T> m) throws MatrixDimensionMismatchException {
/* 231 */     checkAdditionCompatible(m);
/*     */     
/* 233 */     int rowCount = getRowDimension();
/* 234 */     int columnCount = getColumnDimension();
/* 235 */     FieldElement[][] arrayOfFieldElement = (FieldElement[][])MathArrays.buildArray(getField(), rowCount, columnCount);
/* 236 */     for (int row = 0; row < rowCount; row++) {
/* 237 */       T[] dataRow = this.data[row];
/* 238 */       T[] mRow = m.data[row];
/* 239 */       FieldElement[] arrayOfFieldElement1 = arrayOfFieldElement[row];
/* 240 */       for (int col = 0; col < columnCount; col++) {
/* 241 */         arrayOfFieldElement1[col] = (FieldElement)dataRow[col].add(mRow[col]);
/*     */       }
/*     */     } 
/*     */     
/* 245 */     return new Array2DRowFieldMatrix(getField(), (T[][])arrayOfFieldElement, false);
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
/*     */   public Array2DRowFieldMatrix<T> subtract(Array2DRowFieldMatrix<T> m) throws MatrixDimensionMismatchException {
/* 259 */     checkSubtractionCompatible(m);
/*     */     
/* 261 */     int rowCount = getRowDimension();
/* 262 */     int columnCount = getColumnDimension();
/* 263 */     FieldElement[][] arrayOfFieldElement = (FieldElement[][])MathArrays.buildArray(getField(), rowCount, columnCount);
/* 264 */     for (int row = 0; row < rowCount; row++) {
/* 265 */       T[] dataRow = this.data[row];
/* 266 */       T[] mRow = m.data[row];
/* 267 */       FieldElement[] arrayOfFieldElement1 = arrayOfFieldElement[row];
/* 268 */       for (int col = 0; col < columnCount; col++) {
/* 269 */         arrayOfFieldElement1[col] = (FieldElement)dataRow[col].subtract(mRow[col]);
/*     */       }
/*     */     } 
/*     */     
/* 273 */     return new Array2DRowFieldMatrix(getField(), (T[][])arrayOfFieldElement, false);
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
/*     */   public Array2DRowFieldMatrix<T> multiply(Array2DRowFieldMatrix<T> m) throws DimensionMismatchException {
/* 288 */     checkMultiplicationCompatible(m);
/*     */     
/* 290 */     int nRows = getRowDimension();
/* 291 */     int nCols = m.getColumnDimension();
/* 292 */     int nSum = getColumnDimension();
/* 293 */     FieldElement[][] arrayOfFieldElement = (FieldElement[][])MathArrays.buildArray(getField(), nRows, nCols);
/* 294 */     for (int row = 0; row < nRows; row++) {
/* 295 */       T[] dataRow = this.data[row];
/* 296 */       FieldElement[] arrayOfFieldElement1 = arrayOfFieldElement[row];
/* 297 */       for (int col = 0; col < nCols; col++) {
/* 298 */         FieldElement fieldElement = (FieldElement)getField().getZero();
/* 299 */         for (int i = 0; i < nSum; i++) {
/* 300 */           fieldElement = (FieldElement)fieldElement.add(dataRow[i].multiply(m.data[i][col]));
/*     */         }
/* 302 */         arrayOfFieldElement1[col] = fieldElement;
/*     */       } 
/*     */     } 
/*     */     
/* 306 */     return new Array2DRowFieldMatrix(getField(), (T[][])arrayOfFieldElement, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T[][] getData() {
/* 313 */     return copyOut();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T[][] getDataRef() {
/* 323 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubMatrix(T[][] subMatrix, int row, int column) throws OutOfRangeException, NullArgumentException, NoDataException, DimensionMismatchException {
/* 332 */     if (this.data == null) {
/* 333 */       if (row > 0) {
/* 334 */         throw new MathIllegalStateException(LocalizedFormats.FIRST_ROWS_NOT_INITIALIZED_YET, new Object[] { Integer.valueOf(row) });
/*     */       }
/* 336 */       if (column > 0) {
/* 337 */         throw new MathIllegalStateException(LocalizedFormats.FIRST_COLUMNS_NOT_INITIALIZED_YET, new Object[] { Integer.valueOf(column) });
/*     */       }
/* 339 */       int nRows = subMatrix.length;
/* 340 */       if (nRows == 0) {
/* 341 */         throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_ROW);
/*     */       }
/*     */       
/* 344 */       int nCols = (subMatrix[0]).length;
/* 345 */       if (nCols == 0) {
/* 346 */         throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_COLUMN);
/*     */       }
/* 348 */       this.data = (T[][])MathArrays.buildArray(getField(), subMatrix.length, nCols);
/* 349 */       for (int i = 0; i < this.data.length; i++) {
/* 350 */         if ((subMatrix[i]).length != nCols) {
/* 351 */           throw new DimensionMismatchException(nCols, (subMatrix[i]).length);
/*     */         }
/* 353 */         System.arraycopy(subMatrix[i], 0, this.data[i + row], column, nCols);
/*     */       } 
/*     */     } else {
/* 356 */       super.setSubMatrix(subMatrix, row, column);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getEntry(int row, int column) throws OutOfRangeException {
/* 365 */     checkRowIndex(row);
/* 366 */     checkColumnIndex(column);
/*     */     
/* 368 */     return this.data[row][column];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntry(int row, int column, T value) throws OutOfRangeException {
/* 375 */     checkRowIndex(row);
/* 376 */     checkColumnIndex(column);
/*     */     
/* 378 */     this.data[row][column] = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToEntry(int row, int column, T increment) throws OutOfRangeException {
/* 385 */     checkRowIndex(row);
/* 386 */     checkColumnIndex(column);
/*     */     
/* 388 */     this.data[row][column] = (T)this.data[row][column].add(increment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyEntry(int row, int column, T factor) throws OutOfRangeException {
/* 395 */     checkRowIndex(row);
/* 396 */     checkColumnIndex(column);
/*     */     
/* 398 */     this.data[row][column] = (T)this.data[row][column].multiply(factor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowDimension() {
/* 404 */     return (this.data == null) ? 0 : this.data.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnDimension() {
/* 410 */     return (this.data == null || this.data[0] == null) ? 0 : (this.data[0]).length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T[] operate(T[] v) throws DimensionMismatchException {
/* 416 */     int nRows = getRowDimension();
/* 417 */     int nCols = getColumnDimension();
/* 418 */     if (v.length != nCols) {
/* 419 */       throw new DimensionMismatchException(v.length, nCols);
/*     */     }
/* 421 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(getField(), nRows);
/* 422 */     for (int row = 0; row < nRows; row++) {
/* 423 */       T[] dataRow = this.data[row];
/* 424 */       FieldElement fieldElement = (FieldElement)getField().getZero();
/* 425 */       for (int i = 0; i < nCols; i++) {
/* 426 */         fieldElement = (FieldElement)fieldElement.add(dataRow[i].multiply(v[i]));
/*     */       }
/* 428 */       arrayOfFieldElement[row] = fieldElement;
/*     */     } 
/* 430 */     return (T[])arrayOfFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T[] preMultiply(T[] v) throws DimensionMismatchException {
/* 436 */     int nRows = getRowDimension();
/* 437 */     int nCols = getColumnDimension();
/* 438 */     if (v.length != nRows) {
/* 439 */       throw new DimensionMismatchException(v.length, nRows);
/*     */     }
/*     */     
/* 442 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(getField(), nCols);
/* 443 */     for (int col = 0; col < nCols; col++) {
/* 444 */       FieldElement fieldElement = (FieldElement)getField().getZero();
/* 445 */       for (int i = 0; i < nRows; i++) {
/* 446 */         fieldElement = (FieldElement)fieldElement.add(this.data[i][col].multiply(v[i]));
/*     */       }
/* 448 */       arrayOfFieldElement[col] = fieldElement;
/*     */     } 
/*     */     
/* 451 */     return (T[])arrayOfFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T walkInRowOrder(FieldMatrixChangingVisitor<T> visitor) {
/* 457 */     int rows = getRowDimension();
/* 458 */     int columns = getColumnDimension();
/* 459 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/* 460 */     for (int i = 0; i < rows; i++) {
/* 461 */       T[] rowI = this.data[i];
/* 462 */       for (int j = 0; j < columns; j++) {
/* 463 */         rowI[j] = visitor.visit(i, j, rowI[j]);
/*     */       }
/*     */     } 
/* 466 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T walkInRowOrder(FieldMatrixPreservingVisitor<T> visitor) {
/* 472 */     int rows = getRowDimension();
/* 473 */     int columns = getColumnDimension();
/* 474 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/* 475 */     for (int i = 0; i < rows; i++) {
/* 476 */       T[] rowI = this.data[i];
/* 477 */       for (int j = 0; j < columns; j++) {
/* 478 */         visitor.visit(i, j, rowI[j]);
/*     */       }
/*     */     } 
/* 481 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T walkInRowOrder(FieldMatrixChangingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 490 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/* 491 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*     */     
/* 493 */     for (int i = startRow; i <= endRow; i++) {
/* 494 */       T[] rowI = this.data[i];
/* 495 */       for (int j = startColumn; j <= endColumn; j++) {
/* 496 */         rowI[j] = visitor.visit(i, j, rowI[j]);
/*     */       }
/*     */     } 
/* 499 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T walkInRowOrder(FieldMatrixPreservingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 508 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/* 509 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*     */     
/* 511 */     for (int i = startRow; i <= endRow; i++) {
/* 512 */       T[] rowI = this.data[i];
/* 513 */       for (int j = startColumn; j <= endColumn; j++) {
/* 514 */         visitor.visit(i, j, rowI[j]);
/*     */       }
/*     */     } 
/* 517 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T walkInColumnOrder(FieldMatrixChangingVisitor<T> visitor) {
/* 523 */     int rows = getRowDimension();
/* 524 */     int columns = getColumnDimension();
/* 525 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/* 526 */     for (int j = 0; j < columns; j++) {
/* 527 */       for (int i = 0; i < rows; i++) {
/* 528 */         T[] rowI = this.data[i];
/* 529 */         rowI[j] = visitor.visit(i, j, rowI[j]);
/*     */       } 
/*     */     } 
/* 532 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T walkInColumnOrder(FieldMatrixPreservingVisitor<T> visitor) {
/* 538 */     int rows = getRowDimension();
/* 539 */     int columns = getColumnDimension();
/* 540 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/* 541 */     for (int j = 0; j < columns; j++) {
/* 542 */       for (int i = 0; i < rows; i++) {
/* 543 */         visitor.visit(i, j, this.data[i][j]);
/*     */       }
/*     */     } 
/* 546 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T walkInColumnOrder(FieldMatrixChangingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 555 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/* 556 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*     */     
/* 558 */     for (int j = startColumn; j <= endColumn; j++) {
/* 559 */       for (int i = startRow; i <= endRow; i++) {
/* 560 */         T[] rowI = this.data[i];
/* 561 */         rowI[j] = visitor.visit(i, j, rowI[j]);
/*     */       } 
/*     */     } 
/* 564 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T walkInColumnOrder(FieldMatrixPreservingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 573 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/* 574 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*     */     
/* 576 */     for (int j = startColumn; j <= endColumn; j++) {
/* 577 */       for (int i = startRow; i <= endRow; i++) {
/* 578 */         visitor.visit(i, j, this.data[i][j]);
/*     */       }
/*     */     } 
/* 581 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private T[][] copyOut() {
/* 590 */     int nRows = getRowDimension();
/* 591 */     FieldElement[][] arrayOfFieldElement = (FieldElement[][])MathArrays.buildArray(getField(), nRows, getColumnDimension());
/*     */     
/* 593 */     for (int i = 0; i < nRows; i++) {
/* 594 */       System.arraycopy(this.data[i], 0, arrayOfFieldElement[i], 0, (this.data[i]).length);
/*     */     }
/* 596 */     return (T[][])arrayOfFieldElement;
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
/*     */   private void copyIn(T[][] in) throws NullArgumentException, NoDataException, DimensionMismatchException {
/* 610 */     setSubMatrix(in, 0, 0);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/Array2DRowFieldMatrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */