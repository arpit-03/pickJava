/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import org.jfree.chart.LegendItem;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.data.general.Dataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.data.xy.XYZDataset;
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
/*     */ public class XYBubbleRenderer
/*     */   extends AbstractXYItemRenderer
/*     */   implements XYItemRenderer, PublicCloneable
/*     */ {
/*     */   public static final long serialVersionUID = -5221991598674249125L;
/*     */   public static final int SCALE_ON_BOTH_AXES = 0;
/*     */   public static final int SCALE_ON_DOMAIN_AXIS = 1;
/*     */   public static final int SCALE_ON_RANGE_AXIS = 2;
/*     */   private int scaleType;
/*     */   
/*     */   public XYBubbleRenderer() {
/* 126 */     this(0);
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
/*     */   public XYBubbleRenderer(int scaleType) {
/* 138 */     if (scaleType < 0 || scaleType > 2) {
/* 139 */       throw new IllegalArgumentException("Invalid 'scaleType'.");
/*     */     }
/* 141 */     this.scaleType = scaleType;
/* 142 */     setBaseLegendShape(new Ellipse2D.Double(-4.0D, -4.0D, 8.0D, 8.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScaleType() {
/* 152 */     return this.scaleType;
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
/*     */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/* 180 */     if (!getItemVisible(series, item)) {
/*     */       return;
/*     */     }
/*     */     
/* 184 */     PlotOrientation orientation = plot.getOrientation();
/*     */ 
/*     */     
/* 187 */     double x = dataset.getXValue(series, item);
/* 188 */     double y = dataset.getYValue(series, item);
/* 189 */     double z = Double.NaN;
/* 190 */     if (dataset instanceof XYZDataset) {
/* 191 */       XYZDataset xyzData = (XYZDataset)dataset;
/* 192 */       z = xyzData.getZValue(series, item);
/*     */     } 
/* 194 */     if (!Double.isNaN(z)) {
/* 195 */       double zero, zero1, zero2; RectangleEdge domainAxisLocation = plot.getDomainAxisEdge();
/* 196 */       RectangleEdge rangeAxisLocation = plot.getRangeAxisEdge();
/* 197 */       double transX = domainAxis.valueToJava2D(x, dataArea, domainAxisLocation);
/*     */       
/* 199 */       double transY = rangeAxis.valueToJava2D(y, dataArea, rangeAxisLocation);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 206 */       switch (getScaleType()) {
/*     */         case 1:
/* 208 */           zero = domainAxis.valueToJava2D(0.0D, dataArea, domainAxisLocation);
/*     */           
/* 210 */           transDomain = domainAxis.valueToJava2D(z, dataArea, domainAxisLocation) - zero;
/*     */           
/* 212 */           transRange = transDomain;
/*     */           break;
/*     */         case 2:
/* 215 */           zero = rangeAxis.valueToJava2D(0.0D, dataArea, rangeAxisLocation);
/*     */           
/* 217 */           transRange = zero - rangeAxis.valueToJava2D(z, dataArea, rangeAxisLocation);
/*     */           
/* 219 */           transDomain = transRange;
/*     */           break;
/*     */         default:
/* 222 */           zero1 = domainAxis.valueToJava2D(0.0D, dataArea, domainAxisLocation);
/*     */           
/* 224 */           zero2 = rangeAxis.valueToJava2D(0.0D, dataArea, rangeAxisLocation);
/*     */           
/* 226 */           transDomain = domainAxis.valueToJava2D(z, dataArea, domainAxisLocation) - zero1;
/*     */           
/* 228 */           transRange = zero2 - rangeAxis.valueToJava2D(z, dataArea, rangeAxisLocation);
/*     */           break;
/*     */       } 
/* 231 */       double transDomain = Math.abs(transDomain);
/* 232 */       double transRange = Math.abs(transRange);
/* 233 */       Ellipse2D circle = null;
/* 234 */       if (orientation == PlotOrientation.VERTICAL) {
/* 235 */         circle = new Ellipse2D.Double(transX - transDomain / 2.0D, transY - transRange / 2.0D, transDomain, transRange);
/*     */       
/*     */       }
/* 238 */       else if (orientation == PlotOrientation.HORIZONTAL) {
/* 239 */         circle = new Ellipse2D.Double(transY - transRange / 2.0D, transX - transDomain / 2.0D, transRange, transDomain);
/*     */       } else {
/*     */         
/* 242 */         throw new IllegalStateException();
/*     */       } 
/* 244 */       g2.setPaint(getItemPaint(series, item));
/* 245 */       g2.fill(circle);
/* 246 */       g2.setStroke(getItemOutlineStroke(series, item));
/* 247 */       g2.setPaint(getItemOutlinePaint(series, item));
/* 248 */       g2.draw(circle);
/*     */       
/* 250 */       if (isItemLabelVisible(series, item)) {
/* 251 */         if (orientation == PlotOrientation.VERTICAL) {
/* 252 */           drawItemLabel(g2, orientation, dataset, series, item, transX, transY, false);
/*     */         
/*     */         }
/* 255 */         else if (orientation == PlotOrientation.HORIZONTAL) {
/* 256 */           drawItemLabel(g2, orientation, dataset, series, item, transY, transX, false);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 262 */       if (info != null) {
/*     */         
/* 264 */         EntityCollection entities = info.getOwner().getEntityCollection();
/* 265 */         if (entities != null && circle.intersects(dataArea)) {
/* 266 */           addEntity(entities, circle, dataset, series, item, circle
/* 267 */               .getCenterX(), circle.getCenterY());
/*     */         }
/*     */       } 
/*     */       
/* 271 */       int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
/* 272 */       int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
/* 273 */       updateCrosshairValues(crosshairState, x, y, domainAxisIndex, rangeAxisIndex, transX, transY, orientation);
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
/*     */   public LegendItem getLegendItem(int datasetIndex, int series) {
/* 290 */     LegendItem result = null;
/* 291 */     XYPlot plot = getPlot();
/* 292 */     if (plot == null) {
/* 293 */       return null;
/*     */     }
/*     */     
/* 296 */     XYDataset dataset = plot.getDataset(datasetIndex);
/* 297 */     if (dataset != null && 
/* 298 */       getItemVisible(series, 0)) {
/* 299 */       String label = getLegendItemLabelGenerator().generateLabel(dataset, series);
/*     */       
/* 301 */       String description = label;
/* 302 */       String toolTipText = null;
/* 303 */       if (getLegendItemToolTipGenerator() != null) {
/* 304 */         toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*     */       }
/*     */       
/* 307 */       String urlText = null;
/* 308 */       if (getLegendItemURLGenerator() != null) {
/* 309 */         urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*     */       }
/*     */       
/* 312 */       Shape shape = lookupLegendShape(series);
/* 313 */       Paint paint = lookupSeriesPaint(series);
/* 314 */       Paint outlinePaint = lookupSeriesOutlinePaint(series);
/* 315 */       Stroke outlineStroke = lookupSeriesOutlineStroke(series);
/* 316 */       result = new LegendItem(label, description, toolTipText, urlText, shape, paint, outlineStroke, outlinePaint);
/*     */       
/* 318 */       result.setLabelFont(lookupLegendTextFont(series));
/* 319 */       Paint labelPaint = lookupLegendTextPaint(series);
/* 320 */       if (labelPaint != null) {
/* 321 */         result.setLabelPaint(labelPaint);
/*     */       }
/* 323 */       result.setDataset((Dataset)dataset);
/* 324 */       result.setDatasetIndex(datasetIndex);
/* 325 */       result.setSeriesKey(dataset.getSeriesKey(series));
/* 326 */       result.setSeriesIndex(series);
/*     */     } 
/*     */     
/* 329 */     return result;
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
/* 341 */     if (obj == this) {
/* 342 */       return true;
/*     */     }
/* 344 */     if (!(obj instanceof XYBubbleRenderer)) {
/* 345 */       return false;
/*     */     }
/* 347 */     XYBubbleRenderer that = (XYBubbleRenderer)obj;
/* 348 */     if (this.scaleType != that.scaleType) {
/* 349 */       return false;
/*     */     }
/* 351 */     return super.equals(obj);
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
/* 363 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYBubbleRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */