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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChartProgressEvent
/*     */   extends EventObject
/*     */ {
/*     */   public static final int DRAWING_STARTED = 1;
/*     */   public static final int DRAWING_FINISHED = 2;
/*     */   private int type;
/*     */   private int percent;
/*     */   private JFreeChart chart;
/*     */   
/*     */   public ChartProgressEvent(Object source, JFreeChart chart, int type, int percent) {
/*  77 */     super(source);
/*  78 */     this.chart = chart;
/*  79 */     this.type = type;
/*  80 */     this.percent = percent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JFreeChart getChart() {
/*  89 */     return this.chart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChart(JFreeChart chart) {
/*  98 */     this.chart = chart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 107 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(int type) {
/* 116 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPercent() {
/* 125 */     return this.percent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPercent(int percent) {
/* 134 */     this.percent = percent;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/event/ChartProgressEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */