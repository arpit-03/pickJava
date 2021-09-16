/*     */ package org.jfree.chart.util;
/*     */ 
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import org.jfree.ui.GradientPaintTransformer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DirectionalGradientPaintTransformer
/*     */   implements GradientPaintTransformer
/*     */ {
/*     */   public GradientPaint transform(GradientPaint paint, Shape target) {
/*     */     float rx1, ry1, rx2, ry2;
/*  95 */     double px1 = paint.getPoint1().getX();
/*  96 */     double py1 = paint.getPoint1().getY();
/*  97 */     double px2 = paint.getPoint2().getX();
/*  98 */     double py2 = paint.getPoint2().getY();
/*     */     
/* 100 */     Rectangle2D bounds = target.getBounds();
/* 101 */     float bx = (float)bounds.getX();
/* 102 */     float by = (float)bounds.getY();
/* 103 */     float bw = (float)bounds.getWidth();
/* 104 */     float bh = (float)bounds.getHeight();
/*     */ 
/*     */     
/* 107 */     if (px1 == 0.0D && py1 == 0.0D) {
/*     */       
/* 109 */       rx1 = bx;
/* 110 */       ry1 = by;
/* 111 */       if (px2 != 0.0D && py2 != 0.0D)
/*     */       {
/* 113 */         float offset = paint.isCyclic() ? ((bw + bh) / 4.0F) : ((bw + bh) / 2.0F);
/*     */         
/* 115 */         rx2 = bx + offset;
/* 116 */         ry2 = by + offset;
/*     */       
/*     */       }
/*     */       else
/*     */       {
/* 121 */         rx2 = (px2 == 0.0D) ? rx1 : (paint.isCyclic() ? (rx1 + bw / 2.0F) : (rx1 + bw));
/*     */         
/* 123 */         ry2 = (py2 == 0.0D) ? ry1 : (paint.isCyclic() ? (ry1 + bh / 2.0F) : (ry1 + bh));
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 129 */       rx1 = bx;
/* 130 */       ry1 = by + bh;
/* 131 */       float offset = paint.isCyclic() ? ((bw + bh) / 4.0F) : ((bw + bh) / 2.0F);
/*     */       
/* 133 */       rx2 = bx + offset;
/* 134 */       ry2 = by + bh - offset;
/*     */     } 
/* 136 */     return new GradientPaint(rx1, ry1, paint.getColor1(), rx2, ry2, paint
/* 137 */         .getColor2(), paint.isCyclic());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/util/DirectionalGradientPaintTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */