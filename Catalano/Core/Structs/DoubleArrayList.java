/*     */ package Catalano.Core.Structs;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public final class DoubleArrayList
/*     */   implements Serializable
/*     */ {
/*     */   private double[] data;
/*     */   private int size;
/*     */   private static final int DEFAULT_CAPACITY = 10;
/*     */   
/*     */   public DoubleArrayList() {
/*  56 */     this(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayList(int capacity) {
/*  66 */     this.data = new double[capacity];
/*  67 */     this.size = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayList(double[] values) {
/*  77 */     this(Math.max(values.length, 10));
/*  78 */     add(values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(int capacity) {
/*  89 */     if (capacity > this.data.length) {
/*  90 */       int newCap = Math.max(this.data.length << 1, capacity);
/*  91 */       double[] tmp = new double[newCap];
/*  92 */       System.arraycopy(this.data, 0, tmp, 0, this.data.length);
/*  93 */       this.data = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 103 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 112 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trimToSize() {
/* 119 */     if (this.data.length > size()) {
/* 120 */       double[] tmp = toArray();
/* 121 */       this.data = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(double val) {
/* 131 */     ensureCapacity(this.size + 1);
/* 132 */     this.data[this.size++] = val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(double[] vals) {
/* 141 */     ensureCapacity(this.size + vals.length);
/* 142 */     System.arraycopy(vals, 0, this.data, this.size, vals.length);
/* 143 */     this.size += vals.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int index) {
/* 153 */     return this.data[index];
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
/*     */   public void set(int index, double val) {
/* 165 */     if (index < 0 || index >= this.size) {
/* 166 */       throw new IndexOutOfBoundsException(String.valueOf(index));
/*     */     }
/* 168 */     this.data[index] = val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 176 */     this.size = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double remove(int index) {
/* 187 */     if (index < 0 || index >= this.size) {
/* 188 */       throw new IndexOutOfBoundsException(String.valueOf(index));
/*     */     }
/*     */     
/* 191 */     double old = get(index);
/*     */     
/* 193 */     if (index == 0) {
/*     */       
/* 195 */       System.arraycopy(this.data, 1, this.data, 0, this.size - 1);
/* 196 */     } else if (this.size - 1 != index) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 201 */       System.arraycopy(this.data, index + 1, this.data, index, this.size - index + 1);
/*     */     } 
/*     */     
/* 204 */     this.size--;
/* 205 */     return old;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] toArray() {
/* 214 */     return toArray(null);
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
/*     */   public double[] toArray(double[] dest) {
/* 229 */     if (dest == null || dest.length < size()) {
/* 230 */       dest = new double[this.size];
/*     */     }
/*     */     
/* 233 */     System.arraycopy(this.data, 0, dest, 0, this.size);
/* 234 */     return dest;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Core/Structs/DoubleArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */