/*     */ package org.la4j.matrix.dense;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import org.la4j.Matrices;
/*     */ import org.la4j.Matrix;
/*     */ import org.la4j.Vector;
/*     */ import org.la4j.matrix.DenseMatrix;
/*     */ import org.la4j.matrix.MatrixFactory;
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
/*     */ public class Basic1DMatrix
/*     */   extends DenseMatrix
/*     */ {
/*     */   private static final byte MATRIX_TAG = 0;
/*     */   private double[] self;
/*     */   
/*     */   public static Basic1DMatrix zero(int rows, int columns) {
/*  44 */     return new Basic1DMatrix(rows, columns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic1DMatrix constant(int rows, int columns, double constant) {
/*  51 */     double[] array = new double[rows * columns];
/*  52 */     Arrays.fill(array, constant);
/*     */     
/*  54 */     return new Basic1DMatrix(rows, columns, array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic1DMatrix diagonal(int size, double diagonal) {
/*  62 */     double[] array = new double[size * size];
/*     */     
/*  64 */     for (int i = 0; i < size; i++) {
/*  65 */       array[i * size + i] = diagonal;
/*     */     }
/*     */     
/*  68 */     return new Basic1DMatrix(size, size, array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic1DMatrix unit(int rows, int columns) {
/*  76 */     return constant(rows, columns, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic1DMatrix identity(int size) {
/*  83 */     return diagonal(size, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic1DMatrix random(int rows, int columns, Random random) {
/*  91 */     double[] array = new double[rows * columns];
/*     */     
/*  93 */     for (int i = 0; i < rows * columns; i++) {
/*  94 */       array[i] = random.nextDouble();
/*     */     }
/*     */     
/*  97 */     return new Basic1DMatrix(rows, columns, array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic1DMatrix randomSymmetric(int size, Random random) {
/* 104 */     double[] array = new double[size * size];
/*     */     
/* 106 */     for (int i = 0; i < size; i++) {
/* 107 */       for (int j = i; j < size; j++) {
/* 108 */         double value = random.nextDouble();
/* 109 */         array[i * size + j] = value;
/* 110 */         array[j * size + i] = value;
/*     */       } 
/*     */     } 
/*     */     
/* 114 */     return new Basic1DMatrix(size, size, array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic1DMatrix from1DArray(int rows, int columns, double[] array) {
/* 122 */     return new Basic1DMatrix(rows, columns, array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic1DMatrix from2DArray(double[][] array) {
/* 130 */     int rows = array.length;
/* 131 */     int columns = (array[0]).length;
/* 132 */     double[] array1D = new double[rows * columns];
/*     */     
/* 134 */     int offset = 0;
/* 135 */     for (int i = 0; i < rows; i++) {
/* 136 */       System.arraycopy(array[i], 0, array1D, offset, columns);
/* 137 */       offset += columns;
/*     */     } 
/*     */     
/* 140 */     return new Basic1DMatrix(rows, columns, array1D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic1DMatrix block(Matrix a, Matrix b, Matrix c, Matrix d) {
/* 148 */     if (a.rows() != b.rows() || a.columns() != c.columns() || c.rows() != d.rows() || b.columns() != d.columns())
/*     */     {
/* 150 */       throw new IllegalArgumentException("Sides of blocks are incompatible!");
/*     */     }
/*     */     
/* 153 */     int rows = a.rows() + c.rows(), columns = a.columns() + b.columns();
/* 154 */     double[] array = new double[rows * columns];
/*     */     
/* 156 */     for (int i = 0; i < rows; i++) {
/* 157 */       for (int j = 0; j < columns; j++) {
/* 158 */         if (i < a.rows() && j < a.columns()) {
/* 159 */           array[i * rows + j] = a.get(i, j);
/*     */         }
/* 161 */         if (i < a.rows() && j > a.columns()) {
/* 162 */           array[i * rows + j] = b.get(i, j);
/*     */         }
/* 164 */         if (i > a.rows() && j < a.columns()) {
/* 165 */           array[i * rows + j] = c.get(i, j);
/*     */         }
/* 167 */         if (i > a.rows() && j > a.columns()) {
/* 168 */           array[i * rows + j] = d.get(i, j);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 173 */     return new Basic1DMatrix(rows, columns, array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic1DMatrix fromBinary(byte[] array) {
/* 184 */     ByteBuffer buffer = ByteBuffer.wrap(array);
/*     */     
/* 186 */     if (buffer.get() != 0) {
/* 187 */       throw new IllegalArgumentException("Can not decode Basic1DMatrix from the given byte array.");
/*     */     }
/*     */     
/* 190 */     int rows = buffer.getInt();
/* 191 */     int columns = buffer.getInt();
/* 192 */     int capacity = rows * columns;
/* 193 */     double[] values = new double[capacity];
/*     */     
/* 195 */     for (int i = 0; i < capacity; i++) {
/* 196 */       values[i] = buffer.getDouble();
/*     */     }
/*     */     
/* 199 */     return new Basic1DMatrix(rows, columns, values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic1DMatrix fromCSV(String csv) {
/* 210 */     return (Basic1DMatrix)Matrix.fromCSV(csv).to(Matrices.BASIC_1D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic1DMatrix fromMatrixMarket(String mm) {
/* 221 */     return (Basic1DMatrix)Matrix.fromMatrixMarket(mm).to(Matrices.BASIC_1D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Basic1DMatrix() {
/* 227 */     this(0, 0);
/*     */   }
/*     */   
/*     */   public Basic1DMatrix(int rows, int columns) {
/* 231 */     this(rows, columns, new double[rows * columns]);
/*     */   }
/*     */   
/*     */   public Basic1DMatrix(int rows, int columns, double[] array) {
/* 235 */     super(rows, columns);
/* 236 */     this.self = array;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int i, int j) {
/* 241 */     ensureIndexesAreInBounds(i, j);
/* 242 */     return this.self[i * this.columns + j];
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int i, int j, double value) {
/* 247 */     ensureIndexesAreInBounds(i, j);
/* 248 */     this.self[i * this.columns + j] = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAll(double value) {
/* 253 */     Arrays.fill(this.self, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void swapRows(int i, int j) {
/* 258 */     if (i != j) {
/* 259 */       for (int k = 0; k < this.columns; k++) {
/* 260 */         double tmp = this.self[i * this.columns + k];
/* 261 */         this.self[i * this.columns + k] = this.self[j * this.columns + k];
/* 262 */         this.self[j * this.columns + k] = tmp;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void swapColumns(int i, int j) {
/* 269 */     if (i != j) {
/* 270 */       for (int k = 0; k < this.rows; k++) {
/* 271 */         double tmp = this.self[k * this.columns + i];
/* 272 */         this.self[k * this.columns + i] = this.self[k * this.columns + j];
/* 273 */         this.self[k * this.columns + j] = tmp;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getRow(int i) {
/* 280 */     double[] result = new double[this.columns];
/* 281 */     System.arraycopy(this.self, i * this.columns, result, 0, this.columns);
/*     */     
/* 283 */     return (Vector)new BasicVector(result);
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix copyOfShape(int rows, int columns) {
/* 288 */     ensureDimensionsAreCorrect(rows, columns);
/*     */     
/* 290 */     if (this.rows < rows && this.columns == columns) {
/* 291 */       double[] arrayOfDouble = new double[rows * columns];
/* 292 */       System.arraycopy(this.self, 0, arrayOfDouble, 0, this.rows * columns);
/*     */       
/* 294 */       return (Matrix)new Basic1DMatrix(rows, columns, arrayOfDouble);
/*     */     } 
/*     */     
/* 297 */     double[] $self = new double[rows * columns];
/*     */     
/* 299 */     int columnSize = (columns < this.columns) ? columns : this.columns;
/* 300 */     int rowSize = (rows < this.rows) ? rows : this.rows;
/*     */     
/* 302 */     for (int i = 0; i < rowSize; i++) {
/* 303 */       System.arraycopy(this.self, i * this.columns, $self, i * columns, columnSize);
/*     */     }
/*     */ 
/*     */     
/* 307 */     return (Matrix)new Basic1DMatrix(rows, columns, $self);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] toArray() {
/* 313 */     double[][] result = new double[this.rows][this.columns];
/*     */     
/* 315 */     int offset = 0;
/* 316 */     for (int i = 0; i < this.rows; i++) {
/* 317 */       System.arraycopy(this.self, offset, result[i], 0, this.columns);
/* 318 */       offset += this.columns;
/*     */     } 
/*     */     
/* 321 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Matrix> T to(MatrixFactory<T> factory) {
/* 326 */     if (factory.outputClass == Basic1DMatrix.class) {
/* 327 */       return (T)factory.outputClass.cast(this);
/*     */     }
/*     */     
/* 330 */     return (T)super.to(factory);
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix blankOfShape(int rows, int columns) {
/* 335 */     return (Matrix)zero(rows, columns);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] toBinary() {
/* 340 */     int size = 9 + 8 * this.rows * this.columns;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 345 */     ByteBuffer buffer = ByteBuffer.allocate(size);
/*     */     
/* 347 */     buffer.put((byte)0);
/* 348 */     buffer.putInt(this.rows);
/* 349 */     buffer.putInt(this.columns);
/* 350 */     for (double value : this.self) {
/* 351 */       buffer.putDouble(value);
/*     */     }
/*     */     
/* 354 */     return buffer.array();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/matrix/dense/Basic1DMatrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */