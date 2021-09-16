/*     */ package org.apache.commons.math3.util;
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
/*     */ public class Pair<K, V>
/*     */ {
/*     */   private final K key;
/*     */   private final V value;
/*     */   
/*     */   public Pair(K k, V v) {
/*  45 */     this.key = k;
/*  46 */     this.value = v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pair(Pair<? extends K, ? extends V> entry) {
/*  55 */     this(entry.getKey(), entry.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K getKey() {
/*  64 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V getValue() {
/*  73 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K getFirst() {
/*  83 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V getSecond() {
/*  93 */     return this.value;
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
/*     */   public boolean equals(Object o) {
/* 105 */     if (this == o) {
/* 106 */       return true;
/*     */     }
/* 108 */     if (!(o instanceof Pair)) {
/* 109 */       return false;
/*     */     }
/* 111 */     Pair<?, ?> oP = (Pair<?, ?>)o;
/* 112 */     return (((this.key == null) ? (oP.key == null) : this.key.equals(oP.key)) && ((this.value == null) ? (oP.value == null) : this.value.equals(oP.value)));
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
/*     */   public int hashCode() {
/* 128 */     int result = (this.key == null) ? 0 : this.key.hashCode();
/*     */     
/* 130 */     int h = (this.value == null) ? 0 : this.value.hashCode();
/* 131 */     result = 37 * result + h ^ h >>> 16;
/*     */     
/* 133 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 139 */     return "[" + getKey() + ", " + getValue() + "]";
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
/*     */   public static <K, V> Pair<K, V> create(K k, V v) {
/* 154 */     return new Pair<K, V>(k, v);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/Pair.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */