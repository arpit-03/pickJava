/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.statistics.StatisticalCategoryDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
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
/*     */ public class StatisticalLineAndShapeRenderer
/*     */   extends LineAndShapeRenderer
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3557517173697777579L;
/*     */   private transient Paint errorIndicatorPaint;
/*     */   private transient Stroke errorIndicatorStroke;
/*     */   
/*     */   public StatisticalLineAndShapeRenderer() {
/* 120 */     this(true, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatisticalLineAndShapeRenderer(boolean linesVisible, boolean shapesVisible) {
/* 131 */     super(linesVisible, shapesVisible);
/* 132 */     this.errorIndicatorPaint = null;
/* 133 */     this.errorIndicatorStroke = null;
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
/*     */   public Paint getErrorIndicatorPaint() {
/* 145 */     return this.errorIndicatorPaint;
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
/* 158 */     this.errorIndicatorPaint = paint;
/* 159 */     fireChangeEvent();
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
/* 173 */     return this.errorIndicatorStroke;
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
/*     */   public void setErrorIndicatorStroke(Stroke stroke) {
/* 188 */     this.errorIndicatorStroke = stroke;
/* 189 */     fireChangeEvent();
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
/*     */   public Range findRangeBounds(CategoryDataset dataset) {
/* 203 */     return findRangeBounds(dataset, true);
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
/*     */     double x1;
/* 228 */     if (!getItemVisible(row, column)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 234 */     if (!(dataset instanceof StatisticalCategoryDataset)) {
/* 235 */       super.drawItem(g2, state, dataArea, plot, domainAxis, rangeAxis, dataset, row, column, pass);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 240 */     int visibleRow = state.getVisibleSeriesIndex(row);
/* 241 */     if (visibleRow < 0) {
/*     */       return;
/*     */     }
/* 244 */     int visibleRowCount = state.getVisibleSeriesCount();
/*     */     
/* 246 */     StatisticalCategoryDataset statDataset = (StatisticalCategoryDataset)dataset;
/*     */     
/* 248 */     Number meanValue = statDataset.getMeanValue(row, column);
/* 249 */     if (meanValue == null) {
/*     */       return;
/*     */     }
/* 252 */     PlotOrientation orientation = plot.getOrientation();
/*     */ 
/*     */ 
/*     */     
/* 256 */     if (getUseSeriesOffset()) {
/* 257 */       x1 = domainAxis.getCategorySeriesMiddle(column, dataset
/* 258 */           .getColumnCount(), visibleRow, visibleRowCount, 
/*     */           
/* 260 */           getItemMargin(), dataArea, plot.getDomainAxisEdge());
/*     */     } else {
/*     */       
/* 263 */       x1 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot
/* 264 */           .getDomainAxisEdge());
/*     */     } 
/* 266 */     double y1 = rangeAxis.valueToJava2D(meanValue.doubleValue(), dataArea, plot
/* 267 */         .getRangeAxisEdge());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     Number sdv = statDataset.getStdDevValue(row, column);
/* 273 */     if (pass == 1 && sdv != null) {
/*     */       double highVal, lowVal;
/* 275 */       RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
/* 276 */       double valueDelta = sdv.doubleValue();
/*     */       
/* 278 */       if (meanValue.doubleValue() + valueDelta > rangeAxis
/* 279 */         .getRange().getUpperBound()) {
/* 280 */         highVal = rangeAxis.valueToJava2D(rangeAxis
/* 281 */             .getRange().getUpperBound(), dataArea, yAxisLocation);
/*     */       }
/*     */       else {
/*     */         
/* 285 */         highVal = rangeAxis.valueToJava2D(meanValue.doubleValue() + valueDelta, dataArea, yAxisLocation);
/*     */       } 
/*     */ 
/*     */       
/* 289 */       if (meanValue.doubleValue() + valueDelta < rangeAxis
/* 290 */         .getRange().getLowerBound()) {
/* 291 */         lowVal = rangeAxis.valueToJava2D(rangeAxis
/* 292 */             .getRange().getLowerBound(), dataArea, yAxisLocation);
/*     */       }
/*     */       else {
/*     */         
/* 296 */         lowVal = rangeAxis.valueToJava2D(meanValue.doubleValue() - valueDelta, dataArea, yAxisLocation);
/*     */       } 
/*     */ 
/*     */       
/* 300 */       if (this.errorIndicatorPaint != null) {
/* 301 */         g2.setPaint(this.errorIndicatorPaint);
/*     */       } else {
/*     */         
/* 304 */         g2.setPaint(getItemPaint(row, column));
/*     */       } 
/* 306 */       if (this.errorIndicatorStroke != null) {
/* 307 */         g2.setStroke(this.errorIndicatorStroke);
/*     */       } else {
/*     */         
/* 310 */         g2.setStroke(getItemOutlineStroke(row, column));
/*     */       } 
/* 312 */       Line2D line = new Line2D.Double();
/* 313 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 314 */         line.setLine(lowVal, x1, highVal, x1);
/* 315 */         g2.draw(line);
/* 316 */         line.setLine(lowVal, x1 - 5.0D, lowVal, x1 + 5.0D);
/* 317 */         g2.draw(line);
/* 318 */         line.setLine(highVal, x1 - 5.0D, highVal, x1 + 5.0D);
/* 319 */         g2.draw(line);
/*     */       } else {
/*     */         
/* 322 */         line.setLine(x1, lowVal, x1, highVal);
/* 323 */         g2.draw(line);
/* 324 */         line.setLine(x1 - 5.0D, highVal, x1 + 5.0D, highVal);
/* 325 */         g2.draw(line);
/* 326 */         line.setLine(x1 - 5.0D, lowVal, x1 + 5.0D, lowVal);
/* 327 */         g2.draw(line);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 332 */     Shape hotspot = null;
/* 333 */     if (pass == 1 && getItemShapeVisible(row, column)) {
/* 334 */       Shape shape = getItemShape(row, column);
/* 335 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 336 */         shape = ShapeUtilities.createTranslatedShape(shape, y1, x1);
/*     */       }
/* 338 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 339 */         shape = ShapeUtilities.createTranslatedShape(shape, x1, y1);
/*     */       } 
/* 341 */       hotspot = shape;
/*     */       
/* 343 */       if (getItemShapeFilled(row, column)) {
/* 344 */         if (getUseFillPaint()) {
/* 345 */           g2.setPaint(getItemFillPaint(row, column));
/*     */         } else {
/*     */           
/* 348 */           g2.setPaint(getItemPaint(row, column));
/*     */         } 
/* 350 */         g2.fill(shape);
/*     */       } 
/* 352 */       if (getDrawOutlines()) {
/* 353 */         if (getUseOutlinePaint()) {
/* 354 */           g2.setPaint(getItemOutlinePaint(row, column));
/*     */         } else {
/*     */           
/* 357 */           g2.setPaint(getItemPaint(row, column));
/*     */         } 
/* 359 */         g2.setStroke(getItemOutlineStroke(row, column));
/* 360 */         g2.draw(shape);
/*     */       } 
/*     */       
/* 363 */       if (isItemLabelVisible(row, column)) {
/* 364 */         if (orientation == PlotOrientation.HORIZONTAL) {
/* 365 */           drawItemLabel(g2, orientation, dataset, row, column, y1, x1, 
/* 366 */               (meanValue.doubleValue() < 0.0D));
/*     */         }
/* 368 */         else if (orientation == PlotOrientation.VERTICAL) {
/* 369 */           drawItemLabel(g2, orientation, dataset, row, column, x1, y1, 
/* 370 */               (meanValue.doubleValue() < 0.0D));
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 375 */     if (pass == 0 && getItemLineVisible(row, column) && 
/* 376 */       column != 0) {
/*     */       
/* 378 */       Number previousValue = statDataset.getValue(row, column - 1);
/* 379 */       if (previousValue != null) {
/*     */ 
/*     */         
/* 382 */         double x0, previous = previousValue.doubleValue();
/*     */         
/* 384 */         if (getUseSeriesOffset()) {
/* 385 */           x0 = domainAxis.getCategorySeriesMiddle(column - 1, dataset
/* 386 */               .getColumnCount(), visibleRow, visibleRowCount, 
/*     */               
/* 388 */               getItemMargin(), dataArea, plot
/* 389 */               .getDomainAxisEdge());
/*     */         } else {
/*     */           
/* 392 */           x0 = domainAxis.getCategoryMiddle(column - 1, 
/* 393 */               getColumnCount(), dataArea, plot
/* 394 */               .getDomainAxisEdge());
/*     */         } 
/* 396 */         double y0 = rangeAxis.valueToJava2D(previous, dataArea, plot
/* 397 */             .getRangeAxisEdge());
/*     */         
/* 399 */         Line2D line = null;
/* 400 */         if (orientation == PlotOrientation.HORIZONTAL) {
/* 401 */           line = new Line2D.Double(y0, x0, y1, x1);
/*     */         }
/* 403 */         else if (orientation == PlotOrientation.VERTICAL) {
/* 404 */           line = new Line2D.Double(x0, y0, x1, y1);
/*     */         } 
/* 406 */         g2.setPaint(getItemPaint(row, column));
/* 407 */         g2.setStroke(getItemStroke(row, column));
/* 408 */         g2.draw(line);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 413 */     if (pass == 1) {
/*     */       
/* 415 */       EntityCollection entities = state.getEntityCollection();
/* 416 */       if (entities != null) {
/* 417 */         addEntity(entities, hotspot, dataset, row, column, x1, y1);
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
/*     */   public boolean equals(Object obj) {
/* 432 */     if (obj == this) {
/* 433 */       return true;
/*     */     }
/* 435 */     if (!(obj instanceof StatisticalLineAndShapeRenderer)) {
/* 436 */       return false;
/*     */     }
/* 438 */     StatisticalLineAndShapeRenderer that = (StatisticalLineAndShapeRenderer)obj;
/*     */     
/* 440 */     if (!PaintUtilities.equal(this.errorIndicatorPaint, that.errorIndicatorPaint))
/*     */     {
/* 442 */       return false;
/*     */     }
/* 444 */     if (!ObjectUtilities.equal(this.errorIndicatorStroke, that.errorIndicatorStroke))
/*     */     {
/* 446 */       return false;
/*     */     }
/* 448 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 458 */     int hash = super.hashCode();
/* 459 */     hash = HashUtilities.hashCode(hash, this.errorIndicatorPaint);
/* 460 */     return hash;
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
/* 471 */     stream.defaultWriteObject();
/* 472 */     SerialUtilities.writePaint(this.errorIndicatorPaint, stream);
/* 473 */     SerialUtilities.writeStroke(this.errorIndicatorStroke, stream);
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
/* 486 */     stream.defaultReadObject();
/* 487 */     this.errorIndicatorPaint = SerialUtilities.readPaint(stream);
/* 488 */     this.errorIndicatorStroke = SerialUtilities.readStroke(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/StatisticalLineAndShapeRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */