/*     */ package org.jfree.chart.annotations;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.AxisLocation;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.ui.RectangleAnchor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XYImageAnnotation
/*     */   extends AbstractXYAnnotation
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4364694501921559958L;
/*     */   private double x;
/*     */   private double y;
/*     */   private transient Image image;
/*     */   private RectangleAnchor anchor;
/*     */   
/*     */   public XYImageAnnotation(double x, double y, Image image) {
/* 110 */     this(x, y, image, RectangleAnchor.CENTER);
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
/*     */   public XYImageAnnotation(double x, double y, Image image, RectangleAnchor anchor) {
/* 127 */     ParamChecks.nullNotPermitted(image, "image");
/* 128 */     ParamChecks.nullNotPermitted(anchor, "anchor");
/* 129 */     this.x = x;
/* 130 */     this.y = y;
/* 131 */     this.image = image;
/* 132 */     this.anchor = anchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/* 143 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/* 154 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getImage() {
/* 165 */     return this.image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleAnchor getImageAnchor() {
/* 176 */     return this.anchor;
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
/*     */   public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex, PlotRenderingInfo info) {
/* 199 */     PlotOrientation orientation = plot.getOrientation();
/* 200 */     AxisLocation domainAxisLocation = plot.getDomainAxisLocation();
/* 201 */     AxisLocation rangeAxisLocation = plot.getRangeAxisLocation();
/*     */     
/* 203 */     RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(domainAxisLocation, orientation);
/*     */     
/* 205 */     RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(rangeAxisLocation, orientation);
/*     */     
/* 207 */     float j2DX = (float)domainAxis.valueToJava2D(this.x, dataArea, domainEdge);
/*     */     
/* 209 */     float j2DY = (float)rangeAxis.valueToJava2D(this.y, dataArea, rangeEdge);
/* 210 */     float xx = 0.0F;
/* 211 */     float yy = 0.0F;
/* 212 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 213 */       xx = j2DY;
/* 214 */       yy = j2DX;
/*     */     }
/* 216 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 217 */       xx = j2DX;
/* 218 */       yy = j2DY;
/*     */     } 
/* 220 */     int w = this.image.getWidth(null);
/* 221 */     int h = this.image.getHeight(null);
/*     */     
/* 223 */     Rectangle2D imageRect = new Rectangle2D.Double(0.0D, 0.0D, w, h);
/* 224 */     Point2D anchorPoint = RectangleAnchor.coordinates(imageRect, this.anchor);
/*     */     
/* 226 */     xx -= (float)anchorPoint.getX();
/* 227 */     yy -= (float)anchorPoint.getY();
/* 228 */     g2.drawImage(this.image, (int)xx, (int)yy, null);
/*     */     
/* 230 */     String toolTip = getToolTipText();
/* 231 */     String url = getURL();
/* 232 */     if (toolTip != null || url != null) {
/* 233 */       addEntity(info, new Rectangle2D.Float(xx, yy, w, h), rendererIndex, toolTip, url);
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
/*     */   public boolean equals(Object obj) {
/* 247 */     if (obj == this) {
/* 248 */       return true;
/*     */     }
/*     */     
/* 251 */     if (!super.equals(obj)) {
/* 252 */       return false;
/*     */     }
/* 254 */     if (!(obj instanceof XYImageAnnotation)) {
/* 255 */       return false;
/*     */     }
/* 257 */     XYImageAnnotation that = (XYImageAnnotation)obj;
/* 258 */     if (this.x != that.x) {
/* 259 */       return false;
/*     */     }
/* 261 */     if (this.y != that.y) {
/* 262 */       return false;
/*     */     }
/* 264 */     if (!ObjectUtilities.equal(this.image, that.image)) {
/* 265 */       return false;
/*     */     }
/* 267 */     if (!this.anchor.equals(that.anchor)) {
/* 268 */       return false;
/*     */     }
/*     */     
/* 271 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 281 */     return this.image.hashCode();
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
/* 293 */     return super.clone();
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
/* 304 */     stream.defaultWriteObject();
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 318 */     stream.defaultReadObject();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/XYImageAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */