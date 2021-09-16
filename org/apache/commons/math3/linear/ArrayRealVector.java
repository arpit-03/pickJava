/*     */ package org.apache.commons.math3.linear;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArrayRealVector
/*     */   extends RealVector
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1097961340710804027L;
/*  42 */   private static final RealVectorFormat DEFAULT_FORMAT = RealVectorFormat.getInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector() {
/*  56 */     this.data = new double[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector(int size) {
/*  65 */     this.data = new double[size];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector(int size, double preset) {
/*  75 */     this.data = new double[size];
/*  76 */     Arrays.fill(this.data, preset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector(double[] d) {
/*  85 */     this.data = (double[])d.clone();
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
/*     */   public ArrayRealVector(double[] d, boolean copyArray) throws NullArgumentException {
/* 104 */     if (d == null) {
/* 105 */       throw new NullArgumentException();
/*     */     }
/* 107 */     this.data = copyArray ? (double[])d.clone() : d;
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
/*     */   public ArrayRealVector(double[] d, int pos, int size) throws NullArgumentException, NumberIsTooLargeException {
/* 122 */     if (d == null) {
/* 123 */       throw new NullArgumentException();
/*     */     }
/* 125 */     if (d.length < pos + size) {
/* 126 */       throw new NumberIsTooLargeException(Integer.valueOf(pos + size), Integer.valueOf(d.length), true);
/*     */     }
/* 128 */     this.data = new double[size];
/* 129 */     System.arraycopy(d, pos, this.data, 0, size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector(Double[] d) {
/* 138 */     this.data = new double[d.length];
/* 139 */     for (int i = 0; i < d.length; i++) {
/* 140 */       this.data[i] = d[i].doubleValue();
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
/*     */   public ArrayRealVector(Double[] d, int pos, int size) throws NullArgumentException, NumberIsTooLargeException {
/* 156 */     if (d == null) {
/* 157 */       throw new NullArgumentException();
/*     */     }
/* 159 */     if (d.length < pos + size) {
/* 160 */       throw new NumberIsTooLargeException(Integer.valueOf(pos + size), Integer.valueOf(d.length), true);
/*     */     }
/* 162 */     this.data = new double[size];
/* 163 */     for (int i = pos; i < pos + size; i++) {
/* 164 */       this.data[i - pos] = d[i].doubleValue();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector(RealVector v) throws NullArgumentException {
/* 175 */     if (v == null) {
/* 176 */       throw new NullArgumentException();
/*     */     }
/* 178 */     this.data = new double[v.getDimension()];
/* 179 */     for (int i = 0; i < this.data.length; i++) {
/* 180 */       this.data[i] = v.getEntry(i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector(ArrayRealVector v) throws NullArgumentException {
/* 191 */     this(v, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector(ArrayRealVector v, boolean deep) {
/* 202 */     this.data = deep ? (double[])v.data.clone() : v.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector(ArrayRealVector v1, ArrayRealVector v2) {
/* 211 */     this.data = new double[v1.data.length + v2.data.length];
/* 212 */     System.arraycopy(v1.data, 0, this.data, 0, v1.data.length);
/* 213 */     System.arraycopy(v2.data, 0, this.data, v1.data.length, v2.data.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector(ArrayRealVector v1, RealVector v2) {
/* 222 */     int l1 = v1.data.length;
/* 223 */     int l2 = v2.getDimension();
/* 224 */     this.data = new double[l1 + l2];
/* 225 */     System.arraycopy(v1.data, 0, this.data, 0, l1);
/* 226 */     for (int i = 0; i < l2; i++) {
/* 227 */       this.data[l1 + i] = v2.getEntry(i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector(RealVector v1, ArrayRealVector v2) {
/* 237 */     int l1 = v1.getDimension();
/* 238 */     int l2 = v2.data.length;
/* 239 */     this.data = new double[l1 + l2];
/* 240 */     for (int i = 0; i < l1; i++) {
/* 241 */       this.data[i] = v1.getEntry(i);
/*     */     }
/* 243 */     System.arraycopy(v2.data, 0, this.data, l1, l2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector(ArrayRealVector v1, double[] v2) {
/* 252 */     int l1 = v1.getDimension();
/* 253 */     int l2 = v2.length;
/* 254 */     this.data = new double[l1 + l2];
/* 255 */     System.arraycopy(v1.data, 0, this.data, 0, l1);
/* 256 */     System.arraycopy(v2, 0, this.data, l1, l2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector(double[] v1, ArrayRealVector v2) {
/* 265 */     int l1 = v1.length;
/* 266 */     int l2 = v2.getDimension();
/* 267 */     this.data = new double[l1 + l2];
/* 268 */     System.arraycopy(v1, 0, this.data, 0, l1);
/* 269 */     System.arraycopy(v2.data, 0, this.data, l1, l2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector(double[] v1, double[] v2) {
/* 278 */     int l1 = v1.length;
/* 279 */     int l2 = v2.length;
/* 280 */     this.data = new double[l1 + l2];
/* 281 */     System.arraycopy(v1, 0, this.data, 0, l1);
/* 282 */     System.arraycopy(v2, 0, this.data, l1, l2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector copy() {
/* 288 */     return new ArrayRealVector(this, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector add(RealVector v) throws DimensionMismatchException {
/* 295 */     if (v instanceof ArrayRealVector) {
/* 296 */       double[] vData = ((ArrayRealVector)v).data;
/* 297 */       int dim = vData.length;
/* 298 */       checkVectorDimensions(dim);
/* 299 */       ArrayRealVector result = new ArrayRealVector(dim);
/* 300 */       double[] resultData = result.data;
/* 301 */       for (int i = 0; i < dim; i++) {
/* 302 */         resultData[i] = this.data[i] + vData[i];
/*     */       }
/* 304 */       return result;
/*     */     } 
/* 306 */     checkVectorDimensions(v);
/* 307 */     double[] out = (double[])this.data.clone();
/* 308 */     Iterator<RealVector.Entry> it = v.iterator();
/* 309 */     while (it.hasNext()) {
/* 310 */       RealVector.Entry e = it.next();
/* 311 */       out[e.getIndex()] = out[e.getIndex()] + e.getValue();
/*     */     } 
/* 313 */     return new ArrayRealVector(out, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector subtract(RealVector v) throws DimensionMismatchException {
/* 321 */     if (v instanceof ArrayRealVector) {
/* 322 */       double[] vData = ((ArrayRealVector)v).data;
/* 323 */       int dim = vData.length;
/* 324 */       checkVectorDimensions(dim);
/* 325 */       ArrayRealVector result = new ArrayRealVector(dim);
/* 326 */       double[] resultData = result.data;
/* 327 */       for (int i = 0; i < dim; i++) {
/* 328 */         resultData[i] = this.data[i] - vData[i];
/*     */       }
/* 330 */       return result;
/*     */     } 
/* 332 */     checkVectorDimensions(v);
/* 333 */     double[] out = (double[])this.data.clone();
/* 334 */     Iterator<RealVector.Entry> it = v.iterator();
/* 335 */     while (it.hasNext()) {
/* 336 */       RealVector.Entry e = it.next();
/* 337 */       out[e.getIndex()] = out[e.getIndex()] - e.getValue();
/*     */     } 
/* 339 */     return new ArrayRealVector(out, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector map(UnivariateFunction function) {
/* 346 */     return copy().mapToSelf(function);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector mapToSelf(UnivariateFunction function) {
/* 352 */     for (int i = 0; i < this.data.length; i++) {
/* 353 */       this.data[i] = function.value(this.data[i]);
/*     */     }
/* 355 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector mapAddToSelf(double d) {
/* 361 */     for (int i = 0; i < this.data.length; i++) {
/* 362 */       this.data[i] = this.data[i] + d;
/*     */     }
/* 364 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector mapSubtractToSelf(double d) {
/* 370 */     for (int i = 0; i < this.data.length; i++) {
/* 371 */       this.data[i] = this.data[i] - d;
/*     */     }
/* 373 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector mapMultiplyToSelf(double d) {
/* 379 */     for (int i = 0; i < this.data.length; i++) {
/* 380 */       this.data[i] = this.data[i] * d;
/*     */     }
/* 382 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector mapDivideToSelf(double d) {
/* 388 */     for (int i = 0; i < this.data.length; i++) {
/* 389 */       this.data[i] = this.data[i] / d;
/*     */     }
/* 391 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector ebeMultiply(RealVector v) throws DimensionMismatchException {
/* 398 */     if (v instanceof ArrayRealVector) {
/* 399 */       double[] vData = ((ArrayRealVector)v).data;
/* 400 */       int dim = vData.length;
/* 401 */       checkVectorDimensions(dim);
/* 402 */       ArrayRealVector result = new ArrayRealVector(dim);
/* 403 */       double[] resultData = result.data;
/* 404 */       for (int j = 0; j < dim; j++) {
/* 405 */         resultData[j] = this.data[j] * vData[j];
/*     */       }
/* 407 */       return result;
/*     */     } 
/* 409 */     checkVectorDimensions(v);
/* 410 */     double[] out = (double[])this.data.clone();
/* 411 */     for (int i = 0; i < this.data.length; i++) {
/* 412 */       out[i] = out[i] * v.getEntry(i);
/*     */     }
/* 414 */     return new ArrayRealVector(out, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector ebeDivide(RealVector v) throws DimensionMismatchException {
/* 422 */     if (v instanceof ArrayRealVector) {
/* 423 */       double[] vData = ((ArrayRealVector)v).data;
/* 424 */       int dim = vData.length;
/* 425 */       checkVectorDimensions(dim);
/* 426 */       ArrayRealVector result = new ArrayRealVector(dim);
/* 427 */       double[] resultData = result.data;
/* 428 */       for (int j = 0; j < dim; j++) {
/* 429 */         resultData[j] = this.data[j] / vData[j];
/*     */       }
/* 431 */       return result;
/*     */     } 
/* 433 */     checkVectorDimensions(v);
/* 434 */     double[] out = (double[])this.data.clone();
/* 435 */     for (int i = 0; i < this.data.length; i++) {
/* 436 */       out[i] = out[i] / v.getEntry(i);
/*     */     }
/* 438 */     return new ArrayRealVector(out, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getDataRef() {
/* 449 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double dotProduct(RealVector v) throws DimensionMismatchException {
/* 455 */     if (v instanceof ArrayRealVector) {
/* 456 */       double[] vData = ((ArrayRealVector)v).data;
/* 457 */       checkVectorDimensions(vData.length);
/* 458 */       double dot = 0.0D;
/* 459 */       for (int i = 0; i < this.data.length; i++) {
/* 460 */         dot += this.data[i] * vData[i];
/*     */       }
/* 462 */       return dot;
/*     */     } 
/* 464 */     return super.dotProduct(v);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNorm() {
/* 470 */     double sum = 0.0D;
/* 471 */     for (double a : this.data) {
/* 472 */       sum += a * a;
/*     */     }
/* 474 */     return FastMath.sqrt(sum);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getL1Norm() {
/* 480 */     double sum = 0.0D;
/* 481 */     for (double a : this.data) {
/* 482 */       sum += FastMath.abs(a);
/*     */     }
/* 484 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLInfNorm() {
/* 490 */     double max = 0.0D;
/* 491 */     for (double a : this.data) {
/* 492 */       max = FastMath.max(max, FastMath.abs(a));
/*     */     }
/* 494 */     return max;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistance(RealVector v) throws DimensionMismatchException {
/* 500 */     if (v instanceof ArrayRealVector) {
/* 501 */       double[] vData = ((ArrayRealVector)v).data;
/* 502 */       checkVectorDimensions(vData.length);
/* 503 */       double d = 0.0D;
/* 504 */       for (int j = 0; j < this.data.length; j++) {
/* 505 */         double delta = this.data[j] - vData[j];
/* 506 */         d += delta * delta;
/*     */       } 
/* 508 */       return FastMath.sqrt(d);
/*     */     } 
/* 510 */     checkVectorDimensions(v);
/* 511 */     double sum = 0.0D;
/* 512 */     for (int i = 0; i < this.data.length; i++) {
/* 513 */       double delta = this.data[i] - v.getEntry(i);
/* 514 */       sum += delta * delta;
/*     */     } 
/* 516 */     return FastMath.sqrt(sum);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getL1Distance(RealVector v) throws DimensionMismatchException {
/* 524 */     if (v instanceof ArrayRealVector) {
/* 525 */       double[] vData = ((ArrayRealVector)v).data;
/* 526 */       checkVectorDimensions(vData.length);
/* 527 */       double d = 0.0D;
/* 528 */       for (int j = 0; j < this.data.length; j++) {
/* 529 */         double delta = this.data[j] - vData[j];
/* 530 */         d += FastMath.abs(delta);
/*     */       } 
/* 532 */       return d;
/*     */     } 
/* 534 */     checkVectorDimensions(v);
/* 535 */     double sum = 0.0D;
/* 536 */     for (int i = 0; i < this.data.length; i++) {
/* 537 */       double delta = this.data[i] - v.getEntry(i);
/* 538 */       sum += FastMath.abs(delta);
/*     */     } 
/* 540 */     return sum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLInfDistance(RealVector v) throws DimensionMismatchException {
/* 548 */     if (v instanceof ArrayRealVector) {
/* 549 */       double[] vData = ((ArrayRealVector)v).data;
/* 550 */       checkVectorDimensions(vData.length);
/* 551 */       double d = 0.0D;
/* 552 */       for (int j = 0; j < this.data.length; j++) {
/* 553 */         double delta = this.data[j] - vData[j];
/* 554 */         d = FastMath.max(d, FastMath.abs(delta));
/*     */       } 
/* 556 */       return d;
/*     */     } 
/* 558 */     checkVectorDimensions(v);
/* 559 */     double max = 0.0D;
/* 560 */     for (int i = 0; i < this.data.length; i++) {
/* 561 */       double delta = this.data[i] - v.getEntry(i);
/* 562 */       max = FastMath.max(max, FastMath.abs(delta));
/*     */     } 
/* 564 */     return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealMatrix outerProduct(RealVector v) {
/* 571 */     if (v instanceof ArrayRealVector) {
/* 572 */       double[] vData = ((ArrayRealVector)v).data;
/* 573 */       int j = this.data.length;
/* 574 */       int k = vData.length;
/* 575 */       RealMatrix realMatrix = MatrixUtils.createRealMatrix(j, k);
/* 576 */       for (int i1 = 0; i1 < j; i1++) {
/* 577 */         for (int i2 = 0; i2 < k; i2++) {
/* 578 */           realMatrix.setEntry(i1, i2, this.data[i1] * vData[i2]);
/*     */         }
/*     */       } 
/* 581 */       return realMatrix;
/*     */     } 
/* 583 */     int m = this.data.length;
/* 584 */     int n = v.getDimension();
/* 585 */     RealMatrix out = MatrixUtils.createRealMatrix(m, n);
/* 586 */     for (int i = 0; i < m; i++) {
/* 587 */       for (int j = 0; j < n; j++) {
/* 588 */         out.setEntry(i, j, this.data[i] * v.getEntry(j));
/*     */       }
/*     */     } 
/* 591 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEntry(int index) throws OutOfRangeException {
/*     */     try {
/* 599 */       return this.data[index];
/* 600 */     } catch (IndexOutOfBoundsException e) {
/* 601 */       throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(getDimension() - 1));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimension() {
/* 609 */     return this.data.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector append(RealVector v) {
/*     */     try {
/* 616 */       return new ArrayRealVector(this, (ArrayRealVector)v);
/* 617 */     } catch (ClassCastException cce) {
/* 618 */       return new ArrayRealVector(this, v);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector append(ArrayRealVector v) {
/* 629 */     return new ArrayRealVector(this, v);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector append(double in) {
/* 635 */     double[] out = new double[this.data.length + 1];
/* 636 */     System.arraycopy(this.data, 0, out, 0, this.data.length);
/* 637 */     out[this.data.length] = in;
/* 638 */     return new ArrayRealVector(out, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RealVector getSubVector(int index, int n) throws OutOfRangeException, NotPositiveException {
/* 645 */     if (n < 0) {
/* 646 */       throw new NotPositiveException(LocalizedFormats.NUMBER_OF_ELEMENTS_SHOULD_BE_POSITIVE, Integer.valueOf(n));
/*     */     }
/* 648 */     ArrayRealVector out = new ArrayRealVector(n);
/*     */     try {
/* 650 */       System.arraycopy(this.data, index, out.data, 0, n);
/* 651 */     } catch (IndexOutOfBoundsException e) {
/* 652 */       checkIndex(index);
/* 653 */       checkIndex(index + n - 1);
/*     */     } 
/* 655 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntry(int index, double value) throws OutOfRangeException {
/*     */     try {
/* 662 */       this.data[index] = value;
/* 663 */     } catch (IndexOutOfBoundsException e) {
/* 664 */       checkIndex(index);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToEntry(int index, double increment) throws OutOfRangeException {
/*     */     try {
/* 673 */       this.data[index] = this.data[index] + increment;
/* 674 */     } catch (IndexOutOfBoundsException e) {
/* 675 */       throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(this.data.length - 1));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubVector(int index, RealVector v) throws OutOfRangeException {
/* 684 */     if (v instanceof ArrayRealVector) {
/* 685 */       setSubVector(index, ((ArrayRealVector)v).data);
/*     */     } else {
/*     */       try {
/* 688 */         for (int i = index; i < index + v.getDimension(); i++) {
/* 689 */           this.data[i] = v.getEntry(i - index);
/*     */         }
/* 691 */       } catch (IndexOutOfBoundsException e) {
/* 692 */         checkIndex(index);
/* 693 */         checkIndex(index + v.getDimension() - 1);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubVector(int index, double[] v) throws OutOfRangeException {
/*     */     try {
/* 709 */       System.arraycopy(v, 0, this.data, index, v.length);
/* 710 */     } catch (IndexOutOfBoundsException e) {
/* 711 */       checkIndex(index);
/* 712 */       checkIndex(index + v.length - 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(double value) {
/* 719 */     Arrays.fill(this.data, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] toArray() {
/* 725 */     return (double[])this.data.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 731 */     return DEFAULT_FORMAT.format(this);
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
/*     */   protected void checkVectorDimensions(RealVector v) throws DimensionMismatchException {
/* 744 */     checkVectorDimensions(v.getDimension());
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
/*     */   protected void checkVectorDimensions(int n) throws DimensionMismatchException {
/* 757 */     if (this.data.length != n) {
/* 758 */       throw new DimensionMismatchException(this.data.length, n);
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
/*     */   public boolean isNaN() {
/* 770 */     for (double v : this.data) {
/* 771 */       if (Double.isNaN(v)) {
/* 772 */         return true;
/*     */       }
/*     */     } 
/* 775 */     return false;
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
/*     */   public boolean isInfinite() {
/* 787 */     if (isNaN()) {
/* 788 */       return false;
/*     */     }
/*     */     
/* 791 */     for (double v : this.data) {
/* 792 */       if (Double.isInfinite(v)) {
/* 793 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 797 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 803 */     if (this == other) {
/* 804 */       return true;
/*     */     }
/*     */     
/* 807 */     if (!(other instanceof RealVector)) {
/* 808 */       return false;
/*     */     }
/*     */     
/* 811 */     RealVector rhs = (RealVector)other;
/* 812 */     if (this.data.length != rhs.getDimension()) {
/* 813 */       return false;
/*     */     }
/*     */     
/* 816 */     if (rhs.isNaN()) {
/* 817 */       return isNaN();
/*     */     }
/*     */     
/* 820 */     for (int i = 0; i < this.data.length; i++) {
/* 821 */       if (this.data[i] != rhs.getEntry(i)) {
/* 822 */         return false;
/*     */       }
/*     */     } 
/* 825 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 833 */     if (isNaN()) {
/* 834 */       return 9;
/*     */     }
/* 836 */     return MathUtils.hash(this.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector combine(double a, double b, RealVector y) throws DimensionMismatchException {
/* 843 */     return copy().combineToSelf(a, b, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayRealVector combineToSelf(double a, double b, RealVector y) throws DimensionMismatchException {
/* 850 */     if (y instanceof ArrayRealVector) {
/* 851 */       double[] yData = ((ArrayRealVector)y).data;
/* 852 */       checkVectorDimensions(yData.length);
/* 853 */       for (int i = 0; i < this.data.length; i++) {
/* 854 */         this.data[i] = a * this.data[i] + b * yData[i];
/*     */       }
/*     */     } else {
/* 857 */       checkVectorDimensions(y);
/* 858 */       for (int i = 0; i < this.data.length; i++) {
/* 859 */         this.data[i] = a * this.data[i] + b * y.getEntry(i);
/*     */       }
/*     */     } 
/* 862 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInDefaultOrder(RealVectorPreservingVisitor visitor) {
/* 868 */     visitor.start(this.data.length, 0, this.data.length - 1);
/* 869 */     for (int i = 0; i < this.data.length; i++) {
/* 870 */       visitor.visit(i, this.data[i]);
/*     */     }
/* 872 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInDefaultOrder(RealVectorPreservingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/* 880 */     checkIndices(start, end);
/* 881 */     visitor.start(this.data.length, start, end);
/* 882 */     for (int i = start; i <= end; i++) {
/* 883 */       visitor.visit(i, this.data[i]);
/*     */     }
/* 885 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInOptimizedOrder(RealVectorPreservingVisitor visitor) {
/* 895 */     return walkInDefaultOrder(visitor);
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
/*     */   public double walkInOptimizedOrder(RealVectorPreservingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/* 907 */     return walkInDefaultOrder(visitor, start, end);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInDefaultOrder(RealVectorChangingVisitor visitor) {
/* 913 */     visitor.start(this.data.length, 0, this.data.length - 1);
/* 914 */     for (int i = 0; i < this.data.length; i++) {
/* 915 */       this.data[i] = visitor.visit(i, this.data[i]);
/*     */     }
/* 917 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInDefaultOrder(RealVectorChangingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/* 925 */     checkIndices(start, end);
/* 926 */     visitor.start(this.data.length, start, end);
/* 927 */     for (int i = start; i <= end; i++) {
/* 928 */       this.data[i] = visitor.visit(i, this.data[i]);
/*     */     }
/* 930 */     return visitor.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double walkInOptimizedOrder(RealVectorChangingVisitor visitor) {
/* 940 */     return walkInDefaultOrder(visitor);
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
/*     */   public double walkInOptimizedOrder(RealVectorChangingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
/* 952 */     return walkInDefaultOrder(visitor, start, end);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/ArrayRealVector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */