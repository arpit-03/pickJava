/*     */ package org.jfree.chart.entity;
/*     */ 
/*     */ import java.awt.Shape;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XYAnnotationEntity
/*     */   extends ChartEntity
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2340334068383660799L;
/*     */   private int rendererIndex;
/*     */   
/*     */   public XYAnnotationEntity(Shape hotspot, int rendererIndex, String toolTipText, String urlText) {
/*  69 */     super(hotspot, toolTipText, urlText);
/*  70 */     this.rendererIndex = rendererIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRendererIndex() {
/*  79 */     return this.rendererIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRendererIndex(int index) {
/*  88 */     this.rendererIndex = index;
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
/*     */   public boolean equals(Object obj) {
/* 100 */     if (obj == this) {
/* 101 */       return true;
/*     */     }
/* 103 */     if (!super.equals(obj)) {
/* 104 */       return false;
/*     */     }
/* 106 */     if (!(obj instanceof XYAnnotationEntity)) {
/* 107 */       return false;
/*     */     }
/* 109 */     XYAnnotationEntity that = (XYAnnotationEntity)obj;
/* 110 */     if (this.rendererIndex != that.rendererIndex) {
/* 111 */       return false;
/*     */     }
/* 113 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/entity/XYAnnotationEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */