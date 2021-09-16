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
/*     */ public final class PieLabelLinkStyle
/*     */   implements Serializable
/*     */ {
/*  55 */   public static final PieLabelLinkStyle STANDARD = new PieLabelLinkStyle("PieLabelLinkStyle.STANDARD");
/*     */ 
/*     */ 
/*     */   
/*  59 */   public static final PieLabelLinkStyle QUAD_CURVE = new PieLabelLinkStyle("PieLabelLinkStyle.QUAD_CURVE");
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final PieLabelLinkStyle CUBIC_CURVE = new PieLabelLinkStyle("PieLabelLinkStyle.CUBIC_CURVE");
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
/*     */   private PieLabelLinkStyle(String name) {
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
/* 101 */     if (!(obj instanceof PieLabelLinkStyle)) {
/* 102 */       return false;
/*     */     }
/* 104 */     PieLabelLinkStyle style = (PieLabelLinkStyle)obj;
/* 105 */     if (!this.name.equals(style.toString())) {
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
/* 129 */     Object result = null;
/* 130 */     if (equals(STANDARD)) {
/* 131 */       result = STANDARD;
/*     */     }
/* 133 */     else if (equals(QUAD_CURVE)) {
/* 134 */       result = QUAD_CURVE;
/*     */     }
/* 136 */     else if (equals(CUBIC_CURVE)) {
/* 137 */       result = CUBIC_CURVE;
/*     */     } 
/* 139 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/PieLabelLinkStyle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */