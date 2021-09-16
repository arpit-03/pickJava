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
/*     */ public class Well44497b
/*     */   extends AbstractWell
/*     */ {
/*     */   private static final long serialVersionUID = 4032007538246675492L;
/*     */   private static final int K = 44497;
/*     */   private static final int M1 = 23;
/*     */   private static final int M2 = 481;
/*     */   private static final int M3 = 229;
/*     */   
/*     */   public Well44497b() {
/*  56 */     super(44497, 23, 481, 229);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Well44497b(int seed) {
/*  63 */     super(44497, 23, 481, 229, seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Well44497b(int[] seed) {
/*  71 */     super(44497, 23, 481, 229, seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Well44497b(long seed) {
/*  78 */     super(44497, 23, 481, 229, seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int next(int bits) {
/*  87 */     int indexRm1 = this.iRm1[this.index];
/*  88 */     int indexRm2 = this.iRm2[this.index];
/*     */     
/*  90 */     int v0 = this.v[this.index];
/*  91 */     int vM1 = this.v[this.i1[this.index]];
/*  92 */     int vM2 = this.v[this.i2[this.index]];
/*  93 */     int vM3 = this.v[this.i3[this.index]];
/*     */ 
/*     */     
/*  96 */     int z0 = 0xFFFF8000 & this.v[indexRm1] ^ 0x7FFF & this.v[indexRm2];
/*  97 */     int z1 = v0 ^ v0 << 24 ^ vM1 ^ vM1 >>> 30;
/*  98 */     int z2 = vM2 ^ vM2 << 10 ^ vM3 << 26;
/*  99 */     int z3 = z1 ^ z2;
/* 100 */     int z2Prime = (z2 << 9 ^ z2 >>> 23) & 0xFBFFFFFF;
/* 101 */     int z2Second = ((z2 & 0x20000) != 0) ? (z2Prime ^ 0xB729FCEC) : z2Prime;
/* 102 */     int z4 = z0 ^ z1 ^ z1 >>> 20 ^ z2Second ^ z3;
/*     */     
/* 104 */     this.v[this.index] = z3;
/* 105 */     this.v[indexRm1] = z4;
/* 106 */     this.v[indexRm2] = this.v[indexRm2] & 0xFFFF8000;
/* 107 */     this.index = indexRm1;
/*     */ 
/*     */ 
/*     */     
/* 111 */     z4 ^= z4 << 7 & 0x93DD1400;
/* 112 */     z4 ^= z4 << 15 & 0xFA118000;
/*     */     
/* 114 */     return z4 >>> 32 - bits;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/Well44497b.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */