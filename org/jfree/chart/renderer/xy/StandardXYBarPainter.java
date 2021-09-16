/*     */ package org.jfree.chart.renderer.xy;
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
/*     */ public class StandardXYBarPainter
/*     */   implements XYBarPainter, Serializable
/*     */ {
/*     */   public void paintBar(Graphics2D g2, XYBarRenderer renderer, int row, int column, RectangularShape bar, RectangleEdge base) {
/*  87 */     Paint itemPaint = renderer.getItemPaint(row, column);
/*  88 */     GradientPaintTransformer t = renderer.getGradientPaintTransformer();
/*  89 */     if (t != null && itemPaint instanceof GradientPaint) {
/*  90 */       itemPaint = t.transform((GradientPaint)itemPaint, bar);
/*     */     }
/*  92 */     g2.setPaint(itemPaint);
/*  93 */     g2.fill(bar);
/*     */ 
/*     */     
/*  96 */     if (renderer.isDrawBarOutline()) {
/*     */       
/*  98 */       Stroke stroke = renderer.getItemOutlineStroke(row, column);
/*  99 */       Paint paint = renderer.getItemOutlinePaint(row, column);
/* 100 */       if (stroke != null && paint != null) {
/* 101 */         g2.setStroke(stroke);
/* 102 */         g2.setPaint(paint);
/* 103 */         g2.draw(bar);
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
/*     */   public void paintBarShadow(Graphics2D g2, XYBarRenderer renderer, int row, int column, RectangularShape bar, RectangleEdge base, boolean pegShadow) {
/* 128 */     Paint itemPaint = renderer.getItemPaint(row, column);
/* 129 */     if (itemPaint instanceof Color) {
/* 130 */       Color c = (Color)itemPaint;
/* 131 */       if (c.getAlpha() == 0) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 136 */     RectangularShape shadow = createShadow(bar, renderer.getShadowXOffset(), renderer
/* 137 */         .getShadowYOffset(), base, pegShadow);
/* 138 */     g2.setPaint(Color.gray);
/* 139 */     g2.fill(shadow);
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
/* 156 */     double x0 = bar.getMinX();
/* 157 */     double x1 = bar.getMaxX();
/* 158 */     double y0 = bar.getMinY();
/* 159 */     double y1 = bar.getMaxY();
/* 160 */     if (base == RectangleEdge.TOP) {
/* 161 */       x0 += xOffset;
/* 162 */       x1 += xOffset;
/* 163 */       if (!pegShadow) {
/* 164 */         y0 += yOffset;
/*     */       }
/* 166 */       y1 += yOffset;
/*     */     }
/* 168 */     else if (base == RectangleEdge.BOTTOM) {
/* 169 */       x0 += xOffset;
/* 170 */       x1 += xOffset;
/* 171 */       y0 += yOffset;
/* 172 */       if (!pegShadow) {
/* 173 */         y1 += yOffset;
/*     */       }
/*     */     }
/* 176 */     else if (base == RectangleEdge.LEFT) {
/* 177 */       if (!pegShadow) {
/* 178 */         x0 += xOffset;
/*     */       }
/* 180 */       x1 += xOffset;
/* 181 */       y0 += yOffset;
/* 182 */       y1 += yOffset;
/*     */     }
/* 184 */     else if (base == RectangleEdge.RIGHT) {
/* 185 */       x0 += xOffset;
/* 186 */       if (!pegShadow) {
/* 187 */         x1 += xOffset;
/*     */       }
/* 189 */       y0 += yOffset;
/* 190 */       y1 += yOffset;
/*     */     } 
/* 192 */     return new Rectangle2D.Double(x0, y0, x1 - x0, y1 - y0);
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
/* 204 */     if (obj == this) {
/* 205 */       return true;
/*     */     }
/* 207 */     if (!(obj instanceof StandardXYBarPainter)) {
/* 208 */       return false;
/*     */     }
/* 210 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 220 */     int hash = 37;
/*     */     
/* 222 */     return hash;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/StandardXYBarPainter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */