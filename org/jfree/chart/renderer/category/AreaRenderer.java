/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.LegendItem;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.renderer.AreaRendererEndType;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.general.Dataset;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AreaRenderer
/*     */   extends AbstractCategoryItemRenderer
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4231878281385812757L;
/*     */   private AreaRendererEndType endType;
/*     */   
/*     */   public AreaRenderer() {
/* 121 */     this.endType = AreaRendererEndType.TAPER;
/* 122 */     setBaseLegendShape(new Rectangle2D.Double(-4.0D, -4.0D, 8.0D, 8.0D));
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
/*     */   public AreaRendererEndType getEndType() {
/* 134 */     return this.endType;
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
/*     */   public void setEndType(AreaRendererEndType type) {
/* 146 */     ParamChecks.nullNotPermitted(type, "type");
/* 147 */     this.endType = type;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegendItem getLegendItem(int datasetIndex, int series) {
/* 163 */     CategoryPlot cp = getPlot();
/* 164 */     if (cp == null) {
/* 165 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 169 */     if (!isSeriesVisible(series) || !isSeriesVisibleInLegend(series)) {
/* 170 */       return null;
/*     */     }
/*     */     
/* 173 */     CategoryDataset dataset = cp.getDataset(datasetIndex);
/* 174 */     String label = getLegendItemLabelGenerator().generateLabel(dataset, series);
/*     */     
/* 176 */     String description = label;
/* 177 */     String toolTipText = null;
/* 178 */     if (getLegendItemToolTipGenerator() != null) {
/* 179 */       toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*     */     }
/*     */     
/* 182 */     String urlText = null;
/* 183 */     if (getLegendItemURLGenerator() != null) {
/* 184 */       urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*     */     }
/*     */     
/* 187 */     Shape shape = lookupLegendShape(series);
/* 188 */     Paint paint = lookupSeriesPaint(series);
/* 189 */     Paint outlinePaint = lookupSeriesOutlinePaint(series);
/* 190 */     Stroke outlineStroke = lookupSeriesOutlineStroke(series);
/*     */     
/* 192 */     LegendItem result = new LegendItem(label, description, toolTipText, urlText, shape, paint, outlineStroke, outlinePaint);
/*     */     
/* 194 */     result.setLabelFont(lookupLegendTextFont(series));
/* 195 */     Paint labelPaint = lookupLegendTextPaint(series);
/* 196 */     if (labelPaint != null) {
/* 197 */       result.setLabelPaint(labelPaint);
/*     */     }
/* 199 */     result.setDataset((Dataset)dataset);
/* 200 */     result.setDatasetIndex(datasetIndex);
/* 201 */     result.setSeriesKey(dataset.getRowKey(series));
/* 202 */     result.setSeriesIndex(series);
/* 203 */     return result;
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
/* 228 */     if (!getItemVisible(row, column)) {
/*     */       return;
/*     */     }
/* 231 */     Number value = dataset.getValue(row, column);
/* 232 */     if (value == null) {
/*     */       return;
/*     */     }
/* 235 */     PlotOrientation orientation = plot.getOrientation();
/* 236 */     RectangleEdge axisEdge = plot.getDomainAxisEdge();
/* 237 */     int count = dataset.getColumnCount();
/* 238 */     float x0 = (float)domainAxis.getCategoryStart(column, count, dataArea, axisEdge);
/*     */     
/* 240 */     float x1 = (float)domainAxis.getCategoryMiddle(column, count, dataArea, axisEdge);
/*     */     
/* 242 */     float x2 = (float)domainAxis.getCategoryEnd(column, count, dataArea, axisEdge);
/*     */ 
/*     */     
/* 245 */     x0 = Math.round(x0);
/* 246 */     x1 = Math.round(x1);
/* 247 */     x2 = Math.round(x2);
/*     */     
/* 249 */     if (this.endType == AreaRendererEndType.TRUNCATE) {
/* 250 */       if (column == 0) {
/* 251 */         x0 = x1;
/*     */       }
/* 253 */       else if (column == getColumnCount() - 1) {
/* 254 */         x2 = x1;
/*     */       } 
/*     */     }
/*     */     
/* 258 */     double yy1 = value.doubleValue();
/*     */     
/* 260 */     double yy0 = 0.0D;
/* 261 */     if (this.endType == AreaRendererEndType.LEVEL) {
/* 262 */       yy0 = yy1;
/*     */     }
/* 264 */     if (column > 0) {
/* 265 */       Number n0 = dataset.getValue(row, column - 1);
/* 266 */       if (n0 != null) {
/* 267 */         yy0 = (n0.doubleValue() + yy1) / 2.0D;
/*     */       }
/*     */     } 
/*     */     
/* 271 */     double yy2 = 0.0D;
/* 272 */     if (column < dataset.getColumnCount() - 1) {
/* 273 */       Number n2 = dataset.getValue(row, column + 1);
/* 274 */       if (n2 != null) {
/* 275 */         yy2 = (n2.doubleValue() + yy1) / 2.0D;
/*     */       }
/*     */     }
/* 278 */     else if (this.endType == AreaRendererEndType.LEVEL) {
/* 279 */       yy2 = yy1;
/*     */     } 
/*     */     
/* 282 */     RectangleEdge edge = plot.getRangeAxisEdge();
/* 283 */     float y0 = (float)rangeAxis.valueToJava2D(yy0, dataArea, edge);
/* 284 */     float y1 = (float)rangeAxis.valueToJava2D(yy1, dataArea, edge);
/* 285 */     float y2 = (float)rangeAxis.valueToJava2D(yy2, dataArea, edge);
/* 286 */     float yz = (float)rangeAxis.valueToJava2D(0.0D, dataArea, edge);
/* 287 */     double labelXX = x1;
/* 288 */     double labelYY = y1;
/* 289 */     g2.setPaint(getItemPaint(row, column));
/* 290 */     g2.setStroke(getItemStroke(row, column));
/*     */     
/* 292 */     GeneralPath area = new GeneralPath();
/*     */     
/* 294 */     if (orientation == PlotOrientation.VERTICAL) {
/* 295 */       area.moveTo(x0, yz);
/* 296 */       area.lineTo(x0, y0);
/* 297 */       area.lineTo(x1, y1);
/* 298 */       area.lineTo(x2, y2);
/* 299 */       area.lineTo(x2, yz);
/*     */     }
/* 301 */     else if (orientation == PlotOrientation.HORIZONTAL) {
/* 302 */       area.moveTo(yz, x0);
/* 303 */       area.lineTo(y0, x0);
/* 304 */       area.lineTo(y1, x1);
/* 305 */       area.lineTo(y2, x2);
/* 306 */       area.lineTo(yz, x2);
/* 307 */       double temp = labelXX;
/* 308 */       labelXX = labelYY;
/* 309 */       labelYY = temp;
/*     */     } 
/* 311 */     area.closePath();
/*     */     
/* 313 */     g2.setPaint(getItemPaint(row, column));
/* 314 */     g2.fill(area);
/*     */ 
/*     */     
/* 317 */     if (isItemLabelVisible(row, column)) {
/* 318 */       drawItemLabel(g2, orientation, dataset, row, column, labelXX, labelYY, 
/* 319 */           (value.doubleValue() < 0.0D));
/*     */     }
/*     */ 
/*     */     
/* 323 */     int datasetIndex = plot.indexOf(dataset);
/* 324 */     updateCrosshairValues(state.getCrosshairState(), dataset
/* 325 */         .getRowKey(row), dataset.getColumnKey(column), yy1, datasetIndex, x1, y1, orientation);
/*     */ 
/*     */ 
/*     */     
/* 329 */     EntityCollection entities = state.getEntityCollection();
/* 330 */     if (entities != null) {
/* 331 */       addItemEntity(entities, dataset, row, column, area);
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
/* 345 */     if (obj == this) {
/* 346 */       return true;
/*     */     }
/* 348 */     if (!(obj instanceof AreaRenderer)) {
/* 349 */       return false;
/*     */     }
/* 351 */     AreaRenderer that = (AreaRenderer)obj;
/* 352 */     if (!this.endType.equals(that.endType)) {
/* 353 */       return false;
/*     */     }
/* 355 */     return super.equals(obj);
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
/* 367 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/AreaRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */