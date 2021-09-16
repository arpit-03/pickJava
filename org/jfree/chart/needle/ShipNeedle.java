/*     */ package org.jfree.chart.needle;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Arc2D;
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
/*     */ public class ShipNeedle
/*     */   extends MeterNeedle
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 149554868169435612L;
/*     */   
/*     */   protected void drawNeedle(Graphics2D g2, Rectangle2D plotArea, Point2D rotate, double angle) {
/*  77 */     GeneralPath shape = new GeneralPath();
/*  78 */     shape.append(new Arc2D.Double(-9.0D, -7.0D, 10.0D, 14.0D, 0.0D, 25.5D, 0), true);
/*     */     
/*  80 */     shape.append(new Arc2D.Double(0.0D, -7.0D, 10.0D, 14.0D, 154.5D, 25.5D, 0), true);
/*     */     
/*  82 */     shape.closePath();
/*  83 */     getTransform().setToTranslation(plotArea.getMinX(), plotArea.getMaxY());
/*  84 */     getTransform().scale(plotArea.getWidth(), plotArea.getHeight() / 3.0D);
/*  85 */     shape.transform(getTransform());
/*     */     
/*  87 */     if (rotate != null && angle != 0.0D) {
/*     */       
/*  89 */       getTransform().setToRotation(angle, rotate.getX(), rotate.getY());
/*  90 */       shape.transform(getTransform());
/*     */     } 
/*     */     
/*  93 */     defaultDisplay(g2, shape);
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
/*     */   public boolean equals(Object object) {
/* 105 */     if (object == null) {
/* 106 */       return false;
/*     */     }
/* 108 */     if (object == this) {
/* 109 */       return true;
/*     */     }
/* 111 */     if (super.equals(object) && object instanceof ShipNeedle) {
/* 112 */       return true;
/*     */     }
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 124 */     return super.hashCode();
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
/* 137 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/needle/ShipNeedle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */