/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.geom.RectangularShape;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.ui.GradientPaintTransformer;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardBarPainter
/*     */   implements BarPainter, Serializable
/*     */ {
/*     */   public void paintBar(Graphics2D g2, BarRenderer renderer, int row, int column, RectangularShape bar, RectangleEdge base) {
/*  88 */     Paint itemPaint = renderer.getItemPaint(row, column);
/*  89 */     GradientPaintTransformer t = renderer.getGradientPaintTransformer();
/*  90 */     if (t != null && itemPaint instanceof GradientPaint) {
/*  91 */       itemPaint = t.transform((GradientPaint)itemPaint, bar);
/*     */     }
/*  93 */     g2.setPaint(itemPaint);
/*  94 */     g2.fill(bar);
/*     */ 
/*     */     
/*  97 */     if (renderer.isDrawBarOutline()) {
/*     */       
/*  99 */       Stroke stroke = renderer.getItemOutlineStroke(row, column);
/* 100 */       Paint paint = renderer.getItemOutlinePaint(row, column);
/* 101 */       if (stroke != null && paint != null) {
/* 102 */         g2.setStroke(stroke);
/* 103 */         g2.setPaint(paint);
/* 104 */         g2.draw(bar);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBarShadow(Graphics2D g2, BarRenderer renderer, int row, int column, RectangularShape bar, RectangleEdge base, boolean pegShadow) {
/* 129 */     Paint itemPaint = renderer.getItemPaint(row, column);
/* 130 */     if (itemPaint instanceof Color) {
/* 131 */       Color c = (Color)itemPaint;
/* 132 */       if (c.getAlpha() == 0) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 137 */     RectangularShape shadow = createShadow(bar, renderer.getShadowXOffset(), renderer
/* 138 */         .getShadowYOffset(), base, pegShadow);
/* 139 */     g2.setPaint(renderer.getShadowPaint());
/* 140 */     g2.fill(shadow);
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
/*     */ 
/*     */   
/*     */   private Rectangle2D createShadow(RectangularShape bar, double xOffset, double yOffset, RectangleEdge base, boolean pegShadow) {
/* 157 */     double x0 = bar.getMinX();
/* 158 */     double x1 = bar.getMaxX();
/* 159 */     double y0 = bar.getMinY();
/* 160 */     double y1 = bar.getMaxY();
/* 161 */     if (base == RectangleEdge.TOP) {
/* 162 */       x0 += xOffset;
/* 163 */       x1 += xOffset;
/* 164 */       if (!pegShadow) {
/* 165 */         y0 += yOffset;
/*     */       }
/* 167 */       y1 += yOffset;
/*     */     }
/* 169 */     else if (base == RectangleEdge.BOTTOM) {
/* 170 */       x0 += xOffset;
/* 171 */       x1 += xOffset;
/* 172 */       y0 += yOffset;
/* 173 */       if (!pegShadow) {
/* 174 */         y1 += yOffset;
/*     */       }
/*     */     }
/* 177 */     else if (base == RectangleEdge.LEFT) {
/* 178 */       if (!pegShadow) {
/* 179 */         x0 += xOffset;
/*     */       }
/* 181 */       x1 += xOffset;
/* 182 */       y0 += yOffset;
/* 183 */       y1 += yOffset;
/*     */     }
/* 185 */     else if (base == RectangleEdge.RIGHT) {
/* 186 */       x0 += xOffset;
/* 187 */       if (!pegShadow) {
/* 188 */         x1 += xOffset;
/*     */       }
/* 190 */       y0 += yOffset;
/* 191 */       y1 += yOffset;
/*     */     } 
/* 193 */     return new Rectangle2D.Double(x0, y0, x1 - x0, y1 - y0);
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
/* 205 */     if (obj == this) {
/* 206 */       return true;
/*     */     }
/* 208 */     if (!(obj instanceof StandardBarPainter)) {
/* 209 */       return false;
/*     */     }
/* 211 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 221 */     int hash = 37;
/*     */     
/* 223 */     return hash;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/StandardBarPainter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */