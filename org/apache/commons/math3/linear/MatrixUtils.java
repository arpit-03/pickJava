/*      */ package org.apache.commons.math3.linear;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.lang.reflect.Field;
/*      */ import java.util.Arrays;
/*      */ import org.apache.commons.math3.Field;
/*      */ import org.apache.commons.math3.FieldElement;
/*      */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*      */ import org.apache.commons.math3.exception.MathArithmeticException;
/*      */ import org.apache.commons.math3.exception.NoDataException;
/*      */ import org.apache.commons.math3.exception.NullArgumentException;
/*      */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*      */ import org.apache.commons.math3.exception.OutOfRangeException;
/*      */ import org.apache.commons.math3.exception.ZeroException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.fraction.BigFraction;
/*      */ import org.apache.commons.math3.fraction.Fraction;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.MathArrays;
/*      */ import org.apache.commons.math3.util.MathUtils;
/*      */ import org.apache.commons.math3.util.Precision;
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
/*      */ public class MatrixUtils
/*      */ {
/*   52 */   public static final RealMatrixFormat DEFAULT_FORMAT = RealMatrixFormat.getInstance();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   58 */   public static final RealMatrixFormat OCTAVE_FORMAT = new RealMatrixFormat("[", "]", "", "", "; ", ", ");
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
/*      */   public static RealMatrix createRealMatrix(int rows, int columns) {
/*   81 */     return (rows * columns <= 4096) ? new Array2DRowRealMatrix(rows, columns) : new BlockRealMatrix(rows, columns);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends FieldElement<T>> FieldMatrix<T> createFieldMatrix(Field<T> field, int rows, int columns) {
/*  103 */     return (rows * columns <= 4096) ? new Array2DRowFieldMatrix<T>(field, rows, columns) : new BlockFieldMatrix<T>(field, rows, columns);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RealMatrix createRealMatrix(double[][] data) throws NullArgumentException, DimensionMismatchException, NoDataException {
/*  130 */     if (data == null || data[0] == null)
/*      */     {
/*  132 */       throw new NullArgumentException();
/*      */     }
/*  134 */     return (data.length * (data[0]).length <= 4096) ? new Array2DRowRealMatrix(data) : new BlockRealMatrix(data);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends FieldElement<T>> FieldMatrix<T> createFieldMatrix(T[][] data) throws DimensionMismatchException, NoDataException, NullArgumentException {
/*  159 */     if (data == null || data[0] == null)
/*      */     {
/*  161 */       throw new NullArgumentException();
/*      */     }
/*  163 */     return (data.length * (data[0]).length <= 4096) ? new Array2DRowFieldMatrix<T>(data) : new BlockFieldMatrix<T>(data);
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
/*      */   public static RealMatrix createRealIdentityMatrix(int dimension) {
/*  176 */     RealMatrix m = createRealMatrix(dimension, dimension);
/*  177 */     for (int i = 0; i < dimension; i++) {
/*  178 */       m.setEntry(i, i, 1.0D);
/*      */     }
/*  180 */     return m;
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
/*      */   public static <T extends FieldElement<T>> FieldMatrix<T> createFieldIdentityMatrix(Field<T> field, int dimension) {
/*  195 */     FieldElement fieldElement1 = (FieldElement)field.getZero();
/*  196 */     FieldElement fieldElement2 = (FieldElement)field.getOne();
/*  197 */     FieldElement[][] arrayOfFieldElement = (FieldElement[][])MathArrays.buildArray(field, dimension, dimension);
/*  198 */     for (int row = 0; row < dimension; row++) {
/*  199 */       FieldElement[] arrayOfFieldElement1 = arrayOfFieldElement[row];
/*  200 */       Arrays.fill((Object[])arrayOfFieldElement1, fieldElement1);
/*  201 */       arrayOfFieldElement1[row] = fieldElement2;
/*      */     } 
/*  203 */     return new Array2DRowFieldMatrix<T>(field, (T[][])arrayOfFieldElement, false);
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
/*      */   public static RealMatrix createRealDiagonalMatrix(double[] diagonal) {
/*  215 */     RealMatrix m = createRealMatrix(diagonal.length, diagonal.length);
/*  216 */     for (int i = 0; i < diagonal.length; i++) {
/*  217 */       m.setEntry(i, i, diagonal[i]);
/*      */     }
/*  219 */     return m;
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
/*      */   public static <T extends FieldElement<T>> FieldMatrix<T> createFieldDiagonalMatrix(T[] diagonal) {
/*  233 */     FieldMatrix<T> m = createFieldMatrix(diagonal[0].getField(), diagonal.length, diagonal.length);
/*      */     
/*  235 */     for (int i = 0; i < diagonal.length; i++) {
/*  236 */       m.setEntry(i, i, diagonal[i]);
/*      */     }
/*  238 */     return m;
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
/*      */   public static RealVector createRealVector(double[] data) throws NoDataException, NullArgumentException {
/*  251 */     if (data == null) {
/*  252 */       throw new NullArgumentException();
/*      */     }
/*  254 */     return new ArrayRealVector(data, true);
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
/*      */   public static <T extends FieldElement<T>> FieldVector<T> createFieldVector(T[] data) throws NoDataException, NullArgumentException, ZeroException {
/*  269 */     if (data == null) {
/*  270 */       throw new NullArgumentException();
/*      */     }
/*  272 */     if (data.length == 0) {
/*  273 */       throw new ZeroException(LocalizedFormats.VECTOR_MUST_HAVE_AT_LEAST_ONE_ELEMENT, new Object[0]);
/*      */     }
/*  275 */     return new ArrayFieldVector<T>(data[0].getField(), data, true);
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
/*      */   public static RealMatrix createRowRealMatrix(double[] rowData) throws NoDataException, NullArgumentException {
/*  289 */     if (rowData == null) {
/*  290 */       throw new NullArgumentException();
/*      */     }
/*  292 */     int nCols = rowData.length;
/*  293 */     RealMatrix m = createRealMatrix(1, nCols);
/*  294 */     for (int i = 0; i < nCols; i++) {
/*  295 */       m.setEntry(0, i, rowData[i]);
/*      */     }
/*  297 */     return m;
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
/*      */   public static <T extends FieldElement<T>> FieldMatrix<T> createRowFieldMatrix(T[] rowData) throws NoDataException, NullArgumentException {
/*  313 */     if (rowData == null) {
/*  314 */       throw new NullArgumentException();
/*      */     }
/*  316 */     int nCols = rowData.length;
/*  317 */     if (nCols == 0) {
/*  318 */       throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_COLUMN);
/*      */     }
/*  320 */     FieldMatrix<T> m = createFieldMatrix(rowData[0].getField(), 1, nCols);
/*  321 */     for (int i = 0; i < nCols; i++) {
/*  322 */       m.setEntry(0, i, rowData[i]);
/*      */     }
/*  324 */     return m;
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
/*      */   public static RealMatrix createColumnRealMatrix(double[] columnData) throws NoDataException, NullArgumentException {
/*  338 */     if (columnData == null) {
/*  339 */       throw new NullArgumentException();
/*      */     }
/*  341 */     int nRows = columnData.length;
/*  342 */     RealMatrix m = createRealMatrix(nRows, 1);
/*  343 */     for (int i = 0; i < nRows; i++) {
/*  344 */       m.setEntry(i, 0, columnData[i]);
/*      */     }
/*  346 */     return m;
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
/*      */   public static <T extends FieldElement<T>> FieldMatrix<T> createColumnFieldMatrix(T[] columnData) throws NoDataException, NullArgumentException {
/*  362 */     if (columnData == null) {
/*  363 */       throw new NullArgumentException();
/*      */     }
/*  365 */     int nRows = columnData.length;
/*  366 */     if (nRows == 0) {
/*  367 */       throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_ROW);
/*      */     }
/*  369 */     FieldMatrix<T> m = createFieldMatrix(columnData[0].getField(), nRows, 1);
/*  370 */     for (int i = 0; i < nRows; i++) {
/*  371 */       m.setEntry(i, 0, columnData[i]);
/*      */     }
/*  373 */     return m;
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
/*      */   private static boolean isSymmetricInternal(RealMatrix matrix, double relativeTolerance, boolean raiseException) {
/*  390 */     int rows = matrix.getRowDimension();
/*  391 */     if (rows != matrix.getColumnDimension()) {
/*  392 */       if (raiseException) {
/*  393 */         throw new NonSquareMatrixException(rows, matrix.getColumnDimension());
/*      */       }
/*  395 */       return false;
/*      */     } 
/*      */     
/*  398 */     for (int i = 0; i < rows; i++) {
/*  399 */       for (int j = i + 1; j < rows; j++) {
/*  400 */         double mij = matrix.getEntry(i, j);
/*  401 */         double mji = matrix.getEntry(j, i);
/*  402 */         if (FastMath.abs(mij - mji) > FastMath.max(FastMath.abs(mij), FastMath.abs(mji)) * relativeTolerance) {
/*      */           
/*  404 */           if (raiseException) {
/*  405 */             throw new NonSymmetricMatrixException(i, j, relativeTolerance);
/*      */           }
/*  407 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  412 */     return true;
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
/*      */   public static void checkSymmetric(RealMatrix matrix, double eps) {
/*  426 */     isSymmetricInternal(matrix, eps, true);
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
/*      */   public static boolean isSymmetric(RealMatrix matrix, double eps) {
/*  439 */     return isSymmetricInternal(matrix, eps, false);
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
/*      */   public static void checkMatrixIndex(AnyMatrix m, int row, int column) throws OutOfRangeException {
/*  454 */     checkRowIndex(m, row);
/*  455 */     checkColumnIndex(m, column);
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
/*      */   public static void checkRowIndex(AnyMatrix m, int row) throws OutOfRangeException {
/*  467 */     if (row < 0 || row >= m.getRowDimension())
/*      */     {
/*  469 */       throw new OutOfRangeException(LocalizedFormats.ROW_INDEX, Integer.valueOf(row), Integer.valueOf(0), Integer.valueOf(m.getRowDimension() - 1));
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
/*      */   public static void checkColumnIndex(AnyMatrix m, int column) throws OutOfRangeException {
/*  483 */     if (column < 0 || column >= m.getColumnDimension()) {
/*  484 */       throw new OutOfRangeException(LocalizedFormats.COLUMN_INDEX, Integer.valueOf(column), Integer.valueOf(0), Integer.valueOf(m.getColumnDimension() - 1));
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
/*      */ 
/*      */   
/*      */   public static void checkSubMatrixIndex(AnyMatrix m, int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
/*  506 */     checkRowIndex(m, startRow);
/*  507 */     checkRowIndex(m, endRow);
/*  508 */     if (endRow < startRow) {
/*  509 */       throw new NumberIsTooSmallException(LocalizedFormats.INITIAL_ROW_AFTER_FINAL_ROW, Integer.valueOf(endRow), Integer.valueOf(startRow), false);
/*      */     }
/*      */ 
/*      */     
/*  513 */     checkColumnIndex(m, startColumn);
/*  514 */     checkColumnIndex(m, endColumn);
/*  515 */     if (endColumn < startColumn) {
/*  516 */       throw new NumberIsTooSmallException(LocalizedFormats.INITIAL_COLUMN_AFTER_FINAL_COLUMN, Integer.valueOf(endColumn), Integer.valueOf(startColumn), false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkSubMatrixIndex(AnyMatrix m, int[] selectedRows, int[] selectedColumns) throws NoDataException, NullArgumentException, OutOfRangeException {
/*  540 */     if (selectedRows == null) {
/*  541 */       throw new NullArgumentException();
/*      */     }
/*  543 */     if (selectedColumns == null) {
/*  544 */       throw new NullArgumentException();
/*      */     }
/*  546 */     if (selectedRows.length == 0) {
/*  547 */       throw new NoDataException(LocalizedFormats.EMPTY_SELECTED_ROW_INDEX_ARRAY);
/*      */     }
/*  549 */     if (selectedColumns.length == 0) {
/*  550 */       throw new NoDataException(LocalizedFormats.EMPTY_SELECTED_COLUMN_INDEX_ARRAY);
/*      */     }
/*      */     
/*  553 */     for (int row : selectedRows) {
/*  554 */       checkRowIndex(m, row);
/*      */     }
/*  556 */     for (int column : selectedColumns) {
/*  557 */       checkColumnIndex(m, column);
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
/*      */   public static void checkAdditionCompatible(AnyMatrix left, AnyMatrix right) throws MatrixDimensionMismatchException {
/*  571 */     if (left.getRowDimension() != right.getRowDimension() || left.getColumnDimension() != right.getColumnDimension())
/*      */     {
/*  573 */       throw new MatrixDimensionMismatchException(left.getRowDimension(), left.getColumnDimension(), right.getRowDimension(), right.getColumnDimension());
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
/*      */   public static void checkSubtractionCompatible(AnyMatrix left, AnyMatrix right) throws MatrixDimensionMismatchException {
/*  588 */     if (left.getRowDimension() != right.getRowDimension() || left.getColumnDimension() != right.getColumnDimension())
/*      */     {
/*  590 */       throw new MatrixDimensionMismatchException(left.getRowDimension(), left.getColumnDimension(), right.getRowDimension(), right.getColumnDimension());
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
/*      */   public static void checkMultiplicationCompatible(AnyMatrix left, AnyMatrix right) throws DimensionMismatchException {
/*  606 */     if (left.getColumnDimension() != right.getRowDimension()) {
/*  607 */       throw new DimensionMismatchException(left.getColumnDimension(), right.getRowDimension());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Array2DRowRealMatrix fractionMatrixToRealMatrix(FieldMatrix<Fraction> m) {
/*  618 */     FractionMatrixConverter converter = new FractionMatrixConverter();
/*  619 */     m.walkInOptimizedOrder(converter);
/*  620 */     return converter.getConvertedMatrix();
/*      */   }
/*      */   
/*      */   private static class FractionMatrixConverter
/*      */     extends DefaultFieldMatrixPreservingVisitor<Fraction>
/*      */   {
/*      */     private double[][] data;
/*      */     
/*      */     FractionMatrixConverter() {
/*  629 */       super(Fraction.ZERO);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
/*  636 */       this.data = new double[rows][columns];
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void visit(int row, int column, Fraction value) {
/*  642 */       this.data[row][column] = value.doubleValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Array2DRowRealMatrix getConvertedMatrix() {
/*  651 */       return new Array2DRowRealMatrix(this.data, false);
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
/*      */   public static Array2DRowRealMatrix bigFractionMatrixToRealMatrix(FieldMatrix<BigFraction> m) {
/*  663 */     BigFractionMatrixConverter converter = new BigFractionMatrixConverter();
/*  664 */     m.walkInOptimizedOrder(converter);
/*  665 */     return converter.getConvertedMatrix();
/*      */   }
/*      */   
/*      */   private static class BigFractionMatrixConverter
/*      */     extends DefaultFieldMatrixPreservingVisitor<BigFraction>
/*      */   {
/*      */     private double[][] data;
/*      */     
/*      */     BigFractionMatrixConverter() {
/*  674 */       super(BigFraction.ZERO);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
/*  681 */       this.data = new double[rows][columns];
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void visit(int row, int column, BigFraction value) {
/*  687 */       this.data[row][column] = value.doubleValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Array2DRowRealMatrix getConvertedMatrix() {
/*  696 */       return new Array2DRowRealMatrix(this.data, false);
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
/*      */   public static void serializeRealVector(RealVector vector, ObjectOutputStream oos) throws IOException {
/*  743 */     int n = vector.getDimension();
/*  744 */     oos.writeInt(n);
/*  745 */     for (int i = 0; i < n; i++) {
/*  746 */       oos.writeDouble(vector.getEntry(i));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void deserializeRealVector(Object instance, String fieldName, ObjectInputStream ois) throws ClassNotFoundException, IOException {
/*      */     try {
/*  774 */       int n = ois.readInt();
/*  775 */       double[] data = new double[n];
/*  776 */       for (int i = 0; i < n; i++) {
/*  777 */         data[i] = ois.readDouble();
/*      */       }
/*      */ 
/*      */       
/*  781 */       RealVector vector = new ArrayRealVector(data, false);
/*      */ 
/*      */       
/*  784 */       Field f = instance.getClass().getDeclaredField(fieldName);
/*      */       
/*  786 */       f.setAccessible(true);
/*  787 */       f.set(instance, vector);
/*      */     }
/*  789 */     catch (NoSuchFieldException nsfe) {
/*  790 */       IOException ioe = new IOException();
/*  791 */       ioe.initCause(nsfe);
/*  792 */       throw ioe;
/*  793 */     } catch (IllegalAccessException iae) {
/*  794 */       IOException ioe = new IOException();
/*  795 */       ioe.initCause(iae);
/*  796 */       throw ioe;
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
/*      */   public static void serializeRealMatrix(RealMatrix matrix, ObjectOutputStream oos) throws IOException {
/*  844 */     int n = matrix.getRowDimension();
/*  845 */     int m = matrix.getColumnDimension();
/*  846 */     oos.writeInt(n);
/*  847 */     oos.writeInt(m);
/*  848 */     for (int i = 0; i < n; i++) {
/*  849 */       for (int j = 0; j < m; j++) {
/*  850 */         oos.writeDouble(matrix.getEntry(i, j));
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void deserializeRealMatrix(Object instance, String fieldName, ObjectInputStream ois) throws ClassNotFoundException, IOException {
/*      */     try {
/*  879 */       int n = ois.readInt();
/*  880 */       int m = ois.readInt();
/*  881 */       double[][] data = new double[n][m];
/*  882 */       for (int i = 0; i < n; i++) {
/*  883 */         double[] dataI = data[i];
/*  884 */         for (int j = 0; j < m; j++) {
/*  885 */           dataI[j] = ois.readDouble();
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  890 */       RealMatrix matrix = new Array2DRowRealMatrix(data, false);
/*      */ 
/*      */       
/*  893 */       Field f = instance.getClass().getDeclaredField(fieldName);
/*      */       
/*  895 */       f.setAccessible(true);
/*  896 */       f.set(instance, matrix);
/*      */     }
/*  898 */     catch (NoSuchFieldException nsfe) {
/*  899 */       IOException ioe = new IOException();
/*  900 */       ioe.initCause(nsfe);
/*  901 */       throw ioe;
/*  902 */     } catch (IllegalAccessException iae) {
/*  903 */       IOException ioe = new IOException();
/*  904 */       ioe.initCause(iae);
/*  905 */       throw ioe;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void solveLowerTriangularSystem(RealMatrix rm, RealVector b) throws DimensionMismatchException, MathArithmeticException, NonSquareMatrixException {
/*  930 */     if (rm == null || b == null || rm.getRowDimension() != b.getDimension()) {
/*  931 */       throw new DimensionMismatchException((rm == null) ? 0 : rm.getRowDimension(), (b == null) ? 0 : b.getDimension());
/*      */     }
/*      */ 
/*      */     
/*  935 */     if (rm.getColumnDimension() != rm.getRowDimension()) {
/*  936 */       throw new NonSquareMatrixException(rm.getRowDimension(), rm.getColumnDimension());
/*      */     }
/*      */     
/*  939 */     int rows = rm.getRowDimension();
/*  940 */     for (int i = 0; i < rows; i++) {
/*  941 */       double diag = rm.getEntry(i, i);
/*  942 */       if (FastMath.abs(diag) < Precision.SAFE_MIN) {
/*  943 */         throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
/*      */       }
/*  945 */       double bi = b.getEntry(i) / diag;
/*  946 */       b.setEntry(i, bi);
/*  947 */       for (int j = i + 1; j < rows; j++) {
/*  948 */         b.setEntry(j, b.getEntry(j) - bi * rm.getEntry(j, i));
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void solveUpperTriangularSystem(RealMatrix rm, RealVector b) throws DimensionMismatchException, MathArithmeticException, NonSquareMatrixException {
/*  975 */     if (rm == null || b == null || rm.getRowDimension() != b.getDimension()) {
/*  976 */       throw new DimensionMismatchException((rm == null) ? 0 : rm.getRowDimension(), (b == null) ? 0 : b.getDimension());
/*      */     }
/*      */ 
/*      */     
/*  980 */     if (rm.getColumnDimension() != rm.getRowDimension()) {
/*  981 */       throw new NonSquareMatrixException(rm.getRowDimension(), rm.getColumnDimension());
/*      */     }
/*      */     
/*  984 */     int rows = rm.getRowDimension();
/*  985 */     for (int i = rows - 1; i > -1; i--) {
/*  986 */       double diag = rm.getEntry(i, i);
/*  987 */       if (FastMath.abs(diag) < Precision.SAFE_MIN) {
/*  988 */         throw new MathArithmeticException(LocalizedFormats.ZERO_DENOMINATOR, new Object[0]);
/*      */       }
/*  990 */       double bi = b.getEntry(i) / diag;
/*  991 */       b.setEntry(i, bi);
/*  992 */       for (int j = i - 1; j > -1; j--) {
/*  993 */         b.setEntry(j, b.getEntry(j) - bi * rm.getEntry(j, i));
/*      */       }
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
/*      */   public static RealMatrix blockInverse(RealMatrix m, int splitIndex) {
/* 1012 */     int n = m.getRowDimension();
/* 1013 */     if (m.getColumnDimension() != n) {
/* 1014 */       throw new NonSquareMatrixException(m.getRowDimension(), m.getColumnDimension());
/*      */     }
/*      */ 
/*      */     
/* 1018 */     int splitIndex1 = splitIndex + 1;
/*      */     
/* 1020 */     RealMatrix a = m.getSubMatrix(0, splitIndex, 0, splitIndex);
/* 1021 */     RealMatrix b = m.getSubMatrix(0, splitIndex, splitIndex1, n - 1);
/* 1022 */     RealMatrix c = m.getSubMatrix(splitIndex1, n - 1, 0, splitIndex);
/* 1023 */     RealMatrix d = m.getSubMatrix(splitIndex1, n - 1, splitIndex1, n - 1);
/*      */     
/* 1025 */     SingularValueDecomposition aDec = new SingularValueDecomposition(a);
/* 1026 */     DecompositionSolver aSolver = aDec.getSolver();
/* 1027 */     if (!aSolver.isNonSingular()) {
/* 1028 */       throw new SingularMatrixException();
/*      */     }
/* 1030 */     RealMatrix aInv = aSolver.getInverse();
/*      */     
/* 1032 */     SingularValueDecomposition dDec = new SingularValueDecomposition(d);
/* 1033 */     DecompositionSolver dSolver = dDec.getSolver();
/* 1034 */     if (!dSolver.isNonSingular()) {
/* 1035 */       throw new SingularMatrixException();
/*      */     }
/* 1037 */     RealMatrix dInv = dSolver.getInverse();
/*      */     
/* 1039 */     RealMatrix tmp1 = a.subtract(b.multiply(dInv).multiply(c));
/* 1040 */     SingularValueDecomposition tmp1Dec = new SingularValueDecomposition(tmp1);
/* 1041 */     DecompositionSolver tmp1Solver = tmp1Dec.getSolver();
/* 1042 */     if (!tmp1Solver.isNonSingular()) {
/* 1043 */       throw new SingularMatrixException();
/*      */     }
/* 1045 */     RealMatrix result00 = tmp1Solver.getInverse();
/*      */     
/* 1047 */     RealMatrix tmp2 = d.subtract(c.multiply(aInv).multiply(b));
/* 1048 */     SingularValueDecomposition tmp2Dec = new SingularValueDecomposition(tmp2);
/* 1049 */     DecompositionSolver tmp2Solver = tmp2Dec.getSolver();
/* 1050 */     if (!tmp2Solver.isNonSingular()) {
/* 1051 */       throw new SingularMatrixException();
/*      */     }
/* 1053 */     RealMatrix result11 = tmp2Solver.getInverse();
/*      */     
/* 1055 */     RealMatrix result01 = aInv.multiply(b).multiply(result11).scalarMultiply(-1.0D);
/* 1056 */     RealMatrix result10 = dInv.multiply(c).multiply(result00).scalarMultiply(-1.0D);
/*      */     
/* 1058 */     RealMatrix result = new Array2DRowRealMatrix(n, n);
/* 1059 */     result.setSubMatrix(result00.getData(), 0, 0);
/* 1060 */     result.setSubMatrix(result01.getData(), 0, splitIndex1);
/* 1061 */     result.setSubMatrix(result10.getData(), splitIndex1, 0);
/* 1062 */     result.setSubMatrix(result11.getData(), splitIndex1, splitIndex1);
/*      */     
/* 1064 */     return result;
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
/*      */ 
/*      */   
/*      */   public static RealMatrix inverse(RealMatrix matrix) throws NullArgumentException, SingularMatrixException, NonSquareMatrixException {
/* 1085 */     return inverse(matrix, 0.0D);
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
/*      */   
/*      */   public static RealMatrix inverse(RealMatrix matrix, double threshold) throws NullArgumentException, SingularMatrixException, NonSquareMatrixException {
/* 1105 */     MathUtils.checkNotNull(matrix);
/*      */     
/* 1107 */     if (!matrix.isSquare()) {
/* 1108 */       throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
/*      */     }
/*      */ 
/*      */     
/* 1112 */     if (matrix instanceof DiagonalMatrix) {
/* 1113 */       return ((DiagonalMatrix)matrix).inverse(threshold);
/*      */     }
/* 1115 */     QRDecomposition decomposition = new QRDecomposition(matrix, threshold);
/* 1116 */     return decomposition.getSolver().getInverse();
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/MatrixUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */