/*      */ package org.jfree.chart.axis;
/*      */ 
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.font.TextLayout;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.AttributedString;
/*      */ import java.util.Arrays;
/*      */ import java.util.EventListener;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import org.jfree.chart.entity.AxisEntity;
/*      */ import org.jfree.chart.entity.ChartEntity;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.event.AxisChangeEvent;
/*      */ import org.jfree.chart.event.AxisChangeListener;
/*      */ import org.jfree.chart.plot.Plot;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.util.AttrStringUtils;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ import org.jfree.ui.TextAnchor;
/*      */ import org.jfree.util.AttributedStringUtilities;
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
/*      */ public abstract class Axis
/*      */   implements Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 7719289504573298271L;
/*      */   public static final boolean DEFAULT_AXIS_VISIBLE = true;
/*  149 */   public static final Font DEFAULT_AXIS_LABEL_FONT = new Font("SansSerif", 0, 12);
/*      */ 
/*      */ 
/*      */   
/*  153 */   public static final Paint DEFAULT_AXIS_LABEL_PAINT = Color.black;
/*      */ 
/*      */   
/*  156 */   public static final RectangleInsets DEFAULT_AXIS_LABEL_INSETS = new RectangleInsets(3.0D, 3.0D, 3.0D, 3.0D);
/*      */ 
/*      */ 
/*      */   
/*  160 */   public static final Paint DEFAULT_AXIS_LINE_PAINT = Color.gray;
/*      */ 
/*      */   
/*  163 */   public static final Stroke DEFAULT_AXIS_LINE_STROKE = new BasicStroke(0.5F);
/*      */ 
/*      */   
/*      */   public static final boolean DEFAULT_TICK_LABELS_VISIBLE = true;
/*      */ 
/*      */   
/*  169 */   public static final Font DEFAULT_TICK_LABEL_FONT = new Font("SansSerif", 0, 10);
/*      */ 
/*      */ 
/*      */   
/*  173 */   public static final Paint DEFAULT_TICK_LABEL_PAINT = Color.black;
/*      */ 
/*      */   
/*  176 */   public static final RectangleInsets DEFAULT_TICK_LABEL_INSETS = new RectangleInsets(2.0D, 4.0D, 2.0D, 4.0D);
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean DEFAULT_TICK_MARKS_VISIBLE = true;
/*      */ 
/*      */   
/*  183 */   public static final Stroke DEFAULT_TICK_MARK_STROKE = new BasicStroke(0.5F);
/*      */ 
/*      */   
/*  186 */   public static final Paint DEFAULT_TICK_MARK_PAINT = Color.gray;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final float DEFAULT_TICK_MARK_INSIDE_LENGTH = 0.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final float DEFAULT_TICK_MARK_OUTSIDE_LENGTH = 2.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean visible;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String label;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient AttributedString attributedLabel;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Font labelFont;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint labelPaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private RectangleInsets labelInsets;
/*      */ 
/*      */ 
/*      */   
/*      */   private double labelAngle;
/*      */ 
/*      */ 
/*      */   
/*      */   private AxisLabelLocation labelLocation;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean axisLineVisible;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke axisLineStroke;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint axisLinePaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean tickLabelsVisible;
/*      */ 
/*      */ 
/*      */   
/*      */   private Font tickLabelFont;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint tickLabelPaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private RectangleInsets tickLabelInsets;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean tickMarksVisible;
/*      */ 
/*      */ 
/*      */   
/*      */   private float tickMarkInsideLength;
/*      */ 
/*      */ 
/*      */   
/*      */   private float tickMarkOutsideLength;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean minorTickMarksVisible;
/*      */ 
/*      */ 
/*      */   
/*      */   private float minorTickMarkInsideLength;
/*      */ 
/*      */ 
/*      */   
/*      */   private float minorTickMarkOutsideLength;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Stroke tickMarkStroke;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint tickMarkPaint;
/*      */ 
/*      */ 
/*      */   
/*      */   private double fixedDimension;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Plot plot;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient EventListenerList listenerList;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Axis(String label) {
/*  310 */     this.label = label;
/*  311 */     this.visible = true;
/*  312 */     this.labelFont = DEFAULT_AXIS_LABEL_FONT;
/*  313 */     this.labelPaint = DEFAULT_AXIS_LABEL_PAINT;
/*  314 */     this.labelInsets = DEFAULT_AXIS_LABEL_INSETS;
/*  315 */     this.labelAngle = 0.0D;
/*  316 */     this.labelLocation = AxisLabelLocation.MIDDLE;
/*      */     
/*  318 */     this.axisLineVisible = true;
/*  319 */     this.axisLinePaint = DEFAULT_AXIS_LINE_PAINT;
/*  320 */     this.axisLineStroke = DEFAULT_AXIS_LINE_STROKE;
/*      */     
/*  322 */     this.tickLabelsVisible = true;
/*  323 */     this.tickLabelFont = DEFAULT_TICK_LABEL_FONT;
/*  324 */     this.tickLabelPaint = DEFAULT_TICK_LABEL_PAINT;
/*  325 */     this.tickLabelInsets = DEFAULT_TICK_LABEL_INSETS;
/*      */     
/*  327 */     this.tickMarksVisible = true;
/*  328 */     this.tickMarkStroke = DEFAULT_TICK_MARK_STROKE;
/*  329 */     this.tickMarkPaint = DEFAULT_TICK_MARK_PAINT;
/*  330 */     this.tickMarkInsideLength = 0.0F;
/*  331 */     this.tickMarkOutsideLength = 2.0F;
/*      */     
/*  333 */     this.minorTickMarksVisible = false;
/*  334 */     this.minorTickMarkInsideLength = 0.0F;
/*  335 */     this.minorTickMarkOutsideLength = 2.0F;
/*      */     
/*  337 */     this.plot = null;
/*      */     
/*  339 */     this.listenerList = new EventListenerList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isVisible() {
/*  351 */     return this.visible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVisible(boolean flag) {
/*  363 */     if (flag != this.visible) {
/*  364 */       this.visible = flag;
/*  365 */       fireChangeEvent();
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
/*      */   public String getLabel() {
/*  379 */     return this.label;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabel(String label) {
/*  393 */     this.label = label;
/*  394 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributedString getAttributedLabel() {
/*  407 */     if (this.attributedLabel != null) {
/*  408 */       return new AttributedString(this.attributedLabel.getIterator());
/*      */     }
/*  410 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttributedLabel(String label) {
/*  425 */     setAttributedLabel(createAttributedLabel(label));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttributedLabel(AttributedString label) {
/*  437 */     if (label != null) {
/*  438 */       this.attributedLabel = new AttributedString(label.getIterator());
/*      */     } else {
/*  440 */       this.attributedLabel = null;
/*      */     } 
/*  442 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributedString createAttributedLabel(String label) {
/*  456 */     if (label == null) {
/*  457 */       return null;
/*      */     }
/*  459 */     AttributedString s = new AttributedString(label);
/*  460 */     s.addAttributes((Map)this.labelFont.getAttributes(), 0, label.length());
/*  461 */     return s;
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
/*  472 */     return this.labelFont;
/*      */   }
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
/*  484 */     ParamChecks.nullNotPermitted(font, "font");
/*  485 */     if (!this.labelFont.equals(font)) {
/*  486 */       this.labelFont = font;
/*  487 */       fireChangeEvent();
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
/*      */   public Paint getLabelPaint() {
/*  499 */     return this.labelPaint;
/*      */   }
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
/*  511 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  512 */     this.labelPaint = paint;
/*  513 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleInsets getLabelInsets() {
/*  525 */     return this.labelInsets;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelInsets(RectangleInsets insets) {
/*  537 */     setLabelInsets(insets, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelInsets(RectangleInsets insets, boolean notify) {
/*  550 */     ParamChecks.nullNotPermitted(insets, "insets");
/*  551 */     if (!insets.equals(this.labelInsets)) {
/*  552 */       this.labelInsets = insets;
/*  553 */       if (notify) {
/*  554 */         fireChangeEvent();
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
/*      */   public double getLabelAngle() {
/*  567 */     return this.labelAngle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelAngle(double angle) {
/*  579 */     this.labelAngle = angle;
/*  580 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisLabelLocation getLabelLocation() {
/*  592 */     return this.labelLocation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelLocation(AxisLabelLocation location) {
/*  604 */     ParamChecks.nullNotPermitted(location, "location");
/*  605 */     this.labelLocation = location;
/*  606 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAxisLineVisible() {
/*  619 */     return this.axisLineVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxisLineVisible(boolean visible) {
/*  633 */     this.axisLineVisible = visible;
/*  634 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getAxisLinePaint() {
/*  645 */     return this.axisLinePaint;
/*      */   }
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
/*  657 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  658 */     this.axisLinePaint = paint;
/*  659 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getAxisLineStroke() {
/*  670 */     return this.axisLineStroke;
/*      */   }
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
/*  682 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/*  683 */     this.axisLineStroke = stroke;
/*  684 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTickLabelsVisible() {
/*  697 */     return this.tickLabelsVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickLabelsVisible(boolean flag) {
/*  713 */     if (flag != this.tickLabelsVisible) {
/*  714 */       this.tickLabelsVisible = flag;
/*  715 */       fireChangeEvent();
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
/*      */   public boolean isMinorTickMarksVisible() {
/*  732 */     return this.minorTickMarksVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinorTickMarksVisible(boolean flag) {
/*  747 */     if (flag != this.minorTickMarksVisible) {
/*  748 */       this.minorTickMarksVisible = flag;
/*  749 */       fireChangeEvent();
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
/*      */   public Font getTickLabelFont() {
/*  761 */     return this.tickLabelFont;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickLabelFont(Font font) {
/*  773 */     ParamChecks.nullNotPermitted(font, "font");
/*  774 */     if (!this.tickLabelFont.equals(font)) {
/*  775 */       this.tickLabelFont = font;
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
/*      */   public Paint getTickLabelPaint() {
/*  788 */     return this.tickLabelPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickLabelPaint(Paint paint) {
/*  800 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  801 */     this.tickLabelPaint = paint;
/*  802 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleInsets getTickLabelInsets() {
/*  813 */     return this.tickLabelInsets;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickLabelInsets(RectangleInsets insets) {
/*  825 */     ParamChecks.nullNotPermitted(insets, "insets");
/*  826 */     if (!this.tickLabelInsets.equals(insets)) {
/*  827 */       this.tickLabelInsets = insets;
/*  828 */       fireChangeEvent();
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
/*      */   public boolean isTickMarksVisible() {
/*  842 */     return this.tickMarksVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickMarksVisible(boolean flag) {
/*  854 */     if (flag != this.tickMarksVisible) {
/*  855 */       this.tickMarksVisible = flag;
/*  856 */       fireChangeEvent();
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
/*      */   public float getTickMarkInsideLength() {
/*  869 */     return this.tickMarkInsideLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickMarkInsideLength(float length) {
/*  881 */     this.tickMarkInsideLength = length;
/*  882 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getTickMarkOutsideLength() {
/*  894 */     return this.tickMarkOutsideLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickMarkOutsideLength(float length) {
/*  906 */     this.tickMarkOutsideLength = length;
/*  907 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getTickMarkStroke() {
/*  918 */     return this.tickMarkStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickMarkStroke(Stroke stroke) {
/*  930 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/*  931 */     if (!this.tickMarkStroke.equals(stroke)) {
/*  932 */       this.tickMarkStroke = stroke;
/*  933 */       fireChangeEvent();
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
/*      */   public Paint getTickMarkPaint() {
/*  945 */     return this.tickMarkPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickMarkPaint(Paint paint) {
/*  957 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  958 */     this.tickMarkPaint = paint;
/*  959 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getMinorTickMarkInsideLength() {
/*  973 */     return this.minorTickMarkInsideLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinorTickMarkInsideLength(float length) {
/*  987 */     this.minorTickMarkInsideLength = length;
/*  988 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getMinorTickMarkOutsideLength() {
/* 1002 */     return this.minorTickMarkOutsideLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinorTickMarkOutsideLength(float length) {
/* 1016 */     this.minorTickMarkOutsideLength = length;
/* 1017 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Plot getPlot() {
/* 1029 */     return this.plot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlot(Plot plot) {
/* 1042 */     this.plot = plot;
/* 1043 */     configure();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getFixedDimension() {
/* 1054 */     return this.fixedDimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFixedDimension(double dimension) {
/* 1071 */     this.fixedDimension = dimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void configure();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract AxisSpace reserveSpace(Graphics2D paramGraphics2D, Plot paramPlot, Rectangle2D paramRectangle2D, RectangleEdge paramRectangleEdge, AxisSpace paramAxisSpace);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract AxisState draw(Graphics2D paramGraphics2D, double paramDouble, Rectangle2D paramRectangle2D1, Rectangle2D paramRectangle2D2, RectangleEdge paramRectangleEdge, PlotRenderingInfo paramPlotRenderingInfo);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract List refreshTicks(Graphics2D paramGraphics2D, AxisState paramAxisState, Rectangle2D paramRectangle2D, RectangleEdge paramRectangleEdge);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void createAndAddEntity(double cursor, AxisState state, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState) {
/* 1147 */     if (plotState == null || plotState.getOwner() == null) {
/*      */       return;
/*      */     }
/* 1150 */     Rectangle2D hotspot = null;
/* 1151 */     if (edge.equals(RectangleEdge.TOP)) {
/*      */ 
/*      */       
/* 1154 */       hotspot = new Rectangle2D.Double(dataArea.getX(), state.getCursor(), dataArea.getWidth(), cursor - state.getCursor());
/*      */     }
/* 1156 */     else if (edge.equals(RectangleEdge.BOTTOM)) {
/*      */       
/* 1158 */       hotspot = new Rectangle2D.Double(dataArea.getX(), cursor, dataArea.getWidth(), state.getCursor() - cursor);
/*      */     }
/* 1160 */     else if (edge.equals(RectangleEdge.LEFT)) {
/*      */ 
/*      */       
/* 1163 */       hotspot = new Rectangle2D.Double(state.getCursor(), dataArea.getY(), cursor - state.getCursor(), dataArea.getHeight());
/*      */     }
/* 1165 */     else if (edge.equals(RectangleEdge.RIGHT)) {
/*      */       
/* 1167 */       hotspot = new Rectangle2D.Double(cursor, dataArea.getY(), state.getCursor() - cursor, dataArea.getHeight());
/*      */     } 
/* 1169 */     EntityCollection e = plotState.getOwner().getEntityCollection();
/* 1170 */     if (e != null) {
/* 1171 */       e.add((ChartEntity)new AxisEntity(hotspot, this));
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
/*      */   public void addChangeListener(AxisChangeListener listener) {
/* 1183 */     this.listenerList.add(AxisChangeListener.class, listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeChangeListener(AxisChangeListener listener) {
/* 1194 */     this.listenerList.remove(AxisChangeListener.class, listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasListener(EventListener listener) {
/* 1207 */     List list = Arrays.asList(this.listenerList.getListenerList());
/* 1208 */     return list.contains(listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void notifyListeners(AxisChangeEvent event) {
/* 1218 */     Object[] listeners = this.listenerList.getListenerList();
/* 1219 */     for (int i = listeners.length - 2; i >= 0; i -= 2) {
/* 1220 */       if (listeners[i] == AxisChangeListener.class) {
/* 1221 */         ((AxisChangeListener)listeners[i + 1]).axisChanged(event);
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
/* 1232 */     notifyListeners(new AxisChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle2D getLabelEnclosure(Graphics2D g2, RectangleEdge edge) {
/* 1245 */     Rectangle2D result = new Rectangle2D.Double();
/* 1246 */     Rectangle2D bounds = null;
/* 1247 */     if (this.attributedLabel != null) {
/*      */ 
/*      */       
/* 1250 */       TextLayout layout = new TextLayout(this.attributedLabel.getIterator(), g2.getFontRenderContext());
/* 1251 */       bounds = layout.getBounds();
/*      */     } else {
/* 1253 */       String axisLabel = getLabel();
/* 1254 */       if (axisLabel != null && !axisLabel.equals("")) {
/* 1255 */         FontMetrics fm = g2.getFontMetrics(getLabelFont());
/* 1256 */         bounds = TextUtilities.getTextBounds(axisLabel, g2, fm);
/*      */       } 
/*      */     } 
/* 1259 */     if (bounds != null) {
/* 1260 */       RectangleInsets insets = getLabelInsets();
/* 1261 */       bounds = insets.createOutsetRectangle(bounds);
/* 1262 */       double angle = getLabelAngle();
/* 1263 */       if (edge == RectangleEdge.LEFT || edge == RectangleEdge.RIGHT) {
/* 1264 */         angle -= 1.5707963267948966D;
/*      */       }
/* 1266 */       double x = bounds.getCenterX();
/* 1267 */       double y = bounds.getCenterY();
/*      */       
/* 1269 */       AffineTransform transformer = AffineTransform.getRotateInstance(angle, x, y);
/* 1270 */       Shape labelBounds = transformer.createTransformedShape(bounds);
/* 1271 */       result = labelBounds.getBounds2D();
/*      */     } 
/* 1273 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   protected double labelLocationX(AxisLabelLocation location, Rectangle2D dataArea) {
/* 1278 */     if (location.equals(AxisLabelLocation.HIGH_END)) {
/* 1279 */       return dataArea.getMaxX();
/*      */     }
/* 1281 */     if (location.equals(AxisLabelLocation.MIDDLE)) {
/* 1282 */       return dataArea.getCenterX();
/*      */     }
/* 1284 */     if (location.equals(AxisLabelLocation.LOW_END)) {
/* 1285 */       return dataArea.getMinX();
/*      */     }
/* 1287 */     throw new RuntimeException("Unexpected AxisLabelLocation: " + location);
/*      */   }
/*      */ 
/*      */   
/*      */   protected double labelLocationY(AxisLabelLocation location, Rectangle2D dataArea) {
/* 1292 */     if (location.equals(AxisLabelLocation.HIGH_END)) {
/* 1293 */       return dataArea.getMinY();
/*      */     }
/* 1295 */     if (location.equals(AxisLabelLocation.MIDDLE)) {
/* 1296 */       return dataArea.getCenterY();
/*      */     }
/* 1298 */     if (location.equals(AxisLabelLocation.LOW_END)) {
/* 1299 */       return dataArea.getMaxY();
/*      */     }
/* 1301 */     throw new RuntimeException("Unexpected AxisLabelLocation: " + location);
/*      */   }
/*      */   
/*      */   protected TextAnchor labelAnchorH(AxisLabelLocation location) {
/* 1305 */     if (location.equals(AxisLabelLocation.HIGH_END)) {
/* 1306 */       return TextAnchor.CENTER_RIGHT;
/*      */     }
/* 1308 */     if (location.equals(AxisLabelLocation.MIDDLE)) {
/* 1309 */       return TextAnchor.CENTER;
/*      */     }
/* 1311 */     if (location.equals(AxisLabelLocation.LOW_END)) {
/* 1312 */       return TextAnchor.CENTER_LEFT;
/*      */     }
/* 1314 */     throw new RuntimeException("Unexpected AxisLabelLocation: " + location);
/*      */   }
/*      */   
/*      */   protected TextAnchor labelAnchorV(AxisLabelLocation location) {
/* 1318 */     if (location.equals(AxisLabelLocation.HIGH_END)) {
/* 1319 */       return TextAnchor.CENTER_RIGHT;
/*      */     }
/* 1321 */     if (location.equals(AxisLabelLocation.MIDDLE)) {
/* 1322 */       return TextAnchor.CENTER;
/*      */     }
/* 1324 */     if (location.equals(AxisLabelLocation.LOW_END)) {
/* 1325 */       return TextAnchor.CENTER_LEFT;
/*      */     }
/* 1327 */     throw new RuntimeException("Unexpected AxisLabelLocation: " + location);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AxisState drawLabel(String label, Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, AxisState state) {
/* 1347 */     ParamChecks.nullNotPermitted(state, "state");
/*      */     
/* 1349 */     if (label == null || label.equals("")) {
/* 1350 */       return state;
/*      */     }
/*      */     
/* 1353 */     Font font = getLabelFont();
/* 1354 */     RectangleInsets insets = getLabelInsets();
/* 1355 */     g2.setFont(font);
/* 1356 */     g2.setPaint(getLabelPaint());
/* 1357 */     FontMetrics fm = g2.getFontMetrics();
/* 1358 */     Rectangle2D labelBounds = TextUtilities.getTextBounds(label, g2, fm);
/*      */     
/* 1360 */     if (edge == RectangleEdge.TOP) {
/* 1361 */       AffineTransform t = AffineTransform.getRotateInstance(
/* 1362 */           getLabelAngle(), labelBounds.getCenterX(), labelBounds
/* 1363 */           .getCenterY());
/* 1364 */       Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
/* 1365 */       labelBounds = rotatedLabelBounds.getBounds2D();
/* 1366 */       double labelx = labelLocationX(this.labelLocation, dataArea);
/*      */       
/* 1368 */       double labely = state.getCursor() - insets.getBottom() - labelBounds.getHeight() / 2.0D;
/* 1369 */       TextAnchor anchor = labelAnchorH(this.labelLocation);
/* 1370 */       TextUtilities.drawRotatedString(label, g2, (float)labelx, (float)labely, anchor, 
/* 1371 */           getLabelAngle(), TextAnchor.CENTER);
/* 1372 */       state.cursorUp(insets.getTop() + labelBounds.getHeight() + insets
/* 1373 */           .getBottom());
/*      */     }
/* 1375 */     else if (edge == RectangleEdge.BOTTOM) {
/* 1376 */       AffineTransform t = AffineTransform.getRotateInstance(
/* 1377 */           getLabelAngle(), labelBounds.getCenterX(), labelBounds
/* 1378 */           .getCenterY());
/* 1379 */       Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
/* 1380 */       labelBounds = rotatedLabelBounds.getBounds2D();
/* 1381 */       double labelx = labelLocationX(this.labelLocation, dataArea);
/*      */       
/* 1383 */       double labely = state.getCursor() + insets.getTop() + labelBounds.getHeight() / 2.0D;
/* 1384 */       TextAnchor anchor = labelAnchorH(this.labelLocation);
/* 1385 */       TextUtilities.drawRotatedString(label, g2, (float)labelx, (float)labely, anchor, 
/* 1386 */           getLabelAngle(), TextAnchor.CENTER);
/* 1387 */       state.cursorDown(insets.getTop() + labelBounds.getHeight() + insets
/* 1388 */           .getBottom());
/*      */     }
/* 1390 */     else if (edge == RectangleEdge.LEFT) {
/* 1391 */       AffineTransform t = AffineTransform.getRotateInstance(
/* 1392 */           getLabelAngle() - 1.5707963267948966D, labelBounds.getCenterX(), labelBounds
/* 1393 */           .getCenterY());
/* 1394 */       Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
/* 1395 */       labelBounds = rotatedLabelBounds.getBounds2D();
/*      */       
/* 1397 */       double labelx = state.getCursor() - insets.getRight() - labelBounds.getWidth() / 2.0D;
/* 1398 */       double labely = labelLocationY(this.labelLocation, dataArea);
/* 1399 */       TextAnchor anchor = labelAnchorV(this.labelLocation);
/* 1400 */       TextUtilities.drawRotatedString(label, g2, (float)labelx, (float)labely, anchor, 
/* 1401 */           getLabelAngle() - 1.5707963267948966D, anchor);
/*      */       
/* 1403 */       state.cursorLeft(insets.getLeft() + labelBounds.getWidth() + insets
/* 1404 */           .getRight());
/*      */     }
/* 1406 */     else if (edge == RectangleEdge.RIGHT) {
/* 1407 */       AffineTransform t = AffineTransform.getRotateInstance(
/* 1408 */           getLabelAngle() + 1.5707963267948966D, labelBounds
/* 1409 */           .getCenterX(), labelBounds.getCenterY());
/* 1410 */       Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
/* 1411 */       labelBounds = rotatedLabelBounds.getBounds2D();
/*      */       
/* 1413 */       double labelx = state.getCursor() + insets.getLeft() + labelBounds.getWidth() / 2.0D;
/* 1414 */       double labely = labelLocationY(this.labelLocation, dataArea);
/* 1415 */       TextAnchor anchor = labelAnchorV(this.labelLocation);
/* 1416 */       TextUtilities.drawRotatedString(label, g2, (float)labelx, (float)labely, anchor, 
/* 1417 */           getLabelAngle() + 1.5707963267948966D, anchor);
/*      */       
/* 1419 */       state.cursorRight(insets.getLeft() + labelBounds.getWidth() + insets
/* 1420 */           .getRight());
/*      */     } 
/*      */     
/* 1423 */     return state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AxisState drawAttributedLabel(AttributedString label, Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, AxisState state) {
/* 1446 */     ParamChecks.nullNotPermitted(state, "state");
/*      */     
/* 1448 */     if (label == null) {
/* 1449 */       return state;
/*      */     }
/*      */     
/* 1452 */     RectangleInsets insets = getLabelInsets();
/* 1453 */     g2.setFont(getLabelFont());
/* 1454 */     g2.setPaint(getLabelPaint());
/*      */     
/* 1456 */     TextLayout layout = new TextLayout(this.attributedLabel.getIterator(), g2.getFontRenderContext());
/* 1457 */     Rectangle2D labelBounds = layout.getBounds();
/*      */     
/* 1459 */     if (edge == RectangleEdge.TOP) {
/* 1460 */       AffineTransform t = AffineTransform.getRotateInstance(
/* 1461 */           getLabelAngle(), labelBounds.getCenterX(), labelBounds
/* 1462 */           .getCenterY());
/* 1463 */       Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
/* 1464 */       labelBounds = rotatedLabelBounds.getBounds2D();
/* 1465 */       double labelx = labelLocationX(this.labelLocation, dataArea);
/*      */       
/* 1467 */       double labely = state.getCursor() - insets.getBottom() - labelBounds.getHeight() / 2.0D;
/* 1468 */       TextAnchor anchor = labelAnchorH(this.labelLocation);
/* 1469 */       AttrStringUtils.drawRotatedString(label, g2, (float)labelx, (float)labely, anchor, 
/* 1470 */           getLabelAngle(), TextAnchor.CENTER);
/* 1471 */       state.cursorUp(insets.getTop() + labelBounds.getHeight() + insets
/* 1472 */           .getBottom());
/*      */     }
/* 1474 */     else if (edge == RectangleEdge.BOTTOM) {
/* 1475 */       AffineTransform t = AffineTransform.getRotateInstance(
/* 1476 */           getLabelAngle(), labelBounds.getCenterX(), labelBounds
/* 1477 */           .getCenterY());
/* 1478 */       Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
/* 1479 */       labelBounds = rotatedLabelBounds.getBounds2D();
/* 1480 */       double labelx = labelLocationX(this.labelLocation, dataArea);
/*      */       
/* 1482 */       double labely = state.getCursor() + insets.getTop() + labelBounds.getHeight() / 2.0D;
/* 1483 */       TextAnchor anchor = labelAnchorH(this.labelLocation);
/* 1484 */       AttrStringUtils.drawRotatedString(label, g2, (float)labelx, (float)labely, anchor, 
/* 1485 */           getLabelAngle(), TextAnchor.CENTER);
/* 1486 */       state.cursorDown(insets.getTop() + labelBounds.getHeight() + insets
/* 1487 */           .getBottom());
/*      */     }
/* 1489 */     else if (edge == RectangleEdge.LEFT) {
/* 1490 */       AffineTransform t = AffineTransform.getRotateInstance(
/* 1491 */           getLabelAngle() - 1.5707963267948966D, labelBounds.getCenterX(), labelBounds
/* 1492 */           .getCenterY());
/* 1493 */       Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
/* 1494 */       labelBounds = rotatedLabelBounds.getBounds2D();
/*      */       
/* 1496 */       double labelx = state.getCursor() - insets.getRight() - labelBounds.getWidth() / 2.0D;
/* 1497 */       double labely = labelLocationY(this.labelLocation, dataArea);
/* 1498 */       TextAnchor anchor = labelAnchorV(this.labelLocation);
/* 1499 */       AttrStringUtils.drawRotatedString(label, g2, (float)labelx, (float)labely, anchor, 
/* 1500 */           getLabelAngle() - 1.5707963267948966D, anchor);
/*      */       
/* 1502 */       state.cursorLeft(insets.getLeft() + labelBounds.getWidth() + insets
/* 1503 */           .getRight());
/*      */     }
/* 1505 */     else if (edge == RectangleEdge.RIGHT) {
/* 1506 */       AffineTransform t = AffineTransform.getRotateInstance(
/* 1507 */           getLabelAngle() + 1.5707963267948966D, labelBounds
/* 1508 */           .getCenterX(), labelBounds.getCenterY());
/* 1509 */       Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
/* 1510 */       labelBounds = rotatedLabelBounds.getBounds2D();
/*      */       
/* 1512 */       double labelx = state.getCursor() + insets.getLeft() + labelBounds.getWidth() / 2.0D;
/* 1513 */       double labely = labelLocationY(this.labelLocation, dataArea);
/* 1514 */       TextAnchor anchor = labelAnchorV(this.labelLocation);
/* 1515 */       AttrStringUtils.drawRotatedString(label, g2, (float)labelx, (float)labely, anchor, 
/* 1516 */           getLabelAngle() + 1.5707963267948966D, anchor);
/*      */       
/* 1518 */       state.cursorRight(insets.getLeft() + labelBounds.getWidth() + insets
/* 1519 */           .getRight());
/*      */     } 
/* 1521 */     return state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawAxisLine(Graphics2D g2, double cursor, Rectangle2D dataArea, RectangleEdge edge) {
/* 1534 */     Line2D axisLine = null;
/* 1535 */     double x = dataArea.getX();
/* 1536 */     double y = dataArea.getY();
/* 1537 */     if (edge == RectangleEdge.TOP) {
/* 1538 */       axisLine = new Line2D.Double(x, cursor, dataArea.getMaxX(), cursor);
/* 1539 */     } else if (edge == RectangleEdge.BOTTOM) {
/* 1540 */       axisLine = new Line2D.Double(x, cursor, dataArea.getMaxX(), cursor);
/* 1541 */     } else if (edge == RectangleEdge.LEFT) {
/* 1542 */       axisLine = new Line2D.Double(cursor, y, cursor, dataArea.getMaxY());
/* 1543 */     } else if (edge == RectangleEdge.RIGHT) {
/* 1544 */       axisLine = new Line2D.Double(cursor, y, cursor, dataArea.getMaxY());
/*      */     } 
/* 1546 */     g2.setPaint(this.axisLinePaint);
/* 1547 */     g2.setStroke(this.axisLineStroke);
/* 1548 */     Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
/* 1549 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/*      */     
/* 1551 */     g2.draw(axisLine);
/* 1552 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
/*      */   }
/*      */ 
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
/* 1565 */     Axis clone = (Axis)super.clone();
/*      */     
/* 1567 */     clone.plot = null;
/* 1568 */     clone.listenerList = new EventListenerList();
/* 1569 */     return clone;
/*      */   }
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
/* 1581 */     if (obj == this) {
/* 1582 */       return true;
/*      */     }
/* 1584 */     if (!(obj instanceof Axis)) {
/* 1585 */       return false;
/*      */     }
/* 1587 */     Axis that = (Axis)obj;
/* 1588 */     if (this.visible != that.visible) {
/* 1589 */       return false;
/*      */     }
/* 1591 */     if (!ObjectUtilities.equal(this.label, that.label)) {
/* 1592 */       return false;
/*      */     }
/* 1594 */     if (!AttributedStringUtilities.equal(this.attributedLabel, that.attributedLabel))
/*      */     {
/* 1596 */       return false;
/*      */     }
/* 1598 */     if (!ObjectUtilities.equal(this.labelFont, that.labelFont)) {
/* 1599 */       return false;
/*      */     }
/* 1601 */     if (!PaintUtilities.equal(this.labelPaint, that.labelPaint)) {
/* 1602 */       return false;
/*      */     }
/* 1604 */     if (!ObjectUtilities.equal(this.labelInsets, that.labelInsets)) {
/* 1605 */       return false;
/*      */     }
/* 1607 */     if (this.labelAngle != that.labelAngle) {
/* 1608 */       return false;
/*      */     }
/* 1610 */     if (!this.labelLocation.equals(that.labelLocation)) {
/* 1611 */       return false;
/*      */     }
/* 1613 */     if (this.axisLineVisible != that.axisLineVisible) {
/* 1614 */       return false;
/*      */     }
/* 1616 */     if (!ObjectUtilities.equal(this.axisLineStroke, that.axisLineStroke)) {
/* 1617 */       return false;
/*      */     }
/* 1619 */     if (!PaintUtilities.equal(this.axisLinePaint, that.axisLinePaint)) {
/* 1620 */       return false;
/*      */     }
/* 1622 */     if (this.tickLabelsVisible != that.tickLabelsVisible) {
/* 1623 */       return false;
/*      */     }
/* 1625 */     if (!ObjectUtilities.equal(this.tickLabelFont, that.tickLabelFont)) {
/* 1626 */       return false;
/*      */     }
/* 1628 */     if (!PaintUtilities.equal(this.tickLabelPaint, that.tickLabelPaint)) {
/* 1629 */       return false;
/*      */     }
/* 1631 */     if (!ObjectUtilities.equal(this.tickLabelInsets, that.tickLabelInsets))
/*      */     {
/*      */       
/* 1634 */       return false;
/*      */     }
/* 1636 */     if (this.tickMarksVisible != that.tickMarksVisible) {
/* 1637 */       return false;
/*      */     }
/* 1639 */     if (this.tickMarkInsideLength != that.tickMarkInsideLength) {
/* 1640 */       return false;
/*      */     }
/* 1642 */     if (this.tickMarkOutsideLength != that.tickMarkOutsideLength) {
/* 1643 */       return false;
/*      */     }
/* 1645 */     if (!PaintUtilities.equal(this.tickMarkPaint, that.tickMarkPaint)) {
/* 1646 */       return false;
/*      */     }
/* 1648 */     if (!ObjectUtilities.equal(this.tickMarkStroke, that.tickMarkStroke)) {
/* 1649 */       return false;
/*      */     }
/* 1651 */     if (this.minorTickMarksVisible != that.minorTickMarksVisible) {
/* 1652 */       return false;
/*      */     }
/* 1654 */     if (this.minorTickMarkInsideLength != that.minorTickMarkInsideLength) {
/* 1655 */       return false;
/*      */     }
/* 1657 */     if (this.minorTickMarkOutsideLength != that.minorTickMarkOutsideLength)
/*      */     {
/* 1659 */       return false;
/*      */     }
/* 1661 */     if (this.fixedDimension != that.fixedDimension) {
/* 1662 */       return false;
/*      */     }
/* 1664 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1674 */     int hash = 3;
/* 1675 */     if (this.label != null) {
/* 1676 */       hash = 83 * hash + this.label.hashCode();
/*      */     }
/* 1678 */     return hash;
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
/* 1689 */     stream.defaultWriteObject();
/* 1690 */     SerialUtilities.writeAttributedString(this.attributedLabel, stream);
/* 1691 */     SerialUtilities.writePaint(this.labelPaint, stream);
/* 1692 */     SerialUtilities.writePaint(this.tickLabelPaint, stream);
/* 1693 */     SerialUtilities.writeStroke(this.axisLineStroke, stream);
/* 1694 */     SerialUtilities.writePaint(this.axisLinePaint, stream);
/* 1695 */     SerialUtilities.writeStroke(this.tickMarkStroke, stream);
/* 1696 */     SerialUtilities.writePaint(this.tickMarkPaint, stream);
/*      */   }
/*      */ 
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
/* 1709 */     stream.defaultReadObject();
/* 1710 */     this.attributedLabel = SerialUtilities.readAttributedString(stream);
/* 1711 */     this.labelPaint = SerialUtilities.readPaint(stream);
/* 1712 */     this.tickLabelPaint = SerialUtilities.readPaint(stream);
/* 1713 */     this.axisLineStroke = SerialUtilities.readStroke(stream);
/* 1714 */     this.axisLinePaint = SerialUtilities.readPaint(stream);
/* 1715 */     this.tickMarkStroke = SerialUtilities.readStroke(stream);
/* 1716 */     this.tickMarkPaint = SerialUtilities.readPaint(stream);
/* 1717 */     this.listenerList = new EventListenerList();
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/Axis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */