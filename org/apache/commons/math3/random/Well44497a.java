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
/*     */ public class Well44497a
/*     */   extends AbstractWell
/*     */ {
/*     */   private static final long serialVersionUID = -3859207588353972099L;
/*     */   private static final int K = 44497;
/*     */   private static final int M1 = 23;
/*     */   private static final int M2 = 481;
/*     */   private static final int M3 = 229;
/*     */   
/*     */   public Well44497a() {
/*  56 */     super(44497, 23, 481, 229);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Well44497a(int seed) {
/*  63 */     super(44497, 23, 481, 229, seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Well44497a(int[] seed) {
/*  71 */     super(44497, 23, 481, 229, seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Well44497a(long seed) {
/*  78 */     super(44497, 23, 481, 229, seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int next(int bits) {
/*  85 */     int indexRm1 = this.iRm1[this.index];
/*  86 */     int indexRm2 = this.iRm2[this.index];
/*     */     
/*  88 */     int v0 = this.v[this.index];
/*  89 */     int vM1 = this.v[this.i1[this.index]];
/*  90 */     int vM2 = this.v[this.i2[this.index]];
/*  91 */     int vM3 = this.v[this.i3[this.index]];
/*     */ 
/*     */     
/*  94 */     int z0 = 0xFFFF8000 & this.v[indexRm1] ^ 0x7FFF & this.v[indexRm2];
/*  95 */     int z1 = v0 ^ v0 << 24 ^ vM1 ^ vM1 >>> 30;
/*  96 */     int z2 = vM2 ^ vM2 << 10 ^ vM3 << 26;
/*  97 */     int z3 = z1 ^ z2;
/*  98 */     int z2Prime = (z2 << 9 ^ z2 >>> 23) & 0xFBFFFFFF;
/*  99 */     int z2Second = ((z2 & 0x20000) != 0) ? (z2Prime ^ 0xB729FCEC) : z2Prime;
/* 100 */     int z4 = z0 ^ z1 ^ z1 >>> 20 ^ z2Second ^ z3;
/*     */     
/* 102 */     this.v[this.index] = z3;
/* 103 */     this.v[indexRm1] = z4;
/* 104 */     this.v[indexRm2] = this.v[indexRm2] & 0xFFFF8000;
/* 105 */     this.index = indexRm1;
/*     */     
/* 107 */     return z4 >>> 32 - bits;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/Well44497a.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */