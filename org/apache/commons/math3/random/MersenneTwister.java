/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MersenneTwister
/*     */   extends BitsStreamGenerator
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8661194735290153518L;
/*     */   private static final int N = 624;
/*     */   private static final int M = 397;
/*  93 */   private static final int[] MAG01 = new int[] { 0, -1727483681 };
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] mt;
/*     */ 
/*     */ 
/*     */   
/*     */   private int mti;
/*     */ 
/*     */ 
/*     */   
/*     */   public MersenneTwister() {
/* 106 */     this.mt = new int[624];
/* 107 */     setSeed(System.currentTimeMillis() + System.identityHashCode(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MersenneTwister(int seed) {
/* 114 */     this.mt = new int[624];
/* 115 */     setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MersenneTwister(int[] seed) {
/* 123 */     this.mt = new int[624];
/* 124 */     setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MersenneTwister(long seed) {
/* 131 */     this.mt = new int[624];
/* 132 */     setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(int seed) {
/* 143 */     long longMT = seed;
/*     */     
/* 145 */     this.mt[0] = (int)longMT;
/* 146 */     for (this.mti = 1; this.mti < 624; this.mti++) {
/*     */ 
/*     */       
/* 149 */       longMT = 1812433253L * (longMT ^ longMT >> 30L) + this.mti & 0xFFFFFFFFL;
/* 150 */       this.mt[this.mti] = (int)longMT;
/*     */     } 
/*     */     
/* 153 */     clear();
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
/*     */   public void setSeed(int[] seed) {
/* 166 */     if (seed == null) {
/* 167 */       setSeed(System.currentTimeMillis() + System.identityHashCode(this));
/*     */       
/*     */       return;
/*     */     } 
/* 171 */     setSeed(19650218);
/* 172 */     int i = 1;
/* 173 */     int j = 0;
/*     */     int k;
/* 175 */     for (k = FastMath.max(624, seed.length); k != 0; k--) {
/* 176 */       long l0 = this.mt[i] & 0x7FFFFFFFL | ((this.mt[i] < 0) ? 2147483648L : 0L);
/* 177 */       long l1 = this.mt[i - 1] & 0x7FFFFFFFL | ((this.mt[i - 1] < 0) ? 2147483648L : 0L);
/* 178 */       long l = (l0 ^ (l1 ^ l1 >> 30L) * 1664525L) + seed[j] + j;
/* 179 */       this.mt[i] = (int)(l & 0xFFFFFFFFL);
/* 180 */       i++; j++;
/* 181 */       if (i >= 624) {
/* 182 */         this.mt[0] = this.mt[623];
/* 183 */         i = 1;
/*     */       } 
/* 185 */       if (j >= seed.length) {
/* 186 */         j = 0;
/*     */       }
/*     */     } 
/*     */     
/* 190 */     for (k = 623; k != 0; k--) {
/* 191 */       long l0 = this.mt[i] & 0x7FFFFFFFL | ((this.mt[i] < 0) ? 2147483648L : 0L);
/* 192 */       long l1 = this.mt[i - 1] & 0x7FFFFFFFL | ((this.mt[i - 1] < 0) ? 2147483648L : 0L);
/* 193 */       long l = (l0 ^ (l1 ^ l1 >> 30L) * 1566083941L) - i;
/* 194 */       this.mt[i] = (int)(l & 0xFFFFFFFFL);
/* 195 */       i++;
/* 196 */       if (i >= 624) {
/* 197 */         this.mt[0] = this.mt[623];
/* 198 */         i = 1;
/*     */       } 
/*     */     } 
/*     */     
/* 202 */     this.mt[0] = Integer.MIN_VALUE;
/*     */     
/* 204 */     clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(long seed) {
/* 215 */     setSeed(new int[] { (int)(seed >>> 32L), (int)(seed & 0xFFFFFFFFL) });
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
/*     */   protected int next(int bits) {
/* 232 */     if (this.mti >= 624) {
/* 233 */       int mtNext = this.mt[0]; int k;
/* 234 */       for (k = 0; k < 227; k++) {
/* 235 */         int mtCurr = mtNext;
/* 236 */         mtNext = this.mt[k + 1];
/* 237 */         int j = mtCurr & Integer.MIN_VALUE | mtNext & Integer.MAX_VALUE;
/* 238 */         this.mt[k] = this.mt[k + 397] ^ j >>> 1 ^ MAG01[j & 0x1];
/*     */       } 
/* 240 */       for (k = 227; k < 623; k++) {
/* 241 */         int mtCurr = mtNext;
/* 242 */         mtNext = this.mt[k + 1];
/* 243 */         int j = mtCurr & Integer.MIN_VALUE | mtNext & Integer.MAX_VALUE;
/* 244 */         this.mt[k] = this.mt[k + -227] ^ j >>> 1 ^ MAG01[j & 0x1];
/*     */       } 
/* 246 */       int i = mtNext & Integer.MIN_VALUE | this.mt[0] & Integer.MAX_VALUE;
/* 247 */       this.mt[623] = this.mt[396] ^ i >>> 1 ^ MAG01[i & 0x1];
/*     */       
/* 249 */       this.mti = 0;
/*     */     } 
/*     */     
/* 252 */     int y = this.mt[this.mti++];
/*     */ 
/*     */     
/* 255 */     y ^= y >>> 11;
/* 256 */     y ^= y << 7 & 0x9D2C5680;
/* 257 */     y ^= y << 15 & 0xEFC60000;
/* 258 */     y ^= y >>> 18;
/*     */     
/* 260 */     return y >>> 32 - bits;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/MersenneTwister.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */