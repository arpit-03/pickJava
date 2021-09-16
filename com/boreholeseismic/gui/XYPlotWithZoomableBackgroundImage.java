/*     */ package com.boreholeseismic.gui;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ImageObserver;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.ui.Align;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XYPlotWithZoomableBackgroundImage
/*     */   extends XYPlot
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private boolean zoomableBackgroundImage;
/*     */   
/*     */   public XYPlotWithZoomableBackgroundImage(XYDataset dataset, ValueAxis domainAxis, ValueAxis rangeAxis, XYItemRenderer renderer, boolean zoomableBackgroundImage) {
/*  30 */     super(dataset, domainAxis, rangeAxis, renderer);
/*  31 */     this.zoomableBackgroundImage = zoomableBackgroundImage;
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
/*     */   public void drawBackgroundImage(Graphics2D g2, Rectangle2D area) {
/*  47 */     if (!this.zoomableBackgroundImage) {
/*     */       
/*  49 */       super.drawBackgroundImage(g2, area);
/*     */     }
/*     */     else {
/*     */       
/*  53 */       Image backgroundImage = getBackgroundImage();
/*     */ 
/*     */       
/*  56 */       if (backgroundImage != null) {
/*     */ 
/*     */         
/*  59 */         int backgroundImageAlignment = getBackgroundImageAlignment();
/*     */ 
/*     */         
/*  62 */         float backgroundImageAlpha = getBackgroundImageAlpha();
/*     */ 
/*     */         
/*  65 */         ValueAxis xAxis = getDomainAxis();
/*     */ 
/*     */         
/*  68 */         ValueAxis yAxis = getRangeAxis();
/*     */ 
/*     */         
/*  71 */         Range xRange = getDataRange(xAxis);
/*     */ 
/*     */         
/*  74 */         Range yRange = getDataRange(yAxis);
/*     */ 
/*     */         
/*  77 */         double xDataUpperBound = xRange.getUpperBound();
/*  78 */         double xDataLowerBound = xRange.getLowerBound();
/*  79 */         double xRangeValue = xDataUpperBound - xDataLowerBound;
/*     */ 
/*     */         
/*  82 */         double yDataUpperBound = yRange.getUpperBound();
/*  83 */         double yDataLowerBound = yRange.getLowerBound();
/*  84 */         double yRangeValue = yDataUpperBound - yDataLowerBound;
/*     */ 
/*     */ 
/*     */         
/*  88 */         double xmin = xAxis.getLowerBound();
/*     */ 
/*     */         
/*  91 */         double xmax = xAxis.getUpperBound();
/*     */ 
/*     */         
/*  94 */         double ymin = yAxis.getLowerBound();
/*     */ 
/*     */         
/*  97 */         double ymax = yAxis.getUpperBound();
/*     */         
/*  99 */         if (yRangeValue < Math.abs(ymax - ymin)) {
/* 100 */           yRangeValue = Math.abs(ymax - ymin);
/*     */         }
/*     */         
/* 103 */         if (xRangeValue < Math.abs(xmax - xmin)) {
/* 104 */           xRangeValue = Math.abs(xmax - xmin);
/*     */         }
/*     */ 
/*     */         
/* 108 */         int originalImageWidth = backgroundImage.getWidth(null);
/*     */ 
/*     */         
/* 111 */         int originalImageHeight = backgroundImage.getHeight(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 118 */         double xmin2 = xmin - xDataLowerBound;
/* 119 */         double xmax2 = xmax - xDataLowerBound;
/* 120 */         double ymin2 = ymin - yDataLowerBound;
/* 121 */         double ymax2 = ymax - yDataLowerBound;
/*     */ 
/*     */         
/* 124 */         double xmin3 = xmin2;
/* 125 */         double xmax3 = xmax2;
/* 126 */         double ymin3 = yRangeValue - ymax2;
/* 127 */         double ymax3 = yRangeValue - ymin2;
/*     */         
/* 129 */         System.out.println("ymax " + ymax);
/* 130 */         System.out.println("ymin " + ymin);
/* 131 */         System.out.println("ymax2 " + ymax2);
/* 132 */         System.out.println("ymin2 " + ymin2);
/*     */ 
/*     */ 
/*     */         
/* 136 */         System.out.println("xmin3 " + xmin3);
/* 137 */         System.out.println("xmax3 " + xmax3);
/* 138 */         System.out.println("ymin3 " + ymin3);
/* 139 */         System.out.println("ymax3 " + ymax3);
/*     */ 
/*     */ 
/*     */         
/* 143 */         BufferedImage bi = new BufferedImage(originalImageWidth, originalImageHeight, 
/* 144 */             2);
/* 145 */         Graphics2D g = bi.createGraphics();
/* 146 */         g.drawImage(backgroundImage, 0, 0, (ImageObserver)null);
/* 147 */         g.dispose();
/*     */ 
/*     */         
/* 150 */         double newXMin = xmin3 / xRangeValue * originalImageWidth;
/* 151 */         double newXMax = xmax3 / xRangeValue * originalImageWidth;
/* 152 */         double newYMin = ymin3 / yRangeValue * originalImageHeight;
/* 153 */         double newYMax = ymax3 / yRangeValue * originalImageHeight;
/*     */ 
/*     */         
/* 156 */         if (newXMin < 0.0D) {
/* 157 */           newXMin = 0.0D;
/*     */         }
/* 159 */         if (newYMin < 0.0D) {
/* 160 */           newYMin = 0.0D;
/*     */         }
/*     */         
/* 163 */         System.out.println("newXMin " + newXMin);
/* 164 */         System.out.println("newYMin " + newYMin);
/* 165 */         System.out.println("(newXMax - newXMin) " + (newXMax - newXMin));
/* 166 */         System.out.println("(newYMax - newYMin) " + (newYMax - newYMin));
/*     */         
/* 168 */         double subImageWidth = newXMax - newXMin;
/* 169 */         double subImageHeight = newYMax - newYMin;
/*     */ 
/*     */         
/* 172 */         if (newYMin + subImageHeight > bi.getHeight()) {
/* 173 */           subImageHeight = bi.getHeight() - newYMin;
/*     */         }
/*     */         
/* 176 */         if (newXMin + subImageWidth > bi.getWidth()) {
/* 177 */           subImageWidth = bi.getWidth() - newXMin;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 182 */         BufferedImage bi2 = bi.getSubimage((int)newXMin, (int)newYMin, (int)subImageWidth, (int)subImageHeight);
/*     */         
/* 184 */         Composite originalComposite = g2.getComposite();
/* 185 */         g2.setComposite(AlphaComposite.getInstance(3, 
/* 186 */               backgroundImageAlpha));
/* 187 */         Rectangle2D dest = new Rectangle2D.Double(0.0D, 0.0D, 
/* 188 */             bi2.getWidth(null), 
/* 189 */             bi2.getHeight(null));
/* 190 */         Align.align(dest, area, backgroundImageAlignment);
/* 191 */         g2.drawImage(bi2, (int)dest.getX(), 
/* 192 */             (int)dest.getY(), (int)dest.getWidth() + 1, 
/* 193 */             (int)dest.getHeight() + 1, null);
/*     */ 
/*     */         
/* 196 */         g2.setComposite(originalComposite);
/*     */       } 
/*     */     } 
/* 199 */     System.out.println("using me to draw background");
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/gui/XYPlotWithZoomableBackgroundImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */