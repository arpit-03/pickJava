/*     */ package org.jfree.chart.title;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.LegendItem;
/*     */ import org.jfree.chart.LegendItemCollection;
/*     */ import org.jfree.chart.LegendItemSource;
/*     */ import org.jfree.chart.block.Arrangement;
/*     */ import org.jfree.chart.block.Block;
/*     */ import org.jfree.chart.block.BlockContainer;
/*     */ import org.jfree.chart.block.BlockFrame;
/*     */ import org.jfree.chart.block.BlockResult;
/*     */ import org.jfree.chart.block.BorderArrangement;
/*     */ import org.jfree.chart.block.CenterArrangement;
/*     */ import org.jfree.chart.block.ColumnArrangement;
/*     */ import org.jfree.chart.block.EntityBlockParams;
/*     */ import org.jfree.chart.block.FlowArrangement;
/*     */ import org.jfree.chart.block.LabelBlock;
/*     */ import org.jfree.chart.block.RectangleConstraint;
/*     */ import org.jfree.chart.entity.ChartEntity;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.entity.StandardEntityCollection;
/*     */ import org.jfree.chart.entity.TitleEntity;
/*     */ import org.jfree.chart.event.TitleChangeEvent;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleAnchor;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.ui.Size2D;
/*     */ import org.jfree.util.PaintUtilities;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ import org.jfree.util.SortOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LegendTitle
/*     */   extends Title
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2644010518533854633L;
/* 118 */   public static final Font DEFAULT_ITEM_FONT = new Font("SansSerif", 0, 12);
/*     */ 
/*     */ 
/*     */   
/* 122 */   public static final Paint DEFAULT_ITEM_PAINT = Color.black;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LegendItemSource[] sources;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint backgroundPaint;
/*     */ 
/*     */ 
/*     */   
/*     */   private RectangleEdge legendItemGraphicEdge;
/*     */ 
/*     */ 
/*     */   
/*     */   private RectangleAnchor legendItemGraphicAnchor;
/*     */ 
/*     */ 
/*     */   
/*     */   private RectangleAnchor legendItemGraphicLocation;
/*     */ 
/*     */ 
/*     */   
/*     */   private RectangleInsets legendItemGraphicPadding;
/*     */ 
/*     */ 
/*     */   
/*     */   private Font itemFont;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint itemPaint;
/*     */ 
/*     */ 
/*     */   
/*     */   private RectangleInsets itemLabelPadding;
/*     */ 
/*     */ 
/*     */   
/*     */   private BlockContainer items;
/*     */ 
/*     */ 
/*     */   
/*     */   private Arrangement hLayout;
/*     */ 
/*     */ 
/*     */   
/*     */   private Arrangement vLayout;
/*     */ 
/*     */ 
/*     */   
/*     */   private BlockContainer wrapper;
/*     */ 
/*     */ 
/*     */   
/*     */   private SortOrder sortOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegendTitle(LegendItemSource source) {
/* 186 */     this(source, (Arrangement)new FlowArrangement(), (Arrangement)new ColumnArrangement());
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
/*     */   public LegendTitle(LegendItemSource source, Arrangement hLayout, Arrangement vLayout) {
/* 200 */     this.sources = new LegendItemSource[] { source };
/* 201 */     this.items = new BlockContainer(hLayout);
/* 202 */     this.hLayout = hLayout;
/* 203 */     this.vLayout = vLayout;
/* 204 */     this.backgroundPaint = null;
/* 205 */     this.legendItemGraphicEdge = RectangleEdge.LEFT;
/* 206 */     this.legendItemGraphicAnchor = RectangleAnchor.CENTER;
/* 207 */     this.legendItemGraphicLocation = RectangleAnchor.CENTER;
/* 208 */     this.legendItemGraphicPadding = new RectangleInsets(2.0D, 2.0D, 2.0D, 2.0D);
/* 209 */     this.itemFont = DEFAULT_ITEM_FONT;
/* 210 */     this.itemPaint = DEFAULT_ITEM_PAINT;
/* 211 */     this.itemLabelPadding = new RectangleInsets(2.0D, 2.0D, 2.0D, 2.0D);
/* 212 */     this.sortOrder = SortOrder.ASCENDING;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegendItemSource[] getSources() {
/* 221 */     return this.sources;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSources(LegendItemSource[] sources) {
/* 231 */     ParamChecks.nullNotPermitted(sources, "sources");
/* 232 */     this.sources = sources;
/* 233 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getBackgroundPaint() {
/* 242 */     return this.backgroundPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgroundPaint(Paint paint) {
/* 252 */     this.backgroundPaint = paint;
/* 253 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleEdge getLegendItemGraphicEdge() {
/* 262 */     return this.legendItemGraphicEdge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLegendItemGraphicEdge(RectangleEdge edge) {
/* 271 */     ParamChecks.nullNotPermitted(edge, "edge");
/* 272 */     this.legendItemGraphicEdge = edge;
/* 273 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleAnchor getLegendItemGraphicAnchor() {
/* 282 */     return this.legendItemGraphicAnchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLegendItemGraphicAnchor(RectangleAnchor anchor) {
/* 291 */     ParamChecks.nullNotPermitted(anchor, "anchor");
/* 292 */     this.legendItemGraphicAnchor = anchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleAnchor getLegendItemGraphicLocation() {
/* 301 */     return this.legendItemGraphicLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLegendItemGraphicLocation(RectangleAnchor anchor) {
/* 310 */     this.legendItemGraphicLocation = anchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleInsets getLegendItemGraphicPadding() {
/* 319 */     return this.legendItemGraphicPadding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLegendItemGraphicPadding(RectangleInsets padding) {
/* 329 */     ParamChecks.nullNotPermitted(padding, "padding");
/* 330 */     this.legendItemGraphicPadding = padding;
/* 331 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getItemFont() {
/* 340 */     return this.itemFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemFont(Font font) {
/* 350 */     ParamChecks.nullNotPermitted(font, "font");
/* 351 */     this.itemFont = font;
/* 352 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getItemPaint() {
/* 361 */     return this.itemPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemPaint(Paint paint) {
/* 370 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 371 */     this.itemPaint = paint;
/* 372 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleInsets getItemLabelPadding() {
/* 381 */     return this.itemLabelPadding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemLabelPadding(RectangleInsets padding) {
/* 390 */     ParamChecks.nullNotPermitted(padding, "padding");
/* 391 */     this.itemLabelPadding = padding;
/* 392 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SortOrder getSortOrder() {
/* 402 */     return this.sortOrder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSortOrder(SortOrder order) {
/* 413 */     ParamChecks.nullNotPermitted(order, "order");
/* 414 */     this.sortOrder = order;
/* 415 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fetchLegendItems() {
/* 422 */     this.items.clear();
/* 423 */     RectangleEdge p = getPosition();
/* 424 */     if (RectangleEdge.isTopOrBottom(p)) {
/* 425 */       this.items.setArrangement(this.hLayout);
/*     */     } else {
/*     */       
/* 428 */       this.items.setArrangement(this.vLayout);
/*     */     } 
/*     */     
/* 431 */     if (this.sortOrder.equals(SortOrder.ASCENDING)) {
/* 432 */       for (int s = 0; s < this.sources.length; s++) {
/*     */         
/* 434 */         LegendItemCollection legendItems = this.sources[s].getLegendItems();
/* 435 */         if (legendItems != null) {
/* 436 */           for (int i = 0; i < legendItems.getItemCount(); i++) {
/* 437 */             addItemBlock(legendItems.get(i));
/*     */           }
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 443 */       for (int s = this.sources.length - 1; s >= 0; s--) {
/*     */         
/* 445 */         LegendItemCollection legendItems = this.sources[s].getLegendItems();
/* 446 */         if (legendItems != null) {
/* 447 */           for (int i = legendItems.getItemCount() - 1; i >= 0; i--) {
/* 448 */             addItemBlock(legendItems.get(i));
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addItemBlock(LegendItem item) {
/* 456 */     Block block = createLegendItemBlock(item);
/* 457 */     this.items.add(block);
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
/*     */   protected Block createLegendItemBlock(LegendItem item) {
/* 470 */     LegendGraphic lg = new LegendGraphic(item.getShape(), item.getFillPaint());
/* 471 */     lg.setFillPaintTransformer(item.getFillPaintTransformer());
/* 472 */     lg.setShapeFilled(item.isShapeFilled());
/* 473 */     lg.setLine(item.getLine());
/* 474 */     lg.setLineStroke(item.getLineStroke());
/* 475 */     lg.setLinePaint(item.getLinePaint());
/* 476 */     lg.setLineVisible(item.isLineVisible());
/* 477 */     lg.setShapeVisible(item.isShapeVisible());
/* 478 */     lg.setShapeOutlineVisible(item.isShapeOutlineVisible());
/* 479 */     lg.setOutlinePaint(item.getOutlinePaint());
/* 480 */     lg.setOutlineStroke(item.getOutlineStroke());
/* 481 */     lg.setPadding(this.legendItemGraphicPadding);
/*     */ 
/*     */ 
/*     */     
/* 485 */     LegendItemBlockContainer legendItem = new LegendItemBlockContainer((Arrangement)new BorderArrangement(), item.getDataset(), item.getSeriesKey());
/* 486 */     lg.setShapeAnchor(getLegendItemGraphicAnchor());
/* 487 */     lg.setShapeLocation(getLegendItemGraphicLocation());
/* 488 */     legendItem.add(lg, this.legendItemGraphicEdge);
/* 489 */     Font textFont = item.getLabelFont();
/* 490 */     if (textFont == null) {
/* 491 */       textFont = this.itemFont;
/*     */     }
/* 493 */     Paint textPaint = item.getLabelPaint();
/* 494 */     if (textPaint == null) {
/* 495 */       textPaint = this.itemPaint;
/*     */     }
/* 497 */     LabelBlock labelBlock = new LabelBlock(item.getLabel(), textFont, textPaint);
/*     */     
/* 499 */     labelBlock.setPadding(this.itemLabelPadding);
/* 500 */     legendItem.add((Block)labelBlock);
/* 501 */     legendItem.setToolTipText(item.getToolTipText());
/* 502 */     legendItem.setURLText(item.getURLText());
/*     */     
/* 504 */     BlockContainer result = new BlockContainer((Arrangement)new CenterArrangement());
/* 505 */     result.add((Block)legendItem);
/*     */     
/* 507 */     return (Block)result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockContainer getItemContainer() {
/* 516 */     return this.items;
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
/*     */   public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
/* 530 */     Size2D result = new Size2D();
/* 531 */     fetchLegendItems();
/* 532 */     if (this.items.isEmpty()) {
/* 533 */       return result;
/*     */     }
/* 535 */     BlockContainer container = this.wrapper;
/* 536 */     if (container == null) {
/* 537 */       container = this.items;
/*     */     }
/* 539 */     RectangleConstraint c = toContentConstraint(constraint);
/* 540 */     Size2D size = container.arrange(g2, c);
/* 541 */     result.height = calculateTotalHeight(size.height);
/* 542 */     result.width = calculateTotalWidth(size.width);
/* 543 */     return result;
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
/*     */   public void draw(Graphics2D g2, Rectangle2D area) {
/* 555 */     draw(g2, area, (Object)null);
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
/*     */   public Object draw(Graphics2D g2, Rectangle2D area, Object params) {
/* 570 */     Rectangle2D target = (Rectangle2D)area.clone();
/* 571 */     Rectangle2D hotspot = (Rectangle2D)area.clone();
/* 572 */     StandardEntityCollection sec = null;
/* 573 */     if (params instanceof EntityBlockParams && ((EntityBlockParams)params)
/* 574 */       .getGenerateEntities()) {
/* 575 */       sec = new StandardEntityCollection();
/* 576 */       sec.add((ChartEntity)new TitleEntity(hotspot, this));
/*     */     } 
/* 578 */     target = trimMargin(target);
/* 579 */     if (this.backgroundPaint != null) {
/* 580 */       g2.setPaint(this.backgroundPaint);
/* 581 */       g2.fill(target);
/*     */     } 
/* 583 */     BlockFrame border = getFrame();
/* 584 */     border.draw(g2, target);
/* 585 */     border.getInsets().trim(target);
/* 586 */     BlockContainer container = this.wrapper;
/* 587 */     if (container == null) {
/* 588 */       container = this.items;
/*     */     }
/* 590 */     target = trimPadding(target);
/* 591 */     Object val = container.draw(g2, target, params);
/* 592 */     if (val instanceof BlockResult) {
/* 593 */       EntityCollection ec = ((BlockResult)val).getEntityCollection();
/* 594 */       if (ec != null && sec != null) {
/* 595 */         sec.addAll(ec);
/* 596 */         ((BlockResult)val).setEntityCollection((EntityCollection)sec);
/*     */       } 
/*     */     } 
/* 599 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockContainer getWrapper() {
/* 610 */     return this.wrapper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWrapper(BlockContainer wrapper) {
/* 619 */     this.wrapper = wrapper;
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
/* 631 */     if (obj == this) {
/* 632 */       return true;
/*     */     }
/* 634 */     if (!(obj instanceof LegendTitle)) {
/* 635 */       return false;
/*     */     }
/* 637 */     if (!super.equals(obj)) {
/* 638 */       return false;
/*     */     }
/* 640 */     LegendTitle that = (LegendTitle)obj;
/* 641 */     if (!PaintUtilities.equal(this.backgroundPaint, that.backgroundPaint)) {
/* 642 */       return false;
/*     */     }
/* 644 */     if (this.legendItemGraphicEdge != that.legendItemGraphicEdge) {
/* 645 */       return false;
/*     */     }
/* 647 */     if (this.legendItemGraphicAnchor != that.legendItemGraphicAnchor) {
/* 648 */       return false;
/*     */     }
/* 650 */     if (this.legendItemGraphicLocation != that.legendItemGraphicLocation) {
/* 651 */       return false;
/*     */     }
/* 653 */     if (!this.itemFont.equals(that.itemFont)) {
/* 654 */       return false;
/*     */     }
/* 656 */     if (!this.itemPaint.equals(that.itemPaint)) {
/* 657 */       return false;
/*     */     }
/* 659 */     if (!this.hLayout.equals(that.hLayout)) {
/* 660 */       return false;
/*     */     }
/* 662 */     if (!this.vLayout.equals(that.vLayout)) {
/* 663 */       return false;
/*     */     }
/* 665 */     if (!this.sortOrder.equals(that.sortOrder)) {
/* 666 */       return false;
/*     */     }
/* 668 */     return true;
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
/* 679 */     stream.defaultWriteObject();
/* 680 */     SerialUtilities.writePaint(this.backgroundPaint, stream);
/* 681 */     SerialUtilities.writePaint(this.itemPaint, stream);
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
/* 694 */     stream.defaultReadObject();
/* 695 */     this.backgroundPaint = SerialUtilities.readPaint(stream);
/* 696 */     this.itemPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/title/LegendTitle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */