/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.event.RendererChangeEvent;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.LookupPaintScale;
/*     */ import org.jfree.chart.renderer.PaintScale;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.general.DatasetUtilities;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.data.xy.XYZDataset;
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
/*     */ public class XYShapeRenderer
/*     */   extends AbstractXYItemRenderer
/*     */   implements XYItemRenderer, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8320552104211173221L;
/*     */   private PaintScale paintScale;
/*     */   private boolean drawOutlines;
/*     */   private boolean useOutlinePaint;
/*     */   private boolean useFillPaint;
/*     */   private boolean guideLinesVisible;
/*     */   private transient Paint guideLinePaint;
/*     */   private transient Stroke guideLineStroke;
/*     */   
/*     */   public XYShapeRenderer() {
/* 137 */     this.paintScale = (PaintScale)new LookupPaintScale();
/* 138 */     this.useFillPaint = false;
/* 139 */     this.drawOutlines = false;
/* 140 */     this.useOutlinePaint = true;
/* 141 */     this.guideLinesVisible = false;
/* 142 */     this.guideLinePaint = Color.darkGray;
/* 143 */     this.guideLineStroke = new BasicStroke();
/* 144 */     setBaseShape(new Ellipse2D.Double(-5.0D, -5.0D, 10.0D, 10.0D));
/* 145 */     setAutoPopulateSeriesShape(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PaintScale getPaintScale() {
/* 156 */     return this.paintScale;
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
/*     */   public void setPaintScale(PaintScale scale) {
/* 168 */     ParamChecks.nullNotPermitted(scale, "scale");
/* 169 */     this.paintScale = scale;
/* 170 */     notifyListeners(new RendererChangeEvent(this));
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
/*     */   public boolean getDrawOutlines() {
/* 182 */     return this.drawOutlines;
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
/*     */   public void setDrawOutlines(boolean flag) {
/* 198 */     this.drawOutlines = flag;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getUseFillPaint() {
/* 216 */     return this.useFillPaint;
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
/*     */   public void setUseFillPaint(boolean flag) {
/* 229 */     this.useFillPaint = flag;
/* 230 */     fireChangeEvent();
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
/*     */   public boolean getUseOutlinePaint() {
/* 242 */     return this.useOutlinePaint;
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
/*     */   public void setUseOutlinePaint(boolean use) {
/* 255 */     this.useOutlinePaint = use;
/* 256 */     fireChangeEvent();
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
/*     */   public boolean isGuideLinesVisible() {
/* 269 */     return this.guideLinesVisible;
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
/*     */   public void setGuideLinesVisible(boolean visible) {
/* 282 */     this.guideLinesVisible = visible;
/* 283 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getGuideLinePaint() {
/* 294 */     return this.guideLinePaint;
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
/*     */   public void setGuideLinePaint(Paint paint) {
/* 306 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 307 */     this.guideLinePaint = paint;
/* 308 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getGuideLineStroke() {
/* 319 */     return this.guideLineStroke;
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
/*     */   public void setGuideLineStroke(Stroke stroke) {
/* 331 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 332 */     this.guideLineStroke = stroke;
/* 333 */     fireChangeEvent();
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
/*     */   public Range findDomainBounds(XYDataset dataset) {
/* 347 */     if (dataset == null) {
/* 348 */       return null;
/*     */     }
/* 350 */     Range r = DatasetUtilities.findDomainBounds(dataset, false);
/* 351 */     if (r == null) {
/* 352 */       return null;
/*     */     }
/* 354 */     double offset = 0.0D;
/* 355 */     return new Range(r.getLowerBound() + offset, r
/* 356 */         .getUpperBound() + offset);
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
/* 370 */     if (dataset == null) {
/* 371 */       return null;
/*     */     }
/* 373 */     Range r = DatasetUtilities.findRangeBounds(dataset, false);
/* 374 */     if (r == null) {
/* 375 */       return null;
/*     */     }
/* 377 */     double offset = 0.0D;
/* 378 */     return new Range(r.getLowerBound() + offset, r.getUpperBound() + offset);
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
/*     */   public Range findZBounds(XYZDataset dataset) {
/* 391 */     if (dataset != null) {
/* 392 */       return DatasetUtilities.findZBounds(dataset);
/*     */     }
/*     */     
/* 395 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPassCount() {
/* 406 */     return 2;
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
/*     */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/* 432 */     EntityCollection entities = null;
/* 433 */     if (info != null) {
/* 434 */       entities = info.getOwner().getEntityCollection();
/*     */     }
/*     */     
/* 437 */     double x = dataset.getXValue(series, item);
/* 438 */     double y = dataset.getYValue(series, item);
/* 439 */     if (Double.isNaN(x) || Double.isNaN(y)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 444 */     double transX = domainAxis.valueToJava2D(x, dataArea, plot
/* 445 */         .getDomainAxisEdge());
/* 446 */     double transY = rangeAxis.valueToJava2D(y, dataArea, plot
/* 447 */         .getRangeAxisEdge());
/*     */     
/* 449 */     PlotOrientation orientation = plot.getOrientation();
/*     */ 
/*     */     
/* 452 */     if (pass == 0 && this.guideLinesVisible) {
/* 453 */       g2.setStroke(this.guideLineStroke);
/* 454 */       g2.setPaint(this.guideLinePaint);
/* 455 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 456 */         g2.draw(new Line2D.Double(transY, dataArea.getMinY(), transY, dataArea
/* 457 */               .getMaxY()));
/* 458 */         g2.draw(new Line2D.Double(dataArea.getMinX(), transX, dataArea
/* 459 */               .getMaxX(), transX));
/*     */       } else {
/*     */         
/* 462 */         g2.draw(new Line2D.Double(transX, dataArea.getMinY(), transX, dataArea
/* 463 */               .getMaxY()));
/* 464 */         g2.draw(new Line2D.Double(dataArea.getMinX(), transY, dataArea
/* 465 */               .getMaxX(), transY));
/*     */       }
/*     */     
/* 468 */     } else if (pass == 1) {
/* 469 */       Shape shape = getItemShape(series, item);
/* 470 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 471 */         shape = ShapeUtilities.createTranslatedShape(shape, transY, transX);
/*     */       
/*     */       }
/* 474 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 475 */         shape = ShapeUtilities.createTranslatedShape(shape, transX, transY);
/*     */       } 
/*     */       
/* 478 */       Shape hotspot = shape;
/* 479 */       if (shape.intersects(dataArea)) {
/*     */         
/* 481 */         g2.setPaint(getPaint(dataset, series, item));
/* 482 */         g2.fill(shape);
/*     */         
/* 484 */         if (this.drawOutlines) {
/* 485 */           if (getUseOutlinePaint()) {
/* 486 */             g2.setPaint(getItemOutlinePaint(series, item));
/*     */           } else {
/*     */             
/* 489 */             g2.setPaint(getItemPaint(series, item));
/*     */           } 
/* 491 */           g2.setStroke(getItemOutlineStroke(series, item));
/* 492 */           g2.draw(shape);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 497 */       if (entities != null) {
/* 498 */         addEntity(entities, hotspot, dataset, series, item, transX, transY);
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
/*     */   protected Paint getPaint(XYDataset dataset, int series, int item) {
/*     */     Paint p;
/* 515 */     if (dataset instanceof XYZDataset) {
/* 516 */       double z = ((XYZDataset)dataset).getZValue(series, item);
/* 517 */       p = this.paintScale.getPaint(z);
/*     */     
/*     */     }
/* 520 */     else if (this.useFillPaint) {
/* 521 */       p = getItemFillPaint(series, item);
/*     */     } else {
/*     */       
/* 524 */       p = getItemPaint(series, item);
/*     */     } 
/*     */     
/* 527 */     return p;
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
/*     */   public boolean equals(Object obj) {
/* 546 */     if (obj == this) {
/* 547 */       return true;
/*     */     }
/* 549 */     if (!(obj instanceof XYShapeRenderer)) {
/* 550 */       return false;
/*     */     }
/* 552 */     XYShapeRenderer that = (XYShapeRenderer)obj;
/* 553 */     if (!this.paintScale.equals(that.paintScale)) {
/* 554 */       return false;
/*     */     }
/* 556 */     if (this.drawOutlines != that.drawOutlines) {
/* 557 */       return false;
/*     */     }
/* 559 */     if (this.useOutlinePaint != that.useOutlinePaint) {
/* 560 */       return false;
/*     */     }
/* 562 */     if (this.useFillPaint != that.useFillPaint) {
/* 563 */       return false;
/*     */     }
/* 565 */     if (this.guideLinesVisible != that.guideLinesVisible) {
/* 566 */       return false;
/*     */     }
/* 568 */     if (!this.guideLinePaint.equals(that.guideLinePaint)) {
/* 569 */       return false;
/*     */     }
/* 571 */     if (!this.guideLineStroke.equals(that.guideLineStroke)) {
/* 572 */       return false;
/*     */     }
/* 574 */     return super.equals(obj);
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
/* 587 */     XYShapeRenderer clone = (XYShapeRenderer)super.clone();
/* 588 */     if (this.paintScale instanceof PublicCloneable) {
/* 589 */       PublicCloneable pc = (PublicCloneable)this.paintScale;
/* 590 */       clone.paintScale = (PaintScale)pc.clone();
/*     */     } 
/* 592 */     return clone;
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
/* 605 */     stream.defaultReadObject();
/* 606 */     this.guideLinePaint = SerialUtilities.readPaint(stream);
/* 607 */     this.guideLineStroke = SerialUtilities.readStroke(stream);
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
/* 618 */     stream.defaultWriteObject();
/* 619 */     SerialUtilities.writePaint(this.guideLinePaint, stream);
/* 620 */     SerialUtilities.writeStroke(this.guideLineStroke, stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYShapeRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */