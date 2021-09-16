/*      */ package org.jfree.chart.renderer.xy;
/*      */ 
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import org.jfree.chart.LegendItem;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.labels.ItemLabelAnchor;
/*      */ import org.jfree.chart.labels.ItemLabelPosition;
/*      */ import org.jfree.chart.labels.XYItemLabelGenerator;
/*      */ import org.jfree.chart.labels.XYSeriesLabelGenerator;
/*      */ import org.jfree.chart.plot.CrosshairState;
/*      */ import org.jfree.chart.plot.PlotOrientation;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.plot.XYPlot;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.xy.IntervalXYDataset;
/*      */ import org.jfree.data.xy.XYDataset;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.ui.GradientPaintTransformer;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.StandardGradientPaintTransformer;
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
/*      */ public class XYBarRenderer
/*      */   extends AbstractXYItemRenderer
/*      */   implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 770559577251370036L;
/*  164 */   private static XYBarPainter defaultBarPainter = new GradientXYBarPainter();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static XYBarPainter getDefaultBarPainter() {
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
/*      */   public static void setDefaultBarPainter(XYBarPainter painter) {
/*  185 */     ParamChecks.nullNotPermitted(painter, "painter");
/*  186 */     defaultBarPainter = painter;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean defaultShadowsVisible = true;
/*      */   
/*      */   private double base;
/*      */   
/*      */   private boolean useYInterval;
/*      */   
/*      */   private double margin;
/*      */   
/*      */   private boolean drawBarOutline;
/*      */   
/*      */   private GradientPaintTransformer gradientPaintTransformer;
/*      */   private transient Shape legendBar;
/*      */   
/*      */   public static boolean getDefaultShadowsVisible() {
/*  204 */     return defaultShadowsVisible;
/*      */   }
/*      */ 
/*      */   
/*      */   private ItemLabelPosition positiveItemLabelPositionFallback;
/*      */   private ItemLabelPosition negativeItemLabelPositionFallback;
/*      */   private XYBarPainter barPainter;
/*      */   private boolean shadowsVisible;
/*      */   private double shadowXOffset;
/*      */   private double shadowYOffset;
/*      */   private double barAlignmentFactor;
/*      */   
/*      */   public static void setDefaultShadowsVisible(boolean visible) {
/*  217 */     defaultShadowsVisible = visible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class XYBarRendererState
/*      */     extends XYItemRendererState
/*      */   {
/*      */     private double g2Base;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public XYBarRendererState(PlotRenderingInfo info) {
/*  234 */       super(info);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getG2Base() {
/*  243 */       return this.g2Base;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setG2Base(double value) {
/*  252 */       this.g2Base = value;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYBarRenderer() {
/*  334 */     this(0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYBarRenderer(double margin) {
/*  344 */     this.margin = margin;
/*  345 */     this.base = 0.0D;
/*  346 */     this.useYInterval = false;
/*  347 */     this.gradientPaintTransformer = (GradientPaintTransformer)new StandardGradientPaintTransformer();
/*  348 */     this.drawBarOutline = false;
/*  349 */     this.legendBar = new Rectangle2D.Double(-3.0D, -5.0D, 6.0D, 10.0D);
/*  350 */     this.barPainter = getDefaultBarPainter();
/*  351 */     this.shadowsVisible = getDefaultShadowsVisible();
/*  352 */     this.shadowXOffset = 4.0D;
/*  353 */     this.shadowYOffset = 4.0D;
/*  354 */     this.barAlignmentFactor = -1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getBase() {
/*  365 */     return this.base;
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
/*      */   public void setBase(double base) {
/*  379 */     this.base = base;
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
/*      */   public boolean getUseYInterval() {
/*  392 */     return this.useYInterval;
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
/*      */   public void setUseYInterval(boolean use) {
/*  405 */     if (this.useYInterval != use) {
/*  406 */       this.useYInterval = use;
/*  407 */       fireChangeEvent();
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
/*      */   public double getMargin() {
/*  420 */     return this.margin;
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
/*      */   public void setMargin(double margin) {
/*  432 */     this.margin = margin;
/*  433 */     fireChangeEvent();
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
/*  444 */     return this.drawBarOutline;
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
/*  456 */     this.drawBarOutline = draw;
/*  457 */     fireChangeEvent();
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
/*  469 */     return this.gradientPaintTransformer;
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
/*  482 */     this.gradientPaintTransformer = transformer;
/*  483 */     fireChangeEvent();
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
/*      */   public Shape getLegendBar() {
/*  495 */     return this.legendBar;
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
/*      */   public void setLegendBar(Shape bar) {
/*  507 */     ParamChecks.nullNotPermitted(bar, "bar");
/*  508 */     this.legendBar = bar;
/*  509 */     fireChangeEvent();
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
/*      */   public ItemLabelPosition getPositiveItemLabelPositionFallback() {
/*  522 */     return this.positiveItemLabelPositionFallback;
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
/*      */   public void setPositiveItemLabelPositionFallback(ItemLabelPosition position) {
/*  537 */     this.positiveItemLabelPositionFallback = position;
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
/*      */   public ItemLabelPosition getNegativeItemLabelPositionFallback() {
/*  551 */     return this.negativeItemLabelPositionFallback;
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
/*      */   public void setNegativeItemLabelPositionFallback(ItemLabelPosition position) {
/*  566 */     this.negativeItemLabelPositionFallback = position;
/*  567 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYBarPainter getBarPainter() {
/*  578 */     return this.barPainter;
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
/*      */   public void setBarPainter(XYBarPainter painter) {
/*  590 */     ParamChecks.nullNotPermitted(painter, "painter");
/*  591 */     this.barPainter = painter;
/*  592 */     fireChangeEvent();
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
/*  604 */     return this.shadowsVisible;
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
/*      */   public void setShadowVisible(boolean visible) {
/*  617 */     this.shadowsVisible = visible;
/*  618 */     fireChangeEvent();
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
/*  629 */     return this.shadowXOffset;
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
/*  641 */     this.shadowXOffset = offset;
/*  642 */     fireChangeEvent();
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
/*  653 */     return this.shadowYOffset;
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
/*  665 */     this.shadowYOffset = offset;
/*  666 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getBarAlignmentFactor() {
/*  677 */     return this.barAlignmentFactor;
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
/*      */   public void setBarAlignmentFactor(double factor) {
/*  690 */     this.barAlignmentFactor = factor;
/*  691 */     fireChangeEvent();
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
/*      */   public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset dataset, PlotRenderingInfo info) {
/*  713 */     XYBarRendererState state = new XYBarRendererState(info);
/*  714 */     ValueAxis rangeAxis = plot.getRangeAxisForDataset(plot.indexOf(dataset));
/*      */     
/*  716 */     state.setG2Base(rangeAxis.valueToJava2D(this.base, dataArea, plot
/*  717 */           .getRangeAxisEdge()));
/*  718 */     return state;
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
/*      */     LegendItem result;
/*  733 */     XYPlot xyplot = getPlot();
/*  734 */     if (xyplot == null) {
/*  735 */       return null;
/*      */     }
/*  737 */     XYDataset dataset = xyplot.getDataset(datasetIndex);
/*  738 */     if (dataset == null) {
/*  739 */       return null;
/*      */     }
/*      */     
/*  742 */     XYSeriesLabelGenerator lg = getLegendItemLabelGenerator();
/*  743 */     String label = lg.generateLabel(dataset, series);
/*  744 */     String description = label;
/*  745 */     String toolTipText = null;
/*  746 */     if (getLegendItemToolTipGenerator() != null) {
/*  747 */       toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*      */     }
/*      */     
/*  750 */     String urlText = null;
/*  751 */     if (getLegendItemURLGenerator() != null) {
/*  752 */       urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*      */     }
/*      */     
/*  755 */     Shape shape = this.legendBar;
/*  756 */     Paint paint = lookupSeriesPaint(series);
/*  757 */     Paint outlinePaint = lookupSeriesOutlinePaint(series);
/*  758 */     Stroke outlineStroke = lookupSeriesOutlineStroke(series);
/*  759 */     if (this.drawBarOutline) {
/*  760 */       result = new LegendItem(label, description, toolTipText, urlText, shape, paint, outlineStroke, outlinePaint);
/*      */     }
/*      */     else {
/*      */       
/*  764 */       result = new LegendItem(label, description, toolTipText, urlText, shape, paint);
/*      */     } 
/*      */     
/*  767 */     result.setLabelFont(lookupLegendTextFont(series));
/*  768 */     Paint labelPaint = lookupLegendTextPaint(series);
/*  769 */     if (labelPaint != null) {
/*  770 */       result.setLabelPaint(labelPaint);
/*      */     }
/*  772 */     result.setDataset((Dataset)dataset);
/*  773 */     result.setDatasetIndex(datasetIndex);
/*  774 */     result.setSeriesKey(dataset.getSeriesKey(series));
/*  775 */     result.setSeriesIndex(series);
/*  776 */     if (getGradientPaintTransformer() != null) {
/*  777 */       result.setFillPaintTransformer(getGradientPaintTransformer());
/*      */     }
/*  779 */     return result;
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
/*      */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/*      */     double value0, value1;
/*      */     RectangleEdge barBase;
/*  806 */     if (!getItemVisible(series, item)) {
/*      */       return;
/*      */     }
/*  809 */     IntervalXYDataset intervalDataset = (IntervalXYDataset)dataset;
/*      */ 
/*      */ 
/*      */     
/*  813 */     if (this.useYInterval) {
/*  814 */       value0 = intervalDataset.getStartYValue(series, item);
/*  815 */       value1 = intervalDataset.getEndYValue(series, item);
/*      */     } else {
/*      */       
/*  818 */       value0 = this.base;
/*  819 */       value1 = intervalDataset.getYValue(series, item);
/*      */     } 
/*  821 */     if (Double.isNaN(value0) || Double.isNaN(value1)) {
/*      */       return;
/*      */     }
/*  824 */     if (value0 <= value1) {
/*  825 */       if (!rangeAxis.getRange().intersects(value0, value1))
/*      */       {
/*      */         return;
/*      */       }
/*      */     }
/*  830 */     else if (!rangeAxis.getRange().intersects(value1, value0)) {
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  835 */     double translatedValue0 = rangeAxis.valueToJava2D(value0, dataArea, plot
/*  836 */         .getRangeAxisEdge());
/*  837 */     double translatedValue1 = rangeAxis.valueToJava2D(value1, dataArea, plot
/*  838 */         .getRangeAxisEdge());
/*  839 */     double bottom = Math.min(translatedValue0, translatedValue1);
/*  840 */     double top = Math.max(translatedValue0, translatedValue1);
/*      */     
/*  842 */     double startX = intervalDataset.getStartXValue(series, item);
/*  843 */     if (Double.isNaN(startX)) {
/*      */       return;
/*      */     }
/*  846 */     double endX = intervalDataset.getEndXValue(series, item);
/*  847 */     if (Double.isNaN(endX)) {
/*      */       return;
/*      */     }
/*  850 */     if (startX <= endX) {
/*  851 */       if (!domainAxis.getRange().intersects(startX, endX))
/*      */       {
/*      */         return;
/*      */       }
/*      */     }
/*  856 */     else if (!domainAxis.getRange().intersects(endX, startX)) {
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  862 */     if (this.barAlignmentFactor >= 0.0D && this.barAlignmentFactor <= 1.0D) {
/*  863 */       double x = intervalDataset.getXValue(series, item);
/*  864 */       double interval = endX - startX;
/*  865 */       startX = x - interval * this.barAlignmentFactor;
/*  866 */       endX = startX + interval;
/*      */     } 
/*      */     
/*  869 */     RectangleEdge location = plot.getDomainAxisEdge();
/*  870 */     double translatedStartX = domainAxis.valueToJava2D(startX, dataArea, location);
/*      */     
/*  872 */     double translatedEndX = domainAxis.valueToJava2D(endX, dataArea, location);
/*      */ 
/*      */     
/*  875 */     double translatedWidth = Math.max(1.0D, Math.abs(translatedEndX - translatedStartX));
/*      */ 
/*      */     
/*  878 */     double left = Math.min(translatedStartX, translatedEndX);
/*  879 */     if (getMargin() > 0.0D) {
/*  880 */       double cut = translatedWidth * getMargin();
/*  881 */       translatedWidth -= cut;
/*  882 */       left += cut / 2.0D;
/*      */     } 
/*      */     
/*  885 */     Rectangle2D bar = null;
/*  886 */     PlotOrientation orientation = plot.getOrientation();
/*  887 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*      */       
/*  889 */       bottom = Math.max(bottom, dataArea.getMinX());
/*  890 */       top = Math.min(top, dataArea.getMaxX());
/*  891 */       bar = new Rectangle2D.Double(bottom, left, top - bottom, translatedWidth);
/*      */     
/*      */     }
/*  894 */     else if (orientation == PlotOrientation.VERTICAL) {
/*      */       
/*  896 */       bottom = Math.max(bottom, dataArea.getMinY());
/*  897 */       top = Math.min(top, dataArea.getMaxY());
/*  898 */       bar = new Rectangle2D.Double(left, bottom, translatedWidth, top - bottom);
/*      */     } 
/*      */ 
/*      */     
/*  902 */     boolean positive = (value1 > 0.0D);
/*  903 */     boolean inverted = rangeAxis.isInverted();
/*      */     
/*  905 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*  906 */       if ((positive && inverted) || (!positive && !inverted)) {
/*  907 */         barBase = RectangleEdge.RIGHT;
/*      */       } else {
/*      */         
/*  910 */         barBase = RectangleEdge.LEFT;
/*      */       }
/*      */     
/*      */     }
/*  914 */     else if ((positive && !inverted) || (!positive && inverted)) {
/*  915 */       barBase = RectangleEdge.BOTTOM;
/*      */     } else {
/*      */       
/*  918 */       barBase = RectangleEdge.TOP;
/*      */     } 
/*      */     
/*  921 */     if (getShadowsVisible()) {
/*  922 */       this.barPainter.paintBarShadow(g2, this, series, item, bar, barBase, !this.useYInterval);
/*      */     }
/*      */     
/*  925 */     this.barPainter.paintBar(g2, this, series, item, bar, barBase);
/*      */     
/*  927 */     if (isItemLabelVisible(series, item)) {
/*  928 */       XYItemLabelGenerator generator = getItemLabelGenerator(series, item);
/*      */       
/*  930 */       drawItemLabel(g2, dataset, series, item, plot, generator, bar, (value1 < 0.0D));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  935 */     double x1 = (startX + endX) / 2.0D;
/*  936 */     double y1 = dataset.getYValue(series, item);
/*  937 */     double transX1 = domainAxis.valueToJava2D(x1, dataArea, location);
/*  938 */     double transY1 = rangeAxis.valueToJava2D(y1, dataArea, plot
/*  939 */         .getRangeAxisEdge());
/*  940 */     int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
/*  941 */     int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
/*  942 */     updateCrosshairValues(crosshairState, x1, y1, domainAxisIndex, rangeAxisIndex, transX1, transY1, plot
/*  943 */         .getOrientation());
/*      */     
/*  945 */     EntityCollection entities = state.getEntityCollection();
/*  946 */     if (entities != null) {
/*  947 */       addEntity(entities, bar, dataset, series, item, 0.0D, 0.0D);
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
/*      */   protected void drawItemLabel(Graphics2D g2, XYDataset dataset, int series, int item, XYPlot plot, XYItemLabelGenerator generator, Rectangle2D bar, boolean negative) {
/*      */     ItemLabelPosition position;
/*  972 */     if (generator == null) {
/*      */       return;
/*      */     }
/*  975 */     String label = generator.generateLabel(dataset, series, item);
/*  976 */     if (label == null) {
/*      */       return;
/*      */     }
/*      */     
/*  980 */     Font labelFont = getItemLabelFont(series, item);
/*  981 */     g2.setFont(labelFont);
/*  982 */     Paint paint = getItemLabelPaint(series, item);
/*  983 */     g2.setPaint(paint);
/*      */ 
/*      */ 
/*      */     
/*  987 */     if (!negative) {
/*  988 */       position = getPositiveItemLabelPosition(series, item);
/*      */     } else {
/*      */       
/*  991 */       position = getNegativeItemLabelPosition(series, item);
/*      */     } 
/*      */ 
/*      */     
/*  995 */     Point2D anchorPoint = calculateLabelAnchorPoint(position
/*  996 */         .getItemLabelAnchor(), bar, plot.getOrientation());
/*      */     
/*  998 */     if (isInternalAnchor(position.getItemLabelAnchor())) {
/*  999 */       Shape bounds = TextUtilities.calculateRotatedStringBounds(label, g2, 
/* 1000 */           (float)anchorPoint.getX(), (float)anchorPoint.getY(), position
/* 1001 */           .getTextAnchor(), position.getAngle(), position
/* 1002 */           .getRotationAnchor());
/*      */       
/* 1004 */       if (bounds != null && 
/* 1005 */         !bar.contains(bounds.getBounds2D())) {
/* 1006 */         if (!negative) {
/* 1007 */           position = getPositiveItemLabelPositionFallback();
/*      */         } else {
/*      */           
/* 1010 */           position = getNegativeItemLabelPositionFallback();
/*      */         } 
/* 1012 */         if (position != null) {
/* 1013 */           anchorPoint = calculateLabelAnchorPoint(position
/* 1014 */               .getItemLabelAnchor(), bar, plot
/* 1015 */               .getOrientation());
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1022 */     if (position != null) {
/* 1023 */       TextUtilities.drawRotatedString(label, g2, 
/* 1024 */           (float)anchorPoint.getX(), (float)anchorPoint.getY(), position
/* 1025 */           .getTextAnchor(), position.getAngle(), position
/* 1026 */           .getRotationAnchor());
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
/*      */   private Point2D calculateLabelAnchorPoint(ItemLabelAnchor anchor, Rectangle2D bar, PlotOrientation orientation) {
/* 1042 */     Point2D result = null;
/* 1043 */     double offset = getItemLabelAnchorOffset();
/* 1044 */     double x0 = bar.getX() - offset;
/* 1045 */     double x1 = bar.getX();
/* 1046 */     double x2 = bar.getX() + offset;
/* 1047 */     double x3 = bar.getCenterX();
/* 1048 */     double x4 = bar.getMaxX() - offset;
/* 1049 */     double x5 = bar.getMaxX();
/* 1050 */     double x6 = bar.getMaxX() + offset;
/*      */     
/* 1052 */     double y0 = bar.getMaxY() + offset;
/* 1053 */     double y1 = bar.getMaxY();
/* 1054 */     double y2 = bar.getMaxY() - offset;
/* 1055 */     double y3 = bar.getCenterY();
/* 1056 */     double y4 = bar.getMinY() + offset;
/* 1057 */     double y5 = bar.getMinY();
/* 1058 */     double y6 = bar.getMinY() - offset;
/*      */     
/* 1060 */     if (anchor == ItemLabelAnchor.CENTER) {
/* 1061 */       result = new Point2D.Double(x3, y3);
/*      */     }
/* 1063 */     else if (anchor == ItemLabelAnchor.INSIDE1) {
/* 1064 */       result = new Point2D.Double(x4, y4);
/*      */     }
/* 1066 */     else if (anchor == ItemLabelAnchor.INSIDE2) {
/* 1067 */       result = new Point2D.Double(x4, y4);
/*      */     }
/* 1069 */     else if (anchor == ItemLabelAnchor.INSIDE3) {
/* 1070 */       result = new Point2D.Double(x4, y3);
/*      */     }
/* 1072 */     else if (anchor == ItemLabelAnchor.INSIDE4) {
/* 1073 */       result = new Point2D.Double(x4, y2);
/*      */     }
/* 1075 */     else if (anchor == ItemLabelAnchor.INSIDE5) {
/* 1076 */       result = new Point2D.Double(x4, y2);
/*      */     }
/* 1078 */     else if (anchor == ItemLabelAnchor.INSIDE6) {
/* 1079 */       result = new Point2D.Double(x3, y2);
/*      */     }
/* 1081 */     else if (anchor == ItemLabelAnchor.INSIDE7) {
/* 1082 */       result = new Point2D.Double(x2, y2);
/*      */     }
/* 1084 */     else if (anchor == ItemLabelAnchor.INSIDE8) {
/* 1085 */       result = new Point2D.Double(x2, y2);
/*      */     }
/* 1087 */     else if (anchor == ItemLabelAnchor.INSIDE9) {
/* 1088 */       result = new Point2D.Double(x2, y3);
/*      */     }
/* 1090 */     else if (anchor == ItemLabelAnchor.INSIDE10) {
/* 1091 */       result = new Point2D.Double(x2, y4);
/*      */     }
/* 1093 */     else if (anchor == ItemLabelAnchor.INSIDE11) {
/* 1094 */       result = new Point2D.Double(x2, y4);
/*      */     }
/* 1096 */     else if (anchor == ItemLabelAnchor.INSIDE12) {
/* 1097 */       result = new Point2D.Double(x3, y4);
/*      */     }
/* 1099 */     else if (anchor == ItemLabelAnchor.OUTSIDE1) {
/* 1100 */       result = new Point2D.Double(x5, y6);
/*      */     }
/* 1102 */     else if (anchor == ItemLabelAnchor.OUTSIDE2) {
/* 1103 */       result = new Point2D.Double(x6, y5);
/*      */     }
/* 1105 */     else if (anchor == ItemLabelAnchor.OUTSIDE3) {
/* 1106 */       result = new Point2D.Double(x6, y3);
/*      */     }
/* 1108 */     else if (anchor == ItemLabelAnchor.OUTSIDE4) {
/* 1109 */       result = new Point2D.Double(x6, y1);
/*      */     }
/* 1111 */     else if (anchor == ItemLabelAnchor.OUTSIDE5) {
/* 1112 */       result = new Point2D.Double(x5, y0);
/*      */     }
/* 1114 */     else if (anchor == ItemLabelAnchor.OUTSIDE6) {
/* 1115 */       result = new Point2D.Double(x3, y0);
/*      */     }
/* 1117 */     else if (anchor == ItemLabelAnchor.OUTSIDE7) {
/* 1118 */       result = new Point2D.Double(x1, y0);
/*      */     }
/* 1120 */     else if (anchor == ItemLabelAnchor.OUTSIDE8) {
/* 1121 */       result = new Point2D.Double(x0, y1);
/*      */     }
/* 1123 */     else if (anchor == ItemLabelAnchor.OUTSIDE9) {
/* 1124 */       result = new Point2D.Double(x0, y3);
/*      */     }
/* 1126 */     else if (anchor == ItemLabelAnchor.OUTSIDE10) {
/* 1127 */       result = new Point2D.Double(x0, y5);
/*      */     }
/* 1129 */     else if (anchor == ItemLabelAnchor.OUTSIDE11) {
/* 1130 */       result = new Point2D.Double(x1, y6);
/*      */     }
/* 1132 */     else if (anchor == ItemLabelAnchor.OUTSIDE12) {
/* 1133 */       result = new Point2D.Double(x3, y6);
/*      */     } 
/*      */     
/* 1136 */     return result;
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
/* 1148 */     return (anchor == ItemLabelAnchor.CENTER || anchor == ItemLabelAnchor.INSIDE1 || anchor == ItemLabelAnchor.INSIDE2 || anchor == ItemLabelAnchor.INSIDE3 || anchor == ItemLabelAnchor.INSIDE4 || anchor == ItemLabelAnchor.INSIDE5 || anchor == ItemLabelAnchor.INSIDE6 || anchor == ItemLabelAnchor.INSIDE7 || anchor == ItemLabelAnchor.INSIDE8 || anchor == ItemLabelAnchor.INSIDE9 || anchor == ItemLabelAnchor.INSIDE10 || anchor == ItemLabelAnchor.INSIDE11 || anchor == ItemLabelAnchor.INSIDE12);
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
/*      */   public Range findDomainBounds(XYDataset dataset) {
/* 1175 */     return findDomainBounds(dataset, true);
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
/*      */   public Range findRangeBounds(XYDataset dataset) {
/* 1190 */     return findRangeBounds(dataset, this.useYInterval);
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
/* 1202 */     XYBarRenderer result = (XYBarRenderer)super.clone();
/* 1203 */     if (this.gradientPaintTransformer != null) {
/* 1204 */       result
/* 1205 */         .gradientPaintTransformer = (GradientPaintTransformer)ObjectUtilities.clone(this.gradientPaintTransformer);
/*      */     }
/* 1207 */     result.legendBar = ShapeUtilities.clone(this.legendBar);
/* 1208 */     return result;
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
/* 1220 */     if (obj == this) {
/* 1221 */       return true;
/*      */     }
/* 1223 */     if (!(obj instanceof XYBarRenderer)) {
/* 1224 */       return false;
/*      */     }
/* 1226 */     XYBarRenderer that = (XYBarRenderer)obj;
/* 1227 */     if (this.base != that.base) {
/* 1228 */       return false;
/*      */     }
/* 1230 */     if (this.drawBarOutline != that.drawBarOutline) {
/* 1231 */       return false;
/*      */     }
/* 1233 */     if (this.margin != that.margin) {
/* 1234 */       return false;
/*      */     }
/* 1236 */     if (this.useYInterval != that.useYInterval) {
/* 1237 */       return false;
/*      */     }
/* 1239 */     if (!ObjectUtilities.equal(this.gradientPaintTransformer, that.gradientPaintTransformer))
/*      */     {
/* 1241 */       return false;
/*      */     }
/* 1243 */     if (!ShapeUtilities.equal(this.legendBar, that.legendBar)) {
/* 1244 */       return false;
/*      */     }
/* 1246 */     if (!ObjectUtilities.equal(this.positiveItemLabelPositionFallback, that.positiveItemLabelPositionFallback))
/*      */     {
/* 1248 */       return false;
/*      */     }
/* 1250 */     if (!ObjectUtilities.equal(this.negativeItemLabelPositionFallback, that.negativeItemLabelPositionFallback))
/*      */     {
/* 1252 */       return false;
/*      */     }
/* 1254 */     if (!this.barPainter.equals(that.barPainter)) {
/* 1255 */       return false;
/*      */     }
/* 1257 */     if (this.shadowsVisible != that.shadowsVisible) {
/* 1258 */       return false;
/*      */     }
/* 1260 */     if (this.shadowXOffset != that.shadowXOffset) {
/* 1261 */       return false;
/*      */     }
/* 1263 */     if (this.shadowYOffset != that.shadowYOffset) {
/* 1264 */       return false;
/*      */     }
/* 1266 */     if (this.barAlignmentFactor != that.barAlignmentFactor) {
/* 1267 */       return false;
/*      */     }
/* 1269 */     return super.equals(obj);
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
/* 1282 */     stream.defaultReadObject();
/* 1283 */     this.legendBar = SerialUtilities.readShape(stream);
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
/* 1294 */     stream.defaultWriteObject();
/* 1295 */     SerialUtilities.writeShape(this.legendBar, stream);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYBarRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */