/*      */ package org.la4j;
/*      */ 
/*      */ import java.math.BigDecimal;
/*      */ import java.math.RoundingMode;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.Iterator;
/*      */ import java.util.Random;
/*      */ import java.util.StringTokenizer;
/*      */ import org.la4j.decomposition.MatrixDecompositor;
/*      */ import org.la4j.inversion.MatrixInverter;
/*      */ import org.la4j.iterator.ColumnMajorMatrixIterator;
/*      */ import org.la4j.iterator.MatrixIterator;
/*      */ import org.la4j.iterator.RowMajorMatrixIterator;
/*      */ import org.la4j.iterator.VectorIterator;
/*      */ import org.la4j.linear.LinearSystemSolver;
/*      */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*      */ import org.la4j.matrix.DenseMatrix;
/*      */ import org.la4j.matrix.MatrixFactory;
/*      */ import org.la4j.matrix.RowMajorSparseMatrix;
/*      */ import org.la4j.matrix.SparseMatrix;
/*      */ import org.la4j.matrix.dense.Basic1DMatrix;
/*      */ import org.la4j.matrix.dense.Basic2DMatrix;
/*      */ import org.la4j.matrix.functor.AdvancedMatrixPredicate;
/*      */ import org.la4j.matrix.functor.MatrixAccumulator;
/*      */ import org.la4j.matrix.functor.MatrixFunction;
/*      */ import org.la4j.matrix.functor.MatrixPredicate;
/*      */ import org.la4j.matrix.functor.MatrixProcedure;
/*      */ import org.la4j.operation.MatrixMatrixOperation;
/*      */ import org.la4j.operation.MatrixOperation;
/*      */ import org.la4j.operation.MatrixVectorOperation;
/*      */ import org.la4j.vector.functor.VectorAccumulator;
/*      */ import org.la4j.vector.functor.VectorFunction;
/*      */ import org.la4j.vector.functor.VectorProcedure;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Matrix
/*      */   implements Iterable<Double>
/*      */ {
/*      */   private static final String DEFAULT_ROWS_DELIMITER = "\n";
/*      */   private static final String DEFAULT_COLUMNS_DELIMITER = " ";
/*   71 */   private static final NumberFormat DEFAULT_FORMATTER = new DecimalFormat("0.000");
/*   72 */   private static final String[] INDENTS = new String[] { " ", "  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          " };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int rows;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int columns;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Matrix zero(int rows, int columns) {
/*   90 */     long size = rows * columns;
/*   91 */     return (size > 1000L) ? (Matrix)SparseMatrix.zero(rows, columns) : (Matrix)DenseMatrix.zero(rows, columns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Matrix constant(int rows, int columns, double constant) {
/*   98 */     return (Matrix)DenseMatrix.constant(rows, columns, constant);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Matrix diagonal(int size, double diagonal) {
/*  106 */     return (Matrix)SparseMatrix.diagonal(size, diagonal);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Matrix unit(int rows, int columns) {
/*  114 */     return (Matrix)DenseMatrix.unit(rows, columns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Matrix identity(int size) {
/*  121 */     return (Matrix)SparseMatrix.identity(size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Matrix random(int rows, int columns, Random random) {
/*  129 */     return (Matrix)DenseMatrix.random(rows, columns, random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Matrix randomSymmetric(int size, Random random) {
/*  136 */     return (Matrix)DenseMatrix.randomSymmetric(size, random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Matrix from1DArray(int rows, int columns, double[] array) {
/*  144 */     return (Matrix)Basic1DMatrix.from1DArray(rows, columns, array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Matrix from2DArray(double[][] array) {
/*  152 */     return (Matrix)Basic2DMatrix.from2DArray(array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Matrix block(Matrix a, Matrix b, Matrix c, Matrix d) {
/*  160 */     return (Matrix)DenseMatrix.block(a, b, c, d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Matrix fromCSV(String csv) {
/*      */     Matrix matrix;
/*  171 */     StringTokenizer lines = new StringTokenizer(csv, "\n");
/*  172 */     DenseMatrix denseMatrix = DenseMatrix.zero(10, 10);
/*  173 */     int rows = 0, columns = 0;
/*      */     
/*  175 */     while (lines.hasMoreTokens()) {
/*  176 */       if (denseMatrix.rows() == rows) {
/*  177 */         matrix = denseMatrix.copyOfRows(rows * 3 / 2 + 1);
/*      */       }
/*      */       
/*  180 */       StringTokenizer elements = new StringTokenizer(lines.nextToken(), ", ");
/*  181 */       int j = 0;
/*  182 */       while (elements.hasMoreElements()) {
/*  183 */         if (j == matrix.columns()) {
/*  184 */           matrix = matrix.copyOfColumns(j * 3 / 2 + 1);
/*      */         }
/*      */         
/*  187 */         double x = Double.valueOf(elements.nextToken()).doubleValue();
/*  188 */         matrix.set(rows, j++, x);
/*      */       } 
/*      */       
/*  191 */       rows++;
/*  192 */       columns = (j > columns) ? j : columns;
/*      */     } 
/*      */     
/*  195 */     return matrix.copyOfShape(rows, columns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Matrix fromMatrixMarket(String mm) {
/*  206 */     StringTokenizer body = new StringTokenizer(mm, "\n");
/*      */     
/*  208 */     String headerString = body.nextToken();
/*  209 */     StringTokenizer header = new StringTokenizer(headerString);
/*      */     
/*  211 */     if (!"%%MatrixMarket".equals(header.nextToken())) {
/*  212 */       throw new IllegalArgumentException("Wrong input file format: can not read header '%%MatrixMarket'.");
/*      */     }
/*      */     
/*  215 */     String object = header.nextToken();
/*  216 */     if (!"matrix".equals(object)) {
/*  217 */       throw new IllegalArgumentException("Unexpected object: " + object + ".");
/*      */     }
/*      */     
/*  220 */     String format = header.nextToken();
/*  221 */     if (!"coordinate".equals(format) && !"array".equals(format)) {
/*  222 */       throw new IllegalArgumentException("Unknown format: " + format + ".");
/*      */     }
/*      */     
/*  225 */     String field = header.nextToken();
/*  226 */     if (!"real".equals(field)) {
/*  227 */       throw new IllegalArgumentException("Unknown field type: " + field + ".");
/*      */     }
/*      */     
/*  230 */     String symmetry = header.nextToken();
/*  231 */     if (!symmetry.equals("general")) {
/*  232 */       throw new IllegalArgumentException("Unknown symmetry type: " + symmetry + ".");
/*      */     }
/*      */     
/*  235 */     String majority = header.hasMoreTokens() ? header.nextToken() : "row-major";
/*      */     
/*  237 */     String nextToken = body.nextToken();
/*  238 */     while (nextToken.startsWith("%")) {
/*  239 */       nextToken = body.nextToken();
/*      */     }
/*      */     
/*  242 */     if ("coordinate".equals(format)) {
/*  243 */       StringTokenizer stringTokenizer = new StringTokenizer(nextToken);
/*      */       
/*  245 */       int j = Integer.valueOf(stringTokenizer.nextToken()).intValue();
/*  246 */       int m = Integer.valueOf(stringTokenizer.nextToken()).intValue();
/*  247 */       int cardinality = Integer.valueOf(stringTokenizer.nextToken()).intValue();
/*      */       
/*  249 */       Matrix result = "row-major".equals(majority) ? (Matrix)RowMajorSparseMatrix.zero(j, m, cardinality) : (Matrix)ColumnMajorSparseMatrix.zero(j, m, cardinality);
/*      */ 
/*      */ 
/*      */       
/*  253 */       for (int k = 0; k < cardinality; k++) {
/*  254 */         stringTokenizer = new StringTokenizer(body.nextToken());
/*      */         
/*  256 */         int n = Integer.valueOf(stringTokenizer.nextToken()).intValue();
/*  257 */         int i1 = Integer.valueOf(stringTokenizer.nextToken()).intValue();
/*  258 */         double x = Double.valueOf(stringTokenizer.nextToken()).doubleValue();
/*  259 */         result.set(n - 1, i1 - 1, x);
/*      */       } 
/*      */       
/*  262 */       return result;
/*      */     } 
/*  264 */     StringTokenizer lines = new StringTokenizer(nextToken);
/*      */     
/*  266 */     int rows = Integer.valueOf(lines.nextToken()).intValue();
/*  267 */     int columns = Integer.valueOf(lines.nextToken()).intValue();
/*  268 */     DenseMatrix denseMatrix = DenseMatrix.zero(rows, columns);
/*      */     
/*  270 */     for (int i = 0; i < rows; i++) {
/*  271 */       for (int j = 0; j < columns; j++) {
/*  272 */         denseMatrix.set(i, j, Double.valueOf(body.nextToken()).doubleValue());
/*      */       }
/*      */     } 
/*      */     
/*  276 */     return (Matrix)denseMatrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix() {
/*  287 */     this(0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix(int rows, int columns) {
/*  294 */     ensureDimensionsAreCorrect(rows, columns);
/*  295 */     this.rows = rows;
/*  296 */     this.columns = columns;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAll(double value) {
/*  418 */     MatrixIterator it = iterator();
/*      */     
/*  420 */     while (it.hasNext()) {
/*  421 */       it.next();
/*  422 */       it.set(value);
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
/*      */   public void setRow(int i, double value) {
/*  435 */     VectorIterator it = iteratorOfRow(i);
/*      */     
/*  437 */     while (it.hasNext()) {
/*  438 */       it.next();
/*  439 */       it.set(value);
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
/*      */   public void setColumn(int j, double value) {
/*  452 */     VectorIterator it = iteratorOfColumn(j);
/*      */     
/*  454 */     while (it.hasNext()) {
/*  455 */       it.next();
/*  456 */       it.set(value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void swapRows(int i, int j) {
/*  467 */     if (i != j) {
/*  468 */       Vector ii = getRow(i);
/*  469 */       Vector jj = getRow(j);
/*      */       
/*  471 */       setRow(i, jj);
/*  472 */       setRow(j, ii);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void swapColumns(int i, int j) {
/*  483 */     if (i != j) {
/*  484 */       Vector ii = getColumn(i);
/*  485 */       Vector jj = getColumn(j);
/*      */       
/*  487 */       setColumn(i, jj);
/*  488 */       setColumn(j, ii);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int rows() {
/*  498 */     return this.rows;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int columns() {
/*  507 */     return this.columns;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix transpose() {
/*  516 */     Matrix result = blankOfShape(this.columns, this.rows);
/*  517 */     MatrixIterator it = result.iterator();
/*      */     
/*  519 */     while (it.hasNext()) {
/*  520 */       it.next();
/*  521 */       int i = it.rowIndex();
/*  522 */       int j = it.columnIndex();
/*  523 */       it.set(get(j, i));
/*      */     } 
/*      */     
/*  526 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix rotate() {
/*  535 */     Matrix result = blankOfShape(this.columns, this.rows);
/*  536 */     MatrixIterator it = result.iterator();
/*      */     
/*  538 */     while (it.hasNext()) {
/*  539 */       it.next();
/*  540 */       int i = it.rowIndex();
/*  541 */       int j = it.columnIndex();
/*  542 */       it.set(get(this.rows - 1 - j, i));
/*      */     } 
/*      */     
/*  545 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix power(int n) {
/*  556 */     if (n < 0) {
/*  557 */       fail("The exponent should be positive: " + n + ".");
/*      */     }
/*      */     
/*  560 */     Matrix result = blankOfShape(this.rows, this.rows);
/*  561 */     Matrix that = this;
/*      */     
/*  563 */     for (int i = 0; i < this.rows; i++) {
/*  564 */       result.set(i, i, 1.0D);
/*      */     }
/*      */     
/*  567 */     while (n > 0) {
/*  568 */       if (n % 2 == 1) {
/*  569 */         result = result.multiply(that);
/*      */       }
/*      */       
/*  572 */       n /= 2;
/*  573 */       that = that.multiply(that);
/*      */     } 
/*      */     
/*  576 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix multiply(double value) {
/*  587 */     Matrix result = blank();
/*  588 */     MatrixIterator it = iterator();
/*      */     
/*  590 */     while (it.hasNext()) {
/*  591 */       double x = ((Double)it.next()).doubleValue();
/*  592 */       int i = it.rowIndex();
/*  593 */       int j = it.columnIndex();
/*  594 */       result.set(i, j, x * value);
/*      */     } 
/*      */     
/*  597 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector multiply(Vector that) {
/*  608 */     return apply(LinearAlgebra.OO_PLACE_MATRIX_BY_VECTOR_MULTIPLICATION, that);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix multiply(Matrix that) {
/*  619 */     return apply(LinearAlgebra.OO_PLACE_MATRICES_MULTIPLICATION, that);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix multiplyByItsTranspose() {
/*  628 */     return apply(LinearAlgebra.OO_PLACE_MATRIX_BY_ITS_TRANSPOSE_MULTIPLICATION);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix subtract(double value) {
/*  639 */     return add(-value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix subtract(Matrix that) {
/*  650 */     return apply(LinearAlgebra.OO_PLACE_MATRICES_SUBTRACTION, that);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix add(double value) {
/*  661 */     MatrixIterator it = iterator();
/*  662 */     Matrix result = blank();
/*      */     
/*  664 */     while (it.hasNext()) {
/*  665 */       double x = ((Double)it.next()).doubleValue();
/*  666 */       int i = it.rowIndex();
/*  667 */       int j = it.columnIndex();
/*  668 */       result.set(i, j, x + value);
/*      */     } 
/*      */     
/*  671 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix add(Matrix that) {
/*  682 */     return apply(LinearAlgebra.OO_PLACE_MATRIX_ADDITION, that);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix insert(Matrix that) {
/*  693 */     return insert(that, 0, 0, 0, 0, that.rows(), that.columns());
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
/*      */   public Matrix insert(Matrix that, int rows, int columns) {
/*  706 */     return insert(that, 0, 0, 0, 0, rows, columns);
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
/*      */   public Matrix insert(Matrix that, int destRow, int destColumn, int rows, int columns) {
/*  721 */     return insert(that, 0, 0, destRow, destColumn, rows, columns);
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
/*      */   public Matrix insert(Matrix that, int srcRow, int srcColumn, int destRow, int destColumn, int rows, int columns) {
/*  738 */     if (rows < 0 || columns < 0) {
/*  739 */       fail("Cannot have negative rows or columns: " + rows + "x" + columns);
/*      */     }
/*      */     
/*  742 */     if (destRow < 0 || destColumn < 0) {
/*  743 */       fail("Cannot have negative destination position: " + destRow + ", " + destColumn);
/*      */     }
/*      */     
/*  746 */     if (destRow > this.rows || destColumn > this.columns) {
/*  747 */       fail("Destination position out of bounds: " + destRow + ", " + destColumn);
/*      */     }
/*      */     
/*  750 */     if (srcRow < 0 || srcColumn < 0) {
/*  751 */       fail("Cannot have negative source position: " + destRow + ", " + destColumn);
/*      */     }
/*      */     
/*  754 */     if (srcRow > that.rows || srcColumn > that.columns) {
/*  755 */       fail("Destination position out of bounds: " + srcRow + ", " + srcColumn);
/*      */     }
/*      */     
/*  758 */     if (destRow + rows > this.rows || destColumn + columns > this.columns) {
/*  759 */       fail("Out of bounds: Cannot add " + rows + " rows and " + columns + " cols at " + destRow + ", " + destColumn + " in a " + this.rows + "x" + this.columns + " matrix.");
/*      */     }
/*      */ 
/*      */     
/*  763 */     if (srcRow + rows > that.rows || srcColumn + columns > that.columns) {
/*  764 */       fail("Out of bounds: Cannot get " + rows + " rows and " + columns + " cols at " + srcRow + ", " + srcColumn + " from a " + that.rows + "x" + that.columns + " matrix.");
/*      */     }
/*      */ 
/*      */     
/*  768 */     Matrix result = copy();
/*      */     
/*  770 */     for (int i = 0; i < rows; i++) {
/*  771 */       for (int j = 0; j < columns; j++) {
/*  772 */         result.set(i + destRow, j + destColumn, that.get(i + srcRow, j + srcColumn));
/*      */       }
/*      */     } 
/*      */     
/*  776 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix divide(double value) {
/*  787 */     return multiply(1.0D / value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix kroneckerProduct(Matrix that) {
/*  798 */     return apply(LinearAlgebra.OO_PLACE_KRONECKER_PRODUCT, that);
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
/*      */   public double trace() {
/*  812 */     double result = 0.0D;
/*      */     
/*  814 */     for (int i = 0; i < this.rows; i++) {
/*  815 */       result += get(i, i);
/*      */     }
/*      */     
/*  818 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double diagonalProduct() {
/*  827 */     BigDecimal result = BigDecimal.ONE;
/*      */     
/*  829 */     for (int i = 0; i < this.rows; i++) {
/*  830 */       result = result.multiply(BigDecimal.valueOf(get(i, i)));
/*      */     }
/*      */     
/*  833 */     return result.setScale(Matrices.ROUND_FACTOR, RoundingMode.CEILING).doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double norm() {
/*  844 */     return euclideanNorm();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double euclideanNorm() {
/*  853 */     return fold(Matrices.mkEuclideanNormAccumulator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double manhattanNorm() {
/*  862 */     return fold(Matrices.mkManhattanNormAccumulator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double infinityNorm() {
/*  871 */     return fold(Matrices.mkInfinityNormAccumulator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double product() {
/*  880 */     return fold(Matrices.asProductAccumulator(1.0D));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double sum() {
/*  889 */     return fold(Matrices.asSumAccumulator(0.0D));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix hadamardProduct(Matrix that) {
/*  900 */     return apply(LinearAlgebra.OO_PLACE_MATRIX_HADAMARD_PRODUCT, that);
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
/*      */   public double determinant() {
/*  914 */     if (this.rows != this.columns) {
/*  915 */       throw new IllegalStateException("Can not compute determinant of non-square matrix.");
/*      */     }
/*      */     
/*  918 */     if (this.rows == 0)
/*  919 */       return 0.0D; 
/*  920 */     if (this.rows == 1)
/*  921 */       return get(0, 0); 
/*  922 */     if (this.rows == 2) {
/*  923 */       return get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0);
/*      */     }
/*  925 */     if (this.rows == 3) {
/*  926 */       return get(0, 0) * get(1, 1) * get(2, 2) + get(0, 1) * get(1, 2) * get(2, 0) + get(0, 2) * get(1, 0) * get(2, 1) - get(0, 2) * get(1, 1) * get(2, 0) - get(0, 1) * get(1, 0) * get(2, 2) - get(0, 0) * get(1, 2) * get(2, 1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  934 */     MatrixDecompositor decompositor = withDecompositor(LinearAlgebra.LU);
/*  935 */     Matrix[] lup = decompositor.decompose();
/*      */     
/*  937 */     Matrix u = lup[1];
/*  938 */     Matrix p = lup[2];
/*      */     
/*  940 */     double result = u.diagonalProduct();
/*      */ 
/*      */ 
/*      */     
/*  944 */     int[] permutations = new int[p.rows()];
/*  945 */     for (int i = 0; i < p.rows(); i++) {
/*  946 */       for (int k = 0; k < p.columns(); k++) {
/*  947 */         if (p.get(i, k) > 0.0D) {
/*  948 */           permutations[i] = k;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  954 */     int sign = 1;
/*  955 */     for (int j = 0; j < permutations.length; j++) {
/*  956 */       for (int k = j + 1; k < permutations.length; k++) {
/*  957 */         if (permutations[k] < permutations[j]) {
/*  958 */           sign *= -1;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  963 */     return sign * result;
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
/*      */   public int rank() {
/*  977 */     if (this.rows == 0 || this.columns == 0) {
/*  978 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  985 */     MatrixDecompositor decompositor = withDecompositor(LinearAlgebra.SVD);
/*  986 */     Matrix[] usv = decompositor.decompose();
/*      */     
/*  988 */     Matrix s = usv[1];
/*  989 */     double tolerance = Math.max(this.rows, this.columns) * s.get(0, 0) * Matrices.EPS;
/*      */     
/*  991 */     int result = 0;
/*  992 */     for (int i = 0; i < s.rows(); i++) {
/*  993 */       if (s.get(i, i) > tolerance) {
/*  994 */         result++;
/*      */       }
/*      */     } 
/*      */     
/*  998 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRow(int i, Vector row) {
/* 1008 */     if (this.columns != row.length()) {
/* 1009 */       fail("Wrong vector length: " + row.length() + ". Should be: " + this.columns + ".");
/*      */     }
/*      */     
/* 1012 */     for (int j = 0; j < row.length(); j++) {
/* 1013 */       set(i, j, row.get(j));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumn(int j, Vector column) {
/* 1024 */     if (this.rows != column.length()) {
/* 1025 */       fail("Wrong vector length: " + column.length() + ". Should be: " + this.rows + ".");
/*      */     }
/*      */     
/* 1028 */     for (int i = 0; i < column.length(); i++) {
/* 1029 */       set(i, j, column.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix insertRow(int i, Vector row) {
/* 1039 */     if (i >= this.rows || i < 0) {
/* 1040 */       throw new IndexOutOfBoundsException("Illegal row number, must be 0.." + (this.rows - 1));
/*      */     }
/*      */     
/* 1043 */     Matrix result = blankOfShape(this.rows + 1, this.columns);
/*      */     int ii;
/* 1045 */     for (ii = 0; ii < i; ii++) {
/* 1046 */       result.setRow(ii, getRow(ii));
/*      */     }
/*      */     
/* 1049 */     result.setRow(i, row);
/*      */     
/* 1051 */     for (ii = i; ii < this.rows; ii++) {
/* 1052 */       result.setRow(ii + 1, getRow(ii));
/*      */     }
/*      */     
/* 1055 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix insertColumn(int j, Vector column) {
/* 1064 */     if (j >= this.columns || j < 0) {
/* 1065 */       throw new IndexOutOfBoundsException("Illegal row number, must be 0.." + (this.columns - 1));
/*      */     }
/*      */     
/* 1068 */     Matrix result = blankOfShape(this.rows, this.columns + 1);
/*      */     int jj;
/* 1070 */     for (jj = 0; jj < j; jj++) {
/* 1071 */       result.setColumn(jj, getColumn(jj));
/*      */     }
/*      */     
/* 1074 */     result.setColumn(j, column);
/*      */     
/* 1076 */     for (jj = j; jj < this.columns; jj++) {
/* 1077 */       result.setColumn(jj + 1, getColumn(jj));
/*      */     }
/*      */     
/* 1080 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix removeRow(int i) {
/* 1089 */     if (i >= this.rows || i < 0) {
/* 1090 */       throw new IndexOutOfBoundsException("Illegal row number, must be 0.." + (this.rows - 1));
/*      */     }
/*      */     
/* 1093 */     Matrix result = blankOfShape(this.rows - 1, this.columns);
/*      */     int ii;
/* 1095 */     for (ii = 0; ii < i; ii++) {
/* 1096 */       result.setRow(ii, getRow(ii));
/*      */     }
/*      */     
/* 1099 */     for (ii = i + 1; ii < this.rows; ii++) {
/* 1100 */       result.setRow(ii - 1, getRow(ii));
/*      */     }
/*      */     
/* 1103 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix removeColumn(int j) {
/* 1112 */     if (j >= this.columns || j < 0) {
/* 1113 */       throw new IndexOutOfBoundsException("Illegal row number, must be 0.." + (this.columns - 1));
/*      */     }
/*      */     
/* 1116 */     Matrix result = blankOfShape(this.rows, this.columns - 1);
/*      */     int jj;
/* 1118 */     for (jj = 0; jj < j; jj++) {
/* 1119 */       result.setColumn(jj, getColumn(jj));
/*      */     }
/*      */     
/* 1122 */     for (jj = j + 1; jj < this.columns; jj++) {
/* 1123 */       result.setColumn(jj - 1, getColumn(jj));
/*      */     }
/*      */     
/* 1126 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix removeFirstRow() {
/* 1134 */     return removeRow(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix removeFirstColumn() {
/* 1142 */     return removeColumn(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix removeLastRow() {
/* 1150 */     return removeRow(this.rows - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix removeLastColumn() {
/* 1158 */     return removeColumn(this.columns - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix blank() {
/* 1167 */     return blankOfShape(this.rows, this.columns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix blankOfRows(int rows) {
/* 1178 */     return blankOfShape(rows, this.columns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix blankOfColumns(int columns) {
/* 1189 */     return blankOfShape(this.rows, columns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix copy() {
/* 1198 */     return copyOfShape(this.rows, this.columns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix copyOfRows(int rows) {
/* 1209 */     return copyOfShape(rows, this.columns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix copyOfColumns(int columns) {
/* 1220 */     return copyOfShape(this.rows, columns);
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
/*      */   public Matrix shuffle() {
/* 1234 */     Matrix result = copy();
/*      */ 
/*      */     
/* 1237 */     Random random = new Random();
/*      */     
/* 1239 */     for (int i = 0; i < this.rows; i++) {
/* 1240 */       for (int j = 0; j < this.columns; j++) {
/* 1241 */         int ii = random.nextInt(this.rows - i) + i;
/* 1242 */         int jj = random.nextInt(this.columns - j) + j;
/*      */         
/* 1244 */         double a = result.get(ii, jj);
/* 1245 */         result.set(ii, jj, result.get(i, j));
/* 1246 */         result.set(i, j, a);
/*      */       } 
/*      */     } 
/*      */     
/* 1250 */     return result;
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
/*      */   public Matrix slice(int fromRow, int fromColumn, int untilRow, int untilColumn) {
/* 1265 */     if (untilRow - fromRow < 0 || untilColumn - fromColumn < 0) {
/* 1266 */       fail("Wrong slice range: [" + fromRow + ".." + untilRow + "][" + fromColumn + ".." + untilColumn + "].");
/*      */     }
/*      */     
/* 1269 */     Matrix result = blankOfShape(untilRow - fromRow, untilColumn - fromColumn);
/*      */     
/* 1271 */     for (int i = fromRow; i < untilRow; i++) {
/* 1272 */       for (int j = fromColumn; j < untilColumn; j++) {
/* 1273 */         result.set(i - fromRow, j - fromColumn, get(i, j));
/*      */       }
/*      */     } 
/*      */     
/* 1277 */     return result;
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
/*      */   public Matrix sliceTopLeft(int untilRow, int untilColumn) {
/* 1291 */     return slice(0, 0, untilRow, untilColumn);
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
/*      */   public Matrix sliceBottomRight(int fromRow, int fromColumn) {
/* 1305 */     return slice(fromRow, fromColumn, this.rows, this.columns);
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
/*      */   public Matrix select(int[] rowIndices, int[] columnIndices) {
/* 1324 */     int m = rowIndices.length;
/* 1325 */     int n = columnIndices.length;
/*      */     
/* 1327 */     if (m == 0 || n == 0) {
/* 1328 */       fail("No rows or columns selected.");
/*      */     }
/*      */     
/* 1331 */     Matrix result = blankOfShape(m, n);
/*      */     
/* 1333 */     for (int i = 0; i < m; i++) {
/* 1334 */       for (int j = 0; j < n; j++) {
/* 1335 */         result.set(i, j, get(rowIndices[i], columnIndices[j]));
/*      */       }
/*      */     } 
/*      */     
/* 1339 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void each(MatrixProcedure procedure) {
/* 1348 */     MatrixIterator it = iterator();
/*      */     
/* 1350 */     while (it.hasNext()) {
/* 1351 */       double x = ((Double)it.next()).doubleValue();
/* 1352 */       int i = it.rowIndex();
/* 1353 */       int j = it.columnIndex();
/* 1354 */       procedure.apply(i, j, x);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void eachInRow(int i, VectorProcedure procedure) {
/* 1365 */     VectorIterator it = iteratorOfRow(i);
/*      */     
/* 1367 */     while (it.hasNext()) {
/* 1368 */       double x = ((Double)it.next()).doubleValue();
/* 1369 */       int j = it.index();
/* 1370 */       procedure.apply(j, x);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void eachInColumn(int j, VectorProcedure procedure) {
/* 1381 */     VectorIterator it = iteratorOfColumn(j);
/*      */     
/* 1383 */     while (it.hasNext()) {
/* 1384 */       double x = ((Double)it.next()).doubleValue();
/* 1385 */       int i = it.index();
/* 1386 */       procedure.apply(i, x);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double max() {
/* 1396 */     return fold(Matrices.mkMaxAccumulator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double min() {
/* 1405 */     return fold(Matrices.mkMinAccumulator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double maxInRow(int i) {
/* 1416 */     return foldRow(i, Vectors.mkMaxAccumulator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double minInRow(int i) {
/* 1427 */     return foldRow(i, Vectors.mkMinAccumulator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double maxInColumn(int j) {
/* 1438 */     return foldColumn(j, Vectors.mkMaxAccumulator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double minInColumn(int j) {
/* 1449 */     return foldColumn(j, Vectors.mkMinAccumulator());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix transform(MatrixFunction function) {
/* 1460 */     Matrix result = blank();
/* 1461 */     MatrixIterator it = iterator();
/*      */     
/* 1463 */     while (it.hasNext()) {
/* 1464 */       double x = ((Double)it.next()).doubleValue();
/* 1465 */       int i = it.rowIndex();
/* 1466 */       int j = it.columnIndex();
/* 1467 */       result.set(i, j, function.evaluate(i, j, x));
/*      */     } 
/*      */     
/* 1470 */     return result;
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
/*      */   public Matrix transformRow(int i, VectorFunction function) {
/* 1483 */     Matrix result = copy();
/* 1484 */     VectorIterator it = result.iteratorOfRow(i);
/*      */     
/* 1486 */     while (it.hasNext()) {
/* 1487 */       double x = ((Double)it.next()).doubleValue();
/* 1488 */       int j = it.index();
/* 1489 */       it.set(function.evaluate(j, x));
/*      */     } 
/*      */     
/* 1492 */     return result;
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
/*      */   public Matrix transformColumn(int j, VectorFunction function) {
/* 1505 */     Matrix result = copy();
/* 1506 */     VectorIterator it = result.iteratorOfColumn(j);
/*      */     
/* 1508 */     while (it.hasNext()) {
/* 1509 */       double x = ((Double)it.next()).doubleValue();
/* 1510 */       int i = it.index();
/* 1511 */       it.set(function.evaluate(i, x));
/*      */     } 
/*      */     
/* 1514 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(MatrixFunction function) {
/* 1523 */     MatrixIterator it = iterator();
/*      */     
/* 1525 */     while (it.hasNext()) {
/* 1526 */       double x = ((Double)it.next()).doubleValue();
/* 1527 */       int i = it.rowIndex();
/* 1528 */       int j = it.columnIndex();
/* 1529 */       it.set(function.evaluate(i, j, x));
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
/*      */   public void updateAt(int i, int j, MatrixFunction function) {
/* 1541 */     set(i, j, function.evaluate(i, j, get(i, j)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRow(int i, VectorFunction function) {
/* 1551 */     VectorIterator it = iteratorOfRow(i);
/*      */     
/* 1553 */     while (it.hasNext()) {
/* 1554 */       double x = ((Double)it.next()).doubleValue();
/* 1555 */       int j = it.index();
/* 1556 */       it.set(function.evaluate(j, x));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateColumn(int j, VectorFunction function) {
/* 1567 */     VectorIterator it = iteratorOfColumn(j);
/*      */     
/* 1569 */     while (it.hasNext()) {
/* 1570 */       double x = ((Double)it.next()).doubleValue();
/* 1571 */       int i = it.index();
/* 1572 */       it.set(function.evaluate(i, x));
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
/*      */   public double fold(MatrixAccumulator accumulator) {
/* 1584 */     each(Matrices.asAccumulatorProcedure(accumulator));
/* 1585 */     return accumulator.accumulate();
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
/*      */   public double foldRow(int i, VectorAccumulator accumulator) {
/* 1597 */     eachInRow(i, Vectors.asAccumulatorProcedure(accumulator));
/* 1598 */     return accumulator.accumulate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] foldRows(VectorAccumulator accumulator) {
/* 1609 */     double[] result = new double[this.rows];
/*      */     
/* 1611 */     for (int i = 0; i < this.rows; i++) {
/* 1612 */       result[i] = foldRow(i, accumulator);
/*      */     }
/*      */     
/* 1615 */     return result;
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
/*      */   public double foldColumn(int j, VectorAccumulator accumulator) {
/* 1627 */     eachInColumn(j, Vectors.asAccumulatorProcedure(accumulator));
/* 1628 */     return accumulator.accumulate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] foldColumns(VectorAccumulator accumulator) {
/* 1639 */     double[] result = new double[this.columns];
/*      */     
/* 1641 */     for (int i = 0; i < this.columns; i++) {
/* 1642 */       result[i] = foldColumn(i, accumulator);
/*      */     }
/*      */     
/* 1645 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean is(MatrixPredicate predicate) {
/* 1656 */     MatrixIterator it = iterator();
/* 1657 */     boolean result = predicate.test(this.rows, this.columns);
/*      */     
/* 1659 */     while (it.hasNext() && result) {
/* 1660 */       double x = ((Double)it.next()).doubleValue();
/* 1661 */       int i = it.rowIndex();
/* 1662 */       int j = it.columnIndex();
/* 1663 */       result = predicate.test(i, j, x);
/*      */     } 
/*      */     
/* 1666 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean is(AdvancedMatrixPredicate predicate) {
/* 1677 */     return predicate.test(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean non(MatrixPredicate predicate) {
/* 1688 */     return !is(predicate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean non(AdvancedMatrixPredicate predicate) {
/* 1699 */     return !is(predicate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector toRowVector() {
/* 1708 */     return getRow(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector toColumnVector() {
/* 1717 */     return getColumn(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LinearSystemSolver withSolver(LinearAlgebra.SolverFactory factory) {
/* 1728 */     return factory.create(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixInverter withInverter(LinearAlgebra.InverterFactory factory) {
/* 1739 */     return factory.create(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixDecompositor withDecompositor(LinearAlgebra.DecompositorFactory factory) {
/* 1750 */     return factory.create(this);
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
/*      */   public boolean equals(Matrix matrix, double precision) {
/* 1762 */     if (this.rows != matrix.rows() || this.columns != matrix.columns()) {
/* 1763 */       return false;
/*      */     }
/*      */     
/* 1766 */     boolean result = true;
/*      */     
/* 1768 */     for (int i = 0; result && i < this.rows; i++) {
/* 1769 */       for (int j = 0; result && j < this.columns; j++) {
/* 1770 */         double a = get(i, j);
/* 1771 */         double b = matrix.get(i, j);
/* 1772 */         double diff = Math.abs(a - b);
/*      */         
/* 1774 */         result = (a == b || diff < precision || diff / Math.max(Math.abs(a), Math.abs(b)) < precision);
/*      */       } 
/*      */     } 
/*      */     
/* 1778 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String mkString(NumberFormat formatter) {
/* 1789 */     return mkString(formatter, "\n", " ");
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
/*      */   public String mkString(String rowsDelimiter, String columnsDelimiter) {
/* 1801 */     return mkString(DEFAULT_FORMATTER, rowsDelimiter, columnsDelimiter);
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
/*      */   public String mkString(NumberFormat formatter, String rowsDelimiter, String columnsDelimiter) {
/* 1815 */     int[] formats = new int[this.columns];
/*      */     
/* 1817 */     for (int i = 0; i < this.rows; i++) {
/* 1818 */       for (int k = 0; k < this.columns; k++) {
/* 1819 */         double value = get(i, k);
/* 1820 */         String output = formatter.format(value);
/* 1821 */         int size = output.length();
/* 1822 */         formats[k] = (size > formats[k]) ? size : formats[k];
/*      */       } 
/*      */     } 
/*      */     
/* 1826 */     StringBuilder sb = new StringBuilder();
/*      */     
/* 1828 */     for (int j = 0; j < this.rows; j++) {
/* 1829 */       for (int k = 0; k < this.columns; k++) {
/* 1830 */         String output = formatter.format(get(j, k));
/* 1831 */         int outputLength = output.length();
/*      */         
/* 1833 */         if (outputLength < formats[k]) {
/* 1834 */           int align = formats[k] - outputLength;
/* 1835 */           if (align > INDENTS.length - 1) {
/* 1836 */             indent(sb, align);
/*      */           } else {
/* 1838 */             sb.append(INDENTS[align - 1]);
/*      */           } 
/*      */         } 
/*      */         
/* 1842 */         sb.append(output).append((k < this.columns - 1) ? columnsDelimiter : "");
/*      */       } 
/*      */       
/* 1845 */       sb.append(rowsDelimiter);
/*      */     } 
/*      */     
/* 1848 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1853 */     return mkString(DEFAULT_FORMATTER, "\n", " ");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixIterator iterator() {
/* 1863 */     return (MatrixIterator)rowMajorIterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RowMajorMatrixIterator rowMajorIterator() {
/* 1872 */     return new RowMajorMatrixIterator(this.rows, this.columns) {
/* 1873 */         private long limit = this.rows * this.columns;
/* 1874 */         private int i = -1;
/*      */ 
/*      */         
/*      */         public int rowIndex() {
/* 1878 */           return this.i / this.columns;
/*      */         }
/*      */ 
/*      */         
/*      */         public int columnIndex() {
/* 1883 */           return this.i - rowIndex() * this.columns;
/*      */         }
/*      */ 
/*      */         
/*      */         public double get() {
/* 1888 */           return Matrix.this.get(rowIndex(), columnIndex());
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(double value) {
/* 1893 */           Matrix.this.set(rowIndex(), columnIndex(), value);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/* 1898 */           return ((this.i + 1) < this.limit);
/*      */         }
/*      */ 
/*      */         
/*      */         public Double next() {
/* 1903 */           this.i++;
/* 1904 */           return Double.valueOf(get());
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColumnMajorMatrixIterator columnMajorIterator() {
/* 1915 */     return new ColumnMajorMatrixIterator(this.rows, this.columns) {
/* 1916 */         private long limit = this.rows * this.columns;
/* 1917 */         private int i = -1;
/*      */ 
/*      */         
/*      */         public int rowIndex() {
/* 1921 */           return this.i - columnIndex() * this.rows;
/*      */         }
/*      */ 
/*      */         
/*      */         public int columnIndex() {
/* 1926 */           return this.i / this.rows;
/*      */         }
/*      */ 
/*      */         
/*      */         public double get() {
/* 1931 */           return Matrix.this.get(rowIndex(), columnIndex());
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(double value) {
/* 1936 */           Matrix.this.set(rowIndex(), columnIndex(), value);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/* 1941 */           return ((this.i + 1) < this.limit);
/*      */         }
/*      */ 
/*      */         
/*      */         public Double next() {
/* 1946 */           this.i++;
/* 1947 */           return Double.valueOf(get());
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public VectorIterator iteratorOfRow(int i) {
/* 1958 */     final int ii = i;
/* 1959 */     return new VectorIterator(this.columns) {
/* 1960 */         private int j = -1;
/*      */ 
/*      */         
/*      */         public int index() {
/* 1964 */           return this.j;
/*      */         }
/*      */ 
/*      */         
/*      */         public double get() {
/* 1969 */           return Matrix.this.get(ii, this.j);
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(double value) {
/* 1974 */           Matrix.this.set(ii, this.j, value);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/* 1979 */           return (this.j + 1 < Matrix.this.columns);
/*      */         }
/*      */ 
/*      */         
/*      */         public Double next() {
/* 1984 */           this.j++;
/* 1985 */           return Double.valueOf(get());
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public VectorIterator iteratorOfColumn(int j) {
/* 1996 */     final int jj = j;
/* 1997 */     return new VectorIterator(this.rows) {
/* 1998 */         private int i = -1;
/*      */         
/*      */         public int index() {
/* 2001 */           return this.i;
/*      */         }
/*      */ 
/*      */         
/*      */         public double get() {
/* 2006 */           return Matrix.this.get(this.i, jj);
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(double value) {
/* 2011 */           Matrix.this.set(this.i, jj, value);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/* 2016 */           return (this.i + 1 < Matrix.this.rows);
/*      */         }
/*      */ 
/*      */         
/*      */         public Double next() {
/* 2021 */           this.i++;
/* 2022 */           return Double.valueOf(get());
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 2029 */     MatrixIterator it = iterator();
/* 2030 */     int result = 17;
/*      */     
/* 2032 */     while (it.hasNext()) {
/* 2033 */       long value = ((Double)it.next()).longValue();
/* 2034 */       result = 37 * result + (int)(value ^ value >>> 32L);
/*      */     } 
/*      */     
/* 2037 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 2042 */     if (this == o) {
/* 2043 */       return true;
/*      */     }
/* 2045 */     if (o == null) {
/* 2046 */       return false;
/*      */     }
/*      */     
/* 2049 */     if (!(o instanceof Matrix)) {
/* 2050 */       return false;
/*      */     }
/*      */     
/* 2053 */     Matrix matrix = (Matrix)o;
/*      */     
/* 2055 */     return equals(matrix, Matrices.EPS);
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
/*      */   public <T extends Matrix> T to(MatrixFactory<T> factory) {
/* 2067 */     Matrix matrix = factory.apply(this.rows, this.columns);
/* 2068 */     apply(LinearAlgebra.IN_PLACE_COPY_MATRIX_TO_MATRIX, matrix);
/* 2069 */     return (T)matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SparseMatrix toSparseMatrix() {
/* 2078 */     return (SparseMatrix)to(Matrices.SPARSE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DenseMatrix toDenseMatrix() {
/* 2087 */     return (DenseMatrix)to(Matrices.DENSE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RowMajorSparseMatrix toRowMajorSparseMatrix() {
/* 2096 */     return (RowMajorSparseMatrix)to(Matrices.SPARSE_ROW_MAJOR);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColumnMajorSparseMatrix toColumnMajorSparseMatrix() {
/* 2105 */     return (ColumnMajorSparseMatrix)to(Matrices.SPARSE_COLUMN_MAJOR);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toCSV() {
/* 2114 */     return toCSV(DEFAULT_FORMATTER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toMatrixMarket() {
/* 2123 */     return toMatrixMarket(DEFAULT_FORMATTER);
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
/*      */   public String toCSV(NumberFormat formatter) {
/* 2135 */     return mkString(formatter, "\n", ", ");
/*      */   }
/*      */   
/*      */   protected void ensureDimensionsAreCorrect(int rows, int columns) {
/* 2139 */     if (rows < 0 || columns < 0) {
/* 2140 */       fail("Wrong matrix dimensions: " + rows + "x" + columns);
/*      */     }
/* 2142 */     if (rows == Integer.MAX_VALUE || columns == Integer.MAX_VALUE) {
/* 2143 */       fail("Wrong matrix dimensions: use 'Integer.MAX_VALUE - 1' instead.");
/*      */     }
/*      */   }
/*      */   
/*      */   protected void ensureIndexesAreInBounds(int i, int j) {
/* 2148 */     if (i < 0 || i >= this.rows) {
/* 2149 */       throw new IndexOutOfBoundsException("Row '" + i + "' is invalid.");
/*      */     }
/*      */     
/* 2152 */     if (j < 0 || j >= this.columns) {
/* 2153 */       throw new IndexOutOfBoundsException("Column '" + j + "' is invalid.");
/*      */     }
/*      */   }
/*      */   
/*      */   protected void fail(String message) {
/* 2158 */     throw new IllegalArgumentException(message);
/*      */   }
/*      */   
/*      */   private void indent(StringBuilder sb, int howMany) {
/* 2162 */     while (howMany > 0) {
/* 2163 */       sb.append(" ");
/* 2164 */       howMany--;
/*      */     } 
/*      */   }
/*      */   
/*      */   public abstract double get(int paramInt1, int paramInt2);
/*      */   
/*      */   public abstract void set(int paramInt1, int paramInt2, double paramDouble);
/*      */   
/*      */   public abstract Vector getRow(int paramInt);
/*      */   
/*      */   public abstract Vector getColumn(int paramInt);
/*      */   
/*      */   public abstract Matrix blankOfShape(int paramInt1, int paramInt2);
/*      */   
/*      */   public abstract Matrix copyOfShape(int paramInt1, int paramInt2);
/*      */   
/*      */   public abstract <T> T apply(MatrixOperation<T> paramMatrixOperation);
/*      */   
/*      */   public abstract <T> T apply(MatrixMatrixOperation<T> paramMatrixMatrixOperation, Matrix paramMatrix);
/*      */   
/*      */   public abstract <T> T apply(MatrixVectorOperation<T> paramMatrixVectorOperation, Vector paramVector);
/*      */   
/*      */   public abstract byte[] toBinary();
/*      */   
/*      */   public abstract String toMatrixMarket(NumberFormat paramNumberFormat);
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/Matrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */