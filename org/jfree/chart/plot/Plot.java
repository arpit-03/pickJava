/*      */ package org.jfree.chart.plot;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Font;
/*      */ import java.awt.GradientPaint;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Image;
/*      */ import java.awt.Paint;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Ellipse2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import org.jfree.chart.LegendItemCollection;
/*      */ import org.jfree.chart.LegendItemSource;
/*      */ import org.jfree.chart.axis.AxisLocation;
/*      */ import org.jfree.chart.entity.ChartEntity;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.entity.PlotEntity;
/*      */ import org.jfree.chart.event.AnnotationChangeEvent;
/*      */ import org.jfree.chart.event.AnnotationChangeListener;
/*      */ import org.jfree.chart.event.AxisChangeEvent;
/*      */ import org.jfree.chart.event.AxisChangeListener;
/*      */ import org.jfree.chart.event.ChartChangeEventType;
/*      */ import org.jfree.chart.event.MarkerChangeEvent;
/*      */ import org.jfree.chart.event.MarkerChangeListener;
/*      */ import org.jfree.chart.event.PlotChangeEvent;
/*      */ import org.jfree.chart.event.PlotChangeListener;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.general.DatasetChangeEvent;
/*      */ import org.jfree.data.general.DatasetChangeListener;
/*      */ import org.jfree.data.general.DatasetGroup;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.text.G2TextMeasurer;
/*      */ import org.jfree.text.TextBlock;
/*      */ import org.jfree.text.TextBlockAnchor;
/*      */ import org.jfree.text.TextMeasurer;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.ui.Align;
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
/*      */ public abstract class Plot
/*      */   implements AxisChangeListener, DatasetChangeListener, AnnotationChangeListener, MarkerChangeListener, LegendItemSource, PublicCloneable, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -8831571430103671324L;
/*  205 */   public static final Number ZERO = new Integer(0);
/*      */ 
/*      */   
/*  208 */   public static final RectangleInsets DEFAULT_INSETS = new RectangleInsets(4.0D, 8.0D, 4.0D, 8.0D);
/*      */ 
/*      */ 
/*      */   
/*  212 */   public static final Stroke DEFAULT_OUTLINE_STROKE = new BasicStroke(0.5F, 1, 1);
/*      */ 
/*      */ 
/*      */   
/*  216 */   public static final Paint DEFAULT_OUTLINE_PAINT = Color.gray;
/*      */ 
/*      */   
/*      */   public static final float DEFAULT_FOREGROUND_ALPHA = 1.0F;
/*      */ 
/*      */   
/*      */   public static final float DEFAULT_BACKGROUND_ALPHA = 1.0F;
/*      */ 
/*      */   
/*  225 */   public static final Paint DEFAULT_BACKGROUND_PAINT = Color.white;
/*      */ 
/*      */   
/*      */   public static final int MINIMUM_WIDTH_TO_DRAW = 10;
/*      */ 
/*      */   
/*      */   public static final int MINIMUM_HEIGHT_TO_DRAW = 10;
/*      */ 
/*      */   
/*  234 */   public static final Shape DEFAULT_LEGEND_ITEM_BOX = new Rectangle2D.Double(-4.0D, -4.0D, 8.0D, 8.0D);
/*      */ 
/*      */ 
/*      */   
/*  238 */   public static final Shape DEFAULT_LEGEND_ITEM_CIRCLE = new Ellipse2D.Double(-4.0D, -4.0D, 8.0D, 8.0D);
/*      */ 
/*      */ 
/*      */   
/*      */   private Plot parent;
/*      */ 
/*      */ 
/*      */   
/*      */   private DatasetGroup datasetGroup;
/*      */ 
/*      */ 
/*      */   
/*      */   private String noDataMessage;
/*      */ 
/*      */ 
/*      */   
/*      */   private Font noDataMessageFont;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint noDataMessagePaint;
/*      */ 
/*      */   
/*      */   private RectangleInsets insets;
/*      */ 
/*      */   
/*      */   private boolean outlineVisible;
/*      */ 
/*      */   
/*      */   private transient Stroke outlineStroke;
/*      */ 
/*      */   
/*      */   private transient Paint outlinePaint;
/*      */ 
/*      */   
/*      */   private transient Paint backgroundPaint;
/*      */ 
/*      */   
/*      */   private transient Image backgroundImage;
/*      */ 
/*      */   
/*  279 */   private int backgroundImageAlignment = 15;
/*      */ 
/*      */   
/*  282 */   private float backgroundImageAlpha = 0.5F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float foregroundAlpha;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float backgroundAlpha;
/*      */ 
/*      */ 
/*      */   
/*      */   private DrawingSupplier drawingSupplier;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient EventListenerList listenerList;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean notify;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Plot() {
/*  310 */     this.parent = null;
/*  311 */     this.insets = DEFAULT_INSETS;
/*  312 */     this.backgroundPaint = DEFAULT_BACKGROUND_PAINT;
/*  313 */     this.backgroundAlpha = 1.0F;
/*  314 */     this.backgroundImage = null;
/*  315 */     this.outlineVisible = true;
/*  316 */     this.outlineStroke = DEFAULT_OUTLINE_STROKE;
/*  317 */     this.outlinePaint = DEFAULT_OUTLINE_PAINT;
/*  318 */     this.foregroundAlpha = 1.0F;
/*      */     
/*  320 */     this.noDataMessage = null;
/*  321 */     this.noDataMessageFont = new Font("SansSerif", 0, 12);
/*  322 */     this.noDataMessagePaint = Color.black;
/*      */     
/*  324 */     this.drawingSupplier = new DefaultDrawingSupplier();
/*      */     
/*  326 */     this.notify = true;
/*  327 */     this.listenerList = new EventListenerList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DatasetGroup getDatasetGroup() {
/*  339 */     return this.datasetGroup;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setDatasetGroup(DatasetGroup group) {
/*  350 */     this.datasetGroup = group;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNoDataMessage() {
/*  364 */     return this.noDataMessage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoDataMessage(String message) {
/*  377 */     this.noDataMessage = message;
/*  378 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getNoDataMessageFont() {
/*  390 */     return this.noDataMessageFont;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoDataMessageFont(Font font) {
/*  402 */     ParamChecks.nullNotPermitted(font, "font");
/*  403 */     this.noDataMessageFont = font;
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
/*      */   public Paint getNoDataMessagePaint() {
/*  416 */     return this.noDataMessagePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoDataMessagePaint(Paint paint) {
/*  428 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  429 */     this.noDataMessagePaint = paint;
/*  430 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String getPlotType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Plot getParent() {
/*  454 */     return this.parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setParent(Plot parent) {
/*  466 */     this.parent = parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Plot getRootPlot() {
/*  478 */     Plot p = getParent();
/*  479 */     if (p == null) {
/*  480 */       return this;
/*      */     }
/*  482 */     return p.getRootPlot();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSubplot() {
/*  497 */     return (getParent() != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleInsets getInsets() {
/*  508 */     return this.insets;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInsets(RectangleInsets insets) {
/*  521 */     setInsets(insets, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInsets(RectangleInsets insets, boolean notify) {
/*  536 */     ParamChecks.nullNotPermitted(insets, "insets");
/*  537 */     if (!this.insets.equals(insets)) {
/*  538 */       this.insets = insets;
/*  539 */       if (notify) {
/*  540 */         fireChangeEvent();
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
/*      */   public Paint getBackgroundPaint() {
/*  554 */     return this.backgroundPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBackgroundPaint(Paint paint) {
/*  567 */     if (paint == null) {
/*  568 */       if (this.backgroundPaint != null) {
/*  569 */         this.backgroundPaint = null;
/*  570 */         fireChangeEvent();
/*      */       } 
/*      */     } else {
/*      */       
/*  574 */       if (this.backgroundPaint != null && 
/*  575 */         this.backgroundPaint.equals(paint)) {
/*      */         return;
/*      */       }
/*      */       
/*  579 */       this.backgroundPaint = paint;
/*  580 */       fireChangeEvent();
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
/*      */   public float getBackgroundAlpha() {
/*  593 */     return this.backgroundAlpha;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBackgroundAlpha(float alpha) {
/*  605 */     if (this.backgroundAlpha != alpha) {
/*  606 */       this.backgroundAlpha = alpha;
/*  607 */       fireChangeEvent();
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
/*      */   public DrawingSupplier getDrawingSupplier() {
/*      */     DrawingSupplier result;
/*  620 */     Plot p = getParent();
/*  621 */     if (p != null) {
/*  622 */       result = p.getDrawingSupplier();
/*      */     } else {
/*      */       
/*  625 */       result = this.drawingSupplier;
/*      */     } 
/*  627 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDrawingSupplier(DrawingSupplier supplier) {
/*  643 */     this.drawingSupplier = supplier;
/*  644 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDrawingSupplier(DrawingSupplier supplier, boolean notify) {
/*  663 */     this.drawingSupplier = supplier;
/*  664 */     if (notify) {
/*  665 */       fireChangeEvent();
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
/*      */   public Image getBackgroundImage() {
/*  678 */     return this.backgroundImage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBackgroundImage(Image image) {
/*  690 */     this.backgroundImage = image;
/*  691 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBackgroundImageAlignment() {
/*  704 */     return this.backgroundImageAlignment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBackgroundImageAlignment(int alignment) {
/*  718 */     if (this.backgroundImageAlignment != alignment) {
/*  719 */       this.backgroundImageAlignment = alignment;
/*  720 */       fireChangeEvent();
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
/*      */   public float getBackgroundImageAlpha() {
/*  734 */     return this.backgroundImageAlpha;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBackgroundImageAlpha(float alpha) {
/*  749 */     if (alpha < 0.0F || alpha > 1.0F) {
/*  750 */       throw new IllegalArgumentException("The 'alpha' value must be in the range 0.0f to 1.0f.");
/*      */     }
/*      */     
/*  753 */     if (this.backgroundImageAlpha != alpha) {
/*  754 */       this.backgroundImageAlpha = alpha;
/*  755 */       fireChangeEvent();
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
/*      */   public boolean isOutlineVisible() {
/*  773 */     return this.outlineVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutlineVisible(boolean visible) {
/*  787 */     this.outlineVisible = visible;
/*  788 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getOutlineStroke() {
/*  799 */     return this.outlineStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutlineStroke(Stroke stroke) {
/*  812 */     if (stroke == null) {
/*  813 */       if (this.outlineStroke != null) {
/*  814 */         this.outlineStroke = null;
/*  815 */         fireChangeEvent();
/*      */       } 
/*      */     } else {
/*      */       
/*  819 */       if (this.outlineStroke != null && 
/*  820 */         this.outlineStroke.equals(stroke)) {
/*      */         return;
/*      */       }
/*      */       
/*  824 */       this.outlineStroke = stroke;
/*  825 */       fireChangeEvent();
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
/*      */   public Paint getOutlinePaint() {
/*  837 */     return this.outlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutlinePaint(Paint paint) {
/*  850 */     if (paint == null) {
/*  851 */       if (this.outlinePaint != null) {
/*  852 */         this.outlinePaint = null;
/*  853 */         fireChangeEvent();
/*      */       } 
/*      */     } else {
/*      */       
/*  857 */       if (this.outlinePaint != null && 
/*  858 */         this.outlinePaint.equals(paint)) {
/*      */         return;
/*      */       }
/*      */       
/*  862 */       this.outlinePaint = paint;
/*  863 */       fireChangeEvent();
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
/*      */   public float getForegroundAlpha() {
/*  875 */     return this.foregroundAlpha;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setForegroundAlpha(float alpha) {
/*  887 */     if (this.foregroundAlpha != alpha) {
/*  888 */       this.foregroundAlpha = alpha;
/*  889 */       fireChangeEvent();
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
/*  902 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNotify() {
/*  916 */     return this.notify;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNotify(boolean notify) {
/*  930 */     this.notify = notify;
/*      */     
/*  932 */     if (notify) {
/*  933 */       notifyListeners(new PlotChangeEvent(this));
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
/*      */   public void addChangeListener(PlotChangeListener listener) {
/*  945 */     this.listenerList.add(PlotChangeListener.class, listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeChangeListener(PlotChangeListener listener) {
/*  956 */     this.listenerList.remove(PlotChangeListener.class, listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void notifyListeners(PlotChangeEvent event) {
/*  967 */     if (!this.notify) {
/*      */       return;
/*      */     }
/*  970 */     Object[] listeners = this.listenerList.getListenerList();
/*  971 */     for (int i = listeners.length - 2; i >= 0; i -= 2) {
/*  972 */       if (listeners[i] == PlotChangeListener.class) {
/*  973 */         ((PlotChangeListener)listeners[i + 1]).plotChanged(event);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fireChangeEvent() {
/*  984 */     notifyListeners(new PlotChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void draw(Graphics2D paramGraphics2D, Rectangle2D paramRectangle2D, Point2D paramPoint2D, PlotState paramPlotState, PlotRenderingInfo paramPlotRenderingInfo);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1020 */     fillBackground(g2, area);
/* 1021 */     drawBackgroundImage(g2, area);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fillBackground(Graphics2D g2, Rectangle2D area) {
/* 1035 */     fillBackground(g2, area, PlotOrientation.VERTICAL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fillBackground(Graphics2D g2, Rectangle2D area, PlotOrientation orientation) {
/* 1052 */     ParamChecks.nullNotPermitted(orientation, "orientation");
/* 1053 */     if (this.backgroundPaint == null) {
/*      */       return;
/*      */     }
/* 1056 */     Paint p = this.backgroundPaint;
/* 1057 */     if (p instanceof GradientPaint) {
/* 1058 */       GradientPaint gp = (GradientPaint)p;
/* 1059 */       if (orientation == PlotOrientation.VERTICAL) {
/*      */ 
/*      */ 
/*      */         
/* 1063 */         p = new GradientPaint((float)area.getCenterX(), (float)area.getMaxY(), gp.getColor1(), (float)area.getCenterX(), (float)area.getMinY(), gp.getColor2());
/*      */       }
/* 1065 */       else if (orientation == PlotOrientation.HORIZONTAL) {
/*      */ 
/*      */ 
/*      */         
/* 1069 */         p = new GradientPaint((float)area.getMinX(), (float)area.getCenterY(), gp.getColor1(), (float)area.getMaxX(), (float)area.getCenterY(), gp.getColor2());
/*      */       } 
/*      */     } 
/* 1072 */     Composite originalComposite = g2.getComposite();
/* 1073 */     g2.setComposite(AlphaComposite.getInstance(3, this.backgroundAlpha));
/*      */     
/* 1075 */     g2.setPaint(p);
/* 1076 */     g2.fill(area);
/* 1077 */     g2.setComposite(originalComposite);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawBackgroundImage(Graphics2D g2, Rectangle2D area) {
/* 1092 */     if (this.backgroundImage == null) {
/*      */       return;
/*      */     }
/* 1095 */     Composite savedComposite = g2.getComposite();
/* 1096 */     g2.setComposite(AlphaComposite.getInstance(3, this.backgroundImageAlpha));
/*      */ 
/*      */ 
/*      */     
/* 1100 */     Rectangle2D dest = new Rectangle2D.Double(0.0D, 0.0D, this.backgroundImage.getWidth(null), this.backgroundImage.getHeight(null));
/* 1101 */     Align.align(dest, area, this.backgroundImageAlignment);
/* 1102 */     Shape savedClip = g2.getClip();
/* 1103 */     g2.clip(area);
/* 1104 */     g2.drawImage(this.backgroundImage, (int)dest.getX(), 
/* 1105 */         (int)dest.getY(), (int)dest.getWidth() + 1, 
/* 1106 */         (int)dest.getHeight() + 1, null);
/* 1107 */     g2.setClip(savedClip);
/* 1108 */     g2.setComposite(savedComposite);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawOutline(Graphics2D g2, Rectangle2D area) {
/* 1121 */     if (!this.outlineVisible) {
/*      */       return;
/*      */     }
/* 1124 */     if (this.outlineStroke != null && this.outlinePaint != null) {
/* 1125 */       g2.setStroke(this.outlineStroke);
/* 1126 */       g2.setPaint(this.outlinePaint);
/* 1127 */       Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
/* 1128 */       g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/* 1129 */       g2.draw(area);
/* 1130 */       g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawNoDataMessage(Graphics2D g2, Rectangle2D area) {
/* 1141 */     Shape savedClip = g2.getClip();
/* 1142 */     g2.clip(area);
/* 1143 */     String message = this.noDataMessage;
/* 1144 */     if (message != null) {
/* 1145 */       g2.setFont(this.noDataMessageFont);
/* 1146 */       g2.setPaint(this.noDataMessagePaint);
/* 1147 */       TextBlock block = TextUtilities.createTextBlock(this.noDataMessage, this.noDataMessageFont, this.noDataMessagePaint, 0.9F * 
/*      */           
/* 1149 */           (float)area.getWidth(), (TextMeasurer)new G2TextMeasurer(g2));
/*      */       
/* 1151 */       block.draw(g2, (float)area.getCenterX(), 
/* 1152 */           (float)area.getCenterY(), TextBlockAnchor.CENTER);
/*      */     } 
/* 1154 */     g2.setClip(savedClip);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void createAndAddEntity(Rectangle2D dataArea, PlotRenderingInfo plotState, String toolTip, String urlText) {
/* 1173 */     if (plotState != null && plotState.getOwner() != null) {
/* 1174 */       EntityCollection e = plotState.getOwner().getEntityCollection();
/* 1175 */       if (e != null) {
/* 1176 */         e.add((ChartEntity)new PlotEntity(dataArea, this, toolTip, urlText));
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
/*      */   public void zoom(double percent) {}
/*      */ 
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
/* 1215 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void axisChanged(AxisChangeEvent event) {
/* 1225 */     fireChangeEvent();
/*      */   }
/*      */ 
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
/* 1238 */     PlotChangeEvent newEvent = new PlotChangeEvent(this);
/* 1239 */     newEvent.setType(ChartChangeEventType.DATASET_UPDATED);
/* 1240 */     notifyListeners(newEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markerChanged(MarkerChangeEvent event) {
/* 1253 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double getRectX(double x, double w1, double w2, RectangleEdge edge) {
/* 1269 */     double result = x;
/* 1270 */     if (edge == RectangleEdge.LEFT) {
/* 1271 */       result += w1;
/*      */     }
/* 1273 */     else if (edge == RectangleEdge.RIGHT) {
/* 1274 */       result += w2;
/*      */     } 
/* 1276 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double getRectY(double y, double h1, double h2, RectangleEdge edge) {
/* 1293 */     double result = y;
/* 1294 */     if (edge == RectangleEdge.TOP) {
/* 1295 */       result += h1;
/*      */     }
/* 1297 */     else if (edge == RectangleEdge.BOTTOM) {
/* 1298 */       result += h2;
/*      */     } 
/* 1300 */     return result;
/*      */   }
/*      */ 
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
/* 1313 */     if (obj == this) {
/* 1314 */       return true;
/*      */     }
/* 1316 */     if (!(obj instanceof Plot)) {
/* 1317 */       return false;
/*      */     }
/* 1319 */     Plot that = (Plot)obj;
/* 1320 */     if (!ObjectUtilities.equal(this.noDataMessage, that.noDataMessage)) {
/* 1321 */       return false;
/*      */     }
/* 1323 */     if (!ObjectUtilities.equal(this.noDataMessageFont, that.noDataMessageFont))
/*      */     {
/*      */       
/* 1326 */       return false;
/*      */     }
/* 1328 */     if (!PaintUtilities.equal(this.noDataMessagePaint, that.noDataMessagePaint))
/*      */     {
/* 1330 */       return false;
/*      */     }
/* 1332 */     if (!ObjectUtilities.equal(this.insets, that.insets)) {
/* 1333 */       return false;
/*      */     }
/* 1335 */     if (this.outlineVisible != that.outlineVisible) {
/* 1336 */       return false;
/*      */     }
/* 1338 */     if (!ObjectUtilities.equal(this.outlineStroke, that.outlineStroke)) {
/* 1339 */       return false;
/*      */     }
/* 1341 */     if (!PaintUtilities.equal(this.outlinePaint, that.outlinePaint)) {
/* 1342 */       return false;
/*      */     }
/* 1344 */     if (!PaintUtilities.equal(this.backgroundPaint, that.backgroundPaint)) {
/* 1345 */       return false;
/*      */     }
/* 1347 */     if (!ObjectUtilities.equal(this.backgroundImage, that.backgroundImage))
/*      */     {
/* 1349 */       return false;
/*      */     }
/* 1351 */     if (this.backgroundImageAlignment != that.backgroundImageAlignment) {
/* 1352 */       return false;
/*      */     }
/* 1354 */     if (this.backgroundImageAlpha != that.backgroundImageAlpha) {
/* 1355 */       return false;
/*      */     }
/* 1357 */     if (this.foregroundAlpha != that.foregroundAlpha) {
/* 1358 */       return false;
/*      */     }
/* 1360 */     if (this.backgroundAlpha != that.backgroundAlpha) {
/* 1361 */       return false;
/*      */     }
/* 1363 */     if (!this.drawingSupplier.equals(that.drawingSupplier)) {
/* 1364 */       return false;
/*      */     }
/* 1366 */     if (this.notify != that.notify) {
/* 1367 */       return false;
/*      */     }
/* 1369 */     return true;
/*      */   }
/*      */ 
/*      */ 
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
/* 1383 */     Plot clone = (Plot)super.clone();
/*      */ 
/*      */     
/* 1386 */     if (this.datasetGroup != null) {
/* 1387 */       clone
/* 1388 */         .datasetGroup = (DatasetGroup)ObjectUtilities.clone(this.datasetGroup);
/*      */     }
/* 1390 */     clone
/* 1391 */       .drawingSupplier = (DrawingSupplier)ObjectUtilities.clone(this.drawingSupplier);
/* 1392 */     clone.listenerList = new EventListenerList();
/* 1393 */     return clone;
/*      */   }
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
/* 1405 */     stream.defaultWriteObject();
/* 1406 */     SerialUtilities.writePaint(this.noDataMessagePaint, stream);
/* 1407 */     SerialUtilities.writeStroke(this.outlineStroke, stream);
/* 1408 */     SerialUtilities.writePaint(this.outlinePaint, stream);
/*      */     
/* 1410 */     SerialUtilities.writePaint(this.backgroundPaint, stream);
/*      */   }
/*      */ 
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
/* 1423 */     stream.defaultReadObject();
/* 1424 */     this.noDataMessagePaint = SerialUtilities.readPaint(stream);
/* 1425 */     this.outlineStroke = SerialUtilities.readStroke(stream);
/* 1426 */     this.outlinePaint = SerialUtilities.readPaint(stream);
/*      */     
/* 1428 */     this.backgroundPaint = SerialUtilities.readPaint(stream);
/*      */     
/* 1430 */     this.listenerList = new EventListenerList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RectangleEdge resolveDomainAxisLocation(AxisLocation location, PlotOrientation orientation) {
/* 1445 */     ParamChecks.nullNotPermitted(location, "location");
/* 1446 */     ParamChecks.nullNotPermitted(orientation, "orientation");
/*      */     
/* 1448 */     RectangleEdge result = null;
/* 1449 */     if (location == AxisLocation.TOP_OR_RIGHT) {
/* 1450 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 1451 */         result = RectangleEdge.RIGHT;
/*      */       }
/* 1453 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 1454 */         result = RectangleEdge.TOP;
/*      */       }
/*      */     
/* 1457 */     } else if (location == AxisLocation.TOP_OR_LEFT) {
/* 1458 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 1459 */         result = RectangleEdge.LEFT;
/*      */       }
/* 1461 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 1462 */         result = RectangleEdge.TOP;
/*      */       }
/*      */     
/* 1465 */     } else if (location == AxisLocation.BOTTOM_OR_RIGHT) {
/* 1466 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 1467 */         result = RectangleEdge.RIGHT;
/*      */       }
/* 1469 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 1470 */         result = RectangleEdge.BOTTOM;
/*      */       }
/*      */     
/* 1473 */     } else if (location == AxisLocation.BOTTOM_OR_LEFT) {
/* 1474 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 1475 */         result = RectangleEdge.LEFT;
/*      */       }
/* 1477 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 1478 */         result = RectangleEdge.BOTTOM;
/*      */       } 
/*      */     } 
/*      */     
/* 1482 */     if (result == null) {
/* 1483 */       throw new IllegalStateException("resolveDomainAxisLocation()");
/*      */     }
/* 1485 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RectangleEdge resolveRangeAxisLocation(AxisLocation location, PlotOrientation orientation) {
/* 1500 */     ParamChecks.nullNotPermitted(location, "location");
/* 1501 */     ParamChecks.nullNotPermitted(orientation, "orientation");
/*      */     
/* 1503 */     RectangleEdge result = null;
/* 1504 */     if (location == AxisLocation.TOP_OR_RIGHT) {
/* 1505 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 1506 */         result = RectangleEdge.TOP;
/*      */       }
/* 1508 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 1509 */         result = RectangleEdge.RIGHT;
/*      */       }
/*      */     
/* 1512 */     } else if (location == AxisLocation.TOP_OR_LEFT) {
/* 1513 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 1514 */         result = RectangleEdge.TOP;
/*      */       }
/* 1516 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 1517 */         result = RectangleEdge.LEFT;
/*      */       }
/*      */     
/* 1520 */     } else if (location == AxisLocation.BOTTOM_OR_RIGHT) {
/* 1521 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 1522 */         result = RectangleEdge.BOTTOM;
/*      */       }
/* 1524 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 1525 */         result = RectangleEdge.RIGHT;
/*      */       }
/*      */     
/* 1528 */     } else if (location == AxisLocation.BOTTOM_OR_LEFT) {
/* 1529 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 1530 */         result = RectangleEdge.BOTTOM;
/*      */       }
/* 1532 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 1533 */         result = RectangleEdge.LEFT;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1538 */     if (result == null) {
/* 1539 */       throw new IllegalStateException("resolveRangeAxisLocation()");
/*      */     }
/* 1541 */     return result;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/Plot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */