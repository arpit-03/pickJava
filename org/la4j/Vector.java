/*     */ package org.la4j;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.StringTokenizer;
/*     */ import org.la4j.iterator.VectorIterator;
/*     */ import org.la4j.operation.VectorMatrixOperation;
/*     */ import org.la4j.operation.VectorOperation;
/*     */ import org.la4j.operation.VectorVectorOperation;
/*     */ import org.la4j.vector.DenseVector;
/*     */ import org.la4j.vector.SparseVector;
/*     */ import org.la4j.vector.VectorFactory;
/*     */ import org.la4j.vector.functor.VectorAccumulator;
/*     */ import org.la4j.vector.functor.VectorFunction;
/*     */ import org.la4j.vector.functor.VectorPredicate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Vector
/*     */   implements Iterable<Double>
/*     */ {
/*     */   private static final String DEFAULT_DELIMITER = " ";
/*  53 */   private static final NumberFormat DEFAULT_FORMATTER = new DecimalFormat("0.000");
/*     */   
/*     */   protected int length;
/*     */ 
/*     */   
/*     */   public static Vector zero(int length) {
/*  59 */     return (length > 1000) ? (Vector)SparseVector.zero(length) : (Vector)DenseVector.zero(length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector constant(int length, double value) {
/*  67 */     return (Vector)DenseVector.constant(length, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector unit(int length) {
/*  74 */     return (Vector)DenseVector.constant(length, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector random(int length, Random random) {
/*  82 */     return (Vector)DenseVector.random(length, random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector fromArray(double[] array) {
/*  90 */     return (Vector)DenseVector.fromArray(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector fromCSV(String csv) {
/*     */     Vector vector;
/* 101 */     StringTokenizer tokenizer = new StringTokenizer(csv, ", ");
/* 102 */     int estimatedLength = csv.length() / 7 + 1;
/*     */     
/* 104 */     DenseVector denseVector = DenseVector.zero(estimatedLength);
/*     */     
/* 106 */     int i = 0;
/* 107 */     while (tokenizer.hasMoreTokens()) {
/* 108 */       if (denseVector.length() == i) {
/* 109 */         vector = denseVector.copyOfLength(i * 3 / 2 + 1);
/*     */       }
/*     */       
/* 112 */       double x = Double.valueOf(tokenizer.nextToken()).doubleValue();
/* 113 */       vector.set(i++, x);
/*     */     } 
/*     */     
/* 116 */     return vector.copyOfLength(i);
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
/*     */   public static Vector fromMatrixMarket(String mm) {
/* 128 */     StringTokenizer body = new StringTokenizer(mm);
/*     */     
/* 130 */     if (!"%%MatrixMarket".equals(body.nextToken())) {
/* 131 */       throw new IllegalArgumentException("Wrong input file format: can not read header '%%MatrixMarket'.");
/*     */     }
/*     */     
/* 134 */     String object = body.nextToken();
/* 135 */     if (!"vector".equals(object)) {
/* 136 */       throw new IllegalArgumentException("Unexpected object: " + object + ".");
/*     */     }
/*     */     
/* 139 */     String format = body.nextToken();
/* 140 */     if (!"coordinate".equals(format) && !"array".equals(format)) {
/* 141 */       throw new IllegalArgumentException("Unknown format: " + format + ".");
/*     */     }
/*     */     
/* 144 */     String field = body.nextToken();
/* 145 */     if (!"real".equals(field)) {
/* 146 */       throw new IllegalArgumentException("Unknown field type: " + field + ".");
/*     */     }
/*     */     
/* 149 */     int length = Integer.valueOf(body.nextToken()).intValue();
/* 150 */     if ("coordinate".equals(format)) {
/* 151 */       int cardinality = Integer.valueOf(body.nextToken()).intValue();
/* 152 */       SparseVector sparseVector = SparseVector.zero(length, cardinality);
/*     */       
/* 154 */       for (int k = 0; k < cardinality; k++) {
/* 155 */         int j = Integer.valueOf(body.nextToken()).intValue();
/* 156 */         double x = Double.valueOf(body.nextToken()).doubleValue();
/* 157 */         sparseVector.set(j - 1, x);
/*     */       } 
/*     */       
/* 160 */       return (Vector)sparseVector;
/*     */     } 
/* 162 */     DenseVector denseVector = DenseVector.zero(length);
/*     */     
/* 164 */     for (int i = 0; i < length; i++) {
/* 165 */       denseVector.set(i, Double.valueOf(body.nextToken()).doubleValue());
/*     */     }
/*     */     
/* 168 */     return (Vector)denseVector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector fromCollection(Collection<? extends Number> list) {
/* 176 */     return (Vector)DenseVector.fromCollection(list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector fromMap(Map<Integer, ? extends Number> map, int length) {
/* 183 */     return (Vector)SparseVector.fromMap(map, length);
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
/*     */   public Vector() {
/* 195 */     this(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector(int length) {
/* 204 */     ensureLengthIsCorrect(length);
/* 205 */     this.length = length;
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
/*     */   public void setAll(double value) {
/* 330 */     VectorIterator it = iterator();
/*     */     
/* 332 */     while (it.hasNext()) {
/* 333 */       it.next();
/* 334 */       it.set(value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/* 344 */     return this.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector add(double value) {
/* 355 */     VectorIterator it = iterator();
/* 356 */     Vector result = blank();
/*     */     
/* 358 */     while (it.hasNext()) {
/* 359 */       double x = ((Double)it.next()).doubleValue();
/* 360 */       int i = it.index();
/* 361 */       result.set(i, x + value);
/*     */     } 
/*     */     
/* 364 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector add(Vector that) {
/* 375 */     return apply(LinearAlgebra.OO_PLACE_VECTORS_ADDITION, that);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector multiply(double value) {
/* 386 */     VectorIterator it = iterator();
/* 387 */     Vector result = blank();
/*     */     
/* 389 */     while (it.hasNext()) {
/* 390 */       double x = ((Double)it.next()).doubleValue();
/* 391 */       int i = it.index();
/* 392 */       result.set(i, x * value);
/*     */     } 
/*     */     
/* 395 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector hadamardProduct(Vector that) {
/* 406 */     return apply(LinearAlgebra.OO_PLACE_VECTOR_HADAMARD_PRODUCT, that);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector multiply(Matrix that) {
/* 417 */     return apply(LinearAlgebra.OO_PLACE_VECTOR_BY_MATRIX_MULTIPLICATION, that);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector subtract(double value) {
/* 428 */     return add(-value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector subtract(Vector that) {
/* 439 */     return apply(LinearAlgebra.OO_PLACE_VECTORS_SUBTRACTION, that);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector divide(double value) {
/* 450 */     return multiply(1.0D / value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double product() {
/* 459 */     return fold(Vectors.asProductAccumulator(1.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double sum() {
/* 468 */     return fold(Vectors.asSumAccumulator(0.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double innerProduct(Vector that) {
/* 479 */     return ((Double)apply(LinearAlgebra.OO_PLACE_INNER_PRODUCT, that)).doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix outerProduct(Vector that) {
/* 490 */     return apply(LinearAlgebra.OO_PLACE_OUTER_PRODUCT, that);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double norm() {
/* 499 */     return euclideanNorm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double euclideanNorm() {
/* 508 */     return fold(Vectors.mkEuclideanNormAccumulator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double manhattanNorm() {
/* 517 */     return fold(Vectors.mkManhattanNormAccumulator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double infinityNorm() {
/* 526 */     return fold(Vectors.mkInfinityNormAccumulator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void swapElements(int i, int j) {
/* 536 */     if (i != j) {
/* 537 */       double s = get(i);
/* 538 */       set(i, get(j));
/* 539 */       set(j, s);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector blank() {
/* 549 */     return blankOfLength(this.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector copy() {
/* 558 */     return copyOfLength(this.length);
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
/*     */   public Vector shuffle() {
/* 573 */     Vector result = copy();
/*     */ 
/*     */     
/* 576 */     Random random = new Random();
/*     */     
/* 578 */     for (int i = 0; i < this.length; i++) {
/* 579 */       int j = random.nextInt(this.length - i) + i;
/* 580 */       swapElements(i, j);
/*     */     } 
/*     */     
/* 583 */     return result;
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
/*     */   public Vector slice(int from, int until) {
/* 596 */     if (until - from < 0) {
/* 597 */       fail("Wrong slice range: [" + from + ".." + until + "].");
/*     */     }
/*     */     
/* 600 */     Vector result = blankOfLength(until - from);
/*     */     
/* 602 */     for (int i = from; i < until; i++) {
/* 603 */       result.set(i - from, get(i));
/*     */     }
/*     */     
/* 606 */     return result;
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
/*     */   public Vector sliceLeft(int until) {
/* 618 */     return slice(0, until);
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
/*     */   public Vector sliceRight(int from) {
/* 630 */     return slice(from, this.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector select(int[] indices) {
/* 641 */     int newLength = indices.length;
/*     */     
/* 643 */     if (newLength == 0) {
/* 644 */       fail("No elements selected.");
/*     */     }
/*     */     
/* 647 */     Vector result = blankOfLength(newLength);
/*     */     
/* 649 */     for (int i = 0; i < newLength; i++) {
/* 650 */       result.set(i, get(indices[i]));
/*     */     }
/*     */     
/* 653 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void each(VectorProcedure procedure) {
/* 662 */     VectorIterator it = iterator();
/*     */     
/* 664 */     while (it.hasNext()) {
/* 665 */       double x = ((Double)it.next()).doubleValue();
/* 666 */       int i = it.index();
/* 667 */       procedure.apply(i, x);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double max() {
/* 677 */     return fold(Vectors.mkMaxAccumulator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double min() {
/* 686 */     return fold(Vectors.mkMinAccumulator());
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
/*     */   public Vector transform(VectorFunction function) {
/* 698 */     VectorIterator it = iterator();
/* 699 */     Vector result = blank();
/*     */     
/* 701 */     while (it.hasNext()) {
/* 702 */       double x = ((Double)it.next()).doubleValue();
/* 703 */       int i = it.index();
/* 704 */       result.set(i, function.evaluate(i, x));
/*     */     } 
/*     */     
/* 707 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(VectorFunction function) {
/* 716 */     VectorIterator it = iterator();
/*     */     
/* 718 */     while (it.hasNext()) {
/* 719 */       double x = ((Double)it.next()).doubleValue();
/* 720 */       int i = it.index();
/* 721 */       it.set(function.evaluate(i, x));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateAt(int i, VectorFunction function) {
/* 732 */     set(i, function.evaluate(i, get(i)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double fold(VectorAccumulator accumulator) {
/* 743 */     each(Vectors.asAccumulatorProcedure(accumulator));
/* 744 */     return accumulator.accumulate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean is(VectorPredicate predicate) {
/* 755 */     boolean result = true;
/* 756 */     VectorIterator it = iterator();
/*     */     
/* 758 */     while (it.hasNext()) {
/* 759 */       double x = ((Double)it.next()).doubleValue();
/* 760 */       int i = it.index();
/* 761 */       result = (result && predicate.test(i, x));
/*     */     } 
/*     */     
/* 764 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean non(VectorPredicate predicate) {
/* 775 */     return !is(predicate);
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
/*     */   public boolean equals(Vector that, double precision) {
/* 788 */     if (this == that) {
/* 789 */       return true;
/*     */     }
/*     */     
/* 792 */     if (this.length != that.length()) {
/* 793 */       return false;
/*     */     }
/*     */     
/* 796 */     boolean result = true;
/*     */     
/* 798 */     for (int i = 0; result && i < this.length; i++) {
/* 799 */       double a = get(i);
/* 800 */       double b = that.get(i);
/* 801 */       double diff = Math.abs(a - b);
/* 802 */       result = (a == b || diff < precision || diff / Math.max(Math.abs(a), Math.abs(b)) < precision);
/*     */     } 
/*     */ 
/*     */     
/* 806 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String mkString(NumberFormat formatter) {
/* 817 */     return mkString(formatter, " ");
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
/*     */   public String mkString(NumberFormat formatter, String delimiter) {
/* 829 */     StringBuilder sb = new StringBuilder();
/* 830 */     VectorIterator it = iterator();
/*     */     
/* 832 */     while (it.hasNext()) {
/* 833 */       double x = ((Double)it.next()).doubleValue();
/* 834 */       int i = it.index();
/* 835 */       sb.append(formatter.format(x)).append((i < this.length - 1) ? delimiter : "");
/*     */     } 
/*     */ 
/*     */     
/* 839 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 849 */     return mkString(DEFAULT_FORMATTER, " ");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 857 */     return (o != null && o instanceof Vector && equals((Vector)o, Vectors.EPS));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 865 */     VectorIterator it = iterator();
/* 866 */     int result = 17;
/*     */     
/* 868 */     while (it.hasNext()) {
/* 869 */       long value = ((Double)it.next()).longValue();
/* 870 */       result = 37 * result + (int)(value ^ value >>> 32L);
/*     */     } 
/*     */     
/* 873 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VectorIterator iterator() {
/* 883 */     return new VectorIterator(this.length) {
/* 884 */         private int i = -1;
/*     */ 
/*     */         
/*     */         public int index() {
/* 888 */           return this.i;
/*     */         }
/*     */ 
/*     */         
/*     */         public double get() {
/* 893 */           return Vector.this.get(this.i);
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(double value) {
/* 898 */           Vector.this.set(this.i, value);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 903 */           return (this.i + 1 < this.length);
/*     */         }
/*     */ 
/*     */         
/*     */         public Double next() {
/* 908 */           this.i++;
/* 909 */           return Double.valueOf(get());
/*     */         }
/*     */       };
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
/*     */   public <T extends Vector> T to(VectorFactory<T> factory) {
/* 923 */     VectorIterator it = iterator();
/* 924 */     Vector vector = factory.apply(this.length);
/*     */     
/* 926 */     while (it.hasNext()) {
/* 927 */       double x = ((Double)it.next()).doubleValue();
/* 928 */       int i = it.index();
/* 929 */       vector.set(i, x);
/*     */     } 
/*     */     
/* 932 */     return (T)vector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseVector toDenseVector() {
/* 941 */     return (DenseVector)to(Vectors.DENSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseVector toSparseVector() {
/* 950 */     return (SparseVector)to(Vectors.SPARSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toCSV() {
/* 959 */     return toCSV(DEFAULT_FORMATTER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toCSV(NumberFormat formatter) {
/* 969 */     return mkString(formatter, ", ");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toMatrixMarket() {
/* 978 */     return toMatrixMarket(DEFAULT_FORMATTER);
/*     */   }
/*     */   
/*     */   protected void ensureLengthIsCorrect(int length) {
/* 982 */     if (length < 0) {
/* 983 */       fail("Wrong vector length: " + length);
/*     */     }
/* 985 */     if (length == Integer.MAX_VALUE) {
/* 986 */       fail("Wrong vector length: use 'Integer.MAX_VALUE - 1' instead.");
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fail(String message) {
/* 991 */     throw new IllegalArgumentException(message);
/*     */   }
/*     */   
/*     */   public abstract double get(int paramInt);
/*     */   
/*     */   public abstract void set(int paramInt, double paramDouble);
/*     */   
/*     */   public abstract Vector blankOfLength(int paramInt);
/*     */   
/*     */   public abstract Vector copyOfLength(int paramInt);
/*     */   
/*     */   public abstract Matrix toRowMatrix();
/*     */   
/*     */   public abstract Matrix toColumnMatrix();
/*     */   
/*     */   public abstract Matrix toDiagonalMatrix();
/*     */   
/*     */   public abstract <T> T apply(VectorOperation<T> paramVectorOperation);
/*     */   
/*     */   public abstract <T> T apply(VectorVectorOperation<T> paramVectorVectorOperation, Vector paramVector);
/*     */   
/*     */   public abstract <T> T apply(VectorMatrixOperation<T> paramVectorMatrixOperation, Matrix paramMatrix);
/*     */   
/*     */   public abstract byte[] toBinary();
/*     */   
/*     */   public abstract String toMatrixMarket(NumberFormat paramNumberFormat);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/Vector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */