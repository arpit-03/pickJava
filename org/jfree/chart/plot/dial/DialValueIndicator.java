/*     */ package org.jfree.chart.plot.dial;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Arc2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.text.TextUtilities;
/*     */ import org.jfree.ui.RectangleAnchor;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.ui.Size2D;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PaintUtilities;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DialValueIndicator
/*     */   extends AbstractDialLayer
/*     */   implements DialLayer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 803094354130942585L;
/*     */   private int datasetIndex;
/*     */   private double angle;
/*     */   private double radius;
/*     */   private RectangleAnchor frameAnchor;
/*     */   private Number templateValue;
/*     */   private Number maxTemplateValue;
/*     */   private NumberFormat formatter;
/*     */   private Font font;
/*     */   private transient Paint paint;
/*     */   private transient Paint backgroundPaint;
/*     */   private transient Stroke outlineStroke;
/*     */   private transient Paint outlinePaint;
/*     */   private RectangleInsets insets;
/*     */   private RectangleAnchor valueAnchor;
/*     */   private TextAnchor textAnchor;
/*     */   
/*     */   public DialValueIndicator() {
/* 143 */     this(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DialValueIndicator(int datasetIndex) {
/* 152 */     this.datasetIndex = datasetIndex;
/* 153 */     this.angle = -90.0D;
/* 154 */     this.radius = 0.3D;
/* 155 */     this.frameAnchor = RectangleAnchor.CENTER;
/* 156 */     this.templateValue = new Double(100.0D);
/* 157 */     this.maxTemplateValue = null;
/* 158 */     this.formatter = new DecimalFormat("0.0");
/* 159 */     this.font = new Font("Dialog", 1, 14);
/* 160 */     this.paint = Color.black;
/* 161 */     this.backgroundPaint = Color.white;
/* 162 */     this.outlineStroke = new BasicStroke(1.0F);
/* 163 */     this.outlinePaint = Color.blue;
/* 164 */     this.insets = new RectangleInsets(4.0D, 4.0D, 4.0D, 4.0D);
/* 165 */     this.valueAnchor = RectangleAnchor.RIGHT;
/* 166 */     this.textAnchor = TextAnchor.CENTER_RIGHT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDatasetIndex() {
/* 178 */     return this.datasetIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDatasetIndex(int index) {
/* 190 */     this.datasetIndex = index;
/* 191 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAngle() {
/* 203 */     return this.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAngle(double angle) {
/* 215 */     this.angle = angle;
/* 216 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRadius() {
/* 227 */     return this.radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRadius(double radius) {
/* 239 */     this.radius = radius;
/* 240 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleAnchor getFrameAnchor() {
/* 251 */     return this.frameAnchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFrameAnchor(RectangleAnchor anchor) {
/* 263 */     ParamChecks.nullNotPermitted(anchor, "anchor");
/* 264 */     this.frameAnchor = anchor;
/* 265 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getTemplateValue() {
/* 276 */     return this.templateValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTemplateValue(Number value) {
/* 288 */     ParamChecks.nullNotPermitted(value, "value");
/* 289 */     this.templateValue = value;
/* 290 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getMaxTemplateValue() {
/* 304 */     return this.maxTemplateValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxTemplateValue(Number value) {
/* 318 */     this.maxTemplateValue = value;
/* 319 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getNumberFormat() {
/* 330 */     return this.formatter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumberFormat(NumberFormat formatter) {
/* 342 */     ParamChecks.nullNotPermitted(formatter, "formatter");
/* 343 */     this.formatter = formatter;
/* 344 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 355 */     return this.font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 365 */     ParamChecks.nullNotPermitted(font, "font");
/* 366 */     this.font = font;
/* 367 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getPaint() {
/* 378 */     return this.paint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPaint(Paint paint) {
/* 390 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 391 */     this.paint = paint;
/* 392 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getBackgroundPaint() {
/* 403 */     return this.backgroundPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgroundPaint(Paint paint) {
/* 415 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 416 */     this.backgroundPaint = paint;
/* 417 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getOutlineStroke() {
/* 428 */     return this.outlineStroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutlineStroke(Stroke stroke) {
/* 440 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 441 */     this.outlineStroke = stroke;
/* 442 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getOutlinePaint() {
/* 453 */     return this.outlinePaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutlinePaint(Paint paint) {
/* 465 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 466 */     this.outlinePaint = paint;
/* 467 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleInsets getInsets() {
/* 478 */     return this.insets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInsets(RectangleInsets insets) {
/* 490 */     ParamChecks.nullNotPermitted(insets, "insets");
/* 491 */     this.insets = insets;
/* 492 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleAnchor getValueAnchor() {
/* 503 */     return this.valueAnchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValueAnchor(RectangleAnchor anchor) {
/* 515 */     ParamChecks.nullNotPermitted(anchor, "anchor");
/* 516 */     this.valueAnchor = anchor;
/* 517 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextAnchor getTextAnchor() {
/* 528 */     return this.textAnchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextAnchor(TextAnchor anchor) {
/* 540 */     ParamChecks.nullNotPermitted(anchor, "anchor");
/* 541 */     this.textAnchor = anchor;
/* 542 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClippedToWindow() {
/* 553 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
/* 571 */     Rectangle2D f = DialPlot.rectangleByRadius(frame, this.radius, this.radius);
/*     */     
/* 573 */     Arc2D arc = new Arc2D.Double(f, this.angle, 0.0D, 0);
/* 574 */     Point2D pt = arc.getStartPoint();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 579 */     FontMetrics fm = g2.getFontMetrics(this.font);
/* 580 */     double value = plot.getValue(this.datasetIndex);
/* 581 */     String valueStr = this.formatter.format(value);
/* 582 */     Rectangle2D valueBounds = TextUtilities.getTextBounds(valueStr, g2, fm);
/*     */ 
/*     */     
/* 585 */     String s = this.formatter.format(this.templateValue);
/* 586 */     Rectangle2D tb = TextUtilities.getTextBounds(s, g2, fm);
/* 587 */     double minW = tb.getWidth();
/* 588 */     double minH = tb.getHeight();
/*     */     
/* 590 */     double maxW = Double.MAX_VALUE;
/* 591 */     double maxH = Double.MAX_VALUE;
/* 592 */     if (this.maxTemplateValue != null) {
/* 593 */       s = this.formatter.format(this.maxTemplateValue);
/* 594 */       tb = TextUtilities.getTextBounds(s, g2, fm);
/* 595 */       maxW = Math.max(tb.getWidth(), minW);
/* 596 */       maxH = Math.max(tb.getHeight(), minH);
/*     */     } 
/* 598 */     double w = fixToRange(valueBounds.getWidth(), minW, maxW);
/* 599 */     double h = fixToRange(valueBounds.getHeight(), minH, maxH);
/*     */ 
/*     */     
/* 602 */     Rectangle2D bounds = RectangleAnchor.createRectangle(new Size2D(w, h), pt
/* 603 */         .getX(), pt.getY(), this.frameAnchor);
/*     */ 
/*     */     
/* 606 */     Rectangle2D fb = this.insets.createOutsetRectangle(bounds);
/*     */ 
/*     */     
/* 609 */     g2.setPaint(this.backgroundPaint);
/* 610 */     g2.fill(fb);
/*     */ 
/*     */     
/* 613 */     g2.setStroke(this.outlineStroke);
/* 614 */     g2.setPaint(this.outlinePaint);
/* 615 */     g2.draw(fb);
/*     */ 
/*     */     
/* 618 */     Shape savedClip = g2.getClip();
/* 619 */     g2.clip(fb);
/*     */     
/* 621 */     Point2D pt2 = RectangleAnchor.coordinates(bounds, this.valueAnchor);
/* 622 */     g2.setPaint(this.paint);
/* 623 */     g2.setFont(this.font);
/* 624 */     TextUtilities.drawAlignedString(valueStr, g2, (float)pt2.getX(), 
/* 625 */         (float)pt2.getY(), this.textAnchor);
/* 626 */     g2.setClip(savedClip);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double fixToRange(double x, double minX, double maxX) {
/* 641 */     if (minX > maxX) {
/* 642 */       throw new IllegalArgumentException("Requires 'minX' <= 'maxX'.");
/*     */     }
/* 644 */     if (x < minX) {
/* 645 */       return minX;
/*     */     }
/* 647 */     if (x > maxX) {
/* 648 */       return maxX;
/*     */     }
/*     */     
/* 651 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 664 */     if (obj == this) {
/* 665 */       return true;
/*     */     }
/* 667 */     if (!(obj instanceof DialValueIndicator)) {
/* 668 */       return false;
/*     */     }
/* 670 */     DialValueIndicator that = (DialValueIndicator)obj;
/* 671 */     if (this.datasetIndex != that.datasetIndex) {
/* 672 */       return false;
/*     */     }
/* 674 */     if (this.angle != that.angle) {
/* 675 */       return false;
/*     */     }
/* 677 */     if (this.radius != that.radius) {
/* 678 */       return false;
/*     */     }
/* 680 */     if (!this.frameAnchor.equals(that.frameAnchor)) {
/* 681 */       return false;
/*     */     }
/* 683 */     if (!this.templateValue.equals(that.templateValue)) {
/* 684 */       return false;
/*     */     }
/* 686 */     if (!ObjectUtilities.equal(this.maxTemplateValue, that.maxTemplateValue))
/*     */     {
/* 688 */       return false;
/*     */     }
/* 690 */     if (!this.font.equals(that.font)) {
/* 691 */       return false;
/*     */     }
/* 693 */     if (!PaintUtilities.equal(this.paint, that.paint)) {
/* 694 */       return false;
/*     */     }
/* 696 */     if (!PaintUtilities.equal(this.backgroundPaint, that.backgroundPaint)) {
/* 697 */       return false;
/*     */     }
/* 699 */     if (!this.outlineStroke.equals(that.outlineStroke)) {
/* 700 */       return false;
/*     */     }
/* 702 */     if (!PaintUtilities.equal(this.outlinePaint, that.outlinePaint)) {
/* 703 */       return false;
/*     */     }
/* 705 */     if (!this.insets.equals(that.insets)) {
/* 706 */       return false;
/*     */     }
/* 708 */     if (!this.valueAnchor.equals(that.valueAnchor)) {
/* 709 */       return false;
/*     */     }
/* 711 */     if (!this.textAnchor.equals(that.textAnchor)) {
/* 712 */       return false;
/*     */     }
/* 714 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 724 */     int result = 193;
/* 725 */     result = 37 * result + HashUtilities.hashCodeForPaint(this.paint);
/* 726 */     result = 37 * result + HashUtilities.hashCodeForPaint(this.backgroundPaint);
/*     */     
/* 728 */     result = 37 * result + HashUtilities.hashCodeForPaint(this.outlinePaint);
/*     */     
/* 730 */     result = 37 * result + this.outlineStroke.hashCode();
/* 731 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 744 */     return super.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 755 */     stream.defaultWriteObject();
/* 756 */     SerialUtilities.writePaint(this.paint, stream);
/* 757 */     SerialUtilities.writePaint(this.backgroundPaint, stream);
/* 758 */     SerialUtilities.writePaint(this.outlinePaint, stream);
/* 759 */     SerialUtilities.writeStroke(this.outlineStroke, stream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 772 */     stream.defaultReadObject();
/* 773 */     this.paint = SerialUtilities.readPaint(stream);
/* 774 */     this.backgroundPaint = SerialUtilities.readPaint(stream);
/* 775 */     this.outlinePaint = SerialUtilities.readPaint(stream);
/* 776 */     this.outlineStroke = SerialUtilities.readStroke(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/dial/DialValueIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */