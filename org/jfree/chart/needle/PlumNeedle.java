/*     */ package org.jfree.chart.needle;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Arc2D;
/*     */ import java.awt.geom.Area;
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
/*     */ public class PlumNeedle
/*     */   extends MeterNeedle
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3082660488660600718L;
/*     */   
/*     */   protected void drawNeedle(Graphics2D g2, Rectangle2D plotArea, Point2D rotate, double angle) {
/*  76 */     Arc2D shape = new Arc2D.Double(2);
/*  77 */     double radius = plotArea.getHeight();
/*  78 */     double halfX = plotArea.getWidth() / 2.0D;
/*  79 */     double diameter = 2.0D * radius;
/*     */     
/*  81 */     shape.setFrame(plotArea.getMinX() + halfX - radius, plotArea
/*  82 */         .getMinY() - radius, diameter, diameter);
/*     */     
/*  84 */     radius = Math.toDegrees(Math.asin(halfX / radius));
/*  85 */     shape.setAngleStart(270.0D - radius);
/*  86 */     shape.setAngleExtent(2.0D * radius);
/*     */     
/*  88 */     Area s = new Area(shape);
/*     */     
/*  90 */     if (rotate != null && angle != 0.0D) {
/*     */       
/*  92 */       getTransform().setToRotation(angle, rotate.getX(), rotate.getY());
/*  93 */       s.transform(getTransform());
/*     */     } 
/*     */     
/*  96 */     defaultDisplay(g2, s);
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
/* 108 */     if (obj == this) {
/* 109 */       return true;
/*     */     }
/* 111 */     if (!(obj instanceof PlumNeedle)) {
/* 112 */       return false;
/*     */     }
/* 114 */     if (!super.equals(obj)) {
/* 115 */       return false;
/*     */     }
/* 117 */     return true;
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
/*     */ 
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
/* 140 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/needle/PlumNeedle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */