/*      */ package org.jfree.chart.axis;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.SimpleTimeZone;
/*      */ import java.util.TimeZone;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SegmentedTimeline
/*      */   implements Timeline, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1093779862539903110L;
/*      */   public static final long DAY_SEGMENT_SIZE = 86400000L;
/*      */   public static final long HOUR_SEGMENT_SIZE = 3600000L;
/*      */   public static final long FIFTEEN_MINUTE_SEGMENT_SIZE = 900000L;
/*      */   public static final long MINUTE_SEGMENT_SIZE = 60000L;
/*      */   public static long FIRST_MONDAY_AFTER_1900;
/*      */   public static TimeZone NO_DST_TIME_ZONE;
/*  210 */   public static TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Calendar workingCalendarNoDST;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  221 */   private Calendar workingCalendar = Calendar.getInstance();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long segmentSize;
/*      */ 
/*      */ 
/*      */   
/*      */   private int segmentsIncluded;
/*      */ 
/*      */ 
/*      */   
/*      */   private int segmentsExcluded;
/*      */ 
/*      */ 
/*      */   
/*      */   private int groupSegmentCount;
/*      */ 
/*      */ 
/*      */   
/*      */   private long startTime;
/*      */ 
/*      */ 
/*      */   
/*      */   private long segmentsIncludedSize;
/*      */ 
/*      */ 
/*      */   
/*      */   private long segmentsExcludedSize;
/*      */ 
/*      */ 
/*      */   
/*      */   private long segmentsGroupSize;
/*      */ 
/*      */ 
/*      */   
/*  258 */   private List exceptionSegments = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SegmentedTimeline baseTimeline;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean adjustForDaylightSaving = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  281 */     int offset = TimeZone.getDefault().getRawOffset();
/*  282 */     NO_DST_TIME_ZONE = new SimpleTimeZone(offset, "UTC-" + offset);
/*      */ 
/*      */ 
/*      */     
/*  286 */     Calendar cal = new GregorianCalendar(NO_DST_TIME_ZONE);
/*  287 */     cal.set(1900, 0, 1, 0, 0, 0);
/*  288 */     cal.set(14, 0);
/*  289 */     while (cal.get(7) != 2) {
/*  290 */       cal.add(5, 1);
/*      */     }
/*      */ 
/*      */     
/*  294 */     FIRST_MONDAY_AFTER_1900 = cal.getTime().getTime();
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
/*      */   public SegmentedTimeline(long segmentSize, int segmentsIncluded, int segmentsExcluded) {
/*  318 */     this.segmentSize = segmentSize;
/*  319 */     this.segmentsIncluded = segmentsIncluded;
/*  320 */     this.segmentsExcluded = segmentsExcluded;
/*      */     
/*  322 */     this.groupSegmentCount = this.segmentsIncluded + this.segmentsExcluded;
/*  323 */     this.segmentsIncludedSize = this.segmentsIncluded * this.segmentSize;
/*  324 */     this.segmentsExcludedSize = this.segmentsExcluded * this.segmentSize;
/*  325 */     this.segmentsGroupSize = this.segmentsIncludedSize + this.segmentsExcludedSize;
/*      */     
/*  327 */     int offset = TimeZone.getDefault().getRawOffset();
/*  328 */     TimeZone z = new SimpleTimeZone(offset, "UTC-" + offset);
/*  329 */     this
/*  330 */       .workingCalendarNoDST = new GregorianCalendar(z, Locale.getDefault());
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
/*      */   public static long firstMondayAfter1900() {
/*  342 */     int offset = TimeZone.getDefault().getRawOffset();
/*  343 */     TimeZone z = new SimpleTimeZone(offset, "UTC-" + offset);
/*      */ 
/*      */ 
/*      */     
/*  347 */     Calendar cal = new GregorianCalendar(z);
/*  348 */     cal.set(1900, 0, 1, 0, 0, 0);
/*  349 */     cal.set(14, 0);
/*  350 */     while (cal.get(7) != 2) {
/*  351 */       cal.add(5, 1);
/*      */     }
/*      */ 
/*      */     
/*  355 */     return cal.getTime().getTime();
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
/*      */   public static SegmentedTimeline newMondayThroughFridayTimeline() {
/*  367 */     SegmentedTimeline timeline = new SegmentedTimeline(86400000L, 5, 2);
/*      */     
/*  369 */     timeline.setStartTime(firstMondayAfter1900());
/*  370 */     return timeline;
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
/*      */   public static SegmentedTimeline newFifteenMinuteTimeline() {
/*  391 */     SegmentedTimeline timeline = new SegmentedTimeline(900000L, 28, 68);
/*      */     
/*  393 */     timeline.setStartTime(firstMondayAfter1900() + 36L * timeline
/*  394 */         .getSegmentSize());
/*  395 */     timeline.setBaseTimeline(newMondayThroughFridayTimeline());
/*  396 */     return timeline;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAdjustForDaylightSaving() {
/*  406 */     return this.adjustForDaylightSaving;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAdjustForDaylightSaving(boolean adjust) {
/*  416 */     this.adjustForDaylightSaving = adjust;
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
/*      */   public void setStartTime(long millisecond) {
/*  430 */     this.startTime = millisecond;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getStartTime() {
/*  440 */     return this.startTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSegmentsExcluded() {
/*  449 */     return this.segmentsExcluded;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getSegmentsExcludedSize() {
/*  459 */     return this.segmentsExcludedSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getGroupSegmentCount() {
/*  469 */     return this.groupSegmentCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getSegmentsGroupSize() {
/*  479 */     return this.segmentsGroupSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSegmentsIncluded() {
/*  488 */     return this.segmentsIncluded;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getSegmentsIncludedSize() {
/*  497 */     return this.segmentsIncludedSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getSegmentSize() {
/*  506 */     return this.segmentSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List getExceptionSegments() {
/*  516 */     return Collections.unmodifiableList(this.exceptionSegments);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExceptionSegments(List exceptionSegments) {
/*  525 */     this.exceptionSegments = exceptionSegments;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SegmentedTimeline getBaseTimeline() {
/*  534 */     return this.baseTimeline;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseTimeline(SegmentedTimeline baseTimeline) {
/*  545 */     if (baseTimeline != null) {
/*  546 */       if (baseTimeline.getSegmentSize() < this.segmentSize) {
/*  547 */         throw new IllegalArgumentException("baseTimeline.getSegmentSize() is smaller than segmentSize");
/*      */       }
/*      */ 
/*      */       
/*  551 */       if (baseTimeline.getStartTime() > this.startTime) {
/*  552 */         throw new IllegalArgumentException("baseTimeline.getStartTime() is after startTime");
/*      */       }
/*      */       
/*  555 */       if (baseTimeline.getSegmentSize() % this.segmentSize != 0L) {
/*  556 */         throw new IllegalArgumentException("baseTimeline.getSegmentSize() is not multiple of segmentSize");
/*      */       }
/*      */ 
/*      */       
/*  560 */       if ((this.startTime - baseTimeline
/*  561 */         .getStartTime()) % this.segmentSize != 0L) {
/*  562 */         throw new IllegalArgumentException("baseTimeline is not aligned");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  567 */     this.baseTimeline = baseTimeline;
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
/*      */   public long toTimelineValue(long millisecond) {
/*  583 */     long result, rawMilliseconds = millisecond - this.startTime;
/*  584 */     long groupMilliseconds = rawMilliseconds % this.segmentsGroupSize;
/*  585 */     long groupIndex = rawMilliseconds / this.segmentsGroupSize;
/*      */     
/*  587 */     if (groupMilliseconds >= this.segmentsIncludedSize) {
/*  588 */       result = toTimelineValue(this.startTime + this.segmentsGroupSize * (groupIndex + 1L));
/*      */     }
/*      */     else {
/*      */       
/*  592 */       Segment segment = getSegment(millisecond);
/*  593 */       if (segment.inExceptionSegments()) {
/*      */         int p;
/*  595 */         while ((p = binarySearchExceptionSegments(segment)) >= 0) {
/*  596 */           segment = getSegment(
/*  597 */               millisecond = ((Segment)this.exceptionSegments.get(p)).getSegmentEnd() + 1L);
/*      */         }
/*  599 */         result = toTimelineValue(millisecond);
/*      */       } else {
/*      */         
/*  602 */         long shiftedSegmentedValue = millisecond - this.startTime;
/*  603 */         long x = shiftedSegmentedValue % this.segmentsGroupSize;
/*  604 */         long y = shiftedSegmentedValue / this.segmentsGroupSize;
/*      */ 
/*      */         
/*  607 */         long wholeExceptionsBeforeDomainValue = getExceptionSegmentCount(this.startTime, millisecond - 1L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  616 */         if (x < this.segmentsIncludedSize) {
/*  617 */           result = this.segmentsIncludedSize * y + x - wholeExceptionsBeforeDomainValue * this.segmentSize;
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  623 */           result = this.segmentsIncludedSize * (y + 1L) - wholeExceptionsBeforeDomainValue * this.segmentSize;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  631 */     return result;
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
/*      */   public long toTimelineValue(Date date) {
/*  645 */     return toTimelineValue(getTime(date));
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
/*      */   public long toMillisecond(long timelineValue) {
/*  660 */     Segment result = new Segment(this.startTime + timelineValue + timelineValue / this.segmentsIncludedSize * this.segmentsExcludedSize);
/*      */ 
/*      */ 
/*      */     
/*  664 */     long lastIndex = this.startTime;
/*      */ 
/*      */     
/*  667 */     while (lastIndex <= result.segmentStart) {
/*      */       long exceptionSegmentCount;
/*      */ 
/*      */       
/*  671 */       while ((exceptionSegmentCount = getExceptionSegmentCount(lastIndex, result.millisecond / this.segmentSize * this.segmentSize - 1L)) > 0L) {
/*      */ 
/*      */ 
/*      */         
/*  675 */         lastIndex = result.segmentStart;
/*      */ 
/*      */         
/*  678 */         for (int i = 0; i < exceptionSegmentCount;) {
/*      */           while (true)
/*  680 */           { result.inc();
/*      */             
/*  682 */             if (!result.inExcludeSegments())
/*      */               i++;  } 
/*      */         } 
/*  685 */       }  lastIndex = result.segmentStart;
/*      */ 
/*      */       
/*  688 */       while (result.inExceptionSegments() || result.inExcludeSegments()) {
/*  689 */         result.inc();
/*  690 */         lastIndex += this.segmentSize;
/*      */       } 
/*      */       
/*  693 */       lastIndex++;
/*      */     } 
/*      */     
/*  696 */     return getTimeFromLong(result.millisecond);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getTimeFromLong(long date) {
/*  707 */     long result = date;
/*  708 */     if (this.adjustForDaylightSaving) {
/*  709 */       this.workingCalendarNoDST.setTime(new Date(date));
/*  710 */       this.workingCalendar.set(this.workingCalendarNoDST
/*  711 */           .get(1), this.workingCalendarNoDST
/*  712 */           .get(2), this.workingCalendarNoDST
/*  713 */           .get(5), this.workingCalendarNoDST
/*  714 */           .get(11), this.workingCalendarNoDST
/*  715 */           .get(12), this.workingCalendarNoDST
/*  716 */           .get(13));
/*      */       
/*  718 */       this.workingCalendar.set(14, this.workingCalendarNoDST
/*  719 */           .get(14));
/*      */ 
/*      */       
/*  722 */       result = this.workingCalendar.getTime().getTime();
/*      */     } 
/*  724 */     return result;
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
/*      */   public boolean containsDomainValue(long millisecond) {
/*  736 */     Segment segment = getSegment(millisecond);
/*  737 */     return segment.inIncludeSegments();
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
/*      */   public boolean containsDomainValue(Date date) {
/*  749 */     return containsDomainValue(getTime(date));
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
/*      */   public boolean containsDomainRange(long domainValueStart, long domainValueEnd) {
/*  765 */     if (domainValueEnd < domainValueStart) {
/*  766 */       throw new IllegalArgumentException("domainValueEnd (" + domainValueEnd + ") < domainValueStart (" + domainValueStart + ")");
/*      */     }
/*      */ 
/*      */     
/*  770 */     Segment segment = getSegment(domainValueStart);
/*  771 */     boolean contains = true;
/*      */     do {
/*  773 */       contains = segment.inIncludeSegments();
/*  774 */       if (segment.contains(domainValueEnd)) {
/*      */         break;
/*      */       }
/*      */       
/*  778 */       segment.inc();
/*      */     
/*      */     }
/*  781 */     while (contains);
/*  782 */     return contains;
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
/*      */   public boolean containsDomainRange(Date dateDomainValueStart, Date dateDomainValueEnd) {
/*  798 */     return containsDomainRange(getTime(dateDomainValueStart), 
/*  799 */         getTime(dateDomainValueEnd));
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
/*      */   public void addException(long millisecond) {
/*  815 */     addException(new Segment(millisecond));
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
/*      */   public void addException(long fromDomainValue, long toDomainValue) {
/*  834 */     addException(new SegmentRange(fromDomainValue, toDomainValue));
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
/*      */   public void addException(Date exceptionDate) {
/*  849 */     addException(getTime(exceptionDate));
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
/*      */   public void addExceptions(List exceptionList) {
/*  866 */     for (Iterator<Date> iter = exceptionList.iterator(); iter.hasNext();) {
/*  867 */       addException(iter.next());
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
/*      */   private void addException(Segment segment) {
/*  882 */     if (segment.inIncludeSegments()) {
/*  883 */       int p = binarySearchExceptionSegments(segment);
/*  884 */       this.exceptionSegments.add(-(p + 1), segment);
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
/*      */   
/*      */   public void addBaseTimelineException(long domainValue) {
/*  906 */     Segment baseSegment = this.baseTimeline.getSegment(domainValue);
/*  907 */     if (baseSegment.inIncludeSegments()) {
/*      */ 
/*      */ 
/*      */       
/*  911 */       Segment segment = getSegment(baseSegment.getSegmentStart());
/*  912 */       while (segment.getSegmentStart() <= baseSegment.getSegmentEnd()) {
/*  913 */         if (segment.inIncludeSegments()) {
/*      */ 
/*      */           
/*  916 */           long toDomainValue, fromDomainValue = segment.getSegmentStart();
/*      */           
/*      */           do {
/*  919 */             toDomainValue = segment.getSegmentEnd();
/*  920 */             segment.inc();
/*      */           }
/*  922 */           while (segment.inIncludeSegments());
/*      */ 
/*      */           
/*  925 */           addException(fromDomainValue, toDomainValue);
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  930 */         segment.inc();
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void addBaseTimelineException(Date date) {
/*  949 */     addBaseTimelineException(getTime(date));
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
/*      */   public void addBaseTimelineExclusions(long fromBaseDomainValue, long toBaseDomainValue) {
/*  965 */     Segment baseSegment = this.baseTimeline.getSegment(fromBaseDomainValue);
/*  966 */     while (baseSegment.getSegmentStart() <= toBaseDomainValue && 
/*  967 */       !baseSegment.inExcludeSegments())
/*      */     {
/*  969 */       baseSegment.inc();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  974 */     while (baseSegment.getSegmentStart() <= toBaseDomainValue) {
/*      */ 
/*      */ 
/*      */       
/*  978 */       long baseExclusionRangeEnd = baseSegment.getSegmentStart() + this.baseTimeline.getSegmentsExcluded() * this.baseTimeline.getSegmentSize() - 1L;
/*      */ 
/*      */ 
/*      */       
/*  982 */       Segment segment = getSegment(baseSegment.getSegmentStart());
/*  983 */       while (segment.getSegmentStart() <= baseExclusionRangeEnd) {
/*  984 */         if (segment.inIncludeSegments()) {
/*      */ 
/*      */           
/*  987 */           long toDomainValue, fromDomainValue = segment.getSegmentStart();
/*      */           
/*      */           do {
/*  990 */             toDomainValue = segment.getSegmentEnd();
/*  991 */             segment.inc();
/*      */           }
/*  993 */           while (segment.inIncludeSegments());
/*      */ 
/*      */           
/*  996 */           addException(new BaseTimelineSegmentRange(fromDomainValue, toDomainValue));
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 1001 */         segment.inc();
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1006 */       baseSegment.inc(this.baseTimeline.getGroupSegmentCount());
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
/*      */   public long getExceptionSegmentCount(long fromMillisecond, long toMillisecond) {
/* 1021 */     if (toMillisecond < fromMillisecond) {
/* 1022 */       return 0L;
/*      */     }
/*      */     
/* 1025 */     int n = 0;
/* 1026 */     Iterator<Segment> iter = this.exceptionSegments.iterator();
/* 1027 */     while (iter.hasNext()) {
/* 1028 */       Segment segment = iter.next();
/* 1029 */       Segment intersection = segment.intersect(fromMillisecond, toMillisecond);
/*      */       
/* 1031 */       if (intersection != null) {
/* 1032 */         n = (int)(n + intersection.getSegmentCount());
/*      */       }
/*      */     } 
/*      */     
/* 1036 */     return n;
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
/*      */   public Segment getSegment(long millisecond) {
/* 1051 */     return new Segment(millisecond);
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
/*      */   public Segment getSegment(Date date) {
/* 1069 */     return getSegment(getTime(date));
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
/*      */   private boolean equals(Object o, Object p) {
/* 1083 */     return (o == p || (o != null && o.equals(p)));
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
/*      */   public boolean equals(Object o) {
/* 1095 */     if (o instanceof SegmentedTimeline) {
/* 1096 */       SegmentedTimeline other = (SegmentedTimeline)o;
/*      */       
/* 1098 */       boolean b0 = (this.segmentSize == other.getSegmentSize());
/* 1099 */       boolean b1 = (this.segmentsIncluded == other.getSegmentsIncluded());
/* 1100 */       boolean b2 = (this.segmentsExcluded == other.getSegmentsExcluded());
/* 1101 */       boolean b3 = (this.startTime == other.getStartTime());
/* 1102 */       boolean b4 = equals(this.exceptionSegments, other
/* 1103 */           .getExceptionSegments());
/* 1104 */       return (b0 && b1 && b2 && b3 && b4);
/*      */     } 
/*      */     
/* 1107 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1118 */     int result = 19;
/* 1119 */     result = 37 * result + (int)(this.segmentSize ^ this.segmentSize >>> 32L);
/*      */     
/* 1121 */     result = 37 * result + (int)(this.startTime ^ this.startTime >>> 32L);
/* 1122 */     return result;
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
/*      */   private int binarySearchExceptionSegments(Segment segment) {
/* 1141 */     int low = 0;
/* 1142 */     int high = this.exceptionSegments.size() - 1;
/*      */     
/* 1144 */     while (low <= high) {
/* 1145 */       int mid = (low + high) / 2;
/* 1146 */       Segment midSegment = this.exceptionSegments.get(mid);
/*      */ 
/*      */       
/* 1149 */       if (segment.contains(midSegment) || midSegment.contains(segment)) {
/* 1150 */         return mid;
/*      */       }
/*      */       
/* 1153 */       if (midSegment.before(segment)) {
/* 1154 */         low = mid + 1; continue;
/*      */       } 
/* 1156 */       if (midSegment.after(segment)) {
/* 1157 */         high = mid - 1;
/*      */         continue;
/*      */       } 
/* 1160 */       throw new IllegalStateException("Invalid condition.");
/*      */     } 
/*      */     
/* 1163 */     return -(low + 1);
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
/*      */   public long getTime(Date date) {
/* 1177 */     long result = date.getTime();
/* 1178 */     if (this.adjustForDaylightSaving) {
/* 1179 */       this.workingCalendar.setTime(date);
/* 1180 */       this.workingCalendarNoDST.set(this.workingCalendar
/* 1181 */           .get(1), this.workingCalendar
/* 1182 */           .get(2), this.workingCalendar
/* 1183 */           .get(5), this.workingCalendar
/* 1184 */           .get(11), this.workingCalendar
/* 1185 */           .get(12), this.workingCalendar
/* 1186 */           .get(13));
/* 1187 */       this.workingCalendarNoDST.set(14, this.workingCalendar
/* 1188 */           .get(14));
/* 1189 */       Date revisedDate = this.workingCalendarNoDST.getTime();
/* 1190 */       result = revisedDate.getTime();
/*      */     } 
/*      */     
/* 1193 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getDate(long value) {
/* 1204 */     this.workingCalendarNoDST.setTime(new Date(value));
/* 1205 */     return this.workingCalendarNoDST.getTime();
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
/*      */   public Object clone() throws CloneNotSupportedException {
/* 1217 */     SegmentedTimeline clone = (SegmentedTimeline)super.clone();
/* 1218 */     return clone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class Segment
/*      */     implements Comparable, Cloneable, Serializable
/*      */   {
/*      */     protected long segmentNumber;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected long segmentStart;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected long segmentEnd;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected long millisecond;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Segment() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Segment(long millisecond) {
/* 1256 */       this.segmentNumber = calculateSegmentNumber(millisecond);
/* 1257 */       this
/* 1258 */         .segmentStart = SegmentedTimeline.this.startTime + this.segmentNumber * SegmentedTimeline.this.segmentSize;
/* 1259 */       this
/* 1260 */         .segmentEnd = this.segmentStart + SegmentedTimeline.this.segmentSize - 1L;
/* 1261 */       this.millisecond = millisecond;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long calculateSegmentNumber(long millis) {
/* 1272 */       if (millis >= SegmentedTimeline.this.startTime)
/*      */       {
/* 1274 */         return (millis - SegmentedTimeline.this.startTime) / SegmentedTimeline.this.segmentSize;
/*      */       }
/*      */ 
/*      */       
/* 1278 */       return (millis - SegmentedTimeline.this.startTime) / SegmentedTimeline.this.segmentSize - 1L;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getSegmentNumber() {
/* 1288 */       return this.segmentNumber;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getSegmentCount() {
/* 1298 */       return 1L;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getSegmentStart() {
/* 1307 */       return this.segmentStart;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getSegmentEnd() {
/* 1316 */       return this.segmentEnd;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getMillisecond() {
/* 1326 */       return this.millisecond;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Date getDate() {
/* 1336 */       return SegmentedTimeline.this.getDate(this.millisecond);
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
/*      */     public boolean contains(long millis) {
/* 1349 */       return (this.segmentStart <= millis && millis <= this.segmentEnd);
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
/*      */     public boolean contains(long from, long to) {
/* 1363 */       return (this.segmentStart <= from && to <= this.segmentEnd);
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
/*      */     public boolean contains(Segment segment) {
/* 1375 */       return contains(segment.getSegmentStart(), segment.getSegmentEnd());
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
/*      */     public boolean contained(long from, long to) {
/* 1387 */       return (from <= this.segmentStart && this.segmentEnd <= to);
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
/*      */     public Segment intersect(long from, long to) {
/* 1400 */       if (from <= this.segmentStart && this.segmentEnd <= to) {
/* 1401 */         return this;
/*      */       }
/*      */       
/* 1404 */       return null;
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
/*      */     public boolean before(Segment other) {
/* 1417 */       return (this.segmentEnd < other.getSegmentStart());
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
/*      */     public boolean after(Segment other) {
/* 1429 */       return (this.segmentStart > other.getSegmentEnd());
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
/*      */     public boolean equals(Object object) {
/* 1442 */       if (object instanceof Segment) {
/* 1443 */         Segment other = (Segment)object;
/* 1444 */         return (this.segmentNumber == other.getSegmentNumber() && this.segmentStart == other
/* 1445 */           .getSegmentStart() && this.segmentEnd == other
/* 1446 */           .getSegmentEnd() && this.millisecond == other
/* 1447 */           .getMillisecond());
/*      */       } 
/*      */       
/* 1450 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Segment copy() {
/*      */       try {
/* 1462 */         return (Segment)clone();
/*      */       }
/* 1464 */       catch (CloneNotSupportedException e) {
/* 1465 */         return null;
/*      */       } 
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
/*      */     public int compareTo(Object object) {
/* 1480 */       Segment other = (Segment)object;
/* 1481 */       if (before(other)) {
/* 1482 */         return -1;
/*      */       }
/* 1484 */       if (after(other)) {
/* 1485 */         return 1;
/*      */       }
/*      */       
/* 1488 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean inIncludeSegments() {
/* 1499 */       if (getSegmentNumberRelativeToGroup() < SegmentedTimeline.this
/* 1500 */         .segmentsIncluded) {
/* 1501 */         return !inExceptionSegments();
/*      */       }
/*      */       
/* 1504 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean inExcludeSegments() {
/* 1514 */       return 
/* 1515 */         (getSegmentNumberRelativeToGroup() >= SegmentedTimeline.this.segmentsIncluded);
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
/*      */     private long getSegmentNumberRelativeToGroup() {
/* 1528 */       long p = this.segmentNumber % SegmentedTimeline.this.groupSegmentCount;
/* 1529 */       if (p < 0L) {
/* 1530 */         p += SegmentedTimeline.this.groupSegmentCount;
/*      */       }
/* 1532 */       return p;
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
/*      */     
/*      */     public boolean inExceptionSegments() {
/* 1547 */       return (SegmentedTimeline.this.binarySearchExceptionSegments(this) >= 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void inc(long n) {
/* 1557 */       this.segmentNumber += n;
/* 1558 */       long m = n * SegmentedTimeline.this.segmentSize;
/* 1559 */       this.segmentStart += m;
/* 1560 */       this.segmentEnd += m;
/* 1561 */       this.millisecond += m;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void inc() {
/* 1569 */       inc(1L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void dec(long n) {
/* 1579 */       this.segmentNumber -= n;
/* 1580 */       long m = n * SegmentedTimeline.this.segmentSize;
/* 1581 */       this.segmentStart -= m;
/* 1582 */       this.segmentEnd -= m;
/* 1583 */       this.millisecond -= m;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void dec() {
/* 1591 */       dec(1L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void moveIndexToStart() {
/* 1598 */       this.millisecond = this.segmentStart;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void moveIndexToEnd() {
/* 1605 */       this.millisecond = this.segmentEnd;
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
/*      */   protected class SegmentRange
/*      */     extends Segment
/*      */   {
/*      */     private long segmentCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SegmentRange(long fromMillisecond, long toMillisecond) {
/* 1630 */       SegmentedTimeline.Segment start = SegmentedTimeline.this.getSegment(fromMillisecond);
/* 1631 */       SegmentedTimeline.Segment end = SegmentedTimeline.this.getSegment(toMillisecond);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1638 */       this.millisecond = fromMillisecond;
/* 1639 */       this.segmentNumber = calculateSegmentNumber(fromMillisecond);
/* 1640 */       this.segmentStart = start.segmentStart;
/* 1641 */       this.segmentEnd = end.segmentEnd;
/* 1642 */       this
/* 1643 */         .segmentCount = end.getSegmentNumber() - start.getSegmentNumber() + 1L;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getSegmentCount() {
/* 1653 */       return this.segmentCount;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SegmentedTimeline.Segment intersect(long from, long to) {
/* 1672 */       long start = Math.max(from, this.segmentStart);
/* 1673 */       long end = Math.min(to, this.segmentEnd);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1678 */       if (start <= end) {
/* 1679 */         return new SegmentRange(start, end);
/*      */       }
/*      */       
/* 1682 */       return null;
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
/*      */     public boolean inIncludeSegments() {
/* 1694 */       SegmentedTimeline.Segment segment = SegmentedTimeline.this.getSegment(this.segmentStart);
/* 1695 */       for (; segment.getSegmentStart() < this.segmentEnd; 
/* 1696 */         segment.inc()) {
/* 1697 */         if (!segment.inIncludeSegments()) {
/* 1698 */           return false;
/*      */         }
/*      */       } 
/* 1701 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean inExcludeSegments() {
/* 1711 */       SegmentedTimeline.Segment segment = SegmentedTimeline.this.getSegment(this.segmentStart);
/* 1712 */       for (; segment.getSegmentStart() < this.segmentEnd; 
/* 1713 */         segment.inc()) {
/* 1714 */         if (!segment.inExceptionSegments()) {
/* 1715 */           return false;
/*      */         }
/*      */       } 
/* 1718 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void inc(long n) {
/* 1729 */       throw new IllegalArgumentException("Not implemented in SegmentRange");
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
/*      */   protected class BaseTimelineSegmentRange
/*      */     extends SegmentRange
/*      */   {
/*      */     public BaseTimelineSegmentRange(long fromDomainValue, long toDomainValue) {
/* 1748 */       super(fromDomainValue, toDomainValue);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/SegmentedTimeline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */