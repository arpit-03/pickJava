/*     */ package Catalano.Math.Random;
/*     */ 
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Pcg32
/*     */   implements IRandomNumberGenerator
/*     */ {
/*     */   private long state;
/*     */   private long inc;
/*     */   private static final long MULTIPLIER = 6364136223846793005L;
/*     */   private double nextNextGaussian;
/*     */   private boolean haveNextNextGaussian;
/*     */   
/*     */   public void setSeed(long seed) {
/*  54 */     seed(seed, streamUniquifier());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pcg32(long initState, long initSeq)
/*     */   {
/* 210 */     this.haveNextNextGaussian = false; seed(initState, initSeq); } public int nextInt() { long oldState = this.state; this.state = oldState * 6364136223846793005L + this.inc; int xorShifted = (int)((oldState >>> 18L ^ oldState) >>> 27L); int rot = (int)(oldState >>> 59L); return Integer.rotateRight(xorShifted, rot); } public int nextInt(int n) { int bits; int val; if (n <= 0) throw new IllegalArgumentException("n must be positive");  do { bits = nextInt() >>> 1; val = bits % n; } while (bits - val + n - 1 < 0); return val; } public long nextLong() { return (nextInt() << 32L) + nextInt(); } public Pcg32() { this.haveNextNextGaussian = false;
/*     */     seed(); }
/*     */   public long nextLong(long n) { long bits;
/*     */     long val;
/*     */     if (n <= 0L)
/*     */       throw new IllegalArgumentException("n must be positive"); 
/*     */     do {
/*     */       bits = nextLong() >>> 1L;
/*     */       val = bits % n;
/*     */     } while (bits - val + n - 1L < 0L);
/*     */     return val; }
/* 221 */   public boolean nextBoolean() { return ((nextInt() & 0x1) != 0); } public double nextGaussian() { if (this.haveNextNextGaussian) {
/* 222 */       this.haveNextNextGaussian = false;
/* 223 */       return this.nextNextGaussian;
/*     */     } 
/*     */     
/*     */     while (true) {
/* 227 */       double v1 = 2.0D * nextDouble() - 1.0D;
/* 228 */       double v2 = 2.0D * nextDouble() - 1.0D;
/* 229 */       double s = v1 * v1 + v2 * v2;
/* 230 */       if (s < 1.0D && s != 0.0D) {
/* 231 */         double multiplier = StrictMath.sqrt(-2.0D * StrictMath.log(s) / s);
/* 232 */         this.nextNextGaussian = v2 * multiplier;
/* 233 */         this.haveNextNextGaussian = true;
/* 234 */         return v1 * multiplier;
/*     */       } 
/*     */     }  } public float nextFloat() { return nextBits(24) / 1.6777216E7F; } public float nextFloat(float bound) { return bound * nextFloat(); } public double nextDouble() { return ((nextBits(26) << 27L) + nextBits(27)) / 9.007199254740992E15D; }
/*     */   public double nextDouble(double bound) {
/*     */     return bound * nextDouble();
/*     */   }
/*     */   public int nextBits(int bits) {
/*     */     return nextInt() >>> 32 - bits;
/*     */   }
/*     */   public double nextGaussian(double mean, double standardDeviation) {
/* 244 */     return nextGaussian() * standardDeviation + mean;
/*     */   }
/*     */   
/*     */   public void seed(long initState, long initSeq) {
/* 248 */     this.state = 0L;
/* 249 */     this.inc = 2L * initSeq + 1L;
/* 250 */     nextInt();
/* 251 */     this.state += initState;
/* 252 */     nextInt();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void seed() {
/* 263 */     seed(System.nanoTime(), streamUniquifier());
/*     */   }
/*     */   
/*     */   private static long streamUniquifier() {
/*     */     while (true) {
/* 268 */       long current = streamUniquifier.get();
/* 269 */       long next = current * 181783497276652981L;
/* 270 */       if (streamUniquifier.compareAndSet(current, next)) {
/* 271 */         return next;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/* 276 */   private static final AtomicLong streamUniquifier = new AtomicLong(System.identityHashCode(Pcg32.class));
/*     */ 
/*     */   
/*     */   public void nextDoubles(double[] d) {
/* 280 */     int n = d.length;
/* 281 */     for (int i = 0; i < n; i++)
/* 282 */       d[i] = nextDouble(); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Random/Pcg32.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */