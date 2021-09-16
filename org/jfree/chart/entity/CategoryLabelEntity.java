/*     */ package org.jfree.chart.entity;
/*     */ 
/*     */ import java.awt.Shape;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CategoryLabelEntity
/*     */   extends TickLabelEntity
/*     */ {
/*     */   private Comparable key;
/*     */   
/*     */   public CategoryLabelEntity(Comparable key, Shape area, String toolTipText, String urlText) {
/*  70 */     super(area, toolTipText, urlText);
/*  71 */     this.key = key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparable getKey() {
/*  80 */     return this.key;
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
/*  92 */     if (obj == this) {
/*  93 */       return true;
/*     */     }
/*  95 */     if (!(obj instanceof CategoryLabelEntity)) {
/*  96 */       return false;
/*     */     }
/*  98 */     CategoryLabelEntity that = (CategoryLabelEntity)obj;
/*  99 */     if (!ObjectUtilities.equal(this.key, that.key)) {
/* 100 */       return false;
/*     */     }
/* 102 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 112 */     int result = super.hashCode();
/* 113 */     result = HashUtilities.hashCode(result, this.key);
/* 114 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 125 */     StringBuilder sb = new StringBuilder("CategoryLabelEntity: ");
/* 126 */     sb.append("category=");
/* 127 */     sb.append(this.key);
/* 128 */     sb.append(", tooltip=").append(getToolTipText());
/* 129 */     sb.append(", url=").append(getURLText());
/* 130 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/entity/CategoryLabelEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */