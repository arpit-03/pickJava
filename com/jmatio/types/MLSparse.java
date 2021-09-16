/*     */ package com.jmatio.types;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.SortedMap;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MLSparse
/*     */   extends MLNumericArray<Double>
/*     */ {
/*     */   int nzmax;
/*     */   private SortedSet<IndexMN> indexSet;
/*     */   private SortedMap<IndexMN, Double> real;
/*     */   private SortedMap<IndexMN, Double> imaginary;
/*     */   
/*     */   public MLSparse(String name, int[] dims, int attributes, int nzmax) {
/*  24 */     super(name, dims, 5, attributes);
/*  25 */     this.nzmax = nzmax;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void allocate() {
/*  30 */     this.real = new TreeMap<IndexMN, Double>();
/*  31 */     if (isComplex())
/*     */     {
/*  33 */       this.imaginary = new TreeMap<IndexMN, Double>();
/*     */     }
/*  35 */     this.indexSet = new TreeSet<IndexMN>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxNZ() {
/*  45 */     return this.nzmax;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getIR() {
/*  55 */     int[] ir = new int[this.nzmax];
/*  56 */     int i = 0;
/*  57 */     for (IndexMN index : this.indexSet)
/*     */     {
/*  59 */       ir[i++] = index.m;
/*     */     }
/*  61 */     return ir;
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
/*     */   public int[] getJC() {
/*  77 */     int[] jc = new int[getN() + 1];
/*     */     
/*  79 */     for (IndexMN index : this.indexSet) {
/*     */       
/*  81 */       for (int column = index.n + 1; column < jc.length; column++)
/*     */       {
/*  83 */         jc[column] = jc[column] + 1;
/*     */       }
/*     */     } 
/*  86 */     return jc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double[] createArray(int m, int n) {
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double getReal(int m, int n) {
/* 101 */     IndexMN i = new IndexMN(m, n);
/* 102 */     if (this.real.containsKey(i))
/*     */     {
/* 104 */       return this.real.get(i);
/*     */     }
/* 106 */     return new Double(0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double getReal(int index) {
/* 114 */     throw new IllegalArgumentException("Can't get Sparse array elements by index. Please use getReal(int index) instead.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReal(Double value, int m, int n) {
/* 124 */     IndexMN i = new IndexMN(m, n);
/* 125 */     this.indexSet.add(i);
/* 126 */     this.real.put(i, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReal(Double value, int index) {
/* 134 */     throw new IllegalArgumentException("Can't set Sparse array elements by index. Please use setReal(Double value, int m, int n) instead.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImaginary(Double value, int m, int n) {
/* 144 */     IndexMN i = new IndexMN(m, n);
/* 145 */     this.indexSet.add(i);
/* 146 */     this.imaginary.put(i, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImaginary(Double value, int index) {
/* 154 */     throw new IllegalArgumentException("Can't set Sparse array elements by index. Please use setImaginary(Double value, int m, int n) instead.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double getImaginary(int m, int n) {
/* 162 */     IndexMN i = new IndexMN(m, n);
/* 163 */     if (this.imaginary.containsKey(i))
/*     */     {
/* 165 */       return this.imaginary.get(i);
/*     */     }
/* 167 */     return new Double(0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double getImaginary(int index) {
/* 174 */     throw new IllegalArgumentException("Can't get Sparse array elements by index. Please use getImaginary(int index) instead.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double[] exportReal() {
/* 185 */     Double[] ad = new Double[this.indexSet.size()];
/* 186 */     int i = 0;
/* 187 */     for (IndexMN index : this.indexSet) {
/* 188 */       if (this.real.containsKey(index)) {
/* 189 */         ad[i] = this.real.get(index);
/*     */       } else {
/* 191 */         ad[i] = Double.valueOf(0.0D);
/*     */       } 
/* 193 */       i++;
/*     */     } 
/* 195 */     return ad;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double[] exportImaginary() {
/* 205 */     Double[] ad = new Double[this.indexSet.size()];
/* 206 */     int i = 0;
/* 207 */     for (IndexMN index : this.indexSet) {
/* 208 */       if (this.imaginary.containsKey(index)) {
/* 209 */         ad[i] = this.imaginary.get(index);
/*     */       } else {
/* 211 */         ad[i] = Double.valueOf(0.0D);
/*     */       } 
/* 213 */       i++;
/*     */     } 
/* 215 */     return ad;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String contentToString() {
/* 222 */     StringBuffer sb = new StringBuffer();
/* 223 */     sb.append(this.name + " = \n");
/*     */     
/* 225 */     for (IndexMN i : this.indexSet) {
/*     */       
/* 227 */       sb.append("\t(");
/* 228 */       sb.append(i.m + "," + i.n);
/* 229 */       sb.append(")");
/* 230 */       sb.append("\t" + getReal(i.m, i.n));
/* 231 */       if (isComplex())
/*     */       {
/* 233 */         sb.append("+" + getImaginary(i.m, i.n));
/*     */       }
/* 235 */       sb.append("\n");
/*     */     } 
/*     */ 
/*     */     
/* 239 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class IndexMN
/*     */     implements Comparable<IndexMN>
/*     */   {
/*     */     int m;
/*     */ 
/*     */     
/*     */     int n;
/*     */ 
/*     */     
/*     */     public IndexMN(int m, int n) {
/* 254 */       this.m = m;
/* 255 */       this.n = n;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(IndexMN anOtherIndex) {
/* 263 */       return MLSparse.this.getIndex(this.m, this.n) - MLSparse.this.getIndex(anOtherIndex.m, anOtherIndex.n);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 271 */       if (o instanceof IndexMN)
/*     */       {
/* 273 */         return (this.m == ((IndexMN)o).m && this.n == ((IndexMN)o).n);
/*     */       }
/* 275 */       return super.equals(o);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 282 */       StringBuffer sb = new StringBuffer();
/* 283 */       sb.append("{");
/* 284 */       sb.append("m=" + this.m);
/* 285 */       sb.append(", ");
/* 286 */       sb.append("n=" + this.n);
/* 287 */       sb.append("}");
/* 288 */       return sb.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytesAllocated() {
/* 294 */     return 512;
/*     */   }
/*     */   
/*     */   public Double buldFromBytes(byte[] bytes) {
/* 298 */     if (bytes.length != getBytesAllocated())
/*     */     {
/* 300 */       throw new IllegalArgumentException("To build from byte array I need array of size: " + getBytesAllocated());
/*     */     }
/*     */ 
/*     */     
/* 304 */     return Double.valueOf(ByteBuffer.wrap(bytes).getDouble());
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getByteArray(Double value) {
/* 309 */     int byteAllocated = getBytesAllocated();
/* 310 */     ByteBuffer buff = ByteBuffer.allocate(byteAllocated);
/* 311 */     buff.putDouble(value.doubleValue());
/* 312 */     return buff.array();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<Double> getStorageClazz() {
/* 317 */     return Double.class;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLSparse.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */