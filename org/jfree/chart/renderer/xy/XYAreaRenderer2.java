/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.jfree.chart.LegendItem;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.ChartEntity;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.entity.XYItemEntity;
/*     */ import org.jfree.chart.labels.XYSeriesLabelGenerator;
/*     */ import org.jfree.chart.labels.XYToolTipGenerator;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.urls.XYURLGenerator;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.general.Dataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.io.SerialUtilities;
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
/*     */ public class XYAreaRenderer2
/*     */   extends AbstractXYItemRenderer
/*     */   implements XYItemRenderer, PublicCloneable
/*     */ {
/*     */   private static final long serialVersionUID = -7378069681579984133L;
/*     */   private boolean showOutline;
/*     */   private transient Shape legendArea;
/*     */   
/*     */   public XYAreaRenderer2() {
/* 141 */     this((XYToolTipGenerator)null, (XYURLGenerator)null);
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
/*     */   public XYAreaRenderer2(XYToolTipGenerator labelGenerator, XYURLGenerator urlGenerator) {
/* 154 */     this.showOutline = false;
/* 155 */     setBaseToolTipGenerator(labelGenerator);
/* 156 */     setURLGenerator(urlGenerator);
/* 157 */     GeneralPath area = new GeneralPath();
/* 158 */     area.moveTo(0.0F, -4.0F);
/* 159 */     area.lineTo(3.0F, -2.0F);
/* 160 */     area.lineTo(4.0F, 4.0F);
/* 161 */     area.lineTo(-4.0F, 4.0F);
/* 162 */     area.lineTo(-3.0F, -2.0F);
/* 163 */     area.closePath();
/* 164 */     this.legendArea = area;
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
/*     */   public boolean isOutline() {
/* 176 */     return this.showOutline;
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
/*     */   public void setOutline(boolean show) {
/* 189 */     this.showOutline = show;
/* 190 */     fireChangeEvent();
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
/*     */   public boolean getPlotLines() {
/* 203 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape getLegendArea() {
/* 214 */     return this.legendArea;
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
/*     */   public void setLegendArea(Shape area) {
/* 226 */     ParamChecks.nullNotPermitted(area, "area");
/* 227 */     this.legendArea = area;
/* 228 */     fireChangeEvent();
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
/* 242 */     LegendItem result = null;
/* 243 */     XYPlot xyplot = getPlot();
/* 244 */     if (xyplot != null) {
/* 245 */       XYDataset dataset = xyplot.getDataset(datasetIndex);
/* 246 */       if (dataset != null) {
/* 247 */         XYSeriesLabelGenerator lg = getLegendItemLabelGenerator();
/* 248 */         String label = lg.generateLabel(dataset, series);
/* 249 */         String description = label;
/* 250 */         String toolTipText = null;
/* 251 */         if (getLegendItemToolTipGenerator() != null) {
/* 252 */           toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*     */         }
/*     */         
/* 255 */         String urlText = null;
/* 256 */         if (getLegendItemURLGenerator() != null) {
/* 257 */           urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*     */         }
/*     */         
/* 260 */         Paint paint = lookupSeriesPaint(series);
/* 261 */         result = new LegendItem(label, description, toolTipText, urlText, this.legendArea, paint);
/*     */         
/* 263 */         result.setLabelFont(lookupLegendTextFont(series));
/* 264 */         Paint labelPaint = lookupLegendTextPaint(series);
/* 265 */         if (labelPaint != null) {
/* 266 */           result.setLabelPaint(labelPaint);
/*     */         }
/* 268 */         result.setDataset((Dataset)dataset);
/* 269 */         result.setDatasetIndex(datasetIndex);
/* 270 */         result.setSeriesKey(dataset.getSeriesKey(series));
/* 271 */         result.setSeriesIndex(series);
/*     */       } 
/*     */     } 
/* 274 */     return result;
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
/*     */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/* 301 */     if (!getItemVisible(series, item)) {
/*     */       return;
/*     */     }
/*     */     
/* 305 */     double x1 = dataset.getXValue(series, item);
/* 306 */     double y1 = dataset.getYValue(series, item);
/* 307 */     if (Double.isNaN(y1)) {
/* 308 */       y1 = 0.0D;
/*     */     }
/*     */     
/* 311 */     double transX1 = domainAxis.valueToJava2D(x1, dataArea, plot
/* 312 */         .getDomainAxisEdge());
/* 313 */     double transY1 = rangeAxis.valueToJava2D(y1, dataArea, plot
/* 314 */         .getRangeAxisEdge());
/*     */ 
/*     */ 
/*     */     
/* 318 */     double x0 = dataset.getXValue(series, Math.max(item - 1, 0));
/* 319 */     double y0 = dataset.getYValue(series, Math.max(item - 1, 0));
/* 320 */     if (Double.isNaN(y0)) {
/* 321 */       y0 = 0.0D;
/*     */     }
/* 323 */     double transX0 = domainAxis.valueToJava2D(x0, dataArea, plot
/* 324 */         .getDomainAxisEdge());
/* 325 */     double transY0 = rangeAxis.valueToJava2D(y0, dataArea, plot
/* 326 */         .getRangeAxisEdge());
/*     */     
/* 328 */     int itemCount = dataset.getItemCount(series);
/* 329 */     double x2 = dataset.getXValue(series, Math.min(item + 1, itemCount - 1));
/*     */     
/* 331 */     double y2 = dataset.getYValue(series, Math.min(item + 1, itemCount - 1));
/*     */     
/* 333 */     if (Double.isNaN(y2)) {
/* 334 */       y2 = 0.0D;
/*     */     }
/* 336 */     double transX2 = domainAxis.valueToJava2D(x2, dataArea, plot
/* 337 */         .getDomainAxisEdge());
/* 338 */     double transY2 = rangeAxis.valueToJava2D(y2, dataArea, plot
/* 339 */         .getRangeAxisEdge());
/*     */     
/* 341 */     double transZero = rangeAxis.valueToJava2D(0.0D, dataArea, plot
/* 342 */         .getRangeAxisEdge());
/* 343 */     GeneralPath hotspot = new GeneralPath();
/* 344 */     if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
/* 345 */       moveTo(hotspot, transZero, (transX0 + transX1) / 2.0D);
/* 346 */       lineTo(hotspot, (transY0 + transY1) / 2.0D, (transX0 + transX1) / 2.0D);
/*     */       
/* 348 */       lineTo(hotspot, transY1, transX1);
/* 349 */       lineTo(hotspot, (transY1 + transY2) / 2.0D, (transX1 + transX2) / 2.0D);
/*     */       
/* 351 */       lineTo(hotspot, transZero, (transX1 + transX2) / 2.0D);
/*     */     } else {
/*     */       
/* 354 */       moveTo(hotspot, (transX0 + transX1) / 2.0D, transZero);
/* 355 */       lineTo(hotspot, (transX0 + transX1) / 2.0D, (transY0 + transY1) / 2.0D);
/*     */       
/* 357 */       lineTo(hotspot, transX1, transY1);
/* 358 */       lineTo(hotspot, (transX1 + transX2) / 2.0D, (transY1 + transY2) / 2.0D);
/*     */       
/* 360 */       lineTo(hotspot, (transX1 + transX2) / 2.0D, transZero);
/*     */     } 
/* 362 */     hotspot.closePath();
/*     */     
/* 364 */     PlotOrientation orientation = plot.getOrientation();
/* 365 */     Paint paint = getItemPaint(series, item);
/* 366 */     Stroke stroke = getItemStroke(series, item);
/* 367 */     g2.setPaint(paint);
/* 368 */     g2.setStroke(stroke);
/*     */ 
/*     */ 
/*     */     
/* 372 */     g2.fill(hotspot);
/*     */ 
/*     */     
/* 375 */     if (isOutline()) {
/* 376 */       g2.setStroke(lookupSeriesOutlineStroke(series));
/* 377 */       g2.setPaint(lookupSeriesOutlinePaint(series));
/* 378 */       g2.draw(hotspot);
/*     */     } 
/* 380 */     int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
/* 381 */     int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
/* 382 */     updateCrosshairValues(crosshairState, x1, y1, domainAxisIndex, rangeAxisIndex, transX1, transY1, orientation);
/*     */ 
/*     */ 
/*     */     
/* 386 */     if (state.getInfo() != null) {
/* 387 */       EntityCollection entities = state.getEntityCollection();
/* 388 */       if (entities != null) {
/* 389 */         String tip = null;
/* 390 */         XYToolTipGenerator generator = getToolTipGenerator(series, item);
/*     */         
/* 392 */         if (generator != null) {
/* 393 */           tip = generator.generateToolTip(dataset, series, item);
/*     */         }
/* 395 */         String url = null;
/* 396 */         if (getURLGenerator() != null) {
/* 397 */           url = getURLGenerator().generateURL(dataset, series, item);
/*     */         }
/* 399 */         XYItemEntity entity = new XYItemEntity(hotspot, dataset, series, item, tip, url);
/*     */         
/* 401 */         entities.add((ChartEntity)entity);
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
/* 416 */     if (obj == this) {
/* 417 */       return true;
/*     */     }
/* 419 */     if (!(obj instanceof XYAreaRenderer2)) {
/* 420 */       return false;
/*     */     }
/* 422 */     XYAreaRenderer2 that = (XYAreaRenderer2)obj;
/* 423 */     if (this.showOutline != that.showOutline) {
/* 424 */       return false;
/*     */     }
/* 426 */     if (!ShapeUtilities.equal(this.legendArea, that.legendArea)) {
/* 427 */       return false;
/*     */     }
/* 429 */     return super.equals(obj);
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
/* 441 */     XYAreaRenderer2 clone = (XYAreaRenderer2)super.clone();
/* 442 */     clone.legendArea = ShapeUtilities.clone(this.legendArea);
/* 443 */     return clone;
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
/* 456 */     stream.defaultReadObject();
/* 457 */     this.legendArea = SerialUtilities.readShape(stream);
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
/* 468 */     stream.defaultWriteObject();
/* 469 */     SerialUtilities.writeShape(this.legendArea, stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYAreaRenderer2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */