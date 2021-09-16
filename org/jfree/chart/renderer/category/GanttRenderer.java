/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.CategoryItemLabelGenerator;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.gantt.GanttCategoryDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.util.PaintUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GanttRenderer
/*     */   extends IntervalBarRenderer
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4010349116350119512L;
/*     */   private transient Paint completePaint;
/*     */   private transient Paint incompletePaint;
/*     */   private double startPercent;
/*     */   private double endPercent;
/*     */   
/*     */   public GanttRenderer() {
/* 119 */     setIncludeBaseInRange(false);
/* 120 */     this.completePaint = Color.green;
/* 121 */     this.incompletePaint = Color.red;
/* 122 */     this.startPercent = 0.35D;
/* 123 */     this.endPercent = 0.65D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getCompletePaint() {
/* 134 */     return this.completePaint;
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
/*     */   public void setCompletePaint(Paint paint) {
/* 146 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 147 */     this.completePaint = paint;
/* 148 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getIncompletePaint() {
/* 159 */     return this.incompletePaint;
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
/*     */   public void setIncompletePaint(Paint paint) {
/* 171 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 172 */     this.incompletePaint = paint;
/* 173 */     fireChangeEvent();
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
/*     */   public double getStartPercent() {
/* 185 */     return this.startPercent;
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
/*     */   public void setStartPercent(double percent) {
/* 198 */     this.startPercent = percent;
/* 199 */     fireChangeEvent();
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
/*     */   public double getEndPercent() {
/* 211 */     return this.endPercent;
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
/*     */   public void setEndPercent(double percent) {
/* 224 */     this.endPercent = percent;
/* 225 */     fireChangeEvent();
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
/*     */   public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {
/* 248 */     if (dataset instanceof GanttCategoryDataset) {
/* 249 */       GanttCategoryDataset gcd = (GanttCategoryDataset)dataset;
/* 250 */       drawTasks(g2, state, dataArea, plot, domainAxis, rangeAxis, gcd, row, column);
/*     */     }
/*     */     else {
/*     */       
/* 254 */       super.drawItem(g2, state, dataArea, plot, domainAxis, rangeAxis, dataset, row, column, pass);
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
/*     */ 
/*     */   
/*     */   protected void drawTasks(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, GanttCategoryDataset dataset, int row, int column) {
/* 283 */     int count = dataset.getSubIntervalCount(row, column);
/* 284 */     if (count == 0) {
/* 285 */       drawTask(g2, state, dataArea, plot, domainAxis, rangeAxis, dataset, row, column);
/*     */     }
/*     */ 
/*     */     
/* 289 */     PlotOrientation orientation = plot.getOrientation();
/* 290 */     for (int subinterval = 0; subinterval < count; subinterval++) {
/*     */       
/* 292 */       RectangleEdge rangeAxisLocation = plot.getRangeAxisEdge();
/*     */ 
/*     */       
/* 295 */       Number value0 = dataset.getStartValue(row, column, subinterval);
/* 296 */       if (value0 == null) {
/*     */         return;
/*     */       }
/* 299 */       double translatedValue0 = rangeAxis.valueToJava2D(value0
/* 300 */           .doubleValue(), dataArea, rangeAxisLocation);
/*     */ 
/*     */       
/* 303 */       Number value1 = dataset.getEndValue(row, column, subinterval);
/* 304 */       if (value1 == null) {
/*     */         return;
/*     */       }
/* 307 */       double translatedValue1 = rangeAxis.valueToJava2D(value1
/* 308 */           .doubleValue(), dataArea, rangeAxisLocation);
/*     */       
/* 310 */       if (translatedValue1 < translatedValue0) {
/* 311 */         double temp = translatedValue1;
/* 312 */         translatedValue1 = translatedValue0;
/* 313 */         translatedValue0 = temp;
/*     */       } 
/*     */       
/* 316 */       double rectStart = calculateBarW0(plot, plot.getOrientation(), dataArea, domainAxis, state, row, column);
/*     */       
/* 318 */       double rectLength = Math.abs(translatedValue1 - translatedValue0);
/* 319 */       double rectBreadth = state.getBarWidth();
/*     */ 
/*     */       
/* 322 */       Rectangle2D bar = null;
/* 323 */       RectangleEdge barBase = null;
/* 324 */       if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
/* 325 */         bar = new Rectangle2D.Double(translatedValue0, rectStart, rectLength, rectBreadth);
/*     */         
/* 327 */         barBase = RectangleEdge.LEFT;
/*     */       }
/* 329 */       else if (plot.getOrientation() == PlotOrientation.VERTICAL) {
/* 330 */         bar = new Rectangle2D.Double(rectStart, translatedValue0, rectBreadth, rectLength);
/*     */         
/* 332 */         barBase = RectangleEdge.BOTTOM;
/*     */       } 
/*     */       
/* 335 */       Rectangle2D completeBar = null;
/* 336 */       Rectangle2D incompleteBar = null;
/* 337 */       Number percent = dataset.getPercentComplete(row, column, subinterval);
/*     */       
/* 339 */       double start = getStartPercent();
/* 340 */       double end = getEndPercent();
/* 341 */       if (percent != null) {
/* 342 */         double p = percent.doubleValue();
/* 343 */         if (orientation == PlotOrientation.HORIZONTAL) {
/* 344 */           completeBar = new Rectangle2D.Double(translatedValue0, rectStart + start * rectBreadth, rectLength * p, rectBreadth * (end - start));
/*     */ 
/*     */           
/* 347 */           incompleteBar = new Rectangle2D.Double(translatedValue0 + rectLength * p, rectStart + start * rectBreadth, rectLength * (1.0D - p), rectBreadth * (end - start));
/*     */ 
/*     */         
/*     */         }
/* 351 */         else if (orientation == PlotOrientation.VERTICAL) {
/* 352 */           completeBar = new Rectangle2D.Double(rectStart + start * rectBreadth, translatedValue0 + rectLength * (1.0D - p), rectBreadth * (end - start), rectLength * p);
/*     */ 
/*     */ 
/*     */           
/* 356 */           incompleteBar = new Rectangle2D.Double(rectStart + start * rectBreadth, translatedValue0, rectBreadth * (end - start), rectLength * (1.0D - p));
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 363 */       if (getShadowsVisible()) {
/* 364 */         getBarPainter().paintBarShadow(g2, this, row, column, bar, barBase, true);
/*     */       }
/*     */       
/* 367 */       getBarPainter().paintBar(g2, this, row, column, bar, barBase);
/*     */       
/* 369 */       if (completeBar != null) {
/* 370 */         g2.setPaint(getCompletePaint());
/* 371 */         g2.fill(completeBar);
/*     */       } 
/* 373 */       if (incompleteBar != null) {
/* 374 */         g2.setPaint(getIncompletePaint());
/* 375 */         g2.fill(incompleteBar);
/*     */       } 
/* 377 */       if (isDrawBarOutline() && state
/* 378 */         .getBarWidth() > 3.0D) {
/* 379 */         g2.setStroke(getItemStroke(row, column));
/* 380 */         g2.setPaint(getItemOutlinePaint(row, column));
/* 381 */         g2.draw(bar);
/*     */       } 
/*     */       
/* 384 */       if (subinterval == count - 1) {
/*     */         
/* 386 */         int datasetIndex = plot.indexOf((CategoryDataset)dataset);
/* 387 */         Comparable columnKey = dataset.getColumnKey(column);
/* 388 */         Comparable rowKey = dataset.getRowKey(row);
/* 389 */         double xx = domainAxis.getCategorySeriesMiddle(columnKey, rowKey, (CategoryDataset)dataset, 
/* 390 */             getItemMargin(), dataArea, plot
/* 391 */             .getDomainAxisEdge());
/* 392 */         updateCrosshairValues(state.getCrosshairState(), dataset
/* 393 */             .getRowKey(row), dataset.getColumnKey(column), value1
/* 394 */             .doubleValue(), datasetIndex, xx, translatedValue1, orientation);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 399 */       if (state.getInfo() != null) {
/* 400 */         EntityCollection entities = state.getEntityCollection();
/* 401 */         if (entities != null) {
/* 402 */           addItemEntity(entities, (CategoryDataset)dataset, row, column, bar);
/*     */         }
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawTask(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, GanttCategoryDataset dataset, int row, int column) {
/* 431 */     PlotOrientation orientation = plot.getOrientation();
/* 432 */     RectangleEdge rangeAxisLocation = plot.getRangeAxisEdge();
/*     */ 
/*     */     
/* 435 */     Number value0 = dataset.getEndValue(row, column);
/* 436 */     if (value0 == null) {
/*     */       return;
/*     */     }
/* 439 */     double java2dValue0 = rangeAxis.valueToJava2D(value0.doubleValue(), dataArea, rangeAxisLocation);
/*     */ 
/*     */ 
/*     */     
/* 443 */     Number value1 = dataset.getStartValue(row, column);
/* 444 */     if (value1 == null) {
/*     */       return;
/*     */     }
/* 447 */     double java2dValue1 = rangeAxis.valueToJava2D(value1.doubleValue(), dataArea, rangeAxisLocation);
/*     */ 
/*     */     
/* 450 */     if (java2dValue1 < java2dValue0) {
/* 451 */       double temp = java2dValue1;
/* 452 */       java2dValue1 = java2dValue0;
/* 453 */       java2dValue0 = temp;
/* 454 */       value1 = value0;
/*     */     } 
/*     */     
/* 457 */     double rectStart = calculateBarW0(plot, orientation, dataArea, domainAxis, state, row, column);
/*     */     
/* 459 */     double rectBreadth = state.getBarWidth();
/* 460 */     double rectLength = Math.abs(java2dValue1 - java2dValue0);
/*     */     
/* 462 */     Rectangle2D bar = null;
/* 463 */     RectangleEdge barBase = null;
/* 464 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 465 */       bar = new Rectangle2D.Double(java2dValue0, rectStart, rectLength, rectBreadth);
/*     */       
/* 467 */       barBase = RectangleEdge.LEFT;
/*     */     }
/* 469 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 470 */       bar = new Rectangle2D.Double(rectStart, java2dValue1, rectBreadth, rectLength);
/*     */       
/* 472 */       barBase = RectangleEdge.BOTTOM;
/*     */     } 
/*     */     
/* 475 */     Rectangle2D completeBar = null;
/* 476 */     Rectangle2D incompleteBar = null;
/* 477 */     Number percent = dataset.getPercentComplete(row, column);
/* 478 */     double start = getStartPercent();
/* 479 */     double end = getEndPercent();
/* 480 */     if (percent != null) {
/* 481 */       double p = percent.doubleValue();
/* 482 */       if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
/* 483 */         completeBar = new Rectangle2D.Double(java2dValue0, rectStart + start * rectBreadth, rectLength * p, rectBreadth * (end - start));
/*     */ 
/*     */         
/* 486 */         incompleteBar = new Rectangle2D.Double(java2dValue0 + rectLength * p, rectStart + start * rectBreadth, rectLength * (1.0D - p), rectBreadth * (end - start));
/*     */ 
/*     */       
/*     */       }
/* 490 */       else if (plot.getOrientation() == PlotOrientation.VERTICAL) {
/* 491 */         completeBar = new Rectangle2D.Double(rectStart + start * rectBreadth, java2dValue1 + rectLength * (1.0D - p), rectBreadth * (end - start), rectLength * p);
/*     */ 
/*     */         
/* 494 */         incompleteBar = new Rectangle2D.Double(rectStart + start * rectBreadth, java2dValue1, rectBreadth * (end - start), rectLength * (1.0D - p));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 501 */     if (getShadowsVisible()) {
/* 502 */       getBarPainter().paintBarShadow(g2, this, row, column, bar, barBase, true);
/*     */     }
/*     */     
/* 505 */     getBarPainter().paintBar(g2, this, row, column, bar, barBase);
/*     */     
/* 507 */     if (completeBar != null) {
/* 508 */       g2.setPaint(getCompletePaint());
/* 509 */       g2.fill(completeBar);
/*     */     } 
/* 511 */     if (incompleteBar != null) {
/* 512 */       g2.setPaint(getIncompletePaint());
/* 513 */       g2.fill(incompleteBar);
/*     */     } 
/*     */ 
/*     */     
/* 517 */     if (isDrawBarOutline() && state
/* 518 */       .getBarWidth() > 3.0D) {
/* 519 */       Stroke stroke = getItemOutlineStroke(row, column);
/* 520 */       Paint paint = getItemOutlinePaint(row, column);
/* 521 */       if (stroke != null && paint != null) {
/* 522 */         g2.setStroke(stroke);
/* 523 */         g2.setPaint(paint);
/* 524 */         g2.draw(bar);
/*     */       } 
/*     */     } 
/*     */     
/* 528 */     CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
/*     */     
/* 530 */     if (generator != null && isItemLabelVisible(row, column)) {
/* 531 */       drawItemLabel(g2, (CategoryDataset)dataset, row, column, plot, generator, bar, false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 536 */     int datasetIndex = plot.indexOf((CategoryDataset)dataset);
/* 537 */     Comparable columnKey = dataset.getColumnKey(column);
/* 538 */     Comparable rowKey = dataset.getRowKey(row);
/* 539 */     double xx = domainAxis.getCategorySeriesMiddle(columnKey, rowKey, (CategoryDataset)dataset, 
/* 540 */         getItemMargin(), dataArea, plot.getDomainAxisEdge());
/* 541 */     updateCrosshairValues(state.getCrosshairState(), dataset
/* 542 */         .getRowKey(row), dataset.getColumnKey(column), value1
/* 543 */         .doubleValue(), datasetIndex, xx, java2dValue1, orientation);
/*     */ 
/*     */ 
/*     */     
/* 547 */     EntityCollection entities = state.getEntityCollection();
/* 548 */     if (entities != null) {
/* 549 */       addItemEntity(entities, (CategoryDataset)dataset, row, column, bar);
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
/*     */   public double getItemMiddle(Comparable rowKey, Comparable columnKey, CategoryDataset dataset, CategoryAxis axis, Rectangle2D area, RectangleEdge edge) {
/* 571 */     return axis.getCategorySeriesMiddle(columnKey, rowKey, dataset, 
/* 572 */         getItemMargin(), area, edge);
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
/* 584 */     if (obj == this) {
/* 585 */       return true;
/*     */     }
/* 587 */     if (!(obj instanceof GanttRenderer)) {
/* 588 */       return false;
/*     */     }
/* 590 */     GanttRenderer that = (GanttRenderer)obj;
/* 591 */     if (!PaintUtilities.equal(this.completePaint, that.completePaint)) {
/* 592 */       return false;
/*     */     }
/* 594 */     if (!PaintUtilities.equal(this.incompletePaint, that.incompletePaint)) {
/* 595 */       return false;
/*     */     }
/* 597 */     if (this.startPercent != that.startPercent) {
/* 598 */       return false;
/*     */     }
/* 600 */     if (this.endPercent != that.endPercent) {
/* 601 */       return false;
/*     */     }
/* 603 */     return super.equals(obj);
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
/* 614 */     stream.defaultWriteObject();
/* 615 */     SerialUtilities.writePaint(this.completePaint, stream);
/* 616 */     SerialUtilities.writePaint(this.incompletePaint, stream);
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
/* 629 */     stream.defaultReadObject();
/* 630 */     this.completePaint = SerialUtilities.readPaint(stream);
/* 631 */     this.incompletePaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/GanttRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */