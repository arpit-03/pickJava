/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.LegendItemCollection;
/*     */ import org.jfree.chart.axis.AxisSpace;
/*     */ import org.jfree.chart.axis.AxisState;
/*     */ import org.jfree.chart.axis.NumberAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.event.PlotChangeEvent;
/*     */ import org.jfree.chart.event.PlotChangeListener;
/*     */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.chart.util.ShadowGenerator;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CombinedRangeXYPlot
/*     */   extends XYPlot
/*     */   implements PlotChangeListener
/*     */ {
/*     */   private static final long serialVersionUID = -5177814085082031168L;
/*     */   private List<XYPlot> subplots;
/* 140 */   private double gap = 5.0D;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Rectangle2D[] subplotAreas;
/*     */ 
/*     */ 
/*     */   
/*     */   public CombinedRangeXYPlot() {
/* 149 */     this((ValueAxis)new NumberAxis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CombinedRangeXYPlot(ValueAxis rangeAxis) {
/* 158 */     super((XYDataset)null, (ValueAxis)null, rangeAxis, (XYItemRenderer)null);
/*     */ 
/*     */ 
/*     */     
/* 162 */     this.subplots = new ArrayList<XYPlot>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlotType() {
/* 172 */     return localizationResources.getString("Combined_Range_XYPlot");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getGap() {
/* 183 */     return this.gap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGap(double gap) {
/* 194 */     this.gap = gap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDomainPannable() {
/* 205 */     for (XYPlot subplot : this.subplots) {
/* 206 */       if (subplot.isDomainPannable()) {
/* 207 */         return true;
/*     */       }
/*     */     } 
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDomainPannable(boolean pannable) {
/* 221 */     for (XYPlot subplot : this.subplots) {
/* 222 */       subplot.setDomainPannable(pannable);
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
/*     */   public void add(XYPlot subplot) {
/* 235 */     add(subplot, 1);
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
/*     */   public void add(XYPlot subplot, int weight) {
/* 250 */     ParamChecks.nullNotPermitted(subplot, "subplot");
/* 251 */     if (weight <= 0) {
/* 252 */       String msg = "The 'weight' must be positive.";
/* 253 */       throw new IllegalArgumentException(msg);
/*     */     } 
/*     */ 
/*     */     
/* 257 */     subplot.setParent(this);
/* 258 */     subplot.setWeight(weight);
/* 259 */     subplot.setInsets(new RectangleInsets(0.0D, 0.0D, 0.0D, 0.0D));
/* 260 */     subplot.setRangeAxis((ValueAxis)null);
/* 261 */     subplot.addChangeListener(this);
/* 262 */     this.subplots.add(subplot);
/* 263 */     configureRangeAxes();
/* 264 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(XYPlot subplot) {
/* 274 */     ParamChecks.nullNotPermitted(subplot, "subplot");
/* 275 */     int position = -1;
/* 276 */     int size = this.subplots.size();
/* 277 */     int i = 0;
/* 278 */     while (position == -1 && i < size) {
/* 279 */       if (this.subplots.get(i) == subplot) {
/* 280 */         position = i;
/*     */       }
/* 282 */       i++;
/*     */     } 
/* 284 */     if (position != -1) {
/* 285 */       this.subplots.remove(position);
/* 286 */       subplot.setParent(null);
/* 287 */       subplot.removeChangeListener(this);
/* 288 */       configureRangeAxes();
/* 289 */       fireChangeEvent();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getSubplots() {
/* 300 */     if (this.subplots != null) {
/* 301 */       return Collections.unmodifiableList(this.subplots);
/*     */     }
/*     */     
/* 304 */     return Collections.EMPTY_LIST;
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
/*     */   protected AxisSpace calculateAxisSpace(Graphics2D g2, Rectangle2D plotArea) {
/* 320 */     AxisSpace space = new AxisSpace();
/* 321 */     PlotOrientation orientation = getOrientation();
/*     */ 
/*     */     
/* 324 */     AxisSpace fixed = getFixedRangeAxisSpace();
/* 325 */     if (fixed != null) {
/* 326 */       if (orientation == PlotOrientation.VERTICAL) {
/* 327 */         space.setLeft(fixed.getLeft());
/* 328 */         space.setRight(fixed.getRight());
/*     */       }
/* 330 */       else if (orientation == PlotOrientation.HORIZONTAL) {
/* 331 */         space.setTop(fixed.getTop());
/* 332 */         space.setBottom(fixed.getBottom());
/*     */       } 
/*     */     } else {
/*     */       
/* 336 */       ValueAxis valueAxis = getRangeAxis();
/* 337 */       RectangleEdge valueEdge = Plot.resolveRangeAxisLocation(
/* 338 */           getRangeAxisLocation(), orientation);
/*     */       
/* 340 */       if (valueAxis != null) {
/* 341 */         space = valueAxis.reserveSpace(g2, this, plotArea, valueEdge, space);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 346 */     Rectangle2D adjustedPlotArea = space.shrink(plotArea, null);
/*     */     
/* 348 */     int n = this.subplots.size();
/* 349 */     int totalWeight = 0;
/* 350 */     for (int i = 0; i < n; i++) {
/* 351 */       XYPlot sub = this.subplots.get(i);
/* 352 */       totalWeight += sub.getWeight();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 357 */     this.subplotAreas = new Rectangle2D[n];
/* 358 */     double x = adjustedPlotArea.getX();
/* 359 */     double y = adjustedPlotArea.getY();
/* 360 */     double usableSize = 0.0D;
/* 361 */     if (orientation == PlotOrientation.VERTICAL) {
/* 362 */       usableSize = adjustedPlotArea.getWidth() - this.gap * (n - 1);
/*     */     }
/* 364 */     else if (orientation == PlotOrientation.HORIZONTAL) {
/* 365 */       usableSize = adjustedPlotArea.getHeight() - this.gap * (n - 1);
/*     */     } 
/*     */     
/* 368 */     for (int j = 0; j < n; j++) {
/* 369 */       XYPlot plot = this.subplots.get(j);
/*     */ 
/*     */       
/* 372 */       if (orientation == PlotOrientation.VERTICAL) {
/* 373 */         double w = usableSize * plot.getWeight() / totalWeight;
/* 374 */         this.subplotAreas[j] = new Rectangle2D.Double(x, y, w, adjustedPlotArea
/* 375 */             .getHeight());
/* 376 */         x = x + w + this.gap;
/*     */       }
/* 378 */       else if (orientation == PlotOrientation.HORIZONTAL) {
/* 379 */         double h = usableSize * plot.getWeight() / totalWeight;
/* 380 */         this.subplotAreas[j] = new Rectangle2D.Double(x, y, adjustedPlotArea
/* 381 */             .getWidth(), h);
/* 382 */         y = y + h + this.gap;
/*     */       } 
/*     */       
/* 385 */       AxisSpace subSpace = plot.calculateDomainAxisSpace(g2, this.subplotAreas[j], (AxisSpace)null);
/*     */       
/* 387 */       space.ensureAtLeast(subSpace);
/*     */     } 
/*     */ 
/*     */     
/* 391 */     return space;
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
/*     */   public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info) {
/* 411 */     if (info != null) {
/* 412 */       info.setPlotArea(area);
/*     */     }
/*     */ 
/*     */     
/* 416 */     RectangleInsets insets = getInsets();
/* 417 */     insets.trim(area);
/*     */     
/* 419 */     AxisSpace space = calculateAxisSpace(g2, area);
/* 420 */     Rectangle2D dataArea = space.shrink(area, null);
/*     */ 
/*     */ 
/*     */     
/* 424 */     setFixedDomainAxisSpaceForSubplots(space);
/*     */ 
/*     */     
/* 427 */     ValueAxis axis = getRangeAxis();
/* 428 */     RectangleEdge edge = getRangeAxisEdge();
/* 429 */     double cursor = RectangleEdge.coordinate(dataArea, edge);
/* 430 */     AxisState axisState = axis.draw(g2, cursor, area, dataArea, edge, info);
/*     */     
/* 432 */     if (parentState == null) {
/* 433 */       parentState = new PlotState();
/*     */     }
/* 435 */     parentState.getSharedAxisStates().put(axis, axisState);
/*     */ 
/*     */     
/* 438 */     for (int i = 0; i < this.subplots.size(); i++) {
/* 439 */       XYPlot plot = this.subplots.get(i);
/* 440 */       PlotRenderingInfo subplotInfo = null;
/* 441 */       if (info != null) {
/* 442 */         subplotInfo = new PlotRenderingInfo(info.getOwner());
/* 443 */         info.addSubplotInfo(subplotInfo);
/*     */       } 
/* 445 */       plot.draw(g2, this.subplotAreas[i], anchor, parentState, subplotInfo);
/*     */     } 
/*     */ 
/*     */     
/* 449 */     if (info != null) {
/* 450 */       info.setDataArea(dataArea);
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
/*     */   public LegendItemCollection getLegendItems() {
/* 462 */     LegendItemCollection result = getFixedLegendItems();
/* 463 */     if (result == null) {
/* 464 */       result = new LegendItemCollection();
/*     */       
/* 466 */       if (this.subplots != null) {
/* 467 */         Iterator<XYPlot> iterator = this.subplots.iterator();
/* 468 */         while (iterator.hasNext()) {
/* 469 */           XYPlot plot = iterator.next();
/* 470 */           LegendItemCollection more = plot.getLegendItems();
/* 471 */           result.addAll(more);
/*     */         } 
/*     */       } 
/*     */     } 
/* 475 */     return result;
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
/*     */   public void zoomDomainAxes(double factor, PlotRenderingInfo info, Point2D source) {
/* 488 */     zoomDomainAxes(factor, info, source, false);
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
/*     */   public void zoomDomainAxes(double factor, PlotRenderingInfo info, Point2D source, boolean useAnchor) {
/* 503 */     XYPlot subplot = findSubplot(info, source);
/* 504 */     if (subplot != null) {
/* 505 */       subplot.zoomDomainAxes(factor, info, source, useAnchor);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 510 */       Iterator<XYPlot> iterator = getSubplots().iterator();
/* 511 */       while (iterator.hasNext()) {
/* 512 */         subplot = iterator.next();
/* 513 */         subplot.zoomDomainAxes(factor, info, source, useAnchor);
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
/*     */   public void zoomDomainAxes(double lowerPercent, double upperPercent, PlotRenderingInfo info, Point2D source) {
/* 530 */     XYPlot subplot = findSubplot(info, source);
/* 531 */     if (subplot != null) {
/* 532 */       subplot.zoomDomainAxes(lowerPercent, upperPercent, info, source);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 537 */       Iterator<XYPlot> iterator = getSubplots().iterator();
/* 538 */       while (iterator.hasNext()) {
/* 539 */         subplot = iterator.next();
/* 540 */         subplot.zoomDomainAxes(lowerPercent, upperPercent, info, source);
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
/*     */   public void panDomainAxes(double panRange, PlotRenderingInfo info, Point2D source) {
/* 559 */     XYPlot subplot = findSubplot(info, source);
/* 560 */     if (subplot == null) {
/*     */       return;
/*     */     }
/* 563 */     if (!subplot.isDomainPannable()) {
/*     */       return;
/*     */     }
/* 566 */     PlotRenderingInfo subplotInfo = info.getSubplotInfo(info
/* 567 */         .getSubplotIndex(source));
/* 568 */     if (subplotInfo == null) {
/*     */       return;
/*     */     }
/*     */     
/* 572 */     for (int i = 0; i < subplot.getDomainAxisCount(); i++) {
/* 573 */       ValueAxis domainAxis = subplot.getDomainAxis(i);
/* 574 */       if (domainAxis != null) {
/* 575 */         domainAxis.pan(panRange);
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
/*     */   public XYPlot findSubplot(PlotRenderingInfo info, Point2D source) {
/* 590 */     ParamChecks.nullNotPermitted(info, "info");
/* 591 */     ParamChecks.nullNotPermitted(source, "source");
/* 592 */     XYPlot result = null;
/* 593 */     int subplotIndex = info.getSubplotIndex(source);
/* 594 */     if (subplotIndex >= 0) {
/* 595 */       result = this.subplots.get(subplotIndex);
/*     */     }
/* 597 */     return result;
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
/*     */   public void setRenderer(XYItemRenderer renderer) {
/* 611 */     super.setRenderer(renderer);
/*     */ 
/*     */     
/* 614 */     Iterator<XYPlot> iterator = this.subplots.iterator();
/* 615 */     while (iterator.hasNext()) {
/* 616 */       XYPlot plot = iterator.next();
/* 617 */       plot.setRenderer(renderer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOrientation(PlotOrientation orientation) {
/* 628 */     super.setOrientation(orientation);
/* 629 */     Iterator<XYPlot> iterator = this.subplots.iterator();
/* 630 */     while (iterator.hasNext()) {
/* 631 */       XYPlot plot = iterator.next();
/* 632 */       plot.setOrientation(orientation);
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
/*     */   public void setShadowGenerator(ShadowGenerator generator) {
/* 644 */     setNotify(false);
/* 645 */     super.setShadowGenerator(generator);
/* 646 */     Iterator<XYPlot> iterator = this.subplots.iterator();
/* 647 */     while (iterator.hasNext()) {
/* 648 */       XYPlot plot = iterator.next();
/* 649 */       plot.setShadowGenerator(generator);
/*     */     } 
/* 651 */     setNotify(true);
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
/*     */   public Range getDataRange(ValueAxis axis) {
/* 669 */     Range result = null;
/* 670 */     if (this.subplots != null) {
/* 671 */       Iterator<XYPlot> iterator = this.subplots.iterator();
/* 672 */       while (iterator.hasNext()) {
/* 673 */         XYPlot subplot = iterator.next();
/* 674 */         result = Range.combine(result, subplot.getDataRange(axis));
/*     */       } 
/*     */     } 
/* 677 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFixedDomainAxisSpaceForSubplots(AxisSpace space) {
/* 687 */     Iterator<XYPlot> iterator = this.subplots.iterator();
/* 688 */     while (iterator.hasNext()) {
/* 689 */       XYPlot plot = iterator.next();
/* 690 */       plot.setFixedDomainAxisSpace(space, false);
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
/*     */   public void handleClick(int x, int y, PlotRenderingInfo info) {
/* 703 */     Rectangle2D dataArea = info.getDataArea();
/* 704 */     if (dataArea.contains(x, y)) {
/* 705 */       for (int i = 0; i < this.subplots.size(); i++) {
/* 706 */         XYPlot subplot = this.subplots.get(i);
/* 707 */         PlotRenderingInfo subplotInfo = info.getSubplotInfo(i);
/* 708 */         subplot.handleClick(x, y, subplotInfo);
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
/*     */   public void plotChanged(PlotChangeEvent event) {
/* 721 */     notifyListeners(event);
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
/* 733 */     if (obj == this) {
/* 734 */       return true;
/*     */     }
/* 736 */     if (!(obj instanceof CombinedRangeXYPlot)) {
/* 737 */       return false;
/*     */     }
/* 739 */     CombinedRangeXYPlot that = (CombinedRangeXYPlot)obj;
/* 740 */     if (this.gap != that.gap) {
/* 741 */       return false;
/*     */     }
/* 743 */     if (!ObjectUtilities.equal(this.subplots, that.subplots)) {
/* 744 */       return false;
/*     */     }
/* 746 */     return super.equals(obj);
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 760 */     CombinedRangeXYPlot result = (CombinedRangeXYPlot)super.clone();
/* 761 */     result.subplots = (List<XYPlot>)ObjectUtilities.deepClone(this.subplots);
/* 762 */     for (Iterator<XYPlot> it = result.subplots.iterator(); it.hasNext(); ) {
/* 763 */       Plot child = it.next();
/* 764 */       child.setParent(result);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 769 */     ValueAxis rangeAxis = result.getRangeAxis();
/* 770 */     if (rangeAxis != null) {
/* 771 */       rangeAxis.configure();
/*     */     }
/*     */     
/* 774 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/CombinedRangeXYPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */