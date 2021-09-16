/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.FieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ import org.apache.commons.math3.util.OpenIntToFieldHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SparseFieldVector<T extends FieldElement<T>>
/*     */   implements FieldVector<T>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7841233292190413362L;
/*     */   private final Field<T> field;
/*     */   private final OpenIntToFieldHashMap<T> entries;
/*     */   private final int virtualSize;
/*     */   
/*     */   public SparseFieldVector(Field<T> field) {
/*  67 */     this(field, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseFieldVector(Field<T> field, int dimension) {
/*  78 */     this.field = field;
/*  79 */     this.virtualSize = dimension;
/*  80 */     this.entries = new OpenIntToFieldHashMap(field);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SparseFieldVector(SparseFieldVector<T> v, int resize) {
/*  90 */     this.field = v.field;
/*  91 */     this.virtualSize = v.getDimension() + resize;
/*  92 */     this.entries = new OpenIntToFieldHashMap(v.entries);
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
/*     */   public SparseFieldVector(Field<T> field, int dimension, int expectedSize) {
/* 104 */     this.field = field;
/* 105 */     this.virtualSize = dimension;
/* 106 */     this.entries = new OpenIntToFieldHashMap(field, expectedSize);
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
/*     */   public SparseFieldVector(Field<T> field, T[] values) throws NullArgumentException {
/* 118 */     MathUtils.checkNotNull(values);
/* 119 */     this.field = field;
/* 120 */     this.virtualSize = values.length;
/* 121 */     this.entries = new OpenIntToFieldHashMap(field);
/* 122 */     for (int key = 0; key < values.length; key++) {
/* 123 */       T value = values[key];
/* 124 */       this.entries.put(key, (FieldElement)value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseFieldVector(SparseFieldVector<T> v) {
/* 134 */     this.field = v.field;
/* 135 */     this.virtualSize = v.getDimension();
/* 136 */     this.entries = new OpenIntToFieldHashMap(v.getEntries());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OpenIntToFieldHashMap<T> getEntries() {
/* 145 */     return this.entries;
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
/*     */   public FieldVector<T> add(SparseFieldVector<T> v) throws DimensionMismatchException {
/* 158 */     checkVectorDimensions(v.getDimension());
/* 159 */     SparseFieldVector<T> res = (SparseFieldVector<T>)copy();
/* 160 */     OpenIntToFieldHashMap<T>.Iterator iter = v.getEntries().iterator();
/* 161 */     while (iter.hasNext()) {
/* 162 */       iter.advance();
/* 163 */       int key = iter.key();
/* 164 */       FieldElement fieldElement = iter.value();
/* 165 */       if (this.entries.containsKey(key)) {
/* 166 */         res.setEntry(key, (T)this.entries.get(key).add(fieldElement)); continue;
/*     */       } 
/* 168 */       res.setEntry(key, (T)fieldElement);
/*     */     } 
/*     */     
/* 171 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVector<T> append(SparseFieldVector<T> v) {
/* 182 */     SparseFieldVector<T> res = new SparseFieldVector(this, v.getDimension());
/* 183 */     OpenIntToFieldHashMap<T>.Iterator iter = v.entries.iterator();
/* 184 */     while (iter.hasNext()) {
/* 185 */       iter.advance();
/* 186 */       res.setEntry(iter.key() + this.virtualSize, (T)iter.value());
/*     */     } 
/* 188 */     return res;
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldVector<T> append(FieldVector<T> v) {
/* 193 */     if (v instanceof SparseFieldVector) {
/* 194 */       return append((SparseFieldVector<T>)v);
/*     */     }
/* 196 */     int n = v.getDimension();
/* 197 */     FieldVector<T> res = new SparseFieldVector(this, n);
/* 198 */     for (int i = 0; i < n; i++) {
/* 199 */       res.setEntry(i + this.virtualSize, v.getEntry(i));
/*     */     }
/* 201 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVector<T> append(T d) throws NullArgumentException {
/* 209 */     MathUtils.checkNotNull(d);
/* 210 */     FieldVector<T> res = new SparseFieldVector(this, 1);
/* 211 */     res.setEntry(this.virtualSize, d);
/* 212 */     return res;
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldVector<T> copy() {
/* 217 */     return new SparseFieldVector(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public T dotProduct(FieldVector<T> v) throws DimensionMismatchException {
/* 222 */     checkVectorDimensions(v.getDimension());
/* 223 */     FieldElement fieldElement = (FieldElement)this.field.getZero();
/* 224 */     OpenIntToFieldHashMap<T>.Iterator iter = this.entries.iterator();
/* 225 */     while (iter.hasNext()) {
/* 226 */       iter.advance();
/* 227 */       fieldElement = (FieldElement)fieldElement.add(v.getEntry(iter.key()).multiply(iter.value()));
/*     */     } 
/* 229 */     return (T)fieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVector<T> ebeDivide(FieldVector<T> v) throws DimensionMismatchException, MathArithmeticException {
/* 235 */     checkVectorDimensions(v.getDimension());
/* 236 */     SparseFieldVector<T> res = new SparseFieldVector(this);
/* 237 */     OpenIntToFieldHashMap<T>.Iterator iter = res.entries.iterator();
/* 238 */     while (iter.hasNext()) {
/* 239 */       iter.advance();
/* 240 */       res.setEntry(iter.key(), (T)iter.value().divide(v.getEntry(iter.key())));
/*     */     } 
/* 242 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVector<T> ebeMultiply(FieldVector<T> v) throws DimensionMismatchException {
/* 248 */     checkVectorDimensions(v.getDimension());
/* 249 */     SparseFieldVector<T> res = new SparseFieldVector(this);
/* 250 */     OpenIntToFieldHashMap<T>.Iterator iter = res.entries.iterator();
/* 251 */     while (iter.hasNext()) {
/* 252 */       iter.advance();
/* 253 */       res.setEntry(iter.key(), (T)iter.value().multiply(v.getEntry(iter.key())));
/*     */     } 
/* 255 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public T[] getData() {
/* 265 */     return toArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDimension() {
/* 270 */     return this.virtualSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public T getEntry(int index) throws OutOfRangeException {
/* 275 */     checkIndex(index);
/* 276 */     return (T)this.entries.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public Field<T> getField() {
/* 281 */     return this.field;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVector<T> getSubVector(int index, int n) throws OutOfRangeException, NotPositiveException {
/* 287 */     if (n < 0) {
/* 288 */       throw new NotPositiveException(LocalizedFormats.NUMBER_OF_ELEMENTS_SHOULD_BE_POSITIVE, Integer.valueOf(n));
/*     */     }
/* 290 */     checkIndex(index);
/* 291 */     checkIndex(index + n - 1);
/* 292 */     SparseFieldVector<T> res = new SparseFieldVector(this.field, n);
/* 293 */     int end = index + n;
/* 294 */     OpenIntToFieldHashMap<T>.Iterator iter = this.entries.iterator();
/* 295 */     while (iter.hasNext()) {
/* 296 */       iter.advance();
/* 297 */       int key = iter.key();
/* 298 */       if (key >= index && key < end) {
/* 299 */         res.setEntry(key - index, (T)iter.value());
/*     */       }
/*     */     } 
/* 302 */     return res;
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldVector<T> mapAdd(T d) throws NullArgumentException {
/* 307 */     return copy().mapAddToSelf(d);
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldVector<T> mapAddToSelf(T d) throws NullArgumentException {
/* 312 */     for (int i = 0; i < this.virtualSize; i++) {
/* 313 */       setEntry(i, (T)getEntry(i).add(d));
/*     */     }
/* 315 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVector<T> mapDivide(T d) throws NullArgumentException, MathArithmeticException {
/* 321 */     return copy().mapDivideToSelf(d);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVector<T> mapDivideToSelf(T d) throws NullArgumentException, MathArithmeticException {
/* 327 */     OpenIntToFieldHashMap<T>.Iterator iter = this.entries.iterator();
/* 328 */     while (iter.hasNext()) {
/* 329 */       iter.advance();
/* 330 */       this.entries.put(iter.key(), (FieldElement)iter.value().divide(d));
/*     */     } 
/* 332 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldVector<T> mapInv() throws MathArithmeticException {
/* 337 */     return copy().mapInvToSelf();
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldVector<T> mapInvToSelf() throws MathArithmeticException {
/* 342 */     for (int i = 0; i < this.virtualSize; i++) {
/* 343 */       setEntry(i, (T)((FieldElement)this.field.getOne()).divide(getEntry(i)));
/*     */     }
/* 345 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldVector<T> mapMultiply(T d) throws NullArgumentException {
/* 350 */     return copy().mapMultiplyToSelf(d);
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldVector<T> mapMultiplyToSelf(T d) throws NullArgumentException {
/* 355 */     OpenIntToFieldHashMap<T>.Iterator iter = this.entries.iterator();
/* 356 */     while (iter.hasNext()) {
/* 357 */       iter.advance();
/* 358 */       this.entries.put(iter.key(), (FieldElement)iter.value().multiply(d));
/*     */     } 
/* 360 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldVector<T> mapSubtract(T d) throws NullArgumentException {
/* 365 */     return copy().mapSubtractToSelf(d);
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldVector<T> mapSubtractToSelf(T d) throws NullArgumentException {
/* 370 */     return mapAddToSelf((T)((FieldElement)this.field.getZero()).subtract(d));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldMatrix<T> outerProduct(SparseFieldVector<T> v) {
/* 379 */     int n = v.getDimension();
/* 380 */     SparseFieldMatrix<T> res = new SparseFieldMatrix<T>(this.field, this.virtualSize, n);
/* 381 */     OpenIntToFieldHashMap<T>.Iterator iter = this.entries.iterator();
/* 382 */     while (iter.hasNext()) {
/* 383 */       iter.advance();
/* 384 */       OpenIntToFieldHashMap<T>.Iterator iter2 = v.entries.iterator();
/* 385 */       while (iter2.hasNext()) {
/* 386 */         iter2.advance();
/* 387 */         res.setEntry(iter.key(), iter2.key(), (T)iter.value().multiply(iter2.value()));
/*     */       } 
/*     */     } 
/* 390 */     return res;
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldMatrix<T> outerProduct(FieldVector<T> v) {
/* 395 */     if (v instanceof SparseFieldVector) {
/* 396 */       return outerProduct((SparseFieldVector<T>)v);
/*     */     }
/* 398 */     int n = v.getDimension();
/* 399 */     FieldMatrix<T> res = new SparseFieldMatrix<T>(this.field, this.virtualSize, n);
/* 400 */     OpenIntToFieldHashMap<T>.Iterator iter = this.entries.iterator();
/* 401 */     while (iter.hasNext()) {
/* 402 */       iter.advance();
/* 403 */       int row = iter.key();
/* 404 */       FieldElement<T> value = iter.value();
/* 405 */       for (int col = 0; col < n; col++) {
/* 406 */         res.setEntry(row, col, (T)value.multiply(v.getEntry(col)));
/*     */       }
/*     */     } 
/* 409 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVector<T> projection(FieldVector<T> v) throws DimensionMismatchException, MathArithmeticException {
/* 416 */     checkVectorDimensions(v.getDimension());
/* 417 */     return v.mapMultiply((T)dotProduct(v).divide(v.dotProduct(v)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(T value) {
/* 424 */     MathUtils.checkNotNull(value);
/* 425 */     for (int i = 0; i < this.virtualSize; i++) {
/* 426 */       setEntry(i, value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntry(int index, T value) throws NullArgumentException, OutOfRangeException {
/* 434 */     MathUtils.checkNotNull(value);
/* 435 */     checkIndex(index);
/* 436 */     this.entries.put(index, (FieldElement)value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubVector(int index, FieldVector<T> v) throws OutOfRangeException {
/* 442 */     checkIndex(index);
/* 443 */     checkIndex(index + v.getDimension() - 1);
/* 444 */     int n = v.getDimension();
/* 445 */     for (int i = 0; i < n; i++) {
/* 446 */       setEntry(i + index, v.getEntry(i));
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
/*     */   public SparseFieldVector<T> subtract(SparseFieldVector<T> v) throws DimensionMismatchException {
/* 459 */     checkVectorDimensions(v.getDimension());
/* 460 */     SparseFieldVector<T> res = (SparseFieldVector<T>)copy();
/* 461 */     OpenIntToFieldHashMap<T>.Iterator iter = v.getEntries().iterator();
/* 462 */     while (iter.hasNext()) {
/* 463 */       iter.advance();
/* 464 */       int key = iter.key();
/* 465 */       if (this.entries.containsKey(key)) {
/* 466 */         res.setEntry(key, (T)this.entries.get(key).subtract(iter.value())); continue;
/*     */       } 
/* 468 */       res.setEntry(key, (T)((FieldElement)this.field.getZero()).subtract(iter.value()));
/*     */     } 
/*     */     
/* 471 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldVector<T> subtract(FieldVector<T> v) throws DimensionMismatchException {
/* 477 */     if (v instanceof SparseFieldVector) {
/* 478 */       return subtract((SparseFieldVector<T>)v);
/*     */     }
/* 480 */     int n = v.getDimension();
/* 481 */     checkVectorDimensions(n);
/* 482 */     SparseFieldVector<T> res = new SparseFieldVector(this);
/* 483 */     for (int i = 0; i < n; i++) {
/* 484 */       if (this.entries.containsKey(i)) {
/* 485 */         res.setEntry(i, (T)this.entries.get(i).subtract(v.getEntry(i)));
/*     */       } else {
/* 487 */         res.setEntry(i, (T)((FieldElement)this.field.getZero()).subtract(v.getEntry(i)));
/*     */       } 
/*     */     } 
/* 490 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T[] toArray() {
/* 496 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.virtualSize);
/* 497 */     OpenIntToFieldHashMap<T>.Iterator iter = this.entries.iterator();
/* 498 */     while (iter.hasNext()) {
/* 499 */       iter.advance();
/* 500 */       arrayOfFieldElement[iter.key()] = iter.value();
/*     */     } 
/* 502 */     return (T[])arrayOfFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkIndex(int index) throws OutOfRangeException {
/* 512 */     if (index < 0 || index >= getDimension()) {
/* 513 */       throw new OutOfRangeException(Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(getDimension() - 1));
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
/*     */   private void checkIndices(int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/* 528 */     int dim = getDimension();
/* 529 */     if (start < 0 || start >= dim) {
/* 530 */       throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(start), Integer.valueOf(0), Integer.valueOf(dim - 1));
/*     */     }
/*     */     
/* 533 */     if (end < 0 || end >= dim) {
/* 534 */       throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(end), Integer.valueOf(0), Integer.valueOf(dim - 1));
/*     */     }
/*     */     
/* 537 */     if (end < start) {
/* 538 */       throw new NumberIsTooSmallException(LocalizedFormats.INITIAL_ROW_AFTER_FINAL_ROW, Integer.valueOf(end), Integer.valueOf(start), false);
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
/*     */   protected void checkVectorDimensions(int n) throws DimensionMismatchException {
/* 551 */     if (getDimension() != n) {
/* 552 */       throw new DimensionMismatchException(getDimension(), n);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public FieldVector<T> add(FieldVector<T> v) throws DimensionMismatchException {
/* 558 */     if (v instanceof SparseFieldVector) {
/* 559 */       return add((SparseFieldVector<T>)v);
/*     */     }
/* 561 */     int n = v.getDimension();
/* 562 */     checkVectorDimensions(n);
/* 563 */     SparseFieldVector<T> res = new SparseFieldVector(this.field, getDimension());
/*     */     
/* 565 */     for (int i = 0; i < n; i++) {
/* 566 */       res.setEntry(i, (T)v.getEntry(i).add(getEntry(i)));
/*     */     }
/* 568 */     return res;
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
/*     */   public T walkInDefaultOrder(FieldVectorPreservingVisitor<T> visitor) {
/* 583 */     int dim = getDimension();
/* 584 */     visitor.start(dim, 0, dim - 1);
/* 585 */     for (int i = 0; i < dim; i++) {
/* 586 */       visitor.visit(i, getEntry(i));
/*     */     }
/* 588 */     return visitor.end();
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
/*     */   public T walkInDefaultOrder(FieldVectorPreservingVisitor<T> visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/* 607 */     checkIndices(start, end);
/* 608 */     visitor.start(getDimension(), start, end);
/* 609 */     for (int i = start; i <= end; i++) {
/* 610 */       visitor.visit(i, getEntry(i));
/*     */     }
/* 612 */     return visitor.end();
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
/*     */   public T walkInOptimizedOrder(FieldVectorPreservingVisitor<T> visitor) {
/* 628 */     return walkInDefaultOrder(visitor);
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
/*     */   public T walkInOptimizedOrder(FieldVectorPreservingVisitor<T> visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/* 649 */     return walkInDefaultOrder(visitor, start, end);
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
/*     */   public T walkInDefaultOrder(FieldVectorChangingVisitor<T> visitor) {
/* 663 */     int dim = getDimension();
/* 664 */     visitor.start(dim, 0, dim - 1);
/* 665 */     for (int i = 0; i < dim; i++) {
/* 666 */       setEntry(i, visitor.visit(i, getEntry(i)));
/*     */     }
/* 668 */     return visitor.end();
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
/*     */   public T walkInDefaultOrder(FieldVectorChangingVisitor<T> visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/* 687 */     checkIndices(start, end);
/* 688 */     visitor.start(getDimension(), start, end);
/* 689 */     for (int i = start; i <= end; i++) {
/* 690 */       setEntry(i, visitor.visit(i, getEntry(i)));
/*     */     }
/* 692 */     return visitor.end();
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
/*     */   public T walkInOptimizedOrder(FieldVectorChangingVisitor<T> visitor) {
/* 708 */     return walkInDefaultOrder(visitor);
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
/*     */   public T walkInOptimizedOrder(FieldVectorChangingVisitor<T> visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/* 729 */     return walkInDefaultOrder(visitor, start, end);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 735 */     int prime = 31;
/* 736 */     int result = 1;
/* 737 */     result = 31 * result + ((this.field == null) ? 0 : this.field.hashCode());
/* 738 */     result = 31 * result + this.virtualSize;
/* 739 */     OpenIntToFieldHashMap<T>.Iterator iter = this.entries.iterator();
/* 740 */     while (iter.hasNext()) {
/* 741 */       iter.advance();
/* 742 */       int temp = iter.value().hashCode();
/* 743 */       result = 31 * result + temp;
/*     */     } 
/* 745 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 753 */     if (this == obj) {
/* 754 */       return true;
/*     */     }
/*     */     
/* 757 */     if (!(obj instanceof SparseFieldVector)) {
/* 758 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 763 */     SparseFieldVector<T> other = (SparseFieldVector<T>)obj;
/* 764 */     if (this.field == null) {
/* 765 */       if (other.field != null) {
/* 766 */         return false;
/*     */       }
/* 768 */     } else if (!this.field.equals(other.field)) {
/* 769 */       return false;
/*     */     } 
/* 771 */     if (this.virtualSize != other.virtualSize) {
/* 772 */       return false;
/*     */     }
/*     */     
/* 775 */     OpenIntToFieldHashMap<T>.Iterator iter = this.entries.iterator();
/* 776 */     while (iter.hasNext()) {
/* 777 */       iter.advance();
/* 778 */       T test = other.getEntry(iter.key());
/* 779 */       if (!test.equals(iter.value())) {
/* 780 */         return false;
/*     */       }
/*     */     } 
/* 783 */     iter = other.getEntries().iterator();
/* 784 */     while (iter.hasNext()) {
/* 785 */       iter.advance();
/* 786 */       FieldElement fieldElement = iter.value();
/* 787 */       if (!fieldElement.equals(getEntry(iter.key()))) {
/* 788 */         return false;
/*     */       }
/*     */     } 
/* 791 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/SparseFieldVector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */