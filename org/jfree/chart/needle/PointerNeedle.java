/*     */ package org.jfree.chart.needle;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.GeneralPath;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PointerNeedle
/*     */   extends MeterNeedle
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4744677345334729606L;
/*     */   
/*     */   protected void drawNeedle(Graphics2D g2, Rectangle2D plotArea, Point2D rotate, double angle) {
/*  76 */     GeneralPath shape1 = new GeneralPath();
/*  77 */     GeneralPath shape2 = new GeneralPath();
/*  78 */     float minX = (float)plotArea.getMinX();
/*  79 */     float minY = (float)plotArea.getMinY();
/*  80 */     float maxX = (float)plotArea.getMaxX();
/*  81 */     float maxY = (float)plotArea.getMaxY();
/*  82 */     float midX = (float)(minX + plotArea.getWidth() / 2.0D);
/*  83 */     float midY = (float)(minY + plotArea.getHeight() / 2.0D);
/*     */     
/*  85 */     shape1.moveTo(minX, midY);
/*  86 */     shape1.lineTo(midX, minY);
/*  87 */     shape1.lineTo(maxX, midY);
/*  88 */     shape1.closePath();
/*     */     
/*  90 */     shape2.moveTo(minX, midY);
/*  91 */     shape2.lineTo(midX, maxY);
/*  92 */     shape2.lineTo(maxX, midY);
/*  93 */     shape2.closePath();
/*     */     
/*  95 */     if (rotate != null && angle != 0.0D) {
/*     */       
/*  97 */       getTransform().setToRotation(angle, rotate.getX(), rotate.getY());
/*  98 */       shape1.transform(getTransform());
/*  99 */       shape2.transform(getTransform());
/*     */     } 
/*     */     
/* 102 */     if (getFillPaint() != null) {
/* 103 */       g2.setPaint(getFillPaint());
/* 104 */       g2.fill(shape1);
/*     */     } 
/*     */     
/* 107 */     if (getHighlightPaint() != null) {
/* 108 */       g2.setPaint(getHighlightPaint());
/* 109 */       g2.fill(shape2);
/*     */     } 
/*     */     
/* 112 */     if (getOutlinePaint() != null) {
/* 113 */       g2.setStroke(getOutlineStroke());
/* 114 */       g2.setPaint(getOutlinePaint());
/* 115 */       g2.draw(shape1);
/* 116 */       g2.draw(shape2);
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
/*     */   public boolean equals(Object obj) {
/* 129 */     if (obj == this) {
/* 130 */       return true;
/*     */     }
/* 132 */     if (!(obj instanceof PointerNeedle)) {
/* 133 */       return false;
/*     */     }
/* 135 */     if (!super.equals(obj)) {
/* 136 */       return false;
/*     */     }
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 148 */     return super.hashCode();
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
/* 161 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/needle/PointerNeedle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */