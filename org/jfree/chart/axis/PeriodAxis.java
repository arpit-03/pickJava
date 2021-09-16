/*      */ package org.jfree.chart.axis;
/*      */ 
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.text.DateFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.TimeZone;
/*      */ import org.jfree.chart.plot.Plot;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.plot.ValueAxisPlot;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.time.Day;
/*      */ import org.jfree.data.time.Month;
/*      */ import org.jfree.data.time.RegularTimePeriod;
/*      */ import org.jfree.data.time.Year;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.TextAnchor;
/*      */ import org.jfree.util.PublicCloneable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PeriodAxis
/*      */   extends ValueAxis
/*      */   implements Cloneable, PublicCloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 8353295532075872069L;
/*      */   private RegularTimePeriod first;
/*      */   private RegularTimePeriod last;
/*      */   private TimeZone timeZone;
/*      */   private Locale locale;
/*      */   private Calendar calendar;
/*      */   private Class autoRangeTimePeriodClass;
/*      */   private Class majorTickTimePeriodClass;
/*      */   private boolean minorTickMarksVisible;
/*      */   private Class minorTickTimePeriodClass;
/*  170 */   private float minorTickMarkInsideLength = 0.0F;
/*      */ 
/*      */   
/*  173 */   private float minorTickMarkOutsideLength = 2.0F;
/*      */ 
/*      */   
/*  176 */   private transient Stroke minorTickMarkStroke = new BasicStroke(0.5F);
/*      */ 
/*      */   
/*  179 */   private transient Paint minorTickMarkPaint = Color.black;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PeriodAxisLabelInfo[] labelInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeriodAxis(String label) {
/*  190 */     this(label, (RegularTimePeriod)new Day(), (RegularTimePeriod)new Day());
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
/*      */   public PeriodAxis(String label, RegularTimePeriod first, RegularTimePeriod last) {
/*  204 */     this(label, first, last, TimeZone.getDefault(), Locale.getDefault());
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
/*      */   public PeriodAxis(String label, RegularTimePeriod first, RegularTimePeriod last, TimeZone timeZone) {
/*  222 */     this(label, first, last, timeZone, Locale.getDefault());
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
/*      */   public PeriodAxis(String label, RegularTimePeriod first, RegularTimePeriod last, TimeZone timeZone, Locale locale) {
/*  240 */     super(label, (TickUnitSource)null);
/*  241 */     ParamChecks.nullNotPermitted(timeZone, "timeZone");
/*  242 */     ParamChecks.nullNotPermitted(locale, "locale");
/*  243 */     this.first = first;
/*  244 */     this.last = last;
/*  245 */     this.timeZone = timeZone;
/*  246 */     this.locale = locale;
/*  247 */     this.calendar = Calendar.getInstance(timeZone, locale);
/*  248 */     this.first.peg(this.calendar);
/*  249 */     this.last.peg(this.calendar);
/*  250 */     this.autoRangeTimePeriodClass = first.getClass();
/*  251 */     this.majorTickTimePeriodClass = first.getClass();
/*  252 */     this.minorTickMarksVisible = false;
/*  253 */     this.minorTickTimePeriodClass = RegularTimePeriod.downsize(this.majorTickTimePeriodClass);
/*      */     
/*  255 */     setAutoRange(true);
/*  256 */     this.labelInfo = new PeriodAxisLabelInfo[2];
/*  257 */     SimpleDateFormat df0 = new SimpleDateFormat("MMM", locale);
/*  258 */     df0.setTimeZone(timeZone);
/*  259 */     this.labelInfo[0] = new PeriodAxisLabelInfo(Month.class, df0);
/*  260 */     SimpleDateFormat df1 = new SimpleDateFormat("yyyy", locale);
/*  261 */     df1.setTimeZone(timeZone);
/*  262 */     this.labelInfo[1] = new PeriodAxisLabelInfo(Year.class, df1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RegularTimePeriod getFirst() {
/*  271 */     return this.first;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFirst(RegularTimePeriod first) {
/*  281 */     ParamChecks.nullNotPermitted(first, "first");
/*  282 */     this.first = first;
/*  283 */     this.first.peg(this.calendar);
/*  284 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RegularTimePeriod getLast() {
/*  293 */     return this.last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLast(RegularTimePeriod last) {
/*  303 */     ParamChecks.nullNotPermitted(last, "last");
/*  304 */     this.last = last;
/*  305 */     this.last.peg(this.calendar);
/*  306 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeZone getTimeZone() {
/*  316 */     return this.timeZone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTimeZone(TimeZone zone) {
/*  326 */     ParamChecks.nullNotPermitted(zone, "zone");
/*  327 */     this.timeZone = zone;
/*  328 */     this.calendar = Calendar.getInstance(zone, this.locale);
/*  329 */     this.first.peg(this.calendar);
/*  330 */     this.last.peg(this.calendar);
/*  331 */     fireChangeEvent();
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
/*  342 */     return this.locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class getAutoRangeTimePeriodClass() {
/*  352 */     return this.autoRangeTimePeriodClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoRangeTimePeriodClass(Class c) {
/*  363 */     ParamChecks.nullNotPermitted(c, "c");
/*  364 */     this.autoRangeTimePeriodClass = c;
/*  365 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class getMajorTickTimePeriodClass() {
/*  374 */     return this.majorTickTimePeriodClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMajorTickTimePeriodClass(Class c) {
/*  385 */     ParamChecks.nullNotPermitted(c, "c");
/*  386 */     this.majorTickTimePeriodClass = c;
/*  387 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMinorTickMarksVisible() {
/*  398 */     return this.minorTickMarksVisible;
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
/*      */   public void setMinorTickMarksVisible(boolean visible) {
/*  410 */     this.minorTickMarksVisible = visible;
/*  411 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class getMinorTickTimePeriodClass() {
/*  420 */     return this.minorTickTimePeriodClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinorTickTimePeriodClass(Class c) {
/*  431 */     ParamChecks.nullNotPermitted(c, "c");
/*  432 */     this.minorTickTimePeriodClass = c;
/*  433 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getMinorTickMarkStroke() {
/*  443 */     return this.minorTickMarkStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinorTickMarkStroke(Stroke stroke) {
/*  454 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/*  455 */     this.minorTickMarkStroke = stroke;
/*  456 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getMinorTickMarkPaint() {
/*  466 */     return this.minorTickMarkPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinorTickMarkPaint(Paint paint) {
/*  477 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  478 */     this.minorTickMarkPaint = paint;
/*  479 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getMinorTickMarkInsideLength() {
/*  489 */     return this.minorTickMarkInsideLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinorTickMarkInsideLength(float length) {
/*  500 */     this.minorTickMarkInsideLength = length;
/*  501 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getMinorTickMarkOutsideLength() {
/*  511 */     return this.minorTickMarkOutsideLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinorTickMarkOutsideLength(float length) {
/*  522 */     this.minorTickMarkOutsideLength = length;
/*  523 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeriodAxisLabelInfo[] getLabelInfo() {
/*  532 */     return this.labelInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelInfo(PeriodAxisLabelInfo[] info) {
/*  542 */     this.labelInfo = info;
/*  543 */     fireChangeEvent();
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
/*      */   public void setRange(Range range, boolean turnOffAutoRange, boolean notify) {
/*  560 */     long upper = Math.round(range.getUpperBound());
/*  561 */     long lower = Math.round(range.getLowerBound());
/*  562 */     this.first = createInstance(this.autoRangeTimePeriodClass, new Date(lower), this.timeZone, this.locale);
/*      */     
/*  564 */     this.last = createInstance(this.autoRangeTimePeriodClass, new Date(upper), this.timeZone, this.locale);
/*      */     
/*  566 */     super.setRange(new Range(this.first.getFirstMillisecond(), this.last
/*  567 */           .getLastMillisecond() + 1.0D), turnOffAutoRange, notify);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void configure() {
/*  577 */     if (isAutoRange()) {
/*  578 */       autoAdjustRange();
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
/*      */   public AxisSpace reserveSpace(Graphics2D g2, Plot plot, Rectangle2D plotArea, RectangleEdge edge, AxisSpace space) {
/*  599 */     if (space == null) {
/*  600 */       space = new AxisSpace();
/*      */     }
/*      */ 
/*      */     
/*  604 */     if (!isVisible()) {
/*  605 */       return space;
/*      */     }
/*      */ 
/*      */     
/*  609 */     double dimension = getFixedDimension();
/*  610 */     if (dimension > 0.0D) {
/*  611 */       space.ensureAtLeast(dimension, edge);
/*      */     }
/*      */ 
/*      */     
/*  615 */     Rectangle2D labelEnclosure = getLabelEnclosure(g2, edge);
/*      */     
/*  617 */     double tickLabelBandsDimension = 0.0D;
/*      */     
/*  619 */     for (int i = 0; i < this.labelInfo.length; i++) {
/*  620 */       PeriodAxisLabelInfo info = this.labelInfo[i];
/*  621 */       FontMetrics fm = g2.getFontMetrics(info.getLabelFont());
/*  622 */       tickLabelBandsDimension += info
/*  623 */         .getPadding().extendHeight(fm.getHeight());
/*      */     } 
/*      */     
/*  626 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  627 */       double labelHeight = labelEnclosure.getHeight();
/*  628 */       space.add(labelHeight + tickLabelBandsDimension, edge);
/*      */     }
/*  630 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  631 */       double labelWidth = labelEnclosure.getWidth();
/*  632 */       space.add(labelWidth + tickLabelBandsDimension, edge);
/*      */     } 
/*      */ 
/*      */     
/*  636 */     double tickMarkSpace = 0.0D;
/*  637 */     if (isTickMarksVisible()) {
/*  638 */       tickMarkSpace = getTickMarkOutsideLength();
/*      */     }
/*  640 */     if (this.minorTickMarksVisible) {
/*  641 */       tickMarkSpace = Math.max(tickMarkSpace, this.minorTickMarkOutsideLength);
/*      */     }
/*      */     
/*  644 */     space.add(tickMarkSpace, edge);
/*  645 */     return space;
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
/*      */   public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState) {
/*  667 */     AxisState axisState = new AxisState(cursor);
/*  668 */     if (isAxisLineVisible()) {
/*  669 */       drawAxisLine(g2, cursor, dataArea, edge);
/*      */     }
/*  671 */     if (isTickMarksVisible()) {
/*  672 */       drawTickMarks(g2, axisState, dataArea, edge);
/*      */     }
/*  674 */     if (isTickLabelsVisible()) {
/*  675 */       for (int band = 0; band < this.labelInfo.length; band++) {
/*  676 */         axisState = drawTickLabels(band, g2, axisState, dataArea, edge);
/*      */       }
/*      */     }
/*      */     
/*  680 */     if (getAttributedLabel() != null) {
/*  681 */       axisState = drawAttributedLabel(getAttributedLabel(), g2, plotArea, dataArea, edge, axisState);
/*      */     } else {
/*      */       
/*  684 */       axisState = drawLabel(getLabel(), g2, plotArea, dataArea, edge, axisState);
/*      */     } 
/*      */     
/*  687 */     return axisState;
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
/*      */   protected void drawTickMarks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {
/*  701 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  702 */       drawTickMarksHorizontal(g2, state, dataArea, edge);
/*      */     }
/*  704 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  705 */       drawTickMarksVertical(g2, state, dataArea, edge);
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
/*      */   protected void drawTickMarksHorizontal(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {
/*  720 */     List<NumberTick> ticks = new ArrayList();
/*      */     
/*  722 */     double y0 = state.getCursor();
/*  723 */     double insideLength = getTickMarkInsideLength();
/*  724 */     double outsideLength = getTickMarkOutsideLength();
/*  725 */     RegularTimePeriod t = createInstance(this.majorTickTimePeriodClass, this.first
/*  726 */         .getStart(), getTimeZone(), this.locale);
/*  727 */     long t0 = t.getFirstMillisecond();
/*  728 */     Line2D inside = null;
/*  729 */     Line2D outside = null;
/*  730 */     long firstOnAxis = getFirst().getFirstMillisecond();
/*  731 */     long lastOnAxis = getLast().getLastMillisecond() + 1L;
/*  732 */     while (t0 <= lastOnAxis) {
/*  733 */       ticks.add(new NumberTick(Double.valueOf(t0), "", TextAnchor.CENTER, TextAnchor.CENTER, 0.0D));
/*      */       
/*  735 */       double x0 = valueToJava2D(t0, dataArea, edge);
/*  736 */       if (edge == RectangleEdge.TOP) {
/*  737 */         inside = new Line2D.Double(x0, y0, x0, y0 + insideLength);
/*  738 */         outside = new Line2D.Double(x0, y0, x0, y0 - outsideLength);
/*      */       }
/*  740 */       else if (edge == RectangleEdge.BOTTOM) {
/*  741 */         inside = new Line2D.Double(x0, y0, x0, y0 - insideLength);
/*  742 */         outside = new Line2D.Double(x0, y0, x0, y0 + outsideLength);
/*      */       } 
/*  744 */       if (t0 >= firstOnAxis) {
/*  745 */         g2.setPaint(getTickMarkPaint());
/*  746 */         g2.setStroke(getTickMarkStroke());
/*  747 */         g2.draw(inside);
/*  748 */         g2.draw(outside);
/*      */       } 
/*      */       
/*  751 */       if (this.minorTickMarksVisible) {
/*  752 */         RegularTimePeriod tminor = createInstance(this.minorTickTimePeriodClass, new Date(t0), 
/*      */             
/*  754 */             getTimeZone(), this.locale);
/*  755 */         long tt0 = tminor.getFirstMillisecond();
/*  756 */         while (tt0 < t.getLastMillisecond() && tt0 < lastOnAxis) {
/*      */           
/*  758 */           double xx0 = valueToJava2D(tt0, dataArea, edge);
/*  759 */           if (edge == RectangleEdge.TOP) {
/*  760 */             inside = new Line2D.Double(xx0, y0, xx0, y0 + this.minorTickMarkInsideLength);
/*      */             
/*  762 */             outside = new Line2D.Double(xx0, y0, xx0, y0 - this.minorTickMarkOutsideLength);
/*      */           
/*      */           }
/*  765 */           else if (edge == RectangleEdge.BOTTOM) {
/*  766 */             inside = new Line2D.Double(xx0, y0, xx0, y0 - this.minorTickMarkInsideLength);
/*      */             
/*  768 */             outside = new Line2D.Double(xx0, y0, xx0, y0 + this.minorTickMarkOutsideLength);
/*      */           } 
/*      */           
/*  771 */           if (tt0 >= firstOnAxis) {
/*  772 */             g2.setPaint(this.minorTickMarkPaint);
/*  773 */             g2.setStroke(this.minorTickMarkStroke);
/*  774 */             g2.draw(inside);
/*  775 */             g2.draw(outside);
/*      */           } 
/*  777 */           tminor = tminor.next();
/*  778 */           tminor.peg(this.calendar);
/*  779 */           tt0 = tminor.getFirstMillisecond();
/*      */         } 
/*      */       } 
/*  782 */       t = t.next();
/*  783 */       t.peg(this.calendar);
/*  784 */       t0 = t.getFirstMillisecond();
/*      */     } 
/*  786 */     if (edge == RectangleEdge.TOP) {
/*  787 */       state.cursorUp(Math.max(outsideLength, this.minorTickMarkOutsideLength));
/*      */     
/*      */     }
/*  790 */     else if (edge == RectangleEdge.BOTTOM) {
/*  791 */       state.cursorDown(Math.max(outsideLength, this.minorTickMarkOutsideLength));
/*      */     } 
/*      */     
/*  794 */     state.setTicks(ticks);
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
/*      */   protected void drawTickMarksVertical(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AxisState drawTickLabels(int band, Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {
/*  825 */     double delta1 = 0.0D;
/*  826 */     FontMetrics fm = g2.getFontMetrics(this.labelInfo[band].getLabelFont());
/*  827 */     if (edge == RectangleEdge.BOTTOM) {
/*  828 */       delta1 = this.labelInfo[band].getPadding().calculateTopOutset(fm
/*  829 */           .getHeight());
/*      */     }
/*  831 */     else if (edge == RectangleEdge.TOP) {
/*  832 */       delta1 = this.labelInfo[band].getPadding().calculateBottomOutset(fm
/*  833 */           .getHeight());
/*      */     } 
/*  835 */     state.moveCursor(delta1, edge);
/*  836 */     long axisMin = this.first.getFirstMillisecond();
/*  837 */     long axisMax = this.last.getLastMillisecond();
/*  838 */     g2.setFont(this.labelInfo[band].getLabelFont());
/*  839 */     g2.setPaint(this.labelInfo[band].getLabelPaint());
/*      */ 
/*      */     
/*  842 */     RegularTimePeriod p1 = this.labelInfo[band].createInstance(new Date(axisMin), this.timeZone, this.locale);
/*      */     
/*  844 */     RegularTimePeriod p2 = this.labelInfo[band].createInstance(new Date(axisMax), this.timeZone, this.locale);
/*      */     
/*  846 */     DateFormat df = this.labelInfo[band].getDateFormat();
/*  847 */     df.setTimeZone(this.timeZone);
/*  848 */     String label1 = df.format(new Date(p1.getMiddleMillisecond()));
/*  849 */     String label2 = df.format(new Date(p2.getMiddleMillisecond()));
/*  850 */     Rectangle2D b1 = TextUtilities.getTextBounds(label1, g2, g2
/*  851 */         .getFontMetrics());
/*  852 */     Rectangle2D b2 = TextUtilities.getTextBounds(label2, g2, g2
/*  853 */         .getFontMetrics());
/*  854 */     double w = Math.max(b1.getWidth(), b2.getWidth());
/*  855 */     long ww = Math.round(java2DToValue(dataArea.getX() + w + 5.0D, dataArea, edge));
/*      */     
/*  857 */     if (isInverted()) {
/*  858 */       ww = axisMax - ww;
/*      */     } else {
/*      */       
/*  861 */       ww -= axisMin;
/*      */     } 
/*      */     
/*  864 */     long length = p1.getLastMillisecond() - p1.getFirstMillisecond();
/*  865 */     int periods = (int)(ww / length) + 1;
/*      */     
/*  867 */     RegularTimePeriod p = this.labelInfo[band].createInstance(new Date(axisMin), this.timeZone, this.locale);
/*      */     
/*  869 */     Rectangle2D b = null;
/*  870 */     long lastXX = 0L;
/*  871 */     float y = (float)state.getCursor();
/*  872 */     TextAnchor anchor = TextAnchor.TOP_CENTER;
/*  873 */     float yDelta = (float)b1.getHeight();
/*  874 */     if (edge == RectangleEdge.TOP) {
/*  875 */       anchor = TextAnchor.BOTTOM_CENTER;
/*  876 */       yDelta = -yDelta;
/*      */     } 
/*  878 */     while (p.getFirstMillisecond() <= axisMax) {
/*  879 */       float x = (float)valueToJava2D(p.getMiddleMillisecond(), dataArea, edge);
/*      */       
/*  881 */       String label = df.format(new Date(p.getMiddleMillisecond()));
/*  882 */       long first = p.getFirstMillisecond();
/*  883 */       long last = p.getLastMillisecond();
/*  884 */       if (last > axisMax) {
/*      */ 
/*      */         
/*  887 */         Rectangle2D bb = TextUtilities.getTextBounds(label, g2, g2
/*  888 */             .getFontMetrics());
/*  889 */         if (x + bb.getWidth() / 2.0D > dataArea.getMaxX()) {
/*  890 */           float xstart = (float)valueToJava2D(Math.max(first, axisMin), dataArea, edge);
/*      */           
/*  892 */           if (bb.getWidth() < dataArea.getMaxX() - xstart) {
/*  893 */             x = ((float)dataArea.getMaxX() + xstart) / 2.0F;
/*      */           } else {
/*      */             
/*  896 */             label = null;
/*      */           } 
/*      */         } 
/*      */       } 
/*  900 */       if (first < axisMin) {
/*      */ 
/*      */         
/*  903 */         Rectangle2D bb = TextUtilities.getTextBounds(label, g2, g2
/*  904 */             .getFontMetrics());
/*  905 */         if (x - bb.getWidth() / 2.0D < dataArea.getX()) {
/*  906 */           float xlast = (float)valueToJava2D(Math.min(last, axisMax), dataArea, edge);
/*      */           
/*  908 */           if (bb.getWidth() < xlast - dataArea.getX()) {
/*  909 */             x = (xlast + (float)dataArea.getX()) / 2.0F;
/*      */           } else {
/*      */             
/*  912 */             label = null;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  917 */       if (label != null) {
/*  918 */         g2.setPaint(this.labelInfo[band].getLabelPaint());
/*  919 */         b = TextUtilities.drawAlignedString(label, g2, x, y, anchor);
/*      */       } 
/*  921 */       if (lastXX > 0L && 
/*  922 */         this.labelInfo[band].getDrawDividers()) {
/*  923 */         long nextXX = p.getFirstMillisecond();
/*  924 */         long mid = (lastXX + nextXX) / 2L;
/*  925 */         float mid2d = (float)valueToJava2D(mid, dataArea, edge);
/*  926 */         g2.setStroke(this.labelInfo[band].getDividerStroke());
/*  927 */         g2.setPaint(this.labelInfo[band].getDividerPaint());
/*  928 */         g2.draw(new Line2D.Float(mid2d, y, mid2d, y + yDelta));
/*      */       } 
/*      */       
/*  931 */       lastXX = last;
/*  932 */       for (int i = 0; i < periods; i++) {
/*  933 */         p = p.next();
/*      */       }
/*  935 */       p.peg(this.calendar);
/*      */     } 
/*  937 */     double used = 0.0D;
/*  938 */     if (b != null) {
/*  939 */       used = b.getHeight();
/*      */       
/*  941 */       if (edge == RectangleEdge.BOTTOM) {
/*  942 */         used += this.labelInfo[band].getPadding().calculateBottomOutset(fm
/*  943 */             .getHeight());
/*      */       }
/*  945 */       else if (edge == RectangleEdge.TOP) {
/*  946 */         used += this.labelInfo[band].getPadding().calculateTopOutset(fm
/*  947 */             .getHeight());
/*      */       } 
/*      */     } 
/*  950 */     state.moveCursor(used, edge);
/*  951 */     return state;
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
/*      */   public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {
/*  968 */     return Collections.EMPTY_LIST;
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
/*      */   public double valueToJava2D(double value, Rectangle2D area, RectangleEdge edge) {
/*  987 */     double result = Double.NaN;
/*  988 */     double axisMin = this.first.getFirstMillisecond();
/*  989 */     double axisMax = this.last.getLastMillisecond();
/*  990 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  991 */       double minX = area.getX();
/*  992 */       double maxX = area.getMaxX();
/*  993 */       if (isInverted()) {
/*  994 */         result = maxX + (value - axisMin) / (axisMax - axisMin) * (minX - maxX);
/*      */       }
/*      */       else {
/*      */         
/*  998 */         result = minX + (value - axisMin) / (axisMax - axisMin) * (maxX - minX);
/*      */       }
/*      */     
/*      */     }
/* 1002 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/* 1003 */       double minY = area.getMinY();
/* 1004 */       double maxY = area.getMaxY();
/* 1005 */       if (isInverted()) {
/* 1006 */         result = minY + (value - axisMin) / (axisMax - axisMin) * (maxY - minY);
/*      */       }
/*      */       else {
/*      */         
/* 1010 */         result = maxY - (value - axisMin) / (axisMax - axisMin) * (maxY - minY);
/*      */       } 
/*      */     } 
/*      */     
/* 1014 */     return result;
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
/*      */   public double java2DToValue(double java2DValue, Rectangle2D area, RectangleEdge edge) {
/* 1033 */     double result, min = 0.0D;
/* 1034 */     double max = 0.0D;
/* 1035 */     double axisMin = this.first.getFirstMillisecond();
/* 1036 */     double axisMax = this.last.getLastMillisecond();
/* 1037 */     if (RectangleEdge.isTopOrBottom(edge)) {
/* 1038 */       min = area.getX();
/* 1039 */       max = area.getMaxX();
/*      */     }
/* 1041 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/* 1042 */       min = area.getMaxY();
/* 1043 */       max = area.getY();
/*      */     } 
/* 1045 */     if (isInverted()) {
/* 1046 */       result = axisMax - (java2DValue - min) / (max - min) * (axisMax - axisMin);
/*      */     }
/*      */     else {
/*      */       
/* 1050 */       result = axisMin + (java2DValue - min) / (max - min) * (axisMax - axisMin);
/*      */     } 
/*      */     
/* 1053 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void autoAdjustRange() {
/* 1062 */     Plot plot = getPlot();
/* 1063 */     if (plot == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1067 */     if (plot instanceof ValueAxisPlot) {
/* 1068 */       ValueAxisPlot vap = (ValueAxisPlot)plot;
/*      */       
/* 1070 */       Range r = vap.getDataRange(this);
/* 1071 */       if (r == null) {
/* 1072 */         r = getDefaultAutoRange();
/*      */       }
/*      */       
/* 1075 */       long upper = Math.round(r.getUpperBound());
/* 1076 */       long lower = Math.round(r.getLowerBound());
/* 1077 */       this.first = createInstance(this.autoRangeTimePeriodClass, new Date(lower), this.timeZone, this.locale);
/*      */       
/* 1079 */       this.last = createInstance(this.autoRangeTimePeriodClass, new Date(upper), this.timeZone, this.locale);
/*      */       
/* 1081 */       setRange(r, false, false);
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
/*      */   public boolean equals(Object obj) {
/* 1095 */     if (obj == this) {
/* 1096 */       return true;
/*      */     }
/* 1098 */     if (!(obj instanceof PeriodAxis)) {
/* 1099 */       return false;
/*      */     }
/* 1101 */     PeriodAxis that = (PeriodAxis)obj;
/* 1102 */     if (!this.first.equals(that.first)) {
/* 1103 */       return false;
/*      */     }
/* 1105 */     if (!this.last.equals(that.last)) {
/* 1106 */       return false;
/*      */     }
/* 1108 */     if (!this.timeZone.equals(that.timeZone)) {
/* 1109 */       return false;
/*      */     }
/* 1111 */     if (!this.locale.equals(that.locale)) {
/* 1112 */       return false;
/*      */     }
/* 1114 */     if (!this.autoRangeTimePeriodClass.equals(that.autoRangeTimePeriodClass))
/*      */     {
/* 1116 */       return false;
/*      */     }
/* 1118 */     if (isMinorTickMarksVisible() != that.isMinorTickMarksVisible()) {
/* 1119 */       return false;
/*      */     }
/* 1121 */     if (!this.majorTickTimePeriodClass.equals(that.majorTickTimePeriodClass))
/*      */     {
/* 1123 */       return false;
/*      */     }
/* 1125 */     if (!this.minorTickTimePeriodClass.equals(that.minorTickTimePeriodClass))
/*      */     {
/* 1127 */       return false;
/*      */     }
/* 1129 */     if (!this.minorTickMarkPaint.equals(that.minorTickMarkPaint)) {
/* 1130 */       return false;
/*      */     }
/* 1132 */     if (!this.minorTickMarkStroke.equals(that.minorTickMarkStroke)) {
/* 1133 */       return false;
/*      */     }
/* 1135 */     if (!Arrays.equals((Object[])this.labelInfo, (Object[])that.labelInfo)) {
/* 1136 */       return false;
/*      */     }
/* 1138 */     return super.equals(obj);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1148 */     return super.hashCode();
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
/* 1161 */     PeriodAxis clone = (PeriodAxis)super.clone();
/* 1162 */     clone.timeZone = (TimeZone)this.timeZone.clone();
/* 1163 */     clone.labelInfo = (PeriodAxisLabelInfo[])this.labelInfo.clone();
/* 1164 */     return clone;
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
/*      */   private RegularTimePeriod createInstance(Class periodClass, Date millisecond, TimeZone zone, Locale locale) {
/* 1181 */     RegularTimePeriod result = null;
/*      */     try {
/* 1183 */       Constructor<RegularTimePeriod> c = periodClass.getDeclaredConstructor(new Class[] { Date.class, TimeZone.class, Locale.class });
/*      */       
/* 1185 */       result = c.newInstance(new Object[] { millisecond, zone, locale });
/*      */     
/*      */     }
/* 1188 */     catch (Exception e) {
/*      */       try {
/* 1190 */         Constructor<RegularTimePeriod> c = periodClass.getDeclaredConstructor(new Class[] { Date.class });
/*      */         
/* 1192 */         result = c.newInstance(new Object[] { millisecond });
/*      */       
/*      */       }
/* 1195 */       catch (Exception e2) {}
/*      */     } 
/*      */ 
/*      */     
/* 1199 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 1210 */     stream.defaultWriteObject();
/* 1211 */     SerialUtilities.writeStroke(this.minorTickMarkStroke, stream);
/* 1212 */     SerialUtilities.writePaint(this.minorTickMarkPaint, stream);
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
/*      */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 1225 */     stream.defaultReadObject();
/* 1226 */     this.minorTickMarkStroke = SerialUtilities.readStroke(stream);
/* 1227 */     this.minorTickMarkPaint = SerialUtilities.readPaint(stream);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/PeriodAxis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */