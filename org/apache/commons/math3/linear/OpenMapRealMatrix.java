/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.util.OpenIntToDoubleHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OpenMapRealMatrix
/*     */   extends AbstractRealMatrix
/*     */   implements SparseRealMatrix, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5962461716457143437L;
/*     */   private final int rows;
/*     */   private final int columns;
/*     */   private final OpenIntToDoubleHashMap entries;
/*     */   
/*     */   public OpenMapRealMatrix(int rowDimension, int columnDimension) throws NotStrictlyPositiveException, NumberIsTooLargeException {
/*  63 */     super(rowDimension, columnDimension);
/*  64 */     long lRow = rowDimension;
/*  65 */     long lCol = columnDimension;
/*  66 */     if (lRow * lCol >= 2147483647L) {
/*  67 */       throw new NumberIsTooLargeException(Long.valueOf(lRow * lCol), Integer.valueOf(2147483647), false);
/*     */     }
/*  69 */     this.rows = rowDimension;
/*  70 */     this.columns = columnDimension;
/*  71 */     this.entries = new OpenIntToDoubleHashMap(0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealMatrix(OpenMapRealMatrix matrix) {
/*  80 */     this.rows = matrix.rows;
/*  81 */     this.columns = matrix.columns;
/*  82 */     this.entries = new OpenIntToDoubleHashMap(matrix.entries);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealMatrix copy() {
/*  88 */     return new OpenMapRealMatrix(this);
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
/*     */   public OpenMapRealMatrix createMatrix(int rowDimension, int columnDimension) throws NotStrictlyPositiveException, NumberIsTooLargeException {
/* 100 */     return new OpenMapRealMatrix(rowDimension, columnDimension);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnDimension() {
/* 106 */     return this.columns;
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
/*     */   public OpenMapRealMatrix add(OpenMapRealMatrix m) throws MatrixDimensionMismatchException {
/* 120 */     MatrixUtils.checkAdditionCompatible(this, m);
/*     */     
/* 122 */     OpenMapRealMatrix out = new OpenMapRealMatrix(this);
/* 123 */     for (OpenIntToDoubleHashMap.Iterator iterator = m.entries.iterator(); iterator.hasNext(); ) {
/* 124 */       iterator.advance();
/* 125 */       int row = iterator.key() / this.columns;
/* 126 */       int col = iterator.key() - row * this.columns;
/* 127 */       out.setEntry(row, col, getEntry(row, col) + iterator.value());
/*     */     } 
/*     */     
/* 130 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealMatrix subtract(RealMatrix m) throws MatrixDimensionMismatchException {
/*     */     try {
/* 139 */       return subtract((OpenMapRealMatrix)m);
/* 140 */     } catch (ClassCastException cce) {
/* 141 */       return (OpenMapRealMatrix)super.subtract(m);
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
/*     */   public OpenMapRealMatrix subtract(OpenMapRealMatrix m) throws MatrixDimensionMismatchException {
/* 155 */     MatrixUtils.checkAdditionCompatible(this, m);
/*     */     
/* 157 */     OpenMapRealMatrix out = new OpenMapRealMatrix(this);
/* 158 */     for (OpenIntToDoubleHashMap.Iterator iterator = m.entries.iterator(); iterator.hasNext(); ) {
/* 159 */       iterator.advance();
/* 160 */       int row = iterator.key() / this.columns;
/* 161 */       int col = iterator.key() - row * this.columns;
/* 162 */       out.setEntry(row, col, getEntry(row, col) - iterator.value());
/*     */     } 
/*     */     
/* 165 */     return out;
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
/*     */   public RealMatrix multiply(RealMatrix m) throws DimensionMismatchException, NumberIsTooLargeException {
/*     */     try {
/* 179 */       return multiply((OpenMapRealMatrix)m);
/* 180 */     } catch (ClassCastException cce) {
/*     */       
/* 182 */       MatrixUtils.checkMultiplicationCompatible(this, m);
/*     */       
/* 184 */       int outCols = m.getColumnDimension();
/* 185 */       BlockRealMatrix out = new BlockRealMatrix(this.rows, outCols);
/* 186 */       for (OpenIntToDoubleHashMap.Iterator iterator = this.entries.iterator(); iterator.hasNext(); ) {
/* 187 */         iterator.advance();
/* 188 */         double value = iterator.value();
/* 189 */         int key = iterator.key();
/* 190 */         int i = key / this.columns;
/* 191 */         int k = key % this.columns;
/* 192 */         for (int j = 0; j < outCols; j++) {
/* 193 */           out.addToEntry(i, j, value * m.getEntry(k, j));
/*     */         }
/*     */       } 
/*     */       
/* 197 */       return out;
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
/*     */ 
/*     */   
/*     */   public OpenMapRealMatrix multiply(OpenMapRealMatrix m) throws DimensionMismatchException, NumberIsTooLargeException {
/* 214 */     MatrixUtils.checkMultiplicationCompatible(this, m);
/*     */     
/* 216 */     int outCols = m.getColumnDimension();
/* 217 */     OpenMapRealMatrix out = new OpenMapRealMatrix(this.rows, outCols);
/* 218 */     for (OpenIntToDoubleHashMap.Iterator iterator = this.entries.iterator(); iterator.hasNext(); ) {
/* 219 */       iterator.advance();
/* 220 */       double value = iterator.value();
/* 221 */       int key = iterator.key();
/* 222 */       int i = key / this.columns;
/* 223 */       int k = key % this.columns;
/* 224 */       for (int j = 0; j < outCols; j++) {
/* 225 */         int rightKey = m.computeKey(k, j);
/* 226 */         if (m.entries.containsKey(rightKey)) {
/* 227 */           int outKey = out.computeKey(i, j);
/* 228 */           double outValue = out.entries.get(outKey) + value * m.entries.get(rightKey);
/*     */           
/* 230 */           if (outValue == 0.0D) {
/* 231 */             out.entries.remove(outKey);
/*     */           } else {
/* 233 */             out.entries.put(outKey, outValue);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 239 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEntry(int row, int column) throws OutOfRangeException {
/* 245 */     MatrixUtils.checkRowIndex(this, row);
/* 246 */     MatrixUtils.checkColumnIndex(this, column);
/* 247 */     return this.entries.get(computeKey(row, column));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowDimension() {
/* 253 */     return this.rows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntry(int row, int column, double value) throws OutOfRangeException {
/* 260 */     MatrixUtils.checkRowIndex(this, row);
/* 261 */     MatrixUtils.checkColumnIndex(this, column);
/* 262 */     if (value == 0.0D) {
/* 263 */       this.entries.remove(computeKey(row, column));
/*     */     } else {
/* 265 */       this.entries.put(computeKey(row, column), value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToEntry(int row, int column, double increment) throws OutOfRangeException {
/* 273 */     MatrixUtils.checkRowIndex(this, row);
/* 274 */     MatrixUtils.checkColumnIndex(this, column);
/* 275 */     int key = computeKey(row, column);
/* 276 */     double value = this.entries.get(key) + increment;
/* 277 */     if (value == 0.0D) {
/* 278 */       this.entries.remove(key);
/*     */     } else {
/* 280 */       this.entries.put(key, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyEntry(int row, int column, double factor) throws OutOfRangeException {
/* 288 */     MatrixUtils.checkRowIndex(this, row);
/* 289 */     MatrixUtils.checkColumnIndex(this, column);
/* 290 */     int key = computeKey(row, column);
/* 291 */     double value = this.entries.get(key) * factor;
/* 292 */     if (value == 0.0D) {
/* 293 */       this.entries.remove(key);
/*     */     } else {
/* 295 */       this.entries.put(key, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int computeKey(int row, int column) {
/* 306 */     return row * this.columns + column;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/OpenMapRealMatrix.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */