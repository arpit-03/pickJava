/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
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
/*     */ public class DefaultOHLCDataset
/*     */   extends AbstractXYDataset
/*     */   implements OHLCDataset, PublicCloneable
/*     */ {
/*     */   private Comparable key;
/*     */   private OHLCDataItem[] data;
/*     */   
/*     */   public DefaultOHLCDataset(Comparable key, OHLCDataItem[] data) {
/*  73 */     this.key = key;
/*  74 */     this.data = data;
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
/*  86 */     return this.key;
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
/*     */   public Number getX(int series, int item) {
/*  99 */     return new Long(this.data[item].getDate().getTime());
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
/*     */   public Date getXDate(int series, int item) {
/* 111 */     return this.data[item].getDate();
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
/*     */   public Number getY(int series, int item) {
/* 124 */     return getClose(series, item);
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
/*     */   public Number getHigh(int series, int item) {
/* 137 */     return this.data[item].getHigh();
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
/* 151 */     double result = Double.NaN;
/* 152 */     Number high = getHigh(series, item);
/* 153 */     if (high != null) {
/* 154 */       result = high.doubleValue();
/*     */     }
/* 156 */     return result;
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
/*     */   public Number getLow(int series, int item) {
/* 169 */     return this.data[item].getLow();
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
/* 183 */     double result = Double.NaN;
/* 184 */     Number low = getLow(series, item);
/* 185 */     if (low != null) {
/* 186 */       result = low.doubleValue();
/*     */     }
/* 188 */     return result;
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
/*     */   public Number getOpen(int series, int item) {
/* 201 */     return this.data[item].getOpen();
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
/* 215 */     double result = Double.NaN;
/* 216 */     Number open = getOpen(series, item);
/* 217 */     if (open != null) {
/* 218 */       result = open.doubleValue();
/*     */     }
/* 220 */     return result;
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
/*     */   public Number getClose(int series, int item) {
/* 233 */     return this.data[item].getClose();
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
/* 247 */     double result = Double.NaN;
/* 248 */     Number close = getClose(series, item);
/* 249 */     if (close != null) {
/* 250 */       result = close.doubleValue();
/*     */     }
/* 252 */     return result;
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
/*     */   public Number getVolume(int series, int item) {
/* 265 */     return this.data[item].getVolume();
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
/* 279 */     double result = Double.NaN;
/* 280 */     Number volume = getVolume(series, item);
/* 281 */     if (volume != null) {
/* 282 */       result = volume.doubleValue();
/*     */     }
/* 284 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeriesCount() {
/* 294 */     return 1;
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
/*     */   public int getItemCount(int series) {
/* 306 */     return this.data.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sortDataByDate() {
/* 313 */     Arrays.sort((Object[])this.data);
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
/* 325 */     if (this == obj) {
/* 326 */       return true;
/*     */     }
/* 328 */     if (!(obj instanceof DefaultOHLCDataset)) {
/* 329 */       return false;
/*     */     }
/* 331 */     DefaultOHLCDataset that = (DefaultOHLCDataset)obj;
/* 332 */     if (!this.key.equals(that.key)) {
/* 333 */       return false;
/*     */     }
/* 335 */     if (!Arrays.equals((Object[])this.data, (Object[])that.data)) {
/* 336 */       return false;
/*     */     }
/* 338 */     return true;
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
/* 350 */     DefaultOHLCDataset clone = (DefaultOHLCDataset)super.clone();
/* 351 */     clone.data = new OHLCDataItem[this.data.length];
/* 352 */     System.arraycopy(this.data, 0, clone.data, 0, this.data.length);
/* 353 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/DefaultOHLCDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */