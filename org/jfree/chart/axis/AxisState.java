/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AxisState
/*     */ {
/*     */   private double cursor;
/*     */   private List ticks;
/*     */   private double max;
/*     */   
/*     */   public AxisState() {
/*  70 */     this(0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisState(double cursor) {
/*  79 */     this.cursor = cursor;
/*  80 */     this.ticks = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getCursor() {
/*  89 */     return this.cursor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursor(double cursor) {
/*  98 */     this.cursor = cursor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveCursor(double units, RectangleEdge edge) {
/* 108 */     if (edge == RectangleEdge.TOP) {
/* 109 */       cursorUp(units);
/*     */     }
/* 111 */     else if (edge == RectangleEdge.BOTTOM) {
/* 112 */       cursorDown(units);
/*     */     }
/* 114 */     else if (edge == RectangleEdge.LEFT) {
/* 115 */       cursorLeft(units);
/*     */     }
/* 117 */     else if (edge == RectangleEdge.RIGHT) {
/* 118 */       cursorRight(units);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cursorUp(double units) {
/* 128 */     this.cursor -= units;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cursorDown(double units) {
/* 137 */     this.cursor += units;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cursorLeft(double units) {
/* 146 */     this.cursor -= units;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cursorRight(double units) {
/* 155 */     this.cursor += units;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getTicks() {
/* 164 */     return this.ticks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTicks(List ticks) {
/* 173 */     this.ticks = ticks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMax() {
/* 182 */     return this.max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMax(double max) {
/* 191 */     this.max = max;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/AxisState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */