/*      */ package org.jfree.chart.plot;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.LineMetrics;
/*      */ import java.awt.geom.Arc2D;
/*      */ import java.awt.geom.Ellipse2D;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.jfree.chart.LegendItem;
/*      */ import org.jfree.chart.LegendItemCollection;
/*      */ import org.jfree.chart.entity.CategoryItemEntity;
/*      */ import org.jfree.chart.entity.ChartEntity;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.labels.CategoryItemLabelGenerator;
/*      */ import org.jfree.chart.labels.CategoryToolTipGenerator;
/*      */ import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
/*      */ import org.jfree.chart.urls.CategoryURLGenerator;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.category.CategoryDataset;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.general.DatasetChangeEvent;
/*      */ import org.jfree.data.general.DatasetUtilities;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ import org.jfree.util.ObjectUtilities;
/*      */ import org.jfree.util.PaintList;
/*      */ import org.jfree.util.PaintUtilities;
/*      */ import org.jfree.util.Rotation;
/*      */ import org.jfree.util.ShapeUtilities;
/*      */ import org.jfree.util.StrokeList;
/*      */ import org.jfree.util.TableOrder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SpiderWebPlot
/*      */   extends Plot
/*      */   implements Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -5376340422031599463L;
/*      */   public static final double DEFAULT_HEAD = 0.01D;
/*      */   public static final double DEFAULT_AXIS_LABEL_GAP = 0.1D;
/*      */   public static final double DEFAULT_INTERIOR_GAP = 0.25D;
/*      */   public static final double MAX_INTERIOR_GAP = 0.4D;
/*      */   public static final double DEFAULT_START_ANGLE = 90.0D;
/*  148 */   public static final Font DEFAULT_LABEL_FONT = new Font("SansSerif", 0, 10);
/*      */ 
/*      */ 
/*      */   
/*  152 */   public static final Paint DEFAULT_LABEL_PAINT = Color.black;
/*      */ 
/*      */   
/*  155 */   public static final Paint DEFAULT_LABEL_BACKGROUND_PAINT = new Color(255, 255, 192);
/*      */ 
/*      */ 
/*      */   
/*  159 */   public static final Paint DEFAULT_LABEL_OUTLINE_PAINT = Color.black;
/*      */ 
/*      */   
/*  162 */   public static final Stroke DEFAULT_LABEL_OUTLINE_STROKE = new BasicStroke(0.5F);
/*      */ 
/*      */ 
/*      */   
/*  166 */   public static final Paint DEFAULT_LABEL_SHADOW_PAINT = Color.lightGray;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final double DEFAULT_MAX_VALUE = -1.0D;
/*      */ 
/*      */ 
/*      */   
/*      */   protected double headPercent;
/*      */ 
/*      */ 
/*      */   
/*      */   private double interiorGap;
/*      */ 
/*      */ 
/*      */   
/*      */   private double axisLabelGap;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint axisLinePaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke axisLineStroke;
/*      */ 
/*      */ 
/*      */   
/*      */   private CategoryDataset dataset;
/*      */ 
/*      */ 
/*      */   
/*      */   private double maxValue;
/*      */ 
/*      */ 
/*      */   
/*      */   private TableOrder dataExtractOrder;
/*      */ 
/*      */ 
/*      */   
/*      */   private double startAngle;
/*      */ 
/*      */ 
/*      */   
/*      */   private Rotation direction;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Shape legendItemShape;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint seriesPaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private PaintList seriesPaintList;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint baseSeriesPaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint seriesOutlinePaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private PaintList seriesOutlinePaintList;
/*      */ 
/*      */   
/*      */   private transient Paint baseSeriesOutlinePaint;
/*      */ 
/*      */   
/*      */   private transient Stroke seriesOutlineStroke;
/*      */ 
/*      */   
/*      */   private StrokeList seriesOutlineStrokeList;
/*      */ 
/*      */   
/*      */   private transient Stroke baseSeriesOutlineStroke;
/*      */ 
/*      */   
/*      */   private Font labelFont;
/*      */ 
/*      */   
/*      */   private transient Paint labelPaint;
/*      */ 
/*      */   
/*      */   private CategoryItemLabelGenerator labelGenerator;
/*      */ 
/*      */   
/*      */   private boolean webFilled = true;
/*      */ 
/*      */   
/*      */   private CategoryToolTipGenerator toolTipGenerator;
/*      */ 
/*      */   
/*      */   private CategoryURLGenerator urlGenerator;
/*      */ 
/*      */ 
/*      */   
/*      */   public SpiderWebPlot() {
/*  269 */     this((CategoryDataset)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SpiderWebPlot(CategoryDataset dataset) {
/*  279 */     this(dataset, TableOrder.BY_ROW);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SpiderWebPlot(CategoryDataset dataset, TableOrder extract) {
/*  291 */     ParamChecks.nullNotPermitted(extract, "extract");
/*  292 */     this.dataset = dataset;
/*  293 */     if (dataset != null) {
/*  294 */       dataset.addChangeListener(this);
/*      */     }
/*      */     
/*  297 */     this.dataExtractOrder = extract;
/*  298 */     this.headPercent = 0.01D;
/*  299 */     this.axisLabelGap = 0.1D;
/*  300 */     this.axisLinePaint = Color.black;
/*  301 */     this.axisLineStroke = new BasicStroke(1.0F);
/*      */     
/*  303 */     this.interiorGap = 0.25D;
/*  304 */     this.startAngle = 90.0D;
/*  305 */     this.direction = Rotation.CLOCKWISE;
/*  306 */     this.maxValue = -1.0D;
/*      */     
/*  308 */     this.seriesPaint = null;
/*  309 */     this.seriesPaintList = new PaintList();
/*  310 */     this.baseSeriesPaint = null;
/*      */     
/*  312 */     this.seriesOutlinePaint = null;
/*  313 */     this.seriesOutlinePaintList = new PaintList();
/*  314 */     this.baseSeriesOutlinePaint = DEFAULT_OUTLINE_PAINT;
/*      */     
/*  316 */     this.seriesOutlineStroke = null;
/*  317 */     this.seriesOutlineStrokeList = new StrokeList();
/*  318 */     this.baseSeriesOutlineStroke = DEFAULT_OUTLINE_STROKE;
/*      */     
/*  320 */     this.labelFont = DEFAULT_LABEL_FONT;
/*  321 */     this.labelPaint = DEFAULT_LABEL_PAINT;
/*  322 */     this.labelGenerator = (CategoryItemLabelGenerator)new StandardCategoryItemLabelGenerator();
/*      */     
/*  324 */     this.legendItemShape = DEFAULT_LEGEND_ITEM_CIRCLE;
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
/*  335 */     return "Spider Web Plot";
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
/*  346 */     return this.dataset;
/*      */   }
/*      */ 
/*      */ 
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
/*  360 */     if (this.dataset != null) {
/*  361 */       this.dataset.removeChangeListener(this);
/*      */     }
/*      */ 
/*      */     
/*  365 */     this.dataset = dataset;
/*  366 */     if (dataset != null) {
/*  367 */       setDatasetGroup(dataset.getGroup());
/*  368 */       dataset.addChangeListener(this);
/*      */     } 
/*      */ 
/*      */     
/*  372 */     datasetChanged(new DatasetChangeEvent(this, (Dataset)dataset));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWebFilled() {
/*  383 */     return this.webFilled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWebFilled(boolean flag) {
/*  395 */     this.webFilled = flag;
/*  396 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TableOrder getDataExtractOrder() {
/*  407 */     return this.dataExtractOrder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataExtractOrder(TableOrder order) {
/*  422 */     ParamChecks.nullNotPermitted(order, "order");
/*  423 */     this.dataExtractOrder = order;
/*  424 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getHeadPercent() {
/*  435 */     return this.headPercent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHeadPercent(double percent) {
/*  447 */     this.headPercent = percent;
/*  448 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getStartAngle() {
/*  462 */     return this.startAngle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStartAngle(double angle) {
/*  478 */     this.startAngle = angle;
/*  479 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMaxValue() {
/*  490 */     return this.maxValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxValue(double value) {
/*  502 */     this.maxValue = value;
/*  503 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rotation getDirection() {
/*  515 */     return this.direction;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDirection(Rotation direction) {
/*  527 */     ParamChecks.nullNotPermitted(direction, "direction");
/*  528 */     this.direction = direction;
/*  529 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getInteriorGap() {
/*  541 */     return this.interiorGap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInteriorGap(double percent) {
/*  554 */     if (percent < 0.0D || percent > 0.4D) {
/*  555 */       throw new IllegalArgumentException("Percentage outside valid range.");
/*      */     }
/*      */     
/*  558 */     if (this.interiorGap != percent) {
/*  559 */       this.interiorGap = percent;
/*  560 */       fireChangeEvent();
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
/*      */   public double getAxisLabelGap() {
/*  572 */     return this.axisLabelGap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxisLabelGap(double gap) {
/*  584 */     this.axisLabelGap = gap;
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
/*      */   public Paint getAxisLinePaint() {
/*  598 */     return this.axisLinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxisLinePaint(Paint paint) {
/*  611 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  612 */     this.axisLinePaint = paint;
/*  613 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getAxisLineStroke() {
/*  626 */     return this.axisLineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxisLineStroke(Stroke stroke) {
/*  639 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/*  640 */     this.axisLineStroke = stroke;
/*  641 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getSeriesPaint() {
/*  654 */     return this.seriesPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeriesPaint(Paint paint) {
/*  667 */     this.seriesPaint = paint;
/*  668 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getSeriesPaint(int series) {
/*  683 */     if (this.seriesPaint != null) {
/*  684 */       return this.seriesPaint;
/*      */     }
/*      */ 
/*      */     
/*  688 */     Paint result = this.seriesPaintList.getPaint(series);
/*  689 */     if (result == null) {
/*  690 */       DrawingSupplier supplier = getDrawingSupplier();
/*  691 */       if (supplier != null) {
/*  692 */         Paint p = supplier.getNextPaint();
/*  693 */         this.seriesPaintList.setPaint(series, p);
/*  694 */         result = p;
/*      */       } else {
/*      */         
/*  697 */         result = this.baseSeriesPaint;
/*      */       } 
/*      */     } 
/*  700 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeriesPaint(int series, Paint paint) {
/*  714 */     this.seriesPaintList.setPaint(series, paint);
/*  715 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getBaseSeriesPaint() {
/*  727 */     return this.baseSeriesPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseSeriesPaint(Paint paint) {
/*  738 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  739 */     this.baseSeriesPaint = paint;
/*  740 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getSeriesOutlinePaint() {
/*  751 */     return this.seriesOutlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeriesOutlinePaint(Paint paint) {
/*  762 */     this.seriesOutlinePaint = paint;
/*  763 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getSeriesOutlinePaint(int series) {
/*  775 */     if (this.seriesOutlinePaint != null) {
/*  776 */       return this.seriesOutlinePaint;
/*      */     }
/*      */     
/*  779 */     Paint result = this.seriesOutlinePaintList.getPaint(series);
/*  780 */     if (result == null) {
/*  781 */       result = this.baseSeriesOutlinePaint;
/*      */     }
/*  783 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeriesOutlinePaint(int series, Paint paint) {
/*  794 */     this.seriesOutlinePaintList.setPaint(series, paint);
/*  795 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getBaseSeriesOutlinePaint() {
/*  805 */     return this.baseSeriesOutlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseSeriesOutlinePaint(Paint paint) {
/*  814 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  815 */     this.baseSeriesOutlinePaint = paint;
/*  816 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getSeriesOutlineStroke() {
/*  827 */     return this.seriesOutlineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeriesOutlineStroke(Stroke stroke) {
/*  838 */     this.seriesOutlineStroke = stroke;
/*  839 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getSeriesOutlineStroke(int series) {
/*  852 */     if (this.seriesOutlineStroke != null) {
/*  853 */       return this.seriesOutlineStroke;
/*      */     }
/*      */ 
/*      */     
/*  857 */     Stroke result = this.seriesOutlineStrokeList.getStroke(series);
/*  858 */     if (result == null) {
/*  859 */       result = this.baseSeriesOutlineStroke;
/*      */     }
/*  861 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeriesOutlineStroke(int series, Stroke stroke) {
/*  873 */     this.seriesOutlineStrokeList.setStroke(series, stroke);
/*  874 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getBaseSeriesOutlineStroke() {
/*  884 */     return this.baseSeriesOutlineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseSeriesOutlineStroke(Stroke stroke) {
/*  893 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/*  894 */     this.baseSeriesOutlineStroke = stroke;
/*  895 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape getLegendItemShape() {
/*  906 */     return this.legendItemShape;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegendItemShape(Shape shape) {
/*  918 */     ParamChecks.nullNotPermitted(shape, "shape");
/*  919 */     this.legendItemShape = shape;
/*  920 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getLabelFont() {
/*  931 */     return this.labelFont;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelFont(Font font) {
/*  943 */     ParamChecks.nullNotPermitted(font, "font");
/*  944 */     this.labelFont = font;
/*  945 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getLabelPaint() {
/*  956 */     return this.labelPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelPaint(Paint paint) {
/*  968 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  969 */     this.labelPaint = paint;
/*  970 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryItemLabelGenerator getLabelGenerator() {
/*  981 */     return this.labelGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelGenerator(CategoryItemLabelGenerator generator) {
/*  993 */     ParamChecks.nullNotPermitted(generator, "generator");
/*  994 */     this.labelGenerator = generator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryToolTipGenerator getToolTipGenerator() {
/* 1007 */     return this.toolTipGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToolTipGenerator(CategoryToolTipGenerator generator) {
/* 1021 */     this.toolTipGenerator = generator;
/* 1022 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryURLGenerator getURLGenerator() {
/* 1035 */     return this.urlGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setURLGenerator(CategoryURLGenerator generator) {
/* 1049 */     this.urlGenerator = generator;
/* 1050 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LegendItemCollection getLegendItems() {
/* 1060 */     LegendItemCollection result = new LegendItemCollection();
/* 1061 */     if (getDataset() == null) {
/* 1062 */       return result;
/*      */     }
/* 1064 */     List keys = null;
/* 1065 */     if (this.dataExtractOrder == TableOrder.BY_ROW) {
/* 1066 */       keys = this.dataset.getRowKeys();
/*      */     }
/* 1068 */     else if (this.dataExtractOrder == TableOrder.BY_COLUMN) {
/* 1069 */       keys = this.dataset.getColumnKeys();
/*      */     } 
/* 1071 */     if (keys == null) {
/* 1072 */       return result;
/*      */     }
/*      */     
/* 1075 */     int series = 0;
/* 1076 */     Iterator<Comparable> iterator = keys.iterator();
/* 1077 */     Shape shape = getLegendItemShape();
/* 1078 */     while (iterator.hasNext()) {
/* 1079 */       Comparable key = iterator.next();
/* 1080 */       String label = key.toString();
/* 1081 */       String description = label;
/* 1082 */       Paint paint = getSeriesPaint(series);
/* 1083 */       Paint outlinePaint = getSeriesOutlinePaint(series);
/* 1084 */       Stroke stroke = getSeriesOutlineStroke(series);
/* 1085 */       LegendItem item = new LegendItem(label, description, null, null, shape, paint, stroke, outlinePaint);
/*      */       
/* 1087 */       item.setDataset((Dataset)getDataset());
/* 1088 */       item.setSeriesKey(key);
/* 1089 */       item.setSeriesIndex(series);
/* 1090 */       result.add(item);
/* 1091 */       series++;
/*      */     } 
/* 1093 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Point2D getWebPoint(Rectangle2D bounds, double angle, double length) {
/* 1108 */     double angrad = Math.toRadians(angle);
/* 1109 */     double x = Math.cos(angrad) * length * bounds.getWidth() / 2.0D;
/* 1110 */     double y = -Math.sin(angrad) * length * bounds.getHeight() / 2.0D;
/*      */     
/* 1112 */     return new Point2D.Double(bounds.getX() + x + bounds.getWidth() / 2.0D, bounds
/* 1113 */         .getY() + y + bounds.getHeight() / 2.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1131 */     RectangleInsets insets = getInsets();
/* 1132 */     insets.trim(area);
/*      */     
/* 1134 */     if (info != null) {
/* 1135 */       info.setPlotArea(area);
/* 1136 */       info.setDataArea(area);
/*      */     } 
/*      */     
/* 1139 */     drawBackground(g2, area);
/* 1140 */     drawOutline(g2, area);
/*      */     
/* 1142 */     Shape savedClip = g2.getClip();
/*      */     
/* 1144 */     g2.clip(area);
/* 1145 */     Composite originalComposite = g2.getComposite();
/* 1146 */     g2.setComposite(AlphaComposite.getInstance(3, 
/* 1147 */           getForegroundAlpha()));
/*      */     
/* 1149 */     if (!DatasetUtilities.isEmptyOrNull(this.dataset)) {
/*      */       int seriesCount, catCount;
/*      */       
/* 1152 */       if (this.dataExtractOrder == TableOrder.BY_ROW) {
/* 1153 */         seriesCount = this.dataset.getRowCount();
/* 1154 */         catCount = this.dataset.getColumnCount();
/*      */       } else {
/*      */         
/* 1157 */         seriesCount = this.dataset.getColumnCount();
/* 1158 */         catCount = this.dataset.getRowCount();
/*      */       } 
/*      */ 
/*      */       
/* 1162 */       if (this.maxValue == -1.0D) {
/* 1163 */         calculateMaxValue(seriesCount, catCount);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1170 */       double gapHorizontal = area.getWidth() * getInteriorGap();
/* 1171 */       double gapVertical = area.getHeight() * getInteriorGap();
/*      */       
/* 1173 */       double X = area.getX() + gapHorizontal / 2.0D;
/* 1174 */       double Y = area.getY() + gapVertical / 2.0D;
/* 1175 */       double W = area.getWidth() - gapHorizontal;
/* 1176 */       double H = area.getHeight() - gapVertical;
/*      */       
/* 1178 */       double headW = area.getWidth() * this.headPercent;
/* 1179 */       double headH = area.getHeight() * this.headPercent;
/*      */ 
/*      */       
/* 1182 */       double min = Math.min(W, H) / 2.0D;
/* 1183 */       X = (X + X + W) / 2.0D - min;
/* 1184 */       Y = (Y + Y + H) / 2.0D - min;
/* 1185 */       W = 2.0D * min;
/* 1186 */       H = 2.0D * min;
/*      */       
/* 1188 */       Point2D centre = new Point2D.Double(X + W / 2.0D, Y + H / 2.0D);
/* 1189 */       Rectangle2D radarArea = new Rectangle2D.Double(X, Y, W, H);
/*      */ 
/*      */       
/* 1192 */       for (int cat = 0; cat < catCount; cat++) {
/*      */         
/* 1194 */         double angle = getStartAngle() + getDirection().getFactor() * cat * 360.0D / catCount;
/*      */         
/* 1196 */         Point2D endPoint = getWebPoint(radarArea, angle, 1.0D);
/*      */         
/* 1198 */         Line2D line = new Line2D.Double(centre, endPoint);
/* 1199 */         g2.setPaint(this.axisLinePaint);
/* 1200 */         g2.setStroke(this.axisLineStroke);
/* 1201 */         g2.draw(line);
/* 1202 */         drawLabel(g2, radarArea, 0.0D, cat, angle, 360.0D / catCount);
/*      */       } 
/*      */ 
/*      */       
/* 1206 */       for (int series = 0; series < seriesCount; series++) {
/* 1207 */         drawRadarPoly(g2, radarArea, centre, info, series, catCount, headH, headW);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1212 */       drawNoDataMessage(g2, area);
/*      */     } 
/* 1214 */     g2.setClip(savedClip);
/* 1215 */     g2.setComposite(originalComposite);
/* 1216 */     drawOutline(g2, area);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void calculateMaxValue(int seriesCount, int catCount) {
/* 1230 */     for (int seriesIndex = 0; seriesIndex < seriesCount; seriesIndex++) {
/* 1231 */       for (int catIndex = 0; catIndex < catCount; catIndex++) {
/* 1232 */         Number nV = getPlotValue(seriesIndex, catIndex);
/* 1233 */         if (nV != null) {
/* 1234 */           double v = nV.doubleValue();
/* 1235 */           if (v > this.maxValue) {
/* 1236 */             this.maxValue = v;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawRadarPoly(Graphics2D g2, Rectangle2D plotArea, Point2D centre, PlotRenderingInfo info, int series, int catCount, double headH, double headW) {
/* 1262 */     Polygon polygon = new Polygon();
/*      */     
/* 1264 */     EntityCollection entities = null;
/* 1265 */     if (info != null) {
/* 1266 */       entities = info.getOwner().getEntityCollection();
/*      */     }
/*      */ 
/*      */     
/* 1270 */     for (int cat = 0; cat < catCount; cat++) {
/*      */       
/* 1272 */       Number dataValue = getPlotValue(series, cat);
/*      */       
/* 1274 */       if (dataValue != null) {
/* 1275 */         double value = dataValue.doubleValue();
/*      */         
/* 1277 */         if (value >= 0.0D) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1282 */           double angle = getStartAngle() + getDirection().getFactor() * cat * 360.0D / catCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1295 */           Point2D point = getWebPoint(plotArea, angle, value / this.maxValue);
/*      */           
/* 1297 */           polygon.addPoint((int)point.getX(), (int)point.getY());
/*      */ 
/*      */ 
/*      */           
/* 1301 */           Paint paint1 = getSeriesPaint(series);
/* 1302 */           Paint outlinePaint = getSeriesOutlinePaint(series);
/* 1303 */           Stroke outlineStroke = getSeriesOutlineStroke(series);
/*      */ 
/*      */           
/* 1306 */           Ellipse2D head = new Ellipse2D.Double(point.getX() - headW / 2.0D, point.getY() - headH / 2.0D, headW, headH);
/*      */           
/* 1308 */           g2.setPaint(paint1);
/* 1309 */           g2.fill(head);
/* 1310 */           g2.setStroke(outlineStroke);
/* 1311 */           g2.setPaint(outlinePaint);
/* 1312 */           g2.draw(head);
/*      */           
/* 1314 */           if (entities != null) {
/*      */             int row, col;
/* 1316 */             if (this.dataExtractOrder == TableOrder.BY_ROW) {
/* 1317 */               row = series;
/* 1318 */               col = cat;
/*      */             } else {
/*      */               
/* 1321 */               row = cat;
/* 1322 */               col = series;
/*      */             } 
/* 1324 */             String tip = null;
/* 1325 */             if (this.toolTipGenerator != null) {
/* 1326 */               tip = this.toolTipGenerator.generateToolTip(this.dataset, row, col);
/*      */             }
/*      */ 
/*      */             
/* 1330 */             String url = null;
/* 1331 */             if (this.urlGenerator != null) {
/* 1332 */               url = this.urlGenerator.generateURL(this.dataset, row, col);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1338 */             Shape area = new Rectangle((int)(point.getX() - headW), (int)(point.getY() - headH), (int)(headW * 2.0D), (int)(headH * 2.0D));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1343 */             CategoryItemEntity entity = new CategoryItemEntity(area, tip, url, this.dataset, this.dataset.getRowKey(row), this.dataset.getColumnKey(col));
/* 1344 */             entities.add((ChartEntity)entity);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1352 */     Paint paint = getSeriesPaint(series);
/* 1353 */     g2.setPaint(paint);
/* 1354 */     g2.setStroke(getSeriesOutlineStroke(series));
/* 1355 */     g2.draw(polygon);
/*      */ 
/*      */ 
/*      */     
/* 1359 */     if (this.webFilled) {
/* 1360 */       g2.setComposite(AlphaComposite.getInstance(3, 0.1F));
/*      */       
/* 1362 */       g2.fill(polygon);
/* 1363 */       g2.setComposite(AlphaComposite.getInstance(3, 
/* 1364 */             getForegroundAlpha()));
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
/*      */   protected Number getPlotValue(int series, int cat) {
/* 1383 */     Number value = null;
/* 1384 */     if (this.dataExtractOrder == TableOrder.BY_ROW) {
/* 1385 */       value = this.dataset.getValue(series, cat);
/*      */     }
/* 1387 */     else if (this.dataExtractOrder == TableOrder.BY_COLUMN) {
/* 1388 */       value = this.dataset.getValue(cat, series);
/*      */     } 
/* 1390 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawLabel(Graphics2D g2, Rectangle2D plotArea, double value, int cat, double startAngle, double extent) {
/*      */     String label;
/* 1405 */     FontRenderContext frc = g2.getFontRenderContext();
/*      */ 
/*      */     
/* 1408 */     if (this.dataExtractOrder == TableOrder.BY_ROW) {
/*      */       
/* 1410 */       label = this.labelGenerator.generateColumnLabel(this.dataset, cat);
/*      */     }
/*      */     else {
/*      */       
/* 1414 */       label = this.labelGenerator.generateRowLabel(this.dataset, cat);
/*      */     } 
/*      */     
/* 1417 */     Rectangle2D labelBounds = getLabelFont().getStringBounds(label, frc);
/* 1418 */     LineMetrics lm = getLabelFont().getLineMetrics(label, frc);
/* 1419 */     double ascent = lm.getAscent();
/*      */     
/* 1421 */     Point2D labelLocation = calculateLabelLocation(labelBounds, ascent, plotArea, startAngle);
/*      */ 
/*      */     
/* 1424 */     Composite saveComposite = g2.getComposite();
/*      */     
/* 1426 */     g2.setComposite(AlphaComposite.getInstance(3, 1.0F));
/*      */     
/* 1428 */     g2.setPaint(getLabelPaint());
/* 1429 */     g2.setFont(getLabelFont());
/* 1430 */     g2.drawString(label, (float)labelLocation.getX(), 
/* 1431 */         (float)labelLocation.getY());
/* 1432 */     g2.setComposite(saveComposite);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Point2D calculateLabelLocation(Rectangle2D labelBounds, double ascent, Rectangle2D plotArea, double startAngle) {
/* 1450 */     Arc2D arc1 = new Arc2D.Double(plotArea, startAngle, 0.0D, 0);
/* 1451 */     Point2D point1 = arc1.getEndPoint();
/*      */     
/* 1453 */     double deltaX = -(point1.getX() - plotArea.getCenterX()) * this.axisLabelGap;
/*      */     
/* 1455 */     double deltaY = -(point1.getY() - plotArea.getCenterY()) * this.axisLabelGap;
/*      */ 
/*      */     
/* 1458 */     double labelX = point1.getX() - deltaX;
/* 1459 */     double labelY = point1.getY() - deltaY;
/*      */     
/* 1461 */     if (labelX < plotArea.getCenterX()) {
/* 1462 */       labelX -= labelBounds.getWidth();
/*      */     }
/*      */     
/* 1465 */     if (labelX == plotArea.getCenterX()) {
/* 1466 */       labelX -= labelBounds.getWidth() / 2.0D;
/*      */     }
/*      */     
/* 1469 */     if (labelY > plotArea.getCenterY()) {
/* 1470 */       labelY += ascent;
/*      */     }
/*      */     
/* 1473 */     return new Point2D.Double(labelX, labelY);
/*      */   }
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
/* 1485 */     if (obj == this) {
/* 1486 */       return true;
/*      */     }
/* 1488 */     if (!(obj instanceof SpiderWebPlot)) {
/* 1489 */       return false;
/*      */     }
/* 1491 */     if (!super.equals(obj)) {
/* 1492 */       return false;
/*      */     }
/* 1494 */     SpiderWebPlot that = (SpiderWebPlot)obj;
/* 1495 */     if (!this.dataExtractOrder.equals(that.dataExtractOrder)) {
/* 1496 */       return false;
/*      */     }
/* 1498 */     if (this.headPercent != that.headPercent) {
/* 1499 */       return false;
/*      */     }
/* 1501 */     if (this.interiorGap != that.interiorGap) {
/* 1502 */       return false;
/*      */     }
/* 1504 */     if (this.startAngle != that.startAngle) {
/* 1505 */       return false;
/*      */     }
/* 1507 */     if (!this.direction.equals(that.direction)) {
/* 1508 */       return false;
/*      */     }
/* 1510 */     if (this.maxValue != that.maxValue) {
/* 1511 */       return false;
/*      */     }
/* 1513 */     if (this.webFilled != that.webFilled) {
/* 1514 */       return false;
/*      */     }
/* 1516 */     if (this.axisLabelGap != that.axisLabelGap) {
/* 1517 */       return false;
/*      */     }
/* 1519 */     if (!PaintUtilities.equal(this.axisLinePaint, that.axisLinePaint)) {
/* 1520 */       return false;
/*      */     }
/* 1522 */     if (!this.axisLineStroke.equals(that.axisLineStroke)) {
/* 1523 */       return false;
/*      */     }
/* 1525 */     if (!ShapeUtilities.equal(this.legendItemShape, that.legendItemShape)) {
/* 1526 */       return false;
/*      */     }
/* 1528 */     if (!PaintUtilities.equal(this.seriesPaint, that.seriesPaint)) {
/* 1529 */       return false;
/*      */     }
/* 1531 */     if (!this.seriesPaintList.equals(that.seriesPaintList)) {
/* 1532 */       return false;
/*      */     }
/* 1534 */     if (!PaintUtilities.equal(this.baseSeriesPaint, that.baseSeriesPaint)) {
/* 1535 */       return false;
/*      */     }
/* 1537 */     if (!PaintUtilities.equal(this.seriesOutlinePaint, that.seriesOutlinePaint))
/*      */     {
/* 1539 */       return false;
/*      */     }
/* 1541 */     if (!this.seriesOutlinePaintList.equals(that.seriesOutlinePaintList)) {
/* 1542 */       return false;
/*      */     }
/* 1544 */     if (!PaintUtilities.equal(this.baseSeriesOutlinePaint, that.baseSeriesOutlinePaint))
/*      */     {
/* 1546 */       return false;
/*      */     }
/* 1548 */     if (!ObjectUtilities.equal(this.seriesOutlineStroke, that.seriesOutlineStroke))
/*      */     {
/* 1550 */       return false;
/*      */     }
/* 1552 */     if (!this.seriesOutlineStrokeList.equals(that.seriesOutlineStrokeList))
/*      */     {
/* 1554 */       return false;
/*      */     }
/* 1556 */     if (!this.baseSeriesOutlineStroke.equals(that.baseSeriesOutlineStroke))
/*      */     {
/* 1558 */       return false;
/*      */     }
/* 1560 */     if (!this.labelFont.equals(that.labelFont)) {
/* 1561 */       return false;
/*      */     }
/* 1563 */     if (!PaintUtilities.equal(this.labelPaint, that.labelPaint)) {
/* 1564 */       return false;
/*      */     }
/* 1566 */     if (!this.labelGenerator.equals(that.labelGenerator)) {
/* 1567 */       return false;
/*      */     }
/* 1569 */     if (!ObjectUtilities.equal(this.toolTipGenerator, that.toolTipGenerator))
/*      */     {
/* 1571 */       return false;
/*      */     }
/* 1573 */     if (!ObjectUtilities.equal(this.urlGenerator, that.urlGenerator))
/*      */     {
/* 1575 */       return false;
/*      */     }
/* 1577 */     return true;
/*      */   }
/*      */ 
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
/* 1590 */     SpiderWebPlot clone = (SpiderWebPlot)super.clone();
/* 1591 */     clone.legendItemShape = ShapeUtilities.clone(this.legendItemShape);
/* 1592 */     clone.seriesPaintList = (PaintList)this.seriesPaintList.clone();
/* 1593 */     clone
/* 1594 */       .seriesOutlinePaintList = (PaintList)this.seriesOutlinePaintList.clone();
/* 1595 */     clone
/* 1596 */       .seriesOutlineStrokeList = (StrokeList)this.seriesOutlineStrokeList.clone();
/* 1597 */     return clone;
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
/* 1608 */     stream.defaultWriteObject();
/*      */     
/* 1610 */     SerialUtilities.writeShape(this.legendItemShape, stream);
/* 1611 */     SerialUtilities.writePaint(this.seriesPaint, stream);
/* 1612 */     SerialUtilities.writePaint(this.baseSeriesPaint, stream);
/* 1613 */     SerialUtilities.writePaint(this.seriesOutlinePaint, stream);
/* 1614 */     SerialUtilities.writePaint(this.baseSeriesOutlinePaint, stream);
/* 1615 */     SerialUtilities.writeStroke(this.seriesOutlineStroke, stream);
/* 1616 */     SerialUtilities.writeStroke(this.baseSeriesOutlineStroke, stream);
/* 1617 */     SerialUtilities.writePaint(this.labelPaint, stream);
/* 1618 */     SerialUtilities.writePaint(this.axisLinePaint, stream);
/* 1619 */     SerialUtilities.writeStroke(this.axisLineStroke, stream);
/*      */   }
/*      */ 
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
/* 1632 */     stream.defaultReadObject();
/*      */     
/* 1634 */     this.legendItemShape = SerialUtilities.readShape(stream);
/* 1635 */     this.seriesPaint = SerialUtilities.readPaint(stream);
/* 1636 */     this.baseSeriesPaint = SerialUtilities.readPaint(stream);
/* 1637 */     this.seriesOutlinePaint = SerialUtilities.readPaint(stream);
/* 1638 */     this.baseSeriesOutlinePaint = SerialUtilities.readPaint(stream);
/* 1639 */     this.seriesOutlineStroke = SerialUtilities.readStroke(stream);
/* 1640 */     this.baseSeriesOutlineStroke = SerialUtilities.readStroke(stream);
/* 1641 */     this.labelPaint = SerialUtilities.readPaint(stream);
/* 1642 */     this.axisLinePaint = SerialUtilities.readPaint(stream);
/* 1643 */     this.axisLineStroke = SerialUtilities.readStroke(stream);
/* 1644 */     if (this.dataset != null)
/* 1645 */       this.dataset.addChangeListener(this); 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/SpiderWebPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */