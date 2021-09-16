/*     */ package org.jfree.chart.event;
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
/*     */ public final class ChartChangeEventType
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5481917022435735602L;
/*  55 */   public static final ChartChangeEventType GENERAL = new ChartChangeEventType("ChartChangeEventType.GENERAL");
/*     */ 
/*     */ 
/*     */   
/*  59 */   public static final ChartChangeEventType NEW_DATASET = new ChartChangeEventType("ChartChangeEventType.NEW_DATASET");
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final ChartChangeEventType DATASET_UPDATED = new ChartChangeEventType("ChartChangeEventType.DATASET_UPDATED");
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
/*     */   private ChartChangeEventType(String name) {
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
/*     */   public boolean equals(Object obj) {
/*  98 */     if (this == obj) {
/*  99 */       return true;
/*     */     }
/* 101 */     if (!(obj instanceof ChartChangeEventType)) {
/* 102 */       return false;
/*     */     }
/* 104 */     ChartChangeEventType that = (ChartChangeEventType)obj;
/* 105 */     if (!this.name.equals(that.toString())) {
/* 106 */       return false;
/*     */     }
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 118 */     return this.name.hashCode();
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
/* 129 */     if (equals(GENERAL)) {
/* 130 */       return GENERAL;
/*     */     }
/* 132 */     if (equals(NEW_DATASET)) {
/* 133 */       return NEW_DATASET;
/*     */     }
/* 135 */     if (equals(DATASET_UPDATED)) {
/* 136 */       return DATASET_UPDATED;
/*     */     }
/* 138 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/event/ChartChangeEventType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */