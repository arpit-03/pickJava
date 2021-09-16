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
/*     */ public class IntPoint
/*     */ {
/*     */   public int x;
/*     */   public int y;
/*     */   
/*     */   public IntPoint() {}
/*     */   
/*     */   public IntPoint(IntPoint point) {
/*  51 */     this.x = point.x;
/*  52 */     this.y = point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntPoint(int x, int y) {
/*  61 */     this.x = x;
/*  62 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntPoint(float x, float y) {
/*  71 */     this.x = (int)x;
/*  72 */     this.y = (int)y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntPoint(double x, double y) {
/*  81 */     this.x = (int)x;
/*  82 */     this.y = (int)y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntPoint(FloatPoint point) {
/*  90 */     this.x = (int)point.x;
/*  91 */     this.y = (int)point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntPoint(DoublePoint point) {
/*  99 */     this.x = (int)point.x;
/* 100 */     this.y = (int)point.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXY(int x, int y) {
/* 109 */     this.x = x;
/* 110 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Add(IntPoint point) {
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
/*     */   public static IntPoint Add(IntPoint point1, IntPoint point2) {
/* 129 */     IntPoint result = new IntPoint(point1);
/* 130 */     result.Add(point2);
/* 131 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Add(int value) {
/* 139 */     this.x += value;
/* 140 */     this.y += value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Subtract(IntPoint point) {
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
/*     */   public static IntPoint Subtract(IntPoint point1, IntPoint point2) {
/* 159 */     IntPoint result = new IntPoint(point1);
/* 160 */     result.Subtract(point2);
/* 161 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Subtract(int value) {
/* 169 */     this.x -= value;
/* 170 */     this.y -= value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Multiply(IntPoint point) {
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
/*     */   public static IntPoint Multiply(IntPoint point1, IntPoint point2) {
/* 189 */     IntPoint result = new IntPoint(point1);
/* 190 */     result.Multiply(point2);
/* 191 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Multiply(int value) {
/* 199 */     this.x *= value;
/* 200 */     this.y *= value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(IntPoint point) {
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
/*     */   public static IntPoint Divide(IntPoint point1, IntPoint point2) {
/* 219 */     IntPoint result = new IntPoint(point1);
/* 220 */     result.Divide(point2);
/* 221 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(int value) {
/* 229 */     this.x /= value;
/* 230 */     this.y /= value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float DistanceTo(IntPoint anotherPoint) {
/* 239 */     float dx = (this.x - anotherPoint.x);
/* 240 */     float dy = (this.y - anotherPoint.y);
/*     */     
/* 242 */     return (float)Math.sqrt((dx * dx + dy * dy));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatPoint toFloatPoint() {
/* 250 */     return new FloatPoint(this.x, this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoublePoint toDoublePoint() {
/* 258 */     return new DoublePoint(this.x, this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Swap() {
/* 265 */     int temp = this.x;
/* 266 */     this.x = this.y;
/* 267 */     this.y = temp;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 272 */     if (obj.getClass().isAssignableFrom(IntPoint.class)) {
/* 273 */       IntPoint point = (IntPoint)obj;
/* 274 */       if (this.x == point.x && this.y == point.y) {
/* 275 */         return true;
/*     */       }
/*     */     } 
/* 278 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 283 */     int hash = 3;
/* 284 */     hash = 67 * hash + this.x;
/* 285 */     hash = 67 * hash + this.y;
/* 286 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 291 */     return "X: " + this.x + " Y: " + this.y;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Core/IntPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */