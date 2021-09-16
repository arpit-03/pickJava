/*     */ package org.jfree.text;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleAnchor;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.ui.Size2D;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextBox
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3360220213180203706L;
/*     */   private transient Paint outlinePaint;
/*     */   private transient Stroke outlineStroke;
/*     */   private RectangleInsets interiorGap;
/*     */   private transient Paint backgroundPaint;
/*     */   private transient Paint shadowPaint;
/*  94 */   private double shadowXOffset = 2.0D;
/*     */ 
/*     */   
/*  97 */   private double shadowYOffset = 2.0D;
/*     */ 
/*     */ 
/*     */   
/*     */   private TextBlock textBlock;
/*     */ 
/*     */ 
/*     */   
/*     */   public TextBox() {
/* 106 */     this((TextBlock)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextBox(String text) {
/* 115 */     this((TextBlock)null);
/* 116 */     if (text != null) {
/* 117 */       this.textBlock = new TextBlock();
/* 118 */       this.textBlock.addLine(text, new Font("SansSerif", 0, 10), Color.black);
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
/*     */   public TextBox(TextBlock block) {
/* 131 */     this.outlinePaint = Color.black;
/* 132 */     this.outlineStroke = new BasicStroke(1.0F);
/* 133 */     this.interiorGap = new RectangleInsets(1.0D, 3.0D, 1.0D, 3.0D);
/* 134 */     this.backgroundPaint = new Color(255, 255, 192);
/* 135 */     this.shadowPaint = Color.gray;
/* 136 */     this.shadowXOffset = 2.0D;
/* 137 */     this.shadowYOffset = 2.0D;
/* 138 */     this.textBlock = block;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getOutlinePaint() {
/* 147 */     return this.outlinePaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutlinePaint(Paint paint) {
/* 156 */     this.outlinePaint = paint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getOutlineStroke() {
/* 165 */     return this.outlineStroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutlineStroke(Stroke stroke) {
/* 174 */     this.outlineStroke = stroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleInsets getInteriorGap() {
/* 183 */     return this.interiorGap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInteriorGap(RectangleInsets gap) {
/* 192 */     this.interiorGap = gap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getBackgroundPaint() {
/* 201 */     return this.backgroundPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgroundPaint(Paint paint) {
/* 210 */     this.backgroundPaint = paint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getShadowPaint() {
/* 219 */     return this.shadowPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShadowPaint(Paint paint) {
/* 228 */     this.shadowPaint = paint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getShadowXOffset() {
/* 237 */     return this.shadowXOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShadowXOffset(double offset) {
/* 246 */     this.shadowXOffset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getShadowYOffset() {
/* 255 */     return this.shadowYOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShadowYOffset(double offset) {
/* 264 */     this.shadowYOffset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextBlock getTextBlock() {
/* 273 */     return this.textBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextBlock(TextBlock block) {
/* 282 */     this.textBlock = block;
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
/*     */   public void draw(Graphics2D g2, float x, float y, RectangleAnchor anchor) {
/* 296 */     Size2D d1 = this.textBlock.calculateDimensions(g2);
/* 297 */     double w = this.interiorGap.extendWidth(d1.getWidth());
/* 298 */     double h = this.interiorGap.extendHeight(d1.getHeight());
/* 299 */     Size2D d2 = new Size2D(w, h);
/*     */     
/* 301 */     Rectangle2D bounds = RectangleAnchor.createRectangle(d2, x, y, anchor);
/* 302 */     double xx = bounds.getX();
/* 303 */     double yy = bounds.getY();
/*     */     
/* 305 */     if (this.shadowPaint != null) {
/*     */ 
/*     */       
/* 308 */       Rectangle2D shadow = new Rectangle2D.Double(xx + this.shadowXOffset, yy + this.shadowYOffset, bounds.getWidth(), bounds.getHeight());
/* 309 */       g2.setPaint(this.shadowPaint);
/* 310 */       g2.fill(shadow);
/*     */     } 
/* 312 */     if (this.backgroundPaint != null) {
/* 313 */       g2.setPaint(this.backgroundPaint);
/* 314 */       g2.fill(bounds);
/*     */     } 
/*     */     
/* 317 */     if (this.outlinePaint != null && this.outlineStroke != null) {
/* 318 */       g2.setPaint(this.outlinePaint);
/* 319 */       g2.setStroke(this.outlineStroke);
/* 320 */       g2.draw(bounds);
/*     */     } 
/*     */     
/* 323 */     this.textBlock.draw(g2, 
/* 324 */         (float)(xx + this.interiorGap.calculateLeftInset(w)), 
/* 325 */         (float)(yy + this.interiorGap.calculateTopInset(h)), TextBlockAnchor.TOP_LEFT);
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
/*     */   public double getHeight(Graphics2D g2) {
/* 338 */     Size2D d = this.textBlock.calculateDimensions(g2);
/* 339 */     return this.interiorGap.extendHeight(d.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 350 */     if (obj == this) {
/* 351 */       return true;
/*     */     }
/* 353 */     if (!(obj instanceof TextBox)) {
/* 354 */       return false;
/*     */     }
/* 356 */     TextBox that = (TextBox)obj;
/* 357 */     if (!ObjectUtilities.equal(this.outlinePaint, that.outlinePaint)) {
/* 358 */       return false;
/*     */     }
/* 360 */     if (!ObjectUtilities.equal(this.outlineStroke, that.outlineStroke)) {
/* 361 */       return false;
/*     */     }
/* 363 */     if (!ObjectUtilities.equal(this.interiorGap, that.interiorGap)) {
/* 364 */       return false;
/*     */     }
/* 366 */     if (!ObjectUtilities.equal(this.backgroundPaint, that.backgroundPaint))
/*     */     {
/* 368 */       return false;
/*     */     }
/* 370 */     if (!ObjectUtilities.equal(this.shadowPaint, that.shadowPaint)) {
/* 371 */       return false;
/*     */     }
/* 373 */     if (this.shadowXOffset != that.shadowXOffset) {
/* 374 */       return false;
/*     */     }
/* 376 */     if (this.shadowYOffset != that.shadowYOffset) {
/* 377 */       return false;
/*     */     }
/* 379 */     if (!ObjectUtilities.equal(this.textBlock, that.textBlock)) {
/* 380 */       return false;
/*     */     }
/*     */     
/* 383 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 394 */     int result = (this.outlinePaint != null) ? this.outlinePaint.hashCode() : 0;
/*     */     
/* 396 */     result = 29 * result + ((this.outlineStroke != null) ? this.outlineStroke.hashCode() : 0);
/*     */     
/* 398 */     result = 29 * result + ((this.interiorGap != null) ? this.interiorGap.hashCode() : 0);
/*     */     
/* 400 */     result = 29 * result + ((this.backgroundPaint != null) ? this.backgroundPaint.hashCode() : 0);
/*     */     
/* 402 */     result = 29 * result + ((this.shadowPaint != null) ? this.shadowPaint.hashCode() : 0);
/*     */     
/* 404 */     long temp = (this.shadowXOffset != 0.0D) ? Double.doubleToLongBits(this.shadowXOffset) : 0L;
/* 405 */     result = 29 * result + (int)(temp ^ temp >>> 32L);
/*     */     
/* 407 */     temp = (this.shadowYOffset != 0.0D) ? Double.doubleToLongBits(this.shadowYOffset) : 0L;
/* 408 */     result = 29 * result + (int)(temp ^ temp >>> 32L);
/*     */     
/* 410 */     result = 29 * result + ((this.textBlock != null) ? this.textBlock.hashCode() : 0);
/* 411 */     return result;
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
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 423 */     stream.defaultWriteObject();
/* 424 */     SerialUtilities.writePaint(this.outlinePaint, stream);
/* 425 */     SerialUtilities.writeStroke(this.outlineStroke, stream);
/* 426 */     SerialUtilities.writePaint(this.backgroundPaint, stream);
/* 427 */     SerialUtilities.writePaint(this.shadowPaint, stream);
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
/* 440 */     stream.defaultReadObject();
/* 441 */     this.outlinePaint = SerialUtilities.readPaint(stream);
/* 442 */     this.outlineStroke = SerialUtilities.readStroke(stream);
/* 443 */     this.backgroundPaint = SerialUtilities.readPaint(stream);
/* 444 */     this.shadowPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/text/TextBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */