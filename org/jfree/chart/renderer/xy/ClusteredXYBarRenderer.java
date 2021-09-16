/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.XYItemLabelGenerator;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.xy.IntervalXYDataset;
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
/*     */ public class ClusteredXYBarRenderer
/*     */   extends XYBarRenderer
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5864462149177133147L;
/*     */   private boolean centerBarAtStartValue;
/*     */   
/*     */   public ClusteredXYBarRenderer() {
/* 115 */     this(0.0D, false);
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
/*     */   public ClusteredXYBarRenderer(double margin, boolean centerBarAtStartValue) {
/* 127 */     super(margin);
/* 128 */     this.centerBarAtStartValue = centerBarAtStartValue;
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
/*     */   public int getPassCount() {
/* 140 */     return 2;
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
/*     */   public Range findDomainBounds(XYDataset dataset) {
/* 152 */     if (dataset == null) {
/* 153 */       return null;
/*     */     }
/*     */     
/* 156 */     if (this.centerBarAtStartValue) {
/* 157 */       return findDomainBoundsWithOffset((IntervalXYDataset)dataset);
/*     */     }
/*     */     
/* 160 */     return super.findDomainBounds(dataset);
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
/*     */   protected Range findDomainBoundsWithOffset(IntervalXYDataset dataset) {
/* 174 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 175 */     double minimum = Double.POSITIVE_INFINITY;
/* 176 */     double maximum = Double.NEGATIVE_INFINITY;
/* 177 */     int seriesCount = dataset.getSeriesCount();
/*     */ 
/*     */     
/* 180 */     for (int series = 0; series < seriesCount; series++) {
/* 181 */       int itemCount = dataset.getItemCount(series);
/* 182 */       for (int item = 0; item < itemCount; item++) {
/* 183 */         double lvalue = dataset.getStartXValue(series, item);
/* 184 */         double uvalue = dataset.getEndXValue(series, item);
/* 185 */         double offset = (uvalue - lvalue) / 2.0D;
/* 186 */         lvalue -= offset;
/* 187 */         uvalue -= offset;
/* 188 */         minimum = Math.min(minimum, lvalue);
/* 189 */         maximum = Math.max(maximum, uvalue);
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     if (minimum > maximum) {
/* 194 */       return null;
/*     */     }
/*     */     
/* 197 */     return new Range(minimum, maximum);
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
/*     */   
/*     */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/*     */     double y0, y1;
/*     */     RectangleEdge barBase;
/* 230 */     IntervalXYDataset intervalDataset = (IntervalXYDataset)dataset;
/*     */ 
/*     */ 
/*     */     
/* 234 */     if (getUseYInterval()) {
/* 235 */       y0 = intervalDataset.getStartYValue(series, item);
/* 236 */       y1 = intervalDataset.getEndYValue(series, item);
/*     */     } else {
/*     */       
/* 239 */       y0 = getBase();
/* 240 */       y1 = intervalDataset.getYValue(series, item);
/*     */     } 
/* 242 */     if (Double.isNaN(y0) || Double.isNaN(y1)) {
/*     */       return;
/*     */     }
/*     */     
/* 246 */     double yy0 = rangeAxis.valueToJava2D(y0, dataArea, plot
/* 247 */         .getRangeAxisEdge());
/* 248 */     double yy1 = rangeAxis.valueToJava2D(y1, dataArea, plot
/* 249 */         .getRangeAxisEdge());
/*     */     
/* 251 */     RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
/* 252 */     double x0 = intervalDataset.getStartXValue(series, item);
/* 253 */     double xx0 = domainAxis.valueToJava2D(x0, dataArea, xAxisLocation);
/*     */     
/* 255 */     double x1 = intervalDataset.getEndXValue(series, item);
/* 256 */     double xx1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
/*     */     
/* 258 */     double intervalW = xx1 - xx0;
/* 259 */     double baseX = xx0;
/* 260 */     if (this.centerBarAtStartValue) {
/* 261 */       baseX -= intervalW / 2.0D;
/*     */     }
/* 263 */     double m = getMargin();
/* 264 */     if (m > 0.0D) {
/* 265 */       double cut = intervalW * getMargin();
/* 266 */       intervalW -= cut;
/* 267 */       baseX += cut / 2.0D;
/*     */     } 
/*     */     
/* 270 */     double intervalH = Math.abs(yy0 - yy1);
/*     */     
/* 272 */     PlotOrientation orientation = plot.getOrientation();
/*     */     
/* 274 */     int numSeries = dataset.getSeriesCount();
/* 275 */     double seriesBarWidth = intervalW / numSeries;
/*     */     
/* 277 */     Rectangle2D bar = null;
/* 278 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 279 */       double barY0 = baseX + seriesBarWidth * series;
/* 280 */       double barY1 = barY0 + seriesBarWidth;
/* 281 */       double rx = Math.min(yy0, yy1);
/* 282 */       double rw = intervalH;
/* 283 */       double ry = Math.min(barY0, barY1);
/* 284 */       double rh = Math.abs(barY1 - barY0);
/* 285 */       bar = new Rectangle2D.Double(rx, ry, rw, rh);
/*     */     }
/* 287 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 288 */       double barX0 = baseX + seriesBarWidth * series;
/* 289 */       double barX1 = barX0 + seriesBarWidth;
/* 290 */       double rx = Math.min(barX0, barX1);
/* 291 */       double rw = Math.abs(barX1 - barX0);
/* 292 */       double ry = Math.min(yy0, yy1);
/* 293 */       double rh = intervalH;
/* 294 */       bar = new Rectangle2D.Double(rx, ry, rw, rh);
/*     */     } else {
/* 296 */       throw new IllegalStateException();
/*     */     } 
/* 298 */     boolean positive = (y1 > 0.0D);
/* 299 */     boolean inverted = rangeAxis.isInverted();
/*     */     
/* 301 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 302 */       if ((positive && inverted) || (!positive && !inverted)) {
/* 303 */         barBase = RectangleEdge.RIGHT;
/*     */       } else {
/*     */         
/* 306 */         barBase = RectangleEdge.LEFT;
/*     */       }
/*     */     
/*     */     }
/* 310 */     else if ((positive && !inverted) || (!positive && inverted)) {
/* 311 */       barBase = RectangleEdge.BOTTOM;
/*     */     } else {
/*     */       
/* 314 */       barBase = RectangleEdge.TOP;
/*     */     } 
/*     */     
/* 317 */     if (pass == 0 && getShadowsVisible()) {
/* 318 */       getBarPainter().paintBarShadow(g2, this, series, item, bar, barBase, 
/* 319 */           !getUseYInterval());
/*     */     }
/* 321 */     if (pass == 1) {
/* 322 */       getBarPainter().paintBar(g2, this, series, item, bar, barBase);
/*     */       
/* 324 */       if (isItemLabelVisible(series, item)) {
/* 325 */         XYItemLabelGenerator generator = getItemLabelGenerator(series, item);
/*     */         
/* 327 */         drawItemLabel(g2, dataset, series, item, plot, generator, bar, (y1 < 0.0D));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 332 */       if (info != null) {
/*     */         
/* 334 */         EntityCollection entities = info.getOwner().getEntityCollection();
/* 335 */         if (entities != null) {
/* 336 */           addEntity(entities, bar, dataset, series, item, bar
/* 337 */               .getCenterX(), bar.getCenterY());
/*     */         }
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
/*     */   public boolean equals(Object obj) {
/* 356 */     if (obj == this) {
/* 357 */       return true;
/*     */     }
/* 359 */     if (!(obj instanceof ClusteredXYBarRenderer)) {
/* 360 */       return false;
/*     */     }
/* 362 */     ClusteredXYBarRenderer that = (ClusteredXYBarRenderer)obj;
/* 363 */     if (this.centerBarAtStartValue != that.centerBarAtStartValue) {
/* 364 */       return false;
/*     */     }
/* 366 */     return super.equals(obj);
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
/* 378 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/ClusteredXYBarRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */