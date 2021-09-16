/*     */ package Catalano.Statistics;
/*     */ 
/*     */ import Catalano.Core.IntRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HistogramStatistics
/*     */ {
/*     */   public static double Entropy(int[] values) {
/*  46 */     int n = values.length;
/*  47 */     int total = 0;
/*  48 */     double entropy = 0.0D;
/*     */     
/*     */     int i;
/*     */     
/*  52 */     for (i = 0; i < n; i++)
/*     */     {
/*  54 */       total += values[i];
/*     */     }
/*     */     
/*  57 */     if (total != 0)
/*     */     {
/*     */       
/*  60 */       for (i = 0; i < n; i++) {
/*     */ 
/*     */         
/*  63 */         double p = values[i] / total;
/*     */         
/*  65 */         if (p != 0.0D)
/*  66 */           entropy += -p * Math.log10(p) / Math.log10(2.0D); 
/*     */       } 
/*     */     }
/*  69 */     return entropy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IntRange GetRange(int[] values, double percent) {
/*  79 */     int total = 0, n = values.length;
/*     */ 
/*     */     
/*  82 */     for (int i = 0; i < n; i++)
/*     */     {
/*     */       
/*  85 */       total += values[i];
/*     */     }
/*     */ 
/*     */     
/*  89 */     int h = (int)(total * (percent + (1.0D - percent) / 2.0D));
/*     */     
/*     */     int min, hits;
/*  92 */     for (min = 0, hits = total; min < n; min++) {
/*     */       
/*  94 */       hits -= values[min];
/*  95 */       if (hits < h)
/*     */         break; 
/*     */     } 
/*     */     int max;
/*  99 */     for (max = n - 1, hits = total; max >= 0; max--) {
/*     */       
/* 101 */       hits -= values[max];
/* 102 */       if (hits < h)
/*     */         break; 
/*     */     } 
/* 105 */     return new IntRange(min, max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Kurtosis(int[] values) {
/* 114 */     double mean = Mean(values);
/* 115 */     double std = StdDev(values, mean);
/* 116 */     return Kurtosis(values, mean, std);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Kurtosis(int[] values, double mean, double stdDeviation) {
/* 127 */     double n = 0.0D;
/* 128 */     for (int i = 0; i < values.length; i++) {
/* 129 */       n += values[i];
/*     */     }
/* 131 */     double part1 = n * (n + 1.0D);
/* 132 */     part1 /= (n - 1.0D) * (n - 2.0D) * (n - 3.0D);
/*     */     
/* 134 */     double part2 = 0.0D;
/* 135 */     for (int j = 0; j < values.length; j++) {
/* 136 */       part2 += Math.pow((j - mean) / stdDeviation, 4.0D) * values[j];
/*     */     }
/*     */     
/* 139 */     double part3 = 3.0D * Math.pow(n - 1.0D, 2.0D);
/* 140 */     part3 /= (n - 2.0D) * (n - 3.0D);
/*     */     
/* 142 */     return part1 * part2 - part3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Mean(int[] values) {
/* 152 */     long total = 0L;
/* 153 */     double mean = 0.0D;
/*     */ 
/*     */     
/* 156 */     for (int i = 0, n = values.length; i < n; i++) {
/* 157 */       int hits = values[i];
/*     */       
/* 159 */       mean += (i * hits);
/*     */       
/* 161 */       total += hits;
/*     */     } 
/* 163 */     return (total == 0L) ? 0.0D : (mean / total);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int Median(int[] values) {
/* 172 */     int total = 0, n = values.length;
/*     */ 
/*     */     
/* 175 */     for (int i = 0; i < n; i++)
/*     */     {
/*     */       
/* 178 */       total += values[i];
/*     */     }
/*     */     
/* 181 */     int halfTotal = total / 2;
/* 182 */     int median = 0, v = 0;
/*     */ 
/*     */     
/* 185 */     for (; median < n; median++) {
/*     */       
/* 187 */       v += values[median];
/* 188 */       if (v >= halfTotal) {
/*     */         break;
/*     */       }
/*     */     } 
/* 192 */     return median;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int Mode(int[] values) {
/* 201 */     int mode = 0, curMax = 0;
/*     */     
/* 203 */     for (int i = 0, length = values.length; i < length; i++) {
/*     */       
/* 205 */       if (values[i] > curMax) {
/*     */         
/* 207 */         curMax = values[i];
/* 208 */         mode = i;
/*     */       } 
/*     */     } 
/* 211 */     return mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Skewness(int[] values) {
/* 220 */     double mean = Mean(values);
/* 221 */     double std = StdDev(values, mean);
/* 222 */     return Skewness(values, mean, std);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Skewness(int[] values, double mean, double stdDeviation) {
/* 233 */     double n = 0.0D;
/* 234 */     for (int i = 0; i < values.length; i++) {
/* 235 */       n += values[i];
/*     */     }
/* 237 */     double part1 = n / (n - 1.0D) * (n - 2.0D);
/*     */     
/* 239 */     double part2 = 0.0D;
/* 240 */     for (int j = 0; j < values.length; j++) {
/* 241 */       part2 += Math.pow((j - mean) / stdDeviation, 3.0D) * values[j];
/*     */     }
/*     */     
/* 244 */     return part1 * part2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double StdDev(int[] values) {
/* 253 */     return StdDev(values, Mean(values));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double StdDev(int[] values, double mean) {
/* 263 */     double stddev = 0.0D;
/*     */ 
/*     */     
/* 266 */     int total = 0;
/*     */ 
/*     */     
/* 269 */     for (int i = 0, n = values.length; i < n; i++) {
/*     */       
/* 271 */       int hits = values[i];
/* 272 */       double diff = i - mean;
/*     */       
/* 274 */       stddev += diff * diff * hits;
/*     */       
/* 276 */       total += hits;
/*     */     } 
/*     */     
/* 279 */     return (total == 0) ? 0.0D : Math.sqrt(stddev / (total - 1));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/HistogramStatistics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */