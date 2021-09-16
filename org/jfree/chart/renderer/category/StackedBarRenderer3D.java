/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.event.RendererChangeEvent;
/*     */ import org.jfree.chart.labels.CategoryItemLabelGenerator;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.util.PaintAlpha;
/*     */ import org.jfree.data.DataUtilities;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.Values2D;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.general.DatasetUtilities;
/*     */ import org.jfree.util.BooleanUtilities;
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
/*     */ public class StackedBarRenderer3D
/*     */   extends BarRenderer3D
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5832945916493247123L;
/*     */   private boolean renderAsPercentages;
/*     */   private boolean ignoreZeroValues;
/*     */   
/*     */   public StackedBarRenderer3D() {
/* 156 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackedBarRenderer3D(double xOffset, double yOffset) {
/* 166 */     super(xOffset, yOffset);
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
/*     */   public StackedBarRenderer3D(boolean renderAsPercentages) {
/* 179 */     this.renderAsPercentages = renderAsPercentages;
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
/*     */   public StackedBarRenderer3D(double xOffset, double yOffset, boolean renderAsPercentages) {
/* 194 */     super(xOffset, yOffset);
/* 195 */     this.renderAsPercentages = renderAsPercentages;
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
/*     */   public boolean getRenderAsPercentages() {
/* 208 */     return this.renderAsPercentages;
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
/*     */   public void setRenderAsPercentages(boolean asPercentages) {
/* 221 */     this.renderAsPercentages = asPercentages;
/* 222 */     fireChangeEvent();
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
/*     */   public boolean getIgnoreZeroValues() {
/* 234 */     return this.ignoreZeroValues;
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
/*     */   public void setIgnoreZeroValues(boolean ignore) {
/* 247 */     this.ignoreZeroValues = ignore;
/* 248 */     notifyListeners(new RendererChangeEvent(this));
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
/*     */   public Range findRangeBounds(CategoryDataset dataset) {
/* 261 */     if (dataset == null) {
/* 262 */       return null;
/*     */     }
/* 264 */     if (this.renderAsPercentages) {
/* 265 */       return new Range(0.0D, 1.0D);
/*     */     }
/* 267 */     return DatasetUtilities.findStackedRangeBounds(dataset);
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
/*     */   protected void calculateBarWidth(CategoryPlot plot, Rectangle2D dataArea, int rendererIndex, CategoryItemRendererState state) {
/* 283 */     CategoryAxis domainAxis = getDomainAxis(plot, rendererIndex);
/* 284 */     CategoryDataset data = plot.getDataset(rendererIndex);
/* 285 */     if (data != null) {
/* 286 */       PlotOrientation orientation = plot.getOrientation();
/* 287 */       double space = 0.0D;
/* 288 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 289 */         space = dataArea.getHeight();
/*     */       }
/* 291 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 292 */         space = dataArea.getWidth();
/*     */       } 
/* 294 */       double maxWidth = space * getMaximumBarWidth();
/* 295 */       int columns = data.getColumnCount();
/* 296 */       double categoryMargin = 0.0D;
/* 297 */       if (columns > 1) {
/* 298 */         categoryMargin = domainAxis.getCategoryMargin();
/*     */       }
/*     */ 
/*     */       
/* 302 */       double used = space * (1.0D - domainAxis.getLowerMargin() - domainAxis.getUpperMargin() - categoryMargin);
/*     */       
/* 304 */       if (columns > 0) {
/* 305 */         state.setBarWidth(Math.min(used / columns, maxWidth));
/*     */       } else {
/*     */         
/* 308 */         state.setBarWidth(Math.min(used, maxWidth));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List createStackedValueList(CategoryDataset dataset, Comparable category, double base, boolean asPercentages) {
/* 333 */     int[] rows = new int[dataset.getRowCount()];
/* 334 */     for (int i = 0; i < rows.length; i++) {
/* 335 */       rows[i] = i;
/*     */     }
/* 337 */     return createStackedValueList(dataset, category, rows, base, asPercentages);
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
/*     */   protected List createStackedValueList(CategoryDataset dataset, Comparable category, int[] includedRows, double base, boolean asPercentages) {
/* 360 */     List<Object[]> result = new ArrayList();
/* 361 */     double posBase = base;
/* 362 */     double negBase = base;
/* 363 */     double total = 0.0D;
/* 364 */     if (asPercentages) {
/* 365 */       total = DataUtilities.calculateColumnTotal((Values2D)dataset, dataset
/* 366 */           .getColumnIndex(category), includedRows);
/*     */     }
/*     */     
/* 369 */     int baseIndex = -1;
/* 370 */     int rowCount = includedRows.length;
/* 371 */     for (int i = 0; i < rowCount; i++) {
/* 372 */       int r = includedRows[i];
/* 373 */       Number n = dataset.getValue(dataset.getRowKey(r), category);
/* 374 */       if (n != null) {
/*     */ 
/*     */         
/* 377 */         double v = n.doubleValue();
/* 378 */         if (asPercentages) {
/* 379 */           v /= total;
/*     */         }
/* 381 */         if (v > 0.0D || (!this.ignoreZeroValues && v >= 0.0D)) {
/* 382 */           if (baseIndex < 0) {
/* 383 */             result.add(new Object[] { null, new Double(base) });
/* 384 */             baseIndex = 0;
/*     */           } 
/* 386 */           posBase += v;
/* 387 */           result.add(new Object[] { new Integer(r), new Double(posBase) });
/*     */         }
/* 389 */         else if (v < 0.0D) {
/* 390 */           if (baseIndex < 0) {
/* 391 */             result.add(new Object[] { null, new Double(base) });
/* 392 */             baseIndex = 0;
/*     */           } 
/* 394 */           negBase += v;
/* 395 */           result.add(0, new Object[] { new Integer(-r - 1), new Double(negBase) });
/*     */           
/* 397 */           baseIndex++;
/*     */         } 
/*     */       } 
/* 400 */     }  return result;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {
/* 428 */     if (row < dataset.getRowCount() - 1) {
/*     */       return;
/*     */     }
/* 431 */     Comparable category = dataset.getColumnKey(column);
/*     */     
/* 433 */     List values = createStackedValueList(dataset, dataset
/* 434 */         .getColumnKey(column), state.getVisibleSeriesArray(), 
/* 435 */         getBase(), this.renderAsPercentages);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 440 */     Rectangle2D adjusted = new Rectangle2D.Double(dataArea.getX(), dataArea.getY() + getYOffset(), dataArea.getWidth() - getXOffset(), dataArea.getHeight() - getYOffset());
/*     */ 
/*     */     
/* 443 */     PlotOrientation orientation = plot.getOrientation();
/*     */ 
/*     */     
/* 446 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 447 */       drawStackHorizontal(values, category, g2, state, adjusted, plot, domainAxis, rangeAxis, dataset);
/*     */     }
/*     */     else {
/*     */       
/* 451 */       drawStackVertical(values, category, g2, state, adjusted, plot, domainAxis, rangeAxis, dataset);
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
/*     */   
/*     */   protected void drawStackHorizontal(List<Object[]> values, Comparable category, Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset) {
/* 478 */     int column = dataset.getColumnIndex(category);
/*     */ 
/*     */     
/* 481 */     double barX0 = domainAxis.getCategoryMiddle(column, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge()) - state.getBarWidth() / 2.0D;
/* 482 */     double barW = state.getBarWidth();
/*     */ 
/*     */ 
/*     */     
/* 486 */     List<Object[]> itemLabelList = new ArrayList();
/*     */ 
/*     */     
/* 489 */     boolean inverted = rangeAxis.isInverted();
/* 490 */     int blockCount = values.size() - 1;
/* 491 */     for (int k = 0; k < blockCount; k++) {
/* 492 */       int series, index = inverted ? (blockCount - k - 1) : k;
/* 493 */       Object[] prev = values.get(index);
/* 494 */       Object[] curr = values.get(index + 1);
/*     */       
/* 496 */       if (curr[0] == null) {
/* 497 */         series = -((Integer)prev[0]).intValue() - 1;
/*     */       } else {
/*     */         
/* 500 */         series = ((Integer)curr[0]).intValue();
/* 501 */         if (series < 0) {
/* 502 */           series = -((Integer)prev[0]).intValue() - 1;
/*     */         }
/*     */       } 
/* 505 */       double v0 = ((Double)prev[1]).doubleValue();
/* 506 */       double vv0 = rangeAxis.valueToJava2D(v0, dataArea, plot
/* 507 */           .getRangeAxisEdge());
/*     */       
/* 509 */       double v1 = ((Double)curr[1]).doubleValue();
/* 510 */       double vv1 = rangeAxis.valueToJava2D(v1, dataArea, plot
/* 511 */           .getRangeAxisEdge());
/*     */       
/* 513 */       Shape[] faces = createHorizontalBlock(barX0, barW, vv0, vv1, inverted);
/*     */       
/* 515 */       Paint fillPaint = getItemPaint(series, column);
/* 516 */       Paint fillPaintDark = PaintAlpha.darker(fillPaint);
/* 517 */       boolean drawOutlines = isDrawBarOutline();
/* 518 */       Paint outlinePaint = fillPaint;
/* 519 */       if (drawOutlines) {
/* 520 */         outlinePaint = getItemOutlinePaint(series, column);
/* 521 */         g2.setStroke(getItemOutlineStroke(series, column));
/*     */       } 
/* 523 */       for (int f = 0; f < 6; f++) {
/* 524 */         if (f == 5) {
/* 525 */           g2.setPaint(fillPaint);
/*     */         } else {
/*     */           
/* 528 */           g2.setPaint(fillPaintDark);
/*     */         } 
/* 530 */         g2.fill(faces[f]);
/* 531 */         if (drawOutlines) {
/* 532 */           g2.setPaint(outlinePaint);
/* 533 */           g2.draw(faces[f]);
/*     */         } 
/*     */       } 
/*     */       
/* 537 */       itemLabelList.add(new Object[] { new Integer(series), faces[5]
/* 538 */             .getBounds2D(), 
/* 539 */             BooleanUtilities.valueOf((v0 < getBase())) });
/*     */ 
/*     */       
/* 542 */       EntityCollection entities = state.getEntityCollection();
/* 543 */       if (entities != null) {
/* 544 */         addItemEntity(entities, dataset, series, column, faces[5]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 549 */     for (int i = 0; i < itemLabelList.size(); i++) {
/* 550 */       Object[] record = itemLabelList.get(i);
/* 551 */       int series = ((Integer)record[0]).intValue();
/* 552 */       Rectangle2D bar = (Rectangle2D)record[1];
/* 553 */       boolean neg = ((Boolean)record[2]).booleanValue();
/*     */       
/* 555 */       CategoryItemLabelGenerator generator = getItemLabelGenerator(series, column);
/* 556 */       if (generator != null && isItemLabelVisible(series, column)) {
/* 557 */         drawItemLabel(g2, dataset, series, column, plot, generator, bar, neg);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Shape[] createHorizontalBlock(double x0, double width, double y0, double y1, boolean inverted) {
/* 579 */     Shape[] result = new Shape[6];
/* 580 */     Point2D p00 = new Point2D.Double(y0, x0);
/* 581 */     Point2D p01 = new Point2D.Double(y0, x0 + width);
/*     */     
/* 583 */     Point2D p02 = new Point2D.Double(p01.getX() + getXOffset(), p01.getY() - getYOffset());
/*     */     
/* 585 */     Point2D p03 = new Point2D.Double(p00.getX() + getXOffset(), p00.getY() - getYOffset());
/*     */     
/* 587 */     Point2D p0 = new Point2D.Double(y1, x0);
/* 588 */     Point2D p1 = new Point2D.Double(y1, x0 + width);
/*     */     
/* 590 */     Point2D p2 = new Point2D.Double(p1.getX() + getXOffset(), p1.getY() - getYOffset());
/*     */     
/* 592 */     Point2D p3 = new Point2D.Double(p0.getX() + getXOffset(), p0.getY() - getYOffset());
/*     */     
/* 594 */     GeneralPath bottom = new GeneralPath();
/* 595 */     bottom.moveTo((float)p1.getX(), (float)p1.getY());
/* 596 */     bottom.lineTo((float)p01.getX(), (float)p01.getY());
/* 597 */     bottom.lineTo((float)p02.getX(), (float)p02.getY());
/* 598 */     bottom.lineTo((float)p2.getX(), (float)p2.getY());
/* 599 */     bottom.closePath();
/*     */     
/* 601 */     GeneralPath top = new GeneralPath();
/* 602 */     top.moveTo((float)p0.getX(), (float)p0.getY());
/* 603 */     top.lineTo((float)p00.getX(), (float)p00.getY());
/* 604 */     top.lineTo((float)p03.getX(), (float)p03.getY());
/* 605 */     top.lineTo((float)p3.getX(), (float)p3.getY());
/* 606 */     top.closePath();
/*     */     
/* 608 */     GeneralPath back = new GeneralPath();
/* 609 */     back.moveTo((float)p2.getX(), (float)p2.getY());
/* 610 */     back.lineTo((float)p02.getX(), (float)p02.getY());
/* 611 */     back.lineTo((float)p03.getX(), (float)p03.getY());
/* 612 */     back.lineTo((float)p3.getX(), (float)p3.getY());
/* 613 */     back.closePath();
/*     */     
/* 615 */     GeneralPath front = new GeneralPath();
/* 616 */     front.moveTo((float)p0.getX(), (float)p0.getY());
/* 617 */     front.lineTo((float)p1.getX(), (float)p1.getY());
/* 618 */     front.lineTo((float)p01.getX(), (float)p01.getY());
/* 619 */     front.lineTo((float)p00.getX(), (float)p00.getY());
/* 620 */     front.closePath();
/*     */     
/* 622 */     GeneralPath left = new GeneralPath();
/* 623 */     left.moveTo((float)p0.getX(), (float)p0.getY());
/* 624 */     left.lineTo((float)p1.getX(), (float)p1.getY());
/* 625 */     left.lineTo((float)p2.getX(), (float)p2.getY());
/* 626 */     left.lineTo((float)p3.getX(), (float)p3.getY());
/* 627 */     left.closePath();
/*     */     
/* 629 */     GeneralPath right = new GeneralPath();
/* 630 */     right.moveTo((float)p00.getX(), (float)p00.getY());
/* 631 */     right.lineTo((float)p01.getX(), (float)p01.getY());
/* 632 */     right.lineTo((float)p02.getX(), (float)p02.getY());
/* 633 */     right.lineTo((float)p03.getX(), (float)p03.getY());
/* 634 */     right.closePath();
/* 635 */     result[0] = bottom;
/* 636 */     result[1] = back;
/* 637 */     if (inverted) {
/* 638 */       result[2] = right;
/* 639 */       result[3] = left;
/*     */     } else {
/*     */       
/* 642 */       result[2] = left;
/* 643 */       result[3] = right;
/*     */     } 
/* 645 */     result[4] = top;
/* 646 */     result[5] = front;
/* 647 */     return result;
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
/*     */   protected void drawStackVertical(List<Object[]> values, Comparable category, Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset) {
/* 671 */     int column = dataset.getColumnIndex(category);
/*     */ 
/*     */     
/* 674 */     double barX0 = domainAxis.getCategoryMiddle(column, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge()) - state.getBarWidth() / 2.0D;
/* 675 */     double barW = state.getBarWidth();
/*     */ 
/*     */ 
/*     */     
/* 679 */     List<Object[]> itemLabelList = new ArrayList();
/*     */ 
/*     */     
/* 682 */     boolean inverted = rangeAxis.isInverted();
/* 683 */     int blockCount = values.size() - 1;
/* 684 */     for (int k = 0; k < blockCount; k++) {
/* 685 */       int series, index = inverted ? (blockCount - k - 1) : k;
/* 686 */       Object[] prev = values.get(index);
/* 687 */       Object[] curr = values.get(index + 1);
/*     */       
/* 689 */       if (curr[0] == null) {
/* 690 */         series = -((Integer)prev[0]).intValue() - 1;
/*     */       } else {
/*     */         
/* 693 */         series = ((Integer)curr[0]).intValue();
/* 694 */         if (series < 0) {
/* 695 */           series = -((Integer)prev[0]).intValue() - 1;
/*     */         }
/*     */       } 
/* 698 */       double v0 = ((Double)prev[1]).doubleValue();
/* 699 */       double vv0 = rangeAxis.valueToJava2D(v0, dataArea, plot
/* 700 */           .getRangeAxisEdge());
/*     */       
/* 702 */       double v1 = ((Double)curr[1]).doubleValue();
/* 703 */       double vv1 = rangeAxis.valueToJava2D(v1, dataArea, plot
/* 704 */           .getRangeAxisEdge());
/*     */       
/* 706 */       Shape[] faces = createVerticalBlock(barX0, barW, vv0, vv1, inverted);
/*     */       
/* 708 */       Paint fillPaint = getItemPaint(series, column);
/* 709 */       Paint fillPaintDark = PaintAlpha.darker(fillPaint);
/* 710 */       boolean drawOutlines = isDrawBarOutline();
/* 711 */       Paint outlinePaint = fillPaint;
/* 712 */       if (drawOutlines) {
/* 713 */         outlinePaint = getItemOutlinePaint(series, column);
/* 714 */         g2.setStroke(getItemOutlineStroke(series, column));
/*     */       } 
/*     */       
/* 717 */       for (int f = 0; f < 6; f++) {
/* 718 */         if (f == 5) {
/* 719 */           g2.setPaint(fillPaint);
/*     */         } else {
/*     */           
/* 722 */           g2.setPaint(fillPaintDark);
/*     */         } 
/* 724 */         g2.fill(faces[f]);
/* 725 */         if (drawOutlines) {
/* 726 */           g2.setPaint(outlinePaint);
/* 727 */           g2.draw(faces[f]);
/*     */         } 
/*     */       } 
/*     */       
/* 731 */       itemLabelList.add(new Object[] { new Integer(series), faces[5]
/* 732 */             .getBounds2D(), 
/* 733 */             BooleanUtilities.valueOf((v0 < getBase())) });
/*     */ 
/*     */       
/* 736 */       EntityCollection entities = state.getEntityCollection();
/* 737 */       if (entities != null) {
/* 738 */         addItemEntity(entities, dataset, series, column, faces[5]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 743 */     for (int i = 0; i < itemLabelList.size(); i++) {
/* 744 */       Object[] record = itemLabelList.get(i);
/* 745 */       int series = ((Integer)record[0]).intValue();
/* 746 */       Rectangle2D bar = (Rectangle2D)record[1];
/* 747 */       boolean neg = ((Boolean)record[2]).booleanValue();
/*     */       
/* 749 */       CategoryItemLabelGenerator generator = getItemLabelGenerator(series, column);
/* 750 */       if (generator != null && isItemLabelVisible(series, column)) {
/* 751 */         drawItemLabel(g2, dataset, series, column, plot, generator, bar, neg);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Shape[] createVerticalBlock(double x0, double width, double y0, double y1, boolean inverted) {
/* 773 */     Shape[] result = new Shape[6];
/* 774 */     Point2D p00 = new Point2D.Double(x0, y0);
/* 775 */     Point2D p01 = new Point2D.Double(x0 + width, y0);
/*     */     
/* 777 */     Point2D p02 = new Point2D.Double(p01.getX() + getXOffset(), p01.getY() - getYOffset());
/*     */     
/* 779 */     Point2D p03 = new Point2D.Double(p00.getX() + getXOffset(), p00.getY() - getYOffset());
/*     */ 
/*     */     
/* 782 */     Point2D p0 = new Point2D.Double(x0, y1);
/* 783 */     Point2D p1 = new Point2D.Double(x0 + width, y1);
/*     */     
/* 785 */     Point2D p2 = new Point2D.Double(p1.getX() + getXOffset(), p1.getY() - getYOffset());
/*     */     
/* 787 */     Point2D p3 = new Point2D.Double(p0.getX() + getXOffset(), p0.getY() - getYOffset());
/*     */     
/* 789 */     GeneralPath right = new GeneralPath();
/* 790 */     right.moveTo((float)p1.getX(), (float)p1.getY());
/* 791 */     right.lineTo((float)p01.getX(), (float)p01.getY());
/* 792 */     right.lineTo((float)p02.getX(), (float)p02.getY());
/* 793 */     right.lineTo((float)p2.getX(), (float)p2.getY());
/* 794 */     right.closePath();
/*     */     
/* 796 */     GeneralPath left = new GeneralPath();
/* 797 */     left.moveTo((float)p0.getX(), (float)p0.getY());
/* 798 */     left.lineTo((float)p00.getX(), (float)p00.getY());
/* 799 */     left.lineTo((float)p03.getX(), (float)p03.getY());
/* 800 */     left.lineTo((float)p3.getX(), (float)p3.getY());
/* 801 */     left.closePath();
/*     */     
/* 803 */     GeneralPath back = new GeneralPath();
/* 804 */     back.moveTo((float)p2.getX(), (float)p2.getY());
/* 805 */     back.lineTo((float)p02.getX(), (float)p02.getY());
/* 806 */     back.lineTo((float)p03.getX(), (float)p03.getY());
/* 807 */     back.lineTo((float)p3.getX(), (float)p3.getY());
/* 808 */     back.closePath();
/*     */     
/* 810 */     GeneralPath front = new GeneralPath();
/* 811 */     front.moveTo((float)p0.getX(), (float)p0.getY());
/* 812 */     front.lineTo((float)p1.getX(), (float)p1.getY());
/* 813 */     front.lineTo((float)p01.getX(), (float)p01.getY());
/* 814 */     front.lineTo((float)p00.getX(), (float)p00.getY());
/* 815 */     front.closePath();
/*     */     
/* 817 */     GeneralPath top = new GeneralPath();
/* 818 */     top.moveTo((float)p0.getX(), (float)p0.getY());
/* 819 */     top.lineTo((float)p1.getX(), (float)p1.getY());
/* 820 */     top.lineTo((float)p2.getX(), (float)p2.getY());
/* 821 */     top.lineTo((float)p3.getX(), (float)p3.getY());
/* 822 */     top.closePath();
/*     */     
/* 824 */     GeneralPath bottom = new GeneralPath();
/* 825 */     bottom.moveTo((float)p00.getX(), (float)p00.getY());
/* 826 */     bottom.lineTo((float)p01.getX(), (float)p01.getY());
/* 827 */     bottom.lineTo((float)p02.getX(), (float)p02.getY());
/* 828 */     bottom.lineTo((float)p03.getX(), (float)p03.getY());
/* 829 */     bottom.closePath();
/*     */     
/* 831 */     result[0] = bottom;
/* 832 */     result[1] = back;
/* 833 */     result[2] = left;
/* 834 */     result[3] = right;
/* 835 */     result[4] = top;
/* 836 */     result[5] = front;
/* 837 */     if (inverted) {
/* 838 */       result[0] = top;
/* 839 */       result[4] = bottom;
/*     */     } 
/* 841 */     return result;
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
/* 853 */     if (obj == this) {
/* 854 */       return true;
/*     */     }
/* 856 */     if (!(obj instanceof StackedBarRenderer3D)) {
/* 857 */       return false;
/*     */     }
/* 859 */     StackedBarRenderer3D that = (StackedBarRenderer3D)obj;
/* 860 */     if (this.renderAsPercentages != that.getRenderAsPercentages()) {
/* 861 */       return false;
/*     */     }
/* 863 */     if (this.ignoreZeroValues != that.ignoreZeroValues) {
/* 864 */       return false;
/*     */     }
/* 866 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 876 */     int hash = super.hashCode();
/* 877 */     hash = HashUtilities.hashCode(hash, this.renderAsPercentages);
/* 878 */     hash = HashUtilities.hashCode(hash, this.ignoreZeroValues);
/* 879 */     return hash;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/StackedBarRenderer3D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */