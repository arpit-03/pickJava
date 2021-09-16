/*    */ package org.jfree.chart.renderer;
/*    */ 
/*    */ import org.jfree.chart.ChartRenderingInfo;
/*    */ import org.jfree.chart.entity.EntityCollection;
/*    */ import org.jfree.chart.plot.PlotRenderingInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RendererState
/*    */ {
/*    */   private PlotRenderingInfo info;
/*    */   
/*    */   public RendererState(PlotRenderingInfo info) {
/* 63 */     this.info = info;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlotRenderingInfo getInfo() {
/* 72 */     return this.info;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityCollection getEntityCollection() {
/* 83 */     EntityCollection result = null;
/* 84 */     if (this.info != null) {
/* 85 */       ChartRenderingInfo owner = this.info.getOwner();
/* 86 */       if (owner != null) {
/* 87 */         result = owner.getEntityCollection();
/*    */       }
/*    */     } 
/* 90 */     return result;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/RendererState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */