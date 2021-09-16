/*     */ package org.jfree.data.statistics;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.xy.AbstractIntervalXYDataset;
/*     */ import org.jfree.data.xy.IntervalXYDataset;
/*     */ import org.jfree.util.ObjectUtilities;
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
/*     */ public class HistogramDataset
/*     */   extends AbstractIntervalXYDataset
/*     */   implements IntervalXYDataset, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6341668077370231153L;
/* 101 */   private List list = new ArrayList();
/* 102 */   private HistogramType type = HistogramType.FREQUENCY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HistogramType getType() {
/* 111 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(HistogramType type) {
/* 121 */     ParamChecks.nullNotPermitted(type, "type");
/* 122 */     this.type = type;
/* 123 */     fireDatasetChanged();
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
/*     */   public void addSeries(Comparable key, double[] values, int bins) {
/* 136 */     double minimum = getMinimum(values);
/* 137 */     double maximum = getMaximum(values);
/* 138 */     addSeries(key, values, bins, minimum, maximum);
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
/*     */   public void addSeries(Comparable key, double[] values, int bins, double minimum, double maximum) {
/* 156 */     ParamChecks.nullNotPermitted(key, "key");
/* 157 */     ParamChecks.nullNotPermitted(values, "values");
/* 158 */     if (bins < 1) {
/* 159 */       throw new IllegalArgumentException("The 'bins' value must be at least 1.");
/*     */     }
/*     */     
/* 162 */     double binWidth = (maximum - minimum) / bins;
/*     */     
/* 164 */     double lower = minimum;
/*     */     
/* 166 */     List<HistogramBin> binList = new ArrayList(bins); int i;
/* 167 */     for (i = 0; i < bins; i++) {
/*     */       HistogramBin bin;
/*     */ 
/*     */ 
/*     */       
/* 172 */       if (i == bins - 1) {
/* 173 */         bin = new HistogramBin(lower, maximum);
/*     */       } else {
/*     */         
/* 176 */         double upper = minimum + (i + 1) * binWidth;
/* 177 */         bin = new HistogramBin(lower, upper);
/* 178 */         lower = upper;
/*     */       } 
/* 180 */       binList.add(bin);
/*     */     } 
/*     */     
/* 183 */     for (i = 0; i < values.length; i++) {
/* 184 */       int binIndex = bins - 1;
/* 185 */       if (values[i] < maximum) {
/* 186 */         double fraction = (values[i] - minimum) / (maximum - minimum);
/* 187 */         if (fraction < 0.0D) {
/* 188 */           fraction = 0.0D;
/*     */         }
/* 190 */         binIndex = (int)(fraction * bins);
/*     */ 
/*     */ 
/*     */         
/* 194 */         if (binIndex >= bins) {
/* 195 */           binIndex = bins - 1;
/*     */         }
/*     */       } 
/* 198 */       HistogramBin bin = binList.get(binIndex);
/* 199 */       bin.incrementCount();
/*     */     } 
/*     */     
/* 202 */     Map<Object, Object> map = new HashMap<Object, Object>();
/* 203 */     map.put("key", key);
/* 204 */     map.put("bins", binList);
/* 205 */     map.put("values.length", new Integer(values.length));
/* 206 */     map.put("bin width", new Double(binWidth));
/* 207 */     this.list.add(map);
/* 208 */     fireDatasetChanged();
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
/*     */   private double getMinimum(double[] values) {
/* 220 */     if (values == null || values.length < 1) {
/* 221 */       throw new IllegalArgumentException("Null or zero length 'values' argument.");
/*     */     }
/*     */     
/* 224 */     double min = Double.MAX_VALUE;
/* 225 */     for (int i = 0; i < values.length; i++) {
/* 226 */       if (values[i] < min) {
/* 227 */         min = values[i];
/*     */       }
/*     */     } 
/* 230 */     return min;
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
/*     */   private double getMaximum(double[] values) {
/* 242 */     if (values == null || values.length < 1) {
/* 243 */       throw new IllegalArgumentException("Null or zero length 'values' argument.");
/*     */     }
/*     */     
/* 246 */     double max = -1.7976931348623157E308D;
/* 247 */     for (int i = 0; i < values.length; i++) {
/* 248 */       if (values[i] > max) {
/* 249 */         max = values[i];
/*     */       }
/*     */     } 
/* 252 */     return max;
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
/*     */   List getBins(int series) {
/* 267 */     Map map = this.list.get(series);
/* 268 */     return (List)map.get("bins");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getTotal(int series) {
/* 279 */     Map map = this.list.get(series);
/* 280 */     return ((Integer)map.get("values.length")).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getBinWidth(int series) {
/* 291 */     Map map = this.list.get(series);
/* 292 */     return ((Double)map.get("bin width")).doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeriesCount() {
/* 302 */     return this.list.size();
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
/* 318 */     Map map = this.list.get(series);
/* 319 */     return (Comparable)map.get("key");
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
/* 335 */     return getBins(series).size();
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
/* 355 */     List<HistogramBin> bins = getBins(series);
/* 356 */     HistogramBin bin = bins.get(item);
/* 357 */     double x = (bin.getStartBoundary() + bin.getEndBoundary()) / 2.0D;
/* 358 */     return new Double(x);
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
/*     */   public Number getY(int series, int item) {
/* 376 */     List<HistogramBin> bins = getBins(series);
/* 377 */     HistogramBin bin = bins.get(item);
/* 378 */     double total = getTotal(series);
/* 379 */     double binWidth = getBinWidth(series);
/*     */     
/* 381 */     if (this.type == HistogramType.FREQUENCY) {
/* 382 */       return new Double(bin.getCount());
/*     */     }
/* 384 */     if (this.type == HistogramType.RELATIVE_FREQUENCY) {
/* 385 */       return new Double(bin.getCount() / total);
/*     */     }
/* 387 */     if (this.type == HistogramType.SCALE_AREA_TO_1) {
/* 388 */       return new Double(bin.getCount() / binWidth * total);
/*     */     }
/*     */     
/* 391 */     throw new IllegalStateException();
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
/*     */   public Number getStartX(int series, int item) {
/* 409 */     List<HistogramBin> bins = getBins(series);
/* 410 */     HistogramBin bin = bins.get(item);
/* 411 */     return new Double(bin.getStartBoundary());
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
/*     */   public Number getEndX(int series, int item) {
/* 428 */     List<HistogramBin> bins = getBins(series);
/* 429 */     HistogramBin bin = bins.get(item);
/* 430 */     return new Double(bin.getEndBoundary());
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
/*     */   public Number getStartY(int series, int item) {
/* 449 */     return getY(series, item);
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
/*     */   public Number getEndY(int series, int item) {
/* 468 */     return getY(series, item);
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
/* 480 */     if (obj == this) {
/* 481 */       return true;
/*     */     }
/* 483 */     if (!(obj instanceof HistogramDataset)) {
/* 484 */       return false;
/*     */     }
/* 486 */     HistogramDataset that = (HistogramDataset)obj;
/* 487 */     if (!ObjectUtilities.equal(this.type, that.type)) {
/* 488 */       return false;
/*     */     }
/* 490 */     if (!ObjectUtilities.equal(this.list, that.list)) {
/* 491 */       return false;
/*     */     }
/* 493 */     return true;
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
/* 505 */     HistogramDataset clone = (HistogramDataset)super.clone();
/* 506 */     int seriesCount = getSeriesCount();
/* 507 */     clone.list = new ArrayList(seriesCount);
/* 508 */     for (int i = 0; i < seriesCount; i++) {
/* 509 */       clone.list.add(new HashMap<Object, Object>(this.list.get(i)));
/*     */     }
/* 511 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/statistics/HistogramDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */