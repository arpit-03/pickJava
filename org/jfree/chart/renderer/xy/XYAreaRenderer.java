/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Area;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.LegendItem;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
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
/*     */ import org.jfree.ui.GradientPaintTransformer;
/*     */ import org.jfree.ui.StandardGradientPaintTransformer;
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
/*     */ public class XYAreaRenderer
/*     */   extends AbstractXYItemRenderer
/*     */   implements XYItemRenderer, PublicCloneable
/*     */ {
/*     */   private static final long serialVersionUID = -4481971353973876747L;
/*     */   public static final int SHAPES = 1;
/*     */   public static final int LINES = 2;
/*     */   public static final int SHAPES_AND_LINES = 3;
/*     */   public static final int AREA = 4;
/*     */   public static final int AREA_AND_SHAPES = 5;
/*     */   private boolean plotShapes;
/*     */   private boolean plotLines;
/*     */   private boolean plotArea;
/*     */   private boolean showOutline;
/*     */   private transient Shape legendArea;
/*     */   private boolean useFillPaint;
/*     */   private GradientPaintTransformer gradientTransformer;
/*     */   
/*     */   static class XYAreaRendererState
/*     */     extends XYItemRendererState
/*     */   {
/*     */     public GeneralPath area;
/*     */     public Line2D line;
/*     */     
/*     */     public XYAreaRendererState(PlotRenderingInfo info) {
/* 155 */       super(info);
/* 156 */       this.area = new GeneralPath();
/* 157 */       this.line = new Line2D.Double();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYAreaRenderer() {
/* 219 */     this(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYAreaRenderer(int type) {
/* 228 */     this(type, (XYToolTipGenerator)null, (XYURLGenerator)null);
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
/*     */   public XYAreaRenderer(int type, XYToolTipGenerator toolTipGenerator, XYURLGenerator urlGenerator) {
/* 246 */     setBaseToolTipGenerator(toolTipGenerator);
/* 247 */     setURLGenerator(urlGenerator);
/*     */     
/* 249 */     if (type == 1) {
/* 250 */       this.plotShapes = true;
/*     */     }
/* 252 */     if (type == 2) {
/* 253 */       this.plotLines = true;
/*     */     }
/* 255 */     if (type == 3) {
/* 256 */       this.plotShapes = true;
/* 257 */       this.plotLines = true;
/*     */     } 
/* 259 */     if (type == 4) {
/* 260 */       this.plotArea = true;
/*     */     }
/* 262 */     if (type == 5) {
/* 263 */       this.plotArea = true;
/* 264 */       this.plotShapes = true;
/*     */     } 
/* 266 */     this.showOutline = false;
/* 267 */     GeneralPath area = new GeneralPath();
/* 268 */     area.moveTo(0.0F, -4.0F);
/* 269 */     area.lineTo(3.0F, -2.0F);
/* 270 */     area.lineTo(4.0F, 4.0F);
/* 271 */     area.lineTo(-4.0F, 4.0F);
/* 272 */     area.lineTo(-3.0F, -2.0F);
/* 273 */     area.closePath();
/* 274 */     this.legendArea = area;
/* 275 */     this.useFillPaint = false;
/* 276 */     this.gradientTransformer = (GradientPaintTransformer)new StandardGradientPaintTransformer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPlotShapes() {
/* 285 */     return this.plotShapes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPlotLines() {
/* 294 */     return this.plotLines;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPlotArea() {
/* 303 */     return this.plotArea;
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
/* 315 */     return this.showOutline;
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
/*     */   public void setOutline(boolean show) {
/* 327 */     this.showOutline = show;
/* 328 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape getLegendArea() {
/* 337 */     return this.legendArea;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLegendArea(Shape area) {
/* 347 */     ParamChecks.nullNotPermitted(area, "area");
/* 348 */     this.legendArea = area;
/* 349 */     fireChangeEvent();
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
/*     */   public boolean getUseFillPaint() {
/* 361 */     return this.useFillPaint;
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
/*     */   public void setUseFillPaint(boolean use) {
/* 374 */     this.useFillPaint = use;
/* 375 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientPaintTransformer getGradientTransformer() {
/* 386 */     return this.gradientTransformer;
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
/*     */   public void setGradientTransformer(GradientPaintTransformer transformer) {
/* 398 */     ParamChecks.nullNotPermitted(transformer, "transformer");
/* 399 */     this.gradientTransformer = transformer;
/* 400 */     fireChangeEvent();
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
/*     */   public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset data, PlotRenderingInfo info) {
/* 419 */     XYAreaRendererState state = new XYAreaRendererState(info);
/*     */ 
/*     */ 
/*     */     
/* 423 */     state.setProcessVisibleItemsOnly(false);
/* 424 */     return state;
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
/* 438 */     LegendItem result = null;
/* 439 */     XYPlot xyplot = getPlot();
/* 440 */     if (xyplot != null) {
/* 441 */       XYDataset dataset = xyplot.getDataset(datasetIndex);
/* 442 */       if (dataset != null) {
/* 443 */         XYSeriesLabelGenerator lg = getLegendItemLabelGenerator();
/* 444 */         String label = lg.generateLabel(dataset, series);
/* 445 */         String description = label;
/* 446 */         String toolTipText = null;
/* 447 */         if (getLegendItemToolTipGenerator() != null) {
/* 448 */           toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*     */         }
/*     */         
/* 451 */         String urlText = null;
/* 452 */         if (getLegendItemURLGenerator() != null) {
/* 453 */           urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*     */         }
/*     */         
/* 456 */         Paint paint = lookupSeriesPaint(series);
/* 457 */         result = new LegendItem(label, description, toolTipText, urlText, this.legendArea, paint);
/*     */         
/* 459 */         result.setLabelFont(lookupLegendTextFont(series));
/* 460 */         Paint labelPaint = lookupLegendTextPaint(series);
/* 461 */         if (labelPaint != null) {
/* 462 */           result.setLabelPaint(labelPaint);
/*     */         }
/* 464 */         result.setDataset((Dataset)dataset);
/* 465 */         result.setDatasetIndex(datasetIndex);
/* 466 */         result.setSeriesKey(dataset.getSeriesKey(series));
/* 467 */         result.setSeriesIndex(series);
/*     */       } 
/*     */     } 
/* 470 */     return result;
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
/* 497 */     if (!getItemVisible(series, item)) {
/*     */       return;
/*     */     }
/* 500 */     XYAreaRendererState areaState = (XYAreaRendererState)state;
/*     */ 
/*     */     
/* 503 */     double x1 = dataset.getXValue(series, item);
/* 504 */     double y1 = dataset.getYValue(series, item);
/* 505 */     if (Double.isNaN(y1)) {
/* 506 */       y1 = 0.0D;
/*     */     }
/* 508 */     double transX1 = domainAxis.valueToJava2D(x1, dataArea, plot
/* 509 */         .getDomainAxisEdge());
/* 510 */     double transY1 = rangeAxis.valueToJava2D(y1, dataArea, plot
/* 511 */         .getRangeAxisEdge());
/*     */ 
/*     */ 
/*     */     
/* 515 */     int itemCount = dataset.getItemCount(series);
/* 516 */     double x0 = dataset.getXValue(series, Math.max(item - 1, 0));
/* 517 */     double y0 = dataset.getYValue(series, Math.max(item - 1, 0));
/* 518 */     if (Double.isNaN(y0)) {
/* 519 */       y0 = 0.0D;
/*     */     }
/* 521 */     double transX0 = domainAxis.valueToJava2D(x0, dataArea, plot
/* 522 */         .getDomainAxisEdge());
/* 523 */     double transY0 = rangeAxis.valueToJava2D(y0, dataArea, plot
/* 524 */         .getRangeAxisEdge());
/*     */     
/* 526 */     double x2 = dataset.getXValue(series, Math.min(item + 1, itemCount - 1));
/*     */     
/* 528 */     double y2 = dataset.getYValue(series, Math.min(item + 1, itemCount - 1));
/*     */     
/* 530 */     if (Double.isNaN(y2)) {
/* 531 */       y2 = 0.0D;
/*     */     }
/* 533 */     double transX2 = domainAxis.valueToJava2D(x2, dataArea, plot
/* 534 */         .getDomainAxisEdge());
/* 535 */     double transY2 = rangeAxis.valueToJava2D(y2, dataArea, plot
/* 536 */         .getRangeAxisEdge());
/*     */     
/* 538 */     double transZero = rangeAxis.valueToJava2D(0.0D, dataArea, plot
/* 539 */         .getRangeAxisEdge());
/* 540 */     GeneralPath hotspot = new GeneralPath();
/* 541 */     if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
/* 542 */       moveTo(hotspot, transZero, (transX0 + transX1) / 2.0D);
/* 543 */       lineTo(hotspot, (transY0 + transY1) / 2.0D, (transX0 + transX1) / 2.0D);
/*     */       
/* 545 */       lineTo(hotspot, transY1, transX1);
/* 546 */       lineTo(hotspot, (transY1 + transY2) / 2.0D, (transX1 + transX2) / 2.0D);
/*     */       
/* 548 */       lineTo(hotspot, transZero, (transX1 + transX2) / 2.0D);
/*     */     } else {
/*     */       
/* 551 */       moveTo(hotspot, (transX0 + transX1) / 2.0D, transZero);
/* 552 */       lineTo(hotspot, (transX0 + transX1) / 2.0D, (transY0 + transY1) / 2.0D);
/*     */       
/* 554 */       lineTo(hotspot, transX1, transY1);
/* 555 */       lineTo(hotspot, (transX1 + transX2) / 2.0D, (transY1 + transY2) / 2.0D);
/*     */       
/* 557 */       lineTo(hotspot, (transX1 + transX2) / 2.0D, transZero);
/*     */     } 
/* 559 */     hotspot.closePath();
/*     */     
/* 561 */     if (item == 0) {
/* 562 */       areaState.area = new GeneralPath();
/*     */       
/* 564 */       double zero = rangeAxis.valueToJava2D(0.0D, dataArea, plot
/* 565 */           .getRangeAxisEdge());
/* 566 */       if (plot.getOrientation() == PlotOrientation.VERTICAL) {
/* 567 */         moveTo(areaState.area, transX1, zero);
/*     */       }
/* 569 */       else if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
/* 570 */         moveTo(areaState.area, zero, transX1);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 575 */     if (plot.getOrientation() == PlotOrientation.VERTICAL) {
/* 576 */       lineTo(areaState.area, transX1, transY1);
/*     */     }
/* 578 */     else if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
/* 579 */       lineTo(areaState.area, transY1, transX1);
/*     */     } 
/*     */     
/* 582 */     PlotOrientation orientation = plot.getOrientation();
/* 583 */     Paint paint = getItemPaint(series, item);
/* 584 */     Stroke stroke = getItemStroke(series, item);
/* 585 */     g2.setPaint(paint);
/* 586 */     g2.setStroke(stroke);
/*     */ 
/*     */     
/* 589 */     if (getPlotShapes()) {
/* 590 */       Shape shape = getItemShape(series, item);
/* 591 */       if (orientation == PlotOrientation.VERTICAL) {
/* 592 */         shape = ShapeUtilities.createTranslatedShape(shape, transX1, transY1);
/*     */       
/*     */       }
/* 595 */       else if (orientation == PlotOrientation.HORIZONTAL) {
/* 596 */         shape = ShapeUtilities.createTranslatedShape(shape, transY1, transX1);
/*     */       } 
/*     */       
/* 599 */       g2.draw(shape);
/*     */     } 
/*     */     
/* 602 */     if (getPlotLines() && 
/* 603 */       item > 0) {
/* 604 */       if (plot.getOrientation() == PlotOrientation.VERTICAL) {
/* 605 */         areaState.line.setLine(transX0, transY0, transX1, transY1);
/*     */       }
/* 607 */       else if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
/* 608 */         areaState.line.setLine(transY0, transX0, transY1, transX1);
/*     */       } 
/* 610 */       g2.draw(areaState.line);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 616 */     if (getPlotArea() && item > 0 && item == itemCount - 1) {
/*     */       
/* 618 */       if (orientation == PlotOrientation.VERTICAL) {
/*     */         
/* 620 */         lineTo(areaState.area, transX1, transZero);
/* 621 */         areaState.area.closePath();
/*     */       }
/* 623 */       else if (orientation == PlotOrientation.HORIZONTAL) {
/*     */         
/* 625 */         lineTo(areaState.area, transZero, transX1);
/* 626 */         areaState.area.closePath();
/*     */       } 
/*     */       
/* 629 */       if (this.useFillPaint) {
/* 630 */         paint = lookupSeriesFillPaint(series);
/*     */       }
/* 632 */       if (paint instanceof GradientPaint) {
/* 633 */         GradientPaint gp = (GradientPaint)paint;
/* 634 */         GradientPaint adjGP = this.gradientTransformer.transform(gp, dataArea);
/*     */         
/* 636 */         g2.setPaint(adjGP);
/*     */       } 
/* 638 */       g2.fill(areaState.area);
/*     */ 
/*     */       
/* 641 */       if (isOutline()) {
/* 642 */         Shape area = areaState.area;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 649 */         Stroke outlineStroke = lookupSeriesOutlineStroke(series);
/* 650 */         if (outlineStroke instanceof BasicStroke) {
/* 651 */           BasicStroke bs = (BasicStroke)outlineStroke;
/* 652 */           if (bs.getDashArray() != null) {
/* 653 */             Area poly = new Area(areaState.area);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 660 */             Area clip = new Area(new Rectangle2D.Double(dataArea.getX() - 5.0D, dataArea.getY() - 5.0D, dataArea.getWidth() + 10.0D, dataArea.getHeight() + 10.0D));
/* 661 */             poly.intersect(clip);
/* 662 */             area = poly;
/*     */           } 
/*     */         } 
/*     */         
/* 666 */         g2.setStroke(outlineStroke);
/* 667 */         g2.setPaint(lookupSeriesOutlinePaint(series));
/* 668 */         g2.draw(area);
/*     */       } 
/*     */     } 
/*     */     
/* 672 */     int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
/* 673 */     int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
/* 674 */     updateCrosshairValues(crosshairState, x1, y1, domainAxisIndex, rangeAxisIndex, transX1, transY1, orientation);
/*     */ 
/*     */ 
/*     */     
/* 678 */     EntityCollection entities = state.getEntityCollection();
/* 679 */     if (entities != null) {
/* 680 */       addEntity(entities, hotspot, dataset, series, item, 0.0D, 0.0D);
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
/* 694 */     XYAreaRenderer clone = (XYAreaRenderer)super.clone();
/* 695 */     clone.legendArea = ShapeUtilities.clone(this.legendArea);
/* 696 */     return clone;
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
/* 708 */     if (obj == this) {
/* 709 */       return true;
/*     */     }
/* 711 */     if (!(obj instanceof XYAreaRenderer)) {
/* 712 */       return false;
/*     */     }
/* 714 */     XYAreaRenderer that = (XYAreaRenderer)obj;
/* 715 */     if (this.plotArea != that.plotArea) {
/* 716 */       return false;
/*     */     }
/* 718 */     if (this.plotLines != that.plotLines) {
/* 719 */       return false;
/*     */     }
/* 721 */     if (this.plotShapes != that.plotShapes) {
/* 722 */       return false;
/*     */     }
/* 724 */     if (this.showOutline != that.showOutline) {
/* 725 */       return false;
/*     */     }
/* 727 */     if (this.useFillPaint != that.useFillPaint) {
/* 728 */       return false;
/*     */     }
/* 730 */     if (!this.gradientTransformer.equals(that.gradientTransformer)) {
/* 731 */       return false;
/*     */     }
/* 733 */     if (!ShapeUtilities.equal(this.legendArea, that.legendArea)) {
/* 734 */       return false;
/*     */     }
/* 736 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 746 */     int result = super.hashCode();
/* 747 */     result = HashUtilities.hashCode(result, this.plotArea);
/* 748 */     result = HashUtilities.hashCode(result, this.plotLines);
/* 749 */     result = HashUtilities.hashCode(result, this.plotShapes);
/* 750 */     result = HashUtilities.hashCode(result, this.useFillPaint);
/* 751 */     return result;
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
/* 764 */     stream.defaultReadObject();
/* 765 */     this.legendArea = SerialUtilities.readShape(stream);
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
/* 776 */     stream.defaultWriteObject();
/* 777 */     SerialUtilities.writeShape(this.legendArea, stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYAreaRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */