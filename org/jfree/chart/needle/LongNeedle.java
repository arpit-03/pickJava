/*     */ package org.jfree.chart.needle;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
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
/*     */ public class LongNeedle
/*     */   extends MeterNeedle
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4319985779783688159L;
/*     */   
/*     */   public LongNeedle() {
/*  69 */     setRotateY(0.8D);
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
/*  84 */     GeneralPath shape1 = new GeneralPath();
/*  85 */     GeneralPath shape2 = new GeneralPath();
/*  86 */     GeneralPath shape3 = new GeneralPath();
/*     */     
/*  88 */     float minX = (float)plotArea.getMinX();
/*  89 */     float minY = (float)plotArea.getMinY();
/*  90 */     float maxX = (float)plotArea.getMaxX();
/*  91 */     float maxY = (float)plotArea.getMaxY();
/*     */ 
/*     */     
/*  94 */     float midX = (float)(minX + plotArea.getWidth() * 0.5D);
/*  95 */     float midY = (float)(minY + plotArea.getHeight() * 0.8D);
/*  96 */     float y = maxY - 2.0F * (maxY - midY);
/*  97 */     if (y < minY) {
/*  98 */       y = minY;
/*     */     }
/* 100 */     shape1.moveTo(minX, midY);
/* 101 */     shape1.lineTo(midX, minY);
/* 102 */     shape1.lineTo(midX, y);
/* 103 */     shape1.closePath();
/*     */     
/* 105 */     shape2.moveTo(maxX, midY);
/* 106 */     shape2.lineTo(midX, minY);
/* 107 */     shape2.lineTo(midX, y);
/* 108 */     shape2.closePath();
/*     */     
/* 110 */     shape3.moveTo(minX, midY);
/* 111 */     shape3.lineTo(midX, maxY);
/* 112 */     shape3.lineTo(maxX, midY);
/* 113 */     shape3.lineTo(midX, y);
/* 114 */     shape3.closePath();
/*     */     
/* 116 */     Shape s1 = shape1;
/* 117 */     Shape s2 = shape2;
/* 118 */     Shape s3 = shape3;
/*     */     
/* 120 */     if (rotate != null && angle != 0.0D) {
/*     */       
/* 122 */       getTransform().setToRotation(angle, rotate.getX(), rotate.getY());
/* 123 */       s1 = shape1.createTransformedShape(transform);
/* 124 */       s2 = shape2.createTransformedShape(transform);
/* 125 */       s3 = shape3.createTransformedShape(transform);
/*     */     } 
/*     */ 
/*     */     
/* 129 */     if (getHighlightPaint() != null) {
/* 130 */       g2.setPaint(getHighlightPaint());
/* 131 */       g2.fill(s3);
/*     */     } 
/*     */     
/* 134 */     if (getFillPaint() != null) {
/* 135 */       g2.setPaint(getFillPaint());
/* 136 */       g2.fill(s1);
/* 137 */       g2.fill(s2);
/*     */     } 
/*     */ 
/*     */     
/* 141 */     if (getOutlinePaint() != null) {
/* 142 */       g2.setStroke(getOutlineStroke());
/* 143 */       g2.setPaint(getOutlinePaint());
/* 144 */       g2.draw(s1);
/* 145 */       g2.draw(s2);
/* 146 */       g2.draw(s3);
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
/* 159 */     if (obj == this) {
/* 160 */       return true;
/*     */     }
/* 162 */     if (!(obj instanceof LongNeedle)) {
/* 163 */       return false;
/*     */     }
/* 165 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 175 */     return super.hashCode();
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
/* 188 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/needle/LongNeedle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */