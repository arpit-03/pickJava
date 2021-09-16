/*     */ package org.jfree.chart.annotations;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Paint;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.TextAnchor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextAnnotation
/*     */   extends AbstractAnnotation
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7008912287533127432L;
/*  84 */   public static final Font DEFAULT_FONT = new Font("SansSerif", 0, 10);
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static final Paint DEFAULT_PAINT = Color.black;
/*     */ 
/*     */   
/*  91 */   public static final TextAnchor DEFAULT_TEXT_ANCHOR = TextAnchor.CENTER;
/*     */ 
/*     */   
/*  94 */   public static final TextAnchor DEFAULT_ROTATION_ANCHOR = TextAnchor.CENTER;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final double DEFAULT_ROTATION_ANGLE = 0.0D;
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
/*     */   private TextAnchor textAnchor;
/*     */ 
/*     */   
/*     */   private TextAnchor rotationAnchor;
/*     */ 
/*     */   
/*     */   private double rotationAngle;
/*     */ 
/*     */ 
/*     */   
/*     */   protected TextAnnotation(String text) {
/* 124 */     ParamChecks.nullNotPermitted(text, "text");
/* 125 */     this.text = text;
/* 126 */     this.font = DEFAULT_FONT;
/* 127 */     this.paint = DEFAULT_PAINT;
/* 128 */     this.textAnchor = DEFAULT_TEXT_ANCHOR;
/* 129 */     this.rotationAnchor = DEFAULT_ROTATION_ANCHOR;
/* 130 */     this.rotationAngle = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 141 */     return this.text;
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
/*     */   public void setText(String text) {
/* 153 */     ParamChecks.nullNotPermitted(text, "text");
/* 154 */     this.text = text;
/* 155 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 166 */     return this.font;
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
/*     */   public void setFont(Font font) {
/* 178 */     ParamChecks.nullNotPermitted(font, "font");
/* 179 */     this.font = font;
/* 180 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getPaint() {
/* 191 */     return this.paint;
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
/*     */   public void setPaint(Paint paint) {
/* 203 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 204 */     this.paint = paint;
/* 205 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextAnchor getTextAnchor() {
/* 216 */     return this.textAnchor;
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
/*     */   public void setTextAnchor(TextAnchor anchor) {
/* 229 */     ParamChecks.nullNotPermitted(anchor, "anchor");
/* 230 */     this.textAnchor = anchor;
/* 231 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextAnchor getRotationAnchor() {
/* 242 */     return this.rotationAnchor;
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
/*     */   public void setRotationAnchor(TextAnchor anchor) {
/* 254 */     ParamChecks.nullNotPermitted(anchor, "anchor");
/* 255 */     this.rotationAnchor = anchor;
/* 256 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRotationAngle() {
/* 267 */     return this.rotationAngle;
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
/*     */   public void setRotationAngle(double angle) {
/* 279 */     this.rotationAngle = angle;
/* 280 */     fireAnnotationChanged();
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
/* 292 */     if (obj == this) {
/* 293 */       return true;
/*     */     }
/*     */     
/* 296 */     if (!(obj instanceof TextAnnotation)) {
/* 297 */       return false;
/*     */     }
/* 299 */     TextAnnotation that = (TextAnnotation)obj;
/* 300 */     if (!ObjectUtilities.equal(this.text, that.getText())) {
/* 301 */       return false;
/*     */     }
/* 303 */     if (!ObjectUtilities.equal(this.font, that.getFont())) {
/* 304 */       return false;
/*     */     }
/* 306 */     if (!PaintUtilities.equal(this.paint, that.getPaint())) {
/* 307 */       return false;
/*     */     }
/* 309 */     if (!ObjectUtilities.equal(this.textAnchor, that.getTextAnchor())) {
/* 310 */       return false;
/*     */     }
/* 312 */     if (!ObjectUtilities.equal(this.rotationAnchor, that
/* 313 */         .getRotationAnchor())) {
/* 314 */       return false;
/*     */     }
/* 316 */     if (this.rotationAngle != that.getRotationAngle()) {
/* 317 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 321 */     return true;
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
/* 332 */     int result = 193;
/* 333 */     result = 37 * result + this.font.hashCode();
/* 334 */     result = 37 * result + HashUtilities.hashCodeForPaint(this.paint);
/* 335 */     result = 37 * result + this.rotationAnchor.hashCode();
/* 336 */     long temp = Double.doubleToLongBits(this.rotationAngle);
/* 337 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 338 */     result = 37 * result + this.text.hashCode();
/* 339 */     result = 37 * result + this.textAnchor.hashCode();
/* 340 */     return result;
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
/* 351 */     stream.defaultWriteObject();
/* 352 */     SerialUtilities.writePaint(this.paint, stream);
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
/* 365 */     stream.defaultReadObject();
/* 366 */     this.paint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/TextAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */