/*     */ package org.jfree.chart.plot.dial;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Arc2D;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
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
/*     */ public abstract class DialPointer
/*     */   extends AbstractDialLayer
/*     */   implements DialLayer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   double radius;
/*     */   int datasetIndex;
/*     */   
/*     */   protected DialPointer() {
/*  90 */     this(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DialPointer(int datasetIndex) {
/*  99 */     this.radius = 0.9D;
/* 100 */     this.datasetIndex = datasetIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDatasetIndex() {
/* 111 */     return this.datasetIndex;
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
/* 123 */     this.datasetIndex = index;
/* 124 */     notifyListeners(new DialLayerChangeEvent(this));
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
/*     */   public double getRadius() {
/* 136 */     return this.radius;
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
/* 148 */     this.radius = radius;
/* 149 */     notifyListeners(new DialLayerChangeEvent(this));
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
/* 160 */     return true;
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
/*     */   public boolean equals(Object obj) {
/* 172 */     if (obj == this) {
/* 173 */       return true;
/*     */     }
/* 175 */     if (!(obj instanceof DialPointer)) {
/* 176 */       return false;
/*     */     }
/* 178 */     DialPointer that = (DialPointer)obj;
/* 179 */     if (this.datasetIndex != that.datasetIndex) {
/* 180 */       return false;
/*     */     }
/* 182 */     if (this.radius != that.radius) {
/* 183 */       return false;
/*     */     }
/* 185 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 195 */     int result = 23;
/* 196 */     result = HashUtilities.hashCode(result, this.radius);
/* 197 */     return result;
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
/* 210 */     return super.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Pin
/*     */     extends DialPointer
/*     */   {
/*     */     static final long serialVersionUID = -8445860485367689750L;
/*     */ 
/*     */ 
/*     */     
/*     */     private transient Paint paint;
/*     */ 
/*     */     
/*     */     private transient Stroke stroke;
/*     */ 
/*     */ 
/*     */     
/*     */     public Pin() {
/* 231 */       this(0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Pin(int datasetIndex) {
/* 240 */       super(datasetIndex);
/* 241 */       this.paint = Color.red;
/* 242 */       this.stroke = new BasicStroke(3.0F, 1, 2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Paint getPaint() {
/* 254 */       return this.paint;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPaint(Paint paint) {
/* 266 */       ParamChecks.nullNotPermitted(paint, "paint");
/* 267 */       this.paint = paint;
/* 268 */       notifyListeners(new DialLayerChangeEvent(this));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Stroke getStroke() {
/* 279 */       return this.stroke;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setStroke(Stroke stroke) {
/* 291 */       ParamChecks.nullNotPermitted(stroke, "stroke");
/* 292 */       this.stroke = stroke;
/* 293 */       notifyListeners(new DialLayerChangeEvent(this));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
/* 308 */       g2.setPaint(this.paint);
/* 309 */       g2.setStroke(this.stroke);
/* 310 */       Rectangle2D arcRect = DialPlot.rectangleByRadius(frame, this.radius, this.radius);
/*     */ 
/*     */       
/* 313 */       double value = plot.getValue(this.datasetIndex);
/* 314 */       DialScale scale = plot.getScaleForDataset(this.datasetIndex);
/* 315 */       double angle = scale.valueToAngle(value);
/*     */       
/* 317 */       Arc2D arc = new Arc2D.Double(arcRect, angle, 0.0D, 0);
/* 318 */       Point2D pt = arc.getEndPoint();
/*     */ 
/*     */       
/* 321 */       Line2D line = new Line2D.Double(frame.getCenterX(), frame.getCenterY(), pt.getX(), pt.getY());
/* 322 */       g2.draw(line);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 334 */       if (obj == this) {
/* 335 */         return true;
/*     */       }
/* 337 */       if (!(obj instanceof Pin)) {
/* 338 */         return false;
/*     */       }
/* 340 */       Pin that = (Pin)obj;
/* 341 */       if (!PaintUtilities.equal(this.paint, that.paint)) {
/* 342 */         return false;
/*     */       }
/* 344 */       if (!this.stroke.equals(that.stroke)) {
/* 345 */         return false;
/*     */       }
/* 347 */       return super.equals(obj);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 357 */       int result = super.hashCode();
/* 358 */       result = HashUtilities.hashCode(result, this.paint);
/* 359 */       result = HashUtilities.hashCode(result, this.stroke);
/* 360 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void writeObject(ObjectOutputStream stream) throws IOException {
/* 371 */       stream.defaultWriteObject();
/* 372 */       SerialUtilities.writePaint(this.paint, stream);
/* 373 */       SerialUtilities.writeStroke(this.stroke, stream);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 386 */       stream.defaultReadObject();
/* 387 */       this.paint = SerialUtilities.readPaint(stream);
/* 388 */       this.stroke = SerialUtilities.readStroke(stream);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Pointer
/*     */     extends DialPointer
/*     */   {
/*     */     static final long serialVersionUID = -4180500011963176960L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double widthRadius;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private transient Paint fillPaint;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private transient Paint outlinePaint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Pointer() {
/* 424 */       this(0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Pointer(int datasetIndex) {
/* 433 */       super(datasetIndex);
/* 434 */       this.widthRadius = 0.05D;
/* 435 */       this.fillPaint = Color.gray;
/* 436 */       this.outlinePaint = Color.black;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getWidthRadius() {
/* 447 */       return this.widthRadius;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setWidthRadius(double radius) {
/* 459 */       this.widthRadius = radius;
/* 460 */       notifyListeners(new DialLayerChangeEvent(this));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Paint getFillPaint() {
/* 473 */       return this.fillPaint;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setFillPaint(Paint paint) {
/* 487 */       ParamChecks.nullNotPermitted(paint, "paint");
/* 488 */       this.fillPaint = paint;
/* 489 */       notifyListeners(new DialLayerChangeEvent(this));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Paint getOutlinePaint() {
/* 502 */       return this.outlinePaint;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setOutlinePaint(Paint paint) {
/* 516 */       ParamChecks.nullNotPermitted(paint, "paint");
/* 517 */       this.outlinePaint = paint;
/* 518 */       notifyListeners(new DialLayerChangeEvent(this));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
/* 533 */       g2.setPaint(Color.blue);
/* 534 */       g2.setStroke(new BasicStroke(1.0F));
/* 535 */       Rectangle2D lengthRect = DialPlot.rectangleByRadius(frame, this.radius, this.radius);
/*     */       
/* 537 */       Rectangle2D widthRect = DialPlot.rectangleByRadius(frame, this.widthRadius, this.widthRadius);
/*     */       
/* 539 */       double value = plot.getValue(this.datasetIndex);
/* 540 */       DialScale scale = plot.getScaleForDataset(this.datasetIndex);
/* 541 */       double angle = scale.valueToAngle(value);
/*     */       
/* 543 */       Arc2D arc1 = new Arc2D.Double(lengthRect, angle, 0.0D, 0);
/* 544 */       Point2D pt1 = arc1.getEndPoint();
/* 545 */       Arc2D arc2 = new Arc2D.Double(widthRect, angle - 90.0D, 180.0D, 0);
/*     */       
/* 547 */       Point2D pt2 = arc2.getStartPoint();
/* 548 */       Point2D pt3 = arc2.getEndPoint();
/* 549 */       Arc2D arc3 = new Arc2D.Double(widthRect, angle - 180.0D, 0.0D, 0);
/*     */       
/* 551 */       Point2D pt4 = arc3.getStartPoint();
/*     */       
/* 553 */       GeneralPath gp = new GeneralPath();
/* 554 */       gp.moveTo((float)pt1.getX(), (float)pt1.getY());
/* 555 */       gp.lineTo((float)pt2.getX(), (float)pt2.getY());
/* 556 */       gp.lineTo((float)pt4.getX(), (float)pt4.getY());
/* 557 */       gp.lineTo((float)pt3.getX(), (float)pt3.getY());
/* 558 */       gp.closePath();
/* 559 */       g2.setPaint(this.fillPaint);
/* 560 */       g2.fill(gp);
/*     */       
/* 562 */       g2.setPaint(this.outlinePaint);
/*     */       
/* 564 */       Line2D line = new Line2D.Double(frame.getCenterX(), frame.getCenterY(), pt1.getX(), pt1.getY());
/* 565 */       g2.draw(line);
/*     */       
/* 567 */       line.setLine(pt2, pt3);
/* 568 */       g2.draw(line);
/*     */       
/* 570 */       line.setLine(pt3, pt1);
/* 571 */       g2.draw(line);
/*     */       
/* 573 */       line.setLine(pt2, pt1);
/* 574 */       g2.draw(line);
/*     */       
/* 576 */       line.setLine(pt2, pt4);
/* 577 */       g2.draw(line);
/*     */       
/* 579 */       line.setLine(pt3, pt4);
/* 580 */       g2.draw(line);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 592 */       if (obj == this) {
/* 593 */         return true;
/*     */       }
/* 595 */       if (!(obj instanceof Pointer)) {
/* 596 */         return false;
/*     */       }
/* 598 */       Pointer that = (Pointer)obj;
/*     */       
/* 600 */       if (this.widthRadius != that.widthRadius) {
/* 601 */         return false;
/*     */       }
/* 603 */       if (!PaintUtilities.equal(this.fillPaint, that.fillPaint)) {
/* 604 */         return false;
/*     */       }
/* 606 */       if (!PaintUtilities.equal(this.outlinePaint, that.outlinePaint)) {
/* 607 */         return false;
/*     */       }
/* 609 */       return super.equals(obj);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 619 */       int result = super.hashCode();
/* 620 */       result = HashUtilities.hashCode(result, this.widthRadius);
/* 621 */       result = HashUtilities.hashCode(result, this.fillPaint);
/* 622 */       result = HashUtilities.hashCode(result, this.outlinePaint);
/* 623 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void writeObject(ObjectOutputStream stream) throws IOException {
/* 634 */       stream.defaultWriteObject();
/* 635 */       SerialUtilities.writePaint(this.fillPaint, stream);
/* 636 */       SerialUtilities.writePaint(this.outlinePaint, stream);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 649 */       stream.defaultReadObject();
/* 650 */       this.fillPaint = SerialUtilities.readPaint(stream);
/* 651 */       this.outlinePaint = SerialUtilities.readPaint(stream);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/dial/DialPointer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */