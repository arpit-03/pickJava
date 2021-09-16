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
/*     */ public class MLDouble
/*     */   extends MLNumericArray<Double>
/*     */ {
/*     */   public MLDouble(String name, int[] dims, int type, int attributes) {
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
/*     */   public MLDouble(String name, int[] dims) {
/*  34 */     super(name, dims, 6, 0);
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
/*     */   public MLDouble(String name, Double[] vals, int m) {
/*  46 */     super(name, 6, vals, m);
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
/*     */   public MLDouble(String name, double[][] vals) {
/*  59 */     this(name, double2DToDouble(vals), vals.length);
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
/*     */   public MLDouble(String name, double[] vals, int m) {
/*  71 */     this(name, castToDouble(vals), m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double[] createArray(int m, int n) {
/*  78 */     return new Double[m * n];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getArray() {
/*  87 */     double[][] result = new double[getM()][];
/*     */     
/*  89 */     for (int m = 0; m < getM(); m++) {
/*     */       
/*  91 */       result[m] = new double[getN()];
/*     */       
/*  93 */       for (int n = 0; n < getN(); n++)
/*     */       {
/*  95 */         result[m][n] = getReal(m, n).doubleValue();
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
/*     */   private static Double[] castToDouble(double[] d) {
/* 108 */     Double[] dest = new Double[d.length];
/* 109 */     for (int i = 0; i < d.length; i++)
/*     */     {
/* 111 */       dest[i] = Double.valueOf(d[i]);
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
/*     */   private static Double[] double2DToDouble(double[][] dd) {
/* 123 */     Double[] d = new Double[dd.length * (dd[0]).length];
/* 124 */     for (int n = 0; n < (dd[0]).length; n++) {
/*     */       
/* 126 */       for (int m = 0; m < dd.length; m++)
/*     */       {
/* 128 */         d[m + n * dd.length] = Double.valueOf(dd[m][n]);
/*     */       }
/*     */     } 
/* 131 */     return d;
/*     */   }
/*     */   
/*     */   public int getBytesAllocated() {
/* 135 */     return 8;
/*     */   }
/*     */   
/*     */   public Double buldFromBytes(byte[] bytes) {
/* 139 */     if (bytes.length != getBytesAllocated())
/*     */     {
/* 141 */       throw new IllegalArgumentException("To build from byte array I need array of size: " + getBytesAllocated());
/*     */     }
/*     */ 
/*     */     
/* 145 */     return Double.valueOf(ByteBuffer.wrap(bytes).getDouble());
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getByteArray(Double value) {
/* 150 */     int byteAllocated = getBytesAllocated();
/* 151 */     ByteBuffer buff = ByteBuffer.allocate(byteAllocated);
/* 152 */     buff.putDouble(value.doubleValue());
/* 153 */     return buff.array();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<Double> getStorageClazz() {
/* 158 */     return Double.class;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLDouble.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */