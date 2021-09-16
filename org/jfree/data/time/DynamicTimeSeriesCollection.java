/*     */ package org.jfree.data.time;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.TimeZone;
/*     */ import org.jfree.data.DomainInfo;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.RangeInfo;
/*     */ import org.jfree.data.general.SeriesChangeEvent;
/*     */ import org.jfree.data.xy.AbstractIntervalXYDataset;
/*     */ import org.jfree.data.xy.IntervalXYDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DynamicTimeSeriesCollection
/*     */   extends AbstractIntervalXYDataset
/*     */   implements IntervalXYDataset, DomainInfo, RangeInfo
/*     */ {
/*     */   public static final int START = 0;
/*     */   public static final int MIDDLE = 1;
/*     */   public static final int END = 2;
/* 107 */   private int maximumItemCount = 2000;
/*     */ 
/*     */   
/*     */   protected int historyCount;
/*     */ 
/*     */   
/*     */   private Comparable[] seriesKeys;
/*     */ 
/*     */   
/* 116 */   private Class timePeriodClass = Minute.class;
/*     */   
/*     */   protected RegularTimePeriod[] pointsInTime;
/*     */   private int seriesCount;
/*     */   protected ValueSequence[] valueHistory;
/*     */   protected Calendar workingCalendar;
/*     */   private int position;
/*     */   private boolean domainIsPointsInTime;
/*     */   private int oldestAt;
/*     */   private int newestAt;
/*     */   private long deltaTime;
/*     */   private Long domainStart;
/*     */   private Long domainEnd;
/*     */   private Range domainRange;
/*     */   
/*     */   protected class ValueSequence
/*     */   {
/*     */     float[] dataPoints;
/*     */     
/*     */     public ValueSequence() {
/* 136 */       this(DynamicTimeSeriesCollection.this.maximumItemCount);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ValueSequence(int length) {
/* 145 */       this.dataPoints = new float[length];
/* 146 */       for (int i = 0; i < length; i++) {
/* 147 */         this.dataPoints[i] = 0.0F;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterData(int index, float value) {
/* 158 */       this.dataPoints[index] = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getData(int index) {
/* 169 */       return this.dataPoints[index];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   private Float minValue = new Float(0.0F);
/*     */ 
/*     */   
/* 219 */   private Float maxValue = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Range valueRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DynamicTimeSeriesCollection(int nSeries, int nMoments) {
/* 232 */     this(nSeries, nMoments, new Millisecond(), TimeZone.getDefault());
/* 233 */     this.newestAt = nMoments - 1;
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
/*     */   public DynamicTimeSeriesCollection(int nSeries, int nMoments, TimeZone zone) {
/* 245 */     this(nSeries, nMoments, new Millisecond(), zone);
/* 246 */     this.newestAt = nMoments - 1;
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
/*     */   public DynamicTimeSeriesCollection(int nSeries, int nMoments, RegularTimePeriod timeSample) {
/* 258 */     this(nSeries, nMoments, timeSample, TimeZone.getDefault());
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
/*     */   public DynamicTimeSeriesCollection(int nSeries, int nMoments, RegularTimePeriod timeSample, TimeZone zone) {
/* 273 */     this.maximumItemCount = nMoments;
/* 274 */     this.historyCount = nMoments;
/* 275 */     this.seriesKeys = new Comparable[nSeries];
/*     */     
/* 277 */     for (int i = 0; i < nSeries; i++) {
/* 278 */       this.seriesKeys[i] = "";
/*     */     }
/* 280 */     this.newestAt = nMoments - 1;
/* 281 */     this.valueHistory = new ValueSequence[nSeries];
/* 282 */     this.timePeriodClass = timeSample.getClass();
/*     */ 
/*     */     
/* 285 */     if (this.timePeriodClass == Millisecond.class) {
/* 286 */       this.pointsInTime = (RegularTimePeriod[])new Millisecond[nMoments];
/* 287 */     } else if (this.timePeriodClass == Second.class) {
/* 288 */       this.pointsInTime = (RegularTimePeriod[])new Second[nMoments];
/* 289 */     } else if (this.timePeriodClass == Minute.class) {
/* 290 */       this.pointsInTime = (RegularTimePeriod[])new Minute[nMoments];
/* 291 */     } else if (this.timePeriodClass == Hour.class) {
/* 292 */       this.pointsInTime = (RegularTimePeriod[])new Hour[nMoments];
/*     */     } 
/*     */     
/* 295 */     this.workingCalendar = Calendar.getInstance(zone);
/* 296 */     this.position = 0;
/* 297 */     this.domainIsPointsInTime = true;
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
/*     */   public synchronized long setTimeBase(RegularTimePeriod start) {
/* 312 */     if (this.pointsInTime[0] == null) {
/* 313 */       this.pointsInTime[0] = start;
/* 314 */       for (int i = 1; i < this.historyCount; i++) {
/* 315 */         this.pointsInTime[i] = this.pointsInTime[i - 1].next();
/*     */       }
/*     */     } 
/* 318 */     long oldestL = this.pointsInTime[0].getFirstMillisecond(this.workingCalendar);
/*     */     
/* 320 */     long nextL = this.pointsInTime[1].getFirstMillisecond(this.workingCalendar);
/*     */     
/* 322 */     this.deltaTime = nextL - oldestL;
/* 323 */     this.oldestAt = 0;
/* 324 */     this.newestAt = this.historyCount - 1;
/* 325 */     findDomainLimits();
/* 326 */     return this.deltaTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void findDomainLimits() {
/* 334 */     long endL, startL = getOldestTime().getFirstMillisecond(this.workingCalendar);
/*     */     
/* 336 */     if (this.domainIsPointsInTime) {
/* 337 */       endL = getNewestTime().getFirstMillisecond(this.workingCalendar);
/*     */     } else {
/*     */       
/* 340 */       endL = getNewestTime().getLastMillisecond(this.workingCalendar);
/*     */     } 
/* 342 */     this.domainStart = new Long(startL);
/* 343 */     this.domainEnd = new Long(endL);
/* 344 */     this.domainRange = new Range(startL, endL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPosition() {
/* 353 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(int position) {
/* 362 */     this.position = position;
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
/*     */   public void addSeries(float[] values, int seriesNumber, Comparable seriesKey) {
/* 379 */     invalidateRangeInfo();
/*     */     
/* 381 */     if (values == null) {
/* 382 */       throw new IllegalArgumentException("TimeSeriesDataset.addSeries(): cannot add null array of values.");
/*     */     }
/*     */     
/* 385 */     if (seriesNumber >= this.valueHistory.length) {
/* 386 */       throw new IllegalArgumentException("TimeSeriesDataset.addSeries(): cannot add more series than specified in c'tor");
/*     */     }
/*     */     
/* 389 */     if (this.valueHistory[seriesNumber] == null) {
/* 390 */       this.valueHistory[seriesNumber] = new ValueSequence(this.historyCount);
/*     */       
/* 392 */       this.seriesCount++;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 397 */     int srcLength = values.length;
/* 398 */     int copyLength = this.historyCount;
/* 399 */     boolean fillNeeded = false;
/* 400 */     if (srcLength < this.historyCount) {
/* 401 */       fillNeeded = true;
/* 402 */       copyLength = srcLength;
/*     */     } 
/*     */     int i;
/* 405 */     for (i = 0; i < copyLength; i++)
/*     */     {
/* 407 */       this.valueHistory[seriesNumber].enterData(i, values[i]);
/*     */     }
/* 409 */     if (fillNeeded) {
/* 410 */       for (i = copyLength; i < this.historyCount; i++) {
/* 411 */         this.valueHistory[seriesNumber].enterData(i, 0.0F);
/*     */       }
/*     */     }
/*     */     
/* 415 */     if (seriesKey != null) {
/* 416 */       this.seriesKeys[seriesNumber] = seriesKey;
/*     */     }
/* 418 */     fireSeriesChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeriesKey(int seriesNumber, Comparable key) {
/* 428 */     this.seriesKeys[seriesNumber] = key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addValue(int seriesNumber, int index, float value) {
/* 439 */     invalidateRangeInfo();
/* 440 */     if (seriesNumber >= this.valueHistory.length) {
/* 441 */       throw new IllegalArgumentException("TimeSeriesDataset.addValue(): series #" + seriesNumber + "unspecified in c'tor");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 446 */     if (this.valueHistory[seriesNumber] == null) {
/* 447 */       this.valueHistory[seriesNumber] = new ValueSequence(this.historyCount);
/*     */       
/* 449 */       this.seriesCount++;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 454 */     this.valueHistory[seriesNumber].enterData(index, value);
/*     */     
/* 456 */     fireSeriesChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeriesCount() {
/* 466 */     return this.seriesCount;
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
/*     */   public int getItemCount(int series) {
/* 481 */     return this.historyCount;
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
/*     */   protected int translateGet(int toFetch) {
/* 494 */     if (this.oldestAt == 0) {
/* 495 */       return toFetch;
/*     */     }
/*     */     
/* 498 */     int newIndex = toFetch + this.oldestAt;
/* 499 */     if (newIndex >= this.historyCount) {
/* 500 */       newIndex -= this.historyCount;
/*     */     }
/* 502 */     return newIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int offsetFromNewest(int delta) {
/* 513 */     return wrapOffset(this.newestAt + delta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int offsetFromOldest(int delta) {
/* 524 */     return wrapOffset(this.oldestAt + delta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int wrapOffset(int protoIndex) {
/* 535 */     int tmp = protoIndex;
/* 536 */     if (tmp >= this.historyCount) {
/* 537 */       tmp -= this.historyCount;
/*     */     }
/* 539 */     else if (tmp < 0) {
/* 540 */       tmp += this.historyCount;
/*     */     } 
/* 542 */     return tmp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized RegularTimePeriod advanceTime() {
/* 553 */     RegularTimePeriod nextInstant = this.pointsInTime[this.newestAt].next();
/* 554 */     this.newestAt = this.oldestAt;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 561 */     boolean extremaChanged = false;
/* 562 */     float oldMax = 0.0F;
/* 563 */     if (this.maxValue != null) {
/* 564 */       oldMax = this.maxValue.floatValue();
/*     */     }
/* 566 */     for (int s = 0; s < getSeriesCount(); s++) {
/* 567 */       if (this.valueHistory[s].getData(this.oldestAt) == oldMax) {
/* 568 */         extremaChanged = true;
/*     */       }
/* 570 */       if (extremaChanged) {
/*     */         break;
/*     */       }
/*     */     } 
/* 574 */     if (extremaChanged) {
/* 575 */       invalidateRangeInfo();
/*     */     }
/*     */     
/* 578 */     float wiper = 0.0F;
/* 579 */     for (int i = 0; i < getSeriesCount(); i++) {
/* 580 */       this.valueHistory[i].enterData(this.newestAt, wiper);
/*     */     }
/*     */     
/* 583 */     this.pointsInTime[this.newestAt] = nextInstant;
/*     */     
/* 585 */     this.oldestAt++;
/* 586 */     if (this.oldestAt >= this.historyCount) {
/* 587 */       this.oldestAt = 0;
/*     */     }
/*     */     
/* 590 */     long startL = this.domainStart.longValue();
/* 591 */     this.domainStart = new Long(startL + this.deltaTime);
/* 592 */     long endL = this.domainEnd.longValue();
/* 593 */     this.domainEnd = new Long(endL + this.deltaTime);
/* 594 */     this.domainRange = new Range(startL, endL);
/* 595 */     fireSeriesChanged();
/* 596 */     return nextInstant;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidateRangeInfo() {
/* 605 */     this.maxValue = null;
/* 606 */     this.valueRange = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double findMaxValue() {
/* 615 */     double max = 0.0D;
/* 616 */     for (int s = 0; s < getSeriesCount(); s++) {
/* 617 */       for (int i = 0; i < this.historyCount; i++) {
/* 618 */         double tmp = getYValue(s, i);
/* 619 */         if (tmp > max) {
/* 620 */           max = tmp;
/*     */         }
/*     */       } 
/*     */     } 
/* 624 */     return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOldestIndex() {
/* 635 */     return this.oldestAt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNewestIndex() {
/* 644 */     return this.newestAt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendData(float[] newData) {
/* 655 */     int nDataPoints = newData.length;
/* 656 */     if (nDataPoints > this.valueHistory.length) {
/* 657 */       throw new IllegalArgumentException("More data than series to put them in");
/*     */     }
/*     */ 
/*     */     
/* 661 */     for (int s = 0; s < nDataPoints; s++) {
/*     */ 
/*     */       
/* 664 */       if (this.valueHistory[s] == null) {
/* 665 */         this.valueHistory[s] = new ValueSequence(this.historyCount);
/*     */       }
/* 667 */       this.valueHistory[s].enterData(this.newestAt, newData[s]);
/*     */     } 
/* 669 */     fireSeriesChanged();
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
/*     */   public void appendData(float[] newData, int insertionIndex, int refresh) {
/* 681 */     int nDataPoints = newData.length;
/* 682 */     if (nDataPoints > this.valueHistory.length) {
/* 683 */       throw new IllegalArgumentException("More data than series to put them in");
/*     */     }
/*     */     
/* 686 */     for (int s = 0; s < nDataPoints; s++) {
/* 687 */       if (this.valueHistory[s] == null) {
/* 688 */         this.valueHistory[s] = new ValueSequence(this.historyCount);
/*     */       }
/* 690 */       this.valueHistory[s].enterData(insertionIndex, newData[s]);
/*     */     } 
/*     */     
/* 693 */     insertionIndex++;
/* 694 */     if (refresh > 0 && insertionIndex % refresh == 0) {
/* 695 */       fireSeriesChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegularTimePeriod getNewestTime() {
/* 706 */     return this.pointsInTime[this.newestAt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegularTimePeriod getOldestTime() {
/* 715 */     return this.pointsInTime[this.oldestAt];
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
/* 730 */     RegularTimePeriod tp = this.pointsInTime[translateGet(item)];
/* 731 */     return new Long(getX(tp));
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
/* 746 */     ValueSequence values = this.valueHistory[series];
/* 747 */     return values.getData(translateGet(item));
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
/* 760 */     return new Float(getYValue(series, item));
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
/* 773 */     RegularTimePeriod tp = this.pointsInTime[translateGet(item)];
/* 774 */     return new Long(tp.getFirstMillisecond(this.workingCalendar));
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
/* 787 */     RegularTimePeriod tp = this.pointsInTime[translateGet(item)];
/* 788 */     return new Long(tp.getLastMillisecond(this.workingCalendar));
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
/* 801 */     return getY(series, item);
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
/* 814 */     return getY(series, item);
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
/*     */   
/*     */   public Comparable getSeriesKey(int series) {
/* 837 */     return this.seriesKeys[series];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fireSeriesChanged() {
/* 844 */     seriesChanged(new SeriesChangeEvent(this));
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
/*     */   public double getDomainLowerBound(boolean includeInterval) {
/* 862 */     return this.domainStart.doubleValue();
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
/*     */   public double getDomainUpperBound(boolean includeInterval) {
/* 876 */     return this.domainEnd.doubleValue();
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
/*     */   public Range getDomainBounds(boolean includeInterval) {
/* 890 */     if (this.domainRange == null) {
/* 891 */       findDomainLimits();
/*     */     }
/* 893 */     return this.domainRange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long getX(RegularTimePeriod period) {
/* 904 */     switch (this.position) {
/*     */       case 0:
/* 906 */         return period.getFirstMillisecond(this.workingCalendar);
/*     */       case 1:
/* 908 */         return period.getMiddleMillisecond(this.workingCalendar);
/*     */       case 2:
/* 910 */         return period.getLastMillisecond(this.workingCalendar);
/*     */     } 
/* 912 */     return period.getMiddleMillisecond(this.workingCalendar);
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
/*     */   public double getRangeLowerBound(boolean includeInterval) {
/* 932 */     double result = Double.NaN;
/* 933 */     if (this.minValue != null) {
/* 934 */       result = this.minValue.doubleValue();
/*     */     }
/* 936 */     return result;
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
/* 949 */     double result = Double.NaN;
/* 950 */     if (this.maxValue != null) {
/* 951 */       result = this.maxValue.doubleValue();
/*     */     }
/* 953 */     return result;
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
/* 966 */     if (this.valueRange == null) {
/* 967 */       double max = getRangeUpperBound(includeInterval);
/* 968 */       this.valueRange = new Range(0.0D, max);
/*     */     } 
/* 970 */     return this.valueRange;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/DynamicTimeSeriesCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */