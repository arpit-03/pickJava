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
/*     */ public class MLUInt8
/*     */   extends MLNumericArray<Byte>
/*     */ {
/*     */   public MLUInt8(String name, int[] dims, int type, int attributes) {
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
/*     */   public MLUInt8(String name, int[] dims) {
/*  34 */     super(name, dims, 9, 0);
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
/*     */   public MLUInt8(String name, Byte[] vals, int m) {
/*  46 */     super(name, 9, vals, m);
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
/*     */   public MLUInt8(String name, byte[][] vals) {
/*  59 */     this(name, byte2DToByte(vals), vals.length);
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
/*     */   public MLUInt8(String name, byte[] vals, int m) {
/*  71 */     this(name, castToByte(vals), m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte[] createArray(int m, int n) {
/*  78 */     return new Byte[m * n];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[][] getArray() {
/*  87 */     byte[][] result = new byte[getM()][];
/*     */     
/*  89 */     for (int m = 0; m < getM(); m++) {
/*     */       
/*  91 */       result[m] = new byte[getN()];
/*     */       
/*  93 */       for (int n = 0; n < getN(); n++)
/*     */       {
/*  95 */         result[m][n] = getReal(m, n).byteValue();
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
/*     */   private static Byte[] castToByte(byte[] d) {
/* 108 */     Byte[] dest = new Byte[d.length];
/* 109 */     for (int i = 0; i < d.length; i++)
/*     */     {
/* 111 */       dest[i] = Byte.valueOf(d[i]);
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
/*     */   private static Byte[] byte2DToByte(byte[][] dd) {
/* 123 */     Byte[] d = new Byte[dd.length * (dd[0]).length];
/* 124 */     for (int n = 0; n < (dd[0]).length; n++) {
/*     */       
/* 126 */       for (int m = 0; m < dd.length; m++)
/*     */       {
/* 128 */         d[m + n * dd.length] = Byte.valueOf(dd[m][n]);
/*     */       }
/*     */     } 
/* 131 */     return d;
/*     */   }
/*     */   
/*     */   public Byte buldFromBytes(byte[] bytes) {
/* 135 */     if (bytes.length != getBytesAllocated())
/*     */     {
/* 137 */       throw new IllegalArgumentException("To build from byte array I need array of size: " + getBytesAllocated());
/*     */     }
/*     */ 
/*     */     
/* 141 */     return Byte.valueOf(bytes[0]);
/*     */   }
/*     */   
/*     */   public byte[] getByteArray(Byte value) {
/* 145 */     return new byte[] { value.byteValue() };
/*     */   }
/*     */   
/*     */   public int getBytesAllocated() {
/* 149 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<Byte> getStorageClazz() {
/* 154 */     return Byte.class;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Byte _get(ByteBuffer buffer, int index) {
/* 165 */     return Byte.valueOf(buffer.get(index));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLUInt8.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */