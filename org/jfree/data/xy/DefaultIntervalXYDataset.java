/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.jfree.data.general.Dataset;
/*     */ import org.jfree.data.general.DatasetChangeEvent;
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
/*     */ public class DefaultIntervalXYDataset
/*     */   extends AbstractIntervalXYDataset
/*     */   implements PublicCloneable
/*     */ {
/*  86 */   private List seriesKeys = new ArrayList();
/*  87 */   private List seriesList = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeriesCount() {
/*  97 */     return this.seriesList.size();
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
/*     */   public Comparable getSeriesKey(int series) {
/* 113 */     if (series < 0 || series >= getSeriesCount()) {
/* 114 */       throw new IllegalArgumentException("Series index out of bounds");
/*     */     }
/* 116 */     return this.seriesKeys.get(series);
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
/*     */   public int getItemCount(int series) {
/* 132 */     if (series < 0 || series >= getSeriesCount()) {
/* 133 */       throw new IllegalArgumentException("Series index out of bounds");
/*     */     }
/* 135 */     double[][] seriesArray = this.seriesList.get(series);
/* 136 */     return (seriesArray[0]).length;
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
/*     */   public double getXValue(int series, int item) {
/* 158 */     double[][] seriesData = this.seriesList.get(series);
/* 159 */     return seriesData[0][item];
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
/*     */   public double getYValue(int series, int item) {
/* 181 */     double[][] seriesData = this.seriesList.get(series);
/* 182 */     return seriesData[3][item];
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
/*     */   public double getStartXValue(int series, int item) {
/* 204 */     double[][] seriesData = this.seriesList.get(series);
/* 205 */     return seriesData[1][item];
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
/*     */   public double getEndXValue(int series, int item) {
/* 227 */     double[][] seriesData = this.seriesList.get(series);
/* 228 */     return seriesData[2][item];
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
/*     */   public double getStartYValue(int series, int item) {
/* 250 */     double[][] seriesData = this.seriesList.get(series);
/* 251 */     return seriesData[4][item];
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
/*     */   public double getEndYValue(int series, int item) {
/* 273 */     double[][] seriesData = this.seriesList.get(series);
/* 274 */     return seriesData[5][item];
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
/*     */   public Number getEndX(int series, int item) {
/* 296 */     return new Double(getEndXValue(series, item));
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
/*     */   public Number getEndY(int series, int item) {
/* 318 */     return new Double(getEndYValue(series, item));
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
/*     */   public Number getStartX(int series, int item) {
/* 340 */     return new Double(getStartXValue(series, item));
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
/*     */   public Number getStartY(int series, int item) {
/* 362 */     return new Double(getStartYValue(series, item));
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
/*     */   public Number getX(int series, int item) {
/* 384 */     return new Double(getXValue(series, item));
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
/*     */   public Number getY(int series, int item) {
/* 406 */     return new Double(getYValue(series, item));
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
/*     */   public void addSeries(Comparable seriesKey, double[][] data) {
/* 421 */     if (seriesKey == null) {
/* 422 */       throw new IllegalArgumentException("The 'seriesKey' cannot be null.");
/*     */     }
/*     */     
/* 425 */     if (data == null) {
/* 426 */       throw new IllegalArgumentException("The 'data' is null.");
/*     */     }
/* 428 */     if (data.length != 6) {
/* 429 */       throw new IllegalArgumentException("The 'data' array must have length == 6.");
/*     */     }
/*     */     
/* 432 */     int length = (data[0]).length;
/* 433 */     if (length != (data[1]).length || length != (data[2]).length || length != (data[3]).length || length != (data[4]).length || length != (data[5]).length)
/*     */     {
/*     */       
/* 436 */       throw new IllegalArgumentException("The 'data' array must contain six arrays with equal length.");
/*     */     }
/*     */     
/* 439 */     int seriesIndex = indexOf(seriesKey);
/* 440 */     if (seriesIndex == -1) {
/* 441 */       this.seriesKeys.add(seriesKey);
/* 442 */       this.seriesList.add(data);
/*     */     } else {
/*     */       
/* 445 */       this.seriesList.remove(seriesIndex);
/* 446 */       this.seriesList.add(seriesIndex, data);
/*     */     } 
/* 448 */     notifyListeners(new DatasetChangeEvent(this, (Dataset)this));
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
/* 469 */     if (obj == this) {
/* 470 */       return true;
/*     */     }
/* 472 */     if (!(obj instanceof DefaultIntervalXYDataset)) {
/* 473 */       return false;
/*     */     }
/* 475 */     DefaultIntervalXYDataset that = (DefaultIntervalXYDataset)obj;
/* 476 */     if (!this.seriesKeys.equals(that.seriesKeys)) {
/* 477 */       return false;
/*     */     }
/* 479 */     for (int i = 0; i < this.seriesList.size(); i++) {
/* 480 */       double[][] d1 = this.seriesList.get(i);
/* 481 */       double[][] d2 = that.seriesList.get(i);
/* 482 */       double[] d1x = d1[0];
/* 483 */       double[] d2x = d2[0];
/* 484 */       if (!Arrays.equals(d1x, d2x)) {
/* 485 */         return false;
/*     */       }
/* 487 */       double[] d1xs = d1[1];
/* 488 */       double[] d2xs = d2[1];
/* 489 */       if (!Arrays.equals(d1xs, d2xs)) {
/* 490 */         return false;
/*     */       }
/* 492 */       double[] d1xe = d1[2];
/* 493 */       double[] d2xe = d2[2];
/* 494 */       if (!Arrays.equals(d1xe, d2xe)) {
/* 495 */         return false;
/*     */       }
/* 497 */       double[] d1y = d1[3];
/* 498 */       double[] d2y = d2[3];
/* 499 */       if (!Arrays.equals(d1y, d2y)) {
/* 500 */         return false;
/*     */       }
/* 502 */       double[] d1ys = d1[4];
/* 503 */       double[] d2ys = d2[4];
/* 504 */       if (!Arrays.equals(d1ys, d2ys)) {
/* 505 */         return false;
/*     */       }
/* 507 */       double[] d1ye = d1[5];
/* 508 */       double[] d2ye = d2[5];
/* 509 */       if (!Arrays.equals(d1ye, d2ye)) {
/* 510 */         return false;
/*     */       }
/*     */     } 
/* 513 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 524 */     int result = this.seriesKeys.hashCode();
/* 525 */     result = 29 * result + this.seriesList.hashCode();
/* 526 */     return result;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 540 */     DefaultIntervalXYDataset clone = (DefaultIntervalXYDataset)super.clone();
/* 541 */     clone.seriesKeys = new ArrayList(this.seriesKeys);
/* 542 */     clone.seriesList = new ArrayList(this.seriesList.size());
/* 543 */     for (int i = 0; i < this.seriesList.size(); i++) {
/* 544 */       double[][] data = this.seriesList.get(i);
/* 545 */       double[] x = data[0];
/* 546 */       double[] xStart = data[1];
/* 547 */       double[] xEnd = data[2];
/* 548 */       double[] y = data[3];
/* 549 */       double[] yStart = data[4];
/* 550 */       double[] yEnd = data[5];
/* 551 */       double[] xx = new double[x.length];
/* 552 */       double[] xxStart = new double[xStart.length];
/* 553 */       double[] xxEnd = new double[xEnd.length];
/* 554 */       double[] yy = new double[y.length];
/* 555 */       double[] yyStart = new double[yStart.length];
/* 556 */       double[] yyEnd = new double[yEnd.length];
/* 557 */       System.arraycopy(x, 0, xx, 0, x.length);
/* 558 */       System.arraycopy(xStart, 0, xxStart, 0, xStart.length);
/* 559 */       System.arraycopy(xEnd, 0, xxEnd, 0, xEnd.length);
/* 560 */       System.arraycopy(y, 0, yy, 0, y.length);
/* 561 */       System.arraycopy(yStart, 0, yyStart, 0, yStart.length);
/* 562 */       System.arraycopy(yEnd, 0, yyEnd, 0, yEnd.length);
/* 563 */       clone.seriesList.add(i, new double[][] { xx, xxStart, xxEnd, yy, yyStart, yyEnd });
/*     */     } 
/*     */     
/* 566 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/DefaultIntervalXYDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */