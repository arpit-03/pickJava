/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.jfree.chart.LegendItem;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.general.Dataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
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
/*     */ public class XYDotRenderer
/*     */   extends AbstractXYItemRenderer
/*     */   implements XYItemRenderer, PublicCloneable
/*     */ {
/*     */   private static final long serialVersionUID = -2764344339073566425L;
/* 112 */   private int dotWidth = 1;
/* 113 */   private int dotHeight = 1;
/* 114 */   private transient Shape legendShape = new Rectangle2D.Double(-3.0D, -3.0D, 6.0D, 6.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDotWidth() {
/* 126 */     return this.dotWidth;
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
/*     */   public void setDotWidth(int w) {
/* 141 */     if (w < 1) {
/* 142 */       throw new IllegalArgumentException("Requires w > 0.");
/*     */     }
/* 144 */     this.dotWidth = w;
/* 145 */     fireChangeEvent();
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
/*     */   public int getDotHeight() {
/* 157 */     return this.dotHeight;
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
/*     */   public void setDotHeight(int h) {
/* 172 */     if (h < 1) {
/* 173 */       throw new IllegalArgumentException("Requires h > 0.");
/*     */     }
/* 175 */     this.dotHeight = h;
/* 176 */     fireChangeEvent();
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
/*     */   public Shape getLegendShape() {
/* 189 */     return this.legendShape;
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
/*     */   public void setLegendShape(Shape shape) {
/* 203 */     ParamChecks.nullNotPermitted(shape, "shape");
/* 204 */     this.legendShape = shape;
/* 205 */     fireChangeEvent();
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
/* 233 */     if (!getItemVisible(series, item)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 238 */     double x = dataset.getXValue(series, item);
/* 239 */     double y = dataset.getYValue(series, item);
/* 240 */     double adjx = (this.dotWidth - 1) / 2.0D;
/* 241 */     double adjy = (this.dotHeight - 1) / 2.0D;
/* 242 */     if (!Double.isNaN(y)) {
/* 243 */       RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
/* 244 */       RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
/* 245 */       double transX = domainAxis.valueToJava2D(x, dataArea, xAxisLocation) - adjx;
/*     */       
/* 247 */       double transY = rangeAxis.valueToJava2D(y, dataArea, yAxisLocation) - adjy;
/*     */ 
/*     */       
/* 250 */       g2.setPaint(getItemPaint(series, item));
/* 251 */       PlotOrientation orientation = plot.getOrientation();
/* 252 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 253 */         g2.fillRect((int)transY, (int)transX, this.dotHeight, this.dotWidth);
/*     */       
/*     */       }
/* 256 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 257 */         g2.fillRect((int)transX, (int)transY, this.dotWidth, this.dotHeight);
/*     */       } 
/*     */ 
/*     */       
/* 261 */       int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
/* 262 */       int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
/* 263 */       updateCrosshairValues(crosshairState, x, y, domainAxisIndex, rangeAxisIndex, transX, transY, orientation);
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
/*     */   public LegendItem getLegendItem(int datasetIndex, int series) {
/* 282 */     XYPlot plot = getPlot();
/* 283 */     if (plot == null) {
/* 284 */       return null;
/*     */     }
/*     */     
/* 287 */     XYDataset dataset = plot.getDataset(datasetIndex);
/* 288 */     if (dataset == null) {
/* 289 */       return null;
/*     */     }
/*     */     
/* 292 */     LegendItem result = null;
/* 293 */     if (getItemVisible(series, 0)) {
/* 294 */       String label = getLegendItemLabelGenerator().generateLabel(dataset, series);
/*     */       
/* 296 */       String description = label;
/* 297 */       String toolTipText = null;
/* 298 */       if (getLegendItemToolTipGenerator() != null) {
/* 299 */         toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*     */       }
/*     */       
/* 302 */       String urlText = null;
/* 303 */       if (getLegendItemURLGenerator() != null) {
/* 304 */         urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*     */       }
/*     */       
/* 307 */       Paint fillPaint = lookupSeriesPaint(series);
/*     */       
/* 309 */       result = new LegendItem(label, description, toolTipText, urlText, getLegendShape(), fillPaint);
/* 310 */       result.setLabelFont(lookupLegendTextFont(series));
/* 311 */       Paint labelPaint = lookupLegendTextPaint(series);
/* 312 */       if (labelPaint != null) {
/* 313 */         result.setLabelPaint(labelPaint);
/*     */       }
/* 315 */       result.setSeriesKey(dataset.getSeriesKey(series));
/* 316 */       result.setSeriesIndex(series);
/* 317 */       result.setDataset((Dataset)dataset);
/* 318 */       result.setDatasetIndex(datasetIndex);
/*     */     } 
/*     */     
/* 321 */     return result;
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
/*     */   public boolean equals(Object obj) {
/* 341 */     if (obj == this) {
/* 342 */       return true;
/*     */     }
/* 344 */     if (!(obj instanceof XYDotRenderer)) {
/* 345 */       return false;
/*     */     }
/* 347 */     XYDotRenderer that = (XYDotRenderer)obj;
/* 348 */     if (this.dotWidth != that.dotWidth) {
/* 349 */       return false;
/*     */     }
/* 351 */     if (this.dotHeight != that.dotHeight) {
/* 352 */       return false;
/*     */     }
/* 354 */     if (!ShapeUtilities.equal(this.legendShape, that.legendShape)) {
/* 355 */       return false;
/*     */     }
/* 357 */     return super.equals(obj);
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
/* 369 */     return super.clone();
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
/* 382 */     stream.defaultReadObject();
/* 383 */     this.legendShape = SerialUtilities.readShape(stream);
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
/* 394 */     stream.defaultWriteObject();
/* 395 */     SerialUtilities.writeShape(this.legendShape, stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYDotRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */