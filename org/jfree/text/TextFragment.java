/*     */ package org.jfree.text;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.font.LineMetrics;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.Size2D;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ import org.jfree.util.Log;
/*     */ import org.jfree.util.LogContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextFragment
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4465945952903143262L;
/*  83 */   public static final Font DEFAULT_FONT = new Font("Serif", 0, 12);
/*     */ 
/*     */   
/*  86 */   public static final Paint DEFAULT_PAINT = Color.black;
/*     */ 
/*     */ 
/*     */   
/*     */   private String text;
/*     */ 
/*     */ 
/*     */   
/*     */   private Font font;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint paint;
/*     */ 
/*     */   
/*     */   private float baselineOffset;
/*     */ 
/*     */   
/* 104 */   protected static final LogContext logger = Log.createContext(TextFragment.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextFragment(String text) {
/* 113 */     this(text, DEFAULT_FONT, DEFAULT_PAINT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextFragment(String text, Font font) {
/* 123 */     this(text, font, DEFAULT_PAINT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextFragment(String text, Font font, Paint paint) {
/* 134 */     this(text, font, paint, 0.0F);
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
/*     */   public TextFragment(String text, Font font, Paint paint, float baselineOffset) {
/* 147 */     if (text == null) {
/* 148 */       throw new IllegalArgumentException("Null 'text' argument.");
/*     */     }
/* 150 */     if (font == null) {
/* 151 */       throw new IllegalArgumentException("Null 'font' argument.");
/*     */     }
/* 153 */     if (paint == null) {
/* 154 */       throw new IllegalArgumentException("Null 'paint' argument.");
/*     */     }
/* 156 */     this.text = text;
/* 157 */     this.font = font;
/* 158 */     this.paint = paint;
/* 159 */     this.baselineOffset = baselineOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 168 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 177 */     return this.font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getPaint() {
/* 186 */     return this.paint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBaselineOffset() {
/* 195 */     return this.baselineOffset;
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
/*     */   public void draw(Graphics2D g2, float anchorX, float anchorY, TextAnchor anchor, float rotateX, float rotateY, double angle) {
/* 215 */     g2.setFont(this.font);
/* 216 */     g2.setPaint(this.paint);
/* 217 */     TextUtilities.drawRotatedString(this.text, g2, anchorX, anchorY + this.baselineOffset, anchor, angle, rotateX, rotateY);
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
/*     */   public Size2D calculateDimensions(Graphics2D g2) {
/* 230 */     FontMetrics fm = g2.getFontMetrics(this.font);
/* 231 */     Rectangle2D bounds = TextUtilities.getTextBounds(this.text, g2, fm);
/*     */     
/* 233 */     Size2D result = new Size2D(bounds.getWidth(), bounds.getHeight());
/* 234 */     return result;
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
/*     */   public float calculateBaselineOffset(Graphics2D g2, TextAnchor anchor) {
/* 247 */     float result = 0.0F;
/* 248 */     FontMetrics fm = g2.getFontMetrics(this.font);
/* 249 */     LineMetrics lm = fm.getLineMetrics("ABCxyz", g2);
/* 250 */     if (anchor.isTop()) {
/* 251 */       result = lm.getAscent();
/*     */     }
/* 253 */     else if (anchor.isHalfAscent()) {
/* 254 */       result = lm.getAscent() / 2.0F;
/*     */     }
/* 256 */     else if (anchor.isVerticalCenter()) {
/* 257 */       result = lm.getAscent() / 2.0F - lm.getDescent() / 2.0F;
/*     */     }
/* 259 */     else if (anchor.isBottom()) {
/* 260 */       result = -lm.getDescent() - lm.getLeading();
/*     */     } 
/* 262 */     return result;
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
/* 273 */     if (obj == null) {
/* 274 */       return false;
/*     */     }
/* 276 */     if (obj == this) {
/* 277 */       return true;
/*     */     }
/* 279 */     if (obj instanceof TextFragment) {
/* 280 */       TextFragment tf = (TextFragment)obj;
/* 281 */       if (!this.text.equals(tf.text)) {
/* 282 */         return false;
/*     */       }
/* 284 */       if (!this.font.equals(tf.font)) {
/* 285 */         return false;
/*     */       }
/* 287 */       if (!this.paint.equals(tf.paint)) {
/* 288 */         return false;
/*     */       }
/* 290 */       return true;
/*     */     } 
/* 292 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 302 */     int result = (this.text != null) ? this.text.hashCode() : 0;
/* 303 */     result = 29 * result + ((this.font != null) ? this.font.hashCode() : 0);
/* 304 */     result = 29 * result + ((this.paint != null) ? this.paint.hashCode() : 0);
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
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 317 */     stream.defaultWriteObject();
/* 318 */     SerialUtilities.writePaint(this.paint, stream);
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
/* 331 */     stream.defaultReadObject();
/* 332 */     this.paint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/text/TextFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */