/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.data.xy.WindDataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.ui.RectangleEdge;
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
/*     */ public class WindItemRenderer
/*     */   extends AbstractXYItemRenderer
/*     */   implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8078914101916976844L;
/*     */   
/*     */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D plotArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/* 123 */     WindDataset windData = (WindDataset)dataset;
/*     */     
/* 125 */     Paint seriesPaint = getItemPaint(series, item);
/* 126 */     Stroke seriesStroke = getItemStroke(series, item);
/* 127 */     g2.setPaint(seriesPaint);
/* 128 */     g2.setStroke(seriesStroke);
/*     */ 
/*     */ 
/*     */     
/* 132 */     Number x = windData.getX(series, item);
/* 133 */     Number windDir = windData.getWindDirection(series, item);
/* 134 */     Number wforce = windData.getWindForce(series, item);
/* 135 */     double windForce = wforce.doubleValue();
/*     */     
/* 137 */     double wdirt = Math.toRadians(windDir.doubleValue() * -30.0D - 90.0D);
/*     */ 
/*     */ 
/*     */     
/* 141 */     RectangleEdge domainAxisLocation = plot.getDomainAxisEdge();
/* 142 */     RectangleEdge rangeAxisLocation = plot.getRangeAxisEdge();
/* 143 */     double ax1 = domainAxis.valueToJava2D(x.doubleValue(), plotArea, domainAxisLocation);
/*     */     
/* 145 */     double ay1 = rangeAxis.valueToJava2D(0.0D, plotArea, rangeAxisLocation);
/*     */     
/* 147 */     double rax2 = x.doubleValue() + windForce * Math.cos(wdirt) * 8000000.0D;
/* 148 */     double ray2 = windForce * Math.sin(wdirt);
/*     */     
/* 150 */     double ax2 = domainAxis.valueToJava2D(rax2, plotArea, domainAxisLocation);
/* 151 */     double ay2 = rangeAxis.valueToJava2D(ray2, plotArea, rangeAxisLocation);
/*     */     
/* 153 */     int diri = windDir.intValue();
/* 154 */     int forcei = wforce.intValue();
/* 155 */     String dirforce = diri + "-" + forcei;
/* 156 */     Line2D line = new Line2D.Double(ax1, ay1, ax2, ay2);
/*     */     
/* 158 */     g2.draw(line);
/* 159 */     g2.setPaint(Color.blue);
/* 160 */     g2.setFont(new Font("Dialog", 1, 9));
/*     */     
/* 162 */     g2.drawString(dirforce, (float)ax1, (float)ay1);
/*     */     
/* 164 */     g2.setPaint(seriesPaint);
/* 165 */     g2.setStroke(seriesStroke);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     double aldir = Math.toRadians(windDir.doubleValue() * -30.0D - 90.0D - 5.0D);
/*     */ 
/*     */     
/* 173 */     double ralx2 = wforce.doubleValue() * Math.cos(aldir) * 8000000.0D * 0.8D + x.doubleValue();
/* 174 */     double raly2 = wforce.doubleValue() * Math.sin(aldir) * 0.8D;
/*     */     
/* 176 */     double alx2 = domainAxis.valueToJava2D(ralx2, plotArea, domainAxisLocation);
/* 177 */     double aly2 = rangeAxis.valueToJava2D(raly2, plotArea, rangeAxisLocation);
/*     */     
/* 179 */     line = new Line2D.Double(alx2, aly2, ax2, ay2);
/* 180 */     g2.draw(line);
/*     */     
/* 182 */     double ardir = Math.toRadians(windDir.doubleValue() * -30.0D - 90.0D + 5.0D);
/*     */ 
/*     */     
/* 185 */     double rarx2 = wforce.doubleValue() * Math.cos(ardir) * 8000000.0D * 0.8D + x.doubleValue();
/* 186 */     double rary2 = wforce.doubleValue() * Math.sin(ardir) * 0.8D;
/*     */     
/* 188 */     double arx2 = domainAxis.valueToJava2D(rarx2, plotArea, domainAxisLocation);
/* 189 */     double ary2 = rangeAxis.valueToJava2D(rary2, plotArea, rangeAxisLocation);
/*     */     
/* 191 */     line = new Line2D.Double(arx2, ary2, ax2, ay2);
/* 192 */     g2.draw(line);
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 205 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/WindItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */