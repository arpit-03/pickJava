/*      */ package org.apache.commons.math3.linear;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import org.apache.commons.math3.Field;
/*      */ import org.apache.commons.math3.FieldElement;
/*      */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*      */ import org.apache.commons.math3.exception.NoDataException;
/*      */ import org.apache.commons.math3.exception.NotPositiveException;
/*      */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*      */ import org.apache.commons.math3.exception.NullArgumentException;
/*      */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*      */ import org.apache.commons.math3.exception.OutOfRangeException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.util.MathArrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractFieldMatrix<T extends FieldElement<T>>
/*      */   implements FieldMatrix<T>
/*      */ {
/*      */   private final Field<T> field;
/*      */   
/*      */   protected AbstractFieldMatrix() {
/*   52 */     this.field = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractFieldMatrix(Field<T> field) {
/*   60 */     this.field = field;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractFieldMatrix(Field<T> field, int rowDimension, int columnDimension) throws NotStrictlyPositiveException {
/*   76 */     if (rowDimension <= 0) {
/*   77 */       throw new NotStrictlyPositiveException(LocalizedFormats.DIMENSION, Integer.valueOf(rowDimension));
/*      */     }
/*      */     
/*   80 */     if (columnDimension <= 0) {
/*   81 */       throw new NotStrictlyPositiveException(LocalizedFormats.DIMENSION, Integer.valueOf(columnDimension));
/*      */     }
/*      */     
/*   84 */     this.field = field;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends FieldElement<T>> Field<T> extractField(T[][] d) throws NoDataException, NullArgumentException {
/*   98 */     if (d == null) {
/*   99 */       throw new NullArgumentException();
/*      */     }
/*  101 */     if (d.length == 0) {
/*  102 */       throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_ROW);
/*      */     }
/*  104 */     if ((d[0]).length == 0) {
/*  105 */       throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_COLUMN);
/*      */     }
/*  107 */     return d[0][0].getField();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static <T extends FieldElement<T>> Field<T> extractField(T[] d) throws NoDataException {
/*  120 */     if (d.length == 0) {
/*  121 */       throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_ROW);
/*      */     }
/*  123 */     return d[0].getField();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected static <T extends FieldElement<T>> T[][] buildArray(Field<T> field, int rows, int columns) {
/*  142 */     return (T[][])MathArrays.buildArray(field, rows, columns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected static <T extends FieldElement<T>> T[] buildArray(Field<T> field, int length) {
/*  158 */     return (T[])MathArrays.buildArray(field, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public Field<T> getField() {
/*  163 */     return this.field;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract FieldMatrix<T> createMatrix(int paramInt1, int paramInt2) throws NotStrictlyPositiveException;
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract FieldMatrix<T> copy();
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> add(FieldMatrix<T> m) throws MatrixDimensionMismatchException {
/*  178 */     checkAdditionCompatible(m);
/*      */     
/*  180 */     int rowCount = getRowDimension();
/*  181 */     int columnCount = getColumnDimension();
/*  182 */     FieldMatrix<T> out = createMatrix(rowCount, columnCount);
/*  183 */     for (int row = 0; row < rowCount; row++) {
/*  184 */       for (int col = 0; col < columnCount; col++) {
/*  185 */         out.setEntry(row, col, (T)getEntry(row, col).add(m.getEntry(row, col)));
/*      */       }
/*      */     } 
/*      */     
/*  189 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> subtract(FieldMatrix<T> m) throws MatrixDimensionMismatchException {
/*  196 */     checkSubtractionCompatible(m);
/*      */     
/*  198 */     int rowCount = getRowDimension();
/*  199 */     int columnCount = getColumnDimension();
/*  200 */     FieldMatrix<T> out = createMatrix(rowCount, columnCount);
/*  201 */     for (int row = 0; row < rowCount; row++) {
/*  202 */       for (int col = 0; col < columnCount; col++) {
/*  203 */         out.setEntry(row, col, (T)getEntry(row, col).subtract(m.getEntry(row, col)));
/*      */       }
/*      */     } 
/*      */     
/*  207 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> scalarAdd(T d) {
/*  213 */     int rowCount = getRowDimension();
/*  214 */     int columnCount = getColumnDimension();
/*  215 */     FieldMatrix<T> out = createMatrix(rowCount, columnCount);
/*  216 */     for (int row = 0; row < rowCount; row++) {
/*  217 */       for (int col = 0; col < columnCount; col++) {
/*  218 */         out.setEntry(row, col, (T)getEntry(row, col).add(d));
/*      */       }
/*      */     } 
/*      */     
/*  222 */     return out;
/*      */   }
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> scalarMultiply(T d) {
/*  227 */     int rowCount = getRowDimension();
/*  228 */     int columnCount = getColumnDimension();
/*  229 */     FieldMatrix<T> out = createMatrix(rowCount, columnCount);
/*  230 */     for (int row = 0; row < rowCount; row++) {
/*  231 */       for (int col = 0; col < columnCount; col++) {
/*  232 */         out.setEntry(row, col, (T)getEntry(row, col).multiply(d));
/*      */       }
/*      */     } 
/*      */     
/*  236 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> multiply(FieldMatrix<T> m) throws DimensionMismatchException {
/*  243 */     checkMultiplicationCompatible(m);
/*      */     
/*  245 */     int nRows = getRowDimension();
/*  246 */     int nCols = m.getColumnDimension();
/*  247 */     int nSum = getColumnDimension();
/*  248 */     FieldMatrix<T> out = createMatrix(nRows, nCols);
/*  249 */     for (int row = 0; row < nRows; row++) {
/*  250 */       for (int col = 0; col < nCols; col++) {
/*  251 */         FieldElement fieldElement = (FieldElement)this.field.getZero();
/*  252 */         for (int i = 0; i < nSum; i++) {
/*  253 */           fieldElement = (FieldElement)fieldElement.add(getEntry(row, i).multiply(m.getEntry(i, col)));
/*      */         }
/*  255 */         out.setEntry(row, col, (T)fieldElement);
/*      */       } 
/*      */     } 
/*      */     
/*  259 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> preMultiply(FieldMatrix<T> m) throws DimensionMismatchException {
/*  265 */     return m.multiply(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> power(int p) throws NonSquareMatrixException, NotPositiveException {
/*  271 */     if (p < 0) {
/*  272 */       throw new NotPositiveException(Integer.valueOf(p));
/*      */     }
/*      */     
/*  275 */     if (!isSquare()) {
/*  276 */       throw new NonSquareMatrixException(getRowDimension(), getColumnDimension());
/*      */     }
/*      */     
/*  279 */     if (p == 0) {
/*  280 */       return MatrixUtils.createFieldIdentityMatrix(getField(), getRowDimension());
/*      */     }
/*      */     
/*  283 */     if (p == 1) {
/*  284 */       return copy();
/*      */     }
/*      */     
/*  287 */     int power = p - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  296 */     char[] binaryRepresentation = Integer.toBinaryString(power).toCharArray();
/*      */     
/*  298 */     ArrayList<Integer> nonZeroPositions = new ArrayList<Integer>();
/*      */     
/*  300 */     for (int i = 0; i < binaryRepresentation.length; i++) {
/*  301 */       if (binaryRepresentation[i] == '1') {
/*  302 */         int pos = binaryRepresentation.length - i - 1;
/*  303 */         nonZeroPositions.add(Integer.valueOf(pos));
/*      */       } 
/*      */     } 
/*      */     
/*  307 */     ArrayList<FieldMatrix<T>> results = new ArrayList<FieldMatrix<T>>(binaryRepresentation.length);
/*      */ 
/*      */     
/*  310 */     results.add(0, copy());
/*      */     
/*  312 */     for (int j = 1; j < binaryRepresentation.length; j++) {
/*  313 */       FieldMatrix<T> s = results.get(j - 1);
/*  314 */       FieldMatrix<T> r = s.multiply(s);
/*  315 */       results.add(j, r);
/*      */     } 
/*      */     
/*  318 */     FieldMatrix<T> result = copy();
/*      */     
/*  320 */     for (Integer integer : nonZeroPositions) {
/*  321 */       result = result.multiply(results.get(integer.intValue()));
/*      */     }
/*      */     
/*  324 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public T[][] getData() {
/*  329 */     FieldElement[][] arrayOfFieldElement = (FieldElement[][])MathArrays.buildArray(this.field, getRowDimension(), getColumnDimension());
/*      */     
/*  331 */     for (int i = 0; i < arrayOfFieldElement.length; i++) {
/*  332 */       FieldElement[] arrayOfFieldElement1 = arrayOfFieldElement[i];
/*  333 */       for (int j = 0; j < arrayOfFieldElement1.length; j++) {
/*  334 */         arrayOfFieldElement1[j] = (FieldElement)getEntry(i, j);
/*      */       }
/*      */     } 
/*      */     
/*  338 */     return (T[][])arrayOfFieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> getSubMatrix(int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
/*  345 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/*      */     
/*  347 */     FieldMatrix<T> subMatrix = createMatrix(endRow - startRow + 1, endColumn - startColumn + 1);
/*      */     
/*  349 */     for (int i = startRow; i <= endRow; i++) {
/*  350 */       for (int j = startColumn; j <= endColumn; j++) {
/*  351 */         subMatrix.setEntry(i - startRow, j - startColumn, getEntry(i, j));
/*      */       }
/*      */     } 
/*      */     
/*  355 */     return subMatrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> getSubMatrix(final int[] selectedRows, final int[] selectedColumns) throws NoDataException, NullArgumentException, OutOfRangeException {
/*  365 */     checkSubMatrixIndex(selectedRows, selectedColumns);
/*      */ 
/*      */     
/*  368 */     FieldMatrix<T> subMatrix = createMatrix(selectedRows.length, selectedColumns.length);
/*      */     
/*  370 */     subMatrix.walkInOptimizedOrder(new DefaultFieldMatrixChangingVisitor<T>((FieldElement)this.field.getZero())
/*      */         {
/*      */           
/*      */           public T visit(int row, int column, T value)
/*      */           {
/*  375 */             return AbstractFieldMatrix.this.getEntry(selectedRows[row], selectedColumns[column]);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  380 */     return subMatrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copySubMatrix(int startRow, int endRow, int startColumn, int endColumn, final T[][] destination) throws MatrixDimensionMismatchException, NumberIsTooSmallException, OutOfRangeException {
/*  391 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/*  392 */     int rowsCount = endRow + 1 - startRow;
/*  393 */     int columnsCount = endColumn + 1 - startColumn;
/*  394 */     if (destination.length < rowsCount || (destination[0]).length < columnsCount) {
/*  395 */       throw new MatrixDimensionMismatchException(destination.length, (destination[0]).length, rowsCount, columnsCount);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  402 */     walkInOptimizedOrder(new DefaultFieldMatrixPreservingVisitor<T>((FieldElement)this.field.getZero())
/*      */         {
/*      */           private int startRow;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           private int startColumn;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
/*  415 */             this.startRow = startRow;
/*  416 */             this.startColumn = startColumn;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void visit(int row, int column, T value) {
/*  422 */             destination[row - this.startRow][column - this.startColumn] = (FieldElement)value;
/*      */           }
/*      */         }startRow, endRow, startColumn, endColumn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copySubMatrix(int[] selectedRows, int[] selectedColumns, T[][] destination) throws MatrixDimensionMismatchException, NoDataException, NullArgumentException, OutOfRangeException {
/*  434 */     checkSubMatrixIndex(selectedRows, selectedColumns);
/*  435 */     if (destination.length < selectedRows.length || (destination[0]).length < selectedColumns.length)
/*      */     {
/*  437 */       throw new MatrixDimensionMismatchException(destination.length, (destination[0]).length, selectedRows.length, selectedColumns.length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  444 */     for (int i = 0; i < selectedRows.length; i++) {
/*  445 */       T[] destinationI = destination[i];
/*  446 */       for (int j = 0; j < selectedColumns.length; j++) {
/*  447 */         destinationI[j] = getEntry(selectedRows[i], selectedColumns[j]);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSubMatrix(T[][] subMatrix, int row, int column) throws DimensionMismatchException, OutOfRangeException, NoDataException, NullArgumentException {
/*  458 */     if (subMatrix == null) {
/*  459 */       throw new NullArgumentException();
/*      */     }
/*  461 */     int nRows = subMatrix.length;
/*  462 */     if (nRows == 0) {
/*  463 */       throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_ROW);
/*      */     }
/*      */     
/*  466 */     int nCols = (subMatrix[0]).length;
/*  467 */     if (nCols == 0) {
/*  468 */       throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_COLUMN);
/*      */     }
/*      */     
/*  471 */     for (int r = 1; r < nRows; r++) {
/*  472 */       if ((subMatrix[r]).length != nCols) {
/*  473 */         throw new DimensionMismatchException(nCols, (subMatrix[r]).length);
/*      */       }
/*      */     } 
/*      */     
/*  477 */     checkRowIndex(row);
/*  478 */     checkColumnIndex(column);
/*  479 */     checkRowIndex(nRows + row - 1);
/*  480 */     checkColumnIndex(nCols + column - 1);
/*      */     
/*  482 */     for (int i = 0; i < nRows; i++) {
/*  483 */       for (int j = 0; j < nCols; j++) {
/*  484 */         setEntry(row + i, column + j, subMatrix[i][j]);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> getRowMatrix(int row) throws OutOfRangeException {
/*  491 */     checkRowIndex(row);
/*  492 */     int nCols = getColumnDimension();
/*  493 */     FieldMatrix<T> out = createMatrix(1, nCols);
/*  494 */     for (int i = 0; i < nCols; i++) {
/*  495 */       out.setEntry(0, i, getEntry(row, i));
/*      */     }
/*      */     
/*  498 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRowMatrix(int row, FieldMatrix<T> matrix) throws OutOfRangeException, MatrixDimensionMismatchException {
/*  505 */     checkRowIndex(row);
/*  506 */     int nCols = getColumnDimension();
/*  507 */     if (matrix.getRowDimension() != 1 || matrix.getColumnDimension() != nCols)
/*      */     {
/*  509 */       throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), 1, nCols);
/*      */     }
/*      */ 
/*      */     
/*  513 */     for (int i = 0; i < nCols; i++) {
/*  514 */       setEntry(row, i, matrix.getEntry(0, i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> getColumnMatrix(int column) throws OutOfRangeException {
/*  523 */     checkColumnIndex(column);
/*  524 */     int nRows = getRowDimension();
/*  525 */     FieldMatrix<T> out = createMatrix(nRows, 1);
/*  526 */     for (int i = 0; i < nRows; i++) {
/*  527 */       out.setEntry(i, 0, getEntry(i, column));
/*      */     }
/*      */     
/*  530 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumnMatrix(int column, FieldMatrix<T> matrix) throws OutOfRangeException, MatrixDimensionMismatchException {
/*  537 */     checkColumnIndex(column);
/*  538 */     int nRows = getRowDimension();
/*  539 */     if (matrix.getRowDimension() != nRows || matrix.getColumnDimension() != 1)
/*      */     {
/*  541 */       throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), nRows, 1);
/*      */     }
/*      */ 
/*      */     
/*  545 */     for (int i = 0; i < nRows; i++) {
/*  546 */       setEntry(i, column, matrix.getEntry(i, 0));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector<T> getRowVector(int row) throws OutOfRangeException {
/*  554 */     return new ArrayFieldVector<T>(this.field, getRow(row), false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRowVector(int row, FieldVector<T> vector) throws OutOfRangeException, MatrixDimensionMismatchException {
/*  560 */     checkRowIndex(row);
/*  561 */     int nCols = getColumnDimension();
/*  562 */     if (vector.getDimension() != nCols) {
/*  563 */       throw new MatrixDimensionMismatchException(1, vector.getDimension(), 1, nCols);
/*      */     }
/*      */     
/*  566 */     for (int i = 0; i < nCols; i++) {
/*  567 */       setEntry(row, i, vector.getEntry(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector<T> getColumnVector(int column) throws OutOfRangeException {
/*  575 */     return new ArrayFieldVector<T>(this.field, getColumn(column), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumnVector(int column, FieldVector<T> vector) throws OutOfRangeException, MatrixDimensionMismatchException {
/*  582 */     checkColumnIndex(column);
/*  583 */     int nRows = getRowDimension();
/*  584 */     if (vector.getDimension() != nRows) {
/*  585 */       throw new MatrixDimensionMismatchException(vector.getDimension(), 1, nRows, 1);
/*      */     }
/*      */     
/*  588 */     for (int i = 0; i < nRows; i++) {
/*  589 */       setEntry(i, column, vector.getEntry(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public T[] getRow(int row) throws OutOfRangeException {
/*  596 */     checkRowIndex(row);
/*  597 */     int nCols = getColumnDimension();
/*  598 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, nCols);
/*  599 */     for (int i = 0; i < nCols; i++) {
/*  600 */       arrayOfFieldElement[i] = (FieldElement)getEntry(row, i);
/*      */     }
/*      */     
/*  603 */     return (T[])arrayOfFieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRow(int row, T[] array) throws OutOfRangeException, MatrixDimensionMismatchException {
/*  610 */     checkRowIndex(row);
/*  611 */     int nCols = getColumnDimension();
/*  612 */     if (array.length != nCols) {
/*  613 */       throw new MatrixDimensionMismatchException(1, array.length, 1, nCols);
/*      */     }
/*  615 */     for (int i = 0; i < nCols; i++) {
/*  616 */       setEntry(row, i, array[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public T[] getColumn(int column) throws OutOfRangeException {
/*  623 */     checkColumnIndex(column);
/*  624 */     int nRows = getRowDimension();
/*  625 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, nRows);
/*  626 */     for (int i = 0; i < nRows; i++) {
/*  627 */       arrayOfFieldElement[i] = (FieldElement)getEntry(i, column);
/*      */     }
/*      */     
/*  630 */     return (T[])arrayOfFieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumn(int column, T[] array) throws OutOfRangeException, MatrixDimensionMismatchException {
/*  637 */     checkColumnIndex(column);
/*  638 */     int nRows = getRowDimension();
/*  639 */     if (array.length != nRows) {
/*  640 */       throw new MatrixDimensionMismatchException(array.length, 1, nRows, 1);
/*      */     }
/*  642 */     for (int i = 0; i < nRows; i++) {
/*  643 */       setEntry(i, column, array[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public abstract T getEntry(int paramInt1, int paramInt2) throws OutOfRangeException;
/*      */ 
/*      */   
/*      */   public abstract void setEntry(int paramInt1, int paramInt2, T paramT) throws OutOfRangeException;
/*      */ 
/*      */   
/*      */   public abstract void addToEntry(int paramInt1, int paramInt2, T paramT) throws OutOfRangeException;
/*      */ 
/*      */   
/*      */   public abstract void multiplyEntry(int paramInt1, int paramInt2, T paramT) throws OutOfRangeException;
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> transpose() {
/*  661 */     int nRows = getRowDimension();
/*  662 */     int nCols = getColumnDimension();
/*  663 */     final FieldMatrix<T> out = createMatrix(nCols, nRows);
/*  664 */     walkInOptimizedOrder(new DefaultFieldMatrixPreservingVisitor<T>((FieldElement)this.field.getZero())
/*      */         {
/*      */           public void visit(int row, int column, T value)
/*      */           {
/*  668 */             out.setEntry(column, row, value);
/*      */           }
/*      */         });
/*      */     
/*  672 */     return out;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSquare() {
/*  677 */     return (getColumnDimension() == getRowDimension());
/*      */   }
/*      */ 
/*      */   
/*      */   public abstract int getRowDimension();
/*      */ 
/*      */   
/*      */   public abstract int getColumnDimension();
/*      */ 
/*      */   
/*      */   public T getTrace() throws NonSquareMatrixException {
/*  688 */     int nRows = getRowDimension();
/*  689 */     int nCols = getColumnDimension();
/*  690 */     if (nRows != nCols) {
/*  691 */       throw new NonSquareMatrixException(nRows, nCols);
/*      */     }
/*  693 */     FieldElement fieldElement = (FieldElement)this.field.getZero();
/*  694 */     for (int i = 0; i < nRows; i++) {
/*  695 */       fieldElement = (FieldElement)fieldElement.add(getEntry(i, i));
/*      */     }
/*  697 */     return (T)fieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public T[] operate(T[] v) throws DimensionMismatchException {
/*  703 */     int nRows = getRowDimension();
/*  704 */     int nCols = getColumnDimension();
/*  705 */     if (v.length != nCols) {
/*  706 */       throw new DimensionMismatchException(v.length, nCols);
/*      */     }
/*      */     
/*  709 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, nRows);
/*  710 */     for (int row = 0; row < nRows; row++) {
/*  711 */       FieldElement fieldElement = (FieldElement)this.field.getZero();
/*  712 */       for (int i = 0; i < nCols; i++) {
/*  713 */         fieldElement = (FieldElement)fieldElement.add(getEntry(row, i).multiply(v[i]));
/*      */       }
/*  715 */       arrayOfFieldElement[row] = fieldElement;
/*      */     } 
/*      */     
/*  718 */     return (T[])arrayOfFieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector<T> operate(FieldVector<T> v) throws DimensionMismatchException {
/*      */     try {
/*  725 */       return new ArrayFieldVector<T>(this.field, operate(((ArrayFieldVector<T>)v).getDataRef()), false);
/*  726 */     } catch (ClassCastException cce) {
/*  727 */       int nRows = getRowDimension();
/*  728 */       int nCols = getColumnDimension();
/*  729 */       if (v.getDimension() != nCols) {
/*  730 */         throw new DimensionMismatchException(v.getDimension(), nCols);
/*      */       }
/*      */       
/*  733 */       FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, nRows);
/*  734 */       for (int row = 0; row < nRows; row++) {
/*  735 */         FieldElement fieldElement = (FieldElement)this.field.getZero();
/*  736 */         for (int i = 0; i < nCols; i++) {
/*  737 */           fieldElement = (FieldElement)fieldElement.add(getEntry(row, i).multiply(v.getEntry(i)));
/*      */         }
/*  739 */         arrayOfFieldElement[row] = fieldElement;
/*      */       } 
/*      */       
/*  742 */       return new ArrayFieldVector<T>(this.field, (T[])arrayOfFieldElement, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public T[] preMultiply(T[] v) throws DimensionMismatchException {
/*  749 */     int nRows = getRowDimension();
/*  750 */     int nCols = getColumnDimension();
/*  751 */     if (v.length != nRows) {
/*  752 */       throw new DimensionMismatchException(v.length, nRows);
/*      */     }
/*      */     
/*  755 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, nCols);
/*  756 */     for (int col = 0; col < nCols; col++) {
/*  757 */       FieldElement fieldElement = (FieldElement)this.field.getZero();
/*  758 */       for (int i = 0; i < nRows; i++) {
/*  759 */         fieldElement = (FieldElement)fieldElement.add(getEntry(i, col).multiply(v[i]));
/*      */       }
/*  761 */       arrayOfFieldElement[col] = fieldElement;
/*      */     } 
/*      */     
/*  764 */     return (T[])arrayOfFieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector<T> preMultiply(FieldVector<T> v) throws DimensionMismatchException {
/*      */     try {
/*  771 */       return new ArrayFieldVector<T>(this.field, preMultiply(((ArrayFieldVector<T>)v).getDataRef()), false);
/*  772 */     } catch (ClassCastException cce) {
/*  773 */       int nRows = getRowDimension();
/*  774 */       int nCols = getColumnDimension();
/*  775 */       if (v.getDimension() != nRows) {
/*  776 */         throw new DimensionMismatchException(v.getDimension(), nRows);
/*      */       }
/*      */       
/*  779 */       FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, nCols);
/*  780 */       for (int col = 0; col < nCols; col++) {
/*  781 */         FieldElement fieldElement = (FieldElement)this.field.getZero();
/*  782 */         for (int i = 0; i < nRows; i++) {
/*  783 */           fieldElement = (FieldElement)fieldElement.add(getEntry(i, col).multiply(v.getEntry(i)));
/*      */         }
/*  785 */         arrayOfFieldElement[col] = fieldElement;
/*      */       } 
/*      */       
/*  788 */       return new ArrayFieldVector<T>(this.field, (T[])arrayOfFieldElement, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public T walkInRowOrder(FieldMatrixChangingVisitor<T> visitor) {
/*  794 */     int rows = getRowDimension();
/*  795 */     int columns = getColumnDimension();
/*  796 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/*  797 */     for (int row = 0; row < rows; row++) {
/*  798 */       for (int column = 0; column < columns; column++) {
/*  799 */         T oldValue = getEntry(row, column);
/*  800 */         T newValue = visitor.visit(row, column, oldValue);
/*  801 */         setEntry(row, column, newValue);
/*      */       } 
/*      */     } 
/*  804 */     return visitor.end();
/*      */   }
/*      */ 
/*      */   
/*      */   public T walkInRowOrder(FieldMatrixPreservingVisitor<T> visitor) {
/*  809 */     int rows = getRowDimension();
/*  810 */     int columns = getColumnDimension();
/*  811 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/*  812 */     for (int row = 0; row < rows; row++) {
/*  813 */       for (int column = 0; column < columns; column++) {
/*  814 */         visitor.visit(row, column, getEntry(row, column));
/*      */       }
/*      */     } 
/*  817 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T walkInRowOrder(FieldMatrixChangingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
/*  825 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/*  826 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*      */     
/*  828 */     for (int row = startRow; row <= endRow; row++) {
/*  829 */       for (int column = startColumn; column <= endColumn; column++) {
/*  830 */         T oldValue = getEntry(row, column);
/*  831 */         T newValue = visitor.visit(row, column, oldValue);
/*  832 */         setEntry(row, column, newValue);
/*      */       } 
/*      */     } 
/*  835 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T walkInRowOrder(FieldMatrixPreservingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
/*  843 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/*  844 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*      */     
/*  846 */     for (int row = startRow; row <= endRow; row++) {
/*  847 */       for (int column = startColumn; column <= endColumn; column++) {
/*  848 */         visitor.visit(row, column, getEntry(row, column));
/*      */       }
/*      */     } 
/*  851 */     return visitor.end();
/*      */   }
/*      */ 
/*      */   
/*      */   public T walkInColumnOrder(FieldMatrixChangingVisitor<T> visitor) {
/*  856 */     int rows = getRowDimension();
/*  857 */     int columns = getColumnDimension();
/*  858 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/*  859 */     for (int column = 0; column < columns; column++) {
/*  860 */       for (int row = 0; row < rows; row++) {
/*  861 */         T oldValue = getEntry(row, column);
/*  862 */         T newValue = visitor.visit(row, column, oldValue);
/*  863 */         setEntry(row, column, newValue);
/*      */       } 
/*      */     } 
/*  866 */     return visitor.end();
/*      */   }
/*      */ 
/*      */   
/*      */   public T walkInColumnOrder(FieldMatrixPreservingVisitor<T> visitor) {
/*  871 */     int rows = getRowDimension();
/*  872 */     int columns = getColumnDimension();
/*  873 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/*  874 */     for (int column = 0; column < columns; column++) {
/*  875 */       for (int row = 0; row < rows; row++) {
/*  876 */         visitor.visit(row, column, getEntry(row, column));
/*      */       }
/*      */     } 
/*  879 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T walkInColumnOrder(FieldMatrixChangingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
/*  887 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/*  888 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*      */     
/*  890 */     for (int column = startColumn; column <= endColumn; column++) {
/*  891 */       for (int row = startRow; row <= endRow; row++) {
/*  892 */         T oldValue = getEntry(row, column);
/*  893 */         T newValue = visitor.visit(row, column, oldValue);
/*  894 */         setEntry(row, column, newValue);
/*      */       } 
/*      */     } 
/*  897 */     return visitor.end();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T walkInColumnOrder(FieldMatrixPreservingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
/*  905 */     checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
/*  906 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*      */     
/*  908 */     for (int column = startColumn; column <= endColumn; column++) {
/*  909 */       for (int row = startRow; row <= endRow; row++) {
/*  910 */         visitor.visit(row, column, getEntry(row, column));
/*      */       }
/*      */     } 
/*  913 */     return visitor.end();
/*      */   }
/*      */ 
/*      */   
/*      */   public T walkInOptimizedOrder(FieldMatrixChangingVisitor<T> visitor) {
/*  918 */     return walkInRowOrder(visitor);
/*      */   }
/*      */ 
/*      */   
/*      */   public T walkInOptimizedOrder(FieldMatrixPreservingVisitor<T> visitor) {
/*  923 */     return walkInRowOrder(visitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T walkInOptimizedOrder(FieldMatrixChangingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
/*  931 */     return walkInRowOrder(visitor, startRow, endRow, startColumn, endColumn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T walkInOptimizedOrder(FieldMatrixPreservingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
/*  939 */     return walkInRowOrder(visitor, startRow, endRow, startColumn, endColumn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  948 */     int nRows = getRowDimension();
/*  949 */     int nCols = getColumnDimension();
/*  950 */     StringBuffer res = new StringBuffer();
/*  951 */     String fullClassName = getClass().getName();
/*  952 */     String shortClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
/*  953 */     res.append(shortClassName).append("{");
/*      */     
/*  955 */     for (int i = 0; i < nRows; i++) {
/*  956 */       if (i > 0) {
/*  957 */         res.append(",");
/*      */       }
/*  959 */       res.append("{");
/*  960 */       for (int j = 0; j < nCols; j++) {
/*  961 */         if (j > 0) {
/*  962 */           res.append(",");
/*      */         }
/*  964 */         res.append(getEntry(i, j));
/*      */       } 
/*  966 */       res.append("}");
/*      */     } 
/*      */     
/*  969 */     res.append("}");
/*  970 */     return res.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object object) {
/*  983 */     if (object == this) {
/*  984 */       return true;
/*      */     }
/*  986 */     if (!(object instanceof FieldMatrix)) {
/*  987 */       return false;
/*      */     }
/*  989 */     FieldMatrix<?> m = (FieldMatrix)object;
/*  990 */     int nRows = getRowDimension();
/*  991 */     int nCols = getColumnDimension();
/*  992 */     if (m.getColumnDimension() != nCols || m.getRowDimension() != nRows) {
/*  993 */       return false;
/*      */     }
/*  995 */     for (int row = 0; row < nRows; row++) {
/*  996 */       for (int col = 0; col < nCols; col++) {
/*  997 */         if (!getEntry(row, col).equals(m.getEntry(row, col))) {
/*  998 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/* 1002 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1012 */     int ret = 322562;
/* 1013 */     int nRows = getRowDimension();
/* 1014 */     int nCols = getColumnDimension();
/* 1015 */     ret = ret * 31 + nRows;
/* 1016 */     ret = ret * 31 + nCols;
/* 1017 */     for (int row = 0; row < nRows; row++) {
/* 1018 */       for (int col = 0; col < nCols; col++) {
/* 1019 */         ret = ret * 31 + (11 * (row + 1) + 17 * (col + 1)) * getEntry(row, col).hashCode();
/*      */       }
/*      */     } 
/* 1022 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkRowIndex(int row) throws OutOfRangeException {
/* 1032 */     if (row < 0 || row >= getRowDimension()) {
/* 1033 */       throw new OutOfRangeException(LocalizedFormats.ROW_INDEX, Integer.valueOf(row), Integer.valueOf(0), Integer.valueOf(getRowDimension() - 1));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkColumnIndex(int column) throws OutOfRangeException {
/* 1046 */     if (column < 0 || column >= getColumnDimension()) {
/* 1047 */       throw new OutOfRangeException(LocalizedFormats.COLUMN_INDEX, Integer.valueOf(column), Integer.valueOf(0), Integer.valueOf(getColumnDimension() - 1));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkSubMatrixIndex(int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
/* 1067 */     checkRowIndex(startRow);
/* 1068 */     checkRowIndex(endRow);
/* 1069 */     if (endRow < startRow) {
/* 1070 */       throw new NumberIsTooSmallException(LocalizedFormats.INITIAL_ROW_AFTER_FINAL_ROW, Integer.valueOf(endRow), Integer.valueOf(startRow), true);
/*      */     }
/*      */ 
/*      */     
/* 1074 */     checkColumnIndex(startColumn);
/* 1075 */     checkColumnIndex(endColumn);
/* 1076 */     if (endColumn < startColumn) {
/* 1077 */       throw new NumberIsTooSmallException(LocalizedFormats.INITIAL_COLUMN_AFTER_FINAL_COLUMN, Integer.valueOf(endColumn), Integer.valueOf(startColumn), true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkSubMatrixIndex(int[] selectedRows, int[] selectedColumns) throws NoDataException, NullArgumentException, OutOfRangeException {
/* 1094 */     if (selectedRows == null || selectedColumns == null)
/*      */     {
/* 1096 */       throw new NullArgumentException();
/*      */     }
/* 1098 */     if (selectedRows.length == 0 || selectedColumns.length == 0)
/*      */     {
/* 1100 */       throw new NoDataException();
/*      */     }
/*      */     
/* 1103 */     for (int row : selectedRows) {
/* 1104 */       checkRowIndex(row);
/*      */     }
/* 1106 */     for (int column : selectedColumns) {
/* 1107 */       checkColumnIndex(column);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkAdditionCompatible(FieldMatrix<T> m) throws MatrixDimensionMismatchException {
/* 1120 */     if (getRowDimension() != m.getRowDimension() || getColumnDimension() != m.getColumnDimension())
/*      */     {
/* 1122 */       throw new MatrixDimensionMismatchException(m.getRowDimension(), m.getColumnDimension(), getRowDimension(), getColumnDimension());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkSubtractionCompatible(FieldMatrix<T> m) throws MatrixDimensionMismatchException {
/* 1136 */     if (getRowDimension() != m.getRowDimension() || getColumnDimension() != m.getColumnDimension())
/*      */     {
/* 1138 */       throw new MatrixDimensionMismatchException(m.getRowDimension(), m.getColumnDimension(), getRowDimension(), getColumnDimension());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkMultiplicationCompatible(FieldMatrix<T> m) throws DimensionMismatchException {
/* 1152 */     if (getColumnDimension() != m.getRowDimension())
/* 1153 */       throw new DimensionMismatchException(m.getRowDimension(), getColumnDimension()); 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/AbstractFieldMatrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */