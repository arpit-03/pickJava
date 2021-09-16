/*     */ package org.jfree.chart.needle;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PaintUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MeterNeedle
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5203064851510951052L;
/*  80 */   private transient Paint outlinePaint = Color.black;
/*     */ 
/*     */   
/*  83 */   private transient Stroke outlineStroke = new BasicStroke(2.0F);
/*     */ 
/*     */   
/*  86 */   private transient Paint fillPaint = null;
/*     */ 
/*     */   
/*  89 */   private transient Paint highlightPaint = null;
/*     */ 
/*     */   
/*  92 */   private int size = 5;
/*     */ 
/*     */   
/*  95 */   private double rotateX = 0.5D;
/*     */ 
/*     */   
/*  98 */   private double rotateY = 0.5D;
/*     */ 
/*     */   
/* 101 */   protected static AffineTransform transform = new AffineTransform();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MeterNeedle() {
/* 107 */     this(null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MeterNeedle(Paint outline, Paint fill, Paint highlight) {
/* 118 */     this.fillPaint = fill;
/* 119 */     this.highlightPaint = highlight;
/* 120 */     this.outlinePaint = outline;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getOutlinePaint() {
/* 129 */     return this.outlinePaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutlinePaint(Paint p) {
/* 138 */     if (p != null) {
/* 139 */       this.outlinePaint = p;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getOutlineStroke() {
/* 149 */     return this.outlineStroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutlineStroke(Stroke s) {
/* 158 */     if (s != null) {
/* 159 */       this.outlineStroke = s;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getFillPaint() {
/* 169 */     return this.fillPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFillPaint(Paint p) {
/* 178 */     if (p != null) {
/* 179 */       this.fillPaint = p;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getHighlightPaint() {
/* 189 */     return this.highlightPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHighlightPaint(Paint p) {
/* 198 */     if (p != null) {
/* 199 */       this.highlightPaint = p;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRotateX() {
/* 209 */     return this.rotateX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotateX(double x) {
/* 218 */     this.rotateX = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotateY(double y) {
/* 227 */     this.rotateY = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRotateY() {
/* 236 */     return this.rotateY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(Graphics2D g2, Rectangle2D plotArea) {
/* 246 */     draw(g2, plotArea, 0.0D);
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
/*     */   public void draw(Graphics2D g2, Rectangle2D plotArea, double angle) {
/* 258 */     Point2D.Double pt = new Point2D.Double();
/* 259 */     pt.setLocation(plotArea
/* 260 */         .getMinX() + this.rotateX * plotArea.getWidth(), plotArea
/* 261 */         .getMinY() + this.rotateY * plotArea.getHeight());
/*     */     
/* 263 */     draw(g2, plotArea, pt, angle);
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
/*     */   public void draw(Graphics2D g2, Rectangle2D plotArea, Point2D rotate, double angle) {
/* 278 */     Paint savePaint = g2.getColor();
/* 279 */     Stroke saveStroke = g2.getStroke();
/*     */     
/* 281 */     drawNeedle(g2, plotArea, rotate, Math.toRadians(angle));
/*     */     
/* 283 */     g2.setStroke(saveStroke);
/* 284 */     g2.setPaint(savePaint);
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
/*     */   protected abstract void drawNeedle(Graphics2D paramGraphics2D, Rectangle2D paramRectangle2D, Point2D paramPoint2D, double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void defaultDisplay(Graphics2D g2, Shape shape) {
/* 308 */     if (this.fillPaint != null) {
/* 309 */       g2.setPaint(this.fillPaint);
/* 310 */       g2.fill(shape);
/*     */     } 
/*     */     
/* 313 */     if (this.outlinePaint != null) {
/* 314 */       g2.setStroke(this.outlineStroke);
/* 315 */       g2.setPaint(this.outlinePaint);
/* 316 */       g2.draw(shape);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 327 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSize(int pixels) {
/* 336 */     this.size = pixels;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AffineTransform getTransform() {
/* 345 */     return transform;
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
/* 357 */     if (obj == this) {
/* 358 */       return true;
/*     */     }
/* 360 */     if (!(obj instanceof MeterNeedle)) {
/* 361 */       return false;
/*     */     }
/* 363 */     MeterNeedle that = (MeterNeedle)obj;
/* 364 */     if (!PaintUtilities.equal(this.outlinePaint, that.outlinePaint)) {
/* 365 */       return false;
/*     */     }
/* 367 */     if (!ObjectUtilities.equal(this.outlineStroke, that.outlineStroke)) {
/* 368 */       return false;
/*     */     }
/* 370 */     if (!PaintUtilities.equal(this.fillPaint, that.fillPaint)) {
/* 371 */       return false;
/*     */     }
/* 373 */     if (!PaintUtilities.equal(this.highlightPaint, that.highlightPaint)) {
/* 374 */       return false;
/*     */     }
/* 376 */     if (this.size != that.size) {
/* 377 */       return false;
/*     */     }
/* 379 */     if (this.rotateX != that.rotateX) {
/* 380 */       return false;
/*     */     }
/* 382 */     if (this.rotateY != that.rotateY) {
/* 383 */       return false;
/*     */     }
/* 385 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 395 */     int result = HashUtilities.hashCode(193, this.fillPaint);
/* 396 */     result = HashUtilities.hashCode(result, this.highlightPaint);
/* 397 */     result = HashUtilities.hashCode(result, this.outlinePaint);
/* 398 */     result = HashUtilities.hashCode(result, this.outlineStroke);
/* 399 */     result = HashUtilities.hashCode(result, this.rotateX);
/* 400 */     result = HashUtilities.hashCode(result, this.rotateY);
/* 401 */     result = HashUtilities.hashCode(result, this.size);
/* 402 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 413 */     stream.defaultWriteObject();
/* 414 */     SerialUtilities.writeStroke(this.outlineStroke, stream);
/* 415 */     SerialUtilities.writePaint(this.outlinePaint, stream);
/* 416 */     SerialUtilities.writePaint(this.fillPaint, stream);
/* 417 */     SerialUtilities.writePaint(this.highlightPaint, stream);
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 430 */     stream.defaultReadObject();
/* 431 */     this.outlineStroke = SerialUtilities.readStroke(stream);
/* 432 */     this.outlinePaint = SerialUtilities.readPaint(stream);
/* 433 */     this.fillPaint = SerialUtilities.readPaint(stream);
/* 434 */     this.highlightPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/needle/MeterNeedle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */