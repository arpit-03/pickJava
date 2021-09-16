/*     */ package org.jfree.chart.plot.dial;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.PlotState;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.general.Dataset;
/*     */ import org.jfree.data.general.DatasetChangeEvent;
/*     */ import org.jfree.data.general.DatasetChangeListener;
/*     */ import org.jfree.data.general.ValueDataset;
/*     */ import org.jfree.util.ObjectList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DialPlot
/*     */   extends Plot
/*     */   implements DialLayerChangeListener
/*     */ {
/*     */   public DialPlot() {
/* 144 */     this((ValueDataset)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   private DialLayer background = null;
/* 154 */   private DialLayer cap = null;
/* 155 */   private DialFrame dialFrame = new ArcDialFrame();
/* 156 */   private ObjectList datasets = new ObjectList(); private ObjectList scales; public DialPlot(ValueDataset dataset) {
/* 157 */     if (dataset != null) {
/* 158 */       setDataset(dataset);
/*     */     }
/* 160 */     this.scales = new ObjectList();
/* 161 */     this.datasetToScaleMap = new ObjectList();
/* 162 */     this.layers = new ArrayList();
/* 163 */     this.pointers = new ArrayList();
/* 164 */     this.viewX = 0.0D;
/* 165 */     this.viewY = 0.0D;
/* 166 */     this.viewW = 1.0D;
/* 167 */     this.viewH = 1.0D;
/*     */   }
/*     */   private ObjectList datasetToScaleMap;
/*     */   private List layers;
/*     */   private List pointers;
/*     */   private double viewX;
/*     */   private double viewY;
/*     */   private double viewW;
/*     */   private double viewH;
/*     */   
/*     */   public DialLayer getBackground() {
/* 178 */     return this.background;
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
/*     */   public void setBackground(DialLayer background) {
/* 190 */     if (this.background != null) {
/* 191 */       this.background.removeChangeListener(this);
/*     */     }
/* 193 */     this.background = background;
/* 194 */     if (background != null) {
/* 195 */       background.addChangeListener(this);
/*     */     }
/* 197 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DialLayer getCap() {
/* 208 */     return this.cap;
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
/*     */   public void setCap(DialLayer cap) {
/* 220 */     if (this.cap != null) {
/* 221 */       this.cap.removeChangeListener(this);
/*     */     }
/* 223 */     this.cap = cap;
/* 224 */     if (cap != null) {
/* 225 */       cap.addChangeListener(this);
/*     */     }
/* 227 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DialFrame getDialFrame() {
/* 238 */     return this.dialFrame;
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
/*     */   public void setDialFrame(DialFrame frame) {
/* 250 */     ParamChecks.nullNotPermitted(frame, "frame");
/* 251 */     this.dialFrame.removeChangeListener(this);
/* 252 */     this.dialFrame = frame;
/* 253 */     frame.addChangeListener(this);
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
/*     */   public double getViewX() {
/* 266 */     return this.viewX;
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
/*     */   public double getViewY() {
/* 278 */     return this.viewY;
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
/*     */   public double getViewWidth() {
/* 290 */     return this.viewW;
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
/*     */   public double getViewHeight() {
/* 302 */     return this.viewH;
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
/*     */   public void setView(double x, double y, double w, double h) {
/* 320 */     this.viewX = x;
/* 321 */     this.viewY = y;
/* 322 */     this.viewW = w;
/* 323 */     this.viewH = h;
/* 324 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLayer(DialLayer layer) {
/* 334 */     ParamChecks.nullNotPermitted(layer, "layer");
/* 335 */     this.layers.add(layer);
/* 336 */     layer.addChangeListener(this);
/* 337 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLayerIndex(DialLayer layer) {
/* 348 */     ParamChecks.nullNotPermitted(layer, "layer");
/* 349 */     return this.layers.indexOf(layer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeLayer(int index) {
/* 359 */     DialLayer layer = this.layers.get(index);
/* 360 */     if (layer != null) {
/* 361 */       layer.removeChangeListener(this);
/*     */     }
/* 363 */     this.layers.remove(index);
/* 364 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeLayer(DialLayer layer) {
/* 375 */     removeLayer(getLayerIndex(layer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPointer(DialPointer pointer) {
/* 385 */     ParamChecks.nullNotPermitted(pointer, "pointer");
/* 386 */     this.pointers.add(pointer);
/* 387 */     pointer.addChangeListener(this);
/* 388 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPointerIndex(DialPointer pointer) {
/* 399 */     ParamChecks.nullNotPermitted(pointer, "pointer");
/* 400 */     return this.pointers.indexOf(pointer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePointer(int index) {
/* 410 */     DialPointer pointer = this.pointers.get(index);
/* 411 */     if (pointer != null) {
/* 412 */       pointer.removeChangeListener(this);
/*     */     }
/* 414 */     this.pointers.remove(index);
/* 415 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePointer(DialPointer pointer) {
/* 426 */     removeLayer(getPointerIndex(pointer));
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
/*     */   public DialPointer getPointerForDataset(int datasetIndex) {
/* 438 */     DialPointer result = null;
/* 439 */     Iterator<DialPointer> iterator = this.pointers.iterator();
/* 440 */     while (iterator.hasNext()) {
/* 441 */       DialPointer p = iterator.next();
/* 442 */       if (p.getDatasetIndex() == datasetIndex) {
/* 443 */         return p;
/*     */       }
/*     */     } 
/* 446 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueDataset getDataset() {
/* 455 */     return getDataset(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueDataset getDataset(int index) {
/* 466 */     ValueDataset result = null;
/* 467 */     if (this.datasets.size() > index) {
/* 468 */       result = (ValueDataset)this.datasets.get(index);
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
/*     */   public void setDataset(ValueDataset dataset) {
/* 481 */     setDataset(0, dataset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDataset(int index, ValueDataset dataset) {
/* 492 */     ValueDataset existing = (ValueDataset)this.datasets.get(index);
/* 493 */     if (existing != null) {
/* 494 */       existing.removeChangeListener((DatasetChangeListener)this);
/*     */     }
/* 496 */     this.datasets.set(index, dataset);
/* 497 */     if (dataset != null) {
/* 498 */       dataset.addChangeListener((DatasetChangeListener)this);
/*     */     }
/*     */ 
/*     */     
/* 502 */     DatasetChangeEvent event = new DatasetChangeEvent(this, (Dataset)dataset);
/* 503 */     datasetChanged(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDatasetCount() {
/* 513 */     return this.datasets.size();
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
/*     */   public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info) {
/* 532 */     Shape origClip = g2.getClip();
/* 533 */     g2.setClip(area);
/*     */ 
/*     */     
/* 536 */     Rectangle2D frame = viewToFrame(area);
/*     */ 
/*     */     
/* 539 */     if (this.background != null && this.background.isVisible()) {
/* 540 */       if (this.background.isClippedToWindow()) {
/* 541 */         Shape savedClip = g2.getClip();
/* 542 */         g2.clip(this.dialFrame.getWindow(frame));
/* 543 */         this.background.draw(g2, this, frame, area);
/* 544 */         g2.setClip(savedClip);
/*     */       } else {
/*     */         
/* 547 */         this.background.draw(g2, this, frame, area);
/*     */       } 
/*     */     }
/*     */     
/* 551 */     Iterator<DialLayer> iterator = this.layers.iterator();
/* 552 */     while (iterator.hasNext()) {
/* 553 */       DialLayer current = iterator.next();
/* 554 */       if (current.isVisible()) {
/* 555 */         if (current.isClippedToWindow()) {
/* 556 */           Shape savedClip = g2.getClip();
/* 557 */           g2.clip(this.dialFrame.getWindow(frame));
/* 558 */           current.draw(g2, this, frame, area);
/* 559 */           g2.setClip(savedClip);
/*     */           continue;
/*     */         } 
/* 562 */         current.draw(g2, this, frame, area);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 568 */     iterator = this.pointers.iterator();
/* 569 */     while (iterator.hasNext()) {
/* 570 */       DialPointer current = (DialPointer)iterator.next();
/* 571 */       if (current.isVisible()) {
/* 572 */         if (current.isClippedToWindow()) {
/* 573 */           Shape savedClip = g2.getClip();
/* 574 */           g2.clip(this.dialFrame.getWindow(frame));
/* 575 */           current.draw(g2, this, frame, area);
/* 576 */           g2.setClip(savedClip);
/*     */           continue;
/*     */         } 
/* 579 */         current.draw(g2, this, frame, area);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 585 */     if (this.cap != null && this.cap.isVisible()) {
/* 586 */       if (this.cap.isClippedToWindow()) {
/* 587 */         Shape savedClip = g2.getClip();
/* 588 */         g2.clip(this.dialFrame.getWindow(frame));
/* 589 */         this.cap.draw(g2, this, frame, area);
/* 590 */         g2.setClip(savedClip);
/*     */       } else {
/*     */         
/* 593 */         this.cap.draw(g2, this, frame, area);
/*     */       } 
/*     */     }
/*     */     
/* 597 */     if (this.dialFrame.isVisible()) {
/* 598 */       this.dialFrame.draw(g2, this, frame, area);
/*     */     }
/*     */     
/* 601 */     g2.setClip(origClip);
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
/*     */   private Rectangle2D viewToFrame(Rectangle2D view) {
/* 613 */     double width = view.getWidth() / this.viewW;
/* 614 */     double height = view.getHeight() / this.viewH;
/* 615 */     double x = view.getX() - width * this.viewX;
/* 616 */     double y = view.getY() - height * this.viewY;
/* 617 */     return new Rectangle2D.Double(x, y, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValue(int datasetIndex) {
/* 628 */     double result = Double.NaN;
/* 629 */     ValueDataset dataset = getDataset(datasetIndex);
/* 630 */     if (dataset != null) {
/* 631 */       Number n = dataset.getValue();
/* 632 */       if (n != null) {
/* 633 */         result = n.doubleValue();
/*     */       }
/*     */     } 
/* 636 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addScale(int index, DialScale scale) {
/* 647 */     ParamChecks.nullNotPermitted(scale, "scale");
/* 648 */     DialScale existing = (DialScale)this.scales.get(index);
/* 649 */     if (existing != null) {
/* 650 */       removeLayer(existing);
/*     */     }
/* 652 */     this.layers.add(scale);
/* 653 */     this.scales.set(index, scale);
/* 654 */     scale.addChangeListener(this);
/* 655 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DialScale getScale(int index) {
/* 666 */     DialScale result = null;
/* 667 */     if (this.scales.size() > index) {
/* 668 */       result = (DialScale)this.scales.get(index);
/*     */     }
/* 670 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mapDatasetToScale(int index, int scaleIndex) {
/* 680 */     this.datasetToScaleMap.set(index, new Integer(scaleIndex));
/* 681 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DialScale getScaleForDataset(int datasetIndex) {
/* 692 */     DialScale result = (DialScale)this.scales.get(0);
/* 693 */     Integer scaleIndex = (Integer)this.datasetToScaleMap.get(datasetIndex);
/* 694 */     if (scaleIndex != null) {
/* 695 */       result = getScale(scaleIndex.intValue());
/*     */     }
/* 697 */     return result;
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
/*     */   public static Rectangle2D rectangleByRadius(Rectangle2D rect, double radiusW, double radiusH) {
/* 711 */     ParamChecks.nullNotPermitted(rect, "rect");
/* 712 */     double x = rect.getCenterX();
/* 713 */     double y = rect.getCenterY();
/* 714 */     double w = rect.getWidth() * radiusW;
/* 715 */     double h = rect.getHeight() * radiusH;
/* 716 */     return new Rectangle2D.Double(x - w / 2.0D, y - h / 2.0D, w, h);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dialLayerChanged(DialLayerChangeEvent event) {
/* 727 */     fireChangeEvent();
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
/*     */   public boolean equals(Object obj) {
/* 741 */     if (obj == this) {
/* 742 */       return true;
/*     */     }
/* 744 */     if (!(obj instanceof DialPlot)) {
/* 745 */       return false;
/*     */     }
/* 747 */     DialPlot that = (DialPlot)obj;
/* 748 */     if (!ObjectUtilities.equal(this.background, that.background)) {
/* 749 */       return false;
/*     */     }
/* 751 */     if (!ObjectUtilities.equal(this.cap, that.cap)) {
/* 752 */       return false;
/*     */     }
/* 754 */     if (!this.dialFrame.equals(that.dialFrame)) {
/* 755 */       return false;
/*     */     }
/* 757 */     if (this.viewX != that.viewX) {
/* 758 */       return false;
/*     */     }
/* 760 */     if (this.viewY != that.viewY) {
/* 761 */       return false;
/*     */     }
/* 763 */     if (this.viewW != that.viewW) {
/* 764 */       return false;
/*     */     }
/* 766 */     if (this.viewH != that.viewH) {
/* 767 */       return false;
/*     */     }
/* 769 */     if (!this.layers.equals(that.layers)) {
/* 770 */       return false;
/*     */     }
/* 772 */     if (!this.pointers.equals(that.pointers)) {
/* 773 */       return false;
/*     */     }
/* 775 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 785 */     int result = 193;
/* 786 */     result = 37 * result + ObjectUtilities.hashCode(this.background);
/* 787 */     result = 37 * result + ObjectUtilities.hashCode(this.cap);
/* 788 */     result = 37 * result + this.dialFrame.hashCode();
/* 789 */     long temp = Double.doubleToLongBits(this.viewX);
/* 790 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 791 */     temp = Double.doubleToLongBits(this.viewY);
/* 792 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 793 */     temp = Double.doubleToLongBits(this.viewW);
/* 794 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 795 */     temp = Double.doubleToLongBits(this.viewH);
/* 796 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 797 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlotType() {
/* 807 */     return "DialPlot";
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
/* 818 */     stream.defaultWriteObject();
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
/* 831 */     stream.defaultReadObject();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/dial/DialPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */