/*     */ package org.jfree.data;
/*     */ 
/*     */ import java.io.ObjectStreamException;
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
/*     */ 
/*     */ public final class RangeType
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -9073319010650549239L;
/*  56 */   public static final RangeType FULL = new RangeType("RangeType.FULL");
/*     */ 
/*     */   
/*  59 */   public static final RangeType POSITIVE = new RangeType("RangeType.POSITIVE");
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final RangeType NEGATIVE = new RangeType("RangeType.NEGATIVE");
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
/*     */   private RangeType(String name) {
/*  75 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  85 */     return this.name;
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
/*     */   public boolean equals(Object obj) {
/*  99 */     if (this == obj) {
/* 100 */       return true;
/*     */     }
/* 102 */     if (!(obj instanceof RangeType)) {
/* 103 */       return false;
/*     */     }
/* 105 */     RangeType that = (RangeType)obj;
/* 106 */     if (!this.name.equals(that.toString())) {
/* 107 */       return false;
/*     */     }
/* 109 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 119 */     return this.name.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws ObjectStreamException {
/* 130 */     if (equals(FULL)) {
/* 131 */       return FULL;
/*     */     }
/* 133 */     if (equals(POSITIVE)) {
/* 134 */       return POSITIVE;
/*     */     }
/* 136 */     if (equals(NEGATIVE)) {
/* 137 */       return NEGATIVE;
/*     */     }
/* 139 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/RangeType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */