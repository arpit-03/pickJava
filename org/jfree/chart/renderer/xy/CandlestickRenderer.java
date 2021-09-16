/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.HighLowItemLabelGenerator;
/*     */ import org.jfree.chart.labels.XYToolTipGenerator;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.xy.IntervalXYDataset;
/*     */ import org.jfree.data.xy.OHLCDataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.util.PaintUtilities;
/*     */ import org.jfree.util.PublicCloneable;
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
/*     */ public class CandlestickRenderer
/*     */   extends AbstractXYItemRenderer
/*     */   implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 50390395841817121L;
/*     */   public static final int WIDTHMETHOD_AVERAGE = 0;
/*     */   public static final int WIDTHMETHOD_SMALLEST = 1;
/*     */   public static final int WIDTHMETHOD_INTERVALDATA = 2;
/* 151 */   private int autoWidthMethod = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   private double autoWidthFactor = 0.6428571428571429D;
/*     */ 
/*     */   
/* 161 */   private double autoWidthGap = 0.0D;
/*     */ 
/*     */   
/*     */   private double candleWidth;
/*     */ 
/*     */   
/* 167 */   private double maxCandleWidthInMilliseconds = 7.2E7D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double maxCandleWidth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint upPaint;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint downPaint;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean drawVolume;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint volumePaint;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient double maxVolume;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean useOutlinePaint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CandlestickRenderer() {
/* 211 */     this(-1.0D);
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
/*     */   public CandlestickRenderer(double candleWidth) {
/* 223 */     this(candleWidth, true, (XYToolTipGenerator)new HighLowItemLabelGenerator());
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
/*     */   public CandlestickRenderer(double candleWidth, boolean drawVolume, XYToolTipGenerator toolTipGenerator) {
/* 241 */     setBaseToolTipGenerator(toolTipGenerator);
/* 242 */     this.candleWidth = candleWidth;
/* 243 */     this.drawVolume = drawVolume;
/* 244 */     this.volumePaint = Color.gray;
/* 245 */     this.upPaint = Color.green;
/* 246 */     this.downPaint = Color.red;
/* 247 */     this.useOutlinePaint = false;
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
/*     */   public double getCandleWidth() {
/* 259 */     return this.candleWidth;
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
/*     */   public void setCandleWidth(double width) {
/* 276 */     if (width != this.candleWidth) {
/* 277 */       this.candleWidth = width;
/* 278 */       fireChangeEvent();
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
/*     */   public double getMaxCandleWidthInMilliseconds() {
/* 290 */     return this.maxCandleWidthInMilliseconds;
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
/*     */   public void setMaxCandleWidthInMilliseconds(double millis) {
/* 306 */     this.maxCandleWidthInMilliseconds = millis;
/* 307 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAutoWidthMethod() {
/* 318 */     return this.autoWidthMethod;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAutoWidthMethod(int autoWidthMethod) {
/* 348 */     if (this.autoWidthMethod != autoWidthMethod) {
/* 349 */       this.autoWidthMethod = autoWidthMethod;
/* 350 */       fireChangeEvent();
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
/*     */   public double getAutoWidthFactor() {
/* 364 */     return this.autoWidthFactor;
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
/*     */   public void setAutoWidthFactor(double autoWidthFactor) {
/* 380 */     if (this.autoWidthFactor != autoWidthFactor) {
/* 381 */       this.autoWidthFactor = autoWidthFactor;
/* 382 */       fireChangeEvent();
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
/*     */   public double getAutoWidthGap() {
/* 395 */     return this.autoWidthGap;
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
/*     */   public void setAutoWidthGap(double autoWidthGap) {
/* 412 */     if (this.autoWidthGap != autoWidthGap) {
/* 413 */       this.autoWidthGap = autoWidthGap;
/* 414 */       fireChangeEvent();
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
/*     */   public Paint getUpPaint() {
/* 427 */     return this.upPaint;
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
/*     */   public void setUpPaint(Paint paint) {
/* 440 */     this.upPaint = paint;
/* 441 */     fireChangeEvent();
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
/*     */   public Paint getDownPaint() {
/* 453 */     return this.downPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDownPaint(Paint paint) {
/* 464 */     this.downPaint = paint;
/* 465 */     fireChangeEvent();
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
/*     */   public boolean getDrawVolume() {
/* 479 */     return this.drawVolume;
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
/*     */   public void setDrawVolume(boolean flag) {
/* 492 */     if (this.drawVolume != flag) {
/* 493 */       this.drawVolume = flag;
/* 494 */       fireChangeEvent();
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
/*     */   public Paint getVolumePaint() {
/* 509 */     return this.volumePaint;
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
/*     */   public void setVolumePaint(Paint paint) {
/* 524 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 525 */     this.volumePaint = paint;
/* 526 */     fireChangeEvent();
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
/*     */   public boolean getUseOutlinePaint() {
/* 541 */     return this.useOutlinePaint;
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
/*     */   public void setUseOutlinePaint(boolean use) {
/* 556 */     if (this.useOutlinePaint != use) {
/* 557 */       this.useOutlinePaint = use;
/* 558 */       fireChangeEvent();
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
/*     */   public Range findRangeBounds(XYDataset dataset) {
/* 573 */     return findRangeBounds(dataset, true);
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
/*     */ 
/*     */   
/*     */   public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset dataset, PlotRenderingInfo info) {
/* 597 */     ValueAxis axis = plot.getDomainAxis();
/* 598 */     double x1 = axis.getLowerBound();
/* 599 */     double x2 = x1 + this.maxCandleWidthInMilliseconds;
/* 600 */     RectangleEdge edge = plot.getDomainAxisEdge();
/* 601 */     double xx1 = axis.valueToJava2D(x1, dataArea, edge);
/* 602 */     double xx2 = axis.valueToJava2D(x2, dataArea, edge);
/* 603 */     this.maxCandleWidth = Math.abs(xx2 - xx1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 608 */     if (this.drawVolume) {
/* 609 */       OHLCDataset highLowDataset = (OHLCDataset)dataset;
/* 610 */       this.maxVolume = 0.0D;
/* 611 */       for (int series = 0; series < highLowDataset.getSeriesCount(); 
/* 612 */         series++) {
/* 613 */         for (int item = 0; item < highLowDataset.getItemCount(series); 
/* 614 */           item++) {
/* 615 */           double volume = highLowDataset.getVolumeValue(series, item);
/* 616 */           if (volume > this.maxVolume) {
/* 617 */             this.maxVolume = volume;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 624 */     return new XYItemRendererState(info);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/*     */     boolean horiz;
/*     */     double volumeWidth, stickWidth;
/*     */     Rectangle2D body, hotspot;
/* 652 */     PlotOrientation orientation = plot.getOrientation();
/* 653 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 654 */       horiz = true;
/*     */     }
/* 656 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 657 */       horiz = false;
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 664 */     EntityCollection entities = null;
/* 665 */     if (info != null) {
/* 666 */       entities = info.getOwner().getEntityCollection();
/*     */     }
/*     */     
/* 669 */     OHLCDataset highLowData = (OHLCDataset)dataset;
/*     */     
/* 671 */     double x = highLowData.getXValue(series, item);
/* 672 */     double yHigh = highLowData.getHighValue(series, item);
/* 673 */     double yLow = highLowData.getLowValue(series, item);
/* 674 */     double yOpen = highLowData.getOpenValue(series, item);
/* 675 */     double yClose = highLowData.getCloseValue(series, item);
/*     */     
/* 677 */     RectangleEdge domainEdge = plot.getDomainAxisEdge();
/* 678 */     double xx = domainAxis.valueToJava2D(x, dataArea, domainEdge);
/*     */     
/* 680 */     RectangleEdge edge = plot.getRangeAxisEdge();
/* 681 */     double yyHigh = rangeAxis.valueToJava2D(yHigh, dataArea, edge);
/* 682 */     double yyLow = rangeAxis.valueToJava2D(yLow, dataArea, edge);
/* 683 */     double yyOpen = rangeAxis.valueToJava2D(yOpen, dataArea, edge);
/* 684 */     double yyClose = rangeAxis.valueToJava2D(yClose, dataArea, edge);
/*     */ 
/*     */ 
/*     */     
/* 688 */     if (this.candleWidth > 0.0D) {
/*     */ 
/*     */       
/* 691 */       volumeWidth = this.candleWidth;
/* 692 */       stickWidth = this.candleWidth;
/*     */     } else {
/*     */       int itemCount; double lastPos; int i; IntervalXYDataset intervalXYData;
/* 695 */       double startPos, endPos, xxWidth = 0.0D;
/*     */       
/* 697 */       switch (this.autoWidthMethod) {
/*     */         
/*     */         case 0:
/* 700 */           itemCount = highLowData.getItemCount(series);
/* 701 */           if (horiz) {
/* 702 */             xxWidth = dataArea.getHeight() / itemCount;
/*     */             break;
/*     */           } 
/* 705 */           xxWidth = dataArea.getWidth() / itemCount;
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 711 */           itemCount = highLowData.getItemCount(series);
/* 712 */           lastPos = -1.0D;
/* 713 */           xxWidth = dataArea.getWidth();
/* 714 */           for (i = 0; i < itemCount; i++) {
/* 715 */             double pos = domainAxis.valueToJava2D(highLowData
/* 716 */                 .getXValue(series, i), dataArea, domainEdge);
/*     */             
/* 718 */             if (lastPos != -1.0D) {
/* 719 */               xxWidth = Math.min(xxWidth, 
/* 720 */                   Math.abs(pos - lastPos));
/*     */             }
/* 722 */             lastPos = pos;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 2:
/* 727 */           intervalXYData = (IntervalXYDataset)dataset;
/*     */           
/* 729 */           startPos = domainAxis.valueToJava2D(intervalXYData
/* 730 */               .getStartXValue(series, item), dataArea, plot
/* 731 */               .getDomainAxisEdge());
/* 732 */           endPos = domainAxis.valueToJava2D(intervalXYData
/* 733 */               .getEndXValue(series, item), dataArea, plot
/* 734 */               .getDomainAxisEdge());
/* 735 */           xxWidth = Math.abs(endPos - startPos);
/*     */           break;
/*     */       } 
/*     */       
/* 739 */       xxWidth -= 2.0D * this.autoWidthGap;
/* 740 */       xxWidth *= this.autoWidthFactor;
/* 741 */       xxWidth = Math.min(xxWidth, this.maxCandleWidth);
/* 742 */       volumeWidth = Math.max(Math.min(1.0D, this.maxCandleWidth), xxWidth);
/* 743 */       stickWidth = Math.max(Math.min(3.0D, this.maxCandleWidth), xxWidth);
/*     */     } 
/*     */     
/* 746 */     Paint p = getItemPaint(series, item);
/* 747 */     Paint outlinePaint = null;
/* 748 */     if (this.useOutlinePaint) {
/* 749 */       outlinePaint = getItemOutlinePaint(series, item);
/*     */     }
/* 751 */     Stroke s = getItemStroke(series, item);
/*     */     
/* 753 */     g2.setStroke(s);
/*     */     
/* 755 */     if (this.drawVolume) {
/* 756 */       double min, max; int volume = (int)highLowData.getVolumeValue(series, item);
/* 757 */       double volumeHeight = volume / this.maxVolume;
/*     */ 
/*     */       
/* 760 */       if (horiz) {
/* 761 */         min = dataArea.getMinX();
/* 762 */         max = dataArea.getMaxX();
/*     */       } else {
/*     */         
/* 765 */         min = dataArea.getMinY();
/* 766 */         max = dataArea.getMaxY();
/*     */       } 
/*     */       
/* 769 */       double zzVolume = volumeHeight * (max - min);
/*     */       
/* 771 */       g2.setPaint(getVolumePaint());
/* 772 */       Composite originalComposite = g2.getComposite();
/* 773 */       g2.setComposite(AlphaComposite.getInstance(3, 0.3F));
/*     */ 
/*     */       
/* 776 */       if (horiz) {
/* 777 */         g2.fill(new Rectangle2D.Double(min, xx - volumeWidth / 2.0D, zzVolume, volumeWidth));
/*     */       }
/*     */       else {
/*     */         
/* 781 */         g2.fill(new Rectangle2D.Double(xx - volumeWidth / 2.0D, max - zzVolume, volumeWidth, zzVolume));
/*     */       } 
/*     */ 
/*     */       
/* 785 */       g2.setComposite(originalComposite);
/*     */     } 
/*     */     
/* 788 */     if (this.useOutlinePaint) {
/* 789 */       g2.setPaint(outlinePaint);
/*     */     } else {
/*     */       
/* 792 */       g2.setPaint(p);
/*     */     } 
/*     */     
/* 795 */     double yyMaxOpenClose = Math.max(yyOpen, yyClose);
/* 796 */     double yyMinOpenClose = Math.min(yyOpen, yyClose);
/* 797 */     double maxOpenClose = Math.max(yOpen, yClose);
/* 798 */     double minOpenClose = Math.min(yOpen, yClose);
/*     */ 
/*     */     
/* 801 */     if (yHigh > maxOpenClose) {
/* 802 */       if (horiz) {
/* 803 */         g2.draw(new Line2D.Double(yyHigh, xx, yyMaxOpenClose, xx));
/*     */       } else {
/*     */         
/* 806 */         g2.draw(new Line2D.Double(xx, yyHigh, xx, yyMaxOpenClose));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 811 */     if (yLow < minOpenClose) {
/* 812 */       if (horiz) {
/* 813 */         g2.draw(new Line2D.Double(yyLow, xx, yyMinOpenClose, xx));
/*     */       } else {
/*     */         
/* 816 */         g2.draw(new Line2D.Double(xx, yyLow, xx, yyMinOpenClose));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 823 */     double length = Math.abs(yyHigh - yyLow);
/* 824 */     double base = Math.min(yyHigh, yyLow);
/* 825 */     if (horiz) {
/* 826 */       body = new Rectangle2D.Double(yyMinOpenClose, xx - stickWidth / 2.0D, yyMaxOpenClose - yyMinOpenClose, stickWidth);
/*     */       
/* 828 */       hotspot = new Rectangle2D.Double(base, xx - stickWidth / 2.0D, length, stickWidth);
/*     */     }
/*     */     else {
/*     */       
/* 832 */       body = new Rectangle2D.Double(xx - stickWidth / 2.0D, yyMinOpenClose, stickWidth, yyMaxOpenClose - yyMinOpenClose);
/*     */       
/* 834 */       hotspot = new Rectangle2D.Double(xx - stickWidth / 2.0D, base, stickWidth, length);
/*     */     } 
/*     */     
/* 837 */     if (yClose > yOpen) {
/* 838 */       if (this.upPaint != null) {
/* 839 */         g2.setPaint(this.upPaint);
/*     */       } else {
/*     */         
/* 842 */         g2.setPaint(p);
/*     */       } 
/* 844 */       g2.fill(body);
/*     */     } else {
/*     */       
/* 847 */       if (this.downPaint != null) {
/* 848 */         g2.setPaint(this.downPaint);
/*     */       } else {
/*     */         
/* 851 */         g2.setPaint(p);
/*     */       } 
/* 853 */       g2.fill(body);
/*     */     } 
/* 855 */     if (this.useOutlinePaint) {
/* 856 */       g2.setPaint(outlinePaint);
/*     */     } else {
/*     */       
/* 859 */       g2.setPaint(p);
/*     */     } 
/* 861 */     g2.draw(body);
/*     */ 
/*     */     
/* 864 */     if (entities != null) {
/* 865 */       addEntity(entities, hotspot, dataset, series, item, 0.0D, 0.0D);
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
/*     */   public boolean equals(Object obj) {
/* 879 */     if (obj == this) {
/* 880 */       return true;
/*     */     }
/* 882 */     if (!(obj instanceof CandlestickRenderer)) {
/* 883 */       return false;
/*     */     }
/* 885 */     CandlestickRenderer that = (CandlestickRenderer)obj;
/* 886 */     if (this.candleWidth != that.candleWidth) {
/* 887 */       return false;
/*     */     }
/* 889 */     if (!PaintUtilities.equal(this.upPaint, that.upPaint)) {
/* 890 */       return false;
/*     */     }
/* 892 */     if (!PaintUtilities.equal(this.downPaint, that.downPaint)) {
/* 893 */       return false;
/*     */     }
/* 895 */     if (this.drawVolume != that.drawVolume) {
/* 896 */       return false;
/*     */     }
/* 898 */     if (this.maxCandleWidthInMilliseconds != that.maxCandleWidthInMilliseconds)
/*     */     {
/* 900 */       return false;
/*     */     }
/* 902 */     if (this.autoWidthMethod != that.autoWidthMethod) {
/* 903 */       return false;
/*     */     }
/* 905 */     if (this.autoWidthFactor != that.autoWidthFactor) {
/* 906 */       return false;
/*     */     }
/* 908 */     if (this.autoWidthGap != that.autoWidthGap) {
/* 909 */       return false;
/*     */     }
/* 911 */     if (this.useOutlinePaint != that.useOutlinePaint) {
/* 912 */       return false;
/*     */     }
/* 914 */     if (!PaintUtilities.equal(this.volumePaint, that.volumePaint)) {
/* 915 */       return false;
/*     */     }
/* 917 */     return super.equals(obj);
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 929 */     return super.clone();
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
/* 940 */     stream.defaultWriteObject();
/* 941 */     SerialUtilities.writePaint(this.upPaint, stream);
/* 942 */     SerialUtilities.writePaint(this.downPaint, stream);
/* 943 */     SerialUtilities.writePaint(this.volumePaint, stream);
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
/* 956 */     stream.defaultReadObject();
/* 957 */     this.upPaint = SerialUtilities.readPaint(stream);
/* 958 */     this.downPaint = SerialUtilities.readPaint(stream);
/* 959 */     this.volumePaint = SerialUtilities.readPaint(stream);
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
/*     */   public boolean drawVolume() {
/* 974 */     return this.drawVolume;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/CandlestickRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */