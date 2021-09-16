/*     */ package Catalano.Statistics;
/*     */ 
/*     */ import Catalano.Core.FloatRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContinuousHistogram
/*     */ {
/*     */   private int[] values;
/*     */   private FloatRange range;
/*     */   private float mean;
/*     */   private float stdDev;
/*     */   private float median;
/*     */   private float min;
/*     */   private float max;
/*     */   private int total;
/*     */   
/*     */   public int[] getValues() {
/*  49 */     return this.values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMean() {
/*  57 */     return this.mean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getStdDev() {
/*  65 */     return this.stdDev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMedian() {
/*  73 */     return this.median;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMin() {
/*  81 */     return this.min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMax() {
/*  89 */     return this.max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContinuousHistogram(int[] values, FloatRange range) {
/*  98 */     this.values = values;
/*  99 */     this.range = range;
/* 100 */     Update();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatRange getRange(float percent) {
/* 110 */     int h = (int)(this.total * (percent + (1.0F - percent) / 2.0F));
/* 111 */     int n = this.values.length;
/* 112 */     int nM1 = n - 1;
/*     */     
/*     */     int min, hits;
/* 115 */     for (min = 0, hits = this.total; min < n; min++) {
/*     */       
/* 117 */       hits -= this.values[min];
/* 118 */       if (hits < h)
/*     */         break; 
/*     */     } 
/*     */     int max;
/* 122 */     for (max = nM1, hits = this.total; max >= 0; max--) {
/*     */       
/* 124 */       hits -= this.values[max];
/* 125 */       if (hits < h) {
/*     */         break;
/*     */       }
/*     */     } 
/* 129 */     return new FloatRange(min / nM1 * this.range.length() + this.range.getMin(), max / nM1 * this.range.length() + this.range.getMin());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Update() {
/* 137 */     int n = this.values.length;
/* 138 */     int nM1 = n - 1;
/*     */     
/* 140 */     float rangeLength = this.range.length();
/* 141 */     float rangeMin = this.range.getMin();
/*     */     
/* 143 */     this.max = 0.0F;
/* 144 */     this.min = n;
/* 145 */     this.mean = 0.0F;
/* 146 */     this.stdDev = 0.0F;
/* 147 */     this.total = 0;
/*     */     
/* 149 */     double sum = 0.0D;
/*     */     
/*     */     int i;
/* 152 */     for (i = 0; i < n; i++) {
/*     */       
/* 154 */       int j = this.values[i];
/*     */       
/* 156 */       if (j != 0) {
/*     */ 
/*     */         
/* 159 */         if (i > this.max) {
/* 160 */           this.max = i;
/*     */         }
/* 162 */         if (i < this.min) {
/* 163 */           this.min = i;
/*     */         }
/*     */       } 
/*     */       
/* 167 */       this.total += j;
/*     */       
/* 169 */       sum += (i / nM1 * rangeLength + rangeMin) * j;
/*     */     } 
/*     */     
/* 172 */     if (this.total != 0)
/*     */     {
/* 174 */       this.mean = (float)(sum / this.total);
/*     */     }
/*     */     
/* 177 */     this.min = this.min / nM1 * rangeLength + rangeMin;
/* 178 */     this.max = this.max / nM1 * rangeLength + rangeMin;
/*     */ 
/*     */     
/* 181 */     sum = 0.0D;
/*     */ 
/*     */     
/* 184 */     for (i = 0; i < n; i++) {
/*     */       
/* 186 */       int j = this.values[i];
/* 187 */       double diff = i / nM1 * rangeLength + rangeMin - this.mean;
/* 188 */       sum += diff * diff * j;
/*     */     } 
/*     */     
/* 191 */     if (this.total != 0)
/*     */     {
/* 193 */       this.stdDev = (float)Math.sqrt(sum / this.total);
/*     */     }
/*     */ 
/*     */     
/* 197 */     int halfTotal = this.total / 2;
/*     */     int hits, m;
/* 199 */     for (m = 0, hits = 0; m < n; m++) {
/*     */       
/* 201 */       hits += this.values[m];
/* 202 */       if (hits >= halfTotal)
/*     */         break; 
/*     */     } 
/* 205 */     this.median = m / nM1 * rangeLength + rangeMin;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/ContinuousHistogram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */