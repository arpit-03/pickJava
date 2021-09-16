/*     */ package org.jfree.chart;
/*     */ 
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ import java.awt.event.MouseWheelListener;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.plot.PiePlot;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.Zoomable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MouseWheelHandler
/*     */   implements MouseWheelListener, Serializable
/*     */ {
/*     */   private ChartPanel chartPanel;
/*     */   double zoomFactor;
/*     */   
/*     */   public MouseWheelHandler(ChartPanel chartPanel) {
/*  77 */     this.chartPanel = chartPanel;
/*  78 */     this.zoomFactor = 0.1D;
/*  79 */     this.chartPanel.addMouseWheelListener(this);
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
/*     */   public double getZoomFactor() {
/*  91 */     return this.zoomFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZoomFactor(double zoomFactor) {
/* 102 */     this.zoomFactor = zoomFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseWheelMoved(MouseWheelEvent e) {
/* 112 */     JFreeChart chart = this.chartPanel.getChart();
/* 113 */     if (chart == null) {
/*     */       return;
/*     */     }
/* 116 */     Plot plot = chart.getPlot();
/* 117 */     if (plot instanceof Zoomable) {
/* 118 */       Zoomable zoomable = (Zoomable)plot;
/* 119 */       handleZoomable(zoomable, e);
/*     */     }
/* 121 */     else if (plot instanceof PiePlot) {
/* 122 */       PiePlot pp = (PiePlot)plot;
/* 123 */       pp.handleMouseWheelRotation(e.getWheelRotation());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleZoomable(Zoomable zoomable, MouseWheelEvent e) {
/* 135 */     ChartRenderingInfo info = this.chartPanel.getChartRenderingInfo();
/* 136 */     PlotRenderingInfo pinfo = info.getPlotInfo();
/* 137 */     Point2D p = this.chartPanel.translateScreenToJava2D(e.getPoint());
/* 138 */     if (!pinfo.getDataArea().contains(p)) {
/*     */       return;
/*     */     }
/*     */     
/* 142 */     Plot plot = (Plot)zoomable;
/*     */     
/* 144 */     boolean notifyState = plot.isNotify();
/* 145 */     plot.setNotify(false);
/* 146 */     int clicks = e.getWheelRotation();
/* 147 */     double zf = 1.0D + this.zoomFactor;
/* 148 */     if (clicks < 0) {
/* 149 */       zf = 1.0D / zf;
/*     */     }
/* 151 */     if (this.chartPanel.isDomainZoomable()) {
/* 152 */       zoomable.zoomDomainAxes(zf, pinfo, p, true);
/*     */     }
/* 154 */     if (this.chartPanel.isRangeZoomable()) {
/* 155 */       zoomable.zoomRangeAxes(zf, pinfo, p, true);
/*     */     }
/* 157 */     plot.setNotify(notifyState);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/MouseWheelHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */