/*     */ package com.jmatio.types;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MLInt64
/*     */   extends MLNumericArray<Long>
/*     */ {
/*     */   public MLInt64(String name, int[] dims, int type, int attributes) {
/*  23 */     super(name, dims, type, attributes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MLInt64(String name, int[] dims) {
/*  34 */     super(name, dims, 14, 0);
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
/*     */   public MLInt64(String name, Long[] vals, int m) {
/*  46 */     super(name, 14, vals, m);
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
/*     */   public MLInt64(String name, long[][] vals) {
/*  59 */     this(name, long2DToLong(vals), vals.length);
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
/*     */   public MLInt64(String name, long[] vals, int m) {
/*  71 */     this(name, castToLong(vals), m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long[] createArray(int m, int n) {
/*  78 */     return new Long[m * n];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long[][] getArray() {
/*  87 */     long[][] result = new long[getM()][];
/*     */     
/*  89 */     for (int m = 0; m < getM(); m++) {
/*     */       
/*  91 */       result[m] = new long[getN()];
/*     */       
/*  93 */       for (int n = 0; n < getN(); n++)
/*     */       {
/*  95 */         result[m][n] = getReal(m, n).longValue();
/*     */       }
/*     */     } 
/*  98 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Long[] castToLong(long[] d) {
/* 108 */     Long[] dest = new Long[d.length];
/* 109 */     for (int i = 0; i < d.length; i++)
/*     */     {
/* 111 */       dest[i] = Long.valueOf(d[i]);
/*     */     }
/* 113 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Long[] long2DToLong(long[][] dd) {
/* 123 */     Long[] d = new Long[dd.length * (dd[0]).length];
/* 124 */     for (int n = 0; n < (dd[0]).length; n++) {
/*     */       
/* 126 */       for (int m = 0; m < dd.length; m++)
/*     */       {
/* 128 */         d[m + n * dd.length] = Long.valueOf(dd[m][n]);
/*     */       }
/*     */     } 
/* 131 */     return d;
/*     */   }
/*     */   
/*     */   public Long buldFromBytes(byte[] bytes) {
/* 135 */     if (bytes.length != getBytesAllocated())
/*     */     {
/* 137 */       throw new IllegalArgumentException("To build from byte array I need array of size: " + getBytesAllocated());
/*     */     }
/*     */ 
/*     */     
/* 141 */     return Long.valueOf(ByteBuffer.wrap(bytes).getLong());
/*     */   }
/*     */   
/*     */   public int getBytesAllocated() {
/* 145 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<Long> getStorageClazz() {
/* 150 */     return Long.class;
/*     */   }
/*     */   
/*     */   public byte[] getByteArray(Long value) {
/* 154 */     int byteAllocated = getBytesAllocated();
/* 155 */     ByteBuffer buff = ByteBuffer.allocate(byteAllocated);
/* 156 */     buff.putLong(value.longValue());
/* 157 */     return buff.array();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLInt64.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */