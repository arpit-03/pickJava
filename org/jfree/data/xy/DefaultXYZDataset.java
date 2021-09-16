/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.jfree.data.DomainOrder;
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
/*     */ public class DefaultXYZDataset
/*     */   extends AbstractXYZDataset
/*     */   implements XYZDataset, PublicCloneable
/*     */ {
/*  82 */   private List seriesKeys = new ArrayList();
/*  83 */   private List seriesList = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeriesCount() {
/*  93 */     return this.seriesList.size();
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
/* 109 */     if (series < 0 || series >= getSeriesCount()) {
/* 110 */       throw new IllegalArgumentException("Series index out of bounds");
/*     */     }
/* 112 */     return this.seriesKeys.get(series);
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
/*     */   public int indexOf(Comparable seriesKey) {
/* 125 */     return this.seriesKeys.indexOf(seriesKey);
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
/*     */   public DomainOrder getDomainOrder() {
/* 137 */     return DomainOrder.NONE;
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
/* 153 */     if (series < 0 || series >= getSeriesCount()) {
/* 154 */       throw new IllegalArgumentException("Series index out of bounds");
/*     */     }
/* 156 */     double[][] seriesArray = this.seriesList.get(series);
/* 157 */     return (seriesArray[0]).length;
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
/* 179 */     double[][] seriesData = this.seriesList.get(series);
/* 180 */     return seriesData[0][item];
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
/* 202 */     return new Double(getXValue(series, item));
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
/* 224 */     double[][] seriesData = this.seriesList.get(series);
/* 225 */     return seriesData[1][item];
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
/* 247 */     return new Double(getYValue(series, item));
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
/*     */   public double getZValue(int series, int item) {
/* 269 */     double[][] seriesData = this.seriesList.get(series);
/* 270 */     return seriesData[2][item];
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
/*     */   public Number getZ(int series, int item) {
/* 292 */     return new Double(getZValue(series, item));
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
/* 307 */     if (seriesKey == null) {
/* 308 */       throw new IllegalArgumentException("The 'seriesKey' cannot be null.");
/*     */     }
/*     */     
/* 311 */     if (data == null) {
/* 312 */       throw new IllegalArgumentException("The 'data' is null.");
/*     */     }
/* 314 */     if (data.length != 3) {
/* 315 */       throw new IllegalArgumentException("The 'data' array must have length == 3.");
/*     */     }
/*     */     
/* 318 */     if ((data[0]).length != (data[1]).length || (data[0]).length != (data[2]).length)
/*     */     {
/* 320 */       throw new IllegalArgumentException("The 'data' array must contain three arrays all having the same length.");
/*     */     }
/*     */     
/* 323 */     int seriesIndex = indexOf(seriesKey);
/* 324 */     if (seriesIndex == -1) {
/* 325 */       this.seriesKeys.add(seriesKey);
/* 326 */       this.seriesList.add(data);
/*     */     } else {
/*     */       
/* 329 */       this.seriesList.remove(seriesIndex);
/* 330 */       this.seriesList.add(seriesIndex, data);
/*     */     } 
/* 332 */     notifyListeners(new DatasetChangeEvent(this, (Dataset)this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSeries(Comparable seriesKey) {
/* 343 */     int seriesIndex = indexOf(seriesKey);
/* 344 */     if (seriesIndex >= 0) {
/* 345 */       this.seriesKeys.remove(seriesIndex);
/* 346 */       this.seriesList.remove(seriesIndex);
/* 347 */       notifyListeners(new DatasetChangeEvent(this, (Dataset)this));
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
/*     */   public boolean equals(Object obj) {
/* 368 */     if (obj == this) {
/* 369 */       return true;
/*     */     }
/* 371 */     if (!(obj instanceof DefaultXYZDataset)) {
/* 372 */       return false;
/*     */     }
/* 374 */     DefaultXYZDataset that = (DefaultXYZDataset)obj;
/* 375 */     if (!this.seriesKeys.equals(that.seriesKeys)) {
/* 376 */       return false;
/*     */     }
/* 378 */     for (int i = 0; i < this.seriesList.size(); i++) {
/* 379 */       double[][] d1 = this.seriesList.get(i);
/* 380 */       double[][] d2 = that.seriesList.get(i);
/* 381 */       double[] d1x = d1[0];
/* 382 */       double[] d2x = d2[0];
/* 383 */       if (!Arrays.equals(d1x, d2x)) {
/* 384 */         return false;
/*     */       }
/* 386 */       double[] d1y = d1[1];
/* 387 */       double[] d2y = d2[1];
/* 388 */       if (!Arrays.equals(d1y, d2y)) {
/* 389 */         return false;
/*     */       }
/* 391 */       double[] d1z = d1[2];
/* 392 */       double[] d2z = d2[2];
/* 393 */       if (!Arrays.equals(d1z, d2z)) {
/* 394 */         return false;
/*     */       }
/*     */     } 
/* 397 */     return true;
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
/* 408 */     int result = this.seriesKeys.hashCode();
/* 409 */     result = 29 * result + this.seriesList.hashCode();
/* 410 */     return result;
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
/* 424 */     DefaultXYZDataset clone = (DefaultXYZDataset)super.clone();
/* 425 */     clone.seriesKeys = new ArrayList(this.seriesKeys);
/* 426 */     clone.seriesList = new ArrayList(this.seriesList.size());
/* 427 */     for (int i = 0; i < this.seriesList.size(); i++) {
/* 428 */       double[][] data = this.seriesList.get(i);
/* 429 */       double[] x = data[0];
/* 430 */       double[] y = data[1];
/* 431 */       double[] z = data[2];
/* 432 */       double[] xx = new double[x.length];
/* 433 */       double[] yy = new double[y.length];
/* 434 */       double[] zz = new double[z.length];
/* 435 */       System.arraycopy(x, 0, xx, 0, x.length);
/* 436 */       System.arraycopy(y, 0, yy, 0, y.length);
/* 437 */       System.arraycopy(z, 0, zz, 0, z.length);
/* 438 */       clone.seriesList.add(i, new double[][] { xx, yy, zz });
/*     */     } 
/* 440 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/DefaultXYZDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */