/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyVetoException;
/*     */ import java.beans.VetoableChangeListener;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.DomainInfo;
/*     */ import org.jfree.data.DomainOrder;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.RangeInfo;
/*     */ import org.jfree.data.UnknownKeyException;
/*     */ import org.jfree.data.general.Series;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XYSeriesCollection
/*     */   extends AbstractIntervalXYDataset
/*     */   implements IntervalXYDataset, DomainInfo, RangeInfo, VetoableChangeListener, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7590013825931496766L;
/*     */   private List data;
/*     */   private IntervalXYDelegate intervalDelegate;
/*     */   
/*     */   public XYSeriesCollection() {
/* 109 */     this((XYSeries)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYSeriesCollection(XYSeries series) {
/* 118 */     this.data = new ArrayList();
/* 119 */     this.intervalDelegate = new IntervalXYDelegate(this, false);
/* 120 */     addChangeListener(this.intervalDelegate);
/* 121 */     if (series != null) {
/* 122 */       this.data.add(series);
/* 123 */       series.addChangeListener((SeriesChangeListener)this);
/* 124 */       series.addVetoableChangeListener(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DomainOrder getDomainOrder() {
/* 135 */     int seriesCount = getSeriesCount();
/* 136 */     for (int i = 0; i < seriesCount; i++) {
/* 137 */       XYSeries s = getSeries(i);
/* 138 */       if (!s.getAutoSort()) {
/* 139 */         return DomainOrder.NONE;
/*     */       }
/*     */     } 
/* 142 */     return DomainOrder.ASCENDING;
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
/*     */   public void addSeries(XYSeries series) {
/* 155 */     ParamChecks.nullNotPermitted(series, "series");
/* 156 */     if (getSeriesIndex(series.getKey()) >= 0) {
/* 157 */       throw new IllegalArgumentException("This dataset already contains a series with the key " + series
/*     */           
/* 159 */           .getKey());
/*     */     }
/* 161 */     this.data.add(series);
/* 162 */     series.addChangeListener((SeriesChangeListener)this);
/* 163 */     series.addVetoableChangeListener(this);
/* 164 */     fireDatasetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSeries(int series) {
/* 174 */     if (series < 0 || series >= getSeriesCount()) {
/* 175 */       throw new IllegalArgumentException("Series index out of bounds.");
/*     */     }
/* 177 */     XYSeries s = this.data.get(series);
/* 178 */     if (s != null) {
/* 179 */       removeSeries(s);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSeries(XYSeries series) {
/* 190 */     ParamChecks.nullNotPermitted(series, "series");
/* 191 */     if (this.data.contains(series)) {
/* 192 */       series.removeChangeListener((SeriesChangeListener)this);
/* 193 */       series.removeVetoableChangeListener(this);
/* 194 */       this.data.remove(series);
/* 195 */       fireDatasetChanged();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllSeries() {
/* 206 */     for (int i = 0; i < this.data.size(); i++) {
/* 207 */       XYSeries series = this.data.get(i);
/* 208 */       series.removeChangeListener((SeriesChangeListener)this);
/* 209 */       series.removeVetoableChangeListener(this);
/*     */     } 
/*     */ 
/*     */     
/* 213 */     this.data.clear();
/* 214 */     fireDatasetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeriesCount() {
/* 224 */     return this.data.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getSeries() {
/* 233 */     return Collections.unmodifiableList(this.data);
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
/*     */   public int indexOf(XYSeries series) {
/* 247 */     ParamChecks.nullNotPermitted(series, "series");
/* 248 */     return this.data.indexOf(series);
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
/*     */   public XYSeries getSeries(int series) {
/* 262 */     if (series < 0 || series >= getSeriesCount()) {
/* 263 */       throw new IllegalArgumentException("Series index out of bounds");
/*     */     }
/* 265 */     return this.data.get(series);
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
/*     */   public XYSeries getSeries(Comparable key) {
/* 281 */     ParamChecks.nullNotPermitted(key, "key");
/* 282 */     Iterator<XYSeries> iterator = this.data.iterator();
/* 283 */     while (iterator.hasNext()) {
/* 284 */       XYSeries series = iterator.next();
/* 285 */       if (key.equals(series.getKey())) {
/* 286 */         return series;
/*     */       }
/*     */     } 
/* 289 */     throw new UnknownKeyException("Key not found: " + key);
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
/*     */   public Comparable getSeriesKey(int series) {
/* 306 */     return getSeries(series).getKey();
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
/*     */   public int getSeriesIndex(Comparable key) {
/* 320 */     ParamChecks.nullNotPermitted(key, "key");
/* 321 */     int seriesCount = getSeriesCount();
/* 322 */     for (int i = 0; i < seriesCount; i++) {
/* 323 */       XYSeries series = this.data.get(i);
/* 324 */       if (key.equals(series.getKey())) {
/* 325 */         return i;
/*     */       }
/*     */     } 
/* 328 */     return -1;
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
/*     */   public int getItemCount(int series) {
/* 344 */     return getSeries(series).getItemCount();
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
/*     */   public Number getX(int series, int item) {
/* 357 */     XYSeries s = this.data.get(series);
/* 358 */     return s.getX(item);
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
/*     */   public Number getStartX(int series, int item) {
/* 371 */     return this.intervalDelegate.getStartX(series, item);
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
/*     */   public Number getEndX(int series, int item) {
/* 384 */     return this.intervalDelegate.getEndX(series, item);
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
/*     */   public Number getY(int series, int index) {
/* 397 */     XYSeries s = this.data.get(series);
/* 398 */     return s.getY(index);
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
/*     */   public Number getStartY(int series, int item) {
/* 411 */     return getY(series, item);
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
/*     */   public Number getEndY(int series, int item) {
/* 424 */     return getY(series, item);
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
/*     */   public boolean equals(Object obj) {
/* 436 */     if (obj == this) {
/* 437 */       return true;
/*     */     }
/* 439 */     if (!(obj instanceof XYSeriesCollection)) {
/* 440 */       return false;
/*     */     }
/* 442 */     XYSeriesCollection that = (XYSeriesCollection)obj;
/* 443 */     if (!this.intervalDelegate.equals(that.intervalDelegate)) {
/* 444 */       return false;
/*     */     }
/* 446 */     return ObjectUtilities.equal(this.data, that.data);
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 458 */     XYSeriesCollection clone = (XYSeriesCollection)super.clone();
/* 459 */     clone.data = (List)ObjectUtilities.deepClone(this.data);
/* 460 */     clone
/* 461 */       .intervalDelegate = (IntervalXYDelegate)this.intervalDelegate.clone();
/* 462 */     return clone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 472 */     int hash = 5;
/* 473 */     hash = HashUtilities.hashCode(hash, this.intervalDelegate);
/* 474 */     hash = HashUtilities.hashCode(hash, this.data);
/* 475 */     return hash;
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
/*     */   public double getDomainLowerBound(boolean includeInterval) {
/* 488 */     if (includeInterval) {
/* 489 */       return this.intervalDelegate.getDomainLowerBound(includeInterval);
/*     */     }
/* 491 */     double result = Double.NaN;
/* 492 */     int seriesCount = getSeriesCount();
/* 493 */     for (int s = 0; s < seriesCount; s++) {
/* 494 */       XYSeries series = getSeries(s);
/* 495 */       double lowX = series.getMinX();
/* 496 */       if (Double.isNaN(result)) {
/* 497 */         result = lowX;
/*     */       
/*     */       }
/* 500 */       else if (!Double.isNaN(lowX)) {
/* 501 */         result = Math.min(result, lowX);
/*     */       } 
/*     */     } 
/*     */     
/* 505 */     return result;
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
/*     */   public double getDomainUpperBound(boolean includeInterval) {
/* 518 */     if (includeInterval) {
/* 519 */       return this.intervalDelegate.getDomainUpperBound(includeInterval);
/*     */     }
/*     */     
/* 522 */     double result = Double.NaN;
/* 523 */     int seriesCount = getSeriesCount();
/* 524 */     for (int s = 0; s < seriesCount; s++) {
/* 525 */       XYSeries series = getSeries(s);
/* 526 */       double hiX = series.getMaxX();
/* 527 */       if (Double.isNaN(result)) {
/* 528 */         result = hiX;
/*     */       
/*     */       }
/* 531 */       else if (!Double.isNaN(hiX)) {
/* 532 */         result = Math.max(result, hiX);
/*     */       } 
/*     */     } 
/*     */     
/* 536 */     return result;
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
/*     */   public Range getDomainBounds(boolean includeInterval) {
/* 551 */     if (includeInterval) {
/* 552 */       return this.intervalDelegate.getDomainBounds(includeInterval);
/*     */     }
/*     */     
/* 555 */     double lower = Double.POSITIVE_INFINITY;
/* 556 */     double upper = Double.NEGATIVE_INFINITY;
/* 557 */     int seriesCount = getSeriesCount();
/* 558 */     for (int s = 0; s < seriesCount; s++) {
/* 559 */       XYSeries series = getSeries(s);
/* 560 */       double minX = series.getMinX();
/* 561 */       if (!Double.isNaN(minX)) {
/* 562 */         lower = Math.min(lower, minX);
/*     */       }
/* 564 */       double maxX = series.getMaxX();
/* 565 */       if (!Double.isNaN(maxX)) {
/* 566 */         upper = Math.max(upper, maxX);
/*     */       }
/*     */     } 
/* 569 */     if (lower > upper) {
/* 570 */       return null;
/*     */     }
/*     */     
/* 573 */     return new Range(lower, upper);
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
/*     */   public double getIntervalWidth() {
/* 585 */     return this.intervalDelegate.getIntervalWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIntervalWidth(double width) {
/* 595 */     if (width < 0.0D) {
/* 596 */       throw new IllegalArgumentException("Negative 'width' argument.");
/*     */     }
/* 598 */     this.intervalDelegate.setFixedIntervalWidth(width);
/* 599 */     fireDatasetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getIntervalPositionFactor() {
/* 608 */     return this.intervalDelegate.getIntervalPositionFactor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIntervalPositionFactor(double factor) {
/* 619 */     this.intervalDelegate.setIntervalPositionFactor(factor);
/* 620 */     fireDatasetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoWidth() {
/* 629 */     return this.intervalDelegate.isAutoWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAutoWidth(boolean b) {
/* 639 */     this.intervalDelegate.setAutoWidth(b);
/* 640 */     fireDatasetChanged();
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
/*     */   public Range getRangeBounds(boolean includeInterval) {
/* 653 */     double lower = Double.POSITIVE_INFINITY;
/* 654 */     double upper = Double.NEGATIVE_INFINITY;
/* 655 */     int seriesCount = getSeriesCount();
/* 656 */     for (int s = 0; s < seriesCount; s++) {
/* 657 */       XYSeries series = getSeries(s);
/* 658 */       double minY = series.getMinY();
/* 659 */       if (!Double.isNaN(minY)) {
/* 660 */         lower = Math.min(lower, minY);
/*     */       }
/* 662 */       double maxY = series.getMaxY();
/* 663 */       if (!Double.isNaN(maxY)) {
/* 664 */         upper = Math.max(upper, maxY);
/*     */       }
/*     */     } 
/* 667 */     if (lower > upper) {
/* 668 */       return null;
/*     */     }
/*     */     
/* 671 */     return new Range(lower, upper);
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
/*     */   public double getRangeLowerBound(boolean includeInterval) {
/* 685 */     double result = Double.NaN;
/* 686 */     int seriesCount = getSeriesCount();
/* 687 */     for (int s = 0; s < seriesCount; s++) {
/* 688 */       XYSeries series = getSeries(s);
/* 689 */       double lowY = series.getMinY();
/* 690 */       if (Double.isNaN(result)) {
/* 691 */         result = lowY;
/*     */       
/*     */       }
/* 694 */       else if (!Double.isNaN(lowY)) {
/* 695 */         result = Math.min(result, lowY);
/*     */       } 
/*     */     } 
/*     */     
/* 699 */     return result;
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
/*     */   public double getRangeUpperBound(boolean includeInterval) {
/* 712 */     double result = Double.NaN;
/* 713 */     int seriesCount = getSeriesCount();
/* 714 */     for (int s = 0; s < seriesCount; s++) {
/* 715 */       XYSeries series = getSeries(s);
/* 716 */       double hiY = series.getMaxY();
/* 717 */       if (Double.isNaN(result)) {
/* 718 */         result = hiY;
/*     */       
/*     */       }
/* 721 */       else if (!Double.isNaN(hiY)) {
/* 722 */         result = Math.max(result, hiY);
/*     */       } 
/*     */     } 
/*     */     
/* 726 */     return result;
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
/*     */   public void vetoableChange(PropertyChangeEvent e) throws PropertyVetoException {
/* 742 */     if (!"Key".equals(e.getPropertyName())) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 748 */     Series s = (Series)e.getSource();
/* 749 */     if (getSeriesIndex(s.getKey()) == -1) {
/* 750 */       throw new IllegalStateException("Receiving events from a series that does not belong to this collection.");
/*     */     }
/*     */ 
/*     */     
/* 754 */     Comparable key = (Comparable)e.getNewValue();
/* 755 */     if (getSeriesIndex(key) >= 0)
/* 756 */       throw new PropertyVetoException("Duplicate key2", e); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/XYSeriesCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */