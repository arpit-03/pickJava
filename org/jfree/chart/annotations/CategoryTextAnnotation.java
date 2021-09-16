/*     */ package org.jfree.chart.annotations;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.CategoryAnchor;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.text.TextUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
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
/*     */ public class CategoryTextAnnotation
/*     */   extends TextAnnotation
/*     */   implements CategoryAnnotation, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3333360090781320147L;
/*     */   private Comparable category;
/*     */   private CategoryAnchor categoryAnchor;
/*     */   private double value;
/*     */   
/*     */   public CategoryTextAnnotation(String text, Comparable category, double value) {
/*  99 */     super(text);
/* 100 */     ParamChecks.nullNotPermitted(category, "category");
/* 101 */     this.category = category;
/* 102 */     this.value = value;
/* 103 */     this.categoryAnchor = CategoryAnchor.MIDDLE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparable getCategory() {
/* 114 */     return this.category;
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
/*     */   public void setCategory(Comparable category) {
/* 126 */     ParamChecks.nullNotPermitted(category, "category");
/* 127 */     this.category = category;
/* 128 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CategoryAnchor getCategoryAnchor() {
/* 139 */     return this.categoryAnchor;
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
/*     */   public void setCategoryAnchor(CategoryAnchor anchor) {
/* 151 */     ParamChecks.nullNotPermitted(anchor, "anchor");
/* 152 */     this.categoryAnchor = anchor;
/* 153 */     fireAnnotationChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValue() {
/* 164 */     return this.value;
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
/*     */   public void setValue(double value) {
/* 176 */     this.value = value;
/* 177 */     fireAnnotationChanged();
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
/* 193 */     CategoryDataset dataset = plot.getDataset();
/* 194 */     int catIndex = dataset.getColumnIndex(this.category);
/* 195 */     int catCount = dataset.getColumnCount();
/*     */     
/* 197 */     float anchorX = 0.0F;
/* 198 */     float anchorY = 0.0F;
/* 199 */     PlotOrientation orientation = plot.getOrientation();
/* 200 */     RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot
/* 201 */         .getDomainAxisLocation(), orientation);
/* 202 */     RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot
/* 203 */         .getRangeAxisLocation(), orientation);
/*     */     
/* 205 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 206 */       anchorY = (float)domainAxis.getCategoryJava2DCoordinate(this.categoryAnchor, catIndex, catCount, dataArea, domainEdge);
/*     */ 
/*     */       
/* 209 */       anchorX = (float)rangeAxis.valueToJava2D(this.value, dataArea, rangeEdge);
/*     */     
/*     */     }
/* 212 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 213 */       anchorX = (float)domainAxis.getCategoryJava2DCoordinate(this.categoryAnchor, catIndex, catCount, dataArea, domainEdge);
/*     */ 
/*     */       
/* 216 */       anchorY = (float)rangeAxis.valueToJava2D(this.value, dataArea, rangeEdge);
/*     */     } 
/*     */     
/* 219 */     g2.setFont(getFont());
/* 220 */     g2.setPaint(getPaint());
/* 221 */     TextUtilities.drawRotatedString(getText(), g2, anchorX, anchorY, 
/* 222 */         getTextAnchor(), getRotationAngle(), getRotationAnchor());
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
/* 235 */     if (obj == this) {
/* 236 */       return true;
/*     */     }
/* 238 */     if (!(obj instanceof CategoryTextAnnotation)) {
/* 239 */       return false;
/*     */     }
/* 241 */     CategoryTextAnnotation that = (CategoryTextAnnotation)obj;
/* 242 */     if (!super.equals(obj)) {
/* 243 */       return false;
/*     */     }
/* 245 */     if (!this.category.equals(that.getCategory())) {
/* 246 */       return false;
/*     */     }
/* 248 */     if (!this.categoryAnchor.equals(that.getCategoryAnchor())) {
/* 249 */       return false;
/*     */     }
/* 251 */     if (this.value != that.getValue()) {
/* 252 */       return false;
/*     */     }
/* 254 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 264 */     int result = super.hashCode();
/* 265 */     result = 37 * result + this.category.hashCode();
/* 266 */     result = 37 * result + this.categoryAnchor.hashCode();
/* 267 */     long temp = Double.doubleToLongBits(this.value);
/* 268 */     result = 37 * result + (int)(temp ^ temp >>> 32L);
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
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 282 */     return super.clone();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/annotations/CategoryTextAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */