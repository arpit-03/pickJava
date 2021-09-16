/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.text.TextBlockAnchor;
/*     */ import org.jfree.ui.RectangleAnchor;
/*     */ import org.jfree.ui.RectangleEdge;
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
/*     */ public class CategoryLabelPositions
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8999557901920364580L;
/*  65 */   public static final CategoryLabelPositions STANDARD = new CategoryLabelPositions(new CategoryLabelPosition(RectangleAnchor.BOTTOM, TextBlockAnchor.BOTTOM_CENTER), new CategoryLabelPosition(RectangleAnchor.TOP, TextBlockAnchor.TOP_CENTER), new CategoryLabelPosition(RectangleAnchor.RIGHT, TextBlockAnchor.CENTER_RIGHT, CategoryLabelWidthType.RANGE, 0.3F), new CategoryLabelPosition(RectangleAnchor.LEFT, TextBlockAnchor.CENTER_LEFT, CategoryLabelWidthType.RANGE, 0.3F));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final CategoryLabelPositions UP_90 = new CategoryLabelPositions(new CategoryLabelPosition(RectangleAnchor.BOTTOM, TextBlockAnchor.CENTER_LEFT, TextAnchor.CENTER_LEFT, -1.5707963267948966D, CategoryLabelWidthType.RANGE, 0.3F), new CategoryLabelPosition(RectangleAnchor.TOP, TextBlockAnchor.CENTER_RIGHT, TextAnchor.CENTER_RIGHT, -1.5707963267948966D, CategoryLabelWidthType.RANGE, 0.3F), new CategoryLabelPosition(RectangleAnchor.RIGHT, TextBlockAnchor.BOTTOM_CENTER, TextAnchor.BOTTOM_CENTER, -1.5707963267948966D, CategoryLabelWidthType.CATEGORY, 0.9F), new CategoryLabelPosition(RectangleAnchor.LEFT, TextBlockAnchor.TOP_CENTER, TextAnchor.TOP_CENTER, -1.5707963267948966D, CategoryLabelWidthType.CATEGORY, 0.9F));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public static final CategoryLabelPositions DOWN_90 = new CategoryLabelPositions(new CategoryLabelPosition(RectangleAnchor.BOTTOM, TextBlockAnchor.CENTER_RIGHT, TextAnchor.CENTER_RIGHT, 1.5707963267948966D, CategoryLabelWidthType.RANGE, 0.3F), new CategoryLabelPosition(RectangleAnchor.TOP, TextBlockAnchor.CENTER_LEFT, TextAnchor.CENTER_LEFT, 1.5707963267948966D, CategoryLabelWidthType.RANGE, 0.3F), new CategoryLabelPosition(RectangleAnchor.RIGHT, TextBlockAnchor.TOP_CENTER, TextAnchor.TOP_CENTER, 1.5707963267948966D, CategoryLabelWidthType.CATEGORY, 0.9F), new CategoryLabelPosition(RectangleAnchor.LEFT, TextBlockAnchor.BOTTOM_CENTER, TextAnchor.BOTTOM_CENTER, 1.5707963267948966D, CategoryLabelWidthType.CATEGORY, 0.9F));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public static final CategoryLabelPositions UP_45 = createUpRotationLabelPositions(0.7853981633974483D);
/*     */ 
/*     */ 
/*     */   
/* 126 */   public static final CategoryLabelPositions DOWN_45 = createDownRotationLabelPositions(0.7853981633974483D);
/*     */   
/*     */   private CategoryLabelPosition positionForAxisAtTop;
/*     */   
/*     */   private CategoryLabelPosition positionForAxisAtBottom;
/*     */   
/*     */   private CategoryLabelPosition positionForAxisAtLeft;
/*     */   
/*     */   private CategoryLabelPosition positionForAxisAtRight;
/*     */ 
/*     */   
/*     */   public static CategoryLabelPositions createUpRotationLabelPositions(double angle) {
/* 138 */     return new CategoryLabelPositions(new CategoryLabelPosition(RectangleAnchor.BOTTOM, TextBlockAnchor.BOTTOM_LEFT, TextAnchor.BOTTOM_LEFT, -angle, CategoryLabelWidthType.RANGE, 0.5F), new CategoryLabelPosition(RectangleAnchor.TOP, TextBlockAnchor.TOP_RIGHT, TextAnchor.TOP_RIGHT, -angle, CategoryLabelWidthType.RANGE, 0.5F), new CategoryLabelPosition(RectangleAnchor.RIGHT, TextBlockAnchor.BOTTOM_RIGHT, TextAnchor.BOTTOM_RIGHT, -angle, CategoryLabelWidthType.RANGE, 0.5F), new CategoryLabelPosition(RectangleAnchor.LEFT, TextBlockAnchor.TOP_LEFT, TextAnchor.TOP_LEFT, -angle, CategoryLabelWidthType.RANGE, 0.5F));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CategoryLabelPositions createDownRotationLabelPositions(double angle) {
/* 168 */     return new CategoryLabelPositions(new CategoryLabelPosition(RectangleAnchor.BOTTOM, TextBlockAnchor.BOTTOM_RIGHT, TextAnchor.BOTTOM_RIGHT, angle, CategoryLabelWidthType.RANGE, 0.5F), new CategoryLabelPosition(RectangleAnchor.TOP, TextBlockAnchor.TOP_LEFT, TextAnchor.TOP_LEFT, angle, CategoryLabelWidthType.RANGE, 0.5F), new CategoryLabelPosition(RectangleAnchor.RIGHT, TextBlockAnchor.TOP_RIGHT, TextAnchor.TOP_RIGHT, angle, CategoryLabelWidthType.RANGE, 0.5F), new CategoryLabelPosition(RectangleAnchor.LEFT, TextBlockAnchor.BOTTOM_LEFT, TextAnchor.BOTTOM_LEFT, angle, CategoryLabelWidthType.RANGE, 0.5F));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CategoryLabelPositions() {
/* 216 */     this.positionForAxisAtTop = new CategoryLabelPosition();
/* 217 */     this.positionForAxisAtBottom = new CategoryLabelPosition();
/* 218 */     this.positionForAxisAtLeft = new CategoryLabelPosition();
/* 219 */     this.positionForAxisAtRight = new CategoryLabelPosition();
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
/*     */   public CategoryLabelPositions(CategoryLabelPosition top, CategoryLabelPosition bottom, CategoryLabelPosition left, CategoryLabelPosition right) {
/* 238 */     ParamChecks.nullNotPermitted(top, "top");
/* 239 */     ParamChecks.nullNotPermitted(bottom, "bottom");
/* 240 */     ParamChecks.nullNotPermitted(left, "left");
/* 241 */     ParamChecks.nullNotPermitted(right, "right");
/*     */     
/* 243 */     this.positionForAxisAtTop = top;
/* 244 */     this.positionForAxisAtBottom = bottom;
/* 245 */     this.positionForAxisAtLeft = left;
/* 246 */     this.positionForAxisAtRight = right;
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
/*     */   public CategoryLabelPosition getLabelPosition(RectangleEdge edge) {
/* 258 */     CategoryLabelPosition result = null;
/* 259 */     if (edge == RectangleEdge.TOP) {
/* 260 */       result = this.positionForAxisAtTop;
/*     */     }
/* 262 */     else if (edge == RectangleEdge.BOTTOM) {
/* 263 */       result = this.positionForAxisAtBottom;
/*     */     }
/* 265 */     else if (edge == RectangleEdge.LEFT) {
/* 266 */       result = this.positionForAxisAtLeft;
/*     */     }
/* 268 */     else if (edge == RectangleEdge.RIGHT) {
/* 269 */       result = this.positionForAxisAtRight;
/*     */     } 
/* 271 */     return result;
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
/*     */   public static CategoryLabelPositions replaceTopPosition(CategoryLabelPositions base, CategoryLabelPosition top) {
/* 286 */     ParamChecks.nullNotPermitted(base, "base");
/* 287 */     ParamChecks.nullNotPermitted(top, "top");
/*     */     
/* 289 */     return new CategoryLabelPositions(top, base
/* 290 */         .getLabelPosition(RectangleEdge.BOTTOM), base
/* 291 */         .getLabelPosition(RectangleEdge.LEFT), base
/* 292 */         .getLabelPosition(RectangleEdge.RIGHT));
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
/*     */   public static CategoryLabelPositions replaceBottomPosition(CategoryLabelPositions base, CategoryLabelPosition bottom) {
/* 307 */     ParamChecks.nullNotPermitted(base, "base");
/* 308 */     ParamChecks.nullNotPermitted(bottom, "bottom");
/*     */     
/* 310 */     return new CategoryLabelPositions(base
/* 311 */         .getLabelPosition(RectangleEdge.TOP), bottom, base
/*     */         
/* 313 */         .getLabelPosition(RectangleEdge.LEFT), base
/* 314 */         .getLabelPosition(RectangleEdge.RIGHT));
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
/*     */   public static CategoryLabelPositions replaceLeftPosition(CategoryLabelPositions base, CategoryLabelPosition left) {
/* 329 */     ParamChecks.nullNotPermitted(base, "base");
/* 330 */     ParamChecks.nullNotPermitted(left, "left");
/*     */     
/* 332 */     return new CategoryLabelPositions(base
/* 333 */         .getLabelPosition(RectangleEdge.TOP), base
/* 334 */         .getLabelPosition(RectangleEdge.BOTTOM), left, base
/*     */         
/* 336 */         .getLabelPosition(RectangleEdge.RIGHT));
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
/*     */   public static CategoryLabelPositions replaceRightPosition(CategoryLabelPositions base, CategoryLabelPosition right) {
/* 351 */     ParamChecks.nullNotPermitted(base, "base");
/* 352 */     ParamChecks.nullNotPermitted(right, "right");
/* 353 */     return new CategoryLabelPositions(base
/* 354 */         .getLabelPosition(RectangleEdge.TOP), base
/* 355 */         .getLabelPosition(RectangleEdge.BOTTOM), base
/* 356 */         .getLabelPosition(RectangleEdge.LEFT), right);
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
/* 370 */     if (this == obj) {
/* 371 */       return true;
/*     */     }
/* 373 */     if (!(obj instanceof CategoryLabelPositions)) {
/* 374 */       return false;
/*     */     }
/*     */     
/* 377 */     CategoryLabelPositions that = (CategoryLabelPositions)obj;
/* 378 */     if (!this.positionForAxisAtTop.equals(that.positionForAxisAtTop)) {
/* 379 */       return false;
/*     */     }
/* 381 */     if (!this.positionForAxisAtBottom.equals(that.positionForAxisAtBottom))
/*     */     {
/* 383 */       return false;
/*     */     }
/* 385 */     if (!this.positionForAxisAtLeft.equals(that.positionForAxisAtLeft)) {
/* 386 */       return false;
/*     */     }
/* 388 */     if (!this.positionForAxisAtRight.equals(that.positionForAxisAtRight)) {
/* 389 */       return false;
/*     */     }
/* 391 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 401 */     int result = 19;
/* 402 */     result = 37 * result + this.positionForAxisAtTop.hashCode();
/* 403 */     result = 37 * result + this.positionForAxisAtBottom.hashCode();
/* 404 */     result = 37 * result + this.positionForAxisAtLeft.hashCode();
/* 405 */     result = 37 * result + this.positionForAxisAtRight.hashCode();
/* 406 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/CategoryLabelPositions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */