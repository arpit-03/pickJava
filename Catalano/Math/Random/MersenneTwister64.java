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
/*     */ public class MersenneTwister64
/*     */   implements IRandomNumberGenerator
/*     */ {
/*     */   private static final int NN = 312;
/*     */   private static final int MM = 156;
/*     */   private static final long MATRIX_A = -5403634167711393303L;
/*     */   private static final long UM = -2147483648L;
/*     */   private static final long LM = 2147483647L;
/*  47 */   private static final long[] mag01 = new long[] { 0, -5403634167711393303L };
/*     */   
/*  49 */   private long[] mt = new long[312];
/*     */   
/*  51 */   private int mti = 313;
/*     */ 
/*     */   
/*     */   private long bits64;
/*     */ 
/*     */   
/*     */   private boolean bitState = true;
/*     */ 
/*     */   
/*     */   private static final long MAGIC_SEED = 777078800230351907L;
/*     */ 
/*     */   
/*     */   private static final long MAGIC_FACTOR1 = 6364136223846793005L;
/*     */ 
/*     */   
/*     */   public MersenneTwister64() {
/*  67 */     this(777078800230351907L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MersenneTwister64(long seed) {
/*  75 */     setSeed(seed);
/*     */   }
/*     */   
/*     */   public void setSeed(long seed) {
/*  79 */     this.mt[0] = seed;
/*  80 */     for (this.mti = 1; this.mti < 312; this.mti++) {
/*  81 */       this.mt[this.mti] = 6364136223846793005L * (this.mt[this.mti - 1] ^ this.mt[this.mti - 1] >>> 62L) + this.mti;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextBits(int numbits) {
/*  87 */     if (this.bitState) {
/*  88 */       this.bits64 = nextLong();
/*  89 */       this.bitState = false;
/*  90 */       return (int)(this.bits64 >>> 64 - numbits);
/*     */     } 
/*  92 */     this.bitState = true;
/*  93 */     return (int)this.bits64 >>> 32 - numbits;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextDouble() {
/*  99 */     return (nextLong() >>> 1L) / 9.223372036854776E18D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextDoubles(double[] d) {
/* 104 */     int n = d.length;
/* 105 */     for (int i = 0; i < n; i++) {
/* 106 */       d[i] = nextDouble();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt() {
/* 112 */     return nextBits(32);
/*     */   }
/*     */   public int nextInt(int n) {
/*     */     int bits;
/*     */     int val;
/* 117 */     if (n <= 0) {
/* 118 */       throw new IllegalArgumentException("n must be positive");
/*     */     }
/*     */ 
/*     */     
/* 122 */     if ((n & -n) == n) {
/* 123 */       return (int)(n * nextBits(31) >> 31L);
/*     */     }
/*     */ 
/*     */     
/*     */     do {
/* 128 */       bits = nextBits(31);
/* 129 */       val = bits % n;
/* 130 */     } while (bits - val + n - 1 < 0);
/*     */     
/* 132 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long nextLong() {
/* 140 */     if (this.mti >= 312) {
/*     */       int i;
/* 142 */       for (i = 0; i < 156; i++) {
/* 143 */         long l1 = this.mt[i] & 0xFFFFFFFF80000000L | this.mt[i + 1] & 0x7FFFFFFFL;
/* 144 */         this.mt[i] = this.mt[i + 156] ^ l1 >>> 1L ^ mag01[(int)(l1 & 0x1L)];
/*     */       } 
/* 146 */       for (; i < 311; i++) {
/* 147 */         long l1 = this.mt[i] & 0xFFFFFFFF80000000L | this.mt[i + 1] & 0x7FFFFFFFL;
/* 148 */         this.mt[i] = this.mt[i + -156] ^ l1 >>> 1L ^ mag01[(int)(l1 & 0x1L)];
/*     */       } 
/* 150 */       long l = this.mt[311] & 0xFFFFFFFF80000000L | this.mt[0] & 0x7FFFFFFFL;
/* 151 */       this.mt[311] = this.mt[155] ^ l >>> 1L ^ mag01[(int)(l & 0x1L)];
/*     */       
/* 153 */       this.mti = 0;
/*     */     } 
/*     */     
/* 156 */     long x = this.mt[this.mti++];
/*     */ 
/*     */     
/* 159 */     x ^= x >>> 29L & 0x5555555555555555L;
/* 160 */     x ^= x << 17L & 0x71D67FFFEDA60000L;
/* 161 */     x ^= x << 37L & 0xFFF7EEE000000000L;
/* 162 */     x ^= x >>> 43L;
/*     */     
/* 164 */     return x;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Random/MersenneTwister64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */