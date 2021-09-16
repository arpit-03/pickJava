/*     */ package org.jfree.data.general;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.image.BufferedImage;
/*     */ import org.jfree.chart.renderer.PaintScale;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.data.xy.XYSeries;
/*     */ import org.jfree.data.xy.XYSeriesCollection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HeatMapUtilities
/*     */ {
/*     */   public static XYDataset extractRowFromHeatMapDataset(HeatMapDataset dataset, int row, Comparable seriesName) {
/*  72 */     XYSeries series = new XYSeries(seriesName);
/*  73 */     int cols = dataset.getXSampleCount();
/*  74 */     for (int c = 0; c < cols; c++) {
/*  75 */       series.add(dataset.getXValue(c), dataset.getZValue(c, row));
/*     */     }
/*  77 */     XYSeriesCollection result = new XYSeriesCollection(series);
/*  78 */     return (XYDataset)result;
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
/*     */   public static XYDataset extractColumnFromHeatMapDataset(HeatMapDataset dataset, int column, Comparable seriesName) {
/*  93 */     XYSeries series = new XYSeries(seriesName);
/*  94 */     int rows = dataset.getYSampleCount();
/*  95 */     for (int r = 0; r < rows; r++) {
/*  96 */       series.add(dataset.getYValue(r), dataset.getZValue(column, r));
/*     */     }
/*  98 */     XYSeriesCollection result = new XYSeriesCollection(series);
/*  99 */     return (XYDataset)result;
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
/*     */   public static BufferedImage createHeatMapImage(HeatMapDataset dataset, PaintScale paintScale) {
/* 114 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 115 */     ParamChecks.nullNotPermitted(paintScale, "paintScale");
/* 116 */     int xCount = dataset.getXSampleCount();
/* 117 */     int yCount = dataset.getYSampleCount();
/* 118 */     BufferedImage image = new BufferedImage(xCount, yCount, 2);
/*     */     
/* 120 */     Graphics2D g2 = image.createGraphics();
/* 121 */     for (int xIndex = 0; xIndex < xCount; xIndex++) {
/* 122 */       for (int yIndex = 0; yIndex < yCount; yIndex++) {
/* 123 */         double z = dataset.getZValue(xIndex, yIndex);
/* 124 */         Paint p = paintScale.getPaint(z);
/* 125 */         g2.setPaint(p);
/* 126 */         g2.fillRect(xIndex, yCount - yIndex - 1, 1, 1);
/*     */       } 
/*     */     } 
/* 129 */     return image;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/general/HeatMapUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */