/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ public class OpenMapRealVector
/*     */   extends SparseRealVector
/*     */   implements Serializable
/*     */ {
/*     */   public static final double DEFAULT_ZERO_TOLERANCE = 1.0E-12D;
/*     */   private static final long serialVersionUID = 8772222695580707260L;
/*     */   private final OpenIntToDoubleHashMap entries;
/*     */   private final int virtualSize;
/*     */   private final double epsilon;
/*     */   
/*     */   public OpenMapRealVector() {
/*  65 */     this(0, 1.0E-12D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector(int dimension) {
/*  74 */     this(dimension, 1.0E-12D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector(int dimension, double epsilon) {
/*  84 */     this.virtualSize = dimension;
/*  85 */     this.entries = new OpenIntToDoubleHashMap(0.0D);
/*  86 */     this.epsilon = epsilon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected OpenMapRealVector(OpenMapRealVector v, int resize) {
/*  96 */     this.virtualSize = v.getDimension() + resize;
/*  97 */     this.entries = new OpenIntToDoubleHashMap(v.entries);
/*  98 */     this.epsilon = v.epsilon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector(int dimension, int expectedSize) {
/* 108 */     this(dimension, expectedSize, 1.0E-12D);
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
/*     */   public OpenMapRealVector(int dimension, int expectedSize, double epsilon) {
/* 120 */     this.virtualSize = dimension;
/* 121 */     this.entries = new OpenIntToDoubleHashMap(expectedSize, 0.0D);
/* 122 */     this.epsilon = epsilon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector(double[] values) {
/* 132 */     this(values, 1.0E-12D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector(double[] values, double epsilon) {
/* 143 */     this.virtualSize = values.length;
/* 144 */     this.entries = new OpenIntToDoubleHashMap(0.0D);
/* 145 */     this.epsilon = epsilon;
/* 146 */     for (int key = 0; key < values.length; key++) {
/* 147 */       double value = values[key];
/* 148 */       if (!isDefaultValue(value)) {
/* 149 */         this.entries.put(key, value);
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
/*     */   public OpenMapRealVector(Double[] values) {
/* 161 */     this(values, 1.0E-12D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector(Double[] values, double epsilon) {
/* 172 */     this.virtualSize = values.length;
/* 173 */     this.entries = new OpenIntToDoubleHashMap(0.0D);
/* 174 */     this.epsilon = epsilon;
/* 175 */     for (int key = 0; key < values.length; key++) {
/* 176 */       double value = values[key].doubleValue();
/* 177 */       if (!isDefaultValue(value)) {
/* 178 */         this.entries.put(key, value);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector(OpenMapRealVector v) {
/* 189 */     this.virtualSize = v.getDimension();
/* 190 */     this.entries = new OpenIntToDoubleHashMap(v.getEntries());
/* 191 */     this.epsilon = v.epsilon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector(RealVector v) {
/* 200 */     this.virtualSize = v.getDimension();
/* 201 */     this.entries = new OpenIntToDoubleHashMap(0.0D);
/* 202 */     this.epsilon = 1.0E-12D;
/* 203 */     for (int key = 0; key < this.virtualSize; key++) {
/* 204 */       double value = v.getEntry(key);
/* 205 */       if (!isDefaultValue(value)) {
/* 206 */         this.entries.put(key, value);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OpenIntToDoubleHashMap getEntries() {
/* 217 */     return this.entries;
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
/*     */   protected boolean isDefaultValue(double value) {
/* 229 */     return (FastMath.abs(value) < this.epsilon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector add(RealVector v) throws DimensionMismatchException {
/* 236 */     checkVectorDimensions(v.getDimension());
/* 237 */     if (v instanceof OpenMapRealVector) {
/* 238 */       return add((OpenMapRealVector)v);
/*     */     }
/* 240 */     return super.add(v);
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
/*     */   public OpenMapRealVector add(OpenMapRealVector v) throws DimensionMismatchException {
/* 254 */     checkVectorDimensions(v.getDimension());
/* 255 */     boolean copyThis = (this.entries.size() > v.entries.size());
/* 256 */     OpenMapRealVector res = copyThis ? copy() : v.copy();
/* 257 */     OpenIntToDoubleHashMap.Iterator iter = copyThis ? v.entries.iterator() : this.entries.iterator();
/* 258 */     OpenIntToDoubleHashMap randomAccess = copyThis ? this.entries : v.entries;
/* 259 */     while (iter.hasNext()) {
/* 260 */       iter.advance();
/* 261 */       int key = iter.key();
/* 262 */       if (randomAccess.containsKey(key)) {
/* 263 */         res.setEntry(key, randomAccess.get(key) + iter.value()); continue;
/*     */       } 
/* 265 */       res.setEntry(key, iter.value());
/*     */     } 
/*     */     
/* 268 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector append(OpenMapRealVector v) {
/* 277 */     OpenMapRealVector res = new OpenMapRealVector(this, v.getDimension());
/* 278 */     OpenIntToDoubleHashMap.Iterator iter = v.entries.iterator();
/* 279 */     while (iter.hasNext()) {
/* 280 */       iter.advance();
/* 281 */       res.setEntry(iter.key() + this.virtualSize, iter.value());
/*     */     } 
/* 283 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector append(RealVector v) {
/* 289 */     if (v instanceof OpenMapRealVector) {
/* 290 */       return append((OpenMapRealVector)v);
/*     */     }
/* 292 */     OpenMapRealVector res = new OpenMapRealVector(this, v.getDimension());
/* 293 */     for (int i = 0; i < v.getDimension(); i++) {
/* 294 */       res.setEntry(i + this.virtualSize, v.getEntry(i));
/*     */     }
/* 296 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector append(double d) {
/* 303 */     OpenMapRealVector res = new OpenMapRealVector(this, 1);
/* 304 */     res.setEntry(this.virtualSize, d);
/* 305 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector copy() {
/* 314 */     return new OpenMapRealVector(this);
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
/*     */   @Deprecated
/*     */   public double dotProduct(OpenMapRealVector v) throws DimensionMismatchException {
/* 337 */     return dotProduct(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector ebeDivide(RealVector v) throws DimensionMismatchException {
/* 344 */     checkVectorDimensions(v.getDimension());
/* 345 */     OpenMapRealVector res = new OpenMapRealVector(this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 351 */     int n = getDimension();
/* 352 */     for (int i = 0; i < n; i++) {
/* 353 */       res.setEntry(i, getEntry(i) / v.getEntry(i));
/*     */     }
/* 355 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector ebeMultiply(RealVector v) throws DimensionMismatchException {
/* 362 */     checkVectorDimensions(v.getDimension());
/* 363 */     OpenMapRealVector res = new OpenMapRealVector(this);
/* 364 */     OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();
/* 365 */     while (iter.hasNext()) {
/* 366 */       iter.advance();
/* 367 */       res.setEntry(iter.key(), iter.value() * v.getEntry(iter.key()));
/*     */     } 
/* 369 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector getSubVector(int index, int n) throws NotPositiveException, OutOfRangeException {
/* 376 */     checkIndex(index);
/* 377 */     if (n < 0) {
/* 378 */       throw new NotPositiveException(LocalizedFormats.NUMBER_OF_ELEMENTS_SHOULD_BE_POSITIVE, Integer.valueOf(n));
/*     */     }
/* 380 */     checkIndex(index + n - 1);
/* 381 */     OpenMapRealVector res = new OpenMapRealVector(n);
/* 382 */     int end = index + n;
/* 383 */     OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();
/* 384 */     while (iter.hasNext()) {
/* 385 */       iter.advance();
/* 386 */       int key = iter.key();
/* 387 */       if (key >= index && key < end) {
/* 388 */         res.setEntry(key - index, iter.value());
/*     */       }
/*     */     } 
/* 391 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimension() {
/* 397 */     return this.virtualSize;
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
/*     */   public double getDistance(OpenMapRealVector v) throws DimensionMismatchException {
/* 409 */     checkVectorDimensions(v.getDimension());
/* 410 */     OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();
/* 411 */     double res = 0.0D;
/* 412 */     while (iter.hasNext()) {
/* 413 */       iter.advance();
/* 414 */       int key = iter.key();
/*     */       
/* 416 */       double delta = iter.value() - v.getEntry(key);
/* 417 */       res += delta * delta;
/*     */     } 
/* 419 */     iter = v.getEntries().iterator();
/* 420 */     while (iter.hasNext()) {
/* 421 */       iter.advance();
/* 422 */       int key = iter.key();
/* 423 */       if (!this.entries.containsKey(key)) {
/* 424 */         double value = iter.value();
/* 425 */         res += value * value;
/*     */       } 
/*     */     } 
/* 428 */     return FastMath.sqrt(res);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistance(RealVector v) throws DimensionMismatchException {
/* 434 */     checkVectorDimensions(v.getDimension());
/* 435 */     if (v instanceof OpenMapRealVector) {
/* 436 */       return getDistance((OpenMapRealVector)v);
/*     */     }
/* 438 */     return super.getDistance(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEntry(int index) throws OutOfRangeException {
/* 445 */     checkIndex(index);
/* 446 */     return this.entries.get(index);
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
/*     */   public double getL1Distance(OpenMapRealVector v) throws DimensionMismatchException {
/* 461 */     checkVectorDimensions(v.getDimension());
/* 462 */     double max = 0.0D;
/* 463 */     OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();
/* 464 */     while (iter.hasNext()) {
/* 465 */       iter.advance();
/* 466 */       double delta = FastMath.abs(iter.value() - v.getEntry(iter.key()));
/* 467 */       max += delta;
/*     */     } 
/* 469 */     iter = v.getEntries().iterator();
/* 470 */     while (iter.hasNext()) {
/* 471 */       iter.advance();
/* 472 */       int key = iter.key();
/* 473 */       if (!this.entries.containsKey(key)) {
/* 474 */         double delta = FastMath.abs(iter.value());
/* 475 */         max += FastMath.abs(delta);
/*     */       } 
/*     */     } 
/* 478 */     return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getL1Distance(RealVector v) throws DimensionMismatchException {
/* 485 */     checkVectorDimensions(v.getDimension());
/* 486 */     if (v instanceof OpenMapRealVector) {
/* 487 */       return getL1Distance((OpenMapRealVector)v);
/*     */     }
/* 489 */     return super.getL1Distance(v);
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
/*     */   private double getLInfDistance(OpenMapRealVector v) throws DimensionMismatchException {
/* 502 */     checkVectorDimensions(v.getDimension());
/* 503 */     double max = 0.0D;
/* 504 */     OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();
/* 505 */     while (iter.hasNext()) {
/* 506 */       iter.advance();
/* 507 */       double delta = FastMath.abs(iter.value() - v.getEntry(iter.key()));
/* 508 */       if (delta > max) {
/* 509 */         max = delta;
/*     */       }
/*     */     } 
/* 512 */     iter = v.getEntries().iterator();
/* 513 */     while (iter.hasNext()) {
/* 514 */       iter.advance();
/* 515 */       int key = iter.key();
/* 516 */       if (!this.entries.containsKey(key) && iter.value() > max) {
/* 517 */         max = iter.value();
/*     */       }
/*     */     } 
/* 520 */     return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLInfDistance(RealVector v) throws DimensionMismatchException {
/* 527 */     checkVectorDimensions(v.getDimension());
/* 528 */     if (v instanceof OpenMapRealVector) {
/* 529 */       return getLInfDistance((OpenMapRealVector)v);
/*     */     }
/* 531 */     return super.getLInfDistance(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInfinite() {
/* 538 */     boolean infiniteFound = false;
/* 539 */     OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();
/* 540 */     while (iter.hasNext()) {
/* 541 */       iter.advance();
/* 542 */       double value = iter.value();
/* 543 */       if (Double.isNaN(value)) {
/* 544 */         return false;
/*     */       }
/* 546 */       if (Double.isInfinite(value)) {
/* 547 */         infiniteFound = true;
/*     */       }
/*     */     } 
/* 550 */     return infiniteFound;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNaN() {
/* 556 */     OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();
/* 557 */     while (iter.hasNext()) {
/* 558 */       iter.advance();
/* 559 */       if (Double.isNaN(iter.value())) {
/* 560 */         return true;
/*     */       }
/*     */     } 
/* 563 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector mapAdd(double d) {
/* 569 */     return copy().mapAddToSelf(d);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector mapAddToSelf(double d) {
/* 575 */     for (int i = 0; i < this.virtualSize; i++) {
/* 576 */       setEntry(i, getEntry(i) + d);
/*     */     }
/* 578 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntry(int index, double value) throws OutOfRangeException {
/* 585 */     checkIndex(index);
/* 586 */     if (!isDefaultValue(value)) {
/* 587 */       this.entries.put(index, value);
/* 588 */     } else if (this.entries.containsKey(index)) {
/* 589 */       this.entries.remove(index);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubVector(int index, RealVector v) throws OutOfRangeException {
/* 597 */     checkIndex(index);
/* 598 */     checkIndex(index + v.getDimension() - 1);
/* 599 */     for (int i = 0; i < v.getDimension(); i++) {
/* 600 */       setEntry(i + index, v.getEntry(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(double value) {
/* 607 */     for (int i = 0; i < this.virtualSize; i++) {
/* 608 */       setEntry(i, value);
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
/*     */   public OpenMapRealVector subtract(OpenMapRealVector v) throws DimensionMismatchException {
/* 621 */     checkVectorDimensions(v.getDimension());
/* 622 */     OpenMapRealVector res = copy();
/* 623 */     OpenIntToDoubleHashMap.Iterator iter = v.getEntries().iterator();
/* 624 */     while (iter.hasNext()) {
/* 625 */       iter.advance();
/* 626 */       int key = iter.key();
/* 627 */       if (this.entries.containsKey(key)) {
/* 628 */         res.setEntry(key, this.entries.get(key) - iter.value()); continue;
/*     */       } 
/* 630 */       res.setEntry(key, -iter.value());
/*     */     } 
/*     */     
/* 633 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector subtract(RealVector v) throws DimensionMismatchException {
/* 640 */     checkVectorDimensions(v.getDimension());
/* 641 */     if (v instanceof OpenMapRealVector) {
/* 642 */       return subtract((OpenMapRealVector)v);
/*     */     }
/* 644 */     return super.subtract(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenMapRealVector unitVector() throws MathArithmeticException {
/* 651 */     OpenMapRealVector res = copy();
/* 652 */     res.unitize();
/* 653 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unitize() throws MathArithmeticException {
/* 659 */     double norm = getNorm();
/* 660 */     if (isDefaultValue(norm)) {
/* 661 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
/*     */     }
/* 663 */     OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();
/* 664 */     while (iter.hasNext()) {
/* 665 */       iter.advance();
/* 666 */       this.entries.put(iter.key(), iter.value() / norm);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] toArray() {
/* 673 */     double[] res = new double[this.virtualSize];
/* 674 */     OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();
/* 675 */     while (iter.hasNext()) {
/* 676 */       iter.advance();
/* 677 */       res[iter.key()] = iter.value();
/*     */     } 
/* 679 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 690 */     int prime = 31;
/* 691 */     int result = 1;
/*     */     
/* 693 */     long temp = Double.doubleToLongBits(this.epsilon);
/* 694 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 695 */     result = 31 * result + this.virtualSize;
/* 696 */     OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();
/* 697 */     while (iter.hasNext()) {
/* 698 */       iter.advance();
/* 699 */       temp = Double.doubleToLongBits(iter.value());
/* 700 */       result = 31 * result + (int)(temp ^ temp >> 32L);
/*     */     } 
/* 702 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 713 */     if (this == obj) {
/* 714 */       return true;
/*     */     }
/* 716 */     if (!(obj instanceof OpenMapRealVector)) {
/* 717 */       return false;
/*     */     }
/* 719 */     OpenMapRealVector other = (OpenMapRealVector)obj;
/* 720 */     if (this.virtualSize != other.virtualSize) {
/* 721 */       return false;
/*     */     }
/* 723 */     if (Double.doubleToLongBits(this.epsilon) != Double.doubleToLongBits(other.epsilon))
/*     */     {
/* 725 */       return false;
/*     */     }
/* 727 */     OpenIntToDoubleHashMap.Iterator iter = this.entries.iterator();
/* 728 */     while (iter.hasNext()) {
/* 729 */       iter.advance();
/* 730 */       double test = other.getEntry(iter.key());
/* 731 */       if (Double.doubleToLongBits(test) != Double.doubleToLongBits(iter.value())) {
/* 732 */         return false;
/*     */       }
/*     */     } 
/* 735 */     iter = other.getEntries().iterator();
/* 736 */     while (iter.hasNext()) {
/* 737 */       iter.advance();
/* 738 */       double test = iter.value();
/* 739 */       if (Double.doubleToLongBits(test) != Double.doubleToLongBits(getEntry(iter.key()))) {
/* 740 */         return false;
/*     */       }
/*     */     } 
/* 743 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSparsity() {
/* 752 */     return this.entries.size() / getDimension();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<RealVector.Entry> sparseIterator() {
/* 758 */     return new OpenMapSparseIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class OpenMapEntry
/*     */     extends RealVector.Entry
/*     */   {
/*     */     private final OpenIntToDoubleHashMap.Iterator iter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected OpenMapEntry(OpenIntToDoubleHashMap.Iterator iter) {
/* 776 */       this.iter = iter;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double getValue() {
/* 782 */       return this.iter.value();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setValue(double value) {
/* 788 */       OpenMapRealVector.this.entries.put(this.iter.key(), value);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getIndex() {
/* 794 */       return this.iter.key();
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
/*     */   protected class OpenMapSparseIterator
/*     */     implements Iterator<RealVector.Entry>
/*     */   {
/* 812 */     private final OpenIntToDoubleHashMap.Iterator iter = OpenMapRealVector.this.entries.iterator();
/* 813 */     private final RealVector.Entry current = new OpenMapRealVector.OpenMapEntry(this.iter);
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 818 */       return this.iter.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public RealVector.Entry next() {
/* 823 */       this.iter.advance();
/* 824 */       return this.current;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 829 */       throw new UnsupportedOperationException("Not supported");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/OpenMapRealVector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */