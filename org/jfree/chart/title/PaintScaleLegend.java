/*     */ package org.jfree.chart.title;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.jfree.chart.axis.AxisLocation;
/*     */ import org.jfree.chart.axis.AxisSpace;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.block.LengthConstraintType;
/*     */ import org.jfree.chart.block.RectangleConstraint;
/*     */ import org.jfree.chart.event.AxisChangeEvent;
/*     */ import org.jfree.chart.event.AxisChangeListener;
/*     */ import org.jfree.chart.event.TitleChangeEvent;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.renderer.PaintScale;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.Size2D;
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
/*     */ public class PaintScaleLegend
/*     */   extends Title
/*     */   implements AxisChangeListener, PublicCloneable
/*     */ {
/*     */   static final long serialVersionUID = -1365146490993227503L;
/*     */   private PaintScale scale;
/*     */   private ValueAxis axis;
/*     */   private AxisLocation axisLocation;
/*     */   private double axisOffset;
/*     */   private double stripWidth;
/*     */   private boolean stripOutlineVisible;
/*     */   private transient Paint stripOutlinePaint;
/*     */   private transient Stroke stripOutlineStroke;
/*     */   private transient Paint backgroundPaint;
/*     */   private int subdivisions;
/*     */   
/*     */   public PaintScaleLegend(PaintScale scale, ValueAxis axis) {
/* 135 */     ParamChecks.nullNotPermitted(axis, "axis");
/* 136 */     this.scale = scale;
/* 137 */     this.axis = axis;
/* 138 */     this.axis.addChangeListener(this);
/* 139 */     this.axisLocation = AxisLocation.BOTTOM_OR_LEFT;
/* 140 */     this.axisOffset = 0.0D;
/* 141 */     this.axis.setRange(scale.getLowerBound(), scale.getUpperBound());
/* 142 */     this.stripWidth = 15.0D;
/* 143 */     this.stripOutlineVisible = true;
/* 144 */     this.stripOutlinePaint = Color.gray;
/* 145 */     this.stripOutlineStroke = new BasicStroke(0.5F);
/* 146 */     this.backgroundPaint = Color.white;
/* 147 */     this.subdivisions = 100;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PaintScale getScale() {
/* 158 */     return this.scale;
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
/*     */   public void setScale(PaintScale scale) {
/* 170 */     ParamChecks.nullNotPermitted(scale, "scale");
/* 171 */     this.scale = scale;
/* 172 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueAxis getAxis() {
/* 183 */     return this.axis;
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
/*     */   public void setAxis(ValueAxis axis) {
/* 195 */     ParamChecks.nullNotPermitted(axis, "axis");
/* 196 */     this.axis.removeChangeListener(this);
/* 197 */     this.axis = axis;
/* 198 */     this.axis.addChangeListener(this);
/* 199 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisLocation getAxisLocation() {
/* 210 */     return this.axisLocation;
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
/*     */   public void setAxisLocation(AxisLocation location) {
/* 222 */     ParamChecks.nullNotPermitted(location, "location");
/* 223 */     this.axisLocation = location;
/* 224 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAxisOffset() {
/* 235 */     return this.axisOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAxisOffset(double offset) {
/* 245 */     this.axisOffset = offset;
/* 246 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getStripWidth() {
/* 257 */     return this.stripWidth;
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
/*     */   public void setStripWidth(double width) {
/* 269 */     this.stripWidth = width;
/* 270 */     notifyListeners(new TitleChangeEvent(this));
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
/*     */   public boolean isStripOutlineVisible() {
/* 282 */     return this.stripOutlineVisible;
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
/*     */   public void setStripOutlineVisible(boolean visible) {
/* 295 */     this.stripOutlineVisible = visible;
/* 296 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getStripOutlinePaint() {
/* 307 */     return this.stripOutlinePaint;
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
/*     */   public void setStripOutlinePaint(Paint paint) {
/* 319 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 320 */     this.stripOutlinePaint = paint;
/* 321 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getStripOutlineStroke() {
/* 332 */     return this.stripOutlineStroke;
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
/*     */   public void setStripOutlineStroke(Stroke stroke) {
/* 344 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 345 */     this.stripOutlineStroke = stroke;
/* 346 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getBackgroundPaint() {
/* 355 */     return this.backgroundPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgroundPaint(Paint paint) {
/* 365 */     this.backgroundPaint = paint;
/* 366 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSubdivisionCount() {
/* 377 */     return this.subdivisions;
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
/*     */   public void setSubdivisionCount(int count) {
/* 389 */     if (count <= 0) {
/* 390 */       throw new IllegalArgumentException("Requires 'count' > 0.");
/*     */     }
/* 392 */     this.subdivisions = count;
/* 393 */     notifyListeners(new TitleChangeEvent(this));
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
/*     */   public void axisChanged(AxisChangeEvent event) {
/* 406 */     if (this.axis == event.getAxis()) {
/* 407 */       notifyListeners(new TitleChangeEvent(this));
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
/*     */   public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
/* 422 */     RectangleConstraint cc = toContentConstraint(constraint);
/* 423 */     LengthConstraintType w = cc.getWidthConstraintType();
/* 424 */     LengthConstraintType h = cc.getHeightConstraintType();
/* 425 */     Size2D contentSize = null;
/* 426 */     if (w == LengthConstraintType.NONE) {
/* 427 */       if (h == LengthConstraintType.NONE) {
/* 428 */         contentSize = new Size2D(getWidth(), getHeight());
/*     */       } else {
/* 430 */         if (h == LengthConstraintType.RANGE) {
/* 431 */           throw new RuntimeException("Not yet implemented.");
/*     */         }
/* 433 */         if (h == LengthConstraintType.FIXED) {
/* 434 */           throw new RuntimeException("Not yet implemented.");
/*     */         }
/*     */       } 
/* 437 */     } else if (w == LengthConstraintType.RANGE) {
/* 438 */       if (h == LengthConstraintType.NONE) {
/* 439 */         throw new RuntimeException("Not yet implemented.");
/*     */       }
/* 441 */       if (h == LengthConstraintType.RANGE) {
/* 442 */         contentSize = arrangeRR(g2, cc.getWidthRange(), cc
/* 443 */             .getHeightRange());
/*     */       }
/* 445 */       else if (h == LengthConstraintType.FIXED) {
/* 446 */         throw new RuntimeException("Not yet implemented.");
/*     */       }
/*     */     
/* 449 */     } else if (w == LengthConstraintType.FIXED) {
/* 450 */       if (h == LengthConstraintType.NONE) {
/* 451 */         throw new RuntimeException("Not yet implemented.");
/*     */       }
/* 453 */       if (h == LengthConstraintType.RANGE) {
/* 454 */         throw new RuntimeException("Not yet implemented.");
/*     */       }
/* 456 */       if (h == LengthConstraintType.FIXED) {
/* 457 */         throw new RuntimeException("Not yet implemented.");
/*     */       }
/*     */     } 
/* 460 */     assert contentSize != null;
/* 461 */     return new Size2D(calculateTotalWidth(contentSize.getWidth()), 
/* 462 */         calculateTotalHeight(contentSize.getHeight()));
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
/*     */   protected Size2D arrangeRR(Graphics2D g2, Range widthRange, Range heightRange) {
/* 479 */     RectangleEdge position = getPosition();
/* 480 */     if (position == RectangleEdge.TOP || position == RectangleEdge.BOTTOM) {
/*     */ 
/*     */       
/* 483 */       float maxWidth = (float)widthRange.getUpperBound();
/*     */ 
/*     */       
/* 486 */       AxisSpace space = this.axis.reserveSpace(g2, null, new Rectangle2D.Double(0.0D, 0.0D, maxWidth, 100.0D), RectangleEdge.BOTTOM, null);
/*     */ 
/*     */ 
/*     */       
/* 490 */       return new Size2D(maxWidth, this.stripWidth + this.axisOffset + space
/* 491 */           .getTop() + space.getBottom());
/*     */     } 
/* 493 */     if (position == RectangleEdge.LEFT || position == RectangleEdge.RIGHT) {
/*     */       
/* 495 */       float maxHeight = (float)heightRange.getUpperBound();
/* 496 */       AxisSpace space = this.axis.reserveSpace(g2, null, new Rectangle2D.Double(0.0D, 0.0D, 100.0D, maxHeight), RectangleEdge.RIGHT, null);
/*     */ 
/*     */       
/* 499 */       return new Size2D(this.stripWidth + this.axisOffset + space
/* 500 */           .getLeft() + space.getRight(), maxHeight);
/*     */     } 
/*     */     
/* 503 */     throw new RuntimeException("Unrecognised position.");
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
/* 515 */     draw(g2, area, (Object)null);
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
/* 529 */     Rectangle2D target = (Rectangle2D)area.clone();
/* 530 */     target = trimMargin(target);
/* 531 */     if (this.backgroundPaint != null) {
/* 532 */       g2.setPaint(this.backgroundPaint);
/* 533 */       g2.fill(target);
/*     */     } 
/* 535 */     getFrame().draw(g2, target);
/* 536 */     getFrame().getInsets().trim(target);
/* 537 */     target = trimPadding(target);
/* 538 */     double base = this.axis.getLowerBound();
/* 539 */     double increment = this.axis.getRange().getLength() / this.subdivisions;
/* 540 */     Rectangle2D r = new Rectangle2D.Double();
/*     */     
/* 542 */     if (RectangleEdge.isTopOrBottom(getPosition())) {
/* 543 */       RectangleEdge axisEdge = Plot.resolveRangeAxisLocation(this.axisLocation, PlotOrientation.HORIZONTAL);
/*     */       
/* 545 */       if (axisEdge == RectangleEdge.TOP) {
/* 546 */         for (int i = 0; i < this.subdivisions; i++) {
/* 547 */           double v = base + i * increment;
/* 548 */           Paint p = this.scale.getPaint(v);
/* 549 */           double vv0 = this.axis.valueToJava2D(v, target, RectangleEdge.TOP);
/*     */           
/* 551 */           double vv1 = this.axis.valueToJava2D(v + increment, target, RectangleEdge.TOP);
/*     */           
/* 553 */           double ww = Math.abs(vv1 - vv0) + 1.0D;
/* 554 */           r.setRect(Math.min(vv0, vv1), target.getMaxY() - this.stripWidth, ww, this.stripWidth);
/*     */           
/* 556 */           g2.setPaint(p);
/* 557 */           g2.fill(r);
/*     */         } 
/* 559 */         if (isStripOutlineVisible()) {
/* 560 */           g2.setPaint(this.stripOutlinePaint);
/* 561 */           g2.setStroke(this.stripOutlineStroke);
/* 562 */           g2.draw(new Rectangle2D.Double(target.getMinX(), target
/* 563 */                 .getMaxY() - this.stripWidth, target
/* 564 */                 .getWidth(), this.stripWidth));
/*     */         } 
/* 566 */         this.axis.draw(g2, target.getMaxY() - this.stripWidth - this.axisOffset, target, target, RectangleEdge.TOP, null);
/*     */ 
/*     */       
/*     */       }
/* 570 */       else if (axisEdge == RectangleEdge.BOTTOM) {
/* 571 */         for (int i = 0; i < this.subdivisions; i++) {
/* 572 */           double v = base + i * increment;
/* 573 */           Paint p = this.scale.getPaint(v);
/* 574 */           double vv0 = this.axis.valueToJava2D(v, target, RectangleEdge.BOTTOM);
/*     */           
/* 576 */           double vv1 = this.axis.valueToJava2D(v + increment, target, RectangleEdge.BOTTOM);
/*     */           
/* 578 */           double ww = Math.abs(vv1 - vv0) + 1.0D;
/* 579 */           r.setRect(Math.min(vv0, vv1), target.getMinY(), ww, this.stripWidth);
/*     */           
/* 581 */           g2.setPaint(p);
/* 582 */           g2.fill(r);
/*     */         } 
/* 584 */         if (isStripOutlineVisible()) {
/* 585 */           g2.setPaint(this.stripOutlinePaint);
/* 586 */           g2.setStroke(this.stripOutlineStroke);
/* 587 */           g2.draw(new Rectangle2D.Double(target.getMinX(), target
/* 588 */                 .getMinY(), target.getWidth(), this.stripWidth));
/*     */         } 
/*     */         
/* 591 */         this.axis.draw(g2, target.getMinY() + this.stripWidth + this.axisOffset, target, target, RectangleEdge.BOTTOM, null);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 597 */       RectangleEdge axisEdge = Plot.resolveRangeAxisLocation(this.axisLocation, PlotOrientation.VERTICAL);
/*     */       
/* 599 */       if (axisEdge == RectangleEdge.LEFT) {
/* 600 */         for (int i = 0; i < this.subdivisions; i++) {
/* 601 */           double v = base + i * increment;
/* 602 */           Paint p = this.scale.getPaint(v);
/* 603 */           double vv0 = this.axis.valueToJava2D(v, target, RectangleEdge.LEFT);
/*     */           
/* 605 */           double vv1 = this.axis.valueToJava2D(v + increment, target, RectangleEdge.LEFT);
/*     */           
/* 607 */           double hh = Math.abs(vv1 - vv0) + 1.0D;
/* 608 */           r.setRect(target.getMaxX() - this.stripWidth, 
/* 609 */               Math.min(vv0, vv1), this.stripWidth, hh);
/* 610 */           g2.setPaint(p);
/* 611 */           g2.fill(r);
/*     */         } 
/* 613 */         if (isStripOutlineVisible()) {
/* 614 */           g2.setPaint(this.stripOutlinePaint);
/* 615 */           g2.setStroke(this.stripOutlineStroke);
/* 616 */           g2.draw(new Rectangle2D.Double(target.getMaxX() - this.stripWidth, target
/* 617 */                 .getMinY(), this.stripWidth, target
/* 618 */                 .getHeight()));
/*     */         } 
/* 620 */         this.axis.draw(g2, target.getMaxX() - this.stripWidth - this.axisOffset, target, target, RectangleEdge.LEFT, null);
/*     */ 
/*     */       
/*     */       }
/* 624 */       else if (axisEdge == RectangleEdge.RIGHT) {
/* 625 */         for (int i = 0; i < this.subdivisions; i++) {
/* 626 */           double v = base + i * increment;
/* 627 */           Paint p = this.scale.getPaint(v);
/* 628 */           double vv0 = this.axis.valueToJava2D(v, target, RectangleEdge.LEFT);
/*     */           
/* 630 */           double vv1 = this.axis.valueToJava2D(v + increment, target, RectangleEdge.LEFT);
/*     */           
/* 632 */           double hh = Math.abs(vv1 - vv0) + 1.0D;
/* 633 */           r.setRect(target.getMinX(), Math.min(vv0, vv1), this.stripWidth, hh);
/*     */           
/* 635 */           g2.setPaint(p);
/* 636 */           g2.fill(r);
/*     */         } 
/* 638 */         if (isStripOutlineVisible()) {
/* 639 */           g2.setPaint(this.stripOutlinePaint);
/* 640 */           g2.setStroke(this.stripOutlineStroke);
/* 641 */           g2.draw(new Rectangle2D.Double(target.getMinX(), target
/* 642 */                 .getMinY(), this.stripWidth, target
/* 643 */                 .getHeight()));
/*     */         } 
/* 645 */         this.axis.draw(g2, target.getMinX() + this.stripWidth + this.axisOffset, target, target, RectangleEdge.RIGHT, null);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 650 */     return null;
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
/* 662 */     if (!(obj instanceof PaintScaleLegend)) {
/* 663 */       return false;
/*     */     }
/* 665 */     PaintScaleLegend that = (PaintScaleLegend)obj;
/* 666 */     if (!this.scale.equals(that.scale)) {
/* 667 */       return false;
/*     */     }
/* 669 */     if (!this.axis.equals(that.axis)) {
/* 670 */       return false;
/*     */     }
/* 672 */     if (!this.axisLocation.equals(that.axisLocation)) {
/* 673 */       return false;
/*     */     }
/* 675 */     if (this.axisOffset != that.axisOffset) {
/* 676 */       return false;
/*     */     }
/* 678 */     if (this.stripWidth != that.stripWidth) {
/* 679 */       return false;
/*     */     }
/* 681 */     if (this.stripOutlineVisible != that.stripOutlineVisible) {
/* 682 */       return false;
/*     */     }
/* 684 */     if (!PaintUtilities.equal(this.stripOutlinePaint, that.stripOutlinePaint))
/*     */     {
/* 686 */       return false;
/*     */     }
/* 688 */     if (!this.stripOutlineStroke.equals(that.stripOutlineStroke)) {
/* 689 */       return false;
/*     */     }
/* 691 */     if (!PaintUtilities.equal(this.backgroundPaint, that.backgroundPaint)) {
/* 692 */       return false;
/*     */     }
/* 694 */     if (this.subdivisions != that.subdivisions) {
/* 695 */       return false;
/*     */     }
/* 697 */     return super.equals(obj);
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
/* 708 */     stream.defaultWriteObject();
/* 709 */     SerialUtilities.writePaint(this.backgroundPaint, stream);
/* 710 */     SerialUtilities.writePaint(this.stripOutlinePaint, stream);
/* 711 */     SerialUtilities.writeStroke(this.stripOutlineStroke, stream);
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
/* 724 */     stream.defaultReadObject();
/* 725 */     this.backgroundPaint = SerialUtilities.readPaint(stream);
/* 726 */     this.stripOutlinePaint = SerialUtilities.readPaint(stream);
/* 727 */     this.stripOutlineStroke = SerialUtilities.readStroke(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/title/PaintScaleLegend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */