/*      */ package org.jfree.chart.axis;
/*      */ 
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.font.LineMetrics;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.jfree.chart.plot.Plot;
/*      */ import org.jfree.chart.util.AttrStringUtils;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ import org.jfree.util.ObjectUtilities;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ValueAxis
/*      */   extends Axis
/*      */   implements Cloneable, PublicCloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 3698345477322391456L;
/*  158 */   public static final Range DEFAULT_RANGE = new Range(0.0D, 1.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean DEFAULT_AUTO_RANGE = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean DEFAULT_INVERTED = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final double DEFAULT_AUTO_RANGE_MINIMUM_SIZE = 1.0E-8D;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final double DEFAULT_LOWER_MARGIN = 0.05D;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final double DEFAULT_UPPER_MARGIN = 0.05D;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final double DEFAULT_LOWER_BOUND = 0.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final double DEFAULT_UPPER_BOUND = 1.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean DEFAULT_AUTO_TICK_UNIT_SELECTION = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int MAXIMUM_TICK_COUNT = 500;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean positiveArrowVisible;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean negativeArrowVisible;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Shape upArrow;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Shape downArrow;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Shape leftArrow;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Shape rightArrow;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean inverted;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Range range;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoRange;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double autoRangeMinimumSize;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Range defaultAutoRange;
/*      */ 
/*      */ 
/*      */   
/*      */   private double upperMargin;
/*      */ 
/*      */ 
/*      */   
/*      */   private double lowerMargin;
/*      */ 
/*      */ 
/*      */   
/*      */   private double fixedAutoRange;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoTickUnitSelection;
/*      */ 
/*      */ 
/*      */   
/*      */   private TickUnitSource standardTickUnits;
/*      */ 
/*      */ 
/*      */   
/*      */   private int autoTickIndex;
/*      */ 
/*      */ 
/*      */   
/*      */   private int minorTickCount;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean verticalTickLabels;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ValueAxis(String label, TickUnitSource standardTickUnits) {
/*  296 */     super(label);
/*      */     
/*  298 */     this.positiveArrowVisible = false;
/*  299 */     this.negativeArrowVisible = false;
/*      */     
/*  301 */     this.range = DEFAULT_RANGE;
/*  302 */     this.autoRange = true;
/*  303 */     this.defaultAutoRange = DEFAULT_RANGE;
/*      */     
/*  305 */     this.inverted = false;
/*  306 */     this.autoRangeMinimumSize = 1.0E-8D;
/*      */     
/*  308 */     this.lowerMargin = 0.05D;
/*  309 */     this.upperMargin = 0.05D;
/*      */     
/*  311 */     this.fixedAutoRange = 0.0D;
/*      */     
/*  313 */     this.autoTickUnitSelection = true;
/*  314 */     this.standardTickUnits = standardTickUnits;
/*      */     
/*  316 */     Polygon p1 = new Polygon();
/*  317 */     p1.addPoint(0, 0);
/*  318 */     p1.addPoint(-2, 2);
/*  319 */     p1.addPoint(2, 2);
/*      */     
/*  321 */     this.upArrow = p1;
/*      */     
/*  323 */     Polygon p2 = new Polygon();
/*  324 */     p2.addPoint(0, 0);
/*  325 */     p2.addPoint(-2, -2);
/*  326 */     p2.addPoint(2, -2);
/*      */     
/*  328 */     this.downArrow = p2;
/*      */     
/*  330 */     Polygon p3 = new Polygon();
/*  331 */     p3.addPoint(0, 0);
/*  332 */     p3.addPoint(-2, -2);
/*  333 */     p3.addPoint(-2, 2);
/*      */     
/*  335 */     this.rightArrow = p3;
/*      */     
/*  337 */     Polygon p4 = new Polygon();
/*  338 */     p4.addPoint(0, 0);
/*  339 */     p4.addPoint(2, -2);
/*  340 */     p4.addPoint(2, 2);
/*      */     
/*  342 */     this.leftArrow = p4;
/*      */     
/*  344 */     this.verticalTickLabels = false;
/*  345 */     this.minorTickCount = 0;
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
/*      */   public boolean isVerticalTickLabels() {
/*  358 */     return this.verticalTickLabels;
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
/*      */   public void setVerticalTickLabels(boolean flag) {
/*  372 */     if (this.verticalTickLabels != flag) {
/*  373 */       this.verticalTickLabels = flag;
/*  374 */       fireChangeEvent();
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
/*      */   public boolean isPositiveArrowVisible() {
/*  387 */     return this.positiveArrowVisible;
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
/*      */   public void setPositiveArrowVisible(boolean visible) {
/*  400 */     this.positiveArrowVisible = visible;
/*  401 */     fireChangeEvent();
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
/*      */   public boolean isNegativeArrowVisible() {
/*  413 */     return this.negativeArrowVisible;
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
/*      */   public void setNegativeArrowVisible(boolean visible) {
/*  426 */     this.negativeArrowVisible = visible;
/*  427 */     fireChangeEvent();
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
/*      */   public Shape getUpArrow() {
/*  439 */     return this.upArrow;
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
/*      */   public void setUpArrow(Shape arrow) {
/*  452 */     ParamChecks.nullNotPermitted(arrow, "arrow");
/*  453 */     this.upArrow = arrow;
/*  454 */     fireChangeEvent();
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
/*      */   public Shape getDownArrow() {
/*  466 */     return this.downArrow;
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
/*      */   public void setDownArrow(Shape arrow) {
/*  479 */     ParamChecks.nullNotPermitted(arrow, "arrow");
/*  480 */     this.downArrow = arrow;
/*  481 */     fireChangeEvent();
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
/*      */   public Shape getLeftArrow() {
/*  493 */     return this.leftArrow;
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
/*      */   public void setLeftArrow(Shape arrow) {
/*  506 */     ParamChecks.nullNotPermitted(arrow, "arrow");
/*  507 */     this.leftArrow = arrow;
/*  508 */     fireChangeEvent();
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
/*      */   public Shape getRightArrow() {
/*  520 */     return this.rightArrow;
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
/*      */   public void setRightArrow(Shape arrow) {
/*  533 */     ParamChecks.nullNotPermitted(arrow, "arrow");
/*  534 */     this.rightArrow = arrow;
/*  535 */     fireChangeEvent();
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
/*      */   protected void drawAxisLine(Graphics2D g2, double cursor, Rectangle2D dataArea, RectangleEdge edge) {
/*  549 */     Line2D axisLine = null;
/*  550 */     double c = cursor;
/*  551 */     if (edge == RectangleEdge.TOP) {
/*  552 */       axisLine = new Line2D.Double(dataArea.getX(), c, dataArea.getMaxX(), c);
/*      */     }
/*  554 */     else if (edge == RectangleEdge.BOTTOM) {
/*  555 */       axisLine = new Line2D.Double(dataArea.getX(), c, dataArea.getMaxX(), c);
/*      */     }
/*  557 */     else if (edge == RectangleEdge.LEFT) {
/*      */       
/*  559 */       axisLine = new Line2D.Double(c, dataArea.getY(), c, dataArea.getMaxY());
/*  560 */     } else if (edge == RectangleEdge.RIGHT) {
/*      */       
/*  562 */       axisLine = new Line2D.Double(c, dataArea.getY(), c, dataArea.getMaxY());
/*      */     } 
/*  564 */     g2.setPaint(getAxisLinePaint());
/*  565 */     g2.setStroke(getAxisLineStroke());
/*  566 */     Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
/*  567 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/*      */     
/*  569 */     g2.draw(axisLine);
/*  570 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
/*      */     
/*  572 */     boolean drawUpOrRight = false;
/*  573 */     boolean drawDownOrLeft = false;
/*  574 */     if (this.positiveArrowVisible) {
/*  575 */       if (this.inverted) {
/*  576 */         drawDownOrLeft = true;
/*      */       } else {
/*      */         
/*  579 */         drawUpOrRight = true;
/*      */       } 
/*      */     }
/*  582 */     if (this.negativeArrowVisible) {
/*  583 */       if (this.inverted) {
/*  584 */         drawUpOrRight = true;
/*      */       } else {
/*  586 */         drawDownOrLeft = true;
/*      */       } 
/*      */     }
/*  589 */     if (drawUpOrRight) {
/*  590 */       double x = 0.0D;
/*  591 */       double y = 0.0D;
/*  592 */       Shape arrow = null;
/*  593 */       if (edge == RectangleEdge.TOP || edge == RectangleEdge.BOTTOM) {
/*  594 */         x = dataArea.getMaxX();
/*  595 */         y = cursor;
/*  596 */         arrow = this.rightArrow;
/*  597 */       } else if (edge == RectangleEdge.LEFT || edge == RectangleEdge.RIGHT) {
/*      */         
/*  599 */         x = cursor;
/*  600 */         y = dataArea.getMinY();
/*  601 */         arrow = this.upArrow;
/*      */       } 
/*      */ 
/*      */       
/*  605 */       AffineTransform transformer = new AffineTransform();
/*  606 */       transformer.setToTranslation(x, y);
/*  607 */       Shape shape = transformer.createTransformedShape(arrow);
/*  608 */       g2.fill(shape);
/*  609 */       g2.draw(shape);
/*      */     } 
/*      */     
/*  612 */     if (drawDownOrLeft) {
/*  613 */       double x = 0.0D;
/*  614 */       double y = 0.0D;
/*  615 */       Shape arrow = null;
/*  616 */       if (edge == RectangleEdge.TOP || edge == RectangleEdge.BOTTOM) {
/*  617 */         x = dataArea.getMinX();
/*  618 */         y = cursor;
/*  619 */         arrow = this.leftArrow;
/*  620 */       } else if (edge == RectangleEdge.LEFT || edge == RectangleEdge.RIGHT) {
/*      */         
/*  622 */         x = cursor;
/*  623 */         y = dataArea.getMaxY();
/*  624 */         arrow = this.downArrow;
/*      */       } 
/*      */ 
/*      */       
/*  628 */       AffineTransform transformer = new AffineTransform();
/*  629 */       transformer.setToTranslation(x, y);
/*  630 */       Shape shape = transformer.createTransformedShape(arrow);
/*  631 */       g2.fill(shape);
/*  632 */       g2.draw(shape);
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
/*      */   protected float[] calculateAnchorPoint(ValueTick tick, double cursor, Rectangle2D dataArea, RectangleEdge edge) {
/*  650 */     RectangleInsets insets = getTickLabelInsets();
/*  651 */     float[] result = new float[2];
/*  652 */     if (edge == RectangleEdge.TOP) {
/*  653 */       result[0] = (float)valueToJava2D(tick.getValue(), dataArea, edge);
/*  654 */       result[1] = (float)(cursor - insets.getBottom() - 2.0D);
/*      */     }
/*  656 */     else if (edge == RectangleEdge.BOTTOM) {
/*  657 */       result[0] = (float)valueToJava2D(tick.getValue(), dataArea, edge);
/*  658 */       result[1] = (float)(cursor + insets.getTop() + 2.0D);
/*      */     }
/*  660 */     else if (edge == RectangleEdge.LEFT) {
/*  661 */       result[0] = (float)(cursor - insets.getLeft() - 2.0D);
/*  662 */       result[1] = (float)valueToJava2D(tick.getValue(), dataArea, edge);
/*      */     }
/*  664 */     else if (edge == RectangleEdge.RIGHT) {
/*  665 */       result[0] = (float)(cursor + insets.getRight() + 2.0D);
/*  666 */       result[1] = (float)valueToJava2D(tick.getValue(), dataArea, edge);
/*      */     } 
/*  668 */     return result;
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
/*      */   protected AxisState drawTickMarksAndLabels(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge) {
/*  687 */     AxisState state = new AxisState(cursor);
/*  688 */     if (isAxisLineVisible()) {
/*  689 */       drawAxisLine(g2, cursor, dataArea, edge);
/*      */     }
/*  691 */     List ticks = refreshTicks(g2, state, dataArea, edge);
/*  692 */     state.setTicks(ticks);
/*  693 */     g2.setFont(getTickLabelFont());
/*  694 */     Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
/*  695 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/*      */     
/*  697 */     Iterator<ValueTick> iterator = ticks.iterator();
/*  698 */     while (iterator.hasNext()) {
/*  699 */       ValueTick tick = iterator.next();
/*  700 */       if (isTickLabelsVisible()) {
/*  701 */         g2.setPaint(getTickLabelPaint());
/*  702 */         float[] anchorPoint = calculateAnchorPoint(tick, cursor, dataArea, edge);
/*      */         
/*  704 */         if (tick instanceof LogTick) {
/*  705 */           LogTick lt = (LogTick)tick;
/*  706 */           if (lt.getAttributedLabel() == null) {
/*      */             continue;
/*      */           }
/*  709 */           AttrStringUtils.drawRotatedString(lt.getAttributedLabel(), g2, anchorPoint[0], anchorPoint[1], tick
/*      */               
/*  711 */               .getTextAnchor(), tick.getAngle(), tick
/*  712 */               .getRotationAnchor());
/*      */         } else {
/*  714 */           if (tick.getText() == null) {
/*      */             continue;
/*      */           }
/*  717 */           TextUtilities.drawRotatedString(tick.getText(), g2, anchorPoint[0], anchorPoint[1], tick
/*      */               
/*  719 */               .getTextAnchor(), tick.getAngle(), tick
/*  720 */               .getRotationAnchor());
/*      */         } 
/*      */       } 
/*      */       
/*  724 */       if ((isTickMarksVisible() && tick.getTickType().equals(TickType.MAJOR)) || (
/*  725 */         isMinorTickMarksVisible() && tick
/*  726 */         .getTickType().equals(TickType.MINOR))) {
/*      */ 
/*      */ 
/*      */         
/*  730 */         double ol = tick.getTickType().equals(TickType.MINOR) ? getMinorTickMarkOutsideLength() : getTickMarkOutsideLength();
/*      */ 
/*      */ 
/*      */         
/*  734 */         double il = tick.getTickType().equals(TickType.MINOR) ? getMinorTickMarkInsideLength() : getTickMarkInsideLength();
/*      */         
/*  736 */         float xx = (float)valueToJava2D(tick.getValue(), dataArea, edge);
/*      */         
/*  738 */         Line2D mark = null;
/*  739 */         g2.setStroke(getTickMarkStroke());
/*  740 */         g2.setPaint(getTickMarkPaint());
/*  741 */         if (edge == RectangleEdge.LEFT) {
/*  742 */           mark = new Line2D.Double(cursor - ol, xx, cursor + il, xx);
/*      */         }
/*  744 */         else if (edge == RectangleEdge.RIGHT) {
/*  745 */           mark = new Line2D.Double(cursor + ol, xx, cursor - il, xx);
/*      */         }
/*  747 */         else if (edge == RectangleEdge.TOP) {
/*  748 */           mark = new Line2D.Double(xx, cursor - ol, xx, cursor + il);
/*      */         }
/*  750 */         else if (edge == RectangleEdge.BOTTOM) {
/*  751 */           mark = new Line2D.Double(xx, cursor + ol, xx, cursor - il);
/*      */         } 
/*  753 */         g2.draw(mark);
/*      */       } 
/*      */     } 
/*  756 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
/*      */ 
/*      */ 
/*      */     
/*  760 */     double used = 0.0D;
/*  761 */     if (isTickLabelsVisible()) {
/*  762 */       if (edge == RectangleEdge.LEFT) {
/*  763 */         used += findMaximumTickLabelWidth(ticks, g2, plotArea, 
/*  764 */             isVerticalTickLabels());
/*  765 */         state.cursorLeft(used);
/*  766 */       } else if (edge == RectangleEdge.RIGHT) {
/*  767 */         used = findMaximumTickLabelWidth(ticks, g2, plotArea, 
/*  768 */             isVerticalTickLabels());
/*  769 */         state.cursorRight(used);
/*  770 */       } else if (edge == RectangleEdge.TOP) {
/*  771 */         used = findMaximumTickLabelHeight(ticks, g2, plotArea, 
/*  772 */             isVerticalTickLabels());
/*  773 */         state.cursorUp(used);
/*  774 */       } else if (edge == RectangleEdge.BOTTOM) {
/*  775 */         used = findMaximumTickLabelHeight(ticks, g2, plotArea, 
/*  776 */             isVerticalTickLabels());
/*  777 */         state.cursorDown(used);
/*      */       } 
/*      */     }
/*      */     
/*  781 */     return state;
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
/*  801 */     if (space == null) {
/*  802 */       space = new AxisSpace();
/*      */     }
/*      */ 
/*      */     
/*  806 */     if (!isVisible()) {
/*  807 */       return space;
/*      */     }
/*      */ 
/*      */     
/*  811 */     double dimension = getFixedDimension();
/*  812 */     if (dimension > 0.0D) {
/*  813 */       space.add(dimension, edge);
/*  814 */       return space;
/*      */     } 
/*      */ 
/*      */     
/*  818 */     double tickLabelHeight = 0.0D;
/*  819 */     double tickLabelWidth = 0.0D;
/*  820 */     if (isTickLabelsVisible()) {
/*  821 */       g2.setFont(getTickLabelFont());
/*  822 */       List ticks = refreshTicks(g2, new AxisState(), plotArea, edge);
/*  823 */       if (RectangleEdge.isTopOrBottom(edge)) {
/*  824 */         tickLabelHeight = findMaximumTickLabelHeight(ticks, g2, plotArea, 
/*  825 */             isVerticalTickLabels());
/*      */       }
/*  827 */       else if (RectangleEdge.isLeftOrRight(edge)) {
/*  828 */         tickLabelWidth = findMaximumTickLabelWidth(ticks, g2, plotArea, 
/*  829 */             isVerticalTickLabels());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  834 */     Rectangle2D labelEnclosure = getLabelEnclosure(g2, edge);
/*  835 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  836 */       double labelHeight = labelEnclosure.getHeight();
/*  837 */       space.add(labelHeight + tickLabelHeight, edge);
/*      */     }
/*  839 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  840 */       double labelWidth = labelEnclosure.getWidth();
/*  841 */       space.add(labelWidth + tickLabelWidth, edge);
/*      */     } 
/*      */     
/*  844 */     return space;
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
/*      */   protected double findMaximumTickLabelHeight(List ticks, Graphics2D g2, Rectangle2D drawArea, boolean vertical) {
/*  862 */     RectangleInsets insets = getTickLabelInsets();
/*  863 */     Font font = getTickLabelFont();
/*  864 */     g2.setFont(font);
/*  865 */     double maxHeight = 0.0D;
/*  866 */     if (vertical) {
/*  867 */       FontMetrics fm = g2.getFontMetrics(font);
/*  868 */       Iterator<Tick> iterator = ticks.iterator();
/*  869 */       while (iterator.hasNext()) {
/*  870 */         Tick tick = iterator.next();
/*  871 */         Rectangle2D labelBounds = null;
/*  872 */         if (tick instanceof LogTick) {
/*  873 */           LogTick lt = (LogTick)tick;
/*  874 */           if (lt.getAttributedLabel() != null) {
/*  875 */             labelBounds = AttrStringUtils.getTextBounds(lt
/*  876 */                 .getAttributedLabel(), g2);
/*      */           }
/*  878 */         } else if (tick.getText() != null) {
/*  879 */           labelBounds = TextUtilities.getTextBounds(tick
/*  880 */               .getText(), g2, fm);
/*      */         } 
/*  882 */         if (labelBounds != null && labelBounds.getWidth() + insets
/*  883 */           .getTop() + insets.getBottom() > maxHeight)
/*      */         {
/*  885 */           maxHeight = labelBounds.getWidth() + insets.getTop() + insets.getBottom();
/*      */         }
/*      */       } 
/*      */     } else {
/*  889 */       LineMetrics metrics = font.getLineMetrics("ABCxyz", g2
/*  890 */           .getFontRenderContext());
/*      */       
/*  892 */       maxHeight = metrics.getHeight() + insets.getTop() + insets.getBottom();
/*      */     } 
/*  894 */     return maxHeight;
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
/*      */   protected double findMaximumTickLabelWidth(List ticks, Graphics2D g2, Rectangle2D drawArea, boolean vertical) {
/*  912 */     RectangleInsets insets = getTickLabelInsets();
/*  913 */     Font font = getTickLabelFont();
/*  914 */     double maxWidth = 0.0D;
/*  915 */     if (!vertical) {
/*  916 */       FontMetrics fm = g2.getFontMetrics(font);
/*  917 */       Iterator<Tick> iterator = ticks.iterator();
/*  918 */       while (iterator.hasNext()) {
/*  919 */         Tick tick = iterator.next();
/*  920 */         Rectangle2D labelBounds = null;
/*  921 */         if (tick instanceof LogTick) {
/*  922 */           LogTick lt = (LogTick)tick;
/*  923 */           if (lt.getAttributedLabel() != null) {
/*  924 */             labelBounds = AttrStringUtils.getTextBounds(lt
/*  925 */                 .getAttributedLabel(), g2);
/*      */           }
/*  927 */         } else if (tick.getText() != null) {
/*  928 */           labelBounds = TextUtilities.getTextBounds(tick.getText(), g2, fm);
/*      */         } 
/*      */         
/*  931 */         if (labelBounds != null && labelBounds
/*  932 */           .getWidth() + insets.getLeft() + insets
/*  933 */           .getRight() > maxWidth)
/*      */         {
/*  935 */           maxWidth = labelBounds.getWidth() + insets.getLeft() + insets.getRight();
/*      */         }
/*      */       } 
/*      */     } else {
/*  939 */       LineMetrics metrics = font.getLineMetrics("ABCxyz", g2
/*  940 */           .getFontRenderContext());
/*      */       
/*  942 */       maxWidth = metrics.getHeight() + insets.getTop() + insets.getBottom();
/*      */     } 
/*  944 */     return maxWidth;
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
/*      */   public boolean isInverted() {
/*  960 */     return this.inverted;
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
/*      */   public void setInverted(boolean flag) {
/*  972 */     if (this.inverted != flag) {
/*  973 */       this.inverted = flag;
/*  974 */       fireChangeEvent();
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
/*      */   public boolean isAutoRange() {
/*  987 */     return this.autoRange;
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
/*      */   public void setAutoRange(boolean auto) {
/* 1000 */     setAutoRange(auto, true);
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
/*      */   protected void setAutoRange(boolean auto, boolean notify) {
/* 1013 */     if (this.autoRange != auto) {
/* 1014 */       this.autoRange = auto;
/* 1015 */       if (this.autoRange) {
/* 1016 */         autoAdjustRange();
/*      */       }
/* 1018 */       if (notify) {
/* 1019 */         fireChangeEvent();
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
/*      */   public double getAutoRangeMinimumSize() {
/* 1033 */     return this.autoRangeMinimumSize;
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
/*      */   public void setAutoRangeMinimumSize(double size) {
/* 1045 */     setAutoRangeMinimumSize(size, true);
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
/*      */   public void setAutoRangeMinimumSize(double size, boolean notify) {
/* 1059 */     if (size <= 0.0D) {
/* 1060 */       throw new IllegalArgumentException("NumberAxis.setAutoRangeMinimumSize(double): must be > 0.0.");
/*      */     }
/*      */     
/* 1063 */     if (this.autoRangeMinimumSize != size) {
/* 1064 */       this.autoRangeMinimumSize = size;
/* 1065 */       if (this.autoRange) {
/* 1066 */         autoAdjustRange();
/*      */       }
/* 1068 */       if (notify) {
/* 1069 */         fireChangeEvent();
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
/*      */   public Range getDefaultAutoRange() {
/* 1085 */     return this.defaultAutoRange;
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
/*      */   public void setDefaultAutoRange(Range range) {
/* 1099 */     ParamChecks.nullNotPermitted(range, "range");
/* 1100 */     this.defaultAutoRange = range;
/* 1101 */     fireChangeEvent();
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
/*      */   public double getLowerMargin() {
/* 1115 */     return this.lowerMargin;
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
/*      */   public void setLowerMargin(double margin) {
/* 1130 */     this.lowerMargin = margin;
/* 1131 */     if (isAutoRange()) {
/* 1132 */       autoAdjustRange();
/*      */     }
/* 1134 */     fireChangeEvent();
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
/*      */   public double getUpperMargin() {
/* 1148 */     return this.upperMargin;
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
/*      */   public void setUpperMargin(double margin) {
/* 1163 */     this.upperMargin = margin;
/* 1164 */     if (isAutoRange()) {
/* 1165 */       autoAdjustRange();
/*      */     }
/* 1167 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getFixedAutoRange() {
/* 1178 */     return this.fixedAutoRange;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFixedAutoRange(double length) {
/* 1189 */     this.fixedAutoRange = length;
/* 1190 */     if (isAutoRange()) {
/* 1191 */       autoAdjustRange();
/*      */     }
/* 1193 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getLowerBound() {
/* 1204 */     return this.range.getLowerBound();
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
/*      */   public void setLowerBound(double min) {
/* 1216 */     if (this.range.getUpperBound() > min) {
/* 1217 */       setRange(new Range(min, this.range.getUpperBound()));
/*      */     } else {
/*      */       
/* 1220 */       setRange(new Range(min, min + 1.0D));
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
/*      */   public double getUpperBound() {
/* 1232 */     return this.range.getUpperBound();
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
/*      */   public void setUpperBound(double max) {
/* 1244 */     if (this.range.getLowerBound() < max) {
/* 1245 */       setRange(new Range(this.range.getLowerBound(), max));
/*      */     } else {
/*      */       
/* 1248 */       setRange(max - 1.0D, max);
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
/*      */   public Range getRange() {
/* 1260 */     return this.range;
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
/*      */   public void setRange(Range range) {
/* 1274 */     setRange(range, true, true);
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
/*      */   public void setRange(Range range, boolean turnOffAutoRange, boolean notify) {
/* 1294 */     ParamChecks.nullNotPermitted(range, "range");
/* 1295 */     if (range.getLength() <= 0.0D) {
/* 1296 */       throw new IllegalArgumentException("A positive range length is required: " + range);
/*      */     }
/*      */     
/* 1299 */     if (turnOffAutoRange) {
/* 1300 */       this.autoRange = false;
/*      */     }
/* 1302 */     this.range = range;
/* 1303 */     if (notify) {
/* 1304 */       fireChangeEvent();
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
/*      */   public void setRange(double lower, double upper) {
/* 1320 */     setRange(new Range(lower, upper));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeWithMargins(Range range) {
/* 1331 */     setRangeWithMargins(range, true, true);
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
/*      */   public void setRangeWithMargins(Range range, boolean turnOffAutoRange, boolean notify) {
/* 1349 */     ParamChecks.nullNotPermitted(range, "range");
/* 1350 */     setRange(Range.expand(range, getLowerMargin(), getUpperMargin()), turnOffAutoRange, notify);
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
/*      */   public void setRangeWithMargins(double lower, double upper) {
/* 1363 */     setRangeWithMargins(new Range(lower, upper));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeAboutValue(double value, double length) {
/* 1374 */     setRange(new Range(value - length / 2.0D, value + length / 2.0D));
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
/*      */   public boolean isAutoTickUnitSelection() {
/* 1387 */     return this.autoTickUnitSelection;
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
/*      */   public void setAutoTickUnitSelection(boolean flag) {
/* 1400 */     setAutoTickUnitSelection(flag, true);
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
/*      */   public void setAutoTickUnitSelection(boolean flag, boolean notify) {
/* 1414 */     if (this.autoTickUnitSelection != flag) {
/* 1415 */       this.autoTickUnitSelection = flag;
/* 1416 */       if (notify) {
/* 1417 */         fireChangeEvent();
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
/*      */   public TickUnitSource getStandardTickUnits() {
/* 1430 */     return this.standardTickUnits;
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
/*      */   public void setStandardTickUnits(TickUnitSource source) {
/* 1446 */     this.standardTickUnits = source;
/* 1447 */     fireChangeEvent();
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
/*      */   public int getMinorTickCount() {
/* 1460 */     return this.minorTickCount;
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
/*      */   public void setMinorTickCount(int count) {
/* 1474 */     this.minorTickCount = count;
/* 1475 */     fireChangeEvent();
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
/*      */   public abstract double valueToJava2D(double paramDouble, Rectangle2D paramRectangle2D, RectangleEdge paramRectangleEdge);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double lengthToJava2D(double length, Rectangle2D area, RectangleEdge edge) {
/* 1507 */     double zero = valueToJava2D(0.0D, area, edge);
/* 1508 */     double l = valueToJava2D(length, area, edge);
/* 1509 */     return Math.abs(l - zero);
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
/*      */   public abstract double java2DToValue(double paramDouble, Rectangle2D paramRectangle2D, RectangleEdge paramRectangleEdge);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void autoAdjustRange();
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
/* 1542 */     double central = this.range.getCentralValue();
/*      */     
/* 1544 */     Range adjusted = new Range(this.range.getLowerBound() + value - central, this.range.getUpperBound() + value - central);
/* 1545 */     setRange(adjusted);
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
/*      */   public void resizeRange(double percent) {
/* 1561 */     resizeRange(percent, this.range.getCentralValue());
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
/*      */   public void resizeRange(double percent, double anchorValue) {
/* 1578 */     if (percent > 0.0D) {
/* 1579 */       double halfLength = this.range.getLength() * percent / 2.0D;
/* 1580 */       Range adjusted = new Range(anchorValue - halfLength, anchorValue + halfLength);
/*      */       
/* 1582 */       setRange(adjusted);
/*      */     } else {
/*      */       
/* 1585 */       setAutoRange(true);
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
/*      */   public void resizeRange2(double percent, double anchorValue) {
/* 1605 */     if (percent > 0.0D) {
/* 1606 */       double left = anchorValue - getLowerBound();
/* 1607 */       double right = getUpperBound() - anchorValue;
/* 1608 */       Range adjusted = new Range(anchorValue - left * percent, anchorValue + right * percent);
/*      */       
/* 1610 */       setRange(adjusted);
/*      */     } else {
/*      */       
/* 1613 */       setAutoRange(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomRange(double lowerPercent, double upperPercent) {
/* 1624 */     double r0, r1, start = this.range.getLowerBound();
/* 1625 */     double length = this.range.getLength();
/*      */     
/* 1627 */     if (isInverted()) {
/* 1628 */       r0 = start + length * (1.0D - upperPercent);
/* 1629 */       r1 = start + length * (1.0D - lowerPercent);
/*      */     } else {
/*      */       
/* 1632 */       r0 = start + length * lowerPercent;
/* 1633 */       r1 = start + length * upperPercent;
/*      */     } 
/* 1635 */     if (r1 > r0 && !Double.isInfinite(r1 - r0)) {
/* 1636 */       setRange(new Range(r0, r1));
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
/*      */   public void pan(double percent) {
/* 1648 */     Range r = getRange();
/* 1649 */     double length = this.range.getLength();
/* 1650 */     double adj = length * percent;
/* 1651 */     double lower = r.getLowerBound() + adj;
/* 1652 */     double upper = r.getUpperBound() + adj;
/* 1653 */     setRange(lower, upper);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getAutoTickIndex() {
/* 1664 */     return this.autoTickIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setAutoTickIndex(int index) {
/* 1675 */     this.autoTickIndex = index;
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
/* 1687 */     if (obj == this) {
/* 1688 */       return true;
/*      */     }
/* 1690 */     if (!(obj instanceof ValueAxis)) {
/* 1691 */       return false;
/*      */     }
/* 1693 */     ValueAxis that = (ValueAxis)obj;
/* 1694 */     if (this.positiveArrowVisible != that.positiveArrowVisible) {
/* 1695 */       return false;
/*      */     }
/* 1697 */     if (this.negativeArrowVisible != that.negativeArrowVisible) {
/* 1698 */       return false;
/*      */     }
/* 1700 */     if (this.inverted != that.inverted) {
/* 1701 */       return false;
/*      */     }
/*      */     
/* 1704 */     if (!this.autoRange && !ObjectUtilities.equal(this.range, that.range)) {
/* 1705 */       return false;
/*      */     }
/* 1707 */     if (this.autoRange != that.autoRange) {
/* 1708 */       return false;
/*      */     }
/* 1710 */     if (this.autoRangeMinimumSize != that.autoRangeMinimumSize) {
/* 1711 */       return false;
/*      */     }
/* 1713 */     if (!this.defaultAutoRange.equals(that.defaultAutoRange)) {
/* 1714 */       return false;
/*      */     }
/* 1716 */     if (this.upperMargin != that.upperMargin) {
/* 1717 */       return false;
/*      */     }
/* 1719 */     if (this.lowerMargin != that.lowerMargin) {
/* 1720 */       return false;
/*      */     }
/* 1722 */     if (this.fixedAutoRange != that.fixedAutoRange) {
/* 1723 */       return false;
/*      */     }
/* 1725 */     if (this.autoTickUnitSelection != that.autoTickUnitSelection) {
/* 1726 */       return false;
/*      */     }
/* 1728 */     if (!ObjectUtilities.equal(this.standardTickUnits, that.standardTickUnits))
/*      */     {
/* 1730 */       return false;
/*      */     }
/* 1732 */     if (this.verticalTickLabels != that.verticalTickLabels) {
/* 1733 */       return false;
/*      */     }
/* 1735 */     if (this.minorTickCount != that.minorTickCount) {
/* 1736 */       return false;
/*      */     }
/* 1738 */     return super.equals(obj);
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
/* 1751 */     ValueAxis clone = (ValueAxis)super.clone();
/* 1752 */     return clone;
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
/* 1763 */     stream.defaultWriteObject();
/* 1764 */     SerialUtilities.writeShape(this.upArrow, stream);
/* 1765 */     SerialUtilities.writeShape(this.downArrow, stream);
/* 1766 */     SerialUtilities.writeShape(this.leftArrow, stream);
/* 1767 */     SerialUtilities.writeShape(this.rightArrow, stream);
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
/*      */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 1781 */     stream.defaultReadObject();
/* 1782 */     this.upArrow = SerialUtilities.readShape(stream);
/* 1783 */     this.downArrow = SerialUtilities.readShape(stream);
/* 1784 */     this.leftArrow = SerialUtilities.readShape(stream);
/* 1785 */     this.rightArrow = SerialUtilities.readShape(stream);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/ValueAxis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */