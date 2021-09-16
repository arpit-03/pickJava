/*     */ package org.jfree.chart.annotations;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.ui.Drawable;
/*     */ import org.jfree.ui.RectangleEdge;
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
/*     */ public class XYDrawableAnnotation
/*     */   extends AbstractXYAnnotation
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6540812859722691020L;
/*     */   private double drawScaleFactor;
/*     */   private double x;
/*     */   private double y;
/*     */   private double displayWidth;
/*     */   private double displayHeight;
/*     */   private Drawable drawable;
/*     */   
/*     */   public XYDrawableAnnotation(double x, double y, double width, double height, Drawable drawable) {
/* 101 */     this(x, y, width, height, 1.0D, drawable);
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
/*     */   public XYDrawableAnnotation(double x, double y, double displayWidth, double displayHeight, double drawScaleFactor, Drawable drawable) {
/* 123 */     ParamChecks.nullNotPermitted(drawable, "drawable");
/* 124 */     this.x = x;
/* 125 */     this.y = y;
/* 126 */     this.displayWidth = displayWidth;
/* 127 */     this.displayHeight = displayHeight;
/* 128 */     this.drawScaleFactor = drawScaleFactor;
/* 129 */     this.drawable = drawable;
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
/*     */   public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex, PlotRenderingInfo info) {
/* 151 */     PlotOrientation orientation = plot.getOrientation();
/* 152 */     RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot
/* 153 */         .getDomainAxisLocation(), orientation);
/* 154 */     RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot
/* 155 */         .getRangeAxisLocation(), orientation);
/* 156 */     float j2DX = (float)domainAxis.valueToJava2D(this.x, dataArea, domainEdge);
/*     */     
/* 158 */     float j2DY = (float)rangeAxis.valueToJava2D(this.y, dataArea, rangeEdge);
/*     */     
/* 160 */     Rectangle2D displayArea = new Rectangle2D.Double(j2DX - this.displayWidth / 2.0D, j2DY - this.displayHeight / 2.0D, this.displayWidth, this.displayHeight);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     AffineTransform savedTransform = g2.getTransform();
/* 169 */     Rectangle2D drawArea = new Rectangle2D.Double(0.0D, 0.0D, this.displayWidth * this.drawScaleFactor, this.displayHeight * this.drawScaleFactor);
/*     */ 
/*     */ 
/*     */     
/* 173 */     g2.scale(1.0D / this.drawScaleFactor, 1.0D / this.drawScaleFactor);
/* 174 */     g2.translate((j2DX - this.displayWidth / 2.0D) * this.drawScaleFactor, (j2DY - this.displayHeight / 2.0D) * this.drawScaleFactor);
/*     */     
/* 176 */     this.drawable.draw(g2, drawArea);
/* 177 */     g2.setTransform(savedTransform);
/* 178 */     String toolTip = getToolTipText();
/* 179 */     String url = getURL();
/* 180 */     if (toolTip != null || url != null) {
/* 181 */       addEntity(info, displayArea, rendererIndex, toolTip, url);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 196 */     if (obj == this) {
/* 197 */       return true;
/*     */     }
/*     */     
/* 200 */     if (!super.equals(obj)) {
/* 201 */       return false;
/*     */     }
/* 203 */     if (!(obj instanceof XYDrawableAnnotation)) {
/* 204 */       return false;
/*     */     }
/* 206 */     XYDrawableAnnotation that = (XYDrawableAnnotation)obj;
/* 207 */     if (this.x != that.x) {
/* 208 */       return false;
/*     */     }
/* 210 */     if (this.y != that.y) {
/* 211 */       return false;
/*     */     }
/* 213 */     if (this.displayWidth != that.displayWidth) {
/* 214 */       return false;
/*     */     }
/* 216 */     if (this.displayHeight != that.displayHeight) {
/* 217 */       return false;
/*     */     }
/* 219 */     if (this.drawScaleFactor != that.drawScaleFactor) {
/* 220 */       return false;
/*     */     }
/* 222 */     if (!ObjectUtilities.equal(this.drawable, that.drawable)) {
/* 223 */       return false;
/*     */     }
/*     */     
/* 226 */     return true;
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
/*     */   public int hashCode() {
/* 239 */     long temp = Double.doubleToLongBits(this.x);
/* 240 */     int result = (int)(temp ^ temp >>> 32L);
/* 241 */     temp = Double.doubleToLongBits(this.y);
/* 242 */     result = 29 * result + (int)(temp ^ temp >>> 32L);
/* 243 */     temp = Double.doubleToLongBits(this.displayWidth);
/* 244 */     result = 29 * result + (int)(temp ^ temp >>> 32L);
/* 245 */     temp = Double.doubleToLongBits(this.displayHeight);
/* 246 */     result = 29 * result + (int)(temp ^ temp >>> 32L);
/* 247 */     return result;
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
/* 259 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/XYDrawableAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */