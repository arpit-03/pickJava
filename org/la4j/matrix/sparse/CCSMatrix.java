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
/*      */ import org.la4j.iterator.ColumnMajorMatrixIterator;
/*      */ import org.la4j.iterator.VectorIterator;
/*      */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*      */ import org.la4j.matrix.MatrixFactory;
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
/*      */ public class CCSMatrix
/*      */   extends ColumnMajorSparseMatrix
/*      */ {
/*      */   private static final byte MATRIX_TAG = 48;
/*      */   private static final int MINIMUM_SIZE = 32;
/*      */   private double[] values;
/*      */   private int[] rowIndices;
/*      */   private int[] columnPointers;
/*      */   
/*      */   public static CCSMatrix zero(int rows, int columns) {
/*   57 */     return new CCSMatrix(rows, columns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CCSMatrix zero(int rows, int columns, int capacity) {
/*   65 */     return new CCSMatrix(rows, columns, capacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CCSMatrix diagonal(int size, double diagonal) {
/*   73 */     double[] values = new double[size];
/*   74 */     int[] rowIndices = new int[size];
/*   75 */     int[] columnPointers = new int[size + 1];
/*      */     
/*   77 */     for (int i = 0; i < size; i++) {
/*   78 */       rowIndices[i] = i;
/*   79 */       columnPointers[i] = i;
/*   80 */       values[i] = diagonal;
/*      */     } 
/*      */     
/*   83 */     columnPointers[size] = size;
/*      */     
/*   85 */     return new CCSMatrix(size, size, size, values, rowIndices, columnPointers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CCSMatrix identity(int size) {
/*   92 */     return diagonal(size, 1.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CCSMatrix random(int rows, int columns, double density, Random random) {
/*  100 */     if (density < 0.0D || density > 1.0D) {
/*  101 */       throw new IllegalArgumentException("The density value should be between 0 and 1.0");
/*      */     }
/*      */     
/*  104 */     int cardinality = Math.max((int)((rows * columns) * density), columns);
/*      */     
/*  106 */     double[] values = new double[cardinality];
/*  107 */     int[] rowIndices = new int[cardinality];
/*  108 */     int[] columnPointers = new int[columns + 1];
/*      */     
/*  110 */     int kk = cardinality / columns;
/*  111 */     int[] indices = new int[kk];
/*      */     
/*  113 */     int k = 0;
/*  114 */     for (int j = 0; j < columns; j++) {
/*      */       
/*  116 */       columnPointers[j] = k;
/*      */       
/*  118 */       for (int jj = 0; jj < kk; jj++) {
/*  119 */         indices[jj] = random.nextInt(rows);
/*      */       }
/*      */       
/*  122 */       Arrays.sort(indices);
/*      */       
/*  124 */       int previous = -1;
/*  125 */       for (int i = 0; i < kk; i++) {
/*      */         
/*  127 */         if (indices[i] != previous) {
/*      */ 
/*      */ 
/*      */           
/*  131 */           values[k] = random.nextDouble();
/*  132 */           rowIndices[k++] = indices[i];
/*  133 */           previous = indices[i];
/*      */         } 
/*      */       } 
/*      */     } 
/*  137 */     columnPointers[columns] = cardinality;
/*      */     
/*  139 */     return new CCSMatrix(rows, columns, cardinality, values, rowIndices, columnPointers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CCSMatrix randomSymmetric(int size, double density, Random random) {
/*  147 */     int cardinality = (int)((size * size) * density);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  155 */     CCSMatrix matrix = new CCSMatrix(size, size, cardinality);
/*      */     
/*  157 */     for (int k = 0; k < cardinality / 2; k++) {
/*  158 */       int i = random.nextInt(size);
/*  159 */       int j = random.nextInt(size);
/*  160 */       double value = random.nextDouble();
/*      */       
/*  162 */       matrix.set(i, j, value);
/*  163 */       matrix.set(j, i, value);
/*      */     } 
/*      */     
/*  166 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CCSMatrix from1DArray(int rows, int columns, double[] array) {
/*  174 */     CCSMatrix result = zero(rows, columns);
/*      */     
/*  176 */     for (int j = 0; j < columns; j++) {
/*  177 */       for (int i = 0; i < rows; i++) {
/*  178 */         int k = i * columns + j;
/*  179 */         if (array[k] != 0.0D) {
/*  180 */           result.set(i, j, array[k]);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  185 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CCSMatrix from2DArray(double[][] array) {
/*  193 */     int rows = array.length;
/*  194 */     int columns = (array[0]).length;
/*  195 */     CCSMatrix result = zero(rows, columns);
/*      */     
/*  197 */     for (int j = 0; j < columns; j++) {
/*  198 */       for (int i = 0; i < rows; i++) {
/*  199 */         if (array[i][j] != 0.0D) {
/*  200 */           result.set(i, j, array[i][j]);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  205 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CCSMatrix block(Matrix a, Matrix b, Matrix c, Matrix d) {
/*  213 */     if (a.rows() != b.rows() || a.columns() != c.columns() || c.rows() != d.rows() || b.columns() != d.columns())
/*      */     {
/*  215 */       throw new IllegalArgumentException("Sides of blocks are incompatible!");
/*      */     }
/*      */     
/*  218 */     int rows = a.rows() + c.rows(), columns = a.columns() + b.columns();
/*  219 */     ArrayList<Double> values = new ArrayList<>();
/*  220 */     ArrayList<Integer> rowIndices = new ArrayList<>();
/*  221 */     int[] columnPointers = new int[rows + 1];
/*      */     
/*  223 */     int k = 0;
/*  224 */     columnPointers[0] = 0;
/*  225 */     double current = 0.0D;
/*  226 */     for (int i = 0; i < rows; i++) {
/*  227 */       for (int m = 0; m < columns; m++) {
/*  228 */         if (i < a.rows() && m < a.columns()) {
/*  229 */           current = a.get(i, m);
/*      */         }
/*  231 */         if (i < a.rows() && m > a.columns()) {
/*  232 */           current = b.get(i, m);
/*      */         }
/*  234 */         if (i > a.rows() && m < a.columns()) {
/*  235 */           current = c.get(i, m);
/*      */         }
/*  237 */         if (i > a.rows() && m > a.columns()) {
/*  238 */           current = d.get(i, m);
/*      */         }
/*  240 */         if (Math.abs(current) > Matrices.EPS) {
/*  241 */           values.add(Double.valueOf(current));
/*  242 */           rowIndices.add(Integer.valueOf(m));
/*  243 */           k++;
/*      */         } 
/*      */       } 
/*  246 */       columnPointers[i + 1] = k;
/*      */     } 
/*  248 */     double[] valuesArray = new double[values.size()];
/*  249 */     int[] rowIndArray = new int[rowIndices.size()];
/*  250 */     for (int j = 0; j < values.size(); j++) {
/*  251 */       valuesArray[j] = ((Double)values.get(j)).doubleValue();
/*  252 */       rowIndArray[j] = ((Integer)rowIndices.get(j)).intValue();
/*      */     } 
/*      */     
/*  255 */     return new CCSMatrix(rows, columns, k, valuesArray, rowIndArray, columnPointers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CCSMatrix fromBinary(byte[] array) {
/*  266 */     ByteBuffer buffer = ByteBuffer.wrap(array);
/*      */     
/*  268 */     if (buffer.get() != 48) {
/*  269 */       throw new IllegalArgumentException("Can not decode CCSMatrix from the given byte array.");
/*      */     }
/*      */     
/*  272 */     int rows = buffer.getInt();
/*  273 */     int columns = buffer.getInt();
/*  274 */     int cardinality = buffer.getInt();
/*      */     
/*  276 */     int[] rowIndices = new int[cardinality];
/*  277 */     double[] values = new double[cardinality];
/*  278 */     int[] columnsPointers = new int[columns + 1];
/*      */     int i;
/*  280 */     for (i = 0; i < cardinality; i++) {
/*  281 */       rowIndices[i] = buffer.getInt();
/*  282 */       values[i] = buffer.getDouble();
/*      */     } 
/*      */     
/*  285 */     for (i = 0; i < columns + 1; i++) {
/*  286 */       columnsPointers[i] = buffer.getInt();
/*      */     }
/*      */     
/*  289 */     return new CCSMatrix(rows, columns, cardinality, values, rowIndices, columnsPointers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CCSMatrix fromCSV(String csv) {
/*  300 */     return (CCSMatrix)Matrix.fromCSV(csv).to(Matrices.CCS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CCSMatrix fromMatrixMarket(String mm) {
/*  311 */     return (CCSMatrix)Matrix.fromMatrixMarket(mm).to(Matrices.CCS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CCSMatrix() {
/*  321 */     this(0, 0);
/*      */   }
/*      */   
/*      */   public CCSMatrix(int rows, int columns) {
/*  325 */     this(rows, columns, 0);
/*      */   }
/*      */   
/*      */   public CCSMatrix(int rows, int columns, int capacity) {
/*  329 */     super(rows, columns);
/*  330 */     ensureCardinalityIsCorrect(rows, columns, capacity);
/*      */     
/*  332 */     int alignedSize = align(capacity);
/*  333 */     this.values = new double[alignedSize];
/*  334 */     this.rowIndices = new int[alignedSize];
/*  335 */     this.columnPointers = new int[columns + 1];
/*      */   }
/*      */   
/*      */   public CCSMatrix(int rows, int columns, int cardinality, double[] values, int[] rowIndices, int[] columnPointers) {
/*  339 */     super(rows, columns, cardinality);
/*  340 */     ensureCardinalityIsCorrect(rows, columns, cardinality);
/*      */     
/*  342 */     this.values = values;
/*  343 */     this.rowIndices = rowIndices;
/*  344 */     this.columnPointers = columnPointers;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getOrElse(int i, int j, double defaultValue) {
/*  349 */     ensureIndexesAreInBounds(i, j);
/*  350 */     int k = searchForRowIndex(i, this.columnPointers[j], this.columnPointers[j + 1]);
/*      */     
/*  352 */     if (k < this.columnPointers[j + 1] && this.rowIndices[k] == i) {
/*  353 */       return this.values[k];
/*      */     }
/*      */     
/*  356 */     return defaultValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void set(int i, int j, double value) {
/*  361 */     ensureIndexesAreInBounds(i, j);
/*  362 */     int k = searchForRowIndex(i, this.columnPointers[j], this.columnPointers[j + 1]);
/*      */     
/*  364 */     if (k < this.columnPointers[j + 1] && this.rowIndices[k] == i) {
/*      */       
/*  366 */       if (value == 0.0D) {
/*  367 */         remove(k, j);
/*      */       } else {
/*  369 */         this.values[k] = value;
/*      */       } 
/*      */     } else {
/*  372 */       insert(k, i, j, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAll(double value) {
/*  378 */     if (value == 0.0D) {
/*  379 */       this.cardinality = 0;
/*      */     } else {
/*  381 */       int size = (int)capacity();
/*      */       
/*  383 */       if (this.values.length < size) {
/*  384 */         this.values = new double[size];
/*  385 */         this.rowIndices = new int[size];
/*  386 */         this.columnPointers = new int[this.columns + 1];
/*      */       } 
/*      */       
/*  389 */       for (int j = 0; j < this.columns; j++) {
/*  390 */         for (int i = 0; i < this.rows; i++) {
/*  391 */           this.values[j * this.rows + i] = value;
/*  392 */           this.rowIndices[j * this.rows + i] = i;
/*      */         } 
/*  394 */         this.columnPointers[j] = this.rows * j;
/*      */       } 
/*      */       
/*  397 */       this.columnPointers[this.columns] = size;
/*  398 */       this.cardinality = size;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Vector getColumn(int j) {
/*  404 */     int columnCardinality = this.columnPointers[j + 1] - this.columnPointers[j];
/*  405 */     double[] columnValues = new double[columnCardinality];
/*  406 */     int[] columnIndices = new int[columnCardinality];
/*      */     
/*  408 */     System.arraycopy(this.values, this.columnPointers[j], columnValues, 0, columnCardinality);
/*      */     
/*  410 */     System.arraycopy(this.rowIndices, this.columnPointers[j], columnIndices, 0, columnCardinality);
/*      */ 
/*      */     
/*  413 */     return (Vector)new CompressedVector(this.rows, columnCardinality, columnValues, columnIndices);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getRow(int i) {
/*  419 */     CompressedVector compressedVector = CompressedVector.zero(this.columns);
/*  420 */     int j = 0;
/*      */     
/*  422 */     while (this.columnPointers[j] < this.cardinality) {
/*  423 */       int k = searchForRowIndex(i, this.columnPointers[j], this.columnPointers[j + 1]);
/*  424 */       if (k < this.columnPointers[j + 1] && this.rowIndices[k] == i) {
/*  425 */         compressedVector.set(j, this.values[k]);
/*      */       }
/*      */       
/*  428 */       j++;
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
/*  441 */       int[] arrayOfInt2 = new int[columns + 1];
/*      */       
/*  443 */       System.arraycopy(this.values, 0, arrayOfDouble, 0, this.cardinality);
/*  444 */       System.arraycopy(this.rowIndices, 0, arrayOfInt1, 0, this.cardinality);
/*  445 */       System.arraycopy(this.columnPointers, 0, arrayOfInt2, 0, this.columns + 1);
/*      */       
/*  447 */       for (int i = this.columns; i < columns + 1; i++) {
/*  448 */         arrayOfInt2[i] = this.cardinality;
/*      */       }
/*      */       
/*  451 */       return (Matrix)new CCSMatrix(rows, columns, this.cardinality, arrayOfDouble, arrayOfInt1, arrayOfInt2);
/*      */     } 
/*      */     
/*  454 */     double[] $values = new double[align(this.cardinality)];
/*  455 */     int[] $rowIndices = new int[align(this.cardinality)];
/*  456 */     int[] $columnPointers = new int[columns + 1];
/*      */     
/*  458 */     int $cardinality = 0;
/*      */     
/*  460 */     int k = 0, j = 0;
/*  461 */     while (k < this.cardinality && j < columns) {
/*      */       
/*  463 */       $columnPointers[j] = $cardinality;
/*      */       
/*  465 */       int i = this.columnPointers[j];
/*  466 */       for (; i < this.columnPointers[j + 1] && this.rowIndices[i] < rows; i++, k++) {
/*      */         
/*  468 */         $values[$cardinality] = this.values[i];
/*  469 */         $rowIndices[$cardinality] = this.rowIndices[i];
/*  470 */         $cardinality++;
/*      */       } 
/*  472 */       j++;
/*      */     } 
/*      */     
/*  475 */     for (; j < columns + 1; j++) {
/*  476 */       $columnPointers[j] = $cardinality;
/*      */     }
/*      */     
/*  479 */     return (Matrix)new CCSMatrix(rows, columns, $cardinality, $values, $rowIndices, $columnPointers);
/*      */   }
/*      */ 
/*      */   
/*      */   public void eachNonZero(MatrixProcedure procedure) {
/*  484 */     int k = 0, j = 0;
/*  485 */     while (k < this.cardinality) {
/*  486 */       for (int i = this.columnPointers[j]; i < this.columnPointers[j + 1]; i++, k++) {
/*  487 */         procedure.apply(this.rowIndices[i], j, this.values[i]);
/*      */       }
/*      */       
/*  490 */       j++;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void each(MatrixProcedure procedure) {
/*  496 */     int k = 0;
/*  497 */     for (int i = 0; i < this.rows; i++) {
/*  498 */       int valuesSoFar = this.columnPointers[i + 1];
/*  499 */       for (int j = 0; j < this.columns; j++) {
/*  500 */         if (k < valuesSoFar && j == this.rowIndices[k]) {
/*  501 */           procedure.apply(i, j, this.values[k++]);
/*      */         } else {
/*  503 */           procedure.apply(i, j, 0.0D);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void eachInColumn(int j, VectorProcedure procedure) {
/*  512 */     int k = this.columnPointers[j];
/*  513 */     int valuesSoFar = this.columnPointers[j + 1];
/*  514 */     for (int i = 0; i < this.rows; i++) {
/*  515 */       if (k < valuesSoFar && i == this.rowIndices[k]) {
/*  516 */         procedure.apply(i, this.values[k++]);
/*      */       } else {
/*  518 */         procedure.apply(i, 0.0D);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void eachNonZeroInColumn(int j, VectorProcedure procedure) {
/*  525 */     for (int i = this.columnPointers[j]; i < this.columnPointers[j + 1]; i++) {
/*  526 */       procedure.apply(this.rowIndices[i], this.values[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateAt(int i, int j, MatrixFunction function) {
/*  532 */     int k = searchForRowIndex(i, this.columnPointers[j], this.columnPointers[j + 1]);
/*  533 */     if (k < this.columnPointers[j + 1] && this.rowIndices[k] == i) {
/*  534 */       double value = function.evaluate(i, j, this.values[k]);
/*      */       
/*  536 */       if (value == 0.0D) {
/*  537 */         remove(k, j);
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
/*  548 */     int k = searchForRowIndex(i, this.columnPointers[j], this.columnPointers[j + 1]);
/*  549 */     return (k < this.columnPointers[j + 1] && this.rowIndices[k] == i);
/*      */   }
/*      */   
/*      */   private int searchForRowIndex(int i, int left, int right) {
/*  553 */     if (right - left == 0 || i > this.rowIndices[right - 1]) {
/*  554 */       return right;
/*      */     }
/*      */     
/*  557 */     while (left < right) {
/*  558 */       int p = (left + right) / 2;
/*  559 */       if (this.rowIndices[p] > i) {
/*  560 */         right = p; continue;
/*  561 */       }  if (this.rowIndices[p] < i) {
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
/*      */   
/*      */   private void insert(int k, int i, int j, double value) {
/*  574 */     if (value == 0.0D) {
/*      */       return;
/*      */     }
/*      */     
/*  578 */     if (this.values.length < this.cardinality + 1) {
/*  579 */       growUp();
/*      */     }
/*      */     
/*  582 */     if (this.cardinality - k > 0) {
/*  583 */       System.arraycopy(this.values, k, this.values, k + 1, this.cardinality - k);
/*  584 */       System.arraycopy(this.rowIndices, k, this.rowIndices, k + 1, this.cardinality - k);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  592 */     this.values[k] = value;
/*  593 */     this.rowIndices[k] = i;
/*      */     
/*  595 */     for (int jj = j + 1; jj < this.columns + 1; jj++) {
/*  596 */       this.columnPointers[jj] = this.columnPointers[jj] + 1;
/*      */     }
/*      */     
/*  599 */     this.cardinality++;
/*      */   }
/*      */   
/*      */   private void remove(int k, int j) {
/*  603 */     this.cardinality--;
/*      */     
/*  605 */     if (this.cardinality - k > 0) {
/*  606 */       System.arraycopy(this.values, k + 1, this.values, k, this.cardinality - k);
/*  607 */       System.arraycopy(this.rowIndices, k + 1, this.rowIndices, k, this.cardinality - k);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  615 */     for (int jj = j + 1; jj < this.columns + 1; jj++) {
/*  616 */       this.columnPointers[jj] = this.columnPointers[jj] - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void growUp() {
/*  622 */     if (this.values.length == capacity())
/*      */     {
/*  624 */       throw new IllegalStateException("This matrix can't grow up.");
/*      */     }
/*      */     
/*  627 */     int min = (this.rows != 0 && this.columns > Integer.MAX_VALUE / this.rows) ? Integer.MAX_VALUE : (this.rows * this.columns);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  632 */     int capacity = Math.min(min, this.cardinality * 3 / 2 + 1);
/*      */     
/*  634 */     double[] $values = new double[capacity];
/*  635 */     int[] $rowIndices = new int[capacity];
/*      */     
/*  637 */     System.arraycopy(this.values, 0, $values, 0, this.cardinality);
/*  638 */     System.arraycopy(this.rowIndices, 0, $rowIndices, 0, this.cardinality);
/*      */     
/*  640 */     this.values = $values;
/*  641 */     this.rowIndices = $rowIndices;
/*      */   }
/*      */   
/*      */   private int align(int cardinality) {
/*  645 */     return (cardinality / 32 + 1) * 32;
/*      */   }
/*      */ 
/*      */   
/*      */   public double max() {
/*  650 */     double max = Double.NEGATIVE_INFINITY;
/*      */     
/*  652 */     for (int i = 0; i < this.cardinality; i++) {
/*  653 */       if (this.values[i] > max) {
/*  654 */         max = this.values[i];
/*      */       }
/*      */     } 
/*      */     
/*  658 */     return (max > 0.0D) ? max : 0.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   public double min() {
/*  663 */     double min = Double.POSITIVE_INFINITY;
/*      */     
/*  665 */     for (int i = 0; i < this.cardinality; i++) {
/*  666 */       if (this.values[i] < min) {
/*  667 */         min = this.values[i];
/*      */       }
/*      */     } 
/*      */     
/*  671 */     return (min < 0.0D) ? min : 0.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   public double maxInColumn(int j) {
/*  676 */     double max = Double.NEGATIVE_INFINITY;
/*      */     
/*  678 */     for (int k = this.columnPointers[j]; k < this.columnPointers[j + 1]; k++) {
/*  679 */       if (this.values[k] > max) {
/*  680 */         max = this.values[k];
/*      */       }
/*      */     } 
/*      */     
/*  684 */     return (max > 0.0D) ? max : 0.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   public double minInColumn(int j) {
/*  689 */     double min = Double.POSITIVE_INFINITY;
/*      */     
/*  691 */     for (int k = this.columnPointers[j]; k < this.columnPointers[j + 1]; k++) {
/*  692 */       if (this.values[k] < min) {
/*  693 */         min = this.values[k];
/*      */       }
/*      */     } 
/*      */     
/*  697 */     return (min < 0.0D) ? min : 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix select(int[] rowIndices, int[] columnIndices) {
/*  705 */     int newRows = rowIndices.length;
/*  706 */     int newCols = columnIndices.length;
/*      */     
/*  708 */     if (newRows == 0 || newCols == 0) {
/*  709 */       fail("No rows or columns selected.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  715 */     int newCardinality = 0;
/*  716 */     for (int i = 0; i < newRows; i++) {
/*  717 */       for (int k = 0; k < newCols; k++) {
/*  718 */         if (get(rowIndices[i], columnIndices[k]) != 0.0D) {
/*  719 */           newCardinality++;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  725 */     double[] newValues = new double[newCardinality];
/*  726 */     int[] newRowIndices = new int[newCardinality];
/*  727 */     int[] newColumnPointers = new int[newCols + 1];
/*      */     
/*  729 */     newColumnPointers[0] = 0;
/*  730 */     int endPtr = 0;
/*  731 */     for (int j = 0; j < newCols; j++) {
/*  732 */       newColumnPointers[j + 1] = newColumnPointers[j];
/*  733 */       for (int k = 0; k < newRows; k++) {
/*  734 */         double val = get(rowIndices[k], columnIndices[j]);
/*  735 */         if (val != 0.0D) {
/*  736 */           newValues[endPtr] = val;
/*  737 */           newRowIndices[endPtr] = k;
/*  738 */           endPtr++;
/*  739 */           newColumnPointers[j + 1] = newColumnPointers[j + 1] + 1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  744 */     return (Matrix)new CCSMatrix(newRows, newCols, newCardinality, newValues, newRowIndices, newColumnPointers);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends Matrix> T to(MatrixFactory<T> factory) {
/*  750 */     if (factory.outputClass == CCSMatrix.class) {
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
/*      */   public Iterator<Integer> iteratorOrNonZeroColumns() {
/*  764 */     return new Iterator<Integer>() {
/*  765 */         private int j = -1;
/*      */ 
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  770 */           while (this.j + 1 < CCSMatrix.this.columns && CCSMatrix.this.columnPointers[this.j + 1] < CCSMatrix.this.cardinality && CCSMatrix.this.columnPointers[this.j + 1] == CCSMatrix.this.columnPointers[this.j + 2])
/*      */           {
/*  772 */             this.j++;
/*      */           }
/*      */           
/*  775 */           return (this.j + 1 < CCSMatrix.this.columns && CCSMatrix.this.columnPointers[this.j + 1] < CCSMatrix.this.cardinality);
/*      */         }
/*      */ 
/*      */         
/*      */         public Integer next() {
/*  780 */           this.j++;
/*  781 */           return Integer.valueOf(this.j);
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
/*      */   public ColumnMajorMatrixIterator columnMajorIterator() {
/*  793 */     return new ColumnMajorMatrixIterator(this.rows, this.columns) {
/*  794 */         private long limit = this.rows * this.columns;
/*      */         private boolean currentNonZero = false;
/*  796 */         private int i = -1;
/*  797 */         private int k = 0;
/*      */ 
/*      */         
/*      */         public int rowIndex() {
/*  801 */           return this.i - columnIndex() * this.rows;
/*      */         }
/*      */ 
/*      */         
/*      */         public int columnIndex() {
/*  806 */           return this.i / this.rows;
/*      */         }
/*      */ 
/*      */         
/*      */         public double get() {
/*  811 */           return this.currentNonZero ? CCSMatrix.this.values[this.k] : 0.0D;
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(double value) {
/*  816 */           if (this.currentNonZero) {
/*  817 */             if (value == 0.0D) {
/*  818 */               CCSMatrix.this.remove(this.k, columnIndex());
/*  819 */               this.currentNonZero = false;
/*      */             } else {
/*  821 */               CCSMatrix.this.values[this.k] = value;
/*      */             } 
/*      */           } else {
/*  824 */             CCSMatrix.this.insert(this.k, rowIndex(), columnIndex(), value);
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
/*  841 */           this.currentNonZero = (this.k < CCSMatrix.this.columnPointers[columnIndex() + 1] && CCSMatrix.this.rowIndices[this.k] == rowIndex());
/*      */           
/*  843 */           return Double.valueOf(get());
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public ColumnMajorMatrixIterator nonZeroColumnMajorIterator() {
/*  850 */     return new ColumnMajorMatrixIterator(this.rows, this.columns) {
/*  851 */         private int j = 0;
/*  852 */         private int k = -1;
/*      */         private boolean currentIsRemoved = false;
/*  854 */         private int removedIndex = -1;
/*      */ 
/*      */         
/*      */         public int rowIndex() {
/*  858 */           return this.currentIsRemoved ? this.removedIndex : CCSMatrix.this.rowIndices[this.k];
/*      */         }
/*      */ 
/*      */         
/*      */         public int columnIndex() {
/*  863 */           return this.j;
/*      */         }
/*      */ 
/*      */         
/*      */         public double get() {
/*  868 */           return this.currentIsRemoved ? 0.0D : CCSMatrix.this.values[this.k];
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(double value) {
/*  873 */           if (value == 0.0D && !this.currentIsRemoved) {
/*  874 */             this.currentIsRemoved = true;
/*  875 */             this.removedIndex = CCSMatrix.this.rowIndices[this.k];
/*  876 */             CCSMatrix.this.remove(this.k--, this.j);
/*  877 */           } else if (value != 0.0D && !this.currentIsRemoved) {
/*  878 */             CCSMatrix.this.values[this.k] = value;
/*      */           } else {
/*  880 */             this.currentIsRemoved = false;
/*  881 */             CCSMatrix.this.insert(++this.k, this.removedIndex, this.j, value);
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  887 */           return (this.k + 1 < CCSMatrix.this.cardinality);
/*      */         }
/*      */ 
/*      */         
/*      */         public Double next() {
/*  892 */           this.currentIsRemoved = false;
/*  893 */           this.k++;
/*  894 */           while (CCSMatrix.this.columnPointers[this.j + 1] == this.k) {
/*  895 */             this.j++;
/*      */           }
/*  897 */           return Double.valueOf(get());
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public VectorIterator nonZeroIteratorOfColumn(int j) {
/*  904 */     final int jj = j;
/*  905 */     return new VectorIterator(this.rows) {
/*  906 */         private int k = CCSMatrix.this.columnPointers[jj] - 1;
/*      */         private boolean currentIsRemoved = false;
/*  908 */         private int removedIndex = -1;
/*      */ 
/*      */         
/*      */         public int index() {
/*  912 */           return this.currentIsRemoved ? this.removedIndex : CCSMatrix.this.rowIndices[this.k];
/*      */         }
/*      */ 
/*      */         
/*      */         public double get() {
/*  917 */           return this.currentIsRemoved ? 0.0D : CCSMatrix.this.values[this.k];
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(double value) {
/*  922 */           if (value == 0.0D && !this.currentIsRemoved) {
/*  923 */             this.currentIsRemoved = true;
/*  924 */             this.removedIndex = CCSMatrix.this.rowIndices[this.k];
/*  925 */             CCSMatrix.this.remove(this.k--, jj);
/*  926 */           } else if (value != 0.0D && !this.currentIsRemoved) {
/*  927 */             CCSMatrix.this.values[this.k] = value;
/*      */           } else {
/*  929 */             this.currentIsRemoved = false;
/*  930 */             CCSMatrix.this.insert(++this.k, this.removedIndex, jj, value);
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  936 */           return (this.k + 1 < CCSMatrix.this.columnPointers[jj + 1]);
/*      */         }
/*      */ 
/*      */         
/*      */         public Double next() {
/*  941 */           this.currentIsRemoved = false;
/*  942 */           return Double.valueOf(CCSMatrix.this.values[++this.k]);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public VectorIterator iteratorOfColumn(int j) {
/*  949 */     final int jj = j;
/*  950 */     return new VectorIterator(this.rows) {
/*  951 */         private int i = -1;
/*  952 */         private int k = CCSMatrix.this.columnPointers[jj];
/*      */ 
/*      */         
/*      */         public int index() {
/*  956 */           return this.i;
/*      */         }
/*      */ 
/*      */         
/*      */         public double get() {
/*  961 */           if (this.k < CCSMatrix.this.columnPointers[jj + 1] && CCSMatrix.this.rowIndices[this.k] == this.i) {
/*  962 */             return CCSMatrix.this.values[this.k];
/*      */           }
/*  964 */           return 0.0D;
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(double value) {
/*  969 */           if (this.k < CCSMatrix.this.columnPointers[jj + 1] && CCSMatrix.this.rowIndices[this.k] == this.i) {
/*  970 */             if (value == 0.0D) {
/*  971 */               CCSMatrix.this.remove(this.k, jj);
/*      */             } else {
/*  973 */               CCSMatrix.this.values[this.k] = value;
/*      */             } 
/*      */           } else {
/*  976 */             CCSMatrix.this.insert(this.k, this.i, jj, value);
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  982 */           return (this.i + 1 < CCSMatrix.this.rows);
/*      */         }
/*      */ 
/*      */         
/*      */         public Double next() {
/*  987 */           this.i++;
/*  988 */           if (this.k < CCSMatrix.this.columnPointers[jj + 1] && CCSMatrix.this.rowIndices[this.k] == this.i - 1) {
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
/*  999 */     int size = 13 + 8 * this.cardinality + 4 * this.cardinality + 4 * (this.columns + 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1007 */     ByteBuffer buffer = ByteBuffer.allocate(size);
/*      */     
/* 1009 */     buffer.put((byte)48);
/* 1010 */     buffer.putInt(this.rows);
/* 1011 */     buffer.putInt(this.columns);
/* 1012 */     buffer.putInt(this.cardinality);
/*      */     int i;
/* 1014 */     for (i = 0; i < this.cardinality; i++) {
/* 1015 */       buffer.putInt(this.rowIndices[i]);
/* 1016 */       buffer.putDouble(this.values[i]);
/*      */     } 
/*      */     
/* 1019 */     for (i = 0; i < this.columns + 1; i++) {
/* 1020 */       buffer.putInt(this.columnPointers[i]);
/*      */     }
/*      */     
/* 1023 */     return buffer.array();
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/matrix/sparse/CCSMatrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */