/*      */ package org.jfree.chart.renderer.xy;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Font;
/*      */ import java.awt.GradientPaint;
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
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.jfree.chart.LegendItem;
/*      */ import org.jfree.chart.LegendItemCollection;
/*      */ import org.jfree.chart.annotations.XYAnnotation;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.entity.ChartEntity;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.entity.XYItemEntity;
/*      */ import org.jfree.chart.event.AnnotationChangeEvent;
/*      */ import org.jfree.chart.event.AnnotationChangeListener;
/*      */ import org.jfree.chart.labels.ItemLabelPosition;
/*      */ import org.jfree.chart.labels.StandardXYSeriesLabelGenerator;
/*      */ import org.jfree.chart.labels.XYItemLabelGenerator;
/*      */ import org.jfree.chart.labels.XYSeriesLabelGenerator;
/*      */ import org.jfree.chart.labels.XYToolTipGenerator;
/*      */ import org.jfree.chart.plot.CrosshairState;
/*      */ import org.jfree.chart.plot.DrawingSupplier;
/*      */ import org.jfree.chart.plot.IntervalMarker;
/*      */ import org.jfree.chart.plot.Marker;
/*      */ import org.jfree.chart.plot.Plot;
/*      */ import org.jfree.chart.plot.PlotOrientation;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.plot.ValueMarker;
/*      */ import org.jfree.chart.plot.XYPlot;
/*      */ import org.jfree.chart.renderer.AbstractRenderer;
/*      */ import org.jfree.chart.urls.XYURLGenerator;
/*      */ import org.jfree.chart.util.CloneUtils;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.general.DatasetUtilities;
/*      */ import org.jfree.data.xy.XYDataset;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.ui.GradientPaintTransformer;
/*      */ import org.jfree.ui.Layer;
/*      */ import org.jfree.ui.LengthAdjustmentType;
/*      */ import org.jfree.ui.RectangleAnchor;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractXYItemRenderer
/*      */   extends AbstractRenderer
/*      */   implements XYItemRenderer, AnnotationChangeListener, Cloneable, Serializable
/*      */ {
/*  248 */   private XYItemLabelGenerator itemLabelGenerator = null;
/*  249 */   private Map<Integer, XYItemLabelGenerator> itemLabelGeneratorMap = new HashMap<Integer, XYItemLabelGenerator>();
/*      */   
/*  251 */   private XYToolTipGenerator toolTipGenerator = null;
/*  252 */   private Map<Integer, XYToolTipGenerator> toolTipGeneratorMap = new HashMap<Integer, XYToolTipGenerator>();
/*  253 */   private XYURLGenerator urlGenerator = null;
/*  254 */   private List backgroundAnnotations = new ArrayList();
/*  255 */   private List foregroundAnnotations = new ArrayList();
/*  256 */   private XYSeriesLabelGenerator legendItemLabelGenerator = (XYSeriesLabelGenerator)new StandardXYSeriesLabelGenerator("{0}");
/*      */   
/*      */   private static final long serialVersionUID = 8019124836026607990L;
/*      */   
/*      */   private XYPlot plot;
/*      */   
/*      */   private XYItemLabelGenerator baseItemLabelGenerator;
/*      */   
/*      */   private XYToolTipGenerator baseToolTipGenerator;
/*      */   private XYSeriesLabelGenerator legendItemToolTipGenerator;
/*      */   private XYSeriesLabelGenerator legendItemURLGenerator;
/*      */   
/*      */   public int getPassCount() {
/*  269 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYPlot getPlot() {
/*  279 */     return this.plot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlot(XYPlot plot) {
/*  289 */     this.plot = plot;
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
/*      */   public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset data, PlotRenderingInfo info) {
/*  312 */     return new XYItemRendererState(info);
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
/*      */   public XYItemLabelGenerator getItemLabelGenerator(int series, int item) {
/*  331 */     if (this.itemLabelGenerator != null) {
/*  332 */       return this.itemLabelGenerator;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  337 */     XYItemLabelGenerator generator = this.itemLabelGeneratorMap.get(Integer.valueOf(series));
/*  338 */     if (generator == null) {
/*  339 */       generator = this.baseItemLabelGenerator;
/*      */     }
/*  341 */     return generator;
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
/*      */   public XYItemLabelGenerator getSeriesItemLabelGenerator(int series) {
/*  353 */     return this.itemLabelGeneratorMap.get(Integer.valueOf(series));
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
/*      */   public void setSeriesItemLabelGenerator(int series, XYItemLabelGenerator generator) {
/*  366 */     this.itemLabelGeneratorMap.put(Integer.valueOf(series), generator);
/*  367 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYItemLabelGenerator getBaseItemLabelGenerator() {
/*  377 */     return this.baseItemLabelGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseItemLabelGenerator(XYItemLabelGenerator generator) {
/*  388 */     this.baseItemLabelGenerator = generator;
/*  389 */     fireChangeEvent();
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
/*      */   public XYToolTipGenerator getToolTipGenerator(int series, int item) {
/*  407 */     if (this.toolTipGenerator != null) {
/*  408 */       return this.toolTipGenerator;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  413 */     XYToolTipGenerator generator = this.toolTipGeneratorMap.get(Integer.valueOf(series));
/*  414 */     if (generator == null) {
/*  415 */       generator = this.baseToolTipGenerator;
/*      */     }
/*  417 */     return generator;
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
/*  429 */     return this.toolTipGeneratorMap.get(Integer.valueOf(series));
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
/*      */   public void setSeriesToolTipGenerator(int series, XYToolTipGenerator generator) {
/*  442 */     this.toolTipGeneratorMap.put(Integer.valueOf(series), generator);
/*  443 */     fireChangeEvent();
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
/*  455 */     return this.baseToolTipGenerator;
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
/*  468 */     this.baseToolTipGenerator = generator;
/*  469 */     fireChangeEvent();
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
/*  481 */     return this.urlGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setURLGenerator(XYURLGenerator urlGenerator) {
/*  492 */     this.urlGenerator = urlGenerator;
/*  493 */     fireChangeEvent();
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
/*      */   public void addAnnotation(XYAnnotation annotation) {
/*  506 */     addAnnotation(annotation, Layer.FOREGROUND);
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
/*      */   public void addAnnotation(XYAnnotation annotation, Layer layer) {
/*  518 */     ParamChecks.nullNotPermitted(annotation, "annotation");
/*  519 */     if (layer.equals(Layer.FOREGROUND)) {
/*  520 */       this.foregroundAnnotations.add(annotation);
/*  521 */       annotation.addChangeListener(this);
/*  522 */       fireChangeEvent();
/*      */     }
/*  524 */     else if (layer.equals(Layer.BACKGROUND)) {
/*  525 */       this.backgroundAnnotations.add(annotation);
/*  526 */       annotation.addChangeListener(this);
/*  527 */       fireChangeEvent();
/*      */     }
/*      */     else {
/*      */       
/*  531 */       throw new RuntimeException("Unknown layer.");
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
/*      */   public boolean removeAnnotation(XYAnnotation annotation) {
/*  546 */     boolean removed = this.foregroundAnnotations.remove(annotation);
/*  547 */     removed &= this.backgroundAnnotations.remove(annotation);
/*  548 */     annotation.removeChangeListener(this);
/*  549 */     fireChangeEvent();
/*  550 */     return removed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAnnotations() {
/*      */     int i;
/*  559 */     for (i = 0; i < this.foregroundAnnotations.size(); i++) {
/*      */       
/*  561 */       XYAnnotation annotation = this.foregroundAnnotations.get(i);
/*  562 */       annotation.removeChangeListener(this);
/*      */     } 
/*  564 */     for (i = 0; i < this.backgroundAnnotations.size(); i++) {
/*      */       
/*  566 */       XYAnnotation annotation = this.backgroundAnnotations.get(i);
/*  567 */       annotation.removeChangeListener(this);
/*      */     } 
/*  569 */     this.foregroundAnnotations.clear();
/*  570 */     this.backgroundAnnotations.clear();
/*  571 */     fireChangeEvent();
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
/*      */   public void annotationChanged(AnnotationChangeEvent event) {
/*  585 */     fireChangeEvent();
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
/*      */   public Collection getAnnotations() {
/*  598 */     List result = new ArrayList(this.foregroundAnnotations);
/*  599 */     result.addAll(this.backgroundAnnotations);
/*  600 */     return result;
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
/*      */   public XYSeriesLabelGenerator getLegendItemLabelGenerator() {
/*  612 */     return this.legendItemLabelGenerator;
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
/*      */   public void setLegendItemLabelGenerator(XYSeriesLabelGenerator generator) {
/*  625 */     ParamChecks.nullNotPermitted(generator, "generator");
/*  626 */     this.legendItemLabelGenerator = generator;
/*  627 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYSeriesLabelGenerator getLegendItemToolTipGenerator() {
/*  638 */     return this.legendItemToolTipGenerator;
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
/*      */   public void setLegendItemToolTipGenerator(XYSeriesLabelGenerator generator) {
/*  651 */     this.legendItemToolTipGenerator = generator;
/*  652 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYSeriesLabelGenerator getLegendItemURLGenerator() {
/*  663 */     return this.legendItemURLGenerator;
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
/*      */   public void setLegendItemURLGenerator(XYSeriesLabelGenerator generator) {
/*  675 */     this.legendItemURLGenerator = generator;
/*  676 */     fireChangeEvent();
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
/*      */   public Range findDomainBounds(XYDataset dataset) {
/*  692 */     return findDomainBounds(dataset, false);
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
/*      */   protected Range findDomainBounds(XYDataset dataset, boolean includeInterval) {
/*  709 */     if (dataset == null) {
/*  710 */       return null;
/*      */     }
/*  712 */     if (getDataBoundsIncludesVisibleSeriesOnly()) {
/*  713 */       List<Comparable> visibleSeriesKeys = new ArrayList();
/*  714 */       int seriesCount = dataset.getSeriesCount();
/*  715 */       for (int s = 0; s < seriesCount; s++) {
/*  716 */         if (isSeriesVisible(s)) {
/*  717 */           visibleSeriesKeys.add(dataset.getSeriesKey(s));
/*      */         }
/*      */       } 
/*  720 */       return DatasetUtilities.findDomainBounds(dataset, visibleSeriesKeys, includeInterval);
/*      */     } 
/*      */     
/*  723 */     return DatasetUtilities.findDomainBounds(dataset, includeInterval);
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
/*      */   public Range findRangeBounds(XYDataset dataset) {
/*  739 */     return findRangeBounds(dataset, false);
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
/*      */   protected Range findRangeBounds(XYDataset dataset, boolean includeInterval) {
/*  756 */     if (dataset == null) {
/*  757 */       return null;
/*      */     }
/*  759 */     if (getDataBoundsIncludesVisibleSeriesOnly()) {
/*  760 */       List<Comparable> visibleSeriesKeys = new ArrayList();
/*  761 */       int seriesCount = dataset.getSeriesCount();
/*  762 */       for (int s = 0; s < seriesCount; s++) {
/*  763 */         if (isSeriesVisible(s)) {
/*  764 */           visibleSeriesKeys.add(dataset.getSeriesKey(s));
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  769 */       Range xRange = null;
/*  770 */       XYPlot p = getPlot();
/*  771 */       if (p != null) {
/*  772 */         ValueAxis xAxis = null;
/*  773 */         int index = p.getIndexOf(this);
/*  774 */         if (index >= 0) {
/*  775 */           xAxis = this.plot.getDomainAxisForDataset(index);
/*      */         }
/*  777 */         if (xAxis != null) {
/*  778 */           xRange = xAxis.getRange();
/*      */         }
/*      */       } 
/*  781 */       if (xRange == null) {
/*  782 */         xRange = new Range(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
/*      */       }
/*      */       
/*  785 */       return DatasetUtilities.findRangeBounds(dataset, visibleSeriesKeys, xRange, includeInterval);
/*      */     } 
/*      */     
/*  788 */     return DatasetUtilities.findRangeBounds(dataset, includeInterval);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LegendItemCollection getLegendItems() {
/*  799 */     if (this.plot == null) {
/*  800 */       return new LegendItemCollection();
/*      */     }
/*  802 */     LegendItemCollection result = new LegendItemCollection();
/*  803 */     int index = this.plot.getIndexOf(this);
/*  804 */     XYDataset dataset = this.plot.getDataset(index);
/*  805 */     if (dataset != null) {
/*  806 */       int seriesCount = dataset.getSeriesCount();
/*  807 */       for (int i = 0; i < seriesCount; i++) {
/*  808 */         if (isSeriesVisibleInLegend(i)) {
/*  809 */           LegendItem item = getLegendItem(index, i);
/*  810 */           if (item != null) {
/*  811 */             result.add(item);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  817 */     return result;
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
/*  831 */     XYPlot xyplot = getPlot();
/*  832 */     if (xyplot == null) {
/*  833 */       return null;
/*      */     }
/*  835 */     XYDataset dataset = xyplot.getDataset(datasetIndex);
/*  836 */     if (dataset == null) {
/*  837 */       return null;
/*      */     }
/*  839 */     String label = this.legendItemLabelGenerator.generateLabel(dataset, series);
/*      */     
/*  841 */     String description = label;
/*  842 */     String toolTipText = null;
/*  843 */     if (getLegendItemToolTipGenerator() != null) {
/*  844 */       toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*      */     }
/*      */     
/*  847 */     String urlText = null;
/*  848 */     if (getLegendItemURLGenerator() != null) {
/*  849 */       urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*      */     }
/*      */     
/*  852 */     Shape shape = lookupLegendShape(series);
/*  853 */     Paint paint = lookupSeriesPaint(series);
/*  854 */     LegendItem item = new LegendItem(label, paint);
/*  855 */     item.setToolTipText(toolTipText);
/*  856 */     item.setURLText(urlText);
/*  857 */     item.setLabelFont(lookupLegendTextFont(series));
/*  858 */     Paint labelPaint = lookupLegendTextPaint(series);
/*  859 */     if (labelPaint != null) {
/*  860 */       item.setLabelPaint(labelPaint);
/*      */     }
/*  862 */     item.setSeriesKey(dataset.getSeriesKey(series));
/*  863 */     item.setSeriesIndex(series);
/*  864 */     item.setDataset((Dataset)dataset);
/*  865 */     item.setDatasetIndex(datasetIndex);
/*      */     
/*  867 */     if (getTreatLegendShapeAsLine()) {
/*  868 */       item.setLineVisible(true);
/*  869 */       item.setLine(shape);
/*  870 */       item.setLinePaint(paint);
/*  871 */       item.setShapeVisible(false);
/*      */     } else {
/*      */       
/*  874 */       Paint outlinePaint = lookupSeriesOutlinePaint(series);
/*  875 */       Stroke outlineStroke = lookupSeriesOutlineStroke(series);
/*  876 */       item.setOutlinePaint(outlinePaint);
/*  877 */       item.setOutlineStroke(outlineStroke);
/*      */     } 
/*  879 */     return item;
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
/*      */   public void fillDomainGridBand(Graphics2D g2, XYPlot plot, ValueAxis axis, Rectangle2D dataArea, double start, double end) {
/*      */     Rectangle2D band;
/*  897 */     double x1 = axis.valueToJava2D(start, dataArea, plot
/*  898 */         .getDomainAxisEdge());
/*  899 */     double x2 = axis.valueToJava2D(end, dataArea, plot
/*  900 */         .getDomainAxisEdge());
/*      */     
/*  902 */     if (plot.getOrientation() == PlotOrientation.VERTICAL) {
/*      */       
/*  904 */       band = new Rectangle2D.Double(Math.min(x1, x2), dataArea.getMinY(), Math.abs(x2 - x1), dataArea.getHeight());
/*      */     }
/*      */     else {
/*      */       
/*  908 */       band = new Rectangle2D.Double(dataArea.getMinX(), Math.min(x1, x2), dataArea.getWidth(), Math.abs(x2 - x1));
/*      */     } 
/*  910 */     Paint paint = plot.getDomainTickBandPaint();
/*      */     
/*  912 */     if (paint != null) {
/*  913 */       g2.setPaint(paint);
/*  914 */       g2.fill(band);
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
/*      */   public void fillRangeGridBand(Graphics2D g2, XYPlot plot, ValueAxis axis, Rectangle2D dataArea, double start, double end) {
/*      */     Rectangle2D band;
/*  934 */     double y1 = axis.valueToJava2D(start, dataArea, plot
/*  935 */         .getRangeAxisEdge());
/*  936 */     double y2 = axis.valueToJava2D(end, dataArea, plot.getRangeAxisEdge());
/*      */     
/*  938 */     if (plot.getOrientation() == PlotOrientation.VERTICAL) {
/*      */       
/*  940 */       band = new Rectangle2D.Double(dataArea.getMinX(), Math.min(y1, y2), dataArea.getWidth(), Math.abs(y2 - y1));
/*      */     }
/*      */     else {
/*      */       
/*  944 */       band = new Rectangle2D.Double(Math.min(y1, y2), dataArea.getMinY(), Math.abs(y2 - y1), dataArea.getHeight());
/*      */     } 
/*  946 */     Paint paint = plot.getRangeTickBandPaint();
/*      */     
/*  948 */     if (paint != null) {
/*  949 */       g2.setPaint(paint);
/*  950 */       g2.fill(band);
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
/*      */   public void drawDomainGridLine(Graphics2D g2, XYPlot plot, ValueAxis axis, Rectangle2D dataArea, double value) {
/*  969 */     Range range = axis.getRange();
/*  970 */     if (!range.contains(value)) {
/*      */       return;
/*      */     }
/*      */     
/*  974 */     PlotOrientation orientation = plot.getOrientation();
/*  975 */     double v = axis.valueToJava2D(value, dataArea, plot
/*  976 */         .getDomainAxisEdge());
/*  977 */     Line2D line = null;
/*  978 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*      */       
/*  980 */       line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
/*      */     }
/*  982 */     else if (orientation == PlotOrientation.VERTICAL) {
/*      */       
/*  984 */       line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
/*      */     } 
/*      */     
/*  987 */     Paint paint = plot.getDomainGridlinePaint();
/*  988 */     Stroke stroke = plot.getDomainGridlineStroke();
/*  989 */     g2.setPaint((paint != null) ? paint : Plot.DEFAULT_OUTLINE_PAINT);
/*  990 */     g2.setStroke((stroke != null) ? stroke : Plot.DEFAULT_OUTLINE_STROKE);
/*  991 */     Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
/*  992 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/*      */     
/*  994 */     g2.draw(line);
/*  995 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
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
/*      */   public void drawDomainLine(Graphics2D g2, XYPlot plot, ValueAxis axis, Rectangle2D dataArea, double value, Paint paint, Stroke stroke) {
/* 1015 */     Range range = axis.getRange();
/* 1016 */     if (!range.contains(value)) {
/*      */       return;
/*      */     }
/*      */     
/* 1020 */     PlotOrientation orientation = plot.getOrientation();
/* 1021 */     Line2D line = null;
/* 1022 */     double v = axis.valueToJava2D(value, dataArea, plot
/* 1023 */         .getDomainAxisEdge());
/* 1024 */     if (orientation.isHorizontal()) {
/* 1025 */       line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
/*      */     }
/* 1027 */     else if (orientation.isVertical()) {
/*      */       
/* 1029 */       line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
/*      */     } 
/*      */     
/* 1032 */     g2.setPaint(paint);
/* 1033 */     g2.setStroke(stroke);
/* 1034 */     Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
/* 1035 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/*      */     
/* 1037 */     g2.draw(line);
/* 1038 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
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
/*      */   public void drawRangeLine(Graphics2D g2, XYPlot plot, ValueAxis axis, Rectangle2D dataArea, double value, Paint paint, Stroke stroke) {
/* 1057 */     Range range = axis.getRange();
/* 1058 */     if (!range.contains(value)) {
/*      */       return;
/*      */     }
/*      */     
/* 1062 */     PlotOrientation orientation = plot.getOrientation();
/* 1063 */     Line2D line = null;
/* 1064 */     double v = axis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());
/* 1065 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*      */       
/* 1067 */       line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
/* 1068 */     } else if (orientation == PlotOrientation.VERTICAL) {
/*      */       
/* 1070 */       line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
/*      */     } 
/*      */     
/* 1073 */     g2.setPaint(paint);
/* 1074 */     g2.setStroke(stroke);
/* 1075 */     Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
/* 1076 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/*      */     
/* 1078 */     g2.draw(line);
/* 1079 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
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
/*      */   public void drawDomainMarker(Graphics2D g2, XYPlot plot, ValueAxis domainAxis, Marker marker, Rectangle2D dataArea) {
/* 1095 */     if (marker instanceof ValueMarker) {
/* 1096 */       ValueMarker vm = (ValueMarker)marker;
/* 1097 */       double value = vm.getValue();
/* 1098 */       Range range = domainAxis.getRange();
/* 1099 */       if (!range.contains(value)) {
/*      */         return;
/*      */       }
/*      */       
/* 1103 */       double v = domainAxis.valueToJava2D(value, dataArea, plot
/* 1104 */           .getDomainAxisEdge());
/*      */       
/* 1106 */       PlotOrientation orientation = plot.getOrientation();
/* 1107 */       Line2D line = null;
/* 1108 */       if (orientation == PlotOrientation.HORIZONTAL) {
/*      */         
/* 1110 */         line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
/*      */       }
/* 1112 */       else if (orientation == PlotOrientation.VERTICAL) {
/*      */         
/* 1114 */         line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
/*      */       } else {
/* 1116 */         throw new IllegalStateException();
/*      */       } 
/*      */       
/* 1119 */       Composite originalComposite = g2.getComposite();
/* 1120 */       g2.setComposite(AlphaComposite.getInstance(3, marker
/* 1121 */             .getAlpha()));
/* 1122 */       g2.setPaint(marker.getPaint());
/* 1123 */       g2.setStroke(marker.getStroke());
/* 1124 */       g2.draw(line);
/*      */       
/* 1126 */       String label = marker.getLabel();
/* 1127 */       RectangleAnchor anchor = marker.getLabelAnchor();
/* 1128 */       if (label != null) {
/* 1129 */         Font labelFont = marker.getLabelFont();
/* 1130 */         g2.setFont(labelFont);
/* 1131 */         g2.setPaint(marker.getLabelPaint());
/* 1132 */         Point2D coordinates = calculateDomainMarkerTextAnchorPoint(g2, orientation, dataArea, line
/* 1133 */             .getBounds2D(), marker
/* 1134 */             .getLabelOffset(), LengthAdjustmentType.EXPAND, anchor);
/*      */         
/* 1136 */         TextUtilities.drawAlignedString(label, g2, 
/* 1137 */             (float)coordinates.getX(), (float)coordinates.getY(), marker
/* 1138 */             .getLabelTextAnchor());
/*      */       } 
/* 1140 */       g2.setComposite(originalComposite);
/*      */     }
/* 1142 */     else if (marker instanceof IntervalMarker) {
/* 1143 */       IntervalMarker im = (IntervalMarker)marker;
/* 1144 */       double start = im.getStartValue();
/* 1145 */       double end = im.getEndValue();
/* 1146 */       Range range = domainAxis.getRange();
/* 1147 */       if (!range.intersects(start, end)) {
/*      */         return;
/*      */       }
/*      */       
/* 1151 */       double start2d = domainAxis.valueToJava2D(start, dataArea, plot
/* 1152 */           .getDomainAxisEdge());
/* 1153 */       double end2d = domainAxis.valueToJava2D(end, dataArea, plot
/* 1154 */           .getDomainAxisEdge());
/* 1155 */       double low = Math.min(start2d, end2d);
/* 1156 */       double high = Math.max(start2d, end2d);
/*      */       
/* 1158 */       PlotOrientation orientation = plot.getOrientation();
/* 1159 */       Rectangle2D rect = null;
/* 1160 */       if (orientation == PlotOrientation.HORIZONTAL) {
/*      */         
/* 1162 */         low = Math.max(low, dataArea.getMinY());
/* 1163 */         high = Math.min(high, dataArea.getMaxY());
/*      */         
/* 1165 */         rect = new Rectangle2D.Double(dataArea.getMinX(), low, dataArea.getWidth(), high - low);
/*      */       
/*      */       }
/* 1168 */       else if (orientation == PlotOrientation.VERTICAL) {
/*      */         
/* 1170 */         low = Math.max(low, dataArea.getMinX());
/* 1171 */         high = Math.min(high, dataArea.getMaxX());
/*      */ 
/*      */         
/* 1174 */         rect = new Rectangle2D.Double(low, dataArea.getMinY(), high - low, dataArea.getHeight());
/*      */       } 
/*      */       
/* 1177 */       Composite originalComposite = g2.getComposite();
/* 1178 */       g2.setComposite(AlphaComposite.getInstance(3, marker
/* 1179 */             .getAlpha()));
/* 1180 */       Paint p = marker.getPaint();
/* 1181 */       if (p instanceof GradientPaint) {
/* 1182 */         GradientPaint gp = (GradientPaint)p;
/* 1183 */         GradientPaintTransformer t = im.getGradientPaintTransformer();
/* 1184 */         if (t != null) {
/* 1185 */           gp = t.transform(gp, rect);
/*      */         }
/* 1187 */         g2.setPaint(gp);
/*      */       } else {
/*      */         
/* 1190 */         g2.setPaint(p);
/*      */       } 
/* 1192 */       g2.fill(rect);
/*      */ 
/*      */       
/* 1195 */       if (im.getOutlinePaint() != null && im.getOutlineStroke() != null) {
/* 1196 */         if (orientation == PlotOrientation.VERTICAL) {
/* 1197 */           Line2D line = new Line2D.Double();
/* 1198 */           double y0 = dataArea.getMinY();
/* 1199 */           double y1 = dataArea.getMaxY();
/* 1200 */           g2.setPaint(im.getOutlinePaint());
/* 1201 */           g2.setStroke(im.getOutlineStroke());
/* 1202 */           if (range.contains(start)) {
/* 1203 */             line.setLine(start2d, y0, start2d, y1);
/* 1204 */             g2.draw(line);
/*      */           } 
/* 1206 */           if (range.contains(end)) {
/* 1207 */             line.setLine(end2d, y0, end2d, y1);
/* 1208 */             g2.draw(line);
/*      */           } 
/*      */         } else {
/*      */           
/* 1212 */           Line2D line = new Line2D.Double();
/* 1213 */           double x0 = dataArea.getMinX();
/* 1214 */           double x1 = dataArea.getMaxX();
/* 1215 */           g2.setPaint(im.getOutlinePaint());
/* 1216 */           g2.setStroke(im.getOutlineStroke());
/* 1217 */           if (range.contains(start)) {
/* 1218 */             line.setLine(x0, start2d, x1, start2d);
/* 1219 */             g2.draw(line);
/*      */           } 
/* 1221 */           if (range.contains(end)) {
/* 1222 */             line.setLine(x0, end2d, x1, end2d);
/* 1223 */             g2.draw(line);
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1228 */       String label = marker.getLabel();
/* 1229 */       RectangleAnchor anchor = marker.getLabelAnchor();
/* 1230 */       if (label != null) {
/* 1231 */         Font labelFont = marker.getLabelFont();
/* 1232 */         g2.setFont(labelFont);
/* 1233 */         g2.setPaint(marker.getLabelPaint());
/* 1234 */         Point2D coordinates = calculateDomainMarkerTextAnchorPoint(g2, orientation, dataArea, rect, marker
/*      */             
/* 1236 */             .getLabelOffset(), marker.getLabelOffsetType(), anchor);
/*      */         
/* 1238 */         TextUtilities.drawAlignedString(label, g2, 
/* 1239 */             (float)coordinates.getX(), (float)coordinates.getY(), marker
/* 1240 */             .getLabelTextAnchor());
/*      */       } 
/* 1242 */       g2.setComposite(originalComposite);
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
/*      */   protected Point2D calculateDomainMarkerTextAnchorPoint(Graphics2D g2, PlotOrientation orientation, Rectangle2D dataArea, Rectangle2D markerArea, RectangleInsets markerOffset, LengthAdjustmentType labelOffsetType, RectangleAnchor anchor) {
/* 1266 */     Rectangle2D anchorRect = null;
/* 1267 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 1268 */       anchorRect = markerOffset.createAdjustedRectangle(markerArea, LengthAdjustmentType.CONTRACT, labelOffsetType);
/*      */     
/*      */     }
/* 1271 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 1272 */       anchorRect = markerOffset.createAdjustedRectangle(markerArea, labelOffsetType, LengthAdjustmentType.CONTRACT);
/*      */     } 
/*      */     
/* 1275 */     return RectangleAnchor.coordinates(anchorRect, anchor);
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
/*      */   public void drawRangeMarker(Graphics2D g2, XYPlot plot, ValueAxis rangeAxis, Marker marker, Rectangle2D dataArea) {
/* 1292 */     if (marker instanceof ValueMarker) {
/* 1293 */       ValueMarker vm = (ValueMarker)marker;
/* 1294 */       double value = vm.getValue();
/* 1295 */       Range range = rangeAxis.getRange();
/* 1296 */       if (!range.contains(value)) {
/*      */         return;
/*      */       }
/*      */       
/* 1300 */       double v = rangeAxis.valueToJava2D(value, dataArea, plot
/* 1301 */           .getRangeAxisEdge());
/* 1302 */       PlotOrientation orientation = plot.getOrientation();
/* 1303 */       Line2D line = null;
/* 1304 */       if (orientation == PlotOrientation.HORIZONTAL) {
/*      */         
/* 1306 */         line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
/*      */       }
/* 1308 */       else if (orientation == PlotOrientation.VERTICAL) {
/*      */         
/* 1310 */         line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
/*      */       } else {
/*      */         
/* 1313 */         throw new IllegalStateException("Unknown orientation.");
/*      */       } 
/*      */       
/* 1316 */       Composite originalComposite = g2.getComposite();
/* 1317 */       g2.setComposite(AlphaComposite.getInstance(3, marker
/* 1318 */             .getAlpha()));
/* 1319 */       g2.setPaint(marker.getPaint());
/* 1320 */       g2.setStroke(marker.getStroke());
/* 1321 */       g2.draw(line);
/*      */       
/* 1323 */       String label = marker.getLabel();
/* 1324 */       RectangleAnchor anchor = marker.getLabelAnchor();
/* 1325 */       if (label != null) {
/* 1326 */         Font labelFont = marker.getLabelFont();
/* 1327 */         g2.setFont(labelFont);
/* 1328 */         g2.setPaint(marker.getLabelPaint());
/* 1329 */         Point2D coordinates = calculateRangeMarkerTextAnchorPoint(g2, orientation, dataArea, line
/* 1330 */             .getBounds2D(), marker
/* 1331 */             .getLabelOffset(), LengthAdjustmentType.EXPAND, anchor);
/*      */         
/* 1333 */         TextUtilities.drawAlignedString(label, g2, 
/* 1334 */             (float)coordinates.getX(), (float)coordinates.getY(), marker
/* 1335 */             .getLabelTextAnchor());
/*      */       } 
/* 1337 */       g2.setComposite(originalComposite);
/*      */     }
/* 1339 */     else if (marker instanceof IntervalMarker) {
/* 1340 */       IntervalMarker im = (IntervalMarker)marker;
/* 1341 */       double start = im.getStartValue();
/* 1342 */       double end = im.getEndValue();
/* 1343 */       Range range = rangeAxis.getRange();
/* 1344 */       if (!range.intersects(start, end)) {
/*      */         return;
/*      */       }
/*      */       
/* 1348 */       double start2d = rangeAxis.valueToJava2D(start, dataArea, plot
/* 1349 */           .getRangeAxisEdge());
/* 1350 */       double end2d = rangeAxis.valueToJava2D(end, dataArea, plot
/* 1351 */           .getRangeAxisEdge());
/* 1352 */       double low = Math.min(start2d, end2d);
/* 1353 */       double high = Math.max(start2d, end2d);
/*      */       
/* 1355 */       PlotOrientation orientation = plot.getOrientation();
/* 1356 */       Rectangle2D rect = null;
/* 1357 */       if (orientation == PlotOrientation.HORIZONTAL) {
/*      */         
/* 1359 */         low = Math.max(low, dataArea.getMinX());
/* 1360 */         high = Math.min(high, dataArea.getMaxX());
/*      */ 
/*      */         
/* 1363 */         rect = new Rectangle2D.Double(low, dataArea.getMinY(), high - low, dataArea.getHeight());
/*      */       }
/* 1365 */       else if (orientation == PlotOrientation.VERTICAL) {
/*      */         
/* 1367 */         low = Math.max(low, dataArea.getMinY());
/* 1368 */         high = Math.min(high, dataArea.getMaxY());
/*      */         
/* 1370 */         rect = new Rectangle2D.Double(dataArea.getMinX(), low, dataArea.getWidth(), high - low);
/*      */       } 
/*      */ 
/*      */       
/* 1374 */       Composite originalComposite = g2.getComposite();
/* 1375 */       g2.setComposite(AlphaComposite.getInstance(3, marker
/* 1376 */             .getAlpha()));
/* 1377 */       Paint p = marker.getPaint();
/* 1378 */       if (p instanceof GradientPaint) {
/* 1379 */         GradientPaint gp = (GradientPaint)p;
/* 1380 */         GradientPaintTransformer t = im.getGradientPaintTransformer();
/* 1381 */         if (t != null) {
/* 1382 */           gp = t.transform(gp, rect);
/*      */         }
/* 1384 */         g2.setPaint(gp);
/*      */       } else {
/*      */         
/* 1387 */         g2.setPaint(p);
/*      */       } 
/* 1389 */       g2.fill(rect);
/*      */ 
/*      */       
/* 1392 */       if (im.getOutlinePaint() != null && im.getOutlineStroke() != null) {
/* 1393 */         if (orientation == PlotOrientation.VERTICAL) {
/* 1394 */           Line2D line = new Line2D.Double();
/* 1395 */           double x0 = dataArea.getMinX();
/* 1396 */           double x1 = dataArea.getMaxX();
/* 1397 */           g2.setPaint(im.getOutlinePaint());
/* 1398 */           g2.setStroke(im.getOutlineStroke());
/* 1399 */           if (range.contains(start)) {
/* 1400 */             line.setLine(x0, start2d, x1, start2d);
/* 1401 */             g2.draw(line);
/*      */           } 
/* 1403 */           if (range.contains(end)) {
/* 1404 */             line.setLine(x0, end2d, x1, end2d);
/* 1405 */             g2.draw(line);
/*      */           } 
/*      */         } else {
/*      */           
/* 1409 */           Line2D line = new Line2D.Double();
/* 1410 */           double y0 = dataArea.getMinY();
/* 1411 */           double y1 = dataArea.getMaxY();
/* 1412 */           g2.setPaint(im.getOutlinePaint());
/* 1413 */           g2.setStroke(im.getOutlineStroke());
/* 1414 */           if (range.contains(start)) {
/* 1415 */             line.setLine(start2d, y0, start2d, y1);
/* 1416 */             g2.draw(line);
/*      */           } 
/* 1418 */           if (range.contains(end)) {
/* 1419 */             line.setLine(end2d, y0, end2d, y1);
/* 1420 */             g2.draw(line);
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1425 */       String label = marker.getLabel();
/* 1426 */       RectangleAnchor anchor = marker.getLabelAnchor();
/* 1427 */       if (label != null) {
/* 1428 */         Font labelFont = marker.getLabelFont();
/* 1429 */         g2.setFont(labelFont);
/* 1430 */         g2.setPaint(marker.getLabelPaint());
/* 1431 */         Point2D coordinates = calculateRangeMarkerTextAnchorPoint(g2, orientation, dataArea, rect, marker
/*      */             
/* 1433 */             .getLabelOffset(), marker.getLabelOffsetType(), anchor);
/*      */         
/* 1435 */         TextUtilities.drawAlignedString(label, g2, 
/* 1436 */             (float)coordinates.getX(), (float)coordinates.getY(), marker
/* 1437 */             .getLabelTextAnchor());
/*      */       } 
/* 1439 */       g2.setComposite(originalComposite);
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
/*      */   private Point2D calculateRangeMarkerTextAnchorPoint(Graphics2D g2, PlotOrientation orientation, Rectangle2D dataArea, Rectangle2D markerArea, RectangleInsets markerOffset, LengthAdjustmentType labelOffsetForRange, RectangleAnchor anchor) {
/* 1461 */     Rectangle2D anchorRect = null;
/* 1462 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 1463 */       anchorRect = markerOffset.createAdjustedRectangle(markerArea, labelOffsetForRange, LengthAdjustmentType.CONTRACT);
/*      */     
/*      */     }
/* 1466 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 1467 */       anchorRect = markerOffset.createAdjustedRectangle(markerArea, LengthAdjustmentType.CONTRACT, labelOffsetForRange);
/*      */     } 
/*      */     
/* 1470 */     return RectangleAnchor.coordinates(anchorRect, anchor);
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
/*      */   protected Object clone() throws CloneNotSupportedException {
/* 1484 */     AbstractXYItemRenderer clone = (AbstractXYItemRenderer)super.clone();
/*      */ 
/*      */     
/* 1487 */     if (this.itemLabelGenerator != null && this.itemLabelGenerator instanceof PublicCloneable) {
/*      */       
/* 1489 */       PublicCloneable pc = (PublicCloneable)this.itemLabelGenerator;
/* 1490 */       clone.itemLabelGenerator = (XYItemLabelGenerator)pc.clone();
/*      */     } 
/* 1492 */     clone.itemLabelGeneratorMap = CloneUtils.cloneMapValues(this.itemLabelGeneratorMap);
/*      */     
/* 1494 */     if (this.baseItemLabelGenerator != null && this.baseItemLabelGenerator instanceof PublicCloneable) {
/*      */       
/* 1496 */       PublicCloneable pc = (PublicCloneable)this.baseItemLabelGenerator;
/* 1497 */       clone.baseItemLabelGenerator = (XYItemLabelGenerator)pc.clone();
/*      */     } 
/*      */     
/* 1500 */     if (this.toolTipGenerator != null && this.toolTipGenerator instanceof PublicCloneable) {
/*      */       
/* 1502 */       PublicCloneable pc = (PublicCloneable)this.toolTipGenerator;
/* 1503 */       clone.toolTipGenerator = (XYToolTipGenerator)pc.clone();
/*      */     } 
/* 1505 */     clone.toolTipGeneratorMap = CloneUtils.cloneMapValues(this.toolTipGeneratorMap);
/*      */     
/* 1507 */     if (this.baseToolTipGenerator != null && this.baseToolTipGenerator instanceof PublicCloneable) {
/*      */       
/* 1509 */       PublicCloneable pc = (PublicCloneable)this.baseToolTipGenerator;
/* 1510 */       clone.baseToolTipGenerator = (XYToolTipGenerator)pc.clone();
/*      */     } 
/*      */     
/* 1513 */     if (this.legendItemLabelGenerator instanceof PublicCloneable) {
/* 1514 */       clone
/* 1515 */         .legendItemLabelGenerator = (XYSeriesLabelGenerator)ObjectUtilities.clone(this.legendItemLabelGenerator);
/*      */     }
/* 1517 */     if (this.legendItemToolTipGenerator instanceof PublicCloneable) {
/* 1518 */       clone
/* 1519 */         .legendItemToolTipGenerator = (XYSeriesLabelGenerator)ObjectUtilities.clone(this.legendItemToolTipGenerator);
/*      */     }
/* 1521 */     if (this.legendItemURLGenerator instanceof PublicCloneable) {
/* 1522 */       clone
/* 1523 */         .legendItemURLGenerator = (XYSeriesLabelGenerator)ObjectUtilities.clone(this.legendItemURLGenerator);
/*      */     }
/*      */     
/* 1526 */     clone.foregroundAnnotations = (List)ObjectUtilities.deepClone(this.foregroundAnnotations);
/*      */     
/* 1528 */     clone.backgroundAnnotations = (List)ObjectUtilities.deepClone(this.backgroundAnnotations);
/*      */ 
/*      */     
/* 1531 */     return clone;
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
/* 1543 */     if (obj == this) {
/* 1544 */       return true;
/*      */     }
/* 1546 */     if (!(obj instanceof AbstractXYItemRenderer)) {
/* 1547 */       return false;
/*      */     }
/* 1549 */     AbstractXYItemRenderer that = (AbstractXYItemRenderer)obj;
/* 1550 */     if (!ObjectUtilities.equal(this.itemLabelGenerator, that.itemLabelGenerator))
/*      */     {
/* 1552 */       return false;
/*      */     }
/* 1554 */     if (!this.itemLabelGeneratorMap.equals(that.itemLabelGeneratorMap)) {
/* 1555 */       return false;
/*      */     }
/* 1557 */     if (!ObjectUtilities.equal(this.baseItemLabelGenerator, that.baseItemLabelGenerator))
/*      */     {
/* 1559 */       return false;
/*      */     }
/* 1561 */     if (!ObjectUtilities.equal(this.toolTipGenerator, that.toolTipGenerator))
/*      */     {
/* 1563 */       return false;
/*      */     }
/* 1565 */     if (!this.toolTipGeneratorMap.equals(that.toolTipGeneratorMap)) {
/* 1566 */       return false;
/*      */     }
/* 1568 */     if (!ObjectUtilities.equal(this.baseToolTipGenerator, that.baseToolTipGenerator))
/*      */     {
/* 1570 */       return false;
/*      */     }
/* 1572 */     if (!ObjectUtilities.equal(this.urlGenerator, that.urlGenerator)) {
/* 1573 */       return false;
/*      */     }
/* 1575 */     if (!this.foregroundAnnotations.equals(that.foregroundAnnotations)) {
/* 1576 */       return false;
/*      */     }
/* 1578 */     if (!this.backgroundAnnotations.equals(that.backgroundAnnotations)) {
/* 1579 */       return false;
/*      */     }
/* 1581 */     if (!ObjectUtilities.equal(this.legendItemLabelGenerator, that.legendItemLabelGenerator))
/*      */     {
/* 1583 */       return false;
/*      */     }
/* 1585 */     if (!ObjectUtilities.equal(this.legendItemToolTipGenerator, that.legendItemToolTipGenerator))
/*      */     {
/* 1587 */       return false;
/*      */     }
/* 1589 */     if (!ObjectUtilities.equal(this.legendItemURLGenerator, that.legendItemURLGenerator))
/*      */     {
/* 1591 */       return false;
/*      */     }
/* 1593 */     return super.equals(obj);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DrawingSupplier getDrawingSupplier() {
/* 1603 */     DrawingSupplier result = null;
/* 1604 */     XYPlot p = getPlot();
/* 1605 */     if (p != null) {
/* 1606 */       result = p.getDrawingSupplier();
/*      */     }
/* 1608 */     return result;
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
/*      */   protected void updateCrosshairValues(CrosshairState crosshairState, double x, double y, int domainAxisIndex, int rangeAxisIndex, double transX, double transY, PlotOrientation orientation) {
/* 1633 */     ParamChecks.nullNotPermitted(orientation, "orientation");
/* 1634 */     if (crosshairState != null)
/*      */     {
/* 1636 */       if (this.plot.isDomainCrosshairLockedOnData()) {
/* 1637 */         if (this.plot.isRangeCrosshairLockedOnData())
/*      */         {
/* 1639 */           crosshairState.updateCrosshairPoint(x, y, domainAxisIndex, rangeAxisIndex, transX, transY, orientation);
/*      */         
/*      */         }
/*      */         else
/*      */         {
/* 1644 */           crosshairState.updateCrosshairX(x, domainAxisIndex);
/*      */         }
/*      */       
/*      */       }
/* 1648 */       else if (this.plot.isRangeCrosshairLockedOnData()) {
/*      */         
/* 1650 */         crosshairState.updateCrosshairY(y, rangeAxisIndex);
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
/*      */   protected void drawItemLabel(Graphics2D g2, PlotOrientation orientation, XYDataset dataset, int series, int item, double x, double y, boolean negative) {
/* 1674 */     XYItemLabelGenerator generator = getItemLabelGenerator(series, item);
/* 1675 */     if (generator != null) {
/* 1676 */       ItemLabelPosition position; Font labelFont = getItemLabelFont(series, item);
/* 1677 */       Paint paint = getItemLabelPaint(series, item);
/* 1678 */       g2.setFont(labelFont);
/* 1679 */       g2.setPaint(paint);
/* 1680 */       String label = generator.generateLabel(dataset, series, item);
/*      */ 
/*      */ 
/*      */       
/* 1684 */       if (!negative) {
/* 1685 */         position = getPositiveItemLabelPosition(series, item);
/*      */       } else {
/*      */         
/* 1688 */         position = getNegativeItemLabelPosition(series, item);
/*      */       } 
/*      */ 
/*      */       
/* 1692 */       Point2D anchorPoint = calculateLabelAnchorPoint(position
/* 1693 */           .getItemLabelAnchor(), x, y, orientation);
/* 1694 */       TextUtilities.drawRotatedString(label, g2, 
/* 1695 */           (float)anchorPoint.getX(), (float)anchorPoint.getY(), position
/* 1696 */           .getTextAnchor(), position.getAngle(), position
/* 1697 */           .getRotationAnchor());
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
/*      */   public void drawAnnotations(Graphics2D g2, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, Layer layer, PlotRenderingInfo info) {
/* 1717 */     Iterator<XYAnnotation> iterator = null;
/* 1718 */     if (layer.equals(Layer.FOREGROUND)) {
/* 1719 */       iterator = this.foregroundAnnotations.iterator();
/*      */     }
/* 1721 */     else if (layer.equals(Layer.BACKGROUND)) {
/* 1722 */       iterator = this.backgroundAnnotations.iterator();
/*      */     }
/*      */     else {
/*      */       
/* 1726 */       throw new RuntimeException("Unknown layer.");
/*      */     } 
/* 1728 */     while (iterator.hasNext()) {
/* 1729 */       XYAnnotation annotation = iterator.next();
/* 1730 */       int index = this.plot.getIndexOf(this);
/* 1731 */       annotation.draw(g2, this.plot, dataArea, domainAxis, rangeAxis, index, info);
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
/*      */   protected void addEntity(EntityCollection entities, Shape area, XYDataset dataset, int series, int item, double entityX, double entityY) {
/* 1754 */     if (!getItemCreateEntity(series, item)) {
/*      */       return;
/*      */     }
/* 1757 */     Shape hotspot = area;
/* 1758 */     if (hotspot == null) {
/* 1759 */       double r = getDefaultEntityRadius();
/* 1760 */       double w = r * 2.0D;
/* 1761 */       if (getPlot().getOrientation() == PlotOrientation.VERTICAL) {
/* 1762 */         hotspot = new Ellipse2D.Double(entityX - r, entityY - r, w, w);
/*      */       } else {
/*      */         
/* 1765 */         hotspot = new Ellipse2D.Double(entityY - r, entityX - r, w, w);
/*      */       } 
/*      */     } 
/* 1768 */     String tip = null;
/* 1769 */     XYToolTipGenerator generator = getToolTipGenerator(series, item);
/* 1770 */     if (generator != null) {
/* 1771 */       tip = generator.generateToolTip(dataset, series, item);
/*      */     }
/* 1773 */     String url = null;
/* 1774 */     if (getURLGenerator() != null) {
/* 1775 */       url = getURLGenerator().generateURL(dataset, series, item);
/*      */     }
/* 1777 */     XYItemEntity entity = new XYItemEntity(hotspot, dataset, series, item, tip, url);
/*      */     
/* 1779 */     entities.add((ChartEntity)entity);
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
/*      */   public static boolean isPointInRect(Rectangle2D rect, double x, double y) {
/* 1797 */     return (x >= rect.getMinX() && x <= rect.getMaxX() && y >= rect
/* 1798 */       .getMinY() && y <= rect.getMaxY());
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
/*      */   protected static void moveTo(GeneralPath hotspot, double x, double y) {
/* 1813 */     hotspot.moveTo((float)x, (float)y);
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
/*      */   protected static void lineTo(GeneralPath hotspot, double x, double y) {
/* 1828 */     hotspot.lineTo((float)x, (float)y);
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
/*      */ 
/*      */   
/*      */   public XYItemLabelGenerator getItemLabelGenerator() {
/* 1863 */     return this.itemLabelGenerator;
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
/*      */   public void setItemLabelGenerator(XYItemLabelGenerator generator) {
/* 1880 */     this.itemLabelGenerator = generator;
/* 1881 */     fireChangeEvent();
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
/*      */   public XYToolTipGenerator getToolTipGenerator() {
/* 1898 */     return this.toolTipGenerator;
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
/*      */   public void setToolTipGenerator(XYToolTipGenerator generator) {
/* 1915 */     this.toolTipGenerator = generator;
/* 1916 */     fireChangeEvent();
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
/*      */   protected void updateCrosshairValues(CrosshairState crosshairState, double x, double y, double transX, double transY, PlotOrientation orientation) {
/* 1940 */     updateCrosshairValues(crosshairState, x, y, 0, 0, transX, transY, orientation);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/AbstractXYItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */