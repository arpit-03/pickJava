/*      */ package org.jfree.chart.plot.dial;
/*      */ 
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Arc2D;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.NumberFormat;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.ui.TextAnchor;
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
/*      */ public class StandardDialScale
/*      */   extends AbstractDialLayer
/*      */   implements DialScale, Cloneable, PublicCloneable, Serializable
/*      */ {
/*      */   static final long serialVersionUID = 3715644629665918516L;
/*      */   private double lowerBound;
/*      */   private double upperBound;
/*      */   private double startAngle;
/*      */   private double extent;
/*      */   private double tickRadius;
/*      */   private double majorTickIncrement;
/*      */   private double majorTickLength;
/*      */   private transient Paint majorTickPaint;
/*      */   private transient Stroke majorTickStroke;
/*      */   private int minorTickCount;
/*      */   private double minorTickLength;
/*      */   private transient Paint minorTickPaint;
/*      */   private transient Stroke minorTickStroke;
/*      */   private double tickLabelOffset;
/*      */   private Font tickLabelFont;
/*      */   private boolean tickLabelsVisible;
/*      */   private NumberFormat tickLabelFormatter;
/*      */   private boolean firstTickLabelVisible;
/*      */   private transient Paint tickLabelPaint;
/*      */   
/*      */   public StandardDialScale() {
/*  189 */     this(0.0D, 100.0D, 175.0D, -170.0D, 10.0D, 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StandardDialScale(double lowerBound, double upperBound, double startAngle, double extent, double majorTickIncrement, int minorTickCount) {
/*  208 */     if (majorTickIncrement <= 0.0D) {
/*  209 */       throw new IllegalArgumentException("Requires 'majorTickIncrement' > 0.");
/*      */     }
/*      */     
/*  212 */     this.startAngle = startAngle;
/*  213 */     this.extent = extent;
/*  214 */     this.lowerBound = lowerBound;
/*  215 */     this.upperBound = upperBound;
/*  216 */     this.tickRadius = 0.7D;
/*  217 */     this.tickLabelsVisible = true;
/*  218 */     this.tickLabelFormatter = new DecimalFormat("0.0");
/*  219 */     this.firstTickLabelVisible = true;
/*  220 */     this.tickLabelFont = new Font("Dialog", 1, 16);
/*  221 */     this.tickLabelPaint = Color.blue;
/*  222 */     this.tickLabelOffset = 0.1D;
/*  223 */     this.majorTickIncrement = majorTickIncrement;
/*  224 */     this.majorTickLength = 0.04D;
/*  225 */     this.majorTickPaint = Color.black;
/*  226 */     this.majorTickStroke = new BasicStroke(3.0F);
/*  227 */     this.minorTickCount = minorTickCount;
/*  228 */     this.minorTickLength = 0.02D;
/*  229 */     this.minorTickPaint = Color.black;
/*  230 */     this.minorTickStroke = new BasicStroke(1.0F);
/*      */   }
/*      */ 
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
/*  243 */     return this.lowerBound;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLowerBound(double lower) {
/*  257 */     this.lowerBound = lower;
/*  258 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
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
/*  271 */     return this.upperBound;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUpperBound(double upper) {
/*  285 */     this.upperBound = upper;
/*  286 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
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
/*  298 */     return this.startAngle;
/*      */   }
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
/*  310 */     this.startAngle = angle;
/*  311 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getExtent() {
/*  322 */     return this.extent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExtent(double extent) {
/*  334 */     this.extent = extent;
/*  335 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getTickRadius() {
/*  347 */     return this.tickRadius;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickRadius(double radius) {
/*  359 */     if (radius <= 0.0D) {
/*  360 */       throw new IllegalArgumentException("The 'radius' must be positive.");
/*      */     }
/*      */     
/*  363 */     this.tickRadius = radius;
/*  364 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMajorTickIncrement() {
/*  375 */     return this.majorTickIncrement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMajorTickIncrement(double increment) {
/*  387 */     if (increment <= 0.0D) {
/*  388 */       throw new IllegalArgumentException("The 'increment' must be positive.");
/*      */     }
/*      */     
/*  391 */     this.majorTickIncrement = increment;
/*  392 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMajorTickLength() {
/*  405 */     return this.majorTickLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMajorTickLength(double length) {
/*  417 */     if (length < 0.0D) {
/*  418 */       throw new IllegalArgumentException("Negative 'length' argument.");
/*      */     }
/*  420 */     this.majorTickLength = length;
/*  421 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getMajorTickPaint() {
/*  432 */     return this.majorTickPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMajorTickPaint(Paint paint) {
/*  444 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  445 */     this.majorTickPaint = paint;
/*  446 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getMajorTickStroke() {
/*  457 */     return this.majorTickStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMajorTickStroke(Stroke stroke) {
/*  469 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/*  470 */     this.majorTickStroke = stroke;
/*  471 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinorTickCount() {
/*  482 */     return this.minorTickCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinorTickCount(int count) {
/*  494 */     if (count < 0) {
/*  495 */       throw new IllegalArgumentException("The 'count' cannot be negative.");
/*      */     }
/*      */     
/*  498 */     this.minorTickCount = count;
/*  499 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMinorTickLength() {
/*  512 */     return this.minorTickLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinorTickLength(double length) {
/*  524 */     if (length < 0.0D) {
/*  525 */       throw new IllegalArgumentException("Negative 'length' argument.");
/*      */     }
/*  527 */     this.minorTickLength = length;
/*  528 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getMinorTickPaint() {
/*  539 */     return this.minorTickPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinorTickPaint(Paint paint) {
/*  551 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  552 */     this.minorTickPaint = paint;
/*  553 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getMinorTickStroke() {
/*  566 */     return this.minorTickStroke;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinorTickStroke(Stroke stroke) {
/*  580 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/*  581 */     this.minorTickStroke = stroke;
/*  582 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getTickLabelOffset() {
/*  593 */     return this.tickLabelOffset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickLabelOffset(double offset) {
/*  605 */     this.tickLabelOffset = offset;
/*  606 */     notifyListeners(new DialLayerChangeEvent(this));
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
/*  617 */     return this.tickLabelFont;
/*      */   }
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
/*  629 */     ParamChecks.nullNotPermitted(font, "font");
/*  630 */     this.tickLabelFont = font;
/*  631 */     notifyListeners(new DialLayerChangeEvent(this));
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
/*  642 */     return this.tickLabelPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickLabelPaint(Paint paint) {
/*  652 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  653 */     this.tickLabelPaint = paint;
/*  654 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTickLabelsVisible() {
/*  666 */     return this.tickLabelsVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickLabelsVisible(boolean visible) {
/*  679 */     this.tickLabelsVisible = visible;
/*  680 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NumberFormat getTickLabelFormatter() {
/*  692 */     return this.tickLabelFormatter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickLabelFormatter(NumberFormat formatter) {
/*  705 */     ParamChecks.nullNotPermitted(formatter, "formatter");
/*  706 */     this.tickLabelFormatter = formatter;
/*  707 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFirstTickLabelVisible() {
/*  719 */     return this.firstTickLabelVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFirstTickLabelVisible(boolean visible) {
/*  732 */     this.firstTickLabelVisible = visible;
/*  733 */     notifyListeners(new DialLayerChangeEvent(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isClippedToWindow() {
/*  744 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
/*  761 */     Rectangle2D arcRect = DialPlot.rectangleByRadius(frame, this.tickRadius, this.tickRadius);
/*      */     
/*  763 */     Rectangle2D arcRectMajor = DialPlot.rectangleByRadius(frame, this.tickRadius - this.majorTickLength, this.tickRadius - this.majorTickLength);
/*      */ 
/*      */     
/*  766 */     Rectangle2D arcRectMinor = arcRect;
/*  767 */     if (this.minorTickCount > 0 && this.minorTickLength > 0.0D) {
/*  768 */       arcRectMinor = DialPlot.rectangleByRadius(frame, this.tickRadius - this.minorTickLength, this.tickRadius - this.minorTickLength);
/*      */     }
/*      */ 
/*      */     
/*  772 */     Rectangle2D arcRectForLabels = DialPlot.rectangleByRadius(frame, this.tickRadius - this.tickLabelOffset, this.tickRadius - this.tickLabelOffset);
/*      */ 
/*      */ 
/*      */     
/*  776 */     boolean firstLabel = true;
/*      */     
/*  778 */     Arc2D arc = new Arc2D.Double();
/*  779 */     Line2D workingLine = new Line2D.Double(); double v;
/*  780 */     for (v = this.lowerBound; v <= this.upperBound; 
/*  781 */       v += this.majorTickIncrement) {
/*  782 */       arc.setArc(arcRect, this.startAngle, valueToAngle(v) - this.startAngle, 0);
/*      */       
/*  784 */       Point2D pt0 = arc.getEndPoint();
/*  785 */       arc.setArc(arcRectMajor, this.startAngle, valueToAngle(v) - this.startAngle, 0);
/*      */       
/*  787 */       Point2D pt1 = arc.getEndPoint();
/*  788 */       g2.setPaint(this.majorTickPaint);
/*  789 */       g2.setStroke(this.majorTickStroke);
/*  790 */       workingLine.setLine(pt0, pt1);
/*  791 */       g2.draw(workingLine);
/*  792 */       arc.setArc(arcRectForLabels, this.startAngle, valueToAngle(v) - this.startAngle, 0);
/*      */       
/*  794 */       Point2D pt2 = arc.getEndPoint();
/*      */       
/*  796 */       if (this.tickLabelsVisible && (
/*  797 */         !firstLabel || this.firstTickLabelVisible)) {
/*  798 */         g2.setFont(this.tickLabelFont);
/*  799 */         g2.setPaint(this.tickLabelPaint);
/*  800 */         TextUtilities.drawAlignedString(this.tickLabelFormatter
/*  801 */             .format(v), g2, 
/*  802 */             (float)pt2.getX(), (float)pt2.getY(), TextAnchor.CENTER);
/*      */       } 
/*      */ 
/*      */       
/*  806 */       firstLabel = false;
/*      */ 
/*      */       
/*  809 */       if (this.minorTickCount > 0 && this.minorTickLength > 0.0D) {
/*  810 */         double minorTickIncrement = this.majorTickIncrement / (this.minorTickCount + 1);
/*      */         
/*  812 */         for (int i = 0; i < this.minorTickCount; i++) {
/*  813 */           double vv = v + (i + 1) * minorTickIncrement;
/*  814 */           if (vv >= this.upperBound) {
/*      */             break;
/*      */           }
/*  817 */           double angle = valueToAngle(vv);
/*      */           
/*  819 */           arc.setArc(arcRect, this.startAngle, angle - this.startAngle, 0);
/*      */           
/*  821 */           pt0 = arc.getEndPoint();
/*  822 */           arc.setArc(arcRectMinor, this.startAngle, angle - this.startAngle, 0);
/*      */           
/*  824 */           Point2D pt3 = arc.getEndPoint();
/*  825 */           g2.setStroke(this.minorTickStroke);
/*  826 */           g2.setPaint(this.minorTickPaint);
/*  827 */           workingLine.setLine(pt0, pt3);
/*  828 */           g2.draw(workingLine);
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
/*      */   public double valueToAngle(double value) {
/*  847 */     double range = this.upperBound - this.lowerBound;
/*  848 */     double unit = this.extent / range;
/*  849 */     return this.startAngle + unit * (value - this.lowerBound);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double angleToValue(double angle) {
/*  863 */     double range = this.upperBound - this.lowerBound;
/*  864 */     double unit = range / this.extent;
/*  865 */     return (angle - this.startAngle) * unit;
/*      */   }
/*      */ 
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
/*  878 */     if (obj == this) {
/*  879 */       return true;
/*      */     }
/*  881 */     if (!(obj instanceof StandardDialScale)) {
/*  882 */       return false;
/*      */     }
/*  884 */     StandardDialScale that = (StandardDialScale)obj;
/*  885 */     if (this.lowerBound != that.lowerBound) {
/*  886 */       return false;
/*      */     }
/*  888 */     if (this.upperBound != that.upperBound) {
/*  889 */       return false;
/*      */     }
/*  891 */     if (this.startAngle != that.startAngle) {
/*  892 */       return false;
/*      */     }
/*  894 */     if (this.extent != that.extent) {
/*  895 */       return false;
/*      */     }
/*  897 */     if (this.tickRadius != that.tickRadius) {
/*  898 */       return false;
/*      */     }
/*  900 */     if (this.majorTickIncrement != that.majorTickIncrement) {
/*  901 */       return false;
/*      */     }
/*  903 */     if (this.majorTickLength != that.majorTickLength) {
/*  904 */       return false;
/*      */     }
/*  906 */     if (!PaintUtilities.equal(this.majorTickPaint, that.majorTickPaint)) {
/*  907 */       return false;
/*      */     }
/*  909 */     if (!this.majorTickStroke.equals(that.majorTickStroke)) {
/*  910 */       return false;
/*      */     }
/*  912 */     if (this.minorTickCount != that.minorTickCount) {
/*  913 */       return false;
/*      */     }
/*  915 */     if (this.minorTickLength != that.minorTickLength) {
/*  916 */       return false;
/*      */     }
/*  918 */     if (!PaintUtilities.equal(this.minorTickPaint, that.minorTickPaint)) {
/*  919 */       return false;
/*      */     }
/*  921 */     if (!this.minorTickStroke.equals(that.minorTickStroke)) {
/*  922 */       return false;
/*      */     }
/*  924 */     if (this.tickLabelsVisible != that.tickLabelsVisible) {
/*  925 */       return false;
/*      */     }
/*  927 */     if (this.tickLabelOffset != that.tickLabelOffset) {
/*  928 */       return false;
/*      */     }
/*  930 */     if (!this.tickLabelFont.equals(that.tickLabelFont)) {
/*  931 */       return false;
/*      */     }
/*  933 */     if (!PaintUtilities.equal(this.tickLabelPaint, that.tickLabelPaint)) {
/*  934 */       return false;
/*      */     }
/*  936 */     return super.equals(obj);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  946 */     int result = 193;
/*      */     
/*  948 */     long temp = Double.doubleToLongBits(this.lowerBound);
/*  949 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/*      */     
/*  951 */     temp = Double.doubleToLongBits(this.upperBound);
/*  952 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/*      */     
/*  954 */     temp = Double.doubleToLongBits(this.startAngle);
/*  955 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/*      */     
/*  957 */     temp = Double.doubleToLongBits(this.extent);
/*  958 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/*      */     
/*  960 */     temp = Double.doubleToLongBits(this.tickRadius);
/*  961 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  975 */     return result;
/*      */   }
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
/*  987 */     return super.clone();
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
/*  998 */     stream.defaultWriteObject();
/*  999 */     SerialUtilities.writePaint(this.majorTickPaint, stream);
/* 1000 */     SerialUtilities.writeStroke(this.majorTickStroke, stream);
/* 1001 */     SerialUtilities.writePaint(this.minorTickPaint, stream);
/* 1002 */     SerialUtilities.writeStroke(this.minorTickStroke, stream);
/* 1003 */     SerialUtilities.writePaint(this.tickLabelPaint, stream);
/*      */   }
/*      */ 
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
/* 1016 */     stream.defaultReadObject();
/* 1017 */     this.majorTickPaint = SerialUtilities.readPaint(stream);
/* 1018 */     this.majorTickStroke = SerialUtilities.readStroke(stream);
/* 1019 */     this.minorTickPaint = SerialUtilities.readPaint(stream);
/* 1020 */     this.minorTickStroke = SerialUtilities.readStroke(stream);
/* 1021 */     this.tickLabelPaint = SerialUtilities.readPaint(stream);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/dial/StandardDialScale.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */