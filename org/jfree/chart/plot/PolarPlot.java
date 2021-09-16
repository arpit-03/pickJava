/*      */ package org.jfree.chart.plot;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Point;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.TreeMap;
/*      */ import org.jfree.chart.LegendItem;
/*      */ import org.jfree.chart.LegendItemCollection;
/*      */ import org.jfree.chart.axis.Axis;
/*      */ import org.jfree.chart.axis.AxisState;
/*      */ import org.jfree.chart.axis.NumberTick;
/*      */ import org.jfree.chart.axis.NumberTickUnit;
/*      */ import org.jfree.chart.axis.TickType;
/*      */ import org.jfree.chart.axis.TickUnit;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.axis.ValueTick;
/*      */ import org.jfree.chart.event.RendererChangeEvent;
/*      */ import org.jfree.chart.event.RendererChangeListener;
/*      */ import org.jfree.chart.renderer.PolarItemRenderer;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.chart.util.ResourceBundleWrapper;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.general.DatasetChangeEvent;
/*      */ import org.jfree.data.general.DatasetUtilities;
/*      */ import org.jfree.data.xy.XYDataset;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ import org.jfree.ui.TextAnchor;
/*      */ import org.jfree.util.ObjectList;
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
/*      */ public class PolarPlot
/*      */   extends Plot
/*      */   implements ValueAxisPlot, Zoomable, RendererChangeListener, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 3794383185924179525L;
/*      */   private static final int DEFAULT_MARGIN = 20;
/*      */   private static final double ANNOTATION_MARGIN = 7.0D;
/*      */   public static final double DEFAULT_ANGLE_TICK_UNIT_SIZE = 45.0D;
/*      */   public static final double DEFAULT_ANGLE_OFFSET = -90.0D;
/*  152 */   public static final Stroke DEFAULT_GRIDLINE_STROKE = new BasicStroke(0.5F, 0, 2, 0.0F, new float[] { 2.0F, 2.0F }, 0.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  157 */   public static final Paint DEFAULT_GRIDLINE_PAINT = Color.gray;
/*      */ 
/*      */ 
/*      */   
/*  161 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List angleTicks;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ObjectList axes;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ObjectList axisLocations;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ObjectList datasets;
/*      */ 
/*      */ 
/*      */   
/*      */   private ObjectList renderers;
/*      */ 
/*      */ 
/*      */   
/*      */   private TickUnit angleTickUnit;
/*      */ 
/*      */ 
/*      */   
/*      */   private double angleOffset;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean counterClockwise;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean angleLabelsVisible = true;
/*      */ 
/*      */ 
/*      */   
/*  205 */   private Font angleLabelFont = new Font("SansSerif", 0, 12);
/*      */ 
/*      */   
/*  208 */   private transient Paint angleLabelPaint = Color.black;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean angleGridlinesVisible;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke angleGridlineStroke;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint angleGridlinePaint;
/*      */ 
/*      */   
/*      */   private boolean radiusGridlinesVisible;
/*      */ 
/*      */   
/*      */   private transient Stroke radiusGridlineStroke;
/*      */ 
/*      */   
/*      */   private transient Paint radiusGridlinePaint;
/*      */ 
/*      */   
/*      */   private boolean radiusMinorGridlinesVisible;
/*      */ 
/*      */   
/*  235 */   private List cornerTextItems = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int margin;
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
/*      */   private Map datasetToAxesMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolarPlot() {
/*  264 */     this((XYDataset)null, (ValueAxis)null, (PolarItemRenderer)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolarPlot(XYDataset dataset, ValueAxis radiusAxis, PolarItemRenderer renderer) {
/*  279 */     this.datasets = new ObjectList();
/*  280 */     this.datasets.set(0, dataset);
/*  281 */     if (dataset != null) {
/*  282 */       dataset.addChangeListener(this);
/*      */     }
/*  284 */     this.angleTickUnit = (TickUnit)new NumberTickUnit(45.0D);
/*      */     
/*  286 */     this.axes = new ObjectList();
/*  287 */     this.datasetToAxesMap = new TreeMap<Object, Object>();
/*  288 */     this.axes.set(0, radiusAxis);
/*  289 */     if (radiusAxis != null) {
/*  290 */       radiusAxis.setPlot(this);
/*  291 */       radiusAxis.addChangeListener(this);
/*      */     } 
/*      */ 
/*      */     
/*  295 */     this.axisLocations = new ObjectList();
/*  296 */     this.axisLocations.set(0, PolarAxisLocation.EAST_ABOVE);
/*  297 */     this.axisLocations.set(1, PolarAxisLocation.NORTH_LEFT);
/*  298 */     this.axisLocations.set(2, PolarAxisLocation.WEST_BELOW);
/*  299 */     this.axisLocations.set(3, PolarAxisLocation.SOUTH_RIGHT);
/*  300 */     this.axisLocations.set(4, PolarAxisLocation.EAST_BELOW);
/*  301 */     this.axisLocations.set(5, PolarAxisLocation.NORTH_RIGHT);
/*  302 */     this.axisLocations.set(6, PolarAxisLocation.WEST_ABOVE);
/*  303 */     this.axisLocations.set(7, PolarAxisLocation.SOUTH_LEFT);
/*      */     
/*  305 */     this.renderers = new ObjectList();
/*  306 */     this.renderers.set(0, renderer);
/*  307 */     if (renderer != null) {
/*  308 */       renderer.setPlot(this);
/*  309 */       renderer.addChangeListener(this);
/*      */     } 
/*      */     
/*  312 */     this.angleOffset = -90.0D;
/*  313 */     this.counterClockwise = false;
/*  314 */     this.angleGridlinesVisible = true;
/*  315 */     this.angleGridlineStroke = DEFAULT_GRIDLINE_STROKE;
/*  316 */     this.angleGridlinePaint = DEFAULT_GRIDLINE_PAINT;
/*      */     
/*  318 */     this.radiusGridlinesVisible = true;
/*  319 */     this.radiusMinorGridlinesVisible = true;
/*  320 */     this.radiusGridlineStroke = DEFAULT_GRIDLINE_STROKE;
/*  321 */     this.radiusGridlinePaint = DEFAULT_GRIDLINE_PAINT;
/*  322 */     this.margin = 20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlotType() {
/*  332 */     return localizationResources.getString("Polar_Plot");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueAxis getAxis() {
/*  343 */     return getAxis(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueAxis getAxis(int index) {
/*  358 */     ValueAxis result = null;
/*  359 */     if (index < this.axes.size()) {
/*  360 */       result = (ValueAxis)this.axes.get(index);
/*      */     }
/*  362 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxis(ValueAxis axis) {
/*  372 */     setAxis(0, axis);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxis(int index, ValueAxis axis) {
/*  387 */     setAxis(index, axis, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxis(int index, ValueAxis axis, boolean notify) {
/*  403 */     ValueAxis existing = getAxis(index);
/*  404 */     if (existing != null) {
/*  405 */       existing.removeChangeListener(this);
/*      */     }
/*  407 */     if (axis != null) {
/*  408 */       axis.setPlot(this);
/*      */     }
/*  410 */     this.axes.set(index, axis);
/*  411 */     if (axis != null) {
/*  412 */       axis.configure();
/*  413 */       axis.addChangeListener(this);
/*      */     } 
/*  415 */     if (notify) {
/*  416 */       fireChangeEvent();
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
/*      */   public PolarAxisLocation getAxisLocation() {
/*  430 */     return getAxisLocation(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolarAxisLocation getAxisLocation(int index) {
/*  445 */     PolarAxisLocation result = null;
/*  446 */     if (index < this.axisLocations.size()) {
/*  447 */       result = (PolarAxisLocation)this.axisLocations.get(index);
/*      */     }
/*  449 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxisLocation(PolarAxisLocation location) {
/*  464 */     setAxisLocation(0, location, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxisLocation(PolarAxisLocation location, boolean notify) {
/*  480 */     setAxisLocation(0, location, notify);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxisLocation(int index, PolarAxisLocation location) {
/*  496 */     setAxisLocation(index, location, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxisLocation(int index, PolarAxisLocation location, boolean notify) {
/*  511 */     ParamChecks.nullNotPermitted(location, "location");
/*  512 */     this.axisLocations.set(index, location);
/*  513 */     if (notify) {
/*  514 */       fireChangeEvent();
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
/*      */   public int getAxisCount() {
/*  526 */     return this.axes.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYDataset getDataset() {
/*  537 */     return getDataset(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  552 */     XYDataset result = null;
/*  553 */     if (index < this.datasets.size()) {
/*  554 */       result = (XYDataset)this.datasets.get(index);
/*      */     }
/*  556 */     return result;
/*      */   }
/*      */ 
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
/*  569 */     setDataset(0, dataset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  585 */     XYDataset existing = getDataset(index);
/*  586 */     if (existing != null) {
/*  587 */       existing.removeChangeListener(this);
/*      */     }
/*  589 */     this.datasets.set(index, dataset);
/*  590 */     if (dataset != null) {
/*  591 */       dataset.addChangeListener(this);
/*      */     }
/*      */ 
/*      */     
/*  595 */     DatasetChangeEvent event = new DatasetChangeEvent(this, (Dataset)dataset);
/*  596 */     datasetChanged(event);
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
/*  607 */     return this.datasets.size();
/*      */   }
/*      */ 
/*      */ 
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
/*  621 */     int result = -1;
/*  622 */     for (int i = 0; i < this.datasets.size(); i++) {
/*  623 */       if (dataset == this.datasets.get(i)) {
/*  624 */         result = i;
/*      */         break;
/*      */       } 
/*      */     } 
/*  628 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolarItemRenderer getRenderer() {
/*  639 */     return getRenderer(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolarItemRenderer getRenderer(int index) {
/*  654 */     PolarItemRenderer result = null;
/*  655 */     if (index < this.renderers.size()) {
/*  656 */       result = (PolarItemRenderer)this.renderers.get(index);
/*      */     }
/*  658 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderer(PolarItemRenderer renderer) {
/*  671 */     setRenderer(0, renderer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderer(int index, PolarItemRenderer renderer) {
/*  686 */     setRenderer(index, renderer, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderer(int index, PolarItemRenderer renderer, boolean notify) {
/*  703 */     PolarItemRenderer existing = getRenderer(index);
/*  704 */     if (existing != null) {
/*  705 */       existing.removeChangeListener(this);
/*      */     }
/*  707 */     this.renderers.set(index, renderer);
/*  708 */     if (renderer != null) {
/*  709 */       renderer.setPlot(this);
/*  710 */       renderer.addChangeListener(this);
/*      */     } 
/*  712 */     if (notify) {
/*  713 */       fireChangeEvent();
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
/*      */   public TickUnit getAngleTickUnit() {
/*  726 */     return this.angleTickUnit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngleTickUnit(TickUnit unit) {
/*  738 */     ParamChecks.nullNotPermitted(unit, "unit");
/*  739 */     this.angleTickUnit = unit;
/*  740 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getAngleOffset() {
/*  750 */     return this.angleOffset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngleOffset(double offset) {
/*  764 */     this.angleOffset = offset;
/*  765 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCounterClockwise() {
/*  776 */     return this.counterClockwise;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCounterClockwise(boolean counterClockwise) {
/*  790 */     this.counterClockwise = counterClockwise;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAngleLabelsVisible() {
/*  801 */     return this.angleLabelsVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngleLabelsVisible(boolean visible) {
/*  813 */     if (this.angleLabelsVisible != visible) {
/*  814 */       this.angleLabelsVisible = visible;
/*  815 */       fireChangeEvent();
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
/*      */   public Font getAngleLabelFont() {
/*  827 */     return this.angleLabelFont;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngleLabelFont(Font font) {
/*  839 */     ParamChecks.nullNotPermitted(font, "font");
/*  840 */     this.angleLabelFont = font;
/*  841 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getAngleLabelPaint() {
/*  852 */     return this.angleLabelPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngleLabelPaint(Paint paint) {
/*  862 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  863 */     this.angleLabelPaint = paint;
/*  864 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAngleGridlinesVisible() {
/*  876 */     return this.angleGridlinesVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngleGridlinesVisible(boolean visible) {
/*  891 */     if (this.angleGridlinesVisible != visible) {
/*  892 */       this.angleGridlinesVisible = visible;
/*  893 */       fireChangeEvent();
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
/*      */   public Stroke getAngleGridlineStroke() {
/*  906 */     return this.angleGridlineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngleGridlineStroke(Stroke stroke) {
/*  920 */     this.angleGridlineStroke = stroke;
/*  921 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getAngleGridlinePaint() {
/*  933 */     return this.angleGridlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngleGridlinePaint(Paint paint) {
/*  946 */     this.angleGridlinePaint = paint;
/*  947 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRadiusGridlinesVisible() {
/*  959 */     return this.radiusGridlinesVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRadiusGridlinesVisible(boolean visible) {
/*  974 */     if (this.radiusGridlinesVisible != visible) {
/*  975 */       this.radiusGridlinesVisible = visible;
/*  976 */       fireChangeEvent();
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
/*      */   public Stroke getRadiusGridlineStroke() {
/*  989 */     return this.radiusGridlineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRadiusGridlineStroke(Stroke stroke) {
/* 1003 */     this.radiusGridlineStroke = stroke;
/* 1004 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getRadiusGridlinePaint() {
/* 1016 */     return this.radiusGridlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRadiusGridlinePaint(Paint paint) {
/* 1030 */     this.radiusGridlinePaint = paint;
/* 1031 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRadiusMinorGridlinesVisible() {
/* 1042 */     return this.radiusMinorGridlinesVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRadiusMinorGridlinesVisible(boolean flag) {
/* 1054 */     this.radiusMinorGridlinesVisible = flag;
/* 1055 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMargin() {
/* 1066 */     return this.margin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMargin(int margin) {
/* 1078 */     this.margin = margin;
/* 1079 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LegendItemCollection getFixedLegendItems() {
/* 1092 */     return this.fixedLegendItems;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 1107 */     this.fixedLegendItems = items;
/* 1108 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addCornerTextItem(String text) {
/* 1120 */     ParamChecks.nullNotPermitted(text, "text");
/* 1121 */     this.cornerTextItems.add(text);
/* 1122 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeCornerTextItem(String text) {
/* 1134 */     boolean removed = this.cornerTextItems.remove(text);
/* 1135 */     if (removed) {
/* 1136 */       fireChangeEvent();
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
/*      */   public void clearCornerTextItems() {
/* 1148 */     if (this.cornerTextItems.size() > 0) {
/* 1149 */       this.cornerTextItems.clear();
/* 1150 */       fireChangeEvent();
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
/*      */   protected List refreshAngleTicks() {
/* 1162 */     List<NumberTick> ticks = new ArrayList();
/* 1163 */     for (double currentTickVal = 0.0D; currentTickVal < 360.0D; 
/* 1164 */       currentTickVal += this.angleTickUnit.getSize()) {
/*      */       
/* 1166 */       TextAnchor ta = calculateTextAnchor(currentTickVal);
/*      */       
/* 1168 */       NumberTick tick = new NumberTick(new Double(currentTickVal), this.angleTickUnit.valueToString(currentTickVal), ta, TextAnchor.CENTER, 0.0D);
/*      */       
/* 1170 */       ticks.add(tick);
/*      */     } 
/* 1172 */     return ticks;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TextAnchor calculateTextAnchor(double angleDegrees) {
/* 1184 */     TextAnchor ta = TextAnchor.CENTER;
/*      */ 
/*      */     
/* 1187 */     double offset = this.angleOffset;
/* 1188 */     while (offset < 0.0D) {
/* 1189 */       offset += 360.0D;
/*      */     }
/* 1191 */     double normalizedAngle = ((this.counterClockwise ? -1 : true) * angleDegrees + offset) % 360.0D;
/*      */     
/* 1193 */     while (this.counterClockwise && normalizedAngle < 0.0D) {
/* 1194 */       normalizedAngle += 360.0D;
/*      */     }
/*      */     
/* 1197 */     if (normalizedAngle == 0.0D) {
/* 1198 */       ta = TextAnchor.CENTER_LEFT;
/*      */     }
/* 1200 */     else if (normalizedAngle > 0.0D && normalizedAngle < 90.0D) {
/* 1201 */       ta = TextAnchor.TOP_LEFT;
/*      */     }
/* 1203 */     else if (normalizedAngle == 90.0D) {
/* 1204 */       ta = TextAnchor.TOP_CENTER;
/*      */     }
/* 1206 */     else if (normalizedAngle > 90.0D && normalizedAngle < 180.0D) {
/* 1207 */       ta = TextAnchor.TOP_RIGHT;
/*      */     }
/* 1209 */     else if (normalizedAngle == 180.0D) {
/* 1210 */       ta = TextAnchor.CENTER_RIGHT;
/*      */     }
/* 1212 */     else if (normalizedAngle > 180.0D && normalizedAngle < 270.0D) {
/* 1213 */       ta = TextAnchor.BOTTOM_RIGHT;
/*      */     }
/* 1215 */     else if (normalizedAngle == 270.0D) {
/* 1216 */       ta = TextAnchor.BOTTOM_CENTER;
/*      */     }
/* 1218 */     else if (normalizedAngle > 270.0D && normalizedAngle < 360.0D) {
/* 1219 */       ta = TextAnchor.BOTTOM_LEFT;
/*      */     } 
/* 1221 */     return ta;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mapDatasetToAxis(int index, int axisIndex) {
/* 1234 */     List<Integer> axisIndices = new ArrayList(1);
/* 1235 */     axisIndices.add(new Integer(axisIndex));
/* 1236 */     mapDatasetToAxes(index, axisIndices);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mapDatasetToAxes(int index, List<?> axisIndices) {
/* 1250 */     if (index < 0) {
/* 1251 */       throw new IllegalArgumentException("Requires 'index' >= 0.");
/*      */     }
/* 1253 */     checkAxisIndices(axisIndices);
/* 1254 */     Integer key = new Integer(index);
/* 1255 */     this.datasetToAxesMap.put(key, new ArrayList(axisIndices));
/*      */     
/* 1257 */     datasetChanged(new DatasetChangeEvent(this, (Dataset)getDataset(index)));
/*      */   }
/*      */ 
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
/* 1270 */     if (indices == null) {
/*      */       return;
/*      */     }
/* 1273 */     int count = indices.size();
/* 1274 */     if (count == 0) {
/* 1275 */       throw new IllegalArgumentException("Empty list not permitted.");
/*      */     }
/* 1277 */     HashSet<Object> set = new HashSet();
/* 1278 */     for (int i = 0; i < count; i++) {
/* 1279 */       Object item = indices.get(i);
/* 1280 */       if (!(item instanceof Integer)) {
/* 1281 */         throw new IllegalArgumentException("Indices must be Integer instances.");
/*      */       }
/*      */       
/* 1284 */       if (set.contains(item)) {
/* 1285 */         throw new IllegalArgumentException("Indices must be unique.");
/*      */       }
/* 1287 */       set.add(item);
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
/*      */   public ValueAxis getAxisForDataset(int index) {
/*      */     ValueAxis valueAxis;
/* 1302 */     List<Integer> axisIndices = (List)this.datasetToAxesMap.get(new Integer(index));
/*      */     
/* 1304 */     if (axisIndices != null) {
/*      */       
/* 1306 */       Integer axisIndex = axisIndices.get(0);
/* 1307 */       valueAxis = getAxis(axisIndex.intValue());
/*      */     } else {
/*      */       
/* 1310 */       valueAxis = getAxis(0);
/*      */     } 
/* 1312 */     return valueAxis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAxisIndex(ValueAxis axis) {
/* 1325 */     int result = this.axes.indexOf(axis);
/* 1326 */     if (result < 0) {
/*      */       
/* 1328 */       Plot parent = getParent();
/* 1329 */       if (parent instanceof PolarPlot) {
/* 1330 */         PolarPlot p = (PolarPlot)parent;
/* 1331 */         result = p.getAxisIndex(axis);
/*      */       } 
/*      */     } 
/* 1334 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getIndexOf(PolarItemRenderer renderer) {
/* 1348 */     return this.renderers.indexOf(renderer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1376 */     boolean b1 = (area.getWidth() <= 10.0D);
/* 1377 */     boolean b2 = (area.getHeight() <= 10.0D);
/* 1378 */     if (b1 || b2) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1383 */     if (info != null) {
/* 1384 */       info.setPlotArea(area);
/*      */     }
/*      */ 
/*      */     
/* 1388 */     RectangleInsets insets = getInsets();
/* 1389 */     insets.trim(area);
/*      */     
/* 1391 */     Rectangle2D dataArea = area;
/* 1392 */     if (info != null) {
/* 1393 */       info.setDataArea(dataArea);
/*      */     }
/*      */ 
/*      */     
/* 1397 */     drawBackground(g2, dataArea);
/* 1398 */     int axisCount = this.axes.size();
/* 1399 */     AxisState state = null;
/* 1400 */     for (int i = 0; i < axisCount; i++) {
/* 1401 */       ValueAxis axis = getAxis(i);
/* 1402 */       if (axis != null) {
/*      */         
/* 1404 */         PolarAxisLocation location = (PolarAxisLocation)this.axisLocations.get(i);
/* 1405 */         AxisState s = drawAxis(axis, location, g2, dataArea);
/* 1406 */         if (i == 0) {
/* 1407 */           state = s;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1414 */     Shape originalClip = g2.getClip();
/* 1415 */     Composite originalComposite = g2.getComposite();
/*      */     
/* 1417 */     g2.clip(dataArea);
/* 1418 */     g2.setComposite(AlphaComposite.getInstance(3, 
/* 1419 */           getForegroundAlpha()));
/* 1420 */     this.angleTicks = refreshAngleTicks();
/* 1421 */     drawGridlines(g2, dataArea, this.angleTicks, state.getTicks());
/* 1422 */     render(g2, dataArea, info);
/* 1423 */     g2.setClip(originalClip);
/* 1424 */     g2.setComposite(originalComposite);
/* 1425 */     drawOutline(g2, dataArea);
/* 1426 */     drawCornerTextItems(g2, dataArea);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawCornerTextItems(Graphics2D g2, Rectangle2D area) {
/* 1436 */     if (this.cornerTextItems.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/* 1440 */     g2.setColor(Color.black);
/* 1441 */     double width = 0.0D;
/* 1442 */     double height = 0.0D;
/* 1443 */     for (Iterator<String> it = this.cornerTextItems.iterator(); it.hasNext(); ) {
/* 1444 */       String msg = it.next();
/* 1445 */       FontMetrics fm = g2.getFontMetrics();
/* 1446 */       Rectangle2D bounds = TextUtilities.getTextBounds(msg, g2, fm);
/* 1447 */       width = Math.max(width, bounds.getWidth());
/* 1448 */       height += bounds.getHeight();
/*      */     } 
/*      */     
/* 1451 */     double xadj = 14.0D;
/* 1452 */     double yadj = 7.0D;
/* 1453 */     width += xadj;
/* 1454 */     height += yadj;
/*      */     
/* 1456 */     double x = area.getMaxX() - width;
/* 1457 */     double y = area.getMaxY() - height;
/* 1458 */     g2.drawRect((int)x, (int)y, (int)width, (int)height);
/* 1459 */     x += 7.0D;
/* 1460 */     for (Iterator<String> iterator1 = this.cornerTextItems.iterator(); iterator1.hasNext(); ) {
/* 1461 */       String msg = iterator1.next();
/* 1462 */       Rectangle2D bounds = TextUtilities.getTextBounds(msg, g2, g2
/* 1463 */           .getFontMetrics());
/* 1464 */       y += bounds.getHeight();
/* 1465 */       g2.drawString(msg, (int)x, (int)y);
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
/*      */   protected AxisState drawAxis(ValueAxis axis, PolarAxisLocation location, Graphics2D g2, Rectangle2D plotArea) {
/* 1484 */     double centerX = plotArea.getCenterX();
/* 1485 */     double centerY = plotArea.getCenterY();
/* 1486 */     double r = Math.min(plotArea.getWidth() / 2.0D, plotArea
/* 1487 */         .getHeight() / 2.0D) - this.margin;
/* 1488 */     double x = centerX - r;
/* 1489 */     double y = centerY - r;
/*      */     
/* 1491 */     Rectangle2D dataArea = null;
/* 1492 */     AxisState result = null;
/* 1493 */     if (location == PolarAxisLocation.NORTH_RIGHT) {
/* 1494 */       dataArea = new Rectangle2D.Double(x, y, r, r);
/* 1495 */       result = axis.draw(g2, centerX, plotArea, dataArea, RectangleEdge.RIGHT, null);
/*      */     
/*      */     }
/* 1498 */     else if (location == PolarAxisLocation.NORTH_LEFT) {
/* 1499 */       dataArea = new Rectangle2D.Double(centerX, y, r, r);
/* 1500 */       result = axis.draw(g2, centerX, plotArea, dataArea, RectangleEdge.LEFT, null);
/*      */     
/*      */     }
/* 1503 */     else if (location == PolarAxisLocation.SOUTH_LEFT) {
/* 1504 */       dataArea = new Rectangle2D.Double(centerX, centerY, r, r);
/* 1505 */       result = axis.draw(g2, centerX, plotArea, dataArea, RectangleEdge.LEFT, null);
/*      */     
/*      */     }
/* 1508 */     else if (location == PolarAxisLocation.SOUTH_RIGHT) {
/* 1509 */       dataArea = new Rectangle2D.Double(x, centerY, r, r);
/* 1510 */       result = axis.draw(g2, centerX, plotArea, dataArea, RectangleEdge.RIGHT, null);
/*      */     
/*      */     }
/* 1513 */     else if (location == PolarAxisLocation.EAST_ABOVE) {
/* 1514 */       dataArea = new Rectangle2D.Double(centerX, centerY, r, r);
/* 1515 */       result = axis.draw(g2, centerY, plotArea, dataArea, RectangleEdge.TOP, null);
/*      */     
/*      */     }
/* 1518 */     else if (location == PolarAxisLocation.EAST_BELOW) {
/* 1519 */       dataArea = new Rectangle2D.Double(centerX, y, r, r);
/* 1520 */       result = axis.draw(g2, centerY, plotArea, dataArea, RectangleEdge.BOTTOM, null);
/*      */     
/*      */     }
/* 1523 */     else if (location == PolarAxisLocation.WEST_ABOVE) {
/* 1524 */       dataArea = new Rectangle2D.Double(x, centerY, r, r);
/* 1525 */       result = axis.draw(g2, centerY, plotArea, dataArea, RectangleEdge.TOP, null);
/*      */     
/*      */     }
/* 1528 */     else if (location == PolarAxisLocation.WEST_BELOW) {
/* 1529 */       dataArea = new Rectangle2D.Double(x, y, r, r);
/* 1530 */       result = axis.draw(g2, centerY, plotArea, dataArea, RectangleEdge.BOTTOM, null);
/*      */     } 
/*      */ 
/*      */     
/* 1534 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void render(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info) {
/* 1551 */     boolean hasData = false;
/* 1552 */     int datasetCount = this.datasets.size();
/* 1553 */     for (int i = datasetCount - 1; i >= 0; i--) {
/* 1554 */       XYDataset dataset = getDataset(i);
/* 1555 */       if (dataset != null) {
/*      */ 
/*      */         
/* 1558 */         PolarItemRenderer renderer = getRenderer(i);
/* 1559 */         if (renderer != null)
/*      */         {
/*      */           
/* 1562 */           if (!DatasetUtilities.isEmptyOrNull(dataset)) {
/* 1563 */             hasData = true;
/* 1564 */             int seriesCount = dataset.getSeriesCount();
/* 1565 */             for (int series = 0; series < seriesCount; series++)
/* 1566 */               renderer.drawSeries(g2, dataArea, info, this, dataset, series); 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 1571 */     if (!hasData) {
/* 1572 */       drawNoDataMessage(g2, dataArea);
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
/*      */   protected void drawGridlines(Graphics2D g2, Rectangle2D dataArea, List angularTicks, List radialTicks) {
/* 1587 */     PolarItemRenderer renderer = getRenderer();
/*      */     
/* 1589 */     if (renderer == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1594 */     if (isAngleGridlinesVisible()) {
/* 1595 */       Stroke gridStroke = getAngleGridlineStroke();
/* 1596 */       Paint gridPaint = getAngleGridlinePaint();
/* 1597 */       if (gridStroke != null && gridPaint != null) {
/* 1598 */         renderer.drawAngularGridLines(g2, this, angularTicks, dataArea);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1604 */     if (isRadiusGridlinesVisible()) {
/* 1605 */       Stroke gridStroke = getRadiusGridlineStroke();
/* 1606 */       Paint gridPaint = getRadiusGridlinePaint();
/* 1607 */       if (gridStroke != null && gridPaint != null) {
/* 1608 */         List ticks = buildRadialTicks(radialTicks);
/* 1609 */         renderer.drawRadialGridLines(g2, this, getAxis(), ticks, dataArea);
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
/*      */   protected List buildRadialTicks(List allTicks) {
/* 1626 */     List<ValueTick> ticks = new ArrayList();
/* 1627 */     Iterator<ValueTick> it = allTicks.iterator();
/* 1628 */     while (it.hasNext()) {
/* 1629 */       ValueTick tick = it.next();
/* 1630 */       if (isRadiusMinorGridlinesVisible() || TickType.MAJOR
/* 1631 */         .equals(tick.getTickType())) {
/* 1632 */         ticks.add(tick);
/*      */       }
/*      */     } 
/* 1635 */     return ticks;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoom(double percent) {
/* 1645 */     for (int axisIdx = 0; axisIdx < getAxisCount(); axisIdx++) {
/* 1646 */       ValueAxis axis = getAxis(axisIdx);
/* 1647 */       if (axis != null) {
/* 1648 */         if (percent > 0.0D) {
/* 1649 */           double radius = axis.getUpperBound();
/* 1650 */           double scaledRadius = radius * percent;
/* 1651 */           axis.setUpperBound(scaledRadius);
/* 1652 */           axis.setAutoRange(false);
/*      */         } else {
/*      */           
/* 1655 */           axis.setAutoRange(true);
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
/*      */   private List getDatasetsMappedToAxis(Integer axisIndex) {
/* 1672 */     ParamChecks.nullNotPermitted(axisIndex, "axisIndex");
/* 1673 */     List<Object> result = new ArrayList();
/* 1674 */     for (int i = 0; i < this.datasets.size(); i++) {
/* 1675 */       List mappedAxes = (List)this.datasetToAxesMap.get(new Integer(i));
/* 1676 */       if (mappedAxes == null) {
/* 1677 */         if (axisIndex.equals(ZERO)) {
/* 1678 */           result.add(this.datasets.get(i));
/*      */         
/*      */         }
/*      */       }
/* 1682 */       else if (mappedAxes.contains(axisIndex)) {
/* 1683 */         result.add(this.datasets.get(i));
/*      */       } 
/*      */     } 
/*      */     
/* 1687 */     return result;
/*      */   }
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
/* 1699 */     Range result = null;
/* 1700 */     int axisIdx = getAxisIndex(axis);
/* 1701 */     List mappedDatasets = new ArrayList();
/*      */     
/* 1703 */     if (axisIdx >= 0) {
/* 1704 */       mappedDatasets = getDatasetsMappedToAxis(new Integer(axisIdx));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1709 */     Iterator<XYDataset> iterator = mappedDatasets.iterator();
/* 1710 */     int datasetIdx = -1;
/* 1711 */     while (iterator.hasNext()) {
/* 1712 */       datasetIdx++;
/* 1713 */       XYDataset d = iterator.next();
/* 1714 */       if (d != null)
/*      */       {
/* 1716 */         result = Range.combine(result, 
/* 1717 */             DatasetUtilities.findRangeBounds(d));
/*      */       }
/*      */     } 
/*      */     
/* 1721 */     return result;
/*      */   }
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
/* 1733 */     for (int i = 0; i < this.axes.size(); i++) {
/* 1734 */       ValueAxis axis = (ValueAxis)this.axes.get(i);
/* 1735 */       if (axis != null) {
/* 1736 */         axis.configure();
/*      */       }
/*      */     } 
/* 1739 */     if (getParent() != null) {
/* 1740 */       getParent().datasetChanged(event);
/*      */     } else {
/*      */       
/* 1743 */       super.datasetChanged(event);
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
/* 1756 */     fireChangeEvent();
/*      */   }
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
/* 1768 */     if (this.fixedLegendItems != null) {
/* 1769 */       return this.fixedLegendItems;
/*      */     }
/* 1771 */     LegendItemCollection result = new LegendItemCollection();
/* 1772 */     int count = this.datasets.size();
/* 1773 */     for (int datasetIndex = 0; datasetIndex < count; datasetIndex++) {
/* 1774 */       XYDataset dataset = getDataset(datasetIndex);
/* 1775 */       PolarItemRenderer renderer = getRenderer(datasetIndex);
/* 1776 */       if (dataset != null && renderer != null) {
/* 1777 */         int seriesCount = dataset.getSeriesCount();
/* 1778 */         for (int i = 0; i < seriesCount; i++) {
/* 1779 */           LegendItem item = renderer.getLegendItem(i);
/* 1780 */           result.add(item);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1784 */     return result;
/*      */   }
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
/* 1796 */     if (obj == this) {
/* 1797 */       return true;
/*      */     }
/* 1799 */     if (!(obj instanceof PolarPlot)) {
/* 1800 */       return false;
/*      */     }
/* 1802 */     PolarPlot that = (PolarPlot)obj;
/* 1803 */     if (!this.axes.equals(that.axes)) {
/* 1804 */       return false;
/*      */     }
/* 1806 */     if (!this.axisLocations.equals(that.axisLocations)) {
/* 1807 */       return false;
/*      */     }
/* 1809 */     if (!this.renderers.equals(that.renderers)) {
/* 1810 */       return false;
/*      */     }
/* 1812 */     if (!this.angleTickUnit.equals(that.angleTickUnit)) {
/* 1813 */       return false;
/*      */     }
/* 1815 */     if (this.angleGridlinesVisible != that.angleGridlinesVisible) {
/* 1816 */       return false;
/*      */     }
/* 1818 */     if (this.angleOffset != that.angleOffset)
/*      */     {
/* 1820 */       return false;
/*      */     }
/* 1822 */     if (this.counterClockwise != that.counterClockwise)
/*      */     {
/* 1824 */       return false;
/*      */     }
/* 1826 */     if (this.angleLabelsVisible != that.angleLabelsVisible) {
/* 1827 */       return false;
/*      */     }
/* 1829 */     if (!this.angleLabelFont.equals(that.angleLabelFont)) {
/* 1830 */       return false;
/*      */     }
/* 1832 */     if (!PaintUtilities.equal(this.angleLabelPaint, that.angleLabelPaint)) {
/* 1833 */       return false;
/*      */     }
/* 1835 */     if (!ObjectUtilities.equal(this.angleGridlineStroke, that.angleGridlineStroke))
/*      */     {
/* 1837 */       return false;
/*      */     }
/* 1839 */     if (!PaintUtilities.equal(this.angleGridlinePaint, that.angleGridlinePaint))
/*      */     {
/*      */       
/* 1842 */       return false;
/*      */     }
/* 1844 */     if (this.radiusGridlinesVisible != that.radiusGridlinesVisible) {
/* 1845 */       return false;
/*      */     }
/* 1847 */     if (!ObjectUtilities.equal(this.radiusGridlineStroke, that.radiusGridlineStroke))
/*      */     {
/* 1849 */       return false;
/*      */     }
/* 1851 */     if (!PaintUtilities.equal(this.radiusGridlinePaint, that.radiusGridlinePaint))
/*      */     {
/* 1853 */       return false;
/*      */     }
/* 1855 */     if (this.radiusMinorGridlinesVisible != that.radiusMinorGridlinesVisible)
/*      */     {
/* 1857 */       return false;
/*      */     }
/* 1859 */     if (!this.cornerTextItems.equals(that.cornerTextItems)) {
/* 1860 */       return false;
/*      */     }
/* 1862 */     if (this.margin != that.margin) {
/* 1863 */       return false;
/*      */     }
/* 1865 */     if (!ObjectUtilities.equal(this.fixedLegendItems, that.fixedLegendItems))
/*      */     {
/* 1867 */       return false;
/*      */     }
/* 1869 */     return super.equals(obj);
/*      */   }
/*      */ 
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
/* 1882 */     PolarPlot clone = (PolarPlot)super.clone();
/* 1883 */     clone.axes = (ObjectList)ObjectUtilities.clone(this.axes); int i;
/* 1884 */     for (i = 0; i < this.axes.size(); i++) {
/* 1885 */       ValueAxis axis = (ValueAxis)this.axes.get(i);
/* 1886 */       if (axis != null) {
/* 1887 */         ValueAxis clonedAxis = (ValueAxis)axis.clone();
/* 1888 */         clone.axes.set(i, clonedAxis);
/* 1889 */         clonedAxis.setPlot(clone);
/* 1890 */         clonedAxis.addChangeListener(clone);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1895 */     clone.datasets = (ObjectList)ObjectUtilities.clone(this.datasets);
/* 1896 */     for (i = 0; i < clone.datasets.size(); i++) {
/* 1897 */       XYDataset d = getDataset(i);
/* 1898 */       if (d != null) {
/* 1899 */         d.addChangeListener(clone);
/*      */       }
/*      */     } 
/*      */     
/* 1903 */     clone.renderers = (ObjectList)ObjectUtilities.clone(this.renderers);
/* 1904 */     for (i = 0; i < this.renderers.size(); i++) {
/* 1905 */       PolarItemRenderer renderer2 = (PolarItemRenderer)this.renderers.get(i);
/* 1906 */       if (renderer2 instanceof PublicCloneable) {
/* 1907 */         PublicCloneable pc = (PublicCloneable)renderer2;
/* 1908 */         PolarItemRenderer rc = (PolarItemRenderer)pc.clone();
/* 1909 */         clone.renderers.set(i, rc);
/* 1910 */         rc.setPlot(clone);
/* 1911 */         rc.addChangeListener(clone);
/*      */       } 
/*      */     } 
/*      */     
/* 1915 */     clone.cornerTextItems = new ArrayList(this.cornerTextItems);
/*      */     
/* 1917 */     return clone;
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
/* 1928 */     stream.defaultWriteObject();
/* 1929 */     SerialUtilities.writeStroke(this.angleGridlineStroke, stream);
/* 1930 */     SerialUtilities.writePaint(this.angleGridlinePaint, stream);
/* 1931 */     SerialUtilities.writeStroke(this.radiusGridlineStroke, stream);
/* 1932 */     SerialUtilities.writePaint(this.radiusGridlinePaint, stream);
/* 1933 */     SerialUtilities.writePaint(this.angleLabelPaint, stream);
/*      */   }
/*      */ 
/*      */ 
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
/* 1947 */     stream.defaultReadObject();
/* 1948 */     this.angleGridlineStroke = SerialUtilities.readStroke(stream);
/* 1949 */     this.angleGridlinePaint = SerialUtilities.readPaint(stream);
/* 1950 */     this.radiusGridlineStroke = SerialUtilities.readStroke(stream);
/* 1951 */     this.radiusGridlinePaint = SerialUtilities.readPaint(stream);
/* 1952 */     this.angleLabelPaint = SerialUtilities.readPaint(stream);
/*      */     
/* 1954 */     int rangeAxisCount = this.axes.size();
/* 1955 */     for (int i = 0; i < rangeAxisCount; i++) {
/* 1956 */       Axis axis = (Axis)this.axes.get(i);
/* 1957 */       if (axis != null) {
/* 1958 */         axis.setPlot(this);
/* 1959 */         axis.addChangeListener(this);
/*      */       } 
/*      */     } 
/* 1962 */     int datasetCount = this.datasets.size();
/* 1963 */     for (int j = 0; j < datasetCount; j++) {
/* 1964 */       Dataset dataset = (Dataset)this.datasets.get(j);
/* 1965 */       if (dataset != null) {
/* 1966 */         dataset.addChangeListener(this);
/*      */       }
/*      */     } 
/* 1969 */     int rendererCount = this.renderers.size();
/* 1970 */     for (int k = 0; k < rendererCount; k++) {
/* 1971 */       PolarItemRenderer renderer = (PolarItemRenderer)this.renderers.get(k);
/* 1972 */       if (renderer != null) {
/* 1973 */         renderer.addChangeListener(this);
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
/*      */   public void zoomDomainAxes(double factor, PlotRenderingInfo state, Point2D source, boolean useAnchor) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public void zoomRangeAxes(double factor, PlotRenderingInfo state, Point2D source) {
/* 2034 */     zoom(factor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2054 */     double sourceX = source.getX();
/*      */     
/* 2056 */     for (int axisIdx = 0; axisIdx < getAxisCount(); axisIdx++) {
/* 2057 */       ValueAxis axis = getAxis(axisIdx);
/* 2058 */       if (axis != null) {
/* 2059 */         if (useAnchor) {
/* 2060 */           double anchorX = axis.java2DToValue(sourceX, info
/* 2061 */               .getDataArea(), RectangleEdge.BOTTOM);
/* 2062 */           axis.resizeRange(factor, anchorX);
/*      */         } else {
/*      */           
/* 2065 */           axis.resizeRange(factor);
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
/*      */   public void zoomRangeAxes(double lowerPercent, double upperPercent, PlotRenderingInfo state, Point2D source) {
/* 2082 */     zoom((upperPercent + lowerPercent) / 2.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDomainZoomable() {
/* 2092 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangeZoomable() {
/* 2102 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlotOrientation getOrientation() {
/* 2112 */     return PlotOrientation.HORIZONTAL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point translateToJava2D(double angleDegrees, double radius, ValueAxis axis, Rectangle2D dataArea) {
/* 2132 */     if (this.counterClockwise) {
/* 2133 */       angleDegrees = -angleDegrees;
/*      */     }
/* 2135 */     double radians = Math.toRadians(angleDegrees + this.angleOffset);
/*      */     
/* 2137 */     double minx = dataArea.getMinX() + this.margin;
/* 2138 */     double maxx = dataArea.getMaxX() - this.margin;
/* 2139 */     double miny = dataArea.getMinY() + this.margin;
/* 2140 */     double maxy = dataArea.getMaxY() - this.margin;
/*      */     
/* 2142 */     double halfWidth = (maxx - minx) / 2.0D;
/* 2143 */     double halfHeight = (maxy - miny) / 2.0D;
/*      */     
/* 2145 */     double midX = minx + halfWidth;
/* 2146 */     double midY = miny + halfHeight;
/*      */     
/* 2148 */     double l = Math.min(halfWidth, halfHeight);
/* 2149 */     Rectangle2D quadrant = new Rectangle2D.Double(midX, midY, l, l);
/*      */     
/* 2151 */     double axisMin = axis.getLowerBound();
/* 2152 */     double adjustedRadius = Math.max(radius, axisMin);
/*      */     
/* 2154 */     double length = axis.valueToJava2D(adjustedRadius, quadrant, RectangleEdge.BOTTOM) - midX;
/* 2155 */     float x = (float)(midX + Math.cos(radians) * length);
/* 2156 */     float y = (float)(midY + Math.sin(radians) * length);
/*      */     
/* 2158 */     int ix = Math.round(x);
/* 2159 */     int iy = Math.round(y);
/*      */     
/* 2161 */     Point p = new Point(ix, iy);
/* 2162 */     return p;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point translateValueThetaRadiusToJava2D(double angleDegrees, double radius, Rectangle2D dataArea) {
/* 2183 */     return translateToJava2D(angleDegrees, radius, getAxis(), dataArea);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMaxRadius() {
/* 2195 */     return getAxis().getUpperBound();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSeriesCount() {
/* 2208 */     int result = 0;
/* 2209 */     XYDataset dataset = getDataset(0);
/* 2210 */     if (dataset != null) {
/* 2211 */       result = dataset.getSeriesCount();
/*      */     }
/* 2213 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AxisState drawAxis(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea) {
/* 2229 */     return getAxis().draw(g2, dataArea.getMinY(), plotArea, dataArea, RectangleEdge.TOP, null);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/PolarPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */