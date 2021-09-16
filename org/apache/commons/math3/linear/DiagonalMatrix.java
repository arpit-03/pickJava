/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiagonalMatrix
/*     */   extends AbstractRealMatrix
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20121229L;
/*     */   private final double[] data;
/*     */   
/*     */   public DiagonalMatrix(int dimension) throws NotStrictlyPositiveException {
/*  51 */     super(dimension, dimension);
/*  52 */     this.data = new double[dimension];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DiagonalMatrix(double[] d) {
/*  63 */     this(d, true);
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
/*     */   public DiagonalMatrix(double[] d, boolean copyArray) throws NullArgumentException {
/*  82 */     MathUtils.checkNotNull(d);
/*  83 */     this.data = copyArray ? (double[])d.clone() : d;
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
/*     */   public RealMatrix createMatrix(int rowDimension, int columnDimension) throws NotStrictlyPositiveException, DimensionMismatchException {
/*  96 */     if (rowDimension != columnDimension) {
/*  97 */       throw new DimensionMismatchException(rowDimension, columnDimension);
/*     */     }
/*     */     
/* 100 */     return new DiagonalMatrix(rowDimension);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix copy() {
/* 106 */     return new DiagonalMatrix(this.data);
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
/*     */   public DiagonalMatrix add(DiagonalMatrix m) throws MatrixDimensionMismatchException {
/* 120 */     MatrixUtils.checkAdditionCompatible(this, m);
/*     */     
/* 122 */     int dim = getRowDimension();
/* 123 */     double[] outData = new double[dim];
/* 124 */     for (int i = 0; i < dim; i++) {
/* 125 */       outData[i] = this.data[i] + m.data[i];
/*     */     }
/*     */     
/* 128 */     return new DiagonalMatrix(outData, false);
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
/*     */   public DiagonalMatrix subtract(DiagonalMatrix m) throws MatrixDimensionMismatchException {
/* 141 */     MatrixUtils.checkSubtractionCompatible(this, m);
/*     */     
/* 143 */     int dim = getRowDimension();
/* 144 */     double[] outData = new double[dim];
/* 145 */     for (int i = 0; i < dim; i++) {
/* 146 */       outData[i] = this.data[i] - m.data[i];
/*     */     }
/*     */     
/* 149 */     return new DiagonalMatrix(outData, false);
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
/*     */   public DiagonalMatrix multiply(DiagonalMatrix m) throws DimensionMismatchException {
/* 162 */     MatrixUtils.checkMultiplicationCompatible(this, m);
/*     */     
/* 164 */     int dim = getRowDimension();
/* 165 */     double[] outData = new double[dim];
/* 166 */     for (int i = 0; i < dim; i++) {
/* 167 */       outData[i] = this.data[i] * m.data[i];
/*     */     }
/*     */     
/* 170 */     return new DiagonalMatrix(outData, false);
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
/*     */   public RealMatrix multiply(RealMatrix m) throws DimensionMismatchException {
/* 184 */     if (m instanceof DiagonalMatrix) {
/* 185 */       return multiply((DiagonalMatrix)m);
/*     */     }
/* 187 */     MatrixUtils.checkMultiplicationCompatible(this, m);
/* 188 */     int nRows = m.getRowDimension();
/* 189 */     int nCols = m.getColumnDimension();
/* 190 */     double[][] product = new double[nRows][nCols];
/* 191 */     for (int r = 0; r < nRows; r++) {
/* 192 */       for (int c = 0; c < nCols; c++) {
/* 193 */         product[r][c] = this.data[r] * m.getEntry(r, c);
/*     */       }
/*     */     } 
/* 196 */     return new Array2DRowRealMatrix(product, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getData() {
/* 203 */     int dim = getRowDimension();
/* 204 */     double[][] out = new double[dim][dim];
/*     */     
/* 206 */     for (int i = 0; i < dim; i++) {
/* 207 */       out[i][i] = this.data[i];
/*     */     }
/*     */     
/* 210 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getDataRef() {
/* 219 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEntry(int row, int column) throws OutOfRangeException {
/* 226 */     MatrixUtils.checkMatrixIndex(this, row, column);
/* 227 */     return (row == column) ? this.data[row] : 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntry(int row, int column, double value) throws OutOfRangeException, NumberIsTooLargeException {
/* 236 */     if (row == column) {
/* 237 */       MatrixUtils.checkRowIndex(this, row);
/* 238 */       this.data[row] = value;
/*     */     } else {
/* 240 */       ensureZero(value);
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
/*     */   public void addToEntry(int row, int column, double increment) throws OutOfRangeException, NumberIsTooLargeException {
/* 252 */     if (row == column) {
/* 253 */       MatrixUtils.checkRowIndex(this, row);
/* 254 */       this.data[row] = this.data[row] + increment;
/*     */     } else {
/* 256 */       ensureZero(increment);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyEntry(int row, int column, double factor) throws OutOfRangeException {
/* 267 */     if (row == column) {
/* 268 */       MatrixUtils.checkRowIndex(this, row);
/* 269 */       this.data[row] = this.data[row] * factor;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowDimension() {
/* 276 */     return this.data.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnDimension() {
/* 282 */     return this.data.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] operate(double[] v) throws DimensionMismatchException {
/* 289 */     return multiply(new DiagonalMatrix(v, false)).getDataRef();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] preMultiply(double[] v) throws DimensionMismatchException {
/* 296 */     return operate(v);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector preMultiply(RealVector v) throws DimensionMismatchException {
/*     */     double[] vectorData;
/* 303 */     if (v instanceof ArrayRealVector) {
/* 304 */       vectorData = ((ArrayRealVector)v).getDataRef();
/*     */     } else {
/* 306 */       vectorData = v.toArray();
/*     */     } 
/* 308 */     return MatrixUtils.createRealVector(preMultiply(vectorData));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureZero(double value) throws NumberIsTooLargeException {
/* 316 */     if (!Precision.equals(0.0D, value, 1)) {
/* 317 */       throw new NumberIsTooLargeException(Double.valueOf(FastMath.abs(value)), Integer.valueOf(0), true);
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
/*     */   public DiagonalMatrix inverse() throws SingularMatrixException {
/* 332 */     return inverse(0.0D);
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
/*     */   public DiagonalMatrix inverse(double threshold) throws SingularMatrixException {
/* 344 */     if (isSingular(threshold)) {
/* 345 */       throw new SingularMatrixException();
/*     */     }
/*     */     
/* 348 */     double[] result = new double[this.data.length];
/* 349 */     for (int i = 0; i < this.data.length; i++) {
/* 350 */       result[i] = 1.0D / this.data[i];
/*     */     }
/* 352 */     return new DiagonalMatrix(result, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSingular(double threshold) {
/* 363 */     for (int i = 0; i < this.data.length; i++) {
/* 364 */       if (Precision.equals(this.data[i], 0.0D, threshold)) {
/* 365 */         return true;
/*     */       }
/*     */     } 
/* 368 */     return false;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/DiagonalMatrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */