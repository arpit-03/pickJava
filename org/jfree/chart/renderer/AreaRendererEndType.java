/*     */ package org.jfree.chart.renderer;
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
/*     */ public final class AreaRendererEndType
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1774146392916359839L;
/*  57 */   public static final AreaRendererEndType TAPER = new AreaRendererEndType("AreaRendererEndType.TAPER");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final AreaRendererEndType TRUNCATE = new AreaRendererEndType("AreaRendererEndType.TRUNCATE");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final AreaRendererEndType LEVEL = new AreaRendererEndType("AreaRendererEndType.LEVEL");
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
/*     */   private AreaRendererEndType(String name) {
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
/* 107 */     if (!(obj instanceof AreaRendererEndType)) {
/* 108 */       return false;
/*     */     }
/* 110 */     AreaRendererEndType that = (AreaRendererEndType)obj;
/* 111 */     if (!this.name.equals(that.toString())) {
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
/* 125 */     Object result = null;
/* 126 */     if (equals(LEVEL)) {
/* 127 */       result = LEVEL;
/*     */     }
/* 129 */     else if (equals(TAPER)) {
/* 130 */       result = TAPER;
/*     */     }
/* 132 */     else if (equals(TRUNCATE)) {
/* 133 */       result = TRUNCATE;
/*     */     } 
/* 135 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/AreaRendererEndType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */