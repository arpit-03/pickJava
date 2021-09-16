/*      */ package org.jfree.chart.plot;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Arc2D;
/*      */ import java.awt.geom.Ellipse2D;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ResourceBundle;
/*      */ import org.jfree.chart.LegendItem;
/*      */ import org.jfree.chart.LegendItemCollection;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.chart.util.ResourceBundleWrapper;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.general.DatasetChangeEvent;
/*      */ import org.jfree.data.general.ValueDataset;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.text.TextUtilities;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ import org.jfree.ui.TextAnchor;
/*      */ import org.jfree.util.ObjectUtilities;
/*      */ import org.jfree.util.PaintUtilities;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MeterPlot
/*      */   extends Plot
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   private static final long serialVersionUID = 2987472457734470962L;
/*  142 */   static final Paint DEFAULT_DIAL_BACKGROUND_PAINT = Color.black;
/*      */ 
/*      */   
/*  145 */   static final Paint DEFAULT_NEEDLE_PAINT = Color.green;
/*      */ 
/*      */   
/*  148 */   static final Font DEFAULT_VALUE_FONT = new Font("SansSerif", 1, 12);
/*      */ 
/*      */   
/*  151 */   static final Paint DEFAULT_VALUE_PAINT = Color.yellow;
/*      */ 
/*      */   
/*      */   public static final int DEFAULT_METER_ANGLE = 270;
/*      */ 
/*      */   
/*      */   public static final float DEFAULT_BORDER_SIZE = 3.0F;
/*      */ 
/*      */   
/*      */   public static final float DEFAULT_CIRCLE_SIZE = 10.0F;
/*      */ 
/*      */   
/*  163 */   public static final Font DEFAULT_LABEL_FONT = new Font("SansSerif", 1, 10);
/*      */ 
/*      */ 
/*      */   
/*      */   private ValueDataset dataset;
/*      */ 
/*      */ 
/*      */   
/*      */   private DialShape shape;
/*      */ 
/*      */   
/*      */   private int meterAngle;
/*      */ 
/*      */   
/*      */   private Range range;
/*      */ 
/*      */   
/*      */   private double tickSize;
/*      */ 
/*      */   
/*      */   private transient Paint tickPaint;
/*      */ 
/*      */   
/*      */   private String units;
/*      */ 
/*      */   
/*      */   private Font valueFont;
/*      */ 
/*      */   
/*      */   private transient Paint valuePaint;
/*      */ 
/*      */   
/*      */   private boolean drawBorder;
/*      */ 
/*      */   
/*      */   private transient Paint dialOutlinePaint;
/*      */ 
/*      */   
/*      */   private transient Paint dialBackgroundPaint;
/*      */ 
/*      */   
/*      */   private transient Paint needlePaint;
/*      */ 
/*      */   
/*      */   private boolean tickLabelsVisible;
/*      */ 
/*      */   
/*      */   private Font tickLabelFont;
/*      */ 
/*      */   
/*      */   private transient Paint tickLabelPaint;
/*      */ 
/*      */   
/*      */   private NumberFormat tickLabelFormat;
/*      */ 
/*      */   
/*  219 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List intervals;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MeterPlot() {
/*  233 */     this((ValueDataset)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MeterPlot(ValueDataset dataset) {
/*  243 */     this.shape = DialShape.CIRCLE;
/*  244 */     this.meterAngle = 270;
/*  245 */     this.range = new Range(0.0D, 100.0D);
/*  246 */     this.tickSize = 10.0D;
/*  247 */     this.tickPaint = Color.white;
/*  248 */     this.units = "Units";
/*  249 */     this.needlePaint = DEFAULT_NEEDLE_PAINT;
/*  250 */     this.tickLabelsVisible = true;
/*  251 */     this.tickLabelFont = DEFAULT_LABEL_FONT;
/*  252 */     this.tickLabelPaint = Color.black;
/*  253 */     this.tickLabelFormat = NumberFormat.getInstance();
/*  254 */     this.valueFont = DEFAULT_VALUE_FONT;
/*  255 */     this.valuePaint = DEFAULT_VALUE_PAINT;
/*  256 */     this.dialBackgroundPaint = DEFAULT_DIAL_BACKGROUND_PAINT;
/*  257 */     this.intervals = new ArrayList();
/*  258 */     setDataset(dataset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DialShape getDialShape() {
/*  269 */     return this.shape;
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
/*      */   public void setDialShape(DialShape shape) {
/*  281 */     ParamChecks.nullNotPermitted(shape, "shape");
/*  282 */     this.shape = shape;
/*  283 */     fireChangeEvent();
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
/*      */   public int getMeterAngle() {
/*  295 */     return this.meterAngle;
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
/*      */   public void setMeterAngle(int angle) {
/*  307 */     if (angle < 1 || angle > 360) {
/*  308 */       throw new IllegalArgumentException("Invalid 'angle' (" + angle + ")");
/*      */     }
/*      */     
/*  311 */     this.meterAngle = angle;
/*  312 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Range getRange() {
/*  323 */     return this.range;
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
/*      */   public void setRange(Range range) {
/*  336 */     ParamChecks.nullNotPermitted(range, "range");
/*  337 */     if (range.getLength() <= 0.0D) {
/*  338 */       throw new IllegalArgumentException("Range length must be positive.");
/*      */     }
/*      */     
/*  341 */     this.range = range;
/*  342 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getTickSize() {
/*  353 */     return this.tickSize;
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
/*      */   public void setTickSize(double size) {
/*  365 */     if (size <= 0.0D) {
/*  366 */       throw new IllegalArgumentException("Requires 'size' > 0.");
/*      */     }
/*  368 */     this.tickSize = size;
/*  369 */     fireChangeEvent();
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
/*      */   public Paint getTickPaint() {
/*  381 */     return this.tickPaint;
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
/*      */   public void setTickPaint(Paint paint) {
/*  393 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  394 */     this.tickPaint = paint;
/*  395 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnits() {
/*  406 */     return this.units;
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
/*      */   public void setUnits(String units) {
/*  418 */     this.units = units;
/*  419 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getNeedlePaint() {
/*  430 */     return this.needlePaint;
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
/*      */   public void setNeedlePaint(Paint paint) {
/*  442 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  443 */     this.needlePaint = paint;
/*  444 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTickLabelsVisible() {
/*  455 */     return this.tickLabelsVisible;
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
/*      */   public void setTickLabelsVisible(boolean visible) {
/*  467 */     if (this.tickLabelsVisible != visible) {
/*  468 */       this.tickLabelsVisible = visible;
/*  469 */       fireChangeEvent();
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
/*      */   public Font getTickLabelFont() {
/*  481 */     return this.tickLabelFont;
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
/*      */   public void setTickLabelFont(Font font) {
/*  493 */     ParamChecks.nullNotPermitted(font, "font");
/*  494 */     if (!this.tickLabelFont.equals(font)) {
/*  495 */       this.tickLabelFont = font;
/*  496 */       fireChangeEvent();
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
/*      */   public Paint getTickLabelPaint() {
/*  508 */     return this.tickLabelPaint;
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
/*      */   public void setTickLabelPaint(Paint paint) {
/*  520 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  521 */     if (!this.tickLabelPaint.equals(paint)) {
/*  522 */       this.tickLabelPaint = paint;
/*  523 */       fireChangeEvent();
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
/*      */   public NumberFormat getTickLabelFormat() {
/*  535 */     return this.tickLabelFormat;
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
/*      */   public void setTickLabelFormat(NumberFormat format) {
/*  547 */     ParamChecks.nullNotPermitted(format, "format");
/*  548 */     this.tickLabelFormat = format;
/*  549 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getValueFont() {
/*  560 */     return this.valueFont;
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
/*      */   public void setValueFont(Font font) {
/*  572 */     ParamChecks.nullNotPermitted(font, "font");
/*  573 */     this.valueFont = font;
/*  574 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getValuePaint() {
/*  585 */     return this.valuePaint;
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
/*      */   public void setValuePaint(Paint paint) {
/*  597 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  598 */     this.valuePaint = paint;
/*  599 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getDialBackgroundPaint() {
/*  610 */     return this.dialBackgroundPaint;
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
/*      */   public void setDialBackgroundPaint(Paint paint) {
/*  622 */     this.dialBackgroundPaint = paint;
/*  623 */     fireChangeEvent();
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
/*      */   public boolean getDrawBorder() {
/*  635 */     return this.drawBorder;
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
/*      */   public void setDrawBorder(boolean draw) {
/*  649 */     this.drawBorder = draw;
/*  650 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getDialOutlinePaint() {
/*  661 */     return this.dialOutlinePaint;
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
/*      */   public void setDialOutlinePaint(Paint paint) {
/*  673 */     this.dialOutlinePaint = paint;
/*  674 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueDataset getDataset() {
/*  685 */     return this.dataset;
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
/*      */   public void setDataset(ValueDataset dataset) {
/*  700 */     ValueDataset existing = this.dataset;
/*  701 */     if (existing != null) {
/*  702 */       existing.removeChangeListener(this);
/*      */     }
/*      */ 
/*      */     
/*  706 */     this.dataset = dataset;
/*  707 */     if (dataset != null) {
/*  708 */       setDatasetGroup(dataset.getGroup());
/*  709 */       dataset.addChangeListener(this);
/*      */     } 
/*      */ 
/*      */     
/*  713 */     DatasetChangeEvent event = new DatasetChangeEvent(this, (Dataset)dataset);
/*  714 */     datasetChanged(event);
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
/*      */   public List getIntervals() {
/*  726 */     return Collections.unmodifiableList(this.intervals);
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
/*      */   public void addInterval(MeterInterval interval) {
/*  739 */     ParamChecks.nullNotPermitted(interval, "interval");
/*  740 */     this.intervals.add(interval);
/*  741 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearIntervals() {
/*  751 */     this.intervals.clear();
/*  752 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LegendItemCollection getLegendItems() {
/*  762 */     LegendItemCollection result = new LegendItemCollection();
/*  763 */     Iterator<MeterInterval> iterator = this.intervals.iterator();
/*  764 */     while (iterator.hasNext()) {
/*  765 */       MeterInterval mi = iterator.next();
/*  766 */       Paint color = mi.getBackgroundPaint();
/*  767 */       if (color == null) {
/*  768 */         color = mi.getOutlinePaint();
/*      */       }
/*  770 */       LegendItem item = new LegendItem(mi.getLabel(), mi.getLabel(), null, null, new Rectangle2D.Double(-4.0D, -4.0D, 8.0D, 8.0D), color);
/*      */ 
/*      */       
/*  773 */       item.setDataset((Dataset)getDataset());
/*  774 */       result.add(item);
/*      */     } 
/*  776 */     return result;
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
/*      */   public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info) {
/*  793 */     if (info != null) {
/*  794 */       info.setPlotArea(area);
/*      */     }
/*      */ 
/*      */     
/*  798 */     RectangleInsets insets = getInsets();
/*  799 */     insets.trim(area);
/*      */     
/*  801 */     area.setRect(area.getX() + 4.0D, area.getY() + 4.0D, area.getWidth() - 8.0D, area
/*  802 */         .getHeight() - 8.0D);
/*      */ 
/*      */     
/*  805 */     if (this.drawBorder) {
/*  806 */       drawBackground(g2, area);
/*      */     }
/*      */ 
/*      */     
/*  810 */     double gapHorizontal = 6.0D;
/*  811 */     double gapVertical = 6.0D;
/*  812 */     double meterX = area.getX() + gapHorizontal / 2.0D;
/*  813 */     double meterY = area.getY() + gapVertical / 2.0D;
/*  814 */     double meterW = area.getWidth() - gapHorizontal;
/*      */ 
/*      */     
/*  817 */     double meterH = area.getHeight() - gapVertical + ((this.meterAngle <= 180 && this.shape != DialShape.CIRCLE) ? (area.getHeight() / 1.25D) : 0.0D);
/*      */     
/*  819 */     double min = Math.min(meterW, meterH) / 2.0D;
/*  820 */     meterX = (meterX + meterX + meterW) / 2.0D - min;
/*  821 */     meterY = (meterY + meterY + meterH) / 2.0D - min;
/*  822 */     meterW = 2.0D * min;
/*  823 */     meterH = 2.0D * min;
/*      */     
/*  825 */     Rectangle2D meterArea = new Rectangle2D.Double(meterX, meterY, meterW, meterH);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  830 */     Rectangle2D.Double originalArea = new Rectangle2D.Double(meterArea.getX() - 4.0D, meterArea.getY() - 4.0D, meterArea.getWidth() + 8.0D, meterArea.getHeight() + 8.0D);
/*      */     
/*  832 */     double meterMiddleX = meterArea.getCenterX();
/*  833 */     double meterMiddleY = meterArea.getCenterY();
/*      */ 
/*      */     
/*  836 */     ValueDataset data = getDataset();
/*  837 */     if (data != null) {
/*  838 */       double dataMin = this.range.getLowerBound();
/*  839 */       double dataMax = this.range.getUpperBound();
/*      */       
/*  841 */       Shape savedClip = g2.getClip();
/*  842 */       g2.clip(originalArea);
/*  843 */       Composite originalComposite = g2.getComposite();
/*  844 */       g2.setComposite(AlphaComposite.getInstance(3, 
/*  845 */             getForegroundAlpha()));
/*      */       
/*  847 */       if (this.dialBackgroundPaint != null) {
/*  848 */         fillArc(g2, originalArea, dataMin, dataMax, this.dialBackgroundPaint, true);
/*      */       }
/*      */       
/*  851 */       drawTicks(g2, meterArea, dataMin, dataMax);
/*  852 */       drawArcForInterval(g2, meterArea, new MeterInterval("", this.range, this.dialOutlinePaint, new BasicStroke(1.0F), null));
/*      */ 
/*      */       
/*  855 */       Iterator<MeterInterval> iterator = this.intervals.iterator();
/*  856 */       while (iterator.hasNext()) {
/*  857 */         MeterInterval interval = iterator.next();
/*  858 */         drawArcForInterval(g2, meterArea, interval);
/*      */       } 
/*      */       
/*  861 */       Number n = data.getValue();
/*  862 */       if (n != null) {
/*  863 */         double value = n.doubleValue();
/*  864 */         drawValueLabel(g2, meterArea);
/*      */         
/*  866 */         if (this.range.contains(value)) {
/*  867 */           g2.setPaint(this.needlePaint);
/*  868 */           g2.setStroke(new BasicStroke(2.0F));
/*      */           
/*  870 */           double radius = meterArea.getWidth() / 2.0D + 3.0D + 15.0D;
/*      */           
/*  872 */           double valueAngle = valueToAngle(value);
/*      */           
/*  874 */           double valueP1 = meterMiddleX + radius * Math.cos(Math.PI * valueAngle / 180.0D);
/*      */           
/*  876 */           double valueP2 = meterMiddleY - radius * Math.sin(Math.PI * valueAngle / 180.0D);
/*      */           
/*  878 */           Polygon arrow = new Polygon();
/*  879 */           if ((valueAngle > 135.0D && valueAngle < 225.0D) || (valueAngle < 45.0D && valueAngle > -45.0D)) {
/*      */ 
/*      */             
/*  882 */             double valueP3 = meterMiddleY - 2.5D;
/*      */             
/*  884 */             double valueP4 = meterMiddleY + 2.5D;
/*      */             
/*  886 */             arrow.addPoint((int)meterMiddleX, (int)valueP3);
/*  887 */             arrow.addPoint((int)meterMiddleX, (int)valueP4);
/*      */           }
/*      */           else {
/*      */             
/*  891 */             arrow.addPoint((int)(meterMiddleX - 2.5D), (int)meterMiddleY);
/*      */             
/*  893 */             arrow.addPoint((int)(meterMiddleX + 2.5D), (int)meterMiddleY);
/*      */           } 
/*      */           
/*  896 */           arrow.addPoint((int)valueP1, (int)valueP2);
/*  897 */           g2.fill(arrow);
/*      */           
/*  899 */           Ellipse2D circle = new Ellipse2D.Double(meterMiddleX - 5.0D, meterMiddleY - 5.0D, 10.0D, 10.0D);
/*      */ 
/*      */ 
/*      */           
/*  903 */           g2.fill(circle);
/*      */         } 
/*      */       } 
/*      */       
/*  907 */       g2.setClip(savedClip);
/*  908 */       g2.setComposite(originalComposite);
/*      */     } 
/*      */     
/*  911 */     if (this.drawBorder) {
/*  912 */       drawOutline(g2, area);
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
/*      */   protected void drawArcForInterval(Graphics2D g2, Rectangle2D meterArea, MeterInterval interval) {
/*  927 */     double minValue = interval.getRange().getLowerBound();
/*  928 */     double maxValue = interval.getRange().getUpperBound();
/*  929 */     Paint outlinePaint = interval.getOutlinePaint();
/*  930 */     Stroke outlineStroke = interval.getOutlineStroke();
/*  931 */     Paint backgroundPaint = interval.getBackgroundPaint();
/*      */     
/*  933 */     if (backgroundPaint != null) {
/*  934 */       fillArc(g2, meterArea, minValue, maxValue, backgroundPaint, false);
/*      */     }
/*  936 */     if (outlinePaint != null) {
/*  937 */       if (outlineStroke != null) {
/*  938 */         drawArc(g2, meterArea, minValue, maxValue, outlinePaint, outlineStroke);
/*      */       }
/*      */       
/*  941 */       drawTick(g2, meterArea, minValue, true);
/*  942 */       drawTick(g2, meterArea, maxValue, true);
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
/*      */   protected void drawArc(Graphics2D g2, Rectangle2D area, double minValue, double maxValue, Paint paint, Stroke stroke) {
/*  959 */     double startAngle = valueToAngle(maxValue);
/*  960 */     double endAngle = valueToAngle(minValue);
/*  961 */     double extent = endAngle - startAngle;
/*      */     
/*  963 */     double x = area.getX();
/*  964 */     double y = area.getY();
/*  965 */     double w = area.getWidth();
/*  966 */     double h = area.getHeight();
/*  967 */     g2.setPaint(paint);
/*  968 */     g2.setStroke(stroke);
/*      */     
/*  970 */     if (paint != null && stroke != null) {
/*  971 */       Arc2D.Double arc = new Arc2D.Double(x, y, w, h, startAngle, extent, 0);
/*      */       
/*  973 */       g2.setPaint(paint);
/*  974 */       g2.setStroke(stroke);
/*  975 */       g2.draw(arc);
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
/*      */   protected void fillArc(Graphics2D g2, Rectangle2D area, double minValue, double maxValue, Paint paint, boolean dial) {
/*  994 */     ParamChecks.nullNotPermitted(paint, "paint");
/*  995 */     double startAngle = valueToAngle(maxValue);
/*  996 */     double endAngle = valueToAngle(minValue);
/*  997 */     double extent = endAngle - startAngle;
/*      */     
/*  999 */     double x = area.getX();
/* 1000 */     double y = area.getY();
/* 1001 */     double w = area.getWidth();
/* 1002 */     double h = area.getHeight();
/* 1003 */     int joinType = 0;
/* 1004 */     if (this.shape == DialShape.PIE) {
/* 1005 */       joinType = 2;
/*      */     }
/* 1007 */     else if (this.shape == DialShape.CHORD) {
/* 1008 */       if (dial && this.meterAngle > 180) {
/* 1009 */         joinType = 1;
/*      */       } else {
/*      */         
/* 1012 */         joinType = 2;
/*      */       }
/*      */     
/* 1015 */     } else if (this.shape == DialShape.CIRCLE) {
/* 1016 */       joinType = 2;
/* 1017 */       if (dial) {
/* 1018 */         extent = 360.0D;
/*      */       }
/*      */     } else {
/*      */       
/* 1022 */       throw new IllegalStateException("DialShape not recognised.");
/*      */     } 
/*      */     
/* 1025 */     g2.setPaint(paint);
/* 1026 */     Arc2D.Double arc = new Arc2D.Double(x, y, w, h, startAngle, extent, joinType);
/*      */     
/* 1028 */     g2.fill(arc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double valueToAngle(double value) {
/* 1039 */     value -= this.range.getLowerBound();
/* 1040 */     double baseAngle = (180 + (this.meterAngle - 180) / 2);
/* 1041 */     return baseAngle - value / this.range.getLength() * this.meterAngle;
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
/*      */   protected void drawTicks(Graphics2D g2, Rectangle2D meterArea, double minValue, double maxValue) {
/*      */     double v;
/* 1054 */     for (v = minValue; v <= maxValue; v += this.tickSize) {
/* 1055 */       drawTick(g2, meterArea, v);
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
/*      */   protected void drawTick(Graphics2D g2, Rectangle2D meterArea, double value) {
/* 1068 */     drawTick(g2, meterArea, value, false);
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
/*      */   protected void drawTick(Graphics2D g2, Rectangle2D meterArea, double value, boolean label) {
/* 1082 */     double valueAngle = valueToAngle(value);
/*      */     
/* 1084 */     double meterMiddleX = meterArea.getCenterX();
/* 1085 */     double meterMiddleY = meterArea.getCenterY();
/*      */     
/* 1087 */     g2.setPaint(this.tickPaint);
/* 1088 */     g2.setStroke(new BasicStroke(2.0F));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1093 */     double radius = meterArea.getWidth() / 2.0D + 3.0D;
/* 1094 */     double radius1 = radius - 15.0D;
/*      */ 
/*      */     
/* 1097 */     double valueP1X = meterMiddleX + radius * Math.cos(Math.PI * valueAngle / 180.0D);
/*      */     
/* 1099 */     double valueP1Y = meterMiddleY - radius * Math.sin(Math.PI * valueAngle / 180.0D);
/*      */ 
/*      */     
/* 1102 */     double valueP2X = meterMiddleX + radius1 * Math.cos(Math.PI * valueAngle / 180.0D);
/*      */     
/* 1104 */     double valueP2Y = meterMiddleY - radius1 * Math.sin(Math.PI * valueAngle / 180.0D);
/*      */     
/* 1106 */     Line2D.Double line = new Line2D.Double(valueP1X, valueP1Y, valueP2X, valueP2Y);
/*      */     
/* 1108 */     g2.draw(line);
/*      */     
/* 1110 */     if (this.tickLabelsVisible && label) {
/*      */       
/* 1112 */       String tickLabel = this.tickLabelFormat.format(value);
/* 1113 */       g2.setFont(this.tickLabelFont);
/* 1114 */       g2.setPaint(this.tickLabelPaint);
/*      */       
/* 1116 */       FontMetrics fm = g2.getFontMetrics();
/*      */       
/* 1118 */       Rectangle2D tickLabelBounds = TextUtilities.getTextBounds(tickLabel, g2, fm);
/*      */       
/* 1120 */       double x = valueP2X;
/* 1121 */       double y = valueP2Y;
/* 1122 */       if (valueAngle == 90.0D || valueAngle == 270.0D) {
/* 1123 */         x -= tickLabelBounds.getWidth() / 2.0D;
/*      */       }
/* 1125 */       else if (valueAngle < 90.0D || valueAngle > 270.0D) {
/* 1126 */         x -= tickLabelBounds.getWidth();
/*      */       } 
/* 1128 */       if ((valueAngle > 135.0D && valueAngle < 225.0D) || valueAngle > 315.0D || valueAngle < 45.0D) {
/*      */         
/* 1130 */         y -= tickLabelBounds.getHeight() / 2.0D;
/*      */       } else {
/*      */         
/* 1133 */         y += tickLabelBounds.getHeight() / 2.0D;
/*      */       } 
/* 1135 */       g2.drawString(tickLabel, (float)x, (float)y);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawValueLabel(Graphics2D g2, Rectangle2D area) {
/* 1146 */     g2.setFont(this.valueFont);
/* 1147 */     g2.setPaint(this.valuePaint);
/* 1148 */     String valueStr = "No value";
/* 1149 */     if (this.dataset != null) {
/* 1150 */       Number n = this.dataset.getValue();
/* 1151 */       if (n != null) {
/* 1152 */         valueStr = this.tickLabelFormat.format(n.doubleValue()) + " " + this.units;
/*      */       }
/*      */     } 
/*      */     
/* 1156 */     float x = (float)area.getCenterX();
/* 1157 */     float y = (float)area.getCenterY() + 10.0F;
/* 1158 */     TextUtilities.drawAlignedString(valueStr, g2, x, y, TextAnchor.TOP_CENTER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlotType() {
/* 1169 */     return localizationResources.getString("Meter_Plot");
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
/*      */   public void zoom(double percent) {}
/*      */ 
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
/* 1194 */     if (obj == this) {
/* 1195 */       return true;
/*      */     }
/* 1197 */     if (!(obj instanceof MeterPlot)) {
/* 1198 */       return false;
/*      */     }
/* 1200 */     if (!super.equals(obj)) {
/* 1201 */       return false;
/*      */     }
/* 1203 */     MeterPlot that = (MeterPlot)obj;
/* 1204 */     if (!ObjectUtilities.equal(this.units, that.units)) {
/* 1205 */       return false;
/*      */     }
/* 1207 */     if (!ObjectUtilities.equal(this.range, that.range)) {
/* 1208 */       return false;
/*      */     }
/* 1210 */     if (!ObjectUtilities.equal(this.intervals, that.intervals)) {
/* 1211 */       return false;
/*      */     }
/* 1213 */     if (!PaintUtilities.equal(this.dialOutlinePaint, that.dialOutlinePaint))
/*      */     {
/* 1215 */       return false;
/*      */     }
/* 1217 */     if (this.shape != that.shape) {
/* 1218 */       return false;
/*      */     }
/* 1220 */     if (!PaintUtilities.equal(this.dialBackgroundPaint, that.dialBackgroundPaint))
/*      */     {
/* 1222 */       return false;
/*      */     }
/* 1224 */     if (!PaintUtilities.equal(this.needlePaint, that.needlePaint)) {
/* 1225 */       return false;
/*      */     }
/* 1227 */     if (!ObjectUtilities.equal(this.valueFont, that.valueFont)) {
/* 1228 */       return false;
/*      */     }
/* 1230 */     if (!PaintUtilities.equal(this.valuePaint, that.valuePaint)) {
/* 1231 */       return false;
/*      */     }
/* 1233 */     if (!PaintUtilities.equal(this.tickPaint, that.tickPaint)) {
/* 1234 */       return false;
/*      */     }
/* 1236 */     if (this.tickSize != that.tickSize) {
/* 1237 */       return false;
/*      */     }
/* 1239 */     if (this.tickLabelsVisible != that.tickLabelsVisible) {
/* 1240 */       return false;
/*      */     }
/* 1242 */     if (!ObjectUtilities.equal(this.tickLabelFont, that.tickLabelFont)) {
/* 1243 */       return false;
/*      */     }
/* 1245 */     if (!PaintUtilities.equal(this.tickLabelPaint, that.tickLabelPaint)) {
/* 1246 */       return false;
/*      */     }
/* 1248 */     if (!ObjectUtilities.equal(this.tickLabelFormat, that.tickLabelFormat))
/*      */     {
/* 1250 */       return false;
/*      */     }
/* 1252 */     if (this.drawBorder != that.drawBorder) {
/* 1253 */       return false;
/*      */     }
/* 1255 */     if (this.meterAngle != that.meterAngle) {
/* 1256 */       return false;
/*      */     }
/* 1258 */     return true;
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
/* 1269 */     stream.defaultWriteObject();
/* 1270 */     SerialUtilities.writePaint(this.dialBackgroundPaint, stream);
/* 1271 */     SerialUtilities.writePaint(this.dialOutlinePaint, stream);
/* 1272 */     SerialUtilities.writePaint(this.needlePaint, stream);
/* 1273 */     SerialUtilities.writePaint(this.valuePaint, stream);
/* 1274 */     SerialUtilities.writePaint(this.tickPaint, stream);
/* 1275 */     SerialUtilities.writePaint(this.tickLabelPaint, stream);
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
/* 1288 */     stream.defaultReadObject();
/* 1289 */     this.dialBackgroundPaint = SerialUtilities.readPaint(stream);
/* 1290 */     this.dialOutlinePaint = SerialUtilities.readPaint(stream);
/* 1291 */     this.needlePaint = SerialUtilities.readPaint(stream);
/* 1292 */     this.valuePaint = SerialUtilities.readPaint(stream);
/* 1293 */     this.tickPaint = SerialUtilities.readPaint(stream);
/* 1294 */     this.tickLabelPaint = SerialUtilities.readPaint(stream);
/* 1295 */     if (this.dataset != null) {
/* 1296 */       this.dataset.addChangeListener(this);
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
/*      */   public Object clone() throws CloneNotSupportedException {
/* 1312 */     MeterPlot clone = (MeterPlot)super.clone();
/* 1313 */     clone.tickLabelFormat = (NumberFormat)this.tickLabelFormat.clone();
/*      */     
/* 1315 */     clone.intervals = new ArrayList(this.intervals);
/* 1316 */     if (clone.dataset != null) {
/* 1317 */       clone.dataset.addChangeListener(clone);
/*      */     }
/* 1319 */     return clone;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/MeterPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */