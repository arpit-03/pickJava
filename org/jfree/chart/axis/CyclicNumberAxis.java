/*      */ package org.jfree.chart.axis;
/*      */ 
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import org.jfree.chart.plot.Plot;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.TextAnchor;
/*      */ import org.jfree.util.ObjectUtilities;
/*      */ import org.jfree.util.PaintUtilities;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CyclicNumberAxis
/*      */   extends NumberAxis
/*      */ {
/*      */   static final long serialVersionUID = -7514160997164582554L;
/*  134 */   public static Stroke DEFAULT_ADVANCE_LINE_STROKE = new BasicStroke(1.0F);
/*      */ 
/*      */   
/*  137 */   public static final Paint DEFAULT_ADVANCE_LINE_PAINT = Color.gray;
/*      */ 
/*      */   
/*      */   protected double offset;
/*      */ 
/*      */   
/*      */   protected double period;
/*      */ 
/*      */   
/*      */   protected boolean boundMappedToLastCycle;
/*      */ 
/*      */   
/*      */   protected boolean advanceLineVisible;
/*      */ 
/*      */   
/*  152 */   protected transient Stroke advanceLineStroke = DEFAULT_ADVANCE_LINE_STROKE;
/*      */ 
/*      */   
/*      */   protected transient Paint advanceLinePaint;
/*      */ 
/*      */   
/*      */   private transient boolean internalMarkerWhenTicksOverlap;
/*      */ 
/*      */   
/*      */   private transient Tick internalMarkerCycleBoundTick;
/*      */ 
/*      */ 
/*      */   
/*      */   public CyclicNumberAxis(double period) {
/*  166 */     this(period, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CyclicNumberAxis(double period, double offset) {
/*  176 */     this(period, offset, (String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CyclicNumberAxis(double period, String label) {
/*  186 */     this(0.0D, period, label);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CyclicNumberAxis(double period, double offset, String label) {
/*  197 */     super(label);
/*  198 */     this.period = period;
/*  199 */     this.offset = offset;
/*  200 */     setFixedAutoRange(period);
/*  201 */     this.advanceLineVisible = true;
/*  202 */     this.advanceLinePaint = DEFAULT_ADVANCE_LINE_PAINT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAdvanceLineVisible() {
/*  212 */     return this.advanceLineVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAdvanceLineVisible(boolean visible) {
/*  222 */     this.advanceLineVisible = visible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getAdvanceLinePaint() {
/*  232 */     return this.advanceLinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAdvanceLinePaint(Paint paint) {
/*  242 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  243 */     this.advanceLinePaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getAdvanceLineStroke() {
/*  253 */     return this.advanceLineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAdvanceLineStroke(Stroke stroke) {
/*  262 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/*  263 */     this.advanceLineStroke = stroke;
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
/*      */   public boolean isBoundMappedToLastCycle() {
/*  281 */     return this.boundMappedToLastCycle;
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
/*      */   public void setBoundMappedToLastCycle(boolean boundMappedToLastCycle) {
/*  298 */     this.boundMappedToLastCycle = boundMappedToLastCycle;
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
/*      */   protected void selectHorizontalAutoTickUnit(Graphics2D g2, Rectangle2D drawArea, Rectangle2D dataArea, RectangleEdge edge) {
/*  313 */     double tickLabelWidth = estimateMaximumTickLabelWidth(g2, getTickUnit());
/*      */ 
/*      */ 
/*      */     
/*  317 */     double n = getRange().getLength() * tickLabelWidth / dataArea.getWidth();
/*      */     
/*  319 */     setTickUnit((NumberTickUnit)
/*  320 */         getStandardTickUnits().getCeilingTickUnit(n), false, false);
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
/*      */   protected void selectVerticalAutoTickUnit(Graphics2D g2, Rectangle2D drawArea, Rectangle2D dataArea, RectangleEdge edge) {
/*  337 */     double tickLabelWidth = estimateMaximumTickLabelWidth(g2, getTickUnit());
/*      */ 
/*      */ 
/*      */     
/*  341 */     double n = getRange().getLength() * tickLabelWidth / dataArea.getHeight();
/*      */     
/*  343 */     setTickUnit((NumberTickUnit)
/*  344 */         getStandardTickUnits().getCeilingTickUnit(n), false, false);
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
/*      */   protected static class CycleBoundTick
/*      */     extends NumberTick
/*      */   {
/*      */     public boolean mapToLastCycle;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CycleBoundTick(boolean mapToLastCycle, Number number, String label, TextAnchor textAnchor, TextAnchor rotationAnchor, double angle) {
/*  372 */       super(number, label, textAnchor, rotationAnchor, angle);
/*  373 */       this.mapToLastCycle = mapToLastCycle;
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
/*      */   protected float[] calculateAnchorPoint(ValueTick tick, double cursor, Rectangle2D dataArea, RectangleEdge edge) {
/*  390 */     if (tick instanceof CycleBoundTick) {
/*  391 */       boolean mapsav = this.boundMappedToLastCycle;
/*  392 */       this.boundMappedToLastCycle = ((CycleBoundTick)tick).mapToLastCycle;
/*      */       
/*  394 */       float[] ret = super.calculateAnchorPoint(tick, cursor, dataArea, edge);
/*      */ 
/*      */       
/*  397 */       this.boundMappedToLastCycle = mapsav;
/*  398 */       return ret;
/*      */     } 
/*  400 */     return super.calculateAnchorPoint(tick, cursor, dataArea, edge);
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
/*      */   protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/*  419 */     List<CycleBoundTick> result = new ArrayList();
/*      */     
/*  421 */     Font tickLabelFont = getTickLabelFont();
/*  422 */     g2.setFont(tickLabelFont);
/*      */     
/*  424 */     if (isAutoTickUnitSelection()) {
/*  425 */       selectAutoTickUnit(g2, dataArea, edge);
/*      */     }
/*      */     
/*  428 */     double unit = getTickUnit().getSize();
/*  429 */     double cycleBound = getCycleBound();
/*  430 */     double currentTickValue = Math.ceil(cycleBound / unit) * unit;
/*  431 */     double upperValue = getRange().getUpperBound();
/*  432 */     boolean cycled = false;
/*      */     
/*  434 */     boolean boundMapping = this.boundMappedToLastCycle;
/*  435 */     this.boundMappedToLastCycle = false;
/*      */     
/*  437 */     CycleBoundTick lastTick = null;
/*  438 */     float lastX = 0.0F;
/*      */     
/*  440 */     if (upperValue == cycleBound) {
/*  441 */       currentTickValue = calculateLowestVisibleTickValue();
/*  442 */       cycled = true;
/*  443 */       this.boundMappedToLastCycle = true;
/*      */     } 
/*      */     
/*  446 */     while (currentTickValue <= upperValue) {
/*      */       String tickLabel;
/*      */       TextAnchor anchor, rotationAnchor;
/*  449 */       boolean cyclenow = false;
/*  450 */       if (currentTickValue + unit > upperValue && !cycled) {
/*  451 */         cyclenow = true;
/*      */       }
/*      */       
/*  454 */       double xx = valueToJava2D(currentTickValue, dataArea, edge);
/*      */       
/*  456 */       NumberFormat formatter = getNumberFormatOverride();
/*  457 */       if (formatter != null) {
/*  458 */         tickLabel = formatter.format(currentTickValue);
/*      */       } else {
/*      */         
/*  461 */         tickLabel = getTickUnit().valueToString(currentTickValue);
/*      */       } 
/*  463 */       float x = (float)xx;
/*      */ 
/*      */       
/*  466 */       double angle = 0.0D;
/*  467 */       if (isVerticalTickLabels()) {
/*  468 */         if (edge == RectangleEdge.TOP) {
/*  469 */           angle = 1.5707963267948966D;
/*      */         } else {
/*      */           
/*  472 */           angle = -1.5707963267948966D;
/*      */         } 
/*  474 */         anchor = TextAnchor.CENTER_RIGHT;
/*      */         
/*  476 */         if (lastTick != null && lastX == x && currentTickValue != cycleBound) {
/*      */           
/*  478 */           anchor = isInverted() ? TextAnchor.TOP_RIGHT : TextAnchor.BOTTOM_RIGHT;
/*      */           
/*  480 */           result.remove(result.size() - 1);
/*  481 */           result.add(new CycleBoundTick(this.boundMappedToLastCycle, lastTick
/*  482 */                 .getNumber(), lastTick
/*  483 */                 .getText(), anchor, anchor, lastTick
/*  484 */                 .getAngle()));
/*      */           
/*  486 */           this.internalMarkerWhenTicksOverlap = true;
/*  487 */           anchor = isInverted() ? TextAnchor.BOTTOM_RIGHT : TextAnchor.TOP_RIGHT;
/*      */         } 
/*      */         
/*  490 */         rotationAnchor = anchor;
/*      */       
/*      */       }
/*  493 */       else if (edge == RectangleEdge.TOP) {
/*  494 */         anchor = TextAnchor.BOTTOM_CENTER;
/*  495 */         if (lastTick != null && lastX == x && currentTickValue != cycleBound) {
/*      */           
/*  497 */           anchor = isInverted() ? TextAnchor.BOTTOM_LEFT : TextAnchor.BOTTOM_RIGHT;
/*      */           
/*  499 */           result.remove(result.size() - 1);
/*  500 */           result.add(new CycleBoundTick(this.boundMappedToLastCycle, lastTick
/*  501 */                 .getNumber(), lastTick
/*  502 */                 .getText(), anchor, anchor, lastTick
/*  503 */                 .getAngle()));
/*      */           
/*  505 */           this.internalMarkerWhenTicksOverlap = true;
/*  506 */           anchor = isInverted() ? TextAnchor.BOTTOM_RIGHT : TextAnchor.BOTTOM_LEFT;
/*      */         } 
/*      */         
/*  509 */         rotationAnchor = anchor;
/*      */       } else {
/*      */         
/*  512 */         anchor = TextAnchor.TOP_CENTER;
/*  513 */         if (lastTick != null && lastX == x && currentTickValue != cycleBound) {
/*      */           
/*  515 */           anchor = isInverted() ? TextAnchor.TOP_LEFT : TextAnchor.TOP_RIGHT;
/*      */           
/*  517 */           result.remove(result.size() - 1);
/*  518 */           result.add(new CycleBoundTick(this.boundMappedToLastCycle, lastTick
/*  519 */                 .getNumber(), lastTick
/*  520 */                 .getText(), anchor, anchor, lastTick
/*  521 */                 .getAngle()));
/*      */           
/*  523 */           this.internalMarkerWhenTicksOverlap = true;
/*  524 */           anchor = isInverted() ? TextAnchor.TOP_RIGHT : TextAnchor.TOP_LEFT;
/*      */         } 
/*      */         
/*  527 */         rotationAnchor = anchor;
/*      */       } 
/*      */ 
/*      */       
/*  531 */       CycleBoundTick tick = new CycleBoundTick(this.boundMappedToLastCycle, new Double(currentTickValue), tickLabel, anchor, rotationAnchor, angle);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  536 */       if (currentTickValue == cycleBound) {
/*  537 */         this.internalMarkerCycleBoundTick = tick;
/*      */       }
/*  539 */       result.add(tick);
/*  540 */       lastTick = tick;
/*  541 */       lastX = x;
/*      */       
/*  543 */       currentTickValue += unit;
/*      */       
/*  545 */       if (cyclenow) {
/*  546 */         currentTickValue = calculateLowestVisibleTickValue();
/*  547 */         upperValue = cycleBound;
/*  548 */         cycled = true;
/*  549 */         this.boundMappedToLastCycle = true;
/*      */       } 
/*      */     } 
/*      */     
/*  553 */     this.boundMappedToLastCycle = boundMapping;
/*  554 */     return result;
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
/*      */   protected List refreshVerticalTicks(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/*  571 */     List<CycleBoundTick> result = new ArrayList();
/*  572 */     result.clear();
/*      */     
/*  574 */     Font tickLabelFont = getTickLabelFont();
/*  575 */     g2.setFont(tickLabelFont);
/*  576 */     if (isAutoTickUnitSelection()) {
/*  577 */       selectAutoTickUnit(g2, dataArea, edge);
/*      */     }
/*      */     
/*  580 */     double unit = getTickUnit().getSize();
/*  581 */     double cycleBound = getCycleBound();
/*  582 */     double currentTickValue = Math.ceil(cycleBound / unit) * unit;
/*  583 */     double upperValue = getRange().getUpperBound();
/*  584 */     boolean cycled = false;
/*      */     
/*  586 */     boolean boundMapping = this.boundMappedToLastCycle;
/*  587 */     this.boundMappedToLastCycle = true;
/*      */     
/*  589 */     NumberTick lastTick = null;
/*  590 */     float lastY = 0.0F;
/*      */     
/*  592 */     if (upperValue == cycleBound) {
/*  593 */       currentTickValue = calculateLowestVisibleTickValue();
/*  594 */       cycled = true;
/*  595 */       this.boundMappedToLastCycle = true;
/*      */     } 
/*      */     
/*  598 */     while (currentTickValue <= upperValue) {
/*      */       String tickLabel;
/*      */       TextAnchor anchor, rotationAnchor;
/*  601 */       boolean cyclenow = false;
/*  602 */       if (currentTickValue + unit > upperValue && !cycled) {
/*  603 */         cyclenow = true;
/*      */       }
/*      */       
/*  606 */       double yy = valueToJava2D(currentTickValue, dataArea, edge);
/*      */       
/*  608 */       NumberFormat formatter = getNumberFormatOverride();
/*  609 */       if (formatter != null) {
/*  610 */         tickLabel = formatter.format(currentTickValue);
/*      */       } else {
/*      */         
/*  613 */         tickLabel = getTickUnit().valueToString(currentTickValue);
/*      */       } 
/*      */       
/*  616 */       float y = (float)yy;
/*      */ 
/*      */       
/*  619 */       double angle = 0.0D;
/*  620 */       if (isVerticalTickLabels()) {
/*      */         
/*  622 */         if (edge == RectangleEdge.LEFT) {
/*  623 */           anchor = TextAnchor.BOTTOM_CENTER;
/*  624 */           if (lastTick != null && lastY == y && currentTickValue != cycleBound) {
/*      */             
/*  626 */             anchor = isInverted() ? TextAnchor.BOTTOM_LEFT : TextAnchor.BOTTOM_RIGHT;
/*      */             
/*  628 */             result.remove(result.size() - 1);
/*  629 */             result.add(new CycleBoundTick(this.boundMappedToLastCycle, lastTick
/*  630 */                   .getNumber(), lastTick
/*  631 */                   .getText(), anchor, anchor, lastTick
/*  632 */                   .getAngle()));
/*      */             
/*  634 */             this.internalMarkerWhenTicksOverlap = true;
/*  635 */             anchor = isInverted() ? TextAnchor.BOTTOM_RIGHT : TextAnchor.BOTTOM_LEFT;
/*      */           } 
/*      */           
/*  638 */           rotationAnchor = anchor;
/*  639 */           angle = -1.5707963267948966D;
/*      */         } else {
/*      */           
/*  642 */           anchor = TextAnchor.BOTTOM_CENTER;
/*  643 */           if (lastTick != null && lastY == y && currentTickValue != cycleBound) {
/*      */             
/*  645 */             anchor = isInverted() ? TextAnchor.BOTTOM_RIGHT : TextAnchor.BOTTOM_LEFT;
/*      */             
/*  647 */             result.remove(result.size() - 1);
/*  648 */             result.add(new CycleBoundTick(this.boundMappedToLastCycle, lastTick
/*  649 */                   .getNumber(), lastTick
/*  650 */                   .getText(), anchor, anchor, lastTick
/*  651 */                   .getAngle()));
/*      */             
/*  653 */             this.internalMarkerWhenTicksOverlap = true;
/*  654 */             anchor = isInverted() ? TextAnchor.BOTTOM_LEFT : TextAnchor.BOTTOM_RIGHT;
/*      */           } 
/*      */           
/*  657 */           rotationAnchor = anchor;
/*  658 */           angle = 1.5707963267948966D;
/*      */         }
/*      */       
/*      */       }
/*  662 */       else if (edge == RectangleEdge.LEFT) {
/*  663 */         anchor = TextAnchor.CENTER_RIGHT;
/*  664 */         if (lastTick != null && lastY == y && currentTickValue != cycleBound) {
/*      */           
/*  666 */           anchor = isInverted() ? TextAnchor.BOTTOM_RIGHT : TextAnchor.TOP_RIGHT;
/*      */           
/*  668 */           result.remove(result.size() - 1);
/*  669 */           result.add(new CycleBoundTick(this.boundMappedToLastCycle, lastTick
/*  670 */                 .getNumber(), lastTick
/*  671 */                 .getText(), anchor, anchor, lastTick
/*  672 */                 .getAngle()));
/*      */           
/*  674 */           this.internalMarkerWhenTicksOverlap = true;
/*  675 */           anchor = isInverted() ? TextAnchor.TOP_RIGHT : TextAnchor.BOTTOM_RIGHT;
/*      */         } 
/*      */         
/*  678 */         rotationAnchor = anchor;
/*      */       } else {
/*      */         
/*  681 */         anchor = TextAnchor.CENTER_LEFT;
/*  682 */         if (lastTick != null && lastY == y && currentTickValue != cycleBound) {
/*      */           
/*  684 */           anchor = isInverted() ? TextAnchor.BOTTOM_LEFT : TextAnchor.TOP_LEFT;
/*      */           
/*  686 */           result.remove(result.size() - 1);
/*  687 */           result.add(new CycleBoundTick(this.boundMappedToLastCycle, lastTick
/*  688 */                 .getNumber(), lastTick
/*  689 */                 .getText(), anchor, anchor, lastTick
/*  690 */                 .getAngle()));
/*      */           
/*  692 */           this.internalMarkerWhenTicksOverlap = true;
/*  693 */           anchor = isInverted() ? TextAnchor.TOP_LEFT : TextAnchor.BOTTOM_LEFT;
/*      */         } 
/*      */         
/*  696 */         rotationAnchor = anchor;
/*      */       } 
/*      */ 
/*      */       
/*  700 */       CycleBoundTick tick = new CycleBoundTick(this.boundMappedToLastCycle, new Double(currentTickValue), tickLabel, anchor, rotationAnchor, angle);
/*      */ 
/*      */       
/*  703 */       if (currentTickValue == cycleBound) {
/*  704 */         this.internalMarkerCycleBoundTick = tick;
/*      */       }
/*  706 */       result.add(tick);
/*  707 */       lastTick = tick;
/*  708 */       lastY = y;
/*      */       
/*  710 */       if (currentTickValue == cycleBound) {
/*  711 */         this.internalMarkerCycleBoundTick = tick;
/*      */       }
/*      */       
/*  714 */       currentTickValue += unit;
/*      */       
/*  716 */       if (cyclenow) {
/*  717 */         currentTickValue = calculateLowestVisibleTickValue();
/*  718 */         upperValue = cycleBound;
/*  719 */         cycled = true;
/*  720 */         this.boundMappedToLastCycle = false;
/*      */       } 
/*      */     } 
/*      */     
/*  724 */     this.boundMappedToLastCycle = boundMapping;
/*  725 */     return result;
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
/*      */   public double java2DToValue(double java2DValue, Rectangle2D dataArea, RectangleEdge edge) {
/*  740 */     Range range = getRange();
/*      */     
/*  742 */     double vmax = range.getUpperBound();
/*  743 */     double vp = getCycleBound();
/*      */     
/*  745 */     double jmin = 0.0D;
/*  746 */     double jmax = 0.0D;
/*  747 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  748 */       jmin = dataArea.getMinX();
/*  749 */       jmax = dataArea.getMaxX();
/*      */     }
/*  751 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  752 */       jmin = dataArea.getMaxY();
/*  753 */       jmax = dataArea.getMinY();
/*      */     } 
/*      */     
/*  756 */     if (isInverted()) {
/*  757 */       double d = jmax - (vmax - vp) * (jmax - jmin) / this.period;
/*  758 */       if (java2DValue >= d) {
/*  759 */         return vp + (jmax - java2DValue) * this.period / (jmax - jmin);
/*      */       }
/*      */       
/*  762 */       return vp - (java2DValue - jmin) * this.period / (jmax - jmin);
/*      */     } 
/*      */ 
/*      */     
/*  766 */     double jbreak = (vmax - vp) * (jmax - jmin) / this.period + jmin;
/*  767 */     if (java2DValue <= jbreak) {
/*  768 */       return vp + (java2DValue - jmin) * this.period / (jmax - jmin);
/*      */     }
/*      */     
/*  771 */     return vp - (jmax - java2DValue) * this.period / (jmax - jmin);
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
/*      */   public double valueToJava2D(double value, Rectangle2D dataArea, RectangleEdge edge) {
/*  788 */     Range range = getRange();
/*      */     
/*  790 */     double vmin = range.getLowerBound();
/*  791 */     double vmax = range.getUpperBound();
/*  792 */     double vp = getCycleBound();
/*      */     
/*  794 */     if (value < vmin || value > vmax) {
/*  795 */       return Double.NaN;
/*      */     }
/*      */ 
/*      */     
/*  799 */     double jmin = 0.0D;
/*  800 */     double jmax = 0.0D;
/*  801 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  802 */       jmin = dataArea.getMinX();
/*  803 */       jmax = dataArea.getMaxX();
/*      */     }
/*  805 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  806 */       jmax = dataArea.getMinY();
/*  807 */       jmin = dataArea.getMaxY();
/*      */     } 
/*      */     
/*  810 */     if (isInverted()) {
/*  811 */       if (value == vp) {
/*  812 */         return this.boundMappedToLastCycle ? jmin : jmax;
/*      */       }
/*  814 */       if (value > vp) {
/*  815 */         return jmax - (value - vp) * (jmax - jmin) / this.period;
/*      */       }
/*      */       
/*  818 */       return jmin + (vp - value) * (jmax - jmin) / this.period;
/*      */     } 
/*      */ 
/*      */     
/*  822 */     if (value == vp) {
/*  823 */       return this.boundMappedToLastCycle ? jmax : jmin;
/*      */     }
/*  825 */     if (value >= vp) {
/*  826 */       return jmin + (value - vp) * (jmax - jmin) / this.period;
/*      */     }
/*      */     
/*  829 */     return jmax - (vp - value) * (jmax - jmin) / this.period;
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
/*      */   public void centerRange(double value) {
/*  841 */     setRange(value - this.period / 2.0D, value + this.period / 2.0D);
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
/*      */   public void setAutoRangeMinimumSize(double size, boolean notify) {
/*  857 */     if (size > this.period) {
/*  858 */       this.period = size;
/*      */     }
/*  860 */     super.setAutoRangeMinimumSize(size, notify);
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
/*      */   public void setFixedAutoRange(double length) {
/*  873 */     this.period = length;
/*  874 */     super.setFixedAutoRange(length);
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
/*  890 */     double size = range.getUpperBound() - range.getLowerBound();
/*  891 */     if (size > this.period) {
/*  892 */       this.period = size;
/*      */     }
/*  894 */     super.setRange(range, turnOffAutoRange, notify);
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
/*      */   public double getCycleBound() {
/*  908 */     return Math.floor((
/*  909 */         getRange().getUpperBound() - this.offset) / this.period) * this.period + this.offset;
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
/*      */   public double getOffset() {
/*  923 */     return this.offset;
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
/*      */   public void setOffset(double offset) {
/*  936 */     this.offset = offset;
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
/*      */   public double getPeriod() {
/*  949 */     return this.period;
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
/*      */   public void setPeriod(double period) {
/*  962 */     this.period = period;
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
/*      */   protected AxisState drawTickMarksAndLabels(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge) {
/*      */     double ol;
/*  979 */     this.internalMarkerWhenTicksOverlap = false;
/*  980 */     AxisState ret = super.drawTickMarksAndLabels(g2, cursor, plotArea, dataArea, edge);
/*      */ 
/*      */ 
/*      */     
/*  984 */     if (!this.internalMarkerWhenTicksOverlap) {
/*  985 */       return ret;
/*      */     }
/*      */ 
/*      */     
/*  989 */     FontMetrics fm = g2.getFontMetrics(getTickLabelFont());
/*  990 */     if (isVerticalTickLabels()) {
/*  991 */       ol = fm.getMaxAdvance();
/*      */     } else {
/*      */       
/*  994 */       ol = fm.getHeight();
/*      */     } 
/*      */     
/*  997 */     double il = 0.0D;
/*  998 */     if (isTickMarksVisible()) {
/*  999 */       float xx = (float)valueToJava2D(getRange().getUpperBound(), dataArea, edge);
/*      */       
/* 1001 */       Line2D mark = null;
/* 1002 */       g2.setStroke(getTickMarkStroke());
/* 1003 */       g2.setPaint(getTickMarkPaint());
/* 1004 */       if (edge == RectangleEdge.LEFT) {
/* 1005 */         mark = new Line2D.Double(cursor - ol, xx, cursor + il, xx);
/*      */       }
/* 1007 */       else if (edge == RectangleEdge.RIGHT) {
/* 1008 */         mark = new Line2D.Double(cursor + ol, xx, cursor - il, xx);
/*      */       }
/* 1010 */       else if (edge == RectangleEdge.TOP) {
/* 1011 */         mark = new Line2D.Double(xx, cursor - ol, xx, cursor + il);
/*      */       }
/* 1013 */       else if (edge == RectangleEdge.BOTTOM) {
/* 1014 */         mark = new Line2D.Double(xx, cursor + ol, xx, cursor - il);
/*      */       } 
/* 1016 */       g2.draw(mark);
/*      */     } 
/* 1018 */     return ret;
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
/*      */   public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState) {
/* 1038 */     AxisState ret = super.draw(g2, cursor, plotArea, dataArea, edge, plotState);
/*      */     
/* 1040 */     if (isAdvanceLineVisible()) {
/* 1041 */       double xx = valueToJava2D(getRange().getUpperBound(), dataArea, edge);
/*      */       
/* 1043 */       Line2D mark = null;
/* 1044 */       g2.setStroke(getAdvanceLineStroke());
/* 1045 */       g2.setPaint(getAdvanceLinePaint());
/* 1046 */       if (edge == RectangleEdge.LEFT) {
/*      */         
/* 1048 */         mark = new Line2D.Double(cursor, xx, cursor + dataArea.getWidth(), xx);
/*      */       }
/* 1050 */       else if (edge == RectangleEdge.RIGHT) {
/* 1051 */         mark = new Line2D.Double(cursor - dataArea.getWidth(), xx, cursor, xx);
/*      */       
/*      */       }
/* 1054 */       else if (edge == RectangleEdge.TOP) {
/* 1055 */         mark = new Line2D.Double(xx, cursor + dataArea.getHeight(), xx, cursor);
/*      */       
/*      */       }
/* 1058 */       else if (edge == RectangleEdge.BOTTOM) {
/*      */         
/* 1060 */         mark = new Line2D.Double(xx, cursor, xx, cursor - dataArea.getHeight());
/*      */       } 
/* 1062 */       g2.draw(mark);
/*      */     } 
/* 1064 */     return ret;
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
/*      */   public AxisSpace reserveSpace(Graphics2D g2, Plot plot, Rectangle2D plotArea, RectangleEdge edge, AxisSpace space) {
/* 1083 */     this.internalMarkerCycleBoundTick = null;
/* 1084 */     AxisSpace ret = super.reserveSpace(g2, plot, plotArea, edge, space);
/* 1085 */     if (this.internalMarkerCycleBoundTick == null) {
/* 1086 */       return ret;
/*      */     }
/*      */     
/* 1089 */     FontMetrics fm = g2.getFontMetrics(getTickLabelFont());
/* 1090 */     Rectangle2D r = TextUtilities.getTextBounds(this.internalMarkerCycleBoundTick
/* 1091 */         .getText(), g2, fm);
/*      */ 
/*      */     
/* 1094 */     if (RectangleEdge.isTopOrBottom(edge)) {
/* 1095 */       if (isVerticalTickLabels()) {
/* 1096 */         space.add(r.getHeight() / 2.0D, RectangleEdge.RIGHT);
/*      */       } else {
/*      */         
/* 1099 */         space.add(r.getWidth() / 2.0D, RectangleEdge.RIGHT);
/*      */       }
/*      */     
/* 1102 */     } else if (RectangleEdge.isLeftOrRight(edge)) {
/* 1103 */       if (isVerticalTickLabels()) {
/* 1104 */         space.add(r.getWidth() / 2.0D, RectangleEdge.TOP);
/*      */       } else {
/*      */         
/* 1107 */         space.add(r.getHeight() / 2.0D, RectangleEdge.TOP);
/*      */       } 
/*      */     } 
/*      */     
/* 1111 */     return ret;
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
/*      */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 1123 */     stream.defaultWriteObject();
/* 1124 */     SerialUtilities.writePaint(this.advanceLinePaint, stream);
/* 1125 */     SerialUtilities.writeStroke(this.advanceLineStroke, stream);
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
/* 1138 */     stream.defaultReadObject();
/* 1139 */     this.advanceLinePaint = SerialUtilities.readPaint(stream);
/* 1140 */     this.advanceLineStroke = SerialUtilities.readStroke(stream);
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
/* 1153 */     if (obj == this) {
/* 1154 */       return true;
/*      */     }
/* 1156 */     if (!(obj instanceof CyclicNumberAxis)) {
/* 1157 */       return false;
/*      */     }
/* 1159 */     if (!super.equals(obj)) {
/* 1160 */       return false;
/*      */     }
/* 1162 */     CyclicNumberAxis that = (CyclicNumberAxis)obj;
/* 1163 */     if (this.period != that.period) {
/* 1164 */       return false;
/*      */     }
/* 1166 */     if (this.offset != that.offset) {
/* 1167 */       return false;
/*      */     }
/* 1169 */     if (!PaintUtilities.equal(this.advanceLinePaint, that.advanceLinePaint))
/*      */     {
/* 1171 */       return false;
/*      */     }
/* 1173 */     if (!ObjectUtilities.equal(this.advanceLineStroke, that.advanceLineStroke))
/*      */     {
/* 1175 */       return false;
/*      */     }
/* 1177 */     if (this.advanceLineVisible != that.advanceLineVisible) {
/* 1178 */       return false;
/*      */     }
/* 1180 */     if (this.boundMappedToLastCycle != that.boundMappedToLastCycle) {
/* 1181 */       return false;
/*      */     }
/* 1183 */     return true;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/CyclicNumberAxis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */