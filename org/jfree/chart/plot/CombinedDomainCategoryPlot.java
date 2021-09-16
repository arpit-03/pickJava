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
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.event.PlotChangeEvent;
/*     */ import org.jfree.chart.event.PlotChangeListener;
/*     */ import org.jfree.chart.renderer.category.CategoryItemRenderer;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.chart.util.ShadowGenerator;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.category.CategoryDataset;
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
/*     */ public class CombinedDomainCategoryPlot
/*     */   extends CategoryPlot
/*     */   implements PlotChangeListener
/*     */ {
/*     */   private static final long serialVersionUID = 8207194522653701572L;
/*     */   private List subplots;
/*     */   private double gap;
/*     */   private transient Rectangle2D[] subplotAreas;
/*     */   
/*     */   public CombinedDomainCategoryPlot() {
/* 113 */     this(new CategoryAxis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CombinedDomainCategoryPlot(CategoryAxis domainAxis) {
/* 123 */     super((CategoryDataset)null, domainAxis, (ValueAxis)null, (CategoryItemRenderer)null);
/* 124 */     this.subplots = new ArrayList();
/* 125 */     this.gap = 5.0D;
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
/* 136 */     return this.gap;
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
/*     */   public void setGap(double gap) {
/* 148 */     this.gap = gap;
/* 149 */     fireChangeEvent();
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
/*     */   public void add(CategoryPlot subplot) {
/* 162 */     add(subplot, 1);
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
/*     */   public void add(CategoryPlot subplot, int weight) {
/* 176 */     ParamChecks.nullNotPermitted(subplot, "subplot");
/* 177 */     if (weight < 1) {
/* 178 */       throw new IllegalArgumentException("Require weight >= 1.");
/*     */     }
/* 180 */     subplot.setParent(this);
/* 181 */     subplot.setWeight(weight);
/* 182 */     subplot.setInsets(new RectangleInsets(0.0D, 0.0D, 0.0D, 0.0D));
/* 183 */     subplot.setDomainAxis((CategoryAxis)null);
/* 184 */     subplot.setOrientation(getOrientation());
/* 185 */     subplot.addChangeListener(this);
/* 186 */     this.subplots.add(subplot);
/* 187 */     CategoryAxis axis = getDomainAxis();
/* 188 */     if (axis != null) {
/* 189 */       axis.configure();
/*     */     }
/* 191 */     fireChangeEvent();
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
/*     */   public void remove(CategoryPlot subplot) {
/* 203 */     ParamChecks.nullNotPermitted(subplot, "subplot");
/* 204 */     int position = -1;
/* 205 */     int size = this.subplots.size();
/* 206 */     int i = 0;
/* 207 */     while (position == -1 && i < size) {
/* 208 */       if (this.subplots.get(i) == subplot) {
/* 209 */         position = i;
/*     */       }
/* 211 */       i++;
/*     */     } 
/* 213 */     if (position != -1) {
/* 214 */       this.subplots.remove(position);
/* 215 */       subplot.setParent(null);
/* 216 */       subplot.removeChangeListener(this);
/* 217 */       CategoryAxis domain = getDomainAxis();
/* 218 */       if (domain != null) {
/* 219 */         domain.configure();
/*     */       }
/* 221 */       fireChangeEvent();
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
/* 232 */     if (this.subplots != null) {
/* 233 */       return Collections.unmodifiableList(this.subplots);
/*     */     }
/*     */     
/* 236 */     return Collections.EMPTY_LIST;
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
/*     */   public CategoryPlot findSubplot(PlotRenderingInfo info, Point2D source) {
/* 250 */     ParamChecks.nullNotPermitted(info, "info");
/* 251 */     ParamChecks.nullNotPermitted(source, "source");
/* 252 */     CategoryPlot result = null;
/* 253 */     int subplotIndex = info.getSubplotIndex(source);
/* 254 */     if (subplotIndex >= 0) {
/* 255 */       result = this.subplots.get(subplotIndex);
/*     */     }
/* 257 */     return result;
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
/*     */   public void zoomRangeAxes(double factor, PlotRenderingInfo info, Point2D source) {
/* 270 */     zoomRangeAxes(factor, info, source, false);
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
/*     */   public void zoomRangeAxes(double factor, PlotRenderingInfo info, Point2D source, boolean useAnchor) {
/* 285 */     CategoryPlot subplot = findSubplot(info, source);
/* 286 */     if (subplot != null) {
/* 287 */       subplot.zoomRangeAxes(factor, info, source, useAnchor);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 292 */       Iterator<CategoryPlot> iterator = getSubplots().iterator();
/* 293 */       while (iterator.hasNext()) {
/* 294 */         subplot = iterator.next();
/* 295 */         subplot.zoomRangeAxes(factor, info, source, useAnchor);
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
/*     */   public void zoomRangeAxes(double lowerPercent, double upperPercent, PlotRenderingInfo info, Point2D source) {
/* 312 */     CategoryPlot subplot = findSubplot(info, source);
/* 313 */     if (subplot != null) {
/* 314 */       subplot.zoomRangeAxes(lowerPercent, upperPercent, info, source);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 319 */       Iterator<CategoryPlot> iterator = getSubplots().iterator();
/* 320 */       while (iterator.hasNext()) {
/* 321 */         subplot = iterator.next();
/* 322 */         subplot.zoomRangeAxes(lowerPercent, upperPercent, info, source);
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
/*     */   protected AxisSpace calculateAxisSpace(Graphics2D g2, Rectangle2D plotArea) {
/* 339 */     AxisSpace space = new AxisSpace();
/* 340 */     PlotOrientation orientation = getOrientation();
/*     */ 
/*     */     
/* 343 */     AxisSpace fixed = getFixedDomainAxisSpace();
/* 344 */     if (fixed != null) {
/* 345 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 346 */         space.setLeft(fixed.getLeft());
/* 347 */         space.setRight(fixed.getRight());
/*     */       }
/* 349 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 350 */         space.setTop(fixed.getTop());
/* 351 */         space.setBottom(fixed.getBottom());
/*     */       } 
/*     */     } else {
/*     */       
/* 355 */       CategoryAxis categoryAxis = getDomainAxis();
/* 356 */       RectangleEdge categoryEdge = Plot.resolveDomainAxisLocation(
/* 357 */           getDomainAxisLocation(), orientation);
/* 358 */       if (categoryAxis != null) {
/* 359 */         space = categoryAxis.reserveSpace(g2, this, plotArea, categoryEdge, space);
/*     */ 
/*     */       
/*     */       }
/* 363 */       else if (getDrawSharedDomainAxis()) {
/* 364 */         space = getDomainAxis().reserveSpace(g2, this, plotArea, categoryEdge, space);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 370 */     Rectangle2D adjustedPlotArea = space.shrink(plotArea, null);
/*     */ 
/*     */     
/* 373 */     int n = this.subplots.size();
/* 374 */     int totalWeight = 0;
/* 375 */     for (int i = 0; i < n; i++) {
/* 376 */       CategoryPlot sub = this.subplots.get(i);
/* 377 */       totalWeight += sub.getWeight();
/*     */     } 
/* 379 */     this.subplotAreas = new Rectangle2D[n];
/* 380 */     double x = adjustedPlotArea.getX();
/* 381 */     double y = adjustedPlotArea.getY();
/* 382 */     double usableSize = 0.0D;
/* 383 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 384 */       usableSize = adjustedPlotArea.getWidth() - this.gap * (n - 1);
/*     */     }
/* 386 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 387 */       usableSize = adjustedPlotArea.getHeight() - this.gap * (n - 1);
/*     */     } 
/*     */     
/* 390 */     for (int j = 0; j < n; j++) {
/* 391 */       CategoryPlot plot = this.subplots.get(j);
/*     */ 
/*     */       
/* 394 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 395 */         double w = usableSize * plot.getWeight() / totalWeight;
/* 396 */         this.subplotAreas[j] = new Rectangle2D.Double(x, y, w, adjustedPlotArea
/* 397 */             .getHeight());
/* 398 */         x = x + w + this.gap;
/*     */       }
/* 400 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 401 */         double h = usableSize * plot.getWeight() / totalWeight;
/* 402 */         this.subplotAreas[j] = new Rectangle2D.Double(x, y, adjustedPlotArea
/* 403 */             .getWidth(), h);
/* 404 */         y = y + h + this.gap;
/*     */       } 
/*     */       
/* 407 */       AxisSpace subSpace = plot.calculateRangeAxisSpace(g2, this.subplotAreas[j], (AxisSpace)null);
/*     */       
/* 409 */       space.ensureAtLeast(subSpace);
/*     */     } 
/*     */ 
/*     */     
/* 413 */     return space;
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
/*     */   public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info) {
/* 434 */     if (info != null) {
/* 435 */       info.setPlotArea(area);
/*     */     }
/*     */ 
/*     */     
/* 439 */     RectangleInsets insets = getInsets();
/* 440 */     area.setRect(area.getX() + insets.getLeft(), area
/* 441 */         .getY() + insets.getTop(), area
/* 442 */         .getWidth() - insets.getLeft() - insets.getRight(), area
/* 443 */         .getHeight() - insets.getTop() - insets.getBottom());
/*     */ 
/*     */ 
/*     */     
/* 447 */     setFixedRangeAxisSpaceForSubplots((AxisSpace)null);
/* 448 */     AxisSpace space = calculateAxisSpace(g2, area);
/* 449 */     Rectangle2D dataArea = space.shrink(area, null);
/*     */ 
/*     */     
/* 452 */     setFixedRangeAxisSpaceForSubplots(space);
/*     */ 
/*     */     
/* 455 */     CategoryAxis axis = getDomainAxis();
/* 456 */     RectangleEdge domainEdge = getDomainAxisEdge();
/* 457 */     double cursor = RectangleEdge.coordinate(dataArea, domainEdge);
/* 458 */     AxisState axisState = axis.draw(g2, cursor, area, dataArea, domainEdge, info);
/*     */     
/* 460 */     if (parentState == null) {
/* 461 */       parentState = new PlotState();
/*     */     }
/* 463 */     parentState.getSharedAxisStates().put(axis, axisState);
/*     */ 
/*     */     
/* 466 */     for (int i = 0; i < this.subplots.size(); i++) {
/* 467 */       CategoryPlot plot = this.subplots.get(i);
/* 468 */       PlotRenderingInfo subplotInfo = null;
/* 469 */       if (info != null) {
/* 470 */         subplotInfo = new PlotRenderingInfo(info.getOwner());
/* 471 */         info.addSubplotInfo(subplotInfo);
/*     */       } 
/* 473 */       Point2D subAnchor = null;
/* 474 */       if (anchor != null && this.subplotAreas[i].contains(anchor)) {
/* 475 */         subAnchor = anchor;
/*     */       }
/* 477 */       plot.draw(g2, this.subplotAreas[i], subAnchor, parentState, subplotInfo);
/*     */     } 
/*     */ 
/*     */     
/* 481 */     if (info != null) {
/* 482 */       info.setDataArea(dataArea);
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
/*     */   protected void setFixedRangeAxisSpaceForSubplots(AxisSpace space) {
/* 494 */     Iterator<CategoryPlot> iterator = this.subplots.iterator();
/* 495 */     while (iterator.hasNext()) {
/* 496 */       CategoryPlot plot = iterator.next();
/* 497 */       plot.setFixedRangeAxisSpace(space, false);
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
/* 508 */     super.setOrientation(orientation);
/* 509 */     Iterator<CategoryPlot> iterator = this.subplots.iterator();
/* 510 */     while (iterator.hasNext()) {
/* 511 */       CategoryPlot plot = iterator.next();
/* 512 */       plot.setOrientation(orientation);
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
/*     */   public void setShadowGenerator(ShadowGenerator generator) {
/* 525 */     setNotify(false);
/* 526 */     super.setShadowGenerator(generator);
/* 527 */     Iterator<CategoryPlot> iterator = this.subplots.iterator();
/* 528 */     while (iterator.hasNext()) {
/* 529 */       CategoryPlot plot = iterator.next();
/* 530 */       plot.setShadowGenerator(generator);
/*     */     } 
/* 532 */     setNotify(true);
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
/*     */   public Range getDataRange(ValueAxis axis) {
/* 552 */     return super.getDataRange(axis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegendItemCollection getLegendItems() {
/* 562 */     LegendItemCollection result = getFixedLegendItems();
/* 563 */     if (result == null) {
/* 564 */       result = new LegendItemCollection();
/* 565 */       if (this.subplots != null) {
/* 566 */         Iterator<CategoryPlot> iterator = this.subplots.iterator();
/* 567 */         while (iterator.hasNext()) {
/* 568 */           CategoryPlot plot = iterator.next();
/* 569 */           LegendItemCollection more = plot.getLegendItems();
/* 570 */           result.addAll(more);
/*     */         } 
/*     */       } 
/*     */     } 
/* 574 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getCategories() {
/* 585 */     List<Comparable> result = new ArrayList();
/* 586 */     if (this.subplots != null) {
/* 587 */       Iterator<CategoryPlot> iterator = this.subplots.iterator();
/* 588 */       while (iterator.hasNext()) {
/* 589 */         CategoryPlot plot = iterator.next();
/* 590 */         List more = plot.getCategories();
/* 591 */         Iterator<Comparable> moreIterator = more.iterator();
/* 592 */         while (moreIterator.hasNext()) {
/* 593 */           Comparable category = moreIterator.next();
/* 594 */           if (!result.contains(category)) {
/* 595 */             result.add(category);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 600 */     return Collections.unmodifiableList(result);
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
/*     */   public List getCategoriesForAxis(CategoryAxis axis) {
/* 616 */     return getCategories();
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
/*     */   public void handleClick(int x, int y, PlotRenderingInfo info) {
/* 630 */     Rectangle2D dataArea = info.getDataArea();
/* 631 */     if (dataArea.contains(x, y)) {
/* 632 */       for (int i = 0; i < this.subplots.size(); i++) {
/* 633 */         CategoryPlot subplot = this.subplots.get(i);
/* 634 */         PlotRenderingInfo subplotInfo = info.getSubplotInfo(i);
/* 635 */         subplot.handleClick(x, y, subplotInfo);
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
/*     */   public void plotChanged(PlotChangeEvent event) {
/* 649 */     notifyListeners(event);
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
/* 661 */     if (obj == this) {
/* 662 */       return true;
/*     */     }
/* 664 */     if (!(obj instanceof CombinedDomainCategoryPlot)) {
/* 665 */       return false;
/*     */     }
/* 667 */     CombinedDomainCategoryPlot that = (CombinedDomainCategoryPlot)obj;
/* 668 */     if (this.gap != that.gap) {
/* 669 */       return false;
/*     */     }
/* 671 */     if (!ObjectUtilities.equal(this.subplots, that.subplots)) {
/* 672 */       return false;
/*     */     }
/* 674 */     return super.equals(obj);
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 689 */     CombinedDomainCategoryPlot result = (CombinedDomainCategoryPlot)super.clone();
/* 690 */     result.subplots = (List)ObjectUtilities.deepClone(this.subplots);
/* 691 */     for (Iterator<Plot> it = result.subplots.iterator(); it.hasNext(); ) {
/* 692 */       Plot child = it.next();
/* 693 */       child.setParent(result);
/*     */     } 
/* 695 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/CombinedDomainCategoryPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */