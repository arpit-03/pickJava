/*     */ package org.jfree.data.statistics;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Statistics
/*     */ {
/*     */   public static double calculateMean(Number[] values) {
/*  73 */     return calculateMean(values, true);
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
/*     */   public static double calculateMean(Number[] values, boolean includeNullAndNaN) {
/*  92 */     ParamChecks.nullNotPermitted(values, "values");
/*  93 */     double sum = 0.0D;
/*     */     
/*  95 */     int counter = 0;
/*  96 */     for (int i = 0; i < values.length; i++) {
/*     */       double current;
/*  98 */       if (values[i] != null) {
/*  99 */         current = values[i].doubleValue();
/*     */       } else {
/*     */         
/* 102 */         current = Double.NaN;
/*     */       } 
/*     */       
/* 105 */       if (includeNullAndNaN || !Double.isNaN(current)) {
/* 106 */         sum += current;
/* 107 */         counter++;
/*     */       } 
/*     */     } 
/* 110 */     double result = sum / counter;
/* 111 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double calculateMean(Collection values) {
/* 122 */     return calculateMean(values, true);
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
/*     */   public static double calculateMean(Collection values, boolean includeNullAndNaN) {
/* 141 */     ParamChecks.nullNotPermitted(values, "values");
/* 142 */     int count = 0;
/* 143 */     double total = 0.0D;
/* 144 */     Iterator iterator = values.iterator();
/* 145 */     while (iterator.hasNext()) {
/* 146 */       Object object = iterator.next();
/* 147 */       if (object == null) {
/* 148 */         if (includeNullAndNaN) {
/* 149 */           return Double.NaN;
/*     */         }
/*     */         continue;
/*     */       } 
/* 153 */       if (object instanceof Number) {
/* 154 */         Number number = (Number)object;
/* 155 */         double value = number.doubleValue();
/* 156 */         if (Double.isNaN(value)) {
/* 157 */           if (includeNullAndNaN) {
/* 158 */             return Double.NaN;
/*     */           }
/*     */           continue;
/*     */         } 
/* 162 */         total += number.doubleValue();
/* 163 */         count++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 168 */     return total / count;
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
/*     */   public static double calculateMedian(List values) {
/* 183 */     return calculateMedian(values, true);
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
/*     */   public static double calculateMedian(List<Comparable> values, boolean copyAndSort) {
/* 199 */     double result = Double.NaN;
/* 200 */     if (values != null) {
/* 201 */       if (copyAndSort) {
/* 202 */         int itemCount = values.size();
/* 203 */         List<Comparable> copy = new ArrayList(itemCount);
/* 204 */         for (int i = 0; i < itemCount; i++) {
/* 205 */           copy.add(i, values.get(i));
/*     */         }
/* 207 */         Collections.sort(copy);
/* 208 */         values = copy;
/*     */       } 
/* 210 */       int count = values.size();
/* 211 */       if (count > 0) {
/* 212 */         if (count % 2 == 1) {
/* 213 */           if (count > 1) {
/* 214 */             Number value = (Number)values.get((count - 1) / 2);
/* 215 */             result = value.doubleValue();
/*     */           } else {
/*     */             
/* 218 */             Number value = (Number)values.get(0);
/* 219 */             result = value.doubleValue();
/*     */           } 
/*     */         } else {
/*     */           
/* 223 */           Number value1 = (Number)values.get(count / 2 - 1);
/* 224 */           Number value2 = (Number)values.get(count / 2);
/* 225 */           result = (value1.doubleValue() + value2.doubleValue()) / 2.0D;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 230 */     return result;
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
/*     */   public static double calculateMedian(List values, int start, int end) {
/* 244 */     return calculateMedian(values, start, end, true);
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
/*     */   public static double calculateMedian(List<Number> values, int start, int end, boolean copyAndSort) {
/* 263 */     double result = Double.NaN;
/* 264 */     if (copyAndSort) {
/* 265 */       List<Comparable> working = new ArrayList(end - start + 1);
/* 266 */       for (int i = start; i <= end; i++) {
/* 267 */         working.add(values.get(i));
/*     */       }
/* 269 */       Collections.sort(working);
/* 270 */       result = calculateMedian(working, false);
/*     */     } else {
/*     */       
/* 273 */       int count = end - start + 1;
/* 274 */       if (count > 0) {
/* 275 */         if (count % 2 == 1) {
/* 276 */           if (count > 1) {
/*     */             
/* 278 */             Number value = values.get(start + (count - 1) / 2);
/* 279 */             result = value.doubleValue();
/*     */           } else {
/*     */             
/* 282 */             Number value = values.get(start);
/* 283 */             result = value.doubleValue();
/*     */           } 
/*     */         } else {
/*     */           
/* 287 */           Number value1 = values.get(start + count / 2 - 1);
/* 288 */           Number value2 = values.get(start + count / 2);
/*     */           
/* 290 */           result = (value1.doubleValue() + value2.doubleValue()) / 2.0D;
/*     */         } 
/*     */       }
/*     */     } 
/* 294 */     return result;
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
/*     */   public static double getStdDev(Number[] data) {
/* 307 */     ParamChecks.nullNotPermitted(data, "data");
/* 308 */     if (data.length == 0) {
/* 309 */       throw new IllegalArgumentException("Zero length 'data' array.");
/*     */     }
/* 311 */     double avg = calculateMean(data);
/* 312 */     double sum = 0.0D;
/*     */     
/* 314 */     for (int counter = 0; counter < data.length; counter++) {
/* 315 */       double diff = data[counter].doubleValue() - avg;
/* 316 */       sum += diff * diff;
/*     */     } 
/* 318 */     return Math.sqrt(sum / (data.length - 1));
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
/*     */   public static double[] getLinearFit(Number[] xData, Number[] yData) {
/* 332 */     ParamChecks.nullNotPermitted(xData, "xData");
/* 333 */     ParamChecks.nullNotPermitted(yData, "yData");
/* 334 */     if (xData.length != yData.length) {
/* 335 */       throw new IllegalArgumentException("Statistics.getLinearFit(): array lengths must be equal.");
/*     */     }
/*     */ 
/*     */     
/* 339 */     double[] result = new double[2];
/*     */     
/* 341 */     result[1] = getSlope(xData, yData);
/*     */     
/* 343 */     result[0] = calculateMean(yData) - result[1] * calculateMean(xData);
/*     */     
/* 345 */     return result;
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
/*     */   public static double getSlope(Number[] xData, Number[] yData) {
/* 358 */     ParamChecks.nullNotPermitted(xData, "xData");
/* 359 */     ParamChecks.nullNotPermitted(yData, "yData");
/* 360 */     if (xData.length != yData.length) {
/* 361 */       throw new IllegalArgumentException("Array lengths must be equal.");
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
/* 373 */     double sx = 0.0D, sxx = 0.0D, sxy = 0.0D, sy = 0.0D;
/*     */     int counter;
/* 375 */     for (counter = 0; counter < xData.length; counter++) {
/* 376 */       sx += xData[counter].doubleValue();
/* 377 */       sxx += Math.pow(xData[counter].doubleValue(), 2.0D);
/* 378 */       sxy += yData[counter].doubleValue() * xData[counter]
/* 379 */         .doubleValue();
/* 380 */       sy += yData[counter].doubleValue();
/*     */     } 
/* 382 */     return (sxy - sx * sy / counter) / (sxx - sx * sx / counter);
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
/*     */   public static double getCorrelation(Number[] data1, Number[] data2) {
/* 400 */     ParamChecks.nullNotPermitted(data1, "data1");
/* 401 */     ParamChecks.nullNotPermitted(data2, "data2");
/* 402 */     if (data1.length != data2.length) {
/* 403 */       throw new IllegalArgumentException("'data1' and 'data2' arrays must have same length.");
/*     */     }
/*     */ 
/*     */     
/* 407 */     int n = data1.length;
/* 408 */     double sumX = 0.0D;
/* 409 */     double sumY = 0.0D;
/* 410 */     double sumX2 = 0.0D;
/* 411 */     double sumY2 = 0.0D;
/* 412 */     double sumXY = 0.0D;
/* 413 */     for (int i = 0; i < n; i++) {
/* 414 */       double x = 0.0D;
/* 415 */       if (data1[i] != null) {
/* 416 */         x = data1[i].doubleValue();
/*     */       }
/* 418 */       double y = 0.0D;
/* 419 */       if (data2[i] != null) {
/* 420 */         y = data2[i].doubleValue();
/*     */       }
/* 422 */       sumX += x;
/* 423 */       sumY += y;
/* 424 */       sumXY += x * y;
/* 425 */       sumX2 += x * x;
/* 426 */       sumY2 += y * y;
/*     */     } 
/* 428 */     return (n * sumXY - sumX * sumY) / Math.pow((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY), 0.5D);
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
/*     */   public static double[][] getMovingAverage(Number[] xData, Number[] yData, int period) {
/* 446 */     if (xData.length != yData.length) {
/* 447 */       throw new IllegalArgumentException("Array lengths must be equal.");
/*     */     }
/*     */     
/* 450 */     if (period > xData.length) {
/* 451 */       throw new IllegalArgumentException("Period can't be longer than dataset.");
/*     */     }
/*     */ 
/*     */     
/* 455 */     double[][] result = new double[xData.length - period][2];
/* 456 */     for (int i = 0; i < result.length; i++) {
/* 457 */       result[i][0] = xData[i + period].doubleValue();
/*     */       
/* 459 */       double sum = 0.0D;
/* 460 */       for (int j = 0; j < period; j++) {
/* 461 */         sum += yData[i + j].doubleValue();
/*     */       }
/* 463 */       sum /= period;
/* 464 */       result[i][1] = sum;
/*     */     } 
/* 466 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/statistics/Statistics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */