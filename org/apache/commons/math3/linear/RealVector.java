/*      */ package org.apache.commons.math3.linear;
/*      */ 
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import org.apache.commons.math3.analysis.BivariateFunction;
/*      */ import org.apache.commons.math3.analysis.FunctionUtils;
/*      */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*      */ import org.apache.commons.math3.analysis.function.Add;
/*      */ import org.apache.commons.math3.analysis.function.Divide;
/*      */ import org.apache.commons.math3.analysis.function.Multiply;
/*      */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*      */ import org.apache.commons.math3.exception.MathArithmeticException;
/*      */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
/*      */ import org.apache.commons.math3.exception.NotPositiveException;
/*      */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*      */ import org.apache.commons.math3.exception.OutOfRangeException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class RealVector
/*      */ {
/*      */   public abstract int getDimension();
/*      */   
/*      */   public abstract double getEntry(int paramInt) throws OutOfRangeException;
/*      */   
/*      */   public abstract void setEntry(int paramInt, double paramDouble) throws OutOfRangeException;
/*      */   
/*      */   public void addToEntry(int index, double increment) throws OutOfRangeException {
/*   98 */     setEntry(index, getEntry(index) + increment);
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
/*      */   public abstract RealVector append(RealVector paramRealVector);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract RealVector append(double paramDouble);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract RealVector getSubVector(int paramInt1, int paramInt2) throws NotPositiveException, OutOfRangeException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void setSubVector(int paramInt, RealVector paramRealVector) throws OutOfRangeException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean isNaN();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract boolean isInfinite();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkVectorDimensions(RealVector v) throws DimensionMismatchException {
/*  164 */     checkVectorDimensions(v.getDimension());
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
/*  176 */     int d = getDimension();
/*  177 */     if (d != n) {
/*  178 */       throw new DimensionMismatchException(d, n);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkIndex(int index) throws OutOfRangeException {
/*  189 */     if (index < 0 || index >= getDimension())
/*      */     {
/*  191 */       throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(getDimension() - 1));
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
/*      */   protected void checkIndices(int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/*  207 */     int dim = getDimension();
/*  208 */     if (start < 0 || start >= dim) {
/*  209 */       throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(start), Integer.valueOf(0), Integer.valueOf(dim - 1));
/*      */     }
/*      */     
/*  212 */     if (end < 0 || end >= dim) {
/*  213 */       throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(end), Integer.valueOf(0), Integer.valueOf(dim - 1));
/*      */     }
/*      */     
/*  216 */     if (end < start)
/*      */     {
/*  218 */       throw new NumberIsTooSmallException(LocalizedFormats.INITIAL_ROW_AFTER_FINAL_ROW, Integer.valueOf(end), Integer.valueOf(start), false);
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
/*      */   public RealVector add(RealVector v) throws DimensionMismatchException {
/*  233 */     checkVectorDimensions(v);
/*  234 */     RealVector result = v.copy();
/*  235 */     Iterator<Entry> it = iterator();
/*  236 */     while (it.hasNext()) {
/*  237 */       Entry e = it.next();
/*  238 */       int index = e.getIndex();
/*  239 */       result.setEntry(index, e.getValue() + result.getEntry(index));
/*      */     } 
/*  241 */     return result;
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
/*      */   public RealVector subtract(RealVector v) throws DimensionMismatchException {
/*  254 */     checkVectorDimensions(v);
/*  255 */     RealVector result = v.mapMultiply(-1.0D);
/*  256 */     Iterator<Entry> it = iterator();
/*  257 */     while (it.hasNext()) {
/*  258 */       Entry e = it.next();
/*  259 */       int index = e.getIndex();
/*  260 */       result.setEntry(index, e.getValue() + result.getEntry(index));
/*      */     } 
/*  262 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector mapAdd(double d) {
/*  273 */     return copy().mapAddToSelf(d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector mapAddToSelf(double d) {
/*  284 */     if (d != 0.0D) {
/*  285 */       return mapToSelf(FunctionUtils.fix2ndArgument((BivariateFunction)new Add(), d));
/*      */     }
/*  287 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract RealVector copy();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double dotProduct(RealVector v) throws DimensionMismatchException {
/*  306 */     checkVectorDimensions(v);
/*  307 */     double d = 0.0D;
/*  308 */     int n = getDimension();
/*  309 */     for (int i = 0; i < n; i++) {
/*  310 */       d += getEntry(i) * v.getEntry(i);
/*      */     }
/*  312 */     return d;
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
/*      */   public double cosine(RealVector v) throws DimensionMismatchException, MathArithmeticException {
/*  328 */     double norm = getNorm();
/*  329 */     double vNorm = v.getNorm();
/*      */     
/*  331 */     if (norm == 0.0D || vNorm == 0.0D)
/*      */     {
/*  333 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
/*      */     }
/*  335 */     return dotProduct(v) / norm * vNorm;
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
/*      */   public abstract RealVector ebeDivide(RealVector paramRealVector) throws DimensionMismatchException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract RealVector ebeMultiply(RealVector paramRealVector) throws DimensionMismatchException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistance(RealVector v) throws DimensionMismatchException {
/*  375 */     checkVectorDimensions(v);
/*  376 */     double d = 0.0D;
/*  377 */     Iterator<Entry> it = iterator();
/*  378 */     while (it.hasNext()) {
/*  379 */       Entry e = it.next();
/*  380 */       double diff = e.getValue() - v.getEntry(e.getIndex());
/*  381 */       d += diff * diff;
/*      */     } 
/*  383 */     return FastMath.sqrt(d);
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
/*      */   public double getNorm() {
/*  397 */     double sum = 0.0D;
/*  398 */     Iterator<Entry> it = iterator();
/*  399 */     while (it.hasNext()) {
/*  400 */       Entry e = it.next();
/*  401 */       double value = e.getValue();
/*  402 */       sum += value * value;
/*      */     } 
/*  404 */     return FastMath.sqrt(sum);
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
/*      */   public double getL1Norm() {
/*  418 */     double norm = 0.0D;
/*  419 */     Iterator<Entry> it = iterator();
/*  420 */     while (it.hasNext()) {
/*  421 */       Entry e = it.next();
/*  422 */       norm += FastMath.abs(e.getValue());
/*      */     } 
/*  424 */     return norm;
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
/*      */   public double getLInfNorm() {
/*  438 */     double norm = 0.0D;
/*  439 */     Iterator<Entry> it = iterator();
/*  440 */     while (it.hasNext()) {
/*  441 */       Entry e = it.next();
/*  442 */       norm = FastMath.max(norm, FastMath.abs(e.getValue()));
/*      */     } 
/*  444 */     return norm;
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
/*      */   public double getL1Distance(RealVector v) throws DimensionMismatchException {
/*  460 */     checkVectorDimensions(v);
/*  461 */     double d = 0.0D;
/*  462 */     Iterator<Entry> it = iterator();
/*  463 */     while (it.hasNext()) {
/*  464 */       Entry e = it.next();
/*  465 */       d += FastMath.abs(e.getValue() - v.getEntry(e.getIndex()));
/*      */     } 
/*  467 */     return d;
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
/*      */   public double getLInfDistance(RealVector v) throws DimensionMismatchException {
/*  486 */     checkVectorDimensions(v);
/*  487 */     double d = 0.0D;
/*  488 */     Iterator<Entry> it = iterator();
/*  489 */     while (it.hasNext()) {
/*  490 */       Entry e = it.next();
/*  491 */       d = FastMath.max(FastMath.abs(e.getValue() - v.getEntry(e.getIndex())), d);
/*      */     } 
/*  493 */     return d;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinIndex() {
/*  503 */     int minIndex = -1;
/*  504 */     double minValue = Double.POSITIVE_INFINITY;
/*  505 */     Iterator<Entry> iterator = iterator();
/*  506 */     while (iterator.hasNext()) {
/*  507 */       Entry entry = iterator.next();
/*  508 */       if (entry.getValue() <= minValue) {
/*  509 */         minIndex = entry.getIndex();
/*  510 */         minValue = entry.getValue();
/*      */       } 
/*      */     } 
/*  513 */     return minIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMinValue() {
/*  523 */     int minIndex = getMinIndex();
/*  524 */     return (minIndex < 0) ? Double.NaN : getEntry(minIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxIndex() {
/*  534 */     int maxIndex = -1;
/*  535 */     double maxValue = Double.NEGATIVE_INFINITY;
/*  536 */     Iterator<Entry> iterator = iterator();
/*  537 */     while (iterator.hasNext()) {
/*  538 */       Entry entry = iterator.next();
/*  539 */       if (entry.getValue() >= maxValue) {
/*  540 */         maxIndex = entry.getIndex();
/*  541 */         maxValue = entry.getValue();
/*      */       } 
/*      */     } 
/*  544 */     return maxIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMaxValue() {
/*  554 */     int maxIndex = getMaxIndex();
/*  555 */     return (maxIndex < 0) ? Double.NaN : getEntry(maxIndex);
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
/*      */   public RealVector mapMultiply(double d) {
/*  567 */     return copy().mapMultiplyToSelf(d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector mapMultiplyToSelf(double d) {
/*  578 */     return mapToSelf(FunctionUtils.fix2ndArgument((BivariateFunction)new Multiply(), d));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector mapSubtract(double d) {
/*  589 */     return copy().mapSubtractToSelf(d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector mapSubtractToSelf(double d) {
/*  600 */     return mapAddToSelf(-d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector mapDivide(double d) {
/*  611 */     return copy().mapDivideToSelf(d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector mapDivideToSelf(double d) {
/*  622 */     return mapToSelf(FunctionUtils.fix2ndArgument((BivariateFunction)new Divide(), d));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealMatrix outerProduct(RealVector v) {
/*      */     RealMatrix product;
/*  632 */     int m = getDimension();
/*  633 */     int n = v.getDimension();
/*      */     
/*  635 */     if (v instanceof SparseRealVector || this instanceof SparseRealVector) {
/*  636 */       product = new OpenMapRealMatrix(m, n);
/*      */     } else {
/*  638 */       product = new Array2DRowRealMatrix(m, n);
/*      */     } 
/*  640 */     for (int i = 0; i < m; i++) {
/*  641 */       for (int j = 0; j < n; j++) {
/*  642 */         product.setEntry(i, j, getEntry(i) * v.getEntry(j));
/*      */       }
/*      */     } 
/*  645 */     return product;
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
/*      */   public RealVector projection(RealVector v) throws DimensionMismatchException, MathArithmeticException {
/*  660 */     double norm2 = v.dotProduct(v);
/*  661 */     if (norm2 == 0.0D) {
/*  662 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
/*      */     }
/*  664 */     return v.mapMultiply(dotProduct(v) / v.dotProduct(v));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set(double value) {
/*  673 */     Iterator<Entry> it = iterator();
/*  674 */     while (it.hasNext()) {
/*  675 */       Entry e = it.next();
/*  676 */       e.setValue(value);
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
/*      */   public double[] toArray() {
/*  688 */     int dim = getDimension();
/*  689 */     double[] values = new double[dim];
/*  690 */     for (int i = 0; i < dim; i++) {
/*  691 */       values[i] = getEntry(i);
/*      */     }
/*  693 */     return values;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RealVector unitVector() throws MathArithmeticException {
/*  704 */     double norm = getNorm();
/*  705 */     if (norm == 0.0D) {
/*  706 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
/*      */     }
/*  708 */     return mapDivide(norm);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unitize() throws MathArithmeticException {
/*  718 */     double norm = getNorm();
/*  719 */     if (norm == 0.0D) {
/*  720 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
/*      */     }
/*  722 */     mapDivideToSelf(getNorm());
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
/*      */   public Iterator<Entry> sparseIterator() {
/*  738 */     return new SparseEntryIterator();
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
/*      */   public Iterator<Entry> iterator() {
/*  752 */     final int dim = getDimension();
/*  753 */     return new Iterator<Entry>()
/*      */       {
/*      */         
/*  756 */         private int i = 0;
/*      */ 
/*      */         
/*  759 */         private RealVector.Entry e = new RealVector.Entry();
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  763 */           return (this.i < dim);
/*      */         }
/*      */ 
/*      */         
/*      */         public RealVector.Entry next() {
/*  768 */           if (this.i < dim) {
/*  769 */             this.e.setIndex(this.i++);
/*  770 */             return this.e;
/*      */           } 
/*  772 */           throw new NoSuchElementException();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void remove() throws MathUnsupportedOperationException {
/*  782 */           throw new MathUnsupportedOperationException();
/*      */         }
/*      */       };
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
/*      */   public RealVector map(UnivariateFunction function) {
/*  798 */     return copy().mapToSelf(function);
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
/*      */   public RealVector mapToSelf(UnivariateFunction function) {
/*  815 */     Iterator<Entry> it = iterator();
/*  816 */     while (it.hasNext()) {
/*  817 */       Entry e = it.next();
/*  818 */       e.setValue(function.value(e.getValue()));
/*      */     } 
/*  820 */     return this;
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
/*      */   public RealVector combine(double a, double b, RealVector y) throws DimensionMismatchException {
/*  838 */     return copy().combineToSelf(a, b, y);
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
/*      */   public RealVector combineToSelf(double a, double b, RealVector y) throws DimensionMismatchException {
/*  855 */     checkVectorDimensions(y);
/*  856 */     for (int i = 0; i < getDimension(); i++) {
/*  857 */       double xi = getEntry(i);
/*  858 */       double yi = y.getEntry(i);
/*  859 */       setEntry(i, a * xi + b * yi);
/*      */     } 
/*  861 */     return this;
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
/*      */   public double walkInDefaultOrder(RealVectorPreservingVisitor visitor) {
/*  875 */     int dim = getDimension();
/*  876 */     visitor.start(dim, 0, dim - 1);
/*  877 */     for (int i = 0; i < dim; i++) {
/*  878 */       visitor.visit(i, getEntry(i));
/*      */     }
/*  880 */     return visitor.end();
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
/*      */   public double walkInDefaultOrder(RealVectorPreservingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/*  899 */     checkIndices(start, end);
/*  900 */     visitor.start(getDimension(), start, end);
/*  901 */     for (int i = start; i <= end; i++) {
/*  902 */       visitor.visit(i, getEntry(i));
/*      */     }
/*  904 */     return visitor.end();
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
/*      */   public double walkInOptimizedOrder(RealVectorPreservingVisitor visitor) {
/*  920 */     return walkInDefaultOrder(visitor);
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
/*      */   public double walkInOptimizedOrder(RealVectorPreservingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/*  941 */     return walkInDefaultOrder(visitor, start, end);
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
/*      */   public double walkInDefaultOrder(RealVectorChangingVisitor visitor) {
/*  955 */     int dim = getDimension();
/*  956 */     visitor.start(dim, 0, dim - 1);
/*  957 */     for (int i = 0; i < dim; i++) {
/*  958 */       setEntry(i, visitor.visit(i, getEntry(i)));
/*      */     }
/*  960 */     return visitor.end();
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
/*      */   public double walkInDefaultOrder(RealVectorChangingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/*  979 */     checkIndices(start, end);
/*  980 */     visitor.start(getDimension(), start, end);
/*  981 */     for (int i = start; i <= end; i++) {
/*  982 */       setEntry(i, visitor.visit(i, getEntry(i)));
/*      */     }
/*  984 */     return visitor.end();
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
/*      */   public double walkInOptimizedOrder(RealVectorChangingVisitor visitor) {
/* 1000 */     return walkInDefaultOrder(visitor);
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
/*      */   public double walkInOptimizedOrder(RealVectorChangingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/* 1021 */     return walkInDefaultOrder(visitor, start, end);
/*      */   }
/*      */ 
/*      */   
/*      */   protected class Entry
/*      */   {
/*      */     private int index;
/*      */ 
/*      */     
/*      */     public Entry() {
/* 1031 */       setIndex(0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getValue() {
/* 1040 */       return RealVector.this.getEntry(getIndex());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValue(double value) {
/* 1049 */       RealVector.this.setEntry(getIndex(), value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getIndex() {
/* 1058 */       return this.index;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setIndex(int index) {
/* 1067 */       this.index = index;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object other) throws MathUnsupportedOperationException {
/* 1096 */     throw new MathUnsupportedOperationException();
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
/*      */   public int hashCode() throws MathUnsupportedOperationException {
/* 1109 */     throw new MathUnsupportedOperationException();
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
/*      */   protected class SparseEntryIterator
/*      */     implements Iterator<Entry>
/*      */   {
/* 1136 */     private final int dim = RealVector.this.getDimension();
/* 1137 */     private RealVector.Entry current = new RealVector.Entry();
/* 1138 */     private RealVector.Entry next = new RealVector.Entry(); protected SparseEntryIterator() {
/* 1139 */       if (this.next.getValue() == 0.0D) {
/* 1140 */         advance(this.next);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void advance(RealVector.Entry e) {
/* 1150 */       if (e == null) {
/*      */         return;
/*      */       }
/*      */       do {
/* 1154 */         e.setIndex(e.getIndex() + 1);
/* 1155 */       } while (e.getIndex() < this.dim && e.getValue() == 0.0D);
/* 1156 */       if (e.getIndex() >= this.dim) {
/* 1157 */         e.setIndex(-1);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1163 */       return (this.next.getIndex() >= 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public RealVector.Entry next() {
/* 1168 */       int index = this.next.getIndex();
/* 1169 */       if (index < 0) {
/* 1170 */         throw new NoSuchElementException();
/*      */       }
/* 1172 */       this.current.setIndex(index);
/* 1173 */       advance(this.next);
/* 1174 */       return this.current;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void remove() throws MathUnsupportedOperationException {
/* 1183 */       throw new MathUnsupportedOperationException();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RealVector unmodifiableRealVector(final RealVector v) {
/* 1215 */     return new RealVector()
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector mapToSelf(UnivariateFunction function) throws MathUnsupportedOperationException
/*      */         {
/* 1224 */           throw new MathUnsupportedOperationException();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector map(UnivariateFunction function) {
/* 1230 */           return v.map(function);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public Iterator<RealVector.Entry> iterator() {
/* 1236 */           final Iterator<RealVector.Entry> i = v.iterator();
/* 1237 */           return new Iterator<RealVector.Entry>()
/*      */             {
/* 1239 */               private final RealVector.null.UnmodifiableEntry e = new RealVector.null.UnmodifiableEntry();
/*      */ 
/*      */               
/*      */               public boolean hasNext() {
/* 1243 */                 return i.hasNext();
/*      */               }
/*      */ 
/*      */               
/*      */               public RealVector.Entry next() {
/* 1248 */                 this.e.setIndex(((RealVector.Entry)i.next()).getIndex());
/* 1249 */                 return this.e;
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               public void remove() throws MathUnsupportedOperationException {
/* 1259 */                 throw new MathUnsupportedOperationException();
/*      */               }
/*      */             };
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public Iterator<RealVector.Entry> sparseIterator() {
/* 1267 */           final Iterator<RealVector.Entry> i = v.sparseIterator();
/*      */           
/* 1269 */           return new Iterator<RealVector.Entry>()
/*      */             {
/* 1271 */               private final RealVector.null.UnmodifiableEntry e = new RealVector.null.UnmodifiableEntry();
/*      */ 
/*      */               
/*      */               public boolean hasNext() {
/* 1275 */                 return i.hasNext();
/*      */               }
/*      */ 
/*      */               
/*      */               public RealVector.Entry next() {
/* 1280 */                 this.e.setIndex(((RealVector.Entry)i.next()).getIndex());
/* 1281 */                 return this.e;
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               public void remove() throws MathUnsupportedOperationException {
/* 1292 */                 throw new MathUnsupportedOperationException();
/*      */               }
/*      */             };
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector copy() {
/* 1300 */           return v.copy();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector add(RealVector w) throws DimensionMismatchException {
/* 1307 */           return v.add(w);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector subtract(RealVector w) throws DimensionMismatchException {
/* 1314 */           return v.subtract(w);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector mapAdd(double d) {
/* 1320 */           return v.mapAdd(d);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector mapAddToSelf(double d) throws MathUnsupportedOperationException {
/* 1332 */           throw new MathUnsupportedOperationException();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector mapSubtract(double d) {
/* 1338 */           return v.mapSubtract(d);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector mapSubtractToSelf(double d) throws MathUnsupportedOperationException {
/* 1350 */           throw new MathUnsupportedOperationException();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector mapMultiply(double d) {
/* 1356 */           return v.mapMultiply(d);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector mapMultiplyToSelf(double d) throws MathUnsupportedOperationException {
/* 1368 */           throw new MathUnsupportedOperationException();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector mapDivide(double d) {
/* 1374 */           return v.mapDivide(d);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector mapDivideToSelf(double d) throws MathUnsupportedOperationException {
/* 1386 */           throw new MathUnsupportedOperationException();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector ebeMultiply(RealVector w) throws DimensionMismatchException {
/* 1393 */           return v.ebeMultiply(w);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector ebeDivide(RealVector w) throws DimensionMismatchException {
/* 1400 */           return v.ebeDivide(w);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public double dotProduct(RealVector w) throws DimensionMismatchException {
/* 1407 */           return v.dotProduct(w);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public double cosine(RealVector w) throws DimensionMismatchException, MathArithmeticException {
/* 1414 */           return v.cosine(w);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public double getNorm() {
/* 1420 */           return v.getNorm();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public double getL1Norm() {
/* 1426 */           return v.getL1Norm();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public double getLInfNorm() {
/* 1432 */           return v.getLInfNorm();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public double getDistance(RealVector w) throws DimensionMismatchException {
/* 1439 */           return v.getDistance(w);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public double getL1Distance(RealVector w) throws DimensionMismatchException {
/* 1446 */           return v.getL1Distance(w);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public double getLInfDistance(RealVector w) throws DimensionMismatchException {
/* 1453 */           return v.getLInfDistance(w);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector unitVector() throws MathArithmeticException {
/* 1459 */           return v.unitVector();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void unitize() throws MathUnsupportedOperationException {
/* 1470 */           throw new MathUnsupportedOperationException();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public RealMatrix outerProduct(RealVector w) {
/* 1476 */           return v.outerProduct(w);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public double getEntry(int index) throws OutOfRangeException {
/* 1482 */           return v.getEntry(index);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void setEntry(int index, double value) throws MathUnsupportedOperationException {
/* 1494 */           throw new MathUnsupportedOperationException();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void addToEntry(int index, double value) throws MathUnsupportedOperationException {
/* 1506 */           throw new MathUnsupportedOperationException();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public int getDimension() {
/* 1512 */           return v.getDimension();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector append(RealVector w) {
/* 1518 */           return v.append(w);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector append(double d) {
/* 1524 */           return v.append(d);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector getSubVector(int index, int n) throws OutOfRangeException, NotPositiveException {
/* 1531 */           return v.getSubVector(index, n);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void setSubVector(int index, RealVector w) throws MathUnsupportedOperationException {
/* 1543 */           throw new MathUnsupportedOperationException();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void set(double value) throws MathUnsupportedOperationException {
/* 1555 */           throw new MathUnsupportedOperationException();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public double[] toArray() {
/* 1561 */           return v.toArray();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public boolean isNaN() {
/* 1567 */           return v.isNaN();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public boolean isInfinite() {
/* 1573 */           return v.isInfinite();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector combine(double a, double b, RealVector y) throws DimensionMismatchException {
/* 1580 */           return v.combine(a, b, y);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public RealVector combineToSelf(double a, double b, RealVector y) throws MathUnsupportedOperationException {
/* 1592 */           throw new MathUnsupportedOperationException();
/*      */         }
/*      */ 
/*      */         
/*      */         class UnmodifiableEntry
/*      */           extends RealVector.Entry
/*      */         {
/*      */           public double getValue() {
/* 1600 */             return v.getEntry(getIndex());
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void setValue(double value) throws MathUnsupportedOperationException {
/* 1612 */             throw new MathUnsupportedOperationException();
/*      */           }
/*      */         }
/*      */       };
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/RealVector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */