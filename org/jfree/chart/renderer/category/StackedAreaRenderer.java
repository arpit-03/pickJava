/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.data.DataUtilities;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.Values2D;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.general.DatasetUtilities;
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
/*     */ public class StackedAreaRenderer
/*     */   extends AreaRenderer
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3595635038460823663L;
/*     */   private boolean renderAsPercentages;
/*     */   
/*     */   public StackedAreaRenderer() {
/* 112 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackedAreaRenderer(boolean renderAsPercentages) {
/* 123 */     this.renderAsPercentages = renderAsPercentages;
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
/*     */   public boolean getRenderAsPercentages() {
/* 136 */     return this.renderAsPercentages;
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
/*     */   public void setRenderAsPercentages(boolean asPercentages) {
/* 149 */     this.renderAsPercentages = asPercentages;
/* 150 */     fireChangeEvent();
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
/* 162 */     return 2;
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
/*     */   public Range findRangeBounds(CategoryDataset dataset) {
/* 175 */     if (dataset == null) {
/* 176 */       return null;
/*     */     }
/* 178 */     if (this.renderAsPercentages) {
/* 179 */       return new Range(0.0D, 1.0D);
/*     */     }
/*     */     
/* 182 */     return DatasetUtilities.findStackedRangeBounds(dataset);
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
/*     */   public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {
/*     */     float transY1;
/* 206 */     if (!isSeriesVisible(row)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 212 */     EntityCollection entities = state.getEntityCollection();
/*     */     
/* 214 */     double y1 = 0.0D;
/* 215 */     Number n = dataset.getValue(row, column);
/* 216 */     if (n != null) {
/* 217 */       y1 = n.doubleValue();
/* 218 */       if (this.renderAsPercentages) {
/* 219 */         double total = DataUtilities.calculateColumnTotal((Values2D)dataset, column, state
/* 220 */             .getVisibleSeriesArray());
/* 221 */         y1 /= total;
/*     */       } 
/*     */     } 
/* 224 */     double[] stack1 = getStackValues(dataset, row, column, state
/* 225 */         .getVisibleSeriesArray());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     double xx1 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot
/* 232 */         .getDomainAxisEdge());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     double y0 = 0.0D;
/* 238 */     n = dataset.getValue(row, Math.max(column - 1, 0));
/* 239 */     if (n != null) {
/* 240 */       y0 = n.doubleValue();
/* 241 */       if (this.renderAsPercentages) {
/* 242 */         double total = DataUtilities.calculateColumnTotal((Values2D)dataset, 
/* 243 */             Math.max(column - 1, 0), state.getVisibleSeriesArray());
/* 244 */         y0 /= total;
/*     */       } 
/*     */     } 
/* 247 */     double[] stack0 = getStackValues(dataset, row, Math.max(column - 1, 0), state
/* 248 */         .getVisibleSeriesArray());
/*     */ 
/*     */     
/* 251 */     double xx0 = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, plot
/* 252 */         .getDomainAxisEdge());
/*     */     
/* 254 */     int itemCount = dataset.getColumnCount();
/* 255 */     double y2 = 0.0D;
/* 256 */     n = dataset.getValue(row, Math.min(column + 1, itemCount - 1));
/* 257 */     if (n != null) {
/* 258 */       y2 = n.doubleValue();
/* 259 */       if (this.renderAsPercentages) {
/* 260 */         double total = DataUtilities.calculateColumnTotal((Values2D)dataset, 
/* 261 */             Math.min(column + 1, itemCount - 1), state
/* 262 */             .getVisibleSeriesArray());
/* 263 */         y2 /= total;
/*     */       } 
/*     */     } 
/* 266 */     double[] stack2 = getStackValues(dataset, row, Math.min(column + 1, itemCount - 1), state
/* 267 */         .getVisibleSeriesArray());
/*     */     
/* 269 */     double xx2 = domainAxis.getCategoryEnd(column, getColumnCount(), dataArea, plot
/* 270 */         .getDomainAxisEdge());
/*     */ 
/*     */     
/* 273 */     double xxLeft = xx0;
/* 274 */     double xxRight = xx2;
/*     */     
/* 276 */     double[] stackLeft = averageStackValues(stack0, stack1);
/* 277 */     double[] stackRight = averageStackValues(stack1, stack2);
/* 278 */     double[] adjStackLeft = adjustedStackValues(stack0, stack1);
/* 279 */     double[] adjStackRight = adjustedStackValues(stack1, stack2);
/*     */ 
/*     */ 
/*     */     
/* 283 */     RectangleEdge edge1 = plot.getRangeAxisEdge();
/*     */     
/* 285 */     GeneralPath left = new GeneralPath();
/* 286 */     GeneralPath right = new GeneralPath();
/* 287 */     if (y1 >= 0.0D) {
/* 288 */       transY1 = (float)rangeAxis.valueToJava2D(y1 + stack1[1], dataArea, edge1);
/*     */       
/* 290 */       float transStack1 = (float)rangeAxis.valueToJava2D(stack1[1], dataArea, edge1);
/*     */       
/* 292 */       float transStackLeft = (float)rangeAxis.valueToJava2D(adjStackLeft[1], dataArea, edge1);
/*     */ 
/*     */ 
/*     */       
/* 296 */       if (y0 >= 0.0D) {
/* 297 */         double yleft = (y0 + y1) / 2.0D + stackLeft[1];
/*     */         
/* 299 */         float transYLeft = (float)rangeAxis.valueToJava2D(yleft, dataArea, edge1);
/* 300 */         left.moveTo((float)xx1, transY1);
/* 301 */         left.lineTo((float)xx1, transStack1);
/* 302 */         left.lineTo((float)xxLeft, transStackLeft);
/* 303 */         left.lineTo((float)xxLeft, transYLeft);
/* 304 */         left.closePath();
/*     */       } else {
/*     */         
/* 307 */         left.moveTo((float)xx1, transStack1);
/* 308 */         left.lineTo((float)xx1, transY1);
/* 309 */         left.lineTo((float)xxLeft, transStackLeft);
/* 310 */         left.closePath();
/*     */       } 
/*     */       
/* 313 */       float transStackRight = (float)rangeAxis.valueToJava2D(adjStackRight[1], dataArea, edge1);
/*     */ 
/*     */       
/* 316 */       if (y2 >= 0.0D) {
/* 317 */         double yright = (y1 + y2) / 2.0D + stackRight[1];
/*     */         
/* 319 */         float transYRight = (float)rangeAxis.valueToJava2D(yright, dataArea, edge1);
/* 320 */         right.moveTo((float)xx1, transStack1);
/* 321 */         right.lineTo((float)xx1, transY1);
/* 322 */         right.lineTo((float)xxRight, transYRight);
/* 323 */         right.lineTo((float)xxRight, transStackRight);
/* 324 */         right.closePath();
/*     */       } else {
/*     */         
/* 327 */         right.moveTo((float)xx1, transStack1);
/* 328 */         right.lineTo((float)xx1, transY1);
/* 329 */         right.lineTo((float)xxRight, transStackRight);
/* 330 */         right.closePath();
/*     */       } 
/*     */     } else {
/*     */       
/* 334 */       transY1 = (float)rangeAxis.valueToJava2D(y1 + stack1[0], dataArea, edge1);
/*     */       
/* 336 */       float transStack1 = (float)rangeAxis.valueToJava2D(stack1[0], dataArea, edge1);
/*     */       
/* 338 */       float transStackLeft = (float)rangeAxis.valueToJava2D(adjStackLeft[0], dataArea, edge1);
/*     */ 
/*     */ 
/*     */       
/* 342 */       if (y0 >= 0.0D) {
/* 343 */         left.moveTo((float)xx1, transStack1);
/* 344 */         left.lineTo((float)xx1, transY1);
/* 345 */         left.lineTo((float)xxLeft, transStackLeft);
/* 346 */         left.clone();
/*     */       } else {
/*     */         
/* 349 */         double yleft = (y0 + y1) / 2.0D + stackLeft[0];
/* 350 */         float transYLeft = (float)rangeAxis.valueToJava2D(yleft, dataArea, edge1);
/*     */         
/* 352 */         left.moveTo((float)xx1, transY1);
/* 353 */         left.lineTo((float)xx1, transStack1);
/* 354 */         left.lineTo((float)xxLeft, transStackLeft);
/* 355 */         left.lineTo((float)xxLeft, transYLeft);
/* 356 */         left.closePath();
/*     */       } 
/* 358 */       float transStackRight = (float)rangeAxis.valueToJava2D(adjStackRight[0], dataArea, edge1);
/*     */ 
/*     */ 
/*     */       
/* 362 */       if (y2 >= 0.0D) {
/* 363 */         right.moveTo((float)xx1, transStack1);
/* 364 */         right.lineTo((float)xx1, transY1);
/* 365 */         right.lineTo((float)xxRight, transStackRight);
/* 366 */         right.closePath();
/*     */       } else {
/*     */         
/* 369 */         double yright = (y1 + y2) / 2.0D + stackRight[0];
/* 370 */         float transYRight = (float)rangeAxis.valueToJava2D(yright, dataArea, edge1);
/*     */         
/* 372 */         right.moveTo((float)xx1, transStack1);
/* 373 */         right.lineTo((float)xx1, transY1);
/* 374 */         right.lineTo((float)xxRight, transYRight);
/* 375 */         right.lineTo((float)xxRight, transStackRight);
/* 376 */         right.closePath();
/*     */       } 
/*     */     } 
/*     */     
/* 380 */     if (pass == 0) {
/* 381 */       Paint itemPaint = getItemPaint(row, column);
/* 382 */       g2.setPaint(itemPaint);
/* 383 */       g2.fill(left);
/* 384 */       g2.fill(right);
/*     */ 
/*     */       
/* 387 */       if (entities != null) {
/* 388 */         GeneralPath gp = new GeneralPath(left);
/* 389 */         gp.append(right, false);
/* 390 */         Shape entityArea = gp;
/* 391 */         addItemEntity(entities, dataset, row, column, entityArea);
/*     */       }
/*     */     
/* 394 */     } else if (pass == 1) {
/* 395 */       drawItemLabel(g2, plot.getOrientation(), dataset, row, column, xx1, transY1, (y1 < 0.0D));
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
/*     */   protected double[] getStackValues(CategoryDataset dataset, int series, int index, int[] validRows) {
/* 417 */     double[] result = new double[2];
/* 418 */     double total = 0.0D;
/* 419 */     if (this.renderAsPercentages) {
/* 420 */       total = DataUtilities.calculateColumnTotal((Values2D)dataset, index, validRows);
/*     */     }
/*     */     
/* 423 */     for (int i = 0; i < series; i++) {
/* 424 */       if (isSeriesVisible(i)) {
/* 425 */         double v = 0.0D;
/* 426 */         Number n = dataset.getValue(i, index);
/* 427 */         if (n != null) {
/* 428 */           v = n.doubleValue();
/* 429 */           if (this.renderAsPercentages) {
/* 430 */             v /= total;
/*     */           }
/*     */         } 
/* 433 */         if (!Double.isNaN(v)) {
/* 434 */           if (v >= 0.0D) {
/* 435 */             result[1] = result[1] + v;
/*     */           } else {
/*     */             
/* 438 */             result[0] = result[0] + v;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 443 */     return result;
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
/* 456 */     double[] result = new double[2];
/* 457 */     result[0] = (stack1[0] + stack2[0]) / 2.0D;
/* 458 */     result[1] = (stack1[1] + stack2[1]) / 2.0D;
/* 459 */     return result;
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
/* 473 */     double[] result = new double[2];
/* 474 */     if (stack1[0] == 0.0D || stack2[0] == 0.0D) {
/* 475 */       result[0] = 0.0D;
/*     */     } else {
/*     */       
/* 478 */       result[0] = (stack1[0] + stack2[0]) / 2.0D;
/*     */     } 
/* 480 */     if (stack1[1] == 0.0D || stack2[1] == 0.0D) {
/* 481 */       result[1] = 0.0D;
/*     */     } else {
/*     */       
/* 484 */       result[1] = (stack1[1] + stack2[1]) / 2.0D;
/*     */     } 
/* 486 */     return result;
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
/* 498 */     if (obj == this) {
/* 499 */       return true;
/*     */     }
/* 501 */     if (!(obj instanceof StackedAreaRenderer)) {
/* 502 */       return false;
/*     */     }
/* 504 */     StackedAreaRenderer that = (StackedAreaRenderer)obj;
/* 505 */     if (this.renderAsPercentages != that.renderAsPercentages) {
/* 506 */       return false;
/*     */     }
/* 508 */     return super.equals(obj);
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
/*     */   protected double getPreviousHeight(CategoryDataset dataset, int series, int category) {
/* 529 */     double result = 0.0D;
/*     */     
/* 531 */     double total = 0.0D;
/* 532 */     if (this.renderAsPercentages) {
/* 533 */       total = DataUtilities.calculateColumnTotal((Values2D)dataset, category);
/*     */     }
/* 535 */     for (int i = 0; i < series; i++) {
/* 536 */       Number n = dataset.getValue(i, category);
/* 537 */       if (n != null) {
/* 538 */         double v = n.doubleValue();
/* 539 */         if (this.renderAsPercentages) {
/* 540 */           v /= total;
/*     */         }
/* 542 */         result += v;
/*     */       } 
/*     */     } 
/* 545 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/StackedAreaRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */