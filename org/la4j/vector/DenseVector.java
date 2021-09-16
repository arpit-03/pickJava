/*     */ package org.la4j.vector;
/*     */ 
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.Vector;
/*     */ import org.la4j.Vectors;
/*     */ import org.la4j.matrix.dense.Basic2DMatrix;
/*     */ import org.la4j.operation.VectorMatrixOperation;
/*     */ import org.la4j.operation.VectorOperation;
/*     */ import org.la4j.operation.VectorVectorOperation;
/*     */ import org.la4j.vector.dense.BasicVector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DenseVector
/*     */   extends Vector
/*     */ {
/*     */   public static DenseVector zero(int length) {
/*  57 */     return (DenseVector)BasicVector.zero(length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseVector constant(int length, double value) {
/*  65 */     return (DenseVector)BasicVector.constant(length, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseVector unit(int length) {
/*  72 */     return constant(length, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseVector random(int length, Random random) {
/*  80 */     return (DenseVector)BasicVector.random(length, random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseVector fromArray(double[] array) {
/*  88 */     return (DenseVector)BasicVector.fromArray(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseVector fromCSV(String csv) {
/*  99 */     return (DenseVector)Vector.fromCSV(csv).to(Vectors.DENSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseVector fromMatrixMarket(String mm) {
/* 110 */     return (DenseVector)Vector.fromMatrixMarket(mm).to(Vectors.DENSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseVector fromCollection(Collection<? extends Number> list) {
/* 121 */     return (DenseVector)BasicVector.fromCollection(list);
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
/*     */   public static DenseVector fromMap(Map<Integer, ? extends Number> map, int length) {
/* 134 */     return (DenseVector)Vector.fromMap(map, length).to(Vectors.DENSE);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T apply(VectorOperation<T> operation) {
/* 139 */     operation.ensureApplicableTo(this);
/* 140 */     return (T)operation.apply(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T apply(VectorVectorOperation<T> operation, Vector that) {
/* 145 */     return (T)that.apply(operation.partiallyApply(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T apply(VectorMatrixOperation<T> operation, Matrix that) {
/* 150 */     return (T)that.apply(operation.partiallyApply(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract double[] toArray();
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseVector(int length) {
/* 161 */     super(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix toRowMatrix() {
/* 166 */     Basic2DMatrix basic2DMatrix = Basic2DMatrix.zero(1, this.length);
/*     */     
/* 168 */     for (int j = 0; j < this.length; j++) {
/* 169 */       basic2DMatrix.set(0, j, get(j));
/*     */     }
/*     */     
/* 172 */     return (Matrix)basic2DMatrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix toColumnMatrix() {
/* 177 */     Basic2DMatrix basic2DMatrix = Basic2DMatrix.zero(this.length, 1);
/*     */     
/* 179 */     for (int i = 0; i < this.length; i++) {
/* 180 */       basic2DMatrix.set(i, 0, get(i));
/*     */     }
/*     */     
/* 183 */     return (Matrix)basic2DMatrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix toDiagonalMatrix() {
/* 188 */     Basic2DMatrix basic2DMatrix = Basic2DMatrix.zero(this.length, this.length);
/*     */     
/* 190 */     for (int i = 0; i < this.length; i++) {
/* 191 */       basic2DMatrix.set(i, i, get(i));
/*     */     }
/*     */     
/* 194 */     return (Matrix)basic2DMatrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toMatrixMarket(NumberFormat formatter) {
/* 199 */     StringBuilder out = new StringBuilder();
/*     */     
/* 201 */     out.append("%%MatrixMarket vector array real\n");
/* 202 */     out.append(this.length).append('\n');
/* 203 */     for (int i = 0; i < this.length; i++) {
/* 204 */       out.append(formatter.format(get(i))).append('\n');
/*     */     }
/*     */     
/* 207 */     return out.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/vector/DenseVector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */