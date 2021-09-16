/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.Effect3D;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.CategoryItemLabelGenerator;
/*     */ import org.jfree.chart.labels.ItemLabelAnchor;
/*     */ import org.jfree.chart.labels.ItemLabelPosition;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.Marker;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.ValueMarker;
/*     */ import org.jfree.chart.util.PaintAlpha;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.text.TextUtilities;
/*     */ import org.jfree.ui.LengthAdjustmentType;
/*     */ import org.jfree.ui.RectangleAnchor;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.TextAnchor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BarRenderer3D
/*     */   extends BarRenderer
/*     */   implements Effect3D, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7686976503536003636L;
/*     */   public static final double DEFAULT_X_OFFSET = 12.0D;
/*     */   public static final double DEFAULT_Y_OFFSET = 8.0D;
/* 170 */   public static final Paint DEFAULT_WALL_PAINT = new Color(221, 221, 221);
/*     */ 
/*     */ 
/*     */   
/*     */   private double xOffset;
/*     */ 
/*     */   
/*     */   private double yOffset;
/*     */ 
/*     */   
/*     */   private transient Paint wallPaint;
/*     */ 
/*     */ 
/*     */   
/*     */   public BarRenderer3D() {
/* 185 */     this(12.0D, 8.0D);
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
/*     */   public BarRenderer3D(double xOffset, double yOffset) {
/* 197 */     this.xOffset = xOffset;
/* 198 */     this.yOffset = yOffset;
/* 199 */     this.wallPaint = DEFAULT_WALL_PAINT;
/*     */     
/* 201 */     ItemLabelPosition p1 = new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.TOP_CENTER);
/*     */     
/* 203 */     setBasePositiveItemLabelPosition(p1);
/* 204 */     ItemLabelPosition p2 = new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.TOP_CENTER);
/*     */     
/* 206 */     setBaseNegativeItemLabelPosition(p2);
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
/*     */   public double getXOffset() {
/* 219 */     return this.xOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYOffset() {
/* 229 */     return this.yOffset;
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
/*     */   public Paint getWallPaint() {
/* 241 */     return this.wallPaint;
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
/*     */   public void setWallPaint(Paint paint) {
/* 254 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 255 */     this.wallPaint = paint;
/* 256 */     fireChangeEvent();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CategoryItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, CategoryPlot plot, int rendererIndex, PlotRenderingInfo info) {
/* 280 */     Rectangle2D adjusted = new Rectangle2D.Double(dataArea.getX(), dataArea.getY() + getYOffset(), dataArea.getWidth() - getXOffset(), dataArea.getHeight() - getYOffset());
/* 281 */     CategoryItemRendererState state = super.initialise(g2, adjusted, plot, rendererIndex, info);
/*     */     
/* 283 */     return state;
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
/*     */   public void drawBackground(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea) {
/* 298 */     float x0 = (float)dataArea.getX();
/* 299 */     float x1 = x0 + (float)Math.abs(this.xOffset);
/* 300 */     float x3 = (float)dataArea.getMaxX();
/* 301 */     float x2 = x3 - (float)Math.abs(this.xOffset);
/*     */     
/* 303 */     float y0 = (float)dataArea.getMaxY();
/* 304 */     float y1 = y0 - (float)Math.abs(this.yOffset);
/* 305 */     float y3 = (float)dataArea.getMinY();
/* 306 */     float y2 = y3 + (float)Math.abs(this.yOffset);
/*     */     
/* 308 */     GeneralPath clip = new GeneralPath();
/* 309 */     clip.moveTo(x0, y0);
/* 310 */     clip.lineTo(x0, y2);
/* 311 */     clip.lineTo(x1, y3);
/* 312 */     clip.lineTo(x3, y3);
/* 313 */     clip.lineTo(x3, y1);
/* 314 */     clip.lineTo(x2, y0);
/* 315 */     clip.closePath();
/*     */     
/* 317 */     Composite originalComposite = g2.getComposite();
/* 318 */     g2.setComposite(AlphaComposite.getInstance(3, plot
/* 319 */           .getBackgroundAlpha()));
/*     */ 
/*     */     
/* 322 */     Paint backgroundPaint = plot.getBackgroundPaint();
/* 323 */     if (backgroundPaint != null) {
/* 324 */       g2.setPaint(backgroundPaint);
/* 325 */       g2.fill(clip);
/*     */     } 
/*     */     
/* 328 */     GeneralPath leftWall = new GeneralPath();
/* 329 */     leftWall.moveTo(x0, y0);
/* 330 */     leftWall.lineTo(x0, y2);
/* 331 */     leftWall.lineTo(x1, y3);
/* 332 */     leftWall.lineTo(x1, y1);
/* 333 */     leftWall.closePath();
/* 334 */     g2.setPaint(getWallPaint());
/* 335 */     g2.fill(leftWall);
/*     */     
/* 337 */     GeneralPath bottomWall = new GeneralPath();
/* 338 */     bottomWall.moveTo(x0, y0);
/* 339 */     bottomWall.lineTo(x1, y1);
/* 340 */     bottomWall.lineTo(x3, y1);
/* 341 */     bottomWall.lineTo(x2, y0);
/* 342 */     bottomWall.closePath();
/* 343 */     g2.setPaint(getWallPaint());
/* 344 */     g2.fill(bottomWall);
/*     */ 
/*     */     
/* 347 */     g2.setPaint(Color.lightGray);
/* 348 */     Line2D corner = new Line2D.Double(x0, y0, x1, y1);
/* 349 */     g2.draw(corner);
/* 350 */     corner.setLine(x1, y1, x1, y3);
/* 351 */     g2.draw(corner);
/* 352 */     corner.setLine(x1, y1, x3, y1);
/* 353 */     g2.draw(corner);
/*     */ 
/*     */     
/* 356 */     Image backgroundImage = plot.getBackgroundImage();
/* 357 */     if (backgroundImage != null) {
/*     */ 
/*     */ 
/*     */       
/* 361 */       Rectangle2D adjusted = new Rectangle2D.Double(dataArea.getX() + getXOffset(), dataArea.getY(), dataArea.getWidth() - getXOffset(), dataArea.getHeight() - getYOffset());
/* 362 */       plot.drawBackgroundImage(g2, adjusted);
/*     */     } 
/*     */     
/* 365 */     g2.setComposite(originalComposite);
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
/*     */   public void drawOutline(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea) {
/* 380 */     float x0 = (float)dataArea.getX();
/* 381 */     float x1 = x0 + (float)Math.abs(this.xOffset);
/* 382 */     float x3 = (float)dataArea.getMaxX();
/* 383 */     float x2 = x3 - (float)Math.abs(this.xOffset);
/*     */     
/* 385 */     float y0 = (float)dataArea.getMaxY();
/* 386 */     float y1 = y0 - (float)Math.abs(this.yOffset);
/* 387 */     float y3 = (float)dataArea.getMinY();
/* 388 */     float y2 = y3 + (float)Math.abs(this.yOffset);
/*     */     
/* 390 */     GeneralPath clip = new GeneralPath();
/* 391 */     clip.moveTo(x0, y0);
/* 392 */     clip.lineTo(x0, y2);
/* 393 */     clip.lineTo(x1, y3);
/* 394 */     clip.lineTo(x3, y3);
/* 395 */     clip.lineTo(x3, y1);
/* 396 */     clip.lineTo(x2, y0);
/* 397 */     clip.closePath();
/*     */ 
/*     */     
/* 400 */     Stroke outlineStroke = plot.getOutlineStroke();
/* 401 */     Paint outlinePaint = plot.getOutlinePaint();
/* 402 */     if (outlineStroke != null && outlinePaint != null) {
/* 403 */       g2.setStroke(outlineStroke);
/* 404 */       g2.setPaint(outlinePaint);
/* 405 */       g2.draw(clip);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawDomainGridline(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea, double value) {
/* 424 */     Line2D line1 = null;
/* 425 */     Line2D line2 = null;
/* 426 */     PlotOrientation orientation = plot.getOrientation();
/* 427 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 428 */       double y0 = value;
/* 429 */       double y1 = value - getYOffset();
/* 430 */       double x0 = dataArea.getMinX();
/* 431 */       double x1 = x0 + getXOffset();
/* 432 */       double x2 = dataArea.getMaxX();
/* 433 */       line1 = new Line2D.Double(x0, y0, x1, y1);
/* 434 */       line2 = new Line2D.Double(x1, y1, x2, y1);
/*     */     }
/* 436 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 437 */       double x0 = value;
/* 438 */       double x1 = value + getXOffset();
/* 439 */       double y0 = dataArea.getMaxY();
/* 440 */       double y1 = y0 - getYOffset();
/* 441 */       double y2 = dataArea.getMinY();
/* 442 */       line1 = new Line2D.Double(x0, y0, x1, y1);
/* 443 */       line2 = new Line2D.Double(x1, y1, x1, y2);
/*     */     } 
/* 445 */     Paint paint = plot.getDomainGridlinePaint();
/* 446 */     Stroke stroke = plot.getDomainGridlineStroke();
/* 447 */     g2.setPaint((paint != null) ? paint : Plot.DEFAULT_OUTLINE_PAINT);
/* 448 */     g2.setStroke((stroke != null) ? stroke : Plot.DEFAULT_OUTLINE_STROKE);
/* 449 */     g2.draw(line1);
/* 450 */     g2.draw(line2);
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
/*     */   public void drawRangeGridline(Graphics2D g2, CategoryPlot plot, ValueAxis axis, Rectangle2D dataArea, double value) {
/* 469 */     Range range = axis.getRange();
/*     */     
/* 471 */     if (!range.contains(value)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 477 */     Rectangle2D adjusted = new Rectangle2D.Double(dataArea.getX(), dataArea.getY() + getYOffset(), dataArea.getWidth() - getXOffset(), dataArea.getHeight() - getYOffset());
/*     */     
/* 479 */     Line2D line1 = null;
/* 480 */     Line2D line2 = null;
/* 481 */     PlotOrientation orientation = plot.getOrientation();
/* 482 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 483 */       double x0 = axis.valueToJava2D(value, adjusted, plot
/* 484 */           .getRangeAxisEdge());
/* 485 */       double x1 = x0 + getXOffset();
/* 486 */       double y0 = dataArea.getMaxY();
/* 487 */       double y1 = y0 - getYOffset();
/* 488 */       double y2 = dataArea.getMinY();
/* 489 */       line1 = new Line2D.Double(x0, y0, x1, y1);
/* 490 */       line2 = new Line2D.Double(x1, y1, x1, y2);
/*     */     }
/* 492 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 493 */       double y0 = axis.valueToJava2D(value, adjusted, plot
/* 494 */           .getRangeAxisEdge());
/* 495 */       double y1 = y0 - getYOffset();
/* 496 */       double x0 = dataArea.getMinX();
/* 497 */       double x1 = x0 + getXOffset();
/* 498 */       double x2 = dataArea.getMaxX();
/* 499 */       line1 = new Line2D.Double(x0, y0, x1, y1);
/* 500 */       line2 = new Line2D.Double(x1, y1, x2, y1);
/*     */     } 
/* 502 */     Paint paint = plot.getRangeGridlinePaint();
/* 503 */     Stroke stroke = plot.getRangeGridlineStroke();
/* 504 */     g2.setPaint((paint != null) ? paint : Plot.DEFAULT_OUTLINE_PAINT);
/* 505 */     g2.setStroke((stroke != null) ? stroke : Plot.DEFAULT_OUTLINE_STROKE);
/* 506 */     g2.draw(line1);
/* 507 */     g2.draw(line2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawRangeLine(Graphics2D g2, CategoryPlot plot, ValueAxis axis, Rectangle2D dataArea, double value, Paint paint, Stroke stroke) {
/* 531 */     Range range = axis.getRange();
/* 532 */     if (!range.contains(value)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 538 */     Rectangle2D adjusted = new Rectangle2D.Double(dataArea.getX(), dataArea.getY() + getYOffset(), dataArea.getWidth() - getXOffset(), dataArea.getHeight() - getYOffset());
/*     */     
/* 540 */     Line2D line1 = null;
/* 541 */     Line2D line2 = null;
/* 542 */     PlotOrientation orientation = plot.getOrientation();
/* 543 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 544 */       double x0 = axis.valueToJava2D(value, adjusted, plot
/* 545 */           .getRangeAxisEdge());
/* 546 */       double x1 = x0 + getXOffset();
/* 547 */       double y0 = dataArea.getMaxY();
/* 548 */       double y1 = y0 - getYOffset();
/* 549 */       double y2 = dataArea.getMinY();
/* 550 */       line1 = new Line2D.Double(x0, y0, x1, y1);
/* 551 */       line2 = new Line2D.Double(x1, y1, x1, y2);
/*     */     }
/* 553 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 554 */       double y0 = axis.valueToJava2D(value, adjusted, plot
/* 555 */           .getRangeAxisEdge());
/* 556 */       double y1 = y0 - getYOffset();
/* 557 */       double x0 = dataArea.getMinX();
/* 558 */       double x1 = x0 + getXOffset();
/* 559 */       double x2 = dataArea.getMaxX();
/* 560 */       line1 = new Line2D.Double(x0, y0, x1, y1);
/* 561 */       line2 = new Line2D.Double(x1, y1, x2, y1);
/*     */     } 
/* 563 */     g2.setPaint(paint);
/* 564 */     g2.setStroke(stroke);
/* 565 */     g2.draw(line1);
/* 566 */     g2.draw(line2);
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
/*     */   
/*     */   public void drawRangeMarker(Graphics2D g2, CategoryPlot plot, ValueAxis axis, Marker marker, Rectangle2D dataArea) {
/* 586 */     Rectangle2D adjusted = new Rectangle2D.Double(dataArea.getX(), dataArea.getY() + getYOffset(), dataArea.getWidth() - getXOffset(), dataArea.getHeight() - getYOffset());
/* 587 */     if (marker instanceof ValueMarker) {
/* 588 */       ValueMarker vm = (ValueMarker)marker;
/* 589 */       double value = vm.getValue();
/* 590 */       Range range = axis.getRange();
/* 591 */       if (!range.contains(value)) {
/*     */         return;
/*     */       }
/*     */       
/* 595 */       GeneralPath path = null;
/* 596 */       PlotOrientation orientation = plot.getOrientation();
/* 597 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 598 */         float x = (float)axis.valueToJava2D(value, adjusted, plot
/* 599 */             .getRangeAxisEdge());
/* 600 */         float y = (float)adjusted.getMaxY();
/* 601 */         path = new GeneralPath();
/* 602 */         path.moveTo(x, y);
/* 603 */         path.lineTo((float)(x + getXOffset()), y - 
/* 604 */             (float)getYOffset());
/* 605 */         path.lineTo((float)(x + getXOffset()), 
/* 606 */             (float)(adjusted.getMinY() - getYOffset()));
/* 607 */         path.lineTo(x, (float)adjusted.getMinY());
/* 608 */         path.closePath();
/*     */       }
/* 610 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 611 */         float y = (float)axis.valueToJava2D(value, adjusted, plot
/* 612 */             .getRangeAxisEdge());
/* 613 */         float x = (float)dataArea.getX();
/* 614 */         path = new GeneralPath();
/* 615 */         path.moveTo(x, y);
/* 616 */         path.lineTo(x + (float)this.xOffset, y - (float)this.yOffset);
/* 617 */         path.lineTo((float)(adjusted.getMaxX() + this.xOffset), y - (float)this.yOffset);
/*     */         
/* 619 */         path.lineTo((float)adjusted.getMaxX(), y);
/* 620 */         path.closePath();
/*     */       } else {
/* 622 */         throw new IllegalStateException();
/*     */       } 
/* 624 */       g2.setPaint(marker.getPaint());
/* 625 */       g2.fill(path);
/* 626 */       g2.setPaint(marker.getOutlinePaint());
/* 627 */       g2.draw(path);
/*     */       
/* 629 */       String label = marker.getLabel();
/* 630 */       RectangleAnchor anchor = marker.getLabelAnchor();
/* 631 */       if (label != null) {
/* 632 */         Font labelFont = marker.getLabelFont();
/* 633 */         g2.setFont(labelFont);
/* 634 */         g2.setPaint(marker.getLabelPaint());
/* 635 */         Point2D coordinates = calculateRangeMarkerTextAnchorPoint(g2, orientation, dataArea, path
/* 636 */             .getBounds2D(), marker
/* 637 */             .getLabelOffset(), LengthAdjustmentType.EXPAND, anchor);
/*     */         
/* 639 */         TextUtilities.drawAlignedString(label, g2, 
/* 640 */             (float)coordinates.getX(), (float)coordinates.getY(), marker
/* 641 */             .getLabelTextAnchor());
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 646 */       super.drawRangeMarker(g2, plot, axis, marker, adjusted);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {
/*     */     Rectangle2D bar;
/* 673 */     int visibleRow = state.getVisibleSeriesIndex(row);
/* 674 */     if (visibleRow < 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 679 */     Number dataValue = dataset.getValue(row, column);
/* 680 */     if (dataValue == null) {
/*     */       return;
/*     */     }
/*     */     
/* 684 */     double value = dataValue.doubleValue();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 689 */     Rectangle2D adjusted = new Rectangle2D.Double(dataArea.getX(), dataArea.getY() + getYOffset(), dataArea.getWidth() - getXOffset(), dataArea.getHeight() - getYOffset());
/*     */     
/* 691 */     PlotOrientation orientation = plot.getOrientation();
/*     */     
/* 693 */     double barW0 = calculateBarW0(plot, orientation, adjusted, domainAxis, state, visibleRow, column);
/*     */     
/* 695 */     double[] barL0L1 = calculateBarL0L1(value);
/* 696 */     if (barL0L1 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 700 */     RectangleEdge edge = plot.getRangeAxisEdge();
/* 701 */     double transL0 = rangeAxis.valueToJava2D(barL0L1[0], adjusted, edge);
/* 702 */     double transL1 = rangeAxis.valueToJava2D(barL0L1[1], adjusted, edge);
/* 703 */     double barL0 = Math.min(transL0, transL1);
/* 704 */     double barLength = Math.abs(transL1 - transL0);
/*     */ 
/*     */ 
/*     */     
/* 708 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*     */       
/* 710 */       bar = new Rectangle2D.Double(barL0, barW0, barLength, state.getBarWidth());
/*     */     } else {
/*     */       
/* 713 */       bar = new Rectangle2D.Double(barW0, barL0, state.getBarWidth(), barLength);
/*     */     } 
/*     */     
/* 716 */     Paint itemPaint = getItemPaint(row, column);
/* 717 */     g2.setPaint(itemPaint);
/* 718 */     g2.fill(bar);
/*     */     
/* 720 */     double x0 = bar.getMinX();
/* 721 */     double x1 = x0 + getXOffset();
/* 722 */     double x2 = bar.getMaxX();
/* 723 */     double x3 = x2 + getXOffset();
/*     */     
/* 725 */     double y0 = bar.getMinY() - getYOffset();
/* 726 */     double y1 = bar.getMinY();
/* 727 */     double y2 = bar.getMaxY() - getYOffset();
/* 728 */     double y3 = bar.getMaxY();
/*     */     
/* 730 */     GeneralPath bar3dRight = null;
/*     */     
/* 732 */     if (barLength > 0.0D) {
/* 733 */       bar3dRight = new GeneralPath();
/* 734 */       bar3dRight.moveTo((float)x2, (float)y3);
/* 735 */       bar3dRight.lineTo((float)x2, (float)y1);
/* 736 */       bar3dRight.lineTo((float)x3, (float)y0);
/* 737 */       bar3dRight.lineTo((float)x3, (float)y2);
/* 738 */       bar3dRight.closePath();
/*     */       
/* 740 */       g2.setPaint(PaintAlpha.darker(itemPaint));
/* 741 */       g2.fill(bar3dRight);
/*     */     } 
/*     */     
/* 744 */     GeneralPath bar3dTop = new GeneralPath();
/* 745 */     bar3dTop.moveTo((float)x0, (float)y1);
/* 746 */     bar3dTop.lineTo((float)x1, (float)y0);
/* 747 */     bar3dTop.lineTo((float)x3, (float)y0);
/* 748 */     bar3dTop.lineTo((float)x2, (float)y1);
/* 749 */     bar3dTop.closePath();
/* 750 */     g2.fill(bar3dTop);
/*     */     
/* 752 */     if (isDrawBarOutline() && state
/* 753 */       .getBarWidth() > 3.0D) {
/* 754 */       g2.setStroke(getItemOutlineStroke(row, column));
/* 755 */       g2.setPaint(getItemOutlinePaint(row, column));
/* 756 */       g2.draw(bar);
/* 757 */       if (bar3dRight != null) {
/* 758 */         g2.draw(bar3dRight);
/*     */       }
/* 760 */       g2.draw(bar3dTop);
/*     */     } 
/*     */ 
/*     */     
/* 764 */     CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
/* 765 */     if (generator != null && isItemLabelVisible(row, column)) {
/* 766 */       drawItemLabel(g2, dataset, row, column, plot, generator, bar, (value < 0.0D));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 771 */     EntityCollection entities = state.getEntityCollection();
/* 772 */     if (entities != null) {
/* 773 */       GeneralPath barOutline = new GeneralPath();
/* 774 */       barOutline.moveTo((float)x0, (float)y3);
/* 775 */       barOutline.lineTo((float)x0, (float)y1);
/* 776 */       barOutline.lineTo((float)x1, (float)y0);
/* 777 */       barOutline.lineTo((float)x3, (float)y0);
/* 778 */       barOutline.lineTo((float)x3, (float)y2);
/* 779 */       barOutline.lineTo((float)x2, (float)y3);
/* 780 */       barOutline.closePath();
/* 781 */       addItemEntity(entities, dataset, row, column, barOutline);
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
/* 795 */     if (obj == this) {
/* 796 */       return true;
/*     */     }
/* 798 */     if (!(obj instanceof BarRenderer3D)) {
/* 799 */       return false;
/*     */     }
/* 801 */     BarRenderer3D that = (BarRenderer3D)obj;
/* 802 */     if (this.xOffset != that.xOffset) {
/* 803 */       return false;
/*     */     }
/* 805 */     if (this.yOffset != that.yOffset) {
/* 806 */       return false;
/*     */     }
/* 808 */     if (!PaintUtilities.equal(this.wallPaint, that.wallPaint)) {
/* 809 */       return false;
/*     */     }
/* 811 */     return super.equals(obj);
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
/* 822 */     stream.defaultWriteObject();
/* 823 */     SerialUtilities.writePaint(this.wallPaint, stream);
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
/* 836 */     stream.defaultReadObject();
/* 837 */     this.wallPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/BarRenderer3D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */