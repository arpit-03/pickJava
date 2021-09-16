/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.util.UnitType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RectangleInsets
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1902273207559319996L;
/*  70 */   public static final RectangleInsets ZERO_INSETS = new RectangleInsets(UnitType.ABSOLUTE, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */ 
/*     */ 
/*     */   
/*     */   private UnitType unitType;
/*     */ 
/*     */ 
/*     */   
/*     */   private double top;
/*     */ 
/*     */ 
/*     */   
/*     */   private double left;
/*     */ 
/*     */ 
/*     */   
/*     */   private double bottom;
/*     */ 
/*     */   
/*     */   private double right;
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleInsets() {
/*  94 */     this(1.0D, 1.0D, 1.0D, 1.0D);
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
/*     */   public RectangleInsets(double top, double left, double bottom, double right) {
/* 107 */     this(UnitType.ABSOLUTE, top, left, bottom, right);
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
/*     */   public RectangleInsets(UnitType unitType, double top, double left, double bottom, double right) {
/* 123 */     if (unitType == null) {
/* 124 */       throw new IllegalArgumentException("Null 'unitType' argument.");
/*     */     }
/* 126 */     this.unitType = unitType;
/* 127 */     this.top = top;
/* 128 */     this.bottom = bottom;
/* 129 */     this.left = left;
/* 130 */     this.right = right;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnitType getUnitType() {
/* 140 */     return this.unitType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTop() {
/* 149 */     return this.top;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBottom() {
/* 158 */     return this.bottom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLeft() {
/* 167 */     return this.left;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRight() {
/* 176 */     return this.right;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 187 */     if (obj == this) {
/* 188 */       return true;
/*     */     }
/* 190 */     if (!(obj instanceof RectangleInsets)) {
/* 191 */       return false;
/*     */     }
/* 193 */     RectangleInsets that = (RectangleInsets)obj;
/* 194 */     if (that.unitType != this.unitType) {
/* 195 */       return false;
/*     */     }
/* 197 */     if (this.left != that.left) {
/* 198 */       return false;
/*     */     }
/* 200 */     if (this.right != that.right) {
/* 201 */       return false;
/*     */     }
/* 203 */     if (this.top != that.top) {
/* 204 */       return false;
/*     */     }
/* 206 */     if (this.bottom != that.bottom) {
/* 207 */       return false;
/*     */     }
/* 209 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 220 */     int result = (this.unitType != null) ? this.unitType.hashCode() : 0;
/* 221 */     long temp = (this.top != 0.0D) ? Double.doubleToLongBits(this.top) : 0L;
/* 222 */     result = 29 * result + (int)(temp ^ temp >>> 32L);
/* 223 */     temp = (this.bottom != 0.0D) ? Double.doubleToLongBits(this.bottom) : 0L;
/* 224 */     result = 29 * result + (int)(temp ^ temp >>> 32L);
/* 225 */     temp = (this.left != 0.0D) ? Double.doubleToLongBits(this.left) : 0L;
/* 226 */     result = 29 * result + (int)(temp ^ temp >>> 32L);
/* 227 */     temp = (this.right != 0.0D) ? Double.doubleToLongBits(this.right) : 0L;
/* 228 */     result = 29 * result + (int)(temp ^ temp >>> 32L);
/* 229 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 239 */     return "RectangleInsets[t=" + this.top + ",l=" + this.left + ",b=" + this.bottom + ",r=" + this.right + "]";
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
/*     */   public Rectangle2D createAdjustedRectangle(Rectangle2D base, LengthAdjustmentType horizontal, LengthAdjustmentType vertical) {
/* 259 */     if (base == null) {
/* 260 */       throw new IllegalArgumentException("Null 'base' argument.");
/*     */     }
/* 262 */     double x = base.getX();
/* 263 */     double y = base.getY();
/* 264 */     double w = base.getWidth();
/* 265 */     double h = base.getHeight();
/* 266 */     if (horizontal == LengthAdjustmentType.EXPAND) {
/* 267 */       double leftOutset = calculateLeftOutset(w);
/* 268 */       x -= leftOutset;
/* 269 */       w = w + leftOutset + calculateRightOutset(w);
/*     */     }
/* 271 */     else if (horizontal == LengthAdjustmentType.CONTRACT) {
/* 272 */       double leftMargin = calculateLeftInset(w);
/* 273 */       x += leftMargin;
/* 274 */       w = w - leftMargin - calculateRightInset(w);
/*     */     } 
/* 276 */     if (vertical == LengthAdjustmentType.EXPAND) {
/* 277 */       double topMargin = calculateTopOutset(h);
/* 278 */       y -= topMargin;
/* 279 */       h = h + topMargin + calculateBottomOutset(h);
/*     */     }
/* 281 */     else if (vertical == LengthAdjustmentType.CONTRACT) {
/* 282 */       double topMargin = calculateTopInset(h);
/* 283 */       y += topMargin;
/* 284 */       h = h - topMargin - calculateBottomInset(h);
/*     */     } 
/* 286 */     return new Rectangle2D.Double(x, y, w, h);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle2D createInsetRectangle(Rectangle2D base) {
/* 297 */     return createInsetRectangle(base, true, true);
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
/*     */   public Rectangle2D createInsetRectangle(Rectangle2D base, boolean horizontal, boolean vertical) {
/* 312 */     if (base == null) {
/* 313 */       throw new IllegalArgumentException("Null 'base' argument.");
/*     */     }
/* 315 */     double topMargin = 0.0D;
/* 316 */     double bottomMargin = 0.0D;
/* 317 */     if (vertical) {
/* 318 */       topMargin = calculateTopInset(base.getHeight());
/* 319 */       bottomMargin = calculateBottomInset(base.getHeight());
/*     */     } 
/* 321 */     double leftMargin = 0.0D;
/* 322 */     double rightMargin = 0.0D;
/* 323 */     if (horizontal) {
/* 324 */       leftMargin = calculateLeftInset(base.getWidth());
/* 325 */       rightMargin = calculateRightInset(base.getWidth());
/*     */     } 
/* 327 */     return new Rectangle2D.Double(base
/* 328 */         .getX() + leftMargin, base
/* 329 */         .getY() + topMargin, base
/* 330 */         .getWidth() - leftMargin - rightMargin, base
/* 331 */         .getHeight() - topMargin - bottomMargin);
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
/*     */   public Rectangle2D createOutsetRectangle(Rectangle2D base) {
/* 343 */     return createOutsetRectangle(base, true, true);
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
/*     */   public Rectangle2D createOutsetRectangle(Rectangle2D base, boolean horizontal, boolean vertical) {
/* 358 */     if (base == null) {
/* 359 */       throw new IllegalArgumentException("Null 'base' argument.");
/*     */     }
/* 361 */     double topMargin = 0.0D;
/* 362 */     double bottomMargin = 0.0D;
/* 363 */     if (vertical) {
/* 364 */       topMargin = calculateTopOutset(base.getHeight());
/* 365 */       bottomMargin = calculateBottomOutset(base.getHeight());
/*     */     } 
/* 367 */     double leftMargin = 0.0D;
/* 368 */     double rightMargin = 0.0D;
/* 369 */     if (horizontal) {
/* 370 */       leftMargin = calculateLeftOutset(base.getWidth());
/* 371 */       rightMargin = calculateRightOutset(base.getWidth());
/*     */     } 
/* 373 */     return new Rectangle2D.Double(base
/* 374 */         .getX() - leftMargin, base
/* 375 */         .getY() - topMargin, base
/* 376 */         .getWidth() + leftMargin + rightMargin, base
/* 377 */         .getHeight() + topMargin + bottomMargin);
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
/*     */   public double calculateTopInset(double height) {
/* 389 */     double result = this.top;
/* 390 */     if (this.unitType == UnitType.RELATIVE) {
/* 391 */       result = this.top * height;
/*     */     }
/* 393 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateTopOutset(double height) {
/* 404 */     double result = this.top;
/* 405 */     if (this.unitType == UnitType.RELATIVE) {
/* 406 */       result = height / (1.0D - this.top - this.bottom) * this.top;
/*     */     }
/* 408 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateBottomInset(double height) {
/* 419 */     double result = this.bottom;
/* 420 */     if (this.unitType == UnitType.RELATIVE) {
/* 421 */       result = this.bottom * height;
/*     */     }
/* 423 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateBottomOutset(double height) {
/* 434 */     double result = this.bottom;
/* 435 */     if (this.unitType == UnitType.RELATIVE) {
/* 436 */       result = height / (1.0D - this.top - this.bottom) * this.bottom;
/*     */     }
/* 438 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateLeftInset(double width) {
/* 449 */     double result = this.left;
/* 450 */     if (this.unitType == UnitType.RELATIVE) {
/* 451 */       result = this.left * width;
/*     */     }
/* 453 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateLeftOutset(double width) {
/* 464 */     double result = this.left;
/* 465 */     if (this.unitType == UnitType.RELATIVE) {
/* 466 */       result = width / (1.0D - this.left - this.right) * this.left;
/*     */     }
/* 468 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateRightInset(double width) {
/* 479 */     double result = this.right;
/* 480 */     if (this.unitType == UnitType.RELATIVE) {
/* 481 */       result = this.right * width;
/*     */     }
/* 483 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateRightOutset(double width) {
/* 494 */     double result = this.right;
/* 495 */     if (this.unitType == UnitType.RELATIVE) {
/* 496 */       result = width / (1.0D - this.left - this.right) * this.right;
/*     */     }
/* 498 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double trimWidth(double width) {
/* 509 */     return width - calculateLeftInset(width) - calculateRightInset(width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double extendWidth(double width) {
/* 520 */     return width + calculateLeftOutset(width) + calculateRightOutset(width);
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
/*     */   public double trimHeight(double height) {
/* 532 */     return height - calculateTopInset(height) - calculateBottomInset(height);
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
/*     */   public double extendHeight(double height) {
/* 544 */     return height + calculateTopOutset(height) + calculateBottomOutset(height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim(Rectangle2D area) {
/* 553 */     double w = area.getWidth();
/* 554 */     double h = area.getHeight();
/* 555 */     double l = calculateLeftInset(w);
/* 556 */     double r = calculateRightInset(w);
/* 557 */     double t = calculateTopInset(h);
/* 558 */     double b = calculateBottomInset(h);
/* 559 */     area.setRect(area.getX() + l, area.getY() + t, w - l - r, h - t - b);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/RectangleInsets.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */