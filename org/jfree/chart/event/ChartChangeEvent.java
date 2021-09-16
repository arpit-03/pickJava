/*     */ package org.jfree.chart.event;
/*     */ 
/*     */ import java.util.EventObject;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChartChangeEvent
/*     */   extends EventObject
/*     */ {
/*     */   private ChartChangeEventType type;
/*     */   private JFreeChart chart;
/*     */   
/*     */   public ChartChangeEvent(Object source) {
/*  69 */     this(source, null, ChartChangeEventType.GENERAL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChartChangeEvent(Object source, JFreeChart chart) {
/*  80 */     this(source, chart, ChartChangeEventType.GENERAL);
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
/*     */   public ChartChangeEvent(Object source, JFreeChart chart, ChartChangeEventType type) {
/*  93 */     super(source);
/*  94 */     this.chart = chart;
/*  95 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JFreeChart getChart() {
/* 104 */     return this.chart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChart(JFreeChart chart) {
/* 113 */     this.chart = chart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChartChangeEventType getType() {
/* 122 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(ChartChangeEventType type) {
/* 131 */     this.type = type;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/event/ChartChangeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */