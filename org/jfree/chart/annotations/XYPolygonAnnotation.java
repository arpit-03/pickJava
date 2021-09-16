/*     */ package org.jfree.chart.annotations;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
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
/*     */ public class XYPolygonAnnotation
/*     */   extends AbstractXYAnnotation
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6984203651995900036L;
/*     */   private double[] polygon;
/*     */   private transient Stroke stroke;
/*     */   private transient Paint outlinePaint;
/*     */   private transient Paint fillPaint;
/*     */   
/*     */   public XYPolygonAnnotation(double[] polygon) {
/* 102 */     this(polygon, new BasicStroke(1.0F), Color.black);
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
/*     */   public XYPolygonAnnotation(double[] polygon, Stroke stroke, Paint outlinePaint) {
/* 119 */     this(polygon, stroke, outlinePaint, (Paint)null);
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
/*     */   public XYPolygonAnnotation(double[] polygon, Stroke stroke, Paint outlinePaint, Paint fillPaint) {
/* 138 */     ParamChecks.nullNotPermitted(polygon, "polygon");
/* 139 */     if (polygon.length % 2 != 0) {
/* 140 */       throw new IllegalArgumentException("The 'polygon' array must contain an even number of items.");
/*     */     }
/*     */     
/* 143 */     this.polygon = (double[])polygon.clone();
/* 144 */     this.stroke = stroke;
/* 145 */     this.outlinePaint = outlinePaint;
/* 146 */     this.fillPaint = fillPaint;
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
/*     */   public double[] getPolygonCoordinates() {
/* 159 */     return (double[])this.polygon.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getFillPaint() {
/* 170 */     return this.fillPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getOutlineStroke() {
/* 181 */     return this.stroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getOutlinePaint() {
/* 192 */     return this.outlinePaint;
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
/* 213 */     if (this.polygon.length < 4) {
/*     */       return;
/*     */     }
/* 216 */     PlotOrientation orientation = plot.getOrientation();
/* 217 */     RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot
/* 218 */         .getDomainAxisLocation(), orientation);
/* 219 */     RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot
/* 220 */         .getRangeAxisLocation(), orientation);
/*     */     
/* 222 */     GeneralPath area = new GeneralPath();
/* 223 */     double x = domainAxis.valueToJava2D(this.polygon[0], dataArea, domainEdge);
/*     */     
/* 225 */     double y = rangeAxis.valueToJava2D(this.polygon[1], dataArea, rangeEdge);
/*     */     
/* 227 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 228 */       area.moveTo((float)y, (float)x);
/* 229 */       for (int i = 2; i < this.polygon.length; i += 2) {
/* 230 */         x = domainAxis.valueToJava2D(this.polygon[i], dataArea, domainEdge);
/*     */         
/* 232 */         y = rangeAxis.valueToJava2D(this.polygon[i + 1], dataArea, rangeEdge);
/*     */         
/* 234 */         area.lineTo((float)y, (float)x);
/*     */       } 
/* 236 */       area.closePath();
/*     */     }
/* 238 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 239 */       area.moveTo((float)x, (float)y);
/* 240 */       for (int i = 2; i < this.polygon.length; i += 2) {
/* 241 */         x = domainAxis.valueToJava2D(this.polygon[i], dataArea, domainEdge);
/*     */         
/* 243 */         y = rangeAxis.valueToJava2D(this.polygon[i + 1], dataArea, rangeEdge);
/*     */         
/* 245 */         area.lineTo((float)x, (float)y);
/*     */       } 
/* 247 */       area.closePath();
/*     */     } 
/*     */ 
/*     */     
/* 251 */     if (this.fillPaint != null) {
/* 252 */       g2.setPaint(this.fillPaint);
/* 253 */       g2.fill(area);
/*     */     } 
/*     */     
/* 256 */     if (this.stroke != null && this.outlinePaint != null) {
/* 257 */       g2.setPaint(this.outlinePaint);
/* 258 */       g2.setStroke(this.stroke);
/* 259 */       g2.draw(area);
/*     */     } 
/* 261 */     addEntity(info, area, rendererIndex, getToolTipText(), getURL());
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
/* 274 */     if (obj == this) {
/* 275 */       return true;
/*     */     }
/*     */     
/* 278 */     if (!super.equals(obj)) {
/* 279 */       return false;
/*     */     }
/* 281 */     if (!(obj instanceof XYPolygonAnnotation)) {
/* 282 */       return false;
/*     */     }
/* 284 */     XYPolygonAnnotation that = (XYPolygonAnnotation)obj;
/* 285 */     if (!Arrays.equals(this.polygon, that.polygon)) {
/* 286 */       return false;
/*     */     }
/* 288 */     if (!ObjectUtilities.equal(this.stroke, that.stroke)) {
/* 289 */       return false;
/*     */     }
/* 291 */     if (!PaintUtilities.equal(this.outlinePaint, that.outlinePaint)) {
/* 292 */       return false;
/*     */     }
/* 294 */     if (!PaintUtilities.equal(this.fillPaint, that.fillPaint)) {
/* 295 */       return false;
/*     */     }
/*     */     
/* 298 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 308 */     int result = 193;
/* 309 */     result = 37 * result + HashUtilities.hashCodeForDoubleArray(this.polygon);
/*     */     
/* 311 */     result = 37 * result + HashUtilities.hashCodeForPaint(this.fillPaint);
/* 312 */     result = 37 * result + HashUtilities.hashCodeForPaint(this.outlinePaint);
/*     */     
/* 314 */     if (this.stroke != null) {
/* 315 */       result = 37 * result + this.stroke.hashCode();
/*     */     }
/* 317 */     return result;
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
/* 330 */     return super.clone();
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
/* 341 */     stream.defaultWriteObject();
/* 342 */     SerialUtilities.writeStroke(this.stroke, stream);
/* 343 */     SerialUtilities.writePaint(this.outlinePaint, stream);
/* 344 */     SerialUtilities.writePaint(this.fillPaint, stream);
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
/* 357 */     stream.defaultReadObject();
/* 358 */     this.stroke = SerialUtilities.readStroke(stream);
/* 359 */     this.outlinePaint = SerialUtilities.readPaint(stream);
/* 360 */     this.fillPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/XYPolygonAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */