/*      */ package org.jfree.chart.plot;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ResourceBundle;
/*      */ import org.jfree.chart.axis.AxisSpace;
/*      */ import org.jfree.chart.axis.AxisState;
/*      */ import org.jfree.chart.axis.NumberAxis;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.axis.ValueTick;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.chart.util.ResourceBundleWrapper;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ import org.jfree.util.ArrayUtilities;
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
/*      */ public class FastScatterPlot
/*      */   extends Plot
/*      */   implements ValueAxisPlot, Pannable, Zoomable, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 7871545897358563521L;
/*  117 */   public static final Stroke DEFAULT_GRIDLINE_STROKE = new BasicStroke(0.5F, 0, 2, 0.0F, new float[] { 2.0F, 2.0F }, 0.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  122 */   public static final Paint DEFAULT_GRIDLINE_PAINT = Color.lightGray;
/*      */ 
/*      */ 
/*      */   
/*      */   private float[][] data;
/*      */ 
/*      */ 
/*      */   
/*      */   private Range xDataRange;
/*      */ 
/*      */ 
/*      */   
/*      */   private Range yDataRange;
/*      */ 
/*      */ 
/*      */   
/*      */   private ValueAxis domainAxis;
/*      */ 
/*      */ 
/*      */   
/*      */   private ValueAxis rangeAxis;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint paint;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean domainGridlinesVisible;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke domainGridlineStroke;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint domainGridlinePaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean rangeGridlinesVisible;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke rangeGridlineStroke;
/*      */ 
/*      */   
/*      */   private transient Paint rangeGridlinePaint;
/*      */ 
/*      */   
/*      */   private boolean domainPannable;
/*      */ 
/*      */   
/*      */   private boolean rangePannable;
/*      */ 
/*      */   
/*  178 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FastScatterPlot() {
/*  186 */     this((float[][])null, (ValueAxis)new NumberAxis("X"), (ValueAxis)new NumberAxis("Y"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FastScatterPlot(float[][] data, ValueAxis domainAxis, ValueAxis rangeAxis) {
/*  202 */     ParamChecks.nullNotPermitted(domainAxis, "domainAxis");
/*  203 */     ParamChecks.nullNotPermitted(rangeAxis, "rangeAxis");
/*      */     
/*  205 */     this.data = data;
/*  206 */     this.xDataRange = calculateXDataRange(data);
/*  207 */     this.yDataRange = calculateYDataRange(data);
/*  208 */     this.domainAxis = domainAxis;
/*  209 */     this.domainAxis.setPlot(this);
/*  210 */     this.domainAxis.addChangeListener(this);
/*  211 */     this.rangeAxis = rangeAxis;
/*  212 */     this.rangeAxis.setPlot(this);
/*  213 */     this.rangeAxis.addChangeListener(this);
/*      */     
/*  215 */     this.paint = Color.red;
/*      */     
/*  217 */     this.domainGridlinesVisible = true;
/*  218 */     this.domainGridlinePaint = DEFAULT_GRIDLINE_PAINT;
/*  219 */     this.domainGridlineStroke = DEFAULT_GRIDLINE_STROKE;
/*      */     
/*  221 */     this.rangeGridlinesVisible = true;
/*  222 */     this.rangeGridlinePaint = DEFAULT_GRIDLINE_PAINT;
/*  223 */     this.rangeGridlineStroke = DEFAULT_GRIDLINE_STROKE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlotType() {
/*  233 */     return localizationResources.getString("Fast_Scatter_Plot");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[][] getData() {
/*  244 */     return this.data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setData(float[][] data) {
/*  256 */     this.data = data;
/*  257 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlotOrientation getOrientation() {
/*  267 */     return PlotOrientation.VERTICAL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueAxis getDomainAxis() {
/*  278 */     return this.domainAxis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainAxis(ValueAxis axis) {
/*  292 */     ParamChecks.nullNotPermitted(axis, "axis");
/*  293 */     this.domainAxis = axis;
/*  294 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueAxis getRangeAxis() {
/*  305 */     return this.rangeAxis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeAxis(ValueAxis axis) {
/*  319 */     ParamChecks.nullNotPermitted(axis, "axis");
/*  320 */     this.rangeAxis = axis;
/*  321 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getPaint() {
/*  333 */     return this.paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPaint(Paint paint) {
/*  345 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  346 */     this.paint = paint;
/*  347 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDomainGridlinesVisible() {
/*  360 */     return this.domainGridlinesVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainGridlinesVisible(boolean visible) {
/*  373 */     if (this.domainGridlinesVisible != visible) {
/*  374 */       this.domainGridlinesVisible = visible;
/*  375 */       fireChangeEvent();
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
/*      */   public Stroke getDomainGridlineStroke() {
/*  388 */     return this.domainGridlineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainGridlineStroke(Stroke stroke) {
/*  400 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/*  401 */     this.domainGridlineStroke = stroke;
/*  402 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getDomainGridlinePaint() {
/*  414 */     return this.domainGridlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainGridlinePaint(Paint paint) {
/*  426 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  427 */     this.domainGridlinePaint = paint;
/*  428 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangeGridlinesVisible() {
/*  440 */     return this.rangeGridlinesVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeGridlinesVisible(boolean visible) {
/*  453 */     if (this.rangeGridlinesVisible != visible) {
/*  454 */       this.rangeGridlinesVisible = visible;
/*  455 */       fireChangeEvent();
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
/*      */   public Stroke getRangeGridlineStroke() {
/*  468 */     return this.rangeGridlineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeGridlineStroke(Stroke stroke) {
/*  480 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/*  481 */     this.rangeGridlineStroke = stroke;
/*  482 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getRangeGridlinePaint() {
/*  494 */     return this.rangeGridlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeGridlinePaint(Paint paint) {
/*  506 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  507 */     this.rangeGridlinePaint = paint;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info) {
/*  528 */     if (info != null) {
/*  529 */       info.setPlotArea(area);
/*      */     }
/*      */ 
/*      */     
/*  533 */     RectangleInsets insets = getInsets();
/*  534 */     insets.trim(area);
/*      */     
/*  536 */     AxisSpace space = new AxisSpace();
/*  537 */     space = this.domainAxis.reserveSpace(g2, this, area, RectangleEdge.BOTTOM, space);
/*      */     
/*  539 */     space = this.rangeAxis.reserveSpace(g2, this, area, RectangleEdge.LEFT, space);
/*      */     
/*  541 */     Rectangle2D dataArea = space.shrink(area, null);
/*      */     
/*  543 */     if (info != null) {
/*  544 */       info.setDataArea(dataArea);
/*      */     }
/*      */ 
/*      */     
/*  548 */     drawBackground(g2, dataArea);
/*      */     
/*  550 */     AxisState domainAxisState = this.domainAxis.draw(g2, dataArea
/*  551 */         .getMaxY(), area, dataArea, RectangleEdge.BOTTOM, info);
/*  552 */     AxisState rangeAxisState = this.rangeAxis.draw(g2, dataArea.getMinX(), area, dataArea, RectangleEdge.LEFT, info);
/*      */     
/*  554 */     drawDomainGridlines(g2, dataArea, domainAxisState.getTicks());
/*  555 */     drawRangeGridlines(g2, dataArea, rangeAxisState.getTicks());
/*      */     
/*  557 */     Shape originalClip = g2.getClip();
/*  558 */     Composite originalComposite = g2.getComposite();
/*      */     
/*  560 */     g2.clip(dataArea);
/*  561 */     g2.setComposite(AlphaComposite.getInstance(3, 
/*  562 */           getForegroundAlpha()));
/*      */     
/*  564 */     render(g2, dataArea, info, (CrosshairState)null);
/*      */     
/*  566 */     g2.setClip(originalClip);
/*  567 */     g2.setComposite(originalComposite);
/*  568 */     drawOutline(g2, dataArea);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void render(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info, CrosshairState crosshairState) {
/*  585 */     g2.setPaint(this.paint);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  600 */     if (this.data != null) {
/*  601 */       for (int i = 0; i < (this.data[0]).length; i++) {
/*  602 */         float x = this.data[0][i];
/*  603 */         float y = this.data[1][i];
/*      */ 
/*      */ 
/*      */         
/*  607 */         int transX = (int)this.domainAxis.valueToJava2D(x, dataArea, RectangleEdge.BOTTOM);
/*      */         
/*  609 */         int transY = (int)this.rangeAxis.valueToJava2D(y, dataArea, RectangleEdge.LEFT);
/*      */         
/*  611 */         g2.fillRect(transX, transY, 1, 1);
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
/*      */   protected void drawDomainGridlines(Graphics2D g2, Rectangle2D dataArea, List ticks) {
/*  625 */     if (!isDomainGridlinesVisible()) {
/*      */       return;
/*      */     }
/*  628 */     Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
/*  629 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/*      */     
/*  631 */     Iterator<ValueTick> iterator = ticks.iterator();
/*  632 */     while (iterator.hasNext()) {
/*  633 */       ValueTick tick = iterator.next();
/*  634 */       double v = this.domainAxis.valueToJava2D(tick.getValue(), dataArea, RectangleEdge.BOTTOM);
/*      */ 
/*      */       
/*  637 */       Line2D line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
/*  638 */       g2.setPaint(getDomainGridlinePaint());
/*  639 */       g2.setStroke(getDomainGridlineStroke());
/*  640 */       g2.draw(line);
/*      */     } 
/*  642 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawRangeGridlines(Graphics2D g2, Rectangle2D dataArea, List ticks) {
/*  655 */     if (!isRangeGridlinesVisible()) {
/*      */       return;
/*      */     }
/*  658 */     Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
/*  659 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/*      */ 
/*      */     
/*  662 */     Iterator<ValueTick> iterator = ticks.iterator();
/*  663 */     while (iterator.hasNext()) {
/*  664 */       ValueTick tick = iterator.next();
/*  665 */       double v = this.rangeAxis.valueToJava2D(tick.getValue(), dataArea, RectangleEdge.LEFT);
/*      */ 
/*      */       
/*  668 */       Line2D line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
/*  669 */       g2.setPaint(getRangeGridlinePaint());
/*  670 */       g2.setStroke(getRangeGridlineStroke());
/*  671 */       g2.draw(line);
/*      */     } 
/*  673 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Range getDataRange(ValueAxis axis) {
/*  687 */     Range result = null;
/*  688 */     if (axis == this.domainAxis) {
/*  689 */       result = this.xDataRange;
/*      */     }
/*  691 */     else if (axis == this.rangeAxis) {
/*  692 */       result = this.yDataRange;
/*      */     } 
/*  694 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Range calculateXDataRange(float[][] data) {
/*  706 */     Range result = null;
/*      */     
/*  708 */     if (data != null) {
/*  709 */       float lowest = Float.POSITIVE_INFINITY;
/*  710 */       float highest = Float.NEGATIVE_INFINITY;
/*  711 */       for (int i = 0; i < (data[0]).length; i++) {
/*  712 */         float v = data[0][i];
/*  713 */         if (v < lowest) {
/*  714 */           lowest = v;
/*      */         }
/*  716 */         if (v > highest) {
/*  717 */           highest = v;
/*      */         }
/*      */       } 
/*  720 */       if (lowest <= highest) {
/*  721 */         result = new Range(lowest, highest);
/*      */       }
/*      */     } 
/*      */     
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
/*      */   private Range calculateYDataRange(float[][] data) {
/*  738 */     Range result = null;
/*  739 */     if (data != null) {
/*  740 */       float lowest = Float.POSITIVE_INFINITY;
/*  741 */       float highest = Float.NEGATIVE_INFINITY;
/*  742 */       for (int i = 0; i < (data[0]).length; i++) {
/*  743 */         float v = data[1][i];
/*  744 */         if (v < lowest) {
/*  745 */           lowest = v;
/*      */         }
/*  747 */         if (v > highest) {
/*  748 */           highest = v;
/*      */         }
/*      */       } 
/*  751 */       if (lowest <= highest) {
/*  752 */         result = new Range(lowest, highest);
/*      */       }
/*      */     } 
/*  755 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomDomainAxes(double factor, PlotRenderingInfo info, Point2D source) {
/*  769 */     this.domainAxis.resizeRange(factor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomDomainAxes(double factor, PlotRenderingInfo info, Point2D source, boolean useAnchor) {
/*  788 */     if (useAnchor) {
/*      */ 
/*      */       
/*  791 */       double sourceX = source.getX();
/*  792 */       double anchorX = this.domainAxis.java2DToValue(sourceX, info
/*  793 */           .getDataArea(), RectangleEdge.BOTTOM);
/*  794 */       this.domainAxis.resizeRange2(factor, anchorX);
/*      */     } else {
/*      */       
/*  797 */       this.domainAxis.resizeRange(factor);
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
/*      */   public void zoomDomainAxes(double lowerPercent, double upperPercent, PlotRenderingInfo info, Point2D source) {
/*  815 */     this.domainAxis.zoomRange(lowerPercent, upperPercent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomRangeAxes(double factor, PlotRenderingInfo info, Point2D source) {
/*  828 */     this.rangeAxis.resizeRange(factor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomRangeAxes(double factor, PlotRenderingInfo info, Point2D source, boolean useAnchor) {
/*  847 */     if (useAnchor) {
/*      */ 
/*      */       
/*  850 */       double sourceY = source.getY();
/*  851 */       double anchorY = this.rangeAxis.java2DToValue(sourceY, info
/*  852 */           .getDataArea(), RectangleEdge.LEFT);
/*  853 */       this.rangeAxis.resizeRange2(factor, anchorY);
/*      */     } else {
/*      */       
/*  856 */       this.rangeAxis.resizeRange(factor);
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
/*      */   public void zoomRangeAxes(double lowerPercent, double upperPercent, PlotRenderingInfo info, Point2D source) {
/*  874 */     this.rangeAxis.zoomRange(lowerPercent, upperPercent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDomainZoomable() {
/*  884 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangeZoomable() {
/*  894 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDomainPannable() {
/*  907 */     return this.domainPannable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainPannable(boolean pannable) {
/*  919 */     this.domainPannable = pannable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangePannable() {
/*  932 */     return this.rangePannable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangePannable(boolean pannable) {
/*  944 */     this.rangePannable = pannable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void panDomainAxes(double percent, PlotRenderingInfo info, Point2D source) {
/*  959 */     if (!isDomainPannable() || this.domainAxis == null) {
/*      */       return;
/*      */     }
/*  962 */     double length = this.domainAxis.getRange().getLength();
/*  963 */     double adj = percent * length;
/*  964 */     if (this.domainAxis.isInverted()) {
/*  965 */       adj = -adj;
/*      */     }
/*  967 */     this.domainAxis.setRange(this.domainAxis.getLowerBound() + adj, this.domainAxis
/*  968 */         .getUpperBound() + adj);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void panRangeAxes(double percent, PlotRenderingInfo info, Point2D source) {
/*  983 */     if (!isRangePannable() || this.rangeAxis == null) {
/*      */       return;
/*      */     }
/*  986 */     double length = this.rangeAxis.getRange().getLength();
/*  987 */     double adj = percent * length;
/*  988 */     if (this.rangeAxis.isInverted()) {
/*  989 */       adj = -adj;
/*      */     }
/*  991 */     this.rangeAxis.setRange(this.rangeAxis.getLowerBound() + adj, this.rangeAxis
/*  992 */         .getUpperBound() + adj);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 1007 */     if (obj == this) {
/* 1008 */       return true;
/*      */     }
/* 1010 */     if (!super.equals(obj)) {
/* 1011 */       return false;
/*      */     }
/* 1013 */     if (!(obj instanceof FastScatterPlot)) {
/* 1014 */       return false;
/*      */     }
/* 1016 */     FastScatterPlot that = (FastScatterPlot)obj;
/* 1017 */     if (this.domainPannable != that.domainPannable) {
/* 1018 */       return false;
/*      */     }
/* 1020 */     if (this.rangePannable != that.rangePannable) {
/* 1021 */       return false;
/*      */     }
/* 1023 */     if (!ArrayUtilities.equal(this.data, that.data)) {
/* 1024 */       return false;
/*      */     }
/* 1026 */     if (!ObjectUtilities.equal(this.domainAxis, that.domainAxis)) {
/* 1027 */       return false;
/*      */     }
/* 1029 */     if (!ObjectUtilities.equal(this.rangeAxis, that.rangeAxis)) {
/* 1030 */       return false;
/*      */     }
/* 1032 */     if (!PaintUtilities.equal(this.paint, that.paint)) {
/* 1033 */       return false;
/*      */     }
/* 1035 */     if (this.domainGridlinesVisible != that.domainGridlinesVisible) {
/* 1036 */       return false;
/*      */     }
/* 1038 */     if (!PaintUtilities.equal(this.domainGridlinePaint, that.domainGridlinePaint))
/*      */     {
/* 1040 */       return false;
/*      */     }
/* 1042 */     if (!ObjectUtilities.equal(this.domainGridlineStroke, that.domainGridlineStroke))
/*      */     {
/* 1044 */       return false;
/*      */     }
/* 1046 */     if ((!this.rangeGridlinesVisible) == that.rangeGridlinesVisible) {
/* 1047 */       return false;
/*      */     }
/* 1049 */     if (!PaintUtilities.equal(this.rangeGridlinePaint, that.rangeGridlinePaint))
/*      */     {
/* 1051 */       return false;
/*      */     }
/* 1053 */     if (!ObjectUtilities.equal(this.rangeGridlineStroke, that.rangeGridlineStroke))
/*      */     {
/* 1055 */       return false;
/*      */     }
/* 1057 */     return true;
/*      */   }
/*      */ 
/*      */ 
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
/* 1071 */     FastScatterPlot clone = (FastScatterPlot)super.clone();
/* 1072 */     if (this.data != null) {
/* 1073 */       clone.data = ArrayUtilities.clone(this.data);
/*      */     }
/* 1075 */     if (this.domainAxis != null) {
/* 1076 */       clone.domainAxis = (ValueAxis)this.domainAxis.clone();
/* 1077 */       clone.domainAxis.setPlot(clone);
/* 1078 */       clone.domainAxis.addChangeListener(clone);
/*      */     } 
/* 1080 */     if (this.rangeAxis != null) {
/* 1081 */       clone.rangeAxis = (ValueAxis)this.rangeAxis.clone();
/* 1082 */       clone.rangeAxis.setPlot(clone);
/* 1083 */       clone.rangeAxis.addChangeListener(clone);
/*      */     } 
/* 1085 */     return clone;
/*      */   }
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
/* 1097 */     stream.defaultWriteObject();
/* 1098 */     SerialUtilities.writePaint(this.paint, stream);
/* 1099 */     SerialUtilities.writeStroke(this.domainGridlineStroke, stream);
/* 1100 */     SerialUtilities.writePaint(this.domainGridlinePaint, stream);
/* 1101 */     SerialUtilities.writeStroke(this.rangeGridlineStroke, stream);
/* 1102 */     SerialUtilities.writePaint(this.rangeGridlinePaint, stream);
/*      */   }
/*      */ 
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
/* 1115 */     stream.defaultReadObject();
/*      */     
/* 1117 */     this.paint = SerialUtilities.readPaint(stream);
/* 1118 */     this.domainGridlineStroke = SerialUtilities.readStroke(stream);
/* 1119 */     this.domainGridlinePaint = SerialUtilities.readPaint(stream);
/*      */     
/* 1121 */     this.rangeGridlineStroke = SerialUtilities.readStroke(stream);
/* 1122 */     this.rangeGridlinePaint = SerialUtilities.readPaint(stream);
/*      */     
/* 1124 */     if (this.domainAxis != null) {
/* 1125 */       this.domainAxis.addChangeListener(this);
/*      */     }
/*      */     
/* 1128 */     if (this.rangeAxis != null)
/* 1129 */       this.rangeAxis.addChangeListener(this); 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/FastScatterPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */