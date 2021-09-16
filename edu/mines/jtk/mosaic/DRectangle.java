/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.util.Check;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DRectangle
/*     */ {
/*     */   public double x;
/*     */   public double y;
/*     */   public double width;
/*     */   public double height;
/*     */   
/*     */   public DRectangle(double x, double y, double width, double height) {
/*  61 */     Check.argument((width >= 0.0D), "width is non-negative");
/*  62 */     Check.argument((height >= 0.0D), "height is non-negative");
/*  63 */     this.x = x;
/*  64 */     this.y = y;
/*  65 */     this.width = width;
/*  66 */     this.height = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DRectangle(DRectangle r) {
/*  74 */     this(r.x, r.y, r.width, r.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DRectangle union(DRectangle rect) {
/*  83 */     double xn = Math.min(this.x, rect.x);
/*  84 */     double yn = Math.min(this.y, rect.y);
/*  85 */     double wn = Math.max(this.x + this.width, rect.x + rect.width) - xn;
/*  86 */     double hn = Math.max(this.y + this.height, rect.y + rect.height) - yn;
/*  87 */     return new DRectangle(xn, yn, wn, hn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DRectangle intersection(DRectangle rect) {
/*  96 */     double xn = Math.max(this.x, rect.x);
/*  97 */     double yn = Math.max(this.y, rect.y);
/*  98 */     double wn = Math.max(Math.min(this.x + this.width, rect.x + rect.width) - xn, 0.0D);
/*  99 */     double hn = Math.max(Math.min(this.y + this.height, rect.y + rect.height) - yn, 0.0D);
/* 100 */     return new DRectangle(xn, yn, wn, hn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 108 */     return (this.width <= 0.0D || this.height <= 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(DPoint point) {
/* 117 */     return contains(point.x, point.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(double x, double y) {
/* 127 */     return (this.x <= x && this.y <= y && x - this.x <= this.width && y - this.y <= this.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 134 */     if (this == obj)
/* 135 */       return true; 
/* 136 */     if (obj == null || getClass() != obj.getClass())
/* 137 */       return false; 
/* 138 */     DRectangle that = (DRectangle)obj;
/* 139 */     return (this.x == that.x && this.y == that.y && this.width == that.width && this.height == that.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 146 */     long xbits = Double.doubleToLongBits(this.x);
/* 147 */     long ybits = Double.doubleToLongBits(this.y);
/* 148 */     long wbits = Double.doubleToLongBits(this.width);
/* 149 */     long hbits = Double.doubleToLongBits(this.height);
/* 150 */     return (int)(xbits ^ xbits >>> 32L ^ ybits ^ ybits >>> 32L ^ wbits ^ wbits >>> 32L ^ hbits ^ hbits >>> 32L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 157 */     return "(" + this.x + "," + this.y + "," + this.width + "," + this.height + ")";
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/DRectangle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */