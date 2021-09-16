/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.xy.VectorXYDataset;
/*     */ import org.jfree.data.xy.XYDataset;
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
/*     */ public class VectorRenderer
/*     */   extends AbstractXYItemRenderer
/*     */   implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*  83 */   private double baseLength = 0.1D;
/*     */ 
/*     */   
/*  86 */   private double headLength = 0.14D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Range findDomainBounds(XYDataset dataset) {
/* 106 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 107 */     double minimum = Double.POSITIVE_INFINITY;
/* 108 */     double maximum = Double.NEGATIVE_INFINITY;
/* 109 */     int seriesCount = dataset.getSeriesCount();
/*     */ 
/*     */     
/* 112 */     if (dataset instanceof VectorXYDataset) {
/* 113 */       VectorXYDataset vdataset = (VectorXYDataset)dataset;
/* 114 */       for (int series = 0; series < seriesCount; series++) {
/* 115 */         int itemCount = dataset.getItemCount(series);
/* 116 */         for (int item = 0; item < itemCount; item++) {
/* 117 */           double lvalue, uvalue, delta = vdataset.getVectorXValue(series, item);
/* 118 */           if (delta < 0.0D) {
/* 119 */             uvalue = vdataset.getXValue(series, item);
/* 120 */             lvalue = uvalue + delta;
/*     */           } else {
/*     */             
/* 123 */             lvalue = vdataset.getXValue(series, item);
/* 124 */             uvalue = lvalue + delta;
/*     */           } 
/* 126 */           minimum = Math.min(minimum, lvalue);
/* 127 */           maximum = Math.max(maximum, uvalue);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 132 */       for (int series = 0; series < seriesCount; series++) {
/* 133 */         int itemCount = dataset.getItemCount(series);
/* 134 */         for (int item = 0; item < itemCount; item++) {
/* 135 */           double lvalue = dataset.getXValue(series, item);
/* 136 */           double uvalue = lvalue;
/* 137 */           minimum = Math.min(minimum, lvalue);
/* 138 */           maximum = Math.max(maximum, uvalue);
/*     */         } 
/*     */       } 
/*     */     } 
/* 142 */     if (minimum > maximum) {
/* 143 */       return null;
/*     */     }
/*     */     
/* 146 */     return new Range(minimum, maximum);
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
/*     */   public Range findRangeBounds(XYDataset dataset) {
/* 161 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 162 */     double minimum = Double.POSITIVE_INFINITY;
/* 163 */     double maximum = Double.NEGATIVE_INFINITY;
/* 164 */     int seriesCount = dataset.getSeriesCount();
/*     */ 
/*     */     
/* 167 */     if (dataset instanceof VectorXYDataset) {
/* 168 */       VectorXYDataset vdataset = (VectorXYDataset)dataset;
/* 169 */       for (int series = 0; series < seriesCount; series++) {
/* 170 */         int itemCount = dataset.getItemCount(series);
/* 171 */         for (int item = 0; item < itemCount; item++) {
/* 172 */           double lvalue, uvalue, delta = vdataset.getVectorYValue(series, item);
/* 173 */           if (delta < 0.0D) {
/* 174 */             uvalue = vdataset.getYValue(series, item);
/* 175 */             lvalue = uvalue + delta;
/*     */           } else {
/*     */             
/* 178 */             lvalue = vdataset.getYValue(series, item);
/* 179 */             uvalue = lvalue + delta;
/*     */           } 
/* 181 */           minimum = Math.min(minimum, lvalue);
/* 182 */           maximum = Math.max(maximum, uvalue);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 187 */       for (int series = 0; series < seriesCount; series++) {
/* 188 */         int itemCount = dataset.getItemCount(series);
/* 189 */         for (int item = 0; item < itemCount; item++) {
/* 190 */           double lvalue = dataset.getYValue(series, item);
/* 191 */           double uvalue = lvalue;
/* 192 */           minimum = Math.min(minimum, lvalue);
/* 193 */           maximum = Math.max(maximum, uvalue);
/*     */         } 
/*     */       } 
/*     */     } 
/* 197 */     if (minimum > maximum) {
/* 198 */       return null;
/*     */     }
/*     */     
/* 201 */     return new Range(minimum, maximum);
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
/*     */     Line2D line;
/* 227 */     double x = dataset.getXValue(series, item);
/* 228 */     double y = dataset.getYValue(series, item);
/* 229 */     double dx = 0.0D;
/* 230 */     double dy = 0.0D;
/* 231 */     if (dataset instanceof VectorXYDataset) {
/* 232 */       dx = ((VectorXYDataset)dataset).getVectorXValue(series, item);
/* 233 */       dy = ((VectorXYDataset)dataset).getVectorYValue(series, item);
/*     */     } 
/* 235 */     double xx0 = domainAxis.valueToJava2D(x, dataArea, plot
/* 236 */         .getDomainAxisEdge());
/* 237 */     double yy0 = rangeAxis.valueToJava2D(y, dataArea, plot
/* 238 */         .getRangeAxisEdge());
/* 239 */     double xx1 = domainAxis.valueToJava2D(x + dx, dataArea, plot
/* 240 */         .getDomainAxisEdge());
/* 241 */     double yy1 = rangeAxis.valueToJava2D(y + dy, dataArea, plot
/* 242 */         .getRangeAxisEdge());
/*     */     
/* 244 */     PlotOrientation orientation = plot.getOrientation();
/* 245 */     if (orientation.equals(PlotOrientation.HORIZONTAL)) {
/* 246 */       line = new Line2D.Double(yy0, xx0, yy1, xx1);
/*     */     } else {
/*     */       
/* 249 */       line = new Line2D.Double(xx0, yy0, xx1, yy1);
/*     */     } 
/* 251 */     g2.setPaint(getItemPaint(series, item));
/* 252 */     g2.setStroke(getItemStroke(series, item));
/* 253 */     g2.draw(line);
/*     */ 
/*     */     
/* 256 */     double dxx = xx1 - xx0;
/* 257 */     double dyy = yy1 - yy0;
/* 258 */     double bx = xx0 + (1.0D - this.baseLength) * dxx;
/* 259 */     double by = yy0 + (1.0D - this.baseLength) * dyy;
/*     */     
/* 261 */     double cx = xx0 + (1.0D - this.headLength) * dxx;
/* 262 */     double cy = yy0 + (1.0D - this.headLength) * dyy;
/*     */     
/* 264 */     double angle = 0.0D;
/* 265 */     if (dxx != 0.0D) {
/* 266 */       angle = 1.5707963267948966D - Math.atan(dyy / dxx);
/*     */     }
/* 268 */     double deltaX = 2.0D * Math.cos(angle);
/* 269 */     double deltaY = 2.0D * Math.sin(angle);
/*     */     
/* 271 */     double leftx = cx + deltaX;
/* 272 */     double lefty = cy - deltaY;
/* 273 */     double rightx = cx - deltaX;
/* 274 */     double righty = cy + deltaY;
/*     */     
/* 276 */     GeneralPath p = new GeneralPath();
/* 277 */     if (orientation == PlotOrientation.VERTICAL) {
/* 278 */       p.moveTo((float)xx1, (float)yy1);
/* 279 */       p.lineTo((float)rightx, (float)righty);
/* 280 */       p.lineTo((float)bx, (float)by);
/* 281 */       p.lineTo((float)leftx, (float)lefty);
/*     */     } else {
/*     */       
/* 284 */       p.moveTo((float)yy1, (float)xx1);
/* 285 */       p.lineTo((float)righty, (float)rightx);
/* 286 */       p.lineTo((float)by, (float)bx);
/* 287 */       p.lineTo((float)lefty, (float)leftx);
/*     */     } 
/* 289 */     p.closePath();
/* 290 */     g2.draw(p);
/*     */ 
/*     */ 
/*     */     
/* 294 */     if (info != null) {
/* 295 */       EntityCollection entities = info.getOwner().getEntityCollection();
/* 296 */       if (entities != null) {
/* 297 */         addEntity(entities, line.getBounds(), dataset, series, item, 0.0D, 0.0D);
/*     */       }
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
/*     */   public boolean equals(Object obj) {
/* 320 */     if (obj == this) {
/* 321 */       return true;
/*     */     }
/* 323 */     if (!(obj instanceof VectorRenderer)) {
/* 324 */       return false;
/*     */     }
/* 326 */     VectorRenderer that = (VectorRenderer)obj;
/* 327 */     if (this.baseLength != that.baseLength) {
/* 328 */       return false;
/*     */     }
/* 330 */     if (this.headLength != that.headLength) {
/* 331 */       return false;
/*     */     }
/* 333 */     return super.equals(obj);
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
/* 346 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/VectorRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */