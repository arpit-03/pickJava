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
/*     */ public class Histogram
/*     */ {
/*     */   private int[] values;
/*  34 */   private double mean = 0.0D;
/*  35 */   private double stdDev = 0.0D;
/*  36 */   private double entropy = 0.0D;
/*  37 */   private int median = 0;
/*     */   private int mode;
/*     */   private int min;
/*     */   private int max;
/*     */   private long total;
/*  42 */   private int bins = 10;
/*     */   
/*     */   public static int[] MatchHistograms(int[] histA, int[] histB) {
/*  45 */     int length = histA.length;
/*  46 */     double[] PA = CDF(histA);
/*  47 */     double[] PB = CDF(histB);
/*  48 */     int[] F = new int[length];
/*     */     
/*  50 */     for (int a = 0; a < length; a++) {
/*  51 */       int j = length - 1;
/*     */       do {
/*  53 */         F[a] = j;
/*  54 */         --j;
/*  55 */       } while (j >= 0 && PA[a] <= PB[j]);
/*     */     } 
/*     */     
/*  58 */     return F;
/*     */   }
/*     */   
/*     */   public static int[] MatchHistograms(Histogram histA, Histogram histB) {
/*  62 */     return MatchHistograms(histA.values, histB.values);
/*     */   }
/*     */   
/*     */   public static double[] CDF(int[] values) {
/*  66 */     int length = values.length;
/*  67 */     int n = 0;
/*     */     
/*  69 */     for (int i = 0; i < length; i++) {
/*  70 */       n += values[i];
/*     */     }
/*     */     
/*  73 */     double[] P = new double[length];
/*  74 */     int c = values[0];
/*  75 */     P[0] = c / n;
/*  76 */     for (int j = 1; j < length; j++) {
/*  77 */       c += values[j];
/*  78 */       P[j] = c / n;
/*     */     } 
/*     */     
/*  81 */     return P;
/*     */   }
/*     */   
/*     */   public static double[] CDF(Histogram hist) {
/*  85 */     return CDF(hist.values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] Normalize(int[] values) {
/*  94 */     int sum = 0;
/*  95 */     for (int i = 0; i < values.length; i++) {
/*  96 */       sum += values[i];
/*     */     }
/*     */     
/*  99 */     double[] norm = new double[values.length];
/* 100 */     for (int j = 0; j < norm.length; j++) {
/* 101 */       norm[j] = values[j] / sum;
/*     */     }
/*     */     
/* 104 */     return norm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Histogram(int[] values) {
/* 112 */     this.values = values;
/* 113 */     update();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Histogram(int[] values, int bins) {
/* 122 */     this.values = values;
/* 123 */     this.bins = bins;
/* 124 */     update();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getValues() {
/* 132 */     return this.values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMean() {
/* 140 */     return this.mean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getStdDev() {
/* 148 */     return this.stdDev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEntropy() {
/* 156 */     return this.entropy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMedian() {
/* 164 */     return this.median;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMode() {
/* 172 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMin() {
/* 180 */     return this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMax() {
/* 188 */     return this.max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTotal() {
/* 196 */     return this.total;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBins() {
/* 204 */     return this.bins;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void update() {
/* 212 */     int n = this.values.length;
/*     */     
/* 214 */     this.max = 0;
/* 215 */     this.min = n;
/* 216 */     this.total = 0L;
/*     */     
/* 218 */     int maxVal = -2147483647;
/* 219 */     int minVal = Integer.MAX_VALUE;
/*     */ 
/*     */     
/* 222 */     for (int i = 0; i < n; i++) {
/*     */       
/* 224 */       if (this.values[i] != 0) {
/*     */ 
/*     */         
/* 227 */         if (i > this.max) {
/* 228 */           this.max = i;
/*     */         }
/* 230 */         if (i < this.min) {
/* 231 */           this.min = i;
/*     */         }
/* 233 */         maxVal = Math.max(maxVal, this.values[i]);
/* 234 */         minVal = Math.min(minVal, this.values[i]);
/* 235 */         this.total += this.values[i];
/*     */       } 
/*     */     } 
/*     */     
/* 239 */     double k = (maxVal - minVal) / this.bins;
/* 240 */     int[] h = new int[this.bins];
/*     */     
/* 242 */     for (int j = 0; j < this.values.length; j++) {
/* 243 */       double _min = minVal;
/* 244 */       double _max = _min + k;
/*     */ 
/*     */       
/* 247 */       if (this.values[j] >= _min && this.values[j] <= _max) {
/* 248 */         h[0] = h[0] + 1;
/*     */       }
/* 250 */       _min += k;
/* 251 */       _max += k;
/*     */ 
/*     */       
/* 254 */       for (int l = 1; l < this.bins; l++) {
/* 255 */         if (this.values[j] > _min && this.values[j] <= _max) {
/* 256 */           h[l] = h[l] + 1;
/*     */         }
/* 258 */         _min += k;
/* 259 */         _max += k;
/*     */       } 
/*     */     } 
/*     */     
/* 263 */     this.values = h;
/*     */     
/* 265 */     this.mean = HistogramStatistics.Mean(this.values);
/* 266 */     this.stdDev = HistogramStatistics.StdDev(this.values, this.mean);
/* 267 */     this.median = HistogramStatistics.Median(this.values);
/* 268 */     this.mode = HistogramStatistics.Mode(this.values);
/* 269 */     this.entropy = HistogramStatistics.Entropy(this.values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] Normalize() {
/* 277 */     double[] h = new double[this.values.length];
/* 278 */     for (int i = 0; i < h.length; i++) {
/* 279 */       h[i] = this.values[i] / this.total;
/*     */     }
/* 281 */     return h;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Histogram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */