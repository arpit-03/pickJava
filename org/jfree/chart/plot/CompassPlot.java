/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Area;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.ResourceBundle;
/*     */ import org.jfree.chart.LegendItemCollection;
/*     */ import org.jfree.chart.needle.ArrowNeedle;
/*     */ import org.jfree.chart.needle.LineNeedle;
/*     */ import org.jfree.chart.needle.LongNeedle;
/*     */ import org.jfree.chart.needle.MeterNeedle;
/*     */ import org.jfree.chart.needle.MiddlePinNeedle;
/*     */ import org.jfree.chart.needle.PinNeedle;
/*     */ import org.jfree.chart.needle.PlumNeedle;
/*     */ import org.jfree.chart.needle.PointerNeedle;
/*     */ import org.jfree.chart.needle.ShipNeedle;
/*     */ import org.jfree.chart.needle.WindNeedle;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.chart.util.ResourceBundleWrapper;
/*     */ import org.jfree.data.general.DefaultValueDataset;
/*     */ import org.jfree.data.general.ValueDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PaintUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompassPlot
/*     */   extends Plot
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6924382802125527395L;
/* 119 */   public static final Font DEFAULT_LABEL_FONT = new Font("SansSerif", 1, 10);
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int NO_LABELS = 0;
/*     */ 
/*     */   
/*     */   public static final int VALUE_LABELS = 1;
/*     */ 
/*     */   
/*     */   private int labelType;
/*     */ 
/*     */   
/*     */   private Font labelFont;
/*     */ 
/*     */   
/*     */   private boolean drawBorder = false;
/*     */ 
/*     */   
/* 138 */   private transient Paint roseHighlightPaint = Color.black;
/*     */ 
/*     */   
/* 141 */   private transient Paint rosePaint = Color.yellow;
/*     */ 
/*     */   
/* 144 */   private transient Paint roseCenterPaint = Color.white;
/*     */ 
/*     */   
/* 147 */   private Font compassFont = new Font("Arial", 0, 10);
/*     */ 
/*     */   
/*     */   private transient Ellipse2D circle1;
/*     */ 
/*     */   
/*     */   private transient Ellipse2D circle2;
/*     */ 
/*     */   
/*     */   private transient Area a1;
/*     */ 
/*     */   
/*     */   private transient Area a2;
/*     */ 
/*     */   
/*     */   private transient Rectangle2D rect1;
/*     */ 
/*     */   
/* 165 */   private ValueDataset[] datasets = new ValueDataset[1];
/*     */ 
/*     */   
/* 168 */   private MeterNeedle[] seriesNeedle = new MeterNeedle[1];
/*     */ 
/*     */ 
/*     */   
/* 172 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 179 */   protected double revolutionDistance = 360.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompassPlot() {
/* 185 */     this((ValueDataset)new DefaultValueDataset());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompassPlot(ValueDataset dataset) {
/* 195 */     if (dataset != null) {
/* 196 */       this.datasets[0] = dataset;
/* 197 */       dataset.addChangeListener(this);
/*     */     } 
/* 199 */     this.circle1 = new Ellipse2D.Double();
/* 200 */     this.circle2 = new Ellipse2D.Double();
/* 201 */     this.rect1 = new Rectangle2D.Double();
/* 202 */     setSeriesNeedle(0);
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
/*     */   public int getLabelType() {
/* 215 */     return this.labelType;
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
/*     */   public void setLabelType(int type) {
/* 227 */     if (type != 0 && type != 1) {
/* 228 */       throw new IllegalArgumentException("MeterPlot.setLabelType(int): unrecognised type.");
/*     */     }
/*     */     
/* 231 */     if (this.labelType != type) {
/* 232 */       this.labelType = type;
/* 233 */       fireChangeEvent();
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
/*     */   public Font getLabelFont() {
/* 246 */     return this.labelFont;
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
/*     */   public void setLabelFont(Font font) {
/* 259 */     ParamChecks.nullNotPermitted(font, "font");
/* 260 */     this.labelFont = font;
/* 261 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getRosePaint() {
/* 272 */     return this.rosePaint;
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
/*     */   public void setRosePaint(Paint paint) {
/* 284 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 285 */     this.rosePaint = paint;
/* 286 */     fireChangeEvent();
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
/*     */   public Paint getRoseCenterPaint() {
/* 298 */     return this.roseCenterPaint;
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
/*     */   public void setRoseCenterPaint(Paint paint) {
/* 310 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 311 */     this.roseCenterPaint = paint;
/* 312 */     fireChangeEvent();
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
/*     */   public Paint getRoseHighlightPaint() {
/* 324 */     return this.roseHighlightPaint;
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
/*     */   public void setRoseHighlightPaint(Paint paint) {
/* 336 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 337 */     this.roseHighlightPaint = paint;
/* 338 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getDrawBorder() {
/* 349 */     return this.drawBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDrawBorder(boolean status) {
/* 360 */     this.drawBorder = status;
/* 361 */     fireChangeEvent();
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
/*     */   public void setSeriesPaint(int series, Paint paint) {
/* 374 */     if (series >= 0 && series < this.seriesNeedle.length) {
/* 375 */       this.seriesNeedle[series].setFillPaint(paint);
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
/*     */   public void setSeriesOutlinePaint(int series, Paint p) {
/* 389 */     if (series >= 0 && series < this.seriesNeedle.length) {
/* 390 */       this.seriesNeedle[series].setOutlinePaint(p);
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
/*     */   public void setSeriesOutlineStroke(int series, Stroke stroke) {
/* 405 */     if (series >= 0 && series < this.seriesNeedle.length) {
/* 406 */       this.seriesNeedle[series].setOutlineStroke(stroke);
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
/*     */   public void setSeriesNeedle(int type) {
/* 419 */     setSeriesNeedle(0, type);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeriesNeedle(int index, int type) {
/*     */     LongNeedle longNeedle;
/* 442 */     switch (type) {
/*     */       case 0:
/* 444 */         setSeriesNeedle(index, (MeterNeedle)new ArrowNeedle(true));
/* 445 */         setSeriesPaint(index, Color.red);
/* 446 */         this.seriesNeedle[index].setHighlightPaint(Color.white);
/*     */         return;
/*     */       case 1:
/* 449 */         setSeriesNeedle(index, (MeterNeedle)new LineNeedle());
/*     */         return;
/*     */       case 2:
/* 452 */         longNeedle = new LongNeedle();
/* 453 */         longNeedle.setRotateY(0.5D);
/* 454 */         setSeriesNeedle(index, (MeterNeedle)longNeedle);
/*     */         return;
/*     */       case 3:
/* 457 */         setSeriesNeedle(index, (MeterNeedle)new PinNeedle());
/*     */         return;
/*     */       case 4:
/* 460 */         setSeriesNeedle(index, (MeterNeedle)new PlumNeedle());
/*     */         return;
/*     */       case 5:
/* 463 */         setSeriesNeedle(index, (MeterNeedle)new PointerNeedle());
/*     */         return;
/*     */       case 6:
/* 466 */         setSeriesPaint(index, (Paint)null);
/* 467 */         setSeriesOutlineStroke(index, new BasicStroke(3.0F));
/* 468 */         setSeriesNeedle(index, (MeterNeedle)new ShipNeedle());
/*     */         return;
/*     */       case 7:
/* 471 */         setSeriesPaint(index, Color.blue);
/* 472 */         setSeriesNeedle(index, (MeterNeedle)new WindNeedle());
/*     */         return;
/*     */       case 8:
/* 475 */         setSeriesNeedle(index, (MeterNeedle)new ArrowNeedle(true));
/*     */         return;
/*     */       case 9:
/* 478 */         setSeriesNeedle(index, (MeterNeedle)new MiddlePinNeedle());
/*     */         return;
/*     */     } 
/*     */     
/* 482 */     throw new IllegalArgumentException("Unrecognised type.");
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
/*     */   public void setSeriesNeedle(int index, MeterNeedle needle) {
/* 495 */     if (needle != null && index < this.seriesNeedle.length) {
/* 496 */       this.seriesNeedle[index] = needle;
/*     */     }
/* 498 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueDataset[] getDatasets() {
/* 509 */     return this.datasets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDataset(ValueDataset dataset) {
/* 520 */     addDataset(dataset, (MeterNeedle)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDataset(ValueDataset dataset, MeterNeedle needle) {
/* 531 */     if (dataset != null) {
/* 532 */       int i = this.datasets.length + 1;
/* 533 */       ValueDataset[] t = new ValueDataset[i];
/* 534 */       MeterNeedle[] p = new MeterNeedle[i];
/* 535 */       i -= 2;
/* 536 */       for (; i >= 0; i--) {
/* 537 */         t[i] = this.datasets[i];
/* 538 */         p[i] = this.seriesNeedle[i];
/*     */       } 
/* 540 */       i = this.datasets.length;
/* 541 */       t[i] = dataset;
/* 542 */       p[i] = (needle != null) ? needle : p[i - 1];
/*     */       
/* 544 */       ValueDataset[] a = this.datasets;
/* 545 */       MeterNeedle[] b = this.seriesNeedle;
/* 546 */       this.datasets = t;
/* 547 */       this.seriesNeedle = p;
/*     */       
/* 549 */       for (; --i >= 0; i--) {
/* 550 */         a[i] = null;
/* 551 */         b[i] = null;
/*     */       } 
/* 553 */       dataset.addChangeListener(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info) {
/* 575 */     if (info != null) {
/* 576 */       info.setPlotArea(area);
/*     */     }
/*     */ 
/*     */     
/* 580 */     RectangleInsets insets = getInsets();
/* 581 */     insets.trim(area);
/*     */ 
/*     */     
/* 584 */     if (this.drawBorder) {
/* 585 */       drawBackground(g2, area);
/*     */     }
/*     */     
/* 588 */     int midX = (int)(area.getWidth() / 2.0D);
/* 589 */     int midY = (int)(area.getHeight() / 2.0D);
/* 590 */     int radius = midX;
/* 591 */     if (midY < midX) {
/* 592 */       radius = midY;
/*     */     }
/* 594 */     radius--;
/* 595 */     int diameter = 2 * radius;
/*     */     
/* 597 */     midX += (int)area.getMinX();
/* 598 */     midY += (int)area.getMinY();
/*     */     
/* 600 */     this.circle1.setFrame((midX - radius), (midY - radius), diameter, diameter);
/* 601 */     this.circle2.setFrame((midX - radius + 15), (midY - radius + 15), (diameter - 30), (diameter - 30));
/*     */ 
/*     */ 
/*     */     
/* 605 */     g2.setPaint(this.rosePaint);
/* 606 */     this.a1 = new Area(this.circle1);
/* 607 */     this.a2 = new Area(this.circle2);
/* 608 */     this.a1.subtract(this.a2);
/* 609 */     g2.fill(this.a1);
/*     */     
/* 611 */     g2.setPaint(this.roseCenterPaint);
/* 612 */     int x1 = diameter - 30;
/* 613 */     g2.fillOval(midX - radius + 15, midY - radius + 15, x1, x1);
/* 614 */     g2.setPaint(this.roseHighlightPaint);
/* 615 */     g2.drawOval(midX - radius, midY - radius, diameter, diameter);
/* 616 */     x1 = diameter - 20;
/* 617 */     g2.drawOval(midX - radius + 10, midY - radius + 10, x1, x1);
/* 618 */     x1 = diameter - 30;
/* 619 */     g2.drawOval(midX - radius + 15, midY - radius + 15, x1, x1);
/* 620 */     x1 = diameter - 80;
/* 621 */     g2.drawOval(midX - radius + 40, midY - radius + 40, x1, x1);
/*     */     
/* 623 */     int outerRadius = radius - 20;
/* 624 */     int innerRadius = radius - 32; int w;
/* 625 */     for (w = 0; w < 360; w += 15) {
/* 626 */       double a = Math.toRadians(w);
/* 627 */       x1 = midX - (int)(Math.sin(a) * innerRadius);
/* 628 */       int x2 = midX - (int)(Math.sin(a) * outerRadius);
/* 629 */       int j = midY - (int)(Math.cos(a) * innerRadius);
/* 630 */       int y2 = midY - (int)(Math.cos(a) * outerRadius);
/* 631 */       g2.drawLine(x1, j, x2, y2);
/*     */     } 
/*     */     
/* 634 */     g2.setPaint(this.roseHighlightPaint);
/* 635 */     innerRadius = radius - 26;
/* 636 */     outerRadius = 7;
/* 637 */     for (w = 45; w < 360; w += 90) {
/* 638 */       double a = Math.toRadians(w);
/* 639 */       x1 = midX - (int)(Math.sin(a) * innerRadius);
/* 640 */       int j = midY - (int)(Math.cos(a) * innerRadius);
/* 641 */       g2.fillOval(x1 - outerRadius, j - outerRadius, 2 * outerRadius, 2 * outerRadius);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 646 */     for (w = 0; w < 360; w += 90) {
/* 647 */       double a = Math.toRadians(w);
/* 648 */       x1 = midX - (int)(Math.sin(a) * innerRadius);
/* 649 */       int j = midY - (int)(Math.cos(a) * innerRadius);
/*     */       
/* 651 */       Polygon p = new Polygon();
/* 652 */       p.addPoint(x1 - outerRadius, j);
/* 653 */       p.addPoint(x1, j + outerRadius);
/* 654 */       p.addPoint(x1 + outerRadius, j);
/* 655 */       p.addPoint(x1, j - outerRadius);
/* 656 */       g2.fillPolygon(p);
/*     */     } 
/*     */ 
/*     */     
/* 660 */     innerRadius = radius - 42;
/* 661 */     Font f = getCompassFont(radius);
/* 662 */     g2.setFont(f);
/* 663 */     g2.drawString(localizationResources.getString("N"), midX - 5, midY - innerRadius + f.getSize());
/* 664 */     g2.drawString(localizationResources.getString("S"), midX - 5, midY + innerRadius - 5);
/* 665 */     g2.drawString(localizationResources.getString("W"), midX - innerRadius + 5, midY + 5);
/* 666 */     g2.drawString(localizationResources.getString("E"), midX + innerRadius - f.getSize(), midY + 5);
/*     */ 
/*     */     
/* 669 */     int y1 = radius / 2;
/* 670 */     x1 = radius / 6;
/* 671 */     Rectangle2D needleArea = new Rectangle2D.Double((midX - x1), (midY - y1), (2 * x1), (2 * y1));
/*     */ 
/*     */     
/* 674 */     int x = this.seriesNeedle.length;
/*     */ 
/*     */     
/* 677 */     int i = this.datasets.length - 1;
/* 678 */     for (; i >= 0; i--) {
/* 679 */       ValueDataset data = this.datasets[i];
/*     */       
/* 681 */       if (data != null && data.getValue() != null) {
/* 682 */         double value = data.getValue().doubleValue() % this.revolutionDistance;
/*     */         
/* 684 */         value = value / this.revolutionDistance * 360.0D;
/* 685 */         int current = i % x;
/* 686 */         this.seriesNeedle[current].draw(g2, needleArea, value);
/*     */       } 
/*     */     } 
/*     */     
/* 690 */     if (this.drawBorder) {
/* 691 */       drawOutline(g2, area);
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
/*     */   public String getPlotType() {
/* 703 */     return localizationResources.getString("Compass_Plot");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegendItemCollection getLegendItems() {
/* 714 */     return null;
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
/*     */   public void zoom(double percent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Font getCompassFont(int radius) {
/* 735 */     float fontSize = radius / 10.0F;
/* 736 */     if (fontSize < 8.0F) {
/* 737 */       fontSize = 8.0F;
/*     */     }
/* 739 */     Font newFont = this.compassFont.deriveFont(fontSize);
/* 740 */     return newFont;
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
/* 752 */     if (obj == this) {
/* 753 */       return true;
/*     */     }
/* 755 */     if (!(obj instanceof CompassPlot)) {
/* 756 */       return false;
/*     */     }
/* 758 */     if (!super.equals(obj)) {
/* 759 */       return false;
/*     */     }
/* 761 */     CompassPlot that = (CompassPlot)obj;
/* 762 */     if (this.labelType != that.labelType) {
/* 763 */       return false;
/*     */     }
/* 765 */     if (!ObjectUtilities.equal(this.labelFont, that.labelFont)) {
/* 766 */       return false;
/*     */     }
/* 768 */     if (this.drawBorder != that.drawBorder) {
/* 769 */       return false;
/*     */     }
/* 771 */     if (!PaintUtilities.equal(this.roseHighlightPaint, that.roseHighlightPaint))
/*     */     {
/* 773 */       return false;
/*     */     }
/* 775 */     if (!PaintUtilities.equal(this.rosePaint, that.rosePaint)) {
/* 776 */       return false;
/*     */     }
/* 778 */     if (!PaintUtilities.equal(this.roseCenterPaint, that.roseCenterPaint))
/*     */     {
/* 780 */       return false;
/*     */     }
/* 782 */     if (!ObjectUtilities.equal(this.compassFont, that.compassFont)) {
/* 783 */       return false;
/*     */     }
/* 785 */     if (!Arrays.equals((Object[])this.seriesNeedle, (Object[])that.seriesNeedle)) {
/* 786 */       return false;
/*     */     }
/* 788 */     if (getRevolutionDistance() != that.getRevolutionDistance()) {
/* 789 */       return false;
/*     */     }
/* 791 */     return true;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 806 */     CompassPlot clone = (CompassPlot)super.clone();
/* 807 */     if (this.circle1 != null) {
/* 808 */       clone.circle1 = (Ellipse2D)this.circle1.clone();
/*     */     }
/* 810 */     if (this.circle2 != null) {
/* 811 */       clone.circle2 = (Ellipse2D)this.circle2.clone();
/*     */     }
/* 813 */     if (this.a1 != null) {
/* 814 */       clone.a1 = (Area)this.a1.clone();
/*     */     }
/* 816 */     if (this.a2 != null) {
/* 817 */       clone.a2 = (Area)this.a2.clone();
/*     */     }
/* 819 */     if (this.rect1 != null) {
/* 820 */       clone.rect1 = (Rectangle2D)this.rect1.clone();
/*     */     }
/* 822 */     clone.datasets = (ValueDataset[])this.datasets.clone();
/* 823 */     clone.seriesNeedle = (MeterNeedle[])this.seriesNeedle.clone();
/*     */ 
/*     */     
/* 826 */     for (int i = 0; i < this.datasets.length; i++) {
/* 827 */       if (clone.datasets[i] != null) {
/* 828 */         clone.datasets[i].addChangeListener(clone);
/*     */       }
/*     */     } 
/* 831 */     return clone;
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
/*     */   public void setRevolutionDistance(double size) {
/* 844 */     if (size > 0.0D) {
/* 845 */       this.revolutionDistance = size;
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
/*     */   public double getRevolutionDistance() {
/* 857 */     return this.revolutionDistance;
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
/* 868 */     stream.defaultWriteObject();
/* 869 */     SerialUtilities.writePaint(this.rosePaint, stream);
/* 870 */     SerialUtilities.writePaint(this.roseCenterPaint, stream);
/* 871 */     SerialUtilities.writePaint(this.roseHighlightPaint, stream);
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
/* 884 */     stream.defaultReadObject();
/* 885 */     this.rosePaint = SerialUtilities.readPaint(stream);
/* 886 */     this.roseCenterPaint = SerialUtilities.readPaint(stream);
/* 887 */     this.roseHighlightPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/CompassPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */