/*     */ package org.jfree.chart.needle;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArrowNeedle
/*     */   extends MeterNeedle
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5334056511213782357L;
/*     */   private boolean isArrowAtTop = true;
/*     */   
/*     */   public ArrowNeedle(boolean isArrowAtTop) {
/*  80 */     this.isArrowAtTop = isArrowAtTop;
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
/*     */   protected void drawNeedle(Graphics2D g2, Rectangle2D plotArea, Point2D rotate, double angle) {
/*     */     Shape d;
/*  95 */     Line2D shape = new Line2D.Float();
/*     */ 
/*     */     
/*  98 */     float x = (float)(plotArea.getMinX() + plotArea.getWidth() / 2.0D);
/*  99 */     float minY = (float)plotArea.getMinY();
/* 100 */     float maxY = (float)plotArea.getMaxY();
/* 101 */     shape.setLine(x, minY, x, maxY);
/*     */     
/* 103 */     GeneralPath shape1 = new GeneralPath();
/* 104 */     if (this.isArrowAtTop) {
/* 105 */       shape1.moveTo(x, minY);
/* 106 */       minY += (4 * getSize());
/*     */     } else {
/*     */       
/* 109 */       shape1.moveTo(x, maxY);
/* 110 */       minY = maxY - (4 * getSize());
/*     */     } 
/* 112 */     shape1.lineTo(x + getSize(), minY);
/* 113 */     shape1.lineTo(x - getSize(), minY);
/* 114 */     shape1.closePath();
/*     */     
/* 116 */     if (rotate != null && angle != 0.0D) {
/* 117 */       getTransform().setToRotation(angle, rotate.getX(), rotate.getY());
/* 118 */       d = getTransform().createTransformedShape(shape);
/*     */     } else {
/*     */       
/* 121 */       d = shape;
/*     */     } 
/* 123 */     defaultDisplay(g2, d);
/*     */     
/* 125 */     if (rotate != null && angle != 0.0D) {
/* 126 */       d = getTransform().createTransformedShape(shape1);
/*     */     } else {
/*     */       
/* 129 */       d = shape1;
/*     */     } 
/* 131 */     defaultDisplay(g2, d);
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
/* 144 */     if (obj == this) {
/* 145 */       return true;
/*     */     }
/* 147 */     if (!(obj instanceof ArrowNeedle)) {
/* 148 */       return false;
/*     */     }
/* 150 */     if (!super.equals(obj)) {
/* 151 */       return false;
/*     */     }
/* 153 */     ArrowNeedle that = (ArrowNeedle)obj;
/* 154 */     if (this.isArrowAtTop != that.isArrowAtTop) {
/* 155 */       return false;
/*     */     }
/* 157 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 167 */     int result = super.hashCode();
/* 168 */     result = HashUtilities.hashCode(result, this.isArrowAtTop);
/* 169 */     return result;
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
/* 182 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/needle/ArrowNeedle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */