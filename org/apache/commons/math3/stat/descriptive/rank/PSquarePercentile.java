/*     */ package org.apache.commons.math3.stat.descriptive.rank;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
/*     */ import org.apache.commons.math3.analysis.interpolation.NevilleInterpolator;
/*     */ import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;
/*     */ import org.apache.commons.math3.exception.InsufficientDataException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.stat.descriptive.AbstractStorelessUnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.StorelessUnivariateStatistic;
/*     */ import org.apache.commons.math3.stat.descriptive.UnivariateStatistic;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PSquarePercentile
/*     */   extends AbstractStorelessUnivariateStatistic
/*     */   implements StorelessUnivariateStatistic, Serializable
/*     */ {
/*     */   private static final int PSQUARE_CONSTANT = 5;
/*     */   private static final double DEFAULT_QUANTILE_DESIRED = 50.0D;
/*     */   private static final long serialVersionUID = 2283912083175715479L;
/*  78 */   private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("00.00");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   private final List<Double> initialFive = new FixedCapacityList<Double>(5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double quantile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient double lastObservation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   private PSquareMarkers markers = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   private double pValue = Double.NaN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long countOfObservations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PSquarePercentile(double p) {
/* 124 */     if (p > 100.0D || p < 0.0D) {
/* 125 */       throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE, Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(100));
/*     */     }
/*     */     
/* 128 */     this.quantile = p / 100.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PSquarePercentile() {
/* 136 */     this(50.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 144 */     double result = getResult();
/* 145 */     result = Double.isNaN(result) ? 37.0D : result;
/* 146 */     double markersHash = (this.markers == null) ? 0.0D : this.markers.hashCode();
/* 147 */     double[] toHash = { result, this.quantile, markersHash, this.countOfObservations };
/* 148 */     return Arrays.hashCode(toHash);
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
/*     */   public boolean equals(Object o) {
/* 162 */     boolean result = false;
/* 163 */     if (this == o) {
/* 164 */       result = true;
/* 165 */     } else if (o != null && o instanceof PSquarePercentile) {
/* 166 */       PSquarePercentile that = (PSquarePercentile)o;
/* 167 */       boolean isNotNull = (this.markers != null && that.markers != null);
/* 168 */       boolean isNull = (this.markers == null && that.markers == null);
/* 169 */       result = isNotNull ? this.markers.equals(that.markers) : isNull;
/*     */ 
/*     */       
/* 172 */       result = (result && getN() == that.getN());
/*     */     } 
/* 174 */     return result;
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
/*     */   public void increment(double observation) {
/* 187 */     this.countOfObservations++;
/*     */ 
/*     */     
/* 190 */     this.lastObservation = observation;
/*     */ 
/*     */     
/* 193 */     if (this.markers == null) {
/* 194 */       if (this.initialFive.add(Double.valueOf(observation))) {
/* 195 */         Collections.sort(this.initialFive);
/* 196 */         this.pValue = ((Double)this.initialFive.get((int)(this.quantile * (this.initialFive.size() - 1)))).doubleValue();
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 202 */       this.markers = newMarkers(this.initialFive, this.quantile);
/*     */     } 
/*     */     
/* 205 */     this.pValue = this.markers.processDataPoint(observation);
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
/*     */   public String toString() {
/* 217 */     if (this.markers == null) {
/* 218 */       return String.format("obs=%s pValue=%s", new Object[] { DECIMAL_FORMAT.format(this.lastObservation), DECIMAL_FORMAT.format(this.pValue) });
/*     */     }
/*     */ 
/*     */     
/* 222 */     return String.format("obs=%s markers=%s", new Object[] { DECIMAL_FORMAT.format(this.lastObservation), this.markers.toString() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getN() {
/* 231 */     return this.countOfObservations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StorelessUnivariateStatistic copy() {
/* 240 */     PSquarePercentile copy = new PSquarePercentile(100.0D * this.quantile);
/*     */     
/* 242 */     if (this.markers != null) {
/* 243 */       copy.markers = (PSquareMarkers)this.markers.clone();
/*     */     }
/* 245 */     copy.countOfObservations = this.countOfObservations;
/* 246 */     copy.pValue = this.pValue;
/* 247 */     copy.initialFive.clear();
/* 248 */     copy.initialFive.addAll(this.initialFive);
/* 249 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double quantile() {
/* 258 */     return this.quantile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 267 */     this.markers = null;
/* 268 */     this.initialFive.clear();
/* 269 */     this.countOfObservations = 0L;
/* 270 */     this.pValue = Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getResult() {
/* 278 */     if (Double.compare(this.quantile, 1.0D) == 0) {
/* 279 */       this.pValue = maximum();
/* 280 */     } else if (Double.compare(this.quantile, 0.0D) == 0) {
/* 281 */       this.pValue = minimum();
/*     */     } 
/* 283 */     return this.pValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double maximum() {
/* 290 */     double val = Double.NaN;
/* 291 */     if (this.markers != null) {
/* 292 */       val = this.markers.height(5);
/* 293 */     } else if (!this.initialFive.isEmpty()) {
/* 294 */       val = ((Double)this.initialFive.get(this.initialFive.size() - 1)).doubleValue();
/*     */     } 
/* 296 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double minimum() {
/* 303 */     double val = Double.NaN;
/* 304 */     if (this.markers != null) {
/* 305 */       val = this.markers.height(1);
/* 306 */     } else if (!this.initialFive.isEmpty()) {
/* 307 */       val = ((Double)this.initialFive.get(0)).doubleValue();
/*     */     } 
/* 309 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Markers
/*     */     implements PSquareMarkers, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int LOW = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int HIGH = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final PSquarePercentile.Marker[] markerArray;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 339 */     private transient int k = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Markers(PSquarePercentile.Marker[] theMarkerArray) {
/* 347 */       MathUtils.checkNotNull(theMarkerArray);
/* 348 */       this.markerArray = theMarkerArray;
/* 349 */       for (int i = 1; i < 5; i++) {
/* 350 */         this.markerArray[i].previous(this.markerArray[i - 1]).next(this.markerArray[i + 1]).index(i);
/*     */       }
/*     */       
/* 353 */       this.markerArray[0].previous(this.markerArray[0]).next(this.markerArray[1]).index(0);
/*     */       
/* 355 */       this.markerArray[5].previous(this.markerArray[4]).next(this.markerArray[5]).index(5);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Markers(List<Double> initialFive, double p) {
/* 366 */       this(createMarkerArray(initialFive, p));
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
/*     */     private static PSquarePercentile.Marker[] createMarkerArray(List<Double> initialFive, double p) {
/* 378 */       int countObserved = (initialFive == null) ? -1 : initialFive.size();
/*     */       
/* 380 */       if (countObserved < 5) {
/* 381 */         throw new InsufficientDataException(LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, new Object[] { Integer.valueOf(countObserved), Integer.valueOf(5) });
/*     */       }
/*     */ 
/*     */       
/* 385 */       Collections.sort(initialFive);
/* 386 */       return new PSquarePercentile.Marker[] { new PSquarePercentile.Marker(), new PSquarePercentile.Marker(((Double)initialFive.get(0)).doubleValue(), 1.0D, 0.0D, 1.0D), new PSquarePercentile.Marker(((Double)initialFive.get(1)).doubleValue(), 1.0D + 2.0D * p, p / 2.0D, 2.0D), new PSquarePercentile.Marker(((Double)initialFive.get(2)).doubleValue(), 1.0D + 4.0D * p, p, 3.0D), new PSquarePercentile.Marker(((Double)initialFive.get(3)).doubleValue(), 3.0D + 2.0D * p, (1.0D + p) / 2.0D, 4.0D), new PSquarePercentile.Marker(((Double)initialFive.get(4)).doubleValue(), 5.0D, 1.0D, 5.0D) };
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
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 400 */       return Arrays.deepHashCode((Object[])this.markerArray);
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
/*     */     public boolean equals(Object o) {
/* 412 */       boolean result = false;
/* 413 */       if (this == o) {
/* 414 */         result = true;
/* 415 */       } else if (o != null && o instanceof Markers) {
/* 416 */         Markers that = (Markers)o;
/* 417 */         result = Arrays.deepEquals((Object[])this.markerArray, (Object[])that.markerArray);
/*     */       } 
/* 419 */       return result;
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
/*     */     public double processDataPoint(double inputDataPoint) {
/* 431 */       int kthCell = findCellAndUpdateMinMax(inputDataPoint);
/*     */ 
/*     */       
/* 434 */       incrementPositions(1, kthCell + 1, 5);
/*     */ 
/*     */       
/* 437 */       updateDesiredPositions();
/*     */ 
/*     */       
/* 440 */       adjustHeightsOfMarkers();
/*     */ 
/*     */       
/* 443 */       return getPercentileValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getPercentileValue() {
/* 452 */       return height(3);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int findCellAndUpdateMinMax(double observation) {
/* 463 */       this.k = -1;
/* 464 */       if (observation < height(1)) {
/* 465 */         (this.markerArray[1]).markerHeight = observation;
/* 466 */         this.k = 1;
/* 467 */       } else if (observation < height(2)) {
/* 468 */         this.k = 1;
/* 469 */       } else if (observation < height(3)) {
/* 470 */         this.k = 2;
/* 471 */       } else if (observation < height(4)) {
/* 472 */         this.k = 3;
/* 473 */       } else if (observation <= height(5)) {
/* 474 */         this.k = 4;
/*     */       } else {
/* 476 */         (this.markerArray[5]).markerHeight = observation;
/* 477 */         this.k = 4;
/*     */       } 
/* 479 */       return this.k;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void adjustHeightsOfMarkers() {
/* 486 */       for (int i = 2; i <= 4; i++) {
/* 487 */         estimate(i);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double estimate(int index) {
/* 495 */       if (index < 2 || index > 4) {
/* 496 */         throw new OutOfRangeException(Integer.valueOf(index), Integer.valueOf(2), Integer.valueOf(4));
/*     */       }
/* 498 */       return this.markerArray[index].estimate();
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
/*     */     
/*     */     private void incrementPositions(int d, int startIndex, int endIndex) {
/* 511 */       for (int i = startIndex; i <= endIndex; i++) {
/* 512 */         this.markerArray[i].incrementPosition(d);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void updateDesiredPositions() {
/* 521 */       for (int i = 1; i < this.markerArray.length; i++) {
/* 522 */         this.markerArray[i].updateDesiredPosition();
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
/*     */     
/*     */     private void readObject(ObjectInputStream anInputStream) throws ClassNotFoundException, IOException {
/* 536 */       anInputStream.defaultReadObject();
/*     */       
/* 538 */       for (int i = 1; i < 5; i++) {
/* 539 */         this.markerArray[i].previous(this.markerArray[i - 1]).next(this.markerArray[i + 1]).index(i);
/*     */       }
/*     */       
/* 542 */       this.markerArray[0].previous(this.markerArray[0]).next(this.markerArray[1]).index(0);
/*     */       
/* 544 */       this.markerArray[5].previous(this.markerArray[4]).next(this.markerArray[5]).index(5);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double height(int markerIndex) {
/* 555 */       if (markerIndex >= this.markerArray.length || markerIndex <= 0) {
/* 556 */         throw new OutOfRangeException(Integer.valueOf(markerIndex), Integer.valueOf(1), Integer.valueOf(this.markerArray.length));
/*     */       }
/*     */       
/* 559 */       return (this.markerArray[markerIndex]).markerHeight;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 569 */       return new Markers(new PSquarePercentile.Marker[] { new PSquarePercentile.Marker(), (PSquarePercentile.Marker)this.markerArray[1].clone(), (PSquarePercentile.Marker)this.markerArray[2].clone(), (PSquarePercentile.Marker)this.markerArray[3].clone(), (PSquarePercentile.Marker)this.markerArray[4].clone(), (PSquarePercentile.Marker)this.markerArray[5].clone() });
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 585 */       return String.format("m1=[%s],m2=[%s],m3=[%s],m4=[%s],m5=[%s]", new Object[] { this.markerArray[1].toString(), this.markerArray[2].toString(), this.markerArray[3].toString(), this.markerArray[4].toString(), this.markerArray[5].toString() });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Marker
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -3575879478288538431L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int index;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double intMarkerPosition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double desiredMarkerPosition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double markerHeight;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double desiredMarkerIncrement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private transient Marker next;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private transient Marker previous;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 647 */     private final UnivariateInterpolator nonLinear = (UnivariateInterpolator)new NevilleInterpolator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 653 */     private transient UnivariateInterpolator linear = (UnivariateInterpolator)new LinearInterpolator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Marker() {
/* 660 */       this.next = this.previous = this;
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
/*     */     
/*     */     private Marker(double heightOfMarker, double makerPositionDesired, double markerPositionIncrement, double markerPositionNumber) {
/* 673 */       this();
/* 674 */       this.markerHeight = heightOfMarker;
/* 675 */       this.desiredMarkerPosition = makerPositionDesired;
/* 676 */       this.desiredMarkerIncrement = markerPositionIncrement;
/* 677 */       this.intMarkerPosition = markerPositionNumber;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Marker previous(Marker previousMarker) {
/* 688 */       MathUtils.checkNotNull(previousMarker);
/* 689 */       this.previous = previousMarker;
/* 690 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Marker next(Marker nextMarker) {
/* 701 */       MathUtils.checkNotNull(nextMarker);
/* 702 */       this.next = nextMarker;
/* 703 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Marker index(int indexOfMarker) {
/* 713 */       this.index = indexOfMarker;
/* 714 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void updateDesiredPosition() {
/* 721 */       this.desiredMarkerPosition += this.desiredMarkerIncrement;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void incrementPosition(int d) {
/* 730 */       this.intMarkerPosition += d;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double difference() {
/* 739 */       return this.desiredMarkerPosition - this.intMarkerPosition;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double estimate() {
/* 748 */       double di = difference();
/* 749 */       boolean isNextHigher = (this.next.intMarkerPosition - this.intMarkerPosition > 1.0D);
/*     */       
/* 751 */       boolean isPreviousLower = (this.previous.intMarkerPosition - this.intMarkerPosition < -1.0D);
/*     */ 
/*     */       
/* 754 */       if ((di >= 1.0D && isNextHigher) || (di <= -1.0D && isPreviousLower)) {
/* 755 */         int d = (di >= 0.0D) ? 1 : -1;
/* 756 */         double[] xval = { this.previous.intMarkerPosition, this.intMarkerPosition, this.next.intMarkerPosition };
/*     */ 
/*     */         
/* 759 */         double[] yval = { this.previous.markerHeight, this.markerHeight, this.next.markerHeight };
/*     */ 
/*     */         
/* 762 */         double xD = this.intMarkerPosition + d;
/*     */         
/* 764 */         UnivariateFunction univariateFunction = this.nonLinear.interpolate(xval, yval);
/*     */         
/* 766 */         this.markerHeight = univariateFunction.value(xD);
/*     */ 
/*     */         
/* 769 */         if (isEstimateBad(yval, this.markerHeight)) {
/* 770 */           int delta = (xD - xval[1] > 0.0D) ? 1 : -1;
/* 771 */           double[] xBad = { xval[1], xval[1 + delta] };
/*     */           
/* 773 */           double[] yBad = { yval[1], yval[1 + delta] };
/*     */           
/* 775 */           MathArrays.sortInPlace(xBad, new double[][] { yBad });
/* 776 */           univariateFunction = this.linear.interpolate(xBad, yBad);
/* 777 */           this.markerHeight = univariateFunction.value(xD);
/*     */         } 
/* 779 */         incrementPosition(d);
/*     */       } 
/* 781 */       return this.markerHeight;
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
/*     */     private boolean isEstimateBad(double[] y, double yD) {
/* 793 */       return (yD <= y[0] || yD >= y[2]);
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
/*     */     
/*     */     public boolean equals(Object o) {
/* 806 */       boolean result = false;
/* 807 */       if (this == o) {
/* 808 */         result = true;
/* 809 */       } else if (o != null && o instanceof Marker) {
/* 810 */         Marker that = (Marker)o;
/*     */         
/* 812 */         result = (Double.compare(this.markerHeight, that.markerHeight) == 0);
/* 813 */         result = (result && Double.compare(this.intMarkerPosition, that.intMarkerPosition) == 0);
/*     */ 
/*     */ 
/*     */         
/* 817 */         result = (result && Double.compare(this.desiredMarkerPosition, that.desiredMarkerPosition) == 0);
/*     */ 
/*     */ 
/*     */         
/* 821 */         result = (result && Double.compare(this.desiredMarkerIncrement, that.desiredMarkerIncrement) == 0);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 826 */         result = (result && this.next.index == that.next.index);
/* 827 */         result = (result && this.previous.index == that.previous.index);
/*     */       } 
/* 829 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 835 */       return Arrays.hashCode(new double[] { this.markerHeight, this.intMarkerPosition, this.desiredMarkerIncrement, this.desiredMarkerPosition, this.previous.index, this.next.index });
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
/*     */     
/*     */     private void readObject(ObjectInputStream anInstream) throws ClassNotFoundException, IOException {
/* 848 */       anInstream.defaultReadObject();
/* 849 */       this.previous = this.next = this;
/* 850 */       this.linear = (UnivariateInterpolator)new LinearInterpolator();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 860 */       return new Marker(this.markerHeight, this.desiredMarkerPosition, this.desiredMarkerIncrement, this.intMarkerPosition);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 869 */       return String.format("index=%.0f,n=%.0f,np=%.2f,q=%.2f,dn=%.2f,prev=%d,next=%d", new Object[] { Double.valueOf(this.index), Double.valueOf(Precision.round(this.intMarkerPosition, 0)), Double.valueOf(Precision.round(this.desiredMarkerPosition, 2)), Double.valueOf(Precision.round(this.markerHeight, 2)), Double.valueOf(Precision.round(this.desiredMarkerIncrement, 2)), Integer.valueOf(this.previous.index), Integer.valueOf(this.next.index) });
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
/*     */   private static class FixedCapacityList<E>
/*     */     extends ArrayList<E>
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 2283952083075725479L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int capacity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     FixedCapacityList(int fixedCapacity) {
/* 904 */       super(fixedCapacity);
/* 905 */       this.capacity = fixedCapacity;
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
/*     */     public boolean add(E e) {
/* 917 */       return (size() < this.capacity) ? super.add(e) : false;
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
/*     */     
/*     */     public boolean addAll(Collection<? extends E> collection) {
/* 930 */       boolean isCollectionLess = (collection != null && collection.size() + size() <= this.capacity);
/*     */ 
/*     */       
/* 933 */       return isCollectionLess ? super.addAll(collection) : false;
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
/*     */   public static PSquareMarkers newMarkers(List<Double> initialFive, double p) {
/* 946 */     return new Markers(initialFive, p);
/*     */   }
/*     */   
/*     */   protected static interface PSquareMarkers extends Cloneable {
/*     */     double getPercentileValue();
/*     */     
/*     */     Object clone();
/*     */     
/*     */     double height(int param1Int);
/*     */     
/*     */     double processDataPoint(double param1Double);
/*     */     
/*     */     double estimate(int param1Int);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/descriptive/rank/PSquarePercentile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */