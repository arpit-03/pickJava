/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AxisCollection
/*     */ {
/*  72 */   private List axesAtTop = new ArrayList();
/*  73 */   private List axesAtBottom = new ArrayList();
/*  74 */   private List axesAtLeft = new ArrayList();
/*  75 */   private List axesAtRight = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getAxesAtTop() {
/*  85 */     return this.axesAtTop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getAxesAtBottom() {
/*  95 */     return this.axesAtBottom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getAxesAtLeft() {
/* 105 */     return this.axesAtLeft;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getAxesAtRight() {
/* 115 */     return this.axesAtRight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Axis axis, RectangleEdge edge) {
/* 126 */     ParamChecks.nullNotPermitted(axis, "axis");
/* 127 */     ParamChecks.nullNotPermitted(edge, "edge");
/* 128 */     if (edge == RectangleEdge.TOP) {
/* 129 */       this.axesAtTop.add(axis);
/*     */     }
/* 131 */     else if (edge == RectangleEdge.BOTTOM) {
/* 132 */       this.axesAtBottom.add(axis);
/*     */     }
/* 134 */     else if (edge == RectangleEdge.LEFT) {
/* 135 */       this.axesAtLeft.add(axis);
/*     */     }
/* 137 */     else if (edge == RectangleEdge.RIGHT) {
/* 138 */       this.axesAtRight.add(axis);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/AxisCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */