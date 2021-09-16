/*      */ package org.jfree.chart.renderer;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Point;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Ellipse2D;
/*      */ import java.awt.geom.GeneralPath;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.PathIterator;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.jfree.chart.LegendItem;
/*      */ import org.jfree.chart.axis.NumberTick;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.entity.ChartEntity;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.entity.XYItemEntity;
/*      */ import org.jfree.chart.labels.XYSeriesLabelGenerator;
/*      */ import org.jfree.chart.labels.XYToolTipGenerator;
/*      */ import org.jfree.chart.plot.DrawingSupplier;
/*      */ import org.jfree.chart.plot.PlotOrientation;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.plot.PolarPlot;
/*      */ import org.jfree.chart.renderer.xy.AbstractXYItemRenderer;
/*      */ import org.jfree.chart.urls.XYURLGenerator;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.xy.XYDataset;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.util.BooleanList;
/*      */ import org.jfree.util.BooleanUtilities;
/*      */ import org.jfree.util.ObjectList;
/*      */ import org.jfree.util.ObjectUtilities;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DefaultPolarItemRenderer
/*      */   extends AbstractRenderer
/*      */   implements PolarItemRenderer
/*      */ {
/*      */   private PolarPlot plot;
/*  205 */   private BooleanList seriesFilled = new BooleanList();
/*      */   private boolean drawOutlineWhenFilled = true;
/*  207 */   private transient Composite fillComposite = AlphaComposite.getInstance(3, 0.3F);
/*      */   
/*      */   private boolean useFillPaint = false;
/*  210 */   private transient Shape legendLine = new Line2D.Double(-7.0D, 0.0D, 7.0D, 0.0D);
/*      */   
/*      */   private boolean shapesVisible = true;
/*      */   private boolean connectFirstAndLastPoint = true;
/*  214 */   private ObjectList toolTipGeneratorList = new ObjectList();
/*  215 */   private XYURLGenerator urlGenerator = null; private XYToolTipGenerator baseToolTipGenerator;
/*  216 */   private XYSeriesLabelGenerator legendItemToolTipGenerator = null;
/*  217 */   private XYSeriesLabelGenerator legendItemURLGenerator = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlot(PolarPlot plot) {
/*  229 */     this.plot = plot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolarPlot getPlot() {
/*  241 */     return this.plot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDrawOutlineWhenFilled() {
/*  253 */     return this.drawOutlineWhenFilled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDrawOutlineWhenFilled(boolean drawOutlineWhenFilled) {
/*  266 */     this.drawOutlineWhenFilled = drawOutlineWhenFilled;
/*  267 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Composite getFillComposite() {
/*  278 */     return this.fillComposite;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFillComposite(Composite composite) {
/*  291 */     ParamChecks.nullNotPermitted(composite, "composite");
/*  292 */     this.fillComposite = composite;
/*  293 */     fireChangeEvent();
/*      */   }
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
/*  305 */     return this.shapesVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShapesVisible(boolean visible) {
/*  318 */     this.shapesVisible = visible;
/*  319 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getConnectFirstAndLastPoint() {
/*  331 */     return this.connectFirstAndLastPoint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConnectFirstAndLastPoint(boolean connect) {
/*  344 */     this.connectFirstAndLastPoint = connect;
/*  345 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DrawingSupplier getDrawingSupplier() {
/*  355 */     DrawingSupplier result = null;
/*  356 */     PolarPlot p = getPlot();
/*  357 */     if (p != null) {
/*  358 */       result = p.getDrawingSupplier();
/*      */     }
/*  360 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSeriesFilled(int series) {
/*  372 */     boolean result = false;
/*  373 */     Boolean b = this.seriesFilled.getBoolean(series);
/*  374 */     if (b != null) {
/*  375 */       result = b.booleanValue();
/*      */     }
/*  377 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeriesFilled(int series, boolean filled) {
/*  387 */     this.seriesFilled.setBoolean(series, BooleanUtilities.valueOf(filled));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseFillPaint() {
/*  401 */     return this.useFillPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseFillPaint(boolean flag) {
/*  415 */     this.useFillPaint = flag;
/*  416 */     fireChangeEvent();
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
/*  427 */     return this.legendLine;
/*      */   }
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
/*  439 */     ParamChecks.nullNotPermitted(line, "line");
/*  440 */     this.legendLine = line;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addEntity(EntityCollection entities, Shape area, XYDataset dataset, int series, int item, double entityX, double entityY) {
/*  461 */     if (!getItemCreateEntity(series, item)) {
/*      */       return;
/*      */     }
/*  464 */     Shape hotspot = area;
/*  465 */     if (hotspot == null) {
/*  466 */       double r = getDefaultEntityRadius();
/*  467 */       double w = r * 2.0D;
/*  468 */       if (getPlot().getOrientation() == PlotOrientation.VERTICAL) {
/*  469 */         hotspot = new Ellipse2D.Double(entityX - r, entityY - r, w, w);
/*      */       } else {
/*      */         
/*  472 */         hotspot = new Ellipse2D.Double(entityY - r, entityX - r, w, w);
/*      */       } 
/*      */     } 
/*  475 */     String tip = null;
/*  476 */     XYToolTipGenerator generator = getToolTipGenerator(series, item);
/*  477 */     if (generator != null) {
/*  478 */       tip = generator.generateToolTip(dataset, series, item);
/*      */     }
/*  480 */     String url = null;
/*  481 */     if (getURLGenerator() != null) {
/*  482 */       url = getURLGenerator().generateURL(dataset, series, item);
/*      */     }
/*  484 */     XYItemEntity entity = new XYItemEntity(hotspot, dataset, series, item, tip, url);
/*      */     
/*  486 */     entities.add((ChartEntity)entity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawSeries(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info, PolarPlot plot, XYDataset dataset, int seriesIndex) {
/*  504 */     int numPoints = dataset.getItemCount(seriesIndex);
/*  505 */     if (numPoints == 0) {
/*      */       return;
/*      */     }
/*  508 */     GeneralPath poly = null;
/*  509 */     ValueAxis axis = plot.getAxisForDataset(plot.indexOf(dataset));
/*  510 */     for (int i = 0; i < numPoints; i++) {
/*  511 */       double theta = dataset.getXValue(seriesIndex, i);
/*  512 */       double radius = dataset.getYValue(seriesIndex, i);
/*  513 */       Point p = plot.translateToJava2D(theta, radius, axis, dataArea);
/*  514 */       if (poly == null) {
/*  515 */         poly = new GeneralPath();
/*  516 */         poly.moveTo(p.x, p.y);
/*      */       } else {
/*      */         
/*  519 */         poly.lineTo(p.x, p.y);
/*      */       } 
/*      */     } 
/*  522 */     assert poly != null;
/*  523 */     if (getConnectFirstAndLastPoint()) {
/*  524 */       poly.closePath();
/*      */     }
/*      */     
/*  527 */     g2.setPaint(lookupSeriesPaint(seriesIndex));
/*  528 */     g2.setStroke(lookupSeriesStroke(seriesIndex));
/*  529 */     if (isSeriesFilled(seriesIndex)) {
/*  530 */       Composite savedComposite = g2.getComposite();
/*  531 */       g2.setComposite(this.fillComposite);
/*  532 */       g2.fill(poly);
/*  533 */       g2.setComposite(savedComposite);
/*  534 */       if (this.drawOutlineWhenFilled)
/*      */       {
/*  536 */         g2.setPaint(lookupSeriesOutlinePaint(seriesIndex));
/*  537 */         g2.draw(poly);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  542 */       g2.draw(poly);
/*      */     } 
/*      */ 
/*      */     
/*  546 */     if (this.shapesVisible) {
/*      */       
/*  548 */       EntityCollection entities = null;
/*  549 */       if (info != null) {
/*  550 */         entities = info.getOwner().getEntityCollection();
/*      */       }
/*      */       
/*  553 */       PathIterator pi = poly.getPathIterator((AffineTransform)null);
/*  554 */       int j = 0;
/*  555 */       while (!pi.isDone()) {
/*  556 */         Paint paint; float[] coords = new float[6];
/*  557 */         int segType = pi.currentSegment(coords);
/*  558 */         pi.next();
/*  559 */         if (segType != 1 && segType != 0) {
/*      */           continue;
/*      */         }
/*      */         
/*  563 */         int x = Math.round(coords[0]);
/*  564 */         int y = Math.round(coords[1]);
/*  565 */         Shape shape = ShapeUtilities.createTranslatedShape(
/*  566 */             getItemShape(seriesIndex, j++), x, y);
/*      */ 
/*      */         
/*  569 */         if (this.useFillPaint) {
/*  570 */           paint = lookupSeriesFillPaint(seriesIndex);
/*      */         } else {
/*      */           
/*  573 */           paint = lookupSeriesPaint(seriesIndex);
/*      */         } 
/*  575 */         g2.setPaint(paint);
/*  576 */         g2.fill(shape);
/*  577 */         if (isSeriesFilled(seriesIndex) && this.drawOutlineWhenFilled) {
/*  578 */           g2.setPaint(lookupSeriesOutlinePaint(seriesIndex));
/*  579 */           g2.setStroke(lookupSeriesOutlineStroke(seriesIndex));
/*  580 */           g2.draw(shape);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  585 */         if (entities != null && 
/*  586 */           AbstractXYItemRenderer.isPointInRect(dataArea, x, y)) {
/*  587 */           addEntity(entities, shape, dataset, seriesIndex, j - 1, x, y);
/*      */         }
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
/*      */   public void drawAngularGridLines(Graphics2D g2, PolarPlot plot, List ticks, Rectangle2D dataArea) {
/*      */     double centerValue, outerValue;
/*  605 */     g2.setFont(plot.getAngleLabelFont());
/*  606 */     g2.setStroke(plot.getAngleGridlineStroke());
/*  607 */     g2.setPaint(plot.getAngleGridlinePaint());
/*      */     
/*  609 */     ValueAxis axis = plot.getAxis();
/*      */     
/*  611 */     if (axis.isInverted()) {
/*  612 */       outerValue = axis.getLowerBound();
/*  613 */       centerValue = axis.getUpperBound();
/*      */     } else {
/*  615 */       outerValue = axis.getUpperBound();
/*  616 */       centerValue = axis.getLowerBound();
/*      */     } 
/*  618 */     Point center = plot.translateToJava2D(0.0D, centerValue, axis, dataArea);
/*  619 */     Iterator<NumberTick> iterator = ticks.iterator();
/*  620 */     while (iterator.hasNext()) {
/*  621 */       NumberTick tick = iterator.next();
/*  622 */       double tickVal = tick.getNumber().doubleValue();
/*  623 */       Point p = plot.translateToJava2D(tickVal, outerValue, axis, dataArea);
/*      */       
/*  625 */       g2.setPaint(plot.getAngleGridlinePaint());
/*  626 */       g2.drawLine(center.x, center.y, p.x, p.y);
/*  627 */       if (plot.isAngleLabelsVisible()) {
/*  628 */         int x = p.x;
/*  629 */         int y = p.y;
/*  630 */         g2.setPaint(plot.getAngleLabelPaint());
/*  631 */         TextUtilities.drawAlignedString(tick.getText(), g2, x, y, tick
/*  632 */             .getTextAnchor());
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
/*      */   public void drawRadialGridLines(Graphics2D g2, PolarPlot plot, ValueAxis radialAxis, List ticks, Rectangle2D dataArea) {
/*      */     double centerValue;
/*  650 */     ParamChecks.nullNotPermitted(radialAxis, "radialAxis");
/*  651 */     g2.setFont(radialAxis.getTickLabelFont());
/*  652 */     g2.setPaint(plot.getRadiusGridlinePaint());
/*  653 */     g2.setStroke(plot.getRadiusGridlineStroke());
/*      */ 
/*      */     
/*  656 */     if (radialAxis.isInverted()) {
/*  657 */       centerValue = radialAxis.getUpperBound();
/*      */     } else {
/*  659 */       centerValue = radialAxis.getLowerBound();
/*      */     } 
/*  661 */     Point center = plot.translateToJava2D(0.0D, centerValue, radialAxis, dataArea);
/*      */     
/*  663 */     Iterator<NumberTick> iterator = ticks.iterator();
/*  664 */     while (iterator.hasNext()) {
/*  665 */       NumberTick tick = iterator.next();
/*      */       
/*  667 */       double angleDegrees = plot.isCounterClockwise() ? plot.getAngleOffset() : -plot.getAngleOffset();
/*  668 */       Point p = plot.translateToJava2D(angleDegrees, tick
/*  669 */           .getNumber().doubleValue(), radialAxis, dataArea);
/*  670 */       int r = p.x - center.x;
/*  671 */       int upperLeftX = center.x - r;
/*  672 */       int upperLeftY = center.y - r;
/*  673 */       int d = 2 * r;
/*  674 */       Ellipse2D ring = new Ellipse2D.Double(upperLeftX, upperLeftY, d, d);
/*  675 */       g2.setPaint(plot.getRadiusGridlinePaint());
/*  676 */       g2.draw(ring);
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
/*      */   public LegendItem getLegendItem(int series) {
/*      */     Paint paint;
/*  690 */     PolarPlot plot = getPlot();
/*  691 */     if (plot == null) {
/*  692 */       return null;
/*      */     }
/*  694 */     XYDataset dataset = plot.getDataset(plot.getIndexOf(this));
/*  695 */     if (dataset == null) {
/*  696 */       return null;
/*      */     }
/*      */     
/*  699 */     String toolTipText = null;
/*  700 */     if (getLegendItemToolTipGenerator() != null) {
/*  701 */       toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*      */     }
/*      */     
/*  704 */     String urlText = null;
/*  705 */     if (getLegendItemURLGenerator() != null) {
/*  706 */       urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*      */     }
/*      */ 
/*      */     
/*  710 */     Comparable seriesKey = dataset.getSeriesKey(series);
/*  711 */     String label = seriesKey.toString();
/*  712 */     String description = label;
/*  713 */     Shape shape = lookupSeriesShape(series);
/*      */     
/*  715 */     if (this.useFillPaint) {
/*  716 */       paint = lookupSeriesFillPaint(series);
/*      */     } else {
/*      */       
/*  719 */       paint = lookupSeriesPaint(series);
/*      */     } 
/*  721 */     Stroke stroke = lookupSeriesStroke(series);
/*  722 */     Paint outlinePaint = lookupSeriesOutlinePaint(series);
/*  723 */     Stroke outlineStroke = lookupSeriesOutlineStroke(series);
/*  724 */     boolean shapeOutlined = (isSeriesFilled(series) && this.drawOutlineWhenFilled);
/*      */ 
/*      */     
/*  727 */     LegendItem result = new LegendItem(label, description, toolTipText, urlText, getShapesVisible(), shape, true, paint, shapeOutlined, outlinePaint, outlineStroke, true, this.legendLine, stroke, paint);
/*      */ 
/*      */     
/*  730 */     result.setToolTipText(toolTipText);
/*  731 */     result.setURLText(urlText);
/*  732 */     result.setDataset((Dataset)dataset);
/*  733 */     result.setSeriesKey(seriesKey);
/*  734 */     result.setSeriesIndex(series);
/*      */     
/*  736 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYToolTipGenerator getToolTipGenerator(int series, int item) {
/*  752 */     XYToolTipGenerator generator = (XYToolTipGenerator)this.toolTipGeneratorList.get(series);
/*  753 */     if (generator == null) {
/*  754 */       generator = this.baseToolTipGenerator;
/*      */     }
/*  756 */     return generator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYToolTipGenerator getSeriesToolTipGenerator(int series) {
/*  768 */     return (XYToolTipGenerator)this.toolTipGeneratorList.get(series);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeriesToolTipGenerator(int series, XYToolTipGenerator generator) {
/*  782 */     this.toolTipGeneratorList.set(series, generator);
/*  783 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYToolTipGenerator getBaseToolTipGenerator() {
/*  795 */     return this.baseToolTipGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseToolTipGenerator(XYToolTipGenerator generator) {
/*  808 */     this.baseToolTipGenerator = generator;
/*  809 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYURLGenerator getURLGenerator() {
/*  821 */     return this.urlGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setURLGenerator(XYURLGenerator urlGenerator) {
/*  833 */     this.urlGenerator = urlGenerator;
/*  834 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYSeriesLabelGenerator getLegendItemToolTipGenerator() {
/*  846 */     return this.legendItemToolTipGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegendItemToolTipGenerator(XYSeriesLabelGenerator generator) {
/*  860 */     this.legendItemToolTipGenerator = generator;
/*  861 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYSeriesLabelGenerator getLegendItemURLGenerator() {
/*  873 */     return this.legendItemURLGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegendItemURLGenerator(XYSeriesLabelGenerator generator) {
/*  886 */     this.legendItemURLGenerator = generator;
/*  887 */     fireChangeEvent();
/*      */   }
/*      */ 
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
/*  900 */     if (obj == null) {
/*  901 */       return false;
/*      */     }
/*  903 */     if (!(obj instanceof DefaultPolarItemRenderer)) {
/*  904 */       return false;
/*      */     }
/*  906 */     DefaultPolarItemRenderer that = (DefaultPolarItemRenderer)obj;
/*  907 */     if (!this.seriesFilled.equals(that.seriesFilled)) {
/*  908 */       return false;
/*      */     }
/*  910 */     if (this.drawOutlineWhenFilled != that.drawOutlineWhenFilled) {
/*  911 */       return false;
/*      */     }
/*  913 */     if (!ObjectUtilities.equal(this.fillComposite, that.fillComposite)) {
/*  914 */       return false;
/*      */     }
/*  916 */     if (this.useFillPaint != that.useFillPaint) {
/*  917 */       return false;
/*      */     }
/*  919 */     if (!ShapeUtilities.equal(this.legendLine, that.legendLine)) {
/*  920 */       return false;
/*      */     }
/*  922 */     if (this.shapesVisible != that.shapesVisible) {
/*  923 */       return false;
/*      */     }
/*  925 */     if (this.connectFirstAndLastPoint != that.connectFirstAndLastPoint) {
/*  926 */       return false;
/*      */     }
/*  928 */     if (!this.toolTipGeneratorList.equals(that.toolTipGeneratorList)) {
/*  929 */       return false;
/*      */     }
/*  931 */     if (!ObjectUtilities.equal(this.baseToolTipGenerator, that.baseToolTipGenerator))
/*      */     {
/*  933 */       return false;
/*      */     }
/*  935 */     if (!ObjectUtilities.equal(this.urlGenerator, that.urlGenerator)) {
/*  936 */       return false;
/*      */     }
/*  938 */     if (!ObjectUtilities.equal(this.legendItemToolTipGenerator, that.legendItemToolTipGenerator))
/*      */     {
/*  940 */       return false;
/*      */     }
/*  942 */     if (!ObjectUtilities.equal(this.legendItemURLGenerator, that.legendItemURLGenerator))
/*      */     {
/*  944 */       return false;
/*      */     }
/*  946 */     return super.equals(obj);
/*      */   }
/*      */ 
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
/*  959 */     DefaultPolarItemRenderer clone = (DefaultPolarItemRenderer)super.clone();
/*  960 */     if (this.legendLine != null) {
/*  961 */       clone.legendLine = ShapeUtilities.clone(this.legendLine);
/*      */     }
/*  963 */     clone.seriesFilled = (BooleanList)this.seriesFilled.clone();
/*  964 */     clone
/*  965 */       .toolTipGeneratorList = (ObjectList)this.toolTipGeneratorList.clone();
/*  966 */     if (clone.baseToolTipGenerator instanceof org.jfree.util.PublicCloneable) {
/*  967 */       clone
/*  968 */         .baseToolTipGenerator = (XYToolTipGenerator)ObjectUtilities.clone(this.baseToolTipGenerator);
/*      */     }
/*  970 */     if (clone.urlGenerator instanceof org.jfree.util.PublicCloneable) {
/*  971 */       clone
/*  972 */         .urlGenerator = (XYURLGenerator)ObjectUtilities.clone(this.urlGenerator);
/*      */     }
/*  974 */     if (clone.legendItemToolTipGenerator instanceof org.jfree.util.PublicCloneable) {
/*  975 */       clone
/*  976 */         .legendItemToolTipGenerator = (XYSeriesLabelGenerator)ObjectUtilities.clone(this.legendItemToolTipGenerator);
/*      */     }
/*  978 */     if (clone.legendItemURLGenerator instanceof org.jfree.util.PublicCloneable) {
/*  979 */       clone
/*  980 */         .legendItemURLGenerator = (XYSeriesLabelGenerator)ObjectUtilities.clone(this.legendItemURLGenerator);
/*      */     }
/*  982 */     return clone;
/*      */   }
/*      */ 
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
/*  995 */     stream.defaultReadObject();
/*  996 */     this.legendLine = SerialUtilities.readShape(stream);
/*  997 */     this.fillComposite = SerialUtilities.readComposite(stream);
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
/* 1008 */     stream.defaultWriteObject();
/* 1009 */     SerialUtilities.writeShape(this.legendLine, stream);
/* 1010 */     SerialUtilities.writeComposite(this.fillComposite, stream);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/DefaultPolarItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */