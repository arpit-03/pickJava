/*     */ package org.jfree.chart.title;
/*     */ 
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.jfree.chart.block.AbstractBlock;
/*     */ import org.jfree.chart.block.Block;
/*     */ import org.jfree.chart.block.LengthConstraintType;
/*     */ import org.jfree.chart.block.RectangleConstraint;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.GradientPaintTransformer;
/*     */ import org.jfree.ui.RectangleAnchor;
/*     */ import org.jfree.ui.Size2D;
/*     */ import org.jfree.ui.StandardGradientPaintTransformer;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PaintUtilities;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ import org.jfree.util.ShapeUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LegendGraphic
/*     */   extends AbstractBlock
/*     */   implements Block, PublicCloneable
/*     */ {
/*     */   static final long serialVersionUID = -1338791523854985009L;
/*     */   private boolean shapeVisible;
/*     */   private transient Shape shape;
/*     */   private RectangleAnchor shapeLocation;
/*     */   private RectangleAnchor shapeAnchor;
/*     */   private boolean shapeFilled;
/*     */   private transient Paint fillPaint;
/*     */   private GradientPaintTransformer fillPaintTransformer;
/*     */   private boolean shapeOutlineVisible;
/*     */   private transient Paint outlinePaint;
/*     */   private transient Stroke outlineStroke;
/*     */   private boolean lineVisible;
/*     */   private transient Shape line;
/*     */   private transient Stroke lineStroke;
/*     */   private transient Paint linePaint;
/*     */   
/*     */   public LegendGraphic(Shape shape, Paint fillPaint) {
/* 157 */     ParamChecks.nullNotPermitted(shape, "shape");
/* 158 */     ParamChecks.nullNotPermitted(fillPaint, "fillPaint");
/* 159 */     this.shapeVisible = true;
/* 160 */     this.shape = shape;
/* 161 */     this.shapeAnchor = RectangleAnchor.CENTER;
/* 162 */     this.shapeLocation = RectangleAnchor.CENTER;
/* 163 */     this.shapeFilled = true;
/* 164 */     this.fillPaint = fillPaint;
/* 165 */     this.fillPaintTransformer = (GradientPaintTransformer)new StandardGradientPaintTransformer();
/* 166 */     setPadding(2.0D, 2.0D, 2.0D, 2.0D);
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
/*     */   public boolean isShapeVisible() {
/* 178 */     return this.shapeVisible;
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
/*     */   public void setShapeVisible(boolean visible) {
/* 190 */     this.shapeVisible = visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape getShape() {
/* 201 */     return this.shape;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShape(Shape shape) {
/* 212 */     this.shape = shape;
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
/*     */   public boolean isShapeFilled() {
/* 224 */     return this.shapeFilled;
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
/*     */   public void setShapeFilled(boolean filled) {
/* 236 */     this.shapeFilled = filled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getFillPaint() {
/* 247 */     return this.fillPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFillPaint(Paint paint) {
/* 258 */     this.fillPaint = paint;
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
/*     */   public GradientPaintTransformer getFillPaintTransformer() {
/* 272 */     return this.fillPaintTransformer;
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
/*     */   public void setFillPaintTransformer(GradientPaintTransformer transformer) {
/* 286 */     ParamChecks.nullNotPermitted(transformer, "transformer");
/* 287 */     this.fillPaintTransformer = transformer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShapeOutlineVisible() {
/* 298 */     return this.shapeOutlineVisible;
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
/*     */   public void setShapeOutlineVisible(boolean visible) {
/* 310 */     this.shapeOutlineVisible = visible;
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
/* 321 */     return this.outlinePaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutlinePaint(Paint paint) {
/* 332 */     this.outlinePaint = paint;
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
/* 343 */     return this.outlineStroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutlineStroke(Stroke stroke) {
/* 354 */     this.outlineStroke = stroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleAnchor getShapeAnchor() {
/* 365 */     return this.shapeAnchor;
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
/*     */   public void setShapeAnchor(RectangleAnchor anchor) {
/* 377 */     ParamChecks.nullNotPermitted(anchor, "anchor");
/* 378 */     this.shapeAnchor = anchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleAnchor getShapeLocation() {
/* 389 */     return this.shapeLocation;
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
/*     */   public void setShapeLocation(RectangleAnchor location) {
/* 401 */     ParamChecks.nullNotPermitted(location, "location");
/* 402 */     this.shapeLocation = location;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLineVisible() {
/* 413 */     return this.lineVisible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineVisible(boolean visible) {
/* 424 */     this.lineVisible = visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape getLine() {
/* 435 */     return this.line;
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
/*     */   public void setLine(Shape line) {
/* 447 */     this.line = line;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getLinePaint() {
/* 458 */     return this.linePaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLinePaint(Paint paint) {
/* 469 */     this.linePaint = paint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getLineStroke() {
/* 480 */     return this.lineStroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineStroke(Stroke stroke) {
/* 491 */     this.lineStroke = stroke;
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
/*     */   public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
/* 505 */     RectangleConstraint contentConstraint = toContentConstraint(constraint);
/* 506 */     LengthConstraintType w = contentConstraint.getWidthConstraintType();
/* 507 */     LengthConstraintType h = contentConstraint.getHeightConstraintType();
/* 508 */     Size2D contentSize = null;
/* 509 */     if (w == LengthConstraintType.NONE) {
/* 510 */       if (h == LengthConstraintType.NONE) {
/* 511 */         contentSize = arrangeNN(g2);
/*     */       } else {
/* 513 */         if (h == LengthConstraintType.RANGE) {
/* 514 */           throw new RuntimeException("Not yet implemented.");
/*     */         }
/* 516 */         if (h == LengthConstraintType.FIXED) {
/* 517 */           throw new RuntimeException("Not yet implemented.");
/*     */         }
/*     */       } 
/* 520 */     } else if (w == LengthConstraintType.RANGE) {
/* 521 */       if (h == LengthConstraintType.NONE) {
/* 522 */         throw new RuntimeException("Not yet implemented.");
/*     */       }
/* 524 */       if (h == LengthConstraintType.RANGE) {
/* 525 */         throw new RuntimeException("Not yet implemented.");
/*     */       }
/* 527 */       if (h == LengthConstraintType.FIXED) {
/* 528 */         throw new RuntimeException("Not yet implemented.");
/*     */       }
/*     */     }
/* 531 */     else if (w == LengthConstraintType.FIXED) {
/* 532 */       if (h == LengthConstraintType.NONE) {
/* 533 */         throw new RuntimeException("Not yet implemented.");
/*     */       }
/* 535 */       if (h == LengthConstraintType.RANGE) {
/* 536 */         throw new RuntimeException("Not yet implemented.");
/*     */       }
/* 538 */       if (h == LengthConstraintType.FIXED)
/*     */       {
/* 540 */         contentSize = new Size2D(contentConstraint.getWidth(), contentConstraint.getHeight());
/*     */       }
/*     */     } 
/* 543 */     assert contentSize != null;
/* 544 */     return new Size2D(calculateTotalWidth(contentSize.getWidth()), 
/* 545 */         calculateTotalHeight(contentSize.getHeight()));
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
/*     */   protected Size2D arrangeNN(Graphics2D g2) {
/* 558 */     Rectangle2D contentSize = new Rectangle2D.Double();
/* 559 */     if (this.line != null) {
/* 560 */       contentSize.setRect(this.line.getBounds2D());
/*     */     }
/* 562 */     if (this.shape != null) {
/* 563 */       contentSize = contentSize.createUnion(this.shape.getBounds2D());
/*     */     }
/* 565 */     return new Size2D(contentSize.getWidth(), contentSize.getHeight());
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
/*     */   public void draw(Graphics2D g2, Rectangle2D area) {
/* 577 */     area = trimMargin(area);
/* 578 */     drawBorder(g2, area);
/* 579 */     area = trimBorder(area);
/* 580 */     area = trimPadding(area);
/*     */     
/* 582 */     if (this.lineVisible) {
/* 583 */       Point2D location = RectangleAnchor.coordinates(area, this.shapeLocation);
/*     */       
/* 585 */       Shape aLine = ShapeUtilities.createTranslatedShape(getLine(), this.shapeAnchor, location
/* 586 */           .getX(), location.getY());
/* 587 */       g2.setPaint(this.linePaint);
/* 588 */       g2.setStroke(this.lineStroke);
/* 589 */       g2.draw(aLine);
/*     */     } 
/*     */     
/* 592 */     if (this.shapeVisible) {
/* 593 */       Point2D location = RectangleAnchor.coordinates(area, this.shapeLocation);
/*     */ 
/*     */       
/* 596 */       Shape s = ShapeUtilities.createTranslatedShape(this.shape, this.shapeAnchor, location
/* 597 */           .getX(), location.getY());
/* 598 */       if (this.shapeFilled) {
/* 599 */         Paint p = this.fillPaint;
/* 600 */         if (p instanceof GradientPaint) {
/* 601 */           GradientPaint gp = (GradientPaint)this.fillPaint;
/* 602 */           p = this.fillPaintTransformer.transform(gp, s);
/*     */         } 
/* 604 */         g2.setPaint(p);
/* 605 */         g2.fill(s);
/*     */       } 
/* 607 */       if (this.shapeOutlineVisible) {
/* 608 */         g2.setPaint(this.outlinePaint);
/* 609 */         g2.setStroke(this.outlineStroke);
/* 610 */         g2.draw(s);
/*     */       } 
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
/*     */   
/*     */   public Object draw(Graphics2D g2, Rectangle2D area, Object params) {
/* 626 */     draw(g2, area);
/* 627 */     return null;
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
/* 640 */     if (!(obj instanceof LegendGraphic)) {
/* 641 */       return false;
/*     */     }
/* 643 */     LegendGraphic that = (LegendGraphic)obj;
/* 644 */     if (this.shapeVisible != that.shapeVisible) {
/* 645 */       return false;
/*     */     }
/* 647 */     if (!ShapeUtilities.equal(this.shape, that.shape)) {
/* 648 */       return false;
/*     */     }
/* 650 */     if (this.shapeFilled != that.shapeFilled) {
/* 651 */       return false;
/*     */     }
/* 653 */     if (!PaintUtilities.equal(this.fillPaint, that.fillPaint)) {
/* 654 */       return false;
/*     */     }
/* 656 */     if (!ObjectUtilities.equal(this.fillPaintTransformer, that.fillPaintTransformer))
/*     */     {
/* 658 */       return false;
/*     */     }
/* 660 */     if (this.shapeOutlineVisible != that.shapeOutlineVisible) {
/* 661 */       return false;
/*     */     }
/* 663 */     if (!PaintUtilities.equal(this.outlinePaint, that.outlinePaint)) {
/* 664 */       return false;
/*     */     }
/* 666 */     if (!ObjectUtilities.equal(this.outlineStroke, that.outlineStroke)) {
/* 667 */       return false;
/*     */     }
/* 669 */     if (this.shapeAnchor != that.shapeAnchor) {
/* 670 */       return false;
/*     */     }
/* 672 */     if (this.shapeLocation != that.shapeLocation) {
/* 673 */       return false;
/*     */     }
/* 675 */     if (this.lineVisible != that.lineVisible) {
/* 676 */       return false;
/*     */     }
/* 678 */     if (!ShapeUtilities.equal(this.line, that.line)) {
/* 679 */       return false;
/*     */     }
/* 681 */     if (!PaintUtilities.equal(this.linePaint, that.linePaint)) {
/* 682 */       return false;
/*     */     }
/* 684 */     if (!ObjectUtilities.equal(this.lineStroke, that.lineStroke)) {
/* 685 */       return false;
/*     */     }
/* 687 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 697 */     int result = 193;
/* 698 */     result = 37 * result + ObjectUtilities.hashCode(this.fillPaint);
/*     */     
/* 700 */     return result;
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
/* 712 */     LegendGraphic clone = (LegendGraphic)super.clone();
/* 713 */     clone.shape = ShapeUtilities.clone(this.shape);
/* 714 */     clone.line = ShapeUtilities.clone(this.line);
/* 715 */     return clone;
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
/* 726 */     stream.defaultWriteObject();
/* 727 */     SerialUtilities.writeShape(this.shape, stream);
/* 728 */     SerialUtilities.writePaint(this.fillPaint, stream);
/* 729 */     SerialUtilities.writePaint(this.outlinePaint, stream);
/* 730 */     SerialUtilities.writeStroke(this.outlineStroke, stream);
/* 731 */     SerialUtilities.writeShape(this.line, stream);
/* 732 */     SerialUtilities.writePaint(this.linePaint, stream);
/* 733 */     SerialUtilities.writeStroke(this.lineStroke, stream);
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
/* 746 */     stream.defaultReadObject();
/* 747 */     this.shape = SerialUtilities.readShape(stream);
/* 748 */     this.fillPaint = SerialUtilities.readPaint(stream);
/* 749 */     this.outlinePaint = SerialUtilities.readPaint(stream);
/* 750 */     this.outlineStroke = SerialUtilities.readStroke(stream);
/* 751 */     this.line = SerialUtilities.readShape(stream);
/* 752 */     this.linePaint = SerialUtilities.readPaint(stream);
/* 753 */     this.lineStroke = SerialUtilities.readStroke(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/title/LegendGraphic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */