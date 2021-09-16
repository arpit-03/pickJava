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
/*     */ public final class PlotOrientation
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2508771828190337782L;
/*  60 */   public static final PlotOrientation HORIZONTAL = new PlotOrientation("PlotOrientation.HORIZONTAL");
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static final PlotOrientation VERTICAL = new PlotOrientation("PlotOrientation.VERTICAL");
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
/*     */   private PlotOrientation(String name) {
/*  76 */     this.name = name;
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
/*     */   public boolean isHorizontal() {
/*  88 */     return equals(HORIZONTAL);
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
/*     */   public boolean isVertical() {
/* 100 */     return equals(VERTICAL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     return this.name;
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
/* 123 */     if (this == obj) {
/* 124 */       return true;
/*     */     }
/* 126 */     if (!(obj instanceof PlotOrientation)) {
/* 127 */       return false;
/*     */     }
/* 129 */     PlotOrientation orientation = (PlotOrientation)obj;
/* 130 */     if (!this.name.equals(orientation.toString())) {
/* 131 */       return false;
/*     */     }
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 143 */     return this.name.hashCode();
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
/* 154 */     Object result = null;
/* 155 */     if (equals(HORIZONTAL)) {
/* 156 */       result = HORIZONTAL;
/*     */     }
/* 158 */     else if (equals(VERTICAL)) {
/* 159 */       result = VERTICAL;
/*     */     } 
/* 161 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/PlotOrientation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */