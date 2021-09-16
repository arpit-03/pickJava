/*      */ package org.apache.commons.math3.linear;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import org.apache.commons.math3.Field;
/*      */ import org.apache.commons.math3.FieldElement;
/*      */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*      */ import org.apache.commons.math3.exception.MathArithmeticException;
/*      */ import org.apache.commons.math3.exception.NotPositiveException;
/*      */ import org.apache.commons.math3.exception.NullArgumentException;
/*      */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*      */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*      */ import org.apache.commons.math3.exception.OutOfRangeException;
/*      */ import org.apache.commons.math3.exception.ZeroException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.util.MathArrays;
/*      */ import org.apache.commons.math3.util.MathUtils;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ArrayFieldVector<T extends FieldElement<T>>
/*      */   implements FieldVector<T>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 7648186910365927050L;
/*      */   private T[] data;
/*      */   private final Field<T> field;
/*      */   
/*      */   public ArrayFieldVector(Field<T> field) {
/*   62 */     this(field, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayFieldVector(Field<T> field, int size) {
/*   72 */     this.field = field;
/*   73 */     this.data = (T[])MathArrays.buildArray(field, size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayFieldVector(int size, T preset) {
/*   83 */     this(preset.getField(), size);
/*   84 */     Arrays.fill((Object[])this.data, preset);
/*      */   }
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
/*      */   public ArrayFieldVector(T[] d) throws NullArgumentException, ZeroException {
/*  101 */     MathUtils.checkNotNull(d);
/*      */     try {
/*  103 */       this.field = d[0].getField();
/*  104 */       this.data = (T[])d.clone();
/*  105 */     } catch (ArrayIndexOutOfBoundsException e) {
/*  106 */       throw new ZeroException(LocalizedFormats.VECTOR_MUST_HAVE_AT_LEAST_ONE_ELEMENT, new Object[0]);
/*      */     } 
/*      */   }
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
/*      */   public ArrayFieldVector(Field<T> field, T[] d) throws NullArgumentException {
/*  120 */     MathUtils.checkNotNull(d);
/*  121 */     this.field = field;
/*  122 */     this.data = (T[])d.clone();
/*      */   }
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
/*      */   public ArrayFieldVector(T[] d, boolean copyArray) throws NullArgumentException, ZeroException {
/*  148 */     MathUtils.checkNotNull(d);
/*  149 */     if (d.length == 0) {
/*  150 */       throw new ZeroException(LocalizedFormats.VECTOR_MUST_HAVE_AT_LEAST_ONE_ELEMENT, new Object[0]);
/*      */     }
/*  152 */     this.field = d[0].getField();
/*  153 */     this.data = copyArray ? (T[])d.clone() : d;
/*      */   }
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
/*      */   public ArrayFieldVector(Field<T> field, T[] d, boolean copyArray) throws NullArgumentException {
/*  173 */     MathUtils.checkNotNull(d);
/*  174 */     this.field = field;
/*  175 */     this.data = copyArray ? (T[])d.clone() : d;
/*      */   }
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
/*      */   public ArrayFieldVector(T[] d, int pos, int size) throws NullArgumentException, NumberIsTooLargeException {
/*  190 */     MathUtils.checkNotNull(d);
/*  191 */     if (d.length < pos + size) {
/*  192 */       throw new NumberIsTooLargeException(Integer.valueOf(pos + size), Integer.valueOf(d.length), true);
/*      */     }
/*  194 */     this.field = d[0].getField();
/*  195 */     this.data = (T[])MathArrays.buildArray(this.field, size);
/*  196 */     System.arraycopy(d, pos, this.data, 0, size);
/*      */   }
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
/*      */   public ArrayFieldVector(Field<T> field, T[] d, int pos, int size) throws NullArgumentException, NumberIsTooLargeException {
/*  212 */     MathUtils.checkNotNull(d);
/*  213 */     if (d.length < pos + size) {
/*  214 */       throw new NumberIsTooLargeException(Integer.valueOf(pos + size), Integer.valueOf(d.length), true);
/*      */     }
/*  216 */     this.field = field;
/*  217 */     this.data = (T[])MathArrays.buildArray(field, size);
/*  218 */     System.arraycopy(d, pos, this.data, 0, size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayFieldVector(FieldVector<T> v) throws NullArgumentException {
/*  229 */     MathUtils.checkNotNull(v);
/*  230 */     this.field = v.getField();
/*  231 */     this.data = (T[])MathArrays.buildArray(this.field, v.getDimension());
/*  232 */     for (int i = 0; i < this.data.length; i++) {
/*  233 */       this.data[i] = v.getEntry(i);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayFieldVector(ArrayFieldVector<T> v) throws NullArgumentException {
/*  245 */     MathUtils.checkNotNull(v);
/*  246 */     this.field = v.getField();
/*  247 */     this.data = (T[])v.data.clone();
/*      */   }
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
/*      */   public ArrayFieldVector(ArrayFieldVector<T> v, boolean deep) throws NullArgumentException {
/*  260 */     MathUtils.checkNotNull(v);
/*  261 */     this.field = v.getField();
/*  262 */     this.data = deep ? (T[])v.data.clone() : v.data;
/*      */   }
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
/*      */   @Deprecated
/*      */   public ArrayFieldVector(ArrayFieldVector<T> v1, ArrayFieldVector<T> v2) throws NullArgumentException {
/*  277 */     this(v1, v2);
/*      */   }
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
/*      */   public ArrayFieldVector(FieldVector<T> v1, FieldVector<T> v2) throws NullArgumentException {
/*  291 */     MathUtils.checkNotNull(v1);
/*  292 */     MathUtils.checkNotNull(v2);
/*  293 */     this.field = v1.getField();
/*  294 */     T[] v1Data = (v1 instanceof ArrayFieldVector) ? ((ArrayFieldVector)v1).data : v1.toArray();
/*      */     
/*  296 */     T[] v2Data = (v2 instanceof ArrayFieldVector) ? ((ArrayFieldVector)v2).data : v2.toArray();
/*      */     
/*  298 */     this.data = (T[])MathArrays.buildArray(this.field, v1Data.length + v2Data.length);
/*  299 */     System.arraycopy(v1Data, 0, this.data, 0, v1Data.length);
/*  300 */     System.arraycopy(v2Data, 0, this.data, v1Data.length, v2Data.length);
/*      */   }
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
/*      */   @Deprecated
/*      */   public ArrayFieldVector(ArrayFieldVector<T> v1, T[] v2) throws NullArgumentException {
/*  315 */     this(v1, v2);
/*      */   }
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
/*      */   public ArrayFieldVector(FieldVector<T> v1, T[] v2) throws NullArgumentException {
/*  329 */     MathUtils.checkNotNull(v1);
/*  330 */     MathUtils.checkNotNull(v2);
/*  331 */     this.field = v1.getField();
/*  332 */     T[] v1Data = (v1 instanceof ArrayFieldVector) ? ((ArrayFieldVector)v1).data : v1.toArray();
/*      */     
/*  334 */     this.data = (T[])MathArrays.buildArray(this.field, v1Data.length + v2.length);
/*  335 */     System.arraycopy(v1Data, 0, this.data, 0, v1Data.length);
/*  336 */     System.arraycopy(v2, 0, this.data, v1Data.length, v2.length);
/*      */   }
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
/*      */   @Deprecated
/*      */   public ArrayFieldVector(T[] v1, ArrayFieldVector<T> v2) throws NullArgumentException {
/*  351 */     this(v1, v2);
/*      */   }
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
/*      */   public ArrayFieldVector(T[] v1, FieldVector<T> v2) throws NullArgumentException {
/*  365 */     MathUtils.checkNotNull(v1);
/*  366 */     MathUtils.checkNotNull(v2);
/*  367 */     this.field = v2.getField();
/*  368 */     T[] v2Data = (v2 instanceof ArrayFieldVector) ? ((ArrayFieldVector)v2).data : v2.toArray();
/*      */     
/*  370 */     this.data = (T[])MathArrays.buildArray(this.field, v1.length + v2Data.length);
/*  371 */     System.arraycopy(v1, 0, this.data, 0, v1.length);
/*  372 */     System.arraycopy(v2Data, 0, this.data, v1.length, v2Data.length);
/*      */   }
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
/*      */   public ArrayFieldVector(T[] v1, T[] v2) throws NullArgumentException, ZeroException {
/*  392 */     MathUtils.checkNotNull(v1);
/*  393 */     MathUtils.checkNotNull(v2);
/*  394 */     if (v1.length + v2.length == 0) {
/*  395 */       throw new ZeroException(LocalizedFormats.VECTOR_MUST_HAVE_AT_LEAST_ONE_ELEMENT, new Object[0]);
/*      */     }
/*  397 */     this.data = (T[])MathArrays.buildArray(v1[0].getField(), v1.length + v2.length);
/*  398 */     System.arraycopy(v1, 0, this.data, 0, v1.length);
/*  399 */     System.arraycopy(v2, 0, this.data, v1.length, v2.length);
/*  400 */     this.field = this.data[0].getField();
/*      */   }
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
/*      */   public ArrayFieldVector(Field<T> field, T[] v1, T[] v2) throws NullArgumentException, ZeroException {
/*  416 */     MathUtils.checkNotNull(v1);
/*  417 */     MathUtils.checkNotNull(v2);
/*  418 */     if (v1.length + v2.length == 0) {
/*  419 */       throw new ZeroException(LocalizedFormats.VECTOR_MUST_HAVE_AT_LEAST_ONE_ELEMENT, new Object[0]);
/*      */     }
/*  421 */     this.data = (T[])MathArrays.buildArray(field, v1.length + v2.length);
/*  422 */     System.arraycopy(v1, 0, this.data, 0, v1.length);
/*  423 */     System.arraycopy(v2, 0, this.data, v1.length, v2.length);
/*  424 */     this.field = field;
/*      */   }
/*      */ 
/*      */   
/*      */   public Field<T> getField() {
/*  429 */     return this.field;
/*      */   }
/*      */ 
/*      */   
/*      */   public FieldVector<T> copy() {
/*  434 */     return new ArrayFieldVector(this, true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector<T> add(FieldVector<T> v) throws DimensionMismatchException {
/*      */     try {
/*  441 */       return add((ArrayFieldVector<T>)v);
/*  442 */     } catch (ClassCastException cce) {
/*  443 */       checkVectorDimensions(v);
/*  444 */       FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.data.length);
/*  445 */       for (int i = 0; i < this.data.length; i++) {
/*  446 */         arrayOfFieldElement[i] = (FieldElement)this.data[i].add(v.getEntry(i));
/*      */       }
/*  448 */       return new ArrayFieldVector(this.field, (T[])arrayOfFieldElement, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayFieldVector<T> add(ArrayFieldVector<T> v) throws DimensionMismatchException {
/*  461 */     checkVectorDimensions(v.data.length);
/*  462 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.data.length);
/*  463 */     for (int i = 0; i < this.data.length; i++) {
/*  464 */       arrayOfFieldElement[i] = (FieldElement)this.data[i].add(v.data[i]);
/*      */     }
/*  466 */     return new ArrayFieldVector(this.field, (T[])arrayOfFieldElement, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector<T> subtract(FieldVector<T> v) throws DimensionMismatchException {
/*      */     try {
/*  473 */       return subtract((ArrayFieldVector<T>)v);
/*  474 */     } catch (ClassCastException cce) {
/*  475 */       checkVectorDimensions(v);
/*  476 */       FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.data.length);
/*  477 */       for (int i = 0; i < this.data.length; i++) {
/*  478 */         arrayOfFieldElement[i] = (FieldElement)this.data[i].subtract(v.getEntry(i));
/*      */       }
/*  480 */       return new ArrayFieldVector(this.field, (T[])arrayOfFieldElement, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayFieldVector<T> subtract(ArrayFieldVector<T> v) throws DimensionMismatchException {
/*  493 */     checkVectorDimensions(v.data.length);
/*  494 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.data.length);
/*  495 */     for (int i = 0; i < this.data.length; i++) {
/*  496 */       arrayOfFieldElement[i] = (FieldElement)this.data[i].subtract(v.data[i]);
/*      */     }
/*  498 */     return new ArrayFieldVector(this.field, (T[])arrayOfFieldElement, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public FieldVector<T> mapAdd(T d) throws NullArgumentException {
/*  503 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.data.length);
/*  504 */     for (int i = 0; i < this.data.length; i++) {
/*  505 */       arrayOfFieldElement[i] = (FieldElement)this.data[i].add(d);
/*      */     }
/*  507 */     return new ArrayFieldVector(this.field, (T[])arrayOfFieldElement, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public FieldVector<T> mapAddToSelf(T d) throws NullArgumentException {
/*  512 */     for (int i = 0; i < this.data.length; i++) {
/*  513 */       this.data[i] = (T)this.data[i].add(d);
/*      */     }
/*  515 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FieldVector<T> mapSubtract(T d) throws NullArgumentException {
/*  520 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.data.length);
/*  521 */     for (int i = 0; i < this.data.length; i++) {
/*  522 */       arrayOfFieldElement[i] = (FieldElement)this.data[i].subtract(d);
/*      */     }
/*  524 */     return new ArrayFieldVector(this.field, (T[])arrayOfFieldElement, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public FieldVector<T> mapSubtractToSelf(T d) throws NullArgumentException {
/*  529 */     for (int i = 0; i < this.data.length; i++) {
/*  530 */       this.data[i] = (T)this.data[i].subtract(d);
/*      */     }
/*  532 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FieldVector<T> mapMultiply(T d) throws NullArgumentException {
/*  537 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.data.length);
/*  538 */     for (int i = 0; i < this.data.length; i++) {
/*  539 */       arrayOfFieldElement[i] = (FieldElement)this.data[i].multiply(d);
/*      */     }
/*  541 */     return new ArrayFieldVector(this.field, (T[])arrayOfFieldElement, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public FieldVector<T> mapMultiplyToSelf(T d) throws NullArgumentException {
/*  546 */     for (int i = 0; i < this.data.length; i++) {
/*  547 */       this.data[i] = (T)this.data[i].multiply(d);
/*      */     }
/*  549 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector<T> mapDivide(T d) throws NullArgumentException, MathArithmeticException {
/*  555 */     MathUtils.checkNotNull(d);
/*  556 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.data.length);
/*  557 */     for (int i = 0; i < this.data.length; i++) {
/*  558 */       arrayOfFieldElement[i] = (FieldElement)this.data[i].divide(d);
/*      */     }
/*  560 */     return new ArrayFieldVector(this.field, (T[])arrayOfFieldElement, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector<T> mapDivideToSelf(T d) throws NullArgumentException, MathArithmeticException {
/*  566 */     MathUtils.checkNotNull(d);
/*  567 */     for (int i = 0; i < this.data.length; i++) {
/*  568 */       this.data[i] = (T)this.data[i].divide(d);
/*      */     }
/*  570 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public FieldVector<T> mapInv() throws MathArithmeticException {
/*  575 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.data.length);
/*  576 */     FieldElement fieldElement = (FieldElement)this.field.getOne();
/*  577 */     for (int i = 0; i < this.data.length; i++) {
/*      */       try {
/*  579 */         arrayOfFieldElement[i] = (FieldElement)fieldElement.divide(this.data[i]);
/*  580 */       } catch (MathArithmeticException e) {
/*  581 */         throw new MathArithmeticException(LocalizedFormats.INDEX, new Object[] { Integer.valueOf(i) });
/*      */       } 
/*      */     } 
/*  584 */     return new ArrayFieldVector(this.field, (T[])arrayOfFieldElement, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public FieldVector<T> mapInvToSelf() throws MathArithmeticException {
/*  589 */     FieldElement fieldElement = (FieldElement)this.field.getOne();
/*  590 */     for (int i = 0; i < this.data.length; i++) {
/*      */       try {
/*  592 */         this.data[i] = (T)fieldElement.divide(this.data[i]);
/*  593 */       } catch (MathArithmeticException e) {
/*  594 */         throw new MathArithmeticException(LocalizedFormats.INDEX, new Object[] { Integer.valueOf(i) });
/*      */       } 
/*      */     } 
/*  597 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector<T> ebeMultiply(FieldVector<T> v) throws DimensionMismatchException {
/*      */     try {
/*  604 */       return ebeMultiply((ArrayFieldVector<T>)v);
/*  605 */     } catch (ClassCastException cce) {
/*  606 */       checkVectorDimensions(v);
/*  607 */       FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.data.length);
/*  608 */       for (int i = 0; i < this.data.length; i++) {
/*  609 */         arrayOfFieldElement[i] = (FieldElement)this.data[i].multiply(v.getEntry(i));
/*      */       }
/*  611 */       return new ArrayFieldVector(this.field, (T[])arrayOfFieldElement, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayFieldVector<T> ebeMultiply(ArrayFieldVector<T> v) throws DimensionMismatchException {
/*  624 */     checkVectorDimensions(v.data.length);
/*  625 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.data.length);
/*  626 */     for (int i = 0; i < this.data.length; i++) {
/*  627 */       arrayOfFieldElement[i] = (FieldElement)this.data[i].multiply(v.data[i]);
/*      */     }
/*  629 */     return new ArrayFieldVector(this.field, (T[])arrayOfFieldElement, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector<T> ebeDivide(FieldVector<T> v) throws DimensionMismatchException, MathArithmeticException {
/*      */     try {
/*  636 */       return ebeDivide((ArrayFieldVector<T>)v);
/*  637 */     } catch (ClassCastException cce) {
/*  638 */       checkVectorDimensions(v);
/*  639 */       FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.data.length);
/*  640 */       for (int i = 0; i < this.data.length; i++) {
/*      */         try {
/*  642 */           arrayOfFieldElement[i] = (FieldElement)this.data[i].divide(v.getEntry(i));
/*  643 */         } catch (MathArithmeticException e) {
/*  644 */           throw new MathArithmeticException(LocalizedFormats.INDEX, new Object[] { Integer.valueOf(i) });
/*      */         } 
/*      */       } 
/*  647 */       return new ArrayFieldVector(this.field, (T[])arrayOfFieldElement, false);
/*      */     } 
/*      */   }
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
/*      */   public ArrayFieldVector<T> ebeDivide(ArrayFieldVector<T> v) throws DimensionMismatchException, MathArithmeticException {
/*  661 */     checkVectorDimensions(v.data.length);
/*  662 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.data.length);
/*  663 */     for (int i = 0; i < this.data.length; i++) {
/*      */       try {
/*  665 */         arrayOfFieldElement[i] = (FieldElement)this.data[i].divide(v.data[i]);
/*  666 */       } catch (MathArithmeticException e) {
/*  667 */         throw new MathArithmeticException(LocalizedFormats.INDEX, new Object[] { Integer.valueOf(i) });
/*      */       } 
/*      */     } 
/*  670 */     return new ArrayFieldVector(this.field, (T[])arrayOfFieldElement, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public T[] getData() {
/*  675 */     return (T[])this.data.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T[] getDataRef() {
/*  684 */     return this.data;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public T dotProduct(FieldVector<T> v) throws DimensionMismatchException {
/*      */     try {
/*  691 */       return dotProduct((ArrayFieldVector<T>)v);
/*  692 */     } catch (ClassCastException cce) {
/*  693 */       checkVectorDimensions(v);
/*  694 */       FieldElement fieldElement = (FieldElement)this.field.getZero();
/*  695 */       for (int i = 0; i < this.data.length; i++) {
/*  696 */         fieldElement = (FieldElement)fieldElement.add(this.data[i].multiply(v.getEntry(i)));
/*      */       }
/*  698 */       return (T)fieldElement;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T dotProduct(ArrayFieldVector<T> v) throws DimensionMismatchException {
/*  711 */     checkVectorDimensions(v.data.length);
/*  712 */     FieldElement fieldElement = (FieldElement)this.field.getZero();
/*  713 */     for (int i = 0; i < this.data.length; i++) {
/*  714 */       fieldElement = (FieldElement)fieldElement.add(this.data[i].multiply(v.data[i]));
/*      */     }
/*  716 */     return (T)fieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector<T> projection(FieldVector<T> v) throws DimensionMismatchException, MathArithmeticException {
/*  722 */     return v.mapMultiply((T)dotProduct(v).divide(v.dotProduct(v)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayFieldVector<T> projection(ArrayFieldVector<T> v) throws DimensionMismatchException, MathArithmeticException {
/*  734 */     return (ArrayFieldVector<T>)v.mapMultiply((T)dotProduct(v).divide(v.dotProduct(v)));
/*      */   }
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> outerProduct(FieldVector<T> v) {
/*      */     try {
/*  740 */       return outerProduct((ArrayFieldVector<T>)v);
/*  741 */     } catch (ClassCastException cce) {
/*  742 */       int m = this.data.length;
/*  743 */       int n = v.getDimension();
/*  744 */       FieldMatrix<T> out = new Array2DRowFieldMatrix<T>(this.field, m, n);
/*  745 */       for (int i = 0; i < m; i++) {
/*  746 */         for (int j = 0; j < n; j++) {
/*  747 */           out.setEntry(i, j, (T)this.data[i].multiply(v.getEntry(j)));
/*      */         }
/*      */       } 
/*  750 */       return out;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldMatrix<T> outerProduct(ArrayFieldVector<T> v) {
/*  760 */     int m = this.data.length;
/*  761 */     int n = v.data.length;
/*  762 */     FieldMatrix<T> out = new Array2DRowFieldMatrix<T>(this.field, m, n);
/*  763 */     for (int i = 0; i < m; i++) {
/*  764 */       for (int j = 0; j < n; j++) {
/*  765 */         out.setEntry(i, j, (T)this.data[i].multiply(v.data[j]));
/*      */       }
/*      */     } 
/*  768 */     return out;
/*      */   }
/*      */ 
/*      */   
/*      */   public T getEntry(int index) {
/*  773 */     return this.data[index];
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDimension() {
/*  778 */     return this.data.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public FieldVector<T> append(FieldVector<T> v) {
/*      */     try {
/*  784 */       return append((ArrayFieldVector<T>)v);
/*  785 */     } catch (ClassCastException cce) {
/*  786 */       return new ArrayFieldVector(this, new ArrayFieldVector(v));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayFieldVector<T> append(ArrayFieldVector<T> v) {
/*  796 */     return new ArrayFieldVector(this, v);
/*      */   }
/*      */ 
/*      */   
/*      */   public FieldVector<T> append(T in) {
/*  801 */     FieldElement[] arrayOfFieldElement = (FieldElement[])MathArrays.buildArray(this.field, this.data.length + 1);
/*  802 */     System.arraycopy(this.data, 0, arrayOfFieldElement, 0, this.data.length);
/*  803 */     arrayOfFieldElement[this.data.length] = (FieldElement)in;
/*  804 */     return new ArrayFieldVector(this.field, (T[])arrayOfFieldElement, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector<T> getSubVector(int index, int n) throws OutOfRangeException, NotPositiveException {
/*  810 */     if (n < 0) {
/*  811 */       throw new NotPositiveException(LocalizedFormats.NUMBER_OF_ELEMENTS_SHOULD_BE_POSITIVE, Integer.valueOf(n));
/*      */     }
/*  813 */     ArrayFieldVector<T> out = new ArrayFieldVector(this.field, n);
/*      */     try {
/*  815 */       System.arraycopy(this.data, index, out.data, 0, n);
/*  816 */     } catch (IndexOutOfBoundsException e) {
/*  817 */       checkIndex(index);
/*  818 */       checkIndex(index + n - 1);
/*      */     } 
/*  820 */     return out;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntry(int index, T value) {
/*      */     try {
/*  826 */       this.data[index] = value;
/*  827 */     } catch (IndexOutOfBoundsException e) {
/*  828 */       checkIndex(index);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSubVector(int index, FieldVector<T> v) throws OutOfRangeException {
/*      */     try {
/*      */       try {
/*  836 */         set(index, (ArrayFieldVector<T>)v);
/*  837 */       } catch (ClassCastException cce) {
/*  838 */         for (int i = index; i < index + v.getDimension(); i++) {
/*  839 */           this.data[i] = v.getEntry(i - index);
/*      */         }
/*      */       } 
/*  842 */     } catch (IndexOutOfBoundsException e) {
/*  843 */       checkIndex(index);
/*  844 */       checkIndex(index + v.getDimension() - 1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set(int index, ArrayFieldVector<T> v) throws OutOfRangeException {
/*      */     try {
/*  857 */       System.arraycopy(v.data, 0, this.data, index, v.data.length);
/*  858 */     } catch (IndexOutOfBoundsException e) {
/*  859 */       checkIndex(index);
/*  860 */       checkIndex(index + v.data.length - 1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void set(T value) {
/*  866 */     Arrays.fill((Object[])this.data, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public T[] toArray() {
/*  871 */     return (T[])this.data.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkVectorDimensions(FieldVector<T> v) throws DimensionMismatchException {
/*  882 */     checkVectorDimensions(v.getDimension());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkVectorDimensions(int n) throws DimensionMismatchException {
/*  894 */     if (this.data.length != n) {
/*  895 */       throw new DimensionMismatchException(this.data.length, n);
/*      */     }
/*      */   }
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
/*      */   public T walkInDefaultOrder(FieldVectorPreservingVisitor<T> visitor) {
/*  910 */     int dim = getDimension();
/*  911 */     visitor.start(dim, 0, dim - 1);
/*  912 */     for (int i = 0; i < dim; i++) {
/*  913 */       visitor.visit(i, getEntry(i));
/*      */     }
/*  915 */     return visitor.end();
/*      */   }
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
/*      */   public T walkInDefaultOrder(FieldVectorPreservingVisitor<T> visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/*  934 */     checkIndices(start, end);
/*  935 */     visitor.start(getDimension(), start, end);
/*  936 */     for (int i = start; i <= end; i++) {
/*  937 */       visitor.visit(i, getEntry(i));
/*      */     }
/*  939 */     return visitor.end();
/*      */   }
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
/*      */   public T walkInOptimizedOrder(FieldVectorPreservingVisitor<T> visitor) {
/*  955 */     return walkInDefaultOrder(visitor);
/*      */   }
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
/*      */   public T walkInOptimizedOrder(FieldVectorPreservingVisitor<T> visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/*  976 */     return walkInDefaultOrder(visitor, start, end);
/*      */   }
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
/*      */   public T walkInDefaultOrder(FieldVectorChangingVisitor<T> visitor) {
/*  990 */     int dim = getDimension();
/*  991 */     visitor.start(dim, 0, dim - 1);
/*  992 */     for (int i = 0; i < dim; i++) {
/*  993 */       setEntry(i, visitor.visit(i, getEntry(i)));
/*      */     }
/*  995 */     return visitor.end();
/*      */   }
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
/*      */   public T walkInDefaultOrder(FieldVectorChangingVisitor<T> visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/* 1014 */     checkIndices(start, end);
/* 1015 */     visitor.start(getDimension(), start, end);
/* 1016 */     for (int i = start; i <= end; i++) {
/* 1017 */       setEntry(i, visitor.visit(i, getEntry(i)));
/*      */     }
/* 1019 */     return visitor.end();
/*      */   }
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
/*      */   public T walkInOptimizedOrder(FieldVectorChangingVisitor<T> visitor) {
/* 1035 */     return walkInDefaultOrder(visitor);
/*      */   }
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
/*      */   public T walkInOptimizedOrder(FieldVectorChangingVisitor<T> visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/* 1056 */     return walkInDefaultOrder(visitor, start, end);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object other) {
/* 1068 */     if (this == other) {
/* 1069 */       return true;
/*      */     }
/* 1071 */     if (other == null) {
/* 1072 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1077 */       FieldVector<T> rhs = (FieldVector<T>)other;
/* 1078 */       if (this.data.length != rhs.getDimension()) {
/* 1079 */         return false;
/*      */       }
/*      */       
/* 1082 */       for (int i = 0; i < this.data.length; i++) {
/* 1083 */         if (!this.data[i].equals(rhs.getEntry(i))) {
/* 1084 */           return false;
/*      */         }
/*      */       } 
/* 1087 */       return true;
/* 1088 */     } catch (ClassCastException ex) {
/*      */       
/* 1090 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1101 */     int h = 3542;
/* 1102 */     for (T a : this.data) {
/* 1103 */       h ^= a.hashCode();
/*      */     }
/* 1105 */     return h;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkIndex(int index) throws OutOfRangeException {
/* 1115 */     if (index < 0 || index >= getDimension()) {
/* 1116 */       throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(getDimension() - 1));
/*      */     }
/*      */   }
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
/*      */   private void checkIndices(int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/* 1132 */     int dim = getDimension();
/* 1133 */     if (start < 0 || start >= dim) {
/* 1134 */       throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(start), Integer.valueOf(0), Integer.valueOf(dim - 1));
/*      */     }
/*      */     
/* 1137 */     if (end < 0 || end >= dim) {
/* 1138 */       throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(end), Integer.valueOf(0), Integer.valueOf(dim - 1));
/*      */     }
/*      */     
/* 1141 */     if (end < start)
/* 1142 */       throw new NumberIsTooSmallException(LocalizedFormats.INITIAL_ROW_AFTER_FINAL_ROW, Integer.valueOf(end), Integer.valueOf(start), false); 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/ArrayFieldVector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */