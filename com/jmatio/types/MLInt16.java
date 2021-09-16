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
/*     */ public class MLInt16
/*     */   extends MLNumericArray<Short>
/*     */ {
/*     */   public MLInt16(String name, int[] dims, int type, int attributes) {
/*  22 */     super(name, dims, type, attributes);
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
/*     */   public MLInt16(String name, int[] dims) {
/*  34 */     super(name, dims, 10, 0);
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
/*     */   public MLInt16(String name, Short[] vals, int m) {
/*  47 */     super(name, 10, vals, m);
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
/*     */   public MLInt16(String name, short[][] vals) {
/*  61 */     this(name, short2DToShort(vals), vals.length);
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
/*     */   public MLInt16(String name, short[] vals, int m) {
/*  74 */     this(name, castToShort(vals), m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short[] createArray(int m, int n) {
/*  82 */     return new Short[m * n];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short[][] getArray() {
/*  92 */     short[][] result = new short[getM()][];
/*     */     
/*  94 */     for (int m = 0; m < getM(); m++) {
/*     */       
/*  96 */       result[m] = new short[getN()];
/*     */       
/*  98 */       for (int n = 0; n < getN(); n++)
/*     */       {
/* 100 */         result[m][n] = getReal(m, n).shortValue();
/*     */       }
/*     */     } 
/* 103 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Short[] castToShort(short[] d) {
/* 114 */     Short[] dest = new Short[d.length];
/* 115 */     for (int i = 0; i < d.length; i++)
/*     */     {
/* 117 */       dest[i] = Short.valueOf(d[i]);
/*     */     }
/* 119 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Short[] short2DToShort(short[][] dd) {
/* 130 */     Short[] d = new Short[dd.length * (dd[0]).length];
/* 131 */     for (int n = 0; n < (dd[0]).length; n++) {
/*     */       
/* 133 */       for (int m = 0; m < dd.length; m++)
/*     */       {
/* 135 */         d[m + n * dd.length] = Short.valueOf(dd[m][n]);
/*     */       }
/*     */     } 
/* 138 */     return d;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytesAllocated() {
/* 143 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public Short buldFromBytes(byte[] bytes) {
/* 148 */     if (bytes.length != getBytesAllocated())
/*     */     {
/* 150 */       throw new IllegalArgumentException("To build from byte array I need array of size: " + getBytesAllocated());
/*     */     }
/*     */ 
/*     */     
/* 154 */     return Short.valueOf(ByteBuffer.wrap(bytes).getShort());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getByteArray(Short value) {
/* 160 */     int byteAllocated = getBytesAllocated();
/* 161 */     ByteBuffer buff = ByteBuffer.allocate(byteAllocated);
/* 162 */     buff.putShort(value.shortValue());
/* 163 */     return buff.array();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<Short> getStorageClazz() {
/* 168 */     return Short.class;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLInt16.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */