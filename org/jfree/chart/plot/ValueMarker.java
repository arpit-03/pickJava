/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import org.jfree.chart.event.MarkerChangeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValueMarker
/*     */   extends Marker
/*     */ {
/*     */   private double value;
/*     */   
/*     */   public ValueMarker(double value) {
/*  69 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueMarker(double value, Paint paint, Stroke stroke) {
/*  80 */     this(value, paint, stroke, paint, stroke, 1.0F);
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
/*     */   public ValueMarker(double value, Paint paint, Stroke stroke, Paint outlinePaint, Stroke outlineStroke, float alpha) {
/*  95 */     super(paint, stroke, outlinePaint, outlineStroke, alpha);
/*  96 */     this.value = value;
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
/* 107 */     return this.value;
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
/*     */   public void setValue(double value) {
/* 121 */     this.value = value;
/* 122 */     notifyListeners(new MarkerChangeEvent(this));
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
/*     */   public boolean equals(Object obj) {
/* 142 */     if (obj == this) {
/* 143 */       return true;
/*     */     }
/* 145 */     if (!super.equals(obj)) {
/* 146 */       return false;
/*     */     }
/* 148 */     if (!(obj instanceof ValueMarker)) {
/* 149 */       return false;
/*     */     }
/* 151 */     ValueMarker that = (ValueMarker)obj;
/* 152 */     if (this.value != that.value) {
/* 153 */       return false;
/*     */     }
/* 155 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/ValueMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */