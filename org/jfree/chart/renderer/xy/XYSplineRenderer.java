/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.ui.GradientPaintTransformer;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.StandardGradientPaintTransformer;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XYSplineRenderer
/*     */   extends XYLineAndShapeRenderer
/*     */ {
/*     */   private int precision;
/*     */   private FillType fillType;
/*     */   private GradientPaintTransformer gradientPaintTransformer;
/*     */   
/*     */   public enum FillType
/*     */   {
/*  93 */     NONE,
/*  94 */     TO_ZERO,
/*  95 */     TO_LOWER_BOUND,
/*  96 */     TO_UPPER_BOUND;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class XYSplineState
/*     */     extends XYLineAndShapeRenderer.State
/*     */   {
/*     */     public GeneralPath fillArea;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<Point2D> points;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public XYSplineState(PlotRenderingInfo info) {
/* 117 */       super(info);
/* 118 */       this.fillArea = new GeneralPath();
/* 119 */       this.points = new ArrayList<Point2D>();
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
/*     */   public XYSplineRenderer() {
/* 141 */     this(5, FillType.NONE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYSplineRenderer(int precision) {
/* 151 */     this(precision, FillType.NONE);
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
/*     */   public XYSplineRenderer(int precision, FillType fillType) {
/* 166 */     if (precision <= 0) {
/* 167 */       throw new IllegalArgumentException("Requires precision > 0.");
/*     */     }
/* 169 */     ParamChecks.nullNotPermitted(fillType, "fillType");
/* 170 */     this.precision = precision;
/* 171 */     this.fillType = fillType;
/* 172 */     this.gradientPaintTransformer = (GradientPaintTransformer)new StandardGradientPaintTransformer();
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
/*     */   public int getPrecision() {
/* 184 */     return this.precision;
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
/*     */   public void setPrecision(int p) {
/* 196 */     if (p <= 0) {
/* 197 */       throw new IllegalArgumentException("Requires p > 0.");
/*     */     }
/* 199 */     this.precision = p;
/* 200 */     fireChangeEvent();
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
/*     */   public FillType getFillType() {
/* 213 */     return this.fillType;
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
/*     */   public void setFillType(FillType fillType) {
/* 227 */     this.fillType = fillType;
/* 228 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientPaintTransformer getGradientPaintTransformer() {
/* 239 */     return this.gradientPaintTransformer;
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
/*     */   public void setGradientPaintTransformer(GradientPaintTransformer gpt) {
/* 251 */     this.gradientPaintTransformer = gpt;
/* 252 */     fireChangeEvent();
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
/*     */   public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset data, PlotRenderingInfo info) {
/* 275 */     setDrawSeriesLineAsPath(true);
/* 276 */     XYSplineState state = new XYSplineState(info);
/* 277 */     state.setProcessVisibleItemsOnly(false);
/* 278 */     return state;
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
/*     */   protected void drawPrimaryLineAsPath(XYItemRendererState state, Graphics2D g2, XYPlot plot, XYDataset dataset, int pass, int series, int item, ValueAxis xAxis, ValueAxis yAxis, Rectangle2D dataArea) {
/* 305 */     XYSplineState s = (XYSplineState)state;
/* 306 */     RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
/* 307 */     RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
/*     */ 
/*     */     
/* 310 */     double x1 = dataset.getXValue(series, item);
/* 311 */     double y1 = dataset.getYValue(series, item);
/* 312 */     double transX1 = xAxis.valueToJava2D(x1, dataArea, xAxisLocation);
/* 313 */     double transY1 = yAxis.valueToJava2D(y1, dataArea, yAxisLocation);
/*     */ 
/*     */     
/* 316 */     if (!Double.isNaN(transX1) && !Double.isNaN(transY1)) {
/* 317 */       Point2D p = (plot.getOrientation() == PlotOrientation.HORIZONTAL) ? new Point2D.Float((float)transY1, (float)transX1) : new Point2D.Float((float)transX1, (float)transY1);
/*     */ 
/*     */       
/* 320 */       if (!s.points.contains(p)) {
/* 321 */         s.points.add(p);
/*     */       }
/*     */     } 
/* 324 */     if (item == dataset.getItemCount(series) - 1) {
/* 325 */       if (s.points.size() > 1) {
/*     */         Point2D origin;
/* 327 */         if (this.fillType == FillType.TO_ZERO) {
/* 328 */           float xz = (float)xAxis.valueToJava2D(0.0D, dataArea, yAxisLocation);
/*     */           
/* 330 */           float yz = (float)yAxis.valueToJava2D(0.0D, dataArea, yAxisLocation);
/*     */           
/* 332 */           origin = (plot.getOrientation() == PlotOrientation.HORIZONTAL) ? new Point2D.Float(yz, xz) : new Point2D.Float(xz, yz);
/*     */         
/*     */         }
/* 335 */         else if (this.fillType == FillType.TO_LOWER_BOUND) {
/* 336 */           float xlb = (float)xAxis.valueToJava2D(xAxis
/* 337 */               .getLowerBound(), dataArea, xAxisLocation);
/* 338 */           float ylb = (float)yAxis.valueToJava2D(yAxis
/* 339 */               .getLowerBound(), dataArea, yAxisLocation);
/* 340 */           origin = (plot.getOrientation() == PlotOrientation.HORIZONTAL) ? new Point2D.Float(ylb, xlb) : new Point2D.Float(xlb, ylb);
/*     */         }
/*     */         else {
/*     */           
/* 344 */           float xub = (float)xAxis.valueToJava2D(xAxis
/* 345 */               .getUpperBound(), dataArea, xAxisLocation);
/* 346 */           float yub = (float)yAxis.valueToJava2D(yAxis
/* 347 */               .getUpperBound(), dataArea, yAxisLocation);
/* 348 */           origin = (plot.getOrientation() == PlotOrientation.HORIZONTAL) ? new Point2D.Float(yub, xub) : new Point2D.Float(xub, yub);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 354 */         Point2D cp0 = s.points.get(0);
/* 355 */         s.seriesPath.moveTo(cp0.getX(), cp0.getY());
/* 356 */         if (this.fillType != FillType.NONE) {
/* 357 */           if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
/* 358 */             s.fillArea.moveTo(origin.getX(), cp0.getY());
/*     */           } else {
/* 360 */             s.fillArea.moveTo(cp0.getX(), origin.getY());
/*     */           } 
/* 362 */           s.fillArea.lineTo(cp0.getX(), cp0.getY());
/*     */         } 
/* 364 */         if (s.points.size() == 2) {
/*     */ 
/*     */           
/* 367 */           Point2D cp1 = s.points.get(1);
/* 368 */           if (this.fillType != FillType.NONE) {
/* 369 */             s.fillArea.lineTo(cp1.getX(), cp1.getY());
/* 370 */             s.fillArea.lineTo(cp1.getX(), origin.getY());
/* 371 */             s.fillArea.closePath();
/*     */           } 
/* 373 */           s.seriesPath.lineTo(cp1.getX(), cp1.getY());
/*     */         } else {
/*     */           
/* 376 */           int np = s.points.size();
/* 377 */           float[] d = new float[np];
/* 378 */           float[] x = new float[np];
/*     */ 
/*     */ 
/*     */           
/* 382 */           float[] a = new float[np];
/*     */ 
/*     */           
/* 385 */           float[] h = new float[np];
/*     */           int i;
/* 387 */           for (i = 0; i < np; i++) {
/* 388 */             Point2D.Float cpi = (Point2D.Float)s.points.get(i);
/* 389 */             x[i] = cpi.x;
/* 390 */             d[i] = cpi.y;
/*     */           } 
/*     */           
/* 393 */           for (i = 1; i <= np - 1; i++) {
/* 394 */             h[i] = x[i] - x[i - 1];
/*     */           }
/* 396 */           float[] sub = new float[np - 1];
/* 397 */           float[] diag = new float[np - 1];
/* 398 */           float[] sup = new float[np - 1];
/*     */           int j;
/* 400 */           for (j = 1; j <= np - 2; j++) {
/* 401 */             diag[j] = (h[j] + h[j + 1]) / 3.0F;
/* 402 */             sup[j] = h[j + 1] / 6.0F;
/* 403 */             sub[j] = h[j] / 6.0F;
/* 404 */             a[j] = (d[j + 1] - d[j]) / h[j + 1] - (d[j] - d[j - 1]) / h[j];
/*     */           } 
/*     */           
/* 407 */           solveTridiag(sub, diag, sup, a, np - 2);
/*     */ 
/*     */           
/* 410 */           float oldt = x[0];
/* 411 */           float oldy = d[0];
/* 412 */           for (j = 1; j <= np - 1; j++) {
/*     */             
/* 414 */             for (int k = 1; k <= this.precision; k++) {
/* 415 */               float t1 = h[j] * k / this.precision;
/* 416 */               float t2 = h[j] - t1;
/* 417 */               float y = ((-a[j - 1] / 6.0F * (t2 + h[j]) * t1 + d[j - 1]) * t2 + (-a[j] / 6.0F * (t1 + h[j]) * t2 + d[j]) * t1) / h[j];
/*     */ 
/*     */               
/* 420 */               float t = x[j - 1] + t1;
/* 421 */               s.seriesPath.lineTo(t, y);
/* 422 */               if (this.fillType != FillType.NONE) {
/* 423 */                 s.fillArea.lineTo(t, y);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 429 */         if (this.fillType != FillType.NONE) {
/* 430 */           if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
/* 431 */             s.fillArea.lineTo(origin.getX(), ((Point2D)s.points.get(s.points
/* 432 */                   .size() - 1)).getY());
/*     */           } else {
/* 434 */             s.fillArea.lineTo(((Point2D)s.points.get(s.points
/* 435 */                   .size() - 1)).getX(), origin.getY());
/*     */           } 
/* 437 */           s.fillArea.closePath();
/*     */         } 
/*     */ 
/*     */         
/* 441 */         if (this.fillType != FillType.NONE) {
/* 442 */           Paint fp = getSeriesFillPaint(series);
/* 443 */           if (this.gradientPaintTransformer != null && fp instanceof GradientPaint) {
/*     */ 
/*     */             
/* 446 */             GradientPaint gp = this.gradientPaintTransformer.transform((GradientPaint)fp, s.fillArea);
/* 447 */             g2.setPaint(gp);
/*     */           } else {
/* 449 */             g2.setPaint(fp);
/*     */           } 
/* 451 */           g2.fill(s.fillArea);
/* 452 */           s.fillArea.reset();
/*     */         } 
/*     */         
/* 455 */         drawFirstPassShape(g2, pass, series, item, s.seriesPath);
/*     */       } 
/*     */       
/* 458 */       s.points = new ArrayList<Point2D>();
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
/*     */   private void solveTridiag(float[] sub, float[] diag, float[] sup, float[] b, int n) {
/*     */     int i;
/* 474 */     for (i = 2; i <= n; i++) {
/* 475 */       sub[i] = sub[i] / diag[i - 1];
/* 476 */       diag[i] = diag[i] - sub[i] * sup[i - 1];
/* 477 */       b[i] = b[i] - sub[i] * b[i - 1];
/*     */     } 
/* 479 */     b[n] = b[n] / diag[n];
/* 480 */     for (i = n - 1; i >= 1; i--) {
/* 481 */       b[i] = (b[i] - sup[i] * b[i + 1]) / diag[i];
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
/*     */   public boolean equals(Object obj) {
/* 493 */     if (obj == this) {
/* 494 */       return true;
/*     */     }
/* 496 */     if (!(obj instanceof XYSplineRenderer)) {
/* 497 */       return false;
/*     */     }
/* 499 */     XYSplineRenderer that = (XYSplineRenderer)obj;
/* 500 */     if (this.precision != that.precision) {
/* 501 */       return false;
/*     */     }
/* 503 */     if (this.fillType != that.fillType) {
/* 504 */       return false;
/*     */     }
/* 506 */     if (!ObjectUtilities.equal(this.gradientPaintTransformer, that.gradientPaintTransformer))
/*     */     {
/* 508 */       return false;
/*     */     }
/* 510 */     return super.equals(obj);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYSplineRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */