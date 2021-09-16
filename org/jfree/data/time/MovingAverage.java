/*     */ package org.jfree.data.time;
/*     */ 
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.data.xy.XYSeries;
/*     */ import org.jfree.data.xy.XYSeriesCollection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MovingAverage
/*     */ {
/*     */   public static TimeSeriesCollection createMovingAverage(TimeSeriesCollection source, String suffix, int periodCount, int skip) {
/*  80 */     ParamChecks.nullNotPermitted(source, "source");
/*  81 */     if (periodCount < 1) {
/*  82 */       throw new IllegalArgumentException("periodCount must be greater than or equal to 1.");
/*     */     }
/*     */ 
/*     */     
/*  86 */     TimeSeriesCollection result = new TimeSeriesCollection();
/*  87 */     for (int i = 0; i < source.getSeriesCount(); i++) {
/*  88 */       TimeSeries sourceSeries = source.getSeries(i);
/*  89 */       TimeSeries maSeries = createMovingAverage(sourceSeries, sourceSeries
/*  90 */           .getKey() + suffix, periodCount, skip);
/*  91 */       result.addSeries(maSeries);
/*     */     } 
/*  93 */     return result;
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
/*     */   public static TimeSeries createMovingAverage(TimeSeries source, String name, int periodCount, int skip) {
/* 113 */     ParamChecks.nullNotPermitted(source, "source");
/* 114 */     if (periodCount < 1) {
/* 115 */       throw new IllegalArgumentException("periodCount must be greater than or equal to 1.");
/*     */     }
/*     */ 
/*     */     
/* 119 */     TimeSeries result = new TimeSeries(name);
/*     */     
/* 121 */     if (source.getItemCount() > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 126 */       long firstSerial = source.getTimePeriod(0).getSerialIndex() + skip;
/*     */       
/* 128 */       for (int i = source.getItemCount() - 1; i >= 0; i--) {
/*     */ 
/*     */         
/* 131 */         RegularTimePeriod period = source.getTimePeriod(i);
/* 132 */         long serial = period.getSerialIndex();
/*     */         
/* 134 */         if (serial >= firstSerial) {
/*     */           
/* 136 */           int n = 0;
/* 137 */           double sum = 0.0D;
/* 138 */           long serialLimit = period.getSerialIndex() - periodCount;
/* 139 */           int offset = 0;
/* 140 */           boolean finished = false;
/*     */           
/* 142 */           while (offset < periodCount && !finished) {
/* 143 */             if (i - offset >= 0) {
/* 144 */               TimeSeriesDataItem item = source.getRawDataItem(i - offset);
/*     */               
/* 146 */               RegularTimePeriod p = item.getPeriod();
/* 147 */               Number v = item.getValue();
/* 148 */               long currentIndex = p.getSerialIndex();
/* 149 */               if (currentIndex > serialLimit) {
/* 150 */                 if (v != null) {
/* 151 */                   sum += v.doubleValue();
/* 152 */                   n++;
/*     */                 } 
/*     */               } else {
/*     */                 
/* 156 */                 finished = true;
/*     */               } 
/*     */             } 
/* 159 */             offset++;
/*     */           } 
/* 161 */           if (n > 0) {
/* 162 */             result.add(period, sum / n);
/*     */           } else {
/*     */             
/* 165 */             result.add(period, (Number)null);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 172 */     return result;
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
/*     */   public static TimeSeries createPointMovingAverage(TimeSeries source, String name, int pointCount) {
/* 194 */     ParamChecks.nullNotPermitted(source, "source");
/* 195 */     if (pointCount < 2) {
/* 196 */       throw new IllegalArgumentException("periodCount must be greater than or equal to 2.");
/*     */     }
/*     */ 
/*     */     
/* 200 */     TimeSeries result = new TimeSeries(name);
/* 201 */     double rollingSumForPeriod = 0.0D;
/* 202 */     for (int i = 0; i < source.getItemCount(); i++) {
/*     */       
/* 204 */       TimeSeriesDataItem current = source.getRawDataItem(i);
/* 205 */       RegularTimePeriod period = current.getPeriod();
/*     */       
/* 207 */       rollingSumForPeriod += current.getValue().doubleValue();
/*     */       
/* 209 */       if (i > pointCount - 1) {
/*     */         
/* 211 */         TimeSeriesDataItem startOfMovingAvg = source.getRawDataItem(i - pointCount);
/*     */         
/* 213 */         rollingSumForPeriod -= startOfMovingAvg.getValue()
/* 214 */           .doubleValue();
/* 215 */         result.add(period, rollingSumForPeriod / pointCount);
/*     */       }
/* 217 */       else if (i == pointCount - 1) {
/* 218 */         result.add(period, rollingSumForPeriod / pointCount);
/*     */       } 
/*     */     } 
/* 221 */     return result;
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
/*     */   public static XYDataset createMovingAverage(XYDataset source, String suffix, long period, long skip) {
/* 239 */     return createMovingAverage(source, suffix, period, skip);
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
/*     */   public static XYDataset createMovingAverage(XYDataset source, String suffix, double period, double skip) {
/* 260 */     ParamChecks.nullNotPermitted(source, "source");
/* 261 */     XYSeriesCollection result = new XYSeriesCollection();
/* 262 */     for (int i = 0; i < source.getSeriesCount(); i++) {
/* 263 */       XYSeries s = createMovingAverage(source, i, source.getSeriesKey(i) + suffix, period, skip);
/*     */       
/* 265 */       result.addSeries(s);
/*     */     } 
/* 267 */     return (XYDataset)result;
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
/*     */   public static XYSeries createMovingAverage(XYDataset source, int series, String name, double period, double skip) {
/* 285 */     ParamChecks.nullNotPermitted(source, "source");
/* 286 */     if (period < Double.MIN_VALUE) {
/* 287 */       throw new IllegalArgumentException("period must be positive.");
/*     */     }
/* 289 */     if (skip < 0.0D) {
/* 290 */       throw new IllegalArgumentException("skip must be >= 0.0.");
/*     */     }
/*     */     
/* 293 */     XYSeries result = new XYSeries(name);
/*     */     
/* 295 */     if (source.getItemCount(series) > 0) {
/*     */ 
/*     */ 
/*     */       
/* 299 */       double first = source.getXValue(series, 0) + skip;
/*     */       
/* 301 */       for (int i = source.getItemCount(series) - 1; i >= 0; i--) {
/*     */ 
/*     */         
/* 304 */         double x = source.getXValue(series, i);
/*     */         
/* 306 */         if (x >= first) {
/*     */           
/* 308 */           int n = 0;
/* 309 */           double sum = 0.0D;
/* 310 */           double limit = x - period;
/* 311 */           int offset = 0;
/* 312 */           boolean finished = false;
/*     */           
/* 314 */           while (!finished) {
/* 315 */             if (i - offset >= 0) {
/* 316 */               double xx = source.getXValue(series, i - offset);
/* 317 */               Number yy = source.getY(series, i - offset);
/* 318 */               if (xx > limit) {
/* 319 */                 if (yy != null) {
/* 320 */                   sum += yy.doubleValue();
/* 321 */                   n++;
/*     */                 } 
/*     */               } else {
/*     */                 
/* 325 */                 finished = true;
/*     */               } 
/*     */             } else {
/*     */               
/* 329 */               finished = true;
/*     */             } 
/* 331 */             offset++;
/*     */           } 
/* 333 */           if (n > 0) {
/* 334 */             result.add(x, sum / n);
/*     */           } else {
/*     */             
/* 337 */             result.add(x, null);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 344 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/MovingAverage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */