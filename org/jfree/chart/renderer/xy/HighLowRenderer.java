/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.general.DatasetUtilities;
/*     */ import org.jfree.data.xy.OHLCDataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.util.PaintUtilities;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HighLowRenderer
/*     */   extends AbstractXYItemRenderer
/*     */   implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8135673815876552516L;
/*     */   private boolean drawOpenTicks = true;
/*     */   private boolean drawCloseTicks = true;
/*     */   private transient Paint openTickPaint;
/*     */   private transient Paint closeTickPaint;
/* 148 */   private double tickLength = 2.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getDrawOpenTicks() {
/* 160 */     return this.drawOpenTicks;
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
/*     */   public void setDrawOpenTicks(boolean draw) {
/* 172 */     this.drawOpenTicks = draw;
/* 173 */     fireChangeEvent();
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
/*     */   public boolean getDrawCloseTicks() {
/* 185 */     return this.drawCloseTicks;
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
/*     */   public void setDrawCloseTicks(boolean draw) {
/* 197 */     this.drawCloseTicks = draw;
/* 198 */     fireChangeEvent();
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
/*     */   public Paint getOpenTickPaint() {
/* 210 */     return this.openTickPaint;
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
/*     */   public void setOpenTickPaint(Paint paint) {
/* 224 */     this.openTickPaint = paint;
/* 225 */     fireChangeEvent();
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
/*     */   public Paint getCloseTickPaint() {
/* 237 */     return this.closeTickPaint;
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
/*     */   public void setCloseTickPaint(Paint paint) {
/* 251 */     this.closeTickPaint = paint;
/* 252 */     fireChangeEvent();
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
/*     */   public double getTickLength() {
/* 265 */     return this.tickLength;
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
/*     */   public void setTickLength(double length) {
/* 279 */     this.tickLength = length;
/* 280 */     fireChangeEvent();
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
/* 294 */     if (dataset != null) {
/* 295 */       return DatasetUtilities.findRangeBounds(dataset, true);
/*     */     }
/*     */     
/* 298 */     return null;
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
/* 326 */     double x = dataset.getXValue(series, item);
/* 327 */     if (!domainAxis.getRange().contains(x)) {
/*     */       return;
/*     */     }
/* 330 */     double xx = domainAxis.valueToJava2D(x, dataArea, plot
/* 331 */         .getDomainAxisEdge());
/*     */ 
/*     */     
/* 334 */     Shape entityArea = null;
/* 335 */     EntityCollection entities = null;
/* 336 */     if (info != null) {
/* 337 */       entities = info.getOwner().getEntityCollection();
/*     */     }
/*     */     
/* 340 */     PlotOrientation orientation = plot.getOrientation();
/* 341 */     RectangleEdge location = plot.getRangeAxisEdge();
/*     */     
/* 343 */     Paint itemPaint = getItemPaint(series, item);
/* 344 */     Stroke itemStroke = getItemStroke(series, item);
/* 345 */     g2.setPaint(itemPaint);
/* 346 */     g2.setStroke(itemStroke);
/*     */     
/* 348 */     if (dataset instanceof OHLCDataset) {
/* 349 */       OHLCDataset hld = (OHLCDataset)dataset;
/*     */       
/* 351 */       double yHigh = hld.getHighValue(series, item);
/* 352 */       double yLow = hld.getLowValue(series, item);
/* 353 */       if (!Double.isNaN(yHigh) && !Double.isNaN(yLow)) {
/* 354 */         double yyHigh = rangeAxis.valueToJava2D(yHigh, dataArea, location);
/*     */         
/* 356 */         double yyLow = rangeAxis.valueToJava2D(yLow, dataArea, location);
/*     */         
/* 358 */         if (orientation == PlotOrientation.HORIZONTAL) {
/* 359 */           g2.draw(new Line2D.Double(yyLow, xx, yyHigh, xx));
/*     */           
/* 361 */           entityArea = new Rectangle2D.Double(Math.min(yyLow, yyHigh), xx - 1.0D, Math.abs(yyHigh - yyLow), 2.0D);
/*     */         }
/* 363 */         else if (orientation == PlotOrientation.VERTICAL) {
/* 364 */           g2.draw(new Line2D.Double(xx, yyLow, xx, yyHigh));
/*     */ 
/*     */           
/* 367 */           entityArea = new Rectangle2D.Double(xx - 1.0D, Math.min(yyLow, yyHigh), 2.0D, Math.abs(yyHigh - yyLow));
/*     */         } 
/*     */       } 
/*     */       
/* 371 */       double delta = getTickLength();
/* 372 */       if (domainAxis.isInverted()) {
/* 373 */         delta = -delta;
/*     */       }
/* 375 */       if (getDrawOpenTicks()) {
/* 376 */         double yOpen = hld.getOpenValue(series, item);
/* 377 */         if (!Double.isNaN(yOpen)) {
/* 378 */           double yyOpen = rangeAxis.valueToJava2D(yOpen, dataArea, location);
/*     */           
/* 380 */           if (this.openTickPaint != null) {
/* 381 */             g2.setPaint(this.openTickPaint);
/*     */           } else {
/*     */             
/* 384 */             g2.setPaint(itemPaint);
/*     */           } 
/* 386 */           if (orientation == PlotOrientation.HORIZONTAL) {
/* 387 */             g2.draw(new Line2D.Double(yyOpen, xx + delta, yyOpen, xx));
/*     */           
/*     */           }
/* 390 */           else if (orientation == PlotOrientation.VERTICAL) {
/* 391 */             g2.draw(new Line2D.Double(xx - delta, yyOpen, xx, yyOpen));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 397 */       if (getDrawCloseTicks()) {
/* 398 */         double yClose = hld.getCloseValue(series, item);
/* 399 */         if (!Double.isNaN(yClose)) {
/* 400 */           double yyClose = rangeAxis.valueToJava2D(yClose, dataArea, location);
/*     */           
/* 402 */           if (this.closeTickPaint != null) {
/* 403 */             g2.setPaint(this.closeTickPaint);
/*     */           } else {
/*     */             
/* 406 */             g2.setPaint(itemPaint);
/*     */           } 
/* 408 */           if (orientation == PlotOrientation.HORIZONTAL) {
/* 409 */             g2.draw(new Line2D.Double(yyClose, xx, yyClose, xx - delta));
/*     */           
/*     */           }
/* 412 */           else if (orientation == PlotOrientation.VERTICAL) {
/* 413 */             g2.draw(new Line2D.Double(xx, yyClose, xx + delta, yyClose));
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 423 */     else if (item > 0) {
/* 424 */       double x0 = dataset.getXValue(series, item - 1);
/* 425 */       double y0 = dataset.getYValue(series, item - 1);
/* 426 */       double y = dataset.getYValue(series, item);
/* 427 */       if (Double.isNaN(x0) || Double.isNaN(y0) || Double.isNaN(y)) {
/*     */         return;
/*     */       }
/* 430 */       double xx0 = domainAxis.valueToJava2D(x0, dataArea, plot
/* 431 */           .getDomainAxisEdge());
/* 432 */       double yy0 = rangeAxis.valueToJava2D(y0, dataArea, location);
/* 433 */       double yy = rangeAxis.valueToJava2D(y, dataArea, location);
/* 434 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 435 */         g2.draw(new Line2D.Double(yy0, xx0, yy, xx));
/*     */       }
/* 437 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 438 */         g2.draw(new Line2D.Double(xx0, yy0, xx, yy));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 443 */     if (entities != null) {
/* 444 */       addEntity(entities, entityArea, dataset, series, item, 0.0D, 0.0D);
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 458 */     return super.clone();
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
/* 470 */     if (this == obj) {
/* 471 */       return true;
/*     */     }
/* 473 */     if (!(obj instanceof HighLowRenderer)) {
/* 474 */       return false;
/*     */     }
/* 476 */     HighLowRenderer that = (HighLowRenderer)obj;
/* 477 */     if (this.drawOpenTicks != that.drawOpenTicks) {
/* 478 */       return false;
/*     */     }
/* 480 */     if (this.drawCloseTicks != that.drawCloseTicks) {
/* 481 */       return false;
/*     */     }
/* 483 */     if (!PaintUtilities.equal(this.openTickPaint, that.openTickPaint)) {
/* 484 */       return false;
/*     */     }
/* 486 */     if (!PaintUtilities.equal(this.closeTickPaint, that.closeTickPaint)) {
/* 487 */       return false;
/*     */     }
/* 489 */     if (this.tickLength != that.tickLength) {
/* 490 */       return false;
/*     */     }
/* 492 */     if (!super.equals(obj)) {
/* 493 */       return false;
/*     */     }
/* 495 */     return true;
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
/* 508 */     stream.defaultReadObject();
/* 509 */     this.openTickPaint = SerialUtilities.readPaint(stream);
/* 510 */     this.closeTickPaint = SerialUtilities.readPaint(stream);
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
/* 521 */     stream.defaultWriteObject();
/* 522 */     SerialUtilities.writePaint(this.openTickPaint, stream);
/* 523 */     SerialUtilities.writePaint(this.closeTickPaint, stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/HighLowRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */