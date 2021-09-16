/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.geom.Line2D;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.renderer.RendererState;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XYItemRendererState
/*     */   extends RendererState
/*     */ {
/*     */   private int firstItemIndex;
/*     */   private int lastItemIndex;
/*     */   public Line2D workingLine;
/*     */   private boolean processVisibleItemsOnly;
/*     */   
/*     */   public XYItemRendererState(PlotRenderingInfo info) {
/*  97 */     super(info);
/*  98 */     this.workingLine = new Line2D.Double();
/*  99 */     this.processVisibleItemsOnly = true;
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
/*     */   public boolean getProcessVisibleItemsOnly() {
/* 114 */     return this.processVisibleItemsOnly;
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
/*     */   public void setProcessVisibleItemsOnly(boolean flag) {
/* 126 */     this.processVisibleItemsOnly = flag;
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
/*     */   public int getFirstItemIndex() {
/* 138 */     return this.firstItemIndex;
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
/*     */   public int getLastItemIndex() {
/* 150 */     return this.lastItemIndex;
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
/*     */ 
/*     */   
/*     */   public void startSeriesPass(XYDataset dataset, int series, int firstItem, int lastItem, int pass, int passCount) {
/* 172 */     this.firstItemIndex = firstItem;
/* 173 */     this.lastItemIndex = lastItem;
/*     */   }
/*     */   
/*     */   public void endSeriesPass(XYDataset dataset, int series, int firstItem, int lastItem, int pass, int passCount) {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYItemRendererState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */