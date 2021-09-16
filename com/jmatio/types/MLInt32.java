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
/*     */ public class MLInt32
/*     */   extends MLNumericArray<Integer>
/*     */ {
/*     */   public MLInt32(String name, int[] dims, int type, int attributes) {
/*  19 */     super(name, dims, type, attributes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MLInt32(String name, int[] dims) {
/*  30 */     super(name, dims, 12, 0);
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
/*     */   public MLInt32(String name, Integer[] vals, int m) {
/*  42 */     super(name, 12, vals, m);
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
/*     */   public MLInt32(String name, int[][] vals) {
/*  55 */     this(name, int2DToInteger(vals), vals.length);
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
/*     */   public MLInt32(String name, int[] vals, int m) {
/*  67 */     this(name, castToInteger(vals), m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer[] createArray(int m, int n) {
/*  74 */     return new Integer[m * n];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[][] getArray() {
/*  83 */     int[][] result = new int[getM()][];
/*     */     
/*  85 */     for (int m = 0; m < getM(); m++) {
/*     */       
/*  87 */       result[m] = new int[getN()];
/*     */       
/*  89 */       for (int n = 0; n < getN(); n++)
/*     */       {
/*  91 */         result[m][n] = getReal(m, n).intValue();
/*     */       }
/*     */     } 
/*  94 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Integer[] castToInteger(int[] d) {
/* 104 */     Integer[] dest = new Integer[d.length];
/* 105 */     for (int i = 0; i < d.length; i++)
/*     */     {
/* 107 */       dest[i] = Integer.valueOf(d[i]);
/*     */     }
/* 109 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Integer[] int2DToInteger(int[][] dd) {
/* 119 */     Integer[] d = new Integer[dd.length * (dd[0]).length];
/* 120 */     for (int n = 0; n < (dd[0]).length; n++) {
/*     */       
/* 122 */       for (int m = 0; m < dd.length; m++)
/*     */       {
/* 124 */         d[m + n * dd.length] = Integer.valueOf(dd[m][n]);
/*     */       }
/*     */     } 
/* 127 */     return d;
/*     */   }
/*     */   
/*     */   public Integer buldFromBytes(byte[] bytes) {
/* 131 */     if (bytes.length != getBytesAllocated())
/*     */     {
/* 133 */       throw new IllegalArgumentException("To build from byte array I need array of size: " + getBytesAllocated());
/*     */     }
/*     */ 
/*     */     
/* 137 */     return Integer.valueOf(ByteBuffer.wrap(bytes).getInt());
/*     */   }
/*     */   
/*     */   public int getBytesAllocated() {
/* 141 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<Integer> getStorageClazz() {
/* 146 */     return Integer.class;
/*     */   }
/*     */   
/*     */   public byte[] getByteArray(Integer value) {
/* 150 */     int byteAllocated = getBytesAllocated();
/* 151 */     ByteBuffer buff = ByteBuffer.allocate(byteAllocated);
/* 152 */     buff.putInt(value.intValue());
/* 153 */     return buff.array();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLInt32.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */