/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.FieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldLUDecomposition<T extends FieldElement<T>>
/*     */ {
/*     */   private final Field<T> field;
/*     */   private T[][] lu;
/*     */   private int[] pivot;
/*     */   private boolean even;
/*     */   private boolean singular;
/*     */   private FieldMatrix<T> cachedL;
/*     */   private FieldMatrix<T> cachedU;
/*     */   private FieldMatrix<T> cachedP;
/*     */   
/*     */   public FieldLUDecomposition(FieldMatrix<T> matrix) {
/*  85 */     if (!matrix.isSquare()) {
/*  86 */       throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
/*     */     }
/*     */ 
/*     */     
/*  90 */     int m = matrix.getColumnDimension();
/*  91 */     this.field = matrix.getField();
/*  92 */     this.lu = matrix.getData();
/*  93 */     this.pivot = new int[m];
/*  94 */     this.cachedL = null;
/*  95 */     this.cachedU = null;
/*  96 */     this.cachedP = null;
/*     */ 
/*     */     
/*  99 */     for (int row = 0; row < m; row++) {
/* 100 */       this.pivot[row] = row;
/*     */     }
/* 102 */     this.even = true;
/* 103 */     this.singular = false;
/*     */ 
/*     */     
/* 106 */     for (int col = 0; col < m; col++) {
/*     */       
/* 108 */       FieldElement fieldElement = (FieldElement)this.field.getZero();
/*     */ 
/*     */       
/* 111 */       for (int i = 0; i < col; i++) {
/* 112 */         FieldElement fieldElement1; T[] luRow = this.lu[i];
/* 113 */         T t = luRow[col];
/* 114 */         for (int n = 0; n < i; n++) {
/* 115 */           fieldElement1 = (FieldElement)t.subtract(luRow[n].multiply(this.lu[n][col]));
/*     */         }
/* 117 */         luRow[col] = (T)fieldElement1;
/*     */       } 
/*     */ 
/*     */       
/* 121 */       int nonZero = col;
/* 122 */       for (int j = col; j < m; j++) {
/* 123 */         FieldElement fieldElement1; T[] luRow = this.lu[j];
/* 124 */         T t = luRow[col];
/* 125 */         for (int n = 0; n < col; n++) {
/* 126 */           fieldElement1 = (FieldElement)t.subtract(luRow[n].multiply(this.lu[n][col]));
/*     */         }
/* 128 */         luRow[col] = (T)fieldElement1;
/*     */         
/* 130 */         if (this.lu[nonZero][col].equals(this.field.getZero()))
/*     */         {
/* 132 */           nonZero++;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 137 */       if (nonZero >= m) {
/* 138 */         this.singular = true;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 143 */       if (nonZero != col) {
/* 144 */         FieldElement fieldElement1 = (FieldElement)this.field.getZero();
/* 145 */         for (int n = 0; n < m; n++) {
/* 146 */           T t = this.lu[nonZero][n];
/* 147 */           this.lu[nonZero][n] = this.lu[col][n];
/* 148 */           this.lu[col][n] = t;
/*     */         } 
/* 150 */         int temp = this.pivot[nonZero];
/* 151 */         this.pivot[nonZero] = this.pivot[col];
/* 152 */         this.pivot[col] = temp;
/* 153 */         this.even = !this.even;
/*     */       } 
/*     */ 
/*     */       
/* 157 */       T luDiag = this.lu[col][col];
/* 158 */       for (int k = col + 1; k < m; k++) {
/* 159 */         T[] luRow = this.lu[k];
/* 160 */         luRow[col] = (T)luRow[col].divide(luDiag);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldMatrix<T> getL() {
/* 172 */     if (this.cachedL == null && !this.singular) {
/* 173 */       int m = this.pivot.length;
/* 174 */       this.cachedL = new Array2DRowFieldMatrix<T>(this.field, m, m);
/* 175 */       for (int i = 0; i < m; i++) {
/* 176 */         T[] luI = this.lu[i];
/* 177 */         for (int j = 0; j < i; j++) {
/* 178 */           this.cachedL.setEntry(i, j, luI[j]);
/*     */         }
/* 180 */         this.cachedL.setEntry(i, i, (T)this.field.getOne());
/*     */       } 
/*     */     } 
/* 183 */     return this.cachedL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldMatrix<T> getU() {
/* 192 */     if (this.cachedU == null && !this.singular) {
/* 193 */       int m = this.pivot.length;
/* 194 */       this.cachedU = new Array2DRowFieldMatrix<T>(this.field, m, m);
/* 195 */       for (int i = 0; i < m; i++) {
/* 196 */         T[] luI = this.lu[i];
/* 197 */         for (int j = i; j < m; j++) {
/* 198 */           this.cachedU.setEntry(i, j, luI[j]);
/*     */         }
/*     */       } 
/*     */     } 
/* 202 */     return this.cachedU;
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
/*     */   public FieldMatrix<T> getP() {
/* 215 */     if (this.cachedP == null && !this.singular) {
/* 216 */       int m = this.pivot.length;
/* 217 */       this.cachedP = new Array2DRowFieldMatrix<T>(this.field, m, m);
/* 218 */       for (int i = 0; i < m; i++) {
/* 219 */         this.cachedP.setEntry(i, this.pivot[i], (T)this.field.getOne());
/*     */       }
/*     */     } 
/* 222 */     return this.cachedP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getPivot() {
/* 231 */     return (int[])this.pivot.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getDeterminant() {
/* 239 */     if (this.singular) {
/* 240 */       return (T)this.field.getZero();
/*     */     }
/* 242 */     int m = this.pivot.length;
/* 243 */     FieldElement fieldElement = this.even ? (FieldElement)this.field.getOne() : (FieldElement)((FieldElement)this.field.getZero()).subtract(this.field.getOne());
/* 244 */     for (int i = 0; i < m; i++) {
/* 245 */       fieldElement = (FieldElement)fieldElement.multiply(this.lu[i][i]);
/*     */     }
/* 247 */     return (T)fieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldDecompositionSolver<T> getSolver() {
/* 256 */     return new Solver<T>(this.field, (FieldElement[][])this.lu, this.pivot, this.singular);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Solver<T extends FieldElement<T>>
/*     */     implements FieldDecompositionSolver<T>
/*     */   {
/*     */     private final Field<T> field;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final T[][] lu;
/*     */ 
/*     */ 
/*     */     
/*     */     private final int[] pivot;
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean singular;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Solver(Field<T> field, T[][] lu, int[] pivot, boolean singular) {
/* 285 */       this.field = field;
/* 286 */       this.lu = lu;
/* 287 */       this.pivot = pivot;
/* 288 */       this.singular = singular;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isNonSingular() {
/* 293 */       return !this.singular;
/*     */     }
/*     */ 
/*     */     
/*     */     public FieldVector<T> solve(FieldVector<T> b) {
/*     */       try {
/* 299 */         return solve((ArrayFieldVector<T>)b);
/* 300 */       } catch (ClassCastException cce) {
/*     */         
/* 302 */         int m = this.pivot.length;
/* 303 */         if (b.getDimension() != m) {
/* 304 */           throw new DimensionMismatchException(b.getDimension(), m);
/*     */         }
/* 306 */         if (this.singular) {
/* 307 */           throw new SingularMatrixException();
/*     */         }
/*     */ 
/*     */         
/* 311 */         FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, m);
/* 312 */         for (int row = 0; row < m; row++) {
/* 313 */           arrayOfFieldElement[row] = (FieldElement)b.getEntry(this.pivot[row]);
/*     */         }
/*     */         
/*     */         int col;
/* 317 */         for (col = 0; col < m; col++) {
/* 318 */           FieldElement fieldElement = arrayOfFieldElement[col];
/* 319 */           for (int i = col + 1; i < m; i++) {
/* 320 */             arrayOfFieldElement[i] = (FieldElement)arrayOfFieldElement[i].subtract(fieldElement.multiply(this.lu[i][col]));
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 325 */         for (col = m - 1; col >= 0; col--) {
/* 326 */           arrayOfFieldElement[col] = (FieldElement)arrayOfFieldElement[col].divide(this.lu[col][col]);
/* 327 */           FieldElement fieldElement = arrayOfFieldElement[col];
/* 328 */           for (int i = 0; i < col; i++) {
/* 329 */             arrayOfFieldElement[i] = (FieldElement)arrayOfFieldElement[i].subtract(fieldElement.multiply(this.lu[i][col]));
/*     */           }
/*     */         } 
/*     */         
/* 333 */         return new ArrayFieldVector<T>(this.field, (T[])arrayOfFieldElement, false);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ArrayFieldVector<T> solve(ArrayFieldVector<T> b) {
/* 346 */       int m = this.pivot.length;
/* 347 */       int length = b.getDimension();
/* 348 */       if (length != m) {
/* 349 */         throw new DimensionMismatchException(length, m);
/*     */       }
/* 351 */       if (this.singular) {
/* 352 */         throw new SingularMatrixException();
/*     */       }
/*     */ 
/*     */       
/* 356 */       FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, m);
/* 357 */       for (int row = 0; row < m; row++) {
/* 358 */         arrayOfFieldElement[row] = (FieldElement)b.getEntry(this.pivot[row]);
/*     */       }
/*     */       
/*     */       int col;
/* 362 */       for (col = 0; col < m; col++) {
/* 363 */         FieldElement fieldElement = arrayOfFieldElement[col];
/* 364 */         for (int i = col + 1; i < m; i++) {
/* 365 */           arrayOfFieldElement[i] = (FieldElement)arrayOfFieldElement[i].subtract(fieldElement.multiply(this.lu[i][col]));
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 370 */       for (col = m - 1; col >= 0; col--) {
/* 371 */         arrayOfFieldElement[col] = (FieldElement)arrayOfFieldElement[col].divide(this.lu[col][col]);
/* 372 */         FieldElement fieldElement = arrayOfFieldElement[col];
/* 373 */         for (int i = 0; i < col; i++) {
/* 374 */           arrayOfFieldElement[i] = (FieldElement)arrayOfFieldElement[i].subtract(fieldElement.multiply(this.lu[i][col]));
/*     */         }
/*     */       } 
/*     */       
/* 378 */       return new ArrayFieldVector<T>((T[])arrayOfFieldElement, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public FieldMatrix<T> solve(FieldMatrix<T> b) {
/* 383 */       int m = this.pivot.length;
/* 384 */       if (b.getRowDimension() != m) {
/* 385 */         throw new DimensionMismatchException(b.getRowDimension(), m);
/*     */       }
/* 387 */       if (this.singular) {
/* 388 */         throw new SingularMatrixException();
/*     */       }
/*     */       
/* 391 */       int nColB = b.getColumnDimension();
/*     */ 
/*     */       
/* 394 */       FieldElement[][] arrayOfFieldElement = (FieldElement[][])MathArrays.buildArray(this.field, m, nColB);
/* 395 */       for (int row = 0; row < m; row++) {
/* 396 */         FieldElement[] arrayOfFieldElement1 = arrayOfFieldElement[row];
/* 397 */         int pRow = this.pivot[row];
/* 398 */         for (int i = 0; i < nColB; i++) {
/* 399 */           arrayOfFieldElement1[i] = (FieldElement)b.getEntry(pRow, i);
/*     */         }
/*     */       } 
/*     */       
/*     */       int col;
/* 404 */       for (col = 0; col < m; col++) {
/* 405 */         FieldElement[] arrayOfFieldElement1 = arrayOfFieldElement[col];
/* 406 */         for (int i = col + 1; i < m; i++) {
/* 407 */           FieldElement[] arrayOfFieldElement2 = arrayOfFieldElement[i];
/* 408 */           T luICol = this.lu[i][col];
/* 409 */           for (int j = 0; j < nColB; j++) {
/* 410 */             arrayOfFieldElement2[j] = (FieldElement)arrayOfFieldElement2[j].subtract(arrayOfFieldElement1[j].multiply(luICol));
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 416 */       for (col = m - 1; col >= 0; col--) {
/* 417 */         FieldElement[] arrayOfFieldElement1 = arrayOfFieldElement[col];
/* 418 */         T luDiag = this.lu[col][col];
/* 419 */         for (int j = 0; j < nColB; j++) {
/* 420 */           arrayOfFieldElement1[j] = (FieldElement)arrayOfFieldElement1[j].divide(luDiag);
/*     */         }
/* 422 */         for (int i = 0; i < col; i++) {
/* 423 */           FieldElement[] arrayOfFieldElement2 = arrayOfFieldElement[i];
/* 424 */           T luICol = this.lu[i][col];
/* 425 */           for (int k = 0; k < nColB; k++) {
/* 426 */             arrayOfFieldElement2[k] = (FieldElement)arrayOfFieldElement2[k].subtract(arrayOfFieldElement1[k].multiply(luICol));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 431 */       return new Array2DRowFieldMatrix<T>(this.field, (T[][])arrayOfFieldElement, false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public FieldMatrix<T> getInverse() {
/* 437 */       int m = this.pivot.length;
/* 438 */       FieldElement fieldElement = (FieldElement)this.field.getOne();
/* 439 */       FieldMatrix<T> identity = new Array2DRowFieldMatrix<T>(this.field, m, m);
/* 440 */       for (int i = 0; i < m; i++) {
/* 441 */         identity.setEntry(i, i, (T)fieldElement);
/*     */       }
/* 443 */       return solve(identity);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/FieldLUDecomposition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */