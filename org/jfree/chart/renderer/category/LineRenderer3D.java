/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
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
/*     */ import org.jfree.chart.Effect3D;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.Marker;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.ValueMarker;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.util.PaintUtilities;
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
/*     */ public class LineRenderer3D
/*     */   extends LineAndShapeRenderer
/*     */   implements Effect3D, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5467931468380928736L;
/*     */   public static final double DEFAULT_X_OFFSET = 12.0D;
/*     */   public static final double DEFAULT_Y_OFFSET = 8.0D;
/* 112 */   public static final Paint DEFAULT_WALL_PAINT = new Color(221, 221, 221);
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
/*     */   public LineRenderer3D() {
/* 127 */     super(true, false);
/* 128 */     this.xOffset = 12.0D;
/* 129 */     this.yOffset = 8.0D;
/* 130 */     this.wallPaint = DEFAULT_WALL_PAINT;
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
/* 143 */     return this.xOffset;
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
/*     */   public double getYOffset() {
/* 156 */     return this.yOffset;
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
/*     */   public void setXOffset(double xOffset) {
/* 168 */     this.xOffset = xOffset;
/* 169 */     fireChangeEvent();
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
/*     */   public void setYOffset(double yOffset) {
/* 181 */     this.yOffset = yOffset;
/* 182 */     fireChangeEvent();
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
/* 194 */     return this.wallPaint;
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
/* 207 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 208 */     this.wallPaint = paint;
/* 209 */     fireChangeEvent();
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
/*     */   public void drawBackground(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea) {
/* 223 */     float x0 = (float)dataArea.getX();
/* 224 */     float x1 = x0 + (float)Math.abs(this.xOffset);
/* 225 */     float x3 = (float)dataArea.getMaxX();
/* 226 */     float x2 = x3 - (float)Math.abs(this.xOffset);
/*     */     
/* 228 */     float y0 = (float)dataArea.getMaxY();
/* 229 */     float y1 = y0 - (float)Math.abs(this.yOffset);
/* 230 */     float y3 = (float)dataArea.getMinY();
/* 231 */     float y2 = y3 + (float)Math.abs(this.yOffset);
/*     */     
/* 233 */     GeneralPath clip = new GeneralPath();
/* 234 */     clip.moveTo(x0, y0);
/* 235 */     clip.lineTo(x0, y2);
/* 236 */     clip.lineTo(x1, y3);
/* 237 */     clip.lineTo(x3, y3);
/* 238 */     clip.lineTo(x3, y1);
/* 239 */     clip.lineTo(x2, y0);
/* 240 */     clip.closePath();
/*     */     
/* 242 */     Composite originalComposite = g2.getComposite();
/* 243 */     g2.setComposite(AlphaComposite.getInstance(3, plot
/* 244 */           .getBackgroundAlpha()));
/*     */ 
/*     */     
/* 247 */     Paint backgroundPaint = plot.getBackgroundPaint();
/* 248 */     if (backgroundPaint != null) {
/* 249 */       g2.setPaint(backgroundPaint);
/* 250 */       g2.fill(clip);
/*     */     } 
/*     */     
/* 253 */     GeneralPath leftWall = new GeneralPath();
/* 254 */     leftWall.moveTo(x0, y0);
/* 255 */     leftWall.lineTo(x0, y2);
/* 256 */     leftWall.lineTo(x1, y3);
/* 257 */     leftWall.lineTo(x1, y1);
/* 258 */     leftWall.closePath();
/* 259 */     g2.setPaint(getWallPaint());
/* 260 */     g2.fill(leftWall);
/*     */     
/* 262 */     GeneralPath bottomWall = new GeneralPath();
/* 263 */     bottomWall.moveTo(x0, y0);
/* 264 */     bottomWall.lineTo(x1, y1);
/* 265 */     bottomWall.lineTo(x3, y1);
/* 266 */     bottomWall.lineTo(x2, y0);
/* 267 */     bottomWall.closePath();
/* 268 */     g2.setPaint(getWallPaint());
/* 269 */     g2.fill(bottomWall);
/*     */ 
/*     */     
/* 272 */     g2.setPaint(Color.lightGray);
/* 273 */     Line2D corner = new Line2D.Double(x0, y0, x1, y1);
/* 274 */     g2.draw(corner);
/* 275 */     corner.setLine(x1, y1, x1, y3);
/* 276 */     g2.draw(corner);
/* 277 */     corner.setLine(x1, y1, x3, y1);
/* 278 */     g2.draw(corner);
/*     */ 
/*     */     
/* 281 */     Image backgroundImage = plot.getBackgroundImage();
/* 282 */     if (backgroundImage != null) {
/*     */ 
/*     */ 
/*     */       
/* 286 */       Rectangle2D adjusted = new Rectangle2D.Double(dataArea.getX() + getXOffset(), dataArea.getY(), dataArea.getWidth() - getXOffset(), dataArea.getHeight() - getYOffset());
/* 287 */       plot.drawBackgroundImage(g2, adjusted);
/*     */     } 
/*     */     
/* 290 */     g2.setComposite(originalComposite);
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
/* 305 */     float x0 = (float)dataArea.getX();
/* 306 */     float x1 = x0 + (float)Math.abs(this.xOffset);
/* 307 */     float x3 = (float)dataArea.getMaxX();
/* 308 */     float x2 = x3 - (float)Math.abs(this.xOffset);
/*     */     
/* 310 */     float y0 = (float)dataArea.getMaxY();
/* 311 */     float y1 = y0 - (float)Math.abs(this.yOffset);
/* 312 */     float y3 = (float)dataArea.getMinY();
/* 313 */     float y2 = y3 + (float)Math.abs(this.yOffset);
/*     */     
/* 315 */     GeneralPath clip = new GeneralPath();
/* 316 */     clip.moveTo(x0, y0);
/* 317 */     clip.lineTo(x0, y2);
/* 318 */     clip.lineTo(x1, y3);
/* 319 */     clip.lineTo(x3, y3);
/* 320 */     clip.lineTo(x3, y1);
/* 321 */     clip.lineTo(x2, y0);
/* 322 */     clip.closePath();
/*     */ 
/*     */     
/* 325 */     Stroke outlineStroke = plot.getOutlineStroke();
/* 326 */     Paint outlinePaint = plot.getOutlinePaint();
/* 327 */     if (outlineStroke != null && outlinePaint != null) {
/* 328 */       g2.setStroke(outlineStroke);
/* 329 */       g2.setPaint(outlinePaint);
/* 330 */       g2.draw(clip);
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
/* 349 */     Line2D line1 = null;
/* 350 */     Line2D line2 = null;
/* 351 */     PlotOrientation orientation = plot.getOrientation();
/* 352 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 353 */       double y0 = value;
/* 354 */       double y1 = value - getYOffset();
/* 355 */       double x0 = dataArea.getMinX();
/* 356 */       double x1 = x0 + getXOffset();
/* 357 */       double x2 = dataArea.getMaxX();
/* 358 */       line1 = new Line2D.Double(x0, y0, x1, y1);
/* 359 */       line2 = new Line2D.Double(x1, y1, x2, y1);
/*     */     }
/* 361 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 362 */       double x0 = value;
/* 363 */       double x1 = value + getXOffset();
/* 364 */       double y0 = dataArea.getMaxY();
/* 365 */       double y1 = y0 - getYOffset();
/* 366 */       double y2 = dataArea.getMinY();
/* 367 */       line1 = new Line2D.Double(x0, y0, x1, y1);
/* 368 */       line2 = new Line2D.Double(x1, y1, x1, y2);
/*     */     } 
/* 370 */     g2.setPaint(plot.getDomainGridlinePaint());
/* 371 */     g2.setStroke(plot.getDomainGridlineStroke());
/* 372 */     g2.draw(line1);
/* 373 */     g2.draw(line2);
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
/* 392 */     Range range = axis.getRange();
/*     */     
/* 394 */     if (!range.contains(value)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 401 */     Rectangle2D adjusted = new Rectangle2D.Double(dataArea.getX(), dataArea.getY() + getYOffset(), dataArea.getWidth() - getXOffset(), dataArea.getHeight() - getYOffset());
/*     */     
/* 403 */     Line2D line1 = null;
/* 404 */     Line2D line2 = null;
/* 405 */     PlotOrientation orientation = plot.getOrientation();
/* 406 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 407 */       double x0 = axis.valueToJava2D(value, adjusted, plot
/* 408 */           .getRangeAxisEdge());
/* 409 */       double x1 = x0 + getXOffset();
/* 410 */       double y0 = dataArea.getMaxY();
/* 411 */       double y1 = y0 - getYOffset();
/* 412 */       double y2 = dataArea.getMinY();
/* 413 */       line1 = new Line2D.Double(x0, y0, x1, y1);
/* 414 */       line2 = new Line2D.Double(x1, y1, x1, y2);
/*     */     }
/* 416 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 417 */       double y0 = axis.valueToJava2D(value, adjusted, plot
/* 418 */           .getRangeAxisEdge());
/* 419 */       double y1 = y0 - getYOffset();
/* 420 */       double x0 = dataArea.getMinX();
/* 421 */       double x1 = x0 + getXOffset();
/* 422 */       double x2 = dataArea.getMaxX();
/* 423 */       line1 = new Line2D.Double(x0, y0, x1, y1);
/* 424 */       line2 = new Line2D.Double(x1, y1, x2, y1);
/*     */     } 
/* 426 */     g2.setPaint(plot.getRangeGridlinePaint());
/* 427 */     g2.setStroke(plot.getRangeGridlineStroke());
/* 428 */     g2.draw(line1);
/* 429 */     g2.draw(line2);
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
/* 449 */     Rectangle2D adjusted = new Rectangle2D.Double(dataArea.getX(), dataArea.getY() + getYOffset(), dataArea.getWidth() - getXOffset(), dataArea.getHeight() - getYOffset());
/*     */     
/* 451 */     if (marker instanceof ValueMarker) {
/* 452 */       ValueMarker vm = (ValueMarker)marker;
/* 453 */       double value = vm.getValue();
/* 454 */       Range range = axis.getRange();
/* 455 */       if (!range.contains(value)) {
/*     */         return;
/*     */       }
/*     */       
/* 459 */       GeneralPath path = null;
/* 460 */       PlotOrientation orientation = plot.getOrientation();
/* 461 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 462 */         float x = (float)axis.valueToJava2D(value, adjusted, plot
/* 463 */             .getRangeAxisEdge());
/* 464 */         float y = (float)adjusted.getMaxY();
/* 465 */         path = new GeneralPath();
/* 466 */         path.moveTo(x, y);
/* 467 */         path.lineTo((float)(x + getXOffset()), y - 
/* 468 */             (float)getYOffset());
/* 469 */         path.lineTo((float)(x + getXOffset()), 
/* 470 */             (float)(adjusted.getMinY() - getYOffset()));
/* 471 */         path.lineTo(x, (float)adjusted.getMinY());
/* 472 */         path.closePath();
/*     */       }
/* 474 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 475 */         float y = (float)axis.valueToJava2D(value, adjusted, plot
/* 476 */             .getRangeAxisEdge());
/* 477 */         float x = (float)dataArea.getX();
/* 478 */         path = new GeneralPath();
/* 479 */         path.moveTo(x, y);
/* 480 */         path.lineTo(x + (float)this.xOffset, y - (float)this.yOffset);
/* 481 */         path.lineTo((float)(adjusted.getMaxX() + this.xOffset), y - (float)this.yOffset);
/*     */         
/* 483 */         path.lineTo((float)adjusted.getMaxX(), y);
/* 484 */         path.closePath();
/*     */       } 
/* 486 */       g2.setPaint(marker.getPaint());
/* 487 */       g2.fill(path);
/* 488 */       g2.setPaint(marker.getOutlinePaint());
/* 489 */       g2.draw(path);
/*     */     } else {
/*     */       
/* 492 */       super.drawRangeMarker(g2, plot, axis, marker, adjusted);
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
/*     */   public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {
/* 517 */     if (!getItemVisible(row, column)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 522 */     Number v = dataset.getValue(row, column);
/* 523 */     if (v == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 530 */     Rectangle2D adjusted = new Rectangle2D.Double(dataArea.getX(), dataArea.getY() + getYOffset(), dataArea.getWidth() - getXOffset(), dataArea.getHeight() - getYOffset());
/*     */     
/* 532 */     PlotOrientation orientation = plot.getOrientation();
/*     */ 
/*     */     
/* 535 */     double x1 = domainAxis.getCategoryMiddle(column, getColumnCount(), adjusted, plot
/* 536 */         .getDomainAxisEdge());
/* 537 */     double value = v.doubleValue();
/* 538 */     double y1 = rangeAxis.valueToJava2D(value, adjusted, plot
/* 539 */         .getRangeAxisEdge());
/*     */     
/* 541 */     Shape shape = getItemShape(row, column);
/* 542 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 543 */       shape = ShapeUtilities.createTranslatedShape(shape, y1, x1);
/*     */     }
/* 545 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 546 */       shape = ShapeUtilities.createTranslatedShape(shape, x1, y1);
/*     */     } 
/*     */     
/* 549 */     if (pass == 0 && getItemLineVisible(row, column) && 
/* 550 */       column != 0) {
/*     */       
/* 552 */       Number previousValue = dataset.getValue(row, column - 1);
/* 553 */       if (previousValue != null) {
/*     */ 
/*     */         
/* 556 */         double previous = previousValue.doubleValue();
/* 557 */         double x0 = domainAxis.getCategoryMiddle(column - 1, 
/* 558 */             getColumnCount(), adjusted, plot
/* 559 */             .getDomainAxisEdge());
/* 560 */         double y0 = rangeAxis.valueToJava2D(previous, adjusted, plot
/* 561 */             .getRangeAxisEdge());
/*     */         
/* 563 */         double x2 = x0 + getXOffset();
/* 564 */         double y2 = y0 - getYOffset();
/* 565 */         double x3 = x1 + getXOffset();
/* 566 */         double y3 = y1 - getYOffset();
/*     */         
/* 568 */         GeneralPath clip = new GeneralPath();
/*     */         
/* 570 */         if (orientation == PlotOrientation.HORIZONTAL) {
/* 571 */           clip.moveTo((float)y0, (float)x0);
/* 572 */           clip.lineTo((float)y1, (float)x1);
/* 573 */           clip.lineTo((float)y3, (float)x3);
/* 574 */           clip.lineTo((float)y2, (float)x2);
/* 575 */           clip.lineTo((float)y0, (float)x0);
/* 576 */           clip.closePath();
/*     */         }
/* 578 */         else if (orientation == PlotOrientation.VERTICAL) {
/* 579 */           clip.moveTo((float)x0, (float)y0);
/* 580 */           clip.lineTo((float)x1, (float)y1);
/* 581 */           clip.lineTo((float)x3, (float)y3);
/* 582 */           clip.lineTo((float)x2, (float)y2);
/* 583 */           clip.lineTo((float)x0, (float)y0);
/* 584 */           clip.closePath();
/*     */         } 
/*     */         
/* 587 */         g2.setPaint(getItemPaint(row, column));
/* 588 */         g2.fill(clip);
/* 589 */         g2.setStroke(getItemOutlineStroke(row, column));
/* 590 */         g2.setPaint(getItemOutlinePaint(row, column));
/* 591 */         g2.draw(clip);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 597 */     if (pass == 1 && isItemLabelVisible(row, column)) {
/* 598 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 599 */         drawItemLabel(g2, orientation, dataset, row, column, y1, x1, (value < 0.0D));
/*     */       
/*     */       }
/* 602 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 603 */         drawItemLabel(g2, orientation, dataset, row, column, x1, y1, (value < 0.0D));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 609 */     EntityCollection entities = state.getEntityCollection();
/* 610 */     if (entities != null) {
/* 611 */       addItemEntity(entities, dataset, row, column, shape);
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
/* 625 */     if (obj == this) {
/* 626 */       return true;
/*     */     }
/* 628 */     if (!(obj instanceof LineRenderer3D)) {
/* 629 */       return false;
/*     */     }
/* 631 */     LineRenderer3D that = (LineRenderer3D)obj;
/* 632 */     if (this.xOffset != that.xOffset) {
/* 633 */       return false;
/*     */     }
/* 635 */     if (this.yOffset != that.yOffset) {
/* 636 */       return false;
/*     */     }
/* 638 */     if (!PaintUtilities.equal(this.wallPaint, that.wallPaint)) {
/* 639 */       return false;
/*     */     }
/* 641 */     return super.equals(obj);
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
/* 652 */     stream.defaultWriteObject();
/* 653 */     SerialUtilities.writePaint(this.wallPaint, stream);
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
/* 666 */     stream.defaultReadObject();
/* 667 */     this.wallPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/LineRenderer3D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */