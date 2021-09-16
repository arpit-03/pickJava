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
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.axis.CategoryAnchor;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.category.CategoryDataset;
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
/*     */ public class CategoryLineAnnotation
/*     */   extends AbstractAnnotation
/*     */   implements CategoryAnnotation, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 3477740483341587984L;
/*     */   private Comparable category1;
/*     */   private double value1;
/*     */   private Comparable category2;
/*     */   private double value2;
/*  98 */   private transient Paint paint = Color.black;
/*     */ 
/*     */   
/* 101 */   private transient Stroke stroke = new BasicStroke(1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CategoryLineAnnotation(Comparable category1, double value1, Comparable category2, double value2, Paint paint, Stroke stroke) {
/* 118 */     ParamChecks.nullNotPermitted(category1, "category1");
/* 119 */     ParamChecks.nullNotPermitted(category2, "category2");
/* 120 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 121 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 122 */     this.category1 = category1;
/* 123 */     this.value1 = value1;
/* 124 */     this.category2 = category2;
/* 125 */     this.value2 = value2;
/* 126 */     this.paint = paint;
/* 127 */     this.stroke = stroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparable getCategory1() {
/* 138 */     return this.category1;
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
/*     */   public void setCategory1(Comparable category) {
/* 150 */     ParamChecks.nullNotPermitted(category, "category");
/* 151 */     this.category1 = category;
/* 152 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValue1() {
/* 163 */     return this.value1;
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
/*     */   public void setValue1(double value) {
/* 175 */     this.value1 = value;
/* 176 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparable getCategory2() {
/* 187 */     return this.category2;
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
/*     */   public void setCategory2(Comparable category) {
/* 199 */     ParamChecks.nullNotPermitted(category, "category");
/* 200 */     this.category2 = category;
/* 201 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValue2() {
/* 212 */     return this.value2;
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
/*     */   public void setValue2(double value) {
/* 224 */     this.value2 = value;
/* 225 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getPaint() {
/* 236 */     return this.paint;
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
/*     */   public void setPaint(Paint paint) {
/* 248 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 249 */     this.paint = paint;
/* 250 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getStroke() {
/* 261 */     return this.stroke;
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
/*     */   public void setStroke(Stroke stroke) {
/* 273 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 274 */     this.stroke = stroke;
/* 275 */     fireAnnotationChanged();
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
/*     */   public void draw(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea, CategoryAxis domainAxis, ValueAxis rangeAxis) {
/* 291 */     CategoryDataset dataset = plot.getDataset();
/* 292 */     int catIndex1 = dataset.getColumnIndex(this.category1);
/* 293 */     int catIndex2 = dataset.getColumnIndex(this.category2);
/* 294 */     int catCount = dataset.getColumnCount();
/*     */     
/* 296 */     double lineX1 = 0.0D;
/* 297 */     double lineY1 = 0.0D;
/* 298 */     double lineX2 = 0.0D;
/* 299 */     double lineY2 = 0.0D;
/* 300 */     PlotOrientation orientation = plot.getOrientation();
/* 301 */     RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot
/* 302 */         .getDomainAxisLocation(), orientation);
/* 303 */     RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot
/* 304 */         .getRangeAxisLocation(), orientation);
/*     */     
/* 306 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 307 */       lineY1 = domainAxis.getCategoryJava2DCoordinate(CategoryAnchor.MIDDLE, catIndex1, catCount, dataArea, domainEdge);
/*     */ 
/*     */       
/* 310 */       lineX1 = rangeAxis.valueToJava2D(this.value1, dataArea, rangeEdge);
/* 311 */       lineY2 = domainAxis.getCategoryJava2DCoordinate(CategoryAnchor.MIDDLE, catIndex2, catCount, dataArea, domainEdge);
/*     */ 
/*     */       
/* 314 */       lineX2 = rangeAxis.valueToJava2D(this.value2, dataArea, rangeEdge);
/*     */     }
/* 316 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 317 */       lineX1 = domainAxis.getCategoryJava2DCoordinate(CategoryAnchor.MIDDLE, catIndex1, catCount, dataArea, domainEdge);
/*     */ 
/*     */       
/* 320 */       lineY1 = rangeAxis.valueToJava2D(this.value1, dataArea, rangeEdge);
/* 321 */       lineX2 = domainAxis.getCategoryJava2DCoordinate(CategoryAnchor.MIDDLE, catIndex2, catCount, dataArea, domainEdge);
/*     */ 
/*     */       
/* 324 */       lineY2 = rangeAxis.valueToJava2D(this.value2, dataArea, rangeEdge);
/*     */     } 
/* 326 */     g2.setPaint(this.paint);
/* 327 */     g2.setStroke(this.stroke);
/* 328 */     g2.drawLine((int)lineX1, (int)lineY1, (int)lineX2, (int)lineY2);
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
/* 340 */     if (obj == this) {
/* 341 */       return true;
/*     */     }
/* 343 */     if (!(obj instanceof CategoryLineAnnotation)) {
/* 344 */       return false;
/*     */     }
/* 346 */     CategoryLineAnnotation that = (CategoryLineAnnotation)obj;
/* 347 */     if (!this.category1.equals(that.getCategory1())) {
/* 348 */       return false;
/*     */     }
/* 350 */     if (this.value1 != that.getValue1()) {
/* 351 */       return false;
/*     */     }
/* 353 */     if (!this.category2.equals(that.getCategory2())) {
/* 354 */       return false;
/*     */     }
/* 356 */     if (this.value2 != that.getValue2()) {
/* 357 */       return false;
/*     */     }
/* 359 */     if (!PaintUtilities.equal(this.paint, that.paint)) {
/* 360 */       return false;
/*     */     }
/* 362 */     if (!ObjectUtilities.equal(this.stroke, that.stroke)) {
/* 363 */       return false;
/*     */     }
/* 365 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 375 */     int result = 193;
/* 376 */     result = 37 * result + this.category1.hashCode();
/* 377 */     long temp = Double.doubleToLongBits(this.value1);
/* 378 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 379 */     result = 37 * result + this.category2.hashCode();
/* 380 */     temp = Double.doubleToLongBits(this.value2);
/* 381 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
/* 382 */     result = 37 * result + HashUtilities.hashCodeForPaint(this.paint);
/* 383 */     result = 37 * result + this.stroke.hashCode();
/* 384 */     return result;
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
/* 397 */     return super.clone();
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
/* 408 */     stream.defaultWriteObject();
/* 409 */     SerialUtilities.writePaint(this.paint, stream);
/* 410 */     SerialUtilities.writeStroke(this.stroke, stream);
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
/* 423 */     stream.defaultReadObject();
/* 424 */     this.paint = SerialUtilities.readPaint(stream);
/* 425 */     this.stroke = SerialUtilities.readStroke(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/CategoryLineAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */