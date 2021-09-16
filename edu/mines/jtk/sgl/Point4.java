/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Point4
/*     */   extends Tuple4
/*     */ {
/*     */   public Point4() {
/*  28 */     super(0.0D, 0.0D, 0.0D, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4(double x, double y, double z) {
/*  38 */     super(x, y, z, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4(double x, double y, double z, double w) {
/*  49 */     super(x, y, z, w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4(Point3 p) {
/*  58 */     super(p.x, p.y, p.z, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4(Point4 p) {
/*  66 */     super(p.x, p.y, p.z, p.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4 homogenize() {
/*  75 */     return new Point4(this.x / this.w, this.y / this.w, this.z / this.w, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4 homogenizeEquals() {
/*  84 */     this.x /= this.w;
/*  85 */     this.y /= this.w;
/*  86 */     this.z /= this.w;
/*  87 */     this.w = 1.0D;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4 plus(Vector3 v) {
/*  97 */     return new Point4(this.x + v.x, this.y + v.y, this.z + v.z, this.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4 minus(Vector3 v) {
/* 106 */     return new Point4(this.x - v.x, this.y - v.y, this.z - v.z, this.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4 plusEquals(Vector3 v) {
/* 115 */     this.x += v.x;
/* 116 */     this.y += v.y;
/* 117 */     this.z += v.z;
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4 minusEquals(Vector3 v) {
/* 127 */     this.x -= v.x;
/* 128 */     this.y -= v.y;
/* 129 */     this.z -= v.z;
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4 affine(double a, Point4 q) {
/* 140 */     double b = 1.0D - a;
/* 141 */     Point4 p = this;
/* 142 */     return new Point4(b * p.x + a * q.x, b * p.y + a * q.y, b * p.z + a * q.z, b * p.w + a * q.w);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/Point4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */