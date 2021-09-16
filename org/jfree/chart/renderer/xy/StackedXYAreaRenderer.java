/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Point;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Stack;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.ChartEntity;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.entity.XYItemEntity;
/*     */ import org.jfree.chart.labels.XYToolTipGenerator;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.urls.XYURLGenerator;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.general.DatasetUtilities;
/*     */ import org.jfree.data.xy.TableXYDataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PaintUtilities;
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
/*     */ public class StackedXYAreaRenderer
/*     */   extends XYAreaRenderer
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5217394318178570889L;
/*     */   
/*     */   static class StackedXYAreaRendererState
/*     */     extends XYItemRendererState
/*     */   {
/*     */     private Polygon seriesArea;
/*     */     private Line2D line;
/*     */     private Stack lastSeriesPoints;
/*     */     private Stack currentSeriesPoints;
/*     */     
/*     */     public StackedXYAreaRendererState(PlotRenderingInfo info) {
/* 148 */       super(info);
/* 149 */       this.seriesArea = null;
/* 150 */       this.line = new Line2D.Double();
/* 151 */       this.lastSeriesPoints = new Stack();
/* 152 */       this.currentSeriesPoints = new Stack();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Polygon getSeriesArea() {
/* 161 */       return this.seriesArea;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setSeriesArea(Polygon area) {
/* 170 */       this.seriesArea = area;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Line2D getLine() {
/* 179 */       return this.line;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Stack getCurrentSeriesPoints() {
/* 188 */       return this.currentSeriesPoints;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setCurrentSeriesPoints(Stack points) {
/* 197 */       this.currentSeriesPoints = points;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Stack getLastSeriesPoints() {
/* 206 */       return this.lastSeriesPoints;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setLastSeriesPoints(Stack points) {
/* 215 */       this.lastSeriesPoints = points;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 223 */   private transient Paint shapePaint = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 229 */   private transient Stroke shapeStroke = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackedXYAreaRenderer() {
/* 235 */     this(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackedXYAreaRenderer(int type) {
/* 244 */     this(type, (XYToolTipGenerator)null, (XYURLGenerator)null);
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
/*     */   public StackedXYAreaRenderer(int type, XYToolTipGenerator labelGenerator, XYURLGenerator urlGenerator) {
/* 260 */     super(type, labelGenerator, urlGenerator);
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
/*     */   public Paint getShapePaint() {
/* 272 */     return this.shapePaint;
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
/*     */   public void setShapePaint(Paint shapePaint) {
/* 284 */     this.shapePaint = shapePaint;
/* 285 */     fireChangeEvent();
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
/*     */   public Stroke getShapeStroke() {
/* 297 */     return this.shapeStroke;
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
/*     */   public void setShapeStroke(Stroke shapeStroke) {
/* 309 */     this.shapeStroke = shapeStroke;
/* 310 */     fireChangeEvent();
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
/*     */   public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset data, PlotRenderingInfo info) {
/* 332 */     XYItemRendererState state = new StackedXYAreaRendererState(info);
/*     */ 
/*     */     
/* 335 */     state.setProcessVisibleItemsOnly(false);
/* 336 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPassCount() {
/* 346 */     return 2;
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
/*     */   public Range findRangeBounds(XYDataset dataset) {
/* 363 */     if (dataset != null) {
/* 364 */       return DatasetUtilities.findStackedRangeBounds((TableXYDataset)dataset);
/*     */     }
/*     */ 
/*     */     
/* 368 */     return null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/* 399 */     PlotOrientation orientation = plot.getOrientation();
/* 400 */     StackedXYAreaRendererState areaState = (StackedXYAreaRendererState)state;
/*     */ 
/*     */ 
/*     */     
/* 404 */     TableXYDataset tdataset = (TableXYDataset)dataset;
/* 405 */     int itemCount = tdataset.getItemCount();
/*     */ 
/*     */     
/* 408 */     double x1 = dataset.getXValue(series, item);
/* 409 */     double y1 = dataset.getYValue(series, item);
/* 410 */     boolean nullPoint = false;
/* 411 */     if (Double.isNaN(y1)) {
/* 412 */       y1 = 0.0D;
/* 413 */       nullPoint = true;
/*     */     } 
/*     */ 
/*     */     
/* 417 */     double ph1 = getPreviousHeight(tdataset, series, item);
/* 418 */     double transX1 = domainAxis.valueToJava2D(x1, dataArea, plot
/* 419 */         .getDomainAxisEdge());
/* 420 */     double transY1 = rangeAxis.valueToJava2D(y1 + ph1, dataArea, plot
/* 421 */         .getRangeAxisEdge());
/*     */ 
/*     */     
/* 424 */     Paint seriesPaint = getItemPaint(series, item);
/* 425 */     Paint seriesFillPaint = seriesPaint;
/* 426 */     if (getUseFillPaint()) {
/* 427 */       seriesFillPaint = getItemFillPaint(series, item);
/*     */     }
/* 429 */     Stroke seriesStroke = getItemStroke(series, item);
/*     */     
/* 431 */     if (pass == 0) {
/*     */ 
/*     */       
/* 434 */       if (item == 0) {
/*     */         
/* 436 */         areaState.setSeriesArea(new Polygon());
/* 437 */         areaState.setLastSeriesPoints(areaState
/* 438 */             .getCurrentSeriesPoints());
/* 439 */         areaState.setCurrentSeriesPoints(new Stack());
/*     */ 
/*     */         
/* 442 */         double transY2 = rangeAxis.valueToJava2D(ph1, dataArea, plot
/* 443 */             .getRangeAxisEdge());
/*     */ 
/*     */         
/* 446 */         if (orientation == PlotOrientation.VERTICAL) {
/* 447 */           areaState.getSeriesArea().addPoint((int)transX1, (int)transY2);
/*     */         
/*     */         }
/* 450 */         else if (orientation == PlotOrientation.HORIZONTAL) {
/* 451 */           areaState.getSeriesArea().addPoint((int)transY2, (int)transX1);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 457 */       if (orientation == PlotOrientation.VERTICAL) {
/* 458 */         Point point = new Point((int)transX1, (int)transY1);
/* 459 */         areaState.getSeriesArea().addPoint((int)point.getX(), 
/* 460 */             (int)point.getY());
/* 461 */         areaState.getCurrentSeriesPoints().push(point);
/*     */       }
/* 463 */       else if (orientation == PlotOrientation.HORIZONTAL) {
/* 464 */         areaState.getSeriesArea().addPoint((int)transY1, (int)transX1);
/*     */       } 
/*     */ 
/*     */       
/* 468 */       if (getPlotLines() && 
/* 469 */         item > 0) {
/*     */         
/* 471 */         double x0 = dataset.getXValue(series, item - 1);
/* 472 */         double y0 = dataset.getYValue(series, item - 1);
/* 473 */         double ph0 = getPreviousHeight(tdataset, series, item - 1);
/* 474 */         double transX0 = domainAxis.valueToJava2D(x0, dataArea, plot
/* 475 */             .getDomainAxisEdge());
/* 476 */         double transY0 = rangeAxis.valueToJava2D(y0 + ph0, dataArea, plot
/* 477 */             .getRangeAxisEdge());
/*     */         
/* 479 */         if (orientation == PlotOrientation.VERTICAL) {
/* 480 */           areaState.getLine().setLine(transX0, transY0, transX1, transY1);
/*     */         
/*     */         }
/* 483 */         else if (orientation == PlotOrientation.HORIZONTAL) {
/* 484 */           areaState.getLine().setLine(transY0, transX0, transY1, transX1);
/*     */         } 
/*     */         
/* 487 */         g2.setPaint(seriesPaint);
/* 488 */         g2.setStroke(seriesStroke);
/* 489 */         g2.draw(areaState.getLine());
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 495 */       if (getPlotArea() && item > 0 && item == itemCount - 1) {
/*     */         
/* 497 */         double transY2 = rangeAxis.valueToJava2D(ph1, dataArea, plot
/* 498 */             .getRangeAxisEdge());
/*     */         
/* 500 */         if (orientation == PlotOrientation.VERTICAL) {
/*     */           
/* 502 */           areaState.getSeriesArea().addPoint((int)transX1, (int)transY2);
/*     */         
/*     */         }
/* 505 */         else if (orientation == PlotOrientation.HORIZONTAL) {
/*     */           
/* 507 */           areaState.getSeriesArea().addPoint((int)transY2, (int)transX1);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 513 */         if (series != 0) {
/* 514 */           Stack<Point> points = areaState.getLastSeriesPoints();
/* 515 */           while (!points.empty()) {
/* 516 */             Point point = points.pop();
/* 517 */             areaState.getSeriesArea().addPoint((int)point.getX(), 
/* 518 */                 (int)point.getY());
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 523 */         g2.setPaint(seriesFillPaint);
/* 524 */         g2.setStroke(seriesStroke);
/* 525 */         g2.fill(areaState.getSeriesArea());
/*     */ 
/*     */         
/* 528 */         if (isOutline()) {
/* 529 */           g2.setStroke(lookupSeriesOutlineStroke(series));
/* 530 */           g2.setPaint(lookupSeriesOutlinePaint(series));
/* 531 */           g2.draw(areaState.getSeriesArea());
/*     */         } 
/*     */       } 
/*     */       
/* 535 */       int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
/* 536 */       int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
/* 537 */       updateCrosshairValues(crosshairState, x1, ph1 + y1, domainAxisIndex, rangeAxisIndex, transX1, transY1, orientation);
/*     */ 
/*     */     
/*     */     }
/* 541 */     else if (pass == 1) {
/*     */ 
/*     */ 
/*     */       
/* 545 */       Shape shape = null;
/* 546 */       if (getPlotShapes()) {
/* 547 */         shape = getItemShape(series, item);
/* 548 */         if (plot.getOrientation() == PlotOrientation.VERTICAL) {
/* 549 */           shape = ShapeUtilities.createTranslatedShape(shape, transX1, transY1);
/*     */         
/*     */         }
/* 552 */         else if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
/* 553 */           shape = ShapeUtilities.createTranslatedShape(shape, transY1, transX1);
/*     */         } 
/*     */         
/* 556 */         if (!nullPoint) {
/* 557 */           if (getShapePaint() != null) {
/* 558 */             g2.setPaint(getShapePaint());
/*     */           } else {
/*     */             
/* 561 */             g2.setPaint(seriesPaint);
/*     */           } 
/* 563 */           if (getShapeStroke() != null) {
/* 564 */             g2.setStroke(getShapeStroke());
/*     */           } else {
/*     */             
/* 567 */             g2.setStroke(seriesStroke);
/*     */           } 
/* 569 */           g2.draw(shape);
/*     */         }
/*     */       
/*     */       }
/* 573 */       else if (plot.getOrientation() == PlotOrientation.VERTICAL) {
/* 574 */         shape = new Rectangle2D.Double(transX1 - 3.0D, transY1 - 3.0D, 6.0D, 6.0D);
/*     */       
/*     */       }
/* 577 */       else if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
/* 578 */         shape = new Rectangle2D.Double(transY1 - 3.0D, transX1 - 3.0D, 6.0D, 6.0D);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 584 */       if (state.getInfo() != null) {
/* 585 */         EntityCollection entities = state.getEntityCollection();
/* 586 */         if (entities != null && shape != null && !nullPoint) {
/* 587 */           String tip = null;
/*     */           
/* 589 */           XYToolTipGenerator generator = getToolTipGenerator(series, item);
/* 590 */           if (generator != null) {
/* 591 */             tip = generator.generateToolTip(dataset, series, item);
/*     */           }
/* 593 */           String url = null;
/* 594 */           if (getURLGenerator() != null) {
/* 595 */             url = getURLGenerator().generateURL(dataset, series, item);
/*     */           }
/*     */           
/* 598 */           XYItemEntity entity = new XYItemEntity(shape, dataset, series, item, tip, url);
/*     */           
/* 600 */           entities.add((ChartEntity)entity);
/*     */         } 
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
/*     */   
/*     */   protected double getPreviousHeight(TableXYDataset dataset, int series, int index) {
/* 621 */     double result = 0.0D;
/* 622 */     for (int i = 0; i < series; i++) {
/* 623 */       double value = dataset.getYValue(i, index);
/* 624 */       if (!Double.isNaN(value)) {
/* 625 */         result += value;
/*     */       }
/*     */     } 
/* 628 */     return result;
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
/* 640 */     if (obj == this) {
/* 641 */       return true;
/*     */     }
/* 643 */     if (!(obj instanceof StackedXYAreaRenderer) || !super.equals(obj)) {
/* 644 */       return false;
/*     */     }
/* 646 */     StackedXYAreaRenderer that = (StackedXYAreaRenderer)obj;
/* 647 */     if (!PaintUtilities.equal(this.shapePaint, that.shapePaint)) {
/* 648 */       return false;
/*     */     }
/* 650 */     if (!ObjectUtilities.equal(this.shapeStroke, that.shapeStroke)) {
/* 651 */       return false;
/*     */     }
/* 653 */     return true;
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
/* 665 */     return super.clone();
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
/* 678 */     stream.defaultReadObject();
/* 679 */     this.shapePaint = SerialUtilities.readPaint(stream);
/* 680 */     this.shapeStroke = SerialUtilities.readStroke(stream);
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
/* 691 */     stream.defaultWriteObject();
/* 692 */     SerialUtilities.writePaint(this.shapePaint, stream);
/* 693 */     SerialUtilities.writeStroke(this.shapeStroke, stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/StackedXYAreaRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */