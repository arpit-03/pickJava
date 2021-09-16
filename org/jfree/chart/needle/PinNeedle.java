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
/*     */ public class PinNeedle
/*     */   extends MeterNeedle
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3787089953079863373L;
/*     */   
/*     */   protected void drawNeedle(Graphics2D g2, Rectangle2D plotArea, Point2D rotate, double angle) {
/*  78 */     GeneralPath pointer = new GeneralPath();
/*     */     
/*  80 */     int minY = (int)plotArea.getMinY();
/*     */     
/*  82 */     int maxY = (int)plotArea.getMaxY();
/*  83 */     int midX = (int)(plotArea.getMinX() + plotArea.getWidth() / 2.0D);
/*     */     
/*  85 */     int lenX = (int)(plotArea.getWidth() / 10.0D);
/*  86 */     if (lenX < 2) {
/*  87 */       lenX = 2;
/*     */     }
/*     */     
/*  90 */     pointer.moveTo((midX - lenX), (maxY - lenX));
/*  91 */     pointer.lineTo((midX + lenX), (maxY - lenX));
/*  92 */     pointer.lineTo(midX, (minY + lenX));
/*  93 */     pointer.closePath();
/*     */     
/*  95 */     lenX = 4 * lenX;
/*     */     
/*  97 */     Ellipse2D circle = new Ellipse2D.Double((midX - lenX / 2), plotArea.getMaxY() - lenX, lenX, lenX);
/*     */     
/*  99 */     Area shape = new Area(circle);
/* 100 */     shape.add(new Area(pointer));
/* 101 */     if (rotate != null && angle != 0.0D) {
/*     */       
/* 103 */       getTransform().setToRotation(angle, rotate.getX(), rotate.getY());
/* 104 */       shape.transform(getTransform());
/*     */     } 
/*     */     
/* 107 */     defaultDisplay(g2, shape);
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
/* 120 */     if (obj == this) {
/* 121 */       return true;
/*     */     }
/* 123 */     if (!(obj instanceof PinNeedle)) {
/* 124 */       return false;
/*     */     }
/* 126 */     if (!super.equals(obj)) {
/* 127 */       return false;
/*     */     }
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 139 */     return super.hashCode();
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
/* 152 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/needle/PinNeedle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */