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
/*     */ public class MLInt8
/*     */   extends MLNumericArray<Byte>
/*     */ {
/*     */   public MLInt8(String name, int[] dims, int type, int attributes) {
/*  18 */     super(name, dims, type, attributes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MLInt8(String name, int[] dims) {
/*  29 */     super(name, dims, 8, 0);
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
/*     */   public MLInt8(String name, Byte[] vals, int m) {
/*  41 */     super(name, 8, vals, m);
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
/*     */   public MLInt8(String name, byte[][] vals) {
/*  54 */     this(name, byte2DToByte(vals), vals.length);
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
/*     */   public MLInt8(String name, byte[] vals, int m) {
/*  66 */     this(name, castToByte(vals), m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte[] createArray(int m, int n) {
/*  73 */     return new Byte[m * n];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[][] getArray() {
/*  82 */     byte[][] result = new byte[getM()][];
/*     */     
/*  84 */     for (int m = 0; m < getM(); m++) {
/*     */       
/*  86 */       result[m] = new byte[getN()];
/*     */       
/*  88 */       for (int n = 0; n < getN(); n++)
/*     */       {
/*  90 */         result[m][n] = getReal(m, n).byteValue();
/*     */       }
/*     */     } 
/*  93 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Byte[] castToByte(byte[] d) {
/* 103 */     Byte[] dest = new Byte[d.length];
/* 104 */     for (int i = 0; i < d.length; i++)
/*     */     {
/* 106 */       dest[i] = Byte.valueOf(d[i]);
/*     */     }
/* 108 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Byte[] byte2DToByte(byte[][] dd) {
/* 118 */     Byte[] d = new Byte[dd.length * (dd[0]).length];
/* 119 */     for (int n = 0; n < (dd[0]).length; n++) {
/*     */       
/* 121 */       for (int m = 0; m < dd.length; m++)
/*     */       {
/* 123 */         d[m + n * dd.length] = Byte.valueOf(dd[m][n]);
/*     */       }
/*     */     } 
/* 126 */     return d;
/*     */   }
/*     */   
/*     */   public Byte buldFromBytes(byte[] bytes) {
/* 130 */     if (bytes.length != getBytesAllocated())
/*     */     {
/* 132 */       throw new IllegalArgumentException("To build from byte array I need array of size: " + getBytesAllocated());
/*     */     }
/*     */ 
/*     */     
/* 136 */     return Byte.valueOf(bytes[0]);
/*     */   }
/*     */   
/*     */   public byte[] getByteArray(Byte value) {
/* 140 */     return new byte[] { value.byteValue() };
/*     */   }
/*     */   
/*     */   public int getBytesAllocated() {
/* 144 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<Byte> getStorageClazz() {
/* 149 */     return Byte.class;
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
/* 160 */     return Byte.valueOf(buffer.get(index));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLInt8.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */