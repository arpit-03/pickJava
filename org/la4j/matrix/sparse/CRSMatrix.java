/*      */ package org.la4j.matrix.sparse;
/*      */ 
/*      */ import java.nio.ByteBuffer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.Random;
/*      */ import org.la4j.Matrices;
/*      */ import org.la4j.Matrix;
/*      */ import org.la4j.Vector;
/*      */ import org.la4j.iterator.RowMajorMatrixIterator;
/*      */ import org.la4j.iterator.VectorIterator;
/*      */ import org.la4j.matrix.MatrixFactory;
/*      */ import org.la4j.matrix.RowMajorSparseMatrix;
/*      */ import org.la4j.matrix.functor.MatrixFunction;
/*      */ import org.la4j.matrix.functor.MatrixProcedure;
/*      */ import org.la4j.vector.functor.VectorProcedure;
/*      */ import org.la4j.vector.sparse.CompressedVector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CRSMatrix
/*      */   extends RowMajorSparseMatrix
/*      */ {
/*      */   private static final byte MATRIX_TAG = 32;
/*      */   private static final int MINIMUM_SIZE = 32;
/*      */   private double[] values;
/*      */   private int[] columnIndices;
/*      */   private int[] rowPointers;
/*      */   
/*      */   public static CRSMatrix zero(int rows, int columns) {
/*   58 */     return new CRSMatrix(rows, columns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CRSMatrix zero(int rows, int columns, int capacity) {
/*   66 */     return new CRSMatrix(rows, columns, capacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CRSMatrix diagonal(int size, double diagonal) {
/*   74 */     double[] values = new double[size];
/*   75 */     int[] columnIndices = new int[size];
/*   76 */     int[] rowPointers = new int[size + 1];
/*      */     
/*   78 */     for (int i = 0; i < size; i++) {
/*   79 */       columnIndices[i] = i;
/*   80 */       rowPointers[i] = i;
/*   81 */       values[i] = diagonal;
/*      */     } 
/*      */     
/*   84 */     rowPointers[size] = size;
/*      */     
/*   86 */     return new CRSMatrix(size, size, size, values, columnIndices, rowPointers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CRSMatrix identity(int size) {
/*   93 */     return diagonal(size, 1.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CRSMatrix random(int rows, int columns, double density, Random random) {
/*  101 */     if (density < 0.0D || density > 1.0D) {
/*  102 */       throw new IllegalArgumentException("The density value should be between 0 and 1.0");
/*      */     }
/*      */     
/*  105 */     int cardinality = Math.max((int)((rows * columns) * density), rows);
/*      */     
/*  107 */     double[] values = new double[cardinality];
/*  108 */     int[] columnIndices = new int[cardinality];
/*  109 */     int[] rowPointers = new int[rows + 1];
/*      */     
/*  111 */     int kk = cardinality / rows;
/*  112 */     int[] indices = new int[kk];
/*      */     
/*  114 */     int k = 0;
/*  115 */     for (int i = 0; i < rows; i++) {
/*      */       
/*  117 */       rowPointers[i] = k;
/*      */       
/*  119 */       for (int ii = 0; ii < kk; ii++) {
/*  120 */         indices[ii] = random.nextInt(columns);
/*      */       }
/*      */       
/*  123 */       Arrays.sort(indices);
/*      */       
/*  125 */       int previous = -1;
/*  126 */       for (int j = 0; j < kk; j++) {
/*      */         
/*  128 */         if (indices[j] != previous) {
/*      */ 
/*      */ 
/*      */           
/*  132 */           values[k] = random.nextDouble();
/*  133 */           columnIndices[k++] = indices[j];
/*  134 */           previous = indices[j];
/*      */         } 
/*      */       } 
/*      */     } 
/*  138 */     rowPointers[rows] = cardinality;
/*      */     
/*  140 */     return new CRSMatrix(rows, columns, cardinality, values, columnIndices, rowPointers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CRSMatrix randomSymmetric(int size, double density, Random random) {
/*  148 */     int cardinality = (int)((size * size) * density);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  156 */     CRSMatrix matrix = new CRSMatrix(size, size, cardinality);
/*      */     
/*  158 */     for (int k = 0; k < cardinality / 2; k++) {
/*  159 */       int i = random.nextInt(size);
/*  160 */       int j = random.nextInt(size);
/*  161 */       double value = random.nextDouble();
/*      */       
/*  163 */       matrix.set(i, j, value);
/*  164 */       matrix.set(j, i, value);
/*      */     } 
/*      */     
/*  167 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CRSMatrix from1DArray(int rows, int columns, double[] array) {
/*  175 */     CRSMatrix result = zero(rows, columns);
/*      */     
/*  177 */     for (int i = 0; i < rows; i++) {
/*  178 */       for (int j = 0; j < columns; j++) {
/*  179 */         int k = i * columns + j;
/*  180 */         if (array[k] != 0.0D) {
/*  181 */           result.set(i, j, array[k]);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  186 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CRSMatrix from2DArray(double[][] array) {
/*  194 */     int rows = array.length;
/*  195 */     int columns = (array[0]).length;
/*  196 */     CRSMatrix result = zero(rows, columns);
/*      */     
/*  198 */     for (int i = 0; i < rows; i++) {
/*  199 */       for (int j = 0; j < columns; j++) {
/*  200 */         if (array[i][j] != 0.0D) {
/*  201 */           result.set(i, j, array[i][j]);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  206 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CRSMatrix block(Matrix a, Matrix b, Matrix c, Matrix d) {
/*  214 */     if (a.rows() != b.rows() || a.columns() != c.columns() || c.rows() != d.rows() || b.columns() != d.columns())
/*      */     {
/*  216 */       throw new IllegalArgumentException("Sides of blocks are incompatible!");
/*      */     }
/*      */     
/*  219 */     int rows = a.rows() + c.rows(), columns = a.columns() + b.columns();
/*  220 */     ArrayList<Double> values = new ArrayList<>();
/*  221 */     ArrayList<Integer> columnIndices = new ArrayList<>();
/*  222 */     int[] rowPointers = new int[rows + 1];
/*      */     
/*  224 */     int k = 0;
/*  225 */     rowPointers[0] = 0;
/*  226 */     double current = 0.0D;
/*  227 */     for (int i = 0; i < rows; i++) {
/*  228 */       for (int m = 0; m < columns; m++) {
/*  229 */         if (i < a.rows() && m < a.columns()) {
/*  230 */           current = a.get(i, m);
/*      */         }
/*  232 */         if (i < a.rows() && m > a.columns()) {
/*  233 */           current = b.get(i, m);
/*      */         }
/*  235 */         if (i > a.rows() && m < a.columns()) {
/*  236 */           current = c.get(i, m);
/*      */         }
/*  238 */         if (i > a.rows() && m > a.columns()) {
/*  239 */           current = d.get(i, m);
/*      */         }
/*  241 */         if (Math.abs(current) > Matrices.EPS) {
/*  242 */           values.add(Double.valueOf(current));
/*  243 */           columnIndices.add(Integer.valueOf(m));
/*  244 */           k++;
/*      */         } 
/*      */       } 
/*  247 */       rowPointers[i + 1] = k;
/*      */     } 
/*  249 */     double[] valuesArray = new double[values.size()];
/*  250 */     int[] colIndArray = new int[columnIndices.size()];
/*  251 */     for (int j = 0; j < values.size(); j++) {
/*  252 */       valuesArray[j] = ((Double)values.get(j)).doubleValue();
/*  253 */       colIndArray[j] = ((Integer)columnIndices.get(j)).intValue();
/*      */     } 
/*      */     
/*  256 */     return new CRSMatrix(rows, columns, k, valuesArray, colIndArray, rowPointers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CRSMatrix fromBinary(byte[] array) {
/*  267 */     ByteBuffer buffer = ByteBuffer.wrap(array);
/*      */     
/*  269 */     if (buffer.get() != 32) {
/*  270 */       throw new IllegalArgumentException("Can not decode CRSMatrix from the given byte array.");
/*      */     }
/*      */     
/*  273 */     int rows = buffer.getInt();
/*  274 */     int columns = buffer.getInt();
/*  275 */     int cardinality = buffer.getInt();
/*      */     
/*  277 */     int[] columnIndices = new int[cardinality];
/*  278 */     double[] values = new double[cardinality];
/*  279 */     int[] rowPointers = new int[rows + 1];
/*      */     int i;
/*  281 */     for (i = 0; i < cardinality; i++) {
/*  282 */       columnIndices[i] = buffer.getInt();
/*  283 */       values[i] = buffer.getDouble();
/*      */     } 
/*      */     
/*  286 */     for (i = 0; i < rows + 1; i++) {
/*  287 */       rowPointers[i] = buffer.getInt();
/*      */     }
/*      */     
/*  290 */     return new CRSMatrix(rows, columns, cardinality, values, columnIndices, rowPointers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CRSMatrix fromCSV(String csv) {
/*  301 */     return (CRSMatrix)Matrix.fromCSV(csv).to(Matrices.CRS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CRSMatrix fromMatrixMarket(String mm) {
/*  312 */     return (CRSMatrix)Matrix.fromMatrixMarket(mm).to(Matrices.CRS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CRSMatrix() {
/*  322 */     this(0, 0);
/*      */   }
/*      */   
/*      */   public CRSMatrix(int rows, int columns) {
/*  326 */     this(rows, columns, 0);
/*      */   }
/*      */   
/*      */   public CRSMatrix(int rows, int columns, int capacity) {
/*  330 */     super(rows, columns);
/*  331 */     ensureCardinalityIsCorrect(rows, columns, capacity);
/*      */     
/*  333 */     int alignedSize = align(capacity);
/*  334 */     this.values = new double[alignedSize];
/*  335 */     this.columnIndices = new int[alignedSize];
/*  336 */     this.rowPointers = new int[rows + 1];
/*      */   }
/*      */   
/*      */   public CRSMatrix(int rows, int columns, int cardinality, double[] values, int[] columnIndices, int[] rowPointers) {
/*  340 */     super(rows, columns, cardinality);
/*  341 */     ensureCardinalityIsCorrect(rows, columns, cardinality);
/*      */     
/*  343 */     this.values = values;
/*  344 */     this.columnIndices = columnIndices;
/*  345 */     this.rowPointers = rowPointers;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getOrElse(int i, int j, double defaultValue) {
/*  350 */     ensureIndexesAreInBounds(i, j);
/*  351 */     int k = searchForColumnIndex(j, this.rowPointers[i], this.rowPointers[i + 1]);
/*      */     
/*  353 */     if (k < this.rowPointers[i + 1] && this.columnIndices[k] == j) {
/*  354 */       return this.values[k];
/*      */     }
/*      */     
/*  357 */     return defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void set(int i, int j, double value) {
/*  362 */     ensureIndexesAreInBounds(i, j);
/*  363 */     int k = searchForColumnIndex(j, this.rowPointers[i], this.rowPointers[i + 1]);
/*      */     
/*  365 */     if (k < this.rowPointers[i + 1] && this.columnIndices[k] == j) {
/*      */       
/*  367 */       if (value == 0.0D) {
/*  368 */         remove(k, i);
/*      */       } else {
/*  370 */         this.values[k] = value;
/*      */       } 
/*      */     } else {
/*  373 */       insert(k, i, j, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAll(double value) {
/*  379 */     if (value == 0.0D) {
/*  380 */       this.cardinality = 0;
/*      */     } else {
/*  382 */       int size = (int)capacity();
/*      */       
/*  384 */       if (this.values.length < size) {
/*  385 */         this.values = new double[size];
/*  386 */         this.columnIndices = new int[size];
/*  387 */         this.rowPointers = new int[this.rows + 1];
/*      */       } 
/*      */       
/*  390 */       for (int i = 0; i < this.rows; i++) {
/*  391 */         for (int j = 0; j < this.columns; j++) {
/*  392 */           this.values[i * this.columns + j] = value;
/*  393 */           this.columnIndices[i * this.columns + j] = j;
/*      */         } 
/*  395 */         this.rowPointers[i] = this.columns * i;
/*      */       } 
/*      */       
/*  398 */       this.rowPointers[this.rows] = size;
/*  399 */       this.cardinality = size;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Vector getRow(int i) {
/*  405 */     int rowCardinality = this.rowPointers[i + 1] - this.rowPointers[i];
/*  406 */     double[] rowValues = new double[rowCardinality];
/*  407 */     int[] rowIndices = new int[rowCardinality];
/*      */     
/*  409 */     System.arraycopy(this.values, this.rowPointers[i], rowValues, 0, rowCardinality);
/*  410 */     System.arraycopy(this.columnIndices, this.rowPointers[i], rowIndices, 0, rowCardinality);
/*      */ 
/*      */     
/*  413 */     return (Vector)new CompressedVector(this.columns, rowCardinality, rowValues, rowIndices);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getColumn(int j) {
/*  419 */     CompressedVector compressedVector = CompressedVector.zero(this.rows);
/*  420 */     int i = 0;
/*      */     
/*  422 */     while (this.rowPointers[i] < this.cardinality) {
/*  423 */       int k = searchForColumnIndex(j, this.rowPointers[i], this.rowPointers[i + 1]);
/*  424 */       if (k < this.rowPointers[i + 1] && this.columnIndices[k] == j) {
/*  425 */         compressedVector.set(i, this.values[k]);
/*      */       }
/*      */       
/*  428 */       i++;
/*      */     } 
/*      */     
/*  431 */     return (Vector)compressedVector;
/*      */   }
/*      */ 
/*      */   
/*      */   public Matrix copyOfShape(int rows, int columns) {
/*  436 */     ensureDimensionsAreCorrect(rows, columns);
/*      */     
/*  438 */     if (rows >= this.rows && columns >= this.columns) {
/*  439 */       double[] arrayOfDouble = new double[align(this.cardinality)];
/*  440 */       int[] arrayOfInt1 = new int[align(this.cardinality)];
/*  441 */       int[] arrayOfInt2 = new int[rows + 1];
/*      */       
/*  443 */       System.arraycopy(this.values, 0, arrayOfDouble, 0, this.cardinality);
/*  444 */       System.arraycopy(this.columnIndices, 0, arrayOfInt1, 0, this.cardinality);
/*  445 */       System.arraycopy(this.rowPointers, 0, arrayOfInt2, 0, this.rows + 1);
/*      */       
/*  447 */       for (int j = this.rows; j < rows + 1; j++) {
/*  448 */         arrayOfInt2[j] = this.cardinality;
/*      */       }
/*      */       
/*  451 */       return (Matrix)new CRSMatrix(rows, columns, this.cardinality, arrayOfDouble, arrayOfInt1, arrayOfInt2);
/*      */     } 
/*      */     
/*  454 */     double[] $values = new double[align(this.cardinality)];
/*  455 */     int[] $columnIndices = new int[align(this.cardinality)];
/*  456 */     int[] $rowPointers = new int[rows + 1];
/*      */     
/*  458 */     int $cardinality = 0;
/*      */     
/*  460 */     int k = 0, i = 0;
/*  461 */     while (k < this.cardinality && i < rows) {
/*      */       
/*  463 */       $rowPointers[i] = $cardinality;
/*      */       
/*  465 */       int j = this.rowPointers[i];
/*  466 */       for (; j < this.rowPointers[i + 1] && this.columnIndices[j] < columns; j++, k++) {
/*      */         
/*  468 */         $values[$cardinality] = this.values[j];
/*  469 */         $columnIndices[$cardinality] = this.columnIndices[j];
/*  470 */         $cardinality++;
/*      */       } 
/*  472 */       i++;
/*      */     } 
/*      */     
/*  475 */     for (; i < rows + 1; i++) {
/*  476 */       $rowPointers[i] = $cardinality;
/*      */     }
/*      */     
/*  479 */     return (Matrix)new CRSMatrix(rows, columns, $cardinality, $values, $columnIndices, $rowPointers);
/*      */   }
/*      */ 
/*      */   
/*      */   public void eachNonZero(MatrixProcedure procedure) {
/*  484 */     int k = 0, i = 0;
/*  485 */     while (k < this.cardinality) {
/*  486 */       for (int j = this.rowPointers[i]; j < this.rowPointers[i + 1]; j++, k++) {
/*  487 */         procedure.apply(i, this.columnIndices[j], this.values[j]);
/*      */       }
/*  489 */       i++;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void each(MatrixProcedure procedure) {
/*  495 */     int k = 0;
/*  496 */     for (int i = 0; i < this.rows; i++) {
/*  497 */       int valuesSoFar = this.rowPointers[i + 1];
/*  498 */       for (int j = 0; j < this.columns; j++) {
/*  499 */         if (k < valuesSoFar && j == this.columnIndices[k]) {
/*  500 */           procedure.apply(i, j, this.values[k++]);
/*      */         } else {
/*  502 */           procedure.apply(i, j, 0.0D);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void eachInRow(int i, VectorProcedure procedure) {
/*  510 */     int k = this.rowPointers[i];
/*  511 */     int valuesSoFar = this.rowPointers[i + 1];
/*  512 */     for (int j = 0; j < this.columns; j++) {
/*  513 */       if (k < valuesSoFar && j == this.columnIndices[k]) {
/*  514 */         procedure.apply(j, this.values[k++]);
/*      */       } else {
/*  516 */         procedure.apply(j, 0.0D);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void eachNonZeroInRow(int i, VectorProcedure procedure) {
/*  523 */     for (int j = this.rowPointers[i]; j < this.rowPointers[i + 1]; j++) {
/*  524 */       procedure.apply(this.columnIndices[j], this.values[j]);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateAt(int i, int j, MatrixFunction function) {
/*  530 */     int k = searchForColumnIndex(j, this.rowPointers[i], this.rowPointers[i + 1]);
/*  531 */     if (k < this.rowPointers[i + 1] && this.columnIndices[k] == j) {
/*      */       
/*  533 */       double value = function.evaluate(i, j, this.values[k]);
/*      */ 
/*      */       
/*  536 */       if (value == 0.0D) {
/*  537 */         remove(k, i);
/*      */       } else {
/*  539 */         this.values[k] = value;
/*      */       } 
/*      */     } else {
/*  542 */       insert(k, i, j, function.evaluate(i, j, 0.0D));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean nonZeroAt(int i, int j) {
/*  548 */     int k = searchForColumnIndex(j, this.rowPointers[i], this.rowPointers[i + 1]);
/*  549 */     return (k < this.rowPointers[i + 1] && this.columnIndices[k] == j);
/*      */   }
/*      */   
/*      */   private int searchForColumnIndex(int j, int left, int right) {
/*  553 */     if (right - left == 0 || j > this.columnIndices[right - 1]) {
/*  554 */       return right;
/*      */     }
/*      */     
/*  557 */     while (left < right) {
/*  558 */       int p = (left + right) / 2;
/*  559 */       if (this.columnIndices[p] > j) {
/*  560 */         right = p; continue;
/*  561 */       }  if (this.columnIndices[p] < j) {
/*  562 */         left = p + 1; continue;
/*      */       } 
/*  564 */       return p;
/*      */     } 
/*      */ 
/*      */     
/*  568 */     return left;
/*      */   }
/*      */ 
/*      */   
/*      */   private void insert(int k, int i, int j, double value) {
/*  573 */     if (value == 0.0D) {
/*      */       return;
/*      */     }
/*      */     
/*  577 */     if (this.values.length < this.cardinality + 1) {
/*  578 */       growUp();
/*      */     }
/*      */     
/*  581 */     if (this.cardinality - k > 0) {
/*  582 */       System.arraycopy(this.values, k, this.values, k + 1, this.cardinality - k);
/*  583 */       System.arraycopy(this.columnIndices, k, this.columnIndices, k + 1, this.cardinality - k);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  591 */     this.values[k] = value;
/*  592 */     this.columnIndices[k] = j;
/*      */     
/*  594 */     for (int ii = i + 1; ii < this.rows + 1; ii++) {
/*  595 */       this.rowPointers[ii] = this.rowPointers[ii] + 1;
/*      */     }
/*      */     
/*  598 */     this.cardinality++;
/*      */   }
/*      */   
/*      */   private void remove(int k, int i) {
/*  602 */     this.cardinality--;
/*      */     
/*  604 */     if (this.cardinality - k > 0) {
/*  605 */       System.arraycopy(this.values, k + 1, this.values, k, this.cardinality - k);
/*  606 */       System.arraycopy(this.columnIndices, k + 1, this.columnIndices, k, this.cardinality - k);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  614 */     for (int ii = i + 1; ii < this.rows + 1; ii++) {
/*  615 */       this.rowPointers[ii] = this.rowPointers[ii] - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void growUp() {
/*  621 */     if (this.values.length == capacity())
/*      */     {
/*  623 */       throw new IllegalStateException("This matrix can't grow up.");
/*      */     }
/*      */     
/*  626 */     int min = (this.rows != 0 && this.columns > Integer.MAX_VALUE / this.rows) ? Integer.MAX_VALUE : (this.rows * this.columns);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  631 */     int capacity = Math.min(min, this.cardinality * 3 / 2 + 1);
/*      */     
/*  633 */     double[] $values = new double[capacity];
/*  634 */     int[] $columnIndices = new int[capacity];
/*      */     
/*  636 */     System.arraycopy(this.values, 0, $values, 0, this.cardinality);
/*  637 */     System.arraycopy(this.columnIndices, 0, $columnIndices, 0, this.cardinality);
/*      */     
/*  639 */     this.values = $values;
/*  640 */     this.columnIndices = $columnIndices;
/*      */   }
/*      */   
/*      */   private int align(int cardinality) {
/*  644 */     return (cardinality / 32 + 1) * 32;
/*      */   }
/*      */ 
/*      */   
/*      */   public double max() {
/*  649 */     double max = Double.NEGATIVE_INFINITY;
/*      */     
/*  651 */     for (int i = 0; i < this.cardinality; i++) {
/*  652 */       if (this.values[i] > max) {
/*  653 */         max = this.values[i];
/*      */       }
/*      */     } 
/*      */     
/*  657 */     return (max > 0.0D) ? max : 0.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   public double min() {
/*  662 */     double min = Double.POSITIVE_INFINITY;
/*      */     
/*  664 */     for (int i = 0; i < this.cardinality; i++) {
/*  665 */       if (this.values[i] < min) {
/*  666 */         min = this.values[i];
/*      */       }
/*      */     } 
/*      */     
/*  670 */     return (min < 0.0D) ? min : 0.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   public double maxInRow(int i) {
/*  675 */     double max = Double.NEGATIVE_INFINITY;
/*      */     
/*  677 */     for (int k = this.rowPointers[i]; k < this.rowPointers[i + 1]; k++) {
/*  678 */       if (this.values[k] > max) {
/*  679 */         max = this.values[k];
/*      */       }
/*      */     } 
/*      */     
/*  683 */     return (max > 0.0D) ? max : 0.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   public double minInRow(int i) {
/*  688 */     double min = Double.POSITIVE_INFINITY;
/*      */     
/*  690 */     for (int k = this.rowPointers[i]; k < this.rowPointers[i + 1]; k++) {
/*  691 */       if (this.values[k] < min) {
/*  692 */         min = this.values[k];
/*      */       }
/*      */     } 
/*      */     
/*  696 */     return (min < 0.0D) ? min : 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix select(int[] rowIndices, int[] columnIndices) {
/*  704 */     int newRows = rowIndices.length;
/*  705 */     int newCols = columnIndices.length;
/*      */     
/*  707 */     if (newRows == 0 || newCols == 0) {
/*  708 */       fail("No rows or columns selected.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  714 */     int newCardinality = 0;
/*  715 */     for (int i = 0; i < newRows; i++) {
/*  716 */       for (int k = 0; k < newCols; k++) {
/*  717 */         if (get(rowIndices[i], columnIndices[k]) != 0.0D) {
/*  718 */           newCardinality++;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  724 */     double[] newValues = new double[newCardinality];
/*  725 */     int[] newColumnIndices = new int[newCardinality];
/*  726 */     int[] newRowPointers = new int[newRows + 1];
/*      */     
/*  728 */     newRowPointers[0] = 0;
/*  729 */     int endPtr = 0;
/*  730 */     for (int j = 0; j < newRows; j++) {
/*  731 */       newRowPointers[j + 1] = newRowPointers[j];
/*  732 */       for (int k = 0; k < newCols; k++) {
/*  733 */         double val = get(rowIndices[j], columnIndices[k]);
/*  734 */         if (val != 0.0D) {
/*  735 */           newValues[endPtr] = val;
/*  736 */           newColumnIndices[endPtr] = k;
/*  737 */           endPtr++;
/*  738 */           newRowPointers[j + 1] = newRowPointers[j + 1] + 1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  744 */     return (Matrix)new CRSMatrix(newRows, newCols, newCardinality, newValues, newColumnIndices, newRowPointers);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends Matrix> T to(MatrixFactory<T> factory) {
/*  750 */     if (factory.outputClass == CRSMatrix.class) {
/*  751 */       return (T)factory.outputClass.cast(this);
/*      */     }
/*      */     
/*  754 */     return (T)super.to(factory);
/*      */   }
/*      */ 
/*      */   
/*      */   public Matrix blankOfShape(int rows, int columns) {
/*  759 */     return (Matrix)zero(rows, columns);
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterator<Integer> iteratorOfNonZeroRows() {
/*  764 */     return new Iterator<Integer>() {
/*  765 */         private int i = -1;
/*      */ 
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  770 */           while (this.i + 1 < CRSMatrix.this.rows && CRSMatrix.this.rowPointers[this.i + 1] < CRSMatrix.this.cardinality && CRSMatrix.this.rowPointers[this.i + 1] == CRSMatrix.this.rowPointers[this.i + 2])
/*      */           {
/*  772 */             this.i++;
/*      */           }
/*      */           
/*  775 */           return (this.i + 1 < CRSMatrix.this.rows && CRSMatrix.this.rowPointers[this.i + 1] < CRSMatrix.this.cardinality);
/*      */         }
/*      */ 
/*      */         
/*      */         public Integer next() {
/*  780 */           this.i++;
/*  781 */           return Integer.valueOf(this.i);
/*      */         }
/*      */ 
/*      */         
/*      */         public void remove() {
/*  786 */           throw new UnsupportedOperationException("Can not remove from this iterator.");
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public RowMajorMatrixIterator rowMajorIterator() {
/*  793 */     return new RowMajorMatrixIterator(this.rows, this.columns) {
/*  794 */         private long limit = this.rows * this.columns;
/*      */         private boolean currentNonZero = false;
/*  796 */         private int i = -1;
/*  797 */         private int k = 0;
/*      */ 
/*      */         
/*      */         public int rowIndex() {
/*  801 */           return this.i / this.columns;
/*      */         }
/*      */ 
/*      */         
/*      */         public int columnIndex() {
/*  806 */           return this.i - rowIndex() * this.columns;
/*      */         }
/*      */ 
/*      */         
/*      */         public double get() {
/*  811 */           return this.currentNonZero ? CRSMatrix.this.values[this.k] : 0.0D;
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(double value) {
/*  816 */           if (this.currentNonZero) {
/*  817 */             if (value == 0.0D) {
/*  818 */               CRSMatrix.this.remove(this.k, rowIndex());
/*  819 */               this.currentNonZero = false;
/*      */             } else {
/*  821 */               CRSMatrix.this.values[this.k] = value;
/*      */             } 
/*      */           } else {
/*  824 */             CRSMatrix.this.insert(this.k, rowIndex(), columnIndex(), value);
/*  825 */             this.currentNonZero = true;
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  831 */           return ((this.i + 1) < this.limit);
/*      */         }
/*      */ 
/*      */         
/*      */         public Double next() {
/*  836 */           if (this.currentNonZero) {
/*  837 */             this.k++;
/*      */           }
/*      */           
/*  840 */           this.i++;
/*  841 */           this.currentNonZero = (this.k < CRSMatrix.this.rowPointers[rowIndex() + 1] && CRSMatrix.this.columnIndices[this.k] == columnIndex());
/*      */           
/*  843 */           return Double.valueOf(get());
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public RowMajorMatrixIterator nonZeroRowMajorIterator() {
/*  850 */     return new RowMajorMatrixIterator(this.rows, this.columns) {
/*  851 */         private int i = 0;
/*  852 */         private int k = -1;
/*      */         private boolean currentIsRemoved = false;
/*  854 */         private int removedIndex = -1;
/*      */ 
/*      */         
/*      */         public int rowIndex() {
/*  858 */           return this.i;
/*      */         }
/*      */ 
/*      */         
/*      */         public int columnIndex() {
/*  863 */           return this.currentIsRemoved ? this.removedIndex : CRSMatrix.this.columnIndices[this.k];
/*      */         }
/*      */ 
/*      */         
/*      */         public double get() {
/*  868 */           return this.currentIsRemoved ? 0.0D : CRSMatrix.this.values[this.k];
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(double value) {
/*  873 */           if (value == 0.0D && !this.currentIsRemoved) {
/*  874 */             this.currentIsRemoved = true;
/*  875 */             this.removedIndex = CRSMatrix.this.columnIndices[this.k];
/*  876 */             CRSMatrix.this.remove(this.k--, this.i);
/*  877 */           } else if (value != 0.0D && !this.currentIsRemoved) {
/*  878 */             CRSMatrix.this.values[this.k] = value;
/*      */           } else {
/*  880 */             this.currentIsRemoved = false;
/*  881 */             CRSMatrix.this.insert(++this.k, this.i, this.removedIndex, value);
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  887 */           return (this.k + 1 < CRSMatrix.this.cardinality);
/*      */         }
/*      */ 
/*      */         
/*      */         public Double next() {
/*  892 */           this.currentIsRemoved = false;
/*  893 */           this.k++;
/*  894 */           while (CRSMatrix.this.rowPointers[this.i + 1] == this.k) {
/*  895 */             this.i++;
/*      */           }
/*  897 */           return Double.valueOf(get());
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public VectorIterator nonZeroIteratorOfRow(int i) {
/*  904 */     final int ii = i;
/*  905 */     return new VectorIterator(this.columns) {
/*  906 */         private int k = CRSMatrix.this.rowPointers[ii] - 1;
/*      */         private boolean currentIsRemoved = false;
/*  908 */         private int removedIndex = -1;
/*      */ 
/*      */         
/*      */         public int index() {
/*  912 */           return this.currentIsRemoved ? this.removedIndex : CRSMatrix.this.columnIndices[this.k];
/*      */         }
/*      */ 
/*      */         
/*      */         public double get() {
/*  917 */           return this.currentIsRemoved ? 0.0D : CRSMatrix.this.values[this.k];
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(double value) {
/*  922 */           if (value == 0.0D && !this.currentIsRemoved) {
/*  923 */             this.currentIsRemoved = true;
/*  924 */             this.removedIndex = CRSMatrix.this.columnIndices[this.k];
/*  925 */             CRSMatrix.this.remove(this.k--, ii);
/*  926 */           } else if (value != 0.0D && !this.currentIsRemoved) {
/*  927 */             CRSMatrix.this.values[this.k] = value;
/*      */           } else {
/*  929 */             this.currentIsRemoved = false;
/*  930 */             CRSMatrix.this.insert(++this.k, ii, this.removedIndex, value);
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  936 */           return (this.k + 1 < CRSMatrix.this.rowPointers[ii + 1]);
/*      */         }
/*      */ 
/*      */         
/*      */         public Double next() {
/*  941 */           this.currentIsRemoved = false;
/*  942 */           return Double.valueOf(CRSMatrix.this.values[++this.k]);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public VectorIterator iteratorOfRow(int i) {
/*  949 */     final int ii = i;
/*  950 */     return new VectorIterator(this.columns) {
/*  951 */         private int j = -1;
/*  952 */         private int k = CRSMatrix.this.rowPointers[ii];
/*      */ 
/*      */         
/*      */         public int index() {
/*  956 */           return this.j;
/*      */         }
/*      */ 
/*      */         
/*      */         public double get() {
/*  961 */           if (this.k < CRSMatrix.this.rowPointers[ii + 1] && CRSMatrix.this.columnIndices[this.k] == this.j) {
/*  962 */             return CRSMatrix.this.values[this.k];
/*      */           }
/*  964 */           return 0.0D;
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(double value) {
/*  969 */           if (this.k < CRSMatrix.this.rowPointers[ii + 1] && CRSMatrix.this.columnIndices[this.k] == this.j) {
/*  970 */             if (value == 0.0D) {
/*  971 */               CRSMatrix.this.remove(this.k, ii);
/*      */             } else {
/*  973 */               CRSMatrix.this.values[this.k] = value;
/*      */             } 
/*      */           } else {
/*  976 */             CRSMatrix.this.insert(this.k, ii, this.j, value);
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  982 */           return (this.j + 1 < CRSMatrix.this.columns);
/*      */         }
/*      */ 
/*      */         
/*      */         public Double next() {
/*  987 */           this.j++;
/*  988 */           if (this.k < CRSMatrix.this.rowPointers[ii + 1] && CRSMatrix.this.columnIndices[this.k] == this.j - 1) {
/*  989 */             this.k++;
/*      */           }
/*      */           
/*  992 */           return Double.valueOf(get());
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] toBinary() {
/*  999 */     int size = 13 + 8 * this.cardinality + 4 * this.cardinality + 4 * (this.rows + 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1007 */     ByteBuffer buffer = ByteBuffer.allocate(size);
/*      */     
/* 1009 */     buffer.put((byte)32);
/* 1010 */     buffer.putInt(this.rows);
/* 1011 */     buffer.putInt(this.columns);
/* 1012 */     buffer.putInt(this.cardinality);
/*      */     int i;
/* 1014 */     for (i = 0; i < this.cardinality; i++) {
/* 1015 */       buffer.putInt(this.columnIndices[i]);
/* 1016 */       buffer.putDouble(this.values[i]);
/*      */     } 
/*      */     
/* 1019 */     for (i = 0; i < this.rows + 1; i++) {
/* 1020 */       buffer.putInt(this.rowPointers[i]);
/*      */     }
/*      */     
/* 1023 */     return buffer.array();
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/matrix/sparse/CRSMatrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */