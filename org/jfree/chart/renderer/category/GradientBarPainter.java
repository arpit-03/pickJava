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
/*     */ import org.jfree.chart.HashUtilities;
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
/*     */ public class GradientBarPainter
/*     */   implements BarPainter, Serializable
/*     */ {
/*     */   private double g1;
/*     */   private double g2;
/*     */   private double g3;
/*     */   
/*     */   public GradientBarPainter() {
/*  78 */     this(0.1D, 0.2D, 0.8D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientBarPainter(double g1, double g2, double g3) {
/*  89 */     this.g1 = g1;
/*  90 */     this.g2 = g2;
/*  91 */     this.g3 = g3;
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
/*     */   public void paintBar(Graphics2D g2, BarRenderer renderer, int row, int column, RectangularShape bar, RectangleEdge base) {
/*     */     Color c0, c1;
/* 109 */     Paint itemPaint = renderer.getItemPaint(row, column);
/*     */ 
/*     */     
/* 112 */     if (itemPaint instanceof Color) {
/* 113 */       c0 = (Color)itemPaint;
/* 114 */       c1 = c0.brighter();
/*     */     }
/* 116 */     else if (itemPaint instanceof GradientPaint) {
/* 117 */       GradientPaint gp = (GradientPaint)itemPaint;
/* 118 */       c0 = gp.getColor1();
/* 119 */       c1 = gp.getColor2();
/*     */     } else {
/*     */       
/* 122 */       c0 = Color.BLUE;
/* 123 */       c1 = Color.BLUE.brighter();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 128 */     if (c0.getAlpha() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 132 */     if (base == RectangleEdge.TOP || base == RectangleEdge.BOTTOM) {
/* 133 */       Rectangle2D[] regions = splitVerticalBar(bar, this.g1, this.g2, this.g3);
/*     */ 
/*     */       
/* 136 */       GradientPaint gp = new GradientPaint((float)regions[0].getMinX(), 0.0F, c0, (float)regions[0].getMaxX(), 0.0F, Color.WHITE);
/* 137 */       g2.setPaint(gp);
/* 138 */       g2.fill(regions[0]);
/*     */ 
/*     */       
/* 141 */       gp = new GradientPaint((float)regions[1].getMinX(), 0.0F, Color.WHITE, (float)regions[1].getMaxX(), 0.0F, c0);
/* 142 */       g2.setPaint(gp);
/* 143 */       g2.fill(regions[1]);
/*     */ 
/*     */       
/* 146 */       gp = new GradientPaint((float)regions[2].getMinX(), 0.0F, c0, (float)regions[2].getMaxX(), 0.0F, c1);
/* 147 */       g2.setPaint(gp);
/* 148 */       g2.fill(regions[2]);
/*     */ 
/*     */       
/* 151 */       gp = new GradientPaint((float)regions[3].getMinX(), 0.0F, c1, (float)regions[3].getMaxX(), 0.0F, c0);
/* 152 */       g2.setPaint(gp);
/* 153 */       g2.fill(regions[3]);
/*     */     }
/* 155 */     else if (base == RectangleEdge.LEFT || base == RectangleEdge.RIGHT) {
/* 156 */       Rectangle2D[] regions = splitHorizontalBar(bar, this.g1, this.g2, this.g3);
/*     */ 
/*     */ 
/*     */       
/* 160 */       GradientPaint gp = new GradientPaint(0.0F, (float)regions[0].getMinY(), c0, 0.0F, (float)regions[0].getMaxY(), Color.WHITE);
/* 161 */       g2.setPaint(gp);
/* 162 */       g2.fill(regions[0]);
/*     */ 
/*     */       
/* 165 */       gp = new GradientPaint(0.0F, (float)regions[1].getMinY(), Color.WHITE, 0.0F, (float)regions[1].getMaxY(), c0);
/* 166 */       g2.setPaint(gp);
/* 167 */       g2.fill(regions[1]);
/*     */ 
/*     */       
/* 170 */       gp = new GradientPaint(0.0F, (float)regions[2].getMinY(), c0, 0.0F, (float)regions[2].getMaxY(), c1);
/* 171 */       g2.setPaint(gp);
/* 172 */       g2.fill(regions[2]);
/*     */ 
/*     */       
/* 175 */       gp = new GradientPaint(0.0F, (float)regions[3].getMinY(), c1, 0.0F, (float)regions[3].getMaxY(), c0);
/* 176 */       g2.setPaint(gp);
/* 177 */       g2.fill(regions[3]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 182 */     if (renderer.isDrawBarOutline()) {
/*     */       
/* 184 */       Stroke stroke = renderer.getItemOutlineStroke(row, column);
/* 185 */       Paint paint = renderer.getItemOutlinePaint(row, column);
/* 186 */       if (stroke != null && paint != null) {
/* 187 */         g2.setStroke(stroke);
/* 188 */         g2.setPaint(paint);
/* 189 */         g2.draw(bar);
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
/* 214 */     Paint itemPaint = renderer.getItemPaint(row, column);
/* 215 */     if (itemPaint instanceof Color) {
/* 216 */       Color c = (Color)itemPaint;
/* 217 */       if (c.getAlpha() == 0) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 222 */     RectangularShape shadow = createShadow(bar, renderer.getShadowXOffset(), renderer
/* 223 */         .getShadowYOffset(), base, pegShadow);
/* 224 */     g2.setPaint(renderer.getShadowPaint());
/* 225 */     g2.fill(shadow);
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
/* 242 */     double x0 = bar.getMinX();
/* 243 */     double x1 = bar.getMaxX();
/* 244 */     double y0 = bar.getMinY();
/* 245 */     double y1 = bar.getMaxY();
/* 246 */     if (base == RectangleEdge.TOP) {
/* 247 */       x0 += xOffset;
/* 248 */       x1 += xOffset;
/* 249 */       if (!pegShadow) {
/* 250 */         y0 += yOffset;
/*     */       }
/* 252 */       y1 += yOffset;
/*     */     }
/* 254 */     else if (base == RectangleEdge.BOTTOM) {
/* 255 */       x0 += xOffset;
/* 256 */       x1 += xOffset;
/* 257 */       y0 += yOffset;
/* 258 */       if (!pegShadow) {
/* 259 */         y1 += yOffset;
/*     */       }
/*     */     }
/* 262 */     else if (base == RectangleEdge.LEFT) {
/* 263 */       if (!pegShadow) {
/* 264 */         x0 += xOffset;
/*     */       }
/* 266 */       x1 += xOffset;
/* 267 */       y0 += yOffset;
/* 268 */       y1 += yOffset;
/*     */     }
/* 270 */     else if (base == RectangleEdge.RIGHT) {
/* 271 */       x0 += xOffset;
/* 272 */       if (!pegShadow) {
/* 273 */         x1 += xOffset;
/*     */       }
/* 275 */       y0 += yOffset;
/* 276 */       y1 += yOffset;
/*     */     } 
/* 278 */     return new Rectangle2D.Double(x0, y0, x1 - x0, y1 - y0);
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
/*     */   private Rectangle2D[] splitVerticalBar(RectangularShape bar, double a, double b, double c) {
/* 294 */     Rectangle2D[] result = new Rectangle2D[4];
/* 295 */     double x0 = bar.getMinX();
/* 296 */     double x1 = Math.rint(x0 + bar.getWidth() * a);
/* 297 */     double x2 = Math.rint(x0 + bar.getWidth() * b);
/* 298 */     double x3 = Math.rint(x0 + bar.getWidth() * c);
/* 299 */     result[0] = new Rectangle2D.Double(bar.getMinX(), bar.getMinY(), x1 - x0, bar
/* 300 */         .getHeight());
/* 301 */     result[1] = new Rectangle2D.Double(x1, bar.getMinY(), x2 - x1, bar
/* 302 */         .getHeight());
/* 303 */     result[2] = new Rectangle2D.Double(x2, bar.getMinY(), x3 - x2, bar
/* 304 */         .getHeight());
/* 305 */     result[3] = new Rectangle2D.Double(x3, bar.getMinY(), bar
/* 306 */         .getMaxX() - x3, bar.getHeight());
/* 307 */     return result;
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
/*     */   private Rectangle2D[] splitHorizontalBar(RectangularShape bar, double a, double b, double c) {
/* 323 */     Rectangle2D[] result = new Rectangle2D[4];
/* 324 */     double y0 = bar.getMinY();
/* 325 */     double y1 = Math.rint(y0 + bar.getHeight() * a);
/* 326 */     double y2 = Math.rint(y0 + bar.getHeight() * b);
/* 327 */     double y3 = Math.rint(y0 + bar.getHeight() * c);
/* 328 */     result[0] = new Rectangle2D.Double(bar.getMinX(), bar.getMinY(), bar
/* 329 */         .getWidth(), y1 - y0);
/* 330 */     result[1] = new Rectangle2D.Double(bar.getMinX(), y1, bar.getWidth(), y2 - y1);
/*     */     
/* 332 */     result[2] = new Rectangle2D.Double(bar.getMinX(), y2, bar.getWidth(), y3 - y2);
/*     */     
/* 334 */     result[3] = new Rectangle2D.Double(bar.getMinX(), y3, bar.getWidth(), bar
/* 335 */         .getMaxY() - y3);
/* 336 */     return result;
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
/* 348 */     if (obj == this) {
/* 349 */       return true;
/*     */     }
/* 351 */     if (!(obj instanceof GradientBarPainter)) {
/* 352 */       return false;
/*     */     }
/* 354 */     GradientBarPainter that = (GradientBarPainter)obj;
/* 355 */     if (this.g1 != that.g1) {
/* 356 */       return false;
/*     */     }
/* 358 */     if (this.g2 != that.g2) {
/* 359 */       return false;
/*     */     }
/* 361 */     if (this.g3 != that.g3) {
/* 362 */       return false;
/*     */     }
/* 364 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 374 */     int hash = 37;
/* 375 */     hash = HashUtilities.hashCode(hash, this.g1);
/* 376 */     hash = HashUtilities.hashCode(hash, this.g2);
/* 377 */     hash = HashUtilities.hashCode(hash, this.g3);
/* 378 */     return hash;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/GradientBarPainter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */