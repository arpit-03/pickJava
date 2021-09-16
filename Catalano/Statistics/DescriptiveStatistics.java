/*     */ package Catalano.Statistics;
/*     */ 
/*     */ import Catalano.Core.ArraysUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DescriptiveStatistics
/*     */ {
/*     */   public static double Mean(double[] values) {
/*  51 */     double mean = 0.0D;
/*  52 */     for (int i = 0; i < values.length; i++) {
/*  53 */       mean += values[i];
/*     */     }
/*  55 */     return mean / values.length;
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
/*     */   public static float Mean(float[] values) {
/*  70 */     float mean = 0.0F;
/*  71 */     for (int i = 0; i < values.length; i++) {
/*  72 */       mean += values[i];
/*     */     }
/*  74 */     return mean / values.length;
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
/*     */   public static double Mean(int[] values) {
/*  89 */     double mean = 0.0D;
/*  90 */     for (int i = 0; i < values.length; i++) {
/*  91 */       mean += values[i];
/*     */     }
/*  93 */     return mean / values.length;
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
/*     */   public static double Median(double[] values) {
/* 108 */     if (values.length == 1) {
/* 109 */       return values[0];
/*     */     }
/* 111 */     int[] index = ArraysUtil.Argsort(values, true);
/* 112 */     return values[index[(values.length + 1) / 2]];
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
/*     */   public static double Mode(double[] values) {
/* 128 */     int[] ord = ArraysUtil.Argsort(values, true);
/* 129 */     double v = values[ord[0]];
/* 130 */     int index = 0, x = 0, rep = 0;
/* 131 */     for (int i = 1; i < values.length; i++) {
/* 132 */       if (values[ord[i]] == v) {
/* 133 */         x++;
/* 134 */         if (x > rep) {
/* 135 */           rep = x;
/* 136 */           index = i;
/*     */         } 
/* 138 */         v = values[ord[i]];
/* 139 */         x = 0;
/*     */       } else {
/*     */         
/* 142 */         if (x > rep) {
/* 143 */           rep = x;
/* 144 */           index = i;
/*     */         } 
/* 146 */         v = values[ord[i]];
/* 147 */         x = 0;
/*     */       } 
/*     */     } 
/* 150 */     return values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Minimum(double[] values) {
/* 159 */     double min = Double.MAX_VALUE;
/* 160 */     for (int i = 0; i < values.length; i++) {
/* 161 */       if (min > values[i])
/* 162 */         min = values[i]; 
/*     */     } 
/* 164 */     return min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Maximum(double[] values) {
/* 173 */     double max = Double.MIN_VALUE;
/* 174 */     for (int i = 0; i < values.length; i++) {
/* 175 */       if (max < values[i])
/* 176 */         max = values[i]; 
/*     */     } 
/* 178 */     return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Variance(double[] values, double mean) {
/* 188 */     double sum = 0.0D;
/* 189 */     for (int i = 0; i < values.length; i++) {
/* 190 */       sum += Math.pow(values[i] - mean, 2.0D);
/*     */     }
/* 192 */     return sum / (values.length - 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float Variance(float[] values, float mean) {
/* 202 */     float sum = 0.0F;
/* 203 */     for (int i = 0; i < values.length; i++) {
/* 204 */       sum = (float)(sum + Math.pow((values[i] - mean), 2.0D));
/*     */     }
/* 206 */     return sum / (values.length - 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Variance(double[] values) {
/* 215 */     double mean = Mean(values);
/* 216 */     return Variance(values, mean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float Variance(float[] values) {
/* 225 */     float mean = Mean(values);
/* 226 */     return Variance(values, mean);
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
/*     */   public static double Range(double[] values) {
/* 240 */     double min = values[0];
/* 241 */     double max = values[0];
/* 242 */     for (int i = 1; i < values.length; i++) {
/* 243 */       min = Math.min(min, values[i]);
/* 244 */       max = Math.max(max, values[i]);
/*     */     } 
/* 246 */     return max - min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double StandartDeviation(double[] values) {
/* 256 */     double var = Variance(values);
/* 257 */     return Math.sqrt(var);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double StandartDeviation(double[] values, double mean) {
/* 268 */     double var = Variance(values, mean);
/* 269 */     return Math.sqrt(var);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Kurtosis(double[] values, double mean, double stdDeviation) {
/* 280 */     double n = values.length;
/*     */     
/* 282 */     double part1 = n * (n + 1.0D);
/* 283 */     part1 /= (n - 1.0D) * (n - 2.0D) * (n - 3.0D);
/*     */     
/* 285 */     double part2 = 0.0D;
/* 286 */     for (int i = 0; i < values.length; i++) {
/* 287 */       part2 += Math.pow((values[i] - mean) / stdDeviation, 4.0D);
/*     */     }
/*     */     
/* 290 */     double part3 = 3.0D * Math.pow(n - 1.0D, 2.0D);
/* 291 */     part3 /= (n - 2.0D) * (n - 3.0D);
/*     */     
/* 293 */     return part1 * part2 - part3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Kurtosis(double[] values) {
/* 303 */     double mean = Mean(values);
/* 304 */     double std = StandartDeviation(values, mean);
/* 305 */     return Kurtosis(values, mean, std);
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
/*     */   public static double Skewness(double[] values, double mean, double stdDeviation) {
/* 317 */     double n = values.length;
/*     */     
/* 319 */     double part1 = n / (n - 1.0D) * (n - 2.0D);
/*     */     
/* 321 */     double part2 = 0.0D;
/* 322 */     for (int i = 0; i < values.length; i++) {
/* 323 */       part2 += Math.pow((values[i] - mean) / stdDeviation, 3.0D);
/*     */     }
/*     */     
/* 326 */     return part1 * part2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double Skewness(double[] values) {
/* 336 */     double mean = Mean(values);
/* 337 */     double std = StandartDeviation(values, mean);
/* 338 */     return Skewness(values, mean, std);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/DescriptiveStatistics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */