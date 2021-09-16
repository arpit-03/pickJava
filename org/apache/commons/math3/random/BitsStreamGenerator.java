/*     */ package org.apache.commons.math3.random;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
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
/*     */ public abstract class BitsStreamGenerator
/*     */   implements RandomGenerator, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20130104L;
/*  41 */   private double nextGaussian = Double.NaN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setSeed(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setSeed(int[] paramArrayOfint);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setSeed(long paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int next(int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean nextBoolean() {
/*  66 */     return (next(1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public double nextDouble() {
/*  71 */     long high = next(26) << 26L;
/*  72 */     int low = next(26);
/*  73 */     return (high | low) * 2.220446049250313E-16D;
/*     */   }
/*     */ 
/*     */   
/*     */   public float nextFloat() {
/*  78 */     return next(23) * 1.1920929E-7F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextGaussian() {
/*     */     double random;
/*  85 */     if (Double.isNaN(this.nextGaussian)) {
/*     */       
/*  87 */       double x = nextDouble();
/*  88 */       double y = nextDouble();
/*  89 */       double alpha = 6.283185307179586D * x;
/*  90 */       double r = FastMath.sqrt(-2.0D * FastMath.log(y));
/*  91 */       random = r * FastMath.cos(alpha);
/*  92 */       this.nextGaussian = r * FastMath.sin(alpha);
/*     */     } else {
/*     */       
/*  95 */       random = this.nextGaussian;
/*  96 */       this.nextGaussian = Double.NaN;
/*     */     } 
/*     */     
/*  99 */     return random;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int nextInt() {
/* 105 */     return next(32);
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
/*     */   public int nextInt(int n) throws IllegalArgumentException {
/* 124 */     if (n > 0) {
/* 125 */       if ((n & -n) == n) {
/* 126 */         return (int)(n * next(31) >> 31L);
/*     */       }
/*     */ 
/*     */       
/*     */       while (true) {
/* 131 */         int bits = next(31);
/* 132 */         int val = bits % n;
/* 133 */         if (bits - val + n - 1 >= 0)
/* 134 */           return val; 
/*     */       } 
/* 136 */     }  throw new NotStrictlyPositiveException(Integer.valueOf(n));
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextLong() {
/* 141 */     long high = next(32) << 32L;
/* 142 */     long low = next(32) & 0xFFFFFFFFL;
/* 143 */     return high | low;
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
/*     */   public long nextLong(long n) throws IllegalArgumentException {
/* 158 */     if (n > 0L)
/*     */     {
/*     */       while (true) {
/*     */         
/* 162 */         long bits = next(31) << 32L;
/* 163 */         bits |= next(32) & 0xFFFFFFFFL;
/* 164 */         long val = bits % n;
/* 165 */         if (bits - val + n - 1L >= 0L)
/* 166 */           return val; 
/*     */       }  } 
/* 168 */     throw new NotStrictlyPositiveException(Long.valueOf(n));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 176 */     this.nextGaussian = Double.NaN;
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
/*     */   public void nextBytes(byte[] bytes) {
/* 191 */     nextBytesFill(bytes, 0, bytes.length);
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
/*     */   public void nextBytes(byte[] bytes, int start, int len) {
/* 212 */     if (start < 0 || start >= bytes.length)
/*     */     {
/* 214 */       throw new OutOfRangeException(Integer.valueOf(start), Integer.valueOf(0), Integer.valueOf(bytes.length));
/*     */     }
/* 216 */     if (len < 0 || len > bytes.length - start)
/*     */     {
/* 218 */       throw new OutOfRangeException(Integer.valueOf(len), Integer.valueOf(0), Integer.valueOf(bytes.length - start));
/*     */     }
/*     */     
/* 221 */     nextBytesFill(bytes, start, len);
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
/*     */   private void nextBytesFill(byte[] bytes, int start, int len) {
/* 240 */     int index = start;
/*     */ 
/*     */ 
/*     */     
/* 244 */     int indexLoopLimit = index + (len & 0x7FFFFFFC);
/*     */ 
/*     */     
/* 247 */     while (index < indexLoopLimit) {
/* 248 */       int random = next(32);
/* 249 */       bytes[index++] = (byte)random;
/* 250 */       bytes[index++] = (byte)(random >>> 8);
/* 251 */       bytes[index++] = (byte)(random >>> 16);
/* 252 */       bytes[index++] = (byte)(random >>> 24);
/*     */     } 
/*     */     
/* 255 */     int indexLimit = start + len;
/*     */ 
/*     */     
/* 258 */     if (index < indexLimit) {
/* 259 */       int random = next(32);
/*     */       while (true) {
/* 261 */         bytes[index++] = (byte)random;
/* 262 */         if (index < indexLimit) {
/* 263 */           random >>>= 8;
/*     */           continue;
/*     */         } 
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/random/BitsStreamGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */