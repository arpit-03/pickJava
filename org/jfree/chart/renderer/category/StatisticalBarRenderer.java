/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D;
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
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.statistics.StatisticalCategoryDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.GradientPaintTransformer;
/*     */ import org.jfree.ui.RectangleEdge;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatisticalBarRenderer
/*     */   extends BarRenderer
/*     */   implements CategoryItemRenderer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4986038395414039117L;
/* 128 */   private transient Paint errorIndicatorPaint = Color.gray;
/* 129 */   private transient Stroke errorIndicatorStroke = new BasicStroke(1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getErrorIndicatorPaint() {
/* 141 */     return this.errorIndicatorPaint;
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
/*     */   public void setErrorIndicatorPaint(Paint paint) {
/* 154 */     this.errorIndicatorPaint = paint;
/* 155 */     fireChangeEvent();
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
/*     */   public Stroke getErrorIndicatorStroke() {
/* 169 */     return this.errorIndicatorStroke;
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
/*     */   public void setErrorIndicatorStroke(Stroke stroke) {
/* 185 */     this.errorIndicatorStroke = stroke;
/* 186 */     fireChangeEvent();
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
/*     */   public Range findRangeBounds(CategoryDataset dataset) {
/* 201 */     return findRangeBounds(dataset, true);
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
/*     */   public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset data, int row, int column, int pass) {
/* 225 */     int visibleRow = state.getVisibleSeriesIndex(row);
/* 226 */     if (visibleRow < 0) {
/*     */       return;
/*     */     }
/*     */     
/* 230 */     if (!(data instanceof StatisticalCategoryDataset)) {
/* 231 */       throw new IllegalArgumentException("Requires StatisticalCategoryDataset.");
/*     */     }
/*     */     
/* 234 */     StatisticalCategoryDataset statData = (StatisticalCategoryDataset)data;
/*     */     
/* 236 */     PlotOrientation orientation = plot.getOrientation();
/* 237 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 238 */       drawHorizontalItem(g2, state, dataArea, plot, domainAxis, rangeAxis, statData, visibleRow, row, column);
/*     */     
/*     */     }
/* 241 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 242 */       drawVerticalItem(g2, state, dataArea, plot, domainAxis, rangeAxis, statData, visibleRow, row, column);
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
/*     */ 
/*     */   
/*     */   protected void drawHorizontalItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, StatisticalCategoryDataset dataset, int visibleRow, int row, int column) {
/* 273 */     double rectY = calculateBarW0(plot, PlotOrientation.HORIZONTAL, dataArea, domainAxis, state, visibleRow, column);
/*     */ 
/*     */ 
/*     */     
/* 277 */     Number meanValue = dataset.getMeanValue(row, column);
/* 278 */     if (meanValue == null) {
/*     */       return;
/*     */     }
/* 281 */     double value = meanValue.doubleValue();
/* 282 */     double base = 0.0D;
/* 283 */     double lclip = getLowerClip();
/* 284 */     double uclip = getUpperClip();
/*     */     
/* 286 */     if (uclip <= 0.0D) {
/* 287 */       if (value >= uclip) {
/*     */         return;
/*     */       }
/* 290 */       base = uclip;
/* 291 */       if (value <= lclip) {
/* 292 */         value = lclip;
/*     */       }
/*     */     }
/* 295 */     else if (lclip <= 0.0D) {
/* 296 */       if (value >= uclip) {
/* 297 */         value = uclip;
/*     */       
/*     */       }
/* 300 */       else if (value <= lclip) {
/* 301 */         value = lclip;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 306 */       if (value <= lclip) {
/*     */         return;
/*     */       }
/* 309 */       base = getLowerClip();
/* 310 */       if (value >= uclip) {
/* 311 */         value = uclip;
/*     */       }
/*     */     } 
/*     */     
/* 315 */     RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
/* 316 */     double transY1 = rangeAxis.valueToJava2D(base, dataArea, yAxisLocation);
/* 317 */     double transY2 = rangeAxis.valueToJava2D(value, dataArea, yAxisLocation);
/*     */     
/* 319 */     double rectX = Math.min(transY2, transY1);
/*     */     
/* 321 */     double rectHeight = state.getBarWidth();
/* 322 */     double rectWidth = Math.abs(transY2 - transY1);
/*     */     
/* 324 */     Rectangle2D bar = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
/*     */     
/* 326 */     Paint itemPaint = getItemPaint(row, column);
/* 327 */     GradientPaintTransformer t = getGradientPaintTransformer();
/* 328 */     if (t != null && itemPaint instanceof GradientPaint) {
/* 329 */       itemPaint = t.transform((GradientPaint)itemPaint, bar);
/*     */     }
/* 331 */     g2.setPaint(itemPaint);
/* 332 */     g2.fill(bar);
/*     */ 
/*     */     
/* 335 */     if (isDrawBarOutline() && state
/* 336 */       .getBarWidth() > 3.0D) {
/* 337 */       Stroke stroke = getItemOutlineStroke(row, column);
/* 338 */       Paint paint = getItemOutlinePaint(row, column);
/* 339 */       if (stroke != null && paint != null) {
/* 340 */         g2.setStroke(stroke);
/* 341 */         g2.setPaint(paint);
/* 342 */         g2.draw(bar);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 347 */     Number n = dataset.getStdDevValue(row, column);
/* 348 */     if (n != null) {
/* 349 */       double valueDelta = n.doubleValue();
/* 350 */       double highVal = rangeAxis.valueToJava2D(meanValue.doubleValue() + valueDelta, dataArea, yAxisLocation);
/*     */       
/* 352 */       double lowVal = rangeAxis.valueToJava2D(meanValue.doubleValue() - valueDelta, dataArea, yAxisLocation);
/*     */ 
/*     */       
/* 355 */       if (this.errorIndicatorPaint != null) {
/* 356 */         g2.setPaint(this.errorIndicatorPaint);
/*     */       } else {
/*     */         
/* 359 */         g2.setPaint(getItemOutlinePaint(row, column));
/*     */       } 
/* 361 */       if (this.errorIndicatorStroke != null) {
/* 362 */         g2.setStroke(this.errorIndicatorStroke);
/*     */       } else {
/*     */         
/* 365 */         g2.setStroke(getItemOutlineStroke(row, column));
/*     */       } 
/*     */       
/* 368 */       Line2D line = new Line2D.Double(lowVal, rectY + rectHeight / 2.0D, highVal, rectY + rectHeight / 2.0D);
/*     */       
/* 370 */       g2.draw(line);
/* 371 */       line = new Line2D.Double(highVal, rectY + rectHeight * 0.25D, highVal, rectY + rectHeight * 0.75D);
/*     */       
/* 373 */       g2.draw(line);
/* 374 */       line = new Line2D.Double(lowVal, rectY + rectHeight * 0.25D, lowVal, rectY + rectHeight * 0.75D);
/*     */       
/* 376 */       g2.draw(line);
/*     */     } 
/*     */     
/* 379 */     CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
/*     */     
/* 381 */     if (generator != null && isItemLabelVisible(row, column)) {
/* 382 */       drawItemLabel(g2, (CategoryDataset)dataset, row, column, plot, generator, bar, (value < 0.0D));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 387 */     EntityCollection entities = state.getEntityCollection();
/* 388 */     if (entities != null) {
/* 389 */       addItemEntity(entities, (CategoryDataset)dataset, row, column, bar);
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
/*     */ 
/*     */   
/*     */   protected void drawVerticalItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, StatisticalCategoryDataset dataset, int visibleRow, int row, int column) {
/* 420 */     double rectX = calculateBarW0(plot, PlotOrientation.VERTICAL, dataArea, domainAxis, state, visibleRow, column);
/*     */ 
/*     */ 
/*     */     
/* 424 */     Number meanValue = dataset.getMeanValue(row, column);
/* 425 */     if (meanValue == null) {
/*     */       return;
/*     */     }
/*     */     
/* 429 */     double value = meanValue.doubleValue();
/* 430 */     double base = 0.0D;
/* 431 */     double lclip = getLowerClip();
/* 432 */     double uclip = getUpperClip();
/*     */     
/* 434 */     if (uclip <= 0.0D) {
/* 435 */       if (value >= uclip) {
/*     */         return;
/*     */       }
/* 438 */       base = uclip;
/* 439 */       if (value <= lclip) {
/* 440 */         value = lclip;
/*     */       }
/*     */     }
/* 443 */     else if (lclip <= 0.0D) {
/* 444 */       if (value >= uclip) {
/* 445 */         value = uclip;
/*     */       
/*     */       }
/* 448 */       else if (value <= lclip) {
/* 449 */         value = lclip;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 454 */       if (value <= lclip) {
/*     */         return;
/*     */       }
/* 457 */       base = getLowerClip();
/* 458 */       if (value >= uclip) {
/* 459 */         value = uclip;
/*     */       }
/*     */     } 
/*     */     
/* 463 */     RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
/* 464 */     double transY1 = rangeAxis.valueToJava2D(base, dataArea, yAxisLocation);
/* 465 */     double transY2 = rangeAxis.valueToJava2D(value, dataArea, yAxisLocation);
/*     */     
/* 467 */     double rectY = Math.min(transY2, transY1);
/*     */     
/* 469 */     double rectWidth = state.getBarWidth();
/* 470 */     double rectHeight = Math.abs(transY2 - transY1);
/*     */     
/* 472 */     Rectangle2D bar = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
/*     */     
/* 474 */     Paint itemPaint = getItemPaint(row, column);
/* 475 */     GradientPaintTransformer t = getGradientPaintTransformer();
/* 476 */     if (t != null && itemPaint instanceof GradientPaint) {
/* 477 */       itemPaint = t.transform((GradientPaint)itemPaint, bar);
/*     */     }
/* 479 */     g2.setPaint(itemPaint);
/* 480 */     g2.fill(bar);
/*     */     
/* 482 */     if (isDrawBarOutline() && state
/* 483 */       .getBarWidth() > 3.0D) {
/* 484 */       Stroke stroke = getItemOutlineStroke(row, column);
/* 485 */       Paint paint = getItemOutlinePaint(row, column);
/* 486 */       if (stroke != null && paint != null) {
/* 487 */         g2.setStroke(stroke);
/* 488 */         g2.setPaint(paint);
/* 489 */         g2.draw(bar);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 494 */     Number n = dataset.getStdDevValue(row, column);
/* 495 */     if (n != null) {
/* 496 */       double valueDelta = n.doubleValue();
/* 497 */       double highVal = rangeAxis.valueToJava2D(meanValue.doubleValue() + valueDelta, dataArea, yAxisLocation);
/*     */       
/* 499 */       double lowVal = rangeAxis.valueToJava2D(meanValue.doubleValue() - valueDelta, dataArea, yAxisLocation);
/*     */ 
/*     */       
/* 502 */       if (this.errorIndicatorPaint != null) {
/* 503 */         g2.setPaint(this.errorIndicatorPaint);
/*     */       } else {
/*     */         
/* 506 */         g2.setPaint(getItemOutlinePaint(row, column));
/*     */       } 
/* 508 */       if (this.errorIndicatorStroke != null) {
/* 509 */         g2.setStroke(this.errorIndicatorStroke);
/*     */       } else {
/*     */         
/* 512 */         g2.setStroke(getItemOutlineStroke(row, column));
/*     */       } 
/*     */ 
/*     */       
/* 516 */       Line2D line = new Line2D.Double(rectX + rectWidth / 2.0D, lowVal, rectX + rectWidth / 2.0D, highVal);
/*     */       
/* 518 */       g2.draw(line);
/* 519 */       line = new Line2D.Double(rectX + rectWidth / 2.0D - 5.0D, highVal, rectX + rectWidth / 2.0D + 5.0D, highVal);
/*     */       
/* 521 */       g2.draw(line);
/* 522 */       line = new Line2D.Double(rectX + rectWidth / 2.0D - 5.0D, lowVal, rectX + rectWidth / 2.0D + 5.0D, lowVal);
/*     */       
/* 524 */       g2.draw(line);
/*     */     } 
/*     */     
/* 527 */     CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
/*     */     
/* 529 */     if (generator != null && isItemLabelVisible(row, column)) {
/* 530 */       drawItemLabel(g2, (CategoryDataset)dataset, row, column, plot, generator, bar, (value < 0.0D));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 535 */     EntityCollection entities = state.getEntityCollection();
/* 536 */     if (entities != null) {
/* 537 */       addItemEntity(entities, (CategoryDataset)dataset, row, column, bar);
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
/*     */   public boolean equals(Object obj) {
/* 550 */     if (obj == this) {
/* 551 */       return true;
/*     */     }
/* 553 */     if (!(obj instanceof StatisticalBarRenderer)) {
/* 554 */       return false;
/*     */     }
/* 556 */     StatisticalBarRenderer that = (StatisticalBarRenderer)obj;
/* 557 */     if (!PaintUtilities.equal(this.errorIndicatorPaint, that.errorIndicatorPaint))
/*     */     {
/* 559 */       return false;
/*     */     }
/* 561 */     if (!ObjectUtilities.equal(this.errorIndicatorStroke, that.errorIndicatorStroke))
/*     */     {
/* 563 */       return false;
/*     */     }
/* 565 */     return super.equals(obj);
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
/* 576 */     stream.defaultWriteObject();
/* 577 */     SerialUtilities.writePaint(this.errorIndicatorPaint, stream);
/* 578 */     SerialUtilities.writeStroke(this.errorIndicatorStroke, stream);
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
/* 591 */     stream.defaultReadObject();
/* 592 */     this.errorIndicatorPaint = SerialUtilities.readPaint(stream);
/* 593 */     this.errorIndicatorStroke = SerialUtilities.readStroke(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/StatisticalBarRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */