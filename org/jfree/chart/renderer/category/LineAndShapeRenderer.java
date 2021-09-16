/*      */ package org.jfree.chart.renderer.category;
/*      */ 
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.Serializable;
/*      */ import org.jfree.chart.LegendItem;
/*      */ import org.jfree.chart.axis.CategoryAxis;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.plot.CategoryPlot;
/*      */ import org.jfree.chart.plot.PlotOrientation;
/*      */ import org.jfree.data.category.CategoryDataset;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.util.BooleanList;
/*      */ import org.jfree.util.BooleanUtilities;
/*      */ import org.jfree.util.ObjectUtilities;
/*      */ import org.jfree.util.PublicCloneable;
/*      */ import org.jfree.util.ShapeUtilities;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LineAndShapeRenderer
/*      */   extends AbstractCategoryItemRenderer
/*      */   implements Cloneable, PublicCloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -197749519869226398L;
/*      */   private Boolean linesVisible;
/*      */   private BooleanList seriesLinesVisible;
/*      */   private boolean baseLinesVisible;
/*      */   private Boolean shapesVisible;
/*      */   private BooleanList seriesShapesVisible;
/*      */   private boolean baseShapesVisible;
/*      */   private Boolean shapesFilled;
/*      */   private BooleanList seriesShapesFilled;
/*      */   private boolean baseShapesFilled;
/*      */   private boolean useFillPaint;
/*      */   private boolean drawOutlines;
/*      */   private boolean useOutlinePaint;
/*      */   private boolean useSeriesOffset;
/*      */   private double itemMargin;
/*      */   
/*      */   public LineAndShapeRenderer() {
/*  220 */     this(true, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LineAndShapeRenderer(boolean lines, boolean shapes) {
/*  231 */     this.linesVisible = null;
/*  232 */     this.seriesLinesVisible = new BooleanList();
/*  233 */     this.baseLinesVisible = lines;
/*  234 */     this.shapesVisible = null;
/*  235 */     this.seriesShapesVisible = new BooleanList();
/*  236 */     this.baseShapesVisible = shapes;
/*  237 */     this.shapesFilled = null;
/*  238 */     this.seriesShapesFilled = new BooleanList();
/*  239 */     this.baseShapesFilled = true;
/*  240 */     this.useFillPaint = false;
/*  241 */     this.drawOutlines = true;
/*  242 */     this.useOutlinePaint = false;
/*  243 */     this.useSeriesOffset = false;
/*  244 */     this.itemMargin = 0.0D;
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
/*      */   public boolean getItemLineVisible(int series, int item) {
/*  259 */     Boolean flag = this.linesVisible;
/*  260 */     if (flag == null) {
/*  261 */       flag = getSeriesLinesVisible(series);
/*      */     }
/*  263 */     if (flag != null) {
/*  264 */       return flag.booleanValue();
/*      */     }
/*      */     
/*  267 */     return this.baseLinesVisible;
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
/*      */   public Boolean getLinesVisible() {
/*  284 */     return this.linesVisible;
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
/*      */   public void setLinesVisible(Boolean visible) {
/*  301 */     this.linesVisible = visible;
/*  302 */     fireChangeEvent();
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
/*      */   public void setLinesVisible(boolean visible) {
/*  318 */     setLinesVisible(BooleanUtilities.valueOf(visible));
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
/*      */   public Boolean getSeriesLinesVisible(int series) {
/*  332 */     return this.seriesLinesVisible.getBoolean(series);
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
/*      */   public void setSeriesLinesVisible(int series, Boolean flag) {
/*  345 */     this.seriesLinesVisible.setBoolean(series, flag);
/*  346 */     fireChangeEvent();
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
/*      */   public void setSeriesLinesVisible(int series, boolean visible) {
/*  359 */     setSeriesLinesVisible(series, BooleanUtilities.valueOf(visible));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBaseLinesVisible() {
/*  370 */     return this.baseLinesVisible;
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
/*      */   public void setBaseLinesVisible(boolean flag) {
/*  382 */     this.baseLinesVisible = flag;
/*  383 */     fireChangeEvent();
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
/*      */   public boolean getItemShapeVisible(int series, int item) {
/*  398 */     Boolean flag = this.shapesVisible;
/*  399 */     if (flag == null) {
/*  400 */       flag = getSeriesShapesVisible(series);
/*      */     }
/*  402 */     if (flag != null) {
/*  403 */       return flag.booleanValue();
/*      */     }
/*      */     
/*  406 */     return this.baseShapesVisible;
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
/*      */   public Boolean getShapesVisible() {
/*  422 */     return this.shapesVisible;
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
/*      */   public void setShapesVisible(Boolean visible) {
/*  437 */     this.shapesVisible = visible;
/*  438 */     fireChangeEvent();
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
/*      */   public void setShapesVisible(boolean visible) {
/*  453 */     setShapesVisible(BooleanUtilities.valueOf(visible));
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
/*      */   public Boolean getSeriesShapesVisible(int series) {
/*  467 */     return this.seriesShapesVisible.getBoolean(series);
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
/*      */   public void setSeriesShapesVisible(int series, boolean visible) {
/*  480 */     setSeriesShapesVisible(series, BooleanUtilities.valueOf(visible));
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
/*      */   public void setSeriesShapesVisible(int series, Boolean flag) {
/*  493 */     this.seriesShapesVisible.setBoolean(series, flag);
/*  494 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBaseShapesVisible() {
/*  505 */     return this.baseShapesVisible;
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
/*      */   public void setBaseShapesVisible(boolean flag) {
/*  517 */     this.baseShapesVisible = flag;
/*  518 */     fireChangeEvent();
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
/*      */   public boolean getDrawOutlines() {
/*  530 */     return this.drawOutlines;
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
/*      */   public void setDrawOutlines(boolean flag) {
/*  546 */     this.drawOutlines = flag;
/*  547 */     fireChangeEvent();
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
/*      */   public boolean getUseOutlinePaint() {
/*  559 */     return this.useOutlinePaint;
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
/*      */   public void setUseOutlinePaint(boolean use) {
/*  572 */     this.useOutlinePaint = use;
/*  573 */     fireChangeEvent();
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
/*      */   public boolean getItemShapeFilled(int series, int item) {
/*  590 */     return getSeriesShapesFilled(series);
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
/*      */   public boolean getSeriesShapesFilled(int series) {
/*  604 */     if (this.shapesFilled != null) {
/*  605 */       return this.shapesFilled.booleanValue();
/*      */     }
/*      */ 
/*      */     
/*  609 */     Boolean flag = this.seriesShapesFilled.getBoolean(series);
/*  610 */     if (flag != null) {
/*  611 */       return flag.booleanValue();
/*      */     }
/*      */     
/*  614 */     return this.baseShapesFilled;
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
/*      */   public Boolean getShapesFilled() {
/*  631 */     return this.shapesFilled;
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
/*      */   public void setShapesFilled(boolean filled) {
/*  646 */     if (filled) {
/*  647 */       setShapesFilled(Boolean.TRUE);
/*      */     } else {
/*      */       
/*  650 */       setShapesFilled(Boolean.FALSE);
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
/*      */   public void setShapesFilled(Boolean filled) {
/*  666 */     this.shapesFilled = filled;
/*  667 */     fireChangeEvent();
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
/*      */   public void setSeriesShapesFilled(int series, Boolean filled) {
/*  680 */     this.seriesShapesFilled.setBoolean(series, filled);
/*  681 */     fireChangeEvent();
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
/*      */   public void setSeriesShapesFilled(int series, boolean filled) {
/*  695 */     setSeriesShapesFilled(series, BooleanUtilities.valueOf(filled));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBaseShapesFilled() {
/*  706 */     return this.baseShapesFilled;
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
/*      */   public void setBaseShapesFilled(boolean flag) {
/*  718 */     this.baseShapesFilled = flag;
/*  719 */     fireChangeEvent();
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
/*      */   public boolean getUseFillPaint() {
/*  732 */     return this.useFillPaint;
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
/*      */   public void setUseFillPaint(boolean flag) {
/*  745 */     this.useFillPaint = flag;
/*  746 */     fireChangeEvent();
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
/*      */   public boolean getUseSeriesOffset() {
/*  760 */     return this.useSeriesOffset;
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
/*      */   public void setUseSeriesOffset(boolean offset) {
/*  775 */     this.useSeriesOffset = offset;
/*  776 */     fireChangeEvent();
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
/*      */   public double getItemMargin() {
/*  793 */     return this.itemMargin;
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
/*      */   public void setItemMargin(double margin) {
/*  809 */     if (margin < 0.0D || margin >= 1.0D) {
/*  810 */       throw new IllegalArgumentException("Requires 0.0 <= margin < 1.0.");
/*      */     }
/*  812 */     this.itemMargin = margin;
/*  813 */     fireChangeEvent();
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
/*      */   public LegendItem getLegendItem(int datasetIndex, int series) {
/*  827 */     CategoryPlot cp = getPlot();
/*  828 */     if (cp == null) {
/*  829 */       return null;
/*      */     }
/*      */     
/*  832 */     if (isSeriesVisible(series) && isSeriesVisibleInLegend(series)) {
/*  833 */       CategoryDataset dataset = cp.getDataset(datasetIndex);
/*  834 */       String label = getLegendItemLabelGenerator().generateLabel(dataset, series);
/*      */       
/*  836 */       String description = label;
/*  837 */       String toolTipText = null;
/*  838 */       if (getLegendItemToolTipGenerator() != null) {
/*  839 */         toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*      */       }
/*      */       
/*  842 */       String urlText = null;
/*  843 */       if (getLegendItemURLGenerator() != null) {
/*  844 */         urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*      */       }
/*      */       
/*  847 */       Shape shape = lookupLegendShape(series);
/*  848 */       Paint paint = lookupSeriesPaint(series);
/*      */       
/*  850 */       Paint fillPaint = this.useFillPaint ? getItemFillPaint(series, 0) : paint;
/*  851 */       boolean shapeOutlineVisible = this.drawOutlines;
/*      */       
/*  853 */       Paint outlinePaint = this.useOutlinePaint ? getItemOutlinePaint(series, 0) : paint;
/*  854 */       Stroke outlineStroke = lookupSeriesOutlineStroke(series);
/*  855 */       boolean lineVisible = getItemLineVisible(series, 0);
/*  856 */       boolean shapeVisible = getItemShapeVisible(series, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  861 */       LegendItem result = new LegendItem(label, description, toolTipText, urlText, shapeVisible, shape, getItemShapeFilled(series, 0), fillPaint, shapeOutlineVisible, outlinePaint, outlineStroke, lineVisible, new Line2D.Double(-7.0D, 0.0D, 7.0D, 0.0D), getItemStroke(series, 0), getItemPaint(series, 0));
/*  862 */       result.setLabelFont(lookupLegendTextFont(series));
/*  863 */       Paint labelPaint = lookupLegendTextPaint(series);
/*  864 */       if (labelPaint != null) {
/*  865 */         result.setLabelPaint(labelPaint);
/*      */       }
/*  867 */       result.setDataset((Dataset)dataset);
/*  868 */       result.setDatasetIndex(datasetIndex);
/*  869 */       result.setSeriesKey(dataset.getRowKey(series));
/*  870 */       result.setSeriesIndex(series);
/*  871 */       return result;
/*      */     } 
/*  873 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPassCount() {
/*  884 */     return 2;
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
/*      */   public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {
/*      */     double x1;
/*  908 */     if (!getItemVisible(row, column)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  913 */     if (!getItemLineVisible(row, column) && 
/*  914 */       !getItemShapeVisible(row, column)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  919 */     Number v = dataset.getValue(row, column);
/*  920 */     if (v == null) {
/*      */       return;
/*      */     }
/*      */     
/*  924 */     int visibleRow = state.getVisibleSeriesIndex(row);
/*  925 */     if (visibleRow < 0) {
/*      */       return;
/*      */     }
/*  928 */     int visibleRowCount = state.getVisibleSeriesCount();
/*      */     
/*  930 */     PlotOrientation orientation = plot.getOrientation();
/*      */ 
/*      */ 
/*      */     
/*  934 */     if (this.useSeriesOffset) {
/*  935 */       x1 = domainAxis.getCategorySeriesMiddle(column, dataset
/*  936 */           .getColumnCount(), visibleRow, visibleRowCount, this.itemMargin, dataArea, plot
/*  937 */           .getDomainAxisEdge());
/*      */     } else {
/*      */       
/*  940 */       x1 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot
/*  941 */           .getDomainAxisEdge());
/*      */     } 
/*  943 */     double value = v.doubleValue();
/*  944 */     double y1 = rangeAxis.valueToJava2D(value, dataArea, plot
/*  945 */         .getRangeAxisEdge());
/*      */     
/*  947 */     if (pass == 0 && getItemLineVisible(row, column) && 
/*  948 */       column != 0) {
/*  949 */       Number previousValue = dataset.getValue(row, column - 1);
/*  950 */       if (previousValue != null) {
/*      */         
/*  952 */         double x0, previous = previousValue.doubleValue();
/*      */         
/*  954 */         if (this.useSeriesOffset) {
/*  955 */           x0 = domainAxis.getCategorySeriesMiddle(column - 1, dataset
/*  956 */               .getColumnCount(), visibleRow, visibleRowCount, this.itemMargin, dataArea, plot
/*      */ 
/*      */               
/*  959 */               .getDomainAxisEdge());
/*      */         } else {
/*      */           
/*  962 */           x0 = domainAxis.getCategoryMiddle(column - 1, 
/*  963 */               getColumnCount(), dataArea, plot
/*  964 */               .getDomainAxisEdge());
/*      */         } 
/*  966 */         double y0 = rangeAxis.valueToJava2D(previous, dataArea, plot
/*  967 */             .getRangeAxisEdge());
/*      */         
/*  969 */         Line2D line = null;
/*  970 */         if (orientation == PlotOrientation.HORIZONTAL) {
/*  971 */           line = new Line2D.Double(y0, x0, y1, x1);
/*      */         }
/*  973 */         else if (orientation == PlotOrientation.VERTICAL) {
/*  974 */           line = new Line2D.Double(x0, y0, x1, y1);
/*      */         } 
/*  976 */         g2.setPaint(getItemPaint(row, column));
/*  977 */         g2.setStroke(getItemStroke(row, column));
/*  978 */         g2.draw(line);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  983 */     if (pass == 1) {
/*  984 */       Shape shape = getItemShape(row, column);
/*  985 */       if (orientation == PlotOrientation.HORIZONTAL) {
/*  986 */         shape = ShapeUtilities.createTranslatedShape(shape, y1, x1);
/*      */       }
/*  988 */       else if (orientation == PlotOrientation.VERTICAL) {
/*  989 */         shape = ShapeUtilities.createTranslatedShape(shape, x1, y1);
/*      */       } 
/*      */       
/*  992 */       if (getItemShapeVisible(row, column)) {
/*  993 */         if (getItemShapeFilled(row, column)) {
/*  994 */           if (this.useFillPaint) {
/*  995 */             g2.setPaint(getItemFillPaint(row, column));
/*      */           } else {
/*      */             
/*  998 */             g2.setPaint(getItemPaint(row, column));
/*      */           } 
/* 1000 */           g2.fill(shape);
/*      */         } 
/* 1002 */         if (this.drawOutlines) {
/* 1003 */           if (this.useOutlinePaint) {
/* 1004 */             g2.setPaint(getItemOutlinePaint(row, column));
/*      */           } else {
/*      */             
/* 1007 */             g2.setPaint(getItemPaint(row, column));
/*      */           } 
/* 1009 */           g2.setStroke(getItemOutlineStroke(row, column));
/* 1010 */           g2.draw(shape);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1015 */       if (isItemLabelVisible(row, column)) {
/* 1016 */         if (orientation == PlotOrientation.HORIZONTAL) {
/* 1017 */           drawItemLabel(g2, orientation, dataset, row, column, y1, x1, (value < 0.0D));
/*      */         
/*      */         }
/* 1020 */         else if (orientation == PlotOrientation.VERTICAL) {
/* 1021 */           drawItemLabel(g2, orientation, dataset, row, column, x1, y1, (value < 0.0D));
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1027 */       int datasetIndex = plot.indexOf(dataset);
/* 1028 */       updateCrosshairValues(state.getCrosshairState(), dataset
/* 1029 */           .getRowKey(row), dataset.getColumnKey(column), value, datasetIndex, x1, y1, orientation);
/*      */ 
/*      */ 
/*      */       
/* 1033 */       EntityCollection entities = state.getEntityCollection();
/* 1034 */       if (entities != null) {
/* 1035 */         addItemEntity(entities, dataset, row, column, shape);
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
/*      */   public boolean equals(Object obj) {
/* 1051 */     if (obj == this) {
/* 1052 */       return true;
/*      */     }
/* 1054 */     if (!(obj instanceof LineAndShapeRenderer)) {
/* 1055 */       return false;
/*      */     }
/*      */     
/* 1058 */     LineAndShapeRenderer that = (LineAndShapeRenderer)obj;
/* 1059 */     if (this.baseLinesVisible != that.baseLinesVisible) {
/* 1060 */       return false;
/*      */     }
/* 1062 */     if (!ObjectUtilities.equal(this.seriesLinesVisible, that.seriesLinesVisible))
/*      */     {
/* 1064 */       return false;
/*      */     }
/* 1066 */     if (!ObjectUtilities.equal(this.linesVisible, that.linesVisible)) {
/* 1067 */       return false;
/*      */     }
/* 1069 */     if (this.baseShapesVisible != that.baseShapesVisible) {
/* 1070 */       return false;
/*      */     }
/* 1072 */     if (!ObjectUtilities.equal(this.seriesShapesVisible, that.seriesShapesVisible))
/*      */     {
/* 1074 */       return false;
/*      */     }
/* 1076 */     if (!ObjectUtilities.equal(this.shapesVisible, that.shapesVisible)) {
/* 1077 */       return false;
/*      */     }
/* 1079 */     if (!ObjectUtilities.equal(this.shapesFilled, that.shapesFilled)) {
/* 1080 */       return false;
/*      */     }
/* 1082 */     if (!ObjectUtilities.equal(this.seriesShapesFilled, that.seriesShapesFilled))
/*      */     {
/* 1084 */       return false;
/*      */     }
/* 1086 */     if (this.baseShapesFilled != that.baseShapesFilled) {
/* 1087 */       return false;
/*      */     }
/* 1089 */     if (this.useOutlinePaint != that.useOutlinePaint) {
/* 1090 */       return false;
/*      */     }
/* 1092 */     if (this.useSeriesOffset != that.useSeriesOffset) {
/* 1093 */       return false;
/*      */     }
/* 1095 */     if (this.itemMargin != that.itemMargin) {
/* 1096 */       return false;
/*      */     }
/* 1098 */     return super.equals(obj);
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
/*      */   public Object clone() throws CloneNotSupportedException {
/* 1110 */     LineAndShapeRenderer clone = (LineAndShapeRenderer)super.clone();
/* 1111 */     clone
/* 1112 */       .seriesLinesVisible = (BooleanList)this.seriesLinesVisible.clone();
/* 1113 */     clone
/* 1114 */       .seriesShapesVisible = (BooleanList)this.seriesShapesVisible.clone();
/* 1115 */     clone
/* 1116 */       .seriesShapesFilled = (BooleanList)this.seriesShapesFilled.clone();
/* 1117 */     return clone;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/LineAndShapeRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */