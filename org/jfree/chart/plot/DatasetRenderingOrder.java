/*     */ package org.jfree.chart.plot;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DatasetRenderingOrder
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -600593412366385072L;
/*  62 */   public static final DatasetRenderingOrder FORWARD = new DatasetRenderingOrder("DatasetRenderingOrder.FORWARD");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final DatasetRenderingOrder REVERSE = new DatasetRenderingOrder("DatasetRenderingOrder.REVERSE");
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
/*     */   private DatasetRenderingOrder(String name) {
/*  81 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  91 */     return this.name;
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
/* 104 */     if (this == obj) {
/* 105 */       return true;
/*     */     }
/* 107 */     if (!(obj instanceof DatasetRenderingOrder)) {
/* 108 */       return false;
/*     */     }
/* 110 */     DatasetRenderingOrder order = (DatasetRenderingOrder)obj;
/* 111 */     if (!this.name.equals(order.toString())) {
/* 112 */       return false;
/*     */     }
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 124 */     return this.name.hashCode();
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
/* 135 */     if (equals(FORWARD)) {
/* 136 */       return FORWARD;
/*     */     }
/* 138 */     if (equals(REVERSE)) {
/* 139 */       return REVERSE;
/*     */     }
/* 141 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/DatasetRenderingOrder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */