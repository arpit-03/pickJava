/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CategoryLabelWidthType
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6976024792582949656L;
/*  57 */   public static final CategoryLabelWidthType CATEGORY = new CategoryLabelWidthType("CategoryLabelWidthType.CATEGORY");
/*     */ 
/*     */ 
/*     */   
/*  61 */   public static final CategoryLabelWidthType RANGE = new CategoryLabelWidthType("CategoryLabelWidthType.RANGE");
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
/*     */   private CategoryLabelWidthType(String name) {
/*  73 */     ParamChecks.nullNotPermitted(name, "name");
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
/* 100 */     if (!(obj instanceof CategoryLabelWidthType)) {
/* 101 */       return false;
/*     */     }
/* 103 */     CategoryLabelWidthType t = (CategoryLabelWidthType)obj;
/* 104 */     if (!this.name.equals(t.toString())) {
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
/*     */   
/*     */   private Object readResolve() throws ObjectStreamException {
/* 118 */     if (equals(CATEGORY)) {
/* 119 */       return CATEGORY;
/*     */     }
/* 121 */     if (equals(RANGE)) {
/* 122 */       return RANGE;
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/CategoryLabelWidthType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */