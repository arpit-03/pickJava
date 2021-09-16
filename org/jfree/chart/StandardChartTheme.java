/*      */ package org.jfree.chart;
/*      */ 
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Stroke;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.jfree.chart.annotations.XYAnnotation;
/*      */ import org.jfree.chart.annotations.XYTextAnnotation;
/*      */ import org.jfree.chart.axis.CategoryAxis;
/*      */ import org.jfree.chart.axis.PeriodAxis;
/*      */ import org.jfree.chart.axis.PeriodAxisLabelInfo;
/*      */ import org.jfree.chart.axis.SubCategoryAxis;
/*      */ import org.jfree.chart.axis.SymbolAxis;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.block.Block;
/*      */ import org.jfree.chart.block.BlockContainer;
/*      */ import org.jfree.chart.block.LabelBlock;
/*      */ import org.jfree.chart.plot.CategoryPlot;
/*      */ import org.jfree.chart.plot.CombinedDomainCategoryPlot;
/*      */ import org.jfree.chart.plot.CombinedDomainXYPlot;
/*      */ import org.jfree.chart.plot.CombinedRangeCategoryPlot;
/*      */ import org.jfree.chart.plot.CombinedRangeXYPlot;
/*      */ import org.jfree.chart.plot.DefaultDrawingSupplier;
/*      */ import org.jfree.chart.plot.DrawingSupplier;
/*      */ import org.jfree.chart.plot.FastScatterPlot;
/*      */ import org.jfree.chart.plot.MeterPlot;
/*      */ import org.jfree.chart.plot.MultiplePiePlot;
/*      */ import org.jfree.chart.plot.PieLabelLinkStyle;
/*      */ import org.jfree.chart.plot.PiePlot;
/*      */ import org.jfree.chart.plot.Plot;
/*      */ import org.jfree.chart.plot.PolarPlot;
/*      */ import org.jfree.chart.plot.SpiderWebPlot;
/*      */ import org.jfree.chart.plot.ThermometerPlot;
/*      */ import org.jfree.chart.plot.XYPlot;
/*      */ import org.jfree.chart.renderer.AbstractRenderer;
/*      */ import org.jfree.chart.renderer.category.BarPainter;
/*      */ import org.jfree.chart.renderer.category.BarRenderer;
/*      */ import org.jfree.chart.renderer.category.BarRenderer3D;
/*      */ import org.jfree.chart.renderer.category.CategoryItemRenderer;
/*      */ import org.jfree.chart.renderer.category.GradientBarPainter;
/*      */ import org.jfree.chart.renderer.category.LineRenderer3D;
/*      */ import org.jfree.chart.renderer.category.MinMaxCategoryRenderer;
/*      */ import org.jfree.chart.renderer.category.StatisticalBarRenderer;
/*      */ import org.jfree.chart.renderer.xy.GradientXYBarPainter;
/*      */ import org.jfree.chart.renderer.xy.XYBarPainter;
/*      */ import org.jfree.chart.renderer.xy.XYBarRenderer;
/*      */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*      */ import org.jfree.chart.title.CompositeTitle;
/*      */ import org.jfree.chart.title.LegendTitle;
/*      */ import org.jfree.chart.title.PaintScaleLegend;
/*      */ import org.jfree.chart.title.TextTitle;
/*      */ import org.jfree.chart.title.Title;
/*      */ import org.jfree.chart.util.DefaultShadowGenerator;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.chart.util.ShadowGenerator;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.ui.RectangleInsets;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StandardChartTheme
/*      */   implements ChartTheme, Cloneable, PublicCloneable, Serializable
/*      */ {
/*      */   private String name;
/*      */   private Font extraLargeFont;
/*      */   private Font largeFont;
/*      */   private Font regularFont;
/*      */   private Font smallFont;
/*      */   private transient Paint titlePaint;
/*      */   private transient Paint subtitlePaint;
/*      */   private transient Paint chartBackgroundPaint;
/*      */   private transient Paint legendBackgroundPaint;
/*      */   private transient Paint legendItemPaint;
/*      */   private DrawingSupplier drawingSupplier;
/*      */   private transient Paint plotBackgroundPaint;
/*      */   private transient Paint plotOutlinePaint;
/*      */   private PieLabelLinkStyle labelLinkStyle;
/*      */   private transient Paint labelLinkPaint;
/*      */   private transient Paint domainGridlinePaint;
/*      */   private transient Paint rangeGridlinePaint;
/*      */   private transient Paint baselinePaint;
/*      */   private transient Paint crosshairPaint;
/*      */   private RectangleInsets axisOffset;
/*      */   private transient Paint axisLabelPaint;
/*      */   private transient Paint tickLabelPaint;
/*      */   private transient Paint itemLabelPaint;
/*      */   private boolean shadowVisible;
/*      */   private transient Paint shadowPaint;
/*      */   private BarPainter barPainter;
/*      */   private XYBarPainter xyBarPainter;
/*      */   private transient Paint thermometerPaint;
/*      */   private transient Paint wallPaint;
/*      */   private transient Paint errorIndicatorPaint;
/*  233 */   private transient Paint gridBandPaint = SymbolAxis.DEFAULT_GRID_BAND_PAINT;
/*      */ 
/*      */   
/*  236 */   private transient Paint gridBandAlternatePaint = SymbolAxis.DEFAULT_GRID_BAND_ALTERNATE_PAINT;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ShadowGenerator shadowGenerator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ChartTheme createJFreeTheme() {
/*  252 */     return new StandardChartTheme("JFree");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ChartTheme createDarknessTheme() {
/*  262 */     StandardChartTheme theme = new StandardChartTheme("Darkness");
/*  263 */     theme.titlePaint = Color.white;
/*  264 */     theme.subtitlePaint = Color.white;
/*  265 */     theme.legendBackgroundPaint = Color.black;
/*  266 */     theme.legendItemPaint = Color.white;
/*  267 */     theme.chartBackgroundPaint = Color.black;
/*  268 */     theme.plotBackgroundPaint = Color.black;
/*  269 */     theme.plotOutlinePaint = Color.yellow;
/*  270 */     theme.baselinePaint = Color.white;
/*  271 */     theme.crosshairPaint = Color.red;
/*  272 */     theme.labelLinkPaint = Color.lightGray;
/*  273 */     theme.tickLabelPaint = Color.white;
/*  274 */     theme.axisLabelPaint = Color.white;
/*  275 */     theme.shadowPaint = Color.darkGray;
/*  276 */     theme.itemLabelPaint = Color.white;
/*  277 */     theme
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  285 */       .drawingSupplier = (DrawingSupplier)new DefaultDrawingSupplier(new Paint[] { Color.decode("0xFFFF00"), Color.decode("0x0036CC"), Color.decode("0xFF0000"), Color.decode("0xFFFF7F"), Color.decode("0x6681CC"), Color.decode("0xFF7F7F"), Color.decode("0xFFFFBF"), Color.decode("0x99A6CC"), Color.decode("0xFFBFBF"), Color.decode("0xA9A938"), Color.decode("0x2D4587") }, new Paint[] { Color.decode("0xFFFF00"), Color.decode("0x0036CC") }, new Stroke[] { new BasicStroke(2.0F) }, new Stroke[] { new BasicStroke(0.5F) }, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE);
/*      */ 
/*      */ 
/*      */     
/*  289 */     theme.wallPaint = Color.darkGray;
/*  290 */     theme.errorIndicatorPaint = Color.lightGray;
/*  291 */     theme.gridBandPaint = new Color(255, 255, 255, 20);
/*  292 */     theme.gridBandAlternatePaint = new Color(255, 255, 255, 40);
/*  293 */     theme.shadowGenerator = null;
/*  294 */     return theme;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ChartTheme createLegacyTheme() {
/*  305 */     StandardChartTheme theme = new StandardChartTheme("Legacy")
/*      */       {
/*      */         public void apply(JFreeChart chart) {}
/*      */       };
/*      */ 
/*      */     
/*  311 */     return theme;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StandardChartTheme(String name) {
/*  320 */     this(name, false);
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
/*      */   public StandardChartTheme(String name, boolean shadow) {
/*  333 */     ParamChecks.nullNotPermitted(name, "name");
/*  334 */     this.name = name;
/*  335 */     this.extraLargeFont = new Font("Tahoma", 1, 20);
/*  336 */     this.largeFont = new Font("Tahoma", 1, 14);
/*  337 */     this.regularFont = new Font("Tahoma", 0, 12);
/*  338 */     this.smallFont = new Font("Tahoma", 0, 10);
/*  339 */     this.titlePaint = Color.black;
/*  340 */     this.subtitlePaint = Color.black;
/*  341 */     this.legendBackgroundPaint = Color.white;
/*  342 */     this.legendItemPaint = Color.darkGray;
/*  343 */     this.chartBackgroundPaint = Color.white;
/*  344 */     this.drawingSupplier = (DrawingSupplier)new DefaultDrawingSupplier();
/*  345 */     this.plotBackgroundPaint = Color.lightGray;
/*  346 */     this.plotOutlinePaint = Color.black;
/*  347 */     this.labelLinkPaint = Color.black;
/*  348 */     this.labelLinkStyle = PieLabelLinkStyle.CUBIC_CURVE;
/*  349 */     this.axisOffset = new RectangleInsets(4.0D, 4.0D, 4.0D, 4.0D);
/*  350 */     this.domainGridlinePaint = Color.white;
/*  351 */     this.rangeGridlinePaint = Color.white;
/*  352 */     this.baselinePaint = Color.black;
/*  353 */     this.crosshairPaint = Color.blue;
/*  354 */     this.axisLabelPaint = Color.darkGray;
/*  355 */     this.tickLabelPaint = Color.darkGray;
/*  356 */     this.barPainter = (BarPainter)new GradientBarPainter();
/*  357 */     this.xyBarPainter = (XYBarPainter)new GradientXYBarPainter();
/*  358 */     this.shadowVisible = false;
/*  359 */     this.shadowPaint = Color.gray;
/*  360 */     this.itemLabelPaint = Color.black;
/*  361 */     this.thermometerPaint = Color.white;
/*  362 */     this.wallPaint = BarRenderer3D.DEFAULT_WALL_PAINT;
/*  363 */     this.errorIndicatorPaint = Color.black;
/*  364 */     this.shadowGenerator = shadow ? (ShadowGenerator)new DefaultShadowGenerator() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getExtraLargeFont() {
/*  375 */     return this.extraLargeFont;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExtraLargeFont(Font font) {
/*  386 */     ParamChecks.nullNotPermitted(font, "font");
/*  387 */     this.extraLargeFont = font;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getLargeFont() {
/*  398 */     return this.largeFont;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLargeFont(Font font) {
/*  409 */     ParamChecks.nullNotPermitted(font, "font");
/*  410 */     this.largeFont = font;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getRegularFont() {
/*  421 */     return this.regularFont;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRegularFont(Font font) {
/*  432 */     ParamChecks.nullNotPermitted(font, "font");
/*  433 */     this.regularFont = font;
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
/*      */   public Font getSmallFont() {
/*  446 */     return this.smallFont;
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
/*      */   public void setSmallFont(Font font) {
/*  459 */     ParamChecks.nullNotPermitted(font, "font");
/*  460 */     this.smallFont = font;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getTitlePaint() {
/*  471 */     return this.titlePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTitlePaint(Paint paint) {
/*  482 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  483 */     this.titlePaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getSubtitlePaint() {
/*  494 */     return this.subtitlePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSubtitlePaint(Paint paint) {
/*  505 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  506 */     this.subtitlePaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getChartBackgroundPaint() {
/*  517 */     return this.chartBackgroundPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setChartBackgroundPaint(Paint paint) {
/*  528 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  529 */     this.chartBackgroundPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getLegendBackgroundPaint() {
/*  540 */     return this.legendBackgroundPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegendBackgroundPaint(Paint paint) {
/*  551 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  552 */     this.legendBackgroundPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getLegendItemPaint() {
/*  563 */     return this.legendItemPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLegendItemPaint(Paint paint) {
/*  574 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  575 */     this.legendItemPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getPlotBackgroundPaint() {
/*  586 */     return this.plotBackgroundPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlotBackgroundPaint(Paint paint) {
/*  597 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  598 */     this.plotBackgroundPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getPlotOutlinePaint() {
/*  609 */     return this.plotOutlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlotOutlinePaint(Paint paint) {
/*  620 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  621 */     this.plotOutlinePaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PieLabelLinkStyle getLabelLinkStyle() {
/*  632 */     return this.labelLinkStyle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelLinkStyle(PieLabelLinkStyle style) {
/*  643 */     ParamChecks.nullNotPermitted(style, "style");
/*  644 */     this.labelLinkStyle = style;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getLabelLinkPaint() {
/*  655 */     return this.labelLinkPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLabelLinkPaint(Paint paint) {
/*  666 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  667 */     this.labelLinkPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getDomainGridlinePaint() {
/*  678 */     return this.domainGridlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomainGridlinePaint(Paint paint) {
/*  689 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  690 */     this.domainGridlinePaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getRangeGridlinePaint() {
/*  701 */     return this.rangeGridlinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRangeGridlinePaint(Paint paint) {
/*  712 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  713 */     this.rangeGridlinePaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getBaselinePaint() {
/*  724 */     return this.baselinePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaselinePaint(Paint paint) {
/*  735 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  736 */     this.baselinePaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getCrosshairPaint() {
/*  745 */     return this.crosshairPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCrosshairPaint(Paint paint) {
/*  754 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  755 */     this.crosshairPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RectangleInsets getAxisOffset() {
/*  766 */     return this.axisOffset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxisOffset(RectangleInsets offset) {
/*  777 */     ParamChecks.nullNotPermitted(offset, "offset");
/*  778 */     this.axisOffset = offset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getAxisLabelPaint() {
/*  789 */     return this.axisLabelPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAxisLabelPaint(Paint paint) {
/*  800 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  801 */     this.axisLabelPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getTickLabelPaint() {
/*  812 */     return this.tickLabelPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTickLabelPaint(Paint paint) {
/*  823 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  824 */     this.tickLabelPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getItemLabelPaint() {
/*  835 */     return this.itemLabelPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemLabelPaint(Paint paint) {
/*  846 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  847 */     this.itemLabelPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShadowVisible() {
/*  858 */     return this.shadowVisible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShadowVisible(boolean visible) {
/*  869 */     this.shadowVisible = visible;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getShadowPaint() {
/*  880 */     return this.shadowPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setShadowPaint(Paint paint) {
/*  891 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  892 */     this.shadowPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BarPainter getBarPainter() {
/*  903 */     return this.barPainter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBarPainter(BarPainter painter) {
/*  914 */     ParamChecks.nullNotPermitted(painter, "painter");
/*  915 */     this.barPainter = painter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYBarPainter getXYBarPainter() {
/*  926 */     return this.xyBarPainter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setXYBarPainter(XYBarPainter painter) {
/*  937 */     ParamChecks.nullNotPermitted(painter, "painter");
/*  938 */     this.xyBarPainter = painter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getThermometerPaint() {
/*  949 */     return this.thermometerPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThermometerPaint(Paint paint) {
/*  960 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  961 */     this.thermometerPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getWallPaint() {
/*  972 */     return this.wallPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWallPaint(Paint paint) {
/*  983 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  984 */     this.wallPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getErrorIndicatorPaint() {
/*  995 */     return this.errorIndicatorPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setErrorIndicatorPaint(Paint paint) {
/* 1006 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 1007 */     this.errorIndicatorPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getGridBandPaint() {
/* 1018 */     return this.gridBandPaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGridBandPaint(Paint paint) {
/* 1029 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 1030 */     this.gridBandPaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getGridBandAlternatePaint() {
/* 1041 */     return this.gridBandAlternatePaint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGridBandAlternatePaint(Paint paint) {
/* 1052 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 1053 */     this.gridBandAlternatePaint = paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 1062 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DrawingSupplier getDrawingSupplier() {
/* 1071 */     DrawingSupplier result = null;
/* 1072 */     if (this.drawingSupplier instanceof PublicCloneable) {
/* 1073 */       PublicCloneable pc = (PublicCloneable)this.drawingSupplier;
/*      */       try {
/* 1075 */         result = (DrawingSupplier)pc.clone();
/*      */       }
/* 1077 */       catch (CloneNotSupportedException e) {
/* 1078 */         throw new RuntimeException(e);
/*      */       } 
/*      */     } 
/* 1081 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDrawingSupplier(DrawingSupplier supplier) {
/* 1092 */     ParamChecks.nullNotPermitted(supplier, "supplier");
/* 1093 */     this.drawingSupplier = supplier;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void apply(JFreeChart chart) {
/* 1103 */     ParamChecks.nullNotPermitted(chart, "chart");
/* 1104 */     TextTitle title = chart.getTitle();
/* 1105 */     if (title != null) {
/* 1106 */       title.setFont(this.extraLargeFont);
/* 1107 */       title.setPaint(this.titlePaint);
/*      */     } 
/*      */     
/* 1110 */     int subtitleCount = chart.getSubtitleCount();
/* 1111 */     for (int i = 0; i < subtitleCount; i++) {
/* 1112 */       applyToTitle(chart.getSubtitle(i));
/*      */     }
/*      */     
/* 1115 */     chart.setBackgroundPaint(this.chartBackgroundPaint);
/*      */ 
/*      */     
/* 1118 */     Plot plot = chart.getPlot();
/* 1119 */     if (plot != null) {
/* 1120 */       applyToPlot(plot);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToTitle(Title title) {
/* 1130 */     if (title instanceof TextTitle) {
/* 1131 */       TextTitle tt = (TextTitle)title;
/* 1132 */       tt.setFont(this.largeFont);
/* 1133 */       tt.setPaint(this.subtitlePaint);
/*      */     }
/* 1135 */     else if (title instanceof LegendTitle) {
/* 1136 */       LegendTitle lt = (LegendTitle)title;
/* 1137 */       if (lt.getBackgroundPaint() != null) {
/* 1138 */         lt.setBackgroundPaint(this.legendBackgroundPaint);
/*      */       }
/* 1140 */       lt.setItemFont(this.regularFont);
/* 1141 */       lt.setItemPaint(this.legendItemPaint);
/* 1142 */       if (lt.getWrapper() != null) {
/* 1143 */         applyToBlockContainer(lt.getWrapper());
/*      */       }
/*      */     }
/* 1146 */     else if (title instanceof PaintScaleLegend) {
/* 1147 */       PaintScaleLegend psl = (PaintScaleLegend)title;
/* 1148 */       psl.setBackgroundPaint(this.legendBackgroundPaint);
/* 1149 */       ValueAxis axis = psl.getAxis();
/* 1150 */       if (axis != null) {
/* 1151 */         applyToValueAxis(axis);
/*      */       }
/*      */     }
/* 1154 */     else if (title instanceof CompositeTitle) {
/* 1155 */       CompositeTitle ct = (CompositeTitle)title;
/* 1156 */       BlockContainer bc = ct.getContainer();
/* 1157 */       List blocks = bc.getBlocks();
/* 1158 */       Iterator<Block> iterator = blocks.iterator();
/* 1159 */       while (iterator.hasNext()) {
/* 1160 */         Block b = iterator.next();
/* 1161 */         if (b instanceof Title) {
/* 1162 */           applyToTitle((Title)b);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToBlockContainer(BlockContainer bc) {
/* 1174 */     Iterator<Block> iterator = bc.getBlocks().iterator();
/* 1175 */     while (iterator.hasNext()) {
/* 1176 */       Block b = iterator.next();
/* 1177 */       applyToBlock(b);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToBlock(Block b) {
/* 1187 */     if (b instanceof Title) {
/* 1188 */       applyToTitle((Title)b);
/*      */     }
/* 1190 */     else if (b instanceof LabelBlock) {
/* 1191 */       LabelBlock lb = (LabelBlock)b;
/* 1192 */       lb.setFont(this.regularFont);
/* 1193 */       lb.setPaint(this.legendItemPaint);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToPlot(Plot plot) {
/* 1203 */     ParamChecks.nullNotPermitted(plot, "plot");
/* 1204 */     if (plot.getDrawingSupplier() != null) {
/* 1205 */       plot.setDrawingSupplier(getDrawingSupplier());
/*      */     }
/* 1207 */     if (plot.getBackgroundPaint() != null) {
/* 1208 */       plot.setBackgroundPaint(this.plotBackgroundPaint);
/*      */     }
/* 1210 */     plot.setOutlinePaint(this.plotOutlinePaint);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1216 */     if (plot instanceof PiePlot) {
/* 1217 */       applyToPiePlot((PiePlot)plot);
/*      */     }
/* 1219 */     else if (plot instanceof MultiplePiePlot) {
/* 1220 */       applyToMultiplePiePlot((MultiplePiePlot)plot);
/*      */     }
/* 1222 */     else if (plot instanceof CategoryPlot) {
/* 1223 */       applyToCategoryPlot((CategoryPlot)plot);
/*      */     }
/* 1225 */     else if (plot instanceof XYPlot) {
/* 1226 */       applyToXYPlot((XYPlot)plot);
/*      */     }
/* 1228 */     else if (plot instanceof FastScatterPlot) {
/* 1229 */       applyToFastScatterPlot((FastScatterPlot)plot);
/*      */     }
/* 1231 */     else if (plot instanceof MeterPlot) {
/* 1232 */       applyToMeterPlot((MeterPlot)plot);
/*      */     }
/* 1234 */     else if (plot instanceof ThermometerPlot) {
/* 1235 */       applyToThermometerPlot((ThermometerPlot)plot);
/*      */     }
/* 1237 */     else if (plot instanceof SpiderWebPlot) {
/* 1238 */       applyToSpiderWebPlot((SpiderWebPlot)plot);
/*      */     }
/* 1240 */     else if (plot instanceof PolarPlot) {
/* 1241 */       applyToPolarPlot((PolarPlot)plot);
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
/*      */   protected void applyToPiePlot(PiePlot plot) {
/* 1253 */     plot.setLabelLinkPaint(this.labelLinkPaint);
/* 1254 */     plot.setLabelLinkStyle(this.labelLinkStyle);
/* 1255 */     plot.setLabelFont(this.regularFont);
/* 1256 */     plot.setShadowGenerator(this.shadowGenerator);
/*      */ 
/*      */ 
/*      */     
/* 1260 */     if (plot.getAutoPopulateSectionPaint()) {
/* 1261 */       plot.clearSectionPaints(false);
/*      */     }
/* 1263 */     if (plot.getAutoPopulateSectionOutlinePaint()) {
/* 1264 */       plot.clearSectionOutlinePaints(false);
/*      */     }
/* 1266 */     if (plot.getAutoPopulateSectionOutlineStroke()) {
/* 1267 */       plot.clearSectionOutlineStrokes(false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToMultiplePiePlot(MultiplePiePlot plot) {
/* 1277 */     apply(plot.getPieChart());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToCategoryPlot(CategoryPlot plot) {
/* 1286 */     plot.setAxisOffset(this.axisOffset);
/* 1287 */     plot.setDomainGridlinePaint(this.domainGridlinePaint);
/* 1288 */     plot.setRangeGridlinePaint(this.rangeGridlinePaint);
/* 1289 */     plot.setRangeZeroBaselinePaint(this.baselinePaint);
/* 1290 */     plot.setShadowGenerator(this.shadowGenerator);
/*      */ 
/*      */     
/* 1293 */     int domainAxisCount = plot.getDomainAxisCount();
/* 1294 */     for (int i = 0; i < domainAxisCount; i++) {
/* 1295 */       CategoryAxis axis = plot.getDomainAxis(i);
/* 1296 */       if (axis != null) {
/* 1297 */         applyToCategoryAxis(axis);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1302 */     int rangeAxisCount = plot.getRangeAxisCount();
/* 1303 */     for (int j = 0; j < rangeAxisCount; j++) {
/* 1304 */       ValueAxis axis = plot.getRangeAxis(j);
/* 1305 */       if (axis != null) {
/* 1306 */         applyToValueAxis(axis);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1311 */     int rendererCount = plot.getRendererCount();
/* 1312 */     for (int k = 0; k < rendererCount; k++) {
/* 1313 */       CategoryItemRenderer r = plot.getRenderer(k);
/* 1314 */       if (r != null) {
/* 1315 */         applyToCategoryItemRenderer(r);
/*      */       }
/*      */     } 
/*      */     
/* 1319 */     if (plot instanceof CombinedDomainCategoryPlot) {
/* 1320 */       CombinedDomainCategoryPlot cp = (CombinedDomainCategoryPlot)plot;
/* 1321 */       Iterator<CategoryPlot> iterator = cp.getSubplots().iterator();
/* 1322 */       while (iterator.hasNext()) {
/* 1323 */         CategoryPlot subplot = iterator.next();
/* 1324 */         if (subplot != null) {
/* 1325 */           applyToPlot((Plot)subplot);
/*      */         }
/*      */       } 
/*      */     } 
/* 1329 */     if (plot instanceof CombinedRangeCategoryPlot) {
/* 1330 */       CombinedRangeCategoryPlot cp = (CombinedRangeCategoryPlot)plot;
/* 1331 */       Iterator<CategoryPlot> iterator = cp.getSubplots().iterator();
/* 1332 */       while (iterator.hasNext()) {
/* 1333 */         CategoryPlot subplot = iterator.next();
/* 1334 */         if (subplot != null) {
/* 1335 */           applyToPlot((Plot)subplot);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToXYPlot(XYPlot plot) {
/* 1347 */     plot.setAxisOffset(this.axisOffset);
/* 1348 */     plot.setDomainZeroBaselinePaint(this.baselinePaint);
/* 1349 */     plot.setRangeZeroBaselinePaint(this.baselinePaint);
/* 1350 */     plot.setDomainGridlinePaint(this.domainGridlinePaint);
/* 1351 */     plot.setRangeGridlinePaint(this.rangeGridlinePaint);
/* 1352 */     plot.setDomainCrosshairPaint(this.crosshairPaint);
/* 1353 */     plot.setRangeCrosshairPaint(this.crosshairPaint);
/* 1354 */     plot.setShadowGenerator(this.shadowGenerator);
/*      */ 
/*      */     
/* 1357 */     int domainAxisCount = plot.getDomainAxisCount();
/* 1358 */     for (int i = 0; i < domainAxisCount; i++) {
/* 1359 */       ValueAxis axis = plot.getDomainAxis(i);
/* 1360 */       if (axis != null) {
/* 1361 */         applyToValueAxis(axis);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1366 */     int rangeAxisCount = plot.getRangeAxisCount();
/* 1367 */     for (int j = 0; j < rangeAxisCount; j++) {
/* 1368 */       ValueAxis axis = plot.getRangeAxis(j);
/* 1369 */       if (axis != null) {
/* 1370 */         applyToValueAxis(axis);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1375 */     int rendererCount = plot.getRendererCount();
/* 1376 */     for (int k = 0; k < rendererCount; k++) {
/* 1377 */       XYItemRenderer r = plot.getRenderer(k);
/* 1378 */       if (r != null) {
/* 1379 */         applyToXYItemRenderer(r);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1384 */     Iterator<XYAnnotation> iter = plot.getAnnotations().iterator();
/* 1385 */     while (iter.hasNext()) {
/* 1386 */       XYAnnotation a = iter.next();
/* 1387 */       applyToXYAnnotation(a);
/*      */     } 
/*      */     
/* 1390 */     if (plot instanceof CombinedDomainXYPlot) {
/* 1391 */       CombinedDomainXYPlot cp = (CombinedDomainXYPlot)plot;
/* 1392 */       Iterator<XYPlot> iterator = cp.getSubplots().iterator();
/* 1393 */       while (iterator.hasNext()) {
/* 1394 */         XYPlot subplot = iterator.next();
/* 1395 */         if (subplot != null) {
/* 1396 */           applyToPlot((Plot)subplot);
/*      */         }
/*      */       } 
/*      */     } 
/* 1400 */     if (plot instanceof CombinedRangeXYPlot) {
/* 1401 */       CombinedRangeXYPlot cp = (CombinedRangeXYPlot)plot;
/* 1402 */       Iterator<XYPlot> iterator = cp.getSubplots().iterator();
/* 1403 */       while (iterator.hasNext()) {
/* 1404 */         XYPlot subplot = iterator.next();
/* 1405 */         if (subplot != null) {
/* 1406 */           applyToPlot((Plot)subplot);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToFastScatterPlot(FastScatterPlot plot) {
/* 1418 */     plot.setDomainGridlinePaint(this.domainGridlinePaint);
/* 1419 */     plot.setRangeGridlinePaint(this.rangeGridlinePaint);
/* 1420 */     ValueAxis xAxis = plot.getDomainAxis();
/* 1421 */     if (xAxis != null) {
/* 1422 */       applyToValueAxis(xAxis);
/*      */     }
/* 1424 */     ValueAxis yAxis = plot.getRangeAxis();
/* 1425 */     if (yAxis != null) {
/* 1426 */       applyToValueAxis(yAxis);
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
/*      */   protected void applyToPolarPlot(PolarPlot plot) {
/* 1438 */     plot.setAngleLabelFont(this.regularFont);
/* 1439 */     plot.setAngleLabelPaint(this.tickLabelPaint);
/* 1440 */     plot.setAngleGridlinePaint(this.domainGridlinePaint);
/* 1441 */     plot.setRadiusGridlinePaint(this.rangeGridlinePaint);
/* 1442 */     ValueAxis axis = plot.getAxis();
/* 1443 */     if (axis != null) {
/* 1444 */       applyToValueAxis(axis);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToSpiderWebPlot(SpiderWebPlot plot) {
/* 1454 */     plot.setLabelFont(this.regularFont);
/* 1455 */     plot.setLabelPaint(this.axisLabelPaint);
/* 1456 */     plot.setAxisLinePaint(this.axisLabelPaint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToMeterPlot(MeterPlot plot) {
/* 1465 */     plot.setDialBackgroundPaint(this.plotBackgroundPaint);
/* 1466 */     plot.setValueFont(this.largeFont);
/* 1467 */     plot.setValuePaint(this.axisLabelPaint);
/* 1468 */     plot.setDialOutlinePaint(this.plotOutlinePaint);
/* 1469 */     plot.setNeedlePaint(this.thermometerPaint);
/* 1470 */     plot.setTickLabelFont(this.regularFont);
/* 1471 */     plot.setTickLabelPaint(this.tickLabelPaint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToThermometerPlot(ThermometerPlot plot) {
/* 1481 */     plot.setValueFont(this.largeFont);
/* 1482 */     plot.setThermometerPaint(this.thermometerPaint);
/* 1483 */     ValueAxis axis = plot.getRangeAxis();
/* 1484 */     if (axis != null) {
/* 1485 */       applyToValueAxis(axis);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToCategoryAxis(CategoryAxis axis) {
/* 1495 */     axis.setLabelFont(this.largeFont);
/* 1496 */     axis.setLabelPaint(this.axisLabelPaint);
/* 1497 */     axis.setTickLabelFont(this.regularFont);
/* 1498 */     axis.setTickLabelPaint(this.tickLabelPaint);
/* 1499 */     if (axis instanceof SubCategoryAxis) {
/* 1500 */       SubCategoryAxis sca = (SubCategoryAxis)axis;
/* 1501 */       sca.setSubLabelFont(this.regularFont);
/* 1502 */       sca.setSubLabelPaint(this.tickLabelPaint);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToValueAxis(ValueAxis axis) {
/* 1512 */     axis.setLabelFont(this.largeFont);
/* 1513 */     axis.setLabelPaint(this.axisLabelPaint);
/* 1514 */     axis.setTickLabelFont(this.regularFont);
/* 1515 */     axis.setTickLabelPaint(this.tickLabelPaint);
/* 1516 */     if (axis instanceof SymbolAxis) {
/* 1517 */       applyToSymbolAxis((SymbolAxis)axis);
/*      */     }
/* 1519 */     if (axis instanceof PeriodAxis) {
/* 1520 */       applyToPeriodAxis((PeriodAxis)axis);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToSymbolAxis(SymbolAxis axis) {
/* 1530 */     axis.setGridBandPaint(this.gridBandPaint);
/* 1531 */     axis.setGridBandAlternatePaint(this.gridBandAlternatePaint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToPeriodAxis(PeriodAxis axis) {
/* 1540 */     PeriodAxisLabelInfo[] info = axis.getLabelInfo();
/* 1541 */     for (int i = 0; i < info.length; i++) {
/* 1542 */       PeriodAxisLabelInfo e = info[i];
/*      */ 
/*      */ 
/*      */       
/* 1546 */       PeriodAxisLabelInfo n = new PeriodAxisLabelInfo(e.getPeriodClass(), e.getDateFormat(), e.getPadding(), this.regularFont, this.tickLabelPaint, e.getDrawDividers(), e.getDividerStroke(), e.getDividerPaint());
/* 1547 */       info[i] = n;
/*      */     } 
/* 1549 */     axis.setLabelInfo(info);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToAbstractRenderer(AbstractRenderer renderer) {
/* 1558 */     if (renderer.getAutoPopulateSeriesPaint()) {
/* 1559 */       renderer.clearSeriesPaints(false);
/*      */     }
/* 1561 */     if (renderer.getAutoPopulateSeriesStroke()) {
/* 1562 */       renderer.clearSeriesStrokes(false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToCategoryItemRenderer(CategoryItemRenderer renderer) {
/* 1572 */     ParamChecks.nullNotPermitted(renderer, "renderer");
/*      */     
/* 1574 */     if (renderer instanceof AbstractRenderer) {
/* 1575 */       applyToAbstractRenderer((AbstractRenderer)renderer);
/*      */     }
/*      */     
/* 1578 */     renderer.setBaseItemLabelFont(this.regularFont);
/* 1579 */     renderer.setBaseItemLabelPaint(this.itemLabelPaint);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1584 */     if (renderer instanceof BarRenderer) {
/* 1585 */       BarRenderer br = (BarRenderer)renderer;
/* 1586 */       br.setBarPainter(this.barPainter);
/* 1587 */       br.setShadowVisible(this.shadowVisible);
/* 1588 */       br.setShadowPaint(this.shadowPaint);
/*      */     } 
/*      */ 
/*      */     
/* 1592 */     if (renderer instanceof BarRenderer3D) {
/* 1593 */       BarRenderer3D br3d = (BarRenderer3D)renderer;
/* 1594 */       br3d.setWallPaint(this.wallPaint);
/*      */     } 
/*      */ 
/*      */     
/* 1598 */     if (renderer instanceof LineRenderer3D) {
/* 1599 */       LineRenderer3D lr3d = (LineRenderer3D)renderer;
/* 1600 */       lr3d.setWallPaint(this.wallPaint);
/*      */     } 
/*      */ 
/*      */     
/* 1604 */     if (renderer instanceof StatisticalBarRenderer) {
/* 1605 */       StatisticalBarRenderer sbr = (StatisticalBarRenderer)renderer;
/* 1606 */       sbr.setErrorIndicatorPaint(this.errorIndicatorPaint);
/*      */     } 
/*      */ 
/*      */     
/* 1610 */     if (renderer instanceof MinMaxCategoryRenderer) {
/* 1611 */       MinMaxCategoryRenderer mmcr = (MinMaxCategoryRenderer)renderer;
/* 1612 */       mmcr.setGroupPaint(this.errorIndicatorPaint);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToXYItemRenderer(XYItemRenderer renderer) {
/* 1622 */     ParamChecks.nullNotPermitted(renderer, "renderer");
/* 1623 */     if (renderer instanceof AbstractRenderer) {
/* 1624 */       applyToAbstractRenderer((AbstractRenderer)renderer);
/*      */     }
/* 1626 */     renderer.setBaseItemLabelFont(this.regularFont);
/* 1627 */     renderer.setBaseItemLabelPaint(this.itemLabelPaint);
/* 1628 */     if (renderer instanceof XYBarRenderer) {
/* 1629 */       XYBarRenderer br = (XYBarRenderer)renderer;
/* 1630 */       br.setBarPainter(this.xyBarPainter);
/* 1631 */       br.setShadowVisible(this.shadowVisible);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyToXYAnnotation(XYAnnotation annotation) {
/* 1641 */     ParamChecks.nullNotPermitted(annotation, "annotation");
/* 1642 */     if (annotation instanceof XYTextAnnotation) {
/* 1643 */       XYTextAnnotation xyta = (XYTextAnnotation)annotation;
/* 1644 */       xyta.setFont(this.smallFont);
/* 1645 */       xyta.setPaint(this.itemLabelPaint);
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
/*      */   public boolean equals(Object obj) {
/* 1658 */     if (obj == this) {
/* 1659 */       return true;
/*      */     }
/* 1661 */     if (!(obj instanceof StandardChartTheme)) {
/* 1662 */       return false;
/*      */     }
/* 1664 */     StandardChartTheme that = (StandardChartTheme)obj;
/* 1665 */     if (!this.name.equals(that.name)) {
/* 1666 */       return false;
/*      */     }
/* 1668 */     if (!this.extraLargeFont.equals(that.extraLargeFont)) {
/* 1669 */       return false;
/*      */     }
/* 1671 */     if (!this.largeFont.equals(that.largeFont)) {
/* 1672 */       return false;
/*      */     }
/* 1674 */     if (!this.regularFont.equals(that.regularFont)) {
/* 1675 */       return false;
/*      */     }
/* 1677 */     if (!this.smallFont.equals(that.smallFont)) {
/* 1678 */       return false;
/*      */     }
/* 1680 */     if (!PaintUtilities.equal(this.titlePaint, that.titlePaint)) {
/* 1681 */       return false;
/*      */     }
/* 1683 */     if (!PaintUtilities.equal(this.subtitlePaint, that.subtitlePaint)) {
/* 1684 */       return false;
/*      */     }
/* 1686 */     if (!PaintUtilities.equal(this.chartBackgroundPaint, that.chartBackgroundPaint))
/*      */     {
/* 1688 */       return false;
/*      */     }
/* 1690 */     if (!PaintUtilities.equal(this.legendBackgroundPaint, that.legendBackgroundPaint))
/*      */     {
/* 1692 */       return false;
/*      */     }
/* 1694 */     if (!PaintUtilities.equal(this.legendItemPaint, that.legendItemPaint)) {
/* 1695 */       return false;
/*      */     }
/* 1697 */     if (!this.drawingSupplier.equals(that.drawingSupplier)) {
/* 1698 */       return false;
/*      */     }
/* 1700 */     if (!PaintUtilities.equal(this.plotBackgroundPaint, that.plotBackgroundPaint))
/*      */     {
/* 1702 */       return false;
/*      */     }
/* 1704 */     if (!PaintUtilities.equal(this.plotOutlinePaint, that.plotOutlinePaint))
/*      */     {
/* 1706 */       return false;
/*      */     }
/* 1708 */     if (!this.labelLinkStyle.equals(that.labelLinkStyle)) {
/* 1709 */       return false;
/*      */     }
/* 1711 */     if (!PaintUtilities.equal(this.labelLinkPaint, that.labelLinkPaint)) {
/* 1712 */       return false;
/*      */     }
/* 1714 */     if (!PaintUtilities.equal(this.domainGridlinePaint, that.domainGridlinePaint))
/*      */     {
/* 1716 */       return false;
/*      */     }
/* 1718 */     if (!PaintUtilities.equal(this.rangeGridlinePaint, that.rangeGridlinePaint))
/*      */     {
/* 1720 */       return false;
/*      */     }
/* 1722 */     if (!PaintUtilities.equal(this.crosshairPaint, that.crosshairPaint)) {
/* 1723 */       return false;
/*      */     }
/* 1725 */     if (!this.axisOffset.equals(that.axisOffset)) {
/* 1726 */       return false;
/*      */     }
/* 1728 */     if (!PaintUtilities.equal(this.axisLabelPaint, that.axisLabelPaint)) {
/* 1729 */       return false;
/*      */     }
/* 1731 */     if (!PaintUtilities.equal(this.tickLabelPaint, that.tickLabelPaint)) {
/* 1732 */       return false;
/*      */     }
/* 1734 */     if (!PaintUtilities.equal(this.itemLabelPaint, that.itemLabelPaint)) {
/* 1735 */       return false;
/*      */     }
/* 1737 */     if (this.shadowVisible != that.shadowVisible) {
/* 1738 */       return false;
/*      */     }
/* 1740 */     if (!PaintUtilities.equal(this.shadowPaint, that.shadowPaint)) {
/* 1741 */       return false;
/*      */     }
/* 1743 */     if (!this.barPainter.equals(that.barPainter)) {
/* 1744 */       return false;
/*      */     }
/* 1746 */     if (!this.xyBarPainter.equals(that.xyBarPainter)) {
/* 1747 */       return false;
/*      */     }
/* 1749 */     if (!PaintUtilities.equal(this.thermometerPaint, that.thermometerPaint))
/*      */     {
/* 1751 */       return false;
/*      */     }
/* 1753 */     if (!PaintUtilities.equal(this.wallPaint, that.wallPaint)) {
/* 1754 */       return false;
/*      */     }
/* 1756 */     if (!PaintUtilities.equal(this.errorIndicatorPaint, that.errorIndicatorPaint))
/*      */     {
/* 1758 */       return false;
/*      */     }
/* 1760 */     if (!PaintUtilities.equal(this.gridBandPaint, that.gridBandPaint)) {
/* 1761 */       return false;
/*      */     }
/* 1763 */     if (!PaintUtilities.equal(this.gridBandAlternatePaint, that.gridBandAlternatePaint))
/*      */     {
/* 1765 */       return false;
/*      */     }
/* 1767 */     return true;
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
/* 1779 */     return super.clone();
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
/* 1790 */     stream.defaultWriteObject();
/* 1791 */     SerialUtilities.writePaint(this.titlePaint, stream);
/* 1792 */     SerialUtilities.writePaint(this.subtitlePaint, stream);
/* 1793 */     SerialUtilities.writePaint(this.chartBackgroundPaint, stream);
/* 1794 */     SerialUtilities.writePaint(this.legendBackgroundPaint, stream);
/* 1795 */     SerialUtilities.writePaint(this.legendItemPaint, stream);
/* 1796 */     SerialUtilities.writePaint(this.plotBackgroundPaint, stream);
/* 1797 */     SerialUtilities.writePaint(this.plotOutlinePaint, stream);
/* 1798 */     SerialUtilities.writePaint(this.labelLinkPaint, stream);
/* 1799 */     SerialUtilities.writePaint(this.baselinePaint, stream);
/* 1800 */     SerialUtilities.writePaint(this.domainGridlinePaint, stream);
/* 1801 */     SerialUtilities.writePaint(this.rangeGridlinePaint, stream);
/* 1802 */     SerialUtilities.writePaint(this.crosshairPaint, stream);
/* 1803 */     SerialUtilities.writePaint(this.axisLabelPaint, stream);
/* 1804 */     SerialUtilities.writePaint(this.tickLabelPaint, stream);
/* 1805 */     SerialUtilities.writePaint(this.itemLabelPaint, stream);
/* 1806 */     SerialUtilities.writePaint(this.shadowPaint, stream);
/* 1807 */     SerialUtilities.writePaint(this.thermometerPaint, stream);
/* 1808 */     SerialUtilities.writePaint(this.wallPaint, stream);
/* 1809 */     SerialUtilities.writePaint(this.errorIndicatorPaint, stream);
/* 1810 */     SerialUtilities.writePaint(this.gridBandPaint, stream);
/* 1811 */     SerialUtilities.writePaint(this.gridBandAlternatePaint, stream);
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
/* 1824 */     stream.defaultReadObject();
/* 1825 */     this.titlePaint = SerialUtilities.readPaint(stream);
/* 1826 */     this.subtitlePaint = SerialUtilities.readPaint(stream);
/* 1827 */     this.chartBackgroundPaint = SerialUtilities.readPaint(stream);
/* 1828 */     this.legendBackgroundPaint = SerialUtilities.readPaint(stream);
/* 1829 */     this.legendItemPaint = SerialUtilities.readPaint(stream);
/* 1830 */     this.plotBackgroundPaint = SerialUtilities.readPaint(stream);
/* 1831 */     this.plotOutlinePaint = SerialUtilities.readPaint(stream);
/* 1832 */     this.labelLinkPaint = SerialUtilities.readPaint(stream);
/* 1833 */     this.baselinePaint = SerialUtilities.readPaint(stream);
/* 1834 */     this.domainGridlinePaint = SerialUtilities.readPaint(stream);
/* 1835 */     this.rangeGridlinePaint = SerialUtilities.readPaint(stream);
/* 1836 */     this.crosshairPaint = SerialUtilities.readPaint(stream);
/* 1837 */     this.axisLabelPaint = SerialUtilities.readPaint(stream);
/* 1838 */     this.tickLabelPaint = SerialUtilities.readPaint(stream);
/* 1839 */     this.itemLabelPaint = SerialUtilities.readPaint(stream);
/* 1840 */     this.shadowPaint = SerialUtilities.readPaint(stream);
/* 1841 */     this.thermometerPaint = SerialUtilities.readPaint(stream);
/* 1842 */     this.wallPaint = SerialUtilities.readPaint(stream);
/* 1843 */     this.errorIndicatorPaint = SerialUtilities.readPaint(stream);
/* 1844 */     this.gridBandPaint = SerialUtilities.readPaint(stream);
/* 1845 */     this.gridBandAlternatePaint = SerialUtilities.readPaint(stream);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/StandardChartTheme.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */