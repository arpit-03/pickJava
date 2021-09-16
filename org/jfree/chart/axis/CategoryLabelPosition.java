/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.text.TextBlockAnchor;
/*     */ import org.jfree.ui.RectangleAnchor;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CategoryLabelPosition
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5168681143844183864L;
/*     */   private RectangleAnchor categoryAnchor;
/*     */   private TextBlockAnchor labelAnchor;
/*     */   private TextAnchor rotationAnchor;
/*     */   private double angle;
/*     */   private CategoryLabelWidthType widthType;
/*     */   private float widthRatio;
/*     */   
/*     */   public CategoryLabelPosition() {
/*  91 */     this(RectangleAnchor.CENTER, TextBlockAnchor.BOTTOM_CENTER, TextAnchor.CENTER, 0.0D, CategoryLabelWidthType.CATEGORY, 0.95F);
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
/*     */   public CategoryLabelPosition(RectangleAnchor categoryAnchor, TextBlockAnchor labelAnchor) {
/* 105 */     this(categoryAnchor, labelAnchor, TextAnchor.CENTER, 0.0D, CategoryLabelWidthType.CATEGORY, 0.95F);
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
/*     */   public CategoryLabelPosition(RectangleAnchor categoryAnchor, TextBlockAnchor labelAnchor, CategoryLabelWidthType widthType, float widthRatio) {
/* 123 */     this(categoryAnchor, labelAnchor, TextAnchor.CENTER, 0.0D, widthType, widthRatio);
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
/*     */   public CategoryLabelPosition(RectangleAnchor categoryAnchor, TextBlockAnchor labelAnchor, TextAnchor rotationAnchor, double angle, CategoryLabelWidthType widthType, float widthRatio) {
/* 147 */     ParamChecks.nullNotPermitted(categoryAnchor, "categoryAnchor");
/* 148 */     ParamChecks.nullNotPermitted(labelAnchor, "labelAnchor");
/* 149 */     ParamChecks.nullNotPermitted(rotationAnchor, "rotationAnchor");
/* 150 */     ParamChecks.nullNotPermitted(widthType, "widthType");
/*     */     
/* 152 */     this.categoryAnchor = categoryAnchor;
/* 153 */     this.labelAnchor = labelAnchor;
/* 154 */     this.rotationAnchor = rotationAnchor;
/* 155 */     this.angle = angle;
/* 156 */     this.widthType = widthType;
/* 157 */     this.widthRatio = widthRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleAnchor getCategoryAnchor() {
/* 167 */     return this.categoryAnchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextBlockAnchor getLabelAnchor() {
/* 176 */     return this.labelAnchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextAnchor getRotationAnchor() {
/* 185 */     return this.rotationAnchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAngle() {
/* 194 */     return this.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CategoryLabelWidthType getWidthType() {
/* 203 */     return this.widthType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidthRatio() {
/* 212 */     return this.widthRatio;
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
/* 224 */     if (obj == this) {
/* 225 */       return true;
/*     */     }
/* 227 */     if (!(obj instanceof CategoryLabelPosition)) {
/* 228 */       return false;
/*     */     }
/* 230 */     CategoryLabelPosition that = (CategoryLabelPosition)obj;
/* 231 */     if (!this.categoryAnchor.equals(that.categoryAnchor)) {
/* 232 */       return false;
/*     */     }
/* 234 */     if (!this.labelAnchor.equals(that.labelAnchor)) {
/* 235 */       return false;
/*     */     }
/* 237 */     if (!this.rotationAnchor.equals(that.rotationAnchor)) {
/* 238 */       return false;
/*     */     }
/* 240 */     if (this.angle != that.angle) {
/* 241 */       return false;
/*     */     }
/* 243 */     if (this.widthType != that.widthType) {
/* 244 */       return false;
/*     */     }
/* 246 */     if (this.widthRatio != that.widthRatio) {
/* 247 */       return false;
/*     */     }
/* 249 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 259 */     int result = 19;
/* 260 */     result = 37 * result + this.categoryAnchor.hashCode();
/* 261 */     result = 37 * result + this.labelAnchor.hashCode();
/* 262 */     result = 37 * result + this.rotationAnchor.hashCode();
/* 263 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/CategoryLabelPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */