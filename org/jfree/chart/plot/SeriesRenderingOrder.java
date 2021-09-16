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
/*     */ public final class SeriesRenderingOrder
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 209336477448807735L;
/*  60 */   public static final SeriesRenderingOrder FORWARD = new SeriesRenderingOrder("SeriesRenderingOrder.FORWARD");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public static final SeriesRenderingOrder REVERSE = new SeriesRenderingOrder("SeriesRenderingOrder.REVERSE");
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
/*     */   private SeriesRenderingOrder(String name) {
/*  79 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  89 */     return this.name;
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
/* 102 */     if (this == obj) {
/* 103 */       return true;
/*     */     }
/* 105 */     if (!(obj instanceof SeriesRenderingOrder)) {
/* 106 */       return false;
/*     */     }
/* 108 */     SeriesRenderingOrder order = (SeriesRenderingOrder)obj;
/* 109 */     if (!this.name.equals(order.toString())) {
/* 110 */       return false;
/*     */     }
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return this.name.hashCode();
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
/* 133 */     if (equals(FORWARD)) {
/* 134 */       return FORWARD;
/*     */     }
/* 136 */     if (equals(REVERSE)) {
/* 137 */       return REVERSE;
/*     */     }
/* 139 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/SeriesRenderingOrder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */