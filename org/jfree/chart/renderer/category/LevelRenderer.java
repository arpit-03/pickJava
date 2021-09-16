/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.CategoryItemLabelGenerator;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.ui.RectangleEdge;
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
/*     */ public class LevelRenderer
/*     */   extends AbstractCategoryItemRenderer
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8204856624355025117L;
/*     */   public static final double DEFAULT_ITEM_MARGIN = 0.2D;
/*     */   private double itemMargin;
/*     */   private double maxItemWidth;
/*     */   
/*     */   public LevelRenderer() {
/* 104 */     this.itemMargin = 0.2D;
/* 105 */     this.maxItemWidth = 1.0D;
/*     */     
/* 107 */     setBaseLegendShape(new Rectangle2D.Float(-5.0F, -1.0F, 10.0F, 2.0F));
/*     */ 
/*     */     
/* 110 */     setBaseOutlinePaint(new Color(0, 0, 0, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getItemMargin() {
/* 121 */     return this.itemMargin;
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
/*     */   public void setItemMargin(double percent) {
/* 135 */     this.itemMargin = percent;
/* 136 */     fireChangeEvent();
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
/*     */   public double getMaximumItemWidth() {
/* 148 */     return getMaxItemWidth();
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
/*     */   public void setMaximumItemWidth(double percent) {
/* 161 */     setMaxItemWidth(percent);
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
/*     */   public CategoryItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, CategoryPlot plot, int rendererIndex, PlotRenderingInfo info) {
/* 184 */     CategoryItemRendererState state = super.initialise(g2, dataArea, plot, rendererIndex, info);
/*     */     
/* 186 */     calculateItemWidth(plot, dataArea, rendererIndex, state);
/* 187 */     return state;
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
/*     */   protected void calculateItemWidth(CategoryPlot plot, Rectangle2D dataArea, int rendererIndex, CategoryItemRendererState state) {
/* 203 */     CategoryAxis domainAxis = getDomainAxis(plot, rendererIndex);
/* 204 */     CategoryDataset dataset = plot.getDataset(rendererIndex);
/* 205 */     if (dataset != null) {
/* 206 */       int columns = dataset.getColumnCount();
/*     */       
/* 208 */       int rows = (state.getVisibleSeriesCount() >= 0) ? state.getVisibleSeriesCount() : dataset.getRowCount();
/* 209 */       double space = 0.0D;
/* 210 */       PlotOrientation orientation = plot.getOrientation();
/* 211 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 212 */         space = dataArea.getHeight();
/*     */       }
/* 214 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 215 */         space = dataArea.getWidth();
/*     */       } 
/* 217 */       double maxWidth = space * getMaximumItemWidth();
/* 218 */       double categoryMargin = 0.0D;
/* 219 */       double currentItemMargin = 0.0D;
/* 220 */       if (columns > 1) {
/* 221 */         categoryMargin = domainAxis.getCategoryMargin();
/*     */       }
/* 223 */       if (rows > 1) {
/* 224 */         currentItemMargin = getItemMargin();
/*     */       }
/*     */       
/* 227 */       double used = space * (1.0D - domainAxis.getLowerMargin() - domainAxis.getUpperMargin() - categoryMargin - currentItemMargin);
/*     */       
/* 229 */       if (rows * columns > 0) {
/* 230 */         state.setBarWidth(Math.min(used / (rows * columns), maxWidth));
/*     */       } else {
/*     */         
/* 233 */         state.setBarWidth(Math.min(used, maxWidth));
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
/*     */   protected double calculateBarW0(CategoryPlot plot, PlotOrientation orientation, Rectangle2D dataArea, CategoryAxis domainAxis, CategoryItemRendererState state, int row, int column) {
/*     */     double space;
/* 259 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 260 */       space = dataArea.getHeight();
/*     */     } else {
/*     */       
/* 263 */       space = dataArea.getWidth();
/*     */     } 
/* 265 */     double barW0 = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, plot
/* 266 */         .getDomainAxisEdge());
/* 267 */     int seriesCount = state.getVisibleSeriesCount();
/* 268 */     if (seriesCount < 0) {
/* 269 */       seriesCount = getRowCount();
/*     */     }
/* 271 */     int categoryCount = getColumnCount();
/* 272 */     if (seriesCount > 1) {
/* 273 */       double seriesGap = space * getItemMargin() / (categoryCount * (seriesCount - 1));
/*     */       
/* 275 */       double seriesW = calculateSeriesWidth(space, domainAxis, categoryCount, seriesCount);
/*     */ 
/*     */       
/* 278 */       barW0 = barW0 + row * (seriesW + seriesGap) + seriesW / 2.0D - state.getBarWidth() / 2.0D;
/*     */     }
/*     */     else {
/*     */       
/* 282 */       barW0 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot.getDomainAxisEdge()) - state.getBarWidth() / 2.0D;
/*     */     } 
/*     */     
/* 285 */     return barW0;
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
/*     */     Line2D line;
/*     */     double x, y;
/* 310 */     int visibleRow = state.getVisibleSeriesIndex(row);
/* 311 */     if (visibleRow < 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 316 */     Number dataValue = dataset.getValue(row, column);
/* 317 */     if (dataValue == null) {
/*     */       return;
/*     */     }
/*     */     
/* 321 */     double value = dataValue.doubleValue();
/*     */     
/* 323 */     PlotOrientation orientation = plot.getOrientation();
/* 324 */     double barW0 = calculateBarW0(plot, orientation, dataArea, domainAxis, state, visibleRow, column);
/*     */     
/* 326 */     RectangleEdge edge = plot.getRangeAxisEdge();
/* 327 */     double barL = rangeAxis.valueToJava2D(value, dataArea, edge);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 332 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 333 */       x = barL;
/* 334 */       y = barW0 + state.getBarWidth() / 2.0D;
/*     */       
/* 336 */       line = new Line2D.Double(barL, barW0, barL, barW0 + state.getBarWidth());
/*     */     } else {
/*     */       
/* 339 */       x = barW0 + state.getBarWidth() / 2.0D;
/* 340 */       y = barL;
/* 341 */       line = new Line2D.Double(barW0, barL, barW0 + state.getBarWidth(), barL);
/*     */     } 
/*     */     
/* 344 */     Stroke itemStroke = getItemStroke(row, column);
/* 345 */     Paint itemPaint = getItemPaint(row, column);
/* 346 */     g2.setStroke(itemStroke);
/* 347 */     g2.setPaint(itemPaint);
/* 348 */     g2.draw(line);
/*     */     
/* 350 */     CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
/*     */     
/* 352 */     if (generator != null && isItemLabelVisible(row, column)) {
/* 353 */       drawItemLabel(g2, orientation, dataset, row, column, x, y, (value < 0.0D));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 358 */     int datasetIndex = plot.indexOf(dataset);
/* 359 */     updateCrosshairValues(state.getCrosshairState(), dataset
/* 360 */         .getRowKey(row), dataset.getColumnKey(column), value, datasetIndex, barW0, barL, orientation);
/*     */ 
/*     */ 
/*     */     
/* 364 */     EntityCollection entities = state.getEntityCollection();
/* 365 */     if (entities != null) {
/* 366 */       addItemEntity(entities, dataset, row, column, line.getBounds());
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
/*     */   protected double calculateSeriesWidth(double space, CategoryAxis axis, int categories, int series) {
/* 384 */     double factor = 1.0D - getItemMargin() - axis.getLowerMargin() - axis.getUpperMargin();
/* 385 */     if (categories > 1) {
/* 386 */       factor -= axis.getCategoryMargin();
/*     */     }
/* 388 */     return space * factor / (categories * series);
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
/* 409 */     return axis.getCategorySeriesMiddle(columnKey, rowKey, dataset, this.itemMargin, area, edge);
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
/* 422 */     if (obj == this) {
/* 423 */       return true;
/*     */     }
/* 425 */     if (!(obj instanceof LevelRenderer)) {
/* 426 */       return false;
/*     */     }
/* 428 */     LevelRenderer that = (LevelRenderer)obj;
/* 429 */     if (this.itemMargin != that.itemMargin) {
/* 430 */       return false;
/*     */     }
/* 432 */     if (this.maxItemWidth != that.maxItemWidth) {
/* 433 */       return false;
/*     */     }
/* 435 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 445 */     int hash = super.hashCode();
/* 446 */     hash = HashUtilities.hashCode(hash, this.itemMargin);
/* 447 */     hash = HashUtilities.hashCode(hash, this.maxItemWidth);
/* 448 */     return hash;
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
/*     */   public double getMaxItemWidth() {
/* 460 */     return this.maxItemWidth;
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
/*     */   public void setMaxItemWidth(double percent) {
/* 473 */     this.maxItemWidth = percent;
/* 474 */     fireChangeEvent();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/LevelRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */