/*      */ package org.jfree.chart;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Image;
/*      */ import java.awt.Paint;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import org.jfree.chart.block.BlockFrame;
/*      */ import org.jfree.chart.block.BlockParams;
/*      */ import org.jfree.chart.block.EntityBlockResult;
/*      */ import org.jfree.chart.block.LengthConstraintType;
/*      */ import org.jfree.chart.block.LineBorder;
/*      */ import org.jfree.chart.block.RectangleConstraint;
/*      */ import org.jfree.chart.entity.ChartEntity;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.entity.JFreeChartEntity;
/*      */ import org.jfree.chart.event.ChartChangeEvent;
/*      */ import org.jfree.chart.event.ChartChangeListener;
/*      */ import org.jfree.chart.event.ChartProgressEvent;
/*      */ import org.jfree.chart.event.ChartProgressListener;
/*      */ import org.jfree.chart.event.PlotChangeEvent;
/*      */ import org.jfree.chart.event.PlotChangeListener;
/*      */ import org.jfree.chart.event.TitleChangeEvent;
/*      */ import org.jfree.chart.event.TitleChangeListener;
/*      */ import org.jfree.chart.plot.CategoryPlot;
/*      */ import org.jfree.chart.plot.Plot;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.plot.XYPlot;
/*      */ import org.jfree.chart.title.LegendTitle;
/*      */ import org.jfree.chart.title.TextTitle;
/*      */ import org.jfree.chart.title.Title;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.ui.Align;
/*      */ import org.jfree.ui.Drawable;
/*      */ import org.jfree.ui.HorizontalAlignment;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ import org.jfree.ui.Size2D;
/*      */ import org.jfree.ui.VerticalAlignment;
/*      */ import org.jfree.ui.about.ProjectInfo;
/*      */ import org.jfree.util.ObjectUtilities;
/*      */ import org.jfree.util.PaintUtilities;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JFreeChart
/*      */   implements Drawable, TitleChangeListener, PlotChangeListener, Serializable, Cloneable
/*      */ {
/*      */   private static final long serialVersionUID = -3470703747817429120L;
/*  256 */   public static final ProjectInfo INFO = new JFreeChartInfo();
/*      */ 
/*      */   
/*  259 */   public static final Font DEFAULT_TITLE_FONT = new Font("SansSerif", 1, 18);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  264 */   public static final Paint DEFAULT_BACKGROUND_PAINT = UIManager.getColor("Panel.background");
/*      */ 
/*      */   
/*  267 */   public static final Image DEFAULT_BACKGROUND_IMAGE = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int DEFAULT_BACKGROUND_IMAGE_ALIGNMENT = 15;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final float DEFAULT_BACKGROUND_IMAGE_ALPHA = 0.5F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  282 */   public static final RenderingHints.Key KEY_SUPPRESS_SHADOW_GENERATION = new RenderingHints.Key(0)
/*      */     {
/*      */       public boolean isCompatibleValue(Object val)
/*      */       {
/*  286 */         return val instanceof Boolean;
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient RenderingHints renderingHints;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean borderVisible;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke borderStroke;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint borderPaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private RectangleInsets padding;
/*      */ 
/*      */   
/*      */   private TextTitle title;
/*      */ 
/*      */   
/*      */   private List subtitles;
/*      */ 
/*      */   
/*      */   private Plot plot;
/*      */ 
/*      */   
/*      */   private transient Paint backgroundPaint;
/*      */ 
/*      */   
/*      */   private transient Image backgroundImage;
/*      */ 
/*      */   
/*  327 */   private int backgroundImageAlignment = 15;
/*      */ 
/*      */   
/*  330 */   private float backgroundImageAlpha = 0.5F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient EventListenerList changeListeners;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient EventListenerList progressListeners;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean notify;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JFreeChart(Plot plot) {
/*  356 */     this(null, null, plot, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JFreeChart(String title, Plot plot) {
/*  372 */     this(title, DEFAULT_TITLE_FONT, plot, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JFreeChart(String title, Font titleFont, Plot plot, boolean createLegend) {
/*  395 */     ParamChecks.nullNotPermitted(plot, "plot");
/*      */ 
/*      */     
/*  398 */     this.progressListeners = new EventListenerList();
/*  399 */     this.changeListeners = new EventListenerList();
/*  400 */     this.notify = true;
/*      */ 
/*      */     
/*  403 */     this.renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  408 */     this.renderingHints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
/*      */ 
/*      */     
/*  411 */     this.borderVisible = false;
/*  412 */     this.borderStroke = new BasicStroke(1.0F);
/*  413 */     this.borderPaint = Color.black;
/*      */     
/*  415 */     this.padding = RectangleInsets.ZERO_INSETS;
/*      */     
/*  417 */     this.plot = plot;
/*  418 */     plot.addChangeListener(this);
/*      */     
/*  420 */     this.subtitles = new ArrayList();
/*      */ 
/*      */     
/*  423 */     if (createLegend) {
/*  424 */       LegendTitle legend = new LegendTitle((LegendItemSource)this.plot);
/*  425 */       legend.setMargin(new RectangleInsets(1.0D, 1.0D, 1.0D, 1.0D));
/*  426 */       legend.setFrame((BlockFrame)new LineBorder());
/*  427 */       legend.setBackgroundPaint(Color.white);
/*  428 */       legend.setPosition(RectangleEdge.BOTTOM);
/*  429 */       this.subtitles.add(legend);
/*  430 */       legend.addChangeListener(this);
/*      */     } 
/*      */ 
/*      */     
/*  434 */     if (title != null) {
/*  435 */       if (titleFont == null) {
/*  436 */         titleFont = DEFAULT_TITLE_FONT;
/*      */       }
/*  438 */       this.title = new TextTitle(title, titleFont);
/*  439 */       this.title.addChangeListener(this);
/*      */     } 
/*      */     
/*  442 */     this.backgroundPaint = DEFAULT_BACKGROUND_PAINT;
/*      */     
/*  444 */     this.backgroundImage = DEFAULT_BACKGROUND_IMAGE;
/*  445 */     this.backgroundImageAlignment = 15;
/*  446 */     this.backgroundImageAlpha = 0.5F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RenderingHints getRenderingHints() {
/*  458 */     return this.renderingHints;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderingHints(RenderingHints renderingHints) {
/*  471 */     ParamChecks.nullNotPermitted(renderingHints, "renderingHints");
/*  472 */     this.renderingHints = renderingHints;
/*  473 */     fireChartChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBorderVisible() {
/*  485 */     return this.borderVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorderVisible(boolean visible) {
/*  497 */     this.borderVisible = visible;
/*  498 */     fireChartChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getBorderStroke() {
/*  509 */     return this.borderStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorderStroke(Stroke stroke) {
/*  520 */     this.borderStroke = stroke;
/*  521 */     fireChartChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getBorderPaint() {
/*  532 */     return this.borderPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorderPaint(Paint paint) {
/*  543 */     this.borderPaint = paint;
/*  544 */     fireChartChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleInsets getPadding() {
/*  555 */     return this.padding;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPadding(RectangleInsets padding) {
/*  567 */     ParamChecks.nullNotPermitted(padding, "padding");
/*  568 */     this.padding = padding;
/*  569 */     notifyListeners(new ChartChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextTitle getTitle() {
/*  583 */     return this.title;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTitle(TextTitle title) {
/*  597 */     if (this.title != null) {
/*  598 */       this.title.removeChangeListener(this);
/*      */     }
/*  600 */     this.title = title;
/*  601 */     if (title != null) {
/*  602 */       title.addChangeListener(this);
/*      */     }
/*  604 */     fireChartChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTitle(String text) {
/*  620 */     if (text != null) {
/*  621 */       if (this.title == null) {
/*  622 */         setTitle(new TextTitle(text, DEFAULT_TITLE_FONT));
/*      */       } else {
/*      */         
/*  625 */         this.title.setText(text);
/*      */       } 
/*      */     } else {
/*      */       
/*  629 */       setTitle((TextTitle)null);
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
/*      */   public void addLegend(LegendTitle legend) {
/*  642 */     addSubtitle((Title)legend);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LegendTitle getLegend() {
/*  654 */     return getLegend(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LegendTitle getLegend(int index) {
/*  667 */     int seen = 0;
/*  668 */     Iterator<Title> iterator = this.subtitles.iterator();
/*  669 */     while (iterator.hasNext()) {
/*  670 */       Title subtitle = iterator.next();
/*  671 */       if (subtitle instanceof LegendTitle) {
/*  672 */         if (seen == index) {
/*  673 */           return (LegendTitle)subtitle;
/*      */         }
/*      */         
/*  676 */         seen++;
/*      */       } 
/*      */     } 
/*      */     
/*  680 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeLegend() {
/*  690 */     removeSubtitle((Title)getLegend());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List getSubtitles() {
/*  701 */     return new ArrayList(this.subtitles);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSubtitles(List subtitles) {
/*  715 */     if (subtitles == null) {
/*  716 */       throw new NullPointerException("Null 'subtitles' argument.");
/*      */     }
/*  718 */     setNotify(false);
/*  719 */     clearSubtitles();
/*  720 */     Iterator<Title> iterator = subtitles.iterator();
/*  721 */     while (iterator.hasNext()) {
/*  722 */       Title t = iterator.next();
/*  723 */       if (t != null) {
/*  724 */         addSubtitle(t);
/*      */       }
/*      */     } 
/*  727 */     setNotify(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSubtitleCount() {
/*  738 */     return this.subtitles.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Title getSubtitle(int index) {
/*  751 */     if (index < 0 || index >= getSubtitleCount()) {
/*  752 */       throw new IllegalArgumentException("Index out of range.");
/*      */     }
/*  754 */     return this.subtitles.get(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSubtitle(Title subtitle) {
/*  766 */     ParamChecks.nullNotPermitted(subtitle, "subtitle");
/*  767 */     this.subtitles.add(subtitle);
/*  768 */     subtitle.addChangeListener(this);
/*  769 */     fireChartChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSubtitle(int index, Title subtitle) {
/*  782 */     if (index < 0 || index > getSubtitleCount()) {
/*  783 */       throw new IllegalArgumentException("The 'index' argument is out of range.");
/*      */     }
/*      */     
/*  786 */     ParamChecks.nullNotPermitted(subtitle, "subtitle");
/*  787 */     this.subtitles.add(index, subtitle);
/*  788 */     subtitle.addChangeListener(this);
/*  789 */     fireChartChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearSubtitles() {
/*  799 */     Iterator<Title> iterator = this.subtitles.iterator();
/*  800 */     while (iterator.hasNext()) {
/*  801 */       Title t = iterator.next();
/*  802 */       t.removeChangeListener(this);
/*      */     } 
/*  804 */     this.subtitles.clear();
/*  805 */     fireChartChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeSubtitle(Title title) {
/*  817 */     this.subtitles.remove(title);
/*  818 */     fireChartChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Plot getPlot() {
/*  829 */     return this.plot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryPlot getCategoryPlot() {
/*  843 */     return (CategoryPlot)this.plot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYPlot getXYPlot() {
/*  857 */     return (XYPlot)this.plot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAntiAlias() {
/*  869 */     Object val = this.renderingHints.get(RenderingHints.KEY_ANTIALIASING);
/*  870 */     return RenderingHints.VALUE_ANTIALIAS_ON.equals(val);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAntiAlias(boolean flag) {
/*  884 */     Object hint = flag ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF;
/*      */     
/*  886 */     this.renderingHints.put(RenderingHints.KEY_ANTIALIASING, hint);
/*  887 */     fireChartChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getTextAntiAlias() {
/*  901 */     return this.renderingHints.get(RenderingHints.KEY_TEXT_ANTIALIASING);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTextAntiAlias(boolean flag) {
/*  919 */     if (flag) {
/*  920 */       setTextAntiAlias(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*      */     } else {
/*      */       
/*  923 */       setTextAntiAlias(RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
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
/*      */   public void setTextAntiAlias(Object val) {
/*  940 */     this.renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, val);
/*  941 */     notifyListeners(new ChartChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getBackgroundPaint() {
/*  952 */     return this.backgroundPaint;
/*      */   }
/*      */ 
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
/*  965 */     if (this.backgroundPaint != null) {
/*  966 */       if (!this.backgroundPaint.equals(paint)) {
/*  967 */         this.backgroundPaint = paint;
/*  968 */         fireChartChanged();
/*      */       }
/*      */     
/*      */     }
/*  972 */     else if (paint != null) {
/*  973 */       this.backgroundPaint = paint;
/*  974 */       fireChartChanged();
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
/*      */   public Image getBackgroundImage() {
/*  989 */     return this.backgroundImage;
/*      */   }
/*      */ 
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
/* 1002 */     if (this.backgroundImage != null) {
/* 1003 */       if (!this.backgroundImage.equals(image)) {
/* 1004 */         this.backgroundImage = image;
/* 1005 */         fireChartChanged();
/*      */       }
/*      */     
/*      */     }
/* 1009 */     else if (image != null) {
/* 1010 */       this.backgroundImage = image;
/* 1011 */       fireChartChanged();
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
/*      */   public int getBackgroundImageAlignment() {
/* 1027 */     return this.backgroundImageAlignment;
/*      */   }
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
/* 1039 */     if (this.backgroundImageAlignment != alignment) {
/* 1040 */       this.backgroundImageAlignment = alignment;
/* 1041 */       fireChartChanged();
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
/*      */   public float getBackgroundImageAlpha() {
/* 1053 */     return this.backgroundImageAlpha;
/*      */   }
/*      */ 
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
/* 1066 */     if (this.backgroundImageAlpha != alpha) {
/* 1067 */       this.backgroundImageAlpha = alpha;
/* 1068 */       fireChartChanged();
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
/*      */   public boolean isNotify() {
/* 1082 */     return this.notify;
/*      */   }
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
/* 1094 */     this.notify = notify;
/*      */     
/* 1096 */     if (notify) {
/* 1097 */       notifyListeners(new ChartChangeEvent(this));
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
/*      */   public void draw(Graphics2D g2, Rectangle2D area) {
/* 1112 */     draw(g2, area, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(Graphics2D g2, Rectangle2D area, ChartRenderingInfo info) {
/* 1124 */     draw(g2, area, null, info);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(Graphics2D g2, Rectangle2D chartArea, Point2D anchor, ChartRenderingInfo info) {
/* 1142 */     notifyListeners(new ChartProgressEvent(this, this, 1, 0));
/*      */ 
/*      */     
/* 1145 */     EntityCollection entities = null;
/*      */     
/* 1147 */     if (info != null) {
/* 1148 */       info.clear();
/* 1149 */       info.setChartArea(chartArea);
/* 1150 */       entities = info.getEntityCollection();
/*      */     } 
/* 1152 */     if (entities != null) {
/* 1153 */       entities.add((ChartEntity)new JFreeChartEntity((Rectangle2D)chartArea.clone(), this));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1158 */     Shape savedClip = g2.getClip();
/* 1159 */     g2.clip(chartArea);
/*      */     
/* 1161 */     g2.addRenderingHints(this.renderingHints);
/*      */ 
/*      */     
/* 1164 */     if (this.backgroundPaint != null) {
/* 1165 */       g2.setPaint(this.backgroundPaint);
/* 1166 */       g2.fill(chartArea);
/*      */     } 
/*      */     
/* 1169 */     if (this.backgroundImage != null) {
/* 1170 */       Composite originalComposite = g2.getComposite();
/* 1171 */       g2.setComposite(AlphaComposite.getInstance(3, this.backgroundImageAlpha));
/*      */ 
/*      */ 
/*      */       
/* 1175 */       Rectangle2D dest = new Rectangle2D.Double(0.0D, 0.0D, this.backgroundImage.getWidth(null), this.backgroundImage.getHeight(null));
/* 1176 */       Align.align(dest, chartArea, this.backgroundImageAlignment);
/* 1177 */       g2.drawImage(this.backgroundImage, (int)dest.getX(), 
/* 1178 */           (int)dest.getY(), (int)dest.getWidth(), 
/* 1179 */           (int)dest.getHeight(), null);
/* 1180 */       g2.setComposite(originalComposite);
/*      */     } 
/*      */     
/* 1183 */     if (isBorderVisible()) {
/* 1184 */       Paint paint = getBorderPaint();
/* 1185 */       Stroke stroke = getBorderStroke();
/* 1186 */       if (paint != null && stroke != null) {
/*      */ 
/*      */         
/* 1189 */         Rectangle2D borderArea = new Rectangle2D.Double(chartArea.getX(), chartArea.getY(), chartArea.getWidth() - 1.0D, chartArea.getHeight() - 1.0D);
/*      */         
/* 1191 */         g2.setPaint(paint);
/* 1192 */         g2.setStroke(stroke);
/* 1193 */         g2.draw(borderArea);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1198 */     Rectangle2D nonTitleArea = new Rectangle2D.Double();
/* 1199 */     nonTitleArea.setRect(chartArea);
/* 1200 */     this.padding.trim(nonTitleArea);
/*      */     
/* 1202 */     if (this.title != null && this.title.isVisible()) {
/* 1203 */       EntityCollection e = drawTitle((Title)this.title, g2, nonTitleArea, (entities != null));
/*      */       
/* 1205 */       if (e != null && entities != null) {
/* 1206 */         entities.addAll(e);
/*      */       }
/*      */     } 
/*      */     
/* 1210 */     Iterator<Title> iterator = this.subtitles.iterator();
/* 1211 */     while (iterator.hasNext()) {
/* 1212 */       Title currentTitle = iterator.next();
/* 1213 */       if (currentTitle.isVisible()) {
/* 1214 */         EntityCollection e = drawTitle(currentTitle, g2, nonTitleArea, (entities != null));
/*      */         
/* 1216 */         if (e != null && entities != null) {
/* 1217 */           entities.addAll(e);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1222 */     Rectangle2D plotArea = nonTitleArea;
/*      */ 
/*      */     
/* 1225 */     PlotRenderingInfo plotInfo = null;
/* 1226 */     if (info != null) {
/* 1227 */       plotInfo = info.getPlotInfo();
/*      */     }
/* 1229 */     this.plot.draw(g2, plotArea, anchor, null, plotInfo);
/*      */     
/* 1231 */     g2.setClip(savedClip);
/*      */     
/* 1233 */     notifyListeners(new ChartProgressEvent(this, this, 2, 100));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Rectangle2D createAlignedRectangle2D(Size2D dimensions, Rectangle2D frame, HorizontalAlignment hAlign, VerticalAlignment vAlign) {
/* 1250 */     double x = Double.NaN;
/* 1251 */     double y = Double.NaN;
/* 1252 */     if (hAlign == HorizontalAlignment.LEFT) {
/* 1253 */       x = frame.getX();
/*      */     }
/* 1255 */     else if (hAlign == HorizontalAlignment.CENTER) {
/* 1256 */       x = frame.getCenterX() - dimensions.width / 2.0D;
/*      */     }
/* 1258 */     else if (hAlign == HorizontalAlignment.RIGHT) {
/* 1259 */       x = frame.getMaxX() - dimensions.width;
/*      */     } 
/* 1261 */     if (vAlign == VerticalAlignment.TOP) {
/* 1262 */       y = frame.getY();
/*      */     }
/* 1264 */     else if (vAlign == VerticalAlignment.CENTER) {
/* 1265 */       y = frame.getCenterY() - dimensions.height / 2.0D;
/*      */     }
/* 1267 */     else if (vAlign == VerticalAlignment.BOTTOM) {
/* 1268 */       y = frame.getMaxY() - dimensions.height;
/*      */     } 
/*      */     
/* 1271 */     return new Rectangle2D.Double(x, y, dimensions.width, dimensions.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected EntityCollection drawTitle(Title t, Graphics2D g2, Rectangle2D area, boolean entities) {
/* 1292 */     ParamChecks.nullNotPermitted(t, "t");
/* 1293 */     ParamChecks.nullNotPermitted(area, "area");
/*      */     
/* 1295 */     RectangleEdge position = t.getPosition();
/* 1296 */     double ww = area.getWidth();
/* 1297 */     if (ww <= 0.0D) {
/* 1298 */       return null;
/*      */     }
/* 1300 */     double hh = area.getHeight();
/* 1301 */     if (hh <= 0.0D) {
/* 1302 */       return null;
/*      */     }
/* 1304 */     RectangleConstraint constraint = new RectangleConstraint(ww, new Range(0.0D, ww), LengthConstraintType.RANGE, hh, new Range(0.0D, hh), LengthConstraintType.RANGE);
/*      */ 
/*      */     
/* 1307 */     Object retValue = null;
/* 1308 */     BlockParams p = new BlockParams();
/* 1309 */     p.setGenerateEntities(entities);
/* 1310 */     if (position == RectangleEdge.TOP) {
/* 1311 */       Size2D size = t.arrange(g2, constraint);
/* 1312 */       Rectangle2D titleArea = createAlignedRectangle2D(size, area, t
/* 1313 */           .getHorizontalAlignment(), VerticalAlignment.TOP);
/* 1314 */       retValue = t.draw(g2, titleArea, p);
/* 1315 */       area.setRect(area.getX(), Math.min(area.getY() + size.height, area
/* 1316 */             .getMaxY()), area.getWidth(), Math.max(area.getHeight() - size.height, 0.0D));
/*      */     
/*      */     }
/* 1319 */     else if (position == RectangleEdge.BOTTOM) {
/* 1320 */       Size2D size = t.arrange(g2, constraint);
/* 1321 */       Rectangle2D titleArea = createAlignedRectangle2D(size, area, t
/* 1322 */           .getHorizontalAlignment(), VerticalAlignment.BOTTOM);
/* 1323 */       retValue = t.draw(g2, titleArea, p);
/* 1324 */       area.setRect(area.getX(), area.getY(), area.getWidth(), area
/* 1325 */           .getHeight() - size.height);
/*      */     }
/* 1327 */     else if (position == RectangleEdge.RIGHT) {
/* 1328 */       Size2D size = t.arrange(g2, constraint);
/* 1329 */       Rectangle2D titleArea = createAlignedRectangle2D(size, area, HorizontalAlignment.RIGHT, t
/* 1330 */           .getVerticalAlignment());
/* 1331 */       retValue = t.draw(g2, titleArea, p);
/* 1332 */       area.setRect(area.getX(), area.getY(), area.getWidth() - size.width, area
/* 1333 */           .getHeight());
/*      */     
/*      */     }
/* 1336 */     else if (position == RectangleEdge.LEFT) {
/* 1337 */       Size2D size = t.arrange(g2, constraint);
/* 1338 */       Rectangle2D titleArea = createAlignedRectangle2D(size, area, HorizontalAlignment.LEFT, t
/* 1339 */           .getVerticalAlignment());
/* 1340 */       retValue = t.draw(g2, titleArea, p);
/* 1341 */       area.setRect(area.getX() + size.width, area.getY(), area.getWidth() - size.width, area
/* 1342 */           .getHeight());
/*      */     } else {
/*      */       
/* 1345 */       throw new RuntimeException("Unrecognised title position.");
/*      */     } 
/* 1347 */     EntityCollection result = null;
/* 1348 */     if (retValue instanceof EntityBlockResult) {
/* 1349 */       EntityBlockResult ebr = (EntityBlockResult)retValue;
/* 1350 */       result = ebr.getEntityCollection();
/*      */     } 
/* 1352 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BufferedImage createBufferedImage(int width, int height) {
/* 1364 */     return createBufferedImage(width, height, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BufferedImage createBufferedImage(int width, int height, ChartRenderingInfo info) {
/* 1379 */     return createBufferedImage(width, height, 2, info);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BufferedImage createBufferedImage(int width, int height, int imageType, ChartRenderingInfo info) {
/* 1397 */     BufferedImage image = new BufferedImage(width, height, imageType);
/* 1398 */     Graphics2D g2 = image.createGraphics();
/* 1399 */     draw(g2, new Rectangle2D.Double(0.0D, 0.0D, width, height), null, info);
/* 1400 */     g2.dispose();
/* 1401 */     return image;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BufferedImage createBufferedImage(int imageWidth, int imageHeight, double drawWidth, double drawHeight, ChartRenderingInfo info) {
/* 1424 */     BufferedImage image = new BufferedImage(imageWidth, imageHeight, 2);
/*      */     
/* 1426 */     Graphics2D g2 = image.createGraphics();
/* 1427 */     double scaleX = imageWidth / drawWidth;
/* 1428 */     double scaleY = imageHeight / drawHeight;
/* 1429 */     AffineTransform st = AffineTransform.getScaleInstance(scaleX, scaleY);
/* 1430 */     g2.transform(st);
/* 1431 */     draw(g2, new Rectangle2D.Double(0.0D, 0.0D, drawWidth, drawHeight), null, info);
/*      */     
/* 1433 */     g2.dispose();
/* 1434 */     return image;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleClick(int x, int y, ChartRenderingInfo info) {
/* 1454 */     this.plot.handleClick(x, y, info.getPlotInfo());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChangeListener(ChartChangeListener listener) {
/* 1466 */     ParamChecks.nullNotPermitted(listener, "listener");
/* 1467 */     this.changeListeners.add(ChartChangeListener.class, listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeChangeListener(ChartChangeListener listener) {
/* 1478 */     ParamChecks.nullNotPermitted(listener, "listener");
/* 1479 */     this.changeListeners.remove(ChartChangeListener.class, listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fireChartChanged() {
/* 1488 */     ChartChangeEvent event = new ChartChangeEvent(this);
/* 1489 */     notifyListeners(event);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void notifyListeners(ChartChangeEvent event) {
/* 1499 */     if (this.notify) {
/* 1500 */       Object[] listeners = this.changeListeners.getListenerList();
/* 1501 */       for (int i = listeners.length - 2; i >= 0; i -= 2) {
/* 1502 */         if (listeners[i] == ChartChangeListener.class) {
/* 1503 */           ((ChartChangeListener)listeners[i + 1]).chartChanged(event);
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
/*      */   public void addProgressListener(ChartProgressListener listener) {
/* 1519 */     this.progressListeners.add(ChartProgressListener.class, listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeProgressListener(ChartProgressListener listener) {
/* 1530 */     this.progressListeners.remove(ChartProgressListener.class, listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void notifyListeners(ChartProgressEvent event) {
/* 1541 */     Object[] listeners = this.progressListeners.getListenerList();
/* 1542 */     for (int i = listeners.length - 2; i >= 0; i -= 2) {
/* 1543 */       if (listeners[i] == ChartProgressListener.class) {
/* 1544 */         ((ChartProgressListener)listeners[i + 1]).chartProgress(event);
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
/*      */   public void titleChanged(TitleChangeEvent event) {
/* 1558 */     event.setChart(this);
/* 1559 */     notifyListeners((ChartChangeEvent)event);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void plotChanged(PlotChangeEvent event) {
/* 1570 */     event.setChart(this);
/* 1571 */     notifyListeners((ChartChangeEvent)event);
/*      */   }
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
/* 1583 */     if (obj == this) {
/* 1584 */       return true;
/*      */     }
/* 1586 */     if (!(obj instanceof JFreeChart)) {
/* 1587 */       return false;
/*      */     }
/* 1589 */     JFreeChart that = (JFreeChart)obj;
/* 1590 */     if (!this.renderingHints.equals(that.renderingHints)) {
/* 1591 */       return false;
/*      */     }
/* 1593 */     if (this.borderVisible != that.borderVisible) {
/* 1594 */       return false;
/*      */     }
/* 1596 */     if (!ObjectUtilities.equal(this.borderStroke, that.borderStroke)) {
/* 1597 */       return false;
/*      */     }
/* 1599 */     if (!PaintUtilities.equal(this.borderPaint, that.borderPaint)) {
/* 1600 */       return false;
/*      */     }
/* 1602 */     if (!this.padding.equals(that.padding)) {
/* 1603 */       return false;
/*      */     }
/* 1605 */     if (!ObjectUtilities.equal(this.title, that.title)) {
/* 1606 */       return false;
/*      */     }
/* 1608 */     if (!ObjectUtilities.equal(this.subtitles, that.subtitles)) {
/* 1609 */       return false;
/*      */     }
/* 1611 */     if (!ObjectUtilities.equal(this.plot, that.plot)) {
/* 1612 */       return false;
/*      */     }
/* 1614 */     if (!PaintUtilities.equal(this.backgroundPaint, that.backgroundPaint))
/*      */     {
/*      */       
/* 1617 */       return false;
/*      */     }
/* 1619 */     if (!ObjectUtilities.equal(this.backgroundImage, that.backgroundImage))
/*      */     {
/* 1621 */       return false;
/*      */     }
/* 1623 */     if (this.backgroundImageAlignment != that.backgroundImageAlignment) {
/* 1624 */       return false;
/*      */     }
/* 1626 */     if (this.backgroundImageAlpha != that.backgroundImageAlpha) {
/* 1627 */       return false;
/*      */     }
/* 1629 */     if (this.notify != that.notify) {
/* 1630 */       return false;
/*      */     }
/* 1632 */     return true;
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
/* 1643 */     stream.defaultWriteObject();
/* 1644 */     SerialUtilities.writeStroke(this.borderStroke, stream);
/* 1645 */     SerialUtilities.writePaint(this.borderPaint, stream);
/* 1646 */     SerialUtilities.writePaint(this.backgroundPaint, stream);
/*      */   }
/*      */ 
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
/* 1659 */     stream.defaultReadObject();
/* 1660 */     this.borderStroke = SerialUtilities.readStroke(stream);
/* 1661 */     this.borderPaint = SerialUtilities.readPaint(stream);
/* 1662 */     this.backgroundPaint = SerialUtilities.readPaint(stream);
/* 1663 */     this.progressListeners = new EventListenerList();
/* 1664 */     this.changeListeners = new EventListenerList();
/* 1665 */     this.renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*      */ 
/*      */     
/* 1668 */     this.renderingHints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
/*      */ 
/*      */ 
/*      */     
/* 1672 */     if (this.title != null) {
/* 1673 */       this.title.addChangeListener(this);
/*      */     }
/*      */     
/* 1676 */     for (int i = 0; i < getSubtitleCount(); i++) {
/* 1677 */       getSubtitle(i).addChangeListener(this);
/*      */     }
/* 1679 */     this.plot.addChangeListener(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void main(String[] args) {
/* 1688 */     System.out.println(INFO.toString());
/*      */   }
/*      */ 
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
/* 1701 */     JFreeChart chart = (JFreeChart)super.clone();
/*      */     
/* 1703 */     chart.renderingHints = (RenderingHints)this.renderingHints.clone();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1708 */     if (this.title != null) {
/* 1709 */       chart.title = (TextTitle)this.title.clone();
/* 1710 */       chart.title.addChangeListener(chart);
/*      */     } 
/*      */     
/* 1713 */     chart.subtitles = new ArrayList();
/* 1714 */     for (int i = 0; i < getSubtitleCount(); i++) {
/* 1715 */       Title subtitle = (Title)getSubtitle(i).clone();
/* 1716 */       chart.subtitles.add(subtitle);
/* 1717 */       subtitle.addChangeListener(chart);
/*      */     } 
/*      */     
/* 1720 */     if (this.plot != null) {
/* 1721 */       chart.plot = (Plot)this.plot.clone();
/* 1722 */       chart.plot.addChangeListener(chart);
/*      */     } 
/*      */     
/* 1725 */     chart.progressListeners = new EventListenerList();
/* 1726 */     chart.changeListeners = new EventListenerList();
/* 1727 */     return chart;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/JFreeChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */