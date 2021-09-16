/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Well512a
/*     */   extends AbstractWell
/*     */ {
/*     */   private static final long serialVersionUID = -6104179812103820574L;
/*     */   private static final int K = 512;
/*     */   private static final int M1 = 13;
/*     */   private static final int M2 = 9;
/*     */   private static final int M3 = 5;
/*     */   
/*     */   public Well512a() {
/*  56 */     super(512, 13, 9, 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Well512a(int seed) {
/*  63 */     super(512, 13, 9, 5, seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Well512a(int[] seed) {
/*  71 */     super(512, 13, 9, 5, seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Well512a(long seed) {
/*  78 */     super(512, 13, 9, 5, seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int next(int bits) {
/*  85 */     int indexRm1 = this.iRm1[this.index];
/*     */     
/*  87 */     int vi = this.v[this.index];
/*  88 */     int vi1 = this.v[this.i1[this.index]];
/*  89 */     int vi2 = this.v[this.i2[this.index]];
/*  90 */     int z0 = this.v[indexRm1];
/*     */ 
/*     */     
/*  93 */     int z1 = vi ^ vi << 16 ^ vi1 ^ vi1 << 15;
/*  94 */     int z2 = vi2 ^ vi2 >>> 11;
/*  95 */     int z3 = z1 ^ z2;
/*  96 */     int z4 = z0 ^ z0 << 2 ^ z1 ^ z1 << 18 ^ z2 << 28 ^ z3 ^ z3 << 5 & 0xDA442D24;
/*     */     
/*  98 */     this.v[this.index] = z3;
/*  99 */     this.v[indexRm1] = z4;
/* 100 */     this.index = indexRm1;
/*     */     
/* 102 */     return z4 >>> 32 - bits;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/Well512a.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */