/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.XYToolTipGenerator;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.urls.XYURLGenerator;
/*     */ import org.jfree.data.xy.XYDataset;
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
/*     */ public class XYStepAreaRenderer
/*     */   extends AbstractXYItemRenderer
/*     */   implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7311560779702649635L;
/*     */   public static final int SHAPES = 1;
/*     */   public static final int AREA = 2;
/*     */   public static final int AREA_AND_SHAPES = 3;
/*     */   private boolean shapesVisible;
/*     */   private boolean shapesFilled;
/*     */   private boolean plotArea;
/*     */   private boolean showOutline;
/* 120 */   protected transient Polygon pArea = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double rangeBase;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double stepPoint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYStepAreaRenderer() {
/* 140 */     this(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYStepAreaRenderer(int type) {
/* 149 */     this(type, (XYToolTipGenerator)null, (XYURLGenerator)null);
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
/*     */   public XYStepAreaRenderer(int type, XYToolTipGenerator toolTipGenerator, XYURLGenerator urlGenerator) {
/* 166 */     setBaseToolTipGenerator(toolTipGenerator);
/* 167 */     setURLGenerator(urlGenerator);
/*     */     
/* 169 */     if (type == 2) {
/* 170 */       this.plotArea = true;
/*     */     }
/* 172 */     else if (type == 1) {
/* 173 */       this.shapesVisible = true;
/*     */     }
/* 175 */     else if (type == 3) {
/* 176 */       this.plotArea = true;
/* 177 */       this.shapesVisible = true;
/*     */     } 
/* 179 */     this.showOutline = false;
/* 180 */     this.stepPoint = 1.0D;
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
/* 192 */     return this.showOutline;
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
/* 205 */     this.showOutline = show;
/* 206 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getShapesVisible() {
/* 217 */     return this.shapesVisible;
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
/*     */   public void setShapesVisible(boolean flag) {
/* 230 */     this.shapesVisible = flag;
/* 231 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShapesFilled() {
/* 242 */     return this.shapesFilled;
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
/*     */   public void setShapesFilled(boolean filled) {
/* 254 */     this.shapesFilled = filled;
/* 255 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPlotArea() {
/* 266 */     return this.plotArea;
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
/*     */   public void setPlotArea(boolean flag) {
/* 279 */     this.plotArea = flag;
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
/*     */   public double getRangeBase() {
/* 293 */     return this.rangeBase;
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
/*     */   public void setRangeBase(double val) {
/* 308 */     this.rangeBase = val;
/* 309 */     fireChangeEvent();
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
/* 326 */     return this.stepPoint;
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
/* 340 */     if (stepPoint < 0.0D || stepPoint > 1.0D) {
/* 341 */       throw new IllegalArgumentException("Requires stepPoint in [0.0;1.0]");
/*     */     }
/*     */     
/* 344 */     this.stepPoint = stepPoint;
/* 345 */     fireChangeEvent();
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
/*     */   public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset data, PlotRenderingInfo info) {
/* 365 */     XYItemRendererState state = super.initialise(g2, dataArea, plot, data, info);
/*     */ 
/*     */ 
/*     */     
/* 369 */     state.setProcessVisibleItemsOnly(false);
/* 370 */     return state;
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
/* 398 */     PlotOrientation orientation = plot.getOrientation();
/*     */ 
/*     */ 
/*     */     
/* 402 */     int itemCount = dataset.getItemCount(series);
/*     */     
/* 404 */     Paint paint = getItemPaint(series, item);
/* 405 */     Stroke seriesStroke = getItemStroke(series, item);
/* 406 */     g2.setPaint(paint);
/* 407 */     g2.setStroke(seriesStroke);
/*     */ 
/*     */     
/* 410 */     double x1 = dataset.getXValue(series, item);
/* 411 */     double y1 = dataset.getYValue(series, item);
/* 412 */     double x = x1;
/* 413 */     double y = Double.isNaN(y1) ? getRangeBase() : y1;
/* 414 */     double transX1 = domainAxis.valueToJava2D(x, dataArea, plot
/* 415 */         .getDomainAxisEdge());
/* 416 */     double transY1 = rangeAxis.valueToJava2D(y, dataArea, plot
/* 417 */         .getRangeAxisEdge());
/*     */ 
/*     */     
/* 420 */     transY1 = restrictValueToDataArea(transY1, plot, dataArea);
/*     */     
/* 422 */     if (this.pArea == null && !Double.isNaN(y1)) {
/*     */ 
/*     */       
/* 425 */       this.pArea = new Polygon();
/*     */ 
/*     */       
/* 428 */       double transY2 = rangeAxis.valueToJava2D(getRangeBase(), dataArea, plot
/* 429 */           .getRangeAxisEdge());
/*     */ 
/*     */       
/* 432 */       transY2 = restrictValueToDataArea(transY2, plot, dataArea);
/*     */ 
/*     */       
/* 435 */       if (orientation == PlotOrientation.VERTICAL) {
/* 436 */         this.pArea.addPoint((int)transX1, (int)transY2);
/*     */       }
/* 438 */       else if (orientation == PlotOrientation.HORIZONTAL) {
/* 439 */         this.pArea.addPoint((int)transY2, (int)transX1);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 448 */     if (item > 0) {
/*     */       
/* 450 */       double x0 = dataset.getXValue(series, item - 1);
/* 451 */       double y0 = Double.isNaN(y1) ? y1 : dataset.getYValue(series, item - 1);
/*     */       
/* 453 */       x = x0;
/* 454 */       y = Double.isNaN(y0) ? getRangeBase() : y0;
/* 455 */       double transX0 = domainAxis.valueToJava2D(x, dataArea, plot
/* 456 */           .getDomainAxisEdge());
/* 457 */       double transY0 = rangeAxis.valueToJava2D(y, dataArea, plot
/* 458 */           .getRangeAxisEdge());
/*     */ 
/*     */       
/* 461 */       transY0 = restrictValueToDataArea(transY0, plot, dataArea);
/*     */       
/* 463 */       if (Double.isNaN(y1)) {
/*     */ 
/*     */         
/* 466 */         transX1 = transX0;
/* 467 */         transY0 = transY1;
/*     */       } 
/* 469 */       if (transY0 != transY1) {
/*     */         
/* 471 */         double transXs = transX0 + getStepPoint() * (transX1 - transX0);
/*     */         
/* 473 */         if (orientation == PlotOrientation.VERTICAL) {
/* 474 */           this.pArea.addPoint((int)transXs, (int)transY0);
/* 475 */           this.pArea.addPoint((int)transXs, (int)transY1);
/*     */         }
/* 477 */         else if (orientation == PlotOrientation.HORIZONTAL) {
/* 478 */           this.pArea.addPoint((int)transY0, (int)transXs);
/* 479 */           this.pArea.addPoint((int)transY1, (int)transXs);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 484 */     Shape shape = null;
/* 485 */     if (!Double.isNaN(y1)) {
/*     */       
/* 487 */       if (orientation == PlotOrientation.VERTICAL) {
/* 488 */         this.pArea.addPoint((int)transX1, (int)transY1);
/*     */       }
/* 490 */       else if (orientation == PlotOrientation.HORIZONTAL) {
/* 491 */         this.pArea.addPoint((int)transY1, (int)transX1);
/*     */       } 
/*     */       
/* 494 */       if (getShapesVisible()) {
/* 495 */         shape = getItemShape(series, item);
/* 496 */         if (orientation == PlotOrientation.VERTICAL) {
/* 497 */           shape = ShapeUtilities.createTranslatedShape(shape, transX1, transY1);
/*     */         
/*     */         }
/* 500 */         else if (orientation == PlotOrientation.HORIZONTAL) {
/* 501 */           shape = ShapeUtilities.createTranslatedShape(shape, transY1, transX1);
/*     */         } 
/*     */         
/* 504 */         if (isShapesFilled()) {
/* 505 */           g2.fill(shape);
/*     */         } else {
/*     */           
/* 508 */           g2.draw(shape);
/*     */         }
/*     */       
/*     */       }
/* 512 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 513 */         shape = new Rectangle2D.Double(transX1 - 2.0D, transY1 - 2.0D, 4.0D, 4.0D);
/*     */       
/*     */       }
/* 516 */       else if (orientation == PlotOrientation.HORIZONTAL) {
/* 517 */         shape = new Rectangle2D.Double(transY1 - 2.0D, transX1 - 2.0D, 4.0D, 4.0D);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 526 */     if (getPlotArea() && item > 0 && this.pArea != null && (item == itemCount - 1 || 
/* 527 */       Double.isNaN(y1))) {
/*     */       
/* 529 */       double transY2 = rangeAxis.valueToJava2D(getRangeBase(), dataArea, plot
/* 530 */           .getRangeAxisEdge());
/*     */ 
/*     */       
/* 533 */       transY2 = restrictValueToDataArea(transY2, plot, dataArea);
/*     */       
/* 535 */       if (orientation == PlotOrientation.VERTICAL) {
/*     */         
/* 537 */         this.pArea.addPoint((int)transX1, (int)transY2);
/*     */       }
/* 539 */       else if (orientation == PlotOrientation.HORIZONTAL) {
/*     */         
/* 541 */         this.pArea.addPoint((int)transY2, (int)transX1);
/*     */       } 
/*     */ 
/*     */       
/* 545 */       g2.fill(this.pArea);
/*     */ 
/*     */       
/* 548 */       if (isOutline()) {
/* 549 */         g2.setStroke(plot.getOutlineStroke());
/* 550 */         g2.setPaint(plot.getOutlinePaint());
/* 551 */         g2.draw(this.pArea);
/*     */       } 
/*     */ 
/*     */       
/* 555 */       this.pArea = null;
/*     */     } 
/*     */ 
/*     */     
/* 559 */     if (!Double.isNaN(y1)) {
/* 560 */       int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
/* 561 */       int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
/* 562 */       updateCrosshairValues(crosshairState, x1, y1, domainAxisIndex, rangeAxisIndex, transX1, transY1, orientation);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 567 */     EntityCollection entities = state.getEntityCollection();
/* 568 */     if (entities != null) {
/* 569 */       addEntity(entities, shape, dataset, series, item, transX1, transY1);
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
/*     */   public boolean equals(Object obj) {
/* 582 */     if (obj == this) {
/* 583 */       return true;
/*     */     }
/* 585 */     if (!(obj instanceof XYStepAreaRenderer)) {
/* 586 */       return false;
/*     */     }
/* 588 */     XYStepAreaRenderer that = (XYStepAreaRenderer)obj;
/* 589 */     if (this.showOutline != that.showOutline) {
/* 590 */       return false;
/*     */     }
/* 592 */     if (this.shapesVisible != that.shapesVisible) {
/* 593 */       return false;
/*     */     }
/* 595 */     if (this.shapesFilled != that.shapesFilled) {
/* 596 */       return false;
/*     */     }
/* 598 */     if (this.plotArea != that.plotArea) {
/* 599 */       return false;
/*     */     }
/* 601 */     if (this.rangeBase != that.rangeBase) {
/* 602 */       return false;
/*     */     }
/* 604 */     if (this.stepPoint != that.stepPoint) {
/* 605 */       return false;
/*     */     }
/* 607 */     return super.equals(obj);
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
/* 619 */     return super.clone();
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
/*     */   protected static double restrictValueToDataArea(double value, XYPlot plot, Rectangle2D dataArea) {
/* 640 */     double min = 0.0D;
/* 641 */     double max = 0.0D;
/* 642 */     if (plot.getOrientation() == PlotOrientation.VERTICAL) {
/* 643 */       min = dataArea.getMinY();
/* 644 */       max = dataArea.getMaxY();
/*     */     }
/* 646 */     else if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
/* 647 */       min = dataArea.getMinX();
/* 648 */       max = dataArea.getMaxX();
/*     */     } 
/* 650 */     if (value < min) {
/* 651 */       value = min;
/*     */     }
/* 653 */     else if (value > max) {
/* 654 */       value = max;
/*     */     } 
/* 656 */     return value;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYStepAreaRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */