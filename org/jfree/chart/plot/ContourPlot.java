/*      */ package org.jfree.chart.plot;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Ellipse2D;
/*      */ import java.awt.geom.GeneralPath;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.geom.RectangularShape;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ResourceBundle;
/*      */ import org.jfree.chart.ClipPath;
/*      */ import org.jfree.chart.annotations.XYAnnotation;
/*      */ import org.jfree.chart.axis.AxisSpace;
/*      */ import org.jfree.chart.axis.ColorBar;
/*      */ import org.jfree.chart.axis.NumberAxis;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.entity.ChartEntity;
/*      */ import org.jfree.chart.entity.ContourEntity;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.event.AxisChangeEvent;
/*      */ import org.jfree.chart.labels.ContourToolTipGenerator;
/*      */ import org.jfree.chart.labels.StandardContourToolTipGenerator;
/*      */ import org.jfree.chart.urls.XYURLGenerator;
/*      */ import org.jfree.chart.util.ResourceBundleWrapper;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.contour.ContourDataset;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.general.DatasetChangeEvent;
/*      */ import org.jfree.data.general.DatasetUtilities;
/*      */ import org.jfree.data.xy.XYDataset;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.RectangleInsets;
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
/*      */ public class ContourPlot
/*      */   extends Plot
/*      */   implements ContourValuePlot, ValueAxisPlot, PropertyChangeListener, Serializable, Cloneable
/*      */ {
/*      */   private static final long serialVersionUID = 7861072556590502247L;
/*  130 */   protected static final RectangleInsets DEFAULT_INSETS = new RectangleInsets(2.0D, 2.0D, 100.0D, 10.0D);
/*      */ 
/*      */ 
/*      */   
/*      */   private ValueAxis domainAxis;
/*      */ 
/*      */   
/*      */   private ValueAxis rangeAxis;
/*      */ 
/*      */   
/*      */   private ContourDataset dataset;
/*      */ 
/*      */   
/*  143 */   private ColorBar colorBar = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RectangleEdge colorBarLocation;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean domainCrosshairVisible;
/*      */ 
/*      */ 
/*      */   
/*      */   private double domainCrosshairValue;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke domainCrosshairStroke;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint domainCrosshairPaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean domainCrosshairLockedOnData = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean rangeCrosshairVisible;
/*      */ 
/*      */ 
/*      */   
/*      */   private double rangeCrosshairValue;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke rangeCrosshairStroke;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint rangeCrosshairPaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean rangeCrosshairLockedOnData = true;
/*      */ 
/*      */ 
/*      */   
/*  193 */   private double dataAreaRatio = 0.0D;
/*      */ 
/*      */ 
/*      */   
/*      */   private List domainMarkers;
/*      */ 
/*      */ 
/*      */   
/*      */   private List rangeMarkers;
/*      */ 
/*      */ 
/*      */   
/*      */   private List annotations;
/*      */ 
/*      */ 
/*      */   
/*      */   private ContourToolTipGenerator toolTipGenerator;
/*      */ 
/*      */ 
/*      */   
/*      */   private XYURLGenerator urlGenerator;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean renderAsPoints = false;
/*      */ 
/*      */   
/*  220 */   private double ptSizePct = 0.05D;
/*      */ 
/*      */   
/*  223 */   private transient ClipPath clipPath = null;
/*      */ 
/*      */   
/*  226 */   private transient Paint missingPaint = null;
/*      */ 
/*      */ 
/*      */   
/*  230 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ContourPlot() {
/*  237 */     this((ContourDataset)null, (ValueAxis)null, (ValueAxis)null, (ColorBar)null);
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
/*      */   public ContourPlot(ContourDataset dataset, ValueAxis domainAxis, ValueAxis rangeAxis, ColorBar colorBar) {
/*  255 */     this.dataset = dataset;
/*  256 */     if (dataset != null) {
/*  257 */       dataset.addChangeListener(this);
/*      */     }
/*      */     
/*  260 */     this.domainAxis = domainAxis;
/*  261 */     if (domainAxis != null) {
/*  262 */       domainAxis.setPlot(this);
/*  263 */       domainAxis.addChangeListener(this);
/*      */     } 
/*      */     
/*  266 */     this.rangeAxis = rangeAxis;
/*  267 */     if (rangeAxis != null) {
/*  268 */       rangeAxis.setPlot(this);
/*  269 */       rangeAxis.addChangeListener(this);
/*      */     } 
/*      */     
/*  272 */     this.colorBar = colorBar;
/*  273 */     if (colorBar != null) {
/*  274 */       colorBar.getAxis().setPlot(this);
/*  275 */       colorBar.getAxis().addChangeListener(this);
/*  276 */       colorBar.configure(this);
/*      */     } 
/*  278 */     this.colorBarLocation = RectangleEdge.LEFT;
/*      */     
/*  280 */     this.toolTipGenerator = (ContourToolTipGenerator)new StandardContourToolTipGenerator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleEdge getColorBarLocation() {
/*  290 */     return this.colorBarLocation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColorBarLocation(RectangleEdge edge) {
/*  300 */     this.colorBarLocation = edge;
/*  301 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ContourDataset getDataset() {
/*  310 */     return this.dataset;
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
/*      */   public void setDataset(ContourDataset dataset) {
/*  323 */     ContourDataset existing = this.dataset;
/*  324 */     if (existing != null) {
/*  325 */       existing.removeChangeListener(this);
/*      */     }
/*      */ 
/*      */     
/*  329 */     this.dataset = dataset;
/*  330 */     if (dataset != null) {
/*  331 */       setDatasetGroup(dataset.getGroup());
/*  332 */       dataset.addChangeListener(this);
/*      */     } 
/*      */ 
/*      */     
/*  336 */     DatasetChangeEvent event = new DatasetChangeEvent(this, (Dataset)dataset);
/*  337 */     datasetChanged(event);
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
/*  348 */     ValueAxis result = this.domainAxis;
/*      */     
/*  350 */     return result;
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
/*      */   public void setDomainAxis(ValueAxis axis) {
/*  362 */     if (isCompatibleDomainAxis(axis)) {
/*      */       
/*  364 */       if (axis != null) {
/*  365 */         axis.setPlot(this);
/*  366 */         axis.addChangeListener(this);
/*      */       } 
/*      */ 
/*      */       
/*  370 */       if (this.domainAxis != null) {
/*  371 */         this.domainAxis.removeChangeListener(this);
/*      */       }
/*      */       
/*  374 */       this.domainAxis = axis;
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
/*      */   public ValueAxis getRangeAxis() {
/*  388 */     ValueAxis result = this.rangeAxis;
/*      */     
/*  390 */     return result;
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
/*  404 */     if (axis != null) {
/*  405 */       axis.setPlot(this);
/*  406 */       axis.addChangeListener(this);
/*      */     } 
/*      */ 
/*      */     
/*  410 */     if (this.rangeAxis != null) {
/*  411 */       this.rangeAxis.removeChangeListener(this);
/*      */     }
/*      */     
/*  414 */     this.rangeAxis = axis;
/*  415 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColorBarAxis(ColorBar axis) {
/*  426 */     this.colorBar = axis;
/*  427 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDataAreaRatio() {
/*  437 */     return this.dataAreaRatio;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataAreaRatio(double ratio) {
/*  446 */     this.dataAreaRatio = ratio;
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
/*      */   public void addDomainMarker(Marker marker) {
/*  459 */     if (this.domainMarkers == null) {
/*  460 */       this.domainMarkers = new ArrayList();
/*      */     }
/*  462 */     this.domainMarkers.add(marker);
/*  463 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearDomainMarkers() {
/*  471 */     if (this.domainMarkers != null) {
/*  472 */       this.domainMarkers.clear();
/*  473 */       fireChangeEvent();
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
/*      */   public void addRangeMarker(Marker marker) {
/*  487 */     if (this.rangeMarkers == null) {
/*  488 */       this.rangeMarkers = new ArrayList();
/*      */     }
/*  490 */     this.rangeMarkers.add(marker);
/*  491 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearRangeMarkers() {
/*  499 */     if (this.rangeMarkers != null) {
/*  500 */       this.rangeMarkers.clear();
/*  501 */       fireChangeEvent();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAnnotation(XYAnnotation annotation) {
/*  512 */     if (this.annotations == null) {
/*  513 */       this.annotations = new ArrayList();
/*      */     }
/*  515 */     this.annotations.add(annotation);
/*  516 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearAnnotations() {
/*  524 */     if (this.annotations != null) {
/*  525 */       this.annotations.clear();
/*  526 */       fireChangeEvent();
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
/*      */   public boolean isCompatibleDomainAxis(ValueAxis axis) {
/*  540 */     return true;
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
/*      */   public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info) {
/*  565 */     boolean b1 = (area.getWidth() <= 10.0D);
/*  566 */     boolean b2 = (area.getHeight() <= 10.0D);
/*  567 */     if (b1 || b2) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  572 */     if (info != null) {
/*  573 */       info.setPlotArea(area);
/*      */     }
/*      */ 
/*      */     
/*  577 */     RectangleInsets insets = getInsets();
/*  578 */     insets.trim(area);
/*      */     
/*  580 */     AxisSpace space = new AxisSpace();
/*      */     
/*  582 */     space = this.domainAxis.reserveSpace(g2, this, area, RectangleEdge.BOTTOM, space);
/*      */     
/*  584 */     space = this.rangeAxis.reserveSpace(g2, this, area, RectangleEdge.LEFT, space);
/*      */ 
/*      */     
/*  587 */     Rectangle2D estimatedDataArea = space.shrink(area, null);
/*      */     
/*  589 */     AxisSpace space2 = new AxisSpace();
/*  590 */     space2 = this.colorBar.reserveSpace(g2, this, area, estimatedDataArea, this.colorBarLocation, space2);
/*      */     
/*  592 */     Rectangle2D adjustedPlotArea = space2.shrink(area, null);
/*      */     
/*  594 */     Rectangle2D dataArea = space.shrink(adjustedPlotArea, null);
/*      */     
/*  596 */     Rectangle2D colorBarArea = space2.reserved(area, this.colorBarLocation);
/*      */ 
/*      */     
/*  599 */     if (getDataAreaRatio() != 0.0D) {
/*  600 */       double ratio = getDataAreaRatio();
/*  601 */       Rectangle2D tmpDataArea = (Rectangle2D)dataArea.clone();
/*  602 */       double h = tmpDataArea.getHeight();
/*  603 */       double w = tmpDataArea.getWidth();
/*      */       
/*  605 */       if (ratio > 0.0D) {
/*  606 */         if (w * ratio <= h) {
/*  607 */           h = ratio * w;
/*      */         } else {
/*      */           
/*  610 */           w = h / ratio;
/*      */         } 
/*      */       } else {
/*      */         
/*  614 */         ratio *= -1.0D;
/*  615 */         double xLength = getDomainAxis().getRange().getLength();
/*  616 */         double yLength = getRangeAxis().getRange().getLength();
/*  617 */         double unitRatio = yLength / xLength;
/*      */         
/*  619 */         ratio = unitRatio * ratio;
/*      */         
/*  621 */         if (w * ratio <= h) {
/*  622 */           h = ratio * w;
/*      */         } else {
/*      */           
/*  625 */           w = h / ratio;
/*      */         } 
/*      */       } 
/*      */       
/*  629 */       dataArea.setRect(tmpDataArea.getX() + tmpDataArea.getWidth() / 2.0D - w / 2.0D, tmpDataArea
/*  630 */           .getY(), w, h);
/*      */     } 
/*      */     
/*  633 */     if (info != null) {
/*  634 */       info.setDataArea(dataArea);
/*      */     }
/*      */     
/*  637 */     CrosshairState crosshairState = new CrosshairState();
/*  638 */     crosshairState.setCrosshairDistance(Double.POSITIVE_INFINITY);
/*      */ 
/*      */     
/*  641 */     drawBackground(g2, dataArea);
/*      */     
/*  643 */     double cursor = dataArea.getMaxY();
/*  644 */     if (this.domainAxis != null) {
/*  645 */       this.domainAxis.draw(g2, cursor, adjustedPlotArea, dataArea, RectangleEdge.BOTTOM, info);
/*      */     }
/*      */ 
/*      */     
/*  649 */     if (this.rangeAxis != null) {
/*  650 */       cursor = dataArea.getMinX();
/*  651 */       this.rangeAxis.draw(g2, cursor, adjustedPlotArea, dataArea, RectangleEdge.LEFT, info);
/*      */     } 
/*      */ 
/*      */     
/*  655 */     if (this.colorBar != null) {
/*  656 */       cursor = 0.0D;
/*  657 */       this.colorBar.draw(g2, cursor, adjustedPlotArea, dataArea, colorBarArea, this.colorBarLocation);
/*      */     } 
/*      */     
/*  660 */     Shape originalClip = g2.getClip();
/*  661 */     Composite originalComposite = g2.getComposite();
/*      */     
/*  663 */     g2.clip(dataArea);
/*  664 */     g2.setComposite(AlphaComposite.getInstance(3, 
/*  665 */           getForegroundAlpha()));
/*  666 */     render(g2, dataArea, info, crosshairState);
/*      */     
/*  668 */     if (this.domainMarkers != null) {
/*  669 */       Iterator<Marker> iterator = this.domainMarkers.iterator();
/*  670 */       while (iterator.hasNext()) {
/*  671 */         Marker marker = iterator.next();
/*  672 */         drawDomainMarker(g2, this, getDomainAxis(), marker, dataArea);
/*      */       } 
/*      */     } 
/*      */     
/*  676 */     if (this.rangeMarkers != null) {
/*  677 */       Iterator<Marker> iterator = this.rangeMarkers.iterator();
/*  678 */       while (iterator.hasNext()) {
/*  679 */         Marker marker = iterator.next();
/*  680 */         drawRangeMarker(g2, this, getRangeAxis(), marker, dataArea);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  701 */     g2.setClip(originalClip);
/*  702 */     g2.setComposite(originalComposite);
/*  703 */     drawOutline(g2, dataArea);
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
/*      */   public void render(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info, CrosshairState crosshairState) {
/*  724 */     ContourDataset data = getDataset();
/*  725 */     if (data != null) {
/*      */       
/*  727 */       ColorBar zAxis = getColorBar();
/*      */       
/*  729 */       if (this.clipPath != null) {
/*  730 */         GeneralPath clipper = getClipPath().draw(g2, dataArea, this.domainAxis, this.rangeAxis);
/*      */         
/*  732 */         if (this.clipPath.isClip()) {
/*  733 */           g2.clip(clipper);
/*      */         }
/*      */       } 
/*      */       
/*  737 */       if (this.renderAsPoints) {
/*  738 */         pointRenderer(g2, dataArea, info, this, this.domainAxis, this.rangeAxis, zAxis, data, crosshairState);
/*      */       }
/*      */       else {
/*      */         
/*  742 */         contourRenderer(g2, dataArea, info, this, this.domainAxis, this.rangeAxis, zAxis, data, crosshairState);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  747 */       setDomainCrosshairValue(crosshairState.getCrosshairX(), false);
/*  748 */       if (isDomainCrosshairVisible()) {
/*  749 */         drawVerticalLine(g2, dataArea, 
/*  750 */             getDomainCrosshairValue(), 
/*  751 */             getDomainCrosshairStroke(), 
/*  752 */             getDomainCrosshairPaint());
/*      */       }
/*      */ 
/*      */       
/*  756 */       setRangeCrosshairValue(crosshairState.getCrosshairY(), false);
/*  757 */       if (isRangeCrosshairVisible()) {
/*  758 */         drawHorizontalLine(g2, dataArea, 
/*  759 */             getRangeCrosshairValue(), 
/*  760 */             getRangeCrosshairStroke(), 
/*  761 */             getRangeCrosshairPaint());
/*      */       
/*      */       }
/*      */     }
/*  765 */     else if (this.clipPath != null) {
/*  766 */       getClipPath().draw(g2, dataArea, this.domainAxis, this.rangeAxis);
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
/*      */   public void contourRenderer(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info, ContourPlot plot, ValueAxis horizontalAxis, ValueAxis verticalAxis, ColorBar colorBar, ContourDataset data, CrosshairState crosshairState) {
/*  797 */     EntityCollection entities = null;
/*  798 */     if (info != null) {
/*  799 */       entities = info.getOwner().getEntityCollection();
/*      */     }
/*      */ 
/*      */     
/*  803 */     Rectangle2D.Double rect = new Rectangle2D.Double();
/*      */ 
/*      */     
/*  806 */     Object antiAlias = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
/*  807 */     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
/*      */ 
/*      */ 
/*      */     
/*  811 */     Number[] xNumber = data.getXValues();
/*  812 */     Number[] yNumber = data.getYValues();
/*  813 */     Number[] zNumber = data.getZValues();
/*      */     
/*  815 */     double[] x = new double[xNumber.length];
/*  816 */     double[] y = new double[yNumber.length];
/*      */     
/*  818 */     for (int i = 0; i < x.length; i++) {
/*  819 */       x[i] = xNumber[i].doubleValue();
/*  820 */       y[i] = yNumber[i].doubleValue();
/*      */     } 
/*      */     
/*  823 */     int[] xIndex = data.indexX();
/*  824 */     int[] indexX = data.getXIndices();
/*  825 */     boolean vertInverted = ((NumberAxis)verticalAxis).isInverted();
/*  826 */     boolean horizInverted = false;
/*  827 */     if (horizontalAxis instanceof NumberAxis) {
/*  828 */       horizInverted = ((NumberAxis)horizontalAxis).isInverted();
/*      */     }
/*  830 */     double transX = 0.0D;
/*      */ 
/*      */ 
/*      */     
/*  834 */     double transDXp1 = 0.0D;
/*  835 */     double transDX = 0.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  840 */     double transDYp1 = 0.0D;
/*      */     
/*  842 */     int iMax = xIndex[xIndex.length - 1];
/*  843 */     for (int k = 0; k < x.length; k++) {
/*  844 */       double transY, transDYm1; int j = xIndex[k];
/*  845 */       if (indexX[j] == k) {
/*  846 */         double transDXm1; if (j == 0) {
/*  847 */           transX = horizontalAxis.valueToJava2D(x[k], dataArea, RectangleEdge.BOTTOM);
/*      */           
/*  849 */           double transXm1 = transX;
/*  850 */           double transXp1 = horizontalAxis.valueToJava2D(x[indexX[j + 1]], dataArea, RectangleEdge.BOTTOM);
/*      */           
/*  852 */           transDXm1 = Math.abs(0.5D * (transX - transXm1));
/*  853 */           transDXp1 = Math.abs(0.5D * (transX - transXp1));
/*      */         }
/*  855 */         else if (j == iMax) {
/*  856 */           transX = horizontalAxis.valueToJava2D(x[k], dataArea, RectangleEdge.BOTTOM);
/*      */           
/*  858 */           double transXm1 = horizontalAxis.valueToJava2D(x[indexX[j - 1]], dataArea, RectangleEdge.BOTTOM);
/*      */           
/*  860 */           double transXp1 = transX;
/*  861 */           transDXm1 = Math.abs(0.5D * (transX - transXm1));
/*  862 */           transDXp1 = Math.abs(0.5D * (transX - transXp1));
/*      */         } else {
/*      */           
/*  865 */           transX = horizontalAxis.valueToJava2D(x[k], dataArea, RectangleEdge.BOTTOM);
/*      */           
/*  867 */           double transXp1 = horizontalAxis.valueToJava2D(x[indexX[j + 1]], dataArea, RectangleEdge.BOTTOM);
/*      */           
/*  869 */           transDXm1 = transDXp1;
/*  870 */           transDXp1 = Math.abs(0.5D * (transX - transXp1));
/*      */         } 
/*      */         
/*  873 */         if (horizInverted) {
/*  874 */           transX -= transDXp1;
/*      */         } else {
/*      */           
/*  877 */           transX -= transDXm1;
/*      */         } 
/*      */         
/*  880 */         transDX = transDXm1 + transDXp1;
/*      */         
/*  882 */         transY = verticalAxis.valueToJava2D(y[k], dataArea, RectangleEdge.LEFT);
/*      */         
/*  884 */         double transYm1 = transY;
/*  885 */         if (k + 1 == y.length) {
/*      */           continue;
/*      */         }
/*  888 */         double transYp1 = verticalAxis.valueToJava2D(y[k + 1], dataArea, RectangleEdge.LEFT);
/*      */         
/*  890 */         transDYm1 = Math.abs(0.5D * (transY - transYm1));
/*  891 */         transDYp1 = Math.abs(0.5D * (transY - transYp1));
/*      */       }
/*  893 */       else if ((j < indexX.length - 1 && indexX[j + 1] - 1 == k) || k == x.length - 1) {
/*      */ 
/*      */         
/*  896 */         transY = verticalAxis.valueToJava2D(y[k], dataArea, RectangleEdge.LEFT);
/*      */         
/*  898 */         double transYm1 = verticalAxis.valueToJava2D(y[k - 1], dataArea, RectangleEdge.LEFT);
/*      */         
/*  900 */         double transYp1 = transY;
/*  901 */         transDYm1 = Math.abs(0.5D * (transY - transYm1));
/*  902 */         transDYp1 = Math.abs(0.5D * (transY - transYp1));
/*      */       } else {
/*      */         
/*  905 */         transY = verticalAxis.valueToJava2D(y[k], dataArea, RectangleEdge.LEFT);
/*      */         
/*  907 */         double transYp1 = verticalAxis.valueToJava2D(y[k + 1], dataArea, RectangleEdge.LEFT);
/*      */         
/*  909 */         transDYm1 = transDYp1;
/*  910 */         transDYp1 = Math.abs(0.5D * (transY - transYp1));
/*      */       } 
/*  912 */       if (vertInverted) {
/*  913 */         transY -= transDYm1;
/*      */       } else {
/*      */         
/*  916 */         transY -= transDYp1;
/*      */       } 
/*      */       
/*  919 */       double transDY = transDYm1 + transDYp1;
/*      */       
/*  921 */       rect.setRect(transX, transY, transDX, transDY);
/*  922 */       if (zNumber[k] != null) {
/*  923 */         g2.setPaint(colorBar.getPaint(zNumber[k].doubleValue()));
/*  924 */         g2.fill(rect);
/*      */       }
/*  926 */       else if (this.missingPaint != null) {
/*  927 */         g2.setPaint(this.missingPaint);
/*  928 */         g2.fill(rect);
/*      */       } 
/*      */       
/*  931 */       Rectangle2D.Double entityArea = rect;
/*      */ 
/*      */       
/*  934 */       if (entities != null) {
/*  935 */         String tip = "";
/*  936 */         if (getToolTipGenerator() != null) {
/*  937 */           tip = this.toolTipGenerator.generateToolTip(data, k);
/*      */         }
/*      */ 
/*      */         
/*  941 */         String url = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  948 */         ContourEntity entity = new ContourEntity((Rectangle2D.Double)entityArea.clone(), tip, url);
/*  949 */         entity.setIndex(k);
/*  950 */         entities.add((ChartEntity)entity);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  955 */       if (plot.isDomainCrosshairLockedOnData()) {
/*  956 */         if (plot.isRangeCrosshairLockedOnData())
/*      */         {
/*  958 */           crosshairState.updateCrosshairPoint(x[k], y[k], transX, transY, PlotOrientation.VERTICAL);
/*      */         
/*      */         }
/*      */         else
/*      */         {
/*  963 */           crosshairState.updateCrosshairX(transX);
/*      */         }
/*      */       
/*      */       }
/*  967 */       else if (plot.isRangeCrosshairLockedOnData()) {
/*      */         
/*  969 */         crosshairState.updateCrosshairY(transY);
/*      */       } 
/*      */       
/*      */       continue;
/*      */     } 
/*  974 */     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias);
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
/*      */   public void pointRenderer(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info, ContourPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, ColorBar colorBar, ContourDataset data, CrosshairState crosshairState) {
/* 1004 */     EntityCollection entities = null;
/* 1005 */     if (info != null) {
/* 1006 */       entities = info.getOwner().getEntityCollection();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1011 */     RectangularShape rect = new Ellipse2D.Double();
/*      */ 
/*      */ 
/*      */     
/* 1015 */     Object antiAlias = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
/* 1016 */     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1021 */     Number[] xNumber = data.getXValues();
/* 1022 */     Number[] yNumber = data.getYValues();
/* 1023 */     Number[] zNumber = data.getZValues();
/*      */     
/* 1025 */     double[] x = new double[xNumber.length];
/* 1026 */     double[] y = new double[yNumber.length];
/*      */     
/* 1028 */     for (int i = 0; i < x.length; i++) {
/* 1029 */       x[i] = xNumber[i].doubleValue();
/* 1030 */       y[i] = yNumber[i].doubleValue();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1037 */     double size = dataArea.getWidth() * this.ptSizePct;
/* 1038 */     for (int k = 0; k < x.length; k++) {
/*      */       
/* 1040 */       double transX = domainAxis.valueToJava2D(x[k], dataArea, RectangleEdge.BOTTOM) - 0.5D * size;
/*      */       
/* 1042 */       double transY = rangeAxis.valueToJava2D(y[k], dataArea, RectangleEdge.LEFT) - 0.5D * size;
/*      */       
/* 1044 */       double transDX = size;
/* 1045 */       double transDY = size;
/*      */       
/* 1047 */       rect.setFrame(transX, transY, transDX, transDY);
/*      */       
/* 1049 */       if (zNumber[k] != null) {
/* 1050 */         g2.setPaint(colorBar.getPaint(zNumber[k].doubleValue()));
/* 1051 */         g2.fill(rect);
/*      */       }
/* 1053 */       else if (this.missingPaint != null) {
/* 1054 */         g2.setPaint(this.missingPaint);
/* 1055 */         g2.fill(rect);
/*      */       } 
/*      */ 
/*      */       
/* 1059 */       RectangularShape entityArea = rect;
/*      */ 
/*      */       
/* 1062 */       if (entities != null) {
/* 1063 */         String tip = null;
/* 1064 */         if (getToolTipGenerator() != null) {
/* 1065 */           tip = this.toolTipGenerator.generateToolTip(data, k);
/*      */         }
/* 1067 */         String url = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1074 */         ContourEntity entity = new ContourEntity((RectangularShape)entityArea.clone(), tip, url);
/* 1075 */         entity.setIndex(k);
/* 1076 */         entities.add((ChartEntity)entity);
/*      */       } 
/*      */ 
/*      */       
/* 1080 */       if (plot.isDomainCrosshairLockedOnData()) {
/* 1081 */         if (plot.isRangeCrosshairLockedOnData())
/*      */         {
/* 1083 */           crosshairState.updateCrosshairPoint(x[k], y[k], transX, transY, PlotOrientation.VERTICAL);
/*      */         
/*      */         }
/*      */         else
/*      */         {
/* 1088 */           crosshairState.updateCrosshairX(transX);
/*      */         }
/*      */       
/*      */       }
/* 1092 */       else if (plot.isRangeCrosshairLockedOnData()) {
/*      */         
/* 1094 */         crosshairState.updateCrosshairY(transY);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1100 */     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias);
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
/*      */   protected void drawVerticalLine(Graphics2D g2, Rectangle2D dataArea, double value, Stroke stroke, Paint paint) {
/* 1116 */     double xx = getDomainAxis().valueToJava2D(value, dataArea, RectangleEdge.BOTTOM);
/*      */ 
/*      */     
/* 1119 */     Line2D line = new Line2D.Double(xx, dataArea.getMinY(), xx, dataArea.getMaxY());
/* 1120 */     g2.setStroke(stroke);
/* 1121 */     g2.setPaint(paint);
/* 1122 */     g2.draw(line);
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
/*      */   protected void drawHorizontalLine(Graphics2D g2, Rectangle2D dataArea, double value, Stroke stroke, Paint paint) {
/* 1139 */     double yy = getRangeAxis().valueToJava2D(value, dataArea, RectangleEdge.LEFT);
/*      */ 
/*      */     
/* 1142 */     Line2D line = new Line2D.Double(dataArea.getMinX(), yy, dataArea.getMaxX(), yy);
/* 1143 */     g2.setStroke(stroke);
/* 1144 */     g2.setPaint(paint);
/* 1145 */     g2.draw(line);
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
/*      */   public void handleClick(int x, int y, PlotRenderingInfo info) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoom(double percent) {
/* 1190 */     if (percent <= 0.0D) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1200 */       getRangeAxis().setAutoRange(true);
/* 1201 */       getDomainAxis().setAutoRange(true);
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
/*      */   public String getPlotType() {
/* 1213 */     return localizationResources.getString("Contour_Plot");
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
/*      */   public Range getDataRange(ValueAxis axis) {
/* 1226 */     if (this.dataset == null) {
/* 1227 */       return null;
/*      */     }
/*      */     
/* 1230 */     Range result = null;
/*      */     
/* 1232 */     if (axis == getDomainAxis()) {
/* 1233 */       result = DatasetUtilities.findDomainBounds((XYDataset)this.dataset);
/*      */     }
/* 1235 */     else if (axis == getRangeAxis()) {
/* 1236 */       result = DatasetUtilities.findRangeBounds((XYDataset)this.dataset);
/*      */     } 
/* 1238 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Range getContourDataRange() {
/* 1248 */     Range result = null;
/* 1249 */     ContourDataset data = getDataset();
/* 1250 */     if (data != null) {
/* 1251 */       Range h = getDomainAxis().getRange();
/* 1252 */       Range v = getRangeAxis().getRange();
/* 1253 */       result = visibleRange(data, h, v);
/*      */     } 
/* 1255 */     return result;
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
/*      */   public void propertyChange(PropertyChangeEvent event) {
/* 1267 */     fireChangeEvent();
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
/*      */   public void datasetChanged(DatasetChangeEvent event) {
/* 1280 */     if (this.domainAxis != null) {
/* 1281 */       this.domainAxis.configure();
/*      */     }
/* 1283 */     if (this.rangeAxis != null) {
/* 1284 */       this.rangeAxis.configure();
/*      */     }
/* 1286 */     if (this.colorBar != null) {
/* 1287 */       this.colorBar.configure(this);
/*      */     }
/* 1289 */     super.datasetChanged(event);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorBar getColorBar() {
/* 1298 */     return this.colorBar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDomainCrosshairVisible() {
/* 1307 */     return this.domainCrosshairVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainCrosshairVisible(boolean flag) {
/* 1317 */     if (this.domainCrosshairVisible != flag) {
/* 1318 */       this.domainCrosshairVisible = flag;
/* 1319 */       fireChangeEvent();
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
/*      */   public boolean isDomainCrosshairLockedOnData() {
/* 1331 */     return this.domainCrosshairLockedOnData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainCrosshairLockedOnData(boolean flag) {
/* 1341 */     if (this.domainCrosshairLockedOnData != flag) {
/* 1342 */       this.domainCrosshairLockedOnData = flag;
/* 1343 */       fireChangeEvent();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDomainCrosshairValue() {
/* 1353 */     return this.domainCrosshairValue;
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
/*      */   public void setDomainCrosshairValue(double value) {
/* 1365 */     setDomainCrosshairValue(value, true);
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
/*      */   public void setDomainCrosshairValue(double value, boolean notify) {
/* 1379 */     this.domainCrosshairValue = value;
/* 1380 */     if (isDomainCrosshairVisible() && notify) {
/* 1381 */       fireChangeEvent();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getDomainCrosshairStroke() {
/* 1391 */     return this.domainCrosshairStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainCrosshairStroke(Stroke stroke) {
/* 1401 */     this.domainCrosshairStroke = stroke;
/* 1402 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getDomainCrosshairPaint() {
/* 1411 */     return this.domainCrosshairPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainCrosshairPaint(Paint paint) {
/* 1421 */     this.domainCrosshairPaint = paint;
/* 1422 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangeCrosshairVisible() {
/* 1431 */     return this.rangeCrosshairVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeCrosshairVisible(boolean flag) {
/* 1440 */     if (this.rangeCrosshairVisible != flag) {
/* 1441 */       this.rangeCrosshairVisible = flag;
/* 1442 */       fireChangeEvent();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangeCrosshairLockedOnData() {
/* 1453 */     return this.rangeCrosshairLockedOnData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeCrosshairLockedOnData(boolean flag) {
/* 1463 */     if (this.rangeCrosshairLockedOnData != flag) {
/* 1464 */       this.rangeCrosshairLockedOnData = flag;
/* 1465 */       fireChangeEvent();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getRangeCrosshairValue() {
/* 1475 */     return this.rangeCrosshairValue;
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
/*      */   public void setRangeCrosshairValue(double value) {
/* 1487 */     setRangeCrosshairValue(value, true);
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
/*      */   public void setRangeCrosshairValue(double value, boolean notify) {
/* 1501 */     this.rangeCrosshairValue = value;
/* 1502 */     if (isRangeCrosshairVisible() && notify) {
/* 1503 */       fireChangeEvent();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getRangeCrosshairStroke() {
/* 1513 */     return this.rangeCrosshairStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeCrosshairStroke(Stroke stroke) {
/* 1523 */     this.rangeCrosshairStroke = stroke;
/* 1524 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getRangeCrosshairPaint() {
/* 1533 */     return this.rangeCrosshairPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeCrosshairPaint(Paint paint) {
/* 1543 */     this.rangeCrosshairPaint = paint;
/* 1544 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ContourToolTipGenerator getToolTipGenerator() {
/* 1553 */     return this.toolTipGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToolTipGenerator(ContourToolTipGenerator generator) {
/* 1563 */     this.toolTipGenerator = generator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYURLGenerator getURLGenerator() {
/* 1572 */     return this.urlGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setURLGenerator(XYURLGenerator urlGenerator) {
/* 1582 */     this.urlGenerator = urlGenerator;
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
/*      */   public void drawDomainMarker(Graphics2D g2, ContourPlot plot, ValueAxis domainAxis, Marker marker, Rectangle2D dataArea) {
/* 1600 */     if (marker instanceof ValueMarker) {
/* 1601 */       ValueMarker vm = (ValueMarker)marker;
/* 1602 */       double value = vm.getValue();
/* 1603 */       Range range = domainAxis.getRange();
/* 1604 */       if (!range.contains(value)) {
/*      */         return;
/*      */       }
/*      */       
/* 1608 */       double x = domainAxis.valueToJava2D(value, dataArea, RectangleEdge.BOTTOM);
/*      */ 
/*      */       
/* 1611 */       Line2D line = new Line2D.Double(x, dataArea.getMinY(), x, dataArea.getMaxY());
/* 1612 */       Paint paint = marker.getOutlinePaint();
/* 1613 */       Stroke stroke = marker.getOutlineStroke();
/* 1614 */       g2.setPaint((paint != null) ? paint : Plot.DEFAULT_OUTLINE_PAINT);
/* 1615 */       g2.setStroke((stroke != null) ? stroke : Plot.DEFAULT_OUTLINE_STROKE);
/* 1616 */       g2.draw(line);
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
/*      */   public void drawRangeMarker(Graphics2D g2, ContourPlot plot, ValueAxis rangeAxis, Marker marker, Rectangle2D dataArea) {
/* 1636 */     if (marker instanceof ValueMarker) {
/* 1637 */       ValueMarker vm = (ValueMarker)marker;
/* 1638 */       double value = vm.getValue();
/* 1639 */       Range range = rangeAxis.getRange();
/* 1640 */       if (!range.contains(value)) {
/*      */         return;
/*      */       }
/*      */       
/* 1644 */       double y = rangeAxis.valueToJava2D(value, dataArea, RectangleEdge.LEFT);
/*      */ 
/*      */       
/* 1647 */       Line2D line = new Line2D.Double(dataArea.getMinX(), y, dataArea.getMaxX(), y);
/* 1648 */       Paint paint = marker.getOutlinePaint();
/* 1649 */       Stroke stroke = marker.getOutlineStroke();
/* 1650 */       g2.setPaint((paint != null) ? paint : Plot.DEFAULT_OUTLINE_PAINT);
/* 1651 */       g2.setStroke((stroke != null) ? stroke : Plot.DEFAULT_OUTLINE_STROKE);
/* 1652 */       g2.draw(line);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClipPath getClipPath() {
/* 1662 */     return this.clipPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClipPath(ClipPath clipPath) {
/* 1670 */     this.clipPath = clipPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getPtSizePct() {
/* 1678 */     return this.ptSizePct;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRenderAsPoints() {
/* 1686 */     return this.renderAsPoints;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPtSizePct(double ptSizePct) {
/* 1694 */     this.ptSizePct = ptSizePct;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderAsPoints(boolean renderAsPoints) {
/* 1702 */     this.renderAsPoints = renderAsPoints;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void axisChanged(AxisChangeEvent event) {
/* 1712 */     Object source = event.getSource();
/* 1713 */     if (source.equals(this.rangeAxis) || source.equals(this.domainAxis)) {
/* 1714 */       ColorBar cba = this.colorBar;
/* 1715 */       if (this.colorBar.getAxis().isAutoRange()) {
/* 1716 */         cba.getAxis().configure();
/*      */       }
/*      */     } 
/*      */     
/* 1720 */     super.axisChanged(event);
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
/*      */   public Range visibleRange(ContourDataset data, Range x, Range y) {
/* 1733 */     Range range = data.getZValueRange(x, y);
/* 1734 */     return range;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getMissingPaint() {
/* 1742 */     return this.missingPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMissingPaint(Paint paint) {
/* 1751 */     this.missingPaint = paint;
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
/*      */   public void zoomDomainAxes(double x, double y, double factor) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomDomainAxes(double x, double y, double lowerPercent, double upperPercent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomRangeAxes(double x, double y, double factor) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomRangeAxes(double x, double y, double lowerPercent, double upperPercent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDomainZoomable() {
/* 1809 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangeZoomable() {
/* 1818 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() throws CloneNotSupportedException {
/* 1827 */     ContourPlot clone = (ContourPlot)super.clone();
/*      */     
/* 1829 */     if (this.domainAxis != null) {
/* 1830 */       clone.domainAxis = (ValueAxis)this.domainAxis.clone();
/* 1831 */       clone.domainAxis.setPlot(clone);
/* 1832 */       clone.domainAxis.addChangeListener(clone);
/*      */     } 
/* 1834 */     if (this.rangeAxis != null) {
/* 1835 */       clone.rangeAxis = (ValueAxis)this.rangeAxis.clone();
/* 1836 */       clone.rangeAxis.setPlot(clone);
/* 1837 */       clone.rangeAxis.addChangeListener(clone);
/*      */     } 
/*      */     
/* 1840 */     if (clone.dataset != null) {
/* 1841 */       clone.dataset.addChangeListener(clone);
/*      */     }
/*      */     
/* 1844 */     if (this.colorBar != null) {
/* 1845 */       clone.colorBar = (ColorBar)this.colorBar.clone();
/*      */     }
/*      */     
/* 1848 */     clone.domainMarkers = (List)ObjectUtilities.deepClone(this.domainMarkers);
/*      */     
/* 1850 */     clone.rangeMarkers = (List)ObjectUtilities.deepClone(this.rangeMarkers);
/*      */     
/* 1852 */     clone.annotations = (List)ObjectUtilities.deepClone(this.annotations);
/*      */     
/* 1854 */     if (this.clipPath != null) {
/* 1855 */       clone.clipPath = (ClipPath)this.clipPath.clone();
/*      */     }
/*      */     
/* 1858 */     return clone;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/ContourPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */