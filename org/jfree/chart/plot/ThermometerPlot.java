/*      */ package org.jfree.chart.plot;
/*      */ 
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Area;
/*      */ import java.awt.geom.Ellipse2D;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.geom.RoundRectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.Arrays;
/*      */ import java.util.ResourceBundle;
/*      */ import org.jfree.chart.LegendItemCollection;
/*      */ import org.jfree.chart.axis.NumberAxis;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.chart.util.ResourceBundleWrapper;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.general.DatasetChangeEvent;
/*      */ import org.jfree.data.general.DefaultValueDataset;
/*      */ import org.jfree.data.general.ValueDataset;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ import org.jfree.util.ObjectUtilities;
/*      */ import org.jfree.util.PaintUtilities;
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
/*      */ public class ThermometerPlot
/*      */   extends Plot
/*      */   implements ValueAxisPlot, Zoomable, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 4087093313147984390L;
/*      */   public static final int UNITS_NONE = 0;
/*      */   public static final int UNITS_FAHRENHEIT = 1;
/*      */   public static final int UNITS_CELCIUS = 2;
/*      */   public static final int UNITS_KELVIN = 3;
/*      */   public static final int NONE = 0;
/*      */   public static final int RIGHT = 1;
/*      */   public static final int LEFT = 2;
/*      */   public static final int BULB = 3;
/*      */   public static final int NORMAL = 0;
/*      */   public static final int WARNING = 1;
/*      */   public static final int CRITICAL = 2;
/*      */   protected static final int BULB_RADIUS = 40;
/*      */   protected static final int BULB_DIAMETER = 80;
/*      */   protected static final int COLUMN_RADIUS = 20;
/*      */   protected static final int COLUMN_DIAMETER = 40;
/*      */   protected static final int GAP_RADIUS = 5;
/*      */   protected static final int GAP_DIAMETER = 10;
/*      */   protected static final int AXIS_GAP = 10;
/*  238 */   protected static final String[] UNITS = new String[] { "", "°F", "°C", "°K" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int RANGE_LOW = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int RANGE_HIGH = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int DISPLAY_LOW = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int DISPLAY_HIGH = 3;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final double DEFAULT_LOWER_BOUND = 0.0D;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final double DEFAULT_UPPER_BOUND = 100.0D;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int DEFAULT_BULB_RADIUS = 40;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int DEFAULT_COLUMN_RADIUS = 20;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int DEFAULT_GAP = 5;
/*      */ 
/*      */ 
/*      */   
/*      */   private ValueDataset dataset;
/*      */ 
/*      */ 
/*      */   
/*      */   private ValueAxis rangeAxis;
/*      */ 
/*      */ 
/*      */   
/*  287 */   private double lowerBound = 0.0D;
/*      */ 
/*      */   
/*  290 */   private double upperBound = 100.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  297 */   private int bulbRadius = 40;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  304 */   private int columnRadius = 20;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  311 */   private int gap = 5;
/*      */ 
/*      */ 
/*      */   
/*      */   private RectangleInsets padding;
/*      */ 
/*      */ 
/*      */   
/*  319 */   private transient Stroke thermometerStroke = new BasicStroke(1.0F);
/*      */ 
/*      */   
/*  322 */   private transient Paint thermometerPaint = Color.black;
/*      */ 
/*      */   
/*  325 */   private int units = 2;
/*      */ 
/*      */   
/*  328 */   private int valueLocation = 3;
/*      */ 
/*      */   
/*  331 */   private int axisLocation = 2;
/*      */ 
/*      */   
/*  334 */   private Font valueFont = new Font("SansSerif", 1, 16);
/*      */ 
/*      */   
/*  337 */   private transient Paint valuePaint = Color.white;
/*      */ 
/*      */   
/*  340 */   private NumberFormat valueFormat = new DecimalFormat();
/*      */ 
/*      */   
/*  343 */   private transient Paint mercuryPaint = Color.lightGray;
/*      */ 
/*      */   
/*      */   private boolean showValueLines = false;
/*      */ 
/*      */   
/*  349 */   private int subrange = -1;
/*      */ 
/*      */   
/*  352 */   private double[][] subrangeInfo = new double[][] { { 0.0D, 50.0D, 0.0D, 50.0D }, { 50.0D, 75.0D, 50.0D, 75.0D }, { 75.0D, 100.0D, 75.0D, 100.0D } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean followDataInSubranges = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useSubrangePaint = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  371 */   private transient Paint[] subrangePaint = new Paint[] { Color.green, Color.orange, Color.red };
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean subrangeIndicatorsVisible = true;
/*      */ 
/*      */   
/*  378 */   private transient Stroke subrangeIndicatorStroke = new BasicStroke(2.0F);
/*      */ 
/*      */   
/*  381 */   private transient Stroke rangeIndicatorStroke = new BasicStroke(3.0F);
/*      */ 
/*      */ 
/*      */   
/*  385 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ThermometerPlot() {
/*  392 */     this((ValueDataset)new DefaultValueDataset());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ThermometerPlot(ValueDataset dataset) {
/*  404 */     this.padding = new RectangleInsets(UnitType.RELATIVE, 0.05D, 0.05D, 0.05D, 0.05D);
/*      */     
/*  406 */     this.dataset = dataset;
/*  407 */     if (dataset != null) {
/*  408 */       dataset.addChangeListener(this);
/*      */     }
/*  410 */     NumberAxis axis = new NumberAxis(null);
/*  411 */     axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
/*  412 */     axis.setAxisLineVisible(false);
/*  413 */     axis.setPlot(this);
/*  414 */     axis.addChangeListener(this);
/*  415 */     this.rangeAxis = (ValueAxis)axis;
/*  416 */     setAxisRange();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueDataset getDataset() {
/*  427 */     return this.dataset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataset(ValueDataset dataset) {
/*  442 */     ValueDataset existing = this.dataset;
/*  443 */     if (existing != null) {
/*  444 */       existing.removeChangeListener(this);
/*      */     }
/*      */ 
/*      */     
/*  448 */     this.dataset = dataset;
/*  449 */     if (dataset != null) {
/*  450 */       setDatasetGroup(dataset.getGroup());
/*  451 */       dataset.addChangeListener(this);
/*      */     } 
/*      */ 
/*      */     
/*  455 */     DatasetChangeEvent event = new DatasetChangeEvent(this, (Dataset)dataset);
/*  456 */     datasetChanged(event);
/*      */   }
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
/*  468 */     return this.rangeAxis;
/*      */   }
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
/*  480 */     ParamChecks.nullNotPermitted(axis, "axis");
/*      */     
/*  482 */     this.rangeAxis.removeChangeListener(this);
/*      */     
/*  484 */     axis.setPlot(this);
/*  485 */     axis.addChangeListener(this);
/*  486 */     this.rangeAxis = axis;
/*  487 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getLowerBound() {
/*  499 */     return this.lowerBound;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLowerBound(double lower) {
/*  510 */     this.lowerBound = lower;
/*  511 */     setAxisRange();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getUpperBound() {
/*  523 */     return this.upperBound;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUpperBound(double upper) {
/*  534 */     this.upperBound = upper;
/*  535 */     setAxisRange();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRange(double lower, double upper) {
/*  545 */     this.lowerBound = lower;
/*  546 */     this.upperBound = upper;
/*  547 */     setAxisRange();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleInsets getPadding() {
/*  559 */     return this.padding;
/*      */   }
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
/*  571 */     ParamChecks.nullNotPermitted(padding, "padding");
/*  572 */     this.padding = padding;
/*  573 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getThermometerStroke() {
/*  585 */     return this.thermometerStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThermometerStroke(Stroke s) {
/*  597 */     if (s != null) {
/*  598 */       this.thermometerStroke = s;
/*  599 */       fireChangeEvent();
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
/*      */   public Paint getThermometerPaint() {
/*  612 */     return this.thermometerPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThermometerPaint(Paint paint) {
/*  624 */     if (paint != null) {
/*  625 */       this.thermometerPaint = paint;
/*  626 */       fireChangeEvent();
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
/*      */   public int getUnits() {
/*  640 */     return this.units;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUnits(int u) {
/*  659 */     if (u >= 0 && u < UNITS.length && 
/*  660 */       this.units != u) {
/*  661 */       this.units = u;
/*  662 */       fireChangeEvent();
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
/*      */   public void setUnits(String u) {
/*  676 */     if (u == null) {
/*      */       return;
/*      */     }
/*      */     
/*  680 */     u = u.toUpperCase().trim();
/*  681 */     for (int i = 0; i < UNITS.length; i++) {
/*  682 */       if (u.equals(UNITS[i].toUpperCase().trim())) {
/*  683 */         setUnits(i);
/*  684 */         i = UNITS.length;
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
/*      */   public int getValueLocation() {
/*  697 */     return this.valueLocation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValueLocation(int location) {
/*  713 */     if (location >= 0 && location < 4) {
/*  714 */       this.valueLocation = location;
/*  715 */       fireChangeEvent();
/*      */     } else {
/*      */       
/*  718 */       throw new IllegalArgumentException("Location not recognised.");
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
/*      */   public int getAxisLocation() {
/*  731 */     return this.axisLocation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxisLocation(int location) {
/*  745 */     if (location >= 0 && location < 3) {
/*  746 */       this.axisLocation = location;
/*  747 */       fireChangeEvent();
/*      */     } else {
/*      */       
/*  750 */       throw new IllegalArgumentException("Location not recognised.");
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
/*      */   public Font getValueFont() {
/*  762 */     return this.valueFont;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValueFont(Font f) {
/*  773 */     ParamChecks.nullNotPermitted(f, "f");
/*  774 */     if (!this.valueFont.equals(f)) {
/*  775 */       this.valueFont = f;
/*  776 */       fireChangeEvent();
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
/*      */   public Paint getValuePaint() {
/*  788 */     return this.valuePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValuePaint(Paint paint) {
/*  800 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  801 */     if (!this.valuePaint.equals(paint)) {
/*  802 */       this.valuePaint = paint;
/*  803 */       fireChangeEvent();
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
/*      */   public void setValueFormat(NumberFormat formatter) {
/*  816 */     ParamChecks.nullNotPermitted(formatter, "formatter");
/*  817 */     this.valueFormat = formatter;
/*  818 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getMercuryPaint() {
/*  829 */     return this.mercuryPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMercuryPaint(Paint paint) {
/*  841 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  842 */     this.mercuryPaint = paint;
/*  843 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getShowValueLines() {
/*  857 */     return this.showValueLines;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShowValueLines(boolean b) {
/*  871 */     this.showValueLines = b;
/*  872 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSubrangeInfo(int range, double low, double hi) {
/*  883 */     setSubrangeInfo(range, low, hi, low, hi);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSubrangeInfo(int range, double rangeLow, double rangeHigh, double displayLow, double displayHigh) {
/*  899 */     if (range >= 0 && range < 3) {
/*  900 */       setSubrange(range, rangeLow, rangeHigh);
/*  901 */       setDisplayRange(range, displayLow, displayHigh);
/*  902 */       setAxisRange();
/*  903 */       fireChangeEvent();
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
/*      */   public void setSubrange(int range, double low, double high) {
/*  916 */     if (range >= 0 && range < 3) {
/*  917 */       this.subrangeInfo[range][1] = high;
/*  918 */       this.subrangeInfo[range][0] = low;
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
/*      */   public void setDisplayRange(int range, double low, double high) {
/*  931 */     if (range >= 0 && range < this.subrangeInfo.length && 
/*  932 */       isValidNumber(high) && isValidNumber(low))
/*      */     {
/*  934 */       if (high > low) {
/*  935 */         this.subrangeInfo[range][3] = high;
/*  936 */         this.subrangeInfo[range][2] = low;
/*      */       } else {
/*      */         
/*  939 */         this.subrangeInfo[range][3] = low;
/*  940 */         this.subrangeInfo[range][2] = high;
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
/*      */   public Paint getSubrangePaint(int range) {
/*  957 */     if (range >= 0 && range < this.subrangePaint.length) {
/*  958 */       return this.subrangePaint[range];
/*      */     }
/*      */     
/*  961 */     return this.mercuryPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSubrangePaint(int range, Paint paint) {
/*  975 */     if (range >= 0 && range < this.subrangePaint.length && paint != null) {
/*      */       
/*  977 */       this.subrangePaint[range] = paint;
/*  978 */       fireChangeEvent();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFollowDataInSubranges() {
/*  989 */     return this.followDataInSubranges;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFollowDataInSubranges(boolean flag) {
/*  999 */     this.followDataInSubranges = flag;
/* 1000 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseSubrangePaint() {
/* 1012 */     return this.useSubrangePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseSubrangePaint(boolean flag) {
/* 1023 */     this.useSubrangePaint = flag;
/* 1024 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBulbRadius() {
/* 1035 */     return this.bulbRadius;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBulbRadius(int r) {
/* 1049 */     this.bulbRadius = r;
/* 1050 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBulbDiameter() {
/* 1062 */     return getBulbRadius() * 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumnRadius() {
/* 1075 */     return this.columnRadius;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumnRadius(int r) {
/* 1089 */     this.columnRadius = r;
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
/*      */   
/*      */   public int getColumnDiameter() {
/* 1102 */     return getColumnRadius() * 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getGap() {
/* 1116 */     return this.gap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGap(int gap) {
/* 1131 */     this.gap = gap;
/* 1132 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1150 */     RoundRectangle2D outerStem = new RoundRectangle2D.Double();
/* 1151 */     RoundRectangle2D innerStem = new RoundRectangle2D.Double();
/* 1152 */     RoundRectangle2D mercuryStem = new RoundRectangle2D.Double();
/* 1153 */     Ellipse2D outerBulb = new Ellipse2D.Double();
/* 1154 */     Ellipse2D innerBulb = new Ellipse2D.Double();
/*      */ 
/*      */     
/* 1157 */     if (info != null) {
/* 1158 */       info.setPlotArea(area);
/*      */     }
/*      */ 
/*      */     
/* 1162 */     RectangleInsets insets = getInsets();
/* 1163 */     insets.trim(area);
/* 1164 */     drawBackground(g2, area);
/*      */ 
/*      */     
/* 1167 */     Rectangle2D interior = (Rectangle2D)area.clone();
/* 1168 */     this.padding.trim(interior);
/* 1169 */     int midX = (int)(interior.getX() + interior.getWidth() / 2.0D);
/* 1170 */     int midY = (int)(interior.getY() + interior.getHeight() / 2.0D);
/* 1171 */     int stemTop = (int)(interior.getMinY() + getBulbRadius());
/* 1172 */     int stemBottom = (int)(interior.getMaxY() - getBulbDiameter());
/*      */     
/* 1174 */     Rectangle2D dataArea = new Rectangle2D.Double((midX - getColumnRadius()), stemTop, getColumnRadius(), (stemBottom - stemTop));
/*      */     
/* 1176 */     outerBulb.setFrame((midX - getBulbRadius()), stemBottom, 
/* 1177 */         getBulbDiameter(), getBulbDiameter());
/*      */     
/* 1179 */     outerStem.setRoundRect((midX - getColumnRadius()), interior.getMinY(), 
/* 1180 */         getColumnDiameter(), (stemBottom + getBulbDiameter() - stemTop), 
/* 1181 */         getColumnDiameter(), getColumnDiameter());
/*      */     
/* 1183 */     Area outerThermometer = new Area(outerBulb);
/* 1184 */     Area tempArea = new Area(outerStem);
/* 1185 */     outerThermometer.add(tempArea);
/*      */     
/* 1187 */     innerBulb.setFrame((midX - getBulbRadius() + getGap()), (stemBottom + 
/* 1188 */         getGap()), (getBulbDiameter() - getGap() * 2), (getBulbDiameter() - 
/* 1189 */         getGap() * 2));
/*      */     
/* 1191 */     innerStem.setRoundRect((midX - getColumnRadius() + getGap()), interior
/* 1192 */         .getMinY() + getGap(), (getColumnDiameter() - 
/* 1193 */         getGap() * 2), (stemBottom + getBulbDiameter() - getGap() * 2 - stemTop), (
/* 1194 */         getColumnDiameter() - getGap() * 2), (
/* 1195 */         getColumnDiameter() - getGap() * 2));
/*      */     
/* 1197 */     Area innerThermometer = new Area(innerBulb);
/* 1198 */     tempArea = new Area(innerStem);
/* 1199 */     innerThermometer.add(tempArea);
/*      */     
/* 1201 */     if (this.dataset != null && this.dataset.getValue() != null) {
/* 1202 */       String temp, valueString; int stringWidth; double current = this.dataset.getValue().doubleValue();
/* 1203 */       double ds = this.rangeAxis.valueToJava2D(current, dataArea, RectangleEdge.LEFT);
/*      */ 
/*      */       
/* 1206 */       int i = getColumnDiameter() - getGap() * 2;
/* 1207 */       int j = getColumnRadius() - getGap();
/* 1208 */       int l = i / 2;
/* 1209 */       int k = (int)Math.round(ds);
/* 1210 */       if (k < getGap() + interior.getMinY()) {
/* 1211 */         k = (int)(getGap() + interior.getMinY());
/* 1212 */         l = getBulbRadius();
/*      */       } 
/*      */       
/* 1215 */       Area mercury = new Area(innerBulb);
/*      */       
/* 1217 */       if (k < stemBottom + getBulbRadius()) {
/* 1218 */         mercuryStem.setRoundRect((midX - j), k, i, (stemBottom + 
/* 1219 */             getBulbRadius() - k), l, l);
/* 1220 */         tempArea = new Area(mercuryStem);
/* 1221 */         mercury.add(tempArea);
/*      */       } 
/*      */       
/* 1224 */       g2.setPaint(getCurrentPaint());
/* 1225 */       g2.fill(mercury);
/*      */ 
/*      */       
/* 1228 */       if (this.subrangeIndicatorsVisible) {
/* 1229 */         g2.setStroke(this.subrangeIndicatorStroke);
/* 1230 */         Range range = this.rangeAxis.getRange();
/*      */ 
/*      */         
/* 1233 */         double value = this.subrangeInfo[0][0];
/* 1234 */         if (range.contains(value)) {
/* 1235 */           double x = (midX + getColumnRadius() + 2);
/* 1236 */           double y = this.rangeAxis.valueToJava2D(value, dataArea, RectangleEdge.LEFT);
/*      */           
/* 1238 */           Line2D line = new Line2D.Double(x, y, x + 10.0D, y);
/* 1239 */           g2.setPaint(this.subrangePaint[0]);
/* 1240 */           g2.draw(line);
/*      */         } 
/*      */ 
/*      */         
/* 1244 */         value = this.subrangeInfo[1][0];
/* 1245 */         if (range.contains(value)) {
/* 1246 */           double x = (midX + getColumnRadius() + 2);
/* 1247 */           double y = this.rangeAxis.valueToJava2D(value, dataArea, RectangleEdge.LEFT);
/*      */           
/* 1249 */           Line2D line = new Line2D.Double(x, y, x + 10.0D, y);
/* 1250 */           g2.setPaint(this.subrangePaint[1]);
/* 1251 */           g2.draw(line);
/*      */         } 
/*      */ 
/*      */         
/* 1255 */         value = this.subrangeInfo[2][0];
/* 1256 */         if (range.contains(value)) {
/* 1257 */           double x = (midX + getColumnRadius() + 2);
/* 1258 */           double y = this.rangeAxis.valueToJava2D(value, dataArea, RectangleEdge.LEFT);
/*      */           
/* 1260 */           Line2D line = new Line2D.Double(x, y, x + 10.0D, y);
/* 1261 */           g2.setPaint(this.subrangePaint[2]);
/* 1262 */           g2.draw(line);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1267 */       if (this.rangeAxis != null && this.axisLocation != 0) {
/* 1268 */         Rectangle2D drawArea; double cursor; int drawWidth = 10;
/* 1269 */         if (this.showValueLines) {
/* 1270 */           drawWidth += getColumnDiameter();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1275 */         switch (this.axisLocation) {
/*      */           case 1:
/* 1277 */             cursor = (midX + getColumnRadius());
/* 1278 */             drawArea = new Rectangle2D.Double(cursor, stemTop, drawWidth, (stemBottom - stemTop + 1));
/*      */             
/* 1280 */             this.rangeAxis.draw(g2, cursor, area, drawArea, RectangleEdge.RIGHT, null);
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/* 1287 */             cursor = (midX - getColumnRadius());
/* 1288 */             drawArea = new Rectangle2D.Double(cursor, stemTop, drawWidth, (stemBottom - stemTop + 1));
/*      */             
/* 1290 */             this.rangeAxis.draw(g2, cursor, area, drawArea, RectangleEdge.LEFT, null);
/*      */             break;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       } 
/* 1298 */       g2.setFont(this.valueFont);
/* 1299 */       g2.setPaint(this.valuePaint);
/* 1300 */       FontMetrics fontMetrics = g2.getFontMetrics();
/* 1301 */       switch (this.valueLocation) {
/*      */         case 1:
/* 1303 */           g2.drawString(this.valueFormat.format(current), midX + 
/* 1304 */               getColumnRadius() + getGap(), midY);
/*      */           break;
/*      */         case 2:
/* 1307 */           valueString = this.valueFormat.format(current);
/* 1308 */           stringWidth = fontMetrics.stringWidth(valueString);
/* 1309 */           g2.drawString(valueString, midX - getColumnRadius() - 
/* 1310 */               getGap() - stringWidth, midY);
/*      */           break;
/*      */         case 3:
/* 1313 */           temp = this.valueFormat.format(current);
/* 1314 */           i = fontMetrics.stringWidth(temp) / 2;
/* 1315 */           g2.drawString(temp, midX - i, stemBottom + 
/* 1316 */               getBulbRadius() + getGap());
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/* 1323 */     g2.setPaint(this.thermometerPaint);
/* 1324 */     g2.setFont(this.valueFont);
/*      */ 
/*      */     
/* 1327 */     FontMetrics metrics = g2.getFontMetrics();
/*      */     
/* 1329 */     int tickX1 = midX - getColumnRadius() - getGap() * 2 - metrics.stringWidth(UNITS[this.units]);
/* 1330 */     if (tickX1 > area.getMinX()) {
/* 1331 */       g2.drawString(UNITS[this.units], tickX1, 
/* 1332 */           (int)(area.getMinY() + 20.0D));
/*      */     }
/*      */ 
/*      */     
/* 1336 */     g2.setStroke(this.thermometerStroke);
/* 1337 */     g2.draw(outerThermometer);
/* 1338 */     g2.draw(innerThermometer);
/*      */     
/* 1340 */     drawOutline(g2, area);
/*      */   }
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
/*      */   public String getPlotType() {
/* 1362 */     return localizationResources.getString("Thermometer_Plot");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void datasetChanged(DatasetChangeEvent event) {
/* 1372 */     if (this.dataset != null) {
/* 1373 */       Number vn = this.dataset.getValue();
/* 1374 */       if (vn != null) {
/* 1375 */         double value = vn.doubleValue();
/* 1376 */         if (inSubrange(0, value)) {
/* 1377 */           this.subrange = 0;
/*      */         }
/* 1379 */         else if (inSubrange(1, value)) {
/* 1380 */           this.subrange = 1;
/*      */         }
/* 1382 */         else if (inSubrange(2, value)) {
/* 1383 */           this.subrange = 2;
/*      */         } else {
/*      */           
/* 1386 */           this.subrange = -1;
/*      */         } 
/* 1388 */         setAxisRange();
/*      */       } 
/*      */     } 
/* 1391 */     super.datasetChanged(event);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number getMinimumVerticalDataValue() {
/* 1405 */     return new Double(this.lowerBound);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number getMaximumVerticalDataValue() {
/* 1419 */     return new Double(this.upperBound);
/*      */   }
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
/* 1431 */     return new Range(this.lowerBound, this.upperBound);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setAxisRange() {
/* 1438 */     if (this.subrange >= 0 && this.followDataInSubranges) {
/* 1439 */       this.rangeAxis.setRange(new Range(this.subrangeInfo[this.subrange][2], this.subrangeInfo[this.subrange][3]));
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1444 */       this.rangeAxis.setRange(this.lowerBound, this.upperBound);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LegendItemCollection getLegendItems() {
/* 1455 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlotOrientation getOrientation() {
/* 1465 */     return PlotOrientation.VERTICAL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static boolean isValidNumber(double d) {
/* 1477 */     return (!Double.isNaN(d) && !Double.isInfinite(d));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean inSubrange(int subrange, double value) {
/* 1489 */     return (value > this.subrangeInfo[subrange][0] && value <= this.subrangeInfo[subrange][1]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Paint getCurrentPaint() {
/* 1501 */     Paint result = this.mercuryPaint;
/* 1502 */     if (this.useSubrangePaint) {
/* 1503 */       double value = this.dataset.getValue().doubleValue();
/* 1504 */       if (inSubrange(0, value)) {
/* 1505 */         result = this.subrangePaint[0];
/*      */       }
/* 1507 */       else if (inSubrange(1, value)) {
/* 1508 */         result = this.subrangePaint[1];
/*      */       }
/* 1510 */       else if (inSubrange(2, value)) {
/* 1511 */         result = this.subrangePaint[2];
/*      */       } 
/*      */     } 
/* 1514 */     return result;
/*      */   }
/*      */ 
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
/* 1527 */     if (obj == this) {
/* 1528 */       return true;
/*      */     }
/* 1530 */     if (!(obj instanceof ThermometerPlot)) {
/* 1531 */       return false;
/*      */     }
/* 1533 */     ThermometerPlot that = (ThermometerPlot)obj;
/* 1534 */     if (!super.equals(obj)) {
/* 1535 */       return false;
/*      */     }
/* 1537 */     if (!ObjectUtilities.equal(this.rangeAxis, that.rangeAxis)) {
/* 1538 */       return false;
/*      */     }
/* 1540 */     if (this.axisLocation != that.axisLocation) {
/* 1541 */       return false;
/*      */     }
/* 1543 */     if (this.lowerBound != that.lowerBound) {
/* 1544 */       return false;
/*      */     }
/* 1546 */     if (this.upperBound != that.upperBound) {
/* 1547 */       return false;
/*      */     }
/* 1549 */     if (!ObjectUtilities.equal(this.padding, that.padding)) {
/* 1550 */       return false;
/*      */     }
/* 1552 */     if (!ObjectUtilities.equal(this.thermometerStroke, that.thermometerStroke))
/*      */     {
/* 1554 */       return false;
/*      */     }
/* 1556 */     if (!PaintUtilities.equal(this.thermometerPaint, that.thermometerPaint))
/*      */     {
/* 1558 */       return false;
/*      */     }
/* 1560 */     if (this.units != that.units) {
/* 1561 */       return false;
/*      */     }
/* 1563 */     if (this.valueLocation != that.valueLocation) {
/* 1564 */       return false;
/*      */     }
/* 1566 */     if (!ObjectUtilities.equal(this.valueFont, that.valueFont)) {
/* 1567 */       return false;
/*      */     }
/* 1569 */     if (!PaintUtilities.equal(this.valuePaint, that.valuePaint)) {
/* 1570 */       return false;
/*      */     }
/* 1572 */     if (!ObjectUtilities.equal(this.valueFormat, that.valueFormat)) {
/* 1573 */       return false;
/*      */     }
/* 1575 */     if (!PaintUtilities.equal(this.mercuryPaint, that.mercuryPaint)) {
/* 1576 */       return false;
/*      */     }
/* 1578 */     if (this.showValueLines != that.showValueLines) {
/* 1579 */       return false;
/*      */     }
/* 1581 */     if (this.subrange != that.subrange) {
/* 1582 */       return false;
/*      */     }
/* 1584 */     if (this.followDataInSubranges != that.followDataInSubranges) {
/* 1585 */       return false;
/*      */     }
/* 1587 */     if (!equal(this.subrangeInfo, that.subrangeInfo)) {
/* 1588 */       return false;
/*      */     }
/* 1590 */     if (this.useSubrangePaint != that.useSubrangePaint) {
/* 1591 */       return false;
/*      */     }
/* 1593 */     if (this.bulbRadius != that.bulbRadius) {
/* 1594 */       return false;
/*      */     }
/* 1596 */     if (this.columnRadius != that.columnRadius) {
/* 1597 */       return false;
/*      */     }
/* 1599 */     if (this.gap != that.gap) {
/* 1600 */       return false;
/*      */     }
/* 1602 */     for (int i = 0; i < this.subrangePaint.length; i++) {
/* 1603 */       if (!PaintUtilities.equal(this.subrangePaint[i], that.subrangePaint[i]))
/*      */       {
/* 1605 */         return false;
/*      */       }
/*      */     } 
/* 1608 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean equal(double[][] array1, double[][] array2) {
/* 1620 */     if (array1 == null) {
/* 1621 */       return (array2 == null);
/*      */     }
/* 1623 */     if (array2 == null) {
/* 1624 */       return false;
/*      */     }
/* 1626 */     if (array1.length != array2.length) {
/* 1627 */       return false;
/*      */     }
/* 1629 */     for (int i = 0; i < array1.length; i++) {
/* 1630 */       if (!Arrays.equals(array1[i], array2[i])) {
/* 1631 */         return false;
/*      */       }
/*      */     } 
/* 1634 */     return true;
/*      */   }
/*      */ 
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
/* 1647 */     ThermometerPlot clone = (ThermometerPlot)super.clone();
/*      */     
/* 1649 */     if (clone.dataset != null) {
/* 1650 */       clone.dataset.addChangeListener(clone);
/*      */     }
/* 1652 */     clone.rangeAxis = (ValueAxis)ObjectUtilities.clone(this.rangeAxis);
/* 1653 */     if (clone.rangeAxis != null) {
/* 1654 */       clone.rangeAxis.setPlot(clone);
/* 1655 */       clone.rangeAxis.addChangeListener(clone);
/*      */     } 
/* 1657 */     clone.valueFormat = (NumberFormat)this.valueFormat.clone();
/* 1658 */     clone.subrangePaint = (Paint[])this.subrangePaint.clone();
/*      */     
/* 1660 */     return clone;
/*      */   }
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
/* 1672 */     stream.defaultWriteObject();
/* 1673 */     SerialUtilities.writeStroke(this.thermometerStroke, stream);
/* 1674 */     SerialUtilities.writePaint(this.thermometerPaint, stream);
/* 1675 */     SerialUtilities.writePaint(this.valuePaint, stream);
/* 1676 */     SerialUtilities.writePaint(this.mercuryPaint, stream);
/* 1677 */     SerialUtilities.writeStroke(this.subrangeIndicatorStroke, stream);
/* 1678 */     SerialUtilities.writeStroke(this.rangeIndicatorStroke, stream);
/* 1679 */     for (int i = 0; i < 3; i++) {
/* 1680 */       SerialUtilities.writePaint(this.subrangePaint[i], stream);
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
/*      */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 1694 */     stream.defaultReadObject();
/* 1695 */     this.thermometerStroke = SerialUtilities.readStroke(stream);
/* 1696 */     this.thermometerPaint = SerialUtilities.readPaint(stream);
/* 1697 */     this.valuePaint = SerialUtilities.readPaint(stream);
/* 1698 */     this.mercuryPaint = SerialUtilities.readPaint(stream);
/* 1699 */     this.subrangeIndicatorStroke = SerialUtilities.readStroke(stream);
/* 1700 */     this.rangeIndicatorStroke = SerialUtilities.readStroke(stream);
/* 1701 */     this.subrangePaint = new Paint[3];
/* 1702 */     for (int i = 0; i < 3; i++) {
/* 1703 */       this.subrangePaint[i] = SerialUtilities.readPaint(stream);
/*      */     }
/* 1705 */     if (this.rangeAxis != null) {
/* 1706 */       this.rangeAxis.addChangeListener(this);
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
/*      */   public void zoomRangeAxes(double factor, PlotRenderingInfo state, Point2D source) {
/* 1750 */     this.rangeAxis.resizeRange(factor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomRangeAxes(double factor, PlotRenderingInfo state, Point2D source, boolean useAnchor) {
/* 1767 */     double anchorY = getRangeAxis().java2DToValue(source.getY(), state
/* 1768 */         .getDataArea(), RectangleEdge.LEFT);
/* 1769 */     this.rangeAxis.resizeRange(factor, anchorY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public void zoomRangeAxes(double lowerPercent, double upperPercent, PlotRenderingInfo state, Point2D source) {
/* 1797 */     this.rangeAxis.zoomRange(lowerPercent, upperPercent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDomainZoomable() {
/* 1807 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRangeZoomable() {
/* 1817 */     return true;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/ThermometerPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */