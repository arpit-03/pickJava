/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.Effect3D;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.renderer.category.CategoryItemRenderer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NumberAxis3D
/*     */   extends NumberAxis
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1790205852569123512L;
/*     */   
/*     */   public NumberAxis3D() {
/*  98 */     this((String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberAxis3D(String label) {
/* 107 */     super(label);
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
/*     */ 
/*     */   
/*     */   public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState) {
/* 131 */     if (!isVisible()) {
/* 132 */       AxisState state = new AxisState(cursor);
/*     */ 
/*     */       
/* 135 */       List ticks = refreshTicks(g2, state, dataArea, edge);
/* 136 */       state.setTicks(ticks);
/* 137 */       return state;
/*     */     } 
/*     */ 
/*     */     
/* 141 */     double xOffset = 0.0D;
/* 142 */     double yOffset = 0.0D;
/* 143 */     Plot plot = getPlot();
/* 144 */     if (plot instanceof CategoryPlot) {
/* 145 */       CategoryPlot cp = (CategoryPlot)plot;
/* 146 */       CategoryItemRenderer r = cp.getRenderer();
/* 147 */       if (r instanceof Effect3D) {
/* 148 */         Effect3D e3D = (Effect3D)r;
/* 149 */         xOffset = e3D.getXOffset();
/* 150 */         yOffset = e3D.getYOffset();
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     double adjustedX = dataArea.getMinX();
/* 155 */     double adjustedY = dataArea.getMinY();
/* 156 */     double adjustedW = dataArea.getWidth() - xOffset;
/* 157 */     double adjustedH = dataArea.getHeight() - yOffset;
/*     */     
/* 159 */     if (edge == RectangleEdge.LEFT || edge == RectangleEdge.BOTTOM) {
/* 160 */       adjustedY += yOffset;
/*     */     }
/* 162 */     else if (edge == RectangleEdge.RIGHT || edge == RectangleEdge.TOP) {
/* 163 */       adjustedX += xOffset;
/*     */     } 
/* 165 */     Rectangle2D adjustedDataArea = new Rectangle2D.Double(adjustedX, adjustedY, adjustedW, adjustedH);
/*     */ 
/*     */ 
/*     */     
/* 169 */     AxisState info = drawTickMarksAndLabels(g2, cursor, plotArea, adjustedDataArea, edge);
/*     */ 
/*     */     
/* 172 */     if (getAttributedLabel() != null) {
/* 173 */       info = drawAttributedLabel(getAttributedLabel(), g2, plotArea, dataArea, edge, info);
/*     */     } else {
/*     */       
/* 176 */       info = drawLabel(getLabel(), g2, plotArea, dataArea, edge, info);
/*     */     } 
/* 178 */     return info;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/NumberAxis3D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */