/*     */ package org.jfree.chart.plot.dial;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.Arc2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardDialRange
/*     */   extends AbstractDialLayer
/*     */   implements DialLayer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 345515648249364904L;
/*     */   private int scaleIndex;
/*     */   private double lowerBound;
/*     */   private double upperBound;
/*     */   private transient Paint paint;
/*     */   private double innerRadius;
/*     */   private double outerRadius;
/*     */   
/*     */   public StandardDialRange() {
/* 106 */     this(0.0D, 100.0D, Color.white);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StandardDialRange(double lower, double upper, Paint paint) {
/* 117 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 118 */     this.scaleIndex = 0;
/* 119 */     this.lowerBound = lower;
/* 120 */     this.upperBound = upper;
/* 121 */     this.innerRadius = 0.48D;
/* 122 */     this.outerRadius = 0.52D;
/* 123 */     this.paint = paint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScaleIndex() {
/* 134 */     return this.scaleIndex;
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
/*     */   public void setScaleIndex(int index) {
/* 146 */     this.scaleIndex = index;
/* 147 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLowerBound() {
/* 158 */     return this.lowerBound;
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
/*     */   public void setLowerBound(double bound) {
/* 170 */     if (bound >= this.upperBound) {
/* 171 */       throw new IllegalArgumentException("Lower bound must be less than upper bound.");
/*     */     }
/*     */     
/* 174 */     this.lowerBound = bound;
/* 175 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getUpperBound() {
/* 186 */     return this.upperBound;
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
/*     */   public void setUpperBound(double bound) {
/* 198 */     if (bound <= this.lowerBound) {
/* 199 */       throw new IllegalArgumentException("Lower bound must be less than upper bound.");
/*     */     }
/*     */     
/* 202 */     this.upperBound = bound;
/* 203 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBounds(double lower, double upper) {
/* 214 */     if (lower >= upper) {
/* 215 */       throw new IllegalArgumentException("Lower must be less than upper.");
/*     */     }
/*     */     
/* 218 */     this.lowerBound = lower;
/* 219 */     this.upperBound = upper;
/* 220 */     notifyListeners(new DialLayerChangeEvent(this));
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
/* 231 */     return this.paint;
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
/* 243 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 244 */     this.paint = paint;
/* 245 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInnerRadius() {
/* 256 */     return this.innerRadius;
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
/*     */   public void setInnerRadius(double radius) {
/* 268 */     this.innerRadius = radius;
/* 269 */     notifyListeners(new DialLayerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOuterRadius() {
/* 280 */     return this.outerRadius;
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
/*     */   public void setOuterRadius(double radius) {
/* 292 */     this.outerRadius = radius;
/* 293 */     notifyListeners(new DialLayerChangeEvent(this));
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
/* 304 */     return true;
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
/*     */   public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
/* 319 */     Rectangle2D arcRectInner = DialPlot.rectangleByRadius(frame, this.innerRadius, this.innerRadius);
/*     */     
/* 321 */     Rectangle2D arcRectOuter = DialPlot.rectangleByRadius(frame, this.outerRadius, this.outerRadius);
/*     */ 
/*     */     
/* 324 */     DialScale scale = plot.getScale(this.scaleIndex);
/* 325 */     if (scale == null) {
/* 326 */       throw new RuntimeException("No scale for scaleIndex = " + this.scaleIndex);
/*     */     }
/*     */     
/* 329 */     double angleMin = scale.valueToAngle(this.lowerBound);
/* 330 */     double angleMax = scale.valueToAngle(this.upperBound);
/*     */     
/* 332 */     Arc2D arcInner = new Arc2D.Double(arcRectInner, angleMin, angleMax - angleMin, 0);
/*     */     
/* 334 */     Arc2D arcOuter = new Arc2D.Double(arcRectOuter, angleMax, angleMin - angleMax, 0);
/*     */ 
/*     */     
/* 337 */     g2.setPaint(this.paint);
/* 338 */     g2.setStroke(new BasicStroke(2.0F));
/* 339 */     g2.draw(arcInner);
/* 340 */     g2.draw(arcOuter);
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
/* 352 */     if (obj == this) {
/* 353 */       return true;
/*     */     }
/* 355 */     if (!(obj instanceof StandardDialRange)) {
/* 356 */       return false;
/*     */     }
/* 358 */     StandardDialRange that = (StandardDialRange)obj;
/* 359 */     if (this.scaleIndex != that.scaleIndex) {
/* 360 */       return false;
/*     */     }
/* 362 */     if (this.lowerBound != that.lowerBound) {
/* 363 */       return false;
/*     */     }
/* 365 */     if (this.upperBound != that.upperBound) {
/* 366 */       return false;
/*     */     }
/* 368 */     if (!PaintUtilities.equal(this.paint, that.paint)) {
/* 369 */       return false;
/*     */     }
/* 371 */     if (this.innerRadius != that.innerRadius) {
/* 372 */       return false;
/*     */     }
/* 374 */     if (this.outerRadius != that.outerRadius) {
/* 375 */       return false;
/*     */     }
/* 377 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 387 */     int result = 193;
/* 388 */     long temp = Double.doubleToLongBits(this.lowerBound);
/* 389 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 390 */     temp = Double.doubleToLongBits(this.upperBound);
/* 391 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 392 */     temp = Double.doubleToLongBits(this.innerRadius);
/* 393 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 394 */     temp = Double.doubleToLongBits(this.outerRadius);
/* 395 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 396 */     result = 37 * result + HashUtilities.hashCodeForPaint(this.paint);
/* 397 */     return result;
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
/* 410 */     return super.clone();
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
/* 421 */     stream.defaultWriteObject();
/* 422 */     SerialUtilities.writePaint(this.paint, stream);
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
/* 435 */     stream.defaultReadObject();
/* 436 */     this.paint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/dial/StandardDialRange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */