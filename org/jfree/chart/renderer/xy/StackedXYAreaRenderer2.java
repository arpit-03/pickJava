/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.XYToolTipGenerator;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.urls.XYURLGenerator;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.xy.TableXYDataset;
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
/*     */ public class StackedXYAreaRenderer2
/*     */   extends XYAreaRenderer2
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7752676509764539182L;
/*     */   private boolean roundXCoordinates;
/*     */   
/*     */   public StackedXYAreaRenderer2() {
/* 111 */     this((XYToolTipGenerator)null, (XYURLGenerator)null);
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
/*     */   public StackedXYAreaRenderer2(XYToolTipGenerator labelGenerator, XYURLGenerator urlGenerator) {
/* 123 */     super(labelGenerator, urlGenerator);
/* 124 */     this.roundXCoordinates = true;
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
/*     */   public boolean getRoundXCoordinates() {
/* 138 */     return this.roundXCoordinates;
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
/*     */   public void setRoundXCoordinates(boolean round) {
/* 153 */     this.roundXCoordinates = round;
/* 154 */     fireChangeEvent();
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
/* 168 */     if (dataset == null) {
/* 169 */       return null;
/*     */     }
/* 171 */     double min = Double.POSITIVE_INFINITY;
/* 172 */     double max = Double.NEGATIVE_INFINITY;
/* 173 */     TableXYDataset d = (TableXYDataset)dataset;
/* 174 */     int itemCount = d.getItemCount();
/* 175 */     for (int i = 0; i < itemCount; i++) {
/* 176 */       double[] stackValues = getStackValues((TableXYDataset)dataset, d
/* 177 */           .getSeriesCount(), i);
/* 178 */       min = Math.min(min, stackValues[0]);
/* 179 */       max = Math.max(max, stackValues[1]);
/*     */     } 
/* 181 */     if (min == Double.POSITIVE_INFINITY) {
/* 182 */       return null;
/*     */     }
/* 184 */     return new Range(min, max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPassCount() {
/* 194 */     return 1;
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
/*     */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/*     */     float transY1;
/* 222 */     EntityCollection entities = null;
/* 223 */     if (info != null) {
/* 224 */       entities = info.getOwner().getEntityCollection();
/*     */     }
/*     */     
/* 227 */     TableXYDataset tdataset = (TableXYDataset)dataset;
/* 228 */     PlotOrientation orientation = plot.getOrientation();
/*     */ 
/*     */     
/* 231 */     double x1 = dataset.getXValue(series, item);
/* 232 */     double y1 = dataset.getYValue(series, item);
/* 233 */     if (Double.isNaN(y1)) {
/* 234 */       y1 = 0.0D;
/*     */     }
/* 236 */     double[] stack1 = getStackValues(tdataset, series, item);
/*     */ 
/*     */ 
/*     */     
/* 240 */     double x0 = dataset.getXValue(series, Math.max(item - 1, 0));
/* 241 */     double y0 = dataset.getYValue(series, Math.max(item - 1, 0));
/* 242 */     if (Double.isNaN(y0)) {
/* 243 */       y0 = 0.0D;
/*     */     }
/* 245 */     double[] stack0 = getStackValues(tdataset, series, Math.max(item - 1, 0));
/*     */ 
/*     */     
/* 248 */     int itemCount = dataset.getItemCount(series);
/* 249 */     double x2 = dataset.getXValue(series, Math.min(item + 1, itemCount - 1));
/*     */     
/* 251 */     double y2 = dataset.getYValue(series, Math.min(item + 1, itemCount - 1));
/*     */     
/* 253 */     if (Double.isNaN(y2)) {
/* 254 */       y2 = 0.0D;
/*     */     }
/* 256 */     double[] stack2 = getStackValues(tdataset, series, Math.min(item + 1, itemCount - 1));
/*     */ 
/*     */     
/* 259 */     double xleft = (x0 + x1) / 2.0D;
/* 260 */     double xright = (x1 + x2) / 2.0D;
/* 261 */     double[] stackLeft = averageStackValues(stack0, stack1);
/* 262 */     double[] stackRight = averageStackValues(stack1, stack2);
/* 263 */     double[] adjStackLeft = adjustedStackValues(stack0, stack1);
/* 264 */     double[] adjStackRight = adjustedStackValues(stack1, stack2);
/*     */     
/* 266 */     RectangleEdge edge0 = plot.getDomainAxisEdge();
/*     */     
/* 268 */     float transX1 = (float)domainAxis.valueToJava2D(x1, dataArea, edge0);
/* 269 */     float transXLeft = (float)domainAxis.valueToJava2D(xleft, dataArea, edge0);
/*     */     
/* 271 */     float transXRight = (float)domainAxis.valueToJava2D(xright, dataArea, edge0);
/*     */ 
/*     */     
/* 274 */     if (this.roundXCoordinates) {
/* 275 */       transX1 = Math.round(transX1);
/* 276 */       transXLeft = Math.round(transXLeft);
/* 277 */       transXRight = Math.round(transXRight);
/*     */     } 
/*     */ 
/*     */     
/* 281 */     RectangleEdge edge1 = plot.getRangeAxisEdge();
/*     */     
/* 283 */     GeneralPath left = new GeneralPath();
/* 284 */     GeneralPath right = new GeneralPath();
/* 285 */     if (y1 >= 0.0D) {
/* 286 */       transY1 = (float)rangeAxis.valueToJava2D(y1 + stack1[1], dataArea, edge1);
/*     */       
/* 288 */       float transStack1 = (float)rangeAxis.valueToJava2D(stack1[1], dataArea, edge1);
/*     */       
/* 290 */       float transStackLeft = (float)rangeAxis.valueToJava2D(adjStackLeft[1], dataArea, edge1);
/*     */ 
/*     */ 
/*     */       
/* 294 */       if (y0 >= 0.0D) {
/* 295 */         double yleft = (y0 + y1) / 2.0D + stackLeft[1];
/*     */         
/* 297 */         float transYLeft = (float)rangeAxis.valueToJava2D(yleft, dataArea, edge1);
/* 298 */         if (orientation == PlotOrientation.VERTICAL) {
/* 299 */           left.moveTo(transX1, transY1);
/* 300 */           left.lineTo(transX1, transStack1);
/* 301 */           left.lineTo(transXLeft, transStackLeft);
/* 302 */           left.lineTo(transXLeft, transYLeft);
/*     */         } else {
/*     */           
/* 305 */           left.moveTo(transY1, transX1);
/* 306 */           left.lineTo(transStack1, transX1);
/* 307 */           left.lineTo(transStackLeft, transXLeft);
/* 308 */           left.lineTo(transYLeft, transXLeft);
/*     */         } 
/* 310 */         left.closePath();
/*     */       } else {
/*     */         
/* 313 */         if (orientation == PlotOrientation.VERTICAL) {
/* 314 */           left.moveTo(transX1, transStack1);
/* 315 */           left.lineTo(transX1, transY1);
/* 316 */           left.lineTo(transXLeft, transStackLeft);
/*     */         } else {
/*     */           
/* 319 */           left.moveTo(transStack1, transX1);
/* 320 */           left.lineTo(transY1, transX1);
/* 321 */           left.lineTo(transStackLeft, transXLeft);
/*     */         } 
/* 323 */         left.closePath();
/*     */       } 
/*     */       
/* 326 */       float transStackRight = (float)rangeAxis.valueToJava2D(adjStackRight[1], dataArea, edge1);
/*     */ 
/*     */       
/* 329 */       if (y2 >= 0.0D) {
/* 330 */         double yright = (y1 + y2) / 2.0D + stackRight[1];
/*     */         
/* 332 */         float transYRight = (float)rangeAxis.valueToJava2D(yright, dataArea, edge1);
/* 333 */         if (orientation == PlotOrientation.VERTICAL) {
/* 334 */           right.moveTo(transX1, transStack1);
/* 335 */           right.lineTo(transX1, transY1);
/* 336 */           right.lineTo(transXRight, transYRight);
/* 337 */           right.lineTo(transXRight, transStackRight);
/*     */         } else {
/*     */           
/* 340 */           right.moveTo(transStack1, transX1);
/* 341 */           right.lineTo(transY1, transX1);
/* 342 */           right.lineTo(transYRight, transXRight);
/* 343 */           right.lineTo(transStackRight, transXRight);
/*     */         } 
/* 345 */         right.closePath();
/*     */       } else {
/*     */         
/* 348 */         if (orientation == PlotOrientation.VERTICAL) {
/* 349 */           right.moveTo(transX1, transStack1);
/* 350 */           right.lineTo(transX1, transY1);
/* 351 */           right.lineTo(transXRight, transStackRight);
/*     */         } else {
/*     */           
/* 354 */           right.moveTo(transStack1, transX1);
/* 355 */           right.lineTo(transY1, transX1);
/* 356 */           right.lineTo(transStackRight, transXRight);
/*     */         } 
/* 358 */         right.closePath();
/*     */       } 
/*     */     } else {
/*     */       
/* 362 */       transY1 = (float)rangeAxis.valueToJava2D(y1 + stack1[0], dataArea, edge1);
/*     */       
/* 364 */       float transStack1 = (float)rangeAxis.valueToJava2D(stack1[0], dataArea, edge1);
/*     */       
/* 366 */       float transStackLeft = (float)rangeAxis.valueToJava2D(adjStackLeft[0], dataArea, edge1);
/*     */ 
/*     */ 
/*     */       
/* 370 */       if (y0 >= 0.0D) {
/* 371 */         if (orientation == PlotOrientation.VERTICAL) {
/* 372 */           left.moveTo(transX1, transStack1);
/* 373 */           left.lineTo(transX1, transY1);
/* 374 */           left.lineTo(transXLeft, transStackLeft);
/*     */         } else {
/*     */           
/* 377 */           left.moveTo(transStack1, transX1);
/* 378 */           left.lineTo(transY1, transX1);
/* 379 */           left.lineTo(transStackLeft, transXLeft);
/*     */         } 
/* 381 */         left.clone();
/*     */       } else {
/*     */         
/* 384 */         double yleft = (y0 + y1) / 2.0D + stackLeft[0];
/* 385 */         float transYLeft = (float)rangeAxis.valueToJava2D(yleft, dataArea, edge1);
/*     */         
/* 387 */         if (orientation == PlotOrientation.VERTICAL) {
/* 388 */           left.moveTo(transX1, transY1);
/* 389 */           left.lineTo(transX1, transStack1);
/* 390 */           left.lineTo(transXLeft, transStackLeft);
/* 391 */           left.lineTo(transXLeft, transYLeft);
/*     */         } else {
/*     */           
/* 394 */           left.moveTo(transY1, transX1);
/* 395 */           left.lineTo(transStack1, transX1);
/* 396 */           left.lineTo(transStackLeft, transXLeft);
/* 397 */           left.lineTo(transYLeft, transXLeft);
/*     */         } 
/* 399 */         left.closePath();
/*     */       } 
/* 401 */       float transStackRight = (float)rangeAxis.valueToJava2D(adjStackRight[0], dataArea, edge1);
/*     */ 
/*     */ 
/*     */       
/* 405 */       if (y2 >= 0.0D) {
/* 406 */         if (orientation == PlotOrientation.VERTICAL) {
/* 407 */           right.moveTo(transX1, transStack1);
/* 408 */           right.lineTo(transX1, transY1);
/* 409 */           right.lineTo(transXRight, transStackRight);
/*     */         } else {
/*     */           
/* 412 */           right.moveTo(transStack1, transX1);
/* 413 */           right.lineTo(transY1, transX1);
/* 414 */           right.lineTo(transStackRight, transXRight);
/*     */         } 
/* 416 */         right.closePath();
/*     */       } else {
/*     */         
/* 419 */         double yright = (y1 + y2) / 2.0D + stackRight[0];
/* 420 */         float transYRight = (float)rangeAxis.valueToJava2D(yright, dataArea, edge1);
/*     */         
/* 422 */         if (orientation == PlotOrientation.VERTICAL) {
/* 423 */           right.moveTo(transX1, transStack1);
/* 424 */           right.lineTo(transX1, transY1);
/* 425 */           right.lineTo(transXRight, transYRight);
/* 426 */           right.lineTo(transXRight, transStackRight);
/*     */         } else {
/*     */           
/* 429 */           right.moveTo(transStack1, transX1);
/* 430 */           right.lineTo(transY1, transX1);
/* 431 */           right.lineTo(transYRight, transXRight);
/* 432 */           right.lineTo(transStackRight, transXRight);
/*     */         } 
/* 434 */         right.closePath();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 439 */     Paint itemPaint = getItemPaint(series, item);
/* 440 */     if (pass == 0) {
/* 441 */       g2.setPaint(itemPaint);
/* 442 */       g2.fill(left);
/* 443 */       g2.fill(right);
/*     */     } 
/*     */ 
/*     */     
/* 447 */     if (entities != null) {
/* 448 */       GeneralPath gp = new GeneralPath(left);
/* 449 */       gp.append(right, false);
/* 450 */       Shape entityArea = gp;
/* 451 */       addEntity(entities, entityArea, dataset, series, item, transX1, transY1);
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
/*     */   private double[] getStackValues(TableXYDataset dataset, int series, int index) {
/* 472 */     double[] result = new double[2];
/* 473 */     for (int i = 0; i < series; i++) {
/* 474 */       double v = dataset.getYValue(i, index);
/* 475 */       if (!Double.isNaN(v)) {
/* 476 */         if (v >= 0.0D) {
/* 477 */           result[1] = result[1] + v;
/*     */         } else {
/*     */           
/* 480 */           result[0] = result[0] + v;
/*     */         } 
/*     */       }
/*     */     } 
/* 484 */     return result;
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
/*     */   private double[] averageStackValues(double[] stack1, double[] stack2) {
/* 497 */     double[] result = new double[2];
/* 498 */     result[0] = (stack1[0] + stack2[0]) / 2.0D;
/* 499 */     result[1] = (stack1[1] + stack2[1]) / 2.0D;
/* 500 */     return result;
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
/*     */   private double[] adjustedStackValues(double[] stack1, double[] stack2) {
/* 514 */     double[] result = new double[2];
/* 515 */     if (stack1[0] == 0.0D || stack2[0] == 0.0D) {
/* 516 */       result[0] = 0.0D;
/*     */     } else {
/*     */       
/* 519 */       result[0] = (stack1[0] + stack2[0]) / 2.0D;
/*     */     } 
/* 521 */     if (stack1[1] == 0.0D || stack2[1] == 0.0D) {
/* 522 */       result[1] = 0.0D;
/*     */     } else {
/*     */       
/* 525 */       result[1] = (stack1[1] + stack2[1]) / 2.0D;
/*     */     } 
/* 527 */     return result;
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
/* 539 */     if (obj == this) {
/* 540 */       return true;
/*     */     }
/* 542 */     if (!(obj instanceof StackedXYAreaRenderer2)) {
/* 543 */       return false;
/*     */     }
/* 545 */     StackedXYAreaRenderer2 that = (StackedXYAreaRenderer2)obj;
/* 546 */     if (this.roundXCoordinates != that.roundXCoordinates) {
/* 547 */       return false;
/*     */     }
/* 549 */     return super.equals(obj);
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
/* 561 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/StackedXYAreaRenderer2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */