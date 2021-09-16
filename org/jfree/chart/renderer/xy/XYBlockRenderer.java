/*     */ package org.jfree.chart.renderer.xy;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.plot.CrosshairState;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.LookupPaintScale;
/*     */ import org.jfree.chart.renderer.PaintScale;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.general.DatasetUtilities;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.data.xy.XYZDataset;
/*     */ import org.jfree.ui.RectangleAnchor;
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
/*     */ public class XYBlockRenderer
/*     */   extends AbstractXYItemRenderer
/*     */   implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
/*     */ {
/*  90 */   private double blockWidth = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private double blockHeight = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   private RectangleAnchor blockAnchor = RectangleAnchor.CENTER;
/*     */ 
/*     */ 
/*     */   
/*     */   private double xOffset;
/*     */ 
/*     */ 
/*     */   
/*     */   private double yOffset;
/*     */ 
/*     */   
/*     */   private PaintScale paintScale;
/*     */ 
/*     */ 
/*     */   
/*     */   public XYBlockRenderer() {
/* 117 */     updateOffsets();
/* 118 */     this.paintScale = (PaintScale)new LookupPaintScale();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBlockWidth() {
/* 129 */     return this.blockWidth;
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
/*     */   public void setBlockWidth(double width) {
/* 141 */     if (width <= 0.0D) {
/* 142 */       throw new IllegalArgumentException("The 'width' argument must be > 0.0");
/*     */     }
/*     */     
/* 145 */     this.blockWidth = width;
/* 146 */     updateOffsets();
/* 147 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBlockHeight() {
/* 158 */     return this.blockHeight;
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
/*     */   public void setBlockHeight(double height) {
/* 170 */     if (height <= 0.0D) {
/* 171 */       throw new IllegalArgumentException("The 'height' argument must be > 0.0");
/*     */     }
/*     */     
/* 174 */     this.blockHeight = height;
/* 175 */     updateOffsets();
/* 176 */     fireChangeEvent();
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
/*     */   public RectangleAnchor getBlockAnchor() {
/* 188 */     return this.blockAnchor;
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
/*     */   public void setBlockAnchor(RectangleAnchor anchor) {
/* 200 */     ParamChecks.nullNotPermitted(anchor, "anchor");
/* 201 */     if (this.blockAnchor.equals(anchor)) {
/*     */       return;
/*     */     }
/* 204 */     this.blockAnchor = anchor;
/* 205 */     updateOffsets();
/* 206 */     fireChangeEvent();
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
/*     */   public PaintScale getPaintScale() {
/* 218 */     return this.paintScale;
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
/*     */   public void setPaintScale(PaintScale scale) {
/* 231 */     ParamChecks.nullNotPermitted(scale, "scale");
/* 232 */     this.paintScale = scale;
/* 233 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateOffsets() {
/* 241 */     if (this.blockAnchor.equals(RectangleAnchor.BOTTOM_LEFT)) {
/* 242 */       this.xOffset = 0.0D;
/* 243 */       this.yOffset = 0.0D;
/*     */     }
/* 245 */     else if (this.blockAnchor.equals(RectangleAnchor.BOTTOM)) {
/* 246 */       this.xOffset = -this.blockWidth / 2.0D;
/* 247 */       this.yOffset = 0.0D;
/*     */     }
/* 249 */     else if (this.blockAnchor.equals(RectangleAnchor.BOTTOM_RIGHT)) {
/* 250 */       this.xOffset = -this.blockWidth;
/* 251 */       this.yOffset = 0.0D;
/*     */     }
/* 253 */     else if (this.blockAnchor.equals(RectangleAnchor.LEFT)) {
/* 254 */       this.xOffset = 0.0D;
/* 255 */       this.yOffset = -this.blockHeight / 2.0D;
/*     */     }
/* 257 */     else if (this.blockAnchor.equals(RectangleAnchor.CENTER)) {
/* 258 */       this.xOffset = -this.blockWidth / 2.0D;
/* 259 */       this.yOffset = -this.blockHeight / 2.0D;
/*     */     }
/* 261 */     else if (this.blockAnchor.equals(RectangleAnchor.RIGHT)) {
/* 262 */       this.xOffset = -this.blockWidth;
/* 263 */       this.yOffset = -this.blockHeight / 2.0D;
/*     */     }
/* 265 */     else if (this.blockAnchor.equals(RectangleAnchor.TOP_LEFT)) {
/* 266 */       this.xOffset = 0.0D;
/* 267 */       this.yOffset = -this.blockHeight;
/*     */     }
/* 269 */     else if (this.blockAnchor.equals(RectangleAnchor.TOP)) {
/* 270 */       this.xOffset = -this.blockWidth / 2.0D;
/* 271 */       this.yOffset = -this.blockHeight;
/*     */     }
/* 273 */     else if (this.blockAnchor.equals(RectangleAnchor.TOP_RIGHT)) {
/* 274 */       this.xOffset = -this.blockWidth;
/* 275 */       this.yOffset = -this.blockHeight;
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
/*     */ 
/*     */   
/*     */   public Range findDomainBounds(XYDataset dataset) {
/* 292 */     if (dataset == null) {
/* 293 */       return null;
/*     */     }
/* 295 */     Range r = DatasetUtilities.findDomainBounds(dataset, false);
/* 296 */     if (r == null) {
/* 297 */       return null;
/*     */     }
/* 299 */     return new Range(r.getLowerBound() + this.xOffset, r
/* 300 */         .getUpperBound() + this.blockWidth + this.xOffset);
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
/*     */   public Range findRangeBounds(XYDataset dataset) {
/* 316 */     if (dataset != null) {
/* 317 */       Range r = DatasetUtilities.findRangeBounds(dataset, false);
/* 318 */       if (r == null) {
/* 319 */         return null;
/*     */       }
/*     */       
/* 322 */       return new Range(r.getLowerBound() + this.yOffset, r
/* 323 */           .getUpperBound() + this.blockHeight + this.yOffset);
/*     */     } 
/*     */ 
/*     */     
/* 327 */     return null;
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
/*     */   
/*     */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/*     */     Rectangle2D block;
/* 353 */     double x = dataset.getXValue(series, item);
/* 354 */     double y = dataset.getYValue(series, item);
/* 355 */     double z = 0.0D;
/* 356 */     if (dataset instanceof XYZDataset) {
/* 357 */       z = ((XYZDataset)dataset).getZValue(series, item);
/*     */     }
/* 359 */     Paint p = this.paintScale.getPaint(z);
/* 360 */     double xx0 = domainAxis.valueToJava2D(x + this.xOffset, dataArea, plot
/* 361 */         .getDomainAxisEdge());
/* 362 */     double yy0 = rangeAxis.valueToJava2D(y + this.yOffset, dataArea, plot
/* 363 */         .getRangeAxisEdge());
/* 364 */     double xx1 = domainAxis.valueToJava2D(x + this.blockWidth + this.xOffset, dataArea, plot
/* 365 */         .getDomainAxisEdge());
/* 366 */     double yy1 = rangeAxis.valueToJava2D(y + this.blockHeight + this.yOffset, dataArea, plot
/* 367 */         .getRangeAxisEdge());
/*     */     
/* 369 */     PlotOrientation orientation = plot.getOrientation();
/* 370 */     if (orientation.equals(PlotOrientation.HORIZONTAL)) {
/*     */ 
/*     */       
/* 373 */       block = new Rectangle2D.Double(Math.min(yy0, yy1), Math.min(xx0, xx1), Math.abs(yy1 - yy0), Math.abs(xx0 - xx1));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 378 */       block = new Rectangle2D.Double(Math.min(xx0, xx1), Math.min(yy0, yy1), Math.abs(xx1 - xx0), Math.abs(yy1 - yy0));
/*     */     } 
/* 380 */     g2.setPaint(p);
/* 381 */     g2.fill(block);
/* 382 */     g2.setStroke(new BasicStroke(1.0F));
/* 383 */     g2.draw(block);
/*     */     
/* 385 */     EntityCollection entities = state.getEntityCollection();
/* 386 */     if (entities != null) {
/* 387 */       addEntity(entities, block, dataset, series, item, 0.0D, 0.0D);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 408 */     if (obj == this) {
/* 409 */       return true;
/*     */     }
/* 411 */     if (!(obj instanceof XYBlockRenderer)) {
/* 412 */       return false;
/*     */     }
/* 414 */     XYBlockRenderer that = (XYBlockRenderer)obj;
/* 415 */     if (this.blockHeight != that.blockHeight) {
/* 416 */       return false;
/*     */     }
/* 418 */     if (this.blockWidth != that.blockWidth) {
/* 419 */       return false;
/*     */     }
/* 421 */     if (!this.blockAnchor.equals(that.blockAnchor)) {
/* 422 */       return false;
/*     */     }
/* 424 */     if (!this.paintScale.equals(that.paintScale)) {
/* 425 */       return false;
/*     */     }
/* 427 */     return super.equals(obj);
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 440 */     XYBlockRenderer clone = (XYBlockRenderer)super.clone();
/* 441 */     if (this.paintScale instanceof PublicCloneable) {
/* 442 */       PublicCloneable pc = (PublicCloneable)this.paintScale;
/* 443 */       clone.paintScale = (PaintScale)pc.clone();
/*     */     } 
/* 445 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYBlockRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */