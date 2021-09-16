/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import org.jfree.chart.plot.CategoryCrosshairState;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.renderer.RendererState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CategoryItemRendererState
/*     */   extends RendererState
/*     */ {
/*     */   private double barWidth;
/*     */   private double seriesRunningTotal;
/*     */   private int[] visibleSeries;
/*     */   private CategoryCrosshairState crosshairState;
/*     */   
/*     */   public CategoryItemRendererState(PlotRenderingInfo info) {
/*  82 */     super(info);
/*  83 */     this.barWidth = 0.0D;
/*  84 */     this.seriesRunningTotal = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBarWidth() {
/*  95 */     return this.barWidth;
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
/*     */   public void setBarWidth(double width) {
/* 107 */     this.barWidth = width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSeriesRunningTotal() {
/* 118 */     return this.seriesRunningTotal;
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
/*     */   void setSeriesRunningTotal(double total) {
/* 130 */     this.seriesRunningTotal = total;
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
/*     */   public CategoryCrosshairState getCrosshairState() {
/* 143 */     return this.crosshairState;
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
/*     */   public void setCrosshairState(CategoryCrosshairState state) {
/* 156 */     this.crosshairState = state;
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
/*     */   public int getVisibleSeriesIndex(int rowIndex) {
/* 172 */     if (this.visibleSeries == null) {
/* 173 */       return rowIndex;
/*     */     }
/* 175 */     int index = -1;
/* 176 */     for (int vRow = 0; vRow < this.visibleSeries.length; vRow++) {
/* 177 */       if (this.visibleSeries[vRow] == rowIndex) {
/* 178 */         index = vRow;
/*     */         break;
/*     */       } 
/*     */     } 
/* 182 */     return index;
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
/*     */   public int getVisibleSeriesCount() {
/* 194 */     if (this.visibleSeries == null) {
/* 195 */       return -1;
/*     */     }
/* 197 */     return this.visibleSeries.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getVisibleSeriesArray() {
/* 208 */     if (this.visibleSeries == null) {
/* 209 */       return null;
/*     */     }
/* 211 */     int[] result = new int[this.visibleSeries.length];
/* 212 */     System.arraycopy(this.visibleSeries, 0, result, 0, this.visibleSeries.length);
/*     */     
/* 214 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisibleSeriesArray(int[] visibleSeries) {
/* 225 */     this.visibleSeries = visibleSeries;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/CategoryItemRendererState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */