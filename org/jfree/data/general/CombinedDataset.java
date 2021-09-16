/*     */ package org.jfree.data.general;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jfree.data.xy.AbstractIntervalXYDataset;
/*     */ import org.jfree.data.xy.IntervalXYDataset;
/*     */ import org.jfree.data.xy.OHLCDataset;
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
/*     */ public class CombinedDataset
/*     */   extends AbstractIntervalXYDataset
/*     */   implements XYDataset, OHLCDataset, IntervalXYDataset, CombinationDataset
/*     */ {
/*  80 */   private List datasetInfo = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CombinedDataset() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CombinedDataset(SeriesDataset[] data) {
/*  96 */     add(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(SeriesDataset data) {
/* 106 */     fastAdd(data);
/* 107 */     DatasetChangeEvent event = new DatasetChangeEvent(this, (Dataset)this);
/* 108 */     notifyListeners(event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(SeriesDataset[] data) {
/* 119 */     for (int i = 0; i < data.length; i++) {
/* 120 */       fastAdd(data[i]);
/*     */     }
/* 122 */     DatasetChangeEvent event = new DatasetChangeEvent(this, (Dataset)this);
/* 123 */     notifyListeners(event);
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
/*     */   public void add(SeriesDataset data, int series) {
/* 135 */     add((SeriesDataset)new SubSeriesDataset(data, series));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fastAdd(SeriesDataset data) {
/* 144 */     for (int i = 0; i < data.getSeriesCount(); i++) {
/* 145 */       this.datasetInfo.add(new DatasetInfo(data, i));
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
/*     */   public int getSeriesCount() {
/* 160 */     return this.datasetInfo.size();
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
/*     */   public Comparable getSeriesKey(int series) {
/* 172 */     DatasetInfo di = getDatasetInfo(series);
/* 173 */     return di.data.getSeriesKey(di.series);
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
/*     */   public Number getX(int series, int item) {
/* 193 */     DatasetInfo di = getDatasetInfo(series);
/* 194 */     return ((XYDataset)di.data).getX(di.series, item);
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
/*     */   public Number getY(int series, int item) {
/* 210 */     DatasetInfo di = getDatasetInfo(series);
/* 211 */     return ((XYDataset)di.data).getY(di.series, item);
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
/*     */   public int getItemCount(int series) {
/* 226 */     DatasetInfo di = getDatasetInfo(series);
/* 227 */     return ((XYDataset)di.data).getItemCount(di.series);
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
/*     */   public Number getHigh(int series, int item) {
/* 247 */     DatasetInfo di = getDatasetInfo(series);
/* 248 */     return ((OHLCDataset)di.data).getHigh(di.series, item);
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
/*     */   public double getHighValue(int series, int item) {
/* 262 */     double result = Double.NaN;
/* 263 */     Number high = getHigh(series, item);
/* 264 */     if (high != null) {
/* 265 */       result = high.doubleValue();
/*     */     }
/* 267 */     return result;
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
/*     */   public Number getLow(int series, int item) {
/* 283 */     DatasetInfo di = getDatasetInfo(series);
/* 284 */     return ((OHLCDataset)di.data).getLow(di.series, item);
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
/*     */   public double getLowValue(int series, int item) {
/* 298 */     double result = Double.NaN;
/* 299 */     Number low = getLow(series, item);
/* 300 */     if (low != null) {
/* 301 */       result = low.doubleValue();
/*     */     }
/* 303 */     return result;
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
/*     */   public Number getOpen(int series, int item) {
/* 319 */     DatasetInfo di = getDatasetInfo(series);
/* 320 */     return ((OHLCDataset)di.data).getOpen(di.series, item);
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
/*     */   public double getOpenValue(int series, int item) {
/* 334 */     double result = Double.NaN;
/* 335 */     Number open = getOpen(series, item);
/* 336 */     if (open != null) {
/* 337 */       result = open.doubleValue();
/*     */     }
/* 339 */     return result;
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
/*     */   public Number getClose(int series, int item) {
/* 355 */     DatasetInfo di = getDatasetInfo(series);
/* 356 */     return ((OHLCDataset)di.data).getClose(di.series, item);
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
/*     */   public double getCloseValue(int series, int item) {
/* 370 */     double result = Double.NaN;
/* 371 */     Number close = getClose(series, item);
/* 372 */     if (close != null) {
/* 373 */       result = close.doubleValue();
/*     */     }
/* 375 */     return result;
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
/*     */   public Number getVolume(int series, int item) {
/* 391 */     DatasetInfo di = getDatasetInfo(series);
/* 392 */     return ((OHLCDataset)di.data).getVolume(di.series, item);
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
/*     */   public double getVolumeValue(int series, int item) {
/* 406 */     double result = Double.NaN;
/* 407 */     Number volume = getVolume(series, item);
/* 408 */     if (volume != null) {
/* 409 */       result = volume.doubleValue();
/*     */     }
/* 411 */     return result;
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
/*     */   public Number getStartX(int series, int item) {
/* 428 */     DatasetInfo di = getDatasetInfo(series);
/* 429 */     if (di.data instanceof IntervalXYDataset) {
/* 430 */       return ((IntervalXYDataset)di.data).getStartX(di.series, item);
/*     */     }
/*     */     
/* 433 */     return getX(series, item);
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
/*     */   public Number getEndX(int series, int item) {
/* 447 */     DatasetInfo di = getDatasetInfo(series);
/* 448 */     if (di.data instanceof IntervalXYDataset) {
/* 449 */       return ((IntervalXYDataset)di.data).getEndX(di.series, item);
/*     */     }
/*     */     
/* 452 */     return getX(series, item);
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
/*     */   public Number getStartY(int series, int item) {
/* 466 */     DatasetInfo di = getDatasetInfo(series);
/* 467 */     if (di.data instanceof IntervalXYDataset) {
/* 468 */       return ((IntervalXYDataset)di.data).getStartY(di.series, item);
/*     */     }
/*     */     
/* 471 */     return getY(series, item);
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
/*     */   public Number getEndY(int series, int item) {
/* 485 */     DatasetInfo di = getDatasetInfo(series);
/* 486 */     if (di.data instanceof IntervalXYDataset) {
/* 487 */       return ((IntervalXYDataset)di.data).getEndY(di.series, item);
/*     */     }
/*     */     
/* 490 */     return getY(series, item);
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
/*     */   public SeriesDataset getParent() {
/* 508 */     SeriesDataset parent = null;
/* 509 */     for (int i = 0; i < this.datasetInfo.size(); i++) {
/* 510 */       SeriesDataset child = (getDatasetInfo(i)).data;
/* 511 */       if (child instanceof CombinationDataset) {
/*     */         
/* 513 */         SeriesDataset childParent = ((CombinationDataset)child).getParent();
/* 514 */         if (parent == null) {
/* 515 */           parent = childParent;
/*     */         }
/* 517 */         else if (parent != childParent) {
/* 518 */           return null;
/*     */         } 
/*     */       } else {
/*     */         
/* 522 */         return null;
/*     */       } 
/*     */     } 
/* 525 */     return parent;
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
/*     */   public int[] getMap() {
/* 542 */     int[] map = null;
/* 543 */     for (int i = 0; i < this.datasetInfo.size(); i++) {
/* 544 */       SeriesDataset child = (getDatasetInfo(i)).data;
/* 545 */       if (child instanceof CombinationDataset) {
/* 546 */         int[] childMap = ((CombinationDataset)child).getMap();
/* 547 */         if (childMap == null) {
/* 548 */           return null;
/*     */         }
/* 550 */         map = joinMap(map, childMap);
/*     */       } else {
/*     */         
/* 553 */         return null;
/*     */       } 
/*     */     } 
/* 556 */     return map;
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
/*     */   public int getChildPosition(Dataset child) {
/* 572 */     int n = 0;
/* 573 */     for (int i = 0; i < this.datasetInfo.size(); i++) {
/* 574 */       SeriesDataset childDataset = (getDatasetInfo(i)).data;
/* 575 */       if (childDataset instanceof CombinedDataset) {
/*     */         
/* 577 */         int m = ((CombinedDataset)childDataset).getChildPosition(child);
/* 578 */         if (m >= 0) {
/* 579 */           return n + m;
/*     */         }
/* 581 */         n++;
/*     */       } else {
/*     */         
/* 584 */         if (child == childDataset) {
/* 585 */           return n;
/*     */         }
/* 587 */         n++;
/*     */       } 
/*     */     } 
/* 590 */     return -1;
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
/*     */   private DatasetInfo getDatasetInfo(int series) {
/* 605 */     return this.datasetInfo.get(series);
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
/*     */   private int[] joinMap(int[] a, int[] b) {
/* 617 */     if (a == null) {
/* 618 */       return b;
/*     */     }
/* 620 */     if (b == null) {
/* 621 */       return a;
/*     */     }
/* 623 */     int[] result = new int[a.length + b.length];
/* 624 */     System.arraycopy(a, 0, result, 0, a.length);
/* 625 */     System.arraycopy(b, 0, result, a.length, b.length);
/* 626 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class DatasetInfo
/*     */   {
/*     */     private SeriesDataset data;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int series;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DatasetInfo(SeriesDataset data, int series) {
/* 648 */       this.data = data;
/* 649 */       this.series = series;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/general/CombinedDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */