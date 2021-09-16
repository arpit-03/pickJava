/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import org.jfree.chart.event.AxisChangeEvent;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModuloAxis
/*     */   extends NumberAxis
/*     */ {
/*     */   private Range fixedRange;
/*     */   private double displayStart;
/*     */   private double displayEnd;
/*     */   
/*     */   public ModuloAxis(String label, Range fixedRange) {
/*  80 */     super(label);
/*  81 */     this.fixedRange = fixedRange;
/*  82 */     this.displayStart = 270.0D;
/*  83 */     this.displayEnd = 90.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDisplayStart() {
/*  92 */     return this.displayStart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDisplayEnd() {
/* 101 */     return this.displayEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayRange(double start, double end) {
/* 112 */     this.displayStart = mapValueToFixedRange(start);
/* 113 */     this.displayEnd = mapValueToFixedRange(end);
/* 114 */     if (this.displayStart < this.displayEnd) {
/* 115 */       setRange(this.displayStart, this.displayEnd);
/*     */     } else {
/*     */       
/* 118 */       setRange(this.displayStart, this.fixedRange.getUpperBound() + this.displayEnd - this.fixedRange
/* 119 */           .getLowerBound());
/*     */     } 
/* 121 */     notifyListeners(new AxisChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void autoAdjustRange() {
/* 130 */     setRange(this.fixedRange, false, false);
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
/*     */   public double valueToJava2D(double value, Rectangle2D area, RectangleEdge edge) {
/* 146 */     double result, v = mapValueToFixedRange(value);
/* 147 */     if (this.displayStart < this.displayEnd) {
/* 148 */       result = trans(v, area, edge);
/*     */     } else {
/*     */       
/* 151 */       double cutoff = (this.displayStart + this.displayEnd) / 2.0D;
/* 152 */       double length1 = this.fixedRange.getUpperBound() - this.displayStart;
/*     */       
/* 154 */       double length2 = this.displayEnd - this.fixedRange.getLowerBound();
/* 155 */       if (v > cutoff) {
/* 156 */         result = transStart(v, area, edge, length1, length2);
/*     */       } else {
/*     */         
/* 159 */         result = transEnd(v, area, edge, length1, length2);
/*     */       } 
/*     */     } 
/* 162 */     return result;
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
/*     */   private double trans(double value, Rectangle2D area, RectangleEdge edge) {
/* 175 */     double min = 0.0D;
/* 176 */     double max = 0.0D;
/* 177 */     if (RectangleEdge.isTopOrBottom(edge)) {
/* 178 */       min = area.getX();
/* 179 */       max = area.getX() + area.getWidth();
/*     */     }
/* 181 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/* 182 */       min = area.getMaxY();
/* 183 */       max = area.getMaxY() - area.getHeight();
/*     */     } 
/* 185 */     if (isInverted()) {
/* 186 */       return max - (value - this.displayStart) / (this.displayEnd - this.displayStart) * (max - min);
/*     */     }
/*     */ 
/*     */     
/* 190 */     return min + (value - this.displayStart) / (this.displayEnd - this.displayStart) * (max - min);
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
/*     */   private double transStart(double value, Rectangle2D area, RectangleEdge edge, double length1, double length2) {
/* 211 */     double min = 0.0D;
/* 212 */     double max = 0.0D;
/* 213 */     if (RectangleEdge.isTopOrBottom(edge)) {
/* 214 */       min = area.getX();
/* 215 */       max = area.getX() + area.getWidth() * length1 / (length1 + length2);
/*     */     }
/* 217 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/* 218 */       min = area.getMaxY();
/* 219 */       max = area.getMaxY() - area.getHeight() * length1 / (length1 + length2);
/*     */     } 
/*     */     
/* 222 */     if (isInverted())
/*     */     {
/* 224 */       return max - (value - this.displayStart) / (this.fixedRange.getUpperBound() - this.displayStart) * (max - min);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 229 */     return min + (value - this.displayStart) / (this.fixedRange.getUpperBound() - this.displayStart) * (max - min);
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
/*     */   private double transEnd(double value, Rectangle2D area, RectangleEdge edge, double length1, double length2) {
/* 249 */     double min = 0.0D;
/* 250 */     double max = 0.0D;
/* 251 */     if (RectangleEdge.isTopOrBottom(edge)) {
/* 252 */       max = area.getMaxX();
/* 253 */       min = area.getMaxX() - area.getWidth() * length2 / (length1 + length2);
/*     */     
/*     */     }
/* 256 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/* 257 */       max = area.getMinY();
/* 258 */       min = area.getMinY() + area.getHeight() * length2 / (length1 + length2);
/*     */     } 
/*     */     
/* 261 */     if (isInverted())
/*     */     {
/* 263 */       return max - (value - this.fixedRange.getLowerBound()) / (this.displayEnd - this.fixedRange.getLowerBound()) * (max - min);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 268 */     return min + (value - this.fixedRange.getLowerBound()) / (this.displayEnd - this.fixedRange.getLowerBound()) * (max - min);
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
/*     */   private double mapValueToFixedRange(double value) {
/* 282 */     double lower = this.fixedRange.getLowerBound();
/* 283 */     double length = this.fixedRange.getLength();
/* 284 */     if (value < lower) {
/* 285 */       return lower + length + (value - lower) % length;
/*     */     }
/*     */     
/* 288 */     return lower + (value - lower) % length;
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
/*     */   public double java2DToValue(double java2DValue, Rectangle2D area, RectangleEdge edge) {
/* 304 */     double result = 0.0D;
/* 305 */     if (this.displayStart < this.displayEnd) {
/* 306 */       result = super.java2DToValue(java2DValue, area, edge);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 311 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getDisplayLength() {
/* 320 */     if (this.displayStart < this.displayEnd) {
/* 321 */       return this.displayEnd - this.displayStart;
/*     */     }
/*     */ 
/*     */     
/* 325 */     return this.fixedRange.getUpperBound() - this.displayStart + this.displayEnd - this.fixedRange.getLowerBound();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getDisplayCentralValue() {
/* 335 */     return mapValueToFixedRange(this.displayStart + 
/* 336 */         getDisplayLength() / 2.0D);
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
/*     */   public void resizeRange(double percent) {
/* 351 */     resizeRange(percent, getDisplayCentralValue());
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
/*     */   public void resizeRange(double percent, double anchorValue) {
/* 368 */     if (percent > 0.0D) {
/* 369 */       double halfLength = getDisplayLength() * percent / 2.0D;
/* 370 */       setDisplayRange(anchorValue - halfLength, anchorValue + halfLength);
/*     */     } else {
/*     */       
/* 373 */       setAutoRange(true);
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
/*     */   public double lengthToJava2D(double length, Rectangle2D area, RectangleEdge edge) {
/* 391 */     double areaLength, axisLength = 0.0D;
/* 392 */     if (this.displayEnd > this.displayStart) {
/* 393 */       axisLength = this.displayEnd - this.displayStart;
/*     */     }
/*     */     else {
/*     */       
/* 397 */       axisLength = this.fixedRange.getUpperBound() - this.displayStart + this.displayEnd - this.fixedRange.getLowerBound();
/*     */     } 
/*     */     
/* 400 */     if (RectangleEdge.isLeftOrRight(edge)) {
/* 401 */       areaLength = area.getHeight();
/*     */     } else {
/*     */       
/* 404 */       areaLength = area.getWidth();
/*     */     } 
/* 406 */     return length / axisLength * areaLength;
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
/* 418 */     if (obj == this) {
/* 419 */       return true;
/*     */     }
/* 421 */     if (!(obj instanceof ModuloAxis)) {
/* 422 */       return false;
/*     */     }
/* 424 */     ModuloAxis that = (ModuloAxis)obj;
/* 425 */     if (this.displayStart != that.displayStart) {
/* 426 */       return false;
/*     */     }
/* 428 */     if (this.displayEnd != that.displayEnd) {
/* 429 */       return false;
/*     */     }
/* 431 */     if (!this.fixedRange.equals(that.fixedRange)) {
/* 432 */       return false;
/*     */     }
/* 434 */     return super.equals(obj);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/ModuloAxis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */