/*     */ package org.jfree.chart.util;
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
/*     */ public final class XYCoordinateType
/*     */   implements Serializable
/*     */ {
/*  55 */   public static final XYCoordinateType DATA = new XYCoordinateType("XYCoordinateType.DATA");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public static final XYCoordinateType RELATIVE = new XYCoordinateType("XYCoordinateType.RELATIVE");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final XYCoordinateType INDEX = new XYCoordinateType("XYCoordinateType.INDEX");
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
/*     */   private XYCoordinateType(String name) {
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
/* 107 */     if (!(obj instanceof XYCoordinateType)) {
/* 108 */       return false;
/*     */     }
/* 110 */     XYCoordinateType order = (XYCoordinateType)obj;
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
/*     */   
/*     */   private Object readResolve() throws ObjectStreamException {
/* 125 */     if (equals(DATA)) {
/* 126 */       return DATA;
/*     */     }
/* 128 */     if (equals(RELATIVE)) {
/* 129 */       return RELATIVE;
/*     */     }
/* 131 */     if (equals(INDEX)) {
/* 132 */       return INDEX;
/*     */     }
/* 134 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/util/XYCoordinateType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */