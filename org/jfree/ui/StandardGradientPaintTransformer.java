/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardGradientPaintTransformer
/*     */   implements GradientPaintTransformer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8155025776964678320L;
/*     */   private GradientPaintTransformType type;
/*     */   
/*     */   public StandardGradientPaintTransformer() {
/*  74 */     this(GradientPaintTransformType.VERTICAL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StandardGradientPaintTransformer(GradientPaintTransformType type) {
/*  84 */     if (type == null) {
/*  85 */       throw new IllegalArgumentException("Null 'type' argument.");
/*     */     }
/*  87 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientPaintTransformType getType() {
/*  98 */     return this.type;
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
/*     */ 
/*     */   
/*     */   public GradientPaint transform(GradientPaint paint, Shape target) {
/* 113 */     GradientPaint result = paint;
/* 114 */     Rectangle2D bounds = target.getBounds2D();
/*     */     
/* 116 */     if (this.type.equals(GradientPaintTransformType.VERTICAL)) {
/*     */ 
/*     */ 
/*     */       
/* 120 */       result = new GradientPaint((float)bounds.getCenterX(), (float)bounds.getMinY(), paint.getColor1(), (float)bounds.getCenterX(), (float)bounds.getMaxY(), paint.getColor2());
/*     */     }
/* 122 */     else if (this.type.equals(GradientPaintTransformType.HORIZONTAL)) {
/*     */ 
/*     */ 
/*     */       
/* 126 */       result = new GradientPaint((float)bounds.getMinX(), (float)bounds.getCenterY(), paint.getColor1(), (float)bounds.getMaxX(), (float)bounds.getCenterY(), paint.getColor2());
/*     */     }
/* 128 */     else if (this.type.equals(GradientPaintTransformType.CENTER_HORIZONTAL)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 133 */       result = new GradientPaint((float)bounds.getCenterX(), (float)bounds.getCenterY(), paint.getColor2(), (float)bounds.getMaxX(), (float)bounds.getCenterY(), paint.getColor1(), true);
/*     */     }
/* 135 */     else if (this.type.equals(GradientPaintTransformType.CENTER_VERTICAL)) {
/*     */ 
/*     */ 
/*     */       
/* 139 */       result = new GradientPaint((float)bounds.getCenterX(), (float)bounds.getMinY(), paint.getColor1(), (float)bounds.getCenterX(), (float)bounds.getCenterY(), paint.getColor2(), true);
/*     */     } 
/*     */     
/* 142 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 153 */     if (obj == this) {
/* 154 */       return true;
/*     */     }
/* 156 */     if (!(obj instanceof StandardGradientPaintTransformer)) {
/* 157 */       return false;
/*     */     }
/* 159 */     StandardGradientPaintTransformer that = (StandardGradientPaintTransformer)obj;
/*     */     
/* 161 */     if (this.type != that.type) {
/* 162 */       return false;
/*     */     }
/* 164 */     return true;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 177 */     return super.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 186 */     return (this.type != null) ? this.type.hashCode() : 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/StandardGradientPaintTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */