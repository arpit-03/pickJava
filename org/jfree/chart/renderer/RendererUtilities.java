/*     */ package org.jfree.chart.renderer;
/*     */ 
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.DomainOrder;
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
/*     */ public class RendererUtilities
/*     */ {
/*     */   public static int findLiveItemsLowerBound(XYDataset dataset, int series, double xLow, double xHigh) {
/*  75 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*  76 */     if (xLow >= xHigh) {
/*  77 */       throw new IllegalArgumentException("Requires xLow < xHigh.");
/*     */     }
/*  79 */     int itemCount = dataset.getItemCount(series);
/*  80 */     if (itemCount <= 1) {
/*  81 */       return 0;
/*     */     }
/*  83 */     if (dataset.getDomainOrder() == DomainOrder.ASCENDING) {
/*     */ 
/*     */       
/*  86 */       int low = 0;
/*  87 */       int high = itemCount - 1;
/*  88 */       double lowValue = dataset.getXValue(series, low);
/*  89 */       if (lowValue >= xLow)
/*     */       {
/*  91 */         return low;
/*     */       }
/*  93 */       double highValue = dataset.getXValue(series, high);
/*  94 */       if (highValue < xLow)
/*     */       {
/*  96 */         return high;
/*     */       }
/*  98 */       while (high - low > 1) {
/*  99 */         int mid = (low + high) / 2;
/* 100 */         double midV = dataset.getXValue(series, mid);
/* 101 */         if (midV >= xLow) {
/* 102 */           high = mid;
/*     */           continue;
/*     */         } 
/* 105 */         low = mid;
/*     */       } 
/*     */       
/* 108 */       return high;
/*     */     } 
/* 110 */     if (dataset.getDomainOrder() == DomainOrder.DESCENDING) {
/*     */ 
/*     */       
/* 113 */       int low = 0;
/* 114 */       int high = itemCount - 1;
/* 115 */       double lowValue = dataset.getXValue(series, low);
/* 116 */       if (lowValue <= xHigh) {
/* 117 */         return low;
/*     */       }
/* 119 */       double highValue = dataset.getXValue(series, high);
/* 120 */       if (highValue > xHigh) {
/* 121 */         return high;
/*     */       }
/* 123 */       while (high - low > 1) {
/* 124 */         int mid = (low + high) / 2;
/* 125 */         double midV = dataset.getXValue(series, mid);
/* 126 */         if (midV > xHigh) {
/* 127 */           low = mid;
/*     */           continue;
/*     */         } 
/* 130 */         high = mid;
/*     */       } 
/*     */       
/* 133 */       return high;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     int index = 0;
/*     */     
/* 141 */     double x = dataset.getXValue(series, index);
/* 142 */     while (index < itemCount && x < xLow) {
/* 143 */       index++;
/* 144 */       if (index < itemCount) {
/* 145 */         x = dataset.getXValue(series, index);
/*     */       }
/*     */     } 
/* 148 */     return Math.min(Math.max(0, index), itemCount - 1);
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
/*     */   public static int findLiveItemsUpperBound(XYDataset dataset, int series, double xLow, double xHigh) {
/* 169 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 170 */     if (xLow >= xHigh) {
/* 171 */       throw new IllegalArgumentException("Requires xLow < xHigh.");
/*     */     }
/* 173 */     int itemCount = dataset.getItemCount(series);
/* 174 */     if (itemCount <= 1) {
/* 175 */       return 0;
/*     */     }
/* 177 */     if (dataset.getDomainOrder() == DomainOrder.ASCENDING) {
/* 178 */       int low = 0;
/* 179 */       int high = itemCount - 1;
/* 180 */       double lowValue = dataset.getXValue(series, low);
/* 181 */       if (lowValue > xHigh) {
/* 182 */         return low;
/*     */       }
/* 184 */       double highValue = dataset.getXValue(series, high);
/* 185 */       if (highValue <= xHigh) {
/* 186 */         return high;
/*     */       }
/* 188 */       int mid = (low + high) / 2;
/* 189 */       while (high - low > 1) {
/* 190 */         double midV = dataset.getXValue(series, mid);
/* 191 */         if (midV <= xHigh) {
/* 192 */           low = mid;
/*     */         } else {
/*     */           
/* 195 */           high = mid;
/*     */         } 
/* 197 */         mid = (low + high) / 2;
/*     */       } 
/* 199 */       return mid;
/*     */     } 
/* 201 */     if (dataset.getDomainOrder() == DomainOrder.DESCENDING) {
/*     */ 
/*     */       
/* 204 */       int low = 0;
/* 205 */       int high = itemCount - 1;
/* 206 */       int mid = (low + high) / 2;
/* 207 */       double lowValue = dataset.getXValue(series, low);
/* 208 */       if (lowValue < xLow) {
/* 209 */         return low;
/*     */       }
/* 211 */       double highValue = dataset.getXValue(series, high);
/* 212 */       if (highValue >= xLow) {
/* 213 */         return high;
/*     */       }
/* 215 */       while (high - low > 1) {
/* 216 */         double midV = dataset.getXValue(series, mid);
/* 217 */         if (midV >= xLow) {
/* 218 */           low = mid;
/*     */         } else {
/*     */           
/* 221 */           high = mid;
/*     */         } 
/* 223 */         mid = (low + high) / 2;
/*     */       } 
/* 225 */       return mid;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     int index = itemCount - 1;
/*     */     
/* 233 */     double x = dataset.getXValue(series, index);
/* 234 */     while (index >= 0 && x > xHigh) {
/* 235 */       index--;
/* 236 */       if (index >= 0) {
/* 237 */         x = dataset.getXValue(series, index);
/*     */       }
/*     */     } 
/* 240 */     return Math.max(index, 0);
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
/*     */   public static int[] findLiveItems(XYDataset dataset, int series, double xLow, double xHigh) {
/* 260 */     int i0 = findLiveItemsLowerBound(dataset, series, xLow, xHigh);
/* 261 */     int i1 = findLiveItemsUpperBound(dataset, series, xLow, xHigh);
/* 262 */     if (i0 > i1) {
/* 263 */       i0 = i1;
/*     */     }
/* 265 */     return new int[] { i0, i1 };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/RendererUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */