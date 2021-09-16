/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.xy.IntervalXYDataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeviationRenderer
/*     */   extends XYLineAndShapeRenderer
/*     */ {
/*     */   private float alpha;
/*     */   
/*     */   public static class State
/*     */     extends XYLineAndShapeRenderer.State
/*     */   {
/*     */     public List upperCoordinates;
/*     */     public List lowerCoordinates;
/*     */     
/*     */     public State(PlotRenderingInfo info) {
/* 105 */       super(info);
/* 106 */       this.lowerCoordinates = new ArrayList();
/* 107 */       this.upperCoordinates = new ArrayList();
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
/*     */   public DeviationRenderer() {
/* 120 */     this(true, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviationRenderer(boolean lines, boolean shapes) {
/* 130 */     super(lines, shapes);
/* 131 */     super.setDrawSeriesLineAsPath(true);
/* 132 */     this.alpha = 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAlpha() {
/* 143 */     return this.alpha;
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
/*     */   public void setAlpha(float alpha) {
/* 155 */     if (alpha < 0.0F || alpha > 1.0F) {
/* 156 */       throw new IllegalArgumentException("Requires 'alpha' in the range 0.0 to 1.0.");
/*     */     }
/*     */     
/* 159 */     this.alpha = alpha;
/* 160 */     fireChangeEvent();
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
/*     */   public void setDrawSeriesLineAsPath(boolean flag) {}
/*     */ 
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
/* 185 */     return findRangeBounds(dataset, true);
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
/*     */   public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset dataset, PlotRenderingInfo info) {
/* 203 */     State state = new State(info);
/* 204 */     state.seriesPath = new GeneralPath();
/* 205 */     state.setProcessVisibleItemsOnly(false);
/* 206 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPassCount() {
/* 217 */     return 3;
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
/*     */   protected boolean isItemPass(int pass) {
/* 232 */     return (pass == 2);
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
/*     */   protected boolean isLinePass(int pass) {
/* 247 */     return (pass == 1);
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
/* 275 */     if (!getItemVisible(series, item)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 280 */     if (pass == 0) {
/* 281 */       IntervalXYDataset intervalDataset = (IntervalXYDataset)dataset;
/* 282 */       State drState = (State)state;
/*     */       
/* 284 */       double x = intervalDataset.getXValue(series, item);
/* 285 */       double yLow = intervalDataset.getStartYValue(series, item);
/* 286 */       double yHigh = intervalDataset.getEndYValue(series, item);
/*     */       
/* 288 */       RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
/* 289 */       RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
/*     */       
/* 291 */       double xx = domainAxis.valueToJava2D(x, dataArea, xAxisLocation);
/* 292 */       double yyLow = rangeAxis.valueToJava2D(yLow, dataArea, yAxisLocation);
/*     */       
/* 294 */       double yyHigh = rangeAxis.valueToJava2D(yHigh, dataArea, yAxisLocation);
/*     */ 
/*     */       
/* 297 */       PlotOrientation orientation = plot.getOrientation();
/* 298 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 299 */         drState.lowerCoordinates.add(new double[] { yyLow, xx });
/* 300 */         drState.upperCoordinates.add(new double[] { yyHigh, xx });
/*     */       }
/* 302 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 303 */         drState.lowerCoordinates.add(new double[] { xx, yyLow });
/* 304 */         drState.upperCoordinates.add(new double[] { xx, yyHigh });
/*     */       } 
/*     */       
/* 307 */       if (item == dataset.getItemCount(series) - 1) {
/*     */ 
/*     */         
/* 310 */         Composite originalComposite = g2.getComposite();
/* 311 */         g2.setComposite(AlphaComposite.getInstance(3, this.alpha));
/*     */         
/* 313 */         g2.setPaint(getItemFillPaint(series, item));
/*     */ 
/*     */         
/* 316 */         GeneralPath area = new GeneralPath(1, drState.lowerCoordinates.size() + drState.upperCoordinates.size());
/* 317 */         double[] coords = drState.lowerCoordinates.get(0);
/* 318 */         area.moveTo((float)coords[0], (float)coords[1]);
/* 319 */         for (int i = 1; i < drState.lowerCoordinates.size(); i++) {
/* 320 */           coords = drState.lowerCoordinates.get(i);
/* 321 */           area.lineTo((float)coords[0], (float)coords[1]);
/*     */         } 
/* 323 */         int count = drState.upperCoordinates.size();
/* 324 */         coords = drState.upperCoordinates.get(count - 1);
/* 325 */         area.lineTo((float)coords[0], (float)coords[1]);
/* 326 */         for (int j = count - 2; j >= 0; j--) {
/* 327 */           coords = drState.upperCoordinates.get(j);
/* 328 */           area.lineTo((float)coords[0], (float)coords[1]);
/*     */         } 
/* 330 */         area.closePath();
/* 331 */         g2.fill(area);
/* 332 */         g2.setComposite(originalComposite);
/*     */         
/* 334 */         drState.lowerCoordinates.clear();
/* 335 */         drState.upperCoordinates.clear();
/*     */       } 
/*     */     } 
/* 338 */     if (isLinePass(pass)) {
/*     */ 
/*     */ 
/*     */       
/* 342 */       if (item == 0) {
/* 343 */         State s = (State)state;
/* 344 */         s.seriesPath.reset();
/* 345 */         s.setLastPointGood(false);
/*     */       } 
/*     */       
/* 348 */       if (getItemLineVisible(series, item)) {
/* 349 */         drawPrimaryLineAsPath(state, g2, plot, dataset, pass, series, item, domainAxis, rangeAxis, dataArea);
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 355 */     else if (isItemPass(pass)) {
/*     */ 
/*     */       
/* 358 */       EntityCollection entities = null;
/* 359 */       if (info != null) {
/* 360 */         entities = info.getOwner().getEntityCollection();
/*     */       }
/*     */       
/* 363 */       drawSecondaryPass(g2, plot, dataset, pass, series, item, domainAxis, dataArea, rangeAxis, crosshairState, entities);
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
/* 377 */     if (obj == this) {
/* 378 */       return true;
/*     */     }
/* 380 */     if (!(obj instanceof DeviationRenderer)) {
/* 381 */       return false;
/*     */     }
/* 383 */     DeviationRenderer that = (DeviationRenderer)obj;
/* 384 */     if (this.alpha != that.alpha) {
/* 385 */       return false;
/*     */     }
/* 387 */     return super.equals(obj);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/DeviationRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */