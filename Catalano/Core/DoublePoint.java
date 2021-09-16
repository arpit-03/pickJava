/*     */ package Catalano.Core;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoublePoint
/*     */ {
/*     */   public double x;
/*     */   public double y;
/*     */   
/*     */   public DoublePoint() {}
/*     */   
/*     */   public DoublePoint(DoublePoint point) {
/*  51 */     this.x = point.x;
/*  52 */     this.y = point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoublePoint(double x, double y) {
/*  61 */     this.x = x;
/*  62 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoublePoint(float x, float y) {
/*  71 */     this.x = x;
/*  72 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoublePoint(int x, int y) {
/*  81 */     this.x = x;
/*  82 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoublePoint(IntPoint point) {
/*  90 */     this.x = point.x;
/*  91 */     this.y = point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoublePoint(FloatPoint point) {
/*  99 */     this.x = point.x;
/* 100 */     this.y = point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXY(double x, double y) {
/* 109 */     this.x = x;
/* 110 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Add(DoublePoint point) {
/* 118 */     this.x += point.x;
/* 119 */     this.y += point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoublePoint Add(DoublePoint point1, DoublePoint point2) {
/* 129 */     DoublePoint result = new DoublePoint(point1);
/* 130 */     result.Add(point2);
/* 131 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Add(double value) {
/* 139 */     this.x += value;
/* 140 */     this.y += value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Subtract(DoublePoint point) {
/* 148 */     this.x -= point.x;
/* 149 */     this.y -= point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoublePoint Subtract(DoublePoint point1, DoublePoint point2) {
/* 159 */     DoublePoint result = new DoublePoint(point1);
/* 160 */     result.Subtract(point2);
/* 161 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Subtract(double value) {
/* 169 */     this.x -= value;
/* 170 */     this.y -= value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Multiply(DoublePoint point) {
/* 178 */     this.x *= point.x;
/* 179 */     this.y *= point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoublePoint Multiply(DoublePoint point1, DoublePoint point2) {
/* 189 */     DoublePoint result = new DoublePoint(point1);
/* 190 */     result.Multiply(point2);
/* 191 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Multiply(double value) {
/* 199 */     this.x *= value;
/* 200 */     this.y *= value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(DoublePoint point) {
/* 208 */     this.x /= point.x;
/* 209 */     this.y /= point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoublePoint Divide(DoublePoint point1, DoublePoint point2) {
/* 219 */     DoublePoint result = new DoublePoint(point1);
/* 220 */     result.Divide(point2);
/* 221 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(double value) {
/* 229 */     this.x /= value;
/* 230 */     this.y /= value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double DistanceTo(DoublePoint anotherPoint) {
/* 239 */     double dx = this.x - anotherPoint.x;
/* 240 */     double dy = this.y - anotherPoint.y;
/*     */     
/* 242 */     return Math.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Swap() {
/* 249 */     double temp = this.x;
/* 250 */     this.x = this.y;
/* 251 */     this.y = temp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntPoint toIntPoint() {
/* 259 */     return new IntPoint(this.x, this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatPoint toFloatPoint() {
/* 267 */     return new FloatPoint(this.x, this.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 272 */     if (obj.getClass().isAssignableFrom(DoublePoint.class)) {
/* 273 */       DoublePoint point = (DoublePoint)obj;
/* 274 */       if (this.x == point.x && this.y == point.y) {
/* 275 */         return true;
/*     */       }
/*     */     } 
/* 278 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 283 */     int hash = 7;
/* 284 */     hash = 97 * hash + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32L);
/* 285 */     hash = 97 * hash + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32L);
/* 286 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 291 */     return "X: " + this.x + " Y: " + this.y;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Core/DoublePoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */