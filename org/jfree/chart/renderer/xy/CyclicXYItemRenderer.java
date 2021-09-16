/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.CyclicNumberAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.labels.XYToolTipGenerator;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.urls.XYURLGenerator;
/*     */ import org.jfree.data.DomainOrder;
/*     */ import org.jfree.data.general.DatasetChangeListener;
/*     */ import org.jfree.data.general.DatasetGroup;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CyclicXYItemRenderer
/*     */   extends StandardXYItemRenderer
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4035912243303764892L;
/*     */   
/*     */   public CyclicXYItemRenderer() {}
/*     */   
/*     */   public CyclicXYItemRenderer(int type) {
/*  95 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CyclicXYItemRenderer(int type, XYToolTipGenerator labelGenerator) {
/* 105 */     super(type, labelGenerator);
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
/*     */   public CyclicXYItemRenderer(int type, XYToolTipGenerator labelGenerator, XYURLGenerator urlGenerator) {
/* 118 */     super(type, labelGenerator, urlGenerator);
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
/* 149 */     if (!getPlotLines() || (!(domainAxis instanceof CyclicNumberAxis) && !(rangeAxis instanceof CyclicNumberAxis)) || item <= 0) {
/*     */       
/* 151 */       super.drawItem(g2, state, dataArea, info, plot, domainAxis, rangeAxis, dataset, series, item, crosshairState, pass);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 157 */     double xn = dataset.getXValue(series, item - 1);
/* 158 */     double yn = dataset.getYValue(series, item - 1);
/*     */     
/* 160 */     if (Double.isNaN(yn)) {
/* 161 */       super.drawItem(g2, state, dataArea, info, plot, domainAxis, rangeAxis, dataset, series, item, crosshairState, pass);
/*     */       
/*     */       return;
/*     */     } 
/* 165 */     double[] x = new double[2];
/* 166 */     double[] y = new double[2];
/* 167 */     x[0] = xn;
/* 168 */     y[0] = yn;
/*     */ 
/*     */     
/* 171 */     xn = dataset.getXValue(series, item);
/* 172 */     yn = dataset.getYValue(series, item);
/*     */     
/* 174 */     if (Double.isNaN(yn)) {
/*     */       return;
/*     */     }
/* 177 */     x[1] = xn;
/* 178 */     y[1] = yn;
/*     */ 
/*     */     
/* 181 */     double xcycleBound = Double.NaN;
/* 182 */     double ycycleBound = Double.NaN;
/* 183 */     boolean xBoundMapping = false, yBoundMapping = false;
/* 184 */     CyclicNumberAxis cnax = null, cnay = null;
/*     */     
/* 186 */     if (domainAxis instanceof CyclicNumberAxis) {
/* 187 */       cnax = (CyclicNumberAxis)domainAxis;
/* 188 */       xcycleBound = cnax.getCycleBound();
/* 189 */       xBoundMapping = cnax.isBoundMappedToLastCycle();
/*     */ 
/*     */ 
/*     */       
/* 193 */       if (x[0] != x[1] && ((xcycleBound >= x[0] && xcycleBound <= x[1]) || (xcycleBound >= x[1] && xcycleBound <= x[0]))) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 198 */         double[] nx = new double[3];
/* 199 */         double[] ny = new double[3];
/* 200 */         nx[0] = x[0]; nx[2] = x[1]; ny[0] = y[0]; ny[2] = y[1];
/* 201 */         nx[1] = xcycleBound;
/* 202 */         ny[1] = (y[1] - y[0]) * (xcycleBound - x[0]) / (x[1] - x[0]) + y[0];
/*     */         
/* 204 */         x = nx; y = ny;
/*     */       } 
/*     */     } 
/*     */     
/* 208 */     if (rangeAxis instanceof CyclicNumberAxis) {
/* 209 */       cnay = (CyclicNumberAxis)rangeAxis;
/* 210 */       ycycleBound = cnay.getCycleBound();
/* 211 */       yBoundMapping = cnay.isBoundMappedToLastCycle();
/*     */ 
/*     */       
/* 214 */       if (y[0] != y[1] && ((ycycleBound >= y[0] && ycycleBound <= y[1]) || (ycycleBound >= y[1] && ycycleBound <= y[0]))) {
/*     */ 
/*     */         
/* 217 */         double[] nx = new double[x.length + 1];
/* 218 */         double[] ny = new double[y.length + 1];
/* 219 */         nx[0] = x[0]; nx[2] = x[1]; ny[0] = y[0]; ny[2] = y[1];
/* 220 */         ny[1] = ycycleBound;
/* 221 */         nx[1] = (x[1] - x[0]) * (ycycleBound - y[0]) / (y[1] - y[0]) + x[0];
/*     */         
/* 223 */         if (x.length == 3) {
/* 224 */           nx[3] = x[2]; ny[3] = y[2];
/*     */         } 
/* 226 */         x = nx; y = ny;
/*     */       }
/* 228 */       else if (x.length == 3 && y[1] != y[2] && ((ycycleBound >= y[1] && ycycleBound <= y[2]) || (ycycleBound >= y[2] && ycycleBound <= y[1]))) {
/*     */ 
/*     */         
/* 231 */         double[] nx = new double[4];
/* 232 */         double[] ny = new double[4];
/* 233 */         nx[0] = x[0]; nx[1] = x[1]; nx[3] = x[2];
/* 234 */         ny[0] = y[0]; ny[1] = y[1]; ny[3] = y[2];
/* 235 */         ny[2] = ycycleBound;
/* 236 */         nx[2] = (x[2] - x[1]) * (ycycleBound - y[1]) / (y[2] - y[1]) + x[1];
/*     */         
/* 238 */         x = nx; y = ny;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 243 */     if (x.length == 2) {
/* 244 */       super.drawItem(g2, state, dataArea, info, plot, domainAxis, rangeAxis, dataset, series, item, crosshairState, pass);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 249 */     OverwriteDataSet newset = new OverwriteDataSet(x, y, dataset);
/*     */     
/* 251 */     if (cnax != null) {
/* 252 */       if (xcycleBound == x[0]) {
/* 253 */         cnax.setBoundMappedToLastCycle((x[1] <= xcycleBound));
/*     */       }
/* 255 */       if (xcycleBound == x[1]) {
/* 256 */         cnax.setBoundMappedToLastCycle((x[0] <= xcycleBound));
/*     */       }
/*     */     } 
/* 259 */     if (cnay != null) {
/* 260 */       if (ycycleBound == y[0]) {
/* 261 */         cnay.setBoundMappedToLastCycle((y[1] <= ycycleBound));
/*     */       }
/* 263 */       if (ycycleBound == y[1]) {
/* 264 */         cnay.setBoundMappedToLastCycle((y[0] <= ycycleBound));
/*     */       }
/*     */     } 
/* 267 */     super.drawItem(g2, state, dataArea, info, plot, domainAxis, rangeAxis, newset, series, 1, crosshairState, pass);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     if (cnax != null) {
/* 273 */       if (xcycleBound == x[1]) {
/* 274 */         cnax.setBoundMappedToLastCycle((x[2] <= xcycleBound));
/*     */       }
/* 276 */       if (xcycleBound == x[2]) {
/* 277 */         cnax.setBoundMappedToLastCycle((x[1] <= xcycleBound));
/*     */       }
/*     */     } 
/* 280 */     if (cnay != null) {
/* 281 */       if (ycycleBound == y[1]) {
/* 282 */         cnay.setBoundMappedToLastCycle((y[2] <= ycycleBound));
/*     */       }
/* 284 */       if (ycycleBound == y[2]) {
/* 285 */         cnay.setBoundMappedToLastCycle((y[1] <= ycycleBound));
/*     */       }
/*     */     } 
/* 288 */     super.drawItem(g2, state, dataArea, info, plot, domainAxis, rangeAxis, newset, series, 2, crosshairState, pass);
/*     */ 
/*     */     
/* 291 */     if (x.length == 4) {
/* 292 */       if (cnax != null) {
/* 293 */         if (xcycleBound == x[2]) {
/* 294 */           cnax.setBoundMappedToLastCycle((x[3] <= xcycleBound));
/*     */         }
/* 296 */         if (xcycleBound == x[3]) {
/* 297 */           cnax.setBoundMappedToLastCycle((x[2] <= xcycleBound));
/*     */         }
/*     */       } 
/* 300 */       if (cnay != null) {
/* 301 */         if (ycycleBound == y[2]) {
/* 302 */           cnay.setBoundMappedToLastCycle((y[3] <= ycycleBound));
/*     */         }
/* 304 */         if (ycycleBound == y[3]) {
/* 305 */           cnay.setBoundMappedToLastCycle((y[2] <= ycycleBound));
/*     */         }
/*     */       } 
/* 308 */       super.drawItem(g2, state, dataArea, info, plot, domainAxis, rangeAxis, newset, series, 3, crosshairState, pass);
/*     */     } 
/*     */ 
/*     */     
/* 312 */     if (cnax != null) {
/* 313 */       cnax.setBoundMappedToLastCycle(xBoundMapping);
/*     */     }
/* 315 */     if (cnay != null) {
/* 316 */       cnay.setBoundMappedToLastCycle(yBoundMapping);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class OverwriteDataSet
/*     */     implements XYDataset
/*     */   {
/*     */     protected XYDataset delegateSet;
/*     */ 
/*     */ 
/*     */     
/*     */     Double[] x;
/*     */ 
/*     */ 
/*     */     
/*     */     Double[] y;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OverwriteDataSet(double[] x, double[] y, XYDataset delegateSet) {
/* 340 */       this.delegateSet = delegateSet;
/* 341 */       this.x = new Double[x.length]; this.y = new Double[y.length];
/* 342 */       for (int i = 0; i < x.length; i++) {
/* 343 */         this.x[i] = new Double(x[i]);
/* 344 */         this.y[i] = new Double(y[i]);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DomainOrder getDomainOrder() {
/* 355 */       return DomainOrder.NONE;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getItemCount(int series) {
/* 367 */       return this.x.length;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Number getX(int series, int item) {
/* 380 */       return this.x[item];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getXValue(int series, int item) {
/* 394 */       double result = Double.NaN;
/* 395 */       Number xx = getX(series, item);
/* 396 */       if (xx != null) {
/* 397 */         result = xx.doubleValue();
/*     */       }
/* 399 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Number getY(int series, int item) {
/* 412 */       return this.y[item];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getYValue(int series, int item) {
/* 426 */       double result = Double.NaN;
/* 427 */       Number yy = getY(series, item);
/* 428 */       if (yy != null) {
/* 429 */         result = yy.doubleValue();
/*     */       }
/* 431 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getSeriesCount() {
/* 441 */       return this.delegateSet.getSeriesCount();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Comparable getSeriesKey(int series) {
/* 453 */       return this.delegateSet.getSeriesKey(series);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(Comparable seriesName) {
/* 465 */       return this.delegateSet.indexOf(seriesName);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addChangeListener(DatasetChangeListener listener) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void removeChangeListener(DatasetChangeListener listener) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DatasetGroup getGroup() {
/* 496 */       return this.delegateSet.getGroup();
/*     */     }
/*     */     
/*     */     public void setGroup(DatasetGroup group) {}
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/CyclicXYItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */