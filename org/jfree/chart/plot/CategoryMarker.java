/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.event.MarkerChangeEvent;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.ui.LengthAdjustmentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CategoryMarker
/*     */   extends Marker
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private Comparable key;
/*     */   private boolean drawAsLine = false;
/*     */   
/*     */   public CategoryMarker(Comparable key) {
/*  81 */     this(key, Color.gray, new BasicStroke(1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CategoryMarker(Comparable key, Paint paint, Stroke stroke) {
/*  92 */     this(key, paint, stroke, paint, stroke, 1.0F);
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
/*     */   public CategoryMarker(Comparable key, Paint paint, Stroke stroke, Paint outlinePaint, Stroke outlineStroke, float alpha) {
/* 108 */     super(paint, stroke, outlinePaint, outlineStroke, alpha);
/* 109 */     this.key = key;
/* 110 */     setLabelOffsetType(LengthAdjustmentType.EXPAND);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparable getKey() {
/* 119 */     return this.key;
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
/*     */   public void setKey(Comparable key) {
/* 131 */     ParamChecks.nullNotPermitted(key, "key");
/* 132 */     this.key = key;
/* 133 */     notifyListeners(new MarkerChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getDrawAsLine() {
/* 143 */     return this.drawAsLine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDrawAsLine(boolean drawAsLine) {
/* 154 */     this.drawAsLine = drawAsLine;
/* 155 */     notifyListeners(new MarkerChangeEvent(this));
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
/* 167 */     if (obj == null) {
/* 168 */       return false;
/*     */     }
/* 170 */     if (!(obj instanceof CategoryMarker)) {
/* 171 */       return false;
/*     */     }
/* 173 */     if (!super.equals(obj)) {
/* 174 */       return false;
/*     */     }
/* 176 */     CategoryMarker that = (CategoryMarker)obj;
/* 177 */     if (!this.key.equals(that.key)) {
/* 178 */       return false;
/*     */     }
/* 180 */     if (this.drawAsLine != that.drawAsLine) {
/* 181 */       return false;
/*     */     }
/* 183 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/CategoryMarker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */