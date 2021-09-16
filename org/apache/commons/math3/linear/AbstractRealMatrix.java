/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Locale;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ public abstract class AbstractRealMatrix
/*     */   extends RealLinearOperator
/*     */   implements RealMatrix
/*     */ {
/*  46 */   private static final RealMatrixFormat DEFAULT_FORMAT = RealMatrixFormat.getInstance(Locale.US);
/*     */   
/*     */   static {
/*  49 */     DEFAULT_FORMAT.getFormat().setMinimumFractionDigits(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractRealMatrix() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractRealMatrix(int rowDimension, int columnDimension) throws NotStrictlyPositiveException {
/*  67 */     if (rowDimension < 1) {
/*  68 */       throw new NotStrictlyPositiveException(Integer.valueOf(rowDimension));
/*     */     }
/*  70 */     if (columnDimension < 1) {
/*  71 */       throw new NotStrictlyPositiveException(Integer.valueOf(columnDimension));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix add(RealMatrix m) throws MatrixDimensionMismatchException {
/*  78 */     MatrixUtils.checkAdditionCompatible(this, m);
/*     */     
/*  80 */     int rowCount = getRowDimension();
/*  81 */     int columnCount = getColumnDimension();
/*  82 */     RealMatrix out = createMatrix(rowCount, columnCount);
/*  83 */     for (int row = 0; row < rowCount; row++) {
/*  84 */       for (int col = 0; col < columnCount; col++) {
/*  85 */         out.setEntry(row, col, getEntry(row, col) + m.getEntry(row, col));
/*     */       }
/*     */     } 
/*     */     
/*  89 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix subtract(RealMatrix m) throws MatrixDimensionMismatchException {
/*  95 */     MatrixUtils.checkSubtractionCompatible(this, m);
/*     */     
/*  97 */     int rowCount = getRowDimension();
/*  98 */     int columnCount = getColumnDimension();
/*  99 */     RealMatrix out = createMatrix(rowCount, columnCount);
/* 100 */     for (int row = 0; row < rowCount; row++) {
/* 101 */       for (int col = 0; col < columnCount; col++) {
/* 102 */         out.setEntry(row, col, getEntry(row, col) - m.getEntry(row, col));
/*     */       }
/*     */     } 
/*     */     
/* 106 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   public RealMatrix scalarAdd(double d) {
/* 111 */     int rowCount = getRowDimension();
/* 112 */     int columnCount = getColumnDimension();
/* 113 */     RealMatrix out = createMatrix(rowCount, columnCount);
/* 114 */     for (int row = 0; row < rowCount; row++) {
/* 115 */       for (int col = 0; col < columnCount; col++) {
/* 116 */         out.setEntry(row, col, getEntry(row, col) + d);
/*     */       }
/*     */     } 
/*     */     
/* 120 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   public RealMatrix scalarMultiply(double d) {
/* 125 */     int rowCount = getRowDimension();
/* 126 */     int columnCount = getColumnDimension();
/* 127 */     RealMatrix out = createMatrix(rowCount, columnCount);
/* 128 */     for (int row = 0; row < rowCount; row++) {
/* 129 */       for (int col = 0; col < columnCount; col++) {
/* 130 */         out.setEntry(row, col, getEntry(row, col) * d);
/*     */       }
/*     */     } 
/*     */     
/* 134 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix multiply(RealMatrix m) throws DimensionMismatchException {
/* 140 */     MatrixUtils.checkMultiplicationCompatible(this, m);
/*     */     
/* 142 */     int nRows = getRowDimension();
/* 143 */     int nCols = m.getColumnDimension();
/* 144 */     int nSum = getColumnDimension();
/* 145 */     RealMatrix out = createMatrix(nRows, nCols);
/* 146 */     for (int row = 0; row < nRows; row++) {
/* 147 */       for (int col = 0; col < nCols; col++) {
/* 148 */         double sum = 0.0D;
/* 149 */         for (int i = 0; i < nSum; i++) {
/* 150 */           sum += getEntry(row, i) * m.getEntry(i, col);
/*     */         }
/* 152 */         out.setEntry(row, col, sum);
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix preMultiply(RealMatrix m) throws DimensionMismatchException {
/* 162 */     return m.multiply(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix power(int p) throws NotPositiveException, NonSquareMatrixException {
/* 168 */     if (p < 0) {
/* 169 */       throw new NotPositiveException(LocalizedFormats.NOT_POSITIVE_EXPONENT, Integer.valueOf(p));
/*     */     }
/*     */     
/* 172 */     if (!isSquare()) {
/* 173 */       throw new NonSquareMatrixException(getRowDimension(), getColumnDimension());
/*     */     }
/*     */     
/* 176 */     if (p == 0) {
/* 177 */       return MatrixUtils.createRealIdentityMatrix(getRowDimension());
/*     */     }
/*     */     
/* 180 */     if (p == 1) {
/* 181 */       return copy();
/*     */     }
/*     */     
/* 184 */     int power = p - 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     char[] binaryRepresentation = Integer.toBinaryString(power).toCharArray();
/* 194 */     ArrayList<Integer> nonZeroPositions = new ArrayList<Integer>();
/* 195 */     int maxI = -1;
/*     */     
/* 197 */     for (int i = 0; i < binaryRepresentation.length; i++) {
/* 198 */       if (binaryRepresentation[i] == '1') {
/* 199 */         int pos = binaryRepresentation.length - i - 1;
/* 200 */         nonZeroPositions.add(Integer.valueOf(pos));
/*     */ 
/*     */         
/* 203 */         if (maxI == -1) {
/* 204 */           maxI = pos;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     RealMatrix[] results = new RealMatrix[maxI + 1];
/* 210 */     results[0] = copy();
/*     */     
/* 212 */     for (int j = 1; j <= maxI; j++) {
/* 213 */       results[j] = results[j - 1].multiply(results[j - 1]);
/*     */     }
/*     */     
/* 216 */     RealMatrix result = copy();
/*     */     
/* 218 */     for (Integer integer : nonZeroPositions) {
/* 219 */       result = result.multiply(results[integer.intValue()]);
/*     */     }
/*     */     
/* 222 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[][] getData() {
/* 227 */     double[][] data = new double[getRowDimension()][getColumnDimension()];
/*     */     
/* 229 */     for (int i = 0; i < data.length; i++) {
/* 230 */       double[] dataI = data[i];
/* 231 */       for (int j = 0; j < dataI.length; j++) {
/* 232 */         dataI[j] = getEntry(i, j);
/*     */       }
/*     */     } 
/*     */     
/* 236 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNorm() {
/* 241 */     return walkInColumnOrder(new RealMatrixPreservingVisitor()
/*     */         {
/*     */           private double endRow;
/*     */ 
/*     */ 
/*     */           
/*     */           private double columnSum;
/*     */ 
/*     */ 
/*     */           
/*     */           private double maxColSum;
/*     */ 
/*     */ 
/*     */           
/*     */           public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
/* 256 */             this.endRow = endRow;
/* 257 */             this.columnSum = 0.0D;
/* 258 */             this.maxColSum = 0.0D;
/*     */           }
/*     */ 
/*     */           
/*     */           public void visit(int row, int column, double value) {
/* 263 */             this.columnSum += FastMath.abs(value);
/* 264 */             if (row == this.endRow) {
/* 265 */               this.maxColSum = FastMath.max(this.maxColSum, this.columnSum);
/* 266 */               this.columnSum = 0.0D;
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public double end() {
/* 272 */             return this.maxColSum;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public double getFrobeniusNorm() {
/* 279 */     return walkInOptimizedOrder(new RealMatrixPreservingVisitor()
/*     */         {
/*     */           private double sum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
/* 288 */             this.sum = 0.0D;
/*     */           }
/*     */ 
/*     */           
/*     */           public void visit(int row, int column, double value) {
/* 293 */             this.sum += value * value;
/*     */           }
/*     */ 
/*     */           
/*     */           public double end() {
/* 298 */             return FastMath.sqrt(this.sum);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getSubMatrix(int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 307 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/*     */     
/* 309 */     RealMatrix subMatrix = createMatrix(endRow - startRow + 1, endColumn - startColumn + 1);
/*     */     
/* 311 */     for (int i = startRow; i <= endRow; i++) {
/* 312 */       for (int j = startColumn; j <= endColumn; j++) {
/* 313 */         subMatrix.setEntry(i - startRow, j - startColumn, getEntry(i, j));
/*     */       }
/*     */     } 
/*     */     
/* 317 */     return subMatrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getSubMatrix(final int[] selectedRows, final int[] selectedColumns) throws NullArgumentException, NoDataException, OutOfRangeException {
/* 324 */     MatrixUtils.checkSubMatrixIndex(this, selectedRows, selectedColumns);
/*     */     
/* 326 */     RealMatrix subMatrix = createMatrix(selectedRows.length, selectedColumns.length);
/*     */     
/* 328 */     subMatrix.walkInOptimizedOrder(new DefaultRealMatrixChangingVisitor()
/*     */         {
/*     */           
/*     */           public double visit(int row, int column, double value)
/*     */           {
/* 333 */             return AbstractRealMatrix.this.getEntry(selectedRows[row], selectedColumns[column]);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 338 */     return subMatrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copySubMatrix(int startRow, int endRow, int startColumn, int endColumn, final double[][] destination) throws OutOfRangeException, NumberIsTooSmallException, MatrixDimensionMismatchException {
/* 347 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/* 348 */     int rowsCount = endRow + 1 - startRow;
/* 349 */     int columnsCount = endColumn + 1 - startColumn;
/* 350 */     if (destination.length < rowsCount || (destination[0]).length < columnsCount) {
/* 351 */       throw new MatrixDimensionMismatchException(destination.length, (destination[0]).length, rowsCount, columnsCount);
/*     */     }
/*     */ 
/*     */     
/* 355 */     for (int i = 1; i < rowsCount; i++) {
/* 356 */       if ((destination[i]).length < columnsCount) {
/* 357 */         throw new MatrixDimensionMismatchException(destination.length, (destination[i]).length, rowsCount, columnsCount);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 362 */     walkInOptimizedOrder(new DefaultRealMatrixPreservingVisitor()
/*     */         {
/*     */           private int startRow;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           private int startColumn;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
/* 375 */             this.startRow = startRow;
/* 376 */             this.startColumn = startColumn;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void visit(int row, int column, double value) {
/* 382 */             destination[row - this.startRow][column - this.startColumn] = value;
/*     */           }
/*     */         },  startRow, endRow, startColumn, endColumn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copySubMatrix(int[] selectedRows, int[] selectedColumns, double[][] destination) throws OutOfRangeException, NullArgumentException, NoDataException, MatrixDimensionMismatchException {
/* 393 */     MatrixUtils.checkSubMatrixIndex(this, selectedRows, selectedColumns);
/* 394 */     int nCols = selectedColumns.length;
/* 395 */     if (destination.length < selectedRows.length || (destination[0]).length < nCols)
/*     */     {
/* 397 */       throw new MatrixDimensionMismatchException(destination.length, (destination[0]).length, selectedRows.length, selectedColumns.length);
/*     */     }
/*     */ 
/*     */     
/* 401 */     for (int i = 0; i < selectedRows.length; i++) {
/* 402 */       double[] destinationI = destination[i];
/* 403 */       if (destinationI.length < nCols) {
/* 404 */         throw new MatrixDimensionMismatchException(destination.length, destinationI.length, selectedRows.length, selectedColumns.length);
/*     */       }
/*     */       
/* 407 */       for (int j = 0; j < selectedColumns.length; j++) {
/* 408 */         destinationI[j] = getEntry(selectedRows[i], selectedColumns[j]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubMatrix(double[][] subMatrix, int row, int column) throws NoDataException, OutOfRangeException, DimensionMismatchException, NullArgumentException {
/* 417 */     MathUtils.checkNotNull(subMatrix);
/* 418 */     int nRows = subMatrix.length;
/* 419 */     if (nRows == 0) {
/* 420 */       throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_ROW);
/*     */     }
/*     */     
/* 423 */     int nCols = (subMatrix[0]).length;
/* 424 */     if (nCols == 0) {
/* 425 */       throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_COLUMN);
/*     */     }
/*     */     
/* 428 */     for (int r = 1; r < nRows; r++) {
/* 429 */       if ((subMatrix[r]).length != nCols) {
/* 430 */         throw new DimensionMismatchException(nCols, (subMatrix[r]).length);
/*     */       }
/*     */     } 
/*     */     
/* 434 */     MatrixUtils.checkRowIndex(this, row);
/* 435 */     MatrixUtils.checkColumnIndex(this, column);
/* 436 */     MatrixUtils.checkRowIndex(this, nRows + row - 1);
/* 437 */     MatrixUtils.checkColumnIndex(this, nCols + column - 1);
/*     */     
/* 439 */     for (int i = 0; i < nRows; i++) {
/* 440 */       for (int j = 0; j < nCols; j++) {
/* 441 */         setEntry(row + i, column + j, subMatrix[i][j]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RealMatrix getRowMatrix(int row) throws OutOfRangeException {
/* 448 */     MatrixUtils.checkRowIndex(this, row);
/* 449 */     int nCols = getColumnDimension();
/* 450 */     RealMatrix out = createMatrix(1, nCols);
/* 451 */     for (int i = 0; i < nCols; i++) {
/* 452 */       out.setEntry(0, i, getEntry(row, i));
/*     */     }
/*     */     
/* 455 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRowMatrix(int row, RealMatrix matrix) throws OutOfRangeException, MatrixDimensionMismatchException {
/* 461 */     MatrixUtils.checkRowIndex(this, row);
/* 462 */     int nCols = getColumnDimension();
/* 463 */     if (matrix.getRowDimension() != 1 || matrix.getColumnDimension() != nCols)
/*     */     {
/* 465 */       throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), 1, nCols);
/*     */     }
/*     */ 
/*     */     
/* 469 */     for (int i = 0; i < nCols; i++) {
/* 470 */       setEntry(row, i, matrix.getEntry(0, i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix getColumnMatrix(int column) throws OutOfRangeException {
/* 477 */     MatrixUtils.checkColumnIndex(this, column);
/* 478 */     int nRows = getRowDimension();
/* 479 */     RealMatrix out = createMatrix(nRows, 1);
/* 480 */     for (int i = 0; i < nRows; i++) {
/* 481 */       out.setEntry(i, 0, getEntry(i, column));
/*     */     }
/*     */     
/* 484 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColumnMatrix(int column, RealMatrix matrix) throws OutOfRangeException, MatrixDimensionMismatchException {
/* 490 */     MatrixUtils.checkColumnIndex(this, column);
/* 491 */     int nRows = getRowDimension();
/* 492 */     if (matrix.getRowDimension() != nRows || matrix.getColumnDimension() != 1)
/*     */     {
/* 494 */       throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), nRows, 1);
/*     */     }
/*     */ 
/*     */     
/* 498 */     for (int i = 0; i < nRows; i++) {
/* 499 */       setEntry(i, column, matrix.getEntry(i, 0));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector getRowVector(int row) throws OutOfRangeException {
/* 506 */     return new ArrayRealVector(getRow(row), false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRowVector(int row, RealVector vector) throws OutOfRangeException, MatrixDimensionMismatchException {
/* 512 */     MatrixUtils.checkRowIndex(this, row);
/* 513 */     int nCols = getColumnDimension();
/* 514 */     if (vector.getDimension() != nCols) {
/* 515 */       throw new MatrixDimensionMismatchException(1, vector.getDimension(), 1, nCols);
/*     */     }
/*     */     
/* 518 */     for (int i = 0; i < nCols; i++) {
/* 519 */       setEntry(row, i, vector.getEntry(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector getColumnVector(int column) throws OutOfRangeException {
/* 526 */     return new ArrayRealVector(getColumn(column), false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColumnVector(int column, RealVector vector) throws OutOfRangeException, MatrixDimensionMismatchException {
/* 532 */     MatrixUtils.checkColumnIndex(this, column);
/* 533 */     int nRows = getRowDimension();
/* 534 */     if (vector.getDimension() != nRows) {
/* 535 */       throw new MatrixDimensionMismatchException(vector.getDimension(), 1, nRows, 1);
/*     */     }
/*     */     
/* 538 */     for (int i = 0; i < nRows; i++) {
/* 539 */       setEntry(i, column, vector.getEntry(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] getRow(int row) throws OutOfRangeException {
/* 545 */     MatrixUtils.checkRowIndex(this, row);
/* 546 */     int nCols = getColumnDimension();
/* 547 */     double[] out = new double[nCols];
/* 548 */     for (int i = 0; i < nCols; i++) {
/* 549 */       out[i] = getEntry(row, i);
/*     */     }
/*     */     
/* 552 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRow(int row, double[] array) throws OutOfRangeException, MatrixDimensionMismatchException {
/* 558 */     MatrixUtils.checkRowIndex(this, row);
/* 559 */     int nCols = getColumnDimension();
/* 560 */     if (array.length != nCols) {
/* 561 */       throw new MatrixDimensionMismatchException(1, array.length, 1, nCols);
/*     */     }
/* 563 */     for (int i = 0; i < nCols; i++) {
/* 564 */       setEntry(row, i, array[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] getColumn(int column) throws OutOfRangeException {
/* 570 */     MatrixUtils.checkColumnIndex(this, column);
/* 571 */     int nRows = getRowDimension();
/* 572 */     double[] out = new double[nRows];
/* 573 */     for (int i = 0; i < nRows; i++) {
/* 574 */       out[i] = getEntry(i, column);
/*     */     }
/*     */     
/* 577 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColumn(int column, double[] array) throws OutOfRangeException, MatrixDimensionMismatchException {
/* 583 */     MatrixUtils.checkColumnIndex(this, column);
/* 584 */     int nRows = getRowDimension();
/* 585 */     if (array.length != nRows) {
/* 586 */       throw new MatrixDimensionMismatchException(array.length, 1, nRows, 1);
/*     */     }
/* 588 */     for (int i = 0; i < nRows; i++) {
/* 589 */       setEntry(i, column, array[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToEntry(int row, int column, double increment) throws OutOfRangeException {
/* 596 */     MatrixUtils.checkMatrixIndex(this, row, column);
/* 597 */     setEntry(row, column, getEntry(row, column) + increment);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyEntry(int row, int column, double factor) throws OutOfRangeException {
/* 603 */     MatrixUtils.checkMatrixIndex(this, row, column);
/* 604 */     setEntry(row, column, getEntry(row, column) * factor);
/*     */   }
/*     */ 
/*     */   
/*     */   public RealMatrix transpose() {
/* 609 */     int nRows = getRowDimension();
/* 610 */     int nCols = getColumnDimension();
/* 611 */     final RealMatrix out = createMatrix(nCols, nRows);
/* 612 */     walkInOptimizedOrder(new DefaultRealMatrixPreservingVisitor()
/*     */         {
/*     */           
/*     */           public void visit(int row, int column, double value)
/*     */           {
/* 617 */             out.setEntry(column, row, value);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 622 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSquare() {
/* 627 */     return (getColumnDimension() == getRowDimension());
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
/*     */   public double getTrace() throws NonSquareMatrixException {
/* 648 */     int nRows = getRowDimension();
/* 649 */     int nCols = getColumnDimension();
/* 650 */     if (nRows != nCols) {
/* 651 */       throw new NonSquareMatrixException(nRows, nCols);
/*     */     }
/* 653 */     double trace = 0.0D;
/* 654 */     for (int i = 0; i < nRows; i++) {
/* 655 */       trace += getEntry(i, i);
/*     */     }
/* 657 */     return trace;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] operate(double[] v) throws DimensionMismatchException {
/* 663 */     int nRows = getRowDimension();
/* 664 */     int nCols = getColumnDimension();
/* 665 */     if (v.length != nCols) {
/* 666 */       throw new DimensionMismatchException(v.length, nCols);
/*     */     }
/*     */     
/* 669 */     double[] out = new double[nRows];
/* 670 */     for (int row = 0; row < nRows; row++) {
/* 671 */       double sum = 0.0D;
/* 672 */       for (int i = 0; i < nCols; i++) {
/* 673 */         sum += getEntry(row, i) * v[i];
/*     */       }
/* 675 */       out[row] = sum;
/*     */     } 
/*     */     
/* 678 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector operate(RealVector v) throws DimensionMismatchException {
/*     */     try {
/* 686 */       return new ArrayRealVector(operate(((ArrayRealVector)v).getDataRef()), false);
/* 687 */     } catch (ClassCastException cce) {
/* 688 */       int nRows = getRowDimension();
/* 689 */       int nCols = getColumnDimension();
/* 690 */       if (v.getDimension() != nCols) {
/* 691 */         throw new DimensionMismatchException(v.getDimension(), nCols);
/*     */       }
/*     */       
/* 694 */       double[] out = new double[nRows];
/* 695 */       for (int row = 0; row < nRows; row++) {
/* 696 */         double sum = 0.0D;
/* 697 */         for (int i = 0; i < nCols; i++) {
/* 698 */           sum += getEntry(row, i) * v.getEntry(i);
/*     */         }
/* 700 */         out[row] = sum;
/*     */       } 
/*     */       
/* 703 */       return new ArrayRealVector(out, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] preMultiply(double[] v) throws DimensionMismatchException {
/* 710 */     int nRows = getRowDimension();
/* 711 */     int nCols = getColumnDimension();
/* 712 */     if (v.length != nRows) {
/* 713 */       throw new DimensionMismatchException(v.length, nRows);
/*     */     }
/*     */     
/* 716 */     double[] out = new double[nCols];
/* 717 */     for (int col = 0; col < nCols; col++) {
/* 718 */       double sum = 0.0D;
/* 719 */       for (int i = 0; i < nRows; i++) {
/* 720 */         sum += getEntry(i, col) * v[i];
/*     */       }
/* 722 */       out[col] = sum;
/*     */     } 
/*     */     
/* 725 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   public RealVector preMultiply(RealVector v) throws DimensionMismatchException {
/*     */     try {
/* 731 */       return new ArrayRealVector(preMultiply(((ArrayRealVector)v).getDataRef()), false);
/* 732 */     } catch (ClassCastException cce) {
/*     */       
/* 734 */       int nRows = getRowDimension();
/* 735 */       int nCols = getColumnDimension();
/* 736 */       if (v.getDimension() != nRows) {
/* 737 */         throw new DimensionMismatchException(v.getDimension(), nRows);
/*     */       }
/*     */       
/* 740 */       double[] out = new double[nCols];
/* 741 */       for (int col = 0; col < nCols; col++) {
/* 742 */         double sum = 0.0D;
/* 743 */         for (int i = 0; i < nRows; i++) {
/* 744 */           sum += getEntry(i, col) * v.getEntry(i);
/*     */         }
/* 746 */         out[col] = sum;
/*     */       } 
/*     */       
/* 749 */       return new ArrayRealVector(out, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double walkInRowOrder(RealMatrixChangingVisitor visitor) {
/* 755 */     int rows = getRowDimension();
/* 756 */     int columns = getColumnDimension();
/* 757 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/* 758 */     for (int row = 0; row < rows; row++) {
/* 759 */       for (int column = 0; column < columns; column++) {
/* 760 */         double oldValue = getEntry(row, column);
/* 761 */         double newValue = visitor.visit(row, column, oldValue);
/* 762 */         setEntry(row, column, newValue);
/*     */       } 
/*     */     } 
/* 765 */     return visitor.end();
/*     */   }
/*     */ 
/*     */   
/*     */   public double walkInRowOrder(RealMatrixPreservingVisitor visitor) {
/* 770 */     int rows = getRowDimension();
/* 771 */     int columns = getColumnDimension();
/* 772 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/* 773 */     for (int row = 0; row < rows; row++) {
/* 774 */       for (int column = 0; column < columns; column++) {
/* 775 */         visitor.visit(row, column, getEntry(row, column));
/*     */       }
/*     */     } 
/* 778 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInRowOrder(RealMatrixChangingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 786 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/* 787 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*     */     
/* 789 */     for (int row = startRow; row <= endRow; row++) {
/* 790 */       for (int column = startColumn; column <= endColumn; column++) {
/* 791 */         double oldValue = getEntry(row, column);
/* 792 */         double newValue = visitor.visit(row, column, oldValue);
/* 793 */         setEntry(row, column, newValue);
/*     */       } 
/*     */     } 
/* 796 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInRowOrder(RealMatrixPreservingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 804 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/* 805 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*     */     
/* 807 */     for (int row = startRow; row <= endRow; row++) {
/* 808 */       for (int column = startColumn; column <= endColumn; column++) {
/* 809 */         visitor.visit(row, column, getEntry(row, column));
/*     */       }
/*     */     } 
/* 812 */     return visitor.end();
/*     */   }
/*     */ 
/*     */   
/*     */   public double walkInColumnOrder(RealMatrixChangingVisitor visitor) {
/* 817 */     int rows = getRowDimension();
/* 818 */     int columns = getColumnDimension();
/* 819 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/* 820 */     for (int column = 0; column < columns; column++) {
/* 821 */       for (int row = 0; row < rows; row++) {
/* 822 */         double oldValue = getEntry(row, column);
/* 823 */         double newValue = visitor.visit(row, column, oldValue);
/* 824 */         setEntry(row, column, newValue);
/*     */       } 
/*     */     } 
/* 827 */     return visitor.end();
/*     */   }
/*     */ 
/*     */   
/*     */   public double walkInColumnOrder(RealMatrixPreservingVisitor visitor) {
/* 832 */     int rows = getRowDimension();
/* 833 */     int columns = getColumnDimension();
/* 834 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/* 835 */     for (int column = 0; column < columns; column++) {
/* 836 */       for (int row = 0; row < rows; row++) {
/* 837 */         visitor.visit(row, column, getEntry(row, column));
/*     */       }
/*     */     } 
/* 840 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInColumnOrder(RealMatrixChangingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 848 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/* 849 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*     */     
/* 851 */     for (int column = startColumn; column <= endColumn; column++) {
/* 852 */       for (int row = startRow; row <= endRow; row++) {
/* 853 */         double oldValue = getEntry(row, column);
/* 854 */         double newValue = visitor.visit(row, column, oldValue);
/* 855 */         setEntry(row, column, newValue);
/*     */       } 
/*     */     } 
/* 858 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInColumnOrder(RealMatrixPreservingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 866 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/* 867 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*     */     
/* 869 */     for (int column = startColumn; column <= endColumn; column++) {
/* 870 */       for (int row = startRow; row <= endRow; row++) {
/* 871 */         visitor.visit(row, column, getEntry(row, column));
/*     */       }
/*     */     } 
/* 874 */     return visitor.end();
/*     */   }
/*     */ 
/*     */   
/*     */   public double walkInOptimizedOrder(RealMatrixChangingVisitor visitor) {
/* 879 */     return walkInRowOrder(visitor);
/*     */   }
/*     */ 
/*     */   
/*     */   public double walkInOptimizedOrder(RealMatrixPreservingVisitor visitor) {
/* 884 */     return walkInRowOrder(visitor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInOptimizedOrder(RealMatrixChangingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 893 */     return walkInRowOrder(visitor, startRow, endRow, startColumn, endColumn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInOptimizedOrder(RealMatrixPreservingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 902 */     return walkInRowOrder(visitor, startRow, endRow, startColumn, endColumn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 911 */     StringBuilder res = new StringBuilder();
/* 912 */     String fullClassName = getClass().getName();
/* 913 */     String shortClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
/* 914 */     res.append(shortClassName);
/* 915 */     res.append(DEFAULT_FORMAT.format(this));
/* 916 */     return res.toString();
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
/*     */   public boolean equals(Object object) {
/* 929 */     if (object == this) {
/* 930 */       return true;
/*     */     }
/* 932 */     if (!(object instanceof RealMatrix)) {
/* 933 */       return false;
/*     */     }
/* 935 */     RealMatrix m = (RealMatrix)object;
/* 936 */     int nRows = getRowDimension();
/* 937 */     int nCols = getColumnDimension();
/* 938 */     if (m.getColumnDimension() != nCols || m.getRowDimension() != nRows) {
/* 939 */       return false;
/*     */     }
/* 941 */     for (int row = 0; row < nRows; row++) {
/* 942 */       for (int col = 0; col < nCols; col++) {
/* 943 */         if (getEntry(row, col) != m.getEntry(row, col)) {
/* 944 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 948 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 958 */     int ret = 7;
/* 959 */     int nRows = getRowDimension();
/* 960 */     int nCols = getColumnDimension();
/* 961 */     ret = ret * 31 + nRows;
/* 962 */     ret = ret * 31 + nCols;
/* 963 */     for (int row = 0; row < nRows; row++) {
/* 964 */       for (int col = 0; col < nCols; col++) {
/* 965 */         ret = ret * 31 + (11 * (row + 1) + 17 * (col + 1)) * MathUtils.hash(getEntry(row, col));
/*     */       }
/*     */     } 
/*     */     
/* 969 */     return ret;
/*     */   }
/*     */   
/*     */   public abstract int getRowDimension();
/*     */   
/*     */   public abstract int getColumnDimension();
/*     */   
/*     */   public abstract RealMatrix createMatrix(int paramInt1, int paramInt2) throws NotStrictlyPositiveException;
/*     */   
/*     */   public abstract RealMatrix copy();
/*     */   
/*     */   public abstract double getEntry(int paramInt1, int paramInt2) throws OutOfRangeException;
/*     */   
/*     */   public abstract void setEntry(int paramInt1, int paramInt2, double paramDouble) throws OutOfRangeException;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/AbstractRealMatrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */