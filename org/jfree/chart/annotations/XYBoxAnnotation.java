/*     */ package org.jfree.chart.annotations;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.plot.XYPlot;
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
/*     */ public class XYBoxAnnotation
/*     */   extends AbstractXYAnnotation
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6764703772526757457L;
/*     */   private double x0;
/*     */   private double y0;
/*     */   private double x1;
/*     */   private double y1;
/*     */   private transient Stroke stroke;
/*     */   private transient Paint outlinePaint;
/*     */   private transient Paint fillPaint;
/*     */   
/*     */   public XYBoxAnnotation(double x0, double y0, double x1, double y1) {
/* 107 */     this(x0, y0, x1, y1, new BasicStroke(1.0F), Color.black);
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
/*     */   public XYBoxAnnotation(double x0, double y0, double x1, double y1, Stroke stroke, Paint outlinePaint) {
/* 123 */     this(x0, y0, x1, y1, stroke, outlinePaint, (Paint)null);
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
/*     */   public XYBoxAnnotation(double x0, double y0, double x1, double y1, Stroke stroke, Paint outlinePaint, Paint fillPaint) {
/* 141 */     this.x0 = x0;
/* 142 */     this.y0 = y0;
/* 143 */     this.x1 = x1;
/* 144 */     this.y1 = y1;
/* 145 */     this.stroke = stroke;
/* 146 */     this.outlinePaint = outlinePaint;
/* 147 */     this.fillPaint = fillPaint;
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
/*     */   public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex, PlotRenderingInfo info) {
/* 167 */     PlotOrientation orientation = plot.getOrientation();
/* 168 */     RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot
/* 169 */         .getDomainAxisLocation(), orientation);
/* 170 */     RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot
/* 171 */         .getRangeAxisLocation(), orientation);
/*     */     
/* 173 */     double transX0 = domainAxis.valueToJava2D(this.x0, dataArea, domainEdge);
/*     */     
/* 175 */     double transY0 = rangeAxis.valueToJava2D(this.y0, dataArea, rangeEdge);
/* 176 */     double transX1 = domainAxis.valueToJava2D(this.x1, dataArea, domainEdge);
/*     */     
/* 178 */     double transY1 = rangeAxis.valueToJava2D(this.y1, dataArea, rangeEdge);
/*     */     
/* 180 */     Rectangle2D box = null;
/* 181 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 182 */       box = new Rectangle2D.Double(transY0, transX1, transY1 - transY0, transX0 - transX1);
/*     */     
/*     */     }
/* 185 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 186 */       box = new Rectangle2D.Double(transX0, transY1, transX1 - transX0, transY0 - transY1);
/*     */     } 
/*     */ 
/*     */     
/* 190 */     if (this.fillPaint != null) {
/* 191 */       g2.setPaint(this.fillPaint);
/* 192 */       g2.fill(box);
/*     */     } 
/*     */     
/* 195 */     if (this.stroke != null && this.outlinePaint != null) {
/* 196 */       g2.setPaint(this.outlinePaint);
/* 197 */       g2.setStroke(this.stroke);
/* 198 */       g2.draw(box);
/*     */     } 
/* 200 */     addEntity(info, box, rendererIndex, getToolTipText(), getURL());
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
/* 213 */     if (obj == this) {
/* 214 */       return true;
/*     */     }
/*     */     
/* 217 */     if (!super.equals(obj)) {
/* 218 */       return false;
/*     */     }
/* 220 */     if (!(obj instanceof XYBoxAnnotation)) {
/* 221 */       return false;
/*     */     }
/* 223 */     XYBoxAnnotation that = (XYBoxAnnotation)obj;
/* 224 */     if (this.x0 != that.x0) {
/* 225 */       return false;
/*     */     }
/* 227 */     if (this.y0 != that.y0) {
/* 228 */       return false;
/*     */     }
/* 230 */     if (this.x1 != that.x1) {
/* 231 */       return false;
/*     */     }
/* 233 */     if (this.y1 != that.y1) {
/* 234 */       return false;
/*     */     }
/* 236 */     if (!ObjectUtilities.equal(this.stroke, that.stroke)) {
/* 237 */       return false;
/*     */     }
/* 239 */     if (!PaintUtilities.equal(this.outlinePaint, that.outlinePaint)) {
/* 240 */       return false;
/*     */     }
/* 242 */     if (!PaintUtilities.equal(this.fillPaint, that.fillPaint)) {
/* 243 */       return false;
/*     */     }
/*     */     
/* 246 */     return true;
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
/*     */   public int hashCode() {
/* 258 */     long temp = Double.doubleToLongBits(this.x0);
/* 259 */     int result = (int)(temp ^ temp >>> 32L);
/* 260 */     temp = Double.doubleToLongBits(this.x1);
/* 261 */     result = 29 * result + (int)(temp ^ temp >>> 32L);
/* 262 */     temp = Double.doubleToLongBits(this.y0);
/* 263 */     result = 29 * result + (int)(temp ^ temp >>> 32L);
/* 264 */     temp = Double.doubleToLongBits(this.y1);
/* 265 */     result = 29 * result + (int)(temp ^ temp >>> 32L);
/* 266 */     return result;
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
/* 279 */     return super.clone();
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
/* 290 */     stream.defaultWriteObject();
/* 291 */     SerialUtilities.writeStroke(this.stroke, stream);
/* 292 */     SerialUtilities.writePaint(this.outlinePaint, stream);
/* 293 */     SerialUtilities.writePaint(this.fillPaint, stream);
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
/* 307 */     stream.defaultReadObject();
/* 308 */     this.stroke = SerialUtilities.readStroke(stream);
/* 309 */     this.outlinePaint = SerialUtilities.readPaint(stream);
/* 310 */     this.fillPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/XYBoxAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */