/*      */ package org.jfree.chart.axis;
/*      */ 
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.jfree.chart.entity.CategoryLabelEntity;
/*      */ import org.jfree.chart.entity.ChartEntity;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.plot.CategoryPlot;
/*      */ import org.jfree.chart.plot.Plot;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.category.CategoryDataset;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.text.G2TextMeasurer;
/*      */ import org.jfree.text.TextBlock;
/*      */ import org.jfree.text.TextMeasurer;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.ui.RectangleAnchor;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ import org.jfree.ui.Size2D;
/*      */ import org.jfree.util.ObjectUtilities;
/*      */ import org.jfree.util.PaintUtilities;
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
/*      */ public class CategoryAxis
/*      */   extends Axis
/*      */   implements Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 5886554608114265863L;
/*      */   public static final double DEFAULT_AXIS_MARGIN = 0.05D;
/*      */   public static final double DEFAULT_CATEGORY_MARGIN = 0.2D;
/*      */   private double lowerMargin;
/*      */   private double upperMargin;
/*      */   private double categoryMargin;
/*      */   private int maximumCategoryLabelLines;
/*      */   private float maximumCategoryLabelWidthRatio;
/*      */   private int categoryLabelPositionOffset;
/*      */   private CategoryLabelPositions categoryLabelPositions;
/*      */   private Map tickLabelFontMap;
/*      */   private transient Map tickLabelPaintMap;
/*      */   private Map categoryLabelToolTips;
/*      */   private Map categoryLabelURLs;
/*      */   
/*      */   public CategoryAxis() {
/*  206 */     this((String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CategoryAxis(String label) {
/*  215 */     super(label);
/*      */     
/*  217 */     this.lowerMargin = 0.05D;
/*  218 */     this.upperMargin = 0.05D;
/*  219 */     this.categoryMargin = 0.2D;
/*  220 */     this.maximumCategoryLabelLines = 1;
/*  221 */     this.maximumCategoryLabelWidthRatio = 0.0F;
/*      */     
/*  223 */     this.categoryLabelPositionOffset = 4;
/*  224 */     this.categoryLabelPositions = CategoryLabelPositions.STANDARD;
/*  225 */     this.tickLabelFontMap = new HashMap<Object, Object>();
/*  226 */     this.tickLabelPaintMap = new HashMap<Object, Object>();
/*  227 */     this.categoryLabelToolTips = new HashMap<Object, Object>();
/*  228 */     this.categoryLabelURLs = new HashMap<Object, Object>();
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
/*      */   public double getLowerMargin() {
/*  240 */     return this.lowerMargin;
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
/*      */   public void setLowerMargin(double margin) {
/*  253 */     this.lowerMargin = margin;
/*  254 */     fireChangeEvent();
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
/*      */   public double getUpperMargin() {
/*  266 */     return this.upperMargin;
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
/*      */   public void setUpperMargin(double margin) {
/*  279 */     this.upperMargin = margin;
/*  280 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getCategoryMargin() {
/*  291 */     return this.categoryMargin;
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
/*      */   public void setCategoryMargin(double margin) {
/*  305 */     this.categoryMargin = margin;
/*  306 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumCategoryLabelLines() {
/*  317 */     return this.maximumCategoryLabelLines;
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
/*      */   public void setMaximumCategoryLabelLines(int lines) {
/*  329 */     this.maximumCategoryLabelLines = lines;
/*  330 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getMaximumCategoryLabelWidthRatio() {
/*  341 */     return this.maximumCategoryLabelWidthRatio;
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
/*      */   public void setMaximumCategoryLabelWidthRatio(float ratio) {
/*  353 */     this.maximumCategoryLabelWidthRatio = ratio;
/*  354 */     fireChangeEvent();
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
/*      */   public int getCategoryLabelPositionOffset() {
/*  366 */     return this.categoryLabelPositionOffset;
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
/*      */   public void setCategoryLabelPositionOffset(int offset) {
/*  379 */     this.categoryLabelPositionOffset = offset;
/*  380 */     fireChangeEvent();
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
/*      */   public CategoryLabelPositions getCategoryLabelPositions() {
/*  392 */     return this.categoryLabelPositions;
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
/*      */   public void setCategoryLabelPositions(CategoryLabelPositions positions) {
/*  404 */     ParamChecks.nullNotPermitted(positions, "positions");
/*  405 */     this.categoryLabelPositions = positions;
/*  406 */     fireChangeEvent();
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
/*      */   public Font getTickLabelFont(Comparable category) {
/*  419 */     ParamChecks.nullNotPermitted(category, "category");
/*  420 */     Font result = (Font)this.tickLabelFontMap.get(category);
/*      */     
/*  422 */     if (result == null) {
/*  423 */       result = getTickLabelFont();
/*      */     }
/*  425 */     return result;
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
/*      */   public void setTickLabelFont(Comparable category, Font font) {
/*  438 */     ParamChecks.nullNotPermitted(category, "category");
/*  439 */     if (font == null) {
/*  440 */       this.tickLabelFontMap.remove(category);
/*      */     } else {
/*      */       
/*  443 */       this.tickLabelFontMap.put(category, font);
/*      */     } 
/*  445 */     fireChangeEvent();
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
/*      */   public Paint getTickLabelPaint(Comparable category) {
/*  458 */     ParamChecks.nullNotPermitted(category, "category");
/*  459 */     Paint result = (Paint)this.tickLabelPaintMap.get(category);
/*      */     
/*  461 */     if (result == null) {
/*  462 */       result = getTickLabelPaint();
/*      */     }
/*  464 */     return result;
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
/*      */   public void setTickLabelPaint(Comparable category, Paint paint) {
/*  477 */     ParamChecks.nullNotPermitted(category, "category");
/*  478 */     if (paint == null) {
/*  479 */       this.tickLabelPaintMap.remove(category);
/*      */     } else {
/*      */       
/*  482 */       this.tickLabelPaintMap.put(category, paint);
/*      */     } 
/*  484 */     fireChangeEvent();
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
/*      */   public void addCategoryLabelToolTip(Comparable category, String tooltip) {
/*  497 */     ParamChecks.nullNotPermitted(category, "category");
/*  498 */     this.categoryLabelToolTips.put(category, tooltip);
/*  499 */     fireChangeEvent();
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
/*      */   public String getCategoryLabelToolTip(Comparable category) {
/*  514 */     ParamChecks.nullNotPermitted(category, "category");
/*  515 */     return (String)this.categoryLabelToolTips.get(category);
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
/*      */   public void removeCategoryLabelToolTip(Comparable category) {
/*  529 */     ParamChecks.nullNotPermitted(category, "category");
/*  530 */     if (this.categoryLabelToolTips.remove(category) != null) {
/*  531 */       fireChangeEvent();
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
/*      */   public void clearCategoryLabelToolTips() {
/*  543 */     this.categoryLabelToolTips.clear();
/*  544 */     fireChangeEvent();
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
/*      */   public void addCategoryLabelURL(Comparable category, String url) {
/*  559 */     ParamChecks.nullNotPermitted(category, "category");
/*  560 */     this.categoryLabelURLs.put(category, url);
/*  561 */     fireChangeEvent();
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
/*      */   public String getCategoryLabelURL(Comparable category) {
/*  577 */     ParamChecks.nullNotPermitted(category, "category");
/*  578 */     return (String)this.categoryLabelURLs.get(category);
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
/*      */   public void removeCategoryLabelURL(Comparable category) {
/*  594 */     ParamChecks.nullNotPermitted(category, "category");
/*  595 */     if (this.categoryLabelURLs.remove(category) != null) {
/*  596 */       fireChangeEvent();
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
/*      */   public void clearCategoryLabelURLs() {
/*  610 */     this.categoryLabelURLs.clear();
/*  611 */     fireChangeEvent();
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
/*      */   public double getCategoryJava2DCoordinate(CategoryAnchor anchor, int category, int categoryCount, Rectangle2D area, RectangleEdge edge) {
/*  629 */     double result = 0.0D;
/*  630 */     if (anchor == CategoryAnchor.START) {
/*  631 */       result = getCategoryStart(category, categoryCount, area, edge);
/*      */     }
/*  633 */     else if (anchor == CategoryAnchor.MIDDLE) {
/*  634 */       result = getCategoryMiddle(category, categoryCount, area, edge);
/*      */     }
/*  636 */     else if (anchor == CategoryAnchor.END) {
/*  637 */       result = getCategoryEnd(category, categoryCount, area, edge);
/*      */     } 
/*  639 */     return result;
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
/*      */   public double getCategoryStart(int category, int categoryCount, Rectangle2D area, RectangleEdge edge) {
/*  659 */     double result = 0.0D;
/*  660 */     if (edge == RectangleEdge.TOP || edge == RectangleEdge.BOTTOM) {
/*  661 */       result = area.getX() + area.getWidth() * getLowerMargin();
/*      */     }
/*  663 */     else if (edge == RectangleEdge.LEFT || edge == RectangleEdge.RIGHT) {
/*      */       
/*  665 */       result = area.getMinY() + area.getHeight() * getLowerMargin();
/*      */     } 
/*      */     
/*  668 */     double categorySize = calculateCategorySize(categoryCount, area, edge);
/*  669 */     double categoryGapWidth = calculateCategoryGapSize(categoryCount, area, edge);
/*      */ 
/*      */     
/*  672 */     result += category * (categorySize + categoryGapWidth);
/*  673 */     return result;
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
/*      */   public double getCategoryMiddle(int category, int categoryCount, Rectangle2D area, RectangleEdge edge) {
/*  692 */     if (category < 0 || category >= categoryCount) {
/*  693 */       throw new IllegalArgumentException("Invalid category index: " + category);
/*      */     }
/*      */ 
/*      */     
/*  697 */     return getCategoryStart(category, categoryCount, area, edge) + calculateCategorySize(categoryCount, area, edge) / 2.0D;
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
/*      */   public double getCategoryEnd(int category, int categoryCount, Rectangle2D area, RectangleEdge edge) {
/*  717 */     return getCategoryStart(category, categoryCount, area, edge) + calculateCategorySize(categoryCount, area, edge);
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
/*      */   public double getCategoryMiddle(Comparable category, List categories, Rectangle2D area, RectangleEdge edge) {
/*  739 */     ParamChecks.nullNotPermitted(categories, "categories");
/*  740 */     int categoryIndex = categories.indexOf(category);
/*  741 */     int categoryCount = categories.size();
/*  742 */     return getCategoryMiddle(categoryIndex, categoryCount, area, edge);
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
/*      */   public double getCategorySeriesMiddle(Comparable category, Comparable seriesKey, CategoryDataset dataset, double itemMargin, Rectangle2D area, RectangleEdge edge) {
/*  764 */     int categoryIndex = dataset.getColumnIndex(category);
/*  765 */     int categoryCount = dataset.getColumnCount();
/*  766 */     int seriesIndex = dataset.getRowIndex(seriesKey);
/*  767 */     int seriesCount = dataset.getRowCount();
/*  768 */     double start = getCategoryStart(categoryIndex, categoryCount, area, edge);
/*      */     
/*  770 */     double end = getCategoryEnd(categoryIndex, categoryCount, area, edge);
/*  771 */     double width = end - start;
/*  772 */     if (seriesCount == 1) {
/*  773 */       return start + width / 2.0D;
/*      */     }
/*      */     
/*  776 */     double gap = width * itemMargin / (seriesCount - 1);
/*  777 */     double ww = width * (1.0D - itemMargin) / seriesCount;
/*  778 */     return start + seriesIndex * (ww + gap) + ww / 2.0D;
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
/*      */   public double getCategorySeriesMiddle(int categoryIndex, int categoryCount, int seriesIndex, int seriesCount, double itemMargin, Rectangle2D area, RectangleEdge edge) {
/*  802 */     double start = getCategoryStart(categoryIndex, categoryCount, area, edge);
/*      */     
/*  804 */     double end = getCategoryEnd(categoryIndex, categoryCount, area, edge);
/*  805 */     double width = end - start;
/*  806 */     if (seriesCount == 1) {
/*  807 */       return start + width / 2.0D;
/*      */     }
/*      */     
/*  810 */     double gap = width * itemMargin / (seriesCount - 1);
/*  811 */     double ww = width * (1.0D - itemMargin) / seriesCount;
/*  812 */     return start + seriesIndex * (ww + gap) + ww / 2.0D;
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
/*      */   protected double calculateCategorySize(int categoryCount, Rectangle2D area, RectangleEdge edge) {
/*  829 */     double result, available = 0.0D;
/*      */     
/*  831 */     if (edge == RectangleEdge.TOP || edge == RectangleEdge.BOTTOM) {
/*  832 */       available = area.getWidth();
/*      */     }
/*  834 */     else if (edge == RectangleEdge.LEFT || edge == RectangleEdge.RIGHT) {
/*      */       
/*  836 */       available = area.getHeight();
/*      */     } 
/*  838 */     if (categoryCount > 1) {
/*      */       
/*  840 */       result = available * (1.0D - getLowerMargin() - getUpperMargin() - getCategoryMargin());
/*  841 */       result /= categoryCount;
/*      */     } else {
/*      */       
/*  844 */       result = available * (1.0D - getLowerMargin() - getUpperMargin());
/*      */     } 
/*  846 */     return result;
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
/*      */   protected double calculateCategoryGapSize(int categoryCount, Rectangle2D area, RectangleEdge edge) {
/*  862 */     double result = 0.0D;
/*  863 */     double available = 0.0D;
/*      */     
/*  865 */     if (edge == RectangleEdge.TOP || edge == RectangleEdge.BOTTOM) {
/*  866 */       available = area.getWidth();
/*      */     }
/*  868 */     else if (edge == RectangleEdge.LEFT || edge == RectangleEdge.RIGHT) {
/*      */       
/*  870 */       available = area.getHeight();
/*      */     } 
/*      */     
/*  873 */     if (categoryCount > 1) {
/*  874 */       result = available * getCategoryMargin() / (categoryCount - 1);
/*      */     }
/*  876 */     return result;
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
/*      */   public AxisSpace reserveSpace(Graphics2D g2, Plot plot, Rectangle2D plotArea, RectangleEdge edge, AxisSpace space) {
/*  895 */     if (space == null) {
/*  896 */       space = new AxisSpace();
/*      */     }
/*      */ 
/*      */     
/*  900 */     if (!isVisible()) {
/*  901 */       return space;
/*      */     }
/*      */ 
/*      */     
/*  905 */     double tickLabelHeight = 0.0D;
/*  906 */     double tickLabelWidth = 0.0D;
/*  907 */     if (isTickLabelsVisible()) {
/*  908 */       g2.setFont(getTickLabelFont());
/*  909 */       AxisState state = new AxisState();
/*      */       
/*  911 */       refreshTicks(g2, state, plotArea, edge);
/*  912 */       if (edge == RectangleEdge.TOP) {
/*  913 */         tickLabelHeight = state.getMax();
/*      */       }
/*  915 */       else if (edge == RectangleEdge.BOTTOM) {
/*  916 */         tickLabelHeight = state.getMax();
/*      */       }
/*  918 */       else if (edge == RectangleEdge.LEFT) {
/*  919 */         tickLabelWidth = state.getMax();
/*      */       }
/*  921 */       else if (edge == RectangleEdge.RIGHT) {
/*  922 */         tickLabelWidth = state.getMax();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  927 */     Rectangle2D labelEnclosure = getLabelEnclosure(g2, edge);
/*      */     
/*  929 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  930 */       double labelHeight = labelEnclosure.getHeight();
/*  931 */       space.add(labelHeight + tickLabelHeight + this.categoryLabelPositionOffset, edge);
/*      */     
/*      */     }
/*  934 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  935 */       double labelWidth = labelEnclosure.getWidth();
/*  936 */       space.add(labelWidth + tickLabelWidth + this.categoryLabelPositionOffset, edge);
/*      */     } 
/*      */     
/*  939 */     return space;
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
/*      */   public void configure() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState) {
/*  972 */     if (!isVisible()) {
/*  973 */       return new AxisState(cursor);
/*      */     }
/*      */     
/*  976 */     if (isAxisLineVisible()) {
/*  977 */       drawAxisLine(g2, cursor, dataArea, edge);
/*      */     }
/*  979 */     AxisState state = new AxisState(cursor);
/*  980 */     if (isTickMarksVisible()) {
/*  981 */       drawTickMarks(g2, cursor, dataArea, edge, state);
/*      */     }
/*      */     
/*  984 */     createAndAddEntity(cursor, state, dataArea, edge, plotState);
/*      */ 
/*      */     
/*  987 */     state = drawCategoryLabels(g2, plotArea, dataArea, edge, state, plotState);
/*      */     
/*  989 */     if (getAttributedLabel() != null) {
/*  990 */       state = drawAttributedLabel(getAttributedLabel(), g2, plotArea, dataArea, edge, state);
/*      */     }
/*      */     else {
/*      */       
/*  994 */       state = drawLabel(getLabel(), g2, plotArea, dataArea, edge, state);
/*      */     } 
/*  996 */     return state;
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
/*      */   protected AxisState drawCategoryLabels(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, AxisState state, PlotRenderingInfo plotState) {
/* 1018 */     ParamChecks.nullNotPermitted(state, "state");
/* 1019 */     if (!isTickLabelsVisible()) {
/* 1020 */       return state;
/*      */     }
/*      */     
/* 1023 */     List ticks = refreshTicks(g2, state, plotArea, edge);
/* 1024 */     state.setTicks(ticks);
/* 1025 */     int categoryIndex = 0;
/* 1026 */     Iterator<CategoryTick> iterator = ticks.iterator();
/* 1027 */     while (iterator.hasNext()) {
/* 1028 */       CategoryTick tick = iterator.next();
/* 1029 */       g2.setFont(getTickLabelFont(tick.getCategory()));
/* 1030 */       g2.setPaint(getTickLabelPaint(tick.getCategory()));
/*      */ 
/*      */       
/* 1033 */       CategoryLabelPosition position = this.categoryLabelPositions.getLabelPosition(edge);
/* 1034 */       double x0 = 0.0D;
/* 1035 */       double x1 = 0.0D;
/* 1036 */       double y0 = 0.0D;
/* 1037 */       double y1 = 0.0D;
/* 1038 */       if (edge == RectangleEdge.TOP) {
/* 1039 */         x0 = getCategoryStart(categoryIndex, ticks.size(), dataArea, edge);
/*      */         
/* 1041 */         x1 = getCategoryEnd(categoryIndex, ticks.size(), dataArea, edge);
/*      */         
/* 1043 */         y1 = state.getCursor() - this.categoryLabelPositionOffset;
/* 1044 */         y0 = y1 - state.getMax();
/*      */       }
/* 1046 */       else if (edge == RectangleEdge.BOTTOM) {
/* 1047 */         x0 = getCategoryStart(categoryIndex, ticks.size(), dataArea, edge);
/*      */         
/* 1049 */         x1 = getCategoryEnd(categoryIndex, ticks.size(), dataArea, edge);
/*      */         
/* 1051 */         y0 = state.getCursor() + this.categoryLabelPositionOffset;
/* 1052 */         y1 = y0 + state.getMax();
/*      */       }
/* 1054 */       else if (edge == RectangleEdge.LEFT) {
/* 1055 */         y0 = getCategoryStart(categoryIndex, ticks.size(), dataArea, edge);
/*      */         
/* 1057 */         y1 = getCategoryEnd(categoryIndex, ticks.size(), dataArea, edge);
/*      */         
/* 1059 */         x1 = state.getCursor() - this.categoryLabelPositionOffset;
/* 1060 */         x0 = x1 - state.getMax();
/*      */       }
/* 1062 */       else if (edge == RectangleEdge.RIGHT) {
/* 1063 */         y0 = getCategoryStart(categoryIndex, ticks.size(), dataArea, edge);
/*      */         
/* 1065 */         y1 = getCategoryEnd(categoryIndex, ticks.size(), dataArea, edge);
/*      */         
/* 1067 */         x0 = state.getCursor() + this.categoryLabelPositionOffset;
/* 1068 */         x1 = x0 - state.getMax();
/*      */       } 
/* 1070 */       Rectangle2D area = new Rectangle2D.Double(x0, y0, x1 - x0, y1 - y0);
/*      */       
/* 1072 */       Point2D anchorPoint = RectangleAnchor.coordinates(area, position
/* 1073 */           .getCategoryAnchor());
/* 1074 */       TextBlock block = tick.getLabel();
/* 1075 */       block.draw(g2, (float)anchorPoint.getX(), 
/* 1076 */           (float)anchorPoint.getY(), position.getLabelAnchor(), 
/* 1077 */           (float)anchorPoint.getX(), (float)anchorPoint.getY(), position
/* 1078 */           .getAngle());
/* 1079 */       Shape bounds = block.calculateBounds(g2, 
/* 1080 */           (float)anchorPoint.getX(), (float)anchorPoint.getY(), position
/* 1081 */           .getLabelAnchor(), (float)anchorPoint.getX(), 
/* 1082 */           (float)anchorPoint.getY(), position.getAngle());
/* 1083 */       if (plotState != null && plotState.getOwner() != null) {
/*      */         
/* 1085 */         EntityCollection entities = plotState.getOwner().getEntityCollection();
/* 1086 */         if (entities != null) {
/* 1087 */           String tooltip = getCategoryLabelToolTip(tick
/* 1088 */               .getCategory());
/* 1089 */           String url = getCategoryLabelURL(tick.getCategory());
/* 1090 */           entities.add((ChartEntity)new CategoryLabelEntity(tick.getCategory(), bounds, tooltip, url));
/*      */         } 
/*      */       } 
/*      */       
/* 1094 */       categoryIndex++;
/*      */     } 
/*      */     
/* 1097 */     if (edge.equals(RectangleEdge.TOP)) {
/* 1098 */       double h = state.getMax() + this.categoryLabelPositionOffset;
/* 1099 */       state.cursorUp(h);
/*      */     }
/* 1101 */     else if (edge.equals(RectangleEdge.BOTTOM)) {
/* 1102 */       double h = state.getMax() + this.categoryLabelPositionOffset;
/* 1103 */       state.cursorDown(h);
/*      */     }
/* 1105 */     else if (edge == RectangleEdge.LEFT) {
/* 1106 */       double w = state.getMax() + this.categoryLabelPositionOffset;
/* 1107 */       state.cursorLeft(w);
/*      */     }
/* 1109 */     else if (edge == RectangleEdge.RIGHT) {
/* 1110 */       double w = state.getMax() + this.categoryLabelPositionOffset;
/* 1111 */       state.cursorRight(w);
/*      */     } 
/* 1113 */     return state;
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
/*      */   public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {
/* 1130 */     List<Tick> ticks = new ArrayList();
/*      */ 
/*      */     
/* 1133 */     if (dataArea.getHeight() <= 0.0D || dataArea.getWidth() < 0.0D) {
/* 1134 */       return ticks;
/*      */     }
/*      */     
/* 1137 */     CategoryPlot plot = (CategoryPlot)getPlot();
/* 1138 */     List categories = plot.getCategoriesForAxis(this);
/* 1139 */     double max = 0.0D;
/*      */     
/* 1141 */     if (categories != null) {
/*      */       float l;
/* 1143 */       CategoryLabelPosition position = this.categoryLabelPositions.getLabelPosition(edge);
/* 1144 */       float r = this.maximumCategoryLabelWidthRatio;
/* 1145 */       if (r <= 0.0D) {
/* 1146 */         r = position.getWidthRatio();
/*      */       }
/*      */ 
/*      */       
/* 1150 */       if (position.getWidthType() == CategoryLabelWidthType.CATEGORY) {
/* 1151 */         l = (float)calculateCategorySize(categories.size(), dataArea, edge);
/*      */ 
/*      */       
/*      */       }
/* 1155 */       else if (RectangleEdge.isLeftOrRight(edge)) {
/* 1156 */         l = (float)dataArea.getWidth();
/*      */       } else {
/*      */         
/* 1159 */         l = (float)dataArea.getHeight();
/*      */       } 
/*      */       
/* 1162 */       int categoryIndex = 0;
/* 1163 */       Iterator<Comparable> iterator = categories.iterator();
/* 1164 */       while (iterator.hasNext()) {
/* 1165 */         Comparable category = iterator.next();
/* 1166 */         g2.setFont(getTickLabelFont(category));
/* 1167 */         TextBlock label = createLabel(category, l * r, edge, g2);
/* 1168 */         if (edge == RectangleEdge.TOP || edge == RectangleEdge.BOTTOM) {
/* 1169 */           max = Math.max(max, calculateTextBlockHeight(label, position, g2));
/*      */         
/*      */         }
/* 1172 */         else if (edge == RectangleEdge.LEFT || edge == RectangleEdge.RIGHT) {
/*      */           
/* 1174 */           max = Math.max(max, calculateTextBlockWidth(label, position, g2));
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1179 */         Tick tick = new CategoryTick(category, label, position.getLabelAnchor(), position.getRotationAnchor(), position.getAngle());
/* 1180 */         ticks.add(tick);
/* 1181 */         categoryIndex++;
/*      */       } 
/*      */     } 
/* 1184 */     state.setMax(max);
/* 1185 */     return ticks;
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
/*      */   public void drawTickMarks(Graphics2D g2, double cursor, Rectangle2D dataArea, RectangleEdge edge, AxisState state) {
/* 1203 */     Plot p = getPlot();
/* 1204 */     if (p == null) {
/*      */       return;
/*      */     }
/* 1207 */     CategoryPlot plot = (CategoryPlot)p;
/* 1208 */     double il = getTickMarkInsideLength();
/* 1209 */     double ol = getTickMarkOutsideLength();
/* 1210 */     Line2D line = new Line2D.Double();
/* 1211 */     List categories = plot.getCategoriesForAxis(this);
/* 1212 */     g2.setPaint(getTickMarkPaint());
/* 1213 */     g2.setStroke(getTickMarkStroke());
/* 1214 */     Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
/* 1215 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/*      */     
/* 1217 */     if (edge.equals(RectangleEdge.TOP)) {
/* 1218 */       Iterator<Comparable> iterator = categories.iterator();
/* 1219 */       while (iterator.hasNext()) {
/* 1220 */         Comparable key = iterator.next();
/* 1221 */         double x = getCategoryMiddle(key, categories, dataArea, edge);
/* 1222 */         line.setLine(x, cursor, x, cursor + il);
/* 1223 */         g2.draw(line);
/* 1224 */         line.setLine(x, cursor, x, cursor - ol);
/* 1225 */         g2.draw(line);
/*      */       } 
/* 1227 */       state.cursorUp(ol);
/* 1228 */     } else if (edge.equals(RectangleEdge.BOTTOM)) {
/* 1229 */       Iterator<Comparable> iterator = categories.iterator();
/* 1230 */       while (iterator.hasNext()) {
/* 1231 */         Comparable key = iterator.next();
/* 1232 */         double x = getCategoryMiddle(key, categories, dataArea, edge);
/* 1233 */         line.setLine(x, cursor, x, cursor - il);
/* 1234 */         g2.draw(line);
/* 1235 */         line.setLine(x, cursor, x, cursor + ol);
/* 1236 */         g2.draw(line);
/*      */       } 
/* 1238 */       state.cursorDown(ol);
/* 1239 */     } else if (edge.equals(RectangleEdge.LEFT)) {
/* 1240 */       Iterator<Comparable> iterator = categories.iterator();
/* 1241 */       while (iterator.hasNext()) {
/* 1242 */         Comparable key = iterator.next();
/* 1243 */         double y = getCategoryMiddle(key, categories, dataArea, edge);
/* 1244 */         line.setLine(cursor, y, cursor + il, y);
/* 1245 */         g2.draw(line);
/* 1246 */         line.setLine(cursor, y, cursor - ol, y);
/* 1247 */         g2.draw(line);
/*      */       } 
/* 1249 */       state.cursorLeft(ol);
/* 1250 */     } else if (edge.equals(RectangleEdge.RIGHT)) {
/* 1251 */       Iterator<Comparable> iterator = categories.iterator();
/* 1252 */       while (iterator.hasNext()) {
/* 1253 */         Comparable key = iterator.next();
/* 1254 */         double y = getCategoryMiddle(key, categories, dataArea, edge);
/* 1255 */         line.setLine(cursor, y, cursor - il, y);
/* 1256 */         g2.draw(line);
/* 1257 */         line.setLine(cursor, y, cursor + ol, y);
/* 1258 */         g2.draw(line);
/*      */       } 
/* 1260 */       state.cursorRight(ol);
/*      */     } 
/* 1262 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
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
/*      */   protected TextBlock createLabel(Comparable category, float width, RectangleEdge edge, Graphics2D g2) {
/* 1277 */     TextBlock label = TextUtilities.createTextBlock(category.toString(), 
/* 1278 */         getTickLabelFont(category), getTickLabelPaint(category), width, this.maximumCategoryLabelLines, (TextMeasurer)new G2TextMeasurer(g2));
/*      */     
/* 1280 */     return label;
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
/*      */   protected double calculateTextBlockWidth(TextBlock block, CategoryLabelPosition position, Graphics2D g2) {
/* 1294 */     RectangleInsets insets = getTickLabelInsets();
/* 1295 */     Size2D size = block.calculateDimensions(g2);
/*      */     
/* 1297 */     Rectangle2D box = new Rectangle2D.Double(0.0D, 0.0D, size.getWidth(), size.getHeight());
/* 1298 */     Shape rotatedBox = ShapeUtilities.rotateShape(box, position.getAngle(), 0.0F, 0.0F);
/*      */ 
/*      */     
/* 1301 */     double w = rotatedBox.getBounds2D().getWidth() + insets.getLeft() + insets.getRight();
/* 1302 */     return w;
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
/*      */   protected double calculateTextBlockHeight(TextBlock block, CategoryLabelPosition position, Graphics2D g2) {
/* 1316 */     RectangleInsets insets = getTickLabelInsets();
/* 1317 */     Size2D size = block.calculateDimensions(g2);
/*      */     
/* 1319 */     Rectangle2D box = new Rectangle2D.Double(0.0D, 0.0D, size.getWidth(), size.getHeight());
/* 1320 */     Shape rotatedBox = ShapeUtilities.rotateShape(box, position.getAngle(), 0.0F, 0.0F);
/*      */ 
/*      */     
/* 1323 */     double h = rotatedBox.getBounds2D().getHeight() + insets.getTop() + insets.getBottom();
/* 1324 */     return h;
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
/*      */   public Object clone() throws CloneNotSupportedException {
/* 1337 */     CategoryAxis clone = (CategoryAxis)super.clone();
/* 1338 */     clone.tickLabelFontMap = new HashMap<Object, Object>(this.tickLabelFontMap);
/* 1339 */     clone.tickLabelPaintMap = new HashMap<Object, Object>(this.tickLabelPaintMap);
/* 1340 */     clone.categoryLabelToolTips = new HashMap<Object, Object>(this.categoryLabelToolTips);
/* 1341 */     clone.categoryLabelURLs = new HashMap<Object, Object>(this.categoryLabelToolTips);
/* 1342 */     return clone;
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
/* 1354 */     if (obj == this) {
/* 1355 */       return true;
/*      */     }
/* 1357 */     if (!(obj instanceof CategoryAxis)) {
/* 1358 */       return false;
/*      */     }
/* 1360 */     if (!super.equals(obj)) {
/* 1361 */       return false;
/*      */     }
/* 1363 */     CategoryAxis that = (CategoryAxis)obj;
/* 1364 */     if (that.lowerMargin != this.lowerMargin) {
/* 1365 */       return false;
/*      */     }
/* 1367 */     if (that.upperMargin != this.upperMargin) {
/* 1368 */       return false;
/*      */     }
/* 1370 */     if (that.categoryMargin != this.categoryMargin) {
/* 1371 */       return false;
/*      */     }
/* 1373 */     if (that.maximumCategoryLabelWidthRatio != this.maximumCategoryLabelWidthRatio)
/*      */     {
/* 1375 */       return false;
/*      */     }
/* 1377 */     if (that.categoryLabelPositionOffset != this.categoryLabelPositionOffset)
/*      */     {
/* 1379 */       return false;
/*      */     }
/* 1381 */     if (!ObjectUtilities.equal(that.categoryLabelPositions, this.categoryLabelPositions))
/*      */     {
/* 1383 */       return false;
/*      */     }
/* 1385 */     if (!ObjectUtilities.equal(that.categoryLabelToolTips, this.categoryLabelToolTips))
/*      */     {
/* 1387 */       return false;
/*      */     }
/* 1389 */     if (!ObjectUtilities.equal(this.categoryLabelURLs, that.categoryLabelURLs))
/*      */     {
/* 1391 */       return false;
/*      */     }
/* 1393 */     if (!ObjectUtilities.equal(this.tickLabelFontMap, that.tickLabelFontMap))
/*      */     {
/* 1395 */       return false;
/*      */     }
/* 1397 */     if (!equalPaintMaps(this.tickLabelPaintMap, that.tickLabelPaintMap)) {
/* 1398 */       return false;
/*      */     }
/* 1400 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1410 */     return super.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 1421 */     stream.defaultWriteObject();
/* 1422 */     writePaintMap(this.tickLabelPaintMap, stream);
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
/*      */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 1435 */     stream.defaultReadObject();
/* 1436 */     this.tickLabelPaintMap = readPaintMap(stream);
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
/*      */   private Map readPaintMap(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 1454 */     boolean isNull = in.readBoolean();
/* 1455 */     if (isNull) {
/* 1456 */       return null;
/*      */     }
/* 1458 */     Map<Object, Object> result = new HashMap<Object, Object>();
/* 1459 */     int count = in.readInt();
/* 1460 */     for (int i = 0; i < count; i++) {
/* 1461 */       Comparable category = (Comparable)in.readObject();
/* 1462 */       Paint paint = SerialUtilities.readPaint(in);
/* 1463 */       result.put(category, paint);
/*      */     } 
/* 1465 */     return result;
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
/*      */   private void writePaintMap(Map map, ObjectOutputStream out) throws IOException {
/* 1481 */     if (map == null) {
/* 1482 */       out.writeBoolean(true);
/*      */     } else {
/*      */       
/* 1485 */       out.writeBoolean(false);
/* 1486 */       Set keys = map.keySet();
/* 1487 */       int count = keys.size();
/* 1488 */       out.writeInt(count);
/* 1489 */       Iterator<Comparable> iterator = keys.iterator();
/* 1490 */       while (iterator.hasNext()) {
/* 1491 */         Comparable key = iterator.next();
/* 1492 */         out.writeObject(key);
/* 1493 */         SerialUtilities.writePaint((Paint)map.get(key), out);
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
/*      */   private boolean equalPaintMaps(Map map1, Map map2) {
/* 1508 */     if (map1.size() != map2.size()) {
/* 1509 */       return false;
/*      */     }
/* 1511 */     Set entries = map1.entrySet();
/* 1512 */     Iterator<Map.Entry> iterator = entries.iterator();
/* 1513 */     while (iterator.hasNext()) {
/* 1514 */       Map.Entry entry = iterator.next();
/* 1515 */       Paint p1 = (Paint)entry.getValue();
/* 1516 */       Paint p2 = (Paint)map2.get(entry.getKey());
/* 1517 */       if (!PaintUtilities.equal(p1, p2)) {
/* 1518 */         return false;
/*      */       }
/*      */     } 
/* 1521 */     return true;
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
/*      */   protected AxisState drawCategoryLabels(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge, AxisState state, PlotRenderingInfo plotState) {
/* 1544 */     return drawCategoryLabels(g2, dataArea, dataArea, edge, state, plotState);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/CategoryAxis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */