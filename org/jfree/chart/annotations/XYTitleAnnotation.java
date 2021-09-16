/*     */ package org.jfree.chart.annotations;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.axis.AxisLocation;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.block.BlockParams;
/*     */ import org.jfree.chart.block.EntityBlockResult;
/*     */ import org.jfree.chart.block.RectangleConstraint;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.title.Title;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.chart.util.XYCoordinateType;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.ui.RectangleAnchor;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.Size2D;
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
/*     */ public class XYTitleAnnotation
/*     */   extends AbstractXYAnnotation
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4364694501921559958L;
/*     */   private XYCoordinateType coordinateType;
/*     */   private double x;
/*     */   private double y;
/*     */   private double maxWidth;
/*     */   private double maxHeight;
/*     */   private Title title;
/*     */   private RectangleAnchor anchor;
/*     */   
/*     */   public XYTitleAnnotation(double x, double y, Title title) {
/* 119 */     this(x, y, title, RectangleAnchor.CENTER);
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
/*     */   public XYTitleAnnotation(double x, double y, Title title, RectangleAnchor anchor) {
/* 134 */     ParamChecks.nullNotPermitted(title, "title");
/* 135 */     ParamChecks.nullNotPermitted(anchor, "anchor");
/* 136 */     this.coordinateType = XYCoordinateType.RELATIVE;
/* 137 */     this.x = x;
/* 138 */     this.y = y;
/* 139 */     this.maxWidth = 0.0D;
/* 140 */     this.maxHeight = 0.0D;
/* 141 */     this.title = title;
/* 142 */     this.anchor = anchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYCoordinateType getCoordinateType() {
/* 151 */     return this.coordinateType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/* 160 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/* 169 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Title getTitle() {
/* 178 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleAnchor getTitleAnchor() {
/* 187 */     return this.anchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxWidth() {
/* 196 */     return this.maxWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxWidth(double max) {
/* 206 */     this.maxWidth = max;
/* 207 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxHeight() {
/* 216 */     return this.maxHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxHeight(double max) {
/* 226 */     this.maxHeight = max;
/* 227 */     fireAnnotationChanged();
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
/*     */   public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex, PlotRenderingInfo info) {
/*     */     double anchorX, anchorY;
/* 249 */     PlotOrientation orientation = plot.getOrientation();
/* 250 */     AxisLocation domainAxisLocation = plot.getDomainAxisLocation();
/* 251 */     AxisLocation rangeAxisLocation = plot.getRangeAxisLocation();
/* 252 */     RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(domainAxisLocation, orientation);
/*     */     
/* 254 */     RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(rangeAxisLocation, orientation);
/*     */     
/* 256 */     Range xRange = domainAxis.getRange();
/* 257 */     Range yRange = rangeAxis.getRange();
/*     */     
/* 259 */     if (this.coordinateType == XYCoordinateType.RELATIVE) {
/* 260 */       anchorX = xRange.getLowerBound() + this.x * xRange.getLength();
/* 261 */       anchorY = yRange.getLowerBound() + this.y * yRange.getLength();
/*     */     } else {
/*     */       
/* 264 */       anchorX = domainAxis.valueToJava2D(this.x, dataArea, domainEdge);
/* 265 */       anchorY = rangeAxis.valueToJava2D(this.y, dataArea, rangeEdge);
/*     */     } 
/*     */     
/* 268 */     float j2DX = (float)domainAxis.valueToJava2D(anchorX, dataArea, domainEdge);
/*     */     
/* 270 */     float j2DY = (float)rangeAxis.valueToJava2D(anchorY, dataArea, rangeEdge);
/*     */     
/* 272 */     float xx = 0.0F;
/* 273 */     float yy = 0.0F;
/* 274 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 275 */       xx = j2DY;
/* 276 */       yy = j2DX;
/*     */     }
/* 278 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 279 */       xx = j2DX;
/* 280 */       yy = j2DY;
/*     */     } 
/*     */     
/* 283 */     double maxW = dataArea.getWidth();
/* 284 */     double maxH = dataArea.getHeight();
/* 285 */     if (this.coordinateType == XYCoordinateType.RELATIVE) {
/* 286 */       if (this.maxWidth > 0.0D) {
/* 287 */         maxW *= this.maxWidth;
/*     */       }
/* 289 */       if (this.maxHeight > 0.0D) {
/* 290 */         maxH *= this.maxHeight;
/*     */       }
/*     */     } 
/* 293 */     if (this.coordinateType == XYCoordinateType.DATA) {
/* 294 */       maxW = this.maxWidth;
/* 295 */       maxH = this.maxHeight;
/*     */     } 
/* 297 */     RectangleConstraint rc = new RectangleConstraint(new Range(0.0D, maxW), new Range(0.0D, maxH));
/*     */ 
/*     */     
/* 300 */     Size2D size = this.title.arrange(g2, rc);
/* 301 */     Rectangle2D titleRect = new Rectangle2D.Double(0.0D, 0.0D, size.width, size.height);
/*     */     
/* 303 */     Point2D anchorPoint = RectangleAnchor.coordinates(titleRect, this.anchor);
/*     */     
/* 305 */     xx -= (float)anchorPoint.getX();
/* 306 */     yy -= (float)anchorPoint.getY();
/* 307 */     titleRect.setRect(xx, yy, titleRect.getWidth(), titleRect.getHeight());
/* 308 */     BlockParams p = new BlockParams();
/* 309 */     if (info != null && 
/* 310 */       info.getOwner().getEntityCollection() != null) {
/* 311 */       p.setGenerateEntities(true);
/*     */     }
/*     */     
/* 314 */     Object result = this.title.draw(g2, titleRect, p);
/* 315 */     if (info != null) {
/* 316 */       if (result instanceof EntityBlockResult) {
/* 317 */         EntityBlockResult ebr = (EntityBlockResult)result;
/* 318 */         info.getOwner().getEntityCollection().addAll(ebr
/* 319 */             .getEntityCollection());
/*     */       } 
/* 321 */       String toolTip = getToolTipText();
/* 322 */       String url = getURL();
/* 323 */       if (toolTip != null || url != null) {
/* 324 */         addEntity(info, new Rectangle2D.Float(xx, yy, (float)size.width, (float)size.height), rendererIndex, toolTip, url);
/*     */       }
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
/* 340 */     if (obj == this) {
/* 341 */       return true;
/*     */     }
/* 343 */     if (!(obj instanceof XYTitleAnnotation)) {
/* 344 */       return false;
/*     */     }
/* 346 */     XYTitleAnnotation that = (XYTitleAnnotation)obj;
/* 347 */     if (this.coordinateType != that.coordinateType) {
/* 348 */       return false;
/*     */     }
/* 350 */     if (this.x != that.x) {
/* 351 */       return false;
/*     */     }
/* 353 */     if (this.y != that.y) {
/* 354 */       return false;
/*     */     }
/* 356 */     if (this.maxWidth != that.maxWidth) {
/* 357 */       return false;
/*     */     }
/* 359 */     if (this.maxHeight != that.maxHeight) {
/* 360 */       return false;
/*     */     }
/* 362 */     if (!ObjectUtilities.equal(this.title, that.title)) {
/* 363 */       return false;
/*     */     }
/* 365 */     if (!this.anchor.equals(that.anchor)) {
/* 366 */       return false;
/*     */     }
/* 368 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 378 */     int result = 193;
/* 379 */     result = HashUtilities.hashCode(result, this.anchor);
/* 380 */     result = HashUtilities.hashCode(result, this.coordinateType);
/* 381 */     result = HashUtilities.hashCode(result, this.x);
/* 382 */     result = HashUtilities.hashCode(result, this.y);
/* 383 */     result = HashUtilities.hashCode(result, this.maxWidth);
/* 384 */     result = HashUtilities.hashCode(result, this.maxHeight);
/* 385 */     result = HashUtilities.hashCode(result, this.title);
/* 386 */     return result;
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
/* 398 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/XYTitleAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */