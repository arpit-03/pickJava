/*      */ package org.jfree.chart.plot;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.RenderingHints;
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
/*      */ import org.jfree.chart.LegendItem;
/*      */ import org.jfree.chart.LegendItemCollection;
/*      */ import org.jfree.chart.annotations.XYAnnotation;
/*      */ import org.jfree.chart.annotations.XYAnnotationBoundsInfo;
/*      */ import org.jfree.chart.axis.Axis;
/*      */ import org.jfree.chart.axis.AxisCollection;
/*      */ import org.jfree.chart.axis.AxisLocation;
/*      */ import org.jfree.chart.axis.AxisSpace;
/*      */ import org.jfree.chart.axis.AxisState;
/*      */ import org.jfree.chart.axis.TickType;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.axis.ValueTick;
/*      */ import org.jfree.chart.event.AnnotationChangeEvent;
/*      */ import org.jfree.chart.event.ChartChangeEventType;
/*      */ import org.jfree.chart.event.PlotChangeEvent;
/*      */ import org.jfree.chart.event.RendererChangeEvent;
/*      */ import org.jfree.chart.event.RendererChangeListener;
/*      */ import org.jfree.chart.renderer.RendererUtilities;
/*      */ import org.jfree.chart.renderer.xy.AbstractXYItemRenderer;
/*      */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*      */ import org.jfree.chart.renderer.xy.XYItemRendererState;
/*      */ import org.jfree.chart.util.CloneUtils;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.chart.util.ResourceBundleWrapper;
/*      */ import org.jfree.chart.util.ShadowGenerator;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.general.DatasetChangeEvent;
/*      */ import org.jfree.data.general.DatasetUtilities;
/*      */ import org.jfree.data.xy.XYDataset;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.ui.Layer;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ import org.jfree.util.ObjectUtilities;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XYPlot
/*      */   extends Plot
/*      */   implements ValueAxisPlot, Pannable, Zoomable, RendererChangeListener, Cloneable, PublicCloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 7044148245716569264L;
/*  326 */   public static final Stroke DEFAULT_GRIDLINE_STROKE = new BasicStroke(0.5F, 0, 2, 0.0F, new float[] { 2.0F, 2.0F }, 0.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  331 */   public static final Paint DEFAULT_GRIDLINE_PAINT = Color.lightGray;
/*      */ 
/*      */   
/*      */   public static final boolean DEFAULT_CROSSHAIR_VISIBLE = false;
/*      */ 
/*      */   
/*  337 */   public static final Stroke DEFAULT_CROSSHAIR_STROKE = DEFAULT_GRIDLINE_STROKE;
/*      */ 
/*      */ 
/*      */   
/*  341 */   public static final Paint DEFAULT_CROSSHAIR_PAINT = Color.blue;
/*      */ 
/*      */ 
/*      */   
/*  345 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PlotOrientation orientation;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RectangleInsets axisOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Integer, ValueAxis> domainAxes;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Integer, AxisLocation> domainAxisLocations;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Integer, ValueAxis> rangeAxes;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Integer, AxisLocation> rangeAxisLocations;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Integer, XYDataset> datasets;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Integer, XYItemRenderer> renderers;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Integer, List<Integer>> datasetToDomainAxesMap;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Integer, List<Integer>> datasetToRangeAxesMap;
/*      */ 
/*      */ 
/*      */   
/*  393 */   private transient Point2D quadrantOrigin = new Point2D.Double(0.0D, 0.0D);
/*      */ 
/*      */   
/*  396 */   private transient Paint[] quadrantPaint = new Paint[] { null, null, null, null };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean domainGridlinesVisible;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke domainGridlineStroke;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint domainGridlinePaint;
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
/*      */   private boolean domainMinorGridlinesVisible;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke domainMinorGridlineStroke;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint domainMinorGridlinePaint;
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
/*      */   private boolean domainZeroBaselineVisible;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke domainZeroBaselineStroke;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint domainZeroBaselinePaint;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean rangeZeroBaselineVisible;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke rangeZeroBaselineStroke;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint rangeZeroBaselinePaint;
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
/*      */   private Map foregroundDomainMarkers;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map backgroundDomainMarkers;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map foregroundRangeMarkers;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map backgroundRangeMarkers;
/*      */ 
/*      */ 
/*      */   
/*      */   private List<XYAnnotation> annotations;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint domainTickBandPaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint rangeTickBandPaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private AxisSpace fixedDomainAxisSpace;
/*      */ 
/*      */ 
/*      */   
/*      */   private AxisSpace fixedRangeAxisSpace;
/*      */ 
/*      */ 
/*      */   
/*  564 */   private DatasetRenderingOrder datasetRenderingOrder = DatasetRenderingOrder.REVERSE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  571 */   private SeriesRenderingOrder seriesRenderingOrder = SeriesRenderingOrder.REVERSE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int weight;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LegendItemCollection fixedLegendItems;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean domainPannable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean rangePannable;
/*      */ 
/*      */ 
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
/*      */   
/*      */   public XYPlot() {
/*  614 */     this((XYDataset)null, (ValueAxis)null, (ValueAxis)null, (XYItemRenderer)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYPlot(XYDataset dataset, ValueAxis domainAxis, ValueAxis rangeAxis, XYItemRenderer renderer) {
/*  631 */     this.orientation = PlotOrientation.VERTICAL;
/*  632 */     this.weight = 1;
/*  633 */     this.axisOffset = RectangleInsets.ZERO_INSETS;
/*      */ 
/*      */     
/*  636 */     this.domainAxes = new HashMap<Integer, ValueAxis>();
/*  637 */     this.domainAxisLocations = new HashMap<Integer, AxisLocation>();
/*  638 */     this.foregroundDomainMarkers = new HashMap<Object, Object>();
/*  639 */     this.backgroundDomainMarkers = new HashMap<Object, Object>();
/*      */     
/*  641 */     this.rangeAxes = new HashMap<Integer, ValueAxis>();
/*  642 */     this.rangeAxisLocations = new HashMap<Integer, AxisLocation>();
/*  643 */     this.foregroundRangeMarkers = new HashMap<Object, Object>();
/*  644 */     this.backgroundRangeMarkers = new HashMap<Object, Object>();
/*      */     
/*  646 */     this.datasets = new HashMap<Integer, XYDataset>();
/*  647 */     this.renderers = new HashMap<Integer, XYItemRenderer>();
/*      */     
/*  649 */     this.datasetToDomainAxesMap = new TreeMap<Integer, List<Integer>>();
/*  650 */     this.datasetToRangeAxesMap = new TreeMap<Integer, List<Integer>>();
/*      */     
/*  652 */     this.annotations = new ArrayList<XYAnnotation>();
/*      */     
/*  654 */     this.datasets.put(Integer.valueOf(0), dataset);
/*  655 */     if (dataset != null) {
/*  656 */       dataset.addChangeListener(this);
/*      */     }
/*      */     
/*  659 */     this.renderers.put(Integer.valueOf(0), renderer);
/*  660 */     if (renderer != null) {
/*  661 */       renderer.setPlot(this);
/*  662 */       renderer.addChangeListener(this);
/*      */     } 
/*      */     
/*  665 */     this.domainAxes.put(Integer.valueOf(0), domainAxis);
/*  666 */     mapDatasetToDomainAxis(0, 0);
/*  667 */     if (domainAxis != null) {
/*  668 */       domainAxis.setPlot(this);
/*  669 */       domainAxis.addChangeListener(this);
/*      */     } 
/*  671 */     this.domainAxisLocations.put(Integer.valueOf(0), AxisLocation.BOTTOM_OR_LEFT);
/*      */     
/*  673 */     this.rangeAxes.put(Integer.valueOf(0), rangeAxis);
/*  674 */     mapDatasetToRangeAxis(0, 0);
/*  675 */     if (rangeAxis != null) {
/*  676 */       rangeAxis.setPlot(this);
/*  677 */       rangeAxis.addChangeListener(this);
/*      */     } 
/*  679 */     this.rangeAxisLocations.put(Integer.valueOf(0), AxisLocation.BOTTOM_OR_LEFT);
/*      */     
/*  681 */     configureDomainAxes();
/*  682 */     configureRangeAxes();
/*      */     
/*  684 */     this.domainGridlinesVisible = true;
/*  685 */     this.domainGridlineStroke = DEFAULT_GRIDLINE_STROKE;
/*  686 */     this.domainGridlinePaint = DEFAULT_GRIDLINE_PAINT;
/*      */     
/*  688 */     this.domainMinorGridlinesVisible = false;
/*  689 */     this.domainMinorGridlineStroke = DEFAULT_GRIDLINE_STROKE;
/*  690 */     this.domainMinorGridlinePaint = Color.white;
/*      */     
/*  692 */     this.domainZeroBaselineVisible = false;
/*  693 */     this.domainZeroBaselinePaint = Color.black;
/*  694 */     this.domainZeroBaselineStroke = new BasicStroke(0.5F);
/*      */     
/*  696 */     this.rangeGridlinesVisible = true;
/*  697 */     this.rangeGridlineStroke = DEFAULT_GRIDLINE_STROKE;
/*  698 */     this.rangeGridlinePaint = DEFAULT_GRIDLINE_PAINT;
/*      */     
/*  700 */     this.rangeMinorGridlinesVisible = false;
/*  701 */     this.rangeMinorGridlineStroke = DEFAULT_GRIDLINE_STROKE;
/*  702 */     this.rangeMinorGridlinePaint = Color.white;
/*      */     
/*  704 */     this.rangeZeroBaselineVisible = false;
/*  705 */     this.rangeZeroBaselinePaint = Color.black;
/*  706 */     this.rangeZeroBaselineStroke = new BasicStroke(0.5F);
/*      */     
/*  708 */     this.domainCrosshairVisible = false;
/*  709 */     this.domainCrosshairValue = 0.0D;
/*  710 */     this.domainCrosshairStroke = DEFAULT_CROSSHAIR_STROKE;
/*  711 */     this.domainCrosshairPaint = DEFAULT_CROSSHAIR_PAINT;
/*      */     
/*  713 */     this.rangeCrosshairVisible = false;
/*  714 */     this.rangeCrosshairValue = 0.0D;
/*  715 */     this.rangeCrosshairStroke = DEFAULT_CROSSHAIR_STROKE;
/*  716 */     this.rangeCrosshairPaint = DEFAULT_CROSSHAIR_PAINT;
/*  717 */     this.shadowGenerator = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlotType() {
/*  727 */     return localizationResources.getString("XY_Plot");
/*      */   }
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
/*  739 */     return this.orientation;
/*      */   }
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
/*  751 */     ParamChecks.nullNotPermitted(orientation, "orientation");
/*  752 */     if (orientation != this.orientation) {
/*  753 */       this.orientation = orientation;
/*  754 */       fireChangeEvent();
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
/*      */   public RectangleInsets getAxisOffset() {
/*  766 */     return this.axisOffset;
/*      */   }
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
/*  778 */     ParamChecks.nullNotPermitted(offset, "offset");
/*  779 */     this.axisOffset = offset;
/*  780 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueAxis getDomainAxis() {
/*  794 */     return getDomainAxis(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueAxis getDomainAxis(int index) {
/*  808 */     ValueAxis result = this.domainAxes.get(Integer.valueOf(index));
/*  809 */     if (result == null) {
/*  810 */       Plot parent = getParent();
/*  811 */       if (parent instanceof XYPlot) {
/*  812 */         XYPlot xy = (XYPlot)parent;
/*  813 */         result = xy.getDomainAxis(index);
/*      */       } 
/*      */     } 
/*  816 */     return result;
/*      */   }
/*      */ 
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
/*  829 */     setDomainAxis(0, axis);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainAxis(int index, ValueAxis axis) {
/*  843 */     setDomainAxis(index, axis, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainAxis(int index, ValueAxis axis, boolean notify) {
/*  857 */     ValueAxis existing = getDomainAxis(index);
/*  858 */     if (existing != null) {
/*  859 */       existing.removeChangeListener(this);
/*      */     }
/*  861 */     if (axis != null) {
/*  862 */       axis.setPlot(this);
/*      */     }
/*  864 */     this.domainAxes.put(Integer.valueOf(index), axis);
/*  865 */     if (axis != null) {
/*  866 */       axis.configure();
/*  867 */       axis.addChangeListener(this);
/*      */     } 
/*  869 */     if (notify) {
/*  870 */       fireChangeEvent();
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
/*      */   public void setDomainAxes(ValueAxis[] axes) {
/*  883 */     for (int i = 0; i < axes.length; i++) {
/*  884 */       setDomainAxis(i, axes[i], false);
/*      */     }
/*  886 */     fireChangeEvent();
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
/*  897 */     return this.domainAxisLocations.get(Integer.valueOf(0));
/*      */   }
/*      */ 
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
/*  910 */     setDomainAxisLocation(0, location, true);
/*      */   }
/*      */ 
/*      */ 
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
/*  924 */     setDomainAxisLocation(0, location, notify);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleEdge getDomainAxisEdge() {
/*  937 */     return Plot.resolveDomainAxisLocation(getDomainAxisLocation(), this.orientation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDomainAxisCount() {
/*  949 */     return this.domainAxes.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearDomainAxes() {
/*  959 */     for (ValueAxis axis : this.domainAxes.values()) {
/*  960 */       if (axis != null) {
/*  961 */         axis.removeChangeListener(this);
/*      */       }
/*      */     } 
/*  964 */     this.domainAxes.clear();
/*  965 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void configureDomainAxes() {
/*  972 */     for (ValueAxis axis : this.domainAxes.values()) {
/*  973 */       if (axis != null) {
/*  974 */         axis.configure();
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
/*      */   public AxisLocation getDomainAxisLocation(int index) {
/*  991 */     AxisLocation result = this.domainAxisLocations.get(Integer.valueOf(index));
/*  992 */     if (result == null) {
/*  993 */       result = AxisLocation.getOpposite(getDomainAxisLocation());
/*      */     }
/*  995 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 1010 */     setDomainAxisLocation(index, location, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1029 */     if (index == 0 && location == null) {
/* 1030 */       throw new IllegalArgumentException("Null 'location' for index 0 not permitted.");
/*      */     }
/*      */     
/* 1033 */     this.domainAxisLocations.put(Integer.valueOf(index), location);
/* 1034 */     if (notify) {
/* 1035 */       fireChangeEvent();
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
/*      */   public RectangleEdge getDomainAxisEdge(int index) {
/* 1049 */     AxisLocation location = getDomainAxisLocation(index);
/* 1050 */     return Plot.resolveDomainAxisLocation(location, this.orientation);
/*      */   }
/*      */ 
/*      */ 
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
/* 1064 */     return getRangeAxis(0);
/*      */   }
/*      */ 
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
/* 1077 */     if (axis != null) {
/* 1078 */       axis.setPlot(this);
/*      */     }
/*      */     
/* 1081 */     ValueAxis existing = getRangeAxis();
/* 1082 */     if (existing != null) {
/* 1083 */       existing.removeChangeListener(this);
/*      */     }
/* 1085 */     this.rangeAxes.put(Integer.valueOf(0), axis);
/* 1086 */     if (axis != null) {
/* 1087 */       axis.configure();
/* 1088 */       axis.addChangeListener(this);
/*      */     } 
/* 1090 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisLocation getRangeAxisLocation() {
/* 1101 */     return this.rangeAxisLocations.get(Integer.valueOf(0));
/*      */   }
/*      */ 
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
/* 1114 */     setRangeAxisLocation(0, location, true);
/*      */   }
/*      */ 
/*      */ 
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
/* 1128 */     setRangeAxisLocation(0, location, notify);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleEdge getRangeAxisEdge() {
/* 1140 */     return Plot.resolveRangeAxisLocation(getRangeAxisLocation(), this.orientation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueAxis getRangeAxis(int index) {
/* 1155 */     ValueAxis result = this.rangeAxes.get(Integer.valueOf(index));
/* 1156 */     if (result == null) {
/* 1157 */       Plot parent = getParent();
/* 1158 */       if (parent instanceof XYPlot) {
/* 1159 */         XYPlot xy = (XYPlot)parent;
/* 1160 */         result = xy.getRangeAxis(index);
/*      */       } 
/*      */     } 
/* 1163 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeAxis(int index, ValueAxis axis) {
/* 1176 */     setRangeAxis(index, axis, true);
/*      */   }
/*      */ 
/*      */ 
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
/* 1190 */     ValueAxis existing = getRangeAxis(index);
/* 1191 */     if (existing != null) {
/* 1192 */       existing.removeChangeListener(this);
/*      */     }
/* 1194 */     if (axis != null) {
/* 1195 */       axis.setPlot(this);
/*      */     }
/* 1197 */     this.rangeAxes.put(Integer.valueOf(index), axis);
/* 1198 */     if (axis != null) {
/* 1199 */       axis.configure();
/* 1200 */       axis.addChangeListener(this);
/*      */     } 
/* 1202 */     if (notify) {
/* 1203 */       fireChangeEvent();
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
/* 1216 */     for (int i = 0; i < axes.length; i++) {
/* 1217 */       setRangeAxis(i, axes[i], false);
/*      */     }
/* 1219 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRangeAxisCount() {
/* 1230 */     return this.rangeAxes.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearRangeAxes() {
/* 1240 */     for (ValueAxis axis : this.rangeAxes.values()) {
/* 1241 */       if (axis != null) {
/* 1242 */         axis.removeChangeListener(this);
/*      */       }
/*      */     } 
/* 1245 */     this.rangeAxes.clear();
/* 1246 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void configureRangeAxes() {
/* 1255 */     for (ValueAxis axis : this.rangeAxes.values()) {
/* 1256 */       if (axis != null) {
/* 1257 */         axis.configure();
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
/*      */   public AxisLocation getRangeAxisLocation(int index) {
/* 1274 */     AxisLocation result = this.rangeAxisLocations.get(Integer.valueOf(index));
/* 1275 */     if (result == null) {
/* 1276 */       result = AxisLocation.getOpposite(getRangeAxisLocation());
/*      */     }
/* 1278 */     return result;
/*      */   }
/*      */ 
/*      */ 
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
/* 1292 */     setRangeAxisLocation(index, location, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1311 */     if (index == 0 && location == null) {
/* 1312 */       throw new IllegalArgumentException("Null 'location' for index 0 not permitted.");
/*      */     }
/*      */     
/* 1315 */     this.rangeAxisLocations.put(Integer.valueOf(index), location);
/* 1316 */     if (notify) {
/* 1317 */       fireChangeEvent();
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
/*      */   public RectangleEdge getRangeAxisEdge(int index) {
/* 1332 */     AxisLocation location = getRangeAxisLocation(index);
/* 1333 */     return Plot.resolveRangeAxisLocation(location, this.orientation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYDataset getDataset() {
/* 1345 */     return getDataset(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYDataset getDataset(int index) {
/* 1359 */     return this.datasets.get(Integer.valueOf(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataset(XYDataset dataset) {
/* 1372 */     setDataset(0, dataset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataset(int index, XYDataset dataset) {
/* 1385 */     XYDataset existing = getDataset(index);
/* 1386 */     if (existing != null) {
/* 1387 */       existing.removeChangeListener(this);
/*      */     }
/* 1389 */     this.datasets.put(Integer.valueOf(index), dataset);
/* 1390 */     if (dataset != null) {
/* 1391 */       dataset.addChangeListener(this);
/*      */     }
/*      */ 
/*      */     
/* 1395 */     DatasetChangeEvent event = new DatasetChangeEvent(this, (Dataset)dataset);
/* 1396 */     datasetChanged(event);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDatasetCount() {
/* 1405 */     return this.datasets.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(XYDataset dataset) {
/* 1417 */     for (Map.Entry<Integer, XYDataset> entry : this.datasets.entrySet()) {
/* 1418 */       if (dataset == entry.getValue()) {
/* 1419 */         return ((Integer)entry.getKey()).intValue();
/*      */       }
/*      */     } 
/* 1422 */     return -1;
/*      */   }
/*      */ 
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
/* 1435 */     List<Integer> axisIndices = new ArrayList(1);
/* 1436 */     axisIndices.add(new Integer(axisIndex));
/* 1437 */     mapDatasetToDomainAxes(index, axisIndices);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mapDatasetToDomainAxes(int index, List<Integer> axisIndices) {
/* 1451 */     ParamChecks.requireNonNegative(index, "index");
/* 1452 */     checkAxisIndices(axisIndices);
/* 1453 */     Integer key = new Integer(index);
/* 1454 */     this.datasetToDomainAxesMap.put(key, new ArrayList<Integer>(axisIndices));
/*      */     
/* 1456 */     datasetChanged(new DatasetChangeEvent(this, (Dataset)getDataset(index)));
/*      */   }
/*      */ 
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
/* 1469 */     List<Integer> axisIndices = new ArrayList(1);
/* 1470 */     axisIndices.add(new Integer(axisIndex));
/* 1471 */     mapDatasetToRangeAxes(index, axisIndices);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mapDatasetToRangeAxes(int index, List<Integer> axisIndices) {
/* 1485 */     ParamChecks.requireNonNegative(index, "index");
/* 1486 */     checkAxisIndices(axisIndices);
/* 1487 */     Integer key = new Integer(index);
/* 1488 */     this.datasetToRangeAxesMap.put(key, new ArrayList<Integer>(axisIndices));
/*      */     
/* 1490 */     datasetChanged(new DatasetChangeEvent(this, (Dataset)getDataset(index)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkAxisIndices(List<Integer> indices) {
/* 1504 */     if (indices == null) {
/*      */       return;
/*      */     }
/* 1507 */     int count = indices.size();
/* 1508 */     if (count == 0) {
/* 1509 */       throw new IllegalArgumentException("Empty list not permitted.");
/*      */     }
/* 1511 */     Set<Integer> set = new HashSet<Integer>();
/* 1512 */     for (Integer item : indices) {
/* 1513 */       if (set.contains(item)) {
/* 1514 */         throw new IllegalArgumentException("Indices must be unique.");
/*      */       }
/* 1516 */       set.add(item);
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
/*      */   public int getRendererCount() {
/* 1528 */     return this.renderers.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYItemRenderer getRenderer() {
/* 1539 */     return getRenderer(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYItemRenderer getRenderer(int index) {
/* 1552 */     return this.renderers.get(Integer.valueOf(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderer(XYItemRenderer renderer) {
/* 1565 */     setRenderer(0, renderer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderer(int index, XYItemRenderer renderer) {
/* 1580 */     setRenderer(index, renderer, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderer(int index, XYItemRenderer renderer, boolean notify) {
/* 1597 */     XYItemRenderer existing = getRenderer(index);
/* 1598 */     if (existing != null) {
/* 1599 */       existing.removeChangeListener(this);
/*      */     }
/* 1601 */     this.renderers.put(Integer.valueOf(index), renderer);
/* 1602 */     if (renderer != null) {
/* 1603 */       renderer.setPlot(this);
/* 1604 */       renderer.addChangeListener(this);
/*      */     } 
/* 1606 */     configureDomainAxes();
/* 1607 */     configureRangeAxes();
/* 1608 */     if (notify) {
/* 1609 */       fireChangeEvent();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderers(XYItemRenderer[] renderers) {
/* 1620 */     for (int i = 0; i < renderers.length; i++) {
/* 1621 */       setRenderer(i, renderers[i], false);
/*      */     }
/* 1623 */     fireChangeEvent();
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
/* 1634 */     return this.datasetRenderingOrder;
/*      */   }
/*      */ 
/*      */ 
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
/* 1648 */     ParamChecks.nullNotPermitted(order, "order");
/* 1649 */     this.datasetRenderingOrder = order;
/* 1650 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SeriesRenderingOrder getSeriesRenderingOrder() {
/* 1661 */     return this.seriesRenderingOrder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeriesRenderingOrder(SeriesRenderingOrder order) {
/* 1675 */     ParamChecks.nullNotPermitted(order, "order");
/* 1676 */     this.seriesRenderingOrder = order;
/* 1677 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getIndexOf(XYItemRenderer renderer) {
/* 1690 */     for (Map.Entry<Integer, XYItemRenderer> entry : this.renderers.entrySet()) {
/* 1691 */       if (entry.getValue() == renderer) {
/* 1692 */         return ((Integer)entry.getKey()).intValue();
/*      */       }
/*      */     } 
/* 1695 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYItemRenderer getRendererForDataset(XYDataset dataset) {
/* 1709 */     int datasetIndex = indexOf(dataset);
/* 1710 */     if (datasetIndex < 0) {
/* 1711 */       return null;
/*      */     }
/* 1713 */     XYItemRenderer result = this.renderers.get(Integer.valueOf(datasetIndex));
/* 1714 */     if (result == null) {
/* 1715 */       result = getRenderer();
/*      */     }
/* 1717 */     return result;
/*      */   }
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
/* 1729 */     return this.weight;
/*      */   }
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
/* 1741 */     this.weight = weight;
/* 1742 */     fireChangeEvent();
/*      */   }
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
/* 1754 */     return this.domainGridlinesVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 1769 */     if (this.domainGridlinesVisible != visible) {
/* 1770 */       this.domainGridlinesVisible = visible;
/* 1771 */       fireChangeEvent();
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
/*      */   public boolean isDomainMinorGridlinesVisible() {
/* 1786 */     return this.domainMinorGridlinesVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainMinorGridlinesVisible(boolean visible) {
/* 1803 */     if (this.domainMinorGridlinesVisible != visible) {
/* 1804 */       this.domainMinorGridlinesVisible = visible;
/* 1805 */       fireChangeEvent();
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
/* 1818 */     return this.domainGridlineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 1833 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 1834 */     this.domainGridlineStroke = stroke;
/* 1835 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getDomainMinorGridlineStroke() {
/* 1850 */     return this.domainMinorGridlineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainMinorGridlineStroke(Stroke stroke) {
/* 1867 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 1868 */     this.domainMinorGridlineStroke = stroke;
/* 1869 */     fireChangeEvent();
/*      */   }
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
/* 1881 */     return this.domainGridlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 1896 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 1897 */     this.domainGridlinePaint = paint;
/* 1898 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getDomainMinorGridlinePaint() {
/* 1912 */     return this.domainMinorGridlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainMinorGridlinePaint(Paint paint) {
/* 1929 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 1930 */     this.domainMinorGridlinePaint = paint;
/* 1931 */     fireChangeEvent();
/*      */   }
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
/* 1943 */     return this.rangeGridlinesVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 1958 */     if (this.rangeGridlinesVisible != visible) {
/* 1959 */       this.rangeGridlinesVisible = visible;
/* 1960 */       fireChangeEvent();
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
/* 1973 */     return this.rangeGridlineStroke;
/*      */   }
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
/* 1985 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 1986 */     this.rangeGridlineStroke = stroke;
/* 1987 */     fireChangeEvent();
/*      */   }
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
/* 1999 */     return this.rangeGridlinePaint;
/*      */   }
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
/* 2011 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 2012 */     this.rangeGridlinePaint = paint;
/* 2013 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
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
/* 2027 */     return this.rangeMinorGridlinesVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2044 */     if (this.rangeMinorGridlinesVisible != visible) {
/* 2045 */       this.rangeMinorGridlinesVisible = visible;
/* 2046 */       fireChangeEvent();
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
/* 2061 */     return this.rangeMinorGridlineStroke;
/*      */   }
/*      */ 
/*      */ 
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
/* 2075 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 2076 */     this.rangeMinorGridlineStroke = stroke;
/* 2077 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
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
/* 2091 */     return this.rangeMinorGridlinePaint;
/*      */   }
/*      */ 
/*      */ 
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
/* 2105 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 2106 */     this.rangeMinorGridlinePaint = paint;
/* 2107 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDomainZeroBaselineVisible() {
/* 2121 */     return this.domainZeroBaselineVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainZeroBaselineVisible(boolean visible) {
/* 2136 */     this.domainZeroBaselineVisible = visible;
/* 2137 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getDomainZeroBaselineStroke() {
/* 2150 */     return this.domainZeroBaselineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainZeroBaselineStroke(Stroke stroke) {
/* 2164 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 2165 */     this.domainZeroBaselineStroke = stroke;
/* 2166 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getDomainZeroBaselinePaint() {
/* 2180 */     return this.domainZeroBaselinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainZeroBaselinePaint(Paint paint) {
/* 2194 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 2195 */     this.domainZeroBaselinePaint = paint;
/* 2196 */     fireChangeEvent();
/*      */   }
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
/* 2208 */     return this.rangeZeroBaselineVisible;
/*      */   }
/*      */ 
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
/* 2221 */     this.rangeZeroBaselineVisible = visible;
/* 2222 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getRangeZeroBaselineStroke() {
/* 2233 */     return this.rangeZeroBaselineStroke;
/*      */   }
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
/* 2245 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 2246 */     this.rangeZeroBaselineStroke = stroke;
/* 2247 */     fireChangeEvent();
/*      */   }
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
/* 2259 */     return this.rangeZeroBaselinePaint;
/*      */   }
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
/* 2271 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 2272 */     this.rangeZeroBaselinePaint = paint;
/* 2273 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getDomainTickBandPaint() {
/* 2285 */     return this.domainTickBandPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainTickBandPaint(Paint paint) {
/* 2296 */     this.domainTickBandPaint = paint;
/* 2297 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getRangeTickBandPaint() {
/* 2309 */     return this.rangeTickBandPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeTickBandPaint(Paint paint) {
/* 2320 */     this.rangeTickBandPaint = paint;
/* 2321 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point2D getQuadrantOrigin() {
/* 2333 */     return this.quadrantOrigin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQuadrantOrigin(Point2D origin) {
/* 2345 */     ParamChecks.nullNotPermitted(origin, "origin");
/* 2346 */     this.quadrantOrigin = origin;
/* 2347 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getQuadrantPaint(int index) {
/* 2360 */     if (index < 0 || index > 3) {
/* 2361 */       throw new IllegalArgumentException("The index value (" + index + ") should be in the range 0 to 3.");
/*      */     }
/*      */     
/* 2364 */     return this.quadrantPaint[index];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQuadrantPaint(int index, Paint paint) {
/* 2377 */     if (index < 0 || index > 3) {
/* 2378 */       throw new IllegalArgumentException("The index value (" + index + ") should be in the range 0 to 3.");
/*      */     }
/*      */     
/* 2381 */     this.quadrantPaint[index] = paint;
/* 2382 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2399 */     addDomainMarker(marker, Layer.FOREGROUND);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDomainMarker(Marker marker, Layer layer) {
/* 2415 */     addDomainMarker(0, marker, layer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearDomainMarkers() {
/* 2425 */     if (this.backgroundDomainMarkers != null) {
/* 2426 */       Set<Integer> keys = this.backgroundDomainMarkers.keySet();
/* 2427 */       for (Integer key : keys) {
/* 2428 */         clearDomainMarkers(key.intValue());
/*      */       }
/* 2430 */       this.backgroundDomainMarkers.clear();
/*      */     } 
/* 2432 */     if (this.foregroundDomainMarkers != null) {
/* 2433 */       Set<Integer> keys = this.foregroundDomainMarkers.keySet();
/* 2434 */       for (Integer key : keys) {
/* 2435 */         clearDomainMarkers(key.intValue());
/*      */       }
/* 2437 */       this.foregroundDomainMarkers.clear();
/*      */     } 
/* 2439 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearDomainMarkers(int index) {
/* 2451 */     Integer key = new Integer(index);
/* 2452 */     if (this.backgroundDomainMarkers != null) {
/*      */       
/* 2454 */       Collection markers = (Collection)this.backgroundDomainMarkers.get(key);
/* 2455 */       if (markers != null) {
/* 2456 */         Iterator<Marker> iterator = markers.iterator();
/* 2457 */         while (iterator.hasNext()) {
/* 2458 */           Marker m = iterator.next();
/* 2459 */           m.removeChangeListener(this);
/*      */         } 
/* 2461 */         markers.clear();
/*      */       } 
/*      */     } 
/* 2464 */     if (this.foregroundRangeMarkers != null) {
/*      */       
/* 2466 */       Collection markers = (Collection)this.foregroundDomainMarkers.get(key);
/* 2467 */       if (markers != null) {
/* 2468 */         Iterator<Marker> iterator = markers.iterator();
/* 2469 */         while (iterator.hasNext()) {
/* 2470 */           Marker m = iterator.next();
/* 2471 */           m.removeChangeListener(this);
/*      */         } 
/* 2473 */         markers.clear();
/*      */       } 
/*      */     } 
/* 2476 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDomainMarker(int index, Marker marker, Layer layer) {
/* 2495 */     addDomainMarker(index, marker, layer, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDomainMarker(int index, Marker marker, Layer layer, boolean notify) {
/* 2515 */     ParamChecks.nullNotPermitted(marker, "marker");
/* 2516 */     ParamChecks.nullNotPermitted(layer, "layer");
/*      */     
/* 2518 */     if (layer == Layer.FOREGROUND) {
/* 2519 */       Collection<Marker> markers = (Collection)this.foregroundDomainMarkers.get(new Integer(index));
/*      */       
/* 2521 */       if (markers == null) {
/* 2522 */         markers = new ArrayList();
/* 2523 */         this.foregroundDomainMarkers.put(new Integer(index), markers);
/*      */       } 
/* 2525 */       markers.add(marker);
/*      */     }
/* 2527 */     else if (layer == Layer.BACKGROUND) {
/* 2528 */       Collection<Marker> markers = (Collection)this.backgroundDomainMarkers.get(new Integer(index));
/*      */       
/* 2530 */       if (markers == null) {
/* 2531 */         markers = new ArrayList();
/* 2532 */         this.backgroundDomainMarkers.put(new Integer(index), markers);
/*      */       } 
/* 2534 */       markers.add(marker);
/*      */     } 
/* 2536 */     marker.addChangeListener(this);
/* 2537 */     if (notify) {
/* 2538 */       fireChangeEvent();
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
/*      */   public boolean removeDomainMarker(Marker marker) {
/* 2554 */     return removeDomainMarker(marker, Layer.FOREGROUND);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2570 */     return removeDomainMarker(0, marker, layer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2587 */     return removeDomainMarker(index, marker, layer, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2607 */     if (layer == Layer.FOREGROUND) {
/* 2608 */       markers = (ArrayList)this.foregroundDomainMarkers.get(new Integer(index));
/*      */     }
/*      */     else {
/*      */       
/* 2612 */       markers = (ArrayList)this.backgroundDomainMarkers.get(new Integer(index));
/*      */     } 
/*      */     
/* 2615 */     if (markers == null) {
/* 2616 */       return false;
/*      */     }
/* 2618 */     boolean removed = markers.remove(marker);
/* 2619 */     if (removed && notify) {
/* 2620 */       fireChangeEvent();
/*      */     }
/* 2622 */     return removed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 2637 */     addRangeMarker(marker, Layer.FOREGROUND);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2653 */     addRangeMarker(0, marker, layer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearRangeMarkers() {
/* 2663 */     if (this.backgroundRangeMarkers != null) {
/* 2664 */       Set<Integer> keys = this.backgroundRangeMarkers.keySet();
/* 2665 */       for (Integer key : keys) {
/* 2666 */         clearRangeMarkers(key.intValue());
/*      */       }
/* 2668 */       this.backgroundRangeMarkers.clear();
/*      */     } 
/* 2670 */     if (this.foregroundRangeMarkers != null) {
/* 2671 */       Set<Integer> keys = this.foregroundRangeMarkers.keySet();
/* 2672 */       for (Integer key : keys) {
/* 2673 */         clearRangeMarkers(key.intValue());
/*      */       }
/* 2675 */       this.foregroundRangeMarkers.clear();
/*      */     } 
/* 2677 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2695 */     addRangeMarker(index, marker, layer, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2715 */     if (layer == Layer.FOREGROUND) {
/* 2716 */       Collection<Marker> markers = (Collection)this.foregroundRangeMarkers.get(new Integer(index));
/*      */       
/* 2718 */       if (markers == null) {
/* 2719 */         markers = new ArrayList();
/* 2720 */         this.foregroundRangeMarkers.put(new Integer(index), markers);
/*      */       } 
/* 2722 */       markers.add(marker);
/*      */     }
/* 2724 */     else if (layer == Layer.BACKGROUND) {
/* 2725 */       Collection<Marker> markers = (Collection)this.backgroundRangeMarkers.get(new Integer(index));
/*      */       
/* 2727 */       if (markers == null) {
/* 2728 */         markers = new ArrayList();
/* 2729 */         this.backgroundRangeMarkers.put(new Integer(index), markers);
/*      */       } 
/* 2731 */       markers.add(marker);
/*      */     } 
/* 2733 */     marker.addChangeListener(this);
/* 2734 */     if (notify) {
/* 2735 */       fireChangeEvent();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearRangeMarkers(int index) {
/* 2746 */     Integer key = new Integer(index);
/* 2747 */     if (this.backgroundRangeMarkers != null) {
/*      */       
/* 2749 */       Collection markers = (Collection)this.backgroundRangeMarkers.get(key);
/* 2750 */       if (markers != null) {
/* 2751 */         Iterator<Marker> iterator = markers.iterator();
/* 2752 */         while (iterator.hasNext()) {
/* 2753 */           Marker m = iterator.next();
/* 2754 */           m.removeChangeListener(this);
/*      */         } 
/* 2756 */         markers.clear();
/*      */       } 
/*      */     } 
/* 2759 */     if (this.foregroundRangeMarkers != null) {
/*      */       
/* 2761 */       Collection markers = (Collection)this.foregroundRangeMarkers.get(key);
/* 2762 */       if (markers != null) {
/* 2763 */         Iterator<Marker> iterator = markers.iterator();
/* 2764 */         while (iterator.hasNext()) {
/* 2765 */           Marker m = iterator.next();
/* 2766 */           m.removeChangeListener(this);
/*      */         } 
/* 2768 */         markers.clear();
/*      */       } 
/*      */     } 
/* 2771 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 2786 */     return removeRangeMarker(marker, Layer.FOREGROUND);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2802 */     return removeRangeMarker(0, marker, layer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2819 */     return removeRangeMarker(index, marker, layer, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */     List markers;
/* 2838 */     ParamChecks.nullNotPermitted(marker, "marker");
/* 2839 */     ParamChecks.nullNotPermitted(layer, "layer");
/*      */     
/* 2841 */     if (layer == Layer.FOREGROUND) {
/* 2842 */       markers = (List)this.foregroundRangeMarkers.get(new Integer(index));
/*      */     }
/*      */     else {
/*      */       
/* 2846 */       markers = (List)this.backgroundRangeMarkers.get(new Integer(index));
/*      */     } 
/*      */     
/* 2849 */     if (markers == null) {
/* 2850 */       return false;
/*      */     }
/* 2852 */     boolean removed = markers.remove(marker);
/* 2853 */     if (removed && notify) {
/* 2854 */       fireChangeEvent();
/*      */     }
/* 2856 */     return removed;
/*      */   }
/*      */ 
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
/* 2869 */     addAnnotation(annotation, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAnnotation(XYAnnotation annotation, boolean notify) {
/* 2882 */     ParamChecks.nullNotPermitted(annotation, "annotation");
/* 2883 */     this.annotations.add(annotation);
/* 2884 */     annotation.addChangeListener(this);
/* 2885 */     if (notify) {
/* 2886 */       fireChangeEvent();
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
/*      */   public boolean removeAnnotation(XYAnnotation annotation) {
/* 2902 */     return removeAnnotation(annotation, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeAnnotation(XYAnnotation annotation, boolean notify) {
/* 2917 */     ParamChecks.nullNotPermitted(annotation, "annotation");
/* 2918 */     boolean removed = this.annotations.remove(annotation);
/* 2919 */     annotation.removeChangeListener(this);
/* 2920 */     if (removed && notify) {
/* 2921 */       fireChangeEvent();
/*      */     }
/* 2923 */     return removed;
/*      */   }
/*      */ 
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
/* 2936 */     return new ArrayList<XYAnnotation>(this.annotations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearAnnotations() {
/* 2946 */     for (XYAnnotation annotation : this.annotations) {
/* 2947 */       annotation.removeChangeListener(this);
/*      */     }
/* 2949 */     this.annotations.clear();
/* 2950 */     fireChangeEvent();
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
/* 2961 */     return this.shadowGenerator;
/*      */   }
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
/* 2973 */     this.shadowGenerator = generator;
/* 2974 */     fireChangeEvent();
/*      */   }
/*      */ 
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
/* 2987 */     AxisSpace space = new AxisSpace();
/* 2988 */     space = calculateRangeAxisSpace(g2, plotArea, space);
/* 2989 */     Rectangle2D revPlotArea = space.shrink(plotArea, null);
/* 2990 */     space = calculateDomainAxisSpace(g2, revPlotArea, space);
/* 2991 */     return space;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 3006 */     if (space == null) {
/* 3007 */       space = new AxisSpace();
/*      */     }
/*      */ 
/*      */     
/* 3011 */     if (this.fixedDomainAxisSpace != null) {
/* 3012 */       if (this.orientation == PlotOrientation.HORIZONTAL) {
/* 3013 */         space.ensureAtLeast(this.fixedDomainAxisSpace.getLeft(), RectangleEdge.LEFT);
/*      */         
/* 3015 */         space.ensureAtLeast(this.fixedDomainAxisSpace.getRight(), RectangleEdge.RIGHT);
/*      */       
/*      */       }
/* 3018 */       else if (this.orientation == PlotOrientation.VERTICAL) {
/* 3019 */         space.ensureAtLeast(this.fixedDomainAxisSpace.getTop(), RectangleEdge.TOP);
/*      */         
/* 3021 */         space.ensureAtLeast(this.fixedDomainAxisSpace.getBottom(), RectangleEdge.BOTTOM);
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 3027 */       for (ValueAxis axis : this.domainAxes.values()) {
/* 3028 */         if (axis != null) {
/* 3029 */           RectangleEdge edge = getDomainAxisEdge(
/* 3030 */               findDomainAxisIndex(axis));
/* 3031 */           space = axis.reserveSpace(g2, this, plotArea, edge, space);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3036 */     return space;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 3052 */     if (space == null) {
/* 3053 */       space = new AxisSpace();
/*      */     }
/*      */ 
/*      */     
/* 3057 */     if (this.fixedRangeAxisSpace != null) {
/* 3058 */       if (this.orientation == PlotOrientation.HORIZONTAL) {
/* 3059 */         space.ensureAtLeast(this.fixedRangeAxisSpace.getTop(), RectangleEdge.TOP);
/*      */         
/* 3061 */         space.ensureAtLeast(this.fixedRangeAxisSpace.getBottom(), RectangleEdge.BOTTOM);
/*      */       
/*      */       }
/* 3064 */       else if (this.orientation == PlotOrientation.VERTICAL) {
/* 3065 */         space.ensureAtLeast(this.fixedRangeAxisSpace.getLeft(), RectangleEdge.LEFT);
/*      */         
/* 3067 */         space.ensureAtLeast(this.fixedRangeAxisSpace.getRight(), RectangleEdge.RIGHT);
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 3073 */       for (ValueAxis axis : this.rangeAxes.values()) {
/* 3074 */         if (axis != null) {
/* 3075 */           RectangleEdge edge = getRangeAxisEdge(
/* 3076 */               findRangeAxisIndex(axis));
/* 3077 */           space = axis.reserveSpace(g2, this, plotArea, edge, space);
/*      */         } 
/*      */       } 
/*      */     } 
/* 3081 */     return space;
/*      */   }
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
/* 3093 */     int x0 = (int)Math.ceil(rect.getMinX());
/* 3094 */     int y0 = (int)Math.ceil(rect.getMinY());
/* 3095 */     int x1 = (int)Math.floor(rect.getMaxX());
/* 3096 */     int y1 = (int)Math.floor(rect.getMaxY());
/* 3097 */     return new Rectangle(x0, y0, x1 - x0, y1 - y0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 3117 */     boolean b1 = (area.getWidth() <= 10.0D);
/* 3118 */     boolean b2 = (area.getHeight() <= 10.0D);
/* 3119 */     if (b1 || b2) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3124 */     if (info != null) {
/* 3125 */       info.setPlotArea(area);
/*      */     }
/*      */ 
/*      */     
/* 3129 */     RectangleInsets insets = getInsets();
/* 3130 */     insets.trim(area);
/*      */     
/* 3132 */     AxisSpace space = calculateAxisSpace(g2, area);
/* 3133 */     Rectangle2D dataArea = space.shrink(area, null);
/* 3134 */     this.axisOffset.trim(dataArea);
/*      */     
/* 3136 */     dataArea = integerise(dataArea);
/* 3137 */     if (dataArea.isEmpty()) {
/*      */       return;
/*      */     }
/* 3140 */     createAndAddEntity((Rectangle2D)dataArea.clone(), info, null, null);
/* 3141 */     if (info != null) {
/* 3142 */       info.setDataArea(dataArea);
/*      */     }
/*      */ 
/*      */     
/* 3146 */     drawBackground(g2, dataArea);
/* 3147 */     Map<Axis, AxisState> axisStateMap = drawAxes(g2, area, dataArea, info);
/*      */     
/* 3149 */     PlotOrientation orient = getOrientation();
/*      */ 
/*      */ 
/*      */     
/* 3153 */     if (anchor != null && !dataArea.contains(anchor)) {
/* 3154 */       anchor = null;
/*      */     }
/* 3156 */     CrosshairState crosshairState = new CrosshairState();
/* 3157 */     crosshairState.setCrosshairDistance(Double.POSITIVE_INFINITY);
/* 3158 */     crosshairState.setAnchor(anchor);
/*      */     
/* 3160 */     crosshairState.setAnchorX(Double.NaN);
/* 3161 */     crosshairState.setAnchorY(Double.NaN);
/* 3162 */     if (anchor != null) {
/* 3163 */       ValueAxis domainAxis = getDomainAxis();
/* 3164 */       if (domainAxis != null) {
/*      */         double x;
/* 3166 */         if (orient == PlotOrientation.VERTICAL) {
/* 3167 */           x = domainAxis.java2DToValue(anchor.getX(), dataArea, 
/* 3168 */               getDomainAxisEdge());
/*      */         } else {
/*      */           
/* 3171 */           x = domainAxis.java2DToValue(anchor.getY(), dataArea, 
/* 3172 */               getDomainAxisEdge());
/*      */         } 
/* 3174 */         crosshairState.setAnchorX(x);
/*      */       } 
/* 3176 */       ValueAxis rangeAxis = getRangeAxis();
/* 3177 */       if (rangeAxis != null) {
/*      */         double y;
/* 3179 */         if (orient == PlotOrientation.VERTICAL) {
/* 3180 */           y = rangeAxis.java2DToValue(anchor.getY(), dataArea, 
/* 3181 */               getRangeAxisEdge());
/*      */         } else {
/*      */           
/* 3184 */           y = rangeAxis.java2DToValue(anchor.getX(), dataArea, 
/* 3185 */               getRangeAxisEdge());
/*      */         } 
/* 3187 */         crosshairState.setAnchorY(y);
/*      */       } 
/*      */     } 
/* 3190 */     crosshairState.setCrosshairX(getDomainCrosshairValue());
/* 3191 */     crosshairState.setCrosshairY(getRangeCrosshairValue());
/* 3192 */     Shape originalClip = g2.getClip();
/* 3193 */     Composite originalComposite = g2.getComposite();
/*      */     
/* 3195 */     g2.clip(dataArea);
/* 3196 */     g2.setComposite(AlphaComposite.getInstance(3, 
/* 3197 */           getForegroundAlpha()));
/*      */     
/* 3199 */     AxisState domainAxisState = axisStateMap.get(
/* 3200 */         getDomainAxis());
/* 3201 */     if (domainAxisState == null && 
/* 3202 */       parentState != null)
/*      */     {
/* 3204 */       domainAxisState = (AxisState)parentState.getSharedAxisStates().get(getDomainAxis());
/*      */     }
/*      */ 
/*      */     
/* 3208 */     AxisState rangeAxisState = axisStateMap.get(getRangeAxis());
/* 3209 */     if (rangeAxisState == null && 
/* 3210 */       parentState != null)
/*      */     {
/* 3212 */       rangeAxisState = (AxisState)parentState.getSharedAxisStates().get(getRangeAxis());
/*      */     }
/*      */     
/* 3215 */     if (domainAxisState != null) {
/* 3216 */       drawDomainTickBands(g2, dataArea, domainAxisState.getTicks());
/*      */     }
/* 3218 */     if (rangeAxisState != null) {
/* 3219 */       drawRangeTickBands(g2, dataArea, rangeAxisState.getTicks());
/*      */     }
/* 3221 */     if (domainAxisState != null) {
/* 3222 */       drawDomainGridlines(g2, dataArea, domainAxisState.getTicks());
/* 3223 */       drawZeroDomainBaseline(g2, dataArea);
/*      */     } 
/* 3225 */     if (rangeAxisState != null) {
/* 3226 */       drawRangeGridlines(g2, dataArea, rangeAxisState.getTicks());
/* 3227 */       drawZeroRangeBaseline(g2, dataArea);
/*      */     } 
/*      */     
/* 3230 */     Graphics2D savedG2 = g2;
/* 3231 */     BufferedImage dataImage = null;
/* 3232 */     boolean suppressShadow = Boolean.TRUE.equals(g2.getRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION));
/*      */     
/* 3234 */     if (this.shadowGenerator != null && !suppressShadow) {
/*      */       
/* 3236 */       dataImage = new BufferedImage((int)dataArea.getWidth(), (int)dataArea.getHeight(), 2);
/* 3237 */       g2 = dataImage.createGraphics();
/* 3238 */       g2.translate(-dataArea.getX(), -dataArea.getY());
/* 3239 */       g2.setRenderingHints(savedG2.getRenderingHints());
/*      */     } 
/*      */ 
/*      */     
/* 3243 */     for (XYDataset dataset : this.datasets.values()) {
/* 3244 */       int i = indexOf(dataset);
/* 3245 */       drawDomainMarkers(g2, dataArea, i, Layer.BACKGROUND);
/*      */     } 
/* 3247 */     for (XYDataset dataset : this.datasets.values()) {
/* 3248 */       int i = indexOf(dataset);
/* 3249 */       drawRangeMarkers(g2, dataArea, i, Layer.BACKGROUND);
/*      */     } 
/*      */ 
/*      */     
/* 3253 */     boolean foundData = false;
/* 3254 */     DatasetRenderingOrder order = getDatasetRenderingOrder();
/* 3255 */     List<Integer> rendererIndices = getRendererIndices(order);
/* 3256 */     List<Integer> datasetIndices = getDatasetIndices(order);
/*      */     Iterator<Integer> iterator1;
/* 3258 */     for (iterator1 = rendererIndices.iterator(); iterator1.hasNext(); ) { int i = ((Integer)iterator1.next()).intValue();
/* 3259 */       XYItemRenderer renderer = getRenderer(i);
/* 3260 */       if (renderer != null) {
/* 3261 */         ValueAxis domainAxis = getDomainAxisForDataset(i);
/* 3262 */         ValueAxis rangeAxis = getRangeAxisForDataset(i);
/* 3263 */         renderer.drawAnnotations(g2, dataArea, domainAxis, rangeAxis, Layer.BACKGROUND, info);
/*      */       }  }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3269 */     for (iterator1 = datasetIndices.iterator(); iterator1.hasNext(); ) { int i = ((Integer)iterator1.next()).intValue();
/* 3270 */       XYDataset dataset = getDataset(i);
/* 3271 */       foundData = (render(g2, dataArea, i, info, crosshairState) || foundData); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3276 */     for (iterator1 = rendererIndices.iterator(); iterator1.hasNext(); ) { int i = ((Integer)iterator1.next()).intValue();
/* 3277 */       XYItemRenderer renderer = getRenderer(i);
/* 3278 */       if (renderer != null) {
/* 3279 */         ValueAxis domainAxis = getDomainAxisForDataset(i);
/* 3280 */         ValueAxis rangeAxis = getRangeAxisForDataset(i);
/* 3281 */         renderer.drawAnnotations(g2, dataArea, domainAxis, rangeAxis, Layer.FOREGROUND, info);
/*      */       }  }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3287 */     int datasetIndex = crosshairState.getDatasetIndex();
/* 3288 */     ValueAxis xAxis = getDomainAxisForDataset(datasetIndex);
/* 3289 */     RectangleEdge xAxisEdge = getDomainAxisEdge(getDomainAxisIndex(xAxis));
/* 3290 */     if (!this.domainCrosshairLockedOnData && anchor != null) {
/*      */       double xx;
/* 3292 */       if (orient == PlotOrientation.VERTICAL) {
/* 3293 */         xx = xAxis.java2DToValue(anchor.getX(), dataArea, xAxisEdge);
/*      */       } else {
/*      */         
/* 3296 */         xx = xAxis.java2DToValue(anchor.getY(), dataArea, xAxisEdge);
/*      */       } 
/* 3298 */       crosshairState.setCrosshairX(xx);
/*      */     } 
/* 3300 */     setDomainCrosshairValue(crosshairState.getCrosshairX(), false);
/* 3301 */     if (isDomainCrosshairVisible()) {
/* 3302 */       double x = getDomainCrosshairValue();
/* 3303 */       Paint paint = getDomainCrosshairPaint();
/* 3304 */       Stroke stroke = getDomainCrosshairStroke();
/* 3305 */       drawDomainCrosshair(g2, dataArea, orient, x, xAxis, stroke, paint);
/*      */     } 
/*      */ 
/*      */     
/* 3309 */     ValueAxis yAxis = getRangeAxisForDataset(datasetIndex);
/* 3310 */     RectangleEdge yAxisEdge = getRangeAxisEdge(getRangeAxisIndex(yAxis));
/* 3311 */     if (!this.rangeCrosshairLockedOnData && anchor != null) {
/*      */       double yy;
/* 3313 */       if (orient == PlotOrientation.VERTICAL) {
/* 3314 */         yy = yAxis.java2DToValue(anchor.getY(), dataArea, yAxisEdge);
/*      */       } else {
/* 3316 */         yy = yAxis.java2DToValue(anchor.getX(), dataArea, yAxisEdge);
/*      */       } 
/* 3318 */       crosshairState.setCrosshairY(yy);
/*      */     } 
/* 3320 */     setRangeCrosshairValue(crosshairState.getCrosshairY(), false);
/* 3321 */     if (isRangeCrosshairVisible()) {
/* 3322 */       double y = getRangeCrosshairValue();
/* 3323 */       Paint paint = getRangeCrosshairPaint();
/* 3324 */       Stroke stroke = getRangeCrosshairStroke();
/* 3325 */       drawRangeCrosshair(g2, dataArea, orient, y, yAxis, stroke, paint);
/*      */     } 
/*      */     
/* 3328 */     if (!foundData) {
/* 3329 */       drawNoDataMessage(g2, dataArea);
/*      */     }
/*      */     Iterator<Integer> iterator2;
/* 3332 */     for (iterator2 = rendererIndices.iterator(); iterator2.hasNext(); ) { int i = ((Integer)iterator2.next()).intValue();
/* 3333 */       drawDomainMarkers(g2, dataArea, i, Layer.FOREGROUND); }
/*      */     
/* 3335 */     for (iterator2 = rendererIndices.iterator(); iterator2.hasNext(); ) { int i = ((Integer)iterator2.next()).intValue();
/* 3336 */       drawRangeMarkers(g2, dataArea, i, Layer.FOREGROUND); }
/*      */ 
/*      */     
/* 3339 */     drawAnnotations(g2, dataArea, info);
/* 3340 */     if (this.shadowGenerator != null && !suppressShadow) {
/*      */       
/* 3342 */       BufferedImage shadowImage = this.shadowGenerator.createDropShadow(dataImage);
/* 3343 */       g2 = savedG2;
/* 3344 */       g2.drawImage(shadowImage, (int)dataArea.getX() + this.shadowGenerator
/* 3345 */           .calculateOffsetX(), 
/* 3346 */           (int)dataArea.getY() + this.shadowGenerator
/* 3347 */           .calculateOffsetY(), (ImageObserver)null);
/* 3348 */       g2.drawImage(dataImage, (int)dataArea.getX(), 
/* 3349 */           (int)dataArea.getY(), (ImageObserver)null);
/*      */     } 
/* 3351 */     g2.setClip(originalClip);
/* 3352 */     g2.setComposite(originalComposite);
/*      */     
/* 3354 */     drawOutline(g2, dataArea);
/*      */   }
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
/* 3366 */     List<Integer> result = new ArrayList<Integer>();
/* 3367 */     for (Map.Entry<Integer, XYDataset> entry : this.datasets.entrySet()) {
/* 3368 */       if (entry.getValue() != null) {
/* 3369 */         result.add(entry.getKey());
/*      */       }
/*      */     } 
/* 3372 */     Collections.sort(result);
/* 3373 */     if (order == DatasetRenderingOrder.REVERSE) {
/* 3374 */       Collections.reverse(result);
/*      */     }
/* 3376 */     return result;
/*      */   }
/*      */   
/*      */   private List<Integer> getRendererIndices(DatasetRenderingOrder order) {
/* 3380 */     List<Integer> result = new ArrayList<Integer>();
/* 3381 */     for (Map.Entry<Integer, XYItemRenderer> entry : this.renderers.entrySet()) {
/* 3382 */       if (entry.getValue() != null) {
/* 3383 */         result.add(entry.getKey());
/*      */       }
/*      */     } 
/* 3386 */     Collections.sort(result);
/* 3387 */     if (order == DatasetRenderingOrder.REVERSE) {
/* 3388 */       Collections.reverse(result);
/*      */     }
/* 3390 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawBackground(Graphics2D g2, Rectangle2D area) {
/* 3401 */     fillBackground(g2, area, this.orientation);
/* 3402 */     drawQuadrants(g2, area);
/* 3403 */     drawBackgroundImage(g2, area);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawQuadrants(Graphics2D g2, Rectangle2D area) {
/* 3419 */     boolean somethingToDraw = false;
/*      */     
/* 3421 */     ValueAxis xAxis = getDomainAxis();
/* 3422 */     if (xAxis == null) {
/*      */       return;
/*      */     }
/* 3425 */     double x = xAxis.getRange().constrain(this.quadrantOrigin.getX());
/* 3426 */     double xx = xAxis.valueToJava2D(x, area, getDomainAxisEdge());
/*      */     
/* 3428 */     ValueAxis yAxis = getRangeAxis();
/* 3429 */     if (yAxis == null) {
/*      */       return;
/*      */     }
/* 3432 */     double y = yAxis.getRange().constrain(this.quadrantOrigin.getY());
/* 3433 */     double yy = yAxis.valueToJava2D(y, area, getRangeAxisEdge());
/*      */     
/* 3435 */     double xmin = xAxis.getLowerBound();
/* 3436 */     double xxmin = xAxis.valueToJava2D(xmin, area, getDomainAxisEdge());
/*      */     
/* 3438 */     double xmax = xAxis.getUpperBound();
/* 3439 */     double xxmax = xAxis.valueToJava2D(xmax, area, getDomainAxisEdge());
/*      */     
/* 3441 */     double ymin = yAxis.getLowerBound();
/* 3442 */     double yymin = yAxis.valueToJava2D(ymin, area, getRangeAxisEdge());
/*      */     
/* 3444 */     double ymax = yAxis.getUpperBound();
/* 3445 */     double yymax = yAxis.valueToJava2D(ymax, area, getRangeAxisEdge());
/*      */     
/* 3447 */     Rectangle2D[] r = { null, null, null, null };
/* 3448 */     if (this.quadrantPaint[0] != null && 
/* 3449 */       x > xmin && y < ymax) {
/* 3450 */       if (this.orientation == PlotOrientation.HORIZONTAL) {
/* 3451 */         r[0] = new Rectangle2D.Double(Math.min(yymax, yy), 
/* 3452 */             Math.min(xxmin, xx), Math.abs(yy - yymax), 
/* 3453 */             Math.abs(xx - xxmin));
/*      */       } else {
/*      */         
/* 3456 */         r[0] = new Rectangle2D.Double(Math.min(xxmin, xx), 
/* 3457 */             Math.min(yymax, yy), Math.abs(xx - xxmin), 
/* 3458 */             Math.abs(yy - yymax));
/*      */       } 
/* 3460 */       somethingToDraw = true;
/*      */     } 
/*      */     
/* 3463 */     if (this.quadrantPaint[1] != null && 
/* 3464 */       x < xmax && y < ymax) {
/* 3465 */       if (this.orientation == PlotOrientation.HORIZONTAL) {
/* 3466 */         r[1] = new Rectangle2D.Double(Math.min(yymax, yy), 
/* 3467 */             Math.min(xxmax, xx), Math.abs(yy - yymax), 
/* 3468 */             Math.abs(xx - xxmax));
/*      */       } else {
/*      */         
/* 3471 */         r[1] = new Rectangle2D.Double(Math.min(xx, xxmax), 
/* 3472 */             Math.min(yymax, yy), Math.abs(xx - xxmax), 
/* 3473 */             Math.abs(yy - yymax));
/*      */       } 
/* 3475 */       somethingToDraw = true;
/*      */     } 
/*      */     
/* 3478 */     if (this.quadrantPaint[2] != null && 
/* 3479 */       x > xmin && y > ymin) {
/* 3480 */       if (this.orientation == PlotOrientation.HORIZONTAL) {
/* 3481 */         r[2] = new Rectangle2D.Double(Math.min(yymin, yy), 
/* 3482 */             Math.min(xxmin, xx), Math.abs(yy - yymin), 
/* 3483 */             Math.abs(xx - xxmin));
/*      */       } else {
/*      */         
/* 3486 */         r[2] = new Rectangle2D.Double(Math.min(xxmin, xx), 
/* 3487 */             Math.min(yymin, yy), Math.abs(xx - xxmin), 
/* 3488 */             Math.abs(yy - yymin));
/*      */       } 
/* 3490 */       somethingToDraw = true;
/*      */     } 
/*      */     
/* 3493 */     if (this.quadrantPaint[3] != null && 
/* 3494 */       x < xmax && y > ymin) {
/* 3495 */       if (this.orientation == PlotOrientation.HORIZONTAL) {
/* 3496 */         r[3] = new Rectangle2D.Double(Math.min(yymin, yy), 
/* 3497 */             Math.min(xxmax, xx), Math.abs(yy - yymin), 
/* 3498 */             Math.abs(xx - xxmax));
/*      */       } else {
/*      */         
/* 3501 */         r[3] = new Rectangle2D.Double(Math.min(xx, xxmax), 
/* 3502 */             Math.min(yymin, yy), Math.abs(xx - xxmax), 
/* 3503 */             Math.abs(yy - yymin));
/*      */       } 
/* 3505 */       somethingToDraw = true;
/*      */     } 
/*      */     
/* 3508 */     if (somethingToDraw) {
/* 3509 */       Composite originalComposite = g2.getComposite();
/* 3510 */       g2.setComposite(AlphaComposite.getInstance(3, 
/* 3511 */             getBackgroundAlpha()));
/* 3512 */       for (int i = 0; i < 4; i++) {
/* 3513 */         if (this.quadrantPaint[i] != null && r[i] != null) {
/* 3514 */           g2.setPaint(this.quadrantPaint[i]);
/* 3515 */           g2.fill(r[i]);
/*      */         } 
/*      */       } 
/* 3518 */       g2.setComposite(originalComposite);
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
/*      */   public void drawDomainTickBands(Graphics2D g2, Rectangle2D dataArea, List ticks) {
/* 3533 */     Paint bandPaint = getDomainTickBandPaint();
/* 3534 */     if (bandPaint != null) {
/* 3535 */       boolean fillBand = false;
/* 3536 */       ValueAxis xAxis = getDomainAxis();
/* 3537 */       double previous = xAxis.getLowerBound();
/* 3538 */       Iterator<ValueTick> iterator = ticks.iterator();
/* 3539 */       while (iterator.hasNext()) {
/* 3540 */         ValueTick tick = iterator.next();
/* 3541 */         double current = tick.getValue();
/* 3542 */         if (fillBand) {
/* 3543 */           getRenderer().fillDomainGridBand(g2, this, xAxis, dataArea, previous, current);
/*      */         }
/*      */         
/* 3546 */         previous = current;
/* 3547 */         fillBand = !fillBand;
/*      */       } 
/* 3549 */       double end = xAxis.getUpperBound();
/* 3550 */       if (fillBand) {
/* 3551 */         getRenderer().fillDomainGridBand(g2, this, xAxis, dataArea, previous, end);
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
/*      */   public void drawRangeTickBands(Graphics2D g2, Rectangle2D dataArea, List ticks) {
/* 3568 */     Paint bandPaint = getRangeTickBandPaint();
/* 3569 */     if (bandPaint != null) {
/* 3570 */       boolean fillBand = false;
/* 3571 */       ValueAxis axis = getRangeAxis();
/* 3572 */       double previous = axis.getLowerBound();
/* 3573 */       Iterator<ValueTick> iterator = ticks.iterator();
/* 3574 */       while (iterator.hasNext()) {
/* 3575 */         ValueTick tick = iterator.next();
/* 3576 */         double current = tick.getValue();
/* 3577 */         if (fillBand) {
/* 3578 */           getRenderer().fillRangeGridBand(g2, this, axis, dataArea, previous, current);
/*      */         }
/*      */         
/* 3581 */         previous = current;
/* 3582 */         fillBand = !fillBand;
/*      */       } 
/* 3584 */       double end = axis.getUpperBound();
/* 3585 */       if (fillBand) {
/* 3586 */         getRenderer().fillRangeGridBand(g2, this, axis, dataArea, previous, end);
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
/*      */   protected Map<Axis, AxisState> drawAxes(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, PlotRenderingInfo plotState) {
/* 3606 */     AxisCollection axisCollection = new AxisCollection();
/*      */ 
/*      */     
/* 3609 */     for (ValueAxis axis : this.domainAxes.values()) {
/* 3610 */       if (axis != null) {
/* 3611 */         int axisIndex = findDomainAxisIndex(axis);
/* 3612 */         axisCollection.add((Axis)axis, getDomainAxisEdge(axisIndex));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3617 */     for (ValueAxis axis : this.rangeAxes.values()) {
/* 3618 */       if (axis != null) {
/* 3619 */         int axisIndex = findRangeAxisIndex(axis);
/* 3620 */         axisCollection.add((Axis)axis, getRangeAxisEdge(axisIndex));
/*      */       } 
/*      */     } 
/*      */     
/* 3624 */     Map<Object, Object> axisStateMap = new HashMap<Object, Object>();
/*      */ 
/*      */     
/* 3627 */     double cursor = dataArea.getMinY() - this.axisOffset.calculateTopOutset(dataArea
/* 3628 */         .getHeight());
/* 3629 */     Iterator<ValueAxis> iterator = axisCollection.getAxesAtTop().iterator();
/* 3630 */     while (iterator.hasNext()) {
/* 3631 */       ValueAxis axis = iterator.next();
/* 3632 */       AxisState info = axis.draw(g2, cursor, plotArea, dataArea, RectangleEdge.TOP, plotState);
/*      */       
/* 3634 */       cursor = info.getCursor();
/* 3635 */       axisStateMap.put(axis, info);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3640 */     cursor = dataArea.getMaxY() + this.axisOffset.calculateBottomOutset(dataArea.getHeight());
/* 3641 */     iterator = axisCollection.getAxesAtBottom().iterator();
/* 3642 */     while (iterator.hasNext()) {
/* 3643 */       ValueAxis axis = iterator.next();
/* 3644 */       AxisState info = axis.draw(g2, cursor, plotArea, dataArea, RectangleEdge.BOTTOM, plotState);
/*      */       
/* 3646 */       cursor = info.getCursor();
/* 3647 */       axisStateMap.put(axis, info);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3652 */     cursor = dataArea.getMinX() - this.axisOffset.calculateLeftOutset(dataArea.getWidth());
/* 3653 */     iterator = axisCollection.getAxesAtLeft().iterator();
/* 3654 */     while (iterator.hasNext()) {
/* 3655 */       ValueAxis axis = iterator.next();
/* 3656 */       AxisState info = axis.draw(g2, cursor, plotArea, dataArea, RectangleEdge.LEFT, plotState);
/*      */       
/* 3658 */       cursor = info.getCursor();
/* 3659 */       axisStateMap.put(axis, info);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3664 */     cursor = dataArea.getMaxX() + this.axisOffset.calculateRightOutset(dataArea.getWidth());
/* 3665 */     iterator = axisCollection.getAxesAtRight().iterator();
/* 3666 */     while (iterator.hasNext()) {
/* 3667 */       ValueAxis axis = iterator.next();
/* 3668 */       AxisState info = axis.draw(g2, cursor, plotArea, dataArea, RectangleEdge.RIGHT, plotState);
/*      */       
/* 3670 */       cursor = info.getCursor();
/* 3671 */       axisStateMap.put(axis, info);
/*      */     } 
/*      */     
/* 3674 */     return (Map)axisStateMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean render(Graphics2D g2, Rectangle2D dataArea, int index, PlotRenderingInfo info, CrosshairState crosshairState) {
/* 3696 */     boolean foundData = false;
/* 3697 */     XYDataset dataset = getDataset(index);
/* 3698 */     if (!DatasetUtilities.isEmptyOrNull(dataset)) {
/* 3699 */       foundData = true;
/* 3700 */       ValueAxis xAxis = getDomainAxisForDataset(index);
/* 3701 */       ValueAxis yAxis = getRangeAxisForDataset(index);
/* 3702 */       if (xAxis == null || yAxis == null) {
/* 3703 */         return foundData;
/*      */       }
/* 3705 */       XYItemRenderer renderer = getRenderer(index);
/* 3706 */       if (renderer == null) {
/* 3707 */         renderer = getRenderer();
/* 3708 */         if (renderer == null) {
/* 3709 */           return foundData;
/*      */         }
/*      */       } 
/*      */       
/* 3713 */       XYItemRendererState state = renderer.initialise(g2, dataArea, this, dataset, info);
/*      */       
/* 3715 */       int passCount = renderer.getPassCount();
/*      */       
/* 3717 */       SeriesRenderingOrder seriesOrder = getSeriesRenderingOrder();
/* 3718 */       if (seriesOrder == SeriesRenderingOrder.REVERSE) {
/*      */         
/* 3720 */         for (int pass = 0; pass < passCount; pass++) {
/* 3721 */           int seriesCount = dataset.getSeriesCount();
/* 3722 */           for (int series = seriesCount - 1; series >= 0; series--) {
/* 3723 */             int firstItem = 0;
/* 3724 */             int lastItem = dataset.getItemCount(series) - 1;
/* 3725 */             if (lastItem != -1)
/*      */             {
/*      */               
/* 3728 */               if (state.getProcessVisibleItemsOnly()) {
/* 3729 */                 int[] itemBounds = RendererUtilities.findLiveItems(dataset, series, xAxis
/* 3730 */                     .getLowerBound(), xAxis
/* 3731 */                     .getUpperBound());
/* 3732 */                 firstItem = Math.max(itemBounds[0] - 1, 0);
/* 3733 */                 lastItem = Math.min(itemBounds[1] + 1, lastItem);
/*      */               } 
/* 3735 */               state.startSeriesPass(dataset, series, firstItem, lastItem, pass, passCount);
/*      */               
/* 3737 */               for (int item = firstItem; item <= lastItem; item++) {
/* 3738 */                 renderer.drawItem(g2, state, dataArea, info, this, xAxis, yAxis, dataset, series, item, crosshairState, pass);
/*      */               }
/*      */ 
/*      */               
/* 3742 */               state.endSeriesPass(dataset, series, firstItem, lastItem, pass, passCount);
/*      */             }
/*      */           
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/* 3749 */         for (int pass = 0; pass < passCount; pass++) {
/* 3750 */           int seriesCount = dataset.getSeriesCount();
/* 3751 */           for (int series = 0; series < seriesCount; series++) {
/* 3752 */             int firstItem = 0;
/* 3753 */             int lastItem = dataset.getItemCount(series) - 1;
/* 3754 */             if (state.getProcessVisibleItemsOnly()) {
/* 3755 */               int[] itemBounds = RendererUtilities.findLiveItems(dataset, series, xAxis
/* 3756 */                   .getLowerBound(), xAxis
/* 3757 */                   .getUpperBound());
/* 3758 */               firstItem = Math.max(itemBounds[0] - 1, 0);
/* 3759 */               lastItem = Math.min(itemBounds[1] + 1, lastItem);
/*      */             } 
/* 3761 */             state.startSeriesPass(dataset, series, firstItem, lastItem, pass, passCount);
/*      */             
/* 3763 */             for (int item = firstItem; item <= lastItem; item++) {
/* 3764 */               renderer.drawItem(g2, state, dataArea, info, this, xAxis, yAxis, dataset, series, item, crosshairState, pass);
/*      */             }
/*      */ 
/*      */             
/* 3768 */             state.endSeriesPass(dataset, series, firstItem, lastItem, pass, passCount);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3774 */     return foundData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueAxis getDomainAxisForDataset(int index) {
/*      */     ValueAxis valueAxis;
/* 3785 */     ParamChecks.requireNonNegative(index, "index");
/*      */     
/* 3787 */     List<Integer> axisIndices = this.datasetToDomainAxesMap.get(new Integer(index));
/*      */     
/* 3789 */     if (axisIndices != null) {
/*      */       
/* 3791 */       Integer axisIndex = axisIndices.get(0);
/* 3792 */       valueAxis = getDomainAxis(axisIndex.intValue());
/*      */     } else {
/*      */       
/* 3795 */       valueAxis = getDomainAxis(0);
/*      */     } 
/* 3797 */     return valueAxis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueAxis getRangeAxisForDataset(int index) {
/*      */     ValueAxis valueAxis;
/* 3808 */     ParamChecks.requireNonNegative(index, "index");
/*      */     
/* 3810 */     List<Integer> axisIndices = this.datasetToRangeAxesMap.get(new Integer(index));
/*      */     
/* 3812 */     if (axisIndices != null) {
/*      */       
/* 3814 */       Integer axisIndex = axisIndices.get(0);
/* 3815 */       valueAxis = getRangeAxis(axisIndex.intValue());
/*      */     } else {
/*      */       
/* 3818 */       valueAxis = getRangeAxis(0);
/*      */     } 
/* 3820 */     return valueAxis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 3836 */     if (getRenderer() == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3841 */     if (isDomainGridlinesVisible() || isDomainMinorGridlinesVisible()) {
/* 3842 */       Stroke gridStroke = null;
/* 3843 */       Paint gridPaint = null;
/* 3844 */       Iterator<ValueTick> iterator = ticks.iterator();
/*      */       
/* 3846 */       while (iterator.hasNext()) {
/* 3847 */         boolean paintLine = false;
/* 3848 */         ValueTick tick = iterator.next();
/* 3849 */         if (tick.getTickType() == TickType.MINOR && 
/* 3850 */           isDomainMinorGridlinesVisible()) {
/* 3851 */           gridStroke = getDomainMinorGridlineStroke();
/* 3852 */           gridPaint = getDomainMinorGridlinePaint();
/* 3853 */           paintLine = true;
/* 3854 */         } else if (tick.getTickType() == TickType.MAJOR && 
/* 3855 */           isDomainGridlinesVisible()) {
/* 3856 */           gridStroke = getDomainGridlineStroke();
/* 3857 */           gridPaint = getDomainGridlinePaint();
/* 3858 */           paintLine = true;
/*      */         } 
/* 3860 */         XYItemRenderer r = getRenderer();
/* 3861 */         if (r instanceof AbstractXYItemRenderer && paintLine) {
/* 3862 */           ((AbstractXYItemRenderer)r).drawDomainLine(g2, this, 
/* 3863 */               getDomainAxis(), dataArea, tick.getValue(), gridPaint, gridStroke);
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawRangeGridlines(Graphics2D g2, Rectangle2D area, List ticks) {
/* 3884 */     if (getRenderer() == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3889 */     if (isRangeGridlinesVisible() || isRangeMinorGridlinesVisible()) {
/* 3890 */       Stroke gridStroke = null;
/* 3891 */       Paint gridPaint = null;
/* 3892 */       ValueAxis axis = getRangeAxis();
/* 3893 */       if (axis != null) {
/* 3894 */         Iterator<ValueTick> iterator = ticks.iterator();
/*      */         
/* 3896 */         while (iterator.hasNext()) {
/* 3897 */           boolean paintLine = false;
/* 3898 */           ValueTick tick = iterator.next();
/* 3899 */           if (tick.getTickType() == TickType.MINOR && 
/* 3900 */             isRangeMinorGridlinesVisible()) {
/* 3901 */             gridStroke = getRangeMinorGridlineStroke();
/* 3902 */             gridPaint = getRangeMinorGridlinePaint();
/* 3903 */             paintLine = true;
/* 3904 */           } else if (tick.getTickType() == TickType.MAJOR && 
/* 3905 */             isRangeGridlinesVisible()) {
/* 3906 */             gridStroke = getRangeGridlineStroke();
/* 3907 */             gridPaint = getRangeGridlinePaint();
/* 3908 */             paintLine = true;
/*      */           } 
/* 3910 */           if ((tick.getValue() != 0.0D || 
/* 3911 */             !isRangeZeroBaselineVisible()) && paintLine) {
/* 3912 */             getRenderer().drawRangeLine(g2, this, getRangeAxis(), area, tick
/* 3913 */                 .getValue(), gridPaint, gridStroke);
/*      */           }
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
/*      */   protected void drawZeroDomainBaseline(Graphics2D g2, Rectangle2D area) {
/* 3931 */     if (isDomainZeroBaselineVisible()) {
/* 3932 */       XYItemRenderer r = getRenderer();
/*      */ 
/*      */ 
/*      */       
/* 3936 */       if (r instanceof AbstractXYItemRenderer) {
/* 3937 */         AbstractXYItemRenderer renderer = (AbstractXYItemRenderer)r;
/* 3938 */         renderer.drawDomainLine(g2, this, getDomainAxis(), area, 0.0D, this.domainZeroBaselinePaint, this.domainZeroBaselineStroke);
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
/*      */   protected void drawZeroRangeBaseline(Graphics2D g2, Rectangle2D area) {
/* 3954 */     if (isRangeZeroBaselineVisible()) {
/* 3955 */       getRenderer().drawRangeLine(g2, this, getRangeAxis(), area, 0.0D, this.rangeZeroBaselinePaint, this.rangeZeroBaselineStroke);
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
/*      */   public void drawAnnotations(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info) {
/* 3970 */     Iterator<XYAnnotation> iterator = this.annotations.iterator();
/* 3971 */     while (iterator.hasNext()) {
/* 3972 */       XYAnnotation annotation = iterator.next();
/* 3973 */       ValueAxis xAxis = getDomainAxis();
/* 3974 */       ValueAxis yAxis = getRangeAxis();
/* 3975 */       annotation.draw(g2, this, dataArea, xAxis, yAxis, 0, info);
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
/*      */   protected void drawDomainMarkers(Graphics2D g2, Rectangle2D dataArea, int index, Layer layer) {
/* 3992 */     XYItemRenderer r = getRenderer(index);
/* 3993 */     if (r == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3998 */     if (index >= getDatasetCount()) {
/*      */       return;
/*      */     }
/* 4001 */     Collection markers = getDomainMarkers(index, layer);
/* 4002 */     ValueAxis axis = getDomainAxisForDataset(index);
/* 4003 */     if (markers != null && axis != null) {
/* 4004 */       Iterator<Marker> iterator = markers.iterator();
/* 4005 */       while (iterator.hasNext()) {
/* 4006 */         Marker marker = iterator.next();
/* 4007 */         r.drawDomainMarker(g2, this, axis, marker, dataArea);
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
/*      */   protected void drawRangeMarkers(Graphics2D g2, Rectangle2D dataArea, int index, Layer layer) {
/* 4025 */     XYItemRenderer r = getRenderer(index);
/* 4026 */     if (r == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 4031 */     if (index >= getDatasetCount()) {
/*      */       return;
/*      */     }
/* 4034 */     Collection markers = getRangeMarkers(index, layer);
/* 4035 */     ValueAxis axis = getRangeAxisForDataset(index);
/* 4036 */     if (markers != null && axis != null) {
/* 4037 */       Iterator<Marker> iterator = markers.iterator();
/* 4038 */       while (iterator.hasNext()) {
/* 4039 */         Marker marker = iterator.next();
/* 4040 */         r.drawRangeMarker(g2, this, axis, marker, dataArea);
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
/*      */   public Collection getDomainMarkers(Layer layer) {
/* 4055 */     return getDomainMarkers(0, layer);
/*      */   }
/*      */ 
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
/* 4068 */     return getRangeMarkers(0, layer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 4083 */     Collection<?> result = null;
/* 4084 */     Integer key = new Integer(index);
/* 4085 */     if (layer == Layer.FOREGROUND) {
/* 4086 */       result = (Collection)this.foregroundDomainMarkers.get(key);
/*      */     }
/* 4088 */     else if (layer == Layer.BACKGROUND) {
/* 4089 */       result = (Collection)this.backgroundDomainMarkers.get(key);
/*      */     } 
/* 4091 */     if (result != null) {
/* 4092 */       result = Collections.unmodifiableCollection(result);
/*      */     }
/* 4094 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 4109 */     Collection<?> result = null;
/* 4110 */     Integer key = new Integer(index);
/* 4111 */     if (layer == Layer.FOREGROUND) {
/* 4112 */       result = (Collection)this.foregroundRangeMarkers.get(key);
/*      */     }
/* 4114 */     else if (layer == Layer.BACKGROUND) {
/* 4115 */       result = (Collection)this.backgroundRangeMarkers.get(key);
/*      */     } 
/* 4117 */     if (result != null) {
/* 4118 */       result = Collections.unmodifiableCollection(result);
/*      */     }
/* 4120 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 4137 */     ValueAxis axis = getRangeAxis();
/* 4138 */     if (getOrientation() == PlotOrientation.HORIZONTAL) {
/* 4139 */       axis = getDomainAxis();
/*      */     }
/* 4141 */     if (axis.getRange().contains(value)) {
/* 4142 */       double yy = axis.valueToJava2D(value, dataArea, RectangleEdge.LEFT);
/*      */       
/* 4144 */       Line2D line = new Line2D.Double(dataArea.getMinX(), yy, dataArea.getMaxX(), yy);
/* 4145 */       g2.setStroke(stroke);
/* 4146 */       g2.setPaint(paint);
/* 4147 */       g2.draw(line);
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
/*      */   protected void drawDomainCrosshair(Graphics2D g2, Rectangle2D dataArea, PlotOrientation orientation, double value, ValueAxis axis, Stroke stroke, Paint paint) {
/*      */     Line2D line;
/* 4169 */     if (!axis.getRange().contains(value)) {
/*      */       return;
/*      */     }
/*      */     
/* 4173 */     if (orientation == PlotOrientation.VERTICAL) {
/* 4174 */       double xx = axis.valueToJava2D(value, dataArea, RectangleEdge.BOTTOM);
/*      */ 
/*      */       
/* 4177 */       line = new Line2D.Double(xx, dataArea.getMinY(), xx, dataArea.getMaxY());
/*      */     } else {
/* 4179 */       double yy = axis.valueToJava2D(value, dataArea, RectangleEdge.LEFT);
/*      */ 
/*      */       
/* 4182 */       line = new Line2D.Double(dataArea.getMinX(), yy, dataArea.getMaxX(), yy);
/*      */     } 
/* 4184 */     Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
/* 4185 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/*      */     
/* 4187 */     g2.setStroke(stroke);
/* 4188 */     g2.setPaint(paint);
/* 4189 */     g2.draw(line);
/* 4190 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 4205 */     ValueAxis axis = getDomainAxis();
/* 4206 */     if (getOrientation() == PlotOrientation.HORIZONTAL) {
/* 4207 */       axis = getRangeAxis();
/*      */     }
/* 4209 */     if (axis.getRange().contains(value)) {
/* 4210 */       double xx = axis.valueToJava2D(value, dataArea, RectangleEdge.BOTTOM);
/*      */ 
/*      */       
/* 4213 */       Line2D line = new Line2D.Double(xx, dataArea.getMinY(), xx, dataArea.getMaxY());
/* 4214 */       g2.setStroke(stroke);
/* 4215 */       g2.setPaint(paint);
/* 4216 */       g2.draw(line);
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
/*      */   protected void drawRangeCrosshair(Graphics2D g2, Rectangle2D dataArea, PlotOrientation orientation, double value, ValueAxis axis, Stroke stroke, Paint paint) {
/*      */     Line2D line;
/* 4238 */     if (!axis.getRange().contains(value)) {
/*      */       return;
/*      */     }
/* 4241 */     Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
/* 4242 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/*      */ 
/*      */     
/* 4245 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 4246 */       double xx = axis.valueToJava2D(value, dataArea, RectangleEdge.BOTTOM);
/*      */ 
/*      */       
/* 4249 */       line = new Line2D.Double(xx, dataArea.getMinY(), xx, dataArea.getMaxY());
/*      */     } else {
/* 4251 */       double yy = axis.valueToJava2D(value, dataArea, RectangleEdge.LEFT);
/*      */       
/* 4253 */       line = new Line2D.Double(dataArea.getMinX(), yy, dataArea.getMaxX(), yy);
/*      */     } 
/* 4255 */     g2.setStroke(stroke);
/* 4256 */     g2.setPaint(paint);
/* 4257 */     g2.draw(line);
/* 4258 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
/*      */   }
/*      */ 
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
/* 4271 */     Rectangle2D dataArea = info.getDataArea();
/* 4272 */     if (dataArea.contains(x, y)) {
/*      */       
/* 4274 */       ValueAxis xaxis = getDomainAxis();
/* 4275 */       if (xaxis != null) {
/* 4276 */         double hvalue = xaxis.java2DToValue(x, info.getDataArea(), 
/* 4277 */             getDomainAxisEdge());
/* 4278 */         setDomainCrosshairValue(hvalue);
/*      */       } 
/*      */ 
/*      */       
/* 4282 */       ValueAxis yaxis = getRangeAxis();
/* 4283 */       if (yaxis != null) {
/* 4284 */         double vvalue = yaxis.java2DToValue(y, info.getDataArea(), 
/* 4285 */             getRangeAxisEdge());
/* 4286 */         setRangeCrosshairValue(vvalue);
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
/*      */   private List<XYDataset> getDatasetsMappedToDomainAxis(Integer axisIndex) {
/* 4300 */     ParamChecks.nullNotPermitted(axisIndex, "axisIndex");
/* 4301 */     List<XYDataset> result = new ArrayList<XYDataset>();
/* 4302 */     for (Map.Entry<Integer, XYDataset> entry : this.datasets.entrySet()) {
/* 4303 */       int index = ((Integer)entry.getKey()).intValue();
/* 4304 */       List<Integer> mappedAxes = this.datasetToDomainAxesMap.get(Integer.valueOf(index));
/* 4305 */       if (mappedAxes == null) {
/* 4306 */         if (axisIndex.equals(ZERO))
/* 4307 */           result.add(entry.getValue()); 
/*      */         continue;
/*      */       } 
/* 4310 */       if (mappedAxes.contains(axisIndex)) {
/* 4311 */         result.add(entry.getValue());
/*      */       }
/*      */     } 
/*      */     
/* 4315 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<XYDataset> getDatasetsMappedToRangeAxis(Integer axisIndex) {
/* 4327 */     ParamChecks.nullNotPermitted(axisIndex, "axisIndex");
/* 4328 */     List<XYDataset> result = new ArrayList<XYDataset>();
/* 4329 */     for (Map.Entry<Integer, XYDataset> entry : this.datasets.entrySet()) {
/* 4330 */       int index = ((Integer)entry.getKey()).intValue();
/* 4331 */       List<Integer> mappedAxes = this.datasetToRangeAxesMap.get(Integer.valueOf(index));
/* 4332 */       if (mappedAxes == null) {
/* 4333 */         if (axisIndex.equals(ZERO))
/* 4334 */           result.add(entry.getValue()); 
/*      */         continue;
/*      */       } 
/* 4337 */       if (mappedAxes.contains(axisIndex)) {
/* 4338 */         result.add(entry.getValue());
/*      */       }
/*      */     } 
/*      */     
/* 4342 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDomainAxisIndex(ValueAxis axis) {
/* 4355 */     int result = findDomainAxisIndex(axis);
/* 4356 */     if (result < 0) {
/*      */       
/* 4358 */       Plot parent = getParent();
/* 4359 */       if (parent instanceof XYPlot) {
/* 4360 */         XYPlot p = (XYPlot)parent;
/* 4361 */         result = p.getDomainAxisIndex(axis);
/*      */       } 
/*      */     } 
/* 4364 */     return result;
/*      */   }
/*      */   
/*      */   private int findDomainAxisIndex(ValueAxis axis) {
/* 4368 */     for (Map.Entry<Integer, ValueAxis> entry : this.domainAxes.entrySet()) {
/* 4369 */       if (entry.getValue() == axis) {
/* 4370 */         return ((Integer)entry.getKey()).intValue();
/*      */       }
/*      */     } 
/* 4373 */     return -1;
/*      */   }
/*      */ 
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
/* 4386 */     int result = findRangeAxisIndex(axis);
/* 4387 */     if (result < 0) {
/*      */       
/* 4389 */       Plot parent = getParent();
/* 4390 */       if (parent instanceof XYPlot) {
/* 4391 */         XYPlot p = (XYPlot)parent;
/* 4392 */         result = p.getRangeAxisIndex(axis);
/*      */       } 
/*      */     } 
/* 4395 */     return result;
/*      */   }
/*      */   
/*      */   private int findRangeAxisIndex(ValueAxis axis) {
/* 4399 */     for (Map.Entry<Integer, ValueAxis> entry : this.rangeAxes.entrySet()) {
/* 4400 */       if (entry.getValue() == axis) {
/* 4401 */         return ((Integer)entry.getKey()).intValue();
/*      */       }
/*      */     } 
/* 4404 */     return -1;
/*      */   }
/*      */ 
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
/* 4417 */     Range result = null;
/* 4418 */     List<XYDataset> mappedDatasets = new ArrayList<XYDataset>();
/* 4419 */     List<XYAnnotation> includedAnnotations = new ArrayList<XYAnnotation>();
/* 4420 */     boolean isDomainAxis = true;
/*      */ 
/*      */     
/* 4423 */     int domainIndex = getDomainAxisIndex(axis);
/* 4424 */     if (domainIndex >= 0) {
/* 4425 */       isDomainAxis = true;
/* 4426 */       mappedDatasets.addAll(getDatasetsMappedToDomainAxis(Integer.valueOf(domainIndex)));
/* 4427 */       if (domainIndex == 0) {
/*      */         
/* 4429 */         Iterator<XYAnnotation> iterator = this.annotations.iterator();
/* 4430 */         while (iterator.hasNext()) {
/* 4431 */           XYAnnotation annotation = iterator.next();
/* 4432 */           if (annotation instanceof XYAnnotationBoundsInfo) {
/* 4433 */             includedAnnotations.add(annotation);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4440 */     int rangeIndex = getRangeAxisIndex(axis);
/* 4441 */     if (rangeIndex >= 0) {
/* 4442 */       isDomainAxis = false;
/* 4443 */       mappedDatasets.addAll(getDatasetsMappedToRangeAxis(Integer.valueOf(rangeIndex)));
/* 4444 */       if (rangeIndex == 0) {
/* 4445 */         Iterator<XYAnnotation> iterator = this.annotations.iterator();
/* 4446 */         while (iterator.hasNext()) {
/* 4447 */           XYAnnotation annotation = iterator.next();
/* 4448 */           if (annotation instanceof XYAnnotationBoundsInfo) {
/* 4449 */             includedAnnotations.add(annotation);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4457 */     for (XYDataset d : mappedDatasets) {
/* 4458 */       if (d != null) {
/* 4459 */         XYItemRenderer r = getRendererForDataset(d);
/* 4460 */         if (isDomainAxis) {
/* 4461 */           if (r != null) {
/* 4462 */             result = Range.combine(result, r.findDomainBounds(d));
/*      */           } else {
/*      */             
/* 4465 */             result = Range.combine(result, 
/* 4466 */                 DatasetUtilities.findDomainBounds(d));
/*      */           }
/*      */         
/*      */         }
/* 4470 */         else if (r != null) {
/* 4471 */           result = Range.combine(result, r.findRangeBounds(d));
/*      */         } else {
/*      */           
/* 4474 */           result = Range.combine(result, 
/* 4475 */               DatasetUtilities.findRangeBounds(d));
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 4480 */         if (r instanceof AbstractXYItemRenderer) {
/* 4481 */           AbstractXYItemRenderer rr = (AbstractXYItemRenderer)r;
/* 4482 */           Collection c = rr.getAnnotations();
/* 4483 */           Iterator<XYAnnotation> i = c.iterator();
/* 4484 */           while (i.hasNext()) {
/* 4485 */             XYAnnotation a = i.next();
/* 4486 */             if (a instanceof XYAnnotationBoundsInfo) {
/* 4487 */               includedAnnotations.add(a);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 4494 */     Iterator<XYAnnotation> it = includedAnnotations.iterator();
/* 4495 */     while (it.hasNext()) {
/* 4496 */       XYAnnotationBoundsInfo xyabi = (XYAnnotationBoundsInfo)it.next();
/* 4497 */       if (xyabi.getIncludeInDataBounds()) {
/* 4498 */         if (isDomainAxis) {
/* 4499 */           result = Range.combine(result, xyabi.getXRange());
/*      */           continue;
/*      */         } 
/* 4502 */         result = Range.combine(result, xyabi.getYRange());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4507 */     return result;
/*      */   }
/*      */ 
/*      */ 
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
/* 4521 */     if (getParent() != null) {
/* 4522 */       getParent().annotationChanged(event);
/*      */     } else {
/*      */       
/* 4525 */       PlotChangeEvent e = new PlotChangeEvent(this);
/* 4526 */       notifyListeners(e);
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
/* 4539 */     configureDomainAxes();
/* 4540 */     configureRangeAxes();
/* 4541 */     if (getParent() != null) {
/* 4542 */       getParent().datasetChanged(event);
/*      */     } else {
/*      */       
/* 4545 */       PlotChangeEvent e = new PlotChangeEvent(this);
/* 4546 */       e.setType(ChartChangeEventType.DATASET_UPDATED);
/* 4547 */       notifyListeners(e);
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
/*      */   public void rendererChanged(RendererChangeEvent event) {
/* 4560 */     if (event.getSeriesVisibilityChanged()) {
/* 4561 */       configureDomainAxes();
/* 4562 */       configureRangeAxes();
/*      */     } 
/* 4564 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDomainCrosshairVisible() {
/* 4575 */     return this.domainCrosshairVisible;
/*      */   }
/*      */ 
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
/* 4588 */     if (this.domainCrosshairVisible != flag) {
/* 4589 */       this.domainCrosshairVisible = flag;
/* 4590 */       fireChangeEvent();
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
/*      */   public boolean isDomainCrosshairLockedOnData() {
/* 4603 */     return this.domainCrosshairLockedOnData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainCrosshairLockedOnData(boolean flag) {
/* 4616 */     if (this.domainCrosshairLockedOnData != flag) {
/* 4617 */       this.domainCrosshairLockedOnData = flag;
/* 4618 */       fireChangeEvent();
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
/*      */   public double getDomainCrosshairValue() {
/* 4630 */     return this.domainCrosshairValue;
/*      */   }
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
/* 4642 */     setDomainCrosshairValue(value, true);
/*      */   }
/*      */ 
/*      */ 
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
/* 4656 */     this.domainCrosshairValue = value;
/* 4657 */     if (isDomainCrosshairVisible() && notify) {
/* 4658 */       fireChangeEvent();
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
/*      */   public Stroke getDomainCrosshairStroke() {
/* 4672 */     return this.domainCrosshairStroke;
/*      */   }
/*      */ 
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
/* 4685 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 4686 */     this.domainCrosshairStroke = stroke;
/* 4687 */     fireChangeEvent();
/*      */   }
/*      */ 
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
/* 4700 */     return this.domainCrosshairPaint;
/*      */   }
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
/* 4712 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 4713 */     this.domainCrosshairPaint = paint;
/* 4714 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangeCrosshairVisible() {
/* 4726 */     return this.rangeCrosshairVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeCrosshairVisible(boolean flag) {
/* 4739 */     if (this.rangeCrosshairVisible != flag) {
/* 4740 */       this.rangeCrosshairVisible = flag;
/* 4741 */       fireChangeEvent();
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
/* 4754 */     return this.rangeCrosshairLockedOnData;
/*      */   }
/*      */ 
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
/* 4767 */     if (this.rangeCrosshairLockedOnData != flag) {
/* 4768 */       this.rangeCrosshairLockedOnData = flag;
/* 4769 */       fireChangeEvent();
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
/* 4781 */     return this.rangeCrosshairValue;
/*      */   }
/*      */ 
/*      */ 
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
/* 4795 */     setRangeCrosshairValue(value, true);
/*      */   }
/*      */ 
/*      */ 
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
/* 4809 */     this.rangeCrosshairValue = value;
/* 4810 */     if (isRangeCrosshairVisible() && notify) {
/* 4811 */       fireChangeEvent();
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
/*      */   public Stroke getRangeCrosshairStroke() {
/* 4825 */     return this.rangeCrosshairStroke;
/*      */   }
/*      */ 
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
/* 4838 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 4839 */     this.rangeCrosshairStroke = stroke;
/* 4840 */     fireChangeEvent();
/*      */   }
/*      */ 
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
/* 4853 */     return this.rangeCrosshairPaint;
/*      */   }
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
/* 4865 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 4866 */     this.rangeCrosshairPaint = paint;
/* 4867 */     fireChangeEvent();
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
/* 4878 */     return this.fixedDomainAxisSpace;
/*      */   }
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
/* 4890 */     setFixedDomainAxisSpace(space, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 4905 */     this.fixedDomainAxisSpace = space;
/* 4906 */     if (notify) {
/* 4907 */       fireChangeEvent();
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
/* 4919 */     return this.fixedRangeAxisSpace;
/*      */   }
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
/* 4931 */     setFixedRangeAxisSpace(space, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 4946 */     this.fixedRangeAxisSpace = space;
/* 4947 */     if (notify) {
/* 4948 */       fireChangeEvent();
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
/*      */   public boolean isDomainPannable() {
/* 4962 */     return this.domainPannable;
/*      */   }
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
/* 4974 */     this.domainPannable = pannable;
/*      */   }
/*      */ 
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
/* 4987 */     return this.rangePannable;
/*      */   }
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
/* 4999 */     this.rangePannable = pannable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 5014 */     if (!isDomainPannable()) {
/*      */       return;
/*      */     }
/* 5017 */     int domainAxisCount = getDomainAxisCount();
/* 5018 */     for (int i = 0; i < domainAxisCount; i++) {
/* 5019 */       ValueAxis axis = getDomainAxis(i);
/* 5020 */       if (axis != null) {
/*      */ 
/*      */         
/* 5023 */         if (axis.isInverted()) {
/* 5024 */           percent = -percent;
/*      */         }
/* 5026 */         axis.pan(percent);
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
/*      */   public void panRangeAxes(double percent, PlotRenderingInfo info, Point2D source) {
/* 5042 */     if (!isRangePannable()) {
/*      */       return;
/*      */     }
/* 5045 */     int rangeAxisCount = getRangeAxisCount();
/* 5046 */     for (int i = 0; i < rangeAxisCount; i++) {
/* 5047 */       ValueAxis axis = getRangeAxis(i);
/* 5048 */       if (axis != null) {
/*      */ 
/*      */         
/* 5051 */         if (axis.isInverted()) {
/* 5052 */           percent = -percent;
/*      */         }
/* 5054 */         axis.pan(percent);
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
/*      */   public void zoomDomainAxes(double factor, PlotRenderingInfo info, Point2D source) {
/* 5071 */     zoomDomainAxes(factor, info, source, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 5091 */     for (ValueAxis xAxis : this.domainAxes.values()) {
/* 5092 */       if (xAxis == null) {
/*      */         continue;
/*      */       }
/* 5095 */       if (useAnchor) {
/*      */         
/* 5097 */         double sourceX = source.getX();
/* 5098 */         if (this.orientation == PlotOrientation.HORIZONTAL) {
/* 5099 */           sourceX = source.getY();
/*      */         }
/* 5101 */         double anchorX = xAxis.java2DToValue(sourceX, info
/* 5102 */             .getDataArea(), getDomainAxisEdge());
/* 5103 */         xAxis.resizeRange2(factor, anchorX); continue;
/*      */       } 
/* 5105 */       xAxis.resizeRange(factor);
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
/*      */   public void zoomDomainAxes(double lowerPercent, double upperPercent, PlotRenderingInfo info, Point2D source) {
/* 5127 */     for (ValueAxis xAxis : this.domainAxes.values()) {
/* 5128 */       if (xAxis != null) {
/* 5129 */         xAxis.zoomRange(lowerPercent, upperPercent);
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
/*      */   public void zoomRangeAxes(double factor, PlotRenderingInfo info, Point2D source) {
/* 5147 */     zoomRangeAxes(factor, info, source, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 5168 */     for (ValueAxis yAxis : this.rangeAxes.values()) {
/* 5169 */       if (yAxis == null) {
/*      */         continue;
/*      */       }
/* 5172 */       if (useAnchor) {
/*      */         
/* 5174 */         double sourceY = source.getY();
/* 5175 */         if (this.orientation == PlotOrientation.HORIZONTAL) {
/* 5176 */           sourceY = source.getX();
/*      */         }
/* 5178 */         double anchorY = yAxis.java2DToValue(sourceY, info
/* 5179 */             .getDataArea(), getRangeAxisEdge());
/* 5180 */         yAxis.resizeRange2(factor, anchorY); continue;
/*      */       } 
/* 5182 */       yAxis.resizeRange(factor);
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
/* 5200 */     for (ValueAxis yAxis : this.rangeAxes.values()) {
/* 5201 */       if (yAxis != null) {
/* 5202 */         yAxis.zoomRange(lowerPercent, upperPercent);
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
/*      */   public boolean isDomainZoomable() {
/* 5217 */     return true;
/*      */   }
/*      */ 
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
/* 5230 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSeriesCount() {
/* 5240 */     int result = 0;
/* 5241 */     XYDataset dataset = getDataset();
/* 5242 */     if (dataset != null) {
/* 5243 */       result = dataset.getSeriesCount();
/*      */     }
/* 5245 */     return result;
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
/* 5256 */     return this.fixedLegendItems;
/*      */   }
/*      */ 
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
/* 5269 */     this.fixedLegendItems = items;
/* 5270 */     fireChangeEvent();
/*      */   }
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
/* 5282 */     if (this.fixedLegendItems != null) {
/* 5283 */       return this.fixedLegendItems;
/*      */     }
/* 5285 */     LegendItemCollection result = new LegendItemCollection();
/* 5286 */     for (XYDataset dataset : this.datasets.values()) {
/* 5287 */       if (dataset == null) {
/*      */         continue;
/*      */       }
/* 5290 */       int datasetIndex = indexOf(dataset);
/* 5291 */       XYItemRenderer renderer = getRenderer(datasetIndex);
/* 5292 */       if (renderer == null) {
/* 5293 */         renderer = getRenderer(0);
/*      */       }
/* 5295 */       if (renderer != null) {
/* 5296 */         int seriesCount = dataset.getSeriesCount();
/* 5297 */         for (int i = 0; i < seriesCount; i++) {
/* 5298 */           if (renderer.isSeriesVisible(i) && renderer
/* 5299 */             .isSeriesVisibleInLegend(i)) {
/* 5300 */             LegendItem item = renderer.getLegendItem(datasetIndex, i);
/*      */             
/* 5302 */             if (item != null) {
/* 5303 */               result.add(item);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 5309 */     return result;
/*      */   }
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
/* 5321 */     if (obj == this) {
/* 5322 */       return true;
/*      */     }
/* 5324 */     if (!(obj instanceof XYPlot)) {
/* 5325 */       return false;
/*      */     }
/* 5327 */     XYPlot that = (XYPlot)obj;
/* 5328 */     if (this.weight != that.weight) {
/* 5329 */       return false;
/*      */     }
/* 5331 */     if (this.orientation != that.orientation) {
/* 5332 */       return false;
/*      */     }
/* 5334 */     if (!this.domainAxes.equals(that.domainAxes)) {
/* 5335 */       return false;
/*      */     }
/* 5337 */     if (!this.domainAxisLocations.equals(that.domainAxisLocations)) {
/* 5338 */       return false;
/*      */     }
/* 5340 */     if (this.rangeCrosshairLockedOnData != that.rangeCrosshairLockedOnData)
/*      */     {
/* 5342 */       return false;
/*      */     }
/* 5344 */     if (this.domainGridlinesVisible != that.domainGridlinesVisible) {
/* 5345 */       return false;
/*      */     }
/* 5347 */     if (this.rangeGridlinesVisible != that.rangeGridlinesVisible) {
/* 5348 */       return false;
/*      */     }
/* 5350 */     if (this.domainMinorGridlinesVisible != that.domainMinorGridlinesVisible)
/*      */     {
/* 5352 */       return false;
/*      */     }
/* 5354 */     if (this.rangeMinorGridlinesVisible != that.rangeMinorGridlinesVisible)
/*      */     {
/* 5356 */       return false;
/*      */     }
/* 5358 */     if (this.domainZeroBaselineVisible != that.domainZeroBaselineVisible) {
/* 5359 */       return false;
/*      */     }
/* 5361 */     if (this.rangeZeroBaselineVisible != that.rangeZeroBaselineVisible) {
/* 5362 */       return false;
/*      */     }
/* 5364 */     if (this.domainCrosshairVisible != that.domainCrosshairVisible) {
/* 5365 */       return false;
/*      */     }
/* 5367 */     if (this.domainCrosshairValue != that.domainCrosshairValue) {
/* 5368 */       return false;
/*      */     }
/* 5370 */     if (this.domainCrosshairLockedOnData != that.domainCrosshairLockedOnData)
/*      */     {
/* 5372 */       return false;
/*      */     }
/* 5374 */     if (this.rangeCrosshairVisible != that.rangeCrosshairVisible) {
/* 5375 */       return false;
/*      */     }
/* 5377 */     if (this.rangeCrosshairValue != that.rangeCrosshairValue) {
/* 5378 */       return false;
/*      */     }
/* 5380 */     if (!ObjectUtilities.equal(this.axisOffset, that.axisOffset)) {
/* 5381 */       return false;
/*      */     }
/* 5383 */     if (!ObjectUtilities.equal(this.renderers, that.renderers)) {
/* 5384 */       return false;
/*      */     }
/* 5386 */     if (!ObjectUtilities.equal(this.rangeAxes, that.rangeAxes)) {
/* 5387 */       return false;
/*      */     }
/* 5389 */     if (!this.rangeAxisLocations.equals(that.rangeAxisLocations)) {
/* 5390 */       return false;
/*      */     }
/* 5392 */     if (!ObjectUtilities.equal(this.datasetToDomainAxesMap, that.datasetToDomainAxesMap))
/*      */     {
/* 5394 */       return false;
/*      */     }
/* 5396 */     if (!ObjectUtilities.equal(this.datasetToRangeAxesMap, that.datasetToRangeAxesMap))
/*      */     {
/* 5398 */       return false;
/*      */     }
/* 5400 */     if (!ObjectUtilities.equal(this.domainGridlineStroke, that.domainGridlineStroke))
/*      */     {
/* 5402 */       return false;
/*      */     }
/* 5404 */     if (!PaintUtilities.equal(this.domainGridlinePaint, that.domainGridlinePaint))
/*      */     {
/* 5406 */       return false;
/*      */     }
/* 5408 */     if (!ObjectUtilities.equal(this.rangeGridlineStroke, that.rangeGridlineStroke))
/*      */     {
/* 5410 */       return false;
/*      */     }
/* 5412 */     if (!PaintUtilities.equal(this.rangeGridlinePaint, that.rangeGridlinePaint))
/*      */     {
/* 5414 */       return false;
/*      */     }
/* 5416 */     if (!ObjectUtilities.equal(this.domainMinorGridlineStroke, that.domainMinorGridlineStroke))
/*      */     {
/* 5418 */       return false;
/*      */     }
/* 5420 */     if (!PaintUtilities.equal(this.domainMinorGridlinePaint, that.domainMinorGridlinePaint))
/*      */     {
/* 5422 */       return false;
/*      */     }
/* 5424 */     if (!ObjectUtilities.equal(this.rangeMinorGridlineStroke, that.rangeMinorGridlineStroke))
/*      */     {
/* 5426 */       return false;
/*      */     }
/* 5428 */     if (!PaintUtilities.equal(this.rangeMinorGridlinePaint, that.rangeMinorGridlinePaint))
/*      */     {
/* 5430 */       return false;
/*      */     }
/* 5432 */     if (!PaintUtilities.equal(this.domainZeroBaselinePaint, that.domainZeroBaselinePaint))
/*      */     {
/* 5434 */       return false;
/*      */     }
/* 5436 */     if (!ObjectUtilities.equal(this.domainZeroBaselineStroke, that.domainZeroBaselineStroke))
/*      */     {
/* 5438 */       return false;
/*      */     }
/* 5440 */     if (!PaintUtilities.equal(this.rangeZeroBaselinePaint, that.rangeZeroBaselinePaint))
/*      */     {
/* 5442 */       return false;
/*      */     }
/* 5444 */     if (!ObjectUtilities.equal(this.rangeZeroBaselineStroke, that.rangeZeroBaselineStroke))
/*      */     {
/* 5446 */       return false;
/*      */     }
/* 5448 */     if (!ObjectUtilities.equal(this.domainCrosshairStroke, that.domainCrosshairStroke))
/*      */     {
/* 5450 */       return false;
/*      */     }
/* 5452 */     if (!PaintUtilities.equal(this.domainCrosshairPaint, that.domainCrosshairPaint))
/*      */     {
/* 5454 */       return false;
/*      */     }
/* 5456 */     if (!ObjectUtilities.equal(this.rangeCrosshairStroke, that.rangeCrosshairStroke))
/*      */     {
/* 5458 */       return false;
/*      */     }
/* 5460 */     if (!PaintUtilities.equal(this.rangeCrosshairPaint, that.rangeCrosshairPaint))
/*      */     {
/* 5462 */       return false;
/*      */     }
/* 5464 */     if (!ObjectUtilities.equal(this.foregroundDomainMarkers, that.foregroundDomainMarkers))
/*      */     {
/* 5466 */       return false;
/*      */     }
/* 5468 */     if (!ObjectUtilities.equal(this.backgroundDomainMarkers, that.backgroundDomainMarkers))
/*      */     {
/* 5470 */       return false;
/*      */     }
/* 5472 */     if (!ObjectUtilities.equal(this.foregroundRangeMarkers, that.foregroundRangeMarkers))
/*      */     {
/* 5474 */       return false;
/*      */     }
/* 5476 */     if (!ObjectUtilities.equal(this.backgroundRangeMarkers, that.backgroundRangeMarkers))
/*      */     {
/* 5478 */       return false;
/*      */     }
/* 5480 */     if (!ObjectUtilities.equal(this.foregroundDomainMarkers, that.foregroundDomainMarkers))
/*      */     {
/* 5482 */       return false;
/*      */     }
/* 5484 */     if (!ObjectUtilities.equal(this.backgroundDomainMarkers, that.backgroundDomainMarkers))
/*      */     {
/* 5486 */       return false;
/*      */     }
/* 5488 */     if (!ObjectUtilities.equal(this.foregroundRangeMarkers, that.foregroundRangeMarkers))
/*      */     {
/* 5490 */       return false;
/*      */     }
/* 5492 */     if (!ObjectUtilities.equal(this.backgroundRangeMarkers, that.backgroundRangeMarkers))
/*      */     {
/* 5494 */       return false;
/*      */     }
/* 5496 */     if (!ObjectUtilities.equal(this.annotations, that.annotations)) {
/* 5497 */       return false;
/*      */     }
/* 5499 */     if (!ObjectUtilities.equal(this.fixedLegendItems, that.fixedLegendItems))
/*      */     {
/* 5501 */       return false;
/*      */     }
/* 5503 */     if (!PaintUtilities.equal(this.domainTickBandPaint, that.domainTickBandPaint))
/*      */     {
/* 5505 */       return false;
/*      */     }
/* 5507 */     if (!PaintUtilities.equal(this.rangeTickBandPaint, that.rangeTickBandPaint))
/*      */     {
/* 5509 */       return false;
/*      */     }
/* 5511 */     if (!this.quadrantOrigin.equals(that.quadrantOrigin)) {
/* 5512 */       return false;
/*      */     }
/* 5514 */     for (int i = 0; i < 4; i++) {
/* 5515 */       if (!PaintUtilities.equal(this.quadrantPaint[i], that.quadrantPaint[i]))
/*      */       {
/* 5517 */         return false;
/*      */       }
/*      */     } 
/* 5520 */     if (!ObjectUtilities.equal(this.shadowGenerator, that.shadowGenerator))
/*      */     {
/* 5522 */       return false;
/*      */     }
/* 5524 */     return super.equals(obj);
/*      */   }
/*      */ 
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
/* 5537 */     XYPlot clone = (XYPlot)super.clone();
/* 5538 */     clone.domainAxes = CloneUtils.cloneMapValues(this.domainAxes);
/* 5539 */     for (ValueAxis axis : clone.domainAxes.values()) {
/* 5540 */       if (axis != null) {
/* 5541 */         axis.setPlot(clone);
/* 5542 */         axis.addChangeListener(clone);
/*      */       } 
/*      */     } 
/* 5545 */     clone.rangeAxes = CloneUtils.cloneMapValues(this.rangeAxes);
/* 5546 */     for (ValueAxis axis : clone.rangeAxes.values()) {
/* 5547 */       if (axis != null) {
/* 5548 */         axis.setPlot(clone);
/* 5549 */         axis.addChangeListener(clone);
/*      */       } 
/*      */     } 
/* 5552 */     clone.domainAxisLocations = new HashMap<Integer, AxisLocation>(this.domainAxisLocations);
/*      */     
/* 5554 */     clone.rangeAxisLocations = new HashMap<Integer, AxisLocation>(this.rangeAxisLocations);
/*      */ 
/*      */ 
/*      */     
/* 5558 */     clone.datasets = new HashMap<Integer, XYDataset>(this.datasets);
/* 5559 */     for (XYDataset dataset : clone.datasets.values()) {
/* 5560 */       if (dataset != null) {
/* 5561 */         dataset.addChangeListener(clone);
/*      */       }
/*      */     } 
/*      */     
/* 5565 */     clone.datasetToDomainAxesMap = new TreeMap<Integer, List<Integer>>();
/* 5566 */     clone.datasetToDomainAxesMap.putAll(this.datasetToDomainAxesMap);
/* 5567 */     clone.datasetToRangeAxesMap = new TreeMap<Integer, List<Integer>>();
/* 5568 */     clone.datasetToRangeAxesMap.putAll(this.datasetToRangeAxesMap);
/*      */     
/* 5570 */     clone.renderers = CloneUtils.cloneMapValues(this.renderers);
/* 5571 */     for (XYItemRenderer renderer : clone.renderers.values()) {
/* 5572 */       if (renderer != null) {
/* 5573 */         renderer.setPlot(clone);
/* 5574 */         renderer.addChangeListener(clone);
/*      */       } 
/*      */     } 
/* 5577 */     clone.foregroundDomainMarkers = (Map)ObjectUtilities.clone(this.foregroundDomainMarkers);
/*      */     
/* 5579 */     clone.backgroundDomainMarkers = (Map)ObjectUtilities.clone(this.backgroundDomainMarkers);
/*      */     
/* 5581 */     clone.foregroundRangeMarkers = (Map)ObjectUtilities.clone(this.foregroundRangeMarkers);
/*      */     
/* 5583 */     clone.backgroundRangeMarkers = (Map)ObjectUtilities.clone(this.backgroundRangeMarkers);
/*      */     
/* 5585 */     clone.annotations = (List<XYAnnotation>)ObjectUtilities.deepClone(this.annotations);
/* 5586 */     if (this.fixedDomainAxisSpace != null) {
/* 5587 */       clone.fixedDomainAxisSpace = (AxisSpace)ObjectUtilities.clone(this.fixedDomainAxisSpace);
/*      */     }
/*      */     
/* 5590 */     if (this.fixedRangeAxisSpace != null) {
/* 5591 */       clone.fixedRangeAxisSpace = (AxisSpace)ObjectUtilities.clone(this.fixedRangeAxisSpace);
/*      */     }
/*      */     
/* 5594 */     if (this.fixedLegendItems != null) {
/* 5595 */       clone
/* 5596 */         .fixedLegendItems = (LegendItemCollection)this.fixedLegendItems.clone();
/*      */     }
/* 5598 */     clone.quadrantOrigin = (Point2D)ObjectUtilities.clone(this.quadrantOrigin);
/*      */     
/* 5600 */     clone.quadrantPaint = (Paint[])this.quadrantPaint.clone();
/* 5601 */     return clone;
/*      */   }
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
/* 5613 */     stream.defaultWriteObject();
/* 5614 */     SerialUtilities.writeStroke(this.domainGridlineStroke, stream);
/* 5615 */     SerialUtilities.writePaint(this.domainGridlinePaint, stream);
/* 5616 */     SerialUtilities.writeStroke(this.rangeGridlineStroke, stream);
/* 5617 */     SerialUtilities.writePaint(this.rangeGridlinePaint, stream);
/* 5618 */     SerialUtilities.writeStroke(this.domainMinorGridlineStroke, stream);
/* 5619 */     SerialUtilities.writePaint(this.domainMinorGridlinePaint, stream);
/* 5620 */     SerialUtilities.writeStroke(this.rangeMinorGridlineStroke, stream);
/* 5621 */     SerialUtilities.writePaint(this.rangeMinorGridlinePaint, stream);
/* 5622 */     SerialUtilities.writeStroke(this.rangeZeroBaselineStroke, stream);
/* 5623 */     SerialUtilities.writePaint(this.rangeZeroBaselinePaint, stream);
/* 5624 */     SerialUtilities.writeStroke(this.domainCrosshairStroke, stream);
/* 5625 */     SerialUtilities.writePaint(this.domainCrosshairPaint, stream);
/* 5626 */     SerialUtilities.writeStroke(this.rangeCrosshairStroke, stream);
/* 5627 */     SerialUtilities.writePaint(this.rangeCrosshairPaint, stream);
/* 5628 */     SerialUtilities.writePaint(this.domainTickBandPaint, stream);
/* 5629 */     SerialUtilities.writePaint(this.rangeTickBandPaint, stream);
/* 5630 */     SerialUtilities.writePoint2D(this.quadrantOrigin, stream);
/* 5631 */     for (int i = 0; i < 4; i++) {
/* 5632 */       SerialUtilities.writePaint(this.quadrantPaint[i], stream);
/*      */     }
/* 5634 */     SerialUtilities.writeStroke(this.domainZeroBaselineStroke, stream);
/* 5635 */     SerialUtilities.writePaint(this.domainZeroBaselinePaint, stream);
/*      */   }
/*      */ 
/*      */ 
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
/* 5649 */     stream.defaultReadObject();
/* 5650 */     this.domainGridlineStroke = SerialUtilities.readStroke(stream);
/* 5651 */     this.domainGridlinePaint = SerialUtilities.readPaint(stream);
/* 5652 */     this.rangeGridlineStroke = SerialUtilities.readStroke(stream);
/* 5653 */     this.rangeGridlinePaint = SerialUtilities.readPaint(stream);
/* 5654 */     this.domainMinorGridlineStroke = SerialUtilities.readStroke(stream);
/* 5655 */     this.domainMinorGridlinePaint = SerialUtilities.readPaint(stream);
/* 5656 */     this.rangeMinorGridlineStroke = SerialUtilities.readStroke(stream);
/* 5657 */     this.rangeMinorGridlinePaint = SerialUtilities.readPaint(stream);
/* 5658 */     this.rangeZeroBaselineStroke = SerialUtilities.readStroke(stream);
/* 5659 */     this.rangeZeroBaselinePaint = SerialUtilities.readPaint(stream);
/* 5660 */     this.domainCrosshairStroke = SerialUtilities.readStroke(stream);
/* 5661 */     this.domainCrosshairPaint = SerialUtilities.readPaint(stream);
/* 5662 */     this.rangeCrosshairStroke = SerialUtilities.readStroke(stream);
/* 5663 */     this.rangeCrosshairPaint = SerialUtilities.readPaint(stream);
/* 5664 */     this.domainTickBandPaint = SerialUtilities.readPaint(stream);
/* 5665 */     this.rangeTickBandPaint = SerialUtilities.readPaint(stream);
/* 5666 */     this.quadrantOrigin = SerialUtilities.readPoint2D(stream);
/* 5667 */     this.quadrantPaint = new Paint[4];
/* 5668 */     for (int i = 0; i < 4; i++) {
/* 5669 */       this.quadrantPaint[i] = SerialUtilities.readPaint(stream);
/*      */     }
/*      */     
/* 5672 */     this.domainZeroBaselineStroke = SerialUtilities.readStroke(stream);
/* 5673 */     this.domainZeroBaselinePaint = SerialUtilities.readPaint(stream);
/*      */ 
/*      */ 
/*      */     
/* 5677 */     for (ValueAxis axis : this.domainAxes.values()) {
/* 5678 */       if (axis != null) {
/* 5679 */         axis.setPlot(this);
/* 5680 */         axis.addChangeListener(this);
/*      */       } 
/*      */     } 
/* 5683 */     for (ValueAxis axis : this.rangeAxes.values()) {
/* 5684 */       if (axis != null) {
/* 5685 */         axis.setPlot(this);
/* 5686 */         axis.addChangeListener(this);
/*      */       } 
/*      */     } 
/* 5689 */     for (XYDataset dataset : this.datasets.values()) {
/* 5690 */       if (dataset != null) {
/* 5691 */         dataset.addChangeListener(this);
/*      */       }
/*      */     } 
/* 5694 */     for (XYItemRenderer renderer : this.renderers.values()) {
/* 5695 */       if (renderer != null)
/* 5696 */         renderer.addChangeListener(this); 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/XYPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */