/*      */ package org.jfree.chart;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.Color;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.datatransfer.Clipboard;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.awt.event.MouseMotionListener;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.print.PageFormat;
/*      */ import java.awt.print.Printable;
/*      */ import java.awt.print.PrinterException;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
/*      */ import java.util.EventListener;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ResourceBundle;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.ToolTipManager;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import javax.swing.filechooser.FileNameExtensionFilter;
/*      */ import org.jfree.chart.editor.ChartEditor;
/*      */ import org.jfree.chart.editor.ChartEditorManager;
/*      */ import org.jfree.chart.entity.ChartEntity;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.event.ChartChangeEvent;
/*      */ import org.jfree.chart.event.ChartChangeListener;
/*      */ import org.jfree.chart.event.ChartProgressEvent;
/*      */ import org.jfree.chart.event.ChartProgressListener;
/*      */ import org.jfree.chart.event.OverlayChangeEvent;
/*      */ import org.jfree.chart.event.OverlayChangeListener;
/*      */ import org.jfree.chart.panel.Overlay;
/*      */ import org.jfree.chart.plot.Pannable;
/*      */ import org.jfree.chart.plot.Plot;
/*      */ import org.jfree.chart.plot.PlotOrientation;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.plot.Zoomable;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.chart.util.ResourceBundleWrapper;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ChartPanel
/*      */   extends JPanel
/*      */   implements ChartChangeListener, ChartProgressListener, ActionListener, MouseListener, MouseMotionListener, OverlayChangeListener, Printable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 6046366297214274674L;
/*      */   public static final boolean DEFAULT_BUFFER_USED = true;
/*      */   public static final int DEFAULT_WIDTH = 680;
/*      */   public static final int DEFAULT_HEIGHT = 420;
/*      */   public static final int DEFAULT_MINIMUM_DRAW_WIDTH = 300;
/*      */   public static final int DEFAULT_MINIMUM_DRAW_HEIGHT = 200;
/*      */   public static final int DEFAULT_MAXIMUM_DRAW_WIDTH = 1024;
/*      */   public static final int DEFAULT_MAXIMUM_DRAW_HEIGHT = 768;
/*      */   public static final int DEFAULT_ZOOM_TRIGGER_DISTANCE = 10;
/*      */   public static final String PROPERTIES_COMMAND = "PROPERTIES";
/*      */   public static final String COPY_COMMAND = "COPY";
/*      */   public static final String SAVE_COMMAND = "SAVE";
/*      */   private static final String SAVE_AS_PNG_COMMAND = "SAVE_AS_PNG";
/*      */   private static final String SAVE_AS_SVG_COMMAND = "SAVE_AS_SVG";
/*      */   private static final String SAVE_AS_PDF_COMMAND = "SAVE_AS_PDF";
/*      */   public static final String PRINT_COMMAND = "PRINT";
/*      */   public static final String ZOOM_IN_BOTH_COMMAND = "ZOOM_IN_BOTH";
/*      */   public static final String ZOOM_IN_DOMAIN_COMMAND = "ZOOM_IN_DOMAIN";
/*      */   public static final String ZOOM_IN_RANGE_COMMAND = "ZOOM_IN_RANGE";
/*      */   public static final String ZOOM_OUT_BOTH_COMMAND = "ZOOM_OUT_BOTH";
/*      */   public static final String ZOOM_OUT_DOMAIN_COMMAND = "ZOOM_DOMAIN_BOTH";
/*      */   public static final String ZOOM_OUT_RANGE_COMMAND = "ZOOM_RANGE_BOTH";
/*      */   public static final String ZOOM_RESET_BOTH_COMMAND = "ZOOM_RESET_BOTH";
/*      */   public static final String ZOOM_RESET_DOMAIN_COMMAND = "ZOOM_RESET_DOMAIN";
/*      */   public static final String ZOOM_RESET_RANGE_COMMAND = "ZOOM_RESET_RANGE";
/*      */   private JFreeChart chart;
/*      */   private transient EventListenerList chartMouseListeners;
/*      */   private boolean useBuffer;
/*      */   private boolean refreshBuffer;
/*      */   private transient Image chartBuffer;
/*      */   private int chartBufferHeight;
/*      */   private int chartBufferWidth;
/*      */   private int minimumDrawWidth;
/*      */   private int minimumDrawHeight;
/*      */   private int maximumDrawWidth;
/*      */   private int maximumDrawHeight;
/*      */   private JPopupMenu popup;
/*      */   private ChartRenderingInfo info;
/*      */   private Point2D anchor;
/*      */   private double scaleX;
/*      */   private double scaleY;
/*  410 */   private PlotOrientation orientation = PlotOrientation.VERTICAL;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean domainZoomable = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean rangeZoomable = false;
/*      */ 
/*      */ 
/*      */   
/*  423 */   private Point2D zoomPoint = null;
/*      */ 
/*      */   
/*  426 */   private transient Rectangle2D zoomRectangle = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fillZoomRectangle = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private int zoomTriggerDistance;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean horizontalAxisTrace = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean verticalAxisTrace = false;
/*      */ 
/*      */   
/*      */   private transient Line2D verticalTraceLine;
/*      */ 
/*      */   
/*      */   private transient Line2D horizontalTraceLine;
/*      */ 
/*      */   
/*      */   private JMenuItem zoomInBothMenuItem;
/*      */ 
/*      */   
/*      */   private JMenuItem zoomInDomainMenuItem;
/*      */ 
/*      */   
/*      */   private JMenuItem zoomInRangeMenuItem;
/*      */ 
/*      */   
/*      */   private JMenuItem zoomOutBothMenuItem;
/*      */ 
/*      */   
/*      */   private JMenuItem zoomOutDomainMenuItem;
/*      */ 
/*      */   
/*      */   private JMenuItem zoomOutRangeMenuItem;
/*      */ 
/*      */   
/*      */   private JMenuItem zoomResetBothMenuItem;
/*      */ 
/*      */   
/*      */   private JMenuItem zoomResetDomainMenuItem;
/*      */ 
/*      */   
/*      */   private JMenuItem zoomResetRangeMenuItem;
/*      */ 
/*      */   
/*      */   private File defaultDirectoryForSaveAs;
/*      */ 
/*      */   
/*      */   private boolean enforceFileExtensions;
/*      */ 
/*      */   
/*      */   private boolean ownToolTipDelaysActive;
/*      */ 
/*      */   
/*      */   private int originalToolTipInitialDelay;
/*      */ 
/*      */   
/*      */   private int originalToolTipReshowDelay;
/*      */ 
/*      */   
/*      */   private int originalToolTipDismissDelay;
/*      */ 
/*      */   
/*      */   private int ownToolTipInitialDelay;
/*      */ 
/*      */   
/*      */   private int ownToolTipReshowDelay;
/*      */ 
/*      */   
/*      */   private int ownToolTipDismissDelay;
/*      */ 
/*      */   
/*  505 */   private double zoomInFactor = 0.5D;
/*      */ 
/*      */   
/*  508 */   private double zoomOutFactor = 2.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean zoomAroundAnchor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint zoomOutlinePaint;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint zoomFillPaint;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  534 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.LocalizationBundle");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double panW;
/*      */ 
/*      */ 
/*      */   
/*      */   private double panH;
/*      */ 
/*      */ 
/*      */   
/*      */   private Point panLast;
/*      */ 
/*      */ 
/*      */   
/*  551 */   private int panMask = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List overlays;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MouseWheelHandler mouseWheelHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChartPanel(JFreeChart chart) {
/*  567 */     this(chart, 680, 420, 300, 200, 1024, 768, true, true, true, true, true, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChartPanel(JFreeChart chart, boolean useBuffer) {
/*  603 */     this(chart, 680, 420, 300, 200, 1024, 768, useBuffer, true, true, true, true, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChartPanel(JFreeChart chart, boolean properties, boolean save, boolean print, boolean zoom, boolean tooltips) {
/*  637 */     this(chart, 680, 420, 300, 200, 1024, 768, true, properties, save, print, zoom, tooltips);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChartPanel(JFreeChart chart, int width, int height, int minimumDrawWidth, int minimumDrawHeight, int maximumDrawWidth, int maximumDrawHeight, boolean useBuffer, boolean properties, boolean save, boolean print, boolean zoom, boolean tooltips) {
/*  683 */     this(chart, width, height, minimumDrawWidth, minimumDrawHeight, maximumDrawWidth, maximumDrawHeight, useBuffer, properties, true, save, print, zoom, tooltips);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChartPanel(JFreeChart chart, int width, int height, int minimumDrawWidth, int minimumDrawHeight, int maximumDrawWidth, int maximumDrawHeight, boolean useBuffer, boolean properties, boolean copy, boolean save, boolean print, boolean zoom, boolean tooltips) {
/*  722 */     setChart(chart);
/*  723 */     this.chartMouseListeners = new EventListenerList();
/*  724 */     this.info = new ChartRenderingInfo();
/*  725 */     setPreferredSize(new Dimension(width, height));
/*  726 */     this.useBuffer = useBuffer;
/*  727 */     this.refreshBuffer = false;
/*  728 */     this.minimumDrawWidth = minimumDrawWidth;
/*  729 */     this.minimumDrawHeight = minimumDrawHeight;
/*  730 */     this.maximumDrawWidth = maximumDrawWidth;
/*  731 */     this.maximumDrawHeight = maximumDrawHeight;
/*  732 */     this.zoomTriggerDistance = 10;
/*      */ 
/*      */     
/*  735 */     this.popup = null;
/*  736 */     if (properties || copy || save || print || zoom) {
/*  737 */       this.popup = createPopupMenu(properties, copy, save, print, zoom);
/*      */     }
/*      */     
/*  740 */     enableEvents(16L);
/*  741 */     enableEvents(32L);
/*  742 */     setDisplayToolTips(tooltips);
/*  743 */     addMouseListener(this);
/*  744 */     addMouseMotionListener(this);
/*      */     
/*  746 */     this.defaultDirectoryForSaveAs = null;
/*  747 */     this.enforceFileExtensions = true;
/*      */ 
/*      */ 
/*      */     
/*  751 */     ToolTipManager ttm = ToolTipManager.sharedInstance();
/*  752 */     this.ownToolTipInitialDelay = ttm.getInitialDelay();
/*  753 */     this.ownToolTipDismissDelay = ttm.getDismissDelay();
/*  754 */     this.ownToolTipReshowDelay = ttm.getReshowDelay();
/*      */     
/*  756 */     this.zoomAroundAnchor = false;
/*  757 */     this.zoomOutlinePaint = Color.blue;
/*  758 */     this.zoomFillPaint = new Color(0, 0, 255, 63);
/*      */     
/*  760 */     this.panMask = 2;
/*      */ 
/*      */     
/*  763 */     String osName = System.getProperty("os.name").toLowerCase();
/*  764 */     if (osName.startsWith("mac os x")) {
/*  765 */       this.panMask = 8;
/*      */     }
/*      */     
/*  768 */     this.overlays = new ArrayList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JFreeChart getChart() {
/*  777 */     return this.chart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setChart(JFreeChart chart) {
/*  788 */     if (this.chart != null) {
/*  789 */       this.chart.removeChangeListener(this);
/*  790 */       this.chart.removeProgressListener(this);
/*      */     } 
/*      */ 
/*      */     
/*  794 */     this.chart = chart;
/*  795 */     if (chart != null) {
/*  796 */       this.chart.addChangeListener(this);
/*  797 */       this.chart.addProgressListener(this);
/*  798 */       Plot plot = chart.getPlot();
/*  799 */       this.domainZoomable = false;
/*  800 */       this.rangeZoomable = false;
/*  801 */       if (plot instanceof Zoomable) {
/*  802 */         Zoomable z = (Zoomable)plot;
/*  803 */         this.domainZoomable = z.isDomainZoomable();
/*  804 */         this.rangeZoomable = z.isRangeZoomable();
/*  805 */         this.orientation = z.getOrientation();
/*      */       } 
/*      */     } else {
/*      */       
/*  809 */       this.domainZoomable = false;
/*  810 */       this.rangeZoomable = false;
/*      */     } 
/*  812 */     if (this.useBuffer) {
/*  813 */       this.refreshBuffer = true;
/*      */     }
/*  815 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinimumDrawWidth() {
/*  828 */     return this.minimumDrawWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinimumDrawWidth(int width) {
/*  841 */     this.minimumDrawWidth = width;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumDrawWidth() {
/*  853 */     return this.maximumDrawWidth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaximumDrawWidth(int width) {
/*  866 */     this.maximumDrawWidth = width;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinimumDrawHeight() {
/*  878 */     return this.minimumDrawHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinimumDrawHeight(int height) {
/*  891 */     this.minimumDrawHeight = height;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumDrawHeight() {
/*  903 */     return this.maximumDrawHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaximumDrawHeight(int height) {
/*  916 */     this.maximumDrawHeight = height;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getScaleX() {
/*  926 */     return this.scaleX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getScaleY() {
/*  936 */     return this.scaleY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point2D getAnchor() {
/*  945 */     return this.anchor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setAnchor(Point2D anchor) {
/*  955 */     this.anchor = anchor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JPopupMenu getPopupMenu() {
/*  964 */     return this.popup;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPopupMenu(JPopupMenu popup) {
/*  973 */     this.popup = popup;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChartRenderingInfo getChartRenderingInfo() {
/*  982 */     return this.info;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMouseZoomable(boolean flag) {
/*  992 */     setMouseZoomable(flag, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMouseZoomable(boolean flag, boolean fillRectangle) {
/* 1003 */     setDomainZoomable(flag);
/* 1004 */     setRangeZoomable(flag);
/* 1005 */     setFillZoomRectangle(fillRectangle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDomainZoomable() {
/* 1015 */     return this.domainZoomable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainZoomable(boolean flag) {
/* 1026 */     if (flag) {
/* 1027 */       Plot plot = this.chart.getPlot();
/* 1028 */       if (plot instanceof Zoomable) {
/* 1029 */         Zoomable z = (Zoomable)plot;
/* 1030 */         this.domainZoomable = (flag && z.isDomainZoomable());
/*      */       } 
/*      */     } else {
/*      */       
/* 1034 */       this.domainZoomable = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangeZoomable() {
/* 1045 */     return this.rangeZoomable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeZoomable(boolean flag) {
/* 1054 */     if (flag) {
/* 1055 */       Plot plot = this.chart.getPlot();
/* 1056 */       if (plot instanceof Zoomable) {
/* 1057 */         Zoomable z = (Zoomable)plot;
/* 1058 */         this.rangeZoomable = (flag && z.isRangeZoomable());
/*      */       } 
/*      */     } else {
/*      */       
/* 1062 */       this.rangeZoomable = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFillZoomRectangle() {
/* 1073 */     return this.fillZoomRectangle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFillZoomRectangle(boolean flag) {
/* 1083 */     this.fillZoomRectangle = flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getZoomTriggerDistance() {
/* 1093 */     return this.zoomTriggerDistance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setZoomTriggerDistance(int distance) {
/* 1103 */     this.zoomTriggerDistance = distance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getHorizontalAxisTrace() {
/* 1113 */     return this.horizontalAxisTrace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHorizontalAxisTrace(boolean flag) {
/* 1123 */     this.horizontalAxisTrace = flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Line2D getHorizontalTraceLine() {
/* 1132 */     return this.horizontalTraceLine;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setHorizontalTraceLine(Line2D line) {
/* 1141 */     this.horizontalTraceLine = line;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getVerticalAxisTrace() {
/* 1151 */     return this.verticalAxisTrace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVerticalAxisTrace(boolean flag) {
/* 1161 */     this.verticalAxisTrace = flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Line2D getVerticalTraceLine() {
/* 1170 */     return this.verticalTraceLine;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setVerticalTraceLine(Line2D line) {
/* 1179 */     this.verticalTraceLine = line;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getDefaultDirectoryForSaveAs() {
/* 1190 */     return this.defaultDirectoryForSaveAs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultDirectoryForSaveAs(File directory) {
/* 1202 */     if (directory != null && 
/* 1203 */       !directory.isDirectory()) {
/* 1204 */       throw new IllegalArgumentException("The 'directory' argument is not a directory.");
/*      */     }
/*      */ 
/*      */     
/* 1208 */     this.defaultDirectoryForSaveAs = directory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnforceFileExtensions() {
/* 1220 */     return this.enforceFileExtensions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnforceFileExtensions(boolean enforce) {
/* 1231 */     this.enforceFileExtensions = enforce;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getZoomAroundAnchor() {
/* 1245 */     return this.zoomAroundAnchor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setZoomAroundAnchor(boolean zoomAroundAnchor) {
/* 1259 */     this.zoomAroundAnchor = zoomAroundAnchor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getZoomFillPaint() {
/* 1273 */     return this.zoomFillPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setZoomFillPaint(Paint paint) {
/* 1287 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 1288 */     this.zoomFillPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getZoomOutlinePaint() {
/* 1302 */     return this.zoomOutlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setZoomOutlinePaint(Paint paint) {
/* 1316 */     this.zoomOutlinePaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMouseWheelEnabled() {
/* 1333 */     return (this.mouseWheelHandler != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMouseWheelEnabled(boolean flag) {
/* 1344 */     if (flag && this.mouseWheelHandler == null) {
/* 1345 */       this.mouseWheelHandler = new MouseWheelHandler(this);
/*      */     }
/* 1347 */     else if (!flag && this.mouseWheelHandler != null) {
/* 1348 */       removeMouseWheelListener(this.mouseWheelHandler);
/* 1349 */       this.mouseWheelHandler = null;
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
/*      */   public void addOverlay(Overlay overlay) {
/* 1361 */     ParamChecks.nullNotPermitted(overlay, "overlay");
/* 1362 */     this.overlays.add(overlay);
/* 1363 */     overlay.addChangeListener(this);
/* 1364 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeOverlay(Overlay overlay) {
/* 1375 */     ParamChecks.nullNotPermitted(overlay, "overlay");
/* 1376 */     boolean removed = this.overlays.remove(overlay);
/* 1377 */     if (removed) {
/* 1378 */       overlay.removeChangeListener(this);
/* 1379 */       repaint();
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
/*      */   public void overlayChanged(OverlayChangeEvent event) {
/* 1392 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDisplayToolTips(boolean flag) {
/* 1404 */     if (flag) {
/* 1405 */       ToolTipManager.sharedInstance().registerComponent(this);
/*      */     } else {
/*      */       
/* 1408 */       ToolTipManager.sharedInstance().unregisterComponent(this);
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
/*      */   public String getToolTipText(MouseEvent e) {
/* 1421 */     String result = null;
/* 1422 */     if (this.info != null) {
/* 1423 */       EntityCollection entities = this.info.getEntityCollection();
/* 1424 */       if (entities != null) {
/* 1425 */         Insets insets = getInsets();
/* 1426 */         ChartEntity entity = entities.getEntity(
/* 1427 */             (int)((e.getX() - insets.left) / this.scaleX), 
/* 1428 */             (int)((e.getY() - insets.top) / this.scaleY));
/* 1429 */         if (entity != null) {
/* 1430 */           result = entity.getToolTipText();
/*      */         }
/*      */       } 
/*      */     } 
/* 1434 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point translateJava2DToScreen(Point2D java2DPoint) {
/* 1445 */     Insets insets = getInsets();
/* 1446 */     int x = (int)(java2DPoint.getX() * this.scaleX + insets.left);
/* 1447 */     int y = (int)(java2DPoint.getY() * this.scaleY + insets.top);
/* 1448 */     return new Point(x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point2D translateScreenToJava2D(Point screenPoint) {
/* 1460 */     Insets insets = getInsets();
/* 1461 */     double x = (screenPoint.getX() - insets.left) / this.scaleX;
/* 1462 */     double y = (screenPoint.getY() - insets.top) / this.scaleY;
/* 1463 */     return new Point2D.Double(x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle2D scale(Rectangle2D rect) {
/* 1475 */     Insets insets = getInsets();
/* 1476 */     double x = rect.getX() * getScaleX() + insets.left;
/* 1477 */     double y = rect.getY() * getScaleY() + insets.top;
/* 1478 */     double w = rect.getWidth() * getScaleX();
/* 1479 */     double h = rect.getHeight() * getScaleY();
/* 1480 */     return new Rectangle2D.Double(x, y, w, h);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChartEntity getEntityForPoint(int viewX, int viewY) {
/* 1496 */     ChartEntity result = null;
/* 1497 */     if (this.info != null) {
/* 1498 */       Insets insets = getInsets();
/* 1499 */       double x = (viewX - insets.left) / this.scaleX;
/* 1500 */       double y = (viewY - insets.top) / this.scaleY;
/* 1501 */       EntityCollection entities = this.info.getEntityCollection();
/* 1502 */       result = (entities != null) ? entities.getEntity(x, y) : null;
/*      */     } 
/* 1504 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getRefreshBuffer() {
/* 1515 */     return this.refreshBuffer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRefreshBuffer(boolean flag) {
/* 1526 */     this.refreshBuffer = flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintComponent(Graphics g) {
/* 1539 */     super.paintComponent(g);
/* 1540 */     if (this.chart == null) {
/*      */       return;
/*      */     }
/* 1543 */     Graphics2D g2 = (Graphics2D)g.create();
/*      */ 
/*      */     
/* 1546 */     Dimension size = getSize();
/* 1547 */     Insets insets = getInsets();
/*      */ 
/*      */     
/* 1550 */     Rectangle2D available = new Rectangle2D.Double(insets.left, insets.top, size.getWidth() - insets.left - insets.right, size.getHeight() - insets.top - insets.bottom);
/*      */ 
/*      */     
/* 1553 */     boolean scale = false;
/* 1554 */     double drawWidth = available.getWidth();
/* 1555 */     double drawHeight = available.getHeight();
/* 1556 */     this.scaleX = 1.0D;
/* 1557 */     this.scaleY = 1.0D;
/*      */     
/* 1559 */     if (drawWidth < this.minimumDrawWidth) {
/* 1560 */       this.scaleX = drawWidth / this.minimumDrawWidth;
/* 1561 */       drawWidth = this.minimumDrawWidth;
/* 1562 */       scale = true;
/*      */     }
/* 1564 */     else if (drawWidth > this.maximumDrawWidth) {
/* 1565 */       this.scaleX = drawWidth / this.maximumDrawWidth;
/* 1566 */       drawWidth = this.maximumDrawWidth;
/* 1567 */       scale = true;
/*      */     } 
/*      */     
/* 1570 */     if (drawHeight < this.minimumDrawHeight) {
/* 1571 */       this.scaleY = drawHeight / this.minimumDrawHeight;
/* 1572 */       drawHeight = this.minimumDrawHeight;
/* 1573 */       scale = true;
/*      */     }
/* 1575 */     else if (drawHeight > this.maximumDrawHeight) {
/* 1576 */       this.scaleY = drawHeight / this.maximumDrawHeight;
/* 1577 */       drawHeight = this.maximumDrawHeight;
/* 1578 */       scale = true;
/*      */     } 
/*      */     
/* 1581 */     Rectangle2D chartArea = new Rectangle2D.Double(0.0D, 0.0D, drawWidth, drawHeight);
/*      */ 
/*      */ 
/*      */     
/* 1585 */     if (this.useBuffer) {
/*      */ 
/*      */       
/* 1588 */       if (this.chartBuffer == null || this.chartBufferWidth != available
/* 1589 */         .getWidth() || this.chartBufferHeight != available
/* 1590 */         .getHeight()) {
/* 1591 */         this.chartBufferWidth = (int)available.getWidth();
/* 1592 */         this.chartBufferHeight = (int)available.getHeight();
/* 1593 */         GraphicsConfiguration gc = g2.getDeviceConfiguration();
/* 1594 */         this.chartBuffer = gc.createCompatibleImage(this.chartBufferWidth, this.chartBufferHeight, 3);
/*      */ 
/*      */         
/* 1597 */         this.refreshBuffer = true;
/*      */       } 
/*      */ 
/*      */       
/* 1601 */       if (this.refreshBuffer) {
/*      */         
/* 1603 */         this.refreshBuffer = false;
/*      */         
/* 1605 */         Rectangle2D bufferArea = new Rectangle2D.Double(0.0D, 0.0D, this.chartBufferWidth, this.chartBufferHeight);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1610 */         Graphics2D bufferG2 = (Graphics2D)this.chartBuffer.getGraphics();
/* 1611 */         Composite savedComposite = bufferG2.getComposite();
/* 1612 */         bufferG2.setComposite(AlphaComposite.getInstance(1, 0.0F));
/*      */         
/* 1614 */         Rectangle r = new Rectangle(0, 0, this.chartBufferWidth, this.chartBufferHeight);
/*      */         
/* 1616 */         bufferG2.fill(r);
/* 1617 */         bufferG2.setComposite(savedComposite);
/*      */         
/* 1619 */         if (scale) {
/* 1620 */           AffineTransform saved = bufferG2.getTransform();
/* 1621 */           AffineTransform st = AffineTransform.getScaleInstance(this.scaleX, this.scaleY);
/*      */           
/* 1623 */           bufferG2.transform(st);
/* 1624 */           this.chart.draw(bufferG2, chartArea, this.anchor, this.info);
/*      */           
/* 1626 */           bufferG2.setTransform(saved);
/*      */         } else {
/* 1628 */           this.chart.draw(bufferG2, bufferArea, this.anchor, this.info);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1635 */       g2.drawImage(this.chartBuffer, insets.left, insets.top, this);
/*      */     } else {
/*      */       
/* 1638 */       AffineTransform saved = g2.getTransform();
/* 1639 */       g2.translate(insets.left, insets.top);
/* 1640 */       if (scale) {
/* 1641 */         AffineTransform st = AffineTransform.getScaleInstance(this.scaleX, this.scaleY);
/*      */         
/* 1643 */         g2.transform(st);
/*      */       } 
/* 1645 */       this.chart.draw(g2, chartArea, this.anchor, this.info);
/* 1646 */       g2.setTransform(saved);
/*      */     } 
/*      */ 
/*      */     
/* 1650 */     Iterator<Overlay> iterator = this.overlays.iterator();
/* 1651 */     while (iterator.hasNext()) {
/* 1652 */       Overlay overlay = iterator.next();
/* 1653 */       overlay.paintOverlay(g2, this);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1659 */     drawZoomRectangle(g2, !this.useBuffer);
/*      */     
/* 1661 */     g2.dispose();
/*      */     
/* 1663 */     this.anchor = null;
/* 1664 */     this.verticalTraceLine = null;
/* 1665 */     this.horizontalTraceLine = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void chartChanged(ChartChangeEvent event) {
/* 1675 */     this.refreshBuffer = true;
/* 1676 */     Plot plot = this.chart.getPlot();
/* 1677 */     if (plot instanceof Zoomable) {
/* 1678 */       Zoomable z = (Zoomable)plot;
/* 1679 */       this.orientation = z.getOrientation();
/*      */     } 
/* 1681 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void chartProgress(ChartProgressEvent event) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionPerformed(ActionEvent event) {
/* 1702 */     String command = event.getActionCommand();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1707 */     double screenX = -1.0D;
/* 1708 */     double screenY = -1.0D;
/* 1709 */     if (this.zoomPoint != null) {
/* 1710 */       screenX = this.zoomPoint.getX();
/* 1711 */       screenY = this.zoomPoint.getY();
/*      */     } 
/*      */     
/* 1714 */     if (command.equals("PROPERTIES")) {
/* 1715 */       doEditChartProperties();
/*      */     }
/* 1717 */     else if (command.equals("COPY")) {
/* 1718 */       doCopy();
/*      */     }
/* 1720 */     else if (command.equals("SAVE_AS_PNG")) {
/*      */       try {
/* 1722 */         doSaveAs();
/*      */       }
/* 1724 */       catch (IOException e) {
/* 1725 */         JOptionPane.showMessageDialog(this, "I/O error occurred.", "Save As PNG", 2);
/*      */       }
/*      */     
/*      */     }
/* 1729 */     else if (command.equals("SAVE_AS_SVG")) {
/*      */       try {
/* 1731 */         saveAsSVG((File)null);
/* 1732 */       } catch (IOException e) {
/* 1733 */         JOptionPane.showMessageDialog(this, "I/O error occurred.", "Save As SVG", 2);
/*      */       }
/*      */     
/*      */     }
/* 1737 */     else if (command.equals("SAVE_AS_PDF")) {
/* 1738 */       saveAsPDF((File)null);
/*      */     }
/* 1740 */     else if (command.equals("PRINT")) {
/* 1741 */       createChartPrintJob();
/*      */     }
/* 1743 */     else if (command.equals("ZOOM_IN_BOTH")) {
/* 1744 */       zoomInBoth(screenX, screenY);
/*      */     }
/* 1746 */     else if (command.equals("ZOOM_IN_DOMAIN")) {
/* 1747 */       zoomInDomain(screenX, screenY);
/*      */     }
/* 1749 */     else if (command.equals("ZOOM_IN_RANGE")) {
/* 1750 */       zoomInRange(screenX, screenY);
/*      */     }
/* 1752 */     else if (command.equals("ZOOM_OUT_BOTH")) {
/* 1753 */       zoomOutBoth(screenX, screenY);
/*      */     }
/* 1755 */     else if (command.equals("ZOOM_DOMAIN_BOTH")) {
/* 1756 */       zoomOutDomain(screenX, screenY);
/*      */     }
/* 1758 */     else if (command.equals("ZOOM_RANGE_BOTH")) {
/* 1759 */       zoomOutRange(screenX, screenY);
/*      */     }
/* 1761 */     else if (command.equals("ZOOM_RESET_BOTH")) {
/* 1762 */       restoreAutoBounds();
/*      */     }
/* 1764 */     else if (command.equals("ZOOM_RESET_DOMAIN")) {
/* 1765 */       restoreAutoDomainBounds();
/*      */     }
/* 1767 */     else if (command.equals("ZOOM_RESET_RANGE")) {
/* 1768 */       restoreAutoRangeBounds();
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
/*      */   public void mouseEntered(MouseEvent e) {
/* 1782 */     if (!this.ownToolTipDelaysActive) {
/* 1783 */       ToolTipManager ttm = ToolTipManager.sharedInstance();
/*      */       
/* 1785 */       this.originalToolTipInitialDelay = ttm.getInitialDelay();
/* 1786 */       ttm.setInitialDelay(this.ownToolTipInitialDelay);
/*      */       
/* 1788 */       this.originalToolTipReshowDelay = ttm.getReshowDelay();
/* 1789 */       ttm.setReshowDelay(this.ownToolTipReshowDelay);
/*      */       
/* 1791 */       this.originalToolTipDismissDelay = ttm.getDismissDelay();
/* 1792 */       ttm.setDismissDelay(this.ownToolTipDismissDelay);
/*      */       
/* 1794 */       this.ownToolTipDelaysActive = true;
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
/*      */   public void mouseExited(MouseEvent e) {
/* 1807 */     if (this.ownToolTipDelaysActive) {
/*      */       
/* 1809 */       ToolTipManager ttm = ToolTipManager.sharedInstance();
/* 1810 */       ttm.setInitialDelay(this.originalToolTipInitialDelay);
/* 1811 */       ttm.setReshowDelay(this.originalToolTipReshowDelay);
/* 1812 */       ttm.setDismissDelay(this.originalToolTipDismissDelay);
/* 1813 */       this.ownToolTipDelaysActive = false;
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
/*      */   public void mousePressed(MouseEvent e) {
/* 1827 */     if (this.chart == null) {
/*      */       return;
/*      */     }
/* 1830 */     Plot plot = this.chart.getPlot();
/* 1831 */     int mods = e.getModifiers();
/* 1832 */     if ((mods & this.panMask) == this.panMask) {
/*      */       
/* 1834 */       if (plot instanceof Pannable) {
/* 1835 */         Pannable pannable = (Pannable)plot;
/* 1836 */         if (pannable.isDomainPannable() || pannable.isRangePannable()) {
/* 1837 */           Rectangle2D screenDataArea = getScreenDataArea(e.getX(), e
/* 1838 */               .getY());
/* 1839 */           if (screenDataArea != null && screenDataArea.contains(e
/* 1840 */               .getPoint())) {
/* 1841 */             this.panW = screenDataArea.getWidth();
/* 1842 */             this.panH = screenDataArea.getHeight();
/* 1843 */             this.panLast = e.getPoint();
/* 1844 */             setCursor(Cursor.getPredefinedCursor(13));
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1852 */     else if (this.zoomRectangle == null) {
/* 1853 */       Rectangle2D screenDataArea = getScreenDataArea(e.getX(), e.getY());
/* 1854 */       if (screenDataArea != null) {
/* 1855 */         this.zoomPoint = getPointInRectangle(e.getX(), e.getY(), screenDataArea);
/*      */       }
/*      */       else {
/*      */         
/* 1859 */         this.zoomPoint = null;
/*      */       } 
/* 1861 */       if (e.isPopupTrigger() && 
/* 1862 */         this.popup != null) {
/* 1863 */         displayPopupMenu(e.getX(), e.getY());
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
/*      */   private Point2D getPointInRectangle(int x, int y, Rectangle2D area) {
/* 1880 */     double xx = Math.max(area.getMinX(), Math.min(x, area.getMaxX()));
/* 1881 */     double yy = Math.max(area.getMinY(), Math.min(y, area.getMaxY()));
/* 1882 */     return new Point2D.Double(xx, yy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mouseDragged(MouseEvent e) {
/*      */     boolean hZoom, vZoom;
/* 1894 */     if (this.popup != null && this.popup.isShowing()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1899 */     if (this.panLast != null) {
/* 1900 */       double dx = e.getX() - this.panLast.getX();
/* 1901 */       double dy = e.getY() - this.panLast.getY();
/* 1902 */       if (dx == 0.0D && dy == 0.0D) {
/*      */         return;
/*      */       }
/* 1905 */       double wPercent = -dx / this.panW;
/* 1906 */       double hPercent = dy / this.panH;
/* 1907 */       boolean old = this.chart.getPlot().isNotify();
/* 1908 */       this.chart.getPlot().setNotify(false);
/* 1909 */       Pannable p = (Pannable)this.chart.getPlot();
/* 1910 */       if (p.getOrientation() == PlotOrientation.VERTICAL) {
/* 1911 */         p.panDomainAxes(wPercent, this.info.getPlotInfo(), this.panLast);
/*      */         
/* 1913 */         p.panRangeAxes(hPercent, this.info.getPlotInfo(), this.panLast);
/*      */       }
/*      */       else {
/*      */         
/* 1917 */         p.panDomainAxes(hPercent, this.info.getPlotInfo(), this.panLast);
/*      */         
/* 1919 */         p.panRangeAxes(wPercent, this.info.getPlotInfo(), this.panLast);
/*      */       } 
/*      */       
/* 1922 */       this.panLast = e.getPoint();
/* 1923 */       this.chart.getPlot().setNotify(old);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1928 */     if (this.zoomPoint == null) {
/*      */       return;
/*      */     }
/* 1931 */     Graphics2D g2 = (Graphics2D)getGraphics();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1937 */     if (!this.useBuffer) {
/* 1938 */       drawZoomRectangle(g2, true);
/*      */     }
/*      */ 
/*      */     
/* 1942 */     if (this.orientation == PlotOrientation.HORIZONTAL) {
/* 1943 */       hZoom = this.rangeZoomable;
/* 1944 */       vZoom = this.domainZoomable;
/*      */     } else {
/*      */       
/* 1947 */       hZoom = this.domainZoomable;
/* 1948 */       vZoom = this.rangeZoomable;
/*      */     } 
/* 1950 */     Rectangle2D scaledDataArea = getScreenDataArea(
/* 1951 */         (int)this.zoomPoint.getX(), (int)this.zoomPoint.getY());
/* 1952 */     if (hZoom && vZoom) {
/*      */       
/* 1954 */       double xmax = Math.min(e.getX(), scaledDataArea.getMaxX());
/* 1955 */       double ymax = Math.min(e.getY(), scaledDataArea.getMaxY());
/* 1956 */       this
/*      */         
/* 1958 */         .zoomRectangle = new Rectangle2D.Double(this.zoomPoint.getX(), this.zoomPoint.getY(), xmax - this.zoomPoint.getX(), ymax - this.zoomPoint.getY());
/*      */     }
/* 1960 */     else if (hZoom) {
/* 1961 */       double xmax = Math.min(e.getX(), scaledDataArea.getMaxX());
/* 1962 */       this
/*      */         
/* 1964 */         .zoomRectangle = new Rectangle2D.Double(this.zoomPoint.getX(), scaledDataArea.getMinY(), xmax - this.zoomPoint.getX(), scaledDataArea.getHeight());
/*      */     }
/* 1966 */     else if (vZoom) {
/* 1967 */       double ymax = Math.min(e.getY(), scaledDataArea.getMaxY());
/* 1968 */       this
/*      */         
/* 1970 */         .zoomRectangle = new Rectangle2D.Double(scaledDataArea.getMinX(), this.zoomPoint.getY(), scaledDataArea.getWidth(), ymax - this.zoomPoint.getY());
/*      */     } 
/*      */ 
/*      */     
/* 1974 */     if (this.useBuffer) {
/* 1975 */       repaint();
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1980 */       drawZoomRectangle(g2, true);
/*      */     } 
/* 1982 */     g2.dispose();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mouseReleased(MouseEvent e) {
/* 1998 */     if (this.panLast != null) {
/* 1999 */       this.panLast = null;
/* 2000 */       setCursor(Cursor.getDefaultCursor());
/*      */     
/*      */     }
/* 2003 */     else if (this.zoomRectangle != null) {
/*      */       boolean hZoom, vZoom;
/* 2005 */       if (this.orientation == PlotOrientation.HORIZONTAL) {
/* 2006 */         hZoom = this.rangeZoomable;
/* 2007 */         vZoom = this.domainZoomable;
/*      */       } else {
/*      */         
/* 2010 */         hZoom = this.domainZoomable;
/* 2011 */         vZoom = this.rangeZoomable;
/*      */       } 
/*      */       
/* 2014 */       boolean zoomTrigger1 = (hZoom && Math.abs(e.getX() - this.zoomPoint
/* 2015 */           .getX()) >= this.zoomTriggerDistance);
/* 2016 */       boolean zoomTrigger2 = (vZoom && Math.abs(e.getY() - this.zoomPoint
/* 2017 */           .getY()) >= this.zoomTriggerDistance);
/* 2018 */       if (zoomTrigger1 || zoomTrigger2) {
/* 2019 */         if ((hZoom && e.getX() < this.zoomPoint.getX()) || (vZoom && e
/* 2020 */           .getY() < this.zoomPoint.getY())) {
/* 2021 */           restoreAutoBounds();
/*      */         } else {
/*      */           double x, y, w, h;
/*      */           
/* 2025 */           Rectangle2D screenDataArea = getScreenDataArea(
/* 2026 */               (int)this.zoomPoint.getX(), 
/* 2027 */               (int)this.zoomPoint.getY());
/* 2028 */           double maxX = screenDataArea.getMaxX();
/* 2029 */           double maxY = screenDataArea.getMaxY();
/*      */ 
/*      */ 
/*      */           
/* 2033 */           if (!vZoom) {
/* 2034 */             x = this.zoomPoint.getX();
/* 2035 */             y = screenDataArea.getMinY();
/* 2036 */             w = Math.min(this.zoomRectangle.getWidth(), maxX - this.zoomPoint
/* 2037 */                 .getX());
/* 2038 */             h = screenDataArea.getHeight();
/*      */           }
/* 2040 */           else if (!hZoom) {
/* 2041 */             x = screenDataArea.getMinX();
/* 2042 */             y = this.zoomPoint.getY();
/* 2043 */             w = screenDataArea.getWidth();
/* 2044 */             h = Math.min(this.zoomRectangle.getHeight(), maxY - this.zoomPoint
/* 2045 */                 .getY());
/*      */           } else {
/*      */             
/* 2048 */             x = this.zoomPoint.getX();
/* 2049 */             y = this.zoomPoint.getY();
/* 2050 */             w = Math.min(this.zoomRectangle.getWidth(), maxX - this.zoomPoint
/* 2051 */                 .getX());
/* 2052 */             h = Math.min(this.zoomRectangle.getHeight(), maxY - this.zoomPoint
/* 2053 */                 .getY());
/*      */           } 
/* 2055 */           Rectangle2D zoomArea = new Rectangle2D.Double(x, y, w, h);
/* 2056 */           zoom(zoomArea);
/*      */         } 
/* 2058 */         this.zoomPoint = null;
/* 2059 */         this.zoomRectangle = null;
/*      */       }
/*      */       else {
/*      */         
/* 2063 */         Graphics2D g2 = (Graphics2D)getGraphics();
/* 2064 */         if (this.useBuffer) {
/* 2065 */           repaint();
/*      */         } else {
/*      */           
/* 2068 */           drawZoomRectangle(g2, true);
/*      */         } 
/* 2070 */         g2.dispose();
/* 2071 */         this.zoomPoint = null;
/* 2072 */         this.zoomRectangle = null;
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 2077 */     else if (e.isPopupTrigger() && 
/* 2078 */       this.popup != null) {
/* 2079 */       displayPopupMenu(e.getX(), e.getY());
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
/*      */   public void mouseClicked(MouseEvent event) {
/* 2094 */     Insets insets = getInsets();
/* 2095 */     int x = (int)((event.getX() - insets.left) / this.scaleX);
/* 2096 */     int y = (int)((event.getY() - insets.top) / this.scaleY);
/*      */     
/* 2098 */     this.anchor = new Point2D.Double(x, y);
/* 2099 */     if (this.chart == null) {
/*      */       return;
/*      */     }
/* 2102 */     this.chart.setNotify(true);
/*      */     
/* 2104 */     Object[] listeners = this.chartMouseListeners.getListeners(ChartMouseListener.class);
/*      */     
/* 2106 */     if (listeners.length == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 2110 */     ChartEntity entity = null;
/* 2111 */     if (this.info != null) {
/* 2112 */       EntityCollection entities = this.info.getEntityCollection();
/* 2113 */       if (entities != null) {
/* 2114 */         entity = entities.getEntity(x, y);
/*      */       }
/*      */     } 
/* 2117 */     ChartMouseEvent chartEvent = new ChartMouseEvent(getChart(), event, entity);
/*      */     
/* 2119 */     for (int i = listeners.length - 1; i >= 0; i--) {
/* 2120 */       ((ChartMouseListener)listeners[i]).chartMouseClicked(chartEvent);
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
/*      */   public void mouseMoved(MouseEvent e) {
/* 2132 */     Graphics2D g2 = (Graphics2D)getGraphics();
/* 2133 */     if (this.horizontalAxisTrace) {
/* 2134 */       drawHorizontalAxisTrace(g2, e.getX());
/*      */     }
/* 2136 */     if (this.verticalAxisTrace) {
/* 2137 */       drawVerticalAxisTrace(g2, e.getY());
/*      */     }
/* 2139 */     g2.dispose();
/*      */     
/* 2141 */     Object[] listeners = this.chartMouseListeners.getListeners(ChartMouseListener.class);
/*      */     
/* 2143 */     if (listeners.length == 0) {
/*      */       return;
/*      */     }
/* 2146 */     Insets insets = getInsets();
/* 2147 */     int x = (int)((e.getX() - insets.left) / this.scaleX);
/* 2148 */     int y = (int)((e.getY() - insets.top) / this.scaleY);
/*      */     
/* 2150 */     ChartEntity entity = null;
/* 2151 */     if (this.info != null) {
/* 2152 */       EntityCollection entities = this.info.getEntityCollection();
/* 2153 */       if (entities != null) {
/* 2154 */         entity = entities.getEntity(x, y);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2160 */     if (this.chart != null) {
/* 2161 */       ChartMouseEvent event = new ChartMouseEvent(getChart(), e, entity);
/* 2162 */       for (int i = listeners.length - 1; i >= 0; i--) {
/* 2163 */         ((ChartMouseListener)listeners[i]).chartMouseMoved(event);
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
/*      */   public void zoomInBoth(double x, double y) {
/* 2176 */     Plot plot = this.chart.getPlot();
/* 2177 */     if (plot == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2183 */     boolean savedNotify = plot.isNotify();
/* 2184 */     plot.setNotify(false);
/* 2185 */     zoomInDomain(x, y);
/* 2186 */     zoomInRange(x, y);
/* 2187 */     plot.setNotify(savedNotify);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomInDomain(double x, double y) {
/* 2199 */     Plot plot = this.chart.getPlot();
/* 2200 */     if (plot instanceof Zoomable) {
/*      */ 
/*      */ 
/*      */       
/* 2204 */       boolean savedNotify = plot.isNotify();
/* 2205 */       plot.setNotify(false);
/* 2206 */       Zoomable z = (Zoomable)plot;
/* 2207 */       z.zoomDomainAxes(this.zoomInFactor, this.info.getPlotInfo(), 
/* 2208 */           translateScreenToJava2D(new Point((int)x, (int)y)), this.zoomAroundAnchor);
/*      */       
/* 2210 */       plot.setNotify(savedNotify);
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
/*      */   public void zoomInRange(double x, double y) {
/* 2223 */     Plot plot = this.chart.getPlot();
/* 2224 */     if (plot instanceof Zoomable) {
/*      */ 
/*      */ 
/*      */       
/* 2228 */       boolean savedNotify = plot.isNotify();
/* 2229 */       plot.setNotify(false);
/* 2230 */       Zoomable z = (Zoomable)plot;
/* 2231 */       z.zoomRangeAxes(this.zoomInFactor, this.info.getPlotInfo(), 
/* 2232 */           translateScreenToJava2D(new Point((int)x, (int)y)), this.zoomAroundAnchor);
/*      */       
/* 2234 */       plot.setNotify(savedNotify);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomOutBoth(double x, double y) {
/* 2245 */     Plot plot = this.chart.getPlot();
/* 2246 */     if (plot == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2252 */     boolean savedNotify = plot.isNotify();
/* 2253 */     plot.setNotify(false);
/* 2254 */     zoomOutDomain(x, y);
/* 2255 */     zoomOutRange(x, y);
/* 2256 */     plot.setNotify(savedNotify);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomOutDomain(double x, double y) {
/* 2268 */     Plot plot = this.chart.getPlot();
/* 2269 */     if (plot instanceof Zoomable) {
/*      */ 
/*      */ 
/*      */       
/* 2273 */       boolean savedNotify = plot.isNotify();
/* 2274 */       plot.setNotify(false);
/* 2275 */       Zoomable z = (Zoomable)plot;
/* 2276 */       z.zoomDomainAxes(this.zoomOutFactor, this.info.getPlotInfo(), 
/* 2277 */           translateScreenToJava2D(new Point((int)x, (int)y)), this.zoomAroundAnchor);
/*      */       
/* 2279 */       plot.setNotify(savedNotify);
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
/*      */   public void zoomOutRange(double x, double y) {
/* 2292 */     Plot plot = this.chart.getPlot();
/* 2293 */     if (plot instanceof Zoomable) {
/*      */ 
/*      */ 
/*      */       
/* 2297 */       boolean savedNotify = plot.isNotify();
/* 2298 */       plot.setNotify(false);
/* 2299 */       Zoomable z = (Zoomable)plot;
/* 2300 */       z.zoomRangeAxes(this.zoomOutFactor, this.info.getPlotInfo(), 
/* 2301 */           translateScreenToJava2D(new Point((int)x, (int)y)), this.zoomAroundAnchor);
/*      */       
/* 2303 */       plot.setNotify(savedNotify);
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
/*      */   public void zoom(Rectangle2D selection) {
/* 2316 */     Point2D selectOrigin = translateScreenToJava2D(new Point(
/* 2317 */           (int)Math.ceil(selection.getX()), 
/* 2318 */           (int)Math.ceil(selection.getY())));
/* 2319 */     PlotRenderingInfo plotInfo = this.info.getPlotInfo();
/* 2320 */     Rectangle2D scaledDataArea = getScreenDataArea(
/* 2321 */         (int)selection.getCenterX(), (int)selection.getCenterY());
/* 2322 */     if (selection.getHeight() > 0.0D && selection.getWidth() > 0.0D) {
/*      */ 
/*      */       
/* 2325 */       double hLower = (selection.getMinX() - scaledDataArea.getMinX()) / scaledDataArea.getWidth();
/*      */       
/* 2327 */       double hUpper = (selection.getMaxX() - scaledDataArea.getMinX()) / scaledDataArea.getWidth();
/*      */       
/* 2329 */       double vLower = (scaledDataArea.getMaxY() - selection.getMaxY()) / scaledDataArea.getHeight();
/*      */       
/* 2331 */       double vUpper = (scaledDataArea.getMaxY() - selection.getMinY()) / scaledDataArea.getHeight();
/*      */       
/* 2333 */       Plot p = this.chart.getPlot();
/* 2334 */       if (p instanceof Zoomable) {
/*      */ 
/*      */ 
/*      */         
/* 2338 */         boolean savedNotify = p.isNotify();
/* 2339 */         p.setNotify(false);
/* 2340 */         Zoomable z = (Zoomable)p;
/* 2341 */         if (z.getOrientation() == PlotOrientation.HORIZONTAL) {
/* 2342 */           z.zoomDomainAxes(vLower, vUpper, plotInfo, selectOrigin);
/* 2343 */           z.zoomRangeAxes(hLower, hUpper, plotInfo, selectOrigin);
/*      */         } else {
/*      */           
/* 2346 */           z.zoomDomainAxes(hLower, hUpper, plotInfo, selectOrigin);
/* 2347 */           z.zoomRangeAxes(vLower, vUpper, plotInfo, selectOrigin);
/*      */         } 
/* 2349 */         p.setNotify(savedNotify);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void restoreAutoBounds() {
/* 2360 */     Plot plot = this.chart.getPlot();
/* 2361 */     if (plot == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2367 */     boolean savedNotify = plot.isNotify();
/* 2368 */     plot.setNotify(false);
/* 2369 */     restoreAutoDomainBounds();
/* 2370 */     restoreAutoRangeBounds();
/* 2371 */     plot.setNotify(savedNotify);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void restoreAutoDomainBounds() {
/* 2378 */     Plot plot = this.chart.getPlot();
/* 2379 */     if (plot instanceof Zoomable) {
/* 2380 */       Zoomable z = (Zoomable)plot;
/*      */ 
/*      */ 
/*      */       
/* 2384 */       boolean savedNotify = plot.isNotify();
/* 2385 */       plot.setNotify(false);
/*      */       
/* 2387 */       Point2D zp = (this.zoomPoint != null) ? this.zoomPoint : new Point();
/*      */       
/* 2389 */       z.zoomDomainAxes(0.0D, this.info.getPlotInfo(), zp);
/* 2390 */       plot.setNotify(savedNotify);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void restoreAutoRangeBounds() {
/* 2398 */     Plot plot = this.chart.getPlot();
/* 2399 */     if (plot instanceof Zoomable) {
/* 2400 */       Zoomable z = (Zoomable)plot;
/*      */ 
/*      */ 
/*      */       
/* 2404 */       boolean savedNotify = plot.isNotify();
/* 2405 */       plot.setNotify(false);
/*      */       
/* 2407 */       Point2D zp = (this.zoomPoint != null) ? this.zoomPoint : new Point();
/*      */       
/* 2409 */       z.zoomRangeAxes(0.0D, this.info.getPlotInfo(), zp);
/* 2410 */       plot.setNotify(savedNotify);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle2D getScreenDataArea() {
/* 2421 */     Rectangle2D dataArea = this.info.getPlotInfo().getDataArea();
/* 2422 */     Insets insets = getInsets();
/* 2423 */     double x = dataArea.getX() * this.scaleX + insets.left;
/* 2424 */     double y = dataArea.getY() * this.scaleY + insets.top;
/* 2425 */     double w = dataArea.getWidth() * this.scaleX;
/* 2426 */     double h = dataArea.getHeight() * this.scaleY;
/* 2427 */     return new Rectangle2D.Double(x, y, w, h);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle2D getScreenDataArea(int x, int y) {
/*      */     Rectangle2D result;
/* 2440 */     PlotRenderingInfo plotInfo = this.info.getPlotInfo();
/*      */     
/* 2442 */     if (plotInfo.getSubplotCount() == 0) {
/* 2443 */       result = getScreenDataArea();
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 2448 */       Point2D selectOrigin = translateScreenToJava2D(new Point(x, y));
/* 2449 */       int subplotIndex = plotInfo.getSubplotIndex(selectOrigin);
/* 2450 */       if (subplotIndex == -1) {
/* 2451 */         return null;
/*      */       }
/* 2453 */       result = scale(plotInfo.getSubplotInfo(subplotIndex).getDataArea());
/*      */     } 
/* 2455 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInitialDelay() {
/* 2466 */     return this.ownToolTipInitialDelay;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getReshowDelay() {
/* 2477 */     return this.ownToolTipReshowDelay;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDismissDelay() {
/* 2489 */     return this.ownToolTipDismissDelay;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitialDelay(int delay) {
/* 2501 */     this.ownToolTipInitialDelay = delay;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReshowDelay(int delay) {
/* 2513 */     this.ownToolTipReshowDelay = delay;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDismissDelay(int delay) {
/* 2525 */     this.ownToolTipDismissDelay = delay;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getZoomInFactor() {
/* 2536 */     return this.zoomInFactor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setZoomInFactor(double factor) {
/* 2547 */     this.zoomInFactor = factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getZoomOutFactor() {
/* 2558 */     return this.zoomOutFactor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setZoomOutFactor(double factor) {
/* 2569 */     this.zoomOutFactor = factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawZoomRectangle(Graphics2D g2, boolean xor) {
/* 2583 */     if (this.zoomRectangle != null) {
/* 2584 */       if (xor)
/*      */       {
/* 2586 */         g2.setXORMode(Color.gray);
/*      */       }
/* 2588 */       if (this.fillZoomRectangle) {
/* 2589 */         g2.setPaint(this.zoomFillPaint);
/* 2590 */         g2.fill(this.zoomRectangle);
/*      */       } else {
/*      */         
/* 2593 */         g2.setPaint(this.zoomOutlinePaint);
/* 2594 */         g2.draw(this.zoomRectangle);
/*      */       } 
/* 2596 */       if (xor)
/*      */       {
/* 2598 */         g2.setPaintMode();
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
/*      */   private void drawHorizontalAxisTrace(Graphics2D g2, int x) {
/* 2612 */     Rectangle2D dataArea = getScreenDataArea();
/*      */     
/* 2614 */     g2.setXORMode(Color.orange);
/* 2615 */     if ((int)dataArea.getMinX() < x && x < (int)dataArea.getMaxX()) {
/*      */       
/* 2617 */       if (this.verticalTraceLine != null) {
/* 2618 */         g2.draw(this.verticalTraceLine);
/* 2619 */         this.verticalTraceLine.setLine(x, (int)dataArea.getMinY(), x, 
/* 2620 */             (int)dataArea.getMaxY());
/*      */       } else {
/*      */         
/* 2623 */         this
/* 2624 */           .verticalTraceLine = new Line2D.Float(x, (int)dataArea.getMinY(), x, (int)dataArea.getMaxY());
/*      */       } 
/* 2626 */       g2.draw(this.verticalTraceLine);
/*      */     } 
/*      */ 
/*      */     
/* 2630 */     g2.setPaintMode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawVerticalAxisTrace(Graphics2D g2, int y) {
/* 2642 */     Rectangle2D dataArea = getScreenDataArea();
/*      */     
/* 2644 */     g2.setXORMode(Color.orange);
/* 2645 */     if ((int)dataArea.getMinY() < y && y < (int)dataArea.getMaxY()) {
/*      */       
/* 2647 */       if (this.horizontalTraceLine != null) {
/* 2648 */         g2.draw(this.horizontalTraceLine);
/* 2649 */         this.horizontalTraceLine.setLine((int)dataArea.getMinX(), y, 
/* 2650 */             (int)dataArea.getMaxX(), y);
/*      */       } else {
/*      */         
/* 2653 */         this
/* 2654 */           .horizontalTraceLine = new Line2D.Float((int)dataArea.getMinX(), y, (int)dataArea.getMaxX(), y);
/*      */       } 
/*      */       
/* 2657 */       g2.draw(this.horizontalTraceLine);
/*      */     } 
/*      */ 
/*      */     
/* 2661 */     g2.setPaintMode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void doEditChartProperties() {
/* 2672 */     ChartEditor editor = ChartEditorManager.getChartEditor(this.chart);
/* 2673 */     int result = JOptionPane.showConfirmDialog(this, editor, localizationResources
/* 2674 */         .getString("Chart_Properties"), 2, -1);
/*      */     
/* 2676 */     if (result == 0) {
/* 2677 */       editor.updateChart(this.chart);
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
/*      */   public void doCopy() {
/* 2689 */     Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 2690 */     Insets insets = getInsets();
/* 2691 */     int w = getWidth() - insets.left - insets.right;
/* 2692 */     int h = getHeight() - insets.top - insets.bottom;
/*      */ 
/*      */     
/* 2695 */     ChartTransferable selection = new ChartTransferable(this.chart, w, h, getMinimumDrawWidth(), getMinimumDrawHeight(), getMaximumDrawWidth(), getMaximumDrawHeight(), true);
/* 2696 */     systemClipboard.setContents(selection, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void doSaveAs() throws IOException {
/* 2706 */     JFileChooser fileChooser = new JFileChooser();
/* 2707 */     fileChooser.setCurrentDirectory(this.defaultDirectoryForSaveAs);
/*      */     
/* 2709 */     FileNameExtensionFilter filter = new FileNameExtensionFilter(localizationResources.getString("PNG_Image_Files"), new String[] { "png" });
/* 2710 */     fileChooser.addChoosableFileFilter(filter);
/* 2711 */     fileChooser.setFileFilter(filter);
/*      */     
/* 2713 */     int option = fileChooser.showSaveDialog(this);
/* 2714 */     if (option == 0) {
/* 2715 */       String filename = fileChooser.getSelectedFile().getPath();
/* 2716 */       if (isEnforceFileExtensions() && 
/* 2717 */         !filename.endsWith(".png")) {
/* 2718 */         filename = filename + ".png";
/*      */       }
/*      */       
/* 2721 */       ChartUtilities.saveChartAsPNG(new File(filename), this.chart, 
/* 2722 */           getWidth(), getHeight());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveAsSVG(File f) throws IOException {
/* 2733 */     File file = f;
/* 2734 */     if (file == null) {
/* 2735 */       JFileChooser fileChooser = new JFileChooser();
/* 2736 */       fileChooser.setCurrentDirectory(this.defaultDirectoryForSaveAs);
/*      */       
/* 2738 */       FileNameExtensionFilter filter = new FileNameExtensionFilter(localizationResources.getString("SVG_Files"), new String[] { "svg" });
/* 2739 */       fileChooser.addChoosableFileFilter(filter);
/* 2740 */       fileChooser.setFileFilter(filter);
/*      */       
/* 2742 */       int option = fileChooser.showSaveDialog(this);
/* 2743 */       if (option == 0) {
/* 2744 */         String filename = fileChooser.getSelectedFile().getPath();
/* 2745 */         if (isEnforceFileExtensions() && 
/* 2746 */           !filename.endsWith(".svg")) {
/* 2747 */           filename = filename + ".svg";
/*      */         }
/*      */         
/* 2750 */         file = new File(filename);
/* 2751 */         if (file.exists()) {
/* 2752 */           String fileExists = localizationResources.getString("FILE_EXISTS_CONFIRM_OVERWRITE");
/*      */           
/* 2754 */           int response = JOptionPane.showConfirmDialog(this, fileExists, "Save As SVG", 2);
/*      */ 
/*      */           
/* 2757 */           if (response == 2) {
/* 2758 */             file = null;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2764 */     if (file != null) {
/*      */       
/* 2766 */       String svg = generateSVG(getWidth(), getHeight());
/* 2767 */       BufferedWriter writer = null;
/*      */       try {
/* 2769 */         writer = new BufferedWriter(new FileWriter(file));
/* 2770 */         writer.write("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n");
/* 2771 */         writer.write(svg + "\n");
/* 2772 */         writer.flush();
/*      */       } finally {
/*      */         try {
/* 2775 */           if (writer != null) {
/* 2776 */             writer.close();
/*      */           }
/* 2778 */         } catch (IOException ex) {
/* 2779 */           throw new RuntimeException(ex);
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
/*      */   private String generateSVG(int width, int height) {
/* 2796 */     Graphics2D g2 = createSVGGraphics2D(width, height);
/* 2797 */     if (g2 == null) {
/* 2798 */       throw new IllegalStateException("JFreeSVG library is not present.");
/*      */     }
/*      */ 
/*      */     
/* 2802 */     g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, Boolean.valueOf(true));
/* 2803 */     String svg = null;
/* 2804 */     Rectangle2D drawArea = new Rectangle2D.Double(0.0D, 0.0D, width, height);
/* 2805 */     this.chart.draw(g2, drawArea);
/*      */     try {
/* 2807 */       Method m = g2.getClass().getMethod("getSVGElement", new Class[0]);
/* 2808 */       svg = (String)m.invoke(g2, new Object[0]);
/* 2809 */     } catch (NoSuchMethodException e) {
/*      */     
/* 2811 */     } catch (SecurityException e) {
/*      */     
/* 2813 */     } catch (IllegalAccessException e) {
/*      */     
/* 2815 */     } catch (IllegalArgumentException e) {
/*      */     
/* 2817 */     } catch (InvocationTargetException e) {}
/*      */ 
/*      */     
/* 2820 */     return svg;
/*      */   }
/*      */   
/*      */   private Graphics2D createSVGGraphics2D(int w, int h) {
/*      */     try {
/* 2825 */       Class<?> svgGraphics2d = Class.forName("org.jfree.graphics2d.svg.SVGGraphics2D");
/* 2826 */       Constructor<?> ctor = svgGraphics2d.getConstructor(new Class[] { int.class, int.class });
/* 2827 */       return (Graphics2D)ctor.newInstance(new Object[] { Integer.valueOf(w), Integer.valueOf(h) });
/* 2828 */     } catch (ClassNotFoundException ex) {
/* 2829 */       return null;
/* 2830 */     } catch (NoSuchMethodException ex) {
/* 2831 */       return null;
/* 2832 */     } catch (SecurityException ex) {
/* 2833 */       return null;
/* 2834 */     } catch (InstantiationException ex) {
/* 2835 */       return null;
/* 2836 */     } catch (IllegalAccessException ex) {
/* 2837 */       return null;
/* 2838 */     } catch (IllegalArgumentException ex) {
/* 2839 */       return null;
/* 2840 */     } catch (InvocationTargetException ex) {
/* 2841 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void saveAsPDF(File f) {
/* 2852 */     File file = f;
/* 2853 */     if (file == null) {
/* 2854 */       JFileChooser fileChooser = new JFileChooser();
/* 2855 */       fileChooser.setCurrentDirectory(this.defaultDirectoryForSaveAs);
/*      */       
/* 2857 */       FileNameExtensionFilter filter = new FileNameExtensionFilter(localizationResources.getString("PDF_Files"), new String[] { "pdf" });
/* 2858 */       fileChooser.addChoosableFileFilter(filter);
/* 2859 */       fileChooser.setFileFilter(filter);
/*      */       
/* 2861 */       int option = fileChooser.showSaveDialog(this);
/* 2862 */       if (option == 0) {
/* 2863 */         String filename = fileChooser.getSelectedFile().getPath();
/* 2864 */         if (isEnforceFileExtensions() && 
/* 2865 */           !filename.endsWith(".pdf")) {
/* 2866 */           filename = filename + ".pdf";
/*      */         }
/*      */         
/* 2869 */         file = new File(filename);
/* 2870 */         if (file.exists()) {
/* 2871 */           String fileExists = localizationResources.getString("FILE_EXISTS_CONFIRM_OVERWRITE");
/*      */           
/* 2873 */           int response = JOptionPane.showConfirmDialog(this, fileExists, "Save As PDF", 2);
/*      */ 
/*      */           
/* 2876 */           if (response == 2) {
/* 2877 */             file = null;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2883 */     if (file != null) {
/* 2884 */       writeAsPDF(file, getWidth(), getHeight());
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
/*      */   private boolean isOrsonPDFAvailable() {
/* 2896 */     Class<?> pdfDocumentClass = null;
/*      */     try {
/* 2898 */       pdfDocumentClass = Class.forName("com.orsonpdf.PDFDocument");
/* 2899 */     } catch (ClassNotFoundException e) {}
/*      */ 
/*      */     
/* 2902 */     return (pdfDocumentClass != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeAsPDF(File file, int w, int h) {
/* 2916 */     if (!isOrsonPDFAvailable()) {
/* 2917 */       throw new IllegalStateException("OrsonPDF is not present on the classpath.");
/*      */     }
/*      */     
/* 2920 */     ParamChecks.nullNotPermitted(file, "file");
/*      */     try {
/* 2922 */       Class<?> pdfDocClass = Class.forName("com.orsonpdf.PDFDocument");
/* 2923 */       Object pdfDoc = pdfDocClass.newInstance();
/* 2924 */       Method m = pdfDocClass.getMethod("createPage", new Class[] { Rectangle2D.class });
/* 2925 */       Rectangle2D rect = new Rectangle(w, h);
/* 2926 */       Object page = m.invoke(pdfDoc, new Object[] { rect });
/* 2927 */       Method m2 = page.getClass().getMethod("getGraphics2D", new Class[0]);
/* 2928 */       Graphics2D g2 = (Graphics2D)m2.invoke(page, new Object[0]);
/*      */ 
/*      */       
/* 2931 */       g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, Boolean.valueOf(true));
/* 2932 */       Rectangle2D drawArea = new Rectangle2D.Double(0.0D, 0.0D, w, h);
/* 2933 */       this.chart.draw(g2, drawArea);
/* 2934 */       Method m3 = pdfDocClass.getMethod("writeToFile", new Class[] { File.class });
/* 2935 */       m3.invoke(pdfDoc, new Object[] { file });
/* 2936 */     } catch (ClassNotFoundException ex) {
/* 2937 */       throw new RuntimeException(ex);
/* 2938 */     } catch (InstantiationException ex) {
/* 2939 */       throw new RuntimeException(ex);
/* 2940 */     } catch (IllegalAccessException ex) {
/* 2941 */       throw new RuntimeException(ex);
/* 2942 */     } catch (NoSuchMethodException ex) {
/* 2943 */       throw new RuntimeException(ex);
/* 2944 */     } catch (SecurityException ex) {
/* 2945 */       throw new RuntimeException(ex);
/* 2946 */     } catch (IllegalArgumentException ex) {
/* 2947 */       throw new RuntimeException(ex);
/* 2948 */     } catch (InvocationTargetException ex) {
/* 2949 */       throw new RuntimeException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void createChartPrintJob() {
/* 2957 */     PrinterJob job = PrinterJob.getPrinterJob();
/* 2958 */     PageFormat pf = job.defaultPage();
/* 2959 */     PageFormat pf2 = job.pageDialog(pf);
/* 2960 */     if (pf2 != pf) {
/* 2961 */       job.setPrintable(this, pf2);
/* 2962 */       if (job.printDialog()) {
/*      */         try {
/* 2964 */           job.print();
/*      */         }
/* 2966 */         catch (PrinterException e) {
/* 2967 */           JOptionPane.showMessageDialog(this, e);
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
/*      */   public int print(Graphics g, PageFormat pf, int pageIndex) {
/* 2986 */     if (pageIndex != 0) {
/* 2987 */       return 1;
/*      */     }
/* 2989 */     Graphics2D g2 = (Graphics2D)g;
/* 2990 */     double x = pf.getImageableX();
/* 2991 */     double y = pf.getImageableY();
/* 2992 */     double w = pf.getImageableWidth();
/* 2993 */     double h = pf.getImageableHeight();
/* 2994 */     this.chart.draw(g2, new Rectangle2D.Double(x, y, w, h), this.anchor, null);
/*      */     
/* 2996 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChartMouseListener(ChartMouseListener listener) {
/* 3006 */     ParamChecks.nullNotPermitted(listener, "listener");
/* 3007 */     this.chartMouseListeners.add(ChartMouseListener.class, listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeChartMouseListener(ChartMouseListener listener) {
/* 3017 */     this.chartMouseListeners.remove(ChartMouseListener.class, listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EventListener[] getListeners(Class<ChartMouseListener> listenerType) {
/* 3030 */     if (listenerType == ChartMouseListener.class)
/*      */     {
/* 3032 */       return this.chartMouseListeners.getListeners((Class)listenerType);
/*      */     }
/*      */     
/* 3035 */     return super.getListeners((Class)listenerType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JPopupMenu createPopupMenu(boolean properties, boolean save, boolean print, boolean zoom) {
/* 3051 */     return createPopupMenu(properties, false, save, print, zoom);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JPopupMenu createPopupMenu(boolean properties, boolean copy, boolean save, boolean print, boolean zoom) {
/* 3070 */     JPopupMenu result = new JPopupMenu(localizationResources.getString("Chart") + ":");
/* 3071 */     boolean separator = false;
/*      */     
/* 3073 */     if (properties) {
/*      */       
/* 3075 */       JMenuItem propertiesItem = new JMenuItem(localizationResources.getString("Properties..."));
/* 3076 */       propertiesItem.setActionCommand("PROPERTIES");
/* 3077 */       propertiesItem.addActionListener(this);
/* 3078 */       result.add(propertiesItem);
/* 3079 */       separator = true;
/*      */     } 
/*      */     
/* 3082 */     if (copy) {
/* 3083 */       if (separator) {
/* 3084 */         result.addSeparator();
/*      */       }
/*      */       
/* 3087 */       JMenuItem copyItem = new JMenuItem(localizationResources.getString("Copy"));
/* 3088 */       copyItem.setActionCommand("COPY");
/* 3089 */       copyItem.addActionListener(this);
/* 3090 */       result.add(copyItem);
/* 3091 */       separator = !save;
/*      */     } 
/*      */     
/* 3094 */     if (save) {
/* 3095 */       if (separator) {
/* 3096 */         result.addSeparator();
/*      */       }
/* 3098 */       JMenu saveSubMenu = new JMenu(localizationResources.getString("Save_as"));
/*      */       
/* 3100 */       JMenuItem pngItem = new JMenuItem(localizationResources.getString("PNG..."));
/*      */       
/* 3102 */       pngItem.setActionCommand("SAVE_AS_PNG");
/* 3103 */       pngItem.addActionListener(this);
/* 3104 */       saveSubMenu.add(pngItem);
/*      */       
/* 3106 */       if (createSVGGraphics2D(10, 10) != null) {
/* 3107 */         JMenuItem svgItem = new JMenuItem(localizationResources.getString("SVG..."));
/*      */         
/* 3109 */         svgItem.setActionCommand("SAVE_AS_SVG");
/* 3110 */         svgItem.addActionListener(this);
/* 3111 */         saveSubMenu.add(svgItem);
/*      */       } 
/*      */       
/* 3114 */       if (isOrsonPDFAvailable()) {
/*      */         
/* 3116 */         JMenuItem pdfItem = new JMenuItem(localizationResources.getString("PDF..."));
/* 3117 */         pdfItem.setActionCommand("SAVE_AS_PDF");
/* 3118 */         pdfItem.addActionListener(this);
/* 3119 */         saveSubMenu.add(pdfItem);
/*      */       } 
/* 3121 */       result.add(saveSubMenu);
/* 3122 */       separator = true;
/*      */     } 
/*      */     
/* 3125 */     if (print) {
/* 3126 */       if (separator) {
/* 3127 */         result.addSeparator();
/*      */       }
/*      */       
/* 3130 */       JMenuItem printItem = new JMenuItem(localizationResources.getString("Print..."));
/* 3131 */       printItem.setActionCommand("PRINT");
/* 3132 */       printItem.addActionListener(this);
/* 3133 */       result.add(printItem);
/* 3134 */       separator = true;
/*      */     } 
/*      */     
/* 3137 */     if (zoom) {
/* 3138 */       if (separator) {
/* 3139 */         result.addSeparator();
/*      */       }
/*      */ 
/*      */       
/* 3143 */       JMenu zoomInMenu = new JMenu(localizationResources.getString("Zoom_In"));
/*      */       
/* 3145 */       this
/* 3146 */         .zoomInBothMenuItem = new JMenuItem(localizationResources.getString("All_Axes"));
/* 3147 */       this.zoomInBothMenuItem.setActionCommand("ZOOM_IN_BOTH");
/* 3148 */       this.zoomInBothMenuItem.addActionListener(this);
/* 3149 */       zoomInMenu.add(this.zoomInBothMenuItem);
/*      */       
/* 3151 */       zoomInMenu.addSeparator();
/*      */       
/* 3153 */       this
/* 3154 */         .zoomInDomainMenuItem = new JMenuItem(localizationResources.getString("Domain_Axis"));
/* 3155 */       this.zoomInDomainMenuItem.setActionCommand("ZOOM_IN_DOMAIN");
/* 3156 */       this.zoomInDomainMenuItem.addActionListener(this);
/* 3157 */       zoomInMenu.add(this.zoomInDomainMenuItem);
/*      */       
/* 3159 */       this
/* 3160 */         .zoomInRangeMenuItem = new JMenuItem(localizationResources.getString("Range_Axis"));
/* 3161 */       this.zoomInRangeMenuItem.setActionCommand("ZOOM_IN_RANGE");
/* 3162 */       this.zoomInRangeMenuItem.addActionListener(this);
/* 3163 */       zoomInMenu.add(this.zoomInRangeMenuItem);
/*      */       
/* 3165 */       result.add(zoomInMenu);
/*      */ 
/*      */       
/* 3168 */       JMenu zoomOutMenu = new JMenu(localizationResources.getString("Zoom_Out"));
/*      */       
/* 3170 */       this
/* 3171 */         .zoomOutBothMenuItem = new JMenuItem(localizationResources.getString("All_Axes"));
/* 3172 */       this.zoomOutBothMenuItem.setActionCommand("ZOOM_OUT_BOTH");
/* 3173 */       this.zoomOutBothMenuItem.addActionListener(this);
/* 3174 */       zoomOutMenu.add(this.zoomOutBothMenuItem);
/*      */       
/* 3176 */       zoomOutMenu.addSeparator();
/*      */       
/* 3178 */       this
/* 3179 */         .zoomOutDomainMenuItem = new JMenuItem(localizationResources.getString("Domain_Axis"));
/* 3180 */       this.zoomOutDomainMenuItem.setActionCommand("ZOOM_DOMAIN_BOTH");
/*      */       
/* 3182 */       this.zoomOutDomainMenuItem.addActionListener(this);
/* 3183 */       zoomOutMenu.add(this.zoomOutDomainMenuItem);
/*      */       
/* 3185 */       this
/* 3186 */         .zoomOutRangeMenuItem = new JMenuItem(localizationResources.getString("Range_Axis"));
/* 3187 */       this.zoomOutRangeMenuItem.setActionCommand("ZOOM_RANGE_BOTH");
/* 3188 */       this.zoomOutRangeMenuItem.addActionListener(this);
/* 3189 */       zoomOutMenu.add(this.zoomOutRangeMenuItem);
/*      */       
/* 3191 */       result.add(zoomOutMenu);
/*      */ 
/*      */       
/* 3194 */       JMenu autoRangeMenu = new JMenu(localizationResources.getString("Auto_Range"));
/*      */       
/* 3196 */       this
/* 3197 */         .zoomResetBothMenuItem = new JMenuItem(localizationResources.getString("All_Axes"));
/* 3198 */       this.zoomResetBothMenuItem.setActionCommand("ZOOM_RESET_BOTH");
/*      */       
/* 3200 */       this.zoomResetBothMenuItem.addActionListener(this);
/* 3201 */       autoRangeMenu.add(this.zoomResetBothMenuItem);
/*      */       
/* 3203 */       autoRangeMenu.addSeparator();
/* 3204 */       this
/* 3205 */         .zoomResetDomainMenuItem = new JMenuItem(localizationResources.getString("Domain_Axis"));
/* 3206 */       this.zoomResetDomainMenuItem.setActionCommand("ZOOM_RESET_DOMAIN");
/*      */       
/* 3208 */       this.zoomResetDomainMenuItem.addActionListener(this);
/* 3209 */       autoRangeMenu.add(this.zoomResetDomainMenuItem);
/*      */       
/* 3211 */       this
/* 3212 */         .zoomResetRangeMenuItem = new JMenuItem(localizationResources.getString("Range_Axis"));
/* 3213 */       this.zoomResetRangeMenuItem.setActionCommand("ZOOM_RESET_RANGE");
/*      */       
/* 3215 */       this.zoomResetRangeMenuItem.addActionListener(this);
/* 3216 */       autoRangeMenu.add(this.zoomResetRangeMenuItem);
/*      */       
/* 3218 */       result.addSeparator();
/* 3219 */       result.add(autoRangeMenu);
/*      */     } 
/*      */ 
/*      */     
/* 3223 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void displayPopupMenu(int x, int y) {
/* 3236 */     if (this.popup == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3242 */     boolean isDomainZoomable = false;
/* 3243 */     boolean isRangeZoomable = false;
/* 3244 */     Plot plot = (this.chart != null) ? this.chart.getPlot() : null;
/* 3245 */     if (plot instanceof Zoomable) {
/* 3246 */       Zoomable z = (Zoomable)plot;
/* 3247 */       isDomainZoomable = z.isDomainZoomable();
/* 3248 */       isRangeZoomable = z.isRangeZoomable();
/*      */     } 
/*      */     
/* 3251 */     if (this.zoomInDomainMenuItem != null) {
/* 3252 */       this.zoomInDomainMenuItem.setEnabled(isDomainZoomable);
/*      */     }
/* 3254 */     if (this.zoomOutDomainMenuItem != null) {
/* 3255 */       this.zoomOutDomainMenuItem.setEnabled(isDomainZoomable);
/*      */     }
/* 3257 */     if (this.zoomResetDomainMenuItem != null) {
/* 3258 */       this.zoomResetDomainMenuItem.setEnabled(isDomainZoomable);
/*      */     }
/*      */     
/* 3261 */     if (this.zoomInRangeMenuItem != null) {
/* 3262 */       this.zoomInRangeMenuItem.setEnabled(isRangeZoomable);
/*      */     }
/* 3264 */     if (this.zoomOutRangeMenuItem != null) {
/* 3265 */       this.zoomOutRangeMenuItem.setEnabled(isRangeZoomable);
/*      */     }
/*      */     
/* 3268 */     if (this.zoomResetRangeMenuItem != null) {
/* 3269 */       this.zoomResetRangeMenuItem.setEnabled(isRangeZoomable);
/*      */     }
/*      */     
/* 3272 */     if (this.zoomInBothMenuItem != null) {
/* 3273 */       this.zoomInBothMenuItem.setEnabled((isDomainZoomable && isRangeZoomable));
/*      */     }
/*      */     
/* 3276 */     if (this.zoomOutBothMenuItem != null) {
/* 3277 */       this.zoomOutBothMenuItem.setEnabled((isDomainZoomable && isRangeZoomable));
/*      */     }
/*      */     
/* 3280 */     if (this.zoomResetBothMenuItem != null) {
/* 3281 */       this.zoomResetBothMenuItem.setEnabled((isDomainZoomable && isRangeZoomable));
/*      */     }
/*      */ 
/*      */     
/* 3285 */     this.popup.show(this, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateUI() {
/* 3296 */     if (this.popup != null) {
/* 3297 */       SwingUtilities.updateComponentTreeUI(this.popup);
/*      */     }
/* 3299 */     super.updateUI();
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
/* 3310 */     stream.defaultWriteObject();
/* 3311 */     SerialUtilities.writePaint(this.zoomFillPaint, stream);
/* 3312 */     SerialUtilities.writePaint(this.zoomOutlinePaint, stream);
/*      */   }
/*      */ 
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
/* 3325 */     stream.defaultReadObject();
/* 3326 */     this.zoomFillPaint = SerialUtilities.readPaint(stream);
/* 3327 */     this.zoomOutlinePaint = SerialUtilities.readPaint(stream);
/*      */ 
/*      */     
/* 3330 */     this.chartMouseListeners = new EventListenerList();
/*      */ 
/*      */     
/* 3333 */     if (this.chart != null)
/* 3334 */       this.chart.addChangeListener(this); 
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/ChartPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */