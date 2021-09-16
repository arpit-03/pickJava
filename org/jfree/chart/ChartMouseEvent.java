/*     */ package org.jfree.chart;
/*     */ 
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.Serializable;
/*     */ import java.util.EventObject;
/*     */ import org.jfree.chart.entity.ChartEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChartMouseEvent
/*     */   extends EventObject
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -682393837314562149L;
/*     */   private JFreeChart chart;
/*     */   private MouseEvent trigger;
/*     */   private ChartEntity entity;
/*     */   
/*     */   public ChartMouseEvent(JFreeChart chart, MouseEvent trigger, ChartEntity entity) {
/*  85 */     super(chart);
/*  86 */     this.chart = chart;
/*  87 */     this.trigger = trigger;
/*  88 */     this.entity = entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JFreeChart getChart() {
/*  97 */     return this.chart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MouseEvent getTrigger() {
/* 106 */     return this.trigger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChartEntity getEntity() {
/* 115 */     return this.entity;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/ChartMouseEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */