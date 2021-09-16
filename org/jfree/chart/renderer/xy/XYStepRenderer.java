/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.XYToolTipGenerator;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.urls.XYURLGenerator;
/*     */ import org.jfree.data.xy.XYDataset;
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
/*     */ public class XYStepRenderer
/*     */   extends XYLineAndShapeRenderer
/*     */   implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8918141928884796108L;
/* 116 */   private double stepPoint = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYStepRenderer() {
/* 122 */     this((XYToolTipGenerator)null, (XYURLGenerator)null);
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
/*     */   public XYStepRenderer(XYToolTipGenerator toolTipGenerator, XYURLGenerator urlGenerator) {
/* 136 */     setBaseToolTipGenerator(toolTipGenerator);
/* 137 */     setURLGenerator(urlGenerator);
/* 138 */     setBaseShapesVisible(false);
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
/*     */   public double getStepPoint() {
/* 155 */     return this.stepPoint;
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
/*     */   public void setStepPoint(double stepPoint) {
/* 169 */     if (stepPoint < 0.0D || stepPoint > 1.0D) {
/* 170 */       throw new IllegalArgumentException("Requires stepPoint in [0.0;1.0]");
/*     */     }
/*     */     
/* 173 */     this.stepPoint = stepPoint;
/* 174 */     fireChangeEvent();
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
/* 202 */     if (!getItemVisible(series, item)) {
/*     */       return;
/*     */     }
/*     */     
/* 206 */     PlotOrientation orientation = plot.getOrientation();
/*     */     
/* 208 */     Paint seriesPaint = getItemPaint(series, item);
/* 209 */     Stroke seriesStroke = getItemStroke(series, item);
/* 210 */     g2.setPaint(seriesPaint);
/* 211 */     g2.setStroke(seriesStroke);
/*     */ 
/*     */     
/* 214 */     double x1 = dataset.getXValue(series, item);
/* 215 */     double y1 = dataset.getYValue(series, item);
/*     */     
/* 217 */     RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
/* 218 */     RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
/* 219 */     double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
/*     */     
/* 221 */     double transY1 = Double.isNaN(y1) ? Double.NaN : rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);
/*     */     
/* 223 */     if (pass == 0 && item > 0) {
/*     */       
/* 225 */       double x0 = dataset.getXValue(series, item - 1);
/* 226 */       double y0 = dataset.getYValue(series, item - 1);
/* 227 */       double transX0 = domainAxis.valueToJava2D(x0, dataArea, xAxisLocation);
/*     */ 
/*     */       
/* 230 */       double transY0 = Double.isNaN(y0) ? Double.NaN : rangeAxis.valueToJava2D(y0, dataArea, yAxisLocation);
/*     */       
/* 232 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 233 */         if (transY0 == transY1)
/*     */         {
/*     */           
/* 236 */           drawLine(g2, state.workingLine, transY0, transX0, transY1, transX1);
/*     */         
/*     */         }
/*     */         else
/*     */         {
/*     */           
/* 242 */           double transXs = transX0 + getStepPoint() * (transX1 - transX0);
/*     */           
/* 244 */           drawLine(g2, state.workingLine, transY0, transX0, transY0, transXs);
/*     */           
/* 246 */           drawLine(g2, state.workingLine, transY0, transXs, transY1, transXs);
/*     */           
/* 248 */           drawLine(g2, state.workingLine, transY1, transXs, transY1, transX1);
/*     */         }
/*     */       
/*     */       }
/* 252 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 253 */         if (transY0 == transY1) {
/*     */           
/* 255 */           drawLine(g2, state.workingLine, transX0, transY0, transX1, transY1);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 260 */           double transXs = transX0 + getStepPoint() * (transX1 - transX0);
/*     */           
/* 262 */           drawLine(g2, state.workingLine, transX0, transY0, transXs, transY0);
/*     */           
/* 264 */           drawLine(g2, state.workingLine, transXs, transY0, transXs, transY1);
/*     */           
/* 266 */           drawLine(g2, state.workingLine, transXs, transY1, transX1, transY1);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 272 */       int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
/* 273 */       int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
/* 274 */       updateCrosshairValues(crosshairState, x1, y1, domainAxisIndex, rangeAxisIndex, transX1, transY1, orientation);
/*     */ 
/*     */ 
/*     */       
/* 278 */       EntityCollection entities = state.getEntityCollection();
/* 279 */       if (entities != null) {
/* 280 */         addEntity(entities, (Shape)null, dataset, series, item, transX1, transY1);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 286 */     if (pass == 1)
/*     */     {
/* 288 */       if (isItemLabelVisible(series, item)) {
/* 289 */         double xx = transX1;
/* 290 */         double yy = transY1;
/* 291 */         if (orientation == PlotOrientation.HORIZONTAL) {
/* 292 */           xx = transY1;
/* 293 */           yy = transX1;
/*     */         } 
/* 295 */         drawItemLabel(g2, orientation, dataset, series, item, xx, yy, (y1 < 0.0D));
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
/*     */   private void drawLine(Graphics2D g2, Line2D line, double x0, double y0, double x1, double y1) {
/* 314 */     if (Double.isNaN(x0) || Double.isNaN(x1) || Double.isNaN(y0) || 
/* 315 */       Double.isNaN(y1)) {
/*     */       return;
/*     */     }
/* 318 */     line.setLine(x0, y0, x1, y1);
/* 319 */     g2.draw(line);
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
/* 331 */     if (obj == this) {
/* 332 */       return true;
/*     */     }
/* 334 */     if (!(obj instanceof XYLineAndShapeRenderer)) {
/* 335 */       return false;
/*     */     }
/* 337 */     XYStepRenderer that = (XYStepRenderer)obj;
/* 338 */     if (this.stepPoint != that.stepPoint) {
/* 339 */       return false;
/*     */     }
/* 341 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 351 */     return HashUtilities.hashCode(super.hashCode(), this.stepPoint);
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
/* 363 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYStepRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */