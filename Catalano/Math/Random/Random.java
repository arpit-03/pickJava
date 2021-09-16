/*     */ package Catalano.Math.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Random
/*     */ {
/*     */   private IRandomNumberGenerator rng;
/*     */   private long seed;
/*     */   
/*     */   public long getSeed() {
/*  39 */     return this.seed;
/*     */   }
/*     */   
/*     */   public void setSeed(long seed) {
/*  43 */     this.seed = seed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Random() {
/*  50 */     this(new UniversalGenerator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Random(long seed) {
/*  57 */     this(new UniversalGenerator(seed));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Random(IRandomNumberGenerator rng) {
/*  64 */     this.rng = rng;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextDouble() {
/*  72 */     return this.rng.nextDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextDoubles(double[] d) {
/*  80 */     this.rng.nextDoubles(d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextDouble(double lo, double hi) {
/*  90 */     if (lo < 0.0D) {
/*  91 */       if (nextInt(2) == 0) {
/*  92 */         return -nextDouble(0.0D, -lo);
/*     */       }
/*  94 */       return nextDouble(0.0D, hi);
/*     */     } 
/*     */     
/*  97 */     return lo + (hi - lo) * nextDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextDoubles(double[] d, double lo, double hi) {
/* 108 */     this.rng.nextDoubles(d);
/*     */     
/* 110 */     double l = hi - lo;
/* 111 */     int n = d.length;
/* 112 */     for (int i = 0; i < n; i++) {
/* 113 */       d[i] = lo + l * d[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int nextInt() {
/* 121 */     return this.rng.nextInt();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int nextInt(int n) {
/* 128 */     return this.rng.nextInt(n);
/*     */   }
/*     */   
/*     */   public long nextLong() {
/* 132 */     return this.rng.nextLong();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] permutate(int n) {
/* 140 */     int[] x = new int[n];
/* 141 */     for (int i = 0; i < n; i++) {
/* 142 */       x[i] = i;
/*     */     }
/*     */     
/* 145 */     permutate(x);
/*     */     
/* 147 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void permutate(int[] x) {
/* 154 */     for (int i = 0; i < x.length; i++) {
/* 155 */       int j = i + nextInt(x.length - i);
/* 156 */       int s = x[i];
/* 157 */       x[i] = x[j];
/* 158 */       x[j] = s;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void permutate(float[] x) {
/* 166 */     for (int i = 0; i < x.length; i++) {
/* 167 */       int j = i + nextInt(x.length - i);
/* 168 */       float s = x[i];
/* 169 */       x[i] = x[j];
/* 170 */       x[j] = s;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void permutate(double[] x) {
/* 178 */     for (int i = 0; i < x.length; i++) {
/* 179 */       int j = i + nextInt(x.length - i);
/* 180 */       double s = x[i];
/* 181 */       x[i] = x[j];
/* 182 */       x[j] = s;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void permutate(Object[] x) {
/* 190 */     for (int i = 0; i < x.length; i++) {
/* 191 */       int j = i + nextInt(x.length - i);
/* 192 */       Object s = x[i];
/* 193 */       x[i] = x[j];
/* 194 */       x[j] = s;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Random/Random.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */