/*     */ package org.jfree.data;
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
/*     */ 
/*     */ 
/*     */ public final class KeyedValueComparatorType
/*     */   implements Serializable
/*     */ {
/*  53 */   public static final KeyedValueComparatorType BY_KEY = new KeyedValueComparatorType("KeyedValueComparatorType.BY_KEY");
/*     */ 
/*     */ 
/*     */   
/*  57 */   public static final KeyedValueComparatorType BY_VALUE = new KeyedValueComparatorType("KeyedValueComparatorType.BY_VALUE");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private KeyedValueComparatorType(String name) {
/*  69 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  79 */     return this.name;
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
/*     */   public boolean equals(Object o) {
/*  92 */     if (this == o) {
/*  93 */       return true;
/*     */     }
/*  95 */     if (!(o instanceof KeyedValueComparatorType)) {
/*  96 */       return false;
/*     */     }
/*     */     
/*  99 */     KeyedValueComparatorType type = (KeyedValueComparatorType)o;
/* 100 */     if (!this.name.equals(type.name)) {
/* 101 */       return false;
/*     */     }
/*     */     
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 114 */     return this.name.hashCode();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/KeyedValueComparatorType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */