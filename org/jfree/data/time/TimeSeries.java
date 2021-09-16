/*      */ package org.jfree.data.time;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.TimeZone;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.general.Series;
/*      */ import org.jfree.data.general.SeriesException;
/*      */ import org.jfree.util.ObjectUtilities;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TimeSeries
/*      */   extends Series
/*      */   implements Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -5032960206869675528L;
/*      */   protected static final String DEFAULT_DOMAIN_DESCRIPTION = "Time";
/*      */   protected static final String DEFAULT_RANGE_DESCRIPTION = "Value";
/*      */   private String domain;
/*      */   private String range;
/*      */   protected Class timePeriodClass;
/*      */   protected List data;
/*      */   private int maximumItemCount;
/*      */   private long maximumItemAge;
/*      */   private double minY;
/*      */   private double maxY;
/*      */   
/*      */   public TimeSeries(Comparable name) {
/*  172 */     this(name, "Time", "Value");
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
/*      */   public TimeSeries(Comparable name, String domain, String range) {
/*  189 */     super(name);
/*  190 */     this.domain = domain;
/*  191 */     this.range = range;
/*  192 */     this.timePeriodClass = null;
/*  193 */     this.data = new ArrayList();
/*  194 */     this.maximumItemCount = Integer.MAX_VALUE;
/*  195 */     this.maximumItemAge = Long.MAX_VALUE;
/*  196 */     this.minY = Double.NaN;
/*  197 */     this.maxY = Double.NaN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDomainDescription() {
/*  208 */     return this.domain;
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
/*      */   public void setDomainDescription(String description) {
/*  221 */     String old = this.domain;
/*  222 */     this.domain = description;
/*  223 */     firePropertyChange("Domain", old, description);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRangeDescription() {
/*  234 */     return this.range;
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
/*      */   public void setRangeDescription(String description) {
/*  246 */     String old = this.range;
/*  247 */     this.range = description;
/*  248 */     firePropertyChange("Range", old, description);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemCount() {
/*  258 */     return this.data.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List getItems() {
/*  269 */     return Collections.unmodifiableList(this.data);
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
/*      */   public int getMaximumItemCount() {
/*  281 */     return this.maximumItemCount;
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
/*      */   public void setMaximumItemCount(int maximum) {
/*  296 */     if (maximum < 0) {
/*  297 */       throw new IllegalArgumentException("Negative 'maximum' argument.");
/*      */     }
/*  299 */     this.maximumItemCount = maximum;
/*  300 */     int count = this.data.size();
/*  301 */     if (count > maximum) {
/*  302 */       delete(0, count - maximum - 1);
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
/*      */   public long getMaximumItemAge() {
/*  314 */     return this.maximumItemAge;
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
/*      */   public void setMaximumItemAge(long periods) {
/*  330 */     if (periods < 0L) {
/*  331 */       throw new IllegalArgumentException("Negative 'periods' argument.");
/*      */     }
/*  333 */     this.maximumItemAge = periods;
/*  334 */     removeAgedItems(true);
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
/*      */   public Range findValueRange() {
/*  350 */     if (this.data.isEmpty()) {
/*  351 */       return null;
/*      */     }
/*  353 */     return new Range(this.minY, this.maxY);
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
/*      */   public Range findValueRange(Range xRange, TimeZone timeZone) {
/*  371 */     return findValueRange(xRange, TimePeriodAnchor.MIDDLE, timeZone);
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
/*      */   public Range findValueRange(Range xRange, TimePeriodAnchor xAnchor, TimeZone zone) {
/*  391 */     ParamChecks.nullNotPermitted(xRange, "xRange");
/*  392 */     ParamChecks.nullNotPermitted(xAnchor, "xAnchor");
/*  393 */     ParamChecks.nullNotPermitted(zone, "zone");
/*  394 */     if (this.data.isEmpty()) {
/*  395 */       return null;
/*      */     }
/*  397 */     Calendar calendar = Calendar.getInstance(zone);
/*      */ 
/*      */     
/*  400 */     double lowY = Double.POSITIVE_INFINITY;
/*  401 */     double highY = Double.NEGATIVE_INFINITY;
/*  402 */     for (int i = 0; i < this.data.size(); i++) {
/*  403 */       TimeSeriesDataItem item = this.data.get(i);
/*  404 */       long millis = item.getPeriod().getMillisecond(xAnchor, calendar);
/*  405 */       if (xRange.contains(millis)) {
/*  406 */         Number n = item.getValue();
/*  407 */         if (n != null) {
/*  408 */           double v = n.doubleValue();
/*  409 */           lowY = Math.min(lowY, v);
/*  410 */           highY = Math.max(highY, v);
/*      */         } 
/*      */       } 
/*      */     } 
/*  414 */     if (Double.isInfinite(lowY) && Double.isInfinite(highY)) {
/*  415 */       if (lowY < highY) {
/*  416 */         return new Range(lowY, highY);
/*      */       }
/*  418 */       return new Range(Double.NaN, Double.NaN);
/*      */     } 
/*      */     
/*  421 */     return new Range(lowY, highY);
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
/*      */   public double getMinY() {
/*  437 */     return this.minY;
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
/*      */   public double getMaxY() {
/*  453 */     return this.maxY;
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
/*      */   public Class getTimePeriodClass() {
/*  467 */     return this.timePeriodClass;
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
/*      */   public TimeSeriesDataItem getDataItem(int index) {
/*  480 */     TimeSeriesDataItem item = this.data.get(index);
/*  481 */     return (TimeSeriesDataItem)item.clone();
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
/*      */   public TimeSeriesDataItem getDataItem(RegularTimePeriod period) {
/*  497 */     int index = getIndex(period);
/*  498 */     if (index >= 0) {
/*  499 */       return getDataItem(index);
/*      */     }
/*  501 */     return null;
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
/*      */   TimeSeriesDataItem getRawDataItem(int index) {
/*  518 */     return this.data.get(index);
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
/*      */   TimeSeriesDataItem getRawDataItem(RegularTimePeriod period) {
/*  535 */     int index = getIndex(period);
/*  536 */     if (index >= 0) {
/*  537 */       return this.data.get(index);
/*      */     }
/*  539 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RegularTimePeriod getTimePeriod(int index) {
/*  550 */     return getRawDataItem(index).getPeriod();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RegularTimePeriod getNextTimePeriod() {
/*  560 */     RegularTimePeriod last = getTimePeriod(getItemCount() - 1);
/*  561 */     return last.next();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection getTimePeriods() {
/*  570 */     Collection<RegularTimePeriod> result = new ArrayList();
/*  571 */     for (int i = 0; i < getItemCount(); i++) {
/*  572 */       result.add(getTimePeriod(i));
/*      */     }
/*  574 */     return result;
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
/*      */   public Collection getTimePeriodsUniqueToOtherSeries(TimeSeries series) {
/*  586 */     Collection<RegularTimePeriod> result = new ArrayList();
/*  587 */     for (int i = 0; i < series.getItemCount(); i++) {
/*  588 */       RegularTimePeriod period = series.getTimePeriod(i);
/*  589 */       int index = getIndex(period);
/*  590 */       if (index < 0) {
/*  591 */         result.add(period);
/*      */       }
/*      */     } 
/*  594 */     return result;
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
/*      */   public int getIndex(RegularTimePeriod period) {
/*  606 */     ParamChecks.nullNotPermitted(period, "period");
/*  607 */     TimeSeriesDataItem dummy = new TimeSeriesDataItem(period, -2.147483648E9D);
/*      */     
/*  609 */     return Collections.binarySearch(this.data, dummy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number getValue(int index) {
/*  620 */     return getRawDataItem(index).getValue();
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
/*      */   public Number getValue(RegularTimePeriod period) {
/*  632 */     int index = getIndex(period);
/*  633 */     if (index >= 0) {
/*  634 */       return getValue(index);
/*      */     }
/*  636 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(TimeSeriesDataItem item) {
/*  647 */     add(item, true);
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
/*      */   public void add(TimeSeriesDataItem item, boolean notify) {
/*  659 */     ParamChecks.nullNotPermitted(item, "item");
/*  660 */     item = (TimeSeriesDataItem)item.clone();
/*  661 */     Class<?> c = item.getPeriod().getClass();
/*  662 */     if (this.timePeriodClass == null) {
/*  663 */       this.timePeriodClass = c;
/*      */     }
/*  665 */     else if (!this.timePeriodClass.equals(c)) {
/*  666 */       StringBuilder b = new StringBuilder();
/*  667 */       b.append("You are trying to add data where the time period class ");
/*  668 */       b.append("is ");
/*  669 */       b.append(item.getPeriod().getClass().getName());
/*  670 */       b.append(", but the TimeSeries is expecting an instance of ");
/*  671 */       b.append(this.timePeriodClass.getName());
/*  672 */       b.append(".");
/*  673 */       throw new SeriesException(b.toString());
/*      */     } 
/*      */ 
/*      */     
/*  677 */     boolean added = false;
/*  678 */     int count = getItemCount();
/*  679 */     if (count == 0) {
/*  680 */       this.data.add(item);
/*  681 */       added = true;
/*      */     } else {
/*      */       
/*  684 */       RegularTimePeriod last = getTimePeriod(getItemCount() - 1);
/*  685 */       if (item.getPeriod().compareTo((T)last) > 0) {
/*  686 */         this.data.add(item);
/*  687 */         added = true;
/*      */       } else {
/*      */         
/*  690 */         int index = Collections.binarySearch(this.data, item);
/*  691 */         if (index < 0) {
/*  692 */           this.data.add(-index - 1, item);
/*  693 */           added = true;
/*      */         } else {
/*      */           
/*  696 */           StringBuilder b = new StringBuilder();
/*  697 */           b.append("You are attempting to add an observation for ");
/*  698 */           b.append("the time period ");
/*  699 */           b.append(item.getPeriod().toString());
/*  700 */           b.append(" but the series already contains an observation");
/*  701 */           b.append(" for that time period. Duplicates are not ");
/*  702 */           b.append("permitted.  Try using the addOrUpdate() method.");
/*  703 */           throw new SeriesException(b.toString());
/*      */         } 
/*      */       } 
/*      */     } 
/*  707 */     if (added) {
/*  708 */       updateBoundsForAddedItem(item);
/*      */       
/*  710 */       if (getItemCount() > this.maximumItemCount) {
/*  711 */         TimeSeriesDataItem d = this.data.remove(0);
/*  712 */         updateBoundsForRemovedItem(d);
/*      */       } 
/*      */       
/*  715 */       removeAgedItems(false);
/*      */ 
/*      */       
/*  718 */       if (notify) {
/*  719 */         fireSeriesChanged();
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
/*      */   
/*      */   public void add(RegularTimePeriod period, double value) {
/*  734 */     add(period, value, true);
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
/*      */   public void add(RegularTimePeriod period, double value, boolean notify) {
/*  747 */     TimeSeriesDataItem item = new TimeSeriesDataItem(period, value);
/*  748 */     add(item, notify);
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
/*      */   public void add(RegularTimePeriod period, Number value) {
/*  761 */     add(period, value, true);
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
/*      */   public void add(RegularTimePeriod period, Number value, boolean notify) {
/*  774 */     TimeSeriesDataItem item = new TimeSeriesDataItem(period, value);
/*  775 */     add(item, notify);
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
/*      */   public void update(RegularTimePeriod period, double value) {
/*  788 */     update(period, new Double(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(RegularTimePeriod period, Number value) {
/*  799 */     TimeSeriesDataItem temp = new TimeSeriesDataItem(period, value);
/*  800 */     int index = Collections.binarySearch(this.data, temp);
/*  801 */     if (index < 0) {
/*  802 */       throw new SeriesException("There is no existing value for the specified 'period'.");
/*      */     }
/*      */     
/*  805 */     update(index, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(int index, Number value) {
/*  815 */     TimeSeriesDataItem item = this.data.get(index);
/*  816 */     boolean iterate = false;
/*  817 */     Number oldYN = item.getValue();
/*  818 */     if (oldYN != null) {
/*  819 */       double oldY = oldYN.doubleValue();
/*  820 */       if (!Double.isNaN(oldY)) {
/*  821 */         iterate = (oldY <= this.minY || oldY >= this.maxY);
/*      */       }
/*      */     } 
/*  824 */     item.setValue(value);
/*  825 */     if (iterate) {
/*  826 */       updateMinMaxYByIteration();
/*      */     }
/*  828 */     else if (value != null) {
/*  829 */       double yy = value.doubleValue();
/*  830 */       this.minY = minIgnoreNaN(this.minY, yy);
/*  831 */       this.maxY = maxIgnoreNaN(this.maxY, yy);
/*      */     } 
/*  833 */     fireSeriesChanged();
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
/*      */   public TimeSeries addAndOrUpdate(TimeSeries series) {
/*  846 */     TimeSeries overwritten = new TimeSeries("Overwritten values from: " + getKey());
/*  847 */     for (int i = 0; i < series.getItemCount(); i++) {
/*  848 */       TimeSeriesDataItem item = series.getRawDataItem(i);
/*  849 */       TimeSeriesDataItem oldItem = addOrUpdate(item.getPeriod(), item
/*  850 */           .getValue());
/*  851 */       if (oldItem != null) {
/*  852 */         overwritten.add(oldItem);
/*      */       }
/*      */     } 
/*  855 */     return overwritten;
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
/*      */   public TimeSeriesDataItem addOrUpdate(RegularTimePeriod period, double value) {
/*  871 */     return addOrUpdate(period, new Double(value));
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
/*      */   public TimeSeriesDataItem addOrUpdate(RegularTimePeriod period, Number value) {
/*  887 */     return addOrUpdate(new TimeSeriesDataItem(period, value));
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
/*      */   public TimeSeriesDataItem addOrUpdate(TimeSeriesDataItem item) {
/*  903 */     ParamChecks.nullNotPermitted(item, "item");
/*  904 */     Class<?> periodClass = item.getPeriod().getClass();
/*  905 */     if (this.timePeriodClass == null) {
/*  906 */       this.timePeriodClass = periodClass;
/*      */     }
/*  908 */     else if (!this.timePeriodClass.equals(periodClass)) {
/*      */ 
/*      */ 
/*      */       
/*  912 */       String msg = "You are trying to add data where the time period class is " + periodClass.getName() + ", but the TimeSeries is expecting an instance of " + this.timePeriodClass.getName() + ".";
/*  913 */       throw new SeriesException(msg);
/*      */     } 
/*  915 */     TimeSeriesDataItem overwritten = null;
/*  916 */     int index = Collections.binarySearch(this.data, item);
/*  917 */     if (index >= 0) {
/*      */       
/*  919 */       TimeSeriesDataItem existing = this.data.get(index);
/*  920 */       overwritten = (TimeSeriesDataItem)existing.clone();
/*      */ 
/*      */       
/*  923 */       boolean iterate = false;
/*  924 */       Number oldYN = existing.getValue();
/*  925 */       double oldY = (oldYN != null) ? oldYN.doubleValue() : Double.NaN;
/*  926 */       if (!Double.isNaN(oldY)) {
/*  927 */         iterate = (oldY <= this.minY || oldY >= this.maxY);
/*      */       }
/*  929 */       existing.setValue(item.getValue());
/*  930 */       if (iterate) {
/*  931 */         updateMinMaxYByIteration();
/*      */       }
/*  933 */       else if (item.getValue() != null) {
/*  934 */         double yy = item.getValue().doubleValue();
/*  935 */         this.minY = minIgnoreNaN(this.minY, yy);
/*  936 */         this.maxY = maxIgnoreNaN(this.maxY, yy);
/*      */       } 
/*      */     } else {
/*      */       
/*  940 */       item = (TimeSeriesDataItem)item.clone();
/*  941 */       this.data.add(-index - 1, item);
/*  942 */       updateBoundsForAddedItem(item);
/*      */ 
/*      */       
/*  945 */       if (getItemCount() > this.maximumItemCount) {
/*  946 */         TimeSeriesDataItem d = this.data.remove(0);
/*  947 */         updateBoundsForRemovedItem(d);
/*      */       } 
/*      */     } 
/*  950 */     removeAgedItems(false);
/*      */ 
/*      */     
/*  953 */     fireSeriesChanged();
/*  954 */     return overwritten;
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
/*      */   public void removeAgedItems(boolean notify) {
/*  969 */     if (getItemCount() > 1) {
/*  970 */       long latest = getTimePeriod(getItemCount() - 1).getSerialIndex();
/*  971 */       boolean removed = false;
/*  972 */       while (latest - getTimePeriod(0).getSerialIndex() > this.maximumItemAge) {
/*      */         
/*  974 */         this.data.remove(0);
/*  975 */         removed = true;
/*      */       } 
/*  977 */       if (removed) {
/*  978 */         updateMinMaxYByIteration();
/*  979 */         if (notify) {
/*  980 */           fireSeriesChanged();
/*      */         }
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
/*      */ 
/*      */   
/*      */   public void removeAgedItems(long latest, boolean notify) {
/*  997 */     if (this.data.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/* 1001 */     long index = Long.MAX_VALUE;
/*      */     try {
/* 1003 */       Method m = RegularTimePeriod.class.getDeclaredMethod("createInstance", new Class[] { Class.class, Date.class, TimeZone.class });
/*      */ 
/*      */       
/* 1006 */       RegularTimePeriod newest = (RegularTimePeriod)m.invoke(this.timePeriodClass, new Object[] { this.timePeriodClass, new Date(latest), 
/*      */             
/* 1008 */             TimeZone.getDefault() });
/* 1009 */       index = newest.getSerialIndex();
/*      */     }
/* 1011 */     catch (NoSuchMethodException e) {
/* 1012 */       throw new RuntimeException(e);
/*      */     }
/* 1014 */     catch (IllegalAccessException e) {
/* 1015 */       throw new RuntimeException(e);
/*      */     }
/* 1017 */     catch (InvocationTargetException e) {
/* 1018 */       throw new RuntimeException(e);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1023 */     boolean removed = false;
/* 1024 */     while (getItemCount() > 0 && index - 
/* 1025 */       getTimePeriod(0).getSerialIndex() > this.maximumItemAge) {
/* 1026 */       this.data.remove(0);
/* 1027 */       removed = true;
/*      */     } 
/* 1029 */     if (removed) {
/* 1030 */       updateMinMaxYByIteration();
/* 1031 */       if (notify) {
/* 1032 */         fireSeriesChanged();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/* 1042 */     if (this.data.size() > 0) {
/* 1043 */       this.data.clear();
/* 1044 */       this.timePeriodClass = null;
/* 1045 */       this.minY = Double.NaN;
/* 1046 */       this.maxY = Double.NaN;
/* 1047 */       fireSeriesChanged();
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
/*      */   public void delete(RegularTimePeriod period) {
/* 1060 */     int index = getIndex(period);
/* 1061 */     if (index >= 0) {
/* 1062 */       TimeSeriesDataItem item = this.data.remove(index);
/*      */       
/* 1064 */       updateBoundsForRemovedItem(item);
/* 1065 */       if (this.data.isEmpty()) {
/* 1066 */         this.timePeriodClass = null;
/*      */       }
/* 1068 */       fireSeriesChanged();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void delete(int start, int end) {
/* 1079 */     delete(start, end, true);
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
/*      */   public void delete(int start, int end, boolean notify) {
/* 1092 */     if (end < start) {
/* 1093 */       throw new IllegalArgumentException("Requires start <= end.");
/*      */     }
/* 1095 */     for (int i = 0; i <= end - start; i++) {
/* 1096 */       this.data.remove(start);
/*      */     }
/* 1098 */     updateMinMaxYByIteration();
/* 1099 */     if (this.data.isEmpty()) {
/* 1100 */       this.timePeriodClass = null;
/*      */     }
/* 1102 */     if (notify) {
/* 1103 */       fireSeriesChanged();
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
/*      */   public Object clone() throws CloneNotSupportedException {
/* 1124 */     TimeSeries clone = (TimeSeries)super.clone();
/* 1125 */     clone.data = (List)ObjectUtilities.deepClone(this.data);
/* 1126 */     return clone;
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
/*      */   public TimeSeries createCopy(int start, int end) throws CloneNotSupportedException {
/* 1143 */     if (start < 0) {
/* 1144 */       throw new IllegalArgumentException("Requires start >= 0.");
/*      */     }
/* 1146 */     if (end < start) {
/* 1147 */       throw new IllegalArgumentException("Requires start <= end.");
/*      */     }
/* 1149 */     TimeSeries copy = (TimeSeries)super.clone();
/* 1150 */     copy.minY = Double.NaN;
/* 1151 */     copy.maxY = Double.NaN;
/* 1152 */     copy.data = new ArrayList();
/* 1153 */     if (this.data.size() > 0) {
/* 1154 */       for (int index = start; index <= end; index++) {
/*      */         
/* 1156 */         TimeSeriesDataItem item = this.data.get(index);
/* 1157 */         TimeSeriesDataItem clone = (TimeSeriesDataItem)item.clone();
/*      */         try {
/* 1159 */           copy.add(clone);
/*      */         }
/* 1161 */         catch (SeriesException e) {
/* 1162 */           throw new RuntimeException(e);
/*      */         } 
/*      */       } 
/*      */     }
/* 1166 */     return copy;
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
/*      */   public TimeSeries createCopy(RegularTimePeriod start, RegularTimePeriod end) throws CloneNotSupportedException {
/* 1186 */     ParamChecks.nullNotPermitted(start, "start");
/* 1187 */     ParamChecks.nullNotPermitted(end, "end");
/* 1188 */     if (start.compareTo((T)end) > 0) {
/* 1189 */       throw new IllegalArgumentException("Requires start on or before end.");
/*      */     }
/*      */     
/* 1192 */     boolean emptyRange = false;
/* 1193 */     int startIndex = getIndex(start);
/* 1194 */     if (startIndex < 0) {
/* 1195 */       startIndex = -(startIndex + 1);
/* 1196 */       if (startIndex == this.data.size()) {
/* 1197 */         emptyRange = true;
/*      */       }
/*      */     } 
/* 1200 */     int endIndex = getIndex(end);
/* 1201 */     if (endIndex < 0) {
/* 1202 */       endIndex = -(endIndex + 1);
/* 1203 */       endIndex--;
/*      */     } 
/* 1205 */     if (endIndex < 0 || endIndex < startIndex) {
/* 1206 */       emptyRange = true;
/*      */     }
/* 1208 */     if (emptyRange) {
/* 1209 */       TimeSeries copy = (TimeSeries)super.clone();
/* 1210 */       copy.data = new ArrayList();
/* 1211 */       return copy;
/*      */     } 
/* 1213 */     return createCopy(startIndex, endIndex);
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
/*      */   public boolean equals(Object obj) {
/* 1225 */     if (obj == this) {
/* 1226 */       return true;
/*      */     }
/* 1228 */     if (!(obj instanceof TimeSeries)) {
/* 1229 */       return false;
/*      */     }
/* 1231 */     TimeSeries that = (TimeSeries)obj;
/* 1232 */     if (!ObjectUtilities.equal(getDomainDescription(), that
/* 1233 */         .getDomainDescription())) {
/* 1234 */       return false;
/*      */     }
/* 1236 */     if (!ObjectUtilities.equal(getRangeDescription(), that
/* 1237 */         .getRangeDescription())) {
/* 1238 */       return false;
/*      */     }
/* 1240 */     if (!ObjectUtilities.equal(this.timePeriodClass, that.timePeriodClass))
/*      */     {
/* 1242 */       return false;
/*      */     }
/* 1244 */     if (getMaximumItemAge() != that.getMaximumItemAge()) {
/* 1245 */       return false;
/*      */     }
/* 1247 */     if (getMaximumItemCount() != that.getMaximumItemCount()) {
/* 1248 */       return false;
/*      */     }
/* 1250 */     int count = getItemCount();
/* 1251 */     if (count != that.getItemCount()) {
/* 1252 */       return false;
/*      */     }
/* 1254 */     if (!ObjectUtilities.equal(this.data, that.data)) {
/* 1255 */       return false;
/*      */     }
/* 1257 */     return super.equals(obj);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1267 */     int result = super.hashCode();
/* 1268 */     result = 29 * result + ((this.domain != null) ? this.domain.hashCode() : 0);
/*      */     
/* 1270 */     result = 29 * result + ((this.range != null) ? this.range.hashCode() : 0);
/*      */     
/* 1272 */     result = 29 * result + ((this.timePeriodClass != null) ? this.timePeriodClass.hashCode() : 0);
/*      */ 
/*      */     
/* 1275 */     int count = getItemCount();
/* 1276 */     if (count > 0) {
/* 1277 */       TimeSeriesDataItem item = getRawDataItem(0);
/* 1278 */       result = 29 * result + item.hashCode();
/*      */     } 
/* 1280 */     if (count > 1) {
/* 1281 */       TimeSeriesDataItem item = getRawDataItem(count - 1);
/* 1282 */       result = 29 * result + item.hashCode();
/*      */     } 
/* 1284 */     if (count > 2) {
/* 1285 */       TimeSeriesDataItem item = getRawDataItem(count / 2);
/* 1286 */       result = 29 * result + item.hashCode();
/*      */     } 
/* 1288 */     result = 29 * result + this.maximumItemCount;
/* 1289 */     result = 29 * result + (int)this.maximumItemAge;
/* 1290 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateBoundsForAddedItem(TimeSeriesDataItem item) {
/* 1301 */     Number yN = item.getValue();
/* 1302 */     if (item.getValue() != null) {
/* 1303 */       double y = yN.doubleValue();
/* 1304 */       this.minY = minIgnoreNaN(this.minY, y);
/* 1305 */       this.maxY = maxIgnoreNaN(this.maxY, y);
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
/*      */   private void updateBoundsForRemovedItem(TimeSeriesDataItem item) {
/* 1318 */     Number yN = item.getValue();
/* 1319 */     if (yN != null) {
/* 1320 */       double y = yN.doubleValue();
/* 1321 */       if (!Double.isNaN(y) && (
/* 1322 */         y <= this.minY || y >= this.maxY)) {
/* 1323 */         updateMinMaxYByIteration();
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
/*      */   private void updateMinMaxYByIteration() {
/* 1336 */     this.minY = Double.NaN;
/* 1337 */     this.maxY = Double.NaN;
/* 1338 */     Iterator<TimeSeriesDataItem> iterator = this.data.iterator();
/* 1339 */     while (iterator.hasNext()) {
/* 1340 */       TimeSeriesDataItem item = iterator.next();
/* 1341 */       updateBoundsForAddedItem(item);
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
/*      */   private double minIgnoreNaN(double a, double b) {
/* 1355 */     if (Double.isNaN(a)) {
/* 1356 */       return b;
/*      */     }
/* 1358 */     if (Double.isNaN(b)) {
/* 1359 */       return a;
/*      */     }
/* 1361 */     return Math.min(a, b);
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
/*      */   private double maxIgnoreNaN(double a, double b) {
/* 1374 */     if (Double.isNaN(a)) {
/* 1375 */       return b;
/*      */     }
/* 1377 */     if (Double.isNaN(b)) {
/* 1378 */       return a;
/*      */     }
/*      */     
/* 1381 */     return Math.max(a, b);
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
/*      */   public TimeSeries(Comparable name, Class timePeriodClass) {
/* 1399 */     this(name, "Time", "Value", timePeriodClass);
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
/*      */   public TimeSeries(Comparable name, String domain, String range, Class timePeriodClass) {
/* 1422 */     super(name);
/* 1423 */     this.domain = domain;
/* 1424 */     this.range = range;
/* 1425 */     this.timePeriodClass = timePeriodClass;
/* 1426 */     this.data = new ArrayList();
/* 1427 */     this.maximumItemCount = Integer.MAX_VALUE;
/* 1428 */     this.maximumItemAge = Long.MAX_VALUE;
/* 1429 */     this.minY = Double.NaN;
/* 1430 */     this.maxY = Double.NaN;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/TimeSeries.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */