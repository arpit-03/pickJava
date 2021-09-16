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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UniversalGenerator
/*     */   implements IRandomNumberGenerator
/*     */ {
/*     */   private static final int DEFAULT_RANDOM_SEED = 54217137;
/*     */   private static final int BIG_PRIME = 899999963;
/*     */   private double c;
/*     */   private double cd;
/*     */   private double cm;
/*     */   private double[] u;
/*     */   private int i97;
/*     */   private int j97;
/*     */   
/*     */   public void setSeed(long seed) {
/*  58 */     srand(Math.abs((int)(seed % 899999963L)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UniversalGenerator() {
/*  65 */     srand(54217137);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UniversalGenerator(int seed) {
/*  72 */     srand(Math.abs(seed % 899999963));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UniversalGenerator(long seed) {
/*  79 */     setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void srand(int ijkl) {
/*  86 */     this.u = new double[97];
/*     */     
/*  88 */     int ij = ijkl / 30082;
/*  89 */     int kl = ijkl % 30082;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     if (ij < 0 || ij > 31328 || kl < 0 || kl > 30081) {
/*  95 */       ij %= 31329;
/*  96 */       kl %= 30082;
/*     */     } 
/*     */     
/*  99 */     int i = ij / 177 % 177 + 2;
/* 100 */     int j = ij % 177 + 2;
/* 101 */     int k = kl / 169 % 178 + 1;
/* 102 */     int l = kl % 169;
/*     */ 
/*     */ 
/*     */     
/* 106 */     for (int ii = 0; ii < 97; ii++) {
/* 107 */       double s = 0.0D;
/* 108 */       double t = 0.5D;
/* 109 */       for (int jj = 0; jj < 24; jj++) {
/* 110 */         int m = i * j % 179 * k % 179;
/* 111 */         i = j;
/* 112 */         j = k;
/* 113 */         k = m;
/* 114 */         l = (53 * l + 1) % 169;
/* 115 */         if (l * m % 64 >= 32) {
/* 116 */           s += t;
/*     */         }
/* 118 */         t *= 0.5D;
/*     */       } 
/* 120 */       this.u[ii] = s;
/*     */     } 
/*     */     
/* 123 */     this.c = 0.021602869033813477D;
/* 124 */     this.cd = 0.45623308420181274D;
/* 125 */     this.cm = 0.9999998211860657D;
/* 126 */     this.i97 = 96;
/* 127 */     this.j97 = 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextDouble() {
/* 134 */     double uni = this.u[this.i97] - this.u[this.j97];
/* 135 */     if (uni < 0.0D) {
/* 136 */       uni++;
/*     */     }
/*     */     
/* 139 */     this.u[this.i97] = uni;
/* 140 */     if (--this.i97 < 0) {
/* 141 */       this.i97 = 96;
/*     */     }
/*     */     
/* 144 */     if (--this.j97 < 0) {
/* 145 */       this.j97 = 96;
/*     */     }
/*     */     
/* 148 */     this.c -= this.cd;
/* 149 */     if (this.c < 0.0D) {
/* 150 */       this.c += this.cm;
/*     */     }
/*     */     
/* 153 */     uni -= this.c;
/* 154 */     if (uni < 0.0D) {
/* 155 */       uni++;
/*     */     }
/*     */     
/* 158 */     return uni;
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextDoubles(double[] d) {
/* 163 */     int n = d.length;
/*     */ 
/*     */ 
/*     */     
/* 167 */     for (int i = 0; i < n; i++) {
/* 168 */       double uni = this.u[this.i97] - this.u[this.j97];
/* 169 */       if (uni < 0.0D) {
/* 170 */         uni++;
/*     */       }
/* 172 */       this.u[this.i97] = uni;
/* 173 */       if (--this.i97 < 0) {
/* 174 */         this.i97 = 96;
/*     */       }
/* 176 */       if (--this.j97 < 0) {
/* 177 */         this.j97 = 96;
/*     */       }
/* 179 */       this.c -= this.cd;
/* 180 */       if (this.c < 0.0D) {
/* 181 */         this.c += this.cm;
/*     */       }
/* 183 */       uni -= this.c;
/* 184 */       if (uni < 0.0D) {
/* 185 */         uni++;
/*     */       }
/* 187 */       d[i] = uni;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextBits(int numbits) {
/* 193 */     return nextInt() >>> 32 - numbits;
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt() {
/* 198 */     return (int)Math.floor(2.147483647E9D * (2.0D * nextDouble() - 1.0D));
/*     */   }
/*     */   public int nextInt(int n) {
/*     */     int bits;
/*     */     int val;
/* 203 */     if (n <= 0) {
/* 204 */       throw new IllegalArgumentException("n must be positive");
/*     */     }
/*     */ 
/*     */     
/* 208 */     if ((n & -n) == n) {
/* 209 */       return (int)(n * nextBits(31) >> 31L);
/*     */     }
/*     */ 
/*     */     
/*     */     do {
/* 214 */       bits = nextBits(31);
/* 215 */       val = bits % n;
/* 216 */     } while (bits - val + n - 1 < 0);
/*     */     
/* 218 */     return val;
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextLong() {
/* 223 */     return (long)Math.floor(9.223372036854776E18D * (2.0D * nextDouble() - 1.0D));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Random/UniversalGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */