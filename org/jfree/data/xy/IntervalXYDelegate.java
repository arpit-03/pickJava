/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.DomainInfo;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.general.DatasetChangeEvent;
/*     */ import org.jfree.data.general.DatasetChangeListener;
/*     */ import org.jfree.data.general.DatasetUtilities;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntervalXYDelegate
/*     */   implements DatasetChangeListener, DomainInfo, Serializable, Cloneable, PublicCloneable
/*     */ {
/*     */   private static final long serialVersionUID = -685166711639592857L;
/*     */   private XYDataset dataset;
/*     */   private boolean autoWidth;
/*     */   private double intervalPositionFactor;
/*     */   private double fixedIntervalWidth;
/*     */   private double autoIntervalWidth;
/*     */   
/*     */   public IntervalXYDelegate(XYDataset dataset) {
/* 123 */     this(dataset, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntervalXYDelegate(XYDataset dataset, boolean autoWidth) {
/* 134 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 135 */     this.dataset = dataset;
/* 136 */     this.autoWidth = autoWidth;
/* 137 */     this.intervalPositionFactor = 0.5D;
/* 138 */     this.autoIntervalWidth = Double.POSITIVE_INFINITY;
/* 139 */     this.fixedIntervalWidth = 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoWidth() {
/* 149 */     return this.autoWidth;
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
/*     */   public void setAutoWidth(boolean b) {
/* 164 */     this.autoWidth = b;
/* 165 */     if (b) {
/* 166 */       this.autoIntervalWidth = recalculateInterval();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getIntervalPositionFactor() {
/* 176 */     return this.intervalPositionFactor;
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
/*     */   public void setIntervalPositionFactor(double d) {
/* 196 */     if (d < 0.0D || 1.0D < d) {
/* 197 */       throw new IllegalArgumentException("Argument 'd' outside valid range.");
/*     */     }
/*     */     
/* 200 */     this.intervalPositionFactor = d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFixedIntervalWidth() {
/* 209 */     return this.fixedIntervalWidth;
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
/*     */   public void setFixedIntervalWidth(double w) {
/* 224 */     if (w < 0.0D) {
/* 225 */       throw new IllegalArgumentException("Negative 'w' argument.");
/*     */     }
/* 227 */     this.fixedIntervalWidth = w;
/* 228 */     this.autoWidth = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getIntervalWidth() {
/* 239 */     if (isAutoWidth() && !Double.isInfinite(this.autoIntervalWidth))
/*     */     {
/*     */       
/* 242 */       return this.autoIntervalWidth;
/*     */     }
/*     */ 
/*     */     
/* 246 */     return this.fixedIntervalWidth;
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
/*     */   public Number getStartX(int series, int item) {
/* 261 */     Number startX = null;
/* 262 */     Number x = this.dataset.getX(series, item);
/* 263 */     if (x != null)
/*     */     {
/* 265 */       startX = new Double(x.doubleValue() - getIntervalPositionFactor() * getIntervalWidth());
/*     */     }
/* 267 */     return startX;
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
/*     */   public double getStartXValue(int series, int item) {
/* 282 */     return this.dataset.getXValue(series, item) - getIntervalPositionFactor() * getIntervalWidth();
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
/*     */   public Number getEndX(int series, int item) {
/* 296 */     Number endX = null;
/* 297 */     Number x = this.dataset.getX(series, item);
/* 298 */     if (x != null)
/*     */     {
/* 300 */       endX = new Double(x.doubleValue() + (1.0D - getIntervalPositionFactor()) * getIntervalWidth());
/*     */     }
/* 302 */     return endX;
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
/*     */   public double getEndXValue(int series, int item) {
/* 317 */     return this.dataset.getXValue(series, item) + (1.0D - getIntervalPositionFactor()) * getIntervalWidth();
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
/* 330 */     double result = Double.NaN;
/* 331 */     Range r = getDomainBounds(includeInterval);
/* 332 */     if (r != null) {
/* 333 */       result = r.getLowerBound();
/*     */     }
/* 335 */     return result;
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
/* 348 */     double result = Double.NaN;
/* 349 */     Range r = getDomainBounds(includeInterval);
/* 350 */     if (r != null) {
/* 351 */       result = r.getUpperBound();
/*     */     }
/* 353 */     return result;
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
/*     */   public Range getDomainBounds(boolean includeInterval) {
/* 369 */     Range range = DatasetUtilities.findDomainBounds(this.dataset, false);
/* 370 */     if (includeInterval && range != null) {
/* 371 */       double lowerAdj = getIntervalWidth() * getIntervalPositionFactor();
/* 372 */       double upperAdj = getIntervalWidth() - lowerAdj;
/*     */       
/* 374 */       range = new Range(range.getLowerBound() - lowerAdj, range.getUpperBound() + upperAdj);
/*     */     } 
/* 376 */     return range;
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
/*     */   public void datasetChanged(DatasetChangeEvent e) {
/* 392 */     if (this.autoWidth) {
/* 393 */       this.autoIntervalWidth = recalculateInterval();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double recalculateInterval() {
/* 403 */     double result = Double.POSITIVE_INFINITY;
/* 404 */     int seriesCount = this.dataset.getSeriesCount();
/* 405 */     for (int series = 0; series < seriesCount; series++) {
/* 406 */       result = Math.min(result, calculateIntervalForSeries(series));
/*     */     }
/* 408 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double calculateIntervalForSeries(int series) {
/* 419 */     double result = Double.POSITIVE_INFINITY;
/* 420 */     int itemCount = this.dataset.getItemCount(series);
/* 421 */     if (itemCount > 1) {
/* 422 */       double prev = this.dataset.getXValue(series, 0);
/* 423 */       for (int item = 1; item < itemCount; item++) {
/* 424 */         double x = this.dataset.getXValue(series, item);
/* 425 */         result = Math.min(result, x - prev);
/* 426 */         prev = x;
/*     */       } 
/*     */     } 
/* 429 */     return result;
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
/*     */   public boolean equals(Object obj) {
/* 445 */     if (obj == this) {
/* 446 */       return true;
/*     */     }
/* 448 */     if (!(obj instanceof IntervalXYDelegate)) {
/* 449 */       return false;
/*     */     }
/* 451 */     IntervalXYDelegate that = (IntervalXYDelegate)obj;
/* 452 */     if (this.autoWidth != that.autoWidth) {
/* 453 */       return false;
/*     */     }
/* 455 */     if (this.intervalPositionFactor != that.intervalPositionFactor) {
/* 456 */       return false;
/*     */     }
/* 458 */     if (this.fixedIntervalWidth != that.fixedIntervalWidth) {
/* 459 */       return false;
/*     */     }
/* 461 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 471 */     return super.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 481 */     int hash = 5;
/* 482 */     hash = HashUtilities.hashCode(hash, this.autoWidth);
/* 483 */     hash = HashUtilities.hashCode(hash, this.intervalPositionFactor);
/* 484 */     hash = HashUtilities.hashCode(hash, this.fixedIntervalWidth);
/* 485 */     return hash;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/IntervalXYDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */