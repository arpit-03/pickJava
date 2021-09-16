/*     */ package Catalano.Statistics;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Normalizations
/*     */ {
/*     */   public static double[] DecimalScaling(double[] data) {
/*  42 */     double max = 0.0D;
/*  43 */     for (int i = 0; i < data.length; i++) {
/*  44 */       max = Math.abs(data[i]);
/*     */     }
/*     */     
/*  47 */     int k = 10;
/*  48 */     while (max / k > 1.0D) {
/*  49 */       k *= 10;
/*     */     }
/*     */     
/*  52 */     double[] result = new double[data.length];
/*  53 */     for (int j = 0; j < data.length; j++) {
/*  54 */       result[j] = data[j] / k;
/*     */     }
/*     */     
/*  57 */     return result;
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
/*     */   public static double[] RangeNormalization(double[] data, double fromMin, double fromMax, double toMin, double toMax) {
/*  72 */     double[] norm = new double[data.length];
/*  73 */     for (int i = 0; i < data.length; i++) {
/*  74 */       norm[i] = (toMax - toMin) * (norm[i] - fromMin) / (fromMax - fromMin) + toMin;
/*  75 */       if (fromMax - fromMin == 0.0D) norm[i] = 0.0D; 
/*     */     } 
/*  77 */     return norm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] UnitVectorNormalization(double[] data) {
/*  88 */     double[] norm = new double[data.length];
/*     */ 
/*     */     
/*  91 */     double magnitude = 0.0D; int i;
/*  92 */     for (i = 0; i < data.length; i++) {
/*  93 */       magnitude += data[i] * data[i];
/*     */     }
/*     */     
/*  96 */     magnitude = Math.sqrt(magnitude);
/*     */     
/*  98 */     for (i = 0; i < data.length; i++) {
/*  99 */       norm[i] = data[i] / magnitude;
/*     */     }
/*     */     
/* 102 */     return norm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] StandartNormalDensity(double[] data) {
/* 112 */     double[] norm = new double[data.length];
/* 113 */     double mean = DescriptiveStatistics.Mean(data);
/* 114 */     double stdDev = DescriptiveStatistics.StandartDeviation(data);
/*     */     
/* 116 */     for (int i = 0; i < data.length; i++) {
/* 117 */       norm[i] = (data[i] - mean) / stdDev;
/*     */     }
/*     */     
/* 120 */     return norm;
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
/*     */   public static double[] MinMaxNormalization(double[] data, double min, double max) {
/* 132 */     double[] norm = new double[data.length];
/* 133 */     double fMin = DescriptiveStatistics.Minimum(data);
/* 134 */     double fMax = DescriptiveStatistics.Maximum(data);
/*     */     
/* 136 */     for (int i = 0; i < data.length; i++) {
/* 137 */       norm[i] = (data[i] - fMin) / (fMax - fMin) * (max - min) + min;
/*     */     }
/*     */     
/* 140 */     return norm;
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
/*     */   public static double[] SoftmaxScaling(double[] data, double r) {
/* 152 */     double[] norm = new double[data.length];
/* 153 */     double mean = DescriptiveStatistics.Mean(data);
/* 154 */     double stdDev = DescriptiveStatistics.StandartDeviation(data);
/*     */     
/* 156 */     r *= stdDev;
/*     */     
/* 158 */     for (int i = 0; i < data.length; i++) {
/* 159 */       double y = (data[i] - mean) / r;
/* 160 */       norm[i] = 1.0D / (1.0D + Math.pow(Math.E, -y));
/*     */     } 
/*     */     
/* 163 */     return norm;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Normalizations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */