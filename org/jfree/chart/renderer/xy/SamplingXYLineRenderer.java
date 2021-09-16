/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.util.ParamChecks;
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
/*     */ public class SamplingXYLineRenderer
/*     */   extends AbstractXYItemRenderer
/*     */   implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private transient Shape legendLine;
/*     */   
/*     */   public SamplingXYLineRenderer() {
/*  90 */     this.legendLine = new Line2D.Double(-7.0D, 0.0D, 7.0D, 0.0D);
/*  91 */     setBaseLegendShape(this.legendLine);
/*  92 */     setTreatLegendShapeAsLine(true);
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
/*     */   public Shape getLegendLine() {
/* 106 */     return this.legendLine;
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
/*     */   public void setLegendLine(Shape line) {
/* 121 */     ParamChecks.nullNotPermitted(line, "line");
/* 122 */     this.legendLine = line;
/* 123 */     fireChangeEvent();
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
/*     */   public int getPassCount() {
/* 135 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class State
/*     */     extends XYItemRendererState
/*     */   {
/*     */     GeneralPath seriesPath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     GeneralPath intervalPath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     double dX = 1.0D;
/*     */ 
/*     */     
/*     */     double lastX;
/*     */ 
/*     */     
/* 164 */     double openY = 0.0D;
/*     */ 
/*     */     
/* 167 */     double highY = 0.0D;
/*     */ 
/*     */     
/* 170 */     double lowY = 0.0D;
/*     */ 
/*     */     
/* 173 */     double closeY = 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean lastPointGood;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public State(PlotRenderingInfo info) {
/* 187 */       super(info);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void startSeriesPass(XYDataset dataset, int series, int firstItem, int lastItem, int pass, int passCount) {
/* 204 */       this.seriesPath.reset();
/* 205 */       this.intervalPath.reset();
/* 206 */       this.lastPointGood = false;
/* 207 */       super.startSeriesPass(dataset, series, firstItem, lastItem, pass, passCount);
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
/*     */   public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset data, PlotRenderingInfo info) {
/* 234 */     double dpi = 72.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     State state = new State(info);
/* 240 */     state.seriesPath = new GeneralPath();
/* 241 */     state.intervalPath = new GeneralPath();
/* 242 */     state.dX = 72.0D / dpi;
/* 243 */     return state;
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
/* 271 */     if (!getItemVisible(series, item)) {
/*     */       return;
/*     */     }
/* 274 */     RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
/* 275 */     RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
/*     */ 
/*     */     
/* 278 */     double x1 = dataset.getXValue(series, item);
/* 279 */     double y1 = dataset.getYValue(series, item);
/* 280 */     double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
/* 281 */     double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);
/*     */     
/* 283 */     State s = (State)state;
/*     */     
/* 285 */     if (!Double.isNaN(transX1) && !Double.isNaN(transY1)) {
/* 286 */       float x = (float)transX1;
/* 287 */       float y = (float)transY1;
/* 288 */       PlotOrientation orientation = plot.getOrientation();
/* 289 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 290 */         x = (float)transY1;
/* 291 */         y = (float)transX1;
/*     */       } 
/* 293 */       if (s.lastPointGood) {
/* 294 */         if (Math.abs(x - s.lastX) > s.dX) {
/* 295 */           s.seriesPath.lineTo(x, y);
/* 296 */           if (s.lowY < s.highY) {
/* 297 */             s.intervalPath.moveTo((float)s.lastX, (float)s.lowY);
/* 298 */             s.intervalPath.lineTo((float)s.lastX, (float)s.highY);
/*     */           } 
/* 300 */           s.lastX = x;
/* 301 */           s.openY = y;
/* 302 */           s.highY = y;
/* 303 */           s.lowY = y;
/* 304 */           s.closeY = y;
/*     */         } else {
/*     */           
/* 307 */           s.highY = Math.max(s.highY, y);
/* 308 */           s.lowY = Math.min(s.lowY, y);
/* 309 */           s.closeY = y;
/*     */         } 
/*     */       } else {
/*     */         
/* 313 */         s.seriesPath.moveTo(x, y);
/* 314 */         s.lastX = x;
/* 315 */         s.openY = y;
/* 316 */         s.highY = y;
/* 317 */         s.lowY = y;
/* 318 */         s.closeY = y;
/*     */       } 
/* 320 */       s.lastPointGood = true;
/*     */     } else {
/*     */       
/* 323 */       s.lastPointGood = false;
/*     */     } 
/*     */     
/* 326 */     if (item == s.getLastItemIndex()) {
/*     */       
/* 328 */       PathIterator pi = s.seriesPath.getPathIterator(null);
/* 329 */       int count = 0;
/* 330 */       while (!pi.isDone()) {
/* 331 */         count++;
/* 332 */         pi.next();
/*     */       } 
/* 334 */       g2.setStroke(getItemStroke(series, item));
/* 335 */       g2.setPaint(getItemPaint(series, item));
/* 336 */       g2.draw(s.seriesPath);
/* 337 */       g2.draw(s.intervalPath);
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 350 */     SamplingXYLineRenderer clone = (SamplingXYLineRenderer)super.clone();
/* 351 */     if (this.legendLine != null) {
/* 352 */       clone.legendLine = ShapeUtilities.clone(this.legendLine);
/*     */     }
/* 354 */     return clone;
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
/* 366 */     if (obj == this) {
/* 367 */       return true;
/*     */     }
/* 369 */     if (!(obj instanceof SamplingXYLineRenderer)) {
/* 370 */       return false;
/*     */     }
/* 372 */     if (!super.equals(obj)) {
/* 373 */       return false;
/*     */     }
/* 375 */     SamplingXYLineRenderer that = (SamplingXYLineRenderer)obj;
/* 376 */     if (!ShapeUtilities.equal(this.legendLine, that.legendLine)) {
/* 377 */       return false;
/*     */     }
/* 379 */     return true;
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
/* 392 */     stream.defaultReadObject();
/* 393 */     this.legendLine = SerialUtilities.readShape(stream);
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
/* 404 */     stream.defaultWriteObject();
/* 405 */     SerialUtilities.writeShape(this.legendLine, stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/SamplingXYLineRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */