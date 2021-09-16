/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.ItemLabelPosition;
/*     */ import org.jfree.chart.labels.XYItemLabelGenerator;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.xy.IntervalXYDataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.text.TextUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
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
/*     */ public class YIntervalRenderer
/*     */   extends AbstractXYItemRenderer
/*     */   implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2951586537224143260L;
/* 110 */   private XYItemLabelGenerator additionalItemLabelGenerator = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYItemLabelGenerator getAdditionalItemLabelGenerator() {
/* 124 */     return this.additionalItemLabelGenerator;
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
/*     */   public void setAdditionalItemLabelGenerator(XYItemLabelGenerator generator) {
/* 141 */     this.additionalItemLabelGenerator = generator;
/* 142 */     fireChangeEvent();
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
/*     */   public Range findRangeBounds(XYDataset dataset) {
/* 156 */     return findRangeBounds(dataset, true);
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
/*     */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/* 184 */     EntityCollection entities = null;
/* 185 */     if (info != null) {
/* 186 */       entities = info.getOwner().getEntityCollection();
/*     */     }
/*     */     
/* 189 */     IntervalXYDataset intervalDataset = (IntervalXYDataset)dataset;
/*     */     
/* 191 */     double x = intervalDataset.getXValue(series, item);
/* 192 */     double yLow = intervalDataset.getStartYValue(series, item);
/* 193 */     double yHigh = intervalDataset.getEndYValue(series, item);
/*     */     
/* 195 */     RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
/* 196 */     RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
/*     */     
/* 198 */     double xx = domainAxis.valueToJava2D(x, dataArea, xAxisLocation);
/* 199 */     double yyLow = rangeAxis.valueToJava2D(yLow, dataArea, yAxisLocation);
/* 200 */     double yyHigh = rangeAxis.valueToJava2D(yHigh, dataArea, yAxisLocation);
/*     */     
/* 202 */     Paint p = getItemPaint(series, item);
/* 203 */     Stroke s = getItemStroke(series, item);
/*     */     
/* 205 */     Line2D line = null;
/* 206 */     Shape shape = getItemShape(series, item);
/* 207 */     Shape top = null;
/* 208 */     Shape bottom = null;
/* 209 */     PlotOrientation orientation = plot.getOrientation();
/* 210 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 211 */       line = new Line2D.Double(yyLow, xx, yyHigh, xx);
/* 212 */       top = ShapeUtilities.createTranslatedShape(shape, yyHigh, xx);
/* 213 */       bottom = ShapeUtilities.createTranslatedShape(shape, yyLow, xx);
/*     */     }
/* 215 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 216 */       line = new Line2D.Double(xx, yyLow, xx, yyHigh);
/* 217 */       top = ShapeUtilities.createTranslatedShape(shape, xx, yyHigh);
/* 218 */       bottom = ShapeUtilities.createTranslatedShape(shape, xx, yyLow);
/*     */     } else {
/* 220 */       throw new IllegalStateException();
/*     */     } 
/* 222 */     g2.setPaint(p);
/* 223 */     g2.setStroke(s);
/* 224 */     g2.draw(line);
/*     */     
/* 226 */     g2.fill(top);
/* 227 */     g2.fill(bottom);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     if (isItemLabelVisible(series, item)) {
/* 234 */       drawItemLabel(g2, orientation, dataset, series, item, xx, yyHigh, false);
/*     */       
/* 236 */       drawAdditionalItemLabel(g2, orientation, dataset, series, item, xx, yyLow);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 241 */     if (entities != null) {
/* 242 */       addEntity(entities, line.getBounds(), dataset, series, item, 0.0D, 0.0D);
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
/*     */   private void drawAdditionalItemLabel(Graphics2D g2, PlotOrientation orientation, XYDataset dataset, int series, int item, double x, double y) {
/* 263 */     if (this.additionalItemLabelGenerator == null) {
/*     */       return;
/*     */     }
/*     */     
/* 267 */     Font labelFont = getItemLabelFont(series, item);
/* 268 */     Paint paint = getItemLabelPaint(series, item);
/* 269 */     g2.setFont(labelFont);
/* 270 */     g2.setPaint(paint);
/* 271 */     String label = this.additionalItemLabelGenerator.generateLabel(dataset, series, item);
/*     */ 
/*     */     
/* 274 */     ItemLabelPosition position = getNegativeItemLabelPosition(series, item);
/* 275 */     Point2D anchorPoint = calculateLabelAnchorPoint(position
/* 276 */         .getItemLabelAnchor(), x, y, orientation);
/* 277 */     TextUtilities.drawRotatedString(label, g2, 
/* 278 */         (float)anchorPoint.getX(), (float)anchorPoint.getY(), position
/* 279 */         .getTextAnchor(), position.getAngle(), position
/* 280 */         .getRotationAnchor());
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
/* 292 */     if (obj == this) {
/* 293 */       return true;
/*     */     }
/* 295 */     if (!(obj instanceof YIntervalRenderer)) {
/* 296 */       return false;
/*     */     }
/* 298 */     YIntervalRenderer that = (YIntervalRenderer)obj;
/* 299 */     if (!ObjectUtilities.equal(this.additionalItemLabelGenerator, that.additionalItemLabelGenerator))
/*     */     {
/* 301 */       return false;
/*     */     }
/* 303 */     return super.equals(obj);
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
/* 315 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/YIntervalRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */