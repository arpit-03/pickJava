/*      */ package org.jfree.chart.axis;
/*      */ 
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.LineMetrics;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.Serializable;
/*      */ import java.text.DateFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.TimeZone;
/*      */ import org.jfree.chart.plot.Plot;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.plot.ValueAxisPlot;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.time.DateRange;
/*      */ import org.jfree.data.time.Month;
/*      */ import org.jfree.data.time.RegularTimePeriod;
/*      */ import org.jfree.data.time.Year;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ import org.jfree.ui.TextAnchor;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DateAxis
/*      */   extends ValueAxis
/*      */   implements Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -1013460999649007604L;
/*  189 */   public static final DateRange DEFAULT_DATE_RANGE = new DateRange();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final double DEFAULT_AUTO_RANGE_MINIMUM_SIZE_IN_MILLISECONDS = 2.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  202 */   public static final DateTickUnit DEFAULT_DATE_TICK_UNIT = new DateTickUnit(DateTickUnitType.DAY, 1, new SimpleDateFormat());
/*      */ 
/*      */ 
/*      */   
/*  206 */   public static final Date DEFAULT_ANCHOR_DATE = new Date();
/*      */ 
/*      */ 
/*      */   
/*      */   private DateTickUnit tickUnit;
/*      */ 
/*      */ 
/*      */   
/*      */   private DateFormat dateFormatOverride;
/*      */ 
/*      */ 
/*      */   
/*  218 */   private DateTickMarkPosition tickMarkPosition = DateTickMarkPosition.START;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class DefaultTimeline
/*      */     implements Timeline, Serializable
/*      */   {
/*      */     private DefaultTimeline() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long toTimelineValue(long millisecond) {
/*  235 */       return millisecond;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long toTimelineValue(Date date) {
/*  247 */       return date.getTime();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long toMillisecond(long value) {
/*  260 */       return value;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsDomainValue(long millisecond) {
/*  273 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsDomainValue(Date date) {
/*  286 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsDomainRange(long from, long to) {
/*  300 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsDomainRange(Date from, Date to) {
/*  314 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object object) {
/*  326 */       if (object == null) {
/*  327 */         return false;
/*      */       }
/*  329 */       if (object == this) {
/*  330 */         return true;
/*      */       }
/*  332 */       if (object instanceof DefaultTimeline) {
/*  333 */         return true;
/*      */       }
/*  335 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*  340 */   private static final Timeline DEFAULT_TIMELINE = new DefaultTimeline();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TimeZone timeZone;
/*      */ 
/*      */ 
/*      */   
/*      */   private Locale locale;
/*      */ 
/*      */ 
/*      */   
/*      */   private Timeline timeline;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateAxis() {
/*  359 */     this((String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateAxis(String label) {
/*  368 */     this(label, TimeZone.getDefault());
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
/*      */   public DateAxis(String label, TimeZone zone) {
/*  385 */     this(label, zone, Locale.getDefault());
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
/*      */   public DateAxis(String label, TimeZone zone, Locale locale) {
/*  402 */     super(label, createStandardDateTickUnits(zone, locale));
/*  403 */     this.tickUnit = new DateTickUnit(DateTickUnitType.DAY, 1, new SimpleDateFormat());
/*      */     
/*  405 */     setAutoRangeMinimumSize(2.0D);
/*      */     
/*  407 */     setRange((Range)DEFAULT_DATE_RANGE, false, false);
/*  408 */     this.dateFormatOverride = null;
/*  409 */     this.timeZone = zone;
/*  410 */     this.locale = locale;
/*  411 */     this.timeline = DEFAULT_TIMELINE;
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
/*      */   public TimeZone getTimeZone() {
/*  424 */     return this.timeZone;
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
/*      */   public void setTimeZone(TimeZone zone) {
/*  438 */     ParamChecks.nullNotPermitted(zone, "zone");
/*  439 */     this.timeZone = zone;
/*  440 */     setStandardTickUnits(createStandardDateTickUnits(zone, this.locale));
/*  441 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  452 */     return this.locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocale(Locale locale) {
/*  462 */     ParamChecks.nullNotPermitted(locale, "locale");
/*  463 */     this.locale = locale;
/*  464 */     setStandardTickUnits(createStandardDateTickUnits(this.timeZone, this.locale));
/*      */     
/*  466 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Timeline getTimeline() {
/*  475 */     return this.timeline;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTimeline(Timeline timeline) {
/*  485 */     if (this.timeline != timeline) {
/*  486 */       this.timeline = timeline;
/*  487 */       fireChangeEvent();
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
/*      */   public DateTickUnit getTickUnit() {
/*  505 */     return this.tickUnit;
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
/*      */   public void setTickUnit(DateTickUnit unit) {
/*  519 */     setTickUnit(unit, true, true);
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
/*      */   public void setTickUnit(DateTickUnit unit, boolean notify, boolean turnOffAutoSelection) {
/*  535 */     this.tickUnit = unit;
/*  536 */     if (turnOffAutoSelection) {
/*  537 */       setAutoTickUnitSelection(false, false);
/*      */     }
/*  539 */     if (notify) {
/*  540 */       fireChangeEvent();
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
/*      */   public DateFormat getDateFormatOverride() {
/*  552 */     return this.dateFormatOverride;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDateFormatOverride(DateFormat formatter) {
/*  563 */     this.dateFormatOverride = formatter;
/*  564 */     fireChangeEvent();
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
/*      */   public void setRange(Range range) {
/*  576 */     setRange(range, true, true);
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
/*      */   public void setRange(Range range, boolean turnOffAutoRange, boolean notify) {
/*      */     DateRange dateRange;
/*  593 */     ParamChecks.nullNotPermitted(range, "range");
/*      */ 
/*      */     
/*  596 */     if (!(range instanceof DateRange)) {
/*  597 */       dateRange = new DateRange(range);
/*      */     }
/*  599 */     super.setRange((Range)dateRange, turnOffAutoRange, notify);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRange(Date lower, Date upper) {
/*  610 */     if (lower.getTime() >= upper.getTime()) {
/*  611 */       throw new IllegalArgumentException("Requires 'lower' < 'upper'.");
/*      */     }
/*  613 */     setRange((Range)new DateRange(lower, upper));
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
/*      */   public void setRange(double lower, double upper) {
/*  625 */     if (lower >= upper) {
/*  626 */       throw new IllegalArgumentException("Requires 'lower' < 'upper'.");
/*      */     }
/*  628 */     setRange((Range)new DateRange(lower, upper));
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
/*      */   public Date getMinimumDate() {
/*      */     Date result;
/*  641 */     Range range = getRange();
/*  642 */     if (range instanceof DateRange) {
/*  643 */       DateRange r = (DateRange)range;
/*  644 */       result = r.getLowerDate();
/*      */     } else {
/*      */       
/*  647 */       result = new Date((long)range.getLowerBound());
/*      */     } 
/*  649 */     return result;
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
/*      */   public void setMinimumDate(Date date) {
/*  665 */     ParamChecks.nullNotPermitted(date, "date");
/*      */     
/*  667 */     Date maxDate = getMaximumDate();
/*  668 */     long maxMillis = maxDate.getTime();
/*  669 */     long newMinMillis = date.getTime();
/*  670 */     if (maxMillis <= newMinMillis) {
/*  671 */       Date oldMin = getMinimumDate();
/*  672 */       long length = maxMillis - oldMin.getTime();
/*  673 */       maxDate = new Date(newMinMillis + length);
/*      */     } 
/*  675 */     setRange((Range)new DateRange(date, maxDate), true, false);
/*  676 */     fireChangeEvent();
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
/*      */   public Date getMaximumDate() {
/*      */     Date result;
/*  689 */     Range range = getRange();
/*  690 */     if (range instanceof DateRange) {
/*  691 */       DateRange r = (DateRange)range;
/*  692 */       result = r.getUpperDate();
/*      */     } else {
/*      */       
/*  695 */       result = new Date((long)range.getUpperBound());
/*      */     } 
/*  697 */     return result;
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
/*      */   public void setMaximumDate(Date maximumDate) {
/*  713 */     ParamChecks.nullNotPermitted(maximumDate, "maximumDate");
/*      */     
/*  715 */     Date minDate = getMinimumDate();
/*  716 */     long minMillis = minDate.getTime();
/*  717 */     long newMaxMillis = maximumDate.getTime();
/*  718 */     if (minMillis >= newMaxMillis) {
/*  719 */       Date oldMax = getMaximumDate();
/*  720 */       long length = oldMax.getTime() - minMillis;
/*  721 */       minDate = new Date(newMaxMillis - length);
/*      */     } 
/*  723 */     setRange((Range)new DateRange(minDate, maximumDate), true, false);
/*  724 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTickMarkPosition getTickMarkPosition() {
/*  733 */     return this.tickMarkPosition;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickMarkPosition(DateTickMarkPosition position) {
/*  743 */     ParamChecks.nullNotPermitted(position, "position");
/*  744 */     this.tickMarkPosition = position;
/*  745 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void configure() {
/*  754 */     if (isAutoRange()) {
/*  755 */       autoAdjustRange();
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
/*      */   public boolean isHiddenValue(long millis) {
/*  768 */     return !this.timeline.containsDomainValue(new Date(millis));
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
/*      */   public double valueToJava2D(double value, Rectangle2D area, RectangleEdge edge) {
/*  786 */     value = this.timeline.toTimelineValue((long)value);
/*      */     
/*  788 */     DateRange range = (DateRange)getRange();
/*  789 */     double axisMin = this.timeline.toTimelineValue(range.getLowerMillis());
/*  790 */     double axisMax = this.timeline.toTimelineValue(range.getUpperMillis());
/*  791 */     double result = 0.0D;
/*  792 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  793 */       double minX = area.getX();
/*  794 */       double maxX = area.getMaxX();
/*  795 */       if (isInverted()) {
/*  796 */         result = maxX + (value - axisMin) / (axisMax - axisMin) * (minX - maxX);
/*      */       }
/*      */       else {
/*      */         
/*  800 */         result = minX + (value - axisMin) / (axisMax - axisMin) * (maxX - minX);
/*      */       }
/*      */     
/*      */     }
/*  804 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  805 */       double minY = area.getMinY();
/*  806 */       double maxY = area.getMaxY();
/*  807 */       if (isInverted()) {
/*  808 */         result = minY + (value - axisMin) / (axisMax - axisMin) * (maxY - minY);
/*      */       }
/*      */       else {
/*      */         
/*  812 */         result = maxY - (value - axisMin) / (axisMax - axisMin) * (maxY - minY);
/*      */       } 
/*      */     } 
/*      */     
/*  816 */     return result;
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
/*      */   public double dateToJava2D(Date date, Rectangle2D area, RectangleEdge edge) {
/*  832 */     double value = date.getTime();
/*  833 */     return valueToJava2D(value, area, edge);
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
/*      */   public double java2DToValue(double java2DValue, Rectangle2D area, RectangleEdge edge) {
/*      */     double result;
/*  852 */     DateRange range = (DateRange)getRange();
/*  853 */     double axisMin = this.timeline.toTimelineValue(range.getLowerMillis());
/*  854 */     double axisMax = this.timeline.toTimelineValue(range.getUpperMillis());
/*      */     
/*  856 */     double min = 0.0D;
/*  857 */     double max = 0.0D;
/*  858 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  859 */       min = area.getX();
/*  860 */       max = area.getMaxX();
/*      */     }
/*  862 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  863 */       min = area.getMaxY();
/*  864 */       max = area.getY();
/*      */     } 
/*      */ 
/*      */     
/*  868 */     if (isInverted()) {
/*  869 */       result = axisMax - (java2DValue - min) / (max - min) * (axisMax - axisMin);
/*      */     }
/*      */     else {
/*      */       
/*  873 */       result = axisMin + (java2DValue - min) / (max - min) * (axisMax - axisMin);
/*      */     } 
/*      */ 
/*      */     
/*  877 */     return this.timeline.toMillisecond((long)result);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date calculateLowestVisibleTickValue(DateTickUnit unit) {
/*  888 */     return nextStandardDate(getMinimumDate(), unit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date calculateHighestVisibleTickValue(DateTickUnit unit) {
/*  899 */     return previousStandardDate(getMaximumDate(), unit);
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
/*      */   protected Date previousStandardDate(Date date, DateTickUnit unit) {
/*      */     int milliseconds, seconds, minutes, hours, days, months, years;
/*      */     Date mm, dd, d0, d1, d2;
/*      */     Month month;
/*      */     Date standardDate;
/*      */     long millis;
/*      */     Date d3;
/*  920 */     Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
/*  921 */     calendar.setTime(date);
/*  922 */     int count = unit.getCount();
/*  923 */     int current = calendar.get(unit.getCalendarField());
/*  924 */     int value = count * current / count;
/*      */     
/*  926 */     switch (unit.getUnit()) {
/*      */       
/*      */       case 6:
/*  929 */         years = calendar.get(1);
/*  930 */         months = calendar.get(2);
/*  931 */         days = calendar.get(5);
/*  932 */         hours = calendar.get(11);
/*  933 */         minutes = calendar.get(12);
/*  934 */         seconds = calendar.get(13);
/*  935 */         calendar.set(years, months, days, hours, minutes, seconds);
/*  936 */         calendar.set(14, value);
/*  937 */         mm = calendar.getTime();
/*  938 */         if (mm.getTime() >= date.getTime()) {
/*  939 */           calendar.set(14, value - 1);
/*  940 */           mm = calendar.getTime();
/*      */         } 
/*  942 */         return mm;
/*      */       
/*      */       case 5:
/*  945 */         years = calendar.get(1);
/*  946 */         months = calendar.get(2);
/*  947 */         days = calendar.get(5);
/*  948 */         hours = calendar.get(11);
/*  949 */         minutes = calendar.get(12);
/*  950 */         if (this.tickMarkPosition == DateTickMarkPosition.START) {
/*  951 */           milliseconds = 0;
/*      */         }
/*  953 */         else if (this.tickMarkPosition == DateTickMarkPosition.MIDDLE) {
/*  954 */           milliseconds = 500;
/*      */         } else {
/*      */           
/*  957 */           milliseconds = 999;
/*      */         } 
/*  959 */         calendar.set(14, milliseconds);
/*  960 */         calendar.set(years, months, days, hours, minutes, value);
/*  961 */         dd = calendar.getTime();
/*  962 */         if (dd.getTime() >= date.getTime()) {
/*  963 */           calendar.set(13, value - 1);
/*  964 */           dd = calendar.getTime();
/*      */         } 
/*  966 */         return dd;
/*      */       
/*      */       case 4:
/*  969 */         years = calendar.get(1);
/*  970 */         months = calendar.get(2);
/*  971 */         days = calendar.get(5);
/*  972 */         hours = calendar.get(11);
/*  973 */         if (this.tickMarkPosition == DateTickMarkPosition.START) {
/*  974 */           seconds = 0;
/*      */         }
/*  976 */         else if (this.tickMarkPosition == DateTickMarkPosition.MIDDLE) {
/*  977 */           seconds = 30;
/*      */         } else {
/*      */           
/*  980 */           seconds = 59;
/*      */         } 
/*  982 */         calendar.clear(14);
/*  983 */         calendar.set(years, months, days, hours, value, seconds);
/*  984 */         d0 = calendar.getTime();
/*  985 */         if (d0.getTime() >= date.getTime()) {
/*  986 */           calendar.set(12, value - 1);
/*  987 */           d0 = calendar.getTime();
/*      */         } 
/*  989 */         return d0;
/*      */       
/*      */       case 3:
/*  992 */         years = calendar.get(1);
/*  993 */         months = calendar.get(2);
/*  994 */         days = calendar.get(5);
/*  995 */         if (this.tickMarkPosition == DateTickMarkPosition.START) {
/*  996 */           minutes = 0;
/*  997 */           seconds = 0;
/*      */         }
/*  999 */         else if (this.tickMarkPosition == DateTickMarkPosition.MIDDLE) {
/* 1000 */           minutes = 30;
/* 1001 */           seconds = 0;
/*      */         } else {
/*      */           
/* 1004 */           minutes = 59;
/* 1005 */           seconds = 59;
/*      */         } 
/* 1007 */         calendar.clear(14);
/* 1008 */         calendar.set(years, months, days, value, minutes, seconds);
/* 1009 */         d1 = calendar.getTime();
/* 1010 */         if (d1.getTime() >= date.getTime()) {
/* 1011 */           calendar.set(11, value - 1);
/* 1012 */           d1 = calendar.getTime();
/*      */         } 
/* 1014 */         return d1;
/*      */       
/*      */       case 2:
/* 1017 */         years = calendar.get(1);
/* 1018 */         months = calendar.get(2);
/* 1019 */         if (this.tickMarkPosition == DateTickMarkPosition.START) {
/* 1020 */           hours = 0;
/*      */         }
/* 1022 */         else if (this.tickMarkPosition == DateTickMarkPosition.MIDDLE) {
/* 1023 */           hours = 12;
/*      */         } else {
/*      */           
/* 1026 */           hours = 23;
/*      */         } 
/* 1028 */         calendar.clear(14);
/* 1029 */         calendar.set(years, months, value, hours, 0, 0);
/*      */ 
/*      */         
/* 1032 */         d2 = calendar.getTime();
/* 1033 */         if (d2.getTime() >= date.getTime()) {
/* 1034 */           calendar.set(5, value - 1);
/* 1035 */           d2 = calendar.getTime();
/*      */         } 
/* 1037 */         return d2;
/*      */       
/*      */       case 1:
/* 1040 */         years = calendar.get(1);
/* 1041 */         calendar.clear(14);
/* 1042 */         calendar.set(years, value, 1, 0, 0, 0);
/* 1043 */         month = new Month(calendar.getTime(), this.timeZone, this.locale);
/*      */         
/* 1045 */         standardDate = calculateDateForPosition((RegularTimePeriod)month, this.tickMarkPosition);
/*      */         
/* 1047 */         millis = standardDate.getTime();
/* 1048 */         if (millis >= date.getTime()) {
/* 1049 */           month = (Month)month.previous();
/*      */ 
/*      */           
/* 1052 */           month.peg(Calendar.getInstance(this.timeZone));
/* 1053 */           standardDate = calculateDateForPosition((RegularTimePeriod)month, this.tickMarkPosition);
/*      */         } 
/*      */         
/* 1056 */         return standardDate;
/*      */       
/*      */       case 0:
/* 1059 */         if (this.tickMarkPosition == DateTickMarkPosition.START) {
/* 1060 */           months = 0;
/* 1061 */           days = 1;
/*      */         }
/* 1063 */         else if (this.tickMarkPosition == DateTickMarkPosition.MIDDLE) {
/* 1064 */           months = 6;
/* 1065 */           days = 1;
/*      */         } else {
/*      */           
/* 1068 */           months = 11;
/* 1069 */           days = 31;
/*      */         } 
/* 1071 */         calendar.clear(14);
/* 1072 */         calendar.set(value, months, days, 0, 0, 0);
/* 1073 */         d3 = calendar.getTime();
/* 1074 */         if (d3.getTime() >= date.getTime()) {
/* 1075 */           calendar.set(1, value - 1);
/* 1076 */           d3 = calendar.getTime();
/*      */         } 
/* 1078 */         return d3;
/*      */     } 
/* 1080 */     return null;
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
/*      */   private Date calculateDateForPosition(RegularTimePeriod period, DateTickMarkPosition position) {
/* 1097 */     ParamChecks.nullNotPermitted(period, "period");
/* 1098 */     Date result = null;
/* 1099 */     if (position == DateTickMarkPosition.START) {
/* 1100 */       result = new Date(period.getFirstMillisecond());
/*      */     }
/* 1102 */     else if (position == DateTickMarkPosition.MIDDLE) {
/* 1103 */       result = new Date(period.getMiddleMillisecond());
/*      */     }
/* 1105 */     else if (position == DateTickMarkPosition.END) {
/* 1106 */       result = new Date(period.getLastMillisecond());
/*      */     } 
/* 1108 */     return result;
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
/*      */   protected Date nextStandardDate(Date date, DateTickUnit unit) {
/* 1122 */     Date previous = previousStandardDate(date, unit);
/* 1123 */     Calendar calendar = Calendar.getInstance(this.timeZone, this.locale);
/* 1124 */     calendar.setTime(previous);
/* 1125 */     calendar.add(unit.getCalendarField(), unit.getMultiple());
/* 1126 */     return calendar.getTime();
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
/*      */   public static TickUnitSource createStandardDateTickUnits() {
/* 1139 */     return createStandardDateTickUnits(TimeZone.getDefault(), 
/* 1140 */         Locale.getDefault());
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
/*      */   public static TickUnitSource createStandardDateTickUnits(TimeZone zone, Locale locale) {
/* 1160 */     ParamChecks.nullNotPermitted(zone, "zone");
/* 1161 */     ParamChecks.nullNotPermitted(locale, "locale");
/* 1162 */     TickUnits units = new TickUnits();
/*      */ 
/*      */     
/* 1165 */     DateFormat f1 = new SimpleDateFormat("HH:mm:ss.SSS", locale);
/* 1166 */     DateFormat f2 = new SimpleDateFormat("HH:mm:ss", locale);
/* 1167 */     DateFormat f3 = new SimpleDateFormat("HH:mm", locale);
/* 1168 */     DateFormat f4 = new SimpleDateFormat("d-MMM, HH:mm", locale);
/* 1169 */     DateFormat f5 = new SimpleDateFormat("d-MMM", locale);
/* 1170 */     DateFormat f6 = new SimpleDateFormat("MMM-yyyy", locale);
/* 1171 */     DateFormat f7 = new SimpleDateFormat("yyyy", locale);
/*      */     
/* 1173 */     f1.setTimeZone(zone);
/* 1174 */     f2.setTimeZone(zone);
/* 1175 */     f3.setTimeZone(zone);
/* 1176 */     f4.setTimeZone(zone);
/* 1177 */     f5.setTimeZone(zone);
/* 1178 */     f6.setTimeZone(zone);
/* 1179 */     f7.setTimeZone(zone);
/*      */ 
/*      */     
/* 1182 */     units.add(new DateTickUnit(DateTickUnitType.MILLISECOND, 1, f1));
/* 1183 */     units.add(new DateTickUnit(DateTickUnitType.MILLISECOND, 5, DateTickUnitType.MILLISECOND, 1, f1));
/*      */     
/* 1185 */     units.add(new DateTickUnit(DateTickUnitType.MILLISECOND, 10, DateTickUnitType.MILLISECOND, 1, f1));
/*      */     
/* 1187 */     units.add(new DateTickUnit(DateTickUnitType.MILLISECOND, 25, DateTickUnitType.MILLISECOND, 5, f1));
/*      */     
/* 1189 */     units.add(new DateTickUnit(DateTickUnitType.MILLISECOND, 50, DateTickUnitType.MILLISECOND, 10, f1));
/*      */     
/* 1191 */     units.add(new DateTickUnit(DateTickUnitType.MILLISECOND, 100, DateTickUnitType.MILLISECOND, 10, f1));
/*      */     
/* 1193 */     units.add(new DateTickUnit(DateTickUnitType.MILLISECOND, 250, DateTickUnitType.MILLISECOND, 10, f1));
/*      */     
/* 1195 */     units.add(new DateTickUnit(DateTickUnitType.MILLISECOND, 500, DateTickUnitType.MILLISECOND, 50, f1));
/*      */ 
/*      */ 
/*      */     
/* 1199 */     units.add(new DateTickUnit(DateTickUnitType.SECOND, 1, DateTickUnitType.MILLISECOND, 50, f2));
/*      */     
/* 1201 */     units.add(new DateTickUnit(DateTickUnitType.SECOND, 5, DateTickUnitType.SECOND, 1, f2));
/*      */     
/* 1203 */     units.add(new DateTickUnit(DateTickUnitType.SECOND, 10, DateTickUnitType.SECOND, 1, f2));
/*      */     
/* 1205 */     units.add(new DateTickUnit(DateTickUnitType.SECOND, 30, DateTickUnitType.SECOND, 5, f2));
/*      */ 
/*      */ 
/*      */     
/* 1209 */     units.add(new DateTickUnit(DateTickUnitType.MINUTE, 1, DateTickUnitType.SECOND, 5, f3));
/*      */     
/* 1211 */     units.add(new DateTickUnit(DateTickUnitType.MINUTE, 2, DateTickUnitType.SECOND, 10, f3));
/*      */     
/* 1213 */     units.add(new DateTickUnit(DateTickUnitType.MINUTE, 5, DateTickUnitType.MINUTE, 1, f3));
/*      */     
/* 1215 */     units.add(new DateTickUnit(DateTickUnitType.MINUTE, 10, DateTickUnitType.MINUTE, 1, f3));
/*      */     
/* 1217 */     units.add(new DateTickUnit(DateTickUnitType.MINUTE, 15, DateTickUnitType.MINUTE, 5, f3));
/*      */     
/* 1219 */     units.add(new DateTickUnit(DateTickUnitType.MINUTE, 20, DateTickUnitType.MINUTE, 5, f3));
/*      */     
/* 1221 */     units.add(new DateTickUnit(DateTickUnitType.MINUTE, 30, DateTickUnitType.MINUTE, 5, f3));
/*      */ 
/*      */ 
/*      */     
/* 1225 */     units.add(new DateTickUnit(DateTickUnitType.HOUR, 1, DateTickUnitType.MINUTE, 5, f3));
/*      */     
/* 1227 */     units.add(new DateTickUnit(DateTickUnitType.HOUR, 2, DateTickUnitType.MINUTE, 10, f3));
/*      */     
/* 1229 */     units.add(new DateTickUnit(DateTickUnitType.HOUR, 4, DateTickUnitType.MINUTE, 30, f3));
/*      */     
/* 1231 */     units.add(new DateTickUnit(DateTickUnitType.HOUR, 6, DateTickUnitType.HOUR, 1, f3));
/*      */     
/* 1233 */     units.add(new DateTickUnit(DateTickUnitType.HOUR, 12, DateTickUnitType.HOUR, 1, f4));
/*      */ 
/*      */ 
/*      */     
/* 1237 */     units.add(new DateTickUnit(DateTickUnitType.DAY, 1, DateTickUnitType.HOUR, 1, f5));
/*      */     
/* 1239 */     units.add(new DateTickUnit(DateTickUnitType.DAY, 2, DateTickUnitType.HOUR, 1, f5));
/*      */     
/* 1241 */     units.add(new DateTickUnit(DateTickUnitType.DAY, 7, DateTickUnitType.DAY, 1, f5));
/*      */     
/* 1243 */     units.add(new DateTickUnit(DateTickUnitType.DAY, 15, DateTickUnitType.DAY, 1, f5));
/*      */ 
/*      */ 
/*      */     
/* 1247 */     units.add(new DateTickUnit(DateTickUnitType.MONTH, 1, DateTickUnitType.DAY, 1, f6));
/*      */     
/* 1249 */     units.add(new DateTickUnit(DateTickUnitType.MONTH, 2, DateTickUnitType.DAY, 1, f6));
/*      */     
/* 1251 */     units.add(new DateTickUnit(DateTickUnitType.MONTH, 3, DateTickUnitType.MONTH, 1, f6));
/*      */     
/* 1253 */     units.add(new DateTickUnit(DateTickUnitType.MONTH, 4, DateTickUnitType.MONTH, 1, f6));
/*      */     
/* 1255 */     units.add(new DateTickUnit(DateTickUnitType.MONTH, 6, DateTickUnitType.MONTH, 1, f6));
/*      */ 
/*      */ 
/*      */     
/* 1259 */     units.add(new DateTickUnit(DateTickUnitType.YEAR, 1, DateTickUnitType.MONTH, 1, f7));
/*      */     
/* 1261 */     units.add(new DateTickUnit(DateTickUnitType.YEAR, 2, DateTickUnitType.MONTH, 3, f7));
/*      */     
/* 1263 */     units.add(new DateTickUnit(DateTickUnitType.YEAR, 5, DateTickUnitType.YEAR, 1, f7));
/*      */     
/* 1265 */     units.add(new DateTickUnit(DateTickUnitType.YEAR, 10, DateTickUnitType.YEAR, 1, f7));
/*      */     
/* 1267 */     units.add(new DateTickUnit(DateTickUnitType.YEAR, 25, DateTickUnitType.YEAR, 5, f7));
/*      */     
/* 1269 */     units.add(new DateTickUnit(DateTickUnitType.YEAR, 50, DateTickUnitType.YEAR, 10, f7));
/*      */     
/* 1271 */     units.add(new DateTickUnit(DateTickUnitType.YEAR, 100, DateTickUnitType.YEAR, 20, f7));
/*      */ 
/*      */     
/* 1274 */     return units;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void autoAdjustRange() {
/* 1284 */     Plot plot = getPlot();
/*      */     
/* 1286 */     if (plot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1290 */     if (plot instanceof ValueAxisPlot) {
/* 1291 */       DateRange dateRange1; ValueAxisPlot vap = (ValueAxisPlot)plot;
/*      */       
/* 1293 */       Range r = vap.getDataRange(this);
/* 1294 */       if (r == null) {
/* 1295 */         if (this.timeline instanceof SegmentedTimeline) {
/*      */ 
/*      */ 
/*      */           
/* 1299 */           dateRange1 = new DateRange(((SegmentedTimeline)this.timeline).getStartTime(), (((SegmentedTimeline)this.timeline).getStartTime() + 1L));
/*      */         }
/*      */         else {
/*      */           
/* 1303 */           dateRange1 = new DateRange();
/*      */         } 
/*      */       }
/*      */       
/* 1307 */       long upper = this.timeline.toTimelineValue(
/* 1308 */           (long)dateRange1.getUpperBound());
/*      */       
/* 1310 */       long fixedAutoRange = (long)getFixedAutoRange();
/* 1311 */       if (fixedAutoRange > 0.0D) {
/* 1312 */         lower = upper - fixedAutoRange;
/*      */       } else {
/*      */         
/* 1315 */         lower = this.timeline.toTimelineValue((long)dateRange1.getLowerBound());
/* 1316 */         double range = (upper - lower);
/* 1317 */         long minRange = (long)getAutoRangeMinimumSize();
/* 1318 */         if (range < minRange) {
/* 1319 */           long expand = (long)(minRange - range) / 2L;
/* 1320 */           upper += expand;
/* 1321 */           lower -= expand;
/*      */         } 
/* 1323 */         upper += (long)(range * getUpperMargin());
/* 1324 */         lower -= (long)(range * getLowerMargin());
/*      */       } 
/*      */       
/* 1327 */       upper = this.timeline.toMillisecond(upper);
/* 1328 */       long lower = this.timeline.toMillisecond(lower);
/* 1329 */       DateRange dr = new DateRange(new Date(lower), new Date(upper));
/* 1330 */       setRange((Range)dr, false, false);
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
/*      */   protected void selectAutoTickUnit(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/* 1347 */     if (RectangleEdge.isTopOrBottom(edge)) {
/* 1348 */       selectHorizontalAutoTickUnit(g2, dataArea, edge);
/*      */     }
/* 1350 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/* 1351 */       selectVerticalAutoTickUnit(g2, dataArea, edge);
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
/*      */   protected void selectHorizontalAutoTickUnit(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/* 1368 */     long shift = 0L;
/* 1369 */     if (this.timeline instanceof SegmentedTimeline) {
/* 1370 */       shift = ((SegmentedTimeline)this.timeline).getStartTime();
/*      */     }
/* 1372 */     double zero = valueToJava2D(shift + 0.0D, dataArea, edge);
/* 1373 */     double tickLabelWidth = estimateMaximumTickLabelWidth(g2, 
/* 1374 */         getTickUnit());
/*      */ 
/*      */     
/* 1377 */     TickUnitSource tickUnits = getStandardTickUnits();
/* 1378 */     TickUnit unit1 = tickUnits.getCeilingTickUnit(getTickUnit());
/* 1379 */     double x1 = valueToJava2D(shift + unit1.getSize(), dataArea, edge);
/* 1380 */     double unit1Width = Math.abs(x1 - zero);
/*      */ 
/*      */     
/* 1383 */     double guess = tickLabelWidth / unit1Width * unit1.getSize();
/* 1384 */     DateTickUnit unit2 = (DateTickUnit)tickUnits.getCeilingTickUnit(guess);
/* 1385 */     double x2 = valueToJava2D(shift + unit2.getSize(), dataArea, edge);
/* 1386 */     double unit2Width = Math.abs(x2 - zero);
/* 1387 */     tickLabelWidth = estimateMaximumTickLabelWidth(g2, unit2);
/* 1388 */     if (tickLabelWidth > unit2Width) {
/* 1389 */       unit2 = (DateTickUnit)tickUnits.getLargerTickUnit(unit2);
/*      */     }
/* 1391 */     setTickUnit(unit2, false, false);
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
/*      */   protected void selectVerticalAutoTickUnit(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/*      */     DateTickUnit finalUnit;
/* 1407 */     TickUnitSource tickUnits = getStandardTickUnits();
/* 1408 */     double zero = valueToJava2D(0.0D, dataArea, edge);
/*      */ 
/*      */     
/* 1411 */     double estimate1 = getRange().getLength() / 10.0D;
/*      */     
/* 1413 */     DateTickUnit candidate1 = (DateTickUnit)tickUnits.getCeilingTickUnit(estimate1);
/* 1414 */     double labelHeight1 = estimateMaximumTickLabelHeight(g2, candidate1);
/* 1415 */     double y1 = valueToJava2D(candidate1.getSize(), dataArea, edge);
/* 1416 */     double candidate1UnitHeight = Math.abs(y1 - zero);
/*      */ 
/*      */ 
/*      */     
/* 1420 */     double estimate2 = labelHeight1 / candidate1UnitHeight * candidate1.getSize();
/*      */     
/* 1422 */     DateTickUnit candidate2 = (DateTickUnit)tickUnits.getCeilingTickUnit(estimate2);
/* 1423 */     double labelHeight2 = estimateMaximumTickLabelHeight(g2, candidate2);
/* 1424 */     double y2 = valueToJava2D(candidate2.getSize(), dataArea, edge);
/* 1425 */     double unit2Height = Math.abs(y2 - zero);
/*      */ 
/*      */ 
/*      */     
/* 1429 */     if (labelHeight2 < unit2Height) {
/* 1430 */       finalUnit = candidate2;
/*      */     } else {
/*      */       
/* 1433 */       finalUnit = (DateTickUnit)tickUnits.getLargerTickUnit(candidate2);
/*      */     } 
/* 1435 */     setTickUnit(finalUnit, false, false);
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
/*      */   private double estimateMaximumTickLabelWidth(Graphics2D g2, DateTickUnit unit) {
/* 1455 */     RectangleInsets tickLabelInsets = getTickLabelInsets();
/* 1456 */     double result = tickLabelInsets.getLeft() + tickLabelInsets.getRight();
/*      */     
/* 1458 */     Font tickLabelFont = getTickLabelFont();
/* 1459 */     FontRenderContext frc = g2.getFontRenderContext();
/* 1460 */     LineMetrics lm = tickLabelFont.getLineMetrics("ABCxyz", frc);
/* 1461 */     if (isVerticalTickLabels()) {
/*      */ 
/*      */       
/* 1464 */       result += lm.getHeight();
/*      */     } else {
/*      */       String lowerStr, upperStr;
/*      */       
/* 1468 */       DateRange range = (DateRange)getRange();
/* 1469 */       Date lower = range.getLowerDate();
/* 1470 */       Date upper = range.getUpperDate();
/*      */       
/* 1472 */       DateFormat formatter = getDateFormatOverride();
/* 1473 */       if (formatter != null) {
/* 1474 */         lowerStr = formatter.format(lower);
/* 1475 */         upperStr = formatter.format(upper);
/*      */       } else {
/*      */         
/* 1478 */         lowerStr = unit.dateToString(lower);
/* 1479 */         upperStr = unit.dateToString(upper);
/*      */       } 
/* 1481 */       FontMetrics fm = g2.getFontMetrics(tickLabelFont);
/* 1482 */       double w1 = fm.stringWidth(lowerStr);
/* 1483 */       double w2 = fm.stringWidth(upperStr);
/* 1484 */       result += Math.max(w1, w2);
/*      */     } 
/*      */     
/* 1487 */     return result;
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
/*      */   private double estimateMaximumTickLabelHeight(Graphics2D g2, DateTickUnit unit) {
/* 1507 */     RectangleInsets tickLabelInsets = getTickLabelInsets();
/* 1508 */     double result = tickLabelInsets.getTop() + tickLabelInsets.getBottom();
/*      */     
/* 1510 */     Font tickLabelFont = getTickLabelFont();
/* 1511 */     FontRenderContext frc = g2.getFontRenderContext();
/* 1512 */     LineMetrics lm = tickLabelFont.getLineMetrics("ABCxyz", frc);
/* 1513 */     if (!isVerticalTickLabels()) {
/*      */ 
/*      */       
/* 1516 */       result += lm.getHeight();
/*      */     } else {
/*      */       String lowerStr, upperStr;
/*      */       
/* 1520 */       DateRange range = (DateRange)getRange();
/* 1521 */       Date lower = range.getLowerDate();
/* 1522 */       Date upper = range.getUpperDate();
/*      */       
/* 1524 */       DateFormat formatter = getDateFormatOverride();
/* 1525 */       if (formatter != null) {
/* 1526 */         lowerStr = formatter.format(lower);
/* 1527 */         upperStr = formatter.format(upper);
/*      */       } else {
/*      */         
/* 1530 */         lowerStr = unit.dateToString(lower);
/* 1531 */         upperStr = unit.dateToString(upper);
/*      */       } 
/* 1533 */       FontMetrics fm = g2.getFontMetrics(tickLabelFont);
/* 1534 */       double w1 = fm.stringWidth(lowerStr);
/* 1535 */       double w2 = fm.stringWidth(upperStr);
/* 1536 */       result += Math.max(w1, w2);
/*      */     } 
/*      */     
/* 1539 */     return result;
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
/*      */   public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {
/* 1558 */     List result = null;
/* 1559 */     if (RectangleEdge.isTopOrBottom(edge)) {
/* 1560 */       result = refreshTicksHorizontal(g2, dataArea, edge);
/*      */     }
/* 1562 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/* 1563 */       result = refreshTicksVertical(g2, dataArea, edge);
/*      */     } 
/* 1565 */     return result;
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
/*      */   private Date correctTickDateForPosition(Date time, DateTickUnit unit, DateTickMarkPosition position) {
/* 1580 */     Date result = time;
/* 1581 */     switch (unit.getUnit()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 1589 */         result = calculateDateForPosition((RegularTimePeriod)new Month(time, this.timeZone, this.locale), position);
/*      */         break;
/*      */       
/*      */       case 0:
/* 1593 */         result = calculateDateForPosition((RegularTimePeriod)new Year(time, this.timeZone, this.locale), position);
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1599 */     return result;
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
/*      */   protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/* 1614 */     List<DateTick> result = new ArrayList();
/*      */     
/* 1616 */     Font tickLabelFont = getTickLabelFont();
/* 1617 */     g2.setFont(tickLabelFont);
/*      */     
/* 1619 */     if (isAutoTickUnitSelection()) {
/* 1620 */       selectAutoTickUnit(g2, dataArea, edge);
/*      */     }
/*      */     
/* 1623 */     DateTickUnit unit = getTickUnit();
/* 1624 */     Date tickDate = calculateLowestVisibleTickValue(unit);
/* 1625 */     Date upperDate = getMaximumDate();
/*      */     
/* 1627 */     boolean hasRolled = false;
/* 1628 */     while (tickDate.before(upperDate)) {
/*      */       
/* 1630 */       if (!hasRolled) {
/* 1631 */         tickDate = correctTickDateForPosition(tickDate, unit, this.tickMarkPosition);
/*      */       }
/*      */ 
/*      */       
/* 1635 */       long lowestTickTime = tickDate.getTime();
/* 1636 */       long distance = unit.addToDate(tickDate, this.timeZone).getTime() - lowestTickTime;
/*      */       
/* 1638 */       int minorTickSpaces = getMinorTickCount();
/* 1639 */       if (minorTickSpaces <= 0) {
/* 1640 */         minorTickSpaces = unit.getMinorTickCount();
/*      */       }
/* 1642 */       for (int minorTick = 1; minorTick < minorTickSpaces; minorTick++) {
/* 1643 */         long minorTickTime = lowestTickTime - distance * minorTick / minorTickSpaces;
/*      */         
/* 1645 */         if (minorTickTime > 0L && getRange().contains(minorTickTime) && 
/* 1646 */           !isHiddenValue(minorTickTime)) {
/* 1647 */           result.add(new DateTick(TickType.MINOR, new Date(minorTickTime), "", TextAnchor.TOP_CENTER, TextAnchor.CENTER, 0.0D));
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1653 */       if (!isHiddenValue(tickDate.getTime())) {
/*      */         String tickLabel;
/*      */         TextAnchor anchor, rotationAnchor;
/* 1656 */         DateFormat formatter = getDateFormatOverride();
/* 1657 */         if (formatter != null) {
/* 1658 */           tickLabel = formatter.format(tickDate);
/*      */         } else {
/*      */           
/* 1661 */           tickLabel = this.tickUnit.dateToString(tickDate);
/*      */         } 
/*      */         
/* 1664 */         double angle = 0.0D;
/* 1665 */         if (isVerticalTickLabels()) {
/* 1666 */           anchor = TextAnchor.CENTER_RIGHT;
/* 1667 */           rotationAnchor = TextAnchor.CENTER_RIGHT;
/* 1668 */           if (edge == RectangleEdge.TOP) {
/* 1669 */             angle = 1.5707963267948966D;
/*      */           } else {
/*      */             
/* 1672 */             angle = -1.5707963267948966D;
/*      */           }
/*      */         
/*      */         }
/* 1676 */         else if (edge == RectangleEdge.TOP) {
/* 1677 */           anchor = TextAnchor.BOTTOM_CENTER;
/* 1678 */           rotationAnchor = TextAnchor.BOTTOM_CENTER;
/*      */         } else {
/*      */           
/* 1681 */           anchor = TextAnchor.TOP_CENTER;
/* 1682 */           rotationAnchor = TextAnchor.TOP_CENTER;
/*      */         } 
/*      */ 
/*      */         
/* 1686 */         Tick tick = new DateTick(tickDate, tickLabel, anchor, rotationAnchor, angle);
/*      */         
/* 1688 */         result.add(tick);
/* 1689 */         hasRolled = false;
/*      */         
/* 1691 */         long currentTickTime = tickDate.getTime();
/* 1692 */         tickDate = unit.addToDate(tickDate, this.timeZone);
/* 1693 */         long nextTickTime = tickDate.getTime();
/* 1694 */         for (int i = 1; i < minorTickSpaces; 
/* 1695 */           i++) {
/* 1696 */           long minorTickTime = currentTickTime + (nextTickTime - currentTickTime) * i / minorTickSpaces;
/*      */ 
/*      */           
/* 1699 */           if (getRange().contains(minorTickTime) && 
/* 1700 */             !isHiddenValue(minorTickTime)) {
/* 1701 */             result.add(new DateTick(TickType.MINOR, new Date(minorTickTime), "", TextAnchor.TOP_CENTER, TextAnchor.CENTER, 0.0D));
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */       
/* 1710 */       tickDate = unit.rollDate(tickDate, this.timeZone);
/* 1711 */       hasRolled = true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1716 */     return result;
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
/*      */   protected List refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/* 1732 */     List<DateTick> result = new ArrayList();
/*      */     
/* 1734 */     Font tickLabelFont = getTickLabelFont();
/* 1735 */     g2.setFont(tickLabelFont);
/*      */     
/* 1737 */     if (isAutoTickUnitSelection()) {
/* 1738 */       selectAutoTickUnit(g2, dataArea, edge);
/*      */     }
/* 1740 */     DateTickUnit unit = getTickUnit();
/* 1741 */     Date tickDate = calculateLowestVisibleTickValue(unit);
/* 1742 */     Date upperDate = getMaximumDate();
/*      */     
/* 1744 */     boolean hasRolled = false;
/* 1745 */     while (tickDate.before(upperDate)) {
/*      */ 
/*      */       
/* 1748 */       if (!hasRolled) {
/* 1749 */         tickDate = correctTickDateForPosition(tickDate, unit, this.tickMarkPosition);
/*      */       }
/*      */ 
/*      */       
/* 1753 */       long lowestTickTime = tickDate.getTime();
/* 1754 */       long distance = unit.addToDate(tickDate, this.timeZone).getTime() - lowestTickTime;
/*      */       
/* 1756 */       int minorTickSpaces = getMinorTickCount();
/* 1757 */       if (minorTickSpaces <= 0) {
/* 1758 */         minorTickSpaces = unit.getMinorTickCount();
/*      */       }
/* 1760 */       for (int minorTick = 1; minorTick < minorTickSpaces; minorTick++) {
/* 1761 */         long minorTickTime = lowestTickTime - distance * minorTick / minorTickSpaces;
/*      */         
/* 1763 */         if (minorTickTime > 0L && getRange().contains(minorTickTime) && 
/* 1764 */           !isHiddenValue(minorTickTime)) {
/* 1765 */           result.add(new DateTick(TickType.MINOR, new Date(minorTickTime), "", TextAnchor.TOP_CENTER, TextAnchor.CENTER, 0.0D));
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1770 */       if (!isHiddenValue(tickDate.getTime())) {
/*      */         String tickLabel;
/*      */         TextAnchor anchor, rotationAnchor;
/* 1773 */         DateFormat formatter = getDateFormatOverride();
/* 1774 */         if (formatter != null) {
/* 1775 */           tickLabel = formatter.format(tickDate);
/*      */         } else {
/*      */           
/* 1778 */           tickLabel = this.tickUnit.dateToString(tickDate);
/*      */         } 
/*      */         
/* 1781 */         double angle = 0.0D;
/* 1782 */         if (isVerticalTickLabels()) {
/* 1783 */           anchor = TextAnchor.BOTTOM_CENTER;
/* 1784 */           rotationAnchor = TextAnchor.BOTTOM_CENTER;
/* 1785 */           if (edge == RectangleEdge.LEFT) {
/* 1786 */             angle = -1.5707963267948966D;
/*      */           } else {
/*      */             
/* 1789 */             angle = 1.5707963267948966D;
/*      */           }
/*      */         
/*      */         }
/* 1793 */         else if (edge == RectangleEdge.LEFT) {
/* 1794 */           anchor = TextAnchor.CENTER_RIGHT;
/* 1795 */           rotationAnchor = TextAnchor.CENTER_RIGHT;
/*      */         } else {
/*      */           
/* 1798 */           anchor = TextAnchor.CENTER_LEFT;
/* 1799 */           rotationAnchor = TextAnchor.CENTER_LEFT;
/*      */         } 
/*      */ 
/*      */         
/* 1803 */         Tick tick = new DateTick(tickDate, tickLabel, anchor, rotationAnchor, angle);
/*      */         
/* 1805 */         result.add(tick);
/* 1806 */         hasRolled = false;
/*      */         
/* 1808 */         long currentTickTime = tickDate.getTime();
/* 1809 */         tickDate = unit.addToDate(tickDate, this.timeZone);
/* 1810 */         long nextTickTime = tickDate.getTime();
/* 1811 */         for (int i = 1; i < minorTickSpaces; 
/* 1812 */           i++) {
/* 1813 */           long minorTickTime = currentTickTime + (nextTickTime - currentTickTime) * i / minorTickSpaces;
/*      */ 
/*      */           
/* 1816 */           if (getRange().contains(minorTickTime) && 
/* 1817 */             !isHiddenValue(minorTickTime)) {
/* 1818 */             result.add(new DateTick(TickType.MINOR, new Date(minorTickTime), "", TextAnchor.TOP_CENTER, TextAnchor.CENTER, 0.0D));
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1826 */       tickDate = unit.rollDate(tickDate, this.timeZone);
/* 1827 */       hasRolled = true;
/*      */     } 
/*      */     
/* 1830 */     return result;
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
/*      */   public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState) {
/* 1855 */     if (!isVisible()) {
/* 1856 */       AxisState axisState = new AxisState(cursor);
/*      */ 
/*      */       
/* 1859 */       List ticks = refreshTicks(g2, axisState, dataArea, edge);
/* 1860 */       axisState.setTicks(ticks);
/* 1861 */       return axisState;
/*      */     } 
/*      */ 
/*      */     
/* 1865 */     AxisState state = drawTickMarksAndLabels(g2, cursor, plotArea, dataArea, edge);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1870 */     if (getAttributedLabel() != null) {
/* 1871 */       state = drawAttributedLabel(getAttributedLabel(), g2, plotArea, dataArea, edge, state);
/*      */     }
/*      */     else {
/*      */       
/* 1875 */       state = drawLabel(getLabel(), g2, plotArea, dataArea, edge, state);
/*      */     } 
/* 1877 */     createAndAddEntity(cursor, state, dataArea, edge, plotState);
/* 1878 */     return state;
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
/*      */   public void zoomRange(double lowerPercent, double upperPercent) {
/*      */     long adjStart, adjEnd;
/* 1891 */     double start = this.timeline.toTimelineValue(
/* 1892 */         (long)getRange().getLowerBound());
/* 1893 */     double end = this.timeline.toTimelineValue(
/* 1894 */         (long)getRange().getUpperBound());
/* 1895 */     double length = end - start;
/*      */ 
/*      */     
/* 1898 */     if (isInverted()) {
/* 1899 */       adjStart = (long)(start + length * (1.0D - upperPercent));
/* 1900 */       adjEnd = (long)(start + length * (1.0D - lowerPercent));
/*      */     } else {
/*      */       
/* 1903 */       adjStart = (long)(start + length * lowerPercent);
/* 1904 */       adjEnd = (long)(start + length * upperPercent);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1909 */     if (adjEnd <= adjStart) {
/* 1910 */       adjEnd = adjStart + 1L;
/*      */     }
/*      */     
/* 1913 */     DateRange dateRange = new DateRange(this.timeline.toMillisecond(adjStart), this.timeline.toMillisecond(adjEnd));
/* 1914 */     setRange((Range)dateRange);
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
/* 1926 */     if (obj == this) {
/* 1927 */       return true;
/*      */     }
/* 1929 */     if (!(obj instanceof DateAxis)) {
/* 1930 */       return false;
/*      */     }
/* 1932 */     DateAxis that = (DateAxis)obj;
/* 1933 */     if (!ObjectUtilities.equal(this.timeZone, that.timeZone)) {
/* 1934 */       return false;
/*      */     }
/* 1936 */     if (!ObjectUtilities.equal(this.locale, that.locale)) {
/* 1937 */       return false;
/*      */     }
/* 1939 */     if (!ObjectUtilities.equal(this.tickUnit, that.tickUnit)) {
/* 1940 */       return false;
/*      */     }
/* 1942 */     if (!ObjectUtilities.equal(this.dateFormatOverride, that.dateFormatOverride))
/*      */     {
/* 1944 */       return false;
/*      */     }
/* 1946 */     if (!ObjectUtilities.equal(this.tickMarkPosition, that.tickMarkPosition))
/*      */     {
/* 1948 */       return false;
/*      */     }
/* 1950 */     if (!ObjectUtilities.equal(this.timeline, that.timeline)) {
/* 1951 */       return false;
/*      */     }
/* 1953 */     return super.equals(obj);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1963 */     return super.hashCode();
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
/*      */   public Object clone() throws CloneNotSupportedException {
/* 1976 */     DateAxis clone = (DateAxis)super.clone();
/*      */     
/* 1978 */     if (this.dateFormatOverride != null) {
/* 1979 */       clone
/* 1980 */         .dateFormatOverride = (DateFormat)this.dateFormatOverride.clone();
/*      */     }
/*      */     
/* 1983 */     return clone;
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
/*      */   public static TickUnitSource createStandardDateTickUnits(TimeZone zone) {
/* 2002 */     return createStandardDateTickUnits(zone, Locale.getDefault());
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/DateAxis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */