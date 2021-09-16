/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.CategoryItemLabelGenerator;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.ui.GradientPaintTransformer;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.util.ObjectList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayeredBarRenderer
/*     */   extends BarRenderer
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8716572894780469487L;
/*  96 */   protected ObjectList seriesBarWidthList = new ObjectList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSeriesBarWidth(int series) {
/* 108 */     double result = Double.NaN;
/* 109 */     Number n = (Number)this.seriesBarWidthList.get(series);
/* 110 */     if (n != null) {
/* 111 */       result = n.doubleValue();
/*     */     }
/* 113 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeriesBarWidth(int series, double width) {
/* 124 */     this.seriesBarWidthList.set(series, new Double(width));
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
/*     */   protected void calculateBarWidth(CategoryPlot plot, Rectangle2D dataArea, int rendererIndex, CategoryItemRendererState state) {
/* 143 */     CategoryAxis domainAxis = getDomainAxis(plot, rendererIndex);
/* 144 */     CategoryDataset dataset = plot.getDataset(rendererIndex);
/* 145 */     if (dataset != null) {
/* 146 */       int columns = dataset.getColumnCount();
/* 147 */       int rows = dataset.getRowCount();
/* 148 */       double space = 0.0D;
/* 149 */       PlotOrientation orientation = plot.getOrientation();
/* 150 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 151 */         space = dataArea.getHeight();
/*     */       }
/* 153 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 154 */         space = dataArea.getWidth();
/*     */       } 
/* 156 */       double maxWidth = space * getMaximumBarWidth();
/* 157 */       double categoryMargin = 0.0D;
/* 158 */       if (columns > 1) {
/* 159 */         categoryMargin = domainAxis.getCategoryMargin();
/*     */       }
/*     */       
/* 162 */       double used = space * (1.0D - domainAxis.getLowerMargin() - domainAxis.getUpperMargin() - categoryMargin);
/* 163 */       if (rows * columns > 0) {
/* 164 */         state.setBarWidth(Math.min(used / dataset.getColumnCount(), maxWidth));
/*     */       }
/*     */       else {
/*     */         
/* 168 */         state.setBarWidth(Math.min(used, maxWidth));
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
/*     */   public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset data, int row, int column, int pass) {
/* 193 */     PlotOrientation orientation = plot.getOrientation();
/* 194 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 195 */       drawHorizontalItem(g2, state, dataArea, plot, domainAxis, rangeAxis, data, row, column);
/*     */     
/*     */     }
/* 198 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 199 */       drawVerticalItem(g2, state, dataArea, plot, domainAxis, rangeAxis, data, row, column);
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
/*     */   protected void drawHorizontalItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column) {
/* 229 */     Number dataValue = dataset.getValue(row, column);
/* 230 */     if (dataValue == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 235 */     double value = dataValue.doubleValue();
/* 236 */     double base = 0.0D;
/* 237 */     double lclip = getLowerClip();
/* 238 */     double uclip = getUpperClip();
/* 239 */     if (uclip <= 0.0D) {
/* 240 */       if (value >= uclip) {
/*     */         return;
/*     */       }
/* 243 */       base = uclip;
/* 244 */       if (value <= lclip) {
/* 245 */         value = lclip;
/*     */       }
/*     */     }
/* 248 */     else if (lclip <= 0.0D) {
/* 249 */       if (value >= uclip) {
/* 250 */         value = uclip;
/*     */       
/*     */       }
/* 253 */       else if (value <= lclip) {
/* 254 */         value = lclip;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 259 */       if (value <= lclip) {
/*     */         return;
/*     */       }
/* 262 */       base = lclip;
/* 263 */       if (value >= uclip) {
/* 264 */         value = uclip;
/*     */       }
/*     */     } 
/*     */     
/* 268 */     RectangleEdge edge = plot.getRangeAxisEdge();
/* 269 */     double transX1 = rangeAxis.valueToJava2D(base, dataArea, edge);
/* 270 */     double transX2 = rangeAxis.valueToJava2D(value, dataArea, edge);
/* 271 */     double rectX = Math.min(transX1, transX2);
/* 272 */     double rectWidth = Math.abs(transX2 - transX1);
/*     */ 
/*     */ 
/*     */     
/* 276 */     double rectY = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot.getDomainAxisEdge()) - state.getBarWidth() / 2.0D;
/*     */     
/* 278 */     int seriesCount = getRowCount();
/*     */ 
/*     */     
/* 281 */     double shift = 0.0D;
/*     */     
/* 283 */     double widthFactor = 1.0D;
/* 284 */     double seriesBarWidth = getSeriesBarWidth(row);
/* 285 */     if (!Double.isNaN(seriesBarWidth)) {
/* 286 */       widthFactor = seriesBarWidth;
/*     */     }
/* 288 */     double rectHeight = widthFactor * state.getBarWidth();
/* 289 */     rectY += (1.0D - widthFactor) * state.getBarWidth() / 2.0D;
/* 290 */     if (seriesCount > 1) {
/* 291 */       shift = rectHeight * 0.2D / (seriesCount - 1);
/*     */     }
/*     */     
/* 294 */     Rectangle2D bar = new Rectangle2D.Double(rectX, rectY + (seriesCount - 1 - row) * shift, rectWidth, rectHeight - (seriesCount - 1 - row) * shift * 2.0D);
/*     */ 
/*     */ 
/*     */     
/* 298 */     Paint itemPaint = getItemPaint(row, column);
/* 299 */     GradientPaintTransformer t = getGradientPaintTransformer();
/* 300 */     if (t != null && itemPaint instanceof GradientPaint) {
/* 301 */       itemPaint = t.transform((GradientPaint)itemPaint, bar);
/*     */     }
/* 303 */     g2.setPaint(itemPaint);
/* 304 */     g2.fill(bar);
/*     */ 
/*     */     
/* 307 */     if (isDrawBarOutline() && state
/* 308 */       .getBarWidth() > 3.0D) {
/* 309 */       Stroke stroke = getItemOutlineStroke(row, column);
/* 310 */       Paint paint = getItemOutlinePaint(row, column);
/* 311 */       if (stroke != null && paint != null) {
/* 312 */         g2.setStroke(stroke);
/* 313 */         g2.setPaint(paint);
/* 314 */         g2.draw(bar);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 319 */     CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
/* 320 */     if (generator != null && isItemLabelVisible(row, column)) {
/* 321 */       drawItemLabel(g2, dataset, row, column, plot, generator, bar, (transX1 > transX2));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 326 */     EntityCollection entities = state.getEntityCollection();
/* 327 */     if (entities != null) {
/* 328 */       addItemEntity(entities, dataset, row, column, bar);
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
/*     */   protected void drawVerticalItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column) {
/* 356 */     Number dataValue = dataset.getValue(row, column);
/* 357 */     if (dataValue == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 363 */     double rectX = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot.getDomainAxisEdge()) - state.getBarWidth() / 2.0D;
/*     */     
/* 365 */     int seriesCount = getRowCount();
/*     */ 
/*     */     
/* 368 */     double value = dataValue.doubleValue();
/* 369 */     double base = 0.0D;
/* 370 */     double lclip = getLowerClip();
/* 371 */     double uclip = getUpperClip();
/*     */     
/* 373 */     if (uclip <= 0.0D) {
/* 374 */       if (value >= uclip) {
/*     */         return;
/*     */       }
/* 377 */       base = uclip;
/* 378 */       if (value <= lclip) {
/* 379 */         value = lclip;
/*     */       }
/*     */     }
/* 382 */     else if (lclip <= 0.0D) {
/* 383 */       if (value >= uclip) {
/* 384 */         value = uclip;
/*     */       
/*     */       }
/* 387 */       else if (value <= lclip) {
/* 388 */         value = lclip;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 393 */       if (value <= lclip) {
/*     */         return;
/*     */       }
/* 396 */       base = getLowerClip();
/* 397 */       if (value >= uclip) {
/* 398 */         value = uclip;
/*     */       }
/*     */     } 
/*     */     
/* 402 */     RectangleEdge edge = plot.getRangeAxisEdge();
/* 403 */     double transY1 = rangeAxis.valueToJava2D(base, dataArea, edge);
/* 404 */     double transY2 = rangeAxis.valueToJava2D(value, dataArea, edge);
/* 405 */     double rectY = Math.min(transY2, transY1);
/*     */ 
/*     */     
/* 408 */     double rectHeight = Math.abs(transY2 - transY1);
/*     */ 
/*     */     
/* 411 */     double shift = 0.0D;
/* 412 */     double widthFactor = 1.0D;
/* 413 */     double seriesBarWidth = getSeriesBarWidth(row);
/* 414 */     if (!Double.isNaN(seriesBarWidth)) {
/* 415 */       widthFactor = seriesBarWidth;
/*     */     }
/* 417 */     double rectWidth = widthFactor * state.getBarWidth();
/* 418 */     rectX += (1.0D - widthFactor) * state.getBarWidth() / 2.0D;
/* 419 */     if (seriesCount > 1)
/*     */     {
/* 421 */       shift = rectWidth * 0.2D / (seriesCount - 1);
/*     */     }
/*     */     
/* 424 */     Rectangle2D bar = new Rectangle2D.Double(rectX + (seriesCount - 1 - row) * shift, rectY, rectWidth - (seriesCount - 1 - row) * shift * 2.0D, rectHeight);
/*     */ 
/*     */     
/* 427 */     Paint itemPaint = getItemPaint(row, column);
/* 428 */     GradientPaintTransformer t = getGradientPaintTransformer();
/* 429 */     if (t != null && itemPaint instanceof GradientPaint) {
/* 430 */       itemPaint = t.transform((GradientPaint)itemPaint, bar);
/*     */     }
/* 432 */     g2.setPaint(itemPaint);
/* 433 */     g2.fill(bar);
/*     */ 
/*     */     
/* 436 */     if (isDrawBarOutline() && state
/* 437 */       .getBarWidth() > 3.0D) {
/* 438 */       Stroke stroke = getItemOutlineStroke(row, column);
/* 439 */       Paint paint = getItemOutlinePaint(row, column);
/* 440 */       if (stroke != null && paint != null) {
/* 441 */         g2.setStroke(stroke);
/* 442 */         g2.setPaint(paint);
/* 443 */         g2.draw(bar);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 448 */     double transX1 = rangeAxis.valueToJava2D(base, dataArea, edge);
/* 449 */     double transX2 = rangeAxis.valueToJava2D(value, dataArea, edge);
/*     */ 
/*     */     
/* 452 */     CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
/* 453 */     if (generator != null && isItemLabelVisible(row, column)) {
/* 454 */       drawItemLabel(g2, dataset, row, column, plot, generator, bar, (transX1 > transX2));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 459 */     EntityCollection entities = state.getEntityCollection();
/* 460 */     if (entities != null)
/* 461 */       addItemEntity(entities, dataset, row, column, bar); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/LayeredBarRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */