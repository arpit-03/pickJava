/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.CategoryItemLabelGenerator;
/*     */ import org.jfree.chart.labels.ItemLabelAnchor;
/*     */ import org.jfree.chart.labels.ItemLabelPosition;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.data.DataUtilities;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.Values2D;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.general.DatasetUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.TextAnchor;
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
/*     */ public class StackedBarRenderer
/*     */   extends BarRenderer
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 6402943811500067531L;
/*     */   private boolean renderAsPercentages;
/*     */   
/*     */   public StackedBarRenderer() {
/* 144 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackedBarRenderer(boolean renderAsPercentages) {
/* 155 */     this.renderAsPercentages = renderAsPercentages;
/*     */ 
/*     */ 
/*     */     
/* 159 */     ItemLabelPosition p = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
/*     */     
/* 161 */     setBasePositiveItemLabelPosition(p);
/* 162 */     setBaseNegativeItemLabelPosition(p);
/* 163 */     setPositiveItemLabelPositionFallback((ItemLabelPosition)null);
/* 164 */     setNegativeItemLabelPositionFallback((ItemLabelPosition)null);
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
/* 177 */     return this.renderAsPercentages;
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
/* 190 */     this.renderAsPercentages = asPercentages;
/* 191 */     fireChangeEvent();
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
/*     */   public int getPassCount() {
/* 204 */     return 3;
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
/* 217 */     if (dataset == null) {
/* 218 */       return null;
/*     */     }
/* 220 */     if (this.renderAsPercentages) {
/* 221 */       return new Range(0.0D, 1.0D);
/*     */     }
/*     */     
/* 224 */     return DatasetUtilities.findStackedRangeBounds(dataset, getBase());
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
/*     */   protected void calculateBarWidth(CategoryPlot plot, Rectangle2D dataArea, int rendererIndex, CategoryItemRendererState state) {
/* 241 */     CategoryAxis xAxis = plot.getDomainAxisForDataset(rendererIndex);
/* 242 */     CategoryDataset data = plot.getDataset(rendererIndex);
/* 243 */     if (data != null) {
/* 244 */       PlotOrientation orientation = plot.getOrientation();
/* 245 */       double space = 0.0D;
/* 246 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 247 */         space = dataArea.getHeight();
/*     */       }
/* 249 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 250 */         space = dataArea.getWidth();
/*     */       } 
/* 252 */       double maxWidth = space * getMaximumBarWidth();
/* 253 */       int columns = data.getColumnCount();
/* 254 */       double categoryMargin = 0.0D;
/* 255 */       if (columns > 1) {
/* 256 */         categoryMargin = xAxis.getCategoryMargin();
/*     */       }
/*     */ 
/*     */       
/* 260 */       double used = space * (1.0D - xAxis.getLowerMargin() - xAxis.getUpperMargin() - categoryMargin);
/*     */       
/* 262 */       if (columns > 0) {
/* 263 */         state.setBarWidth(Math.min(used / columns, maxWidth));
/*     */       } else {
/*     */         
/* 266 */         state.setBarWidth(Math.min(used, maxWidth));
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
/*     */   public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {
/*     */     double translatedBase, translatedValue;
/*     */     RectangleEdge barBase;
/*     */     Rectangle2D bar;
/* 292 */     if (!isSeriesVisible(row)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 297 */     Number dataValue = dataset.getValue(row, column);
/* 298 */     if (dataValue == null) {
/*     */       return;
/*     */     }
/*     */     
/* 302 */     double value = dataValue.doubleValue();
/* 303 */     double total = 0.0D;
/* 304 */     if (this.renderAsPercentages) {
/* 305 */       total = DataUtilities.calculateColumnTotal((Values2D)dataset, column, state
/* 306 */           .getVisibleSeriesArray());
/* 307 */       value /= total;
/*     */     } 
/*     */     
/* 310 */     PlotOrientation orientation = plot.getOrientation();
/*     */ 
/*     */     
/* 313 */     double barW0 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot.getDomainAxisEdge()) - state.getBarWidth() / 2.0D;
/*     */     
/* 315 */     double positiveBase = getBase();
/* 316 */     double negativeBase = positiveBase;
/*     */     
/* 318 */     for (int i = 0; i < row; i++) {
/* 319 */       Number v = dataset.getValue(i, column);
/* 320 */       if (v != null && isSeriesVisible(i)) {
/* 321 */         double d = v.doubleValue();
/* 322 */         if (this.renderAsPercentages) {
/* 323 */           d /= total;
/*     */         }
/* 325 */         if (d > 0.0D) {
/* 326 */           positiveBase += d;
/*     */         } else {
/*     */           
/* 329 */           negativeBase += d;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 336 */     boolean positive = (value > 0.0D);
/* 337 */     boolean inverted = rangeAxis.isInverted();
/*     */     
/* 339 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 340 */       if ((positive && inverted) || (!positive && !inverted)) {
/* 341 */         barBase = RectangleEdge.RIGHT;
/*     */       } else {
/*     */         
/* 344 */         barBase = RectangleEdge.LEFT;
/*     */       }
/*     */     
/*     */     }
/* 348 */     else if ((positive && !inverted) || (!positive && inverted)) {
/* 349 */       barBase = RectangleEdge.BOTTOM;
/*     */     } else {
/*     */       
/* 352 */       barBase = RectangleEdge.TOP;
/*     */     } 
/*     */ 
/*     */     
/* 356 */     RectangleEdge location = plot.getRangeAxisEdge();
/* 357 */     if (positive) {
/* 358 */       translatedBase = rangeAxis.valueToJava2D(positiveBase, dataArea, location);
/*     */       
/* 360 */       translatedValue = rangeAxis.valueToJava2D(positiveBase + value, dataArea, location);
/*     */     }
/*     */     else {
/*     */       
/* 364 */       translatedBase = rangeAxis.valueToJava2D(negativeBase, dataArea, location);
/*     */       
/* 366 */       translatedValue = rangeAxis.valueToJava2D(negativeBase + value, dataArea, location);
/*     */     } 
/*     */     
/* 369 */     double barL0 = Math.min(translatedBase, translatedValue);
/* 370 */     double barLength = Math.max(Math.abs(translatedValue - translatedBase), 
/* 371 */         getMinimumBarLength());
/*     */ 
/*     */     
/* 374 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*     */       
/* 376 */       bar = new Rectangle2D.Double(barL0, barW0, barLength, state.getBarWidth());
/*     */     } else {
/*     */       
/* 379 */       bar = new Rectangle2D.Double(barW0, barL0, state.getBarWidth(), barLength);
/*     */     } 
/*     */     
/* 382 */     if (pass == 0) {
/* 383 */       if (getShadowsVisible())
/*     */       {
/* 385 */         boolean pegToBase = ((positive && positiveBase == getBase()) || (!positive && negativeBase == getBase()));
/* 386 */         getBarPainter().paintBarShadow(g2, this, row, column, bar, barBase, pegToBase);
/*     */       }
/*     */     
/*     */     }
/* 390 */     else if (pass == 1) {
/* 391 */       getBarPainter().paintBar(g2, this, row, column, bar, barBase);
/*     */ 
/*     */       
/* 394 */       EntityCollection entities = state.getEntityCollection();
/* 395 */       if (entities != null) {
/* 396 */         addItemEntity(entities, dataset, row, column, bar);
/*     */       }
/*     */     }
/* 399 */     else if (pass == 2) {
/* 400 */       CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
/*     */       
/* 402 */       if (generator != null && isItemLabelVisible(row, column)) {
/* 403 */         drawItemLabel(g2, dataset, row, column, plot, generator, bar, (value < 0.0D));
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
/* 418 */     if (obj == this) {
/* 419 */       return true;
/*     */     }
/* 421 */     if (!(obj instanceof StackedBarRenderer)) {
/* 422 */       return false;
/*     */     }
/* 424 */     StackedBarRenderer that = (StackedBarRenderer)obj;
/* 425 */     if (this.renderAsPercentages != that.renderAsPercentages) {
/* 426 */       return false;
/*     */     }
/* 428 */     return super.equals(obj);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/StackedBarRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */