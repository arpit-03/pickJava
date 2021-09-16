/*     */ package org.jfree.chart.annotations;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PaintUtilities;
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
/*     */ public class XYShapeAnnotation
/*     */   extends AbstractXYAnnotation
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8553218317600684041L;
/*     */   private transient Shape shape;
/*     */   private transient Stroke stroke;
/*     */   private transient Paint outlinePaint;
/*     */   private transient Paint fillPaint;
/*     */   
/*     */   public XYShapeAnnotation(Shape shape) {
/* 114 */     this(shape, new BasicStroke(1.0F), Color.black);
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
/*     */   public XYShapeAnnotation(Shape shape, Stroke stroke, Paint outlinePaint) {
/* 126 */     this(shape, stroke, outlinePaint, (Paint)null);
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
/*     */   public XYShapeAnnotation(Shape shape, Stroke stroke, Paint outlinePaint, Paint fillPaint) {
/* 141 */     ParamChecks.nullNotPermitted(shape, "shape");
/* 142 */     this.shape = shape;
/* 143 */     this.stroke = stroke;
/* 144 */     this.outlinePaint = outlinePaint;
/* 145 */     this.fillPaint = fillPaint;
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
/* 166 */     PlotOrientation orientation = plot.getOrientation();
/* 167 */     RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot
/* 168 */         .getDomainAxisLocation(), orientation);
/* 169 */     RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot
/* 170 */         .getRangeAxisLocation(), orientation);
/*     */ 
/*     */ 
/*     */     
/* 174 */     Rectangle2D bounds = this.shape.getBounds2D();
/* 175 */     double x0 = bounds.getMinX();
/* 176 */     double x1 = bounds.getMaxX();
/* 177 */     double xx0 = domainAxis.valueToJava2D(x0, dataArea, domainEdge);
/* 178 */     double xx1 = domainAxis.valueToJava2D(x1, dataArea, domainEdge);
/* 179 */     double m00 = (xx1 - xx0) / (x1 - x0);
/* 180 */     double m02 = xx0 - x0 * m00;
/*     */     
/* 182 */     double y0 = bounds.getMaxY();
/* 183 */     double y1 = bounds.getMinY();
/* 184 */     double yy0 = rangeAxis.valueToJava2D(y0, dataArea, rangeEdge);
/* 185 */     double yy1 = rangeAxis.valueToJava2D(y1, dataArea, rangeEdge);
/* 186 */     double m11 = (yy1 - yy0) / (y1 - y0);
/* 187 */     double m12 = yy0 - m11 * y0;
/*     */ 
/*     */     
/* 190 */     Shape s = null;
/* 191 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 192 */       AffineTransform t1 = new AffineTransform(0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
/*     */       
/* 194 */       AffineTransform t2 = new AffineTransform(m11, 0.0D, 0.0D, m00, m12, m02);
/*     */       
/* 196 */       s = t1.createTransformedShape(this.shape);
/* 197 */       s = t2.createTransformedShape(s);
/*     */     }
/* 199 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 200 */       AffineTransform t = new AffineTransform(m00, 0.0D, 0.0D, m11, m02, m12);
/* 201 */       s = t.createTransformedShape(this.shape);
/*     */     } 
/*     */     
/* 204 */     if (this.fillPaint != null) {
/* 205 */       g2.setPaint(this.fillPaint);
/* 206 */       g2.fill(s);
/*     */     } 
/*     */     
/* 209 */     if (this.stroke != null && this.outlinePaint != null) {
/* 210 */       g2.setPaint(this.outlinePaint);
/* 211 */       g2.setStroke(this.stroke);
/* 212 */       g2.draw(s);
/*     */     } 
/* 214 */     addEntity(info, s, rendererIndex, getToolTipText(), getURL());
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
/* 227 */     if (obj == this) {
/* 228 */       return true;
/*     */     }
/*     */     
/* 231 */     if (!super.equals(obj)) {
/* 232 */       return false;
/*     */     }
/* 234 */     if (!(obj instanceof XYShapeAnnotation)) {
/* 235 */       return false;
/*     */     }
/* 237 */     XYShapeAnnotation that = (XYShapeAnnotation)obj;
/* 238 */     if (!this.shape.equals(that.shape)) {
/* 239 */       return false;
/*     */     }
/* 241 */     if (!ObjectUtilities.equal(this.stroke, that.stroke)) {
/* 242 */       return false;
/*     */     }
/* 244 */     if (!PaintUtilities.equal(this.outlinePaint, that.outlinePaint)) {
/* 245 */       return false;
/*     */     }
/* 247 */     if (!PaintUtilities.equal(this.fillPaint, that.fillPaint)) {
/* 248 */       return false;
/*     */     }
/*     */     
/* 251 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 261 */     int result = 193;
/* 262 */     result = 37 * result + this.shape.hashCode();
/* 263 */     if (this.stroke != null) {
/* 264 */       result = 37 * result + this.stroke.hashCode();
/*     */     }
/* 266 */     result = 37 * result + HashUtilities.hashCodeForPaint(this.outlinePaint);
/*     */     
/* 268 */     result = 37 * result + HashUtilities.hashCodeForPaint(this.fillPaint);
/* 269 */     return result;
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
/* 281 */     return super.clone();
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
/* 292 */     stream.defaultWriteObject();
/* 293 */     SerialUtilities.writeShape(this.shape, stream);
/* 294 */     SerialUtilities.writeStroke(this.stroke, stream);
/* 295 */     SerialUtilities.writePaint(this.outlinePaint, stream);
/* 296 */     SerialUtilities.writePaint(this.fillPaint, stream);
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
/* 309 */     stream.defaultReadObject();
/* 310 */     this.shape = SerialUtilities.readShape(stream);
/* 311 */     this.stroke = SerialUtilities.readStroke(stream);
/* 312 */     this.outlinePaint = SerialUtilities.readPaint(stream);
/* 313 */     this.fillPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/XYShapeAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */