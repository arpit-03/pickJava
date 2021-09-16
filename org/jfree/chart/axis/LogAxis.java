/*      */ package org.jfree.chart.axis;
/*      */ 
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.LineMetrics;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.text.AttributedString;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.Format;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import org.jfree.chart.plot.Plot;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.plot.ValueAxisPlot;
/*      */ import org.jfree.chart.util.AttrStringUtils;
/*      */ import org.jfree.chart.util.LogFormat;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.Range;
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
/*      */ public class LogAxis
/*      */   extends ValueAxis
/*      */ {
/*  103 */   private double base = 10.0D;
/*      */ 
/*      */   
/*  106 */   private double baseLog = Math.log(10.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   private String baseSymbol = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  118 */   private Format baseFormatter = new DecimalFormat("0");
/*      */ 
/*      */   
/*  121 */   private double smallestValue = 1.0E-100D;
/*      */ 
/*      */ 
/*      */   
/*      */   private NumberTickUnit tickUnit;
/*      */ 
/*      */   
/*      */   private NumberFormat numberFormatOverride;
/*      */ 
/*      */ 
/*      */   
/*      */   public LogAxis() {
/*  133 */     this((String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LogAxis(String label) {
/*  142 */     super(label, new NumberTickUnitSource());
/*  143 */     setDefaultAutoRange(new Range(0.01D, 1.0D));
/*  144 */     this.tickUnit = new NumberTickUnit(1.0D, new DecimalFormat("0.#"), 10);
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
/*      */   public double getBase() {
/*  156 */     return this.base;
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
/*      */   public void setBase(double base) {
/*  168 */     if (base <= 1.0D) {
/*  169 */       throw new IllegalArgumentException("Requires 'base' > 1.0.");
/*      */     }
/*  171 */     this.base = base;
/*  172 */     this.baseLog = Math.log(base);
/*  173 */     fireChangeEvent();
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
/*      */   public String getBaseSymbol() {
/*  186 */     return this.baseSymbol;
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
/*      */   public void setBaseSymbol(String symbol) {
/*  198 */     this.baseSymbol = symbol;
/*  199 */     fireChangeEvent();
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
/*      */   public Format getBaseFormatter() {
/*  212 */     return this.baseFormatter;
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
/*      */   public void setBaseFormatter(Format formatter) {
/*  225 */     ParamChecks.nullNotPermitted(formatter, "formatter");
/*  226 */     this.baseFormatter = formatter;
/*  227 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getSmallestValue() {
/*  238 */     return this.smallestValue;
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
/*      */   public void setSmallestValue(double value) {
/*  250 */     if (value <= 0.0D) {
/*  251 */       throw new IllegalArgumentException("Requires 'value' > 0.0.");
/*      */     }
/*  253 */     this.smallestValue = value;
/*  254 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NumberTickUnit getTickUnit() {
/*  265 */     return this.tickUnit;
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
/*      */   public void setTickUnit(NumberTickUnit unit) {
/*  281 */     setTickUnit(unit, true, true);
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
/*      */   public void setTickUnit(NumberTickUnit unit, boolean notify, boolean turnOffAutoSelect) {
/*  299 */     ParamChecks.nullNotPermitted(unit, "unit");
/*  300 */     this.tickUnit = unit;
/*  301 */     if (turnOffAutoSelect) {
/*  302 */       setAutoTickUnitSelection(false, false);
/*      */     }
/*  304 */     if (notify) {
/*  305 */       fireChangeEvent();
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
/*      */   public NumberFormat getNumberFormatOverride() {
/*  318 */     return this.numberFormatOverride;
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
/*      */   public void setNumberFormatOverride(NumberFormat formatter) {
/*  331 */     this.numberFormatOverride = formatter;
/*  332 */     fireChangeEvent();
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
/*      */   public double calculateLog(double value) {
/*  346 */     return Math.log(value) / this.baseLog;
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
/*      */   public double calculateValue(double log) {
/*  360 */     return Math.pow(this.base, log);
/*      */   }
/*      */   
/*      */   private double calculateValueNoINF(double log) {
/*  364 */     double result = calculateValue(log);
/*  365 */     if (Double.isInfinite(result)) {
/*  366 */       result = Double.MAX_VALUE;
/*      */     }
/*  368 */     if (result <= 0.0D) {
/*  369 */       result = Double.MIN_VALUE;
/*      */     }
/*  371 */     return result;
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
/*      */     double log;
/*  390 */     Range range = getRange();
/*  391 */     double axisMin = calculateLog(Math.max(this.smallestValue, range
/*  392 */           .getLowerBound()));
/*  393 */     double axisMax = calculateLog(range.getUpperBound());
/*      */     
/*  395 */     double min = 0.0D;
/*  396 */     double max = 0.0D;
/*  397 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  398 */       min = area.getX();
/*  399 */       max = area.getMaxX();
/*  400 */     } else if (RectangleEdge.isLeftOrRight(edge)) {
/*  401 */       min = area.getMaxY();
/*  402 */       max = area.getY();
/*      */     } 
/*      */     
/*  405 */     if (isInverted()) {
/*  406 */       log = axisMax - (java2DValue - min) / (max - min) * (axisMax - axisMin);
/*      */     } else {
/*      */       
/*  409 */       log = axisMin + (java2DValue - min) / (max - min) * (axisMax - axisMin);
/*      */     } 
/*      */     
/*  412 */     return calculateValue(log);
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
/*  430 */     Range range = getRange();
/*  431 */     double axisMin = calculateLog(range.getLowerBound());
/*  432 */     double axisMax = calculateLog(range.getUpperBound());
/*  433 */     value = calculateLog(value);
/*      */     
/*  435 */     double min = 0.0D;
/*  436 */     double max = 0.0D;
/*  437 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  438 */       min = area.getX();
/*  439 */       max = area.getMaxX();
/*  440 */     } else if (RectangleEdge.isLeftOrRight(edge)) {
/*  441 */       max = area.getMinY();
/*  442 */       min = area.getMaxY();
/*      */     } 
/*  444 */     if (isInverted()) {
/*  445 */       return max - (value - axisMin) / (axisMax - axisMin) * (max - min);
/*      */     }
/*      */     
/*  448 */     return min + (value - axisMin) / (axisMax - axisMin) * (max - min);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void configure() {
/*  459 */     if (isAutoRange()) {
/*  460 */       autoAdjustRange();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void autoAdjustRange() {
/*  470 */     Plot plot = getPlot();
/*  471 */     if (plot == null) {
/*      */       return;
/*      */     }
/*      */     
/*  475 */     if (plot instanceof ValueAxisPlot) {
/*  476 */       ValueAxisPlot vap = (ValueAxisPlot)plot;
/*      */       
/*  478 */       Range r = vap.getDataRange(this);
/*  479 */       if (r == null) {
/*  480 */         r = getDefaultAutoRange();
/*      */       }
/*      */       
/*  483 */       double upper = r.getUpperBound();
/*  484 */       double lower = Math.max(r.getLowerBound(), this.smallestValue);
/*  485 */       double range = upper - lower;
/*      */ 
/*      */       
/*  488 */       double fixedAutoRange = getFixedAutoRange();
/*  489 */       if (fixedAutoRange > 0.0D) {
/*  490 */         lower = Math.max(upper - fixedAutoRange, this.smallestValue);
/*      */       }
/*      */       else {
/*      */         
/*  494 */         double minRange = getAutoRangeMinimumSize();
/*  495 */         if (range < minRange) {
/*  496 */           double expand = (minRange - range) / 2.0D;
/*  497 */           upper += expand;
/*  498 */           lower -= expand;
/*      */         } 
/*      */ 
/*      */         
/*  502 */         double logUpper = calculateLog(upper);
/*  503 */         double logLower = calculateLog(lower);
/*  504 */         double logRange = logUpper - logLower;
/*  505 */         logUpper += getUpperMargin() * logRange;
/*  506 */         logLower -= getLowerMargin() * logRange;
/*  507 */         upper = calculateValueNoINF(logUpper);
/*  508 */         lower = calculateValueNoINF(logLower);
/*      */       } 
/*  510 */       setRange(new Range(lower, upper), false, false);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState) {
/*  536 */     if (!isVisible()) {
/*  537 */       AxisState axisState = new AxisState(cursor);
/*      */ 
/*      */       
/*  540 */       List ticks = refreshTicks(g2, axisState, dataArea, edge);
/*  541 */       axisState.setTicks(ticks);
/*  542 */       return axisState;
/*      */     } 
/*  544 */     AxisState state = drawTickMarksAndLabels(g2, cursor, plotArea, dataArea, edge);
/*  545 */     if (getAttributedLabel() != null) {
/*  546 */       state = drawAttributedLabel(getAttributedLabel(), g2, plotArea, dataArea, edge, state);
/*      */     }
/*      */     else {
/*      */       
/*  550 */       state = drawLabel(getLabel(), g2, plotArea, dataArea, edge, state);
/*      */     } 
/*  552 */     createAndAddEntity(cursor, state, dataArea, edge, plotState);
/*  553 */     return state;
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
/*  570 */     List result = new ArrayList();
/*  571 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  572 */       result = refreshTicksHorizontal(g2, dataArea, edge);
/*      */     }
/*  574 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  575 */       result = refreshTicksVertical(g2, dataArea, edge);
/*      */     } 
/*  577 */     return result;
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
/*      */   protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/*      */     TextAnchor textAnchor;
/*  592 */     Range range = getRange();
/*  593 */     List<LogTick> ticks = new ArrayList();
/*  594 */     Font tickLabelFont = getTickLabelFont();
/*  595 */     g2.setFont(tickLabelFont);
/*      */     
/*  597 */     if (edge == RectangleEdge.TOP) {
/*  598 */       textAnchor = TextAnchor.BOTTOM_CENTER;
/*      */     } else {
/*      */       
/*  601 */       textAnchor = TextAnchor.TOP_CENTER;
/*      */     } 
/*      */     
/*  604 */     if (isAutoTickUnitSelection()) {
/*  605 */       selectAutoTickUnit(g2, dataArea, edge);
/*      */     }
/*  607 */     int minorTickCount = this.tickUnit.getMinorTickCount();
/*  608 */     double unit = getTickUnit().getSize();
/*  609 */     double index = Math.ceil(calculateLog(getRange().getLowerBound()) / unit);
/*      */     
/*  611 */     double start = index * unit;
/*  612 */     double end = calculateLog(getUpperBound());
/*  613 */     double current = start;
/*      */     
/*  615 */     boolean hasTicks = (this.tickUnit.getSize() > 0.0D && !Double.isInfinite(start));
/*  616 */     while (hasTicks && current <= end) {
/*  617 */       double v = calculateValueNoINF(current);
/*  618 */       if (range.contains(v)) {
/*  619 */         ticks.add(new LogTick(TickType.MAJOR, v, createTickLabel(v), textAnchor));
/*      */       }
/*      */ 
/*      */       
/*  623 */       double next = Math.pow(this.base, current + this.tickUnit
/*  624 */           .getSize());
/*  625 */       for (int i = 1; i < minorTickCount; i++) {
/*  626 */         double minorV = v + i * (next - v) / minorTickCount;
/*  627 */         if (range.contains(minorV)) {
/*  628 */           ticks.add(new LogTick(TickType.MINOR, minorV, null, textAnchor));
/*      */         }
/*      */       } 
/*      */       
/*  632 */       current += this.tickUnit.getSize();
/*      */     } 
/*  634 */     return ticks;
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
/*      */   protected List refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/*      */     TextAnchor textAnchor;
/*  650 */     Range range = getRange();
/*  651 */     List<LogTick> ticks = new ArrayList();
/*  652 */     Font tickLabelFont = getTickLabelFont();
/*  653 */     g2.setFont(tickLabelFont);
/*      */     
/*  655 */     if (edge == RectangleEdge.RIGHT) {
/*  656 */       textAnchor = TextAnchor.CENTER_LEFT;
/*      */     } else {
/*      */       
/*  659 */       textAnchor = TextAnchor.CENTER_RIGHT;
/*      */     } 
/*      */     
/*  662 */     if (isAutoTickUnitSelection()) {
/*  663 */       selectAutoTickUnit(g2, dataArea, edge);
/*      */     }
/*  665 */     int minorTickCount = this.tickUnit.getMinorTickCount();
/*  666 */     double unit = getTickUnit().getSize();
/*  667 */     double index = Math.ceil(calculateLog(getRange().getLowerBound()) / unit);
/*      */     
/*  669 */     double start = index * unit;
/*  670 */     double end = calculateLog(getUpperBound());
/*  671 */     double current = start;
/*      */     
/*  673 */     boolean hasTicks = (this.tickUnit.getSize() > 0.0D && !Double.isInfinite(start));
/*  674 */     while (hasTicks && current <= end) {
/*  675 */       double v = calculateValueNoINF(current);
/*  676 */       if (range.contains(v)) {
/*  677 */         ticks.add(new LogTick(TickType.MAJOR, v, createTickLabel(v), textAnchor));
/*      */       }
/*      */ 
/*      */       
/*  681 */       double next = Math.pow(this.base, current + this.tickUnit
/*  682 */           .getSize());
/*  683 */       for (int i = 1; i < minorTickCount; i++) {
/*  684 */         double minorV = v + i * (next - v) / minorTickCount;
/*  685 */         if (range.contains(minorV)) {
/*  686 */           ticks.add(new LogTick(TickType.MINOR, minorV, null, textAnchor));
/*      */         }
/*      */       } 
/*      */       
/*  690 */       current += this.tickUnit.getSize();
/*      */     } 
/*  692 */     return ticks;
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
/*      */   protected void selectAutoTickUnit(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/*  709 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  710 */       selectHorizontalAutoTickUnit(g2, dataArea, edge);
/*      */     }
/*  712 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  713 */       selectVerticalAutoTickUnit(g2, dataArea, edge);
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
/*      */   protected void selectHorizontalAutoTickUnit(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/*  733 */     Range range = getRange();
/*  734 */     double logAxisMin = calculateLog(Math.max(this.smallestValue, range
/*  735 */           .getLowerBound()));
/*  736 */     double logAxisMax = calculateLog(range.getUpperBound());
/*  737 */     double size = (logAxisMax - logAxisMin) / 50.0D;
/*  738 */     TickUnitSource tickUnits = getStandardTickUnits();
/*  739 */     TickUnit candidate = tickUnits.getCeilingTickUnit(size);
/*  740 */     TickUnit prevCandidate = candidate;
/*  741 */     boolean found = false;
/*  742 */     while (!found) {
/*      */ 
/*      */       
/*  745 */       this.tickUnit = (NumberTickUnit)candidate;
/*  746 */       double tickLabelWidth = estimateMaximumTickLabelWidth(g2, candidate);
/*      */ 
/*      */       
/*  749 */       double candidateWidth = exponentLengthToJava2D(candidate.getSize(), dataArea, edge);
/*      */       
/*  751 */       if (tickLabelWidth < candidateWidth) {
/*  752 */         found = true; continue;
/*  753 */       }  if (Double.isNaN(candidateWidth)) {
/*  754 */         candidate = prevCandidate;
/*  755 */         found = true; continue;
/*      */       } 
/*  757 */       prevCandidate = candidate;
/*  758 */       candidate = tickUnits.getLargerTickUnit(prevCandidate);
/*  759 */       if (candidate.equals(prevCandidate)) {
/*  760 */         found = true;
/*      */       }
/*      */     } 
/*      */     
/*  764 */     setTickUnit((NumberTickUnit)candidate, false, false);
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
/*      */   public double exponentLengthToJava2D(double length, Rectangle2D area, RectangleEdge edge) {
/*  781 */     double one = valueToJava2D(calculateValueNoINF(1.0D), area, edge);
/*  782 */     double l = valueToJava2D(calculateValueNoINF(length + 1.0D), area, edge);
/*  783 */     return Math.abs(l - one);
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
/*      */   protected void selectVerticalAutoTickUnit(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/*  801 */     Range range = getRange();
/*  802 */     double logAxisMin = calculateLog(Math.max(this.smallestValue, range
/*  803 */           .getLowerBound()));
/*  804 */     double logAxisMax = calculateLog(range.getUpperBound());
/*  805 */     double size = (logAxisMax - logAxisMin) / 50.0D;
/*  806 */     TickUnitSource tickUnits = getStandardTickUnits();
/*  807 */     TickUnit candidate = tickUnits.getCeilingTickUnit(size);
/*  808 */     TickUnit prevCandidate = candidate;
/*  809 */     boolean found = false;
/*  810 */     while (!found) {
/*      */ 
/*      */       
/*  813 */       this.tickUnit = (NumberTickUnit)candidate;
/*  814 */       double tickLabelHeight = estimateMaximumTickLabelHeight(g2);
/*      */       
/*  816 */       double candidateHeight = exponentLengthToJava2D(candidate.getSize(), dataArea, edge);
/*      */       
/*  818 */       if (tickLabelHeight < candidateHeight) {
/*  819 */         found = true; continue;
/*  820 */       }  if (Double.isNaN(candidateHeight)) {
/*  821 */         candidate = prevCandidate;
/*  822 */         found = true; continue;
/*      */       } 
/*  824 */       prevCandidate = candidate;
/*  825 */       candidate = tickUnits.getLargerTickUnit(prevCandidate);
/*  826 */       if (candidate.equals(prevCandidate)) {
/*  827 */         found = true;
/*      */       }
/*      */     } 
/*      */     
/*  831 */     setTickUnit((NumberTickUnit)candidate, false, false);
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
/*      */   protected AttributedString createTickLabel(double value) {
/*  845 */     if (this.numberFormatOverride != null) {
/*  846 */       return new AttributedString(this.numberFormatOverride
/*  847 */           .format(value));
/*      */     }
/*  849 */     String baseStr = this.baseSymbol;
/*  850 */     if (baseStr == null) {
/*  851 */       baseStr = this.baseFormatter.format(Double.valueOf(this.base));
/*      */     }
/*  853 */     double logy = calculateLog(value);
/*  854 */     String exponentStr = getTickUnit().valueToString(logy);
/*  855 */     AttributedString as = new AttributedString(baseStr + exponentStr);
/*  856 */     as.addAttributes((Map)getTickLabelFont().getAttributes(), 0, (baseStr + exponentStr)
/*  857 */         .length());
/*  858 */     as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, baseStr
/*  859 */         .length(), baseStr
/*  860 */         .length() + exponentStr.length());
/*  861 */     return as;
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
/*      */   protected double estimateMaximumTickLabelHeight(Graphics2D g2) {
/*  875 */     RectangleInsets tickLabelInsets = getTickLabelInsets();
/*  876 */     double result = tickLabelInsets.getTop() + tickLabelInsets.getBottom();
/*      */     
/*  878 */     Font tickLabelFont = getTickLabelFont();
/*  879 */     FontRenderContext frc = g2.getFontRenderContext();
/*  880 */     result += tickLabelFont.getLineMetrics("123", frc).getHeight();
/*  881 */     return result;
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
/*      */   protected double estimateMaximumTickLabelWidth(Graphics2D g2, TickUnit unit) {
/*  902 */     RectangleInsets tickLabelInsets = getTickLabelInsets();
/*  903 */     double result = tickLabelInsets.getLeft() + tickLabelInsets.getRight();
/*      */     
/*  905 */     if (isVerticalTickLabels()) {
/*      */ 
/*      */       
/*  908 */       FontRenderContext frc = g2.getFontRenderContext();
/*  909 */       LineMetrics lm = getTickLabelFont().getLineMetrics("0", frc);
/*  910 */       result += lm.getHeight();
/*      */     }
/*      */     else {
/*      */       
/*  914 */       Range range = getRange();
/*  915 */       double lower = range.getLowerBound();
/*  916 */       double upper = range.getUpperBound();
/*  917 */       AttributedString lowerStr = createTickLabel(lower);
/*  918 */       AttributedString upperStr = createTickLabel(upper);
/*  919 */       double w1 = AttrStringUtils.getTextBounds(lowerStr, g2).getWidth();
/*  920 */       double w2 = AttrStringUtils.getTextBounds(upperStr, g2).getWidth();
/*  921 */       result += Math.max(w1, w2);
/*      */     } 
/*  923 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomRange(double lowerPercent, double upperPercent) {
/*  934 */     Range adjusted, range = getRange();
/*  935 */     double start = range.getLowerBound();
/*  936 */     double end = range.getUpperBound();
/*  937 */     double log1 = calculateLog(start);
/*  938 */     double log2 = calculateLog(end);
/*  939 */     double length = log2 - log1;
/*      */     
/*  941 */     if (isInverted()) {
/*  942 */       double logA = log1 + length * (1.0D - upperPercent);
/*  943 */       double logB = log1 + length * (1.0D - lowerPercent);
/*      */       
/*  945 */       adjusted = new Range(calculateValueNoINF(logA), calculateValueNoINF(logB));
/*      */     } else {
/*      */       
/*  948 */       double logA = log1 + length * lowerPercent;
/*  949 */       double logB = log1 + length * upperPercent;
/*      */       
/*  951 */       adjusted = new Range(calculateValueNoINF(logA), calculateValueNoINF(logB));
/*      */     } 
/*  953 */     setRange(adjusted);
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
/*      */   public void pan(double percent) {
/*  965 */     Range range = getRange();
/*  966 */     double lower = range.getLowerBound();
/*  967 */     double upper = range.getUpperBound();
/*  968 */     double log1 = calculateLog(lower);
/*  969 */     double log2 = calculateLog(upper);
/*  970 */     double length = log2 - log1;
/*  971 */     double adj = length * percent;
/*  972 */     log1 += adj;
/*  973 */     log2 += adj;
/*  974 */     setRange(calculateValueNoINF(log1), calculateValueNoINF(log2));
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
/*      */   public void resizeRange(double percent) {
/*  991 */     Range range = getRange();
/*  992 */     double logMin = calculateLog(range.getLowerBound());
/*  993 */     double logMax = calculateLog(range.getUpperBound());
/*  994 */     double centralValue = calculateValueNoINF((logMin + logMax) / 2.0D);
/*  995 */     resizeRange(percent, centralValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public void resizeRange(double percent, double anchorValue) {
/* 1000 */     resizeRange2(percent, anchorValue);
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
/*      */   public void resizeRange2(double percent, double anchorValue) {
/* 1021 */     if (percent > 0.0D) {
/* 1022 */       double logAnchorValue = calculateLog(anchorValue);
/* 1023 */       Range range = getRange();
/* 1024 */       double logAxisMin = calculateLog(range.getLowerBound());
/* 1025 */       double logAxisMax = calculateLog(range.getUpperBound());
/*      */       
/* 1027 */       double left = percent * (logAnchorValue - logAxisMin);
/* 1028 */       double right = percent * (logAxisMax - logAnchorValue);
/*      */       
/* 1030 */       double upperBound = calculateValueNoINF(logAnchorValue + right);
/* 1031 */       Range adjusted = new Range(calculateValueNoINF(logAnchorValue - left), upperBound);
/*      */       
/* 1033 */       setRange(adjusted);
/*      */     } else {
/*      */       
/* 1036 */       setAutoRange(true);
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
/*      */   public boolean equals(Object obj) {
/* 1049 */     if (obj == this) {
/* 1050 */       return true;
/*      */     }
/* 1052 */     if (!(obj instanceof LogAxis)) {
/* 1053 */       return false;
/*      */     }
/* 1055 */     LogAxis that = (LogAxis)obj;
/* 1056 */     if (this.base != that.base) {
/* 1057 */       return false;
/*      */     }
/* 1059 */     if (!ObjectUtilities.equal(this.baseSymbol, that.baseSymbol)) {
/* 1060 */       return false;
/*      */     }
/* 1062 */     if (!this.baseFormatter.equals(that.baseFormatter)) {
/* 1063 */       return false;
/*      */     }
/* 1065 */     if (this.smallestValue != that.smallestValue) {
/* 1066 */       return false;
/*      */     }
/* 1068 */     if (!ObjectUtilities.equal(this.numberFormatOverride, that.numberFormatOverride))
/*      */     {
/* 1070 */       return false;
/*      */     }
/* 1072 */     return super.equals(obj);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1082 */     int result = 193;
/* 1083 */     long temp = Double.doubleToLongBits(this.base);
/* 1084 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 1085 */     temp = Double.doubleToLongBits(this.smallestValue);
/* 1086 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 1087 */     if (this.numberFormatOverride != null) {
/* 1088 */       result = 37 * result + this.numberFormatOverride.hashCode();
/*      */     }
/* 1090 */     result = 37 * result + this.tickUnit.hashCode();
/* 1091 */     return result;
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
/*      */   public static TickUnitSource createLogTickUnits(Locale locale) {
/* 1109 */     TickUnits units = new TickUnits();
/* 1110 */     LogFormat logFormat = new LogFormat();
/* 1111 */     units.add(new NumberTickUnit(0.05D, (NumberFormat)logFormat, 2));
/* 1112 */     units.add(new NumberTickUnit(0.1D, (NumberFormat)logFormat, 10));
/* 1113 */     units.add(new NumberTickUnit(0.2D, (NumberFormat)logFormat, 2));
/* 1114 */     units.add(new NumberTickUnit(0.5D, (NumberFormat)logFormat, 5));
/* 1115 */     units.add(new NumberTickUnit(1.0D, (NumberFormat)logFormat, 10));
/* 1116 */     units.add(new NumberTickUnit(2.0D, (NumberFormat)logFormat, 10));
/* 1117 */     units.add(new NumberTickUnit(3.0D, (NumberFormat)logFormat, 15));
/* 1118 */     units.add(new NumberTickUnit(4.0D, (NumberFormat)logFormat, 20));
/* 1119 */     units.add(new NumberTickUnit(5.0D, (NumberFormat)logFormat, 25));
/* 1120 */     units.add(new NumberTickUnit(6.0D, (NumberFormat)logFormat));
/* 1121 */     units.add(new NumberTickUnit(7.0D, (NumberFormat)logFormat));
/* 1122 */     units.add(new NumberTickUnit(8.0D, (NumberFormat)logFormat));
/* 1123 */     units.add(new NumberTickUnit(9.0D, (NumberFormat)logFormat));
/* 1124 */     units.add(new NumberTickUnit(10.0D, (NumberFormat)logFormat));
/* 1125 */     return units;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/LogAxis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */