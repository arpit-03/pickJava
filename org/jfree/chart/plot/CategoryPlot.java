/*      */ package org.jfree.chart.plot;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import org.jfree.chart.JFreeChart;
/*      */ import org.jfree.chart.LegendItemCollection;
/*      */ import org.jfree.chart.annotations.CategoryAnnotation;
/*      */ import org.jfree.chart.axis.Axis;
/*      */ import org.jfree.chart.axis.AxisCollection;
/*      */ import org.jfree.chart.axis.AxisLocation;
/*      */ import org.jfree.chart.axis.AxisSpace;
/*      */ import org.jfree.chart.axis.AxisState;
/*      */ import org.jfree.chart.axis.CategoryAnchor;
/*      */ import org.jfree.chart.axis.CategoryAxis;
/*      */ import org.jfree.chart.axis.TickType;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.axis.ValueTick;
/*      */ import org.jfree.chart.event.AnnotationChangeEvent;
/*      */ import org.jfree.chart.event.AnnotationChangeListener;
/*      */ import org.jfree.chart.event.ChartChangeEventType;
/*      */ import org.jfree.chart.event.PlotChangeEvent;
/*      */ import org.jfree.chart.event.RendererChangeEvent;
/*      */ import org.jfree.chart.event.RendererChangeListener;
/*      */ import org.jfree.chart.renderer.category.AbstractCategoryItemRenderer;
/*      */ import org.jfree.chart.renderer.category.CategoryItemRenderer;
/*      */ import org.jfree.chart.renderer.category.CategoryItemRendererState;
/*      */ import org.jfree.chart.util.CloneUtils;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.chart.util.ResourceBundleWrapper;
/*      */ import org.jfree.chart.util.ShadowGenerator;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.category.CategoryDataset;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.general.DatasetChangeEvent;
/*      */ import org.jfree.data.general.DatasetUtilities;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.ui.Layer;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ import org.jfree.util.ObjectUtilities;
/*      */ import org.jfree.util.PaintUtilities;
/*      */ import org.jfree.util.PublicCloneable;
/*      */ import org.jfree.util.ShapeUtilities;
/*      */ import org.jfree.util.SortOrder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CategoryPlot
/*      */   extends Plot
/*      */   implements ValueAxisPlot, Pannable, Zoomable, AnnotationChangeListener, RendererChangeListener, Cloneable, PublicCloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -3537691700434728188L;
/*      */   public static final boolean DEFAULT_DOMAIN_GRIDLINES_VISIBLE = false;
/*      */   public static final boolean DEFAULT_RANGE_GRIDLINES_VISIBLE = true;
/*  286 */   public static final Stroke DEFAULT_GRIDLINE_STROKE = new BasicStroke(0.5F, 0, 2, 0.0F, new float[] { 2.0F, 2.0F }, 0.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  291 */   public static final Paint DEFAULT_GRIDLINE_PAINT = Color.lightGray;
/*      */ 
/*      */   
/*  294 */   public static final Font DEFAULT_VALUE_LABEL_FONT = new Font("SansSerif", 0, 10);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean DEFAULT_CROSSHAIR_VISIBLE = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  309 */   public static final Stroke DEFAULT_CROSSHAIR_STROKE = DEFAULT_GRIDLINE_STROKE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  317 */   public static final Paint DEFAULT_CROSSHAIR_PAINT = Color.blue;
/*      */ 
/*      */ 
/*      */   
/*  321 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
/*      */ 
/*      */ 
/*      */   
/*      */   private PlotOrientation orientation;
/*      */ 
/*      */ 
/*      */   
/*      */   private RectangleInsets axisOffset;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Integer, CategoryAxis> domainAxes;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Integer, AxisLocation> domainAxisLocations;
/*      */ 
/*      */   
/*      */   private boolean drawSharedDomainAxis;
/*      */ 
/*      */   
/*      */   private Map<Integer, ValueAxis> rangeAxes;
/*      */ 
/*      */   
/*      */   private Map<Integer, AxisLocation> rangeAxisLocations;
/*      */ 
/*      */   
/*      */   private Map<Integer, CategoryDataset> datasets;
/*      */ 
/*      */   
/*      */   private TreeMap datasetToDomainAxesMap;
/*      */ 
/*      */   
/*      */   private TreeMap datasetToRangeAxesMap;
/*      */ 
/*      */   
/*      */   private Map<Integer, CategoryItemRenderer> renderers;
/*      */ 
/*      */   
/*  361 */   private DatasetRenderingOrder renderingOrder = DatasetRenderingOrder.REVERSE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  368 */   private SortOrder columnRenderingOrder = SortOrder.ASCENDING;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  374 */   private SortOrder rowRenderingOrder = SortOrder.ASCENDING;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean domainGridlinesVisible;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CategoryAnchor domainGridlinePosition;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke domainGridlineStroke;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint domainGridlinePaint;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean rangeZeroBaselineVisible;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke rangeZeroBaselineStroke;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint rangeZeroBaselinePaint;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean rangeGridlinesVisible;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke rangeGridlineStroke;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint rangeGridlinePaint;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean rangeMinorGridlinesVisible;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke rangeMinorGridlineStroke;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint rangeMinorGridlinePaint;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double anchorValue;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int crosshairDatasetIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean domainCrosshairVisible;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Comparable domainCrosshairRowKey;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Comparable domainCrosshairColumnKey;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke domainCrosshairStroke;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint domainCrosshairPaint;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean rangeCrosshairVisible;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double rangeCrosshairValue;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke rangeCrosshairStroke;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint rangeCrosshairPaint;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean rangeCrosshairLockedOnData = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map foregroundDomainMarkers;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map backgroundDomainMarkers;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map foregroundRangeMarkers;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map backgroundRangeMarkers;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List annotations;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int weight;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AxisSpace fixedDomainAxisSpace;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AxisSpace fixedRangeAxisSpace;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LegendItemCollection fixedLegendItems;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean rangePannable;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ShadowGenerator shadowGenerator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryPlot() {
/*  567 */     this((CategoryDataset)null, (CategoryAxis)null, (ValueAxis)null, (CategoryItemRenderer)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryPlot(CategoryDataset dataset, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryItemRenderer renderer) {
/*  584 */     this.orientation = PlotOrientation.VERTICAL;
/*      */ 
/*      */     
/*  587 */     this.domainAxes = new HashMap<Integer, CategoryAxis>();
/*  588 */     this.domainAxisLocations = new HashMap<Integer, AxisLocation>();
/*  589 */     this.rangeAxes = new HashMap<Integer, ValueAxis>();
/*  590 */     this.rangeAxisLocations = new HashMap<Integer, AxisLocation>();
/*      */     
/*  592 */     this.datasetToDomainAxesMap = new TreeMap<Object, Object>();
/*  593 */     this.datasetToRangeAxesMap = new TreeMap<Object, Object>();
/*      */     
/*  595 */     this.renderers = new HashMap<Integer, CategoryItemRenderer>();
/*      */     
/*  597 */     this.datasets = new HashMap<Integer, CategoryDataset>();
/*  598 */     this.datasets.put(Integer.valueOf(0), dataset);
/*  599 */     if (dataset != null) {
/*  600 */       dataset.addChangeListener(this);
/*      */     }
/*      */     
/*  603 */     this.axisOffset = RectangleInsets.ZERO_INSETS;
/*  604 */     this.domainAxisLocations.put(Integer.valueOf(0), AxisLocation.BOTTOM_OR_LEFT);
/*  605 */     this.rangeAxisLocations.put(Integer.valueOf(0), AxisLocation.TOP_OR_LEFT);
/*      */     
/*  607 */     this.renderers.put(Integer.valueOf(0), renderer);
/*  608 */     if (renderer != null) {
/*  609 */       renderer.setPlot(this);
/*  610 */       renderer.addChangeListener(this);
/*      */     } 
/*      */     
/*  613 */     this.domainAxes.put(Integer.valueOf(0), domainAxis);
/*  614 */     mapDatasetToDomainAxis(0, 0);
/*  615 */     if (domainAxis != null) {
/*  616 */       domainAxis.setPlot(this);
/*  617 */       domainAxis.addChangeListener(this);
/*      */     } 
/*  619 */     this.drawSharedDomainAxis = false;
/*      */     
/*  621 */     this.rangeAxes.put(Integer.valueOf(0), rangeAxis);
/*  622 */     mapDatasetToRangeAxis(0, 0);
/*  623 */     if (rangeAxis != null) {
/*  624 */       rangeAxis.setPlot(this);
/*  625 */       rangeAxis.addChangeListener(this);
/*      */     } 
/*      */     
/*  628 */     configureDomainAxes();
/*  629 */     configureRangeAxes();
/*      */     
/*  631 */     this.domainGridlinesVisible = false;
/*  632 */     this.domainGridlinePosition = CategoryAnchor.MIDDLE;
/*  633 */     this.domainGridlineStroke = DEFAULT_GRIDLINE_STROKE;
/*  634 */     this.domainGridlinePaint = DEFAULT_GRIDLINE_PAINT;
/*      */     
/*  636 */     this.rangeZeroBaselineVisible = false;
/*  637 */     this.rangeZeroBaselinePaint = Color.black;
/*  638 */     this.rangeZeroBaselineStroke = new BasicStroke(0.5F);
/*      */     
/*  640 */     this.rangeGridlinesVisible = true;
/*  641 */     this.rangeGridlineStroke = DEFAULT_GRIDLINE_STROKE;
/*  642 */     this.rangeGridlinePaint = DEFAULT_GRIDLINE_PAINT;
/*      */     
/*  644 */     this.rangeMinorGridlinesVisible = false;
/*  645 */     this.rangeMinorGridlineStroke = DEFAULT_GRIDLINE_STROKE;
/*  646 */     this.rangeMinorGridlinePaint = Color.white;
/*      */     
/*  648 */     this.foregroundDomainMarkers = new HashMap<Object, Object>();
/*  649 */     this.backgroundDomainMarkers = new HashMap<Object, Object>();
/*  650 */     this.foregroundRangeMarkers = new HashMap<Object, Object>();
/*  651 */     this.backgroundRangeMarkers = new HashMap<Object, Object>();
/*      */     
/*  653 */     this.anchorValue = 0.0D;
/*      */     
/*  655 */     this.domainCrosshairVisible = false;
/*  656 */     this.domainCrosshairStroke = DEFAULT_CROSSHAIR_STROKE;
/*  657 */     this.domainCrosshairPaint = DEFAULT_CROSSHAIR_PAINT;
/*      */     
/*  659 */     this.rangeCrosshairVisible = false;
/*  660 */     this.rangeCrosshairValue = 0.0D;
/*  661 */     this.rangeCrosshairStroke = DEFAULT_CROSSHAIR_STROKE;
/*  662 */     this.rangeCrosshairPaint = DEFAULT_CROSSHAIR_PAINT;
/*      */     
/*  664 */     this.annotations = new ArrayList();
/*      */     
/*  666 */     this.rangePannable = false;
/*  667 */     this.shadowGenerator = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlotType() {
/*  677 */     return localizationResources.getString("Category_Plot");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlotOrientation getOrientation() {
/*  689 */     return this.orientation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOrientation(PlotOrientation orientation) {
/*  701 */     ParamChecks.nullNotPermitted(orientation, "orientation");
/*  702 */     this.orientation = orientation;
/*  703 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleInsets getAxisOffset() {
/*  714 */     return this.axisOffset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxisOffset(RectangleInsets offset) {
/*  726 */     ParamChecks.nullNotPermitted(offset, "offset");
/*  727 */     this.axisOffset = offset;
/*  728 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryAxis getDomainAxis() {
/*  741 */     return getDomainAxis(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryAxis getDomainAxis(int index) {
/*  754 */     CategoryAxis result = this.domainAxes.get(Integer.valueOf(index));
/*  755 */     if (result == null) {
/*  756 */       Plot parent = getParent();
/*  757 */       if (parent instanceof CategoryPlot) {
/*  758 */         CategoryPlot cp = (CategoryPlot)parent;
/*  759 */         result = cp.getDomainAxis(index);
/*      */       } 
/*      */     } 
/*  762 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainAxis(CategoryAxis axis) {
/*  774 */     setDomainAxis(0, axis);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainAxis(int index, CategoryAxis axis) {
/*  787 */     setDomainAxis(index, axis, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainAxis(int index, CategoryAxis axis, boolean notify) {
/*  799 */     CategoryAxis existing = this.domainAxes.get(Integer.valueOf(index));
/*  800 */     if (existing != null) {
/*  801 */       existing.removeChangeListener(this);
/*      */     }
/*  803 */     if (axis != null) {
/*  804 */       axis.setPlot(this);
/*      */     }
/*  806 */     this.domainAxes.put(Integer.valueOf(index), axis);
/*  807 */     if (axis != null) {
/*  808 */       axis.configure();
/*  809 */       axis.addChangeListener(this);
/*      */     } 
/*  811 */     if (notify) {
/*  812 */       fireChangeEvent();
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
/*      */   public void setDomainAxes(CategoryAxis[] axes) {
/*  825 */     for (int i = 0; i < axes.length; i++) {
/*  826 */       setDomainAxis(i, axes[i], false);
/*      */     }
/*  828 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDomainAxisIndex(CategoryAxis axis) {
/*  845 */     ParamChecks.nullNotPermitted(axis, "axis");
/*  846 */     for (Map.Entry<Integer, CategoryAxis> entry : this.domainAxes.entrySet()) {
/*  847 */       if (entry.getValue() == axis) {
/*  848 */         return ((Integer)entry.getKey()).intValue();
/*      */       }
/*      */     } 
/*  851 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisLocation getDomainAxisLocation() {
/*  862 */     return getDomainAxisLocation(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisLocation getDomainAxisLocation(int index) {
/*  875 */     AxisLocation result = this.domainAxisLocations.get(Integer.valueOf(index));
/*  876 */     if (result == null) {
/*  877 */       result = AxisLocation.getOpposite(getDomainAxisLocation(0));
/*      */     }
/*  879 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainAxisLocation(AxisLocation location) {
/*  893 */     setDomainAxisLocation(0, location, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainAxisLocation(AxisLocation location, boolean notify) {
/*  905 */     setDomainAxisLocation(0, location, notify);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainAxisLocation(int index, AxisLocation location) {
/*  920 */     setDomainAxisLocation(index, location, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainAxisLocation(int index, AxisLocation location, boolean notify) {
/*  938 */     if (index == 0 && location == null) {
/*  939 */       throw new IllegalArgumentException("Null 'location' for index 0 not permitted.");
/*      */     }
/*      */     
/*  942 */     this.domainAxisLocations.put(Integer.valueOf(index), location);
/*  943 */     if (notify) {
/*  944 */       fireChangeEvent();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleEdge getDomainAxisEdge() {
/*  955 */     return getDomainAxisEdge(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleEdge getDomainAxisEdge(int index) {
/*      */     RectangleEdge result;
/*  967 */     AxisLocation location = getDomainAxisLocation(index);
/*  968 */     if (location != null) {
/*  969 */       result = Plot.resolveDomainAxisLocation(location, this.orientation);
/*      */     } else {
/*  971 */       result = RectangleEdge.opposite(getDomainAxisEdge(0));
/*      */     } 
/*  973 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDomainAxisCount() {
/*  982 */     return this.domainAxes.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearDomainAxes() {
/*  990 */     for (CategoryAxis xAxis : this.domainAxes.values()) {
/*  991 */       if (xAxis != null) {
/*  992 */         xAxis.removeChangeListener(this);
/*      */       }
/*      */     } 
/*  995 */     this.domainAxes.clear();
/*  996 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void configureDomainAxes() {
/* 1003 */     for (CategoryAxis xAxis : this.domainAxes.values()) {
/* 1004 */       if (xAxis != null) {
/* 1005 */         xAxis.configure();
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
/*      */   public ValueAxis getRangeAxis() {
/* 1018 */     return getRangeAxis(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueAxis getRangeAxis(int index) {
/* 1029 */     ValueAxis result = this.rangeAxes.get(Integer.valueOf(index));
/* 1030 */     if (result == null) {
/* 1031 */       Plot parent = getParent();
/* 1032 */       if (parent instanceof CategoryPlot) {
/* 1033 */         CategoryPlot cp = (CategoryPlot)parent;
/* 1034 */         result = cp.getRangeAxis(index);
/*      */       } 
/*      */     } 
/* 1037 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeAxis(ValueAxis axis) {
/* 1047 */     setRangeAxis(0, axis);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeAxis(int index, ValueAxis axis) {
/* 1058 */     setRangeAxis(index, axis, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeAxis(int index, ValueAxis axis, boolean notify) {
/* 1070 */     ValueAxis existing = this.rangeAxes.get(Integer.valueOf(index));
/* 1071 */     if (existing != null) {
/* 1072 */       existing.removeChangeListener(this);
/*      */     }
/* 1074 */     if (axis != null) {
/* 1075 */       axis.setPlot(this);
/*      */     }
/* 1077 */     this.rangeAxes.put(Integer.valueOf(index), axis);
/* 1078 */     if (axis != null) {
/* 1079 */       axis.configure();
/* 1080 */       axis.addChangeListener(this);
/*      */     } 
/* 1082 */     if (notify) {
/* 1083 */       fireChangeEvent();
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
/*      */   public void setRangeAxes(ValueAxis[] axes) {
/* 1096 */     for (int i = 0; i < axes.length; i++) {
/* 1097 */       setRangeAxis(i, axes[i], false);
/*      */     }
/* 1099 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRangeAxisIndex(ValueAxis axis) {
/* 1116 */     ParamChecks.nullNotPermitted(axis, "axis");
/* 1117 */     int result = findRangeAxisIndex(axis);
/* 1118 */     if (result < 0) {
/* 1119 */       Plot parent = getParent();
/* 1120 */       if (parent instanceof CategoryPlot) {
/* 1121 */         CategoryPlot p = (CategoryPlot)parent;
/* 1122 */         result = p.getRangeAxisIndex(axis);
/*      */       } 
/*      */     } 
/* 1125 */     return result;
/*      */   }
/*      */   
/*      */   private int findRangeAxisIndex(ValueAxis axis) {
/* 1129 */     for (Map.Entry<Integer, ValueAxis> entry : this.rangeAxes.entrySet()) {
/* 1130 */       if (entry.getValue() == axis) {
/* 1131 */         return ((Integer)entry.getKey()).intValue();
/*      */       }
/*      */     } 
/* 1134 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisLocation getRangeAxisLocation() {
/* 1143 */     return getRangeAxisLocation(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisLocation getRangeAxisLocation(int index) {
/* 1156 */     AxisLocation result = this.rangeAxisLocations.get(Integer.valueOf(index));
/* 1157 */     if (result == null) {
/* 1158 */       result = AxisLocation.getOpposite(getRangeAxisLocation(0));
/*      */     }
/* 1160 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeAxisLocation(AxisLocation location) {
/* 1174 */     setRangeAxisLocation(location, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeAxisLocation(AxisLocation location, boolean notify) {
/* 1187 */     setRangeAxisLocation(0, location, notify);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeAxisLocation(int index, AxisLocation location) {
/* 1201 */     setRangeAxisLocation(index, location, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeAxisLocation(int index, AxisLocation location, boolean notify) {
/* 1217 */     if (index == 0 && location == null) {
/* 1218 */       throw new IllegalArgumentException("Null 'location' for index 0 not permitted.");
/*      */     }
/*      */     
/* 1221 */     this.rangeAxisLocations.put(Integer.valueOf(index), location);
/* 1222 */     if (notify) {
/* 1223 */       fireChangeEvent();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleEdge getRangeAxisEdge() {
/* 1233 */     return getRangeAxisEdge(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleEdge getRangeAxisEdge(int index) {
/* 1244 */     AxisLocation location = getRangeAxisLocation(index);
/* 1245 */     return Plot.resolveRangeAxisLocation(location, this.orientation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRangeAxisCount() {
/* 1254 */     return this.rangeAxes.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearRangeAxes() {
/* 1262 */     for (ValueAxis yAxis : this.rangeAxes.values()) {
/* 1263 */       if (yAxis != null) {
/* 1264 */         yAxis.removeChangeListener(this);
/*      */       }
/*      */     } 
/* 1267 */     this.rangeAxes.clear();
/* 1268 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void configureRangeAxes() {
/* 1275 */     for (ValueAxis yAxis : this.rangeAxes.values()) {
/* 1276 */       if (yAxis != null) {
/* 1277 */         yAxis.configure();
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
/*      */   public CategoryDataset getDataset() {
/* 1290 */     return getDataset(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryDataset getDataset(int index) {
/* 1304 */     return this.datasets.get(Integer.valueOf(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataset(CategoryDataset dataset) {
/* 1319 */     setDataset(0, dataset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataset(int index, CategoryDataset dataset) {
/* 1332 */     CategoryDataset existing = this.datasets.get(Integer.valueOf(index));
/* 1333 */     if (existing != null) {
/* 1334 */       existing.removeChangeListener(this);
/*      */     }
/* 1336 */     this.datasets.put(Integer.valueOf(index), dataset);
/* 1337 */     if (dataset != null) {
/* 1338 */       dataset.addChangeListener(this);
/*      */     }
/*      */     
/* 1341 */     DatasetChangeEvent event = new DatasetChangeEvent(this, (Dataset)dataset);
/* 1342 */     datasetChanged(event);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDatasetCount() {
/* 1353 */     return this.datasets.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(CategoryDataset dataset) {
/* 1367 */     for (Map.Entry<Integer, CategoryDataset> entry : this.datasets.entrySet()) {
/* 1368 */       if (entry.getValue() == dataset) {
/* 1369 */         return ((Integer)entry.getKey()).intValue();
/*      */       }
/*      */     } 
/* 1372 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mapDatasetToDomainAxis(int index, int axisIndex) {
/* 1384 */     List<Integer> axisIndices = new ArrayList<Integer>(1);
/* 1385 */     axisIndices.add(Integer.valueOf(axisIndex));
/* 1386 */     mapDatasetToDomainAxes(index, axisIndices);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mapDatasetToDomainAxes(int index, List<?> axisIndices) {
/* 1400 */     ParamChecks.requireNonNegative(index, "index");
/* 1401 */     checkAxisIndices(axisIndices);
/* 1402 */     this.datasetToDomainAxesMap.put(Integer.valueOf(index), new ArrayList(axisIndices));
/*      */     
/* 1404 */     datasetChanged(new DatasetChangeEvent(this, (Dataset)getDataset(index)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkAxisIndices(List indices) {
/* 1418 */     if (indices == null) {
/*      */       return;
/*      */     }
/* 1421 */     int count = indices.size();
/* 1422 */     if (count == 0) {
/* 1423 */       throw new IllegalArgumentException("Empty list not permitted.");
/*      */     }
/* 1425 */     HashSet<Object> set = new HashSet();
/* 1426 */     for (int i = 0; i < count; i++) {
/* 1427 */       Object item = indices.get(i);
/* 1428 */       if (!(item instanceof Integer)) {
/* 1429 */         throw new IllegalArgumentException("Indices must be Integer instances.");
/*      */       }
/*      */       
/* 1432 */       if (set.contains(item)) {
/* 1433 */         throw new IllegalArgumentException("Indices must be unique.");
/*      */       }
/* 1435 */       set.add(item);
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
/*      */   public CategoryAxis getDomainAxisForDataset(int index) {
/*      */     CategoryAxis axis;
/* 1450 */     ParamChecks.requireNonNegative(index, "index");
/*      */     
/* 1452 */     List<Integer> axisIndices = (List)this.datasetToDomainAxesMap.get(new Integer(index));
/*      */     
/* 1454 */     if (axisIndices != null) {
/*      */       
/* 1456 */       Integer axisIndex = axisIndices.get(0);
/* 1457 */       axis = getDomainAxis(axisIndex.intValue());
/*      */     } else {
/* 1459 */       axis = getDomainAxis(0);
/*      */     } 
/* 1461 */     return axis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mapDatasetToRangeAxis(int index, int axisIndex) {
/* 1473 */     List<Integer> axisIndices = new ArrayList(1);
/* 1474 */     axisIndices.add(new Integer(axisIndex));
/* 1475 */     mapDatasetToRangeAxes(index, axisIndices);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mapDatasetToRangeAxes(int index, List<?> axisIndices) {
/* 1489 */     ParamChecks.requireNonNegative(index, "index");
/* 1490 */     checkAxisIndices(axisIndices);
/* 1491 */     this.datasetToRangeAxesMap.put(Integer.valueOf(index), new ArrayList(axisIndices));
/*      */     
/* 1493 */     datasetChanged(new DatasetChangeEvent(this, (Dataset)getDataset(index)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueAxis getRangeAxisForDataset(int index) {
/*      */     ValueAxis axis;
/* 1507 */     ParamChecks.requireNonNegative(index, "index");
/*      */     
/* 1509 */     List<Integer> axisIndices = (List)this.datasetToRangeAxesMap.get(new Integer(index));
/*      */     
/* 1511 */     if (axisIndices != null) {
/*      */       
/* 1513 */       Integer axisIndex = axisIndices.get(0);
/* 1514 */       axis = getRangeAxis(axisIndex.intValue());
/*      */     } else {
/* 1516 */       axis = getRangeAxis(0);
/*      */     } 
/* 1518 */     return axis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRendererCount() {
/* 1529 */     return this.renderers.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryItemRenderer getRenderer() {
/* 1540 */     return getRenderer(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryItemRenderer getRenderer(int index) {
/* 1553 */     CategoryItemRenderer renderer = this.renderers.get(Integer.valueOf(index));
/* 1554 */     if (renderer == null) {
/* 1555 */       return this.renderers.get(Integer.valueOf(0));
/*      */     }
/* 1557 */     return renderer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderer(CategoryItemRenderer renderer) {
/* 1569 */     setRenderer(0, renderer, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderer(CategoryItemRenderer renderer, boolean notify) {
/* 1590 */     setRenderer(0, renderer, notify);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderer(int index, CategoryItemRenderer renderer) {
/* 1606 */     setRenderer(index, renderer, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderer(int index, CategoryItemRenderer renderer, boolean notify) {
/* 1623 */     CategoryItemRenderer existing = this.renderers.get(Integer.valueOf(index));
/* 1624 */     if (existing != null) {
/* 1625 */       existing.removeChangeListener(this);
/*      */     }
/* 1627 */     this.renderers.put(Integer.valueOf(index), renderer);
/* 1628 */     if (renderer != null) {
/* 1629 */       renderer.setPlot(this);
/* 1630 */       renderer.addChangeListener(this);
/*      */     } 
/* 1632 */     configureDomainAxes();
/* 1633 */     configureRangeAxes();
/* 1634 */     if (notify) {
/* 1635 */       fireChangeEvent();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderers(CategoryItemRenderer[] renderers) {
/* 1646 */     for (int i = 0; i < renderers.length; i++) {
/* 1647 */       setRenderer(i, renderers[i], false);
/*      */     }
/* 1649 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryItemRenderer getRendererForDataset(CategoryDataset dataset) {
/* 1661 */     int datasetIndex = indexOf(dataset);
/* 1662 */     if (datasetIndex < 0) {
/* 1663 */       return null;
/*      */     }
/* 1665 */     CategoryItemRenderer renderer = this.renderers.get(Integer.valueOf(datasetIndex));
/* 1666 */     if (renderer == null) {
/* 1667 */       return getRenderer();
/*      */     }
/* 1669 */     return renderer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getIndexOf(CategoryItemRenderer renderer) {
/* 1682 */     for (Map.Entry<Integer, CategoryItemRenderer> entry : this.renderers.entrySet()) {
/* 1683 */       if (entry.getValue() == renderer) {
/* 1684 */         return ((Integer)entry.getKey()).intValue();
/*      */       }
/*      */     } 
/* 1687 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DatasetRenderingOrder getDatasetRenderingOrder() {
/* 1698 */     return this.renderingOrder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDatasetRenderingOrder(DatasetRenderingOrder order) {
/* 1712 */     ParamChecks.nullNotPermitted(order, "order");
/* 1713 */     this.renderingOrder = order;
/* 1714 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SortOrder getColumnRenderingOrder() {
/* 1726 */     return this.columnRenderingOrder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumnRenderingOrder(SortOrder order) {
/* 1741 */     ParamChecks.nullNotPermitted(order, "order");
/* 1742 */     this.columnRenderingOrder = order;
/* 1743 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SortOrder getRowRenderingOrder() {
/* 1755 */     return this.rowRenderingOrder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRowRenderingOrder(SortOrder order) {
/* 1770 */     ParamChecks.nullNotPermitted(order, "order");
/* 1771 */     this.rowRenderingOrder = order;
/* 1772 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDomainGridlinesVisible() {
/* 1783 */     return this.domainGridlinesVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 1798 */     if (this.domainGridlinesVisible != visible) {
/* 1799 */       this.domainGridlinesVisible = visible;
/* 1800 */       fireChangeEvent();
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
/*      */   public CategoryAnchor getDomainGridlinePosition() {
/* 1812 */     return this.domainGridlinePosition;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainGridlinePosition(CategoryAnchor position) {
/* 1824 */     ParamChecks.nullNotPermitted(position, "position");
/* 1825 */     this.domainGridlinePosition = position;
/* 1826 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getDomainGridlineStroke() {
/* 1837 */     return this.domainGridlineStroke;
/*      */   }
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
/* 1849 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 1850 */     this.domainGridlineStroke = stroke;
/* 1851 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getDomainGridlinePaint() {
/* 1862 */     return this.domainGridlinePaint;
/*      */   }
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
/* 1874 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 1875 */     this.domainGridlinePaint = paint;
/* 1876 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangeZeroBaselineVisible() {
/* 1890 */     return this.rangeZeroBaselineVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeZeroBaselineVisible(boolean visible) {
/* 1905 */     this.rangeZeroBaselineVisible = visible;
/* 1906 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getRangeZeroBaselineStroke() {
/* 1919 */     return this.rangeZeroBaselineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeZeroBaselineStroke(Stroke stroke) {
/* 1933 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 1934 */     this.rangeZeroBaselineStroke = stroke;
/* 1935 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getRangeZeroBaselinePaint() {
/* 1949 */     return this.rangeZeroBaselinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeZeroBaselinePaint(Paint paint) {
/* 1963 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 1964 */     this.rangeZeroBaselinePaint = paint;
/* 1965 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangeGridlinesVisible() {
/* 1976 */     return this.rangeGridlinesVisible;
/*      */   }
/*      */ 
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
/* 1989 */     if (this.rangeGridlinesVisible != visible) {
/* 1990 */       this.rangeGridlinesVisible = visible;
/* 1991 */       fireChangeEvent();
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
/*      */   public Stroke getRangeGridlineStroke() {
/* 2003 */     return this.rangeGridlineStroke;
/*      */   }
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
/* 2015 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 2016 */     this.rangeGridlineStroke = stroke;
/* 2017 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getRangeGridlinePaint() {
/* 2028 */     return this.rangeGridlinePaint;
/*      */   }
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
/* 2040 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 2041 */     this.rangeGridlinePaint = paint;
/* 2042 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangeMinorGridlinesVisible() {
/* 2056 */     return this.rangeMinorGridlinesVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeMinorGridlinesVisible(boolean visible) {
/* 2073 */     if (this.rangeMinorGridlinesVisible != visible) {
/* 2074 */       this.rangeMinorGridlinesVisible = visible;
/* 2075 */       fireChangeEvent();
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
/*      */   public Stroke getRangeMinorGridlineStroke() {
/* 2090 */     return this.rangeMinorGridlineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeMinorGridlineStroke(Stroke stroke) {
/* 2104 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 2105 */     this.rangeMinorGridlineStroke = stroke;
/* 2106 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getRangeMinorGridlinePaint() {
/* 2120 */     return this.rangeMinorGridlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeMinorGridlinePaint(Paint paint) {
/* 2134 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 2135 */     this.rangeMinorGridlinePaint = paint;
/* 2136 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LegendItemCollection getFixedLegendItems() {
/* 2147 */     return this.fixedLegendItems;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFixedLegendItems(LegendItemCollection items) {
/* 2160 */     this.fixedLegendItems = items;
/* 2161 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LegendItemCollection getLegendItems() {
/* 2173 */     if (this.fixedLegendItems != null) {
/* 2174 */       return this.fixedLegendItems;
/*      */     }
/* 2176 */     LegendItemCollection result = new LegendItemCollection();
/*      */     
/* 2178 */     for (CategoryDataset dataset : this.datasets.values()) {
/* 2179 */       if (dataset != null) {
/* 2180 */         int datasetIndex = indexOf(dataset);
/* 2181 */         CategoryItemRenderer renderer = getRenderer(datasetIndex);
/* 2182 */         if (renderer != null) {
/* 2183 */           result.addAll(renderer.getLegendItems());
/*      */         }
/*      */       } 
/*      */     } 
/* 2187 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleClick(int x, int y, PlotRenderingInfo info) {
/* 2201 */     Rectangle2D dataArea = info.getDataArea();
/* 2202 */     if (dataArea.contains(x, y)) {
/*      */       
/* 2204 */       double java2D = 0.0D;
/* 2205 */       if (this.orientation == PlotOrientation.HORIZONTAL) {
/* 2206 */         java2D = x;
/* 2207 */       } else if (this.orientation == PlotOrientation.VERTICAL) {
/* 2208 */         java2D = y;
/*      */       } 
/* 2210 */       RectangleEdge edge = Plot.resolveRangeAxisLocation(
/* 2211 */           getRangeAxisLocation(), this.orientation);
/* 2212 */       double value = getRangeAxis().java2DToValue(java2D, info
/* 2213 */           .getDataArea(), edge);
/* 2214 */       setAnchorValue(value);
/* 2215 */       setRangeCrosshairValue(value);
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
/*      */   public void zoom(double percent) {
/* 2231 */     if (percent > 0.0D) {
/* 2232 */       double range = getRangeAxis().getRange().getLength();
/* 2233 */       double scaledRange = range * percent;
/* 2234 */       getRangeAxis().setRange(this.anchorValue - scaledRange / 2.0D, this.anchorValue + scaledRange / 2.0D);
/*      */     }
/*      */     else {
/*      */       
/* 2238 */       getRangeAxis().setAutoRange(true);
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
/*      */   public void annotationChanged(AnnotationChangeEvent event) {
/* 2252 */     if (getParent() != null) {
/* 2253 */       getParent().annotationChanged(event);
/*      */     } else {
/* 2255 */       PlotChangeEvent e = new PlotChangeEvent(this);
/* 2256 */       notifyListeners(e);
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
/*      */   public void datasetChanged(DatasetChangeEvent event) {
/* 2269 */     for (ValueAxis yAxis : this.rangeAxes.values()) {
/* 2270 */       if (yAxis != null) {
/* 2271 */         yAxis.configure();
/*      */       }
/*      */     } 
/* 2274 */     if (getParent() != null) {
/* 2275 */       getParent().datasetChanged(event);
/*      */     } else {
/* 2277 */       PlotChangeEvent e = new PlotChangeEvent(this);
/* 2278 */       e.setType(ChartChangeEventType.DATASET_UPDATED);
/* 2279 */       notifyListeners(e);
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
/*      */   public void rendererChanged(RendererChangeEvent event) {
/* 2291 */     Plot parent = getParent();
/* 2292 */     if (parent != null) {
/* 2293 */       if (parent instanceof RendererChangeListener) {
/* 2294 */         RendererChangeListener rcl = (RendererChangeListener)parent;
/* 2295 */         rcl.rendererChanged(event);
/*      */       }
/*      */       else {
/*      */         
/* 2299 */         throw new RuntimeException("The renderer has changed and I don't know what to do!");
/*      */       } 
/*      */     } else {
/*      */       
/* 2303 */       configureRangeAxes();
/* 2304 */       PlotChangeEvent e = new PlotChangeEvent(this);
/* 2305 */       notifyListeners(e);
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
/*      */   public void addDomainMarker(CategoryMarker marker) {
/* 2320 */     addDomainMarker(marker, Layer.FOREGROUND);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDomainMarker(CategoryMarker marker, Layer layer) {
/* 2336 */     addDomainMarker(0, marker, layer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDomainMarker(int index, CategoryMarker marker, Layer layer) {
/* 2353 */     addDomainMarker(index, marker, layer, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDomainMarker(int index, CategoryMarker marker, Layer layer, boolean notify) {
/* 2374 */     ParamChecks.nullNotPermitted(marker, "marker");
/* 2375 */     ParamChecks.nullNotPermitted(layer, "layer");
/*      */     
/* 2377 */     if (layer == Layer.FOREGROUND) {
/* 2378 */       Collection<CategoryMarker> markers = (Collection)this.foregroundDomainMarkers.get(new Integer(index));
/*      */       
/* 2380 */       if (markers == null) {
/* 2381 */         markers = new ArrayList();
/* 2382 */         this.foregroundDomainMarkers.put(new Integer(index), markers);
/*      */       } 
/* 2384 */       markers.add(marker);
/* 2385 */     } else if (layer == Layer.BACKGROUND) {
/* 2386 */       Collection<CategoryMarker> markers = (Collection)this.backgroundDomainMarkers.get(new Integer(index));
/*      */       
/* 2388 */       if (markers == null) {
/* 2389 */         markers = new ArrayList();
/* 2390 */         this.backgroundDomainMarkers.put(new Integer(index), markers);
/*      */       } 
/* 2392 */       markers.add(marker);
/*      */     } 
/* 2394 */     marker.addChangeListener(this);
/* 2395 */     if (notify) {
/* 2396 */       fireChangeEvent();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearDomainMarkers() {
/* 2407 */     if (this.backgroundDomainMarkers != null) {
/* 2408 */       Set keys = this.backgroundDomainMarkers.keySet();
/* 2409 */       Iterator<Integer> iterator = keys.iterator();
/* 2410 */       while (iterator.hasNext()) {
/* 2411 */         Integer key = iterator.next();
/* 2412 */         clearDomainMarkers(key.intValue());
/*      */       } 
/* 2414 */       this.backgroundDomainMarkers.clear();
/*      */     } 
/* 2416 */     if (this.foregroundDomainMarkers != null) {
/* 2417 */       Set keys = this.foregroundDomainMarkers.keySet();
/* 2418 */       Iterator<Integer> iterator = keys.iterator();
/* 2419 */       while (iterator.hasNext()) {
/* 2420 */         Integer key = iterator.next();
/* 2421 */         clearDomainMarkers(key.intValue());
/*      */       } 
/* 2423 */       this.foregroundDomainMarkers.clear();
/*      */     } 
/* 2425 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection getDomainMarkers(Layer layer) {
/* 2436 */     return getDomainMarkers(0, layer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection getDomainMarkers(int index, Layer layer) {
/* 2449 */     Collection<?> result = null;
/* 2450 */     Integer key = new Integer(index);
/* 2451 */     if (layer == Layer.FOREGROUND) {
/* 2452 */       result = (Collection)this.foregroundDomainMarkers.get(key);
/*      */     }
/* 2454 */     else if (layer == Layer.BACKGROUND) {
/* 2455 */       result = (Collection)this.backgroundDomainMarkers.get(key);
/*      */     } 
/* 2457 */     if (result != null) {
/* 2458 */       result = Collections.unmodifiableCollection(result);
/*      */     }
/* 2460 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearDomainMarkers(int index) {
/* 2471 */     Integer key = new Integer(index);
/* 2472 */     if (this.backgroundDomainMarkers != null) {
/*      */       
/* 2474 */       Collection markers = (Collection)this.backgroundDomainMarkers.get(key);
/* 2475 */       if (markers != null) {
/* 2476 */         Iterator<Marker> iterator = markers.iterator();
/* 2477 */         while (iterator.hasNext()) {
/* 2478 */           Marker m = iterator.next();
/* 2479 */           m.removeChangeListener(this);
/*      */         } 
/* 2481 */         markers.clear();
/*      */       } 
/*      */     } 
/* 2484 */     if (this.foregroundDomainMarkers != null) {
/*      */       
/* 2486 */       Collection markers = (Collection)this.foregroundDomainMarkers.get(key);
/* 2487 */       if (markers != null) {
/* 2488 */         Iterator<Marker> iterator = markers.iterator();
/* 2489 */         while (iterator.hasNext()) {
/* 2490 */           Marker m = iterator.next();
/* 2491 */           m.removeChangeListener(this);
/*      */         } 
/* 2493 */         markers.clear();
/*      */       } 
/*      */     } 
/* 2496 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeDomainMarker(Marker marker) {
/* 2511 */     return removeDomainMarker(marker, Layer.FOREGROUND);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeDomainMarker(Marker marker, Layer layer) {
/* 2527 */     return removeDomainMarker(0, marker, layer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeDomainMarker(int index, Marker marker, Layer layer) {
/* 2544 */     return removeDomainMarker(index, marker, layer, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeDomainMarker(int index, Marker marker, Layer layer, boolean notify) {
/*      */     ArrayList markers;
/* 2564 */     if (layer == Layer.FOREGROUND) {
/* 2565 */       markers = (ArrayList)this.foregroundDomainMarkers.get(new Integer(index));
/*      */     } else {
/*      */       
/* 2568 */       markers = (ArrayList)this.backgroundDomainMarkers.get(new Integer(index));
/*      */     } 
/*      */     
/* 2571 */     if (markers == null) {
/* 2572 */       return false;
/*      */     }
/* 2574 */     boolean removed = markers.remove(marker);
/* 2575 */     if (removed && notify) {
/* 2576 */       fireChangeEvent();
/*      */     }
/* 2578 */     return removed;
/*      */   }
/*      */ 
/*      */ 
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
/* 2592 */     addRangeMarker(marker, Layer.FOREGROUND);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRangeMarker(Marker marker, Layer layer) {
/* 2608 */     addRangeMarker(0, marker, layer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRangeMarker(int index, Marker marker, Layer layer) {
/* 2625 */     addRangeMarker(index, marker, layer, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRangeMarker(int index, Marker marker, Layer layer, boolean notify) {
/* 2647 */     if (layer == Layer.FOREGROUND) {
/* 2648 */       Collection<Marker> markers = (Collection)this.foregroundRangeMarkers.get(new Integer(index));
/*      */       
/* 2650 */       if (markers == null) {
/* 2651 */         markers = new ArrayList();
/* 2652 */         this.foregroundRangeMarkers.put(new Integer(index), markers);
/*      */       } 
/* 2654 */       markers.add(marker);
/* 2655 */     } else if (layer == Layer.BACKGROUND) {
/* 2656 */       Collection<Marker> markers = (Collection)this.backgroundRangeMarkers.get(new Integer(index));
/*      */       
/* 2658 */       if (markers == null) {
/* 2659 */         markers = new ArrayList();
/* 2660 */         this.backgroundRangeMarkers.put(new Integer(index), markers);
/*      */       } 
/* 2662 */       markers.add(marker);
/*      */     } 
/* 2664 */     marker.addChangeListener(this);
/* 2665 */     if (notify) {
/* 2666 */       fireChangeEvent();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearRangeMarkers() {
/* 2677 */     if (this.backgroundRangeMarkers != null) {
/* 2678 */       Set keys = this.backgroundRangeMarkers.keySet();
/* 2679 */       Iterator<Integer> iterator = keys.iterator();
/* 2680 */       while (iterator.hasNext()) {
/* 2681 */         Integer key = iterator.next();
/* 2682 */         clearRangeMarkers(key.intValue());
/*      */       } 
/* 2684 */       this.backgroundRangeMarkers.clear();
/*      */     } 
/* 2686 */     if (this.foregroundRangeMarkers != null) {
/* 2687 */       Set keys = this.foregroundRangeMarkers.keySet();
/* 2688 */       Iterator<Integer> iterator = keys.iterator();
/* 2689 */       while (iterator.hasNext()) {
/* 2690 */         Integer key = iterator.next();
/* 2691 */         clearRangeMarkers(key.intValue());
/*      */       } 
/* 2693 */       this.foregroundRangeMarkers.clear();
/*      */     } 
/* 2695 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection getRangeMarkers(Layer layer) {
/* 2708 */     return getRangeMarkers(0, layer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection getRangeMarkers(int index, Layer layer) {
/* 2721 */     Collection<?> result = null;
/* 2722 */     Integer key = new Integer(index);
/* 2723 */     if (layer == Layer.FOREGROUND) {
/* 2724 */       result = (Collection)this.foregroundRangeMarkers.get(key);
/*      */     }
/* 2726 */     else if (layer == Layer.BACKGROUND) {
/* 2727 */       result = (Collection)this.backgroundRangeMarkers.get(key);
/*      */     } 
/* 2729 */     if (result != null) {
/* 2730 */       result = Collections.unmodifiableCollection(result);
/*      */     }
/* 2732 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearRangeMarkers(int index) {
/* 2743 */     Integer key = new Integer(index);
/* 2744 */     if (this.backgroundRangeMarkers != null) {
/*      */       
/* 2746 */       Collection markers = (Collection)this.backgroundRangeMarkers.get(key);
/* 2747 */       if (markers != null) {
/* 2748 */         Iterator<Marker> iterator = markers.iterator();
/* 2749 */         while (iterator.hasNext()) {
/* 2750 */           Marker m = iterator.next();
/* 2751 */           m.removeChangeListener(this);
/*      */         } 
/* 2753 */         markers.clear();
/*      */       } 
/*      */     } 
/* 2756 */     if (this.foregroundRangeMarkers != null) {
/*      */       
/* 2758 */       Collection markers = (Collection)this.foregroundRangeMarkers.get(key);
/* 2759 */       if (markers != null) {
/* 2760 */         Iterator<Marker> iterator = markers.iterator();
/* 2761 */         while (iterator.hasNext()) {
/* 2762 */           Marker m = iterator.next();
/* 2763 */           m.removeChangeListener(this);
/*      */         } 
/* 2765 */         markers.clear();
/*      */       } 
/*      */     } 
/* 2768 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeRangeMarker(Marker marker) {
/* 2785 */     return removeRangeMarker(marker, Layer.FOREGROUND);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeRangeMarker(Marker marker, Layer layer) {
/* 2803 */     return removeRangeMarker(0, marker, layer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeRangeMarker(int index, Marker marker, Layer layer) {
/* 2822 */     return removeRangeMarker(index, marker, layer, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeRangeMarker(int index, Marker marker, Layer layer, boolean notify) {
/*      */     ArrayList markers;
/* 2843 */     ParamChecks.nullNotPermitted(marker, "marker");
/*      */     
/* 2845 */     if (layer == Layer.FOREGROUND) {
/* 2846 */       markers = (ArrayList)this.foregroundRangeMarkers.get(new Integer(index));
/*      */     } else {
/*      */       
/* 2849 */       markers = (ArrayList)this.backgroundRangeMarkers.get(new Integer(index));
/*      */     } 
/*      */     
/* 2852 */     if (markers == null) {
/* 2853 */       return false;
/*      */     }
/* 2855 */     boolean removed = markers.remove(marker);
/* 2856 */     if (removed && notify) {
/* 2857 */       fireChangeEvent();
/*      */     }
/* 2859 */     return removed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDomainCrosshairVisible() {
/* 2873 */     return this.domainCrosshairVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainCrosshairVisible(boolean flag) {
/* 2889 */     if (this.domainCrosshairVisible != flag) {
/* 2890 */       this.domainCrosshairVisible = flag;
/* 2891 */       fireChangeEvent();
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
/*      */   public Comparable getDomainCrosshairRowKey() {
/* 2903 */     return this.domainCrosshairRowKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainCrosshairRowKey(Comparable key) {
/* 2915 */     setDomainCrosshairRowKey(key, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainCrosshairRowKey(Comparable key, boolean notify) {
/* 2928 */     this.domainCrosshairRowKey = key;
/* 2929 */     if (notify) {
/* 2930 */       fireChangeEvent();
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
/*      */   public Comparable getDomainCrosshairColumnKey() {
/* 2942 */     return this.domainCrosshairColumnKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainCrosshairColumnKey(Comparable key) {
/* 2954 */     setDomainCrosshairColumnKey(key, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainCrosshairColumnKey(Comparable key, boolean notify) {
/* 2967 */     this.domainCrosshairColumnKey = key;
/* 2968 */     if (notify) {
/* 2969 */       fireChangeEvent();
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
/*      */   public int getCrosshairDatasetIndex() {
/* 2981 */     return this.crosshairDatasetIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCrosshairDatasetIndex(int index) {
/* 2993 */     setCrosshairDatasetIndex(index, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCrosshairDatasetIndex(int index, boolean notify) {
/* 3006 */     this.crosshairDatasetIndex = index;
/* 3007 */     if (notify) {
/* 3008 */       fireChangeEvent();
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
/*      */   public Paint getDomainCrosshairPaint() {
/* 3023 */     return this.domainCrosshairPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainCrosshairPaint(Paint paint) {
/* 3036 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 3037 */     this.domainCrosshairPaint = paint;
/* 3038 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getDomainCrosshairStroke() {
/* 3052 */     return this.domainCrosshairStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainCrosshairStroke(Stroke stroke) {
/* 3066 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 3067 */     this.domainCrosshairStroke = stroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangeCrosshairVisible() {
/* 3078 */     return this.rangeCrosshairVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeCrosshairVisible(boolean flag) {
/* 3089 */     if (this.rangeCrosshairVisible != flag) {
/* 3090 */       this.rangeCrosshairVisible = flag;
/* 3091 */       fireChangeEvent();
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
/*      */   public boolean isRangeCrosshairLockedOnData() {
/* 3104 */     return this.rangeCrosshairLockedOnData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeCrosshairLockedOnData(boolean flag) {
/* 3117 */     if (this.rangeCrosshairLockedOnData != flag) {
/* 3118 */       this.rangeCrosshairLockedOnData = flag;
/* 3119 */       fireChangeEvent();
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
/*      */   public double getRangeCrosshairValue() {
/* 3131 */     return this.rangeCrosshairValue;
/*      */   }
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
/* 3143 */     setRangeCrosshairValue(value, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 3158 */     this.rangeCrosshairValue = value;
/* 3159 */     if (isRangeCrosshairVisible() && notify) {
/* 3160 */       fireChangeEvent();
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
/*      */   public Stroke getRangeCrosshairStroke() {
/* 3175 */     return this.rangeCrosshairStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeCrosshairStroke(Stroke stroke) {
/* 3189 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 3190 */     this.rangeCrosshairStroke = stroke;
/* 3191 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getRangeCrosshairPaint() {
/* 3204 */     return this.rangeCrosshairPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeCrosshairPaint(Paint paint) {
/* 3216 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 3217 */     this.rangeCrosshairPaint = paint;
/* 3218 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List getAnnotations() {
/* 3230 */     return this.annotations;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAnnotation(CategoryAnnotation annotation) {
/* 3242 */     addAnnotation(annotation, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAnnotation(CategoryAnnotation annotation, boolean notify) {
/* 3255 */     ParamChecks.nullNotPermitted(annotation, "annotation");
/* 3256 */     this.annotations.add(annotation);
/* 3257 */     annotation.addChangeListener(this);
/* 3258 */     if (notify) {
/* 3259 */       fireChangeEvent();
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
/*      */   public boolean removeAnnotation(CategoryAnnotation annotation) {
/* 3274 */     return removeAnnotation(annotation, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeAnnotation(CategoryAnnotation annotation, boolean notify) {
/* 3290 */     ParamChecks.nullNotPermitted(annotation, "annotation");
/* 3291 */     boolean removed = this.annotations.remove(annotation);
/* 3292 */     annotation.removeChangeListener(this);
/* 3293 */     if (removed && notify) {
/* 3294 */       fireChangeEvent();
/*      */     }
/* 3296 */     return removed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearAnnotations() {
/* 3304 */     for (int i = 0; i < this.annotations.size(); i++) {
/*      */       
/* 3306 */       CategoryAnnotation annotation = this.annotations.get(i);
/* 3307 */       annotation.removeChangeListener(this);
/*      */     } 
/* 3309 */     this.annotations.clear();
/* 3310 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShadowGenerator getShadowGenerator() {
/* 3321 */     return this.shadowGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShadowGenerator(ShadowGenerator generator) {
/* 3333 */     this.shadowGenerator = generator;
/* 3334 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AxisSpace calculateDomainAxisSpace(Graphics2D g2, Rectangle2D plotArea, AxisSpace space) {
/* 3349 */     if (space == null) {
/* 3350 */       space = new AxisSpace();
/*      */     }
/*      */ 
/*      */     
/* 3354 */     if (this.fixedDomainAxisSpace != null) {
/* 3355 */       if (this.orientation.isHorizontal()) {
/* 3356 */         space.ensureAtLeast(this.fixedDomainAxisSpace
/* 3357 */             .getLeft(), RectangleEdge.LEFT);
/* 3358 */         space.ensureAtLeast(this.fixedDomainAxisSpace.getRight(), RectangleEdge.RIGHT);
/*      */       }
/* 3360 */       else if (this.orientation.isVertical()) {
/* 3361 */         space.ensureAtLeast(this.fixedDomainAxisSpace.getTop(), RectangleEdge.TOP);
/*      */         
/* 3363 */         space.ensureAtLeast(this.fixedDomainAxisSpace.getBottom(), RectangleEdge.BOTTOM);
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 3369 */       RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(
/* 3370 */           getDomainAxisLocation(), this.orientation);
/* 3371 */       if (this.drawSharedDomainAxis) {
/* 3372 */         space = getDomainAxis().reserveSpace(g2, this, plotArea, domainEdge, space);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3377 */       for (CategoryAxis xAxis : this.domainAxes.values()) {
/* 3378 */         if (xAxis != null) {
/* 3379 */           int i = getDomainAxisIndex(xAxis);
/* 3380 */           RectangleEdge edge = getDomainAxisEdge(i);
/* 3381 */           space = xAxis.reserveSpace(g2, this, plotArea, edge, space);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3386 */     return space;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AxisSpace calculateRangeAxisSpace(Graphics2D g2, Rectangle2D plotArea, AxisSpace space) {
/* 3402 */     if (space == null) {
/* 3403 */       space = new AxisSpace();
/*      */     }
/*      */ 
/*      */     
/* 3407 */     if (this.fixedRangeAxisSpace != null) {
/* 3408 */       if (this.orientation.isHorizontal()) {
/* 3409 */         space.ensureAtLeast(this.fixedRangeAxisSpace.getTop(), RectangleEdge.TOP);
/*      */         
/* 3411 */         space.ensureAtLeast(this.fixedRangeAxisSpace.getBottom(), RectangleEdge.BOTTOM);
/*      */       }
/* 3413 */       else if (this.orientation == PlotOrientation.VERTICAL) {
/* 3414 */         space.ensureAtLeast(this.fixedRangeAxisSpace.getLeft(), RectangleEdge.LEFT);
/*      */         
/* 3416 */         space.ensureAtLeast(this.fixedRangeAxisSpace.getRight(), RectangleEdge.RIGHT);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 3421 */       for (ValueAxis yAxis : this.rangeAxes.values()) {
/* 3422 */         if (yAxis != null) {
/* 3423 */           int i = findRangeAxisIndex(yAxis);
/* 3424 */           RectangleEdge edge = getRangeAxisEdge(i);
/* 3425 */           space = yAxis.reserveSpace(g2, this, plotArea, edge, space);
/*      */         } 
/*      */       } 
/*      */     } 
/* 3429 */     return space;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Rectangle integerise(Rectangle2D rect) {
/* 3441 */     int x0 = (int)Math.ceil(rect.getMinX());
/* 3442 */     int y0 = (int)Math.ceil(rect.getMinY());
/* 3443 */     int x1 = (int)Math.floor(rect.getMaxX());
/* 3444 */     int y1 = (int)Math.floor(rect.getMaxY());
/* 3445 */     return new Rectangle(x0, y0, x1 - x0, y1 - y0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AxisSpace calculateAxisSpace(Graphics2D g2, Rectangle2D plotArea) {
/* 3458 */     AxisSpace space = new AxisSpace();
/* 3459 */     space = calculateRangeAxisSpace(g2, plotArea, space);
/* 3460 */     space = calculateDomainAxisSpace(g2, plotArea, space);
/* 3461 */     return space;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo state) {
/* 3485 */     boolean b1 = (area.getWidth() <= 10.0D);
/* 3486 */     boolean b2 = (area.getHeight() <= 10.0D);
/* 3487 */     if (b1 || b2) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3492 */     if (state == null)
/*      */     {
/*      */ 
/*      */       
/* 3496 */       state = new PlotRenderingInfo(null);
/*      */     }
/* 3498 */     state.setPlotArea(area);
/*      */ 
/*      */     
/* 3501 */     RectangleInsets insets = getInsets();
/* 3502 */     insets.trim(area);
/*      */ 
/*      */     
/* 3505 */     AxisSpace space = calculateAxisSpace(g2, area);
/* 3506 */     Rectangle2D dataArea = space.shrink(area, null);
/* 3507 */     this.axisOffset.trim(dataArea);
/* 3508 */     dataArea = integerise(dataArea);
/* 3509 */     if (dataArea.isEmpty()) {
/*      */       return;
/*      */     }
/* 3512 */     state.setDataArea(dataArea);
/* 3513 */     createAndAddEntity((Rectangle2D)dataArea.clone(), state, null, null);
/*      */ 
/*      */ 
/*      */     
/* 3517 */     if (getRenderer() != null) {
/* 3518 */       getRenderer().drawBackground(g2, this, dataArea);
/*      */     } else {
/* 3520 */       drawBackground(g2, dataArea);
/*      */     } 
/*      */     
/* 3523 */     Map axisStateMap = drawAxes(g2, area, dataArea, state);
/*      */ 
/*      */ 
/*      */     
/* 3527 */     if (anchor != null && !dataArea.contains(anchor)) {
/* 3528 */       anchor = ShapeUtilities.getPointInRectangle(anchor.getX(), anchor
/* 3529 */           .getY(), dataArea);
/*      */     }
/* 3531 */     CategoryCrosshairState crosshairState = new CategoryCrosshairState();
/* 3532 */     crosshairState.setCrosshairDistance(Double.POSITIVE_INFINITY);
/* 3533 */     crosshairState.setAnchor(anchor);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3538 */     crosshairState.setAnchorX(Double.NaN);
/* 3539 */     crosshairState.setAnchorY(Double.NaN);
/* 3540 */     if (anchor != null) {
/* 3541 */       ValueAxis rangeAxis = getRangeAxis();
/* 3542 */       if (rangeAxis != null) {
/*      */         double y;
/* 3544 */         if (getOrientation() == PlotOrientation.VERTICAL) {
/* 3545 */           y = rangeAxis.java2DToValue(anchor.getY(), dataArea, 
/* 3546 */               getRangeAxisEdge());
/*      */         } else {
/*      */           
/* 3549 */           y = rangeAxis.java2DToValue(anchor.getX(), dataArea, 
/* 3550 */               getRangeAxisEdge());
/*      */         } 
/* 3552 */         crosshairState.setAnchorY(y);
/*      */       } 
/*      */     } 
/* 3555 */     crosshairState.setRowKey(getDomainCrosshairRowKey());
/* 3556 */     crosshairState.setColumnKey(getDomainCrosshairColumnKey());
/* 3557 */     crosshairState.setCrosshairY(getRangeCrosshairValue());
/*      */ 
/*      */     
/* 3560 */     Shape savedClip = g2.getClip();
/* 3561 */     g2.clip(dataArea);
/*      */     
/* 3563 */     drawDomainGridlines(g2, dataArea);
/*      */     
/* 3565 */     AxisState rangeAxisState = (AxisState)axisStateMap.get(getRangeAxis());
/* 3566 */     if (rangeAxisState == null && 
/* 3567 */       parentState != null)
/*      */     {
/* 3569 */       rangeAxisState = (AxisState)parentState.getSharedAxisStates().get(getRangeAxis());
/*      */     }
/*      */     
/* 3572 */     if (rangeAxisState != null) {
/* 3573 */       drawRangeGridlines(g2, dataArea, rangeAxisState.getTicks());
/* 3574 */       drawZeroRangeBaseline(g2, dataArea);
/*      */     } 
/*      */     
/* 3577 */     Graphics2D savedG2 = g2;
/* 3578 */     BufferedImage dataImage = null;
/* 3579 */     boolean suppressShadow = Boolean.TRUE.equals(g2.getRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION));
/*      */     
/* 3581 */     if (this.shadowGenerator != null && !suppressShadow) {
/*      */       
/* 3583 */       dataImage = new BufferedImage((int)dataArea.getWidth(), (int)dataArea.getHeight(), 2);
/* 3584 */       g2 = dataImage.createGraphics();
/* 3585 */       g2.translate(-dataArea.getX(), -dataArea.getY());
/* 3586 */       g2.setRenderingHints(savedG2.getRenderingHints());
/*      */     } 
/*      */ 
/*      */     
/* 3590 */     for (CategoryItemRenderer renderer : this.renderers.values()) {
/* 3591 */       int i = getIndexOf(renderer);
/* 3592 */       drawDomainMarkers(g2, dataArea, i, Layer.BACKGROUND);
/*      */     } 
/* 3594 */     for (CategoryItemRenderer renderer : this.renderers.values()) {
/* 3595 */       int i = getIndexOf(renderer);
/* 3596 */       drawRangeMarkers(g2, dataArea, i, Layer.BACKGROUND);
/*      */     } 
/*      */ 
/*      */     
/* 3600 */     boolean foundData = false;
/*      */ 
/*      */     
/* 3603 */     Composite originalComposite = g2.getComposite();
/* 3604 */     g2.setComposite(AlphaComposite.getInstance(3, 
/* 3605 */           getForegroundAlpha()));
/*      */     
/* 3607 */     DatasetRenderingOrder order = getDatasetRenderingOrder();
/* 3608 */     List<Integer> datasetIndices = getDatasetIndices(order);
/* 3609 */     for (Iterator<Integer> iterator1 = datasetIndices.iterator(); iterator1.hasNext(); ) { int i = ((Integer)iterator1.next()).intValue();
/* 3610 */       foundData = (render(g2, dataArea, i, state, crosshairState) || foundData); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3615 */     List<Integer> rendererIndices = getRendererIndices(order); Iterator<Integer> iterator2;
/* 3616 */     for (iterator2 = rendererIndices.iterator(); iterator2.hasNext(); ) { int i = ((Integer)iterator2.next()).intValue();
/* 3617 */       drawDomainMarkers(g2, dataArea, i, Layer.FOREGROUND); }
/*      */     
/* 3619 */     for (iterator2 = rendererIndices.iterator(); iterator2.hasNext(); ) { int i = ((Integer)iterator2.next()).intValue();
/* 3620 */       drawRangeMarkers(g2, dataArea, i, Layer.FOREGROUND); }
/*      */ 
/*      */ 
/*      */     
/* 3624 */     drawAnnotations(g2, dataArea);
/*      */     
/* 3626 */     if (this.shadowGenerator != null && !suppressShadow) {
/* 3627 */       BufferedImage shadowImage = this.shadowGenerator.createDropShadow(dataImage);
/*      */       
/* 3629 */       g2 = savedG2;
/* 3630 */       g2.drawImage(shadowImage, (int)dataArea.getX() + this.shadowGenerator
/* 3631 */           .calculateOffsetX(), 
/* 3632 */           (int)dataArea.getY() + this.shadowGenerator
/* 3633 */           .calculateOffsetY(), (ImageObserver)null);
/* 3634 */       g2.drawImage(dataImage, (int)dataArea.getX(), 
/* 3635 */           (int)dataArea.getY(), (ImageObserver)null);
/*      */     } 
/* 3637 */     g2.setClip(savedClip);
/* 3638 */     g2.setComposite(originalComposite);
/*      */     
/* 3640 */     if (!foundData) {
/* 3641 */       drawNoDataMessage(g2, dataArea);
/*      */     }
/*      */     
/* 3644 */     int datasetIndex = crosshairState.getDatasetIndex();
/* 3645 */     setCrosshairDatasetIndex(datasetIndex, false);
/*      */ 
/*      */     
/* 3648 */     Comparable rowKey = crosshairState.getRowKey();
/* 3649 */     Comparable columnKey = crosshairState.getColumnKey();
/* 3650 */     setDomainCrosshairRowKey(rowKey, false);
/* 3651 */     setDomainCrosshairColumnKey(columnKey, false);
/* 3652 */     if (isDomainCrosshairVisible() && columnKey != null) {
/* 3653 */       Paint paint = getDomainCrosshairPaint();
/* 3654 */       Stroke stroke = getDomainCrosshairStroke();
/* 3655 */       drawDomainCrosshair(g2, dataArea, this.orientation, datasetIndex, rowKey, columnKey, stroke, paint);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3660 */     ValueAxis yAxis = getRangeAxisForDataset(datasetIndex);
/* 3661 */     RectangleEdge yAxisEdge = getRangeAxisEdge();
/* 3662 */     if (!this.rangeCrosshairLockedOnData && anchor != null) {
/*      */       double yy;
/* 3664 */       if (getOrientation() == PlotOrientation.VERTICAL) {
/* 3665 */         yy = yAxis.java2DToValue(anchor.getY(), dataArea, yAxisEdge);
/*      */       } else {
/*      */         
/* 3668 */         yy = yAxis.java2DToValue(anchor.getX(), dataArea, yAxisEdge);
/*      */       } 
/* 3670 */       crosshairState.setCrosshairY(yy);
/*      */     } 
/* 3672 */     setRangeCrosshairValue(crosshairState.getCrosshairY(), false);
/* 3673 */     if (isRangeCrosshairVisible()) {
/* 3674 */       double y = getRangeCrosshairValue();
/* 3675 */       Paint paint = getRangeCrosshairPaint();
/* 3676 */       Stroke stroke = getRangeCrosshairStroke();
/* 3677 */       drawRangeCrosshair(g2, dataArea, getOrientation(), y, yAxis, stroke, paint);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3682 */     if (isOutlineVisible()) {
/* 3683 */       if (getRenderer() != null) {
/* 3684 */         getRenderer().drawOutline(g2, this, dataArea);
/*      */       } else {
/*      */         
/* 3687 */         drawOutline(g2, dataArea);
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
/*      */   private List<Integer> getDatasetIndices(DatasetRenderingOrder order) {
/* 3701 */     List<Integer> result = new ArrayList<Integer>();
/*      */     
/* 3703 */     for (Map.Entry<Integer, CategoryDataset> entry : this.datasets.entrySet()) {
/* 3704 */       if (entry.getValue() != null) {
/* 3705 */         result.add(entry.getKey());
/*      */       }
/*      */     } 
/* 3708 */     Collections.sort(result);
/* 3709 */     if (order == DatasetRenderingOrder.REVERSE) {
/* 3710 */       Collections.reverse(result);
/*      */     }
/* 3712 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<Integer> getRendererIndices(DatasetRenderingOrder order) {
/* 3724 */     List<Integer> result = new ArrayList<Integer>();
/*      */     
/* 3726 */     for (Map.Entry<Integer, CategoryItemRenderer> entry : this.renderers.entrySet()) {
/* 3727 */       if (entry.getValue() != null) {
/* 3728 */         result.add(entry.getKey());
/*      */       }
/*      */     } 
/* 3731 */     Collections.sort(result);
/* 3732 */     if (order == DatasetRenderingOrder.REVERSE) {
/* 3733 */       Collections.reverse(result);
/*      */     }
/* 3735 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawBackground(Graphics2D g2, Rectangle2D area) {
/* 3750 */     fillBackground(g2, area, this.orientation);
/* 3751 */     drawBackgroundImage(g2, area);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Map drawAxes(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, PlotRenderingInfo plotState) {
/* 3768 */     AxisCollection axisCollection = new AxisCollection();
/*      */ 
/*      */     
/* 3771 */     for (CategoryAxis xAxis : this.domainAxes.values()) {
/* 3772 */       if (xAxis != null) {
/* 3773 */         int index = getDomainAxisIndex(xAxis);
/* 3774 */         axisCollection.add((Axis)xAxis, getDomainAxisEdge(index));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3779 */     for (ValueAxis yAxis : this.rangeAxes.values()) {
/* 3780 */       if (yAxis != null) {
/* 3781 */         int index = findRangeAxisIndex(yAxis);
/* 3782 */         axisCollection.add((Axis)yAxis, getRangeAxisEdge(index));
/*      */       } 
/*      */     } 
/*      */     
/* 3786 */     Map<Object, Object> axisStateMap = new HashMap<Object, Object>();
/*      */ 
/*      */     
/* 3789 */     double cursor = dataArea.getMinY() - this.axisOffset.calculateTopOutset(dataArea
/* 3790 */         .getHeight());
/* 3791 */     Iterator<Axis> iterator = axisCollection.getAxesAtTop().iterator();
/* 3792 */     while (iterator.hasNext()) {
/* 3793 */       Axis axis = iterator.next();
/* 3794 */       if (axis != null) {
/* 3795 */         AxisState axisState = axis.draw(g2, cursor, plotArea, dataArea, RectangleEdge.TOP, plotState);
/*      */         
/* 3797 */         cursor = axisState.getCursor();
/* 3798 */         axisStateMap.put(axis, axisState);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3804 */     cursor = dataArea.getMaxY() + this.axisOffset.calculateBottomOutset(dataArea.getHeight());
/* 3805 */     iterator = axisCollection.getAxesAtBottom().iterator();
/* 3806 */     while (iterator.hasNext()) {
/* 3807 */       Axis axis = iterator.next();
/* 3808 */       if (axis != null) {
/* 3809 */         AxisState axisState = axis.draw(g2, cursor, plotArea, dataArea, RectangleEdge.BOTTOM, plotState);
/*      */         
/* 3811 */         cursor = axisState.getCursor();
/* 3812 */         axisStateMap.put(axis, axisState);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3818 */     cursor = dataArea.getMinX() - this.axisOffset.calculateLeftOutset(dataArea.getWidth());
/* 3819 */     iterator = axisCollection.getAxesAtLeft().iterator();
/* 3820 */     while (iterator.hasNext()) {
/* 3821 */       Axis axis = iterator.next();
/* 3822 */       if (axis != null) {
/* 3823 */         AxisState axisState = axis.draw(g2, cursor, plotArea, dataArea, RectangleEdge.LEFT, plotState);
/*      */         
/* 3825 */         cursor = axisState.getCursor();
/* 3826 */         axisStateMap.put(axis, axisState);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3832 */     cursor = dataArea.getMaxX() + this.axisOffset.calculateRightOutset(dataArea.getWidth());
/* 3833 */     iterator = axisCollection.getAxesAtRight().iterator();
/* 3834 */     while (iterator.hasNext()) {
/* 3835 */       Axis axis = iterator.next();
/* 3836 */       if (axis != null) {
/* 3837 */         AxisState axisState = axis.draw(g2, cursor, plotArea, dataArea, RectangleEdge.RIGHT, plotState);
/*      */         
/* 3839 */         cursor = axisState.getCursor();
/* 3840 */         axisStateMap.put(axis, axisState);
/*      */       } 
/*      */     } 
/*      */     
/* 3844 */     return axisStateMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean render(Graphics2D g2, Rectangle2D dataArea, int index, PlotRenderingInfo info, CategoryCrosshairState crosshairState) {
/* 3866 */     boolean foundData = false;
/* 3867 */     CategoryDataset currentDataset = getDataset(index);
/* 3868 */     CategoryItemRenderer renderer = getRenderer(index);
/* 3869 */     CategoryAxis domainAxis = getDomainAxisForDataset(index);
/* 3870 */     ValueAxis rangeAxis = getRangeAxisForDataset(index);
/* 3871 */     boolean hasData = !DatasetUtilities.isEmptyOrNull(currentDataset);
/* 3872 */     if (hasData && renderer != null) {
/*      */       
/* 3874 */       foundData = true;
/* 3875 */       CategoryItemRendererState state = renderer.initialise(g2, dataArea, this, index, info);
/*      */       
/* 3877 */       state.setCrosshairState(crosshairState);
/* 3878 */       int columnCount = currentDataset.getColumnCount();
/* 3879 */       int rowCount = currentDataset.getRowCount();
/* 3880 */       int passCount = renderer.getPassCount();
/* 3881 */       for (int pass = 0; pass < passCount; pass++) {
/* 3882 */         if (this.columnRenderingOrder == SortOrder.ASCENDING) {
/* 3883 */           for (int column = 0; column < columnCount; column++) {
/* 3884 */             if (this.rowRenderingOrder == SortOrder.ASCENDING) {
/* 3885 */               for (int row = 0; row < rowCount; row++) {
/* 3886 */                 renderer.drawItem(g2, state, dataArea, this, domainAxis, rangeAxis, currentDataset, row, column, pass);
/*      */               
/*      */               }
/*      */             }
/*      */             else {
/*      */               
/* 3892 */               for (int row = rowCount - 1; row >= 0; row--) {
/* 3893 */                 renderer.drawItem(g2, state, dataArea, this, domainAxis, rangeAxis, currentDataset, row, column, pass);
/*      */               }
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 3901 */           for (int column = columnCount - 1; column >= 0; column--) {
/* 3902 */             if (this.rowRenderingOrder == SortOrder.ASCENDING) {
/* 3903 */               for (int row = 0; row < rowCount; row++) {
/* 3904 */                 renderer.drawItem(g2, state, dataArea, this, domainAxis, rangeAxis, currentDataset, row, column, pass);
/*      */               
/*      */               }
/*      */             }
/*      */             else {
/*      */               
/* 3910 */               for (int row = rowCount - 1; row >= 0; row--) {
/* 3911 */                 renderer.drawItem(g2, state, dataArea, this, domainAxis, rangeAxis, currentDataset, row, column, pass);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3920 */     return foundData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawDomainGridlines(Graphics2D g2, Rectangle2D dataArea) {
/* 3934 */     if (!isDomainGridlinesVisible()) {
/*      */       return;
/*      */     }
/* 3937 */     CategoryAnchor anchor = getDomainGridlinePosition();
/* 3938 */     RectangleEdge domainAxisEdge = getDomainAxisEdge();
/* 3939 */     CategoryDataset dataset = getDataset();
/* 3940 */     if (dataset == null) {
/*      */       return;
/*      */     }
/* 3943 */     CategoryAxis axis = getDomainAxis();
/* 3944 */     if (axis != null) {
/* 3945 */       int columnCount = dataset.getColumnCount();
/* 3946 */       for (int c = 0; c < columnCount; c++) {
/* 3947 */         double xx = axis.getCategoryJava2DCoordinate(anchor, c, columnCount, dataArea, domainAxisEdge);
/*      */         
/* 3949 */         CategoryItemRenderer renderer1 = getRenderer();
/* 3950 */         if (renderer1 != null) {
/* 3951 */           renderer1.drawDomainGridline(g2, this, dataArea, xx);
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
/*      */   
/*      */   protected void drawRangeGridlines(Graphics2D g2, Rectangle2D dataArea, List ticks) {
/* 3969 */     if (!isRangeGridlinesVisible() && !isRangeMinorGridlinesVisible()) {
/*      */       return;
/*      */     }
/*      */     
/* 3973 */     ValueAxis axis = getRangeAxis();
/* 3974 */     if (axis == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3978 */     CategoryItemRenderer r = getRenderer();
/* 3979 */     if (r == null) {
/*      */       return;
/*      */     }
/*      */     
/* 3983 */     Stroke gridStroke = null;
/* 3984 */     Paint gridPaint = null;
/*      */     
/* 3986 */     Iterator<ValueTick> iterator = ticks.iterator();
/* 3987 */     while (iterator.hasNext()) {
/* 3988 */       boolean paintLine = false;
/* 3989 */       ValueTick tick = iterator.next();
/* 3990 */       if (tick.getTickType() == TickType.MINOR && 
/* 3991 */         isRangeMinorGridlinesVisible()) {
/* 3992 */         gridStroke = getRangeMinorGridlineStroke();
/* 3993 */         gridPaint = getRangeMinorGridlinePaint();
/* 3994 */         paintLine = true;
/*      */       }
/* 3996 */       else if (tick.getTickType() == TickType.MAJOR && 
/* 3997 */         isRangeGridlinesVisible()) {
/* 3998 */         gridStroke = getRangeGridlineStroke();
/* 3999 */         gridPaint = getRangeGridlinePaint();
/* 4000 */         paintLine = true;
/*      */       } 
/* 4002 */       if ((tick.getValue() != 0.0D || 
/* 4003 */         !isRangeZeroBaselineVisible()) && paintLine) {
/*      */ 
/*      */         
/* 4006 */         if (r instanceof AbstractCategoryItemRenderer) {
/* 4007 */           AbstractCategoryItemRenderer aci = (AbstractCategoryItemRenderer)r;
/*      */           
/* 4009 */           aci.drawRangeLine(g2, this, axis, dataArea, tick
/* 4010 */               .getValue(), gridPaint, gridStroke);
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 4015 */         r.drawRangeGridline(g2, this, axis, dataArea, tick
/* 4016 */             .getValue());
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
/*      */   protected void drawZeroRangeBaseline(Graphics2D g2, Rectangle2D area) {
/* 4033 */     if (!isRangeZeroBaselineVisible()) {
/*      */       return;
/*      */     }
/* 4036 */     CategoryItemRenderer r = getRenderer();
/* 4037 */     if (r instanceof AbstractCategoryItemRenderer) {
/* 4038 */       AbstractCategoryItemRenderer aci = (AbstractCategoryItemRenderer)r;
/* 4039 */       aci.drawRangeLine(g2, this, getRangeAxis(), area, 0.0D, this.rangeZeroBaselinePaint, this.rangeZeroBaselineStroke);
/*      */     }
/*      */     else {
/*      */       
/* 4043 */       r.drawRangeGridline(g2, this, getRangeAxis(), area, 0.0D);
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
/*      */   protected void drawAnnotations(Graphics2D g2, Rectangle2D dataArea) {
/* 4055 */     if (getAnnotations() != null) {
/* 4056 */       Iterator<CategoryAnnotation> iterator = getAnnotations().iterator();
/* 4057 */       while (iterator.hasNext()) {
/*      */         
/* 4059 */         CategoryAnnotation annotation = iterator.next();
/* 4060 */         annotation.draw(g2, this, dataArea, getDomainAxis(), 
/* 4061 */             getRangeAxis());
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
/*      */   protected void drawDomainMarkers(Graphics2D g2, Rectangle2D dataArea, int index, Layer layer) {
/* 4081 */     CategoryItemRenderer r = getRenderer(index);
/* 4082 */     if (r == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4086 */     Collection markers = getDomainMarkers(index, layer);
/* 4087 */     CategoryAxis axis = getDomainAxisForDataset(index);
/* 4088 */     if (markers != null && axis != null) {
/* 4089 */       Iterator<CategoryMarker> iterator = markers.iterator();
/* 4090 */       while (iterator.hasNext()) {
/* 4091 */         CategoryMarker marker = iterator.next();
/* 4092 */         r.drawDomainMarker(g2, this, axis, marker, dataArea);
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
/*      */   protected void drawRangeMarkers(Graphics2D g2, Rectangle2D dataArea, int index, Layer layer) {
/* 4112 */     CategoryItemRenderer r = getRenderer(index);
/* 4113 */     if (r == null) {
/*      */       return;
/*      */     }
/*      */     
/* 4117 */     Collection markers = getRangeMarkers(index, layer);
/* 4118 */     ValueAxis axis = getRangeAxisForDataset(index);
/* 4119 */     if (markers != null && axis != null) {
/* 4120 */       Iterator<Marker> iterator = markers.iterator();
/* 4121 */       while (iterator.hasNext()) {
/* 4122 */         Marker marker = iterator.next();
/* 4123 */         r.drawRangeMarker(g2, this, axis, marker, dataArea);
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
/*      */   protected void drawRangeLine(Graphics2D g2, Rectangle2D dataArea, double value, Stroke stroke, Paint paint) {
/* 4142 */     double java2D = getRangeAxis().valueToJava2D(value, dataArea, 
/* 4143 */         getRangeAxisEdge());
/* 4144 */     Line2D line = null;
/* 4145 */     if (this.orientation == PlotOrientation.HORIZONTAL) {
/*      */       
/* 4147 */       line = new Line2D.Double(java2D, dataArea.getMinY(), java2D, dataArea.getMaxY());
/*      */     }
/* 4149 */     else if (this.orientation == PlotOrientation.VERTICAL) {
/*      */       
/* 4151 */       line = new Line2D.Double(dataArea.getMinX(), java2D, dataArea.getMaxX(), java2D);
/*      */     } 
/* 4153 */     g2.setStroke(stroke);
/* 4154 */     g2.setPaint(paint);
/* 4155 */     g2.draw(line);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawDomainCrosshair(Graphics2D g2, Rectangle2D dataArea, PlotOrientation orientation, int datasetIndex, Comparable rowKey, Comparable columnKey, Stroke stroke, Paint paint) {
/*      */     Line2D line;
/* 4181 */     CategoryDataset dataset = getDataset(datasetIndex);
/* 4182 */     CategoryAxis axis = getDomainAxisForDataset(datasetIndex);
/* 4183 */     CategoryItemRenderer renderer = getRenderer(datasetIndex);
/*      */     
/* 4185 */     if (orientation == PlotOrientation.VERTICAL) {
/* 4186 */       double xx = renderer.getItemMiddle(rowKey, columnKey, dataset, axis, dataArea, RectangleEdge.BOTTOM);
/*      */ 
/*      */       
/* 4189 */       line = new Line2D.Double(xx, dataArea.getMinY(), xx, dataArea.getMaxY());
/*      */     } else {
/*      */       
/* 4192 */       double yy = renderer.getItemMiddle(rowKey, columnKey, dataset, axis, dataArea, RectangleEdge.LEFT);
/*      */ 
/*      */       
/* 4195 */       line = new Line2D.Double(dataArea.getMinX(), yy, dataArea.getMaxX(), yy);
/*      */     } 
/* 4197 */     g2.setStroke(stroke);
/* 4198 */     g2.setPaint(paint);
/* 4199 */     g2.draw(line);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawRangeCrosshair(Graphics2D g2, Rectangle2D dataArea, PlotOrientation orientation, double value, ValueAxis axis, Stroke stroke, Paint paint) {
/*      */     Line2D line;
/* 4223 */     if (!axis.getRange().contains(value)) {
/*      */       return;
/*      */     }
/*      */     
/* 4227 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 4228 */       double xx = axis.valueToJava2D(value, dataArea, RectangleEdge.BOTTOM);
/*      */ 
/*      */       
/* 4231 */       line = new Line2D.Double(xx, dataArea.getMinY(), xx, dataArea.getMaxY());
/*      */     } else {
/*      */       
/* 4234 */       double yy = axis.valueToJava2D(value, dataArea, RectangleEdge.LEFT);
/*      */ 
/*      */       
/* 4237 */       line = new Line2D.Double(dataArea.getMinX(), yy, dataArea.getMaxX(), yy);
/*      */     } 
/* 4239 */     g2.setStroke(stroke);
/* 4240 */     g2.setPaint(paint);
/* 4241 */     g2.draw(line);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 4256 */     Range result = null;
/* 4257 */     List<CategoryDataset> mappedDatasets = new ArrayList<CategoryDataset>();
/* 4258 */     int rangeIndex = findRangeAxisIndex(axis);
/* 4259 */     if (rangeIndex >= 0) {
/* 4260 */       mappedDatasets.addAll(datasetsMappedToRangeAxis(rangeIndex));
/*      */     }
/* 4262 */     else if (axis == getRangeAxis()) {
/* 4263 */       mappedDatasets.addAll(datasetsMappedToRangeAxis(0));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4268 */     for (CategoryDataset d : mappedDatasets) {
/* 4269 */       CategoryItemRenderer r = getRendererForDataset(d);
/* 4270 */       if (r != null) {
/* 4271 */         result = Range.combine(result, r.findRangeBounds(d));
/*      */       }
/*      */     } 
/* 4274 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<CategoryDataset> datasetsMappedToDomainAxis(int axisIndex) {
/* 4288 */     Integer key = new Integer(axisIndex);
/* 4289 */     List<CategoryDataset> result = new ArrayList<CategoryDataset>();
/* 4290 */     for (CategoryDataset dataset : this.datasets.values()) {
/* 4291 */       if (dataset == null) {
/*      */         continue;
/*      */       }
/* 4294 */       int i = indexOf(dataset);
/* 4295 */       List mappedAxes = (List)this.datasetToDomainAxesMap.get(new Integer(i));
/*      */       
/* 4297 */       if (mappedAxes == null) {
/* 4298 */         if (key.equals(ZERO))
/* 4299 */           result.add(dataset); 
/*      */         continue;
/*      */       } 
/* 4302 */       if (mappedAxes.contains(key)) {
/* 4303 */         result.add(dataset);
/*      */       }
/*      */     } 
/*      */     
/* 4307 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List datasetsMappedToRangeAxis(int index) {
/* 4319 */     Integer key = new Integer(index);
/* 4320 */     List result = new ArrayList();
/* 4321 */     for (CategoryDataset dataset : this.datasets.values()) {
/* 4322 */       int i = indexOf(dataset);
/* 4323 */       List mappedAxes = (List)this.datasetToRangeAxesMap.get(new Integer(i));
/*      */       
/* 4325 */       if (mappedAxes == null) {
/* 4326 */         if (key.equals(ZERO))
/* 4327 */           result.add(this.datasets.get(Integer.valueOf(i))); 
/*      */         continue;
/*      */       } 
/* 4330 */       if (mappedAxes.contains(key)) {
/* 4331 */         result.add(this.datasets.get(Integer.valueOf(i)));
/*      */       }
/*      */     } 
/*      */     
/* 4335 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getWeight() {
/* 4347 */     return this.weight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWeight(int weight) {
/* 4359 */     this.weight = weight;
/* 4360 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisSpace getFixedDomainAxisSpace() {
/* 4371 */     return this.fixedDomainAxisSpace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFixedDomainAxisSpace(AxisSpace space) {
/* 4383 */     setFixedDomainAxisSpace(space, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFixedDomainAxisSpace(AxisSpace space, boolean notify) {
/* 4398 */     this.fixedDomainAxisSpace = space;
/* 4399 */     if (notify) {
/* 4400 */       fireChangeEvent();
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
/*      */   public AxisSpace getFixedRangeAxisSpace() {
/* 4412 */     return this.fixedRangeAxisSpace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFixedRangeAxisSpace(AxisSpace space) {
/* 4424 */     setFixedRangeAxisSpace(space, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFixedRangeAxisSpace(AxisSpace space, boolean notify) {
/* 4439 */     this.fixedRangeAxisSpace = space;
/* 4440 */     if (notify) {
/* 4441 */       fireChangeEvent();
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
/*      */   public List getCategories() {
/* 4453 */     List<?> result = null;
/* 4454 */     if (getDataset() != null) {
/* 4455 */       result = Collections.unmodifiableList(getDataset().getColumnKeys());
/*      */     }
/* 4457 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List getCategoriesForAxis(CategoryAxis axis) {
/* 4471 */     List<Comparable> result = new ArrayList();
/* 4472 */     int axisIndex = getDomainAxisIndex(axis);
/* 4473 */     for (CategoryDataset dataset : datasetsMappedToDomainAxis(axisIndex)) {
/*      */       
/* 4475 */       for (int i = 0; i < dataset.getColumnCount(); i++) {
/* 4476 */         Comparable category = dataset.getColumnKey(i);
/* 4477 */         if (!result.contains(category)) {
/* 4478 */           result.add(category);
/*      */         }
/*      */       } 
/*      */     } 
/* 4482 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDrawSharedDomainAxis() {
/* 4494 */     return this.drawSharedDomainAxis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDrawSharedDomainAxis(boolean draw) {
/* 4506 */     this.drawSharedDomainAxis = draw;
/* 4507 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 4522 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 4538 */     return this.rangePannable;
/*      */   }
/*      */ 
/*      */ 
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
/* 4552 */     this.rangePannable = pannable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void panDomainAxes(double percent, PlotRenderingInfo info, Point2D source) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 4582 */     if (!isRangePannable()) {
/*      */       return;
/*      */     }
/* 4585 */     for (ValueAxis axis : this.rangeAxes.values()) {
/* 4586 */       if (axis == null) {
/*      */         continue;
/*      */       }
/* 4589 */       double length = axis.getRange().getLength();
/* 4590 */       double adj = percent * length;
/* 4591 */       if (axis.isInverted()) {
/* 4592 */         adj = -adj;
/*      */       }
/* 4594 */       axis.setRange(axis.getLowerBound() + adj, axis
/* 4595 */           .getUpperBound() + adj);
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
/*      */   public boolean isDomainZoomable() {
/* 4609 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangeZoomable() {
/* 4621 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomDomainAxes(double factor, PlotRenderingInfo state, Point2D source) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomDomainAxes(double lowerPercent, double upperPercent, PlotRenderingInfo state, Point2D source) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomDomainAxes(double factor, PlotRenderingInfo info, Point2D source, boolean useAnchor) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomRangeAxes(double factor, PlotRenderingInfo state, Point2D source) {
/* 4683 */     zoomRangeAxes(factor, state, source, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 4704 */     for (ValueAxis rangeAxis : this.rangeAxes.values()) {
/* 4705 */       if (rangeAxis == null) {
/*      */         continue;
/*      */       }
/* 4708 */       if (useAnchor) {
/*      */         
/* 4710 */         double sourceY = source.getY();
/* 4711 */         if (this.orientation.isHorizontal()) {
/* 4712 */           sourceY = source.getX();
/*      */         }
/* 4714 */         double anchorY = rangeAxis.java2DToValue(sourceY, info
/* 4715 */             .getDataArea(), getRangeAxisEdge());
/* 4716 */         rangeAxis.resizeRange2(factor, anchorY); continue;
/*      */       } 
/* 4718 */       rangeAxis.resizeRange(factor);
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
/*      */   public void zoomRangeAxes(double lowerPercent, double upperPercent, PlotRenderingInfo state, Point2D source) {
/* 4734 */     for (ValueAxis yAxis : this.rangeAxes.values()) {
/* 4735 */       if (yAxis != null) {
/* 4736 */         yAxis.zoomRange(lowerPercent, upperPercent);
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
/*      */   public double getAnchorValue() {
/* 4749 */     return this.anchorValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnchorValue(double value) {
/* 4761 */     setAnchorValue(value, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnchorValue(double value, boolean notify) {
/* 4774 */     this.anchorValue = value;
/* 4775 */     if (notify) {
/* 4776 */       fireChangeEvent();
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
/*      */   public boolean equals(Object obj) {
/* 4789 */     if (obj == this) {
/* 4790 */       return true;
/*      */     }
/* 4792 */     if (!(obj instanceof CategoryPlot)) {
/* 4793 */       return false;
/*      */     }
/* 4795 */     CategoryPlot that = (CategoryPlot)obj;
/* 4796 */     if (this.orientation != that.orientation) {
/* 4797 */       return false;
/*      */     }
/* 4799 */     if (!ObjectUtilities.equal(this.axisOffset, that.axisOffset)) {
/* 4800 */       return false;
/*      */     }
/* 4802 */     if (!this.domainAxes.equals(that.domainAxes)) {
/* 4803 */       return false;
/*      */     }
/* 4805 */     if (!this.domainAxisLocations.equals(that.domainAxisLocations)) {
/* 4806 */       return false;
/*      */     }
/* 4808 */     if (this.drawSharedDomainAxis != that.drawSharedDomainAxis) {
/* 4809 */       return false;
/*      */     }
/* 4811 */     if (!this.rangeAxes.equals(that.rangeAxes)) {
/* 4812 */       return false;
/*      */     }
/* 4814 */     if (!this.rangeAxisLocations.equals(that.rangeAxisLocations)) {
/* 4815 */       return false;
/*      */     }
/* 4817 */     if (!ObjectUtilities.equal(this.datasetToDomainAxesMap, that.datasetToDomainAxesMap))
/*      */     {
/* 4819 */       return false;
/*      */     }
/* 4821 */     if (!ObjectUtilities.equal(this.datasetToRangeAxesMap, that.datasetToRangeAxesMap))
/*      */     {
/* 4823 */       return false;
/*      */     }
/* 4825 */     if (!ObjectUtilities.equal(this.renderers, that.renderers)) {
/* 4826 */       return false;
/*      */     }
/* 4828 */     if (this.renderingOrder != that.renderingOrder) {
/* 4829 */       return false;
/*      */     }
/* 4831 */     if (this.columnRenderingOrder != that.columnRenderingOrder) {
/* 4832 */       return false;
/*      */     }
/* 4834 */     if (this.rowRenderingOrder != that.rowRenderingOrder) {
/* 4835 */       return false;
/*      */     }
/* 4837 */     if (this.domainGridlinesVisible != that.domainGridlinesVisible) {
/* 4838 */       return false;
/*      */     }
/* 4840 */     if (this.domainGridlinePosition != that.domainGridlinePosition) {
/* 4841 */       return false;
/*      */     }
/* 4843 */     if (!ObjectUtilities.equal(this.domainGridlineStroke, that.domainGridlineStroke))
/*      */     {
/* 4845 */       return false;
/*      */     }
/* 4847 */     if (!PaintUtilities.equal(this.domainGridlinePaint, that.domainGridlinePaint))
/*      */     {
/* 4849 */       return false;
/*      */     }
/* 4851 */     if (this.rangeGridlinesVisible != that.rangeGridlinesVisible) {
/* 4852 */       return false;
/*      */     }
/* 4854 */     if (!ObjectUtilities.equal(this.rangeGridlineStroke, that.rangeGridlineStroke))
/*      */     {
/* 4856 */       return false;
/*      */     }
/* 4858 */     if (!PaintUtilities.equal(this.rangeGridlinePaint, that.rangeGridlinePaint))
/*      */     {
/* 4860 */       return false;
/*      */     }
/* 4862 */     if (this.anchorValue != that.anchorValue) {
/* 4863 */       return false;
/*      */     }
/* 4865 */     if (this.rangeCrosshairVisible != that.rangeCrosshairVisible) {
/* 4866 */       return false;
/*      */     }
/* 4868 */     if (this.rangeCrosshairValue != that.rangeCrosshairValue) {
/* 4869 */       return false;
/*      */     }
/* 4871 */     if (!ObjectUtilities.equal(this.rangeCrosshairStroke, that.rangeCrosshairStroke))
/*      */     {
/* 4873 */       return false;
/*      */     }
/* 4875 */     if (!PaintUtilities.equal(this.rangeCrosshairPaint, that.rangeCrosshairPaint))
/*      */     {
/* 4877 */       return false;
/*      */     }
/* 4879 */     if (this.rangeCrosshairLockedOnData != that.rangeCrosshairLockedOnData)
/*      */     {
/* 4881 */       return false;
/*      */     }
/* 4883 */     if (!ObjectUtilities.equal(this.foregroundDomainMarkers, that.foregroundDomainMarkers))
/*      */     {
/* 4885 */       return false;
/*      */     }
/* 4887 */     if (!ObjectUtilities.equal(this.backgroundDomainMarkers, that.backgroundDomainMarkers))
/*      */     {
/* 4889 */       return false;
/*      */     }
/* 4891 */     if (!ObjectUtilities.equal(this.foregroundRangeMarkers, that.foregroundRangeMarkers))
/*      */     {
/* 4893 */       return false;
/*      */     }
/* 4895 */     if (!ObjectUtilities.equal(this.backgroundRangeMarkers, that.backgroundRangeMarkers))
/*      */     {
/* 4897 */       return false;
/*      */     }
/* 4899 */     if (!ObjectUtilities.equal(this.annotations, that.annotations)) {
/* 4900 */       return false;
/*      */     }
/* 4902 */     if (this.weight != that.weight) {
/* 4903 */       return false;
/*      */     }
/* 4905 */     if (!ObjectUtilities.equal(this.fixedDomainAxisSpace, that.fixedDomainAxisSpace))
/*      */     {
/* 4907 */       return false;
/*      */     }
/* 4909 */     if (!ObjectUtilities.equal(this.fixedRangeAxisSpace, that.fixedRangeAxisSpace))
/*      */     {
/* 4911 */       return false;
/*      */     }
/* 4913 */     if (!ObjectUtilities.equal(this.fixedLegendItems, that.fixedLegendItems))
/*      */     {
/* 4915 */       return false;
/*      */     }
/* 4917 */     if (this.domainCrosshairVisible != that.domainCrosshairVisible) {
/* 4918 */       return false;
/*      */     }
/* 4920 */     if (this.crosshairDatasetIndex != that.crosshairDatasetIndex) {
/* 4921 */       return false;
/*      */     }
/* 4923 */     if (!ObjectUtilities.equal(this.domainCrosshairColumnKey, that.domainCrosshairColumnKey))
/*      */     {
/* 4925 */       return false;
/*      */     }
/* 4927 */     if (!ObjectUtilities.equal(this.domainCrosshairRowKey, that.domainCrosshairRowKey))
/*      */     {
/* 4929 */       return false;
/*      */     }
/* 4931 */     if (!PaintUtilities.equal(this.domainCrosshairPaint, that.domainCrosshairPaint))
/*      */     {
/* 4933 */       return false;
/*      */     }
/* 4935 */     if (!ObjectUtilities.equal(this.domainCrosshairStroke, that.domainCrosshairStroke))
/*      */     {
/* 4937 */       return false;
/*      */     }
/* 4939 */     if (this.rangeMinorGridlinesVisible != that.rangeMinorGridlinesVisible)
/*      */     {
/* 4941 */       return false;
/*      */     }
/* 4943 */     if (!PaintUtilities.equal(this.rangeMinorGridlinePaint, that.rangeMinorGridlinePaint))
/*      */     {
/* 4945 */       return false;
/*      */     }
/* 4947 */     if (!ObjectUtilities.equal(this.rangeMinorGridlineStroke, that.rangeMinorGridlineStroke))
/*      */     {
/* 4949 */       return false;
/*      */     }
/* 4951 */     if (this.rangeZeroBaselineVisible != that.rangeZeroBaselineVisible) {
/* 4952 */       return false;
/*      */     }
/* 4954 */     if (!PaintUtilities.equal(this.rangeZeroBaselinePaint, that.rangeZeroBaselinePaint))
/*      */     {
/* 4956 */       return false;
/*      */     }
/* 4958 */     if (!ObjectUtilities.equal(this.rangeZeroBaselineStroke, that.rangeZeroBaselineStroke))
/*      */     {
/* 4960 */       return false;
/*      */     }
/* 4962 */     if (!ObjectUtilities.equal(this.shadowGenerator, that.shadowGenerator))
/*      */     {
/* 4964 */       return false;
/*      */     }
/* 4966 */     return super.equals(obj);
/*      */   }
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
/* 4978 */     CategoryPlot clone = (CategoryPlot)super.clone();
/* 4979 */     clone.domainAxes = CloneUtils.cloneMapValues(this.domainAxes);
/* 4980 */     for (CategoryAxis axis : clone.domainAxes.values()) {
/* 4981 */       if (axis != null) {
/* 4982 */         axis.setPlot(clone);
/* 4983 */         axis.addChangeListener(clone);
/*      */       } 
/*      */     } 
/* 4986 */     clone.rangeAxes = CloneUtils.cloneMapValues(this.rangeAxes);
/* 4987 */     for (ValueAxis axis : clone.rangeAxes.values()) {
/* 4988 */       if (axis != null) {
/* 4989 */         axis.setPlot(clone);
/* 4990 */         axis.addChangeListener(clone);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4995 */     clone.domainAxisLocations = new HashMap<Integer, AxisLocation>(this.domainAxisLocations);
/*      */     
/* 4997 */     clone.rangeAxisLocations = new HashMap<Integer, AxisLocation>(this.rangeAxisLocations);
/*      */ 
/*      */     
/* 5000 */     clone.datasets = new HashMap<Integer, CategoryDataset>(this.datasets);
/* 5001 */     for (CategoryDataset dataset : clone.datasets.values()) {
/* 5002 */       if (dataset != null) {
/* 5003 */         dataset.addChangeListener(clone);
/*      */       }
/*      */     } 
/* 5006 */     clone.datasetToDomainAxesMap = new TreeMap<Object, Object>();
/* 5007 */     clone.datasetToDomainAxesMap.putAll(this.datasetToDomainAxesMap);
/* 5008 */     clone.datasetToRangeAxesMap = new TreeMap<Object, Object>();
/* 5009 */     clone.datasetToRangeAxesMap.putAll(this.datasetToRangeAxesMap);
/*      */     
/* 5011 */     clone.renderers = CloneUtils.cloneMapValues(this.renderers);
/* 5012 */     for (CategoryItemRenderer renderer : clone.renderers.values()) {
/* 5013 */       if (renderer != null) {
/* 5014 */         renderer.setPlot(clone);
/* 5015 */         renderer.addChangeListener(clone);
/*      */       } 
/*      */     } 
/* 5018 */     if (this.fixedDomainAxisSpace != null) {
/* 5019 */       clone.fixedDomainAxisSpace = (AxisSpace)ObjectUtilities.clone(this.fixedDomainAxisSpace);
/*      */     }
/*      */     
/* 5022 */     if (this.fixedRangeAxisSpace != null) {
/* 5023 */       clone.fixedRangeAxisSpace = (AxisSpace)ObjectUtilities.clone(this.fixedRangeAxisSpace);
/*      */     }
/*      */ 
/*      */     
/* 5027 */     clone.annotations = (List)ObjectUtilities.deepClone(this.annotations);
/* 5028 */     clone.foregroundDomainMarkers = cloneMarkerMap(this.foregroundDomainMarkers);
/*      */     
/* 5030 */     clone.backgroundDomainMarkers = cloneMarkerMap(this.backgroundDomainMarkers);
/*      */     
/* 5032 */     clone.foregroundRangeMarkers = cloneMarkerMap(this.foregroundRangeMarkers);
/*      */     
/* 5034 */     clone.backgroundRangeMarkers = cloneMarkerMap(this.backgroundRangeMarkers);
/*      */     
/* 5036 */     if (this.fixedLegendItems != null) {
/* 5037 */       clone
/* 5038 */         .fixedLegendItems = (LegendItemCollection)this.fixedLegendItems.clone();
/*      */     }
/* 5040 */     return clone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map cloneMarkerMap(Map map) throws CloneNotSupportedException {
/* 5054 */     Map<Object, Object> clone = new HashMap<Object, Object>();
/* 5055 */     Set keys = map.keySet();
/* 5056 */     Iterator iterator = keys.iterator();
/* 5057 */     while (iterator.hasNext()) {
/* 5058 */       Object key = iterator.next();
/* 5059 */       List entry = (List)map.get(key);
/* 5060 */       Object toAdd = ObjectUtilities.deepClone(entry);
/* 5061 */       clone.put(key, toAdd);
/*      */     } 
/* 5063 */     return clone;
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
/* 5074 */     stream.defaultWriteObject();
/* 5075 */     SerialUtilities.writeStroke(this.domainGridlineStroke, stream);
/* 5076 */     SerialUtilities.writePaint(this.domainGridlinePaint, stream);
/* 5077 */     SerialUtilities.writeStroke(this.rangeGridlineStroke, stream);
/* 5078 */     SerialUtilities.writePaint(this.rangeGridlinePaint, stream);
/* 5079 */     SerialUtilities.writeStroke(this.rangeCrosshairStroke, stream);
/* 5080 */     SerialUtilities.writePaint(this.rangeCrosshairPaint, stream);
/* 5081 */     SerialUtilities.writeStroke(this.domainCrosshairStroke, stream);
/* 5082 */     SerialUtilities.writePaint(this.domainCrosshairPaint, stream);
/* 5083 */     SerialUtilities.writeStroke(this.rangeMinorGridlineStroke, stream);
/* 5084 */     SerialUtilities.writePaint(this.rangeMinorGridlinePaint, stream);
/* 5085 */     SerialUtilities.writeStroke(this.rangeZeroBaselineStroke, stream);
/* 5086 */     SerialUtilities.writePaint(this.rangeZeroBaselinePaint, stream);
/*      */   }
/*      */ 
/*      */ 
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
/* 5100 */     stream.defaultReadObject();
/* 5101 */     this.domainGridlineStroke = SerialUtilities.readStroke(stream);
/* 5102 */     this.domainGridlinePaint = SerialUtilities.readPaint(stream);
/* 5103 */     this.rangeGridlineStroke = SerialUtilities.readStroke(stream);
/* 5104 */     this.rangeGridlinePaint = SerialUtilities.readPaint(stream);
/* 5105 */     this.rangeCrosshairStroke = SerialUtilities.readStroke(stream);
/* 5106 */     this.rangeCrosshairPaint = SerialUtilities.readPaint(stream);
/* 5107 */     this.domainCrosshairStroke = SerialUtilities.readStroke(stream);
/* 5108 */     this.domainCrosshairPaint = SerialUtilities.readPaint(stream);
/* 5109 */     this.rangeMinorGridlineStroke = SerialUtilities.readStroke(stream);
/* 5110 */     this.rangeMinorGridlinePaint = SerialUtilities.readPaint(stream);
/* 5111 */     this.rangeZeroBaselineStroke = SerialUtilities.readStroke(stream);
/* 5112 */     this.rangeZeroBaselinePaint = SerialUtilities.readPaint(stream);
/*      */     
/* 5114 */     for (CategoryAxis xAxis : this.domainAxes.values()) {
/* 5115 */       if (xAxis != null) {
/* 5116 */         xAxis.setPlot(this);
/* 5117 */         xAxis.addChangeListener(this);
/*      */       } 
/*      */     } 
/* 5120 */     for (ValueAxis yAxis : this.rangeAxes.values()) {
/* 5121 */       if (yAxis != null) {
/* 5122 */         yAxis.setPlot(this);
/* 5123 */         yAxis.addChangeListener(this);
/*      */       } 
/*      */     } 
/* 5126 */     for (CategoryDataset dataset : this.datasets.values()) {
/* 5127 */       if (dataset != null) {
/* 5128 */         dataset.addChangeListener(this);
/*      */       }
/*      */     } 
/* 5131 */     for (CategoryItemRenderer renderer : this.renderers.values()) {
/* 5132 */       if (renderer != null)
/* 5133 */         renderer.addChangeListener(this); 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/CategoryPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */