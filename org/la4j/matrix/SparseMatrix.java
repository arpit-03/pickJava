/*     */ package org.la4j.matrix;
/*     */ 
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Random;
/*     */ import org.la4j.Matrices;
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.Vector;
/*     */ import org.la4j.Vectors;
/*     */ import org.la4j.iterator.ColumnMajorMatrixIterator;
/*     */ import org.la4j.iterator.MatrixIterator;
/*     */ import org.la4j.iterator.RowMajorMatrixIterator;
/*     */ import org.la4j.iterator.VectorIterator;
/*     */ import org.la4j.matrix.functor.MatrixAccumulator;
/*     */ import org.la4j.matrix.functor.MatrixProcedure;
/*     */ import org.la4j.matrix.sparse.CCSMatrix;
/*     */ import org.la4j.matrix.sparse.CRSMatrix;
/*     */ import org.la4j.vector.SparseVector;
/*     */ import org.la4j.vector.functor.VectorAccumulator;
/*     */ import org.la4j.vector.functor.VectorProcedure;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SparseMatrix
/*     */   extends Matrix
/*     */ {
/*     */   protected int cardinality;
/*     */   
/*     */   public static SparseMatrix zero(int rows, int columns) {
/*  50 */     return (SparseMatrix)CCSMatrix.zero(rows, columns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseMatrix zero(int rows, int columns, int capacity) {
/*  58 */     return (SparseMatrix)CRSMatrix.zero(rows, columns, capacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseMatrix diagonal(int size, double diagonal) {
/*  66 */     return (SparseMatrix)CRSMatrix.diagonal(size, diagonal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseMatrix identity(int size) {
/*  73 */     return (SparseMatrix)CRSMatrix.identity(size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseMatrix random(int rows, int columns, double density, Random random) {
/*  81 */     return (SparseMatrix)CRSMatrix.random(rows, columns, density, random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseMatrix randomSymmetric(int size, double density, Random random) {
/*  88 */     return (SparseMatrix)CRSMatrix.randomSymmetric(size, density, random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseMatrix from1DArray(int rows, int columns, double[] array) {
/*  96 */     return (SparseMatrix)CRSMatrix.from1DArray(rows, columns, array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseMatrix from2DArray(double[][] array) {
/* 104 */     return (SparseMatrix)CRSMatrix.from2DArray(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseMatrix block(Matrix a, Matrix b, Matrix c, Matrix d) {
/* 112 */     return (SparseMatrix)CRSMatrix.block(a, b, c, d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseMatrix fromCSV(String csv) {
/* 123 */     return (SparseMatrix)Matrix.fromCSV(csv).to(Matrices.SPARSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseMatrix fromMatrixMarket(String mm) {
/* 134 */     return (SparseMatrix)Matrix.fromMatrixMarket(mm).to(Matrices.SPARSE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseMatrix(int rows, int columns) {
/* 140 */     this(rows, columns, 0);
/*     */   }
/*     */   
/*     */   public SparseMatrix(int rows, int columns, int cardinality) {
/* 144 */     super(rows, columns);
/* 145 */     this.cardinality = cardinality;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int i, int j) {
/* 150 */     return getOrElse(i, j, 0.0D);
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
/*     */   public boolean isColumnMajor() {
/* 171 */     return !isRowMajor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int cardinality() {
/* 181 */     return this.cardinality;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double density() {
/* 191 */     return this.cardinality / (this.rows * this.columns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long capacity() {
/* 198 */     return this.rows * this.columns;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getRow(int i) {
/* 203 */     SparseVector sparseVector = SparseVector.zero(this.columns);
/* 204 */     VectorIterator it = nonZeroIteratorOfRow(i);
/*     */     
/* 206 */     while (it.hasNext()) {
/* 207 */       double x = ((Double)it.next()).doubleValue();
/* 208 */       int j = it.index();
/* 209 */       sparseVector.set(j, x);
/*     */     } 
/*     */     
/* 212 */     return (Vector)sparseVector;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getColumn(int j) {
/* 217 */     SparseVector sparseVector = SparseVector.zero(this.rows);
/* 218 */     VectorIterator it = nonZeroIteratorOfColumn(j);
/*     */     
/* 220 */     while (it.hasNext()) {
/* 221 */       double x = ((Double)it.next()).doubleValue();
/* 222 */       int i = it.index();
/* 223 */       sparseVector.set(i, x);
/*     */     } 
/*     */     
/* 226 */     return (Vector)sparseVector;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix multiply(double value) {
/* 231 */     MatrixIterator it = nonZeroIterator();
/* 232 */     Matrix result = blank();
/*     */     
/* 234 */     while (it.hasNext()) {
/* 235 */       double x = ((Double)it.next()).doubleValue();
/* 236 */       int i = it.rowIndex();
/* 237 */       int j = it.columnIndex();
/* 238 */       result.set(i, j, x * value);
/*     */     } 
/*     */     
/* 241 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix add(double value) {
/* 246 */     MatrixIterator it = nonZeroIterator();
/* 247 */     Matrix result = DenseMatrix.constant(this.rows, this.columns, value);
/*     */     
/* 249 */     while (it.hasNext()) {
/* 250 */       double x = ((Double)it.next()).doubleValue();
/* 251 */       int i = it.rowIndex();
/* 252 */       int j = it.columnIndex();
/* 253 */       result.set(i, j, x + value);
/*     */     } 
/*     */     
/* 256 */     return result;
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
/*     */   public boolean isZeroAt(int i, int j) {
/* 268 */     return !nonZeroAt(i, j);
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
/*     */   public void eachNonZero(MatrixProcedure procedure) {
/* 287 */     MatrixIterator it = nonZeroIterator();
/*     */     
/* 289 */     while (it.hasNext()) {
/* 290 */       double x = ((Double)it.next()).doubleValue();
/* 291 */       int i = it.rowIndex();
/* 292 */       int j = it.columnIndex();
/* 293 */       procedure.apply(i, j, x);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void eachNonZeroInRow(int i, VectorProcedure procedure) {
/* 304 */     VectorIterator it = nonZeroIteratorOfRow(i);
/*     */     
/* 306 */     while (it.hasNext()) {
/* 307 */       double x = ((Double)it.next()).doubleValue();
/* 308 */       int j = it.index();
/* 309 */       procedure.apply(j, x);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void eachNonZeroInColumn(int j, VectorProcedure procedure) {
/* 320 */     VectorIterator it = nonZeroIteratorOfColumn(j);
/*     */     
/* 322 */     while (it.hasNext()) {
/* 323 */       double x = ((Double)it.next()).doubleValue();
/* 324 */       int i = it.index();
/* 325 */       procedure.apply(i, x);
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
/*     */   public double foldNonZero(MatrixAccumulator accumulator) {
/* 337 */     eachNonZero(Matrices.asAccumulatorProcedure(accumulator));
/* 338 */     return accumulator.accumulate();
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
/*     */   public double foldNonZeroInRow(int i, VectorAccumulator accumulator) {
/* 350 */     eachNonZeroInRow(i, Vectors.asAccumulatorProcedure(accumulator));
/* 351 */     return accumulator.accumulate();
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
/*     */   public double foldNonZeroInColumn(int j, VectorAccumulator accumulator) {
/* 363 */     eachNonZeroInColumn(j, Vectors.asAccumulatorProcedure(accumulator));
/* 364 */     return accumulator.accumulate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] foldNonZeroInColumns(VectorAccumulator accumulator) {
/* 375 */     double[] result = new double[this.columns];
/*     */     
/* 377 */     for (int j = 0; j < this.columns; j++) {
/* 378 */       result[j] = foldNonZeroInColumn(j, accumulator);
/*     */     }
/*     */     
/* 381 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] foldNonZeroInRows(VectorAccumulator accumulator) {
/* 392 */     double[] result = new double[this.rows];
/*     */     
/* 394 */     for (int i = 0; i < this.rows; i++) {
/* 395 */       result[i] = foldNonZeroInRow(i, accumulator);
/*     */     }
/*     */     
/* 398 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MatrixIterator nonZeroIterator() {
/* 407 */     return (MatrixIterator)nonZeroRowMajorIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RowMajorMatrixIterator nonZeroRowMajorIterator() {
/* 416 */     return new RowMajorMatrixIterator(this.rows, this.columns) {
/* 417 */         private long limit = this.rows * this.columns;
/* 418 */         private long i = -1L;
/*     */ 
/*     */         
/*     */         public int rowIndex() {
/* 422 */           return (int)(this.i / this.columns);
/*     */         }
/*     */ 
/*     */         
/*     */         public int columnIndex() {
/* 427 */           return (int)(this.i - this.i / this.columns * this.columns);
/*     */         }
/*     */ 
/*     */         
/*     */         public double get() {
/* 432 */           return SparseMatrix.this.get(rowIndex(), columnIndex());
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(double value) {
/* 437 */           SparseMatrix.this.set(rowIndex(), columnIndex(), value);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 442 */           while (this.i + 1L < this.limit) {
/* 443 */             this.i++;
/* 444 */             if (SparseMatrix.this.nonZeroAt(rowIndex(), columnIndex())) {
/* 445 */               this.i--;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 450 */           return (this.i + 1L < this.limit);
/*     */         }
/*     */ 
/*     */         
/*     */         public Double next() {
/* 455 */           this.i++;
/* 456 */           return Double.valueOf(get());
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColumnMajorMatrixIterator nonZeroColumnMajorIterator() {
/* 467 */     return new ColumnMajorMatrixIterator(this.rows, this.columns) {
/* 468 */         private long limit = this.rows * this.columns;
/* 469 */         private long i = -1L;
/*     */ 
/*     */         
/*     */         public int rowIndex() {
/* 473 */           return (int)(this.i - this.i / this.rows * this.rows);
/*     */         }
/*     */ 
/*     */         
/*     */         public int columnIndex() {
/* 478 */           return (int)(this.i / this.rows);
/*     */         }
/*     */ 
/*     */         
/*     */         public double get() {
/* 483 */           return SparseMatrix.this.get(rowIndex(), columnIndex());
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(double value) {
/* 488 */           SparseMatrix.this.set(rowIndex(), columnIndex(), value);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 493 */           while (this.i + 1L < this.limit) {
/* 494 */             this.i++;
/* 495 */             if (SparseMatrix.this.nonZeroAt(rowIndex(), columnIndex())) {
/* 496 */               this.i--;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 501 */           return (this.i + 1L < this.limit);
/*     */         }
/*     */ 
/*     */         
/*     */         public Double next() {
/* 506 */           this.i++;
/* 507 */           return Double.valueOf(get());
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VectorIterator nonZeroIteratorOfRow(int i) {
/* 518 */     final int ii = i;
/* 519 */     return new VectorIterator(this.columns) {
/* 520 */         private int j = -1;
/*     */ 
/*     */         
/*     */         public int index() {
/* 524 */           return this.j;
/*     */         }
/*     */ 
/*     */         
/*     */         public double get() {
/* 529 */           return SparseMatrix.this.get(ii, this.j);
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(double value) {
/* 534 */           SparseMatrix.this.set(ii, this.j, value);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 539 */           while (this.j + 1 < SparseMatrix.this.columns && SparseMatrix.this.isZeroAt(ii, this.j + 1)) {
/* 540 */             this.j++;
/*     */           }
/*     */           
/* 543 */           return (this.j + 1 < SparseMatrix.this.columns);
/*     */         }
/*     */ 
/*     */         
/*     */         public Double next() {
/* 548 */           this.j++;
/* 549 */           return Double.valueOf(get());
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VectorIterator nonZeroIteratorOfColumn(int j) {
/* 560 */     final int jj = j;
/* 561 */     return new VectorIterator(this.rows) {
/* 562 */         private int i = -1;
/*     */ 
/*     */         
/*     */         public int index() {
/* 566 */           return this.i;
/*     */         }
/*     */ 
/*     */         
/*     */         public double get() {
/* 571 */           return SparseMatrix.this.get(this.i, jj);
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(double value) {
/* 576 */           SparseMatrix.this.set(this.i, jj, value);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 581 */           while (this.i + 1 < SparseMatrix.this.rows && SparseMatrix.this.isZeroAt(this.i + 1, jj)) {
/* 582 */             this.i++;
/*     */           }
/*     */           
/* 585 */           return (this.i + 1 < SparseMatrix.this.rows);
/*     */         }
/*     */ 
/*     */         
/*     */         public Double next() {
/* 590 */           this.i++;
/* 591 */           return Double.valueOf(get());
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public String toMatrixMarket(NumberFormat formatter) {
/* 598 */     String majority = isRowMajor() ? "row-major" : "column-major";
/* 599 */     StringBuilder out = new StringBuilder();
/* 600 */     MatrixIterator it = nonZeroIterator();
/*     */     
/* 602 */     out.append("%%MatrixMarket matrix coordinate real general ").append(majority).append('\n');
/*     */     
/* 604 */     out.append(this.rows).append(' ').append(this.columns).append(' ').append(this.cardinality).append('\n');
/*     */     
/* 606 */     while (it.hasNext()) {
/* 607 */       double x = ((Double)it.next()).doubleValue();
/* 608 */       int i = it.rowIndex();
/* 609 */       int j = it.columnIndex();
/* 610 */       out.append(i + 1).append(' ').append(j + 1).append(' ').append(formatter.format(x)).append('\n');
/*     */     } 
/*     */ 
/*     */     
/* 614 */     return out.toString();
/*     */   }
/*     */   
/*     */   protected void ensureCardinalityIsCorrect(long rows, long columns, long cardinality) {
/* 618 */     if (cardinality < 0L) {
/* 619 */       fail("Cardinality should be positive: " + cardinality + ".");
/*     */     }
/*     */     
/* 622 */     long capacity = capacity();
/*     */     
/* 624 */     if (cardinality > capacity)
/* 625 */       fail("Cardinality should be less then or equal to capacity: " + capacity + "."); 
/*     */   }
/*     */   
/*     */   public abstract double getOrElse(int paramInt1, int paramInt2, double paramDouble);
/*     */   
/*     */   public abstract boolean isRowMajor();
/*     */   
/*     */   public abstract boolean nonZeroAt(int paramInt1, int paramInt2);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/matrix/SparseMatrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */