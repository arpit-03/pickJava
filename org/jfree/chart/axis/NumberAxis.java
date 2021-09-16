/*      */ package org.jfree.chart.axis;
/*      */ 
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.LineMetrics;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.Serializable;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import org.jfree.chart.event.AxisChangeEvent;
/*      */ import org.jfree.chart.plot.Plot;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.plot.ValueAxisPlot;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.RangeType;
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
/*      */ public class NumberAxis
/*      */   extends ValueAxis
/*      */   implements Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 2805933088476185789L;
/*      */   public static final boolean DEFAULT_AUTO_RANGE_INCLUDES_ZERO = true;
/*      */   public static final boolean DEFAULT_AUTO_RANGE_STICKY_ZERO = true;
/*  154 */   public static final NumberTickUnit DEFAULT_TICK_UNIT = new NumberTickUnit(1.0D, new DecimalFormat("0"));
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean DEFAULT_VERTICAL_TICK_LABELS = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RangeType rangeType;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoRangeIncludesZero;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoRangeStickyZero;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private NumberTickUnit tickUnit;
/*      */ 
/*      */ 
/*      */   
/*      */   private NumberFormat numberFormatOverride;
/*      */ 
/*      */ 
/*      */   
/*      */   private MarkerAxisBand markerBand;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NumberAxis() {
/*  193 */     this((String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NumberAxis(String label) {
/*  202 */     super(label, createStandardTickUnits());
/*  203 */     this.rangeType = RangeType.FULL;
/*  204 */     this.autoRangeIncludesZero = true;
/*  205 */     this.autoRangeStickyZero = true;
/*  206 */     this.tickUnit = DEFAULT_TICK_UNIT;
/*  207 */     this.numberFormatOverride = null;
/*  208 */     this.markerBand = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RangeType getRangeType() {
/*  219 */     return this.rangeType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeType(RangeType rangeType) {
/*  230 */     ParamChecks.nullNotPermitted(rangeType, "rangeType");
/*  231 */     this.rangeType = rangeType;
/*  232 */     notifyListeners(new AxisChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAutoRangeIncludesZero() {
/*  242 */     return this.autoRangeIncludesZero;
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
/*      */   public void setAutoRangeIncludesZero(boolean flag) {
/*  259 */     if (this.autoRangeIncludesZero != flag) {
/*  260 */       this.autoRangeIncludesZero = flag;
/*  261 */       if (isAutoRange()) {
/*  262 */         autoAdjustRange();
/*      */       }
/*  264 */       notifyListeners(new AxisChangeEvent(this));
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
/*      */   public boolean getAutoRangeStickyZero() {
/*  277 */     return this.autoRangeStickyZero;
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
/*      */   public void setAutoRangeStickyZero(boolean flag) {
/*  289 */     if (this.autoRangeStickyZero != flag) {
/*  290 */       this.autoRangeStickyZero = flag;
/*  291 */       if (isAutoRange()) {
/*  292 */         autoAdjustRange();
/*      */       }
/*  294 */       notifyListeners(new AxisChangeEvent(this));
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
/*      */   public NumberTickUnit getTickUnit() {
/*  312 */     return this.tickUnit;
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
/*      */   public void setTickUnit(NumberTickUnit unit) {
/*  329 */     setTickUnit(unit, true, true);
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
/*      */   public void setTickUnit(NumberTickUnit unit, boolean notify, boolean turnOffAutoSelect) {
/*  346 */     ParamChecks.nullNotPermitted(unit, "unit");
/*  347 */     this.tickUnit = unit;
/*  348 */     if (turnOffAutoSelect) {
/*  349 */       setAutoTickUnitSelection(false, false);
/*      */     }
/*  351 */     if (notify) {
/*  352 */       notifyListeners(new AxisChangeEvent(this));
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
/*      */   public NumberFormat getNumberFormatOverride() {
/*  366 */     return this.numberFormatOverride;
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
/*      */   public void setNumberFormatOverride(NumberFormat formatter) {
/*  378 */     this.numberFormatOverride = formatter;
/*  379 */     notifyListeners(new AxisChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MarkerAxisBand getMarkerBand() {
/*  390 */     return this.markerBand;
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
/*      */   public void setMarkerBand(MarkerAxisBand band) {
/*  404 */     this.markerBand = band;
/*  405 */     notifyListeners(new AxisChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void configure() {
/*  414 */     if (isAutoRange()) {
/*  415 */       autoAdjustRange();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void autoAdjustRange() {
/*  425 */     Plot plot = getPlot();
/*  426 */     if (plot == null) {
/*      */       return;
/*      */     }
/*      */     
/*  430 */     if (plot instanceof ValueAxisPlot) {
/*  431 */       ValueAxisPlot vap = (ValueAxisPlot)plot;
/*      */       
/*  433 */       Range r = vap.getDataRange(this);
/*  434 */       if (r == null) {
/*  435 */         r = getDefaultAutoRange();
/*      */       }
/*      */       
/*  438 */       double upper = r.getUpperBound();
/*  439 */       double lower = r.getLowerBound();
/*  440 */       if (this.rangeType == RangeType.POSITIVE) {
/*  441 */         lower = Math.max(0.0D, lower);
/*  442 */         upper = Math.max(0.0D, upper);
/*      */       }
/*  444 */       else if (this.rangeType == RangeType.NEGATIVE) {
/*  445 */         lower = Math.min(0.0D, lower);
/*  446 */         upper = Math.min(0.0D, upper);
/*      */       } 
/*      */       
/*  449 */       if (getAutoRangeIncludesZero()) {
/*  450 */         lower = Math.min(lower, 0.0D);
/*  451 */         upper = Math.max(upper, 0.0D);
/*      */       } 
/*  453 */       double range = upper - lower;
/*      */ 
/*      */       
/*  456 */       double fixedAutoRange = getFixedAutoRange();
/*  457 */       if (fixedAutoRange > 0.0D) {
/*  458 */         lower = upper - fixedAutoRange;
/*      */       }
/*      */       else {
/*      */         
/*  462 */         double minRange = getAutoRangeMinimumSize();
/*  463 */         if (range < minRange) {
/*  464 */           double expand = (minRange - range) / 2.0D;
/*  465 */           upper += expand;
/*  466 */           lower -= expand;
/*  467 */           if (lower == upper) {
/*  468 */             double adjust = Math.abs(lower) / 10.0D;
/*  469 */             lower -= adjust;
/*  470 */             upper += adjust;
/*      */           } 
/*  472 */           if (this.rangeType == RangeType.POSITIVE) {
/*  473 */             if (lower < 0.0D) {
/*  474 */               upper -= lower;
/*  475 */               lower = 0.0D;
/*      */             }
/*      */           
/*  478 */           } else if (this.rangeType == RangeType.NEGATIVE && 
/*  479 */             upper > 0.0D) {
/*  480 */             lower -= upper;
/*  481 */             upper = 0.0D;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  486 */         if (getAutoRangeStickyZero()) {
/*  487 */           if (upper <= 0.0D) {
/*  488 */             upper = Math.min(0.0D, upper + getUpperMargin() * range);
/*      */           } else {
/*      */             
/*  491 */             upper += getUpperMargin() * range;
/*      */           } 
/*  493 */           if (lower >= 0.0D) {
/*  494 */             lower = Math.max(0.0D, lower - getLowerMargin() * range);
/*      */           } else {
/*      */             
/*  497 */             lower -= getLowerMargin() * range;
/*      */           } 
/*      */         } else {
/*      */           
/*  501 */           upper += getUpperMargin() * range;
/*  502 */           lower -= getLowerMargin() * range;
/*      */         } 
/*      */       } 
/*      */       
/*  506 */       setRange(new Range(lower, upper), false, false);
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
/*      */   
/*      */   public double valueToJava2D(double value, Rectangle2D area, RectangleEdge edge) {
/*  529 */     Range range = getRange();
/*  530 */     double axisMin = range.getLowerBound();
/*  531 */     double axisMax = range.getUpperBound();
/*      */     
/*  533 */     double min = 0.0D;
/*  534 */     double max = 0.0D;
/*  535 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  536 */       min = area.getX();
/*  537 */       max = area.getMaxX();
/*      */     }
/*  539 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  540 */       max = area.getMinY();
/*  541 */       min = area.getMaxY();
/*      */     } 
/*  543 */     if (isInverted()) {
/*  544 */       return max - (value - axisMin) / (axisMax - axisMin) * (max - min);
/*      */     }
/*      */ 
/*      */     
/*  548 */     return min + (value - axisMin) / (axisMax - axisMin) * (max - min);
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
/*      */   public double java2DToValue(double java2DValue, Rectangle2D area, RectangleEdge edge) {
/*  570 */     Range range = getRange();
/*  571 */     double axisMin = range.getLowerBound();
/*  572 */     double axisMax = range.getUpperBound();
/*      */     
/*  574 */     double min = 0.0D;
/*  575 */     double max = 0.0D;
/*  576 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  577 */       min = area.getX();
/*  578 */       max = area.getMaxX();
/*      */     }
/*  580 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  581 */       min = area.getMaxY();
/*  582 */       max = area.getY();
/*      */     } 
/*  584 */     if (isInverted()) {
/*  585 */       return axisMax - (java2DValue - min) / (max - min) * (axisMax - axisMin);
/*      */     }
/*      */ 
/*      */     
/*  589 */     return axisMin + (java2DValue - min) / (max - min) * (axisMax - axisMin);
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
/*      */   protected double calculateLowestVisibleTickValue() {
/*  603 */     double unit = getTickUnit().getSize();
/*  604 */     double index = Math.ceil(getRange().getLowerBound() / unit);
/*  605 */     return index * unit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double calculateHighestVisibleTickValue() {
/*  616 */     double unit = getTickUnit().getSize();
/*  617 */     double index = Math.floor(getRange().getUpperBound() / unit);
/*  618 */     return index * unit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int calculateVisibleTickCount() {
/*  627 */     double unit = getTickUnit().getSize();
/*  628 */     Range range = getRange();
/*      */     
/*  630 */     return (int)(Math.floor(range.getUpperBound() / unit) - Math.ceil(range.getLowerBound() / unit) + 1.0D);
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
/*      */   
/*      */   public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState) {
/*  656 */     if (!isVisible()) {
/*  657 */       AxisState axisState = new AxisState(cursor);
/*      */ 
/*      */       
/*  660 */       List ticks = refreshTicks(g2, axisState, dataArea, edge);
/*  661 */       axisState.setTicks(ticks);
/*  662 */       return axisState;
/*      */     } 
/*      */ 
/*      */     
/*  666 */     AxisState state = drawTickMarksAndLabels(g2, cursor, plotArea, dataArea, edge);
/*      */     
/*  668 */     if (getAttributedLabel() != null) {
/*  669 */       state = drawAttributedLabel(getAttributedLabel(), g2, plotArea, dataArea, edge, state);
/*      */     }
/*      */     else {
/*      */       
/*  673 */       state = drawLabel(getLabel(), g2, plotArea, dataArea, edge, state);
/*      */     } 
/*  675 */     createAndAddEntity(cursor, state, dataArea, edge, plotState);
/*  676 */     return state;
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
/*      */   public static TickUnitSource createStandardTickUnits() {
/*  693 */     return new NumberTickUnitSource();
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
/*      */   public static TickUnitSource createIntegerTickUnits() {
/*  705 */     return new NumberTickUnitSource(true);
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
/*      */   public static TickUnitSource createStandardTickUnits(Locale locale) {
/*  724 */     NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
/*  725 */     return new NumberTickUnitSource(false, numberFormat);
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
/*      */   public static TickUnitSource createIntegerTickUnits(Locale locale) {
/*  739 */     NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
/*  740 */     return new NumberTickUnitSource(true, numberFormat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double estimateMaximumTickLabelHeight(Graphics2D g2) {
/*  751 */     RectangleInsets tickLabelInsets = getTickLabelInsets();
/*  752 */     double result = tickLabelInsets.getTop() + tickLabelInsets.getBottom();
/*      */     
/*  754 */     Font tickLabelFont = getTickLabelFont();
/*  755 */     FontRenderContext frc = g2.getFontRenderContext();
/*  756 */     result += tickLabelFont.getLineMetrics("123", frc).getHeight();
/*  757 */     return result;
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
/*      */   protected double estimateMaximumTickLabelWidth(Graphics2D g2, TickUnit unit) {
/*  776 */     RectangleInsets tickLabelInsets = getTickLabelInsets();
/*  777 */     double result = tickLabelInsets.getLeft() + tickLabelInsets.getRight();
/*      */     
/*  779 */     if (isVerticalTickLabels()) {
/*      */ 
/*      */       
/*  782 */       FontRenderContext frc = g2.getFontRenderContext();
/*  783 */       LineMetrics lm = getTickLabelFont().getLineMetrics("0", frc);
/*  784 */       result += lm.getHeight();
/*      */     } else {
/*      */       String lowerStr, upperStr;
/*      */       
/*  788 */       FontMetrics fm = g2.getFontMetrics(getTickLabelFont());
/*  789 */       Range range = getRange();
/*  790 */       double lower = range.getLowerBound();
/*  791 */       double upper = range.getUpperBound();
/*      */       
/*  793 */       NumberFormat formatter = getNumberFormatOverride();
/*  794 */       if (formatter != null) {
/*  795 */         lowerStr = formatter.format(lower);
/*  796 */         upperStr = formatter.format(upper);
/*      */       } else {
/*      */         
/*  799 */         lowerStr = unit.valueToString(lower);
/*  800 */         upperStr = unit.valueToString(upper);
/*      */       } 
/*  802 */       double w1 = fm.stringWidth(lowerStr);
/*  803 */       double w2 = fm.stringWidth(upperStr);
/*  804 */       result += Math.max(w1, w2);
/*      */     } 
/*      */     
/*  807 */     return result;
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
/*  823 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  824 */       selectHorizontalAutoTickUnit(g2, dataArea, edge);
/*      */     }
/*  826 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  827 */       selectVerticalAutoTickUnit(g2, dataArea, edge);
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
/*  844 */     double tickLabelWidth = estimateMaximumTickLabelWidth(g2, 
/*  845 */         getTickUnit());
/*      */ 
/*      */     
/*  848 */     TickUnitSource tickUnits = getStandardTickUnits();
/*  849 */     TickUnit unit1 = tickUnits.getCeilingTickUnit(getTickUnit());
/*  850 */     double unit1Width = lengthToJava2D(unit1.getSize(), dataArea, edge);
/*      */ 
/*      */     
/*  853 */     double guess = tickLabelWidth / unit1Width * unit1.getSize();
/*      */     
/*  855 */     NumberTickUnit unit2 = (NumberTickUnit)tickUnits.getCeilingTickUnit(guess);
/*      */     
/*  857 */     double unit2Width = lengthToJava2D(unit2.getSize(), dataArea, edge);
/*      */     
/*  859 */     tickLabelWidth = estimateMaximumTickLabelWidth(g2, unit2);
/*  860 */     if (tickLabelWidth > unit2Width) {
/*  861 */       unit2 = (NumberTickUnit)tickUnits.getLargerTickUnit(unit2);
/*      */     }
/*      */     
/*  864 */     setTickUnit(unit2, false, false);
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
/*      */   protected void selectVerticalAutoTickUnit(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/*  880 */     double tickLabelHeight = estimateMaximumTickLabelHeight(g2);
/*      */ 
/*      */     
/*  883 */     TickUnitSource tickUnits = getStandardTickUnits();
/*  884 */     TickUnit unit1 = tickUnits.getCeilingTickUnit(getTickUnit());
/*  885 */     double unitHeight = lengthToJava2D(unit1.getSize(), dataArea, edge);
/*  886 */     double guess = unit1.getSize();
/*  887 */     if (unitHeight > 0.0D)
/*      */     {
/*  889 */       guess = tickLabelHeight / unitHeight * unit1.getSize();
/*      */     }
/*  891 */     NumberTickUnit unit2 = (NumberTickUnit)tickUnits.getCeilingTickUnit(guess);
/*      */     
/*  893 */     double unit2Height = lengthToJava2D(unit2.getSize(), dataArea, edge);
/*      */     
/*  895 */     tickLabelHeight = estimateMaximumTickLabelHeight(g2);
/*  896 */     if (tickLabelHeight > unit2Height) {
/*  897 */       unit2 = (NumberTickUnit)tickUnits.getLargerTickUnit(unit2);
/*      */     }
/*      */     
/*  900 */     setTickUnit(unit2, false, false);
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
/*  919 */     List result = new ArrayList();
/*  920 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  921 */       result = refreshTicksHorizontal(g2, dataArea, edge);
/*      */     }
/*  923 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  924 */       result = refreshTicksVertical(g2, dataArea, edge);
/*      */     } 
/*  926 */     return result;
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
/*      */   protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/*  943 */     List<NumberTick> result = new ArrayList();
/*      */     
/*  945 */     Font tickLabelFont = getTickLabelFont();
/*  946 */     g2.setFont(tickLabelFont);
/*      */     
/*  948 */     if (isAutoTickUnitSelection()) {
/*  949 */       selectAutoTickUnit(g2, dataArea, edge);
/*      */     }
/*      */     
/*  952 */     TickUnit tu = getTickUnit();
/*  953 */     double size = tu.getSize();
/*  954 */     int count = calculateVisibleTickCount();
/*  955 */     double lowestTickValue = calculateLowestVisibleTickValue();
/*      */     
/*  957 */     if (count <= 500) {
/*  958 */       int minorTickSpaces = getMinorTickCount();
/*  959 */       if (minorTickSpaces <= 0) {
/*  960 */         minorTickSpaces = tu.getMinorTickCount();
/*      */       }
/*  962 */       for (int minorTick = 1; minorTick < minorTickSpaces; minorTick++) {
/*  963 */         double minorTickValue = lowestTickValue - size * minorTick / minorTickSpaces;
/*      */         
/*  965 */         if (getRange().contains(minorTickValue)) {
/*  966 */           result.add(new NumberTick(TickType.MINOR, minorTickValue, "", TextAnchor.TOP_CENTER, TextAnchor.CENTER, 0.0D));
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  971 */       for (int i = 0; i < count; i++) {
/*  972 */         String tickLabel; TextAnchor anchor, rotationAnchor; double currentTickValue = lowestTickValue + i * size;
/*      */         
/*  974 */         NumberFormat formatter = getNumberFormatOverride();
/*  975 */         if (formatter != null) {
/*  976 */           tickLabel = formatter.format(currentTickValue);
/*      */         } else {
/*      */           
/*  979 */           tickLabel = getTickUnit().valueToString(currentTickValue);
/*      */         } 
/*      */         
/*  982 */         double angle = 0.0D;
/*  983 */         if (isVerticalTickLabels()) {
/*  984 */           anchor = TextAnchor.CENTER_RIGHT;
/*  985 */           rotationAnchor = TextAnchor.CENTER_RIGHT;
/*  986 */           if (edge == RectangleEdge.TOP) {
/*  987 */             angle = 1.5707963267948966D;
/*      */           } else {
/*      */             
/*  990 */             angle = -1.5707963267948966D;
/*      */           }
/*      */         
/*      */         }
/*  994 */         else if (edge == RectangleEdge.TOP) {
/*  995 */           anchor = TextAnchor.BOTTOM_CENTER;
/*  996 */           rotationAnchor = TextAnchor.BOTTOM_CENTER;
/*      */         } else {
/*      */           
/*  999 */           anchor = TextAnchor.TOP_CENTER;
/* 1000 */           rotationAnchor = TextAnchor.TOP_CENTER;
/*      */         } 
/*      */ 
/*      */         
/* 1004 */         Tick tick = new NumberTick(new Double(currentTickValue), tickLabel, anchor, rotationAnchor, angle);
/*      */         
/* 1006 */         result.add(tick);
/* 1007 */         double nextTickValue = lowestTickValue + (i + 1) * size;
/* 1008 */         for (int j = 1; j < minorTickSpaces; 
/* 1009 */           j++) {
/* 1010 */           double minorTickValue = currentTickValue + (nextTickValue - currentTickValue) * j / minorTickSpaces;
/*      */ 
/*      */           
/* 1013 */           if (getRange().contains(minorTickValue)) {
/* 1014 */             result.add(new NumberTick(TickType.MINOR, minorTickValue, "", TextAnchor.TOP_CENTER, TextAnchor.CENTER, 0.0D));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1021 */     return result;
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
/*      */   protected List refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/* 1038 */     List<NumberTick> result = new ArrayList();
/* 1039 */     result.clear();
/*      */     
/* 1041 */     Font tickLabelFont = getTickLabelFont();
/* 1042 */     g2.setFont(tickLabelFont);
/* 1043 */     if (isAutoTickUnitSelection()) {
/* 1044 */       selectAutoTickUnit(g2, dataArea, edge);
/*      */     }
/*      */     
/* 1047 */     TickUnit tu = getTickUnit();
/* 1048 */     double size = tu.getSize();
/* 1049 */     int count = calculateVisibleTickCount();
/* 1050 */     double lowestTickValue = calculateLowestVisibleTickValue();
/*      */     
/* 1052 */     if (count <= 500) {
/* 1053 */       int minorTickSpaces = getMinorTickCount();
/* 1054 */       if (minorTickSpaces <= 0) {
/* 1055 */         minorTickSpaces = tu.getMinorTickCount();
/*      */       }
/* 1057 */       for (int minorTick = 1; minorTick < minorTickSpaces; minorTick++) {
/* 1058 */         double minorTickValue = lowestTickValue - size * minorTick / minorTickSpaces;
/*      */         
/* 1060 */         if (getRange().contains(minorTickValue)) {
/* 1061 */           result.add(new NumberTick(TickType.MINOR, minorTickValue, "", TextAnchor.TOP_CENTER, TextAnchor.CENTER, 0.0D));
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1067 */       for (int i = 0; i < count; i++) {
/* 1068 */         String tickLabel; TextAnchor anchor, rotationAnchor; double currentTickValue = lowestTickValue + i * size;
/*      */         
/* 1070 */         NumberFormat formatter = getNumberFormatOverride();
/* 1071 */         if (formatter != null) {
/* 1072 */           tickLabel = formatter.format(currentTickValue);
/*      */         } else {
/*      */           
/* 1075 */           tickLabel = getTickUnit().valueToString(currentTickValue);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1080 */         double angle = 0.0D;
/* 1081 */         if (isVerticalTickLabels()) {
/* 1082 */           if (edge == RectangleEdge.LEFT) {
/* 1083 */             anchor = TextAnchor.BOTTOM_CENTER;
/* 1084 */             rotationAnchor = TextAnchor.BOTTOM_CENTER;
/* 1085 */             angle = -1.5707963267948966D;
/*      */           } else {
/*      */             
/* 1088 */             anchor = TextAnchor.BOTTOM_CENTER;
/* 1089 */             rotationAnchor = TextAnchor.BOTTOM_CENTER;
/* 1090 */             angle = 1.5707963267948966D;
/*      */           }
/*      */         
/*      */         }
/* 1094 */         else if (edge == RectangleEdge.LEFT) {
/* 1095 */           anchor = TextAnchor.CENTER_RIGHT;
/* 1096 */           rotationAnchor = TextAnchor.CENTER_RIGHT;
/*      */         } else {
/*      */           
/* 1099 */           anchor = TextAnchor.CENTER_LEFT;
/* 1100 */           rotationAnchor = TextAnchor.CENTER_LEFT;
/*      */         } 
/*      */ 
/*      */         
/* 1104 */         Tick tick = new NumberTick(new Double(currentTickValue), tickLabel, anchor, rotationAnchor, angle);
/*      */         
/* 1106 */         result.add(tick);
/*      */         
/* 1108 */         double nextTickValue = lowestTickValue + (i + 1) * size;
/* 1109 */         for (int j = 1; j < minorTickSpaces; 
/* 1110 */           j++) {
/* 1111 */           double minorTickValue = currentTickValue + (nextTickValue - currentTickValue) * j / minorTickSpaces;
/*      */ 
/*      */           
/* 1114 */           if (getRange().contains(minorTickValue)) {
/* 1115 */             result.add(new NumberTick(TickType.MINOR, minorTickValue, "", TextAnchor.TOP_CENTER, TextAnchor.CENTER, 0.0D));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
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
/*      */   public Object clone() throws CloneNotSupportedException {
/* 1136 */     NumberAxis clone = (NumberAxis)super.clone();
/* 1137 */     if (this.numberFormatOverride != null) {
/* 1138 */       clone
/* 1139 */         .numberFormatOverride = (NumberFormat)this.numberFormatOverride.clone();
/*      */     }
/* 1141 */     return clone;
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
/* 1153 */     if (obj == this) {
/* 1154 */       return true;
/*      */     }
/* 1156 */     if (!(obj instanceof NumberAxis)) {
/* 1157 */       return false;
/*      */     }
/* 1159 */     NumberAxis that = (NumberAxis)obj;
/* 1160 */     if (this.autoRangeIncludesZero != that.autoRangeIncludesZero) {
/* 1161 */       return false;
/*      */     }
/* 1163 */     if (this.autoRangeStickyZero != that.autoRangeStickyZero) {
/* 1164 */       return false;
/*      */     }
/* 1166 */     if (!ObjectUtilities.equal(this.tickUnit, that.tickUnit)) {
/* 1167 */       return false;
/*      */     }
/* 1169 */     if (!ObjectUtilities.equal(this.numberFormatOverride, that.numberFormatOverride))
/*      */     {
/* 1171 */       return false;
/*      */     }
/* 1173 */     if (!this.rangeType.equals(that.rangeType)) {
/* 1174 */       return false;
/*      */     }
/* 1176 */     return super.equals(obj);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1186 */     return super.hashCode();
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/NumberAxis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */