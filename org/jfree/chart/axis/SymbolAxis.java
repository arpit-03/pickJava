/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.text.TextUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ import org.jfree.util.PaintUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SymbolAxis
/*     */   extends NumberAxis
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7216330468770619716L;
/* 123 */   public static final Paint DEFAULT_GRID_BAND_PAINT = new Color(232, 234, 232, 128);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   public static final Paint DEFAULT_GRID_BAND_ALTERNATE_PAINT = new Color(0, 0, 0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List symbols;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean gridBandsVisible;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint gridBandPaint;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint gridBandAlternatePaint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SymbolAxis(String label, String[] sv) {
/* 159 */     super(label);
/* 160 */     this.symbols = Arrays.asList(sv);
/* 161 */     this.gridBandsVisible = true;
/* 162 */     this.gridBandPaint = DEFAULT_GRID_BAND_PAINT;
/* 163 */     this.gridBandAlternatePaint = DEFAULT_GRID_BAND_ALTERNATE_PAINT;
/* 164 */     setAutoTickUnitSelection(false, false);
/* 165 */     setAutoRangeStickyZero(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getSymbols() {
/* 175 */     String[] result = new String[this.symbols.size()];
/* 176 */     result = (String[])this.symbols.toArray((Object[])result);
/* 177 */     return result;
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
/*     */   public boolean isGridBandsVisible() {
/* 190 */     return this.gridBandsVisible;
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
/*     */   public void setGridBandsVisible(boolean flag) {
/* 202 */     this.gridBandsVisible = flag;
/* 203 */     fireChangeEvent();
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
/*     */   public Paint getGridBandPaint() {
/* 215 */     return this.gridBandPaint;
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
/*     */   public void setGridBandPaint(Paint paint) {
/* 227 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 228 */     this.gridBandPaint = paint;
/* 229 */     fireChangeEvent();
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
/*     */   public Paint getGridBandAlternatePaint() {
/* 243 */     return this.gridBandAlternatePaint;
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
/*     */   public void setGridBandAlternatePaint(Paint paint) {
/* 258 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 259 */     this.gridBandAlternatePaint = paint;
/* 260 */     fireChangeEvent();
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
/*     */   protected void selectAutoTickUnit(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/* 273 */     throw new UnsupportedOperationException();
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
/*     */   public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState) {
/* 297 */     AxisState info = new AxisState(cursor);
/* 298 */     if (isVisible()) {
/* 299 */       info = super.draw(g2, cursor, plotArea, dataArea, edge, plotState);
/*     */     }
/* 301 */     if (this.gridBandsVisible) {
/* 302 */       drawGridBands(g2, plotArea, dataArea, edge, info.getTicks());
/*     */     }
/* 304 */     return info;
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
/*     */   protected void drawGridBands(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, List ticks) {
/* 324 */     Shape savedClip = g2.getClip();
/* 325 */     g2.clip(dataArea);
/* 326 */     if (RectangleEdge.isTopOrBottom(edge)) {
/* 327 */       drawGridBandsHorizontal(g2, plotArea, dataArea, true, ticks);
/* 328 */     } else if (RectangleEdge.isLeftOrRight(edge)) {
/* 329 */       drawGridBandsVertical(g2, plotArea, dataArea, true, ticks);
/*     */     } 
/* 331 */     g2.setClip(savedClip);
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
/*     */   protected void drawGridBandsHorizontal(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, boolean firstGridBandIsDark, List ticks) {
/* 352 */     boolean currentGridBandIsDark = firstGridBandIsDark;
/* 353 */     double yy = dataArea.getY();
/*     */ 
/*     */ 
/*     */     
/* 357 */     double outlineStrokeWidth = 1.0D;
/* 358 */     Stroke outlineStroke = getPlot().getOutlineStroke();
/* 359 */     if (outlineStroke != null && outlineStroke instanceof BasicStroke) {
/* 360 */       outlineStrokeWidth = ((BasicStroke)outlineStroke).getLineWidth();
/*     */     }
/*     */     
/* 363 */     Iterator<ValueTick> iterator = ticks.iterator();
/*     */ 
/*     */     
/* 366 */     while (iterator.hasNext()) {
/* 367 */       ValueTick tick = iterator.next();
/* 368 */       double xx1 = valueToJava2D(tick.getValue() - 0.5D, dataArea, RectangleEdge.BOTTOM);
/*     */       
/* 370 */       double xx2 = valueToJava2D(tick.getValue() + 0.5D, dataArea, RectangleEdge.BOTTOM);
/*     */       
/* 372 */       if (currentGridBandIsDark) {
/* 373 */         g2.setPaint(this.gridBandPaint);
/*     */       } else {
/*     */         
/* 376 */         g2.setPaint(this.gridBandAlternatePaint);
/*     */       } 
/*     */ 
/*     */       
/* 380 */       Rectangle2D band = new Rectangle2D.Double(Math.min(xx1, xx2), yy + outlineStrokeWidth, Math.abs(xx2 - xx1), dataArea.getMaxY() - yy - outlineStrokeWidth);
/* 381 */       g2.fill(band);
/* 382 */       currentGridBandIsDark = !currentGridBandIsDark;
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
/*     */   protected void drawGridBandsVertical(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, boolean firstGridBandIsDark, List ticks) {
/* 404 */     boolean currentGridBandIsDark = firstGridBandIsDark;
/* 405 */     double xx = dataArea.getX();
/*     */ 
/*     */ 
/*     */     
/* 409 */     double outlineStrokeWidth = 1.0D;
/* 410 */     Stroke outlineStroke = getPlot().getOutlineStroke();
/* 411 */     if (outlineStroke != null && outlineStroke instanceof BasicStroke) {
/* 412 */       outlineStrokeWidth = ((BasicStroke)outlineStroke).getLineWidth();
/*     */     }
/*     */     
/* 415 */     Iterator<ValueTick> iterator = ticks.iterator();
/*     */ 
/*     */     
/* 418 */     while (iterator.hasNext()) {
/* 419 */       ValueTick tick = iterator.next();
/* 420 */       double yy1 = valueToJava2D(tick.getValue() + 0.5D, dataArea, RectangleEdge.LEFT);
/*     */       
/* 422 */       double yy2 = valueToJava2D(tick.getValue() - 0.5D, dataArea, RectangleEdge.LEFT);
/*     */       
/* 424 */       if (currentGridBandIsDark) {
/* 425 */         g2.setPaint(this.gridBandPaint);
/*     */       } else {
/*     */         
/* 428 */         g2.setPaint(this.gridBandAlternatePaint);
/*     */       } 
/*     */ 
/*     */       
/* 432 */       Rectangle2D band = new Rectangle2D.Double(xx + outlineStrokeWidth, Math.min(yy1, yy2), dataArea.getMaxX() - xx - outlineStrokeWidth, Math.abs(yy2 - yy1));
/* 433 */       g2.fill(band);
/* 434 */       currentGridBandIsDark = !currentGridBandIsDark;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void autoAdjustRange() {
/* 443 */     Plot plot = getPlot();
/* 444 */     if (plot == null) {
/*     */       return;
/*     */     }
/*     */     
/* 448 */     if (plot instanceof org.jfree.chart.plot.ValueAxisPlot) {
/*     */ 
/*     */       
/* 451 */       double upper = (this.symbols.size() - 1);
/* 452 */       double lower = 0.0D;
/* 453 */       double range = upper - lower;
/*     */ 
/*     */       
/* 456 */       double minRange = getAutoRangeMinimumSize();
/* 457 */       if (range < minRange) {
/* 458 */         upper = (upper + lower + minRange) / 2.0D;
/* 459 */         lower = (upper + lower - minRange) / 2.0D;
/*     */       } 
/*     */ 
/*     */       
/* 463 */       double upperMargin = 0.5D;
/* 464 */       double lowerMargin = 0.5D;
/*     */       
/* 466 */       if (getAutoRangeIncludesZero()) {
/* 467 */         if (getAutoRangeStickyZero()) {
/* 468 */           if (upper <= 0.0D) {
/* 469 */             upper = 0.0D;
/*     */           } else {
/* 471 */             upper += upperMargin;
/*     */           } 
/* 473 */           if (lower >= 0.0D) {
/* 474 */             lower = 0.0D;
/*     */           } else {
/* 476 */             lower -= lowerMargin;
/*     */           } 
/*     */         } else {
/* 479 */           upper = Math.max(0.0D, upper + upperMargin);
/* 480 */           lower = Math.min(0.0D, lower - lowerMargin);
/*     */         }
/*     */       
/* 483 */       } else if (getAutoRangeStickyZero()) {
/* 484 */         if (upper <= 0.0D) {
/* 485 */           upper = Math.min(0.0D, upper + upperMargin);
/*     */         } else {
/* 487 */           upper += upperMargin * range;
/*     */         } 
/* 489 */         if (lower >= 0.0D) {
/* 490 */           lower = Math.max(0.0D, lower - lowerMargin);
/*     */         } else {
/* 492 */           lower -= lowerMargin;
/*     */         } 
/*     */       } else {
/* 495 */         upper += upperMargin;
/* 496 */         lower -= lowerMargin;
/*     */       } 
/*     */       
/* 499 */       setRange(new Range(lower, upper), false, false);
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
/*     */   public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {
/* 517 */     List ticks = null;
/* 518 */     if (RectangleEdge.isTopOrBottom(edge)) {
/* 519 */       ticks = refreshTicksHorizontal(g2, dataArea, edge);
/* 520 */     } else if (RectangleEdge.isLeftOrRight(edge)) {
/* 521 */       ticks = refreshTicksVertical(g2, dataArea, edge);
/*     */     } 
/* 523 */     return ticks;
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
/*     */   protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/* 540 */     List<Tick> ticks = new ArrayList();
/*     */     
/* 542 */     Font tickLabelFont = getTickLabelFont();
/* 543 */     g2.setFont(tickLabelFont);
/*     */     
/* 545 */     double size = getTickUnit().getSize();
/* 546 */     int count = calculateVisibleTickCount();
/* 547 */     double lowestTickValue = calculateLowestVisibleTickValue();
/*     */     
/* 549 */     double previousDrawnTickLabelPos = 0.0D;
/* 550 */     double previousDrawnTickLabelLength = 0.0D;
/*     */     
/* 552 */     if (count <= 500) {
/* 553 */       for (int i = 0; i < count; i++) {
/* 554 */         String tickLabel; TextAnchor anchor, rotationAnchor; double currentTickValue = lowestTickValue + i * size;
/* 555 */         double xx = valueToJava2D(currentTickValue, dataArea, edge);
/*     */         
/* 557 */         NumberFormat formatter = getNumberFormatOverride();
/* 558 */         if (formatter != null) {
/* 559 */           tickLabel = formatter.format(currentTickValue);
/*     */         } else {
/*     */           
/* 562 */           tickLabel = valueToString(currentTickValue);
/*     */         } 
/*     */ 
/*     */         
/* 566 */         Rectangle2D bounds = TextUtilities.getTextBounds(tickLabel, g2, g2
/* 567 */             .getFontMetrics());
/*     */         
/* 569 */         double tickLabelLength = isVerticalTickLabels() ? bounds.getHeight() : bounds.getWidth();
/* 570 */         boolean tickLabelsOverlapping = false;
/* 571 */         if (i > 0) {
/* 572 */           double avgTickLabelLength = (previousDrawnTickLabelLength + tickLabelLength) / 2.0D;
/*     */           
/* 574 */           if (Math.abs(xx - previousDrawnTickLabelPos) < avgTickLabelLength)
/*     */           {
/* 576 */             tickLabelsOverlapping = true;
/*     */           }
/*     */         } 
/* 579 */         if (tickLabelsOverlapping) {
/* 580 */           tickLabel = "";
/*     */         }
/*     */         else {
/*     */           
/* 584 */           previousDrawnTickLabelPos = xx;
/* 585 */           previousDrawnTickLabelLength = tickLabelLength;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 590 */         double angle = 0.0D;
/* 591 */         if (isVerticalTickLabels()) {
/* 592 */           anchor = TextAnchor.CENTER_RIGHT;
/* 593 */           rotationAnchor = TextAnchor.CENTER_RIGHT;
/* 594 */           if (edge == RectangleEdge.TOP) {
/* 595 */             angle = 1.5707963267948966D;
/*     */           } else {
/*     */             
/* 598 */             angle = -1.5707963267948966D;
/*     */           }
/*     */         
/*     */         }
/* 602 */         else if (edge == RectangleEdge.TOP) {
/* 603 */           anchor = TextAnchor.BOTTOM_CENTER;
/* 604 */           rotationAnchor = TextAnchor.BOTTOM_CENTER;
/*     */         } else {
/*     */           
/* 607 */           anchor = TextAnchor.TOP_CENTER;
/* 608 */           rotationAnchor = TextAnchor.TOP_CENTER;
/*     */         } 
/*     */         
/* 611 */         Tick tick = new NumberTick(new Double(currentTickValue), tickLabel, anchor, rotationAnchor, angle);
/*     */         
/* 613 */         ticks.add(tick);
/*     */       } 
/*     */     }
/* 616 */     return ticks;
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
/*     */   protected List refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/* 634 */     List<Tick> ticks = new ArrayList();
/*     */     
/* 636 */     Font tickLabelFont = getTickLabelFont();
/* 637 */     g2.setFont(tickLabelFont);
/*     */     
/* 639 */     double size = getTickUnit().getSize();
/* 640 */     int count = calculateVisibleTickCount();
/* 641 */     double lowestTickValue = calculateLowestVisibleTickValue();
/*     */     
/* 643 */     double previousDrawnTickLabelPos = 0.0D;
/* 644 */     double previousDrawnTickLabelLength = 0.0D;
/*     */     
/* 646 */     if (count <= 500) {
/* 647 */       for (int i = 0; i < count; i++) {
/* 648 */         String tickLabel; TextAnchor anchor, rotationAnchor; double currentTickValue = lowestTickValue + i * size;
/* 649 */         double yy = valueToJava2D(currentTickValue, dataArea, edge);
/*     */         
/* 651 */         NumberFormat formatter = getNumberFormatOverride();
/* 652 */         if (formatter != null) {
/* 653 */           tickLabel = formatter.format(currentTickValue);
/*     */         } else {
/*     */           
/* 656 */           tickLabel = valueToString(currentTickValue);
/*     */         } 
/*     */ 
/*     */         
/* 660 */         Rectangle2D bounds = TextUtilities.getTextBounds(tickLabel, g2, g2
/* 661 */             .getFontMetrics());
/*     */         
/* 663 */         double tickLabelLength = isVerticalTickLabels() ? bounds.getWidth() : bounds.getHeight();
/* 664 */         boolean tickLabelsOverlapping = false;
/* 665 */         if (i > 0) {
/* 666 */           double avgTickLabelLength = (previousDrawnTickLabelLength + tickLabelLength) / 2.0D;
/*     */           
/* 668 */           if (Math.abs(yy - previousDrawnTickLabelPos) < avgTickLabelLength)
/*     */           {
/* 670 */             tickLabelsOverlapping = true;
/*     */           }
/*     */         } 
/* 673 */         if (tickLabelsOverlapping) {
/* 674 */           tickLabel = "";
/*     */         }
/*     */         else {
/*     */           
/* 678 */           previousDrawnTickLabelPos = yy;
/* 679 */           previousDrawnTickLabelLength = tickLabelLength;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 684 */         double angle = 0.0D;
/* 685 */         if (isVerticalTickLabels()) {
/* 686 */           anchor = TextAnchor.BOTTOM_CENTER;
/* 687 */           rotationAnchor = TextAnchor.BOTTOM_CENTER;
/* 688 */           if (edge == RectangleEdge.LEFT) {
/* 689 */             angle = -1.5707963267948966D;
/*     */           } else {
/*     */             
/* 692 */             angle = 1.5707963267948966D;
/*     */           }
/*     */         
/*     */         }
/* 696 */         else if (edge == RectangleEdge.LEFT) {
/* 697 */           anchor = TextAnchor.CENTER_RIGHT;
/* 698 */           rotationAnchor = TextAnchor.CENTER_RIGHT;
/*     */         } else {
/*     */           
/* 701 */           anchor = TextAnchor.CENTER_LEFT;
/* 702 */           rotationAnchor = TextAnchor.CENTER_LEFT;
/*     */         } 
/*     */         
/* 705 */         Tick tick = new NumberTick(new Double(currentTickValue), tickLabel, anchor, rotationAnchor, angle);
/*     */         
/* 707 */         ticks.add(tick);
/*     */       } 
/*     */     }
/* 710 */     return ticks;
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
/*     */   public String valueToString(double value) {
/*     */     String str;
/*     */     try {
/* 724 */       str = this.symbols.get((int)value);
/*     */     }
/* 726 */     catch (IndexOutOfBoundsException ex) {
/* 727 */       str = "";
/*     */     } 
/* 729 */     return str;
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
/* 741 */     if (obj == this) {
/* 742 */       return true;
/*     */     }
/* 744 */     if (!(obj instanceof SymbolAxis)) {
/* 745 */       return false;
/*     */     }
/* 747 */     SymbolAxis that = (SymbolAxis)obj;
/* 748 */     if (!this.symbols.equals(that.symbols)) {
/* 749 */       return false;
/*     */     }
/* 751 */     if (this.gridBandsVisible != that.gridBandsVisible) {
/* 752 */       return false;
/*     */     }
/* 754 */     if (!PaintUtilities.equal(this.gridBandPaint, that.gridBandPaint)) {
/* 755 */       return false;
/*     */     }
/* 757 */     if (!PaintUtilities.equal(this.gridBandAlternatePaint, that.gridBandAlternatePaint))
/*     */     {
/* 759 */       return false;
/*     */     }
/* 761 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 772 */     stream.defaultWriteObject();
/* 773 */     SerialUtilities.writePaint(this.gridBandPaint, stream);
/* 774 */     SerialUtilities.writePaint(this.gridBandAlternatePaint, stream);
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 787 */     stream.defaultReadObject();
/* 788 */     this.gridBandPaint = SerialUtilities.readPaint(stream);
/* 789 */     this.gridBandAlternatePaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/SymbolAxis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */