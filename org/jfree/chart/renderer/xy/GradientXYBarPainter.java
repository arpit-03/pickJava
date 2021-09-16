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
/*     */ public class GradientXYBarPainter
/*     */   implements XYBarPainter, Serializable
/*     */ {
/*     */   private double g1;
/*     */   private double g2;
/*     */   private double g3;
/*     */   
/*     */   public GradientXYBarPainter() {
/*  77 */     this(0.1D, 0.2D, 0.8D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientXYBarPainter(double g1, double g2, double g3) {
/*  88 */     this.g1 = g1;
/*  89 */     this.g2 = g2;
/*  90 */     this.g3 = g3;
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
/*     */   public void paintBar(Graphics2D g2, XYBarRenderer renderer, int row, int column, RectangularShape bar, RectangleEdge base) {
/*     */     Color c0, c1;
/* 108 */     Paint itemPaint = renderer.getItemPaint(row, column);
/*     */ 
/*     */     
/* 111 */     if (itemPaint instanceof Color) {
/* 112 */       c0 = (Color)itemPaint;
/* 113 */       c1 = c0.brighter();
/*     */     }
/* 115 */     else if (itemPaint instanceof GradientPaint) {
/* 116 */       GradientPaint gp = (GradientPaint)itemPaint;
/* 117 */       c0 = gp.getColor1();
/* 118 */       c1 = gp.getColor2();
/*     */     } else {
/*     */       
/* 121 */       c0 = Color.blue;
/* 122 */       c1 = Color.blue.brighter();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 127 */     if (c0.getAlpha() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 131 */     if (base == RectangleEdge.TOP || base == RectangleEdge.BOTTOM) {
/* 132 */       Rectangle2D[] regions = splitVerticalBar(bar, this.g1, this.g2, this.g3);
/*     */ 
/*     */       
/* 135 */       GradientPaint gp = new GradientPaint((float)regions[0].getMinX(), 0.0F, c0, (float)regions[0].getMaxX(), 0.0F, Color.white);
/* 136 */       g2.setPaint(gp);
/* 137 */       g2.fill(regions[0]);
/*     */ 
/*     */       
/* 140 */       gp = new GradientPaint((float)regions[1].getMinX(), 0.0F, Color.white, (float)regions[1].getMaxX(), 0.0F, c0);
/* 141 */       g2.setPaint(gp);
/* 142 */       g2.fill(regions[1]);
/*     */ 
/*     */       
/* 145 */       gp = new GradientPaint((float)regions[2].getMinX(), 0.0F, c0, (float)regions[2].getMaxX(), 0.0F, c1);
/* 146 */       g2.setPaint(gp);
/* 147 */       g2.fill(regions[2]);
/*     */ 
/*     */       
/* 150 */       gp = new GradientPaint((float)regions[3].getMinX(), 0.0F, c1, (float)regions[3].getMaxX(), 0.0F, c0);
/* 151 */       g2.setPaint(gp);
/* 152 */       g2.fill(regions[3]);
/*     */     }
/* 154 */     else if (base == RectangleEdge.LEFT || base == RectangleEdge.RIGHT) {
/* 155 */       Rectangle2D[] regions = splitHorizontalBar(bar, this.g1, this.g2, this.g3);
/*     */ 
/*     */ 
/*     */       
/* 159 */       GradientPaint gp = new GradientPaint(0.0F, (float)regions[0].getMinY(), c0, 0.0F, (float)regions[0].getMaxX(), Color.white);
/* 160 */       g2.setPaint(gp);
/* 161 */       g2.fill(regions[0]);
/*     */ 
/*     */       
/* 164 */       gp = new GradientPaint(0.0F, (float)regions[1].getMinY(), Color.white, 0.0F, (float)regions[1].getMaxY(), c0);
/* 165 */       g2.setPaint(gp);
/* 166 */       g2.fill(regions[1]);
/*     */ 
/*     */       
/* 169 */       gp = new GradientPaint(0.0F, (float)regions[2].getMinY(), c0, 0.0F, (float)regions[2].getMaxY(), c1);
/* 170 */       g2.setPaint(gp);
/* 171 */       g2.fill(regions[2]);
/*     */ 
/*     */       
/* 174 */       gp = new GradientPaint(0.0F, (float)regions[3].getMinY(), c1, 0.0F, (float)regions[3].getMaxY(), c0);
/* 175 */       g2.setPaint(gp);
/* 176 */       g2.fill(regions[3]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 181 */     if (renderer.isDrawBarOutline()) {
/* 182 */       Stroke stroke = renderer.getItemOutlineStroke(row, column);
/* 183 */       Paint paint = renderer.getItemOutlinePaint(row, column);
/* 184 */       if (stroke != null && paint != null) {
/* 185 */         g2.setStroke(stroke);
/* 186 */         g2.setPaint(paint);
/* 187 */         g2.draw(bar);
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
/* 212 */     Paint itemPaint = renderer.getItemPaint(row, column);
/* 213 */     if (itemPaint instanceof Color) {
/* 214 */       Color c = (Color)itemPaint;
/* 215 */       if (c.getAlpha() == 0) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 220 */     RectangularShape shadow = createShadow(bar, renderer.getShadowXOffset(), renderer
/* 221 */         .getShadowYOffset(), base, pegShadow);
/* 222 */     g2.setPaint(Color.gray);
/* 223 */     g2.fill(shadow);
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
/* 240 */     double x0 = bar.getMinX();
/* 241 */     double x1 = bar.getMaxX();
/* 242 */     double y0 = bar.getMinY();
/* 243 */     double y1 = bar.getMaxY();
/* 244 */     if (base == RectangleEdge.TOP) {
/* 245 */       x0 += xOffset;
/* 246 */       x1 += xOffset;
/* 247 */       if (!pegShadow) {
/* 248 */         y0 += yOffset;
/*     */       }
/* 250 */       y1 += yOffset;
/*     */     }
/* 252 */     else if (base == RectangleEdge.BOTTOM) {
/* 253 */       x0 += xOffset;
/* 254 */       x1 += xOffset;
/* 255 */       y0 += yOffset;
/* 256 */       if (!pegShadow) {
/* 257 */         y1 += yOffset;
/*     */       }
/*     */     }
/* 260 */     else if (base == RectangleEdge.LEFT) {
/* 261 */       if (!pegShadow) {
/* 262 */         x0 += xOffset;
/*     */       }
/* 264 */       x1 += xOffset;
/* 265 */       y0 += yOffset;
/* 266 */       y1 += yOffset;
/*     */     }
/* 268 */     else if (base == RectangleEdge.RIGHT) {
/* 269 */       x0 += xOffset;
/* 270 */       if (!pegShadow) {
/* 271 */         x1 += xOffset;
/*     */       }
/* 273 */       y0 += yOffset;
/* 274 */       y1 += yOffset;
/*     */     } 
/* 276 */     return new Rectangle2D.Double(x0, y0, x1 - x0, y1 - y0);
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
/* 292 */     Rectangle2D[] result = new Rectangle2D[4];
/* 293 */     double x0 = bar.getMinX();
/* 294 */     double x1 = Math.rint(x0 + bar.getWidth() * a);
/* 295 */     double x2 = Math.rint(x0 + bar.getWidth() * b);
/* 296 */     double x3 = Math.rint(x0 + bar.getWidth() * c);
/* 297 */     result[0] = new Rectangle2D.Double(bar.getMinX(), bar.getMinY(), x1 - x0, bar
/* 298 */         .getHeight());
/* 299 */     result[1] = new Rectangle2D.Double(x1, bar.getMinY(), x2 - x1, bar
/* 300 */         .getHeight());
/* 301 */     result[2] = new Rectangle2D.Double(x2, bar.getMinY(), x3 - x2, bar
/* 302 */         .getHeight());
/* 303 */     result[3] = new Rectangle2D.Double(x3, bar.getMinY(), bar
/* 304 */         .getMaxX() - x3, bar.getHeight());
/* 305 */     return result;
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
/* 321 */     Rectangle2D[] result = new Rectangle2D[4];
/* 322 */     double y0 = bar.getMinY();
/* 323 */     double y1 = Math.rint(y0 + bar.getHeight() * a);
/* 324 */     double y2 = Math.rint(y0 + bar.getHeight() * b);
/* 325 */     double y3 = Math.rint(y0 + bar.getHeight() * c);
/* 326 */     result[0] = new Rectangle2D.Double(bar.getMinX(), bar.getMinY(), bar
/* 327 */         .getWidth(), y1 - y0);
/* 328 */     result[1] = new Rectangle2D.Double(bar.getMinX(), y1, bar.getWidth(), y2 - y1);
/*     */     
/* 330 */     result[2] = new Rectangle2D.Double(bar.getMinX(), y2, bar.getWidth(), y3 - y2);
/*     */     
/* 332 */     result[3] = new Rectangle2D.Double(bar.getMinX(), y3, bar.getWidth(), bar
/* 333 */         .getMaxY() - y3);
/* 334 */     return result;
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
/* 346 */     if (obj == this) {
/* 347 */       return true;
/*     */     }
/* 349 */     if (!(obj instanceof GradientXYBarPainter)) {
/* 350 */       return false;
/*     */     }
/* 352 */     GradientXYBarPainter that = (GradientXYBarPainter)obj;
/* 353 */     if (this.g1 != that.g1) {
/* 354 */       return false;
/*     */     }
/* 356 */     if (this.g2 != that.g2) {
/* 357 */       return false;
/*     */     }
/* 359 */     if (this.g3 != that.g3) {
/* 360 */       return false;
/*     */     }
/* 362 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 372 */     int hash = 37;
/* 373 */     hash = HashUtilities.hashCode(hash, this.g1);
/* 374 */     hash = HashUtilities.hashCode(hash, this.g2);
/* 375 */     hash = HashUtilities.hashCode(hash, this.g3);
/* 376 */     return hash;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/GradientXYBarPainter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */