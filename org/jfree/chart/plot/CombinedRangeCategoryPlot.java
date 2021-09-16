/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.LegendItemCollection;
/*     */ import org.jfree.chart.axis.AxisSpace;
/*     */ import org.jfree.chart.axis.AxisState;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.NumberAxis;
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
/*     */ public class CombinedRangeCategoryPlot
/*     */   extends CategoryPlot
/*     */   implements PlotChangeListener
/*     */ {
/*     */   private static final long serialVersionUID = 7260210007554504515L;
/*     */   private List subplots;
/*     */   private double gap;
/*     */   private transient Rectangle2D[] subplotArea;
/*     */   
/*     */   public CombinedRangeCategoryPlot() {
/* 110 */     this((ValueAxis)new NumberAxis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CombinedRangeCategoryPlot(ValueAxis rangeAxis) {
/* 119 */     super((CategoryDataset)null, (CategoryAxis)null, rangeAxis, (CategoryItemRenderer)null);
/* 120 */     this.subplots = new ArrayList();
/* 121 */     this.gap = 5.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getGap() {
/* 130 */     return this.gap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGap(double gap) {
/* 140 */     this.gap = gap;
/* 141 */     fireChangeEvent();
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
/*     */   public void add(CategoryPlot subplot) {
/* 155 */     add(subplot, 1);
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
/* 169 */     ParamChecks.nullNotPermitted(subplot, "subplot");
/* 170 */     if (weight <= 0) {
/* 171 */       throw new IllegalArgumentException("Require weight >= 1.");
/*     */     }
/*     */     
/* 174 */     subplot.setParent(this);
/* 175 */     subplot.setWeight(weight);
/* 176 */     subplot.setInsets(new RectangleInsets(0.0D, 0.0D, 0.0D, 0.0D));
/* 177 */     subplot.setRangeAxis((ValueAxis)null);
/* 178 */     subplot.setOrientation(getOrientation());
/* 179 */     subplot.addChangeListener(this);
/* 180 */     this.subplots.add(subplot);
/*     */     
/* 182 */     ValueAxis axis = getRangeAxis();
/* 183 */     if (axis != null) {
/* 184 */       axis.configure();
/*     */     }
/* 186 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(CategoryPlot subplot) {
/* 195 */     ParamChecks.nullNotPermitted(subplot, "subplot");
/* 196 */     int position = -1;
/* 197 */     int size = this.subplots.size();
/* 198 */     int i = 0;
/* 199 */     while (position == -1 && i < size) {
/* 200 */       if (this.subplots.get(i) == subplot) {
/* 201 */         position = i;
/*     */       }
/* 203 */       i++;
/*     */     } 
/* 205 */     if (position != -1) {
/* 206 */       this.subplots.remove(position);
/* 207 */       subplot.setParent(null);
/* 208 */       subplot.removeChangeListener(this);
/*     */       
/* 210 */       ValueAxis range = getRangeAxis();
/* 211 */       if (range != null) {
/* 212 */         range.configure();
/*     */       }
/*     */       
/* 215 */       ValueAxis range2 = getRangeAxis(1);
/* 216 */       if (range2 != null) {
/* 217 */         range2.configure();
/*     */       }
/* 219 */       fireChangeEvent();
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
/* 230 */     if (this.subplots != null) {
/* 231 */       return Collections.unmodifiableList(this.subplots);
/*     */     }
/*     */     
/* 234 */     return Collections.EMPTY_LIST;
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
/* 250 */     AxisSpace space = new AxisSpace();
/* 251 */     PlotOrientation orientation = getOrientation();
/*     */ 
/*     */     
/* 254 */     AxisSpace fixed = getFixedRangeAxisSpace();
/* 255 */     if (fixed != null) {
/* 256 */       if (orientation == PlotOrientation.VERTICAL) {
/* 257 */         space.setLeft(fixed.getLeft());
/* 258 */         space.setRight(fixed.getRight());
/*     */       }
/* 260 */       else if (orientation == PlotOrientation.HORIZONTAL) {
/* 261 */         space.setTop(fixed.getTop());
/* 262 */         space.setBottom(fixed.getBottom());
/*     */       } 
/*     */     } else {
/*     */       
/* 266 */       ValueAxis valueAxis = getRangeAxis();
/* 267 */       RectangleEdge valueEdge = Plot.resolveRangeAxisLocation(
/* 268 */           getRangeAxisLocation(), orientation);
/* 269 */       if (valueAxis != null) {
/* 270 */         space = valueAxis.reserveSpace(g2, this, plotArea, valueEdge, space);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 275 */     Rectangle2D adjustedPlotArea = space.shrink(plotArea, null);
/*     */     
/* 277 */     int n = this.subplots.size();
/* 278 */     int totalWeight = 0;
/* 279 */     for (int i = 0; i < n; i++) {
/* 280 */       CategoryPlot sub = this.subplots.get(i);
/* 281 */       totalWeight += sub.getWeight();
/*     */     } 
/*     */ 
/*     */     
/* 285 */     this.subplotArea = new Rectangle2D[n];
/* 286 */     double x = adjustedPlotArea.getX();
/* 287 */     double y = adjustedPlotArea.getY();
/* 288 */     double usableSize = 0.0D;
/* 289 */     if (orientation == PlotOrientation.VERTICAL) {
/* 290 */       usableSize = adjustedPlotArea.getWidth() - this.gap * (n - 1);
/*     */     }
/* 292 */     else if (orientation == PlotOrientation.HORIZONTAL) {
/* 293 */       usableSize = adjustedPlotArea.getHeight() - this.gap * (n - 1);
/*     */     } 
/*     */     
/* 296 */     for (int j = 0; j < n; j++) {
/* 297 */       CategoryPlot plot = this.subplots.get(j);
/*     */ 
/*     */       
/* 300 */       if (orientation == PlotOrientation.VERTICAL) {
/* 301 */         double w = usableSize * plot.getWeight() / totalWeight;
/* 302 */         this.subplotArea[j] = new Rectangle2D.Double(x, y, w, adjustedPlotArea
/* 303 */             .getHeight());
/* 304 */         x = x + w + this.gap;
/*     */       }
/* 306 */       else if (orientation == PlotOrientation.HORIZONTAL) {
/* 307 */         double h = usableSize * plot.getWeight() / totalWeight;
/* 308 */         this.subplotArea[j] = new Rectangle2D.Double(x, y, adjustedPlotArea
/* 309 */             .getWidth(), h);
/* 310 */         y = y + h + this.gap;
/*     */       } 
/*     */       
/* 313 */       AxisSpace subSpace = plot.calculateDomainAxisSpace(g2, this.subplotArea[j], (AxisSpace)null);
/*     */       
/* 315 */       space.ensureAtLeast(subSpace);
/*     */     } 
/*     */ 
/*     */     
/* 319 */     return space;
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
/*     */   public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info) {
/* 341 */     if (info != null) {
/* 342 */       info.setPlotArea(area);
/*     */     }
/*     */ 
/*     */     
/* 346 */     RectangleInsets insets = getInsets();
/* 347 */     insets.trim(area);
/*     */ 
/*     */     
/* 350 */     AxisSpace space = calculateAxisSpace(g2, area);
/* 351 */     Rectangle2D dataArea = space.shrink(area, null);
/*     */ 
/*     */     
/* 354 */     setFixedDomainAxisSpaceForSubplots(space);
/*     */ 
/*     */     
/* 357 */     ValueAxis axis = getRangeAxis();
/* 358 */     RectangleEdge rangeEdge = getRangeAxisEdge();
/* 359 */     double cursor = RectangleEdge.coordinate(dataArea, rangeEdge);
/* 360 */     AxisState state = axis.draw(g2, cursor, area, dataArea, rangeEdge, info);
/*     */     
/* 362 */     if (parentState == null) {
/* 363 */       parentState = new PlotState();
/*     */     }
/* 365 */     parentState.getSharedAxisStates().put(axis, state);
/*     */ 
/*     */     
/* 368 */     for (int i = 0; i < this.subplots.size(); i++) {
/* 369 */       CategoryPlot plot = this.subplots.get(i);
/* 370 */       PlotRenderingInfo subplotInfo = null;
/* 371 */       if (info != null) {
/* 372 */         subplotInfo = new PlotRenderingInfo(info.getOwner());
/* 373 */         info.addSubplotInfo(subplotInfo);
/*     */       } 
/* 375 */       Point2D subAnchor = null;
/* 376 */       if (anchor != null && this.subplotArea[i].contains(anchor)) {
/* 377 */         subAnchor = anchor;
/*     */       }
/* 379 */       plot.draw(g2, this.subplotArea[i], subAnchor, parentState, subplotInfo);
/*     */     } 
/*     */ 
/*     */     
/* 383 */     if (info != null) {
/* 384 */       info.setDataArea(dataArea);
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
/*     */   public void setOrientation(PlotOrientation orientation) {
/* 396 */     super.setOrientation(orientation);
/* 397 */     Iterator<CategoryPlot> iterator = this.subplots.iterator();
/* 398 */     while (iterator.hasNext()) {
/* 399 */       CategoryPlot plot = iterator.next();
/* 400 */       plot.setOrientation(orientation);
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
/* 412 */     setNotify(false);
/* 413 */     super.setShadowGenerator(generator);
/* 414 */     Iterator<CategoryPlot> iterator = this.subplots.iterator();
/* 415 */     while (iterator.hasNext()) {
/* 416 */       CategoryPlot plot = iterator.next();
/* 417 */       plot.setShadowGenerator(generator);
/*     */     } 
/* 419 */     setNotify(true);
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
/* 437 */     Range result = null;
/* 438 */     if (this.subplots != null) {
/* 439 */       Iterator<CategoryPlot> iterator = this.subplots.iterator();
/* 440 */       while (iterator.hasNext()) {
/* 441 */         CategoryPlot subplot = iterator.next();
/* 442 */         result = Range.combine(result, subplot.getDataRange(axis));
/*     */       } 
/*     */     } 
/* 445 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegendItemCollection getLegendItems() {
/* 455 */     LegendItemCollection result = getFixedLegendItems();
/* 456 */     if (result == null) {
/* 457 */       result = new LegendItemCollection();
/* 458 */       if (this.subplots != null) {
/* 459 */         Iterator<CategoryPlot> iterator = this.subplots.iterator();
/* 460 */         while (iterator.hasNext()) {
/* 461 */           CategoryPlot plot = iterator.next();
/* 462 */           LegendItemCollection more = plot.getLegendItems();
/* 463 */           result.addAll(more);
/*     */         } 
/*     */       } 
/*     */     } 
/* 467 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setFixedDomainAxisSpaceForSubplots(AxisSpace space) {
/* 477 */     Iterator<CategoryPlot> iterator = this.subplots.iterator();
/* 478 */     while (iterator.hasNext()) {
/* 479 */       CategoryPlot plot = iterator.next();
/* 480 */       plot.setFixedDomainAxisSpace(space, false);
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
/*     */   public void handleClick(int x, int y, PlotRenderingInfo info) {
/* 494 */     Rectangle2D dataArea = info.getDataArea();
/* 495 */     if (dataArea.contains(x, y)) {
/* 496 */       for (int i = 0; i < this.subplots.size(); i++) {
/* 497 */         CategoryPlot subplot = this.subplots.get(i);
/* 498 */         PlotRenderingInfo subplotInfo = info.getSubplotInfo(i);
/* 499 */         subplot.handleClick(x, y, subplotInfo);
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
/* 512 */     notifyListeners(event);
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
/* 524 */     if (obj == this) {
/* 525 */       return true;
/*     */     }
/* 527 */     if (!(obj instanceof CombinedRangeCategoryPlot)) {
/* 528 */       return false;
/*     */     }
/* 530 */     CombinedRangeCategoryPlot that = (CombinedRangeCategoryPlot)obj;
/* 531 */     if (this.gap != that.gap) {
/* 532 */       return false;
/*     */     }
/* 534 */     if (!ObjectUtilities.equal(this.subplots, that.subplots)) {
/* 535 */       return false;
/*     */     }
/* 537 */     return super.equals(obj);
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
/* 551 */     CombinedRangeCategoryPlot result = (CombinedRangeCategoryPlot)super.clone();
/* 552 */     result.subplots = (List)ObjectUtilities.deepClone(this.subplots);
/* 553 */     for (Iterator<Plot> it = result.subplots.iterator(); it.hasNext(); ) {
/* 554 */       Plot child = it.next();
/* 555 */       child.setParent(result);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 560 */     ValueAxis rangeAxis = result.getRangeAxis();
/* 561 */     if (rangeAxis != null) {
/* 562 */       rangeAxis.configure();
/*     */     }
/*     */     
/* 565 */     return result;
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 579 */     stream.defaultReadObject();
/*     */ 
/*     */ 
/*     */     
/* 583 */     ValueAxis rangeAxis = getRangeAxis();
/* 584 */     if (rangeAxis != null)
/* 585 */       rangeAxis.configure(); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/CombinedRangeCategoryPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */