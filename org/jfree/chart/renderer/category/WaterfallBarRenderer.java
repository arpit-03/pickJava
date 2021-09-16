/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.CategoryItemLabelGenerator;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.GradientPaintTransformType;
/*     */ import org.jfree.ui.GradientPaintTransformer;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.StandardGradientPaintTransformer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaterfallBarRenderer
/*     */   extends BarRenderer
/*     */ {
/*     */   private static final long serialVersionUID = -2482910643727230911L;
/*     */   private transient Paint firstBarPaint;
/*     */   private transient Paint lastBarPaint;
/*     */   private transient Paint positiveBarPaint;
/*     */   private transient Paint negativeBarPaint;
/*     */   
/*     */   public WaterfallBarRenderer() {
/* 129 */     this(new GradientPaint(0.0F, 0.0F, new Color(34, 34, 255), 0.0F, 0.0F, new Color(102, 102, 255)), new GradientPaint(0.0F, 0.0F, new Color(34, 255, 34), 0.0F, 0.0F, new Color(102, 255, 102)), new GradientPaint(0.0F, 0.0F, new Color(255, 34, 34), 0.0F, 0.0F, new Color(255, 102, 102)), new GradientPaint(0.0F, 0.0F, new Color(255, 255, 34), 0.0F, 0.0F, new Color(255, 255, 102)));
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
/*     */   public WaterfallBarRenderer(Paint firstBarPaint, Paint positiveBarPaint, Paint negativeBarPaint, Paint lastBarPaint) {
/* 154 */     ParamChecks.nullNotPermitted(firstBarPaint, "firstBarPaint");
/* 155 */     ParamChecks.nullNotPermitted(positiveBarPaint, "positiveBarPaint");
/* 156 */     ParamChecks.nullNotPermitted(negativeBarPaint, "negativeBarPaint");
/* 157 */     ParamChecks.nullNotPermitted(lastBarPaint, "lastBarPaint");
/* 158 */     this.firstBarPaint = firstBarPaint;
/* 159 */     this.lastBarPaint = lastBarPaint;
/* 160 */     this.positiveBarPaint = positiveBarPaint;
/* 161 */     this.negativeBarPaint = negativeBarPaint;
/* 162 */     setGradientPaintTransformer((GradientPaintTransformer)new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_VERTICAL));
/*     */     
/* 164 */     setMinimumBarLength(1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getFirstBarPaint() {
/* 173 */     return this.firstBarPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFirstBarPaint(Paint paint) {
/* 183 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 184 */     this.firstBarPaint = paint;
/* 185 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getLastBarPaint() {
/* 194 */     return this.lastBarPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastBarPaint(Paint paint) {
/* 204 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 205 */     this.lastBarPaint = paint;
/* 206 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getPositiveBarPaint() {
/* 215 */     return this.positiveBarPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPositiveBarPaint(Paint paint) {
/* 224 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 225 */     this.positiveBarPaint = paint;
/* 226 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getNegativeBarPaint() {
/* 235 */     return this.negativeBarPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNegativeBarPaint(Paint paint) {
/* 245 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 246 */     this.negativeBarPaint = paint;
/* 247 */     fireChangeEvent();
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
/* 260 */     if (dataset == null) {
/* 261 */       return null;
/*     */     }
/* 263 */     boolean allItemsNull = true;
/*     */     
/* 265 */     double minimum = 0.0D;
/* 266 */     double maximum = 0.0D;
/* 267 */     int columnCount = dataset.getColumnCount();
/* 268 */     for (int row = 0; row < dataset.getRowCount(); row++) {
/* 269 */       double runningTotal = 0.0D;
/* 270 */       for (int column = 0; column <= columnCount - 1; column++) {
/* 271 */         Number n = dataset.getValue(row, column);
/* 272 */         if (n != null) {
/* 273 */           allItemsNull = false;
/* 274 */           double value = n.doubleValue();
/* 275 */           if (column == columnCount - 1) {
/*     */             
/* 277 */             runningTotal = value;
/*     */           } else {
/*     */             
/* 280 */             runningTotal += value;
/*     */           } 
/* 282 */           minimum = Math.min(minimum, runningTotal);
/* 283 */           maximum = Math.max(maximum, runningTotal);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 288 */     if (!allItemsNull) {
/* 289 */       return new Range(minimum, maximum);
/*     */     }
/*     */     
/* 292 */     return null;
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
/*     */     Paint seriesPaint;
/* 317 */     double previous = state.getSeriesRunningTotal();
/* 318 */     if (column == dataset.getColumnCount() - 1) {
/* 319 */       previous = 0.0D;
/*     */     }
/* 321 */     double current = 0.0D;
/* 322 */     Number n = dataset.getValue(row, column);
/* 323 */     if (n != null) {
/* 324 */       current = previous + n.doubleValue();
/*     */     }
/* 326 */     state.setSeriesRunningTotal(current);
/*     */     
/* 328 */     int categoryCount = getColumnCount();
/* 329 */     PlotOrientation orientation = plot.getOrientation();
/*     */     
/* 331 */     double rectX = 0.0D;
/* 332 */     double rectY = 0.0D;
/*     */     
/* 334 */     RectangleEdge rangeAxisLocation = plot.getRangeAxisEdge();
/*     */ 
/*     */     
/* 337 */     double j2dy0 = rangeAxis.valueToJava2D(previous, dataArea, rangeAxisLocation);
/*     */ 
/*     */ 
/*     */     
/* 341 */     double j2dy1 = rangeAxis.valueToJava2D(current, dataArea, rangeAxisLocation);
/*     */ 
/*     */     
/* 344 */     double valDiff = current - previous;
/* 345 */     if (j2dy1 < j2dy0) {
/* 346 */       double temp = j2dy1;
/* 347 */       j2dy1 = j2dy0;
/* 348 */       j2dy0 = temp;
/*     */     } 
/*     */ 
/*     */     
/* 352 */     double rectWidth = state.getBarWidth();
/*     */ 
/*     */     
/* 355 */     double rectHeight = Math.max(getMinimumBarLength(), 
/* 356 */         Math.abs(j2dy1 - j2dy0));
/*     */     
/* 358 */     Comparable seriesKey = dataset.getRowKey(row);
/* 359 */     Comparable categoryKey = dataset.getColumnKey(column);
/* 360 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 361 */       rectY = domainAxis.getCategorySeriesMiddle(categoryKey, seriesKey, dataset, 
/* 362 */           getItemMargin(), dataArea, RectangleEdge.LEFT);
/*     */       
/* 364 */       rectX = j2dy0;
/* 365 */       rectHeight = state.getBarWidth();
/* 366 */       rectY -= rectHeight / 2.0D;
/* 367 */       rectWidth = Math.max(getMinimumBarLength(), 
/* 368 */           Math.abs(j2dy1 - j2dy0));
/*     */     
/*     */     }
/* 371 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 372 */       rectX = domainAxis.getCategorySeriesMiddle(categoryKey, seriesKey, dataset, 
/* 373 */           getItemMargin(), dataArea, RectangleEdge.TOP);
/* 374 */       rectX -= rectWidth / 2.0D;
/* 375 */       rectY = j2dy0;
/*     */     } 
/* 377 */     Rectangle2D bar = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
/*     */ 
/*     */     
/* 380 */     if (column == 0) {
/* 381 */       seriesPaint = getFirstBarPaint();
/*     */     }
/* 383 */     else if (column == categoryCount - 1) {
/* 384 */       seriesPaint = getLastBarPaint();
/*     */     
/*     */     }
/* 387 */     else if (valDiff >= 0.0D) {
/* 388 */       seriesPaint = getPositiveBarPaint();
/*     */     } else {
/* 390 */       seriesPaint = getNegativeBarPaint();
/*     */     } 
/*     */     
/* 393 */     if (getGradientPaintTransformer() != null && seriesPaint instanceof GradientPaint) {
/*     */       
/* 395 */       GradientPaint gp = (GradientPaint)seriesPaint;
/* 396 */       seriesPaint = getGradientPaintTransformer().transform(gp, bar);
/*     */     } 
/* 398 */     g2.setPaint(seriesPaint);
/* 399 */     g2.fill(bar);
/*     */ 
/*     */     
/* 402 */     if (isDrawBarOutline() && state
/* 403 */       .getBarWidth() > 3.0D) {
/* 404 */       Stroke stroke = getItemOutlineStroke(row, column);
/* 405 */       Paint paint = getItemOutlinePaint(row, column);
/* 406 */       if (stroke != null && paint != null) {
/* 407 */         g2.setStroke(stroke);
/* 408 */         g2.setPaint(paint);
/* 409 */         g2.draw(bar);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 414 */     CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
/* 415 */     if (generator != null && isItemLabelVisible(row, column)) {
/* 416 */       drawItemLabel(g2, dataset, row, column, plot, generator, bar, (valDiff < 0.0D));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 421 */     EntityCollection entities = state.getEntityCollection();
/* 422 */     if (entities != null) {
/* 423 */       addItemEntity(entities, dataset, row, column, bar);
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
/*     */   public boolean equals(Object obj) {
/* 438 */     if (obj == this) {
/* 439 */       return true;
/*     */     }
/* 441 */     if (!super.equals(obj)) {
/* 442 */       return false;
/*     */     }
/* 444 */     if (!(obj instanceof WaterfallBarRenderer)) {
/* 445 */       return false;
/*     */     }
/* 447 */     WaterfallBarRenderer that = (WaterfallBarRenderer)obj;
/* 448 */     if (!PaintUtilities.equal(this.firstBarPaint, that.firstBarPaint)) {
/* 449 */       return false;
/*     */     }
/* 451 */     if (!PaintUtilities.equal(this.lastBarPaint, that.lastBarPaint)) {
/* 452 */       return false;
/*     */     }
/* 454 */     if (!PaintUtilities.equal(this.positiveBarPaint, that.positiveBarPaint))
/*     */     {
/* 456 */       return false;
/*     */     }
/* 458 */     if (!PaintUtilities.equal(this.negativeBarPaint, that.negativeBarPaint))
/*     */     {
/* 460 */       return false;
/*     */     }
/* 462 */     return true;
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
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 474 */     stream.defaultWriteObject();
/* 475 */     SerialUtilities.writePaint(this.firstBarPaint, stream);
/* 476 */     SerialUtilities.writePaint(this.lastBarPaint, stream);
/* 477 */     SerialUtilities.writePaint(this.positiveBarPaint, stream);
/* 478 */     SerialUtilities.writePaint(this.negativeBarPaint, stream);
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
/* 491 */     stream.defaultReadObject();
/* 492 */     this.firstBarPaint = SerialUtilities.readPaint(stream);
/* 493 */     this.lastBarPaint = SerialUtilities.readPaint(stream);
/* 494 */     this.positiveBarPaint = SerialUtilities.readPaint(stream);
/* 495 */     this.negativeBarPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/WaterfallBarRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */