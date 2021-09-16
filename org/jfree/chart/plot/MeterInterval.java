/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.Range;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MeterInterval
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1530982090622488257L;
/*     */   private String label;
/*     */   private Range range;
/*     */   private transient Paint outlinePaint;
/*     */   private transient Stroke outlineStroke;
/*     */   private transient Paint backgroundPaint;
/*     */   
/*     */   public MeterInterval(String label, Range range) {
/*  91 */     this(label, range, Color.yellow, new BasicStroke(2.0F), null);
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
/*     */   public MeterInterval(String label, Range range, Paint outlinePaint, Stroke outlineStroke, Paint backgroundPaint) {
/* 106 */     ParamChecks.nullNotPermitted(label, "label");
/* 107 */     ParamChecks.nullNotPermitted(range, "range");
/* 108 */     this.label = label;
/* 109 */     this.range = range;
/* 110 */     this.outlinePaint = outlinePaint;
/* 111 */     this.outlineStroke = outlineStroke;
/* 112 */     this.backgroundPaint = backgroundPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLabel() {
/* 121 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Range getRange() {
/* 130 */     return this.range;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getBackgroundPaint() {
/* 140 */     return this.backgroundPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getOutlinePaint() {
/* 149 */     return this.outlinePaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getOutlineStroke() {
/* 158 */     return this.outlineStroke;
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
/* 170 */     if (obj == this) {
/* 171 */       return true;
/*     */     }
/* 173 */     if (!(obj instanceof MeterInterval)) {
/* 174 */       return false;
/*     */     }
/* 176 */     MeterInterval that = (MeterInterval)obj;
/* 177 */     if (!this.label.equals(that.label)) {
/* 178 */       return false;
/*     */     }
/* 180 */     if (!this.range.equals(that.range)) {
/* 181 */       return false;
/*     */     }
/* 183 */     if (!PaintUtilities.equal(this.outlinePaint, that.outlinePaint)) {
/* 184 */       return false;
/*     */     }
/* 186 */     if (!ObjectUtilities.equal(this.outlineStroke, that.outlineStroke)) {
/* 187 */       return false;
/*     */     }
/* 189 */     if (!PaintUtilities.equal(this.backgroundPaint, that.backgroundPaint)) {
/* 190 */       return false;
/*     */     }
/* 192 */     return true;
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
/* 203 */     stream.defaultWriteObject();
/* 204 */     SerialUtilities.writePaint(this.outlinePaint, stream);
/* 205 */     SerialUtilities.writeStroke(this.outlineStroke, stream);
/* 206 */     SerialUtilities.writePaint(this.backgroundPaint, stream);
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
/* 219 */     stream.defaultReadObject();
/* 220 */     this.outlinePaint = SerialUtilities.readPaint(stream);
/* 221 */     this.outlineStroke = SerialUtilities.readStroke(stream);
/* 222 */     this.backgroundPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/MeterInterval.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */