/*    */ package Catalano.Math.Random;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StandardRandom
/*    */   implements IRandomNumberGenerator
/*    */ {
/*    */   private Random r;
/*    */   private long seed;
/*    */   
/*    */   public StandardRandom() {
/* 21 */     this(System.nanoTime());
/*    */   }
/*    */   
/*    */   public StandardRandom(long seed) {
/* 25 */     setSeed(seed);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSeed(long seed) {
/* 30 */     this.seed = seed;
/* 31 */     this.r = new Random(seed);
/*    */   }
/*    */ 
/*    */   
/*    */   public int nextBits(int numbits) {
/* 36 */     throw new UnsupportedOperationException("Not supported yet.");
/*    */   }
/*    */ 
/*    */   
/*    */   public int nextInt() {
/* 41 */     return this.r.nextInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public int nextInt(int n) {
/* 46 */     return this.r.nextInt(n);
/*    */   }
/*    */ 
/*    */   
/*    */   public long nextLong() {
/* 51 */     return this.r.nextLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public double nextDouble() {
/* 56 */     return this.r.nextDouble();
/*    */   }
/*    */ 
/*    */   
/*    */   public void nextDoubles(double[] d) {
/* 61 */     int n = d.length;
/* 62 */     for (int i = 0; i < n; i++)
/* 63 */       d[i] = nextDouble(); 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Random/StandardRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */