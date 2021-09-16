/*     */ package org.jfree.chart.plot.dial;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.Arc2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.text.TextUtilities;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ import org.jfree.util.PaintUtilities;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DialTextAnnotation
/*     */   extends AbstractDialLayer
/*     */   implements DialLayer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 3065267524054428071L;
/*     */   private String label;
/*     */   private Font font;
/*     */   private transient Paint paint;
/*     */   private double angle;
/*     */   private double radius;
/*     */   private TextAnchor anchor;
/*     */   
/*     */   public DialTextAnnotation(String label) {
/* 105 */     ParamChecks.nullNotPermitted(label, "label");
/* 106 */     this.angle = -90.0D;
/* 107 */     this.radius = 0.3D;
/* 108 */     this.font = new Font("Dialog", 1, 14);
/* 109 */     this.paint = Color.black;
/* 110 */     this.label = label;
/* 111 */     this.anchor = TextAnchor.TOP_CENTER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLabel() {
/* 122 */     return this.label;
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
/*     */   public void setLabel(String label) {
/* 134 */     ParamChecks.nullNotPermitted(label, "label");
/* 135 */     this.label = label;
/* 136 */     notifyListeners(new DialLayerChangeEvent(this));
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
/* 147 */     return this.font;
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
/* 159 */     ParamChecks.nullNotPermitted(font, "font");
/* 160 */     this.font = font;
/* 161 */     notifyListeners(new DialLayerChangeEvent(this));
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
/* 172 */     return this.paint;
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
/* 184 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 185 */     this.paint = paint;
/* 186 */     notifyListeners(new DialLayerChangeEvent(this));
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
/*     */   public double getAngle() {
/* 198 */     return this.angle;
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
/*     */   public void setAngle(double angle) {
/* 211 */     this.angle = angle;
/* 212 */     notifyListeners(new DialLayerChangeEvent(this));
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
/*     */   public double getRadius() {
/* 225 */     return this.radius;
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
/*     */   public void setRadius(double radius) {
/* 239 */     if (radius < 0.0D) {
/* 240 */       throw new IllegalArgumentException("The 'radius' cannot be negative.");
/*     */     }
/*     */     
/* 243 */     this.radius = radius;
/* 244 */     notifyListeners(new DialLayerChangeEvent(this));
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
/*     */   public TextAnchor getAnchor() {
/* 256 */     return this.anchor;
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
/*     */   public void setAnchor(TextAnchor anchor) {
/* 268 */     ParamChecks.nullNotPermitted(anchor, "anchor");
/* 269 */     this.anchor = anchor;
/* 270 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClippedToWindow() {
/* 281 */     return true;
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
/*     */   public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
/* 299 */     Rectangle2D f = DialPlot.rectangleByRadius(frame, this.radius, this.radius);
/*     */     
/* 301 */     Arc2D arc = new Arc2D.Double(f, this.angle, 0.0D, 0);
/* 302 */     Point2D pt = arc.getStartPoint();
/* 303 */     g2.setPaint(this.paint);
/* 304 */     g2.setFont(this.font);
/* 305 */     TextUtilities.drawAlignedString(this.label, g2, (float)pt.getX(), 
/* 306 */         (float)pt.getY(), this.anchor);
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
/* 319 */     if (obj == this) {
/* 320 */       return true;
/*     */     }
/* 322 */     if (!(obj instanceof DialTextAnnotation)) {
/* 323 */       return false;
/*     */     }
/* 325 */     DialTextAnnotation that = (DialTextAnnotation)obj;
/* 326 */     if (!this.label.equals(that.label)) {
/* 327 */       return false;
/*     */     }
/* 329 */     if (!this.font.equals(that.font)) {
/* 330 */       return false;
/*     */     }
/* 332 */     if (!PaintUtilities.equal(this.paint, that.paint)) {
/* 333 */       return false;
/*     */     }
/* 335 */     if (this.radius != that.radius) {
/* 336 */       return false;
/*     */     }
/* 338 */     if (this.angle != that.angle) {
/* 339 */       return false;
/*     */     }
/* 341 */     if (!this.anchor.equals(that.anchor)) {
/* 342 */       return false;
/*     */     }
/* 344 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 354 */     int result = 193;
/* 355 */     result = 37 * result + HashUtilities.hashCodeForPaint(this.paint);
/* 356 */     result = 37 * result + this.font.hashCode();
/* 357 */     result = 37 * result + this.label.hashCode();
/* 358 */     result = 37 * result + this.anchor.hashCode();
/* 359 */     long temp = Double.doubleToLongBits(this.angle);
/* 360 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 361 */     temp = Double.doubleToLongBits(this.radius);
/* 362 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 363 */     return result;
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
/* 376 */     return super.clone();
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
/* 387 */     stream.defaultWriteObject();
/* 388 */     SerialUtilities.writePaint(this.paint, stream);
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
/* 401 */     stream.defaultReadObject();
/* 402 */     this.paint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/dial/DialTextAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */