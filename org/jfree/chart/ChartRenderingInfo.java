/*     */ package org.jfree.chart;
/*     */ 
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.entity.StandardEntityCollection;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChartRenderingInfo
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2751952018173406822L;
/*     */   private transient Rectangle2D chartArea;
/*     */   private PlotRenderingInfo plotInfo;
/*     */   private EntityCollection entities;
/*     */   
/*     */   public ChartRenderingInfo() {
/* 102 */     this((EntityCollection)new StandardEntityCollection());
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
/*     */   public ChartRenderingInfo(EntityCollection entities) {
/* 114 */     this.chartArea = new Rectangle2D.Double();
/* 115 */     this.plotInfo = new PlotRenderingInfo(this);
/* 116 */     this.entities = entities;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle2D getChartArea() {
/* 127 */     return this.chartArea;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChartArea(Rectangle2D area) {
/* 138 */     this.chartArea.setRect(area);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityCollection getEntityCollection() {
/* 149 */     return this.entities;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntityCollection(EntityCollection entities) {
/* 160 */     this.entities = entities;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 167 */     this.chartArea.setRect(0.0D, 0.0D, 0.0D, 0.0D);
/* 168 */     this.plotInfo = new PlotRenderingInfo(this);
/* 169 */     if (this.entities != null) {
/* 170 */       this.entities.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlotRenderingInfo getPlotInfo() {
/* 180 */     return this.plotInfo;
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
/* 192 */     if (obj == this) {
/* 193 */       return true;
/*     */     }
/* 195 */     if (!(obj instanceof ChartRenderingInfo)) {
/* 196 */       return false;
/*     */     }
/* 198 */     ChartRenderingInfo that = (ChartRenderingInfo)obj;
/* 199 */     if (!ObjectUtilities.equal(this.chartArea, that.chartArea)) {
/* 200 */       return false;
/*     */     }
/* 202 */     if (!ObjectUtilities.equal(this.plotInfo, that.plotInfo)) {
/* 203 */       return false;
/*     */     }
/* 205 */     if (!ObjectUtilities.equal(this.entities, that.entities)) {
/* 206 */       return false;
/*     */     }
/* 208 */     return true;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 220 */     ChartRenderingInfo clone = (ChartRenderingInfo)super.clone();
/* 221 */     if (this.chartArea != null) {
/* 222 */       clone.chartArea = (Rectangle2D)this.chartArea.clone();
/*     */     }
/* 224 */     if (this.entities instanceof PublicCloneable) {
/* 225 */       PublicCloneable pc = (PublicCloneable)this.entities;
/* 226 */       clone.entities = (EntityCollection)pc.clone();
/*     */     } 
/* 228 */     return clone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 239 */     stream.defaultWriteObject();
/* 240 */     SerialUtilities.writeShape(this.chartArea, stream);
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 253 */     stream.defaultReadObject();
/* 254 */     this.chartArea = (Rectangle2D)SerialUtilities.readShape(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/ChartRenderingInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */