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
/*     */ 
/*     */ 
/*     */ public final class AxisLabelLocation
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  57 */   public static final AxisLabelLocation HIGH_END = new AxisLabelLocation("HIGH_END");
/*     */ 
/*     */ 
/*     */   
/*  61 */   public static final AxisLabelLocation MIDDLE = new AxisLabelLocation("MIDDLE");
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static final AxisLabelLocation LOW_END = new AxisLabelLocation("LOW_END");
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
/*     */   private AxisLabelLocation(String name) {
/*  77 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  87 */     return this.name;
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
/* 100 */     if (this == obj) {
/* 101 */       return true;
/*     */     }
/* 103 */     if (!(obj instanceof AxisLabelLocation)) {
/* 104 */       return false;
/*     */     }
/* 106 */     AxisLabelLocation location = (AxisLabelLocation)obj;
/* 107 */     if (!this.name.equals(location.toString())) {
/* 108 */       return false;
/*     */     }
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     int hash = 5;
/* 121 */     hash = 83 * hash + this.name.hashCode();
/* 122 */     return hash;
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
/* 133 */     if (equals(HIGH_END)) {
/* 134 */       return HIGH_END;
/*     */     }
/* 136 */     if (equals(MIDDLE)) {
/* 137 */       return MIDDLE;
/*     */     }
/* 139 */     if (equals(LOW_END)) {
/* 140 */       return LOW_END;
/*     */     }
/*     */     
/* 143 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/AxisLabelLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */