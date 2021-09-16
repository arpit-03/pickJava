/*     */ package org.jfree.data.statistics;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.RangeInfo;
/*     */ import org.jfree.data.xy.AbstractXYDataset;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultBoxAndWhiskerXYDataset
/*     */   extends AbstractXYDataset
/*     */   implements BoxAndWhiskerXYDataset, RangeInfo
/*     */ {
/*     */   private Comparable seriesKey;
/*     */   private List dates;
/*     */   private List items;
/*     */   private Number minimumRangeValue;
/*     */   private Number maximumRangeValue;
/*     */   private Range rangeBounds;
/* 104 */   private double outlierCoefficient = 1.5D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   private double faroutCoefficient = 2.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultBoxAndWhiskerXYDataset(Comparable seriesKey) {
/* 123 */     this.seriesKey = seriesKey;
/* 124 */     this.dates = new ArrayList();
/* 125 */     this.items = new ArrayList();
/* 126 */     this.minimumRangeValue = null;
/* 127 */     this.maximumRangeValue = null;
/* 128 */     this.rangeBounds = null;
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
/*     */   public double getOutlierCoefficient() {
/* 145 */     return this.outlierCoefficient;
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
/*     */   public void setOutlierCoefficient(double outlierCoefficient) {
/* 157 */     this.outlierCoefficient = outlierCoefficient;
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
/*     */   public double getFaroutCoefficient() {
/* 171 */     return this.faroutCoefficient;
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
/*     */   public void setFaroutCoefficient(double faroutCoefficient) {
/* 185 */     if (faroutCoefficient > getOutlierCoefficient()) {
/* 186 */       this.faroutCoefficient = faroutCoefficient;
/*     */     } else {
/*     */       
/* 189 */       throw new IllegalArgumentException("Farout value must be greater than the outlier value, which is currently set at: (" + 
/*     */           
/* 191 */           getOutlierCoefficient() + ")");
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
/*     */   public int getSeriesCount() {
/* 204 */     return 1;
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
/*     */   public int getItemCount(int series) {
/* 216 */     return this.dates.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Date date, BoxAndWhiskerItem item) {
/* 227 */     this.dates.add(date);
/* 228 */     this.items.add(item);
/* 229 */     if (this.minimumRangeValue == null) {
/* 230 */       this.minimumRangeValue = item.getMinRegularValue();
/*     */     
/*     */     }
/* 233 */     else if (item.getMinRegularValue().doubleValue() < this.minimumRangeValue
/* 234 */       .doubleValue()) {
/* 235 */       this.minimumRangeValue = item.getMinRegularValue();
/*     */     } 
/*     */     
/* 238 */     if (this.maximumRangeValue == null) {
/* 239 */       this.maximumRangeValue = item.getMaxRegularValue();
/*     */     
/*     */     }
/* 242 */     else if (item.getMaxRegularValue().doubleValue() > this.maximumRangeValue
/* 243 */       .doubleValue()) {
/* 244 */       this.maximumRangeValue = item.getMaxRegularValue();
/*     */     } 
/*     */     
/* 247 */     this
/* 248 */       .rangeBounds = new Range(this.minimumRangeValue.doubleValue(), this.maximumRangeValue.doubleValue());
/* 249 */     fireDatasetChanged();
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
/*     */   public Comparable getSeriesKey(int i) {
/* 261 */     return this.seriesKey;
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
/*     */   public BoxAndWhiskerItem getItem(int series, int item) {
/* 274 */     return this.items.get(item);
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
/*     */   public Number getX(int series, int item) {
/* 290 */     return new Long(((Date)this.dates.get(item)).getTime());
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
/*     */   public Date getXDate(int series, int item) {
/* 304 */     return this.dates.get(item);
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
/*     */   public Number getY(int series, int item) {
/* 320 */     return getMeanValue(series, item);
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
/*     */   public Number getMeanValue(int series, int item) {
/* 333 */     Number result = null;
/* 334 */     BoxAndWhiskerItem stats = this.items.get(item);
/* 335 */     if (stats != null) {
/* 336 */       result = stats.getMean();
/*     */     }
/* 338 */     return result;
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
/*     */   public Number getMedianValue(int series, int item) {
/* 351 */     Number result = null;
/* 352 */     BoxAndWhiskerItem stats = this.items.get(item);
/* 353 */     if (stats != null) {
/* 354 */       result = stats.getMedian();
/*     */     }
/* 356 */     return result;
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
/*     */   public Number getQ1Value(int series, int item) {
/* 369 */     Number result = null;
/* 370 */     BoxAndWhiskerItem stats = this.items.get(item);
/* 371 */     if (stats != null) {
/* 372 */       result = stats.getQ1();
/*     */     }
/* 374 */     return result;
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
/*     */   public Number getQ3Value(int series, int item) {
/* 387 */     Number result = null;
/* 388 */     BoxAndWhiskerItem stats = this.items.get(item);
/* 389 */     if (stats != null) {
/* 390 */       result = stats.getQ3();
/*     */     }
/* 392 */     return result;
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
/*     */   public Number getMinRegularValue(int series, int item) {
/* 405 */     Number result = null;
/* 406 */     BoxAndWhiskerItem stats = this.items.get(item);
/* 407 */     if (stats != null) {
/* 408 */       result = stats.getMinRegularValue();
/*     */     }
/* 410 */     return result;
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
/*     */   public Number getMaxRegularValue(int series, int item) {
/* 423 */     Number result = null;
/* 424 */     BoxAndWhiskerItem stats = this.items.get(item);
/* 425 */     if (stats != null) {
/* 426 */       result = stats.getMaxRegularValue();
/*     */     }
/* 428 */     return result;
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
/*     */   public Number getMinOutlier(int series, int item) {
/* 440 */     Number result = null;
/* 441 */     BoxAndWhiskerItem stats = this.items.get(item);
/* 442 */     if (stats != null) {
/* 443 */       result = stats.getMinOutlier();
/*     */     }
/* 445 */     return result;
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
/*     */   public Number getMaxOutlier(int series, int item) {
/* 459 */     Number result = null;
/* 460 */     BoxAndWhiskerItem stats = this.items.get(item);
/* 461 */     if (stats != null) {
/* 462 */       result = stats.getMaxOutlier();
/*     */     }
/* 464 */     return result;
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
/*     */   public List getOutliers(int series, int item) {
/* 478 */     List result = null;
/* 479 */     BoxAndWhiskerItem stats = this.items.get(item);
/* 480 */     if (stats != null) {
/* 481 */       result = stats.getOutliers();
/*     */     }
/* 483 */     return result;
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
/*     */   public double getRangeLowerBound(boolean includeInterval) {
/* 496 */     double result = Double.NaN;
/* 497 */     if (this.minimumRangeValue != null) {
/* 498 */       result = this.minimumRangeValue.doubleValue();
/*     */     }
/* 500 */     return result;
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
/* 513 */     double result = Double.NaN;
/* 514 */     if (this.maximumRangeValue != null) {
/* 515 */       result = this.maximumRangeValue.doubleValue();
/*     */     }
/* 517 */     return result;
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
/* 530 */     return this.rangeBounds;
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
/* 542 */     if (obj == this) {
/* 543 */       return true;
/*     */     }
/* 545 */     if (!(obj instanceof DefaultBoxAndWhiskerXYDataset)) {
/* 546 */       return false;
/*     */     }
/* 548 */     DefaultBoxAndWhiskerXYDataset that = (DefaultBoxAndWhiskerXYDataset)obj;
/*     */     
/* 550 */     if (!ObjectUtilities.equal(this.seriesKey, that.seriesKey)) {
/* 551 */       return false;
/*     */     }
/* 553 */     if (!this.dates.equals(that.dates)) {
/* 554 */       return false;
/*     */     }
/* 556 */     if (!this.items.equals(that.items)) {
/* 557 */       return false;
/*     */     }
/* 559 */     return true;
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
/* 572 */     DefaultBoxAndWhiskerXYDataset clone = (DefaultBoxAndWhiskerXYDataset)super.clone();
/* 573 */     clone.dates = new ArrayList(this.dates);
/* 574 */     clone.items = new ArrayList(this.items);
/* 575 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/statistics/DefaultBoxAndWhiskerXYDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */