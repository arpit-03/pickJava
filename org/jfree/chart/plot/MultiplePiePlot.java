/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jfree.chart.ChartRenderingInfo;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.LegendItem;
/*     */ import org.jfree.chart.LegendItemCollection;
/*     */ import org.jfree.chart.title.TextTitle;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.CategoryToPieDataset;
/*     */ import org.jfree.data.general.Dataset;
/*     */ import org.jfree.data.general.DatasetChangeEvent;
/*     */ import org.jfree.data.general.DatasetUtilities;
/*     */ import org.jfree.data.general.PieDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PaintUtilities;
/*     */ import org.jfree.util.ShapeUtilities;
/*     */ import org.jfree.util.TableOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiplePiePlot
/*     */   extends Plot
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -355377800470807389L;
/*     */   private JFreeChart pieChart;
/*     */   private CategoryDataset dataset;
/*     */   private TableOrder dataExtractOrder;
/* 118 */   private double limit = 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Comparable aggregatedItemsKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint aggregatedItemsPaint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Map sectionPaints;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Shape legendItemShape;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiplePiePlot() {
/* 152 */     this((CategoryDataset)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultiplePiePlot(CategoryDataset dataset) {
/* 162 */     setDataset(dataset);
/* 163 */     PiePlot piePlot = new PiePlot(null);
/* 164 */     piePlot.setIgnoreNullValues(true);
/* 165 */     this.pieChart = new JFreeChart(piePlot);
/* 166 */     this.pieChart.removeLegend();
/* 167 */     this.dataExtractOrder = TableOrder.BY_COLUMN;
/* 168 */     this.pieChart.setBackgroundPaint(null);
/* 169 */     TextTitle seriesTitle = new TextTitle("Series Title", new Font("SansSerif", 1, 12));
/*     */     
/* 171 */     seriesTitle.setPosition(RectangleEdge.BOTTOM);
/* 172 */     this.pieChart.setTitle(seriesTitle);
/* 173 */     this.aggregatedItemsKey = "Other";
/* 174 */     this.aggregatedItemsPaint = Color.lightGray;
/* 175 */     this.sectionPaints = new HashMap<Object, Object>();
/* 176 */     this.legendItemShape = new Ellipse2D.Double(-4.0D, -4.0D, 8.0D, 8.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CategoryDataset getDataset() {
/* 185 */     return this.dataset;
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
/*     */   public void setDataset(CategoryDataset dataset) {
/* 197 */     if (this.dataset != null) {
/* 198 */       this.dataset.removeChangeListener(this);
/*     */     }
/*     */ 
/*     */     
/* 202 */     this.dataset = dataset;
/* 203 */     if (dataset != null) {
/* 204 */       setDatasetGroup(dataset.getGroup());
/* 205 */       dataset.addChangeListener(this);
/*     */     } 
/*     */ 
/*     */     
/* 209 */     datasetChanged(new DatasetChangeEvent(this, (Dataset)dataset));
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
/*     */   public JFreeChart getPieChart() {
/* 222 */     return this.pieChart;
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
/*     */   public void setPieChart(JFreeChart pieChart) {
/* 234 */     ParamChecks.nullNotPermitted(pieChart, "pieChart");
/* 235 */     if (!(pieChart.getPlot() instanceof PiePlot)) {
/* 236 */       throw new IllegalArgumentException("The 'pieChart' argument must be a chart based on a PiePlot.");
/*     */     }
/*     */     
/* 239 */     this.pieChart = pieChart;
/* 240 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableOrder getDataExtractOrder() {
/* 249 */     return this.dataExtractOrder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDataExtractOrder(TableOrder order) {
/* 259 */     ParamChecks.nullNotPermitted(order, "order");
/* 260 */     this.dataExtractOrder = order;
/* 261 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLimit() {
/* 271 */     return this.limit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLimit(double limit) {
/* 281 */     this.limit = limit;
/* 282 */     fireChangeEvent();
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
/*     */   public Comparable getAggregatedItemsKey() {
/* 294 */     return this.aggregatedItemsKey;
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
/*     */   public void setAggregatedItemsKey(Comparable key) {
/* 306 */     ParamChecks.nullNotPermitted(key, "key");
/* 307 */     this.aggregatedItemsKey = key;
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
/*     */   
/*     */   public Paint getAggregatedItemsPaint() {
/* 320 */     return this.aggregatedItemsPaint;
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
/*     */   public void setAggregatedItemsPaint(Paint paint) {
/* 332 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 333 */     this.aggregatedItemsPaint = paint;
/* 334 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlotType() {
/* 344 */     return "Multiple Pie Plot";
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
/*     */   public Shape getLegendItemShape() {
/* 358 */     return this.legendItemShape;
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
/*     */   public void setLegendItemShape(Shape shape) {
/* 372 */     ParamChecks.nullNotPermitted(shape, "shape");
/* 373 */     this.legendItemShape = shape;
/* 374 */     fireChangeEvent();
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
/*     */   public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info) {
/*     */     int pieCount;
/* 392 */     RectangleInsets insets = getInsets();
/* 393 */     insets.trim(area);
/* 394 */     drawBackground(g2, area);
/* 395 */     drawOutline(g2, area);
/*     */ 
/*     */     
/* 398 */     if (DatasetUtilities.isEmptyOrNull(this.dataset)) {
/* 399 */       drawNoDataMessage(g2, area);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 404 */     if (this.dataExtractOrder == TableOrder.BY_ROW) {
/* 405 */       pieCount = this.dataset.getRowCount();
/*     */     } else {
/*     */       
/* 408 */       pieCount = this.dataset.getColumnCount();
/*     */     } 
/*     */ 
/*     */     
/* 412 */     int displayCols = (int)Math.ceil(Math.sqrt(pieCount));
/*     */     
/* 414 */     int displayRows = (int)Math.ceil(pieCount / displayCols);
/*     */ 
/*     */     
/* 417 */     if (displayCols > displayRows && area.getWidth() < area.getHeight()) {
/* 418 */       int temp = displayCols;
/* 419 */       displayCols = displayRows;
/* 420 */       displayRows = temp;
/*     */     } 
/*     */     
/* 423 */     prefetchSectionPaints();
/*     */     
/* 425 */     int x = (int)area.getX();
/* 426 */     int y = (int)area.getY();
/* 427 */     int width = (int)area.getWidth() / displayCols;
/* 428 */     int height = (int)area.getHeight() / displayRows;
/* 429 */     int row = 0;
/* 430 */     int column = 0;
/* 431 */     int diff = displayRows * displayCols - pieCount;
/* 432 */     int xoffset = 0;
/* 433 */     Rectangle rect = new Rectangle();
/*     */     
/* 435 */     for (int pieIndex = 0; pieIndex < pieCount; pieIndex++) {
/* 436 */       String title; CategoryToPieDataset categoryToPieDataset1; rect.setBounds(x + xoffset + width * column, y + height * row, width, height);
/*     */ 
/*     */ 
/*     */       
/* 440 */       if (this.dataExtractOrder == TableOrder.BY_ROW) {
/* 441 */         title = this.dataset.getRowKey(pieIndex).toString();
/*     */       } else {
/*     */         
/* 444 */         title = this.dataset.getColumnKey(pieIndex).toString();
/*     */       } 
/* 446 */       this.pieChart.setTitle(title);
/*     */ 
/*     */       
/* 449 */       CategoryToPieDataset categoryToPieDataset2 = new CategoryToPieDataset(this.dataset, this.dataExtractOrder, pieIndex);
/*     */       
/* 451 */       if (this.limit > 0.0D) {
/* 452 */         PieDataset piedataset = DatasetUtilities.createConsolidatedPieDataset((PieDataset)categoryToPieDataset2, this.aggregatedItemsKey, this.limit);
/*     */       }
/*     */       else {
/*     */         
/* 456 */         categoryToPieDataset1 = categoryToPieDataset2;
/*     */       } 
/* 458 */       PiePlot piePlot = (PiePlot)this.pieChart.getPlot();
/* 459 */       piePlot.setDataset((PieDataset)categoryToPieDataset1);
/* 460 */       piePlot.setPieIndex(pieIndex);
/*     */ 
/*     */       
/* 463 */       for (int i = 0; i < categoryToPieDataset1.getItemCount(); i++) {
/* 464 */         Paint p; Comparable key = categoryToPieDataset1.getKey(i);
/*     */         
/* 466 */         if (key.equals(this.aggregatedItemsKey)) {
/* 467 */           p = this.aggregatedItemsPaint;
/*     */         } else {
/*     */           
/* 470 */           p = (Paint)this.sectionPaints.get(key);
/*     */         } 
/* 472 */         piePlot.setSectionPaint(key, p);
/*     */       } 
/*     */       
/* 475 */       ChartRenderingInfo subinfo = null;
/* 476 */       if (info != null) {
/* 477 */         subinfo = new ChartRenderingInfo();
/*     */       }
/* 479 */       this.pieChart.draw(g2, rect, subinfo);
/* 480 */       if (info != null) {
/* 481 */         assert subinfo != null;
/* 482 */         info.getOwner().getEntityCollection().addAll(subinfo
/* 483 */             .getEntityCollection());
/* 484 */         info.addSubplotInfo(subinfo.getPlotInfo());
/*     */       } 
/*     */       
/* 487 */       column++;
/* 488 */       if (column == displayCols) {
/* 489 */         column = 0;
/* 490 */         row++;
/*     */         
/* 492 */         if (row == displayRows - 1 && diff != 0) {
/* 493 */           xoffset = diff * width / 2;
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
/*     */   private void prefetchSectionPaints() {
/* 512 */     PiePlot piePlot = (PiePlot)getPieChart().getPlot();
/*     */     
/* 514 */     if (this.dataExtractOrder == TableOrder.BY_ROW) {
/*     */       
/* 516 */       for (int c = 0; c < this.dataset.getColumnCount(); c++) {
/* 517 */         Comparable key = this.dataset.getColumnKey(c);
/* 518 */         Paint p = piePlot.getSectionPaint(key);
/* 519 */         if (p == null) {
/* 520 */           p = (Paint)this.sectionPaints.get(key);
/* 521 */           if (p == null) {
/* 522 */             p = getDrawingSupplier().getNextPaint();
/*     */           }
/*     */         } 
/* 525 */         this.sectionPaints.put(key, p);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 530 */       for (int r = 0; r < this.dataset.getRowCount(); r++) {
/* 531 */         Comparable key = this.dataset.getRowKey(r);
/* 532 */         Paint p = piePlot.getSectionPaint(key);
/* 533 */         if (p == null) {
/* 534 */           p = (Paint)this.sectionPaints.get(key);
/* 535 */           if (p == null) {
/* 536 */             p = getDrawingSupplier().getNextPaint();
/*     */           }
/*     */         } 
/* 539 */         this.sectionPaints.put(key, p);
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
/*     */   public LegendItemCollection getLegendItems() {
/* 553 */     LegendItemCollection result = new LegendItemCollection();
/* 554 */     if (this.dataset == null) {
/* 555 */       return result;
/*     */     }
/*     */     
/* 558 */     List keys = null;
/* 559 */     prefetchSectionPaints();
/* 560 */     if (this.dataExtractOrder == TableOrder.BY_ROW) {
/* 561 */       keys = this.dataset.getColumnKeys();
/*     */     }
/* 563 */     else if (this.dataExtractOrder == TableOrder.BY_COLUMN) {
/* 564 */       keys = this.dataset.getRowKeys();
/*     */     } 
/* 566 */     if (keys == null) {
/* 567 */       return result;
/*     */     }
/* 569 */     int section = 0;
/* 570 */     Iterator<Comparable> iterator = keys.iterator();
/* 571 */     while (iterator.hasNext()) {
/* 572 */       Comparable key = iterator.next();
/* 573 */       String label = key.toString();
/* 574 */       String description = label;
/* 575 */       Paint paint = (Paint)this.sectionPaints.get(key);
/*     */       
/* 577 */       LegendItem item = new LegendItem(label, description, null, null, getLegendItemShape(), paint, Plot.DEFAULT_OUTLINE_STROKE, paint);
/*     */       
/* 579 */       item.setSeriesKey(key);
/* 580 */       item.setSeriesIndex(section);
/* 581 */       item.setDataset((Dataset)getDataset());
/* 582 */       result.add(item);
/* 583 */       section++;
/*     */     } 
/* 585 */     if (this.limit > 0.0D) {
/*     */ 
/*     */       
/* 588 */       LegendItem a = new LegendItem(this.aggregatedItemsKey.toString(), this.aggregatedItemsKey.toString(), null, null, getLegendItemShape(), this.aggregatedItemsPaint, Plot.DEFAULT_OUTLINE_STROKE, this.aggregatedItemsPaint);
/*     */       
/* 590 */       result.add(a);
/*     */     } 
/* 592 */     return result;
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
/* 606 */     if (obj == this) {
/* 607 */       return true;
/*     */     }
/* 609 */     if (!(obj instanceof MultiplePiePlot)) {
/* 610 */       return false;
/*     */     }
/* 612 */     MultiplePiePlot that = (MultiplePiePlot)obj;
/* 613 */     if (this.dataExtractOrder != that.dataExtractOrder) {
/* 614 */       return false;
/*     */     }
/* 616 */     if (this.limit != that.limit) {
/* 617 */       return false;
/*     */     }
/* 619 */     if (!this.aggregatedItemsKey.equals(that.aggregatedItemsKey)) {
/* 620 */       return false;
/*     */     }
/* 622 */     if (!PaintUtilities.equal(this.aggregatedItemsPaint, that.aggregatedItemsPaint))
/*     */     {
/* 624 */       return false;
/*     */     }
/* 626 */     if (!ObjectUtilities.equal(this.pieChart, that.pieChart)) {
/* 627 */       return false;
/*     */     }
/* 629 */     if (!ShapeUtilities.equal(this.legendItemShape, that.legendItemShape)) {
/* 630 */       return false;
/*     */     }
/* 632 */     if (!super.equals(obj)) {
/* 633 */       return false;
/*     */     }
/* 635 */     return true;
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
/* 648 */     MultiplePiePlot clone = (MultiplePiePlot)super.clone();
/* 649 */     clone.pieChart = (JFreeChart)this.pieChart.clone();
/* 650 */     clone.sectionPaints = new HashMap<Object, Object>(this.sectionPaints);
/* 651 */     clone.legendItemShape = ShapeUtilities.clone(this.legendItemShape);
/* 652 */     return clone;
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
/* 663 */     stream.defaultWriteObject();
/* 664 */     SerialUtilities.writePaint(this.aggregatedItemsPaint, stream);
/* 665 */     SerialUtilities.writeShape(this.legendItemShape, stream);
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
/* 679 */     this.aggregatedItemsPaint = SerialUtilities.readPaint(stream);
/* 680 */     this.legendItemShape = SerialUtilities.readShape(stream);
/* 681 */     this.sectionPaints = new HashMap<Object, Object>();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/MultiplePiePlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */