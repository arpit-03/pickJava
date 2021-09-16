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
/*     */ public class ISAACRandom
/*     */   extends BitsStreamGenerator
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7288197941165002400L;
/*     */   private static final int SIZE_L = 8;
/*     */   private static final int SIZE = 256;
/*     */   private static final int H_SIZE = 128;
/*     */   private static final int MASK = 1020;
/*     */   private static final int GLD_RATIO = -1640531527;
/*  57 */   private final int[] rsl = new int[256];
/*     */   
/*  59 */   private final int[] mem = new int[256];
/*     */   
/*     */   private int count;
/*     */   
/*     */   private int isaacA;
/*     */   
/*     */   private int isaacB;
/*     */   
/*     */   private int isaacC;
/*     */   
/*  69 */   private final int[] arr = new int[8];
/*     */ 
/*     */ 
/*     */   
/*     */   private int isaacX;
/*     */ 
/*     */ 
/*     */   
/*     */   private int isaacI;
/*     */ 
/*     */   
/*     */   private int isaacJ;
/*     */ 
/*     */ 
/*     */   
/*     */   public ISAACRandom() {
/*  85 */     setSeed(System.currentTimeMillis() + System.identityHashCode(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISAACRandom(long seed) {
/*  94 */     setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISAACRandom(int[] seed) {
/* 104 */     setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(int seed) {
/* 110 */     setSeed(new int[] { seed });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(long seed) {
/* 116 */     setSeed(new int[] { (int)(seed >>> 32L), (int)(seed & 0xFFFFFFFFL) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(int[] seed) {
/* 122 */     if (seed == null) {
/* 123 */       setSeed(System.currentTimeMillis() + System.identityHashCode(this));
/*     */       return;
/*     */     } 
/* 126 */     int seedLen = seed.length;
/* 127 */     int rslLen = this.rsl.length;
/* 128 */     System.arraycopy(seed, 0, this.rsl, 0, FastMath.min(seedLen, rslLen));
/* 129 */     if (seedLen < rslLen) {
/* 130 */       for (int j = seedLen; j < rslLen; j++) {
/* 131 */         long k = this.rsl[j - seedLen];
/* 132 */         this.rsl[j] = (int)(1812433253L * (k ^ k >> 30L) + j & 0xFFFFFFFFL);
/*     */       } 
/*     */     }
/* 135 */     initState();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int next(int bits) {
/* 141 */     if (this.count < 0) {
/* 142 */       isaac();
/* 143 */       this.count = 255;
/*     */     } 
/* 145 */     return this.rsl[this.count--] >>> 32 - bits;
/*     */   }
/*     */ 
/*     */   
/*     */   private void isaac() {
/* 150 */     this.isaacI = 0;
/* 151 */     this.isaacJ = 128;
/* 152 */     this.isaacB += ++this.isaacC;
/* 153 */     while (this.isaacI < 128) {
/* 154 */       isaac2();
/*     */     }
/* 156 */     this.isaacJ = 0;
/* 157 */     while (this.isaacJ < 128) {
/* 158 */       isaac2();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void isaac2() {
/* 164 */     this.isaacX = this.mem[this.isaacI];
/* 165 */     this.isaacA ^= this.isaacA << 13;
/* 166 */     this.isaacA += this.mem[this.isaacJ++];
/* 167 */     isaac3();
/* 168 */     this.isaacX = this.mem[this.isaacI];
/* 169 */     this.isaacA ^= this.isaacA >>> 6;
/* 170 */     this.isaacA += this.mem[this.isaacJ++];
/* 171 */     isaac3();
/* 172 */     this.isaacX = this.mem[this.isaacI];
/* 173 */     this.isaacA ^= this.isaacA << 2;
/* 174 */     this.isaacA += this.mem[this.isaacJ++];
/* 175 */     isaac3();
/* 176 */     this.isaacX = this.mem[this.isaacI];
/* 177 */     this.isaacA ^= this.isaacA >>> 16;
/* 178 */     this.isaacA += this.mem[this.isaacJ++];
/* 179 */     isaac3();
/*     */   }
/*     */ 
/*     */   
/*     */   private void isaac3() {
/* 184 */     this.mem[this.isaacI] = this.mem[(this.isaacX & 0x3FC) >> 2] + this.isaacA + this.isaacB;
/* 185 */     this.isaacB = this.mem[(this.mem[this.isaacI] >> 8 & 0x3FC) >> 2] + this.isaacX;
/* 186 */     this.rsl[this.isaacI++] = this.isaacB;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initState() {
/* 191 */     this.isaacA = 0;
/* 192 */     this.isaacB = 0;
/* 193 */     this.isaacC = 0; int j;
/* 194 */     for (j = 0; j < this.arr.length; j++) {
/* 195 */       this.arr[j] = -1640531527;
/*     */     }
/* 197 */     for (j = 0; j < 4; j++) {
/* 198 */       shuffle();
/*     */     }
/*     */     
/* 201 */     for (j = 0; j < 256; j += 8) {
/* 202 */       this.arr[0] = this.arr[0] + this.rsl[j];
/* 203 */       this.arr[1] = this.arr[1] + this.rsl[j + 1];
/* 204 */       this.arr[2] = this.arr[2] + this.rsl[j + 2];
/* 205 */       this.arr[3] = this.arr[3] + this.rsl[j + 3];
/* 206 */       this.arr[4] = this.arr[4] + this.rsl[j + 4];
/* 207 */       this.arr[5] = this.arr[5] + this.rsl[j + 5];
/* 208 */       this.arr[6] = this.arr[6] + this.rsl[j + 6];
/* 209 */       this.arr[7] = this.arr[7] + this.rsl[j + 7];
/* 210 */       shuffle();
/* 211 */       setState(j);
/*     */     } 
/*     */     
/* 214 */     for (j = 0; j < 256; j += 8) {
/* 215 */       this.arr[0] = this.arr[0] + this.mem[j];
/* 216 */       this.arr[1] = this.arr[1] + this.mem[j + 1];
/* 217 */       this.arr[2] = this.arr[2] + this.mem[j + 2];
/* 218 */       this.arr[3] = this.arr[3] + this.mem[j + 3];
/* 219 */       this.arr[4] = this.arr[4] + this.mem[j + 4];
/* 220 */       this.arr[5] = this.arr[5] + this.mem[j + 5];
/* 221 */       this.arr[6] = this.arr[6] + this.mem[j + 6];
/* 222 */       this.arr[7] = this.arr[7] + this.mem[j + 7];
/* 223 */       shuffle();
/* 224 */       setState(j);
/*     */     } 
/* 226 */     isaac();
/* 227 */     this.count = 255;
/* 228 */     clear();
/*     */   }
/*     */ 
/*     */   
/*     */   private void shuffle() {
/* 233 */     this.arr[0] = this.arr[0] ^ this.arr[1] << 11;
/* 234 */     this.arr[3] = this.arr[3] + this.arr[0];
/* 235 */     this.arr[1] = this.arr[1] + this.arr[2];
/* 236 */     this.arr[1] = this.arr[1] ^ this.arr[2] >>> 2;
/* 237 */     this.arr[4] = this.arr[4] + this.arr[1];
/* 238 */     this.arr[2] = this.arr[2] + this.arr[3];
/* 239 */     this.arr[2] = this.arr[2] ^ this.arr[3] << 8;
/* 240 */     this.arr[5] = this.arr[5] + this.arr[2];
/* 241 */     this.arr[3] = this.arr[3] + this.arr[4];
/* 242 */     this.arr[3] = this.arr[3] ^ this.arr[4] >>> 16;
/* 243 */     this.arr[6] = this.arr[6] + this.arr[3];
/* 244 */     this.arr[4] = this.arr[4] + this.arr[5];
/* 245 */     this.arr[4] = this.arr[4] ^ this.arr[5] << 10;
/* 246 */     this.arr[7] = this.arr[7] + this.arr[4];
/* 247 */     this.arr[5] = this.arr[5] + this.arr[6];
/* 248 */     this.arr[5] = this.arr[5] ^ this.arr[6] >>> 4;
/* 249 */     this.arr[0] = this.arr[0] + this.arr[5];
/* 250 */     this.arr[6] = this.arr[6] + this.arr[7];
/* 251 */     this.arr[6] = this.arr[6] ^ this.arr[7] << 8;
/* 252 */     this.arr[1] = this.arr[1] + this.arr[6];
/* 253 */     this.arr[7] = this.arr[7] + this.arr[0];
/* 254 */     this.arr[7] = this.arr[7] ^ this.arr[0] >>> 9;
/* 255 */     this.arr[2] = this.arr[2] + this.arr[7];
/* 256 */     this.arr[0] = this.arr[0] + this.arr[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setState(int start) {
/* 264 */     this.mem[start] = this.arr[0];
/* 265 */     this.mem[start + 1] = this.arr[1];
/* 266 */     this.mem[start + 2] = this.arr[2];
/* 267 */     this.mem[start + 3] = this.arr[3];
/* 268 */     this.mem[start + 4] = this.arr[4];
/* 269 */     this.mem[start + 5] = this.arr[5];
/* 270 */     this.mem[start + 6] = this.arr[6];
/* 271 */     this.mem[start + 7] = this.arr[7];
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/ISAACRandom.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */