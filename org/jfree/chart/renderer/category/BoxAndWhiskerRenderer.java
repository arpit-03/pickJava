/*      */ package org.jfree.chart.renderer.category;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Ellipse2D;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.jfree.chart.LegendItem;
/*      */ import org.jfree.chart.axis.CategoryAxis;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.plot.CategoryPlot;
/*      */ import org.jfree.chart.plot.PlotOrientation;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.renderer.Outlier;
/*      */ import org.jfree.chart.renderer.OutlierList;
/*      */ import org.jfree.chart.renderer.OutlierListCollection;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.category.CategoryDataset;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.util.PaintUtilities;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BoxAndWhiskerRenderer
/*      */   extends AbstractCategoryItemRenderer
/*      */   implements Cloneable, PublicCloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 632027470694481177L;
/*      */   private transient Paint artifactPaint;
/*      */   private boolean fillBox;
/*      */   private double itemMargin;
/*      */   private double maximumBarWidth;
/*      */   private boolean medianVisible;
/*      */   private boolean meanVisible;
/*      */   private boolean useOutlinePaintForWhiskers;
/*      */   private double whiskerWidth;
/*      */   
/*      */   public BoxAndWhiskerRenderer() {
/*  195 */     this.artifactPaint = Color.black;
/*  196 */     this.fillBox = true;
/*  197 */     this.itemMargin = 0.2D;
/*  198 */     this.maximumBarWidth = 1.0D;
/*  199 */     this.medianVisible = true;
/*  200 */     this.meanVisible = true;
/*  201 */     this.useOutlinePaintForWhiskers = false;
/*  202 */     this.whiskerWidth = 1.0D;
/*  203 */     setBaseLegendShape(new Rectangle2D.Double(-4.0D, -4.0D, 8.0D, 8.0D));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getArtifactPaint() {
/*  215 */     return this.artifactPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setArtifactPaint(Paint paint) {
/*  227 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  228 */     this.artifactPaint = paint;
/*  229 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFillBox() {
/*  240 */     return this.fillBox;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFillBox(boolean flag) {
/*  252 */     this.fillBox = flag;
/*  253 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getItemMargin() {
/*  265 */     return this.itemMargin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemMargin(double margin) {
/*  277 */     this.itemMargin = margin;
/*  278 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMaximumBarWidth() {
/*  292 */     return this.maximumBarWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaximumBarWidth(double percent) {
/*  308 */     this.maximumBarWidth = percent;
/*  309 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMeanVisible() {
/*  323 */     return this.meanVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMeanVisible(boolean visible) {
/*  338 */     if (this.meanVisible == visible) {
/*      */       return;
/*      */     }
/*  341 */     this.meanVisible = visible;
/*  342 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMedianVisible() {
/*  356 */     return this.medianVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMedianVisible(boolean visible) {
/*  371 */     if (this.medianVisible == visible) {
/*      */       return;
/*      */     }
/*  374 */     this.medianVisible = visible;
/*  375 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseOutlinePaintForWhiskers() {
/*  387 */     return this.useOutlinePaintForWhiskers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseOutlinePaintForWhiskers(boolean flag) {
/*  400 */     if (this.useOutlinePaintForWhiskers == flag) {
/*      */       return;
/*      */     }
/*  403 */     this.useOutlinePaintForWhiskers = flag;
/*  404 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getWhiskerWidth() {
/*  417 */     return this.whiskerWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWhiskerWidth(double width) {
/*  432 */     if (width < 0.0D || width > 1.0D) {
/*  433 */       throw new IllegalArgumentException("Value for whisker width out of range");
/*      */     }
/*      */     
/*  436 */     if (width == this.whiskerWidth) {
/*      */       return;
/*      */     }
/*  439 */     this.whiskerWidth = width;
/*  440 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
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
/*  454 */     CategoryPlot cp = getPlot();
/*  455 */     if (cp == null) {
/*  456 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  460 */     if (!isSeriesVisible(series) || !isSeriesVisibleInLegend(series)) {
/*  461 */       return null;
/*      */     }
/*      */     
/*  464 */     CategoryDataset dataset = cp.getDataset(datasetIndex);
/*  465 */     String label = getLegendItemLabelGenerator().generateLabel(dataset, series);
/*      */     
/*  467 */     String description = label;
/*  468 */     String toolTipText = null;
/*  469 */     if (getLegendItemToolTipGenerator() != null) {
/*  470 */       toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*      */     }
/*      */     
/*  473 */     String urlText = null;
/*  474 */     if (getLegendItemURLGenerator() != null) {
/*  475 */       urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*      */     }
/*      */     
/*  478 */     Shape shape = lookupLegendShape(series);
/*  479 */     Paint paint = lookupSeriesPaint(series);
/*  480 */     Paint outlinePaint = lookupSeriesOutlinePaint(series);
/*  481 */     Stroke outlineStroke = lookupSeriesOutlineStroke(series);
/*  482 */     LegendItem result = new LegendItem(label, description, toolTipText, urlText, shape, paint, outlineStroke, outlinePaint);
/*      */     
/*  484 */     result.setLabelFont(lookupLegendTextFont(series));
/*  485 */     Paint labelPaint = lookupLegendTextPaint(series);
/*  486 */     if (labelPaint != null) {
/*  487 */       result.setLabelPaint(labelPaint);
/*      */     }
/*  489 */     result.setDataset((Dataset)dataset);
/*  490 */     result.setDatasetIndex(datasetIndex);
/*  491 */     result.setSeriesKey(dataset.getRowKey(series));
/*  492 */     result.setSeriesIndex(series);
/*  493 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Range findRangeBounds(CategoryDataset dataset) {
/*  507 */     return findRangeBounds(dataset, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, CategoryPlot plot, int rendererIndex, PlotRenderingInfo info) {
/*  527 */     CategoryItemRendererState state = super.initialise(g2, dataArea, plot, rendererIndex, info);
/*      */ 
/*      */     
/*  530 */     CategoryAxis domainAxis = getDomainAxis(plot, rendererIndex);
/*  531 */     CategoryDataset dataset = plot.getDataset(rendererIndex);
/*  532 */     if (dataset != null) {
/*  533 */       int columns = dataset.getColumnCount();
/*  534 */       int rows = dataset.getRowCount();
/*  535 */       double space = 0.0D;
/*  536 */       PlotOrientation orientation = plot.getOrientation();
/*  537 */       if (orientation == PlotOrientation.HORIZONTAL) {
/*  538 */         space = dataArea.getHeight();
/*      */       }
/*  540 */       else if (orientation == PlotOrientation.VERTICAL) {
/*  541 */         space = dataArea.getWidth();
/*      */       } 
/*  543 */       double maxWidth = space * getMaximumBarWidth();
/*  544 */       double categoryMargin = 0.0D;
/*  545 */       double currentItemMargin = 0.0D;
/*  546 */       if (columns > 1) {
/*  547 */         categoryMargin = domainAxis.getCategoryMargin();
/*      */       }
/*  549 */       if (rows > 1) {
/*  550 */         currentItemMargin = getItemMargin();
/*      */       }
/*      */       
/*  553 */       double used = space * (1.0D - domainAxis.getLowerMargin() - domainAxis.getUpperMargin() - categoryMargin - currentItemMargin);
/*      */       
/*  555 */       if (rows * columns > 0) {
/*  556 */         state.setBarWidth(Math.min(used / (dataset.getColumnCount() * dataset
/*  557 */               .getRowCount()), maxWidth));
/*      */       } else {
/*      */         
/*  560 */         state.setBarWidth(Math.min(used, maxWidth));
/*      */       } 
/*      */     } 
/*  563 */     return state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {
/*  589 */     if (!getItemVisible(row, column)) {
/*      */       return;
/*      */     }
/*      */     
/*  593 */     if (!(dataset instanceof BoxAndWhiskerCategoryDataset)) {
/*  594 */       throw new IllegalArgumentException("BoxAndWhiskerRenderer.drawItem() : the data should be of type BoxAndWhiskerCategoryDataset only.");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  599 */     PlotOrientation orientation = plot.getOrientation();
/*      */     
/*  601 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*  602 */       drawHorizontalItem(g2, state, dataArea, plot, domainAxis, rangeAxis, dataset, row, column);
/*      */     
/*      */     }
/*  605 */     else if (orientation == PlotOrientation.VERTICAL) {
/*  606 */       drawVerticalItem(g2, state, dataArea, plot, domainAxis, rangeAxis, dataset, row, column);
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
/*      */   public void drawHorizontalItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column) {
/*  633 */     BoxAndWhiskerCategoryDataset bawDataset = (BoxAndWhiskerCategoryDataset)dataset;
/*      */ 
/*      */     
/*  636 */     double categoryEnd = domainAxis.getCategoryEnd(column, 
/*  637 */         getColumnCount(), dataArea, plot.getDomainAxisEdge());
/*  638 */     double categoryStart = domainAxis.getCategoryStart(column, 
/*  639 */         getColumnCount(), dataArea, plot.getDomainAxisEdge());
/*  640 */     double categoryWidth = Math.abs(categoryEnd - categoryStart);
/*      */     
/*  642 */     double yy = categoryStart;
/*  643 */     int seriesCount = getRowCount();
/*  644 */     int categoryCount = getColumnCount();
/*      */     
/*  646 */     if (seriesCount > 1) {
/*  647 */       double seriesGap = dataArea.getHeight() * getItemMargin() / (categoryCount * (seriesCount - 1));
/*      */       
/*  649 */       double usedWidth = state.getBarWidth() * seriesCount + seriesGap * (seriesCount - 1);
/*      */ 
/*      */ 
/*      */       
/*  653 */       double offset = (categoryWidth - usedWidth) / 2.0D;
/*  654 */       yy = yy + offset + row * (state.getBarWidth() + seriesGap);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  659 */       double offset = (categoryWidth - state.getBarWidth()) / 2.0D;
/*  660 */       yy += offset;
/*      */     } 
/*      */     
/*  663 */     g2.setPaint(getItemPaint(row, column));
/*  664 */     Stroke s = getItemStroke(row, column);
/*  665 */     g2.setStroke(s);
/*      */     
/*  667 */     RectangleEdge location = plot.getRangeAxisEdge();
/*      */     
/*  669 */     Number xQ1 = bawDataset.getQ1Value(row, column);
/*  670 */     Number xQ3 = bawDataset.getQ3Value(row, column);
/*  671 */     Number xMax = bawDataset.getMaxRegularValue(row, column);
/*  672 */     Number xMin = bawDataset.getMinRegularValue(row, column);
/*      */     
/*  674 */     Shape box = null;
/*  675 */     if (xQ1 != null && xQ3 != null && xMax != null && xMin != null) {
/*      */       
/*  677 */       double xxQ1 = rangeAxis.valueToJava2D(xQ1.doubleValue(), dataArea, location);
/*      */       
/*  679 */       double xxQ3 = rangeAxis.valueToJava2D(xQ3.doubleValue(), dataArea, location);
/*      */       
/*  681 */       double xxMax = rangeAxis.valueToJava2D(xMax.doubleValue(), dataArea, location);
/*      */       
/*  683 */       double xxMin = rangeAxis.valueToJava2D(xMin.doubleValue(), dataArea, location);
/*      */       
/*  685 */       double yymid = yy + state.getBarWidth() / 2.0D;
/*  686 */       double halfW = state.getBarWidth() / 2.0D * this.whiskerWidth;
/*      */ 
/*      */ 
/*      */       
/*  690 */       box = new Rectangle2D.Double(Math.min(xxQ1, xxQ3), yy, Math.abs(xxQ1 - xxQ3), state.getBarWidth());
/*  691 */       if (this.fillBox) {
/*  692 */         g2.fill(box);
/*      */       }
/*      */       
/*  695 */       Paint outlinePaint = getItemOutlinePaint(row, column);
/*  696 */       if (this.useOutlinePaintForWhiskers) {
/*  697 */         g2.setPaint(outlinePaint);
/*      */       }
/*      */       
/*  700 */       g2.draw(new Line2D.Double(xxMax, yymid, xxQ3, yymid));
/*  701 */       g2.draw(new Line2D.Double(xxMax, yymid - halfW, xxMax, yymid + halfW));
/*      */ 
/*      */ 
/*      */       
/*  705 */       g2.draw(new Line2D.Double(xxMin, yymid, xxQ1, yymid));
/*  706 */       g2.draw(new Line2D.Double(xxMin, yymid - halfW, xxMin, yy + halfW));
/*      */ 
/*      */       
/*  709 */       g2.setStroke(getItemOutlineStroke(row, column));
/*  710 */       g2.setPaint(outlinePaint);
/*  711 */       g2.draw(box);
/*      */     } 
/*      */ 
/*      */     
/*  715 */     g2.setPaint(this.artifactPaint);
/*      */     
/*  717 */     if (this.meanVisible) {
/*  718 */       Number xMean = bawDataset.getMeanValue(row, column);
/*  719 */       if (xMean != null) {
/*  720 */         double xxMean = rangeAxis.valueToJava2D(xMean.doubleValue(), dataArea, location);
/*      */         
/*  722 */         double aRadius = state.getBarWidth() / 4.0D;
/*      */ 
/*      */         
/*  725 */         if (xxMean > dataArea.getMinX() - aRadius && xxMean < dataArea
/*  726 */           .getMaxX() + aRadius) {
/*  727 */           Ellipse2D.Double avgEllipse = new Ellipse2D.Double(xxMean - aRadius, yy + aRadius, aRadius * 2.0D, aRadius * 2.0D);
/*      */           
/*  729 */           g2.fill(avgEllipse);
/*  730 */           g2.draw(avgEllipse);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  736 */     if (this.medianVisible) {
/*  737 */       Number xMedian = bawDataset.getMedianValue(row, column);
/*  738 */       if (xMedian != null) {
/*  739 */         double xxMedian = rangeAxis.valueToJava2D(xMedian.doubleValue(), dataArea, location);
/*      */         
/*  741 */         g2.draw(new Line2D.Double(xxMedian, yy, xxMedian, yy + state
/*  742 */               .getBarWidth()));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  747 */     if (state.getInfo() != null && box != null) {
/*  748 */       EntityCollection entities = state.getEntityCollection();
/*  749 */       if (entities != null) {
/*  750 */         addItemEntity(entities, dataset, row, column, box);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawVerticalItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column) {
/*  776 */     BoxAndWhiskerCategoryDataset bawDataset = (BoxAndWhiskerCategoryDataset)dataset;
/*      */ 
/*      */     
/*  779 */     double categoryEnd = domainAxis.getCategoryEnd(column, 
/*  780 */         getColumnCount(), dataArea, plot.getDomainAxisEdge());
/*  781 */     double categoryStart = domainAxis.getCategoryStart(column, 
/*  782 */         getColumnCount(), dataArea, plot.getDomainAxisEdge());
/*  783 */     double categoryWidth = categoryEnd - categoryStart;
/*      */     
/*  785 */     double xx = categoryStart;
/*  786 */     int seriesCount = getRowCount();
/*  787 */     int categoryCount = getColumnCount();
/*      */     
/*  789 */     if (seriesCount > 1) {
/*  790 */       double seriesGap = dataArea.getWidth() * getItemMargin() / (categoryCount * (seriesCount - 1));
/*      */       
/*  792 */       double usedWidth = state.getBarWidth() * seriesCount + seriesGap * (seriesCount - 1);
/*      */ 
/*      */ 
/*      */       
/*  796 */       double offset = (categoryWidth - usedWidth) / 2.0D;
/*  797 */       xx = xx + offset + row * (state.getBarWidth() + seriesGap);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  802 */       double offset = (categoryWidth - state.getBarWidth()) / 2.0D;
/*  803 */       xx += offset;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  809 */     Paint itemPaint = getItemPaint(row, column);
/*  810 */     g2.setPaint(itemPaint);
/*  811 */     Stroke s = getItemStroke(row, column);
/*  812 */     g2.setStroke(s);
/*      */     
/*  814 */     double aRadius = 0.0D;
/*      */     
/*  816 */     RectangleEdge location = plot.getRangeAxisEdge();
/*      */     
/*  818 */     Number yQ1 = bawDataset.getQ1Value(row, column);
/*  819 */     Number yQ3 = bawDataset.getQ3Value(row, column);
/*  820 */     Number yMax = bawDataset.getMaxRegularValue(row, column);
/*  821 */     Number yMin = bawDataset.getMinRegularValue(row, column);
/*  822 */     Shape box = null;
/*  823 */     if (yQ1 != null && yQ3 != null && yMax != null && yMin != null) {
/*      */       
/*  825 */       double yyQ1 = rangeAxis.valueToJava2D(yQ1.doubleValue(), dataArea, location);
/*      */       
/*  827 */       double yyQ3 = rangeAxis.valueToJava2D(yQ3.doubleValue(), dataArea, location);
/*      */       
/*  829 */       double yyMax = rangeAxis.valueToJava2D(yMax.doubleValue(), dataArea, location);
/*      */       
/*  831 */       double yyMin = rangeAxis.valueToJava2D(yMin.doubleValue(), dataArea, location);
/*      */       
/*  833 */       double xxmid = xx + state.getBarWidth() / 2.0D;
/*  834 */       double halfW = state.getBarWidth() / 2.0D * this.whiskerWidth;
/*      */ 
/*      */ 
/*      */       
/*  838 */       box = new Rectangle2D.Double(xx, Math.min(yyQ1, yyQ3), state.getBarWidth(), Math.abs(yyQ1 - yyQ3));
/*  839 */       if (this.fillBox) {
/*  840 */         g2.fill(box);
/*      */       }
/*      */       
/*  843 */       Paint outlinePaint = getItemOutlinePaint(row, column);
/*  844 */       if (this.useOutlinePaintForWhiskers) {
/*  845 */         g2.setPaint(outlinePaint);
/*      */       }
/*      */       
/*  848 */       g2.draw(new Line2D.Double(xxmid, yyMax, xxmid, yyQ3));
/*  849 */       g2.draw(new Line2D.Double(xxmid - halfW, yyMax, xxmid + halfW, yyMax));
/*      */ 
/*      */       
/*  852 */       g2.draw(new Line2D.Double(xxmid, yyMin, xxmid, yyQ1));
/*  853 */       g2.draw(new Line2D.Double(xxmid - halfW, yyMin, xxmid + halfW, yyMin));
/*      */       
/*  855 */       g2.setStroke(getItemOutlineStroke(row, column));
/*  856 */       g2.setPaint(outlinePaint);
/*  857 */       g2.draw(box);
/*      */     } 
/*      */     
/*  860 */     g2.setPaint(this.artifactPaint);
/*      */ 
/*      */     
/*  863 */     if (this.meanVisible) {
/*  864 */       Number yMean = bawDataset.getMeanValue(row, column);
/*  865 */       if (yMean != null) {
/*  866 */         double yyAverage = rangeAxis.valueToJava2D(yMean.doubleValue(), dataArea, location);
/*      */         
/*  868 */         aRadius = state.getBarWidth() / 4.0D;
/*      */ 
/*      */         
/*  871 */         if (yyAverage > dataArea.getMinY() - aRadius && yyAverage < dataArea
/*  872 */           .getMaxY() + aRadius) {
/*  873 */           Ellipse2D.Double avgEllipse = new Ellipse2D.Double(xx + aRadius, yyAverage - aRadius, aRadius * 2.0D, aRadius * 2.0D);
/*      */ 
/*      */           
/*  876 */           g2.fill(avgEllipse);
/*  877 */           g2.draw(avgEllipse);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  883 */     if (this.medianVisible) {
/*  884 */       Number yMedian = bawDataset.getMedianValue(row, column);
/*  885 */       if (yMedian != null) {
/*  886 */         double yyMedian = rangeAxis.valueToJava2D(yMedian
/*  887 */             .doubleValue(), dataArea, location);
/*  888 */         g2.draw(new Line2D.Double(xx, yyMedian, xx + state
/*  889 */               .getBarWidth(), yyMedian));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  894 */     double maxAxisValue = rangeAxis.valueToJava2D(rangeAxis
/*  895 */         .getUpperBound(), dataArea, location) + aRadius;
/*  896 */     double minAxisValue = rangeAxis.valueToJava2D(rangeAxis
/*  897 */         .getLowerBound(), dataArea, location) - aRadius;
/*      */     
/*  899 */     g2.setPaint(itemPaint);
/*      */ 
/*      */     
/*  902 */     double oRadius = state.getBarWidth() / 3.0D;
/*  903 */     List<Outlier> outliers = new ArrayList();
/*  904 */     OutlierListCollection outlierListCollection = new OutlierListCollection();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  910 */     List<Number> yOutliers = bawDataset.getOutliers(row, column);
/*  911 */     if (yOutliers != null) {
/*  912 */       for (int i = 0; i < yOutliers.size(); i++) {
/*  913 */         double outlier = ((Number)yOutliers.get(i)).doubleValue();
/*  914 */         Number minOutlier = bawDataset.getMinOutlier(row, column);
/*  915 */         Number maxOutlier = bawDataset.getMaxOutlier(row, column);
/*  916 */         Number minRegular = bawDataset.getMinRegularValue(row, column);
/*  917 */         Number maxRegular = bawDataset.getMaxRegularValue(row, column);
/*  918 */         if (outlier > maxOutlier.doubleValue()) {
/*  919 */           outlierListCollection.setHighFarOut(true);
/*      */         }
/*  921 */         else if (outlier < minOutlier.doubleValue()) {
/*  922 */           outlierListCollection.setLowFarOut(true);
/*      */         }
/*  924 */         else if (outlier > maxRegular.doubleValue()) {
/*  925 */           double yyOutlier = rangeAxis.valueToJava2D(outlier, dataArea, location);
/*      */           
/*  927 */           outliers.add(new Outlier(xx + state.getBarWidth() / 2.0D, yyOutlier, oRadius));
/*      */         
/*      */         }
/*  930 */         else if (outlier < minRegular.doubleValue()) {
/*  931 */           double yyOutlier = rangeAxis.valueToJava2D(outlier, dataArea, location);
/*      */           
/*  933 */           outliers.add(new Outlier(xx + state.getBarWidth() / 2.0D, yyOutlier, oRadius));
/*      */         } 
/*      */         
/*  936 */         Collections.sort(outliers);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  941 */       for (Iterator<Outlier> iterator1 = outliers.iterator(); iterator1.hasNext(); ) {
/*  942 */         Outlier outlier = iterator1.next();
/*  943 */         outlierListCollection.add(outlier);
/*      */       } 
/*      */       
/*  946 */       Iterator<OutlierList> iterator = outlierListCollection.iterator();
/*  947 */       while (iterator.hasNext()) {
/*  948 */         OutlierList list = iterator.next();
/*  949 */         Outlier outlier = list.getAveragedOutlier();
/*  950 */         Point2D point = outlier.getPoint();
/*      */         
/*  952 */         if (list.isMultiple()) {
/*  953 */           drawMultipleEllipse(point, state.getBarWidth(), oRadius, g2);
/*      */           
/*      */           continue;
/*      */         } 
/*  957 */         drawEllipse(point, oRadius, g2);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  962 */       if (outlierListCollection.isHighFarOut()) {
/*  963 */         drawHighFarOut(aRadius / 2.0D, g2, xx + state
/*  964 */             .getBarWidth() / 2.0D, maxAxisValue);
/*      */       }
/*      */       
/*  967 */       if (outlierListCollection.isLowFarOut()) {
/*  968 */         drawLowFarOut(aRadius / 2.0D, g2, xx + state
/*  969 */             .getBarWidth() / 2.0D, minAxisValue);
/*      */       }
/*      */     } 
/*      */     
/*  973 */     if (state.getInfo() != null && box != null) {
/*  974 */       EntityCollection entities = state.getEntityCollection();
/*  975 */       if (entities != null) {
/*  976 */         addItemEntity(entities, dataset, row, column, box);
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
/*      */   private void drawEllipse(Point2D point, double oRadius, Graphics2D g2) {
/*  991 */     Ellipse2D dot = new Ellipse2D.Double(point.getX() + oRadius / 2.0D, point.getY(), oRadius, oRadius);
/*  992 */     g2.draw(dot);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawMultipleEllipse(Point2D point, double boxWidth, double oRadius, Graphics2D g2) {
/* 1007 */     Ellipse2D dot1 = new Ellipse2D.Double(point.getX() - boxWidth / 2.0D + oRadius, point.getY(), oRadius, oRadius);
/*      */     
/* 1009 */     Ellipse2D dot2 = new Ellipse2D.Double(point.getX() + boxWidth / 2.0D, point.getY(), oRadius, oRadius);
/* 1010 */     g2.draw(dot1);
/* 1011 */     g2.draw(dot2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawHighFarOut(double aRadius, Graphics2D g2, double xx, double m) {
/* 1024 */     double side = aRadius * 2.0D;
/* 1025 */     g2.draw(new Line2D.Double(xx - side, m + side, xx + side, m + side));
/* 1026 */     g2.draw(new Line2D.Double(xx - side, m + side, xx, m));
/* 1027 */     g2.draw(new Line2D.Double(xx + side, m + side, xx, m));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawLowFarOut(double aRadius, Graphics2D g2, double xx, double m) {
/* 1040 */     double side = aRadius * 2.0D;
/* 1041 */     g2.draw(new Line2D.Double(xx - side, m - side, xx + side, m - side));
/* 1042 */     g2.draw(new Line2D.Double(xx - side, m - side, xx, m));
/* 1043 */     g2.draw(new Line2D.Double(xx + side, m - side, xx, m));
/*      */   }
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
/* 1055 */     if (obj == this) {
/* 1056 */       return true;
/*      */     }
/* 1058 */     if (!(obj instanceof BoxAndWhiskerRenderer)) {
/* 1059 */       return false;
/*      */     }
/* 1061 */     BoxAndWhiskerRenderer that = (BoxAndWhiskerRenderer)obj;
/* 1062 */     if (this.fillBox != that.fillBox) {
/* 1063 */       return false;
/*      */     }
/* 1065 */     if (this.itemMargin != that.itemMargin) {
/* 1066 */       return false;
/*      */     }
/* 1068 */     if (this.maximumBarWidth != that.maximumBarWidth) {
/* 1069 */       return false;
/*      */     }
/* 1071 */     if (this.meanVisible != that.meanVisible) {
/* 1072 */       return false;
/*      */     }
/* 1074 */     if (this.medianVisible != that.medianVisible) {
/* 1075 */       return false;
/*      */     }
/* 1077 */     if (this.useOutlinePaintForWhiskers != that.useOutlinePaintForWhiskers)
/*      */     {
/* 1079 */       return false;
/*      */     }
/* 1081 */     if (this.whiskerWidth != that.whiskerWidth) {
/* 1082 */       return false;
/*      */     }
/* 1084 */     if (!PaintUtilities.equal(this.artifactPaint, that.artifactPaint)) {
/* 1085 */       return false;
/*      */     }
/* 1087 */     return super.equals(obj);
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
/* 1098 */     stream.defaultWriteObject();
/* 1099 */     SerialUtilities.writePaint(this.artifactPaint, stream);
/*      */   }
/*      */ 
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
/* 1112 */     stream.defaultReadObject();
/* 1113 */     this.artifactPaint = SerialUtilities.readPaint(stream);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/BoxAndWhiskerRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */