/*      */ package org.apache.commons.math3.util;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*      */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*      */ import org.apache.commons.math3.exception.MathInternalError;
/*      */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*      */ import org.apache.commons.math3.exception.NullArgumentException;
/*      */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*      */ import org.apache.commons.math3.exception.util.Localizable;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*      */ public class ResizableDoubleArray
/*      */   implements DoubleArray, Serializable
/*      */ {
/*      */   @Deprecated
/*      */   public static final int ADDITIVE_MODE = 1;
/*      */   @Deprecated
/*      */   public static final int MULTIPLICATIVE_MODE = 0;
/*      */   private static final long serialVersionUID = -3485529955529426875L;
/*      */   private static final int DEFAULT_INITIAL_CAPACITY = 16;
/*      */   private static final double DEFAULT_EXPANSION_FACTOR = 2.0D;
/*      */   private static final double DEFAULT_CONTRACTION_DELTA = 0.5D;
/*  112 */   private double contractionCriterion = 2.5D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  122 */   private double expansionFactor = 2.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  128 */   private ExpansionMode expansionMode = ExpansionMode.MULTIPLICATIVE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double[] internalArray;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  139 */   private int numElements = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  146 */   private int startIndex = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum ExpansionMode
/*      */   {
/*  154 */     MULTIPLICATIVE,
/*      */     
/*  156 */     ADDITIVE;
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
/*      */   public ResizableDoubleArray() {
/*  169 */     this(16);
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
/*      */   public ResizableDoubleArray(int initialCapacity) throws MathIllegalArgumentException {
/*  185 */     this(initialCapacity, 2.0D);
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
/*      */   public ResizableDoubleArray(double[] initialArray) {
/*  207 */     this(16, 2.0D, 2.5D, ExpansionMode.MULTIPLICATIVE, initialArray);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ResizableDoubleArray(int initialCapacity, float expansionFactor) throws MathIllegalArgumentException {
/*  241 */     this(initialCapacity, expansionFactor);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public ResizableDoubleArray(int initialCapacity, double expansionFactor) throws MathIllegalArgumentException {
/*  270 */     this(initialCapacity, expansionFactor, 0.5D + expansionFactor);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ResizableDoubleArray(int initialCapacity, float expansionFactor, float contractionCriteria) throws MathIllegalArgumentException {
/*  301 */     this(initialCapacity, expansionFactor, contractionCriteria);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public ResizableDoubleArray(int initialCapacity, double expansionFactor, double contractionCriterion) throws MathIllegalArgumentException {
/*  330 */     this(initialCapacity, expansionFactor, contractionCriterion, ExpansionMode.MULTIPLICATIVE, null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public ResizableDoubleArray(int initialCapacity, float expansionFactor, float contractionCriteria, int expansionMode) throws MathIllegalArgumentException {
/*  364 */     this(initialCapacity, expansionFactor, contractionCriteria, (expansionMode == 1) ? ExpansionMode.ADDITIVE : ExpansionMode.MULTIPLICATIVE, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  373 */     setExpansionMode(expansionMode);
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
/*      */ 
/*      */   
/*      */   public ResizableDoubleArray(int initialCapacity, double expansionFactor, double contractionCriterion, ExpansionMode expansionMode, double... data) throws MathIllegalArgumentException {
/*  401 */     if (initialCapacity <= 0) {
/*  402 */       throw new NotStrictlyPositiveException(LocalizedFormats.INITIAL_CAPACITY_NOT_POSITIVE, Integer.valueOf(initialCapacity));
/*      */     }
/*      */     
/*  405 */     checkContractExpand(contractionCriterion, expansionFactor);
/*      */     
/*  407 */     this.expansionFactor = expansionFactor;
/*  408 */     this.contractionCriterion = contractionCriterion;
/*  409 */     this.expansionMode = expansionMode;
/*  410 */     this.internalArray = new double[initialCapacity];
/*  411 */     this.numElements = 0;
/*  412 */     this.startIndex = 0;
/*      */     
/*  414 */     if (data != null && data.length > 0) {
/*  415 */       addElements(data);
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
/*      */   public ResizableDoubleArray(ResizableDoubleArray original) throws NullArgumentException {
/*  431 */     MathUtils.checkNotNull(original);
/*  432 */     copy(original, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void addElement(double value) {
/*  441 */     if (this.internalArray.length <= this.startIndex + this.numElements) {
/*  442 */       expand();
/*      */     }
/*  444 */     this.internalArray[this.startIndex + this.numElements++] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void addElements(double[] values) {
/*  454 */     double[] tempArray = new double[this.numElements + values.length + 1];
/*  455 */     System.arraycopy(this.internalArray, this.startIndex, tempArray, 0, this.numElements);
/*  456 */     System.arraycopy(values, 0, tempArray, this.numElements, values.length);
/*  457 */     this.internalArray = tempArray;
/*  458 */     this.startIndex = 0;
/*  459 */     this.numElements += values.length;
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
/*      */   public synchronized double addElementRolling(double value) {
/*  479 */     double discarded = this.internalArray[this.startIndex];
/*      */     
/*  481 */     if (this.startIndex + this.numElements + 1 > this.internalArray.length) {
/*  482 */       expand();
/*      */     }
/*      */     
/*  485 */     this.startIndex++;
/*      */ 
/*      */     
/*  488 */     this.internalArray[this.startIndex + this.numElements - 1] = value;
/*      */ 
/*      */     
/*  491 */     if (shouldContract()) {
/*  492 */       contract();
/*      */     }
/*  494 */     return discarded;
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
/*      */   public synchronized double substituteMostRecentElement(double value) throws MathIllegalStateException {
/*  509 */     if (this.numElements < 1) {
/*  510 */       throw new MathIllegalStateException(LocalizedFormats.CANNOT_SUBSTITUTE_ELEMENT_FROM_EMPTY_ARRAY, new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  514 */     int substIndex = this.startIndex + this.numElements - 1;
/*  515 */     double discarded = this.internalArray[substIndex];
/*      */     
/*  517 */     this.internalArray[substIndex] = value;
/*      */     
/*  519 */     return discarded;
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
/*      */   @Deprecated
/*      */   protected void checkContractExpand(float contraction, float expansion) throws MathIllegalArgumentException {
/*  537 */     checkContractExpand(contraction, expansion);
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
/*      */   protected void checkContractExpand(double contraction, double expansion) throws NumberIsTooSmallException {
/*  556 */     if (contraction < expansion) {
/*  557 */       NumberIsTooSmallException e = new NumberIsTooSmallException(Double.valueOf(contraction), Integer.valueOf(1), true);
/*  558 */       e.getContext().addMessage((Localizable)LocalizedFormats.CONTRACTION_CRITERIA_SMALLER_THAN_EXPANSION_FACTOR, new Object[] { Double.valueOf(contraction), Double.valueOf(expansion) });
/*      */       
/*  560 */       throw e;
/*      */     } 
/*      */     
/*  563 */     if (contraction <= 1.0D) {
/*  564 */       NumberIsTooSmallException e = new NumberIsTooSmallException(Double.valueOf(contraction), Integer.valueOf(1), false);
/*  565 */       e.getContext().addMessage((Localizable)LocalizedFormats.CONTRACTION_CRITERIA_SMALLER_THAN_ONE, new Object[] { Double.valueOf(contraction) });
/*      */       
/*  567 */       throw e;
/*      */     } 
/*      */     
/*  570 */     if (expansion <= 1.0D) {
/*  571 */       NumberIsTooSmallException e = new NumberIsTooSmallException(Double.valueOf(contraction), Integer.valueOf(1), false);
/*  572 */       e.getContext().addMessage((Localizable)LocalizedFormats.EXPANSION_FACTOR_SMALLER_THAN_ONE, new Object[] { Double.valueOf(expansion) });
/*      */       
/*  574 */       throw e;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void clear() {
/*  582 */     this.numElements = 0;
/*  583 */     this.startIndex = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void contract() {
/*  592 */     double[] tempArray = new double[this.numElements + 1];
/*      */ 
/*      */     
/*  595 */     System.arraycopy(this.internalArray, this.startIndex, tempArray, 0, this.numElements);
/*  596 */     this.internalArray = tempArray;
/*      */ 
/*      */     
/*  599 */     this.startIndex = 0;
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
/*      */   public synchronized void discardFrontElements(int i) throws MathIllegalArgumentException {
/*  615 */     discardExtremeElements(i, true);
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
/*      */   public synchronized void discardMostRecentElements(int i) throws MathIllegalArgumentException {
/*  631 */     discardExtremeElements(i, false);
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
/*      */   private synchronized void discardExtremeElements(int i, boolean front) throws MathIllegalArgumentException {
/*  656 */     if (i > this.numElements) {
/*  657 */       throw new MathIllegalArgumentException(LocalizedFormats.TOO_MANY_ELEMENTS_TO_DISCARD_FROM_ARRAY, new Object[] { Integer.valueOf(i), Integer.valueOf(this.numElements) });
/*      */     }
/*      */     
/*  660 */     if (i < 0) {
/*  661 */       throw new MathIllegalArgumentException(LocalizedFormats.CANNOT_DISCARD_NEGATIVE_NUMBER_OF_ELEMENTS, new Object[] { Integer.valueOf(i) });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  666 */     this.numElements -= i;
/*  667 */     if (front) {
/*  668 */       this.startIndex += i;
/*      */     }
/*      */     
/*  671 */     if (shouldContract()) {
/*  672 */       contract();
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void expand() {
/*  691 */     int newSize = 0;
/*  692 */     if (this.expansionMode == ExpansionMode.MULTIPLICATIVE) {
/*  693 */       newSize = (int)FastMath.ceil(this.internalArray.length * this.expansionFactor);
/*      */     } else {
/*  695 */       newSize = (int)(this.internalArray.length + FastMath.round(this.expansionFactor));
/*      */     } 
/*  697 */     double[] tempArray = new double[newSize];
/*      */ 
/*      */     
/*  700 */     System.arraycopy(this.internalArray, 0, tempArray, 0, this.internalArray.length);
/*  701 */     this.internalArray = tempArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void expandTo(int size) {
/*  710 */     double[] tempArray = new double[size];
/*      */     
/*  712 */     System.arraycopy(this.internalArray, 0, tempArray, 0, this.internalArray.length);
/*  713 */     this.internalArray = tempArray;
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
/*      */   @Deprecated
/*      */   public float getContractionCriteria() {
/*  732 */     return (float)getContractionCriterion();
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
/*      */   public double getContractionCriterion() {
/*  749 */     return this.contractionCriterion;
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
/*      */   public synchronized double getElement(int index) {
/*  761 */     if (index >= this.numElements)
/*  762 */       throw new ArrayIndexOutOfBoundsException(index); 
/*  763 */     if (index >= 0) {
/*  764 */       return this.internalArray[this.startIndex + index];
/*      */     }
/*  766 */     throw new ArrayIndexOutOfBoundsException(index);
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
/*      */   public synchronized double[] getElements() {
/*  778 */     double[] elementArray = new double[this.numElements];
/*  779 */     System.arraycopy(this.internalArray, this.startIndex, elementArray, 0, this.numElements);
/*  780 */     return elementArray;
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
/*      */   @Deprecated
/*      */   public float getExpansionFactor() {
/*  798 */     return (float)this.expansionFactor;
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
/*      */   @Deprecated
/*      */   public int getExpansionMode() {
/*  811 */     synchronized (this) {
/*  812 */       switch (this.expansionMode) {
/*      */         case MULTIPLICATIVE:
/*  814 */           return 0;
/*      */         case ADDITIVE:
/*  816 */           return 1;
/*      */       } 
/*  818 */       throw new MathInternalError();
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
/*      */   @Deprecated
/*      */   synchronized int getInternalLength() {
/*  834 */     return this.internalArray.length;
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
/*      */   public int getCapacity() {
/*  847 */     return this.internalArray.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized int getNumElements() {
/*  857 */     return this.numElements;
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
/*      */   @Deprecated
/*      */   public synchronized double[] getInternalValues() {
/*  875 */     return this.internalArray;
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
/*      */   protected double[] getArrayRef() {
/*  898 */     return this.internalArray;
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
/*      */   protected int getStartIndex() {
/*  913 */     return this.startIndex;
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
/*      */   @Deprecated
/*      */   public void setContractionCriteria(float contractionCriteria) throws MathIllegalArgumentException {
/*  927 */     checkContractExpand(contractionCriteria, getExpansionFactor());
/*  928 */     synchronized (this) {
/*  929 */       this.contractionCriterion = contractionCriteria;
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
/*      */   public double compute(MathArrays.Function f) {
/*      */     double[] array;
/*      */     int start;
/*      */     int num;
/*  944 */     synchronized (this) {
/*  945 */       array = this.internalArray;
/*  946 */       start = this.startIndex;
/*  947 */       num = this.numElements;
/*      */     } 
/*  949 */     return f.evaluate(array, start, num);
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
/*      */   public synchronized void setElement(int index, double value) {
/*  964 */     if (index < 0) {
/*  965 */       throw new ArrayIndexOutOfBoundsException(index);
/*      */     }
/*  967 */     if (index + 1 > this.numElements) {
/*  968 */       this.numElements = index + 1;
/*      */     }
/*  970 */     if (this.startIndex + index >= this.internalArray.length) {
/*  971 */       expandTo(this.startIndex + index + 1);
/*      */     }
/*  973 */     this.internalArray[this.startIndex + index] = value;
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
/*      */   @Deprecated
/*      */   public void setExpansionFactor(float expansionFactor) throws MathIllegalArgumentException {
/*  990 */     checkContractExpand(getContractionCriterion(), expansionFactor);
/*      */     
/*  992 */     synchronized (this) {
/*  993 */       this.expansionFactor = expansionFactor;
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
/*      */   @Deprecated
/*      */   public void setExpansionMode(int expansionMode) throws MathIllegalArgumentException {
/* 1008 */     if (expansionMode != 0 && expansionMode != 1)
/*      */     {
/* 1010 */       throw new MathIllegalArgumentException(LocalizedFormats.UNSUPPORTED_EXPANSION_MODE, new Object[] { Integer.valueOf(expansionMode), Integer.valueOf(0), "MULTIPLICATIVE_MODE", Integer.valueOf(1), "ADDITIVE_MODE" });
/*      */     }
/*      */ 
/*      */     
/* 1014 */     synchronized (this) {
/* 1015 */       if (expansionMode == 0) {
/* 1016 */         setExpansionMode(ExpansionMode.MULTIPLICATIVE);
/* 1017 */       } else if (expansionMode == 1) {
/* 1018 */         setExpansionMode(ExpansionMode.ADDITIVE);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void setExpansionMode(ExpansionMode expansionMode) {
/* 1031 */     synchronized (this) {
/* 1032 */       this.expansionMode = expansionMode;
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
/*      */   @Deprecated
/*      */   protected void setInitialCapacity(int initialCapacity) throws MathIllegalArgumentException {}
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
/*      */   public synchronized void setNumElements(int i) throws MathIllegalArgumentException {
/* 1061 */     if (i < 0) {
/* 1062 */       throw new MathIllegalArgumentException(LocalizedFormats.INDEX_NOT_POSITIVE, new Object[] { Integer.valueOf(i) });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1069 */     int newSize = this.startIndex + i;
/* 1070 */     if (newSize > this.internalArray.length) {
/* 1071 */       expandTo(newSize);
/*      */     }
/*      */ 
/*      */     
/* 1075 */     this.numElements = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized boolean shouldContract() {
/* 1085 */     if (this.expansionMode == ExpansionMode.MULTIPLICATIVE) {
/* 1086 */       return ((this.internalArray.length / this.numElements) > this.contractionCriterion);
/*      */     }
/* 1088 */     return ((this.internalArray.length - this.numElements) > this.contractionCriterion);
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
/*      */   @Deprecated
/*      */   public synchronized int start() {
/* 1104 */     return this.startIndex;
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
/*      */   public static void copy(ResizableDoubleArray source, ResizableDoubleArray dest) throws NullArgumentException {
/* 1127 */     MathUtils.checkNotNull(source);
/* 1128 */     MathUtils.checkNotNull(dest);
/* 1129 */     synchronized (source) {
/* 1130 */       synchronized (dest) {
/* 1131 */         dest.contractionCriterion = source.contractionCriterion;
/* 1132 */         dest.expansionFactor = source.expansionFactor;
/* 1133 */         dest.expansionMode = source.expansionMode;
/* 1134 */         dest.internalArray = new double[source.internalArray.length];
/* 1135 */         System.arraycopy(source.internalArray, 0, dest.internalArray, 0, dest.internalArray.length);
/*      */         
/* 1137 */         dest.numElements = source.numElements;
/* 1138 */         dest.startIndex = source.startIndex;
/*      */       } 
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
/*      */   public synchronized ResizableDoubleArray copy() {
/* 1152 */     ResizableDoubleArray result = new ResizableDoubleArray();
/* 1153 */     copy(this, result);
/* 1154 */     return result;
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
/*      */   public boolean equals(Object object) {
/* 1168 */     if (object == this) {
/* 1169 */       return true;
/*      */     }
/* 1171 */     if (!(object instanceof ResizableDoubleArray)) {
/* 1172 */       return false;
/*      */     }
/* 1174 */     synchronized (this) {
/* 1175 */       synchronized (object) {
/* 1176 */         boolean result = true;
/* 1177 */         ResizableDoubleArray other = (ResizableDoubleArray)object;
/* 1178 */         result = (result && other.contractionCriterion == this.contractionCriterion);
/* 1179 */         result = (result && other.expansionFactor == this.expansionFactor);
/* 1180 */         result = (result && other.expansionMode == this.expansionMode);
/* 1181 */         result = (result && other.numElements == this.numElements);
/* 1182 */         result = (result && other.startIndex == this.startIndex);
/* 1183 */         if (!result) {
/* 1184 */           return false;
/*      */         }
/* 1186 */         return Arrays.equals(this.internalArray, other.internalArray);
/*      */       } 
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
/*      */   public synchronized int hashCode() {
/* 1200 */     int[] hashData = new int[6];
/* 1201 */     hashData[0] = Double.valueOf(this.expansionFactor).hashCode();
/* 1202 */     hashData[1] = Double.valueOf(this.contractionCriterion).hashCode();
/* 1203 */     hashData[2] = this.expansionMode.hashCode();
/* 1204 */     hashData[3] = Arrays.hashCode(this.internalArray);
/* 1205 */     hashData[4] = this.numElements;
/* 1206 */     hashData[5] = this.startIndex;
/* 1207 */     return Arrays.hashCode(hashData);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/ResizableDoubleArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */