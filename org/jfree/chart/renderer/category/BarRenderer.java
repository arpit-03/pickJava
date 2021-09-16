/*      */ package org.jfree.chart.renderer.category;
/*      */ 
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import org.jfree.chart.LegendItem;
/*      */ import org.jfree.chart.axis.CategoryAxis;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.labels.CategoryItemLabelGenerator;
/*      */ import org.jfree.chart.labels.ItemLabelAnchor;
/*      */ import org.jfree.chart.labels.ItemLabelPosition;
/*      */ import org.jfree.chart.plot.CategoryPlot;
/*      */ import org.jfree.chart.plot.PlotOrientation;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.category.CategoryDataset;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.ui.GradientPaintTransformer;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.StandardGradientPaintTransformer;
/*      */ import org.jfree.util.ObjectUtilities;
/*      */ import org.jfree.util.PaintUtilities;
/*      */ import org.jfree.util.PublicCloneable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BarRenderer
/*      */   extends AbstractCategoryItemRenderer
/*      */   implements Cloneable, PublicCloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 6000649414965887481L;
/*      */   public static final double DEFAULT_ITEM_MARGIN = 0.2D;
/*      */   public static final double BAR_OUTLINE_WIDTH_THRESHOLD = 3.0D;
/*  164 */   private static BarPainter defaultBarPainter = new GradientBarPainter();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BarPainter getDefaultBarPainter() {
/*  174 */     return defaultBarPainter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDefaultBarPainter(BarPainter painter) {
/*  185 */     ParamChecks.nullNotPermitted(painter, "painter");
/*  186 */     defaultBarPainter = painter;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean defaultShadowsVisible = true;
/*      */   
/*      */   private double itemMargin;
/*      */   
/*      */   private boolean drawBarOutline;
/*      */   
/*      */   private double maximumBarWidth;
/*      */   
/*      */   private double minimumBarLength;
/*      */   private GradientPaintTransformer gradientPaintTransformer;
/*      */   private ItemLabelPosition positiveItemLabelPositionFallback;
/*      */   private ItemLabelPosition negativeItemLabelPositionFallback;
/*      */   
/*      */   public static boolean getDefaultShadowsVisible() {
/*  204 */     return defaultShadowsVisible;
/*      */   }
/*      */ 
/*      */   
/*      */   private double upperClip;
/*      */   
/*      */   private double lowerClip;
/*      */   
/*      */   private double base;
/*      */   
/*      */   private boolean includeBaseInRange;
/*      */   
/*      */   public static void setDefaultShadowsVisible(boolean visible) {
/*  217 */     defaultShadowsVisible = visible;
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
/*      */   private BarPainter barPainter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shadowsVisible;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Paint shadowPaint;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double shadowXOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double shadowYOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BarRenderer() {
/*  307 */     this.base = 0.0D;
/*  308 */     this.includeBaseInRange = true;
/*  309 */     this.itemMargin = 0.2D;
/*  310 */     this.drawBarOutline = false;
/*  311 */     this.maximumBarWidth = 1.0D;
/*      */     
/*  313 */     this.positiveItemLabelPositionFallback = null;
/*  314 */     this.negativeItemLabelPositionFallback = null;
/*  315 */     this.gradientPaintTransformer = (GradientPaintTransformer)new StandardGradientPaintTransformer();
/*  316 */     this.minimumBarLength = 0.0D;
/*  317 */     setBaseLegendShape(new Rectangle2D.Double(-4.0D, -4.0D, 8.0D, 8.0D));
/*  318 */     this.barPainter = getDefaultBarPainter();
/*  319 */     this.shadowsVisible = getDefaultShadowsVisible();
/*  320 */     this.shadowPaint = Color.gray;
/*  321 */     this.shadowXOffset = 4.0D;
/*  322 */     this.shadowYOffset = 4.0D;
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
/*      */   public double getBase() {
/*  334 */     return this.base;
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
/*      */   public void setBase(double base) {
/*  346 */     this.base = base;
/*  347 */     fireChangeEvent();
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
/*      */   public double getItemMargin() {
/*  359 */     return this.itemMargin;
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
/*      */   public void setItemMargin(double percent) {
/*  373 */     this.itemMargin = percent;
/*  374 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDrawBarOutline() {
/*  385 */     return this.drawBarOutline;
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
/*      */   public void setDrawBarOutline(boolean draw) {
/*  397 */     this.drawBarOutline = draw;
/*  398 */     fireChangeEvent();
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
/*      */   public double getMaximumBarWidth() {
/*  410 */     return this.maximumBarWidth;
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
/*      */   public void setMaximumBarWidth(double percent) {
/*  423 */     this.maximumBarWidth = percent;
/*  424 */     fireChangeEvent();
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
/*      */   public double getMinimumBarLength() {
/*  436 */     return this.minimumBarLength;
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
/*      */   public void setMinimumBarLength(double min) {
/*  454 */     if (min < 0.0D) {
/*  455 */       throw new IllegalArgumentException("Requires 'min' >= 0.0");
/*      */     }
/*  457 */     this.minimumBarLength = min;
/*  458 */     fireChangeEvent();
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
/*      */   public GradientPaintTransformer getGradientPaintTransformer() {
/*  470 */     return this.gradientPaintTransformer;
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
/*      */   public void setGradientPaintTransformer(GradientPaintTransformer transformer) {
/*  483 */     this.gradientPaintTransformer = transformer;
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
/*      */   public ItemLabelPosition getPositiveItemLabelPositionFallback() {
/*  496 */     return this.positiveItemLabelPositionFallback;
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
/*      */   public void setPositiveItemLabelPositionFallback(ItemLabelPosition position) {
/*  510 */     this.positiveItemLabelPositionFallback = position;
/*  511 */     fireChangeEvent();
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
/*      */   public ItemLabelPosition getNegativeItemLabelPositionFallback() {
/*  523 */     return this.negativeItemLabelPositionFallback;
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
/*      */   public void setNegativeItemLabelPositionFallback(ItemLabelPosition position) {
/*  537 */     this.negativeItemLabelPositionFallback = position;
/*  538 */     fireChangeEvent();
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
/*      */   public boolean getIncludeBaseInRange() {
/*  554 */     return this.includeBaseInRange;
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
/*      */   public void setIncludeBaseInRange(boolean include) {
/*  570 */     if (this.includeBaseInRange != include) {
/*  571 */       this.includeBaseInRange = include;
/*  572 */       fireChangeEvent();
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
/*      */   public BarPainter getBarPainter() {
/*  586 */     return this.barPainter;
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
/*      */   public void setBarPainter(BarPainter painter) {
/*  600 */     ParamChecks.nullNotPermitted(painter, "painter");
/*  601 */     this.barPainter = painter;
/*  602 */     fireChangeEvent();
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
/*      */   public boolean getShadowsVisible() {
/*  614 */     return this.shadowsVisible;
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
/*      */   public void setShadowVisible(boolean visible) {
/*  626 */     this.shadowsVisible = visible;
/*  627 */     fireChangeEvent();
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
/*      */   public Paint getShadowPaint() {
/*  640 */     return this.shadowPaint;
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
/*      */   public void setShadowPaint(Paint paint) {
/*  654 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  655 */     this.shadowPaint = paint;
/*  656 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getShadowXOffset() {
/*  667 */     return this.shadowXOffset;
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
/*      */   public void setShadowXOffset(double offset) {
/*  679 */     this.shadowXOffset = offset;
/*  680 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getShadowYOffset() {
/*  691 */     return this.shadowYOffset;
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
/*      */   public void setShadowYOffset(double offset) {
/*  703 */     this.shadowYOffset = offset;
/*  704 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getLowerClip() {
/*  715 */     return this.lowerClip;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getUpperClip() {
/*  726 */     return this.upperClip;
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
/*      */   public CategoryItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, CategoryPlot plot, int rendererIndex, PlotRenderingInfo info) {
/*  747 */     CategoryItemRendererState state = super.initialise(g2, dataArea, plot, rendererIndex, info);
/*      */ 
/*      */ 
/*      */     
/*  751 */     ValueAxis rangeAxis = plot.getRangeAxisForDataset(rendererIndex);
/*  752 */     this.lowerClip = rangeAxis.getRange().getLowerBound();
/*  753 */     this.upperClip = rangeAxis.getRange().getUpperBound();
/*      */ 
/*      */     
/*  756 */     calculateBarWidth(plot, dataArea, rendererIndex, state);
/*      */     
/*  758 */     return state;
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
/*      */   protected void calculateBarWidth(CategoryPlot plot, Rectangle2D dataArea, int rendererIndex, CategoryItemRendererState state) {
/*  775 */     CategoryAxis domainAxis = getDomainAxis(plot, rendererIndex);
/*  776 */     CategoryDataset dataset = plot.getDataset(rendererIndex);
/*  777 */     if (dataset != null) {
/*  778 */       int columns = dataset.getColumnCount();
/*      */       
/*  780 */       int rows = (state.getVisibleSeriesCount() >= 0) ? state.getVisibleSeriesCount() : dataset.getRowCount();
/*  781 */       double space = 0.0D;
/*  782 */       PlotOrientation orientation = plot.getOrientation();
/*  783 */       if (orientation == PlotOrientation.HORIZONTAL) {
/*  784 */         space = dataArea.getHeight();
/*      */       }
/*  786 */       else if (orientation == PlotOrientation.VERTICAL) {
/*  787 */         space = dataArea.getWidth();
/*      */       } 
/*  789 */       double maxWidth = space * getMaximumBarWidth();
/*  790 */       double categoryMargin = 0.0D;
/*  791 */       double currentItemMargin = 0.0D;
/*  792 */       if (columns > 1) {
/*  793 */         categoryMargin = domainAxis.getCategoryMargin();
/*      */       }
/*  795 */       if (rows > 1) {
/*  796 */         currentItemMargin = getItemMargin();
/*      */       }
/*      */       
/*  799 */       double used = space * (1.0D - domainAxis.getLowerMargin() - domainAxis.getUpperMargin() - categoryMargin - currentItemMargin);
/*      */       
/*  801 */       if (rows * columns > 0) {
/*  802 */         state.setBarWidth(Math.min(used / (rows * columns), maxWidth));
/*      */       } else {
/*      */         
/*  805 */         state.setBarWidth(Math.min(used, maxWidth));
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
/*      */ 
/*      */   
/*      */   protected double calculateBarW0(CategoryPlot plot, PlotOrientation orientation, Rectangle2D dataArea, CategoryAxis domainAxis, CategoryItemRendererState state, int row, int column) {
/*      */     double space;
/*  831 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*  832 */       space = dataArea.getHeight();
/*      */     } else {
/*      */       
/*  835 */       space = dataArea.getWidth();
/*      */     } 
/*  837 */     double barW0 = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, plot
/*  838 */         .getDomainAxisEdge());
/*      */     
/*  840 */     int seriesCount = (state.getVisibleSeriesCount() >= 0) ? state.getVisibleSeriesCount() : getRowCount();
/*  841 */     int categoryCount = getColumnCount();
/*  842 */     if (seriesCount > 1) {
/*  843 */       double seriesGap = space * getItemMargin() / (categoryCount * (seriesCount - 1));
/*      */       
/*  845 */       double seriesW = calculateSeriesWidth(space, domainAxis, categoryCount, seriesCount);
/*      */ 
/*      */       
/*  848 */       barW0 = barW0 + row * (seriesW + seriesGap) + seriesW / 2.0D - state.getBarWidth() / 2.0D;
/*      */     }
/*      */     else {
/*      */       
/*  852 */       barW0 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot.getDomainAxisEdge()) - state.getBarWidth() / 2.0D;
/*      */     } 
/*      */     
/*  855 */     return barW0;
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
/*      */   protected double[] calculateBarL0L1(double value) {
/*  867 */     double lclip = getLowerClip();
/*  868 */     double uclip = getUpperClip();
/*  869 */     double barLow = Math.min(this.base, value);
/*  870 */     double barHigh = Math.max(this.base, value);
/*  871 */     if (barHigh < lclip) {
/*  872 */       return null;
/*      */     }
/*  874 */     if (barLow > uclip) {
/*  875 */       return null;
/*      */     }
/*  877 */     barLow = Math.max(barLow, lclip);
/*  878 */     barHigh = Math.min(barHigh, uclip);
/*  879 */     return new double[] { barLow, barHigh };
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
/*      */   public Range findRangeBounds(CategoryDataset dataset, boolean includeInterval) {
/*  897 */     if (dataset == null) {
/*  898 */       return null;
/*      */     }
/*  900 */     Range result = super.findRangeBounds(dataset, includeInterval);
/*  901 */     if (result != null && 
/*  902 */       this.includeBaseInRange) {
/*  903 */       result = Range.expandToInclude(result, this.base);
/*      */     }
/*      */     
/*  906 */     return result;
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
/*  920 */     CategoryPlot cp = getPlot();
/*  921 */     if (cp == null) {
/*  922 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  926 */     if (!isSeriesVisible(series) || !isSeriesVisibleInLegend(series)) {
/*  927 */       return null;
/*      */     }
/*      */     
/*  930 */     CategoryDataset dataset = cp.getDataset(datasetIndex);
/*  931 */     String label = getLegendItemLabelGenerator().generateLabel(dataset, series);
/*      */     
/*  933 */     String description = label;
/*  934 */     String toolTipText = null;
/*  935 */     if (getLegendItemToolTipGenerator() != null) {
/*  936 */       toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*      */     }
/*      */     
/*  939 */     String urlText = null;
/*  940 */     if (getLegendItemURLGenerator() != null) {
/*  941 */       urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*      */     }
/*      */     
/*  944 */     Shape shape = lookupLegendShape(series);
/*  945 */     Paint paint = lookupSeriesPaint(series);
/*  946 */     Paint outlinePaint = lookupSeriesOutlinePaint(series);
/*  947 */     Stroke outlineStroke = lookupSeriesOutlineStroke(series);
/*      */ 
/*      */     
/*  950 */     LegendItem result = new LegendItem(label, description, toolTipText, urlText, true, shape, true, paint, isDrawBarOutline(), outlinePaint, outlineStroke, false, new Line2D.Float(), new BasicStroke(1.0F), Color.black);
/*      */ 
/*      */     
/*  953 */     result.setLabelFont(lookupLegendTextFont(series));
/*  954 */     Paint labelPaint = lookupLegendTextPaint(series);
/*  955 */     if (labelPaint != null) {
/*  956 */       result.setLabelPaint(labelPaint);
/*      */     }
/*  958 */     result.setDataset((Dataset)dataset);
/*  959 */     result.setDatasetIndex(datasetIndex);
/*  960 */     result.setSeriesKey(dataset.getRowKey(series));
/*  961 */     result.setSeriesIndex(series);
/*  962 */     if (this.gradientPaintTransformer != null) {
/*  963 */       result.setFillPaintTransformer(this.gradientPaintTransformer);
/*      */     }
/*  965 */     return result;
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
/*      */     RectangleEdge barBase;
/*      */     Rectangle2D bar;
/*  990 */     int visibleRow = state.getVisibleSeriesIndex(row);
/*  991 */     if (visibleRow < 0) {
/*      */       return;
/*      */     }
/*      */     
/*  995 */     Number dataValue = dataset.getValue(row, column);
/*  996 */     if (dataValue == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1000 */     double value = dataValue.doubleValue();
/* 1001 */     PlotOrientation orientation = plot.getOrientation();
/* 1002 */     double barW0 = calculateBarW0(plot, orientation, dataArea, domainAxis, state, visibleRow, column);
/*      */     
/* 1004 */     double[] barL0L1 = calculateBarL0L1(value);
/* 1005 */     if (barL0L1 == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1009 */     RectangleEdge edge = plot.getRangeAxisEdge();
/* 1010 */     double transL0 = rangeAxis.valueToJava2D(barL0L1[0], dataArea, edge);
/* 1011 */     double transL1 = rangeAxis.valueToJava2D(barL0L1[1], dataArea, edge);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1020 */     boolean positive = (value >= this.base);
/* 1021 */     boolean inverted = rangeAxis.isInverted();
/* 1022 */     double barL0 = Math.min(transL0, transL1);
/* 1023 */     double barLength = Math.abs(transL1 - transL0);
/* 1024 */     double barLengthAdj = 0.0D;
/* 1025 */     if (barLength > 0.0D && barLength < getMinimumBarLength()) {
/* 1026 */       barLengthAdj = getMinimumBarLength() - barLength;
/*      */     }
/* 1028 */     double barL0Adj = 0.0D;
/*      */     
/* 1030 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 1031 */       if ((positive && inverted) || (!positive && !inverted)) {
/* 1032 */         barL0Adj = barLengthAdj;
/* 1033 */         barBase = RectangleEdge.RIGHT;
/*      */       } else {
/*      */         
/* 1036 */         barBase = RectangleEdge.LEFT;
/*      */       }
/*      */     
/*      */     }
/* 1040 */     else if ((positive && !inverted) || (!positive && inverted)) {
/* 1041 */       barL0Adj = barLengthAdj;
/* 1042 */       barBase = RectangleEdge.BOTTOM;
/*      */     } else {
/*      */       
/* 1045 */       barBase = RectangleEdge.TOP;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1051 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*      */       
/* 1053 */       bar = new Rectangle2D.Double(barL0 - barL0Adj, barW0, barLength + barLengthAdj, state.getBarWidth());
/*      */     }
/*      */     else {
/*      */       
/* 1057 */       bar = new Rectangle2D.Double(barW0, barL0 - barL0Adj, state.getBarWidth(), barLength + barLengthAdj);
/*      */     } 
/* 1059 */     if (getShadowsVisible()) {
/* 1060 */       this.barPainter.paintBarShadow(g2, this, row, column, bar, barBase, true);
/*      */     }
/*      */     
/* 1063 */     this.barPainter.paintBar(g2, this, row, column, bar, barBase);
/*      */     
/* 1065 */     CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
/*      */     
/* 1067 */     if (generator != null && isItemLabelVisible(row, column)) {
/* 1068 */       drawItemLabel(g2, dataset, row, column, plot, generator, bar, (value < 0.0D));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1073 */     int datasetIndex = plot.indexOf(dataset);
/* 1074 */     updateCrosshairValues(state.getCrosshairState(), dataset
/* 1075 */         .getRowKey(row), dataset.getColumnKey(column), value, datasetIndex, barW0, barL0, orientation);
/*      */ 
/*      */ 
/*      */     
/* 1079 */     EntityCollection entities = state.getEntityCollection();
/* 1080 */     if (entities != null) {
/* 1081 */       addItemEntity(entities, dataset, row, column, bar);
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
/*      */   protected double calculateSeriesWidth(double space, CategoryAxis axis, int categories, int series) {
/* 1099 */     double factor = 1.0D - getItemMargin() - axis.getLowerMargin() - axis.getUpperMargin();
/* 1100 */     if (categories > 1) {
/* 1101 */       factor -= axis.getCategoryMargin();
/*      */     }
/* 1103 */     return space * factor / (categories * series);
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
/*      */   protected void drawItemLabel(Graphics2D g2, CategoryDataset data, int row, int column, CategoryPlot plot, CategoryItemLabelGenerator generator, Rectangle2D bar, boolean negative) {
/*      */     ItemLabelPosition position;
/* 1128 */     String label = generator.generateLabel(data, row, column);
/* 1129 */     if (label == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1133 */     Font labelFont = getItemLabelFont(row, column);
/* 1134 */     g2.setFont(labelFont);
/* 1135 */     Paint paint = getItemLabelPaint(row, column);
/* 1136 */     g2.setPaint(paint);
/*      */ 
/*      */ 
/*      */     
/* 1140 */     if (!negative) {
/* 1141 */       position = getPositiveItemLabelPosition(row, column);
/*      */     } else {
/*      */       
/* 1144 */       position = getNegativeItemLabelPosition(row, column);
/*      */     } 
/*      */ 
/*      */     
/* 1148 */     Point2D anchorPoint = calculateLabelAnchorPoint(position
/* 1149 */         .getItemLabelAnchor(), bar, plot.getOrientation());
/*      */     
/* 1151 */     if (isInternalAnchor(position.getItemLabelAnchor())) {
/* 1152 */       Shape bounds = TextUtilities.calculateRotatedStringBounds(label, g2, 
/* 1153 */           (float)anchorPoint.getX(), (float)anchorPoint.getY(), position
/* 1154 */           .getTextAnchor(), position.getAngle(), position
/* 1155 */           .getRotationAnchor());
/*      */       
/* 1157 */       if (bounds != null && 
/* 1158 */         !bar.contains(bounds.getBounds2D())) {
/* 1159 */         if (!negative) {
/* 1160 */           position = getPositiveItemLabelPositionFallback();
/*      */         } else {
/*      */           
/* 1163 */           position = getNegativeItemLabelPositionFallback();
/*      */         } 
/* 1165 */         if (position != null) {
/* 1166 */           anchorPoint = calculateLabelAnchorPoint(position
/* 1167 */               .getItemLabelAnchor(), bar, plot
/* 1168 */               .getOrientation());
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1175 */     if (position != null) {
/* 1176 */       TextUtilities.drawRotatedString(label, g2, 
/* 1177 */           (float)anchorPoint.getX(), (float)anchorPoint.getY(), position
/* 1178 */           .getTextAnchor(), position.getAngle(), position
/* 1179 */           .getRotationAnchor());
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
/*      */   private Point2D calculateLabelAnchorPoint(ItemLabelAnchor anchor, Rectangle2D bar, PlotOrientation orientation) {
/* 1196 */     Point2D result = null;
/* 1197 */     double offset = getItemLabelAnchorOffset();
/* 1198 */     double x0 = bar.getX() - offset;
/* 1199 */     double x1 = bar.getX();
/* 1200 */     double x2 = bar.getX() + offset;
/* 1201 */     double x3 = bar.getCenterX();
/* 1202 */     double x4 = bar.getMaxX() - offset;
/* 1203 */     double x5 = bar.getMaxX();
/* 1204 */     double x6 = bar.getMaxX() + offset;
/*      */     
/* 1206 */     double y0 = bar.getMaxY() + offset;
/* 1207 */     double y1 = bar.getMaxY();
/* 1208 */     double y2 = bar.getMaxY() - offset;
/* 1209 */     double y3 = bar.getCenterY();
/* 1210 */     double y4 = bar.getMinY() + offset;
/* 1211 */     double y5 = bar.getMinY();
/* 1212 */     double y6 = bar.getMinY() - offset;
/*      */     
/* 1214 */     if (anchor == ItemLabelAnchor.CENTER) {
/* 1215 */       result = new Point2D.Double(x3, y3);
/*      */     }
/* 1217 */     else if (anchor == ItemLabelAnchor.INSIDE1) {
/* 1218 */       result = new Point2D.Double(x4, y4);
/*      */     }
/* 1220 */     else if (anchor == ItemLabelAnchor.INSIDE2) {
/* 1221 */       result = new Point2D.Double(x4, y4);
/*      */     }
/* 1223 */     else if (anchor == ItemLabelAnchor.INSIDE3) {
/* 1224 */       result = new Point2D.Double(x4, y3);
/*      */     }
/* 1226 */     else if (anchor == ItemLabelAnchor.INSIDE4) {
/* 1227 */       result = new Point2D.Double(x4, y2);
/*      */     }
/* 1229 */     else if (anchor == ItemLabelAnchor.INSIDE5) {
/* 1230 */       result = new Point2D.Double(x4, y2);
/*      */     }
/* 1232 */     else if (anchor == ItemLabelAnchor.INSIDE6) {
/* 1233 */       result = new Point2D.Double(x3, y2);
/*      */     }
/* 1235 */     else if (anchor == ItemLabelAnchor.INSIDE7) {
/* 1236 */       result = new Point2D.Double(x2, y2);
/*      */     }
/* 1238 */     else if (anchor == ItemLabelAnchor.INSIDE8) {
/* 1239 */       result = new Point2D.Double(x2, y2);
/*      */     }
/* 1241 */     else if (anchor == ItemLabelAnchor.INSIDE9) {
/* 1242 */       result = new Point2D.Double(x2, y3);
/*      */     }
/* 1244 */     else if (anchor == ItemLabelAnchor.INSIDE10) {
/* 1245 */       result = new Point2D.Double(x2, y4);
/*      */     }
/* 1247 */     else if (anchor == ItemLabelAnchor.INSIDE11) {
/* 1248 */       result = new Point2D.Double(x2, y4);
/*      */     }
/* 1250 */     else if (anchor == ItemLabelAnchor.INSIDE12) {
/* 1251 */       result = new Point2D.Double(x3, y4);
/*      */     }
/* 1253 */     else if (anchor == ItemLabelAnchor.OUTSIDE1) {
/* 1254 */       result = new Point2D.Double(x5, y6);
/*      */     }
/* 1256 */     else if (anchor == ItemLabelAnchor.OUTSIDE2) {
/* 1257 */       result = new Point2D.Double(x6, y5);
/*      */     }
/* 1259 */     else if (anchor == ItemLabelAnchor.OUTSIDE3) {
/* 1260 */       result = new Point2D.Double(x6, y3);
/*      */     }
/* 1262 */     else if (anchor == ItemLabelAnchor.OUTSIDE4) {
/* 1263 */       result = new Point2D.Double(x6, y1);
/*      */     }
/* 1265 */     else if (anchor == ItemLabelAnchor.OUTSIDE5) {
/* 1266 */       result = new Point2D.Double(x5, y0);
/*      */     }
/* 1268 */     else if (anchor == ItemLabelAnchor.OUTSIDE6) {
/* 1269 */       result = new Point2D.Double(x3, y0);
/*      */     }
/* 1271 */     else if (anchor == ItemLabelAnchor.OUTSIDE7) {
/* 1272 */       result = new Point2D.Double(x1, y0);
/*      */     }
/* 1274 */     else if (anchor == ItemLabelAnchor.OUTSIDE8) {
/* 1275 */       result = new Point2D.Double(x0, y1);
/*      */     }
/* 1277 */     else if (anchor == ItemLabelAnchor.OUTSIDE9) {
/* 1278 */       result = new Point2D.Double(x0, y3);
/*      */     }
/* 1280 */     else if (anchor == ItemLabelAnchor.OUTSIDE10) {
/* 1281 */       result = new Point2D.Double(x0, y5);
/*      */     }
/* 1283 */     else if (anchor == ItemLabelAnchor.OUTSIDE11) {
/* 1284 */       result = new Point2D.Double(x1, y6);
/*      */     }
/* 1286 */     else if (anchor == ItemLabelAnchor.OUTSIDE12) {
/* 1287 */       result = new Point2D.Double(x3, y6);
/*      */     } 
/*      */     
/* 1290 */     return result;
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
/*      */   private boolean isInternalAnchor(ItemLabelAnchor anchor) {
/* 1302 */     return (anchor == ItemLabelAnchor.CENTER || anchor == ItemLabelAnchor.INSIDE1 || anchor == ItemLabelAnchor.INSIDE2 || anchor == ItemLabelAnchor.INSIDE3 || anchor == ItemLabelAnchor.INSIDE4 || anchor == ItemLabelAnchor.INSIDE5 || anchor == ItemLabelAnchor.INSIDE6 || anchor == ItemLabelAnchor.INSIDE7 || anchor == ItemLabelAnchor.INSIDE8 || anchor == ItemLabelAnchor.INSIDE9 || anchor == ItemLabelAnchor.INSIDE10 || anchor == ItemLabelAnchor.INSIDE11 || anchor == ItemLabelAnchor.INSIDE12);
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
/*      */   public boolean equals(Object obj) {
/* 1326 */     if (obj == this) {
/* 1327 */       return true;
/*      */     }
/* 1329 */     if (!(obj instanceof BarRenderer)) {
/* 1330 */       return false;
/*      */     }
/* 1332 */     BarRenderer that = (BarRenderer)obj;
/* 1333 */     if (this.base != that.base) {
/* 1334 */       return false;
/*      */     }
/* 1336 */     if (this.itemMargin != that.itemMargin) {
/* 1337 */       return false;
/*      */     }
/* 1339 */     if (this.drawBarOutline != that.drawBarOutline) {
/* 1340 */       return false;
/*      */     }
/* 1342 */     if (this.maximumBarWidth != that.maximumBarWidth) {
/* 1343 */       return false;
/*      */     }
/* 1345 */     if (this.minimumBarLength != that.minimumBarLength) {
/* 1346 */       return false;
/*      */     }
/* 1348 */     if (!ObjectUtilities.equal(this.gradientPaintTransformer, that.gradientPaintTransformer))
/*      */     {
/* 1350 */       return false;
/*      */     }
/* 1352 */     if (!ObjectUtilities.equal(this.positiveItemLabelPositionFallback, that.positiveItemLabelPositionFallback))
/*      */     {
/* 1354 */       return false;
/*      */     }
/* 1356 */     if (!ObjectUtilities.equal(this.negativeItemLabelPositionFallback, that.negativeItemLabelPositionFallback))
/*      */     {
/* 1358 */       return false;
/*      */     }
/* 1360 */     if (!this.barPainter.equals(that.barPainter)) {
/* 1361 */       return false;
/*      */     }
/* 1363 */     if (this.shadowsVisible != that.shadowsVisible) {
/* 1364 */       return false;
/*      */     }
/* 1366 */     if (!PaintUtilities.equal(this.shadowPaint, that.shadowPaint)) {
/* 1367 */       return false;
/*      */     }
/* 1369 */     if (this.shadowXOffset != that.shadowXOffset) {
/* 1370 */       return false;
/*      */     }
/* 1372 */     if (this.shadowYOffset != that.shadowYOffset) {
/* 1373 */       return false;
/*      */     }
/* 1375 */     return super.equals(obj);
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
/* 1386 */     stream.defaultWriteObject();
/* 1387 */     SerialUtilities.writePaint(this.shadowPaint, stream);
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
/* 1400 */     stream.defaultReadObject();
/* 1401 */     this.shadowPaint = SerialUtilities.readPaint(stream);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/BarRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */