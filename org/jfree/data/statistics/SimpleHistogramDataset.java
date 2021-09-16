/*     */ package org.jfree.data.statistics;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.DomainOrder;
/*     */ import org.jfree.data.general.Dataset;
/*     */ import org.jfree.data.general.DatasetChangeEvent;
/*     */ import org.jfree.data.xy.AbstractIntervalXYDataset;
/*     */ import org.jfree.data.xy.IntervalXYDataset;
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
/*     */ public class SimpleHistogramDataset
/*     */   extends AbstractIntervalXYDataset
/*     */   implements IntervalXYDataset, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7997996479768018443L;
/*     */   private Comparable key;
/*     */   private List bins;
/*     */   private boolean adjustForBinSize;
/*     */   
/*     */   public SimpleHistogramDataset(Comparable key) {
/*  91 */     ParamChecks.nullNotPermitted(key, "key");
/*  92 */     this.key = key;
/*  93 */     this.bins = new ArrayList();
/*  94 */     this.adjustForBinSize = true;
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
/*     */   public boolean getAdjustForBinSize() {
/* 106 */     return this.adjustForBinSize;
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
/*     */   public void setAdjustForBinSize(boolean adjust) {
/* 119 */     this.adjustForBinSize = adjust;
/* 120 */     notifyListeners(new DatasetChangeEvent(this, (Dataset)this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeriesCount() {
/* 130 */     return 1;
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
/*     */   public Comparable getSeriesKey(int series) {
/* 143 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DomainOrder getDomainOrder() {
/* 153 */     return DomainOrder.ASCENDING;
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
/*     */   public int getItemCount(int series) {
/* 166 */     return this.bins.size();
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
/*     */   public void addBin(SimpleHistogramBin bin) {
/* 179 */     Iterator<SimpleHistogramBin> iterator = this.bins.iterator();
/* 180 */     while (iterator.hasNext()) {
/*     */       
/* 182 */       SimpleHistogramBin existingBin = iterator.next();
/* 183 */       if (bin.overlapsWith(existingBin)) {
/* 184 */         throw new RuntimeException("Overlapping bin");
/*     */       }
/*     */     } 
/* 187 */     this.bins.add(bin);
/* 188 */     Collections.sort(this.bins);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addObservation(double value) {
/* 199 */     addObservation(value, true);
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
/*     */   public void addObservation(double value, boolean notify) {
/* 211 */     boolean placed = false;
/* 212 */     Iterator<SimpleHistogramBin> iterator = this.bins.iterator();
/* 213 */     while (iterator.hasNext() && !placed) {
/* 214 */       SimpleHistogramBin bin = iterator.next();
/* 215 */       if (bin.accepts(value)) {
/* 216 */         bin.setItemCount(bin.getItemCount() + 1);
/* 217 */         placed = true;
/*     */       } 
/*     */     } 
/* 220 */     if (!placed) {
/* 221 */       throw new RuntimeException("No bin.");
/*     */     }
/* 223 */     if (notify) {
/* 224 */       notifyListeners(new DatasetChangeEvent(this, (Dataset)this));
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
/*     */   public void addObservations(double[] values) {
/* 237 */     for (int i = 0; i < values.length; i++) {
/* 238 */       addObservation(values[i], false);
/*     */     }
/* 240 */     notifyListeners(new DatasetChangeEvent(this, (Dataset)this));
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
/*     */   public void clearObservations() {
/* 253 */     Iterator<SimpleHistogramBin> iterator = this.bins.iterator();
/* 254 */     while (iterator.hasNext()) {
/* 255 */       SimpleHistogramBin bin = iterator.next();
/* 256 */       bin.setItemCount(0);
/*     */     } 
/* 258 */     notifyListeners(new DatasetChangeEvent(this, (Dataset)this));
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
/*     */   public void removeAllBins() {
/* 270 */     this.bins = new ArrayList();
/* 271 */     notifyListeners(new DatasetChangeEvent(this, (Dataset)this));
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
/*     */   public Number getX(int series, int item) {
/* 286 */     return new Double(getXValue(series, item));
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
/*     */   public double getXValue(int series, int item) {
/* 299 */     SimpleHistogramBin bin = this.bins.get(item);
/* 300 */     return (bin.getLowerBound() + bin.getUpperBound()) / 2.0D;
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
/*     */   public Number getY(int series, int item) {
/* 313 */     return new Double(getYValue(series, item));
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
/*     */   public double getYValue(int series, int item) {
/* 328 */     SimpleHistogramBin bin = this.bins.get(item);
/* 329 */     if (this.adjustForBinSize)
/*     */     {
/* 331 */       return bin.getItemCount() / (bin.getUpperBound() - bin.getLowerBound());
/*     */     }
/*     */     
/* 334 */     return bin.getItemCount();
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
/*     */   public Number getStartX(int series, int item) {
/* 348 */     return new Double(getStartXValue(series, item));
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
/*     */   public double getStartXValue(int series, int item) {
/* 362 */     SimpleHistogramBin bin = this.bins.get(item);
/* 363 */     return bin.getLowerBound();
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
/* 376 */     return new Double(getEndXValue(series, item));
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
/*     */   public double getEndXValue(int series, int item) {
/* 390 */     SimpleHistogramBin bin = this.bins.get(item);
/* 391 */     return bin.getUpperBound();
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
/* 404 */     return getY(series, item);
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
/*     */   public double getStartYValue(int series, int item) {
/* 418 */     return getYValue(series, item);
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
/* 431 */     return getY(series, item);
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
/*     */   public double getEndYValue(int series, int item) {
/* 445 */     return getYValue(series, item);
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
/* 457 */     if (obj == this) {
/* 458 */       return true;
/*     */     }
/* 460 */     if (!(obj instanceof SimpleHistogramDataset)) {
/* 461 */       return false;
/*     */     }
/* 463 */     SimpleHistogramDataset that = (SimpleHistogramDataset)obj;
/* 464 */     if (!this.key.equals(that.key)) {
/* 465 */       return false;
/*     */     }
/* 467 */     if (this.adjustForBinSize != that.adjustForBinSize) {
/* 468 */       return false;
/*     */     }
/* 470 */     if (!this.bins.equals(that.bins)) {
/* 471 */       return false;
/*     */     }
/* 473 */     return true;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 486 */     SimpleHistogramDataset clone = (SimpleHistogramDataset)super.clone();
/* 487 */     clone.bins = (List)ObjectUtilities.deepClone(this.bins);
/* 488 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/statistics/SimpleHistogramDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */