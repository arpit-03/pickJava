/*     */ package org.la4j.vector;
/*     */ 
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.Vector;
/*     */ import org.la4j.Vectors;
/*     */ import org.la4j.iterator.VectorIterator;
/*     */ import org.la4j.matrix.ColumnMajorSparseMatrix;
/*     */ import org.la4j.matrix.RowMajorSparseMatrix;
/*     */ import org.la4j.matrix.sparse.CRSMatrix;
/*     */ import org.la4j.operation.VectorMatrixOperation;
/*     */ import org.la4j.operation.VectorOperation;
/*     */ import org.la4j.operation.VectorVectorOperation;
/*     */ import org.la4j.vector.functor.VectorAccumulator;
/*     */ import org.la4j.vector.functor.VectorProcedure;
/*     */ import org.la4j.vector.sparse.CompressedVector;
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
/*     */ public abstract class SparseVector
/*     */   extends Vector
/*     */ {
/*     */   protected int cardinality;
/*     */   
/*     */   public static SparseVector zero(int length) {
/*  62 */     return (SparseVector)CompressedVector.zero(length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseVector zero(int length, int capacity) {
/*  70 */     return (SparseVector)CompressedVector.zero(length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseVector random(int length, double density, Random random) {
/*  78 */     return (SparseVector)CompressedVector.random(length, density, random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseVector fromArray(double[] array) {
/*  86 */     return (SparseVector)CompressedVector.fromArray(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseVector fromCSV(String csv) {
/*  97 */     return (SparseVector)Vector.fromCSV(csv).to(Vectors.SPARSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseVector fromMatrixMarket(String mm) {
/* 108 */     return (SparseVector)Vector.fromMatrixMarket(mm).to(Vectors.SPARSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseVector fromCollection(Collection<? extends Number> list) {
/* 119 */     return (SparseVector)Vector.fromCollection(list).to(Vectors.SPARSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseVector fromMap(Map<Integer, ? extends Number> map, int length) {
/* 130 */     return (SparseVector)CompressedVector.fromMap(map, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseVector(int length) {
/* 136 */     this(length, 0);
/*     */   }
/*     */   
/*     */   public SparseVector(int length, int cardinality) {
/* 140 */     super(length);
/* 141 */     this.cardinality = cardinality;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int cardinality() {
/* 151 */     return this.cardinality;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double density() {
/* 161 */     return this.cardinality / this.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int i) {
/* 166 */     return getOrElse(i, 0.0D);
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
/*     */   public abstract double getOrElse(int paramInt, double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isZeroAt(int i) {
/* 188 */     return !nonZeroAt(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean nonZeroAt(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double foldNonZero(VectorAccumulator accumulator) {
/* 208 */     eachNonZero(Vectors.asAccumulatorProcedure(accumulator));
/* 209 */     return accumulator.accumulate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void eachNonZero(VectorProcedure procedure) {
/* 218 */     VectorIterator it = nonZeroIterator();
/*     */     
/* 220 */     while (it.hasNext()) {
/* 221 */       double x = ((Double)it.next()).doubleValue();
/* 222 */       int i = it.index();
/* 223 */       procedure.apply(i, x);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector add(double value) {
/* 229 */     Vector result = DenseVector.constant(this.length, value);
/* 230 */     VectorIterator it = nonZeroIterator();
/*     */     
/* 232 */     while (it.hasNext()) {
/* 233 */       double x = ((Double)it.next()).doubleValue();
/* 234 */       int i = it.index();
/* 235 */       result.set(i, x + value);
/*     */     } 
/*     */     
/* 238 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector multiply(double value) {
/* 243 */     Vector result = blank();
/* 244 */     VectorIterator it = nonZeroIterator();
/*     */     
/* 246 */     while (it.hasNext()) {
/* 247 */       double x = ((Double)it.next()).doubleValue();
/* 248 */       int i = it.index();
/* 249 */       result.set(i, x * value);
/*     */     } 
/*     */     
/* 252 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public double max() {
/* 257 */     double max = foldNonZero(Vectors.mkMaxAccumulator());
/* 258 */     return (max > 0.0D) ? max : 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double min() {
/* 263 */     double min = foldNonZero(Vectors.mkMinAccumulator());
/* 264 */     return (min < 0.0D) ? min : 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double euclideanNorm() {
/* 269 */     return foldNonZero(Vectors.mkEuclideanNormAccumulator());
/*     */   }
/*     */ 
/*     */   
/*     */   public double manhattanNorm() {
/* 274 */     return foldNonZero(Vectors.mkManhattanNormAccumulator());
/*     */   }
/*     */ 
/*     */   
/*     */   public double infinityNorm() {
/* 279 */     double norm = foldNonZero(Vectors.mkInfinityNormAccumulator());
/* 280 */     return (norm > 0.0D) ? norm : 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract VectorIterator nonZeroIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Vector> T to(VectorFactory<T> factory) {
/* 292 */     T result = factory.apply(this.length);
/* 293 */     VectorIterator it = nonZeroIterator();
/*     */     
/* 295 */     while (it.hasNext()) {
/* 296 */       double x = ((Double)it.next()).doubleValue();
/* 297 */       int i = it.index();
/* 298 */       result.set(i, x);
/*     */     } 
/*     */     
/* 301 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 306 */     int result = 17;
/* 307 */     VectorIterator it = nonZeroIterator();
/*     */     
/* 309 */     while (it.hasNext()) {
/* 310 */       long x = ((Double)it.next()).longValue();
/* 311 */       long i = it.index();
/* 312 */       result = 37 * result + (int)(x ^ x >>> 32L);
/* 313 */       result = 37 * result + (int)(i ^ i >>> 32L);
/*     */     } 
/*     */     
/* 316 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T apply(VectorOperation<T> operation) {
/* 321 */     operation.ensureApplicableTo(this);
/* 322 */     return (T)operation.apply(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T apply(VectorVectorOperation<T> operation, Vector that) {
/* 327 */     return (T)that.apply(operation.partiallyApply(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T apply(VectorMatrixOperation<T> operation, Matrix that) {
/* 332 */     return (T)that.apply(operation.partiallyApply(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix toRowMatrix() {
/* 337 */     VectorIterator it = nonZeroIterator();
/* 338 */     CRSMatrix cRSMatrix = CRSMatrix.zero(1, this.length);
/*     */     
/* 340 */     while (it.hasNext()) {
/* 341 */       double x = ((Double)it.next()).doubleValue();
/* 342 */       int j = it.index();
/* 343 */       cRSMatrix.set(0, j, x);
/*     */     } 
/*     */     
/* 346 */     return (Matrix)cRSMatrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix toColumnMatrix() {
/* 351 */     VectorIterator it = nonZeroIterator();
/* 352 */     ColumnMajorSparseMatrix columnMajorSparseMatrix = ColumnMajorSparseMatrix.zero(this.length, 1);
/*     */     
/* 354 */     while (it.hasNext()) {
/* 355 */       double x = ((Double)it.next()).doubleValue();
/* 356 */       int i = it.index();
/* 357 */       columnMajorSparseMatrix.set(i, 0, x);
/*     */     } 
/*     */     
/* 360 */     return (Matrix)columnMajorSparseMatrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix toDiagonalMatrix() {
/* 365 */     VectorIterator it = nonZeroIterator();
/* 366 */     RowMajorSparseMatrix rowMajorSparseMatrix = RowMajorSparseMatrix.zero(this.length, this.length);
/*     */     
/* 368 */     while (it.hasNext()) {
/* 369 */       double x = ((Double)it.next()).doubleValue();
/* 370 */       int i = it.index();
/* 371 */       rowMajorSparseMatrix.set(i, i, x);
/*     */     } 
/*     */     
/* 374 */     return (Matrix)rowMajorSparseMatrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toMatrixMarket(NumberFormat formatter) {
/* 379 */     StringBuilder out = new StringBuilder();
/* 380 */     VectorIterator it = nonZeroIterator();
/*     */     
/* 382 */     out.append("%%MatrixMarket vector coordinate real\n");
/* 383 */     out.append(this.length).append(' ').append(this.cardinality).append('\n');
/* 384 */     while (it.hasNext()) {
/* 385 */       double x = ((Double)it.next()).doubleValue();
/* 386 */       int i = it.index();
/* 387 */       out.append(i + 1).append(' ').append(formatter.format(x)).append('\n');
/*     */     } 
/*     */     
/* 390 */     return out.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void ensureIndexIsInBounds(int i) {
/* 399 */     if (i < 0 || i >= this.length)
/* 400 */       throw new IndexOutOfBoundsException("Index '" + i + "' is invalid."); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/vector/SparseVector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */