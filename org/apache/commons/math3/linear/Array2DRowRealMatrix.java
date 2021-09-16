/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Array2DRowRealMatrix
/*     */   extends AbstractRealMatrix
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1067294169172445528L;
/*     */   private double[][] data;
/*     */   
/*     */   public Array2DRowRealMatrix() {}
/*     */   
/*     */   public Array2DRowRealMatrix(int rowDimension, int columnDimension) throws NotStrictlyPositiveException {
/*  60 */     super(rowDimension, columnDimension);
/*  61 */     this.data = new double[rowDimension][columnDimension];
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
/*     */   public Array2DRowRealMatrix(double[][] d) throws DimensionMismatchException, NoDataException, NullArgumentException {
/*  79 */     copyIn(d);
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
/*     */   
/*     */   public Array2DRowRealMatrix(double[][] d, boolean copyArray) throws DimensionMismatchException, NoDataException, NullArgumentException {
/* 101 */     if (copyArray) {
/* 102 */       copyIn(d);
/*     */     } else {
/* 104 */       if (d == null) {
/* 105 */         throw new NullArgumentException();
/*     */       }
/* 107 */       int nRows = d.length;
/* 108 */       if (nRows == 0) {
/* 109 */         throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_ROW);
/*     */       }
/* 111 */       int nCols = (d[0]).length;
/* 112 */       if (nCols == 0) {
/* 113 */         throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_COLUMN);
/*     */       }
/* 115 */       for (int r = 1; r < nRows; r++) {
/* 116 */         if ((d[r]).length != nCols) {
/* 117 */           throw new DimensionMismatchException((d[r]).length, nCols);
/*     */         }
/*     */       } 
/* 120 */       this.data = d;
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
/*     */   public Array2DRowRealMatrix(double[] v) {
/* 132 */     int nRows = v.length;
/* 133 */     this.data = new double[nRows][1];
/* 134 */     for (int row = 0; row < nRows; row++) {
/* 135 */       this.data[row][0] = v[row];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix createMatrix(int rowDimension, int columnDimension) throws NotStrictlyPositiveException {
/* 144 */     return new Array2DRowRealMatrix(rowDimension, columnDimension);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix copy() {
/* 150 */     return new Array2DRowRealMatrix(copyOut(), false);
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
/*     */   public Array2DRowRealMatrix add(Array2DRowRealMatrix m) throws MatrixDimensionMismatchException {
/* 164 */     MatrixUtils.checkAdditionCompatible(this, m);
/*     */     
/* 166 */     int rowCount = getRowDimension();
/* 167 */     int columnCount = getColumnDimension();
/* 168 */     double[][] outData = new double[rowCount][columnCount];
/* 169 */     for (int row = 0; row < rowCount; row++) {
/* 170 */       double[] dataRow = this.data[row];
/* 171 */       double[] mRow = m.data[row];
/* 172 */       double[] outDataRow = outData[row];
/* 173 */       for (int col = 0; col < columnCount; col++) {
/* 174 */         outDataRow[col] = dataRow[col] + mRow[col];
/*     */       }
/*     */     } 
/*     */     
/* 178 */     return new Array2DRowRealMatrix(outData, false);
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
/*     */   public Array2DRowRealMatrix subtract(Array2DRowRealMatrix m) throws MatrixDimensionMismatchException {
/* 191 */     MatrixUtils.checkSubtractionCompatible(this, m);
/*     */     
/* 193 */     int rowCount = getRowDimension();
/* 194 */     int columnCount = getColumnDimension();
/* 195 */     double[][] outData = new double[rowCount][columnCount];
/* 196 */     for (int row = 0; row < rowCount; row++) {
/* 197 */       double[] dataRow = this.data[row];
/* 198 */       double[] mRow = m.data[row];
/* 199 */       double[] outDataRow = outData[row];
/* 200 */       for (int col = 0; col < columnCount; col++) {
/* 201 */         outDataRow[col] = dataRow[col] - mRow[col];
/*     */       }
/*     */     } 
/*     */     
/* 205 */     return new Array2DRowRealMatrix(outData, false);
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
/*     */   public Array2DRowRealMatrix multiply(Array2DRowRealMatrix m) throws DimensionMismatchException {
/* 218 */     MatrixUtils.checkMultiplicationCompatible(this, m);
/*     */     
/* 220 */     int nRows = getRowDimension();
/* 221 */     int nCols = m.getColumnDimension();
/* 222 */     int nSum = getColumnDimension();
/*     */     
/* 224 */     double[][] outData = new double[nRows][nCols];
/*     */     
/* 226 */     double[] mCol = new double[nSum];
/* 227 */     double[][] mData = m.data;
/*     */ 
/*     */     
/* 230 */     for (int col = 0; col < nCols; col++) {
/*     */ 
/*     */       
/* 233 */       for (int mRow = 0; mRow < nSum; mRow++) {
/* 234 */         mCol[mRow] = mData[mRow][col];
/*     */       }
/*     */       
/* 237 */       for (int row = 0; row < nRows; row++) {
/* 238 */         double[] dataRow = this.data[row];
/* 239 */         double sum = 0.0D;
/* 240 */         for (int i = 0; i < nSum; i++) {
/* 241 */           sum += dataRow[i] * mCol[i];
/*     */         }
/* 243 */         outData[row][col] = sum;
/*     */       } 
/*     */     } 
/*     */     
/* 247 */     return new Array2DRowRealMatrix(outData, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getData() {
/* 253 */     return copyOut();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getDataRef() {
/* 262 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubMatrix(double[][] subMatrix, int row, int column) throws NoDataException, OutOfRangeException, DimensionMismatchException, NullArgumentException {
/* 271 */     if (this.data == null) {
/* 272 */       if (row > 0) {
/* 273 */         throw new MathIllegalStateException(LocalizedFormats.FIRST_ROWS_NOT_INITIALIZED_YET, new Object[] { Integer.valueOf(row) });
/*     */       }
/* 275 */       if (column > 0) {
/* 276 */         throw new MathIllegalStateException(LocalizedFormats.FIRST_COLUMNS_NOT_INITIALIZED_YET, new Object[] { Integer.valueOf(column) });
/*     */       }
/* 278 */       MathUtils.checkNotNull(subMatrix);
/* 279 */       int nRows = subMatrix.length;
/* 280 */       if (nRows == 0) {
/* 281 */         throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_ROW);
/*     */       }
/*     */       
/* 284 */       int nCols = (subMatrix[0]).length;
/* 285 */       if (nCols == 0) {
/* 286 */         throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_COLUMN);
/*     */       }
/* 288 */       this.data = new double[subMatrix.length][nCols];
/* 289 */       for (int i = 0; i < this.data.length; i++) {
/* 290 */         if ((subMatrix[i]).length != nCols) {
/* 291 */           throw new DimensionMismatchException((subMatrix[i]).length, nCols);
/*     */         }
/* 293 */         System.arraycopy(subMatrix[i], 0, this.data[i + row], column, nCols);
/*     */       } 
/*     */     } else {
/* 296 */       super.setSubMatrix(subMatrix, row, column);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEntry(int row, int column) throws OutOfRangeException {
/* 305 */     MatrixUtils.checkMatrixIndex(this, row, column);
/* 306 */     return this.data[row][column];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntry(int row, int column, double value) throws OutOfRangeException {
/* 313 */     MatrixUtils.checkMatrixIndex(this, row, column);
/* 314 */     this.data[row][column] = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToEntry(int row, int column, double increment) throws OutOfRangeException {
/* 322 */     MatrixUtils.checkMatrixIndex(this, row, column);
/* 323 */     this.data[row][column] = this.data[row][column] + increment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyEntry(int row, int column, double factor) throws OutOfRangeException {
/* 331 */     MatrixUtils.checkMatrixIndex(this, row, column);
/* 332 */     this.data[row][column] = this.data[row][column] * factor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowDimension() {
/* 338 */     return (this.data == null) ? 0 : this.data.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnDimension() {
/* 344 */     return (this.data == null || this.data[0] == null) ? 0 : (this.data[0]).length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] operate(double[] v) throws DimensionMismatchException {
/* 351 */     int nRows = getRowDimension();
/* 352 */     int nCols = getColumnDimension();
/* 353 */     if (v.length != nCols) {
/* 354 */       throw new DimensionMismatchException(v.length, nCols);
/*     */     }
/* 356 */     double[] out = new double[nRows];
/* 357 */     for (int row = 0; row < nRows; row++) {
/* 358 */       double[] dataRow = this.data[row];
/* 359 */       double sum = 0.0D;
/* 360 */       for (int i = 0; i < nCols; i++) {
/* 361 */         sum += dataRow[i] * v[i];
/*     */       }
/* 363 */       out[row] = sum;
/*     */     } 
/* 365 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] preMultiply(double[] v) throws DimensionMismatchException {
/* 372 */     int nRows = getRowDimension();
/* 373 */     int nCols = getColumnDimension();
/* 374 */     if (v.length != nRows) {
/* 375 */       throw new DimensionMismatchException(v.length, nRows);
/*     */     }
/*     */     
/* 378 */     double[] out = new double[nCols];
/* 379 */     for (int col = 0; col < nCols; col++) {
/* 380 */       double sum = 0.0D;
/* 381 */       for (int i = 0; i < nRows; i++) {
/* 382 */         sum += this.data[i][col] * v[i];
/*     */       }
/* 384 */       out[col] = sum;
/*     */     } 
/*     */     
/* 387 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInRowOrder(RealMatrixChangingVisitor visitor) {
/* 394 */     int rows = getRowDimension();
/* 395 */     int columns = getColumnDimension();
/* 396 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/* 397 */     for (int i = 0; i < rows; i++) {
/* 398 */       double[] rowI = this.data[i];
/* 399 */       for (int j = 0; j < columns; j++) {
/* 400 */         rowI[j] = visitor.visit(i, j, rowI[j]);
/*     */       }
/*     */     } 
/* 403 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInRowOrder(RealMatrixPreservingVisitor visitor) {
/* 409 */     int rows = getRowDimension();
/* 410 */     int columns = getColumnDimension();
/* 411 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/* 412 */     for (int i = 0; i < rows; i++) {
/* 413 */       double[] rowI = this.data[i];
/* 414 */       for (int j = 0; j < columns; j++) {
/* 415 */         visitor.visit(i, j, rowI[j]);
/*     */       }
/*     */     } 
/* 418 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInRowOrder(RealMatrixChangingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 427 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/* 428 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*     */     
/* 430 */     for (int i = startRow; i <= endRow; i++) {
/* 431 */       double[] rowI = this.data[i];
/* 432 */       for (int j = startColumn; j <= endColumn; j++) {
/* 433 */         rowI[j] = visitor.visit(i, j, rowI[j]);
/*     */       }
/*     */     } 
/* 436 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInRowOrder(RealMatrixPreservingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 445 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/* 446 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*     */     
/* 448 */     for (int i = startRow; i <= endRow; i++) {
/* 449 */       double[] rowI = this.data[i];
/* 450 */       for (int j = startColumn; j <= endColumn; j++) {
/* 451 */         visitor.visit(i, j, rowI[j]);
/*     */       }
/*     */     } 
/* 454 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInColumnOrder(RealMatrixChangingVisitor visitor) {
/* 460 */     int rows = getRowDimension();
/* 461 */     int columns = getColumnDimension();
/* 462 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/* 463 */     for (int j = 0; j < columns; j++) {
/* 464 */       for (int i = 0; i < rows; i++) {
/* 465 */         double[] rowI = this.data[i];
/* 466 */         rowI[j] = visitor.visit(i, j, rowI[j]);
/*     */       } 
/*     */     } 
/* 469 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInColumnOrder(RealMatrixPreservingVisitor visitor) {
/* 475 */     int rows = getRowDimension();
/* 476 */     int columns = getColumnDimension();
/* 477 */     visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
/* 478 */     for (int j = 0; j < columns; j++) {
/* 479 */       for (int i = 0; i < rows; i++) {
/* 480 */         visitor.visit(i, j, this.data[i][j]);
/*     */       }
/*     */     } 
/* 483 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInColumnOrder(RealMatrixChangingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 492 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/* 493 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*     */     
/* 495 */     for (int j = startColumn; j <= endColumn; j++) {
/* 496 */       for (int i = startRow; i <= endRow; i++) {
/* 497 */         double[] rowI = this.data[i];
/* 498 */         rowI[j] = visitor.visit(i, j, rowI[j]);
/*     */       } 
/*     */     } 
/* 501 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInColumnOrder(RealMatrixPreservingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
/* 510 */     MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
/* 511 */     visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
/*     */     
/* 513 */     for (int j = startColumn; j <= endColumn; j++) {
/* 514 */       for (int i = startRow; i <= endRow; i++) {
/* 515 */         visitor.visit(i, j, this.data[i][j]);
/*     */       }
/*     */     } 
/* 518 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[][] copyOut() {
/* 527 */     int nRows = getRowDimension();
/* 528 */     double[][] out = new double[nRows][getColumnDimension()];
/*     */     
/* 530 */     for (int i = 0; i < nRows; i++) {
/* 531 */       System.arraycopy(this.data[i], 0, out[i], 0, (this.data[i]).length);
/*     */     }
/* 533 */     return out;
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
/*     */   private void copyIn(double[][] in) throws DimensionMismatchException, NoDataException, NullArgumentException {
/* 546 */     setSubMatrix(in, 0, 0);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/Array2DRowRealMatrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */