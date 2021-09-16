/*     */ package org.jfree.chart.annotations;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.category.CategoryDataset;
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
/*     */ public class CategoryPointerAnnotation
/*     */   extends CategoryTextAnnotation
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
/*     */   public CategoryPointerAnnotation(String label, Comparable key, double value, double angle) {
/* 152 */     super(label, key, value);
/* 153 */     this.angle = angle;
/* 154 */     this.tipRadius = 10.0D;
/* 155 */     this.baseRadius = 30.0D;
/* 156 */     this.arrowLength = 5.0D;
/* 157 */     this.arrowWidth = 3.0D;
/* 158 */     this.labelOffset = 3.0D;
/* 159 */     this.arrowStroke = new BasicStroke(1.0F);
/* 160 */     this.arrowPaint = Color.black;
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
/* 172 */     return this.angle;
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
/* 184 */     this.angle = angle;
/* 185 */     fireAnnotationChanged();
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
/* 196 */     return this.tipRadius;
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
/* 208 */     this.tipRadius = radius;
/* 209 */     fireAnnotationChanged();
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
/* 220 */     return this.baseRadius;
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
/* 232 */     this.baseRadius = radius;
/* 233 */     fireAnnotationChanged();
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
/* 244 */     return this.labelOffset;
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
/* 257 */     this.labelOffset = offset;
/* 258 */     fireAnnotationChanged();
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
/* 269 */     return this.arrowLength;
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
/* 281 */     this.arrowLength = length;
/* 282 */     fireAnnotationChanged();
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
/* 293 */     return this.arrowWidth;
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
/* 305 */     this.arrowWidth = width;
/* 306 */     fireAnnotationChanged();
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
/* 317 */     return this.arrowStroke;
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
/* 329 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 330 */     this.arrowStroke = stroke;
/* 331 */     fireAnnotationChanged();
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
/* 342 */     return this.arrowPaint;
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
/* 354 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 355 */     this.arrowPaint = paint;
/* 356 */     fireAnnotationChanged();
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
/*     */   public void draw(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea, CategoryAxis domainAxis, ValueAxis rangeAxis) {
/* 372 */     PlotOrientation orientation = plot.getOrientation();
/* 373 */     RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot
/* 374 */         .getDomainAxisLocation(), orientation);
/* 375 */     RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot
/* 376 */         .getRangeAxisLocation(), orientation);
/* 377 */     CategoryDataset dataset = plot.getDataset();
/* 378 */     int catIndex = dataset.getColumnIndex(getCategory());
/* 379 */     int catCount = dataset.getColumnCount();
/* 380 */     double j2DX = domainAxis.getCategoryMiddle(catIndex, catCount, dataArea, domainEdge);
/*     */     
/* 382 */     double j2DY = rangeAxis.valueToJava2D(getValue(), dataArea, rangeEdge);
/* 383 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 384 */       double temp = j2DX;
/* 385 */       j2DX = j2DY;
/* 386 */       j2DY = temp;
/*     */     } 
/* 388 */     double startX = j2DX + Math.cos(this.angle) * this.baseRadius;
/* 389 */     double startY = j2DY + Math.sin(this.angle) * this.baseRadius;
/*     */     
/* 391 */     double endX = j2DX + Math.cos(this.angle) * this.tipRadius;
/* 392 */     double endY = j2DY + Math.sin(this.angle) * this.tipRadius;
/*     */     
/* 394 */     double arrowBaseX = endX + Math.cos(this.angle) * this.arrowLength;
/* 395 */     double arrowBaseY = endY + Math.sin(this.angle) * this.arrowLength;
/*     */ 
/*     */     
/* 398 */     double arrowLeftX = arrowBaseX + Math.cos(this.angle + 1.5707963267948966D) * this.arrowWidth;
/*     */     
/* 400 */     double arrowLeftY = arrowBaseY + Math.sin(this.angle + 1.5707963267948966D) * this.arrowWidth;
/*     */ 
/*     */     
/* 403 */     double arrowRightX = arrowBaseX - Math.cos(this.angle + 1.5707963267948966D) * this.arrowWidth;
/*     */     
/* 405 */     double arrowRightY = arrowBaseY - Math.sin(this.angle + 1.5707963267948966D) * this.arrowWidth;
/*     */     
/* 407 */     GeneralPath arrow = new GeneralPath();
/* 408 */     arrow.moveTo((float)endX, (float)endY);
/* 409 */     arrow.lineTo((float)arrowLeftX, (float)arrowLeftY);
/* 410 */     arrow.lineTo((float)arrowRightX, (float)arrowRightY);
/* 411 */     arrow.closePath();
/*     */     
/* 413 */     g2.setStroke(this.arrowStroke);
/* 414 */     g2.setPaint(this.arrowPaint);
/* 415 */     Line2D line = new Line2D.Double(startX, startY, arrowBaseX, arrowBaseY);
/* 416 */     g2.draw(line);
/* 417 */     g2.fill(arrow);
/*     */ 
/*     */     
/* 420 */     g2.setFont(getFont());
/* 421 */     g2.setPaint(getPaint());
/*     */     
/* 423 */     double labelX = j2DX + Math.cos(this.angle) * (this.baseRadius + this.labelOffset);
/*     */     
/* 425 */     double labelY = j2DY + Math.sin(this.angle) * (this.baseRadius + this.labelOffset);
/* 426 */     TextUtilities.drawAlignedString(getText(), g2, (float)labelX, (float)labelY, 
/* 427 */         getTextAnchor());
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
/*     */   public boolean equals(Object obj) {
/* 442 */     if (obj == this) {
/* 443 */       return true;
/*     */     }
/* 445 */     if (!(obj instanceof CategoryPointerAnnotation)) {
/* 446 */       return false;
/*     */     }
/* 448 */     if (!super.equals(obj)) {
/* 449 */       return false;
/*     */     }
/* 451 */     CategoryPointerAnnotation that = (CategoryPointerAnnotation)obj;
/* 452 */     if (this.angle != that.angle) {
/* 453 */       return false;
/*     */     }
/* 455 */     if (this.tipRadius != that.tipRadius) {
/* 456 */       return false;
/*     */     }
/* 458 */     if (this.baseRadius != that.baseRadius) {
/* 459 */       return false;
/*     */     }
/* 461 */     if (this.arrowLength != that.arrowLength) {
/* 462 */       return false;
/*     */     }
/* 464 */     if (this.arrowWidth != that.arrowWidth) {
/* 465 */       return false;
/*     */     }
/* 467 */     if (!this.arrowPaint.equals(that.arrowPaint)) {
/* 468 */       return false;
/*     */     }
/* 470 */     if (!ObjectUtilities.equal(this.arrowStroke, that.arrowStroke)) {
/* 471 */       return false;
/*     */     }
/* 473 */     if (this.labelOffset != that.labelOffset) {
/* 474 */       return false;
/*     */     }
/* 476 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 486 */     int result = 193;
/* 487 */     long temp = Double.doubleToLongBits(this.angle);
/* 488 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 489 */     temp = Double.doubleToLongBits(this.tipRadius);
/* 490 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 491 */     temp = Double.doubleToLongBits(this.baseRadius);
/* 492 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 493 */     temp = Double.doubleToLongBits(this.arrowLength);
/* 494 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 495 */     temp = Double.doubleToLongBits(this.arrowWidth);
/* 496 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 497 */     result = 37 * result + HashUtilities.hashCodeForPaint(this.arrowPaint);
/* 498 */     result = 37 * result + this.arrowStroke.hashCode();
/* 499 */     temp = Double.doubleToLongBits(this.labelOffset);
/* 500 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 501 */     return result;
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
/* 513 */     return super.clone();
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
/* 524 */     stream.defaultWriteObject();
/* 525 */     SerialUtilities.writePaint(this.arrowPaint, stream);
/* 526 */     SerialUtilities.writeStroke(this.arrowStroke, stream);
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
/* 539 */     stream.defaultReadObject();
/* 540 */     this.arrowPaint = SerialUtilities.readPaint(stream);
/* 541 */     this.arrowStroke = SerialUtilities.readStroke(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/CategoryPointerAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */