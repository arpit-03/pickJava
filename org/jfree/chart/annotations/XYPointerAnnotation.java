/*     */ package org.jfree.chart.annotations;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.text.TextUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.util.ObjectUtilities;
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
/*     */ public class XYPointerAnnotation
/*     */   extends XYTextAnnotation
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4031161445009858551L;
/*     */   public static final double DEFAULT_TIP_RADIUS = 10.0D;
/*     */   public static final double DEFAULT_BASE_RADIUS = 30.0D;
/*     */   public static final double DEFAULT_LABEL_OFFSET = 3.0D;
/*     */   public static final double DEFAULT_ARROW_LENGTH = 5.0D;
/*     */   public static final double DEFAULT_ARROW_WIDTH = 3.0D;
/*     */   private double angle;
/*     */   private double tipRadius;
/*     */   private double baseRadius;
/*     */   private double arrowLength;
/*     */   private double arrowWidth;
/*     */   private transient Stroke arrowStroke;
/*     */   private transient Paint arrowPaint;
/*     */   private double labelOffset;
/*     */   
/*     */   public XYPointerAnnotation(String label, double x, double y, double angle) {
/* 159 */     super(label, x, y);
/* 160 */     this.angle = angle;
/* 161 */     this.tipRadius = 10.0D;
/* 162 */     this.baseRadius = 30.0D;
/* 163 */     this.arrowLength = 5.0D;
/* 164 */     this.arrowWidth = 3.0D;
/* 165 */     this.labelOffset = 3.0D;
/* 166 */     this.arrowStroke = new BasicStroke(1.0F);
/* 167 */     this.arrowPaint = Color.black;
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
/* 179 */     return this.angle;
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
/* 191 */     this.angle = angle;
/* 192 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTipRadius() {
/* 203 */     return this.tipRadius;
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
/*     */   public void setTipRadius(double radius) {
/* 215 */     this.tipRadius = radius;
/* 216 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBaseRadius() {
/* 227 */     return this.baseRadius;
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
/*     */   public void setBaseRadius(double radius) {
/* 239 */     this.baseRadius = radius;
/* 240 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLabelOffset() {
/* 251 */     return this.labelOffset;
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
/*     */   public void setLabelOffset(double offset) {
/* 264 */     this.labelOffset = offset;
/* 265 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getArrowLength() {
/* 276 */     return this.arrowLength;
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
/*     */   public void setArrowLength(double length) {
/* 288 */     this.arrowLength = length;
/* 289 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getArrowWidth() {
/* 300 */     return this.arrowWidth;
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
/*     */   public void setArrowWidth(double width) {
/* 312 */     this.arrowWidth = width;
/* 313 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getArrowStroke() {
/* 324 */     return this.arrowStroke;
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
/*     */   public void setArrowStroke(Stroke stroke) {
/* 336 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 337 */     this.arrowStroke = stroke;
/* 338 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getArrowPaint() {
/* 349 */     return this.arrowPaint;
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
/*     */   public void setArrowPaint(Paint paint) {
/* 361 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 362 */     this.arrowPaint = paint;
/* 363 */     fireAnnotationChanged();
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
/*     */   
/*     */   public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex, PlotRenderingInfo info) {
/* 382 */     PlotOrientation orientation = plot.getOrientation();
/* 383 */     RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot
/* 384 */         .getDomainAxisLocation(), orientation);
/* 385 */     RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot
/* 386 */         .getRangeAxisLocation(), orientation);
/* 387 */     double j2DX = domainAxis.valueToJava2D(getX(), dataArea, domainEdge);
/* 388 */     double j2DY = rangeAxis.valueToJava2D(getY(), dataArea, rangeEdge);
/* 389 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 390 */       double temp = j2DX;
/* 391 */       j2DX = j2DY;
/* 392 */       j2DY = temp;
/*     */     } 
/* 394 */     double startX = j2DX + Math.cos(this.angle) * this.baseRadius;
/* 395 */     double startY = j2DY + Math.sin(this.angle) * this.baseRadius;
/*     */     
/* 397 */     double endX = j2DX + Math.cos(this.angle) * this.tipRadius;
/* 398 */     double endY = j2DY + Math.sin(this.angle) * this.tipRadius;
/*     */     
/* 400 */     double arrowBaseX = endX + Math.cos(this.angle) * this.arrowLength;
/* 401 */     double arrowBaseY = endY + Math.sin(this.angle) * this.arrowLength;
/*     */ 
/*     */     
/* 404 */     double arrowLeftX = arrowBaseX + Math.cos(this.angle + 1.5707963267948966D) * this.arrowWidth;
/*     */     
/* 406 */     double arrowLeftY = arrowBaseY + Math.sin(this.angle + 1.5707963267948966D) * this.arrowWidth;
/*     */ 
/*     */     
/* 409 */     double arrowRightX = arrowBaseX - Math.cos(this.angle + 1.5707963267948966D) * this.arrowWidth;
/*     */     
/* 411 */     double arrowRightY = arrowBaseY - Math.sin(this.angle + 1.5707963267948966D) * this.arrowWidth;
/*     */     
/* 413 */     GeneralPath arrow = new GeneralPath();
/* 414 */     arrow.moveTo((float)endX, (float)endY);
/* 415 */     arrow.lineTo((float)arrowLeftX, (float)arrowLeftY);
/* 416 */     arrow.lineTo((float)arrowRightX, (float)arrowRightY);
/* 417 */     arrow.closePath();
/*     */     
/* 419 */     g2.setStroke(this.arrowStroke);
/* 420 */     g2.setPaint(this.arrowPaint);
/* 421 */     Line2D line = new Line2D.Double(startX, startY, arrowBaseX, arrowBaseY);
/* 422 */     g2.draw(line);
/* 423 */     g2.fill(arrow);
/*     */ 
/*     */     
/* 426 */     double labelX = j2DX + Math.cos(this.angle) * (this.baseRadius + this.labelOffset);
/*     */     
/* 428 */     double labelY = j2DY + Math.sin(this.angle) * (this.baseRadius + this.labelOffset);
/*     */     
/* 430 */     g2.setFont(getFont());
/* 431 */     Shape hotspot = TextUtilities.calculateRotatedStringBounds(
/* 432 */         getText(), g2, (float)labelX, (float)labelY, getTextAnchor(), 
/* 433 */         getRotationAngle(), getRotationAnchor());
/* 434 */     if (getBackgroundPaint() != null) {
/* 435 */       g2.setPaint(getBackgroundPaint());
/* 436 */       g2.fill(hotspot);
/*     */     } 
/* 438 */     g2.setPaint(getPaint());
/* 439 */     TextUtilities.drawRotatedString(getText(), g2, (float)labelX, (float)labelY, 
/* 440 */         getTextAnchor(), getRotationAngle(), 
/* 441 */         getRotationAnchor());
/* 442 */     if (isOutlineVisible()) {
/* 443 */       g2.setStroke(getOutlineStroke());
/* 444 */       g2.setPaint(getOutlinePaint());
/* 445 */       g2.draw(hotspot);
/*     */     } 
/*     */     
/* 448 */     String toolTip = getToolTipText();
/* 449 */     String url = getURL();
/* 450 */     if (toolTip != null || url != null) {
/* 451 */       addEntity(info, hotspot, rendererIndex, toolTip, url);
/*     */     }
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
/* 465 */     if (obj == this) {
/* 466 */       return true;
/*     */     }
/* 468 */     if (!(obj instanceof XYPointerAnnotation)) {
/* 469 */       return false;
/*     */     }
/* 471 */     XYPointerAnnotation that = (XYPointerAnnotation)obj;
/* 472 */     if (this.angle != that.angle) {
/* 473 */       return false;
/*     */     }
/* 475 */     if (this.tipRadius != that.tipRadius) {
/* 476 */       return false;
/*     */     }
/* 478 */     if (this.baseRadius != that.baseRadius) {
/* 479 */       return false;
/*     */     }
/* 481 */     if (this.arrowLength != that.arrowLength) {
/* 482 */       return false;
/*     */     }
/* 484 */     if (this.arrowWidth != that.arrowWidth) {
/* 485 */       return false;
/*     */     }
/* 487 */     if (!this.arrowPaint.equals(that.arrowPaint)) {
/* 488 */       return false;
/*     */     }
/* 490 */     if (!ObjectUtilities.equal(this.arrowStroke, that.arrowStroke)) {
/* 491 */       return false;
/*     */     }
/* 493 */     if (this.labelOffset != that.labelOffset) {
/* 494 */       return false;
/*     */     }
/* 496 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 506 */     int result = super.hashCode();
/* 507 */     long temp = Double.doubleToLongBits(this.angle);
/* 508 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 509 */     temp = Double.doubleToLongBits(this.tipRadius);
/* 510 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 511 */     temp = Double.doubleToLongBits(this.baseRadius);
/* 512 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 513 */     temp = Double.doubleToLongBits(this.arrowLength);
/* 514 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 515 */     temp = Double.doubleToLongBits(this.arrowWidth);
/* 516 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 517 */     result = result * 37 + HashUtilities.hashCodeForPaint(this.arrowPaint);
/* 518 */     result = result * 37 + this.arrowStroke.hashCode();
/* 519 */     temp = Double.doubleToLongBits(this.labelOffset);
/* 520 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 521 */     return result;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 533 */     return super.clone();
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
/* 544 */     stream.defaultWriteObject();
/* 545 */     SerialUtilities.writePaint(this.arrowPaint, stream);
/* 546 */     SerialUtilities.writeStroke(this.arrowStroke, stream);
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
/* 559 */     stream.defaultReadObject();
/* 560 */     this.arrowPaint = SerialUtilities.readPaint(stream);
/* 561 */     this.arrowStroke = SerialUtilities.readStroke(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/XYPointerAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */