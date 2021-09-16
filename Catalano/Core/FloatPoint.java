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
/*     */ public class FloatPoint
/*     */ {
/*     */   public float x;
/*     */   public float y;
/*     */   
/*     */   public FloatPoint() {}
/*     */   
/*     */   public FloatPoint(FloatPoint point) {
/*  49 */     this.x = point.x;
/*  50 */     this.y = point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatPoint(float x, float y) {
/*  59 */     this.x = x;
/*  60 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatPoint(double x, double y) {
/*  69 */     this.x = (float)x;
/*  70 */     this.y = (float)y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatPoint(int x, int y) {
/*  79 */     this.x = x;
/*  80 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatPoint(IntPoint point) {
/*  88 */     this.x = point.x;
/*  89 */     this.y = point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatPoint(DoublePoint point) {
/*  97 */     this.x = (float)point.x;
/*  98 */     this.y = (float)point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXY(float x, float y) {
/* 107 */     this.x = x;
/* 108 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Add(FloatPoint point) {
/* 116 */     this.x += point.x;
/* 117 */     this.y += point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatPoint Add(FloatPoint point1, FloatPoint point2) {
/* 127 */     FloatPoint result = new FloatPoint(point1);
/* 128 */     result.Add(point2);
/* 129 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Add(float value) {
/* 137 */     this.x += value;
/* 138 */     this.y += value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Subtract(FloatPoint point) {
/* 146 */     this.x -= point.x;
/* 147 */     this.y -= point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatPoint Subtract(FloatPoint point1, FloatPoint point2) {
/* 157 */     FloatPoint result = new FloatPoint(point1);
/* 158 */     result.Subtract(point2);
/* 159 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Subtract(float value) {
/* 167 */     this.x -= value;
/* 168 */     this.y -= value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Multiply(FloatPoint point) {
/* 176 */     this.x *= point.x;
/* 177 */     this.y *= point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatPoint Multiply(FloatPoint point1, FloatPoint point2) {
/* 187 */     FloatPoint result = new FloatPoint(point1);
/* 188 */     result.Multiply(point2);
/* 189 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Multiply(float value) {
/* 197 */     this.x *= value;
/* 198 */     this.y *= value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(FloatPoint point) {
/* 206 */     this.x /= point.x;
/* 207 */     this.y /= point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatPoint Divide(FloatPoint point1, FloatPoint point2) {
/* 217 */     FloatPoint result = new FloatPoint(point1);
/* 218 */     result.Divide(point2);
/* 219 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(float value) {
/* 227 */     this.x /= value;
/* 228 */     this.y /= value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float DistanceTo(FloatPoint anotherPoint) {
/* 237 */     float dx = this.x - anotherPoint.x;
/* 238 */     float dy = this.y - anotherPoint.y;
/*     */     
/* 240 */     return (float)Math.sqrt((dx * dx + dy * dy));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntPoint toIntPoint() {
/* 248 */     return new IntPoint(this.x, this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoublePoint toDoublePoint() {
/* 256 */     return new DoublePoint(this.x, this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Swap() {
/* 263 */     float temp = this.x;
/* 264 */     this.x = this.y;
/* 265 */     this.y = temp;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 270 */     if (obj.getClass().isAssignableFrom(DoublePoint.class)) {
/* 271 */       FloatPoint point = (FloatPoint)obj;
/* 272 */       if (this.x == point.x && this.y == point.y) {
/* 273 */         return true;
/*     */       }
/*     */     } 
/* 276 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 281 */     int hash = 7;
/* 282 */     hash = 89 * hash + Float.floatToIntBits(this.x);
/* 283 */     hash = 89 * hash + Float.floatToIntBits(this.y);
/* 284 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 289 */     return "X: " + this.x + " Y: " + this.y;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Core/FloatPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */