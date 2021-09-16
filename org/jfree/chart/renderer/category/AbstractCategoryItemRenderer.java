/*      */ package org.jfree.chart.renderer.category;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Font;
/*      */ import java.awt.GradientPaint;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Ellipse2D;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.jfree.chart.LegendItem;
/*      */ import org.jfree.chart.LegendItemCollection;
/*      */ import org.jfree.chart.axis.CategoryAxis;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.entity.CategoryItemEntity;
/*      */ import org.jfree.chart.entity.ChartEntity;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.labels.CategoryItemLabelGenerator;
/*      */ import org.jfree.chart.labels.CategorySeriesLabelGenerator;
/*      */ import org.jfree.chart.labels.CategoryToolTipGenerator;
/*      */ import org.jfree.chart.labels.ItemLabelPosition;
/*      */ import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
/*      */ import org.jfree.chart.plot.CategoryCrosshairState;
/*      */ import org.jfree.chart.plot.CategoryMarker;
/*      */ import org.jfree.chart.plot.CategoryPlot;
/*      */ import org.jfree.chart.plot.DrawingSupplier;
/*      */ import org.jfree.chart.plot.IntervalMarker;
/*      */ import org.jfree.chart.plot.Marker;
/*      */ import org.jfree.chart.plot.PlotOrientation;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.plot.ValueMarker;
/*      */ import org.jfree.chart.renderer.AbstractRenderer;
/*      */ import org.jfree.chart.urls.CategoryURLGenerator;
/*      */ import org.jfree.chart.util.CloneUtils;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.chart.util.TextUtils;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.category.CategoryDataset;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.general.DatasetUtilities;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.ui.GradientPaintTransformer;
/*      */ import org.jfree.ui.LengthAdjustmentType;
/*      */ import org.jfree.ui.RectangleAnchor;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ import org.jfree.util.ObjectUtilities;
/*      */ import org.jfree.util.PublicCloneable;
/*      */ import org.jfree.util.SortOrder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class AbstractCategoryItemRenderer
/*      */   extends AbstractRenderer
/*      */   implements CategoryItemRenderer, Cloneable, PublicCloneable, Serializable
/*      */ {
/*  229 */   private CategoryItemLabelGenerator itemLabelGenerator = null;
/*  230 */   private Map<Integer, CategoryItemLabelGenerator> itemLabelGeneratorMap = new HashMap<Integer, CategoryItemLabelGenerator>();
/*      */   
/*  232 */   private CategoryToolTipGenerator toolTipGenerator = null;
/*  233 */   private Map<Integer, CategoryToolTipGenerator> toolTipGeneratorMap = new HashMap<Integer, CategoryToolTipGenerator>();
/*      */   
/*  235 */   private CategoryURLGenerator itemURLGenerator = null;
/*  236 */   private Map<Integer, CategoryURLGenerator> itemURLGeneratorMap = new HashMap<Integer, CategoryURLGenerator>();
/*  237 */   private CategorySeriesLabelGenerator legendItemLabelGenerator = (CategorySeriesLabelGenerator)new StandardCategorySeriesLabelGenerator();
/*      */   
/*      */   private static final long serialVersionUID = 1247553218442497391L;
/*      */   private CategoryPlot plot;
/*      */   private CategoryItemLabelGenerator baseItemLabelGenerator;
/*      */   private CategoryToolTipGenerator baseToolTipGenerator;
/*      */   private CategoryURLGenerator baseItemURLGenerator;
/*      */   private CategorySeriesLabelGenerator legendItemToolTipGenerator;
/*      */   private CategorySeriesLabelGenerator legendItemURLGenerator;
/*      */   private transient int rowCount;
/*      */   private transient int columnCount;
/*      */   
/*      */   public int getPassCount() {
/*  250 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryPlot getPlot() {
/*  264 */     return this.plot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlot(CategoryPlot plot) {
/*  278 */     ParamChecks.nullNotPermitted(plot, "plot");
/*  279 */     this.plot = plot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryItemLabelGenerator getItemLabelGenerator(int row, int column) {
/*  298 */     return getSeriesItemLabelGenerator(row);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryItemLabelGenerator getSeriesItemLabelGenerator(int series) {
/*  314 */     if (this.itemLabelGenerator != null) {
/*  315 */       return this.itemLabelGenerator;
/*      */     }
/*      */ 
/*      */     
/*  319 */     CategoryItemLabelGenerator generator = this.itemLabelGeneratorMap.get(
/*  320 */         Integer.valueOf(series));
/*  321 */     if (generator == null) {
/*  322 */       generator = this.baseItemLabelGenerator;
/*      */     }
/*  324 */     return generator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeriesItemLabelGenerator(int series, CategoryItemLabelGenerator generator) {
/*  339 */     this.itemLabelGeneratorMap.put(Integer.valueOf(series), generator);
/*  340 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryItemLabelGenerator getBaseItemLabelGenerator() {
/*  352 */     return this.baseItemLabelGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseItemLabelGenerator(CategoryItemLabelGenerator generator) {
/*  366 */     this.baseItemLabelGenerator = generator;
/*  367 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryToolTipGenerator getToolTipGenerator(int row, int column) {
/*      */     CategoryToolTipGenerator result;
/*  388 */     if (this.toolTipGenerator != null) {
/*  389 */       result = this.toolTipGenerator;
/*      */     } else {
/*      */       
/*  392 */       result = getSeriesToolTipGenerator(row);
/*  393 */       if (result == null) {
/*  394 */         result = this.baseToolTipGenerator;
/*      */       }
/*      */     } 
/*  397 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryToolTipGenerator getSeriesToolTipGenerator(int series) {
/*  412 */     return this.toolTipGeneratorMap.get(Integer.valueOf(series));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeriesToolTipGenerator(int series, CategoryToolTipGenerator generator) {
/*  427 */     this.toolTipGeneratorMap.put(Integer.valueOf(series), generator);
/*  428 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryToolTipGenerator getBaseToolTipGenerator() {
/*  440 */     return this.baseToolTipGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseToolTipGenerator(CategoryToolTipGenerator generator) {
/*  453 */     this.baseToolTipGenerator = generator;
/*  454 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryURLGenerator getItemURLGenerator(int row, int column) {
/*  471 */     return getSeriesItemURLGenerator(row);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryURLGenerator getSeriesItemURLGenerator(int series) {
/*  486 */     if (this.itemURLGenerator != null) {
/*  487 */       return this.itemURLGenerator;
/*      */     }
/*      */     
/*  490 */     CategoryURLGenerator generator = this.itemURLGeneratorMap.get(Integer.valueOf(series));
/*  491 */     if (generator == null) {
/*  492 */       generator = this.baseItemURLGenerator;
/*      */     }
/*  494 */     return generator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeriesItemURLGenerator(int series, CategoryURLGenerator generator) {
/*  509 */     this.itemURLGeneratorMap.put(Integer.valueOf(series), generator);
/*  510 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryURLGenerator getBaseItemURLGenerator() {
/*  522 */     return this.baseItemURLGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaseItemURLGenerator(CategoryURLGenerator generator) {
/*  535 */     this.baseItemURLGenerator = generator;
/*  536 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowCount() {
/*  546 */     return this.rowCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumnCount() {
/*  556 */     return this.columnCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CategoryItemRendererState createState(PlotRenderingInfo info) {
/*  572 */     return new CategoryItemRendererState(info);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, CategoryPlot plot, int rendererIndex, PlotRenderingInfo info) {
/*  595 */     setPlot(plot);
/*  596 */     CategoryDataset data = plot.getDataset(rendererIndex);
/*  597 */     if (data != null) {
/*  598 */       this.rowCount = data.getRowCount();
/*  599 */       this.columnCount = data.getColumnCount();
/*      */     } else {
/*      */       
/*  602 */       this.rowCount = 0;
/*  603 */       this.columnCount = 0;
/*      */     } 
/*  605 */     CategoryItemRendererState state = createState(info);
/*  606 */     int[] visibleSeriesTemp = new int[this.rowCount];
/*  607 */     int visibleSeriesCount = 0;
/*  608 */     for (int row = 0; row < this.rowCount; row++) {
/*  609 */       if (isSeriesVisible(row)) {
/*  610 */         visibleSeriesTemp[visibleSeriesCount] = row;
/*  611 */         visibleSeriesCount++;
/*      */       } 
/*      */     } 
/*  614 */     int[] visibleSeries = new int[visibleSeriesCount];
/*  615 */     System.arraycopy(visibleSeriesTemp, 0, visibleSeries, 0, visibleSeriesCount);
/*      */     
/*  617 */     state.setVisibleSeriesArray(visibleSeries);
/*  618 */     return state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Range findRangeBounds(CategoryDataset dataset) {
/*  632 */     return findRangeBounds(dataset, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Range findRangeBounds(CategoryDataset dataset, boolean includeInterval) {
/*  649 */     if (dataset == null) {
/*  650 */       return null;
/*      */     }
/*  652 */     if (getDataBoundsIncludesVisibleSeriesOnly()) {
/*  653 */       List<Comparable> visibleSeriesKeys = new ArrayList();
/*  654 */       int seriesCount = dataset.getRowCount();
/*  655 */       for (int s = 0; s < seriesCount; s++) {
/*  656 */         if (isSeriesVisible(s)) {
/*  657 */           visibleSeriesKeys.add(dataset.getRowKey(s));
/*      */         }
/*      */       } 
/*  660 */       return DatasetUtilities.findRangeBounds(dataset, visibleSeriesKeys, includeInterval);
/*      */     } 
/*      */ 
/*      */     
/*  664 */     return DatasetUtilities.findRangeBounds(dataset, includeInterval);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getItemMiddle(Comparable rowKey, Comparable columnKey, CategoryDataset dataset, CategoryAxis axis, Rectangle2D area, RectangleEdge edge) {
/*  686 */     return axis.getCategoryMiddle(columnKey, dataset.getColumnKeys(), area, edge);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawBackground(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea) {
/*  702 */     plot.drawBackground(g2, dataArea);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawOutline(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea) {
/*  717 */     plot.drawOutline(g2, dataArea);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawDomainGridline(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea, double value) {
/*  740 */     Line2D line = null;
/*  741 */     PlotOrientation orientation = plot.getOrientation();
/*      */     
/*  743 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*      */       
/*  745 */       line = new Line2D.Double(dataArea.getMinX(), value, dataArea.getMaxX(), value);
/*      */     }
/*  747 */     else if (orientation == PlotOrientation.VERTICAL) {
/*      */       
/*  749 */       line = new Line2D.Double(value, dataArea.getMinY(), value, dataArea.getMaxY());
/*      */     } 
/*      */     
/*  752 */     Paint paint = plot.getDomainGridlinePaint();
/*  753 */     if (paint == null) {
/*  754 */       paint = CategoryPlot.DEFAULT_GRIDLINE_PAINT;
/*      */     }
/*  756 */     g2.setPaint(paint);
/*      */     
/*  758 */     Stroke stroke = plot.getDomainGridlineStroke();
/*  759 */     if (stroke == null) {
/*  760 */       stroke = CategoryPlot.DEFAULT_GRIDLINE_STROKE;
/*      */     }
/*  762 */     g2.setStroke(stroke);
/*      */     
/*  764 */     g2.draw(line);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawRangeGridline(Graphics2D g2, CategoryPlot plot, ValueAxis axis, Rectangle2D dataArea, double value) {
/*  783 */     Range range = axis.getRange();
/*  784 */     if (!range.contains(value)) {
/*      */       return;
/*      */     }
/*      */     
/*  788 */     PlotOrientation orientation = plot.getOrientation();
/*  789 */     double v = axis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());
/*  790 */     Line2D line = null;
/*  791 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*      */       
/*  793 */       line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
/*      */     }
/*  795 */     else if (orientation == PlotOrientation.VERTICAL) {
/*      */       
/*  797 */       line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
/*      */     } 
/*      */     
/*  800 */     Paint paint = plot.getRangeGridlinePaint();
/*  801 */     if (paint == null) {
/*  802 */       paint = CategoryPlot.DEFAULT_GRIDLINE_PAINT;
/*      */     }
/*  804 */     g2.setPaint(paint);
/*      */     
/*  806 */     Stroke stroke = plot.getRangeGridlineStroke();
/*  807 */     if (stroke == null) {
/*  808 */       stroke = CategoryPlot.DEFAULT_GRIDLINE_STROKE;
/*      */     }
/*  810 */     g2.setStroke(stroke);
/*      */     
/*  812 */     g2.draw(line);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawRangeLine(Graphics2D g2, CategoryPlot plot, ValueAxis axis, Rectangle2D dataArea, double value, Paint paint, Stroke stroke) {
/*  837 */     Range range = axis.getRange();
/*  838 */     if (!range.contains(value)) {
/*      */       return;
/*      */     }
/*      */     
/*  842 */     PlotOrientation orientation = plot.getOrientation();
/*  843 */     Line2D line = null;
/*  844 */     double v = axis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());
/*  845 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*      */       
/*  847 */       line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
/*  848 */     } else if (orientation == PlotOrientation.VERTICAL) {
/*      */       
/*  850 */       line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
/*      */     } 
/*      */     
/*  853 */     g2.setPaint(paint);
/*  854 */     g2.setStroke(stroke);
/*  855 */     Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
/*  856 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/*      */     
/*  858 */     g2.draw(line);
/*  859 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawDomainMarker(Graphics2D g2, CategoryPlot plot, CategoryAxis axis, CategoryMarker marker, Rectangle2D dataArea) {
/*      */     Rectangle2D bounds;
/*  878 */     Comparable category = marker.getKey();
/*  879 */     CategoryDataset dataset = plot.getDataset(plot.getIndexOf(this));
/*  880 */     int columnIndex = dataset.getColumnIndex(category);
/*  881 */     if (columnIndex < 0) {
/*      */       return;
/*      */     }
/*      */     
/*  885 */     Composite savedComposite = g2.getComposite();
/*  886 */     g2.setComposite(AlphaComposite.getInstance(3, marker
/*  887 */           .getAlpha()));
/*      */     
/*  889 */     PlotOrientation orientation = plot.getOrientation();
/*      */     
/*  891 */     if (marker.getDrawAsLine()) {
/*  892 */       double v = axis.getCategoryMiddle(columnIndex, dataset
/*  893 */           .getColumnCount(), dataArea, plot
/*  894 */           .getDomainAxisEdge());
/*  895 */       Line2D line = null;
/*  896 */       if (orientation == PlotOrientation.HORIZONTAL) {
/*      */         
/*  898 */         line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
/*      */       }
/*  900 */       else if (orientation == PlotOrientation.VERTICAL) {
/*      */         
/*  902 */         line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
/*      */       } else {
/*  904 */         throw new IllegalStateException();
/*      */       } 
/*  906 */       g2.setPaint(marker.getPaint());
/*  907 */       g2.setStroke(marker.getStroke());
/*  908 */       g2.draw(line);
/*  909 */       bounds = line.getBounds2D();
/*      */     } else {
/*      */       
/*  912 */       double v0 = axis.getCategoryStart(columnIndex, dataset
/*  913 */           .getColumnCount(), dataArea, plot
/*  914 */           .getDomainAxisEdge());
/*  915 */       double v1 = axis.getCategoryEnd(columnIndex, dataset
/*  916 */           .getColumnCount(), dataArea, plot
/*  917 */           .getDomainAxisEdge());
/*  918 */       Rectangle2D area = null;
/*  919 */       if (orientation == PlotOrientation.HORIZONTAL) {
/*      */         
/*  921 */         area = new Rectangle2D.Double(dataArea.getMinX(), v0, dataArea.getWidth(), v1 - v0);
/*      */       }
/*  923 */       else if (orientation == PlotOrientation.VERTICAL) {
/*      */         
/*  925 */         area = new Rectangle2D.Double(v0, dataArea.getMinY(), v1 - v0, dataArea.getHeight());
/*      */       } 
/*  927 */       g2.setPaint(marker.getPaint());
/*  928 */       g2.fill(area);
/*  929 */       bounds = area;
/*      */     } 
/*      */     
/*  932 */     String label = marker.getLabel();
/*  933 */     RectangleAnchor anchor = marker.getLabelAnchor();
/*  934 */     if (label != null) {
/*  935 */       Font labelFont = marker.getLabelFont();
/*  936 */       g2.setFont(labelFont);
/*  937 */       g2.setPaint(marker.getLabelPaint());
/*  938 */       Point2D coordinates = calculateDomainMarkerTextAnchorPoint(g2, orientation, dataArea, bounds, marker
/*  939 */           .getLabelOffset(), marker
/*  940 */           .getLabelOffsetType(), anchor);
/*  941 */       TextUtilities.drawAlignedString(label, g2, 
/*  942 */           (float)coordinates.getX(), (float)coordinates.getY(), marker
/*  943 */           .getLabelTextAnchor());
/*      */     } 
/*  945 */     g2.setComposite(savedComposite);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawRangeMarker(Graphics2D g2, CategoryPlot plot, ValueAxis axis, Marker marker, Rectangle2D dataArea) {
/*  964 */     if (marker instanceof ValueMarker) {
/*  965 */       ValueMarker vm = (ValueMarker)marker;
/*  966 */       double value = vm.getValue();
/*  967 */       Range range = axis.getRange();
/*      */       
/*  969 */       if (!range.contains(value)) {
/*      */         return;
/*      */       }
/*      */       
/*  973 */       Composite savedComposite = g2.getComposite();
/*  974 */       g2.setComposite(AlphaComposite.getInstance(3, marker
/*  975 */             .getAlpha()));
/*      */       
/*  977 */       PlotOrientation orientation = plot.getOrientation();
/*  978 */       double v = axis.valueToJava2D(value, dataArea, plot
/*  979 */           .getRangeAxisEdge());
/*  980 */       Line2D line = null;
/*  981 */       if (orientation == PlotOrientation.HORIZONTAL) {
/*      */         
/*  983 */         line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
/*      */       }
/*  985 */       else if (orientation == PlotOrientation.VERTICAL) {
/*      */         
/*  987 */         line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
/*      */       } else {
/*  989 */         throw new IllegalStateException();
/*      */       } 
/*      */       
/*  992 */       g2.setPaint(marker.getPaint());
/*  993 */       g2.setStroke(marker.getStroke());
/*  994 */       g2.draw(line);
/*      */       
/*  996 */       String label = marker.getLabel();
/*  997 */       RectangleAnchor anchor = marker.getLabelAnchor();
/*  998 */       if (label != null) {
/*  999 */         Font labelFont = marker.getLabelFont();
/* 1000 */         g2.setFont(labelFont);
/* 1001 */         Point2D coordinates = calculateRangeMarkerTextAnchorPoint(g2, orientation, dataArea, line
/* 1002 */             .getBounds2D(), marker
/* 1003 */             .getLabelOffset(), LengthAdjustmentType.EXPAND, anchor);
/*      */         
/* 1005 */         Rectangle2D rect = TextUtils.calcAlignedStringBounds(label, g2, 
/* 1006 */             (float)coordinates.getX(), (float)coordinates.getY(), marker
/* 1007 */             .getLabelTextAnchor());
/* 1008 */         g2.setPaint(marker.getLabelBackgroundColor());
/* 1009 */         g2.fill(rect);
/* 1010 */         g2.setPaint(marker.getLabelPaint());
/* 1011 */         TextUtils.drawAlignedString(label, g2, 
/* 1012 */             (float)coordinates.getX(), (float)coordinates.getY(), marker
/* 1013 */             .getLabelTextAnchor());
/*      */       } 
/* 1015 */       g2.setComposite(savedComposite);
/*      */     }
/* 1017 */     else if (marker instanceof IntervalMarker) {
/* 1018 */       IntervalMarker im = (IntervalMarker)marker;
/* 1019 */       double start = im.getStartValue();
/* 1020 */       double end = im.getEndValue();
/* 1021 */       Range range = axis.getRange();
/* 1022 */       if (!range.intersects(start, end)) {
/*      */         return;
/*      */       }
/*      */       
/* 1026 */       Composite savedComposite = g2.getComposite();
/* 1027 */       g2.setComposite(AlphaComposite.getInstance(3, marker
/* 1028 */             .getAlpha()));
/*      */       
/* 1030 */       double start2d = axis.valueToJava2D(start, dataArea, plot
/* 1031 */           .getRangeAxisEdge());
/* 1032 */       double end2d = axis.valueToJava2D(end, dataArea, plot
/* 1033 */           .getRangeAxisEdge());
/* 1034 */       double low = Math.min(start2d, end2d);
/* 1035 */       double high = Math.max(start2d, end2d);
/*      */       
/* 1037 */       PlotOrientation orientation = plot.getOrientation();
/* 1038 */       Rectangle2D rect = null;
/* 1039 */       if (orientation == PlotOrientation.HORIZONTAL) {
/*      */         
/* 1041 */         low = Math.max(low, dataArea.getMinX());
/* 1042 */         high = Math.min(high, dataArea.getMaxX());
/*      */ 
/*      */         
/* 1045 */         rect = new Rectangle2D.Double(low, dataArea.getMinY(), high - low, dataArea.getHeight());
/*      */       }
/* 1047 */       else if (orientation == PlotOrientation.VERTICAL) {
/*      */         
/* 1049 */         low = Math.max(low, dataArea.getMinY());
/* 1050 */         high = Math.min(high, dataArea.getMaxY());
/*      */         
/* 1052 */         rect = new Rectangle2D.Double(dataArea.getMinX(), low, dataArea.getWidth(), high - low);
/*      */       } 
/*      */       
/* 1055 */       Paint p = marker.getPaint();
/* 1056 */       if (p instanceof GradientPaint) {
/* 1057 */         GradientPaint gp = (GradientPaint)p;
/* 1058 */         GradientPaintTransformer t = im.getGradientPaintTransformer();
/* 1059 */         if (t != null) {
/* 1060 */           gp = t.transform(gp, rect);
/*      */         }
/* 1062 */         g2.setPaint(gp);
/*      */       } else {
/*      */         
/* 1065 */         g2.setPaint(p);
/*      */       } 
/* 1067 */       g2.fill(rect);
/*      */ 
/*      */       
/* 1070 */       if (im.getOutlinePaint() != null && im.getOutlineStroke() != null) {
/* 1071 */         if (orientation == PlotOrientation.VERTICAL) {
/* 1072 */           Line2D line = new Line2D.Double();
/* 1073 */           double x0 = dataArea.getMinX();
/* 1074 */           double x1 = dataArea.getMaxX();
/* 1075 */           g2.setPaint(im.getOutlinePaint());
/* 1076 */           g2.setStroke(im.getOutlineStroke());
/* 1077 */           if (range.contains(start)) {
/* 1078 */             line.setLine(x0, start2d, x1, start2d);
/* 1079 */             g2.draw(line);
/*      */           } 
/* 1081 */           if (range.contains(end)) {
/* 1082 */             line.setLine(x0, end2d, x1, end2d);
/* 1083 */             g2.draw(line);
/*      */           } 
/*      */         } else {
/*      */           
/* 1087 */           Line2D line = new Line2D.Double();
/* 1088 */           double y0 = dataArea.getMinY();
/* 1089 */           double y1 = dataArea.getMaxY();
/* 1090 */           g2.setPaint(im.getOutlinePaint());
/* 1091 */           g2.setStroke(im.getOutlineStroke());
/* 1092 */           if (range.contains(start)) {
/* 1093 */             line.setLine(start2d, y0, start2d, y1);
/* 1094 */             g2.draw(line);
/*      */           } 
/* 1096 */           if (range.contains(end)) {
/* 1097 */             line.setLine(end2d, y0, end2d, y1);
/* 1098 */             g2.draw(line);
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1103 */       String label = marker.getLabel();
/* 1104 */       RectangleAnchor anchor = marker.getLabelAnchor();
/* 1105 */       if (label != null) {
/* 1106 */         Font labelFont = marker.getLabelFont();
/* 1107 */         g2.setFont(labelFont);
/* 1108 */         g2.setPaint(marker.getLabelPaint());
/* 1109 */         Point2D coordinates = calculateRangeMarkerTextAnchorPoint(g2, orientation, dataArea, rect, marker
/*      */             
/* 1111 */             .getLabelOffset(), marker.getLabelOffsetType(), anchor);
/*      */         
/* 1113 */         TextUtilities.drawAlignedString(label, g2, 
/* 1114 */             (float)coordinates.getX(), (float)coordinates.getY(), marker
/* 1115 */             .getLabelTextAnchor());
/*      */       } 
/* 1117 */       g2.setComposite(savedComposite);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Point2D calculateDomainMarkerTextAnchorPoint(Graphics2D g2, PlotOrientation orientation, Rectangle2D dataArea, Rectangle2D markerArea, RectangleInsets markerOffset, LengthAdjustmentType labelOffsetType, RectangleAnchor anchor) {
/* 1140 */     Rectangle2D anchorRect = null;
/* 1141 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 1142 */       anchorRect = markerOffset.createAdjustedRectangle(markerArea, LengthAdjustmentType.CONTRACT, labelOffsetType);
/*      */     
/*      */     }
/* 1145 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 1146 */       anchorRect = markerOffset.createAdjustedRectangle(markerArea, labelOffsetType, LengthAdjustmentType.CONTRACT);
/*      */     } 
/*      */     
/* 1149 */     return RectangleAnchor.coordinates(anchorRect, anchor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Point2D calculateRangeMarkerTextAnchorPoint(Graphics2D g2, PlotOrientation orientation, Rectangle2D dataArea, Rectangle2D markerArea, RectangleInsets markerOffset, LengthAdjustmentType labelOffsetType, RectangleAnchor anchor) {
/* 1171 */     Rectangle2D anchorRect = null;
/* 1172 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 1173 */       anchorRect = markerOffset.createAdjustedRectangle(markerArea, labelOffsetType, LengthAdjustmentType.CONTRACT);
/*      */     
/*      */     }
/* 1176 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 1177 */       anchorRect = markerOffset.createAdjustedRectangle(markerArea, LengthAdjustmentType.CONTRACT, labelOffsetType);
/*      */     } 
/*      */     
/* 1180 */     return RectangleAnchor.coordinates(anchorRect, anchor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LegendItem getLegendItem(int datasetIndex, int series) {
/* 1199 */     CategoryPlot p = getPlot();
/* 1200 */     if (p == null) {
/* 1201 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1205 */     if (!isSeriesVisible(series) || !isSeriesVisibleInLegend(series)) {
/* 1206 */       return null;
/*      */     }
/*      */     
/* 1209 */     CategoryDataset dataset = p.getDataset(datasetIndex);
/* 1210 */     String label = this.legendItemLabelGenerator.generateLabel(dataset, series);
/*      */     
/* 1212 */     String description = label;
/* 1213 */     String toolTipText = null;
/* 1214 */     if (this.legendItemToolTipGenerator != null) {
/* 1215 */       toolTipText = this.legendItemToolTipGenerator.generateLabel(dataset, series);
/*      */     }
/*      */     
/* 1218 */     String urlText = null;
/* 1219 */     if (this.legendItemURLGenerator != null) {
/* 1220 */       urlText = this.legendItemURLGenerator.generateLabel(dataset, series);
/*      */     }
/*      */     
/* 1223 */     Shape shape = lookupLegendShape(series);
/* 1224 */     Paint paint = lookupSeriesPaint(series);
/* 1225 */     Paint outlinePaint = lookupSeriesOutlinePaint(series);
/* 1226 */     Stroke outlineStroke = lookupSeriesOutlineStroke(series);
/*      */     
/* 1228 */     LegendItem item = new LegendItem(label, description, toolTipText, urlText, shape, paint, outlineStroke, outlinePaint);
/*      */     
/* 1230 */     item.setLabelFont(lookupLegendTextFont(series));
/* 1231 */     Paint labelPaint = lookupLegendTextPaint(series);
/* 1232 */     if (labelPaint != null) {
/* 1233 */       item.setLabelPaint(labelPaint);
/*      */     }
/* 1235 */     item.setSeriesKey(dataset.getRowKey(series));
/* 1236 */     item.setSeriesIndex(series);
/* 1237 */     item.setDataset((Dataset)dataset);
/* 1238 */     item.setDatasetIndex(datasetIndex);
/* 1239 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/* 1251 */     if (obj == this) {
/* 1252 */       return true;
/*      */     }
/* 1254 */     if (!(obj instanceof AbstractCategoryItemRenderer)) {
/* 1255 */       return false;
/*      */     }
/* 1257 */     AbstractCategoryItemRenderer that = (AbstractCategoryItemRenderer)obj;
/*      */     
/* 1259 */     if (!ObjectUtilities.equal(this.itemLabelGenerator, that.itemLabelGenerator))
/*      */     {
/* 1261 */       return false;
/*      */     }
/* 1263 */     if (!ObjectUtilities.equal(this.itemLabelGeneratorMap, that.itemLabelGeneratorMap))
/*      */     {
/* 1265 */       return false;
/*      */     }
/* 1267 */     if (!ObjectUtilities.equal(this.baseItemLabelGenerator, that.baseItemLabelGenerator))
/*      */     {
/* 1269 */       return false;
/*      */     }
/* 1271 */     if (!ObjectUtilities.equal(this.toolTipGenerator, that.toolTipGenerator))
/*      */     {
/* 1273 */       return false;
/*      */     }
/* 1275 */     if (!ObjectUtilities.equal(this.toolTipGeneratorMap, that.toolTipGeneratorMap))
/*      */     {
/* 1277 */       return false;
/*      */     }
/* 1279 */     if (!ObjectUtilities.equal(this.baseToolTipGenerator, that.baseToolTipGenerator))
/*      */     {
/* 1281 */       return false;
/*      */     }
/* 1283 */     if (!ObjectUtilities.equal(this.itemURLGenerator, that.itemURLGenerator))
/*      */     {
/* 1285 */       return false;
/*      */     }
/* 1287 */     if (!ObjectUtilities.equal(this.itemURLGeneratorMap, that.itemURLGeneratorMap))
/*      */     {
/* 1289 */       return false;
/*      */     }
/* 1291 */     if (!ObjectUtilities.equal(this.baseItemURLGenerator, that.baseItemURLGenerator))
/*      */     {
/* 1293 */       return false;
/*      */     }
/* 1295 */     if (!ObjectUtilities.equal(this.legendItemLabelGenerator, that.legendItemLabelGenerator))
/*      */     {
/* 1297 */       return false;
/*      */     }
/* 1299 */     if (!ObjectUtilities.equal(this.legendItemToolTipGenerator, that.legendItemToolTipGenerator))
/*      */     {
/* 1301 */       return false;
/*      */     }
/* 1303 */     if (!ObjectUtilities.equal(this.legendItemURLGenerator, that.legendItemURLGenerator))
/*      */     {
/* 1305 */       return false;
/*      */     }
/* 1307 */     return super.equals(obj);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1317 */     int result = super.hashCode();
/* 1318 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DrawingSupplier getDrawingSupplier() {
/* 1328 */     DrawingSupplier result = null;
/* 1329 */     CategoryPlot cp = getPlot();
/* 1330 */     if (cp != null) {
/* 1331 */       result = cp.getDrawingSupplier();
/*      */     }
/* 1333 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateCrosshairValues(CategoryCrosshairState crosshairState, Comparable rowKey, Comparable columnKey, double value, int datasetIndex, double transX, double transY, PlotOrientation orientation) {
/* 1359 */     ParamChecks.nullNotPermitted(orientation, "orientation");
/*      */     
/* 1361 */     if (crosshairState != null) {
/* 1362 */       if (this.plot.isRangeCrosshairLockedOnData()) {
/*      */         
/* 1364 */         crosshairState.updateCrosshairPoint(rowKey, columnKey, value, datasetIndex, transX, transY, orientation);
/*      */       }
/*      */       else {
/*      */         
/* 1368 */         crosshairState.updateCrosshairX(rowKey, columnKey, datasetIndex, transX, orientation);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawItemLabel(Graphics2D g2, PlotOrientation orientation, CategoryDataset dataset, int row, int column, double x, double y, boolean negative) {
/* 1391 */     CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
/*      */     
/* 1393 */     if (generator != null) {
/* 1394 */       ItemLabelPosition position; Font labelFont = getItemLabelFont(row, column);
/* 1395 */       Paint paint = getItemLabelPaint(row, column);
/* 1396 */       g2.setFont(labelFont);
/* 1397 */       g2.setPaint(paint);
/* 1398 */       String label = generator.generateLabel(dataset, row, column);
/*      */       
/* 1400 */       if (!negative) {
/* 1401 */         position = getPositiveItemLabelPosition(row, column);
/*      */       } else {
/*      */         
/* 1404 */         position = getNegativeItemLabelPosition(row, column);
/*      */       } 
/* 1406 */       Point2D anchorPoint = calculateLabelAnchorPoint(position
/* 1407 */           .getItemLabelAnchor(), x, y, orientation);
/* 1408 */       TextUtilities.drawRotatedString(label, g2, 
/* 1409 */           (float)anchorPoint.getX(), (float)anchorPoint.getY(), position
/* 1410 */           .getTextAnchor(), position
/* 1411 */           .getAngle(), position.getRotationAnchor());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() throws CloneNotSupportedException {
/* 1429 */     AbstractCategoryItemRenderer clone = (AbstractCategoryItemRenderer)super.clone();
/*      */     
/* 1431 */     if (this.itemLabelGenerator != null) {
/* 1432 */       if (this.itemLabelGenerator instanceof PublicCloneable) {
/* 1433 */         PublicCloneable pc = (PublicCloneable)this.itemLabelGenerator;
/* 1434 */         clone
/* 1435 */           .itemLabelGenerator = (CategoryItemLabelGenerator)pc.clone();
/*      */       } else {
/*      */         
/* 1438 */         throw new CloneNotSupportedException("ItemLabelGenerator not cloneable.");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1443 */     if (this.itemLabelGeneratorMap != null) {
/* 1444 */       clone.itemLabelGeneratorMap = CloneUtils.cloneMapValues(this.itemLabelGeneratorMap);
/*      */     }
/*      */ 
/*      */     
/* 1448 */     if (this.baseItemLabelGenerator != null) {
/* 1449 */       if (this.baseItemLabelGenerator instanceof PublicCloneable) {
/* 1450 */         PublicCloneable pc = (PublicCloneable)this.baseItemLabelGenerator;
/*      */         
/* 1452 */         clone
/* 1453 */           .baseItemLabelGenerator = (CategoryItemLabelGenerator)pc.clone();
/*      */       } else {
/*      */         
/* 1456 */         throw new CloneNotSupportedException("ItemLabelGenerator not cloneable.");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1461 */     if (this.toolTipGenerator != null) {
/* 1462 */       if (this.toolTipGenerator instanceof PublicCloneable) {
/* 1463 */         PublicCloneable pc = (PublicCloneable)this.toolTipGenerator;
/* 1464 */         clone.toolTipGenerator = (CategoryToolTipGenerator)pc.clone();
/*      */       } else {
/*      */         
/* 1467 */         throw new CloneNotSupportedException("Tool tip generator not cloneable.");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1472 */     if (this.toolTipGeneratorMap != null) {
/* 1473 */       clone.toolTipGeneratorMap = CloneUtils.cloneMapValues(this.toolTipGeneratorMap);
/*      */     }
/*      */ 
/*      */     
/* 1477 */     if (this.baseToolTipGenerator != null) {
/* 1478 */       if (this.baseToolTipGenerator instanceof PublicCloneable) {
/* 1479 */         PublicCloneable pc = (PublicCloneable)this.baseToolTipGenerator;
/*      */         
/* 1481 */         clone
/* 1482 */           .baseToolTipGenerator = (CategoryToolTipGenerator)pc.clone();
/*      */       } else {
/*      */         
/* 1485 */         throw new CloneNotSupportedException("Base tool tip generator not cloneable.");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1490 */     if (this.itemURLGenerator != null) {
/* 1491 */       if (this.itemURLGenerator instanceof PublicCloneable) {
/* 1492 */         PublicCloneable pc = (PublicCloneable)this.itemURLGenerator;
/* 1493 */         clone.itemURLGenerator = (CategoryURLGenerator)pc.clone();
/*      */       } else {
/*      */         
/* 1496 */         throw new CloneNotSupportedException("Item URL generator not cloneable.");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1501 */     if (this.itemURLGeneratorMap != null) {
/* 1502 */       clone.itemURLGeneratorMap = CloneUtils.cloneMapValues(this.itemURLGeneratorMap);
/*      */     }
/*      */ 
/*      */     
/* 1506 */     if (this.baseItemURLGenerator != null) {
/* 1507 */       if (this.baseItemURLGenerator instanceof PublicCloneable) {
/* 1508 */         PublicCloneable pc = (PublicCloneable)this.baseItemURLGenerator;
/*      */         
/* 1510 */         clone.baseItemURLGenerator = (CategoryURLGenerator)pc.clone();
/*      */       } else {
/*      */         
/* 1513 */         throw new CloneNotSupportedException("Base item URL generator not cloneable.");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1518 */     if (this.legendItemLabelGenerator instanceof PublicCloneable) {
/* 1519 */       clone
/* 1520 */         .legendItemLabelGenerator = (CategorySeriesLabelGenerator)ObjectUtilities.clone(this.legendItemLabelGenerator);
/*      */     }
/* 1522 */     if (this.legendItemToolTipGenerator instanceof PublicCloneable) {
/* 1523 */       clone
/* 1524 */         .legendItemToolTipGenerator = (CategorySeriesLabelGenerator)ObjectUtilities.clone(this.legendItemToolTipGenerator);
/*      */     }
/* 1526 */     if (this.legendItemURLGenerator instanceof PublicCloneable) {
/* 1527 */       clone
/* 1528 */         .legendItemURLGenerator = (CategorySeriesLabelGenerator)ObjectUtilities.clone(this.legendItemURLGenerator);
/*      */     }
/* 1530 */     return clone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CategoryAxis getDomainAxis(CategoryPlot plot, int index) {
/* 1542 */     CategoryAxis result = plot.getDomainAxis(index);
/* 1543 */     if (result == null) {
/* 1544 */       result = plot.getDomainAxis();
/*      */     }
/* 1546 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ValueAxis getRangeAxis(CategoryPlot plot, int index) {
/* 1558 */     ValueAxis result = plot.getRangeAxis(index);
/* 1559 */     if (result == null) {
/* 1560 */       result = plot.getRangeAxis();
/*      */     }
/* 1562 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LegendItemCollection getLegendItems() {
/* 1575 */     LegendItemCollection result = new LegendItemCollection();
/* 1576 */     if (this.plot == null) {
/* 1577 */       return result;
/*      */     }
/* 1579 */     int index = this.plot.getIndexOf(this);
/* 1580 */     CategoryDataset dataset = this.plot.getDataset(index);
/* 1581 */     if (dataset == null) {
/* 1582 */       return result;
/*      */     }
/* 1584 */     int seriesCount = dataset.getRowCount();
/* 1585 */     if (this.plot.getRowRenderingOrder().equals(SortOrder.ASCENDING)) {
/* 1586 */       for (int i = 0; i < seriesCount; i++) {
/* 1587 */         if (isSeriesVisibleInLegend(i)) {
/* 1588 */           LegendItem item = getLegendItem(index, i);
/* 1589 */           if (item != null) {
/* 1590 */             result.add(item);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1596 */       for (int i = seriesCount - 1; i >= 0; i--) {
/* 1597 */         if (isSeriesVisibleInLegend(i)) {
/* 1598 */           LegendItem item = getLegendItem(index, i);
/* 1599 */           if (item != null) {
/* 1600 */             result.add(item);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1605 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategorySeriesLabelGenerator getLegendItemLabelGenerator() {
/* 1616 */     return this.legendItemLabelGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegendItemLabelGenerator(CategorySeriesLabelGenerator generator) {
/* 1629 */     ParamChecks.nullNotPermitted(generator, "generator");
/* 1630 */     this.legendItemLabelGenerator = generator;
/* 1631 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategorySeriesLabelGenerator getLegendItemToolTipGenerator() {
/* 1642 */     return this.legendItemToolTipGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegendItemToolTipGenerator(CategorySeriesLabelGenerator generator) {
/* 1655 */     this.legendItemToolTipGenerator = generator;
/* 1656 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategorySeriesLabelGenerator getLegendItemURLGenerator() {
/* 1667 */     return this.legendItemURLGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegendItemURLGenerator(CategorySeriesLabelGenerator generator) {
/* 1680 */     this.legendItemURLGenerator = generator;
/* 1681 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addItemEntity(EntityCollection entities, CategoryDataset dataset, int row, int column, Shape hotspot) {
/* 1695 */     ParamChecks.nullNotPermitted(hotspot, "hotspot");
/* 1696 */     if (!getItemCreateEntity(row, column)) {
/*      */       return;
/*      */     }
/* 1699 */     String tip = null;
/* 1700 */     CategoryToolTipGenerator tipster = getToolTipGenerator(row, column);
/* 1701 */     if (tipster != null) {
/* 1702 */       tip = tipster.generateToolTip(dataset, row, column);
/*      */     }
/* 1704 */     String url = null;
/* 1705 */     CategoryURLGenerator urlster = getItemURLGenerator(row, column);
/* 1706 */     if (urlster != null) {
/* 1707 */       url = urlster.generateURL(dataset, row, column);
/*      */     }
/*      */     
/* 1710 */     CategoryItemEntity entity = new CategoryItemEntity(hotspot, tip, url, dataset, dataset.getRowKey(row), dataset.getColumnKey(column));
/* 1711 */     entities.add((ChartEntity)entity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addEntity(EntityCollection entities, Shape hotspot, CategoryDataset dataset, int row, int column, double entityX, double entityY) {
/* 1733 */     if (!getItemCreateEntity(row, column)) {
/*      */       return;
/*      */     }
/* 1736 */     Shape s = hotspot;
/* 1737 */     if (hotspot == null) {
/* 1738 */       double r = getDefaultEntityRadius();
/* 1739 */       double w = r * 2.0D;
/* 1740 */       if (getPlot().getOrientation() == PlotOrientation.VERTICAL) {
/* 1741 */         s = new Ellipse2D.Double(entityX - r, entityY - r, w, w);
/*      */       } else {
/*      */         
/* 1744 */         s = new Ellipse2D.Double(entityY - r, entityX - r, w, w);
/*      */       } 
/*      */     } 
/* 1747 */     String tip = null;
/* 1748 */     CategoryToolTipGenerator generator = getToolTipGenerator(row, column);
/* 1749 */     if (generator != null) {
/* 1750 */       tip = generator.generateToolTip(dataset, row, column);
/*      */     }
/* 1752 */     String url = null;
/* 1753 */     CategoryURLGenerator urlster = getItemURLGenerator(row, column);
/* 1754 */     if (urlster != null) {
/* 1755 */       url = urlster.generateURL(dataset, row, column);
/*      */     }
/*      */     
/* 1758 */     CategoryItemEntity entity = new CategoryItemEntity(s, tip, url, dataset, dataset.getRowKey(row), dataset.getColumnKey(column));
/* 1759 */     entities.add((ChartEntity)entity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemLabelGenerator(CategoryItemLabelGenerator generator) {
/* 1798 */     this.itemLabelGenerator = generator;
/* 1799 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryToolTipGenerator getToolTipGenerator() {
/* 1816 */     return this.toolTipGenerator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setToolTipGenerator(CategoryToolTipGenerator generator) {
/* 1835 */     this.toolTipGenerator = generator;
/* 1836 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemURLGenerator(CategoryURLGenerator generator) {
/* 1852 */     this.itemURLGenerator = generator;
/* 1853 */     fireChangeEvent();
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/AbstractCategoryItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */