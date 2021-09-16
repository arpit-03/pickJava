/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.xy.IntervalXYDataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PaintUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XYErrorRenderer
/*     */   extends XYLineAndShapeRenderer
/*     */ {
/*     */   static final long serialVersionUID = 5162283570955172424L;
/*     */   private boolean drawXError;
/*     */   private boolean drawYError;
/*     */   private double capLength;
/*     */   private transient Paint errorPaint;
/*     */   private transient Stroke errorStroke;
/*     */   
/*     */   public XYErrorRenderer() {
/* 113 */     super(false, true);
/* 114 */     this.drawXError = true;
/* 115 */     this.drawYError = true;
/* 116 */     this.errorPaint = null;
/* 117 */     this.errorStroke = null;
/* 118 */     this.capLength = 4.0D;
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
/*     */   public boolean getDrawXError() {
/* 130 */     return this.drawXError;
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
/*     */   public void setDrawXError(boolean draw) {
/* 143 */     if (this.drawXError != draw) {
/* 144 */       this.drawXError = draw;
/* 145 */       fireChangeEvent();
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
/*     */   public boolean getDrawYError() {
/* 158 */     return this.drawYError;
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
/*     */   public void setDrawYError(boolean draw) {
/* 171 */     if (this.drawYError != draw) {
/* 172 */       this.drawYError = draw;
/* 173 */       fireChangeEvent();
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
/*     */   public double getCapLength() {
/* 186 */     return this.capLength;
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
/*     */   public void setCapLength(double length) {
/* 198 */     this.capLength = length;
/* 199 */     fireChangeEvent();
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
/*     */   public Paint getErrorPaint() {
/* 211 */     return this.errorPaint;
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
/*     */   public void setErrorPaint(Paint paint) {
/* 223 */     this.errorPaint = paint;
/* 224 */     fireChangeEvent();
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
/*     */   public Stroke getErrorStroke() {
/* 239 */     return this.errorStroke;
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
/*     */   public void setErrorStroke(Stroke stroke) {
/* 253 */     this.errorStroke = stroke;
/* 254 */     fireChangeEvent();
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
/*     */   public Range findDomainBounds(XYDataset dataset) {
/* 269 */     return findDomainBounds(dataset, true);
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
/*     */   public Range findRangeBounds(XYDataset dataset) {
/* 284 */     return findRangeBounds(dataset, true);
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
/*     */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/* 309 */     if (pass == 0 && dataset instanceof IntervalXYDataset && 
/* 310 */       getItemVisible(series, item)) {
/* 311 */       IntervalXYDataset ixyd = (IntervalXYDataset)dataset;
/* 312 */       PlotOrientation orientation = plot.getOrientation();
/* 313 */       if (this.drawXError) {
/*     */         Line2D line, cap1, cap2;
/* 315 */         double x0 = ixyd.getStartXValue(series, item);
/* 316 */         double x1 = ixyd.getEndXValue(series, item);
/* 317 */         double y = ixyd.getYValue(series, item);
/* 318 */         RectangleEdge edge = plot.getDomainAxisEdge();
/* 319 */         double xx0 = domainAxis.valueToJava2D(x0, dataArea, edge);
/* 320 */         double xx1 = domainAxis.valueToJava2D(x1, dataArea, edge);
/* 321 */         double yy = rangeAxis.valueToJava2D(y, dataArea, plot
/* 322 */             .getRangeAxisEdge());
/*     */ 
/*     */ 
/*     */         
/* 326 */         double adj = this.capLength / 2.0D;
/* 327 */         if (orientation == PlotOrientation.VERTICAL) {
/* 328 */           line = new Line2D.Double(xx0, yy, xx1, yy);
/* 329 */           cap1 = new Line2D.Double(xx0, yy - adj, xx0, yy + adj);
/* 330 */           cap2 = new Line2D.Double(xx1, yy - adj, xx1, yy + adj);
/*     */         } else {
/*     */           
/* 333 */           line = new Line2D.Double(yy, xx0, yy, xx1);
/* 334 */           cap1 = new Line2D.Double(yy - adj, xx0, yy + adj, xx0);
/* 335 */           cap2 = new Line2D.Double(yy - adj, xx1, yy + adj, xx1);
/*     */         } 
/* 337 */         if (this.errorPaint != null) {
/* 338 */           g2.setPaint(this.errorPaint);
/*     */         } else {
/*     */           
/* 341 */           g2.setPaint(getItemPaint(series, item));
/*     */         } 
/* 343 */         if (this.errorStroke != null) {
/* 344 */           g2.setStroke(this.errorStroke);
/*     */         } else {
/*     */           
/* 347 */           g2.setStroke(getItemStroke(series, item));
/*     */         } 
/* 349 */         g2.draw(line);
/* 350 */         g2.draw(cap1);
/* 351 */         g2.draw(cap2);
/*     */       } 
/* 353 */       if (this.drawYError) {
/*     */         Line2D line, cap1, cap2;
/* 355 */         double y0 = ixyd.getStartYValue(series, item);
/* 356 */         double y1 = ixyd.getEndYValue(series, item);
/* 357 */         double x = ixyd.getXValue(series, item);
/* 358 */         RectangleEdge edge = plot.getRangeAxisEdge();
/* 359 */         double yy0 = rangeAxis.valueToJava2D(y0, dataArea, edge);
/* 360 */         double yy1 = rangeAxis.valueToJava2D(y1, dataArea, edge);
/* 361 */         double xx = domainAxis.valueToJava2D(x, dataArea, plot
/* 362 */             .getDomainAxisEdge());
/*     */ 
/*     */ 
/*     */         
/* 366 */         double adj = this.capLength / 2.0D;
/* 367 */         if (orientation == PlotOrientation.VERTICAL) {
/* 368 */           line = new Line2D.Double(xx, yy0, xx, yy1);
/* 369 */           cap1 = new Line2D.Double(xx - adj, yy0, xx + adj, yy0);
/* 370 */           cap2 = new Line2D.Double(xx - adj, yy1, xx + adj, yy1);
/*     */         } else {
/*     */           
/* 373 */           line = new Line2D.Double(yy0, xx, yy1, xx);
/* 374 */           cap1 = new Line2D.Double(yy0, xx - adj, yy0, xx + adj);
/* 375 */           cap2 = new Line2D.Double(yy1, xx - adj, yy1, xx + adj);
/*     */         } 
/* 377 */         if (this.errorPaint != null) {
/* 378 */           g2.setPaint(this.errorPaint);
/*     */         } else {
/*     */           
/* 381 */           g2.setPaint(getItemPaint(series, item));
/*     */         } 
/* 383 */         if (this.errorStroke != null) {
/* 384 */           g2.setStroke(this.errorStroke);
/*     */         } else {
/*     */           
/* 387 */           g2.setStroke(getItemStroke(series, item));
/*     */         } 
/* 389 */         g2.draw(line);
/* 390 */         g2.draw(cap1);
/* 391 */         g2.draw(cap2);
/*     */       } 
/*     */     } 
/* 394 */     super.drawItem(g2, state, dataArea, info, plot, domainAxis, rangeAxis, dataset, series, item, crosshairState, pass);
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
/* 407 */     if (obj == this) {
/* 408 */       return true;
/*     */     }
/* 410 */     if (!(obj instanceof XYErrorRenderer)) {
/* 411 */       return false;
/*     */     }
/* 413 */     XYErrorRenderer that = (XYErrorRenderer)obj;
/* 414 */     if (this.drawXError != that.drawXError) {
/* 415 */       return false;
/*     */     }
/* 417 */     if (this.drawYError != that.drawYError) {
/* 418 */       return false;
/*     */     }
/* 420 */     if (this.capLength != that.capLength) {
/* 421 */       return false;
/*     */     }
/* 423 */     if (!PaintUtilities.equal(this.errorPaint, that.errorPaint)) {
/* 424 */       return false;
/*     */     }
/* 426 */     if (!ObjectUtilities.equal(this.errorStroke, that.errorStroke)) {
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
/*     */   
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 442 */     stream.defaultReadObject();
/* 443 */     this.errorPaint = SerialUtilities.readPaint(stream);
/* 444 */     this.errorStroke = SerialUtilities.readStroke(stream);
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
/* 455 */     stream.defaultWriteObject();
/* 456 */     SerialUtilities.writePaint(this.errorPaint, stream);
/* 457 */     SerialUtilities.writeStroke(this.errorStroke, stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYErrorRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */