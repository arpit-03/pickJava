/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.labels.CrosshairLabelGenerator;
/*     */ import org.jfree.chart.labels.StandardCrosshairLabelGenerator;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleAnchor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Crosshair
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private boolean visible;
/*     */   private double value;
/*     */   private transient Paint paint;
/*     */   private transient Stroke stroke;
/*     */   private boolean labelVisible;
/*     */   private RectangleAnchor labelAnchor;
/*     */   private CrosshairLabelGenerator labelGenerator;
/*     */   private double labelXOffset;
/*     */   private double labelYOffset;
/*     */   private Font labelFont;
/*     */   private transient Paint labelPaint;
/*     */   private transient Paint labelBackgroundPaint;
/*     */   private boolean labelOutlineVisible;
/*     */   private transient Stroke labelOutlineStroke;
/*     */   private transient Paint labelOutlinePaint;
/*     */   private transient PropertyChangeSupport pcs;
/*     */   
/*     */   public Crosshair() {
/* 138 */     this(0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Crosshair(double value) {
/* 147 */     this(value, Color.black, new BasicStroke(1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Crosshair(double value, Paint paint, Stroke stroke) {
/* 158 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 159 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 160 */     this.visible = true;
/* 161 */     this.value = value;
/* 162 */     this.paint = paint;
/* 163 */     this.stroke = stroke;
/* 164 */     this.labelVisible = false;
/* 165 */     this.labelGenerator = (CrosshairLabelGenerator)new StandardCrosshairLabelGenerator();
/* 166 */     this.labelAnchor = RectangleAnchor.BOTTOM_LEFT;
/* 167 */     this.labelXOffset = 3.0D;
/* 168 */     this.labelYOffset = 3.0D;
/* 169 */     this.labelFont = new Font("Tahoma", 0, 12);
/* 170 */     this.labelPaint = Color.black;
/* 171 */     this.labelBackgroundPaint = new Color(0, 0, 255, 63);
/* 172 */     this.labelOutlineVisible = true;
/* 173 */     this.labelOutlinePaint = Color.black;
/* 174 */     this.labelOutlineStroke = new BasicStroke(0.5F);
/* 175 */     this.pcs = new PropertyChangeSupport(this);
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
/*     */   public boolean isVisible() {
/* 187 */     return this.visible;
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
/*     */   public void setVisible(boolean visible) {
/* 200 */     boolean old = this.visible;
/* 201 */     this.visible = visible;
/* 202 */     this.pcs.firePropertyChange("visible", old, visible);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValue() {
/* 213 */     return this.value;
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
/*     */   public void setValue(double value) {
/* 225 */     Double oldValue = new Double(this.value);
/* 226 */     this.value = value;
/* 227 */     this.pcs.firePropertyChange("value", oldValue, new Double(value));
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
/* 238 */     return this.paint;
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
/* 250 */     Paint old = this.paint;
/* 251 */     this.paint = paint;
/* 252 */     this.pcs.firePropertyChange("paint", old, paint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getStroke() {
/* 263 */     return this.stroke;
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
/*     */   public void setStroke(Stroke stroke) {
/* 275 */     Stroke old = this.stroke;
/* 276 */     this.stroke = stroke;
/* 277 */     this.pcs.firePropertyChange("stroke", old, stroke);
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
/*     */   public boolean isLabelVisible() {
/* 289 */     return this.labelVisible;
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
/*     */   public void setLabelVisible(boolean visible) {
/* 302 */     boolean old = this.labelVisible;
/* 303 */     this.labelVisible = visible;
/* 304 */     this.pcs.firePropertyChange("labelVisible", old, visible);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrosshairLabelGenerator getLabelGenerator() {
/* 315 */     return this.labelGenerator;
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
/*     */   public void setLabelGenerator(CrosshairLabelGenerator generator) {
/* 327 */     ParamChecks.nullNotPermitted(generator, "generator");
/* 328 */     CrosshairLabelGenerator old = this.labelGenerator;
/* 329 */     this.labelGenerator = generator;
/* 330 */     this.pcs.firePropertyChange("labelGenerator", old, generator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleAnchor getLabelAnchor() {
/* 341 */     return this.labelAnchor;
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
/*     */   public void setLabelAnchor(RectangleAnchor anchor) {
/* 353 */     RectangleAnchor old = this.labelAnchor;
/* 354 */     this.labelAnchor = anchor;
/* 355 */     this.pcs.firePropertyChange("labelAnchor", old, anchor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLabelXOffset() {
/* 366 */     return this.labelXOffset;
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
/*     */   public void setLabelXOffset(double offset) {
/* 378 */     Double old = new Double(this.labelXOffset);
/* 379 */     this.labelXOffset = offset;
/* 380 */     this.pcs.firePropertyChange("labelXOffset", old, new Double(offset));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLabelYOffset() {
/* 391 */     return this.labelYOffset;
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
/*     */   public void setLabelYOffset(double offset) {
/* 403 */     Double old = new Double(this.labelYOffset);
/* 404 */     this.labelYOffset = offset;
/* 405 */     this.pcs.firePropertyChange("labelYOffset", old, new Double(offset));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getLabelFont() {
/* 416 */     return this.labelFont;
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
/*     */   public void setLabelFont(Font font) {
/* 428 */     ParamChecks.nullNotPermitted(font, "font");
/* 429 */     Font old = this.labelFont;
/* 430 */     this.labelFont = font;
/* 431 */     this.pcs.firePropertyChange("labelFont", old, font);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getLabelPaint() {
/* 442 */     return this.labelPaint;
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
/*     */   public void setLabelPaint(Paint paint) {
/* 454 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 455 */     Paint old = this.labelPaint;
/* 456 */     this.labelPaint = paint;
/* 457 */     this.pcs.firePropertyChange("labelPaint", old, paint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getLabelBackgroundPaint() {
/* 468 */     return this.labelBackgroundPaint;
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
/*     */   public void setLabelBackgroundPaint(Paint paint) {
/* 480 */     Paint old = this.labelBackgroundPaint;
/* 481 */     this.labelBackgroundPaint = paint;
/* 482 */     this.pcs.firePropertyChange("labelBackgroundPaint", old, paint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLabelOutlineVisible() {
/* 493 */     return this.labelOutlineVisible;
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
/*     */   public void setLabelOutlineVisible(boolean visible) {
/* 506 */     boolean old = this.labelOutlineVisible;
/* 507 */     this.labelOutlineVisible = visible;
/* 508 */     this.pcs.firePropertyChange("labelOutlineVisible", old, visible);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getLabelOutlinePaint() {
/* 519 */     return this.labelOutlinePaint;
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
/*     */   public void setLabelOutlinePaint(Paint paint) {
/* 531 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 532 */     Paint old = this.labelOutlinePaint;
/* 533 */     this.labelOutlinePaint = paint;
/* 534 */     this.pcs.firePropertyChange("labelOutlinePaint", old, paint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getLabelOutlineStroke() {
/* 545 */     return this.labelOutlineStroke;
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
/*     */   public void setLabelOutlineStroke(Stroke stroke) {
/* 557 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 558 */     Stroke old = this.labelOutlineStroke;
/* 559 */     this.labelOutlineStroke = stroke;
/* 560 */     this.pcs.firePropertyChange("labelOutlineStroke", old, stroke);
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
/* 572 */     if (obj == this) {
/* 573 */       return true;
/*     */     }
/* 575 */     if (!(obj instanceof Crosshair)) {
/* 576 */       return false;
/*     */     }
/* 578 */     Crosshair that = (Crosshair)obj;
/* 579 */     if (this.visible != that.visible) {
/* 580 */       return false;
/*     */     }
/* 582 */     if (this.value != that.value) {
/* 583 */       return false;
/*     */     }
/* 585 */     if (!PaintUtilities.equal(this.paint, that.paint)) {
/* 586 */       return false;
/*     */     }
/* 588 */     if (!this.stroke.equals(that.stroke)) {
/* 589 */       return false;
/*     */     }
/* 591 */     if (this.labelVisible != that.labelVisible) {
/* 592 */       return false;
/*     */     }
/* 594 */     if (!this.labelGenerator.equals(that.labelGenerator)) {
/* 595 */       return false;
/*     */     }
/* 597 */     if (!this.labelAnchor.equals(that.labelAnchor)) {
/* 598 */       return false;
/*     */     }
/* 600 */     if (this.labelXOffset != that.labelXOffset) {
/* 601 */       return false;
/*     */     }
/* 603 */     if (this.labelYOffset != that.labelYOffset) {
/* 604 */       return false;
/*     */     }
/* 606 */     if (!this.labelFont.equals(that.labelFont)) {
/* 607 */       return false;
/*     */     }
/* 609 */     if (!PaintUtilities.equal(this.labelPaint, that.labelPaint)) {
/* 610 */       return false;
/*     */     }
/* 612 */     if (!PaintUtilities.equal(this.labelBackgroundPaint, that.labelBackgroundPaint))
/*     */     {
/* 614 */       return false;
/*     */     }
/* 616 */     if (this.labelOutlineVisible != that.labelOutlineVisible) {
/* 617 */       return false;
/*     */     }
/* 619 */     if (!PaintUtilities.equal(this.labelOutlinePaint, that.labelOutlinePaint))
/*     */     {
/* 621 */       return false;
/*     */     }
/* 623 */     if (!this.labelOutlineStroke.equals(that.labelOutlineStroke)) {
/* 624 */       return false;
/*     */     }
/* 626 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 636 */     int hash = 7;
/* 637 */     hash = HashUtilities.hashCode(hash, this.visible);
/* 638 */     hash = HashUtilities.hashCode(hash, this.value);
/* 639 */     hash = HashUtilities.hashCode(hash, this.paint);
/* 640 */     hash = HashUtilities.hashCode(hash, this.stroke);
/* 641 */     hash = HashUtilities.hashCode(hash, this.labelVisible);
/* 642 */     hash = HashUtilities.hashCode(hash, this.labelAnchor);
/* 643 */     hash = HashUtilities.hashCode(hash, this.labelGenerator);
/* 644 */     hash = HashUtilities.hashCode(hash, this.labelXOffset);
/* 645 */     hash = HashUtilities.hashCode(hash, this.labelYOffset);
/* 646 */     hash = HashUtilities.hashCode(hash, this.labelFont);
/* 647 */     hash = HashUtilities.hashCode(hash, this.labelPaint);
/* 648 */     hash = HashUtilities.hashCode(hash, this.labelBackgroundPaint);
/* 649 */     hash = HashUtilities.hashCode(hash, this.labelOutlineVisible);
/* 650 */     hash = HashUtilities.hashCode(hash, this.labelOutlineStroke);
/* 651 */     hash = HashUtilities.hashCode(hash, this.labelOutlinePaint);
/* 652 */     return hash;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 666 */     return super.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPropertyChangeListener(PropertyChangeListener l) {
/* 677 */     this.pcs.addPropertyChangeListener(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePropertyChangeListener(PropertyChangeListener l) {
/* 688 */     this.pcs.removePropertyChangeListener(l);
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
/* 699 */     stream.defaultWriteObject();
/* 700 */     SerialUtilities.writePaint(this.paint, stream);
/* 701 */     SerialUtilities.writeStroke(this.stroke, stream);
/* 702 */     SerialUtilities.writePaint(this.labelPaint, stream);
/* 703 */     SerialUtilities.writePaint(this.labelBackgroundPaint, stream);
/* 704 */     SerialUtilities.writeStroke(this.labelOutlineStroke, stream);
/* 705 */     SerialUtilities.writePaint(this.labelOutlinePaint, stream);
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
/* 718 */     stream.defaultReadObject();
/* 719 */     this.paint = SerialUtilities.readPaint(stream);
/* 720 */     this.stroke = SerialUtilities.readStroke(stream);
/* 721 */     this.labelPaint = SerialUtilities.readPaint(stream);
/* 722 */     this.labelBackgroundPaint = SerialUtilities.readPaint(stream);
/* 723 */     this.labelOutlineStroke = SerialUtilities.readStroke(stream);
/* 724 */     this.labelOutlinePaint = SerialUtilities.readPaint(stream);
/* 725 */     this.pcs = new PropertyChangeSupport(this);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/Crosshair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */