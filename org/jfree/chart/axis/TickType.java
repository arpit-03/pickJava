/*     */ package org.jfree.chart.axis;
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
/*     */ public final class TickType
/*     */   implements Serializable
/*     */ {
/*  54 */   public static final TickType MAJOR = new TickType("MAJOR");
/*     */ 
/*     */   
/*  57 */   public static final TickType MINOR = new TickType("MINOR");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TickType(String name) {
/*  68 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  78 */     return this.name;
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
/*  91 */     if (this == obj) {
/*  92 */       return true;
/*     */     }
/*  94 */     if (!(obj instanceof TickType)) {
/*  95 */       return false;
/*     */     }
/*     */     
/*  98 */     TickType that = (TickType)obj;
/*  99 */     if (!this.name.equals(that.name)) {
/* 100 */       return false;
/*     */     }
/* 102 */     return true;
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
/* 113 */     Object result = null;
/* 114 */     if (equals(MAJOR)) {
/* 115 */       result = MAJOR;
/*     */     }
/* 117 */     else if (equals(MINOR)) {
/* 118 */       result = MINOR;
/*     */     } 
/* 120 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/TickType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */