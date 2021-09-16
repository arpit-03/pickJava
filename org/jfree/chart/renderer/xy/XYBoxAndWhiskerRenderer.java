/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.BoxAndWhiskerXYToolTipGenerator;
/*     */ import org.jfree.chart.labels.XYToolTipGenerator;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.Outlier;
/*     */ import org.jfree.chart.renderer.OutlierList;
/*     */ import org.jfree.chart.renderer.OutlierListCollection;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.statistics.BoxAndWhiskerXYDataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.util.PaintUtilities;
/*     */ import org.jfree.util.PublicCloneable;
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
/*     */ public class XYBoxAndWhiskerRenderer
/*     */   extends AbstractXYItemRenderer
/*     */   implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8020170108532232324L;
/*     */   private double boxWidth;
/*     */   private transient Paint boxPaint;
/*     */   private boolean fillBox;
/* 149 */   private transient Paint artifactPaint = Color.black;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYBoxAndWhiskerRenderer() {
/* 155 */     this(-1.0D);
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
/*     */   public XYBoxAndWhiskerRenderer(double boxWidth) {
/* 168 */     this.boxWidth = boxWidth;
/* 169 */     this.boxPaint = Color.green;
/* 170 */     this.fillBox = true;
/* 171 */     setBaseToolTipGenerator((XYToolTipGenerator)new BoxAndWhiskerXYToolTipGenerator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBoxWidth() {
/* 182 */     return this.boxWidth;
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
/*     */   public void setBoxWidth(double width) {
/* 197 */     if (width != this.boxWidth) {
/* 198 */       this.boxWidth = width;
/* 199 */       fireChangeEvent();
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
/*     */   public Paint getBoxPaint() {
/* 211 */     return this.boxPaint;
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
/*     */   public void setBoxPaint(Paint paint) {
/* 223 */     this.boxPaint = paint;
/* 224 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFillBox() {
/* 235 */     return this.fillBox;
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
/*     */   public void setFillBox(boolean flag) {
/* 247 */     this.fillBox = flag;
/* 248 */     fireChangeEvent();
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
/*     */   public Paint getArtifactPaint() {
/* 260 */     return this.artifactPaint;
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
/*     */   public void setArtifactPaint(Paint paint) {
/* 273 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 274 */     this.artifactPaint = paint;
/* 275 */     fireChangeEvent();
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
/*     */   public Range findRangeBounds(XYDataset dataset) {
/* 291 */     return findRangeBounds(dataset, true);
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
/*     */   protected Paint lookupBoxPaint(int series, int item) {
/* 307 */     Paint p = getBoxPaint();
/* 308 */     if (p != null) {
/* 309 */       return p;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 314 */     return getItemPaint(series, item);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/* 343 */     PlotOrientation orientation = plot.getOrientation();
/*     */     
/* 345 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 346 */       drawHorizontalItem(g2, dataArea, info, plot, domainAxis, rangeAxis, dataset, series, item, crosshairState, pass);
/*     */     
/*     */     }
/* 349 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 350 */       drawVerticalItem(g2, dataArea, info, plot, domainAxis, rangeAxis, dataset, series, item, crosshairState, pass);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawHorizontalItem(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/*     */     Shape box;
/* 380 */     EntityCollection entities = null;
/* 381 */     if (info != null) {
/* 382 */       entities = info.getOwner().getEntityCollection();
/*     */     }
/*     */     
/* 385 */     BoxAndWhiskerXYDataset boxAndWhiskerData = (BoxAndWhiskerXYDataset)dataset;
/*     */ 
/*     */     
/* 388 */     Number x = boxAndWhiskerData.getX(series, item);
/* 389 */     Number yMax = boxAndWhiskerData.getMaxRegularValue(series, item);
/* 390 */     Number yMin = boxAndWhiskerData.getMinRegularValue(series, item);
/* 391 */     Number yMedian = boxAndWhiskerData.getMedianValue(series, item);
/* 392 */     Number yAverage = boxAndWhiskerData.getMeanValue(series, item);
/* 393 */     Number yQ1Median = boxAndWhiskerData.getQ1Value(series, item);
/* 394 */     Number yQ3Median = boxAndWhiskerData.getQ3Value(series, item);
/*     */     
/* 396 */     double xx = domainAxis.valueToJava2D(x.doubleValue(), dataArea, plot
/* 397 */         .getDomainAxisEdge());
/*     */     
/* 399 */     RectangleEdge location = plot.getRangeAxisEdge();
/* 400 */     double yyMax = rangeAxis.valueToJava2D(yMax.doubleValue(), dataArea, location);
/*     */     
/* 402 */     double yyMin = rangeAxis.valueToJava2D(yMin.doubleValue(), dataArea, location);
/*     */     
/* 404 */     double yyMedian = rangeAxis.valueToJava2D(yMedian.doubleValue(), dataArea, location);
/*     */     
/* 406 */     double yyAverage = 0.0D;
/* 407 */     if (yAverage != null) {
/* 408 */       yyAverage = rangeAxis.valueToJava2D(yAverage.doubleValue(), dataArea, location);
/*     */     }
/*     */     
/* 411 */     double yyQ1Median = rangeAxis.valueToJava2D(yQ1Median.doubleValue(), dataArea, location);
/*     */     
/* 413 */     double yyQ3Median = rangeAxis.valueToJava2D(yQ3Median.doubleValue(), dataArea, location);
/*     */ 
/*     */     
/* 416 */     double exactBoxWidth = getBoxWidth();
/* 417 */     double width = exactBoxWidth;
/* 418 */     double dataAreaX = dataArea.getHeight();
/* 419 */     double maxBoxPercent = 0.1D;
/* 420 */     double maxBoxWidth = dataAreaX * maxBoxPercent;
/* 421 */     if (exactBoxWidth <= 0.0D) {
/* 422 */       int itemCount = boxAndWhiskerData.getItemCount(series);
/* 423 */       exactBoxWidth = dataAreaX / itemCount * 4.5D / 7.0D;
/* 424 */       if (exactBoxWidth < 3.0D) {
/* 425 */         width = 3.0D;
/*     */       }
/* 427 */       else if (exactBoxWidth > maxBoxWidth) {
/* 428 */         width = maxBoxWidth;
/*     */       } else {
/*     */         
/* 431 */         width = exactBoxWidth;
/*     */       } 
/*     */     } 
/*     */     
/* 435 */     g2.setPaint(getItemPaint(series, item));
/* 436 */     Stroke s = getItemStroke(series, item);
/* 437 */     g2.setStroke(s);
/*     */ 
/*     */     
/* 440 */     g2.draw(new Line2D.Double(yyMax, xx, yyQ3Median, xx));
/* 441 */     g2.draw(new Line2D.Double(yyMax, xx - width / 2.0D, yyMax, xx + width / 2.0D));
/*     */ 
/*     */ 
/*     */     
/* 445 */     g2.draw(new Line2D.Double(yyMin, xx, yyQ1Median, xx));
/* 446 */     g2.draw(new Line2D.Double(yyMin, xx - width / 2.0D, yyMin, xx + width / 2.0D));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 451 */     if (yyQ1Median < yyQ3Median) {
/* 452 */       box = new Rectangle2D.Double(yyQ1Median, xx - width / 2.0D, yyQ3Median - yyQ1Median, width);
/*     */     }
/*     */     else {
/*     */       
/* 456 */       box = new Rectangle2D.Double(yyQ3Median, xx - width / 2.0D, yyQ1Median - yyQ3Median, width);
/*     */     } 
/*     */     
/* 459 */     if (this.fillBox) {
/* 460 */       g2.setPaint(lookupBoxPaint(series, item));
/* 461 */       g2.fill(box);
/*     */     } 
/* 463 */     g2.setStroke(getItemOutlineStroke(series, item));
/* 464 */     g2.setPaint(getItemOutlinePaint(series, item));
/* 465 */     g2.draw(box);
/*     */ 
/*     */     
/* 468 */     g2.setPaint(getArtifactPaint());
/* 469 */     g2.draw(new Line2D.Double(yyMedian, xx - width / 2.0D, yyMedian, xx + width / 2.0D));
/*     */ 
/*     */ 
/*     */     
/* 473 */     if (yAverage != null) {
/* 474 */       double aRadius = width / 4.0D;
/*     */ 
/*     */       
/* 477 */       if (yyAverage > dataArea.getMinX() - aRadius && yyAverage < dataArea
/* 478 */         .getMaxX() + aRadius) {
/* 479 */         Ellipse2D.Double avgEllipse = new Ellipse2D.Double(yyAverage - aRadius, xx - aRadius, aRadius * 2.0D, aRadius * 2.0D);
/*     */ 
/*     */         
/* 482 */         g2.fill(avgEllipse);
/* 483 */         g2.draw(avgEllipse);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 490 */     if (entities != null && box.intersects(dataArea)) {
/* 491 */       addEntity(entities, box, dataset, series, item, yyAverage, xx);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawVerticalItem(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/*     */     Shape box;
/* 520 */     EntityCollection entities = null;
/* 521 */     if (info != null) {
/* 522 */       entities = info.getOwner().getEntityCollection();
/*     */     }
/*     */     
/* 525 */     BoxAndWhiskerXYDataset boxAndWhiskerData = (BoxAndWhiskerXYDataset)dataset;
/*     */ 
/*     */     
/* 528 */     Number x = boxAndWhiskerData.getX(series, item);
/* 529 */     Number yMax = boxAndWhiskerData.getMaxRegularValue(series, item);
/* 530 */     Number yMin = boxAndWhiskerData.getMinRegularValue(series, item);
/* 531 */     Number yMedian = boxAndWhiskerData.getMedianValue(series, item);
/* 532 */     Number yAverage = boxAndWhiskerData.getMeanValue(series, item);
/* 533 */     Number yQ1Median = boxAndWhiskerData.getQ1Value(series, item);
/* 534 */     Number yQ3Median = boxAndWhiskerData.getQ3Value(series, item);
/* 535 */     List<Number> yOutliers = boxAndWhiskerData.getOutliers(series, item);
/*     */ 
/*     */     
/* 538 */     if (yOutliers == null) {
/* 539 */       yOutliers = Collections.EMPTY_LIST;
/*     */     }
/*     */     
/* 542 */     double xx = domainAxis.valueToJava2D(x.doubleValue(), dataArea, plot
/* 543 */         .getDomainAxisEdge());
/*     */     
/* 545 */     RectangleEdge location = plot.getRangeAxisEdge();
/* 546 */     double yyMax = rangeAxis.valueToJava2D(yMax.doubleValue(), dataArea, location);
/*     */     
/* 548 */     double yyMin = rangeAxis.valueToJava2D(yMin.doubleValue(), dataArea, location);
/*     */     
/* 550 */     double yyMedian = rangeAxis.valueToJava2D(yMedian.doubleValue(), dataArea, location);
/*     */     
/* 552 */     double yyAverage = 0.0D;
/* 553 */     if (yAverage != null) {
/* 554 */       yyAverage = rangeAxis.valueToJava2D(yAverage.doubleValue(), dataArea, location);
/*     */     }
/*     */     
/* 557 */     double yyQ1Median = rangeAxis.valueToJava2D(yQ1Median.doubleValue(), dataArea, location);
/*     */     
/* 559 */     double yyQ3Median = rangeAxis.valueToJava2D(yQ3Median.doubleValue(), dataArea, location);
/*     */ 
/*     */ 
/*     */     
/* 563 */     double exactBoxWidth = getBoxWidth();
/* 564 */     double width = exactBoxWidth;
/* 565 */     double dataAreaX = dataArea.getMaxX() - dataArea.getMinX();
/* 566 */     double maxBoxPercent = 0.1D;
/* 567 */     double maxBoxWidth = dataAreaX * maxBoxPercent;
/* 568 */     if (exactBoxWidth <= 0.0D) {
/* 569 */       int itemCount = boxAndWhiskerData.getItemCount(series);
/* 570 */       exactBoxWidth = dataAreaX / itemCount * 4.5D / 7.0D;
/* 571 */       if (exactBoxWidth < 3.0D) {
/* 572 */         width = 3.0D;
/*     */       }
/* 574 */       else if (exactBoxWidth > maxBoxWidth) {
/* 575 */         width = maxBoxWidth;
/*     */       } else {
/*     */         
/* 578 */         width = exactBoxWidth;
/*     */       } 
/*     */     } 
/*     */     
/* 582 */     g2.setPaint(getItemPaint(series, item));
/* 583 */     Stroke s = getItemStroke(series, item);
/* 584 */     g2.setStroke(s);
/*     */ 
/*     */     
/* 587 */     g2.draw(new Line2D.Double(xx, yyMax, xx, yyQ3Median));
/* 588 */     g2.draw(new Line2D.Double(xx - width / 2.0D, yyMax, xx + width / 2.0D, yyMax));
/*     */ 
/*     */ 
/*     */     
/* 592 */     g2.draw(new Line2D.Double(xx, yyMin, xx, yyQ1Median));
/* 593 */     g2.draw(new Line2D.Double(xx - width / 2.0D, yyMin, xx + width / 2.0D, yyMin));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 598 */     if (yyQ1Median > yyQ3Median) {
/* 599 */       box = new Rectangle2D.Double(xx - width / 2.0D, yyQ3Median, width, yyQ1Median - yyQ3Median);
/*     */     }
/*     */     else {
/*     */       
/* 603 */       box = new Rectangle2D.Double(xx - width / 2.0D, yyQ1Median, width, yyQ3Median - yyQ1Median);
/*     */     } 
/*     */     
/* 606 */     if (this.fillBox) {
/* 607 */       g2.setPaint(lookupBoxPaint(series, item));
/* 608 */       g2.fill(box);
/*     */     } 
/* 610 */     g2.setStroke(getItemOutlineStroke(series, item));
/* 611 */     g2.setPaint(getItemOutlinePaint(series, item));
/* 612 */     g2.draw(box);
/*     */ 
/*     */     
/* 615 */     g2.setPaint(getArtifactPaint());
/* 616 */     g2.draw(new Line2D.Double(xx - width / 2.0D, yyMedian, xx + width / 2.0D, yyMedian));
/*     */ 
/*     */     
/* 619 */     double aRadius = 0.0D;
/* 620 */     double oRadius = width / 3.0D;
/*     */ 
/*     */     
/* 623 */     if (yAverage != null) {
/* 624 */       aRadius = width / 4.0D;
/*     */ 
/*     */       
/* 627 */       if (yyAverage > dataArea.getMinY() - aRadius && yyAverage < dataArea
/* 628 */         .getMaxY() + aRadius) {
/* 629 */         Ellipse2D.Double avgEllipse = new Ellipse2D.Double(xx - aRadius, yyAverage - aRadius, aRadius * 2.0D, aRadius * 2.0D);
/*     */         
/* 631 */         g2.fill(avgEllipse);
/* 632 */         g2.draw(avgEllipse);
/*     */       } 
/*     */     } 
/*     */     
/* 636 */     List<Outlier> outliers = new ArrayList();
/* 637 */     OutlierListCollection outlierListCollection = new OutlierListCollection();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 644 */     for (int i = 0; i < yOutliers.size(); i++) {
/* 645 */       double outlier = ((Number)yOutliers.get(i)).doubleValue();
/* 646 */       if (outlier > boxAndWhiskerData.getMaxOutlier(series, item)
/* 647 */         .doubleValue()) {
/* 648 */         outlierListCollection.setHighFarOut(true);
/*     */       }
/* 650 */       else if (outlier < boxAndWhiskerData.getMinOutlier(series, item)
/* 651 */         .doubleValue()) {
/* 652 */         outlierListCollection.setLowFarOut(true);
/*     */       }
/* 654 */       else if (outlier > boxAndWhiskerData.getMaxRegularValue(series, item)
/* 655 */         .doubleValue()) {
/* 656 */         double yyOutlier = rangeAxis.valueToJava2D(outlier, dataArea, location);
/*     */         
/* 658 */         outliers.add(new Outlier(xx, yyOutlier, oRadius));
/*     */       }
/* 660 */       else if (outlier < boxAndWhiskerData.getMinRegularValue(series, item)
/* 661 */         .doubleValue()) {
/* 662 */         double yyOutlier = rangeAxis.valueToJava2D(outlier, dataArea, location);
/*     */         
/* 664 */         outliers.add(new Outlier(xx, yyOutlier, oRadius));
/*     */       } 
/* 666 */       Collections.sort(outliers);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 671 */     for (Iterator<Outlier> iterator = outliers.iterator(); iterator.hasNext(); ) {
/* 672 */       Outlier outlier = iterator.next();
/* 673 */       outlierListCollection.add(outlier);
/*     */     } 
/*     */ 
/*     */     
/* 677 */     double maxAxisValue = rangeAxis.valueToJava2D(rangeAxis.getUpperBound(), dataArea, location) + aRadius;
/*     */     
/* 679 */     double minAxisValue = rangeAxis.valueToJava2D(rangeAxis.getLowerBound(), dataArea, location) - aRadius;
/*     */ 
/*     */ 
/*     */     
/* 683 */     Iterator<OutlierList> iterator1 = outlierListCollection.iterator();
/* 684 */     while (iterator1.hasNext()) {
/* 685 */       OutlierList list = iterator1.next();
/* 686 */       Outlier outlier = list.getAveragedOutlier();
/* 687 */       Point2D point = outlier.getPoint();
/*     */       
/* 689 */       if (list.isMultiple()) {
/* 690 */         drawMultipleEllipse(point, width, oRadius, g2);
/*     */         continue;
/*     */       } 
/* 693 */       drawEllipse(point, oRadius, g2);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 698 */     if (outlierListCollection.isHighFarOut()) {
/* 699 */       drawHighFarOut(aRadius, g2, xx, maxAxisValue);
/*     */     }
/*     */     
/* 702 */     if (outlierListCollection.isLowFarOut()) {
/* 703 */       drawLowFarOut(aRadius, g2, xx, minAxisValue);
/*     */     }
/*     */ 
/*     */     
/* 707 */     if (entities != null && box.intersects(dataArea)) {
/* 708 */       addEntity(entities, box, dataset, series, item, xx, yyAverage);
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
/*     */   protected void drawEllipse(Point2D point, double oRadius, Graphics2D g2) {
/* 722 */     Ellipse2D.Double dot = new Ellipse2D.Double(point.getX() + oRadius / 2.0D, point.getY(), oRadius, oRadius);
/* 723 */     g2.draw(dot);
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
/*     */   protected void drawMultipleEllipse(Point2D point, double boxWidth, double oRadius, Graphics2D g2) {
/* 738 */     Ellipse2D.Double dot1 = new Ellipse2D.Double(point.getX() - boxWidth / 2.0D + oRadius, point.getY(), oRadius, oRadius);
/*     */     
/* 740 */     Ellipse2D.Double dot2 = new Ellipse2D.Double(point.getX() + boxWidth / 2.0D, point.getY(), oRadius, oRadius);
/* 741 */     g2.draw(dot1);
/* 742 */     g2.draw(dot2);
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
/*     */   protected void drawHighFarOut(double aRadius, Graphics2D g2, double xx, double m) {
/* 756 */     double side = aRadius * 2.0D;
/* 757 */     g2.draw(new Line2D.Double(xx - side, m + side, xx + side, m + side));
/* 758 */     g2.draw(new Line2D.Double(xx - side, m + side, xx, m));
/* 759 */     g2.draw(new Line2D.Double(xx + side, m + side, xx, m));
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
/*     */   protected void drawLowFarOut(double aRadius, Graphics2D g2, double xx, double m) {
/* 772 */     double side = aRadius * 2.0D;
/* 773 */     g2.draw(new Line2D.Double(xx - side, m - side, xx + side, m - side));
/* 774 */     g2.draw(new Line2D.Double(xx - side, m - side, xx, m));
/* 775 */     g2.draw(new Line2D.Double(xx + side, m - side, xx, m));
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
/* 787 */     if (obj == this) {
/* 788 */       return true;
/*     */     }
/* 790 */     if (!(obj instanceof XYBoxAndWhiskerRenderer)) {
/* 791 */       return false;
/*     */     }
/* 793 */     if (!super.equals(obj)) {
/* 794 */       return false;
/*     */     }
/* 796 */     XYBoxAndWhiskerRenderer that = (XYBoxAndWhiskerRenderer)obj;
/* 797 */     if (this.boxWidth != that.getBoxWidth()) {
/* 798 */       return false;
/*     */     }
/* 800 */     if (!PaintUtilities.equal(this.boxPaint, that.boxPaint)) {
/* 801 */       return false;
/*     */     }
/* 803 */     if (!PaintUtilities.equal(this.artifactPaint, that.artifactPaint)) {
/* 804 */       return false;
/*     */     }
/* 806 */     if (this.fillBox != that.fillBox) {
/* 807 */       return false;
/*     */     }
/* 809 */     return true;
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
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 821 */     stream.defaultWriteObject();
/* 822 */     SerialUtilities.writePaint(this.boxPaint, stream);
/* 823 */     SerialUtilities.writePaint(this.artifactPaint, stream);
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 837 */     stream.defaultReadObject();
/* 838 */     this.boxPaint = SerialUtilities.readPaint(stream);
/* 839 */     this.artifactPaint = SerialUtilities.readPaint(stream);
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 851 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYBoxAndWhiskerRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */