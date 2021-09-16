/*     */ package org.jfree.chart.needle;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Area;
/*     */ import java.awt.geom.Ellipse2D;
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
/*     */ public class MiddlePinNeedle
/*     */   extends MeterNeedle
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6237073996403125310L;
/*     */   
/*     */   protected void drawNeedle(Graphics2D g2, Rectangle2D plotArea, Point2D rotate, double angle) {
/*  78 */     GeneralPath pointer = new GeneralPath();
/*     */     
/*  80 */     int minY = (int)plotArea.getMinY();
/*     */     
/*  82 */     int maxY = (int)plotArea.getMaxY();
/*  83 */     int midY = (maxY - minY) / 2 + minY;
/*     */     
/*  85 */     int midX = (int)(plotArea.getMinX() + plotArea.getWidth() / 2.0D);
/*     */     
/*  87 */     int lenX = (int)(plotArea.getWidth() / 10.0D);
/*  88 */     if (lenX < 2) {
/*  89 */       lenX = 2;
/*     */     }
/*     */     
/*  92 */     pointer.moveTo((midX - lenX), (midY - lenX));
/*  93 */     pointer.lineTo((midX + lenX), (midY - lenX));
/*  94 */     pointer.lineTo(midX, minY);
/*  95 */     pointer.closePath();
/*     */     
/*  97 */     lenX = 4 * lenX;
/*  98 */     Ellipse2D circle = new Ellipse2D.Double((midX - lenX / 2), (midY - lenX), lenX, lenX);
/*     */ 
/*     */     
/* 101 */     Area shape = new Area(circle);
/* 102 */     shape.add(new Area(pointer));
/* 103 */     if (rotate != null && angle != 0.0D) {
/*     */       
/* 105 */       getTransform().setToRotation(angle, rotate.getX(), rotate.getY());
/* 106 */       shape.transform(getTransform());
/*     */     } 
/*     */     
/* 109 */     defaultDisplay(g2, shape);
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
/* 122 */     if (object == null) {
/* 123 */       return false;
/*     */     }
/* 125 */     if (object == this) {
/* 126 */       return true;
/*     */     }
/* 128 */     if (super.equals(object) && object instanceof MiddlePinNeedle) {
/* 129 */       return true;
/*     */     }
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 141 */     return super.hashCode();
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
/* 154 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/needle/MiddlePinNeedle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */