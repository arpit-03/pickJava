/*      */ package org.jfree.chart.renderer.xy;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.GeneralPath;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.util.Collections;
/*      */ import java.util.LinkedList;
/*      */ import org.jfree.chart.LegendItem;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.entity.ChartEntity;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.entity.XYItemEntity;
/*      */ import org.jfree.chart.event.RendererChangeEvent;
/*      */ import org.jfree.chart.labels.XYToolTipGenerator;
/*      */ import org.jfree.chart.plot.CrosshairState;
/*      */ import org.jfree.chart.plot.PlotOrientation;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.plot.XYPlot;
/*      */ import org.jfree.chart.urls.XYURLGenerator;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.xy.XYDataset;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.util.PaintUtilities;
/*      */ import org.jfree.util.PublicCloneable;
/*      */ import org.jfree.util.ShapeUtilities;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XYDifferenceRenderer
/*      */   extends AbstractXYItemRenderer
/*      */   implements XYItemRenderer, PublicCloneable
/*      */ {
/*      */   private static final long serialVersionUID = -8447915602375584857L;
/*      */   private transient Paint positivePaint;
/*      */   private transient Paint negativePaint;
/*      */   private boolean shapesVisible;
/*      */   private transient Shape legendLine;
/*      */   private boolean roundXCoordinates;
/*      */   
/*      */   public XYDifferenceRenderer() {
/*  160 */     this(Color.green, Color.red, false);
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
/*      */   public XYDifferenceRenderer(Paint positivePaint, Paint negativePaint, boolean shapes) {
/*  174 */     ParamChecks.nullNotPermitted(positivePaint, "positivePaint");
/*  175 */     ParamChecks.nullNotPermitted(negativePaint, "negativePaint");
/*  176 */     this.positivePaint = positivePaint;
/*  177 */     this.negativePaint = negativePaint;
/*  178 */     this.shapesVisible = shapes;
/*  179 */     this.legendLine = new Line2D.Double(-7.0D, 0.0D, 7.0D, 0.0D);
/*  180 */     this.roundXCoordinates = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getPositivePaint() {
/*  191 */     return this.positivePaint;
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
/*      */   public void setPositivePaint(Paint paint) {
/*  203 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  204 */     this.positivePaint = paint;
/*  205 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getNegativePaint() {
/*  216 */     return this.negativePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNegativePaint(Paint paint) {
/*  227 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  228 */     this.negativePaint = paint;
/*  229 */     notifyListeners(new RendererChangeEvent(this));
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
/*      */   public boolean getShapesVisible() {
/*  241 */     return this.shapesVisible;
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
/*      */   public void setShapesVisible(boolean flag) {
/*  254 */     this.shapesVisible = flag;
/*  255 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape getLegendLine() {
/*  266 */     return this.legendLine;
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
/*      */   public void setLegendLine(Shape line) {
/*  278 */     ParamChecks.nullNotPermitted(line, "line");
/*  279 */     this.legendLine = line;
/*  280 */     fireChangeEvent();
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
/*      */   public boolean getRoundXCoordinates() {
/*  294 */     return this.roundXCoordinates;
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
/*      */   public void setRoundXCoordinates(boolean round) {
/*  309 */     this.roundXCoordinates = round;
/*  310 */     fireChangeEvent();
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
/*      */   public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset data, PlotRenderingInfo info) {
/*  332 */     XYItemRendererState state = super.initialise(g2, dataArea, plot, data, info);
/*      */     
/*  334 */     state.setProcessVisibleItemsOnly(false);
/*  335 */     return state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPassCount() {
/*  346 */     return 2;
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
/*      */   
/*      */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/*  373 */     if (pass == 0) {
/*  374 */       drawItemPass0(g2, dataArea, info, plot, domainAxis, rangeAxis, dataset, series, item, crosshairState);
/*      */     
/*      */     }
/*  377 */     else if (pass == 1) {
/*  378 */       drawItemPass1(g2, dataArea, info, plot, domainAxis, rangeAxis, dataset, series, item, crosshairState);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawItemPass0(Graphics2D x_graphics, Rectangle2D x_dataArea, PlotRenderingInfo x_info, XYPlot x_plot, ValueAxis x_domainAxis, ValueAxis x_rangeAxis, XYDataset x_dataset, int x_series, int x_item, CrosshairState x_crosshairState) {
/*  411 */     if (0 != x_series || 0 != x_item) {
/*      */       return;
/*      */     }
/*      */     
/*  415 */     boolean b_impliedZeroSubtrahend = (1 == x_dataset.getSeriesCount());
/*      */ 
/*      */     
/*  418 */     if (isEitherSeriesDegenerate(x_dataset, b_impliedZeroSubtrahend)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  423 */     if (!b_impliedZeroSubtrahend && areSeriesDisjoint(x_dataset)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  428 */     LinkedList<Double> l_minuendXs = new LinkedList();
/*  429 */     LinkedList<Double> l_minuendYs = new LinkedList();
/*  430 */     LinkedList<Double> l_subtrahendXs = new LinkedList();
/*  431 */     LinkedList<Double> l_subtrahendYs = new LinkedList();
/*  432 */     LinkedList<Double> l_polygonXs = new LinkedList();
/*  433 */     LinkedList<Double> l_polygonYs = new LinkedList();
/*      */ 
/*      */     
/*  436 */     int l_minuendItem = 0;
/*  437 */     int l_minuendItemCount = x_dataset.getItemCount(0);
/*  438 */     Double l_minuendCurX = null;
/*  439 */     Double l_minuendNextX = null;
/*  440 */     Double l_minuendCurY = null;
/*  441 */     Double l_minuendNextY = null;
/*  442 */     double l_minuendMaxY = Double.NEGATIVE_INFINITY;
/*  443 */     double l_minuendMinY = Double.POSITIVE_INFINITY;
/*      */     
/*  445 */     int l_subtrahendItem = 0;
/*  446 */     int l_subtrahendItemCount = 0;
/*  447 */     Double l_subtrahendCurX = null;
/*  448 */     Double l_subtrahendNextX = null;
/*  449 */     Double l_subtrahendCurY = null;
/*  450 */     Double l_subtrahendNextY = null;
/*  451 */     double l_subtrahendMaxY = Double.NEGATIVE_INFINITY;
/*  452 */     double l_subtrahendMinY = Double.POSITIVE_INFINITY;
/*      */ 
/*      */     
/*  455 */     if (b_impliedZeroSubtrahend) {
/*  456 */       l_subtrahendItem = 0;
/*  457 */       l_subtrahendItemCount = 2;
/*  458 */       l_subtrahendCurX = new Double(x_dataset.getXValue(0, 0));
/*  459 */       l_subtrahendNextX = new Double(x_dataset.getXValue(0, l_minuendItemCount - 1));
/*      */       
/*  461 */       l_subtrahendCurY = new Double(0.0D);
/*  462 */       l_subtrahendNextY = new Double(0.0D);
/*  463 */       l_subtrahendMaxY = 0.0D;
/*  464 */       l_subtrahendMinY = 0.0D;
/*      */       
/*  466 */       l_subtrahendXs.add(l_subtrahendCurX);
/*  467 */       l_subtrahendYs.add(l_subtrahendCurY);
/*      */     } else {
/*      */       
/*  470 */       l_subtrahendItemCount = x_dataset.getItemCount(1);
/*      */     } 
/*      */     
/*  473 */     boolean b_minuendDone = false;
/*  474 */     boolean b_minuendAdvanced = true;
/*  475 */     boolean b_minuendAtIntersect = false;
/*  476 */     boolean b_minuendFastForward = false;
/*  477 */     boolean b_subtrahendDone = false;
/*  478 */     boolean b_subtrahendAdvanced = true;
/*  479 */     boolean b_subtrahendAtIntersect = false;
/*  480 */     boolean b_subtrahendFastForward = false;
/*  481 */     boolean b_colinear = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  486 */     double l_x1 = 0.0D, l_y1 = 0.0D;
/*  487 */     double l_x2 = 0.0D, l_y2 = 0.0D;
/*  488 */     double l_x3 = 0.0D, l_y3 = 0.0D;
/*  489 */     double l_x4 = 0.0D, l_y4 = 0.0D;
/*      */ 
/*      */     
/*  492 */     boolean b_fastForwardDone = false;
/*  493 */     while (!b_fastForwardDone) {
/*      */       
/*  495 */       l_x1 = x_dataset.getXValue(0, l_minuendItem);
/*  496 */       l_y1 = x_dataset.getYValue(0, l_minuendItem);
/*  497 */       l_x2 = x_dataset.getXValue(0, l_minuendItem + 1);
/*  498 */       l_y2 = x_dataset.getYValue(0, l_minuendItem + 1);
/*      */       
/*  500 */       l_minuendCurX = new Double(l_x1);
/*  501 */       l_minuendCurY = new Double(l_y1);
/*  502 */       l_minuendNextX = new Double(l_x2);
/*  503 */       l_minuendNextY = new Double(l_y2);
/*      */       
/*  505 */       if (b_impliedZeroSubtrahend) {
/*  506 */         l_x3 = l_subtrahendCurX.doubleValue();
/*  507 */         l_y3 = l_subtrahendCurY.doubleValue();
/*  508 */         l_x4 = l_subtrahendNextX.doubleValue();
/*  509 */         l_y4 = l_subtrahendNextY.doubleValue();
/*      */       } else {
/*      */         
/*  512 */         l_x3 = x_dataset.getXValue(1, l_subtrahendItem);
/*  513 */         l_y3 = x_dataset.getYValue(1, l_subtrahendItem);
/*  514 */         l_x4 = x_dataset.getXValue(1, l_subtrahendItem + 1);
/*  515 */         l_y4 = x_dataset.getYValue(1, l_subtrahendItem + 1);
/*      */         
/*  517 */         l_subtrahendCurX = new Double(l_x3);
/*  518 */         l_subtrahendCurY = new Double(l_y3);
/*  519 */         l_subtrahendNextX = new Double(l_x4);
/*  520 */         l_subtrahendNextY = new Double(l_y4);
/*      */       } 
/*      */       
/*  523 */       if (l_x2 <= l_x3) {
/*      */         
/*  525 */         l_minuendItem++;
/*  526 */         b_minuendFastForward = true;
/*      */         
/*      */         continue;
/*      */       } 
/*  530 */       if (l_x4 <= l_x1) {
/*      */         
/*  532 */         l_subtrahendItem++;
/*  533 */         b_subtrahendFastForward = true;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  538 */       if (l_x3 < l_x1 && l_x1 < l_x4) {
/*      */         
/*  540 */         double l_slope = (l_y4 - l_y3) / (l_x4 - l_x3);
/*  541 */         l_subtrahendCurX = l_minuendCurX;
/*  542 */         l_subtrahendCurY = new Double(l_slope * l_x1 + l_y3 - l_slope * l_x3);
/*      */ 
/*      */         
/*  545 */         l_subtrahendXs.add(l_subtrahendCurX);
/*  546 */         l_subtrahendYs.add(l_subtrahendCurY);
/*      */       } 
/*      */       
/*  549 */       if (l_x1 < l_x3 && l_x3 < l_x2) {
/*      */         
/*  551 */         double l_slope = (l_y2 - l_y1) / (l_x2 - l_x1);
/*  552 */         l_minuendCurX = l_subtrahendCurX;
/*  553 */         l_minuendCurY = new Double(l_slope * l_x3 + l_y1 - l_slope * l_x1);
/*      */ 
/*      */         
/*  556 */         l_minuendXs.add(l_minuendCurX);
/*  557 */         l_minuendYs.add(l_minuendCurY);
/*      */       } 
/*      */       
/*  560 */       l_minuendMaxY = l_minuendCurY.doubleValue();
/*  561 */       l_minuendMinY = l_minuendCurY.doubleValue();
/*  562 */       l_subtrahendMaxY = l_subtrahendCurY.doubleValue();
/*  563 */       l_subtrahendMinY = l_subtrahendCurY.doubleValue();
/*      */       
/*  565 */       b_fastForwardDone = true;
/*      */     } 
/*      */ 
/*      */     
/*  569 */     while (!b_minuendDone && !b_subtrahendDone) {
/*  570 */       if (!b_minuendDone && !b_minuendFastForward && b_minuendAdvanced) {
/*  571 */         l_x1 = x_dataset.getXValue(0, l_minuendItem);
/*  572 */         l_y1 = x_dataset.getYValue(0, l_minuendItem);
/*  573 */         l_minuendCurX = new Double(l_x1);
/*  574 */         l_minuendCurY = new Double(l_y1);
/*      */         
/*  576 */         if (!b_minuendAtIntersect) {
/*  577 */           l_minuendXs.add(l_minuendCurX);
/*  578 */           l_minuendYs.add(l_minuendCurY);
/*      */         } 
/*      */         
/*  581 */         l_minuendMaxY = Math.max(l_minuendMaxY, l_y1);
/*  582 */         l_minuendMinY = Math.min(l_minuendMinY, l_y1);
/*      */         
/*  584 */         l_x2 = x_dataset.getXValue(0, l_minuendItem + 1);
/*  585 */         l_y2 = x_dataset.getYValue(0, l_minuendItem + 1);
/*  586 */         l_minuendNextX = new Double(l_x2);
/*  587 */         l_minuendNextY = new Double(l_y2);
/*      */       } 
/*      */ 
/*      */       
/*  591 */       if (!b_impliedZeroSubtrahend && !b_subtrahendDone && !b_subtrahendFastForward && b_subtrahendAdvanced) {
/*      */         
/*  593 */         l_x3 = x_dataset.getXValue(1, l_subtrahendItem);
/*  594 */         l_y3 = x_dataset.getYValue(1, l_subtrahendItem);
/*  595 */         l_subtrahendCurX = new Double(l_x3);
/*  596 */         l_subtrahendCurY = new Double(l_y3);
/*      */         
/*  598 */         if (!b_subtrahendAtIntersect) {
/*  599 */           l_subtrahendXs.add(l_subtrahendCurX);
/*  600 */           l_subtrahendYs.add(l_subtrahendCurY);
/*      */         } 
/*      */         
/*  603 */         l_subtrahendMaxY = Math.max(l_subtrahendMaxY, l_y3);
/*  604 */         l_subtrahendMinY = Math.min(l_subtrahendMinY, l_y3);
/*      */         
/*  606 */         l_x4 = x_dataset.getXValue(1, l_subtrahendItem + 1);
/*  607 */         l_y4 = x_dataset.getYValue(1, l_subtrahendItem + 1);
/*  608 */         l_subtrahendNextX = new Double(l_x4);
/*  609 */         l_subtrahendNextY = new Double(l_y4);
/*      */       } 
/*      */ 
/*      */       
/*  613 */       b_minuendFastForward = false;
/*  614 */       b_subtrahendFastForward = false;
/*      */       
/*  616 */       Double l_intersectX = null;
/*  617 */       Double l_intersectY = null;
/*  618 */       boolean b_intersect = false;
/*      */       
/*  620 */       b_minuendAtIntersect = false;
/*  621 */       b_subtrahendAtIntersect = false;
/*      */ 
/*      */       
/*  624 */       if (l_x2 == l_x4 && l_y2 == l_y4) {
/*      */         
/*  626 */         if (l_x1 == l_x3 && l_y1 == l_y3) {
/*  627 */           b_colinear = true;
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  632 */           l_intersectX = new Double(l_x2);
/*  633 */           l_intersectY = new Double(l_y2);
/*      */           
/*  635 */           b_intersect = true;
/*  636 */           b_minuendAtIntersect = true;
/*  637 */           b_subtrahendAtIntersect = true;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  642 */         double l_denominator = (l_y4 - l_y3) * (l_x2 - l_x1) - (l_x4 - l_x3) * (l_y2 - l_y1);
/*      */ 
/*      */ 
/*      */         
/*  646 */         double l_deltaY = l_y1 - l_y3;
/*  647 */         double l_deltaX = l_x1 - l_x3;
/*      */ 
/*      */         
/*  650 */         double l_numeratorA = (l_x4 - l_x3) * l_deltaY - (l_y4 - l_y3) * l_deltaX;
/*      */         
/*  652 */         double l_numeratorB = (l_x2 - l_x1) * l_deltaY - (l_y2 - l_y1) * l_deltaX;
/*      */ 
/*      */ 
/*      */         
/*  656 */         if (0.0D == l_numeratorA && 0.0D == l_numeratorB && 0.0D == l_denominator) {
/*      */           
/*  658 */           b_colinear = true;
/*      */ 
/*      */         
/*      */         }
/*  662 */         else if (b_colinear) {
/*      */           
/*  664 */           l_minuendXs.clear();
/*  665 */           l_minuendYs.clear();
/*  666 */           l_subtrahendXs.clear();
/*  667 */           l_subtrahendYs.clear();
/*  668 */           l_polygonXs.clear();
/*  669 */           l_polygonYs.clear();
/*      */           
/*  671 */           b_colinear = false;
/*      */ 
/*      */           
/*  674 */           boolean b_useMinuend = (l_x3 <= l_x1 && l_x1 <= l_x4);
/*      */           
/*  676 */           l_polygonXs.add(b_useMinuend ? l_minuendCurX : l_subtrahendCurX);
/*      */           
/*  678 */           l_polygonYs.add(b_useMinuend ? l_minuendCurY : l_subtrahendCurY);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  684 */         double l_slopeA = l_numeratorA / l_denominator;
/*  685 */         double l_slopeB = l_numeratorB / l_denominator;
/*      */ 
/*      */         
/*  688 */         boolean b_vertical = (l_x1 == l_x2 && l_x3 == l_x4 && l_x2 == l_x4);
/*      */ 
/*      */         
/*  691 */         if ((0.0D < l_slopeA && l_slopeA <= 1.0D && 0.0D < l_slopeB && l_slopeB <= 1.0D) || b_vertical) {
/*      */           double l_xi, l_yi;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  697 */           if (b_vertical) {
/*  698 */             b_colinear = false;
/*  699 */             l_xi = l_x2;
/*  700 */             l_yi = l_x4;
/*      */           } else {
/*      */             
/*  703 */             l_xi = l_x1 + l_slopeA * (l_x2 - l_x1);
/*  704 */             l_yi = l_y1 + l_slopeA * (l_y2 - l_y1);
/*      */           } 
/*      */           
/*  707 */           l_intersectX = new Double(l_xi);
/*  708 */           l_intersectY = new Double(l_yi);
/*  709 */           b_intersect = true;
/*  710 */           b_minuendAtIntersect = (l_xi == l_x2 && l_yi == l_y2);
/*      */           
/*  712 */           b_subtrahendAtIntersect = (l_xi == l_x4 && l_yi == l_y4);
/*      */ 
/*      */ 
/*      */           
/*  716 */           l_minuendCurX = l_intersectX;
/*  717 */           l_minuendCurY = l_intersectY;
/*  718 */           l_subtrahendCurX = l_intersectX;
/*  719 */           l_subtrahendCurY = l_intersectY;
/*      */         } 
/*      */       } 
/*      */       
/*  723 */       if (b_intersect) {
/*      */ 
/*      */         
/*  726 */         l_polygonXs.addAll(l_minuendXs);
/*  727 */         l_polygonYs.addAll(l_minuendYs);
/*      */ 
/*      */         
/*  730 */         l_polygonXs.add(l_intersectX);
/*  731 */         l_polygonYs.add(l_intersectY);
/*      */ 
/*      */         
/*  734 */         Collections.reverse(l_subtrahendXs);
/*  735 */         Collections.reverse(l_subtrahendYs);
/*  736 */         l_polygonXs.addAll(l_subtrahendXs);
/*  737 */         l_polygonYs.addAll(l_subtrahendYs);
/*      */ 
/*      */         
/*  740 */         boolean bool = (l_subtrahendMaxY <= l_minuendMaxY && l_subtrahendMinY <= l_minuendMinY);
/*      */         
/*  742 */         createPolygon(x_graphics, x_dataArea, x_plot, x_domainAxis, x_rangeAxis, bool, l_polygonXs, l_polygonYs);
/*      */ 
/*      */ 
/*      */         
/*  746 */         l_minuendXs.clear();
/*  747 */         l_minuendYs.clear();
/*  748 */         l_subtrahendXs.clear();
/*  749 */         l_subtrahendYs.clear();
/*  750 */         l_polygonXs.clear();
/*  751 */         l_polygonYs.clear();
/*      */ 
/*      */         
/*  754 */         double l_y = l_intersectY.doubleValue();
/*  755 */         l_minuendMaxY = l_y;
/*  756 */         l_subtrahendMaxY = l_y;
/*  757 */         l_minuendMinY = l_y;
/*  758 */         l_subtrahendMinY = l_y;
/*      */ 
/*      */         
/*  761 */         l_polygonXs.add(l_intersectX);
/*  762 */         l_polygonYs.add(l_intersectY);
/*      */       } 
/*      */ 
/*      */       
/*  766 */       if (l_x2 <= l_x4) {
/*  767 */         l_minuendItem++;
/*  768 */         b_minuendAdvanced = true;
/*      */       } else {
/*      */         
/*  771 */         b_minuendAdvanced = false;
/*      */       } 
/*      */ 
/*      */       
/*  775 */       if (l_x4 <= l_x2) {
/*  776 */         l_subtrahendItem++;
/*  777 */         b_subtrahendAdvanced = true;
/*      */       } else {
/*      */         
/*  780 */         b_subtrahendAdvanced = false;
/*      */       } 
/*      */       
/*  783 */       b_minuendDone = (l_minuendItem == l_minuendItemCount - 1);
/*  784 */       b_subtrahendDone = (l_subtrahendItem == l_subtrahendItemCount - 1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  789 */     if (b_minuendDone && l_x3 < l_x2 && l_x2 < l_x4) {
/*      */       
/*  791 */       double l_slope = (l_y4 - l_y3) / (l_x4 - l_x3);
/*  792 */       l_subtrahendNextX = l_minuendNextX;
/*  793 */       l_subtrahendNextY = new Double(l_slope * l_x2 + l_y3 - l_slope * l_x3);
/*      */     } 
/*      */ 
/*      */     
/*  797 */     if (b_subtrahendDone && l_x1 < l_x4 && l_x4 < l_x2) {
/*      */       
/*  799 */       double l_slope = (l_y2 - l_y1) / (l_x2 - l_x1);
/*  800 */       l_minuendNextX = l_subtrahendNextX;
/*  801 */       l_minuendNextY = new Double(l_slope * l_x4 + l_y1 - l_slope * l_x1);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  807 */     l_minuendMaxY = Math.max(l_minuendMaxY, l_minuendNextY
/*  808 */         .doubleValue());
/*  809 */     l_subtrahendMaxY = Math.max(l_subtrahendMaxY, l_subtrahendNextY
/*  810 */         .doubleValue());
/*  811 */     l_minuendMinY = Math.min(l_minuendMinY, l_minuendNextY
/*  812 */         .doubleValue());
/*  813 */     l_subtrahendMinY = Math.min(l_subtrahendMinY, l_subtrahendNextY
/*  814 */         .doubleValue());
/*      */ 
/*      */     
/*  817 */     l_minuendXs.add(l_minuendNextX);
/*  818 */     l_minuendYs.add(l_minuendNextY);
/*  819 */     l_subtrahendXs.add(l_subtrahendNextX);
/*  820 */     l_subtrahendYs.add(l_subtrahendNextY);
/*      */ 
/*      */ 
/*      */     
/*  824 */     l_polygonXs.addAll(l_minuendXs);
/*  825 */     l_polygonYs.addAll(l_minuendYs);
/*      */ 
/*      */     
/*  828 */     Collections.reverse(l_subtrahendXs);
/*  829 */     Collections.reverse(l_subtrahendYs);
/*  830 */     l_polygonXs.addAll(l_subtrahendXs);
/*  831 */     l_polygonYs.addAll(l_subtrahendYs);
/*      */ 
/*      */     
/*  834 */     boolean b_positive = (l_subtrahendMaxY <= l_minuendMaxY && l_subtrahendMinY <= l_minuendMinY);
/*      */     
/*  836 */     createPolygon(x_graphics, x_dataArea, x_plot, x_domainAxis, x_rangeAxis, b_positive, l_polygonXs, l_polygonYs);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawItemPass1(Graphics2D x_graphics, Rectangle2D x_dataArea, PlotRenderingInfo x_info, XYPlot x_plot, ValueAxis x_domainAxis, ValueAxis x_rangeAxis, XYDataset x_dataset, int x_series, int x_item, CrosshairState x_crosshairState) {
/*  869 */     Shape l_entityArea = null;
/*  870 */     EntityCollection l_entities = null;
/*  871 */     if (null != x_info) {
/*  872 */       l_entities = x_info.getOwner().getEntityCollection();
/*      */     }
/*      */     
/*  875 */     Paint l_seriesPaint = getItemPaint(x_series, x_item);
/*  876 */     Stroke l_seriesStroke = getItemStroke(x_series, x_item);
/*  877 */     x_graphics.setPaint(l_seriesPaint);
/*  878 */     x_graphics.setStroke(l_seriesStroke);
/*      */     
/*  880 */     PlotOrientation l_orientation = x_plot.getOrientation();
/*  881 */     RectangleEdge l_domainAxisLocation = x_plot.getDomainAxisEdge();
/*  882 */     RectangleEdge l_rangeAxisLocation = x_plot.getRangeAxisEdge();
/*      */     
/*  884 */     double l_x0 = x_dataset.getXValue(x_series, x_item);
/*  885 */     double l_y0 = x_dataset.getYValue(x_series, x_item);
/*  886 */     double l_x1 = x_domainAxis.valueToJava2D(l_x0, x_dataArea, l_domainAxisLocation);
/*      */     
/*  888 */     double l_y1 = x_rangeAxis.valueToJava2D(l_y0, x_dataArea, l_rangeAxisLocation);
/*      */ 
/*      */     
/*  891 */     if (getShapesVisible()) {
/*  892 */       Shape l_shape = getItemShape(x_series, x_item);
/*  893 */       if (l_orientation == PlotOrientation.HORIZONTAL) {
/*  894 */         l_shape = ShapeUtilities.createTranslatedShape(l_shape, l_y1, l_x1);
/*      */       }
/*      */       else {
/*      */         
/*  898 */         l_shape = ShapeUtilities.createTranslatedShape(l_shape, l_x1, l_y1);
/*      */       } 
/*      */       
/*  901 */       if (l_shape.intersects(x_dataArea)) {
/*  902 */         x_graphics.setPaint(getItemPaint(x_series, x_item));
/*  903 */         x_graphics.fill(l_shape);
/*      */       } 
/*  905 */       l_entityArea = l_shape;
/*      */     } 
/*      */ 
/*      */     
/*  909 */     if (null != l_entities) {
/*  910 */       if (null == l_entityArea) {
/*  911 */         l_entityArea = new Rectangle2D.Double(l_x1 - 2.0D, l_y1 - 2.0D, 4.0D, 4.0D);
/*      */       }
/*      */       
/*  914 */       String l_tip = null;
/*  915 */       XYToolTipGenerator l_tipGenerator = getToolTipGenerator(x_series, x_item);
/*      */       
/*  917 */       if (null != l_tipGenerator) {
/*  918 */         l_tip = l_tipGenerator.generateToolTip(x_dataset, x_series, x_item);
/*      */       }
/*      */       
/*  921 */       String l_url = null;
/*  922 */       XYURLGenerator l_urlGenerator = getURLGenerator();
/*  923 */       if (null != l_urlGenerator) {
/*  924 */         l_url = l_urlGenerator.generateURL(x_dataset, x_series, x_item);
/*      */       }
/*      */       
/*  927 */       XYItemEntity l_entity = new XYItemEntity(l_entityArea, x_dataset, x_series, x_item, l_tip, l_url);
/*      */       
/*  929 */       l_entities.add((ChartEntity)l_entity);
/*      */     } 
/*      */ 
/*      */     
/*  933 */     if (isItemLabelVisible(x_series, x_item)) {
/*  934 */       drawItemLabel(x_graphics, l_orientation, x_dataset, x_series, x_item, l_x1, l_y1, (l_y1 < 0.0D));
/*      */     }
/*      */ 
/*      */     
/*  938 */     int l_domainAxisIndex = x_plot.getDomainAxisIndex(x_domainAxis);
/*  939 */     int l_rangeAxisIndex = x_plot.getRangeAxisIndex(x_rangeAxis);
/*  940 */     updateCrosshairValues(x_crosshairState, l_x0, l_y0, l_domainAxisIndex, l_rangeAxisIndex, l_x1, l_y1, l_orientation);
/*      */ 
/*      */     
/*  943 */     if (0 == x_item) {
/*      */       return;
/*      */     }
/*      */     
/*  947 */     double l_x2 = x_domainAxis.valueToJava2D(x_dataset.getXValue(x_series, x_item - 1), x_dataArea, l_domainAxisLocation);
/*      */     
/*  949 */     double l_y2 = x_rangeAxis.valueToJava2D(x_dataset.getYValue(x_series, x_item - 1), x_dataArea, l_rangeAxisLocation);
/*      */ 
/*      */     
/*  952 */     Line2D l_line = null;
/*  953 */     if (PlotOrientation.HORIZONTAL == l_orientation) {
/*  954 */       l_line = new Line2D.Double(l_y1, l_x1, l_y2, l_x2);
/*      */     }
/*  956 */     else if (PlotOrientation.VERTICAL == l_orientation) {
/*  957 */       l_line = new Line2D.Double(l_x1, l_y1, l_x2, l_y2);
/*      */     } 
/*      */     
/*  960 */     if (null != l_line && l_line.intersects(x_dataArea)) {
/*  961 */       x_graphics.setPaint(getItemPaint(x_series, x_item));
/*  962 */       x_graphics.setStroke(getItemStroke(x_series, x_item));
/*  963 */       x_graphics.draw(l_line);
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
/*      */   private boolean isEitherSeriesDegenerate(XYDataset x_dataset, boolean x_impliedZeroSubtrahend) {
/*  979 */     if (x_impliedZeroSubtrahend) {
/*  980 */       return (x_dataset.getItemCount(0) < 2);
/*      */     }
/*      */     
/*  983 */     return (x_dataset.getItemCount(0) < 2 || x_dataset
/*  984 */       .getItemCount(1) < 2);
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
/*      */   private boolean areSeriesDisjoint(XYDataset x_dataset) {
/*  997 */     int l_minuendItemCount = x_dataset.getItemCount(0);
/*  998 */     double l_minuendFirst = x_dataset.getXValue(0, 0);
/*  999 */     double l_minuendLast = x_dataset.getXValue(0, l_minuendItemCount - 1);
/*      */     
/* 1001 */     int l_subtrahendItemCount = x_dataset.getItemCount(1);
/* 1002 */     double l_subtrahendFirst = x_dataset.getXValue(1, 0);
/* 1003 */     double l_subtrahendLast = x_dataset.getXValue(1, l_subtrahendItemCount - 1);
/*      */ 
/*      */     
/* 1006 */     return (l_minuendLast < l_subtrahendFirst || l_subtrahendLast < l_minuendFirst);
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
/*      */ 
/*      */ 
/*      */   
/*      */   private void createPolygon(Graphics2D x_graphics, Rectangle2D x_dataArea, XYPlot x_plot, ValueAxis x_domainAxis, ValueAxis x_rangeAxis, boolean x_positive, LinkedList x_xValues, LinkedList x_yValues) {
/* 1035 */     PlotOrientation l_orientation = x_plot.getOrientation();
/* 1036 */     RectangleEdge l_domainAxisLocation = x_plot.getDomainAxisEdge();
/* 1037 */     RectangleEdge l_rangeAxisLocation = x_plot.getRangeAxisEdge();
/*      */     
/* 1039 */     Object[] l_xValues = x_xValues.toArray();
/* 1040 */     Object[] l_yValues = x_yValues.toArray();
/*      */     
/* 1042 */     GeneralPath l_path = new GeneralPath();
/*      */     
/* 1044 */     if (PlotOrientation.VERTICAL == l_orientation) {
/* 1045 */       double l_x = x_domainAxis.valueToJava2D(((Double)l_xValues[0])
/* 1046 */           .doubleValue(), x_dataArea, l_domainAxisLocation);
/*      */       
/* 1048 */       if (this.roundXCoordinates) {
/* 1049 */         l_x = Math.rint(l_x);
/*      */       }
/*      */       
/* 1052 */       double l_y = x_rangeAxis.valueToJava2D(((Double)l_yValues[0])
/* 1053 */           .doubleValue(), x_dataArea, l_rangeAxisLocation);
/*      */ 
/*      */       
/* 1056 */       l_path.moveTo((float)l_x, (float)l_y);
/* 1057 */       for (int i = 1; i < l_xValues.length; i++) {
/* 1058 */         l_x = x_domainAxis.valueToJava2D(((Double)l_xValues[i])
/* 1059 */             .doubleValue(), x_dataArea, l_domainAxisLocation);
/*      */         
/* 1061 */         if (this.roundXCoordinates) {
/* 1062 */           l_x = Math.rint(l_x);
/*      */         }
/*      */         
/* 1065 */         l_y = x_rangeAxis.valueToJava2D(((Double)l_yValues[i])
/* 1066 */             .doubleValue(), x_dataArea, l_rangeAxisLocation);
/*      */         
/* 1068 */         l_path.lineTo((float)l_x, (float)l_y);
/*      */       } 
/* 1070 */       l_path.closePath();
/*      */     } else {
/*      */       
/* 1073 */       double l_x = x_domainAxis.valueToJava2D(((Double)l_xValues[0])
/* 1074 */           .doubleValue(), x_dataArea, l_domainAxisLocation);
/*      */       
/* 1076 */       if (this.roundXCoordinates) {
/* 1077 */         l_x = Math.rint(l_x);
/*      */       }
/*      */       
/* 1080 */       double l_y = x_rangeAxis.valueToJava2D(((Double)l_yValues[0])
/* 1081 */           .doubleValue(), x_dataArea, l_rangeAxisLocation);
/*      */ 
/*      */       
/* 1084 */       l_path.moveTo((float)l_y, (float)l_x);
/* 1085 */       for (int i = 1; i < l_xValues.length; i++) {
/* 1086 */         l_x = x_domainAxis.valueToJava2D(((Double)l_xValues[i])
/* 1087 */             .doubleValue(), x_dataArea, l_domainAxisLocation);
/*      */         
/* 1089 */         if (this.roundXCoordinates) {
/* 1090 */           l_x = Math.rint(l_x);
/*      */         }
/*      */         
/* 1093 */         l_y = x_rangeAxis.valueToJava2D(((Double)l_yValues[i])
/* 1094 */             .doubleValue(), x_dataArea, l_rangeAxisLocation);
/*      */         
/* 1096 */         l_path.lineTo((float)l_y, (float)l_x);
/*      */       } 
/* 1098 */       l_path.closePath();
/*      */     } 
/*      */     
/* 1101 */     if (l_path.intersects(x_dataArea)) {
/* 1102 */       x_graphics.setPaint(x_positive ? getPositivePaint() : 
/* 1103 */           getNegativePaint());
/* 1104 */       x_graphics.fill(l_path);
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
/*      */   public LegendItem getLegendItem(int datasetIndex, int series) {
/* 1119 */     LegendItem result = null;
/* 1120 */     XYPlot p = getPlot();
/* 1121 */     if (p != null) {
/* 1122 */       XYDataset dataset = p.getDataset(datasetIndex);
/* 1123 */       if (dataset != null && 
/* 1124 */         getItemVisible(series, 0)) {
/* 1125 */         String label = getLegendItemLabelGenerator().generateLabel(dataset, series);
/*      */         
/* 1127 */         String description = label;
/* 1128 */         String toolTipText = null;
/* 1129 */         if (getLegendItemToolTipGenerator() != null)
/*      */         {
/* 1131 */           toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*      */         }
/*      */         
/* 1134 */         String urlText = null;
/* 1135 */         if (getLegendItemURLGenerator() != null) {
/* 1136 */           urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*      */         }
/*      */         
/* 1139 */         Paint paint = lookupSeriesPaint(series);
/* 1140 */         Stroke stroke = lookupSeriesStroke(series);
/* 1141 */         Shape line = getLegendLine();
/* 1142 */         result = new LegendItem(label, description, toolTipText, urlText, line, stroke, paint);
/*      */         
/* 1144 */         result.setLabelFont(lookupLegendTextFont(series));
/* 1145 */         Paint labelPaint = lookupLegendTextPaint(series);
/* 1146 */         if (labelPaint != null) {
/* 1147 */           result.setLabelPaint(labelPaint);
/*      */         }
/* 1149 */         result.setDataset((Dataset)dataset);
/* 1150 */         result.setDatasetIndex(datasetIndex);
/* 1151 */         result.setSeriesKey(dataset.getSeriesKey(series));
/* 1152 */         result.setSeriesIndex(series);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1158 */     return result;
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
/* 1171 */     if (obj == this) {
/* 1172 */       return true;
/*      */     }
/* 1174 */     if (!(obj instanceof XYDifferenceRenderer)) {
/* 1175 */       return false;
/*      */     }
/* 1177 */     if (!super.equals(obj)) {
/* 1178 */       return false;
/*      */     }
/* 1180 */     XYDifferenceRenderer that = (XYDifferenceRenderer)obj;
/* 1181 */     if (!PaintUtilities.equal(this.positivePaint, that.positivePaint)) {
/* 1182 */       return false;
/*      */     }
/* 1184 */     if (!PaintUtilities.equal(this.negativePaint, that.negativePaint)) {
/* 1185 */       return false;
/*      */     }
/* 1187 */     if (this.shapesVisible != that.shapesVisible) {
/* 1188 */       return false;
/*      */     }
/* 1190 */     if (!ShapeUtilities.equal(this.legendLine, that.legendLine)) {
/* 1191 */       return false;
/*      */     }
/* 1193 */     if (this.roundXCoordinates != that.roundXCoordinates) {
/* 1194 */       return false;
/*      */     }
/* 1196 */     return true;
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
/* 1208 */     XYDifferenceRenderer clone = (XYDifferenceRenderer)super.clone();
/* 1209 */     clone.legendLine = ShapeUtilities.clone(this.legendLine);
/* 1210 */     return clone;
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
/* 1221 */     stream.defaultWriteObject();
/* 1222 */     SerialUtilities.writePaint(this.positivePaint, stream);
/* 1223 */     SerialUtilities.writePaint(this.negativePaint, stream);
/* 1224 */     SerialUtilities.writeShape(this.legendLine, stream);
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
/* 1237 */     stream.defaultReadObject();
/* 1238 */     this.positivePaint = SerialUtilities.readPaint(stream);
/* 1239 */     this.negativePaint = SerialUtilities.readPaint(stream);
/* 1240 */     this.legendLine = SerialUtilities.readShape(stream);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYDifferenceRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */