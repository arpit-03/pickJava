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
/*     */ public final class DomainOrder
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4902774943512072627L;
/*  55 */   public static final DomainOrder NONE = new DomainOrder("DomainOrder.NONE");
/*     */ 
/*     */   
/*  58 */   public static final DomainOrder ASCENDING = new DomainOrder("DomainOrder.ASCENDING");
/*     */ 
/*     */ 
/*     */   
/*  62 */   public static final DomainOrder DESCENDING = new DomainOrder("DomainOrder.DESCENDING");
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
/*     */   private DomainOrder(String name) {
/*  74 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  84 */     return this.name;
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
/*     */   public boolean equals(Object obj) {
/*  97 */     if (this == obj) {
/*  98 */       return true;
/*     */     }
/* 100 */     if (!(obj instanceof DomainOrder)) {
/* 101 */       return false;
/*     */     }
/* 103 */     DomainOrder that = (DomainOrder)obj;
/* 104 */     if (!this.name.equals(that.toString())) {
/* 105 */       return false;
/*     */     }
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 117 */     return this.name.hashCode();
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
/* 128 */     if (equals(ASCENDING)) {
/* 129 */       return ASCENDING;
/*     */     }
/* 131 */     if (equals(DESCENDING)) {
/* 132 */       return DESCENDING;
/*     */     }
/* 134 */     if (equals(NONE)) {
/* 135 */       return NONE;
/*     */     }
/* 137 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/DomainOrder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */