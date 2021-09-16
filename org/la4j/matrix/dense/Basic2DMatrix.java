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
/*     */ public class Basic2DMatrix
/*     */   extends DenseMatrix
/*     */ {
/*     */   private static final byte MATRIX_TAG = 16;
/*     */   private double[][] self;
/*     */   
/*     */   public static Basic2DMatrix zero(int rows, int columns) {
/*  44 */     return new Basic2DMatrix(rows, columns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic2DMatrix constant(int rows, int columns, double constant) {
/*  51 */     double[][] array = new double[rows][columns];
/*     */     
/*  53 */     for (int i = 0; i < rows; i++) {
/*  54 */       Arrays.fill(array[i], constant);
/*     */     }
/*     */     
/*  57 */     return new Basic2DMatrix(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic2DMatrix diagonal(int size, double diagonal) {
/*  65 */     double[][] array = new double[size][size];
/*     */     
/*  67 */     for (int i = 0; i < size; i++) {
/*  68 */       array[i][i] = diagonal;
/*     */     }
/*     */     
/*  71 */     return new Basic2DMatrix(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic2DMatrix unit(int rows, int columns) {
/*  79 */     return constant(rows, columns, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic2DMatrix identity(int size) {
/*  86 */     return diagonal(size, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic2DMatrix random(int rows, int columns, Random random) {
/*  94 */     double[][] array = new double[rows][columns];
/*     */     
/*  96 */     for (int i = 0; i < rows; i++) {
/*  97 */       for (int j = 0; j < columns; j++) {
/*  98 */         array[i][j] = random.nextDouble();
/*     */       }
/*     */     } 
/*     */     
/* 102 */     return new Basic2DMatrix(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic2DMatrix randomSymmetric(int size, Random random) {
/* 109 */     double[][] array = new double[size][size];
/*     */     
/* 111 */     for (int i = 0; i < size; i++) {
/* 112 */       for (int j = i; j < size; j++) {
/* 113 */         double value = random.nextDouble();
/* 114 */         array[i][j] = value;
/* 115 */         array[j][i] = value;
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     return new Basic2DMatrix(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic2DMatrix from1DArray(int rows, int columns, double[] array) {
/* 127 */     double[][] array2D = new double[rows][columns];
/*     */     
/* 129 */     for (int i = 0; i < rows; i++) {
/* 130 */       System.arraycopy(array, i * columns, array2D[i], 0, columns);
/*     */     }
/*     */     
/* 133 */     return new Basic2DMatrix(array2D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic2DMatrix from2DArray(double[][] array) {
/* 141 */     return new Basic2DMatrix(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic2DMatrix block(Matrix a, Matrix b, Matrix c, Matrix d) {
/* 149 */     if (a.rows() != b.rows() || a.columns() != c.columns() || c.rows() != d.rows() || b.columns() != d.columns())
/*     */     {
/* 151 */       throw new IllegalArgumentException("Sides of blocks are incompatible!");
/*     */     }
/*     */     
/* 154 */     int rows = a.rows() + c.rows(), columns = a.columns() + b.columns();
/* 155 */     double[][] array = new double[rows][columns];
/*     */     
/* 157 */     for (int i = 0; i < rows; i++) {
/* 158 */       for (int j = 0; j < columns; j++) {
/* 159 */         if (i < a.rows() && j < a.columns()) {
/* 160 */           array[i][j] = a.get(i, j);
/*     */         }
/* 162 */         if (i < a.rows() && j > a.columns()) {
/* 163 */           array[i][j] = b.get(i, j);
/*     */         }
/* 165 */         if (i > a.rows() && j < a.columns()) {
/* 166 */           array[i][j] = c.get(i, j);
/*     */         }
/* 168 */         if (i > a.rows() && j > a.columns()) {
/* 169 */           array[i][j] = d.get(i, j);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     return new Basic2DMatrix(array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic2DMatrix fromBinary(byte[] array) {
/* 185 */     ByteBuffer buffer = ByteBuffer.wrap(array);
/*     */     
/* 187 */     if (buffer.get() != 16) {
/* 188 */       throw new IllegalArgumentException("Can not decode Basic2DMatrix from the given byte array.");
/*     */     }
/*     */     
/* 191 */     int rows = buffer.getInt();
/* 192 */     int columns = buffer.getInt();
/* 193 */     double[][] values = new double[rows][columns];
/*     */     
/* 195 */     for (int i = 0; i < rows; i++) {
/* 196 */       for (int j = 0; j < columns; j++) {
/* 197 */         values[i][j] = buffer.getDouble();
/*     */       }
/*     */     } 
/*     */     
/* 201 */     return new Basic2DMatrix(values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic2DMatrix fromCSV(String csv) {
/* 212 */     return (Basic2DMatrix)Matrix.fromCSV(csv).to(Matrices.BASIC_2D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Basic2DMatrix fromMatrixMarket(String mm) {
/* 223 */     return (Basic2DMatrix)Matrix.fromMatrixMarket(mm).to(Matrices.BASIC_2D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Basic2DMatrix() {
/* 229 */     this(0, 0);
/*     */   }
/*     */   
/*     */   public Basic2DMatrix(int rows, int columns) {
/* 233 */     this(new double[rows][columns]);
/*     */   }
/*     */   
/*     */   public Basic2DMatrix(double[][] array) {
/* 237 */     super(array.length, (array.length == 0) ? 0 : (array[0]).length);
/* 238 */     this.self = array;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int i, int j) {
/* 243 */     return this.self[i][j];
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int i, int j, double value) {
/* 248 */     this.self[i][j] = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAll(double value) {
/* 253 */     for (int i = 0; i < this.rows; i++) {
/* 254 */       Arrays.fill(this.self[i], value);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void swapRows(int i, int j) {
/* 260 */     if (i != j) {
/* 261 */       double[] tmp = this.self[i];
/* 262 */       this.self[i] = this.self[j];
/* 263 */       this.self[j] = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void swapColumns(int i, int j) {
/* 269 */     if (i != j) {
/* 270 */       for (int ii = 0; ii < this.rows; ii++) {
/* 271 */         double tmp = this.self[ii][i];
/* 272 */         this.self[ii][i] = this.self[ii][j];
/* 273 */         this.self[ii][j] = tmp;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getRow(int i) {
/* 280 */     double[] result = new double[this.columns];
/* 281 */     System.arraycopy(this.self[i], 0, result, 0, this.columns);
/*     */     
/* 283 */     return (Vector)new BasicVector(result);
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix copyOfShape(int rows, int columns) {
/* 288 */     ensureDimensionsAreCorrect(rows, columns);
/*     */     
/* 290 */     double[][] $self = new double[rows][columns];
/* 291 */     for (int i = 0; i < Math.min(this.rows, rows); i++) {
/* 292 */       System.arraycopy(this.self[i], 0, $self[i], 0, Math.min(this.columns, columns));
/*     */     }
/*     */     
/* 295 */     return (Matrix)new Basic2DMatrix($self);
/*     */   }
/*     */ 
/*     */   
/*     */   public double[][] toArray() {
/* 300 */     double[][] result = new double[this.rows][this.columns];
/*     */     
/* 302 */     for (int i = 0; i < this.rows; i++) {
/* 303 */       System.arraycopy(this.self[i], 0, result[i], 0, this.columns);
/*     */     }
/*     */     
/* 306 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Matrix> T to(MatrixFactory<T> factory) {
/* 311 */     if (factory.outputClass == Basic2DMatrix.class) {
/* 312 */       return (T)factory.outputClass.cast(this);
/*     */     }
/*     */     
/* 315 */     return (T)super.to(factory);
/*     */   }
/*     */ 
/*     */   
/*     */   public Matrix blankOfShape(int rows, int columns) {
/* 320 */     return (Matrix)zero(rows, columns);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] toBinary() {
/* 325 */     int size = 9 + 8 * this.rows * this.columns;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     ByteBuffer buffer = ByteBuffer.allocate(size);
/*     */     
/* 332 */     buffer.put((byte)16);
/* 333 */     buffer.putInt(this.rows);
/* 334 */     buffer.putInt(this.columns);
/* 335 */     for (int i = 0; i < this.rows; i++) {
/* 336 */       for (double value : this.self[i]) {
/* 337 */         buffer.putDouble(value);
/*     */       }
/*     */     } 
/*     */     
/* 341 */     return buffer.array();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/matrix/dense/Basic2DMatrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */