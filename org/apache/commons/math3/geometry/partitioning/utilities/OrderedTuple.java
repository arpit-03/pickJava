/*     */ package org.apache.commons.math3.geometry.partitioning.utilities;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class OrderedTuple
/*     */   implements Comparable<OrderedTuple>
/*     */ {
/*     */   private static final long SIGN_MASK = -9223372036854775808L;
/*     */   private static final long EXPONENT_MASK = 9218868437227405312L;
/*     */   private static final long MANTISSA_MASK = 4503599627370495L;
/*     */   private static final long IMPLICIT_ONE = 4503599627370496L;
/*     */   private double[] components;
/*     */   private int offset;
/*     */   private int lsb;
/*     */   private long[] encoding;
/*     */   private boolean posInf;
/*     */   private boolean negInf;
/*     */   private boolean nan;
/*     */   
/*     */   public OrderedTuple(double... components) {
/* 135 */     this.components = (double[])components.clone();
/* 136 */     int msb = Integer.MIN_VALUE;
/* 137 */     this.lsb = Integer.MAX_VALUE;
/* 138 */     this.posInf = false;
/* 139 */     this.negInf = false;
/* 140 */     this.nan = false;
/* 141 */     for (int i = 0; i < components.length; i++) {
/* 142 */       if (Double.isInfinite(components[i])) {
/* 143 */         if (components[i] < 0.0D) {
/* 144 */           this.negInf = true;
/*     */         } else {
/* 146 */           this.posInf = true;
/*     */         } 
/* 148 */       } else if (Double.isNaN(components[i])) {
/* 149 */         this.nan = true;
/*     */       } else {
/* 151 */         long b = Double.doubleToLongBits(components[i]);
/* 152 */         long m = mantissa(b);
/* 153 */         if (m != 0L) {
/* 154 */           int e = exponent(b);
/* 155 */           msb = FastMath.max(msb, e + computeMSB(m));
/* 156 */           this.lsb = FastMath.min(this.lsb, e + computeLSB(m));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     if (this.posInf && this.negInf) {
/*     */       
/* 163 */       this.posInf = false;
/* 164 */       this.negInf = false;
/* 165 */       this.nan = true;
/*     */     } 
/*     */     
/* 168 */     if (this.lsb <= msb) {
/*     */       
/* 170 */       encode(msb + 16);
/*     */     } else {
/* 172 */       this.encoding = new long[] { 0L };
/*     */     } 
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
/*     */   private void encode(int minOffset) {
/* 186 */     this.offset = minOffset + 31;
/* 187 */     this.offset -= this.offset % 32;
/*     */     
/* 189 */     if (this.encoding != null && this.encoding.length == 1 && this.encoding[0] == 0L) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 196 */     int neededBits = this.offset + 1 - this.lsb;
/* 197 */     int neededLongs = (neededBits + 62) / 63;
/* 198 */     this.encoding = new long[this.components.length * neededLongs];
/*     */ 
/*     */     
/* 201 */     int eIndex = 0;
/* 202 */     int shift = 62;
/* 203 */     long word = 0L;
/* 204 */     for (int k = this.offset; eIndex < this.encoding.length; k--) {
/* 205 */       for (int vIndex = 0; vIndex < this.components.length; vIndex++) {
/* 206 */         if (getBit(vIndex, k) != 0) {
/* 207 */           word |= 1L << shift;
/*     */         }
/* 209 */         if (shift-- == 0) {
/* 210 */           this.encoding[eIndex++] = word;
/* 211 */           word = 0L;
/* 212 */           shift = 62;
/*     */         } 
/*     */       } 
/*     */     } 
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
/*     */   public int compareTo(OrderedTuple ot) {
/* 250 */     if (this.components.length == ot.components.length) {
/* 251 */       if (this.nan)
/* 252 */         return 1; 
/* 253 */       if (ot.nan)
/* 254 */         return -1; 
/* 255 */       if (this.negInf || ot.posInf)
/* 256 */         return -1; 
/* 257 */       if (this.posInf || ot.negInf) {
/* 258 */         return 1;
/*     */       }
/*     */       
/* 261 */       if (this.offset < ot.offset) {
/* 262 */         encode(ot.offset);
/* 263 */       } else if (this.offset > ot.offset) {
/* 264 */         ot.encode(this.offset);
/*     */       } 
/*     */       
/* 267 */       int limit = FastMath.min(this.encoding.length, ot.encoding.length);
/* 268 */       for (int i = 0; i < limit; i++) {
/* 269 */         if (this.encoding[i] < ot.encoding[i])
/* 270 */           return -1; 
/* 271 */         if (this.encoding[i] > ot.encoding[i]) {
/* 272 */           return 1;
/*     */         }
/*     */       } 
/*     */       
/* 276 */       if (this.encoding.length < ot.encoding.length)
/* 277 */         return -1; 
/* 278 */       if (this.encoding.length > ot.encoding.length) {
/* 279 */         return 1;
/*     */       }
/* 281 */       return 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 287 */     return this.components.length - ot.components.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 294 */     if (this == other)
/* 295 */       return true; 
/* 296 */     if (other instanceof OrderedTuple) {
/* 297 */       return (compareTo((OrderedTuple)other) == 0);
/*     */     }
/* 299 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 307 */     int multiplier = 37;
/* 308 */     int trueHash = 97;
/* 309 */     int falseHash = 71;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     int hash = Arrays.hashCode(this.components);
/* 315 */     hash = hash * 37 + this.offset;
/* 316 */     hash = hash * 37 + this.lsb;
/* 317 */     hash = hash * 37 + (this.posInf ? 97 : 71);
/* 318 */     hash = hash * 37 + (this.negInf ? 97 : 71);
/* 319 */     hash = hash * 37 + (this.nan ? 97 : 71);
/*     */     
/* 321 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getComponents() {
/* 329 */     return (double[])this.components.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long sign(long bits) {
/* 337 */     return bits & Long.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int exponent(long bits) {
/* 345 */     return (int)((bits & 0x7FF0000000000000L) >> 52L) - 1075;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long mantissa(long bits) {
/* 353 */     return ((bits & 0x7FF0000000000000L) == 0L) ? ((bits & 0xFFFFFFFFFFFFFL) << 1L) : (0x10000000000000L | bits & 0xFFFFFFFFFFFFFL);
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
/*     */   private static int computeMSB(long l) {
/* 366 */     long ll = l;
/* 367 */     long mask = 4294967295L;
/* 368 */     int scale = 32;
/* 369 */     int msb = 0;
/*     */     
/* 371 */     while (scale != 0) {
/* 372 */       if ((ll & mask) != ll) {
/* 373 */         msb |= scale;
/* 374 */         ll >>= scale;
/*     */       } 
/* 376 */       scale >>= 1;
/* 377 */       mask >>= scale;
/*     */     } 
/*     */     
/* 380 */     return msb;
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
/*     */   private static int computeLSB(long l) {
/* 392 */     long ll = l;
/* 393 */     long mask = -4294967296L;
/* 394 */     int scale = 32;
/* 395 */     int lsb = 0;
/*     */     
/* 397 */     while (scale != 0) {
/* 398 */       if ((ll & mask) == ll) {
/* 399 */         lsb |= scale;
/* 400 */         ll >>= scale;
/*     */       } 
/* 402 */       scale >>= 1;
/* 403 */       mask >>= scale;
/*     */     } 
/*     */     
/* 406 */     return lsb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getBit(int i, int k) {
/* 417 */     long bits = Double.doubleToLongBits(this.components[i]);
/* 418 */     int e = exponent(bits);
/* 419 */     if (k < e || k > this.offset)
/* 420 */       return 0; 
/* 421 */     if (k == this.offset)
/* 422 */       return (sign(bits) == 0L) ? 1 : 0; 
/* 423 */     if (k > e + 52) {
/* 424 */       return (sign(bits) == 0L) ? 0 : 1;
/*     */     }
/* 426 */     long m = (sign(bits) == 0L) ? mantissa(bits) : -mantissa(bits);
/* 427 */     return (int)(m >> k - e & 0x1L);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/utilities/OrderedTuple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */