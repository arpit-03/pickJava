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
/*     */ import java.util.List;
/*     */ import org.jfree.chart.LegendItem;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.general.Dataset;
/*     */ import org.jfree.data.statistics.MultiValueCategoryDataset;
/*     */ import org.jfree.util.BooleanList;
/*     */ import org.jfree.util.BooleanUtilities;
/*     */ import org.jfree.util.ObjectUtilities;
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
/*     */ public class ScatterRenderer
/*     */   extends AbstractCategoryItemRenderer
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/* 134 */   private BooleanList seriesShapesFilled = new BooleanList();
/*     */   private boolean baseShapesFilled = true;
/*     */   private boolean useFillPaint = false;
/*     */   private boolean drawOutlines = false;
/*     */   private boolean useOutlinePaint = false;
/*     */   private boolean useSeriesOffset = true;
/* 140 */   private double itemMargin = 0.2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getUseSeriesOffset() {
/* 152 */     return this.useSeriesOffset;
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
/*     */   public void setUseSeriesOffset(boolean offset) {
/* 165 */     this.useSeriesOffset = offset;
/* 166 */     fireChangeEvent();
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
/*     */   public double getItemMargin() {
/* 181 */     return this.itemMargin;
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
/*     */   public void setItemMargin(double margin) {
/* 195 */     if (margin < 0.0D || margin >= 1.0D) {
/* 196 */       throw new IllegalArgumentException("Requires 0.0 <= margin < 1.0.");
/*     */     }
/* 198 */     this.itemMargin = margin;
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
/*     */   public boolean getDrawOutlines() {
/* 211 */     return this.drawOutlines;
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
/*     */   public void setDrawOutlines(boolean flag) {
/* 226 */     this.drawOutlines = flag;
/* 227 */     fireChangeEvent();
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
/*     */   public boolean getUseOutlinePaint() {
/* 239 */     return this.useOutlinePaint;
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
/*     */   public void setUseOutlinePaint(boolean use) {
/* 252 */     this.useOutlinePaint = use;
/* 253 */     fireChangeEvent();
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
/*     */   public boolean getItemShapeFilled(int series, int item) {
/* 269 */     return getSeriesShapesFilled(series);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSeriesShapesFilled(int series) {
/* 280 */     Boolean flag = this.seriesShapesFilled.getBoolean(series);
/* 281 */     if (flag != null) {
/* 282 */       return flag.booleanValue();
/*     */     }
/*     */     
/* 285 */     return this.baseShapesFilled;
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
/*     */   public void setSeriesShapesFilled(int series, Boolean filled) {
/* 298 */     this.seriesShapesFilled.setBoolean(series, filled);
/* 299 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeriesShapesFilled(int series, boolean filled) {
/* 310 */     this.seriesShapesFilled.setBoolean(series, 
/* 311 */         BooleanUtilities.valueOf(filled));
/* 312 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBaseShapesFilled() {
/* 321 */     return this.baseShapesFilled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseShapesFilled(boolean flag) {
/* 331 */     this.baseShapesFilled = flag;
/* 332 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getUseFillPaint() {
/* 343 */     return this.useFillPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseFillPaint(boolean flag) {
/* 354 */     this.useFillPaint = flag;
/* 355 */     fireChangeEvent();
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
/* 370 */     return findRangeBounds(dataset, true);
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
/* 394 */     if (!getItemVisible(row, column)) {
/*     */       return;
/*     */     }
/* 397 */     int visibleRow = state.getVisibleSeriesIndex(row);
/* 398 */     if (visibleRow < 0) {
/*     */       return;
/*     */     }
/* 401 */     int visibleRowCount = state.getVisibleSeriesCount();
/*     */     
/* 403 */     PlotOrientation orientation = plot.getOrientation();
/*     */     
/* 405 */     MultiValueCategoryDataset d = (MultiValueCategoryDataset)dataset;
/* 406 */     List<Number> values = d.getValues(row, column);
/* 407 */     if (values == null) {
/*     */       return;
/*     */     }
/* 410 */     int valueCount = values.size();
/* 411 */     for (int i = 0; i < valueCount; i++) {
/*     */       double x1;
/*     */       
/* 414 */       if (this.useSeriesOffset) {
/* 415 */         x1 = domainAxis.getCategorySeriesMiddle(column, dataset
/* 416 */             .getColumnCount(), visibleRow, visibleRowCount, this.itemMargin, dataArea, plot
/* 417 */             .getDomainAxisEdge());
/*     */       } else {
/*     */         
/* 420 */         x1 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot
/* 421 */             .getDomainAxisEdge());
/*     */       } 
/* 423 */       Number n = values.get(i);
/* 424 */       double value = n.doubleValue();
/* 425 */       double y1 = rangeAxis.valueToJava2D(value, dataArea, plot
/* 426 */           .getRangeAxisEdge());
/*     */       
/* 428 */       Shape shape = getItemShape(row, column);
/* 429 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 430 */         shape = ShapeUtilities.createTranslatedShape(shape, y1, x1);
/*     */       }
/* 432 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 433 */         shape = ShapeUtilities.createTranslatedShape(shape, x1, y1);
/*     */       } 
/* 435 */       if (getItemShapeFilled(row, column)) {
/* 436 */         if (this.useFillPaint) {
/* 437 */           g2.setPaint(getItemFillPaint(row, column));
/*     */         } else {
/*     */           
/* 440 */           g2.setPaint(getItemPaint(row, column));
/*     */         } 
/* 442 */         g2.fill(shape);
/*     */       } 
/* 444 */       if (this.drawOutlines) {
/* 445 */         if (this.useOutlinePaint) {
/* 446 */           g2.setPaint(getItemOutlinePaint(row, column));
/*     */         } else {
/*     */           
/* 449 */           g2.setPaint(getItemPaint(row, column));
/*     */         } 
/* 451 */         g2.setStroke(getItemOutlineStroke(row, column));
/* 452 */         g2.draw(shape);
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
/*     */   public LegendItem getLegendItem(int datasetIndex, int series) {
/* 469 */     CategoryPlot cp = getPlot();
/* 470 */     if (cp == null) {
/* 471 */       return null;
/*     */     }
/*     */     
/* 474 */     if (isSeriesVisible(series) && isSeriesVisibleInLegend(series)) {
/* 475 */       CategoryDataset dataset = cp.getDataset(datasetIndex);
/* 476 */       String label = getLegendItemLabelGenerator().generateLabel(dataset, series);
/*     */       
/* 478 */       String description = label;
/* 479 */       String toolTipText = null;
/* 480 */       if (getLegendItemToolTipGenerator() != null) {
/* 481 */         toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*     */       }
/*     */       
/* 484 */       String urlText = null;
/* 485 */       if (getLegendItemURLGenerator() != null) {
/* 486 */         urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*     */       }
/*     */       
/* 489 */       Shape shape = lookupLegendShape(series);
/* 490 */       Paint paint = lookupSeriesPaint(series);
/*     */       
/* 492 */       Paint fillPaint = this.useFillPaint ? getItemFillPaint(series, 0) : paint;
/* 493 */       boolean shapeOutlineVisible = this.drawOutlines;
/*     */       
/* 495 */       Paint outlinePaint = this.useOutlinePaint ? getItemOutlinePaint(series, 0) : paint;
/* 496 */       Stroke outlineStroke = lookupSeriesOutlineStroke(series);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 501 */       LegendItem result = new LegendItem(label, description, toolTipText, urlText, true, shape, getItemShapeFilled(series, 0), fillPaint, shapeOutlineVisible, outlinePaint, outlineStroke, false, new Line2D.Double(-7.0D, 0.0D, 7.0D, 0.0D), getItemStroke(series, 0), getItemPaint(series, 0));
/* 502 */       result.setLabelFont(lookupLegendTextFont(series));
/* 503 */       Paint labelPaint = lookupLegendTextPaint(series);
/* 504 */       if (labelPaint != null) {
/* 505 */         result.setLabelPaint(labelPaint);
/*     */       }
/* 507 */       result.setDataset((Dataset)dataset);
/* 508 */       result.setDatasetIndex(datasetIndex);
/* 509 */       result.setSeriesKey(dataset.getRowKey(series));
/* 510 */       result.setSeriesIndex(series);
/* 511 */       return result;
/*     */     } 
/* 513 */     return null;
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
/* 525 */     if (obj == this) {
/* 526 */       return true;
/*     */     }
/* 528 */     if (!(obj instanceof ScatterRenderer)) {
/* 529 */       return false;
/*     */     }
/* 531 */     ScatterRenderer that = (ScatterRenderer)obj;
/* 532 */     if (!ObjectUtilities.equal(this.seriesShapesFilled, that.seriesShapesFilled))
/*     */     {
/* 534 */       return false;
/*     */     }
/* 536 */     if (this.baseShapesFilled != that.baseShapesFilled) {
/* 537 */       return false;
/*     */     }
/* 539 */     if (this.useFillPaint != that.useFillPaint) {
/* 540 */       return false;
/*     */     }
/* 542 */     if (this.drawOutlines != that.drawOutlines) {
/* 543 */       return false;
/*     */     }
/* 545 */     if (this.useOutlinePaint != that.useOutlinePaint) {
/* 546 */       return false;
/*     */     }
/* 548 */     if (this.useSeriesOffset != that.useSeriesOffset) {
/* 549 */       return false;
/*     */     }
/* 551 */     if (this.itemMargin != that.itemMargin) {
/* 552 */       return false;
/*     */     }
/* 554 */     return super.equals(obj);
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
/* 566 */     ScatterRenderer clone = (ScatterRenderer)super.clone();
/* 567 */     clone
/* 568 */       .seriesShapesFilled = (BooleanList)this.seriesShapesFilled.clone();
/* 569 */     return clone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 579 */     stream.defaultWriteObject();
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
/* 592 */     stream.defaultReadObject();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/ScatterRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */