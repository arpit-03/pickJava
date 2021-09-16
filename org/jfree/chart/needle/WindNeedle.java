/*     */ package org.jfree.chart.needle;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
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
/*     */ public class WindNeedle
/*     */   extends ArrowNeedle
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2861061368907167834L;
/*     */   
/*     */   public WindNeedle() {
/*  65 */     super(false);
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
/*     */   protected void drawNeedle(Graphics2D g2, Rectangle2D plotArea, Point2D rotate, double angle) {
/*  80 */     super.drawNeedle(g2, plotArea, rotate, angle);
/*  81 */     if (rotate != null && plotArea != null) {
/*     */       
/*  83 */       int spacing = getSize() * 3;
/*  84 */       Rectangle2D newArea = new Rectangle2D.Double();
/*     */       
/*  86 */       Point2D newRotate = rotate;
/*  87 */       newArea.setRect(plotArea.getMinX() - spacing, plotArea.getMinY(), plotArea
/*  88 */           .getWidth(), plotArea.getHeight());
/*  89 */       super.drawNeedle(g2, newArea, newRotate, angle);
/*     */       
/*  91 */       newArea.setRect(plotArea.getMinX() + spacing, plotArea
/*  92 */           .getMinY(), plotArea.getWidth(), plotArea
/*  93 */           .getHeight());
/*  94 */       super.drawNeedle(g2, newArea, newRotate, angle);
/*     */     } 
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
/*     */   public boolean equals(Object object) {
/* 108 */     if (object == null) {
/* 109 */       return false;
/*     */     }
/* 111 */     if (object == this) {
/* 112 */       return true;
/*     */     }
/* 114 */     if (super.equals(object) && object instanceof WindNeedle) {
/* 115 */       return true;
/*     */     }
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 127 */     return super.hashCode();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/needle/WindNeedle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */