/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.CategoryItemLabelGenerator;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.IntervalCategoryDataset;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntervalBarRenderer
/*     */   extends BarRenderer
/*     */ {
/*     */   private static final long serialVersionUID = -5068857361615528725L;
/*     */   
/*     */   public Range findRangeBounds(CategoryDataset dataset) {
/* 118 */     return findRangeBounds(dataset, true);
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
/* 141 */     if (dataset instanceof IntervalCategoryDataset) {
/* 142 */       IntervalCategoryDataset d = (IntervalCategoryDataset)dataset;
/* 143 */       drawInterval(g2, state, dataArea, plot, domainAxis, rangeAxis, d, row, column);
/*     */     }
/*     */     else {
/*     */       
/* 147 */       super.drawItem(g2, state, dataArea, plot, domainAxis, rangeAxis, dataset, row, column, pass);
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
/*     */   protected void drawInterval(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, IntervalCategoryDataset dataset, int row, int column) {
/* 176 */     int visibleRow = state.getVisibleSeriesIndex(row);
/* 177 */     if (visibleRow < 0) {
/*     */       return;
/*     */     }
/*     */     
/* 181 */     PlotOrientation orientation = plot.getOrientation();
/* 182 */     double rectX = 0.0D;
/* 183 */     double rectY = 0.0D;
/*     */     
/* 185 */     RectangleEdge rangeAxisLocation = plot.getRangeAxisEdge();
/*     */ 
/*     */     
/* 188 */     Number value0 = dataset.getEndValue(row, column);
/* 189 */     if (value0 == null) {
/*     */       return;
/*     */     }
/* 192 */     double java2dValue0 = rangeAxis.valueToJava2D(value0.doubleValue(), dataArea, rangeAxisLocation);
/*     */ 
/*     */ 
/*     */     
/* 196 */     Number value1 = dataset.getStartValue(row, column);
/* 197 */     if (value1 == null) {
/*     */       return;
/*     */     }
/* 200 */     double java2dValue1 = rangeAxis.valueToJava2D(value1
/* 201 */         .doubleValue(), dataArea, rangeAxisLocation);
/*     */     
/* 203 */     if (java2dValue1 < java2dValue0) {
/* 204 */       double temp = java2dValue1;
/* 205 */       java2dValue1 = java2dValue0;
/* 206 */       java2dValue0 = temp;
/*     */     } 
/*     */ 
/*     */     
/* 210 */     double rectWidth = state.getBarWidth();
/*     */ 
/*     */     
/* 213 */     double rectHeight = Math.abs(java2dValue1 - java2dValue0);
/*     */     
/* 215 */     RectangleEdge barBase = RectangleEdge.LEFT;
/* 216 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*     */       
/* 218 */       rectX = java2dValue0;
/* 219 */       rectY = calculateBarW0(getPlot(), orientation, dataArea, domainAxis, state, visibleRow, column);
/*     */       
/* 221 */       rectHeight = state.getBarWidth();
/* 222 */       rectWidth = Math.abs(java2dValue1 - java2dValue0);
/* 223 */       barBase = RectangleEdge.LEFT;
/*     */     }
/* 225 */     else if (orientation == PlotOrientation.VERTICAL) {
/*     */       
/* 227 */       rectX = calculateBarW0(getPlot(), orientation, dataArea, domainAxis, state, visibleRow, column);
/*     */       
/* 229 */       rectY = java2dValue0;
/* 230 */       barBase = RectangleEdge.BOTTOM;
/*     */     } 
/* 232 */     Rectangle2D bar = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
/*     */     
/* 234 */     BarPainter painter = getBarPainter();
/* 235 */     if (getShadowsVisible()) {
/* 236 */       painter.paintBarShadow(g2, this, row, column, bar, barBase, false);
/*     */     }
/* 238 */     getBarPainter().paintBar(g2, this, row, column, bar, barBase);
/*     */     
/* 240 */     CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
/*     */     
/* 242 */     if (generator != null && isItemLabelVisible(row, column)) {
/* 243 */       drawItemLabel(g2, (CategoryDataset)dataset, row, column, plot, generator, bar, false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 248 */     EntityCollection entities = state.getEntityCollection();
/* 249 */     if (entities != null) {
/* 250 */       addItemEntity(entities, (CategoryDataset)dataset, row, column, bar);
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
/* 264 */     if (obj == this) {
/* 265 */       return true;
/*     */     }
/* 267 */     if (!(obj instanceof IntervalBarRenderer)) {
/* 268 */       return false;
/*     */     }
/*     */     
/* 271 */     return super.equals(obj);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/IntervalBarRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */