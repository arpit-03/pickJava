/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.LegendItem;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.general.Dataset;
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
/*     */ public class CategoryStepRenderer
/*     */   extends AbstractCategoryItemRenderer
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5121079703118261470L;
/*     */   public static final int STAGGER_WIDTH = 5;
/*     */   
/*     */   protected static class State
/*     */     extends CategoryItemRendererState
/*     */   {
/*     */     public Line2D line;
/*     */     
/*     */     public State(PlotRenderingInfo info) {
/* 103 */       super(info);
/* 104 */       this.line = new Line2D.Double();
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
/*     */   private boolean stagger = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CategoryStepRenderer() {
/* 125 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CategoryStepRenderer(boolean stagger) {
/* 135 */     this.stagger = stagger;
/* 136 */     setBaseLegendShape(new Rectangle2D.Double(-4.0D, -3.0D, 8.0D, 6.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getStagger() {
/* 145 */     return this.stagger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStagger(boolean shouldStagger) {
/* 156 */     this.stagger = shouldStagger;
/* 157 */     fireChangeEvent();
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
/*     */   public LegendItem getLegendItem(int datasetIndex, int series) {
/* 171 */     CategoryPlot p = getPlot();
/* 172 */     if (p == null) {
/* 173 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 177 */     if (!isSeriesVisible(series) || !isSeriesVisibleInLegend(series)) {
/* 178 */       return null;
/*     */     }
/*     */     
/* 181 */     CategoryDataset dataset = p.getDataset(datasetIndex);
/* 182 */     String label = getLegendItemLabelGenerator().generateLabel(dataset, series);
/*     */     
/* 184 */     String description = label;
/* 185 */     String toolTipText = null;
/* 186 */     if (getLegendItemToolTipGenerator() != null) {
/* 187 */       toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*     */     }
/*     */     
/* 190 */     String urlText = null;
/* 191 */     if (getLegendItemURLGenerator() != null) {
/* 192 */       urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*     */     }
/*     */     
/* 195 */     Shape shape = lookupLegendShape(series);
/* 196 */     Paint paint = lookupSeriesPaint(series);
/*     */     
/* 198 */     LegendItem item = new LegendItem(label, description, toolTipText, urlText, shape, paint);
/*     */     
/* 200 */     item.setLabelFont(lookupLegendTextFont(series));
/* 201 */     Paint labelPaint = lookupLegendTextPaint(series);
/* 202 */     if (labelPaint != null) {
/* 203 */       item.setLabelPaint(labelPaint);
/*     */     }
/* 205 */     item.setSeriesKey(dataset.getRowKey(series));
/* 206 */     item.setSeriesIndex(series);
/* 207 */     item.setDataset((Dataset)dataset);
/* 208 */     item.setDatasetIndex(datasetIndex);
/* 209 */     return item;
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
/*     */   protected CategoryItemRendererState createState(PlotRenderingInfo info) {
/* 224 */     return new State(info);
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
/*     */   protected void drawLine(Graphics2D g2, State state, PlotOrientation orientation, double x0, double y0, double x1, double y1) {
/* 248 */     if (orientation == PlotOrientation.VERTICAL) {
/* 249 */       state.line.setLine(x0, y0, x1, y1);
/* 250 */       g2.draw(state.line);
/*     */     }
/* 252 */     else if (orientation == PlotOrientation.HORIZONTAL) {
/* 253 */       state.line.setLine(y0, x0, y1, x1);
/* 254 */       g2.draw(state.line);
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
/*     */   public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {
/* 280 */     if (!getItemVisible(row, column)) {
/*     */       return;
/*     */     }
/*     */     
/* 284 */     Number value = dataset.getValue(row, column);
/* 285 */     if (value == null) {
/*     */       return;
/*     */     }
/* 288 */     PlotOrientation orientation = plot.getOrientation();
/*     */ 
/*     */     
/* 291 */     double x1s = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, plot
/* 292 */         .getDomainAxisEdge());
/* 293 */     double x1 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot
/* 294 */         .getDomainAxisEdge());
/* 295 */     double x1e = 2.0D * x1 - x1s;
/* 296 */     double y1 = rangeAxis.valueToJava2D(value.doubleValue(), dataArea, plot
/* 297 */         .getRangeAxisEdge());
/* 298 */     g2.setPaint(getItemPaint(row, column));
/* 299 */     g2.setStroke(getItemStroke(row, column));
/*     */     
/* 301 */     if (column != 0) {
/* 302 */       Number previousValue = dataset.getValue(row, column - 1);
/* 303 */       if (previousValue != null) {
/*     */         
/* 305 */         double previous = previousValue.doubleValue();
/* 306 */         double x0s = domainAxis.getCategoryStart(column - 1, 
/* 307 */             getColumnCount(), dataArea, plot.getDomainAxisEdge());
/* 308 */         double x0 = domainAxis.getCategoryMiddle(column - 1, 
/* 309 */             getColumnCount(), dataArea, plot.getDomainAxisEdge());
/* 310 */         double x0e = 2.0D * x0 - x0s;
/* 311 */         double y0 = rangeAxis.valueToJava2D(previous, dataArea, plot
/* 312 */             .getRangeAxisEdge());
/* 313 */         if (getStagger()) {
/* 314 */           int xStagger = row * 5;
/* 315 */           if (xStagger > x1s - x0e) {
/* 316 */             xStagger = (int)(x1s - x0e);
/*     */           }
/* 318 */           x1s = x0e + xStagger;
/*     */         } 
/* 320 */         drawLine(g2, (State)state, orientation, x0e, y0, x1s, y0);
/*     */ 
/*     */         
/* 323 */         drawLine(g2, (State)state, orientation, x1s, y0, x1s, y1);
/*     */       } 
/*     */     } 
/*     */     
/* 327 */     drawLine(g2, (State)state, orientation, x1s, y1, x1e, y1);
/*     */ 
/*     */ 
/*     */     
/* 331 */     if (isItemLabelVisible(row, column)) {
/* 332 */       drawItemLabel(g2, orientation, dataset, row, column, x1, y1, 
/* 333 */           (value.doubleValue() < 0.0D));
/*     */     }
/*     */ 
/*     */     
/* 337 */     EntityCollection entities = state.getEntityCollection();
/* 338 */     if (entities != null) {
/* 339 */       Rectangle2D hotspot = new Rectangle2D.Double();
/* 340 */       if (orientation == PlotOrientation.VERTICAL) {
/* 341 */         hotspot.setRect(x1s, y1, x1e - x1s, 4.0D);
/*     */       } else {
/*     */         
/* 344 */         hotspot.setRect(y1 - 2.0D, x1s, 4.0D, x1e - x1s);
/*     */       } 
/* 346 */       addItemEntity(entities, dataset, row, column, hotspot);
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
/* 360 */     if (obj == this) {
/* 361 */       return true;
/*     */     }
/* 363 */     if (!(obj instanceof CategoryStepRenderer)) {
/* 364 */       return false;
/*     */     }
/* 366 */     CategoryStepRenderer that = (CategoryStepRenderer)obj;
/* 367 */     if (this.stagger != that.stagger) {
/* 368 */       return false;
/*     */     }
/* 370 */     return super.equals(obj);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/CategoryStepRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */