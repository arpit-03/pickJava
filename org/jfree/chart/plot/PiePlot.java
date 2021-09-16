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
/*      */ import java.awt.RadialGradientPaint;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Arc2D;
/*      */ import java.awt.geom.CubicCurve2D;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.QuadCurve2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.TreeMap;
/*      */ import org.jfree.chart.JFreeChart;
/*      */ import org.jfree.chart.LegendItem;
/*      */ import org.jfree.chart.LegendItemCollection;
/*      */ import org.jfree.chart.PaintMap;
/*      */ import org.jfree.chart.StrokeMap;
/*      */ import org.jfree.chart.entity.ChartEntity;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.entity.PieSectionEntity;
/*      */ import org.jfree.chart.labels.PieSectionLabelGenerator;
/*      */ import org.jfree.chart.labels.PieToolTipGenerator;
/*      */ import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
/*      */ import org.jfree.chart.urls.PieURLGenerator;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.chart.util.ResourceBundleWrapper;
/*      */ import org.jfree.chart.util.ShadowGenerator;
/*      */ import org.jfree.data.DefaultKeyedValues;
/*      */ import org.jfree.data.KeyedValues;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.general.DatasetChangeEvent;
/*      */ import org.jfree.data.general.DatasetUtilities;
/*      */ import org.jfree.data.general.PieDataset;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.text.G2TextMeasurer;
/*      */ import org.jfree.text.TextBlock;
/*      */ import org.jfree.text.TextBox;
/*      */ import org.jfree.text.TextMeasurer;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.ui.RectangleAnchor;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ import org.jfree.ui.TextAnchor;
/*      */ import org.jfree.util.ObjectUtilities;
/*      */ import org.jfree.util.PaintUtilities;
/*      */ import org.jfree.util.Rotation;
/*      */ import org.jfree.util.ShapeUtilities;
/*      */ import org.jfree.util.UnitType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PiePlot
/*      */   extends Plot
/*      */   implements Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -795612466005590431L;
/*      */   public static final double DEFAULT_INTERIOR_GAP = 0.08D;
/*      */   public static final double MAX_INTERIOR_GAP = 0.4D;
/*      */   public static final double DEFAULT_START_ANGLE = 90.0D;
/*  277 */   public static final Font DEFAULT_LABEL_FONT = new Font("SansSerif", 0, 10);
/*      */ 
/*      */ 
/*      */   
/*  281 */   public static final Paint DEFAULT_LABEL_PAINT = Color.black;
/*      */ 
/*      */   
/*  284 */   public static final Paint DEFAULT_LABEL_BACKGROUND_PAINT = new Color(255, 255, 192);
/*      */ 
/*      */ 
/*      */   
/*  288 */   public static final Paint DEFAULT_LABEL_OUTLINE_PAINT = Color.black;
/*      */ 
/*      */   
/*  291 */   public static final Stroke DEFAULT_LABEL_OUTLINE_STROKE = new BasicStroke(0.5F);
/*      */ 
/*      */ 
/*      */   
/*  295 */   public static final Paint DEFAULT_LABEL_SHADOW_PAINT = new Color(151, 151, 151, 128);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final double DEFAULT_MINIMUM_ARC_ANGLE_TO_DRAW = 1.0E-5D;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PieDataset dataset;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int pieIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double interiorGap;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean circular;
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
/*      */   private PaintMap sectionPaintMap;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint baseSectionPaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoPopulateSectionPaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sectionOutlinesVisible;
/*      */ 
/*      */ 
/*      */   
/*      */   private PaintMap sectionOutlinePaintMap;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint baseSectionOutlinePaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoPopulateSectionOutlinePaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private StrokeMap sectionOutlineStrokeMap;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke baseSectionOutlineStroke;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoPopulateSectionOutlineStroke;
/*      */ 
/*      */ 
/*      */   
/*  371 */   private transient Paint shadowPaint = Color.gray;
/*      */ 
/*      */   
/*  374 */   private double shadowXOffset = 4.0D;
/*      */ 
/*      */   
/*  377 */   private double shadowYOffset = 4.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map explodePercentages;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PieSectionLabelGenerator labelGenerator;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Font labelFont;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint labelPaint;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint labelBackgroundPaint;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint labelOutlinePaint;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke labelOutlineStroke;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint labelShadowPaint;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean simpleLabels = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RectangleInsets labelPadding;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RectangleInsets simpleLabelOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  438 */   private double maximumLabelWidth = 0.14D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  444 */   private double labelGap = 0.025D;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean labelLinksVisible;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  454 */   private PieLabelLinkStyle labelLinkStyle = PieLabelLinkStyle.STANDARD;
/*      */ 
/*      */   
/*  457 */   private double labelLinkMargin = 0.025D;
/*      */ 
/*      */   
/*  460 */   private transient Paint labelLinkPaint = Color.black;
/*      */ 
/*      */   
/*  463 */   private transient Stroke labelLinkStroke = new BasicStroke(0.5F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AbstractPieLabelDistributor labelDistributor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PieToolTipGenerator toolTipGenerator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PieURLGenerator urlGenerator;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PieSectionLabelGenerator legendLabelGenerator;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PieSectionLabelGenerator legendLabelToolTipGenerator;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PieURLGenerator legendLabelURLGenerator;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ignoreNullValues;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ignoreZeroValues;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Shape legendItemShape;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double minimumArcAngleToDraw;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ShadowGenerator shadowGenerator;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  526 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
/*      */ 
/*      */ 
/*      */   
/*      */   static final boolean DEBUG_DRAW_INTERIOR = false;
/*      */ 
/*      */ 
/*      */   
/*      */   static final boolean DEBUG_DRAW_LINK_AREA = false;
/*      */ 
/*      */ 
/*      */   
/*      */   static final boolean DEBUG_DRAW_PIE_AREA = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint sectionPaint;
/*      */ 
/*      */   
/*      */   private transient Paint sectionOutlinePaint;
/*      */ 
/*      */   
/*      */   private transient Stroke sectionOutlineStroke;
/*      */ 
/*      */ 
/*      */   
/*      */   public PiePlot() {
/*  553 */     this((PieDataset)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PiePlot(PieDataset dataset) {
/*  563 */     this.dataset = dataset;
/*  564 */     if (dataset != null) {
/*  565 */       dataset.addChangeListener(this);
/*      */     }
/*  567 */     this.pieIndex = 0;
/*      */     
/*  569 */     this.interiorGap = 0.08D;
/*  570 */     this.circular = true;
/*  571 */     this.startAngle = 90.0D;
/*  572 */     this.direction = Rotation.CLOCKWISE;
/*  573 */     this.minimumArcAngleToDraw = 1.0E-5D;
/*      */     
/*  575 */     this.sectionPaint = null;
/*  576 */     this.sectionPaintMap = new PaintMap();
/*  577 */     this.baseSectionPaint = Color.gray;
/*  578 */     this.autoPopulateSectionPaint = true;
/*      */     
/*  580 */     this.sectionOutlinesVisible = true;
/*  581 */     this.sectionOutlinePaint = null;
/*  582 */     this.sectionOutlinePaintMap = new PaintMap();
/*  583 */     this.baseSectionOutlinePaint = DEFAULT_OUTLINE_PAINT;
/*  584 */     this.autoPopulateSectionOutlinePaint = false;
/*      */     
/*  586 */     this.sectionOutlineStroke = null;
/*  587 */     this.sectionOutlineStrokeMap = new StrokeMap();
/*  588 */     this.baseSectionOutlineStroke = DEFAULT_OUTLINE_STROKE;
/*  589 */     this.autoPopulateSectionOutlineStroke = false;
/*      */     
/*  591 */     this.explodePercentages = new TreeMap<Object, Object>();
/*      */     
/*  593 */     this.labelGenerator = (PieSectionLabelGenerator)new StandardPieSectionLabelGenerator();
/*  594 */     this.labelFont = DEFAULT_LABEL_FONT;
/*  595 */     this.labelPaint = DEFAULT_LABEL_PAINT;
/*  596 */     this.labelBackgroundPaint = DEFAULT_LABEL_BACKGROUND_PAINT;
/*  597 */     this.labelOutlinePaint = DEFAULT_LABEL_OUTLINE_PAINT;
/*  598 */     this.labelOutlineStroke = DEFAULT_LABEL_OUTLINE_STROKE;
/*  599 */     this.labelShadowPaint = DEFAULT_LABEL_SHADOW_PAINT;
/*  600 */     this.labelLinksVisible = true;
/*  601 */     this.labelDistributor = new PieLabelDistributor(0);
/*      */     
/*  603 */     this.simpleLabels = false;
/*  604 */     this.simpleLabelOffset = new RectangleInsets(UnitType.RELATIVE, 0.18D, 0.18D, 0.18D, 0.18D);
/*      */     
/*  606 */     this.labelPadding = new RectangleInsets(2.0D, 2.0D, 2.0D, 2.0D);
/*      */     
/*  608 */     this.toolTipGenerator = null;
/*  609 */     this.urlGenerator = null;
/*  610 */     this.legendLabelGenerator = (PieSectionLabelGenerator)new StandardPieSectionLabelGenerator();
/*  611 */     this.legendLabelToolTipGenerator = null;
/*  612 */     this.legendLabelURLGenerator = null;
/*  613 */     this.legendItemShape = Plot.DEFAULT_LEGEND_ITEM_CIRCLE;
/*      */     
/*  615 */     this.ignoreNullValues = false;
/*  616 */     this.ignoreZeroValues = false;
/*      */     
/*  618 */     this.shadowGenerator = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PieDataset getDataset() {
/*  629 */     return this.dataset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataset(PieDataset dataset) {
/*  642 */     PieDataset existing = this.dataset;
/*  643 */     if (existing != null) {
/*  644 */       existing.removeChangeListener(this);
/*      */     }
/*      */ 
/*      */     
/*  648 */     this.dataset = dataset;
/*  649 */     if (dataset != null) {
/*  650 */       setDatasetGroup(dataset.getGroup());
/*  651 */       dataset.addChangeListener(this);
/*      */     } 
/*      */ 
/*      */     
/*  655 */     DatasetChangeEvent event = new DatasetChangeEvent(this, (Dataset)dataset);
/*  656 */     datasetChanged(event);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPieIndex() {
/*  668 */     return this.pieIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPieIndex(int index) {
/*  680 */     this.pieIndex = index;
/*      */   }
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
/*  692 */     return this.startAngle;
/*      */   }
/*      */ 
/*      */ 
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
/*  706 */     this.startAngle = angle;
/*  707 */     fireChangeEvent();
/*      */   }
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
/*  719 */     return this.direction;
/*      */   }
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
/*  731 */     ParamChecks.nullNotPermitted(direction, "direction");
/*  732 */     this.direction = direction;
/*  733 */     fireChangeEvent();
/*      */   }
/*      */ 
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
/*  746 */     return this.interiorGap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  761 */     if (percent < 0.0D || percent > 0.4D) {
/*  762 */       throw new IllegalArgumentException("Invalid 'percent' (" + percent + ") argument.");
/*      */     }
/*      */ 
/*      */     
/*  766 */     if (this.interiorGap != percent) {
/*  767 */       this.interiorGap = percent;
/*  768 */       fireChangeEvent();
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
/*      */   public boolean isCircular() {
/*  782 */     return this.circular;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCircular(boolean flag) {
/*  794 */     setCircular(flag, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCircular(boolean circular, boolean notify) {
/*  807 */     this.circular = circular;
/*  808 */     if (notify) {
/*  809 */       fireChangeEvent();
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
/*      */   public boolean getIgnoreNullValues() {
/*  822 */     return this.ignoreNullValues;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIgnoreNullValues(boolean flag) {
/*  837 */     this.ignoreNullValues = flag;
/*  838 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getIgnoreZeroValues() {
/*  850 */     return this.ignoreZeroValues;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIgnoreZeroValues(boolean flag) {
/*  865 */     this.ignoreZeroValues = flag;
/*  866 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Paint lookupSectionPaint(Comparable key) {
/*  884 */     return lookupSectionPaint(key, getAutoPopulateSectionPaint());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Paint lookupSectionPaint(Comparable key, boolean autoPopulate) {
/*  913 */     Paint result = getSectionPaint();
/*  914 */     if (result != null) {
/*  915 */       return result;
/*      */     }
/*      */ 
/*      */     
/*  919 */     result = this.sectionPaintMap.getPaint(key);
/*  920 */     if (result != null) {
/*  921 */       return result;
/*      */     }
/*      */ 
/*      */     
/*  925 */     if (autoPopulate) {
/*  926 */       DrawingSupplier ds = getDrawingSupplier();
/*  927 */       if (ds != null) {
/*  928 */         result = ds.getNextPaint();
/*  929 */         this.sectionPaintMap.put(key, result);
/*      */       } else {
/*      */         
/*  932 */         result = this.baseSectionPaint;
/*      */       } 
/*      */     } else {
/*      */       
/*  936 */       result = this.baseSectionPaint;
/*      */     } 
/*  938 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getSectionPaint() {
/*  952 */     return this.sectionPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSectionPaint(Paint paint) {
/*  968 */     this.sectionPaint = paint;
/*  969 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Comparable getSectionKey(int section) {
/*  987 */     Comparable key = null;
/*  988 */     if (this.dataset != null && 
/*  989 */       section >= 0 && section < this.dataset.getItemCount()) {
/*  990 */       key = this.dataset.getKey(section);
/*      */     }
/*      */     
/*  993 */     if (key == null) {
/*  994 */       key = new Integer(section);
/*      */     }
/*  996 */     return key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getSectionPaint(Comparable key) {
/* 1017 */     return this.sectionPaintMap.getPaint(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSectionPaint(Comparable key, Paint paint) {
/* 1036 */     this.sectionPaintMap.put(key, paint);
/* 1037 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearSectionPaints(boolean notify) {
/* 1053 */     this.sectionPaintMap.clear();
/* 1054 */     if (notify) {
/* 1055 */       fireChangeEvent();
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
/*      */   public Paint getBaseSectionPaint() {
/* 1068 */     return this.baseSectionPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseSectionPaint(Paint paint) {
/* 1080 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 1081 */     this.baseSectionPaint = paint;
/* 1082 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAutoPopulateSectionPaint() {
/* 1094 */     return this.autoPopulateSectionPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoPopulateSectionPaint(boolean auto) {
/* 1107 */     this.autoPopulateSectionPaint = auto;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getSectionOutlinesVisible() {
/* 1123 */     return this.sectionOutlinesVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSectionOutlinesVisible(boolean visible) {
/* 1136 */     this.sectionOutlinesVisible = visible;
/* 1137 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Paint lookupSectionOutlinePaint(Comparable key) {
/* 1154 */     return lookupSectionOutlinePaint(key, 
/* 1155 */         getAutoPopulateSectionOutlinePaint());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Paint lookupSectionOutlinePaint(Comparable key, boolean autoPopulate) {
/* 1185 */     Paint result = getSectionOutlinePaint();
/* 1186 */     if (result != null) {
/* 1187 */       return result;
/*      */     }
/*      */ 
/*      */     
/* 1191 */     result = this.sectionOutlinePaintMap.getPaint(key);
/* 1192 */     if (result != null) {
/* 1193 */       return result;
/*      */     }
/*      */ 
/*      */     
/* 1197 */     if (autoPopulate) {
/* 1198 */       DrawingSupplier ds = getDrawingSupplier();
/* 1199 */       if (ds != null) {
/* 1200 */         result = ds.getNextOutlinePaint();
/* 1201 */         this.sectionOutlinePaintMap.put(key, result);
/*      */       } else {
/*      */         
/* 1204 */         result = this.baseSectionOutlinePaint;
/*      */       } 
/*      */     } else {
/*      */       
/* 1208 */       result = this.baseSectionOutlinePaint;
/*      */     } 
/* 1210 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getSectionOutlinePaint(Comparable key) {
/* 1231 */     return this.sectionOutlinePaintMap.getPaint(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSectionOutlinePaint(Comparable key, Paint paint) {
/* 1250 */     this.sectionOutlinePaintMap.put(key, paint);
/* 1251 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearSectionOutlinePaints(boolean notify) {
/* 1267 */     this.sectionOutlinePaintMap.clear();
/* 1268 */     if (notify) {
/* 1269 */       fireChangeEvent();
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
/*      */   public Paint getBaseSectionOutlinePaint() {
/* 1282 */     return this.baseSectionOutlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseSectionOutlinePaint(Paint paint) {
/* 1293 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 1294 */     this.baseSectionOutlinePaint = paint;
/* 1295 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAutoPopulateSectionOutlinePaint() {
/* 1308 */     return this.autoPopulateSectionOutlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoPopulateSectionOutlinePaint(boolean auto) {
/* 1321 */     this.autoPopulateSectionOutlinePaint = auto;
/* 1322 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Stroke lookupSectionOutlineStroke(Comparable key) {
/* 1341 */     return lookupSectionOutlineStroke(key, 
/* 1342 */         getAutoPopulateSectionOutlineStroke());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Stroke lookupSectionOutlineStroke(Comparable key, boolean autoPopulate) {
/* 1372 */     Stroke result = getSectionOutlineStroke();
/* 1373 */     if (result != null) {
/* 1374 */       return result;
/*      */     }
/*      */ 
/*      */     
/* 1378 */     result = this.sectionOutlineStrokeMap.getStroke(key);
/* 1379 */     if (result != null) {
/* 1380 */       return result;
/*      */     }
/*      */ 
/*      */     
/* 1384 */     if (autoPopulate) {
/* 1385 */       DrawingSupplier ds = getDrawingSupplier();
/* 1386 */       if (ds != null) {
/* 1387 */         result = ds.getNextOutlineStroke();
/* 1388 */         this.sectionOutlineStrokeMap.put(key, result);
/*      */       } else {
/*      */         
/* 1391 */         result = this.baseSectionOutlineStroke;
/*      */       } 
/*      */     } else {
/*      */       
/* 1395 */       result = this.baseSectionOutlineStroke;
/*      */     } 
/* 1397 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getSectionOutlineStroke(Comparable key) {
/* 1418 */     return this.sectionOutlineStrokeMap.getStroke(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSectionOutlineStroke(Comparable key, Stroke stroke) {
/* 1437 */     this.sectionOutlineStrokeMap.put(key, stroke);
/* 1438 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearSectionOutlineStrokes(boolean notify) {
/* 1454 */     this.sectionOutlineStrokeMap.clear();
/* 1455 */     if (notify) {
/* 1456 */       fireChangeEvent();
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
/*      */   public Stroke getBaseSectionOutlineStroke() {
/* 1469 */     return this.baseSectionOutlineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseSectionOutlineStroke(Stroke stroke) {
/* 1480 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 1481 */     this.baseSectionOutlineStroke = stroke;
/* 1482 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAutoPopulateSectionOutlineStroke() {
/* 1495 */     return this.autoPopulateSectionOutlineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoPopulateSectionOutlineStroke(boolean auto) {
/* 1508 */     this.autoPopulateSectionOutlineStroke = auto;
/* 1509 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getShadowPaint() {
/* 1520 */     return this.shadowPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShadowPaint(Paint paint) {
/* 1532 */     this.shadowPaint = paint;
/* 1533 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getShadowXOffset() {
/* 1544 */     return this.shadowXOffset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShadowXOffset(double offset) {
/* 1556 */     this.shadowXOffset = offset;
/* 1557 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getShadowYOffset() {
/* 1568 */     return this.shadowYOffset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShadowYOffset(double offset) {
/* 1580 */     this.shadowYOffset = offset;
/* 1581 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getExplodePercent(Comparable key) {
/* 1601 */     double result = 0.0D;
/* 1602 */     if (this.explodePercentages != null) {
/* 1603 */       Number percent = (Number)this.explodePercentages.get(key);
/* 1604 */       if (percent != null) {
/* 1605 */         result = percent.doubleValue();
/*      */       }
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
/*      */   public void setExplodePercent(Comparable key, double percent) {
/* 1623 */     ParamChecks.nullNotPermitted(key, "key");
/* 1624 */     if (this.explodePercentages == null) {
/* 1625 */       this.explodePercentages = new TreeMap<Object, Object>();
/*      */     }
/* 1627 */     this.explodePercentages.put(key, new Double(percent));
/* 1628 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMaximumExplodePercent() {
/* 1637 */     if (this.dataset == null) {
/* 1638 */       return 0.0D;
/*      */     }
/* 1640 */     double result = 0.0D;
/* 1641 */     Iterator<Comparable> iterator = this.dataset.getKeys().iterator();
/* 1642 */     while (iterator.hasNext()) {
/* 1643 */       Comparable key = iterator.next();
/* 1644 */       Number explode = (Number)this.explodePercentages.get(key);
/* 1645 */       if (explode != null) {
/* 1646 */         result = Math.max(result, explode.doubleValue());
/*      */       }
/*      */     } 
/* 1649 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PieSectionLabelGenerator getLabelGenerator() {
/* 1660 */     return this.labelGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelGenerator(PieSectionLabelGenerator generator) {
/* 1672 */     this.labelGenerator = generator;
/* 1673 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getLabelGap() {
/* 1685 */     return this.labelGap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelGap(double gap) {
/* 1698 */     this.labelGap = gap;
/* 1699 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMaximumLabelWidth() {
/* 1710 */     return this.maximumLabelWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaximumLabelWidth(double width) {
/* 1722 */     this.maximumLabelWidth = width;
/* 1723 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getLabelLinksVisible() {
/* 1735 */     return this.labelLinksVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelLinksVisible(boolean visible) {
/* 1750 */     this.labelLinksVisible = visible;
/* 1751 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PieLabelLinkStyle getLabelLinkStyle() {
/* 1764 */     return this.labelLinkStyle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelLinkStyle(PieLabelLinkStyle style) {
/* 1778 */     ParamChecks.nullNotPermitted(style, "style");
/* 1779 */     this.labelLinkStyle = style;
/* 1780 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getLabelLinkMargin() {
/* 1792 */     return this.labelLinkMargin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelLinkMargin(double margin) {
/* 1804 */     this.labelLinkMargin = margin;
/* 1805 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getLabelLinkPaint() {
/* 1817 */     return this.labelLinkPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelLinkPaint(Paint paint) {
/* 1830 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 1831 */     this.labelLinkPaint = paint;
/* 1832 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getLabelLinkStroke() {
/* 1843 */     return this.labelLinkStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelLinkStroke(Stroke stroke) {
/* 1855 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 1856 */     this.labelLinkStroke = stroke;
/* 1857 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double getLabelLinkDepth() {
/* 1872 */     return 0.1D;
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
/* 1883 */     return this.labelFont;
/*      */   }
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
/* 1895 */     ParamChecks.nullNotPermitted(font, "font");
/* 1896 */     this.labelFont = font;
/* 1897 */     fireChangeEvent();
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
/* 1908 */     return this.labelPaint;
/*      */   }
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
/* 1920 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 1921 */     this.labelPaint = paint;
/* 1922 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getLabelBackgroundPaint() {
/* 1933 */     return this.labelBackgroundPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelBackgroundPaint(Paint paint) {
/* 1945 */     this.labelBackgroundPaint = paint;
/* 1946 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getLabelOutlinePaint() {
/* 1957 */     return this.labelOutlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelOutlinePaint(Paint paint) {
/* 1969 */     this.labelOutlinePaint = paint;
/* 1970 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getLabelOutlineStroke() {
/* 1981 */     return this.labelOutlineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelOutlineStroke(Stroke stroke) {
/* 1993 */     this.labelOutlineStroke = stroke;
/* 1994 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getLabelShadowPaint() {
/* 2005 */     return this.labelShadowPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelShadowPaint(Paint paint) {
/* 2017 */     this.labelShadowPaint = paint;
/* 2018 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleInsets getLabelPadding() {
/* 2031 */     return this.labelPadding;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelPadding(RectangleInsets padding) {
/* 2045 */     ParamChecks.nullNotPermitted(padding, "padding");
/* 2046 */     this.labelPadding = padding;
/* 2047 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getSimpleLabels() {
/* 2059 */     return this.simpleLabels;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSimpleLabels(boolean simple) {
/* 2072 */     this.simpleLabels = simple;
/* 2073 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleInsets getSimpleLabelOffset() {
/* 2086 */     return this.simpleLabelOffset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSimpleLabelOffset(RectangleInsets offset) {
/* 2100 */     ParamChecks.nullNotPermitted(offset, "offset");
/* 2101 */     this.simpleLabelOffset = offset;
/* 2102 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AbstractPieLabelDistributor getLabelDistributor() {
/* 2114 */     return this.labelDistributor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelDistributor(AbstractPieLabelDistributor distributor) {
/* 2126 */     ParamChecks.nullNotPermitted(distributor, "distributor");
/* 2127 */     this.labelDistributor = distributor;
/* 2128 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PieToolTipGenerator getToolTipGenerator() {
/* 2141 */     return this.toolTipGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToolTipGenerator(PieToolTipGenerator generator) {
/* 2154 */     this.toolTipGenerator = generator;
/* 2155 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PieURLGenerator getURLGenerator() {
/* 2166 */     return this.urlGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setURLGenerator(PieURLGenerator generator) {
/* 2178 */     this.urlGenerator = generator;
/* 2179 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMinimumArcAngleToDraw() {
/* 2191 */     return this.minimumArcAngleToDraw;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinimumArcAngleToDraw(double angle) {
/* 2213 */     this.minimumArcAngleToDraw = angle;
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
/* 2224 */     return this.legendItemShape;
/*      */   }
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
/* 2236 */     ParamChecks.nullNotPermitted(shape, "shape");
/* 2237 */     this.legendItemShape = shape;
/* 2238 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PieSectionLabelGenerator getLegendLabelGenerator() {
/* 2249 */     return this.legendLabelGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegendLabelGenerator(PieSectionLabelGenerator generator) {
/* 2261 */     ParamChecks.nullNotPermitted(generator, "generator");
/* 2262 */     this.legendLabelGenerator = generator;
/* 2263 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PieSectionLabelGenerator getLegendLabelToolTipGenerator() {
/* 2274 */     return this.legendLabelToolTipGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegendLabelToolTipGenerator(PieSectionLabelGenerator generator) {
/* 2287 */     this.legendLabelToolTipGenerator = generator;
/* 2288 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PieURLGenerator getLegendLabelURLGenerator() {
/* 2301 */     return this.legendLabelURLGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegendLabelURLGenerator(PieURLGenerator generator) {
/* 2315 */     this.legendLabelURLGenerator = generator;
/* 2316 */     fireChangeEvent();
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
/* 2327 */     return this.shadowGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 2342 */     this.shadowGenerator = generator;
/* 2343 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMouseWheelRotation(int rotateClicks) {
/* 2355 */     setStartAngle(this.startAngle + rotateClicks * 4.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PiePlotState initialise(Graphics2D g2, Rectangle2D plotArea, PiePlot plot, Integer index, PlotRenderingInfo info) {
/* 2376 */     PiePlotState state = new PiePlotState(info);
/* 2377 */     state.setPassesRequired(2);
/* 2378 */     if (this.dataset != null) {
/* 2379 */       state.setTotal(DatasetUtilities.calculatePieDatasetTotal(plot
/* 2380 */             .getDataset()));
/*      */     }
/* 2382 */     state.setLatestAngle(plot.getStartAngle());
/* 2383 */     return state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 2403 */     RectangleInsets insets = getInsets();
/* 2404 */     insets.trim(area);
/*      */     
/* 2406 */     if (info != null) {
/* 2407 */       info.setPlotArea(area);
/* 2408 */       info.setDataArea(area);
/*      */     } 
/*      */     
/* 2411 */     drawBackground(g2, area);
/* 2412 */     drawOutline(g2, area);
/*      */     
/* 2414 */     Shape savedClip = g2.getClip();
/* 2415 */     g2.clip(area);
/*      */     
/* 2417 */     Composite originalComposite = g2.getComposite();
/* 2418 */     g2.setComposite(AlphaComposite.getInstance(3, 
/* 2419 */           getForegroundAlpha()));
/*      */     
/* 2421 */     if (!DatasetUtilities.isEmptyOrNull(this.dataset)) {
/* 2422 */       Graphics2D savedG2 = g2;
/* 2423 */       boolean suppressShadow = Boolean.TRUE.equals(g2.getRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION));
/*      */       
/* 2425 */       BufferedImage dataImage = null;
/* 2426 */       if (this.shadowGenerator != null && !suppressShadow) {
/*      */         
/* 2428 */         dataImage = new BufferedImage((int)area.getWidth(), (int)area.getHeight(), 2);
/* 2429 */         g2 = dataImage.createGraphics();
/* 2430 */         g2.translate(-area.getX(), -area.getY());
/* 2431 */         g2.setRenderingHints(savedG2.getRenderingHints());
/*      */       } 
/* 2433 */       drawPie(g2, area, info);
/* 2434 */       if (this.shadowGenerator != null && !suppressShadow)
/*      */       {
/* 2436 */         BufferedImage shadowImage = this.shadowGenerator.createDropShadow(dataImage);
/* 2437 */         g2 = savedG2;
/* 2438 */         g2.drawImage(shadowImage, (int)area.getX() + this.shadowGenerator
/* 2439 */             .calculateOffsetX(), 
/* 2440 */             (int)area.getY() + this.shadowGenerator
/* 2441 */             .calculateOffsetY(), (ImageObserver)null);
/* 2442 */         g2.drawImage(dataImage, (int)area.getX(), (int)area.getY(), (ImageObserver)null);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 2447 */       drawNoDataMessage(g2, area);
/*      */     } 
/*      */     
/* 2450 */     g2.setClip(savedClip);
/* 2451 */     g2.setComposite(originalComposite);
/*      */     
/* 2453 */     drawOutline(g2, area);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawPie(Graphics2D g2, Rectangle2D plotArea, PlotRenderingInfo info) {
/* 2467 */     PiePlotState state = initialise(g2, plotArea, this, (Integer)null, info);
/*      */ 
/*      */     
/* 2470 */     double labelReserve = 0.0D;
/* 2471 */     if (this.labelGenerator != null && !this.simpleLabels) {
/* 2472 */       labelReserve = this.labelGap + this.maximumLabelWidth;
/*      */     }
/* 2474 */     double gapHorizontal = plotArea.getWidth() * labelReserve * 2.0D;
/* 2475 */     double gapVertical = plotArea.getHeight() * this.interiorGap * 2.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2491 */     double linkX = plotArea.getX() + gapHorizontal / 2.0D;
/* 2492 */     double linkY = plotArea.getY() + gapVertical / 2.0D;
/* 2493 */     double linkW = plotArea.getWidth() - gapHorizontal;
/* 2494 */     double linkH = plotArea.getHeight() - gapVertical;
/*      */ 
/*      */     
/* 2497 */     if (this.circular) {
/* 2498 */       double min = Math.min(linkW, linkH) / 2.0D;
/* 2499 */       linkX = (linkX + linkX + linkW) / 2.0D - min;
/* 2500 */       linkY = (linkY + linkY + linkH) / 2.0D - min;
/* 2501 */       linkW = 2.0D * min;
/* 2502 */       linkH = 2.0D * min;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2507 */     Rectangle2D linkArea = new Rectangle2D.Double(linkX, linkY, linkW, linkH);
/*      */     
/* 2509 */     state.setLinkArea(linkArea);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2522 */     double lm = 0.0D;
/* 2523 */     if (!this.simpleLabels) {
/* 2524 */       lm = this.labelLinkMargin;
/*      */     }
/* 2526 */     double hh = linkArea.getWidth() * lm * 2.0D;
/* 2527 */     double vv = linkArea.getHeight() * lm * 2.0D;
/* 2528 */     Rectangle2D explodeArea = new Rectangle2D.Double(linkX + hh / 2.0D, linkY + vv / 2.0D, linkW - hh, linkH - vv);
/*      */ 
/*      */     
/* 2531 */     state.setExplodedPieArea(explodeArea);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2536 */     double maximumExplodePercent = getMaximumExplodePercent();
/* 2537 */     double percent = maximumExplodePercent / (1.0D + maximumExplodePercent);
/*      */     
/* 2539 */     double h1 = explodeArea.getWidth() * percent;
/* 2540 */     double v1 = explodeArea.getHeight() * percent;
/*      */ 
/*      */     
/* 2543 */     Rectangle2D pieArea = new Rectangle2D.Double(explodeArea.getX() + h1 / 2.0D, explodeArea.getY() + v1 / 2.0D, explodeArea.getWidth() - h1, explodeArea.getHeight() - v1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2549 */     state.setPieArea(pieArea);
/* 2550 */     state.setPieCenterX(pieArea.getCenterX());
/* 2551 */     state.setPieCenterY(pieArea.getCenterY());
/* 2552 */     state.setPieWRadius(pieArea.getWidth() / 2.0D);
/* 2553 */     state.setPieHRadius(pieArea.getHeight() / 2.0D);
/*      */ 
/*      */     
/* 2556 */     if (this.dataset != null && this.dataset.getKeys().size() > 0) {
/*      */       
/* 2558 */       List keys = this.dataset.getKeys();
/* 2559 */       double totalValue = DatasetUtilities.calculatePieDatasetTotal(this.dataset);
/*      */ 
/*      */       
/* 2562 */       int passesRequired = state.getPassesRequired();
/* 2563 */       for (int pass = 0; pass < passesRequired; pass++) {
/* 2564 */         double runningTotal = 0.0D;
/* 2565 */         for (int section = 0; section < keys.size(); section++) {
/* 2566 */           Number n = this.dataset.getValue(section);
/* 2567 */           if (n != null) {
/* 2568 */             double value = n.doubleValue();
/* 2569 */             if (value > 0.0D) {
/* 2570 */               runningTotal += value;
/* 2571 */               drawItem(g2, section, explodeArea, state, pass);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2576 */       if (this.simpleLabels) {
/* 2577 */         drawSimpleLabels(g2, keys, totalValue, plotArea, linkArea, state);
/*      */       }
/*      */       else {
/*      */         
/* 2581 */         drawLabels(g2, keys, totalValue, plotArea, linkArea, state);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 2586 */       drawNoDataMessage(g2, plotArea);
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
/*      */   protected void drawItem(Graphics2D g2, int section, Rectangle2D dataArea, PiePlotState state, int currentPass) {
/* 2602 */     Number n = this.dataset.getValue(section);
/* 2603 */     if (n == null) {
/*      */       return;
/*      */     }
/* 2606 */     double value = n.doubleValue();
/* 2607 */     double angle1 = 0.0D;
/* 2608 */     double angle2 = 0.0D;
/*      */     
/* 2610 */     if (this.direction == Rotation.CLOCKWISE) {
/* 2611 */       angle1 = state.getLatestAngle();
/* 2612 */       angle2 = angle1 - value / state.getTotal() * 360.0D;
/*      */     }
/* 2614 */     else if (this.direction == Rotation.ANTICLOCKWISE) {
/* 2615 */       angle1 = state.getLatestAngle();
/* 2616 */       angle2 = angle1 + value / state.getTotal() * 360.0D;
/*      */     } else {
/*      */       
/* 2619 */       throw new IllegalStateException("Rotation type not recognised.");
/*      */     } 
/*      */     
/* 2622 */     double angle = angle2 - angle1;
/* 2623 */     if (Math.abs(angle) > getMinimumArcAngleToDraw()) {
/* 2624 */       double ep = 0.0D;
/* 2625 */       double mep = getMaximumExplodePercent();
/* 2626 */       if (mep > 0.0D) {
/* 2627 */         ep = getExplodePercent(section) / mep;
/*      */       }
/* 2629 */       Rectangle2D arcBounds = getArcBounds(state.getPieArea(), state
/* 2630 */           .getExplodedPieArea(), angle1, angle, ep);
/* 2631 */       Arc2D.Double arc = new Arc2D.Double(arcBounds, angle1, angle, 2);
/*      */ 
/*      */       
/* 2634 */       if (currentPass == 0) {
/* 2635 */         if (this.shadowPaint != null && this.shadowGenerator == null) {
/* 2636 */           Shape shadowArc = ShapeUtilities.createTranslatedShape(arc, (float)this.shadowXOffset, (float)this.shadowYOffset);
/*      */ 
/*      */           
/* 2639 */           g2.setPaint(this.shadowPaint);
/* 2640 */           g2.fill(shadowArc);
/*      */         }
/*      */       
/* 2643 */       } else if (currentPass == 1) {
/* 2644 */         Comparable key = getSectionKey(section);
/* 2645 */         Paint paint = lookupSectionPaint(key, state);
/* 2646 */         g2.setPaint(paint);
/* 2647 */         g2.fill(arc);
/*      */         
/* 2649 */         Paint outlinePaint = lookupSectionOutlinePaint(key);
/* 2650 */         Stroke outlineStroke = lookupSectionOutlineStroke(key);
/* 2651 */         if (this.sectionOutlinesVisible) {
/* 2652 */           g2.setPaint(outlinePaint);
/* 2653 */           g2.setStroke(outlineStroke);
/* 2654 */           g2.draw(arc);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2659 */         if (state.getInfo() != null) {
/* 2660 */           EntityCollection entities = state.getEntityCollection();
/* 2661 */           if (entities != null) {
/* 2662 */             String tip = null;
/* 2663 */             if (this.toolTipGenerator != null) {
/* 2664 */               tip = this.toolTipGenerator.generateToolTip(this.dataset, key);
/*      */             }
/*      */             
/* 2667 */             String url = null;
/* 2668 */             if (this.urlGenerator != null) {
/* 2669 */               url = this.urlGenerator.generateURL(this.dataset, key, this.pieIndex);
/*      */             }
/*      */             
/* 2672 */             PieSectionEntity entity = new PieSectionEntity(arc, this.dataset, this.pieIndex, section, key, tip, url);
/*      */ 
/*      */             
/* 2675 */             entities.add((ChartEntity)entity);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 2680 */     state.setLatestAngle(angle2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawSimpleLabels(Graphics2D g2, List keys, double totalValue, Rectangle2D plotArea, Rectangle2D pieArea, PiePlotState state) {
/* 2699 */     Composite originalComposite = g2.getComposite();
/* 2700 */     g2.setComposite(AlphaComposite.getInstance(3, 1.0F));
/*      */ 
/*      */     
/* 2703 */     Rectangle2D labelsArea = this.simpleLabelOffset.createInsetRectangle(pieArea);
/*      */     
/* 2705 */     double runningTotal = 0.0D;
/* 2706 */     Iterator<Comparable> iterator = keys.iterator();
/* 2707 */     while (iterator.hasNext()) {
/* 2708 */       boolean include; Comparable key = iterator.next();
/*      */       
/* 2710 */       double v = 0.0D;
/* 2711 */       Number n = getDataset().getValue(key);
/* 2712 */       if (n == null) {
/* 2713 */         include = !getIgnoreNullValues();
/*      */       } else {
/*      */         
/* 2716 */         v = n.doubleValue();
/* 2717 */         include = getIgnoreZeroValues() ? ((v > 0.0D)) : ((v >= 0.0D));
/*      */       } 
/*      */       
/* 2720 */       if (include) {
/* 2721 */         runningTotal += v;
/*      */ 
/*      */         
/* 2724 */         double mid = getStartAngle() + getDirection().getFactor() * (runningTotal - v / 2.0D) * 360.0D / totalValue;
/*      */ 
/*      */ 
/*      */         
/* 2728 */         Arc2D arc = new Arc2D.Double(labelsArea, getStartAngle(), mid - getStartAngle(), 0);
/* 2729 */         int x = (int)arc.getEndPoint().getX();
/* 2730 */         int y = (int)arc.getEndPoint().getY();
/*      */         
/* 2732 */         PieSectionLabelGenerator myLabelGenerator = getLabelGenerator();
/* 2733 */         if (myLabelGenerator == null) {
/*      */           continue;
/*      */         }
/* 2736 */         String label = myLabelGenerator.generateSectionLabel(this.dataset, key);
/*      */         
/* 2738 */         if (label == null) {
/*      */           continue;
/*      */         }
/* 2741 */         g2.setFont(this.labelFont);
/* 2742 */         FontMetrics fm = g2.getFontMetrics();
/* 2743 */         Rectangle2D bounds = TextUtilities.getTextBounds(label, g2, fm);
/* 2744 */         Rectangle2D out = this.labelPadding.createOutsetRectangle(bounds);
/*      */         
/* 2746 */         Shape bg = ShapeUtilities.createTranslatedShape(out, x - bounds
/* 2747 */             .getCenterX(), y - bounds.getCenterY());
/* 2748 */         if (this.labelShadowPaint != null && this.shadowGenerator == null) {
/*      */           
/* 2750 */           Shape shadow = ShapeUtilities.createTranslatedShape(bg, this.shadowXOffset, this.shadowYOffset);
/*      */           
/* 2752 */           g2.setPaint(this.labelShadowPaint);
/* 2753 */           g2.fill(shadow);
/*      */         } 
/* 2755 */         if (this.labelBackgroundPaint != null) {
/* 2756 */           g2.setPaint(this.labelBackgroundPaint);
/* 2757 */           g2.fill(bg);
/*      */         } 
/* 2759 */         if (this.labelOutlinePaint != null && this.labelOutlineStroke != null) {
/*      */           
/* 2761 */           g2.setPaint(this.labelOutlinePaint);
/* 2762 */           g2.setStroke(this.labelOutlineStroke);
/* 2763 */           g2.draw(bg);
/*      */         } 
/*      */         
/* 2766 */         g2.setPaint(this.labelPaint);
/* 2767 */         g2.setFont(this.labelFont);
/* 2768 */         TextUtilities.drawAlignedString(label, g2, x, y, TextAnchor.CENTER);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2774 */     g2.setComposite(originalComposite);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawLabels(Graphics2D g2, List keys, double totalValue, Rectangle2D plotArea, Rectangle2D linkArea, PiePlotState state) {
/* 2792 */     Composite originalComposite = g2.getComposite();
/* 2793 */     g2.setComposite(AlphaComposite.getInstance(3, 1.0F));
/*      */ 
/*      */ 
/*      */     
/* 2797 */     DefaultKeyedValues leftKeys = new DefaultKeyedValues();
/* 2798 */     DefaultKeyedValues rightKeys = new DefaultKeyedValues();
/*      */     
/* 2800 */     double runningTotal = 0.0D;
/* 2801 */     Iterator<Comparable> iterator = keys.iterator();
/* 2802 */     while (iterator.hasNext()) {
/* 2803 */       boolean include; Comparable key = iterator.next();
/*      */       
/* 2805 */       double v = 0.0D;
/* 2806 */       Number n = this.dataset.getValue(key);
/* 2807 */       if (n == null) {
/* 2808 */         include = !this.ignoreNullValues;
/*      */       } else {
/*      */         
/* 2811 */         v = n.doubleValue();
/* 2812 */         include = this.ignoreZeroValues ? ((v > 0.0D)) : ((v >= 0.0D));
/*      */       } 
/*      */       
/* 2815 */       if (include) {
/* 2816 */         runningTotal += v;
/*      */ 
/*      */         
/* 2819 */         double mid = this.startAngle + this.direction.getFactor() * (runningTotal - v / 2.0D) * 360.0D / totalValue;
/*      */         
/* 2821 */         if (Math.cos(Math.toRadians(mid)) < 0.0D) {
/* 2822 */           leftKeys.addValue(key, new Double(mid));
/*      */           continue;
/*      */         } 
/* 2825 */         rightKeys.addValue(key, new Double(mid));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2830 */     g2.setFont(getLabelFont());
/*      */ 
/*      */ 
/*      */     
/* 2834 */     double marginX = plotArea.getX();
/* 2835 */     double gap = plotArea.getWidth() * this.labelGap;
/* 2836 */     double ww = linkArea.getX() - gap - marginX;
/* 2837 */     float labelWidth = (float)this.labelPadding.trimWidth(ww);
/*      */ 
/*      */     
/* 2840 */     if (this.labelGenerator != null) {
/* 2841 */       drawLeftLabels((KeyedValues)leftKeys, g2, plotArea, linkArea, labelWidth, state);
/*      */       
/* 2843 */       drawRightLabels((KeyedValues)rightKeys, g2, plotArea, linkArea, labelWidth, state);
/*      */     } 
/*      */     
/* 2846 */     g2.setComposite(originalComposite);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawLeftLabels(KeyedValues leftKeys, Graphics2D g2, Rectangle2D plotArea, Rectangle2D linkArea, float maxLabelWidth, PiePlotState state) {
/* 2866 */     this.labelDistributor.clear();
/* 2867 */     double lGap = plotArea.getWidth() * this.labelGap;
/* 2868 */     double verticalLinkRadius = state.getLinkArea().getHeight() / 2.0D;
/* 2869 */     for (int i = 0; i < leftKeys.getItemCount(); i++) {
/* 2870 */       String label = this.labelGenerator.generateSectionLabel(this.dataset, leftKeys
/* 2871 */           .getKey(i));
/* 2872 */       if (label != null) {
/* 2873 */         TextBlock block = TextUtilities.createTextBlock(label, this.labelFont, this.labelPaint, maxLabelWidth, (TextMeasurer)new G2TextMeasurer(g2));
/*      */ 
/*      */         
/* 2876 */         TextBox labelBox = new TextBox(block);
/* 2877 */         labelBox.setBackgroundPaint(this.labelBackgroundPaint);
/* 2878 */         labelBox.setOutlinePaint(this.labelOutlinePaint);
/* 2879 */         labelBox.setOutlineStroke(this.labelOutlineStroke);
/* 2880 */         if (this.shadowGenerator == null) {
/* 2881 */           labelBox.setShadowPaint(this.labelShadowPaint);
/*      */         } else {
/*      */           
/* 2884 */           labelBox.setShadowPaint(null);
/*      */         } 
/* 2886 */         labelBox.setInteriorGap(this.labelPadding);
/* 2887 */         double theta = Math.toRadians(leftKeys
/* 2888 */             .getValue(i).doubleValue());
/* 2889 */         double baseY = state.getPieCenterY() - Math.sin(theta) * verticalLinkRadius;
/*      */         
/* 2891 */         double d1 = labelBox.getHeight(g2);
/*      */         
/* 2893 */         this.labelDistributor.addPieLabelRecord(new PieLabelRecord(leftKeys
/* 2894 */               .getKey(i), theta, baseY, labelBox, d1, lGap / 2.0D + lGap / 2.0D * 
/* 2895 */               -Math.cos(theta), 1.0D - 
/* 2896 */               getLabelLinkDepth() + 
/* 2897 */               getExplodePercent(leftKeys.getKey(i))));
/*      */       } 
/*      */     } 
/* 2900 */     double hh = plotArea.getHeight();
/* 2901 */     double gap = hh * getInteriorGap();
/* 2902 */     this.labelDistributor.distributeLabels(plotArea.getMinY() + gap, hh - 2.0D * gap);
/*      */     
/* 2904 */     for (int j = 0; j < this.labelDistributor.getItemCount(); j++) {
/* 2905 */       drawLeftLabel(g2, state, this.labelDistributor
/* 2906 */           .getPieLabelRecord(j));
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
/*      */   protected void drawRightLabels(KeyedValues keys, Graphics2D g2, Rectangle2D plotArea, Rectangle2D linkArea, float maxLabelWidth, PiePlotState state) {
/* 2925 */     this.labelDistributor.clear();
/* 2926 */     double lGap = plotArea.getWidth() * this.labelGap;
/* 2927 */     double verticalLinkRadius = state.getLinkArea().getHeight() / 2.0D;
/*      */     
/* 2929 */     for (int i = 0; i < keys.getItemCount(); i++) {
/* 2930 */       String label = this.labelGenerator.generateSectionLabel(this.dataset, keys
/* 2931 */           .getKey(i));
/*      */       
/* 2933 */       if (label != null) {
/* 2934 */         TextBlock block = TextUtilities.createTextBlock(label, this.labelFont, this.labelPaint, maxLabelWidth, (TextMeasurer)new G2TextMeasurer(g2));
/*      */ 
/*      */         
/* 2937 */         TextBox labelBox = new TextBox(block);
/* 2938 */         labelBox.setBackgroundPaint(this.labelBackgroundPaint);
/* 2939 */         labelBox.setOutlinePaint(this.labelOutlinePaint);
/* 2940 */         labelBox.setOutlineStroke(this.labelOutlineStroke);
/* 2941 */         if (this.shadowGenerator == null) {
/* 2942 */           labelBox.setShadowPaint(this.labelShadowPaint);
/*      */         } else {
/*      */           
/* 2945 */           labelBox.setShadowPaint(null);
/*      */         } 
/* 2947 */         labelBox.setInteriorGap(this.labelPadding);
/* 2948 */         double theta = Math.toRadians(keys.getValue(i).doubleValue());
/*      */         
/* 2950 */         double baseY = state.getPieCenterY() - Math.sin(theta) * verticalLinkRadius;
/* 2951 */         double d1 = labelBox.getHeight(g2);
/* 2952 */         this.labelDistributor.addPieLabelRecord(new PieLabelRecord(keys
/* 2953 */               .getKey(i), theta, baseY, labelBox, d1, lGap / 2.0D + lGap / 2.0D * 
/* 2954 */               Math.cos(theta), 1.0D - 
/* 2955 */               getLabelLinkDepth() + 
/* 2956 */               getExplodePercent(keys.getKey(i))));
/*      */       } 
/*      */     } 
/* 2959 */     double hh = plotArea.getHeight();
/* 2960 */     double gap = 0.0D;
/* 2961 */     this.labelDistributor.distributeLabels(plotArea.getMinY() + gap, hh - 2.0D * gap);
/*      */     
/* 2963 */     for (int j = 0; j < this.labelDistributor.getItemCount(); j++) {
/* 2964 */       drawRightLabel(g2, state, this.labelDistributor
/* 2965 */           .getPieLabelRecord(j));
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
/*      */   public LegendItemCollection getLegendItems() {
/* 2978 */     LegendItemCollection result = new LegendItemCollection();
/* 2979 */     if (this.dataset == null) {
/* 2980 */       return result;
/*      */     }
/* 2982 */     List keys = this.dataset.getKeys();
/* 2983 */     int section = 0;
/* 2984 */     Shape shape = getLegendItemShape();
/* 2985 */     Iterator<Comparable> iterator = keys.iterator();
/* 2986 */     while (iterator.hasNext()) {
/* 2987 */       boolean include; Comparable key = iterator.next();
/* 2988 */       Number n = this.dataset.getValue(key);
/*      */       
/* 2990 */       if (n == null) {
/* 2991 */         include = !this.ignoreNullValues;
/*      */       } else {
/*      */         
/* 2994 */         double v = n.doubleValue();
/* 2995 */         if (v == 0.0D) {
/* 2996 */           include = !this.ignoreZeroValues;
/*      */         } else {
/*      */           
/* 2999 */           include = (v > 0.0D);
/*      */         } 
/*      */       } 
/* 3002 */       if (include) {
/* 3003 */         String label = this.legendLabelGenerator.generateSectionLabel(this.dataset, key);
/*      */         
/* 3005 */         if (label != null) {
/* 3006 */           String description = label;
/* 3007 */           String toolTipText = null;
/* 3008 */           if (this.legendLabelToolTipGenerator != null)
/*      */           {
/* 3010 */             toolTipText = this.legendLabelToolTipGenerator.generateSectionLabel(this.dataset, key);
/*      */           }
/* 3012 */           String urlText = null;
/* 3013 */           if (this.legendLabelURLGenerator != null) {
/* 3014 */             urlText = this.legendLabelURLGenerator.generateURL(this.dataset, key, this.pieIndex);
/*      */           }
/*      */           
/* 3017 */           Paint paint = lookupSectionPaint(key);
/* 3018 */           Paint outlinePaint = lookupSectionOutlinePaint(key);
/* 3019 */           Stroke outlineStroke = lookupSectionOutlineStroke(key);
/* 3020 */           LegendItem item = new LegendItem(label, description, toolTipText, urlText, true, shape, true, paint, true, outlinePaint, outlineStroke, false, new Line2D.Float(), new BasicStroke(), Color.black);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3025 */           item.setDataset((Dataset)getDataset());
/* 3026 */           item.setSeriesIndex(this.dataset.getIndex(key));
/* 3027 */           item.setSeriesKey(key);
/* 3028 */           result.add(item);
/*      */         } 
/* 3030 */         section++;
/*      */         continue;
/*      */       } 
/* 3033 */       section++;
/*      */     } 
/*      */     
/* 3036 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlotType() {
/* 3046 */     return localizationResources.getString("Pie_Plot");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle2D getArcBounds(Rectangle2D unexploded, Rectangle2D exploded, double angle, double extent, double explodePercent) {
/* 3068 */     if (explodePercent == 0.0D) {
/* 3069 */       return unexploded;
/*      */     }
/* 3071 */     Arc2D arc1 = new Arc2D.Double(unexploded, angle, extent / 2.0D, 0);
/*      */     
/* 3073 */     Point2D point1 = arc1.getEndPoint();
/* 3074 */     Arc2D.Double arc2 = new Arc2D.Double(exploded, angle, extent / 2.0D, 0);
/*      */     
/* 3076 */     Point2D point2 = arc2.getEndPoint();
/* 3077 */     double deltaX = (point1.getX() - point2.getX()) * explodePercent;
/* 3078 */     double deltaY = (point1.getY() - point2.getY()) * explodePercent;
/* 3079 */     return new Rectangle2D.Double(unexploded.getX() - deltaX, unexploded
/* 3080 */         .getY() - deltaY, unexploded.getWidth(), unexploded
/* 3081 */         .getHeight());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawLeftLabel(Graphics2D g2, PiePlotState state, PieLabelRecord record) {
/* 3094 */     double anchorX = state.getLinkArea().getMinX();
/* 3095 */     double targetX = anchorX - record.getGap();
/* 3096 */     double targetY = record.getAllocatedY();
/*      */     
/* 3098 */     if (this.labelLinksVisible) {
/* 3099 */       double theta = record.getAngle();
/*      */       
/* 3101 */       double linkX = state.getPieCenterX() + Math.cos(theta) * state.getPieWRadius() * record.getLinkPercent();
/*      */       
/* 3103 */       double linkY = state.getPieCenterY() - Math.sin(theta) * state.getPieHRadius() * record.getLinkPercent();
/*      */       
/* 3105 */       double elbowX = state.getPieCenterX() + Math.cos(theta) * state.getLinkArea().getWidth() / 2.0D;
/*      */       
/* 3107 */       double elbowY = state.getPieCenterY() - Math.sin(theta) * state.getLinkArea().getHeight() / 2.0D;
/* 3108 */       double anchorY = elbowY;
/* 3109 */       g2.setPaint(this.labelLinkPaint);
/* 3110 */       g2.setStroke(this.labelLinkStroke);
/* 3111 */       PieLabelLinkStyle style = getLabelLinkStyle();
/* 3112 */       if (style.equals(PieLabelLinkStyle.STANDARD)) {
/* 3113 */         g2.draw(new Line2D.Double(linkX, linkY, elbowX, elbowY));
/* 3114 */         g2.draw(new Line2D.Double(anchorX, anchorY, elbowX, elbowY));
/* 3115 */         g2.draw(new Line2D.Double(anchorX, anchorY, targetX, targetY));
/*      */       }
/* 3117 */       else if (style.equals(PieLabelLinkStyle.QUAD_CURVE)) {
/* 3118 */         QuadCurve2D q = new QuadCurve2D.Float();
/* 3119 */         q.setCurve(targetX, targetY, anchorX, anchorY, elbowX, elbowY);
/* 3120 */         g2.draw(q);
/* 3121 */         g2.draw(new Line2D.Double(elbowX, elbowY, linkX, linkY));
/*      */       }
/* 3123 */       else if (style.equals(PieLabelLinkStyle.CUBIC_CURVE)) {
/* 3124 */         CubicCurve2D c = new CubicCurve2D.Float();
/* 3125 */         c.setCurve(targetX, targetY, anchorX, anchorY, elbowX, elbowY, linkX, linkY);
/*      */         
/* 3127 */         g2.draw(c);
/*      */       } 
/*      */     } 
/* 3130 */     TextBox tb = record.getLabel();
/* 3131 */     tb.draw(g2, (float)targetX, (float)targetY, RectangleAnchor.RIGHT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawRightLabel(Graphics2D g2, PiePlotState state, PieLabelRecord record) {
/* 3145 */     double anchorX = state.getLinkArea().getMaxX();
/* 3146 */     double targetX = anchorX + record.getGap();
/* 3147 */     double targetY = record.getAllocatedY();
/*      */     
/* 3149 */     if (this.labelLinksVisible) {
/* 3150 */       double theta = record.getAngle();
/*      */       
/* 3152 */       double linkX = state.getPieCenterX() + Math.cos(theta) * state.getPieWRadius() * record.getLinkPercent();
/*      */       
/* 3154 */       double linkY = state.getPieCenterY() - Math.sin(theta) * state.getPieHRadius() * record.getLinkPercent();
/*      */       
/* 3156 */       double elbowX = state.getPieCenterX() + Math.cos(theta) * state.getLinkArea().getWidth() / 2.0D;
/*      */       
/* 3158 */       double elbowY = state.getPieCenterY() - Math.sin(theta) * state.getLinkArea().getHeight() / 2.0D;
/* 3159 */       double anchorY = elbowY;
/* 3160 */       g2.setPaint(this.labelLinkPaint);
/* 3161 */       g2.setStroke(this.labelLinkStroke);
/* 3162 */       PieLabelLinkStyle style = getLabelLinkStyle();
/* 3163 */       if (style.equals(PieLabelLinkStyle.STANDARD)) {
/* 3164 */         g2.draw(new Line2D.Double(linkX, linkY, elbowX, elbowY));
/* 3165 */         g2.draw(new Line2D.Double(anchorX, anchorY, elbowX, elbowY));
/* 3166 */         g2.draw(new Line2D.Double(anchorX, anchorY, targetX, targetY));
/*      */       }
/* 3168 */       else if (style.equals(PieLabelLinkStyle.QUAD_CURVE)) {
/* 3169 */         QuadCurve2D q = new QuadCurve2D.Float();
/* 3170 */         q.setCurve(targetX, targetY, anchorX, anchorY, elbowX, elbowY);
/* 3171 */         g2.draw(q);
/* 3172 */         g2.draw(new Line2D.Double(elbowX, elbowY, linkX, linkY));
/*      */       }
/* 3174 */       else if (style.equals(PieLabelLinkStyle.CUBIC_CURVE)) {
/* 3175 */         CubicCurve2D c = new CubicCurve2D.Float();
/* 3176 */         c.setCurve(targetX, targetY, anchorX, anchorY, elbowX, elbowY, linkX, linkY);
/*      */         
/* 3178 */         g2.draw(c);
/*      */       } 
/*      */     } 
/*      */     
/* 3182 */     TextBox tb = record.getLabel();
/* 3183 */     tb.draw(g2, (float)targetX, (float)targetY, RectangleAnchor.LEFT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Point2D getArcCenter(PiePlotState state, Comparable key) {
/* 3201 */     Point2D center = new Point2D.Double(state.getPieCenterX(), state.getPieCenterY());
/*      */     
/* 3203 */     double ep = getExplodePercent(key);
/* 3204 */     double mep = getMaximumExplodePercent();
/* 3205 */     if (mep > 0.0D) {
/* 3206 */       ep /= mep;
/*      */     }
/* 3208 */     if (ep != 0.0D) {
/* 3209 */       double angle1, angle2; Rectangle2D pieArea = state.getPieArea();
/* 3210 */       Rectangle2D expPieArea = state.getExplodedPieArea();
/*      */       
/* 3212 */       Number n = this.dataset.getValue(key);
/* 3213 */       double value = n.doubleValue();
/*      */       
/* 3215 */       if (this.direction == Rotation.CLOCKWISE) {
/* 3216 */         angle1 = state.getLatestAngle();
/* 3217 */         angle2 = angle1 - value / state.getTotal() * 360.0D;
/* 3218 */       } else if (this.direction == Rotation.ANTICLOCKWISE) {
/* 3219 */         angle1 = state.getLatestAngle();
/* 3220 */         angle2 = angle1 + value / state.getTotal() * 360.0D;
/*      */       } else {
/* 3222 */         throw new IllegalStateException("Rotation type not recognised.");
/*      */       } 
/* 3224 */       double angle = angle2 - angle1;
/*      */       
/* 3226 */       Arc2D arc1 = new Arc2D.Double(pieArea, angle1, angle / 2.0D, 0);
/*      */       
/* 3228 */       Point2D point1 = arc1.getEndPoint();
/* 3229 */       Arc2D.Double arc2 = new Arc2D.Double(expPieArea, angle1, angle / 2.0D, 0);
/*      */       
/* 3231 */       Point2D point2 = arc2.getEndPoint();
/* 3232 */       double deltaX = (point1.getX() - point2.getX()) * ep;
/* 3233 */       double deltaY = (point1.getY() - point2.getY()) * ep;
/*      */ 
/*      */       
/* 3236 */       center = new Point2D.Double(state.getPieCenterX() - deltaX, state.getPieCenterY() - deltaY);
/*      */     } 
/*      */     
/* 3239 */     return center;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Paint lookupSectionPaint(Comparable key, PiePlotState state) {
/* 3256 */     Paint paint = lookupSectionPaint(key, getAutoPopulateSectionPaint());
/*      */ 
/*      */     
/* 3259 */     if (paint instanceof RadialGradientPaint) {
/* 3260 */       RadialGradientPaint rgp = (RadialGradientPaint)paint;
/* 3261 */       Point2D center = getArcCenter(state, key);
/* 3262 */       float radius = (float)Math.max(state.getPieHRadius(), state
/* 3263 */           .getPieWRadius());
/* 3264 */       float[] fractions = rgp.getFractions();
/* 3265 */       Color[] colors = rgp.getColors();
/* 3266 */       paint = new RadialGradientPaint(center, radius, fractions, colors);
/*      */     } 
/* 3268 */     return paint;
/*      */   }
/*      */ 
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
/* 3281 */     if (obj == this) {
/* 3282 */       return true;
/*      */     }
/* 3284 */     if (!(obj instanceof PiePlot)) {
/* 3285 */       return false;
/*      */     }
/* 3287 */     if (!super.equals(obj)) {
/* 3288 */       return false;
/*      */     }
/* 3290 */     PiePlot that = (PiePlot)obj;
/* 3291 */     if (this.pieIndex != that.pieIndex) {
/* 3292 */       return false;
/*      */     }
/* 3294 */     if (this.interiorGap != that.interiorGap) {
/* 3295 */       return false;
/*      */     }
/* 3297 */     if (this.circular != that.circular) {
/* 3298 */       return false;
/*      */     }
/* 3300 */     if (this.startAngle != that.startAngle) {
/* 3301 */       return false;
/*      */     }
/* 3303 */     if (this.direction != that.direction) {
/* 3304 */       return false;
/*      */     }
/* 3306 */     if (this.ignoreZeroValues != that.ignoreZeroValues) {
/* 3307 */       return false;
/*      */     }
/* 3309 */     if (this.ignoreNullValues != that.ignoreNullValues) {
/* 3310 */       return false;
/*      */     }
/* 3312 */     if (!PaintUtilities.equal(this.sectionPaint, that.sectionPaint)) {
/* 3313 */       return false;
/*      */     }
/* 3315 */     if (!ObjectUtilities.equal(this.sectionPaintMap, that.sectionPaintMap))
/*      */     {
/* 3317 */       return false;
/*      */     }
/* 3319 */     if (!PaintUtilities.equal(this.baseSectionPaint, that.baseSectionPaint))
/*      */     {
/* 3321 */       return false;
/*      */     }
/* 3323 */     if (this.sectionOutlinesVisible != that.sectionOutlinesVisible) {
/* 3324 */       return false;
/*      */     }
/* 3326 */     if (!PaintUtilities.equal(this.sectionOutlinePaint, that.sectionOutlinePaint))
/*      */     {
/* 3328 */       return false;
/*      */     }
/* 3330 */     if (!ObjectUtilities.equal(this.sectionOutlinePaintMap, that.sectionOutlinePaintMap))
/*      */     {
/* 3332 */       return false;
/*      */     }
/* 3334 */     if (!PaintUtilities.equal(this.baseSectionOutlinePaint, that.baseSectionOutlinePaint))
/*      */     {
/* 3336 */       return false;
/*      */     }
/* 3338 */     if (!ObjectUtilities.equal(this.sectionOutlineStroke, that.sectionOutlineStroke))
/*      */     {
/* 3340 */       return false;
/*      */     }
/* 3342 */     if (!ObjectUtilities.equal(this.sectionOutlineStrokeMap, that.sectionOutlineStrokeMap))
/*      */     {
/* 3344 */       return false;
/*      */     }
/* 3346 */     if (!ObjectUtilities.equal(this.baseSectionOutlineStroke, that.baseSectionOutlineStroke))
/*      */     {
/* 3348 */       return false;
/*      */     }
/* 3350 */     if (!PaintUtilities.equal(this.shadowPaint, that.shadowPaint)) {
/* 3351 */       return false;
/*      */     }
/* 3353 */     if (this.shadowXOffset != that.shadowXOffset) {
/* 3354 */       return false;
/*      */     }
/* 3356 */     if (this.shadowYOffset != that.shadowYOffset) {
/* 3357 */       return false;
/*      */     }
/* 3359 */     if (!ObjectUtilities.equal(this.explodePercentages, that.explodePercentages))
/*      */     {
/* 3361 */       return false;
/*      */     }
/* 3363 */     if (!ObjectUtilities.equal(this.labelGenerator, that.labelGenerator))
/*      */     {
/* 3365 */       return false;
/*      */     }
/* 3367 */     if (!ObjectUtilities.equal(this.labelFont, that.labelFont)) {
/* 3368 */       return false;
/*      */     }
/* 3370 */     if (!PaintUtilities.equal(this.labelPaint, that.labelPaint)) {
/* 3371 */       return false;
/*      */     }
/* 3373 */     if (!PaintUtilities.equal(this.labelBackgroundPaint, that.labelBackgroundPaint))
/*      */     {
/* 3375 */       return false;
/*      */     }
/* 3377 */     if (!PaintUtilities.equal(this.labelOutlinePaint, that.labelOutlinePaint))
/*      */     {
/* 3379 */       return false;
/*      */     }
/* 3381 */     if (!ObjectUtilities.equal(this.labelOutlineStroke, that.labelOutlineStroke))
/*      */     {
/* 3383 */       return false;
/*      */     }
/* 3385 */     if (!PaintUtilities.equal(this.labelShadowPaint, that.labelShadowPaint))
/*      */     {
/* 3387 */       return false;
/*      */     }
/* 3389 */     if (this.simpleLabels != that.simpleLabels) {
/* 3390 */       return false;
/*      */     }
/* 3392 */     if (!this.simpleLabelOffset.equals(that.simpleLabelOffset)) {
/* 3393 */       return false;
/*      */     }
/* 3395 */     if (!this.labelPadding.equals(that.labelPadding)) {
/* 3396 */       return false;
/*      */     }
/* 3398 */     if (this.maximumLabelWidth != that.maximumLabelWidth) {
/* 3399 */       return false;
/*      */     }
/* 3401 */     if (this.labelGap != that.labelGap) {
/* 3402 */       return false;
/*      */     }
/* 3404 */     if (this.labelLinkMargin != that.labelLinkMargin) {
/* 3405 */       return false;
/*      */     }
/* 3407 */     if (this.labelLinksVisible != that.labelLinksVisible) {
/* 3408 */       return false;
/*      */     }
/* 3410 */     if (!this.labelLinkStyle.equals(that.labelLinkStyle)) {
/* 3411 */       return false;
/*      */     }
/* 3413 */     if (!PaintUtilities.equal(this.labelLinkPaint, that.labelLinkPaint)) {
/* 3414 */       return false;
/*      */     }
/* 3416 */     if (!ObjectUtilities.equal(this.labelLinkStroke, that.labelLinkStroke))
/*      */     {
/* 3418 */       return false;
/*      */     }
/* 3420 */     if (!ObjectUtilities.equal(this.toolTipGenerator, that.toolTipGenerator))
/*      */     {
/* 3422 */       return false;
/*      */     }
/* 3424 */     if (!ObjectUtilities.equal(this.urlGenerator, that.urlGenerator)) {
/* 3425 */       return false;
/*      */     }
/* 3427 */     if (this.minimumArcAngleToDraw != that.minimumArcAngleToDraw) {
/* 3428 */       return false;
/*      */     }
/* 3430 */     if (!ShapeUtilities.equal(this.legendItemShape, that.legendItemShape)) {
/* 3431 */       return false;
/*      */     }
/* 3433 */     if (!ObjectUtilities.equal(this.legendLabelGenerator, that.legendLabelGenerator))
/*      */     {
/* 3435 */       return false;
/*      */     }
/* 3437 */     if (!ObjectUtilities.equal(this.legendLabelToolTipGenerator, that.legendLabelToolTipGenerator))
/*      */     {
/* 3439 */       return false;
/*      */     }
/* 3441 */     if (!ObjectUtilities.equal(this.legendLabelURLGenerator, that.legendLabelURLGenerator))
/*      */     {
/* 3443 */       return false;
/*      */     }
/* 3445 */     if (this.autoPopulateSectionPaint != that.autoPopulateSectionPaint) {
/* 3446 */       return false;
/*      */     }
/* 3448 */     if (this.autoPopulateSectionOutlinePaint != that.autoPopulateSectionOutlinePaint)
/*      */     {
/* 3450 */       return false;
/*      */     }
/* 3452 */     if (this.autoPopulateSectionOutlineStroke != that.autoPopulateSectionOutlineStroke)
/*      */     {
/* 3454 */       return false;
/*      */     }
/* 3456 */     if (!ObjectUtilities.equal(this.shadowGenerator, that.shadowGenerator))
/*      */     {
/* 3458 */       return false;
/*      */     }
/*      */     
/* 3461 */     return true;
/*      */   }
/*      */ 
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
/* 3474 */     PiePlot clone = (PiePlot)super.clone();
/* 3475 */     clone.sectionPaintMap = (PaintMap)this.sectionPaintMap.clone();
/* 3476 */     clone
/* 3477 */       .sectionOutlinePaintMap = (PaintMap)this.sectionOutlinePaintMap.clone();
/* 3478 */     clone
/* 3479 */       .sectionOutlineStrokeMap = (StrokeMap)this.sectionOutlineStrokeMap.clone();
/* 3480 */     clone.explodePercentages = new TreeMap<Object, Object>(this.explodePercentages);
/*      */     
/* 3482 */     if (this.labelGenerator != null) {
/* 3483 */       clone
/* 3484 */         .labelGenerator = (PieSectionLabelGenerator)ObjectUtilities.clone(this.labelGenerator);
/*      */     }
/* 3486 */     if (clone.dataset != null) {
/* 3487 */       clone.dataset.addChangeListener(clone);
/*      */     }
/* 3489 */     if (this.urlGenerator instanceof org.jfree.util.PublicCloneable) {
/* 3490 */       clone.urlGenerator = (PieURLGenerator)ObjectUtilities.clone(this.urlGenerator);
/*      */     }
/*      */     
/* 3493 */     clone.legendItemShape = ShapeUtilities.clone(this.legendItemShape);
/* 3494 */     if (this.legendLabelGenerator != null) {
/* 3495 */       clone
/* 3496 */         .legendLabelGenerator = (PieSectionLabelGenerator)ObjectUtilities.clone(this.legendLabelGenerator);
/*      */     }
/* 3498 */     if (this.legendLabelToolTipGenerator != null) {
/* 3499 */       clone
/* 3500 */         .legendLabelToolTipGenerator = (PieSectionLabelGenerator)ObjectUtilities.clone(this.legendLabelToolTipGenerator);
/*      */     }
/* 3502 */     if (this.legendLabelURLGenerator instanceof org.jfree.util.PublicCloneable) {
/* 3503 */       clone
/* 3504 */         .legendLabelURLGenerator = (PieURLGenerator)ObjectUtilities.clone(this.legendLabelURLGenerator);
/*      */     }
/* 3506 */     return clone;
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
/* 3517 */     stream.defaultWriteObject();
/* 3518 */     SerialUtilities.writePaint(this.sectionPaint, stream);
/* 3519 */     SerialUtilities.writePaint(this.baseSectionPaint, stream);
/* 3520 */     SerialUtilities.writePaint(this.sectionOutlinePaint, stream);
/* 3521 */     SerialUtilities.writePaint(this.baseSectionOutlinePaint, stream);
/* 3522 */     SerialUtilities.writeStroke(this.sectionOutlineStroke, stream);
/* 3523 */     SerialUtilities.writeStroke(this.baseSectionOutlineStroke, stream);
/* 3524 */     SerialUtilities.writePaint(this.shadowPaint, stream);
/* 3525 */     SerialUtilities.writePaint(this.labelPaint, stream);
/* 3526 */     SerialUtilities.writePaint(this.labelBackgroundPaint, stream);
/* 3527 */     SerialUtilities.writePaint(this.labelOutlinePaint, stream);
/* 3528 */     SerialUtilities.writeStroke(this.labelOutlineStroke, stream);
/* 3529 */     SerialUtilities.writePaint(this.labelShadowPaint, stream);
/* 3530 */     SerialUtilities.writePaint(this.labelLinkPaint, stream);
/* 3531 */     SerialUtilities.writeStroke(this.labelLinkStroke, stream);
/* 3532 */     SerialUtilities.writeShape(this.legendItemShape, stream);
/*      */   }
/*      */ 
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
/* 3545 */     stream.defaultReadObject();
/* 3546 */     this.sectionPaint = SerialUtilities.readPaint(stream);
/* 3547 */     this.baseSectionPaint = SerialUtilities.readPaint(stream);
/* 3548 */     this.sectionOutlinePaint = SerialUtilities.readPaint(stream);
/* 3549 */     this.baseSectionOutlinePaint = SerialUtilities.readPaint(stream);
/* 3550 */     this.sectionOutlineStroke = SerialUtilities.readStroke(stream);
/* 3551 */     this.baseSectionOutlineStroke = SerialUtilities.readStroke(stream);
/* 3552 */     this.shadowPaint = SerialUtilities.readPaint(stream);
/* 3553 */     this.labelPaint = SerialUtilities.readPaint(stream);
/* 3554 */     this.labelBackgroundPaint = SerialUtilities.readPaint(stream);
/* 3555 */     this.labelOutlinePaint = SerialUtilities.readPaint(stream);
/* 3556 */     this.labelOutlineStroke = SerialUtilities.readStroke(stream);
/* 3557 */     this.labelShadowPaint = SerialUtilities.readPaint(stream);
/* 3558 */     this.labelLinkPaint = SerialUtilities.readPaint(stream);
/* 3559 */     this.labelLinkStroke = SerialUtilities.readStroke(stream);
/* 3560 */     this.legendItemShape = SerialUtilities.readShape(stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getSectionPaint(int section) {
/* 3602 */     Comparable key = getSectionKey(section);
/* 3603 */     return getSectionPaint(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSectionPaint(int section, Paint paint) {
/* 3616 */     Comparable key = getSectionKey(section);
/* 3617 */     setSectionPaint(key, paint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getSectionOutlinePaint() {
/* 3632 */     return this.sectionOutlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSectionOutlinePaint(Paint paint) {
/* 3649 */     this.sectionOutlinePaint = paint;
/* 3650 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getSectionOutlinePaint(int section) {
/* 3663 */     Comparable key = getSectionKey(section);
/* 3664 */     return getSectionOutlinePaint(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSectionOutlinePaint(int section, Paint paint) {
/* 3678 */     Comparable key = getSectionKey(section);
/* 3679 */     setSectionOutlinePaint(key, paint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getSectionOutlineStroke() {
/* 3694 */     return this.sectionOutlineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSectionOutlineStroke(Stroke stroke) {
/* 3711 */     this.sectionOutlineStroke = stroke;
/* 3712 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getSectionOutlineStroke(int section) {
/* 3725 */     Comparable key = getSectionKey(section);
/* 3726 */     return getSectionOutlineStroke(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSectionOutlineStroke(int section, Stroke stroke) {
/* 3740 */     Comparable key = getSectionKey(section);
/* 3741 */     setSectionOutlineStroke(key, stroke);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getExplodePercent(int section) {
/* 3754 */     Comparable key = getSectionKey(section);
/* 3755 */     return getExplodePercent(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExplodePercent(int section, double percent) {
/* 3768 */     Comparable key = getSectionKey(section);
/* 3769 */     setExplodePercent(key, percent);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/PiePlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */