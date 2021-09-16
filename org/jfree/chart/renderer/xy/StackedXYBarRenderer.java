/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.ItemLabelAnchor;
/*     */ import org.jfree.chart.labels.ItemLabelPosition;
/*     */ import org.jfree.chart.labels.XYItemLabelGenerator;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.general.DatasetUtilities;
/*     */ import org.jfree.data.xy.IntervalXYDataset;
/*     */ import org.jfree.data.xy.TableXYDataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StackedXYBarRenderer
/*     */   extends XYBarRenderer
/*     */ {
/*     */   private static final long serialVersionUID = -7049101055533436444L;
/*     */   private boolean renderAsPercentages;
/*     */   
/*     */   public StackedXYBarRenderer() {
/* 107 */     this(0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackedXYBarRenderer(double margin) {
/* 116 */     super(margin);
/* 117 */     this.renderAsPercentages = false;
/*     */ 
/*     */ 
/*     */     
/* 121 */     ItemLabelPosition p = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER);
/*     */     
/* 123 */     setBasePositiveItemLabelPosition(p);
/* 124 */     setBaseNegativeItemLabelPosition(p);
/* 125 */     setPositiveItemLabelPositionFallback((ItemLabelPosition)null);
/* 126 */     setNegativeItemLabelPositionFallback((ItemLabelPosition)null);
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
/*     */   public boolean getRenderAsPercentages() {
/* 141 */     return this.renderAsPercentages;
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
/*     */   public void setRenderAsPercentages(boolean asPercentages) {
/* 156 */     this.renderAsPercentages = asPercentages;
/* 157 */     fireChangeEvent();
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
/*     */   public int getPassCount() {
/* 170 */     return 3;
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
/*     */   public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset data, PlotRenderingInfo info) {
/* 190 */     return new XYBarRenderer.XYBarRendererState(this, info);
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
/* 204 */     if (dataset != null) {
/* 205 */       if (this.renderAsPercentages) {
/* 206 */         return new Range(0.0D, 1.0D);
/*     */       }
/*     */       
/* 209 */       return DatasetUtilities.findStackedRangeBounds((TableXYDataset)dataset);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 214 */     return null;
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
/*     */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/*     */     double translatedBase, translatedValue;
/*     */     RectangleEdge barBase;
/* 242 */     if (!getItemVisible(series, item)) {
/*     */       return;
/*     */     }
/*     */     
/* 246 */     if (!(dataset instanceof IntervalXYDataset) || !(dataset instanceof TableXYDataset)) {
/*     */       
/* 248 */       String message = "dataset (type " + dataset.getClass().getName() + ") has wrong type:";
/*     */       
/* 250 */       boolean and = false;
/* 251 */       if (!IntervalXYDataset.class.isAssignableFrom(dataset.getClass())) {
/* 252 */         message = message + " it is no IntervalXYDataset";
/* 253 */         and = true;
/*     */       } 
/* 255 */       if (!TableXYDataset.class.isAssignableFrom(dataset.getClass())) {
/* 256 */         if (and) {
/* 257 */           message = message + " and";
/*     */         }
/* 259 */         message = message + " it is no TableXYDataset";
/*     */       } 
/*     */       
/* 262 */       throw new IllegalArgumentException(message);
/*     */     } 
/*     */     
/* 265 */     IntervalXYDataset intervalDataset = (IntervalXYDataset)dataset;
/* 266 */     double value = intervalDataset.getYValue(series, item);
/* 267 */     if (Double.isNaN(value)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 278 */     double total = 0.0D;
/* 279 */     if (this.renderAsPercentages) {
/* 280 */       total = DatasetUtilities.calculateStackTotal((TableXYDataset)dataset, item);
/*     */       
/* 282 */       value /= total;
/*     */     } 
/*     */     
/* 285 */     double positiveBase = 0.0D;
/* 286 */     double negativeBase = 0.0D;
/*     */     
/* 288 */     for (int i = 0; i < series; i++) {
/* 289 */       double v = dataset.getYValue(i, item);
/* 290 */       if (!Double.isNaN(v) && isSeriesVisible(i)) {
/* 291 */         if (this.renderAsPercentages) {
/* 292 */           v /= total;
/*     */         }
/* 294 */         if (v > 0.0D) {
/* 295 */           positiveBase += v;
/*     */         } else {
/*     */           
/* 298 */           negativeBase += v;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 305 */     RectangleEdge edgeR = plot.getRangeAxisEdge();
/* 306 */     if (value > 0.0D) {
/* 307 */       translatedBase = rangeAxis.valueToJava2D(positiveBase, dataArea, edgeR);
/*     */       
/* 309 */       translatedValue = rangeAxis.valueToJava2D(positiveBase + value, dataArea, edgeR);
/*     */     }
/*     */     else {
/*     */       
/* 313 */       translatedBase = rangeAxis.valueToJava2D(negativeBase, dataArea, edgeR);
/*     */       
/* 315 */       translatedValue = rangeAxis.valueToJava2D(negativeBase + value, dataArea, edgeR);
/*     */     } 
/*     */ 
/*     */     
/* 319 */     RectangleEdge edgeD = plot.getDomainAxisEdge();
/* 320 */     double startX = intervalDataset.getStartXValue(series, item);
/* 321 */     if (Double.isNaN(startX)) {
/*     */       return;
/*     */     }
/* 324 */     double translatedStartX = domainAxis.valueToJava2D(startX, dataArea, edgeD);
/*     */ 
/*     */     
/* 327 */     double endX = intervalDataset.getEndXValue(series, item);
/* 328 */     if (Double.isNaN(endX)) {
/*     */       return;
/*     */     }
/* 331 */     double translatedEndX = domainAxis.valueToJava2D(endX, dataArea, edgeD);
/*     */     
/* 333 */     double translatedWidth = Math.max(1.0D, Math.abs(translatedEndX - translatedStartX));
/*     */     
/* 335 */     double translatedHeight = Math.abs(translatedValue - translatedBase);
/* 336 */     if (getMargin() > 0.0D) {
/* 337 */       double cut = translatedWidth * getMargin();
/* 338 */       translatedWidth -= cut;
/* 339 */       translatedStartX += cut / 2.0D;
/*     */     } 
/*     */     
/* 342 */     Rectangle2D bar = null;
/* 343 */     PlotOrientation orientation = plot.getOrientation();
/* 344 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*     */       
/* 346 */       bar = new Rectangle2D.Double(Math.min(translatedBase, translatedValue), Math.min(translatedEndX, translatedStartX), translatedHeight, translatedWidth);
/*     */     
/*     */     }
/* 349 */     else if (orientation == PlotOrientation.VERTICAL) {
/*     */       
/* 351 */       bar = new Rectangle2D.Double(Math.min(translatedStartX, translatedEndX), Math.min(translatedBase, translatedValue), translatedWidth, translatedHeight);
/*     */     } else {
/*     */       
/* 354 */       throw new IllegalStateException();
/*     */     } 
/* 356 */     boolean positive = (value > 0.0D);
/* 357 */     boolean inverted = rangeAxis.isInverted();
/*     */     
/* 359 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 360 */       if ((positive && inverted) || (!positive && !inverted)) {
/* 361 */         barBase = RectangleEdge.RIGHT;
/*     */       } else {
/*     */         
/* 364 */         barBase = RectangleEdge.LEFT;
/*     */       }
/*     */     
/*     */     }
/* 368 */     else if ((positive && !inverted) || (!positive && inverted)) {
/* 369 */       barBase = RectangleEdge.BOTTOM;
/*     */     } else {
/*     */       
/* 372 */       barBase = RectangleEdge.TOP;
/*     */     } 
/*     */ 
/*     */     
/* 376 */     if (pass == 0) {
/* 377 */       if (getShadowsVisible()) {
/* 378 */         getBarPainter().paintBarShadow(g2, this, series, item, bar, barBase, false);
/*     */       
/*     */       }
/*     */     }
/* 382 */     else if (pass == 1) {
/* 383 */       getBarPainter().paintBar(g2, this, series, item, bar, barBase);
/*     */ 
/*     */       
/* 386 */       if (info != null)
/*     */       {
/* 388 */         EntityCollection entities = info.getOwner().getEntityCollection();
/* 389 */         if (entities != null) {
/* 390 */           addEntity(entities, bar, dataset, series, item, bar
/* 391 */               .getCenterX(), bar.getCenterY());
/*     */         }
/*     */       }
/*     */     
/* 395 */     } else if (pass == 2) {
/*     */ 
/*     */       
/* 398 */       if (isItemLabelVisible(series, item)) {
/* 399 */         XYItemLabelGenerator generator = getItemLabelGenerator(series, item);
/*     */         
/* 401 */         drawItemLabel(g2, dataset, series, item, plot, generator, bar, (value < 0.0D));
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
/*     */   public boolean equals(Object obj) {
/* 417 */     if (obj == this) {
/* 418 */       return true;
/*     */     }
/* 420 */     if (!(obj instanceof StackedXYBarRenderer)) {
/* 421 */       return false;
/*     */     }
/* 423 */     StackedXYBarRenderer that = (StackedXYBarRenderer)obj;
/* 424 */     if (this.renderAsPercentages != that.renderAsPercentages) {
/* 425 */       return false;
/*     */     }
/* 427 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 437 */     int result = super.hashCode();
/* 438 */     result = result * 37 + (this.renderAsPercentages ? 1 : 0);
/* 439 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/StackedXYBarRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */