/*     */ package org.jfree.chart.annotations;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.ChartEntity;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.entity.XYAnnotationEntity;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractXYAnnotation
/*     */   extends AbstractAnnotation
/*     */   implements XYAnnotation
/*     */ {
/*  75 */   private String toolTipText = null;
/*  76 */   private String url = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getToolTipText() {
/*  89 */     return this.toolTipText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setToolTipText(String text) {
/* 100 */     this.toolTipText = text;
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
/*     */   public String getURL() {
/* 112 */     return this.url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURL(String url) {
/* 123 */     this.url = url;
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
/*     */   public abstract void draw(Graphics2D paramGraphics2D, XYPlot paramXYPlot, Rectangle2D paramRectangle2D, ValueAxis paramValueAxis1, ValueAxis paramValueAxis2, int paramInt, PlotRenderingInfo paramPlotRenderingInfo);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addEntity(PlotRenderingInfo info, Shape hotspot, int rendererIndex, String toolTipText, String urlText) {
/* 157 */     if (info == null) {
/*     */       return;
/*     */     }
/* 160 */     EntityCollection entities = info.getOwner().getEntityCollection();
/* 161 */     if (entities == null) {
/*     */       return;
/*     */     }
/* 164 */     XYAnnotationEntity entity = new XYAnnotationEntity(hotspot, rendererIndex, toolTipText, urlText);
/*     */     
/* 166 */     entities.add((ChartEntity)entity);
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
/*     */   public boolean equals(Object obj) {
/* 178 */     if (obj == this) {
/* 179 */       return true;
/*     */     }
/* 181 */     if (!(obj instanceof AbstractXYAnnotation)) {
/* 182 */       return false;
/*     */     }
/* 184 */     AbstractXYAnnotation that = (AbstractXYAnnotation)obj;
/* 185 */     if (!ObjectUtilities.equal(this.toolTipText, that.toolTipText)) {
/* 186 */       return false;
/*     */     }
/* 188 */     if (!ObjectUtilities.equal(this.url, that.url)) {
/* 189 */       return false;
/*     */     }
/* 191 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 201 */     int result = 193;
/* 202 */     if (this.toolTipText != null) {
/* 203 */       result = 37 * result + this.toolTipText.hashCode();
/*     */     }
/* 205 */     if (this.url != null) {
/* 206 */       result = 37 * result + this.url.hashCode();
/*     */     }
/* 208 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/AbstractXYAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */